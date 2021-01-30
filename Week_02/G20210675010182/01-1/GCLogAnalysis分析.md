# 一、GC日志分析

设置程序持续运行时间为2秒。本机物理内存8g

1.1 使用java -Xmx128m -XX:+PrintGCDetails GCLogAnalysis，出现了OOM。

```
YongGC
1、总共发生了10次YGC
2、最长YGC耗时40毫秒
3、总耗时将近200毫秒
OldGC
1、在10次YongGC后就一直是FullGC了，一直到内存溢出。
2、FullGC最长一次耗时能达到30毫秒，
3、FullGC总共发生了27次，总耗时将近500毫秒

也就是，在程序运行的2秒内，STW达到了700毫秒。
```

1.2 使用java -Xmx512m -XX:+PrintGCDetails GCLogAnalysis，没有出现OOM。

```
总共创建对象7752
1、大概每3次YongGC后发生一次FullGC
2、YongGC发生了29次，平均耗时25毫秒，总耗时725毫秒
3、FullGC发生了8次，平均耗时70毫秒，总耗时560毫秒

STW达到了1285毫秒，但没有内存溢出。
```

1.3 使用java -Xmx1g -XX:+PrintGCDetails GCLogAnalysis

```
总共创建对象7924
1、大概每4次YongGC后发生一次FullGC
2、YongGC发生了22次，平均耗时35毫秒，总耗时770毫秒
3、FullGC发生了6次，平均耗时60，总耗时360毫秒

STW达到了1120毫秒
```

1.4 使用java -Xmx2g -XX:+PrintGCDetails GCLogAnalysis

```
总共创建对象14907
1、大概每3次YongGC后发生一次FullGC
2、YongGC发生了19次，平均耗时30毫秒，总耗时570毫秒
3、FullGC发生了6次，平均耗时50，总耗时300毫秒

STW达到了870毫秒
```

1.5 使用 java -Xmx4g -XX:+PrintGCDetails GCLogAnalysis 和 java -Xmx8g -XX:+PrintGCDetails GCLogAnalysis 

```
1、创建的对象数量相差不大，而且都能达到14000个左右
2、GC的日志差别不大。
```

总结：

1、分别使用了设置不同堆大小的命令进行GC日志的打印和分析，发现从2g开始，性能较好，2g到8g的设置区别都不大。也从侧面说明了，堆大小的设置也不是越大就越好的。

2、在没有指定垃圾收集器的情况下，1.8版本的jdk默认使用的gc策略是ParallelGC

增加了 -Xms的参数配置，即初始化堆大小。

1.6 使用 java -Xmx1g -Xms1g -XX:+PrintGCDetails GCLogAnalysis

```
很明显能看出来，FullGC的次数减少了很多，只发生了2次，YongGC也只发生了22次。且总共创建的对象有12996个。
每次YongGC的耗时在25毫秒左右。
```

1.7 使用 java -Xmx2g -Xms2g -XX:+PrintGCDetails GCLogAnalysis

```
[GC (Allocation Failure) [PSYoungGen: 524800K->87024K(611840K)] 524800K->146015K(2010112K), 0.0537840 secs] [Times: user=0.08 sys=0.09, real=0.05 secs]
[GC (Allocation Failure) [PSYoungGen: 611824K->87034K(611840K)] 670815K->264434K(2010112K), 0.0705150 secs] [Times: user=0.06 sys=0.17, real=0.07 secs]
[GC (Allocation Failure) [PSYoungGen: 611834K->87033K(611840K)] 789234K->370445K(2010112K), 0.0529487 secs] [Times: user=0.06 sys=0.11, real=0.05 secs]
[GC (Allocation Failure) [PSYoungGen: 611833K->87036K(611840K)] 895245K->493745K(2010112K), 0.0554797 secs] [Times: user=0.13 sys=0.06, real=0.06 secs]
[GC (Allocation Failure) [PSYoungGen: 611836K->87039K(611840K)] 1018545K->609942K(2010112K), 0.0704278 secs] [Times: user=0.13 sys=0.09, real=0.07 secs]
[GC (Allocation Failure) [PSYoungGen: 611839K->87034K(326144K)] 1134742K->714778K(1724416K), 0.1127206 secs] [Times: user=0.11 sys=0.23, real=0.11 secs]
[GC (Allocation Failure) [PSYoungGen: 326138K->138013K(377344K)] 953882K->773132K(1775616K), 0.0370935 secs] [Times: user=0.12 sys=0.00, real=0.04 secs]
[GC (Allocation Failure) [PSYoungGen: 377117K->176100K(467456K)] 1012236K->822598K(1865728K), 0.0432809 secs] [Times: user=0.06 sys=0.03, real=0.04 secs]
[GC (Allocation Failure) [PSYoungGen: 410596K->184248K(465920K)] 1057094K->861807K(1864192K), 0.0586310 secs] [Times: user=0.14 sys=0.05, real=0.06 secs]
[GC (Allocation Failure) [PSYoungGen: 418744K->142222K(466944K)] 1096303K->908868K(1865216K), 0.0993262 secs] [Times: user=0.09 sys=0.20, real=0.10 secs]
[GC (Allocation Failure) [PSYoungGen: 382350K->76220K(316416K)] 1148996K->955237K(1714688K), 0.1333744 secs] [Times: user=0.05 sys=0.28, real=0.13 secs]
执行结束!共生成对象次数:16535


将堆的大小调成了2g后，发现，虽然没有了FullGC，但是每次YongGC的耗时有时候达到了100毫秒以上，对性能就比较影响了。
```

