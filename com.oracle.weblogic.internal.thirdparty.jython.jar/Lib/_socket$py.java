import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.ContextGuard;
import org.python.core.ContextManager;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
import org.python.core.PyException;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("_socket.py")
public class _socket$py extends PyFunctionTable implements PyRunnable {
   static _socket$py self;
   static final PyCode f$0;
   static final PyCode _debug$1;
   static final PyCode DaemonThreadFactory$2;
   static final PyCode __init__$3;
   static final PyCode newThread$4;
   static final PyCode _check_threadpool_for_pending_threads$5;
   static final PyCode _shutdown_threadpool$6;
   static final PyCode error$7;
   static final PyCode herror$8;
   static final PyCode gaierror$9;
   static final PyCode timeout$10;
   static final PyCode SSLError$11;
   static final PyCode _add_exception_attrs$12;
   static final PyCode _unmapped_exception$13;
   static final PyCode java_net_socketexception_handler$14;
   static final PyCode would_block_error$15;
   static final PyCode f$16;
   static final PyCode f$17;
   static final PyCode f$18;
   static final PyCode f$19;
   static final PyCode f$20;
   static final PyCode f$21;
   static final PyCode f$22;
   static final PyCode f$23;
   static final PyCode f$24;
   static final PyCode f$25;
   static final PyCode f$26;
   static final PyCode f$27;
   static final PyCode f$28;
   static final PyCode _map_exception$29;
   static final PyCode raises_java_exception$30;
   static final PyCode handle_exception$31;
   static final PyCode _Select$32;
   static final PyCode __init__$33;
   static final PyCode _normalize_sockets$34;
   static final PyCode notify$35;
   static final PyCode __str__$36;
   static final PyCode _register_sockets$37;
   static final PyCode __call__$38;
   static final PyCode f$39;
   static final PyCode f$40;
   static final PyCode poll$41;
   static final PyCode __init__$42;
   static final PyCode notify$43;
   static final PyCode register$44;
   static final PyCode modify$45;
   static final PyCode unregister$46;
   static final PyCode _event_test$47;
   static final PyCode _handle_poll$48;
   static final PyCode poll$49;
   static final PyCode PythonInboundHandler$50;
   static final PyCode __init__$51;
   static final PyCode channelActive$52;
   static final PyCode channelRead$53;
   static final PyCode channelWritabilityChanged$54;
   static final PyCode exceptionCaught$55;
   static final PyCode ChildSocketHandler$56;
   static final PyCode __init__$57;
   static final PyCode initChannel$58;
   static final PyCode f$59;
   static final PyCode wait_for_barrier$60;
   static final PyCode _identity$61;
   static final PyCode _set_option$62;
   static final PyCode _socktuple$63;
   static final PyCode _realsocket$64;
   static final PyCode __init__$65;
   static final PyCode __repr__$66;
   static final PyCode _make_active$67;
   static final PyCode _register_selector$68;
   static final PyCode _unregister_selector$69;
   static final PyCode _notify_selectors$70;
   static final PyCode _handle_channel_future$71;
   static final PyCode setblocking$72;
   static final PyCode settimeout$73;
   static final PyCode gettimeout$74;
   static final PyCode _handle_timeout$75;
   static final PyCode bind$76;
   static final PyCode _init_client_mode$77;
   static final PyCode _connect$78;
   static final PyCode _post_connect$79;
   static final PyCode _peer_closed$80;
   static final PyCode connect$81;
   static final PyCode connect_ex$82;
   static final PyCode listen$83;
   static final PyCode accept$84;
   static final PyCode _datagram_connect$85;
   static final PyCode sendto$86;
   static final PyCode recvfrom_into$87;
   static final PyCode recv_into$88;
   static final PyCode close$89;
   static final PyCode _finish_closing$90;
   static final PyCode shutdown$91;
   static final PyCode _readable$92;
   static final PyCode _pending$93;
   static final PyCode _writable$94;
   static final PyCode _verify_channel$95;
   static final PyCode send$96;
   static final PyCode _get_incoming_msg$97;
   static final PyCode _get_message$98;
   static final PyCode recv$99;
   static final PyCode recvfrom$100;
   static final PyCode fileno$101;
   static final PyCode setsockopt$102;
   static final PyCode getsockopt$103;
   static final PyCode getsockname$104;
   static final PyCode getpeername$105;
   static final PyCode _closedsocket$106;
   static final PyCode close$107;
   static final PyCode _dummy$108;
   static final PyCode _socketobject$109;
   static final PyCode __init__$110;
   static final PyCode close$111;
   static final PyCode fileno$112;
   static final PyCode accept$113;
   static final PyCode dup$114;
   static final PyCode makefile$115;
   static final PyCode f$116;
   static final PyCode f$117;
   static final PyCode f$118;
   static final PyCode meth$119;
   static final PyCode ChildSocket$120;
   static final PyCode __init__$121;
   static final PyCode _make_active$122;
   static final PyCode send$123;
   static final PyCode recv$124;
   static final PyCode recvfrom$125;
   static final PyCode setblocking$126;
   static final PyCode close$127;
   static final PyCode shutdown$128;
   static final PyCode __del__$129;
   static final PyCode select$130;
   static final PyCode create_connection$131;
   static final PyCode _calctimeoutvalue$132;
   static final PyCode getdefaulttimeout$133;
   static final PyCode setdefaulttimeout$134;
   static final PyCode _ip_address_t$135;
   static final PyCode _ipv4_address_t$136;
   static final PyCode __new__$137;
   static final PyCode _ipv6_address_t$138;
   static final PyCode __new__$139;
   static final PyCode _get_jsockaddr$140;
   static final PyCode _get_jsockaddr2$141;
   static final PyCode _is_ip_address$142;
   static final PyCode is_ipv4_address$143;
   static final PyCode is_ipv6_address$144;
   static final PyCode is_ip_address$145;
   static final PyCode _use_ipv4_addresses_only$146;
   static final PyCode _getaddrinfo_get_host$147;
   static final PyCode _getaddrinfo_get_port$148;
   static final PyCode getaddrinfo$149;
   static final PyCode f$150;
   static final PyCode f$151;
   static final PyCode f$152;
   static final PyCode htons$153;
   static final PyCode htonl$154;
   static final PyCode ntohs$155;
   static final PyCode ntohl$156;
   static final PyCode inet_pton$157;
   static final PyCode inet_ntop$158;
   static final PyCode inet_aton$159;
   static final PyCode inet_ntoa$160;
   static final PyCode _gethostbyaddr$161;
   static final PyCode getfqdn$162;
   static final PyCode gethostname$163;
   static final PyCode gethostbyname$164;
   static final PyCode gethostbyname_ex$165;
   static final PyCode gethostbyaddr$166;
   static final PyCode getservbyname$167;
   static final PyCode getservbyport$168;
   static final PyCode getprotobyname$169;
   static final PyCode getservbyname$170;
   static final PyCode getservbyport$171;
   static final PyCode getprotobyname$172;
   static final PyCode _getnameinfo_get_host$173;
   static final PyCode _getnameinfo_get_port$174;
   static final PyCode getnameinfo$175;
   static final PyCode _fileobject$176;
   static final PyCode __init__$177;
   static final PyCode _getclosed$178;
   static final PyCode close$179;
   static final PyCode __del__$180;
   static final PyCode flush$181;
   static final PyCode fileno$182;
   static final PyCode write$183;
   static final PyCode writelines$184;
   static final PyCode read$185;
   static final PyCode readline$186;
   static final PyCode readlines$187;
   static final PyCode __iter__$188;
   static final PyCode next$189;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("array", var1, -1);
      var1.setlocal("array", var3);
      var3 = null;
      var1.setline(2);
      var3 = imp.importOne("encodings.idna", var1, -1);
      var1.setlocal("encodings", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("errno", var1, -1);
      var1.setlocal("errno", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("jarray", var1, -1);
      var1.setlocal("jarray", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("logging", var1, -1);
      var1.setlocal("logging", var3);
      var3 = null;
      var1.setline(6);
      var3 = imp.importOne("numbers", var1, -1);
      var1.setlocal("numbers", var3);
      var3 = null;
      var1.setline(7);
      var3 = imp.importOne("pprint", var1, -1);
      var1.setlocal("pprint", var3);
      var3 = null;
      var1.setline(8);
      var3 = imp.importOne("struct", var1, -1);
      var1.setlocal("struct", var3);
      var3 = null;
      var1.setline(9);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(10);
      var3 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var3);
      var3 = null;
      var1.setline(11);
      var3 = imp.importOne("_google_ipaddr_r234", var1, -1);
      var1.setlocal("_google_ipaddr_r234", var3);
      var3 = null;
      var1.setline(12);
      String[] var8 = new String[]{"namedtuple", "Iterable"};
      PyObject[] var9 = imp.importFrom("collections", var8, var1, -1);
      PyObject var4 = var9[0];
      var1.setlocal("namedtuple", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("Iterable", var4);
      var4 = null;
      var1.setline(13);
      var8 = new String[]{"contextmanager"};
      var9 = imp.importFrom("contextlib", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("contextmanager", var4);
      var4 = null;
      var1.setline(14);
      var8 = new String[]{"partial", "wraps"};
      var9 = imp.importFrom("functools", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("partial", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("wraps", var4);
      var4 = null;
      var1.setline(15);
      var8 = new String[]{"chain"};
      var9 = imp.importFrom("itertools", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("chain", var4);
      var4 = null;
      var1.setline(16);
      var8 = new String[]{"MapMaker", "dict_builder"};
      var9 = imp.importFrom("jythonlib", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("MapMaker", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("dict_builder", var4);
      var4 = null;
      var1.setline(17);
      var8 = new String[]{"Number"};
      var9 = imp.importFrom("numbers", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("Number", var4);
      var4 = null;
      var1.setline(18);
      var8 = new String[]{"StringIO"};
      var9 = imp.importFrom("StringIO", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("StringIO", var4);
      var4 = null;
      var1.setline(19);
      var8 = new String[]{"Condition", "Lock"};
      var9 = imp.importFrom("threading", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("Condition", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("Lock", var4);
      var4 = null;
      var1.setline(20);
      var8 = new String[]{"MethodType", "NoneType"};
      var9 = imp.importFrom("types", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("MethodType", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("NoneType", var4);
      var4 = null;
      var1.setline(22);
      var3 = imp.importOne("java", var1, -1);
      var1.setlocal("java", var3);
      var3 = null;
      var1.setline(23);
      var8 = new String[]{"IOException", "InterruptedIOException"};
      var9 = imp.importFrom("java.io", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("IOException", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("InterruptedIOException", var4);
      var4 = null;
      var1.setline(24);
      var8 = new String[]{"Thread", "ArrayIndexOutOfBoundsException", "IllegalStateException"};
      var9 = imp.importFrom("java.lang", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("Thread", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("ArrayIndexOutOfBoundsException", var4);
      var4 = null;
      var4 = var9[2];
      var1.setlocal("IllegalStateException", var4);
      var4 = null;
      var1.setline(25);
      var8 = new String[]{"InetAddress", "InetSocketAddress"};
      var9 = imp.importFrom("java.net", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("InetAddress", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("InetSocketAddress", var4);
      var4 = null;
      var1.setline(26);
      var8 = new String[]{"ClosedChannelException"};
      var9 = imp.importFrom("java.nio.channels", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("ClosedChannelException", var4);
      var4 = null;
      var1.setline(27);
      var8 = new String[]{"CertificateException"};
      var9 = imp.importFrom("java.security.cert", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("CertificateException", var4);
      var4 = null;
      var1.setline(28);
      var8 = new String[]{"NoSuchElementException"};
      var9 = imp.importFrom("java.util", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("NoSuchElementException", var4);
      var4 = null;
      var1.setline(29);
      var8 = new String[]{"ArrayBlockingQueue", "CopyOnWriteArrayList", "CountDownLatch", "LinkedBlockingQueue", "ExecutionException", "RejectedExecutionException", "ThreadFactory", "TimeoutException", "TimeUnit"};
      var9 = imp.importFrom("java.util.concurrent", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("ArrayBlockingQueue", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("CopyOnWriteArrayList", var4);
      var4 = null;
      var4 = var9[2];
      var1.setlocal("CountDownLatch", var4);
      var4 = null;
      var4 = var9[3];
      var1.setlocal("LinkedBlockingQueue", var4);
      var4 = null;
      var4 = var9[4];
      var1.setlocal("ExecutionException", var4);
      var4 = null;
      var4 = var9[5];
      var1.setlocal("RejectedExecutionException", var4);
      var4 = null;
      var4 = var9[6];
      var1.setlocal("ThreadFactory", var4);
      var4 = null;
      var4 = var9[7];
      var1.setlocal("TimeoutException", var4);
      var4 = null;
      var4 = var9[8];
      var1.setlocal("TimeUnit", var4);
      var4 = null;
      var1.setline(33);
      var8 = new String[]{"AtomicBoolean", "AtomicLong"};
      var9 = imp.importFrom("java.util.concurrent.atomic", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("AtomicBoolean", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("AtomicLong", var4);
      var4 = null;
      var1.setline(34);
      var8 = new String[]{"SSLPeerUnverifiedException", "SSLException", "SSLHandshakeException"};
      var9 = imp.importFrom("javax.net.ssl", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("SSLPeerUnverifiedException", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("SSLException", var4);
      var4 = null;
      var4 = var9[2];
      var1.setlocal("SSLHandshakeException", var4);
      var4 = null;

      PyObject var5;
      PyException var10;
      String[] var11;
      PyObject[] var13;
      try {
         var1.setline(38);
         var8 = new String[]{"Bootstrap", "ChannelFactory", "ServerBootstrap"};
         var9 = imp.importFrom("org.python.netty.bootstrap", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("Bootstrap", var4);
         var4 = null;
         var4 = var9[1];
         var1.setlocal("ChannelFactory", var4);
         var4 = null;
         var4 = var9[2];
         var1.setlocal("ServerBootstrap", var4);
         var4 = null;
         var1.setline(39);
         var8 = new String[]{"PooledByteBufAllocator", "Unpooled"};
         var9 = imp.importFrom("org.python.netty.buffer", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("PooledByteBufAllocator", var4);
         var4 = null;
         var4 = var9[1];
         var1.setlocal("Unpooled", var4);
         var4 = null;
         var1.setline(40);
         var8 = new String[]{"ChannelException", "ChannelInboundHandlerAdapter", "ChannelInitializer", "ChannelOption"};
         var9 = imp.importFrom("org.python.netty.channel", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("NettyChannelException", var4);
         var4 = null;
         var4 = var9[1];
         var1.setlocal("ChannelInboundHandlerAdapter", var4);
         var4 = null;
         var4 = var9[2];
         var1.setlocal("ChannelInitializer", var4);
         var4 = null;
         var4 = var9[3];
         var1.setlocal("ChannelOption", var4);
         var4 = null;
         var1.setline(41);
         var8 = new String[]{"NioEventLoopGroup"};
         var9 = imp.importFrom("org.python.netty.channel.nio", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("NioEventLoopGroup", var4);
         var4 = null;
         var1.setline(42);
         var8 = new String[]{"DatagramPacket"};
         var9 = imp.importFrom("org.python.netty.channel.socket", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("DatagramPacket", var4);
         var4 = null;
         var1.setline(43);
         var8 = new String[]{"NioDatagramChannel", "NioSocketChannel", "NioServerSocketChannel"};
         var9 = imp.importFrom("org.python.netty.channel.socket.nio", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("NioDatagramChannel", var4);
         var4 = null;
         var4 = var9[1];
         var1.setlocal("NioSocketChannel", var4);
         var4 = null;
         var4 = var9[2];
         var1.setlocal("NioServerSocketChannel", var4);
         var4 = null;
         var1.setline(44);
         var8 = new String[]{"NotSslRecordException"};
         var9 = imp.importFrom("org.python.netty.handler.ssl", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("NotSslRecordException", var4);
         var4 = null;
      } catch (Throwable var7) {
         var10 = Py.setException(var7, var1);
         if (!var10.match(var1.getname("ImportError"))) {
            throw var10;
         }

         var1.setline(48);
         var11 = new String[]{"Bootstrap", "ChannelFactory", "ServerBootstrap"};
         var13 = imp.importFrom("io.netty.bootstrap", var11, var1, -1);
         var5 = var13[0];
         var1.setlocal("Bootstrap", var5);
         var5 = null;
         var5 = var13[1];
         var1.setlocal("ChannelFactory", var5);
         var5 = null;
         var5 = var13[2];
         var1.setlocal("ServerBootstrap", var5);
         var5 = null;
         var1.setline(49);
         var11 = new String[]{"PooledByteBufAllocator", "Unpooled"};
         var13 = imp.importFrom("io.netty.buffer", var11, var1, -1);
         var5 = var13[0];
         var1.setlocal("PooledByteBufAllocator", var5);
         var5 = null;
         var5 = var13[1];
         var1.setlocal("Unpooled", var5);
         var5 = null;
         var1.setline(50);
         var11 = new String[]{"ChannelException", "ChannelInboundHandlerAdapter", "ChannelInitializer", "ChannelOption"};
         var13 = imp.importFrom("io.netty.channel", var11, var1, -1);
         var5 = var13[0];
         var1.setlocal("NettyChannelException", var5);
         var5 = null;
         var5 = var13[1];
         var1.setlocal("ChannelInboundHandlerAdapter", var5);
         var5 = null;
         var5 = var13[2];
         var1.setlocal("ChannelInitializer", var5);
         var5 = null;
         var5 = var13[3];
         var1.setlocal("ChannelOption", var5);
         var5 = null;
         var1.setline(51);
         var11 = new String[]{"NioEventLoopGroup"};
         var13 = imp.importFrom("io.netty.channel.nio", var11, var1, -1);
         var5 = var13[0];
         var1.setlocal("NioEventLoopGroup", var5);
         var5 = null;
         var1.setline(52);
         var11 = new String[]{"DatagramPacket"};
         var13 = imp.importFrom("io.netty.channel.socket", var11, var1, -1);
         var5 = var13[0];
         var1.setlocal("DatagramPacket", var5);
         var5 = null;
         var1.setline(53);
         var11 = new String[]{"NioDatagramChannel", "NioSocketChannel", "NioServerSocketChannel"};
         var13 = imp.importFrom("io.netty.channel.socket.nio", var11, var1, -1);
         var5 = var13[0];
         var1.setlocal("NioDatagramChannel", var5);
         var5 = null;
         var5 = var13[1];
         var1.setlocal("NioSocketChannel", var5);
         var5 = null;
         var5 = var13[2];
         var1.setlocal("NioServerSocketChannel", var5);
         var5 = null;
         var1.setline(54);
         var11 = new String[]{"NotSslRecordException"};
         var13 = imp.importFrom("io.netty.handler.ssl", var11, var1, -1);
         var5 = var13[0];
         var1.setlocal("NotSslRecordException", var5);
         var5 = null;
      }

      var1.setline(56);
      var3 = var1.getname("logging").__getattr__("getLogger").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_socket"));
      var1.setlocal("log", var3);
      var3 = null;
      var1.setline(57);
      PyObject var10000 = var1.getname("log").__getattr__("setLevel");
      var9 = new PyObject[]{var1.getname("logging").__getattr__("WARNING")};
      var11 = new String[]{"level"};
      var10000.__call__(var2, var9, var11);
      var3 = null;
      var1.setline(60);
      var9 = Py.EmptyObjects;
      PyFunction var12 = new PyFunction(var1.f_globals, var9, _debug$1, (PyObject)null);
      var1.setlocal("_debug", var12);
      var3 = null;
      var1.setline(73);
      var3 = var1.getname("True");
      var1.setlocal("has_ipv6", var3);
      var3 = null;
      var1.setline(74);
      var3 = var1.getname("object").__call__(var2);
      var1.setlocal("_GLOBAL_DEFAULT_TIMEOUT", var3);
      var3 = null;
      var1.setline(75);
      var3 = var1.getname("InetSocketAddress").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal("_EPHEMERAL_ADDRESS", var3);
      var3 = null;
      var1.setline(76);
      var3 = var1.getname("object").__call__(var2);
      var1.setlocal("_BOUND_EPHEMERAL_ADDRESS", var3);
      var3 = null;
      var1.setline(82);
      PyInteger var14 = Py.newInteger(0);
      var1.setlocal("SHUT_RD", var14);
      var3 = null;
      var1.setline(83);
      var14 = Py.newInteger(1);
      var1.setlocal("SHUT_WR", var14);
      var3 = null;
      var1.setline(84);
      var14 = Py.newInteger(2);
      var1.setlocal("SHUT_RDWR", var14);
      var3 = null;
      var1.setline(86);
      var14 = Py.newInteger(0);
      var1.setlocal("AF_UNSPEC", var14);
      var3 = null;
      var1.setline(87);
      var14 = Py.newInteger(2);
      var1.setlocal("AF_INET", var14);
      var3 = null;
      var1.setline(88);
      var14 = Py.newInteger(23);
      var1.setlocal("AF_INET6", var14);
      var3 = null;
      var1.setline(90);
      var14 = Py.newInteger(1);
      var1.setlocal("AI_PASSIVE", var14);
      var3 = null;
      var1.setline(91);
      var14 = Py.newInteger(2);
      var1.setlocal("AI_CANONNAME", var14);
      var3 = null;
      var1.setline(92);
      var14 = Py.newInteger(4);
      var1.setlocal("AI_NUMERICHOST", var14);
      var3 = null;
      var1.setline(93);
      var14 = Py.newInteger(8);
      var1.setlocal("AI_V4MAPPED", var14);
      var3 = null;
      var1.setline(94);
      var14 = Py.newInteger(16);
      var1.setlocal("AI_ALL", var14);
      var3 = null;
      var1.setline(95);
      var14 = Py.newInteger(32);
      var1.setlocal("AI_ADDRCONFIG", var14);
      var3 = null;
      var1.setline(96);
      var14 = Py.newInteger(1024);
      var1.setlocal("AI_NUMERICSERV", var14);
      var3 = null;
      var1.setline(98);
      var14 = Py.newInteger(-2);
      var1.setlocal("EAI_NONAME", var14);
      var3 = null;
      var1.setline(99);
      var14 = Py.newInteger(-8);
      var1.setlocal("EAI_SERVICE", var14);
      var3 = null;
      var1.setline(100);
      var14 = Py.newInteger(-9);
      var1.setlocal("EAI_ADDRFAMILY", var14);
      var3 = null;
      var1.setline(102);
      var14 = Py.newInteger(1);
      var1.setlocal("NI_NUMERICHOST", var14);
      var3 = null;
      var1.setline(103);
      var14 = Py.newInteger(2);
      var1.setlocal("NI_NUMERICSERV", var14);
      var3 = null;
      var1.setline(104);
      var14 = Py.newInteger(4);
      var1.setlocal("NI_NOFQDN", var14);
      var3 = null;
      var1.setline(105);
      var14 = Py.newInteger(8);
      var1.setlocal("NI_NAMEREQD", var14);
      var3 = null;
      var1.setline(106);
      var14 = Py.newInteger(16);
      var1.setlocal("NI_DGRAM", var14);
      var3 = null;
      var1.setline(107);
      var14 = Py.newInteger(32);
      var1.setlocal("NI_MAXSERV", var14);
      var3 = null;
      var1.setline(108);
      var14 = Py.newInteger(64);
      var1.setlocal("NI_IDN", var14);
      var3 = null;
      var1.setline(109);
      var14 = Py.newInteger(128);
      var1.setlocal("NI_IDN_ALLOW_UNASSIGNED", var14);
      var3 = null;
      var1.setline(110);
      var14 = Py.newInteger(256);
      var1.setlocal("NI_IDN_USE_STD3_ASCII_RULES", var14);
      var3 = null;
      var1.setline(111);
      var14 = Py.newInteger(1025);
      var1.setlocal("NI_MAXHOST", var14);
      var3 = null;
      var1.setline(113);
      var14 = Py.newInteger(1);
      var1.setlocal("SOCK_DGRAM", var14);
      var3 = null;
      var1.setline(114);
      var14 = Py.newInteger(2);
      var1.setlocal("SOCK_STREAM", var14);
      var3 = null;
      var1.setline(115);
      var14 = Py.newInteger(3);
      var1.setlocal("SOCK_RAW", var14);
      var3 = null;
      var1.setline(116);
      var14 = Py.newInteger(4);
      var1.setlocal("SOCK_RDM", var14);
      var3 = null;
      var1.setline(117);
      var14 = Py.newInteger(5);
      var1.setlocal("SOCK_SEQPACKET", var14);
      var3 = null;
      var1.setline(119);
      var14 = Py.newInteger(65535);
      var1.setlocal("SOL_SOCKET", var14);
      var3 = null;
      var1.setline(121);
      var14 = Py.newInteger(51);
      var1.setlocal("IPPROTO_AH", var14);
      var3 = null;
      var1.setline(122);
      var14 = Py.newInteger(60);
      var1.setlocal("IPPROTO_DSTOPTS", var14);
      var3 = null;
      var1.setline(123);
      var14 = Py.newInteger(50);
      var1.setlocal("IPPROTO_ESP", var14);
      var3 = null;
      var1.setline(124);
      var14 = Py.newInteger(44);
      var1.setlocal("IPPROTO_FRAGMENT", var14);
      var3 = null;
      var1.setline(125);
      var14 = Py.newInteger(3);
      var1.setlocal("IPPROTO_GGP", var14);
      var3 = null;
      var1.setline(126);
      var14 = Py.newInteger(0);
      var1.setlocal("IPPROTO_HOPOPTS", var14);
      var3 = null;
      var1.setline(127);
      var14 = Py.newInteger(1);
      var1.setlocal("IPPROTO_ICMP", var14);
      var3 = null;
      var1.setline(128);
      var14 = Py.newInteger(58);
      var1.setlocal("IPPROTO_ICMPV6", var14);
      var3 = null;
      var1.setline(129);
      var14 = Py.newInteger(22);
      var1.setlocal("IPPROTO_IDP", var14);
      var3 = null;
      var1.setline(130);
      var14 = Py.newInteger(2);
      var1.setlocal("IPPROTO_IGMP", var14);
      var3 = null;
      var1.setline(131);
      var14 = Py.newInteger(0);
      var1.setlocal("IPPROTO_IP", var14);
      var3 = null;
      var1.setline(132);
      var14 = Py.newInteger(4);
      var1.setlocal("IPPROTO_IPV4", var14);
      var3 = null;
      var1.setline(133);
      var14 = Py.newInteger(41);
      var1.setlocal("IPPROTO_IPV6", var14);
      var3 = null;
      var1.setline(134);
      var14 = Py.newInteger(256);
      var1.setlocal("IPPROTO_MAX", var14);
      var3 = null;
      var1.setline(135);
      var14 = Py.newInteger(77);
      var1.setlocal("IPPROTO_ND", var14);
      var3 = null;
      var1.setline(136);
      var14 = Py.newInteger(59);
      var1.setlocal("IPPROTO_NONE", var14);
      var3 = null;
      var1.setline(137);
      var14 = Py.newInteger(12);
      var1.setlocal("IPPROTO_PUP", var14);
      var3 = null;
      var1.setline(138);
      var14 = Py.newInteger(255);
      var1.setlocal("IPPROTO_RAW", var14);
      var3 = null;
      var1.setline(139);
      var14 = Py.newInteger(43);
      var1.setlocal("IPPROTO_ROUTING", var14);
      var3 = null;
      var1.setline(140);
      var14 = Py.newInteger(6);
      var1.setlocal("SOL_TCP", var14);
      var1.setlocal("IPPROTO_TCP", var14);
      var1.setline(141);
      var14 = Py.newInteger(17);
      var1.setlocal("IPPROTO_UDP", var14);
      var3 = null;
      var1.setline(143);
      var14 = Py.newInteger(1);
      var1.setlocal("SO_ACCEPTCONN", var14);
      var3 = null;
      var1.setline(144);
      var14 = Py.newInteger(2);
      var1.setlocal("SO_BROADCAST", var14);
      var3 = null;
      var1.setline(145);
      var14 = Py.newInteger(4);
      var1.setlocal("SO_ERROR", var14);
      var3 = null;
      var1.setline(146);
      var14 = Py.newInteger(8);
      var1.setlocal("SO_KEEPALIVE", var14);
      var3 = null;
      var1.setline(147);
      var14 = Py.newInteger(16);
      var1.setlocal("SO_LINGER", var14);
      var3 = null;
      var1.setline(148);
      var14 = Py.newInteger(32);
      var1.setlocal("SO_OOBINLINE", var14);
      var3 = null;
      var1.setline(149);
      var14 = Py.newInteger(64);
      var1.setlocal("SO_RCVBUF", var14);
      var3 = null;
      var1.setline(150);
      var14 = Py.newInteger(128);
      var1.setlocal("SO_REUSEADDR", var14);
      var3 = null;
      var1.setline(151);
      var14 = Py.newInteger(256);
      var1.setlocal("SO_SNDBUF", var14);
      var3 = null;
      var1.setline(152);
      var14 = Py.newInteger(512);
      var1.setlocal("SO_TIMEOUT", var14);
      var3 = null;
      var1.setline(153);
      var14 = Py.newInteger(1024);
      var1.setlocal("SO_TYPE", var14);
      var3 = null;
      var1.setline(159);
      var14 = Py.newInteger(-1);
      var1.setlocal("SO_DEBUG", var14);
      var3 = null;
      var1.setline(160);
      var14 = Py.newInteger(-1);
      var1.setlocal("SO_DONTROUTE", var14);
      var3 = null;
      var1.setline(161);
      var14 = Py.newInteger(-16);
      var1.setlocal("SO_RCVLOWAT", var14);
      var3 = null;
      var1.setline(162);
      var14 = Py.newInteger(-32);
      var1.setlocal("SO_RCVTIMEO", var14);
      var3 = null;
      var1.setline(163);
      var14 = Py.newInteger(-64);
      var1.setlocal("SO_REUSEPORT", var14);
      var3 = null;
      var1.setline(164);
      var14 = Py.newInteger(-128);
      var1.setlocal("SO_SNDLOWAT", var14);
      var3 = null;
      var1.setline(165);
      var14 = Py.newInteger(-256);
      var1.setlocal("SO_SNDTIMEO", var14);
      var3 = null;
      var1.setline(166);
      var14 = Py.newInteger(-512);
      var1.setlocal("SO_USELOOPBACK", var14);
      var3 = null;
      var1.setline(168);
      var14 = Py.newInteger(2048);
      var1.setlocal("TCP_NODELAY", var14);
      var3 = null;
      var1.setline(170);
      PyString var16 = PyString.fromInterned("0.0.0.0");
      var1.setlocal("INADDR_ANY", var16);
      var3 = null;
      var1.setline(171);
      var16 = PyString.fromInterned("255.255.255.255");
      var1.setlocal("INADDR_BROADCAST", var16);
      var3 = null;
      var1.setline(173);
      var16 = PyString.fromInterned("::");
      var1.setlocal("IN6ADDR_ANY_INIT", var16);
      var3 = null;
      var1.setline(175);
      var14 = Py.newInteger(1);
      var1.setlocal("POLLIN", var14);
      var3 = null;
      var1.setline(176);
      var14 = Py.newInteger(2);
      var1.setlocal("POLLOUT", var14);
      var3 = null;
      var1.setline(177);
      var14 = Py.newInteger(4);
      var1.setlocal("POLLPRI", var14);
      var3 = null;
      var1.setline(178);
      var14 = Py.newInteger(8);
      var1.setlocal("POLLERR", var14);
      var3 = null;
      var1.setline(179);
      var14 = Py.newInteger(16);
      var1.setlocal("POLLHUP", var14);
      var3 = null;
      var1.setline(180);
      var14 = Py.newInteger(32);
      var1.setlocal("POLLNVAL", var14);
      var3 = null;
      var1.setline(188);
      var14 = Py.newInteger(1000000000);
      var1.setlocal("_TO_NANOSECONDS", var14);
      var3 = null;
      var1.setline(190);
      var3 = var1.getname("object").__call__(var2);
      var1.setlocal("_PEER_CLOSED", var3);
      var3 = null;
      var1.setline(196);
      var14 = Py.newInteger(10);
      var1.setlocal("_NUM_THREADS", var14);
      var3 = null;
      var1.setline(203);
      var9 = new PyObject[]{var1.getname("ThreadFactory")};
      var4 = Py.makeClass("DaemonThreadFactory", var9, DaemonThreadFactory$2);
      var1.setlocal("DaemonThreadFactory", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(217);
      var3 = var1.getname("NioEventLoopGroup").__call__(var2, var1.getname("_NUM_THREADS"), var1.getname("DaemonThreadFactory").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Jython-Netty-Client-%s")));
      var1.setlocal("NIO_GROUP", var3);
      var3 = null;
      var1.setline(220);
      var9 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var9, _check_threadpool_for_pending_threads$5, (PyObject)null);
      var1.setlocal("_check_threadpool_for_pending_threads", var12);
      var3 = null;
      var1.setline(230);
      var9 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var9, _shutdown_threadpool$6, (PyObject)null);
      var1.setlocal("_shutdown_threadpool", var12);
      var3 = null;
      var1.setline(243);
      var1.getname("sys").__getattr__("registerCloser").__call__(var2, var1.getname("_shutdown_threadpool"));
      var1.setline(249);
      var9 = new PyObject[]{var1.getname("IOError")};
      var4 = Py.makeClass("error", var9, error$7);
      var1.setlocal("error", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(250);
      var9 = new PyObject[]{var1.getname("error")};
      var4 = Py.makeClass("herror", var9, herror$8);
      var1.setlocal("herror", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(251);
      var9 = new PyObject[]{var1.getname("error")};
      var4 = Py.makeClass("gaierror", var9, gaierror$9);
      var1.setlocal("gaierror", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(252);
      var9 = new PyObject[]{var1.getname("error")};
      var4 = Py.makeClass("timeout", var9, timeout$10);
      var1.setlocal("timeout", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(253);
      var9 = new PyObject[]{var1.getname("error")};
      var4 = Py.makeClass("SSLError", var9, SSLError$11);
      var1.setlocal("SSLError", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(255);
      var14 = Py.newInteger(1);
      var1.setlocal("SSL_ERROR_SSL", var14);
      var3 = null;
      var1.setline(256);
      var14 = Py.newInteger(2);
      var1.setlocal("SSL_ERROR_WANT_READ", var14);
      var3 = null;
      var1.setline(257);
      var14 = Py.newInteger(3);
      var1.setlocal("SSL_ERROR_WANT_WRITE", var14);
      var3 = null;
      var1.setline(258);
      var14 = Py.newInteger(4);
      var1.setlocal("SSL_ERROR_WANT_X509_LOOKUP", var14);
      var3 = null;
      var1.setline(259);
      var14 = Py.newInteger(5);
      var1.setlocal("SSL_ERROR_SYSCALL", var14);
      var3 = null;
      var1.setline(260);
      var14 = Py.newInteger(6);
      var1.setlocal("SSL_ERROR_ZERO_RETURN", var14);
      var3 = null;
      var1.setline(261);
      var14 = Py.newInteger(7);
      var1.setlocal("SSL_ERROR_WANT_CONNECT", var14);
      var3 = null;
      var1.setline(262);
      var14 = Py.newInteger(8);
      var1.setlocal("SSL_ERROR_EOF", var14);
      var3 = null;
      var1.setline(263);
      var14 = Py.newInteger(9);
      var1.setlocal("SSL_ERROR_INVALID_ERROR_CODE", var14);
      var3 = null;
      var1.setline(264);
      var14 = Py.newInteger(10);
      var1.setlocal("SSL_UNKNOWN_PROTOCOL", var14);
      var3 = null;
      var1.setline(267);
      var9 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var9, _add_exception_attrs$12, (PyObject)null);
      var1.setlocal("_add_exception_attrs", var12);
      var3 = null;
      var1.setline(273);
      var9 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var9, _unmapped_exception$13, (PyObject)null);
      var1.setlocal("_unmapped_exception", var12);
      var3 = null;
      var1.setline(277);
      var9 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var9, java_net_socketexception_handler$14, (PyObject)null);
      var1.setlocal("java_net_socketexception_handler", var12);
      var3 = null;
      var1.setline(287);
      var9 = new PyObject[]{var1.getname("None")};
      var12 = new PyFunction(var1.f_globals, var9, would_block_error$15, (PyObject)null);
      var1.setlocal("would_block_error", var12);
      var3 = null;
      var1.setline(292);
      PyObject[] var10002 = new PyObject[]{var1.getname("IOException"), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null};
      var1.setline(296);
      var9 = Py.EmptyObjects;
      var10002[1] = new PyFunction(var1.f_globals, var9, f$16);
      var10002[2] = var1.getname("InterruptedIOException");
      var1.setline(297);
      var9 = Py.EmptyObjects;
      var10002[3] = new PyFunction(var1.f_globals, var9, f$17);
      var10002[4] = var1.getname("IllegalStateException");
      var1.setline(298);
      var9 = Py.EmptyObjects;
      var10002[5] = new PyFunction(var1.f_globals, var9, f$18);
      var10002[6] = var1.getname("java").__getattr__("net").__getattr__("BindException");
      var1.setline(300);
      var9 = Py.EmptyObjects;
      var10002[7] = new PyFunction(var1.f_globals, var9, f$19);
      var10002[8] = var1.getname("java").__getattr__("net").__getattr__("ConnectException");
      var1.setline(301);
      var9 = Py.EmptyObjects;
      var10002[9] = new PyFunction(var1.f_globals, var9, f$20);
      var10002[10] = var1.getname("java").__getattr__("net").__getattr__("NoRouteToHostException");
      var1.setline(302);
      var9 = Py.EmptyObjects;
      var10002[11] = new PyFunction(var1.f_globals, var9, f$21);
      var10002[12] = var1.getname("java").__getattr__("net").__getattr__("PortUnreachableException");
      var10002[13] = var1.getname("None");
      var10002[14] = var1.getname("java").__getattr__("net").__getattr__("ProtocolException");
      var10002[15] = var1.getname("None");
      var10002[16] = var1.getname("java").__getattr__("net").__getattr__("SocketException");
      var10002[17] = var1.getname("java_net_socketexception_handler");
      var10002[18] = var1.getname("java").__getattr__("net").__getattr__("SocketTimeoutException");
      var1.setline(306);
      var9 = Py.EmptyObjects;
      var10002[19] = new PyFunction(var1.f_globals, var9, f$22);
      var10002[20] = var1.getname("java").__getattr__("net").__getattr__("UnknownHostException");
      var1.setline(307);
      var9 = Py.EmptyObjects;
      var10002[21] = new PyFunction(var1.f_globals, var9, f$23);
      var10002[22] = var1.getname("java").__getattr__("nio").__getattr__("channels").__getattr__("AlreadyConnectedException");
      var1.setline(309);
      var9 = Py.EmptyObjects;
      var10002[23] = new PyFunction(var1.f_globals, var9, f$24);
      var10002[24] = var1.getname("java").__getattr__("nio").__getattr__("channels").__getattr__("AsynchronousCloseException");
      var10002[25] = var1.getname("None");
      var10002[26] = var1.getname("java").__getattr__("nio").__getattr__("channels").__getattr__("CancelledKeyException");
      var10002[27] = var1.getname("None");
      var10002[28] = var1.getname("java").__getattr__("nio").__getattr__("channels").__getattr__("ClosedByInterruptException");
      var10002[29] = var1.getname("None");
      var10002[30] = var1.getname("java").__getattr__("nio").__getattr__("channels").__getattr__("ClosedChannelException");
      var1.setline(313);
      var9 = Py.EmptyObjects;
      var10002[31] = new PyFunction(var1.f_globals, var9, f$25);
      var10002[32] = var1.getname("java").__getattr__("nio").__getattr__("channels").__getattr__("ClosedSelectorException");
      var10002[33] = var1.getname("None");
      var10002[34] = var1.getname("java").__getattr__("nio").__getattr__("channels").__getattr__("ConnectionPendingException");
      var10002[35] = var1.getname("None");
      var10002[36] = var1.getname("java").__getattr__("nio").__getattr__("channels").__getattr__("IllegalBlockingModeException");
      var10002[37] = var1.getname("None");
      var10002[38] = var1.getname("java").__getattr__("nio").__getattr__("channels").__getattr__("IllegalSelectorException");
      var10002[39] = var1.getname("None");
      var10002[40] = var1.getname("java").__getattr__("nio").__getattr__("channels").__getattr__("NoConnectionPendingException");
      var10002[41] = var1.getname("None");
      var10002[42] = var1.getname("java").__getattr__("nio").__getattr__("channels").__getattr__("NonReadableChannelException");
      var10002[43] = var1.getname("None");
      var10002[44] = var1.getname("java").__getattr__("nio").__getattr__("channels").__getattr__("NonWritableChannelException");
      var10002[45] = var1.getname("None");
      var10002[46] = var1.getname("java").__getattr__("nio").__getattr__("channels").__getattr__("NotYetBoundException");
      var10002[47] = var1.getname("None");
      var10002[48] = var1.getname("java").__getattr__("nio").__getattr__("channels").__getattr__("NotYetConnectedException");
      var10002[49] = var1.getname("None");
      var10002[50] = var1.getname("java").__getattr__("nio").__getattr__("channels").__getattr__("UnresolvedAddressException");
      var1.setline(323);
      var9 = Py.EmptyObjects;
      var10002[51] = new PyFunction(var1.f_globals, var9, f$26);
      var10002[52] = var1.getname("java").__getattr__("nio").__getattr__("channels").__getattr__("UnsupportedAddressTypeException");
      var10002[53] = var1.getname("None");
      var10002[54] = var1.getname("SSLPeerUnverifiedException");
      var1.setline(326);
      var9 = Py.EmptyObjects;
      var10002[55] = new PyFunction(var1.f_globals, var9, f$27);
      var10002[56] = var1.getname("NotSslRecordException");
      var1.setline(332);
      var9 = Py.EmptyObjects;
      var10002[57] = new PyFunction(var1.f_globals, var9, f$28);
      PyDictionary var17 = new PyDictionary(var10002);
      var1.setlocal("_exception_map", var17);
      var3 = null;
      var1.setline(336);
      var9 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var9, _map_exception$29, (PyObject)null);
      var1.setlocal("_map_exception", var12);
      var3 = null;
      var1.setline(372);
      var9 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var9, raises_java_exception$30, PyString.fromInterned("Maps java socket exceptions to the equivalent python exception.\n    Also sets _last_error on socket objects so as to support SO_ERROR.\n    "));
      var1.setlocal("raises_java_exception", var12);
      var3 = null;
      var1.setline(398);
      var9 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("_Select", var9, _Select$32);
      var1.setlocal("_Select", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(461);
      var3 = var1.getname("namedtuple").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_PollNotification"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("sock"), PyString.fromInterned("fd"), PyString.fromInterned("exception"), PyString.fromInterned("hangup")})));
      var1.setlocal("_PollNotification", var3);
      var3 = null;
      var1.setline(469);
      var9 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("poll", var9, poll$41);
      var1.setlocal("poll", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(587);
      var9 = new PyObject[]{var1.getname("ChannelInboundHandlerAdapter")};
      var4 = Py.makeClass("PythonInboundHandler", var9, PythonInboundHandler$50);
      var1.setlocal("PythonInboundHandler", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(615);
      var9 = new PyObject[]{var1.getname("ChannelInitializer")};
      var4 = Py.makeClass("ChildSocketHandler", var9, ChildSocketHandler$56);
      var1.setlocal("ChildSocketHandler", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(687);
      var3 = var1.getname("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(4));
      var13 = Py.unpackSequence(var3, 4);
      var5 = var13[0];
      var1.setlocal("UNKNOWN_SOCKET", var5);
      var5 = null;
      var5 = var13[1];
      var1.setlocal("CLIENT_SOCKET", var5);
      var5 = null;
      var5 = var13[2];
      var1.setlocal("SERVER_SOCKET", var5);
      var5 = null;
      var5 = var13[3];
      var1.setlocal("DATAGRAM_SOCKET", var5);
      var5 = null;
      var3 = null;
      var1.setline(688);
      var17 = new PyDictionary(new PyObject[]{var1.getname("UNKNOWN_SOCKET"), PyString.fromInterned("unknown"), var1.getname("CLIENT_SOCKET"), PyString.fromInterned("client"), var1.getname("SERVER_SOCKET"), PyString.fromInterned("server"), var1.getname("DATAGRAM_SOCKET"), PyString.fromInterned("datagram")});
      var1.setlocal("_socket_types", var17);
      var3 = null;
      var1.setline(696);
      var9 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var9, _identity$61, (PyObject)null);
      var1.setlocal("_identity", var12);
      var3 = null;
      var1.setline(700);
      var9 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var9, _set_option$62, (PyObject)null);
      var1.setlocal("_set_option", var12);
      var3 = null;
      var1.setline(719);
      var17 = new PyDictionary(new PyObject[]{var1.getname("IPPROTO_TCP"), new PyDictionary(new PyObject[]{new PyTuple(new PyObject[]{var1.getname("SOL_SOCKET"), var1.getname("SO_KEEPALIVE")}), new PyTuple(new PyObject[]{var1.getname("ChannelOption").__getattr__("SO_KEEPALIVE"), var1.getname("bool")}), new PyTuple(new PyObject[]{var1.getname("SOL_SOCKET"), var1.getname("SO_LINGER")}), new PyTuple(new PyObject[]{var1.getname("ChannelOption").__getattr__("SO_LINGER"), var1.getname("_identity")}), new PyTuple(new PyObject[]{var1.getname("SOL_SOCKET"), var1.getname("SO_RCVBUF")}), new PyTuple(new PyObject[]{var1.getname("ChannelOption").__getattr__("SO_RCVBUF"), var1.getname("int")}), new PyTuple(new PyObject[]{var1.getname("SOL_SOCKET"), var1.getname("SO_REUSEADDR")}), new PyTuple(new PyObject[]{var1.getname("ChannelOption").__getattr__("SO_REUSEADDR"), var1.getname("bool")}), new PyTuple(new PyObject[]{var1.getname("SOL_SOCKET"), var1.getname("SO_SNDBUF")}), new PyTuple(new PyObject[]{var1.getname("ChannelOption").__getattr__("SO_SNDBUF"), var1.getname("int")}), new PyTuple(new PyObject[]{var1.getname("SOL_SOCKET"), var1.getname("SO_TIMEOUT")}), new PyTuple(new PyObject[]{var1.getname("ChannelOption").__getattr__("SO_TIMEOUT"), var1.getname("int")}), new PyTuple(new PyObject[]{var1.getname("IPPROTO_TCP"), var1.getname("TCP_NODELAY")}), new PyTuple(new PyObject[]{var1.getname("ChannelOption").__getattr__("TCP_NODELAY"), var1.getname("bool")})}), var1.getname("IPPROTO_UDP"), new PyDictionary(new PyObject[]{new PyTuple(new PyObject[]{var1.getname("SOL_SOCKET"), var1.getname("SO_BROADCAST")}), new PyTuple(new PyObject[]{var1.getname("ChannelOption").__getattr__("SO_BROADCAST"), var1.getname("bool")}), new PyTuple(new PyObject[]{var1.getname("SOL_SOCKET"), var1.getname("SO_RCVBUF")}), new PyTuple(new PyObject[]{var1.getname("ChannelOption").__getattr__("SO_RCVBUF"), var1.getname("int")}), new PyTuple(new PyObject[]{var1.getname("SOL_SOCKET"), var1.getname("SO_REUSEADDR")}), new PyTuple(new PyObject[]{var1.getname("ChannelOption").__getattr__("SO_REUSEADDR"), var1.getname("bool")}), new PyTuple(new PyObject[]{var1.getname("SOL_SOCKET"), var1.getname("SO_SNDBUF")}), new PyTuple(new PyObject[]{var1.getname("ChannelOption").__getattr__("SO_SNDBUF"), var1.getname("int")}), new PyTuple(new PyObject[]{var1.getname("SOL_SOCKET"), var1.getname("SO_TIMEOUT")}), new PyTuple(new PyObject[]{var1.getname("ChannelOption").__getattr__("SO_TIMEOUT"), var1.getname("int")})})});
      var1.setlocal("_socket_options", var17);
      var3 = null;
      var1.setline(738);
      var9 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var9, _socktuple$63, (PyObject)null);
      var1.setlocal("_socktuple", var12);
      var3 = null;
      var1.setline(749);
      var9 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("_realsocket", var9, _realsocket$64);
      var1.setlocal("_realsocket", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(1350);
      PyTuple var18 = new PyTuple(new PyObject[]{PyString.fromInterned("bind"), PyString.fromInterned("connect"), PyString.fromInterned("connect_ex"), PyString.fromInterned("fileno"), PyString.fromInterned("listen"), PyString.fromInterned("getpeername"), PyString.fromInterned("getsockname"), PyString.fromInterned("getsockopt"), PyString.fromInterned("setsockopt"), PyString.fromInterned("sendall"), PyString.fromInterned("setblocking"), PyString.fromInterned("settimeout"), PyString.fromInterned("gettimeout"), PyString.fromInterned("shutdown")});
      var1.setlocal("_socketmethods", var18);
      var3 = null;
      var1.setline(1361);
      var18 = new PyTuple(new PyObject[]{PyString.fromInterned("recv"), PyString.fromInterned("recvfrom"), PyString.fromInterned("recv_into"), PyString.fromInterned("recvfrom_into"), PyString.fromInterned("send"), PyString.fromInterned("sendto"), PyString.fromInterned("fileno")});
      var1.setlocal("_delegate_methods", var18);
      var3 = null;
      var1.setline(1365);
      var9 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("_closedsocket", var9, _closedsocket$106);
      var1.setlocal("_closedsocket", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(1383);
      var9 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("_socketobject", var9, _socketobject$109);
      var1.setlocal("_socketobject", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(1440);
      var9 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var9, meth$119, (PyObject)null);
      var1.setlocal("meth", var12);
      var3 = null;
      var1.setline(1444);
      var3 = var1.getname("_socketmethods").__iter__();

      while(true) {
         var1.setline(1444);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1452);
            var3 = var1.getname("_socketobject");
            var1.setlocal("socket", var3);
            var1.setlocal("SocketType", var3);
            var1.setline(1457);
            var9 = new PyObject[]{var1.getname("_realsocket")};
            var4 = Py.makeClass("ChildSocket", var9, ChildSocket$120);
            var1.setlocal("ChildSocket", var4);
            var4 = null;
            Arrays.fill(var9, (Object)null);
            var1.setline(1529);
            var9 = new PyObject[]{var1.getname("None")};
            var12 = new PyFunction(var1.f_globals, var9, select$130, (PyObject)null);
            var1.setlocal("select", var12);
            var3 = null;
            var1.setline(1540);
            var9 = new PyObject[]{var1.getname("_GLOBAL_DEFAULT_TIMEOUT"), var1.getname("None")};
            var12 = new PyFunction(var1.f_globals, var9, create_connection$131, PyString.fromInterned("Connect to *address* and return the socket object.\n\n    Convenience function.  Connect to *address* (a 2-tuple ``(host,\n    port)``) and return the socket object.  Passing the optional\n    *timeout* parameter will set the timeout on the socket instance\n    before attempting to connect.  If no *timeout* is supplied, the\n    global default timeout setting returned by :func:`getdefaulttimeout`\n    is used.  If *source_address* is set it must be a tuple of (host, port)\n    for the socket to bind as a source address before making the connection.\n    An host of '' or port 0 tells the OS to use the default.\n    "));
            var1.setlocal("create_connection", var12);
            var3 = null;
            var1.setline(1581);
            var3 = var1.getname("None");
            var1.setlocal("_defaulttimeout", var3);
            var3 = null;
            var1.setline(1583);
            var9 = Py.EmptyObjects;
            var12 = new PyFunction(var1.f_globals, var9, _calctimeoutvalue$132, (PyObject)null);
            var1.setlocal("_calctimeoutvalue", var12);
            var3 = null;
            var1.setline(1594);
            var9 = Py.EmptyObjects;
            var12 = new PyFunction(var1.f_globals, var9, getdefaulttimeout$133, (PyObject)null);
            var1.setlocal("getdefaulttimeout", var12);
            var3 = null;
            var1.setline(1597);
            var9 = Py.EmptyObjects;
            var12 = new PyFunction(var1.f_globals, var9, setdefaulttimeout$134, (PyObject)null);
            var1.setlocal("setdefaulttimeout", var12);
            var3 = null;
            var1.setline(1604);
            var9 = new PyObject[]{var1.getname("tuple")};
            var4 = Py.makeClass("_ip_address_t", var9, _ip_address_t$135);
            var1.setlocal("_ip_address_t", var4);
            var4 = null;
            Arrays.fill(var9, (Object)null);
            var1.setline(1608);
            var9 = new PyObject[]{var1.getname("_ip_address_t")};
            var4 = Py.makeClass("_ipv4_address_t", var9, _ipv4_address_t$136);
            var1.setlocal("_ipv4_address_t", var4);
            var4 = null;
            Arrays.fill(var9, (Object)null);
            var1.setline(1617);
            var9 = new PyObject[]{var1.getname("_ip_address_t")};
            var4 = Py.makeClass("_ipv6_address_t", var9, _ipv6_address_t$138);
            var1.setlocal("_ipv6_address_t", var4);
            var4 = null;
            Arrays.fill(var9, (Object)null);
            var1.setline(1627);
            var9 = Py.EmptyObjects;
            var12 = new PyFunction(var1.f_globals, var9, _get_jsockaddr$140, (PyObject)null);
            var1.setlocal("_get_jsockaddr", var12);
            var3 = null;
            var1.setline(1638);
            var9 = Py.EmptyObjects;
            var12 = new PyFunction(var1.f_globals, var9, _get_jsockaddr2$141, (PyObject)null);
            var1.setlocal("_get_jsockaddr2", var12);
            var3 = null;
            var1.setline(1674);
            var9 = new PyObject[]{var1.getname("None")};
            var12 = new PyFunction(var1.f_globals, var9, _is_ip_address$142, (PyObject)null);
            var1.setlocal("_is_ip_address", var12);
            var3 = null;
            var1.setline(1682);
            var9 = Py.EmptyObjects;
            var12 = new PyFunction(var1.f_globals, var9, is_ipv4_address$143, (PyObject)null);
            var1.setlocal("is_ipv4_address", var12);
            var3 = null;
            var1.setline(1686);
            var9 = Py.EmptyObjects;
            var12 = new PyFunction(var1.f_globals, var9, is_ipv6_address$144, (PyObject)null);
            var1.setlocal("is_ipv6_address", var12);
            var3 = null;
            var1.setline(1690);
            var9 = Py.EmptyObjects;
            var12 = new PyFunction(var1.f_globals, var9, is_ip_address$145, (PyObject)null);
            var1.setlocal("is_ip_address", var12);
            var3 = null;
            var1.setline(1697);
            var3 = var1.getname("False");
            var1.setlocal("_ipv4_addresses_only", var3);
            var3 = null;
            var1.setline(1699);
            var9 = Py.EmptyObjects;
            var12 = new PyFunction(var1.f_globals, var9, _use_ipv4_addresses_only$146, (PyObject)null);
            var1.setlocal("_use_ipv4_addresses_only", var12);
            var3 = null;
            var1.setline(1704);
            var9 = Py.EmptyObjects;
            var12 = new PyFunction(var1.f_globals, var9, _getaddrinfo_get_host$147, (PyObject)null);
            var1.setlocal("_getaddrinfo_get_host", var12);
            var3 = null;
            var1.setline(1719);
            var9 = Py.EmptyObjects;
            var12 = new PyFunction(var1.f_globals, var9, _getaddrinfo_get_port$148, (PyObject)null);
            var1.setlocal("_getaddrinfo_get_port", var12);
            var3 = null;
            var1.setline(1740);
            var9 = new PyObject[]{var1.getname("AF_UNSPEC"), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0)};
            var12 = new PyFunction(var1.f_globals, var9, getaddrinfo$149, (PyObject)null);
            var3 = var1.getname("raises_java_exception").__call__((ThreadState)var2, (PyObject)var12);
            var1.setlocal("getaddrinfo", var3);
            var3 = null;
            var1.setline(1796);
            var9 = Py.EmptyObjects;
            var12 = new PyFunction(var1.f_globals, var9, htons$153, (PyObject)null);
            var1.setlocal("htons", var12);
            var3 = null;
            var1.setline(1797);
            var9 = Py.EmptyObjects;
            var12 = new PyFunction(var1.f_globals, var9, htonl$154, (PyObject)null);
            var1.setlocal("htonl", var12);
            var3 = null;
            var1.setline(1798);
            var9 = Py.EmptyObjects;
            var12 = new PyFunction(var1.f_globals, var9, ntohs$155, (PyObject)null);
            var1.setlocal("ntohs", var12);
            var3 = null;
            var1.setline(1799);
            var9 = Py.EmptyObjects;
            var12 = new PyFunction(var1.f_globals, var9, ntohl$156, (PyObject)null);
            var1.setlocal("ntohl", var12);
            var3 = null;
            var1.setline(1801);
            var9 = Py.EmptyObjects;
            var12 = new PyFunction(var1.f_globals, var9, inet_pton$157, (PyObject)null);
            var3 = var1.getname("raises_java_exception").__call__((ThreadState)var2, (PyObject)var12);
            var1.setlocal("inet_pton", var3);
            var3 = null;
            var1.setline(1820);
            var9 = Py.EmptyObjects;
            var12 = new PyFunction(var1.f_globals, var9, inet_ntop$158, (PyObject)null);
            var3 = var1.getname("raises_java_exception").__call__((ThreadState)var2, (PyObject)var12);
            var1.setlocal("inet_ntop", var3);
            var3 = null;
            var1.setline(1834);
            var9 = Py.EmptyObjects;
            var12 = new PyFunction(var1.f_globals, var9, inet_aton$159, (PyObject)null);
            var1.setlocal("inet_aton", var12);
            var3 = null;
            var1.setline(1837);
            var9 = Py.EmptyObjects;
            var12 = new PyFunction(var1.f_globals, var9, inet_ntoa$160, (PyObject)null);
            var1.setlocal("inet_ntoa", var12);
            var3 = null;
            var1.setline(1845);
            var9 = Py.EmptyObjects;
            var12 = new PyFunction(var1.f_globals, var9, _gethostbyaddr$161, (PyObject)null);
            var1.setlocal("_gethostbyaddr", var12);
            var3 = null;
            var1.setline(1855);
            var9 = new PyObject[]{var1.getname("None")};
            var12 = new PyFunction(var1.f_globals, var9, getfqdn$162, PyString.fromInterned("\n    Return a fully qualified domain name for name. If name is omitted or empty\n    it is interpreted as the local host.  To find the fully qualified name,\n    the hostname returned by gethostbyaddr() is checked, then aliases for the\n    host, if available. The first name which includes a period is selected.\n    In case no fully qualified domain name is available, the hostname is retur\n    New in version 2.0.\n    "));
            var3 = var1.getname("raises_java_exception").__call__((ThreadState)var2, (PyObject)var12);
            var1.setlocal("getfqdn", var3);
            var3 = null;
            var1.setline(1873);
            var9 = Py.EmptyObjects;
            var12 = new PyFunction(var1.f_globals, var9, gethostname$163, (PyObject)null);
            var3 = var1.getname("raises_java_exception").__call__((ThreadState)var2, (PyObject)var12);
            var1.setlocal("gethostname", var3);
            var3 = null;
            var1.setline(1877);
            var9 = Py.EmptyObjects;
            var12 = new PyFunction(var1.f_globals, var9, gethostbyname$164, (PyObject)null);
            var3 = var1.getname("raises_java_exception").__call__((ThreadState)var2, (PyObject)var12);
            var1.setlocal("gethostbyname", var3);
            var3 = null;
            var1.setline(1886);
            var9 = Py.EmptyObjects;
            var12 = new PyFunction(var1.f_globals, var9, gethostbyname_ex$165, (PyObject)null);
            var3 = var1.getname("raises_java_exception").__call__((ThreadState)var2, (PyObject)var12);
            var1.setlocal("gethostbyname_ex", var3);
            var3 = null;
            var1.setline(1890);
            var9 = Py.EmptyObjects;
            var12 = new PyFunction(var1.f_globals, var9, gethostbyaddr$166, (PyObject)null);
            var3 = var1.getname("raises_java_exception").__call__((ThreadState)var2, (PyObject)var12);
            var1.setlocal("gethostbyaddr", var3);
            var3 = null;

            try {
               var1.setline(1897);
               var8 = new String[]{"Service", "Protocol"};
               var9 = imp.importFrom("jnr.netdb", var8, var1, -1);
               var4 = var9[0];
               var1.setlocal("Service", var4);
               var4 = null;
               var4 = var9[1];
               var1.setlocal("Protocol", var4);
               var4 = null;
               var1.setline(1899);
               var9 = new PyObject[]{var1.getname("None")};
               var12 = new PyFunction(var1.f_globals, var9, getservbyname$167, (PyObject)null);
               var1.setlocal("getservbyname", var12);
               var3 = null;
               var1.setline(1905);
               var9 = new PyObject[]{var1.getname("None")};
               var12 = new PyFunction(var1.f_globals, var9, getservbyport$168, (PyObject)null);
               var1.setlocal("getservbyport", var12);
               var3 = null;
               var1.setline(1911);
               var9 = new PyObject[]{var1.getname("None")};
               var12 = new PyFunction(var1.f_globals, var9, getprotobyname$169, (PyObject)null);
               var1.setlocal("getprotobyname", var12);
               var3 = null;
            } catch (Throwable var6) {
               var10 = Py.setException(var6, var1);
               if (!var10.match(var1.getname("ImportError"))) {
                  throw var10;
               }

               var1.setline(1918);
               var13 = new PyObject[]{var1.getname("None")};
               PyFunction var15 = new PyFunction(var1.f_globals, var13, getservbyname$170, (PyObject)null);
               var1.setlocal("getservbyname", var15);
               var4 = null;
               var1.setline(1921);
               var13 = new PyObject[]{var1.getname("None")};
               var15 = new PyFunction(var1.f_globals, var13, getservbyport$171, (PyObject)null);
               var1.setlocal("getservbyport", var15);
               var4 = null;
               var1.setline(1924);
               var13 = new PyObject[]{var1.getname("None")};
               var15 = new PyFunction(var1.f_globals, var13, getprotobyname$172, (PyObject)null);
               var1.setlocal("getprotobyname", var15);
               var4 = null;
            }

            var1.setline(1928);
            var9 = Py.EmptyObjects;
            var12 = new PyFunction(var1.f_globals, var9, _getnameinfo_get_host$173, (PyObject)null);
            var1.setlocal("_getnameinfo_get_host", var12);
            var3 = null;
            var1.setline(1945);
            var9 = Py.EmptyObjects;
            var12 = new PyFunction(var1.f_globals, var9, _getnameinfo_get_port$174, (PyObject)null);
            var1.setlocal("_getnameinfo_get_port", var12);
            var3 = null;
            var1.setline(1955);
            var9 = Py.EmptyObjects;
            var12 = new PyFunction(var1.f_globals, var9, getnameinfo$175, (PyObject)null);
            var3 = var1.getname("raises_java_exception").__call__((ThreadState)var2, (PyObject)var12);
            var1.setlocal("getnameinfo", var3);
            var3 = null;
            var1.setline(1965);
            var9 = new PyObject[]{var1.getname("object")};
            var4 = Py.makeClass("_fileobject", var9, _fileobject$176);
            var1.setlocal("_fileobject", var4);
            var4 = null;
            Arrays.fill(var9, (Object)null);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal("_m", var4);
         var1.setline(1445);
         var5 = var1.getname("partial").__call__(var2, var1.getname("meth"), var1.getname("_m"));
         var1.setlocal("p", var5);
         var5 = null;
         var1.setline(1446);
         var5 = var1.getname("_m");
         var1.getname("p").__setattr__("__name__", var5);
         var5 = null;
         var1.setline(1447);
         var5 = var1.getname("getattr").__call__(var2, var1.getname("_realsocket"), var1.getname("_m")).__getattr__("__doc__");
         var1.getname("p").__setattr__("__doc__", var5);
         var5 = null;
         var1.setline(1448);
         var5 = var1.getname("MethodType").__call__(var2, var1.getname("p"), var1.getname("None"), var1.getname("_socketobject"));
         var1.setlocal("m", var5);
         var5 = null;
         var1.setline(1449);
         var1.getname("setattr").__call__(var2, var1.getname("_socketobject"), var1.getname("_m"), var1.getname("m"));
      }
   }

   public PyObject _debug$1(PyFrame var1, ThreadState var2) {
      var1.setline(61);
      PyString var3 = PyString.fromInterned("%(asctime)-15s %(threadName)s %(levelname)s %(funcName)s %(message)s %(sock)s");
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(62);
      PyObject var5 = var1.getglobal("logging").__getattr__("StreamHandler").__call__(var2);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(63);
      var1.getlocal(1).__getattr__("setFormatter").__call__(var2, var1.getglobal("logging").__getattr__("Formatter").__call__(var2, var1.getlocal(0)));
      var1.setline(64);
      var1.getglobal("log").__getattr__("addHandler").__call__(var2, var1.getlocal(1));
      var1.setline(65);
      PyObject var10000 = var1.getglobal("log").__getattr__("setLevel");
      PyObject[] var6 = new PyObject[]{var1.getglobal("logging").__getattr__("DEBUG")};
      String[] var4 = new String[]{"level"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject DaemonThreadFactory$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(205);
      PyObject var3 = var1.getname("AtomicLong").__call__(var2);
      var1.setlocal("thread_count", var3);
      var3 = null;
      var1.setline(207);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$3, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(210);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, newThread$4, (PyObject)null);
      var1.setlocal("newThread", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(208);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("label", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject newThread$4(PyFrame var1, ThreadState var2) {
      var1.setline(211);
      PyObject var3 = var1.getglobal("Thread").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(212);
      var3 = var1.getglobal("True");
      var1.getlocal(2).__setattr__("daemon", var3);
      var3 = null;
      var1.setline(213);
      var3 = var1.getlocal(0).__getattr__("label")._mod(var1.getlocal(0).__getattr__("thread_count").__getattr__("getAndIncrement").__call__(var2));
      var1.getlocal(2).__setattr__("name", var3);
      var3 = null;
      var1.setline(214);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _check_threadpool_for_pending_threads$5(PyFrame var1, ThreadState var2) {
      var1.setline(221);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(222);
      PyObject var6 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(222);
         PyObject var4 = var6.__iternext__();
         PyObject var10000;
         if (var4 == null) {
            var1.setline(226);
            var10000 = var1.getglobal("log").__getattr__("debug");
            PyObject[] var7 = new PyObject[]{PyString.fromInterned("Pending threads in Netty pool: %s"), var1.getglobal("pprint").__getattr__("pformat").__call__(var2, var1.getlocal(1)), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), PyString.fromInterned("*")})};
            String[] var8 = new String[]{"extra"};
            var10000.__call__(var2, var7, var8);
            var3 = null;
            var1.setline(227);
            var6 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(2, var4);
         var1.setline(223);
         PyObject var5 = var1.getlocal(2).__getattr__("pendingTasks").__call__(var2);
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(224);
         var5 = var1.getlocal(3);
         var10000 = var5._gt(Py.newInteger(0));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(225);
            var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)})));
         }
      }
   }

   public PyObject _shutdown_threadpool$6(PyFrame var1, ThreadState var2) {
      var1.setline(231);
      PyObject var10000 = var1.getglobal("log").__getattr__("debug");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("Shutting down thread pool..."), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), PyString.fromInterned("*")})};
      String[] var4 = new String[]{"extra"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(237);
      var1.getglobal("NIO_GROUP").__getattr__("shutdownGracefully").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)Py.newInteger(100), (PyObject)var1.getglobal("TimeUnit").__getattr__("MILLISECONDS"));
      var1.setline(238);
      var10000 = var1.getglobal("log").__getattr__("debug");
      var3 = new PyObject[]{PyString.fromInterned("Shut down thread pool"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), PyString.fromInterned("*")})};
      var4 = new String[]{"extra"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject error$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(249);
      return var1.getf_locals();
   }

   public PyObject herror$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(250);
      return var1.getf_locals();
   }

   public PyObject gaierror$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(251);
      return var1.getf_locals();
   }

   public PyObject timeout$10(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(252);
      return var1.getf_locals();
   }

   public PyObject SSLError$11(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(253);
      return var1.getf_locals();
   }

   public PyObject _add_exception_attrs$12(PyFrame var1, ThreadState var2) {
      var1.setline(268);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
      var1.getlocal(0).__setattr__("errno", var3);
      var3 = null;
      var1.setline(269);
      var3 = var1.getlocal(0).__getitem__(Py.newInteger(1));
      var1.getlocal(0).__setattr__("strerror", var3);
      var3 = null;
      var1.setline(270);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _unmapped_exception$13(PyFrame var1, ThreadState var2) {
      var1.setline(274);
      PyObject var3 = var1.getglobal("_add_exception_attrs").__call__(var2, var1.getglobal("error").__call__((ThreadState)var2, (PyObject)Py.newInteger(-1), (PyObject)PyString.fromInterned("Unmapped exception: %s")._mod(var1.getlocal(0))));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject java_net_socketexception_handler$14(PyFrame var1, ThreadState var2) {
      var1.setline(278);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("message").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Address family not supported by protocol family")).__nonzero__()) {
         var1.setline(279);
         var3 = var1.getglobal("_add_exception_attrs").__call__(var2, var1.getglobal("error").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("EAFNOSUPPORT"), (PyObject)PyString.fromInterned("Address family not supported by protocol family: See http://wiki.python.org/jython/NewSocketModule#IPV6_address_support")));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(282);
         if (var1.getlocal(0).__getattr__("message").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Address already in use")).__nonzero__()) {
            var1.setline(283);
            var3 = var1.getglobal("error").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("EADDRINUSE"), (PyObject)PyString.fromInterned("Address already in use"));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(284);
            var3 = var1.getglobal("_unmapped_exception").__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject would_block_error$15(PyFrame var1, ThreadState var2) {
      var1.setline(288);
      PyObject var3 = var1.getglobal("_add_exception_attrs").__call__(var2, var1.getglobal("error").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("EWOULDBLOCK"), (PyObject)PyString.fromInterned("The socket operation could not complete without blocking")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$16(PyFrame var1, ThreadState var2) {
      var1.setline(296);
      PyObject var3 = var1.getglobal("error").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("ECONNRESET"), (PyObject)PyString.fromInterned("Software caused connection abort"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$17(PyFrame var1, ThreadState var2) {
      var1.setline(297);
      PyObject var3 = var1.getglobal("timeout").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("ETIMEDOUT"), (PyObject)PyString.fromInterned("timed out"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$18(PyFrame var1, ThreadState var2) {
      var1.setline(298);
      PyObject var3 = var1.getglobal("error").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("EPIPE"), (PyObject)PyString.fromInterned("Illegal state exception"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$19(PyFrame var1, ThreadState var2) {
      var1.setline(300);
      PyObject var3 = var1.getglobal("error").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("EADDRINUSE"), (PyObject)PyString.fromInterned("Address already in use"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$20(PyFrame var1, ThreadState var2) {
      var1.setline(301);
      PyObject var3 = var1.getglobal("error").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("ECONNREFUSED"), (PyObject)PyString.fromInterned("Connection refused"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$21(PyFrame var1, ThreadState var2) {
      var1.setline(302);
      PyObject var3 = var1.getglobal("error").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("EHOSTUNREACH"), (PyObject)PyString.fromInterned("No route to host"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$22(PyFrame var1, ThreadState var2) {
      var1.setline(306);
      PyObject var3 = var1.getglobal("timeout").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("ETIMEDOUT"), (PyObject)PyString.fromInterned("timed out"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$23(PyFrame var1, ThreadState var2) {
      var1.setline(307);
      PyObject var3 = var1.getglobal("gaierror").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("EGETADDRINFOFAILED"), (PyObject)PyString.fromInterned("getaddrinfo failed"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$24(PyFrame var1, ThreadState var2) {
      var1.setline(309);
      PyObject var3 = var1.getglobal("error").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("EISCONN"), (PyObject)PyString.fromInterned("Socket is already connected"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$25(PyFrame var1, ThreadState var2) {
      var1.setline(313);
      PyObject var3 = var1.getglobal("error").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("ECONNRESET"), (PyObject)PyString.fromInterned("Socket closed"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$26(PyFrame var1, ThreadState var2) {
      var1.setline(323);
      PyObject var3 = var1.getglobal("gaierror").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("EGETADDRINFOFAILED"), (PyObject)PyString.fromInterned("getaddrinfo failed"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$27(PyFrame var1, ThreadState var2) {
      var1.setline(326);
      PyObject var3 = var1.getglobal("SSLError").__call__(var2, var1.getglobal("SSL_ERROR_SSL"), var1.getlocal(0).__getattr__("message"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$28(PyFrame var1, ThreadState var2) {
      var1.setline(332);
      PyObject var3 = var1.getglobal("SSLError").__call__(var2, var1.getglobal("SSL_UNKNOWN_PROTOCOL"), var1.getlocal(0).__getattr__("message"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _map_exception$29(PyFrame var1, ThreadState var2) {
      var1.setline(337);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("NettyChannelException")).__nonzero__()) {
         var1.setline(338);
         var3 = var1.getlocal(0).__getattr__("cause");
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(339);
      PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("SSLException"));
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("CertificateException"));
      }

      if (var10000.__nonzero__()) {
         var1.setline(340);
         var3 = var1.getlocal(0).__getattr__("cause");
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(341);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(344);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("SSLHandshakeException")).__nonzero__()) {
               var1.setline(345);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1).__getattr__("cause"), var1.getglobal("CertificateException")).__nonzero__()) {
                  var1.setline(346);
                  var3 = var1.getlocal(1).__getattr__("cause");
                  var1.setlocal(0, var3);
                  var3 = null;
               }
            }

            var1.setline(348);
            var3 = PyString.fromInterned("%s (%s)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("message"), var1.getlocal(1)}));
            var1.setlocal(2, var3);
            var3 = null;
         } else {
            var1.setline(350);
            var3 = var1.getlocal(0).__getattr__("message");
            var1.setlocal(2, var3);
            var3 = null;
         }

         var1.setline(351);
         var3 = var1.getglobal("SSLError").__call__(var2, var1.getglobal("SSL_ERROR_SSL"), var1.getlocal(2));
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(356);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("java").__getattr__("net").__getattr__("ConnectException")).__nonzero__()) {
            var1.setline(357);
            var3 = var1.getglobal("_exception_map").__getattr__("get").__call__(var2, var1.getglobal("java").__getattr__("net").__getattr__("ConnectException"));
            var1.setlocal(4, var3);
            var3 = null;
         } else {
            var1.setline(360);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("java").__getattr__("net").__getattr__("NoRouteToHostException")).__nonzero__()) {
               var1.setline(361);
               var3 = var1.getglobal("_exception_map").__getattr__("get").__call__(var2, var1.getglobal("java").__getattr__("net").__getattr__("NoRouteToHostException"));
               var1.setlocal(4, var3);
               var3 = null;
            } else {
               var1.setline(363);
               var3 = var1.getglobal("_exception_map").__getattr__("get").__call__(var2, var1.getlocal(0).__getattr__("__class__"));
               var1.setlocal(4, var3);
               var3 = null;
            }
         }

         var1.setline(364);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(365);
            var3 = var1.getlocal(4).__call__(var2, var1.getlocal(0));
            var1.setlocal(3, var3);
            var3 = null;
         } else {
            var1.setline(367);
            var3 = var1.getglobal("error").__call__((ThreadState)var2, (PyObject)Py.newInteger(-1), (PyObject)PyString.fromInterned("Unmapped exception: %s")._mod(var1.getlocal(0)));
            var1.setlocal(3, var3);
            var3 = null;
         }
      }

      var1.setline(368);
      var3 = var1.getlocal(0);
      var1.getlocal(3).__setattr__("java_exception", var3);
      var3 = null;
      var1.setline(369);
      var3 = var1.getglobal("_add_exception_attrs").__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject raises_java_exception$30(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(375);
      PyString.fromInterned("Maps java socket exceptions to the equivalent python exception.\n    Also sets _last_error on socket objects so as to support SO_ERROR.\n    ");
      var1.setline(377);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = handle_exception$31;
      var3 = new PyObject[]{var1.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      PyObject var5 = var1.getglobal("wraps").__call__(var2, var1.getderef(0)).__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(392);
      var5 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject handle_exception$31(PyFrame var1, ThreadState var2) {
      var1.setline(379);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(0)), var1.getglobal("_realsocket"));
      }

      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;

      PyException var4;
      PyObject var5;
      try {
         try {
            var1.setline(382);
            var10000 = var1.getderef(0);
            PyObject[] var8 = Py.EmptyObjects;
            String[] var9 = new String[0];
            var10000 = var10000._callextra(var8, var9, var1.getlocal(0), var1.getlocal(1));
            var3 = null;
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         } catch (Throwable var6) {
            var4 = Py.setException(var6, var1);
            if (var4.match(var1.getglobal("java").__getattr__("lang").__getattr__("Exception"))) {
               var5 = var4.value;
               var1.setlocal(3, var5);
               var5 = null;
               var1.setline(384);
               throw Py.makeException(var1.getglobal("_map_exception").__call__(var2, var1.getlocal(3)));
            } else {
               throw var4;
            }
         }
      } catch (Throwable var7) {
         var4 = Py.setException(var7, var1);
         if (var4.match(var1.getglobal("error"))) {
            var5 = var4.value;
            var1.setlocal(4, var5);
            var5 = null;
            var1.setline(386);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(387);
               var5 = var1.getlocal(4).__getitem__(Py.newInteger(0));
               var1.getlocal(0).__getitem__(Py.newInteger(0)).__setattr__("_last_error", var5);
               var5 = null;
            }

            var1.setline(388);
            throw Py.makeException();
         } else {
            throw var4;
         }
      }
   }

   public PyObject _Select$32(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(400);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$33, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(406);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _normalize_sockets$34, (PyObject)null);
      var1.setlocal("_normalize_sockets", var4);
      var3 = null;
      var1.setline(418);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, notify$35, (PyObject)null);
      var1.setlocal("notify", var4);
      var3 = null;
      var1.setline(422);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$36, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      var1.setline(425);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _register_sockets$37, (PyObject)null);
      PyObject var5 = var1.getname("contextmanager").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("_register_sockets", var5);
      var3 = null;
      var1.setline(434);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __call__$38, (PyObject)null);
      var1.setlocal("__call__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$33(PyFrame var1, ThreadState var2) {
      var1.setline(401);
      PyObject var3 = var1.getglobal("Condition").__call__(var2);
      var1.getlocal(0).__setattr__("cv", var3);
      var3 = null;
      var1.setline(402);
      var3 = var1.getglobal("frozenset").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("rlist", var3);
      var3 = null;
      var1.setline(403);
      var3 = var1.getglobal("frozenset").__call__(var2, var1.getlocal(2));
      var1.getlocal(0).__setattr__("wlist", var3);
      var3 = null;
      var1.setline(404);
      var3 = var1.getglobal("frozenset").__call__(var2, var1.getlocal(3));
      var1.getlocal(0).__setattr__("xlist", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _normalize_sockets$34(PyFrame var1, ThreadState var2) {
      var1.setline(408);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(409);
      PyObject var7 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(409);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(416);
            var7 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var7;
         }

         var1.setlocal(3, var4);

         PyException var5;
         try {
            var1.setline(411);
            PyObject var8 = var1.getlocal(3).__getattr__("fileno").__call__(var2);
            var1.setlocal(4, var8);
            var5 = null;
            var1.setline(412);
            var1.getlocal(4).__getattr__("_register_selector");
            var1.setline(413);
            var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(4));
         } catch (Throwable var6) {
            var5 = Py.setException(var6, var1);
            if (var5.match(var1.getglobal("AttributeError"))) {
               var1.setline(415);
               throw Py.makeException(var1.getglobal("error").__call__(var2, var1.getglobal("errno").__getattr__("EBADF"), PyString.fromInterned("Bad file descriptor: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(3)}))));
            }

            throw var5;
         }
      }
   }

   public PyObject notify$35(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      ContextManager var3;
      PyObject var4 = (var3 = ContextGuard.getManager(var1.getlocal(0).__getattr__("cv"))).__enter__(var2);

      label16: {
         try {
            var1.setline(420);
            var1.getlocal(0).__getattr__("cv").__getattr__("notify").__call__(var2);
         } catch (Throwable var5) {
            if (var3.__exit__(var2, Py.setException(var5, var1))) {
               break label16;
            }

            throw (Throwable)Py.makeException();
         }

         var3.__exit__(var2, (PyException)null);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __str__$36(PyFrame var1, ThreadState var2) {
      var1.setline(423);
      PyObject var3 = PyString.fromInterned("_Select(r={},w={},x={})").__getattr__("format").__call__(var2, var1.getglobal("list").__call__(var2, var1.getlocal(0).__getattr__("rlist")), var1.getglobal("list").__call__(var2, var1.getlocal(0).__getattr__("wlist")), var1.getglobal("list").__call__(var2, var1.getlocal(0).__getattr__("xlist")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _register_sockets$37(PyFrame var1, ThreadState var2) {
      Object[] var3;
      PyObject var4;
      PyObject var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(427);
            var5 = var1.getlocal(0).__getattr__("_normalize_sockets").__call__(var2, var1.getlocal(1));
            var1.setlocal(1, var5);
            var3 = null;
            var1.setline(428);
            var5 = var1.getlocal(1).__iter__();

            while(true) {
               var1.setline(428);
               var4 = var5.__iternext__();
               if (var4 == null) {
                  var1.setline(430);
                  var1.setline(430);
                  var6 = var1.getlocal(0);
                  var1.f_lasti = 1;
                  var3 = new Object[5];
                  var1.f_savedlocals = var3;
                  return var6;
               }

               var1.setlocal(2, var4);
               var1.setline(429);
               var1.getlocal(2).__getattr__("_register_selector").__call__(var2, var1.getlocal(0));
            }
         case 1:
            var3 = var1.f_savedlocals;
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            } else {
               var6 = (PyObject)var10000;
               var1.setline(431);
               var5 = var1.getlocal(1).__iter__();

               while(true) {
                  var1.setline(431);
                  var4 = var5.__iternext__();
                  if (var4 == null) {
                     var1.f_lasti = -1;
                     return Py.None;
                  }

                  var1.setlocal(2, var4);
                  var1.setline(432);
                  var1.getlocal(2).__getattr__("_unregister_selector").__call__(var2, var1.getlocal(0));
               }
            }
      }
   }

   public PyObject __call__$38(PyFrame param1, ThreadState param2) {
      // $FF: Couldn't be decompiled
   }

   public PyObject f$39(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(440);
            var3 = var1.getlocal(0).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var6 = (PyObject)var10000;
      }

      do {
         var1.setline(440);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(440);
      } while(!var1.getlocal(1).__getattr__("fileno").__call__(var2).__getattr__("_readable").__call__(var2).__nonzero__());

      var1.setline(440);
      var1.setline(440);
      var6 = var1.getlocal(1);
      var1.f_lasti = 1;
      var5 = new Object[]{null, null, null, var3, var4};
      var1.f_savedlocals = var5;
      return var6;
   }

   public PyObject f$40(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(441);
            var3 = var1.getlocal(0).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var6 = (PyObject)var10000;
      }

      do {
         var1.setline(441);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(441);
      } while(!var1.getlocal(1).__getattr__("fileno").__call__(var2).__getattr__("_writable").__call__(var2).__nonzero__());

      var1.setline(441);
      var1.setline(441);
      var6 = var1.getlocal(1);
      var1.f_lasti = 1;
      var5 = new Object[]{null, null, null, var3, var4};
      var1.f_savedlocals = var5;
      return var6;
   }

   public PyObject poll$41(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(471);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$42, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(476);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, notify$43, (PyObject)null);
      var1.setlocal("notify", var4);
      var3 = null;
      var1.setline(486);
      var3 = new PyObject[]{var1.getname("POLLIN")._or(var1.getname("POLLPRI"))._or(var1.getname("POLLOUT"))};
      var4 = new PyFunction(var1.f_globals, var3, register$44, (PyObject)null);
      var1.setlocal("register", var4);
      var3 = null;
      var1.setline(496);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, modify$45, (PyObject)null);
      var1.setlocal("modify", var4);
      var3 = null;
      var1.setline(503);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unregister$46, (PyObject)null);
      var1.setlocal("unregister", var4);
      var3 = null;
      var1.setline(511);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _event_test$47, (PyObject)null);
      var1.setlocal("_event_test", var4);
      var3 = null;
      var1.setline(532);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _handle_poll$48, (PyObject)null);
      var1.setlocal("_handle_poll", var4);
      var3 = null;
      var1.setline(561);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, poll$49, (PyObject)null);
      var1.setlocal("poll", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$42(PyFrame var1, ThreadState var2) {
      var1.setline(472);
      PyObject var3 = var1.getglobal("LinkedBlockingQueue").__call__(var2);
      var1.getlocal(0).__setattr__("queue", var3);
      var3 = null;
      var1.setline(473);
      var3 = var1.getglobal("dict").__call__(var2);
      var1.getlocal(0).__setattr__("registered", var3);
      var3 = null;
      var1.setline(474);
      var3 = var1.getglobal("dict_builder").__call__(var2, var1.getglobal("MapMaker").__call__(var2).__getattr__("weakKeys").__call__(var2).__getattr__("makeMap")).__call__(var2);
      var1.getlocal(0).__setattr__("socks2fd", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject notify$43(PyFrame var1, ThreadState var2) {
      var1.setline(477);
      PyObject var10000 = var1.getglobal("_PollNotification");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("socks2fd").__getattr__("get").__call__(var2, var1.getlocal(1)), var1.getlocal(2), var1.getlocal(3)};
      String[] var4 = new String[]{"sock", "fd", "exception", "hangup"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(482);
      var10000 = var1.getglobal("log").__getattr__("debug");
      var3 = new PyObject[]{PyString.fromInterned("Notify %s"), var1.getlocal(4), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), PyString.fromInterned("*")})};
      var4 = new String[]{"extra"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(484);
      var1.getlocal(0).__getattr__("queue").__getattr__("put").__call__(var2, var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject register$44(PyFrame var1, ThreadState var2) {
      var1.setline(487);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("fileno")).__not__().__nonzero__()) {
         var1.setline(488);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("argument must have a fileno() method")));
      } else {
         var1.setline(489);
         PyObject var3 = var1.getlocal(1).__getattr__("fileno").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(490);
         PyObject var10000 = var1.getglobal("log").__getattr__("debug");
         PyObject[] var5 = new PyObject[]{PyString.fromInterned("Register fd=%s eventmask=%s"), var1.getlocal(1), var1.getlocal(2), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(3)})};
         String[] var4 = new String[]{"extra"};
         var10000.__call__(var2, var5, var4);
         var3 = null;
         var1.setline(491);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__getattr__("registered").__setitem__(var1.getlocal(1), var3);
         var3 = null;
         var1.setline(492);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__getattr__("socks2fd").__setitem__(var1.getlocal(3), var3);
         var3 = null;
         var1.setline(493);
         var1.getlocal(3).__getattr__("_register_selector").__call__(var2, var1.getlocal(0));
         var1.setline(494);
         var1.getlocal(0).__getattr__("notify").__call__(var2, var1.getlocal(3));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject modify$45(PyFrame var1, ThreadState var2) {
      var1.setline(497);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("fileno")).__not__().__nonzero__()) {
         var1.setline(498);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("argument must have a fileno() method")));
      } else {
         var1.setline(499);
         PyObject var3 = var1.getlocal(1);
         PyObject var10000 = var3._notin(var1.getlocal(0).__getattr__("registered"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(500);
            throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("ENOENT"), (PyObject)PyString.fromInterned("No such file or directory")));
         } else {
            var1.setline(501);
            var3 = var1.getlocal(2);
            var1.getlocal(0).__getattr__("registered").__setitem__(var1.getlocal(1), var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject unregister$46(PyFrame var1, ThreadState var2) {
      var1.setline(504);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("fileno")).__not__().__nonzero__()) {
         var1.setline(505);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("argument must have a fileno() method")));
      } else {
         var1.setline(506);
         PyObject var10000 = var1.getglobal("log").__getattr__("debug");
         PyObject[] var3 = new PyObject[]{PyString.fromInterned("Unregister socket fd=%s"), var1.getlocal(1), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(1).__getattr__("fileno").__call__(var2)})};
         String[] var4 = new String[]{"extra"};
         var10000.__call__(var2, var3, var4);
         var3 = null;
         var1.setline(507);
         var1.getlocal(0).__getattr__("registered").__delitem__(var1.getlocal(1));
         var1.setline(508);
         PyObject var5 = var1.getlocal(1).__getattr__("fileno").__call__(var2);
         var1.setlocal(2, var5);
         var3 = null;
         var1.setline(509);
         var1.getlocal(2).__getattr__("_unregister_selector").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _event_test$47(PyFrame var1, ThreadState var2) {
      var1.setline(514);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyTuple var6;
      if (var10000.__nonzero__()) {
         var1.setline(515);
         var6 = new PyTuple(new PyObject[]{var1.getglobal("None"), Py.newInteger(0)});
         var1.f_lasti = -1;
         return var6;
      } else {
         var1.setline(516);
         PyObject var4 = var1.getlocal(0).__getattr__("registered").__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("fd"), (PyObject)Py.newInteger(0));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(517);
         var10000 = var1.getglobal("log").__getattr__("debug");
         PyObject[] var7 = new PyObject[]{PyString.fromInterned("Testing notification=%s mask=%s"), var1.getlocal(1), var1.getlocal(2), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), PyString.fromInterned("*")})};
         String[] var5 = new String[]{"extra"};
         var10000.__call__(var2, var7, var5);
         var4 = null;
         var1.setline(518);
         PyInteger var8 = Py.newInteger(0);
         var1.setlocal(3, var8);
         var4 = null;
         var1.setline(519);
         var10000 = var1.getlocal(2)._and(var1.getglobal("POLLIN"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__getattr__("sock").__getattr__("_readable").__call__(var2);
         }

         if (var10000.__nonzero__()) {
            var1.setline(520);
            var4 = var1.getlocal(3);
            var4 = var4._ior(var1.getglobal("POLLIN"));
            var1.setlocal(3, var4);
         }

         var1.setline(521);
         var10000 = var1.getlocal(2)._and(var1.getglobal("POLLOUT"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__getattr__("sock").__getattr__("_writable").__call__(var2);
         }

         if (var10000.__nonzero__()) {
            var1.setline(522);
            var4 = var1.getlocal(3);
            var4 = var4._ior(var1.getglobal("POLLOUT"));
            var1.setlocal(3, var4);
         }

         var1.setline(523);
         var10000 = var1.getlocal(2)._and(var1.getglobal("POLLERR"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__getattr__("exception");
         }

         if (var10000.__nonzero__()) {
            var1.setline(524);
            var4 = var1.getlocal(3);
            var4 = var4._ior(var1.getglobal("POLLERR"));
            var1.setlocal(3, var4);
         }

         var1.setline(525);
         var10000 = var1.getlocal(2)._and(var1.getglobal("POLLHUP"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__getattr__("hangup");
            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(1).__getattr__("sock").__getattr__("channel").__not__();
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(526);
            var4 = var1.getlocal(3);
            var4 = var4._ior(var1.getglobal("POLLHUP"));
            var1.setlocal(3, var4);
         }

         var1.setline(527);
         var10000 = var1.getlocal(2)._and(var1.getglobal("POLLNVAL"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__getattr__("sock").__getattr__("peer_closed").__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(528);
            var4 = var1.getlocal(3);
            var4 = var4._ior(var1.getglobal("POLLNVAL"));
            var1.setlocal(3, var4);
         }

         var1.setline(529);
         var10000 = var1.getglobal("log").__getattr__("debug");
         var7 = new PyObject[]{PyString.fromInterned("Tested notification=%s event=%s"), var1.getlocal(1), var1.getlocal(3), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), PyString.fromInterned("*")})};
         var5 = new String[]{"extra"};
         var10000.__call__(var2, var7, var5);
         var4 = null;
         var1.setline(530);
         var6 = new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("fd"), var1.getlocal(3)});
         var1.f_lasti = -1;
         return var6;
      }
   }

   public PyObject _handle_poll$48(PyFrame var1, ThreadState var2) {
      var1.setline(533);
      PyObject var3 = var1.getlocal(1).__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(534);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(535);
         PyList var9 = new PyList(Py.EmptyObjects);
         var1.f_lasti = -1;
         return var9;
      } else {
         var1.setline(539);
         PyList var4 = new PyList(new PyObject[]{var1.getlocal(2)});
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(540);
         var1.getlocal(0).__getattr__("queue").__getattr__("drainTo").__call__(var2, var1.getlocal(3));
         var1.setline(541);
         var10000 = var1.getglobal("log").__getattr__("debug");
         PyObject[] var10 = new PyObject[]{PyString.fromInterned("Got notification(s) %s"), var1.getlocal(3), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), PyString.fromInterned("MODULE")})};
         String[] var5 = new String[]{"extra"};
         var10000.__call__(var2, var10, var5);
         var4 = null;
         var1.setline(542);
         var4 = new PyList(Py.EmptyObjects);
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(543);
         PyObject var12 = var1.getglobal("set").__call__(var2);
         var1.setlocal(5, var12);
         var4 = null;
         var1.setline(548);
         var12 = var1.getlocal(3).__iter__();

         while(true) {
            var1.setline(548);
            PyObject var11 = var12.__iternext__();
            if (var11 == null) {
               var1.setline(556);
               var12 = var1.getlocal(5).__iter__();

               while(true) {
                  var1.setline(556);
                  var11 = var12.__iternext__();
                  if (var11 == null) {
                     var1.setline(559);
                     var3 = var1.getlocal(4);
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var1.setlocal(8, var11);
                  var1.setline(557);
                  var1.getlocal(0).__getattr__("notify").__call__(var2, var1.getlocal(8));
               }
            }

            var1.setlocal(2, var11);
            var1.setline(549);
            PyObject var6 = var1.getlocal(2).__getattr__("sock");
            var10000 = var6._notin(var1.getlocal(5));
            var6 = null;
            if (var10000.__nonzero__()) {
               var1.setline(550);
               var6 = var1.getlocal(0).__getattr__("_event_test").__call__(var2, var1.getlocal(2));
               PyObject[] var7 = Py.unpackSequence(var6, 2);
               PyObject var8 = var7[0];
               var1.setlocal(6, var8);
               var8 = null;
               var8 = var7[1];
               var1.setlocal(7, var8);
               var8 = null;
               var6 = null;
               var1.setline(551);
               if (var1.getlocal(7).__nonzero__()) {
                  var1.setline(552);
                  var1.getlocal(4).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(7)})));
                  var1.setline(553);
                  var1.getlocal(5).__getattr__("add").__call__(var2, var1.getlocal(2).__getattr__("sock"));
               }
            }
         }
      }
   }

   public PyObject poll$49(PyFrame var1, ThreadState var2) {
      var1.setline(562);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("numbers").__getattr__("Real"));
      }

      if (var10000.__not__().__nonzero__()) {
         var1.setline(563);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("timeout must be a number or None, got %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(1)}))));
      } else {
         var1.setline(564);
         var3 = var1.getlocal(1);
         var10000 = var3._lt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(565);
            var3 = var1.getglobal("None");
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(566);
         var10000 = var1.getglobal("log").__getattr__("debug");
         PyObject[] var7 = new PyObject[]{PyString.fromInterned("Polling timeout=%s"), var1.getlocal(1), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), PyString.fromInterned("*")})};
         String[] var4 = new String[]{"extra"};
         var10000.__call__(var2, var7, var4);
         var3 = null;
         var1.setline(567);
         var3 = var1.getlocal(1);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(568);
            var3 = var1.getlocal(0).__getattr__("_handle_poll").__call__(var2, var1.getlocal(0).__getattr__("queue").__getattr__("take"));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(569);
            PyObject var6 = var1.getlocal(1);
            var10000 = var6._eq(Py.newInteger(0));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(570);
               var3 = var1.getlocal(0).__getattr__("_handle_poll").__call__(var2, var1.getlocal(0).__getattr__("queue").__getattr__("poll"));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(572);
               var6 = var1.getglobal("float").__call__(var2, var1.getlocal(1))._div(Py.newFloat(1000.0));
               var1.setlocal(1, var6);
               var4 = null;

               while(true) {
                  var1.setline(573);
                  var6 = var1.getlocal(1);
                  var10000 = var6._gt(Py.newInteger(0));
                  var4 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(581);
                     PyList var8 = new PyList(Py.EmptyObjects);
                     var1.f_lasti = -1;
                     return var8;
                  }

                  var1.setline(574);
                  var6 = var1.getglobal("time").__getattr__("time").__call__(var2);
                  var1.setlocal(2, var6);
                  var4 = null;
                  var1.setline(575);
                  var6 = var1.getglobal("int").__call__(var2, var1.getlocal(1)._mul(var1.getglobal("_TO_NANOSECONDS")));
                  var1.setlocal(3, var6);
                  var4 = null;
                  var1.setline(576);
                  var6 = var1.getlocal(0).__getattr__("_handle_poll").__call__(var2, var1.getglobal("partial").__call__(var2, var1.getlocal(0).__getattr__("queue").__getattr__("poll"), var1.getlocal(3), var1.getglobal("TimeUnit").__getattr__("NANOSECONDS")));
                  var1.setlocal(4, var6);
                  var4 = null;
                  var1.setline(577);
                  if (var1.getlocal(4).__nonzero__()) {
                     var1.setline(578);
                     var3 = var1.getlocal(4);
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var1.setline(579);
                  var6 = var1.getlocal(1);
                  var6 = var6._isub(var1.getglobal("time").__getattr__("time").__call__(var2)._sub(var1.getlocal(2)));
                  var1.setlocal(1, var6);
                  var1.setline(580);
                  var10000 = var1.getglobal("log").__getattr__("debug");
                  PyObject[] var9 = new PyObject[]{PyString.fromInterned("Spurious wakeup, retrying with timeout=%s"), var1.getlocal(1), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), PyString.fromInterned("*")})};
                  String[] var5 = new String[]{"extra"};
                  var10000.__call__(var2, var9, var5);
                  var4 = null;
               }
            }
         }
      }
   }

   public PyObject PythonInboundHandler$50(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(589);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$51, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(593);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, channelActive$52, (PyObject)null);
      var1.setlocal("channelActive", var4);
      var3 = null;
      var1.setline(598);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, channelRead$53, (PyObject)null);
      var1.setlocal("channelRead", var4);
      var3 = null;
      var1.setline(605);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, channelWritabilityChanged$54, (PyObject)null);
      var1.setlocal("channelWritabilityChanged", var4);
      var3 = null;
      var1.setline(610);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, exceptionCaught$55, (PyObject)null);
      var1.setlocal("exceptionCaught", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$51(PyFrame var1, ThreadState var2) {
      var1.setline(590);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("sock", var3);
      var3 = null;
      var1.setline(591);
      PyObject var10000 = var1.getglobal("log").__getattr__("debug");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("Initializing inbound handler"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0).__getattr__("sock")})};
      String[] var4 = new String[]{"extra"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject channelActive$52(PyFrame var1, ThreadState var2) {
      var1.setline(594);
      PyObject var10000 = var1.getglobal("log").__getattr__("debug");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("Channel is active"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0).__getattr__("sock")})};
      String[] var4 = new String[]{"extra"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(595);
      var1.getlocal(0).__getattr__("sock").__getattr__("_notify_selectors").__call__(var2);
      var1.setline(596);
      var1.getlocal(1).__getattr__("fireChannelActive").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject channelRead$53(PyFrame var1, ThreadState var2) {
      var1.setline(599);
      PyObject var10000 = var1.getglobal("log").__getattr__("debug");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("Channel read message %s"), var1.getlocal(2), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0).__getattr__("sock")})};
      String[] var4 = new String[]{"extra"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(600);
      var1.getlocal(2).__getattr__("retain").__call__(var2);
      var1.setline(601);
      var1.getlocal(0).__getattr__("sock").__getattr__("incoming").__getattr__("put").__call__(var2, var1.getlocal(2));
      var1.setline(602);
      var1.getlocal(0).__getattr__("sock").__getattr__("_notify_selectors").__call__(var2);
      var1.setline(603);
      var1.getlocal(1).__getattr__("fireChannelRead").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject channelWritabilityChanged$54(PyFrame var1, ThreadState var2) {
      var1.setline(606);
      PyObject var10000 = var1.getglobal("log").__getattr__("debug");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("Channel ready for write"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0).__getattr__("sock")})};
      String[] var4 = new String[]{"extra"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(607);
      var1.getlocal(0).__getattr__("sock").__getattr__("_notify_selectors").__call__(var2);
      var1.setline(608);
      var1.getlocal(1).__getattr__("fireChannelWritabilityChanged").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject exceptionCaught$55(PyFrame var1, ThreadState var2) {
      var1.setline(611);
      PyObject var10000 = var1.getglobal("log").__getattr__("debug");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("Channel caught exception %s"), var1.getlocal(2), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0).__getattr__("sock")})};
      String[] var4 = new String[]{"extra"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(612);
      var10000 = var1.getlocal(0).__getattr__("sock").__getattr__("_notify_selectors");
      var3 = new PyObject[]{var1.getlocal(2)};
      var4 = new String[]{"exception"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ChildSocketHandler$56(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(617);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$57, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(620);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, initChannel$58, (PyObject)null);
      var1.setlocal("initChannel", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$57(PyFrame var1, ThreadState var2) {
      var1.setline(618);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("parent_socket", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject initChannel$58(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.f_exits = new PyObject[1];
      var1.setline(621);
      PyObject var3 = var1.getglobal("ChildSocket").__call__(var2, var1.getderef(0).__getattr__("parent_socket"));
      var1.setderef(1, var3);
      var3 = null;
      var1.setline(622);
      PyObject var10000 = var1.getglobal("log").__getattr__("debug");
      PyObject[] var8 = new PyObject[]{PyString.fromInterned("Initializing child %s"), var1.getderef(1), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getderef(0).__getattr__("parent_socket")})};
      String[] var4 = new String[]{"extra"};
      var10000.__call__(var2, var8, var4);
      var3 = null;
      var1.setline(624);
      var3 = var1.getglobal("IPPROTO_TCP");
      var1.getderef(1).__setattr__("proto", var3);
      var3 = null;
      var1.setline(625);
      var1.getderef(1).__getattr__("_init_client_mode").__call__(var2, var1.getlocal(1));
      var1.setline(632);
      var10000 = var1.getglobal("dict");
      var1.setline(632);
      var8 = Py.EmptyObjects;
      PyFunction var9 = new PyFunction(var1.f_globals, var8, f$59, (PyObject)null);
      PyObject var10002 = var9.__call__(var2, var1.getderef(0).__getattr__("parent_socket").__getattr__("options").__getattr__("iteritems").__call__(var2).__iter__());
      Arrays.fill(var8, (Object)null);
      var3 = var10000.__call__(var2, var10002);
      var1.getderef(1).__setattr__("options", var3);
      var3 = null;
      var1.setline(633);
      PyObject var10;
      if (var1.getderef(1).__getattr__("options").__nonzero__()) {
         var1.setline(634);
         var10000 = var1.getglobal("log").__getattr__("debug");
         var8 = new PyObject[]{PyString.fromInterned("Setting inherited options %s"), var1.getderef(1).__getattr__("options"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getderef(1)})};
         var4 = new String[]{"extra"};
         var10000.__call__(var2, var8, var4);
         var3 = null;
         var1.setline(635);
         var3 = var1.getlocal(1).__getattr__("config").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(636);
         var3 = var1.getderef(1).__getattr__("options").__getattr__("iteritems").__call__(var2).__iter__();

         while(true) {
            var1.setline(636);
            var10 = var3.__iternext__();
            if (var10 == null) {
               break;
            }

            PyObject[] var5 = Py.unpackSequence(var10, 2);
            PyObject var6 = var5[0];
            var1.setlocal(4, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(5, var6);
            var6 = null;
            var1.setline(637);
            var1.getglobal("_set_option").__call__(var2, var1.getlocal(3).__getattr__("setOption"), var1.getlocal(4), var1.getlocal(5));
         }
      }

      var1.setline(642);
      var1.getlocal(1).__getattr__("closeFuture").__call__(var2).__getattr__("addListener").__call__(var2, var1.getderef(1).__getattr__("_make_active"));
      var1.setline(646);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getderef(0).__getattr__("parent_socket"), (PyObject)PyString.fromInterned("ssl_wrap_child_socket")).__nonzero__()) {
         var1.setline(647);
         var10000 = var1.getglobal("log").__getattr__("debug");
         var8 = new PyObject[]{PyString.fromInterned("Wrapping child socket for a wrapped parent=%s"), var1.getderef(0).__getattr__("parent_socket"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getderef(0)})};
         var4 = new String[]{"extra"};
         var10000.__call__(var2, var8, var4);
         var3 = null;
         var1.setline(648);
         var3 = var1.getderef(0).__getattr__("parent_socket").__getattr__("ssl_wrap_child_socket").__call__(var2, var1.getderef(1));
         var1.getderef(1).__setattr__("_wrapper_socket", var3);
         var3 = null;
         var1.setline(649);
         var1.getderef(1).__getattr__("_handshake_future").__getattr__("sync").__call__(var2);
         var1.setline(650);
         var1.getderef(1).__getattr__("_post_connect").__call__(var2);
         var1.setline(651);
         var1.getderef(0).__getattr__("parent_socket").__getattr__("child_queue").__getattr__("put").__call__(var2, var1.getderef(1));
         var1.setline(652);
         var10000 = var1.getglobal("log").__getattr__("debug");
         var8 = new PyObject[]{PyString.fromInterned("Notifing listeners of parent socket %s"), var1.getderef(0).__getattr__("parent_socket"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getderef(1)})};
         var4 = new String[]{"extra"};
         var10000.__call__(var2, var8, var4);
         var3 = null;
         var1.setline(653);
         var1.getderef(0).__getattr__("parent_socket").__getattr__("_notify_selectors").__call__(var2);
         var1.setline(654);
         var10000 = var1.getglobal("log").__getattr__("debug");
         var8 = new PyObject[]{PyString.fromInterned("Notified listeners of parent socket %s with queue %s"), var1.getderef(0).__getattr__("parent_socket"), var1.getderef(0).__getattr__("parent_socket").__getattr__("child_queue"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getderef(1)})};
         var4 = new String[]{"extra"};
         var10000.__call__(var2, var8, var4);
         var3 = null;
         var1.setline(656);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         ContextManager var13;
         var10 = (var13 = ContextGuard.getManager(var1.getderef(1).__getattr__("_activation_cv"))).__enter__(var2);

         label34: {
            try {
               var1.setline(665);
               PyObject[] var12 = Py.EmptyObjects;
               var10002 = var1.f_globals;
               PyObject[] var10003 = var12;
               PyCode var10004 = wait_for_barrier$60;
               var12 = new PyObject[]{var1.getclosure(1), var1.getclosure(0)};
               var9 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var12);
               var1.setlocal(6, var9);
               var4 = null;
               var1.setline(672);
               var1.getderef(0).__getattr__("parent_socket").__getattr__("parent_group").__getattr__("submit").__call__(var2, var1.getlocal(6));

               while(true) {
                  var1.setline(673);
                  if (!var1.getderef(1).__getattr__("_activated").__not__().__nonzero__()) {
                     break;
                  }

                  var1.setline(674);
                  var10000 = var1.getglobal("log").__getattr__("debug");
                  var12 = new PyObject[]{PyString.fromInterned("Waiting for optional wrapping"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getderef(1)})};
                  String[] var11 = new String[]{"extra"};
                  var10000.__call__(var2, var12, var11);
                  var4 = null;
                  var1.setline(675);
                  var1.getderef(1).__getattr__("_activation_cv").__getattr__("wait").__call__(var2);
               }
            } catch (Throwable var7) {
               if (var13.__exit__(var2, Py.setException(var7, var1))) {
                  break label34;
               }

               throw (Throwable)Py.makeException();
            }

