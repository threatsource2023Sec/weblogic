package logging;

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
import org.python.core.PyFloat;
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
@Filename("logging/handlers.py")
public class handlers$py extends PyFunctionTable implements PyRunnable {
   static handlers$py self;
   static final PyCode f$0;
   static final PyCode BaseRotatingHandler$1;
   static final PyCode __init__$2;
   static final PyCode emit$3;
   static final PyCode RotatingFileHandler$4;
   static final PyCode __init__$5;
   static final PyCode doRollover$6;
   static final PyCode shouldRollover$7;
   static final PyCode TimedRotatingFileHandler$8;
   static final PyCode __init__$9;
   static final PyCode computeRollover$10;
   static final PyCode shouldRollover$11;
   static final PyCode getFilesToDelete$12;
   static final PyCode doRollover$13;
   static final PyCode WatchedFileHandler$14;
   static final PyCode __init__$15;
   static final PyCode _statstream$16;
   static final PyCode emit$17;
   static final PyCode SocketHandler$18;
   static final PyCode __init__$19;
   static final PyCode makeSocket$20;
   static final PyCode createSocket$21;
   static final PyCode send$22;
   static final PyCode makePickle$23;
   static final PyCode handleError$24;
   static final PyCode emit$25;
   static final PyCode close$26;
   static final PyCode DatagramHandler$27;
   static final PyCode __init__$28;
   static final PyCode makeSocket$29;
   static final PyCode send$30;
   static final PyCode SysLogHandler$31;
   static final PyCode __init__$32;
   static final PyCode _connect_unixsocket$33;
   static final PyCode encodePriority$34;
   static final PyCode close$35;
   static final PyCode mapPriority$36;
   static final PyCode emit$37;
   static final PyCode SMTPHandler$38;
   static final PyCode __init__$39;
   static final PyCode getSubject$40;
   static final PyCode emit$41;
   static final PyCode NTEventLogHandler$42;
   static final PyCode __init__$43;
   static final PyCode getMessageID$44;
   static final PyCode getEventCategory$45;
   static final PyCode getEventType$46;
   static final PyCode emit$47;
   static final PyCode close$48;
   static final PyCode HTTPHandler$49;
   static final PyCode __init__$50;
   static final PyCode mapLogRecord$51;
   static final PyCode emit$52;
   static final PyCode BufferingHandler$53;
   static final PyCode __init__$54;
   static final PyCode shouldFlush$55;
   static final PyCode emit$56;
   static final PyCode flush$57;
   static final PyCode close$58;
   static final PyCode MemoryHandler$59;
   static final PyCode __init__$60;
   static final PyCode shouldFlush$61;
   static final PyCode setTarget$62;
   static final PyCode flush$63;
   static final PyCode close$64;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nAdditional handlers for the logging package for Python. The core package is\nbased on PEP 282 and comments thereto in comp.lang.python.\n\nCopyright (C) 2001-2012 Vinay Sajip. All Rights Reserved.\n\nTo use, simply 'import logging.handlers' and log away!\n"));
      var1.setline(24);
      PyString.fromInterned("\nAdditional handlers for the logging package for Python. The core package is\nbased on PEP 282 and comments thereto in comp.lang.python.\n\nCopyright (C) 2001-2012 Vinay Sajip. All Rights Reserved.\n\nTo use, simply 'import logging.handlers' and log away!\n");
      var1.setline(26);
      PyObject var3 = imp.importOne("errno", var1, -1);
      var1.setlocal("errno", var3);
      var3 = null;
      var3 = imp.importOne("logging", var1, -1);
      var1.setlocal("logging", var3);
      var3 = null;
      var3 = imp.importOne("socket", var1, -1);
      var1.setlocal("socket", var3);
      var3 = null;
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var3 = imp.importOne("cPickle", var1, -1);
      var1.setlocal("cPickle", var3);
      var3 = null;
      var3 = imp.importOne("struct", var1, -1);
      var1.setlocal("struct", var3);
      var3 = null;
      var3 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var3);
      var3 = null;
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(27);
      String[] var7 = new String[]{"ST_DEV", "ST_INO", "ST_MTIME"};
      PyObject[] var8 = imp.importFrom("stat", var7, var1, -1);
      PyObject var4 = var8[0];
      var1.setlocal("ST_DEV", var4);
      var4 = null;
      var4 = var8[1];
      var1.setlocal("ST_INO", var4);
      var4 = null;
      var4 = var8[2];
      var1.setlocal("ST_MTIME", var4);
      var4 = null;

      PyException var9;
      try {
         var1.setline(30);
         var3 = imp.importOne("codecs", var1, -1);
         var1.setlocal("codecs", var3);
         var3 = null;
      } catch (Throwable var6) {
         var9 = Py.setException(var6, var1);
         if (!var9.match(var1.getname("ImportError"))) {
            throw var9;
         }

         var1.setline(32);
         var4 = var1.getname("None");
         var1.setlocal("codecs", var4);
         var4 = null;
      }

      try {
         var1.setline(34);
         var1.getname("unicode");
         var1.setline(35);
         var3 = var1.getname("True");
         var1.setlocal("_unicode", var3);
         var3 = null;
      } catch (Throwable var5) {
         var9 = Py.setException(var5, var1);
         if (!var9.match(var1.getname("NameError"))) {
            throw var9;
         }

         var1.setline(37);
         var4 = var1.getname("False");
         var1.setlocal("_unicode", var4);
         var4 = null;
      }

      var1.setline(43);
      PyInteger var10 = Py.newInteger(9020);
      var1.setlocal("DEFAULT_TCP_LOGGING_PORT", var10);
      var3 = null;
      var1.setline(44);
      var10 = Py.newInteger(9021);
      var1.setlocal("DEFAULT_UDP_LOGGING_PORT", var10);
      var3 = null;
      var1.setline(45);
      var10 = Py.newInteger(9022);
      var1.setlocal("DEFAULT_HTTP_LOGGING_PORT", var10);
      var3 = null;
      var1.setline(46);
      var10 = Py.newInteger(9023);
      var1.setlocal("DEFAULT_SOAP_LOGGING_PORT", var10);
      var3 = null;
      var1.setline(47);
      var10 = Py.newInteger(514);
      var1.setlocal("SYSLOG_UDP_PORT", var10);
      var3 = null;
      var1.setline(48);
      var10 = Py.newInteger(514);
      var1.setlocal("SYSLOG_TCP_PORT", var10);
      var3 = null;
      var1.setline(50);
      var3 = Py.newInteger(24)._mul(Py.newInteger(60))._mul(Py.newInteger(60));
      var1.setlocal("_MIDNIGHT", var3);
      var3 = null;
      var1.setline(52);
      var8 = new PyObject[]{var1.getname("logging").__getattr__("FileHandler")};
      var4 = Py.makeClass("BaseRotatingHandler", var8, BaseRotatingHandler$1);
      var1.setlocal("BaseRotatingHandler", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(84);
      var8 = new PyObject[]{var1.getname("BaseRotatingHandler")};
      var4 = Py.makeClass("RotatingFileHandler", var8, RotatingFileHandler$4);
      var1.setlocal("RotatingFileHandler", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(160);
      var8 = new PyObject[]{var1.getname("BaseRotatingHandler")};
      var4 = Py.makeClass("TimedRotatingFileHandler", var8, TimedRotatingFileHandler$8);
      var1.setlocal("TimedRotatingFileHandler", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(371);
      var8 = new PyObject[]{var1.getname("logging").__getattr__("FileHandler")};
      var4 = Py.makeClass("WatchedFileHandler", var8, WatchedFileHandler$14);
      var1.setlocal("WatchedFileHandler", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(432);
      var8 = new PyObject[]{var1.getname("logging").__getattr__("Handler")};
      var4 = Py.makeClass("SocketHandler", var8, SocketHandler$18);
      var1.setlocal("SocketHandler", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(598);
      var8 = new PyObject[]{var1.getname("SocketHandler")};
      var4 = Py.makeClass("DatagramHandler", var8, DatagramHandler$27);
      var1.setlocal("DatagramHandler", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(636);
      var8 = new PyObject[]{var1.getname("logging").__getattr__("Handler")};
      var4 = Py.makeClass("SysLogHandler", var8, SysLogHandler$31);
      var1.setlocal("SysLogHandler", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(847);
      var8 = new PyObject[]{var1.getname("logging").__getattr__("Handler")};
      var4 = Py.makeClass("SMTPHandler", var8, SMTPHandler$38);
      var1.setlocal("SMTPHandler", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(925);
      var8 = new PyObject[]{var1.getname("logging").__getattr__("Handler")};
      var4 = Py.makeClass("NTEventLogHandler", var8, NTEventLogHandler$42);
      var1.setlocal("NTEventLogHandler", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(1025);
      var8 = new PyObject[]{var1.getname("logging").__getattr__("Handler")};
      var4 = Py.makeClass("HTTPHandler", var8, HTTPHandler$49);
      var1.setlocal("HTTPHandler", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(1087);
      var8 = new PyObject[]{var1.getname("logging").__getattr__("Handler")};
      var4 = Py.makeClass("BufferingHandler", var8, BufferingHandler$53);
      var1.setlocal("BufferingHandler", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(1142);
      var8 = new PyObject[]{var1.getname("BufferingHandler")};
      var4 = Py.makeClass("MemoryHandler", var8, MemoryHandler$59);
      var1.setlocal("MemoryHandler", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BaseRotatingHandler$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    Base class for handlers that rotate log files at a certain point.\n    Not meant to be instantiated directly.  Instead, use RotatingFileHandler\n    or TimedRotatingFileHandler.\n    "));
      var1.setline(57);
      PyString.fromInterned("\n    Base class for handlers that rotate log files at a certain point.\n    Not meant to be instantiated directly.  Instead, use RotatingFileHandler\n    or TimedRotatingFileHandler.\n    ");
      var1.setline(58);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), Py.newInteger(0)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, PyString.fromInterned("\n        Use the specified filename for streamed logging\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(68);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, emit$3, PyString.fromInterned("\n        Emit a record.\n\n        Output the record to the file, catering for rollover as described\n        in doRollover().\n        "));
      var1.setlocal("emit", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(61);
      PyString.fromInterned("\n        Use the specified filename for streamed logging\n        ");
      var1.setline(62);
      PyObject var3 = var1.getglobal("codecs");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(63);
         var3 = var1.getglobal("None");
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(64);
      var10000 = var1.getglobal("logging").__getattr__("FileHandler").__getattr__("__init__");
      PyObject[] var4 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)};
      var10000.__call__(var2, var4);
      var1.setline(65);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("mode", var3);
      var3 = null;
      var1.setline(66);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("encoding", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject emit$3(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      PyString.fromInterned("\n        Emit a record.\n\n        Output the record to the file, catering for rollover as described\n        in doRollover().\n        ");

      try {
         var1.setline(76);
         if (var1.getlocal(0).__getattr__("shouldRollover").__call__(var2, var1.getlocal(1)).__nonzero__()) {
            var1.setline(77);
            var1.getlocal(0).__getattr__("doRollover").__call__(var2);
         }

         var1.setline(78);
         var1.getglobal("logging").__getattr__("FileHandler").__getattr__("emit").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      } catch (Throwable var4) {
         PyException var3 = Py.setException(var4, var1);
         if (var3.match(new PyTuple(new PyObject[]{var1.getglobal("KeyboardInterrupt"), var1.getglobal("SystemExit")}))) {
            var1.setline(80);
            throw Py.makeException();
         }

         var1.setline(82);
         var1.getlocal(0).__getattr__("handleError").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject RotatingFileHandler$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    Handler for logging to a set of files, which switches from one file\n    to the next when the current file reaches a certain size.\n    "));
      var1.setline(88);
      PyString.fromInterned("\n    Handler for logging to a set of files, which switches from one file\n    to the next when the current file reaches a certain size.\n    ");
      var1.setline(89);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("a"), Py.newInteger(0), Py.newInteger(0), var1.getname("None"), Py.newInteger(0)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$5, PyString.fromInterned("\n        Open the specified file and use it as the stream for logging.\n\n        By default, the file grows indefinitely. You can specify particular\n        values of maxBytes and backupCount to allow the file to rollover at\n        a predetermined size.\n\n        Rollover occurs whenever the current log file is nearly maxBytes in\n        length. If backupCount is >= 1, the system will successively create\n        new files with the same pathname as the base file, but with extensions\n        \".1\", \".2\" etc. appended to it. For example, with a backupCount of 5\n        and a base file name of \"app.log\", you would get \"app.log\",\n        \"app.log.1\", \"app.log.2\", ... through to \"app.log.5\". The file being\n        written to is always \"app.log\" - when it gets filled up, it is closed\n        and renamed to \"app.log.1\", and if files \"app.log.1\", \"app.log.2\" etc.\n        exist, then they are renamed to \"app.log.2\", \"app.log.3\" etc.\n        respectively.\n\n        If maxBytes is zero, rollover never occurs.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(121);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, doRollover$6, PyString.fromInterned("\n        Do a rollover, as described in __init__().\n        "));
      var1.setlocal("doRollover", var4);
      var3 = null;
      var1.setline(144);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, shouldRollover$7, PyString.fromInterned("\n        Determine if rollover should occur.\n\n        Basically, see if the supplied record would cause the file to exceed\n        the size limit we have.\n        "));
      var1.setlocal("shouldRollover", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$5(PyFrame var1, ThreadState var2) {
      var1.setline(109);
      PyString.fromInterned("\n        Open the specified file and use it as the stream for logging.\n\n        By default, the file grows indefinitely. You can specify particular\n        values of maxBytes and backupCount to allow the file to rollover at\n        a predetermined size.\n\n        Rollover occurs whenever the current log file is nearly maxBytes in\n        length. If backupCount is >= 1, the system will successively create\n        new files with the same pathname as the base file, but with extensions\n        \".1\", \".2\" etc. appended to it. For example, with a backupCount of 5\n        and a base file name of \"app.log\", you would get \"app.log\",\n        \"app.log.1\", \"app.log.2\", ... through to \"app.log.5\". The file being\n        written to is always \"app.log\" - when it gets filled up, it is closed\n        and renamed to \"app.log.1\", and if files \"app.log.1\", \"app.log.2\" etc.\n        exist, then they are renamed to \"app.log.2\", \"app.log.3\" etc.\n        respectively.\n\n        If maxBytes is zero, rollover never occurs.\n        ");
      var1.setline(115);
      PyObject var3 = var1.getlocal(3);
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(116);
         PyString var4 = PyString.fromInterned("a");
         var1.setlocal(2, var4);
         var3 = null;
      }

      var1.setline(117);
      var10000 = var1.getglobal("BaseRotatingHandler").__getattr__("__init__");
      PyObject[] var5 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(5), var1.getlocal(6)};
      var10000.__call__(var2, var5);
      var1.setline(118);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("maxBytes", var3);
      var3 = null;
      var1.setline(119);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("backupCount", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject doRollover$6(PyFrame var1, ThreadState var2) {
      var1.setline(124);
      PyString.fromInterned("\n        Do a rollover, as described in __init__().\n        ");
      var1.setline(125);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("stream").__nonzero__()) {
         var1.setline(126);
         var1.getlocal(0).__getattr__("stream").__getattr__("close").__call__(var2);
         var1.setline(127);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("stream", var3);
         var3 = null;
      }

      var1.setline(128);
      var3 = var1.getlocal(0).__getattr__("backupCount");
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(129);
         var3 = var1.getglobal("range").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("backupCount")._sub(Py.newInteger(1)), (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(-1)).__iter__();

         while(true) {
            var1.setline(129);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(137);
               var3 = var1.getlocal(0).__getattr__("baseFilename")._add(PyString.fromInterned(".1"));
               var1.setlocal(3, var3);
               var3 = null;
               var1.setline(138);
               if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(3)).__nonzero__()) {
                  var1.setline(139);
                  var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getlocal(3));
               }

               var1.setline(140);
               var1.getglobal("os").__getattr__("rename").__call__(var2, var1.getlocal(0).__getattr__("baseFilename"), var1.getlocal(3));
               break;
            }

            var1.setlocal(1, var4);
            var1.setline(130);
            PyObject var5 = PyString.fromInterned("%s.%d")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("baseFilename"), var1.getlocal(1)}));
            var1.setlocal(2, var5);
            var5 = null;
            var1.setline(131);
            var5 = PyString.fromInterned("%s.%d")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("baseFilename"), var1.getlocal(1)._add(Py.newInteger(1))}));
            var1.setlocal(3, var5);
            var5 = null;
            var1.setline(132);
            if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(2)).__nonzero__()) {
               var1.setline(134);
               if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(3)).__nonzero__()) {
                  var1.setline(135);
                  var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getlocal(3));
               }

               var1.setline(136);
               var1.getglobal("os").__getattr__("rename").__call__(var2, var1.getlocal(2), var1.getlocal(3));
            }
         }
      }

      var1.setline(142);
      var3 = var1.getlocal(0).__getattr__("_open").__call__(var2);
      var1.getlocal(0).__setattr__("stream", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject shouldRollover$7(PyFrame var1, ThreadState var2) {
      var1.setline(150);
      PyString.fromInterned("\n        Determine if rollover should occur.\n\n        Basically, see if the supplied record would cause the file to exceed\n        the size limit we have.\n        ");
      var1.setline(151);
      PyObject var3 = var1.getlocal(0).__getattr__("stream");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(152);
         var3 = var1.getlocal(0).__getattr__("_open").__call__(var2);
         var1.getlocal(0).__setattr__("stream", var3);
         var3 = null;
      }

      var1.setline(153);
      var3 = var1.getlocal(0).__getattr__("maxBytes");
      var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      PyInteger var4;
      if (var10000.__nonzero__()) {
         var1.setline(154);
         var3 = PyString.fromInterned("%s\n")._mod(var1.getlocal(0).__getattr__("format").__call__(var2, var1.getlocal(1)));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(155);
         var1.getlocal(0).__getattr__("stream").__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(2));
         var1.setline(156);
         var3 = var1.getlocal(0).__getattr__("stream").__getattr__("tell").__call__(var2)._add(var1.getglobal("len").__call__(var2, var1.getlocal(2)));
         var10000 = var3._ge(var1.getlocal(0).__getattr__("maxBytes"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(157);
            var4 = Py.newInteger(1);
            var1.f_lasti = -1;
            return var4;
         }
      }

      var1.setline(158);
      var4 = Py.newInteger(0);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject TimedRotatingFileHandler$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    Handler for logging to a file, rotating the log file at certain timed\n    intervals.\n\n    If backupCount is > 0, when rollover is done, no more than backupCount\n    files are kept - the oldest ones are deleted.\n    "));
      var1.setline(167);
      PyString.fromInterned("\n    Handler for logging to a file, rotating the log file at certain timed\n    intervals.\n\n    If backupCount is > 0, when rollover is done, no more than backupCount\n    files are kept - the oldest ones are deleted.\n    ");
      var1.setline(168);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("h"), Py.newInteger(1), Py.newInteger(0), var1.getname("None"), var1.getname("False"), var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$9, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(221);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, computeRollover$10, PyString.fromInterned("\n        Work out the rollover time based on the specified time.\n        "));
      var1.setlocal("computeRollover", var4);
      var3 = null;
      var1.setline(281);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, shouldRollover$11, PyString.fromInterned("\n        Determine if rollover should occur.\n\n        record is not used, as we are just comparing times, but it is needed so\n        the method signatures are the same\n        "));
      var1.setlocal("shouldRollover", var4);
      var3 = null;
      var1.setline(294);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getFilesToDelete$12, PyString.fromInterned("\n        Determine the files to delete when rolling over.\n\n        More specific than the earlier method, which just used glob.glob().\n        "));
      var1.setlocal("getFilesToDelete", var4);
      var3 = null;
      var1.setline(317);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, doRollover$13, PyString.fromInterned("\n        do a rollover; in this case, a date/time stamp is appended to the filename\n        when the rollover happens.  However, you want the file to be named for the\n        start of the interval, not the current time.  If there is a backup count,\n        then we have to get a list of matching filenames, sort them and remove\n        the one with the oldest suffix.\n        "));
      var1.setlocal("doRollover", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$9(PyFrame var1, ThreadState var2) {
      var1.setline(169);
      PyObject var10000 = var1.getglobal("BaseRotatingHandler").__getattr__("__init__");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), PyString.fromInterned("a"), var1.getlocal(5), var1.getlocal(6)};
      var10000.__call__(var2, var3);
      var1.setline(170);
      PyObject var4 = var1.getlocal(2).__getattr__("upper").__call__(var2);
      var1.getlocal(0).__setattr__("when", var4);
      var3 = null;
      var1.setline(171);
      var4 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("backupCount", var4);
      var3 = null;
      var1.setline(172);
      var4 = var1.getlocal(7);
      var1.getlocal(0).__setattr__("utc", var4);
      var3 = null;
      var1.setline(185);
      var4 = var1.getlocal(0).__getattr__("when");
      var10000 = var4._eq(PyString.fromInterned("S"));
      var3 = null;
      PyInteger var5;
      PyString var6;
      if (var10000.__nonzero__()) {
         var1.setline(186);
         var5 = Py.newInteger(1);
         var1.getlocal(0).__setattr__((String)"interval", var5);
         var3 = null;
         var1.setline(187);
         var6 = PyString.fromInterned("%Y-%m-%d_%H-%M-%S");
         var1.getlocal(0).__setattr__((String)"suffix", var6);
         var3 = null;
         var1.setline(188);
         var6 = PyString.fromInterned("^\\d{4}-\\d{2}-\\d{2}_\\d{2}-\\d{2}-\\d{2}$");
         var1.getlocal(0).__setattr__((String)"extMatch", var6);
         var3 = null;
      } else {
         var1.setline(189);
         var4 = var1.getlocal(0).__getattr__("when");
         var10000 = var4._eq(PyString.fromInterned("M"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(190);
            var5 = Py.newInteger(60);
            var1.getlocal(0).__setattr__((String)"interval", var5);
            var3 = null;
            var1.setline(191);
            var6 = PyString.fromInterned("%Y-%m-%d_%H-%M");
            var1.getlocal(0).__setattr__((String)"suffix", var6);
            var3 = null;
            var1.setline(192);
            var6 = PyString.fromInterned("^\\d{4}-\\d{2}-\\d{2}_\\d{2}-\\d{2}$");
            var1.getlocal(0).__setattr__((String)"extMatch", var6);
            var3 = null;
         } else {
            var1.setline(193);
            var4 = var1.getlocal(0).__getattr__("when");
            var10000 = var4._eq(PyString.fromInterned("H"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(194);
               var4 = Py.newInteger(60)._mul(Py.newInteger(60));
               var1.getlocal(0).__setattr__("interval", var4);
               var3 = null;
               var1.setline(195);
               var6 = PyString.fromInterned("%Y-%m-%d_%H");
               var1.getlocal(0).__setattr__((String)"suffix", var6);
               var3 = null;
               var1.setline(196);
               var6 = PyString.fromInterned("^\\d{4}-\\d{2}-\\d{2}_\\d{2}$");
               var1.getlocal(0).__setattr__((String)"extMatch", var6);
               var3 = null;
            } else {
               var1.setline(197);
               var4 = var1.getlocal(0).__getattr__("when");
               var10000 = var4._eq(PyString.fromInterned("D"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var4 = var1.getlocal(0).__getattr__("when");
                  var10000 = var4._eq(PyString.fromInterned("MIDNIGHT"));
                  var3 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(198);
                  var4 = Py.newInteger(60)._mul(Py.newInteger(60))._mul(Py.newInteger(24));
                  var1.getlocal(0).__setattr__("interval", var4);
                  var3 = null;
                  var1.setline(199);
                  var6 = PyString.fromInterned("%Y-%m-%d");
                  var1.getlocal(0).__setattr__((String)"suffix", var6);
                  var3 = null;
                  var1.setline(200);
                  var6 = PyString.fromInterned("^\\d{4}-\\d{2}-\\d{2}$");
                  var1.getlocal(0).__setattr__((String)"extMatch", var6);
                  var3 = null;
               } else {
                  var1.setline(201);
                  if (!var1.getlocal(0).__getattr__("when").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("W")).__nonzero__()) {
                     var1.setline(211);
                     throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Invalid rollover interval specified: %s")._mod(var1.getlocal(0).__getattr__("when"))));
                  }

                  var1.setline(202);
                  var4 = Py.newInteger(60)._mul(Py.newInteger(60))._mul(Py.newInteger(24))._mul(Py.newInteger(7));
                  var1.getlocal(0).__setattr__("interval", var4);
                  var3 = null;
                  var1.setline(203);
                  var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("when"));
                  var10000 = var4._ne(Py.newInteger(2));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(204);
                     throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("You must specify a day for weekly rollover from 0 to 6 (0 is Monday): %s")._mod(var1.getlocal(0).__getattr__("when"))));
                  }

                  var1.setline(205);
                  var4 = var1.getlocal(0).__getattr__("when").__getitem__(Py.newInteger(1));
                  var10000 = var4._lt(PyString.fromInterned("0"));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     var4 = var1.getlocal(0).__getattr__("when").__getitem__(Py.newInteger(1));
                     var10000 = var4._gt(PyString.fromInterned("6"));
                     var3 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(206);
                     throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Invalid day specified for weekly rollover: %s")._mod(var1.getlocal(0).__getattr__("when"))));
                  }

                  var1.setline(207);
                  var4 = var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("when").__getitem__(Py.newInteger(1)));
                  var1.getlocal(0).__setattr__("dayOfWeek", var4);
                  var3 = null;
                  var1.setline(208);
                  var6 = PyString.fromInterned("%Y-%m-%d");
                  var1.getlocal(0).__setattr__((String)"suffix", var6);
                  var3 = null;
                  var1.setline(209);
                  var6 = PyString.fromInterned("^\\d{4}-\\d{2}-\\d{2}$");
                  var1.getlocal(0).__setattr__((String)"extMatch", var6);
                  var3 = null;
               }
            }
         }
      }

      var1.setline(213);
      var4 = var1.getglobal("re").__getattr__("compile").__call__(var2, var1.getlocal(0).__getattr__("extMatch"));
      var1.getlocal(0).__setattr__("extMatch", var4);
      var3 = null;
      var1.setline(214);
      var4 = var1.getlocal(0).__getattr__("interval")._mul(var1.getlocal(3));
      var1.getlocal(0).__setattr__("interval", var4);
      var3 = null;
      var1.setline(215);
      if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(216);
         var4 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(1)).__getitem__(var1.getglobal("ST_MTIME"));
         var1.setlocal(8, var4);
         var3 = null;
      } else {
         var1.setline(218);
         var4 = var1.getglobal("int").__call__(var2, var1.getglobal("time").__getattr__("time").__call__(var2));
         var1.setlocal(8, var4);
         var3 = null;
      }

      var1.setline(219);
      var4 = var1.getlocal(0).__getattr__("computeRollover").__call__(var2, var1.getlocal(8));
      var1.getlocal(0).__setattr__("rolloverAt", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject computeRollover$10(PyFrame var1, ThreadState var2) {
      var1.setline(224);
      PyString.fromInterned("\n        Work out the rollover time based on the specified time.\n        ");
      var1.setline(225);
      PyObject var3 = var1.getlocal(1)._add(var1.getlocal(0).__getattr__("interval"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(233);
      var3 = var1.getlocal(0).__getattr__("when");
      PyObject var10000 = var3._eq(PyString.fromInterned("MIDNIGHT"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("when").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("W"));
      }

      if (var10000.__nonzero__()) {
         var1.setline(235);
         if (var1.getlocal(0).__getattr__("utc").__nonzero__()) {
            var1.setline(236);
            var3 = var1.getglobal("time").__getattr__("gmtime").__call__(var2, var1.getlocal(1));
            var1.setlocal(3, var3);
            var3 = null;
         } else {
            var1.setline(238);
            var3 = var1.getglobal("time").__getattr__("localtime").__call__(var2, var1.getlocal(1));
            var1.setlocal(3, var3);
            var3 = null;
         }

         var1.setline(239);
         var3 = var1.getlocal(3).__getitem__(Py.newInteger(3));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(240);
         var3 = var1.getlocal(3).__getitem__(Py.newInteger(4));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(241);
         var3 = var1.getlocal(3).__getitem__(Py.newInteger(5));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(243);
         var3 = var1.getglobal("_MIDNIGHT")._sub(var1.getlocal(4)._mul(Py.newInteger(60))._add(var1.getlocal(5))._mul(Py.newInteger(60))._add(var1.getlocal(6)));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(245);
         var3 = var1.getlocal(1)._add(var1.getlocal(7));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(261);
         if (var1.getlocal(0).__getattr__("when").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("W")).__nonzero__()) {
            var1.setline(262);
            var3 = var1.getlocal(3).__getitem__(Py.newInteger(6));
            var1.setlocal(8, var3);
            var3 = null;
            var1.setline(263);
            var3 = var1.getlocal(8);
            var10000 = var3._ne(var1.getlocal(0).__getattr__("dayOfWeek"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(264);
               var3 = var1.getlocal(8);
               var10000 = var3._lt(var1.getlocal(0).__getattr__("dayOfWeek"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(265);
                  var3 = var1.getlocal(0).__getattr__("dayOfWeek")._sub(var1.getlocal(8));
                  var1.setlocal(9, var3);
                  var3 = null;
               } else {
                  var1.setline(267);
                  var3 = Py.newInteger(6)._sub(var1.getlocal(8))._add(var1.getlocal(0).__getattr__("dayOfWeek"))._add(Py.newInteger(1));
                  var1.setlocal(9, var3);
                  var3 = null;
               }

               var1.setline(268);
               var3 = var1.getlocal(2)._add(var1.getlocal(9)._mul(Py.newInteger(60)._mul(Py.newInteger(60))._mul(Py.newInteger(24))));
               var1.setlocal(10, var3);
               var3 = null;
               var1.setline(269);
               if (var1.getlocal(0).__getattr__("utc").__not__().__nonzero__()) {
                  var1.setline(270);
                  var3 = var1.getlocal(3).__getitem__(Py.newInteger(-1));
                  var1.setlocal(11, var3);
                  var3 = null;
                  var1.setline(271);
                  var3 = var1.getglobal("time").__getattr__("localtime").__call__(var2, var1.getlocal(10)).__getitem__(Py.newInteger(-1));
                  var1.setlocal(12, var3);
                  var3 = null;
                  var1.setline(272);
                  var3 = var1.getlocal(11);
                  var10000 = var3._ne(var1.getlocal(12));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(273);
                     PyInteger var4;
                     if (var1.getlocal(11).__not__().__nonzero__()) {
                        var1.setline(274);
                        var4 = Py.newInteger(-3600);
                        var1.setlocal(13, var4);
                        var3 = null;
                     } else {
                        var1.setline(276);
                        var4 = Py.newInteger(3600);
                        var1.setlocal(13, var4);
                        var3 = null;
                     }

                     var1.setline(277);
                     var3 = var1.getlocal(10);
                     var3 = var3._iadd(var1.getlocal(13));
                     var1.setlocal(10, var3);
                  }
               }

               var1.setline(278);
               var3 = var1.getlocal(10);
               var1.setlocal(2, var3);
               var3 = null;
            }
         }
      }

      var1.setline(279);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject shouldRollover$11(PyFrame var1, ThreadState var2) {
      var1.setline(287);
      PyString.fromInterned("\n        Determine if rollover should occur.\n\n        record is not used, as we are just comparing times, but it is needed so\n        the method signatures are the same\n        ");
      var1.setline(288);
      PyObject var3 = var1.getglobal("int").__call__(var2, var1.getglobal("time").__getattr__("time").__call__(var2));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(289);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._ge(var1.getlocal(0).__getattr__("rolloverAt"));
      var3 = null;
      PyInteger var4;
      if (var10000.__nonzero__()) {
         var1.setline(290);
         var4 = Py.newInteger(1);
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(292);
         var4 = Py.newInteger(0);
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject getFilesToDelete$12(PyFrame var1, ThreadState var2) {
      var1.setline(299);
      PyString.fromInterned("\n        Determine the files to delete when rolling over.\n\n        More specific than the earlier method, which just used glob.glob().\n        ");
      var1.setline(300);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getlocal(0).__getattr__("baseFilename"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(301);
      var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(302);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(303);
      var3 = var1.getlocal(2)._add(PyString.fromInterned("."));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(304);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(5));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(305);
      var3 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(305);
         PyObject var7 = var3.__iternext__();
         PyObject var10000;
         if (var7 == null) {
            var1.setline(310);
            var1.getlocal(4).__getattr__("sort").__call__(var2);
            var1.setline(311);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
            var10000 = var3._lt(var1.getlocal(0).__getattr__("backupCount"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(312);
               var6 = new PyList(Py.EmptyObjects);
               var1.setlocal(4, var6);
               var3 = null;
            } else {
               var1.setline(314);
               var3 = var1.getlocal(4).__getslice__((PyObject)null, var1.getglobal("len").__call__(var2, var1.getlocal(4))._sub(var1.getlocal(0).__getattr__("backupCount")), (PyObject)null);
               var1.setlocal(4, var3);
               var3 = null;
            }

            var1.setline(315);
            var3 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(7, var7);
         var1.setline(306);
         var5 = var1.getlocal(7).__getslice__((PyObject)null, var1.getlocal(6), (PyObject)null);
         var10000 = var5._eq(var1.getlocal(5));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(307);
            var5 = var1.getlocal(7).__getslice__(var1.getlocal(6), (PyObject)null, (PyObject)null);
            var1.setlocal(8, var5);
            var5 = null;
            var1.setline(308);
            if (var1.getlocal(0).__getattr__("extMatch").__getattr__("match").__call__(var2, var1.getlocal(8)).__nonzero__()) {
               var1.setline(309);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1), var1.getlocal(7)));
            }
         }
      }
   }

   public PyObject doRollover$13(PyFrame var1, ThreadState var2) {
      var1.setline(324);
      PyString.fromInterned("\n        do a rollover; in this case, a date/time stamp is appended to the filename\n        when the rollover happens.  However, you want the file to be named for the\n        start of the interval, not the current time.  If there is a backup count,\n        then we have to get a list of matching filenames, sort them and remove\n        the one with the oldest suffix.\n        ");
      var1.setline(325);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("stream").__nonzero__()) {
         var1.setline(326);
         var1.getlocal(0).__getattr__("stream").__getattr__("close").__call__(var2);
         var1.setline(327);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("stream", var3);
         var3 = null;
      }

      var1.setline(329);
      var3 = var1.getglobal("int").__call__(var2, var1.getglobal("time").__getattr__("time").__call__(var2));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(330);
      var3 = var1.getglobal("time").__getattr__("localtime").__call__(var2, var1.getlocal(1)).__getitem__(Py.newInteger(-1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(331);
      var3 = var1.getlocal(0).__getattr__("rolloverAt")._sub(var1.getlocal(0).__getattr__("interval"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(332);
      PyObject var10000;
      PyInteger var5;
      if (var1.getlocal(0).__getattr__("utc").__nonzero__()) {
         var1.setline(333);
         var3 = var1.getglobal("time").__getattr__("gmtime").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var3);
         var3 = null;
      } else {
         var1.setline(335);
         var3 = var1.getglobal("time").__getattr__("localtime").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(336);
         var3 = var1.getlocal(4).__getitem__(Py.newInteger(-1));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(337);
         var3 = var1.getlocal(2);
         var10000 = var3._ne(var1.getlocal(5));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(338);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(339);
               var5 = Py.newInteger(3600);
               var1.setlocal(6, var5);
               var3 = null;
            } else {
               var1.setline(341);
               var5 = Py.newInteger(-3600);
               var1.setlocal(6, var5);
               var3 = null;
            }

            var1.setline(342);
            var3 = var1.getglobal("time").__getattr__("localtime").__call__(var2, var1.getlocal(3)._add(var1.getlocal(6)));
            var1.setlocal(4, var3);
            var3 = null;
         }
      }

      var1.setline(343);
      var3 = var1.getlocal(0).__getattr__("baseFilename")._add(PyString.fromInterned("."))._add(var1.getglobal("time").__getattr__("strftime").__call__(var2, var1.getlocal(0).__getattr__("suffix"), var1.getlocal(4)));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(344);
      if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(7)).__nonzero__()) {
         var1.setline(345);
         var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getlocal(7));
      }

      var1.setline(346);
      var1.getglobal("os").__getattr__("rename").__call__(var2, var1.getlocal(0).__getattr__("baseFilename"), var1.getlocal(7));
      var1.setline(347);
      var3 = var1.getlocal(0).__getattr__("backupCount");
      var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(353);
         var3 = var1.getlocal(0).__getattr__("getFilesToDelete").__call__(var2).__iter__();

         while(true) {
            var1.setline(353);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(8, var4);
            var1.setline(354);
            var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getlocal(8));
         }
      }

      var1.setline(356);
      var3 = var1.getlocal(0).__getattr__("_open").__call__(var2);
      var1.getlocal(0).__setattr__("stream", var3);
      var3 = null;
      var1.setline(357);
      var3 = var1.getlocal(0).__getattr__("computeRollover").__call__(var2, var1.getlocal(1));
      var1.setlocal(9, var3);
      var3 = null;

      while(true) {
         var1.setline(358);
         var3 = var1.getlocal(9);
         var10000 = var3._le(var1.getlocal(1));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(361);
            var3 = var1.getlocal(0).__getattr__("when");
            var10000 = var3._eq(PyString.fromInterned("MIDNIGHT"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("when").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("W"));
            }

            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("utc").__not__();
            }

            if (var10000.__nonzero__()) {
               var1.setline(362);
               var3 = var1.getglobal("time").__getattr__("localtime").__call__(var2, var1.getlocal(9)).__getitem__(Py.newInteger(-1));
               var1.setlocal(10, var3);
               var3 = null;
               var1.setline(363);
               var3 = var1.getlocal(2);
               var10000 = var3._ne(var1.getlocal(10));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(364);
                  if (var1.getlocal(2).__not__().__nonzero__()) {
                     var1.setline(365);
                     var5 = Py.newInteger(-3600);
                     var1.setlocal(6, var5);
                     var3 = null;
                  } else {
                     var1.setline(367);
                     var5 = Py.newInteger(3600);
                     var1.setlocal(6, var5);
                     var3 = null;
                  }

                  var1.setline(368);
                  var3 = var1.getlocal(9);
                  var3 = var3._iadd(var1.getlocal(6));
                  var1.setlocal(9, var3);
               }
            }

            var1.setline(369);
            var3 = var1.getlocal(9);
            var1.getlocal(0).__setattr__("rolloverAt", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(359);
         var3 = var1.getlocal(9)._add(var1.getlocal(0).__getattr__("interval"));
         var1.setlocal(9, var3);
         var3 = null;
      }
   }

   public PyObject WatchedFileHandler$14(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    A handler for logging to a file, which watches the file\n    to see if it has changed while in use. This can happen because of\n    usage of programs such as newsyslog and logrotate which perform\n    log file rotation. This handler, intended for use under Unix,\n    watches the file to see if it has changed since the last emit.\n    (A file has changed if its device or inode have changed.)\n    If it has changed, the old file stream is closed, and the file\n    opened to get a new stream.\n\n    This handler is not appropriate for use under Windows, because\n    under Windows open files cannot be moved or renamed - logging\n    opens the files with exclusive locks - and so there is no need\n    for such a handler. Furthermore, ST_INO is not supported under\n    Windows; stat always returns zero for this value.\n\n    This handler is based on a suggestion and patch by Chad J.\n    Schroeder.\n    "));
      var1.setline(390);
      PyString.fromInterned("\n    A handler for logging to a file, which watches the file\n    to see if it has changed while in use. This can happen because of\n    usage of programs such as newsyslog and logrotate which perform\n    log file rotation. This handler, intended for use under Unix,\n    watches the file to see if it has changed since the last emit.\n    (A file has changed if its device or inode have changed.)\n    If it has changed, the old file stream is closed, and the file\n    opened to get a new stream.\n\n    This handler is not appropriate for use under Windows, because\n    under Windows open files cannot be moved or renamed - logging\n    opens the files with exclusive locks - and so there is no need\n    for such a handler. Furthermore, ST_INO is not supported under\n    Windows; stat always returns zero for this value.\n\n    This handler is based on a suggestion and patch by Chad J.\n    Schroeder.\n    ");
      var1.setline(391);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("a"), var1.getname("None"), Py.newInteger(0)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$15, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(396);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _statstream$16, (PyObject)null);
      var1.setlocal("_statstream", var4);
      var3 = null;
      var1.setline(401);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, emit$17, PyString.fromInterned("\n        Emit a record.\n\n        First check if the underlying file has changed, and if it\n        has, close the old stream and reopen the file to get the\n        current stream.\n        "));
      var1.setlocal("emit", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$15(PyFrame var1, ThreadState var2) {
      var1.setline(392);
      PyObject var10000 = var1.getglobal("logging").__getattr__("FileHandler").__getattr__("__init__");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)};
      var10000.__call__(var2, var3);
      var1.setline(393);
      PyTuple var6 = new PyTuple(new PyObject[]{Py.newInteger(-1), Py.newInteger(-1)});
      PyObject[] var4 = Py.unpackSequence(var6, 2);
      PyObject var5 = var4[0];
      var1.getlocal(0).__setattr__("dev", var5);
      var5 = null;
      var5 = var4[1];
      var1.getlocal(0).__setattr__("ino", var5);
      var5 = null;
      var3 = null;
      var1.setline(394);
      var1.getlocal(0).__getattr__("_statstream").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _statstream$16(PyFrame var1, ThreadState var2) {
      var1.setline(397);
      if (var1.getlocal(0).__getattr__("stream").__nonzero__()) {
         var1.setline(398);
         PyObject var3 = var1.getglobal("os").__getattr__("fstat").__call__(var2, var1.getlocal(0).__getattr__("stream").__getattr__("fileno").__call__(var2));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(399);
         PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(1).__getitem__(var1.getglobal("ST_DEV")), var1.getlocal(1).__getitem__(var1.getglobal("ST_INO"))});
         PyObject[] var4 = Py.unpackSequence(var6, 2);
         PyObject var5 = var4[0];
         var1.getlocal(0).__setattr__("dev", var5);
         var5 = null;
         var5 = var4[1];
         var1.getlocal(0).__setattr__("ino", var5);
         var5 = null;
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject emit$17(PyFrame var1, ThreadState var2) {
      var1.setline(408);
      PyString.fromInterned("\n        Emit a record.\n\n        First check if the underlying file has changed, and if it\n        has, close the old stream and reopen the file to get the\n        current stream.\n        ");

      PyObject var10000;
      PyException var3;
      PyObject var6;
      try {
         var1.setline(415);
         var6 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(0).__getattr__("baseFilename"));
         var1.setlocal(2, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (!var3.match(var1.getglobal("OSError"))) {
            throw var3;
         }

         PyObject var4 = var3.value;
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(417);
         var4 = var1.getlocal(3).__getattr__("errno");
         var10000 = var4._eq(var1.getglobal("errno").__getattr__("ENOENT"));
         var4 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(420);
            throw Py.makeException();
         }

         var1.setline(418);
         var4 = var1.getglobal("None");
         var1.setlocal(2, var4);
         var4 = null;
      }

      var1.setline(422);
      var10000 = var1.getlocal(2).__not__();
      if (!var10000.__nonzero__()) {
         var6 = var1.getlocal(2).__getitem__(var1.getglobal("ST_DEV"));
         var10000 = var6._ne(var1.getlocal(0).__getattr__("dev"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var6 = var1.getlocal(2).__getitem__(var1.getglobal("ST_INO"));
            var10000 = var6._ne(var1.getlocal(0).__getattr__("ino"));
            var3 = null;
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(423);
         var6 = var1.getlocal(0).__getattr__("stream");
         var10000 = var6._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(425);
            var1.getlocal(0).__getattr__("stream").__getattr__("flush").__call__(var2);
            var1.setline(426);
            var1.getlocal(0).__getattr__("stream").__getattr__("close").__call__(var2);
            var1.setline(428);
            var6 = var1.getlocal(0).__getattr__("_open").__call__(var2);
            var1.getlocal(0).__setattr__("stream", var6);
            var3 = null;
            var1.setline(429);
            var1.getlocal(0).__getattr__("_statstream").__call__(var2);
         }
      }

      var1.setline(430);
      var1.getglobal("logging").__getattr__("FileHandler").__getattr__("emit").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject SocketHandler$18(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    A handler class which writes logging records, in pickle format, to\n    a streaming socket. The socket is kept open across logging calls.\n    If the peer resets it, an attempt is made to reconnect on the next call.\n    The pickle which is sent is that of the LogRecord's attribute dictionary\n    (__dict__), so that the receiver does not need to have the logging module\n    installed in order to process the logging event.\n\n    To unpickle the record at the receiving end into a LogRecord, use the\n    makeLogRecord function.\n    "));
      var1.setline(443);
      PyString.fromInterned("\n    A handler class which writes logging records, in pickle format, to\n    a streaming socket. The socket is kept open across logging calls.\n    If the peer resets it, an attempt is made to reconnect on the next call.\n    The pickle which is sent is that of the LogRecord's attribute dictionary\n    (__dict__), so that the receiver does not need to have the logging module\n    installed in order to process the logging event.\n\n    To unpickle the record at the receiving end into a LogRecord, use the\n    makeLogRecord function.\n    ");
      var1.setline(445);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$19, PyString.fromInterned("\n        Initializes the handler with a specific host address and port.\n\n        The attribute 'closeOnError' is set to 1 - which means that if\n        a socket error occurs, the socket is silently closed and then\n        reopened on the next logging call.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(466);
      var3 = new PyObject[]{Py.newInteger(1)};
      var4 = new PyFunction(var1.f_globals, var3, makeSocket$20, PyString.fromInterned("\n        A factory method which allows subclasses to define the precise\n        type of socket they want.\n        "));
      var1.setlocal("makeSocket", var4);
      var3 = null;
      var1.setline(477);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, createSocket$21, PyString.fromInterned("\n        Try to create a socket, using an exponential backoff with\n        a max retry time. Thanks to Robert Olson for the original patch\n        (SF #815911) which has been slightly refactored.\n        "));
      var1.setlocal("createSocket", var4);
      var3 = null;
      var1.setline(505);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, send$22, PyString.fromInterned("\n        Send a pickled string to the socket.\n\n        This function allows for partial sends which can happen when the\n        network is busy.\n        "));
      var1.setlocal("send", var4);
      var3 = null;
      var1.setline(532);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, makePickle$23, PyString.fromInterned("\n        Pickles the record in binary format with a length prefix, and\n        returns it ready for transmission across the socket.\n        "));
      var1.setlocal("makePickle", var4);
      var3 = null;
      var1.setline(554);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, handleError$24, PyString.fromInterned("\n        Handle an error during logging.\n\n        An error has occurred during logging. Most likely cause -\n        connection lost. Close the socket so that we can retry on the\n        next event.\n        "));
      var1.setlocal("handleError", var4);
      var3 = null;
      var1.setline(568);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, emit$25, PyString.fromInterned("\n        Emit a record.\n\n        Pickles the record and writes it to the socket in binary format.\n        If there is an error with the socket, silently drop the packet.\n        If there was a problem with the socket, re-establishes the\n        socket.\n        "));
      var1.setlocal("emit", var4);
      var3 = null;
      var1.setline(585);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$26, PyString.fromInterned("\n        Closes the socket.\n        "));
      var1.setlocal("close", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$19(PyFrame var1, ThreadState var2) {
      var1.setline(452);
      PyString.fromInterned("\n        Initializes the handler with a specific host address and port.\n\n        The attribute 'closeOnError' is set to 1 - which means that if\n        a socket error occurs, the socket is silently closed and then\n        reopened on the next logging call.\n        ");
      var1.setline(453);
      var1.getglobal("logging").__getattr__("Handler").__getattr__("__init__").__call__(var2, var1.getlocal(0));
      var1.setline(454);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("host", var3);
      var3 = null;
      var1.setline(455);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("port", var3);
      var3 = null;
      var1.setline(456);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("sock", var3);
      var3 = null;
      var1.setline(457);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"closeOnError", var4);
      var3 = null;
      var1.setline(458);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("retryTime", var3);
      var3 = null;
      var1.setline(462);
      PyFloat var5 = Py.newFloat(1.0);
      var1.getlocal(0).__setattr__((String)"retryStart", var5);
      var3 = null;
      var1.setline(463);
      var5 = Py.newFloat(30.0);
      var1.getlocal(0).__setattr__((String)"retryMax", var5);
      var3 = null;
      var1.setline(464);
      var5 = Py.newFloat(2.0);
      var1.getlocal(0).__setattr__((String)"retryFactor", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject makeSocket$20(PyFrame var1, ThreadState var2) {
      var1.setline(470);
      PyString.fromInterned("\n        A factory method which allows subclasses to define the precise\n        type of socket they want.\n        ");
      var1.setline(471);
      PyObject var3 = var1.getglobal("socket").__getattr__("socket").__call__(var2, var1.getglobal("socket").__getattr__("AF_INET"), var1.getglobal("socket").__getattr__("SOCK_STREAM"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(472);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("settimeout")).__nonzero__()) {
         var1.setline(473);
         var1.getlocal(2).__getattr__("settimeout").__call__(var2, var1.getlocal(1));
      }

      var1.setline(474);
      var1.getlocal(2).__getattr__("connect").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("host"), var1.getlocal(0).__getattr__("port")})));
      var1.setline(475);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject createSocket$21(PyFrame var1, ThreadState var2) {
      var1.setline(482);
      PyString.fromInterned("\n        Try to create a socket, using an exponential backoff with\n        a max retry time. Thanks to Robert Olson for the original patch\n        (SF #815911) which has been slightly refactored.\n        ");
      var1.setline(483);
      PyObject var3 = var1.getglobal("time").__getattr__("time").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(487);
      var3 = var1.getlocal(0).__getattr__("retryTime");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(488);
         PyInteger var6 = Py.newInteger(1);
         var1.setlocal(2, var6);
         var3 = null;
      } else {
         var1.setline(490);
         var3 = var1.getlocal(1);
         var10000 = var3._ge(var1.getlocal(0).__getattr__("retryTime"));
         var3 = null;
         var3 = var10000;
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(491);
      if (var1.getlocal(2).__nonzero__()) {
         try {
            var1.setline(493);
            var3 = var1.getlocal(0).__getattr__("makeSocket").__call__(var2);
            var1.getlocal(0).__setattr__("sock", var3);
            var3 = null;
            var1.setline(494);
            var3 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("retryTime", var3);
            var3 = null;
         } catch (Throwable var5) {
            PyException var7 = Py.setException(var5, var1);
            if (!var7.match(var1.getglobal("socket").__getattr__("error"))) {
               throw var7;
            }

            var1.setline(497);
            PyObject var4 = var1.getlocal(0).__getattr__("retryTime");
            var10000 = var4._is(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(498);
               var4 = var1.getlocal(0).__getattr__("retryStart");
               var1.getlocal(0).__setattr__("retryPeriod", var4);
               var4 = null;
            } else {
               var1.setline(500);
               var4 = var1.getlocal(0).__getattr__("retryPeriod")._mul(var1.getlocal(0).__getattr__("retryFactor"));
               var1.getlocal(0).__setattr__("retryPeriod", var4);
               var4 = null;
               var1.setline(501);
               var4 = var1.getlocal(0).__getattr__("retryPeriod");
               var10000 = var4._gt(var1.getlocal(0).__getattr__("retryMax"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(502);
                  var4 = var1.getlocal(0).__getattr__("retryMax");
                  var1.getlocal(0).__setattr__("retryPeriod", var4);
                  var4 = null;
               }
            }

            var1.setline(503);
            var4 = var1.getlocal(1)._add(var1.getlocal(0).__getattr__("retryPeriod"));
            var1.getlocal(0).__setattr__("retryTime", var4);
            var4 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send$22(PyFrame var1, ThreadState var2) {
      var1.setline(511);
      PyString.fromInterned("\n        Send a pickled string to the socket.\n\n        This function allows for partial sends which can happen when the\n        network is busy.\n        ");
      var1.setline(512);
      PyObject var3 = var1.getlocal(0).__getattr__("sock");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(513);
         var1.getlocal(0).__getattr__("createSocket").__call__(var2);
      }

      var1.setline(517);
      if (var1.getlocal(0).__getattr__("sock").__nonzero__()) {
         try {
            var1.setline(519);
            if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("sock"), (PyObject)PyString.fromInterned("sendall")).__nonzero__()) {
               var1.setline(520);
               var1.getlocal(0).__getattr__("sock").__getattr__("sendall").__call__(var2, var1.getlocal(1));
            } else {
               var1.setline(522);
               PyInteger var7 = Py.newInteger(0);
               var1.setlocal(2, var7);
               var3 = null;
               var1.setline(523);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
               var1.setlocal(3, var3);
               var3 = null;

               while(true) {
                  var1.setline(524);
                  var3 = var1.getlocal(3);
                  var10000 = var3._gt(Py.newInteger(0));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     break;
                  }

                  var1.setline(525);
                  var3 = var1.getlocal(0).__getattr__("sock").__getattr__("send").__call__(var2, var1.getlocal(1).__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null));
                  var1.setlocal(4, var3);
                  var3 = null;
                  var1.setline(526);
                  var3 = var1.getlocal(2)._add(var1.getlocal(4));
                  var1.setlocal(2, var3);
                  var3 = null;
                  var1.setline(527);
                  var3 = var1.getlocal(3)._sub(var1.getlocal(4));
                  var1.setlocal(3, var3);
                  var3 = null;
               }
            }
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (!var6.match(var1.getglobal("socket").__getattr__("error"))) {
               throw var6;
            }

            var1.setline(529);
            var1.getlocal(0).__getattr__("sock").__getattr__("close").__call__(var2);
            var1.setline(530);
            PyObject var4 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("sock", var4);
            var4 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject makePickle$23(PyFrame var1, ThreadState var2) {
      var1.setline(536);
      PyString.fromInterned("\n        Pickles the record in binary format with a length prefix, and\n        returns it ready for transmission across the socket.\n        ");
      var1.setline(537);
      PyObject var3 = var1.getlocal(1).__getattr__("exc_info");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(538);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(540);
         var3 = var1.getlocal(0).__getattr__("format").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(541);
         var3 = var1.getglobal("None");
         var1.getlocal(1).__setattr__("exc_info", var3);
         var3 = null;
      }

      var1.setline(545);
      var3 = var1.getglobal("dict").__call__(var2, var1.getlocal(1).__getattr__("__dict__"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(546);
      var3 = var1.getlocal(1).__getattr__("getMessage").__call__(var2);
      var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("msg"), var3);
      var3 = null;
      var1.setline(547);
      var3 = var1.getglobal("None");
      var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("args"), var3);
      var3 = null;
      var1.setline(548);
      var3 = var1.getglobal("cPickle").__getattr__("dumps").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)Py.newInteger(1));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(549);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(550);
         var3 = var1.getlocal(2);
         var1.getlocal(1).__setattr__("exc_info", var3);
         var3 = null;
      }

      var1.setline(551);
      var3 = var1.getglobal("struct").__getattr__("pack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">L"), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(5)));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(552);
      var3 = var1.getlocal(6)._add(var1.getlocal(5));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject handleError$24(PyFrame var1, ThreadState var2) {
      var1.setline(561);
      PyString.fromInterned("\n        Handle an error during logging.\n\n        An error has occurred during logging. Most likely cause -\n        connection lost. Close the socket so that we can retry on the\n        next event.\n        ");
      var1.setline(562);
      PyObject var10000 = var1.getlocal(0).__getattr__("closeOnError");
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("sock");
      }

      if (var10000.__nonzero__()) {
         var1.setline(563);
         var1.getlocal(0).__getattr__("sock").__getattr__("close").__call__(var2);
         var1.setline(564);
         PyObject var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("sock", var3);
         var3 = null;
      } else {
         var1.setline(566);
         var1.getglobal("logging").__getattr__("Handler").__getattr__("handleError").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject emit$25(PyFrame var1, ThreadState var2) {
      var1.setline(576);
      PyString.fromInterned("\n        Emit a record.\n\n        Pickles the record and writes it to the socket in binary format.\n        If there is an error with the socket, silently drop the packet.\n        If there was a problem with the socket, re-establishes the\n        socket.\n        ");

      PyException var3;
      try {
         var1.setline(578);
         PyObject var5 = var1.getlocal(0).__getattr__("makePickle").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var5);
         var3 = null;
         var1.setline(579);
         var1.getlocal(0).__getattr__("send").__call__(var2, var1.getlocal(2));
      } catch (Throwable var4) {
         var3 = Py.setException(var4, var1);
         if (var3.match(new PyTuple(new PyObject[]{var1.getglobal("KeyboardInterrupt"), var1.getglobal("SystemExit")}))) {
            var1.setline(581);
            throw Py.makeException();
         }

         var1.setline(583);
         var1.getlocal(0).__getattr__("handleError").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$26(PyFrame var1, ThreadState var2) {
      var1.setline(588);
      PyString.fromInterned("\n        Closes the socket.\n        ");
      var1.setline(589);
      var1.getlocal(0).__getattr__("acquire").__call__(var2);
      Object var3 = null;

      try {
         var1.setline(591);
         if (var1.getlocal(0).__getattr__("sock").__nonzero__()) {
            var1.setline(592);
            var1.getlocal(0).__getattr__("sock").__getattr__("close").__call__(var2);
            var1.setline(593);
            PyObject var4 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("sock", var4);
            var4 = null;
         }
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(595);
         var1.getlocal(0).__getattr__("release").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(595);
      var1.getlocal(0).__getattr__("release").__call__(var2);
      var1.setline(596);
      var1.getglobal("logging").__getattr__("Handler").__getattr__("close").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject DatagramHandler$27(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    A handler class which writes logging records, in pickle format, to\n    a datagram socket.  The pickle which is sent is that of the LogRecord's\n    attribute dictionary (__dict__), so that the receiver does not need to\n    have the logging module installed in order to process the logging event.\n\n    To unpickle the record at the receiving end into a LogRecord, use the\n    makeLogRecord function.\n\n    "));
      var1.setline(608);
      PyString.fromInterned("\n    A handler class which writes logging records, in pickle format, to\n    a datagram socket.  The pickle which is sent is that of the LogRecord's\n    attribute dictionary (__dict__), so that the receiver does not need to\n    have the logging module installed in order to process the logging event.\n\n    To unpickle the record at the receiving end into a LogRecord, use the\n    makeLogRecord function.\n\n    ");
      var1.setline(609);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$28, PyString.fromInterned("\n        Initializes the handler with a specific host address and port.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(616);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, makeSocket$29, PyString.fromInterned("\n        The factory method of SocketHandler is here overridden to create\n        a UDP socket (SOCK_DGRAM).\n        "));
      var1.setlocal("makeSocket", var4);
      var3 = null;
      var1.setline(624);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, send$30, PyString.fromInterned("\n        Send a pickled string to a socket.\n\n        This function no longer allows for partial sends which can happen\n        when the network is busy - UDP does not guarantee delivery and\n        can deliver packets out of sequence.\n        "));
      var1.setlocal("send", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$28(PyFrame var1, ThreadState var2) {
      var1.setline(612);
      PyString.fromInterned("\n        Initializes the handler with a specific host address and port.\n        ");
      var1.setline(613);
      var1.getglobal("SocketHandler").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.setline(614);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"closeOnError", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject makeSocket$29(PyFrame var1, ThreadState var2) {
      var1.setline(620);
      PyString.fromInterned("\n        The factory method of SocketHandler is here overridden to create\n        a UDP socket (SOCK_DGRAM).\n        ");
      var1.setline(621);
      PyObject var3 = var1.getglobal("socket").__getattr__("socket").__call__(var2, var1.getglobal("socket").__getattr__("AF_INET"), var1.getglobal("socket").__getattr__("SOCK_DGRAM"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(622);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject send$30(PyFrame var1, ThreadState var2) {
      var1.setline(631);
      PyString.fromInterned("\n        Send a pickled string to a socket.\n\n        This function no longer allows for partial sends which can happen\n        when the network is busy - UDP does not guarantee delivery and\n        can deliver packets out of sequence.\n        ");
      var1.setline(632);
      PyObject var3 = var1.getlocal(0).__getattr__("sock");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(633);
         var1.getlocal(0).__getattr__("createSocket").__call__(var2);
      }

      var1.setline(634);
      var1.getlocal(0).__getattr__("sock").__getattr__("sendto").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("host"), var1.getlocal(0).__getattr__("port")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject SysLogHandler$31(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    A handler class which sends formatted logging records to a syslog\n    server. Based on Sam Rushing's syslog module:\n    http://www.nightmare.com/squirl/python-ext/misc/syslog.py\n    Contributed by Nicolas Untz (after which minor refactoring changes\n    have been made).\n    "));
      var1.setline(643);
      PyString.fromInterned("\n    A handler class which sends formatted logging records to a syslog\n    server. Based on Sam Rushing's syslog module:\n    http://www.nightmare.com/squirl/python-ext/misc/syslog.py\n    Contributed by Nicolas Untz (after which minor refactoring changes\n    have been made).\n    ");
      var1.setline(655);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal("LOG_EMERG", var3);
      var3 = null;
      var1.setline(656);
      var3 = Py.newInteger(1);
      var1.setlocal("LOG_ALERT", var3);
      var3 = null;
      var1.setline(657);
      var3 = Py.newInteger(2);
      var1.setlocal("LOG_CRIT", var3);
      var3 = null;
      var1.setline(658);
      var3 = Py.newInteger(3);
      var1.setlocal("LOG_ERR", var3);
      var3 = null;
      var1.setline(659);
      var3 = Py.newInteger(4);
      var1.setlocal("LOG_WARNING", var3);
      var3 = null;
      var1.setline(660);
      var3 = Py.newInteger(5);
      var1.setlocal("LOG_NOTICE", var3);
      var3 = null;
      var1.setline(661);
      var3 = Py.newInteger(6);
      var1.setlocal("LOG_INFO", var3);
      var3 = null;
      var1.setline(662);
      var3 = Py.newInteger(7);
      var1.setlocal("LOG_DEBUG", var3);
      var3 = null;
      var1.setline(665);
      var3 = Py.newInteger(0);
      var1.setlocal("LOG_KERN", var3);
      var3 = null;
      var1.setline(666);
      var3 = Py.newInteger(1);
      var1.setlocal("LOG_USER", var3);
      var3 = null;
      var1.setline(667);
      var3 = Py.newInteger(2);
      var1.setlocal("LOG_MAIL", var3);
      var3 = null;
      var1.setline(668);
      var3 = Py.newInteger(3);
      var1.setlocal("LOG_DAEMON", var3);
      var3 = null;
      var1.setline(669);
      var3 = Py.newInteger(4);
      var1.setlocal("LOG_AUTH", var3);
      var3 = null;
      var1.setline(670);
      var3 = Py.newInteger(5);
      var1.setlocal("LOG_SYSLOG", var3);
      var3 = null;
      var1.setline(671);
      var3 = Py.newInteger(6);
      var1.setlocal("LOG_LPR", var3);
      var3 = null;
      var1.setline(672);
      var3 = Py.newInteger(7);
      var1.setlocal("LOG_NEWS", var3);
      var3 = null;
      var1.setline(673);
      var3 = Py.newInteger(8);
      var1.setlocal("LOG_UUCP", var3);
      var3 = null;
      var1.setline(674);
      var3 = Py.newInteger(9);
      var1.setlocal("LOG_CRON", var3);
      var3 = null;
      var1.setline(675);
      var3 = Py.newInteger(10);
      var1.setlocal("LOG_AUTHPRIV", var3);
      var3 = null;
      var1.setline(676);
      var3 = Py.newInteger(11);
      var1.setlocal("LOG_FTP", var3);
      var3 = null;
      var1.setline(679);
      var3 = Py.newInteger(16);
      var1.setlocal("LOG_LOCAL0", var3);
      var3 = null;
      var1.setline(680);
      var3 = Py.newInteger(17);
      var1.setlocal("LOG_LOCAL1", var3);
      var3 = null;
      var1.setline(681);
      var3 = Py.newInteger(18);
      var1.setlocal("LOG_LOCAL2", var3);
      var3 = null;
      var1.setline(682);
      var3 = Py.newInteger(19);
      var1.setlocal("LOG_LOCAL3", var3);
      var3 = null;
      var1.setline(683);
      var3 = Py.newInteger(20);
      var1.setlocal("LOG_LOCAL4", var3);
      var3 = null;
      var1.setline(684);
      var3 = Py.newInteger(21);
      var1.setlocal("LOG_LOCAL5", var3);
      var3 = null;
      var1.setline(685);
      var3 = Py.newInteger(22);
      var1.setlocal("LOG_LOCAL6", var3);
      var3 = null;
      var1.setline(686);
      var3 = Py.newInteger(23);
      var1.setlocal("LOG_LOCAL7", var3);
      var3 = null;
      var1.setline(688);
      PyDictionary var4 = new PyDictionary(new PyObject[]{PyString.fromInterned("alert"), var1.getname("LOG_ALERT"), PyString.fromInterned("crit"), var1.getname("LOG_CRIT"), PyString.fromInterned("critical"), var1.getname("LOG_CRIT"), PyString.fromInterned("debug"), var1.getname("LOG_DEBUG"), PyString.fromInterned("emerg"), var1.getname("LOG_EMERG"), PyString.fromInterned("err"), var1.getname("LOG_ERR"), PyString.fromInterned("error"), var1.getname("LOG_ERR"), PyString.fromInterned("info"), var1.getname("LOG_INFO"), PyString.fromInterned("notice"), var1.getname("LOG_NOTICE"), PyString.fromInterned("panic"), var1.getname("LOG_EMERG"), PyString.fromInterned("warn"), var1.getname("LOG_WARNING"), PyString.fromInterned("warning"), var1.getname("LOG_WARNING")});
      var1.setlocal("priority_names", var4);
      var3 = null;
      var1.setline(703);
      var4 = new PyDictionary(new PyObject[]{PyString.fromInterned("auth"), var1.getname("LOG_AUTH"), PyString.fromInterned("authpriv"), var1.getname("LOG_AUTHPRIV"), PyString.fromInterned("cron"), var1.getname("LOG_CRON"), PyString.fromInterned("daemon"), var1.getname("LOG_DAEMON"), PyString.fromInterned("ftp"), var1.getname("LOG_FTP"), PyString.fromInterned("kern"), var1.getname("LOG_KERN"), PyString.fromInterned("lpr"), var1.getname("LOG_LPR"), PyString.fromInterned("mail"), var1.getname("LOG_MAIL"), PyString.fromInterned("news"), var1.getname("LOG_NEWS"), PyString.fromInterned("security"), var1.getname("LOG_AUTH"), PyString.fromInterned("syslog"), var1.getname("LOG_SYSLOG"), PyString.fromInterned("user"), var1.getname("LOG_USER"), PyString.fromInterned("uucp"), var1.getname("LOG_UUCP"), PyString.fromInterned("local0"), var1.getname("LOG_LOCAL0"), PyString.fromInterned("local1"), var1.getname("LOG_LOCAL1"), PyString.fromInterned("local2"), var1.getname("LOG_LOCAL2"), PyString.fromInterned("local3"), var1.getname("LOG_LOCAL3"), PyString.fromInterned("local4"), var1.getname("LOG_LOCAL4"), PyString.fromInterned("local5"), var1.getname("LOG_LOCAL5"), PyString.fromInterned("local6"), var1.getname("LOG_LOCAL6"), PyString.fromInterned("local7"), var1.getname("LOG_LOCAL7")});
      var1.setlocal("facility_names", var4);
      var3 = null;
      var1.setline(731);
      var4 = new PyDictionary(new PyObject[]{PyString.fromInterned("DEBUG"), PyString.fromInterned("debug"), PyString.fromInterned("INFO"), PyString.fromInterned("info"), PyString.fromInterned("WARNING"), PyString.fromInterned("warning"), PyString.fromInterned("ERROR"), PyString.fromInterned("error"), PyString.fromInterned("CRITICAL"), PyString.fromInterned("critical")});
      var1.setlocal("priority_map", var4);
      var3 = null;
      var1.setline(739);
      PyObject[] var5 = new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("localhost"), var1.getname("SYSLOG_UDP_PORT")}), var1.getname("LOG_USER"), var1.getname("socket").__getattr__("SOCK_DGRAM")};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __init__$32, PyString.fromInterned("\n        Initialize a handler.\n\n        If address is specified as a string, a UNIX socket is used. To log to a\n        local syslogd, \"SysLogHandler(address=\"/dev/log\")\" can be used.\n        If facility is not specified, LOG_USER is used.\n        "));
      var1.setlocal("__init__", var6);
      var3 = null;
      var1.setline(764);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _connect_unixsocket$33, (PyObject)null);
      var1.setlocal("_connect_unixsocket", var6);
      var3 = null;
      var1.setline(776);
      PyString var7 = PyString.fromInterned("<%d>%s\u0000");
      var1.setlocal("log_format_string", var7);
      var3 = null;
      var1.setline(778);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, encodePriority$34, PyString.fromInterned("\n        Encode the facility and priority. You can pass in strings or\n        integers - if strings are passed, the facility_names and\n        priority_names mapping dictionaries are used to convert them to\n        integers.\n        "));
      var1.setlocal("encodePriority", var6);
      var3 = null;
      var1.setline(791);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, close$35, PyString.fromInterned("\n        Closes the socket.\n        "));
      var1.setlocal("close", var6);
      var3 = null;
      var1.setline(803);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, mapPriority$36, PyString.fromInterned("\n        Map a logging level name to a key in the priority_names map.\n        This is useful in two scenarios: when custom levels are being\n        used, and in the case where you can't do a straightforward\n        mapping by lowercasing the logging level name because of locale-\n        specific issues (see SF #1524081).\n        "));
      var1.setlocal("mapPriority", var6);
      var3 = null;
      var1.setline(813);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, emit$37, PyString.fromInterned("\n        Emit a record.\n\n        The record is formatted, and then sent to the syslog server. If\n        exception information is present, it is NOT sent to the server.\n        "));
      var1.setlocal("emit", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$32(PyFrame var1, ThreadState var2) {
      var1.setline(747);
      PyString.fromInterned("\n        Initialize a handler.\n\n        If address is specified as a string, a UNIX socket is used. To log to a\n        local syslogd, \"SysLogHandler(address=\"/dev/log\")\" can be used.\n        If facility is not specified, LOG_USER is used.\n        ");
      var1.setline(748);
      var1.getglobal("logging").__getattr__("Handler").__getattr__("__init__").__call__(var2, var1.getlocal(0));
      var1.setline(750);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("address", var3);
      var3 = null;
      var1.setline(751);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("facility", var3);
      var3 = null;
      var1.setline(752);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("socktype", var3);
      var3 = null;
      var1.setline(754);
      PyInteger var4;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__nonzero__()) {
         var1.setline(755);
         var4 = Py.newInteger(1);
         var1.getlocal(0).__setattr__((String)"unixsocket", var4);
         var3 = null;
         var1.setline(756);
         var1.getlocal(0).__getattr__("_connect_unixsocket").__call__(var2, var1.getlocal(1));
      } else {
         var1.setline(758);
         var4 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"unixsocket", var4);
         var3 = null;
         var1.setline(759);
         var3 = var1.getglobal("socket").__getattr__("socket").__call__(var2, var1.getglobal("socket").__getattr__("AF_INET"), var1.getlocal(3));
         var1.getlocal(0).__setattr__("socket", var3);
         var3 = null;
         var1.setline(760);
         var3 = var1.getlocal(3);
         PyObject var10000 = var3._eq(var1.getglobal("socket").__getattr__("SOCK_STREAM"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(761);
            var1.getlocal(0).__getattr__("socket").__getattr__("connect").__call__(var2, var1.getlocal(1));
         }
      }

      var1.setline(762);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("formatter", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _connect_unixsocket$33(PyFrame var1, ThreadState var2) {
      var1.setline(765);
      PyObject var3 = var1.getglobal("socket").__getattr__("socket").__call__(var2, var1.getglobal("socket").__getattr__("AF_UNIX"), var1.getlocal(0).__getattr__("socktype"));
      var1.getlocal(0).__setattr__("socket", var3);
      var3 = null;

      try {
         var1.setline(767);
         var1.getlocal(0).__getattr__("socket").__getattr__("connect").__call__(var2, var1.getlocal(1));
      } catch (Throwable var4) {
         PyException var5 = Py.setException(var4, var1);
         if (var5.match(var1.getglobal("socket").__getattr__("error"))) {
            var1.setline(769);
            var1.getlocal(0).__getattr__("socket").__getattr__("close").__call__(var2);
            var1.setline(770);
            throw Py.makeException();
         }

         throw var5;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject encodePriority$34(PyFrame var1, ThreadState var2) {
      var1.setline(784);
      PyString.fromInterned("\n        Encode the facility and priority. You can pass in strings or\n        integers - if strings are passed, the facility_names and\n        priority_names mapping dictionaries are used to convert them to\n        integers.\n        ");
      var1.setline(785);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__nonzero__()) {
         var1.setline(786);
         var3 = var1.getlocal(0).__getattr__("facility_names").__getitem__(var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(787);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("basestring")).__nonzero__()) {
         var1.setline(788);
         var3 = var1.getlocal(0).__getattr__("priority_names").__getitem__(var1.getlocal(2));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(789);
      var3 = var1.getlocal(1)._lshift(Py.newInteger(3))._or(var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject close$35(PyFrame var1, ThreadState var2) {
      var1.setline(794);
      PyString.fromInterned("\n        Closes the socket.\n        ");
      var1.setline(795);
      var1.getlocal(0).__getattr__("acquire").__call__(var2);
      Object var3 = null;

      try {
         var1.setline(797);
         if (var1.getlocal(0).__getattr__("unixsocket").__nonzero__()) {
            var1.setline(798);
            var1.getlocal(0).__getattr__("socket").__getattr__("close").__call__(var2);
         }
      } catch (Throwable var4) {
         Py.addTraceback(var4, var1);
         var1.setline(800);
         var1.getlocal(0).__getattr__("release").__call__(var2);
         throw (Throwable)var4;
      }

      var1.setline(800);
      var1.getlocal(0).__getattr__("release").__call__(var2);
      var1.setline(801);
      var1.getglobal("logging").__getattr__("Handler").__getattr__("close").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject mapPriority$36(PyFrame var1, ThreadState var2) {
      var1.setline(810);
      PyString.fromInterned("\n        Map a logging level name to a key in the priority_names map.\n        This is useful in two scenarios: when custom levels are being\n        used, and in the case where you can't do a straightforward\n        mapping by lowercasing the logging level name because of locale-\n        specific issues (see SF #1524081).\n        ");
      var1.setline(811);
      PyObject var3 = var1.getlocal(0).__getattr__("priority_map").__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("warning"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject emit$37(PyFrame var1, ThreadState var2) {
      var1.setline(819);
      PyString.fromInterned("\n        Emit a record.\n\n        The record is formatted, and then sent to the syslog server. If\n        exception information is present, it is NOT sent to the server.\n        ");
      var1.setline(820);
      PyObject var3 = var1.getlocal(0).__getattr__("format").__call__(var2, var1.getlocal(1))._add(PyString.fromInterned("\u0000"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(824);
      PyString.fromInterned("\n        We need to convert record level to lowercase, maybe this will\n        change in the future.\n        ");
      var1.setline(825);
      var3 = PyString.fromInterned("<%d>")._mod(var1.getlocal(0).__getattr__("encodePriority").__call__(var2, var1.getlocal(0).__getattr__("facility"), var1.getlocal(0).__getattr__("mapPriority").__call__(var2, var1.getlocal(1).__getattr__("levelname"))));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(828);
      var3 = var1.getglobal("type").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._is(var1.getglobal("unicode"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(829);
         var3 = var1.getlocal(2).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf-8"));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(830);
      var3 = var1.getlocal(3)._add(var1.getlocal(2));
      var1.setlocal(2, var3);
      var3 = null;

      PyException var6;
      try {
         var1.setline(832);
         if (var1.getlocal(0).__getattr__("unixsocket").__nonzero__()) {
            try {
               var1.setline(834);
               var1.getlocal(0).__getattr__("socket").__getattr__("send").__call__(var2, var1.getlocal(2));
            } catch (Throwable var4) {
               var6 = Py.setException(var4, var1);
               if (!var6.match(var1.getglobal("socket").__getattr__("error"))) {
                  throw var6;
               }

               var1.setline(836);
               var1.getlocal(0).__getattr__("_connect_unixsocket").__call__(var2, var1.getlocal(0).__getattr__("address"));
               var1.setline(837);
               var1.getlocal(0).__getattr__("socket").__getattr__("send").__call__(var2, var1.getlocal(2));
            }
         } else {
            var1.setline(838);
            var3 = var1.getlocal(0).__getattr__("socktype");
            var10000 = var3._eq(var1.getglobal("socket").__getattr__("SOCK_DGRAM"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(839);
               var1.getlocal(0).__getattr__("socket").__getattr__("sendto").__call__(var2, var1.getlocal(2), var1.getlocal(0).__getattr__("address"));
            } else {
               var1.setline(841);
               var1.getlocal(0).__getattr__("socket").__getattr__("sendall").__call__(var2, var1.getlocal(2));
            }
         }
      } catch (Throwable var5) {
         var6 = Py.setException(var5, var1);
         if (var6.match(new PyTuple(new PyObject[]{var1.getglobal("KeyboardInterrupt"), var1.getglobal("SystemExit")}))) {
            var1.setline(843);
            throw Py.makeException();
         }

         var1.setline(845);
         var1.getlocal(0).__getattr__("handleError").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject SMTPHandler$38(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    A handler class which sends an SMTP email for each logging event.\n    "));
      var1.setline(850);
      PyString.fromInterned("\n    A handler class which sends an SMTP email for each logging event.\n    ");
      var1.setline(851);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$39, PyString.fromInterned("\n        Initialize the handler.\n\n        Initialize the instance with the from and to addresses and subject\n        line of the email. To specify a non-standard SMTP port, use the\n        (host, port) tuple format for the mailhost argument. To specify\n        authentication credentials, supply a (username, password) tuple\n        for the credentials argument. To specify the use of a secure\n        protocol (TLS), pass in a tuple for the secure argument. This will\n        only be used when authentication credentials are supplied. The tuple\n        will be either an empty tuple, or a single-value tuple with the name\n        of a keyfile, or a 2-value tuple with the names of the keyfile and\n        certificate file. (This tuple is passed to the `starttls` method).\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(884);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getSubject$40, PyString.fromInterned("\n        Determine the subject for the email.\n\n        If you want to specify a subject line which is record-dependent,\n        override this method.\n        "));
      var1.setlocal("getSubject", var4);
      var3 = null;
      var1.setline(893);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, emit$41, PyString.fromInterned("\n        Emit a record.\n\n        Format the record and send it to the specified addressees.\n        "));
      var1.setlocal("emit", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$39(PyFrame var1, ThreadState var2) {
      var1.setline(866);
      PyString.fromInterned("\n        Initialize the handler.\n\n        Initialize the instance with the from and to addresses and subject\n        line of the email. To specify a non-standard SMTP port, use the\n        (host, port) tuple format for the mailhost argument. To specify\n        authentication credentials, supply a (username, password) tuple\n        for the credentials argument. To specify the use of a secure\n        protocol (TLS), pass in a tuple for the secure argument. This will\n        only be used when authentication credentials are supplied. The tuple\n        will be either an empty tuple, or a single-value tuple with the name\n        of a keyfile, or a 2-value tuple with the names of the keyfile and\n        certificate file. (This tuple is passed to the `starttls` method).\n        ");
      var1.setline(867);
      var1.getglobal("logging").__getattr__("Handler").__getattr__("__init__").__call__(var2, var1.getlocal(0));
      var1.setline(868);
      PyObject var3;
      PyObject[] var4;
      PyObject var5;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("tuple")).__nonzero__()) {
         var1.setline(869);
         var3 = var1.getlocal(1);
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.getlocal(0).__setattr__("mailhost", var5);
         var5 = null;
         var5 = var4[1];
         var1.getlocal(0).__setattr__("mailport", var5);
         var5 = null;
         var3 = null;
      } else {
         var1.setline(871);
         PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("None")});
         var4 = Py.unpackSequence(var6, 2);
         var5 = var4[0];
         var1.getlocal(0).__setattr__("mailhost", var5);
         var5 = null;
         var5 = var4[1];
         var1.getlocal(0).__setattr__("mailport", var5);
         var5 = null;
         var3 = null;
      }

      var1.setline(872);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("tuple")).__nonzero__()) {
         var1.setline(873);
         var3 = var1.getlocal(5);
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.getlocal(0).__setattr__("username", var5);
         var5 = null;
         var5 = var4[1];
         var1.getlocal(0).__setattr__("password", var5);
         var5 = null;
         var3 = null;
      } else {
         var1.setline(875);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("username", var3);
         var3 = null;
      }

      var1.setline(876);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("fromaddr", var3);
      var3 = null;
      var1.setline(877);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("basestring")).__nonzero__()) {
         var1.setline(878);
         PyList var7 = new PyList(new PyObject[]{var1.getlocal(3)});
         var1.setlocal(3, var7);
         var3 = null;
      }

      var1.setline(879);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("toaddrs", var3);
      var3 = null;
      var1.setline(880);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("subject", var3);
      var3 = null;
      var1.setline(881);
      var3 = var1.getlocal(6);
      var1.getlocal(0).__setattr__("secure", var3);
      var3 = null;
      var1.setline(882);
      PyFloat var8 = Py.newFloat(5.0);
      var1.getlocal(0).__setattr__((String)"_timeout", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getSubject$40(PyFrame var1, ThreadState var2) {
      var1.setline(890);
      PyString.fromInterned("\n        Determine the subject for the email.\n\n        If you want to specify a subject line which is record-dependent,\n        override this method.\n        ");
      var1.setline(891);
      PyObject var3 = var1.getlocal(0).__getattr__("subject");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject emit$41(PyFrame var1, ThreadState var2) {
      var1.setline(898);
      PyString.fromInterned("\n        Emit a record.\n\n        Format the record and send it to the specified addressees.\n        ");

      PyException var3;
      try {
         var1.setline(900);
         PyObject var6 = imp.importOne("smtplib", var1, -1);
         var1.setlocal(2, var6);
         var3 = null;
         var1.setline(901);
         String[] var7 = new String[]{"formatdate"};
         PyObject[] var8 = imp.importFrom("email.utils", var7, var1, -1);
         PyObject var4 = var8[0];
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(902);
         var6 = var1.getlocal(0).__getattr__("mailport");
         var1.setlocal(4, var6);
         var3 = null;
         var1.setline(903);
         if (var1.getlocal(4).__not__().__nonzero__()) {
            var1.setline(904);
            var6 = var1.getlocal(2).__getattr__("SMTP_PORT");
            var1.setlocal(4, var6);
            var3 = null;
         }

         var1.setline(905);
         PyObject var10000 = var1.getlocal(2).__getattr__("SMTP");
         var8 = new PyObject[]{var1.getlocal(0).__getattr__("mailhost"), var1.getlocal(4), var1.getlocal(0).__getattr__("_timeout")};
         String[] var9 = new String[]{"timeout"};
         var10000 = var10000.__call__(var2, var8, var9);
         var3 = null;
         var6 = var10000;
         var1.setlocal(5, var6);
         var3 = null;
         var1.setline(906);
         var6 = var1.getlocal(0).__getattr__("format").__call__(var2, var1.getlocal(1));
         var1.setlocal(6, var6);
         var3 = null;
         var1.setline(907);
         var6 = PyString.fromInterned("From: %s\r\nTo: %s\r\nSubject: %s\r\nDate: %s\r\n\r\n%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("fromaddr"), PyString.fromInterned(",").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("toaddrs")), var1.getlocal(0).__getattr__("getSubject").__call__(var2, var1.getlocal(1)), var1.getlocal(3).__call__(var2), var1.getlocal(6)}));
         var1.setlocal(6, var6);
         var3 = null;
         var1.setline(912);
         if (var1.getlocal(0).__getattr__("username").__nonzero__()) {
            var1.setline(913);
            var6 = var1.getlocal(0).__getattr__("secure");
            var10000 = var6._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(914);
               var1.getlocal(5).__getattr__("ehlo").__call__(var2);
               var1.setline(915);
               var10000 = var1.getlocal(5).__getattr__("starttls");
               var8 = Py.EmptyObjects;
               var9 = new String[0];
               var10000._callextra(var8, var9, var1.getlocal(0).__getattr__("secure"), (PyObject)null);
               var3 = null;
               var1.setline(916);
               var1.getlocal(5).__getattr__("ehlo").__call__(var2);
            }

            var1.setline(917);
            var1.getlocal(5).__getattr__("login").__call__(var2, var1.getlocal(0).__getattr__("username"), var1.getlocal(0).__getattr__("password"));
         }

         var1.setline(918);
         var1.getlocal(5).__getattr__("sendmail").__call__(var2, var1.getlocal(0).__getattr__("fromaddr"), var1.getlocal(0).__getattr__("toaddrs"), var1.getlocal(6));
         var1.setline(919);
         var1.getlocal(5).__getattr__("quit").__call__(var2);
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(new PyTuple(new PyObject[]{var1.getglobal("KeyboardInterrupt"), var1.getglobal("SystemExit")}))) {
            var1.setline(921);
            throw Py.makeException();
         }

         var1.setline(923);
         var1.getlocal(0).__getattr__("handleError").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject NTEventLogHandler$42(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    A handler class which sends events to the NT Event Log. Adds a\n    registry entry for the specified application name. If no dllname is\n    provided, win32service.pyd (which contains some basic message\n    placeholders) is used. Note that use of these placeholders will make\n    your event logs big, as the entire message source is held in the log.\n    If you want slimmer logs, you have to pass in the name of your own DLL\n    which contains the message definitions you want to use in the event log.\n    "));
      var1.setline(934);
      PyString.fromInterned("\n    A handler class which sends events to the NT Event Log. Adds a\n    registry entry for the specified application name. If no dllname is\n    provided, win32service.pyd (which contains some basic message\n    placeholders) is used. Note that use of these placeholders will make\n    your event logs big, as the entire message source is held in the log.\n    If you want slimmer logs, you have to pass in the name of your own DLL\n    which contains the message definitions you want to use in the event log.\n    ");
      var1.setline(935);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), PyString.fromInterned("Application")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$43, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(961);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getMessageID$44, PyString.fromInterned("\n        Return the message ID for the event record. If you are using your\n        own messages, you could do this by having the msg passed to the\n        logger being an ID rather than a formatting string. Then, in here,\n        you could use a dictionary lookup to get the message ID. This\n        version returns 1, which is the base message ID in win32service.pyd.\n        "));
      var1.setlocal("getMessageID", var4);
      var3 = null;
      var1.setline(971);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getEventCategory$45, PyString.fromInterned("\n        Return the event category for the record.\n\n        Override this if you want to specify your own categories. This version\n        returns 0.\n        "));
      var1.setlocal("getEventCategory", var4);
      var3 = null;
      var1.setline(980);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getEventType$46, PyString.fromInterned("\n        Return the event type for the record.\n\n        Override this if you want to specify your own types. This version does\n        a mapping using the handler's typemap attribute, which is set up in\n        __init__() to a dictionary which contains mappings for DEBUG, INFO,\n        WARNING, ERROR and CRITICAL. If you are using your own levels you will\n        either need to override this method or place a suitable dictionary in\n        the handler's typemap attribute.\n        "));
      var1.setlocal("getEventType", var4);
      var3 = null;
      var1.setline(993);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, emit$47, PyString.fromInterned("\n        Emit a record.\n\n        Determine the message ID, event category and event type. Then\n        log the message in the NT event log.\n        "));
      var1.setlocal("emit", var4);
      var3 = null;
      var1.setline(1012);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$48, PyString.fromInterned("\n        Clean up this handler.\n\n        You can remove the application name from the registry as a\n        source of event log entries. However, if you do this, you will\n        not be able to see the events as you intended in the Event Log\n        Viewer - it needs to be able to access the registry to get the\n        DLL name.\n        "));
      var1.setlocal("close", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$43(PyFrame var1, ThreadState var2) {
      var1.setline(936);
      var1.getglobal("logging").__getattr__("Handler").__getattr__("__init__").__call__(var2, var1.getlocal(0));

      PyException var3;
      try {
         var1.setline(938);
         PyObject var6 = imp.importOne("win32evtlogutil", var1, -1);
         var1.setlocal(4, var6);
         var3 = null;
         var6 = imp.importOne("win32evtlog", var1, -1);
         var1.setlocal(5, var6);
         var3 = null;
         var1.setline(939);
         var6 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("appname", var6);
         var3 = null;
         var1.setline(940);
         var6 = var1.getlocal(4);
         var1.getlocal(0).__setattr__("_welu", var6);
         var3 = null;
         var1.setline(941);
         if (var1.getlocal(2).__not__().__nonzero__()) {
            var1.setline(942);
            var6 = var1.getglobal("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getlocal(0).__getattr__("_welu").__getattr__("__file__"));
            var1.setlocal(2, var6);
            var3 = null;
            var1.setline(943);
            var6 = var1.getglobal("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(0)));
            var1.setlocal(2, var6);
            var3 = null;
            var1.setline(944);
            var6 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(Py.newInteger(0)), (PyObject)PyString.fromInterned("win32service.pyd"));
            var1.setlocal(2, var6);
            var3 = null;
         }

         var1.setline(945);
         var6 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("dllname", var6);
         var3 = null;
         var1.setline(946);
         var6 = var1.getlocal(3);
         var1.getlocal(0).__setattr__("logtype", var6);
         var3 = null;
         var1.setline(947);
         var1.getlocal(0).__getattr__("_welu").__getattr__("AddSourceToRegistry").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
         var1.setline(948);
         var6 = var1.getlocal(5).__getattr__("EVENTLOG_ERROR_TYPE");
         var1.getlocal(0).__setattr__("deftype", var6);
         var3 = null;
         var1.setline(949);
         PyDictionary var7 = new PyDictionary(new PyObject[]{var1.getglobal("logging").__getattr__("DEBUG"), var1.getlocal(5).__getattr__("EVENTLOG_INFORMATION_TYPE"), var1.getglobal("logging").__getattr__("INFO"), var1.getlocal(5).__getattr__("EVENTLOG_INFORMATION_TYPE"), var1.getglobal("logging").__getattr__("WARNING"), var1.getlocal(5).__getattr__("EVENTLOG_WARNING_TYPE"), var1.getglobal("logging").__getattr__("ERROR"), var1.getlocal(5).__getattr__("EVENTLOG_ERROR_TYPE"), var1.getglobal("logging").__getattr__("CRITICAL"), var1.getlocal(5).__getattr__("EVENTLOG_ERROR_TYPE")});
         var1.getlocal(0).__setattr__((String)"typemap", var7);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (!var3.match(var1.getglobal("ImportError"))) {
            throw var3;
         }

         var1.setline(957);
         Py.println(PyString.fromInterned("The Python Win32 extensions for NT (service, event logging) appear not to be available."));
         var1.setline(959);
         PyObject var4 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("_welu", var4);
         var4 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getMessageID$44(PyFrame var1, ThreadState var2) {
      var1.setline(968);
      PyString.fromInterned("\n        Return the message ID for the event record. If you are using your\n        own messages, you could do this by having the msg passed to the\n        logger being an ID rather than a formatting string. Then, in here,\n        you could use a dictionary lookup to get the message ID. This\n        version returns 1, which is the base message ID in win32service.pyd.\n        ");
      var1.setline(969);
      PyInteger var3 = Py.newInteger(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getEventCategory$45(PyFrame var1, ThreadState var2) {
      var1.setline(977);
      PyString.fromInterned("\n        Return the event category for the record.\n\n        Override this if you want to specify your own categories. This version\n        returns 0.\n        ");
      var1.setline(978);
      PyInteger var3 = Py.newInteger(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getEventType$46(PyFrame var1, ThreadState var2) {
      var1.setline(990);
      PyString.fromInterned("\n        Return the event type for the record.\n\n        Override this if you want to specify your own types. This version does\n        a mapping using the handler's typemap attribute, which is set up in\n        __init__() to a dictionary which contains mappings for DEBUG, INFO,\n        WARNING, ERROR and CRITICAL. If you are using your own levels you will\n        either need to override this method or place a suitable dictionary in\n        the handler's typemap attribute.\n        ");
      var1.setline(991);
      PyObject var3 = var1.getlocal(0).__getattr__("typemap").__getattr__("get").__call__(var2, var1.getlocal(1).__getattr__("levelno"), var1.getlocal(0).__getattr__("deftype"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject emit$47(PyFrame var1, ThreadState var2) {
      var1.setline(999);
      PyString.fromInterned("\n        Emit a record.\n\n        Determine the message ID, event category and event type. Then\n        log the message in the NT event log.\n        ");
      var1.setline(1000);
      if (var1.getlocal(0).__getattr__("_welu").__nonzero__()) {
         PyException var3;
         try {
            var1.setline(1002);
            PyObject var5 = var1.getlocal(0).__getattr__("getMessageID").__call__(var2, var1.getlocal(1));
            var1.setlocal(2, var5);
            var3 = null;
            var1.setline(1003);
            var5 = var1.getlocal(0).__getattr__("getEventCategory").__call__(var2, var1.getlocal(1));
            var1.setlocal(3, var5);
            var3 = null;
            var1.setline(1004);
            var5 = var1.getlocal(0).__getattr__("getEventType").__call__(var2, var1.getlocal(1));
            var1.setlocal(4, var5);
            var3 = null;
            var1.setline(1005);
            var5 = var1.getlocal(0).__getattr__("format").__call__(var2, var1.getlocal(1));
            var1.setlocal(5, var5);
            var3 = null;
            var1.setline(1006);
            PyObject var10000 = var1.getlocal(0).__getattr__("_welu").__getattr__("ReportEvent");
            PyObject[] var6 = new PyObject[]{var1.getlocal(0).__getattr__("appname"), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), new PyList(new PyObject[]{var1.getlocal(5)})};
            var10000.__call__(var2, var6);
         } catch (Throwable var4) {
            var3 = Py.setException(var4, var1);
            if (var3.match(new PyTuple(new PyObject[]{var1.getglobal("KeyboardInterrupt"), var1.getglobal("SystemExit")}))) {
               var1.setline(1008);
               throw Py.makeException();
            }

            var1.setline(1010);
            var1.getlocal(0).__getattr__("handleError").__call__(var2, var1.getlocal(1));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$48(PyFrame var1, ThreadState var2) {
      var1.setline(1021);
      PyString.fromInterned("\n        Clean up this handler.\n\n        You can remove the application name from the registry as a\n        source of event log entries. However, if you do this, you will\n        not be able to see the events as you intended in the Event Log\n        Viewer - it needs to be able to access the registry to get the\n        DLL name.\n        ");
      var1.setline(1023);
      var1.getglobal("logging").__getattr__("Handler").__getattr__("close").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject HTTPHandler$49(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    A class which sends records to a Web server, using either GET or\n    POST semantics.\n    "));
      var1.setline(1029);
      PyString.fromInterned("\n    A class which sends records to a Web server, using either GET or\n    POST semantics.\n    ");
      var1.setline(1030);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("GET")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$50, PyString.fromInterned("\n        Initialize the instance with the host, the request URL, and the method\n        (\"GET\" or \"POST\")\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1043);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, mapLogRecord$51, PyString.fromInterned("\n        Default implementation of mapping the log record into a dict\n        that is sent as the CGI data. Overwrite in your class.\n        Contributed by Franz  Glasner.\n        "));
      var1.setlocal("mapLogRecord", var4);
      var3 = null;
      var1.setline(1051);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, emit$52, PyString.fromInterned("\n        Emit a record.\n\n        Send the record to the Web server as a percent-encoded dictionary\n        "));
      var1.setlocal("emit", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$50(PyFrame var1, ThreadState var2) {
      var1.setline(1034);
      PyString.fromInterned("\n        Initialize the instance with the host, the request URL, and the method\n        (\"GET\" or \"POST\")\n        ");
      var1.setline(1035);
      var1.getglobal("logging").__getattr__("Handler").__getattr__("__init__").__call__(var2, var1.getlocal(0));
      var1.setline(1036);
      PyObject var3 = var1.getlocal(3).__getattr__("upper").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1037);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._notin(new PyList(new PyObject[]{PyString.fromInterned("GET"), PyString.fromInterned("POST")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1038);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("method must be GET or POST")));
      } else {
         var1.setline(1039);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("host", var3);
         var3 = null;
         var1.setline(1040);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("url", var3);
         var3 = null;
         var1.setline(1041);
         var3 = var1.getlocal(3);
         var1.getlocal(0).__setattr__("method", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject mapLogRecord$51(PyFrame var1, ThreadState var2) {
      var1.setline(1048);
      PyString.fromInterned("\n        Default implementation of mapping the log record into a dict\n        that is sent as the CGI data. Overwrite in your class.\n        Contributed by Franz  Glasner.\n        ");
      var1.setline(1049);
      PyObject var3 = var1.getlocal(1).__getattr__("__dict__");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject emit$52(PyFrame var1, ThreadState var2) {
      var1.setline(1056);
      PyString.fromInterned("\n        Emit a record.\n\n        Send the record to the Web server as a percent-encoded dictionary\n        ");

      PyException var3;
      try {
         var1.setline(1058);
         PyObject var5 = imp.importOne("httplib", var1, -1);
         var1.setlocal(2, var5);
         var3 = null;
         var5 = imp.importOne("urllib", var1, -1);
         var1.setlocal(3, var5);
         var3 = null;
         var1.setline(1059);
         var5 = var1.getlocal(0).__getattr__("host");
         var1.setlocal(4, var5);
         var3 = null;
         var1.setline(1060);
         var5 = var1.getlocal(2).__getattr__("HTTP").__call__(var2, var1.getlocal(4));
         var1.setlocal(5, var5);
         var3 = null;
         var1.setline(1061);
         var5 = var1.getlocal(0).__getattr__("url");
         var1.setlocal(6, var5);
         var3 = null;
         var1.setline(1062);
         var5 = var1.getlocal(3).__getattr__("urlencode").__call__(var2, var1.getlocal(0).__getattr__("mapLogRecord").__call__(var2, var1.getlocal(1)));
         var1.setlocal(7, var5);
         var3 = null;
         var1.setline(1063);
         var5 = var1.getlocal(0).__getattr__("method");
         PyObject var10000 = var5._eq(PyString.fromInterned("GET"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1064);
            var5 = var1.getlocal(6).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("?"));
            var10000 = var5._ge(Py.newInteger(0));
            var3 = null;
            PyString var6;
            if (var10000.__nonzero__()) {
               var1.setline(1065);
               var6 = PyString.fromInterned("&");
               var1.setlocal(8, var6);
               var3 = null;
            } else {
               var1.setline(1067);
               var6 = PyString.fromInterned("?");
               var1.setlocal(8, var6);
               var3 = null;
            }

            var1.setline(1068);
            var5 = var1.getlocal(6)._add(PyString.fromInterned("%c%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(7)})));
            var1.setlocal(6, var5);
            var3 = null;
         }

         var1.setline(1069);
         var1.getlocal(5).__getattr__("putrequest").__call__(var2, var1.getlocal(0).__getattr__("method"), var1.getlocal(6));
         var1.setline(1072);
         var5 = var1.getlocal(4).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
         var1.setlocal(9, var5);
         var3 = null;
         var1.setline(1073);
         var5 = var1.getlocal(9);
         var10000 = var5._ge(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1074);
            var5 = var1.getlocal(4).__getslice__((PyObject)null, var1.getlocal(9), (PyObject)null);
            var1.setlocal(4, var5);
            var3 = null;
         }

         var1.setline(1075);
         var1.getlocal(5).__getattr__("putheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Host"), (PyObject)var1.getlocal(4));
         var1.setline(1076);
         var5 = var1.getlocal(0).__getattr__("method");
         var10000 = var5._eq(PyString.fromInterned("POST"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1077);
            var1.getlocal(5).__getattr__("putheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-type"), (PyObject)PyString.fromInterned("application/x-www-form-urlencoded"));
            var1.setline(1079);
            var1.getlocal(5).__getattr__("putheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-length"), (PyObject)var1.getglobal("str").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(7))));
         }

         var1.setline(1080);
         var10000 = var1.getlocal(5).__getattr__("endheaders");
         var1.setline(1080);
         var5 = var1.getlocal(0).__getattr__("method");
         PyObject var10002 = var5._eq(PyString.fromInterned("POST"));
         var3 = null;
         var10000.__call__(var2, var10002.__nonzero__() ? var1.getlocal(7) : var1.getglobal("None"));
         var1.setline(1081);
         var1.getlocal(5).__getattr__("getreply").__call__(var2);
      } catch (Throwable var4) {
         var3 = Py.setException(var4, var1);
         if (var3.match(new PyTuple(new PyObject[]{var1.getglobal("KeyboardInterrupt"), var1.getglobal("SystemExit")}))) {
            var1.setline(1083);
            throw Py.makeException();
         }

         var1.setline(1085);
         var1.getlocal(0).__getattr__("handleError").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BufferingHandler$53(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n  A handler class which buffers logging records in memory. Whenever each\n  record is added to the buffer, a check is made to see if the buffer should\n  be flushed. If it should, then flush() is expected to do what's needed.\n    "));
      var1.setline(1092);
      PyString.fromInterned("\n  A handler class which buffers logging records in memory. Whenever each\n  record is added to the buffer, a check is made to see if the buffer should\n  be flushed. If it should, then flush() is expected to do what's needed.\n    ");
      var1.setline(1093);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$54, PyString.fromInterned("\n        Initialize the handler with the buffer size.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1101);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, shouldFlush$55, PyString.fromInterned("\n        Should the handler flush its buffer?\n\n        Returns true if the buffer is up to capacity. This method can be\n        overridden to implement custom flushing strategies.\n        "));
      var1.setlocal("shouldFlush", var4);
      var3 = null;
      var1.setline(1110);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, emit$56, PyString.fromInterned("\n        Emit a record.\n\n        Append the record. If shouldFlush() tells us to, call flush() to process\n        the buffer.\n        "));
      var1.setlocal("emit", var4);
      var3 = null;
      var1.setline(1121);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, flush$57, PyString.fromInterned("\n        Override to implement custom flushing behaviour.\n\n        This version just zaps the buffer to empty.\n        "));
      var1.setlocal("flush", var4);
      var3 = null;
      var1.setline(1133);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$58, PyString.fromInterned("\n        Close the handler.\n\n        This version just flushes and chains to the parent class' close().\n        "));
      var1.setlocal("close", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$54(PyFrame var1, ThreadState var2) {
      var1.setline(1096);
      PyString.fromInterned("\n        Initialize the handler with the buffer size.\n        ");
      var1.setline(1097);
      var1.getglobal("logging").__getattr__("Handler").__getattr__("__init__").__call__(var2, var1.getlocal(0));
      var1.setline(1098);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("capacity", var3);
      var3 = null;
      var1.setline(1099);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"buffer", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject shouldFlush$55(PyFrame var1, ThreadState var2) {
      var1.setline(1107);
      PyString.fromInterned("\n        Should the handler flush its buffer?\n\n        Returns true if the buffer is up to capacity. This method can be\n        overridden to implement custom flushing strategies.\n        ");
      var1.setline(1108);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("buffer"));
      PyObject var10000 = var3._ge(var1.getlocal(0).__getattr__("capacity"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject emit$56(PyFrame var1, ThreadState var2) {
      var1.setline(1116);
      PyString.fromInterned("\n        Emit a record.\n\n        Append the record. If shouldFlush() tells us to, call flush() to process\n        the buffer.\n        ");
      var1.setline(1117);
      var1.getlocal(0).__getattr__("buffer").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.setline(1118);
      if (var1.getlocal(0).__getattr__("shouldFlush").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(1119);
         var1.getlocal(0).__getattr__("flush").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject flush$57(PyFrame var1, ThreadState var2) {
      var1.setline(1126);
      PyString.fromInterned("\n        Override to implement custom flushing behaviour.\n\n        This version just zaps the buffer to empty.\n        ");
      var1.setline(1127);
      var1.getlocal(0).__getattr__("acquire").__call__(var2);
      Object var3 = null;

      try {
         var1.setline(1129);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"buffer", var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(1131);
         var1.getlocal(0).__getattr__("release").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(1131);
      var1.getlocal(0).__getattr__("release").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$58(PyFrame var1, ThreadState var2) {
      var1.setline(1138);
      PyString.fromInterned("\n        Close the handler.\n\n        This version just flushes and chains to the parent class' close().\n        ");
      var1.setline(1139);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(1140);
      var1.getglobal("logging").__getattr__("Handler").__getattr__("close").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MemoryHandler$59(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    A handler class which buffers logging records in memory, periodically\n    flushing them to a target handler. Flushing occurs whenever the buffer\n    is full, or when an event of a certain severity or greater is seen.\n    "));
      var1.setline(1147);
      PyString.fromInterned("\n    A handler class which buffers logging records in memory, periodically\n    flushing them to a target handler. Flushing occurs whenever the buffer\n    is full, or when an event of a certain severity or greater is seen.\n    ");
      var1.setline(1148);
      PyObject[] var3 = new PyObject[]{var1.getname("logging").__getattr__("ERROR"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$60, PyString.fromInterned("\n        Initialize the handler with the buffer size, the level at which\n        flushing should occur and an optional target.\n\n        Note that without a target being set either here or via setTarget(),\n        a MemoryHandler is no use to anyone!\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1160);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, shouldFlush$61, PyString.fromInterned("\n        Check for buffer full or a record at the flushLevel or higher.\n        "));
      var1.setlocal("shouldFlush", var4);
      var3 = null;
      var1.setline(1167);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setTarget$62, PyString.fromInterned("\n        Set the target handler for this handler.\n        "));
      var1.setlocal("setTarget", var4);
      var3 = null;
      var1.setline(1173);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, flush$63, PyString.fromInterned("\n        For a MemoryHandler, flushing means just sending the buffered\n        records to the target, if there is one. Override if you want\n        different behaviour.\n        "));
      var1.setlocal("flush", var4);
      var3 = null;
      var1.setline(1188);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$64, PyString.fromInterned("\n        Flush, set the target to None and lose the buffer.\n        "));
      var1.setlocal("close", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$60(PyFrame var1, ThreadState var2) {
      var1.setline(1155);
      PyString.fromInterned("\n        Initialize the handler with the buffer size, the level at which\n        flushing should occur and an optional target.\n\n        Note that without a target being set either here or via setTarget(),\n        a MemoryHandler is no use to anyone!\n        ");
      var1.setline(1156);
      var1.getglobal("BufferingHandler").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(1157);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("flushLevel", var3);
      var3 = null;
      var1.setline(1158);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("target", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject shouldFlush$61(PyFrame var1, ThreadState var2) {
      var1.setline(1163);
      PyString.fromInterned("\n        Check for buffer full or a record at the flushLevel or higher.\n        ");
      var1.setline(1164);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("buffer"));
      PyObject var10000 = var3._ge(var1.getlocal(0).__getattr__("capacity"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(1).__getattr__("levelno");
         var10000 = var3._ge(var1.getlocal(0).__getattr__("flushLevel"));
         var3 = null;
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setTarget$62(PyFrame var1, ThreadState var2) {
      var1.setline(1170);
      PyString.fromInterned("\n        Set the target handler for this handler.\n        ");
      var1.setline(1171);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("target", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject flush$63(PyFrame var1, ThreadState var2) {
      var1.setline(1178);
      PyString.fromInterned("\n        For a MemoryHandler, flushing means just sending the buffered\n        records to the target, if there is one. Override if you want\n        different behaviour.\n        ");
      var1.setline(1179);
      var1.getlocal(0).__getattr__("acquire").__call__(var2);
      Object var3 = null;

      try {
         var1.setline(1181);
         if (var1.getlocal(0).__getattr__("target").__nonzero__()) {
            var1.setline(1182);
            PyObject var4 = var1.getlocal(0).__getattr__("buffer").__iter__();

            while(true) {
               var1.setline(1182);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(1184);
                  PyList var7 = new PyList(Py.EmptyObjects);
                  var1.getlocal(0).__setattr__((String)"buffer", var7);
                  var4 = null;
                  break;
               }

               var1.setlocal(1, var5);
               var1.setline(1183);
               var1.getlocal(0).__getattr__("target").__getattr__("handle").__call__(var2, var1.getlocal(1));
            }
         }
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(1186);
         var1.getlocal(0).__getattr__("release").__call__(var2);
         throw (Throwable)var6;
      }

      var1.setline(1186);
      var1.getlocal(0).__getattr__("release").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$64(PyFrame var1, ThreadState var2) {
      var1.setline(1191);
      PyString.fromInterned("\n        Flush, set the target to None and lose the buffer.\n        ");
      var1.setline(1192);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(1193);
      var1.getlocal(0).__getattr__("acquire").__call__(var2);
      Object var3 = null;

      try {
         var1.setline(1195);
         PyObject var4 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("target", var4);
         var4 = null;
         var1.setline(1196);
         var1.getglobal("BufferingHandler").__getattr__("close").__call__(var2, var1.getlocal(0));
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(1198);
         var1.getlocal(0).__getattr__("release").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(1198);
      var1.getlocal(0).__getattr__("release").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public handlers$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      BaseRotatingHandler$1 = Py.newCode(0, var2, var1, "BaseRotatingHandler", 52, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "filename", "mode", "encoding", "delay"};
      __init__$2 = Py.newCode(5, var2, var1, "__init__", 58, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record"};
      emit$3 = Py.newCode(2, var2, var1, "emit", 68, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      RotatingFileHandler$4 = Py.newCode(0, var2, var1, "RotatingFileHandler", 84, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "filename", "mode", "maxBytes", "backupCount", "encoding", "delay"};
      __init__$5 = Py.newCode(7, var2, var1, "__init__", 89, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "sfn", "dfn"};
      doRollover$6 = Py.newCode(1, var2, var1, "doRollover", 121, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record", "msg"};
      shouldRollover$7 = Py.newCode(2, var2, var1, "shouldRollover", 144, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TimedRotatingFileHandler$8 = Py.newCode(0, var2, var1, "TimedRotatingFileHandler", 160, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "filename", "when", "interval", "backupCount", "encoding", "delay", "utc", "t"};
      __init__$9 = Py.newCode(8, var2, var1, "__init__", 168, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "currentTime", "result", "t", "currentHour", "currentMinute", "currentSecond", "r", "day", "daysToWait", "newRolloverAt", "dstNow", "dstAtRollover", "addend"};
      computeRollover$10 = Py.newCode(2, var2, var1, "computeRollover", 221, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record", "t"};
      shouldRollover$11 = Py.newCode(2, var2, var1, "shouldRollover", 281, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dirName", "baseName", "fileNames", "result", "prefix", "plen", "fileName", "suffix"};
      getFilesToDelete$12 = Py.newCode(1, var2, var1, "getFilesToDelete", 294, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "currentTime", "dstNow", "t", "timeTuple", "dstThen", "addend", "dfn", "s", "newRolloverAt", "dstAtRollover"};
      doRollover$13 = Py.newCode(1, var2, var1, "doRollover", 317, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      WatchedFileHandler$14 = Py.newCode(0, var2, var1, "WatchedFileHandler", 371, false, false, self, 14, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "filename", "mode", "encoding", "delay"};
      __init__$15 = Py.newCode(5, var2, var1, "__init__", 391, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sres"};
      _statstream$16 = Py.newCode(1, var2, var1, "_statstream", 396, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record", "sres", "err"};
      emit$17 = Py.newCode(2, var2, var1, "emit", 401, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SocketHandler$18 = Py.newCode(0, var2, var1, "SocketHandler", 432, false, false, self, 18, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "host", "port"};
      __init__$19 = Py.newCode(3, var2, var1, "__init__", 445, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "timeout", "s"};
      makeSocket$20 = Py.newCode(2, var2, var1, "makeSocket", 466, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "now", "attempt"};
      createSocket$21 = Py.newCode(1, var2, var1, "createSocket", 477, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "sentsofar", "left", "sent"};
      send$22 = Py.newCode(2, var2, var1, "send", 505, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record", "ei", "dummy", "d", "s", "slen"};
      makePickle$23 = Py.newCode(2, var2, var1, "makePickle", 532, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record"};
      handleError$24 = Py.newCode(2, var2, var1, "handleError", 554, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record", "s"};
      emit$25 = Py.newCode(2, var2, var1, "emit", 568, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$26 = Py.newCode(1, var2, var1, "close", 585, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DatagramHandler$27 = Py.newCode(0, var2, var1, "DatagramHandler", 598, false, false, self, 27, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "host", "port"};
      __init__$28 = Py.newCode(3, var2, var1, "__init__", 609, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s"};
      makeSocket$29 = Py.newCode(1, var2, var1, "makeSocket", 616, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s"};
      send$30 = Py.newCode(2, var2, var1, "send", 624, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SysLogHandler$31 = Py.newCode(0, var2, var1, "SysLogHandler", 636, false, false, self, 31, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "address", "facility", "socktype"};
      __init__$32 = Py.newCode(4, var2, var1, "__init__", 739, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "address"};
      _connect_unixsocket$33 = Py.newCode(2, var2, var1, "_connect_unixsocket", 764, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "facility", "priority"};
      encodePriority$34 = Py.newCode(3, var2, var1, "encodePriority", 778, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$35 = Py.newCode(1, var2, var1, "close", 791, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "levelName"};
      mapPriority$36 = Py.newCode(2, var2, var1, "mapPriority", 803, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record", "msg", "prio"};
      emit$37 = Py.newCode(2, var2, var1, "emit", 813, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SMTPHandler$38 = Py.newCode(0, var2, var1, "SMTPHandler", 847, false, false, self, 38, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "mailhost", "fromaddr", "toaddrs", "subject", "credentials", "secure"};
      __init__$39 = Py.newCode(7, var2, var1, "__init__", 851, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record"};
      getSubject$40 = Py.newCode(2, var2, var1, "getSubject", 884, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record", "smtplib", "formatdate", "port", "smtp", "msg"};
      emit$41 = Py.newCode(2, var2, var1, "emit", 893, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      NTEventLogHandler$42 = Py.newCode(0, var2, var1, "NTEventLogHandler", 925, false, false, self, 42, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "appname", "dllname", "logtype", "win32evtlogutil", "win32evtlog"};
      __init__$43 = Py.newCode(4, var2, var1, "__init__", 935, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record"};
      getMessageID$44 = Py.newCode(2, var2, var1, "getMessageID", 961, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record"};
      getEventCategory$45 = Py.newCode(2, var2, var1, "getEventCategory", 971, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record"};
      getEventType$46 = Py.newCode(2, var2, var1, "getEventType", 980, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record", "id", "cat", "type", "msg"};
      emit$47 = Py.newCode(2, var2, var1, "emit", 993, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$48 = Py.newCode(1, var2, var1, "close", 1012, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      HTTPHandler$49 = Py.newCode(0, var2, var1, "HTTPHandler", 1025, false, false, self, 49, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "host", "url", "method"};
      __init__$50 = Py.newCode(4, var2, var1, "__init__", 1030, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record"};
      mapLogRecord$51 = Py.newCode(2, var2, var1, "mapLogRecord", 1043, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record", "httplib", "urllib", "host", "h", "url", "data", "sep", "i"};
      emit$52 = Py.newCode(2, var2, var1, "emit", 1051, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BufferingHandler$53 = Py.newCode(0, var2, var1, "BufferingHandler", 1087, false, false, self, 53, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "capacity"};
      __init__$54 = Py.newCode(2, var2, var1, "__init__", 1093, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record"};
      shouldFlush$55 = Py.newCode(2, var2, var1, "shouldFlush", 1101, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record"};
      emit$56 = Py.newCode(2, var2, var1, "emit", 1110, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      flush$57 = Py.newCode(1, var2, var1, "flush", 1121, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$58 = Py.newCode(1, var2, var1, "close", 1133, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MemoryHandler$59 = Py.newCode(0, var2, var1, "MemoryHandler", 1142, false, false, self, 59, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "capacity", "flushLevel", "target"};
      __init__$60 = Py.newCode(4, var2, var1, "__init__", 1148, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record"};
      shouldFlush$61 = Py.newCode(2, var2, var1, "shouldFlush", 1160, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "target"};
      setTarget$62 = Py.newCode(2, var2, var1, "setTarget", 1167, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record"};
      flush$63 = Py.newCode(1, var2, var1, "flush", 1173, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$64 = Py.newCode(1, var2, var1, "close", 1188, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new handlers$py("logging/handlers$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(handlers$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.BaseRotatingHandler$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.emit$3(var2, var3);
         case 4:
            return this.RotatingFileHandler$4(var2, var3);
         case 5:
            return this.__init__$5(var2, var3);
         case 6:
            return this.doRollover$6(var2, var3);
         case 7:
            return this.shouldRollover$7(var2, var3);
         case 8:
            return this.TimedRotatingFileHandler$8(var2, var3);
         case 9:
            return this.__init__$9(var2, var3);
         case 10:
            return this.computeRollover$10(var2, var3);
         case 11:
            return this.shouldRollover$11(var2, var3);
         case 12:
            return this.getFilesToDelete$12(var2, var3);
         case 13:
            return this.doRollover$13(var2, var3);
         case 14:
            return this.WatchedFileHandler$14(var2, var3);
         case 15:
            return this.__init__$15(var2, var3);
         case 16:
            return this._statstream$16(var2, var3);
         case 17:
            return this.emit$17(var2, var3);
         case 18:
            return this.SocketHandler$18(var2, var3);
         case 19:
            return this.__init__$19(var2, var3);
         case 20:
            return this.makeSocket$20(var2, var3);
         case 21:
            return this.createSocket$21(var2, var3);
         case 22:
            return this.send$22(var2, var3);
         case 23:
            return this.makePickle$23(var2, var3);
         case 24:
            return this.handleError$24(var2, var3);
         case 25:
            return this.emit$25(var2, var3);
         case 26:
            return this.close$26(var2, var3);
         case 27:
            return this.DatagramHandler$27(var2, var3);
         case 28:
            return this.__init__$28(var2, var3);
         case 29:
            return this.makeSocket$29(var2, var3);
         case 30:
            return this.send$30(var2, var3);
         case 31:
            return this.SysLogHandler$31(var2, var3);
         case 32:
            return this.__init__$32(var2, var3);
         case 33:
            return this._connect_unixsocket$33(var2, var3);
         case 34:
            return this.encodePriority$34(var2, var3);
         case 35:
            return this.close$35(var2, var3);
         case 36:
            return this.mapPriority$36(var2, var3);
         case 37:
            return this.emit$37(var2, var3);
         case 38:
            return this.SMTPHandler$38(var2, var3);
         case 39:
            return this.__init__$39(var2, var3);
         case 40:
            return this.getSubject$40(var2, var3);
         case 41:
            return this.emit$41(var2, var3);
         case 42:
            return this.NTEventLogHandler$42(var2, var3);
         case 43:
            return this.__init__$43(var2, var3);
         case 44:
            return this.getMessageID$44(var2, var3);
         case 45:
            return this.getEventCategory$45(var2, var3);
         case 46:
            return this.getEventType$46(var2, var3);
         case 47:
            return this.emit$47(var2, var3);
         case 48:
            return this.close$48(var2, var3);
         case 49:
            return this.HTTPHandler$49(var2, var3);
         case 50:
            return this.__init__$50(var2, var3);
         case 51:
            return this.mapLogRecord$51(var2, var3);
         case 52:
            return this.emit$52(var2, var3);
         case 53:
            return this.BufferingHandler$53(var2, var3);
         case 54:
            return this.__init__$54(var2, var3);
         case 55:
            return this.shouldFlush$55(var2, var3);
         case 56:
            return this.emit$56(var2, var3);
         case 57:
            return this.flush$57(var2, var3);
         case 58:
            return this.close$58(var2, var3);
         case 59:
            return this.MemoryHandler$59(var2, var3);
         case 60:
            return this.__init__$60(var2, var3);
         case 61:
            return this.shouldFlush$61(var2, var3);
         case 62:
            return this.setTarget$62(var2, var3);
         case 63:
            return this.flush$63(var2, var3);
         case 64:
            return this.close$64(var2, var3);
         default:
            return null;
      }
   }
}