1.8  使用 java -Xmx4g -Xms4g -XX:+PrintGCDetails GCLogAnalysis

```
[GC (Allocation Failure) [PSYoungGen: 1048576K->174582K(1223168K)] 1048576K->240623K(4019712K), 0.1946601 secs] [Times: user=0.19 sys=0.42, real=0.19 secs]
[GC (Allocation Failure) [PSYoungGen: 1223158K->174591K(1223168K)] 1289199K->364704K(4019712K), 0.2481135 secs] [Times: user=0.14 sys=0.64, real=0.25 secs]
[GC (Allocation Failure) [PSYoungGen: 1223167K->174583K(1223168K)] 1413280K->483406K(4019712K), 0.1358524 secs] [Times: user=0.16 sys=0.33, real=0.14 secs]
执行结束!共生成对象次数:14630

将堆的大小调成了4g后，发现，每次YongGC的耗时平均耗时将近200毫秒，对性能就比较影响了。
```

1.9 在关掉自适应后，java -Xmx2g -Xms2g -XX:-UseAdaptiveSizePolicy -XX:+PrintGCDetails GCLogAnalysis

```
[GC (Allocation Failure) [PSYoungGen: 524800K->87039K(611840K)] 524800K->137152K(2010112K), 0.0739560 secs] [Times: user=0.16 sys=0.11, real=0.07 secs]
[GC (Allocation Failure) [PSYoungGen: 611839K->87039K(611840K)] 661952K->250306K(2010112K), 0.0739754 secs] [Times: user=0.06 sys=0.17, real=0.07 secs]
[GC (Allocation Failure) [PSYoungGen: 611839K->87036K(611840K)] 775106K->365833K(2010112K), 0.0669452 secs] [Times: user=0.08 sys=0.09, real=0.07 secs]
[GC (Allocation Failure) [PSYoungGen: 611836K->87034K(611840K)] 890633K->482118K(2010112K), 0.0725528 secs] [Times: user=0.14 sys=0.08, real=0.07 secs]
[GC (Allocation Failure) [PSYoungGen: 611834K->87028K(611840K)] 1006918K->605217K(2010112K), 0.0566947 secs] [Times: user=0.11 sys=0.06, real=0.06 secs]
[GC (Allocation Failure) [PSYoungGen: 611828K->87037K(611840K)] 1130017K->722274K(2010112K), 0.0529863 secs] [Times: user=0.08 sys=0.11, real=0.05 secs]
[GC (Allocation Failure) [PSYoungGen: 611837K->87024K(611840K)] 1247074K->833300K(2010112K), 0.0531488 secs] [Times: user=0.08 sys=0.16, real=0.05 secs]
[GC (Allocation Failure) [PSYoungGen: 611824K->87034K(611840K)] 1358100K->952327K(2010112K), 0.0538011 secs] [Times: user=0.09 sys=0.16, real=0.05 secs]
[GC (Allocation Failure) [PSYoungGen: 611834K->87035K(611840K)] 1477127K->1062897K(2010112K), 0.0607805 secs] [Times: user=0.08 sys=0.11, real=0.06 secs]
[GC (Allocation Failure) [PSYoungGen: 611487K->87037K(611840K)] 1587349K->1174916K(2010112K), 0.1110677 secs] [Times: user=0.09 sys=0.28, real=0.11 secs]
执行结束!共生成对象次数:19809

这个应该是最优的，没有FullGC，且YongGC的次数并不多，如果堆大小设置成1g后，就会出现FullGC，且YongGC次数也会增多。
```

总结：

1、设置Xms比不设置Xms会好，

