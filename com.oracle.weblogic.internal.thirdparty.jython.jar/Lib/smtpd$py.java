import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
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
@MTime(1498849383000L)
@Filename("smtpd.py")
public class smtpd$py extends PyFunctionTable implements PyRunnable {
   static smtpd$py self;
   static final PyCode f$0;
   static final PyCode Devnull$1;
   static final PyCode write$2;
   static final PyCode flush$3;
   static final PyCode usage$4;
   static final PyCode SMTPChannel$5;
   static final PyCode __init__$6;
   static final PyCode push$7;
   static final PyCode collect_incoming_data$8;
   static final PyCode found_terminator$9;
   static final PyCode smtp_HELO$10;
   static final PyCode smtp_NOOP$11;
   static final PyCode smtp_QUIT$12;
   static final PyCode _SMTPChannel__getaddr$13;
   static final PyCode smtp_MAIL$14;
   static final PyCode smtp_RCPT$15;
   static final PyCode smtp_RSET$16;
   static final PyCode smtp_DATA$17;
   static final PyCode SMTPServer$18;
   static final PyCode __init__$19;
   static final PyCode handle_accept$20;
   static final PyCode process_message$21;
   static final PyCode DebuggingServer$22;
   static final PyCode process_message$23;
   static final PyCode PureProxy$24;
   static final PyCode process_message$25;
   static final PyCode _deliver$26;
   static final PyCode MailmanProxy$27;
   static final PyCode process_message$28;
   static final PyCode Options$29;
   static final PyCode parseargs$30;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("An RFC 2821 smtp proxy.\n\nUsage: %(program)s [options] [localhost:localport [remotehost:remoteport]]\n\nOptions:\n\n    --nosetuid\n    -n\n        This program generally tries to setuid `nobody', unless this flag is\n        set.  The setuid call will fail if this program is not run as root (in\n        which case, use this flag).\n\n    --version\n    -V\n        Print the version number and exit.\n\n    --class classname\n    -c classname\n        Use `classname' as the concrete SMTP proxy class.  Uses `PureProxy' by\n        default.\n\n    --debug\n    -d\n        Turn on debugging prints.\n\n    --help\n    -h\n        Print this message and exit.\n\nVersion: %(__version__)s\n\nIf localhost is not given then `localhost' is used, and if localport is not\ngiven then 8025 is used.  If remotehost is not given then `localhost' is used,\nand if remoteport is not given, then 25 is used.\n"));
      var1.setline(36);
      PyString.fromInterned("An RFC 2821 smtp proxy.\n\nUsage: %(program)s [options] [localhost:localport [remotehost:remoteport]]\n\nOptions:\n\n    --nosetuid\n    -n\n        This program generally tries to setuid `nobody', unless this flag is\n        set.  The setuid call will fail if this program is not run as root (in\n        which case, use this flag).\n\n    --version\n    -V\n        Print the version number and exit.\n\n    --class classname\n    -c classname\n        Use `classname' as the concrete SMTP proxy class.  Uses `PureProxy' by\n        default.\n\n    --debug\n    -d\n        Turn on debugging prints.\n\n    --help\n    -h\n        Print this message and exit.\n\nVersion: %(__version__)s\n\nIf localhost is not given then `localhost' is used, and if localport is not\ngiven then 8025 is used.  If remotehost is not given then `localhost' is used,\nand if remoteport is not given, then 25 is used.\n");
      var1.setline(72);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(73);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(74);
      var3 = imp.importOne("errno", var1, -1);
      var1.setlocal("errno", var3);
      var3 = null;
      var1.setline(75);
      var3 = imp.importOne("getopt", var1, -1);
      var1.setlocal("getopt", var3);
      var3 = null;
      var1.setline(76);
      var3 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var3);
      var3 = null;
      var1.setline(77);
      var3 = imp.importOne("socket", var1, -1);
      var1.setlocal("socket", var3);
      var3 = null;
      var1.setline(78);
      var3 = imp.importOne("asyncore", var1, -1);
      var1.setlocal("asyncore", var3);
      var3 = null;
      var1.setline(79);
      var3 = imp.importOne("asynchat", var1, -1);
      var1.setlocal("asynchat", var3);
      var3 = null;
      var1.setline(81);
      PyList var8 = new PyList(new PyObject[]{PyString.fromInterned("SMTPServer"), PyString.fromInterned("DebuggingServer"), PyString.fromInterned("PureProxy"), PyString.fromInterned("MailmanProxy")});
      var1.setlocal("__all__", var8);
      var3 = null;
      var1.setline(83);
      var3 = var1.getname("sys").__getattr__("argv").__getitem__(Py.newInteger(0));
      var1.setlocal("program", var3);
      var3 = null;
      var1.setline(84);
      PyString var9 = PyString.fromInterned("Python SMTP proxy version 0.2");
      var1.setlocal("__version__", var9);
      var3 = null;
      var1.setline(87);
      PyObject[] var10 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("Devnull", var10, Devnull$1);
      var1.setlocal("Devnull", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(92);
      var3 = var1.getname("Devnull").__call__(var2);
      var1.setlocal("DEBUGSTREAM", var3);
      var3 = null;
      var1.setline(93);
      var9 = PyString.fromInterned("\n");
      var1.setlocal("NEWLINE", var9);
      var3 = null;
      var1.setline(94);
      var9 = PyString.fromInterned("");
      var1.setlocal("EMPTYSTRING", var9);
      var3 = null;
      var1.setline(95);
      var9 = PyString.fromInterned(", ");
      var1.setlocal("COMMASPACE", var9);
      var3 = null;
      var1.setline(98);
      var10 = new PyObject[]{PyString.fromInterned("")};
      PyFunction var11 = new PyFunction(var1.f_globals, var10, usage$4, (PyObject)null);
      var1.setlocal("usage", var11);
      var3 = null;
      var1.setline(105);
      var10 = new PyObject[]{var1.getname("asynchat").__getattr__("async_chat")};
      var4 = Py.makeClass("SMTPChannel", var10, SMTPChannel$5);
      var1.setlocal("SMTPChannel", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(276);
      var10 = new PyObject[]{var1.getname("asyncore").__getattr__("dispatcher")};
      var4 = Py.makeClass("SMTPServer", var10, SMTPServer$18);
      var1.setlocal("SMTPServer", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(330);
      var10 = new PyObject[]{var1.getname("SMTPServer")};
      var4 = Py.makeClass("DebuggingServer", var10, DebuggingServer$22);
      var1.setlocal("DebuggingServer", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(345);
      var10 = new PyObject[]{var1.getname("SMTPServer")};
      var4 = Py.makeClass("PureProxy", var10, PureProxy$24);
      var1.setlocal("PureProxy", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(385);
      var10 = new PyObject[]{var1.getname("PureProxy")};
      var4 = Py.makeClass("MailmanProxy", var10, MailmanProxy$27);
      var1.setlocal("MailmanProxy", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(463);
      var10 = Py.EmptyObjects;
      var4 = Py.makeClass("Options", var10, Options$29);
      var1.setlocal("Options", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(468);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, parseargs$30, (PyObject)null);
      var1.setlocal("parseargs", var11);
      var3 = null;
      var1.setline(524);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(525);
         var3 = var1.getname("parseargs").__call__(var2);
         var1.setlocal("options", var3);
         var3 = null;
         var1.setline(527);
         var3 = var1.getname("options").__getattr__("classname");
         var1.setlocal("classname", var3);
         var3 = null;
         var1.setline(528);
         var9 = PyString.fromInterned(".");
         var10000 = var9._in(var1.getname("classname"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(529);
            var3 = var1.getname("classname").__getattr__("rfind").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
            var1.setlocal("lastdot", var3);
            var3 = null;
            var1.setline(530);
            var3 = var1.getname("__import__").__call__(var2, var1.getname("classname").__getslice__((PyObject)null, var1.getname("lastdot"), (PyObject)null), var1.getname("globals").__call__(var2), var1.getname("locals").__call__(var2), new PyList(new PyObject[]{PyString.fromInterned("")}));
            var1.setlocal("mod", var3);
            var3 = null;
            var1.setline(531);
            var3 = var1.getname("classname").__getslice__(var1.getname("lastdot")._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
            var1.setlocal("classname", var3);
            var3 = null;
         } else {
            var1.setline(533);
            var3 = imp.importOneAs("__main__", var1, -1);
            var1.setlocal("mod", var3);
            var3 = null;
         }

         var1.setline(534);
         var3 = var1.getname("getattr").__call__(var2, var1.getname("mod"), var1.getname("classname"));
         var1.setlocal("class_", var3);
         var3 = null;
         var1.setline(535);
         var3 = var1.getname("class_").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getname("options").__getattr__("localhost"), var1.getname("options").__getattr__("localport")})), (PyObject)(new PyTuple(new PyObject[]{var1.getname("options").__getattr__("remotehost"), var1.getname("options").__getattr__("remoteport")})));
         var1.setlocal("proxy", var3);
         var3 = null;
         var1.setline(537);
         PyException var12;
         if (var1.getname("options").__getattr__("setuid").__nonzero__()) {
            try {
               var1.setline(539);
               var3 = imp.importOne("pwd", var1, -1);
               var1.setlocal("pwd", var3);
               var3 = null;
            } catch (Throwable var7) {
               var12 = Py.setException(var7, var1);
               if (!var12.match(var1.getname("ImportError"))) {
                  throw var12;
               }

               var1.setline(541);
               var4 = var1.getname("sys").__getattr__("stderr");
               Py.println(var4, PyString.fromInterned("Cannot import module \"pwd\"; try running with -n option."));
               var1.setline(543);
               var1.getname("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
            }

            var1.setline(544);
            var3 = var1.getname("pwd").__getattr__("getpwnam").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("nobody")).__getitem__(Py.newInteger(2));
            var1.setlocal("nobody", var3);
            var3 = null;

            try {
               var1.setline(546);
               var1.getname("os").__getattr__("setuid").__call__(var2, var1.getname("nobody"));
            } catch (Throwable var6) {
               var12 = Py.setException(var6, var1);
               if (!var12.match(var1.getname("OSError"))) {
                  throw var12;
               }

               var4 = var12.value;
               var1.setlocal("e", var4);
               var4 = null;
               var1.setline(548);
               var4 = var1.getname("e").__getattr__("errno");
               var10000 = var4._ne(var1.getname("errno").__getattr__("EPERM"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(548);
                  throw Py.makeException();
               }

               var1.setline(549);
               var4 = var1.getname("sys").__getattr__("stderr");
               Py.println(var4, PyString.fromInterned("Cannot setuid \"nobody\"; try running with -n option."));
               var1.setline(551);
               var1.getname("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
            }
         }

         try {
            var1.setline(553);
            var1.getname("asyncore").__getattr__("loop").__call__(var2);
         } catch (Throwable var5) {
            var12 = Py.setException(var5, var1);
            if (!var12.match(var1.getname("KeyboardInterrupt"))) {
               throw var12;
            }

            var1.setline(555);
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Devnull$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(88);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, write$2, (PyObject)null);
      var1.setlocal("write", var4);
      var3 = null;
      var1.setline(89);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, flush$3, (PyObject)null);
      var1.setlocal("flush", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject write$2(PyFrame var1, ThreadState var2) {
      var1.setline(88);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject flush$3(PyFrame var1, ThreadState var2) {
      var1.setline(89);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject usage$4(PyFrame var1, ThreadState var2) {
      var1.setline(99);
      PyObject var3 = var1.getglobal("sys").__getattr__("stderr");
      Py.println(var3, var1.getglobal("__doc__")._mod(var1.getglobal("globals").__call__(var2)));
      var1.setline(100);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(101);
         var3 = var1.getglobal("sys").__getattr__("stderr");
         Py.println(var3, var1.getlocal(1));
      }

      var1.setline(102);
      var1.getglobal("sys").__getattr__("exit").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject SMTPChannel$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(106);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal("COMMAND", var3);
      var3 = null;
      var1.setline(107);
      var3 = Py.newInteger(1);
      var1.setlocal("DATA", var3);
      var3 = null;
      var1.setline(109);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$6, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(135);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, push$7, (PyObject)null);
      var1.setlocal("push", var5);
      var3 = null;
      var1.setline(139);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, collect_incoming_data$8, (PyObject)null);
      var1.setlocal("collect_incoming_data", var5);
      var3 = null;
      var1.setline(143);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, found_terminator$9, (PyObject)null);
      var1.setlocal("found_terminator", var5);
      var3 = null;
      var1.setline(192);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, smtp_HELO$10, (PyObject)null);
      var1.setlocal("smtp_HELO", var5);
      var3 = null;
      var1.setline(202);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, smtp_NOOP$11, (PyObject)null);
      var1.setlocal("smtp_NOOP", var5);
      var3 = null;
      var1.setline(208);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, smtp_QUIT$12, (PyObject)null);
      var1.setlocal("smtp_QUIT", var5);
      var3 = null;
      var1.setline(214);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _SMTPChannel__getaddr$13, (PyObject)null);
      var1.setlocal("_SMTPChannel__getaddr", var5);
      var3 = null;
      var1.setline(227);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, smtp_MAIL$14, (PyObject)null);
      var1.setlocal("smtp_MAIL", var5);
      var3 = null;
      var1.setline(240);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, smtp_RCPT$15, (PyObject)null);
      var1.setlocal("smtp_RCPT", var5);
      var3 = null;
      var1.setline(253);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, smtp_RSET$16, (PyObject)null);
      var1.setlocal("smtp_RSET", var5);
      var3 = null;
      var1.setline(264);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, smtp_DATA$17, (PyObject)null);
      var1.setlocal("smtp_DATA", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$6(PyFrame var1, ThreadState var2) {
      var1.setline(110);
      var1.getglobal("asynchat").__getattr__("async_chat").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(2));
      var1.setline(111);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_SMTPChannel__server", var3);
      var3 = null;
      var1.setline(112);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_SMTPChannel__conn", var3);
      var3 = null;
      var1.setline(113);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("_SMTPChannel__addr", var3);
      var3 = null;
      var1.setline(114);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_SMTPChannel__line", var6);
      var3 = null;
      var1.setline(115);
      var3 = var1.getlocal(0).__getattr__("COMMAND");
      var1.getlocal(0).__setattr__("_SMTPChannel__state", var3);
      var3 = null;
      var1.setline(116);
      PyInteger var7 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_SMTPChannel__greeting", var7);
      var3 = null;
      var1.setline(117);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_SMTPChannel__mailfrom", var3);
      var3 = null;
      var1.setline(118);
      var6 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_SMTPChannel__rcpttos", var6);
      var3 = null;
      var1.setline(119);
      PyString var8 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"_SMTPChannel__data", var8);
      var3 = null;
      var1.setline(120);
      var3 = var1.getglobal("socket").__getattr__("getfqdn").__call__(var2);
      var1.getlocal(0).__setattr__("_SMTPChannel__fqdn", var3);
      var3 = null;

      try {
         var1.setline(122);
         var3 = var1.getlocal(2).__getattr__("getpeername").__call__(var2);
         var1.getlocal(0).__setattr__("_SMTPChannel__peer", var3);
         var3 = null;
      } catch (Throwable var5) {
         PyException var9 = Py.setException(var5, var1);
         if (var9.match(var1.getglobal("socket").__getattr__("error"))) {
            PyObject var4 = var9.value;
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(126);
            var1.getlocal(0).__getattr__("close").__call__(var2);
            var1.setline(127);
            var4 = var1.getlocal(4).__getitem__(Py.newInteger(0));
            PyObject var10000 = var4._ne(var1.getglobal("errno").__getattr__("ENOTCONN"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(128);
               throw Py.makeException();
            }

            var1.setline(129);
            var1.f_lasti = -1;
            return Py.None;
         }

         throw var9;
      }

      var1.setline(130);
      var3 = var1.getglobal("DEBUGSTREAM");
      Py.printComma(var3, PyString.fromInterned("Peer:"));
      Py.println(var3, var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("_SMTPChannel__peer")));
      var1.setline(131);
      var1.getlocal(0).__getattr__("push").__call__(var2, PyString.fromInterned("220 %s %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_SMTPChannel__fqdn"), var1.getglobal("__version__")})));
      var1.setline(132);
      var1.getlocal(0).__getattr__("set_terminator").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\r\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject push$7(PyFrame var1, ThreadState var2) {
      var1.setline(136);
      var1.getglobal("asynchat").__getattr__("async_chat").__getattr__("push").__call__(var2, var1.getlocal(0), var1.getlocal(1)._add(PyString.fromInterned("\r\n")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject collect_incoming_data$8(PyFrame var1, ThreadState var2) {
      var1.setline(140);
      var1.getlocal(0).__getattr__("_SMTPChannel__line").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject found_terminator$9(PyFrame var1, ThreadState var2) {
      var1.setline(144);
      PyObject var3 = var1.getglobal("EMPTYSTRING").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_SMTPChannel__line"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(145);
      var3 = var1.getglobal("DEBUGSTREAM");
      Py.printComma(var3, PyString.fromInterned("Data:"));
      Py.println(var3, var1.getglobal("repr").__call__(var2, var1.getlocal(1)));
      var1.setline(146);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_SMTPChannel__line", var6);
      var3 = null;
      var1.setline(147);
      var3 = var1.getlocal(0).__getattr__("_SMTPChannel__state");
      PyObject var10000 = var3._eq(var1.getlocal(0).__getattr__("COMMAND"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(148);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(149);
            var1.getlocal(0).__getattr__("push").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("500 Error: bad syntax"));
            var1.setline(150);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(151);
            var3 = var1.getglobal("None");
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(152);
            var3 = var1.getlocal(1).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" "));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(153);
            var3 = var1.getlocal(3);
            var10000 = var3._lt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(154);
               var3 = var1.getlocal(1).__getattr__("upper").__call__(var2);
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(155);
               var3 = var1.getglobal("None");
               var1.setlocal(5, var3);
               var3 = null;
            } else {
               var1.setline(157);
               var3 = var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(3), (PyObject)null).__getattr__("upper").__call__(var2);
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(158);
               var3 = var1.getlocal(1).__getslice__(var1.getlocal(3)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null).__getattr__("strip").__call__(var2);
               var1.setlocal(5, var3);
               var3 = null;
            }

            var1.setline(159);
            var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), PyString.fromInterned("smtp_")._add(var1.getlocal(4)), var1.getglobal("None"));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(160);
            if (var1.getlocal(2).__not__().__nonzero__()) {
               var1.setline(161);
               var1.getlocal(0).__getattr__("push").__call__(var2, PyString.fromInterned("502 Error: command \"%s\" not implemented")._mod(var1.getlocal(4)));
               var1.setline(162);
               var1.f_lasti = -1;
               return Py.None;
            } else {
               var1.setline(163);
               var1.getlocal(2).__call__(var2, var1.getlocal(5));
               var1.setline(164);
               var1.f_lasti = -1;
               return Py.None;
            }
         }
      } else {
         var1.setline(166);
         var3 = var1.getlocal(0).__getattr__("_SMTPChannel__state");
         var10000 = var3._ne(var1.getlocal(0).__getattr__("DATA"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(167);
            var1.getlocal(0).__getattr__("push").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("451 Internal confusion"));
            var1.setline(168);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(171);
            var6 = new PyList(Py.EmptyObjects);
            var1.setlocal(6, var6);
            var3 = null;
            var1.setline(172);
            var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\r\n")).__iter__();

            while(true) {
               var1.setline(172);
               PyObject var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(177);
                  var3 = var1.getglobal("NEWLINE").__getattr__("join").__call__(var2, var1.getlocal(6));
                  var1.getlocal(0).__setattr__("_SMTPChannel__data", var3);
                  var3 = null;
                  var1.setline(178);
                  var3 = var1.getlocal(0).__getattr__("_SMTPChannel__server").__getattr__("process_message").__call__(var2, var1.getlocal(0).__getattr__("_SMTPChannel__peer"), var1.getlocal(0).__getattr__("_SMTPChannel__mailfrom"), var1.getlocal(0).__getattr__("_SMTPChannel__rcpttos"), var1.getlocal(0).__getattr__("_SMTPChannel__data"));
                  var1.setlocal(8, var3);
                  var3 = null;
                  var1.setline(182);
                  var6 = new PyList(Py.EmptyObjects);
                  var1.getlocal(0).__setattr__((String)"_SMTPChannel__rcpttos", var6);
                  var3 = null;
                  var1.setline(183);
                  var3 = var1.getglobal("None");
                  var1.getlocal(0).__setattr__("_SMTPChannel__mailfrom", var3);
                  var3 = null;
                  var1.setline(184);
                  var3 = var1.getlocal(0).__getattr__("COMMAND");
                  var1.getlocal(0).__setattr__("_SMTPChannel__state", var3);
                  var3 = null;
                  var1.setline(185);
                  var1.getlocal(0).__getattr__("set_terminator").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\r\n"));
                  var1.setline(186);
                  if (var1.getlocal(8).__not__().__nonzero__()) {
                     var1.setline(187);
                     var1.getlocal(0).__getattr__("push").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("250 Ok"));
                  } else {
                     var1.setline(189);
                     var1.getlocal(0).__getattr__("push").__call__(var2, var1.getlocal(8));
                  }

                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(7, var4);
               var1.setline(173);
               var10000 = var1.getlocal(7);
               if (var10000.__nonzero__()) {
                  PyObject var5 = var1.getlocal(7).__getitem__(Py.newInteger(0));
                  var10000 = var5._eq(PyString.fromInterned("."));
                  var5 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(174);
                  var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(7).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
               } else {
                  var1.setline(176);
                  var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(7));
               }
            }
         }
      }
   }

   public PyObject smtp_HELO$10(PyFrame var1, ThreadState var2) {
      var1.setline(193);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(194);
         var1.getlocal(0).__getattr__("push").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("501 Syntax: HELO hostname"));
         var1.setline(195);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(196);
         if (var1.getlocal(0).__getattr__("_SMTPChannel__greeting").__nonzero__()) {
            var1.setline(197);
            var1.getlocal(0).__getattr__("push").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("503 Duplicate HELO/EHLO"));
         } else {
            var1.setline(199);
            PyObject var3 = var1.getlocal(1);
            var1.getlocal(0).__setattr__("_SMTPChannel__greeting", var3);
            var3 = null;
            var1.setline(200);
            var1.getlocal(0).__getattr__("push").__call__(var2, PyString.fromInterned("250 %s")._mod(var1.getlocal(0).__getattr__("_SMTPChannel__fqdn")));
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject smtp_NOOP$11(PyFrame var1, ThreadState var2) {
      var1.setline(203);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(204);
         var1.getlocal(0).__getattr__("push").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("501 Syntax: NOOP"));
      } else {
         var1.setline(206);
         var1.getlocal(0).__getattr__("push").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("250 Ok"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject smtp_QUIT$12(PyFrame var1, ThreadState var2) {
      var1.setline(210);
      var1.getlocal(0).__getattr__("push").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("221 Bye"));
      var1.setline(211);
      var1.getlocal(0).__getattr__("close_when_done").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _SMTPChannel__getaddr$13(PyFrame var1, ThreadState var2) {
      var1.setline(215);
      PyObject var3 = var1.getglobal("None");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(216);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(217);
      var3 = var1.getlocal(2).__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null).__getattr__("upper").__call__(var2);
      PyObject var10000 = var3._eq(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(218);
         var3 = var1.getlocal(2).__getslice__(var1.getlocal(4), (PyObject)null, (PyObject)null).__getattr__("strip").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(219);
         if (var1.getlocal(3).__not__().__nonzero__()) {
            var1.setline(220);
         } else {
            var1.setline(221);
            var3 = var1.getlocal(3).__getitem__(Py.newInteger(0));
            var10000 = var3._eq(PyString.fromInterned("<"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(3).__getitem__(Py.newInteger(-1));
               var10000 = var3._eq(PyString.fromInterned(">"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(3);
                  var10000 = var3._ne(PyString.fromInterned("<>"));
                  var3 = null;
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(224);
               var3 = var1.getlocal(3).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null);
               var1.setlocal(3, var3);
               var3 = null;
            }
         }
      }

      var1.setline(225);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject smtp_MAIL$14(PyFrame var1, ThreadState var2) {
      var1.setline(228);
      PyObject var3 = var1.getglobal("DEBUGSTREAM");
      Py.printComma(var3, PyString.fromInterned("===> MAIL"));
      Py.println(var3, var1.getlocal(1));
      var1.setline(229);
      var1.setline(229);
      var3 = var1.getlocal(1).__nonzero__() ? var1.getlocal(0).__getattr__("_SMTPChannel__getaddr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("FROM:"), (PyObject)var1.getlocal(1)) : var1.getglobal("None");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(230);
      if (var1.getlocal(2).__not__().__nonzero__()) {
         var1.setline(231);
         var1.getlocal(0).__getattr__("push").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("501 Syntax: MAIL FROM:<address>"));
         var1.setline(232);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(233);
         if (var1.getlocal(0).__getattr__("_SMTPChannel__mailfrom").__nonzero__()) {
            var1.setline(234);
            var1.getlocal(0).__getattr__("push").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("503 Error: nested MAIL command"));
            var1.setline(235);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(236);
            var3 = var1.getlocal(2);
            var1.getlocal(0).__setattr__("_SMTPChannel__mailfrom", var3);
            var3 = null;
            var1.setline(237);
            var3 = var1.getglobal("DEBUGSTREAM");
            Py.printComma(var3, PyString.fromInterned("sender:"));
            Py.println(var3, var1.getlocal(0).__getattr__("_SMTPChannel__mailfrom"));
            var1.setline(238);
            var1.getlocal(0).__getattr__("push").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("250 Ok"));
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject smtp_RCPT$15(PyFrame var1, ThreadState var2) {
      var1.setline(241);
      PyObject var3 = var1.getglobal("DEBUGSTREAM");
      Py.printComma(var3, PyString.fromInterned("===> RCPT"));
      Py.println(var3, var1.getlocal(1));
      var1.setline(242);
      if (var1.getlocal(0).__getattr__("_SMTPChannel__mailfrom").__not__().__nonzero__()) {
         var1.setline(243);
         var1.getlocal(0).__getattr__("push").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("503 Error: need MAIL command"));
         var1.setline(244);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(245);
         var1.setline(245);
         var3 = var1.getlocal(1).__nonzero__() ? var1.getlocal(0).__getattr__("_SMTPChannel__getaddr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TO:"), (PyObject)var1.getlocal(1)) : var1.getglobal("None");
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(246);
         if (var1.getlocal(2).__not__().__nonzero__()) {
            var1.setline(247);
            var1.getlocal(0).__getattr__("push").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("501 Syntax: RCPT TO: <address>"));
            var1.setline(248);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(249);
            var1.getlocal(0).__getattr__("_SMTPChannel__rcpttos").__getattr__("append").__call__(var2, var1.getlocal(2));
            var1.setline(250);
            var3 = var1.getglobal("DEBUGSTREAM");
            Py.printComma(var3, PyString.fromInterned("recips:"));
            Py.println(var3, var1.getlocal(0).__getattr__("_SMTPChannel__rcpttos"));
            var1.setline(251);
            var1.getlocal(0).__getattr__("push").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("250 Ok"));
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject smtp_RSET$16(PyFrame var1, ThreadState var2) {
      var1.setline(254);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(255);
         var1.getlocal(0).__getattr__("push").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("501 Syntax: RSET"));
         var1.setline(256);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(258);
         PyObject var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("_SMTPChannel__mailfrom", var3);
         var3 = null;
         var1.setline(259);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"_SMTPChannel__rcpttos", var4);
         var3 = null;
         var1.setline(260);
         PyString var5 = PyString.fromInterned("");
         var1.getlocal(0).__setattr__((String)"_SMTPChannel__data", var5);
         var3 = null;
         var1.setline(261);
         var3 = var1.getlocal(0).__getattr__("COMMAND");
         var1.getlocal(0).__setattr__("_SMTPChannel__state", var3);
         var3 = null;
         var1.setline(262);
         var1.getlocal(0).__getattr__("push").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("250 Ok"));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject smtp_DATA$17(PyFrame var1, ThreadState var2) {
      var1.setline(265);
      if (var1.getlocal(0).__getattr__("_SMTPChannel__rcpttos").__not__().__nonzero__()) {
         var1.setline(266);
         var1.getlocal(0).__getattr__("push").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("503 Error: need RCPT command"));
         var1.setline(267);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(268);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(269);
            var1.getlocal(0).__getattr__("push").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("501 Syntax: DATA"));
            var1.setline(270);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(271);
            PyObject var3 = var1.getlocal(0).__getattr__("DATA");
            var1.getlocal(0).__setattr__("_SMTPChannel__state", var3);
            var3 = null;
            var1.setline(272);
            var1.getlocal(0).__getattr__("set_terminator").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\r\n.\r\n"));
            var1.setline(273);
            var1.getlocal(0).__getattr__("push").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("354 End data with <CR><LF>.<CR><LF>"));
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject SMTPServer$18(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(277);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$19, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(297);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, handle_accept$20, (PyObject)null);
      var1.setlocal("handle_accept", var4);
      var3 = null;
      var1.setline(305);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, process_message$21, PyString.fromInterned("Override this abstract method to handle messages from the client.\n\n        peer is a tuple containing (ipaddr, port) of the client that made the\n        socket connection to our smtp port.\n\n        mailfrom is the raw address the client claims the message is coming\n        from.\n\n        rcpttos is a list of raw addresses the client wishes to deliver the\n        message to.\n\n        data is a string containing the entire full text of the message,\n        headers (if supplied) and all.  It has been `de-transparencied'\n        according to RFC 821, Section 4.5.2.  In other words, a line\n        containing a `.' followed by other text has had the leading dot\n        removed.\n\n        This function should return None, for a normal `250 Ok' response;\n        otherwise it returns the desired response string in RFC 821 format.\n\n        "));
      var1.setlocal("process_message", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$19(PyFrame var1, ThreadState var2) {
      var1.setline(278);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_localaddr", var3);
      var3 = null;
      var1.setline(279);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_remoteaddr", var3);
      var3 = null;
      var1.setline(280);
      var1.getglobal("asyncore").__getattr__("dispatcher").__getattr__("__init__").__call__(var2, var1.getlocal(0));

      try {
         var1.setline(282);
         var1.getlocal(0).__getattr__("create_socket").__call__(var2, var1.getglobal("socket").__getattr__("AF_INET"), var1.getglobal("socket").__getattr__("SOCK_STREAM"));
         var1.setline(284);
         var1.getlocal(0).__getattr__("set_reuse_addr").__call__(var2);
         var1.setline(285);
         var1.getlocal(0).__getattr__("bind").__call__(var2, var1.getlocal(1));
         var1.setline(286);
         var1.getlocal(0).__getattr__("listen").__call__((ThreadState)var2, (PyObject)Py.newInteger(5));
      } catch (Throwable var5) {
         Py.setException(var5, var1);
         var1.setline(289);
         var1.getlocal(0).__getattr__("close").__call__(var2);
         var1.setline(290);
         throw Py.makeException();
      }

      var1.setline(292);
      PyObject var4 = var1.getglobal("DEBUGSTREAM");
      Py.println(var4, PyString.fromInterned("%s started at %s\n\tLocal addr: %s\n\tRemote addr:%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"), var1.getglobal("time").__getattr__("ctime").__call__(var2, var1.getglobal("time").__getattr__("time").__call__(var2)), var1.getlocal(1), var1.getlocal(2)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_accept$20(PyFrame var1, ThreadState var2) {
      var1.setline(298);
      PyObject var3 = var1.getlocal(0).__getattr__("accept").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(299);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(300);
         var3 = var1.getlocal(1);
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
         var1.setline(301);
         var3 = var1.getglobal("DEBUGSTREAM");
         Py.println(var3, PyString.fromInterned("Incoming connection from %s")._mod(var1.getglobal("repr").__call__(var2, var1.getlocal(3))));
         var1.setline(302);
         var3 = var1.getglobal("SMTPChannel").__call__(var2, var1.getlocal(0), var1.getlocal(2), var1.getlocal(3));
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject process_message$21(PyFrame var1, ThreadState var2) {
      var1.setline(326);
      PyString.fromInterned("Override this abstract method to handle messages from the client.\n\n        peer is a tuple containing (ipaddr, port) of the client that made the\n        socket connection to our smtp port.\n\n        mailfrom is the raw address the client claims the message is coming\n        from.\n\n        rcpttos is a list of raw addresses the client wishes to deliver the\n        message to.\n\n        data is a string containing the entire full text of the message,\n        headers (if supplied) and all.  It has been `de-transparencied'\n        according to RFC 821, Section 4.5.2.  In other words, a line\n        containing a `.' followed by other text has had the leading dot\n        removed.\n\n        This function should return None, for a normal `250 Ok' response;\n        otherwise it returns the desired response string in RFC 821 format.\n\n        ");
      var1.setline(327);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject DebuggingServer$22(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(332);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, process_message$23, (PyObject)null);
      var1.setlocal("process_message", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject process_message$23(PyFrame var1, ThreadState var2) {
      var1.setline(333);
      PyInteger var3 = Py.newInteger(1);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(334);
      PyObject var6 = var1.getlocal(4).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      var1.setlocal(6, var6);
      var3 = null;
      var1.setline(335);
      Py.println(PyString.fromInterned("---------- MESSAGE FOLLOWS ----------"));
      var1.setline(336);
      var6 = var1.getlocal(6).__iter__();

      while(true) {
         var1.setline(336);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(342);
            Py.println(PyString.fromInterned("------------ END MESSAGE ------------"));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(7, var4);
         var1.setline(338);
         PyObject var10000 = var1.getlocal(5);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(7).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(339);
            Py.printComma(PyString.fromInterned("X-Peer:"));
            Py.println(var1.getlocal(1).__getitem__(Py.newInteger(0)));
            var1.setline(340);
            PyInteger var5 = Py.newInteger(0);
            var1.setlocal(5, var5);
            var5 = null;
         }

         var1.setline(341);
         Py.println(var1.getlocal(7));
      }
   }

   public PyObject PureProxy$24(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(346);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, process_message$25, (PyObject)null);
      var1.setlocal("process_message", var4);
      var3 = null;
      var1.setline(360);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _deliver$26, (PyObject)null);
      var1.setlocal("_deliver", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject process_message$25(PyFrame var1, ThreadState var2) {
      var1.setline(347);
      PyObject var3 = var1.getlocal(4).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(349);
      PyInteger var6 = Py.newInteger(0);
      var1.setlocal(6, var6);
      var3 = null;
      var1.setline(350);
      var3 = var1.getlocal(5).__iter__();

      while(true) {
         var1.setline(350);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            break;
         }

         var1.setlocal(7, var4);
         var1.setline(351);
         if (var1.getlocal(7).__not__().__nonzero__()) {
            break;
         }

         var1.setline(353);
         PyObject var5 = var1.getlocal(6);
         var5 = var5._iadd(Py.newInteger(1));
         var1.setlocal(6, var5);
      }

      var1.setline(354);
      var1.getlocal(5).__getattr__("insert").__call__(var2, var1.getlocal(6), PyString.fromInterned("X-Peer: %s")._mod(var1.getlocal(1).__getitem__(Py.newInteger(0))));
      var1.setline(355);
      var3 = var1.getglobal("NEWLINE").__getattr__("join").__call__(var2, var1.getlocal(5));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(356);
      var3 = var1.getlocal(0).__getattr__("_deliver").__call__(var2, var1.getlocal(2), var1.getlocal(3), var1.getlocal(4));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(358);
      var3 = var1.getglobal("DEBUGSTREAM");
      Py.printComma(var3, PyString.fromInterned("we got some refusals:"));
      Py.println(var3, var1.getlocal(8));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _deliver$26(PyFrame var1, ThreadState var2) {
      var1.setline(361);
      PyObject var3 = imp.importOne("smtplib", var1, -1);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(362);
      PyDictionary var9 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(5, var9);
      var3 = null;

      PyObject var4;
      try {
         var1.setline(364);
         var3 = var1.getlocal(4).__getattr__("SMTP").__call__(var2);
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(365);
         var1.getlocal(6).__getattr__("connect").__call__(var2, var1.getlocal(0).__getattr__("_remoteaddr").__getitem__(Py.newInteger(0)), var1.getlocal(0).__getattr__("_remoteaddr").__getitem__(Py.newInteger(1)));
         var3 = null;

         try {
            var1.setline(367);
            var4 = var1.getlocal(6).__getattr__("sendmail").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
            var1.setlocal(5, var4);
            var4 = null;
         } catch (Throwable var7) {
            Py.addTraceback(var7, var1);
            var1.setline(369);
            var1.getlocal(6).__getattr__("quit").__call__(var2);
            throw (Throwable)var7;
         }

         var1.setline(369);
         var1.getlocal(6).__getattr__("quit").__call__(var2);
      } catch (Throwable var8) {
         PyException var10 = Py.setException(var8, var1);
         if (var10.match(var1.getlocal(4).__getattr__("SMTPRecipientsRefused"))) {
            var4 = var10.value;
            var1.setlocal(7, var4);
            var4 = null;
            var1.setline(371);
            var4 = var1.getglobal("DEBUGSTREAM");
            Py.println(var4, PyString.fromInterned("got SMTPRecipientsRefused"));
            var1.setline(372);
            var4 = var1.getlocal(7).__getattr__("recipients");
            var1.setlocal(5, var4);
            var4 = null;
         } else {
            if (!var10.match(new PyTuple(new PyObject[]{var1.getglobal("socket").__getattr__("error"), var1.getlocal(4).__getattr__("SMTPException")}))) {
               throw var10;
            }

            var4 = var10.value;
            var1.setlocal(7, var4);
            var4 = null;
            var1.setline(374);
            var4 = var1.getglobal("DEBUGSTREAM");
            Py.printComma(var4, PyString.fromInterned("got"));
            Py.println(var4, var1.getlocal(7).__getattr__("__class__"));
            var1.setline(378);
            var4 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(7), (PyObject)PyString.fromInterned("smtp_code"), (PyObject)Py.newInteger(-1));
            var1.setlocal(8, var4);
            var4 = null;
            var1.setline(379);
            var4 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(7), (PyObject)PyString.fromInterned("smtp_error"), (PyObject)PyString.fromInterned("ignore"));
            var1.setlocal(9, var4);
            var4 = null;
            var1.setline(380);
            var4 = var1.getlocal(2).__iter__();

            while(true) {
               var1.setline(380);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  break;
               }

               var1.setlocal(10, var5);
               var1.setline(381);
               PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(9)});
               var1.getlocal(5).__setitem__((PyObject)var1.getlocal(10), var6);
               var6 = null;
            }
         }
      }

      var1.setline(382);
      var3 = var1.getlocal(5);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject MailmanProxy$27(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(386);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, process_message$28, (PyObject)null);
      var1.setlocal("process_message", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject process_message$28(PyFrame var1, ThreadState var2) {
      var1.setline(387);
      String[] var3 = new String[]{"StringIO"};
      PyObject[] var7 = imp.importFrom("cStringIO", var3, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal(5, var4);
      var4 = null;
      var1.setline(388);
      var3 = new String[]{"Utils"};
      var7 = imp.importFrom("Mailman", var3, var1, -1);
      var4 = var7[0];
      var1.setlocal(6, var4);
      var4 = null;
      var1.setline(389);
      var3 = new String[]{"Message"};
      var7 = imp.importFrom("Mailman", var3, var1, -1);
      var4 = var7[0];
      var1.setlocal(7, var4);
      var4 = null;
      var1.setline(390);
      var3 = new String[]{"MailList"};
      var7 = imp.importFrom("Mailman", var3, var1, -1);
      var4 = var7[0];
      var1.setlocal(8, var4);
      var4 = null;
      var1.setline(394);
      PyList var8 = new PyList(Py.EmptyObjects);
      var1.setlocal(9, var8);
      var3 = null;
      var1.setline(395);
      PyObject var9 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(395);
         var4 = var9.__iternext__();
         PyObject var5;
         PyString var11;
         PyObject var10000;
         if (var4 == null) {
            var1.setline(419);
            var9 = var1.getlocal(9).__iter__();

            while(true) {
               var1.setline(419);
               var4 = var9.__iternext__();
               PyObject var6;
               PyObject[] var13;
               if (var4 == null) {
                  var1.setline(422);
                  var9 = var1.getglobal("DEBUGSTREAM");
                  Py.printComma(var9, PyString.fromInterned("forwarding recips:"));
                  Py.println(var9, PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(3)));
                  var1.setline(423);
                  if (var1.getlocal(3).__nonzero__()) {
                     var1.setline(424);
                     var9 = var1.getlocal(0).__getattr__("_deliver").__call__(var2, var1.getlocal(2), var1.getlocal(3), var1.getlocal(4));
                     var1.setlocal(15, var9);
                     var3 = null;
                     var1.setline(426);
                     var9 = var1.getglobal("DEBUGSTREAM");
                     Py.printComma(var9, PyString.fromInterned("we got refusals:"));
                     Py.println(var9, var1.getlocal(15));
                  }

                  var1.setline(428);
                  PyDictionary var10 = new PyDictionary(Py.EmptyObjects);
                  var1.setlocal(16, var10);
                  var3 = null;
                  var1.setline(429);
                  var9 = var1.getlocal(5).__call__(var2, var1.getlocal(4));
                  var1.setlocal(17, var9);
                  var3 = null;
                  var1.setline(430);
                  var9 = var1.getlocal(7).__getattr__("Message").__call__(var2, var1.getlocal(17));
                  var1.setlocal(18, var9);
                  var3 = null;
                  var1.setline(434);
                  if (var1.getlocal(18).__getattr__("getheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("from")).__not__().__nonzero__()) {
                     var1.setline(435);
                     var9 = var1.getlocal(2);
                     var1.getlocal(18).__setitem__((PyObject)PyString.fromInterned("From"), var9);
                     var3 = null;
                  }

                  var1.setline(436);
                  if (var1.getlocal(18).__getattr__("getheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("date")).__not__().__nonzero__()) {
                     var1.setline(437);
                     var9 = var1.getglobal("time").__getattr__("ctime").__call__(var2, var1.getglobal("time").__getattr__("time").__call__(var2));
                     var1.getlocal(18).__setitem__((PyObject)PyString.fromInterned("Date"), var9);
                     var3 = null;
                  }

                  var1.setline(438);
                  var9 = var1.getlocal(9).__iter__();

                  while(true) {
                     var1.setline(438);
                     var4 = var9.__iternext__();
                     if (var4 == null) {
                        var1.f_lasti = -1;
                        return Py.None;
                     }

                     var13 = Py.unpackSequence(var4, 3);
                     var6 = var13[0];
                     var1.setlocal(10, var6);
                     var6 = null;
                     var6 = var13[1];
                     var1.setlocal(13, var6);
                     var6 = null;
                     var6 = var13[2];
                     var1.setlocal(14, var6);
                     var6 = null;
                     var1.setline(439);
                     var5 = var1.getglobal("DEBUGSTREAM");
                     Py.printComma(var5, PyString.fromInterned("sending message to"));
                     Py.println(var5, var1.getlocal(10));
                     var1.setline(440);
                     var5 = var1.getlocal(16).__getattr__("get").__call__(var2, var1.getlocal(13));
                     var1.setlocal(19, var5);
                     var5 = null;
                     var1.setline(441);
                     String[] var12;
                     if (var1.getlocal(19).__not__().__nonzero__()) {
                        var1.setline(442);
                        var10000 = var1.getlocal(8).__getattr__("MailList");
                        var13 = new PyObject[]{var1.getlocal(13), Py.newInteger(0)};
                        var12 = new String[]{"lock"};
                        var10000 = var10000.__call__(var2, var13, var12);
                        var5 = null;
                        var5 = var10000;
                        var1.setlocal(19, var5);
                        var5 = null;
                        var1.setline(443);
                        var5 = var1.getlocal(19);
                        var1.getlocal(16).__setitem__(var1.getlocal(13), var5);
                        var5 = null;
                     }

                     var1.setline(445);
                     var5 = var1.getlocal(14);
                     var10000 = var5._eq(PyString.fromInterned(""));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(447);
                        var10000 = var1.getlocal(18).__getattr__("Enqueue");
                        var13 = new PyObject[]{var1.getlocal(19), Py.newInteger(1)};
                        var12 = new String[]{"tolist"};
                        var10000.__call__(var2, var13, var12);
                        var5 = null;
                     } else {
                        var1.setline(448);
                        var5 = var1.getlocal(14);
                        var10000 = var5._eq(PyString.fromInterned("admin"));
                        var5 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(449);
                           var10000 = var1.getlocal(18).__getattr__("Enqueue");
                           var13 = new PyObject[]{var1.getlocal(19), Py.newInteger(1)};
                           var12 = new String[]{"toadmin"};
                           var10000.__call__(var2, var13, var12);
                           var5 = null;
                        } else {
                           var1.setline(450);
                           var5 = var1.getlocal(14);
                           var10000 = var5._eq(PyString.fromInterned("owner"));
                           var5 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(451);
                              var10000 = var1.getlocal(18).__getattr__("Enqueue");
                              var13 = new PyObject[]{var1.getlocal(19), Py.newInteger(1)};
                              var12 = new String[]{"toowner"};
                              var10000.__call__(var2, var13, var12);
                              var5 = null;
                           } else {
                              var1.setline(452);
                              var5 = var1.getlocal(14);
                              var10000 = var5._eq(PyString.fromInterned("request"));
                              var5 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(453);
                                 var10000 = var1.getlocal(18).__getattr__("Enqueue");
                                 var13 = new PyObject[]{var1.getlocal(19), Py.newInteger(1)};
                                 var12 = new String[]{"torequest"};
                                 var10000.__call__(var2, var13, var12);
                                 var5 = null;
                              } else {
                                 var1.setline(454);
                                 var5 = var1.getlocal(14);
                                 var10000 = var5._in(new PyTuple(new PyObject[]{PyString.fromInterned("join"), PyString.fromInterned("leave")}));
                                 var5 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(456);
                                    var5 = var1.getlocal(14);
                                    var10000 = var5._eq(PyString.fromInterned("join"));
                                    var5 = null;
                                    if (var10000.__nonzero__()) {
                                       var1.setline(457);
                                       var11 = PyString.fromInterned("subscribe");
                                       var1.getlocal(18).__setitem__((PyObject)PyString.fromInterned("Subject"), var11);
                                       var5 = null;
                                    } else {
                                       var1.setline(459);
                                       var11 = PyString.fromInterned("unsubscribe");
                                       var1.getlocal(18).__setitem__((PyObject)PyString.fromInterned("Subject"), var11);
                                       var5 = null;
                                    }

                                    var1.setline(460);
                                    var10000 = var1.getlocal(18).__getattr__("Enqueue");
                                    var13 = new PyObject[]{var1.getlocal(19), Py.newInteger(1)};
                                    var12 = new String[]{"torequest"};
                                    var10000.__call__(var2, var13, var12);
                                    var5 = null;
                                 }
                              }
                           }
                        }
                     }
                  }
               }

               var13 = Py.unpackSequence(var4, 3);
               var6 = var13[0];
               var1.setlocal(10, var6);
               var6 = null;
               var6 = var13[1];
               var1.setlocal(13, var6);
               var6 = null;
               var6 = var13[2];
               var1.setlocal(14, var6);
               var6 = null;
               var1.setline(420);
               var1.getlocal(3).__getattr__("remove").__call__(var2, var1.getlocal(10));
            }
         }

         var1.setlocal(10, var4);
         var1.setline(396);
         var5 = var1.getlocal(10).__getattr__("lower").__call__(var2).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("@")).__getitem__(Py.newInteger(0));
         var1.setlocal(11, var5);
         var5 = null;
         var1.setline(404);
         var5 = var1.getlocal(11).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-"));
         var1.setlocal(12, var5);
         var5 = null;
         var1.setline(405);
         var5 = var1.getglobal("len").__call__(var2, var1.getlocal(12));
         var10000 = var5._gt(Py.newInteger(2));
         var5 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(407);
            var5 = var1.getlocal(12).__getitem__(Py.newInteger(0));
            var1.setlocal(13, var5);
            var5 = null;
            var1.setline(408);
            var5 = var1.getglobal("len").__call__(var2, var1.getlocal(12));
            var10000 = var5._eq(Py.newInteger(2));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(409);
               var5 = var1.getlocal(12).__getitem__(Py.newInteger(1));
               var1.setlocal(14, var5);
               var5 = null;
            } else {
               var1.setline(411);
               var11 = PyString.fromInterned("");
               var1.setlocal(14, var11);
               var5 = null;
            }

            var1.setline(412);
            var10000 = var1.getlocal(6).__getattr__("list_exists").__call__(var2, var1.getlocal(13)).__not__();
            if (!var10000.__nonzero__()) {
               var5 = var1.getlocal(14);
               var10000 = var5._notin(new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("admin"), PyString.fromInterned("owner"), PyString.fromInterned("request"), PyString.fromInterned("join"), PyString.fromInterned("leave")}));
               var5 = null;
            }

            if (!var10000.__nonzero__()) {
               var1.setline(415);
               var1.getlocal(9).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(10), var1.getlocal(13), var1.getlocal(14)})));
            }
         }
      }
   }

   public PyObject Options$29(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(464);
      PyInteger var3 = Py.newInteger(1);
      var1.setlocal("setuid", var3);
      var3 = null;
      var1.setline(465);
      PyString var4 = PyString.fromInterned("PureProxy");
      var1.setlocal("classname", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject parseargs$30(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var4;
      PyObject var5;
      PyObject var10;
      try {
         var1.setline(471);
         var10 = var1.getglobal("getopt").__getattr__("getopt").__call__((ThreadState)var2, var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), (PyObject)PyString.fromInterned("nVhc:d"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("class="), PyString.fromInterned("nosetuid"), PyString.fromInterned("version"), PyString.fromInterned("help"), PyString.fromInterned("debug")})));
         PyObject[] var11 = Py.unpackSequence(var10, 2);
         var5 = var11[0];
         var1.setlocal(0, var5);
         var5 = null;
         var5 = var11[1];
         var1.setlocal(1, var5);
         var5 = null;
         var3 = null;
      } catch (Throwable var9) {
         var3 = Py.setException(var9, var1);
         if (!var3.match(var1.getglobal("getopt").__getattr__("error"))) {
            throw var3;
         }

         var4 = var3.value;
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(475);
         var1.getglobal("usage").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)var1.getlocal(2));
      }

      var1.setline(477);
      var10 = var1.getglobal("Options").__call__(var2);
      var1.setlocal(3, var10);
      var3 = null;
      var1.setline(478);
      var10 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(478);
         var4 = var10.__iternext__();
         PyObject var10000;
         if (var4 == null) {
            var1.setline(492);
            var10 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
            var10000 = var10._lt(Py.newInteger(1));
            var3 = null;
            PyString var12;
            if (var10000.__nonzero__()) {
               var1.setline(493);
               var12 = PyString.fromInterned("localhost:8025");
               var1.setlocal(6, var12);
               var3 = null;
               var1.setline(494);
               var12 = PyString.fromInterned("localhost:25");
               var1.setlocal(7, var12);
               var3 = null;
            } else {
               var1.setline(495);
               var10 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
               var10000 = var10._lt(Py.newInteger(2));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(496);
                  var10 = var1.getlocal(1).__getitem__(Py.newInteger(0));
                  var1.setlocal(6, var10);
                  var3 = null;
                  var1.setline(497);
                  var12 = PyString.fromInterned("localhost:25");
                  var1.setlocal(7, var12);
                  var3 = null;
               } else {
                  var1.setline(498);
                  var10 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
                  var10000 = var10._lt(Py.newInteger(3));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(499);
                     var10 = var1.getlocal(1).__getitem__(Py.newInteger(0));
                     var1.setlocal(6, var10);
                     var3 = null;
                     var1.setline(500);
                     var10 = var1.getlocal(1).__getitem__(Py.newInteger(1));
                     var1.setlocal(7, var10);
                     var3 = null;
                  } else {
                     var1.setline(502);
                     var1.getglobal("usage").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)PyString.fromInterned("Invalid arguments: %s")._mod(var1.getglobal("COMMASPACE").__getattr__("join").__call__(var2, var1.getlocal(1))));
                  }
               }
            }

            var1.setline(505);
            var10 = var1.getlocal(6).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
            var1.setlocal(8, var10);
            var3 = null;
            var1.setline(506);
            var10 = var1.getlocal(8);
            var10000 = var10._lt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(507);
               var1.getglobal("usage").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)PyString.fromInterned("Bad local spec: %s")._mod(var1.getlocal(6)));
            }

            var1.setline(508);
            var10 = var1.getlocal(6).__getslice__((PyObject)null, var1.getlocal(8), (PyObject)null);
            var1.getlocal(3).__setattr__("localhost", var10);
            var3 = null;

            try {
               var1.setline(510);
               var10 = var1.getglobal("int").__call__(var2, var1.getlocal(6).__getslice__(var1.getlocal(8)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null));
               var1.getlocal(3).__setattr__("localport", var10);
               var3 = null;
            } catch (Throwable var8) {
               var3 = Py.setException(var8, var1);
               if (!var3.match(var1.getglobal("ValueError"))) {
                  throw var3;
               }

               var1.setline(512);
               var1.getglobal("usage").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)PyString.fromInterned("Bad local port: %s")._mod(var1.getlocal(6)));
            }

            var1.setline(513);
            var10 = var1.getlocal(7).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
            var1.setlocal(8, var10);
            var3 = null;
            var1.setline(514);
            var10 = var1.getlocal(8);
            var10000 = var10._lt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(515);
               var1.getglobal("usage").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)PyString.fromInterned("Bad remote spec: %s")._mod(var1.getlocal(7)));
            }

            var1.setline(516);
            var10 = var1.getlocal(7).__getslice__((PyObject)null, var1.getlocal(8), (PyObject)null);
            var1.getlocal(3).__setattr__("remotehost", var10);
            var3 = null;

            try {
               var1.setline(518);
               var10 = var1.getglobal("int").__call__(var2, var1.getlocal(7).__getslice__(var1.getlocal(8)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null));
               var1.getlocal(3).__setattr__("remoteport", var10);
               var3 = null;
            } catch (Throwable var7) {
               var3 = Py.setException(var7, var1);
               if (!var3.match(var1.getglobal("ValueError"))) {
                  throw var3;
               }

               var1.setline(520);
               var1.getglobal("usage").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)PyString.fromInterned("Bad remote port: %s")._mod(var1.getlocal(7)));
            }

            var1.setline(521);
            var10 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var10;
         }

         PyObject[] var13 = Py.unpackSequence(var4, 2);
         PyObject var6 = var13[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var13[1];
         var1.setlocal(5, var6);
         var6 = null;
         var1.setline(479);
         var5 = var1.getlocal(4);
         var10000 = var5._in(new PyTuple(new PyObject[]{PyString.fromInterned("-h"), PyString.fromInterned("--help")}));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(480);
            var1.getglobal("usage").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         } else {
            var1.setline(481);
            var5 = var1.getlocal(4);
            var10000 = var5._in(new PyTuple(new PyObject[]{PyString.fromInterned("-V"), PyString.fromInterned("--version")}));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(482);
               var5 = var1.getglobal("sys").__getattr__("stderr");
               Py.println(var5, var1.getglobal("__version__"));
               var1.setline(483);
               var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            } else {
               var1.setline(484);
               var5 = var1.getlocal(4);
               var10000 = var5._in(new PyTuple(new PyObject[]{PyString.fromInterned("-n"), PyString.fromInterned("--nosetuid")}));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(485);
                  PyInteger var14 = Py.newInteger(0);
                  var1.getlocal(3).__setattr__((String)"setuid", var14);
                  var5 = null;
               } else {
                  var1.setline(486);
                  var5 = var1.getlocal(4);
                  var10000 = var5._in(new PyTuple(new PyObject[]{PyString.fromInterned("-c"), PyString.fromInterned("--class")}));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(487);
                     var5 = var1.getlocal(5);
                     var1.getlocal(3).__setattr__("classname", var5);
                     var5 = null;
                  } else {
                     var1.setline(488);
                     var5 = var1.getlocal(4);
                     var10000 = var5._in(new PyTuple(new PyObject[]{PyString.fromInterned("-d"), PyString.fromInterned("--debug")}));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(489);
                        var5 = var1.getglobal("sys").__getattr__("stderr");
                        var1.setglobal("DEBUGSTREAM", var5);
                        var5 = null;
                     }
                  }
               }
            }
         }
      }
   }

   public smtpd$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Devnull$1 = Py.newCode(0, var2, var1, "Devnull", 87, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "msg"};
      write$2 = Py.newCode(2, var2, var1, "write", 88, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      flush$3 = Py.newCode(1, var2, var1, "flush", 89, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"code", "msg"};
      usage$4 = Py.newCode(2, var2, var1, "usage", 98, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SMTPChannel$5 = Py.newCode(0, var2, var1, "SMTPChannel", 105, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "server", "conn", "addr", "err"};
      __init__$6 = Py.newCode(4, var2, var1, "__init__", 109, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      push$7 = Py.newCode(2, var2, var1, "push", 135, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      collect_incoming_data$8 = Py.newCode(2, var2, var1, "collect_incoming_data", 139, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line", "method", "i", "command", "arg", "data", "text", "status"};
      found_terminator$9 = Py.newCode(1, var2, var1, "found_terminator", 143, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      smtp_HELO$10 = Py.newCode(2, var2, var1, "smtp_HELO", 192, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      smtp_NOOP$11 = Py.newCode(2, var2, var1, "smtp_NOOP", 202, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      smtp_QUIT$12 = Py.newCode(2, var2, var1, "smtp_QUIT", 208, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "keyword", "arg", "address", "keylen"};
      _SMTPChannel__getaddr$13 = Py.newCode(3, var2, var1, "_SMTPChannel__getaddr", 214, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg", "address"};
      smtp_MAIL$14 = Py.newCode(2, var2, var1, "smtp_MAIL", 227, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg", "address"};
      smtp_RCPT$15 = Py.newCode(2, var2, var1, "smtp_RCPT", 240, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      smtp_RSET$16 = Py.newCode(2, var2, var1, "smtp_RSET", 253, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      smtp_DATA$17 = Py.newCode(2, var2, var1, "smtp_DATA", 264, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SMTPServer$18 = Py.newCode(0, var2, var1, "SMTPServer", 276, false, false, self, 18, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "localaddr", "remoteaddr"};
      __init__$19 = Py.newCode(3, var2, var1, "__init__", 277, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pair", "conn", "addr", "channel"};
      handle_accept$20 = Py.newCode(1, var2, var1, "handle_accept", 297, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "peer", "mailfrom", "rcpttos", "data"};
      process_message$21 = Py.newCode(5, var2, var1, "process_message", 305, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DebuggingServer$22 = Py.newCode(0, var2, var1, "DebuggingServer", 330, false, false, self, 22, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "peer", "mailfrom", "rcpttos", "data", "inheaders", "lines", "line"};
      process_message$23 = Py.newCode(5, var2, var1, "process_message", 332, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      PureProxy$24 = Py.newCode(0, var2, var1, "PureProxy", 345, false, false, self, 24, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "peer", "mailfrom", "rcpttos", "data", "lines", "i", "line", "refused"};
      process_message$25 = Py.newCode(5, var2, var1, "process_message", 346, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "mailfrom", "rcpttos", "data", "smtplib", "refused", "s", "e", "errcode", "errmsg", "r"};
      _deliver$26 = Py.newCode(4, var2, var1, "_deliver", 360, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MailmanProxy$27 = Py.newCode(0, var2, var1, "MailmanProxy", 385, false, false, self, 27, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "peer", "mailfrom", "rcpttos", "data", "StringIO", "Utils", "Message", "MailList", "listnames", "rcpt", "local", "parts", "listname", "command", "refused", "mlists", "s", "msg", "mlist"};
      process_message$28 = Py.newCode(5, var2, var1, "process_message", 386, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Options$29 = Py.newCode(0, var2, var1, "Options", 463, false, false, self, 29, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"opts", "args", "e", "options", "opt", "arg", "localspec", "remotespec", "i"};
      parseargs$30 = Py.newCode(0, var2, var1, "parseargs", 468, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new smtpd$py("smtpd$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(smtpd$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Devnull$1(var2, var3);
         case 2:
            return this.write$2(var2, var3);
         case 3:
            return this.flush$3(var2, var3);
         case 4:
            return this.usage$4(var2, var3);
         case 5:
            return this.SMTPChannel$5(var2, var3);
         case 6:
            return this.__init__$6(var2, var3);
         case 7:
            return this.push$7(var2, var3);
         case 8:
            return this.collect_incoming_data$8(var2, var3);
         case 9:
            return this.found_terminator$9(var2, var3);
         case 10:
            return this.smtp_HELO$10(var2, var3);
         case 11:
            return this.smtp_NOOP$11(var2, var3);
         case 12:
            return this.smtp_QUIT$12(var2, var3);
         case 13:
            return this._SMTPChannel__getaddr$13(var2, var3);
         case 14:
            return this.smtp_MAIL$14(var2, var3);
         case 15:
            return this.smtp_RCPT$15(var2, var3);
         case 16:
            return this.smtp_RSET$16(var2, var3);
         case 17:
            return this.smtp_DATA$17(var2, var3);
         case 18:
            return this.SMTPServer$18(var2, var3);
         case 19:
            return this.__init__$19(var2, var3);
         case 20:
            return this.handle_accept$20(var2, var3);
         case 21:
            return this.process_message$21(var2, var3);
         case 22:
            return this.DebuggingServer$22(var2, var3);
         case 23:
            return this.process_message$23(var2, var3);
         case 24:
            return this.PureProxy$24(var2, var3);
         case 25:
            return this.process_message$25(var2, var3);
         case 26:
            return this._deliver$26(var2, var3);
         case 27:
            return this.MailmanProxy$27(var2, var3);
         case 28:
            return this.process_message$28(var2, var3);
         case 29:
            return this.Options$29(var2, var3);
         case 30:
            return this.parseargs$30(var2, var3);
         default:
            return null;
      }
   }
}
