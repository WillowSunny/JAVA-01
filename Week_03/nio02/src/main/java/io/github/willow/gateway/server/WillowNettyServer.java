package io.github.willow.gateway.server;

import io.github.willow.gateway.inbound.WillowInboundInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.Data;

import java.util.List;

@Data
public class WillowNettyServer {

    private int port;

    private List<String> proxyServers;

    public WillowNettyServer(int port, List<String> proxyServers) {
        this.port=port;
        this.proxyServers = proxyServers;
    }

    public void run() throws InterruptedException {
        //创建两个线程组bossGroup和workerGroup, 含有的子线程NioEventLoop的个数默认为cpu核数的两倍,
        // bossGroup只是处理连接请求，接待的人。 他就是为了解决响应不及时的问题,真正的和客户端业务处理，会交给workerGroup完成，他才是干活的人
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(16);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)//设置两个线程组
                    //使用NioServerSocketChannel作为服务器的通道实现
                    .channel(NioServerSocketChannel.class)
                    //ChannelOption.SO_BACKLOG对应的是tcp/ip协议listen函数中的backlog参数，函数listen(int socketfd,int backlog)用来初始化服务端可连接队列，
                    //服务端处理客户端连接请求是顺序处理的，所以同一时间只能处理一个客户端连接，多个客户端来的时候，
                    // 服务端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了队列的大小
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    //ChannelOption.TCP_NODELAY参数对应于套接字选项中的TCP_NODELAY,该参数的使用与Nagle算法有关
                    //Nagle算法是将小的数据包组装为更大的帧然后进行发送，而不是输入一次发送一次,因此在数据包不足的时候会等待其他数据的到了，
                    // 组装成大的数据包进行发送，虽然该方式有效提高网络的有效负载，但是却造成了延时，
                    // 而该参数的作用就是禁止使用Nagle算法，使用于小数据即时传输，
                    // 于TCP_NODELAY相对应的是TCP_CORK，该选项是需要等到发送的数据量最大的时候，一次性发送数据，适用于文件传输。
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    //Channeloption.SO_KEEPALIVE参数对应于套接字选项中的SO_KEEPALIVE，该参数用于设置TCP连接，
                    // 当设置该选项以后，连接会测试链接的状态，这个选项用于可能长时间没有数据交流的
                    //连接。当设置该选项以后，如果在两小时内没有数据的通信时，TCP会自动发送一个活动探测数据报文。
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //ChanneOption.SO_REUSEADDR对应于套接字选项中的SO_REUSEADDR，这个参数表示允许重复使用本地地址和端口，
                    //比如，某个服务器进程占用了TCP的80端口进行监听，此时再次监听该端口就会返回错误，使用该参数就可以解决问题，该参数允许共用该端口，这个在服务器程序中比较常使用，
                    //比如某个进程非正常退出，该程序占用的端口可能要被占用一段时间才能允许其他进程使用，而且程序死掉以后，内核一需要一定的时间才能够释放此端口，不设置SO_REUSEADDR
                    //就无法正常使用该端口。
                    .childOption(ChannelOption.SO_REUSEADDR, true)
                    // ChannelOption.SO_SNDBUF参数对应于套接字选项中的SO_SNDBUF，
                    // ChannelOption.SO_RCVBUF参数对应于套接字选项中的SO_RCVBUF
                    // 这两个参数用于操作接收缓冲区和发送缓冲区的大小，
                    // 接收缓冲区用于保存网络协议站内收到的数据，直到应用程序读取成功，发送缓冲区用于保存发送数据，直到发送成功
                    .childOption(ChannelOption.SO_RCVBUF, 32 * 1024)
                    .childOption(ChannelOption.SO_SNDBUF, 32 * 1024)
                    // 允许重用端口，
                    .childOption(EpollChannelOption.SO_REUSEPORT, true)
                    //
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new WillowInboundInitializer(this.proxyServers));

            Channel ch = bootstrap.bind(port).sync().channel();
            System.out.println("开启netty http服务器，监听地址和端口为 http://127.0.0.1:" + port + '/');
            ch.closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