```
-Xms为应用初始化部署的时候，向操作系统申请多少内存。如果不设置-Xms后，应用只会申请一小部分的内存，当堆内存使用达到峰值，会进行GC而不会重新申请。当应用运行一段时间，堆内存最低水位会逐渐抬高。例如该监控图，一开始最低水位在0.2G，现在到达0.4G。当最低水位一直抬高，应用才会向操作系统申请扩容内存使用。直到到达-Xmx设置阀值。

如果使用VisualVM，在不设置Xms的时候，堆的大小一直在缓慢增加，而设置了堆的大小的，从一开始，堆的最大值就固定下来了。
```

2、关闭Eden和Survivor区的比例自适应会更好。



通过上面的分析，可以看到，不同的堆大小，YongGC的次数会多，或者少。这就引出了另一个问题，就是，YongGC多，好不好？这就是需要讨论的吞吐量和响应时间。

吞吐量=用户线程执行时间/(用户线程执行时间+GC时间)，比如虚拟机总共运行了2秒，但是STW时间达到了0.5秒，那吞吐量=1.5/2=75%

响应时间：虽然GC次数多，但是每次GC的时间都短的话，那就能让程序尽可能的快速响应。这个应该是对应高性能的说法。

这两个指标是从解释上来看，就是矛盾的。只能找到一个较好的平衡点。也就是在高吞吐的前提下尽可能的降低单次GC时间和GC频率。



# 二、分析不同GC使用情况

采用1g的大小进行测试不同的GC

2.1 使用 java -XX:-UseAdaptiveSizePolicy -XX:+PrintGCDetails -XX:+UseSerialGC -Xmx1g -Xms1g GCLogAnalysis

```
[GC (Allocation Failure) [DefNew: 279616K->34944K(314560K), 0.0848958 secs] 279616K->83538K(1013632K), 0.0949519 secs] [Times: user=0.08 sys=0.02, real=0.10 secs]
[GC (Allocation Failure) [DefNew: 314560K->34943K(314560K), 0.1129128 secs] 363154K->162907K(1013632K), 0.1171263 secs] [Times: user=0.09 sys=0.02, real=0.12 secs]
[GC (Allocation Failure) [DefNew: 314559K->34943K(314560K), 0.0747240 secs] 442523K->243997K(1013632K), 0.0767122 secs] [Times: user=0.02 sys=0.05, real=0.08 secs]
[GC (Allocation Failure) [DefNew: 314559K->34944K(314560K), 0.0511755 secs] 523613K->322011K(1013632K), 0.0513602 secs] [Times: user=0.02 sys=0.03, real=0.05 secs]
[GC (Allocation Failure) [DefNew: 314560K->34943K(314560K), 0.0554255 secs] 601627K->400955K(1013632K), 0.0557300 secs] [Times: user=0.03 sys=0.01, real=0.06 secs]
[GC (Allocation Failure) [DefNew: 314559K->34943K(314560K), 0.0543120 secs] 680571K->482867K(1013632K), 0.0547151 secs] [Times: user=0.05 sys=0.00, real=0.06 secs]
[GC (Allocation Failure) [DefNew: 314559K->34944K(314560K), 0.0589212 secs] 762483K->560706K(1013632K), 0.0692208 secs] [Times: user=0.05 sys=0.02, real=0.07 secs]
[GC (Allocation Failure) [DefNew: 314560K->34943K(314560K), 0.0620959 secs] 840322K->643966K(1013632K), 0.0625201 secs] [Times: user=0.05 sys=0.00, real=0.06 secs]
[GC (Allocation Failure) [DefNew: 314559K->314559K(314560K), 0.0001209 secs][Tenured: 609022K->380981K(699072K), 0.0845971 secs] 923582K->380981K(1013632K), [Metaspace: 2645K->2645K(10567
68K)], 0.0850053 secs] [Times: user=0.06 sys=0.00, real=0.09 secs]
[GC (Allocation Failure) [DefNew: 279616K->34943K(314560K), 0.0324426 secs] 660597K->473366K(1013632K), 0.0335413 secs] [Times: user=0.03 sys=0.00, real=0.03 secs]
[GC (Allocation Failure) [DefNew: 314326K->34943K(314560K), 0.0304647 secs] 752749K->543792K(1013632K), 0.0438830 secs] [Times: user=0.03 sys=0.00, real=0.04 secs]
[GC (Allocation Failure) [DefNew: 314559K->34943K(314560K), 0.0261559 secs] 823408K->622439K(1013632K), 0.0307013 secs] [Times: user=0.01 sys=0.00, real=0.03 secs]
执行结束!共生成对象次数:12661

从中可以看出并行GC的STW比较长，而且GC次数较多。这既没有达到高吞吐，也没没有达到高性能的指标。
```



