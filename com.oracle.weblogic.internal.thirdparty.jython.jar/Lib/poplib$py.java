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
@Filename("poplib.py")
public class poplib$py extends PyFunctionTable implements PyRunnable {
   static poplib$py self;
   static final PyCode f$0;
   static final PyCode error_proto$1;
   static final PyCode POP3$2;
   static final PyCode __init__$3;
   static final PyCode _putline$4;
   static final PyCode _putcmd$5;
   static final PyCode _getline$6;
   static final PyCode _getresp$7;
   static final PyCode _getlongresp$8;
   static final PyCode _shortcmd$9;
   static final PyCode _longcmd$10;
   static final PyCode getwelcome$11;
   static final PyCode set_debuglevel$12;
   static final PyCode user$13;
   static final PyCode pass_$14;
   static final PyCode stat$15;
   static final PyCode list$16;
   static final PyCode retr$17;
   static final PyCode dele$18;
   static final PyCode noop$19;
   static final PyCode rset$20;
   static final PyCode quit$21;
   static final PyCode rpop$22;
   static final PyCode apop$23;
   static final PyCode f$24;
   static final PyCode top$25;
   static final PyCode uidl$26;
   static final PyCode POP3_SSL$27;
   static final PyCode __init__$28;
   static final PyCode _fillBuffer$29;
   static final PyCode _getline$30;
   static final PyCode _putline$31;
   static final PyCode quit$32;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("A POP3 client class.\n\nBased on the J. Myers POP3 draft, Jan. 96\n"));
      var1.setline(4);
      PyString.fromInterned("A POP3 client class.\n\nBased on the J. Myers POP3 draft, Jan. 96\n");
      var1.setline(16);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var3 = imp.importOne("socket", var1, -1);
      var1.setlocal("socket", var3);
      var3 = null;
      var1.setline(18);
      PyList var9 = new PyList(new PyObject[]{PyString.fromInterned("POP3"), PyString.fromInterned("error_proto")});
      var1.setlocal("__all__", var9);
      var3 = null;
      var1.setline(22);
      PyObject[] var10 = new PyObject[]{var1.getname("Exception")};
      PyObject var4 = Py.makeClass("error_proto", var10, error_proto$1);
      var1.setlocal("error_proto", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(25);
      PyInteger var11 = Py.newInteger(110);
      var1.setlocal("POP3_PORT", var11);
      var3 = null;
      var1.setline(28);
      var11 = Py.newInteger(995);
      var1.setlocal("POP3_SSL_PORT", var11);
      var3 = null;
      var1.setline(31);
      PyString var14 = PyString.fromInterned("\r");
      var1.setlocal("CR", var14);
      var3 = null;
      var1.setline(32);
      var14 = PyString.fromInterned("\n");
      var1.setlocal("LF", var14);
      var3 = null;
      var1.setline(33);
      var3 = var1.getname("CR")._add(var1.getname("LF"));
      var1.setlocal("CRLF", var3);
      var3 = null;
      var1.setline(39);
      var11 = Py.newInteger(2048);
      var1.setlocal("_MAXLINE", var11);
      var3 = null;
      var1.setline(42);
      var10 = Py.EmptyObjects;
      var4 = Py.makeClass("POP3", var10, POP3$2);
      var1.setlocal("POP3", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);

      PyObject var5;
      PyObject[] var12;
      label41: {
         try {
            var1.setline(320);
            var3 = imp.importOne("ssl", var1, -1);
            var1.setlocal("ssl", var3);
            var3 = null;
         } catch (Throwable var8) {
            PyException var15 = Py.setException(var8, var1);
            if (var15.match(var1.getname("ImportError"))) {
               var1.setline(322);
               break label41;
            }

            throw var15;
         }

         var1.setline(325);
         var12 = new PyObject[]{var1.getname("POP3")};
         var5 = Py.makeClass("POP3_SSL", var12, POP3_SSL$27);
         var1.setlocal("POP3_SSL", var5);
         var5 = null;
         Arrays.fill(var12, (Object)null);
         var1.setline(411);
         var1.getname("__all__").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP3_SSL"));
      }

      var1.setline(413);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(414);
         var3 = imp.importOne("sys", var1, -1);
         var1.setlocal("sys", var3);
         var3 = null;
         var1.setline(415);
         var3 = var1.getname("POP3").__call__(var2, var1.getname("sys").__getattr__("argv").__getitem__(Py.newInteger(1)));
         var1.setlocal("a", var3);
         var3 = null;
         var1.setline(416);
         Py.println(var1.getname("a").__getattr__("getwelcome").__call__(var2));
         var1.setline(417);
         var1.getname("a").__getattr__("user").__call__(var2, var1.getname("sys").__getattr__("argv").__getitem__(Py.newInteger(2)));
         var1.setline(418);
         var1.getname("a").__getattr__("pass_").__call__(var2, var1.getname("sys").__getattr__("argv").__getitem__(Py.newInteger(3)));
         var1.setline(419);
         var1.getname("a").__getattr__("list").__call__(var2);
         var1.setline(420);
         var3 = var1.getname("a").__getattr__("stat").__call__(var2);
         var12 = Py.unpackSequence(var3, 2);
         var5 = var12[0];
         var1.setlocal("numMsgs", var5);
         var5 = null;
         var5 = var12[1];
         var1.setlocal("totalSize", var5);
         var5 = null;
         var3 = null;
         var1.setline(421);
         var3 = var1.getname("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)var1.getname("numMsgs")._add(Py.newInteger(1))).__iter__();

         while(true) {
            var1.setline(421);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(427);
               var1.getname("a").__getattr__("quit").__call__(var2);
               break;
            }

            var1.setlocal("i", var4);
            var1.setline(422);
            var5 = var1.getname("a").__getattr__("retr").__call__(var2, var1.getname("i"));
            PyObject[] var6 = Py.unpackSequence(var5, 3);
            PyObject var7 = var6[0];
            var1.setlocal("header", var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal("msg", var7);
            var7 = null;
            var7 = var6[2];
            var1.setlocal("octets", var7);
            var7 = null;
            var5 = null;
            var1.setline(423);
            Py.println(PyString.fromInterned("Message %d:")._mod(var1.getname("i")));
            var1.setline(424);
            var5 = var1.getname("msg").__iter__();

            while(true) {
               var1.setline(424);
               PyObject var13 = var5.__iternext__();
               if (var13 == null) {
                  var1.setline(426);
                  Py.println(PyString.fromInterned("-----------------------"));
                  break;
               }

               var1.setlocal("line", var13);
               var1.setline(425);
               Py.println(PyString.fromInterned("   ")._add(var1.getname("line")));
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject error_proto$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(22);
      return var1.getf_locals();
   }

   public PyObject POP3$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("This class supports both the minimal and optional command sets.\n    Arguments can be strings or integers (where appropriate)\n    (e.g.: retr(1) and retr('1') both work equally well.\n\n    Minimal Command Set:\n            USER name               user(name)\n            PASS string             pass_(string)\n            STAT                    stat()\n            LIST [msg]              list(msg = None)\n            RETR msg                retr(msg)\n            DELE msg                dele(msg)\n            NOOP                    noop()\n            RSET                    rset()\n            QUIT                    quit()\n\n    Optional Commands (some servers support these):\n            RPOP name               rpop(name)\n            APOP name digest        apop(name, digest)\n            TOP msg n               top(msg, n)\n            UIDL [msg]              uidl(msg = None)\n\n    Raises one exception: 'error_proto'.\n\n    Instantiate with:\n            POP3(hostname, port=110)\n\n    NB:     the POP protocol locks the mailbox from user\n            authorization until QUIT, so be sure to get in, suck\n            the messages, and quit, each time you access the\n            mailbox.\n\n            POP is a line-based protocol, which means large mail\n            messages consume lots of python cycles reading them\n            line-by-line.\n\n            If it's available on your mail server, use IMAP4\n            instead, it doesn't suffer from the two problems\n            above.\n    "));
      var1.setline(82);
      PyString.fromInterned("This class supports both the minimal and optional command sets.\n    Arguments can be strings or integers (where appropriate)\n    (e.g.: retr(1) and retr('1') both work equally well.\n\n    Minimal Command Set:\n            USER name               user(name)\n            PASS string             pass_(string)\n            STAT                    stat()\n            LIST [msg]              list(msg = None)\n            RETR msg                retr(msg)\n            DELE msg                dele(msg)\n            NOOP                    noop()\n            RSET                    rset()\n            QUIT                    quit()\n\n    Optional Commands (some servers support these):\n            RPOP name               rpop(name)\n            APOP name digest        apop(name, digest)\n            TOP msg n               top(msg, n)\n            UIDL [msg]              uidl(msg = None)\n\n    Raises one exception: 'error_proto'.\n\n    Instantiate with:\n            POP3(hostname, port=110)\n\n    NB:     the POP protocol locks the mailbox from user\n            authorization until QUIT, so be sure to get in, suck\n            the messages, and quit, each time you access the\n            mailbox.\n\n            POP is a line-based protocol, which means large mail\n            messages consume lots of python cycles reading them\n            line-by-line.\n\n            If it's available on your mail server, use IMAP4\n            instead, it doesn't suffer from the two problems\n            above.\n    ");
      var1.setline(85);
      PyObject[] var3 = new PyObject[]{var1.getname("POP3_PORT"), var1.getname("socket").__getattr__("_GLOBAL_DEFAULT_TIMEOUT")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$3, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(95);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _putline$4, (PyObject)null);
      var1.setlocal("_putline", var4);
      var3 = null;
      var1.setline(102);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _putcmd$5, (PyObject)null);
      var1.setlocal("_putcmd", var4);
      var3 = null;
      var1.setline(111);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _getline$6, (PyObject)null);
      var1.setlocal("_getline", var4);
      var3 = null;
      var1.setline(131);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _getresp$7, (PyObject)null);
      var1.setlocal("_getresp", var4);
      var3 = null;
      var1.setline(142);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _getlongresp$8, (PyObject)null);
      var1.setlocal("_getlongresp", var4);
      var3 = null;
      var1.setline(158);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _shortcmd$9, (PyObject)null);
      var1.setlocal("_shortcmd", var4);
      var3 = null;
      var1.setline(165);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _longcmd$10, (PyObject)null);
      var1.setlocal("_longcmd", var4);
      var3 = null;
      var1.setline(172);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getwelcome$11, (PyObject)null);
      var1.setlocal("getwelcome", var4);
      var3 = null;
      var1.setline(176);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_debuglevel$12, (PyObject)null);
      var1.setlocal("set_debuglevel", var4);
      var3 = null;
      var1.setline(182);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, user$13, PyString.fromInterned("Send user name, return response\n\n        (should indicate password required).\n        "));
      var1.setlocal("user", var4);
      var3 = null;
      var1.setline(190);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pass_$14, PyString.fromInterned("Send password, return response\n\n        (response includes message count, mailbox size).\n\n        NB: mailbox is locked by server from here to 'quit()'\n        "));
      var1.setlocal("pass_", var4);
      var3 = null;
      var1.setline(200);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, stat$15, PyString.fromInterned("Get mailbox status.\n\n        Result is tuple of 2 ints (message count, mailbox size)\n        "));
      var1.setlocal("stat", var4);
      var3 = null;
      var1.setline(213);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, list$16, PyString.fromInterned("Request listing, return result.\n\n        Result without a message number argument is in form\n        ['response', ['mesg_num octets', ...], octets].\n\n        Result when a message number argument is given is a\n        single response: the \"scan listing\" for that message.\n        "));
      var1.setlocal("list", var4);
      var3 = null;
      var1.setline(227);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, retr$17, PyString.fromInterned("Retrieve whole message number 'which'.\n\n        Result is in form ['response', ['line', ...], octets].\n        "));
      var1.setlocal("retr", var4);
      var3 = null;
      var1.setline(235);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, dele$18, PyString.fromInterned("Delete message number 'which'.\n\n        Result is 'response'.\n        "));
      var1.setlocal("dele", var4);
      var3 = null;
      var1.setline(243);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, noop$19, PyString.fromInterned("Does nothing.\n\n        One supposes the response indicates the server is alive.\n        "));
      var1.setlocal("noop", var4);
      var3 = null;
      var1.setline(251);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, rset$20, PyString.fromInterned("Unmark all messages marked for deletion."));
      var1.setlocal("rset", var4);
      var3 = null;
      var1.setline(256);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, quit$21, PyString.fromInterned("Signoff: commit changes on server, unlock mailbox, close connection."));
      var1.setlocal("quit", var4);
      var3 = null;
      var1.setline(272);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, rpop$22, PyString.fromInterned("Not sure what this does."));
      var1.setlocal("rpop", var4);
      var3 = null;
      var1.setline(277);
      PyObject var5 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\+OK.*(<[^>]+>)"));
      var1.setlocal("timestamp", var5);
      var3 = null;
      var1.setline(279);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, apop$23, PyString.fromInterned("Authorisation\n\n        - only possible if server has supplied a timestamp in initial greeting.\n\n        Args:\n                user    - mailbox user;\n                secret  - secret shared between client and server.\n\n        NB: mailbox is locked by server from here to 'quit()'\n        "));
      var1.setlocal("apop", var4);
      var3 = null;
      var1.setline(299);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, top$25, PyString.fromInterned("Retrieve message header of message number 'which'\n        and first 'howmuch' lines of message body.\n\n        Result is in form ['response', ['line', ...], octets].\n        "));
      var1.setlocal("top", var4);
      var3 = null;
      var1.setline(308);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, uidl$26, PyString.fromInterned("Return message digest (unique id) list.\n\n        If 'which', result contains unique id for that message\n        in the form 'response mesgnum uid', otherwise result is\n        the list ['response', ['mesgnum uid', ...], octets]\n        "));
      var1.setlocal("uidl", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(87);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("host", var3);
      var3 = null;
      var1.setline(88);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("port", var3);
      var3 = null;
      var1.setline(89);
      var3 = var1.getglobal("socket").__getattr__("create_connection").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})), (PyObject)var1.getlocal(3));
      var1.getlocal(0).__setattr__("sock", var3);
      var3 = null;
      var1.setline(90);
      var3 = var1.getlocal(0).__getattr__("sock").__getattr__("makefile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("rb"));
      var1.getlocal(0).__setattr__("file", var3);
      var3 = null;
      var1.setline(91);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_debugging", var4);
      var3 = null;
      var1.setline(92);
      var3 = var1.getlocal(0).__getattr__("_getresp").__call__(var2);
      var1.getlocal(0).__setattr__("welcome", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _putline$4(PyFrame var1, ThreadState var2) {
      var1.setline(96);
      PyObject var3 = var1.getlocal(0).__getattr__("_debugging");
      PyObject var10000 = var3._gt(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(96);
         Py.printComma(PyString.fromInterned("*put*"));
         Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(1)));
      }

      var1.setline(97);
      var1.getlocal(0).__getattr__("sock").__getattr__("sendall").__call__(var2, PyString.fromInterned("%s%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("CRLF")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _putcmd$5(PyFrame var1, ThreadState var2) {
      var1.setline(103);
      if (var1.getlocal(0).__getattr__("_debugging").__nonzero__()) {
         var1.setline(103);
         Py.printComma(PyString.fromInterned("*cmd*"));
         Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(1)));
      }

      var1.setline(104);
      var1.getlocal(0).__getattr__("_putline").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _getline$6(PyFrame var1, ThreadState var2) {
      var1.setline(112);
      PyObject var3 = var1.getlocal(0).__getattr__("file").__getattr__("readline").__call__(var2, var1.getglobal("_MAXLINE")._add(Py.newInteger(1)));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(113);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._gt(var1.getglobal("_MAXLINE"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(114);
         throw Py.makeException(var1.getglobal("error_proto").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("line too long")));
      } else {
         var1.setline(115);
         var3 = var1.getlocal(0).__getattr__("_debugging");
         var10000 = var3._gt(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(115);
            Py.printComma(PyString.fromInterned("*get*"));
            Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(1)));
         }

         var1.setline(116);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(116);
            throw Py.makeException(var1.getglobal("error_proto").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-ERR EOF")));
         } else {
            var1.setline(117);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(121);
            var3 = var1.getlocal(1).__getslice__(Py.newInteger(-2), (PyObject)null, (PyObject)null);
            var10000 = var3._eq(var1.getglobal("CRLF"));
            var3 = null;
            PyTuple var5;
            if (var10000.__nonzero__()) {
               var1.setline(122);
               var5 = new PyTuple(new PyObject[]{var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-2), (PyObject)null), var1.getlocal(2)});
               var1.f_lasti = -1;
               return var5;
            } else {
               var1.setline(123);
               PyObject var4 = var1.getlocal(1).__getitem__(Py.newInteger(0));
               var10000 = var4._eq(var1.getglobal("CR"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(124);
                  var5 = new PyTuple(new PyObject[]{var1.getlocal(1).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null), var1.getlocal(2)});
                  var1.f_lasti = -1;
                  return var5;
               } else {
                  var1.setline(125);
                  var5 = new PyTuple(new PyObject[]{var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null), var1.getlocal(2)});
                  var1.f_lasti = -1;
                  return var5;
               }
            }
         }
      }
   }

   public PyObject _getresp$7(PyFrame var1, ThreadState var2) {
      var1.setline(132);
      PyObject var3 = var1.getlocal(0).__getattr__("_getline").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(133);
      var3 = var1.getlocal(0).__getattr__("_debugging");
      PyObject var10000 = var3._gt(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(133);
         Py.printComma(PyString.fromInterned("*resp*"));
         Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(1)));
      }

      var1.setline(134);
      var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(135);
      var3 = var1.getlocal(3);
      var10000 = var3._ne(PyString.fromInterned("+"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(136);
         throw Py.makeException(var1.getglobal("error_proto").__call__(var2, var1.getlocal(1)));
      } else {
         var1.setline(137);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _getlongresp$8(PyFrame var1, ThreadState var2) {
      var1.setline(143);
      PyObject var3 = var1.getlocal(0).__getattr__("_getresp").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(144);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(144);
      PyInteger var7 = Py.newInteger(0);
      var1.setlocal(3, var7);
      var3 = null;
      var1.setline(145);
      var3 = var1.getlocal(0).__getattr__("_getline").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;

      while(true) {
         var1.setline(146);
         var3 = var1.getlocal(4);
         PyObject var10000 = var3._ne(PyString.fromInterned("."));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(153);
            PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)});
            var1.f_lasti = -1;
            return var8;
         }

         var1.setline(147);
         var3 = var1.getlocal(4).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
         var10000 = var3._eq(PyString.fromInterned(".."));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(148);
            var3 = var1.getlocal(5)._sub(Py.newInteger(1));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(149);
            var3 = var1.getlocal(4).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
            var1.setlocal(4, var3);
            var3 = null;
         }

         var1.setline(150);
         var3 = var1.getlocal(3)._add(var1.getlocal(5));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(151);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(4));
         var1.setline(152);
         var3 = var1.getlocal(0).__getattr__("_getline").__call__(var2);
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(5, var5);
         var5 = null;
         var3 = null;
      }
   }

   public PyObject _shortcmd$9(PyFrame var1, ThreadState var2) {
      var1.setline(159);
      var1.getlocal(0).__getattr__("_putcmd").__call__(var2, var1.getlocal(1));
      var1.setline(160);
      PyObject var3 = var1.getlocal(0).__getattr__("_getresp").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _longcmd$10(PyFrame var1, ThreadState var2) {
      var1.setline(166);
      var1.getlocal(0).__getattr__("_putcmd").__call__(var2, var1.getlocal(1));
      var1.setline(167);
      PyObject var3 = var1.getlocal(0).__getattr__("_getlongresp").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getwelcome$11(PyFrame var1, ThreadState var2) {
      var1.setline(173);
      PyObject var3 = var1.getlocal(0).__getattr__("welcome");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_debuglevel$12(PyFrame var1, ThreadState var2) {
      var1.setline(177);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_debugging", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject user$13(PyFrame var1, ThreadState var2) {
      var1.setline(186);
      PyString.fromInterned("Send user name, return response\n\n        (should indicate password required).\n        ");
      var1.setline(187);
      PyObject var3 = var1.getlocal(0).__getattr__("_shortcmd").__call__(var2, PyString.fromInterned("USER %s")._mod(var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject pass_$14(PyFrame var1, ThreadState var2) {
      var1.setline(196);
      PyString.fromInterned("Send password, return response\n\n        (response includes message count, mailbox size).\n\n        NB: mailbox is locked by server from here to 'quit()'\n        ");
      var1.setline(197);
      PyObject var3 = var1.getlocal(0).__getattr__("_shortcmd").__call__(var2, PyString.fromInterned("PASS %s")._mod(var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject stat$15(PyFrame var1, ThreadState var2) {
      var1.setline(204);
      PyString.fromInterned("Get mailbox status.\n\n        Result is tuple of 2 ints (message count, mailbox size)\n        ");
      var1.setline(205);
      PyObject var3 = var1.getlocal(0).__getattr__("_shortcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("STAT"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(206);
      var3 = var1.getlocal(1).__getattr__("split").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(207);
      if (var1.getlocal(0).__getattr__("_debugging").__nonzero__()) {
         var1.setline(207);
         Py.printComma(PyString.fromInterned("*stat*"));
         Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(2)));
      }

      var1.setline(208);
      var3 = var1.getglobal("int").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(1)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(209);
      var3 = var1.getglobal("int").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(2)));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(210);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject list$16(PyFrame var1, ThreadState var2) {
      var1.setline(221);
      PyString.fromInterned("Request listing, return result.\n\n        Result without a message number argument is in form\n        ['response', ['mesg_num octets', ...], octets].\n\n        Result when a message number argument is given is a\n        single response: the \"scan listing\" for that message.\n        ");
      var1.setline(222);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(223);
         var3 = var1.getlocal(0).__getattr__("_shortcmd").__call__(var2, PyString.fromInterned("LIST %s")._mod(var1.getlocal(1)));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(224);
         var3 = var1.getlocal(0).__getattr__("_longcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LIST"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject retr$17(PyFrame var1, ThreadState var2) {
      var1.setline(231);
      PyString.fromInterned("Retrieve whole message number 'which'.\n\n        Result is in form ['response', ['line', ...], octets].\n        ");
      var1.setline(232);
      PyObject var3 = var1.getlocal(0).__getattr__("_longcmd").__call__(var2, PyString.fromInterned("RETR %s")._mod(var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject dele$18(PyFrame var1, ThreadState var2) {
      var1.setline(239);
      PyString.fromInterned("Delete message number 'which'.\n\n        Result is 'response'.\n        ");
      var1.setline(240);
      PyObject var3 = var1.getlocal(0).__getattr__("_shortcmd").__call__(var2, PyString.fromInterned("DELE %s")._mod(var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject noop$19(PyFrame var1, ThreadState var2) {
      var1.setline(247);
      PyString.fromInterned("Does nothing.\n\n        One supposes the response indicates the server is alive.\n        ");
      var1.setline(248);
      PyObject var3 = var1.getlocal(0).__getattr__("_shortcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("NOOP"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject rset$20(PyFrame var1, ThreadState var2) {
      var1.setline(252);
      PyString.fromInterned("Unmark all messages marked for deletion.");
      var1.setline(253);
      PyObject var3 = var1.getlocal(0).__getattr__("_shortcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("RSET"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject quit$21(PyFrame var1, ThreadState var2) {
      var1.setline(257);
      PyString.fromInterned("Signoff: commit changes on server, unlock mailbox, close connection.");

      PyException var3;
      PyObject var6;
      try {
         var1.setline(259);
         var6 = var1.getlocal(0).__getattr__("_shortcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("QUIT"));
         var1.setlocal(1, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (!var3.match(var1.getglobal("error_proto"))) {
            throw var3;
         }

         PyObject var4 = var3.value;
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(261);
         var4 = var1.getlocal(2);
         var1.setlocal(1, var4);
         var4 = null;
      }

      var1.setline(262);
      var1.getlocal(0).__getattr__("file").__getattr__("close").__call__(var2);
      var1.setline(263);
      var1.getlocal(0).__getattr__("sock").__getattr__("close").__call__(var2);
      var1.setline(264);
      var1.getlocal(0).__delattr__("file");
      var1.getlocal(0).__delattr__("sock");
      var1.setline(265);
      var6 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject rpop$22(PyFrame var1, ThreadState var2) {
      var1.setline(273);
      PyString.fromInterned("Not sure what this does.");
      var1.setline(274);
      PyObject var3 = var1.getlocal(0).__getattr__("_shortcmd").__call__(var2, PyString.fromInterned("RPOP %s")._mod(var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject apop$23(PyFrame var1, ThreadState var2) {
      var1.setline(289);
      PyString.fromInterned("Authorisation\n\n        - only possible if server has supplied a timestamp in initial greeting.\n\n        Args:\n                user    - mailbox user;\n                secret  - secret shared between client and server.\n\n        NB: mailbox is locked by server from here to 'quit()'\n        ");
      var1.setline(290);
      PyObject var3 = var1.getlocal(0).__getattr__("timestamp").__getattr__("match").__call__(var2, var1.getlocal(0).__getattr__("welcome"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(291);
      if (var1.getlocal(3).__not__().__nonzero__()) {
         var1.setline(292);
         throw Py.makeException(var1.getglobal("error_proto").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-ERR APOP not supported by server")));
      } else {
         var1.setline(293);
         var3 = imp.importOne("hashlib", var1, -1);
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(294);
         var3 = var1.getlocal(4).__getattr__("md5").__call__(var2, var1.getlocal(3).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1))._add(var1.getlocal(2))).__getattr__("digest").__call__(var2);
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(295);
         PyObject var10000 = PyString.fromInterned("").__getattr__("join");
         PyObject var10002 = var1.getglobal("map");
         var1.setline(295);
         PyObject[] var4 = Py.EmptyObjects;
         var3 = var10000.__call__(var2, var10002.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var4, f$24)), (PyObject)var1.getlocal(5)));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(296);
         var3 = var1.getlocal(0).__getattr__("_shortcmd").__call__(var2, PyString.fromInterned("APOP %s %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(5)})));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject f$24(PyFrame var1, ThreadState var2) {
      var1.setline(295);
      PyObject var3 = PyString.fromInterned("%02x")._mod(var1.getglobal("ord").__call__(var2, var1.getlocal(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject top$25(PyFrame var1, ThreadState var2) {
      var1.setline(304);
      PyString.fromInterned("Retrieve message header of message number 'which'\n        and first 'howmuch' lines of message body.\n\n        Result is in form ['response', ['line', ...], octets].\n        ");
      var1.setline(305);
      PyObject var3 = var1.getlocal(0).__getattr__("_longcmd").__call__(var2, PyString.fromInterned("TOP %s %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject uidl$26(PyFrame var1, ThreadState var2) {
      var1.setline(314);
      PyString.fromInterned("Return message digest (unique id) list.\n\n        If 'which', result contains unique id for that message\n        in the form 'response mesgnum uid', otherwise result is\n        the list ['response', ['mesgnum uid', ...], octets]\n        ");
      var1.setline(315);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(316);
         var3 = var1.getlocal(0).__getattr__("_shortcmd").__call__(var2, PyString.fromInterned("UIDL %s")._mod(var1.getlocal(1)));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(317);
         var3 = var1.getlocal(0).__getattr__("_longcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("UIDL"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject POP3_SSL$27(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("POP3 client class over SSL connection\n\n        Instantiate with: POP3_SSL(hostname, port=995, keyfile=None, certfile=None)\n\n               hostname - the hostname of the pop3 over ssl server\n               port - port number\n               keyfile - PEM formatted file that contains your private key\n               certfile - PEM formatted certificate chain file\n\n            See the methods of the parent class POP3 for more documentation.\n        "));
      var1.setline(336);
      PyString.fromInterned("POP3 client class over SSL connection\n\n        Instantiate with: POP3_SSL(hostname, port=995, keyfile=None, certfile=None)\n\n               hostname - the hostname of the pop3 over ssl server\n               port - port number\n               keyfile - PEM formatted file that contains your private key\n               certfile - PEM formatted certificate chain file\n\n            See the methods of the parent class POP3 for more documentation.\n        ");
      var1.setline(338);
      PyObject[] var3 = new PyObject[]{var1.getname("POP3_SSL_PORT"), var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$28, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(364);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _fillBuffer$29, (PyObject)null);
      var1.setlocal("_fillBuffer", var4);
      var3 = null;
      var1.setline(370);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _getline$30, (PyObject)null);
      var1.setlocal("_getline", var4);
      var3 = null;
      var1.setline(390);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _putline$31, (PyObject)null);
      var1.setlocal("_putline", var4);
      var3 = null;
      var1.setline(401);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, quit$32, PyString.fromInterned("Signoff: commit changes on server, unlock mailbox, close connection."));
      var1.setlocal("quit", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$28(PyFrame var1, ThreadState var2) {
      var1.setline(339);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("host", var3);
      var3 = null;
      var1.setline(340);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("port", var3);
      var3 = null;
      var1.setline(341);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("keyfile", var3);
      var3 = null;
      var1.setline(342);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("certfile", var3);
      var3 = null;
      var1.setline(343);
      PyString var9 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"buffer", var9);
      var3 = null;
      var1.setline(344);
      var9 = PyString.fromInterned("getaddrinfo returns an empty list");
      var1.setlocal(5, var9);
      var3 = null;
      var1.setline(345);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("sock", var3);
      var3 = null;
      var1.setline(346);
      var3 = var1.getglobal("socket").__getattr__("getaddrinfo").__call__(var2, var1.getlocal(0).__getattr__("host"), var1.getlocal(0).__getattr__("port"), Py.newInteger(0), var1.getglobal("socket").__getattr__("SOCK_STREAM")).__iter__();

      while(true) {
         var1.setline(346);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            break;
         }

         var1.setlocal(6, var4);
         var1.setline(347);
         PyObject var5 = var1.getlocal(6);
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

         try {
            var1.setline(349);
            var5 = var1.getglobal("socket").__getattr__("socket").__call__(var2, var1.getlocal(7), var1.getlocal(8), var1.getlocal(9));
            var1.getlocal(0).__setattr__("sock", var5);
            var5 = null;
            var1.setline(350);
            var1.getlocal(0).__getattr__("sock").__getattr__("connect").__call__(var2, var1.getlocal(11));
            break;
         } catch (Throwable var8) {
            PyException var10 = Py.setException(var8, var1);
            if (!var10.match(var1.getglobal("socket").__getattr__("error"))) {
               throw var10;
            }

            PyObject var11 = var10.value;
            var1.setlocal(5, var11);
            var6 = null;
            var1.setline(352);
            if (var1.getlocal(0).__getattr__("sock").__nonzero__()) {
               var1.setline(353);
               var1.getlocal(0).__getattr__("sock").__getattr__("close").__call__(var2);
            }

            var1.setline(354);
            var11 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("sock", var11);
            var6 = null;
         }
      }

      var1.setline(357);
      if (var1.getlocal(0).__getattr__("sock").__not__().__nonzero__()) {
         var1.setline(358);
         throw Py.makeException(var1.getglobal("socket").__getattr__("error"), var1.getlocal(5));
      } else {
         var1.setline(359);
         var3 = var1.getlocal(0).__getattr__("sock").__getattr__("makefile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("rb"));
         var1.getlocal(0).__setattr__("file", var3);
         var3 = null;
         var1.setline(360);
         var3 = var1.getglobal("ssl").__getattr__("wrap_socket").__call__(var2, var1.getlocal(0).__getattr__("sock"), var1.getlocal(0).__getattr__("keyfile"), var1.getlocal(0).__getattr__("certfile"));
         var1.getlocal(0).__setattr__("sslobj", var3);
         var3 = null;
         var1.setline(361);
         PyInteger var12 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"_debugging", var12);
         var3 = null;
         var1.setline(362);
         var3 = var1.getlocal(0).__getattr__("_getresp").__call__(var2);
         var1.getlocal(0).__setattr__("welcome", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _fillBuffer$29(PyFrame var1, ThreadState var2) {
      var1.setline(365);
      PyObject var3 = var1.getlocal(0).__getattr__("sslobj").__getattr__("read").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(366);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(367);
         throw Py.makeException(var1.getglobal("error_proto").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-ERR EOF")));
      } else {
         var1.setline(368);
         var10000 = var1.getlocal(0);
         String var6 = "buffer";
         PyObject var4 = var10000;
         PyObject var5 = var4.__getattr__(var6);
         var5 = var5._iadd(var1.getlocal(1));
         var4.__setattr__(var6, var5);
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _getline$30(PyFrame var1, ThreadState var2) {
      var1.setline(371);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(372);
      PyObject var5 = var1.getglobal("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".*?\\n"));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(373);
      var5 = var1.getlocal(2).__getattr__("match").__call__(var2, var1.getlocal(0).__getattr__("buffer"));
      var1.setlocal(3, var5);
      var3 = null;

      while(true) {
         var1.setline(374);
         PyObject var10000;
         if (!var1.getlocal(3).__not__().__nonzero__()) {
            var1.setline(379);
            var5 = var1.getlocal(3).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            var1.setlocal(1, var5);
            var3 = null;
            var1.setline(380);
            var5 = var1.getlocal(2).__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned(""), (PyObject)var1.getlocal(0).__getattr__("buffer"), (PyObject)Py.newInteger(1));
            var1.getlocal(0).__setattr__("buffer", var5);
            var3 = null;
            var1.setline(381);
            var5 = var1.getlocal(0).__getattr__("_debugging");
            var10000 = var5._gt(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(381);
               Py.printComma(PyString.fromInterned("*get*"));
               Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(1)));
            }

            var1.setline(383);
            var5 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
            var1.setlocal(4, var5);
            var3 = null;
            var1.setline(384);
            var5 = var1.getlocal(1).__getslice__(Py.newInteger(-2), (PyObject)null, (PyObject)null);
            var10000 = var5._eq(var1.getglobal("CRLF"));
            var3 = null;
            PyTuple var6;
            if (var10000.__nonzero__()) {
               var1.setline(385);
               var6 = new PyTuple(new PyObject[]{var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-2), (PyObject)null), var1.getlocal(4)});
               var1.f_lasti = -1;
               return var6;
            } else {
               var1.setline(386);
               PyObject var4 = var1.getlocal(1).__getitem__(Py.newInteger(0));
               var10000 = var4._eq(var1.getglobal("CR"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(387);
                  var6 = new PyTuple(new PyObject[]{var1.getlocal(1).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null), var1.getlocal(4)});
                  var1.f_lasti = -1;
                  return var6;
               } else {
                  var1.setline(388);
                  var6 = new PyTuple(new PyObject[]{var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null), var1.getlocal(4)});
                  var1.f_lasti = -1;
                  return var6;
               }
            }
         }

         var1.setline(375);
         var1.getlocal(0).__getattr__("_fillBuffer").__call__(var2);
         var1.setline(376);
         var5 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("buffer"));
         var10000 = var5._gt(var1.getglobal("_MAXLINE"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(377);
            throw Py.makeException(var1.getglobal("error_proto").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("line too long")));
         }

         var1.setline(378);
         var5 = var1.getlocal(2).__getattr__("match").__call__(var2, var1.getlocal(0).__getattr__("buffer"));
         var1.setlocal(3, var5);
         var3 = null;
      }
   }

   public PyObject _putline$31(PyFrame var1, ThreadState var2) {
      var1.setline(391);
      PyObject var3 = var1.getlocal(0).__getattr__("_debugging");
      PyObject var10000 = var3._gt(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(391);
         Py.printComma(PyString.fromInterned("*put*"));
         Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(1)));
      }

      var1.setline(392);
      var3 = var1.getlocal(1);
      var3 = var3._iadd(var1.getglobal("CRLF"));
      var1.setlocal(1, var3);
      var1.setline(393);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;

      while(true) {
         var1.setline(394);
         var3 = var1.getlocal(2);
         var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(395);
         var3 = var1.getlocal(0).__getattr__("sslobj").__getattr__("write").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(396);
         var3 = var1.getlocal(3);
         var10000 = var3._eq(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(398);
         var3 = var1.getlocal(1).__getslice__(var1.getlocal(3), (PyObject)null, (PyObject)null);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(399);
         var3 = var1.getlocal(2)._sub(var1.getlocal(3));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject quit$32(PyFrame var1, ThreadState var2) {
      var1.setline(402);
      PyString.fromInterned("Signoff: commit changes on server, unlock mailbox, close connection.");

      PyException var3;
      PyObject var6;
      try {
         var1.setline(404);
         var6 = var1.getlocal(0).__getattr__("_shortcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("QUIT"));
         var1.setlocal(1, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (!var3.match(var1.getglobal("error_proto"))) {
            throw var3;
         }

         PyObject var4 = var3.value;
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(406);
         var4 = var1.getlocal(2);
         var1.setlocal(1, var4);
         var4 = null;
      }

      var1.setline(407);
      var1.getlocal(0).__getattr__("sock").__getattr__("close").__call__(var2);
      var1.setline(408);
      var1.getlocal(0).__delattr__("sslobj");
      var1.getlocal(0).__delattr__("sock");
      var1.setline(409);
      var6 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var6;
   }

   public poplib$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      error_proto$1 = Py.newCode(0, var2, var1, "error_proto", 22, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      POP3$2 = Py.newCode(0, var2, var1, "POP3", 42, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "host", "port", "timeout"};
      __init__$3 = Py.newCode(4, var2, var1, "__init__", 85, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      _putline$4 = Py.newCode(2, var2, var1, "_putline", 95, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      _putcmd$5 = Py.newCode(2, var2, var1, "_putcmd", 102, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line", "octets"};
      _getline$6 = Py.newCode(1, var2, var1, "_getline", 111, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "resp", "o", "c"};
      _getresp$7 = Py.newCode(1, var2, var1, "_getresp", 131, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "resp", "list", "octets", "line", "o"};
      _getlongresp$8 = Py.newCode(1, var2, var1, "_getlongresp", 142, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      _shortcmd$9 = Py.newCode(2, var2, var1, "_shortcmd", 158, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      _longcmd$10 = Py.newCode(2, var2, var1, "_longcmd", 165, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getwelcome$11 = Py.newCode(1, var2, var1, "getwelcome", 172, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "level"};
      set_debuglevel$12 = Py.newCode(2, var2, var1, "set_debuglevel", 176, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "user"};
      user$13 = Py.newCode(2, var2, var1, "user", 182, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pswd"};
      pass_$14 = Py.newCode(2, var2, var1, "pass_", 190, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "retval", "rets", "numMessages", "sizeMessages"};
      stat$15 = Py.newCode(1, var2, var1, "stat", 200, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "which"};
      list$16 = Py.newCode(2, var2, var1, "list", 213, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "which"};
      retr$17 = Py.newCode(2, var2, var1, "retr", 227, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "which"};
      dele$18 = Py.newCode(2, var2, var1, "dele", 235, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      noop$19 = Py.newCode(1, var2, var1, "noop", 243, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      rset$20 = Py.newCode(1, var2, var1, "rset", 251, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "resp", "val"};
      quit$21 = Py.newCode(1, var2, var1, "quit", 256, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "user"};
      rpop$22 = Py.newCode(2, var2, var1, "rpop", 272, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "user", "secret", "m", "hashlib", "digest"};
      apop$23 = Py.newCode(3, var2, var1, "apop", 279, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$24 = Py.newCode(1, var2, var1, "<lambda>", 295, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "which", "howmuch"};
      top$25 = Py.newCode(3, var2, var1, "top", 299, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "which"};
      uidl$26 = Py.newCode(2, var2, var1, "uidl", 308, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      POP3_SSL$27 = Py.newCode(0, var2, var1, "POP3_SSL", 325, false, false, self, 27, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "host", "port", "keyfile", "certfile", "msg", "res", "af", "socktype", "proto", "canonname", "sa"};
      __init__$28 = Py.newCode(5, var2, var1, "__init__", 338, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "localbuf"};
      _fillBuffer$29 = Py.newCode(1, var2, var1, "_fillBuffer", 364, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line", "renewline", "match", "octets"};
      _getline$30 = Py.newCode(1, var2, var1, "_getline", 370, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line", "bytes", "sent"};
      _putline$31 = Py.newCode(2, var2, var1, "_putline", 390, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "resp", "val"};
      quit$32 = Py.newCode(1, var2, var1, "quit", 401, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new poplib$py("poplib$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(poplib$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.error_proto$1(var2, var3);
         case 2:
            return this.POP3$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this._putline$4(var2, var3);
         case 5:
            return this._putcmd$5(var2, var3);
         case 6:
            return this._getline$6(var2, var3);
         case 7:
            return this._getresp$7(var2, var3);
         case 8:
            return this._getlongresp$8(var2, var3);
         case 9:
            return this._shortcmd$9(var2, var3);
         case 10:
            return this._longcmd$10(var2, var3);
         case 11:
            return this.getwelcome$11(var2, var3);
         case 12:
            return this.set_debuglevel$12(var2, var3);
         case 13:
            return this.user$13(var2, var3);
         case 14:
            return this.pass_$14(var2, var3);
         case 15:
            return this.stat$15(var2, var3);
         case 16:
            return this.list$16(var2, var3);
         case 17:
            return this.retr$17(var2, var3);
         case 18:
            return this.dele$18(var2, var3);
         case 19:
            return this.noop$19(var2, var3);
         case 20:
            return this.rset$20(var2, var3);
         case 21:
            return this.quit$21(var2, var3);
         case 22:
            return this.rpop$22(var2, var3);
         case 23:
            return this.apop$23(var2, var3);
         case 24:
            return this.f$24(var2, var3);
         case 25:
            return this.top$25(var2, var3);
         case 26:
            return this.uidl$26(var2, var3);
         case 27:
            return this.POP3_SSL$27(var2, var3);
         case 28:
            return this.__init__$28(var2, var3);
         case 29:
            return this._fillBuffer$29(var2, var3);
         case 30:
            return this._getline$30(var2, var3);
         case 31:
            return this._putline$31(var2, var3);
         case 32:
            return this.quit$32(var2, var3);
         default:
            return null;
      }
   }
}
