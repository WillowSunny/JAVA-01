# 字节码分析

## 1：源代码

```java
package com.willow.jvm.bytecode;

/**
 * <p>Description:</p>
 * Create by willow on 2021/1/8 at 12:43
 */
public class HelloByteCode {
    public static final int initData = 666;

    public int compute(int aa,int bb ,int cc,int dd){
        int a = 6;
        int b = 3;
        int c= (a+b)*10*7;

        if(c == 10){
            int d = 100;
            System.out.println("C==10,D="+d);
        }else{
            int d = 101;
            System.out.println("C==10,D="+d);
        }

        String[] str = new String[]{"s1","s2","s3"};
        String strAppend ="";
        for (String s : str){
            strAppend =strAppend + s;
        }
        System.out.println(strAppend);
        StringBuilder sb = new StringBuilder();
        for (String s : str){
            sb.append(s);
        }
        System.out.println(sb.toString());
        return c;
    }

    public static void main(String[] args){
        HelloByteCode hello = new HelloByteCode();
        hello.compute(1,2,3,1);
    }
}
```



## 2：字节码文件

使用`javap -v HelloByteCode.class`的命令得出下面详细的字节码信息。

```java
  Last modified 2021-1-12; size 1679 bytes	//最后修改日期	文件的大小
  MD5 checksum adeb62b77b620f44356cf13a2c9d1121
  Compiled from "HelloByteCode.java"	//.class 文件是从哪个java文件编译过来的。

//下面开始， 就是字节码的详细信息：
public class com.willow.jvm.bytecode.HelloByteCode
  minor version: 0	//jdk的次版本号
  major version: 52	//jdk的主版本号，52代表jdk8.
  flags: ACC_PUBLIC, ACC_SUPER	//访问权限
Constant pool:
/**
常量池：可以看做是Java类的一个资源仓库，类比一个java对象里的那些属性，需要用的时候，就可以通过索引去常量池中获取。
常量池中存放的常量分有两种：符号引用和字面量。不知道还有没有其他的。
下面分析每种类型的常量
*/

    /**
    1：Methodref：方法的符号引用 他的结构是：{tag:标记位；class_index:类引用在常量池索引(#18)；NameAndType_index:方法描述引用索引位置(#58)}
    */
   #1 = Methodref          #18.#58        // java/lang/Object."<init>":()V
   /**
    7：Fieldref：字段的符号引用 他的结构是：{tag:标记位；class_index:类引用在常量池索引(#59)；NameAndType_index:字段描述引用索引位置(#60)}
    #59 = Class              #76            // java/lang/System  指向类符号引用，最终指向类描述的字面量。
    #60 = NameAndType        #77:#78        // out:Ljava/io/PrintStream;
    #60会再指向了#77，#78，分别是字段名：out，字段的类型：java.io.PrintStream。
    如果点开System类，就可以看到定义了一个字段变量：public final static PrintStream out = null;
    
    这里说明一下：Ljava/io/PrintStream;  这个格式是字节码文件描述对象的一种写法，凡是L开头的都是代表一个对象类型，最后面那个分号是不能少的。
    */
   #2 = Fieldref           #59.#60        // java/lang/System.out:Ljava/io/PrintStream;
   #3 = Class              #61            // java/lang/StringBuilder
   #4 = Methodref          #3.#58         // java/lang/StringBuilder."<init>":()V
   /**
    8：String：字符串对象的符号引用 他的结构是：{tag:标记位；string_index:字符串信息的索引(#62)}
    #62 = Utf8               C==10,D=
    #62 就是一个字面量，直接描述了这个字符串的值是多少。
    源码里：
    if(c == 10){
            int d = 100;
            System.out.println("C==10,D="+d);
        }else{
            int d = 101;
            System.out.println("C==10,D="+d);
        }
    存在2个一模一样的字符串："C==10,D="，但是在字节码的常量池里只有一个，这里从侧面可以证明，字符串信息维护在常量池里，同样的字符串是只有一份的，是可以复用的。
    */
   #5 = String             #62            // C==10,D=
   #6 = Methodref          #3.#63         // java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
   #7 = Methodref          #3.#64         // java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
   #8 = Methodref          #3.#65         // java/lang/StringBuilder.toString:()Ljava/lang/String;
   #9 = Methodref          #66.#67        // java/io/PrintStream.println:(Ljava/lang/String;)V
  #10 = Class              #68            // java/lang/String
  #11 = String             #69            // s1
  #12 = String             #70            // s2
  #13 = String             #71            // s3
  #14 = String             #72            //
  #15 = Class              #73            // com/willow/jvm/bytecode/HelloByteCode
  #16 = Methodref          #15.#58        // com/willow/jvm/bytecode/HelloByteCode."<init>":()V
  #17 = Methodref          #15.#74        // com/willow/jvm/bytecode/HelloByteCode.compute:(IIII)I
  /**
    2：Class：类描述的符号引用，它指向了#75，而#75就是一个字面量了。按我的理解：字面量就是一个直接描述的表达形式。
    */     
  #18 = Class              #75            // java/lang/Object
  #19 = Utf8               initData
  #20 = Utf8               I
  /**
   9：#21，#22 是对static final 的变量进行的描述  
   #21 是字面量 代表：ConstantValue（常量值）  #22 字面量 代表：整型值 666
   按照一般的理解，#21，#22 是一个索引地址，但是在
   public static final int initData;
    descriptor: I
    flags: ACC_PUBLIC, ACC_STATIC, ACC_FINAL
    ConstantValue: int 666
    我没有看到有查找这个索引位置的地方，或者说整个字节码文件都没有查找这个索引地址的地方。
    所以我没搞懂，字节码这里将它维护在这里是怎么被用到的。
    */     
  #21 = Utf8               ConstantValue
  #22 = Integer            666
  /**
   5： Object的init方法名的索引代表的字面量
    */      
  #23 = Utf8               <init>
  /**
   6： Object的init方法的描述，在字节码里，方法描述有个固定的格式，就是：(先参数描述)，后面跟着返回值格式
    init方法：入参为空，返回值是void，在字节码里，用V代表void，所以，这里转成了字面量后就是：()V
    */ 
  #24 = Utf8               ()V
  #25 = Utf8               Code
  /**
  LineNumberTable属性用于描写叙述Java源码行号和字节码行号（字节码的偏移量:即在字节码文件中方法快里的“：”前面的数字）之间的相应关系，它不是执行时必须属性。但默认会生成到Class文件里。
  */
  #26 = Utf8               LineNumberTable
         /**
  LineNumberTable属性用于描写叙述栈帧中局部变量表中的变量与Java源码定义的变量之间的关系。
  */
  #27 = Utf8               LocalVariableTable
  #28 = Utf8               this
  #29 = Utf8               Lcom/willow/jvm/bytecode/HelloByteCode;
  #30 = Utf8               compute
  /**
  (IIII)I：代表：有4个入参，每个入参的类型都是int型，且其返回值也是int型。
  */
  #31 = Utf8               (IIII)I
  #32 = Utf8               d
  #33 = Utf8               s
  #34 = Utf8               Ljava/lang/String;
  #35 = Utf8               aa
  #36 = Utf8               bb
  #37 = Utf8               cc
  #38 = Utf8               dd
  #39 = Utf8               a
  #40 = Utf8               b
  #41 = Utf8               c
  #42 = Utf8               str
  #43 = Utf8               [Ljava/lang/String;
  #44 = Utf8               strAppend
  #45 = Utf8               sb
  #46 = Utf8               Ljava/lang/StringBuilder;
  /**
   StackMapTable  栈图表，没去研究。
    */                 
  #47 = Utf8               StackMapTable
  #48 = Class              #73            // com/willow/jvm/bytecode/HelloByteCode
  #49 = Class              #43            // "[Ljava/lang/String;"
  #50 = Class              #68            // java/lang/String
  #51 = Class              #61            // java/lang/StringBuilder
  #52 = Utf8               main
  #53 = Utf8               ([Ljava/lang/String;)V
  #54 = Utf8               args
  #55 = Utf8               hello
  #56 = Utf8               SourceFile
  #57 = Utf8               HelloByteCode.java
  /**
   4： NameAndType：方法描述引用，他的结构是：{name_index:方法名（#23）；descriptor_index:方法描述（#24）}
    */                         
  #58 = NameAndType        #23:#24        // "<init>":()V
  #59 = Class              #76            // java/lang/System
  #60 = NameAndType        #77:#78        // out:Ljava/io/PrintStream;
  #61 = Utf8               java/lang/StringBuilder
  #62 = Utf8               C==10,D=
  #63 = NameAndType        #79:#80        // append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
  #64 = NameAndType        #79:#81        // append:(I)Ljava/lang/StringBuilder;
  #65 = NameAndType        #82:#83        // toString:()Ljava/lang/String;
  #66 = Class              #84            // java/io/PrintStream
  #67 = NameAndType        #85:#86        // println:(Ljava/lang/String;)V
  #68 = Utf8               java/lang/String
  #69 = Utf8               s1
  #70 = Utf8               s2
  #71 = Utf8               s3
  #72 = Utf8
  #73 = Utf8               com/willow/jvm/bytecode/HelloByteCode
  #74 = NameAndType        #30:#31        // compute:(IIII)I
  /**
   3： Utf8：字面量，类比于实例对象的某个属性，有种直接表达：A代表A的意思。只是查找的时候是通过常量池的索引来定位的。
    */                           
  #75 = Utf8               java/lang/Object
  #76 = Utf8               java/lang/System
  #77 = Utf8               out
  #78 = Utf8               Ljava/io/PrintStream;
  #79 = Utf8               append
  #80 = Utf8               (Ljava/lang/String;)Ljava/lang/StringBuilder;
  #81 = Utf8               (I)Ljava/lang/StringBuilder;
  #82 = Utf8               toString
  #83 = Utf8               ()Ljava/lang/String;
  #84 = Utf8               java/io/PrintStream
  #85 = Utf8               println
  #86 = Utf8               (Ljava/lang/String;)V
{
  public static final int initData;
    descriptor: I
    flags: ACC_PUBLIC, ACC_STATIC, ACC_FINAL
    ConstantValue: int 666

  public com.willow.jvm.bytecode.HelloByteCode();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0					//将第一个引用类型本地变量推送至栈顶，也就是this
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V  调用超类构建方法, 实例初始化方法, 私有方法  这里我就不懂了，访问超类方法的不是用super吗？用this这个变量也可以？
         4: return
      LineNumberTable:
        line 7: 0
      LocalVariableTable:
   //   开始位移地址	变量的影响长度	变量槽位 变量名
        Start  		Length  	Slot  	Name   Signature
            0       5（0-4闭区间） 0      this   Lcom/willow/jvm/bytecode/HelloByteCode;//

  public int compute(int, int, int, int);
    descriptor: (IIII)I
    flags: ACC_PUBLIC
    Code:
  //  栈的深度	本地变量个数	方法的参数个数
      stack=4, locals=15, args_size=5
         0: bipush        6	//将单字节的常量值(-128~127)推送至栈顶 bipush  后面跟着的6是具体的值
         2: istore        5	//将栈顶int型数值存入指定本地变量槽位，数字5就代表的这个本地变量槽位下标（代码里的a）。 此时，栈顶的值必定是int型的。
         4: iconst_3		//将int型3推送至栈顶 这个是一种简写的写法
         5: istore        6	
         7: iload         5	//将本地变量表的槽位为5的int型数值压入栈顶
         9: iload         6
        11: iadd			//将栈顶两int型数值相加并将结果压入栈顶
        12: bipush        10
        14: imul			//将栈顶两int型数值相乘并将结果压入栈顶
        15: bipush        7
        17: imul
        18: istore        7
        20: iload         7
        22: bipush        10
        24: if_icmpne     60//比较栈顶两int型数值大小, 当结果不等于0时跳转 if_int compare not equal 后面跟着的60 是调到指定的指令执行位置，即“：”左边对应的值的地方。
        27: bipush        100
        29: istore        8
        31: getstatic     #2 //获取指定类的静态域, 并将其压入栈顶 Field java/lang/System.out:Ljava/io/PrintStream; 
        34: new           #3 //创建一个对象, 并将其引用引用值压入栈顶 class java/lang/StringBuilder。这里就有意思了：在源代码里写的是String，然后采用直接拼接的方式操作字符串，在字节码里的体现是会默认采用StringBuilder这个对象进行处理。
        37: dup	//复制栈顶数值并将复制值压入栈顶,此时，栈顶的前两个值应该都是刚刚创建的StringBuilder对象的引用值，为什么会这样操作，详情可以看：https://www.zhihu.com/question/52749416
        38: invokespecial #4                  // Method java/lang/StringBuilder."<init>":()V
        41: ldc           #5//将int,float或String型常量值从常量池中推送至栈顶，这里根据#5去常量池找最终的字面量 String C==10,D=  这时候，栈顶是这个将要拼接的字符串，栈的第二个信息是StringBuilder的对象引用。
        43: invokevirtual #6 //弹出栈顶的两个信息。调用实例方法，这里即StringBuilder对象的append方法。注意了，在执行完方法后，这个方法还会再次返回StringBuilder对象引用，然后会再次将返回的引用压入栈顶的。 Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        46: iload         8//将本地变量表槽位为8的int型数据压入栈顶。
        48: invokevirtual #7                  // Method java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        51: invokevirtual #8// Method java/lang/StringBuilder.toString:()Ljava/lang/String;此时栈顶是拼接的字符串，第二个是PrintStream对象引用
        54: invokevirtual #9// Method java/io/PrintStream.println:(Ljava/lang/String;)V 弹出字符串和PrintStream两个操作数，进行打印操作
        57: goto          90//调到执行指令90的地方 从27-57之间的指令是if里面的=条件的，所以从57到90之间是if里不等于范围的指令执行
        60: bipush        101
        62: istore        8
        64: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
        67: new           #3                  // class java/lang/StringBuilder
        70: dup
        71: invokespecial #4                  // Method java/lang/StringBuilder."<init>":()V
        74: ldc           #5                  // String C==10,D=
        76: invokevirtual #6                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        79: iload         8
        81: invokevirtual #7                  // Method java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        84: invokevirtual #8                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
        87: invokevirtual #9                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
        90: iconst_3	//将int型3推送至栈顶 这个是一种简写的写法		
        91: anewarray     #10  //创建一个引用型(如类, 接口, 数组)的数组, 并将其引用值压入栈顶 class java/lang/String
        94: dup //复制栈顶数组的引用值，并压入栈顶，这时候，栈顶前两个都是数组引用，第三个是int行数值3
        95: iconst_0//将int型0推送至栈顶 这个是一种简写的写法  这个应该是代表的数组的0下标
        96: ldc           #11 //将int,float或String型常量值从常量池中推送至栈顶 字面量“s1” String s1
        98: aastore//将栈顶引用型数值存入指定数组的指定索引位置：弹出栈顶的两个值，即字面量”s1“和数组引用值。 这个会吃掉3个操作数：下标，值，数组引用
        99: dup  //要开始放数组第二个数据了，因此，在字节码里是重复了上面的三个步骤。一直到108行结束。有初始值的数组才算是初始化成功了。从这里也可以看出来，我们写的一行代码，在编译期是会被拆分成好几个步骤的。
       100: iconst_1
       101: ldc           #12                 // String s2
       103: aastore
       104: dup
       105: iconst_2
       106: ldc           #13                 // String s3
       108: aastore//将下标2，和值s3，和数组引用从栈里弹出来，进行处理，处理后，栈顶是复制前的那个数组引用了，只剩下一个了。
       109: astore        8//将栈顶引用类型数值存入指定本地变量表8槽位，即数组引用在栈里已经被用完了。此时，栈里剩下的操作数是：在90步压入的int型3数值
       111: ldc           #14 //从常量池里加载一个 String值=“”到栈顶。此时栈的前两位是：“”和3
       113: astore        9//将栈顶引用类型数值存入指定本地变量表9槽位 即String strAppend ="";栈剩下3。
       115: aload         8//本地变量表8槽位加载引用类型数值到栈顶：将数组str引用放到栈顶
       117: astore        10//将栈顶数组引用数值存入指定本地变量表10槽位，这不是变相的将变量表槽位8的值赋值给了槽位10？ 这感觉不对啊。两个槽位的类型不一样啊。
       119: aload         10//接着又从本地变量表10槽位加载引用类型数值到栈顶，槽位10的作用域不是从186开始吗？怎么现在就加载到栈里了？
       121: arraylength
       122: istore        11
       124: iconst_0
       125: istore        12
       127: iload         12
       129: iload         11
       131: if_icmpge     169
       134: aload         10
       136: iload         12
       138: aaload
       139: astore        13
       141: new           #3                  // class java/lang/StringBuilder
       144: dup
       145: invokespecial #4                  // Method java/lang/StringBuilder."<init>":()V
       148: aload         9
       150: invokevirtual #6                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
       153: aload         13
       155: invokevirtual #6                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
       158: invokevirtual #8                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
       161: astore        9
       163: iinc          12, 1
       166: goto          127
       169: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
       172: aload         9
       174: invokevirtual #9                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
       177: new           #3                  // class java/lang/StringBuilder
       180: dup
       181: invokespecial #4                  // Method java/lang/StringBuilder."<init>":()V
       184: astore        10
       186: aload         8
       188: astore        11
       190: aload         11
       192: arraylength
       193: istore        12
       195: iconst_0
       196: istore        13
       198: iload         13
       200: iload         12
       202: if_icmpge     226
       205: aload         11
       207: iload         13
       209: aaload
       210: astore        14
       212: aload         10
       214: aload         14
       216: invokevirtual #6                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
       219: pop
       220: iinc          13, 1
       223: goto          198
       226: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
       229: aload         10
       231: invokevirtual #8                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
       234: invokevirtual #9                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
       237: iload         7
       239: ireturn
      LineNumberTable:
        line 11: 0
        line 12: 4
        line 13: 7
        line 15: 20
        line 16: 27
        line 17: 31
        line 18: 57
        line 19: 60
        line 20: 64
        line 23: 90
        line 24: 111
        line 25: 115
        line 26: 141
        line 25: 163
        line 28: 169
        line 29: 177
        line 30: 186
        line 31: 212
        line 30: 220
        line 33: 226
        line 34: 237
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
           31      26     8     d   I  //槽位8的数据存在两个，但是影响范围不一样。也就是Start不同。
           64      26     8     d   I	//这就说明了，如果是不同影响域的同一个名字的变量，他们是可以共用同一个槽位的。
          141      22    13     s   Ljava/lang/String;//刚好这里槽位13和槽位14的变量名都是s,而他们的影响域也都不一样。
          212       8    14     s   Ljava/lang/String;//所以上面的情况只是个例了。还是说，槽位会尽可能的少，还是有一定的个数限制？
            0     240     0  this   Lcom/willow/jvm/bytecode/HelloByteCode;
            0     240     1    aa   I
            0     240     2    bb   I
            0     240     3    cc   I
            0     240     4    dd   I
            4     236     5     a   I
            7     233     6     b   I
           20     220     7     c   I
          111     129     8   str   [Ljava/lang/String;
          115     125     9 strAppend   Ljava/lang/String;
          186      54    10    sb   Ljava/lang/StringBuilder;
      StackMapTable: number_of_entries = 6
        frame_type = 254 /* append */
          offset_delta = 60
          locals = [ int, int, int ]
        frame_type = 29 /* same */
        frame_type = 255 /* full_frame */
          offset_delta = 36
          locals = [ class com/willow/jvm/bytecode/HelloByteCode, int, int, int, int, int, int, int, class "[Ljava/lang/String;", class java/lang/String, class "[Ljava/lang/String;",
int, int ]
          stack = []
        frame_type = 248 /* chop */
          offset_delta = 41
        frame_type = 255 /* full_frame */
          offset_delta = 28
          locals = [ class com/willow/jvm/bytecode/HelloByteCode, int, int, int, int, int, int, int, class "[Ljava/lang/String;", class java/lang/String, class java/lang/StringBuilder
, class "[Ljava/lang/String;", int, int ]
          stack = []
        frame_type = 248 /* chop */
          offset_delta = 27

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=5, locals=2, args_size=1
         0: new           #15                 // class com/willow/jvm/bytecode/HelloByteCode
         3: dup
         4: invokespecial #16                 // Method "<init>":()V
         7: astore_1
         8: aload_1
         9: iconst_1
        10: iconst_2
        11: iconst_3
        12: iconst_1
        13: invokevirtual #17                 // Method compute:(IIII)I
        16: pop
        17: return
      LineNumberTable:
        line 38: 0
        line 39: 8
        line 40: 17
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      18     0  args   [Ljava/lang/String;
            8      10     1 hello   Lcom/willow/jvm/bytecode/HelloByteCode;
}
SourceFile: "HelloByteCode.java"

```

这个字节码比较长，暂时先分析到这里吧。