```
[GC (Allocation Failure) [PSYoungGen: 262144K->43515K(305664K)] 262144K->77148K(1005056K), 0.0507085 secs] [Times: user=0.09 sys=0.09, real=0.05 secs]
[GC (Allocation Failure) [PSYoungGen: 305659K->43511K(305664K)] 339292K->160719K(1005056K), 0.0539619 secs] [Times: user=0.06 sys=0.05, real=0.05 secs]
[GC (Allocation Failure) [PSYoungGen: 305655K->43512K(305664K)] 422863K->224971K(1005056K), 0.0559090 secs] [Times: user=0.14 sys=0.01, real=0.06 secs]
[GC (Allocation Failure) [PSYoungGen: 305656K->43506K(305664K)] 487115K->303259K(1005056K), 0.0472465 secs] [Times: user=0.06 sys=0.03, real=0.05 secs]
[GC (Allocation Failure) [PSYoungGen: 305650K->43514K(305664K)] 565403K->375861K(1005056K), 0.0425950 secs] [Times: user=0.02 sys=0.05, real=0.04 secs]
[GC (Allocation Failure) [PSYoungGen: 305658K->43518K(305664K)] 638005K->442823K(1005056K), 0.0407619 secs] [Times: user=0.08 sys=0.03, real=0.04 secs]
[GC (Allocation Failure) [PSYoungGen: 305662K->43510K(305664K)] 704967K->517267K(1005056K), 0.0424884 secs] [Times: user=0.03 sys=0.08, real=0.04 secs]
[GC (Allocation Failure) [PSYoungGen: 305654K->43512K(305664K)] 779411K->586902K(1005056K), 0.0529048 secs] [Times: user=0.06 sys=0.09, real=0.05 secs]
[GC (Allocation Failure) [PSYoungGen: 305656K->43518K(305664K)] 849046K->661067K(1005056K), 0.0717546 secs] [Times: user=0.11 sys=0.13, real=0.07 secs]
[Full GC (Ergonomics) [PSYoungGen: 43518K->0K(305664K)] [ParOldGen: 617549K->335547K(699392K)] 661067K->335547K(1005056K), [Metaspace: 2645K->2645K(1056768K)], 0.0816653 secs] [Times: use
r=0.19 sys=0.00, real=0.08 secs]
[GC (Allocation Failure) [PSYoungGen: 262002K->43512K(305664K)] 597550K->424318K(1005056K), 0.0419884 secs] [Times: user=0.09 sys=0.00, real=0.04 secs]
[GC (Allocation Failure) [PSYoungGen: 305656K->43515K(305664K)] 686462K->494728K(1005056K), 0.0312943 secs] [Times: user=0.05 sys=0.00, real=0.03 secs]
[GC (Allocation Failure) [PSYoungGen: 305659K->43519K(305664K)] 756872K->568760K(1005056K), 0.0231802 secs] [Times: user=0.05 sys=0.00, real=0.02 secs]
[GC (Allocation Failure) [PSYoungGen: 305663K->43518K(305664K)] 830904K->637531K(1005056K), 0.0235109 secs] [Times: user=0.03 sys=0.00, real=0.02 secs]
[GC (Allocation Failure) [PSYoungGen: 305331K->43518K(305664K)] 899344K->707523K(1005056K), 0.0644644 secs] [Times: user=0.09 sys=0.06, real=0.06 secs]
[Full GC (Ergonomics) [PSYoungGen: 43518K->0K(305664K)] [ParOldGen: 664005K->367510K(699392K)] 707523K->367510K(1005056K), [Metaspace: 2645K->2645K(1056768K)], 0.0925618 secs] [Times: use
r=0.20 sys=0.00, real=0.09 secs]
执行结束!共生成对象次数:13721


ParallelGC，GC频率也比较高，而且伴随FullGC，但是可以看到，单次YongGC的STW时间对比SerialGC是少了一半，这就相当于同时提升了吞吐量和响应性能。
```



