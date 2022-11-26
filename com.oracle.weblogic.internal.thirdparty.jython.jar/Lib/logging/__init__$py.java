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
@MTime(1498849384000L)
@Filename("logging/__init__.py")
public class logging$py extends PyFunctionTable implements PyRunnable {
   static logging$py self;
   static final PyCode f$0;
   static final PyCode currentframe$1;
   static final PyCode f$2;
   static final PyCode getLevelName$3;
   static final PyCode addLevelName$4;
   static final PyCode _checkLevel$5;
   static final PyCode _acquireLock$6;
   static final PyCode _releaseLock$7;
   static final PyCode LogRecord$8;
   static final PyCode __init__$9;
   static final PyCode __str__$10;
   static final PyCode getMessage$11;
   static final PyCode makeLogRecord$12;
   static final PyCode Formatter$13;
   static final PyCode __init__$14;
   static final PyCode formatTime$15;
   static final PyCode formatException$16;
   static final PyCode usesTime$17;
   static final PyCode format$18;
   static final PyCode BufferingFormatter$19;
   static final PyCode __init__$20;
   static final PyCode formatHeader$21;
   static final PyCode formatFooter$22;
   static final PyCode format$23;
   static final PyCode Filter$24;
   static final PyCode __init__$25;
   static final PyCode filter$26;
   static final PyCode Filterer$27;
   static final PyCode __init__$28;
   static final PyCode addFilter$29;
   static final PyCode removeFilter$30;
   static final PyCode filter$31;
   static final PyCode _removeHandlerRef$32;
   static final PyCode _addHandlerRef$33;
   static final PyCode Handler$34;
   static final PyCode __init__$35;
   static final PyCode get_name$36;
   static final PyCode set_name$37;
   static final PyCode createLock$38;
   static final PyCode acquire$39;
   static final PyCode release$40;
   static final PyCode setLevel$41;
   static final PyCode format$42;
   static final PyCode emit$43;
   static final PyCode handle$44;
   static final PyCode setFormatter$45;
   static final PyCode flush$46;
   static final PyCode close$47;
   static final PyCode handleError$48;
   static final PyCode StreamHandler$49;
   static final PyCode __init__$50;
   static final PyCode flush$51;
   static final PyCode emit$52;
   static final PyCode FileHandler$53;
   static final PyCode __init__$54;
   static final PyCode close$55;
   static final PyCode _open$56;
   static final PyCode emit$57;
   static final PyCode PlaceHolder$58;
   static final PyCode __init__$59;
   static final PyCode append$60;
   static final PyCode setLoggerClass$61;
   static final PyCode getLoggerClass$62;
   static final PyCode Manager$63;
   static final PyCode __init__$64;
   static final PyCode getLogger$65;
   static final PyCode setLoggerClass$66;
   static final PyCode _fixupParents$67;
   static final PyCode _fixupChildren$68;
   static final PyCode Logger$69;
   static final PyCode __init__$70;
   static final PyCode setLevel$71;
   static final PyCode debug$72;
   static final PyCode info$73;
   static final PyCode warning$74;
   static final PyCode error$75;
   static final PyCode exception$76;
   static final PyCode critical$77;
   static final PyCode log$78;
   static final PyCode findCaller$79;
   static final PyCode makeRecord$80;
   static final PyCode _log$81;
   static final PyCode handle$82;
   static final PyCode addHandler$83;
   static final PyCode removeHandler$84;
   static final PyCode callHandlers$85;
   static final PyCode getEffectiveLevel$86;
   static final PyCode isEnabledFor$87;
   static final PyCode getChild$88;
   static final PyCode RootLogger$89;
   static final PyCode __init__$90;
   static final PyCode LoggerAdapter$91;
   static final PyCode __init__$92;
   static final PyCode process$93;
   static final PyCode debug$94;
   static final PyCode info$95;
   static final PyCode warning$96;
   static final PyCode error$97;
   static final PyCode exception$98;
   static final PyCode critical$99;
   static final PyCode log$100;
   static final PyCode isEnabledFor$101;
   static final PyCode basicConfig$102;
   static final PyCode getLogger$103;
   static final PyCode critical$104;
   static final PyCode error$105;
   static final PyCode exception$106;
   static final PyCode warning$107;
   static final PyCode info$108;
   static final PyCode debug$109;
   static final PyCode log$110;
   static final PyCode disable$111;
   static final PyCode shutdown$112;
   static final PyCode NullHandler$113;
   static final PyCode handle$114;
   static final PyCode emit$115;
   static final PyCode createLock$116;
   static final PyCode _showwarning$117;
   static final PyCode captureWarnings$118;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nLogging package for Python. Based on PEP 282 and comments thereto in\ncomp.lang.python.\n\nCopyright (C) 2001-2012 Vinay Sajip. All Rights Reserved.\n\nTo use, simply 'import logging' and log away!\n"));
      var1.setline(24);
      PyString.fromInterned("\nLogging package for Python. Based on PEP 282 and comments thereto in\ncomp.lang.python.\n\nCopyright (C) 2001-2012 Vinay Sajip. All Rights Reserved.\n\nTo use, simply 'import logging' and log away!\n");
      var1.setline(26);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var3 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var3);
      var3 = null;
      var3 = imp.importOne("cStringIO", var1, -1);
      var1.setlocal("cStringIO", var3);
      var3 = null;
      var3 = imp.importOne("traceback", var1, -1);
      var1.setlocal("traceback", var3);
      var3 = null;
      var3 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var3);
      var3 = null;
      var3 = imp.importOne("weakref", var1, -1);
      var1.setlocal("weakref", var3);
      var3 = null;
      var1.setline(28);
      PyList var8 = new PyList(new PyObject[]{PyString.fromInterned("BASIC_FORMAT"), PyString.fromInterned("BufferingFormatter"), PyString.fromInterned("CRITICAL"), PyString.fromInterned("DEBUG"), PyString.fromInterned("ERROR"), PyString.fromInterned("FATAL"), PyString.fromInterned("FileHandler"), PyString.fromInterned("Filter"), PyString.fromInterned("Formatter"), PyString.fromInterned("Handler"), PyString.fromInterned("INFO"), PyString.fromInterned("LogRecord"), PyString.fromInterned("Logger"), PyString.fromInterned("LoggerAdapter"), PyString.fromInterned("NOTSET"), PyString.fromInterned("NullHandler"), PyString.fromInterned("StreamHandler"), PyString.fromInterned("WARN"), PyString.fromInterned("WARNING"), PyString.fromInterned("addLevelName"), PyString.fromInterned("basicConfig"), PyString.fromInterned("captureWarnings"), PyString.fromInterned("critical"), PyString.fromInterned("debug"), PyString.fromInterned("disable"), PyString.fromInterned("error"), PyString.fromInterned("exception"), PyString.fromInterned("fatal"), PyString.fromInterned("getLevelName"), PyString.fromInterned("getLogger"), PyString.fromInterned("getLoggerClass"), PyString.fromInterned("info"), PyString.fromInterned("log"), PyString.fromInterned("makeLogRecord"), PyString.fromInterned("setLoggerClass"), PyString.fromInterned("warn"), PyString.fromInterned("warning")});
      var1.setlocal("__all__", var8);
      var3 = null;

      PyObject var4;
      PyException var9;
      try {
         var1.setline(37);
         var3 = imp.importOne("codecs", var1, -1);
         var1.setlocal("codecs", var3);
         var3 = null;
      } catch (Throwable var7) {
         var9 = Py.setException(var7, var1);
         if (!var9.match(var1.getname("ImportError"))) {
            throw var9;
         }

         var1.setline(39);
         var4 = var1.getname("None");
         var1.setlocal("codecs", var4);
         var4 = null;
      }

      try {
         var1.setline(42);
         var3 = imp.importOne("thread", var1, -1);
         var1.setlocal("thread", var3);
         var3 = null;
         var1.setline(43);
         var3 = imp.importOne("threading", var1, -1);
         var1.setlocal("threading", var3);
         var3 = null;
      } catch (Throwable var6) {
         var9 = Py.setException(var6, var1);
         if (!var9.match(var1.getname("ImportError"))) {
            throw var9;
         }

         var1.setline(45);
         var4 = var1.getname("None");
         var1.setlocal("thread", var4);
         var4 = null;
      }

      var1.setline(47);
      PyString var10 = PyString.fromInterned("Vinay Sajip <vinay_sajip@red-dove.com>");
      var1.setlocal("__author__", var10);
      var3 = null;
      var1.setline(48);
      var10 = PyString.fromInterned("production");
      var1.setlocal("__status__", var10);
      var3 = null;
      var1.setline(49);
      var10 = PyString.fromInterned("0.5.1.2");
      var1.setlocal("__version__", var10);
      var3 = null;
      var1.setline(50);
      var10 = PyString.fromInterned("07 February 2010");
      var1.setlocal("__date__", var10);
      var3 = null;

      try {
         var1.setline(56);
         var1.getname("unicode");
         var1.setline(57);
         var3 = var1.getname("True");
         var1.setlocal("_unicode", var3);
         var3 = null;
      } catch (Throwable var5) {
         var9 = Py.setException(var5, var1);
         if (!var9.match(var1.getname("NameError"))) {
            throw var9;
         }

         var1.setline(59);
         var4 = var1.getname("False");
         var1.setlocal("_unicode", var4);
         var4 = null;
      }

      var1.setline(65);
      if (var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("sys"), (PyObject)PyString.fromInterned("frozen")).__nonzero__()) {
         var1.setline(66);
         var3 = PyString.fromInterned("logging%s__init__%s")._mod(new PyTuple(new PyObject[]{var1.getname("os").__getattr__("sep"), var1.getname("__file__").__getslice__(Py.newInteger(-4), (PyObject)null, (PyObject)null)}));
         var1.setlocal("_srcfile", var3);
         var3 = null;
      } else {
         var1.setline(67);
         var3 = var1.getname("__file__").__getslice__(Py.newInteger(-4), (PyObject)null, (PyObject)null).__getattr__("lower").__call__(var2);
         PyObject var10000 = var3._in(new PyList(new PyObject[]{PyString.fromInterned(".pyc"), PyString.fromInterned(".pyo")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(68);
            var3 = var1.getname("__file__").__getslice__((PyObject)null, Py.newInteger(-4), (PyObject)null)._add(PyString.fromInterned(".py"));
            var1.setlocal("_srcfile", var3);
            var3 = null;
         } else {
            var1.setline(69);
            if (var1.getname("__file__").__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("$py.class")).__nonzero__()) {
               var1.setline(70);
               var3 = var1.getname("__file__").__getslice__((PyObject)null, Py.newInteger(-9), (PyObject)null)._add(PyString.fromInterned(".py"));
               var1.setlocal("_srcfile", var3);
               var3 = null;
            } else {
               var1.setline(72);
               var3 = var1.getname("__file__");
               var1.setlocal("_srcfile", var3);
               var3 = null;
            }
         }
      }

      var1.setline(73);
      var3 = var1.getname("os").__getattr__("path").__getattr__("normcase").__call__(var2, var1.getname("_srcfile"));
      var1.setlocal("_srcfile", var3);
      var3 = null;
      var1.setline(76);
      PyObject[] var11 = Py.EmptyObjects;
      PyFunction var12 = new PyFunction(var1.f_globals, var11, currentframe$1, PyString.fromInterned("Return the frame object for the caller's stack frame."));
      var1.setlocal("currentframe", var12);
      var3 = null;
      var1.setline(83);
      if (var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("sys"), (PyObject)PyString.fromInterned("_getframe")).__nonzero__()) {
         var1.setline(83);
         var1.setline(83);
         var11 = Py.EmptyObjects;
         var12 = new PyFunction(var1.f_globals, var11, f$2);
         var1.setlocal("currentframe", var12);
         var3 = null;
      }

      var1.setline(96);
      var3 = var1.getname("time").__getattr__("time").__call__(var2);
      var1.setlocal("_startTime", var3);
      var3 = null;
      var1.setline(102);
      PyInteger var13 = Py.newInteger(1);
      var1.setlocal("raiseExceptions", var13);
      var3 = null;
      var1.setline(107);
      var13 = Py.newInteger(1);
      var1.setlocal("logThreads", var13);
      var3 = null;
      var1.setline(112);
      var13 = Py.newInteger(1);
      var1.setlocal("logMultiprocessing", var13);
      var3 = null;
      var1.setline(117);
      var13 = Py.newInteger(1);
      var1.setlocal("logProcesses", var13);
      var3 = null;
      var1.setline(130);
      var13 = Py.newInteger(50);
      var1.setlocal("CRITICAL", var13);
      var3 = null;
      var1.setline(131);
      var3 = var1.getname("CRITICAL");
      var1.setlocal("FATAL", var3);
      var3 = null;
      var1.setline(132);
      var13 = Py.newInteger(40);
      var1.setlocal("ERROR", var13);
      var3 = null;
      var1.setline(133);
      var13 = Py.newInteger(30);
      var1.setlocal("WARNING", var13);
      var3 = null;
      var1.setline(134);
      var3 = var1.getname("WARNING");
      var1.setlocal("WARN", var3);
      var3 = null;
      var1.setline(135);
      var13 = Py.newInteger(20);
      var1.setlocal("INFO", var13);
      var3 = null;
      var1.setline(136);
      var13 = Py.newInteger(10);
      var1.setlocal("DEBUG", var13);
      var3 = null;
      var1.setline(137);
      var13 = Py.newInteger(0);
      var1.setlocal("NOTSET", var13);
      var3 = null;
      var1.setline(139);
      PyDictionary var14 = new PyDictionary(new PyObject[]{var1.getname("CRITICAL"), PyString.fromInterned("CRITICAL"), var1.getname("ERROR"), PyString.fromInterned("ERROR"), var1.getname("WARNING"), PyString.fromInterned("WARNING"), var1.getname("INFO"), PyString.fromInterned("INFO"), var1.getname("DEBUG"), PyString.fromInterned("DEBUG"), var1.getname("NOTSET"), PyString.fromInterned("NOTSET"), PyString.fromInterned("CRITICAL"), var1.getname("CRITICAL"), PyString.fromInterned("ERROR"), var1.getname("ERROR"), PyString.fromInterned("WARN"), var1.getname("WARNING"), PyString.fromInterned("WARNING"), var1.getname("WARNING"), PyString.fromInterned("INFO"), var1.getname("INFO"), PyString.fromInterned("DEBUG"), var1.getname("DEBUG"), PyString.fromInterned("NOTSET"), var1.getname("NOTSET")});
      var1.setlocal("_levelNames", var14);
      var3 = null;
      var1.setline(155);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, getLevelName$3, PyString.fromInterned("\n    Return the textual representation of logging level 'level'.\n\n    If the level is one of the predefined levels (CRITICAL, ERROR, WARNING,\n    INFO, DEBUG) then you get the corresponding string. If you have\n    associated levels with names using addLevelName then the name you have\n    associated with 'level' is returned.\n\n    If a numeric value corresponding to one of the defined levels is passed\n    in, the corresponding string representation is returned.\n\n    Otherwise, the string \"Level %s\" % level is returned.\n    "));
      var1.setlocal("getLevelName", var12);
      var3 = null;
      var1.setline(171);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, addLevelName$4, PyString.fromInterned("\n    Associate 'levelName' with 'level'.\n\n    This is used when converting levels to text during message formatting.\n    "));
      var1.setlocal("addLevelName", var12);
      var3 = null;
      var1.setline(184);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, _checkLevel$5, (PyObject)null);
      var1.setlocal("_checkLevel", var12);
      var3 = null;
      var1.setline(207);
      if (var1.getname("thread").__nonzero__()) {
         var1.setline(208);
         var3 = var1.getname("threading").__getattr__("RLock").__call__(var2);
         var1.setlocal("_lock", var3);
         var3 = null;
      } else {
         var1.setline(210);
         var3 = var1.getname("None");
         var1.setlocal("_lock", var3);
         var3 = null;
      }

      var1.setline(212);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, _acquireLock$6, PyString.fromInterned("\n    Acquire the module-level lock for serializing access to shared data.\n\n    This should be released with _releaseLock().\n    "));
      var1.setlocal("_acquireLock", var12);
      var3 = null;
      var1.setline(221);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, _releaseLock$7, PyString.fromInterned("\n    Release the module-level lock acquired by calling _acquireLock().\n    "));
      var1.setlocal("_releaseLock", var12);
      var3 = null;
      var1.setline(232);
      var11 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("LogRecord", var11, LogRecord$8);
      var1.setlocal("LogRecord", var4);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(333);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, makeLogRecord$12, PyString.fromInterned("\n    Make a LogRecord whose attributes are defined by the specified dictionary,\n    This function is useful for converting a logging event received over\n    a socket connection (which is sent as a dictionary) into a LogRecord\n    instance.\n    "));
      var1.setlocal("makeLogRecord", var12);
      var3 = null;
      var1.setline(348);
      var11 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("Formatter", var11, Formatter$13);
      var1.setlocal("Formatter", var4);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(494);
      var3 = var1.getname("Formatter").__call__(var2);
      var1.setlocal("_defaultFormatter", var3);
      var3 = null;
      var1.setline(496);
      var11 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("BufferingFormatter", var11, BufferingFormatter$19);
      var1.setlocal("BufferingFormatter", var4);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(538);
      var11 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("Filter", var11, Filter$24);
      var1.setlocal("Filter", var4);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(575);
      var11 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("Filterer", var11, Filterer$27);
      var1.setlocal("Filterer", var4);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(619);
      var3 = var1.getname("weakref").__getattr__("WeakValueDictionary").__call__(var2);
      var1.setlocal("_handlers", var3);
      var3 = null;
      var1.setline(620);
      var8 = new PyList(Py.EmptyObjects);
      var1.setlocal("_handlerList", var8);
      var3 = null;
      var1.setline(622);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, _removeHandlerRef$32, PyString.fromInterned("\n    Remove a handler reference from the internal cleanup list.\n    "));
      var1.setlocal("_removeHandlerRef", var12);
      var3 = null;
      var1.setline(638);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, _addHandlerRef$33, PyString.fromInterned("\n    Add a handler to the internal cleanup list using a weak reference.\n    "));
      var1.setlocal("_addHandlerRef", var12);
      var3 = null;
      var1.setline(648);
      var11 = new PyObject[]{var1.getname("Filterer")};
      var4 = Py.makeClass("Handler", var11, Handler$34);
      var1.setlocal("Handler", var4);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(812);
      var11 = new PyObject[]{var1.getname("Handler")};
      var4 = Py.makeClass("StreamHandler", var11, StreamHandler$49);
      var1.setlocal("StreamHandler", var4);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(883);
      var11 = new PyObject[]{var1.getname("StreamHandler")};
      var4 = Py.makeClass("FileHandler", var11, FileHandler$53);
      var1.setlocal("FileHandler", var4);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(947);
      var11 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("PlaceHolder", var11, PlaceHolder$58);
      var1.setlocal("PlaceHolder", var4);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(972);
      var3 = var1.getname("None");
      var1.setlocal("_loggerClass", var3);
      var3 = null;
      var1.setline(974);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, setLoggerClass$61, PyString.fromInterned("\n    Set the class to be used when instantiating a logger. The class should\n    define __init__() such that only a name argument is required, and the\n    __init__() should call Logger.__init__()\n    "));
      var1.setlocal("setLoggerClass", var12);
      var3 = null;
      var1.setline(987);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, getLoggerClass$62, PyString.fromInterned("\n    Return the class to be used when instantiating a logger.\n    "));
      var1.setlocal("getLoggerClass", var12);
      var3 = null;
      var1.setline(994);
      var11 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("Manager", var11, Manager$63);
      var1.setlocal("Manager", var4);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(1096);
      var11 = new PyObject[]{var1.getname("Filterer")};
      var4 = Py.makeClass("Logger", var11, Logger$69);
      var1.setlocal("Logger", var4);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(1371);
      var11 = new PyObject[]{var1.getname("Logger")};
      var4 = Py.makeClass("RootLogger", var11, RootLogger$89);
      var1.setlocal("RootLogger", var4);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(1383);
      var3 = var1.getname("Logger");
      var1.setlocal("_loggerClass", var3);
      var3 = null;
      var1.setline(1385);
      var11 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("LoggerAdapter", var11, LoggerAdapter$91);
      var1.setlocal("LoggerAdapter", var4);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(1481);
      var3 = var1.getname("RootLogger").__call__(var2, var1.getname("WARNING"));
      var1.setlocal("root", var3);
      var3 = null;
      var1.setline(1482);
      var3 = var1.getname("root");
      var1.getname("Logger").__setattr__("root", var3);
      var3 = null;
      var1.setline(1483);
      var3 = var1.getname("Manager").__call__(var2, var1.getname("Logger").__getattr__("root"));
      var1.getname("Logger").__setattr__("manager", var3);
      var3 = null;
      var1.setline(1489);
      var10 = PyString.fromInterned("%(levelname)s:%(name)s:%(message)s");
      var1.setlocal("BASIC_FORMAT", var10);
      var3 = null;
      var1.setline(1491);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, basicConfig$102, PyString.fromInterned("\n    Do basic configuration for the logging system.\n\n    This function does nothing if the root logger already has handlers\n    configured. It is a convenience method intended for use by simple scripts\n    to do one-shot configuration of the logging package.\n\n    The default behaviour is to create a StreamHandler which writes to\n    sys.stderr, set a formatter using the BASIC_FORMAT format string, and\n    add the handler to the root logger.\n\n    A number of optional keyword arguments may be specified, which can alter\n    the default behaviour.\n\n    filename  Specifies that a FileHandler be created, using the specified\n              filename, rather than a StreamHandler.\n    filemode  Specifies the mode to open the file, if filename is specified\n              (if filemode is unspecified, it defaults to 'a').\n    format    Use the specified format string for the handler.\n    datefmt   Use the specified date/time format.\n    level     Set the root logger level to the specified level.\n    stream    Use the specified stream to initialize the StreamHandler. Note\n              that this argument is incompatible with 'filename' - if both\n              are present, 'stream' is ignored.\n\n    Note that you could specify a stream created using open(filename, mode)\n    rather than passing the filename and mode in. However, it should be\n    remembered that StreamHandler does not close its stream (since it may be\n    using sys.stdout or sys.stderr), whereas FileHandler closes its stream\n    when the handler is closed.\n    "));
      var1.setlocal("basicConfig", var12);
      var3 = null;
      var1.setline(1551);
      var11 = new PyObject[]{var1.getname("None")};
      var12 = new PyFunction(var1.f_globals, var11, getLogger$103, PyString.fromInterned("\n    Return a logger with the specified name, creating it if necessary.\n\n    If no name is specified, return the root logger.\n    "));
      var1.setlocal("getLogger", var12);
      var3 = null;
      var1.setline(1571);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, critical$104, PyString.fromInterned("\n    Log a message with severity 'CRITICAL' on the root logger.\n    "));
      var1.setlocal("critical", var12);
      var3 = null;
      var1.setline(1579);
      var3 = var1.getname("critical");
      var1.setlocal("fatal", var3);
      var3 = null;
      var1.setline(1581);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, error$105, PyString.fromInterned("\n    Log a message with severity 'ERROR' on the root logger.\n    "));
      var1.setlocal("error", var12);
      var3 = null;
      var1.setline(1589);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, exception$106, PyString.fromInterned("\n    Log a message with severity 'ERROR' on the root logger,\n    with exception information.\n    "));
      var1.setlocal("exception", var12);
      var3 = null;
      var1.setline(1597);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, warning$107, PyString.fromInterned("\n    Log a message with severity 'WARNING' on the root logger.\n    "));
      var1.setlocal("warning", var12);
      var3 = null;
      var1.setline(1605);
      var3 = var1.getname("warning");
      var1.setlocal("warn", var3);
      var3 = null;
      var1.setline(1607);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, info$108, PyString.fromInterned("\n    Log a message with severity 'INFO' on the root logger.\n    "));
      var1.setlocal("info", var12);
      var3 = null;
      var1.setline(1615);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, debug$109, PyString.fromInterned("\n    Log a message with severity 'DEBUG' on the root logger.\n    "));
      var1.setlocal("debug", var12);
      var3 = null;
      var1.setline(1623);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, log$110, PyString.fromInterned("\n    Log 'msg % args' with the integer severity 'level' on the root logger.\n    "));
      var1.setlocal("log", var12);
      var3 = null;
      var1.setline(1631);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, disable$111, PyString.fromInterned("\n    Disable all logging calls of severity 'level' and below.\n    "));
      var1.setlocal("disable", var12);
      var3 = null;
      var1.setline(1637);
      var11 = new PyObject[]{var1.getname("_handlerList")};
      var12 = new PyFunction(var1.f_globals, var11, shutdown$112, PyString.fromInterned("\n    Perform any cleanup actions in the logging system (e.g. flushing\n    buffers).\n\n    Should be called at application exit.\n    "));
      var1.setlocal("shutdown", var12);
      var3 = null;
      var1.setline(1668);
      var3 = imp.importOne("atexit", var1, -1);
      var1.setlocal("atexit", var3);
      var3 = null;
      var1.setline(1669);
      var1.getname("atexit").__getattr__("register").__call__(var2, var1.getname("shutdown"));
      var1.setline(1673);
      var11 = new PyObject[]{var1.getname("Handler")};
      var4 = Py.makeClass("NullHandler", var11, NullHandler$113);
      var1.setlocal("NullHandler", var4);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(1694);
      var3 = var1.getname("None");
      var1.setlocal("_warnings_showwarning", var3);
      var3 = null;
      var1.setline(1696);
      var11 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var12 = new PyFunction(var1.f_globals, var11, _showwarning$117, PyString.fromInterned("\n    Implementation of showwarnings which redirects to logging, which will first\n    check to see if the file parameter is None. If a file is specified, it will\n    delegate to the original warnings implementation of showwarning. Otherwise,\n    it will call warnings.formatwarning and will log the resulting string to a\n    warnings logger named \"py.warnings\" with level logging.WARNING.\n    "));
      var1.setlocal("_showwarning", var12);
      var3 = null;
      var1.setline(1714);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, captureWarnings$118, PyString.fromInterned("\n    If capture is true, redirect all warnings to the logging package.\n    If capture is False, ensure that warnings are not redirected to logging\n    but to their original destinations.\n    "));
      var1.setlocal("captureWarnings", var12);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject currentframe$1(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      PyString.fromInterned("Return the frame object for the caller's stack frame.");

      try {
         var1.setline(79);
         throw Py.makeException(var1.getglobal("Exception"));
      } catch (Throwable var5) {
         Py.setException(var5, var1);
         var1.setline(81);
         PyObject var4 = var1.getglobal("sys").__getattr__("exc_info").__call__(var2).__getitem__(Py.newInteger(2)).__getattr__("tb_frame").__getattr__("f_back");
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject f$2(PyFrame var1, ThreadState var2) {
      var1.setline(83);
      PyObject var3 = var1.getglobal("sys").__getattr__("_getframe").__call__((ThreadState)var2, (PyObject)Py.newInteger(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getLevelName$3(PyFrame var1, ThreadState var2) {
      var1.setline(168);
      PyString.fromInterned("\n    Return the textual representation of logging level 'level'.\n\n    If the level is one of the predefined levels (CRITICAL, ERROR, WARNING,\n    INFO, DEBUG) then you get the corresponding string. If you have\n    associated levels with names using addLevelName then the name you have\n    associated with 'level' is returned.\n\n    If a numeric value corresponding to one of the defined levels is passed\n    in, the corresponding string representation is returned.\n\n    Otherwise, the string \"Level %s\" % level is returned.\n    ");
      var1.setline(169);
      PyObject var3 = var1.getglobal("_levelNames").__getattr__("get").__call__(var2, var1.getlocal(0), PyString.fromInterned("Level %s")._mod(var1.getlocal(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject addLevelName$4(PyFrame var1, ThreadState var2) {
      var1.setline(176);
      PyString.fromInterned("\n    Associate 'levelName' with 'level'.\n\n    This is used when converting levels to text during message formatting.\n    ");
      var1.setline(177);
      var1.getglobal("_acquireLock").__call__(var2);
      Object var3 = null;

      try {
         var1.setline(179);
         PyObject var4 = var1.getlocal(1);
         var1.getglobal("_levelNames").__setitem__(var1.getlocal(0), var4);
         var4 = null;
         var1.setline(180);
         var4 = var1.getlocal(0);
         var1.getglobal("_levelNames").__setitem__(var1.getlocal(1), var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(182);
         var1.getglobal("_releaseLock").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(182);
      var1.getglobal("_releaseLock").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _checkLevel$5(PyFrame var1, ThreadState var2) {
      var1.setline(185);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__nonzero__()) {
         var1.setline(186);
         var3 = var1.getlocal(0);
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(187);
         var3 = var1.getglobal("str").__call__(var2, var1.getlocal(0));
         PyObject var10000 = var3._eq(var1.getlocal(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(192);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("Level not an integer or a valid string: %r")._mod(var1.getlocal(0))));
         }

         var1.setline(188);
         var3 = var1.getlocal(0);
         var10000 = var3._notin(var1.getglobal("_levelNames"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(189);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Unknown level: %r")._mod(var1.getlocal(0))));
         }

         var1.setline(190);
         var3 = var1.getglobal("_levelNames").__getitem__(var1.getlocal(0));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(193);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _acquireLock$6(PyFrame var1, ThreadState var2) {
      var1.setline(217);
      PyString.fromInterned("\n    Acquire the module-level lock for serializing access to shared data.\n\n    This should be released with _releaseLock().\n    ");
      var1.setline(218);
      if (var1.getglobal("_lock").__nonzero__()) {
         var1.setline(219);
         var1.getglobal("_lock").__getattr__("acquire").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _releaseLock$7(PyFrame var1, ThreadState var2) {
      var1.setline(224);
      PyString.fromInterned("\n    Release the module-level lock acquired by calling _acquireLock().\n    ");
      var1.setline(225);
      if (var1.getglobal("_lock").__nonzero__()) {
         var1.setline(226);
         var1.getglobal("_lock").__getattr__("release").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject LogRecord$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    A LogRecord instance represents an event being logged.\n\n    LogRecord instances are created every time something is logged. They\n    contain all the information pertinent to the event being logged. The\n    main information passed in is in msg and args, which are combined\n    using str(msg) % args to create the message field of the record. The\n    record also includes information such as when the record was created,\n    the source line where the logging call was made, and any exception\n    information to be logged.\n    "));
      var1.setline(243);
      PyString.fromInterned("\n    A LogRecord instance represents an event being logged.\n\n    LogRecord instances are created every time something is logged. They\n    contain all the information pertinent to the event being logged. The\n    main information passed in is in msg and args, which are combined\n    using str(msg) % args to create the message field of the record. The\n    record also includes information such as when the record was created,\n    the source line where the logging call was made, and any exception\n    information to be logged.\n    ");
      var1.setline(244);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$9, PyString.fromInterned("\n        Initialize a logging record with interesting information.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(309);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$10, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      var1.setline(313);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getMessage$11, PyString.fromInterned("\n        Return the message for this LogRecord.\n\n        Return the message for this LogRecord after merging any user-supplied\n        arguments with the message.\n        "));
      var1.setlocal("getMessage", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$9(PyFrame var1, ThreadState var2) {
      var1.setline(248);
      PyString.fromInterned("\n        Initialize a logging record with interesting information.\n        ");
      var1.setline(249);
      PyObject var3 = var1.getglobal("time").__getattr__("time").__call__(var2);
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(250);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("name", var3);
      var3 = null;
      var1.setline(251);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("msg", var3);
      var3 = null;
      var1.setline(265);
      PyObject var10000 = var1.getlocal(6);
      if (var10000.__nonzero__()) {
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(6));
         var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(6).__getitem__(Py.newInteger(0)), var1.getglobal("dict"));
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(6).__getitem__(Py.newInteger(0));
            }
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(266);
         var3 = var1.getlocal(6).__getitem__(Py.newInteger(0));
         var1.setlocal(6, var3);
         var3 = null;
      }

      var1.setline(267);
      var3 = var1.getlocal(6);
      var1.getlocal(0).__setattr__("args", var3);
      var3 = null;
      var1.setline(268);
      var3 = var1.getglobal("getLevelName").__call__(var2, var1.getlocal(2));
      var1.getlocal(0).__setattr__("levelname", var3);
      var3 = null;
      var1.setline(269);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("levelno", var3);
      var3 = null;
      var1.setline(270);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("pathname", var3);
      var3 = null;

      PyException var8;
      try {
         var1.setline(272);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(3));
         var1.getlocal(0).__setattr__("filename", var3);
         var3 = null;
         var1.setline(273);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getlocal(0).__getattr__("filename")).__getitem__(Py.newInteger(0));
         var1.getlocal(0).__setattr__("module", var3);
         var3 = null;
      } catch (Throwable var6) {
         var8 = Py.setException(var6, var1);
         if (!var8.match(new PyTuple(new PyObject[]{var1.getglobal("TypeError"), var1.getglobal("ValueError"), var1.getglobal("AttributeError")}))) {
            throw var8;
         }

         var1.setline(275);
         PyObject var4 = var1.getlocal(3);
         var1.getlocal(0).__setattr__("filename", var4);
         var4 = null;
         var1.setline(276);
         PyString var7 = PyString.fromInterned("Unknown module");
         var1.getlocal(0).__setattr__((String)"module", var7);
         var4 = null;
      }

      var1.setline(277);
      var3 = var1.getlocal(7);
      var1.getlocal(0).__setattr__("exc_info", var3);
      var3 = null;
      var1.setline(278);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("exc_text", var3);
      var3 = null;
      var1.setline(279);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.setline(280);
      var3 = var1.getlocal(8);
      var1.getlocal(0).__setattr__("funcName", var3);
      var3 = null;
      var1.setline(281);
      var3 = var1.getlocal(9);
      var1.getlocal(0).__setattr__("created", var3);
      var3 = null;
      var1.setline(282);
      var3 = var1.getlocal(9)._sub(var1.getglobal("long").__call__(var2, var1.getlocal(9)))._mul(Py.newInteger(1000));
      var1.getlocal(0).__setattr__("msecs", var3);
      var3 = null;
      var1.setline(283);
      var3 = var1.getlocal(0).__getattr__("created")._sub(var1.getglobal("_startTime"))._mul(Py.newInteger(1000));
      var1.getlocal(0).__setattr__("relativeCreated", var3);
      var3 = null;
      var1.setline(284);
      var10000 = var1.getglobal("logThreads");
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("thread");
      }

      if (var10000.__nonzero__()) {
         var1.setline(285);
         var3 = var1.getglobal("thread").__getattr__("get_ident").__call__(var2);
         var1.getlocal(0).__setattr__("thread", var3);
         var3 = null;
         var1.setline(286);
         var3 = var1.getglobal("threading").__getattr__("current_thread").__call__(var2).__getattr__("name");
         var1.getlocal(0).__setattr__("threadName", var3);
         var3 = null;
      } else {
         var1.setline(288);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("thread", var3);
         var3 = null;
         var1.setline(289);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("threadName", var3);
         var3 = null;
      }

      var1.setline(290);
      if (var1.getglobal("logMultiprocessing").__not__().__nonzero__()) {
         var1.setline(291);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("processName", var3);
         var3 = null;
      } else {
         var1.setline(293);
         PyString var9 = PyString.fromInterned("MainProcess");
         var1.getlocal(0).__setattr__((String)"processName", var9);
         var3 = null;
         var1.setline(294);
         var3 = var1.getglobal("sys").__getattr__("modules").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("multiprocessing"));
         var1.setlocal(10, var3);
         var3 = null;
         var1.setline(295);
         var3 = var1.getlocal(10);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            try {
               var1.setline(301);
               var3 = var1.getlocal(10).__getattr__("current_process").__call__(var2).__getattr__("name");
               var1.getlocal(0).__setattr__("processName", var3);
               var3 = null;
            } catch (Throwable var5) {
               var8 = Py.setException(var5, var1);
               if (!var8.match(var1.getglobal("StandardError"))) {
                  throw var8;
               }

               var1.setline(303);
            }
         }
      }

      var1.setline(304);
      var10000 = var1.getglobal("logProcesses");
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("getpid"));
      }

      if (var10000.__nonzero__()) {
         var1.setline(305);
         var3 = var1.getglobal("os").__getattr__("getpid").__call__(var2);
         var1.getlocal(0).__setattr__("process", var3);
         var3 = null;
      } else {
         var1.setline(307);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("process", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __str__$10(PyFrame var1, ThreadState var2) {
      var1.setline(310);
      PyObject var3 = PyString.fromInterned("<LogRecord: %s, %s, %s, %s, \"%s\">")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("name"), var1.getlocal(0).__getattr__("levelno"), var1.getlocal(0).__getattr__("pathname"), var1.getlocal(0).__getattr__("lineno"), var1.getlocal(0).__getattr__("msg")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getMessage$11(PyFrame var1, ThreadState var2) {
      var1.setline(319);
      PyString.fromInterned("\n        Return the message for this LogRecord.\n\n        Return the message for this LogRecord after merging any user-supplied\n        arguments with the message.\n        ");
      var1.setline(320);
      PyObject var3;
      if (var1.getglobal("_unicode").__not__().__nonzero__()) {
         var1.setline(321);
         var3 = var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("msg"));
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(323);
         var3 = var1.getlocal(0).__getattr__("msg");
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(324);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__not__().__nonzero__()) {
            try {
               var1.setline(326);
               var3 = var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("msg"));
               var1.setlocal(1, var3);
               var3 = null;
            } catch (Throwable var5) {
               PyException var6 = Py.setException(var5, var1);
               if (!var6.match(var1.getglobal("UnicodeError"))) {
                  throw var6;
               }

               var1.setline(328);
               PyObject var4 = var1.getlocal(0).__getattr__("msg");
               var1.setlocal(1, var4);
               var4 = null;
            }
         }
      }

      var1.setline(329);
      if (var1.getlocal(0).__getattr__("args").__nonzero__()) {
         var1.setline(330);
         var3 = var1.getlocal(1)._mod(var1.getlocal(0).__getattr__("args"));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(331);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject makeLogRecord$12(PyFrame var1, ThreadState var2) {
      var1.setline(339);
      PyString.fromInterned("\n    Make a LogRecord whose attributes are defined by the specified dictionary,\n    This function is useful for converting a logging event received over\n    a socket connection (which is sent as a dictionary) into a LogRecord\n    instance.\n    ");
      var1.setline(340);
      PyObject var10000 = var1.getglobal("LogRecord");
      PyObject[] var3 = new PyObject[]{var1.getglobal("None"), var1.getglobal("None"), PyString.fromInterned(""), Py.newInteger(0), PyString.fromInterned(""), new PyTuple(Py.EmptyObjects), var1.getglobal("None"), var1.getglobal("None")};
      PyObject var4 = var10000.__call__(var2, var3);
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(341);
      var1.getlocal(1).__getattr__("__dict__").__getattr__("update").__call__(var2, var1.getlocal(0));
      var1.setline(342);
      var4 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject Formatter$13(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    Formatter instances are used to convert a LogRecord to text.\n\n    Formatters need to know how a LogRecord is constructed. They are\n    responsible for converting a LogRecord to (usually) a string which can\n    be interpreted by either a human or an external system. The base Formatter\n    allows a formatting string to be specified. If none is supplied, the\n    default value of \"%s(message)\\n\" is used.\n\n    The Formatter can be initialized with a format string which makes use of\n    knowledge of the LogRecord attributes - e.g. the default value mentioned\n    above makes use of the fact that the user's message and arguments are pre-\n    formatted into a LogRecord's message attribute. Currently, the useful\n    attributes in a LogRecord are described by:\n\n    %(name)s            Name of the logger (logging channel)\n    %(levelno)s         Numeric logging level for the message (DEBUG, INFO,\n                        WARNING, ERROR, CRITICAL)\n    %(levelname)s       Text logging level for the message (\"DEBUG\", \"INFO\",\n                        \"WARNING\", \"ERROR\", \"CRITICAL\")\n    %(pathname)s        Full pathname of the source file where the logging\n                        call was issued (if available)\n    %(filename)s        Filename portion of pathname\n    %(module)s          Module (name portion of filename)\n    %(lineno)d          Source line number where the logging call was issued\n                        (if available)\n    %(funcName)s        Function name\n    %(created)f         Time when the LogRecord was created (time.time()\n                        return value)\n    %(asctime)s         Textual time when the LogRecord was created\n    %(msecs)d           Millisecond portion of the creation time\n    %(relativeCreated)d Time in milliseconds when the LogRecord was created,\n                        relative to the time the logging module was loaded\n                        (typically at application startup time)\n    %(thread)d          Thread ID (if available)\n    %(threadName)s      Thread name (if available)\n    %(process)d         Process ID (if available)\n    %(message)s         The result of record.getMessage(), computed just as\n                        the record is emitted\n    "));
      var1.setline(388);
      PyString.fromInterned("\n    Formatter instances are used to convert a LogRecord to text.\n\n    Formatters need to know how a LogRecord is constructed. They are\n    responsible for converting a LogRecord to (usually) a string which can\n    be interpreted by either a human or an external system. The base Formatter\n    allows a formatting string to be specified. If none is supplied, the\n    default value of \"%s(message)\\n\" is used.\n\n    The Formatter can be initialized with a format string which makes use of\n    knowledge of the LogRecord attributes - e.g. the default value mentioned\n    above makes use of the fact that the user's message and arguments are pre-\n    formatted into a LogRecord's message attribute. Currently, the useful\n    attributes in a LogRecord are described by:\n\n    %(name)s            Name of the logger (logging channel)\n    %(levelno)s         Numeric logging level for the message (DEBUG, INFO,\n                        WARNING, ERROR, CRITICAL)\n    %(levelname)s       Text logging level for the message (\"DEBUG\", \"INFO\",\n                        \"WARNING\", \"ERROR\", \"CRITICAL\")\n    %(pathname)s        Full pathname of the source file where the logging\n                        call was issued (if available)\n    %(filename)s        Filename portion of pathname\n    %(module)s          Module (name portion of filename)\n    %(lineno)d          Source line number where the logging call was issued\n                        (if available)\n    %(funcName)s        Function name\n    %(created)f         Time when the LogRecord was created (time.time()\n                        return value)\n    %(asctime)s         Textual time when the LogRecord was created\n    %(msecs)d           Millisecond portion of the creation time\n    %(relativeCreated)d Time in milliseconds when the LogRecord was created,\n                        relative to the time the logging module was loaded\n                        (typically at application startup time)\n    %(thread)d          Thread ID (if available)\n    %(threadName)s      Thread name (if available)\n    %(process)d         Process ID (if available)\n    %(message)s         The result of record.getMessage(), computed just as\n                        the record is emitted\n    ");
      var1.setline(390);
      PyObject var3 = var1.getname("time").__getattr__("localtime");
      var1.setlocal("converter", var3);
      var3 = null;
      var1.setline(392);
      PyObject[] var4 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$14, PyString.fromInterned("\n        Initialize the formatter with specified format strings.\n\n        Initialize the formatter either with the specified format string, or a\n        default as described above. Allow for specialized date formatting with\n        the optional datefmt argument (if omitted, you get the ISO8601 format).\n        "));
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(406);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, formatTime$15, PyString.fromInterned("\n        Return the creation time of the specified LogRecord as formatted text.\n\n        This method should be called from format() by a formatter which\n        wants to make use of a formatted time. This method can be overridden\n        in formatters to provide for any specific requirement, but the\n        basic behaviour is as follows: if datefmt (a string) is specified,\n        it is used with time.strftime() to format the creation time of the\n        record. Otherwise, the ISO8601 format is used. The resulting\n        string is returned. This function uses a user-configurable function\n        to convert the creation time to a tuple. By default, time.localtime()\n        is used; to change this for a particular formatter instance, set the\n        'converter' attribute to a function with the same signature as\n        time.localtime() or time.gmtime(). To change it for all formatters,\n        for example if you want all logging times to be shown in GMT,\n        set the 'converter' attribute in the Formatter class.\n        "));
      var1.setlocal("formatTime", var5);
      var3 = null;
      var1.setline(432);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, formatException$16, PyString.fromInterned("\n        Format and return the specified exception information as a string.\n\n        This default implementation just uses\n        traceback.print_exception()\n        "));
      var1.setlocal("formatException", var5);
      var3 = null;
      var1.setline(447);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, usesTime$17, PyString.fromInterned("\n        Check if the format uses the creation time of the record.\n        "));
      var1.setlocal("usesTime", var5);
      var3 = null;
      var1.setline(453);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, format$18, PyString.fromInterned("\n        Format the specified record as text.\n\n        The record's attribute dictionary is used as the operand to a\n        string formatting operation which yields the returned string.\n        Before formatting the dictionary, a couple of preparatory steps\n        are carried out. The message attribute of the record is computed\n        using LogRecord.getMessage(). If the formatting string uses the\n        time (as determined by a call to usesTime(), formatTime() is\n        called to format the event time. If there is exception information,\n        it is formatted using formatException() and appended to the message.\n        "));
      var1.setlocal("format", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$14(PyFrame var1, ThreadState var2) {
      var1.setline(399);
      PyString.fromInterned("\n        Initialize the formatter with specified format strings.\n\n        Initialize the formatter either with the specified format string, or a\n        default as described above. Allow for specialized date formatting with\n        the optional datefmt argument (if omitted, you get the ISO8601 format).\n        ");
      var1.setline(400);
      PyObject var3;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(401);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("_fmt", var3);
         var3 = null;
      } else {
         var1.setline(403);
         PyString var4 = PyString.fromInterned("%(message)s");
         var1.getlocal(0).__setattr__((String)"_fmt", var4);
         var3 = null;
      }

      var1.setline(404);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("datefmt", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject formatTime$15(PyFrame var1, ThreadState var2) {
      var1.setline(423);
      PyString.fromInterned("\n        Return the creation time of the specified LogRecord as formatted text.\n\n        This method should be called from format() by a formatter which\n        wants to make use of a formatted time. This method can be overridden\n        in formatters to provide for any specific requirement, but the\n        basic behaviour is as follows: if datefmt (a string) is specified,\n        it is used with time.strftime() to format the creation time of the\n        record. Otherwise, the ISO8601 format is used. The resulting\n        string is returned. This function uses a user-configurable function\n        to convert the creation time to a tuple. By default, time.localtime()\n        is used; to change this for a particular formatter instance, set the\n        'converter' attribute to a function with the same signature as\n        time.localtime() or time.gmtime(). To change it for all formatters,\n        for example if you want all logging times to be shown in GMT,\n        set the 'converter' attribute in the Formatter class.\n        ");
      var1.setline(424);
      PyObject var3 = var1.getlocal(0).__getattr__("converter").__call__(var2, var1.getlocal(1).__getattr__("created"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(425);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(426);
         var3 = var1.getglobal("time").__getattr__("strftime").__call__(var2, var1.getlocal(2), var1.getlocal(3));
         var1.setlocal(4, var3);
         var3 = null;
      } else {
         var1.setline(428);
         var3 = var1.getglobal("time").__getattr__("strftime").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%Y-%m-%d %H:%M:%S"), (PyObject)var1.getlocal(3));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(429);
         var3 = PyString.fromInterned("%s,%03d")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(1).__getattr__("msecs")}));
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(430);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject formatException$16(PyFrame var1, ThreadState var2) {
      var1.setline(438);
      PyString.fromInterned("\n        Format and return the specified exception information as a string.\n\n        This default implementation just uses\n        traceback.print_exception()\n        ");
      var1.setline(439);
      PyObject var3 = var1.getglobal("cStringIO").__getattr__("StringIO").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(440);
      PyObject var10000 = var1.getglobal("traceback").__getattr__("print_exception");
      PyObject[] var4 = new PyObject[]{var1.getlocal(1).__getitem__(Py.newInteger(0)), var1.getlocal(1).__getitem__(Py.newInteger(1)), var1.getlocal(1).__getitem__(Py.newInteger(2)), var1.getglobal("None"), var1.getlocal(2)};
      var10000.__call__(var2, var4);
      var1.setline(441);
      var3 = var1.getlocal(2).__getattr__("getvalue").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(442);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(443);
      var3 = var1.getlocal(3).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null);
      var10000 = var3._eq(PyString.fromInterned("\n"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(444);
         var3 = var1.getlocal(3).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(445);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject usesTime$17(PyFrame var1, ThreadState var2) {
      var1.setline(450);
      PyString.fromInterned("\n        Check if the format uses the creation time of the record.\n        ");
      var1.setline(451);
      PyObject var3 = var1.getlocal(0).__getattr__("_fmt").__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%(asctime)"));
      PyObject var10000 = var3._ge(Py.newInteger(0));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject format$18(PyFrame var1, ThreadState var2) {
      var1.setline(465);
      PyString.fromInterned("\n        Format the specified record as text.\n\n        The record's attribute dictionary is used as the operand to a\n        string formatting operation which yields the returned string.\n        Before formatting the dictionary, a couple of preparatory steps\n        are carried out. The message attribute of the record is computed\n        using LogRecord.getMessage(). If the formatting string uses the\n        time (as determined by a call to usesTime(), formatTime() is\n        called to format the event time. If there is exception information,\n        it is formatted using formatException() and appended to the message.\n        ");
      var1.setline(466);
      PyObject var3 = var1.getlocal(1).__getattr__("getMessage").__call__(var2);
      var1.getlocal(1).__setattr__("message", var3);
      var3 = null;
      var1.setline(467);
      if (var1.getlocal(0).__getattr__("usesTime").__call__(var2).__nonzero__()) {
         var1.setline(468);
         var3 = var1.getlocal(0).__getattr__("formatTime").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("datefmt"));
         var1.getlocal(1).__setattr__("asctime", var3);
         var3 = null;
      }

      var1.setline(469);
      var3 = var1.getlocal(0).__getattr__("_fmt")._mod(var1.getlocal(1).__getattr__("__dict__"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(470);
      if (var1.getlocal(1).__getattr__("exc_info").__nonzero__()) {
         var1.setline(473);
         if (var1.getlocal(1).__getattr__("exc_text").__not__().__nonzero__()) {
            var1.setline(474);
            var3 = var1.getlocal(0).__getattr__("formatException").__call__(var2, var1.getlocal(1).__getattr__("exc_info"));
            var1.getlocal(1).__setattr__("exc_text", var3);
            var3 = null;
         }
      }

      var1.setline(475);
      if (var1.getlocal(1).__getattr__("exc_text").__nonzero__()) {
         var1.setline(476);
         var3 = var1.getlocal(2).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null);
         PyObject var10000 = var3._ne(PyString.fromInterned("\n"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(477);
            var3 = var1.getlocal(2)._add(PyString.fromInterned("\n"));
            var1.setlocal(2, var3);
            var3 = null;
         }

         try {
            var1.setline(479);
            var3 = var1.getlocal(2)._add(var1.getlocal(1).__getattr__("exc_text"));
            var1.setlocal(2, var3);
            var3 = null;
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (!var6.match(var1.getglobal("UnicodeError"))) {
               throw var6;
            }

            var1.setline(487);
            PyObject var4 = var1.getlocal(2)._add(var1.getlocal(1).__getattr__("exc_text").__getattr__("decode").__call__((ThreadState)var2, (PyObject)var1.getglobal("sys").__getattr__("getfilesystemencoding").__call__(var2), (PyObject)PyString.fromInterned("replace")));
            var1.setlocal(2, var4);
            var4 = null;
         }
      }

      var1.setline(489);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject BufferingFormatter$19(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    A formatter suitable for formatting a number of records.\n    "));
      var1.setline(499);
      PyString.fromInterned("\n    A formatter suitable for formatting a number of records.\n    ");
      var1.setline(500);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$20, PyString.fromInterned("\n        Optionally specify a formatter which will be used to format each\n        individual record.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(510);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, formatHeader$21, PyString.fromInterned("\n        Return the header string for the specified records.\n        "));
      var1.setlocal("formatHeader", var4);
      var3 = null;
      var1.setline(516);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, formatFooter$22, PyString.fromInterned("\n        Return the footer string for the specified records.\n        "));
      var1.setlocal("formatFooter", var4);
      var3 = null;
      var1.setline(522);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, format$23, PyString.fromInterned("\n        Format the specified records and return the result as a string.\n        "));
      var1.setlocal("format", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$20(PyFrame var1, ThreadState var2) {
      var1.setline(504);
      PyString.fromInterned("\n        Optionally specify a formatter which will be used to format each\n        individual record.\n        ");
      var1.setline(505);
      PyObject var3;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(506);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("linefmt", var3);
         var3 = null;
      } else {
         var1.setline(508);
         var3 = var1.getglobal("_defaultFormatter");
         var1.getlocal(0).__setattr__("linefmt", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject formatHeader$21(PyFrame var1, ThreadState var2) {
      var1.setline(513);
      PyString.fromInterned("\n        Return the header string for the specified records.\n        ");
      var1.setline(514);
      PyString var3 = PyString.fromInterned("");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject formatFooter$22(PyFrame var1, ThreadState var2) {
      var1.setline(519);
      PyString.fromInterned("\n        Return the footer string for the specified records.\n        ");
      var1.setline(520);
      PyString var3 = PyString.fromInterned("");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject format$23(PyFrame var1, ThreadState var2) {
      var1.setline(525);
      PyString.fromInterned("\n        Format the specified records and return the result as a string.\n        ");
      var1.setline(526);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(527);
      PyObject var6 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var6._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(528);
         var6 = var1.getlocal(2)._add(var1.getlocal(0).__getattr__("formatHeader").__call__(var2, var1.getlocal(1)));
         var1.setlocal(2, var6);
         var3 = null;
         var1.setline(529);
         var6 = var1.getlocal(1).__iter__();

         while(true) {
            var1.setline(529);
            PyObject var4 = var6.__iternext__();
            if (var4 == null) {
               var1.setline(531);
               var6 = var1.getlocal(2)._add(var1.getlocal(0).__getattr__("formatFooter").__call__(var2, var1.getlocal(1)));
               var1.setlocal(2, var6);
               var3 = null;
               break;
            }

            var1.setlocal(3, var4);
            var1.setline(530);
            PyObject var5 = var1.getlocal(2)._add(var1.getlocal(0).__getattr__("linefmt").__getattr__("format").__call__(var2, var1.getlocal(3)));
            var1.setlocal(2, var5);
            var5 = null;
         }
      }

      var1.setline(532);
      var6 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject Filter$24(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    Filter instances are used to perform arbitrary filtering of LogRecords.\n\n    Loggers and Handlers can optionally use Filter instances to filter\n    records as desired. The base filter class only allows events which are\n    below a certain point in the logger hierarchy. For example, a filter\n    initialized with \"A.B\" will allow events logged by loggers \"A.B\",\n    \"A.B.C\", \"A.B.C.D\", \"A.B.D\" etc. but not \"A.BB\", \"B.A.B\" etc. If\n    initialized with the empty string, all events are passed.\n    "));
      var1.setline(548);
      PyString.fromInterned("\n    Filter instances are used to perform arbitrary filtering of LogRecords.\n\n    Loggers and Handlers can optionally use Filter instances to filter\n    records as desired. The base filter class only allows events which are\n    below a certain point in the logger hierarchy. For example, a filter\n    initialized with \"A.B\" will allow events logged by loggers \"A.B\",\n    \"A.B.C\", \"A.B.C.D\", \"A.B.D\" etc. but not \"A.BB\", \"B.A.B\" etc. If\n    initialized with the empty string, all events are passed.\n    ");
      var1.setline(549);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$25, PyString.fromInterned("\n        Initialize a filter.\n\n        Initialize with the name of the logger which, together with its\n        children, will have its events allowed through the filter. If no\n        name is specified, allow every event.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(560);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, filter$26, PyString.fromInterned("\n        Determine if the specified record is to be logged.\n\n        Is the specified record to be logged? Returns 0 for no, nonzero for\n        yes. If deemed appropriate, the record may be modified in-place.\n        "));
      var1.setlocal("filter", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$25(PyFrame var1, ThreadState var2) {
      var1.setline(556);
      PyString.fromInterned("\n        Initialize a filter.\n\n        Initialize with the name of the logger which, together with its\n        children, will have its events allowed through the filter. If no\n        name is specified, allow every event.\n        ");
      var1.setline(557);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("name", var3);
      var3 = null;
      var1.setline(558);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("nlen", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject filter$26(PyFrame var1, ThreadState var2) {
      var1.setline(566);
      PyString.fromInterned("\n        Determine if the specified record is to be logged.\n\n        Is the specified record to be logged? Returns 0 for no, nonzero for\n        yes. If deemed appropriate, the record may be modified in-place.\n        ");
      var1.setline(567);
      PyObject var3 = var1.getlocal(0).__getattr__("nlen");
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      PyInteger var5;
      if (var10000.__nonzero__()) {
         var1.setline(568);
         var5 = Py.newInteger(1);
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(569);
         PyObject var4 = var1.getlocal(0).__getattr__("name");
         var10000 = var4._eq(var1.getlocal(1).__getattr__("name"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(570);
            var5 = Py.newInteger(1);
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(571);
            var4 = var1.getlocal(1).__getattr__("name").__getattr__("find").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("name"), (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(0).__getattr__("nlen"));
            var10000 = var4._ne(Py.newInteger(0));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(572);
               var5 = Py.newInteger(0);
               var1.f_lasti = -1;
               return var5;
            } else {
               var1.setline(573);
               var4 = var1.getlocal(1).__getattr__("name").__getitem__(var1.getlocal(0).__getattr__("nlen"));
               var10000 = var4._eq(PyString.fromInterned("."));
               var4 = null;
               var3 = var10000;
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject Filterer$27(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    A base class for loggers and handlers which allows them to share\n    common code.\n    "));
      var1.setline(579);
      PyString.fromInterned("\n    A base class for loggers and handlers which allows them to share\n    common code.\n    ");
      var1.setline(580);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$28, PyString.fromInterned("\n        Initialize the list of filters to be an empty list.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(586);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, addFilter$29, PyString.fromInterned("\n        Add the specified filter to this handler.\n        "));
      var1.setlocal("addFilter", var4);
      var3 = null;
      var1.setline(593);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, removeFilter$30, PyString.fromInterned("\n        Remove the specified filter from this handler.\n        "));
      var1.setlocal("removeFilter", var4);
      var3 = null;
      var1.setline(600);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, filter$31, PyString.fromInterned("\n        Determine if a record is loggable by consulting all the filters.\n\n        The default is to allow the record to be logged; any filter can veto\n        this and the record is then dropped. Returns a zero value if a record\n        is to be dropped, else non-zero.\n        "));
      var1.setlocal("filter", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$28(PyFrame var1, ThreadState var2) {
      var1.setline(583);
      PyString.fromInterned("\n        Initialize the list of filters to be an empty list.\n        ");
      var1.setline(584);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"filters", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addFilter$29(PyFrame var1, ThreadState var2) {
      var1.setline(589);
      PyString.fromInterned("\n        Add the specified filter to this handler.\n        ");
      var1.setline(590);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("filters"));
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(591);
         var1.getlocal(0).__getattr__("filters").__getattr__("append").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject removeFilter$30(PyFrame var1, ThreadState var2) {
      var1.setline(596);
      PyString.fromInterned("\n        Remove the specified filter from this handler.\n        ");
      var1.setline(597);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("filters"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(598);
         var1.getlocal(0).__getattr__("filters").__getattr__("remove").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject filter$31(PyFrame var1, ThreadState var2) {
      var1.setline(607);
      PyString.fromInterned("\n        Determine if a record is loggable by consulting all the filters.\n\n        The default is to allow the record to be logged; any filter can veto\n        this and the record is then dropped. Returns a zero value if a record\n        is to be dropped, else non-zero.\n        ");
      var1.setline(608);
      PyInteger var3 = Py.newInteger(1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(609);
      PyObject var6 = var1.getlocal(0).__getattr__("filters").__iter__();

      while(true) {
         var1.setline(609);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            break;
         }

         var1.setlocal(3, var4);
         var1.setline(610);
         if (var1.getlocal(3).__getattr__("filter").__call__(var2, var1.getlocal(1)).__not__().__nonzero__()) {
            var1.setline(611);
            PyInteger var5 = Py.newInteger(0);
            var1.setlocal(2, var5);
            var5 = null;
            break;
         }
      }

      var1.setline(613);
      var6 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject _removeHandlerRef$32(PyFrame var1, ThreadState var2) {
      var1.setline(625);
      PyString.fromInterned("\n    Remove a handler reference from the internal cleanup list.\n    ");
      var1.setline(629);
      PyObject var3 = var1.getglobal("_acquireLock");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getglobal("_handlerList");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getglobal("_releaseLock");
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(631);
         var1.getglobal("_acquireLock").__call__(var2);
         var3 = null;

         try {
            var1.setline(633);
            PyObject var4 = var1.getlocal(0);
            var10000 = var4._in(var1.getglobal("_handlerList"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(634);
               var1.getglobal("_handlerList").__getattr__("remove").__call__(var2, var1.getlocal(0));
            }
         } catch (Throwable var5) {
            Py.addTraceback(var5, var1);
            var1.setline(636);
            var1.getglobal("_releaseLock").__call__(var2);
            throw (Throwable)var5;
         }

         var1.setline(636);
         var1.getglobal("_releaseLock").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _addHandlerRef$33(PyFrame var1, ThreadState var2) {
      var1.setline(641);
      PyString.fromInterned("\n    Add a handler to the internal cleanup list using a weak reference.\n    ");
      var1.setline(642);
      var1.getglobal("_acquireLock").__call__(var2);
      Object var3 = null;

      try {
         var1.setline(644);
         var1.getglobal("_handlerList").__getattr__("append").__call__(var2, var1.getglobal("weakref").__getattr__("ref").__call__(var2, var1.getlocal(0), var1.getglobal("_removeHandlerRef")));
      } catch (Throwable var4) {
         Py.addTraceback(var4, var1);
         var1.setline(646);
         var1.getglobal("_releaseLock").__call__(var2);
         throw (Throwable)var4;
      }

      var1.setline(646);
      var1.getglobal("_releaseLock").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Handler$34(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    Handler instances dispatch logging events to specific destinations.\n\n    The base handler class. Acts as a placeholder which defines the Handler\n    interface. Handlers can optionally use Formatter instances to format\n    records as desired. By default, no formatter is specified; in this case,\n    the 'raw' message as determined by record.message is logged.\n    "));
      var1.setline(656);
      PyString.fromInterned("\n    Handler instances dispatch logging events to specific destinations.\n\n    The base handler class. Acts as a placeholder which defines the Handler\n    interface. Handlers can optionally use Formatter instances to format\n    records as desired. By default, no formatter is specified; in this case,\n    the 'raw' message as determined by record.message is logged.\n    ");
      var1.setline(657);
      PyObject[] var3 = new PyObject[]{var1.getname("NOTSET")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$35, PyString.fromInterned("\n        Initializes the instance - basically setting the formatter to None\n        and the filter list to empty.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(670);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_name$36, (PyObject)null);
      var1.setlocal("get_name", var4);
      var3 = null;
      var1.setline(673);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_name$37, (PyObject)null);
      var1.setlocal("set_name", var4);
      var3 = null;
      var1.setline(684);
      PyObject var5 = var1.getname("property").__call__(var2, var1.getname("get_name"), var1.getname("set_name"));
      var1.setlocal("name", var5);
      var3 = null;
      var1.setline(686);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, createLock$38, PyString.fromInterned("\n        Acquire a thread lock for serializing access to the underlying I/O.\n        "));
      var1.setlocal("createLock", var4);
      var3 = null;
      var1.setline(695);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, acquire$39, PyString.fromInterned("\n        Acquire the I/O thread lock.\n        "));
      var1.setlocal("acquire", var4);
      var3 = null;
      var1.setline(702);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, release$40, PyString.fromInterned("\n        Release the I/O thread lock.\n        "));
      var1.setlocal("release", var4);
      var3 = null;
      var1.setline(709);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setLevel$41, PyString.fromInterned("\n        Set the logging level of this handler.\n        "));
      var1.setlocal("setLevel", var4);
      var3 = null;
      var1.setline(715);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, format$42, PyString.fromInterned("\n        Format the specified record.\n\n        If a formatter is set, use it. Otherwise, use the default formatter\n        for the module.\n        "));
      var1.setlocal("format", var4);
      var3 = null;
      var1.setline(728);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, emit$43, PyString.fromInterned("\n        Do whatever it takes to actually log the specified logging record.\n\n        This version is intended to be implemented by subclasses and so\n        raises a NotImplementedError.\n        "));
      var1.setlocal("emit", var4);
      var3 = null;
      var1.setline(738);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, handle$44, PyString.fromInterned("\n        Conditionally emit the specified logging record.\n\n        Emission depends on filters which may have been added to the handler.\n        Wrap the actual emission of the record with acquisition/release of\n        the I/O thread lock. Returns whether the filter passed the record for\n        emission.\n        "));
      var1.setlocal("handle", var4);
      var3 = null;
      var1.setline(756);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setFormatter$45, PyString.fromInterned("\n        Set the formatter for this handler.\n        "));
      var1.setlocal("setFormatter", var4);
      var3 = null;
      var1.setline(762);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, flush$46, PyString.fromInterned("\n        Ensure all logging output has been flushed.\n\n        This version does nothing and is intended to be implemented by\n        subclasses.\n        "));
      var1.setlocal("flush", var4);
      var3 = null;
      var1.setline(771);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$47, PyString.fromInterned("\n        Tidy up any resources used by the handler.\n\n        This version removes the handler from an internal map of handlers,\n        _handlers, which is used for handler lookup by name. Subclasses\n        should ensure that this gets called from overridden close()\n        methods.\n        "));
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(788);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, handleError$48, PyString.fromInterned("\n        Handle errors which occur during an emit() call.\n\n        This method should be called from handlers when an exception is\n        encountered during an emit() call. If raiseExceptions is false,\n        exceptions get silently ignored. This is what is mostly wanted\n        for a logging system - most users will not care about errors in\n        the logging system, they are more interested in application errors.\n        You could, however, replace this with a custom handler if you wish.\n        The record which was being processed is passed in to this method.\n        "));
      var1.setlocal("handleError", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$35(PyFrame var1, ThreadState var2) {
      var1.setline(661);
      PyString.fromInterned("\n        Initializes the instance - basically setting the formatter to None\n        and the filter list to empty.\n        ");
      var1.setline(662);
      var1.getglobal("Filterer").__getattr__("__init__").__call__(var2, var1.getlocal(0));
      var1.setline(663);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_name", var3);
      var3 = null;
      var1.setline(664);
      var3 = var1.getglobal("_checkLevel").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("level", var3);
      var3 = null;
      var1.setline(665);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("formatter", var3);
      var3 = null;
      var1.setline(667);
      var1.getglobal("_addHandlerRef").__call__(var2, var1.getlocal(0));
      var1.setline(668);
      var1.getlocal(0).__getattr__("createLock").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_name$36(PyFrame var1, ThreadState var2) {
      var1.setline(671);
      PyObject var3 = var1.getlocal(0).__getattr__("_name");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_name$37(PyFrame var1, ThreadState var2) {
      var1.setline(674);
      var1.getglobal("_acquireLock").__call__(var2);
      Object var3 = null;

      try {
         var1.setline(676);
         PyObject var4 = var1.getlocal(0).__getattr__("_name");
         PyObject var10000 = var4._in(var1.getglobal("_handlers"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(677);
            var1.getglobal("_handlers").__delitem__(var1.getlocal(0).__getattr__("_name"));
         }

         var1.setline(678);
         var4 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("_name", var4);
         var4 = null;
         var1.setline(679);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(680);
            var4 = var1.getlocal(0);
            var1.getglobal("_handlers").__setitem__(var1.getlocal(1), var4);
            var4 = null;
         }
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(682);
         var1.getglobal("_releaseLock").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(682);
      var1.getglobal("_releaseLock").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject createLock$38(PyFrame var1, ThreadState var2) {
      var1.setline(689);
      PyString.fromInterned("\n        Acquire a thread lock for serializing access to the underlying I/O.\n        ");
      var1.setline(690);
      PyObject var3;
      if (var1.getglobal("thread").__nonzero__()) {
         var1.setline(691);
         var3 = var1.getglobal("threading").__getattr__("RLock").__call__(var2);
         var1.getlocal(0).__setattr__("lock", var3);
         var3 = null;
      } else {
         var1.setline(693);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("lock", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject acquire$39(PyFrame var1, ThreadState var2) {
      var1.setline(698);
      PyString.fromInterned("\n        Acquire the I/O thread lock.\n        ");
      var1.setline(699);
      if (var1.getlocal(0).__getattr__("lock").__nonzero__()) {
         var1.setline(700);
         var1.getlocal(0).__getattr__("lock").__getattr__("acquire").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject release$40(PyFrame var1, ThreadState var2) {
      var1.setline(705);
      PyString.fromInterned("\n        Release the I/O thread lock.\n        ");
      var1.setline(706);
      if (var1.getlocal(0).__getattr__("lock").__nonzero__()) {
         var1.setline(707);
         var1.getlocal(0).__getattr__("lock").__getattr__("release").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setLevel$41(PyFrame var1, ThreadState var2) {
      var1.setline(712);
      PyString.fromInterned("\n        Set the logging level of this handler.\n        ");
      var1.setline(713);
      PyObject var3 = var1.getglobal("_checkLevel").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("level", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject format$42(PyFrame var1, ThreadState var2) {
      var1.setline(721);
      PyString.fromInterned("\n        Format the specified record.\n\n        If a formatter is set, use it. Otherwise, use the default formatter\n        for the module.\n        ");
      var1.setline(722);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("formatter").__nonzero__()) {
         var1.setline(723);
         var3 = var1.getlocal(0).__getattr__("formatter");
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(725);
         var3 = var1.getglobal("_defaultFormatter");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(726);
      var3 = var1.getlocal(2).__getattr__("format").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject emit$43(PyFrame var1, ThreadState var2) {
      var1.setline(734);
      PyString.fromInterned("\n        Do whatever it takes to actually log the specified logging record.\n\n        This version is intended to be implemented by subclasses and so\n        raises a NotImplementedError.\n        ");
      var1.setline(735);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("emit must be implemented by Handler subclasses")));
   }

   public PyObject handle$44(PyFrame var1, ThreadState var2) {
      var1.setline(746);
      PyString.fromInterned("\n        Conditionally emit the specified logging record.\n\n        Emission depends on filters which may have been added to the handler.\n        Wrap the actual emission of the record with acquisition/release of\n        the I/O thread lock. Returns whether the filter passed the record for\n        emission.\n        ");
      var1.setline(747);
      PyObject var3 = var1.getlocal(0).__getattr__("filter").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(748);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(749);
         var1.getlocal(0).__getattr__("acquire").__call__(var2);
         var3 = null;

         try {
            var1.setline(751);
            var1.getlocal(0).__getattr__("emit").__call__(var2, var1.getlocal(1));
         } catch (Throwable var4) {
            Py.addTraceback(var4, var1);
            var1.setline(753);
            var1.getlocal(0).__getattr__("release").__call__(var2);
            throw (Throwable)var4;
         }

         var1.setline(753);
         var1.getlocal(0).__getattr__("release").__call__(var2);
      }

      var1.setline(754);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setFormatter$45(PyFrame var1, ThreadState var2) {
      var1.setline(759);
      PyString.fromInterned("\n        Set the formatter for this handler.\n        ");
      var1.setline(760);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("formatter", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject flush$46(PyFrame var1, ThreadState var2) {
      var1.setline(768);
      PyString.fromInterned("\n        Ensure all logging output has been flushed.\n\n        This version does nothing and is intended to be implemented by\n        subclasses.\n        ");
      var1.setline(769);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$47(PyFrame var1, ThreadState var2) {
      var1.setline(779);
      PyString.fromInterned("\n        Tidy up any resources used by the handler.\n\n        This version removes the handler from an internal map of handlers,\n        _handlers, which is used for handler lookup by name. Subclasses\n        should ensure that this gets called from overridden close()\n        methods.\n        ");
      var1.setline(781);
      var1.getglobal("_acquireLock").__call__(var2);
      Object var3 = null;

      try {
         var1.setline(783);
         PyObject var10000 = var1.getlocal(0).__getattr__("_name");
         if (var10000.__nonzero__()) {
            PyObject var4 = var1.getlocal(0).__getattr__("_name");
            var10000 = var4._in(var1.getglobal("_handlers"));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(784);
            var1.getglobal("_handlers").__delitem__(var1.getlocal(0).__getattr__("_name"));
         }
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(786);
         var1.getglobal("_releaseLock").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(786);
      var1.getglobal("_releaseLock").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handleError$48(PyFrame var1, ThreadState var2) {
      var1.setline(799);
      PyString.fromInterned("\n        Handle errors which occur during an emit() call.\n\n        This method should be called from handlers when an exception is\n        encountered during an emit() call. If raiseExceptions is false,\n        exceptions get silently ignored. This is what is mostly wanted\n        for a logging system - most users will not care about errors in\n        the logging system, they are more interested in application errors.\n        You could, however, replace this with a custom handler if you wish.\n        The record which was being processed is passed in to this method.\n        ");
      var1.setline(800);
      PyObject var10000 = var1.getglobal("raiseExceptions");
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("sys").__getattr__("stderr");
      }

      if (var10000.__nonzero__()) {
         var1.setline(801);
         PyObject var3 = var1.getglobal("sys").__getattr__("exc_info").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
         var3 = null;

         try {
            try {
               var1.setline(803);
               var10000 = var1.getglobal("traceback").__getattr__("print_exception");
               PyObject[] var7 = new PyObject[]{var1.getlocal(2).__getitem__(Py.newInteger(0)), var1.getlocal(2).__getitem__(Py.newInteger(1)), var1.getlocal(2).__getitem__(Py.newInteger(2)), var1.getglobal("None"), var1.getglobal("sys").__getattr__("stderr")};
               var10000.__call__(var2, var7);
               var1.setline(805);
               var1.getglobal("sys").__getattr__("stderr").__getattr__("write").__call__(var2, PyString.fromInterned("Logged from file %s, line %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("filename"), var1.getlocal(1).__getattr__("lineno")})));
            } catch (Throwable var5) {
               PyException var4 = Py.setException(var5, var1);
               if (!var4.match(var1.getglobal("IOError"))) {
                  throw var4;
               }

               var1.setline(808);
            }
         } catch (Throwable var6) {
            Py.addTraceback(var6, var1);
            var1.setline(810);
            var1.dellocal(2);
            throw (Throwable)var6;
         }

         var1.setline(810);
         var1.dellocal(2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject StreamHandler$49(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    A handler class which writes logging records, appropriately formatted,\n    to a stream. Note that this class does not close the stream, as\n    sys.stdout or sys.stderr may be used.\n    "));
      var1.setline(817);
      PyString.fromInterned("\n    A handler class which writes logging records, appropriately formatted,\n    to a stream. Note that this class does not close the stream, as\n    sys.stdout or sys.stderr may be used.\n    ");
      var1.setline(819);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$50, PyString.fromInterned("\n        Initialize the handler.\n\n        If stream is not specified, sys.stderr is used.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(830);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, flush$51, PyString.fromInterned("\n        Flushes the stream.\n        "));
      var1.setlocal("flush", var4);
      var3 = null;
      var1.setline(841);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, emit$52, PyString.fromInterned("\n        Emit a record.\n\n        If a formatter is specified, it is used to format the record.\n        The record is then written to the stream with a trailing newline.  If\n        exception information is present, it is formatted using\n        traceback.print_exception and appended to the stream.  If the stream\n        has an 'encoding' attribute, it is used to determine how to do the\n        output to the stream.\n        "));
      var1.setlocal("emit", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$50(PyFrame var1, ThreadState var2) {
      var1.setline(824);
      PyString.fromInterned("\n        Initialize the handler.\n\n        If stream is not specified, sys.stderr is used.\n        ");
      var1.setline(825);
      var1.getglobal("Handler").__getattr__("__init__").__call__(var2, var1.getlocal(0));
      var1.setline(826);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(827);
         var3 = var1.getglobal("sys").__getattr__("stderr");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(828);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("stream", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject flush$51(PyFrame var1, ThreadState var2) {
      var1.setline(833);
      PyString.fromInterned("\n        Flushes the stream.\n        ");
      var1.setline(834);
      var1.getlocal(0).__getattr__("acquire").__call__(var2);
      Object var3 = null;

      try {
         var1.setline(836);
         PyObject var10000 = var1.getlocal(0).__getattr__("stream");
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("stream"), (PyObject)PyString.fromInterned("flush"));
         }

         if (var10000.__nonzero__()) {
            var1.setline(837);
            var1.getlocal(0).__getattr__("stream").__getattr__("flush").__call__(var2);
         }
      } catch (Throwable var4) {
         Py.addTraceback(var4, var1);
         var1.setline(839);
         var1.getlocal(0).__getattr__("release").__call__(var2);
         throw (Throwable)var4;
      }

      var1.setline(839);
      var1.getlocal(0).__getattr__("release").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject emit$52(PyFrame var1, ThreadState var2) {
      var1.setline(851);
      PyString.fromInterned("\n        Emit a record.\n\n        If a formatter is specified, it is used to format the record.\n        The record is then written to the stream with a trailing newline.  If\n        exception information is present, it is formatted using\n        traceback.print_exception and appended to the stream.  If the stream\n        has an 'encoding' attribute, it is used to determine how to do the\n        output to the stream.\n        ");

      PyException var3;
      try {
         var1.setline(853);
         PyObject var7 = var1.getlocal(0).__getattr__("format").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var7);
         var3 = null;
         var1.setline(854);
         var7 = var1.getlocal(0).__getattr__("stream");
         var1.setlocal(3, var7);
         var3 = null;
         var1.setline(855);
         PyString var8 = PyString.fromInterned("%s\n");
         var1.setlocal(4, var8);
         var3 = null;
         var1.setline(856);
         if (var1.getglobal("_unicode").__not__().__nonzero__()) {
            var1.setline(857);
            var1.getlocal(3).__getattr__("write").__call__(var2, var1.getlocal(4)._mod(var1.getlocal(2)));
         } else {
            try {
               var1.setline(860);
               PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("unicode"));
               if (var10000.__nonzero__()) {
                  var10000 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)PyString.fromInterned("encoding"), (PyObject)var1.getglobal("None"));
               }

               if (var10000.__nonzero__()) {
                  var1.setline(862);
                  var7 = var1.getlocal(4).__getattr__("decode").__call__(var2, var1.getlocal(3).__getattr__("encoding"));
                  var1.setlocal(5, var7);
                  var3 = null;

                  try {
                     var1.setline(864);
                     var1.getlocal(3).__getattr__("write").__call__(var2, var1.getlocal(5)._mod(var1.getlocal(2)));
                  } catch (Throwable var4) {
                     var3 = Py.setException(var4, var1);
                     if (!var3.match(var1.getglobal("UnicodeEncodeError"))) {
                        throw var3;
                     }

                     var1.setline(872);
                     var1.getlocal(3).__getattr__("write").__call__(var2, var1.getlocal(5)._mod(var1.getlocal(2)).__getattr__("encode").__call__(var2, var1.getlocal(3).__getattr__("encoding")));
                  }
               } else {
                  var1.setline(874);
                  var1.getlocal(3).__getattr__("write").__call__(var2, var1.getlocal(4)._mod(var1.getlocal(2)));
               }
            } catch (Throwable var5) {
               var3 = Py.setException(var5, var1);
               if (!var3.match(var1.getglobal("UnicodeError"))) {
                  throw var3;
               }

               var1.setline(876);
               var1.getlocal(3).__getattr__("write").__call__(var2, var1.getlocal(4)._mod(var1.getlocal(2).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("UTF-8"))));
            }
         }

         var1.setline(877);
         var1.getlocal(0).__getattr__("flush").__call__(var2);
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (var3.match(new PyTuple(new PyObject[]{var1.getglobal("KeyboardInterrupt"), var1.getglobal("SystemExit")}))) {
            var1.setline(879);
            throw Py.makeException();
         }

         var1.setline(881);
         var1.getlocal(0).__getattr__("handleError").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FileHandler$53(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    A handler class which writes formatted logging records to disk files.\n    "));
      var1.setline(886);
      PyString.fromInterned("\n    A handler class which writes formatted logging records to disk files.\n    ");
      var1.setline(887);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("a"), var1.getname("None"), Py.newInteger(0)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$54, PyString.fromInterned("\n        Open the specified file and use it as the stream for logging.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(906);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$55, PyString.fromInterned("\n        Closes the stream.\n        "));
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(921);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _open$56, PyString.fromInterned("\n        Open the current base file with the (original) mode and encoding.\n        Return the resulting stream.\n        "));
      var1.setlocal("_open", var4);
      var3 = null;
      var1.setline(932);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, emit$57, PyString.fromInterned("\n        Emit a record.\n\n        If the stream was not opened because 'delay' was specified in the\n        constructor, open it before calling the superclass's emit.\n        "));
      var1.setlocal("emit", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$54(PyFrame var1, ThreadState var2) {
      var1.setline(890);
      PyString.fromInterned("\n        Open the specified file and use it as the stream for logging.\n        ");
      var1.setline(893);
      PyObject var3 = var1.getglobal("codecs");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(894);
         var3 = var1.getglobal("None");
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(895);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("baseFilename", var3);
      var3 = null;
      var1.setline(896);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("mode", var3);
      var3 = null;
      var1.setline(897);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("encoding", var3);
      var3 = null;
      var1.setline(898);
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(901);
         var1.getglobal("Handler").__getattr__("__init__").__call__(var2, var1.getlocal(0));
         var1.setline(902);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("stream", var3);
         var3 = null;
      } else {
         var1.setline(904);
         var1.getglobal("StreamHandler").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(0).__getattr__("_open").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$55(PyFrame var1, ThreadState var2) {
      var1.setline(909);
      PyString.fromInterned("\n        Closes the stream.\n        ");
      var1.setline(910);
      var1.getlocal(0).__getattr__("acquire").__call__(var2);
      Object var3 = null;

      try {
         var1.setline(912);
         if (var1.getlocal(0).__getattr__("stream").__nonzero__()) {
            var1.setline(913);
            var1.getlocal(0).__getattr__("flush").__call__(var2);
            var1.setline(914);
            if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("stream"), (PyObject)PyString.fromInterned("close")).__nonzero__()) {
               var1.setline(915);
               var1.getlocal(0).__getattr__("stream").__getattr__("close").__call__(var2);
            }

            var1.setline(916);
            var1.getglobal("StreamHandler").__getattr__("close").__call__(var2, var1.getlocal(0));
            var1.setline(917);
            PyObject var4 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("stream", var4);
            var4 = null;
         }
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(919);
         var1.getlocal(0).__getattr__("release").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(919);
      var1.getlocal(0).__getattr__("release").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _open$56(PyFrame var1, ThreadState var2) {
      var1.setline(925);
      PyString.fromInterned("\n        Open the current base file with the (original) mode and encoding.\n        Return the resulting stream.\n        ");
      var1.setline(926);
      PyObject var3 = var1.getlocal(0).__getattr__("encoding");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(927);
         var3 = var1.getglobal("open").__call__(var2, var1.getlocal(0).__getattr__("baseFilename"), var1.getlocal(0).__getattr__("mode"));
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(929);
         var3 = var1.getglobal("codecs").__getattr__("open").__call__(var2, var1.getlocal(0).__getattr__("baseFilename"), var1.getlocal(0).__getattr__("mode"), var1.getlocal(0).__getattr__("encoding"));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(930);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject emit$57(PyFrame var1, ThreadState var2) {
      var1.setline(938);
      PyString.fromInterned("\n        Emit a record.\n\n        If the stream was not opened because 'delay' was specified in the\n        constructor, open it before calling the superclass's emit.\n        ");
      var1.setline(939);
      PyObject var3 = var1.getlocal(0).__getattr__("stream");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(940);
         var3 = var1.getlocal(0).__getattr__("_open").__call__(var2);
         var1.getlocal(0).__setattr__("stream", var3);
         var3 = null;
      }

      var1.setline(941);
      var1.getglobal("StreamHandler").__getattr__("emit").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject PlaceHolder$58(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    PlaceHolder instances are used in the Manager logger hierarchy to take\n    the place of nodes for which no loggers have been defined. This class is\n    intended for internal use only and not as part of the public API.\n    "));
      var1.setline(952);
      PyString.fromInterned("\n    PlaceHolder instances are used in the Manager logger hierarchy to take\n    the place of nodes for which no loggers have been defined. This class is\n    intended for internal use only and not as part of the public API.\n    ");
      var1.setline(953);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$59, PyString.fromInterned("\n        Initialize with the specified logger being a child of this placeholder.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(960);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, append$60, PyString.fromInterned("\n        Add the specified logger as a child of this placeholder.\n        "));
      var1.setlocal("append", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$59(PyFrame var1, ThreadState var2) {
      var1.setline(956);
      PyString.fromInterned("\n        Initialize with the specified logger being a child of this placeholder.\n        ");
      var1.setline(958);
      PyDictionary var3 = new PyDictionary(new PyObject[]{var1.getlocal(1), var1.getglobal("None")});
      var1.getlocal(0).__setattr__((String)"loggerMap", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject append$60(PyFrame var1, ThreadState var2) {
      var1.setline(963);
      PyString.fromInterned("\n        Add the specified logger as a child of this placeholder.\n        ");
      var1.setline(965);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._notin(var1.getlocal(0).__getattr__("loggerMap"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(967);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__getattr__("loggerMap").__setitem__(var1.getlocal(1), var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setLoggerClass$61(PyFrame var1, ThreadState var2) {
      var1.setline(979);
      PyString.fromInterned("\n    Set the class to be used when instantiating a logger. The class should\n    define __init__() such that only a name argument is required, and the\n    __init__() should call Logger.__init__()\n    ");
      var1.setline(980);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._ne(var1.getglobal("Logger"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(981);
         if (var1.getglobal("issubclass").__call__(var2, var1.getlocal(0), var1.getglobal("Logger")).__not__().__nonzero__()) {
            var1.setline(982);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("logger not derived from logging.Logger: ")._add(var1.getlocal(0).__getattr__("__name__"))));
         }
      }

      var1.setline(985);
      var3 = var1.getlocal(0);
      var1.setglobal("_loggerClass", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getLoggerClass$62(PyFrame var1, ThreadState var2) {
      var1.setline(990);
      PyString.fromInterned("\n    Return the class to be used when instantiating a logger.\n    ");
      var1.setline(992);
      PyObject var3 = var1.getglobal("_loggerClass");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Manager$63(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    There is [under normal circumstances] just one Manager instance, which\n    holds the hierarchy of loggers.\n    "));
      var1.setline(998);
      PyString.fromInterned("\n    There is [under normal circumstances] just one Manager instance, which\n    holds the hierarchy of loggers.\n    ");
      var1.setline(999);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$64, PyString.fromInterned("\n        Initialize the manager with the root node of the logger hierarchy.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1009);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getLogger$65, PyString.fromInterned("\n        Get a logger with the specified name (channel name), creating it\n        if it doesn't yet exist. This name is a dot-separated hierarchical\n        name, such as \"a\", \"a.b\", \"a.b.c\" or similar.\n\n        If a PlaceHolder existed for the specified name [i.e. the logger\n        didn't exist but a child of it did], replace it with the created\n        logger and fix up the parent/child references which pointed to the\n        placeholder to now point to the logger.\n        "));
      var1.setlocal("getLogger", var4);
      var3 = null;
      var1.setline(1045);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setLoggerClass$66, PyString.fromInterned("\n        Set the class to be used when instantiating a logger with this Manager.\n        "));
      var1.setlocal("setLoggerClass", var4);
      var3 = null;
      var1.setline(1055);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _fixupParents$67, PyString.fromInterned("\n        Ensure that there are either loggers or placeholders all the way\n        from the specified logger to the root of the logger hierarchy.\n        "));
      var1.setlocal("_fixupParents", var4);
      var3 = null;
      var1.setline(1079);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _fixupChildren$68, PyString.fromInterned("\n        Ensure that children of the placeholder ph are connected to the\n        specified logger.\n        "));
      var1.setlocal("_fixupChildren", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$64(PyFrame var1, ThreadState var2) {
      var1.setline(1002);
      PyString.fromInterned("\n        Initialize the manager with the root node of the logger hierarchy.\n        ");
      var1.setline(1003);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("root", var3);
      var3 = null;
      var1.setline(1004);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"disable", var4);
      var3 = null;
      var1.setline(1005);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"emittedNoHandlerWarning", var4);
      var3 = null;
      var1.setline(1006);
      PyDictionary var5 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"loggerDict", var5);
      var3 = null;
      var1.setline(1007);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("loggerClass", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getLogger$65(PyFrame var1, ThreadState var2) {
      var1.setline(1019);
      PyString.fromInterned("\n        Get a logger with the specified name (channel name), creating it\n        if it doesn't yet exist. This name is a dot-separated hierarchical\n        name, such as \"a\", \"a.b\", \"a.b.c\" or similar.\n\n        If a PlaceHolder existed for the specified name [i.e. the logger\n        didn't exist but a child of it did], replace it with the created\n        logger and fix up the parent/child references which pointed to the\n        placeholder to now point to the logger.\n        ");
      var1.setline(1020);
      PyObject var3 = var1.getglobal("None");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1021);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__not__().__nonzero__()) {
         var1.setline(1022);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("A logger name must be string or Unicode")));
      } else {
         var1.setline(1023);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("unicode")).__nonzero__()) {
            var1.setline(1024);
            var3 = var1.getlocal(1).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf-8"));
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(1025);
         var1.getglobal("_acquireLock").__call__(var2);
         var3 = null;

         try {
            var1.setline(1027);
            PyObject var4 = var1.getlocal(1);
            PyObject var10000 = var4._in(var1.getlocal(0).__getattr__("loggerDict"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1028);
               var4 = var1.getlocal(0).__getattr__("loggerDict").__getitem__(var1.getlocal(1));
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(1029);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("PlaceHolder")).__nonzero__()) {
                  var1.setline(1030);
                  var4 = var1.getlocal(2);
                  var1.setlocal(3, var4);
                  var4 = null;
                  var1.setline(1031);
                  var10000 = var1.getlocal(0).__getattr__("loggerClass");
                  if (!var10000.__nonzero__()) {
                     var10000 = var1.getglobal("_loggerClass");
                  }

                  var4 = var10000.__call__(var2, var1.getlocal(1));
                  var1.setlocal(2, var4);
                  var4 = null;
                  var1.setline(1032);
                  var4 = var1.getlocal(0);
                  var1.getlocal(2).__setattr__("manager", var4);
                  var4 = null;
                  var1.setline(1033);
                  var4 = var1.getlocal(2);
                  var1.getlocal(0).__getattr__("loggerDict").__setitem__(var1.getlocal(1), var4);
                  var4 = null;
                  var1.setline(1034);
                  var1.getlocal(0).__getattr__("_fixupChildren").__call__(var2, var1.getlocal(3), var1.getlocal(2));
                  var1.setline(1035);
                  var1.getlocal(0).__getattr__("_fixupParents").__call__(var2, var1.getlocal(2));
               }
            } else {
               var1.setline(1037);
               var10000 = var1.getlocal(0).__getattr__("loggerClass");
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getglobal("_loggerClass");
               }

               var4 = var10000.__call__(var2, var1.getlocal(1));
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(1038);
               var4 = var1.getlocal(0);
               var1.getlocal(2).__setattr__("manager", var4);
               var4 = null;
               var1.setline(1039);
               var4 = var1.getlocal(2);
               var1.getlocal(0).__getattr__("loggerDict").__setitem__(var1.getlocal(1), var4);
               var4 = null;
               var1.setline(1040);
               var1.getlocal(0).__getattr__("_fixupParents").__call__(var2, var1.getlocal(2));
            }
         } catch (Throwable var5) {
            Py.addTraceback(var5, var1);
            var1.setline(1042);
            var1.getglobal("_releaseLock").__call__(var2);
            throw (Throwable)var5;
         }

         var1.setline(1042);
         var1.getglobal("_releaseLock").__call__(var2);
         var1.setline(1043);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject setLoggerClass$66(PyFrame var1, ThreadState var2) {
      var1.setline(1048);
      PyString.fromInterned("\n        Set the class to be used when instantiating a logger with this Manager.\n        ");
      var1.setline(1049);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._ne(var1.getglobal("Logger"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1050);
         if (var1.getglobal("issubclass").__call__(var2, var1.getlocal(1), var1.getglobal("Logger")).__not__().__nonzero__()) {
            var1.setline(1051);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("logger not derived from logging.Logger: ")._add(var1.getlocal(1).__getattr__("__name__"))));
         }
      }

      var1.setline(1053);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("loggerClass", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _fixupParents$67(PyFrame var1, ThreadState var2) {
      var1.setline(1059);
      PyString.fromInterned("\n        Ensure that there are either loggers or placeholders all the way\n        from the specified logger to the root of the logger hierarchy.\n        ");
      var1.setline(1060);
      PyObject var3 = var1.getlocal(1).__getattr__("name");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1061);
      var3 = var1.getlocal(2).__getattr__("rfind").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1062);
      var3 = var1.getglobal("None");
      var1.setlocal(4, var3);
      var3 = null;

      while(true) {
         var1.setline(1063);
         var3 = var1.getlocal(3);
         PyObject var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(4).__not__();
         }

         if (!var10000.__nonzero__()) {
            var1.setline(1075);
            if (var1.getlocal(4).__not__().__nonzero__()) {
               var1.setline(1076);
               var3 = var1.getlocal(0).__getattr__("root");
               var1.setlocal(4, var3);
               var3 = null;
            }

            var1.setline(1077);
            var3 = var1.getlocal(4);
            var1.getlocal(1).__setattr__("parent", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(1064);
         var3 = var1.getlocal(2).__getslice__((PyObject)null, var1.getlocal(3), (PyObject)null);
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(1065);
         var3 = var1.getlocal(5);
         var10000 = var3._notin(var1.getlocal(0).__getattr__("loggerDict"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1066);
            var3 = var1.getglobal("PlaceHolder").__call__(var2, var1.getlocal(1));
            var1.getlocal(0).__getattr__("loggerDict").__setitem__(var1.getlocal(5), var3);
            var3 = null;
         } else {
            var1.setline(1068);
            var3 = var1.getlocal(0).__getattr__("loggerDict").__getitem__(var1.getlocal(5));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(1069);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(6), var1.getglobal("Logger")).__nonzero__()) {
               var1.setline(1070);
               var3 = var1.getlocal(6);
               var1.setlocal(4, var3);
               var3 = null;
            } else {
               var1.setline(1072);
               if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(6), var1.getglobal("PlaceHolder")).__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }

               var1.setline(1073);
               var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(1));
            }
         }

         var1.setline(1074);
         var3 = var1.getlocal(2).__getattr__("rfind").__call__((ThreadState)var2, PyString.fromInterned("."), (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(3)._sub(Py.newInteger(1)));
         var1.setlocal(3, var3);
         var3 = null;
      }
   }

   public PyObject _fixupChildren$68(PyFrame var1, ThreadState var2) {
      var1.setline(1083);
      PyString.fromInterned("\n        Ensure that children of the placeholder ph are connected to the\n        specified logger.\n        ");
      var1.setline(1084);
      PyObject var3 = var1.getlocal(2).__getattr__("name");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1085);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1086);
      var3 = var1.getlocal(1).__getattr__("loggerMap").__getattr__("keys").__call__(var2).__iter__();

      while(true) {
         var1.setline(1086);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(5, var4);
         var1.setline(1088);
         PyObject var5 = var1.getlocal(5).__getattr__("parent").__getattr__("name").__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null);
         PyObject var10000 = var5._ne(var1.getlocal(3));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1089);
            var5 = var1.getlocal(5).__getattr__("parent");
            var1.getlocal(2).__setattr__("parent", var5);
            var5 = null;
            var1.setline(1090);
            var5 = var1.getlocal(2);
            var1.getlocal(5).__setattr__("parent", var5);
            var5 = null;
         }
      }
   }

   public PyObject Logger$69(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    Instances of the Logger class represent a single logging channel. A\n    \"logging channel\" indicates an area of an application. Exactly how an\n    \"area\" is defined is up to the application developer. Since an\n    application can have any number of areas, logging channels are identified\n    by a unique string. Application areas can be nested (e.g. an area\n    of \"input processing\" might include sub-areas \"read CSV files\", \"read\n    XLS files\" and \"read Gnumeric files\"). To cater for this natural nesting,\n    channel names are organized into a namespace hierarchy where levels are\n    separated by periods, much like the Java or Python package namespace. So\n    in the instance given above, channel names might be \"input\" for the upper\n    level, and \"input.csv\", \"input.xls\" and \"input.gnu\" for the sub-levels.\n    There is no arbitrary limit to the depth of nesting.\n    "));
      var1.setline(1110);
      PyString.fromInterned("\n    Instances of the Logger class represent a single logging channel. A\n    \"logging channel\" indicates an area of an application. Exactly how an\n    \"area\" is defined is up to the application developer. Since an\n    application can have any number of areas, logging channels are identified\n    by a unique string. Application areas can be nested (e.g. an area\n    of \"input processing\" might include sub-areas \"read CSV files\", \"read\n    XLS files\" and \"read Gnumeric files\"). To cater for this natural nesting,\n    channel names are organized into a namespace hierarchy where levels are\n    separated by periods, much like the Java or Python package namespace. So\n    in the instance given above, channel names might be \"input\" for the upper\n    level, and \"input.csv\", \"input.xls\" and \"input.gnu\" for the sub-levels.\n    There is no arbitrary limit to the depth of nesting.\n    ");
      var1.setline(1111);
      PyObject[] var3 = new PyObject[]{var1.getname("NOTSET")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$70, PyString.fromInterned("\n        Initialize the logger with a name and an optional level.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1123);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setLevel$71, PyString.fromInterned("\n        Set the logging level of this logger.\n        "));
      var1.setlocal("setLevel", var4);
      var3 = null;
      var1.setline(1129);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, debug$72, PyString.fromInterned("\n        Log 'msg % args' with severity 'DEBUG'.\n\n        To pass exception information, use the keyword argument exc_info with\n        a true value, e.g.\n\n        logger.debug(\"Houston, we have a %s\", \"thorny problem\", exc_info=1)\n        "));
      var1.setlocal("debug", var4);
      var3 = null;
      var1.setline(1141);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, info$73, PyString.fromInterned("\n        Log 'msg % args' with severity 'INFO'.\n\n        To pass exception information, use the keyword argument exc_info with\n        a true value, e.g.\n\n        logger.info(\"Houston, we have a %s\", \"interesting problem\", exc_info=1)\n        "));
      var1.setlocal("info", var4);
      var3 = null;
      var1.setline(1153);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, warning$74, PyString.fromInterned("\n        Log 'msg % args' with severity 'WARNING'.\n\n        To pass exception information, use the keyword argument exc_info with\n        a true value, e.g.\n\n        logger.warning(\"Houston, we have a %s\", \"bit of a problem\", exc_info=1)\n        "));
      var1.setlocal("warning", var4);
      var3 = null;
      var1.setline(1165);
      PyObject var5 = var1.getname("warning");
      var1.setlocal("warn", var5);
      var3 = null;
      var1.setline(1167);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, error$75, PyString.fromInterned("\n        Log 'msg % args' with severity 'ERROR'.\n\n        To pass exception information, use the keyword argument exc_info with\n        a true value, e.g.\n\n        logger.error(\"Houston, we have a %s\", \"major problem\", exc_info=1)\n        "));
      var1.setlocal("error", var4);
      var3 = null;
      var1.setline(1179);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, exception$76, PyString.fromInterned("\n        Convenience method for logging an ERROR with exception information.\n        "));
      var1.setlocal("exception", var4);
      var3 = null;
      var1.setline(1186);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, critical$77, PyString.fromInterned("\n        Log 'msg % args' with severity 'CRITICAL'.\n\n        To pass exception information, use the keyword argument exc_info with\n        a true value, e.g.\n\n        logger.critical(\"Houston, we have a %s\", \"major disaster\", exc_info=1)\n        "));
      var1.setlocal("critical", var4);
      var3 = null;
      var1.setline(1198);
      var5 = var1.getname("critical");
      var1.setlocal("fatal", var5);
      var3 = null;
      var1.setline(1200);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, log$78, PyString.fromInterned("\n        Log 'msg % args' with the integer severity 'level'.\n\n        To pass exception information, use the keyword argument exc_info with\n        a true value, e.g.\n\n        logger.log(level, \"We have a %s\", \"mysterious problem\", exc_info=1)\n        "));
      var1.setlocal("log", var4);
      var3 = null;
      var1.setline(1217);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, findCaller$79, PyString.fromInterned("\n        Find the stack frame of the caller so that we can note the source\n        file name, line number and function name.\n        "));
      var1.setlocal("findCaller", var4);
      var3 = null;
      var1.setline(1238);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, makeRecord$80, PyString.fromInterned("\n        A factory method which can be overridden in subclasses to create\n        specialized LogRecords.\n        "));
      var1.setlocal("makeRecord", var4);
      var3 = null;
      var1.setline(1251);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, _log$81, PyString.fromInterned("\n        Low-level logging routine which creates a LogRecord and then calls\n        all the handlers of this logger to handle the record.\n        "));
      var1.setlocal("_log", var4);
      var3 = null;
      var1.setline(1272);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, handle$82, PyString.fromInterned("\n        Call the handlers for the specified record.\n\n        This method is used for unpickled records received from a socket, as\n        well as those created locally. Logger-level filtering is applied.\n        "));
      var1.setlocal("handle", var4);
      var3 = null;
      var1.setline(1282);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, addHandler$83, PyString.fromInterned("\n        Add the specified handler to this logger.\n        "));
      var1.setlocal("addHandler", var4);
      var3 = null;
      var1.setline(1293);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, removeHandler$84, PyString.fromInterned("\n        Remove the specified handler from this logger.\n        "));
      var1.setlocal("removeHandler", var4);
      var3 = null;
      var1.setline(1304);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, callHandlers$85, PyString.fromInterned("\n        Pass a record to all relevant handlers.\n\n        Loop through all handlers for this logger and its parents in the\n        logger hierarchy. If no handler was found, output a one-off error\n        message to sys.stderr. Stop searching up the hierarchy whenever a\n        logger with the \"propagate\" attribute set to zero is found - that\n        will be the last logger whose handlers are called.\n        "));
      var1.setlocal("callHandlers", var4);
      var3 = null;
      var1.setline(1330);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getEffectiveLevel$86, PyString.fromInterned("\n        Get the effective level for this logger.\n\n        Loop through this logger and its parents in the logger hierarchy,\n        looking for a non-zero logging level. Return the first one found.\n        "));
      var1.setlocal("getEffectiveLevel", var4);
      var3 = null;
      var1.setline(1344);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isEnabledFor$87, PyString.fromInterned("\n        Is this logger enabled for level 'level'?\n        "));
      var1.setlocal("isEnabledFor", var4);
      var3 = null;
      var1.setline(1352);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChild$88, PyString.fromInterned("\n        Get a logger which is a descendant to this one.\n\n        This is a convenience method, such that\n\n        logging.getLogger('abc').getChild('def.ghi')\n\n        is the same as\n\n        logging.getLogger('abc.def.ghi')\n\n        It's useful, for example, when the parent logger is named using\n        __name__ rather than a literal string.\n        "));
      var1.setlocal("getChild", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$70(PyFrame var1, ThreadState var2) {
      var1.setline(1114);
      PyString.fromInterned("\n        Initialize the logger with a name and an optional level.\n        ");
      var1.setline(1115);
      var1.getglobal("Filterer").__getattr__("__init__").__call__(var2, var1.getlocal(0));
      var1.setline(1116);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("name", var3);
      var3 = null;
      var1.setline(1117);
      var3 = var1.getglobal("_checkLevel").__call__(var2, var1.getlocal(2));
      var1.getlocal(0).__setattr__("level", var3);
      var3 = null;
      var1.setline(1118);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("parent", var3);
      var3 = null;
      var1.setline(1119);
      PyInteger var4 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"propagate", var4);
      var3 = null;
      var1.setline(1120);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"handlers", var5);
      var3 = null;
      var1.setline(1121);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"disabled", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setLevel$71(PyFrame var1, ThreadState var2) {
      var1.setline(1126);
      PyString.fromInterned("\n        Set the logging level of this logger.\n        ");
      var1.setline(1127);
      PyObject var3 = var1.getglobal("_checkLevel").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("level", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject debug$72(PyFrame var1, ThreadState var2) {
      var1.setline(1137);
      PyString.fromInterned("\n        Log 'msg % args' with severity 'DEBUG'.\n\n        To pass exception information, use the keyword argument exc_info with\n        a true value, e.g.\n\n        logger.debug(\"Houston, we have a %s\", \"thorny problem\", exc_info=1)\n        ");
      var1.setline(1138);
      if (var1.getlocal(0).__getattr__("isEnabledFor").__call__(var2, var1.getglobal("DEBUG")).__nonzero__()) {
         var1.setline(1139);
         PyObject var10000 = var1.getlocal(0).__getattr__("_log");
         PyObject[] var3 = new PyObject[]{var1.getglobal("DEBUG"), var1.getlocal(1), var1.getlocal(2)};
         String[] var4 = new String[0];
         var10000._callextra(var3, var4, (PyObject)null, var1.getlocal(3));
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject info$73(PyFrame var1, ThreadState var2) {
      var1.setline(1149);
      PyString.fromInterned("\n        Log 'msg % args' with severity 'INFO'.\n\n        To pass exception information, use the keyword argument exc_info with\n        a true value, e.g.\n\n        logger.info(\"Houston, we have a %s\", \"interesting problem\", exc_info=1)\n        ");
      var1.setline(1150);
      if (var1.getlocal(0).__getattr__("isEnabledFor").__call__(var2, var1.getglobal("INFO")).__nonzero__()) {
         var1.setline(1151);
         PyObject var10000 = var1.getlocal(0).__getattr__("_log");
         PyObject[] var3 = new PyObject[]{var1.getglobal("INFO"), var1.getlocal(1), var1.getlocal(2)};
         String[] var4 = new String[0];
         var10000._callextra(var3, var4, (PyObject)null, var1.getlocal(3));
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject warning$74(PyFrame var1, ThreadState var2) {
      var1.setline(1161);
      PyString.fromInterned("\n        Log 'msg % args' with severity 'WARNING'.\n\n        To pass exception information, use the keyword argument exc_info with\n        a true value, e.g.\n\n        logger.warning(\"Houston, we have a %s\", \"bit of a problem\", exc_info=1)\n        ");
      var1.setline(1162);
      if (var1.getlocal(0).__getattr__("isEnabledFor").__call__(var2, var1.getglobal("WARNING")).__nonzero__()) {
         var1.setline(1163);
         PyObject var10000 = var1.getlocal(0).__getattr__("_log");
         PyObject[] var3 = new PyObject[]{var1.getglobal("WARNING"), var1.getlocal(1), var1.getlocal(2)};
         String[] var4 = new String[0];
         var10000._callextra(var3, var4, (PyObject)null, var1.getlocal(3));
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject error$75(PyFrame var1, ThreadState var2) {
      var1.setline(1175);
      PyString.fromInterned("\n        Log 'msg % args' with severity 'ERROR'.\n\n        To pass exception information, use the keyword argument exc_info with\n        a true value, e.g.\n\n        logger.error(\"Houston, we have a %s\", \"major problem\", exc_info=1)\n        ");
      var1.setline(1176);
      if (var1.getlocal(0).__getattr__("isEnabledFor").__call__(var2, var1.getglobal("ERROR")).__nonzero__()) {
         var1.setline(1177);
         PyObject var10000 = var1.getlocal(0).__getattr__("_log");
         PyObject[] var3 = new PyObject[]{var1.getglobal("ERROR"), var1.getlocal(1), var1.getlocal(2)};
         String[] var4 = new String[0];
         var10000._callextra(var3, var4, (PyObject)null, var1.getlocal(3));
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject exception$76(PyFrame var1, ThreadState var2) {
      var1.setline(1182);
      PyString.fromInterned("\n        Convenience method for logging an ERROR with exception information.\n        ");
      var1.setline(1183);
      PyInteger var3 = Py.newInteger(1);
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("exc_info"), var3);
      var3 = null;
      var1.setline(1184);
      PyObject var10000 = var1.getlocal(0).__getattr__("error");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1)};
      String[] var4 = new String[0];
      var10000._callextra(var5, var4, var1.getlocal(2), var1.getlocal(3));
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject critical$77(PyFrame var1, ThreadState var2) {
      var1.setline(1194);
      PyString.fromInterned("\n        Log 'msg % args' with severity 'CRITICAL'.\n\n        To pass exception information, use the keyword argument exc_info with\n        a true value, e.g.\n\n        logger.critical(\"Houston, we have a %s\", \"major disaster\", exc_info=1)\n        ");
      var1.setline(1195);
      if (var1.getlocal(0).__getattr__("isEnabledFor").__call__(var2, var1.getglobal("CRITICAL")).__nonzero__()) {
         var1.setline(1196);
         PyObject var10000 = var1.getlocal(0).__getattr__("_log");
         PyObject[] var3 = new PyObject[]{var1.getglobal("CRITICAL"), var1.getlocal(1), var1.getlocal(2)};
         String[] var4 = new String[0];
         var10000._callextra(var3, var4, (PyObject)null, var1.getlocal(3));
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject log$78(PyFrame var1, ThreadState var2) {
      var1.setline(1208);
      PyString.fromInterned("\n        Log 'msg % args' with the integer severity 'level'.\n\n        To pass exception information, use the keyword argument exc_info with\n        a true value, e.g.\n\n        logger.log(level, \"We have a %s\", \"mysterious problem\", exc_info=1)\n        ");
      var1.setline(1209);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("int")).__not__().__nonzero__()) {
         var1.setline(1210);
         if (var1.getglobal("raiseExceptions").__nonzero__()) {
            var1.setline(1211);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("level must be an integer")));
         } else {
            var1.setline(1213);
            var1.f_lasti = -1;
            return Py.None;
         }
      } else {
         var1.setline(1214);
         if (var1.getlocal(0).__getattr__("isEnabledFor").__call__(var2, var1.getlocal(1)).__nonzero__()) {
            var1.setline(1215);
            PyObject var10000 = var1.getlocal(0).__getattr__("_log");
            PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)};
            String[] var4 = new String[0];
            var10000._callextra(var3, var4, (PyObject)null, var1.getlocal(4));
            var3 = null;
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject findCaller$79(PyFrame var1, ThreadState var2) {
      var1.setline(1221);
      PyString.fromInterned("\n        Find the stack frame of the caller so that we can note the source\n        file name, line number and function name.\n        ");
      var1.setline(1222);
      PyObject var3 = var1.getglobal("currentframe").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1225);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1226);
         var3 = var1.getlocal(1).__getattr__("f_back");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1227);
      PyTuple var4 = new PyTuple(new PyObject[]{PyString.fromInterned("(unknown file)"), Py.newInteger(0), PyString.fromInterned("(unknown function)")});
      var1.setlocal(2, var4);
      var3 = null;

      while(true) {
         var1.setline(1228);
         if (!var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("f_code")).__nonzero__()) {
            break;
         }

         var1.setline(1229);
         var3 = var1.getlocal(1).__getattr__("f_code");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1230);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("normcase").__call__(var2, var1.getlocal(3).__getattr__("co_filename"));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1231);
         var3 = var1.getlocal(4);
         var10000 = var3._eq(var1.getglobal("_srcfile"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(1234);
            var4 = new PyTuple(new PyObject[]{var1.getlocal(3).__getattr__("co_filename"), var1.getlocal(1).__getattr__("f_lineno"), var1.getlocal(3).__getattr__("co_name")});
            var1.setlocal(2, var4);
            var3 = null;
            break;
         }

         var1.setline(1232);
         var3 = var1.getlocal(1).__getattr__("f_back");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1236);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject makeRecord$80(PyFrame var1, ThreadState var2) {
      var1.setline(1242);
      PyString.fromInterned("\n        A factory method which can be overridden in subclasses to create\n        specialized LogRecords.\n        ");
      var1.setline(1243);
      PyObject var10000 = var1.getglobal("LogRecord");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7), var1.getlocal(8)};
      PyObject var6 = var10000.__call__(var2, var3);
      var1.setlocal(10, var6);
      var3 = null;
      var1.setline(1244);
      var6 = var1.getlocal(9);
      var10000 = var6._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1245);
         var6 = var1.getlocal(9).__iter__();

         while(true) {
            var1.setline(1245);
            PyObject var4 = var6.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(11, var4);
            var1.setline(1246);
            PyObject var5 = var1.getlocal(11);
            var10000 = var5._in(new PyList(new PyObject[]{PyString.fromInterned("message"), PyString.fromInterned("asctime")}));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var5 = var1.getlocal(11);
               var10000 = var5._in(var1.getlocal(10).__getattr__("__dict__"));
               var5 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(1247);
               throw Py.makeException(var1.getglobal("KeyError").__call__(var2, PyString.fromInterned("Attempt to overwrite %r in LogRecord")._mod(var1.getlocal(11))));
            }

            var1.setline(1248);
            var5 = var1.getlocal(9).__getitem__(var1.getlocal(11));
            var1.getlocal(10).__getattr__("__dict__").__setitem__(var1.getlocal(11), var5);
            var5 = null;
         }
      }

      var1.setline(1249);
      var6 = var1.getlocal(10);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject _log$81(PyFrame var1, ThreadState var2) {
      var1.setline(1255);
      PyString.fromInterned("\n        Low-level logging routine which creates a LogRecord and then calls\n        all the handlers of this logger to handle the record.\n        ");
      var1.setline(1256);
      PyTuple var3;
      PyObject[] var4;
      PyObject var5;
      PyObject var9;
      if (var1.getglobal("_srcfile").__nonzero__()) {
         try {
            var1.setline(1261);
            var9 = var1.getlocal(0).__getattr__("findCaller").__call__(var2);
            var4 = Py.unpackSequence(var9, 3);
            var5 = var4[0];
            var1.setlocal(6, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(7, var5);
            var5 = null;
            var5 = var4[2];
            var1.setlocal(8, var5);
            var5 = null;
            var3 = null;
         } catch (Throwable var7) {
            PyException var8 = Py.setException(var7, var1);
            if (!var8.match(var1.getglobal("ValueError"))) {
               throw var8;
            }

            var1.setline(1263);
            PyTuple var10 = new PyTuple(new PyObject[]{PyString.fromInterned("(unknown file)"), Py.newInteger(0), PyString.fromInterned("(unknown function)")});
            PyObject[] var12 = Py.unpackSequence(var10, 3);
            PyObject var6 = var12[0];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var12[1];
            var1.setlocal(7, var6);
            var6 = null;
            var6 = var12[2];
            var1.setlocal(8, var6);
            var6 = null;
            var4 = null;
         }
      } else {
         var1.setline(1265);
         var3 = new PyTuple(new PyObject[]{PyString.fromInterned("(unknown file)"), Py.newInteger(0), PyString.fromInterned("(unknown function)")});
         var4 = Py.unpackSequence(var3, 3);
         var5 = var4[0];
         var1.setlocal(6, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(7, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(8, var5);
         var5 = null;
         var3 = null;
      }

      var1.setline(1266);
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(1267);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("tuple")).__not__().__nonzero__()) {
            var1.setline(1268);
            var9 = var1.getglobal("sys").__getattr__("exc_info").__call__(var2);
            var1.setlocal(4, var9);
            var3 = null;
         }
      }

      var1.setline(1269);
      PyObject var10000 = var1.getlocal(0).__getattr__("makeRecord");
      PyObject[] var11 = new PyObject[]{var1.getlocal(0).__getattr__("name"), var1.getlocal(1), var1.getlocal(6), var1.getlocal(7), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(8), var1.getlocal(5)};
      var9 = var10000.__call__(var2, var11);
      var1.setlocal(9, var9);
      var3 = null;
      var1.setline(1270);
      var1.getlocal(0).__getattr__("handle").__call__(var2, var1.getlocal(9));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle$82(PyFrame var1, ThreadState var2) {
      var1.setline(1278);
      PyString.fromInterned("\n        Call the handlers for the specified record.\n\n        This method is used for unpickled records received from a socket, as\n        well as those created locally. Logger-level filtering is applied.\n        ");
      var1.setline(1279);
      PyObject var10000 = var1.getlocal(0).__getattr__("disabled").__not__();
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("filter").__call__(var2, var1.getlocal(1));
      }

      if (var10000.__nonzero__()) {
         var1.setline(1280);
         var1.getlocal(0).__getattr__("callHandlers").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addHandler$83(PyFrame var1, ThreadState var2) {
      var1.setline(1285);
      PyString.fromInterned("\n        Add the specified handler to this logger.\n        ");
      var1.setline(1286);
      var1.getglobal("_acquireLock").__call__(var2);
      Object var3 = null;

      try {
         var1.setline(1288);
         PyObject var4 = var1.getlocal(1);
         PyObject var10000 = var4._in(var1.getlocal(0).__getattr__("handlers"));
         var4 = null;
         if (var10000.__not__().__nonzero__()) {
            var1.setline(1289);
            var1.getlocal(0).__getattr__("handlers").__getattr__("append").__call__(var2, var1.getlocal(1));
         }
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(1291);
         var1.getglobal("_releaseLock").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(1291);
      var1.getglobal("_releaseLock").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject removeHandler$84(PyFrame var1, ThreadState var2) {
      var1.setline(1296);
      PyString.fromInterned("\n        Remove the specified handler from this logger.\n        ");
      var1.setline(1297);
      var1.getglobal("_acquireLock").__call__(var2);
      Object var3 = null;

      try {
         var1.setline(1299);
         PyObject var4 = var1.getlocal(1);
         PyObject var10000 = var4._in(var1.getlocal(0).__getattr__("handlers"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1300);
            var1.getlocal(0).__getattr__("handlers").__getattr__("remove").__call__(var2, var1.getlocal(1));
         }
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(1302);
         var1.getglobal("_releaseLock").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(1302);
      var1.getglobal("_releaseLock").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject callHandlers$85(PyFrame var1, ThreadState var2) {
      var1.setline(1313);
      PyString.fromInterned("\n        Pass a record to all relevant handlers.\n\n        Loop through all handlers for this logger and its parents in the\n        logger hierarchy. If no handler was found, output a one-off error\n        message to sys.stderr. Stop searching up the hierarchy whenever a\n        logger with the \"propagate\" attribute set to zero is found - that\n        will be the last logger whose handlers are called.\n        ");
      var1.setline(1314);
      PyObject var3 = var1.getlocal(0);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1315);
      PyInteger var6 = Py.newInteger(0);
      var1.setlocal(3, var6);
      var3 = null;

      while(true) {
         var1.setline(1316);
         PyObject var10000;
         if (!var1.getlocal(2).__nonzero__()) {
            var1.setline(1325);
            var3 = var1.getlocal(3);
            var10000 = var3._eq(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("raiseExceptions");
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(0).__getattr__("manager").__getattr__("emittedNoHandlerWarning").__not__();
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(1326);
               var1.getglobal("sys").__getattr__("stderr").__getattr__("write").__call__(var2, PyString.fromInterned("No handlers could be found for logger \"%s\"\n")._mod(var1.getlocal(0).__getattr__("name")));
               var1.setline(1328);
               var6 = Py.newInteger(1);
               var1.getlocal(0).__getattr__("manager").__setattr__((String)"emittedNoHandlerWarning", var6);
               var3 = null;
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(1317);
         var3 = var1.getlocal(2).__getattr__("handlers").__iter__();

         while(true) {
            var1.setline(1317);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(1321);
               if (var1.getlocal(2).__getattr__("propagate").__not__().__nonzero__()) {
                  var1.setline(1322);
                  var3 = var1.getglobal("None");
                  var1.setlocal(2, var3);
                  var3 = null;
               } else {
                  var1.setline(1324);
                  var3 = var1.getlocal(2).__getattr__("parent");
                  var1.setlocal(2, var3);
                  var3 = null;
               }
               break;
            }

            var1.setlocal(4, var4);
            var1.setline(1318);
            PyObject var5 = var1.getlocal(3)._add(Py.newInteger(1));
            var1.setlocal(3, var5);
            var5 = null;
            var1.setline(1319);
            var5 = var1.getlocal(1).__getattr__("levelno");
            var10000 = var5._ge(var1.getlocal(4).__getattr__("level"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1320);
               var1.getlocal(4).__getattr__("handle").__call__(var2, var1.getlocal(1));
            }
         }
      }
   }

   public PyObject getEffectiveLevel$86(PyFrame var1, ThreadState var2) {
      var1.setline(1336);
      PyString.fromInterned("\n        Get the effective level for this logger.\n\n        Loop through this logger and its parents in the logger hierarchy,\n        looking for a non-zero logging level. Return the first one found.\n        ");
      var1.setline(1337);
      PyObject var3 = var1.getlocal(0);
      var1.setlocal(1, var3);
      var3 = null;

      while(true) {
         var1.setline(1338);
         if (!var1.getlocal(1).__nonzero__()) {
            var1.setline(1342);
            var3 = var1.getglobal("NOTSET");
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(1339);
         if (var1.getlocal(1).__getattr__("level").__nonzero__()) {
            var1.setline(1340);
            var3 = var1.getlocal(1).__getattr__("level");
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(1341);
         PyObject var4 = var1.getlocal(1).__getattr__("parent");
         var1.setlocal(1, var4);
         var4 = null;
      }
   }

   public PyObject isEnabledFor$87(PyFrame var1, ThreadState var2) {
      var1.setline(1347);
      PyString.fromInterned("\n        Is this logger enabled for level 'level'?\n        ");
      var1.setline(1348);
      PyObject var3 = var1.getlocal(0).__getattr__("manager").__getattr__("disable");
      PyObject var10000 = var3._ge(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1349);
         PyInteger var5 = Py.newInteger(0);
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(1350);
         PyObject var4 = var1.getlocal(1);
         var10000 = var4._ge(var1.getlocal(0).__getattr__("getEffectiveLevel").__call__(var2));
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getChild$88(PyFrame var1, ThreadState var2) {
      var1.setline(1366);
      PyString.fromInterned("\n        Get a logger which is a descendant to this one.\n\n        This is a convenience method, such that\n\n        logging.getLogger('abc').getChild('def.ghi')\n\n        is the same as\n\n        logging.getLogger('abc.def.ghi')\n\n        It's useful, for example, when the parent logger is named using\n        __name__ rather than a literal string.\n        ");
      var1.setline(1367);
      PyObject var3 = var1.getlocal(0).__getattr__("root");
      PyObject var10000 = var3._isnot(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1368);
         var3 = PyString.fromInterned(".").__getattr__("join").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("name"), var1.getlocal(1)})));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1369);
      var3 = var1.getlocal(0).__getattr__("manager").__getattr__("getLogger").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject RootLogger$89(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    A root logger is not that different to any other logger, except that\n    it must have a logging level and there is only one instance of it in\n    the hierarchy.\n    "));
      var1.setline(1376);
      PyString.fromInterned("\n    A root logger is not that different to any other logger, except that\n    it must have a logging level and there is only one instance of it in\n    the hierarchy.\n    ");
      var1.setline(1377);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$90, PyString.fromInterned("\n        Initialize the logger with the name \"root\".\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$90(PyFrame var1, ThreadState var2) {
      var1.setline(1380);
      PyString.fromInterned("\n        Initialize the logger with the name \"root\".\n        ");
      var1.setline(1381);
      var1.getglobal("Logger").__getattr__("__init__").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("root"), (PyObject)var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject LoggerAdapter$91(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    An adapter for loggers which makes it easier to specify contextual\n    information in logging output.\n    "));
      var1.setline(1389);
      PyString.fromInterned("\n    An adapter for loggers which makes it easier to specify contextual\n    information in logging output.\n    ");
      var1.setline(1391);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$92, PyString.fromInterned("\n        Initialize the adapter with a logger and a dict-like object which\n        provides contextual information. This constructor signature allows\n        easy stacking of LoggerAdapters, if so desired.\n\n        You can effectively pass keyword arguments as shown in the\n        following example:\n\n        adapter = LoggerAdapter(someLogger, dict(p1=v1, p2=\"v2\"))\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1405);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, process$93, PyString.fromInterned("\n        Process the logging message and keyword arguments passed in to\n        a logging call to insert contextual information. You can either\n        manipulate the message itself, the keyword args or both. Return\n        the message and kwargs modified (or not) to suit your needs.\n\n        Normally, you'll only need to override this one method in a\n        LoggerAdapter subclass for your specific needs.\n        "));
      var1.setlocal("process", var4);
      var3 = null;
      var1.setline(1418);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, debug$94, PyString.fromInterned("\n        Delegate a debug call to the underlying logger, after adding\n        contextual information from this adapter instance.\n        "));
      var1.setlocal("debug", var4);
      var3 = null;
      var1.setline(1426);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, info$95, PyString.fromInterned("\n        Delegate an info call to the underlying logger, after adding\n        contextual information from this adapter instance.\n        "));
      var1.setlocal("info", var4);
      var3 = null;
      var1.setline(1434);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, warning$96, PyString.fromInterned("\n        Delegate a warning call to the underlying logger, after adding\n        contextual information from this adapter instance.\n        "));
      var1.setlocal("warning", var4);
      var3 = null;
      var1.setline(1442);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, error$97, PyString.fromInterned("\n        Delegate an error call to the underlying logger, after adding\n        contextual information from this adapter instance.\n        "));
      var1.setlocal("error", var4);
      var3 = null;
      var1.setline(1450);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, exception$98, PyString.fromInterned("\n        Delegate an exception call to the underlying logger, after adding\n        contextual information from this adapter instance.\n        "));
      var1.setlocal("exception", var4);
      var3 = null;
      var1.setline(1459);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, critical$99, PyString.fromInterned("\n        Delegate a critical call to the underlying logger, after adding\n        contextual information from this adapter instance.\n        "));
      var1.setlocal("critical", var4);
      var3 = null;
      var1.setline(1467);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, log$100, PyString.fromInterned("\n        Delegate a log call to the underlying logger, after adding\n        contextual information from this adapter instance.\n        "));
      var1.setlocal("log", var4);
      var3 = null;
      var1.setline(1475);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isEnabledFor$101, PyString.fromInterned("\n        See if the underlying logger is enabled for the specified level.\n        "));
      var1.setlocal("isEnabledFor", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$92(PyFrame var1, ThreadState var2) {
      var1.setline(1401);
      PyString.fromInterned("\n        Initialize the adapter with a logger and a dict-like object which\n        provides contextual information. This constructor signature allows\n        easy stacking of LoggerAdapters, if so desired.\n\n        You can effectively pass keyword arguments as shown in the\n        following example:\n\n        adapter = LoggerAdapter(someLogger, dict(p1=v1, p2=\"v2\"))\n        ");
      var1.setline(1402);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("logger", var3);
      var3 = null;
      var1.setline(1403);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("extra", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject process$93(PyFrame var1, ThreadState var2) {
      var1.setline(1414);
      PyString.fromInterned("\n        Process the logging message and keyword arguments passed in to\n        a logging call to insert contextual information. You can either\n        manipulate the message itself, the keyword args or both. Return\n        the message and kwargs modified (or not) to suit your needs.\n\n        Normally, you'll only need to override this one method in a\n        LoggerAdapter subclass for your specific needs.\n        ");
      var1.setline(1415);
      PyObject var3 = var1.getlocal(0).__getattr__("extra");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("extra"), var3);
      var3 = null;
      var1.setline(1416);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject debug$94(PyFrame var1, ThreadState var2) {
      var1.setline(1422);
      PyString.fromInterned("\n        Delegate a debug call to the underlying logger, after adding\n        contextual information from this adapter instance.\n        ");
      var1.setline(1423);
      PyObject var3 = var1.getlocal(0).__getattr__("process").__call__(var2, var1.getlocal(1), var1.getlocal(3));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(1424);
      PyObject var10000 = var1.getlocal(0).__getattr__("logger").__getattr__("debug");
      PyObject[] var6 = new PyObject[]{var1.getlocal(1)};
      String[] var7 = new String[0];
      var10000._callextra(var6, var7, var1.getlocal(2), var1.getlocal(3));
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject info$95(PyFrame var1, ThreadState var2) {
      var1.setline(1430);
      PyString.fromInterned("\n        Delegate an info call to the underlying logger, after adding\n        contextual information from this adapter instance.\n        ");
      var1.setline(1431);
      PyObject var3 = var1.getlocal(0).__getattr__("process").__call__(var2, var1.getlocal(1), var1.getlocal(3));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(1432);
      PyObject var10000 = var1.getlocal(0).__getattr__("logger").__getattr__("info");
      PyObject[] var6 = new PyObject[]{var1.getlocal(1)};
      String[] var7 = new String[0];
      var10000._callextra(var6, var7, var1.getlocal(2), var1.getlocal(3));
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject warning$96(PyFrame var1, ThreadState var2) {
      var1.setline(1438);
      PyString.fromInterned("\n        Delegate a warning call to the underlying logger, after adding\n        contextual information from this adapter instance.\n        ");
      var1.setline(1439);
      PyObject var3 = var1.getlocal(0).__getattr__("process").__call__(var2, var1.getlocal(1), var1.getlocal(3));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(1440);
      PyObject var10000 = var1.getlocal(0).__getattr__("logger").__getattr__("warning");
      PyObject[] var6 = new PyObject[]{var1.getlocal(1)};
      String[] var7 = new String[0];
      var10000._callextra(var6, var7, var1.getlocal(2), var1.getlocal(3));
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject error$97(PyFrame var1, ThreadState var2) {
      var1.setline(1446);
      PyString.fromInterned("\n        Delegate an error call to the underlying logger, after adding\n        contextual information from this adapter instance.\n        ");
      var1.setline(1447);
      PyObject var3 = var1.getlocal(0).__getattr__("process").__call__(var2, var1.getlocal(1), var1.getlocal(3));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(1448);
      PyObject var10000 = var1.getlocal(0).__getattr__("logger").__getattr__("error");
      PyObject[] var6 = new PyObject[]{var1.getlocal(1)};
      String[] var7 = new String[0];
      var10000._callextra(var6, var7, var1.getlocal(2), var1.getlocal(3));
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject exception$98(PyFrame var1, ThreadState var2) {
      var1.setline(1454);
      PyString.fromInterned("\n        Delegate an exception call to the underlying logger, after adding\n        contextual information from this adapter instance.\n        ");
      var1.setline(1455);
      PyObject var3 = var1.getlocal(0).__getattr__("process").__call__(var2, var1.getlocal(1), var1.getlocal(3));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(1456);
      PyInteger var6 = Py.newInteger(1);
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("exc_info"), var6);
      var3 = null;
      var1.setline(1457);
      PyObject var10000 = var1.getlocal(0).__getattr__("logger").__getattr__("error");
      PyObject[] var7 = new PyObject[]{var1.getlocal(1)};
      String[] var8 = new String[0];
      var10000._callextra(var7, var8, var1.getlocal(2), var1.getlocal(3));
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject critical$99(PyFrame var1, ThreadState var2) {
      var1.setline(1463);
      PyString.fromInterned("\n        Delegate a critical call to the underlying logger, after adding\n        contextual information from this adapter instance.\n        ");
      var1.setline(1464);
      PyObject var3 = var1.getlocal(0).__getattr__("process").__call__(var2, var1.getlocal(1), var1.getlocal(3));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(1465);
      PyObject var10000 = var1.getlocal(0).__getattr__("logger").__getattr__("critical");
      PyObject[] var6 = new PyObject[]{var1.getlocal(1)};
      String[] var7 = new String[0];
      var10000._callextra(var6, var7, var1.getlocal(2), var1.getlocal(3));
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject log$100(PyFrame var1, ThreadState var2) {
      var1.setline(1471);
      PyString.fromInterned("\n        Delegate a log call to the underlying logger, after adding\n        contextual information from this adapter instance.\n        ");
      var1.setline(1472);
      PyObject var3 = var1.getlocal(0).__getattr__("process").__call__(var2, var1.getlocal(2), var1.getlocal(4));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(1473);
      PyObject var10000 = var1.getlocal(0).__getattr__("logger").__getattr__("log");
      PyObject[] var6 = new PyObject[]{var1.getlocal(1), var1.getlocal(2)};
      String[] var7 = new String[0];
      var10000._callextra(var6, var7, var1.getlocal(3), var1.getlocal(4));
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject isEnabledFor$101(PyFrame var1, ThreadState var2) {
      var1.setline(1478);
      PyString.fromInterned("\n        See if the underlying logger is enabled for the specified level.\n        ");
      var1.setline(1479);
      PyObject var3 = var1.getlocal(0).__getattr__("logger").__getattr__("isEnabledFor").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject basicConfig$102(PyFrame var1, ThreadState var2) {
      var1.setline(1522);
      PyString.fromInterned("\n    Do basic configuration for the logging system.\n\n    This function does nothing if the root logger already has handlers\n    configured. It is a convenience method intended for use by simple scripts\n    to do one-shot configuration of the logging package.\n\n    The default behaviour is to create a StreamHandler which writes to\n    sys.stderr, set a formatter using the BASIC_FORMAT format string, and\n    add the handler to the root logger.\n\n    A number of optional keyword arguments may be specified, which can alter\n    the default behaviour.\n\n    filename  Specifies that a FileHandler be created, using the specified\n              filename, rather than a StreamHandler.\n    filemode  Specifies the mode to open the file, if filename is specified\n              (if filemode is unspecified, it defaults to 'a').\n    format    Use the specified format string for the handler.\n    datefmt   Use the specified date/time format.\n    level     Set the root logger level to the specified level.\n    stream    Use the specified stream to initialize the StreamHandler. Note\n              that this argument is incompatible with 'filename' - if both\n              are present, 'stream' is ignored.\n\n    Note that you could specify a stream created using open(filename, mode)\n    rather than passing the filename and mode in. However, it should be\n    remembered that StreamHandler does not close its stream (since it may be\n    using sys.stdout or sys.stderr), whereas FileHandler closes its stream\n    when the handler is closed.\n    ");
      var1.setline(1525);
      var1.getglobal("_acquireLock").__call__(var2);
      Object var3 = null;

      try {
         var1.setline(1527);
         PyObject var4 = var1.getglobal("len").__call__(var2, var1.getglobal("root").__getattr__("handlers"));
         PyObject var10000 = var4._eq(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1528);
            var4 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("filename"));
            var1.setlocal(1, var4);
            var4 = null;
            var1.setline(1529);
            if (var1.getlocal(1).__nonzero__()) {
               var1.setline(1530);
               var4 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("filemode"), (PyObject)PyString.fromInterned("a"));
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(1531);
               var4 = var1.getglobal("FileHandler").__call__(var2, var1.getlocal(1), var1.getlocal(2));
               var1.setlocal(3, var4);
               var4 = null;
            } else {
               var1.setline(1533);
               var4 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("stream"));
               var1.setlocal(4, var4);
               var4 = null;
               var1.setline(1534);
               var4 = var1.getglobal("StreamHandler").__call__(var2, var1.getlocal(4));
               var1.setlocal(3, var4);
               var4 = null;
            }

            var1.setline(1535);
            var4 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("format"), (PyObject)var1.getglobal("BASIC_FORMAT"));
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(1536);
            var4 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("datefmt"), (PyObject)var1.getglobal("None"));
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(1537);
            var4 = var1.getglobal("Formatter").__call__(var2, var1.getlocal(5), var1.getlocal(6));
            var1.setlocal(7, var4);
            var4 = null;
            var1.setline(1538);
            var1.getlocal(3).__getattr__("setFormatter").__call__(var2, var1.getlocal(7));
            var1.setline(1539);
            var1.getglobal("root").__getattr__("addHandler").__call__(var2, var1.getlocal(3));
            var1.setline(1540);
            var4 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("level"));
            var1.setlocal(8, var4);
            var4 = null;
            var1.setline(1541);
            var4 = var1.getlocal(8);
            var10000 = var4._isnot(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1542);
               var1.getglobal("root").__getattr__("setLevel").__call__(var2, var1.getlocal(8));
            }
         }
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(1544);
         var1.getglobal("_releaseLock").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(1544);
      var1.getglobal("_releaseLock").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getLogger$103(PyFrame var1, ThreadState var2) {
      var1.setline(1556);
      PyString.fromInterned("\n    Return a logger with the specified name, creating it if necessary.\n\n    If no name is specified, return the root logger.\n    ");
      var1.setline(1557);
      PyObject var3;
      if (var1.getlocal(0).__nonzero__()) {
         var1.setline(1558);
         var3 = var1.getglobal("Logger").__getattr__("manager").__getattr__("getLogger").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1560);
         var3 = var1.getglobal("root");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject critical$104(PyFrame var1, ThreadState var2) {
      var1.setline(1574);
      PyString.fromInterned("\n    Log a message with severity 'CRITICAL' on the root logger.\n    ");
      var1.setline(1575);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getglobal("root").__getattr__("handlers"));
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1576);
         var1.getglobal("basicConfig").__call__(var2);
      }

      var1.setline(1577);
      var10000 = var1.getglobal("root").__getattr__("critical");
      PyObject[] var5 = new PyObject[]{var1.getlocal(0)};
      String[] var4 = new String[0];
      var10000._callextra(var5, var4, var1.getlocal(1), var1.getlocal(2));
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject error$105(PyFrame var1, ThreadState var2) {
      var1.setline(1584);
      PyString.fromInterned("\n    Log a message with severity 'ERROR' on the root logger.\n    ");
      var1.setline(1585);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getglobal("root").__getattr__("handlers"));
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1586);
         var1.getglobal("basicConfig").__call__(var2);
      }

      var1.setline(1587);
      var10000 = var1.getglobal("root").__getattr__("error");
      PyObject[] var5 = new PyObject[]{var1.getlocal(0)};
      String[] var4 = new String[0];
      var10000._callextra(var5, var4, var1.getlocal(1), var1.getlocal(2));
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject exception$106(PyFrame var1, ThreadState var2) {
      var1.setline(1593);
      PyString.fromInterned("\n    Log a message with severity 'ERROR' on the root logger,\n    with exception information.\n    ");
      var1.setline(1594);
      PyInteger var3 = Py.newInteger(1);
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("exc_info"), var3);
      var3 = null;
      var1.setline(1595);
      PyObject var10000 = var1.getglobal("error");
      PyObject[] var5 = new PyObject[]{var1.getlocal(0)};
      String[] var4 = new String[0];
      var10000._callextra(var5, var4, var1.getlocal(1), var1.getlocal(2));
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject warning$107(PyFrame var1, ThreadState var2) {
      var1.setline(1600);
      PyString.fromInterned("\n    Log a message with severity 'WARNING' on the root logger.\n    ");
      var1.setline(1601);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getglobal("root").__getattr__("handlers"));
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1602);
         var1.getglobal("basicConfig").__call__(var2);
      }

      var1.setline(1603);
      var10000 = var1.getglobal("root").__getattr__("warning");
      PyObject[] var5 = new PyObject[]{var1.getlocal(0)};
      String[] var4 = new String[0];
      var10000._callextra(var5, var4, var1.getlocal(1), var1.getlocal(2));
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject info$108(PyFrame var1, ThreadState var2) {
      var1.setline(1610);
      PyString.fromInterned("\n    Log a message with severity 'INFO' on the root logger.\n    ");
      var1.setline(1611);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getglobal("root").__getattr__("handlers"));
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1612);
         var1.getglobal("basicConfig").__call__(var2);
      }

      var1.setline(1613);
      var10000 = var1.getglobal("root").__getattr__("info");
      PyObject[] var5 = new PyObject[]{var1.getlocal(0)};
      String[] var4 = new String[0];
      var10000._callextra(var5, var4, var1.getlocal(1), var1.getlocal(2));
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject debug$109(PyFrame var1, ThreadState var2) {
      var1.setline(1618);
      PyString.fromInterned("\n    Log a message with severity 'DEBUG' on the root logger.\n    ");
      var1.setline(1619);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getglobal("root").__getattr__("handlers"));
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1620);
         var1.getglobal("basicConfig").__call__(var2);
      }

      var1.setline(1621);
      var10000 = var1.getglobal("root").__getattr__("debug");
      PyObject[] var5 = new PyObject[]{var1.getlocal(0)};
      String[] var4 = new String[0];
      var10000._callextra(var5, var4, var1.getlocal(1), var1.getlocal(2));
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject log$110(PyFrame var1, ThreadState var2) {
      var1.setline(1626);
      PyString.fromInterned("\n    Log 'msg % args' with the integer severity 'level' on the root logger.\n    ");
      var1.setline(1627);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getglobal("root").__getattr__("handlers"));
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1628);
         var1.getglobal("basicConfig").__call__(var2);
      }

      var1.setline(1629);
      var10000 = var1.getglobal("root").__getattr__("log");
      PyObject[] var5 = new PyObject[]{var1.getlocal(0), var1.getlocal(1)};
      String[] var4 = new String[0];
      var10000._callextra(var5, var4, var1.getlocal(2), var1.getlocal(3));
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject disable$111(PyFrame var1, ThreadState var2) {
      var1.setline(1634);
      PyString.fromInterned("\n    Disable all logging calls of severity 'level' and below.\n    ");
      var1.setline(1635);
      PyObject var3 = var1.getlocal(0);
      var1.getglobal("root").__getattr__("manager").__setattr__("disable", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject shutdown$112(PyFrame var1, ThreadState var2) {
      var1.setline(1643);
      PyString.fromInterned("\n    Perform any cleanup actions in the logging system (e.g. flushing\n    buffers).\n\n    Should be called at application exit.\n    ");
      var1.setline(1644);
      PyObject var3 = var1.getglobal("reversed").__call__(var2, var1.getlocal(0).__getslice__((PyObject)null, (PyObject)null, (PyObject)null)).__iter__();

      while(true) {
         var1.setline(1644);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);

         try {
            var1.setline(1648);
            PyObject var5 = var1.getlocal(1).__call__(var2);
            var1.setlocal(2, var5);
            var5 = null;
            var1.setline(1649);
            if (var1.getlocal(2).__nonzero__()) {
               var5 = null;

               try {
                  try {
                     var1.setline(1651);
                     var1.getlocal(2).__getattr__("acquire").__call__(var2);
                     var1.setline(1652);
                     var1.getlocal(2).__getattr__("flush").__call__(var2);
                     var1.setline(1653);
                     var1.getlocal(2).__getattr__("close").__call__(var2);
                  } catch (Throwable var7) {
                     PyException var6 = Py.setException(var7, var1);
                     if (!var6.match(new PyTuple(new PyObject[]{var1.getglobal("IOError"), var1.getglobal("ValueError")}))) {
                        throw var6;
                     }

                     var1.setline(1659);
                  }
               } catch (Throwable var8) {
                  Py.addTraceback(var8, var1);
                  var1.setline(1661);
                  var1.getlocal(2).__getattr__("release").__call__(var2);
                  throw (Throwable)var8;
               }

               var1.setline(1661);
               var1.getlocal(2).__getattr__("release").__call__(var2);
            }
         } catch (Throwable var9) {
            Py.setException(var9, var1);
            var1.setline(1663);
            if (var1.getglobal("raiseExceptions").__nonzero__()) {
               var1.setline(1664);
               throw Py.makeException();
            }
         }
      }
   }

   public PyObject NullHandler$113(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    This handler does nothing. It's intended to be used to avoid the\n    \"No handlers could be found for logger XXX\" one-off warning. This is\n    important for library code, which may contain code to log events. If a user\n    of the library does not configure logging, the one-off warning might be\n    produced; to avoid this, the library developer simply needs to instantiate\n    a NullHandler and add it to the top-level logger of the library module or\n    package.\n    "));
      var1.setline(1682);
      PyString.fromInterned("\n    This handler does nothing. It's intended to be used to avoid the\n    \"No handlers could be found for logger XXX\" one-off warning. This is\n    important for library code, which may contain code to log events. If a user\n    of the library does not configure logging, the one-off warning might be\n    produced; to avoid this, the library developer simply needs to instantiate\n    a NullHandler and add it to the top-level logger of the library module or\n    package.\n    ");
      var1.setline(1683);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, handle$114, (PyObject)null);
      var1.setlocal("handle", var4);
      var3 = null;
      var1.setline(1686);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, emit$115, (PyObject)null);
      var1.setlocal("emit", var4);
      var3 = null;
      var1.setline(1689);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, createLock$116, (PyObject)null);
      var1.setlocal("createLock", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject handle$114(PyFrame var1, ThreadState var2) {
      var1.setline(1684);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject emit$115(PyFrame var1, ThreadState var2) {
      var1.setline(1687);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject createLock$116(PyFrame var1, ThreadState var2) {
      var1.setline(1690);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("lock", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _showwarning$117(PyFrame var1, ThreadState var2) {
      var1.setline(1703);
      PyString.fromInterned("\n    Implementation of showwarnings which redirects to logging, which will first\n    check to see if the file parameter is None. If a file is specified, it will\n    delegate to the original warnings implementation of showwarning. Otherwise,\n    it will call warnings.formatwarning and will log the resulting string to a\n    warnings logger named \"py.warnings\" with level logging.WARNING.\n    ");
      var1.setline(1704);
      PyObject var3 = var1.getlocal(4);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      PyObject[] var4;
      if (var10000.__nonzero__()) {
         var1.setline(1705);
         var3 = var1.getglobal("_warnings_showwarning");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1706);
            var10000 = var1.getglobal("_warnings_showwarning");
            var4 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)};
            var10000.__call__(var2, var4);
         }
      } else {
         var1.setline(1708);
         var10000 = var1.getglobal("warnings").__getattr__("formatwarning");
         var4 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(5)};
         var3 = var10000.__call__(var2, var4);
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(1709);
         var3 = var1.getglobal("getLogger").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("py.warnings"));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(1710);
         if (var1.getlocal(7).__getattr__("handlers").__not__().__nonzero__()) {
            var1.setline(1711);
            var1.getlocal(7).__getattr__("addHandler").__call__(var2, var1.getglobal("NullHandler").__call__(var2));
         }

         var1.setline(1712);
         var1.getlocal(7).__getattr__("warning").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%s"), (PyObject)var1.getlocal(6));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject captureWarnings$118(PyFrame var1, ThreadState var2) {
      var1.setline(1719);
      PyString.fromInterned("\n    If capture is true, redirect all warnings to the logging package.\n    If capture is False, ensure that warnings are not redirected to logging\n    but to their original destinations.\n    ");
      var1.setline(1721);
      PyObject var10000;
      PyObject var3;
      if (var1.getlocal(0).__nonzero__()) {
         var1.setline(1722);
         var3 = var1.getglobal("_warnings_showwarning");
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1723);
            var3 = var1.getglobal("warnings").__getattr__("showwarning");
            var1.setglobal("_warnings_showwarning", var3);
            var3 = null;
            var1.setline(1724);
            var3 = var1.getglobal("_showwarning");
            var1.getglobal("warnings").__setattr__("showwarning", var3);
            var3 = null;
         }
      } else {
         var1.setline(1726);
         var3 = var1.getglobal("_warnings_showwarning");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1727);
            var3 = var1.getglobal("_warnings_showwarning");
            var1.getglobal("warnings").__setattr__("showwarning", var3);
            var3 = null;
            var1.setline(1728);
            var3 = var1.getglobal("None");
            var1.setglobal("_warnings_showwarning", var3);
            var3 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public logging$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      currentframe$1 = Py.newCode(0, var2, var1, "currentframe", 76, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      f$2 = Py.newCode(0, var2, var1, "<lambda>", 83, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"level"};
      getLevelName$3 = Py.newCode(1, var2, var1, "getLevelName", 155, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"level", "levelName"};
      addLevelName$4 = Py.newCode(2, var2, var1, "addLevelName", 171, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"level", "rv"};
      _checkLevel$5 = Py.newCode(1, var2, var1, "_checkLevel", 184, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _acquireLock$6 = Py.newCode(0, var2, var1, "_acquireLock", 212, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _releaseLock$7 = Py.newCode(0, var2, var1, "_releaseLock", 221, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      LogRecord$8 = Py.newCode(0, var2, var1, "LogRecord", 232, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name", "level", "pathname", "lineno", "msg", "args", "exc_info", "func", "ct", "mp"};
      __init__$9 = Py.newCode(9, var2, var1, "__init__", 244, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$10 = Py.newCode(1, var2, var1, "__str__", 309, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      getMessage$11 = Py.newCode(1, var2, var1, "getMessage", 313, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"dict", "rv"};
      makeLogRecord$12 = Py.newCode(1, var2, var1, "makeLogRecord", 333, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Formatter$13 = Py.newCode(0, var2, var1, "Formatter", 348, false, false, self, 13, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fmt", "datefmt"};
      __init__$14 = Py.newCode(3, var2, var1, "__init__", 392, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record", "datefmt", "ct", "s", "t"};
      formatTime$15 = Py.newCode(3, var2, var1, "formatTime", 406, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ei", "sio", "s"};
      formatException$16 = Py.newCode(2, var2, var1, "formatException", 432, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      usesTime$17 = Py.newCode(1, var2, var1, "usesTime", 447, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record", "s"};
      format$18 = Py.newCode(2, var2, var1, "format", 453, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BufferingFormatter$19 = Py.newCode(0, var2, var1, "BufferingFormatter", 496, false, false, self, 19, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "linefmt"};
      __init__$20 = Py.newCode(2, var2, var1, "__init__", 500, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "records"};
      formatHeader$21 = Py.newCode(2, var2, var1, "formatHeader", 510, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "records"};
      formatFooter$22 = Py.newCode(2, var2, var1, "formatFooter", 516, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "records", "rv", "record"};
      format$23 = Py.newCode(2, var2, var1, "format", 522, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Filter$24 = Py.newCode(0, var2, var1, "Filter", 538, false, false, self, 24, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name"};
      __init__$25 = Py.newCode(2, var2, var1, "__init__", 549, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record"};
      filter$26 = Py.newCode(2, var2, var1, "filter", 560, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Filterer$27 = Py.newCode(0, var2, var1, "Filterer", 575, false, false, self, 27, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$28 = Py.newCode(1, var2, var1, "__init__", 580, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filter"};
      addFilter$29 = Py.newCode(2, var2, var1, "addFilter", 586, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filter"};
      removeFilter$30 = Py.newCode(2, var2, var1, "removeFilter", 593, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record", "rv", "f"};
      filter$31 = Py.newCode(2, var2, var1, "filter", 600, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"wr"};
      _removeHandlerRef$32 = Py.newCode(1, var2, var1, "_removeHandlerRef", 622, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"handler"};
      _addHandlerRef$33 = Py.newCode(1, var2, var1, "_addHandlerRef", 638, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Handler$34 = Py.newCode(0, var2, var1, "Handler", 648, false, false, self, 34, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "level"};
      __init__$35 = Py.newCode(2, var2, var1, "__init__", 657, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_name$36 = Py.newCode(1, var2, var1, "get_name", 670, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      set_name$37 = Py.newCode(2, var2, var1, "set_name", 673, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      createLock$38 = Py.newCode(1, var2, var1, "createLock", 686, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      acquire$39 = Py.newCode(1, var2, var1, "acquire", 695, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      release$40 = Py.newCode(1, var2, var1, "release", 702, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "level"};
      setLevel$41 = Py.newCode(2, var2, var1, "setLevel", 709, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record", "fmt"};
      format$42 = Py.newCode(2, var2, var1, "format", 715, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record"};
      emit$43 = Py.newCode(2, var2, var1, "emit", 728, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record", "rv"};
      handle$44 = Py.newCode(2, var2, var1, "handle", 738, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fmt"};
      setFormatter$45 = Py.newCode(2, var2, var1, "setFormatter", 756, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      flush$46 = Py.newCode(1, var2, var1, "flush", 762, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$47 = Py.newCode(1, var2, var1, "close", 771, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record", "ei"};
      handleError$48 = Py.newCode(2, var2, var1, "handleError", 788, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StreamHandler$49 = Py.newCode(0, var2, var1, "StreamHandler", 812, false, false, self, 49, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "stream"};
      __init__$50 = Py.newCode(2, var2, var1, "__init__", 819, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      flush$51 = Py.newCode(1, var2, var1, "flush", 830, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record", "msg", "stream", "fs", "ufs"};
      emit$52 = Py.newCode(2, var2, var1, "emit", 841, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FileHandler$53 = Py.newCode(0, var2, var1, "FileHandler", 883, false, false, self, 53, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "filename", "mode", "encoding", "delay"};
      __init__$54 = Py.newCode(5, var2, var1, "__init__", 887, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$55 = Py.newCode(1, var2, var1, "close", 906, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "stream"};
      _open$56 = Py.newCode(1, var2, var1, "_open", 921, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record"};
      emit$57 = Py.newCode(2, var2, var1, "emit", 932, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      PlaceHolder$58 = Py.newCode(0, var2, var1, "PlaceHolder", 947, false, false, self, 58, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "alogger"};
      __init__$59 = Py.newCode(2, var2, var1, "__init__", 953, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "alogger"};
      append$60 = Py.newCode(2, var2, var1, "append", 960, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"klass"};
      setLoggerClass$61 = Py.newCode(1, var2, var1, "setLoggerClass", 974, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      getLoggerClass$62 = Py.newCode(0, var2, var1, "getLoggerClass", 987, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Manager$63 = Py.newCode(0, var2, var1, "Manager", 994, false, false, self, 63, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "rootnode"};
      __init__$64 = Py.newCode(2, var2, var1, "__init__", 999, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "rv", "ph"};
      getLogger$65 = Py.newCode(2, var2, var1, "getLogger", 1009, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "klass"};
      setLoggerClass$66 = Py.newCode(2, var2, var1, "setLoggerClass", 1045, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "alogger", "name", "i", "rv", "substr", "obj"};
      _fixupParents$67 = Py.newCode(2, var2, var1, "_fixupParents", 1055, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ph", "alogger", "name", "namelen", "c"};
      _fixupChildren$68 = Py.newCode(3, var2, var1, "_fixupChildren", 1079, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Logger$69 = Py.newCode(0, var2, var1, "Logger", 1096, false, false, self, 69, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name", "level"};
      __init__$70 = Py.newCode(3, var2, var1, "__init__", 1111, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "level"};
      setLevel$71 = Py.newCode(2, var2, var1, "setLevel", 1123, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "args", "kwargs"};
      debug$72 = Py.newCode(4, var2, var1, "debug", 1129, true, true, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "args", "kwargs"};
      info$73 = Py.newCode(4, var2, var1, "info", 1141, true, true, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "args", "kwargs"};
      warning$74 = Py.newCode(4, var2, var1, "warning", 1153, true, true, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "args", "kwargs"};
      error$75 = Py.newCode(4, var2, var1, "error", 1167, true, true, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "args", "kwargs"};
      exception$76 = Py.newCode(4, var2, var1, "exception", 1179, true, true, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "args", "kwargs"};
      critical$77 = Py.newCode(4, var2, var1, "critical", 1186, true, true, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "level", "msg", "args", "kwargs"};
      log$78 = Py.newCode(5, var2, var1, "log", 1200, true, true, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "f", "rv", "co", "filename"};
      findCaller$79 = Py.newCode(1, var2, var1, "findCaller", 1217, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "level", "fn", "lno", "msg", "args", "exc_info", "func", "extra", "rv", "key"};
      makeRecord$80 = Py.newCode(10, var2, var1, "makeRecord", 1238, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "level", "msg", "args", "exc_info", "extra", "fn", "lno", "func", "record"};
      _log$81 = Py.newCode(6, var2, var1, "_log", 1251, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record"};
      handle$82 = Py.newCode(2, var2, var1, "handle", 1272, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "hdlr"};
      addHandler$83 = Py.newCode(2, var2, var1, "addHandler", 1282, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "hdlr"};
      removeHandler$84 = Py.newCode(2, var2, var1, "removeHandler", 1293, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record", "c", "found", "hdlr"};
      callHandlers$85 = Py.newCode(2, var2, var1, "callHandlers", 1304, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "logger"};
      getEffectiveLevel$86 = Py.newCode(1, var2, var1, "getEffectiveLevel", 1330, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "level"};
      isEnabledFor$87 = Py.newCode(2, var2, var1, "isEnabledFor", 1344, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "suffix"};
      getChild$88 = Py.newCode(2, var2, var1, "getChild", 1352, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      RootLogger$89 = Py.newCode(0, var2, var1, "RootLogger", 1371, false, false, self, 89, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "level"};
      __init__$90 = Py.newCode(2, var2, var1, "__init__", 1377, false, false, self, 90, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      LoggerAdapter$91 = Py.newCode(0, var2, var1, "LoggerAdapter", 1385, false, false, self, 91, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "logger", "extra"};
      __init__$92 = Py.newCode(3, var2, var1, "__init__", 1391, false, false, self, 92, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "kwargs"};
      process$93 = Py.newCode(3, var2, var1, "process", 1405, false, false, self, 93, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "args", "kwargs"};
      debug$94 = Py.newCode(4, var2, var1, "debug", 1418, true, true, self, 94, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "args", "kwargs"};
      info$95 = Py.newCode(4, var2, var1, "info", 1426, true, true, self, 95, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "args", "kwargs"};
      warning$96 = Py.newCode(4, var2, var1, "warning", 1434, true, true, self, 96, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "args", "kwargs"};
      error$97 = Py.newCode(4, var2, var1, "error", 1442, true, true, self, 97, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "args", "kwargs"};
      exception$98 = Py.newCode(4, var2, var1, "exception", 1450, true, true, self, 98, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "args", "kwargs"};
      critical$99 = Py.newCode(4, var2, var1, "critical", 1459, true, true, self, 99, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "level", "msg", "args", "kwargs"};
      log$100 = Py.newCode(5, var2, var1, "log", 1467, true, true, self, 100, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "level"};
      isEnabledFor$101 = Py.newCode(2, var2, var1, "isEnabledFor", 1475, false, false, self, 101, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"kwargs", "filename", "mode", "hdlr", "stream", "fs", "dfs", "fmt", "level"};
      basicConfig$102 = Py.newCode(1, var2, var1, "basicConfig", 1491, false, true, self, 102, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name"};
      getLogger$103 = Py.newCode(1, var2, var1, "getLogger", 1551, false, false, self, 103, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"msg", "args", "kwargs"};
      critical$104 = Py.newCode(3, var2, var1, "critical", 1571, true, true, self, 104, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"msg", "args", "kwargs"};
      error$105 = Py.newCode(3, var2, var1, "error", 1581, true, true, self, 105, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"msg", "args", "kwargs"};
      exception$106 = Py.newCode(3, var2, var1, "exception", 1589, true, true, self, 106, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"msg", "args", "kwargs"};
      warning$107 = Py.newCode(3, var2, var1, "warning", 1597, true, true, self, 107, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"msg", "args", "kwargs"};
      info$108 = Py.newCode(3, var2, var1, "info", 1607, true, true, self, 108, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"msg", "args", "kwargs"};
      debug$109 = Py.newCode(3, var2, var1, "debug", 1615, true, true, self, 109, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"level", "msg", "args", "kwargs"};
      log$110 = Py.newCode(4, var2, var1, "log", 1623, true, true, self, 110, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"level"};
      disable$111 = Py.newCode(1, var2, var1, "disable", 1631, false, false, self, 111, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"handlerList", "wr", "h"};
      shutdown$112 = Py.newCode(1, var2, var1, "shutdown", 1637, false, false, self, 112, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      NullHandler$113 = Py.newCode(0, var2, var1, "NullHandler", 1673, false, false, self, 113, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "record"};
      handle$114 = Py.newCode(2, var2, var1, "handle", 1683, false, false, self, 114, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "record"};
      emit$115 = Py.newCode(2, var2, var1, "emit", 1686, false, false, self, 115, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      createLock$116 = Py.newCode(1, var2, var1, "createLock", 1689, false, false, self, 116, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"message", "category", "filename", "lineno", "file", "line", "s", "logger"};
      _showwarning$117 = Py.newCode(6, var2, var1, "_showwarning", 1696, false, false, self, 117, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"capture"};
      captureWarnings$118 = Py.newCode(1, var2, var1, "captureWarnings", 1714, false, false, self, 118, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new logging$py("logging$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(logging$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.currentframe$1(var2, var3);
         case 2:
            return this.f$2(var2, var3);
         case 3:
            return this.getLevelName$3(var2, var3);
         case 4:
            return this.addLevelName$4(var2, var3);
         case 5:
            return this._checkLevel$5(var2, var3);
         case 6:
            return this._acquireLock$6(var2, var3);
         case 7:
            return this._releaseLock$7(var2, var3);
         case 8:
            return this.LogRecord$8(var2, var3);
         case 9:
            return this.__init__$9(var2, var3);
         case 10:
            return this.__str__$10(var2, var3);
         case 11:
            return this.getMessage$11(var2, var3);
         case 12:
            return this.makeLogRecord$12(var2, var3);
         case 13:
            return this.Formatter$13(var2, var3);
         case 14:
            return this.__init__$14(var2, var3);
         case 15:
            return this.formatTime$15(var2, var3);
         case 16:
            return this.formatException$16(var2, var3);
         case 17:
            return this.usesTime$17(var2, var3);
         case 18:
            return this.format$18(var2, var3);
         case 19:
            return this.BufferingFormatter$19(var2, var3);
         case 20:
            return this.__init__$20(var2, var3);
         case 21:
            return this.formatHeader$21(var2, var3);
         case 22:
            return this.formatFooter$22(var2, var3);
         case 23:
            return this.format$23(var2, var3);
         case 24:
            return this.Filter$24(var2, var3);
         case 25:
            return this.__init__$25(var2, var3);
         case 26:
            return this.filter$26(var2, var3);
         case 27:
            return this.Filterer$27(var2, var3);
         case 28:
            return this.__init__$28(var2, var3);
         case 29:
            return this.addFilter$29(var2, var3);
         case 30:
            return this.removeFilter$30(var2, var3);
         case 31:
            return this.filter$31(var2, var3);
         case 32:
            return this._removeHandlerRef$32(var2, var3);
         case 33:
            return this._addHandlerRef$33(var2, var3);
         case 34:
            return this.Handler$34(var2, var3);
         case 35:
            return this.__init__$35(var2, var3);
         case 36:
            return this.get_name$36(var2, var3);
         case 37:
            return this.set_name$37(var2, var3);
         case 38:
            return this.createLock$38(var2, var3);
         case 39:
            return this.acquire$39(var2, var3);
         case 40:
            return this.release$40(var2, var3);
         case 41:
            return this.setLevel$41(var2, var3);
         case 42:
            return this.format$42(var2, var3);
         case 43:
            return this.emit$43(var2, var3);
         case 44:
            return this.handle$44(var2, var3);
         case 45:
            return this.setFormatter$45(var2, var3);
         case 46:
            return this.flush$46(var2, var3);
         case 47:
            return this.close$47(var2, var3);
         case 48:
            return this.handleError$48(var2, var3);
         case 49:
            return this.StreamHandler$49(var2, var3);
         case 50:
            return this.__init__$50(var2, var3);
         case 51:
            return this.flush$51(var2, var3);
         case 52:
            return this.emit$52(var2, var3);
         case 53:
            return this.FileHandler$53(var2, var3);
         case 54:
            return this.__init__$54(var2, var3);
         case 55:
            return this.close$55(var2, var3);
         case 56:
            return this._open$56(var2, var3);
         case 57:
            return this.emit$57(var2, var3);
         case 58:
            return this.PlaceHolder$58(var2, var3);
         case 59:
            return this.__init__$59(var2, var3);
         case 60:
            return this.append$60(var2, var3);
         case 61:
            return this.setLoggerClass$61(var2, var3);
         case 62:
            return this.getLoggerClass$62(var2, var3);
         case 63:
            return this.Manager$63(var2, var3);
         case 64:
            return this.__init__$64(var2, var3);
         case 65:
            return this.getLogger$65(var2, var3);
         case 66:
            return this.setLoggerClass$66(var2, var3);
         case 67:
            return this._fixupParents$67(var2, var3);
         case 68:
            return this._fixupChildren$68(var2, var3);
         case 69:
            return this.Logger$69(var2, var3);
         case 70:
            return this.__init__$70(var2, var3);
         case 71:
            return this.setLevel$71(var2, var3);
         case 72:
            return this.debug$72(var2, var3);
         case 73:
            return this.info$73(var2, var3);
         case 74:
            return this.warning$74(var2, var3);
         case 75:
            return this.error$75(var2, var3);
         case 76:
            return this.exception$76(var2, var3);
         case 77:
            return this.critical$77(var2, var3);
         case 78:
            return this.log$78(var2, var3);
         case 79:
            return this.findCaller$79(var2, var3);
         case 80:
            return this.makeRecord$80(var2, var3);
         case 81:
            return this._log$81(var2, var3);
         case 82:
            return this.handle$82(var2, var3);
         case 83:
            return this.addHandler$83(var2, var3);
         case 84:
            return this.removeHandler$84(var2, var3);
         case 85:
            return this.callHandlers$85(var2, var3);
         case 86:
            return this.getEffectiveLevel$86(var2, var3);
         case 87:
            return this.isEnabledFor$87(var2, var3);
         case 88:
            return this.getChild$88(var2, var3);
         case 89:
            return this.RootLogger$89(var2, var3);
         case 90:
            return this.__init__$90(var2, var3);
         case 91:
            return this.LoggerAdapter$91(var2, var3);
         case 92:
            return this.__init__$92(var2, var3);
         case 93:
            return this.process$93(var2, var3);
         case 94:
            return this.debug$94(var2, var3);
         case 95:
            return this.info$95(var2, var3);
         case 96:
            return this.warning$96(var2, var3);
         case 97:
            return this.error$97(var2, var3);
         case 98:
            return this.exception$98(var2, var3);
         case 99:
            return this.critical$99(var2, var3);
         case 100:
            return this.log$100(var2, var3);
         case 101:
            return this.isEnabledFor$101(var2, var3);
         case 102:
            return this.basicConfig$102(var2, var3);
         case 103:
            return this.getLogger$103(var2, var3);
         case 104:
            return this.critical$104(var2, var3);
         case 105:
            return this.error$105(var2, var3);
         case 106:
            return this.exception$106(var2, var3);
         case 107:
            return this.warning$107(var2, var3);
         case 108:
            return this.info$108(var2, var3);
         case 109:
            return this.debug$109(var2, var3);
         case 110:
            return this.log$110(var2, var3);
         case 111:
            return this.disable$111(var2, var3);
         case 112:
            return this.shutdown$112(var2, var3);
         case 113:
            return this.NullHandler$113(var2, var3);
         case 114:
            return this.handle$114(var2, var3);
         case 115:
            return this.emit$115(var2, var3);
         case 116:
            return this.createLock$116(var2, var3);
         case 117:
            return this._showwarning$117(var2, var3);
         case 118:
            return this.captureWarnings$118(var2, var3);
         default:
            return null;
      }
   }
}
