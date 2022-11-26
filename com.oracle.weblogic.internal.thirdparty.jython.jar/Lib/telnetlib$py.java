import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
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
@MTime(1498849383000L)
@Filename("telnetlib.py")
public class telnetlib$py extends PyFunctionTable implements PyRunnable {
   static telnetlib$py self;
   static final PyCode f$0;
   static final PyCode Telnet$1;
   static final PyCode __init__$2;
   static final PyCode open$3;
   static final PyCode __del__$4;
   static final PyCode msg$5;
   static final PyCode set_debuglevel$6;
   static final PyCode close$7;
   static final PyCode get_socket$8;
   static final PyCode fileno$9;
   static final PyCode write$10;
   static final PyCode read_until$11;
   static final PyCode _read_until_with_poll$12;
   static final PyCode _read_until_with_select$13;
   static final PyCode read_all$14;
   static final PyCode read_some$15;
   static final PyCode read_very_eager$16;
   static final PyCode read_eager$17;
   static final PyCode read_lazy$18;
   static final PyCode read_very_lazy$19;
   static final PyCode read_sb_data$20;
   static final PyCode set_option_negotiation_callback$21;
   static final PyCode process_rawq$22;
   static final PyCode rawq_getchar$23;
   static final PyCode fill_rawq$24;
   static final PyCode sock_avail$25;
   static final PyCode interact$26;
   static final PyCode mt_interact$27;
   static final PyCode listener$28;
   static final PyCode expect$29;
   static final PyCode _expect_with_poll$30;
   static final PyCode _expect_with_select$31;
   static final PyCode test$32;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("TELNET client class.\n\nBased on RFC 854: TELNET Protocol Specification, by J. Postel and\nJ. Reynolds\n\nExample:\n\n>>> from telnetlib import Telnet\n>>> tn = Telnet('www.python.org', 79)   # connect to finger port\n>>> tn.write('guido\\r\\n')\n>>> print tn.read_all()\nLogin       Name               TTY         Idle    When    Where\nguido    Guido van Rossum      pts/2        <Dec  2 11:10> snag.cnri.reston..\n\n>>>\n\nNote that read_all() won't read until eof -- it just reads some data\n-- but it guarantees to read at least one byte unless EOF is hit.\n\nIt is possible to pass a Telnet object to select.select() in order to\nwait until more data is available.  Note that in this case,\nread_eager() may return '' even if there was data on the socket,\nbecause the protocol negotiation may have eaten the data.  This is why\nEOFError is needed in some cases to distinguish between \"no data\" and\n\"connection closed\" (since the socket also appears ready for reading\nwhen it is closed).\n\nTo do:\n- option negotiation\n- timeout should be intrinsic to the connection object instead of an\n  option on one of the read calls only\n\n"));
      var1.setline(33);
      PyString.fromInterned("TELNET client class.\n\nBased on RFC 854: TELNET Protocol Specification, by J. Postel and\nJ. Reynolds\n\nExample:\n\n>>> from telnetlib import Telnet\n>>> tn = Telnet('www.python.org', 79)   # connect to finger port\n>>> tn.write('guido\\r\\n')\n>>> print tn.read_all()\nLogin       Name               TTY         Idle    When    Where\nguido    Guido van Rossum      pts/2        <Dec  2 11:10> snag.cnri.reston..\n\n>>>\n\nNote that read_all() won't read until eof -- it just reads some data\n-- but it guarantees to read at least one byte unless EOF is hit.\n\nIt is possible to pass a Telnet object to select.select() in order to\nwait until more data is available.  Note that in this case,\nread_eager() may return '' even if there was data on the socket,\nbecause the protocol negotiation may have eaten the data.  This is why\nEOFError is needed in some cases to distinguish between \"no data\" and\n\"connection closed\" (since the socket also appears ready for reading\nwhen it is closed).\n\nTo do:\n- option negotiation\n- timeout should be intrinsic to the connection object instead of an\n  option on one of the read calls only\n\n");
      var1.setline(37);
      PyObject var3 = imp.importOne("errno", var1, -1);
      var1.setlocal("errno", var3);
      var3 = null;
      var1.setline(38);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(39);
      var3 = imp.importOne("socket", var1, -1);
      var1.setlocal("socket", var3);
      var3 = null;
      var1.setline(40);
      var3 = imp.importOne("select", var1, -1);
      var1.setlocal("select", var3);
      var3 = null;
      var1.setline(42);
      PyList var5 = new PyList(new PyObject[]{PyString.fromInterned("Telnet")});
      var1.setlocal("__all__", var5);
      var3 = null;
      var1.setline(45);
      PyInteger var6 = Py.newInteger(0);
      var1.setlocal("DEBUGLEVEL", var6);
      var3 = null;
      var1.setline(48);
      var6 = Py.newInteger(23);
      var1.setlocal("TELNET_PORT", var6);
      var3 = null;
      var1.setline(51);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(255));
      var1.setlocal("IAC", var3);
      var3 = null;
      var1.setline(52);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(254));
      var1.setlocal("DONT", var3);
      var3 = null;
      var1.setline(53);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(253));
      var1.setlocal("DO", var3);
      var3 = null;
      var1.setline(54);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(252));
      var1.setlocal("WONT", var3);
      var3 = null;
      var1.setline(55);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(251));
      var1.setlocal("WILL", var3);
      var3 = null;
      var1.setline(56);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal("theNULL", var3);
      var3 = null;
      var1.setline(58);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(240));
      var1.setlocal("SE", var3);
      var3 = null;
      var1.setline(59);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(241));
      var1.setlocal("NOP", var3);
      var3 = null;
      var1.setline(60);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(242));
      var1.setlocal("DM", var3);
      var3 = null;
      var1.setline(61);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(243));
      var1.setlocal("BRK", var3);
      var3 = null;
      var1.setline(62);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(244));
      var1.setlocal("IP", var3);
      var3 = null;
      var1.setline(63);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(245));
      var1.setlocal("AO", var3);
      var3 = null;
      var1.setline(64);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(246));
      var1.setlocal("AYT", var3);
      var3 = null;
      var1.setline(65);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(247));
      var1.setlocal("EC", var3);
      var3 = null;
      var1.setline(66);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(248));
      var1.setlocal("EL", var3);
      var3 = null;
      var1.setline(67);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(249));
      var1.setlocal("GA", var3);
      var3 = null;
      var1.setline(68);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(250));
      var1.setlocal("SB", var3);
      var3 = null;
      var1.setline(73);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal("BINARY", var3);
      var3 = null;
      var1.setline(74);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setlocal("ECHO", var3);
      var3 = null;
      var1.setline(75);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
      var1.setlocal("RCP", var3);
      var3 = null;
      var1.setline(76);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(3));
      var1.setlocal("SGA", var3);
      var3 = null;
      var1.setline(77);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(4));
      var1.setlocal("NAMS", var3);
      var3 = null;
      var1.setline(78);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(5));
      var1.setlocal("STATUS", var3);
      var3 = null;
      var1.setline(79);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(6));
      var1.setlocal("TM", var3);
      var3 = null;
      var1.setline(80);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(7));
      var1.setlocal("RCTE", var3);
      var3 = null;
      var1.setline(81);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(8));
      var1.setlocal("NAOL", var3);
      var3 = null;
      var1.setline(82);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(9));
      var1.setlocal("NAOP", var3);
      var3 = null;
      var1.setline(83);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(10));
      var1.setlocal("NAOCRD", var3);
      var3 = null;
      var1.setline(84);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(11));
      var1.setlocal("NAOHTS", var3);
      var3 = null;
      var1.setline(85);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(12));
      var1.setlocal("NAOHTD", var3);
      var3 = null;
      var1.setline(86);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(13));
      var1.setlocal("NAOFFD", var3);
      var3 = null;
      var1.setline(87);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(14));
      var1.setlocal("NAOVTS", var3);
      var3 = null;
      var1.setline(88);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(15));
      var1.setlocal("NAOVTD", var3);
      var3 = null;
      var1.setline(89);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(16));
      var1.setlocal("NAOLFD", var3);
      var3 = null;
      var1.setline(90);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(17));
      var1.setlocal("XASCII", var3);
      var3 = null;
      var1.setline(91);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(18));
      var1.setlocal("LOGOUT", var3);
      var3 = null;
      var1.setline(92);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(19));
      var1.setlocal("BM", var3);
      var3 = null;
      var1.setline(93);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(20));
      var1.setlocal("DET", var3);
      var3 = null;
      var1.setline(94);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(21));
      var1.setlocal("SUPDUP", var3);
      var3 = null;
      var1.setline(95);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(22));
      var1.setlocal("SUPDUPOUTPUT", var3);
      var3 = null;
      var1.setline(96);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(23));
      var1.setlocal("SNDLOC", var3);
      var3 = null;
      var1.setline(97);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(24));
      var1.setlocal("TTYPE", var3);
      var3 = null;
      var1.setline(98);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(25));
      var1.setlocal("EOR", var3);
      var3 = null;
      var1.setline(99);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(26));
      var1.setlocal("TUID", var3);
      var3 = null;
      var1.setline(100);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(27));
      var1.setlocal("OUTMRK", var3);
      var3 = null;
      var1.setline(101);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(28));
      var1.setlocal("TTYLOC", var3);
      var3 = null;
      var1.setline(102);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(29));
      var1.setlocal("VT3270REGIME", var3);
      var3 = null;
      var1.setline(103);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(30));
      var1.setlocal("X3PAD", var3);
      var3 = null;
      var1.setline(104);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(31));
      var1.setlocal("NAWS", var3);
      var3 = null;
      var1.setline(105);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(32));
      var1.setlocal("TSPEED", var3);
      var3 = null;
      var1.setline(106);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(33));
      var1.setlocal("LFLOW", var3);
      var3 = null;
      var1.setline(107);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(34));
      var1.setlocal("LINEMODE", var3);
      var3 = null;
      var1.setline(108);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(35));
      var1.setlocal("XDISPLOC", var3);
      var3 = null;
      var1.setline(109);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(36));
      var1.setlocal("OLD_ENVIRON", var3);
      var3 = null;
      var1.setline(110);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(37));
      var1.setlocal("AUTHENTICATION", var3);
      var3 = null;
      var1.setline(111);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(38));
      var1.setlocal("ENCRYPT", var3);
      var3 = null;
      var1.setline(112);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(39));
      var1.setlocal("NEW_ENVIRON", var3);
      var3 = null;
      var1.setline(117);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(40));
      var1.setlocal("TN3270E", var3);
      var3 = null;
      var1.setline(118);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(41));
      var1.setlocal("XAUTH", var3);
      var3 = null;
      var1.setline(119);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(42));
      var1.setlocal("CHARSET", var3);
      var3 = null;
      var1.setline(120);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(43));
      var1.setlocal("RSP", var3);
      var3 = null;
      var1.setline(121);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(44));
      var1.setlocal("COM_PORT_OPTION", var3);
      var3 = null;
      var1.setline(122);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(45));
      var1.setlocal("SUPPRESS_LOCAL_ECHO", var3);
      var3 = null;
      var1.setline(123);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(46));
      var1.setlocal("TLS", var3);
      var3 = null;
      var1.setline(124);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(47));
      var1.setlocal("KERMIT", var3);
      var3 = null;
      var1.setline(125);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(48));
      var1.setlocal("SEND_URL", var3);
      var3 = null;
      var1.setline(126);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(49));
      var1.setlocal("FORWARD_X", var3);
      var3 = null;
      var1.setline(127);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(138));
      var1.setlocal("PRAGMA_LOGON", var3);
      var3 = null;
      var1.setline(128);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(139));
      var1.setlocal("SSPI_LOGON", var3);
      var3 = null;
      var1.setline(129);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(140));
      var1.setlocal("PRAGMA_HEARTBEAT", var3);
      var3 = null;
      var1.setline(130);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(255));
      var1.setlocal("EXOPL", var3);
      var3 = null;
      var1.setline(131);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal("NOOPT", var3);
      var3 = null;
      var1.setline(133);
      PyObject[] var7 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("Telnet", var7, Telnet$1);
      var1.setlocal("Telnet", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(758);
      var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, test$32, PyString.fromInterned("Test program for telnetlib.\n\n    Usage: python telnetlib.py [-d] ... [host [port]]\n\n    Default host is localhost; default port is 23.\n\n    "));
      var1.setlocal("test", var8);
      var3 = null;
      var1.setline(786);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(787);
         var1.getname("test").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Telnet$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Telnet interface class.\n\n    An instance of this class represents a connection to a telnet\n    server.  The instance is initially not connected; the open()\n    method must be used to establish a connection.  Alternatively, the\n    host name and optional port number can be passed to the\n    constructor, too.\n\n    Don't try to reopen an already connected instance.\n\n    This class has many read_*() methods.  Note that some of them\n    raise EOFError when the end of the connection is read, because\n    they can return an empty string for other reasons.  See the\n    individual doc strings.\n\n    read_until(expected, [timeout])\n        Read until the expected string has been seen, or a timeout is\n        hit (default is no timeout); may block.\n\n    read_all()\n        Read all data until EOF; may block.\n\n    read_some()\n        Read at least one byte or EOF; may block.\n\n    read_very_eager()\n        Read all data available already queued or on the socket,\n        without blocking.\n\n    read_eager()\n        Read either data already queued or some data available on the\n        socket, without blocking.\n\n    read_lazy()\n        Read all data in the raw queue (processing it first), without\n        doing any socket I/O.\n\n    read_very_lazy()\n        Reads all data in the cooked queue, without doing any socket\n        I/O.\n\n    read_sb_data()\n        Reads available data between SB ... SE sequence. Don't block.\n\n    set_option_negotiation_callback(callback)\n        Each time a telnet option is read on the input flow, this callback\n        (if set) is called with the following parameters :\n        callback(telnet socket, command, option)\n            option will be chr(0) when there is no option.\n        No other action is done afterwards by telnetlib.\n\n    "));
      var1.setline(186);
      PyString.fromInterned("Telnet interface class.\n\n    An instance of this class represents a connection to a telnet\n    server.  The instance is initially not connected; the open()\n    method must be used to establish a connection.  Alternatively, the\n    host name and optional port number can be passed to the\n    constructor, too.\n\n    Don't try to reopen an already connected instance.\n\n    This class has many read_*() methods.  Note that some of them\n    raise EOFError when the end of the connection is read, because\n    they can return an empty string for other reasons.  See the\n    individual doc strings.\n\n    read_until(expected, [timeout])\n        Read until the expected string has been seen, or a timeout is\n        hit (default is no timeout); may block.\n\n    read_all()\n        Read all data until EOF; may block.\n\n    read_some()\n        Read at least one byte or EOF; may block.\n\n    read_very_eager()\n        Read all data available already queued or on the socket,\n        without blocking.\n\n    read_eager()\n        Read either data already queued or some data available on the\n        socket, without blocking.\n\n    read_lazy()\n        Read all data in the raw queue (processing it first), without\n        doing any socket I/O.\n\n    read_very_lazy()\n        Reads all data in the cooked queue, without doing any socket\n        I/O.\n\n    read_sb_data()\n        Reads available data between SB ... SE sequence. Don't block.\n\n    set_option_negotiation_callback(callback)\n        Each time a telnet option is read on the input flow, this callback\n        (if set) is called with the following parameters :\n        callback(telnet socket, command, option)\n            option will be chr(0) when there is no option.\n        No other action is done afterwards by telnetlib.\n\n    ");
      var1.setline(188);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), Py.newInteger(0), var1.getname("socket").__getattr__("_GLOBAL_DEFAULT_TIMEOUT")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, PyString.fromInterned("Constructor.\n\n        When called without arguments, create an unconnected instance.\n        With a hostname argument, it connects the instance; port number\n        and timeout are optional.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(213);
      var3 = new PyObject[]{Py.newInteger(0), var1.getname("socket").__getattr__("_GLOBAL_DEFAULT_TIMEOUT")};
      var4 = new PyFunction(var1.f_globals, var3, open$3, PyString.fromInterned("Connect to a host.\n\n        The optional second argument is the port number, which\n        defaults to the standard telnet port (23).\n\n        Don't try to reopen an already connected instance.\n        "));
      var1.setlocal("open", var4);
      var3 = null;
      var1.setline(229);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __del__$4, PyString.fromInterned("Destructor -- close the connection."));
      var1.setlocal("__del__", var4);
      var3 = null;
      var1.setline(233);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, msg$5, PyString.fromInterned("Print a debug message, when the debug level is > 0.\n\n        If extra arguments are present, they are substituted in the\n        message using the standard string formatting operator.\n\n        "));
      var1.setlocal("msg", var4);
      var3 = null;
      var1.setline(247);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_debuglevel$6, PyString.fromInterned("Set the debug level.\n\n        The higher it is, the more debug output you get (on sys.stdout).\n\n        "));
      var1.setlocal("set_debuglevel", var4);
      var3 = null;
      var1.setline(255);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$7, PyString.fromInterned("Close the connection."));
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(264);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_socket$8, PyString.fromInterned("Return the socket object used internally."));
      var1.setlocal("get_socket", var4);
      var3 = null;
      var1.setline(268);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, fileno$9, PyString.fromInterned("Return the fileno() of the socket object used internally."));
      var1.setlocal("fileno", var4);
      var3 = null;
      var1.setline(272);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, write$10, PyString.fromInterned("Write a string to the socket, doubling any IAC characters.\n\n        Can block if the connection is blocked.  May raise\n        socket.error if the connection is closed.\n\n        "));
      var1.setlocal("write", var4);
      var3 = null;
      var1.setline(284);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, read_until$11, PyString.fromInterned("Read until a given string is encountered or until timeout.\n\n        When no match is found, return whatever is available instead,\n        possibly the empty string.  Raise EOFError if the connection\n        is closed and no cooked data is available.\n\n        "));
      var1.setlocal("read_until", var4);
      var3 = null;
      var1.setline(297);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _read_until_with_poll$12, PyString.fromInterned("Read until a given string is encountered or until timeout.\n\n        This method uses select.poll() to implement the timeout.\n        "));
      var1.setlocal("_read_until_with_poll", var4);
      var3 = null;
      var1.setline(342);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, _read_until_with_select$13, PyString.fromInterned("Read until a given string is encountered or until timeout.\n\n        The timeout is implemented using select.select().\n        "));
      var1.setlocal("_read_until_with_select", var4);
      var3 = null;
      var1.setline(378);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read_all$14, PyString.fromInterned("Read all data until EOF; block until connection closed."));
      var1.setlocal("read_all", var4);
      var3 = null;
      var1.setline(388);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read_some$15, PyString.fromInterned("Read at least one byte of cooked data unless EOF is hit.\n\n        Return '' if EOF is hit.  Block if no data is immediately\n        available.\n\n        "));
      var1.setlocal("read_some", var4);
      var3 = null;
      var1.setline(403);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read_very_eager$16, PyString.fromInterned("Read everything that's possible without blocking in I/O (eager).\n\n        Raise EOFError if connection closed and no cooked data\n        available.  Return '' if no cooked data available otherwise.\n        Don't block unless in the midst of an IAC sequence.\n\n        "));
      var1.setlocal("read_very_eager", var4);
      var3 = null;
      var1.setline(417);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read_eager$17, PyString.fromInterned("Read readily available data.\n\n        Raise EOFError if connection closed and no cooked data\n        available.  Return '' if no cooked data available otherwise.\n        Don't block unless in the midst of an IAC sequence.\n\n        "));
      var1.setlocal("read_eager", var4);
      var3 = null;
      var1.setline(431);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read_lazy$18, PyString.fromInterned("Process and return data that's already in the queues (lazy).\n\n        Raise EOFError if connection closed and no data available.\n        Return '' if no cooked data available otherwise.  Don't block\n        unless in the midst of an IAC sequence.\n\n        "));
      var1.setlocal("read_lazy", var4);
      var3 = null;
      var1.setline(442);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read_very_lazy$19, PyString.fromInterned("Return any data available in the cooked queue (very lazy).\n\n        Raise EOFError if connection closed and no data available.\n        Return '' if no cooked data available otherwise.  Don't block.\n\n        "));
      var1.setlocal("read_very_lazy", var4);
      var3 = null;
      var1.setline(455);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read_sb_data$20, PyString.fromInterned("Return any data available in the SB ... SE queue.\n\n        Return '' if no SB ... SE available. Should only be called\n        after seeing a SB or SE command. When a new SB command is\n        found, old unread SB data will be discarded. Don't block.\n\n        "));
      var1.setlocal("read_sb_data", var4);
      var3 = null;
      var1.setline(467);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_option_negotiation_callback$21, PyString.fromInterned("Provide a callback function called after each receipt of a telnet option."));
      var1.setlocal("set_option_negotiation_callback", var4);
      var3 = null;
      var1.setline(471);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, process_rawq$22, PyString.fromInterned("Transfer from raw queue to cooked queue.\n\n        Set self.eof when connection is closed.  Don't block unless in\n        the midst of an IAC sequence.\n\n        "));
      var1.setlocal("process_rawq", var4);
      var3 = null;
      var1.setline(543);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, rawq_getchar$23, PyString.fromInterned("Get next char from raw queue.\n\n        Block if no data is immediately available.  Raise EOFError\n        when connection is closed.\n\n        "));
      var1.setlocal("rawq_getchar", var4);
      var3 = null;
      var1.setline(561);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, fill_rawq$24, PyString.fromInterned("Fill raw queue from exactly one recv() system call.\n\n        Block if no data is immediately available.  Set self.eof when\n        connection is closed.\n\n        "));
      var1.setlocal("fill_rawq", var4);
      var3 = null;
      var1.setline(578);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, sock_avail$25, PyString.fromInterned("Test whether data is available on the socket."));
      var1.setlocal("sock_avail", var4);
      var3 = null;
      var1.setline(582);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, interact$26, PyString.fromInterned("Interaction function, emulates a very dumb telnet client."));
      var1.setlocal("interact", var4);
      var3 = null;
      var1.setline(604);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, mt_interact$27, PyString.fromInterned("Multithreaded version of interact()."));
      var1.setlocal("mt_interact", var4);
      var3 = null;
      var1.setline(614);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, listener$28, PyString.fromInterned("Helper for mt_interact() -- this executes in the other thread."));
      var1.setlocal("listener", var4);
      var3 = null;
      var1.setline(627);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, expect$29, PyString.fromInterned("Read until one from a list of a regular expressions matches.\n\n        The first argument is a list of regular expressions, either\n        compiled (re.RegexObject instances) or uncompiled (strings).\n        The optional second argument is a timeout, in seconds; default\n        is no timeout.\n\n        Return a tuple of three items: the index in the list of the\n        first regular expression that matches; the match object\n        returned; and the text read up till and including the match.\n\n        If EOF is read and no text was read, raise EOFError.\n        Otherwise, when nothing matches, return (-1, None, text) where\n        text is the text received so far (may be the empty string if a\n        timeout happened).\n\n        If a regular expression ends with a greedy match (e.g. '.*')\n        or if more than one expression can match the same input, the\n        results are undeterministic, and may depend on the I/O timing.\n\n        "));
      var1.setlocal("expect", var4);
      var3 = null;
      var1.setline(654);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, _expect_with_poll$30, PyString.fromInterned("Read until one from a list of a regular expressions matches.\n\n        This method uses select.poll() to implement the timeout.\n        "));
      var1.setlocal("_expect_with_poll", var4);
      var3 = null;
      var1.setline(717);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, _expect_with_select$31, PyString.fromInterned("Read until one from a list of a regular expressions matches.\n\n        The timeout is implemented using select.select().\n        "));
      var1.setlocal("_expect_with_select", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(195);
      PyString.fromInterned("Constructor.\n\n        When called without arguments, create an unconnected instance.\n        With a hostname argument, it connects the instance; port number\n        and timeout are optional.\n        ");
      var1.setline(196);
      PyObject var3 = var1.getglobal("DEBUGLEVEL");
      var1.getlocal(0).__setattr__("debuglevel", var3);
      var3 = null;
      var1.setline(197);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("host", var3);
      var3 = null;
      var1.setline(198);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("port", var3);
      var3 = null;
      var1.setline(199);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("timeout", var3);
      var3 = null;
      var1.setline(200);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("sock", var3);
      var3 = null;
      var1.setline(201);
      PyString var4 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"rawq", var4);
      var3 = null;
      var1.setline(202);
      PyInteger var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"irawq", var5);
      var3 = null;
      var1.setline(203);
      var4 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"cookedq", var4);
      var3 = null;
      var1.setline(204);
      var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"eof", var5);
      var3 = null;
      var1.setline(205);
      var4 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"iacseq", var4);
      var3 = null;
      var1.setline(206);
      var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"sb", var5);
      var3 = null;
      var1.setline(207);
      var4 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"sbdataq", var4);
      var3 = null;
      var1.setline(208);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("option_callback", var3);
      var3 = null;
      var1.setline(209);
      var3 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("select"), (PyObject)PyString.fromInterned("poll"));
      var1.getlocal(0).__setattr__("_has_poll", var3);
      var3 = null;
      var1.setline(210);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(211);
         var1.getlocal(0).__getattr__("open").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject open$3(PyFrame var1, ThreadState var2) {
      var1.setline(220);
      PyString.fromInterned("Connect to a host.\n\n        The optional second argument is the port number, which\n        defaults to the standard telnet port (23).\n\n        Don't try to reopen an already connected instance.\n        ");
      var1.setline(221);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"eof", var3);
      var3 = null;
      var1.setline(222);
      PyObject var4;
      if (var1.getlocal(2).__not__().__nonzero__()) {
         var1.setline(223);
         var4 = var1.getglobal("TELNET_PORT");
         var1.setlocal(2, var4);
         var3 = null;
      }

      var1.setline(224);
      var4 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("host", var4);
      var3 = null;
      var1.setline(225);
      var4 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("port", var4);
      var3 = null;
      var1.setline(226);
      var4 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("timeout", var4);
      var3 = null;
      var1.setline(227);
      var4 = var1.getglobal("socket").__getattr__("create_connection").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})), (PyObject)var1.getlocal(3));
      var1.getlocal(0).__setattr__("sock", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __del__$4(PyFrame var1, ThreadState var2) {
      var1.setline(230);
      PyString.fromInterned("Destructor -- close the connection.");
      var1.setline(231);
      var1.getlocal(0).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject msg$5(PyFrame var1, ThreadState var2) {
      var1.setline(239);
      PyString.fromInterned("Print a debug message, when the debug level is > 0.\n\n        If extra arguments are present, they are substituted in the\n        message using the standard string formatting operator.\n\n        ");
      var1.setline(240);
      PyObject var3 = var1.getlocal(0).__getattr__("debuglevel");
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(241);
         Py.printComma(PyString.fromInterned("Telnet(%s,%s):")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("host"), var1.getlocal(0).__getattr__("port")})));
         var1.setline(242);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(243);
            Py.println(var1.getlocal(1)._mod(var1.getlocal(2)));
         } else {
            var1.setline(245);
            Py.println(var1.getlocal(1));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_debuglevel$6(PyFrame var1, ThreadState var2) {
      var1.setline(252);
      PyString.fromInterned("Set the debug level.\n\n        The higher it is, the more debug output you get (on sys.stdout).\n\n        ");
      var1.setline(253);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("debuglevel", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$7(PyFrame var1, ThreadState var2) {
      var1.setline(256);
      PyString.fromInterned("Close the connection.");
      var1.setline(257);
      if (var1.getlocal(0).__getattr__("sock").__nonzero__()) {
         var1.setline(258);
         var1.getlocal(0).__getattr__("sock").__getattr__("close").__call__(var2);
      }

      var1.setline(259);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"sock", var3);
      var3 = null;
      var1.setline(260);
      var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"eof", var3);
      var3 = null;
      var1.setline(261);
      PyString var4 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"iacseq", var4);
      var3 = null;
      var1.setline(262);
      var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"sb", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_socket$8(PyFrame var1, ThreadState var2) {
      var1.setline(265);
      PyString.fromInterned("Return the socket object used internally.");
      var1.setline(266);
      PyObject var3 = var1.getlocal(0).__getattr__("sock");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject fileno$9(PyFrame var1, ThreadState var2) {
      var1.setline(269);
      PyString.fromInterned("Return the fileno() of the socket object used internally.");
      var1.setline(270);
      PyObject var3 = var1.getlocal(0).__getattr__("sock").__getattr__("fileno").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject write$10(PyFrame var1, ThreadState var2) {
      var1.setline(278);
      PyString.fromInterned("Write a string to the socket, doubling any IAC characters.\n\n        Can block if the connection is blocked.  May raise\n        socket.error if the connection is closed.\n\n        ");
      var1.setline(279);
      PyObject var3 = var1.getglobal("IAC");
      PyObject var10000 = var3._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(280);
         var3 = var1.getlocal(1).__getattr__("replace").__call__(var2, var1.getglobal("IAC"), var1.getglobal("IAC")._add(var1.getglobal("IAC")));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(281);
      var1.getlocal(0).__getattr__("msg").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("send %r"), (PyObject)var1.getlocal(1));
      var1.setline(282);
      var1.getlocal(0).__getattr__("sock").__getattr__("sendall").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read_until$11(PyFrame var1, ThreadState var2) {
      var1.setline(291);
      PyString.fromInterned("Read until a given string is encountered or until timeout.\n\n        When no match is found, return whatever is available instead,\n        possibly the empty string.  Raise EOFError if the connection\n        is closed and no cooked data is available.\n\n        ");
      var1.setline(292);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_has_poll").__nonzero__()) {
         var1.setline(293);
         var3 = var1.getlocal(0).__getattr__("_read_until_with_poll").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(295);
         var3 = var1.getlocal(0).__getattr__("_read_until_with_select").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _read_until_with_poll$12(PyFrame var1, ThreadState var2) {
      var1.setline(301);
      PyString.fromInterned("Read until a given string is encountered or until timeout.\n\n        This method uses select.poll() to implement the timeout.\n        ");
      var1.setline(302);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(303);
      var3 = var1.getlocal(2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(304);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(305);
         String[] var8 = new String[]{"time"};
         PyObject[] var9 = imp.importFrom("time", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(306);
         var3 = var1.getlocal(5).__call__(var2);
         var1.setlocal(6, var3);
         var3 = null;
      }

      var1.setline(307);
      var1.getlocal(0).__getattr__("process_rawq").__call__(var2);
      var1.setline(308);
      var3 = var1.getlocal(0).__getattr__("cookedq").__getattr__("find").__call__(var2, var1.getlocal(1));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(309);
      var3 = var1.getlocal(7);
      var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(310);
         var3 = var1.getglobal("select").__getattr__("poll").__call__(var2);
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(311);
         var3 = var1.getglobal("select").__getattr__("POLLIN")._or(var1.getglobal("select").__getattr__("POLLPRI"));
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(312);
         var1.getlocal(8).__getattr__("register").__call__(var2, var1.getlocal(0), var1.getlocal(9));

         label66:
         while(true) {
            var1.setline(313);
            var3 = var1.getlocal(7);
            var10000 = var3._lt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("eof").__not__();
            }

            if (!var10000.__nonzero__()) {
               break;
            }

            try {
               var1.setline(315);
               var3 = var1.getlocal(8).__getattr__("poll").__call__(var2, var1.getlocal(4));
               var1.setlocal(10, var3);
               var3 = null;
            } catch (Throwable var7) {
               PyException var11 = Py.setException(var7, var1);
               if (var11.match(var1.getglobal("select").__getattr__("error"))) {
                  var4 = var11.value;
                  var1.setlocal(11, var4);
                  var4 = null;
                  var1.setline(317);
                  var4 = var1.getlocal(11).__getattr__("errno");
                  var10000 = var4._eq(var1.getglobal("errno").__getattr__("EINTR"));
                  var4 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(322);
                     throw Py.makeException();
                  }

                  var1.setline(318);
                  var4 = var1.getlocal(2);
                  var10000 = var4._isnot(var1.getglobal("None"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(319);
                     var4 = var1.getlocal(5).__call__(var2)._sub(var1.getlocal(6));
                     var1.setlocal(12, var4);
                     var4 = null;
                     var1.setline(320);
                     var4 = var1.getlocal(2)._sub(var1.getlocal(12));
                     var1.setlocal(4, var4);
                     var4 = null;
                  }
                  continue;
               }

               throw var11;
            }

            var1.setline(323);
            var3 = var1.getlocal(10).__iter__();

            while(true) {
               var1.setline(323);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(329);
                  var3 = var1.getlocal(2);
                  var10000 = var3._isnot(var1.getglobal("None"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(330);
                     var3 = var1.getlocal(5).__call__(var2)._sub(var1.getlocal(6));
                     var1.setlocal(12, var3);
                     var3 = null;
                     var1.setline(331);
                     var3 = var1.getlocal(12);
                     var10000 = var3._ge(var1.getlocal(2));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        break label66;
                     }

                     var1.setline(333);
                     var3 = var1.getlocal(2)._sub(var1.getlocal(12));
                     var1.setlocal(4, var3);
                     var3 = null;
                  }
                  break;
               }

               PyObject[] var5 = Py.unpackSequence(var4, 2);
               PyObject var6 = var5[0];
               var1.setlocal(13, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(14, var6);
               var6 = null;
               var1.setline(324);
               if (var1.getlocal(14)._and(var1.getlocal(9)).__nonzero__()) {
                  var1.setline(325);
                  PyObject var10 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("cookedq"))._sub(var1.getlocal(3)));
                  var1.setlocal(7, var10);
                  var5 = null;
                  var1.setline(326);
                  var1.getlocal(0).__getattr__("fill_rawq").__call__(var2);
                  var1.setline(327);
                  var1.getlocal(0).__getattr__("process_rawq").__call__(var2);
                  var1.setline(328);
                  var10 = var1.getlocal(0).__getattr__("cookedq").__getattr__("find").__call__(var2, var1.getlocal(1), var1.getlocal(7));
                  var1.setlocal(7, var10);
                  var5 = null;
               }
            }
         }

         var1.setline(334);
         var1.getlocal(8).__getattr__("unregister").__call__(var2, var1.getlocal(0));
      }

      var1.setline(335);
      var3 = var1.getlocal(7);
      var10000 = var3._ge(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(336);
         var3 = var1.getlocal(7)._add(var1.getlocal(3));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(337);
         var3 = var1.getlocal(0).__getattr__("cookedq").__getslice__((PyObject)null, var1.getlocal(7), (PyObject)null);
         var1.setlocal(15, var3);
         var3 = null;
         var1.setline(338);
         var3 = var1.getlocal(0).__getattr__("cookedq").__getslice__(var1.getlocal(7), (PyObject)null, (PyObject)null);
         var1.getlocal(0).__setattr__("cookedq", var3);
         var3 = null;
         var1.setline(339);
         var3 = var1.getlocal(15);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(340);
         var3 = var1.getlocal(0).__getattr__("read_very_lazy").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _read_until_with_select$13(PyFrame var1, ThreadState var2) {
      var1.setline(346);
      PyString.fromInterned("Read until a given string is encountered or until timeout.\n\n        The timeout is implemented using select.select().\n        ");
      var1.setline(347);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(348);
      var1.getlocal(0).__getattr__("process_rawq").__call__(var2);
      var1.setline(349);
      var3 = var1.getlocal(0).__getattr__("cookedq").__getattr__("find").__call__(var2, var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(350);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._ge(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(351);
         var3 = var1.getlocal(4)._add(var1.getlocal(3));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(352);
         var3 = var1.getlocal(0).__getattr__("cookedq").__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null);
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(353);
         var3 = var1.getlocal(0).__getattr__("cookedq").__getslice__(var1.getlocal(4), (PyObject)null, (PyObject)null);
         var1.getlocal(0).__setattr__("cookedq", var3);
         var3 = null;
         var1.setline(354);
         var3 = var1.getlocal(5);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(355);
         PyTuple var4 = new PyTuple(new PyObject[]{new PyList(new PyObject[]{var1.getlocal(0)}), new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects)});
         var1.setlocal(6, var4);
         var4 = null;
         var1.setline(356);
         PyObject var8 = var1.getlocal(6);
         var1.setlocal(7, var8);
         var4 = null;
         var1.setline(357);
         var8 = var1.getlocal(2);
         var10000 = var8._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(358);
            var8 = var1.getlocal(7)._add(new PyTuple(new PyObject[]{var1.getlocal(2)}));
            var1.setlocal(7, var8);
            var4 = null;
            var1.setline(359);
            String[] var9 = new String[]{"time"};
            PyObject[] var10 = imp.importFrom("time", var9, var1, -1);
            PyObject var5 = var10[0];
            var1.setlocal(8, var5);
            var5 = null;
            var1.setline(360);
            var8 = var1.getlocal(8).__call__(var2);
            var1.setlocal(9, var8);
            var4 = null;
         }

         while(true) {
            var1.setline(361);
            var10000 = var1.getlocal(0).__getattr__("eof").__not__();
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("select").__getattr__("select");
               PyObject[] var6 = Py.EmptyObjects;
               String[] var7 = new String[0];
               var10000 = var10000._callextra(var6, var7, var1.getlocal(7), (PyObject)null);
               var6 = null;
               var8 = var10000;
               var10000 = var8._eq(var1.getlocal(6));
               var4 = null;
            }

            if (!var10000.__nonzero__()) {
               break;
            }

            var1.setline(362);
            var8 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("cookedq"))._sub(var1.getlocal(3)));
            var1.setlocal(4, var8);
            var4 = null;
            var1.setline(363);
            var1.getlocal(0).__getattr__("fill_rawq").__call__(var2);
            var1.setline(364);
            var1.getlocal(0).__getattr__("process_rawq").__call__(var2);
            var1.setline(365);
            var8 = var1.getlocal(0).__getattr__("cookedq").__getattr__("find").__call__(var2, var1.getlocal(1), var1.getlocal(4));
            var1.setlocal(4, var8);
            var4 = null;
            var1.setline(366);
            var8 = var1.getlocal(4);
            var10000 = var8._ge(Py.newInteger(0));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(367);
               var8 = var1.getlocal(4)._add(var1.getlocal(3));
               var1.setlocal(4, var8);
               var4 = null;
               var1.setline(368);
               var8 = var1.getlocal(0).__getattr__("cookedq").__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null);
               var1.setlocal(5, var8);
               var4 = null;
               var1.setline(369);
               var8 = var1.getlocal(0).__getattr__("cookedq").__getslice__(var1.getlocal(4), (PyObject)null, (PyObject)null);
               var1.getlocal(0).__setattr__("cookedq", var8);
               var4 = null;
               var1.setline(370);
               var3 = var1.getlocal(5);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(371);
            var8 = var1.getlocal(2);
            var10000 = var8._isnot(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(372);
               var8 = var1.getlocal(8).__call__(var2)._sub(var1.getlocal(9));
               var1.setlocal(10, var8);
               var4 = null;
               var1.setline(373);
               var8 = var1.getlocal(10);
               var10000 = var8._ge(var1.getlocal(2));
               var4 = null;
               if (var10000.__nonzero__()) {
                  break;
               }

               var1.setline(375);
               var8 = var1.getlocal(6)._add(new PyTuple(new PyObject[]{var1.getlocal(2)._sub(var1.getlocal(10))}));
               var1.setlocal(7, var8);
               var4 = null;
            }
         }

         var1.setline(376);
         var3 = var1.getlocal(0).__getattr__("read_very_lazy").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject read_all$14(PyFrame var1, ThreadState var2) {
      var1.setline(379);
      PyString.fromInterned("Read all data until EOF; block until connection closed.");
      var1.setline(380);
      var1.getlocal(0).__getattr__("process_rawq").__call__(var2);

      while(true) {
         var1.setline(381);
         if (!var1.getlocal(0).__getattr__("eof").__not__().__nonzero__()) {
            var1.setline(384);
            PyObject var3 = var1.getlocal(0).__getattr__("cookedq");
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(385);
            PyString var4 = PyString.fromInterned("");
            var1.getlocal(0).__setattr__((String)"cookedq", var4);
            var3 = null;
            var1.setline(386);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(382);
         var1.getlocal(0).__getattr__("fill_rawq").__call__(var2);
         var1.setline(383);
         var1.getlocal(0).__getattr__("process_rawq").__call__(var2);
      }
   }

   public PyObject read_some$15(PyFrame var1, ThreadState var2) {
      var1.setline(394);
      PyString.fromInterned("Read at least one byte of cooked data unless EOF is hit.\n\n        Return '' if EOF is hit.  Block if no data is immediately\n        available.\n\n        ");
      var1.setline(395);
      var1.getlocal(0).__getattr__("process_rawq").__call__(var2);

      while(true) {
         var1.setline(396);
         PyObject var10000 = var1.getlocal(0).__getattr__("cookedq").__not__();
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("eof").__not__();
         }

         if (!var10000.__nonzero__()) {
            var1.setline(399);
            PyObject var3 = var1.getlocal(0).__getattr__("cookedq");
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(400);
            PyString var4 = PyString.fromInterned("");
            var1.getlocal(0).__setattr__((String)"cookedq", var4);
            var3 = null;
            var1.setline(401);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(397);
         var1.getlocal(0).__getattr__("fill_rawq").__call__(var2);
         var1.setline(398);
         var1.getlocal(0).__getattr__("process_rawq").__call__(var2);
      }
   }

   public PyObject read_very_eager$16(PyFrame var1, ThreadState var2) {
      var1.setline(410);
      PyString.fromInterned("Read everything that's possible without blocking in I/O (eager).\n\n        Raise EOFError if connection closed and no cooked data\n        available.  Return '' if no cooked data available otherwise.\n        Don't block unless in the midst of an IAC sequence.\n\n        ");
      var1.setline(411);
      var1.getlocal(0).__getattr__("process_rawq").__call__(var2);

      while(true) {
         var1.setline(412);
         PyObject var10000 = var1.getlocal(0).__getattr__("eof").__not__();
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("sock_avail").__call__(var2);
         }

         if (!var10000.__nonzero__()) {
            var1.setline(415);
            PyObject var3 = var1.getlocal(0).__getattr__("read_very_lazy").__call__(var2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(413);
         var1.getlocal(0).__getattr__("fill_rawq").__call__(var2);
         var1.setline(414);
         var1.getlocal(0).__getattr__("process_rawq").__call__(var2);
      }
   }

   public PyObject read_eager$17(PyFrame var1, ThreadState var2) {
      var1.setline(424);
      PyString.fromInterned("Read readily available data.\n\n        Raise EOFError if connection closed and no cooked data\n        available.  Return '' if no cooked data available otherwise.\n        Don't block unless in the midst of an IAC sequence.\n\n        ");
      var1.setline(425);
      var1.getlocal(0).__getattr__("process_rawq").__call__(var2);

      while(true) {
         var1.setline(426);
         PyObject var10000 = var1.getlocal(0).__getattr__("cookedq").__not__();
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("eof").__not__();
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("sock_avail").__call__(var2);
            }
         }

         if (!var10000.__nonzero__()) {
            var1.setline(429);
            PyObject var3 = var1.getlocal(0).__getattr__("read_very_lazy").__call__(var2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(427);
         var1.getlocal(0).__getattr__("fill_rawq").__call__(var2);
         var1.setline(428);
         var1.getlocal(0).__getattr__("process_rawq").__call__(var2);
      }
   }

   public PyObject read_lazy$18(PyFrame var1, ThreadState var2) {
      var1.setline(438);
      PyString.fromInterned("Process and return data that's already in the queues (lazy).\n\n        Raise EOFError if connection closed and no data available.\n        Return '' if no cooked data available otherwise.  Don't block\n        unless in the midst of an IAC sequence.\n\n        ");
      var1.setline(439);
      var1.getlocal(0).__getattr__("process_rawq").__call__(var2);
      var1.setline(440);
      PyObject var3 = var1.getlocal(0).__getattr__("read_very_lazy").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject read_very_lazy$19(PyFrame var1, ThreadState var2) {
      var1.setline(448);
      PyString.fromInterned("Return any data available in the cooked queue (very lazy).\n\n        Raise EOFError if connection closed and no data available.\n        Return '' if no cooked data available otherwise.  Don't block.\n\n        ");
      var1.setline(449);
      PyObject var3 = var1.getlocal(0).__getattr__("cookedq");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(450);
      PyString var4 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"cookedq", var4);
      var3 = null;
      var1.setline(451);
      PyObject var10000 = var1.getlocal(1).__not__();
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("eof");
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("rawq").__not__();
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(452);
         throw Py.makeException(var1.getglobal("EOFError"), PyString.fromInterned("telnet connection closed"));
      } else {
         var1.setline(453);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject read_sb_data$20(PyFrame var1, ThreadState var2) {
      var1.setline(462);
      PyString.fromInterned("Return any data available in the SB ... SE queue.\n\n        Return '' if no SB ... SE available. Should only be called\n        after seeing a SB or SE command. When a new SB command is\n        found, old unread SB data will be discarded. Don't block.\n\n        ");
      var1.setline(463);
      PyObject var3 = var1.getlocal(0).__getattr__("sbdataq");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(464);
      PyString var4 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"sbdataq", var4);
      var3 = null;
      var1.setline(465);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_option_negotiation_callback$21(PyFrame var1, ThreadState var2) {
      var1.setline(468);
      PyString.fromInterned("Provide a callback function called after each receipt of a telnet option.");
      var1.setline(469);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("option_callback", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject process_rawq$22(PyFrame var1, ThreadState var2) {
      var1.setline(477);
      PyString.fromInterned("Transfer from raw queue to cooked queue.\n\n        Set self.eof when connection is closed.  Don't block unless in\n        the midst of an IAC sequence.\n\n        ");
      var1.setline(478);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("")});
      var1.setlocal(1, var3);
      var3 = null;

      PyObject var8;
      try {
         while(true) {
            var1.setline(480);
            if (!var1.getlocal(0).__getattr__("rawq").__nonzero__()) {
               break;
            }

            var1.setline(481);
            var8 = var1.getlocal(0).__getattr__("rawq_getchar").__call__(var2);
            var1.setlocal(2, var8);
            var3 = null;
            var1.setline(482);
            PyObject var5;
            PyObject var10;
            String var11;
            PyObject var10000;
            if (var1.getlocal(0).__getattr__("iacseq").__not__().__nonzero__()) {
               var1.setline(483);
               var8 = var1.getlocal(2);
               var10000 = var8._eq(var1.getglobal("theNULL"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(485);
                  var8 = var1.getlocal(2);
                  var10000 = var8._eq(PyString.fromInterned("\u0011"));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(487);
                     var8 = var1.getlocal(2);
                     var10000 = var8._ne(var1.getglobal("IAC"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(488);
                        var8 = var1.getlocal(1).__getitem__(var1.getlocal(0).__getattr__("sb"))._add(var1.getlocal(2));
                        var1.getlocal(1).__setitem__(var1.getlocal(0).__getattr__("sb"), var8);
                        var3 = null;
                     } else {
                        var1.setline(491);
                        var10000 = var1.getlocal(0);
                        var11 = "iacseq";
                        var10 = var10000;
                        var5 = var10.__getattr__(var11);
                        var5 = var5._iadd(var1.getlocal(2));
                        var10.__setattr__(var11, var5);
                     }
                  }
               }
            } else {
               var1.setline(492);
               var8 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("iacseq"));
               var10000 = var8._eq(Py.newInteger(1));
               var3 = null;
               PyString var12;
               if (var10000.__nonzero__()) {
                  var1.setline(494);
                  var8 = var1.getlocal(2);
                  var10000 = var8._in(new PyTuple(new PyObject[]{var1.getglobal("DO"), var1.getglobal("DONT"), var1.getglobal("WILL"), var1.getglobal("WONT")}));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(495);
                     var10000 = var1.getlocal(0);
                     var11 = "iacseq";
                     var10 = var10000;
                     var5 = var10.__getattr__(var11);
                     var5 = var5._iadd(var1.getlocal(2));
                     var10.__setattr__(var11, var5);
                  } else {
                     var1.setline(498);
                     var12 = PyString.fromInterned("");
                     var1.getlocal(0).__setattr__((String)"iacseq", var12);
                     var3 = null;
                     var1.setline(499);
                     var8 = var1.getlocal(2);
                     var10000 = var8._eq(var1.getglobal("IAC"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(500);
                        var8 = var1.getlocal(1).__getitem__(var1.getlocal(0).__getattr__("sb"))._add(var1.getlocal(2));
                        var1.getlocal(1).__setitem__(var1.getlocal(0).__getattr__("sb"), var8);
                        var3 = null;
                     } else {
                        var1.setline(502);
                        var8 = var1.getlocal(2);
                        var10000 = var8._eq(var1.getglobal("SB"));
                        var3 = null;
                        PyInteger var13;
                        if (var10000.__nonzero__()) {
                           var1.setline(503);
                           var13 = Py.newInteger(1);
                           var1.getlocal(0).__setattr__((String)"sb", var13);
                           var3 = null;
                           var1.setline(504);
                           var12 = PyString.fromInterned("");
                           var1.getlocal(0).__setattr__((String)"sbdataq", var12);
                           var3 = null;
                        } else {
                           var1.setline(505);
                           var8 = var1.getlocal(2);
                           var10000 = var8._eq(var1.getglobal("SE"));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(506);
                              var13 = Py.newInteger(0);
                              var1.getlocal(0).__setattr__((String)"sb", var13);
                              var3 = null;
                              var1.setline(507);
                              var8 = var1.getlocal(0).__getattr__("sbdataq")._add(var1.getlocal(1).__getitem__(Py.newInteger(1)));
                              var1.getlocal(0).__setattr__("sbdataq", var8);
                              var3 = null;
                              var1.setline(508);
                              var12 = PyString.fromInterned("");
                              var1.getlocal(1).__setitem__((PyObject)Py.newInteger(1), var12);
                              var3 = null;
                           }
                        }

                        var1.setline(509);
                        if (var1.getlocal(0).__getattr__("option_callback").__nonzero__()) {
                           var1.setline(512);
                           var1.getlocal(0).__getattr__("option_callback").__call__(var2, var1.getlocal(0).__getattr__("sock"), var1.getlocal(2), var1.getglobal("NOOPT"));
                        } else {
                           var1.setline(517);
                           var1.getlocal(0).__getattr__("msg").__call__(var2, PyString.fromInterned("IAC %d not recognized")._mod(var1.getglobal("ord").__call__(var2, var1.getlocal(2))));
                        }
                     }
                  }
               } else {
                  var1.setline(518);
                  var8 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("iacseq"));
                  var10000 = var8._eq(Py.newInteger(2));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(519);
                     var8 = var1.getlocal(0).__getattr__("iacseq").__getitem__(Py.newInteger(1));
                     var1.setlocal(3, var8);
                     var3 = null;
                     var1.setline(520);
                     var12 = PyString.fromInterned("");
                     var1.getlocal(0).__setattr__((String)"iacseq", var12);
                     var3 = null;
                     var1.setline(521);
                     var8 = var1.getlocal(2);
                     var1.setlocal(4, var8);
                     var3 = null;
                     var1.setline(522);
                     var8 = var1.getlocal(3);
                     var10000 = var8._in(new PyTuple(new PyObject[]{var1.getglobal("DO"), var1.getglobal("DONT")}));
                     var3 = null;
                     PyString var10002;
                     Object var10003;
                     if (var10000.__nonzero__()) {
                        var1.setline(523);
                        var10000 = var1.getlocal(0).__getattr__("msg");
                        var10002 = PyString.fromInterned("IAC %s %d");
                        var8 = var1.getlocal(3);
                        var10003 = var8._eq(var1.getglobal("DO"));
                        var3 = null;
                        if (((PyObject)var10003).__nonzero__()) {
                           var10003 = PyString.fromInterned("DO");
                        }

                        if (!((PyObject)var10003).__nonzero__()) {
                           var10003 = PyString.fromInterned("DONT");
                        }

                        var10000.__call__((ThreadState)var2, var10002, (PyObject)var10003, (PyObject)var1.getglobal("ord").__call__(var2, var1.getlocal(4)));
                        var1.setline(525);
                        if (var1.getlocal(0).__getattr__("option_callback").__nonzero__()) {
                           var1.setline(526);
                           var1.getlocal(0).__getattr__("option_callback").__call__(var2, var1.getlocal(0).__getattr__("sock"), var1.getlocal(3), var1.getlocal(4));
                        } else {
                           var1.setline(528);
                           var1.getlocal(0).__getattr__("sock").__getattr__("sendall").__call__(var2, var1.getglobal("IAC")._add(var1.getglobal("WONT"))._add(var1.getlocal(4)));
                        }
                     } else {
                        var1.setline(529);
                        var8 = var1.getlocal(3);
                        var10000 = var8._in(new PyTuple(new PyObject[]{var1.getglobal("WILL"), var1.getglobal("WONT")}));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(530);
                           var10000 = var1.getlocal(0).__getattr__("msg");
                           var10002 = PyString.fromInterned("IAC %s %d");
                           var8 = var1.getlocal(3);
                           var10003 = var8._eq(var1.getglobal("WILL"));
                           var3 = null;
                           if (((PyObject)var10003).__nonzero__()) {
                              var10003 = PyString.fromInterned("WILL");
                           }

                           if (!((PyObject)var10003).__nonzero__()) {
                              var10003 = PyString.fromInterned("WONT");
                           }

                           var10000.__call__((ThreadState)var2, var10002, (PyObject)var10003, (PyObject)var1.getglobal("ord").__call__(var2, var1.getlocal(4)));
                           var1.setline(532);
                           if (var1.getlocal(0).__getattr__("option_callback").__nonzero__()) {
                              var1.setline(533);
                              var1.getlocal(0).__getattr__("option_callback").__call__(var2, var1.getlocal(0).__getattr__("sock"), var1.getlocal(3), var1.getlocal(4));
                           } else {
                              var1.setline(535);
                              var1.getlocal(0).__getattr__("sock").__getattr__("sendall").__call__(var2, var1.getglobal("IAC")._add(var1.getglobal("DONT"))._add(var1.getlocal(4)));
                           }
                        }
                     }
                  }
               }
            }
         }
      } catch (Throwable var6) {
         PyException var7 = Py.setException(var6, var1);
         if (!var7.match(var1.getglobal("EOFError"))) {
            throw var7;
         }

         var1.setline(537);
         PyString var4 = PyString.fromInterned("");
         var1.getlocal(0).__setattr__((String)"iacseq", var4);
         var4 = null;
         var1.setline(538);
         PyInteger var9 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"sb", var9);
         var4 = null;
         var1.setline(539);
      }

      var1.setline(540);
      var8 = var1.getlocal(0).__getattr__("cookedq")._add(var1.getlocal(1).__getitem__(Py.newInteger(0)));
      var1.getlocal(0).__setattr__("cookedq", var8);
      var3 = null;
      var1.setline(541);
      var8 = var1.getlocal(0).__getattr__("sbdataq")._add(var1.getlocal(1).__getitem__(Py.newInteger(1)));
      var1.getlocal(0).__setattr__("sbdataq", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject rawq_getchar$23(PyFrame var1, ThreadState var2) {
      var1.setline(549);
      PyString.fromInterned("Get next char from raw queue.\n\n        Block if no data is immediately available.  Raise EOFError\n        when connection is closed.\n\n        ");
      var1.setline(550);
      if (var1.getlocal(0).__getattr__("rawq").__not__().__nonzero__()) {
         var1.setline(551);
         var1.getlocal(0).__getattr__("fill_rawq").__call__(var2);
         var1.setline(552);
         if (var1.getlocal(0).__getattr__("eof").__nonzero__()) {
            var1.setline(553);
            throw Py.makeException(var1.getglobal("EOFError"));
         }
      }

      var1.setline(554);
      PyObject var3 = var1.getlocal(0).__getattr__("rawq").__getitem__(var1.getlocal(0).__getattr__("irawq"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(555);
      var3 = var1.getlocal(0).__getattr__("irawq")._add(Py.newInteger(1));
      var1.getlocal(0).__setattr__("irawq", var3);
      var3 = null;
      var1.setline(556);
      var3 = var1.getlocal(0).__getattr__("irawq");
      PyObject var10000 = var3._ge(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("rawq")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(557);
         PyString var4 = PyString.fromInterned("");
         var1.getlocal(0).__setattr__((String)"rawq", var4);
         var3 = null;
         var1.setline(558);
         PyInteger var5 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"irawq", var5);
         var3 = null;
      }

      var1.setline(559);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject fill_rawq$24(PyFrame var1, ThreadState var2) {
      var1.setline(567);
      PyString.fromInterned("Fill raw queue from exactly one recv() system call.\n\n        Block if no data is immediately available.  Set self.eof when\n        connection is closed.\n\n        ");
      var1.setline(568);
      PyObject var3 = var1.getlocal(0).__getattr__("irawq");
      PyObject var10000 = var3._ge(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("rawq")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(569);
         PyString var4 = PyString.fromInterned("");
         var1.getlocal(0).__setattr__((String)"rawq", var4);
         var3 = null;
         var1.setline(570);
         PyInteger var5 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"irawq", var5);
         var3 = null;
      }

      var1.setline(573);
      var3 = var1.getlocal(0).__getattr__("sock").__getattr__("recv").__call__((ThreadState)var2, (PyObject)Py.newInteger(50));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(574);
      var1.getlocal(0).__getattr__("msg").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("recv %r"), (PyObject)var1.getlocal(1));
      var1.setline(575);
      var3 = var1.getlocal(1).__not__();
      var1.getlocal(0).__setattr__("eof", var3);
      var3 = null;
      var1.setline(576);
      var3 = var1.getlocal(0).__getattr__("rawq")._add(var1.getlocal(1));
      var1.getlocal(0).__setattr__("rawq", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject sock_avail$25(PyFrame var1, ThreadState var2) {
      var1.setline(579);
      PyString.fromInterned("Test whether data is available on the socket.");
      var1.setline(580);
      PyObject var3 = var1.getglobal("select").__getattr__("select").__call__(var2, new PyList(new PyObject[]{var1.getlocal(0)}), new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects), Py.newInteger(0));
      PyObject var10000 = var3._eq(new PyTuple(new PyObject[]{new PyList(new PyObject[]{var1.getlocal(0)}), new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects)}));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject interact$26(PyFrame var1, ThreadState var2) {
      var1.setline(583);
      PyString.fromInterned("Interaction function, emulates a very dumb telnet client.");
      var1.setline(584);
      PyObject var3 = var1.getglobal("sys").__getattr__("platform");
      PyObject var10000 = var3._eq(PyString.fromInterned("win32"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(585);
         var1.getlocal(0).__getattr__("mt_interact").__call__(var2);
         var1.setline(586);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         while(true) {
            var1.setline(587);
            if (!Py.newInteger(1).__nonzero__()) {
               break;
            }

            var1.setline(588);
            var3 = var1.getglobal("select").__getattr__("select").__call__((ThreadState)var2, new PyList(new PyObject[]{var1.getlocal(0), var1.getglobal("sys").__getattr__("stdin")}), (PyObject)(new PyList(Py.EmptyObjects)), (PyObject)(new PyList(Py.EmptyObjects)));
            PyObject[] var4 = Py.unpackSequence(var3, 3);
            PyObject var5 = var4[0];
            var1.setlocal(1, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(2, var5);
            var5 = null;
            var5 = var4[2];
            var1.setlocal(3, var5);
            var5 = null;
            var3 = null;
            var1.setline(589);
            var3 = var1.getlocal(0);
            var10000 = var3._in(var1.getlocal(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               try {
                  var1.setline(591);
                  var3 = var1.getlocal(0).__getattr__("read_eager").__call__(var2);
                  var1.setlocal(4, var3);
                  var3 = null;
               } catch (Throwable var6) {
                  PyException var7 = Py.setException(var6, var1);
                  if (var7.match(var1.getglobal("EOFError"))) {
                     var1.setline(593);
                     Py.println(PyString.fromInterned("*** Connection closed by remote host ***"));
                     break;
                  }

                  throw var7;
               }

               var1.setline(595);
               if (var1.getlocal(4).__nonzero__()) {
                  var1.setline(596);
                  var1.getglobal("sys").__getattr__("stdout").__getattr__("write").__call__(var2, var1.getlocal(4));
                  var1.setline(597);
                  var1.getglobal("sys").__getattr__("stdout").__getattr__("flush").__call__(var2);
               }
            }

            var1.setline(598);
            var3 = var1.getglobal("sys").__getattr__("stdin");
            var10000 = var3._in(var1.getlocal(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(599);
               var3 = var1.getglobal("sys").__getattr__("stdin").__getattr__("readline").__call__(var2);
               var1.setlocal(5, var3);
               var3 = null;
               var1.setline(600);
               if (var1.getlocal(5).__not__().__nonzero__()) {
                  break;
               }

               var1.setline(602);
               var1.getlocal(0).__getattr__("write").__call__(var2, var1.getlocal(5));
            }
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject mt_interact$27(PyFrame var1, ThreadState var2) {
      var1.setline(605);
      PyString.fromInterned("Multithreaded version of interact().");
      var1.setline(606);
      PyObject var3 = imp.importOne("thread", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(607);
      var1.getlocal(1).__getattr__("start_new_thread").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("listener"), (PyObject)(new PyTuple(Py.EmptyObjects)));

      while(true) {
         var1.setline(608);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(609);
         var3 = var1.getglobal("sys").__getattr__("stdin").__getattr__("readline").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(610);
         if (var1.getlocal(2).__not__().__nonzero__()) {
            break;
         }

         var1.setline(612);
         var1.getlocal(0).__getattr__("write").__call__(var2, var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject listener$28(PyFrame var1, ThreadState var2) {
      var1.setline(615);
      PyString.fromInterned("Helper for mt_interact() -- this executes in the other thread.");

      while(true) {
         var1.setline(616);
         if (!Py.newInteger(1).__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyException var3;
         try {
            var1.setline(618);
            PyObject var5 = var1.getlocal(0).__getattr__("read_eager").__call__(var2);
            var1.setlocal(1, var5);
            var3 = null;
         } catch (Throwable var4) {
            var3 = Py.setException(var4, var1);
            if (var3.match(var1.getglobal("EOFError"))) {
               var1.setline(620);
               Py.println(PyString.fromInterned("*** Connection closed by remote host ***"));
               var1.setline(621);
               var1.f_lasti = -1;
               return Py.None;
            }

            throw var3;
         }

         var1.setline(622);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(623);
            var1.getglobal("sys").__getattr__("stdout").__getattr__("write").__call__(var2, var1.getlocal(1));
         } else {
            var1.setline(625);
            var1.getglobal("sys").__getattr__("stdout").__getattr__("flush").__call__(var2);
         }
      }
   }

   public PyObject expect$29(PyFrame var1, ThreadState var2) {
      var1.setline(648);
      PyString.fromInterned("Read until one from a list of a regular expressions matches.\n\n        The first argument is a list of regular expressions, either\n        compiled (re.RegexObject instances) or uncompiled (strings).\n        The optional second argument is a timeout, in seconds; default\n        is no timeout.\n\n        Return a tuple of three items: the index in the list of the\n        first regular expression that matches; the match object\n        returned; and the text read up till and including the match.\n\n        If EOF is read and no text was read, raise EOFError.\n        Otherwise, when nothing matches, return (-1, None, text) where\n        text is the text received so far (may be the empty string if a\n        timeout happened).\n\n        If a regular expression ends with a greedy match (e.g. '.*')\n        or if more than one expression can match the same input, the\n        results are undeterministic, and may depend on the I/O timing.\n\n        ");
      var1.setline(649);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_has_poll").__nonzero__()) {
         var1.setline(650);
         var3 = var1.getlocal(0).__getattr__("_expect_with_poll").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(652);
         var3 = var1.getlocal(0).__getattr__("_expect_with_select").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _expect_with_poll$30(PyFrame var1, ThreadState var2) {
      var1.setline(658);
      PyString.fromInterned("Read until one from a list of a regular expressions matches.\n\n        This method uses select.poll() to implement the timeout.\n        ");
      var1.setline(659);
      PyObject var3 = var1.getglobal("None");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(660);
      var3 = var1.getlocal(1).__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(661);
      var3 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(1)));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(662);
      var3 = var1.getlocal(4).__iter__();

      while(true) {
         var1.setline(662);
         PyObject var4 = var3.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(666);
            var3 = var1.getlocal(2);
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(667);
            var3 = var1.getlocal(2);
            PyObject var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(668);
               String[] var9 = new String[]{"time"};
               PyObject[] var10 = imp.importFrom("time", var9, var1, -1);
               var4 = var10[0];
               var1.setlocal(7, var4);
               var4 = null;
               var1.setline(669);
               var3 = var1.getlocal(7).__call__(var2);
               var1.setlocal(8, var3);
               var3 = null;
            }

            var1.setline(670);
            var1.getlocal(0).__getattr__("process_rawq").__call__(var2);
            var1.setline(671);
            var3 = var1.getglobal("None");
            var1.setlocal(9, var3);
            var3 = null;
            var1.setline(672);
            var3 = var1.getlocal(4).__iter__();

            while(true) {
               var1.setline(672);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  break;
               }

               var1.setlocal(5, var4);
               var1.setline(673);
               var5 = var1.getlocal(1).__getitem__(var1.getlocal(5)).__getattr__("search").__call__(var2, var1.getlocal(0).__getattr__("cookedq"));
               var1.setlocal(9, var5);
               var5 = null;
               var1.setline(674);
               if (var1.getlocal(9).__nonzero__()) {
                  var1.setline(675);
                  var5 = var1.getlocal(9).__getattr__("end").__call__(var2);
                  var1.setlocal(10, var5);
                  var5 = null;
                  var1.setline(676);
                  var5 = var1.getlocal(0).__getattr__("cookedq").__getslice__((PyObject)null, var1.getlocal(10), (PyObject)null);
                  var1.setlocal(11, var5);
                  var5 = null;
                  var1.setline(677);
                  var5 = var1.getlocal(0).__getattr__("cookedq").__getslice__(var1.getlocal(10), (PyObject)null, (PyObject)null);
                  var1.getlocal(0).__setattr__("cookedq", var5);
                  var5 = null;
                  break;
               }
            }

            var1.setline(679);
            if (var1.getlocal(9).__not__().__nonzero__()) {
               var1.setline(680);
               var3 = var1.getglobal("select").__getattr__("poll").__call__(var2);
               var1.setlocal(12, var3);
               var3 = null;
               var1.setline(681);
               var3 = var1.getglobal("select").__getattr__("POLLIN")._or(var1.getglobal("select").__getattr__("POLLPRI"));
               var1.setlocal(13, var3);
               var3 = null;
               var1.setline(682);
               var1.getlocal(12).__getattr__("register").__call__(var2, var1.getlocal(0), var1.getlocal(13));

               label103:
               while(true) {
                  var1.setline(683);
                  var10000 = var1.getlocal(9).__not__();
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getlocal(0).__getattr__("eof").__not__();
                  }

                  if (!var10000.__nonzero__()) {
                     break;
                  }

                  try {
                     var1.setline(685);
                     var3 = var1.getlocal(12).__getattr__("poll").__call__(var2, var1.getlocal(6));
                     var1.setlocal(14, var3);
                     var3 = null;
                  } catch (Throwable var8) {
                     PyException var12 = Py.setException(var8, var1);
                     if (var12.match(var1.getglobal("select").__getattr__("error"))) {
                        var4 = var12.value;
                        var1.setlocal(10, var4);
                        var4 = null;
                        var1.setline(687);
                        var4 = var1.getlocal(10).__getattr__("errno");
                        var10000 = var4._eq(var1.getglobal("errno").__getattr__("EINTR"));
                        var4 = null;
                        if (!var10000.__nonzero__()) {
                           var1.setline(692);
                           throw Py.makeException();
                        }

                        var1.setline(688);
                        var4 = var1.getlocal(2);
                        var10000 = var4._isnot(var1.getglobal("None"));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(689);
                           var4 = var1.getlocal(7).__call__(var2)._sub(var1.getlocal(8));
                           var1.setlocal(15, var4);
                           var4 = null;
                           var1.setline(690);
                           var4 = var1.getlocal(2)._sub(var1.getlocal(15));
                           var1.setlocal(6, var4);
                           var4 = null;
                        }
                        continue;
                     }

                     throw var12;
                  }

                  var1.setline(693);
                  var3 = var1.getlocal(14).__iter__();

                  while(true) {
                     label99:
                     while(true) {
                        PyObject var6;
                        do {
                           var1.setline(693);
                           var4 = var3.__iternext__();
                           if (var4 == null) {
                              var1.setline(704);
                              var3 = var1.getlocal(2);
                              var10000 = var3._isnot(var1.getglobal("None"));
                              var3 = null;
                              if (!var10000.__nonzero__()) {
                                 continue label103;
                              }

                              var1.setline(705);
                              var3 = var1.getlocal(7).__call__(var2)._sub(var1.getlocal(8));
                              var1.setlocal(15, var3);
                              var3 = null;
                              var1.setline(706);
                              var3 = var1.getlocal(15);
                              var10000 = var3._ge(var1.getlocal(2));
                              var3 = null;
                              if (var10000.__nonzero__()) {
                                 break label103;
                              }

                              var1.setline(708);
                              var3 = var1.getlocal(2)._sub(var1.getlocal(15));
                              var1.setlocal(6, var3);
                              var3 = null;
                              continue label103;
                           }

                           PyObject[] var11 = Py.unpackSequence(var4, 2);
                           var6 = var11[0];
                           var1.setlocal(16, var6);
                           var6 = null;
                           var6 = var11[1];
                           var1.setlocal(17, var6);
                           var6 = null;
                           var1.setline(694);
                        } while(!var1.getlocal(17)._and(var1.getlocal(13)).__nonzero__());

                        var1.setline(695);
                        var1.getlocal(0).__getattr__("fill_rawq").__call__(var2);
                        var1.setline(696);
                        var1.getlocal(0).__getattr__("process_rawq").__call__(var2);
                        var1.setline(697);
                        var5 = var1.getlocal(4).__iter__();

                        PyObject var7;
                        do {
                           var1.setline(697);
                           var6 = var5.__iternext__();
                           if (var6 == null) {
                              continue label99;
                           }

                           var1.setlocal(5, var6);
                           var1.setline(698);
                           var7 = var1.getlocal(1).__getitem__(var1.getlocal(5)).__getattr__("search").__call__(var2, var1.getlocal(0).__getattr__("cookedq"));
                           var1.setlocal(9, var7);
                           var7 = null;
                           var1.setline(699);
                        } while(!var1.getlocal(9).__nonzero__());

                        var1.setline(700);
                        var7 = var1.getlocal(9).__getattr__("end").__call__(var2);
                        var1.setlocal(10, var7);
                        var7 = null;
                        var1.setline(701);
                        var7 = var1.getlocal(0).__getattr__("cookedq").__getslice__((PyObject)null, var1.getlocal(10), (PyObject)null);
                        var1.setlocal(11, var7);
                        var7 = null;
                        var1.setline(702);
                        var7 = var1.getlocal(0).__getattr__("cookedq").__getslice__(var1.getlocal(10), (PyObject)null, (PyObject)null);
                        var1.getlocal(0).__setattr__("cookedq", var7);
                        var7 = null;
                     }
                  }
               }

               var1.setline(709);
               var1.getlocal(12).__getattr__("unregister").__call__(var2, var1.getlocal(0));
            }

            var1.setline(710);
            PyTuple var13;
            if (var1.getlocal(9).__nonzero__()) {
               var1.setline(711);
               var13 = new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(9), var1.getlocal(11)});
               var1.f_lasti = -1;
               return var13;
            } else {
               var1.setline(712);
               var4 = var1.getlocal(0).__getattr__("read_very_lazy").__call__(var2);
               var1.setlocal(11, var4);
               var4 = null;
               var1.setline(713);
               var10000 = var1.getlocal(11).__not__();
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(0).__getattr__("eof");
               }

               if (var10000.__nonzero__()) {
                  var1.setline(714);
                  throw Py.makeException(var1.getglobal("EOFError"));
               } else {
                  var1.setline(715);
                  var13 = new PyTuple(new PyObject[]{Py.newInteger(-1), var1.getglobal("None"), var1.getlocal(11)});
                  var1.f_lasti = -1;
                  return var13;
               }
            }
         }

         var1.setlocal(5, var4);
         var1.setline(663);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getitem__(var1.getlocal(5)), (PyObject)PyString.fromInterned("search")).__not__().__nonzero__()) {
            var1.setline(664);
            if (var1.getlocal(3).__not__().__nonzero__()) {
               var1.setline(664);
               var5 = imp.importOne("re", var1, -1);
               var1.setlocal(3, var5);
               var5 = null;
            }

            var1.setline(665);
            var5 = var1.getlocal(3).__getattr__("compile").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(5)));
            var1.getlocal(1).__setitem__(var1.getlocal(5), var5);
            var5 = null;
         }
      }
   }

   public PyObject _expect_with_select$31(PyFrame var1, ThreadState var2) {
      var1.setline(721);
      PyString.fromInterned("Read until one from a list of a regular expressions matches.\n\n        The timeout is implemented using select.select().\n        ");
      var1.setline(722);
      PyObject var3 = var1.getglobal("None");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(723);
      var3 = var1.getlocal(1).__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(724);
      var3 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(1)));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(725);
      var3 = var1.getlocal(4).__iter__();

      while(true) {
         var1.setline(725);
         PyObject var4 = var3.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(729);
            var3 = var1.getlocal(2);
            PyObject var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            PyObject[] var10;
            if (var10000.__nonzero__()) {
               var1.setline(730);
               String[] var9 = new String[]{"time"};
               var10 = imp.importFrom("time", var9, var1, -1);
               var4 = var10[0];
               var1.setlocal(6, var4);
               var4 = null;
               var1.setline(731);
               var3 = var1.getlocal(6).__call__(var2);
               var1.setlocal(7, var3);
               var3 = null;
            }

            PyTuple var11;
            label60:
            while(true) {
               var1.setline(732);
               if (!Py.newInteger(1).__nonzero__()) {
                  break;
               }

               var1.setline(733);
               var1.getlocal(0).__getattr__("process_rawq").__call__(var2);
               var1.setline(734);
               var3 = var1.getlocal(4).__iter__();

               do {
                  var1.setline(734);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.setline(741);
                     if (var1.getlocal(0).__getattr__("eof").__nonzero__()) {
                        break label60;
                     }

                     var1.setline(743);
                     var3 = var1.getlocal(2);
                     var10000 = var3._isnot(var1.getglobal("None"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(744);
                        var3 = var1.getlocal(6).__call__(var2)._sub(var1.getlocal(7));
                        var1.setlocal(11, var3);
                        var3 = null;
                        var1.setline(745);
                        var3 = var1.getlocal(11);
                        var10000 = var3._ge(var1.getlocal(2));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           break label60;
                        }

                        var1.setline(747);
                        PyTuple var12 = new PyTuple(new PyObject[]{new PyList(new PyObject[]{var1.getlocal(0).__getattr__("fileno").__call__(var2)}), new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects), var1.getlocal(2)._sub(var1.getlocal(11))});
                        var1.setlocal(12, var12);
                        var3 = null;
                        var1.setline(748);
                        var10000 = var1.getglobal("select").__getattr__("select");
                        var10 = Py.EmptyObjects;
                        String[] var7 = new String[0];
                        var10000 = var10000._callextra(var10, var7, var1.getlocal(12), (PyObject)null);
                        var3 = null;
                        var3 = var10000;
                        PyObject[] var8 = Py.unpackSequence(var3, 3);
                        PyObject var6 = var8[0];
                        var1.setlocal(13, var6);
                        var6 = null;
                        var6 = var8[1];
                        var1.setlocal(14, var6);
                        var6 = null;
                        var6 = var8[2];
                        var1.setlocal(15, var6);
                        var6 = null;
                        var3 = null;
                        var1.setline(749);
                        if (var1.getlocal(13).__not__().__nonzero__()) {
                           break label60;
                        }
                     }

                     var1.setline(751);
                     var1.getlocal(0).__getattr__("fill_rawq").__call__(var2);
                     continue label60;
                  }

                  var1.setlocal(5, var4);
                  var1.setline(735);
                  var5 = var1.getlocal(1).__getitem__(var1.getlocal(5)).__getattr__("search").__call__(var2, var1.getlocal(0).__getattr__("cookedq"));
                  var1.setlocal(8, var5);
                  var5 = null;
                  var1.setline(736);
               } while(!var1.getlocal(8).__nonzero__());

               var1.setline(737);
               var5 = var1.getlocal(8).__getattr__("end").__call__(var2);
               var1.setlocal(9, var5);
               var5 = null;
               var1.setline(738);
               var5 = var1.getlocal(0).__getattr__("cookedq").__getslice__((PyObject)null, var1.getlocal(9), (PyObject)null);
               var1.setlocal(10, var5);
               var5 = null;
               var1.setline(739);
               var5 = var1.getlocal(0).__getattr__("cookedq").__getslice__(var1.getlocal(9), (PyObject)null, (PyObject)null);
               var1.getlocal(0).__setattr__("cookedq", var5);
               var5 = null;
               var1.setline(740);
               var11 = new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(8), var1.getlocal(10)});
               var1.f_lasti = -1;
               return var11;
            }

            var1.setline(752);
            var3 = var1.getlocal(0).__getattr__("read_very_lazy").__call__(var2);
            var1.setlocal(10, var3);
            var3 = null;
            var1.setline(753);
            var10000 = var1.getlocal(10).__not__();
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("eof");
            }

            if (var10000.__nonzero__()) {
               var1.setline(754);
               throw Py.makeException(var1.getglobal("EOFError"));
            } else {
               var1.setline(755);
               var11 = new PyTuple(new PyObject[]{Py.newInteger(-1), var1.getglobal("None"), var1.getlocal(10)});
               var1.f_lasti = -1;
               return var11;
            }
         }

         var1.setlocal(5, var4);
         var1.setline(726);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getitem__(var1.getlocal(5)), (PyObject)PyString.fromInterned("search")).__not__().__nonzero__()) {
            var1.setline(727);
            if (var1.getlocal(3).__not__().__nonzero__()) {
               var1.setline(727);
               var5 = imp.importOne("re", var1, -1);
               var1.setlocal(3, var5);
               var5 = null;
            }

            var1.setline(728);
            var5 = var1.getlocal(3).__getattr__("compile").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(5)));
            var1.getlocal(1).__setitem__(var1.getlocal(5), var5);
            var5 = null;
         }
      }
   }

   public PyObject test$32(PyFrame var1, ThreadState var2) {
      var1.setline(765);
      PyString.fromInterned("Test program for telnetlib.\n\n    Usage: python telnetlib.py [-d] ... [host [port]]\n\n    Default host is localhost; default port is 23.\n\n    ");
      var1.setline(766);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(0, var3);
      var3 = null;

      while(true) {
         var1.setline(767);
         PyObject var10000 = var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
         PyObject var6;
         if (var10000.__nonzero__()) {
            var6 = var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(1));
            var10000 = var6._eq(PyString.fromInterned("-d"));
            var3 = null;
         }

         if (!var10000.__nonzero__()) {
            var1.setline(770);
            PyString var8 = PyString.fromInterned("localhost");
            var1.setlocal(1, var8);
            var3 = null;
            var1.setline(771);
            if (var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__nonzero__()) {
               var1.setline(772);
               var6 = var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(1));
               var1.setlocal(1, var6);
               var3 = null;
            }

            var1.setline(773);
            var3 = Py.newInteger(0);
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(774);
            if (var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null).__nonzero__()) {
               var1.setline(775);
               var6 = var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(2));
               var1.setlocal(3, var6);
               var3 = null;

               try {
                  var1.setline(777);
                  var6 = var1.getglobal("int").__call__(var2, var1.getlocal(3));
                  var1.setlocal(2, var6);
                  var3 = null;
               } catch (Throwable var5) {
                  PyException var9 = Py.setException(var5, var1);
                  if (!var9.match(var1.getglobal("ValueError"))) {
                     throw var9;
                  }

                  var1.setline(779);
                  PyObject var4 = var1.getglobal("socket").__getattr__("getservbyname").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("tcp"));
                  var1.setlocal(2, var4);
                  var4 = null;
               }
            }

            var1.setline(780);
            var6 = var1.getglobal("Telnet").__call__(var2);
            var1.setlocal(4, var6);
            var3 = null;
            var1.setline(781);
            var1.getlocal(4).__getattr__("set_debuglevel").__call__(var2, var1.getlocal(0));
            var1.setline(782);
            var10000 = var1.getlocal(4).__getattr__("open");
            PyObject[] var10 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), Py.newFloat(0.5)};
            String[] var7 = new String[]{"timeout"};
            var10000.__call__(var2, var10, var7);
            var3 = null;
            var1.setline(783);
            var1.getlocal(4).__getattr__("interact").__call__(var2);
            var1.setline(784);
            var1.getlocal(4).__getattr__("close").__call__(var2);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(768);
         var6 = var1.getlocal(0)._add(Py.newInteger(1));
         var1.setlocal(0, var6);
         var3 = null;
         var1.setline(769);
         var1.getglobal("sys").__getattr__("argv").__delitem__((PyObject)Py.newInteger(1));
      }
   }

   public telnetlib$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Telnet$1 = Py.newCode(0, var2, var1, "Telnet", 133, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "host", "port", "timeout"};
      __init__$2 = Py.newCode(4, var2, var1, "__init__", 188, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "host", "port", "timeout"};
      open$3 = Py.newCode(4, var2, var1, "open", 213, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __del__$4 = Py.newCode(1, var2, var1, "__del__", 229, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "args"};
      msg$5 = Py.newCode(3, var2, var1, "msg", 233, true, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "debuglevel"};
      set_debuglevel$6 = Py.newCode(2, var2, var1, "set_debuglevel", 247, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$7 = Py.newCode(1, var2, var1, "close", 255, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_socket$8 = Py.newCode(1, var2, var1, "get_socket", 264, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      fileno$9 = Py.newCode(1, var2, var1, "fileno", 268, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "buffer"};
      write$10 = Py.newCode(2, var2, var1, "write", 272, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "match", "timeout"};
      read_until$11 = Py.newCode(3, var2, var1, "read_until", 284, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "match", "timeout", "n", "call_timeout", "time", "time_start", "i", "poller", "poll_in_or_priority_flags", "ready", "e", "elapsed", "fd", "mode", "buf"};
      _read_until_with_poll$12 = Py.newCode(3, var2, var1, "_read_until_with_poll", 297, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "match", "timeout", "n", "i", "buf", "s_reply", "s_args", "time", "time_start", "elapsed"};
      _read_until_with_select$13 = Py.newCode(3, var2, var1, "_read_until_with_select", 342, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "buf"};
      read_all$14 = Py.newCode(1, var2, var1, "read_all", 378, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "buf"};
      read_some$15 = Py.newCode(1, var2, var1, "read_some", 388, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      read_very_eager$16 = Py.newCode(1, var2, var1, "read_very_eager", 403, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      read_eager$17 = Py.newCode(1, var2, var1, "read_eager", 417, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      read_lazy$18 = Py.newCode(1, var2, var1, "read_lazy", 431, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "buf"};
      read_very_lazy$19 = Py.newCode(1, var2, var1, "read_very_lazy", 442, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "buf"};
      read_sb_data$20 = Py.newCode(1, var2, var1, "read_sb_data", 455, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "callback"};
      set_option_negotiation_callback$21 = Py.newCode(2, var2, var1, "set_option_negotiation_callback", 467, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "buf", "c", "cmd", "opt"};
      process_rawq$22 = Py.newCode(1, var2, var1, "process_rawq", 471, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "c"};
      rawq_getchar$23 = Py.newCode(1, var2, var1, "rawq_getchar", 543, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "buf"};
      fill_rawq$24 = Py.newCode(1, var2, var1, "fill_rawq", 561, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      sock_avail$25 = Py.newCode(1, var2, var1, "sock_avail", 578, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "rfd", "wfd", "xfd", "text", "line"};
      interact$26 = Py.newCode(1, var2, var1, "interact", 582, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "thread", "line"};
      mt_interact$27 = Py.newCode(1, var2, var1, "mt_interact", 604, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      listener$28 = Py.newCode(1, var2, var1, "listener", 614, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "list", "timeout"};
      expect$29 = Py.newCode(3, var2, var1, "expect", 627, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "expect_list", "timeout", "re", "indices", "i", "call_timeout", "time", "time_start", "m", "e", "text", "poller", "poll_in_or_priority_flags", "ready", "elapsed", "fd", "mode"};
      _expect_with_poll$30 = Py.newCode(3, var2, var1, "_expect_with_poll", 654, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "list", "timeout", "re", "indices", "i", "time", "time_start", "m", "e", "text", "elapsed", "s_args", "r", "w", "x"};
      _expect_with_select$31 = Py.newCode(3, var2, var1, "_expect_with_select", 717, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"debuglevel", "host", "port", "portstr", "tn"};
      test$32 = Py.newCode(0, var2, var1, "test", 758, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new telnetlib$py("telnetlib$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(telnetlib$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Telnet$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.open$3(var2, var3);
         case 4:
            return this.__del__$4(var2, var3);
         case 5:
            return this.msg$5(var2, var3);
         case 6:
            return this.set_debuglevel$6(var2, var3);
         case 7:
            return this.close$7(var2, var3);
         case 8:
            return this.get_socket$8(var2, var3);
         case 9:
            return this.fileno$9(var2, var3);
         case 10:
            return this.write$10(var2, var3);
         case 11:
            return this.read_until$11(var2, var3);
         case 12:
            return this._read_until_with_poll$12(var2, var3);
         case 13:
            return this._read_until_with_select$13(var2, var3);
         case 14:
            return this.read_all$14(var2, var3);
         case 15:
            return this.read_some$15(var2, var3);
         case 16:
            return this.read_very_eager$16(var2, var3);
         case 17:
            return this.read_eager$17(var2, var3);
         case 18:
            return this.read_lazy$18(var2, var3);
         case 19:
            return this.read_very_lazy$19(var2, var3);
         case 20:
            return this.read_sb_data$20(var2, var3);
         case 21:
            return this.set_option_negotiation_callback$21(var2, var3);
         case 22:
            return this.process_rawq$22(var2, var3);
         case 23:
            return this.rawq_getchar$23(var2, var3);
         case 24:
            return this.fill_rawq$24(var2, var3);
         case 25:
            return this.sock_avail$25(var2, var3);
         case 26:
            return this.interact$26(var2, var3);
         case 27:
            return this.mt_interact$27(var2, var3);
         case 28:
            return this.listener$28(var2, var3);
         case 29:
            return this.expect$29(var2, var3);
         case 30:
            return this._expect_with_poll$30(var2, var3);
         case 31:
            return this._expect_with_select$31(var2, var3);
         case 32:
            return this.test$32(var2, var3);
         default:
            return null;
      }
   }
}