```
[GC (Allocation Failure) [ParNew: 272640K->34047K(306688K), 0.0659166 secs] 272640K->88241K(1014528K), 0.0699426 secs] [Times: user=0.16 sys=0.09, real=0.07 secs]
[GC (Allocation Failure) [ParNew: 306687K->34048K(306688K), 0.0801646 secs] 360881K->166001K(1014528K), 0.0803795 secs] [Times: user=0.08 sys=0.19, real=0.09 secs]
[GC (Allocation Failure) [ParNew: 306688K->34048K(306688K), 0.0750228 secs] 438641K->243222K(1014528K), 0.0832891 secs] [Times: user=0.14 sys=0.05, real=0.08 secs]
[GC (Allocation Failure) [ParNew: 306688K->34048K(306688K), 0.0771484 secs] 515862K->324203K(1014528K), 0.0774027 secs] [Times: user=0.23 sys=0.06, real=0.08 secs]
[GC (Allocation Failure) [ParNew: 306688K->34048K(306688K), 0.0806800 secs] 596843K->393770K(1014528K), 0.0808893 secs] [Times: user=0.20 sys=0.03, real=0.08 secs]
[GC (CMS Initial Mark) [1 CMS-initial-mark: 359722K(707840K)] 394417K(1014528K), 0.0008022 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[CMS-concurrent-mark-start]
[CMS-concurrent-mark: 0.015/0.015 secs] [Times: user=0.01 sys=0.02, real=0.02 secs]
[CMS-concurrent-preclean-start]
[CMS-concurrent-preclean: 0.002/0.002 secs] [Times: user=0.03 sys=0.00, real=0.00 secs]
[CMS-concurrent-abortable-preclean-start]
[GC (Allocation Failure) [ParNew[CMS-concurrent-abortable-preclean: 0.003/0.102 secs] [Times: user=0.17 sys=0.00, real=0.10 secs]
: 306688K->34048K(306688K), 0.0807661 secs] 666410K->469189K(1014528K), 0.0850709 secs] [Times: user=0.23 sys=0.00, real=0.09 secs]
[GC (CMS Final Remark) [YG occupancy: 34787 K (306688 K)][Rescan (parallel) , 0.0024665 secs][weak refs processing, 0.0000901 secs][class unloading, 0.0003387 secs][scrub symbol table, 0.
0004881 secs][scrub string table, 0.0001853 secs][1 CMS-remark: 435141K(707840K)] 469928K(1014528K), 0.0149025 secs] [Times: user=0.00 sys=0.00, real=0.02 secs]
[CMS-concurrent-sweep-start]
[CMS-concurrent-sweep: 0.002/0.002 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[CMS-concurrent-reset-start]
[CMS-concurrent-reset: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
这里只截取了第一部分。
ConcMarkSWeepGC：在FullGC出现前，出现了5次YongGC，耗时在350毫秒左右，出现的FullGC
1、初始标记：花了0.8毫秒，这个会暂停应用线程
2、并发标记：花了15毫秒，这个时候，应用线程和GC线程并行
3、并发预清理：花了2毫秒，这个时候，应用线程和GC线程是并行的
4、最终标记：这个最终标记做了以下这些事情：[Rescan (parallel) , 0.0024665 secs][weak refs processing, 0.0000901 secs][class unloading, 0.0003387 secs][scrub symbol table, 0.
0004881 secs][scrub string table, 0.0001853 secs]
这个过程是会暂停应用线程的。耗时14毫秒
5、并发清理：应用线程与GC线程并行，耗时2毫秒
6、并发重置：应用线程与GC线程并行，耗时1毫秒

整个STW的过程就是在初始标记和最终标记两个过程。

```



```
[GC pause (G1 Evacuation Pause) (young), 0.0061875 secs]
   [Parallel Time: 5.8 ms, GC Workers: 4]
      [GC Worker Start (ms): Min: 245.7, Avg: 247.6, Max: 250.1, Diff: 4.4]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.2, Max: 0.4, Diff: 0.4, Sum: 0.8]
      [Update RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
         [Processed Buffers: Min: 0, Avg: 0.0, Max: 0, Diff: 0, Sum: 0]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 1.3, Avg: 3.6, Max: 5.3, Diff: 4.0, Sum: 14.4]
      [Termination (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.1, Sum: 0.2]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.0, Sum: 0.2]
      [GC Worker Total (ms): Min: 1.3, Avg: 3.9, Max: 5.7, Diff: 4.4, Sum: 15.6]
      [GC Worker End (ms): Min: 251.4, Avg: 251.4, Max: 251.5, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.0 ms]
   [Other: 0.4 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.0 ms]
      [Humongous Register: 0.0 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.0 ms]
   [Eden: 51.0M(51.0M)->0.0B(44.0M) Survivors: 0.0B->7168.0K Heap: 62.0M(1024.0M)->20.9M(1024.0M)]
 [Times: user=0.05 sys=0.00, real=0.01 secs]

G1GC 
1、可以看到Eden区的大小是会变化的
2、存在YongGC和MixedGC，每次垃圾收集都会分好几个阶段，涉及RememberSet等
3、可以和明显的看出来，G1GC是使用的复制算法进行垃圾收集的。
```