            var13.__exit__(var2, (PyException)null);
         }

         var1.setline(677);
         var10000 = var1.getglobal("log").__getattr__("debug");
         var8 = new PyObject[]{PyString.fromInterned("Completed waiting for optional wrapping"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getderef(1)})};
         var4 = new String[]{"extra"};
         var10000.__call__(var2, var8, var4);
         var3 = null;
         var1.setline(678);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getderef(1), (PyObject)PyString.fromInterned("ssl_wrap_self")).__nonzero__()) {
            var1.setline(679);
            var10000 = var1.getglobal("log").__getattr__("debug");
            var8 = new PyObject[]{PyString.fromInterned("Wrapping self"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getderef(1)})};
            var4 = new String[]{"extra"};
            var10000.__call__(var2, var8, var4);
            var3 = null;
            var1.setline(680);
            var1.getderef(1).__getattr__("ssl_wrap_self").__call__(var2);
         }

         var1.setline(681);
         var10000 = var1.getglobal("log").__getattr__("debug");
         var8 = new PyObject[]{PyString.fromInterned("Activating child socket by adding inbound handler"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getderef(1)})};
         var4 = new String[]{"extra"};
         var10000.__call__(var2, var8, var4);
         var3 = null;
         var1.setline(682);
         var1.getderef(1).__getattr__("_post_connect").__call__(var2);
         var1.setline(683);
         var3 = var1.getglobal("True");
         var1.getderef(1).__setattr__("_channel_is_initialized", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject f$59(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(632);
            var3 = var1.getlocal(0).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            PyObject var8 = (PyObject)var10000;
      }

      var1.setline(632);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         PyObject[] var7 = Py.unpackSequence(var4, 2);
         PyObject var6 = var7[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var7[1];
         var1.setlocal(2, var6);
         var6 = null;
         var1.setline(632);
         var1.setline(632);
         var7 = new PyObject[]{var1.getlocal(1), var1.getlocal(2)};
         PyTuple var9 = new PyTuple(var7);
         Arrays.fill(var7, (Object)null);
         var1.f_lasti = 1;
         var5 = new Object[7];
         var5[3] = var3;
         var5[4] = var4;
         var1.f_savedlocals = var5;
         return var9;
      }
   }

   public PyObject wait_for_barrier$60(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      ContextManager var3;
      PyObject var4 = (var3 = ContextGuard.getManager(var1.getderef(0).__getattr__("_activation_cv"))).__enter__(var2);

      label16: {
         try {
            var1.setline(667);
            var1.getderef(1).__getattr__("parent_socket").__getattr__("child_queue").__getattr__("put").__call__(var2, var1.getderef(0));
            var1.setline(668);
            PyObject var10000 = var1.getglobal("log").__getattr__("debug");
            PyObject[] var7 = new PyObject[]{PyString.fromInterned("Notifing listeners of parent socket %s"), var1.getderef(1).__getattr__("parent_socket"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getderef(0)})};
            String[] var5 = new String[]{"extra"};
            var10000.__call__(var2, var7, var5);
            var4 = null;
            var1.setline(669);
            var1.getderef(1).__getattr__("parent_socket").__getattr__("_notify_selectors").__call__(var2);
            var1.setline(670);
            var10000 = var1.getglobal("log").__getattr__("debug");
            var7 = new PyObject[]{PyString.fromInterned("Notified listeners of parent socket %s with queue %s"), var1.getderef(1).__getattr__("parent_socket"), var1.getderef(1).__getattr__("parent_socket").__getattr__("child_queue"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getderef(0)})};
            var5 = new String[]{"extra"};
            var10000.__call__(var2, var7, var5);
            var4 = null;
         } catch (Throwable var6) {
            if (var3.__exit__(var2, Py.setException(var6, var1))) {
               break label16;
            }

            throw (Throwable)Py.makeException();
         }

         var3.__exit__(var2, (PyException)null);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _identity$61(PyFrame var1, ThreadState var2) {
      var1.setline(697);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _set_option$62(PyFrame var1, ThreadState var2) {
      var1.setline(701);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("ChannelOption").__getattr__("SO_LINGER"), var1.getglobal("ChannelOption").__getattr__("SO_TIMEOUT")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(712);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(714);
         var1.getlocal(0).__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _socktuple$63(PyFrame var1, ThreadState var2) {
      var1.setline(739);
      PyObject var3 = var1.getlocal(0).__getattr__("getPort").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(740);
      var3 = var1.getlocal(0).__getattr__("getAddress").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(741);
      PyTuple var4;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("java").__getattr__("net").__getattr__("Inet6Address")).__nonzero__()) {
         var1.setline(742);
         var4 = new PyTuple(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(2).__getattr__("getHostAddress").__call__(var2)), var1.getlocal(1), Py.newInteger(0), var1.getlocal(2).__getattr__("getScopeId").__call__(var2)});
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(744);
         var4 = new PyTuple(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(2).__getattr__("getHostAddress").__call__(var2)), var1.getlocal(1)});
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject _realsocket$64(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(751);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), Py.newInteger(0)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$65, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(786);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$66, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(790);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _make_active$67, (PyObject)null);
      var1.setlocal("_make_active", var4);
      var3 = null;
      var1.setline(793);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _register_selector$68, (PyObject)null);
      var1.setlocal("_register_selector", var4);
      var3 = null;
      var1.setline(797);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _unregister_selector$69, (PyObject)null);
      var1.setlocal("_unregister_selector", var4);
      var3 = null;
      var1.setline(805);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, _notify_selectors$70, (PyObject)null);
      var1.setlocal("_notify_selectors", var4);
      var3 = null;
      var1.setline(809);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _handle_channel_future$71, (PyObject)null);
      PyObject var5 = var1.getname("raises_java_exception").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("_handle_channel_future", var5);
      var3 = null;
      var1.setline(828);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setblocking$72, (PyObject)null);
      var1.setlocal("setblocking", var4);
      var3 = null;
      var1.setline(834);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, settimeout$73, (PyObject)null);
      var1.setlocal("settimeout", var4);
      var3 = null;
      var1.setline(837);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, gettimeout$74, (PyObject)null);
      var1.setlocal("gettimeout", var4);
      var3 = null;
      var1.setline(840);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _handle_timeout$75, (PyObject)null);
      var1.setlocal("_handle_timeout", var4);
      var3 = null;
      var1.setline(857);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, bind$76, (PyObject)null);
      var1.setlocal("bind", var4);
      var3 = null;
      var1.setline(867);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, _init_client_mode$77, (PyObject)null);
      var1.setlocal("_init_client_mode", var4);
      var3 = null;
      var1.setline(883);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _connect$78, (PyObject)null);
      var1.setlocal("_connect", var4);
      var3 = null;
      var1.setline(913);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _post_connect$79, (PyObject)null);
      var1.setlocal("_post_connect", var4);
      var3 = null;
      var1.setline(929);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, connect$81, (PyObject)null);
      var1.setlocal("connect", var4);
      var3 = null;
      var1.setline(939);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, connect_ex$82, (PyObject)null);
      var1.setlocal("connect_ex", var4);
      var3 = null;
      var1.setline(980);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, listen$83, (PyObject)null);
      var5 = var1.getname("raises_java_exception").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("listen", var5);
      var3 = null;
      var1.setline(1009);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, accept$84, (PyObject)null);
      var1.setlocal("accept", var4);
      var3 = null;
      var1.setline(1030);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, _datagram_connect$85, (PyObject)null);
      var1.setlocal("_datagram_connect", var4);
      var3 = null;
      var1.setline(1057);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, sendto$86, (PyObject)null);
      var1.setlocal("sendto", var4);
      var3 = null;
      var1.setline(1075);
      var3 = new PyObject[]{Py.newInteger(0), Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, recvfrom_into$87, (PyObject)null);
      var1.setlocal("recvfrom_into", var4);
      var3 = null;
      var1.setline(1082);
      var3 = new PyObject[]{Py.newInteger(0), Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, recv_into$88, (PyObject)null);
      var1.setlocal("recv_into", var4);
      var3 = null;
      var1.setline(1091);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$89, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(1104);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _finish_closing$90, (PyObject)null);
      var1.setlocal("_finish_closing", var4);
      var3 = null;
      var1.setline(1124);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, shutdown$91, (PyObject)null);
      var1.setlocal("shutdown", var4);
      var3 = null;
      var1.setline(1137);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _readable$92, (PyObject)null);
      var1.setlocal("_readable", var4);
      var3 = null;
      var1.setline(1148);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _pending$93, (PyObject)null);
      var1.setlocal("_pending", var4);
      var3 = null;
      var1.setline(1165);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _writable$94, (PyObject)null);
      var1.setlocal("_writable", var4);
      var3 = null;
      var1.setline(1168);
      var5 = var1.getname("_writable");
      var1.setlocal("can_write", var5);
      var3 = null;
      var1.setline(1170);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _verify_channel$95, (PyObject)null);
      var1.setlocal("_verify_channel", var4);
      var3 = null;
      var1.setline(1175);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, send$96, (PyObject)null);
      var5 = var1.getname("raises_java_exception").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("send", var5);
      var3 = null;
      var1.setline(1205);
      var5 = var1.getname("send");
      var1.setlocal("sendall", var5);
      var3 = null;
      var1.setline(1207);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get_incoming_msg$97, (PyObject)null);
      var1.setlocal("_get_incoming_msg", var4);
      var3 = null;
      var1.setline(1232);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get_message$98, (PyObject)null);
      var5 = var1.getname("raises_java_exception").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("_get_message", var5);
      var3 = null;
      var1.setline(1263);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, recv$99, (PyObject)null);
      var1.setlocal("recv", var4);
      var3 = null;
      var1.setline(1274);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, recvfrom$100, (PyObject)null);
      var1.setlocal("recvfrom", var4);
      var3 = null;
      var1.setline(1281);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, fileno$101, (PyObject)null);
      var1.setlocal("fileno", var4);
      var3 = null;
      var1.setline(1284);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setsockopt$102, (PyObject)null);
      var5 = var1.getname("raises_java_exception").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("setsockopt", var5);
      var3 = null;
      var1.setline(1297);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, getsockopt$103, (PyObject)null);
      var5 = var1.getname("raises_java_exception").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("getsockopt", var5);
      var3 = null;
      var1.setline(1323);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getsockname$104, (PyObject)null);
      var1.setlocal("getsockname", var4);
      var3 = null;
      var1.setline(1342);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getpeername$105, (PyObject)null);
      var1.setlocal("getpeername", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$65(PyFrame var1, ThreadState var2) {
      var1.setline(753);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("family", var3);
      var3 = null;
      var1.setline(754);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("type", var3);
      var3 = null;
      var1.setline(755);
      PyObject var10000;
      if (var1.getlocal(3).__not__().__nonzero__()) {
         var1.setline(756);
         var3 = var1.getlocal(2);
         var10000 = var3._eq(var1.getglobal("SOCK_STREAM"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(757);
            var3 = var1.getglobal("IPPROTO_TCP");
            var1.setlocal(3, var3);
            var3 = null;
         } else {
            var1.setline(758);
            var3 = var1.getlocal(2);
            var10000 = var3._eq(var1.getglobal("SOCK_DGRAM"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(759);
               var3 = var1.getglobal("IPPROTO_UDP");
               var1.setlocal(3, var3);
               var3 = null;
            }
         }
      }

      var1.setline(760);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("proto", var3);
      var3 = null;
      var1.setline(762);
      var3 = var1.getlocal(0);
      var1.getlocal(0).__setattr__("_sock", var3);
      var3 = null;
      var1.setline(763);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_last_error", var4);
      var3 = null;
      var1.setline(764);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("connected", var3);
      var3 = null;
      var1.setline(765);
      var3 = var1.getglobal("_defaulttimeout");
      var1.getlocal(0).__setattr__("timeout", var3);
      var3 = null;
      var1.setline(766);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("channel", var3);
      var3 = null;
      var1.setline(767);
      var3 = var1.getglobal("_EPHEMERAL_ADDRESS");
      var1.getlocal(0).__setattr__("bind_addr", var3);
      var3 = null;
      var1.setline(768);
      var3 = var1.getglobal("CopyOnWriteArrayList").__call__(var2);
      var1.getlocal(0).__setattr__("selectors", var3);
      var3 = null;
      var1.setline(769);
      PyDictionary var5 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"options", var5);
      var3 = null;
      var1.setline(770);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("peer_closed", var3);
      var3 = null;
      var1.setline(771);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("channel_closed", var3);
      var3 = null;
      var1.setline(774);
      var3 = var1.getglobal("Lock").__call__(var2);
      var1.getlocal(0).__setattr__("open_lock", var3);
      var3 = null;
      var1.setline(775);
      var4 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"open_count", var4);
      var3 = null;
      var1.setline(777);
      var3 = var1.getlocal(0).__getattr__("type");
      var10000 = var3._eq(var1.getglobal("SOCK_DGRAM"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(778);
         var3 = var1.getglobal("DATAGRAM_SOCKET");
         var1.getlocal(0).__setattr__("socket_type", var3);
         var3 = null;
         var1.setline(779);
         var3 = var1.getglobal("LinkedBlockingQueue").__call__(var2);
         var1.getlocal(0).__setattr__("incoming", var3);
         var3 = null;
         var1.setline(780);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("incoming_head", var3);
         var3 = null;
         var1.setline(781);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("python_inbound_handler", var3);
         var3 = null;
         var1.setline(782);
         var3 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("_can_write", var3);
         var3 = null;
      } else {
         var1.setline(784);
         var3 = var1.getglobal("UNKNOWN_SOCKET");
         var1.getlocal(0).__setattr__("socket_type", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$66(PyFrame var1, ThreadState var2) {
      var1.setline(787);
      PyObject var10000 = PyString.fromInterned("<_realsocket at {:#x} type={} open_count={} channel={} timeout={}>").__getattr__("format");
      PyObject[] var3 = new PyObject[]{var1.getglobal("id").__call__(var2, var1.getlocal(0)), var1.getglobal("_socket_types").__getitem__(var1.getlocal(0).__getattr__("socket_type")), var1.getlocal(0).__getattr__("open_count"), var1.getlocal(0).__getattr__("channel"), var1.getlocal(0).__getattr__("timeout")};
      PyObject var4 = var10000.__call__(var2, var3);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject _make_active$67(PyFrame var1, ThreadState var2) {
      var1.setline(791);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _register_selector$68(PyFrame var1, ThreadState var2) {
      var1.setline(794);
      var1.getlocal(0).__getattr__("_make_active").__call__(var2);
      var1.setline(795);
      var1.getlocal(0).__getattr__("selectors").__getattr__("addIfAbsent").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _unregister_selector$69(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(799);
         var3 = var1.getlocal(0).__getattr__("selectors").__getattr__("remove").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("ValueError"))) {
            var1.setline(801);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         } else if (var4.match(var1.getglobal("ArrayIndexOutOfBoundsException"))) {
            var1.setline(803);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject _notify_selectors$70(PyFrame var1, ThreadState var2) {
      var1.setline(806);
      PyObject var3 = var1.getlocal(0).__getattr__("selectors").__iter__();

      while(true) {
         var1.setline(806);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(807);
         PyObject var10000 = var1.getlocal(3).__getattr__("notify");
         PyObject[] var5 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)};
         String[] var6 = new String[]{"exception", "hangup"};
         var10000.__call__(var2, var5, var6);
         var5 = null;
      }
   }

   public PyObject _handle_channel_future$71(PyFrame var1, ThreadState var2) {
      var1.setline(815);
      var1.getlocal(1).__getattr__("addListener").__call__(var2, var1.getlocal(0).__getattr__("_notify_selectors"));
      var1.setline(816);
      PyObject var3 = var1.getlocal(0).__getattr__("timeout");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(817);
         var10000 = var1.getglobal("log").__getattr__("debug");
         PyObject[] var6 = new PyObject[]{PyString.fromInterned("Syncing on future %s for %s"), var1.getlocal(1), var1.getlocal(2), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
         String[] var7 = new String[]{"extra"};
         var10000.__call__(var2, var6, var7);
         var3 = null;
         var1.setline(818);
         var3 = var1.getlocal(1).__getattr__("sync").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(819);
         if (var1.getlocal(0).__getattr__("timeout").__nonzero__()) {
            var1.setline(820);
            var1.getlocal(0).__getattr__("_handle_timeout").__call__(var2, var1.getlocal(1).__getattr__("await"), var1.getlocal(2));
            var1.setline(821);
            if (var1.getlocal(1).__getattr__("isSuccess").__call__(var2).__not__().__nonzero__()) {
               var1.setline(822);
               var10000 = var1.getglobal("log").__getattr__("debug");
               PyObject[] var4 = new PyObject[]{PyString.fromInterned("Got this failure %s during %s"), var1.getlocal(1).__getattr__("cause").__call__(var2), var1.getlocal(2), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
               String[] var5 = new String[]{"extra"};
               var10000.__call__(var2, var4, var5);
               var4 = null;
               var1.setline(823);
               throw Py.makeException(var1.getlocal(1).__getattr__("cause").__call__(var2));
            } else {
               var1.setline(824);
               var3 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var3;
            }
         } else {
            var1.setline(826);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject setblocking$72(PyFrame var1, ThreadState var2) {
      var1.setline(829);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(830);
         var1.getlocal(0).__getattr__("settimeout").__call__(var2, var1.getglobal("None"));
      } else {
         var1.setline(832);
         var1.getlocal(0).__getattr__("settimeout").__call__((ThreadState)var2, (PyObject)Py.newFloat(0.0));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject settimeout$73(PyFrame var1, ThreadState var2) {
      var1.setline(835);
      PyObject var3 = var1.getglobal("_calctimeoutvalue").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("timeout", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject gettimeout$74(PyFrame var1, ThreadState var2) {
      var1.setline(838);
      PyObject var3 = var1.getlocal(0).__getattr__("timeout");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _handle_timeout$75(PyFrame var1, ThreadState var2) {
      var1.setline(841);
      PyObject var3 = var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("timeout")._mul(var1.getglobal("_TO_NANOSECONDS")));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(842);
      PyObject var10000 = var1.getglobal("log").__getattr__("debug");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("Waiting for up to %.2fs for %s"), var1.getlocal(0).__getattr__("timeout"), var1.getlocal(2), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
      String[] var4 = new String[]{"extra"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(843);
      var3 = var1.getglobal("time").__getattr__("time").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(844);
      var3 = var1.getlocal(1).__call__(var2, var1.getlocal(3), var1.getglobal("TimeUnit").__getattr__("NANOSECONDS"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(845);
      var10000 = var1.getglobal("log").__getattr__("debug");
      var5 = new PyObject[]{PyString.fromInterned("Completed in %.2fs"), var1.getglobal("time").__getattr__("time").__call__(var2)._sub(var1.getlocal(4)), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
      var4 = new String[]{"extra"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(846);
      if (var1.getlocal(5).__not__().__nonzero__()) {
         var1.setline(851);
         var3 = var1.getlocal(0).__getattr__("timeout");
         var10000 = var3._eq(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(852);
            throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("ETIMEDOUT"), (PyObject)PyString.fromInterned("Connection timed out")));
         } else {
            var1.setline(854);
            throw Py.makeException(var1.getglobal("timeout").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("ETIMEDOUT"), (PyObject)PyString.fromInterned("timed out")));
         }
      } else {
         var1.setline(855);
         var3 = var1.getlocal(5);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject bind$76(PyFrame var1, ThreadState var2) {
      var1.setline(860);
      PyObject var10000 = var1.getglobal("_get_jsockaddr");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("family"), var1.getlocal(0).__getattr__("type"), var1.getlocal(0).__getattr__("proto"), var1.getglobal("AI_PASSIVE")};
      PyObject var4 = var10000.__call__(var2, var3);
      var1.getlocal(0).__setattr__("bind_addr", var4);
      var3 = null;
      var1.setline(861);
      var1.getlocal(0).__getattr__("_datagram_connect").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _init_client_mode$77(PyFrame var1, ThreadState var2) {
      var1.setline(869);
      PyObject var3 = var1.getglobal("CLIENT_SOCKET");
      var1.getlocal(0).__setattr__("socket_type", var3);
      var3 = null;
      var1.setline(870);
      var3 = var1.getglobal("LinkedBlockingQueue").__call__(var2);
      var1.getlocal(0).__setattr__("incoming", var3);
      var3 = null;
      var1.setline(871);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("incoming_head", var3);
      var3 = null;
      var1.setline(872);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("python_inbound_handler", var3);
      var3 = null;
      var1.setline(873);
      var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("_can_write", var3);
      var3 = null;
      var1.setline(874);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"connect_handlers", var5);
      var3 = null;
      var1.setline(875);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("connected", var3);
      var3 = null;
      var1.setline(876);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(877);
         PyObject var10000 = var1.getglobal("log").__getattr__("debug");
         PyObject[] var6 = new PyObject[]{PyString.fromInterned("Setting up channel %s"), var1.getlocal(1), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
         String[] var4 = new String[]{"extra"};
         var10000.__call__(var2, var6, var4);
         var3 = null;
         var1.setline(878);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("channel", var3);
         var3 = null;
         var1.setline(879);
         var3 = var1.getglobal("PythonInboundHandler").__call__(var2, var1.getlocal(0));
         var1.getlocal(0).__setattr__("python_inbound_handler", var3);
         var3 = null;
         var1.setline(880);
         var5 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("python_inbound_handler")});
         var1.getlocal(0).__setattr__((String)"connect_handlers", var5);
         var3 = null;
         var1.setline(881);
         var3 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("connected", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _connect$78(PyFrame var1, ThreadState var2) {
      var1.setline(884);
      PyObject var10000 = var1.getglobal("log").__getattr__("debug");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("Begin connection to %s"), var1.getlocal(1), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
      String[] var4 = new String[]{"extra"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(885);
      var10000 = var1.getglobal("_get_jsockaddr");
      var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("family"), var1.getlocal(0).__getattr__("type"), var1.getlocal(0).__getattr__("proto"), Py.newInteger(0)};
      PyObject var7 = var10000.__call__(var2, var3);
      var1.setlocal(1, var7);
      var3 = null;
      var1.setline(886);
      var1.getlocal(0).__getattr__("_init_client_mode").__call__(var2);
      var1.setline(887);
      var7 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("connected", var7);
      var3 = null;
      var1.setline(888);
      var7 = var1.getglobal("PythonInboundHandler").__call__(var2, var1.getlocal(0));
      var1.getlocal(0).__setattr__("python_inbound_handler", var7);
      var3 = null;
      var1.setline(889);
      var7 = var1.getglobal("Bootstrap").__call__(var2).__getattr__("group").__call__(var2, var1.getglobal("NIO_GROUP")).__getattr__("channel").__call__(var2, var1.getglobal("NioSocketChannel"));
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(890);
      var7 = var1.getlocal(0).__getattr__("options").__getattr__("iteritems").__call__(var2).__iter__();

      while(true) {
         var1.setline(890);
         PyObject var8 = var7.__iternext__();
         if (var8 == null) {
            var1.setline(895);
            if (var1.getlocal(0).__getattr__("connect_handlers").__nonzero__()) {
               var1.setline(896);
               var7 = var1.getlocal(0).__getattr__("connect_handlers").__iter__();

               while(true) {
                  var1.setline(896);
                  var8 = var7.__iternext__();
                  if (var8 == null) {
                     break;
                  }

                  var1.setlocal(5, var8);
                  var1.setline(897);
                  var1.getlocal(2).__getattr__("handler").__call__(var2, var1.getlocal(5));
               }
            } else {
               var1.setline(899);
               var1.getlocal(2).__getattr__("handler").__call__(var2, var1.getlocal(0).__getattr__("python_inbound_handler"));
            }

            var1.setline(901);
            if (var1.getlocal(0).__getattr__("bind_addr").__nonzero__()) {
               var1.setline(902);
               var10000 = var1.getglobal("log").__getattr__("debug");
               var3 = new PyObject[]{PyString.fromInterned("Connect %s to %s"), var1.getlocal(0).__getattr__("bind_addr"), var1.getlocal(1), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
               var4 = new String[]{"extra"};
               var10000.__call__(var2, var3, var4);
               var3 = null;
               var1.setline(903);
               var7 = var1.getlocal(2).__getattr__("bind").__call__(var2, var1.getlocal(0).__getattr__("bind_addr")).__getattr__("sync").__call__(var2);
               var1.setlocal(6, var7);
               var3 = null;
               var1.setline(904);
               var1.getlocal(0).__getattr__("_handle_channel_future").__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)PyString.fromInterned("local bind"));
               var1.setline(905);
               var7 = var1.getlocal(6).__getattr__("channel").__call__(var2);
               var1.getlocal(0).__setattr__("channel", var7);
               var3 = null;
            } else {
               var1.setline(907);
               var10000 = var1.getglobal("log").__getattr__("debug");
               var3 = new PyObject[]{PyString.fromInterned("Connect to %s"), var1.getlocal(1), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
               var4 = new String[]{"extra"};
               var10000.__call__(var2, var3, var4);
               var3 = null;
               var1.setline(908);
               var7 = var1.getlocal(2).__getattr__("channel").__call__(var2);
               var1.getlocal(0).__setattr__("channel", var7);
               var3 = null;
            }

            var1.setline(910);
            var7 = var1.getlocal(0).__getattr__("channel").__getattr__("connect").__call__(var2, var1.getlocal(1));
            var1.getlocal(0).__setattr__("connect_future", var7);
            var3 = null;
            var1.setline(911);
            var1.getlocal(0).__getattr__("_handle_channel_future").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("connect_future"), (PyObject)PyString.fromInterned("connect"));
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var8, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(891);
         var1.getglobal("_set_option").__call__(var2, var1.getlocal(2).__getattr__("option"), var1.getlocal(3), var1.getlocal(4));
      }
   }

   public PyObject _post_connect$79(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(917);
      if (var1.getderef(0).__getattr__("connect_handlers").__nonzero__()) {
         var1.setline(918);
         var1.getderef(0).__getattr__("channel").__getattr__("pipeline").__call__(var2).__getattr__("addLast").__call__(var2, var1.getderef(0).__getattr__("python_inbound_handler"));
      }

      var1.setline(920);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = _peer_closed$80;
      var3 = new PyObject[]{var1.getclosure(0)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(926);
      PyObject var10000 = var1.getglobal("log").__getattr__("debug");
      var3 = new PyObject[]{PyString.fromInterned("Add _peer_closed to channel close"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getderef(0)})};
      String[] var4 = new String[]{"extra"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(927);
      var1.getderef(0).__getattr__("channel").__getattr__("closeFuture").__call__(var2).__getattr__("addListener").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _peer_closed$80(PyFrame var1, ThreadState var2) {
      var1.setline(921);
      PyObject var10000 = var1.getglobal("log").__getattr__("debug");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("Peer closed channel %s"), var1.getlocal(0), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getderef(0)})};
      String[] var4 = new String[]{"extra"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(922);
      PyObject var5 = var1.getglobal("True");
      var1.getderef(0).__setattr__("channel_closed", var5);
      var3 = null;
      var1.setline(923);
      var1.getderef(0).__getattr__("incoming").__getattr__("put").__call__(var2, var1.getglobal("_PEER_CLOSED"));
      var1.setline(924);
      var10000 = var1.getderef(0).__getattr__("_notify_selectors");
      var3 = new PyObject[]{var1.getglobal("True")};
      var4 = new String[]{"hangup"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject connect$81(PyFrame var1, ThreadState var2) {
      var1.setline(931);
      PyObject var3 = var1.getlocal(0).__getattr__("socket_type");
      PyObject var10000 = var3._eq(var1.getglobal("DATAGRAM_SOCKET"));
      var3 = null;
      String[] var4;
      PyObject[] var5;
      if (var10000.__nonzero__()) {
         var1.setline(932);
         var1.getlocal(0).__getattr__("_datagram_connect").__call__(var2, var1.getlocal(1));
         var1.setline(933);
         var10000 = var1.getglobal("log").__getattr__("debug");
         var5 = new PyObject[]{PyString.fromInterned("Completed datagram connection to %s"), var1.getlocal(1), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
         var4 = new String[]{"extra"};
         var10000.__call__(var2, var5, var4);
         var3 = null;
      } else {
         var1.setline(935);
         var1.getlocal(0).__getattr__("_connect").__call__(var2, var1.getlocal(1));
         var1.setline(936);
         var1.getlocal(0).__getattr__("_post_connect").__call__(var2);
         var1.setline(937);
         var10000 = var1.getglobal("log").__getattr__("debug");
         var5 = new PyObject[]{PyString.fromInterned("Completed connection to %s"), var1.getlocal(1), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
         var4 = new String[]{"extra"};
         var10000.__call__(var2, var5, var4);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject connect_ex$82(PyFrame var1, ThreadState var2) {
      var1.setline(940);
      PyObject var3 = var1.getlocal(0).__getattr__("connected");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(942);
      PyObject var4;
      PyException var7;
      if (var1.getlocal(0).__getattr__("connected").__not__().__nonzero__()) {
         try {
            var1.setline(944);
            var1.getlocal(0).__getattr__("connect").__call__(var2, var1.getlocal(1));
         } catch (Throwable var5) {
            var7 = Py.setException(var5, var1);
            if (var7.match(var1.getglobal("error"))) {
               var4 = var7.value;
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(946);
               var4 = var1.getlocal(3).__getattr__("errno");
               var1.f_lasti = -1;
               return var4;
            }

            throw var7;
         }
      }

      var1.setline(947);
      if (var1.getlocal(0).__getattr__("connect_future").__getattr__("isDone").__call__(var2).__not__().__nonzero__()) {
         var1.setline(948);
         if (var1.getlocal(2).__nonzero__()) {
            try {
               var1.setline(955);
               var1.getlocal(0).__getattr__("connect_future").__getattr__("get").__call__((ThreadState)var2, (PyObject)Py.newInteger(1500), (PyObject)var1.getglobal("TimeUnit").__getattr__("MICROSECONDS"));
            } catch (Throwable var6) {
               var7 = Py.setException(var6, var1);
               if (var7.match(var1.getglobal("ExecutionException"))) {
                  var1.setline(959);
               } else {
                  if (!var7.match(var1.getglobal("TimeoutException"))) {
                     throw var7;
                  }

                  var1.setline(962);
               }
            }
         }
      }

      var1.setline(964);
      if (var1.getlocal(0).__getattr__("connect_future").__getattr__("isDone").__call__(var2).__not__().__nonzero__()) {
         var1.setline(965);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(966);
            var4 = var1.getglobal("errno").__getattr__("EALREADY");
            var1.f_lasti = -1;
            return var4;
         } else {
            var1.setline(968);
            var4 = var1.getglobal("errno").__getattr__("EINPROGRESS");
            var1.f_lasti = -1;
            return var4;
         }
      } else {
         var1.setline(969);
         if (var1.getlocal(0).__getattr__("connect_future").__getattr__("isSuccess").__call__(var2).__nonzero__()) {
            var1.setline(974);
            var4 = var1.getglobal("errno").__getattr__("EISCONN");
            var1.f_lasti = -1;
            return var4;
         } else {
            var1.setline(976);
            var4 = var1.getglobal("errno").__getattr__("ENOTCONN");
            var1.f_lasti = -1;
            return var4;
         }
      }
   }

   public PyObject listen$83(PyFrame var1, ThreadState var2) {
      var1.setline(982);
      PyObject var3 = var1.getglobal("SERVER_SOCKET");
      var1.getlocal(0).__setattr__("socket_type", var3);
      var3 = null;
      var1.setline(983);
      var3 = var1.getglobal("ArrayBlockingQueue").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("child_queue", var3);
      var3 = null;
      var1.setline(984);
      PyInteger var8 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"accepted_children", var8);
      var3 = null;
      var1.setline(986);
      var3 = var1.getglobal("ServerBootstrap").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;

      try {
         var1.setline(988);
         var3 = var1.getglobal("NioEventLoopGroup").__call__(var2, var1.getglobal("_NUM_THREADS"), var1.getglobal("DaemonThreadFactory").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Jython-Netty-Parent-%s")));
         var1.getlocal(0).__setattr__("parent_group", var3);
         var3 = null;
         var1.setline(989);
         var3 = var1.getglobal("NioEventLoopGroup").__call__(var2, var1.getglobal("_NUM_THREADS"), var1.getglobal("DaemonThreadFactory").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Jython-Netty-Child-%s")));
         var1.getlocal(0).__setattr__("child_group", var3);
         var3 = null;
      } catch (Throwable var7) {
         PyException var10 = Py.setException(var7, var1);
         if (var10.match(var1.getglobal("IllegalStateException"))) {
            var1.setline(991);
            throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("EMFILE"), (PyObject)PyString.fromInterned("Cannot allocate thread pool for server socket")));
         }

         throw var10;
      }

      var1.setline(992);
      var1.getlocal(2).__getattr__("group").__call__(var2, var1.getlocal(0).__getattr__("parent_group"), var1.getlocal(0).__getattr__("child_group"));
      var1.setline(993);
      var1.getlocal(2).__getattr__("channel").__call__(var2, var1.getglobal("NioServerSocketChannel"));
      var1.setline(994);
      var1.getlocal(2).__getattr__("option").__call__(var2, var1.getglobal("ChannelOption").__getattr__("SO_BACKLOG"), var1.getlocal(1));
      var1.setline(995);
      var3 = var1.getlocal(0).__getattr__("options").__getattr__("iteritems").__call__(var2).__iter__();

      while(true) {
         var1.setline(995);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1001);
            var3 = var1.getglobal("ChildSocketHandler").__call__(var2, var1.getlocal(0));
            var1.getlocal(0).__setattr__("child_handler", var3);
            var3 = null;
            var1.setline(1002);
            var1.getlocal(2).__getattr__("childHandler").__call__(var2, var1.getlocal(0).__getattr__("child_handler"));
            var1.setline(1004);
            var3 = var1.getlocal(2).__getattr__("bind").__call__(var2, var1.getlocal(0).__getattr__("bind_addr").__getattr__("getAddress").__call__(var2), var1.getlocal(0).__getattr__("bind_addr").__getattr__("getPort").__call__(var2));
            var1.getlocal(0).__setattr__("bind_future", var3);
            var3 = null;
            var1.setline(1005);
            var1.getlocal(0).__getattr__("_handle_channel_future").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("bind_future"), (PyObject)PyString.fromInterned("listen"));
            var1.setline(1006);
            var3 = var1.getlocal(0).__getattr__("bind_future").__getattr__("channel").__call__(var2);
            var1.getlocal(0).__setattr__("channel", var3);
            var3 = null;
            var1.setline(1007);
            PyObject var10000 = var1.getglobal("log").__getattr__("debug");
            PyObject[] var11 = new PyObject[]{PyString.fromInterned("Bound server socket to %s"), var1.getlocal(0).__getattr__("bind_addr"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
            String[] var9 = new String[]{"extra"};
            var10000.__call__(var2, var11, var9);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(996);
         var1.getglobal("_set_option").__call__(var2, var1.getlocal(2).__getattr__("option"), var1.getlocal(3), var1.getlocal(4));
      }
   }

   public PyObject accept$84(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(1010);
      PyObject var3 = var1.getlocal(0).__getattr__("timeout");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      String[] var4;
      PyObject[] var8;
      if (var10000.__nonzero__()) {
         var1.setline(1011);
         var10000 = var1.getglobal("log").__getattr__("debug");
         var8 = new PyObject[]{PyString.fromInterned("Blocking indefinitely for child on queue %s"), var1.getlocal(0).__getattr__("child_queue"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
         var4 = new String[]{"extra"};
         var10000.__call__(var2, var8, var4);
         var3 = null;
         var1.setline(1012);
         var3 = var1.getlocal(0).__getattr__("child_queue").__getattr__("take").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(1013);
         if (var1.getlocal(0).__getattr__("timeout").__nonzero__()) {
            var1.setline(1014);
            var10000 = var1.getglobal("log").__getattr__("debug");
            var8 = new PyObject[]{PyString.fromInterned("Timed wait for child on queue %s"), var1.getlocal(0).__getattr__("child_queue"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
            var4 = new String[]{"extra"};
            var10000.__call__(var2, var8, var4);
            var3 = null;
            var1.setline(1015);
            var3 = var1.getlocal(0).__getattr__("_handle_timeout").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("child_queue").__getattr__("poll"), (PyObject)PyString.fromInterned("accept"));
            var1.setlocal(1, var3);
            var3 = null;
         } else {
            var1.setline(1017);
            var10000 = var1.getglobal("log").__getattr__("debug");
            var8 = new PyObject[]{PyString.fromInterned("Polling for child on queue %s"), var1.getlocal(0).__getattr__("child_queue"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
            var4 = new String[]{"extra"};
            var10000.__call__(var2, var8, var4);
            var3 = null;
            var1.setline(1018);
            var3 = var1.getlocal(0).__getattr__("child_queue").__getattr__("poll").__call__(var2);
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(1019);
            var3 = var1.getlocal(1);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1020);
               throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("EWOULDBLOCK"), (PyObject)PyString.fromInterned("Resource temporarily unavailable")));
            }
         }
      }

      var1.setline(1021);
      var1.setline(1021);
      var3 = var1.getlocal(1).__nonzero__() ? var1.getlocal(1).__getattr__("getpeername").__call__(var2) : var1.getglobal("None");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1022);
      var10000 = var1.getglobal("log").__getattr__("debug");
      var8 = new PyObject[]{PyString.fromInterned("Got child %s connected to %s"), var1.getlocal(1), var1.getlocal(2), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
      var4 = new String[]{"extra"};
      var10000.__call__(var2, var8, var4);
      var3 = null;
      var1.setline(1023);
      var3 = var1.getglobal("True");
      var1.getlocal(1).__setattr__("accepted", var3);
      var3 = null;
      ContextManager var11;
      PyObject var9 = (var11 = ContextGuard.getManager(var1.getlocal(0).__getattr__("open_lock"))).__enter__(var2);

      label31: {
         try {
            var1.setline(1025);
            var10000 = var1.getlocal(0);
            String var10 = "accepted_children";
            PyObject var5 = var10000;
            PyObject var6 = var5.__getattr__(var10);
            var6 = var6._iadd(Py.newInteger(1));
            var5.__setattr__(var10, var6);
         } catch (Throwable var7) {
            if (var11.__exit__(var2, Py.setException(var7, var1))) {
               break label31;
            }

            throw (Throwable)Py.makeException();
         }

         var11.__exit__(var2, (PyException)null);
      }

      var1.setline(1026);
      PyTuple var12 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
      var1.f_lasti = -1;
      return var12;
   }

   public PyObject _datagram_connect$85(PyFrame var1, ThreadState var2) {
      var1.setline(1032);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      PyObject[] var7;
      if (var10000.__nonzero__()) {
         var1.setline(1033);
         var10000 = var1.getglobal("_get_jsockaddr");
         var7 = new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("family"), var1.getlocal(0).__getattr__("type"), var1.getlocal(0).__getattr__("proto"), Py.newInteger(0)};
         var3 = var10000.__call__(var2, var7);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1035);
      var10000 = var1.getlocal(0).__getattr__("connected").__not__();
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("socket_type");
         var10000 = var3._eq(var1.getglobal("DATAGRAM_SOCKET"));
         var3 = null;
      }

      String[] var4;
      if (var10000.__nonzero__()) {
         var1.setline(1036);
         var10000 = var1.getglobal("log").__getattr__("debug");
         var7 = new PyObject[]{PyString.fromInterned("Binding datagram socket to %s"), var1.getlocal(0).__getattr__("bind_addr"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
         var4 = new String[]{"extra"};
         var10000.__call__(var2, var7, var4);
         var3 = null;
         var1.setline(1037);
         var3 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("connected", var3);
         var3 = null;
         var1.setline(1038);
         var3 = var1.getglobal("PythonInboundHandler").__call__(var2, var1.getlocal(0));
         var1.getlocal(0).__setattr__("python_inbound_handler", var3);
         var3 = null;
         var1.setline(1039);
         var3 = var1.getglobal("Bootstrap").__call__(var2).__getattr__("group").__call__(var2, var1.getglobal("NIO_GROUP")).__getattr__("channel").__call__(var2, var1.getglobal("NioDatagramChannel"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1040);
         var1.getlocal(2).__getattr__("handler").__call__(var2, var1.getlocal(0).__getattr__("python_inbound_handler"));
         var1.setline(1041);
         var3 = var1.getlocal(0).__getattr__("options").__getattr__("iteritems").__call__(var2).__iter__();

         while(true) {
            var1.setline(1041);
            PyObject var8 = var3.__iternext__();
            if (var8 == null) {
               var1.setline(1044);
               var3 = var1.getlocal(2).__getattr__("register").__call__(var2);
               var1.setlocal(5, var3);
               var3 = null;
               var1.setline(1045);
               var1.getlocal(0).__getattr__("_handle_channel_future").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned("register"));
               var1.setline(1046);
               var3 = var1.getlocal(5).__getattr__("channel").__call__(var2);
               var1.getlocal(0).__setattr__("channel", var3);
               var3 = null;
               var1.setline(1047);
               var1.getlocal(0).__getattr__("_handle_channel_future").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("channel").__getattr__("bind").__call__(var2, var1.getlocal(0).__getattr__("bind_addr")), (PyObject)PyString.fromInterned("bind"));
               break;
            }

            PyObject[] var5 = Py.unpackSequence(var8, 2);
            PyObject var6 = var5[0];
            var1.setlocal(3, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(4, var6);
            var6 = null;
            var1.setline(1042);
            var1.getglobal("_set_option").__call__(var2, var1.getlocal(2).__getattr__("option"), var1.getlocal(3), var1.getlocal(4));
         }
      }

      var1.setline(1049);
      var3 = var1.getlocal(1);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1053);
         var10000 = var1.getglobal("log").__getattr__("debug");
         var7 = new PyObject[]{PyString.fromInterned("Connecting datagram socket to %s"), var1.getlocal(1), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
         var4 = new String[]{"extra"};
         var10000.__call__(var2, var7, var4);
         var3 = null;
         var1.setline(1054);
         var3 = var1.getlocal(0).__getattr__("channel").__getattr__("connect").__call__(var2, var1.getlocal(1));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(1055);
         var1.getlocal(0).__getattr__("_handle_channel_future").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned("connect"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject sendto$86(PyFrame var1, ThreadState var2) {
      var1.setline(1059);
      PyObject var3 = var1.getlocal(3);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1060);
         var3 = var1.getlocal(2);
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1061);
         var3 = var1.getlocal(3);
         var1.setlocal(5, var3);
         var3 = null;
      } else {
         var1.setline(1063);
         var3 = var1.getglobal("None");
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1064);
         var3 = var1.getlocal(2);
         var1.setlocal(5, var3);
         var3 = null;
      }

      var1.setline(1066);
      var10000 = var1.getglobal("_get_jsockaddr");
      PyObject[] var5 = new PyObject[]{var1.getlocal(5), var1.getlocal(0).__getattr__("family"), var1.getlocal(0).__getattr__("type"), var1.getlocal(0).__getattr__("proto"), Py.newInteger(0)};
      var3 = var10000.__call__(var2, var5);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1068);
      var10000 = var1.getglobal("log").__getattr__("debug");
      var5 = new PyObject[]{PyString.fromInterned("Sending datagram to %s <<<{!r:.20}>>>").__getattr__("format").__call__(var2, var1.getlocal(1)), var1.getlocal(5), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
      String[] var4 = new String[]{"extra"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(1069);
      var1.getlocal(0).__getattr__("_datagram_connect").__call__(var2);
      var1.setline(1070);
      var3 = var1.getglobal("DatagramPacket").__call__(var2, var1.getglobal("Unpooled").__getattr__("wrappedBuffer").__call__(var2, var1.getlocal(1)), var1.getlocal(5));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1071);
      var3 = var1.getlocal(0).__getattr__("channel").__getattr__("writeAndFlush").__call__(var2, var1.getlocal(6));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(1072);
      var1.getlocal(0).__getattr__("_handle_channel_future").__call__((ThreadState)var2, (PyObject)var1.getlocal(7), (PyObject)PyString.fromInterned("sendto"));
      var1.setline(1073);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject recvfrom_into$87(PyFrame var1, ThreadState var2) {
      var1.setline(1076);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1077);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1078);
      var3 = var1.getlocal(0).__getattr__("recvfrom").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(1079);
      var3 = var1.getlocal(4);
      var1.getlocal(1).__setslice__(Py.newInteger(0), var1.getglobal("len").__call__(var2, var1.getlocal(4)), (PyObject)null, var3);
      var3 = null;
      var1.setline(1080);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getglobal("len").__call__(var2, var1.getlocal(4)), var1.getlocal(5)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject recv_into$88(PyFrame var1, ThreadState var2) {
      var1.setline(1083);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1084);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1085);
      var3 = var1.getlocal(0).__getattr__("recv").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1086);
      var3 = var1.getlocal(4);
      var1.getlocal(1).__setslice__(Py.newInteger(0), var1.getglobal("len").__call__(var2, var1.getlocal(4)), (PyObject)null, var3);
      var3 = null;
      var1.setline(1087);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject close$89(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      ContextManager var3;
      PyObject var4 = (var3 = ContextGuard.getManager(var1.getlocal(0).__getattr__("open_lock"))).__enter__(var2);

      label54: {
         Throwable var10000;
         label58: {
            boolean var10001;
            label59: {
               PyObject var15;
               try {
                  var1.setline(1093);
                  var15 = var1.getlocal(0);
                  String var12 = "open_count";
                  PyObject var5 = var15;
                  PyObject var6 = var5.__getattr__(var12);
                  var6 = var6._isub(Py.newInteger(1));
                  var5.__setattr__(var12, var6);
                  var1.setline(1094);
                  var4 = var1.getlocal(0).__getattr__("open_count");
                  var15 = var4._gt(Py.newInteger(0));
                  var4 = null;
                  if (var15.__nonzero__()) {
                     var1.setline(1095);
                     var15 = var1.getglobal("log").__getattr__("debug");
                     PyObject[] var14 = new PyObject[]{PyString.fromInterned("Open count > 0, so not closing underlying socket"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
                     String[] var13 = new String[]{"extra"};
                     var15.__call__(var2, var14, var13);
                     var4 = null;
                     var1.setline(1096);
                     break label59;
                  }
               } catch (Throwable var11) {
                  var10000 = var11;
                  var10001 = false;
                  break label58;
               }

               label60: {
                  try {
                     var1.setline(1098);
                     var4 = var1.getlocal(0).__getattr__("channel");
                     var15 = var4._is(var1.getglobal("None"));
                     var4 = null;
                     if (!var15.__nonzero__()) {
                        break label60;
                     }

                     var1.setline(1099);
                  } catch (Throwable var10) {
                     var10000 = var10;
                     var10001 = false;
                     break label58;
                  }

                  var3.__exit__(var2, (PyException)null);

                  try {
                     var1.f_lasti = -1;
                     return Py.None;
                  } catch (Throwable var7) {
                     var10000 = var7;
                     var10001 = false;
                     break label58;
                  }
               }

               try {
                  var1.setline(1101);
                  var4 = var1.getlocal(0).__getattr__("channel").__getattr__("close").__call__(var2);
                  var1.setlocal(1, var4);
                  var4 = null;
                  var1.setline(1102);
                  var1.getlocal(1).__getattr__("addListener").__call__(var2, var1.getlocal(0).__getattr__("_finish_closing"));
               } catch (Throwable var9) {
                  var10000 = var9;
                  var10001 = false;
                  break label58;
               }

               var3.__exit__(var2, (PyException)null);
               break label54;
            }

            var3.__exit__(var2, (PyException)null);

            try {
               var1.f_lasti = -1;
               return Py.None;
            } catch (Throwable var8) {
               var10000 = var8;
               var10001 = false;
            }
         }

         if (!var3.__exit__(var2, Py.setException(var10000, var1))) {
            throw (Throwable)Py.makeException();
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _finish_closing$90(PyFrame var1, ThreadState var2) {
      var1.setline(1105);
      PyObject var3 = var1.getlocal(0).__getattr__("socket_type");
      PyObject var10000 = var3._eq(var1.getglobal("SERVER_SOCKET"));
      var3 = null;
      String[] var4;
      PyObject var5;
      PyObject[] var6;
      PyObject var8;
      if (var10000.__nonzero__()) {
         var1.setline(1106);
         var10000 = var1.getglobal("log").__getattr__("debug");
         var6 = new PyObject[]{PyString.fromInterned("Shutting down server socket parent group"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
         var4 = new String[]{"extra"};
         var10000.__call__(var2, var6, var4);
         var3 = null;
         var1.setline(1107);
         var1.getlocal(0).__getattr__("parent_group").__getattr__("shutdownGracefully").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)Py.newInteger(100), (PyObject)var1.getglobal("TimeUnit").__getattr__("MILLISECONDS"));
         var1.setline(1108);
         var10000 = var1.getlocal(0);
         String var7 = "accepted_children";
         var8 = var10000;
         var5 = var8.__getattr__(var7);
         var5 = var5._isub(Py.newInteger(1));
         var8.__setattr__(var7, var5);

         while(true) {
            var1.setline(1109);
            if (!var1.getglobal("True").__nonzero__()) {
               break;
            }

            var1.setline(1110);
            var3 = var1.getlocal(0).__getattr__("child_queue").__getattr__("poll").__call__(var2);
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(1111);
            var3 = var1.getlocal(2);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               break;
            }

            var1.setline(1113);
            var10000 = var1.getglobal("log").__getattr__("debug");
            var6 = new PyObject[]{PyString.fromInterned("Closed child socket %s not yet accepted"), var1.getlocal(2), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
            var4 = new String[]{"extra"};
            var10000.__call__(var2, var6, var4);
            var3 = null;
            var1.setline(1114);
            var1.getlocal(2).__getattr__("close").__call__(var2);
         }
      } else {
         var1.setline(1116);
         PyList var9 = new PyList(Py.EmptyObjects);
         var1.setlocal(3, var9);
         var3 = null;
         var1.setline(1117);
         var1.getlocal(0).__getattr__("incoming").__getattr__("drainTo").__call__(var2, var1.getlocal(3));
         var1.setline(1118);
         var3 = var1.getlocal(3).__iter__();

         while(true) {
            var1.setline(1118);
            var8 = var3.__iternext__();
            if (var8 == null) {
               break;
            }

            var1.setlocal(4, var8);
            var1.setline(1119);
            var5 = var1.getlocal(4);
            var10000 = var5._isnot(var1.getglobal("_PEER_CLOSED"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1120);
               var1.getlocal(4).__getattr__("release").__call__(var2);
            }
         }
      }

      var1.setline(1122);
      var10000 = var1.getglobal("log").__getattr__("debug");
      var6 = new PyObject[]{PyString.fromInterned("Closed socket"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
      var4 = new String[]{"extra"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject shutdown$91(PyFrame var1, ThreadState var2) {
      var1.setline(1125);
      PyObject var10000 = var1.getglobal("log").__getattr__("debug");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("Got request to shutdown socket how=%s"), var1.getlocal(1), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
      String[] var4 = new String[]{"extra"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(1126);
      var1.getlocal(0).__getattr__("_verify_channel").__call__(var2);
      var1.setline(1127);
      var10000 = var1.getlocal(1)._and(var1.getglobal("SHUT_RD"));
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(1)._and(var1.getglobal("SHUT_RDWR"));
      }

      if (var10000.__nonzero__()) {
         try {
            var1.setline(1129);
            var1.getlocal(0).__getattr__("channel").__getattr__("pipeline").__call__(var2).__getattr__("remove").__call__(var2, var1.getlocal(0).__getattr__("python_inbound_handler"));
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (var6.match(var1.getglobal("NoSuchElementException"))) {
               var1.setline(1131);
            } else {
               if (!var6.match(var1.getglobal("AttributeError"))) {
                  throw var6;
               }

               var1.setline(1133);
            }
         }
      }

      var1.setline(1134);
      var10000 = var1.getlocal(1)._and(var1.getglobal("SHUT_WR"));
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(1)._and(var1.getglobal("SHUT_RDWR"));
      }

      if (var10000.__nonzero__()) {
         var1.setline(1135);
         PyObject var7 = var1.getglobal("False");
         var1.getlocal(0).__setattr__("_can_write", var7);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _readable$92(PyFrame var1, ThreadState var2) {
      var1.setline(1138);
      PyObject var3 = var1.getlocal(0).__getattr__("socket_type");
      PyObject var10000 = var3._eq(var1.getglobal("CLIENT_SOCKET"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("socket_type");
         var10000 = var3._eq(var1.getglobal("DATAGRAM_SOCKET"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1139);
         var10000 = var1.getglobal("log").__getattr__("debug");
         PyObject[] var6 = new PyObject[]{PyString.fromInterned("Incoming head=%s queue=%s"), var1.getlocal(0).__getattr__("incoming_head"), var1.getlocal(0).__getattr__("incoming"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
         String[] var5 = new String[]{"extra"};
         var10000.__call__(var2, var6, var5);
         var3 = null;
         var1.setline(1140);
         var10000 = var1.getglobal("bool");
         var3 = var1.getlocal(0).__getattr__("incoming_head");
         PyObject var10002 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10002.__nonzero__()) {
            var10002 = var1.getlocal(0).__getattr__("incoming_head").__getattr__("readableBytes").__call__(var2);
         }

         if (!var10002.__nonzero__()) {
            var10002 = var1.getlocal(0).__getattr__("incoming").__getattr__("peek").__call__(var2);
         }

         var3 = var10000.__call__(var2, var10002);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1143);
         PyObject var4 = var1.getlocal(0).__getattr__("socket_type");
         var10000 = var4._eq(var1.getglobal("SERVER_SOCKET"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1144);
            var3 = var1.getglobal("bool").__call__(var2, var1.getlocal(0).__getattr__("child_queue").__getattr__("peek").__call__(var2));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1146);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _pending$93(PyFrame var1, ThreadState var2) {
      var1.setline(1155);
      PyObject var3 = var1.getlocal(0).__getattr__("socket_type");
      PyObject var10000 = var3._eq(var1.getglobal("CLIENT_SOCKET"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("socket_type");
         var10000 = var3._eq(var1.getglobal("DATAGRAM_SOCKET"));
         var3 = null;
      }

      PyInteger var6;
      if (!var10000.__nonzero__()) {
         var1.setline(1163);
         var6 = Py.newInteger(0);
         var1.f_lasti = -1;
         return var6;
      } else {
         var1.setline(1156);
         var3 = var1.getlocal(0).__getattr__("incoming_head");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1157);
            var3 = var1.getlocal(0).__getattr__("incoming_head").__getattr__("readableBytes").__call__(var2);
            var1.setlocal(1, var3);
            var3 = null;
         } else {
            var1.setline(1159);
            var6 = Py.newInteger(0);
            var1.setlocal(1, var6);
            var3 = null;
         }

         var1.setline(1160);
         var3 = var1.getlocal(0).__getattr__("incoming").__iter__();

         while(true) {
            var1.setline(1160);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(1162);
               var3 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(2, var4);
            var1.setline(1161);
            PyObject var5 = var1.getlocal(1);
            var5 = var5._iadd(var1.getlocal(2).__getattr__("readableBytes").__call__(var2));
            var1.setlocal(1, var5);
         }
      }
   }

   public PyObject _writable$94(PyFrame var1, ThreadState var2) {
      var1.setline(1166);
      PyObject var10000 = var1.getlocal(0).__getattr__("channel_closed");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("channel");
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("channel").__getattr__("isActive").__call__(var2);
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("channel").__getattr__("isWritable").__call__(var2);
            }
         }
      }

      PyObject var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _verify_channel$95(PyFrame var1, ThreadState var2) {
      var1.setline(1171);
      PyObject var3 = var1.getlocal(0).__getattr__("channel");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1172);
         var10000 = var1.getglobal("log").__getattr__("debug");
         PyObject[] var5 = new PyObject[]{PyString.fromInterned("Channel is not connected or setup"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
         String[] var4 = new String[]{"extra"};
         var10000.__call__(var2, var5, var4);
         var3 = null;
         var1.setline(1173);
         throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("ENOTCONN"), (PyObject)PyString.fromInterned("Socket is not connected")));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject send$96(PyFrame var1, ThreadState var2) {
      var1.setline(1178);
      var1.getlocal(0).__getattr__("_verify_channel").__call__(var2);
      var1.setline(1179);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("memoryview")).__nonzero__()) {
         var1.setline(1180);
         var3 = var1.getlocal(1).__getattr__("tobytes").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1181);
      var3 = var1.getglobal("str").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1182);
      PyObject var10000 = var1.getglobal("log").__getattr__("debug");
      PyObject[] var6 = new PyObject[]{PyString.fromInterned("Sending data <<<{!r:.20}>>>").__getattr__("format").__call__(var2, var1.getlocal(1)), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
      String[] var4 = new String[]{"extra"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(1184);
      var3 = var1.getlocal(0).__getattr__("socket_type");
      var10000 = var3._eq(var1.getglobal("DATAGRAM_SOCKET"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1185);
         var3 = var1.getglobal("DatagramPacket").__call__(var2, var1.getglobal("Unpooled").__getattr__("wrappedBuffer").__call__(var2, var1.getlocal(1)), var1.getlocal(0).__getattr__("channel").__getattr__("remoteAddress").__call__(var2));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1186);
         var3 = var1.getlocal(0).__getattr__("channel").__getattr__("writeAndFlush").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1187);
         var1.getlocal(0).__getattr__("_handle_channel_future").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("send"));
         var1.setline(1188);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1190);
         if (var1.getlocal(0).__getattr__("_can_write").__not__().__nonzero__()) {
            var1.setline(1191);
            throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("ENOTCONN"), (PyObject)PyString.fromInterned("Socket not connected")));
         } else {
            var1.setline(1193);
            PyObject var7 = var1.getlocal(0).__getattr__("channel").__getattr__("bytesBeforeUnwritable").__call__(var2);
            var1.setlocal(5, var7);
            var4 = null;
            var1.setline(1194);
            var7 = var1.getlocal(5);
            var10000 = var7._gt(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1195);
               var7 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
               var1.setlocal(5, var7);
               var4 = null;
            }

            var1.setline(1197);
            var7 = var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(5), (PyObject)null);
            var1.setlocal(6, var7);
            var4 = null;
            var1.setline(1199);
            var7 = var1.getlocal(0).__getattr__("channel").__getattr__("writeAndFlush").__call__(var2, var1.getglobal("Unpooled").__getattr__("wrappedBuffer").__call__(var2, var1.getlocal(6)));
            var1.setlocal(4, var7);
            var4 = null;
            var1.setline(1200);
            var1.getlocal(0).__getattr__("_handle_channel_future").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("send"));
            var1.setline(1201);
            var10000 = var1.getglobal("log").__getattr__("debug");
            PyObject[] var8 = new PyObject[]{PyString.fromInterned("Sent data <<<{!r:.20}>>>").__getattr__("format").__call__(var2, var1.getlocal(6)), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
            String[] var5 = new String[]{"extra"};
            var10000.__call__(var2, var8, var5);
            var4 = null;
            var1.setline(1203);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(6));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _get_incoming_msg$97(PyFrame var1, ThreadState var2) {
      var1.setline(1208);
      PyObject var10000 = var1.getglobal("log").__getattr__("debug");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("head=%s incoming=%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("incoming_head"), var1.getlocal(0).__getattr__("incoming")})), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
      String[] var4 = new String[]{"extra"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(1209);
      PyObject var6 = var1.getlocal(0).__getattr__("incoming_head");
      var10000 = var6._is(var1.getglobal("None"));
      var3 = null;
      PyObject var7;
      if (var10000.__nonzero__()) {
         var1.setline(1210);
         var6 = var1.getlocal(0).__getattr__("timeout");
         var10000 = var6._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1211);
            if (var1.getlocal(0).__getattr__("peer_closed").__nonzero__()) {
               var1.setline(1212);
               var6 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var6;
            }

            var1.setline(1213);
            var7 = var1.getlocal(0).__getattr__("incoming").__getattr__("take").__call__(var2);
            var1.getlocal(0).__setattr__("incoming_head", var7);
            var4 = null;
         } else {
            var1.setline(1214);
            if (var1.getlocal(0).__getattr__("timeout").__nonzero__()) {
               var1.setline(1215);
               if (var1.getlocal(0).__getattr__("peer_closed").__nonzero__()) {
                  var1.setline(1216);
                  var6 = var1.getglobal("None");
                  var1.f_lasti = -1;
                  return var6;
               }

               var1.setline(1217);
               var7 = var1.getlocal(0).__getattr__("_handle_timeout").__call__(var2, var1.getlocal(0).__getattr__("incoming").__getattr__("poll"), var1.getlocal(1));
               var1.getlocal(0).__setattr__("incoming_head", var7);
               var4 = null;
            } else {
               var1.setline(1219);
               var7 = var1.getlocal(0).__getattr__("incoming").__getattr__("poll").__call__(var2);
               var1.getlocal(0).__setattr__("incoming_head", var7);
               var4 = null;
               var1.setline(1220);
               var7 = var1.getlocal(0).__getattr__("incoming_head");
               var10000 = var7._is(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1222);
                  var10000 = var1.getglobal("log").__getattr__("debug");
                  PyObject[] var8 = new PyObject[]{PyString.fromInterned("No data yet for socket"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
                  String[] var5 = new String[]{"extra"};
                  var10000.__call__(var2, var8, var5);
                  var4 = null;
                  var1.setline(1223);
                  throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("EAGAIN"), (PyObject)PyString.fromInterned("Resource temporarily unavailable")));
               }
            }
         }
      }

      var1.setline(1225);
      var7 = var1.getlocal(0).__getattr__("incoming_head");
      var1.setlocal(2, var7);
      var4 = null;
      var1.setline(1226);
      var7 = var1.getlocal(2);
      var10000 = var7._is(var1.getglobal("_PEER_CLOSED"));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1228);
         var7 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("incoming_head", var7);
         var4 = null;
         var1.setline(1229);
         var7 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("peer_closed", var7);
         var4 = null;
      }

      var1.setline(1230);
      var6 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject _get_message$98(PyFrame var1, ThreadState var2) {
      var1.setline(1234);
      var1.getlocal(0).__getattr__("_datagram_connect").__call__(var2);
      var1.setline(1235);
      var1.getlocal(0).__getattr__("_verify_channel").__call__(var2);
      var1.setline(1236);
      PyObject var3 = var1.getlocal(0).__getattr__("_get_incoming_msg").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1238);
      var3 = var1.getlocal(0).__getattr__("socket_type");
      PyObject var10000 = var3._eq(var1.getglobal("DATAGRAM_SOCKET"));
      var3 = null;
      PyObject var4;
      PyTuple var5;
      if (var10000.__nonzero__()) {
         var1.setline(1239);
         var3 = var1.getlocal(3);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1240);
            var5 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")});
            var1.f_lasti = -1;
            return var5;
         }

         var1.setline(1241);
         var4 = var1.getlocal(3);
         var10000 = var4._is(var1.getglobal("_PEER_CLOSED"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1242);
            var5 = new PyTuple(new PyObject[]{PyString.fromInterned(""), var1.getglobal("None")});
            var1.f_lasti = -1;
            return var5;
         }
      } else {
         var1.setline(1244);
         var4 = var1.getlocal(3);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1245);
            var5 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getlocal(0).__getattr__("channel").__getattr__("remoteAddress").__call__(var2)});
            var1.f_lasti = -1;
            return var5;
         }

         var1.setline(1246);
         var4 = var1.getlocal(3);
         var10000 = var4._is(var1.getglobal("_PEER_CLOSED"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1247);
            var5 = new PyTuple(new PyObject[]{PyString.fromInterned(""), var1.getlocal(0).__getattr__("channel").__getattr__("remoteAddress").__call__(var2)});
            var1.f_lasti = -1;
            return var5;
         }
      }

      var1.setline(1249);
      var4 = var1.getlocal(0).__getattr__("socket_type");
      var10000 = var4._eq(var1.getglobal("DATAGRAM_SOCKET"));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1250);
         var4 = var1.getlocal(3).__getattr__("content").__call__(var2);
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(1251);
         var4 = var1.getlocal(3).__getattr__("sender").__call__(var2);
         var1.setlocal(5, var4);
         var4 = null;
      } else {
         var1.setline(1253);
         var4 = var1.getlocal(3);
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(1254);
         var4 = var1.getlocal(0).__getattr__("channel").__getattr__("remoteAddress").__call__(var2);
         var1.setlocal(5, var4);
         var4 = null;
      }

      var1.setline(1255);
      var4 = var1.getlocal(4).__getattr__("readableBytes").__call__(var2);
      var1.setlocal(6, var4);
      var4 = null;
      var1.setline(1256);
      var4 = var1.getglobal("jarray").__getattr__("zeros").__call__((ThreadState)var2, (PyObject)var1.getglobal("min").__call__(var2, var1.getlocal(6), var1.getlocal(1)), (PyObject)PyString.fromInterned("b"));
      var1.setlocal(7, var4);
      var4 = null;
      var1.setline(1257);
      var1.getlocal(4).__getattr__("readBytes").__call__(var2, var1.getlocal(7));
      var1.setline(1258);
      var4 = var1.getlocal(4).__getattr__("readableBytes").__call__(var2);
      var10000 = var4._eq(Py.newInteger(0));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1259);
         var1.getlocal(3).__getattr__("release").__call__(var2);
         var1.setline(1260);
         var4 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("incoming_head", var4);
         var4 = null;
      }

      var1.setline(1261);
      var5 = new PyTuple(new PyObject[]{var1.getlocal(7).__getattr__("tostring").__call__(var2), var1.getlocal(5)});
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject recv$99(PyFrame var1, ThreadState var2) {
      var1.setline(1264);
      var1.getlocal(0).__getattr__("_verify_channel").__call__(var2);
      var1.setline(1265);
      PyObject var10000 = var1.getglobal("log").__getattr__("debug");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("Waiting on recv"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
      String[] var4 = new String[]{"extra"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(1270);
      PyObject var6 = var1.getlocal(0).__getattr__("_get_message").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("recv"));
      PyObject[] var7 = Py.unpackSequence(var6, 2);
      PyObject var5 = var7[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var7[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(1271);
      var10000 = var1.getglobal("log").__getattr__("debug");
      var3 = new PyObject[]{PyString.fromInterned("Received <<<{!r:.20}>>>").__getattr__("format").__call__(var2, var1.getlocal(3)), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
      var4 = new String[]{"extra"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(1272);
      var6 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject recvfrom$100(PyFrame var1, ThreadState var2) {
      var1.setline(1275);
      var1.getlocal(0).__getattr__("_verify_channel").__call__(var2);
      var1.setline(1276);
      PyObject var3 = var1.getlocal(0).__getattr__("_get_message").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("recvfrom"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(1277);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(4).__getattr__("getHostString").__call__(var2), var1.getlocal(4).__getattr__("getPort").__call__(var2)});
      var1.setlocal(5, var6);
      var3 = null;
      var1.setline(1278);
      PyObject var10000 = var1.getglobal("log").__getattr__("debug");
      PyObject[] var7 = new PyObject[]{PyString.fromInterned("Received from sender %s <<<{!r:20}>>>").__getattr__("format").__call__(var2, var1.getlocal(3)), var1.getlocal(5), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
      String[] var8 = new String[]{"extra"};
      var10000.__call__(var2, var7, var8);
      var3 = null;
      var1.setline(1279);
      var6 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(5)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject fileno$101(PyFrame var1, ThreadState var2) {
      var1.setline(1282);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setsockopt$102(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var7;
      try {
         var1.setline(1287);
         var7 = var1.getglobal("_socket_options").__getitem__(var1.getlocal(0).__getattr__("proto")).__getitem__(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)}));
         PyObject[] var4 = Py.unpackSequence(var7, 2);
         PyObject var5 = var4[0];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(5, var5);
         var5 = null;
         var3 = null;
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (var3.match(var1.getglobal("KeyError"))) {
            var1.setline(1289);
            throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("ENOPROTOOPT"), (PyObject)PyString.fromInterned("Protocol not available")));
         }

         throw var3;
      }

      var1.setline(1291);
      var7 = var1.getlocal(5).__call__(var2, var1.getlocal(3));
      var1.setlocal(6, var7);
      var3 = null;
      var1.setline(1292);
      var7 = var1.getlocal(6);
      var1.getlocal(0).__getattr__("options").__setitem__(var1.getlocal(4), var7);
      var3 = null;
      var1.setline(1293);
      PyObject var10000 = var1.getglobal("log").__getattr__("debug");
      PyObject[] var9 = new PyObject[]{PyString.fromInterned("Setting option %s to %s"), var1.getlocal(2), var1.getlocal(3), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
      String[] var8 = new String[]{"extra"};
      var10000.__call__(var2, var9, var8);
      var3 = null;
      var1.setline(1294);
      if (var1.getlocal(0).__getattr__("channel").__nonzero__()) {
         var1.setline(1295);
         var1.getglobal("_set_option").__call__(var2, var1.getlocal(0).__getattr__("channel").__getattr__("config").__call__(var2).__getattr__("setOption"), var1.getlocal(4), var1.getlocal(6));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getsockopt$103(PyFrame var1, ThreadState var2) {
      var1.setline(1300);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(var1.getglobal("SOL_SOCKET"));
      var3 = null;
      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(1301);
         var3 = var1.getlocal(2);
         var10000 = var3._eq(var1.getglobal("SO_ACCEPTCONN"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1302);
            var3 = var1.getlocal(0).__getattr__("socket_type");
            var10000 = var3._eq(var1.getglobal("SERVER_SOCKET"));
            var3 = null;
            PyInteger var10;
            if (var10000.__nonzero__()) {
               var1.setline(1303);
               var10 = Py.newInteger(1);
               var1.f_lasti = -1;
               return var10;
            }

            var1.setline(1304);
            var4 = var1.getlocal(0).__getattr__("type");
            var10000 = var4._eq(var1.getglobal("SOCK_STREAM"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1305);
               var10 = Py.newInteger(0);
               var1.f_lasti = -1;
               return var10;
            }

            var1.setline(1307);
            throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("ENOPROTOOPT"), (PyObject)PyString.fromInterned("Protocol not available")));
         }

         var1.setline(1308);
         var4 = var1.getlocal(2);
         var10000 = var4._eq(var1.getglobal("SO_TYPE"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1309);
            var3 = var1.getlocal(0).__getattr__("type");
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(1310);
         var4 = var1.getlocal(2);
         var10000 = var4._eq(var1.getglobal("SO_ERROR"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1311);
            var4 = var1.getlocal(0).__getattr__("_last_error");
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(1312);
            PyInteger var12 = Py.newInteger(0);
            var1.getlocal(0).__setattr__((String)"_last_error", var12);
            var4 = null;
            var1.setline(1313);
            var3 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var3;
         }
      }

      try {
         var1.setline(1317);
         var4 = var1.getglobal("_socket_options").__getitem__(var1.getlocal(0).__getattr__("proto")).__getitem__(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)}));
         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(6, var6);
         var6 = null;
         var4 = null;
      } catch (Throwable var7) {
         PyException var8 = Py.setException(var7, var1);
         if (var8.match(var1.getglobal("KeyError"))) {
            var1.setline(1319);
            throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("ENOPROTOOPT"), (PyObject)PyString.fromInterned("Protocol not available")));
         }

         throw var8;
      }

      var1.setline(1320);
      var10000 = var1.getglobal("log").__getattr__("debug");
      PyObject[] var11 = new PyObject[]{PyString.fromInterned("Shadow option settings %s"), var1.getlocal(0).__getattr__("options"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
      String[] var9 = new String[]{"extra"};
      var10000.__call__(var2, var11, var9);
      var4 = null;
      var1.setline(1321);
      var3 = var1.getlocal(0).__getattr__("options").__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getsockname$104(PyFrame var1, ThreadState var2) {
      var1.setline(1324);
      PyObject var3 = var1.getlocal(0).__getattr__("channel");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1325);
         var3 = var1.getlocal(0).__getattr__("bind_addr");
         var10000 = var3._eq(var1.getglobal("_EPHEMERAL_ADDRESS"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1326);
            throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("ENOTCONN"), (PyObject)PyString.fromInterned("Socket is not connected")));
         } else {
            var1.setline(1328);
            var3 = var1.getglobal("_socktuple").__call__(var2, var1.getlocal(0).__getattr__("bind_addr"));
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(1329);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("bind_future")).__nonzero__()) {
            var1.setline(1330);
            var1.getlocal(0).__getattr__("bind_future").__getattr__("sync").__call__(var2);
         }

         var1.setline(1331);
         PyObject var4 = var1.getlocal(0).__getattr__("channel").__getattr__("localAddress").__call__(var2);
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(1332);
         if (var1.getlocal(1).__getattr__("getAddress").__call__(var2).__getattr__("isAnyLocalAddress").__call__(var2).__nonzero__()) {
            var1.setline(1337);
            var4 = var1.getglobal("type").__call__(var2, var1.getlocal(0).__getattr__("bind_addr").__getattr__("getAddress").__call__(var2));
            var10000 = var4._ne(var1.getglobal("type").__call__(var2, var1.getlocal(1).__getattr__("getAddress").__call__(var2)));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1338);
               var3 = var1.getglobal("_socktuple").__call__(var2, var1.getglobal("java").__getattr__("net").__getattr__("InetSocketAddress").__call__(var2, var1.getlocal(0).__getattr__("bind_addr").__getattr__("getAddress").__call__(var2), var1.getlocal(1).__getattr__("getPort").__call__(var2)));
               var1.f_lasti = -1;
               return var3;
            }
         }

         var1.setline(1340);
         var3 = var1.getglobal("_socktuple").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getpeername$105(PyFrame var1, ThreadState var2) {
      var1.setline(1343);
      var1.getlocal(0).__getattr__("_verify_channel").__call__(var2);
      var1.setline(1344);
      PyObject var3 = var1.getlocal(0).__getattr__("channel").__getattr__("remoteAddress").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1345);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1346);
         throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("ENOTCONN"), (PyObject)PyString.fromInterned("Socket is not connected")));
      } else {
         var1.setline(1347);
         var3 = var1.getglobal("_socktuple").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _closedsocket$106(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1367);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, close$107, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(1370);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _dummy$108, (PyObject)null);
      var1.setlocal("_dummy", var4);
      var3 = null;
      var1.setline(1374);
      PyObject var5 = var1.getname("_dummy");
      var1.setlocal("fileno", var5);
      var1.setlocal("send", var5);
      var1.setlocal("recv", var5);
      var1.setlocal("recv_into", var5);
      var1.setlocal("sendto", var5);
      var1.setlocal("recvfrom", var5);
      var1.setlocal("recvfrom_into", var5);
      var1.setline(1376);
      var5 = var1.getname("_dummy");
      var1.setlocal("__getattr__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject close$107(PyFrame var1, ThreadState var2) {
      var1.setline(1368);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _dummy$108(PyFrame var1, ThreadState var2) {
      var1.setline(1371);
      throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("EBADF"), (PyObject)PyString.fromInterned("Bad file descriptor")));
   }

   public PyObject _socketobject$109(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1385);
      PyObject var3 = var1.getname("_realsocket").__getattr__("__doc__");
      var1.setlocal("__doc__", var3);
      var3 = null;
      var1.setline(1387);
      PyObject[] var5 = new PyObject[]{var1.getname("AF_INET"), var1.getname("SOCK_STREAM"), var1.getname("None"), var1.getname("None")};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __init__$110, (PyObject)null);
      var1.setlocal("__init__", var6);
      var3 = null;
      var1.setline(1394);
      var5 = new PyObject[]{var1.getname("_closedsocket"), var1.getname("_delegate_methods"), var1.getname("setattr")};
      var6 = new PyFunction(var1.f_globals, var5, close$111, (PyObject)null);
      var1.setlocal("close", var6);
      var3 = null;
      var1.setline(1402);
      var3 = var1.getname("_realsocket").__getattr__("close").__getattr__("__doc__");
      var1.getname("close").__setattr__("__doc__", var3);
      var3 = null;
      var1.setline(1404);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, fileno$112, (PyObject)null);
      var1.setlocal("fileno", var6);
      var3 = null;
      var1.setline(1407);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, accept$113, (PyObject)null);
      var1.setlocal("accept", var6);
      var3 = null;
      var1.setline(1410);
      var3 = var1.getname("_realsocket").__getattr__("accept").__getattr__("__doc__");
      var1.getname("accept").__setattr__("__doc__", var3);
      var3 = null;
      var1.setline(1412);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, dup$114, PyString.fromInterned("dup() -> socket object\n\n        Return a new socket object connected to the same system resource."));
      var1.setlocal("dup", var6);
      var3 = null;
      var1.setline(1423);
      var5 = new PyObject[]{PyString.fromInterned("r"), Py.newInteger(-1)};
      var6 = new PyFunction(var1.f_globals, var5, makefile$115, PyString.fromInterned("makefile([mode[, bufsize]]) -> file object\n\n        Return a regular file object corresponding to the socket.  The mode\n        and bufsize arguments are as for the built-in open() function."));
      var1.setlocal("makefile", var6);
      var3 = null;
      var1.setline(1435);
      PyObject var10000 = var1.getname("property");
      var5 = new PyObject[2];
      var1.setline(1435);
      PyObject[] var4 = Py.EmptyObjects;
      var5[0] = new PyFunction(var1.f_globals, var4, f$116);
      var5[1] = PyString.fromInterned("the socket family");
      String[] var7 = new String[]{"doc"};
      var10000 = var10000.__call__(var2, var5, var7);
      var3 = null;
      var3 = var10000;
      var1.setlocal("family", var3);
      var3 = null;
      var1.setline(1436);
      var10000 = var1.getname("property");
      var5 = new PyObject[2];
      var1.setline(1436);
      var4 = Py.EmptyObjects;
      var5[0] = new PyFunction(var1.f_globals, var4, f$117);
      var5[1] = PyString.fromInterned("the socket type");
      var7 = new String[]{"doc"};
      var10000 = var10000.__call__(var2, var5, var7);
      var3 = null;
      var3 = var10000;
      var1.setlocal("type", var3);
      var3 = null;
      var1.setline(1437);
      var10000 = var1.getname("property");
      var5 = new PyObject[2];
      var1.setline(1437);
      var4 = Py.EmptyObjects;
      var5[0] = new PyFunction(var1.f_globals, var4, f$118);
      var5[1] = PyString.fromInterned("the socket protocol");
      var7 = new String[]{"doc"};
      var10000 = var10000.__call__(var2, var5, var7);
      var3 = null;
      var3 = var10000;
      var1.setlocal("proto", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$110(PyFrame var1, ThreadState var2) {
      var1.setline(1388);
      PyObject var3 = var1.getlocal(4);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1389);
         var3 = var1.getglobal("_realsocket").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(1390);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("_sock", var3);
      var3 = null;
      var1.setline(1391);
      var3 = var1.getglobal("_delegate_methods").__iter__();

      while(true) {
         var1.setline(1391);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(5, var4);
         var1.setline(1392);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(5), var1.getglobal("getattr").__call__(var2, var1.getlocal(4), var1.getlocal(5)));
      }
   }

   public PyObject close$111(PyFrame var1, ThreadState var2) {
      var1.setline(1397);
      var1.getlocal(0).__getattr__("_sock").__getattr__("close").__call__(var2);
      var1.setline(1398);
      PyObject var3 = var1.getlocal(1).__call__(var2);
      var1.getlocal(0).__setattr__("_sock", var3);
      var3 = null;
      var1.setline(1399);
      var3 = var1.getlocal(0).__getattr__("_sock").__getattr__("_dummy");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1400);
      var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(1400);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(5, var4);
         var1.setline(1401);
         var1.getlocal(3).__call__(var2, var1.getlocal(0), var1.getlocal(5), var1.getlocal(4));
      }
   }

   public PyObject fileno$112(PyFrame var1, ThreadState var2) {
      var1.setline(1405);
      PyObject var3 = var1.getlocal(0).__getattr__("_sock");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject accept$113(PyFrame var1, ThreadState var2) {
      var1.setline(1408);
      PyObject var3 = var1.getlocal(0).__getattr__("_sock").__getattr__("accept").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(1409);
      PyObject[] var10002 = new PyObject[2];
      PyObject var10005 = var1.getglobal("_socketobject");
      PyObject[] var6 = new PyObject[]{var1.getlocal(1)};
      String[] var7 = new String[]{"_sock"};
      var10005 = var10005.__call__(var2, var6, var7);
      var3 = null;
      var10002[0] = var10005;
      var10002[1] = var1.getlocal(2);
      PyTuple var8 = new PyTuple(var10002);
      var1.f_lasti = -1;
      return var8;
   }

   public PyObject dup$114(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(1415);
      PyString.fromInterned("dup() -> socket object\n\n        Return a new socket object connected to the same system resource.");
      var1.setline(1417);
      PyObject var3;
      PyObject var15;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("_sock"), var1.getglobal("_closedsocket")).__nonzero__()) {
         var1.setline(1418);
         var15 = var1.getglobal("_socketobject");
         PyObject[] var10 = new PyObject[]{var1.getglobal("_closedsocket").__call__(var2)};
         String[] var11 = new String[]{"_sock"};
         var15 = var15.__call__(var2, var10, var11);
         var3 = null;
         var3 = var15;
         var1.f_lasti = -1;
         return var3;
      } else {
         ContextManager var4;
         PyObject var5 = (var4 = ContextGuard.getManager(var1.getlocal(0).__getattr__("_sock").__getattr__("open_lock"))).__enter__(var2);

         Throwable var10000;
         label32: {
            boolean var10001;
            try {
               var1.setline(1420);
               var15 = var1.getlocal(0).__getattr__("_sock");
               String var12 = "open_count";
               PyObject var6 = var15;
               PyObject var7 = var6.__getattr__(var12);
               var7 = var7._iadd(Py.newInteger(1));
               var6.__setattr__(var12, var7);
               var1.setline(1421);
               var15 = var1.getglobal("_socketobject");
               PyObject[] var13 = new PyObject[]{var1.getlocal(0).__getattr__("_sock")};
               String[] var14 = new String[]{"_sock"};
               var15 = var15.__call__(var2, var13, var14);
               var5 = null;
               var3 = var15;
            } catch (Throwable var9) {
               var10000 = var9;
               var10001 = false;
               break label32;
            }

            var4.__exit__(var2, (PyException)null);

            try {
               var1.f_lasti = -1;
               return var3;
            } catch (Throwable var8) {
               var10000 = var8;
               var10001 = false;
            }
         }

         if (!var4.__exit__(var2, Py.setException(var10000, var1))) {
            throw (Throwable)Py.makeException();
         } else {
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject makefile$115(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(1427);
      PyString.fromInterned("makefile([mode[, bufsize]]) -> file object\n\n        Return a regular file object corresponding to the socket.  The mode\n        and bufsize arguments are as for the built-in open() function.");
      var1.setline(1429);
      PyObject var3;
      PyObject var15;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("_sock"), var1.getglobal("_closedsocket")).__nonzero__()) {
         var1.setline(1430);
         var15 = var1.getglobal("_fileobject");
         PyObject[] var10 = new PyObject[]{var1.getglobal("_closedsocket").__call__(var2), var1.getlocal(1), var1.getlocal(2), var1.getglobal("True")};
         String[] var11 = new String[]{"close"};
         var15 = var15.__call__(var2, var10, var11);
         var3 = null;
         var3 = var15;
         var1.f_lasti = -1;
         return var3;
      } else {
         ContextManager var4;
         PyObject var5 = (var4 = ContextGuard.getManager(var1.getlocal(0).__getattr__("_sock").__getattr__("open_lock"))).__enter__(var2);

         Throwable var10000;
         label32: {
            boolean var10001;
            try {
               var1.setline(1432);
               var15 = var1.getlocal(0).__getattr__("_sock");
               String var12 = "open_count";
               PyObject var6 = var15;
               PyObject var7 = var6.__getattr__(var12);
               var7 = var7._iadd(Py.newInteger(1));
               var6.__setattr__(var12, var7);
               var1.setline(1433);
               var15 = var1.getglobal("_fileobject");
               PyObject[] var13 = new PyObject[]{var1.getlocal(0).__getattr__("_sock"), var1.getlocal(1), var1.getlocal(2), var1.getglobal("True")};
               String[] var14 = new String[]{"close"};
               var15 = var15.__call__(var2, var13, var14);
               var5 = null;
               var3 = var15;
            } catch (Throwable var9) {
               var10000 = var9;
               var10001 = false;
               break label32;
            }

            var4.__exit__(var2, (PyException)null);

            try {
               var1.f_lasti = -1;
               return var3;
            } catch (Throwable var8) {
               var10000 = var8;
               var10001 = false;
            }
         }

         if (!var4.__exit__(var2, Py.setException(var10000, var1))) {
            throw (Throwable)Py.makeException();
         } else {
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject f$116(PyFrame var1, ThreadState var2) {
      var1.setline(1435);
      PyObject var3 = var1.getlocal(0).__getattr__("_sock").__getattr__("family");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$117(PyFrame var1, ThreadState var2) {
      var1.setline(1436);
      PyObject var3 = var1.getlocal(0).__getattr__("_sock").__getattr__("type");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$118(PyFrame var1, ThreadState var2) {
      var1.setline(1437);
      PyObject var3 = var1.getlocal(0).__getattr__("_sock").__getattr__("proto");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject meth$119(PyFrame var1, ThreadState var2) {
      var1.setline(1441);
      PyObject var10000 = var1.getglobal("getattr").__call__(var2, var1.getlocal(1).__getattr__("_sock"), var1.getlocal(0));
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(2), (PyObject)null);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject ChildSocket$120(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1459);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$121, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1467);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _make_active$122, (PyObject)null);
      var1.setlocal("_make_active", var4);
      var3 = null;
      var1.setline(1482);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, send$123, (PyObject)null);
      var1.setlocal("send", var4);
      var3 = null;
      var1.setline(1486);
      PyObject var5 = var1.getname("send");
      var1.setlocal("sendall", var5);
      var3 = null;
      var1.setline(1488);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, recv$124, (PyObject)null);
      var1.setlocal("recv", var4);
      var3 = null;
      var1.setline(1492);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, recvfrom$125, (PyObject)null);
      var1.setlocal("recvfrom", var4);
      var3 = null;
      var1.setline(1496);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setblocking$126, (PyObject)null);
      var1.setlocal("setblocking", var4);
      var3 = null;
      var1.setline(1500);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$127, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(1513);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, shutdown$128, (PyObject)null);
      var1.setlocal("shutdown", var4);
      var3 = null;
      var1.setline(1517);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __del__$129, (PyObject)null);
      var1.setlocal("__del__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$121(PyFrame var1, ThreadState var2) {
      var1.setline(1460);
      PyObject var10000 = var1.getglobal("super").__call__(var2, var1.getglobal("ChildSocket"), var1.getlocal(0)).__getattr__("__init__");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1).__getattr__("type")};
      String[] var4 = new String[]{"type"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(1461);
      PyObject var5 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("parent_socket", var5);
      var3 = null;
      var1.setline(1462);
      var5 = var1.getglobal("Condition").__call__(var2);
      var1.getlocal(0).__setattr__("_activation_cv", var5);
      var3 = null;
      var1.setline(1463);
      var5 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("_activated", var5);
      var3 = null;
      var1.setline(1464);
      var5 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("accepted", var5);
      var3 = null;
      var1.setline(1465);
      var5 = var1.getlocal(1).__getattr__("timeout");
      var1.getlocal(0).__setattr__("timeout", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _make_active$122(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(1468);
      if (var1.getlocal(0).__getattr__("_activated").__nonzero__()) {
         var1.setline(1469);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         ContextManager var3;
         PyObject var4 = (var3 = ContextGuard.getManager(var1.getlocal(0).__getattr__("_activation_cv"))).__enter__(var2);

         label18: {
            try {
               var1.setline(1471);
               var4 = var1.getglobal("True");
               var1.getlocal(0).__setattr__("_activated", var4);
               var4 = null;
               var1.setline(1472);
               var1.getlocal(0).__getattr__("_activation_cv").__getattr__("notify").__call__(var2);
            } catch (Throwable var5) {
               if (var3.__exit__(var2, Py.setException(var5, var1))) {
                  break label18;
               }

               throw (Throwable)Py.makeException();
            }

            var3.__exit__(var2, (PyException)null);
         }

         var1.setline(1473);
         PyObject var10000 = var1.getglobal("log").__getattr__("debug");
         PyObject[] var6 = new PyObject[]{PyString.fromInterned("Child socket is now activated"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
         String[] var7 = new String[]{"extra"};
         var10000.__call__(var2, var6, var7);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject send$123(PyFrame var1, ThreadState var2) {
      var1.setline(1483);
      var1.getlocal(0).__getattr__("_make_active").__call__(var2);
      var1.setline(1484);
      PyObject var3 = var1.getglobal("super").__call__(var2, var1.getglobal("ChildSocket"), var1.getlocal(0)).__getattr__("send").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject recv$124(PyFrame var1, ThreadState var2) {
      var1.setline(1489);
      var1.getlocal(0).__getattr__("_make_active").__call__(var2);
      var1.setline(1490);
      PyObject var3 = var1.getglobal("super").__call__(var2, var1.getglobal("ChildSocket"), var1.getlocal(0)).__getattr__("recv").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject recvfrom$125(PyFrame var1, ThreadState var2) {
      var1.setline(1493);
      var1.getlocal(0).__getattr__("_make_active").__call__(var2);
      var1.setline(1494);
      PyObject var3 = var1.getglobal("super").__call__(var2, var1.getglobal("ChildSocket"), var1.getlocal(0)).__getattr__("recvfrom").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setblocking$126(PyFrame var1, ThreadState var2) {
      var1.setline(1497);
      var1.getlocal(0).__getattr__("_make_active").__call__(var2);
      var1.setline(1498);
      PyObject var3 = var1.getglobal("super").__call__(var2, var1.getglobal("ChildSocket"), var1.getlocal(0)).__getattr__("setblocking").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject close$127(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(1501);
      var1.getlocal(0).__getattr__("_make_active").__call__(var2);
      var1.setline(1502);
      var1.getglobal("super").__call__(var2, var1.getglobal("ChildSocket"), var1.getlocal(0)).__getattr__("close").__call__(var2);
      var1.setline(1503);
      PyObject var3 = var1.getlocal(0).__getattr__("open_count");
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1504);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(1505);
         if (var1.getlocal(0).__getattr__("accepted").__nonzero__()) {
            label39: {
               ContextManager var8;
               PyObject var4 = (var8 = ContextGuard.getManager(var1.getlocal(0).__getattr__("parent_socket").__getattr__("open_lock"))).__enter__(var2);

               try {
                  var1.setline(1507);
                  var10000 = var1.getlocal(0).__getattr__("parent_socket");
                  String var9 = "accepted_children";
                  PyObject var5 = var10000;
                  PyObject var6 = var5.__getattr__(var9);
                  var6 = var6._isub(Py.newInteger(1));
                  var5.__setattr__(var9, var6);
                  var1.setline(1508);
                  var4 = var1.getlocal(0).__getattr__("parent_socket").__getattr__("open_count");
                  var10000 = var4._eq(Py.newInteger(0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var4 = var1.getlocal(0).__getattr__("parent_socket").__getattr__("accepted_children");
                     var10000 = var4._eq(Py.newInteger(0));
                     var4 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(1509);
                     var10000 = var1.getglobal("log").__getattr__("debug");
                     PyObject[] var11 = new PyObject[]{PyString.fromInterned("Shutting down child group for parent socket=%s accepted_children=%s"), var1.getlocal(0).__getattr__("parent_socket"), var1.getlocal(0).__getattr__("parent_socket").__getattr__("accepted_children"), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), var1.getlocal(0)})};
                     String[] var10 = new String[]{"extra"};
                     var10000.__call__(var2, var11, var10);
                     var4 = null;
                     var1.setline(1511);
                     var1.getlocal(0).__getattr__("parent_socket").__getattr__("child_group").__getattr__("shutdownGracefully").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)Py.newInteger(100), (PyObject)var1.getglobal("TimeUnit").__getattr__("MILLISECONDS"));
                  }
               } catch (Throwable var7) {
                  if (var8.__exit__(var2, Py.setException(var7, var1))) {
                     break label39;
                  }

                  throw (Throwable)Py.makeException();
               }

               var8.__exit__(var2, (PyException)null);
            }
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject shutdown$128(PyFrame var1, ThreadState var2) {
      var1.setline(1514);
      var1.getlocal(0).__getattr__("_make_active").__call__(var2);
      var1.setline(1515);
      var1.getglobal("super").__call__(var2, var1.getglobal("ChildSocket"), var1.getlocal(0)).__getattr__("shutdown").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __del__$129(PyFrame var1, ThreadState var2) {
      var1.setline(1523);
      var1.getlocal(0).__getattr__("_make_active").__call__(var2);
      var1.setline(1524);
      var1.getlocal(0).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject select$130(PyFrame var1, ThreadState var2) {
      var1.setline(1530);
      PyObject var3 = (new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)})).__iter__();

      do {
         var1.setline(1530);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1533);
            var3 = var1.getlocal(3);
            PyObject var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("Number"));
            }

            if (var10000.__not__().__nonzero__()) {
               var1.setline(1534);
               throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("timeout must be a float or None")));
            } else {
               var1.setline(1535);
               var3 = var1.getlocal(3);
               var10000 = var3._isnot(var1.getglobal("None"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(3);
                  var10000 = var3._lt(Py.newInteger(0));
                  var3 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(1536);
                  throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("EINVAL"), (PyObject)PyString.fromInterned("Invalid argument")));
               } else {
                  var1.setline(1537);
                  var3 = var1.getglobal("_Select").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)).__call__(var2, var1.getlocal(3));
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }

         var1.setlocal(4, var4);
         var1.setline(1531);
      } while(!var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("Iterable")).__not__().__nonzero__());

      var1.setline(1532);
      throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("arguments 1-3 must be sequences")));
   }

   public PyObject create_connection$131(PyFrame var1, ThreadState var2) {
      var1.setline(1552);
      PyString.fromInterned("Connect to *address* and return the socket object.\n\n    Convenience function.  Connect to *address* (a 2-tuple ``(host,\n    port)``) and return the socket object.  Passing the optional\n    *timeout* parameter will set the timeout on the socket instance\n    before attempting to connect.  If no *timeout* is supplied, the\n    global default timeout setting returned by :func:`getdefaulttimeout`\n    is used.  If *source_address* is set it must be a tuple of (host, port)\n    for the socket to bind as a source address before making the connection.\n    An host of '' or port 0 tells the OS to use the default.\n    ");
      var1.setline(1554);
      PyObject var3 = var1.getlocal(0);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(1555);
      var3 = var1.getglobal("None");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1556);
      var3 = var1.getglobal("getaddrinfo").__call__(var2, var1.getlocal(3), var1.getlocal(4), Py.newInteger(0), var1.getglobal("SOCK_STREAM")).__iter__();

      while(true) {
         var1.setline(1556);
         PyObject var9 = var3.__iternext__();
         PyObject var10000;
         if (var9 == null) {
            var1.setline(1573);
            var3 = var1.getlocal(5);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1574);
               throw Py.makeException(var1.getlocal(5));
            }

            var1.setline(1576);
            throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("getaddrinfo returns an empty list")));
         }

         var1.setlocal(6, var9);
         var1.setline(1557);
         var5 = var1.getlocal(6);
         PyObject[] var6 = Py.unpackSequence(var5, 5);
         PyObject var7 = var6[0];
         var1.setlocal(7, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(8, var7);
         var7 = null;
         var7 = var6[2];
         var1.setlocal(9, var7);
         var7 = null;
         var7 = var6[3];
         var1.setlocal(10, var7);
         var7 = null;
         var7 = var6[4];
         var1.setlocal(11, var7);
         var7 = null;
         var5 = null;
         var1.setline(1558);
         var5 = var1.getglobal("None");
         var1.setlocal(12, var5);
         var5 = null;

         try {
            var1.setline(1560);
            var5 = var1.getglobal("socket").__call__(var2, var1.getlocal(7), var1.getlocal(8), var1.getlocal(9));
            var1.setlocal(12, var5);
            var5 = null;
            var1.setline(1561);
            var5 = var1.getlocal(1);
            var10000 = var5._isnot(var1.getglobal("_GLOBAL_DEFAULT_TIMEOUT"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1562);
               var1.getlocal(12).__getattr__("settimeout").__call__(var2, var1.getlocal(1));
            }

            var1.setline(1563);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(1564);
               var1.getlocal(12).__getattr__("bind").__call__(var2, var1.getlocal(2));
            }

            var1.setline(1565);
            var1.getlocal(12).__getattr__("connect").__call__(var2, var1.getlocal(11));
            var1.setline(1566);
            var5 = var1.getlocal(12);
            var1.f_lasti = -1;
            return var5;
         } catch (Throwable var8) {
            PyException var10 = Py.setException(var8, var1);
            if (!var10.match(var1.getglobal("error"))) {
               throw var10;
            }

            var7 = var10.value;
            var1.setlocal(13, var7);
            var7 = null;
            var1.setline(1569);
            var7 = var1.getlocal(13);
            var1.setlocal(5, var7);
            var7 = null;
            var1.setline(1570);
            var7 = var1.getlocal(12);
            var10000 = var7._isnot(var1.getglobal("None"));
            var7 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1571);
               var1.getlocal(12).__getattr__("close").__call__(var2);
            }
         }
      }
   }

   public PyObject _calctimeoutvalue$132(PyFrame var1, ThreadState var2) {
      var1.setline(1584);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1585);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         PyObject var4;
         try {
            var1.setline(1587);
            var4 = var1.getglobal("float").__call__(var2, var1.getlocal(0));
            var1.setlocal(1, var4);
            var4 = null;
         } catch (Throwable var5) {
            Py.setException(var5, var1);
            var1.setline(1589);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Socket timeout value must be a number or None")));
         }

         var1.setline(1590);
         var4 = var1.getlocal(1);
         var10000 = var4._lt(Py.newFloat(0.0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1591);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Socket timeout value cannot be negative")));
         } else {
            var1.setline(1592);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject getdefaulttimeout$133(PyFrame var1, ThreadState var2) {
      var1.setline(1595);
      PyObject var3 = var1.getglobal("_defaulttimeout");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setdefaulttimeout$134(PyFrame var1, ThreadState var2) {
      var1.setline(1599);
      PyObject var3 = var1.getglobal("_calctimeoutvalue").__call__(var2, var1.getlocal(0));
      var1.setglobal("_defaulttimeout", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _ip_address_t$135(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1605);
      return var1.getf_locals();
   }

   public PyObject _ipv4_address_t$136(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1610);
      PyObject var3 = var1.getname("None");
      var1.setlocal("jaddress", var3);
      var3 = null;
      var1.setline(1612);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __new__$137, (PyObject)null);
      var1.setlocal("__new__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __new__$137(PyFrame var1, ThreadState var2) {
      var1.setline(1613);
      PyObject var3 = var1.getglobal("tuple").__getattr__("__new__").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1614);
      var3 = var1.getlocal(3);
      var1.getlocal(4).__setattr__("jaddress", var3);
      var3 = null;
      var1.setline(1615);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _ipv6_address_t$138(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1619);
      PyObject var3 = var1.getname("None");
      var1.setlocal("jaddress", var3);
      var3 = null;
      var1.setline(1621);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __new__$139, (PyObject)null);
      var1.setlocal("__new__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __new__$139(PyFrame var1, ThreadState var2) {
      var1.setline(1622);
      PyObject var3 = var1.getglobal("tuple").__getattr__("__new__").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), Py.newInteger(0), var1.getlocal(3).__getattr__("scopeId")})));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1623);
      var3 = var1.getlocal(3);
      var1.getlocal(4).__setattr__("jaddress", var3);
      var3 = null;
      var1.setline(1624);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_jsockaddr$140(PyFrame var1, ThreadState var2) {
      var1.setline(1628);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1629);
         var3 = var1.getglobal("AF_UNSPEC");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1630);
      var3 = var1.getlocal(2);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyInteger var5;
      if (var10000.__nonzero__()) {
         var1.setline(1631);
         var5 = Py.newInteger(0);
         var1.setlocal(2, var5);
         var3 = null;
      }

      var1.setline(1632);
      var3 = var1.getlocal(3);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1633);
         var5 = Py.newInteger(0);
         var1.setlocal(3, var5);
         var3 = null;
      }

      var1.setline(1634);
      var10000 = var1.getglobal("_get_jsockaddr2");
      PyObject[] var6 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)};
      var3 = var10000.__call__(var2, var6);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1635);
      var10000 = var1.getglobal("log").__getattr__("debug");
      var6 = new PyObject[]{PyString.fromInterned("Address %s for %s"), var1.getlocal(5), var1.getlocal(0), new PyDictionary(new PyObject[]{PyString.fromInterned("sock"), PyString.fromInterned("*")})};
      String[] var4 = new String[]{"extra"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(1636);
      var3 = var1.getlocal(5);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_jsockaddr2$141(PyFrame var1, ThreadState var2) {
      var1.setline(1640);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("_ip_address_t")).__nonzero__()) {
         var1.setline(1641);
         var3 = var1.getglobal("java").__getattr__("net").__getattr__("InetSocketAddress").__call__(var2, var1.getlocal(0).__getattr__("jaddress"), var1.getlocal(0).__getitem__(Py.newInteger(1)));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1644);
         PyObject var4 = var1.getlocal(0);
         PyObject var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1645);
            PyTuple var5 = new PyTuple(new PyObject[]{PyString.fromInterned(""), Py.newInteger(0)});
            var1.setlocal(0, var5);
            var4 = null;
         }

         var1.setline(1646);
         PyString var6 = PyString.fromInterned("Address must be a 2-tuple (ipv4: (host, port)) or a 4-tuple (ipv6: (host, port, flow, scope))");
         var1.setlocal(5, var6);
         var4 = null;
         var1.setline(1647);
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("tuple")).__not__();
         if (!var10000.__nonzero__()) {
            var4 = var1.getlocal(1);
            var10000 = var4._eq(var1.getglobal("AF_INET"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
               var10000 = var4._ne(Py.newInteger(2));
               var4 = null;
            }

            if (!var10000.__nonzero__()) {
               var4 = var1.getlocal(1);
               var10000 = var4._eq(var1.getglobal("AF_INET6"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
                  var10000 = var4._notin(new PyList(new PyObject[]{Py.newInteger(2), Py.newInteger(4)}));
                  var4 = null;
               }
            }

            if (!var10000.__nonzero__()) {
               var10000 = var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getitem__(Py.newInteger(0)), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("basestring"), var1.getglobal("NoneType")}))).__not__();
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getitem__(Py.newInteger(1)), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__not__();
               }
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(1652);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2, var1.getlocal(5)));
         } else {
            var1.setline(1653);
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
            var10000 = var4._eq(Py.newInteger(4));
            var4 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getitem__(Py.newInteger(3)), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__not__();
            }

            if (var10000.__nonzero__()) {
               var1.setline(1654);
               throw Py.makeException(var1.getglobal("TypeError").__call__(var2, var1.getlocal(5)));
            } else {
               var1.setline(1655);
               var4 = var1.getlocal(0).__getitem__(Py.newInteger(0));
               var1.setlocal(6, var4);
               var4 = null;
               var1.setline(1656);
               var4 = var1.getlocal(6);
               var10000 = var4._isnot(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1657);
                  var4 = var1.getlocal(6).__getattr__("strip").__call__(var2);
                  var1.setlocal(6, var4);
                  var4 = null;
               }

               var1.setline(1658);
               var4 = var1.getlocal(0).__getitem__(Py.newInteger(1));
               var1.setlocal(7, var4);
               var4 = null;
               var1.setline(1659);
               var4 = var1.getlocal(1);
               var10000 = var4._eq(var1.getglobal("AF_INET"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var4 = var1.getlocal(2);
                  var10000 = var4._eq(var1.getglobal("SOCK_DGRAM"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var4 = var1.getlocal(6);
                     var10000 = var4._eq(PyString.fromInterned("<broadcast>"));
                     var4 = null;
                  }
               }

               if (var10000.__nonzero__()) {
                  var1.setline(1660);
                  var4 = var1.getglobal("INADDR_BROADCAST");
                  var1.setlocal(6, var4);
                  var4 = null;
               }

               var1.setline(1661);
               var4 = var1.getlocal(6);
               var10000 = var4._in(new PyList(new PyObject[]{PyString.fromInterned(""), var1.getglobal("None")}));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1662);
                  if (var1.getlocal(4)._and(var1.getglobal("AI_PASSIVE")).__nonzero__()) {
                     var1.setline(1663);
                     var4 = (new PyDictionary(new PyObject[]{var1.getglobal("AF_INET"), var1.getglobal("INADDR_ANY"), var1.getglobal("AF_INET6"), var1.getglobal("IN6ADDR_ANY_INIT")})).__getitem__(var1.getlocal(1));
                     var1.setlocal(6, var4);
                     var4 = null;
                  } else {
                     var1.setline(1665);
                     var6 = PyString.fromInterned("localhost");
                     var1.setlocal(6, var6);
                     var4 = null;
                  }
               }

               var1.setline(1666);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(6), var1.getglobal("unicode")).__nonzero__()) {
                  var1.setline(1667);
                  var4 = var1.getglobal("encodings").__getattr__("idna").__getattr__("ToASCII").__call__(var2, var1.getlocal(6));
                  var1.setlocal(6, var4);
                  var4 = null;
               }

               var1.setline(1668);
               var10000 = var1.getglobal("getaddrinfo");
               PyObject[] var7 = new PyObject[]{var1.getlocal(6), var1.getlocal(7), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)};
               var4 = var10000.__call__(var2, var7);
               var1.setlocal(8, var4);
               var4 = null;
               var1.setline(1669);
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(8));
               var10000 = var4._eq(Py.newInteger(0));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1670);
                  throw Py.makeException(var1.getglobal("gaierror").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("EGETADDRINFOFAILED"), (PyObject)PyString.fromInterned("getaddrinfo failed")));
               } else {
                  var1.setline(1671);
                  var3 = var1.getglobal("java").__getattr__("net").__getattr__("InetSocketAddress").__call__(var2, var1.getlocal(8).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(4)).__getattr__("jaddress"), var1.getlocal(7));
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      }
   }

   public PyObject _is_ip_address$142(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(1676);
         var1.getglobal("_google_ipaddr_r234").__getattr__("IPAddress").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.setline(1677);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("ValueError"))) {
            var1.setline(1679);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject is_ipv4_address$143(PyFrame var1, ThreadState var2) {
      var1.setline(1683);
      PyObject var3 = var1.getglobal("_is_ip_address").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)Py.newInteger(4));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_ipv6_address$144(PyFrame var1, ThreadState var2) {
      var1.setline(1687);
      PyObject var3 = var1.getglobal("_is_ip_address").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)Py.newInteger(6));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_ip_address$145(PyFrame var1, ThreadState var2) {
      var1.setline(1691);
      PyObject var3 = var1.getglobal("_is_ip_address").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _use_ipv4_addresses_only$146(PyFrame var1, ThreadState var2) {
      var1.setline(1701);
      PyObject var3 = var1.getlocal(0);
      var1.setglobal("_ipv4_addresses_only", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _getaddrinfo_get_host$147(PyFrame var1, ThreadState var2) {
      var1.setline(1705);
      PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("basestring")).__not__();
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1706);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("getaddrinfo() argument 1 must be string or None")));
      } else {
         var1.setline(1707);
         if (var1.getlocal(2)._and(var1.getglobal("AI_NUMERICHOST")).__nonzero__()) {
            var1.setline(1708);
            if (var1.getglobal("is_ip_address").__call__(var2, var1.getlocal(0)).__not__().__nonzero__()) {
               var1.setline(1709);
               throw Py.makeException(var1.getglobal("gaierror").__call__((ThreadState)var2, (PyObject)var1.getglobal("EAI_NONAME"), (PyObject)PyString.fromInterned("Name or service not known")));
            }

            var1.setline(1710);
            var3 = var1.getlocal(1);
            var10000 = var3._eq(var1.getglobal("AF_INET"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("is_ipv4_address").__call__(var2, var1.getlocal(0)).__not__();
            }

            if (var10000.__nonzero__()) {
               var1.setline(1711);
               throw Py.makeException(var1.getglobal("gaierror").__call__((ThreadState)var2, (PyObject)var1.getglobal("EAI_ADDRFAMILY"), (PyObject)PyString.fromInterned("Address family for hostname not supported")));
            }

            var1.setline(1712);
            var3 = var1.getlocal(1);
            var10000 = var3._eq(var1.getglobal("AF_INET6"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("is_ipv6_address").__call__(var2, var1.getlocal(0)).__not__();
            }

            if (var10000.__nonzero__()) {
               var1.setline(1713);
               throw Py.makeException(var1.getglobal("gaierror").__call__((ThreadState)var2, (PyObject)var1.getglobal("EAI_ADDRFAMILY"), (PyObject)PyString.fromInterned("Address family for hostname not supported")));
            }
         }

         var1.setline(1714);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("unicode")).__nonzero__()) {
            var1.setline(1715);
            var3 = var1.getglobal("encodings").__getattr__("idna").__getattr__("ToASCII").__call__(var2, var1.getlocal(0));
            var1.setlocal(0, var3);
            var3 = null;
         }

         var1.setline(1716);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _getaddrinfo_get_port$148(PyFrame var1, ThreadState var2) {
      var1.setline(1720);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("basestring")).__nonzero__()) {
         try {
            var1.setline(1722);
            var3 = var1.getglobal("int").__call__(var2, var1.getlocal(0));
            var1.setlocal(2, var3);
            var3 = null;
         } catch (Throwable var6) {
            PyException var9 = Py.setException(var6, var1);
            if (!var9.match(var1.getglobal("ValueError"))) {
               throw var9;
            }

            var1.setline(1724);
            if (var1.getlocal(1)._and(var1.getglobal("AI_NUMERICSERV")).__nonzero__()) {
               var1.setline(1725);
               throw Py.makeException(var1.getglobal("gaierror").__call__((ThreadState)var2, (PyObject)var1.getglobal("EAI_NONAME"), (PyObject)PyString.fromInterned("Name or service not known")));
            }

            PyException var4;
            try {
               var1.setline(1728);
               PyObject var8 = var1.getglobal("getservbyname").__call__(var2, var1.getlocal(0));
               var1.setlocal(2, var8);
               var4 = null;
            } catch (Throwable var5) {
               var4 = Py.setException(var5, var1);
               if (var4.match(var1.getglobal("error"))) {
                  var1.setline(1730);
                  throw Py.makeException(var1.getglobal("gaierror").__call__((ThreadState)var2, (PyObject)var1.getglobal("EAI_SERVICE"), (PyObject)PyString.fromInterned("Servname not supported for ai_socktype")));
               }

               throw var4;
            }
         }
      } else {
         var1.setline(1731);
         var3 = var1.getlocal(0);
         PyObject var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1732);
            PyInteger var7 = Py.newInteger(0);
            var1.setlocal(2, var7);
            var3 = null;
         } else {
            var1.setline(1733);
            if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__not__().__nonzero__()) {
               var1.setline(1734);
               throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Int or String expected")));
            }

            var1.setline(1736);
            var3 = var1.getglobal("int").__call__(var2, var1.getlocal(0));
            var1.setlocal(2, var3);
            var3 = null;
         }
      }

      var1.setline(1737);
      var3 = var1.getlocal(2)._mod(Py.newInteger(65536));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getaddrinfo$149(PyFrame var1, ThreadState var2) {
      var1.setline(1742);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1743);
         var3 = var1.getglobal("AF_UNSPEC");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1744);
      var3 = var1.getlocal(3);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1745);
         PyInteger var11 = Py.newInteger(0);
         var1.setlocal(3, var11);
         var3 = null;
      }

      var1.setline(1746);
      var3 = var1.getlocal(2);
      var10000 = var3._in(new PyList(new PyObject[]{var1.getglobal("AF_INET"), var1.getglobal("AF_INET6"), var1.getglobal("AF_UNSPEC")}));
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(1747);
         throw Py.makeException(var1.getglobal("gaierror").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("EIO"), (PyObject)PyString.fromInterned("ai_family not supported")));
      } else {
         var1.setline(1748);
         var3 = var1.getglobal("_getaddrinfo_get_host").__call__(var2, var1.getlocal(0), var1.getlocal(2), var1.getlocal(5));
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(1749);
         var3 = var1.getglobal("_getaddrinfo_get_port").__call__(var2, var1.getlocal(1), var1.getlocal(5));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(1750);
         var3 = var1.getlocal(3);
         var10000 = var3._notin(new PyList(new PyObject[]{Py.newInteger(0), var1.getglobal("SOCK_DGRAM"), var1.getglobal("SOCK_STREAM")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1751);
            throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("ESOCKTNOSUPPORT"), (PyObject)PyString.fromInterned("Socket type is not supported")));
         } else {
            var1.setline(1752);
            PyList var13 = new PyList(Py.EmptyObjects);
            var1.setlocal(6, var13);
            var3 = null;
            var1.setline(1753);
            var10000 = var1.getlocal(6).__getattr__("append");
            PyObject[] var10004 = new PyObject[]{var1.getglobal("AF_INET"), null, null, null, null, null};
            var1.setline(1754);
            PyObject[] var14 = Py.EmptyObjects;
            var10004[1] = new PyFunction(var1.f_globals, var14, f$150);
            var10004[2] = var1.getglobal("AF_INET6");
            var1.setline(1755);
            var14 = Py.EmptyObjects;
            var10004[3] = new PyFunction(var1.f_globals, var14, f$151);
            var10004[4] = var1.getglobal("AF_UNSPEC");
            var1.setline(1756);
            var14 = Py.EmptyObjects;
            var10004[5] = new PyFunction(var1.f_globals, var14, f$152);
            var10000.__call__(var2, (new PyDictionary(var10004)).__getitem__(var1.getlocal(2)));
            var1.setline(1758);
            var3 = var1.getlocal(0);
            var10000 = var3._in(new PyList(new PyObject[]{var1.getglobal("None"), PyString.fromInterned("")}));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1759);
               if (var1.getlocal(5)._and(var1.getglobal("AI_PASSIVE")).__nonzero__()) {
                  var1.setline(1760);
                  var3 = (new PyDictionary(new PyObject[]{var1.getglobal("AF_INET"), new PyList(new PyObject[]{var1.getglobal("INADDR_ANY")}), var1.getglobal("AF_INET6"), new PyList(new PyObject[]{var1.getglobal("IN6ADDR_ANY_INIT")}), var1.getglobal("AF_UNSPEC"), new PyList(new PyObject[]{var1.getglobal("INADDR_ANY"), var1.getglobal("IN6ADDR_ANY_INIT")})})).__getitem__(var1.getlocal(2));
                  var1.setlocal(7, var3);
                  var3 = null;
               } else {
                  var1.setline(1762);
                  var13 = new PyList(new PyObject[]{PyString.fromInterned("localhost")});
                  var1.setlocal(7, var13);
                  var3 = null;
               }
            } else {
               var1.setline(1764);
               var13 = new PyList(new PyObject[]{var1.getlocal(0)});
               var1.setlocal(7, var13);
               var3 = null;
            }

            var1.setline(1765);
            var13 = new PyList(Py.EmptyObjects);
            var1.setlocal(8, var13);
            var3 = null;
            var1.setline(1766);
            var3 = var1.getlocal(7).__iter__();

            label93:
            while(true) {
               var1.setline(1766);
               PyObject var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(1792);
                  var3 = var1.getlocal(8);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(9, var4);

               PyException var5;
               PyObject var12;
               try {
                  var1.setline(1768);
                  var12 = var1.getglobal("java").__getattr__("net").__getattr__("InetAddress").__getattr__("getAllByName").__call__(var2, var1.getlocal(9));
                  var1.setlocal(10, var12);
                  var5 = null;
               } catch (Throwable var10) {
                  var5 = Py.setException(var10, var1);
                  if (var5.match(var1.getglobal("java").__getattr__("net").__getattr__("UnknownHostException"))) {
                     var1.setline(1770);
                     throw Py.makeException(var1.getglobal("gaierror").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("ENOEXEC"), (PyObject)PyString.fromInterned("nodename nor servname provided, or not known")));
                  }

                  throw var5;
               }

               var1.setline(1772);
               var12 = var1.getlocal(10).__iter__();

               while(true) {
                  PyObject var7;
                  PyObject var8;
                  PyList var10002;
                  do {
                     var1.setline(1772);
                     PyObject var6 = var12.__iternext__();
                     if (var6 == null) {
                        continue label93;
                     }

                     var1.setlocal(11, var6);
                     var1.setline(1773);
                     var10000 = var1.getglobal("len");
                     var10002 = new PyList();
                     var7 = var10002.__getattr__("append");
                     var1.setlocal(12, var7);
                     var7 = null;
                     var1.setline(1773);
                     var7 = var1.getlocal(6).__iter__();

                     while(true) {
                        var1.setline(1773);
                        var8 = var7.__iternext__();
                        if (var8 == null) {
                           var1.setline(1773);
                           var1.dellocal(12);
                           break;
                        }

                        var1.setlocal(13, var8);
                        var1.setline(1773);
                        if (var1.getlocal(13).__call__(var2, var1.getlocal(11)).__nonzero__()) {
                           var1.setline(1773);
                           var1.getlocal(12).__call__(var2, var1.getlocal(13));
                        }
                     }
                  } while(!var10000.__call__((ThreadState)var2, (PyObject)var10002).__nonzero__());

                  var1.setline(1774);
                  var7 = (new PyDictionary(new PyObject[]{var1.getglobal("java").__getattr__("net").__getattr__("Inet4Address"), var1.getglobal("AF_INET"), var1.getglobal("java").__getattr__("net").__getattr__("Inet6Address"), var1.getglobal("AF_INET6")})).__getitem__(var1.getlocal(11).__getattr__("getClass").__call__(var2));
                  var1.setlocal(2, var7);
                  var7 = null;
                  var1.setline(1775);
                  if (var1.getlocal(5)._and(var1.getglobal("AI_CANONNAME")).__nonzero__()) {
                     var1.setline(1776);
                     var7 = var1.getglobal("str").__call__(var2, var1.getlocal(11).__getattr__("getCanonicalHostName").__call__(var2));
                     var1.setlocal(14, var7);
                     var7 = null;
                  } else {
                     var1.setline(1778);
                     PyString var15 = PyString.fromInterned("");
                     var1.setlocal(14, var15);
                     var7 = null;
                  }

                  var1.setline(1779);
                  var7 = var1.getglobal("str").__call__(var2, var1.getlocal(11).__getattr__("getHostAddress").__call__(var2));
                  var1.setlocal(15, var7);
                  var7 = null;
                  var1.setline(1781);
                  var7 = (new PyDictionary(new PyObject[]{var1.getglobal("AF_INET"), var1.getglobal("_ipv4_address_t"), var1.getglobal("AF_INET6"), var1.getglobal("_ipv6_address_t")})).__getitem__(var1.getlocal(2)).__call__(var2, var1.getlocal(15), var1.getlocal(1), var1.getlocal(11));
                  var1.setlocal(16, var7);
                  var7 = null;
                  var1.setline(1782);
                  var7 = var1.getlocal(3);
                  var10000 = var7._eq(Py.newInteger(0));
                  var7 = null;
                  PyList var16;
                  if (var10000.__nonzero__()) {
                     var1.setline(1783);
                     var16 = new PyList(new PyObject[]{var1.getglobal("SOCK_DGRAM"), var1.getglobal("SOCK_STREAM")});
                     var1.setlocal(17, var16);
                     var7 = null;
                  } else {
                     var1.setline(1785);
                     var16 = new PyList(new PyObject[]{var1.getlocal(3)});
                     var1.setlocal(17, var16);
                     var7 = null;
                  }

                  var1.setline(1786);
                  var7 = var1.getlocal(17).__iter__();

                  while(true) {
                     var1.setline(1786);
                     var8 = var7.__iternext__();
                     if (var8 == null) {
                        break;
                     }

                     var1.setlocal(18, var8);
                     var1.setline(1787);
                     PyObject var9 = (new PyDictionary(new PyObject[]{var1.getglobal("SOCK_DGRAM"), var1.getglobal("IPPROTO_UDP"), var1.getglobal("SOCK_STREAM"), var1.getglobal("IPPROTO_TCP")})).__getitem__(var1.getlocal(18));
                     var1.setlocal(19, var9);
                     var9 = null;
                     var1.setline(1788);
                     var9 = var1.getlocal(4);
                     var10000 = var9._in(new PyList(new PyObject[]{Py.newInteger(0), var1.getlocal(19)}));
                     var9 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(1791);
                        var1.getlocal(8).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(18), var1.getlocal(19), var1.getlocal(14), var1.getlocal(16)})));
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject f$150(PyFrame var1, ThreadState var2) {
      var1.setline(1754);
      PyObject var3 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("java").__getattr__("net").__getattr__("Inet4Address"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$151(PyFrame var1, ThreadState var2) {
      var1.setline(1755);
      PyObject var3 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("java").__getattr__("net").__getattr__("Inet6Address"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$152(PyFrame var1, ThreadState var2) {
      var1.setline(1756);
      PyObject var3 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("java").__getattr__("net").__getattr__("InetAddress"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject htons$153(PyFrame var1, ThreadState var2) {
      var1.setline(1796);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject htonl$154(PyFrame var1, ThreadState var2) {
      var1.setline(1797);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ntohs$155(PyFrame var1, ThreadState var2) {
      var1.setline(1798);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ntohl$156(PyFrame var1, ThreadState var2) {
      var1.setline(1799);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject inet_pton$157(PyFrame var1, ThreadState var2) {
      var1.setline(1803);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(var1.getglobal("AF_INET"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1804);
         if (var1.getglobal("is_ipv4_address").__call__(var2, var1.getlocal(1)).__not__().__nonzero__()) {
            var1.setline(1805);
            throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("illegal IP address string passed to inet_pton")));
         }
      } else {
         var1.setline(1806);
         var3 = var1.getlocal(0);
         var10000 = var3._eq(var1.getglobal("AF_INET6"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(1810);
            throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)var1.getglobal("errno").__getattr__("EAFNOSUPPORT"), (PyObject)PyString.fromInterned("Address family not supported by protocol")));
         }

         var1.setline(1807);
         if (var1.getglobal("is_ipv6_address").__call__(var2, var1.getlocal(1)).__not__().__nonzero__()) {
            var1.setline(1808);
            throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("illegal IP address string passed to inet_pton")));
         }
      }

      var1.setline(1811);
      var3 = var1.getglobal("java").__getattr__("net").__getattr__("InetAddress").__getattr__("getByName").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1812);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(1813);
      var3 = var1.getlocal(2).__getattr__("getAddress").__call__(var2).__iter__();

      while(true) {
         var1.setline(1813);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1818);
            var10000 = PyString.fromInterned("").__getattr__("join");
            PyList var10002 = new PyList();
            var3 = var10002.__getattr__("append");
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(1818);
            var3 = var1.getlocal(3).__iter__();

            while(true) {
               var1.setline(1818);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(1818);
                  var1.dellocal(5);
                  var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(4, var4);
               var1.setline(1818);
               var1.getlocal(5).__call__(var2, var1.getglobal("chr").__call__(var2, var1.getlocal(4)));
            }
         }

         var1.setlocal(4, var4);
         var1.setline(1814);
         PyObject var5 = var1.getlocal(4);
         var10000 = var5._lt(Py.newInteger(0));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1815);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(4)._add(Py.newInteger(256)));
         } else {
            var1.setline(1817);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(4));
         }
      }
   }

   public PyObject inet_ntop$158(PyFrame var1, ThreadState var2) {
      var1.setline(1822);
      PyObject var3 = var1.getglobal("array").__getattr__("array").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("b"), (PyObject)var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1823);
      var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(var1.getglobal("AF_INET"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1824);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         var10000 = var3._ne(Py.newInteger(4));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1825);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("invalid length of packed IP address string")));
         }
      } else {
         var1.setline(1826);
         var3 = var1.getlocal(0);
         var10000 = var3._eq(var1.getglobal("AF_INET6"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(1830);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("unknown address family %s")._mod(var1.getlocal(0))));
         }

         var1.setline(1827);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         var10000 = var3._ne(Py.newInteger(16));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1828);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("invalid length of packed IP address string")));
         }
      }

      var1.setline(1831);
      var3 = var1.getglobal("java").__getattr__("net").__getattr__("InetAddress").__getattr__("getByAddress").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1832);
      var3 = var1.getlocal(3).__getattr__("getHostAddress").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject inet_aton$159(PyFrame var1, ThreadState var2) {
      var1.setline(1835);
      PyObject var3 = var1.getglobal("inet_pton").__call__(var2, var1.getglobal("AF_INET"), var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject inet_ntoa$160(PyFrame var1, ThreadState var2) {
      var1.setline(1838);
      PyObject var3 = var1.getglobal("inet_ntop").__call__(var2, var1.getglobal("AF_INET"), var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _gethostbyaddr$161(PyFrame var1, ThreadState var2) {
      var1.setline(1847);
      PyObject var3 = var1.getglobal("InetAddress").__getattr__("getAllByName").__call__(var2, var1.getglobal("gethostbyname").__call__(var2, var1.getlocal(0)));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1848);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(1849);
      var5 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(1850);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(1850);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1853);
            PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)});
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(4, var4);
         var1.setline(1851);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(4).__getattr__("getHostName").__call__(var2)));
         var1.setline(1852);
         var1.getlocal(3).__getattr__("append").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(4).__getattr__("getHostAddress").__call__(var2)));
      }
   }

   public PyObject getfqdn$162(PyFrame var1, ThreadState var2) {
      var1.setline(1864);
      PyString.fromInterned("\n    Return a fully qualified domain name for name. If name is omitted or empty\n    it is interpreted as the local host.  To find the fully qualified name,\n    the hostname returned by gethostbyaddr() is checked, then aliases for the\n    host, if available. The first name which includes a period is selected.\n    In case no fully qualified domain name is available, the hostname is retur\n    New in version 2.0.\n    ");
      var1.setline(1865);
      PyObject var3;
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(1866);
         var3 = var1.getglobal("gethostname").__call__(var2);
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(1867);
      var3 = var1.getglobal("_gethostbyaddr").__call__(var2, var1.getlocal(0));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(1868);
      var3 = var1.getlocal(1).__iter__();

      PyObject var10000;
      do {
         var1.setline(1868);
         PyObject var6 = var3.__iternext__();
         if (var6 == null) {
            var1.setline(1871);
            var5 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(3, var6);
         var1.setline(1869);
         var5 = var1.getlocal(3).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
         var10000 = var5._ge(Py.newInteger(0));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(1870);
      var5 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject gethostname$163(PyFrame var1, ThreadState var2) {
      var1.setline(1875);
      PyObject var3 = var1.getglobal("str").__call__(var2, var1.getglobal("InetAddress").__getattr__("getLocalHost").__call__(var2).__getattr__("getHostName").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject gethostbyname$164(PyFrame var1, ThreadState var2) {
      var1.setline(1879);
      PyObject var3 = var1.getglobal("str").__call__(var2, var1.getglobal("InetAddress").__getattr__("getByName").__call__(var2, var1.getlocal(0)).__getattr__("getHostAddress").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject gethostbyname_ex$165(PyFrame var1, ThreadState var2) {
      var1.setline(1888);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0), new PyList(Py.EmptyObjects), var1.getglobal("gethostbyname").__call__(var2, var1.getlocal(0))});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject gethostbyaddr$166(PyFrame var1, ThreadState var2) {
      var1.setline(1892);
      PyObject var3 = var1.getglobal("_gethostbyaddr").__call__(var2, var1.getlocal(0));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(1893);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(1).__getitem__(Py.newInteger(0)), var1.getlocal(1), var1.getlocal(2)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject getservbyname$167(PyFrame var1, ThreadState var2) {
      var1.setline(1900);
      PyObject var3 = var1.getglobal("Service").__getattr__("getServiceByName").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1901);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1902);
         throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("service/proto not found")));
      } else {
         var1.setline(1903);
         var3 = var1.getlocal(2).__getattr__("getPort").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getservbyport$168(PyFrame var1, ThreadState var2) {
      var1.setline(1906);
      PyObject var3 = var1.getglobal("Service").__getattr__("getServiceByPort").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1907);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1908);
         throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("port/proto not found")));
      } else {
         var1.setline(1909);
         var3 = var1.getlocal(2).__getattr__("getName").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getprotobyname$169(PyFrame var1, ThreadState var2) {
      var1.setline(1912);
      PyObject var3 = var1.getglobal("Protocol").__getattr__("getProtocolByName").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1913);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1914);
         throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("protocol not found")));
      } else {
         var1.setline(1915);
         var3 = var1.getlocal(1).__getattr__("getProto").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getservbyname$170(PyFrame var1, ThreadState var2) {
      var1.setline(1919);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getservbyport$171(PyFrame var1, ThreadState var2) {
      var1.setline(1922);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getprotobyname$172(PyFrame var1, ThreadState var2) {
      var1.setline(1925);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _getnameinfo_get_host$173(PyFrame var1, ThreadState var2) {
      var1.setline(1929);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("basestring")).__not__().__nonzero__()) {
         var1.setline(1930);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("getnameinfo() address 1 must be string, not None")));
      } else {
         var1.setline(1931);
         PyObject var3;
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("unicode")).__nonzero__()) {
            var1.setline(1932);
            var3 = var1.getglobal("encodings").__getattr__("idna").__getattr__("ToASCII").__call__(var2, var1.getlocal(0));
            var1.setlocal(0, var3);
            var3 = null;
         }

         var1.setline(1933);
         var3 = var1.getglobal("InetAddress").__getattr__("getByName").__call__(var2, var1.getlocal(0));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1934);
         var3 = var1.getlocal(2).__getattr__("getCanonicalHostName").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1935);
         if (var1.getlocal(1)._and(var1.getglobal("NI_NAMEREQD")).__nonzero__()) {
            var1.setline(1936);
            if (var1.getglobal("is_ip_address").__call__(var2, var1.getlocal(3)).__nonzero__()) {
               var1.setline(1937);
               throw Py.makeException(var1.getglobal("gaierror").__call__((ThreadState)var2, (PyObject)var1.getglobal("EAI_NONAME"), (PyObject)PyString.fromInterned("Name or service not known")));
            }
         } else {
            var1.setline(1938);
            if (var1.getlocal(1)._and(var1.getglobal("NI_NUMERICHOST")).__nonzero__()) {
               var1.setline(1939);
               var3 = var1.getlocal(2).__getattr__("getHostAddress").__call__(var2);
               var1.setlocal(3, var3);
               var3 = null;
            }
         }

         var1.setline(1941);
         if (var1.getlocal(1)._and(var1.getglobal("NI_IDN")).__nonzero__()) {
            var1.setline(1942);
            var3 = var1.getglobal("encodings").__getattr__("idna").__getattr__("ToASCII").__call__(var2, var1.getlocal(3));
            var1.setlocal(3, var3);
            var3 = null;
         }

         var1.setline(1943);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _getnameinfo_get_port$174(PyFrame var1, ThreadState var2) {
      var1.setline(1946);
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__not__().__nonzero__()) {
         var1.setline(1947);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("getnameinfo() port number must be an integer")));
      } else {
         var1.setline(1948);
         PyObject var3;
         if (var1.getlocal(1)._and(var1.getglobal("NI_NUMERICSERV")).__nonzero__()) {
            var1.setline(1949);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1950);
            PyObject var4 = var1.getglobal("None");
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(1951);
            if (var1.getlocal(1)._and(var1.getglobal("NI_DGRAM")).__nonzero__()) {
               var1.setline(1952);
               PyString var5 = PyString.fromInterned("udp");
               var1.setlocal(2, var5);
               var4 = null;
            }

            var1.setline(1953);
            var3 = var1.getglobal("getservbyport").__call__(var2, var1.getlocal(0), var1.getlocal(2));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject getnameinfo$175(PyFrame var1, ThreadState var2) {
      var1.setline(1957);
      PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("tuple")).__not__();
      PyObject var3;
      if (!var10000.__nonzero__()) {
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
         var10000 = var3._lt(Py.newInteger(2));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1958);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("getnameinfo() argument 1 must be a tuple")));
      } else {
         var1.setline(1959);
         var3 = var1.getglobal("_getnameinfo_get_host").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(0)), var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1960);
         var3 = var1.getglobal("_getnameinfo_get_port").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(1)), var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1961);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)});
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject _fileobject$176(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Faux file object attached to a socket object."));
      var1.setline(1966);
      PyString.fromInterned("Faux file object attached to a socket object.");
      var1.setline(1968);
      PyInteger var3 = Py.newInteger(8192);
      var1.setlocal("default_bufsize", var3);
      var3 = null;
      var1.setline(1969);
      PyString var5 = PyString.fromInterned("<socket>");
      var1.setlocal("name", var5);
      var3 = null;
      var1.setline(1971);
      PyList var6 = new PyList(new PyObject[]{PyString.fromInterned("mode"), PyString.fromInterned("bufsize"), PyString.fromInterned("softspace"), PyString.fromInterned("_sock"), PyString.fromInterned("_rbufsize"), PyString.fromInterned("_wbufsize"), PyString.fromInterned("_rbuf"), PyString.fromInterned("_wbuf"), PyString.fromInterned("_wbuf_len"), PyString.fromInterned("_close")});
      var1.setlocal("__slots__", var6);
      var3 = null;
      var1.setline(1976);
      PyObject[] var7 = new PyObject[]{PyString.fromInterned("rb"), Py.newInteger(-1), var1.getname("False")};
      PyFunction var8 = new PyFunction(var1.f_globals, var7, __init__$177, (PyObject)null);
      var1.setlocal("__init__", var8);
      var3 = null;
      var1.setline(2002);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _getclosed$178, (PyObject)null);
      var1.setlocal("_getclosed", var8);
      var3 = null;
      var1.setline(2004);
      PyObject var10000 = var1.getname("property");
      var7 = new PyObject[]{var1.getname("_getclosed"), PyString.fromInterned("True if the file is closed")};
      String[] var4 = new String[]{"doc"};
      var10000 = var10000.__call__(var2, var7, var4);
      var3 = null;
      PyObject var9 = var10000;
      var1.setlocal("closed", var9);
      var3 = null;
      var1.setline(2006);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, close$179, (PyObject)null);
      var1.setlocal("close", var8);
      var3 = null;
      var1.setline(2015);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, __del__$180, (PyObject)null);
      var1.setlocal("__del__", var8);
      var3 = null;
      var1.setline(2022);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, flush$181, (PyObject)null);
      var1.setlocal("flush", var8);
      var3 = null;
      var1.setline(2045);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, fileno$182, (PyObject)null);
      var1.setlocal("fileno", var8);
      var3 = null;
      var1.setline(2048);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, write$183, (PyObject)null);
      var1.setlocal("write", var8);
      var3 = null;
      var1.setline(2059);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, writelines$184, (PyObject)null);
      var1.setlocal("writelines", var8);
      var3 = null;
      var1.setline(2069);
      var7 = new PyObject[]{Py.newInteger(-1)};
      var8 = new PyFunction(var1.f_globals, var7, read$185, (PyObject)null);
      var1.setlocal("read", var8);
      var3 = null;
      var1.setline(2139);
      var7 = new PyObject[]{Py.newInteger(-1)};
      var8 = new PyFunction(var1.f_globals, var7, readline$186, (PyObject)null);
      var1.setlocal("readline", var8);
      var3 = null;
      var1.setline(2244);
      var7 = new PyObject[]{Py.newInteger(0)};
      var8 = new PyFunction(var1.f_globals, var7, readlines$187, (PyObject)null);
      var1.setlocal("readlines", var8);
      var3 = null;
      var1.setline(2259);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, __iter__$188, (PyObject)null);
      var1.setlocal("__iter__", var8);
      var3 = null;
      var1.setline(2262);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, next$189, (PyObject)null);
      var1.setlocal("next", var8);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$177(PyFrame var1, ThreadState var2) {
      var1.setline(1977);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_sock", var3);
      var3 = null;
      var1.setline(1978);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("mode", var3);
      var3 = null;
      var1.setline(1979);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1980);
         var3 = var1.getlocal(0).__getattr__("default_bufsize");
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(1981);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("bufsize", var3);
      var3 = null;
      var1.setline(1982);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("softspace", var3);
      var3 = null;
      var1.setline(1986);
      var3 = var1.getlocal(3);
      var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      PyInteger var4;
      if (var10000.__nonzero__()) {
         var1.setline(1987);
         var4 = Py.newInteger(1);
         var1.getlocal(0).__setattr__((String)"_rbufsize", var4);
         var3 = null;
      } else {
         var1.setline(1988);
         var3 = var1.getlocal(3);
         var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1989);
            var3 = var1.getlocal(0).__getattr__("default_bufsize");
            var1.getlocal(0).__setattr__("_rbufsize", var3);
            var3 = null;
         } else {
            var1.setline(1991);
            var3 = var1.getlocal(3);
            var1.getlocal(0).__setattr__("_rbufsize", var3);
            var3 = null;
         }
      }

      var1.setline(1992);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("_wbufsize", var3);
      var3 = null;
      var1.setline(1997);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.getlocal(0).__setattr__("_rbuf", var3);
      var3 = null;
      var1.setline(1998);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_wbuf", var5);
      var3 = null;
      var1.setline(1999);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_wbuf_len", var4);
      var3 = null;
      var1.setline(2000);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("_close", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _getclosed$178(PyFrame var1, ThreadState var2) {
      var1.setline(2003);
      PyObject var3 = var1.getlocal(0).__getattr__("_sock");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject close$179(PyFrame var1, ThreadState var2) {
      Object var3 = null;

      PyObject var4;
      try {
         var1.setline(2008);
         if (var1.getlocal(0).__getattr__("_sock").__nonzero__()) {
            var1.setline(2009);
            var1.getlocal(0).__getattr__("flush").__call__(var2);
         }
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(2011);
         if (var1.getlocal(0).__getattr__("_close").__nonzero__()) {
            var1.setline(2012);
            var1.getlocal(0).__getattr__("_sock").__getattr__("close").__call__(var2);
         }

         var1.setline(2013);
         var4 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("_sock", var4);
         var4 = null;
         throw (Throwable)var5;
      }

      var1.setline(2011);
      if (var1.getlocal(0).__getattr__("_close").__nonzero__()) {
         var1.setline(2012);
         var1.getlocal(0).__getattr__("_sock").__getattr__("close").__call__(var2);
      }

      var1.setline(2013);
      var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_sock", var4);
      var4 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __del__$180(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(2017);
         var1.getlocal(0).__getattr__("close").__call__(var2);
      } catch (Throwable var4) {
         Py.setException(var4, var1);
         var1.setline(2020);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject flush$181(PyFrame var1, ThreadState var2) {
      var1.setline(2023);
      if (var1.getlocal(0).__getattr__("_wbuf").__nonzero__()) {
         var1.setline(2024);
         PyObject var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_wbuf"));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(2025);
         PyList var6 = new PyList(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"_wbuf", var6);
         var3 = null;
         var1.setline(2026);
         PyInteger var7 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"_wbuf_len", var7);
         var3 = null;
         var1.setline(2027);
         var3 = var1.getglobal("max").__call__(var2, var1.getlocal(0).__getattr__("_rbufsize"), var1.getlocal(0).__getattr__("default_bufsize"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(2028);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(2029);
         var7 = Py.newInteger(0);
         var1.setlocal(4, var7);
         var3 = null;
         var1.setline(2032);
         var3 = var1.getlocal(1);
         var1.setlocal(5, var3);
         var3 = null;
         var3 = null;

         PyObject var10000;
         PyObject var4;
         try {
            while(true) {
               var1.setline(2034);
               var4 = var1.getlocal(4);
               var10000 = var4._lt(var1.getlocal(3));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  break;
               }

               var1.setline(2035);
               var4 = var1.getlocal(5).__getslice__(var1.getlocal(4), var1.getlocal(4)._add(var1.getlocal(2)), (PyObject)null);
               var1.setlocal(6, var4);
               var4 = null;
               var1.setline(2036);
               var1.getlocal(0).__getattr__("_sock").__getattr__("sendall").__call__(var2, var1.getlocal(6));
               var1.setline(2037);
               var4 = var1.getlocal(4);
               var4 = var4._iadd(var1.getlocal(2));
               var1.setlocal(4, var4);
            }
         } catch (Throwable var5) {
            Py.addTraceback(var5, var1);
            var1.setline(2039);
            var4 = var1.getlocal(4);
            var10000 = var4._lt(var1.getlocal(3));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(2040);
               var4 = var1.getlocal(1).__getslice__(var1.getlocal(4), (PyObject)null, (PyObject)null);
               var1.setlocal(7, var4);
               var4 = null;
               var1.setline(2041);
               var1.dellocal(5);
               var1.dellocal(1);
               var1.setline(2042);
               var1.getlocal(0).__getattr__("_wbuf").__getattr__("append").__call__(var2, var1.getlocal(7));
               var1.setline(2043);
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(7));
               var1.getlocal(0).__setattr__("_wbuf_len", var4);
               var4 = null;
            }

            throw (Throwable)var5;
         }

         var1.setline(2039);
         var4 = var1.getlocal(4);
         var10000 = var4._lt(var1.getlocal(3));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2040);
            var4 = var1.getlocal(1).__getslice__(var1.getlocal(4), (PyObject)null, (PyObject)null);
            var1.setlocal(7, var4);
            var4 = null;
            var1.setline(2041);
            var1.dellocal(5);
            var1.dellocal(1);
            var1.setline(2042);
            var1.getlocal(0).__getattr__("_wbuf").__getattr__("append").__call__(var2, var1.getlocal(7));
            var1.setline(2043);
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(7));
            var1.getlocal(0).__setattr__("_wbuf_len", var4);
            var4 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject fileno$182(PyFrame var1, ThreadState var2) {
      var1.setline(2046);
      PyObject var3 = var1.getlocal(0).__getattr__("_sock").__getattr__("fileno").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject write$183(PyFrame var1, ThreadState var2) {
      var1.setline(2049);
      PyObject var3 = var1.getglobal("str").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2050);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(2051);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(2052);
         var1.getlocal(0).__getattr__("_wbuf").__getattr__("append").__call__(var2, var1.getlocal(1));
         var1.setline(2053);
         PyObject var10000 = var1.getlocal(0);
         String var6 = "_wbuf_len";
         PyObject var4 = var10000;
         PyObject var5 = var4.__getattr__(var6);
         var5 = var5._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
         var4.__setattr__(var6, var5);
         var1.setline(2054);
         var3 = var1.getlocal(0).__getattr__("_wbufsize");
         var10000 = var3._eq(Py.newInteger(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(0).__getattr__("_wbufsize");
            var10000 = var3._eq(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               PyString var7 = PyString.fromInterned("\n");
               var10000 = var7._in(var1.getlocal(1));
               var3 = null;
            }

            if (!var10000.__nonzero__()) {
               var3 = var1.getlocal(0).__getattr__("_wbufsize");
               var10000 = var3._gt(Py.newInteger(1));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(0).__getattr__("_wbuf_len");
                  var10000 = var3._ge(var1.getlocal(0).__getattr__("_wbufsize"));
                  var3 = null;
               }
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(2057);
            var1.getlocal(0).__getattr__("flush").__call__(var2);
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject writelines$184(PyFrame var1, ThreadState var2) {
      var1.setline(2062);
      PyObject var3 = var1.getglobal("filter").__call__(var2, var1.getglobal("None"), var1.getglobal("map").__call__(var2, var1.getglobal("str"), var1.getlocal(1)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2063);
      PyObject var10000 = var1.getlocal(0);
      String var6 = "_wbuf_len";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var6);
      var5 = var5._iadd(var1.getglobal("sum").__call__(var2, var1.getglobal("map").__call__(var2, var1.getglobal("len"), var1.getlocal(2))));
      var4.__setattr__(var6, var5);
      var1.setline(2064);
      var1.getlocal(0).__getattr__("_wbuf").__getattr__("extend").__call__(var2, var1.getlocal(2));
      var1.setline(2065);
      var3 = var1.getlocal(0).__getattr__("_wbufsize");
      var10000 = var3._le(Py.newInteger(1));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("_wbuf_len");
         var10000 = var3._ge(var1.getlocal(0).__getattr__("_wbufsize"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(2067);
         var1.getlocal(0).__getattr__("flush").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read$185(PyFrame var1, ThreadState var2) {
      var1.setline(2073);
      PyObject var3 = var1.getglobal("max").__call__(var2, var1.getlocal(0).__getattr__("_rbufsize"), var1.getlocal(0).__getattr__("default_bufsize"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2077);
      var3 = var1.getlocal(0).__getattr__("_rbuf");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2078);
      var1.getlocal(3).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(2));
      var1.setline(2079);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(2081);
         var3 = var1.getglobal("StringIO").__call__(var2);
         var1.getlocal(0).__setattr__("_rbuf", var3);
         var3 = null;

         while(true) {
            var1.setline(2082);
            if (!var1.getglobal("True").__nonzero__()) {
               break;
            }

            try {
               var1.setline(2084);
               var3 = var1.getlocal(0).__getattr__("_sock").__getattr__("recv").__call__(var2, var1.getlocal(2));
               var1.setlocal(4, var3);
               var3 = null;
            } catch (Throwable var6) {
               PyException var8 = Py.setException(var6, var1);
               if (var8.match(var1.getglobal("error"))) {
                  var4 = var8.value;
                  var1.setlocal(5, var4);
                  var4 = null;
                  var1.setline(2086);
                  var4 = var1.getlocal(5).__getattr__("args").__getitem__(Py.newInteger(0));
                  var10000 = var4._eq(var1.getglobal("errno").__getattr__("EINTR"));
                  var4 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(2088);
                     throw Py.makeException();
                  }
                  continue;
               }

               throw var8;
            }

            var1.setline(2089);
            if (var1.getlocal(4).__not__().__nonzero__()) {
               break;
            }

            var1.setline(2091);
            var1.getlocal(3).__getattr__("write").__call__(var2, var1.getlocal(4));
         }

         var1.setline(2092);
         var3 = var1.getlocal(3).__getattr__("getvalue").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(2095);
         var4 = var1.getlocal(3).__getattr__("tell").__call__(var2);
         var1.setlocal(6, var4);
         var4 = null;
         var1.setline(2096);
         var4 = var1.getlocal(6);
         var10000 = var4._ge(var1.getlocal(1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2098);
            var1.getlocal(3).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            var1.setline(2099);
            var4 = var1.getlocal(3).__getattr__("read").__call__(var2, var1.getlocal(1));
            var1.setlocal(7, var4);
            var4 = null;
            var1.setline(2100);
            var4 = var1.getglobal("StringIO").__call__(var2);
            var1.getlocal(0).__setattr__("_rbuf", var4);
            var4 = null;
            var1.setline(2101);
            var1.getlocal(0).__getattr__("_rbuf").__getattr__("write").__call__(var2, var1.getlocal(3).__getattr__("read").__call__(var2));
            var1.setline(2102);
            var3 = var1.getlocal(7);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(2104);
            var4 = var1.getglobal("StringIO").__call__(var2);
            var1.getlocal(0).__setattr__("_rbuf", var4);
            var4 = null;

            while(true) {
               var1.setline(2105);
               if (!var1.getglobal("True").__nonzero__()) {
                  break;
               }

               var1.setline(2106);
               var4 = var1.getlocal(1)._sub(var1.getlocal(6));
               var1.setlocal(8, var4);
               var4 = null;

               try {
                  var1.setline(2113);
                  var4 = var1.getlocal(0).__getattr__("_sock").__getattr__("recv").__call__(var2, var1.getlocal(8));
                  var1.setlocal(4, var4);
                  var4 = null;
               } catch (Throwable var7) {
                  PyException var9 = Py.setException(var7, var1);
                  if (var9.match(var1.getglobal("error"))) {
                     PyObject var5 = var9.value;
                     var1.setlocal(5, var5);
                     var5 = null;
                     var1.setline(2115);
                     var5 = var1.getlocal(5).__getattr__("args").__getitem__(Py.newInteger(0));
                     var10000 = var5._eq(var1.getglobal("errno").__getattr__("EINTR"));
                     var5 = null;
                     if (!var10000.__nonzero__()) {
                        var1.setline(2117);
                        throw Py.makeException();
                     }
                     continue;
                  }

                  throw var9;
               }

               var1.setline(2118);
               if (var1.getlocal(4).__not__().__nonzero__()) {
                  break;
               }

               var1.setline(2120);
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
               var1.setlocal(9, var4);
               var4 = null;
               var1.setline(2121);
               var4 = var1.getlocal(9);
               var10000 = var4._eq(var1.getlocal(1));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(6).__not__();
               }

               if (var10000.__nonzero__()) {
                  var1.setline(2127);
                  var3 = var1.getlocal(4);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(2128);
               var4 = var1.getlocal(9);
               var10000 = var4._eq(var1.getlocal(8));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(2129);
                  var1.getlocal(3).__getattr__("write").__call__(var2, var1.getlocal(4));
                  var1.setline(2130);
                  var1.dellocal(4);
                  break;
               }

               var1.setline(2132);
               if (var1.getglobal("__debug__").__nonzero__()) {
                  var4 = var1.getlocal(9);
                  var10000 = var4._le(var1.getlocal(8));
                  var4 = null;
                  if (!var10000.__nonzero__()) {
                     throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("recv(%d) returned %d bytes")._mod(new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(9)})));
                  }
               }

               var1.setline(2133);
               var1.getlocal(3).__getattr__("write").__call__(var2, var1.getlocal(4));
               var1.setline(2134);
               var4 = var1.getlocal(6);
               var4 = var4._iadd(var1.getlocal(9));
               var1.setlocal(6, var4);
               var1.setline(2135);
               var1.dellocal(4);
            }

            var1.setline(2137);
            var3 = var1.getlocal(3).__getattr__("getvalue").__call__(var2);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject readline$186(PyFrame var1, ThreadState var2) {
      var1.setline(2140);
      PyObject var3 = var1.getlocal(0).__getattr__("_rbuf");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2141);
      var1.getlocal(2).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(2));
      var1.setline(2142);
      var3 = var1.getlocal(2).__getattr__("tell").__call__(var2);
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2144);
         var1.getlocal(2).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setline(2145);
         var3 = var1.getlocal(2).__getattr__("readline").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(2146);
         var10000 = var1.getlocal(3).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
         if (!var10000.__nonzero__()) {
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
            var10000 = var3._eq(var1.getlocal(1));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(2147);
            var3 = var1.getglobal("StringIO").__call__(var2);
            var1.getlocal(0).__setattr__("_rbuf", var3);
            var3 = null;
            var1.setline(2148);
            var1.getlocal(0).__getattr__("_rbuf").__getattr__("write").__call__(var2, var1.getlocal(2).__getattr__("read").__call__(var2));
            var1.setline(2149);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(2150);
         var1.dellocal(3);
      }

      var1.setline(2151);
      PyObject var4 = var1.getlocal(1);
      var10000 = var4._lt(Py.newInteger(0));
      var4 = null;
      PyObject var5;
      PyException var9;
      if (var10000.__nonzero__()) {
         var1.setline(2153);
         var4 = var1.getlocal(0).__getattr__("_rbufsize");
         var10000 = var4._le(Py.newInteger(1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2155);
            var1.getlocal(2).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            var1.setline(2156);
            PyList var10 = new PyList(new PyObject[]{var1.getlocal(2).__getattr__("read").__call__(var2)});
            var1.setlocal(4, var10);
            var4 = null;
            var1.setline(2157);
            var4 = var1.getglobal("StringIO").__call__(var2);
            var1.getlocal(0).__setattr__("_rbuf", var4);
            var4 = null;
            var1.setline(2158);
            var4 = var1.getglobal("None");
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(2159);
            var4 = var1.getlocal(0).__getattr__("_sock").__getattr__("recv");
            var1.setlocal(6, var4);
            var4 = null;

            label107:
            while(true) {
               var1.setline(2160);
               if (!var1.getglobal("True").__nonzero__()) {
                  break;
               }

               try {
                  while(true) {
                     var1.setline(2162);
                     var4 = var1.getlocal(5);
                     var10000 = var4._ne(PyString.fromInterned("\n"));
                     var4 = null;
                     if (!var10000.__nonzero__()) {
                        break label107;
                     }

                     var1.setline(2163);
                     var4 = var1.getlocal(6).__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
                     var1.setlocal(5, var4);
                     var4 = null;
                     var1.setline(2164);
                     if (var1.getlocal(5).__not__().__nonzero__()) {
                        break label107;
                     }

                     var1.setline(2166);
                     var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(5));
                  }
               } catch (Throwable var6) {
                  var9 = Py.setException(var6, var1);
                  if (!var9.match(var1.getglobal("error"))) {
                     throw var9;
                  }

                  var5 = var9.value;
                  var1.setlocal(7, var5);
                  var5 = null;
                  var1.setline(2170);
                  var5 = var1.getlocal(7).__getattr__("args").__getitem__(Py.newInteger(0));
                  var10000 = var5._eq(var1.getglobal("errno").__getattr__("EINTR"));
                  var5 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(2172);
                     throw Py.makeException();
                  }
               }
            }

            var1.setline(2174);
            var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(4));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(2176);
            var1.getlocal(2).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(2));
            var1.setline(2177);
            var4 = var1.getglobal("StringIO").__call__(var2);
            var1.getlocal(0).__setattr__("_rbuf", var4);
            var4 = null;

            while(true) {
               var1.setline(2178);
               if (!var1.getglobal("True").__nonzero__()) {
                  break;
               }

               try {
                  var1.setline(2180);
                  var4 = var1.getlocal(0).__getattr__("_sock").__getattr__("recv").__call__(var2, var1.getlocal(0).__getattr__("_rbufsize"));
                  var1.setlocal(5, var4);
                  var4 = null;
               } catch (Throwable var7) {
                  var9 = Py.setException(var7, var1);
                  if (var9.match(var1.getglobal("error"))) {
                     var5 = var9.value;
                     var1.setlocal(7, var5);
                     var5 = null;
                     var1.setline(2182);
                     var5 = var1.getlocal(7).__getattr__("args").__getitem__(Py.newInteger(0));
                     var10000 = var5._eq(var1.getglobal("errno").__getattr__("EINTR"));
                     var5 = null;
                     if (!var10000.__nonzero__()) {
                        var1.setline(2184);
                        throw Py.makeException();
                     }
                     continue;
                  }

                  throw var9;
               }

               var1.setline(2185);
               if (var1.getlocal(5).__not__().__nonzero__()) {
                  break;
               }

               var1.setline(2187);
               var4 = var1.getlocal(5).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
               var1.setlocal(8, var4);
               var4 = null;
               var1.setline(2188);
               var4 = var1.getlocal(8);
               var10000 = var4._ge(Py.newInteger(0));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(2189);
                  var4 = var1.getlocal(8);
                  var4 = var4._iadd(Py.newInteger(1));
                  var1.setlocal(8, var4);
                  var1.setline(2190);
                  var1.getlocal(2).__getattr__("write").__call__(var2, var1.getlocal(5).__getslice__((PyObject)null, var1.getlocal(8), (PyObject)null));
                  var1.setline(2191);
                  var1.getlocal(0).__getattr__("_rbuf").__getattr__("write").__call__(var2, var1.getlocal(5).__getslice__(var1.getlocal(8), (PyObject)null, (PyObject)null));
                  var1.setline(2192);
                  var1.dellocal(5);
                  break;
               }

               var1.setline(2194);
               var1.getlocal(2).__getattr__("write").__call__(var2, var1.getlocal(5));
            }

            var1.setline(2195);
            var3 = var1.getlocal(2).__getattr__("getvalue").__call__(var2);
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(2198);
         var1.getlocal(2).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(2));
         var1.setline(2199);
         var4 = var1.getlocal(2).__getattr__("tell").__call__(var2);
         var1.setlocal(9, var4);
         var4 = null;
         var1.setline(2200);
         var4 = var1.getlocal(9);
         var10000 = var4._ge(var1.getlocal(1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2201);
            var1.getlocal(2).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            var1.setline(2202);
            var4 = var1.getlocal(2).__getattr__("read").__call__(var2, var1.getlocal(1));
            var1.setlocal(10, var4);
            var4 = null;
            var1.setline(2203);
            var4 = var1.getglobal("StringIO").__call__(var2);
            var1.getlocal(0).__setattr__("_rbuf", var4);
            var4 = null;
            var1.setline(2204);
            var1.getlocal(0).__getattr__("_rbuf").__getattr__("write").__call__(var2, var1.getlocal(2).__getattr__("read").__call__(var2));
            var1.setline(2205);
            var3 = var1.getlocal(10);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(2206);
            var4 = var1.getglobal("StringIO").__call__(var2);
            var1.getlocal(0).__setattr__("_rbuf", var4);
            var4 = null;

            while(true) {
               var1.setline(2207);
               if (!var1.getglobal("True").__nonzero__()) {
                  break;
               }

               try {
                  var1.setline(2209);
                  var4 = var1.getlocal(0).__getattr__("_sock").__getattr__("recv").__call__(var2, var1.getlocal(0).__getattr__("_rbufsize"));
                  var1.setlocal(5, var4);
                  var4 = null;
               } catch (Throwable var8) {
                  var9 = Py.setException(var8, var1);
                  if (var9.match(var1.getglobal("error"))) {
                     var5 = var9.value;
                     var1.setlocal(7, var5);
                     var5 = null;
                     var1.setline(2211);
                     var5 = var1.getlocal(7).__getattr__("args").__getitem__(Py.newInteger(0));
                     var10000 = var5._eq(var1.getglobal("errno").__getattr__("EINTR"));
                     var5 = null;
                     if (!var10000.__nonzero__()) {
                        var1.setline(2213);
                        throw Py.makeException();
                     }
                     continue;
                  }

                  throw var9;
               }

               var1.setline(2214);
               if (var1.getlocal(5).__not__().__nonzero__()) {
                  break;
               }

               var1.setline(2216);
               var4 = var1.getlocal(1)._sub(var1.getlocal(9));
               var1.setlocal(11, var4);
               var4 = null;
               var1.setline(2218);
               var4 = var1.getlocal(5).__getattr__("find").__call__((ThreadState)var2, PyString.fromInterned("\n"), (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(11));
               var1.setlocal(8, var4);
               var4 = null;
               var1.setline(2219);
               var4 = var1.getlocal(8);
               var10000 = var4._ge(Py.newInteger(0));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(2220);
                  var4 = var1.getlocal(8);
                  var4 = var4._iadd(Py.newInteger(1));
                  var1.setlocal(8, var4);
                  var1.setline(2222);
                  var1.getlocal(0).__getattr__("_rbuf").__getattr__("write").__call__(var2, var1.getlocal(5).__getslice__(var1.getlocal(8), (PyObject)null, (PyObject)null));
                  var1.setline(2223);
                  if (!var1.getlocal(9).__nonzero__()) {
                     var1.setline(2229);
                     var3 = var1.getlocal(5).__getslice__((PyObject)null, var1.getlocal(8), (PyObject)null);
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var1.setline(2224);
                  var1.getlocal(2).__getattr__("write").__call__(var2, var1.getlocal(5).__getslice__((PyObject)null, var1.getlocal(8), (PyObject)null));
                  break;
               }

               var1.setline(2230);
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(5));
               var1.setlocal(12, var4);
               var4 = null;
               var1.setline(2231);
               var4 = var1.getlocal(12);
               var10000 = var4._eq(var1.getlocal(1));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(9).__not__();
               }

               if (var10000.__nonzero__()) {
                  var1.setline(2234);
                  var3 = var1.getlocal(5);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(2235);
               var4 = var1.getlocal(12);
               var10000 = var4._ge(var1.getlocal(11));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(2236);
                  var1.getlocal(2).__getattr__("write").__call__(var2, var1.getlocal(5).__getslice__((PyObject)null, var1.getlocal(11), (PyObject)null));
                  var1.setline(2237);
                  var1.getlocal(0).__getattr__("_rbuf").__getattr__("write").__call__(var2, var1.getlocal(5).__getslice__(var1.getlocal(11), (PyObject)null, (PyObject)null));
                  break;
               }

               var1.setline(2239);
               var1.getlocal(2).__getattr__("write").__call__(var2, var1.getlocal(5));
               var1.setline(2240);
               var4 = var1.getlocal(9);
               var4 = var4._iadd(var1.getlocal(12));
               var1.setlocal(9, var4);
            }

            var1.setline(2242);
            var3 = var1.getlocal(2).__getattr__("getvalue").__call__(var2);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject readlines$187(PyFrame var1, ThreadState var2) {
      var1.setline(2245);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2246);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var4);
      var3 = null;

      PyObject var10000;
      PyObject var5;
      do {
         var1.setline(2247);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(2248);
         var5 = var1.getlocal(0).__getattr__("readline").__call__(var2);
         var1.setlocal(4, var5);
         var3 = null;
         var1.setline(2249);
         if (var1.getlocal(4).__not__().__nonzero__()) {
            break;
         }

         var1.setline(2251);
         var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(4));
         var1.setline(2252);
         var5 = var1.getlocal(2);
         var5 = var5._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(4)));
         var1.setlocal(2, var5);
         var1.setline(2253);
         var10000 = var1.getlocal(1);
         if (var10000.__nonzero__()) {
            var5 = var1.getlocal(2);
            var10000 = var5._ge(var1.getlocal(1));
            var3 = null;
         }
      } while(!var10000.__nonzero__());

      var1.setline(2255);
      var5 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject __iter__$188(PyFrame var1, ThreadState var2) {
      var1.setline(2260);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject next$189(PyFrame var1, ThreadState var2) {
      var1.setline(2263);
      PyObject var3 = var1.getlocal(0).__getattr__("readline").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2264);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(2265);
         throw Py.makeException(var1.getglobal("StopIteration"));
      } else {
         var1.setline(2266);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public _socket$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"FORMAT", "debug_sh"};
      _debug$1 = Py.newCode(0, var2, var1, "_debug", 60, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DaemonThreadFactory$2 = Py.newCode(0, var2, var1, "DaemonThreadFactory", 203, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "label"};
      __init__$3 = Py.newCode(2, var2, var1, "__init__", 207, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "runnable", "t"};
      newThread$4 = Py.newCode(2, var2, var1, "newThread", 210, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"group", "pending_threads", "t", "pending_count"};
      _check_threadpool_for_pending_threads$5 = Py.newCode(1, var2, var1, "_check_threadpool_for_pending_threads", 220, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _shutdown_threadpool$6 = Py.newCode(0, var2, var1, "_shutdown_threadpool", 230, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      error$7 = Py.newCode(0, var2, var1, "error", 249, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      herror$8 = Py.newCode(0, var2, var1, "herror", 250, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      gaierror$9 = Py.newCode(0, var2, var1, "gaierror", 251, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      timeout$10 = Py.newCode(0, var2, var1, "timeout", 252, false, false, self, 10, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      SSLError$11 = Py.newCode(0, var2, var1, "SSLError", 253, false, false, self, 11, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"exc"};
      _add_exception_attrs$12 = Py.newCode(1, var2, var1, "_add_exception_attrs", 267, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"exc"};
      _unmapped_exception$13 = Py.newCode(1, var2, var1, "_unmapped_exception", 273, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"exc"};
      java_net_socketexception_handler$14 = Py.newCode(1, var2, var1, "java_net_socketexception_handler", 277, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"exc"};
      would_block_error$15 = Py.newCode(1, var2, var1, "would_block_error", 287, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$16 = Py.newCode(1, var2, var1, "<lambda>", 296, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$17 = Py.newCode(1, var2, var1, "<lambda>", 297, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$18 = Py.newCode(1, var2, var1, "<lambda>", 298, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$19 = Py.newCode(1, var2, var1, "<lambda>", 300, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$20 = Py.newCode(1, var2, var1, "<lambda>", 301, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$21 = Py.newCode(1, var2, var1, "<lambda>", 302, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$22 = Py.newCode(1, var2, var1, "<lambda>", 306, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$23 = Py.newCode(1, var2, var1, "<lambda>", 307, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$24 = Py.newCode(1, var2, var1, "<lambda>", 309, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$25 = Py.newCode(1, var2, var1, "<lambda>", 313, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$26 = Py.newCode(1, var2, var1, "<lambda>", 323, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$27 = Py.newCode(1, var2, var1, "<lambda>", 326, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$28 = Py.newCode(1, var2, var1, "<lambda>", 332, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"java_exception", "cause", "msg", "py_exception", "mapped_exception"};
      _map_exception$29 = Py.newCode(1, var2, var1, "_map_exception", 336, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"method_or_function", "handle_exception"};
      String[] var10001 = var2;
      _socket$py var10007 = self;
      var2 = new String[]{"method_or_function"};
      raises_java_exception$30 = Py.newCode(1, var10001, var1, "raises_java_exception", 372, false, false, var10007, 30, var2, (String[])null, 0, 4097);
      var2 = new String[]{"args", "kwargs", "is_socket", "jlx", "e"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"method_or_function"};
      handle_exception$31 = Py.newCode(2, var10001, var1, "handle_exception", 377, true, true, var10007, 31, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      _Select$32 = Py.newCode(0, var2, var1, "_Select", 398, false, false, self, 32, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "rlist", "wlist", "xlist"};
      __init__$33 = Py.newCode(4, var2, var1, "__init__", 400, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "socks", "_socks", "sock", "_sock"};
      _normalize_sockets$34 = Py.newCode(2, var2, var1, "_normalize_sockets", 406, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sock", "_"};
      notify$35 = Py.newCode(3, var2, var1, "notify", 418, false, true, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$36 = Py.newCode(1, var2, var1, "__str__", 422, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "socks", "sock"};
      _register_sockets$37 = Py.newCode(2, var2, var1, "_register_sockets", 425, false, false, self, 37, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "timeout", "started", "selected_rlist", "_(440_37)", "selected_wlist", "_(441_37)", "selected_xlist", "completed"};
      __call__$38 = Py.newCode(2, var2, var1, "__call__", 434, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "sock"};
      f$39 = Py.newCode(1, var2, var1, "<genexpr>", 440, false, false, self, 39, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"_(x)", "sock"};
      f$40 = Py.newCode(1, var2, var1, "<genexpr>", 441, false, false, self, 40, (String[])null, (String[])null, 0, 4129);
      var2 = new String[0];
      poll$41 = Py.newCode(0, var2, var1, "poll", 469, false, false, self, 41, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$42 = Py.newCode(1, var2, var1, "__init__", 471, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sock", "exception", "hangup", "notification"};
      notify$43 = Py.newCode(4, var2, var1, "notify", 476, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fd", "eventmask", "sock"};
      register$44 = Py.newCode(3, var2, var1, "register", 486, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fd", "eventmask"};
      modify$45 = Py.newCode(3, var2, var1, "modify", 496, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fd", "sock"};
      unregister$46 = Py.newCode(2, var2, var1, "unregister", 503, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "notification", "mask", "event"};
      _event_test$47 = Py.newCode(2, var2, var1, "_event_test", 511, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "poller", "notification", "notifications", "result", "socks", "fd", "event", "sock"};
      _handle_poll$48 = Py.newCode(2, var2, var1, "_handle_poll", 532, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "timeout", "started", "timeout_in_ns", "result"};
      poll$49 = Py.newCode(2, var2, var1, "poll", 561, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      PythonInboundHandler$50 = Py.newCode(0, var2, var1, "PythonInboundHandler", 587, false, false, self, 50, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "sock"};
      __init__$51 = Py.newCode(2, var2, var1, "__init__", 589, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ctx"};
      channelActive$52 = Py.newCode(2, var2, var1, "channelActive", 593, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ctx", "msg"};
      channelRead$53 = Py.newCode(3, var2, var1, "channelRead", 598, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ctx"};
      channelWritabilityChanged$54 = Py.newCode(2, var2, var1, "channelWritabilityChanged", 605, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ctx", "cause"};
      exceptionCaught$55 = Py.newCode(3, var2, var1, "exceptionCaught", 610, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ChildSocketHandler$56 = Py.newCode(0, var2, var1, "ChildSocketHandler", 615, false, false, self, 56, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "parent_socket"};
      __init__$57 = Py.newCode(2, var2, var1, "__init__", 617, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "child_channel", "_(632_30)", "config", "option", "value", "wait_for_barrier", "child"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self", "child"};
      initChannel$58 = Py.newCode(2, var10001, var1, "initChannel", 620, false, false, var10007, 58, var2, (String[])null, 1, 4097);
      var2 = new String[]{"_(x)", "option", "value"};
      f$59 = Py.newCode(1, var2, var1, "<genexpr>", 632, false, false, self, 59, (String[])null, (String[])null, 0, 4129);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"child", "self"};
      wait_for_barrier$60 = Py.newCode(0, var10001, var1, "wait_for_barrier", 665, false, false, var10007, 60, (String[])null, var2, 0, 4097);
      var2 = new String[]{"value"};
      _identity$61 = Py.newCode(1, var2, var1, "_identity", 696, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"setter", "option", "value"};
      _set_option$62 = Py.newCode(3, var2, var1, "_set_option", 700, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"addr", "port", "inet_addr"};
      _socktuple$63 = Py.newCode(1, var2, var1, "_socktuple", 738, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _realsocket$64 = Py.newCode(0, var2, var1, "_realsocket", 749, false, false, self, 64, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "family", "type", "proto"};
      __init__$65 = Py.newCode(4, var2, var1, "__init__", 751, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$66 = Py.newCode(1, var2, var1, "__repr__", 786, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _make_active$67 = Py.newCode(1, var2, var1, "_make_active", 790, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "selector"};
      _register_selector$68 = Py.newCode(2, var2, var1, "_register_selector", 793, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "selector"};
      _unregister_selector$69 = Py.newCode(2, var2, var1, "_unregister_selector", 797, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exception", "hangup", "selector"};
      _notify_selectors$70 = Py.newCode(3, var2, var1, "_notify_selectors", 805, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "future", "reason"};
      _handle_channel_future$71 = Py.newCode(3, var2, var1, "_handle_channel_future", 809, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "flag"};
      setblocking$72 = Py.newCode(2, var2, var1, "setblocking", 828, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "timeout"};
      settimeout$73 = Py.newCode(2, var2, var1, "settimeout", 834, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      gettimeout$74 = Py.newCode(1, var2, var1, "gettimeout", 837, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "waiter", "reason", "timeout_in_ns", "started", "result"};
      _handle_timeout$75 = Py.newCode(3, var2, var1, "_handle_timeout", 840, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "address"};
      bind$76 = Py.newCode(2, var2, var1, "bind", 857, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "channel"};
      _init_client_mode$77 = Py.newCode(2, var2, var1, "_init_client_mode", 867, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "addr", "bootstrap", "option", "value", "handler", "bind_future"};
      _connect$78 = Py.newCode(2, var2, var1, "_connect", 883, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_peer_closed"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      _post_connect$79 = Py.newCode(1, var10001, var1, "_post_connect", 913, false, false, var10007, 79, var2, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      _peer_closed$80 = Py.newCode(1, var10001, var1, "_peer_closed", 920, false, false, var10007, 80, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "addr"};
      connect$81 = Py.newCode(2, var2, var1, "connect", 929, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "addr", "was_connecting", "e"};
      connect_ex$82 = Py.newCode(2, var2, var1, "connect_ex", 939, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "backlog", "b", "option", "value"};
      listen$83 = Py.newCode(2, var2, var1, "listen", 980, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "child", "peername"};
      accept$84 = Py.newCode(1, var2, var1, "accept", 1009, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "addr", "bootstrap", "option", "value", "future"};
      _datagram_connect$85 = Py.newCode(2, var2, var1, "_datagram_connect", 1030, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "string", "arg1", "arg2", "flags", "address", "packet", "future"};
      sendto$86 = Py.newCode(4, var2, var1, "sendto", 1057, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "buffer", "nbytes", "flags", "data", "remote_addr"};
      recvfrom_into$87 = Py.newCode(4, var2, var1, "recvfrom_into", 1075, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "buffer", "nbytes", "flags", "data"};
      recv_into$88 = Py.newCode(4, var2, var1, "recv_into", 1082, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "close_future"};
      close$89 = Py.newCode(1, var2, var1, "close", 1091, false, false, self, 89, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_", "child", "msgs", "msg"};
      _finish_closing$90 = Py.newCode(2, var2, var1, "_finish_closing", 1104, false, false, self, 90, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "how"};
      shutdown$91 = Py.newCode(2, var2, var1, "shutdown", 1124, false, false, self, 91, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _readable$92 = Py.newCode(1, var2, var1, "_readable", 1137, false, false, self, 92, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pending", "msg"};
      _pending$93 = Py.newCode(1, var2, var1, "_pending", 1148, false, false, self, 93, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _writable$94 = Py.newCode(1, var2, var1, "_writable", 1165, false, false, self, 94, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _verify_channel$95 = Py.newCode(1, var2, var1, "_verify_channel", 1170, false, false, self, 95, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "flags", "packet", "future", "bytes_writable", "sent_data"};
      send$96 = Py.newCode(3, var2, var1, "send", 1175, false, false, self, 96, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "reason", "msg"};
      _get_incoming_msg$97 = Py.newCode(2, var2, var1, "_get_incoming_msg", 1207, false, false, self, 97, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "bufsize", "reason", "msg", "content", "sender", "msg_length", "buf"};
      _get_message$98 = Py.newCode(3, var2, var1, "_get_message", 1232, false, false, self, 98, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "bufsize", "flags", "data", "_"};
      recv$99 = Py.newCode(3, var2, var1, "recv", 1263, false, false, self, 99, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "bufsize", "flags", "data", "sender", "remote_addr"};
      recvfrom$100 = Py.newCode(3, var2, var1, "recvfrom", 1274, false, false, self, 100, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      fileno$101 = Py.newCode(1, var2, var1, "fileno", 1281, false, false, self, 101, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "level", "optname", "value", "option", "cast", "cast_value"};
      setsockopt$102 = Py.newCode(4, var2, var1, "setsockopt", 1284, false, false, self, 102, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "level", "optname", "buflen", "last_error", "option", "_"};
      getsockopt$103 = Py.newCode(4, var2, var1, "getsockopt", 1297, false, false, self, 103, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "local_addr"};
      getsockname$104 = Py.newCode(1, var2, var1, "getsockname", 1323, false, false, self, 104, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "remote_addr"};
      getpeername$105 = Py.newCode(1, var2, var1, "getpeername", 1342, false, false, self, 105, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _closedsocket$106 = Py.newCode(0, var2, var1, "_closedsocket", 1365, false, false, self, 106, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      close$107 = Py.newCode(1, var2, var1, "close", 1367, false, false, self, 107, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args"};
      _dummy$108 = Py.newCode(1, var2, var1, "_dummy", 1370, true, false, self, 108, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _socketobject$109 = Py.newCode(0, var2, var1, "_socketobject", 1383, false, false, self, 109, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "family", "type", "proto", "_sock", "method"};
      __init__$110 = Py.newCode(5, var2, var1, "__init__", 1387, false, false, self, 110, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_closedsocket", "_delegate_methods", "setattr", "dummy", "method"};
      close$111 = Py.newCode(4, var2, var1, "close", 1394, false, false, self, 111, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      fileno$112 = Py.newCode(1, var2, var1, "fileno", 1404, false, false, self, 112, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sock", "addr"};
      accept$113 = Py.newCode(1, var2, var1, "accept", 1407, false, false, self, 113, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      dup$114 = Py.newCode(1, var2, var1, "dup", 1412, false, false, self, 114, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "mode", "bufsize"};
      makefile$115 = Py.newCode(3, var2, var1, "makefile", 1423, false, false, self, 115, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      f$116 = Py.newCode(1, var2, var1, "<lambda>", 1435, false, false, self, 116, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      f$117 = Py.newCode(1, var2, var1, "<lambda>", 1436, false, false, self, 117, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      f$118 = Py.newCode(1, var2, var1, "<lambda>", 1437, false, false, self, 118, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "self", "args"};
      meth$119 = Py.newCode(3, var2, var1, "meth", 1440, true, false, self, 119, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ChildSocket$120 = Py.newCode(0, var2, var1, "ChildSocket", 1457, false, false, self, 120, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "parent_socket"};
      __init__$121 = Py.newCode(2, var2, var1, "__init__", 1459, false, false, self, 121, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ignore"};
      _make_active$122 = Py.newCode(2, var2, var1, "_make_active", 1467, true, false, self, 122, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      send$123 = Py.newCode(2, var2, var1, "send", 1482, false, false, self, 123, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "bufsize", "flags"};
      recv$124 = Py.newCode(3, var2, var1, "recv", 1488, false, false, self, 124, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "bufsize", "flags"};
      recvfrom$125 = Py.newCode(3, var2, var1, "recvfrom", 1492, false, false, self, 125, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "mode"};
      setblocking$126 = Py.newCode(2, var2, var1, "setblocking", 1496, false, false, self, 126, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$127 = Py.newCode(1, var2, var1, "close", 1500, false, false, self, 127, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "how"};
      shutdown$128 = Py.newCode(2, var2, var1, "shutdown", 1513, false, false, self, 128, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __del__$129 = Py.newCode(1, var2, var1, "__del__", 1517, false, false, self, 129, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"rlist", "wlist", "xlist", "timeout", "lst"};
      select$130 = Py.newCode(4, var2, var1, "select", 1529, false, false, self, 130, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"address", "timeout", "source_address", "host", "port", "err", "res", "af", "socktype", "proto", "canonname", "sa", "sock", "_"};
      create_connection$131 = Py.newCode(3, var2, var1, "create_connection", 1540, false, false, self, 131, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"value", "floatvalue"};
      _calctimeoutvalue$132 = Py.newCode(1, var2, var1, "_calctimeoutvalue", 1583, false, false, self, 132, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      getdefaulttimeout$133 = Py.newCode(0, var2, var1, "getdefaulttimeout", 1594, false, false, self, 133, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"timeout"};
      setdefaulttimeout$134 = Py.newCode(1, var2, var1, "setdefaulttimeout", 1597, false, false, self, 134, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _ip_address_t$135 = Py.newCode(0, var2, var1, "_ip_address_t", 1604, false, false, self, 135, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _ipv4_address_t$136 = Py.newCode(0, var2, var1, "_ipv4_address_t", 1608, false, false, self, 136, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls", "sockaddr", "port", "jaddress", "ntup"};
      __new__$137 = Py.newCode(4, var2, var1, "__new__", 1612, false, false, self, 137, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _ipv6_address_t$138 = Py.newCode(0, var2, var1, "_ipv6_address_t", 1617, false, false, self, 138, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls", "sockaddr", "port", "jaddress", "ntup"};
      __new__$139 = Py.newCode(4, var2, var1, "__new__", 1621, false, false, self, 139, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"address_object", "family", "sock_type", "proto", "flags", "addr"};
      _get_jsockaddr$140 = Py.newCode(5, var2, var1, "_get_jsockaddr", 1627, false, false, self, 140, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"address_object", "family", "sock_type", "proto", "flags", "error_message", "hostname", "port", "addresses"};
      _get_jsockaddr2$141 = Py.newCode(5, var2, var1, "_get_jsockaddr2", 1638, false, false, self, 141, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"addr", "version"};
      _is_ip_address$142 = Py.newCode(2, var2, var1, "_is_ip_address", 1674, false, false, self, 142, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"addr"};
      is_ipv4_address$143 = Py.newCode(1, var2, var1, "is_ipv4_address", 1682, false, false, self, 143, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"addr"};
      is_ipv6_address$144 = Py.newCode(1, var2, var1, "is_ipv6_address", 1686, false, false, self, 144, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"addr"};
      is_ip_address$145 = Py.newCode(1, var2, var1, "is_ip_address", 1690, false, false, self, 145, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"value"};
      _use_ipv4_addresses_only$146 = Py.newCode(1, var2, var1, "_use_ipv4_addresses_only", 1699, false, false, self, 146, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"host", "family", "flags"};
      _getaddrinfo_get_host$147 = Py.newCode(3, var2, var1, "_getaddrinfo_get_host", 1704, false, false, self, 147, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"port", "flags", "int_port"};
      _getaddrinfo_get_port$148 = Py.newCode(2, var2, var1, "_getaddrinfo_get_port", 1719, false, false, self, 148, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"host", "port", "family", "socktype", "proto", "flags", "filter_fns", "hosts", "results", "h", "all_by_name", "a", "_[1773_20]", "f", "canonname", "sockaddr", "sock_tuple", "socktypes", "result_socktype", "result_proto"};
      getaddrinfo$149 = Py.newCode(6, var2, var1, "getaddrinfo", 1740, false, false, self, 149, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$150 = Py.newCode(1, var2, var1, "<lambda>", 1754, false, false, self, 150, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$151 = Py.newCode(1, var2, var1, "<lambda>", 1755, false, false, self, 151, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$152 = Py.newCode(1, var2, var1, "<lambda>", 1756, false, false, self, 152, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      htons$153 = Py.newCode(1, var2, var1, "htons", 1796, false, false, self, 153, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      htonl$154 = Py.newCode(1, var2, var1, "htonl", 1797, false, false, self, 154, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      ntohs$155 = Py.newCode(1, var2, var1, "ntohs", 1798, false, false, self, 155, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      ntohl$156 = Py.newCode(1, var2, var1, "ntohl", 1799, false, false, self, 156, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"family", "ip_string", "ia", "bytes", "byte", "_[1818_20]"};
      inet_pton$157 = Py.newCode(2, var2, var1, "inet_pton", 1801, false, false, self, 157, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"family", "packed_ip", "jByteArray", "ia"};
      inet_ntop$158 = Py.newCode(2, var2, var1, "inet_ntop", 1820, false, false, self, 158, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"ip_string"};
      inet_aton$159 = Py.newCode(1, var2, var1, "inet_aton", 1834, false, false, self, 159, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"packed_ip"};
      inet_ntoa$160 = Py.newCode(1, var2, var1, "inet_ntoa", 1837, false, false, self, 160, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "addresses", "names", "addrs", "addr"};
      _gethostbyaddr$161 = Py.newCode(1, var2, var1, "_gethostbyaddr", 1845, false, false, self, 161, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "names", "addrs", "a"};
      getfqdn$162 = Py.newCode(1, var2, var1, "getfqdn", 1855, false, false, self, 162, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      gethostname$163 = Py.newCode(0, var2, var1, "gethostname", 1873, false, false, self, 163, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name"};
      gethostbyname$164 = Py.newCode(1, var2, var1, "gethostbyname", 1877, false, false, self, 164, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name"};
      gethostbyname_ex$165 = Py.newCode(1, var2, var1, "gethostbyname_ex", 1886, false, false, self, 165, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "names", "addrs"};
      gethostbyaddr$166 = Py.newCode(1, var2, var1, "gethostbyaddr", 1890, false, false, self, 166, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"service_name", "protocol_name", "service"};
      getservbyname$167 = Py.newCode(2, var2, var1, "getservbyname", 1899, false, false, self, 167, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"port", "protocol_name", "service"};
      getservbyport$168 = Py.newCode(2, var2, var1, "getservbyport", 1905, false, false, self, 168, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"protocol_name", "proto"};
      getprotobyname$169 = Py.newCode(1, var2, var1, "getprotobyname", 1911, false, false, self, 169, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"service_name", "protocol_name"};
      getservbyname$170 = Py.newCode(2, var2, var1, "getservbyname", 1918, false, false, self, 170, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"port", "protocol_name"};
      getservbyport$171 = Py.newCode(2, var2, var1, "getservbyport", 1921, false, false, self, 171, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"protocol_name"};
      getprotobyname$172 = Py.newCode(1, var2, var1, "getprotobyname", 1924, false, false, self, 172, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"address", "flags", "jia", "result"};
      _getnameinfo_get_host$173 = Py.newCode(2, var2, var1, "_getnameinfo_get_host", 1928, false, false, self, 173, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"port", "flags", "proto"};
      _getnameinfo_get_port$174 = Py.newCode(2, var2, var1, "_getnameinfo_get_port", 1945, false, false, self, 174, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"sock_addr", "flags", "host", "port"};
      getnameinfo$175 = Py.newCode(2, var2, var1, "getnameinfo", 1955, false, false, self, 175, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _fileobject$176 = Py.newCode(0, var2, var1, "_fileobject", 1965, false, false, self, 176, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "sock", "mode", "bufsize", "close"};
      __init__$177 = Py.newCode(5, var2, var1, "__init__", 1976, false, false, self, 177, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _getclosed$178 = Py.newCode(1, var2, var1, "_getclosed", 2002, false, false, self, 178, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$179 = Py.newCode(1, var2, var1, "close", 2006, false, false, self, 179, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __del__$180 = Py.newCode(1, var2, var1, "__del__", 2015, false, false, self, 180, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "buffer_size", "data_size", "write_offset", "view", "chunk", "remainder"};
      flush$181 = Py.newCode(1, var2, var1, "flush", 2022, false, false, self, 181, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      fileno$182 = Py.newCode(1, var2, var1, "fileno", 2045, false, false, self, 182, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      write$183 = Py.newCode(2, var2, var1, "write", 2048, false, false, self, 183, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "list", "lines"};
      writelines$184 = Py.newCode(2, var2, var1, "writelines", 2059, false, false, self, 184, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size", "rbufsize", "buf", "data", "e", "buf_len", "rv", "left", "n"};
      read$185 = Py.newCode(2, var2, var1, "read", 2069, false, false, self, 185, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size", "buf", "bline", "buffers", "data", "recv", "e", "nl", "buf_len", "rv", "left", "n"};
      readline$186 = Py.newCode(2, var2, var1, "readline", 2139, false, false, self, 186, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sizehint", "total", "list", "line"};
      readlines$187 = Py.newCode(2, var2, var1, "readlines", 2244, false, false, self, 187, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __iter__$188 = Py.newCode(1, var2, var1, "__iter__", 2259, false, false, self, 188, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      next$189 = Py.newCode(1, var2, var1, "next", 2262, false, false, self, 189, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new _socket$py("_socket$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(_socket$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._debug$1(var2, var3);
         case 2:
            return this.DaemonThreadFactory$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this.newThread$4(var2, var3);
         case 5:
            return this._check_threadpool_for_pending_threads$5(var2, var3);
         case 6:
            return this._shutdown_threadpool$6(var2, var3);
         case 7:
            return this.error$7(var2, var3);
         case 8:
            return this.herror$8(var2, var3);
         case 9:
            return this.gaierror$9(var2, var3);
         case 10:
            return this.timeout$10(var2, var3);
         case 11:
            return this.SSLError$11(var2, var3);
         case 12:
            return this._add_exception_attrs$12(var2, var3);
         case 13:
            return this._unmapped_exception$13(var2, var3);
         case 14:
            return this.java_net_socketexception_handler$14(var2, var3);
         case 15:
            return this.would_block_error$15(var2, var3);
         case 16:
            return this.f$16(var2, var3);
         case 17:
            return this.f$17(var2, var3);
         case 18:
            return this.f$18(var2, var3);
         case 19:
            return this.f$19(var2, var3);
         case 20:
            return this.f$20(var2, var3);
         case 21:
            return this.f$21(var2, var3);
         case 22:
            return this.f$22(var2, var3);
         case 23:
            return this.f$23(var2, var3);
         case 24:
            return this.f$24(var2, var3);
         case 25:
            return this.f$25(var2, var3);
         case 26:
            return this.f$26(var2, var3);
         case 27:
            return this.f$27(var2, var3);
         case 28:
            return this.f$28(var2, var3);
         case 29:
            return this._map_exception$29(var2, var3);
         case 30:
            return this.raises_java_exception$30(var2, var3);
         case 31:
            return this.handle_exception$31(var2, var3);
         case 32:
            return this._Select$32(var2, var3);
         case 33:
            return this.__init__$33(var2, var3);
         case 34:
            return this._normalize_sockets$34(var2, var3);
         case 35:
            return this.notify$35(var2, var3);
         case 36:
            return this.__str__$36(var2, var3);
         case 37:
            return this._register_sockets$37(var2, var3);
         case 38:
            return this.__call__$38(var2, var3);
         case 39:
            return this.f$39(var2, var3);
         case 40:
            return this.f$40(var2, var3);
         case 41:
            return this.poll$41(var2, var3);
         case 42:
            return this.__init__$42(var2, var3);
         case 43:
            return this.notify$43(var2, var3);
         case 44:
            return this.register$44(var2, var3);
         case 45:
            return this.modify$45(var2, var3);
         case 46:
            return this.unregister$46(var2, var3);
         case 47:
            return this._event_test$47(var2, var3);
         case 48:
            return this._handle_poll$48(var2, var3);
         case 49:
            return this.poll$49(var2, var3);
         case 50:
            return this.PythonInboundHandler$50(var2, var3);
         case 51:
            return this.__init__$51(var2, var3);
         case 52:
            return this.channelActive$52(var2, var3);
         case 53:
            return this.channelRead$53(var2, var3);
         case 54:
            return this.channelWritabilityChanged$54(var2, var3);
         case 55:
            return this.exceptionCaught$55(var2, var3);
         case 56:
            return this.ChildSocketHandler$56(var2, var3);
         case 57:
            return this.__init__$57(var2, var3);
         case 58:
            return this.initChannel$58(var2, var3);
         case 59:
            return this.f$59(var2, var3);
         case 60:
            return this.wait_for_barrier$60(var2, var3);
         case 61:
            return this._identity$61(var2, var3);
         case 62:
            return this._set_option$62(var2, var3);
         case 63:
            return this._socktuple$63(var2, var3);
         case 64:
            return this._realsocket$64(var2, var3);
         case 65:
            return this.__init__$65(var2, var3);
         case 66:
            return this.__repr__$66(var2, var3);
         case 67:
            return this._make_active$67(var2, var3);
         case 68:
            return this._register_selector$68(var2, var3);
         case 69:
            return this._unregister_selector$69(var2, var3);
         case 70:
            return this._notify_selectors$70(var2, var3);
         case 71:
            return this._handle_channel_future$71(var2, var3);
         case 72:
            return this.setblocking$72(var2, var3);
         case 73:
            return this.settimeout$73(var2, var3);
         case 74:
            return this.gettimeout$74(var2, var3);
         case 75:
            return this._handle_timeout$75(var2, var3);
         case 76:
            return this.bind$76(var2, var3);
         case 77:
            return this._init_client_mode$77(var2, var3);
         case 78:
            return this._connect$78(var2, var3);
         case 79:
            return this._post_connect$79(var2, var3);
         case 80:
            return this._peer_closed$80(var2, var3);
         case 81:
            return this.connect$81(var2, var3);
         case 82:
            return this.connect_ex$82(var2, var3);
         case 83:
            return this.listen$83(var2, var3);
         case 84:
            return this.accept$84(var2, var3);
         case 85:
            return this._datagram_connect$85(var2, var3);
         case 86:
            return this.sendto$86(var2, var3);
         case 87:
            return this.recvfrom_into$87(var2, var3);
         case 88:
            return this.recv_into$88(var2, var3);
         case 89:
            return this.close$89(var2, var3);
         case 90:
            return this._finish_closing$90(var2, var3);
         case 91:
            return this.shutdown$91(var2, var3);
         case 92:
            return this._readable$92(var2, var3);
         case 93:
            return this._pending$93(var2, var3);
         case 94:
            return this._writable$94(var2, var3);
         case 95:
            return this._verify_channel$95(var2, var3);
         case 96:
            return this.send$96(var2, var3);
         case 97:
            return this._get_incoming_msg$97(var2, var3);
         case 98:
            return this._get_message$98(var2, var3);
         case 99:
            return this.recv$99(var2, var3);
         case 100:
            return this.recvfrom$100(var2, var3);
         case 101:
            return this.fileno$101(var2, var3);
         case 102:
            return this.setsockopt$102(var2, var3);
         case 103:
            return this.getsockopt$103(var2, var3);
         case 104:
            return this.getsockname$104(var2, var3);
         case 105:
            return this.getpeername$105(var2, var3);
         case 106:
            return this._closedsocket$106(var2, var3);
         case 107:
            return this.close$107(var2, var3);
         case 108:
            return this._dummy$108(var2, var3);
         case 109:
            return this._socketobject$109(var2, var3);
         case 110:
            return this.__init__$110(var2, var3);
         case 111:
            return this.close$111(var2, var3);
         case 112:
            return this.fileno$112(var2, var3);
         case 113:
            return this.accept$113(var2, var3);
         case 114:
            return this.dup$114(var2, var3);
         case 115:
            return this.makefile$115(var2, var3);
         case 116:
            return this.f$116(var2, var3);
         case 117:
            return this.f$117(var2, var3);
         case 118:
            return this.f$118(var2, var3);
         case 119:
            return this.meth$119(var2, var3);
         case 120:
            return this.ChildSocket$120(var2, var3);
         case 121:
            return this.__init__$121(var2, var3);
         case 122:
            return this._make_active$122(var2, var3);
         case 123:
            return this.send$123(var2, var3);
         case 124:
            return this.recv$124(var2, var3);
         case 125:
            return this.recvfrom$125(var2, var3);
         case 126:
            return this.setblocking$126(var2, var3);
         case 127:
            return this.close$127(var2, var3);
         case 128:
            return this.shutdown$128(var2, var3);
         case 129:
            return this.__del__$129(var2, var3);
         case 130:
            return this.select$130(var2, var3);
         case 131:
            return this.create_connection$131(var2, var3);
         case 132:
            return this._calctimeoutvalue$132(var2, var3);
         case 133:
            return this.getdefaulttimeout$133(var2, var3);
         case 134:
            return this.setdefaulttimeout$134(var2, var3);
         case 135:
            return this._ip_address_t$135(var2, var3);
         case 136:
            return this._ipv4_address_t$136(var2, var3);
         case 137:
            return this.__new__$137(var2, var3);
         case 138:
            return this._ipv6_address_t$138(var2, var3);
         case 139:
            return this.__new__$139(var2, var3);
         case 140:
            return this._get_jsockaddr$140(var2, var3);
         case 141:
            return this._get_jsockaddr2$141(var2, var3);
         case 142:
            return this._is_ip_address$142(var2, var3);
         case 143:
            return this.is_ipv4_address$143(var2, var3);
         case 144:
            return this.is_ipv6_address$144(var2, var3);
         case 145:
            return this.is_ip_address$145(var2, var3);
         case 146:
            return this._use_ipv4_addresses_only$146(var2, var3);
         case 147:
            return this._getaddrinfo_get_host$147(var2, var3);
         case 148:
            return this._getaddrinfo_get_port$148(var2, var3);
         case 149:
            return this.getaddrinfo$149(var2, var3);
         case 150:
            return this.f$150(var2, var3);
         case 151:
            return this.f$151(var2, var3);
         case 152:
            return this.f$152(var2, var3);
         case 153:
            return this.htons$153(var2, var3);
         case 154:
            return this.htonl$154(var2, var3);
         case 155:
            return this.ntohs$155(var2, var3);
         case 156:
            return this.ntohl$156(var2, var3);
         case 157:
            return this.inet_pton$157(var2, var3);
         case 158:
            return this.inet_ntop$158(var2, var3);
         case 159:
            return this.inet_aton$159(var2, var3);
         case 160:
            return this.inet_ntoa$160(var2, var3);
         case 161:
            return this._gethostbyaddr$161(var2, var3);
         case 162:
            return this.getfqdn$162(var2, var3);
         case 163:
            return this.gethostname$163(var2, var3);
         case 164:
            return this.gethostbyname$164(var2, var3);
         case 165:
            return this.gethostbyname_ex$165(var2, var3);
         case 166:
            return this.gethostbyaddr$166(var2, var3);
         case 167:
            return this.getservbyname$167(var2, var3);
         case 168:
            return this.getservbyport$168(var2, var3);
         case 169:
            return this.getprotobyname$169(var2, var3);
         case 170:
            return this.getservbyname$170(var2, var3);
         case 171:
            return this.getservbyport$171(var2, var3);
         case 172:
            return this.getprotobyname$172(var2, var3);
         case 173:
            return this._getnameinfo_get_host$173(var2, var3);
         case 174:
            return this._getnameinfo_get_port$174(var2, var3);
         case 175:
            return this.getnameinfo$175(var2, var3);
         case 176:
            return this._fileobject$176(var2, var3);
         case 177:
            return this.__init__$177(var2, var3);
         case 178:
            return this._getclosed$178(var2, var3);
         case 179:
            return this.close$179(var2, var3);
         case 180:
            return this.__del__$180(var2, var3);
         case 181:
            return this.flush$181(var2, var3);
         case 182:
            return this.fileno$182(var2, var3);
         case 183:
            return this.write$183(var2, var3);
         case 184:
            return this.writelines$184(var2, var3);
         case 185:
            return this.read$185(var2, var3);
         case 186:
            return this.readline$186(var2, var3);
         case 187:
            return this.readlines$187(var2, var3);
         case 188:
            return this.__iter__$188(var2, var3);
         case 189:
            return this.next$189(var2, var3);
         default:
            return null;
      }
   }
}
