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
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("_io.py")
public class _io$py extends PyFunctionTable implements PyRunnable {
   static _io$py self;
   static final PyCode f$0;
   static final PyCode BlockingIOError$1;
   static final PyCode __init__$2;
   static final PyCode _BufferedIOBase$3;
   static final PyCode read$4;
   static final PyCode read1$5;
   static final PyCode readinto$6;
   static final PyCode write$7;
   static final PyCode detach$8;
   static final PyCode _BufferedIOMixin$9;
   static final PyCode __init__$10;
   static final PyCode seek$11;
   static final PyCode tell$12;
   static final PyCode truncate$13;
   static final PyCode flush$14;
   static final PyCode close$15;
   static final PyCode detach$16;
   static final PyCode seekable$17;
   static final PyCode readable$18;
   static final PyCode writable$19;
   static final PyCode raw$20;
   static final PyCode closed$21;
   static final PyCode _checkInitialized$22;
   static final PyCode name$23;
   static final PyCode mode$24;
   static final PyCode __repr__$25;
   static final PyCode fileno$26;
   static final PyCode isatty$27;
   static final PyCode BytesIO$28;
   static final PyCode __init__$29;
   static final PyCode __getstate__$30;
   static final PyCode __setstate__$31;
   static final PyCode getvalue$32;
   static final PyCode read$33;
   static final PyCode read1$34;
   static final PyCode write$35;
   static final PyCode seek$36;
   static final PyCode tell$37;
   static final PyCode truncate$38;
   static final PyCode readable$39;
   static final PyCode writable$40;
   static final PyCode seekable$41;
   static final PyCode BufferedReader$42;
   static final PyCode __init__$43;
   static final PyCode _reset_read_buf$44;
   static final PyCode read$45;
   static final PyCode _read_unlocked$46;
   static final PyCode peek$47;
   static final PyCode _peek_unlocked$48;
   static final PyCode read1$49;
   static final PyCode tell$50;
   static final PyCode seek$51;
   static final PyCode BufferedWriter$52;
   static final PyCode __init__$53;
   static final PyCode write$54;
   static final PyCode truncate$55;
   static final PyCode flush$56;
   static final PyCode _flush_unlocked$57;
   static final PyCode tell$58;
   static final PyCode seek$59;
   static final PyCode BufferedRWPair$60;
   static final PyCode __init__$61;
   static final PyCode read$62;
   static final PyCode readinto$63;
   static final PyCode write$64;
   static final PyCode peek$65;
   static final PyCode read1$66;
   static final PyCode readable$67;
   static final PyCode writable$68;
   static final PyCode flush$69;
   static final PyCode close$70;
   static final PyCode isatty$71;
   static final PyCode closed$72;
   static final PyCode BufferedRandom$73;
   static final PyCode __init__$74;
   static final PyCode seek$75;
   static final PyCode tell$76;
   static final PyCode truncate$77;
   static final PyCode read$78;
   static final PyCode readinto$79;
   static final PyCode peek$80;
   static final PyCode read1$81;
   static final PyCode write$82;
   static final PyCode _TextIOBase$83;
   static final PyCode read$84;
   static final PyCode write$85;
   static final PyCode truncate$86;
   static final PyCode readline$87;
   static final PyCode detach$88;
   static final PyCode encoding$89;
   static final PyCode newlines$90;
   static final PyCode errors$91;
   static final PyCode IncrementalNewlineDecoder$92;
   static final PyCode __init__$93;
   static final PyCode decode$94;
   static final PyCode getstate$95;
   static final PyCode setstate$96;
   static final PyCode reset$97;
   static final PyCode newlines$98;
   static final PyCode _check_decoded_chars$99;
   static final PyCode _check_buffered_bytes$100;
   static final PyCode TextIOWrapper$101;
   static final PyCode __init__$102;
   static final PyCode __repr__$103;
   static final PyCode encoding$104;
   static final PyCode errors$105;
   static final PyCode line_buffering$106;
   static final PyCode buffer$107;
   static final PyCode seekable$108;
   static final PyCode readable$109;
   static final PyCode writable$110;
   static final PyCode flush$111;
   static final PyCode close$112;
   static final PyCode closed$113;
   static final PyCode _checkInitialized$114;
   static final PyCode name$115;
   static final PyCode fileno$116;
   static final PyCode isatty$117;
   static final PyCode write$118;
   static final PyCode _get_encoder$119;
   static final PyCode _get_decoder$120;
   static final PyCode _set_decoded_chars$121;
   static final PyCode _get_decoded_chars$122;
   static final PyCode _rewind_decoded_chars$123;
   static final PyCode _read_chunk$124;
   static final PyCode _pack_cookie$125;
   static final PyCode _unpack_cookie$126;
   static final PyCode tell$127;
   static final PyCode truncate$128;
   static final PyCode detach$129;
   static final PyCode seek$130;
   static final PyCode read$131;
   static final PyCode next$132;
   static final PyCode readline$133;
   static final PyCode newlines$134;
   static final PyCode StringIO$135;
   static final PyCode __init__$136;
   static final PyCode __getstate__$137;
   static final PyCode __setstate__$138;
   static final PyCode getvalue$139;
   static final PyCode __repr__$140;
   static final PyCode errors$141;
   static final PyCode encoding$142;
   static final PyCode detach$143;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("This is based on  _pyio.py from CPython 2.7 which is Python implementation of\nthe io module. The upgrade from a 2.6-ish version accounts for the large\nnumber of changes made all at once.\n\nIt is here to stand in for classes that should be provided by the Java\nimplementation of the _io module.  In CPython 2.7, when client code\nimports io, that module imports a set of classes from _io and\nre-exports them as its own. In Jython, io.py imports those things from\n_io, which in turn imports from _jyio those so far implemented in\nJava. Consequently _io implements the rest here using nearly the same\ncode as _pyio. (Previous to Jython 2.7.1, the import was reversed:\nthis specific Python-based module was named _jyio, and it imported\nfrom org.python.modules.io._io; although reasonable enough for Jython\nitself, we found that extant Python code expected that the _io module\nwas the one defining various classes and constants. See\nhttp://bugs.jython.org/issue2368 for more background. If we ever get\naround to rewriting this module completely to Java, which is doubtful,\nthis problem will go away.)\n\nSome classes have gained an underscore to match their _io module names:\n_IOBase, _RawIOBase, _BufferedIOBase, _TextIOBase.\n\nAs Jython implements more and more of _io in Java, the Python implementations here\nwill progressively be replaced with imports from _io. Eventually we should implement\nall this in Java, remove this module and revert io.py to its CPython original.\n\n"));
      var1.setline(27);
      PyString.fromInterned("This is based on  _pyio.py from CPython 2.7 which is Python implementation of\nthe io module. The upgrade from a 2.6-ish version accounts for the large\nnumber of changes made all at once.\n\nIt is here to stand in for classes that should be provided by the Java\nimplementation of the _io module.  In CPython 2.7, when client code\nimports io, that module imports a set of classes from _io and\nre-exports them as its own. In Jython, io.py imports those things from\n_io, which in turn imports from _jyio those so far implemented in\nJava. Consequently _io implements the rest here using nearly the same\ncode as _pyio. (Previous to Jython 2.7.1, the import was reversed:\nthis specific Python-based module was named _jyio, and it imported\nfrom org.python.modules.io._io; although reasonable enough for Jython\nitself, we found that extant Python code expected that the _io module\nwas the one defining various classes and constants. See\nhttp://bugs.jython.org/issue2368 for more background. If we ever get\naround to rewriting this module completely to Java, which is doubtful,\nthis problem will go away.)\n\nSome classes have gained an underscore to match their _io module names:\n_IOBase, _RawIOBase, _BufferedIOBase, _TextIOBase.\n\nAs Jython implements more and more of _io in Java, the Python implementations here\nwill progressively be replaced with imports from _io. Eventually we should implement\nall this in Java, remove this module and revert io.py to its CPython original.\n\n");
      var1.setline(29);
      String[] var3 = new String[]{"print_function", "unicode_literals"};
      PyObject[] var7 = imp.importFrom("__future__", var3, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("print_function", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("unicode_literals", var4);
      var4 = null;
      var1.setline(31);
      PyObject var8 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var8);
      var3 = null;
      var1.setline(32);
      var8 = imp.importOne("abc", var1, -1);
      var1.setlocal("abc", var8);
      var3 = null;
      var1.setline(33);
      var8 = imp.importOne("codecs", var1, -1);
      var1.setlocal("codecs", var8);
      var3 = null;
      var1.setline(34);
      var8 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var8);
      var3 = null;
      var1.setline(35);
      var8 = imp.importOne("errno", var1, -1);
      var1.setlocal("errno", var8);
      var3 = null;
      var1.setline(36);
      var8 = imp.importOne("array", var1, -1);
      var1.setlocal("array", var8);
      var3 = null;

      try {
         var1.setline(39);
         var3 = new String[]{"allocate_lock"};
         var7 = imp.importFrom("thread", var3, var1, -1);
         var4 = var7[0];
         var1.setlocal("Lock", var4);
         var4 = null;
      } catch (Throwable var6) {
         PyException var11 = Py.setException(var6, var1);
         if (!var11.match(var1.getname("ImportError"))) {
            throw var11;
         }

         var1.setline(41);
         String[] var9 = new String[]{"allocate_lock"};
         PyObject[] var10 = imp.importFrom("dummy_thread", var9, var1, -1);
         PyObject var5 = var10[0];
         var1.setlocal("Lock", var5);
         var5 = null;
      }

      var1.setline(45);
      var3 = new String[]{"EINTR"};
      var7 = imp.importFrom("errno", var3, var1, -1);
      var4 = var7[0];
      var1.setlocal("EINTR", var4);
      var4 = null;
      var1.setline(47);
      var8 = var1.getname("type");
      var1.setlocal("__metaclass__", var8);
      var3 = null;
      var1.setline(50);
      var3 = new String[]{"DEFAULT_BUFFER_SIZE"};
      var7 = imp.importFrom("_jyio", var3, var1, -1);
      var4 = var7[0];
      var1.setlocal("DEFAULT_BUFFER_SIZE", var4);
      var4 = null;
      var1.setline(56);
      var7 = new PyObject[]{var1.getname("IOError")};
      var4 = Py.makeClass("BlockingIOError", var7, BlockingIOError$1);
      var1.setlocal("BlockingIOError", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(67);
      var3 = new String[]{"open", "UnsupportedOperation", "_IOBase", "_RawIOBase", "FileIO"};
      var7 = imp.importFrom("_jyio", var3, var1, -1);
      var4 = var7[0];
      var1.setlocal("open", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("UnsupportedOperation", var4);
      var4 = null;
      var4 = var7[2];
      var1.setlocal("_IOBase", var4);
      var4 = null;
      var4 = var7[3];
      var1.setlocal("_RawIOBase", var4);
      var4 = null;
      var4 = var7[4];
      var1.setlocal("FileIO", var4);
      var4 = null;
      var1.setline(70);
      var7 = new PyObject[]{var1.getname("_IOBase")};
      var4 = Py.makeClass("_BufferedIOBase", var7, _BufferedIOBase$3);
      var1.setlocal("_BufferedIOBase", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(155);
      var7 = new PyObject[]{var1.getname("_BufferedIOBase")};
      var4 = Py.makeClass("_BufferedIOMixin", var7, _BufferedIOMixin$9);
      var1.setlocal("_BufferedIOMixin", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(275);
      var7 = new PyObject[]{var1.getname("_BufferedIOBase")};
      var4 = Py.makeClass("BytesIO", var7, BytesIO$28);
      var1.setlocal("BytesIO", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(428);
      var7 = new PyObject[]{var1.getname("_BufferedIOMixin")};
      var4 = Py.makeClass("BufferedReader", var7, BufferedReader$42);
      var1.setlocal("BufferedReader", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(583);
      var7 = new PyObject[]{var1.getname("_BufferedIOMixin")};
      var4 = Py.makeClass("BufferedWriter", var7, BufferedWriter$52);
      var1.setlocal("BufferedWriter", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(686);
      var7 = new PyObject[]{var1.getname("_BufferedIOBase")};
      var4 = Py.makeClass("BufferedRWPair", var7, BufferedRWPair$60);
      var1.setlocal("BufferedRWPair", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(758);
      var7 = new PyObject[]{var1.getname("BufferedWriter"), var1.getname("BufferedReader")};
      var4 = Py.makeClass("BufferedRandom", var7, BufferedRandom$73);
      var1.setlocal("BufferedRandom", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(831);
      var7 = new PyObject[]{var1.getname("_IOBase")};
      var4 = Py.makeClass("_TextIOBase", var7, _TextIOBase$83);
      var1.setlocal("_TextIOBase", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(895);
      var7 = new PyObject[]{var1.getname("codecs").__getattr__("IncrementalDecoder")};
      var4 = Py.makeClass("IncrementalNewlineDecoder", var7, IncrementalNewlineDecoder$92);
      var1.setlocal("IncrementalNewlineDecoder", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(980);
      var7 = Py.EmptyObjects;
      PyFunction var12 = new PyFunction(var1.f_globals, var7, _check_decoded_chars$99, PyUnicode.fromInterned("Check decoder output is unicode"));
      var1.setlocal("_check_decoded_chars", var12);
      var3 = null;
      var1.setline(986);
      var7 = new PyObject[]{PyUnicode.fromInterned("read")};
      var12 = new PyFunction(var1.f_globals, var7, _check_buffered_bytes$100, PyUnicode.fromInterned("Check buffer has returned bytes"));
      var1.setlocal("_check_buffered_bytes", var12);
      var3 = null;
      var1.setline(994);
      var7 = new PyObject[]{var1.getname("_TextIOBase")};
      var4 = Py.makeClass("TextIOWrapper", var7, TextIOWrapper$101);
      var1.setlocal("TextIOWrapper", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1553);
      var7 = new PyObject[]{var1.getname("TextIOWrapper")};
      var4 = Py.makeClass("StringIO", var7, StringIO$135);
      var1.setlocal("StringIO", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BlockingIOError$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyUnicode.fromInterned("Exception raised when I/O would block on a non-blocking I/O stream."));
      var1.setline(58);
      PyUnicode.fromInterned("Exception raised when I/O would block on a non-blocking I/O stream.");
      var1.setline(60);
      PyObject[] var3 = new PyObject[]{Py.newInteger(0)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(61);
      var1.getglobal("super").__call__(var2, var1.getglobal("IOError"), var1.getlocal(0)).__getattr__("__init__").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(62);
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__not__().__nonzero__()) {
         var1.setline(63);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("characters_written must be a integer")));
      } else {
         var1.setline(64);
         PyObject var3 = var1.getlocal(3);
         var1.getlocal(0).__setattr__("characters_written", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _BufferedIOBase$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyUnicode.fromInterned("Base class for buffered IO objects.\n\n    The main difference with _RawIOBase is that the read() method\n    supports omitting the size argument, and does not have a default\n    implementation that defers to readinto().\n\n    In addition, read(), readinto() and write() may raise\n    BlockingIOError if the underlying raw stream is in non-blocking\n    mode and not ready; unlike their raw counterparts, they will never\n    return None.\n\n    A typical implementation should not inherit from a _RawIOBase\n    implementation, but wrap one.\n    "));
      var1.setline(85);
      PyUnicode.fromInterned("Base class for buffered IO objects.\n\n    The main difference with _RawIOBase is that the read() method\n    supports omitting the size argument, and does not have a default\n    implementation that defers to readinto().\n\n    In addition, read(), readinto() and write() may raise\n    BlockingIOError if the underlying raw stream is in non-blocking\n    mode and not ready; unlike their raw counterparts, they will never\n    return None.\n\n    A typical implementation should not inherit from a _RawIOBase\n    implementation, but wrap one.\n    ");
      var1.setline(87);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, read$4, PyUnicode.fromInterned("Read and return up to n bytes.\n\n        If the argument is omitted, None, or negative, reads and\n        returns all data until EOF.\n\n        If the argument is positive, and the underlying raw stream is\n        not 'interactive', multiple raw reads may be issued to satisfy\n        the byte count (unless EOF is reached first).  But for\n        interactive raw streams (XXX and for pipes?), at most one raw\n        read will be issued, and a short result does not imply that\n        EOF is imminent.\n\n        Returns an empty bytes array on EOF.\n\n        Raises BlockingIOError if the underlying raw stream has no\n        data at the moment.\n        "));
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(107);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, read1$5, PyUnicode.fromInterned("Read up to n bytes with at most one read() system call."));
      var1.setlocal("read1", var4);
      var3 = null;
      var1.setline(111);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, readinto$6, PyUnicode.fromInterned("Read up to len(b) bytes into b.\n\n        Like read(), this may issue multiple reads to the underlying raw\n        stream, unless the latter is 'interactive'.\n\n        Returns the number of bytes read (0 for EOF).\n\n        Raises BlockingIOError if the underlying raw stream has no\n        data at the moment.\n        "));
      var1.setlocal("readinto", var4);
      var3 = null;
      var1.setline(134);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, write$7, PyUnicode.fromInterned("Write the given buffer to the IO stream.\n\n        Return the number of bytes written, which is never less than\n        len(b).\n\n        Raises BlockingIOError if the buffer is full and the\n        underlying raw stream cannot accept more data at the moment.\n        "));
      var1.setlocal("write", var4);
      var3 = null;
      var1.setline(145);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, detach$8, PyUnicode.fromInterned("\n        Separate the underlying raw stream from the buffer and return it.\n\n        After the raw stream has been detached, the buffer is in an unusable\n        state.\n        "));
      var1.setlocal("detach", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject read$4(PyFrame var1, ThreadState var2) {
      var1.setline(104);
      PyUnicode.fromInterned("Read and return up to n bytes.\n\n        If the argument is omitted, None, or negative, reads and\n        returns all data until EOF.\n\n        If the argument is positive, and the underlying raw stream is\n        not 'interactive', multiple raw reads may be issued to satisfy\n        the byte count (unless EOF is reached first).  But for\n        interactive raw streams (XXX and for pipes?), at most one raw\n        read will be issued, and a short result does not imply that\n        EOF is imminent.\n\n        Returns an empty bytes array on EOF.\n\n        Raises BlockingIOError if the underlying raw stream has no\n        data at the moment.\n        ");
      var1.setline(105);
      var1.getlocal(0).__getattr__("_unsupported").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("read"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read1$5(PyFrame var1, ThreadState var2) {
      var1.setline(108);
      PyUnicode.fromInterned("Read up to n bytes with at most one read() system call.");
      var1.setline(109);
      var1.getlocal(0).__getattr__("_unsupported").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("read1"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject readinto$6(PyFrame var1, ThreadState var2) {
      var1.setline(121);
      PyUnicode.fromInterned("Read up to len(b) bytes into b.\n\n        Like read(), this may issue multiple reads to the underlying raw\n        stream, unless the latter is 'interactive'.\n\n        Returns the number of bytes read (0 for EOF).\n\n        Raises BlockingIOError if the underlying raw stream has no\n        data at the moment.\n        ");
      var1.setline(123);
      PyObject var3 = var1.getlocal(0).__getattr__("read").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(1)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(124);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;

      try {
         var1.setline(126);
         var3 = var1.getlocal(2);
         var1.getlocal(1).__setslice__((PyObject)null, var1.getlocal(3), (PyObject)null, var3);
         var3 = null;
      } catch (Throwable var5) {
         PyException var6 = Py.setException(var5, var1);
         if (!var6.match(var1.getglobal("TypeError"))) {
            throw var6;
         }

         PyObject var4 = var6.value;
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(128);
         var4 = imp.importOne("array", var1, -1);
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(129);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getlocal(5).__getattr__("array")).__not__().__nonzero__()) {
            var1.setline(130);
            throw Py.makeException(var1.getlocal(4));
         }

         var1.setline(131);
         var4 = var1.getlocal(5).__getattr__("array").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("b"), (PyObject)var1.getlocal(2));
         var1.getlocal(1).__setslice__((PyObject)null, var1.getlocal(3), (PyObject)null, var4);
         var4 = null;
      }

      var1.setline(132);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject write$7(PyFrame var1, ThreadState var2) {
      var1.setline(142);
      PyUnicode.fromInterned("Write the given buffer to the IO stream.\n\n        Return the number of bytes written, which is never less than\n        len(b).\n\n        Raises BlockingIOError if the buffer is full and the\n        underlying raw stream cannot accept more data at the moment.\n        ");
      var1.setline(143);
      var1.getlocal(0).__getattr__("_unsupported").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("write"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject detach$8(PyFrame var1, ThreadState var2) {
      var1.setline(151);
      PyUnicode.fromInterned("\n        Separate the underlying raw stream from the buffer and return it.\n\n        After the raw stream has been detached, the buffer is in an unusable\n        state.\n        ");
      var1.setline(152);
      var1.getlocal(0).__getattr__("_unsupported").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("detach"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _BufferedIOMixin$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyUnicode.fromInterned("A mixin implementation of _BufferedIOBase with an underlying raw stream.\n\n    This passes most requests on to the underlying raw stream.  It\n    does *not* provide implementations of read(), readinto() or\n    write().\n    "));
      var1.setline(162);
      PyUnicode.fromInterned("A mixin implementation of _BufferedIOBase with an underlying raw stream.\n\n    This passes most requests on to the underlying raw stream.  It\n    does *not* provide implementations of read(), readinto() or\n    write().\n    ");
      var1.setline(164);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$10, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(170);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, seek$11, (PyObject)null);
      var1.setlocal("seek", var4);
      var3 = null;
      var1.setline(176);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tell$12, (PyObject)null);
      var1.setlocal("tell", var4);
      var3 = null;
      var1.setline(182);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, truncate$13, (PyObject)null);
      var1.setlocal("truncate", var4);
      var3 = null;
      var1.setline(196);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, flush$14, (PyObject)null);
      var1.setlocal("flush", var4);
      var3 = null;
      var1.setline(201);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$15, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(210);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, detach$16, (PyObject)null);
      var1.setlocal("detach", var4);
      var3 = null;
      var1.setline(220);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, seekable$17, (PyObject)null);
      var1.setlocal("seekable", var4);
      var3 = null;
      var1.setline(224);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, readable$18, (PyObject)null);
      var1.setlocal("readable", var4);
      var3 = null;
      var1.setline(228);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, writable$19, (PyObject)null);
      var1.setlocal("writable", var4);
      var3 = null;
      var1.setline(232);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, raw$20, (PyObject)null);
      PyObject var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("raw", var5);
      var3 = null;
      var1.setline(236);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, closed$21, (PyObject)null);
      var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("closed", var5);
      var3 = null;
      var1.setline(242);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _checkInitialized$22, (PyObject)null);
      var1.setlocal("_checkInitialized", var4);
      var3 = null;
      var1.setline(249);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, name$23, (PyObject)null);
      var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("name", var5);
      var3 = null;
      var1.setline(253);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, mode$24, (PyObject)null);
      var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("mode", var5);
      var3 = null;
      var1.setline(257);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$25, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(268);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, fileno$26, (PyObject)null);
      var1.setlocal("fileno", var4);
      var3 = null;
      var1.setline(271);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isatty$27, (PyObject)null);
      var1.setlocal("isatty", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$10(PyFrame var1, ThreadState var2) {
      var1.setline(165);
      PyObject var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("_ok", var3);
      var3 = null;
      var1.setline(166);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_raw", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject seek$11(PyFrame var1, ThreadState var2) {
      var1.setline(171);
      PyObject var3 = var1.getlocal(0).__getattr__("raw").__getattr__("seek").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(172);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(173);
         throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("seek() returned an invalid position")));
      } else {
         var1.setline(174);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject tell$12(PyFrame var1, ThreadState var2) {
      var1.setline(177);
      PyObject var3 = var1.getlocal(0).__getattr__("raw").__getattr__("tell").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(178);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(179);
         throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("tell() returned an invalid position")));
      } else {
         var1.setline(180);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject truncate$13(PyFrame var1, ThreadState var2) {
      var1.setline(186);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(188);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(189);
         var3 = var1.getlocal(0).__getattr__("tell").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(192);
      var3 = var1.getlocal(0).__getattr__("raw").__getattr__("truncate").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject flush$14(PyFrame var1, ThreadState var2) {
      var1.setline(197);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(198);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("flush of closed file")));
      } else {
         var1.setline(199);
         var1.getlocal(0).__getattr__("raw").__getattr__("flush").__call__(var2);
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject close$15(PyFrame var1, ThreadState var2) {
      var1.setline(202);
      PyObject var3 = var1.getlocal(0).__getattr__("raw");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("closed").__not__();
      }

      if (var10000.__nonzero__()) {
         var3 = null;

         try {
            var1.setline(206);
            var1.getglobal("super").__call__(var2, var1.getglobal("_BufferedIOBase"), var1.getlocal(0)).__getattr__("close").__call__(var2);
         } catch (Throwable var4) {
            Py.addTraceback(var4, var1);
            var1.setline(208);
            var1.getlocal(0).__getattr__("raw").__getattr__("close").__call__(var2);
            throw (Throwable)var4;
         }

         var1.setline(208);
         var1.getlocal(0).__getattr__("raw").__getattr__("close").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject detach$16(PyFrame var1, ThreadState var2) {
      var1.setline(211);
      PyObject var3 = var1.getlocal(0).__getattr__("raw");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(212);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("raw stream already detached")));
      } else {
         var1.setline(213);
         var1.getlocal(0).__getattr__("flush").__call__(var2);
         var1.setline(214);
         var3 = var1.getlocal(0).__getattr__("_raw");
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(215);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("_raw", var3);
         var3 = null;
         var1.setline(216);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject seekable$17(PyFrame var1, ThreadState var2) {
      var1.setline(221);
      var1.getlocal(0).__getattr__("_checkInitialized").__call__(var2);
      var1.setline(222);
      PyObject var3 = var1.getlocal(0).__getattr__("raw").__getattr__("seekable").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject readable$18(PyFrame var1, ThreadState var2) {
      var1.setline(225);
      var1.getlocal(0).__getattr__("_checkInitialized").__call__(var2);
      var1.setline(226);
      PyObject var3 = var1.getlocal(0).__getattr__("raw").__getattr__("readable").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject writable$19(PyFrame var1, ThreadState var2) {
      var1.setline(229);
      var1.getlocal(0).__getattr__("_checkInitialized").__call__(var2);
      var1.setline(230);
      PyObject var3 = var1.getlocal(0).__getattr__("raw").__getattr__("writable").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject raw$20(PyFrame var1, ThreadState var2) {
      var1.setline(234);
      PyObject var3 = var1.getlocal(0).__getattr__("_raw");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject closed$21(PyFrame var1, ThreadState var2) {
      var1.setline(238);
      PyObject var3 = var1.getlocal(0).__getattr__("raw").__getattr__("closed");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _checkInitialized$22(PyFrame var1, ThreadState var2) {
      var1.setline(243);
      if (var1.getlocal(0).__getattr__("_ok").__not__().__nonzero__()) {
         var1.setline(244);
         PyObject var3 = var1.getlocal(0).__getattr__("raw");
         PyObject var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(245);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("raw stream has been detached")));
         } else {
            var1.setline(247);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("I/O operation on uninitialized object")));
         }
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject name$23(PyFrame var1, ThreadState var2) {
      var1.setline(251);
      PyObject var3 = var1.getlocal(0).__getattr__("raw").__getattr__("name");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject mode$24(PyFrame var1, ThreadState var2) {
      var1.setline(255);
      PyObject var3 = var1.getlocal(0).__getattr__("raw").__getattr__("mode");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$25(PyFrame var1, ThreadState var2) {
      var1.setline(258);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__getattr__("__name__");
      var1.setlocal(1, var3);
      var3 = null;

      PyObject var4;
      try {
         var1.setline(260);
         var3 = var1.getlocal(0).__getattr__("name");
         var1.setlocal(2, var3);
         var3 = null;
      } catch (Throwable var5) {
         PyException var6 = Py.setException(var5, var1);
         if (var6.match(var1.getglobal("AttributeError"))) {
            var1.setline(262);
            var4 = PyUnicode.fromInterned("<_io.{0}>").__getattr__("format").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var4;
         }

         throw var6;
      }

      var1.setline(264);
      var4 = PyUnicode.fromInterned("<_io.{0} name={1!r}>").__getattr__("format").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject fileno$26(PyFrame var1, ThreadState var2) {
      var1.setline(269);
      PyObject var3 = var1.getlocal(0).__getattr__("raw").__getattr__("fileno").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isatty$27(PyFrame var1, ThreadState var2) {
      var1.setline(272);
      PyObject var3 = var1.getlocal(0).__getattr__("raw").__getattr__("isatty").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject BytesIO$28(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyUnicode.fromInterned("Buffered I/O implementation using an in-memory bytes buffer."));
      var1.setline(277);
      PyUnicode.fromInterned("Buffered I/O implementation using an in-memory bytes buffer.");
      var1.setline(279);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$29, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(287);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getstate__$30, (PyObject)null);
      var1.setlocal("__getstate__", var4);
      var3 = null;
      var1.setline(294);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __setstate__$31, (PyObject)null);
      var1.setlocal("__setstate__", var4);
      var3 = null;
      var1.setline(329);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getvalue$32, PyUnicode.fromInterned("Return the bytes value (contents) of the buffer\n        "));
      var1.setlocal("getvalue", var4);
      var3 = null;
      var1.setline(336);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, read$33, (PyObject)null);
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(353);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read1$34, PyUnicode.fromInterned("This is the same as read.\n        "));
      var1.setlocal("read1", var4);
      var3 = null;
      var1.setline(358);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, write$35, (PyObject)null);
      var1.setlocal("write", var4);
      var3 = null;
      var1.setline(376);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, seek$36, (PyObject)null);
      var1.setlocal("seek", var4);
      var3 = null;
      var1.setline(395);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tell$37, (PyObject)null);
      var1.setlocal("tell", var4);
      var3 = null;
      var1.setline(400);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, truncate$38, (PyObject)null);
      var1.setlocal("truncate", var4);
      var3 = null;
      var1.setline(415);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, readable$39, (PyObject)null);
      var1.setlocal("readable", var4);
      var3 = null;
      var1.setline(419);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, writable$40, (PyObject)null);
      var1.setlocal("writable", var4);
      var3 = null;
      var1.setline(423);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, seekable$41, (PyObject)null);
      var1.setlocal("seekable", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$29(PyFrame var1, ThreadState var2) {
      var1.setline(280);
      PyObject var3 = var1.getglobal("bytearray").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(281);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(282);
         var1.getlocal(2).__getattr__("extend").__call__(var2, var1.getlocal(1));
      }

      var1.setline(283);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_buffer", var3);
      var3 = null;
      var1.setline(284);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_pos", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getstate__$30(PyFrame var1, ThreadState var2) {
      var1.setline(288);
      PyObject var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyUnicode.fromInterned("__dict__"), (PyObject)var1.getglobal("None"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(289);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(290);
         var3 = var1.getlocal(1).__getattr__("copy").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(291);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("getvalue").__call__(var2), var1.getlocal(0).__getattr__("_pos"), var1.getlocal(1)});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __setstate__$31(PyFrame var1, ThreadState var2) {
      var1.setline(296);
      PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("tuple")).__not__();
      PyObject var3;
      if (!var10000.__nonzero__()) {
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var10000 = var3._lt(Py.newInteger(3));
         var3 = null;
      }

      PyUnicode var5;
      if (var10000.__nonzero__()) {
         var1.setline(297);
         var5 = PyUnicode.fromInterned("%s.__setstate__ argument should be 3-tuple got %s");
         var1.setlocal(2, var5);
         var3 = null;
         var1.setline(298);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, var1.getlocal(2)._mod(new PyTuple(new PyObject[]{var1.getglobal("type").__call__(var2, var1.getlocal(0)), var1.getglobal("type").__call__(var2, var1.getlocal(1))}))));
      } else {
         var1.setline(302);
         var3 = var1.getglobal("bytearray").__call__(var2);
         var1.getlocal(0).__setattr__("_buffer", var3);
         var3 = null;
         var1.setline(303);
         PyInteger var4 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"_pos", var4);
         var3 = null;
         var1.setline(307);
         var1.getlocal(0).__getattr__("write").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)));
         var1.setline(312);
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(313);
         if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__not__().__nonzero__()) {
            var1.setline(314);
            var5 = PyUnicode.fromInterned("second item of state must be an integer, got %s");
            var1.setlocal(2, var5);
            var3 = null;
            var1.setline(315);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2, var1.getlocal(2)._mod(var1.getglobal("type").__call__(var2, var1.getlocal(3)))));
         } else {
            var1.setline(316);
            var3 = var1.getlocal(3);
            var10000 = var3._lt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(317);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("position value cannot be negative")));
            } else {
               var1.setline(318);
               var3 = var1.getlocal(3);
               var1.getlocal(0).__setattr__("_pos", var3);
               var3 = null;
               var1.setline(321);
               var3 = var1.getlocal(1).__getitem__(Py.newInteger(2));
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(322);
               var3 = var1.getlocal(4);
               var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
               if (var10000.__not__().__nonzero__()) {
                  var1.setline(323);
                  if (!var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("dict")).__nonzero__()) {
                     var1.setline(326);
                     var5 = PyUnicode.fromInterned("third item of state should be a dict, got %s");
                     var1.setlocal(2, var5);
                     var3 = null;
                     var1.setline(327);
                     throw Py.makeException(var1.getglobal("TypeError").__call__(var2, var1.getlocal(2)._mod(var1.getglobal("type").__call__(var2, var1.getlocal(4)))));
                  }

                  var1.setline(324);
                  var3 = var1.getlocal(4);
                  var1.getlocal(0).__setattr__("__dict__", var3);
                  var3 = null;
               }

               var1.f_lasti = -1;
               return Py.None;
            }
         }
      }
   }

   public PyObject getvalue$32(PyFrame var1, ThreadState var2) {
      var1.setline(331);
      PyUnicode.fromInterned("Return the bytes value (contents) of the buffer\n        ");
      var1.setline(332);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(333);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("getvalue on closed file")));
      } else {
         var1.setline(334);
         PyObject var3 = var1.getglobal("bytes").__call__(var2, var1.getlocal(0).__getattr__("_buffer"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject read$33(PyFrame var1, ThreadState var2) {
      var1.setline(337);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(338);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("read from closed file")));
      } else {
         var1.setline(339);
         PyObject var3 = var1.getlocal(1);
         PyObject var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(340);
            PyInteger var5 = Py.newInteger(-1);
            var1.setlocal(1, var5);
            var3 = null;
         }

         var1.setline(341);
         if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__not__().__nonzero__()) {
            var1.setline(342);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyUnicode.fromInterned("integer argument expected, got {0!r}").__getattr__("format").__call__(var2, var1.getglobal("type").__call__(var2, var1.getlocal(1)))));
         } else {
            var1.setline(344);
            var3 = var1.getlocal(1);
            var10000 = var3._lt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(345);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_buffer"));
               var1.setlocal(1, var3);
               var3 = null;
            }

            var1.setline(346);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_buffer"));
            var10000 = var3._le(var1.getlocal(0).__getattr__("_pos"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(347);
               PyString var6 = PyString.fromInterned("");
               var1.f_lasti = -1;
               return var6;
            } else {
               var1.setline(348);
               PyObject var4 = var1.getglobal("min").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_buffer")), var1.getlocal(0).__getattr__("_pos")._add(var1.getlocal(1)));
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(349);
               var4 = var1.getlocal(0).__getattr__("_buffer").__getslice__(var1.getlocal(0).__getattr__("_pos"), var1.getlocal(2), (PyObject)null);
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(350);
               var4 = var1.getlocal(2);
               var1.getlocal(0).__setattr__("_pos", var4);
               var4 = null;
               var1.setline(351);
               var3 = var1.getglobal("bytes").__call__(var2, var1.getlocal(3));
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject read1$34(PyFrame var1, ThreadState var2) {
      var1.setline(355);
      PyUnicode.fromInterned("This is the same as read.\n        ");
      var1.setline(356);
      PyObject var3 = var1.getlocal(0).__getattr__("read").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject write$35(PyFrame var1, ThreadState var2) {
      var1.setline(359);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(360);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("write to closed file")));
      } else {
         var1.setline(361);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("unicode")).__nonzero__()) {
            var1.setline(362);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("can't write unicode to binary stream")));
         } else {
            var1.setline(363);
            PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(364);
            var3 = var1.getlocal(2);
            PyObject var10000 = var3._eq(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(365);
               PyInteger var7 = Py.newInteger(0);
               var1.f_lasti = -1;
               return var7;
            } else {
               var1.setline(366);
               PyObject var4 = var1.getlocal(0).__getattr__("_pos");
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(367);
               var4 = var1.getlocal(3);
               var10000 = var4._gt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_buffer")));
               var4 = null;
               PyObject var5;
               PyObject var6;
               String var8;
               if (var10000.__nonzero__()) {
                  var1.setline(370);
                  var4 = PyString.fromInterned("\u0000")._mul(var1.getlocal(3)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_buffer"))));
                  var1.setlocal(4, var4);
                  var4 = null;
                  var1.setline(371);
                  var10000 = var1.getlocal(0);
                  var8 = "_buffer";
                  var5 = var10000;
                  var6 = var5.__getattr__(var8);
                  var6 = var6._iadd(var1.getlocal(4));
                  var5.__setattr__(var8, var6);
               }

               var1.setline(372);
               var4 = var1.getlocal(1);
               var1.getlocal(0).__getattr__("_buffer").__setslice__(var1.getlocal(3), var1.getlocal(3)._add(var1.getlocal(2)), (PyObject)null, var4);
               var4 = null;
               var1.setline(373);
               var10000 = var1.getlocal(0);
               var8 = "_pos";
               var5 = var10000;
               var6 = var5.__getattr__(var8);
               var6 = var6._iadd(var1.getlocal(2));
               var5.__setattr__(var8, var6);
               var1.setline(374);
               var3 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject seek$36(PyFrame var1, ThreadState var2) {
      var1.setline(377);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(378);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("seek on closed file")));
      } else {
         PyException var3;
         try {
            var1.setline(380);
            var1.getlocal(1).__getattr__("__index__");
         } catch (Throwable var4) {
            var3 = Py.setException(var4, var1);
            if (var3.match(var1.getglobal("AttributeError"))) {
               var1.setline(382);
               throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("an integer is required")));
            }

            throw var3;
         }

         var1.setline(383);
         PyObject var5 = var1.getlocal(2);
         PyObject var10000 = var5._eq(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(384);
            var5 = var1.getlocal(1);
            var10000 = var5._lt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(385);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyUnicode.fromInterned("negative seek position %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(1)}))));
            }

            var1.setline(386);
            var5 = var1.getlocal(1);
            var1.getlocal(0).__setattr__("_pos", var5);
            var3 = null;
         } else {
            var1.setline(387);
            var5 = var1.getlocal(2);
            var10000 = var5._eq(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(388);
               var5 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(0).__getattr__("_pos")._add(var1.getlocal(1)));
               var1.getlocal(0).__setattr__("_pos", var5);
               var3 = null;
            } else {
               var1.setline(389);
               var5 = var1.getlocal(2);
               var10000 = var5._eq(Py.newInteger(2));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(392);
                  throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("invalid whence value")));
               }

               var1.setline(390);
               var5 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_buffer"))._add(var1.getlocal(1)));
               var1.getlocal(0).__setattr__("_pos", var5);
               var3 = null;
            }
         }

         var1.setline(393);
         var5 = var1.getlocal(0).__getattr__("_pos");
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject tell$37(PyFrame var1, ThreadState var2) {
      var1.setline(396);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(397);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("tell on closed file")));
      } else {
         var1.setline(398);
         PyObject var3 = var1.getlocal(0).__getattr__("_pos");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject truncate$38(PyFrame var1, ThreadState var2) {
      var1.setline(401);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(402);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("truncate on closed file")));
      } else {
         var1.setline(403);
         PyObject var3 = var1.getlocal(1);
         PyObject var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(404);
            var3 = var1.getlocal(0).__getattr__("_pos");
            var1.setlocal(1, var3);
            var3 = null;
         } else {
            try {
               var1.setline(407);
               var1.getlocal(1).__getattr__("__index__");
            } catch (Throwable var4) {
               PyException var5 = Py.setException(var4, var1);
               if (var5.match(var1.getglobal("AttributeError"))) {
                  var1.setline(409);
                  throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("an integer is required")));
               }

               throw var5;
            }

            var1.setline(410);
            var3 = var1.getlocal(1);
            var10000 = var3._lt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(411);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyUnicode.fromInterned("negative truncate position %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(1)}))));
            }
         }

         var1.setline(412);
         var1.getlocal(0).__getattr__("_buffer").__delslice__(var1.getlocal(1), (PyObject)null, (PyObject)null);
         var1.setline(413);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject readable$39(PyFrame var1, ThreadState var2) {
      var1.setline(416);
      var1.getlocal(0).__getattr__("_checkClosed").__call__(var2);
      var1.setline(417);
      PyObject var3 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject writable$40(PyFrame var1, ThreadState var2) {
      var1.setline(420);
      var1.getlocal(0).__getattr__("_checkClosed").__call__(var2);
      var1.setline(421);
      PyObject var3 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject seekable$41(PyFrame var1, ThreadState var2) {
      var1.setline(424);
      var1.getlocal(0).__getattr__("_checkClosed").__call__(var2);
      var1.setline(425);
      PyObject var3 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject BufferedReader$42(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyUnicode.fromInterned("BufferedReader(raw[, buffer_size])\n\n    A buffer for a readable, sequential BaseRawIO object.\n\n    The constructor creates a BufferedReader for the given readable raw\n    stream and buffer_size. If buffer_size is omitted, DEFAULT_BUFFER_SIZE\n    is used.\n    "));
      var1.setline(437);
      PyUnicode.fromInterned("BufferedReader(raw[, buffer_size])\n\n    A buffer for a readable, sequential BaseRawIO object.\n\n    The constructor creates a BufferedReader for the given readable raw\n    stream and buffer_size. If buffer_size is omitted, DEFAULT_BUFFER_SIZE\n    is used.\n    ");
      var1.setline(439);
      PyObject[] var3 = new PyObject[]{var1.getname("DEFAULT_BUFFER_SIZE")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$43, PyUnicode.fromInterned("Create a new buffered reader using the given readable raw IO object.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(453);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _reset_read_buf$44, (PyObject)null);
      var1.setlocal("_reset_read_buf", var4);
      var3 = null;
      var1.setline(457);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, read$45, PyUnicode.fromInterned("Read n bytes.\n\n        Returns exactly n bytes of data unless the underlying raw IO\n        stream reaches EOF or if the call would block in non-blocking\n        mode. If n is negative, read until EOF or until read() would\n        block.\n        "));
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(471);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, _read_unlocked$46, (PyObject)null);
      var1.setlocal("_read_unlocked", var4);
      var3 = null;
      var1.setline(527);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, peek$47, PyUnicode.fromInterned("Returns buffered bytes without advancing the position.\n\n        The argument indicates a desired minimal number of bytes; we\n        do at most one raw read to satisfy it.  We never return more\n        than self.buffer_size.\n        "));
      var1.setlocal("peek", var4);
      var3 = null;
      var1.setline(537);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, _peek_unlocked$48, (PyObject)null);
      var1.setlocal("_peek_unlocked", var4);
      var3 = null;
      var1.setline(555);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read1$49, PyUnicode.fromInterned("Reads up to n bytes, with at most one read() system call."));
      var1.setlocal("read1", var4);
      var3 = null;
      var1.setline(569);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tell$50, (PyObject)null);
      var1.setlocal("tell", var4);
      var3 = null;
      var1.setline(572);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, seek$51, (PyObject)null);
      var1.setlocal("seek", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$43(PyFrame var1, ThreadState var2) {
      var1.setline(441);
      PyUnicode.fromInterned("Create a new buffered reader using the given readable raw IO object.\n        ");
      var1.setline(442);
      if (var1.getlocal(1).__getattr__("readable").__call__(var2).__not__().__nonzero__()) {
         var1.setline(443);
         throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\"raw\" argument must be readable.")));
      } else {
         var1.setline(445);
         var1.getglobal("_BufferedIOMixin").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.setline(446);
         PyObject var3 = var1.getlocal(2);
         PyObject var10000 = var3._le(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(447);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("invalid buffer size")));
         } else {
            var1.setline(448);
            var3 = var1.getlocal(2);
            var1.getlocal(0).__setattr__("buffer_size", var3);
            var3 = null;
            var1.setline(449);
            var1.getlocal(0).__getattr__("_reset_read_buf").__call__(var2);
            var1.setline(450);
            var3 = var1.getglobal("Lock").__call__(var2);
            var1.getlocal(0).__setattr__("_read_lock", var3);
            var3 = null;
            var1.setline(451);
            var3 = var1.getglobal("True");
            var1.getlocal(0).__setattr__("_ok", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject _reset_read_buf$44(PyFrame var1, ThreadState var2) {
      var1.setline(454);
      PyString var3 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"_read_buf", var3);
      var3 = null;
      var1.setline(455);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_read_pos", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read$45(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(464);
      PyUnicode.fromInterned("Read n bytes.\n\n        Returns exactly n bytes of data unless the underlying raw IO\n        stream reaches EOF or if the call would block in non-blocking\n        mode. If n is negative, read until EOF or until read() would\n        block.\n        ");
      var1.setline(465);
      var1.getlocal(0).__getattr__("_checkReadable").__call__(var2);
      var1.setline(466);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._lt(Py.newInteger(-1));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(467);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("invalid number of bytes to read")));
      } else {
         ContextManager var7;
         PyObject var4 = (var7 = ContextGuard.getManager(var1.getlocal(0).__getattr__("_read_lock"))).__enter__(var2);

         Throwable var8;
         label36: {
            boolean var10001;
            try {
               var1.setline(469);
               var4 = var1.getlocal(0).__getattr__("_read_unlocked").__call__(var2, var1.getlocal(1));
            } catch (Throwable var6) {
               var8 = var6;
               var10001 = false;
               break label36;
            }

            var7.__exit__(var2, (PyException)null);

            try {
               var1.f_lasti = -1;
               return var4;
            } catch (Throwable var5) {
               var8 = var5;
               var10001 = false;
            }
         }

         if (!var7.__exit__(var2, Py.setException(var8, var1))) {
            throw (Throwable)Py.makeException();
         } else {
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject _read_unlocked$46(PyFrame var1, ThreadState var2) {
      var1.setline(472);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(473);
      PyTuple var9 = new PyTuple(new PyObject[]{PyString.fromInterned(""), var1.getglobal("None")});
      var1.setlocal(3, var9);
      var3 = null;
      var1.setline(474);
      PyObject var10 = var1.getlocal(0).__getattr__("_read_buf");
      var1.setlocal(4, var10);
      var3 = null;
      var1.setline(475);
      var10 = var1.getlocal(0).__getattr__("_read_pos");
      var1.setlocal(5, var10);
      var3 = null;
      var1.setline(478);
      var10 = var1.getlocal(1);
      PyObject var10000 = var10._is(var1.getglobal("None"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var10 = var1.getlocal(1);
         var10000 = var10._eq(Py.newInteger(-1));
         var3 = null;
      }

      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(479);
         var1.getlocal(0).__getattr__("_reset_read_buf").__call__(var2);
         var1.setline(480);
         PyList var13 = new PyList(new PyObject[]{var1.getlocal(4).__getslice__(var1.getlocal(5), (PyObject)null, (PyObject)null)});
         var1.setlocal(6, var13);
         var3 = null;
         var1.setline(481);
         PyInteger var14 = Py.newInteger(0);
         var1.setlocal(7, var14);
         var3 = null;

         while(true) {
            var1.setline(482);
            if (!var1.getglobal("True").__nonzero__()) {
               break;
            }

            try {
               var1.setline(485);
               var10 = var1.getlocal(0).__getattr__("raw").__getattr__("read").__call__(var2);
               var1.setlocal(8, var10);
               var3 = null;
            } catch (Throwable var7) {
               PyException var15 = Py.setException(var7, var1);
               if (var15.match(var1.getglobal("IOError"))) {
                  var4 = var15.value;
                  var1.setlocal(9, var4);
                  var4 = null;
                  var1.setline(487);
                  var4 = var1.getlocal(9).__getattr__("errno");
                  var10000 = var4._ne(var1.getglobal("EINTR"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(488);
                     throw Py.makeException();
                  }
                  continue;
               }

               throw var15;
            }

            var1.setline(490);
            var10 = var1.getlocal(8);
            var10000 = var10._in(var1.getlocal(3));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(491);
               var10 = var1.getlocal(8);
               var1.setlocal(2, var10);
               var3 = null;
               break;
            }

            var1.setline(493);
            var10 = var1.getlocal(7);
            var10 = var10._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(8)));
            var1.setlocal(7, var10);
            var1.setline(494);
            var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(8));
         }

         var1.setline(495);
         var10000 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(6));
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(2);
         }

         var10 = var10000;
         var1.f_lasti = -1;
         return var10;
      } else {
         var1.setline(498);
         var4 = var1.getglobal("len").__call__(var2, var1.getlocal(4))._sub(var1.getlocal(5));
         var1.setlocal(10, var4);
         var4 = null;
         var1.setline(499);
         var4 = var1.getlocal(1);
         var10000 = var4._le(var1.getlocal(10));
         var4 = null;
         PyObject var5;
         if (var10000.__nonzero__()) {
            var1.setline(501);
            var10000 = var1.getlocal(0);
            String var17 = "_read_pos";
            var5 = var10000;
            PyObject var6 = var5.__getattr__(var17);
            var6 = var6._iadd(var1.getlocal(1));
            var5.__setattr__(var17, var6);
            var1.setline(502);
            var10 = var1.getlocal(4).__getslice__(var1.getlocal(5), var1.getlocal(5)._add(var1.getlocal(1)), (PyObject)null);
            var1.f_lasti = -1;
            return var10;
         } else {
            var1.setline(505);
            PyList var11 = new PyList(new PyObject[]{var1.getlocal(4).__getslice__(var1.getlocal(5), (PyObject)null, (PyObject)null)});
            var1.setlocal(6, var11);
            var4 = null;
            var1.setline(506);
            var4 = var1.getglobal("max").__call__(var2, var1.getlocal(0).__getattr__("buffer_size"), var1.getlocal(1));
            var1.setlocal(11, var4);
            var4 = null;

            while(true) {
               var1.setline(507);
               var4 = var1.getlocal(10);
               var10000 = var4._lt(var1.getlocal(1));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  break;
               }

               try {
                  var1.setline(509);
                  var4 = var1.getlocal(0).__getattr__("raw").__getattr__("read").__call__(var2, var1.getlocal(11));
                  var1.setlocal(8, var4);
                  var4 = null;
               } catch (Throwable var8) {
                  PyException var12 = Py.setException(var8, var1);
                  if (var12.match(var1.getglobal("IOError"))) {
                     var5 = var12.value;
                     var1.setlocal(9, var5);
                     var5 = null;
                     var1.setline(511);
                     var5 = var1.getlocal(9).__getattr__("errno");
                     var10000 = var5._ne(var1.getglobal("EINTR"));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(512);
                        throw Py.makeException();
                     }
                     continue;
                  }

                  throw var12;
               }

               var1.setline(514);
               var4 = var1.getlocal(8);
               var10000 = var4._in(var1.getlocal(3));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(515);
                  var4 = var1.getlocal(8);
                  var1.setlocal(2, var4);
                  var4 = null;
                  break;
               }

               var1.setline(517);
               var4 = var1.getlocal(10);
               var4 = var4._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(8)));
               var1.setlocal(10, var4);
               var1.setline(518);
               var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(8));
            }

            var1.setline(521);
            var4 = var1.getglobal("min").__call__(var2, var1.getlocal(1), var1.getlocal(10));
            var1.setlocal(1, var4);
            var4 = null;
            var1.setline(522);
            var4 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(6));
            var1.setlocal(12, var4);
            var4 = null;
            var1.setline(523);
            var4 = var1.getlocal(12).__getslice__(var1.getlocal(1), (PyObject)null, (PyObject)null);
            var1.getlocal(0).__setattr__("_read_buf", var4);
            var4 = null;
            var1.setline(524);
            PyInteger var16 = Py.newInteger(0);
            var1.getlocal(0).__setattr__((String)"_read_pos", var16);
            var4 = null;
            var1.setline(525);
            var1.setline(525);
            var10 = var1.getlocal(12).__nonzero__() ? var1.getlocal(12).__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null) : var1.getlocal(2);
            var1.f_lasti = -1;
            return var10;
         }
      }
   }

   public PyObject peek$47(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(533);
      PyUnicode.fromInterned("Returns buffered bytes without advancing the position.\n\n        The argument indicates a desired minimal number of bytes; we\n        do at most one raw read to satisfy it.  We never return more\n        than self.buffer_size.\n        ");
      ContextManager var3;
      PyObject var4 = (var3 = ContextGuard.getManager(var1.getlocal(0).__getattr__("_read_lock"))).__enter__(var2);

      Throwable var10000;
      label28: {
         boolean var10001;
         try {
            var1.setline(535);
            var4 = var1.getlocal(0).__getattr__("_peek_unlocked").__call__(var2, var1.getlocal(1));
         } catch (Throwable var6) {
            var10000 = var6;
            var10001 = false;
            break label28;
         }

         var3.__exit__(var2, (PyException)null);

         try {
            var1.f_lasti = -1;
            return var4;
         } catch (Throwable var5) {
            var10000 = var5;
            var10001 = false;
         }
      }

      if (!var3.__exit__(var2, Py.setException(var10000, var1))) {
         throw (Throwable)Py.makeException();
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _peek_unlocked$48(PyFrame var1, ThreadState var2) {
      var1.setline(538);
      PyObject var3 = var1.getglobal("min").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("buffer_size"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(539);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_read_buf"))._sub(var1.getlocal(0).__getattr__("_read_pos"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(540);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._lt(var1.getlocal(2));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(3);
         var10000 = var3._le(Py.newInteger(0));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(541);
         var3 = var1.getlocal(0).__getattr__("buffer_size")._sub(var1.getlocal(3));
         var1.setlocal(4, var3);
         var3 = null;

         while(true) {
            var1.setline(542);
            if (!var1.getglobal("True").__nonzero__()) {
               break;
            }

            try {
               var1.setline(544);
               var3 = var1.getlocal(0).__getattr__("raw").__getattr__("read").__call__(var2, var1.getlocal(4));
               var1.setlocal(5, var3);
               var3 = null;
               break;
            } catch (Throwable var5) {
               PyException var6 = Py.setException(var5, var1);
               if (!var6.match(var1.getglobal("IOError"))) {
                  throw var6;
               }

               PyObject var4 = var6.value;
               var1.setlocal(6, var4);
               var4 = null;
               var1.setline(546);
               var4 = var1.getlocal(6).__getattr__("errno");
               var10000 = var4._ne(var1.getglobal("EINTR"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(547);
                  throw Py.makeException();
               }
            }
         }

         var1.setline(550);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(551);
            var3 = var1.getlocal(0).__getattr__("_read_buf").__getslice__(var1.getlocal(0).__getattr__("_read_pos"), (PyObject)null, (PyObject)null)._add(var1.getlocal(5));
            var1.getlocal(0).__setattr__("_read_buf", var3);
            var3 = null;
            var1.setline(552);
            PyInteger var7 = Py.newInteger(0);
            var1.getlocal(0).__setattr__((String)"_read_pos", var7);
            var3 = null;
         }
      }

      var1.setline(553);
      var3 = var1.getlocal(0).__getattr__("_read_buf").__getslice__(var1.getlocal(0).__getattr__("_read_pos"), (PyObject)null, (PyObject)null);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject read1$49(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(556);
      PyUnicode.fromInterned("Reads up to n bytes, with at most one read() system call.");
      var1.setline(559);
      var1.getlocal(0).__getattr__("_checkReadable").__call__(var2);
      var1.setline(560);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(561);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("number of bytes to read must be positive")));
      } else {
         var1.setline(562);
         var3 = var1.getlocal(1);
         var10000 = var3._eq(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(563);
            PyString var8 = PyString.fromInterned("");
            var1.f_lasti = -1;
            return var8;
         } else {
            ContextManager var4;
            PyObject var5 = (var4 = ContextGuard.getManager(var1.getlocal(0).__getattr__("_read_lock"))).__enter__(var2);

            Throwable var9;
            label36: {
               boolean var10001;
               try {
                  var1.setline(565);
                  var1.getlocal(0).__getattr__("_peek_unlocked").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
                  var1.setline(566);
                  var3 = var1.getlocal(0).__getattr__("_read_unlocked").__call__(var2, var1.getglobal("min").__call__(var2, var1.getlocal(1), var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_read_buf"))._sub(var1.getlocal(0).__getattr__("_read_pos"))));
               } catch (Throwable var7) {
                  var9 = var7;
                  var10001 = false;
                  break label36;
               }

               var4.__exit__(var2, (PyException)null);

               try {
                  var1.f_lasti = -1;
                  return var3;
               } catch (Throwable var6) {
                  var9 = var6;
                  var10001 = false;
               }
            }

            if (!var4.__exit__(var2, Py.setException(var9, var1))) {
               throw (Throwable)Py.makeException();
            } else {
               var1.f_lasti = -1;
               return Py.None;
            }
         }
      }
   }

   public PyObject tell$50(PyFrame var1, ThreadState var2) {
      var1.setline(570);
      PyObject var3 = var1.getglobal("_BufferedIOMixin").__getattr__("tell").__call__(var2, var1.getlocal(0))._sub(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_read_buf")))._add(var1.getlocal(0).__getattr__("_read_pos"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject seek$51(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(573);
      PyInteger var3 = Py.newInteger(0);
      PyObject var10001 = var1.getlocal(2);
      PyInteger var10000 = var3;
      PyObject var7 = var10001;
      PyObject var4;
      if ((var4 = var10000._le(var10001)).__nonzero__()) {
         var4 = var7._le(Py.newInteger(2));
      }

      var3 = null;
      if (var4.__not__().__nonzero__()) {
         var1.setline(574);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("invalid whence value")));
      } else {
         ContextManager var8;
         var4 = (var8 = ContextGuard.getManager(var1.getlocal(0).__getattr__("_read_lock"))).__enter__(var2);

         Throwable var9;
         label42: {
            boolean var11;
            try {
               var1.setline(576);
               var4 = var1.getlocal(2);
               PyObject var10 = var4._eq(Py.newInteger(1));
               var4 = null;
               if (var10.__nonzero__()) {
                  var1.setline(577);
                  var4 = var1.getlocal(1);
                  var4 = var4._isub(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_read_buf"))._sub(var1.getlocal(0).__getattr__("_read_pos")));
                  var1.setlocal(1, var4);
               }

               var1.setline(578);
               var4 = var1.getglobal("_BufferedIOMixin").__getattr__("seek").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
               var1.setlocal(1, var4);
               var4 = null;
               var1.setline(579);
               var1.getlocal(0).__getattr__("_reset_read_buf").__call__(var2);
               var1.setline(580);
               var4 = var1.getlocal(1);
            } catch (Throwable var6) {
               var9 = var6;
               var11 = false;
               break label42;
            }

            var8.__exit__(var2, (PyException)null);

            try {
               var1.f_lasti = -1;
               return var4;
            } catch (Throwable var5) {
               var9 = var5;
               var11 = false;
            }
         }

         if (!var8.__exit__(var2, Py.setException(var9, var1))) {
            throw (Throwable)Py.makeException();
         } else {
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject BufferedWriter$52(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyUnicode.fromInterned("A buffer for a writeable sequential RawIO object.\n\n    The constructor creates a BufferedWriter for the given writeable raw\n    stream. If the buffer_size is not given, it defaults to\n    DEFAULT_BUFFER_SIZE.\n    "));
      var1.setline(590);
      PyUnicode.fromInterned("A buffer for a writeable sequential RawIO object.\n\n    The constructor creates a BufferedWriter for the given writeable raw\n    stream. If the buffer_size is not given, it defaults to\n    DEFAULT_BUFFER_SIZE.\n    ");
      var1.setline(592);
      PyInteger var3 = Py.newInteger(2);
      var1.setlocal("_warning_stack_offset", var3);
      var3 = null;
      var1.setline(594);
      PyObject[] var4 = new PyObject[]{var1.getname("DEFAULT_BUFFER_SIZE"), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$53, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(610);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, write$54, (PyObject)null);
      var1.setlocal("write", var5);
      var3 = null;
      var1.setline(642);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, truncate$55, (PyObject)null);
      var1.setlocal("truncate", var5);
      var3 = null;
      var1.setline(649);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, flush$56, (PyObject)null);
      var1.setlocal("flush", var5);
      var3 = null;
      var1.setline(653);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _flush_unlocked$57, (PyObject)null);
      var1.setlocal("_flush_unlocked", var5);
      var3 = null;
      var1.setline(675);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, tell$58, (PyObject)null);
      var1.setlocal("tell", var5);
      var3 = null;
      var1.setline(678);
      var4 = new PyObject[]{Py.newInteger(0)};
      var5 = new PyFunction(var1.f_globals, var4, seek$59, (PyObject)null);
      var1.setlocal("seek", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$53(PyFrame var1, ThreadState var2) {
      var1.setline(596);
      if (var1.getlocal(1).__getattr__("writable").__call__(var2).__not__().__nonzero__()) {
         var1.setline(597);
         throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\"raw\" argument must be writable.")));
      } else {
         var1.setline(599);
         var1.getglobal("_BufferedIOMixin").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.setline(600);
         PyObject var3 = var1.getlocal(2);
         PyObject var10000 = var3._le(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(601);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("invalid buffer size")));
         } else {
            var1.setline(602);
            var3 = var1.getlocal(3);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(603);
               var1.getglobal("warnings").__getattr__("warn").__call__((ThreadState)var2, PyUnicode.fromInterned("max_buffer_size is deprecated"), (PyObject)var1.getglobal("DeprecationWarning"), (PyObject)var1.getlocal(0).__getattr__("_warning_stack_offset"));
            }

            var1.setline(605);
            var3 = var1.getlocal(2);
            var1.getlocal(0).__setattr__("buffer_size", var3);
            var3 = null;
            var1.setline(606);
            var3 = var1.getglobal("bytearray").__call__(var2);
            var1.getlocal(0).__setattr__("_write_buf", var3);
            var3 = null;
            var1.setline(607);
            var3 = var1.getglobal("Lock").__call__(var2);
            var1.getlocal(0).__setattr__("_write_lock", var3);
            var3 = null;
            var1.setline(608);
            var3 = var1.getglobal("True");
            var1.getlocal(0).__setattr__("_ok", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject write$54(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(611);
      var1.getlocal(0).__getattr__("_checkWritable").__call__(var2);
      var1.setline(612);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(613);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("write to closed file")));
      } else {
         var1.setline(614);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("unicode")).__nonzero__()) {
            var1.setline(615);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("can't write unicode to binary stream")));
         } else {
            ContextManager var3;
            PyObject var4 = (var3 = ContextGuard.getManager(var1.getlocal(0).__getattr__("_write_lock"))).__enter__(var2);

            Throwable var10000;
            label66: {
               boolean var10001;
               try {
                  var1.setline(619);
                  var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_write_buf"));
                  PyObject var10 = var4._gt(var1.getlocal(0).__getattr__("buffer_size"));
                  var4 = null;
                  if (var10.__nonzero__()) {
                     var1.setline(622);
                     var1.getlocal(0).__getattr__("_flush_unlocked").__call__(var2);
                  }

                  var1.setline(623);
                  var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_write_buf"));
                  var1.setlocal(2, var4);
                  var4 = null;
                  var1.setline(624);
                  if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("array").__getattr__("array")).__nonzero__()) {
                     var1.setline(625);
                     var1.getlocal(0).__getattr__("_write_buf").__getattr__("extend").__call__(var2, var1.getlocal(1).__getattr__("tostring").__call__(var2));
                  } else {
                     var1.setline(627);
                     var1.getlocal(0).__getattr__("_write_buf").__getattr__("extend").__call__(var2, var1.getlocal(1));
                  }

                  var1.setline(628);
                  var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_write_buf"))._sub(var1.getlocal(2));
                  var1.setlocal(3, var4);
                  var4 = null;
                  var1.setline(629);
                  var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_write_buf"));
                  var10 = var4._gt(var1.getlocal(0).__getattr__("buffer_size"));
                  var4 = null;
                  if (var10.__nonzero__()) {
                     try {
                        var1.setline(631);
                        var1.getlocal(0).__getattr__("_flush_unlocked").__call__(var2);
                     } catch (Throwable var6) {
                        PyException var9 = Py.setException(var6, var1);
                        if (!var9.match(var1.getglobal("BlockingIOError"))) {
                           throw var9;
                        }

                        PyObject var5 = var9.value;
                        var1.setlocal(4, var5);
                        var5 = null;
                        var1.setline(633);
                        var5 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_write_buf"));
                        var10 = var5._gt(var1.getlocal(0).__getattr__("buffer_size"));
                        var5 = null;
                        if (var10.__nonzero__()) {
                           var1.setline(636);
                           var5 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_write_buf"))._sub(var1.getlocal(0).__getattr__("buffer_size"));
                           var1.setlocal(5, var5);
                           var5 = null;
                           var1.setline(637);
                           var5 = var1.getlocal(3);
                           var5 = var5._isub(var1.getlocal(5));
                           var1.setlocal(3, var5);
                           var1.setline(638);
                           var5 = var1.getlocal(0).__getattr__("_write_buf").__getslice__((PyObject)null, var1.getlocal(0).__getattr__("buffer_size"), (PyObject)null);
                           var1.getlocal(0).__setattr__("_write_buf", var5);
                           var5 = null;
                           var1.setline(639);
                           throw Py.makeException(var1.getglobal("BlockingIOError").__call__(var2, var1.getlocal(4).__getattr__("errno"), var1.getlocal(4).__getattr__("strerror"), var1.getlocal(3)));
                        }
                     }
                  }

                  var1.setline(640);
                  var4 = var1.getlocal(3);
               } catch (Throwable var8) {
                  var10000 = var8;
                  var10001 = false;
                  break label66;
               }

               var3.__exit__(var2, (PyException)null);

               try {
                  var1.f_lasti = -1;
                  return var4;
               } catch (Throwable var7) {
                  var10000 = var7;
                  var10001 = false;
               }
            }

            if (!var3.__exit__(var2, Py.setException(var10000, var1))) {
               throw (Throwable)Py.makeException();
            } else {
               var1.f_lasti = -1;
               return Py.None;
            }
         }
      }
   }

   public PyObject truncate$55(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      ContextManager var3;
      PyObject var4 = (var3 = ContextGuard.getManager(var1.getlocal(0).__getattr__("_write_lock"))).__enter__(var2);

      Throwable var10000;
      label34: {
         boolean var10001;
         try {
            var1.setline(644);
            var1.getlocal(0).__getattr__("_flush_unlocked").__call__(var2);
            var1.setline(645);
            var4 = var1.getlocal(1);
            PyObject var7 = var4._is(var1.getglobal("None"));
            var4 = null;
            if (var7.__nonzero__()) {
               var1.setline(646);
               var4 = var1.getlocal(0).__getattr__("raw").__getattr__("tell").__call__(var2);
               var1.setlocal(1, var4);
               var4 = null;
            }

            var1.setline(647);
            var4 = var1.getlocal(0).__getattr__("raw").__getattr__("truncate").__call__(var2, var1.getlocal(1));
         } catch (Throwable var6) {
            var10000 = var6;
            var10001 = false;
            break label34;
         }

         var3.__exit__(var2, (PyException)null);

         try {
            var1.f_lasti = -1;
            return var4;
         } catch (Throwable var5) {
            var10000 = var5;
            var10001 = false;
         }
      }

      if (!var3.__exit__(var2, Py.setException(var10000, var1))) {
         throw (Throwable)Py.makeException();
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject flush$56(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      ContextManager var3;
      PyObject var4 = (var3 = ContextGuard.getManager(var1.getlocal(0).__getattr__("_write_lock"))).__enter__(var2);

      label16: {
         try {
            var1.setline(651);
            var1.getlocal(0).__getattr__("_flush_unlocked").__call__(var2);
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

   public PyObject _flush_unlocked$57(PyFrame var1, ThreadState var2) {
      var1.setline(654);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(655);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("flush of closed file")));
      } else {
         var1.setline(656);
         var1.getlocal(0).__getattr__("_checkWritable").__call__(var2);

         while(true) {
            var1.setline(657);
            if (!var1.getlocal(0).__getattr__("_write_buf").__nonzero__()) {
               var1.f_lasti = -1;
               return Py.None;
            }

            PyObject var10000;
            PyException var3;
            PyObject var6;
            try {
               var1.setline(659);
               var6 = var1.getlocal(0).__getattr__("raw").__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("_write_buf"));
               var1.setlocal(1, var6);
               var3 = null;
            } catch (Throwable var5) {
               var3 = Py.setException(var5, var1);
               if (var3.match(var1.getglobal("BlockingIOError"))) {
                  var1.setline(661);
                  throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("self.raw should implement _RawIOBase: it should not raise BlockingIOError")));
               }

               if (!var3.match(var1.getglobal("IOError"))) {
                  throw var3;
               }

               PyObject var4 = var3.value;
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(664);
               var4 = var1.getlocal(2).__getattr__("errno");
               var10000 = var4._ne(var1.getglobal("EINTR"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(665);
                  throw Py.makeException();
               }
               continue;
            }

            var1.setline(667);
            var6 = var1.getlocal(1);
            var10000 = var6._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(668);
               throw Py.makeException(var1.getglobal("BlockingIOError").__call__((ThreadState)var2, var1.getglobal("errno").__getattr__("EAGAIN"), (PyObject)PyUnicode.fromInterned("write could not complete without blocking"), (PyObject)Py.newInteger(0)));
            }

            var1.setline(671);
            var6 = var1.getlocal(1);
            var10000 = var6._gt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_write_buf")));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var6 = var1.getlocal(1);
               var10000 = var6._lt(Py.newInteger(0));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(672);
               throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("write() returned incorrect number of bytes")));
            }

            var1.setline(673);
            var1.getlocal(0).__getattr__("_write_buf").__delslice__((PyObject)null, var1.getlocal(1), (PyObject)null);
         }
      }
   }

   public PyObject tell$58(PyFrame var1, ThreadState var2) {
      var1.setline(676);
      PyObject var3 = var1.getglobal("_BufferedIOMixin").__getattr__("tell").__call__(var2, var1.getlocal(0))._add(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_write_buf")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject seek$59(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(679);
      PyInteger var3 = Py.newInteger(0);
      PyObject var10001 = var1.getlocal(2);
      PyInteger var10000 = var3;
      PyObject var7 = var10001;
      PyObject var4;
      if ((var4 = var10000._le(var10001)).__nonzero__()) {
         var4 = var7._le(Py.newInteger(2));
      }

      var3 = null;
      if (var4.__not__().__nonzero__()) {
         var1.setline(680);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("invalid whence")));
      } else {
         ContextManager var8;
         var4 = (var8 = ContextGuard.getManager(var1.getlocal(0).__getattr__("_write_lock"))).__enter__(var2);

         Throwable var9;
         label36: {
            boolean var10;
            try {
               var1.setline(682);
               var1.getlocal(0).__getattr__("_flush_unlocked").__call__(var2);
               var1.setline(683);
               var4 = var1.getglobal("_BufferedIOMixin").__getattr__("seek").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
            } catch (Throwable var6) {
               var9 = var6;
               var10 = false;
               break label36;
            }

            var8.__exit__(var2, (PyException)null);

            try {
               var1.f_lasti = -1;
               return var4;
            } catch (Throwable var5) {
               var9 = var5;
               var10 = false;
            }
         }

         if (!var8.__exit__(var2, Py.setException(var9, var1))) {
            throw (Throwable)Py.makeException();
         } else {
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject BufferedRWPair$60(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyUnicode.fromInterned("A buffered reader and writer object together.\n\n    A buffered reader object and buffered writer object put together to\n    form a sequential IO object that can read and write. This is typically\n    used with a socket or two-way pipe.\n\n    reader and writer are _RawIOBase objects that are readable and\n    writeable respectively. If the buffer_size is omitted it defaults to\n    DEFAULT_BUFFER_SIZE.\n    "));
      var1.setline(697);
      PyUnicode.fromInterned("A buffered reader and writer object together.\n\n    A buffered reader object and buffered writer object put together to\n    form a sequential IO object that can read and write. This is typically\n    used with a socket or two-way pipe.\n\n    reader and writer are _RawIOBase objects that are readable and\n    writeable respectively. If the buffer_size is omitted it defaults to\n    DEFAULT_BUFFER_SIZE.\n    ");
      var1.setline(702);
      PyObject[] var3 = new PyObject[]{var1.getname("DEFAULT_BUFFER_SIZE"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$61, PyUnicode.fromInterned("Constructor.\n\n        The arguments are two RawIO instances.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(720);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, read$62, (PyObject)null);
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(725);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, readinto$63, (PyObject)null);
      var1.setlocal("readinto", var4);
      var3 = null;
      var1.setline(728);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, write$64, (PyObject)null);
      var1.setlocal("write", var4);
      var3 = null;
      var1.setline(731);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, peek$65, (PyObject)null);
      var1.setlocal("peek", var4);
      var3 = null;
      var1.setline(734);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read1$66, (PyObject)null);
      var1.setlocal("read1", var4);
      var3 = null;
      var1.setline(737);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, readable$67, (PyObject)null);
      var1.setlocal("readable", var4);
      var3 = null;
      var1.setline(740);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, writable$68, (PyObject)null);
      var1.setlocal("writable", var4);
      var3 = null;
      var1.setline(743);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, flush$69, (PyObject)null);
      var1.setlocal("flush", var4);
      var3 = null;
      var1.setline(746);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$70, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(750);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isatty$71, (PyObject)null);
      var1.setlocal("isatty", var4);
      var3 = null;
      var1.setline(753);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, closed$72, (PyObject)null);
      PyObject var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("closed", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$61(PyFrame var1, ThreadState var2) {
      var1.setline(707);
      PyUnicode.fromInterned("Constructor.\n\n        The arguments are two RawIO instances.\n        ");
      var1.setline(708);
      PyObject var3 = var1.getlocal(4);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(709);
         var1.getglobal("warnings").__getattr__("warn").__call__((ThreadState)var2, PyUnicode.fromInterned("max_buffer_size is deprecated"), (PyObject)var1.getglobal("DeprecationWarning"), (PyObject)Py.newInteger(2));
      }

      var1.setline(711);
      if (var1.getlocal(1).__getattr__("readable").__call__(var2).__not__().__nonzero__()) {
         var1.setline(712);
         throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\"reader\" argument must be readable.")));
      } else {
         var1.setline(714);
         if (var1.getlocal(2).__getattr__("writable").__call__(var2).__not__().__nonzero__()) {
            var1.setline(715);
            throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\"writer\" argument must be writable.")));
         } else {
            var1.setline(717);
            var3 = var1.getglobal("BufferedReader").__call__(var2, var1.getlocal(1), var1.getlocal(3));
            var1.getlocal(0).__setattr__("reader", var3);
            var3 = null;
            var1.setline(718);
            var3 = var1.getglobal("BufferedWriter").__call__(var2, var1.getlocal(2), var1.getlocal(3));
            var1.getlocal(0).__setattr__("writer", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject read$62(PyFrame var1, ThreadState var2) {
      var1.setline(721);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(722);
         PyInteger var4 = Py.newInteger(-1);
         var1.setlocal(1, var4);
         var3 = null;
      }

      var1.setline(723);
      var3 = var1.getlocal(0).__getattr__("reader").__getattr__("read").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject readinto$63(PyFrame var1, ThreadState var2) {
      var1.setline(726);
      PyObject var3 = var1.getlocal(0).__getattr__("reader").__getattr__("readinto").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject write$64(PyFrame var1, ThreadState var2) {
      var1.setline(729);
      PyObject var3 = var1.getlocal(0).__getattr__("writer").__getattr__("write").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject peek$65(PyFrame var1, ThreadState var2) {
      var1.setline(732);
      PyObject var3 = var1.getlocal(0).__getattr__("reader").__getattr__("peek").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject read1$66(PyFrame var1, ThreadState var2) {
      var1.setline(735);
      PyObject var3 = var1.getlocal(0).__getattr__("reader").__getattr__("read1").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject readable$67(PyFrame var1, ThreadState var2) {
      var1.setline(738);
      PyObject var3 = var1.getlocal(0).__getattr__("reader").__getattr__("readable").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject writable$68(PyFrame var1, ThreadState var2) {
      var1.setline(741);
      PyObject var3 = var1.getlocal(0).__getattr__("writer").__getattr__("writable").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject flush$69(PyFrame var1, ThreadState var2) {
      var1.setline(744);
      PyObject var3 = var1.getlocal(0).__getattr__("writer").__getattr__("flush").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject close$70(PyFrame var1, ThreadState var2) {
      var1.setline(747);
      var1.getlocal(0).__getattr__("writer").__getattr__("close").__call__(var2);
      var1.setline(748);
      var1.getlocal(0).__getattr__("reader").__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject isatty$71(PyFrame var1, ThreadState var2) {
      var1.setline(751);
      PyObject var10000 = var1.getlocal(0).__getattr__("reader").__getattr__("isatty").__call__(var2);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("writer").__getattr__("isatty").__call__(var2);
      }

      PyObject var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject closed$72(PyFrame var1, ThreadState var2) {
      var1.setline(755);
      PyObject var3 = var1.getlocal(0).__getattr__("writer").__getattr__("closed");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject BufferedRandom$73(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyUnicode.fromInterned("A buffered interface to random access streams.\n\n    The constructor creates a reader and writer for a seekable stream,\n    raw, given in the first argument. If the buffer_size is omitted it\n    defaults to DEFAULT_BUFFER_SIZE.\n    "));
      var1.setline(765);
      PyUnicode.fromInterned("A buffered interface to random access streams.\n\n    The constructor creates a reader and writer for a seekable stream,\n    raw, given in the first argument. If the buffer_size is omitted it\n    defaults to DEFAULT_BUFFER_SIZE.\n    ");
      var1.setline(767);
      PyInteger var3 = Py.newInteger(3);
      var1.setlocal("_warning_stack_offset", var3);
      var3 = null;
      var1.setline(769);
      PyObject[] var4 = new PyObject[]{var1.getname("DEFAULT_BUFFER_SIZE"), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$74, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(775);
      var4 = new PyObject[]{Py.newInteger(0)};
      var5 = new PyFunction(var1.f_globals, var4, seek$75, (PyObject)null);
      var1.setlocal("seek", var5);
      var3 = null;
      var1.setline(792);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, tell$76, (PyObject)null);
      var1.setlocal("tell", var5);
      var3 = null;
      var1.setline(798);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, truncate$77, (PyObject)null);
      var1.setlocal("truncate", var5);
      var3 = null;
      var1.setline(804);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, read$78, (PyObject)null);
      var1.setlocal("read", var5);
      var3 = null;
      var1.setline(810);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, readinto$79, (PyObject)null);
      var1.setlocal("readinto", var5);
      var3 = null;
      var1.setline(814);
      var4 = new PyObject[]{Py.newInteger(0)};
      var5 = new PyFunction(var1.f_globals, var4, peek$80, (PyObject)null);
      var1.setlocal("peek", var5);
      var3 = null;
      var1.setline(818);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, read1$81, (PyObject)null);
      var1.setlocal("read1", var5);
      var3 = null;
      var1.setline(822);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, write$82, (PyObject)null);
      var1.setlocal("write", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$74(PyFrame var1, ThreadState var2) {
      var1.setline(771);
      var1.getlocal(1).__getattr__("_checkSeekable").__call__(var2);
      var1.setline(772);
      var1.getglobal("BufferedReader").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.setline(773);
      var1.getglobal("BufferedWriter").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject seek$75(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[2];
      var1.setline(776);
      PyInteger var3 = Py.newInteger(0);
      PyObject var10001 = var1.getlocal(2);
      PyInteger var10000 = var3;
      PyObject var7 = var10001;
      PyObject var4;
      if ((var4 = var10000._le(var10001)).__nonzero__()) {
         var4 = var7._le(Py.newInteger(2));
      }

      var3 = null;
      if (var4.__not__().__nonzero__()) {
         var1.setline(777);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("invalid whence")));
      } else {
         var1.setline(778);
         var1.getlocal(0).__getattr__("flush").__call__(var2);
         var1.setline(779);
         ContextManager var8;
         if (var1.getlocal(0).__getattr__("_read_buf").__nonzero__()) {
            label51: {
               var4 = (var8 = ContextGuard.getManager(var1.getlocal(0).__getattr__("_read_lock"))).__enter__(var2);

               try {
                  var1.setline(782);
                  var1.getlocal(0).__getattr__("raw").__getattr__("seek").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_read_pos")._sub(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_read_buf"))), (PyObject)Py.newInteger(1));
               } catch (Throwable var6) {
                  if (var8.__exit__(var2, Py.setException(var6, var1))) {
                     break label51;
                  }

                  throw (Throwable)Py.makeException();
               }

               var8.__exit__(var2, (PyException)null);
            }
         }

         var1.setline(785);
         var7 = var1.getlocal(0).__getattr__("raw").__getattr__("seek").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setlocal(1, var7);
         var3 = null;
         var4 = (var8 = ContextGuard.getManager(var1.getlocal(0).__getattr__("_read_lock"))).__enter__(var2);

         label33: {
            try {
               var1.setline(787);
               var1.getlocal(0).__getattr__("_reset_read_buf").__call__(var2);
            } catch (Throwable var5) {
               if (var8.__exit__(var2, Py.setException(var5, var1))) {
                  break label33;
               }

               throw (Throwable)Py.makeException();
            }

            var8.__exit__(var2, (PyException)null);
         }

         var1.setline(788);
         var7 = var1.getlocal(1);
         PyObject var9 = var7._lt(Py.newInteger(0));
         var3 = null;
         if (var9.__nonzero__()) {
            var1.setline(789);
            throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("seek() returned invalid position")));
         } else {
            var1.setline(790);
            var7 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var7;
         }
      }
   }

   public PyObject tell$76(PyFrame var1, ThreadState var2) {
      var1.setline(793);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_write_buf").__nonzero__()) {
         var1.setline(794);
         var3 = var1.getglobal("BufferedWriter").__getattr__("tell").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(796);
         var3 = var1.getglobal("BufferedReader").__getattr__("tell").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject truncate$77(PyFrame var1, ThreadState var2) {
      var1.setline(799);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(800);
         var3 = var1.getlocal(0).__getattr__("tell").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(802);
      var3 = var1.getglobal("BufferedWriter").__getattr__("truncate").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject read$78(PyFrame var1, ThreadState var2) {
      var1.setline(805);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(806);
         PyInteger var4 = Py.newInteger(-1);
         var1.setlocal(1, var4);
         var3 = null;
      }

      var1.setline(807);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(808);
      var3 = var1.getglobal("BufferedReader").__getattr__("read").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject readinto$79(PyFrame var1, ThreadState var2) {
      var1.setline(811);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(812);
      PyObject var3 = var1.getglobal("BufferedReader").__getattr__("readinto").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject peek$80(PyFrame var1, ThreadState var2) {
      var1.setline(815);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(816);
      PyObject var3 = var1.getglobal("BufferedReader").__getattr__("peek").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject read1$81(PyFrame var1, ThreadState var2) {
      var1.setline(819);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(820);
      PyObject var3 = var1.getglobal("BufferedReader").__getattr__("read1").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject write$82(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(823);
      if (var1.getlocal(0).__getattr__("_read_buf").__nonzero__()) {
         label24: {
            ContextManager var3;
            PyObject var4 = (var3 = ContextGuard.getManager(var1.getlocal(0).__getattr__("_read_lock"))).__enter__(var2);

            try {
               var1.setline(826);
               var1.getlocal(0).__getattr__("raw").__getattr__("seek").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_read_pos")._sub(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_read_buf"))), (PyObject)Py.newInteger(1));
               var1.setline(827);
               var1.getlocal(0).__getattr__("_reset_read_buf").__call__(var2);
            } catch (Throwable var5) {
               if (var3.__exit__(var2, Py.setException(var5, var1))) {
                  break label24;
               }

               throw (Throwable)Py.makeException();
            }

            var3.__exit__(var2, (PyException)null);
         }
      }

      var1.setline(828);
      PyObject var6 = var1.getglobal("BufferedWriter").__getattr__("write").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject _TextIOBase$83(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyUnicode.fromInterned("Base class for text I/O.\n\n    This class provides a character and line based interface to stream\n    I/O. There is no readinto method because Python's character strings\n    are immutable. There is no public constructor.\n    "));
      var1.setline(838);
      PyUnicode.fromInterned("Base class for text I/O.\n\n    This class provides a character and line based interface to stream\n    I/O. There is no readinto method because Python's character strings\n    are immutable. There is no public constructor.\n    ");
      var1.setline(840);
      PyObject[] var3 = new PyObject[]{Py.newInteger(-1)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, read$84, PyUnicode.fromInterned("Read at most n characters from stream.\n\n        Read from underlying buffer until we have n characters or we hit EOF.\n        If n is negative or omitted, read until EOF.\n        "));
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(848);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, write$85, PyUnicode.fromInterned("Write string s to stream."));
      var1.setlocal("write", var4);
      var3 = null;
      var1.setline(852);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, truncate$86, PyUnicode.fromInterned("Truncate size to pos."));
      var1.setlocal("truncate", var4);
      var3 = null;
      var1.setline(856);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, readline$87, PyUnicode.fromInterned("Read until newline or EOF.\n\n        Returns an empty string if EOF is hit immediately.\n        "));
      var1.setlocal("readline", var4);
      var3 = null;
      var1.setline(863);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, detach$88, PyUnicode.fromInterned("\n        Separate the underlying buffer from the _TextIOBase and return it.\n\n        After the underlying buffer has been detached, the TextIO is in an\n        unusable state.\n        "));
      var1.setlocal("detach", var4);
      var3 = null;
      var1.setline(872);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, encoding$89, PyUnicode.fromInterned("Subclasses should override."));
      PyObject var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("encoding", var5);
      var3 = null;
      var1.setline(877);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, newlines$90, PyUnicode.fromInterned("Line endings translated so far.\n\n        Only line endings translated during reading are considered.\n\n        Subclasses should override.\n        "));
      var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("newlines", var5);
      var3 = null;
      var1.setline(887);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, errors$91, PyUnicode.fromInterned("Error setting of the decoder or encoder.\n\n        Subclasses should override."));
      var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("errors", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject read$84(PyFrame var1, ThreadState var2) {
      var1.setline(845);
      PyUnicode.fromInterned("Read at most n characters from stream.\n\n        Read from underlying buffer until we have n characters or we hit EOF.\n        If n is negative or omitted, read until EOF.\n        ");
      var1.setline(846);
      var1.getlocal(0).__getattr__("_unsupported").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("read"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject write$85(PyFrame var1, ThreadState var2) {
      var1.setline(849);
      PyUnicode.fromInterned("Write string s to stream.");
      var1.setline(850);
      var1.getlocal(0).__getattr__("_unsupported").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("write"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject truncate$86(PyFrame var1, ThreadState var2) {
      var1.setline(853);
      PyUnicode.fromInterned("Truncate size to pos.");
      var1.setline(854);
      var1.getlocal(0).__getattr__("_unsupported").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("truncate"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject readline$87(PyFrame var1, ThreadState var2) {
      var1.setline(860);
      PyUnicode.fromInterned("Read until newline or EOF.\n\n        Returns an empty string if EOF is hit immediately.\n        ");
      var1.setline(861);
      var1.getlocal(0).__getattr__("_unsupported").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("readline"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject detach$88(PyFrame var1, ThreadState var2) {
      var1.setline(869);
      PyUnicode.fromInterned("\n        Separate the underlying buffer from the _TextIOBase and return it.\n\n        After the underlying buffer has been detached, the TextIO is in an\n        unusable state.\n        ");
      var1.setline(870);
      var1.getlocal(0).__getattr__("_unsupported").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("detach"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject encoding$89(PyFrame var1, ThreadState var2) {
      var1.setline(874);
      PyUnicode.fromInterned("Subclasses should override.");
      var1.setline(875);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject newlines$90(PyFrame var1, ThreadState var2) {
      var1.setline(884);
      PyUnicode.fromInterned("Line endings translated so far.\n\n        Only line endings translated during reading are considered.\n\n        Subclasses should override.\n        ");
      var1.setline(885);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject errors$91(PyFrame var1, ThreadState var2) {
      var1.setline(891);
      PyUnicode.fromInterned("Error setting of the decoder or encoder.\n\n        Subclasses should override.");
      var1.setline(892);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IncrementalNewlineDecoder$92(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyUnicode.fromInterned("Codec used when reading a file in universal newlines mode.  It wraps\n    another incremental decoder, translating \\r\\n and \\r into \\n.  It also\n    records the types of newlines encountered.  When used with\n    translate=False, it ensures that the newline sequence is returned in\n    one piece.\n    "));
      var1.setline(901);
      PyUnicode.fromInterned("Codec used when reading a file in universal newlines mode.  It wraps\n    another incremental decoder, translating \\r\\n and \\r into \\n.  It also\n    records the types of newlines encountered.  When used with\n    translate=False, it ensures that the newline sequence is returned in\n    one piece.\n    ");
      var1.setline(902);
      PyObject[] var3 = new PyObject[]{PyUnicode.fromInterned("strict")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$93, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(909);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, decode$94, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      var1.setline(940);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getstate$95, (PyObject)null);
      var1.setlocal("getstate", var4);
      var3 = null;
      var1.setline(951);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setstate$96, (PyObject)null);
      var1.setlocal("setstate", var4);
      var3 = null;
      var1.setline(957);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$97, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(963);
      PyInteger var5 = Py.newInteger(1);
      var1.setlocal("_LF", var5);
      var3 = null;
      var1.setline(964);
      var5 = Py.newInteger(2);
      var1.setlocal("_CR", var5);
      var3 = null;
      var1.setline(965);
      var5 = Py.newInteger(4);
      var1.setlocal("_CRLF", var5);
      var3 = null;
      var1.setline(967);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, newlines$98, (PyObject)null);
      PyObject var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("newlines", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$93(PyFrame var1, ThreadState var2) {
      var1.setline(903);
      PyObject var10000 = var1.getglobal("codecs").__getattr__("IncrementalDecoder").__getattr__("__init__");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getlocal(3)};
      String[] var4 = new String[]{"errors"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(904);
      PyObject var5 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("translate", var5);
      var3 = null;
      var1.setline(905);
      var5 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("decoder", var5);
      var3 = null;
      var1.setline(906);
      PyInteger var6 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"seennl", var6);
      var3 = null;
      var1.setline(907);
      var5 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("pendingcr", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject decode$94(PyFrame var1, ThreadState var2) {
      var1.setline(911);
      PyObject var3 = var1.getlocal(0).__getattr__("decoder");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(912);
         var3 = var1.getlocal(1);
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(914);
         var10000 = var1.getlocal(0).__getattr__("decoder").__getattr__("decode");
         PyObject[] var6 = new PyObject[]{var1.getlocal(1), var1.getlocal(2)};
         String[] var4 = new String[]{"final"};
         var10000 = var10000.__call__(var2, var6, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(915);
      var10000 = var1.getlocal(0).__getattr__("pendingcr");
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(3);
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(2);
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(916);
         var3 = PyUnicode.fromInterned("\r")._add(var1.getlocal(3));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(917);
         var3 = var1.getglobal("False");
         var1.getlocal(0).__setattr__("pendingcr", var3);
         var3 = null;
      }

      var1.setline(921);
      var10000 = var1.getlocal(3).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\r"));
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(2).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(922);
         var3 = var1.getlocal(3).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(923);
         var3 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("pendingcr", var3);
         var3 = null;
      }

      var1.setline(926);
      var3 = var1.getlocal(3).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\r\n"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(927);
      var3 = var1.getlocal(3).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\r"))._sub(var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(928);
      var3 = var1.getlocal(3).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\n"))._sub(var1.getlocal(4));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(929);
      var10000 = var1.getlocal(0);
      String var8 = "seennl";
      PyObject var7 = var10000;
      PyObject var5 = var7.__getattr__(var8);
      var10000 = var1.getlocal(6);
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("_LF");
      }

      PyObject var10001 = var1.getlocal(5);
      if (var10001.__nonzero__()) {
         var10001 = var1.getlocal(0).__getattr__("_CR");
      }

      var10000 = var10000._or(var10001);
      var10001 = var1.getlocal(4);
      if (var10001.__nonzero__()) {
         var10001 = var1.getlocal(0).__getattr__("_CRLF");
      }

      var5 = var5._ior(var10000._or(var10001));
      var7.__setattr__(var8, var5);
      var1.setline(932);
      if (var1.getlocal(0).__getattr__("translate").__nonzero__()) {
         var1.setline(933);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(934);
            var3 = var1.getlocal(3).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\r\n"), (PyObject)PyUnicode.fromInterned("\n"));
            var1.setlocal(3, var3);
            var3 = null;
         }

         var1.setline(935);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(936);
            var3 = var1.getlocal(3).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\r"), (PyObject)PyUnicode.fromInterned("\n"));
            var1.setlocal(3, var3);
            var3 = null;
         }
      }

      var1.setline(938);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getstate$95(PyFrame var1, ThreadState var2) {
      var1.setline(941);
      PyObject var3 = var1.getlocal(0).__getattr__("decoder");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(942);
         PyString var6 = PyString.fromInterned("");
         var1.setlocal(1, var6);
         var3 = null;
         var1.setline(943);
         PyInteger var7 = Py.newInteger(0);
         var1.setlocal(2, var7);
         var3 = null;
      } else {
         var1.setline(945);
         var3 = var1.getlocal(0).__getattr__("decoder").__getattr__("getstate").__call__(var2);
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(2, var5);
         var5 = null;
         var3 = null;
      }

      var1.setline(946);
      var3 = var1.getlocal(2);
      var3 = var3._ilshift(Py.newInteger(1));
      var1.setlocal(2, var3);
      var1.setline(947);
      if (var1.getlocal(0).__getattr__("pendingcr").__nonzero__()) {
         var1.setline(948);
         var3 = var1.getlocal(2);
         var3 = var3._ior(Py.newInteger(1));
         var1.setlocal(2, var3);
      }

      var1.setline(949);
      PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
      var1.f_lasti = -1;
      return var8;
   }

   public PyObject setstate$96(PyFrame var1, ThreadState var2) {
      var1.setline(952);
      PyObject var3 = var1.getlocal(1);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(953);
      var3 = var1.getglobal("bool").__call__(var2, var1.getlocal(3)._and(Py.newInteger(1)));
      var1.getlocal(0).__setattr__("pendingcr", var3);
      var3 = null;
      var1.setline(954);
      var3 = var1.getlocal(0).__getattr__("decoder");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(955);
         var1.getlocal(0).__getattr__("decoder").__getattr__("setstate").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)._rshift(Py.newInteger(1))})));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject reset$97(PyFrame var1, ThreadState var2) {
      var1.setline(958);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"seennl", var3);
      var3 = null;
      var1.setline(959);
      PyObject var4 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("pendingcr", var4);
      var3 = null;
      var1.setline(960);
      var4 = var1.getlocal(0).__getattr__("decoder");
      PyObject var10000 = var4._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(961);
         var1.getlocal(0).__getattr__("decoder").__getattr__("reset").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject newlines$98(PyFrame var1, ThreadState var2) {
      var1.setline(969);
      PyObject var3 = (new PyTuple(new PyObject[]{var1.getglobal("None"), PyUnicode.fromInterned("\n"), PyUnicode.fromInterned("\r"), new PyTuple(new PyObject[]{PyUnicode.fromInterned("\r"), PyUnicode.fromInterned("\n")}), PyUnicode.fromInterned("\r\n"), new PyTuple(new PyObject[]{PyUnicode.fromInterned("\n"), PyUnicode.fromInterned("\r\n")}), new PyTuple(new PyObject[]{PyUnicode.fromInterned("\r"), PyUnicode.fromInterned("\r\n")}), new PyTuple(new PyObject[]{PyUnicode.fromInterned("\r"), PyUnicode.fromInterned("\n"), PyUnicode.fromInterned("\r\n")})})).__getitem__(var1.getlocal(0).__getattr__("seennl"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _check_decoded_chars$99(PyFrame var1, ThreadState var2) {
      var1.setline(981);
      PyUnicode.fromInterned("Check decoder output is unicode");
      var1.setline(982);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("unicode")).__not__().__nonzero__()) {
         var1.setline(983);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyUnicode.fromInterned("decoder should return a string result, not '%s'")._mod(var1.getglobal("type").__call__(var2, var1.getlocal(0)))));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _check_buffered_bytes$100(PyFrame var1, ThreadState var2) {
      var1.setline(987);
      PyUnicode.fromInterned("Check buffer has returned bytes");
      var1.setline(988);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("str")).__not__().__nonzero__()) {
         var1.setline(989);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyUnicode.fromInterned("underlying %s() should have returned a bytes object, not '%s'")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("type").__call__(var2, var1.getlocal(0))}))));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject TextIOWrapper$101(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyUnicode.fromInterned("Character and line based layer over a _BufferedIOBase object, buffer.\n\n    encoding gives the name of the encoding that the stream will be\n    decoded or encoded with. It defaults to locale.getpreferredencoding.\n\n    errors determines the strictness of encoding and decoding (see the\n    codecs.register) and defaults to \"strict\".\n\n    newline can be None, '', '\\n', '\\r', or '\\r\\n'.  It controls the\n    handling of line endings. If it is None, universal newlines is\n    enabled.  With this enabled, on input, the lines endings '\\n', '\\r',\n    or '\\r\\n' are translated to '\\n' before being returned to the\n    caller. Conversely, on output, '\\n' is translated to the system\n    default line separator, os.linesep. If newline is any other of its\n    legal values, that newline becomes the newline when the file is read\n    and it is returned untranslated. On output, '\\n' is converted to the\n    newline.\n\n    If line_buffering is True, a call to flush is implied when a call to\n    write contains a newline character.\n    "));
      var1.setline(1016);
      PyUnicode.fromInterned("Character and line based layer over a _BufferedIOBase object, buffer.\n\n    encoding gives the name of the encoding that the stream will be\n    decoded or encoded with. It defaults to locale.getpreferredencoding.\n\n    errors determines the strictness of encoding and decoding (see the\n    codecs.register) and defaults to \"strict\".\n\n    newline can be None, '', '\\n', '\\r', or '\\r\\n'.  It controls the\n    handling of line endings. If it is None, universal newlines is\n    enabled.  With this enabled, on input, the lines endings '\\n', '\\r',\n    or '\\r\\n' are translated to '\\n' before being returned to the\n    caller. Conversely, on output, '\\n' is translated to the system\n    default line separator, os.linesep. If newline is any other of its\n    legal values, that newline becomes the newline when the file is read\n    and it is returned untranslated. On output, '\\n' is converted to the\n    newline.\n\n    If line_buffering is True, a call to flush is implied when a call to\n    write contains a newline character.\n    ");
      var1.setline(1018);
      PyInteger var3 = Py.newInteger(2048);
      var1.setlocal("_CHUNK_SIZE", var3);
      var3 = null;
      var1.setline(1020);
      PyObject[] var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("False")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$102, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(1081);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __repr__$103, (PyObject)null);
      var1.setlocal("__repr__", var5);
      var3 = null;
      var1.setline(1090);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, encoding$104, (PyObject)null);
      PyObject var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("encoding", var6);
      var3 = null;
      var1.setline(1094);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, errors$105, (PyObject)null);
      var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("errors", var6);
      var3 = null;
      var1.setline(1098);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, line_buffering$106, (PyObject)null);
      var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("line_buffering", var6);
      var3 = null;
      var1.setline(1102);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, buffer$107, (PyObject)null);
      var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("buffer", var6);
      var3 = null;
      var1.setline(1106);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, seekable$108, (PyObject)null);
      var1.setlocal("seekable", var5);
      var3 = null;
      var1.setline(1111);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, readable$109, (PyObject)null);
      var1.setlocal("readable", var5);
      var3 = null;
      var1.setline(1115);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, writable$110, (PyObject)null);
      var1.setlocal("writable", var5);
      var3 = null;
      var1.setline(1119);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, flush$111, (PyObject)null);
      var1.setlocal("flush", var5);
      var3 = null;
      var1.setline(1124);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, close$112, (PyObject)null);
      var1.setlocal("close", var5);
      var3 = null;
      var1.setline(1133);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, closed$113, (PyObject)null);
      var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("closed", var6);
      var3 = null;
      var1.setline(1139);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _checkInitialized$114, (PyObject)null);
      var1.setlocal("_checkInitialized", var5);
      var3 = null;
      var1.setline(1146);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, name$115, (PyObject)null);
      var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("name", var6);
      var3 = null;
      var1.setline(1150);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, fileno$116, (PyObject)null);
      var1.setlocal("fileno", var5);
      var3 = null;
      var1.setline(1153);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, isatty$117, (PyObject)null);
      var1.setlocal("isatty", var5);
      var3 = null;
      var1.setline(1156);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, write$118, (PyObject)null);
      var1.setlocal("write", var5);
      var3 = null;
      var1.setline(1178);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_encoder$119, (PyObject)null);
      var1.setlocal("_get_encoder", var5);
      var3 = null;
      var1.setline(1183);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_decoder$120, (PyObject)null);
      var1.setlocal("_get_decoder", var5);
      var3 = null;
      var1.setline(1194);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _set_decoded_chars$121, PyUnicode.fromInterned("Set the _decoded_chars buffer."));
      var1.setlocal("_set_decoded_chars", var5);
      var3 = null;
      var1.setline(1199);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, _get_decoded_chars$122, PyUnicode.fromInterned("Advance into the _decoded_chars buffer."));
      var1.setlocal("_get_decoded_chars", var5);
      var3 = null;
      var1.setline(1209);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _rewind_decoded_chars$123, PyUnicode.fromInterned("Rewind the _decoded_chars buffer."));
      var1.setlocal("_rewind_decoded_chars", var5);
      var3 = null;
      var1.setline(1215);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _read_chunk$124, PyUnicode.fromInterned("\n        Read and decode the next chunk of data from the BufferedReader.\n        "));
      var1.setlocal("_read_chunk", var5);
      var3 = null;
      var1.setline(1253);
      var4 = new PyObject[]{Py.newInteger(0), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0)};
      var5 = new PyFunction(var1.f_globals, var4, _pack_cookie$125, (PyObject)null);
      var1.setlocal("_pack_cookie", var5);
      var3 = null;
      var1.setline(1263);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _unpack_cookie$126, (PyObject)null);
      var1.setlocal("_unpack_cookie", var5);
      var3 = null;
      var1.setline(1270);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, tell$127, (PyObject)null);
      var1.setlocal("tell", var5);
      var3 = null;
      var1.setline(1332);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, truncate$128, (PyObject)null);
      var1.setlocal("truncate", var5);
      var3 = null;
      var1.setline(1338);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, detach$129, (PyObject)null);
      var1.setlocal("detach", var5);
      var3 = null;
      var1.setline(1347);
      var4 = new PyObject[]{Py.newInteger(0)};
      var5 = new PyFunction(var1.f_globals, var4, seek$130, (PyObject)null);
      var1.setlocal("seek", var5);
      var3 = null;
      var1.setline(1422);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, read$131, (PyObject)null);
      var1.setlocal("read", var5);
      var3 = null;
      var1.setline(1451);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, next$132, (PyObject)null);
      var1.setlocal("next", var5);
      var3 = null;
      var1.setline(1460);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, readline$133, (PyObject)null);
      var1.setlocal("readline", var5);
      var3 = null;
      var1.setline(1548);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, newlines$134, (PyObject)null);
      var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("newlines", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$102(PyFrame var1, ThreadState var2) {
      var1.setline(1022);
      PyObject var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("_ok", var3);
      var3 = null;
      var1.setline(1023);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("basestring")).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(1024);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyUnicode.fromInterned("illegal newline type: %r")._mod(new PyTuple(new PyObject[]{var1.getglobal("type").__call__(var2, var1.getlocal(4))}))));
      } else {
         var1.setline(1025);
         var3 = var1.getlocal(4);
         var10000 = var3._notin(new PyTuple(new PyObject[]{var1.getglobal("None"), PyUnicode.fromInterned(""), PyUnicode.fromInterned("\n"), PyUnicode.fromInterned("\r"), PyUnicode.fromInterned("\r\n")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1026);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyUnicode.fromInterned("illegal newline value: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(4)}))));
         } else {
            var1.setline(1027);
            var3 = var1.getlocal(2);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            PyException var8;
            if (var10000.__nonzero__()) {
               label80: {
                  PyUnicode var4;
                  try {
                     var1.setline(1029);
                     var3 = imp.importOne("locale", var1, -1);
                     var1.setlocal(6, var3);
                     var3 = null;
                  } catch (Throwable var6) {
                     var8 = Py.setException(var6, var1);
                     if (var8.match(var1.getglobal("ImportError"))) {
                        var1.setline(1032);
                        var4 = PyUnicode.fromInterned("ascii");
                        var1.setlocal(2, var4);
                        var4 = null;
                        break label80;
                     }

                     throw var8;
                  }

                  var1.setline(1034);
                  PyObject var7 = var1.getlocal(6).__getattr__("getpreferredencoding").__call__(var2);
                  var1.setlocal(2, var7);
                  var4 = null;
               }
            }

            var1.setline(1036);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("basestring")).__not__().__nonzero__()) {
               var1.setline(1037);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyUnicode.fromInterned("invalid encoding: %r")._mod(var1.getlocal(2))));
            } else {
               var1.setline(1039);
               var3 = var1.getlocal(3);
               var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
               PyUnicode var9;
               if (var10000.__nonzero__()) {
                  var1.setline(1040);
                  var9 = PyUnicode.fromInterned("strict");
                  var1.setlocal(3, var9);
                  var3 = null;
               } else {
                  var1.setline(1042);
                  if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("basestring")).__not__().__nonzero__()) {
                     var1.setline(1043);
                     throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyUnicode.fromInterned("invalid errors: %r")._mod(var1.getlocal(3))));
                  }
               }

               var1.setline(1045);
               var3 = var1.getlocal(1);
               var1.getlocal(0).__setattr__("_buffer", var3);
               var3 = null;
               var1.setline(1046);
               var3 = var1.getlocal(5);
               var1.getlocal(0).__setattr__("_line_buffering", var3);
               var3 = null;
               var1.setline(1047);
               var3 = var1.getlocal(2);
               var1.getlocal(0).__setattr__("_encoding", var3);
               var3 = null;
               var1.setline(1048);
               var3 = var1.getlocal(3);
               var1.getlocal(0).__setattr__("_errors", var3);
               var3 = null;
               var1.setline(1049);
               var3 = var1.getlocal(4).__not__();
               var1.getlocal(0).__setattr__("_readuniversal", var3);
               var3 = null;
               var1.setline(1050);
               var3 = var1.getlocal(4);
               var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
               var3 = var10000;
               var1.getlocal(0).__setattr__("_readtranslate", var3);
               var3 = null;
               var1.setline(1051);
               var3 = var1.getlocal(4);
               var1.getlocal(0).__setattr__("_readnl", var3);
               var3 = null;
               var1.setline(1052);
               var3 = var1.getlocal(4);
               var10000 = var3._ne(PyUnicode.fromInterned(""));
               var3 = null;
               var3 = var10000;
               var1.getlocal(0).__setattr__("_writetranslate", var3);
               var3 = null;
               var1.setline(1053);
               var10000 = var1.getlocal(4);
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getglobal("os").__getattr__("linesep");
               }

               var3 = var10000;
               var1.getlocal(0).__setattr__("_writenl", var3);
               var3 = null;
               var1.setline(1054);
               var3 = var1.getglobal("None");
               var1.getlocal(0).__setattr__("_encoder", var3);
               var3 = null;
               var1.setline(1055);
               var3 = var1.getglobal("None");
               var1.getlocal(0).__setattr__("_decoder", var3);
               var3 = null;
               var1.setline(1056);
               var9 = PyUnicode.fromInterned("");
               var1.getlocal(0).__setattr__((String)"_decoded_chars", var9);
               var3 = null;
               var1.setline(1057);
               PyInteger var10 = Py.newInteger(0);
               var1.getlocal(0).__setattr__((String)"_decoded_chars_used", var10);
               var3 = null;
               var1.setline(1058);
               var3 = var1.getglobal("None");
               var1.getlocal(0).__setattr__("_snapshot", var3);
               var3 = null;
               var1.setline(1059);
               var3 = var1.getlocal(0).__getattr__("buffer").__getattr__("seekable").__call__(var2);
               var1.getlocal(0).__setattr__("_seekable", var3);
               var1.getlocal(0).__setattr__("_telling", var3);
               var1.setline(1061);
               var3 = var1.getglobal("True");
               var1.getlocal(0).__setattr__("_ok", var3);
               var3 = null;
               var1.setline(1063);
               var10000 = var1.getlocal(0).__getattr__("_seekable");
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(0).__getattr__("writable").__call__(var2);
               }

               if (var10000.__nonzero__()) {
                  var1.setline(1064);
                  var3 = var1.getlocal(0).__getattr__("buffer").__getattr__("tell").__call__(var2);
                  var1.setlocal(7, var3);
                  var3 = null;
                  var1.setline(1065);
                  var3 = var1.getlocal(7);
                  var10000 = var3._ne(Py.newInteger(0));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     try {
                        var1.setline(1067);
                        var1.getlocal(0).__getattr__("_get_encoder").__call__(var2).__getattr__("setstate").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                     } catch (Throwable var5) {
                        var8 = Py.setException(var5, var1);
                        if (!var8.match(var1.getglobal("LookupError"))) {
                           throw var8;
                        }

                        var1.setline(1070);
                     }
                  }
               }

               var1.f_lasti = -1;
               return Py.None;
            }
         }
      }
   }

   public PyObject __repr__$103(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var4;
      try {
         var1.setline(1083);
         PyObject var6 = var1.getlocal(0).__getattr__("name");
         var1.setlocal(1, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("AttributeError"))) {
            var1.setline(1085);
            var4 = PyUnicode.fromInterned("<_io.TextIOWrapper encoding='{0}'>").__getattr__("format").__call__(var2, var1.getlocal(0).__getattr__("encoding"));
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(1087);
      var4 = PyUnicode.fromInterned("<_io.TextIOWrapper name={0!r} encoding='{1}'>").__getattr__("format").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("encoding"));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject encoding$104(PyFrame var1, ThreadState var2) {
      var1.setline(1092);
      PyObject var3 = var1.getlocal(0).__getattr__("_encoding");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject errors$105(PyFrame var1, ThreadState var2) {
      var1.setline(1096);
      PyObject var3 = var1.getlocal(0).__getattr__("_errors");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject line_buffering$106(PyFrame var1, ThreadState var2) {
      var1.setline(1100);
      PyObject var3 = var1.getlocal(0).__getattr__("_line_buffering");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject buffer$107(PyFrame var1, ThreadState var2) {
      var1.setline(1104);
      PyObject var3 = var1.getlocal(0).__getattr__("_buffer");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject seekable$108(PyFrame var1, ThreadState var2) {
      var1.setline(1107);
      var1.getlocal(0).__getattr__("_checkInitialized").__call__(var2);
      var1.setline(1108);
      var1.getlocal(0).__getattr__("_checkClosed").__call__(var2);
      var1.setline(1109);
      PyObject var3 = var1.getlocal(0).__getattr__("_seekable");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject readable$109(PyFrame var1, ThreadState var2) {
      var1.setline(1112);
      var1.getlocal(0).__getattr__("_checkInitialized").__call__(var2);
      var1.setline(1113);
      PyObject var3 = var1.getlocal(0).__getattr__("buffer").__getattr__("readable").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject writable$110(PyFrame var1, ThreadState var2) {
      var1.setline(1116);
      var1.getlocal(0).__getattr__("_checkInitialized").__call__(var2);
      var1.setline(1117);
      PyObject var3 = var1.getlocal(0).__getattr__("buffer").__getattr__("writable").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject flush$111(PyFrame var1, ThreadState var2) {
      var1.setline(1120);
      var1.getlocal(0).__getattr__("_checkInitialized").__call__(var2);
      var1.setline(1121);
      var1.getlocal(0).__getattr__("buffer").__getattr__("flush").__call__(var2);
      var1.setline(1122);
      PyObject var3 = var1.getlocal(0).__getattr__("_seekable");
      var1.getlocal(0).__setattr__("_telling", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$112(PyFrame var1, ThreadState var2) {
      var1.setline(1125);
      PyObject var3 = var1.getlocal(0).__getattr__("buffer");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("closed").__not__();
      }

      if (var10000.__nonzero__()) {
         var3 = null;

         try {
            var1.setline(1129);
            var1.getglobal("super").__call__(var2, var1.getglobal("TextIOWrapper"), var1.getlocal(0)).__getattr__("close").__call__(var2);
         } catch (Throwable var4) {
            Py.addTraceback(var4, var1);
            var1.setline(1131);
            var1.getlocal(0).__getattr__("buffer").__getattr__("close").__call__(var2);
            throw (Throwable)var4;
         }

         var1.setline(1131);
         var1.getlocal(0).__getattr__("buffer").__getattr__("close").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject closed$113(PyFrame var1, ThreadState var2) {
      var1.setline(1135);
      PyObject var3 = var1.getlocal(0).__getattr__("buffer").__getattr__("closed");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _checkInitialized$114(PyFrame var1, ThreadState var2) {
      var1.setline(1140);
      if (var1.getlocal(0).__getattr__("_ok").__not__().__nonzero__()) {
         var1.setline(1141);
         PyObject var3 = var1.getlocal(0).__getattr__("buffer");
         PyObject var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1142);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("underlying buffer has been detached")));
         } else {
            var1.setline(1144);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("I/O operation on uninitialized object")));
         }
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject name$115(PyFrame var1, ThreadState var2) {
      var1.setline(1148);
      PyObject var3 = var1.getlocal(0).__getattr__("buffer").__getattr__("name");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject fileno$116(PyFrame var1, ThreadState var2) {
      var1.setline(1151);
      PyObject var3 = var1.getlocal(0).__getattr__("buffer").__getattr__("fileno").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isatty$117(PyFrame var1, ThreadState var2) {
      var1.setline(1154);
      PyObject var3 = var1.getlocal(0).__getattr__("buffer").__getattr__("isatty").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject write$118(PyFrame var1, ThreadState var2) {
      var1.setline(1157);
      var1.getlocal(0).__getattr__("_checkWritable").__call__(var2);
      var1.setline(1158);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(1159);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("write to closed file")));
      } else {
         var1.setline(1160);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("unicode")).__not__().__nonzero__()) {
            var1.setline(1161);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyUnicode.fromInterned("can't write %s to text stream")._mod(var1.getlocal(1).__getattr__("__class__").__getattr__("__name__"))));
         } else {
            var1.setline(1163);
            PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(1164);
            PyObject var10000 = var1.getlocal(0).__getattr__("_writetranslate");
            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("_line_buffering");
            }

            PyUnicode var4;
            if (var10000.__nonzero__()) {
               var4 = PyUnicode.fromInterned("\n");
               var10000 = var4._in(var1.getlocal(1));
               var3 = null;
            }

            var3 = var10000;
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(1165);
            var10000 = var1.getlocal(3);
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("_writetranslate");
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(0).__getattr__("_writenl");
                  var10000 = var3._ne(PyUnicode.fromInterned("\n"));
                  var3 = null;
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(1166);
               var3 = var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\n"), (PyObject)var1.getlocal(0).__getattr__("_writenl"));
               var1.setlocal(1, var3);
               var3 = null;
            }

            var1.setline(1167);
            var10000 = var1.getlocal(0).__getattr__("_encoder");
            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("_get_encoder").__call__(var2);
            }

            var3 = var10000;
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(1169);
            var3 = var1.getlocal(4).__getattr__("encode").__call__(var2, var1.getlocal(1));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(1170);
            var1.getlocal(0).__getattr__("buffer").__getattr__("write").__call__(var2, var1.getlocal(5));
            var1.setline(1171);
            var10000 = var1.getlocal(0).__getattr__("_line_buffering");
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(3);
               if (!var10000.__nonzero__()) {
                  var4 = PyUnicode.fromInterned("\r");
                  var10000 = var4._in(var1.getlocal(1));
                  var3 = null;
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(1172);
               var1.getlocal(0).__getattr__("flush").__call__(var2);
            }

            var1.setline(1173);
            var3 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("_snapshot", var3);
            var3 = null;
            var1.setline(1174);
            if (var1.getlocal(0).__getattr__("_decoder").__nonzero__()) {
               var1.setline(1175);
               var1.getlocal(0).__getattr__("_decoder").__getattr__("reset").__call__(var2);
            }

            var1.setline(1176);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _get_encoder$119(PyFrame var1, ThreadState var2) {
      var1.setline(1179);
      PyObject var3 = var1.getglobal("codecs").__getattr__("getincrementalencoder").__call__(var2, var1.getlocal(0).__getattr__("_encoding"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1180);
      var3 = var1.getlocal(1).__call__(var2, var1.getlocal(0).__getattr__("_errors"));
      var1.getlocal(0).__setattr__("_encoder", var3);
      var3 = null;
      var1.setline(1181);
      var3 = var1.getlocal(0).__getattr__("_encoder");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_decoder$120(PyFrame var1, ThreadState var2) {
      var1.setline(1184);
      PyObject var3 = var1.getglobal("codecs").__getattr__("getincrementaldecoder").__call__(var2, var1.getlocal(0).__getattr__("_encoding"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1185);
      var3 = var1.getlocal(1).__call__(var2, var1.getlocal(0).__getattr__("_errors"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1186);
      if (var1.getlocal(0).__getattr__("_readuniversal").__nonzero__()) {
         var1.setline(1187);
         var3 = var1.getglobal("IncrementalNewlineDecoder").__call__(var2, var1.getlocal(2), var1.getlocal(0).__getattr__("_readtranslate"));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1188);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_decoder", var3);
      var3 = null;
      var1.setline(1189);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _set_decoded_chars$121(PyFrame var1, ThreadState var2) {
      var1.setline(1195);
      PyUnicode.fromInterned("Set the _decoded_chars buffer.");
      var1.setline(1196);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_decoded_chars", var3);
      var3 = null;
      var1.setline(1197);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_decoded_chars_used", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_decoded_chars$122(PyFrame var1, ThreadState var2) {
      var1.setline(1200);
      PyUnicode.fromInterned("Advance into the _decoded_chars buffer.");
      var1.setline(1201);
      PyObject var3 = var1.getlocal(0).__getattr__("_decoded_chars_used");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1202);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1203);
         var3 = var1.getlocal(0).__getattr__("_decoded_chars").__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null);
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(1205);
         var3 = var1.getlocal(0).__getattr__("_decoded_chars").__getslice__(var1.getlocal(2), var1.getlocal(2)._add(var1.getlocal(1)), (PyObject)null);
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(1206);
      var10000 = var1.getlocal(0);
      String var6 = "_decoded_chars_used";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var6);
      var5 = var5._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(3)));
      var4.__setattr__(var6, var5);
      var1.setline(1207);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _rewind_decoded_chars$123(PyFrame var1, ThreadState var2) {
      var1.setline(1210);
      PyUnicode.fromInterned("Rewind the _decoded_chars buffer.");
      var1.setline(1211);
      PyObject var3 = var1.getlocal(0).__getattr__("_decoded_chars_used");
      PyObject var10000 = var3._lt(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1212);
         throw Py.makeException(var1.getglobal("AssertionError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("rewind decoded_chars out of bounds")));
      } else {
         var1.setline(1213);
         var10000 = var1.getlocal(0);
         String var6 = "_decoded_chars_used";
         PyObject var4 = var10000;
         PyObject var5 = var4.__getattr__(var6);
         var5 = var5._isub(var1.getlocal(1));
         var4.__setattr__(var6, var5);
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _read_chunk$124(PyFrame var1, ThreadState var2) {
      var1.setline(1218);
      PyUnicode.fromInterned("\n        Read and decode the next chunk of data from the BufferedReader.\n        ");
      var1.setline(1226);
      PyObject var3 = var1.getlocal(0).__getattr__("_decoder");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1227);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("no decoder")));
      } else {
         var1.setline(1229);
         if (var1.getlocal(0).__getattr__("_telling").__nonzero__()) {
            var1.setline(1233);
            var3 = var1.getlocal(0).__getattr__("_decoder").__getattr__("getstate").__call__(var2);
            PyObject[] var4 = Py.unpackSequence(var3, 2);
            PyObject var5 = var4[0];
            var1.setlocal(1, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(2, var5);
            var5 = null;
            var3 = null;
         }

         var1.setline(1238);
         var3 = var1.getlocal(0).__getattr__("buffer").__getattr__("read1").__call__(var2, var1.getlocal(0).__getattr__("_CHUNK_SIZE"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1239);
         var1.getglobal("_check_buffered_bytes").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyUnicode.fromInterned("read1"));
         var1.setline(1241);
         var3 = var1.getlocal(3).__not__();
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1242);
         var3 = var1.getlocal(0).__getattr__("_decoder").__getattr__("decode").__call__(var2, var1.getlocal(3), var1.getlocal(4));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(1243);
         var1.getglobal("_check_decoded_chars").__call__(var2, var1.getlocal(5));
         var1.setline(1244);
         var1.getlocal(0).__getattr__("_set_decoded_chars").__call__(var2, var1.getlocal(5));
         var1.setline(1246);
         if (var1.getlocal(0).__getattr__("_telling").__nonzero__()) {
            var1.setline(1249);
            PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(1)._add(var1.getlocal(3))});
            var1.getlocal(0).__setattr__((String)"_snapshot", var6);
            var3 = null;
         }

         var1.setline(1251);
         var3 = var1.getlocal(4).__not__();
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _pack_cookie$125(PyFrame var1, ThreadState var2) {
      var1.setline(1260);
      PyObject var3 = var1.getlocal(1)._or(var1.getlocal(2)._lshift(Py.newInteger(64)))._or(var1.getlocal(3)._lshift(Py.newInteger(128)))._or(var1.getlocal(5)._lshift(Py.newInteger(192)))._or(var1.getglobal("bool").__call__(var2, var1.getlocal(4))._lshift(Py.newInteger(256)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _unpack_cookie$126(PyFrame var1, ThreadState var2) {
      var1.setline(1264);
      PyObject var3 = var1.getglobal("divmod").__call__(var2, var1.getlocal(1), Py.newInteger(1)._lshift(Py.newInteger(64)));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(1265);
      var3 = var1.getglobal("divmod").__call__(var2, var1.getlocal(2), Py.newInteger(1)._lshift(Py.newInteger(64)));
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(1266);
      var3 = var1.getglobal("divmod").__call__(var2, var1.getlocal(2), Py.newInteger(1)._lshift(Py.newInteger(64)));
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(1267);
      var3 = var1.getglobal("divmod").__call__(var2, var1.getlocal(2), Py.newInteger(1)._lshift(Py.newInteger(64)));
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(7, var5);
      var5 = null;
      var3 = null;
      var1.setline(1268);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject tell$127(PyFrame var1, ThreadState var2) {
      var1.setline(1271);
      if (var1.getlocal(0).__getattr__("_seekable").__not__().__nonzero__()) {
         var1.setline(1272);
         throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("underlying stream is not seekable")));
      } else {
         var1.setline(1273);
         if (var1.getlocal(0).__getattr__("_telling").__not__().__nonzero__()) {
            var1.setline(1274);
            throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("telling position disabled by next() call")));
         } else {
            var1.setline(1275);
            var1.getlocal(0).__getattr__("flush").__call__(var2);
            var1.setline(1276);
            PyObject var3 = var1.getlocal(0).__getattr__("buffer").__getattr__("tell").__call__(var2);
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(1277);
            var3 = var1.getlocal(0).__getattr__("_decoder");
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(1278);
            var3 = var1.getlocal(2);
            PyObject var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var3 = var1.getlocal(0).__getattr__("_snapshot");
               var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(1279);
               if (var1.getlocal(0).__getattr__("_decoded_chars").__nonzero__()) {
                  var1.setline(1281);
                  throw Py.makeException(var1.getglobal("AssertionError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("pending decoded text")));
               } else {
                  var1.setline(1282);
                  var3 = var1.getlocal(1);
                  var1.f_lasti = -1;
                  return var3;
               }
            } else {
               var1.setline(1285);
               PyObject var4 = var1.getlocal(0).__getattr__("_snapshot");
               PyObject[] var5 = Py.unpackSequence(var4, 2);
               PyObject var6 = var5[0];
               var1.setlocal(3, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(4, var6);
               var6 = null;
               var4 = null;
               var1.setline(1286);
               var4 = var1.getlocal(1);
               var4 = var4._isub(var1.getglobal("len").__call__(var2, var1.getlocal(4)));
               var1.setlocal(1, var4);
               var1.setline(1289);
               var4 = var1.getlocal(0).__getattr__("_decoded_chars_used");
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(1290);
               var4 = var1.getlocal(5);
               var10000 = var4._eq(Py.newInteger(0));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1292);
                  var3 = var1.getlocal(0).__getattr__("_pack_cookie").__call__(var2, var1.getlocal(1), var1.getlocal(3));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(1296);
                  var4 = var1.getlocal(2).__getattr__("getstate").__call__(var2);
                  var1.setlocal(6, var4);
                  var4 = null;
                  var4 = null;

                  Throwable var23;
                  label83: {
                     PyObject var7;
                     PyObject var15;
                     boolean var10001;
                     try {
                        var1.setline(1299);
                        var1.getlocal(2).__getattr__("setstate").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned(""), var1.getlocal(3)})));
                        var1.setline(1300);
                        var15 = var1.getlocal(1);
                        var1.setlocal(7, var15);
                        var5 = null;
                        var1.setline(1301);
                        PyTuple var16 = new PyTuple(new PyObject[]{var1.getlocal(3), Py.newInteger(0), Py.newInteger(0)});
                        PyObject[] var18 = Py.unpackSequence(var16, 3);
                        var7 = var18[0];
                        var1.setlocal(8, var7);
                        var7 = null;
                        var7 = var18[1];
                        var1.setlocal(9, var7);
                        var7 = null;
                        var7 = var18[2];
                        var1.setlocal(10, var7);
                        var7 = null;
                        var5 = null;
                        var1.setline(1302);
                        PyInteger var17 = Py.newInteger(0);
                        var1.setlocal(11, var17);
                        var5 = null;
                        var1.setline(1308);
                        var15 = var1.getlocal(4).__iter__();
                     } catch (Throwable var12) {
                        var23 = var12;
                        var10001 = false;
                        break label83;
                     }

                     while(true) {
                        PyObject[] var8;
                        try {
                           var1.setline(1308);
                           var6 = var15.__iternext__();
                           if (var6 == null) {
                              var1.setline(1321);
                              var7 = var1.getlocal(10);
                              var10000 = var1.getglobal("len");
                              PyObject var10002 = var1.getlocal(2).__getattr__("decode");
                              var8 = new PyObject[]{PyString.fromInterned(""), var1.getglobal("True")};
                              String[] var20 = new String[]{"final"};
                              var10002 = var10002.__call__(var2, var8, var20);
                              var8 = null;
                              var7 = var7._iadd(var10000.__call__(var2, var10002));
                              var1.setlocal(10, var7);
                              var1.setline(1322);
                              PyInteger var22 = Py.newInteger(1);
                              var1.setlocal(11, var22);
                              var7 = null;
                              var1.setline(1323);
                              var7 = var1.getlocal(10);
                              var10000 = var7._lt(var1.getlocal(5));
                              var7 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(1324);
                                 throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("can't reconstruct logical file position")));
                              }
                              break;
                           }
                        } catch (Throwable var14) {
                           var23 = var14;
                           var10001 = false;
                           break label83;
                        }

                        try {
                           var1.setlocal(12, var6);
                           var1.setline(1309);
                           var7 = var1.getlocal(9);
                           var7 = var7._iadd(Py.newInteger(1));
                           var1.setlocal(9, var7);
                           var1.setline(1310);
                           var7 = var1.getlocal(10);
                           var7 = var7._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(2).__getattr__("decode").__call__(var2, var1.getlocal(12))));
                           var1.setlocal(10, var7);
                           var1.setline(1311);
                           var7 = var1.getlocal(2).__getattr__("getstate").__call__(var2);
                           var8 = Py.unpackSequence(var7, 2);
                           PyObject var9 = var8[0];
                           var1.setlocal(13, var9);
                           var9 = null;
                           var9 = var8[1];
                           var1.setlocal(3, var9);
                           var9 = null;
                           var7 = null;
                           var1.setline(1312);
                           var10000 = var1.getlocal(13).__not__();
                           if (var10000.__nonzero__()) {
                              var7 = var1.getlocal(10);
                              var10000 = var7._le(var1.getlocal(5));
                              var7 = null;
                           }

                           if (var10000.__nonzero__()) {
                              var1.setline(1314);
                              var7 = var1.getlocal(7);
                              var7 = var7._iadd(var1.getlocal(9));
                              var1.setlocal(7, var7);
                              var1.setline(1315);
                              var7 = var1.getlocal(5);
                              var7 = var7._isub(var1.getlocal(10));
                              var1.setlocal(5, var7);
                              var1.setline(1316);
                              PyTuple var21 = new PyTuple(new PyObject[]{var1.getlocal(3), Py.newInteger(0), Py.newInteger(0)});
                              var8 = Py.unpackSequence(var21, 3);
                              var9 = var8[0];
                              var1.setlocal(8, var9);
                              var9 = null;
                              var9 = var8[1];
                              var1.setlocal(9, var9);
                              var9 = null;
                              var9 = var8[2];
                              var1.setlocal(10, var9);
                              var9 = null;
                              var7 = null;
                           }

                           var1.setline(1317);
                           var7 = var1.getlocal(10);
                           var10000 = var7._ge(var1.getlocal(5));
                           var7 = null;
                           if (var10000.__nonzero__()) {
                              break;
                           }
                        } catch (Throwable var13) {
                           var23 = var13;
                           var10001 = false;
                           break label83;
                        }
                     }

                     try {
                        var1.setline(1327);
                        var10000 = var1.getlocal(0).__getattr__("_pack_cookie");
                        var5 = new PyObject[]{var1.getlocal(7), var1.getlocal(8), var1.getlocal(9), var1.getlocal(11), var1.getlocal(5)};
                        var3 = var10000.__call__(var2, var5);
                     } catch (Throwable var11) {
                        var23 = var11;
                        var10001 = false;
                        break label83;
                     }

                     var1.setline(1330);
                     var1.getlocal(2).__getattr__("setstate").__call__(var2, var1.getlocal(6));

                     try {
                        var1.f_lasti = -1;
                        return var3;
                     } catch (Throwable var10) {
                        var23 = var10;
                        var10001 = false;
                     }
                  }

                  Throwable var19 = var23;
                  Py.addTraceback(var19, var1);
                  var1.setline(1330);
                  var1.getlocal(2).__getattr__("setstate").__call__(var2, var1.getlocal(6));
                  throw (Throwable)var19;
               }
            }
         }
      }
   }

   public PyObject truncate$128(PyFrame var1, ThreadState var2) {
      var1.setline(1333);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(1334);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1335);
         var3 = var1.getlocal(0).__getattr__("tell").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1336);
      var3 = var1.getlocal(0).__getattr__("buffer").__getattr__("truncate").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject detach$129(PyFrame var1, ThreadState var2) {
      var1.setline(1339);
      PyObject var3 = var1.getlocal(0).__getattr__("buffer");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1340);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("buffer is already detached")));
      } else {
         var1.setline(1341);
         var1.getlocal(0).__getattr__("flush").__call__(var2);
         var1.setline(1342);
         var3 = var1.getglobal("False");
         var1.getlocal(0).__setattr__("_ok", var3);
         var3 = null;
         var1.setline(1343);
         var3 = var1.getlocal(0).__getattr__("_buffer");
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(1344);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("_buffer", var3);
         var3 = null;
         var1.setline(1345);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject seek$130(PyFrame var1, ThreadState var2) {
      var1.setline(1348);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(1349);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("tell on closed file")));
      } else {
         var1.setline(1350);
         if (var1.getlocal(0).__getattr__("_seekable").__not__().__nonzero__()) {
            var1.setline(1351);
            throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("underlying stream is not seekable")));
         } else {
            var1.setline(1352);
            PyObject var3 = var1.getlocal(2);
            PyObject var10000 = var3._eq(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1353);
               var3 = var1.getlocal(1);
               var10000 = var3._ne(Py.newInteger(0));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1354);
                  throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("can't do nonzero cur-relative seeks")));
               }

               var1.setline(1357);
               PyInteger var8 = Py.newInteger(0);
               var1.setlocal(2, var8);
               var3 = null;
               var1.setline(1358);
               var3 = var1.getlocal(0).__getattr__("tell").__call__(var2);
               var1.setlocal(1, var3);
               var3 = null;
            }

            var1.setline(1359);
            var3 = var1.getlocal(2);
            var10000 = var3._eq(Py.newInteger(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1360);
               var3 = var1.getlocal(1);
               var10000 = var3._ne(Py.newInteger(0));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1361);
                  throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("can't do nonzero end-relative seeks")));
               } else {
                  var1.setline(1362);
                  var1.getlocal(0).__getattr__("flush").__call__(var2);
                  var1.setline(1363);
                  var3 = var1.getlocal(0).__getattr__("buffer").__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(2));
                  var1.setlocal(3, var3);
                  var3 = null;
                  var1.setline(1364);
                  var1.getlocal(0).__getattr__("_set_decoded_chars").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned(""));
                  var1.setline(1365);
                  var3 = var1.getglobal("None");
                  var1.getlocal(0).__setattr__("_snapshot", var3);
                  var3 = null;
                  var1.setline(1366);
                  if (var1.getlocal(0).__getattr__("_decoder").__nonzero__()) {
                     var1.setline(1367);
                     var1.getlocal(0).__getattr__("_decoder").__getattr__("reset").__call__(var2);
                  }

                  var1.setline(1368);
                  var3 = var1.getlocal(3);
                  var1.f_lasti = -1;
                  return var3;
               }
            } else {
               var1.setline(1369);
               PyObject var4 = var1.getlocal(2);
               var10000 = var4._ne(Py.newInteger(0));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1370);
                  throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyUnicode.fromInterned("invalid whence (%r, should be 0, 1 or 2)")._mod(new PyTuple(new PyObject[]{var1.getlocal(2)}))));
               } else {
                  var1.setline(1372);
                  var4 = var1.getlocal(1);
                  var10000 = var4._lt(Py.newInteger(0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1373);
                     throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyUnicode.fromInterned("negative seek position %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(1)}))));
                  } else {
                     var1.setline(1374);
                     var1.getlocal(0).__getattr__("flush").__call__(var2);
                     var1.setline(1378);
                     var4 = var1.getlocal(0).__getattr__("_unpack_cookie").__call__(var2, var1.getlocal(1));
                     PyObject[] var5 = Py.unpackSequence(var4, 5);
                     PyObject var6 = var5[0];
                     var1.setlocal(4, var6);
                     var6 = null;
                     var6 = var5[1];
                     var1.setlocal(5, var6);
                     var6 = null;
                     var6 = var5[2];
                     var1.setlocal(6, var6);
                     var6 = null;
                     var6 = var5[3];
                     var1.setlocal(7, var6);
                     var6 = null;
                     var6 = var5[4];
                     var1.setlocal(8, var6);
                     var6 = null;
                     var4 = null;
                     var1.setline(1382);
                     var1.getlocal(0).__getattr__("buffer").__getattr__("seek").__call__(var2, var1.getlocal(4));
                     var1.setline(1383);
                     var1.getlocal(0).__getattr__("_set_decoded_chars").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned(""));
                     var1.setline(1384);
                     var4 = var1.getglobal("None");
                     var1.getlocal(0).__setattr__("_snapshot", var4);
                     var4 = null;
                     var1.setline(1387);
                     var4 = var1.getlocal(1);
                     var10000 = var4._eq(Py.newInteger(0));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var10000 = var1.getlocal(0).__getattr__("_decoder");
                     }

                     PyTuple var10;
                     if (var10000.__nonzero__()) {
                        var1.setline(1388);
                        var1.getlocal(0).__getattr__("_decoder").__getattr__("reset").__call__(var2);
                     } else {
                        var1.setline(1389);
                        var10000 = var1.getlocal(0).__getattr__("_decoder");
                        if (!var10000.__nonzero__()) {
                           var10000 = var1.getlocal(5);
                           if (!var10000.__nonzero__()) {
                              var10000 = var1.getlocal(8);
                           }
                        }

                        if (var10000.__nonzero__()) {
                           var1.setline(1390);
                           var10000 = var1.getlocal(0).__getattr__("_decoder");
                           if (!var10000.__nonzero__()) {
                              var10000 = var1.getlocal(0).__getattr__("_get_decoder").__call__(var2);
                           }

                           var4 = var10000;
                           var1.getlocal(0).__setattr__("_decoder", var4);
                           var4 = null;
                           var1.setline(1391);
                           var1.getlocal(0).__getattr__("_decoder").__getattr__("setstate").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned(""), var1.getlocal(5)})));
                           var1.setline(1392);
                           var10 = new PyTuple(new PyObject[]{var1.getlocal(5), PyString.fromInterned("")});
                           var1.getlocal(0).__setattr__((String)"_snapshot", var10);
                           var4 = null;
                        }
                     }

                     var1.setline(1394);
                     if (var1.getlocal(8).__nonzero__()) {
                        var1.setline(1396);
                        var4 = var1.getlocal(0).__getattr__("buffer").__getattr__("read").__call__(var2, var1.getlocal(6));
                        var1.setlocal(9, var4);
                        var4 = null;
                        var1.setline(1397);
                        var1.getglobal("_check_buffered_bytes").__call__(var2, var1.getlocal(9));
                        var1.setline(1398);
                        var4 = var1.getlocal(0).__getattr__("_decoder").__getattr__("decode").__call__(var2, var1.getlocal(9), var1.getlocal(7));
                        var1.setlocal(10, var4);
                        var4 = null;
                        var1.setline(1399);
                        var1.getglobal("_check_decoded_chars").__call__(var2, var1.getlocal(10));
                        var1.setline(1400);
                        var1.getlocal(0).__getattr__("_set_decoded_chars").__call__(var2, var1.getlocal(10));
                        var1.setline(1402);
                        var10 = new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(9)});
                        var1.getlocal(0).__setattr__((String)"_snapshot", var10);
                        var4 = null;
                        var1.setline(1405);
                        var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_decoded_chars"));
                        var10000 = var4._lt(var1.getlocal(8));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(1406);
                           throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("can't restore logical file position")));
                        }

                        var1.setline(1407);
                        var4 = var1.getlocal(8);
                        var1.getlocal(0).__setattr__("_decoded_chars_used", var4);
                        var4 = null;
                     }

                     label80: {
                        try {
                           var1.setline(1411);
                           var10000 = var1.getlocal(0).__getattr__("_encoder");
                           if (!var10000.__nonzero__()) {
                              var10000 = var1.getlocal(0).__getattr__("_get_encoder").__call__(var2);
                           }

                           var4 = var10000;
                           var1.setlocal(11, var4);
                           var4 = null;
                        } catch (Throwable var7) {
                           PyException var11 = Py.setException(var7, var1);
                           if (var11.match(var1.getglobal("LookupError"))) {
                              var1.setline(1414);
                              break label80;
                           }

                           throw var11;
                        }

                        var1.setline(1416);
                        PyObject var9 = var1.getlocal(1);
                        var10000 = var9._ne(Py.newInteger(0));
                        var5 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(1417);
                           var1.getlocal(11).__getattr__("setstate").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                        } else {
                           var1.setline(1419);
                           var1.getlocal(11).__getattr__("reset").__call__(var2);
                        }
                     }

                     var1.setline(1420);
                     var3 = var1.getlocal(1);
                     var1.f_lasti = -1;
                     return var3;
                  }
               }
            }
         }
      }
   }

   public PyObject read$131(PyFrame var1, ThreadState var2) {
      var1.setline(1423);
      var1.getlocal(0).__getattr__("_checkReadable").__call__(var2);
      var1.setline(1424);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1425);
         PyInteger var6 = Py.newInteger(-1);
         var1.setlocal(1, var6);
         var3 = null;
      }

      var1.setline(1426);
      var10000 = var1.getlocal(0).__getattr__("_decoder");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("_get_decoder").__call__(var2);
      }

      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;

      try {
         var1.setline(1428);
         var1.getlocal(1).__getattr__("__index__");
      } catch (Throwable var5) {
         PyException var7 = Py.setException(var5, var1);
         if (var7.match(var1.getglobal("AttributeError"))) {
            var1.setline(1430);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("an integer is required")));
         }

         throw var7;
      }

      var1.setline(1431);
      var3 = var1.getlocal(1);
      var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1433);
         var3 = var1.getlocal(0).__getattr__("buffer").__getattr__("read").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1435);
         var1.getglobal("_check_buffered_bytes").__call__(var2, var1.getlocal(3));
         var1.setline(1436);
         var10000 = var1.getlocal(2).__getattr__("decode");
         PyObject[] var8 = new PyObject[]{var1.getlocal(3), var1.getglobal("True")};
         String[] var9 = new String[]{"final"};
         var10000 = var10000.__call__(var2, var8, var9);
         var3 = null;
         var3 = var10000;
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1437);
         var1.getglobal("_check_decoded_chars").__call__(var2, var1.getlocal(4));
         var1.setline(1438);
         var3 = var1.getlocal(0).__getattr__("_get_decoded_chars").__call__(var2)._add(var1.getlocal(4));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(1439);
         var1.getlocal(0).__getattr__("_set_decoded_chars").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned(""));
         var1.setline(1440);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("_snapshot", var3);
         var3 = null;
         var1.setline(1441);
         var3 = var1.getlocal(5);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1444);
         PyObject var4 = var1.getglobal("False");
         var1.setlocal(6, var4);
         var4 = null;
         var1.setline(1445);
         var4 = var1.getlocal(0).__getattr__("_get_decoded_chars").__call__(var2, var1.getlocal(1));
         var1.setlocal(5, var4);
         var4 = null;

         while(true) {
            var1.setline(1446);
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(5));
            var10000 = var4._lt(var1.getlocal(1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(6).__not__();
            }

            if (!var10000.__nonzero__()) {
               var1.setline(1449);
               var3 = var1.getlocal(5);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(1447);
            var4 = var1.getlocal(0).__getattr__("_read_chunk").__call__(var2).__not__();
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(1448);
            var4 = var1.getlocal(5);
            var4 = var4._iadd(var1.getlocal(0).__getattr__("_get_decoded_chars").__call__(var2, var1.getlocal(1)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(5)))));
            var1.setlocal(5, var4);
         }
      }
   }

   public PyObject next$132(PyFrame var1, ThreadState var2) {
      var1.setline(1452);
      PyObject var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("_telling", var3);
      var3 = null;
      var1.setline(1453);
      var3 = var1.getlocal(0).__getattr__("readline").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1454);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(1455);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("_snapshot", var3);
         var3 = null;
         var1.setline(1456);
         var3 = var1.getlocal(0).__getattr__("_seekable");
         var1.getlocal(0).__setattr__("_telling", var3);
         var3 = null;
         var1.setline(1457);
         throw Py.makeException(var1.getglobal("StopIteration"));
      } else {
         var1.setline(1458);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject readline$133(PyFrame var1, ThreadState var2) {
      var1.setline(1461);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(1462);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("read from closed file")));
      } else {
         var1.setline(1463);
         PyObject var3 = var1.getlocal(1);
         PyObject var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         PyInteger var5;
         if (var10000.__nonzero__()) {
            var1.setline(1464);
            var5 = Py.newInteger(-1);
            var1.setlocal(1, var5);
            var3 = null;
         } else {
            var1.setline(1465);
            if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__not__().__nonzero__()) {
               var1.setline(1466);
               throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("limit must be an integer")));
            }
         }

         var1.setline(1469);
         var3 = var1.getlocal(0).__getattr__("_get_decoded_chars").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1471);
         var5 = Py.newInteger(0);
         var1.setlocal(3, var5);
         var3 = null;
         var1.setline(1473);
         if (var1.getlocal(0).__getattr__("_decoder").__not__().__nonzero__()) {
            var1.setline(1474);
            var1.getlocal(0).__getattr__("_get_decoder").__call__(var2);
         }

         var1.setline(1476);
         var3 = var1.getglobal("None");
         var1.setlocal(4, var3);
         var1.setlocal(5, var3);

         while(true) {
            var1.setline(1477);
            if (!var1.getglobal("True").__nonzero__()) {
               break;
            }

            var1.setline(1478);
            if (var1.getlocal(0).__getattr__("_readtranslate").__nonzero__()) {
               var1.setline(1480);
               var3 = var1.getlocal(2).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\n"), (PyObject)var1.getlocal(3));
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(1481);
               var3 = var1.getlocal(4);
               var10000 = var3._ge(Py.newInteger(0));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1482);
                  var3 = var1.getlocal(4)._add(Py.newInteger(1));
                  var1.setlocal(5, var3);
                  var3 = null;
                  break;
               }

               var1.setline(1485);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
               var1.setlocal(3, var3);
               var3 = null;
            } else {
               var1.setline(1487);
               if (var1.getlocal(0).__getattr__("_readuniversal").__nonzero__()) {
                  var1.setline(1492);
                  var3 = var1.getlocal(2).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\n"), (PyObject)var1.getlocal(3));
                  var1.setlocal(6, var3);
                  var3 = null;
                  var1.setline(1493);
                  var3 = var1.getlocal(2).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\r"), (PyObject)var1.getlocal(3));
                  var1.setlocal(7, var3);
                  var3 = null;
                  var1.setline(1494);
                  var3 = var1.getlocal(7);
                  var10000 = var3._eq(Py.newInteger(-1));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(1502);
                     var3 = var1.getlocal(6);
                     var10000 = var3._eq(Py.newInteger(-1));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(1504);
                        var3 = var1.getlocal(7)._add(Py.newInteger(1));
                        var1.setlocal(5, var3);
                        var3 = null;
                     } else {
                        var1.setline(1506);
                        var3 = var1.getlocal(6);
                        var10000 = var3._lt(var1.getlocal(7));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(1508);
                           var3 = var1.getlocal(6)._add(Py.newInteger(1));
                           var1.setlocal(5, var3);
                           var3 = null;
                        } else {
                           var1.setline(1510);
                           var3 = var1.getlocal(6);
                           var10000 = var3._eq(var1.getlocal(7)._add(Py.newInteger(1)));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(1512);
                              var3 = var1.getlocal(7)._add(Py.newInteger(2));
                              var1.setlocal(5, var3);
                              var3 = null;
                           } else {
                              var1.setline(1516);
                              var3 = var1.getlocal(7)._add(Py.newInteger(1));
                              var1.setlocal(5, var3);
                              var3 = null;
                           }
                        }
                     }
                     break;
                  }

                  var1.setline(1495);
                  var3 = var1.getlocal(6);
                  var10000 = var3._eq(Py.newInteger(-1));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(1500);
                     var3 = var1.getlocal(6)._add(Py.newInteger(1));
                     var1.setlocal(5, var3);
                     var3 = null;
                     break;
                  }

                  var1.setline(1497);
                  var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
                  var1.setlocal(3, var3);
                  var3 = null;
               } else {
                  var1.setline(1520);
                  var3 = var1.getlocal(2).__getattr__("find").__call__(var2, var1.getlocal(0).__getattr__("_readnl"));
                  var1.setlocal(4, var3);
                  var3 = null;
                  var1.setline(1521);
                  var3 = var1.getlocal(4);
                  var10000 = var3._ge(Py.newInteger(0));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1522);
                     var3 = var1.getlocal(4)._add(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_readnl")));
                     var1.setlocal(5, var3);
                     var3 = null;
                     break;
                  }
               }
            }

            var1.setline(1525);
            var3 = var1.getlocal(1);
            var10000 = var3._ge(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
               var10000 = var3._ge(var1.getlocal(1));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(1526);
               var3 = var1.getlocal(1);
               var1.setlocal(5, var3);
               var3 = null;
               break;
            }

            do {
               var1.setline(1530);
               if (!var1.getlocal(0).__getattr__("_read_chunk").__call__(var2).__nonzero__()) {
                  break;
               }

               var1.setline(1531);
            } while(!var1.getlocal(0).__getattr__("_decoded_chars").__nonzero__());

            var1.setline(1533);
            if (!var1.getlocal(0).__getattr__("_decoded_chars").__nonzero__()) {
               var1.setline(1537);
               var1.getlocal(0).__getattr__("_set_decoded_chars").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned(""));
               var1.setline(1538);
               var3 = var1.getglobal("None");
               var1.getlocal(0).__setattr__("_snapshot", var3);
               var3 = null;
               var1.setline(1539);
               var3 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(1534);
            var3 = var1.getlocal(2);
            var3 = var3._iadd(var1.getlocal(0).__getattr__("_get_decoded_chars").__call__(var2));
            var1.setlocal(2, var3);
         }

         var1.setline(1541);
         PyObject var4 = var1.getlocal(1);
         var10000 = var4._ge(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var4 = var1.getlocal(5);
            var10000 = var4._gt(var1.getlocal(1));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1542);
            var4 = var1.getlocal(1);
            var1.setlocal(5, var4);
            var4 = null;
         }

         var1.setline(1545);
         var1.getlocal(0).__getattr__("_rewind_decoded_chars").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(2))._sub(var1.getlocal(5)));
         var1.setline(1546);
         var3 = var1.getlocal(2).__getslice__((PyObject)null, var1.getlocal(5), (PyObject)null);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject newlines$134(PyFrame var1, ThreadState var2) {
      var1.setline(1550);
      var1.setline(1550);
      PyObject var3 = var1.getlocal(0).__getattr__("_decoder").__nonzero__() ? var1.getlocal(0).__getattr__("_decoder").__getattr__("newlines") : var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject StringIO$135(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyUnicode.fromInterned("Text I/O implementation using an in-memory buffer.\n\n    The initial_value argument sets the value of object.  The newline\n    argument is like the one of TextIOWrapper's constructor.\n    "));
      var1.setline(1558);
      PyUnicode.fromInterned("Text I/O implementation using an in-memory buffer.\n\n    The initial_value argument sets the value of object.  The newline\n    argument is like the one of TextIOWrapper's constructor.\n    ");
      var1.setline(1560);
      PyObject[] var3 = new PyObject[]{PyUnicode.fromInterned(""), PyUnicode.fromInterned("\n")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$136, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1584);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getstate__$137, (PyObject)null);
      var1.setlocal("__getstate__", var4);
      var3 = null;
      var1.setline(1591);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __setstate__$138, (PyObject)null);
      var1.setlocal("__setstate__", var4);
      var3 = null;
      var1.setline(1633);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getvalue$139, (PyObject)null);
      var1.setlocal("getvalue", var4);
      var3 = null;
      var1.setline(1637);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$140, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(1642);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, errors$141, (PyObject)null);
      PyObject var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("errors", var5);
      var3 = null;
      var1.setline(1646);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, encoding$142, (PyObject)null);
      var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("encoding", var5);
      var3 = null;
      var1.setline(1650);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, detach$143, (PyObject)null);
      var1.setlocal("detach", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$136(PyFrame var1, ThreadState var2) {
      var1.setline(1563);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("unicode")).__nonzero__()) {
         var1.setline(1564);
         var3 = var1.getlocal(2).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("utf-8"));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1566);
      PyObject var10000 = var1.getglobal("super").__call__(var2, var1.getglobal("StringIO"), var1.getlocal(0)).__getattr__("__init__");
      PyObject[] var5 = new PyObject[]{var1.getglobal("BytesIO").__call__(var2), PyUnicode.fromInterned("utf-8"), PyUnicode.fromInterned("strict"), var1.getlocal(2)};
      String[] var4 = new String[]{"encoding", "errors", "newline"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(1572);
      var3 = var1.getlocal(2);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1573);
         var3 = var1.getglobal("False");
         var1.getlocal(0).__setattr__("_writetranslate", var3);
         var3 = null;
      }

      var1.setline(1575);
      var3 = var1.getlocal(1);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1576);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("unicode")).__not__().__nonzero__()) {
            var1.setline(1577);
            PyUnicode var6 = PyUnicode.fromInterned("initial value should be unicode or None, got %s");
            var1.setlocal(3, var6);
            var3 = null;
            var1.setline(1578);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2, var1.getlocal(3)._mod(var1.getglobal("type").__call__(var2, var1.getlocal(1)))));
         }

         var1.setline(1579);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(1580);
            var1.getlocal(0).__getattr__("write").__call__(var2, var1.getlocal(1));
            var1.setline(1581);
            var1.getlocal(0).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getstate__$137(PyFrame var1, ThreadState var2) {
      var1.setline(1585);
      PyObject var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyUnicode.fromInterned("__dict__"), (PyObject)var1.getglobal("None"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1586);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1587);
         var3 = var1.getlocal(1).__getattr__("copy").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1588);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("getvalue").__call__(var2), var1.getlocal(0).__getattr__("_readnl"), var1.getlocal(0).__getattr__("tell").__call__(var2), var1.getlocal(1)});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __setstate__$138(PyFrame var1, ThreadState var2) {
      var1.setline(1592);
      var1.getlocal(0).__getattr__("_checkClosed").__call__(var2);
      var1.setline(1594);
      PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("tuple")).__not__();
      PyObject var3;
      if (!var10000.__nonzero__()) {
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var10000 = var3._lt(Py.newInteger(4));
         var3 = null;
      }

      PyUnicode var4;
      if (var10000.__nonzero__()) {
         var1.setline(1595);
         var4 = PyUnicode.fromInterned("%s.__setstate__ argument should be 4-tuple got %s");
         var1.setlocal(2, var4);
         var3 = null;
         var1.setline(1596);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, var1.getlocal(2)._mod(new PyTuple(new PyObject[]{var1.getglobal("type").__call__(var2, var1.getlocal(0)), var1.getglobal("type").__call__(var2, var1.getlocal(1))}))));
      } else {
         var1.setline(1599);
         var1.getlocal(0).__getattr__("__init__").__call__(var2, var1.getglobal("None"), var1.getlocal(1).__getitem__(Py.newInteger(1)));
         var1.setline(1602);
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1603);
         var3 = var1.getlocal(3);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1604);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("unicode")).__not__().__nonzero__()) {
               var1.setline(1605);
               var4 = PyUnicode.fromInterned("ivalue should be unicode or None, got %s");
               var1.setlocal(2, var4);
               var3 = null;
               var1.setline(1606);
               throw Py.makeException(var1.getglobal("TypeError").__call__(var2, var1.getlocal(2)._mod(var1.getglobal("type").__call__(var2, var1.getlocal(3)))));
            }

            var1.setline(1607);
            var10000 = var1.getlocal(0).__getattr__("_encoder");
            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("_get_encoder").__call__(var2);
            }

            var3 = var10000;
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(1608);
            var3 = var1.getlocal(4).__getattr__("encode").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(1609);
            var1.getlocal(0).__getattr__("buffer").__getattr__("write").__call__(var2, var1.getlocal(5));
         }

         var1.setline(1613);
         var1.getlocal(0).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setline(1616);
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(2));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(1617);
         if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__not__().__nonzero__()) {
            var1.setline(1618);
            var4 = PyUnicode.fromInterned("third item of state must be an integer, got %s");
            var1.setlocal(2, var4);
            var3 = null;
            var1.setline(1619);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2, var1.getlocal(2)._mod(var1.getglobal("type").__call__(var2, var1.getlocal(6)))));
         } else {
            var1.setline(1620);
            var3 = var1.getlocal(6);
            var10000 = var3._lt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1621);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("position value cannot be negative")));
            } else {
               var1.setline(1622);
               var1.getlocal(0).__getattr__("seek").__call__(var2, var1.getlocal(6));
               var1.setline(1625);
               var3 = var1.getlocal(1).__getitem__(Py.newInteger(3));
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(1626);
               var3 = var1.getlocal(7);
               var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
               if (var10000.__not__().__nonzero__()) {
                  var1.setline(1627);
                  if (!var1.getglobal("isinstance").__call__(var2, var1.getlocal(7), var1.getglobal("dict")).__nonzero__()) {
                     var1.setline(1630);
                     var4 = PyUnicode.fromInterned("fourth item of state should be a dict, got %s");
                     var1.setlocal(2, var4);
                     var3 = null;
                     var1.setline(1631);
                     throw Py.makeException(var1.getglobal("TypeError").__call__(var2, var1.getlocal(2)._mod(var1.getglobal("type").__call__(var2, var1.getlocal(7)))));
                  }

                  var1.setline(1628);
                  var3 = var1.getlocal(7);
                  var1.getlocal(0).__setattr__("__dict__", var3);
                  var3 = null;
               }

               var1.f_lasti = -1;
               return Py.None;
            }
         }
      }
   }

   public PyObject getvalue$139(PyFrame var1, ThreadState var2) {
      var1.setline(1634);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(1635);
      PyObject var3 = var1.getlocal(0).__getattr__("buffer").__getattr__("getvalue").__call__(var2).__getattr__("decode").__call__(var2, var1.getlocal(0).__getattr__("_encoding"), var1.getlocal(0).__getattr__("_errors"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$140(PyFrame var1, ThreadState var2) {
      var1.setline(1640);
      PyObject var3 = var1.getglobal("object").__getattr__("__repr__").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject errors$141(PyFrame var1, ThreadState var2) {
      var1.setline(1644);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject encoding$142(PyFrame var1, ThreadState var2) {
      var1.setline(1648);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject detach$143(PyFrame var1, ThreadState var2) {
      var1.setline(1652);
      var1.getlocal(0).__getattr__("_unsupported").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("detach"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public _io$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      BlockingIOError$1 = Py.newCode(0, var2, var1, "BlockingIOError", 56, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "errno", "strerror", "characters_written"};
      __init__$2 = Py.newCode(4, var2, var1, "__init__", 60, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _BufferedIOBase$3 = Py.newCode(0, var2, var1, "_BufferedIOBase", 70, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "n"};
      read$4 = Py.newCode(2, var2, var1, "read", 87, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      read1$5 = Py.newCode(2, var2, var1, "read1", 107, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "b", "data", "n", "err", "array"};
      readinto$6 = Py.newCode(2, var2, var1, "readinto", 111, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "b"};
      write$7 = Py.newCode(2, var2, var1, "write", 134, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      detach$8 = Py.newCode(1, var2, var1, "detach", 145, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _BufferedIOMixin$9 = Py.newCode(0, var2, var1, "_BufferedIOMixin", 155, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "raw"};
      __init__$10 = Py.newCode(2, var2, var1, "__init__", 164, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos", "whence", "new_position"};
      seek$11 = Py.newCode(3, var2, var1, "seek", 170, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos"};
      tell$12 = Py.newCode(1, var2, var1, "tell", 176, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos"};
      truncate$13 = Py.newCode(2, var2, var1, "truncate", 182, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      flush$14 = Py.newCode(1, var2, var1, "flush", 196, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$15 = Py.newCode(1, var2, var1, "close", 201, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "raw"};
      detach$16 = Py.newCode(1, var2, var1, "detach", 210, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      seekable$17 = Py.newCode(1, var2, var1, "seekable", 220, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      readable$18 = Py.newCode(1, var2, var1, "readable", 224, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      writable$19 = Py.newCode(1, var2, var1, "writable", 228, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      raw$20 = Py.newCode(1, var2, var1, "raw", 232, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      closed$21 = Py.newCode(1, var2, var1, "closed", 236, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _checkInitialized$22 = Py.newCode(1, var2, var1, "_checkInitialized", 242, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      name$23 = Py.newCode(1, var2, var1, "name", 249, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      mode$24 = Py.newCode(1, var2, var1, "mode", 253, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "clsname", "name"};
      __repr__$25 = Py.newCode(1, var2, var1, "__repr__", 257, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      fileno$26 = Py.newCode(1, var2, var1, "fileno", 268, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isatty$27 = Py.newCode(1, var2, var1, "isatty", 271, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BytesIO$28 = Py.newCode(0, var2, var1, "BytesIO", 275, false, false, self, 28, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "initial_bytes", "buf"};
      __init__$29 = Py.newCode(2, var2, var1, "__init__", 279, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "d"};
      __getstate__$30 = Py.newCode(1, var2, var1, "__getstate__", 287, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "state", "fmt", "p", "d"};
      __setstate__$31 = Py.newCode(2, var2, var1, "__setstate__", 294, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getvalue$32 = Py.newCode(1, var2, var1, "getvalue", 329, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "newpos", "b"};
      read$33 = Py.newCode(2, var2, var1, "read", 336, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      read1$34 = Py.newCode(2, var2, var1, "read1", 353, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "b", "n", "pos", "padding"};
      write$35 = Py.newCode(2, var2, var1, "write", 358, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos", "whence"};
      seek$36 = Py.newCode(3, var2, var1, "seek", 376, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tell$37 = Py.newCode(1, var2, var1, "tell", 395, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos"};
      truncate$38 = Py.newCode(2, var2, var1, "truncate", 400, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      readable$39 = Py.newCode(1, var2, var1, "readable", 415, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      writable$40 = Py.newCode(1, var2, var1, "writable", 419, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      seekable$41 = Py.newCode(1, var2, var1, "seekable", 423, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BufferedReader$42 = Py.newCode(0, var2, var1, "BufferedReader", 428, false, false, self, 42, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "raw", "buffer_size"};
      __init__$43 = Py.newCode(3, var2, var1, "__init__", 439, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _reset_read_buf$44 = Py.newCode(1, var2, var1, "_reset_read_buf", 453, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      read$45 = Py.newCode(2, var2, var1, "read", 457, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "nodata_val", "empty_values", "buf", "pos", "chunks", "current_size", "chunk", "e", "avail", "wanted", "out"};
      _read_unlocked$46 = Py.newCode(2, var2, var1, "_read_unlocked", 471, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      peek$47 = Py.newCode(2, var2, var1, "peek", 527, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "want", "have", "to_read", "current", "e"};
      _peek_unlocked$48 = Py.newCode(2, var2, var1, "_peek_unlocked", 537, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      read1$49 = Py.newCode(2, var2, var1, "read1", 555, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tell$50 = Py.newCode(1, var2, var1, "tell", 569, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos", "whence"};
      seek$51 = Py.newCode(3, var2, var1, "seek", 572, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BufferedWriter$52 = Py.newCode(0, var2, var1, "BufferedWriter", 583, false, false, self, 52, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "raw", "buffer_size", "max_buffer_size"};
      __init__$53 = Py.newCode(4, var2, var1, "__init__", 594, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "b", "before", "written", "e", "overage"};
      write$54 = Py.newCode(2, var2, var1, "write", 610, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos"};
      truncate$55 = Py.newCode(2, var2, var1, "truncate", 642, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      flush$56 = Py.newCode(1, var2, var1, "flush", 649, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "e"};
      _flush_unlocked$57 = Py.newCode(1, var2, var1, "_flush_unlocked", 653, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tell$58 = Py.newCode(1, var2, var1, "tell", 675, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos", "whence"};
      seek$59 = Py.newCode(3, var2, var1, "seek", 678, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BufferedRWPair$60 = Py.newCode(0, var2, var1, "BufferedRWPair", 686, false, false, self, 60, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "reader", "writer", "buffer_size", "max_buffer_size"};
      __init__$61 = Py.newCode(5, var2, var1, "__init__", 702, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      read$62 = Py.newCode(2, var2, var1, "read", 720, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "b"};
      readinto$63 = Py.newCode(2, var2, var1, "readinto", 725, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "b"};
      write$64 = Py.newCode(2, var2, var1, "write", 728, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      peek$65 = Py.newCode(2, var2, var1, "peek", 731, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      read1$66 = Py.newCode(2, var2, var1, "read1", 734, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      readable$67 = Py.newCode(1, var2, var1, "readable", 737, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      writable$68 = Py.newCode(1, var2, var1, "writable", 740, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      flush$69 = Py.newCode(1, var2, var1, "flush", 743, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$70 = Py.newCode(1, var2, var1, "close", 746, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isatty$71 = Py.newCode(1, var2, var1, "isatty", 750, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      closed$72 = Py.newCode(1, var2, var1, "closed", 753, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BufferedRandom$73 = Py.newCode(0, var2, var1, "BufferedRandom", 758, false, false, self, 73, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "raw", "buffer_size", "max_buffer_size"};
      __init__$74 = Py.newCode(4, var2, var1, "__init__", 769, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos", "whence"};
      seek$75 = Py.newCode(3, var2, var1, "seek", 775, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tell$76 = Py.newCode(1, var2, var1, "tell", 792, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos"};
      truncate$77 = Py.newCode(2, var2, var1, "truncate", 798, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      read$78 = Py.newCode(2, var2, var1, "read", 804, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "b"};
      readinto$79 = Py.newCode(2, var2, var1, "readinto", 810, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      peek$80 = Py.newCode(2, var2, var1, "peek", 814, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      read1$81 = Py.newCode(2, var2, var1, "read1", 818, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "b"};
      write$82 = Py.newCode(2, var2, var1, "write", 822, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _TextIOBase$83 = Py.newCode(0, var2, var1, "_TextIOBase", 831, false, false, self, 83, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "n"};
      read$84 = Py.newCode(2, var2, var1, "read", 840, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s"};
      write$85 = Py.newCode(2, var2, var1, "write", 848, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos"};
      truncate$86 = Py.newCode(2, var2, var1, "truncate", 852, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      readline$87 = Py.newCode(1, var2, var1, "readline", 856, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      detach$88 = Py.newCode(1, var2, var1, "detach", 863, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      encoding$89 = Py.newCode(1, var2, var1, "encoding", 872, false, false, self, 89, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      newlines$90 = Py.newCode(1, var2, var1, "newlines", 877, false, false, self, 90, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      errors$91 = Py.newCode(1, var2, var1, "errors", 887, false, false, self, 91, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalNewlineDecoder$92 = Py.newCode(0, var2, var1, "IncrementalNewlineDecoder", 895, false, false, self, 92, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "decoder", "translate", "errors"};
      __init__$93 = Py.newCode(4, var2, var1, "__init__", 902, false, false, self, 93, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "final", "output", "crlf", "cr", "lf"};
      decode$94 = Py.newCode(3, var2, var1, "decode", 909, false, false, self, 94, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "buf", "flag"};
      getstate$95 = Py.newCode(1, var2, var1, "getstate", 940, false, false, self, 95, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "state", "buf", "flag"};
      setstate$96 = Py.newCode(2, var2, var1, "setstate", 951, false, false, self, 96, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$97 = Py.newCode(1, var2, var1, "reset", 957, false, false, self, 97, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      newlines$98 = Py.newCode(1, var2, var1, "newlines", 967, false, false, self, 98, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"chars"};
      _check_decoded_chars$99 = Py.newCode(1, var2, var1, "_check_decoded_chars", 980, false, false, self, 99, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"b", "context"};
      _check_buffered_bytes$100 = Py.newCode(2, var2, var1, "_check_buffered_bytes", 986, false, false, self, 100, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TextIOWrapper$101 = Py.newCode(0, var2, var1, "TextIOWrapper", 994, false, false, self, 101, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "buffer", "encoding", "errors", "newline", "line_buffering", "locale", "position"};
      __init__$102 = Py.newCode(6, var2, var1, "__init__", 1020, false, false, self, 102, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      __repr__$103 = Py.newCode(1, var2, var1, "__repr__", 1081, false, false, self, 103, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      encoding$104 = Py.newCode(1, var2, var1, "encoding", 1090, false, false, self, 104, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      errors$105 = Py.newCode(1, var2, var1, "errors", 1094, false, false, self, 105, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      line_buffering$106 = Py.newCode(1, var2, var1, "line_buffering", 1098, false, false, self, 106, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      buffer$107 = Py.newCode(1, var2, var1, "buffer", 1102, false, false, self, 107, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      seekable$108 = Py.newCode(1, var2, var1, "seekable", 1106, false, false, self, 108, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      readable$109 = Py.newCode(1, var2, var1, "readable", 1111, false, false, self, 109, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      writable$110 = Py.newCode(1, var2, var1, "writable", 1115, false, false, self, 110, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      flush$111 = Py.newCode(1, var2, var1, "flush", 1119, false, false, self, 111, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$112 = Py.newCode(1, var2, var1, "close", 1124, false, false, self, 112, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      closed$113 = Py.newCode(1, var2, var1, "closed", 1133, false, false, self, 113, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _checkInitialized$114 = Py.newCode(1, var2, var1, "_checkInitialized", 1139, false, false, self, 114, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      name$115 = Py.newCode(1, var2, var1, "name", 1146, false, false, self, 115, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      fileno$116 = Py.newCode(1, var2, var1, "fileno", 1150, false, false, self, 116, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isatty$117 = Py.newCode(1, var2, var1, "isatty", 1153, false, false, self, 117, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "length", "haslf", "encoder", "b"};
      write$118 = Py.newCode(2, var2, var1, "write", 1156, false, false, self, 118, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "make_encoder"};
      _get_encoder$119 = Py.newCode(1, var2, var1, "_get_encoder", 1178, false, false, self, 119, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "make_decoder", "decoder"};
      _get_decoder$120 = Py.newCode(1, var2, var1, "_get_decoder", 1183, false, false, self, 120, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "chars"};
      _set_decoded_chars$121 = Py.newCode(2, var2, var1, "_set_decoded_chars", 1194, false, false, self, 121, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "offset", "chars"};
      _get_decoded_chars$122 = Py.newCode(2, var2, var1, "_get_decoded_chars", 1199, false, false, self, 122, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      _rewind_decoded_chars$123 = Py.newCode(2, var2, var1, "_rewind_decoded_chars", 1209, false, false, self, 123, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dec_buffer", "dec_flags", "input_chunk", "eof", "decoded_chunk"};
      _read_chunk$124 = Py.newCode(1, var2, var1, "_read_chunk", 1215, false, false, self, 124, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "position", "dec_flags", "bytes_to_feed", "need_eof", "chars_to_skip"};
      _pack_cookie$125 = Py.newCode(6, var2, var1, "_pack_cookie", 1253, false, false, self, 125, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "bigint", "rest", "position", "dec_flags", "bytes_to_feed", "need_eof", "chars_to_skip"};
      _unpack_cookie$126 = Py.newCode(2, var2, var1, "_unpack_cookie", 1263, false, false, self, 126, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "position", "decoder", "dec_flags", "next_input", "chars_to_skip", "saved_state", "start_pos", "start_flags", "bytes_fed", "chars_decoded", "need_eof", "next_byte", "dec_buffer"};
      tell$127 = Py.newCode(1, var2, var1, "tell", 1270, false, false, self, 127, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos"};
      truncate$128 = Py.newCode(2, var2, var1, "truncate", 1332, false, false, self, 128, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "buffer"};
      detach$129 = Py.newCode(1, var2, var1, "detach", 1338, false, false, self, 129, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cookie", "whence", "position", "start_pos", "dec_flags", "bytes_to_feed", "need_eof", "chars_to_skip", "input_chunk", "decoded_chunk", "encoder"};
      seek$130 = Py.newCode(3, var2, var1, "seek", 1347, false, false, self, 130, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "decoder", "input_chunk", "decoded_chunk", "result", "eof"};
      read$131 = Py.newCode(2, var2, var1, "read", 1422, false, false, self, 131, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      next$132 = Py.newCode(1, var2, var1, "next", 1451, false, false, self, 132, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "limit", "line", "start", "pos", "endpos", "nlpos", "crpos"};
      readline$133 = Py.newCode(2, var2, var1, "readline", 1460, false, false, self, 133, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      newlines$134 = Py.newCode(1, var2, var1, "newlines", 1548, false, false, self, 134, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StringIO$135 = Py.newCode(0, var2, var1, "StringIO", 1553, false, false, self, 135, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "initial_value", "newline", "fmt"};
      __init__$136 = Py.newCode(3, var2, var1, "__init__", 1560, false, false, self, 136, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "d"};
      __getstate__$137 = Py.newCode(1, var2, var1, "__getstate__", 1584, false, false, self, 137, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "state", "fmt", "value", "encoder", "b", "p", "d"};
      __setstate__$138 = Py.newCode(2, var2, var1, "__setstate__", 1591, false, false, self, 138, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getvalue$139 = Py.newCode(1, var2, var1, "getvalue", 1633, false, false, self, 139, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$140 = Py.newCode(1, var2, var1, "__repr__", 1637, false, false, self, 140, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      errors$141 = Py.newCode(1, var2, var1, "errors", 1642, false, false, self, 141, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      encoding$142 = Py.newCode(1, var2, var1, "encoding", 1646, false, false, self, 142, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      detach$143 = Py.newCode(1, var2, var1, "detach", 1650, false, false, self, 143, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new _io$py("_io$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(_io$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.BlockingIOError$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this._BufferedIOBase$3(var2, var3);
         case 4:
            return this.read$4(var2, var3);
         case 5:
            return this.read1$5(var2, var3);
         case 6:
            return this.readinto$6(var2, var3);
         case 7:
            return this.write$7(var2, var3);
         case 8:
            return this.detach$8(var2, var3);
         case 9:
            return this._BufferedIOMixin$9(var2, var3);
         case 10:
            return this.__init__$10(var2, var3);
         case 11:
            return this.seek$11(var2, var3);
         case 12:
            return this.tell$12(var2, var3);
         case 13:
            return this.truncate$13(var2, var3);
         case 14:
            return this.flush$14(var2, var3);
         case 15:
            return this.close$15(var2, var3);
         case 16:
            return this.detach$16(var2, var3);
         case 17:
            return this.seekable$17(var2, var3);
         case 18:
            return this.readable$18(var2, var3);
         case 19:
            return this.writable$19(var2, var3);
         case 20:
            return this.raw$20(var2, var3);
         case 21:
            return this.closed$21(var2, var3);
         case 22:
            return this._checkInitialized$22(var2, var3);
         case 23:
            return this.name$23(var2, var3);
         case 24:
            return this.mode$24(var2, var3);
         case 25:
            return this.__repr__$25(var2, var3);
         case 26:
            return this.fileno$26(var2, var3);
         case 27:
            return this.isatty$27(var2, var3);
         case 28:
            return this.BytesIO$28(var2, var3);
         case 29:
            return this.__init__$29(var2, var3);
         case 30:
            return this.__getstate__$30(var2, var3);
         case 31:
            return this.__setstate__$31(var2, var3);
         case 32:
            return this.getvalue$32(var2, var3);
         case 33:
            return this.read$33(var2, var3);
         case 34:
            return this.read1$34(var2, var3);
         case 35:
            return this.write$35(var2, var3);
         case 36:
            return this.seek$36(var2, var3);
         case 37:
            return this.tell$37(var2, var3);
         case 38:
            return this.truncate$38(var2, var3);
         case 39:
            return this.readable$39(var2, var3);
         case 40:
            return this.writable$40(var2, var3);
         case 41:
            return this.seekable$41(var2, var3);
         case 42:
            return this.BufferedReader$42(var2, var3);
         case 43:
            return this.__init__$43(var2, var3);
         case 44:
            return this._reset_read_buf$44(var2, var3);
         case 45:
            return this.read$45(var2, var3);
         case 46:
            return this._read_unlocked$46(var2, var3);
         case 47:
            return this.peek$47(var2, var3);
         case 48:
            return this._peek_unlocked$48(var2, var3);
         case 49:
            return this.read1$49(var2, var3);
         case 50:
            return this.tell$50(var2, var3);
         case 51:
            return this.seek$51(var2, var3);
         case 52:
            return this.BufferedWriter$52(var2, var3);
         case 53:
            return this.__init__$53(var2, var3);
         case 54:
            return this.write$54(var2, var3);
         case 55:
            return this.truncate$55(var2, var3);
         case 56:
            return this.flush$56(var2, var3);
         case 57:
            return this._flush_unlocked$57(var2, var3);
         case 58:
            return this.tell$58(var2, var3);
         case 59:
            return this.seek$59(var2, var3);
         case 60:
            return this.BufferedRWPair$60(var2, var3);
         case 61:
            return this.__init__$61(var2, var3);
         case 62:
            return this.read$62(var2, var3);
         case 63:
            return this.readinto$63(var2, var3);
         case 64:
            return this.write$64(var2, var3);
         case 65:
            return this.peek$65(var2, var3);
         case 66:
            return this.read1$66(var2, var3);
         case 67:
            return this.readable$67(var2, var3);
         case 68:
            return this.writable$68(var2, var3);
         case 69:
            return this.flush$69(var2, var3);
         case 70:
            return this.close$70(var2, var3);
         case 71:
            return this.isatty$71(var2, var3);
         case 72:
            return this.closed$72(var2, var3);
         case 73:
            return this.BufferedRandom$73(var2, var3);
         case 74:
            return this.__init__$74(var2, var3);
         case 75:
            return this.seek$75(var2, var3);
         case 76:
            return this.tell$76(var2, var3);
         case 77:
            return this.truncate$77(var2, var3);
         case 78:
            return this.read$78(var2, var3);
         case 79:
            return this.readinto$79(var2, var3);
         case 80:
            return this.peek$80(var2, var3);
         case 81:
            return this.read1$81(var2, var3);
         case 82:
            return this.write$82(var2, var3);
         case 83:
            return this._TextIOBase$83(var2, var3);
         case 84:
            return this.read$84(var2, var3);
         case 85:
            return this.write$85(var2, var3);
         case 86:
            return this.truncate$86(var2, var3);
         case 87:
            return this.readline$87(var2, var3);
         case 88:
            return this.detach$88(var2, var3);
         case 89:
            return this.encoding$89(var2, var3);
         case 90:
            return this.newlines$90(var2, var3);
         case 91:
            return this.errors$91(var2, var3);
         case 92:
            return this.IncrementalNewlineDecoder$92(var2, var3);
         case 93:
            return this.__init__$93(var2, var3);
         case 94:
            return this.decode$94(var2, var3);
         case 95:
            return this.getstate$95(var2, var3);
         case 96:
            return this.setstate$96(var2, var3);
         case 97:
            return this.reset$97(var2, var3);
         case 98:
            return this.newlines$98(var2, var3);
         case 99:
            return this._check_decoded_chars$99(var2, var3);
         case 100:
            return this._check_buffered_bytes$100(var2, var3);
         case 101:
            return this.TextIOWrapper$101(var2, var3);
         case 102:
            return this.__init__$102(var2, var3);
         case 103:
            return this.__repr__$103(var2, var3);
         case 104:
            return this.encoding$104(var2, var3);
         case 105:
            return this.errors$105(var2, var3);
         case 106:
            return this.line_buffering$106(var2, var3);
         case 107:
            return this.buffer$107(var2, var3);
         case 108:
            return this.seekable$108(var2, var3);
         case 109:
            return this.readable$109(var2, var3);
         case 110:
            return this.writable$110(var2, var3);
         case 111:
            return this.flush$111(var2, var3);
         case 112:
            return this.close$112(var2, var3);
         case 113:
            return this.closed$113(var2, var3);
         case 114:
            return this._checkInitialized$114(var2, var3);
         case 115:
            return this.name$115(var2, var3);
         case 116:
            return this.fileno$116(var2, var3);
         case 117:
            return this.isatty$117(var2, var3);
         case 118:
            return this.write$118(var2, var3);
         case 119:
            return this._get_encoder$119(var2, var3);
         case 120:
            return this._get_decoder$120(var2, var3);
         case 121:
            return this._set_decoded_chars$121(var2, var3);
         case 122:
            return this._get_decoded_chars$122(var2, var3);
         case 123:
            return this._rewind_decoded_chars$123(var2, var3);
         case 124:
            return this._read_chunk$124(var2, var3);
         case 125:
            return this._pack_cookie$125(var2, var3);
         case 126:
            return this._unpack_cookie$126(var2, var3);
         case 127:
            return this.tell$127(var2, var3);
         case 128:
            return this.truncate$128(var2, var3);
         case 129:
            return this.detach$129(var2, var3);
         case 130:
            return this.seek$130(var2, var3);
         case 131:
            return this.read$131(var2, var3);
         case 132:
            return this.next$132(var2, var3);
         case 133:
            return this.readline$133(var2, var3);
         case 134:
            return this.newlines$134(var2, var3);
         case 135:
            return this.StringIO$135(var2, var3);
         case 136:
            return this.__init__$136(var2, var3);
         case 137:
            return this.__getstate__$137(var2, var3);
         case 138:
            return this.__setstate__$138(var2, var3);
         case 139:
            return this.getvalue$139(var2, var3);
         case 140:
            return this.__repr__$140(var2, var3);
         case 141:
            return this.errors$141(var2, var3);
         case 142:
            return this.encoding$142(var2, var3);
         case 143:
            return this.detach$143(var2, var3);
         default:
            return null;
      }
   }
}
