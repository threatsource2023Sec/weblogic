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
@MTime(1498849383000L)
@Filename("_pyio.py")
public class _pyio$py extends PyFunctionTable implements PyRunnable {
   static _pyio$py self;
   static final PyCode f$0;
   static final PyCode BlockingIOError$1;
   static final PyCode __init__$2;
   static final PyCode open$3;
   static final PyCode DocDescriptor$4;
   static final PyCode __get__$5;
   static final PyCode OpenWrapper$6;
   static final PyCode __new__$7;
   static final PyCode UnsupportedOperation$8;
   static final PyCode IOBase$9;
   static final PyCode _unsupported$10;
   static final PyCode seek$11;
   static final PyCode tell$12;
   static final PyCode truncate$13;
   static final PyCode flush$14;
   static final PyCode close$15;
   static final PyCode __del__$16;
   static final PyCode seekable$17;
   static final PyCode _checkSeekable$18;
   static final PyCode readable$19;
   static final PyCode _checkReadable$20;
   static final PyCode writable$21;
   static final PyCode _checkWritable$22;
   static final PyCode closed$23;
   static final PyCode _checkClosed$24;
   static final PyCode __enter__$25;
   static final PyCode __exit__$26;
   static final PyCode fileno$27;
   static final PyCode isatty$28;
   static final PyCode readline$29;
   static final PyCode nreadahead$30;
   static final PyCode nreadahead$31;
   static final PyCode __iter__$32;
   static final PyCode next$33;
   static final PyCode readlines$34;
   static final PyCode writelines$35;
   static final PyCode RawIOBase$36;
   static final PyCode read$37;
   static final PyCode readall$38;
   static final PyCode readinto$39;
   static final PyCode write$40;
   static final PyCode BufferedIOBase$41;
   static final PyCode read$42;
   static final PyCode read1$43;
   static final PyCode readinto$44;
   static final PyCode write$45;
   static final PyCode detach$46;
   static final PyCode _BufferedIOMixin$47;
   static final PyCode __init__$48;
   static final PyCode seek$49;
   static final PyCode tell$50;
   static final PyCode truncate$51;
   static final PyCode flush$52;
   static final PyCode close$53;
   static final PyCode detach$54;
   static final PyCode seekable$55;
   static final PyCode readable$56;
   static final PyCode writable$57;
   static final PyCode raw$58;
   static final PyCode closed$59;
   static final PyCode name$60;
   static final PyCode mode$61;
   static final PyCode __repr__$62;
   static final PyCode fileno$63;
   static final PyCode isatty$64;
   static final PyCode BytesIO$65;
   static final PyCode __init__$66;
   static final PyCode __getstate__$67;
   static final PyCode getvalue$68;
   static final PyCode read$69;
   static final PyCode read1$70;
   static final PyCode write$71;
   static final PyCode seek$72;
   static final PyCode tell$73;
   static final PyCode truncate$74;
   static final PyCode readable$75;
   static final PyCode writable$76;
   static final PyCode seekable$77;
   static final PyCode BufferedReader$78;
   static final PyCode __init__$79;
   static final PyCode _reset_read_buf$80;
   static final PyCode read$81;
   static final PyCode _read_unlocked$82;
   static final PyCode peek$83;
   static final PyCode _peek_unlocked$84;
   static final PyCode read1$85;
   static final PyCode tell$86;
   static final PyCode seek$87;
   static final PyCode BufferedWriter$88;
   static final PyCode __init__$89;
   static final PyCode write$90;
   static final PyCode truncate$91;
   static final PyCode flush$92;
   static final PyCode _flush_unlocked$93;
   static final PyCode tell$94;
   static final PyCode seek$95;
   static final PyCode BufferedRWPair$96;
   static final PyCode __init__$97;
   static final PyCode read$98;
   static final PyCode readinto$99;
   static final PyCode write$100;
   static final PyCode peek$101;
   static final PyCode read1$102;
   static final PyCode readable$103;
   static final PyCode writable$104;
   static final PyCode flush$105;
   static final PyCode close$106;
   static final PyCode isatty$107;
   static final PyCode closed$108;
   static final PyCode BufferedRandom$109;
   static final PyCode __init__$110;
   static final PyCode seek$111;
   static final PyCode tell$112;
   static final PyCode truncate$113;
   static final PyCode read$114;
   static final PyCode readinto$115;
   static final PyCode peek$116;
   static final PyCode read1$117;
   static final PyCode write$118;
   static final PyCode TextIOBase$119;
   static final PyCode read$120;
   static final PyCode write$121;
   static final PyCode truncate$122;
   static final PyCode readline$123;
   static final PyCode detach$124;
   static final PyCode encoding$125;
   static final PyCode newlines$126;
   static final PyCode errors$127;
   static final PyCode IncrementalNewlineDecoder$128;
   static final PyCode __init__$129;
   static final PyCode decode$130;
   static final PyCode getstate$131;
   static final PyCode setstate$132;
   static final PyCode reset$133;
   static final PyCode newlines$134;
   static final PyCode TextIOWrapper$135;
   static final PyCode __init__$136;
   static final PyCode __repr__$137;
   static final PyCode encoding$138;
   static final PyCode errors$139;
   static final PyCode line_buffering$140;
   static final PyCode buffer$141;
   static final PyCode seekable$142;
   static final PyCode readable$143;
   static final PyCode writable$144;
   static final PyCode flush$145;
   static final PyCode close$146;
   static final PyCode closed$147;
   static final PyCode name$148;
   static final PyCode fileno$149;
   static final PyCode isatty$150;
   static final PyCode write$151;
   static final PyCode _get_encoder$152;
   static final PyCode _get_decoder$153;
   static final PyCode _set_decoded_chars$154;
   static final PyCode _get_decoded_chars$155;
   static final PyCode _rewind_decoded_chars$156;
   static final PyCode _read_chunk$157;
   static final PyCode _pack_cookie$158;
   static final PyCode _unpack_cookie$159;
   static final PyCode tell$160;
   static final PyCode truncate$161;
   static final PyCode detach$162;
   static final PyCode seek$163;
   static final PyCode read$164;
   static final PyCode next$165;
   static final PyCode readline$166;
   static final PyCode newlines$167;
   static final PyCode StringIO$168;
   static final PyCode __init__$169;
   static final PyCode getvalue$170;
   static final PyCode __repr__$171;
   static final PyCode errors$172;
   static final PyCode encoding$173;
   static final PyCode detach$174;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nPython implementation of the io module.\n"));
      var1.setline(3);
      PyString.fromInterned("\nPython implementation of the io module.\n");
      var1.setline(5);
      String[] var3 = new String[]{"print_function", "unicode_literals"};
      PyObject[] var7 = imp.importFrom("__future__", var3, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("print_function", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("unicode_literals", var4);
      var4 = null;
      var1.setline(7);
      PyObject var8 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var8);
      var3 = null;
      var1.setline(8);
      var8 = imp.importOne("abc", var1, -1);
      var1.setlocal("abc", var8);
      var3 = null;
      var1.setline(9);
      var8 = imp.importOne("codecs", var1, -1);
      var1.setlocal("codecs", var8);
      var3 = null;
      var1.setline(10);
      var8 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var8);
      var3 = null;
      var1.setline(11);
      var8 = imp.importOne("errno", var1, -1);
      var1.setlocal("errno", var8);
      var3 = null;

      try {
         var1.setline(14);
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

         var1.setline(16);
         String[] var9 = new String[]{"allocate_lock"};
         PyObject[] var10 = imp.importFrom("dummy_thread", var9, var1, -1);
         PyObject var5 = var10[0];
         var1.setlocal("Lock", var5);
         var5 = null;
      }

      var1.setline(18);
      var8 = imp.importOne("io", var1, -1);
      var1.setlocal("io", var8);
      var3 = null;
      var1.setline(19);
      var3 = new String[]{"__all__", "SEEK_SET", "SEEK_CUR", "SEEK_END"};
      var7 = imp.importFrom("io", var3, var1, -1);
      var4 = var7[0];
      var1.setlocal("__all__", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("SEEK_SET", var4);
      var4 = null;
      var4 = var7[2];
      var1.setlocal("SEEK_CUR", var4);
      var4 = null;
      var4 = var7[3];
      var1.setlocal("SEEK_END", var4);
      var4 = null;
      var1.setline(20);
      var3 = new String[]{"EINTR"};
      var7 = imp.importFrom("errno", var3, var1, -1);
      var4 = var7[0];
      var1.setlocal("EINTR", var4);
      var4 = null;
      var1.setline(22);
      var8 = var1.getname("type");
      var1.setlocal("__metaclass__", var8);
      var3 = null;
      var1.setline(25);
      var8 = Py.newInteger(8)._mul(Py.newInteger(1024));
      var1.setlocal("DEFAULT_BUFFER_SIZE", var8);
      var3 = null;
      var1.setline(32);
      var7 = new PyObject[]{var1.getname("IOError")};
      var4 = Py.makeClass("BlockingIOError", var7, BlockingIOError$1);
      var1.setlocal("BlockingIOError", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(43);
      var7 = new PyObject[]{PyUnicode.fromInterned("r"), Py.newInteger(-1), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("True")};
      PyFunction var12 = new PyFunction(var1.f_globals, var7, open$3, PyUnicode.fromInterned("Open file and return a stream.  Raise IOError upon failure.\n\n    file is either a text or byte string giving the name (and the path\n    if the file isn't in the current working directory) of the file to\n    be opened or an integer file descriptor of the file to be\n    wrapped. (If a file descriptor is given, it is closed when the\n    returned I/O object is closed, unless closefd is set to False.)\n\n    mode is an optional string that specifies the mode in which the file\n    is opened. It defaults to 'r' which means open for reading in text\n    mode.  Other common values are 'w' for writing (truncating the file if\n    it already exists), and 'a' for appending (which on some Unix systems,\n    means that all writes append to the end of the file regardless of the\n    current seek position). In text mode, if encoding is not specified the\n    encoding used is platform dependent. (For reading and writing raw\n    bytes use binary mode and leave encoding unspecified.) The available\n    modes are:\n\n    ========= ===============================================================\n    Character Meaning\n    --------- ---------------------------------------------------------------\n    'r'       open for reading (default)\n    'w'       open for writing, truncating the file first\n    'a'       open for writing, appending to the end of the file if it exists\n    'b'       binary mode\n    't'       text mode (default)\n    '+'       open a disk file for updating (reading and writing)\n    'U'       universal newline mode (for backwards compatibility; unneeded\n              for new code)\n    ========= ===============================================================\n\n    The default mode is 'rt' (open for reading text). For binary random\n    access, the mode 'w+b' opens and truncates the file to 0 bytes, while\n    'r+b' opens the file without truncation.\n\n    Python distinguishes between files opened in binary and text modes,\n    even when the underlying operating system doesn't. Files opened in\n    binary mode (appending 'b' to the mode argument) return contents as\n    bytes objects without any decoding. In text mode (the default, or when\n    't' is appended to the mode argument), the contents of the file are\n    returned as strings, the bytes having been first decoded using a\n    platform-dependent encoding or using the specified encoding if given.\n\n    buffering is an optional integer used to set the buffering policy.\n    Pass 0 to switch buffering off (only allowed in binary mode), 1 to select\n    line buffering (only usable in text mode), and an integer > 1 to indicate\n    the size of a fixed-size chunk buffer.  When no buffering argument is\n    given, the default buffering policy works as follows:\n\n    * Binary files are buffered in fixed-size chunks; the size of the buffer\n      is chosen using a heuristic trying to determine the underlying device's\n      \"block size\" and falling back on `io.DEFAULT_BUFFER_SIZE`.\n      On many systems, the buffer will typically be 4096 or 8192 bytes long.\n\n    * \"Interactive\" text files (files for which isatty() returns True)\n      use line buffering.  Other text files use the policy described above\n      for binary files.\n\n    encoding is the name of the encoding used to decode or encode the\n    file. This should only be used in text mode. The default encoding is\n    platform dependent, but any encoding supported by Python can be\n    passed.  See the codecs module for the list of supported encodings.\n\n    errors is an optional string that specifies how encoding errors are to\n    be handled---this argument should not be used in binary mode. Pass\n    'strict' to raise a ValueError exception if there is an encoding error\n    (the default of None has the same effect), or pass 'ignore' to ignore\n    errors. (Note that ignoring encoding errors can lead to data loss.)\n    See the documentation for codecs.register for a list of the permitted\n    encoding error strings.\n\n    newline controls how universal newlines works (it only applies to text\n    mode). It can be None, '', '\\n', '\\r', and '\\r\\n'.  It works as\n    follows:\n\n    * On input, if newline is None, universal newlines mode is\n      enabled. Lines in the input can end in '\\n', '\\r', or '\\r\\n', and\n      these are translated into '\\n' before being returned to the\n      caller. If it is '', universal newline mode is enabled, but line\n      endings are returned to the caller untranslated. If it has any of\n      the other legal values, input lines are only terminated by the given\n      string, and the line ending is returned to the caller untranslated.\n\n    * On output, if newline is None, any '\\n' characters written are\n      translated to the system default line separator, os.linesep. If\n      newline is '', no translation takes place. If newline is any of the\n      other legal values, any '\\n' characters written are translated to\n      the given string.\n\n    If closefd is False, the underlying file descriptor will be kept open\n    when the file is closed. This does not work when a file name is given\n    and must be True in that case.\n\n    open() returns a file object whose type depends on the mode, and\n    through which the standard file operations such as reading and writing\n    are performed. When open() is used to open a file in a text mode ('w',\n    'r', 'wt', 'rt', etc.), it returns a TextIOWrapper. When used to open\n    a file in a binary mode, the returned class varies: in read binary\n    mode, it returns a BufferedReader; in write binary and append binary\n    modes, it returns a BufferedWriter, and in read/write mode, it returns\n    a BufferedRandom.\n\n    It is also possible to use a string or bytearray as a file for both\n    reading and writing. For strings StringIO can be used like a file\n    opened in a text mode, and for bytes a BytesIO can be used like a file\n    opened in a binary mode.\n    "));
      var1.setlocal("open", var12);
      var3 = null;
      var1.setline(229);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("DocDescriptor", var7, DocDescriptor$4);
      var1.setlocal("DocDescriptor", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(238);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("OpenWrapper", var7, OpenWrapper$6);
      var1.setlocal("OpenWrapper", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(252);
      var7 = new PyObject[]{var1.getname("ValueError"), var1.getname("IOError")};
      var4 = Py.makeClass("UnsupportedOperation", var7, UnsupportedOperation$8);
      var1.setlocal("UnsupportedOperation", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(256);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("IOBase", var7, IOBase$9);
      var1.setlocal("IOBase", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(524);
      var1.getname("io").__getattr__("IOBase").__getattr__("register").__call__(var2, var1.getname("IOBase"));
      var1.setline(527);
      var7 = new PyObject[]{var1.getname("IOBase")};
      var4 = Py.makeClass("RawIOBase", var7, RawIOBase$36);
      var1.setlocal("RawIOBase", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(587);
      var1.getname("io").__getattr__("RawIOBase").__getattr__("register").__call__(var2, var1.getname("RawIOBase"));
      var1.setline(588);
      var3 = new String[]{"FileIO"};
      var7 = imp.importFrom("_io", var3, var1, -1);
      var4 = var7[0];
      var1.setlocal("FileIO", var4);
      var4 = null;
      var1.setline(589);
      var1.getname("RawIOBase").__getattr__("register").__call__(var2, var1.getname("FileIO"));
      var1.setline(592);
      var7 = new PyObject[]{var1.getname("IOBase")};
      var4 = Py.makeClass("BufferedIOBase", var7, BufferedIOBase$41);
      var1.setlocal("BufferedIOBase", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(676);
      var1.getname("io").__getattr__("BufferedIOBase").__getattr__("register").__call__(var2, var1.getname("BufferedIOBase"));
      var1.setline(679);
      var7 = new PyObject[]{var1.getname("BufferedIOBase")};
      var4 = Py.makeClass("_BufferedIOMixin", var7, _BufferedIOMixin$47);
      var1.setlocal("_BufferedIOMixin", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(785);
      var7 = new PyObject[]{var1.getname("BufferedIOBase")};
      var4 = Py.makeClass("BytesIO", var7, BytesIO$65);
      var1.setlocal("BytesIO", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(903);
      var7 = new PyObject[]{var1.getname("_BufferedIOMixin")};
      var4 = Py.makeClass("BufferedReader", var7, BufferedReader$78);
      var1.setlocal("BufferedReader", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1054);
      var7 = new PyObject[]{var1.getname("_BufferedIOMixin")};
      var4 = Py.makeClass("BufferedWriter", var7, BufferedWriter$88);
      var1.setlocal("BufferedWriter", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1151);
      var7 = new PyObject[]{var1.getname("BufferedIOBase")};
      var4 = Py.makeClass("BufferedRWPair", var7, BufferedRWPair$96);
      var1.setlocal("BufferedRWPair", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1223);
      var7 = new PyObject[]{var1.getname("BufferedWriter"), var1.getname("BufferedReader")};
      var4 = Py.makeClass("BufferedRandom", var7, BufferedRandom$109);
      var1.setlocal("BufferedRandom", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1296);
      var7 = new PyObject[]{var1.getname("IOBase")};
      var4 = Py.makeClass("TextIOBase", var7, TextIOBase$119);
      var1.setlocal("TextIOBase", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1359);
      var1.getname("io").__getattr__("TextIOBase").__getattr__("register").__call__(var2, var1.getname("TextIOBase"));
      var1.setline(1362);
      var7 = new PyObject[]{var1.getname("codecs").__getattr__("IncrementalDecoder")};
      var4 = Py.makeClass("IncrementalNewlineDecoder", var7, IncrementalNewlineDecoder$128);
      var1.setlocal("IncrementalNewlineDecoder", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1447);
      var7 = new PyObject[]{var1.getname("TextIOBase")};
      var4 = Py.makeClass("TextIOWrapper", var7, TextIOWrapper$135);
      var1.setlocal("TextIOWrapper", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1976);
      var7 = new PyObject[]{var1.getname("TextIOWrapper")};
      var4 = Py.makeClass("StringIO", var7, StringIO$168);
      var1.setlocal("StringIO", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BlockingIOError$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyUnicode.fromInterned("Exception raised when I/O would block on a non-blocking I/O stream."));
      var1.setline(34);
      PyUnicode.fromInterned("Exception raised when I/O would block on a non-blocking I/O stream.");
      var1.setline(36);
      PyObject[] var3 = new PyObject[]{Py.newInteger(0)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(37);
      var1.getglobal("super").__call__(var2, var1.getglobal("IOError"), var1.getlocal(0)).__getattr__("__init__").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(38);
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__not__().__nonzero__()) {
         var1.setline(39);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("characters_written must be a integer")));
      } else {
         var1.setline(40);
         PyObject var3 = var1.getlocal(3);
         var1.getlocal(0).__setattr__("characters_written", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject open$3(PyFrame var1, ThreadState var2) {
      var1.setline(153);
      PyUnicode.fromInterned("Open file and return a stream.  Raise IOError upon failure.\n\n    file is either a text or byte string giving the name (and the path\n    if the file isn't in the current working directory) of the file to\n    be opened or an integer file descriptor of the file to be\n    wrapped. (If a file descriptor is given, it is closed when the\n    returned I/O object is closed, unless closefd is set to False.)\n\n    mode is an optional string that specifies the mode in which the file\n    is opened. It defaults to 'r' which means open for reading in text\n    mode.  Other common values are 'w' for writing (truncating the file if\n    it already exists), and 'a' for appending (which on some Unix systems,\n    means that all writes append to the end of the file regardless of the\n    current seek position). In text mode, if encoding is not specified the\n    encoding used is platform dependent. (For reading and writing raw\n    bytes use binary mode and leave encoding unspecified.) The available\n    modes are:\n\n    ========= ===============================================================\n    Character Meaning\n    --------- ---------------------------------------------------------------\n    'r'       open for reading (default)\n    'w'       open for writing, truncating the file first\n    'a'       open for writing, appending to the end of the file if it exists\n    'b'       binary mode\n    't'       text mode (default)\n    '+'       open a disk file for updating (reading and writing)\n    'U'       universal newline mode (for backwards compatibility; unneeded\n              for new code)\n    ========= ===============================================================\n\n    The default mode is 'rt' (open for reading text). For binary random\n    access, the mode 'w+b' opens and truncates the file to 0 bytes, while\n    'r+b' opens the file without truncation.\n\n    Python distinguishes between files opened in binary and text modes,\n    even when the underlying operating system doesn't. Files opened in\n    binary mode (appending 'b' to the mode argument) return contents as\n    bytes objects without any decoding. In text mode (the default, or when\n    't' is appended to the mode argument), the contents of the file are\n    returned as strings, the bytes having been first decoded using a\n    platform-dependent encoding or using the specified encoding if given.\n\n    buffering is an optional integer used to set the buffering policy.\n    Pass 0 to switch buffering off (only allowed in binary mode), 1 to select\n    line buffering (only usable in text mode), and an integer > 1 to indicate\n    the size of a fixed-size chunk buffer.  When no buffering argument is\n    given, the default buffering policy works as follows:\n\n    * Binary files are buffered in fixed-size chunks; the size of the buffer\n      is chosen using a heuristic trying to determine the underlying device's\n      \"block size\" and falling back on `io.DEFAULT_BUFFER_SIZE`.\n      On many systems, the buffer will typically be 4096 or 8192 bytes long.\n\n    * \"Interactive\" text files (files for which isatty() returns True)\n      use line buffering.  Other text files use the policy described above\n      for binary files.\n\n    encoding is the name of the encoding used to decode or encode the\n    file. This should only be used in text mode. The default encoding is\n    platform dependent, but any encoding supported by Python can be\n    passed.  See the codecs module for the list of supported encodings.\n\n    errors is an optional string that specifies how encoding errors are to\n    be handled---this argument should not be used in binary mode. Pass\n    'strict' to raise a ValueError exception if there is an encoding error\n    (the default of None has the same effect), or pass 'ignore' to ignore\n    errors. (Note that ignoring encoding errors can lead to data loss.)\n    See the documentation for codecs.register for a list of the permitted\n    encoding error strings.\n\n    newline controls how universal newlines works (it only applies to text\n    mode). It can be None, '', '\\n', '\\r', and '\\r\\n'.  It works as\n    follows:\n\n    * On input, if newline is None, universal newlines mode is\n      enabled. Lines in the input can end in '\\n', '\\r', or '\\r\\n', and\n      these are translated into '\\n' before being returned to the\n      caller. If it is '', universal newline mode is enabled, but line\n      endings are returned to the caller untranslated. If it has any of\n      the other legal values, input lines are only terminated by the given\n      string, and the line ending is returned to the caller untranslated.\n\n    * On output, if newline is None, any '\\n' characters written are\n      translated to the system default line separator, os.linesep. If\n      newline is '', no translation takes place. If newline is any of the\n      other legal values, any '\\n' characters written are translated to\n      the given string.\n\n    If closefd is False, the underlying file descriptor will be kept open\n    when the file is closed. This does not work when a file name is given\n    and must be True in that case.\n\n    open() returns a file object whose type depends on the mode, and\n    through which the standard file operations such as reading and writing\n    are performed. When open() is used to open a file in a text mode ('w',\n    'r', 'wt', 'rt', etc.), it returns a TextIOWrapper. When used to open\n    a file in a binary mode, the returned class varies: in read binary\n    mode, it returns a BufferedReader; in write binary and append binary\n    modes, it returns a BufferedWriter, and in read/write mode, it returns\n    a BufferedRandom.\n\n    It is also possible to use a string or bytearray as a file for both\n    reading and writing. For strings StringIO can be used like a file\n    opened in a text mode, and for bytes a BytesIO can be used like a file\n    opened in a binary mode.\n    ");
      var1.setline(154);
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("basestring"), var1.getglobal("int"), var1.getglobal("long")}))).__not__().__nonzero__()) {
         var1.setline(155);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyUnicode.fromInterned("invalid file: %r")._mod(var1.getlocal(0))));
      } else {
         var1.setline(156);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__not__().__nonzero__()) {
            var1.setline(157);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyUnicode.fromInterned("invalid mode: %r")._mod(var1.getlocal(1))));
         } else {
            var1.setline(158);
            if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__not__().__nonzero__()) {
               var1.setline(159);
               throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyUnicode.fromInterned("invalid buffering: %r")._mod(var1.getlocal(2))));
            } else {
               var1.setline(160);
               PyObject var3 = var1.getlocal(3);
               PyObject var10000 = var3._isnot(var1.getglobal("None"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("basestring")).__not__();
               }

               if (var10000.__nonzero__()) {
                  var1.setline(161);
                  throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyUnicode.fromInterned("invalid encoding: %r")._mod(var1.getlocal(3))));
               } else {
                  var1.setline(162);
                  var3 = var1.getlocal(4);
                  var10000 = var3._isnot(var1.getglobal("None"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("basestring")).__not__();
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(163);
                     throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyUnicode.fromInterned("invalid errors: %r")._mod(var1.getlocal(4))));
                  } else {
                     var1.setline(164);
                     var3 = var1.getglobal("set").__call__(var2, var1.getlocal(1));
                     var1.setlocal(7, var3);
                     var3 = null;
                     var1.setline(165);
                     var10000 = var1.getlocal(7)._sub(var1.getglobal("set").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("arwb+tU")));
                     if (!var10000.__nonzero__()) {
                        var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
                        var10000 = var3._gt(var1.getglobal("len").__call__(var2, var1.getlocal(7)));
                        var3 = null;
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(166);
                        throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyUnicode.fromInterned("invalid mode: %r")._mod(var1.getlocal(1))));
                     } else {
                        var1.setline(167);
                        PyUnicode var6 = PyUnicode.fromInterned("r");
                        var10000 = var6._in(var1.getlocal(7));
                        var3 = null;
                        var3 = var10000;
                        var1.setlocal(8, var3);
                        var3 = null;
                        var1.setline(168);
                        var6 = PyUnicode.fromInterned("w");
                        var10000 = var6._in(var1.getlocal(7));
                        var3 = null;
                        var3 = var10000;
                        var1.setlocal(9, var3);
                        var3 = null;
                        var1.setline(169);
                        var6 = PyUnicode.fromInterned("a");
                        var10000 = var6._in(var1.getlocal(7));
                        var3 = null;
                        var3 = var10000;
                        var1.setlocal(10, var3);
                        var3 = null;
                        var1.setline(170);
                        var6 = PyUnicode.fromInterned("+");
                        var10000 = var6._in(var1.getlocal(7));
                        var3 = null;
                        var3 = var10000;
                        var1.setlocal(11, var3);
                        var3 = null;
                        var1.setline(171);
                        var6 = PyUnicode.fromInterned("t");
                        var10000 = var6._in(var1.getlocal(7));
                        var3 = null;
                        var3 = var10000;
                        var1.setlocal(12, var3);
                        var3 = null;
                        var1.setline(172);
                        var6 = PyUnicode.fromInterned("b");
                        var10000 = var6._in(var1.getlocal(7));
                        var3 = null;
                        var3 = var10000;
                        var1.setlocal(13, var3);
                        var3 = null;
                        var1.setline(173);
                        var6 = PyUnicode.fromInterned("U");
                        var10000 = var6._in(var1.getlocal(7));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(174);
                           var10000 = var1.getlocal(9);
                           if (!var10000.__nonzero__()) {
                              var10000 = var1.getlocal(10);
                           }

                           if (var10000.__nonzero__()) {
                              var1.setline(175);
                              throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("can't use U and writing mode at once")));
                           }

                           var1.setline(176);
                           var3 = var1.getglobal("True");
                           var1.setlocal(8, var3);
                           var3 = null;
                        }

                        var1.setline(177);
                        var10000 = var1.getlocal(12);
                        if (var10000.__nonzero__()) {
                           var10000 = var1.getlocal(13);
                        }

                        if (var10000.__nonzero__()) {
                           var1.setline(178);
                           throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("can't have text and binary mode at once")));
                        } else {
                           var1.setline(179);
                           var3 = var1.getlocal(8)._add(var1.getlocal(9))._add(var1.getlocal(10));
                           var10000 = var3._gt(Py.newInteger(1));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(180);
                              throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("can't have read/write/append mode at once")));
                           } else {
                              var1.setline(181);
                              var10000 = var1.getlocal(8);
                              if (!var10000.__nonzero__()) {
                                 var10000 = var1.getlocal(9);
                                 if (!var10000.__nonzero__()) {
                                    var10000 = var1.getlocal(10);
                                 }
                              }

                              if (var10000.__not__().__nonzero__()) {
                                 var1.setline(182);
                                 throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("must have exactly one of read/write/append mode")));
                              } else {
                                 var1.setline(183);
                                 var10000 = var1.getlocal(13);
                                 if (var10000.__nonzero__()) {
                                    var3 = var1.getlocal(3);
                                    var10000 = var3._isnot(var1.getglobal("None"));
                                    var3 = null;
                                 }

                                 if (var10000.__nonzero__()) {
                                    var1.setline(184);
                                    throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("binary mode doesn't take an encoding argument")));
                                 } else {
                                    var1.setline(185);
                                    var10000 = var1.getlocal(13);
                                    if (var10000.__nonzero__()) {
                                       var3 = var1.getlocal(4);
                                       var10000 = var3._isnot(var1.getglobal("None"));
                                       var3 = null;
                                    }

                                    if (var10000.__nonzero__()) {
                                       var1.setline(186);
                                       throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("binary mode doesn't take an errors argument")));
                                    } else {
                                       var1.setline(187);
                                       var10000 = var1.getlocal(13);
                                       if (var10000.__nonzero__()) {
                                          var3 = var1.getlocal(5);
                                          var10000 = var3._isnot(var1.getglobal("None"));
                                          var3 = null;
                                       }

                                       if (var10000.__nonzero__()) {
                                          var1.setline(188);
                                          throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("binary mode doesn't take a newline argument")));
                                       } else {
                                          var1.setline(189);
                                          var10000 = var1.getglobal("FileIO");
                                          PyObject var10002 = var1.getlocal(0);
                                          Object var10003 = var1.getlocal(8);
                                          if (((PyObject)var10003).__nonzero__()) {
                                             var10003 = PyUnicode.fromInterned("r");
                                          }

                                          if (!((PyObject)var10003).__nonzero__()) {
                                             var10003 = PyUnicode.fromInterned("");
                                          }

                                          Object var10004 = var1.getlocal(9);
                                          if (((PyObject)var10004).__nonzero__()) {
                                             var10004 = PyUnicode.fromInterned("w");
                                          }

                                          if (!((PyObject)var10004).__nonzero__()) {
                                             var10004 = PyUnicode.fromInterned("");
                                          }

                                          PyObject var10 = ((PyObject)var10003)._add((PyObject)var10004);
                                          var10004 = var1.getlocal(10);
                                          if (((PyObject)var10004).__nonzero__()) {
                                             var10004 = PyUnicode.fromInterned("a");
                                          }

                                          if (!((PyObject)var10004).__nonzero__()) {
                                             var10004 = PyUnicode.fromInterned("");
                                          }

                                          var10 = var10._add((PyObject)var10004);
                                          var10004 = var1.getlocal(11);
                                          if (((PyObject)var10004).__nonzero__()) {
                                             var10004 = PyUnicode.fromInterned("+");
                                          }

                                          if (!((PyObject)var10004).__nonzero__()) {
                                             var10004 = PyUnicode.fromInterned("");
                                          }

                                          var3 = var10000.__call__(var2, var10002, var10._add((PyObject)var10004), var1.getlocal(6));
                                          var1.setlocal(14, var3);
                                          var3 = null;
                                          var1.setline(195);
                                          var3 = var1.getglobal("False");
                                          var1.setlocal(15, var3);
                                          var3 = null;
                                          var1.setline(196);
                                          var3 = var1.getlocal(2);
                                          var10000 = var3._eq(Py.newInteger(1));
                                          var3 = null;
                                          if (!var10000.__nonzero__()) {
                                             var3 = var1.getlocal(2);
                                             var10000 = var3._lt(Py.newInteger(0));
                                             var3 = null;
                                             if (var10000.__nonzero__()) {
                                                var10000 = var1.getlocal(14).__getattr__("isatty").__call__(var2);
                                             }
                                          }

                                          if (var10000.__nonzero__()) {
                                             var1.setline(197);
                                             PyInteger var8 = Py.newInteger(-1);
                                             var1.setlocal(2, var8);
                                             var3 = null;
                                             var1.setline(198);
                                             var3 = var1.getglobal("True");
                                             var1.setlocal(15, var3);
                                             var3 = null;
                                          }

                                          var1.setline(199);
                                          var3 = var1.getlocal(2);
                                          var10000 = var3._lt(Py.newInteger(0));
                                          var3 = null;
                                          PyObject var4;
                                          if (var10000.__nonzero__()) {
                                             label211: {
                                                var1.setline(200);
                                                var3 = var1.getglobal("DEFAULT_BUFFER_SIZE");
                                                var1.setlocal(2, var3);
                                                var3 = null;

                                                try {
                                                   var1.setline(202);
                                                   var3 = var1.getglobal("os").__getattr__("fstat").__call__(var2, var1.getlocal(14).__getattr__("fileno").__call__(var2)).__getattr__("st_blksize");
                                                   var1.setlocal(16, var3);
                                                   var3 = null;
                                                } catch (Throwable var5) {
                                                   PyException var9 = Py.setException(var5, var1);
                                                   if (var9.match(new PyTuple(new PyObject[]{var1.getglobal("os").__getattr__("error"), var1.getglobal("AttributeError")}))) {
                                                      var1.setline(204);
                                                      break label211;
                                                   }

                                                   throw var9;
                                                }

                                                var1.setline(206);
                                                var4 = var1.getlocal(16);
                                                var10000 = var4._gt(Py.newInteger(1));
                                                var4 = null;
                                                if (var10000.__nonzero__()) {
                                                   var1.setline(207);
                                                   var4 = var1.getlocal(16);
                                                   var1.setlocal(2, var4);
                                                   var4 = null;
                                                }
                                             }
                                          }

                                          var1.setline(208);
                                          var3 = var1.getlocal(2);
                                          var10000 = var3._lt(Py.newInteger(0));
                                          var3 = null;
                                          if (var10000.__nonzero__()) {
                                             var1.setline(209);
                                             throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("invalid buffering size")));
                                          } else {
                                             var1.setline(210);
                                             var3 = var1.getlocal(2);
                                             var10000 = var3._eq(Py.newInteger(0));
                                             var3 = null;
                                             if (var10000.__nonzero__()) {
                                                var1.setline(211);
                                                if (var1.getlocal(13).__nonzero__()) {
                                                   var1.setline(212);
                                                   var3 = var1.getlocal(14);
                                                   var1.f_lasti = -1;
                                                   return var3;
                                                } else {
                                                   var1.setline(213);
                                                   throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("can't have unbuffered text I/O")));
                                                }
                                             } else {
                                                var1.setline(214);
                                                if (var1.getlocal(11).__nonzero__()) {
                                                   var1.setline(215);
                                                   var4 = var1.getglobal("BufferedRandom").__call__(var2, var1.getlocal(14), var1.getlocal(2));
                                                   var1.setlocal(17, var4);
                                                   var4 = null;
                                                } else {
                                                   var1.setline(216);
                                                   var10000 = var1.getlocal(9);
                                                   if (!var10000.__nonzero__()) {
                                                      var10000 = var1.getlocal(10);
                                                   }

                                                   if (var10000.__nonzero__()) {
                                                      var1.setline(217);
                                                      var4 = var1.getglobal("BufferedWriter").__call__(var2, var1.getlocal(14), var1.getlocal(2));
                                                      var1.setlocal(17, var4);
                                                      var4 = null;
                                                   } else {
                                                      var1.setline(218);
                                                      if (!var1.getlocal(8).__nonzero__()) {
                                                         var1.setline(221);
                                                         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyUnicode.fromInterned("unknown mode: %r")._mod(var1.getlocal(1))));
                                                      }

                                                      var1.setline(219);
                                                      var4 = var1.getglobal("BufferedReader").__call__(var2, var1.getlocal(14), var1.getlocal(2));
                                                      var1.setlocal(17, var4);
                                                      var4 = null;
                                                   }
                                                }

                                                var1.setline(222);
                                                if (var1.getlocal(13).__nonzero__()) {
                                                   var1.setline(223);
                                                   var3 = var1.getlocal(17);
                                                   var1.f_lasti = -1;
                                                   return var3;
                                                } else {
                                                   var1.setline(224);
                                                   var10000 = var1.getglobal("TextIOWrapper");
                                                   PyObject[] var7 = new PyObject[]{var1.getlocal(17), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(15)};
                                                   var4 = var10000.__call__(var2, var7);
                                                   var1.setlocal(12, var4);
                                                   var4 = null;
                                                   var1.setline(225);
                                                   var4 = var1.getlocal(1);
                                                   var1.getlocal(12).__setattr__("mode", var4);
                                                   var4 = null;
                                                   var1.setline(226);
                                                   var3 = var1.getlocal(12);
                                                   var1.f_lasti = -1;
                                                   return var3;
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject DocDescriptor$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyUnicode.fromInterned("Helper for builtins.open.__doc__\n    "));
      var1.setline(231);
      PyUnicode.fromInterned("Helper for builtins.open.__doc__\n    ");
      var1.setline(232);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __get__$5, (PyObject)null);
      var1.setlocal("__get__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __get__$5(PyFrame var1, ThreadState var2) {
      var1.setline(233);
      PyObject var3 = PyUnicode.fromInterned("open(file, mode='r', buffering=-1, encoding=None, errors=None, newline=None, closefd=True)\n\n")._add(var1.getglobal("open").__getattr__("__doc__"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject OpenWrapper$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyUnicode.fromInterned("Wrapper for builtins.open\n\n    Trick so that open won't become a bound method when stored\n    as a class variable (as dbm.dumb does).\n\n    See initstdio() in Python/pythonrun.c.\n    "));
      var1.setline(245);
      PyUnicode.fromInterned("Wrapper for builtins.open\n\n    Trick so that open won't become a bound method when stored\n    as a class variable (as dbm.dumb does).\n\n    See initstdio() in Python/pythonrun.c.\n    ");
      var1.setline(246);
      PyObject var3 = var1.getname("DocDescriptor").__call__(var2);
      var1.setlocal("__doc__", var3);
      var3 = null;
      var1.setline(248);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __new__$7, (PyObject)null);
      var1.setlocal("__new__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __new__$7(PyFrame var1, ThreadState var2) {
      var1.setline(249);
      PyObject var10000 = var1.getglobal("open");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(1), var1.getlocal(2));
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject UnsupportedOperation$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(253);
      return var1.getf_locals();
   }

   public PyObject IOBase$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(257);
      PyObject var3 = var1.getname("abc").__getattr__("ABCMeta");
      var1.setlocal("__metaclass__", var3);
      var3 = null;
      var1.setline(287);
      PyUnicode.fromInterned("The abstract base class for all I/O classes, acting on streams of\n    bytes. There is no public constructor.\n\n    This class provides dummy implementations for many methods that\n    derived classes can override selectively; the default implementations\n    represent a file that cannot be read, written or seeked.\n\n    Even though IOBase does not declare read, readinto, or write because\n    their signatures will vary, implementations and clients should\n    consider those methods part of the interface. Also, implementations\n    may raise a IOError when operations they do not support are called.\n\n    The basic type used for binary data read from or written to a file is\n    bytes. bytearrays are accepted too, and in some cases (such as\n    readinto) needed. Text I/O classes work with str data.\n\n    Note that calling any method (even inquiries) on a closed stream is\n    undefined. Implementations may raise IOError in this case.\n\n    IOBase (and its subclasses) support the iterator protocol, meaning\n    that an IOBase object can be iterated over yielding the lines in a\n    stream.\n\n    IOBase also supports the :keyword:`with` statement. In this example,\n    fp is closed after the suite of the with statement is complete:\n\n    with open('spam.txt', 'r') as fp:\n        fp.write('Spam and eggs!')\n    ");
      var1.setline(291);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, _unsupported$10, PyUnicode.fromInterned("Internal: raise an exception for unsupported operations."));
      var1.setlocal("_unsupported", var5);
      var3 = null;
      var1.setline(298);
      var4 = new PyObject[]{Py.newInteger(0)};
      var5 = new PyFunction(var1.f_globals, var4, seek$11, PyUnicode.fromInterned("Change stream position.\n\n        Change the stream position to byte offset offset. offset is\n        interpreted relative to the position indicated by whence.  Values\n        for whence are:\n\n        * 0 -- start of stream (the default); offset should be zero or positive\n        * 1 -- current stream position; offset may be negative\n        * 2 -- end of stream; offset is usually negative\n\n        Return the new absolute position.\n        "));
      var1.setlocal("seek", var5);
      var3 = null;
      var1.setline(313);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, tell$12, PyUnicode.fromInterned("Return current stream position."));
      var1.setlocal("tell", var5);
      var3 = null;
      var1.setline(317);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, truncate$13, PyUnicode.fromInterned("Truncate file to size bytes.\n\n        Size defaults to the current IO position as reported by tell().  Return\n        the new size.\n        "));
      var1.setlocal("truncate", var5);
      var3 = null;
      var1.setline(327);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, flush$14, PyUnicode.fromInterned("Flush write buffers, if applicable.\n\n        This is not implemented for read-only and non-blocking streams.\n        "));
      var1.setlocal("flush", var5);
      var3 = null;
      var1.setline(335);
      var3 = var1.getname("False");
      var1.setlocal("_IOBase__closed", var3);
      var3 = null;
      var1.setline(337);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, close$15, PyUnicode.fromInterned("Flush and close the IO object.\n\n        This method has no effect if the file is already closed.\n        "));
      var1.setlocal("close", var5);
      var3 = null;
      var1.setline(348);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __del__$16, PyUnicode.fromInterned("Destructor.  Calls close()."));
      var1.setlocal("__del__", var5);
      var3 = null;
      var1.setline(362);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, seekable$17, PyUnicode.fromInterned("Return whether object supports random access.\n\n        If False, seek(), tell() and truncate() will raise IOError.\n        This method may need to do a test seek().\n        "));
      var1.setlocal("seekable", var5);
      var3 = null;
      var1.setline(370);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, _checkSeekable$18, PyUnicode.fromInterned("Internal: raise an IOError if file is not seekable\n        "));
      var1.setlocal("_checkSeekable", var5);
      var3 = null;
      var1.setline(378);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, readable$19, PyUnicode.fromInterned("Return whether object was opened for reading.\n\n        If False, read() will raise IOError.\n        "));
      var1.setlocal("readable", var5);
      var3 = null;
      var1.setline(385);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, _checkReadable$20, PyUnicode.fromInterned("Internal: raise an IOError if file is not readable\n        "));
      var1.setlocal("_checkReadable", var5);
      var3 = null;
      var1.setline(392);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, writable$21, PyUnicode.fromInterned("Return whether object was opened for writing.\n\n        If False, write() and truncate() will raise IOError.\n        "));
      var1.setlocal("writable", var5);
      var3 = null;
      var1.setline(399);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, _checkWritable$22, PyUnicode.fromInterned("Internal: raise an IOError if file is not writable\n        "));
      var1.setlocal("_checkWritable", var5);
      var3 = null;
      var1.setline(406);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, closed$23, PyUnicode.fromInterned("closed: bool.  True iff the file has been closed.\n\n        For backwards compatibility, this is a property, not a predicate.\n        "));
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("closed", var3);
      var3 = null;
      var1.setline(414);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, _checkClosed$24, PyUnicode.fromInterned("Internal: raise an ValueError if file is closed\n        "));
      var1.setlocal("_checkClosed", var5);
      var3 = null;
      var1.setline(423);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __enter__$25, PyUnicode.fromInterned("Context management protocol.  Returns self."));
      var1.setlocal("__enter__", var5);
      var3 = null;
      var1.setline(428);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __exit__$26, PyUnicode.fromInterned("Context management protocol.  Calls close()"));
      var1.setlocal("__exit__", var5);
      var3 = null;
      var1.setline(436);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, fileno$27, PyUnicode.fromInterned("Returns underlying file descriptor if one exists.\n\n        An IOError is raised if the IO object does not use a file descriptor.\n        "));
      var1.setlocal("fileno", var5);
      var3 = null;
      var1.setline(443);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, isatty$28, PyUnicode.fromInterned("Return whether this is an 'interactive' stream.\n\n        Return False if it can't be determined.\n        "));
      var1.setlocal("isatty", var5);
      var3 = null;
      var1.setline(453);
      var4 = new PyObject[]{Py.newInteger(-1)};
      var5 = new PyFunction(var1.f_globals, var4, readline$29, PyUnicode.fromInterned("Read and return a line from the stream.\n\n        If limit is specified, at most limit bytes will be read.\n\n        The line terminator is always b'\\n' for binary files; for text\n        files, the newlines argument to open can be used to select the line\n        terminator(s) recognized.\n        "));
      var1.setlocal("readline", var5);
      var3 = null;
      var1.setline(489);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __iter__$32, (PyObject)null);
      var1.setlocal("__iter__", var5);
      var3 = null;
      var1.setline(493);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, next$33, (PyObject)null);
      var1.setlocal("next", var5);
      var3 = null;
      var1.setline(499);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, readlines$34, PyUnicode.fromInterned("Return a list of lines from the stream.\n\n        hint can be specified to control the number of lines read: no more\n        lines will be read if the total size (in bytes/characters) of all\n        lines so far exceeds hint.\n        "));
      var1.setlocal("readlines", var5);
      var3 = null;
      var1.setline(519);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, writelines$35, (PyObject)null);
      var1.setlocal("writelines", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _unsupported$10(PyFrame var1, ThreadState var2) {
      var1.setline(292);
      PyUnicode.fromInterned("Internal: raise an exception for unsupported operations.");
      var1.setline(293);
      throw Py.makeException(var1.getglobal("UnsupportedOperation").__call__(var2, PyUnicode.fromInterned("%s.%s() not supported")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"), var1.getlocal(1)}))));
   }

   public PyObject seek$11(PyFrame var1, ThreadState var2) {
      var1.setline(310);
      PyUnicode.fromInterned("Change stream position.\n\n        Change the stream position to byte offset offset. offset is\n        interpreted relative to the position indicated by whence.  Values\n        for whence are:\n\n        * 0 -- start of stream (the default); offset should be zero or positive\n        * 1 -- current stream position; offset may be negative\n        * 2 -- end of stream; offset is usually negative\n\n        Return the new absolute position.\n        ");
      var1.setline(311);
      var1.getlocal(0).__getattr__("_unsupported").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("seek"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tell$12(PyFrame var1, ThreadState var2) {
      var1.setline(314);
      PyUnicode.fromInterned("Return current stream position.");
      var1.setline(315);
      PyObject var3 = var1.getlocal(0).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject truncate$13(PyFrame var1, ThreadState var2) {
      var1.setline(322);
      PyUnicode.fromInterned("Truncate file to size bytes.\n\n        Size defaults to the current IO position as reported by tell().  Return\n        the new size.\n        ");
      var1.setline(323);
      var1.getlocal(0).__getattr__("_unsupported").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("truncate"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject flush$14(PyFrame var1, ThreadState var2) {
      var1.setline(331);
      PyUnicode.fromInterned("Flush write buffers, if applicable.\n\n        This is not implemented for read-only and non-blocking streams.\n        ");
      var1.setline(332);
      var1.getlocal(0).__getattr__("_checkClosed").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$15(PyFrame var1, ThreadState var2) {
      var1.setline(341);
      PyUnicode.fromInterned("Flush and close the IO object.\n\n        This method has no effect if the file is already closed.\n        ");
      var1.setline(342);
      if (var1.getlocal(0).__getattr__("_IOBase__closed").__not__().__nonzero__()) {
         Object var3 = null;

         PyObject var4;
         try {
            var1.setline(344);
            var1.getlocal(0).__getattr__("flush").__call__(var2);
         } catch (Throwable var5) {
            Py.addTraceback(var5, var1);
            var1.setline(346);
            var4 = var1.getglobal("True");
            var1.getlocal(0).__setattr__("_IOBase__closed", var4);
            var4 = null;
            throw (Throwable)var5;
         }

         var1.setline(346);
         var4 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("_IOBase__closed", var4);
         var4 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __del__$16(PyFrame var1, ThreadState var2) {
      var1.setline(349);
      PyUnicode.fromInterned("Destructor.  Calls close().");

      try {
         var1.setline(356);
         var1.getlocal(0).__getattr__("close").__call__(var2);
      } catch (Throwable var4) {
         Py.setException(var4, var1);
         var1.setline(358);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject seekable$17(PyFrame var1, ThreadState var2) {
      var1.setline(367);
      PyUnicode.fromInterned("Return whether object supports random access.\n\n        If False, seek(), tell() and truncate() will raise IOError.\n        This method may need to do a test seek().\n        ");
      var1.setline(368);
      PyObject var3 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _checkSeekable$18(PyFrame var1, ThreadState var2) {
      var1.setline(372);
      PyUnicode.fromInterned("Internal: raise an IOError if file is not seekable\n        ");
      var1.setline(373);
      if (var1.getlocal(0).__getattr__("seekable").__call__(var2).__not__().__nonzero__()) {
         var1.setline(374);
         PyObject var10000 = var1.getglobal("IOError");
         var1.setline(375);
         PyObject var3 = var1.getlocal(1);
         PyObject var10002 = var3._is(var1.getglobal("None"));
         var3 = null;
         throw Py.makeException(var10000.__call__((ThreadState)var2, (PyObject)(var10002.__nonzero__() ? PyUnicode.fromInterned("File or stream is not seekable.") : var1.getlocal(1))));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject readable$19(PyFrame var1, ThreadState var2) {
      var1.setline(382);
      PyUnicode.fromInterned("Return whether object was opened for reading.\n\n        If False, read() will raise IOError.\n        ");
      var1.setline(383);
      PyObject var3 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _checkReadable$20(PyFrame var1, ThreadState var2) {
      var1.setline(387);
      PyUnicode.fromInterned("Internal: raise an IOError if file is not readable\n        ");
      var1.setline(388);
      if (var1.getlocal(0).__getattr__("readable").__call__(var2).__not__().__nonzero__()) {
         var1.setline(389);
         PyObject var10000 = var1.getglobal("IOError");
         var1.setline(390);
         PyObject var3 = var1.getlocal(1);
         PyObject var10002 = var3._is(var1.getglobal("None"));
         var3 = null;
         throw Py.makeException(var10000.__call__((ThreadState)var2, (PyObject)(var10002.__nonzero__() ? PyUnicode.fromInterned("File or stream is not readable.") : var1.getlocal(1))));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject writable$21(PyFrame var1, ThreadState var2) {
      var1.setline(396);
      PyUnicode.fromInterned("Return whether object was opened for writing.\n\n        If False, write() and truncate() will raise IOError.\n        ");
      var1.setline(397);
      PyObject var3 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _checkWritable$22(PyFrame var1, ThreadState var2) {
      var1.setline(401);
      PyUnicode.fromInterned("Internal: raise an IOError if file is not writable\n        ");
      var1.setline(402);
      if (var1.getlocal(0).__getattr__("writable").__call__(var2).__not__().__nonzero__()) {
         var1.setline(403);
         PyObject var10000 = var1.getglobal("IOError");
         var1.setline(404);
         PyObject var3 = var1.getlocal(1);
         PyObject var10002 = var3._is(var1.getglobal("None"));
         var3 = null;
         throw Py.makeException(var10000.__call__((ThreadState)var2, (PyObject)(var10002.__nonzero__() ? PyUnicode.fromInterned("File or stream is not writable.") : var1.getlocal(1))));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject closed$23(PyFrame var1, ThreadState var2) {
      var1.setline(411);
      PyUnicode.fromInterned("closed: bool.  True iff the file has been closed.\n\n        For backwards compatibility, this is a property, not a predicate.\n        ");
      var1.setline(412);
      PyObject var3 = var1.getlocal(0).__getattr__("_IOBase__closed");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _checkClosed$24(PyFrame var1, ThreadState var2) {
      var1.setline(416);
      PyUnicode.fromInterned("Internal: raise an ValueError if file is closed\n        ");
      var1.setline(417);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(418);
         PyObject var10000 = var1.getglobal("ValueError");
         var1.setline(419);
         PyObject var3 = var1.getlocal(1);
         PyObject var10002 = var3._is(var1.getglobal("None"));
         var3 = null;
         throw Py.makeException(var10000.__call__((ThreadState)var2, (PyObject)(var10002.__nonzero__() ? PyUnicode.fromInterned("I/O operation on closed file.") : var1.getlocal(1))));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject __enter__$25(PyFrame var1, ThreadState var2) {
      var1.setline(424);
      PyUnicode.fromInterned("Context management protocol.  Returns self.");
      var1.setline(425);
      var1.getlocal(0).__getattr__("_checkClosed").__call__(var2);
      var1.setline(426);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __exit__$26(PyFrame var1, ThreadState var2) {
      var1.setline(429);
      PyUnicode.fromInterned("Context management protocol.  Calls close()");
      var1.setline(430);
      var1.getlocal(0).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject fileno$27(PyFrame var1, ThreadState var2) {
      var1.setline(440);
      PyUnicode.fromInterned("Returns underlying file descriptor if one exists.\n\n        An IOError is raised if the IO object does not use a file descriptor.\n        ");
      var1.setline(441);
      var1.getlocal(0).__getattr__("_unsupported").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("fileno"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject isatty$28(PyFrame var1, ThreadState var2) {
      var1.setline(447);
      PyUnicode.fromInterned("Return whether this is an 'interactive' stream.\n\n        Return False if it can't be determined.\n        ");
      var1.setline(448);
      var1.getlocal(0).__getattr__("_checkClosed").__call__(var2);
      var1.setline(449);
      PyObject var3 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject readline$29(PyFrame var1, ThreadState var2) {
      var1.to_cell(1, 0);
      var1.to_cell(0, 1);
      var1.setline(461);
      PyUnicode.fromInterned("Read and return a line from the stream.\n\n        If limit is specified, at most limit bytes will be read.\n\n        The line terminator is always b'\\n' for binary files; for text\n        files, the newlines argument to open can be used to select the line\n        terminator(s) recognized.\n        ");
      var1.setline(463);
      PyObject[] var3;
      PyFunction var4;
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getderef(1), (PyObject)PyUnicode.fromInterned("peek")).__nonzero__()) {
         var1.setline(464);
         var3 = Py.EmptyObjects;
         PyObject var10002 = var1.f_globals;
         PyObject[] var10003 = var3;
         PyCode var10004 = nreadahead$30;
         var3 = new PyObject[]{var1.getclosure(1), var1.getclosure(0)};
         var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
         var1.setlocal(2, var4);
         var3 = null;
      } else {
         var1.setline(473);
         var3 = Py.EmptyObjects;
         var4 = new PyFunction(var1.f_globals, var3, nreadahead$31, (PyObject)null);
         var1.setlocal(2, var4);
         var3 = null;
      }

      var1.setline(475);
      PyObject var5 = var1.getderef(0);
      PyObject var10000 = var5._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(476);
         PyInteger var6 = Py.newInteger(-1);
         var1.setderef(0, var6);
         var3 = null;
      } else {
         var1.setline(477);
         if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getderef(0), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__not__().__nonzero__()) {
            var1.setline(478);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("limit must be an integer")));
         }
      }

      var1.setline(479);
      var5 = var1.getglobal("bytearray").__call__(var2);
      var1.setlocal(3, var5);
      var3 = null;

      do {
         var1.setline(480);
         var5 = var1.getderef(0);
         var10000 = var5._lt(Py.newInteger(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var5 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
            var10000 = var5._lt(var1.getderef(0));
            var3 = null;
         }

         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(481);
         var5 = var1.getderef(1).__getattr__("read").__call__(var2, var1.getlocal(2).__call__(var2));
         var1.setlocal(4, var5);
         var3 = null;
         var1.setline(482);
         if (var1.getlocal(4).__not__().__nonzero__()) {
            break;
         }

         var1.setline(484);
         var5 = var1.getlocal(3);
         var5 = var5._iadd(var1.getlocal(4));
         var1.setlocal(3, var5);
         var1.setline(485);
      } while(!var1.getlocal(3).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__nonzero__());

      var1.setline(487);
      var5 = var1.getglobal("bytes").__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject nreadahead$30(PyFrame var1, ThreadState var2) {
      var1.setline(465);
      PyObject var3 = var1.getderef(0).__getattr__("peek").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(466);
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(467);
         PyInteger var5 = Py.newInteger(1);
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(468);
         PyObject var10000 = var1.getlocal(0).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"))._add(Py.newInteger(1));
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
         }

         PyObject var4 = var10000;
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(469);
         var4 = var1.getderef(1);
         var10000 = var4._ge(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(470);
            var4 = var1.getglobal("min").__call__(var2, var1.getlocal(1), var1.getderef(1));
            var1.setlocal(1, var4);
            var4 = null;
         }

         var1.setline(471);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject nreadahead$31(PyFrame var1, ThreadState var2) {
      var1.setline(474);
      PyInteger var3 = Py.newInteger(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __iter__$32(PyFrame var1, ThreadState var2) {
      var1.setline(490);
      var1.getlocal(0).__getattr__("_checkClosed").__call__(var2);
      var1.setline(491);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject next$33(PyFrame var1, ThreadState var2) {
      var1.setline(494);
      PyObject var3 = var1.getlocal(0).__getattr__("readline").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(495);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(496);
         throw Py.makeException(var1.getglobal("StopIteration"));
      } else {
         var1.setline(497);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject readlines$34(PyFrame var1, ThreadState var2) {
      var1.setline(505);
      PyUnicode.fromInterned("Return a list of lines from the stream.\n\n        hint can be specified to control the number of lines read: no more\n        lines will be read if the total size (in bytes/characters) of all\n        lines so far exceeds hint.\n        ");
      var1.setline(506);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(507);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("integer or None expected")));
      } else {
         var1.setline(508);
         var3 = var1.getlocal(1);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(1);
            var10000 = var3._le(Py.newInteger(0));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(509);
            var3 = var1.getglobal("list").__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(510);
            PyInteger var4 = Py.newInteger(0);
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(511);
            PyList var7 = new PyList(Py.EmptyObjects);
            var1.setlocal(3, var7);
            var4 = null;
            var1.setline(512);
            PyObject var8 = var1.getlocal(0).__iter__();

            do {
               var1.setline(512);
               PyObject var5 = var8.__iternext__();
               if (var5 == null) {
                  break;
               }

               var1.setlocal(4, var5);
               var1.setline(513);
               var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(4));
               var1.setline(514);
               PyObject var6 = var1.getlocal(2);
               var6 = var6._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(4)));
               var1.setlocal(2, var6);
               var1.setline(515);
               var6 = var1.getlocal(2);
               var10000 = var6._ge(var1.getlocal(1));
               var6 = null;
            } while(!var10000.__nonzero__());

            var1.setline(517);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject writelines$35(PyFrame var1, ThreadState var2) {
      var1.setline(520);
      var1.getlocal(0).__getattr__("_checkClosed").__call__(var2);
      var1.setline(521);
      PyObject var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(521);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(522);
         var1.getlocal(0).__getattr__("write").__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject RawIOBase$36(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyUnicode.fromInterned("Base class for raw binary I/O."));
      var1.setline(529);
      PyUnicode.fromInterned("Base class for raw binary I/O.");
      var1.setline(541);
      PyObject[] var3 = new PyObject[]{Py.newInteger(-1)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, read$37, PyUnicode.fromInterned("Read and return up to n bytes.\n\n        Returns an empty bytes object on EOF, or None if the object is\n        set not to block and has no data to read.\n        "));
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(558);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, readall$38, PyUnicode.fromInterned("Read until EOF, using multiple read() call."));
      var1.setlocal("readall", var4);
      var3 = null;
      var1.setline(572);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, readinto$39, PyUnicode.fromInterned("Read up to len(b) bytes into b.\n\n        Returns number of bytes read (0 for EOF), or None if the object\n        is set not to block and has no data to read.\n        "));
      var1.setlocal("readinto", var4);
      var3 = null;
      var1.setline(580);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, write$40, PyUnicode.fromInterned("Write the given buffer to the IO stream.\n\n        Returns the number of bytes written, which may be less than len(b).\n        "));
      var1.setlocal("write", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject read$37(PyFrame var1, ThreadState var2) {
      var1.setline(546);
      PyUnicode.fromInterned("Read and return up to n bytes.\n\n        Returns an empty bytes object on EOF, or None if the object is\n        set not to block and has no data to read.\n        ");
      var1.setline(547);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(548);
         PyInteger var5 = Py.newInteger(-1);
         var1.setlocal(1, var5);
         var3 = null;
      }

      var1.setline(549);
      var3 = var1.getlocal(1);
      var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(550);
         var3 = var1.getlocal(0).__getattr__("readall").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(551);
         PyObject var4 = var1.getglobal("bytearray").__call__(var2, var1.getlocal(1).__getattr__("__index__").__call__(var2));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(552);
         var4 = var1.getlocal(0).__getattr__("readinto").__call__(var2, var1.getlocal(2));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(553);
         var4 = var1.getlocal(1);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(554);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(555);
            var1.getlocal(2).__delslice__(var1.getlocal(1), (PyObject)null, (PyObject)null);
            var1.setline(556);
            var3 = var1.getglobal("bytes").__call__(var2, var1.getlocal(2));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject readall$38(PyFrame var1, ThreadState var2) {
      var1.setline(559);
      PyUnicode.fromInterned("Read until EOF, using multiple read() call.");
      var1.setline(560);
      PyObject var3 = var1.getglobal("bytearray").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;

      while(true) {
         var1.setline(561);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(562);
         var3 = var1.getlocal(0).__getattr__("read").__call__(var2, var1.getglobal("DEFAULT_BUFFER_SIZE"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(563);
         if (var1.getlocal(2).__not__().__nonzero__()) {
            break;
         }

         var1.setline(565);
         var3 = var1.getlocal(1);
         var3 = var3._iadd(var1.getlocal(2));
         var1.setlocal(1, var3);
      }

      var1.setline(566);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(567);
         var3 = var1.getglobal("bytes").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(570);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject readinto$39(PyFrame var1, ThreadState var2) {
      var1.setline(577);
      PyUnicode.fromInterned("Read up to len(b) bytes into b.\n\n        Returns number of bytes read (0 for EOF), or None if the object\n        is set not to block and has no data to read.\n        ");
      var1.setline(578);
      var1.getlocal(0).__getattr__("_unsupported").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("readinto"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject write$40(PyFrame var1, ThreadState var2) {
      var1.setline(584);
      PyUnicode.fromInterned("Write the given buffer to the IO stream.\n\n        Returns the number of bytes written, which may be less than len(b).\n        ");
      var1.setline(585);
      var1.getlocal(0).__getattr__("_unsupported").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("write"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BufferedIOBase$41(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyUnicode.fromInterned("Base class for buffered IO objects.\n\n    The main difference with RawIOBase is that the read() method\n    supports omitting the size argument, and does not have a default\n    implementation that defers to readinto().\n\n    In addition, read(), readinto() and write() may raise\n    BlockingIOError if the underlying raw stream is in non-blocking\n    mode and not ready; unlike their raw counterparts, they will never\n    return None.\n\n    A typical implementation should not inherit from a RawIOBase\n    implementation, but wrap one.\n    "));
      var1.setline(607);
      PyUnicode.fromInterned("Base class for buffered IO objects.\n\n    The main difference with RawIOBase is that the read() method\n    supports omitting the size argument, and does not have a default\n    implementation that defers to readinto().\n\n    In addition, read(), readinto() and write() may raise\n    BlockingIOError if the underlying raw stream is in non-blocking\n    mode and not ready; unlike their raw counterparts, they will never\n    return None.\n\n    A typical implementation should not inherit from a RawIOBase\n    implementation, but wrap one.\n    ");
      var1.setline(609);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, read$42, PyUnicode.fromInterned("Read and return up to n bytes.\n\n        If the argument is omitted, None, or negative, reads and\n        returns all data until EOF.\n\n        If the argument is positive, and the underlying raw stream is\n        not 'interactive', multiple raw reads may be issued to satisfy\n        the byte count (unless EOF is reached first).  But for\n        interactive raw streams (XXX and for pipes?), at most one raw\n        read will be issued, and a short result does not imply that\n        EOF is imminent.\n\n        Returns an empty bytes array on EOF.\n\n        Raises BlockingIOError if the underlying raw stream has no\n        data at the moment.\n        "));
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(629);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, read1$43, PyUnicode.fromInterned("Read up to n bytes with at most one read() system call."));
      var1.setlocal("read1", var4);
      var3 = null;
      var1.setline(633);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, readinto$44, PyUnicode.fromInterned("Read up to len(b) bytes into b.\n\n        Like read(), this may issue multiple reads to the underlying raw\n        stream, unless the latter is 'interactive'.\n\n        Returns the number of bytes read (0 for EOF).\n\n        Raises BlockingIOError if the underlying raw stream has no\n        data at the moment.\n        "));
      var1.setlocal("readinto", var4);
      var3 = null;
      var1.setline(656);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, write$45, PyUnicode.fromInterned("Write the given buffer to the IO stream.\n\n        Return the number of bytes written, which is never less than\n        len(b).\n\n        Raises BlockingIOError if the buffer is full and the\n        underlying raw stream cannot accept more data at the moment.\n        "));
      var1.setlocal("write", var4);
      var3 = null;
      var1.setline(667);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, detach$46, PyUnicode.fromInterned("\n        Separate the underlying raw stream from the buffer and return it.\n\n        After the raw stream has been detached, the buffer is in an unusable\n        state.\n        "));
      var1.setlocal("detach", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject read$42(PyFrame var1, ThreadState var2) {
      var1.setline(626);
      PyUnicode.fromInterned("Read and return up to n bytes.\n\n        If the argument is omitted, None, or negative, reads and\n        returns all data until EOF.\n\n        If the argument is positive, and the underlying raw stream is\n        not 'interactive', multiple raw reads may be issued to satisfy\n        the byte count (unless EOF is reached first).  But for\n        interactive raw streams (XXX and for pipes?), at most one raw\n        read will be issued, and a short result does not imply that\n        EOF is imminent.\n\n        Returns an empty bytes array on EOF.\n\n        Raises BlockingIOError if the underlying raw stream has no\n        data at the moment.\n        ");
      var1.setline(627);
      var1.getlocal(0).__getattr__("_unsupported").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("read"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read1$43(PyFrame var1, ThreadState var2) {
      var1.setline(630);
      PyUnicode.fromInterned("Read up to n bytes with at most one read() system call.");
      var1.setline(631);
      var1.getlocal(0).__getattr__("_unsupported").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("read1"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject readinto$44(PyFrame var1, ThreadState var2) {
      var1.setline(643);
      PyUnicode.fromInterned("Read up to len(b) bytes into b.\n\n        Like read(), this may issue multiple reads to the underlying raw\n        stream, unless the latter is 'interactive'.\n\n        Returns the number of bytes read (0 for EOF).\n\n        Raises BlockingIOError if the underlying raw stream has no\n        data at the moment.\n        ");
      var1.setline(645);
      PyObject var3 = var1.getlocal(0).__getattr__("read").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(1)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(646);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;

      try {
         var1.setline(648);
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
         var1.setline(650);
         var4 = imp.importOne("array", var1, -1);
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(651);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getlocal(5).__getattr__("array")).__not__().__nonzero__()) {
            var1.setline(652);
            throw Py.makeException(var1.getlocal(4));
         }

         var1.setline(653);
         var4 = var1.getlocal(5).__getattr__("array").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("b"), (PyObject)var1.getlocal(2));
         var1.getlocal(1).__setslice__((PyObject)null, var1.getlocal(3), (PyObject)null, var4);
         var4 = null;
      }

      var1.setline(654);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject write$45(PyFrame var1, ThreadState var2) {
      var1.setline(664);
      PyUnicode.fromInterned("Write the given buffer to the IO stream.\n\n        Return the number of bytes written, which is never less than\n        len(b).\n\n        Raises BlockingIOError if the buffer is full and the\n        underlying raw stream cannot accept more data at the moment.\n        ");
      var1.setline(665);
      var1.getlocal(0).__getattr__("_unsupported").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("write"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject detach$46(PyFrame var1, ThreadState var2) {
      var1.setline(673);
      PyUnicode.fromInterned("\n        Separate the underlying raw stream from the buffer and return it.\n\n        After the raw stream has been detached, the buffer is in an unusable\n        state.\n        ");
      var1.setline(674);
      var1.getlocal(0).__getattr__("_unsupported").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("detach"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _BufferedIOMixin$47(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyUnicode.fromInterned("A mixin implementation of BufferedIOBase with an underlying raw stream.\n\n    This passes most requests on to the underlying raw stream.  It\n    does *not* provide implementations of read(), readinto() or\n    write().\n    "));
      var1.setline(686);
      PyUnicode.fromInterned("A mixin implementation of BufferedIOBase with an underlying raw stream.\n\n    This passes most requests on to the underlying raw stream.  It\n    does *not* provide implementations of read(), readinto() or\n    write().\n    ");
      var1.setline(688);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$48, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(693);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, seek$49, (PyObject)null);
      var1.setlocal("seek", var4);
      var3 = null;
      var1.setline(699);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tell$50, (PyObject)null);
      var1.setlocal("tell", var4);
      var3 = null;
      var1.setline(705);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, truncate$51, (PyObject)null);
      var1.setlocal("truncate", var4);
      var3 = null;
      var1.setline(719);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, flush$52, (PyObject)null);
      var1.setlocal("flush", var4);
      var3 = null;
      var1.setline(724);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$53, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(732);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, detach$54, (PyObject)null);
      var1.setlocal("detach", var4);
      var3 = null;
      var1.setline(742);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, seekable$55, (PyObject)null);
      var1.setlocal("seekable", var4);
      var3 = null;
      var1.setline(745);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, readable$56, (PyObject)null);
      var1.setlocal("readable", var4);
      var3 = null;
      var1.setline(748);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, writable$57, (PyObject)null);
      var1.setlocal("writable", var4);
      var3 = null;
      var1.setline(751);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, raw$58, (PyObject)null);
      PyObject var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("raw", var5);
      var3 = null;
      var1.setline(755);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, closed$59, (PyObject)null);
      var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("closed", var5);
      var3 = null;
      var1.setline(759);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, name$60, (PyObject)null);
      var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("name", var5);
      var3 = null;
      var1.setline(763);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, mode$61, (PyObject)null);
      var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("mode", var5);
      var3 = null;
      var1.setline(767);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$62, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(778);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, fileno$63, (PyObject)null);
      var1.setlocal("fileno", var4);
      var3 = null;
      var1.setline(781);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isatty$64, (PyObject)null);
      var1.setlocal("isatty", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$48(PyFrame var1, ThreadState var2) {
      var1.setline(689);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_raw", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject seek$49(PyFrame var1, ThreadState var2) {
      var1.setline(694);
      PyObject var3 = var1.getlocal(0).__getattr__("raw").__getattr__("seek").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(695);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(696);
         throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("seek() returned an invalid position")));
      } else {
         var1.setline(697);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject tell$50(PyFrame var1, ThreadState var2) {
      var1.setline(700);
      PyObject var3 = var1.getlocal(0).__getattr__("raw").__getattr__("tell").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(701);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(702);
         throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("tell() returned an invalid position")));
      } else {
         var1.setline(703);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject truncate$51(PyFrame var1, ThreadState var2) {
      var1.setline(709);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(711);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(712);
         var3 = var1.getlocal(0).__getattr__("tell").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(715);
      var3 = var1.getlocal(0).__getattr__("raw").__getattr__("truncate").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject flush$52(PyFrame var1, ThreadState var2) {
      var1.setline(720);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(721);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("flush of closed file")));
      } else {
         var1.setline(722);
         var1.getlocal(0).__getattr__("raw").__getattr__("flush").__call__(var2);
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject close$53(PyFrame var1, ThreadState var2) {
      var1.setline(725);
      PyObject var3 = var1.getlocal(0).__getattr__("raw");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("closed").__not__();
      }

      if (var10000.__nonzero__()) {
         var3 = null;

         try {
            var1.setline(728);
            var1.getlocal(0).__getattr__("flush").__call__(var2);
         } catch (Throwable var4) {
            Py.addTraceback(var4, var1);
            var1.setline(730);
            var1.getlocal(0).__getattr__("raw").__getattr__("close").__call__(var2);
            throw (Throwable)var4;
         }

         var1.setline(730);
         var1.getlocal(0).__getattr__("raw").__getattr__("close").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject detach$54(PyFrame var1, ThreadState var2) {
      var1.setline(733);
      PyObject var3 = var1.getlocal(0).__getattr__("raw");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(734);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("raw stream already detached")));
      } else {
         var1.setline(735);
         var1.getlocal(0).__getattr__("flush").__call__(var2);
         var1.setline(736);
         var3 = var1.getlocal(0).__getattr__("_raw");
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(737);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("_raw", var3);
         var3 = null;
         var1.setline(738);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject seekable$55(PyFrame var1, ThreadState var2) {
      var1.setline(743);
      PyObject var3 = var1.getlocal(0).__getattr__("raw").__getattr__("seekable").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject readable$56(PyFrame var1, ThreadState var2) {
      var1.setline(746);
      PyObject var3 = var1.getlocal(0).__getattr__("raw").__getattr__("readable").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject writable$57(PyFrame var1, ThreadState var2) {
      var1.setline(749);
      PyObject var3 = var1.getlocal(0).__getattr__("raw").__getattr__("writable").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject raw$58(PyFrame var1, ThreadState var2) {
      var1.setline(753);
      PyObject var3 = var1.getlocal(0).__getattr__("_raw");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject closed$59(PyFrame var1, ThreadState var2) {
      var1.setline(757);
      PyObject var3 = var1.getlocal(0).__getattr__("raw").__getattr__("closed");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject name$60(PyFrame var1, ThreadState var2) {
      var1.setline(761);
      PyObject var3 = var1.getlocal(0).__getattr__("raw").__getattr__("name");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject mode$61(PyFrame var1, ThreadState var2) {
      var1.setline(765);
      PyObject var3 = var1.getlocal(0).__getattr__("raw").__getattr__("mode");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$62(PyFrame var1, ThreadState var2) {
      var1.setline(768);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__getattr__("__name__");
      var1.setlocal(1, var3);
      var3 = null;

      PyObject var4;
      try {
         var1.setline(770);
         var3 = var1.getlocal(0).__getattr__("name");
         var1.setlocal(2, var3);
         var3 = null;
      } catch (Throwable var5) {
         PyException var6 = Py.setException(var5, var1);
         if (var6.match(var1.getglobal("AttributeError"))) {
            var1.setline(772);
            var4 = PyUnicode.fromInterned("<_pyio.{0}>").__getattr__("format").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var4;
         }

         throw var6;
      }

      var1.setline(774);
      var4 = PyUnicode.fromInterned("<_pyio.{0} name={1!r}>").__getattr__("format").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject fileno$63(PyFrame var1, ThreadState var2) {
      var1.setline(779);
      PyObject var3 = var1.getlocal(0).__getattr__("raw").__getattr__("fileno").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isatty$64(PyFrame var1, ThreadState var2) {
      var1.setline(782);
      PyObject var3 = var1.getlocal(0).__getattr__("raw").__getattr__("isatty").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject BytesIO$65(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyUnicode.fromInterned("Buffered I/O implementation using an in-memory bytes buffer."));
      var1.setline(787);
      PyUnicode.fromInterned("Buffered I/O implementation using an in-memory bytes buffer.");
      var1.setline(789);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$66, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(796);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getstate__$67, (PyObject)null);
      var1.setlocal("__getstate__", var4);
      var3 = null;
      var1.setline(801);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getvalue$68, PyUnicode.fromInterned("Return the bytes value (contents) of the buffer\n        "));
      var1.setlocal("getvalue", var4);
      var3 = null;
      var1.setline(808);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, read$69, (PyObject)null);
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(825);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read1$70, PyUnicode.fromInterned("This is the same as read.\n        "));
      var1.setlocal("read1", var4);
      var3 = null;
      var1.setline(830);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, write$71, (PyObject)null);
      var1.setlocal("write", var4);
      var3 = null;
      var1.setline(848);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, seek$72, (PyObject)null);
      var1.setlocal("seek", var4);
      var3 = null;
      var1.setline(867);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tell$73, (PyObject)null);
      var1.setlocal("tell", var4);
      var3 = null;
      var1.setline(872);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, truncate$74, (PyObject)null);
      var1.setlocal("truncate", var4);
      var3 = null;
      var1.setline(887);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, readable$75, (PyObject)null);
      var1.setlocal("readable", var4);
      var3 = null;
      var1.setline(892);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, writable$76, (PyObject)null);
      var1.setlocal("writable", var4);
      var3 = null;
      var1.setline(897);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, seekable$77, (PyObject)null);
      var1.setlocal("seekable", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$66(PyFrame var1, ThreadState var2) {
      var1.setline(790);
      PyObject var3 = var1.getglobal("bytearray").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(791);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(792);
         var1.getlocal(2).__getattr__("extend").__call__(var2, var1.getlocal(1));
      }

      var1.setline(793);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_buffer", var3);
      var3 = null;
      var1.setline(794);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_pos", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getstate__$67(PyFrame var1, ThreadState var2) {
      var1.setline(797);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(798);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("__getstate__ on closed file")));
      } else {
         var1.setline(799);
         PyObject var3 = var1.getlocal(0).__getattr__("__dict__").__getattr__("copy").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getvalue$68(PyFrame var1, ThreadState var2) {
      var1.setline(803);
      PyUnicode.fromInterned("Return the bytes value (contents) of the buffer\n        ");
      var1.setline(804);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(805);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("getvalue on closed file")));
      } else {
         var1.setline(806);
         PyObject var3 = var1.getglobal("bytes").__call__(var2, var1.getlocal(0).__getattr__("_buffer"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject read$69(PyFrame var1, ThreadState var2) {
      var1.setline(809);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(810);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("read from closed file")));
      } else {
         var1.setline(811);
         PyObject var3 = var1.getlocal(1);
         PyObject var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(812);
            PyInteger var5 = Py.newInteger(-1);
            var1.setlocal(1, var5);
            var3 = null;
         }

         var1.setline(813);
         if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__not__().__nonzero__()) {
            var1.setline(814);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyUnicode.fromInterned("integer argument expected, got {0!r}").__getattr__("format").__call__(var2, var1.getglobal("type").__call__(var2, var1.getlocal(1)))));
         } else {
            var1.setline(816);
            var3 = var1.getlocal(1);
            var10000 = var3._lt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(817);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_buffer"));
               var1.setlocal(1, var3);
               var3 = null;
            }

            var1.setline(818);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_buffer"));
            var10000 = var3._le(var1.getlocal(0).__getattr__("_pos"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(819);
               PyString var6 = PyString.fromInterned("");
               var1.f_lasti = -1;
               return var6;
            } else {
               var1.setline(820);
               PyObject var4 = var1.getglobal("min").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_buffer")), var1.getlocal(0).__getattr__("_pos")._add(var1.getlocal(1)));
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(821);
               var4 = var1.getlocal(0).__getattr__("_buffer").__getslice__(var1.getlocal(0).__getattr__("_pos"), var1.getlocal(2), (PyObject)null);
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(822);
               var4 = var1.getlocal(2);
               var1.getlocal(0).__setattr__("_pos", var4);
               var4 = null;
               var1.setline(823);
               var3 = var1.getglobal("bytes").__call__(var2, var1.getlocal(3));
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject read1$70(PyFrame var1, ThreadState var2) {
      var1.setline(827);
      PyUnicode.fromInterned("This is the same as read.\n        ");
      var1.setline(828);
      PyObject var3 = var1.getlocal(0).__getattr__("read").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject write$71(PyFrame var1, ThreadState var2) {
      var1.setline(831);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(832);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("write to closed file")));
      } else {
         var1.setline(833);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("unicode")).__nonzero__()) {
            var1.setline(834);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("can't write unicode to binary stream")));
         } else {
            var1.setline(835);
            PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(836);
            var3 = var1.getlocal(2);
            PyObject var10000 = var3._eq(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(837);
               PyInteger var7 = Py.newInteger(0);
               var1.f_lasti = -1;
               return var7;
            } else {
               var1.setline(838);
               PyObject var4 = var1.getlocal(0).__getattr__("_pos");
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(839);
               var4 = var1.getlocal(3);
               var10000 = var4._gt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_buffer")));
               var4 = null;
               PyObject var5;
               PyObject var6;
               String var8;
               if (var10000.__nonzero__()) {
                  var1.setline(842);
                  var4 = PyString.fromInterned("\u0000")._mul(var1.getlocal(3)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_buffer"))));
                  var1.setlocal(4, var4);
                  var4 = null;
                  var1.setline(843);
                  var10000 = var1.getlocal(0);
                  var8 = "_buffer";
                  var5 = var10000;
                  var6 = var5.__getattr__(var8);
                  var6 = var6._iadd(var1.getlocal(4));
                  var5.__setattr__(var8, var6);
               }

               var1.setline(844);
               var4 = var1.getlocal(1);
               var1.getlocal(0).__getattr__("_buffer").__setslice__(var1.getlocal(3), var1.getlocal(3)._add(var1.getlocal(2)), (PyObject)null, var4);
               var4 = null;
               var1.setline(845);
               var10000 = var1.getlocal(0);
               var8 = "_pos";
               var5 = var10000;
               var6 = var5.__getattr__(var8);
               var6 = var6._iadd(var1.getlocal(2));
               var5.__setattr__(var8, var6);
               var1.setline(846);
               var3 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject seek$72(PyFrame var1, ThreadState var2) {
      var1.setline(849);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(850);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("seek on closed file")));
      } else {
         PyException var3;
         try {
            var1.setline(852);
            var1.getlocal(1).__getattr__("__index__");
         } catch (Throwable var4) {
            var3 = Py.setException(var4, var1);
            if (var3.match(var1.getglobal("AttributeError"))) {
               var1.setline(854);
               throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("an integer is required")));
            }

            throw var3;
         }

         var1.setline(855);
         PyObject var5 = var1.getlocal(2);
         PyObject var10000 = var5._eq(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(856);
            var5 = var1.getlocal(1);
            var10000 = var5._lt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(857);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyUnicode.fromInterned("negative seek position %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(1)}))));
            }

            var1.setline(858);
            var5 = var1.getlocal(1);
            var1.getlocal(0).__setattr__("_pos", var5);
            var3 = null;
         } else {
            var1.setline(859);
            var5 = var1.getlocal(2);
            var10000 = var5._eq(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(860);
               var5 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(0).__getattr__("_pos")._add(var1.getlocal(1)));
               var1.getlocal(0).__setattr__("_pos", var5);
               var3 = null;
            } else {
               var1.setline(861);
               var5 = var1.getlocal(2);
               var10000 = var5._eq(Py.newInteger(2));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(864);
                  throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("invalid whence value")));
               }

               var1.setline(862);
               var5 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_buffer"))._add(var1.getlocal(1)));
               var1.getlocal(0).__setattr__("_pos", var5);
               var3 = null;
            }
         }

         var1.setline(865);
         var5 = var1.getlocal(0).__getattr__("_pos");
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject tell$73(PyFrame var1, ThreadState var2) {
      var1.setline(868);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(869);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("tell on closed file")));
      } else {
         var1.setline(870);
         PyObject var3 = var1.getlocal(0).__getattr__("_pos");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject truncate$74(PyFrame var1, ThreadState var2) {
      var1.setline(873);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(874);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("truncate on closed file")));
      } else {
         var1.setline(875);
         PyObject var3 = var1.getlocal(1);
         PyObject var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(876);
            var3 = var1.getlocal(0).__getattr__("_pos");
            var1.setlocal(1, var3);
            var3 = null;
         } else {
            try {
               var1.setline(879);
               var1.getlocal(1).__getattr__("__index__");
            } catch (Throwable var4) {
               PyException var5 = Py.setException(var4, var1);
               if (var5.match(var1.getglobal("AttributeError"))) {
                  var1.setline(881);
                  throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("an integer is required")));
               }

               throw var5;
            }

            var1.setline(882);
            var3 = var1.getlocal(1);
            var10000 = var3._lt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(883);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyUnicode.fromInterned("negative truncate position %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(1)}))));
            }
         }

         var1.setline(884);
         var1.getlocal(0).__getattr__("_buffer").__delslice__(var1.getlocal(1), (PyObject)null, (PyObject)null);
         var1.setline(885);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject readable$75(PyFrame var1, ThreadState var2) {
      var1.setline(888);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(889);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("I/O operation on closed file.")));
      } else {
         var1.setline(890);
         PyObject var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject writable$76(PyFrame var1, ThreadState var2) {
      var1.setline(893);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(894);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("I/O operation on closed file.")));
      } else {
         var1.setline(895);
         PyObject var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject seekable$77(PyFrame var1, ThreadState var2) {
      var1.setline(898);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(899);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("I/O operation on closed file.")));
      } else {
         var1.setline(900);
         PyObject var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject BufferedReader$78(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyUnicode.fromInterned("BufferedReader(raw[, buffer_size])\n\n    A buffer for a readable, sequential BaseRawIO object.\n\n    The constructor creates a BufferedReader for the given readable raw\n    stream and buffer_size. If buffer_size is omitted, DEFAULT_BUFFER_SIZE\n    is used.\n    "));
      var1.setline(912);
      PyUnicode.fromInterned("BufferedReader(raw[, buffer_size])\n\n    A buffer for a readable, sequential BaseRawIO object.\n\n    The constructor creates a BufferedReader for the given readable raw\n    stream and buffer_size. If buffer_size is omitted, DEFAULT_BUFFER_SIZE\n    is used.\n    ");
      var1.setline(914);
      PyObject[] var3 = new PyObject[]{var1.getname("DEFAULT_BUFFER_SIZE")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$79, PyUnicode.fromInterned("Create a new buffered reader using the given readable raw IO object.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(927);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _reset_read_buf$80, (PyObject)null);
      var1.setlocal("_reset_read_buf", var4);
      var3 = null;
      var1.setline(931);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, read$81, PyUnicode.fromInterned("Read n bytes.\n\n        Returns exactly n bytes of data unless the underlying raw IO\n        stream reaches EOF or if the call would block in non-blocking\n        mode. If n is negative, read until EOF or until read() would\n        block.\n        "));
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(944);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, _read_unlocked$82, (PyObject)null);
      var1.setlocal("_read_unlocked", var4);
      var3 = null;
      var1.setline(1000);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, peek$83, PyUnicode.fromInterned("Returns buffered bytes without advancing the position.\n\n        The argument indicates a desired minimal number of bytes; we\n        do at most one raw read to satisfy it.  We never return more\n        than self.buffer_size.\n        "));
      var1.setlocal("peek", var4);
      var3 = null;
      var1.setline(1010);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, _peek_unlocked$84, (PyObject)null);
      var1.setlocal("_peek_unlocked", var4);
      var3 = null;
      var1.setline(1028);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read1$85, PyUnicode.fromInterned("Reads up to n bytes, with at most one read() system call."));
      var1.setlocal("read1", var4);
      var3 = null;
      var1.setline(1041);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tell$86, (PyObject)null);
      var1.setlocal("tell", var4);
      var3 = null;
      var1.setline(1044);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, seek$87, (PyObject)null);
      var1.setlocal("seek", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$79(PyFrame var1, ThreadState var2) {
      var1.setline(916);
      PyUnicode.fromInterned("Create a new buffered reader using the given readable raw IO object.\n        ");
      var1.setline(917);
      if (var1.getlocal(1).__getattr__("readable").__call__(var2).__not__().__nonzero__()) {
         var1.setline(918);
         throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\"raw\" argument must be readable.")));
      } else {
         var1.setline(920);
         var1.getglobal("_BufferedIOMixin").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.setline(921);
         PyObject var3 = var1.getlocal(2);
         PyObject var10000 = var3._le(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(922);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("invalid buffer size")));
         } else {
            var1.setline(923);
            var3 = var1.getlocal(2);
            var1.getlocal(0).__setattr__("buffer_size", var3);
            var3 = null;
            var1.setline(924);
            var1.getlocal(0).__getattr__("_reset_read_buf").__call__(var2);
            var1.setline(925);
            var3 = var1.getglobal("Lock").__call__(var2);
            var1.getlocal(0).__setattr__("_read_lock", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject _reset_read_buf$80(PyFrame var1, ThreadState var2) {
      var1.setline(928);
      PyString var3 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"_read_buf", var3);
      var3 = null;
      var1.setline(929);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_read_pos", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read$81(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(938);
      PyUnicode.fromInterned("Read n bytes.\n\n        Returns exactly n bytes of data unless the underlying raw IO\n        stream reaches EOF or if the call would block in non-blocking\n        mode. If n is negative, read until EOF or until read() would\n        block.\n        ");
      var1.setline(939);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._lt(Py.newInteger(-1));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(940);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("invalid number of bytes to read")));
      } else {
         ContextManager var7;
         PyObject var4 = (var7 = ContextGuard.getManager(var1.getlocal(0).__getattr__("_read_lock"))).__enter__(var2);

         Throwable var8;
         label36: {
            boolean var10001;
            try {
               var1.setline(942);
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

   public PyObject _read_unlocked$82(PyFrame var1, ThreadState var2) {
      var1.setline(945);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(946);
      PyTuple var9 = new PyTuple(new PyObject[]{PyString.fromInterned(""), var1.getglobal("None")});
      var1.setlocal(3, var9);
      var3 = null;
      var1.setline(947);
      PyObject var10 = var1.getlocal(0).__getattr__("_read_buf");
      var1.setlocal(4, var10);
      var3 = null;
      var1.setline(948);
      var10 = var1.getlocal(0).__getattr__("_read_pos");
      var1.setlocal(5, var10);
      var3 = null;
      var1.setline(951);
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
         var1.setline(952);
         var1.getlocal(0).__getattr__("_reset_read_buf").__call__(var2);
         var1.setline(953);
         PyList var13 = new PyList(new PyObject[]{var1.getlocal(4).__getslice__(var1.getlocal(5), (PyObject)null, (PyObject)null)});
         var1.setlocal(6, var13);
         var3 = null;
         var1.setline(954);
         PyInteger var14 = Py.newInteger(0);
         var1.setlocal(7, var14);
         var3 = null;

         while(true) {
            var1.setline(955);
            if (!var1.getglobal("True").__nonzero__()) {
               break;
            }

            try {
               var1.setline(958);
               var10 = var1.getlocal(0).__getattr__("raw").__getattr__("read").__call__(var2);
               var1.setlocal(8, var10);
               var3 = null;
            } catch (Throwable var7) {
               PyException var15 = Py.setException(var7, var1);
               if (var15.match(var1.getglobal("IOError"))) {
                  var4 = var15.value;
                  var1.setlocal(9, var4);
                  var4 = null;
                  var1.setline(960);
                  var4 = var1.getlocal(9).__getattr__("errno");
                  var10000 = var4._ne(var1.getglobal("EINTR"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(961);
                     throw Py.makeException();
                  }
                  continue;
               }

               throw var15;
            }

            var1.setline(963);
            var10 = var1.getlocal(8);
            var10000 = var10._in(var1.getlocal(3));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(964);
               var10 = var1.getlocal(8);
               var1.setlocal(2, var10);
               var3 = null;
               break;
            }

            var1.setline(966);
            var10 = var1.getlocal(7);
            var10 = var10._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(8)));
            var1.setlocal(7, var10);
            var1.setline(967);
            var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(8));
         }

         var1.setline(968);
         var10000 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(6));
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(2);
         }

         var10 = var10000;
         var1.f_lasti = -1;
         return var10;
      } else {
         var1.setline(971);
         var4 = var1.getglobal("len").__call__(var2, var1.getlocal(4))._sub(var1.getlocal(5));
         var1.setlocal(10, var4);
         var4 = null;
         var1.setline(972);
         var4 = var1.getlocal(1);
         var10000 = var4._le(var1.getlocal(10));
         var4 = null;
         PyObject var5;
         if (var10000.__nonzero__()) {
            var1.setline(974);
            var10000 = var1.getlocal(0);
            String var17 = "_read_pos";
            var5 = var10000;
            PyObject var6 = var5.__getattr__(var17);
            var6 = var6._iadd(var1.getlocal(1));
            var5.__setattr__(var17, var6);
            var1.setline(975);
            var10 = var1.getlocal(4).__getslice__(var1.getlocal(5), var1.getlocal(5)._add(var1.getlocal(1)), (PyObject)null);
            var1.f_lasti = -1;
            return var10;
         } else {
            var1.setline(978);
            PyList var11 = new PyList(new PyObject[]{var1.getlocal(4).__getslice__(var1.getlocal(5), (PyObject)null, (PyObject)null)});
            var1.setlocal(6, var11);
            var4 = null;
            var1.setline(979);
            var4 = var1.getglobal("max").__call__(var2, var1.getlocal(0).__getattr__("buffer_size"), var1.getlocal(1));
            var1.setlocal(11, var4);
            var4 = null;

            while(true) {
               var1.setline(980);
               var4 = var1.getlocal(10);
               var10000 = var4._lt(var1.getlocal(1));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  break;
               }

               try {
                  var1.setline(982);
                  var4 = var1.getlocal(0).__getattr__("raw").__getattr__("read").__call__(var2, var1.getlocal(11));
                  var1.setlocal(8, var4);
                  var4 = null;
               } catch (Throwable var8) {
                  PyException var12 = Py.setException(var8, var1);
                  if (var12.match(var1.getglobal("IOError"))) {
                     var5 = var12.value;
                     var1.setlocal(9, var5);
                     var5 = null;
                     var1.setline(984);
                     var5 = var1.getlocal(9).__getattr__("errno");
                     var10000 = var5._ne(var1.getglobal("EINTR"));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(985);
                        throw Py.makeException();
                     }
                     continue;
                  }

                  throw var12;
               }

               var1.setline(987);
               var4 = var1.getlocal(8);
               var10000 = var4._in(var1.getlocal(3));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(988);
                  var4 = var1.getlocal(8);
                  var1.setlocal(2, var4);
                  var4 = null;
                  break;
               }

               var1.setline(990);
               var4 = var1.getlocal(10);
               var4 = var4._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(8)));
               var1.setlocal(10, var4);
               var1.setline(991);
               var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(8));
            }

            var1.setline(994);
            var4 = var1.getglobal("min").__call__(var2, var1.getlocal(1), var1.getlocal(10));
            var1.setlocal(1, var4);
            var4 = null;
            var1.setline(995);
            var4 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(6));
            var1.setlocal(12, var4);
            var4 = null;
            var1.setline(996);
            var4 = var1.getlocal(12).__getslice__(var1.getlocal(1), (PyObject)null, (PyObject)null);
            var1.getlocal(0).__setattr__("_read_buf", var4);
            var4 = null;
            var1.setline(997);
            PyInteger var16 = Py.newInteger(0);
            var1.getlocal(0).__setattr__((String)"_read_pos", var16);
            var4 = null;
            var1.setline(998);
            var1.setline(998);
            var10 = var1.getlocal(12).__nonzero__() ? var1.getlocal(12).__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null) : var1.getlocal(2);
            var1.f_lasti = -1;
            return var10;
         }
      }
   }

   public PyObject peek$83(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(1006);
      PyUnicode.fromInterned("Returns buffered bytes without advancing the position.\n\n        The argument indicates a desired minimal number of bytes; we\n        do at most one raw read to satisfy it.  We never return more\n        than self.buffer_size.\n        ");
      ContextManager var3;
      PyObject var4 = (var3 = ContextGuard.getManager(var1.getlocal(0).__getattr__("_read_lock"))).__enter__(var2);

      Throwable var10000;
      label28: {
         boolean var10001;
         try {
            var1.setline(1008);
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

   public PyObject _peek_unlocked$84(PyFrame var1, ThreadState var2) {
      var1.setline(1011);
      PyObject var3 = var1.getglobal("min").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("buffer_size"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1012);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_read_buf"))._sub(var1.getlocal(0).__getattr__("_read_pos"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1013);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._lt(var1.getlocal(2));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(3);
         var10000 = var3._le(Py.newInteger(0));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1014);
         var3 = var1.getlocal(0).__getattr__("buffer_size")._sub(var1.getlocal(3));
         var1.setlocal(4, var3);
         var3 = null;

         while(true) {
            var1.setline(1015);
            if (!var1.getglobal("True").__nonzero__()) {
               break;
            }

            try {
               var1.setline(1017);
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
               var1.setline(1019);
               var4 = var1.getlocal(6).__getattr__("errno");
               var10000 = var4._ne(var1.getglobal("EINTR"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1020);
                  throw Py.makeException();
               }
            }
         }

         var1.setline(1023);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(1024);
            var3 = var1.getlocal(0).__getattr__("_read_buf").__getslice__(var1.getlocal(0).__getattr__("_read_pos"), (PyObject)null, (PyObject)null)._add(var1.getlocal(5));
            var1.getlocal(0).__setattr__("_read_buf", var3);
            var3 = null;
            var1.setline(1025);
            PyInteger var7 = Py.newInteger(0);
            var1.getlocal(0).__setattr__((String)"_read_pos", var7);
            var3 = null;
         }
      }

      var1.setline(1026);
      var3 = var1.getlocal(0).__getattr__("_read_buf").__getslice__(var1.getlocal(0).__getattr__("_read_pos"), (PyObject)null, (PyObject)null);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject read1$85(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(1029);
      PyUnicode.fromInterned("Reads up to n bytes, with at most one read() system call.");
      var1.setline(1032);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1033);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("number of bytes to read must be positive")));
      } else {
         var1.setline(1034);
         var3 = var1.getlocal(1);
         var10000 = var3._eq(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1035);
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
                  var1.setline(1037);
                  var1.getlocal(0).__getattr__("_peek_unlocked").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
                  var1.setline(1038);
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

   public PyObject tell$86(PyFrame var1, ThreadState var2) {
      var1.setline(1042);
      PyObject var3 = var1.getglobal("_BufferedIOMixin").__getattr__("tell").__call__(var2, var1.getlocal(0))._sub(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_read_buf")))._add(var1.getlocal(0).__getattr__("_read_pos"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject seek$87(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(1045);
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
         var1.setline(1046);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("invalid whence value")));
      } else {
         ContextManager var8;
         var4 = (var8 = ContextGuard.getManager(var1.getlocal(0).__getattr__("_read_lock"))).__enter__(var2);

         Throwable var9;
         label42: {
            boolean var11;
            try {
               var1.setline(1048);
               var4 = var1.getlocal(2);
               PyObject var10 = var4._eq(Py.newInteger(1));
               var4 = null;
               if (var10.__nonzero__()) {
                  var1.setline(1049);
                  var4 = var1.getlocal(1);
                  var4 = var4._isub(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_read_buf"))._sub(var1.getlocal(0).__getattr__("_read_pos")));
                  var1.setlocal(1, var4);
               }

               var1.setline(1050);
               var4 = var1.getglobal("_BufferedIOMixin").__getattr__("seek").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
               var1.setlocal(1, var4);
               var4 = null;
               var1.setline(1051);
               var1.getlocal(0).__getattr__("_reset_read_buf").__call__(var2);
               var1.setline(1052);
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

   public PyObject BufferedWriter$88(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyUnicode.fromInterned("A buffer for a writeable sequential RawIO object.\n\n    The constructor creates a BufferedWriter for the given writeable raw\n    stream. If the buffer_size is not given, it defaults to\n    DEFAULT_BUFFER_SIZE.\n    "));
      var1.setline(1061);
      PyUnicode.fromInterned("A buffer for a writeable sequential RawIO object.\n\n    The constructor creates a BufferedWriter for the given writeable raw\n    stream. If the buffer_size is not given, it defaults to\n    DEFAULT_BUFFER_SIZE.\n    ");
      var1.setline(1063);
      PyInteger var3 = Py.newInteger(2);
      var1.setlocal("_warning_stack_offset", var3);
      var3 = null;
      var1.setline(1065);
      PyObject[] var4 = new PyObject[]{var1.getname("DEFAULT_BUFFER_SIZE"), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$89, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(1080);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, write$90, (PyObject)null);
      var1.setlocal("write", var5);
      var3 = null;
      var1.setline(1108);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, truncate$91, (PyObject)null);
      var1.setlocal("truncate", var5);
      var3 = null;
      var1.setline(1115);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, flush$92, (PyObject)null);
      var1.setlocal("flush", var5);
      var3 = null;
      var1.setline(1119);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _flush_unlocked$93, (PyObject)null);
      var1.setlocal("_flush_unlocked", var5);
      var3 = null;
      var1.setline(1140);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, tell$94, (PyObject)null);
      var1.setlocal("tell", var5);
      var3 = null;
      var1.setline(1143);
      var4 = new PyObject[]{Py.newInteger(0)};
      var5 = new PyFunction(var1.f_globals, var4, seek$95, (PyObject)null);
      var1.setlocal("seek", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$89(PyFrame var1, ThreadState var2) {
      var1.setline(1067);
      if (var1.getlocal(1).__getattr__("writable").__call__(var2).__not__().__nonzero__()) {
         var1.setline(1068);
         throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\"raw\" argument must be writable.")));
      } else {
         var1.setline(1070);
         var1.getglobal("_BufferedIOMixin").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.setline(1071);
         PyObject var3 = var1.getlocal(2);
         PyObject var10000 = var3._le(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1072);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("invalid buffer size")));
         } else {
            var1.setline(1073);
            var3 = var1.getlocal(3);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1074);
               var1.getglobal("warnings").__getattr__("warn").__call__((ThreadState)var2, PyUnicode.fromInterned("max_buffer_size is deprecated"), (PyObject)var1.getglobal("DeprecationWarning"), (PyObject)var1.getlocal(0).__getattr__("_warning_stack_offset"));
            }

            var1.setline(1076);
            var3 = var1.getlocal(2);
            var1.getlocal(0).__setattr__("buffer_size", var3);
            var3 = null;
            var1.setline(1077);
            var3 = var1.getglobal("bytearray").__call__(var2);
            var1.getlocal(0).__setattr__("_write_buf", var3);
            var3 = null;
            var1.setline(1078);
            var3 = var1.getglobal("Lock").__call__(var2);
            var1.getlocal(0).__setattr__("_write_lock", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject write$90(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(1081);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(1082);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("write to closed file")));
      } else {
         var1.setline(1083);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("unicode")).__nonzero__()) {
            var1.setline(1084);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("can't write unicode to binary stream")));
         } else {
            ContextManager var3;
            PyObject var4 = (var3 = ContextGuard.getManager(var1.getlocal(0).__getattr__("_write_lock"))).__enter__(var2);

            Throwable var10000;
            label59: {
               boolean var10001;
               try {
                  var1.setline(1088);
                  var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_write_buf"));
                  PyObject var10 = var4._gt(var1.getlocal(0).__getattr__("buffer_size"));
                  var4 = null;
                  if (var10.__nonzero__()) {
                     var1.setline(1091);
                     var1.getlocal(0).__getattr__("_flush_unlocked").__call__(var2);
                  }

                  var1.setline(1092);
                  var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_write_buf"));
                  var1.setlocal(2, var4);
                  var4 = null;
                  var1.setline(1093);
                  var1.getlocal(0).__getattr__("_write_buf").__getattr__("extend").__call__(var2, var1.getlocal(1));
                  var1.setline(1094);
                  var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_write_buf"))._sub(var1.getlocal(2));
                  var1.setlocal(3, var4);
                  var4 = null;
                  var1.setline(1095);
                  var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_write_buf"));
                  var10 = var4._gt(var1.getlocal(0).__getattr__("buffer_size"));
                  var4 = null;
                  if (var10.__nonzero__()) {
                     try {
                        var1.setline(1097);
                        var1.getlocal(0).__getattr__("_flush_unlocked").__call__(var2);
                     } catch (Throwable var6) {
                        PyException var9 = Py.setException(var6, var1);
                        if (!var9.match(var1.getglobal("BlockingIOError"))) {
                           throw var9;
                        }

                        PyObject var5 = var9.value;
                        var1.setlocal(4, var5);
                        var5 = null;
                        var1.setline(1099);
                        var5 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_write_buf"));
                        var10 = var5._gt(var1.getlocal(0).__getattr__("buffer_size"));
                        var5 = null;
                        if (var10.__nonzero__()) {
                           var1.setline(1102);
                           var5 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_write_buf"))._sub(var1.getlocal(0).__getattr__("buffer_size"));
                           var1.setlocal(5, var5);
                           var5 = null;
                           var1.setline(1103);
                           var5 = var1.getlocal(3);
                           var5 = var5._isub(var1.getlocal(5));
                           var1.setlocal(3, var5);
                           var1.setline(1104);
                           var5 = var1.getlocal(0).__getattr__("_write_buf").__getslice__((PyObject)null, var1.getlocal(0).__getattr__("buffer_size"), (PyObject)null);
                           var1.getlocal(0).__setattr__("_write_buf", var5);
                           var5 = null;
                           var1.setline(1105);
                           throw Py.makeException(var1.getglobal("BlockingIOError").__call__(var2, var1.getlocal(4).__getattr__("errno"), var1.getlocal(4).__getattr__("strerror"), var1.getlocal(3)));
                        }
                     }
                  }

                  var1.setline(1106);
                  var4 = var1.getlocal(3);
               } catch (Throwable var8) {
                  var10000 = var8;
                  var10001 = false;
                  break label59;
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

   public PyObject truncate$91(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      ContextManager var3;
      PyObject var4 = (var3 = ContextGuard.getManager(var1.getlocal(0).__getattr__("_write_lock"))).__enter__(var2);

      Throwable var10000;
      label34: {
         boolean var10001;
         try {
            var1.setline(1110);
            var1.getlocal(0).__getattr__("_flush_unlocked").__call__(var2);
            var1.setline(1111);
            var4 = var1.getlocal(1);
            PyObject var7 = var4._is(var1.getglobal("None"));
            var4 = null;
            if (var7.__nonzero__()) {
               var1.setline(1112);
               var4 = var1.getlocal(0).__getattr__("raw").__getattr__("tell").__call__(var2);
               var1.setlocal(1, var4);
               var4 = null;
            }

            var1.setline(1113);
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

   public PyObject flush$92(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      ContextManager var3;
      PyObject var4 = (var3 = ContextGuard.getManager(var1.getlocal(0).__getattr__("_write_lock"))).__enter__(var2);

      label16: {
         try {
            var1.setline(1117);
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

   public PyObject _flush_unlocked$93(PyFrame var1, ThreadState var2) {
      var1.setline(1120);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(1121);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("flush of closed file")));
      } else {
         while(true) {
            var1.setline(1122);
            if (!var1.getlocal(0).__getattr__("_write_buf").__nonzero__()) {
               var1.f_lasti = -1;
               return Py.None;
            }

            PyObject var10000;
            PyException var3;
            PyObject var6;
            try {
               var1.setline(1124);
               var6 = var1.getlocal(0).__getattr__("raw").__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("_write_buf"));
               var1.setlocal(1, var6);
               var3 = null;
            } catch (Throwable var5) {
               var3 = Py.setException(var5, var1);
               if (var3.match(var1.getglobal("BlockingIOError"))) {
                  var1.setline(1126);
                  throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("self.raw should implement RawIOBase: it should not raise BlockingIOError")));
               }

               if (!var3.match(var1.getglobal("IOError"))) {
                  throw var3;
               }

               PyObject var4 = var3.value;
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(1129);
               var4 = var1.getlocal(2).__getattr__("errno");
               var10000 = var4._ne(var1.getglobal("EINTR"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1130);
                  throw Py.makeException();
               }
               continue;
            }

            var1.setline(1132);
            var6 = var1.getlocal(1);
            var10000 = var6._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1133);
               throw Py.makeException(var1.getglobal("BlockingIOError").__call__((ThreadState)var2, var1.getglobal("errno").__getattr__("EAGAIN"), (PyObject)PyUnicode.fromInterned("write could not complete without blocking"), (PyObject)Py.newInteger(0)));
            }

            var1.setline(1136);
            var6 = var1.getlocal(1);
            var10000 = var6._gt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_write_buf")));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var6 = var1.getlocal(1);
               var10000 = var6._lt(Py.newInteger(0));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(1137);
               throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("write() returned incorrect number of bytes")));
            }

            var1.setline(1138);
            var1.getlocal(0).__getattr__("_write_buf").__delslice__((PyObject)null, var1.getlocal(1), (PyObject)null);
         }
      }
   }

   public PyObject tell$94(PyFrame var1, ThreadState var2) {
      var1.setline(1141);
      PyObject var3 = var1.getglobal("_BufferedIOMixin").__getattr__("tell").__call__(var2, var1.getlocal(0))._add(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_write_buf")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject seek$95(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(1144);
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
         var1.setline(1145);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("invalid whence")));
      } else {
         ContextManager var8;
         var4 = (var8 = ContextGuard.getManager(var1.getlocal(0).__getattr__("_write_lock"))).__enter__(var2);

         Throwable var9;
         label36: {
            boolean var10;
            try {
               var1.setline(1147);
               var1.getlocal(0).__getattr__("_flush_unlocked").__call__(var2);
               var1.setline(1148);
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

   public PyObject BufferedRWPair$96(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyUnicode.fromInterned("A buffered reader and writer object together.\n\n    A buffered reader object and buffered writer object put together to\n    form a sequential IO object that can read and write. This is typically\n    used with a socket or two-way pipe.\n\n    reader and writer are RawIOBase objects that are readable and\n    writeable respectively. If the buffer_size is omitted it defaults to\n    DEFAULT_BUFFER_SIZE.\n    "));
      var1.setline(1162);
      PyUnicode.fromInterned("A buffered reader and writer object together.\n\n    A buffered reader object and buffered writer object put together to\n    form a sequential IO object that can read and write. This is typically\n    used with a socket or two-way pipe.\n\n    reader and writer are RawIOBase objects that are readable and\n    writeable respectively. If the buffer_size is omitted it defaults to\n    DEFAULT_BUFFER_SIZE.\n    ");
      var1.setline(1167);
      PyObject[] var3 = new PyObject[]{var1.getname("DEFAULT_BUFFER_SIZE"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$97, PyUnicode.fromInterned("Constructor.\n\n        The arguments are two RawIO instances.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1185);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, read$98, (PyObject)null);
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(1190);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, readinto$99, (PyObject)null);
      var1.setlocal("readinto", var4);
      var3 = null;
      var1.setline(1193);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, write$100, (PyObject)null);
      var1.setlocal("write", var4);
      var3 = null;
      var1.setline(1196);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, peek$101, (PyObject)null);
      var1.setlocal("peek", var4);
      var3 = null;
      var1.setline(1199);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read1$102, (PyObject)null);
      var1.setlocal("read1", var4);
      var3 = null;
      var1.setline(1202);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, readable$103, (PyObject)null);
      var1.setlocal("readable", var4);
      var3 = null;
      var1.setline(1205);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, writable$104, (PyObject)null);
      var1.setlocal("writable", var4);
      var3 = null;
      var1.setline(1208);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, flush$105, (PyObject)null);
      var1.setlocal("flush", var4);
      var3 = null;
      var1.setline(1211);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$106, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(1215);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isatty$107, (PyObject)null);
      var1.setlocal("isatty", var4);
      var3 = null;
      var1.setline(1218);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, closed$108, (PyObject)null);
      PyObject var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("closed", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$97(PyFrame var1, ThreadState var2) {
      var1.setline(1172);
      PyUnicode.fromInterned("Constructor.\n\n        The arguments are two RawIO instances.\n        ");
      var1.setline(1173);
      PyObject var3 = var1.getlocal(4);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1174);
         var1.getglobal("warnings").__getattr__("warn").__call__((ThreadState)var2, PyUnicode.fromInterned("max_buffer_size is deprecated"), (PyObject)var1.getglobal("DeprecationWarning"), (PyObject)Py.newInteger(2));
      }

      var1.setline(1176);
      if (var1.getlocal(1).__getattr__("readable").__call__(var2).__not__().__nonzero__()) {
         var1.setline(1177);
         throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\"reader\" argument must be readable.")));
      } else {
         var1.setline(1179);
         if (var1.getlocal(2).__getattr__("writable").__call__(var2).__not__().__nonzero__()) {
            var1.setline(1180);
            throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\"writer\" argument must be writable.")));
         } else {
            var1.setline(1182);
            var3 = var1.getglobal("BufferedReader").__call__(var2, var1.getlocal(1), var1.getlocal(3));
            var1.getlocal(0).__setattr__("reader", var3);
            var3 = null;
            var1.setline(1183);
            var3 = var1.getglobal("BufferedWriter").__call__(var2, var1.getlocal(2), var1.getlocal(3));
            var1.getlocal(0).__setattr__("writer", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject read$98(PyFrame var1, ThreadState var2) {
      var1.setline(1186);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1187);
         PyInteger var4 = Py.newInteger(-1);
         var1.setlocal(1, var4);
         var3 = null;
      }

      var1.setline(1188);
      var3 = var1.getlocal(0).__getattr__("reader").__getattr__("read").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject readinto$99(PyFrame var1, ThreadState var2) {
      var1.setline(1191);
      PyObject var3 = var1.getlocal(0).__getattr__("reader").__getattr__("readinto").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject write$100(PyFrame var1, ThreadState var2) {
      var1.setline(1194);
      PyObject var3 = var1.getlocal(0).__getattr__("writer").__getattr__("write").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject peek$101(PyFrame var1, ThreadState var2) {
      var1.setline(1197);
      PyObject var3 = var1.getlocal(0).__getattr__("reader").__getattr__("peek").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject read1$102(PyFrame var1, ThreadState var2) {
      var1.setline(1200);
      PyObject var3 = var1.getlocal(0).__getattr__("reader").__getattr__("read1").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject readable$103(PyFrame var1, ThreadState var2) {
      var1.setline(1203);
      PyObject var3 = var1.getlocal(0).__getattr__("reader").__getattr__("readable").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject writable$104(PyFrame var1, ThreadState var2) {
      var1.setline(1206);
      PyObject var3 = var1.getlocal(0).__getattr__("writer").__getattr__("writable").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject flush$105(PyFrame var1, ThreadState var2) {
      var1.setline(1209);
      PyObject var3 = var1.getlocal(0).__getattr__("writer").__getattr__("flush").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject close$106(PyFrame var1, ThreadState var2) {
      var1.setline(1212);
      var1.getlocal(0).__getattr__("writer").__getattr__("close").__call__(var2);
      var1.setline(1213);
      var1.getlocal(0).__getattr__("reader").__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject isatty$107(PyFrame var1, ThreadState var2) {
      var1.setline(1216);
      PyObject var10000 = var1.getlocal(0).__getattr__("reader").__getattr__("isatty").__call__(var2);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("writer").__getattr__("isatty").__call__(var2);
      }

      PyObject var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject closed$108(PyFrame var1, ThreadState var2) {
      var1.setline(1220);
      PyObject var3 = var1.getlocal(0).__getattr__("writer").__getattr__("closed");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject BufferedRandom$109(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyUnicode.fromInterned("A buffered interface to random access streams.\n\n    The constructor creates a reader and writer for a seekable stream,\n    raw, given in the first argument. If the buffer_size is omitted it\n    defaults to DEFAULT_BUFFER_SIZE.\n    "));
      var1.setline(1230);
      PyUnicode.fromInterned("A buffered interface to random access streams.\n\n    The constructor creates a reader and writer for a seekable stream,\n    raw, given in the first argument. If the buffer_size is omitted it\n    defaults to DEFAULT_BUFFER_SIZE.\n    ");
      var1.setline(1232);
      PyInteger var3 = Py.newInteger(3);
      var1.setlocal("_warning_stack_offset", var3);
      var3 = null;
      var1.setline(1234);
      PyObject[] var4 = new PyObject[]{var1.getname("DEFAULT_BUFFER_SIZE"), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$110, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(1240);
      var4 = new PyObject[]{Py.newInteger(0)};
      var5 = new PyFunction(var1.f_globals, var4, seek$111, (PyObject)null);
      var1.setlocal("seek", var5);
      var3 = null;
      var1.setline(1257);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, tell$112, (PyObject)null);
      var1.setlocal("tell", var5);
      var3 = null;
      var1.setline(1263);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, truncate$113, (PyObject)null);
      var1.setlocal("truncate", var5);
      var3 = null;
      var1.setline(1269);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, read$114, (PyObject)null);
      var1.setlocal("read", var5);
      var3 = null;
      var1.setline(1275);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, readinto$115, (PyObject)null);
      var1.setlocal("readinto", var5);
      var3 = null;
      var1.setline(1279);
      var4 = new PyObject[]{Py.newInteger(0)};
      var5 = new PyFunction(var1.f_globals, var4, peek$116, (PyObject)null);
      var1.setlocal("peek", var5);
      var3 = null;
      var1.setline(1283);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, read1$117, (PyObject)null);
      var1.setlocal("read1", var5);
      var3 = null;
      var1.setline(1287);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, write$118, (PyObject)null);
      var1.setlocal("write", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$110(PyFrame var1, ThreadState var2) {
      var1.setline(1236);
      var1.getlocal(1).__getattr__("_checkSeekable").__call__(var2);
      var1.setline(1237);
      var1.getglobal("BufferedReader").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.setline(1238);
      var1.getglobal("BufferedWriter").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject seek$111(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[2];
      var1.setline(1241);
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
         var1.setline(1242);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("invalid whence")));
      } else {
         var1.setline(1243);
         var1.getlocal(0).__getattr__("flush").__call__(var2);
         var1.setline(1244);
         ContextManager var8;
         if (var1.getlocal(0).__getattr__("_read_buf").__nonzero__()) {
            label51: {
               var4 = (var8 = ContextGuard.getManager(var1.getlocal(0).__getattr__("_read_lock"))).__enter__(var2);

               try {
                  var1.setline(1247);
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

         var1.setline(1250);
         var7 = var1.getlocal(0).__getattr__("raw").__getattr__("seek").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setlocal(1, var7);
         var3 = null;
         var4 = (var8 = ContextGuard.getManager(var1.getlocal(0).__getattr__("_read_lock"))).__enter__(var2);

         label33: {
            try {
               var1.setline(1252);
               var1.getlocal(0).__getattr__("_reset_read_buf").__call__(var2);
            } catch (Throwable var5) {
               if (var8.__exit__(var2, Py.setException(var5, var1))) {
                  break label33;
               }

               throw (Throwable)Py.makeException();
            }

            var8.__exit__(var2, (PyException)null);
         }

         var1.setline(1253);
         var7 = var1.getlocal(1);
         PyObject var9 = var7._lt(Py.newInteger(0));
         var3 = null;
         if (var9.__nonzero__()) {
            var1.setline(1254);
            throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("seek() returned invalid position")));
         } else {
            var1.setline(1255);
            var7 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var7;
         }
      }
   }

   public PyObject tell$112(PyFrame var1, ThreadState var2) {
      var1.setline(1258);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_write_buf").__nonzero__()) {
         var1.setline(1259);
         var3 = var1.getglobal("BufferedWriter").__getattr__("tell").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1261);
         var3 = var1.getglobal("BufferedReader").__getattr__("tell").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject truncate$113(PyFrame var1, ThreadState var2) {
      var1.setline(1264);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1265);
         var3 = var1.getlocal(0).__getattr__("tell").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1267);
      var3 = var1.getglobal("BufferedWriter").__getattr__("truncate").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject read$114(PyFrame var1, ThreadState var2) {
      var1.setline(1270);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1271);
         PyInteger var4 = Py.newInteger(-1);
         var1.setlocal(1, var4);
         var3 = null;
      }

      var1.setline(1272);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(1273);
      var3 = var1.getglobal("BufferedReader").__getattr__("read").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject readinto$115(PyFrame var1, ThreadState var2) {
      var1.setline(1276);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(1277);
      PyObject var3 = var1.getglobal("BufferedReader").__getattr__("readinto").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject peek$116(PyFrame var1, ThreadState var2) {
      var1.setline(1280);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(1281);
      PyObject var3 = var1.getglobal("BufferedReader").__getattr__("peek").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject read1$117(PyFrame var1, ThreadState var2) {
      var1.setline(1284);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(1285);
      PyObject var3 = var1.getglobal("BufferedReader").__getattr__("read1").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject write$118(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(1288);
      if (var1.getlocal(0).__getattr__("_read_buf").__nonzero__()) {
         label24: {
            ContextManager var3;
            PyObject var4 = (var3 = ContextGuard.getManager(var1.getlocal(0).__getattr__("_read_lock"))).__enter__(var2);

            try {
               var1.setline(1291);
               var1.getlocal(0).__getattr__("raw").__getattr__("seek").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_read_pos")._sub(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_read_buf"))), (PyObject)Py.newInteger(1));
               var1.setline(1292);
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

      var1.setline(1293);
      PyObject var6 = var1.getglobal("BufferedWriter").__getattr__("write").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject TextIOBase$119(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyUnicode.fromInterned("Base class for text I/O.\n\n    This class provides a character and line based interface to stream\n    I/O. There is no readinto method because Python's character strings\n    are immutable. There is no public constructor.\n    "));
      var1.setline(1303);
      PyUnicode.fromInterned("Base class for text I/O.\n\n    This class provides a character and line based interface to stream\n    I/O. There is no readinto method because Python's character strings\n    are immutable. There is no public constructor.\n    ");
      var1.setline(1305);
      PyObject[] var3 = new PyObject[]{Py.newInteger(-1)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, read$120, PyUnicode.fromInterned("Read at most n characters from stream.\n\n        Read from underlying buffer until we have n characters or we hit EOF.\n        If n is negative or omitted, read until EOF.\n        "));
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(1313);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, write$121, PyUnicode.fromInterned("Write string s to stream."));
      var1.setlocal("write", var4);
      var3 = null;
      var1.setline(1317);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, truncate$122, PyUnicode.fromInterned("Truncate size to pos."));
      var1.setlocal("truncate", var4);
      var3 = null;
      var1.setline(1321);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, readline$123, PyUnicode.fromInterned("Read until newline or EOF.\n\n        Returns an empty string if EOF is hit immediately.\n        "));
      var1.setlocal("readline", var4);
      var3 = null;
      var1.setline(1328);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, detach$124, PyUnicode.fromInterned("\n        Separate the underlying buffer from the TextIOBase and return it.\n\n        After the underlying buffer has been detached, the TextIO is in an\n        unusable state.\n        "));
      var1.setlocal("detach", var4);
      var3 = null;
      var1.setline(1337);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, encoding$125, PyUnicode.fromInterned("Subclasses should override."));
      PyObject var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("encoding", var5);
      var3 = null;
      var1.setline(1342);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, newlines$126, PyUnicode.fromInterned("Line endings translated so far.\n\n        Only line endings translated during reading are considered.\n\n        Subclasses should override.\n        "));
      var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("newlines", var5);
      var3 = null;
      var1.setline(1352);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, errors$127, PyUnicode.fromInterned("Error setting of the decoder or encoder.\n\n        Subclasses should override."));
      var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("errors", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject read$120(PyFrame var1, ThreadState var2) {
      var1.setline(1310);
      PyUnicode.fromInterned("Read at most n characters from stream.\n\n        Read from underlying buffer until we have n characters or we hit EOF.\n        If n is negative or omitted, read until EOF.\n        ");
      var1.setline(1311);
      var1.getlocal(0).__getattr__("_unsupported").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("read"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject write$121(PyFrame var1, ThreadState var2) {
      var1.setline(1314);
      PyUnicode.fromInterned("Write string s to stream.");
      var1.setline(1315);
      var1.getlocal(0).__getattr__("_unsupported").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("write"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject truncate$122(PyFrame var1, ThreadState var2) {
      var1.setline(1318);
      PyUnicode.fromInterned("Truncate size to pos.");
      var1.setline(1319);
      var1.getlocal(0).__getattr__("_unsupported").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("truncate"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject readline$123(PyFrame var1, ThreadState var2) {
      var1.setline(1325);
      PyUnicode.fromInterned("Read until newline or EOF.\n\n        Returns an empty string if EOF is hit immediately.\n        ");
      var1.setline(1326);
      var1.getlocal(0).__getattr__("_unsupported").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("readline"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject detach$124(PyFrame var1, ThreadState var2) {
      var1.setline(1334);
      PyUnicode.fromInterned("\n        Separate the underlying buffer from the TextIOBase and return it.\n\n        After the underlying buffer has been detached, the TextIO is in an\n        unusable state.\n        ");
      var1.setline(1335);
      var1.getlocal(0).__getattr__("_unsupported").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("detach"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject encoding$125(PyFrame var1, ThreadState var2) {
      var1.setline(1339);
      PyUnicode.fromInterned("Subclasses should override.");
      var1.setline(1340);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject newlines$126(PyFrame var1, ThreadState var2) {
      var1.setline(1349);
      PyUnicode.fromInterned("Line endings translated so far.\n\n        Only line endings translated during reading are considered.\n\n        Subclasses should override.\n        ");
      var1.setline(1350);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject errors$127(PyFrame var1, ThreadState var2) {
      var1.setline(1356);
      PyUnicode.fromInterned("Error setting of the decoder or encoder.\n\n        Subclasses should override.");
      var1.setline(1357);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IncrementalNewlineDecoder$128(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyUnicode.fromInterned("Codec used when reading a file in universal newlines mode.  It wraps\n    another incremental decoder, translating \\r\\n and \\r into \\n.  It also\n    records the types of newlines encountered.  When used with\n    translate=False, it ensures that the newline sequence is returned in\n    one piece.\n    "));
      var1.setline(1368);
      PyUnicode.fromInterned("Codec used when reading a file in universal newlines mode.  It wraps\n    another incremental decoder, translating \\r\\n and \\r into \\n.  It also\n    records the types of newlines encountered.  When used with\n    translate=False, it ensures that the newline sequence is returned in\n    one piece.\n    ");
      var1.setline(1369);
      PyObject[] var3 = new PyObject[]{PyUnicode.fromInterned("strict")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$129, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1376);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, decode$130, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      var1.setline(1407);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getstate$131, (PyObject)null);
      var1.setlocal("getstate", var4);
      var3 = null;
      var1.setline(1418);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setstate$132, (PyObject)null);
      var1.setlocal("setstate", var4);
      var3 = null;
      var1.setline(1424);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$133, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(1430);
      PyInteger var5 = Py.newInteger(1);
      var1.setlocal("_LF", var5);
      var3 = null;
      var1.setline(1431);
      var5 = Py.newInteger(2);
      var1.setlocal("_CR", var5);
      var3 = null;
      var1.setline(1432);
      var5 = Py.newInteger(4);
      var1.setlocal("_CRLF", var5);
      var3 = null;
      var1.setline(1434);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, newlines$134, (PyObject)null);
      PyObject var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("newlines", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$129(PyFrame var1, ThreadState var2) {
      var1.setline(1370);
      PyObject var10000 = var1.getglobal("codecs").__getattr__("IncrementalDecoder").__getattr__("__init__");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getlocal(3)};
      String[] var4 = new String[]{"errors"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(1371);
      PyObject var5 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("translate", var5);
      var3 = null;
      var1.setline(1372);
      var5 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("decoder", var5);
      var3 = null;
      var1.setline(1373);
      PyInteger var6 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"seennl", var6);
      var3 = null;
      var1.setline(1374);
      var5 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("pendingcr", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject decode$130(PyFrame var1, ThreadState var2) {
      var1.setline(1378);
      PyObject var3 = var1.getlocal(0).__getattr__("decoder");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1379);
         var3 = var1.getlocal(1);
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(1381);
         var10000 = var1.getlocal(0).__getattr__("decoder").__getattr__("decode");
         PyObject[] var6 = new PyObject[]{var1.getlocal(1), var1.getlocal(2)};
         String[] var4 = new String[]{"final"};
         var10000 = var10000.__call__(var2, var6, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(1382);
      var10000 = var1.getlocal(0).__getattr__("pendingcr");
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(3);
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(2);
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(1383);
         var3 = PyUnicode.fromInterned("\r")._add(var1.getlocal(3));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1384);
         var3 = var1.getglobal("False");
         var1.getlocal(0).__setattr__("pendingcr", var3);
         var3 = null;
      }

      var1.setline(1388);
      var10000 = var1.getlocal(3).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\r"));
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(2).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(1389);
         var3 = var1.getlocal(3).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1390);
         var3 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("pendingcr", var3);
         var3 = null;
      }

      var1.setline(1393);
      var3 = var1.getlocal(3).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\r\n"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1394);
      var3 = var1.getlocal(3).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\r"))._sub(var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1395);
      var3 = var1.getlocal(3).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\n"))._sub(var1.getlocal(4));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1396);
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
      var1.setline(1399);
      if (var1.getlocal(0).__getattr__("translate").__nonzero__()) {
         var1.setline(1400);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(1401);
            var3 = var1.getlocal(3).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\r\n"), (PyObject)PyUnicode.fromInterned("\n"));
            var1.setlocal(3, var3);
            var3 = null;
         }

         var1.setline(1402);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(1403);
            var3 = var1.getlocal(3).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\r"), (PyObject)PyUnicode.fromInterned("\n"));
            var1.setlocal(3, var3);
            var3 = null;
         }
      }

      var1.setline(1405);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getstate$131(PyFrame var1, ThreadState var2) {
      var1.setline(1408);
      PyObject var3 = var1.getlocal(0).__getattr__("decoder");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1409);
         PyString var6 = PyString.fromInterned("");
         var1.setlocal(1, var6);
         var3 = null;
         var1.setline(1410);
         PyInteger var7 = Py.newInteger(0);
         var1.setlocal(2, var7);
         var3 = null;
      } else {
         var1.setline(1412);
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

      var1.setline(1413);
      var3 = var1.getlocal(2);
      var3 = var3._ilshift(Py.newInteger(1));
      var1.setlocal(2, var3);
      var1.setline(1414);
      if (var1.getlocal(0).__getattr__("pendingcr").__nonzero__()) {
         var1.setline(1415);
         var3 = var1.getlocal(2);
         var3 = var3._ior(Py.newInteger(1));
         var1.setlocal(2, var3);
      }

      var1.setline(1416);
      PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
      var1.f_lasti = -1;
      return var8;
   }

   public PyObject setstate$132(PyFrame var1, ThreadState var2) {
      var1.setline(1419);
      PyObject var3 = var1.getlocal(1);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(1420);
      var3 = var1.getglobal("bool").__call__(var2, var1.getlocal(3)._and(Py.newInteger(1)));
      var1.getlocal(0).__setattr__("pendingcr", var3);
      var3 = null;
      var1.setline(1421);
      var3 = var1.getlocal(0).__getattr__("decoder");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1422);
         var1.getlocal(0).__getattr__("decoder").__getattr__("setstate").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)._rshift(Py.newInteger(1))})));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject reset$133(PyFrame var1, ThreadState var2) {
      var1.setline(1425);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"seennl", var3);
      var3 = null;
      var1.setline(1426);
      PyObject var4 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("pendingcr", var4);
      var3 = null;
      var1.setline(1427);
      var4 = var1.getlocal(0).__getattr__("decoder");
      PyObject var10000 = var4._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1428);
         var1.getlocal(0).__getattr__("decoder").__getattr__("reset").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject newlines$134(PyFrame var1, ThreadState var2) {
      var1.setline(1436);
      PyObject var3 = (new PyTuple(new PyObject[]{var1.getglobal("None"), PyUnicode.fromInterned("\n"), PyUnicode.fromInterned("\r"), new PyTuple(new PyObject[]{PyUnicode.fromInterned("\r"), PyUnicode.fromInterned("\n")}), PyUnicode.fromInterned("\r\n"), new PyTuple(new PyObject[]{PyUnicode.fromInterned("\n"), PyUnicode.fromInterned("\r\n")}), new PyTuple(new PyObject[]{PyUnicode.fromInterned("\r"), PyUnicode.fromInterned("\r\n")}), new PyTuple(new PyObject[]{PyUnicode.fromInterned("\r"), PyUnicode.fromInterned("\n"), PyUnicode.fromInterned("\r\n")})})).__getitem__(var1.getlocal(0).__getattr__("seennl"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject TextIOWrapper$135(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyUnicode.fromInterned("Character and line based layer over a BufferedIOBase object, buffer.\n\n    encoding gives the name of the encoding that the stream will be\n    decoded or encoded with. It defaults to locale.getpreferredencoding.\n\n    errors determines the strictness of encoding and decoding (see the\n    codecs.register) and defaults to \"strict\".\n\n    newline can be None, '', '\\n', '\\r', or '\\r\\n'.  It controls the\n    handling of line endings. If it is None, universal newlines is\n    enabled.  With this enabled, on input, the lines endings '\\n', '\\r',\n    or '\\r\\n' are translated to '\\n' before being returned to the\n    caller. Conversely, on output, '\\n' is translated to the system\n    default line separator, os.linesep. If newline is any other of its\n    legal values, that newline becomes the newline when the file is read\n    and it is returned untranslated. On output, '\\n' is converted to the\n    newline.\n\n    If line_buffering is True, a call to flush is implied when a call to\n    write contains a newline character.\n    "));
      var1.setline(1469);
      PyUnicode.fromInterned("Character and line based layer over a BufferedIOBase object, buffer.\n\n    encoding gives the name of the encoding that the stream will be\n    decoded or encoded with. It defaults to locale.getpreferredencoding.\n\n    errors determines the strictness of encoding and decoding (see the\n    codecs.register) and defaults to \"strict\".\n\n    newline can be None, '', '\\n', '\\r', or '\\r\\n'.  It controls the\n    handling of line endings. If it is None, universal newlines is\n    enabled.  With this enabled, on input, the lines endings '\\n', '\\r',\n    or '\\r\\n' are translated to '\\n' before being returned to the\n    caller. Conversely, on output, '\\n' is translated to the system\n    default line separator, os.linesep. If newline is any other of its\n    legal values, that newline becomes the newline when the file is read\n    and it is returned untranslated. On output, '\\n' is converted to the\n    newline.\n\n    If line_buffering is True, a call to flush is implied when a call to\n    write contains a newline character.\n    ");
      var1.setline(1471);
      PyInteger var3 = Py.newInteger(2048);
      var1.setlocal("_CHUNK_SIZE", var3);
      var3 = null;
      var1.setline(1473);
      PyObject[] var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("False")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$136, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(1531);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __repr__$137, (PyObject)null);
      var1.setlocal("__repr__", var5);
      var3 = null;
      var1.setline(1540);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, encoding$138, (PyObject)null);
      PyObject var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("encoding", var6);
      var3 = null;
      var1.setline(1544);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, errors$139, (PyObject)null);
      var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("errors", var6);
      var3 = null;
      var1.setline(1548);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, line_buffering$140, (PyObject)null);
      var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("line_buffering", var6);
      var3 = null;
      var1.setline(1552);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, buffer$141, (PyObject)null);
      var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("buffer", var6);
      var3 = null;
      var1.setline(1556);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, seekable$142, (PyObject)null);
      var1.setlocal("seekable", var5);
      var3 = null;
      var1.setline(1561);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, readable$143, (PyObject)null);
      var1.setlocal("readable", var5);
      var3 = null;
      var1.setline(1564);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, writable$144, (PyObject)null);
      var1.setlocal("writable", var5);
      var3 = null;
      var1.setline(1567);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, flush$145, (PyObject)null);
      var1.setlocal("flush", var5);
      var3 = null;
      var1.setline(1571);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, close$146, (PyObject)null);
      var1.setlocal("close", var5);
      var3 = null;
      var1.setline(1578);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, closed$147, (PyObject)null);
      var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("closed", var6);
      var3 = null;
      var1.setline(1582);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, name$148, (PyObject)null);
      var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("name", var6);
      var3 = null;
      var1.setline(1586);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, fileno$149, (PyObject)null);
      var1.setlocal("fileno", var5);
      var3 = null;
      var1.setline(1589);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, isatty$150, (PyObject)null);
      var1.setlocal("isatty", var5);
      var3 = null;
      var1.setline(1592);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, write$151, (PyObject)null);
      var1.setlocal("write", var5);
      var3 = null;
      var1.setline(1613);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_encoder$152, (PyObject)null);
      var1.setlocal("_get_encoder", var5);
      var3 = null;
      var1.setline(1618);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_decoder$153, (PyObject)null);
      var1.setlocal("_get_decoder", var5);
      var3 = null;
      var1.setline(1629);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _set_decoded_chars$154, PyUnicode.fromInterned("Set the _decoded_chars buffer."));
      var1.setlocal("_set_decoded_chars", var5);
      var3 = null;
      var1.setline(1634);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, _get_decoded_chars$155, PyUnicode.fromInterned("Advance into the _decoded_chars buffer."));
      var1.setlocal("_get_decoded_chars", var5);
      var3 = null;
      var1.setline(1644);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _rewind_decoded_chars$156, PyUnicode.fromInterned("Rewind the _decoded_chars buffer."));
      var1.setlocal("_rewind_decoded_chars", var5);
      var3 = null;
      var1.setline(1650);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _read_chunk$157, PyUnicode.fromInterned("\n        Read and decode the next chunk of data from the BufferedReader.\n        "));
      var1.setlocal("_read_chunk", var5);
      var3 = null;
      var1.setline(1684);
      var4 = new PyObject[]{Py.newInteger(0), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0)};
      var5 = new PyFunction(var1.f_globals, var4, _pack_cookie$158, (PyObject)null);
      var1.setlocal("_pack_cookie", var5);
      var3 = null;
      var1.setline(1694);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _unpack_cookie$159, (PyObject)null);
      var1.setlocal("_unpack_cookie", var5);
      var3 = null;
      var1.setline(1701);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, tell$160, (PyObject)null);
      var1.setlocal("tell", var5);
      var3 = null;
      var1.setline(1763);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, truncate$161, (PyObject)null);
      var1.setlocal("truncate", var5);
      var3 = null;
      var1.setline(1769);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, detach$162, (PyObject)null);
      var1.setlocal("detach", var5);
      var3 = null;
      var1.setline(1777);
      var4 = new PyObject[]{Py.newInteger(0)};
      var5 = new PyFunction(var1.f_globals, var4, seek$163, (PyObject)null);
      var1.setlocal("seek", var5);
      var3 = null;
      var1.setline(1849);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, read$164, (PyObject)null);
      var1.setlocal("read", var5);
      var3 = null;
      var1.setline(1874);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, next$165, (PyObject)null);
      var1.setlocal("next", var5);
      var3 = null;
      var1.setline(1883);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, readline$166, (PyObject)null);
      var1.setlocal("readline", var5);
      var3 = null;
      var1.setline(1971);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, newlines$167, (PyObject)null);
      var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("newlines", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$136(PyFrame var1, ThreadState var2) {
      var1.setline(1475);
      PyObject var3 = var1.getlocal(4);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("basestring")).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(1476);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyUnicode.fromInterned("illegal newline type: %r")._mod(new PyTuple(new PyObject[]{var1.getglobal("type").__call__(var2, var1.getlocal(4))}))));
      } else {
         var1.setline(1477);
         var3 = var1.getlocal(4);
         var10000 = var3._notin(new PyTuple(new PyObject[]{var1.getglobal("None"), PyUnicode.fromInterned(""), PyUnicode.fromInterned("\n"), PyUnicode.fromInterned("\r"), PyUnicode.fromInterned("\r\n")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1478);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyUnicode.fromInterned("illegal newline value: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(4)}))));
         } else {
            var1.setline(1479);
            var3 = var1.getlocal(2);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            PyException var8;
            if (var10000.__nonzero__()) {
               label80: {
                  PyUnicode var4;
                  try {
                     var1.setline(1481);
                     var3 = imp.importOne("locale", var1, -1);
                     var1.setlocal(6, var3);
                     var3 = null;
                  } catch (Throwable var6) {
                     var8 = Py.setException(var6, var1);
                     if (var8.match(var1.getglobal("ImportError"))) {
                        var1.setline(1484);
                        var4 = PyUnicode.fromInterned("ascii");
                        var1.setlocal(2, var4);
                        var4 = null;
                        break label80;
                     }

                     throw var8;
                  }

                  var1.setline(1486);
                  PyObject var7 = var1.getlocal(6).__getattr__("getpreferredencoding").__call__(var2);
                  var1.setlocal(2, var7);
                  var4 = null;
               }
            }

            var1.setline(1488);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("basestring")).__not__().__nonzero__()) {
               var1.setline(1489);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyUnicode.fromInterned("invalid encoding: %r")._mod(var1.getlocal(2))));
            } else {
               var1.setline(1491);
               var3 = var1.getlocal(3);
               var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
               PyUnicode var9;
               if (var10000.__nonzero__()) {
                  var1.setline(1492);
                  var9 = PyUnicode.fromInterned("strict");
                  var1.setlocal(3, var9);
                  var3 = null;
               } else {
                  var1.setline(1494);
                  if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("basestring")).__not__().__nonzero__()) {
                     var1.setline(1495);
                     throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyUnicode.fromInterned("invalid errors: %r")._mod(var1.getlocal(3))));
                  }
               }

               var1.setline(1497);
               var3 = var1.getlocal(1);
               var1.getlocal(0).__setattr__("_buffer", var3);
               var3 = null;
               var1.setline(1498);
               var3 = var1.getlocal(5);
               var1.getlocal(0).__setattr__("_line_buffering", var3);
               var3 = null;
               var1.setline(1499);
               var3 = var1.getlocal(2);
               var1.getlocal(0).__setattr__("_encoding", var3);
               var3 = null;
               var1.setline(1500);
               var3 = var1.getlocal(3);
               var1.getlocal(0).__setattr__("_errors", var3);
               var3 = null;
               var1.setline(1501);
               var3 = var1.getlocal(4).__not__();
               var1.getlocal(0).__setattr__("_readuniversal", var3);
               var3 = null;
               var1.setline(1502);
               var3 = var1.getlocal(4);
               var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
               var3 = var10000;
               var1.getlocal(0).__setattr__("_readtranslate", var3);
               var3 = null;
               var1.setline(1503);
               var3 = var1.getlocal(4);
               var1.getlocal(0).__setattr__("_readnl", var3);
               var3 = null;
               var1.setline(1504);
               var3 = var1.getlocal(4);
               var10000 = var3._ne(PyUnicode.fromInterned(""));
               var3 = null;
               var3 = var10000;
               var1.getlocal(0).__setattr__("_writetranslate", var3);
               var3 = null;
               var1.setline(1505);
               var10000 = var1.getlocal(4);
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getglobal("os").__getattr__("linesep");
               }

               var3 = var10000;
               var1.getlocal(0).__setattr__("_writenl", var3);
               var3 = null;
               var1.setline(1506);
               var3 = var1.getglobal("None");
               var1.getlocal(0).__setattr__("_encoder", var3);
               var3 = null;
               var1.setline(1507);
               var3 = var1.getglobal("None");
               var1.getlocal(0).__setattr__("_decoder", var3);
               var3 = null;
               var1.setline(1508);
               var9 = PyUnicode.fromInterned("");
               var1.getlocal(0).__setattr__((String)"_decoded_chars", var9);
               var3 = null;
               var1.setline(1509);
               PyInteger var10 = Py.newInteger(0);
               var1.getlocal(0).__setattr__((String)"_decoded_chars_used", var10);
               var3 = null;
               var1.setline(1510);
               var3 = var1.getglobal("None");
               var1.getlocal(0).__setattr__("_snapshot", var3);
               var3 = null;
               var1.setline(1511);
               var3 = var1.getlocal(0).__getattr__("buffer").__getattr__("seekable").__call__(var2);
               var1.getlocal(0).__setattr__("_seekable", var3);
               var1.getlocal(0).__setattr__("_telling", var3);
               var1.setline(1513);
               var10000 = var1.getlocal(0).__getattr__("_seekable");
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(0).__getattr__("writable").__call__(var2);
               }

               if (var10000.__nonzero__()) {
                  var1.setline(1514);
                  var3 = var1.getlocal(0).__getattr__("buffer").__getattr__("tell").__call__(var2);
                  var1.setlocal(7, var3);
                  var3 = null;
                  var1.setline(1515);
                  var3 = var1.getlocal(7);
                  var10000 = var3._ne(Py.newInteger(0));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     try {
                        var1.setline(1517);
                        var1.getlocal(0).__getattr__("_get_encoder").__call__(var2).__getattr__("setstate").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                     } catch (Throwable var5) {
                        var8 = Py.setException(var5, var1);
                        if (!var8.match(var1.getglobal("LookupError"))) {
                           throw var8;
                        }

                        var1.setline(1520);
                     }
                  }
               }

               var1.f_lasti = -1;
               return Py.None;
            }
         }
      }
   }

   public PyObject __repr__$137(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var4;
      try {
         var1.setline(1533);
         PyObject var6 = var1.getlocal(0).__getattr__("name");
         var1.setlocal(1, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("AttributeError"))) {
            var1.setline(1535);
            var4 = PyUnicode.fromInterned("<_pyio.TextIOWrapper encoding='{0}'>").__getattr__("format").__call__(var2, var1.getlocal(0).__getattr__("encoding"));
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(1537);
      var4 = PyUnicode.fromInterned("<_pyio.TextIOWrapper name={0!r} encoding='{1}'>").__getattr__("format").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("encoding"));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject encoding$138(PyFrame var1, ThreadState var2) {
      var1.setline(1542);
      PyObject var3 = var1.getlocal(0).__getattr__("_encoding");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject errors$139(PyFrame var1, ThreadState var2) {
      var1.setline(1546);
      PyObject var3 = var1.getlocal(0).__getattr__("_errors");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject line_buffering$140(PyFrame var1, ThreadState var2) {
      var1.setline(1550);
      PyObject var3 = var1.getlocal(0).__getattr__("_line_buffering");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject buffer$141(PyFrame var1, ThreadState var2) {
      var1.setline(1554);
      PyObject var3 = var1.getlocal(0).__getattr__("_buffer");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject seekable$142(PyFrame var1, ThreadState var2) {
      var1.setline(1557);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(1558);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("I/O operation on closed file.")));
      } else {
         var1.setline(1559);
         PyObject var3 = var1.getlocal(0).__getattr__("_seekable");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject readable$143(PyFrame var1, ThreadState var2) {
      var1.setline(1562);
      PyObject var3 = var1.getlocal(0).__getattr__("buffer").__getattr__("readable").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject writable$144(PyFrame var1, ThreadState var2) {
      var1.setline(1565);
      PyObject var3 = var1.getlocal(0).__getattr__("buffer").__getattr__("writable").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject flush$145(PyFrame var1, ThreadState var2) {
      var1.setline(1568);
      var1.getlocal(0).__getattr__("buffer").__getattr__("flush").__call__(var2);
      var1.setline(1569);
      PyObject var3 = var1.getlocal(0).__getattr__("_seekable");
      var1.getlocal(0).__setattr__("_telling", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$146(PyFrame var1, ThreadState var2) {
      var1.setline(1572);
      PyObject var3 = var1.getlocal(0).__getattr__("buffer");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("closed").__not__();
      }

      if (var10000.__nonzero__()) {
         var3 = null;

         try {
            var1.setline(1574);
            var1.getlocal(0).__getattr__("flush").__call__(var2);
         } catch (Throwable var4) {
            Py.addTraceback(var4, var1);
            var1.setline(1576);
            var1.getlocal(0).__getattr__("buffer").__getattr__("close").__call__(var2);
            throw (Throwable)var4;
         }

         var1.setline(1576);
         var1.getlocal(0).__getattr__("buffer").__getattr__("close").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject closed$147(PyFrame var1, ThreadState var2) {
      var1.setline(1580);
      PyObject var3 = var1.getlocal(0).__getattr__("buffer").__getattr__("closed");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject name$148(PyFrame var1, ThreadState var2) {
      var1.setline(1584);
      PyObject var3 = var1.getlocal(0).__getattr__("buffer").__getattr__("name");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject fileno$149(PyFrame var1, ThreadState var2) {
      var1.setline(1587);
      PyObject var3 = var1.getlocal(0).__getattr__("buffer").__getattr__("fileno").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isatty$150(PyFrame var1, ThreadState var2) {
      var1.setline(1590);
      PyObject var3 = var1.getlocal(0).__getattr__("buffer").__getattr__("isatty").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject write$151(PyFrame var1, ThreadState var2) {
      var1.setline(1593);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(1594);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("write to closed file")));
      } else {
         var1.setline(1595);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("unicode")).__not__().__nonzero__()) {
            var1.setline(1596);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyUnicode.fromInterned("can't write %s to text stream")._mod(var1.getlocal(1).__getattr__("__class__").__getattr__("__name__"))));
         } else {
            var1.setline(1598);
            PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(1599);
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
            var1.setline(1600);
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
               var1.setline(1601);
               var3 = var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\n"), (PyObject)var1.getlocal(0).__getattr__("_writenl"));
               var1.setlocal(1, var3);
               var3 = null;
            }

            var1.setline(1602);
            var10000 = var1.getlocal(0).__getattr__("_encoder");
            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("_get_encoder").__call__(var2);
            }

            var3 = var10000;
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(1604);
            var3 = var1.getlocal(4).__getattr__("encode").__call__(var2, var1.getlocal(1));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(1605);
            var1.getlocal(0).__getattr__("buffer").__getattr__("write").__call__(var2, var1.getlocal(5));
            var1.setline(1606);
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
               var1.setline(1607);
               var1.getlocal(0).__getattr__("flush").__call__(var2);
            }

            var1.setline(1608);
            var3 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("_snapshot", var3);
            var3 = null;
            var1.setline(1609);
            if (var1.getlocal(0).__getattr__("_decoder").__nonzero__()) {
               var1.setline(1610);
               var1.getlocal(0).__getattr__("_decoder").__getattr__("reset").__call__(var2);
            }

            var1.setline(1611);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _get_encoder$152(PyFrame var1, ThreadState var2) {
      var1.setline(1614);
      PyObject var3 = var1.getglobal("codecs").__getattr__("getincrementalencoder").__call__(var2, var1.getlocal(0).__getattr__("_encoding"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1615);
      var3 = var1.getlocal(1).__call__(var2, var1.getlocal(0).__getattr__("_errors"));
      var1.getlocal(0).__setattr__("_encoder", var3);
      var3 = null;
      var1.setline(1616);
      var3 = var1.getlocal(0).__getattr__("_encoder");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_decoder$153(PyFrame var1, ThreadState var2) {
      var1.setline(1619);
      PyObject var3 = var1.getglobal("codecs").__getattr__("getincrementaldecoder").__call__(var2, var1.getlocal(0).__getattr__("_encoding"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1620);
      var3 = var1.getlocal(1).__call__(var2, var1.getlocal(0).__getattr__("_errors"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1621);
      if (var1.getlocal(0).__getattr__("_readuniversal").__nonzero__()) {
         var1.setline(1622);
         var3 = var1.getglobal("IncrementalNewlineDecoder").__call__(var2, var1.getlocal(2), var1.getlocal(0).__getattr__("_readtranslate"));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1623);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_decoder", var3);
      var3 = null;
      var1.setline(1624);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _set_decoded_chars$154(PyFrame var1, ThreadState var2) {
      var1.setline(1630);
      PyUnicode.fromInterned("Set the _decoded_chars buffer.");
      var1.setline(1631);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_decoded_chars", var3);
      var3 = null;
      var1.setline(1632);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_decoded_chars_used", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_decoded_chars$155(PyFrame var1, ThreadState var2) {
      var1.setline(1635);
      PyUnicode.fromInterned("Advance into the _decoded_chars buffer.");
      var1.setline(1636);
      PyObject var3 = var1.getlocal(0).__getattr__("_decoded_chars_used");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1637);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1638);
         var3 = var1.getlocal(0).__getattr__("_decoded_chars").__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null);
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(1640);
         var3 = var1.getlocal(0).__getattr__("_decoded_chars").__getslice__(var1.getlocal(2), var1.getlocal(2)._add(var1.getlocal(1)), (PyObject)null);
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(1641);
      var10000 = var1.getlocal(0);
      String var6 = "_decoded_chars_used";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var6);
      var5 = var5._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(3)));
      var4.__setattr__(var6, var5);
      var1.setline(1642);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _rewind_decoded_chars$156(PyFrame var1, ThreadState var2) {
      var1.setline(1645);
      PyUnicode.fromInterned("Rewind the _decoded_chars buffer.");
      var1.setline(1646);
      PyObject var3 = var1.getlocal(0).__getattr__("_decoded_chars_used");
      PyObject var10000 = var3._lt(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1647);
         throw Py.makeException(var1.getglobal("AssertionError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("rewind decoded_chars out of bounds")));
      } else {
         var1.setline(1648);
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

   public PyObject _read_chunk$157(PyFrame var1, ThreadState var2) {
      var1.setline(1653);
      PyUnicode.fromInterned("\n        Read and decode the next chunk of data from the BufferedReader.\n        ");
      var1.setline(1661);
      PyObject var3 = var1.getlocal(0).__getattr__("_decoder");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1662);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("no decoder")));
      } else {
         var1.setline(1664);
         if (var1.getlocal(0).__getattr__("_telling").__nonzero__()) {
            var1.setline(1668);
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

         var1.setline(1673);
         var3 = var1.getlocal(0).__getattr__("buffer").__getattr__("read1").__call__(var2, var1.getlocal(0).__getattr__("_CHUNK_SIZE"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1674);
         var3 = var1.getlocal(3).__not__();
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1675);
         var1.getlocal(0).__getattr__("_set_decoded_chars").__call__(var2, var1.getlocal(0).__getattr__("_decoder").__getattr__("decode").__call__(var2, var1.getlocal(3), var1.getlocal(4)));
         var1.setline(1677);
         if (var1.getlocal(0).__getattr__("_telling").__nonzero__()) {
            var1.setline(1680);
            PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(1)._add(var1.getlocal(3))});
            var1.getlocal(0).__setattr__((String)"_snapshot", var6);
            var3 = null;
         }

         var1.setline(1682);
         var3 = var1.getlocal(4).__not__();
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _pack_cookie$158(PyFrame var1, ThreadState var2) {
      var1.setline(1691);
      PyObject var3 = var1.getlocal(1)._or(var1.getlocal(2)._lshift(Py.newInteger(64)))._or(var1.getlocal(3)._lshift(Py.newInteger(128)))._or(var1.getlocal(5)._lshift(Py.newInteger(192)))._or(var1.getglobal("bool").__call__(var2, var1.getlocal(4))._lshift(Py.newInteger(256)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _unpack_cookie$159(PyFrame var1, ThreadState var2) {
      var1.setline(1695);
      PyObject var3 = var1.getglobal("divmod").__call__(var2, var1.getlocal(1), Py.newInteger(1)._lshift(Py.newInteger(64)));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(1696);
      var3 = var1.getglobal("divmod").__call__(var2, var1.getlocal(2), Py.newInteger(1)._lshift(Py.newInteger(64)));
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(1697);
      var3 = var1.getglobal("divmod").__call__(var2, var1.getlocal(2), Py.newInteger(1)._lshift(Py.newInteger(64)));
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(1698);
      var3 = var1.getglobal("divmod").__call__(var2, var1.getlocal(2), Py.newInteger(1)._lshift(Py.newInteger(64)));
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(7, var5);
      var5 = null;
      var3 = null;
      var1.setline(1699);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject tell$160(PyFrame var1, ThreadState var2) {
      var1.setline(1702);
      if (var1.getlocal(0).__getattr__("_seekable").__not__().__nonzero__()) {
         var1.setline(1703);
         throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("underlying stream is not seekable")));
      } else {
         var1.setline(1704);
         if (var1.getlocal(0).__getattr__("_telling").__not__().__nonzero__()) {
            var1.setline(1705);
            throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("telling position disabled by next() call")));
         } else {
            var1.setline(1706);
            var1.getlocal(0).__getattr__("flush").__call__(var2);
            var1.setline(1707);
            PyObject var3 = var1.getlocal(0).__getattr__("buffer").__getattr__("tell").__call__(var2);
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(1708);
            var3 = var1.getlocal(0).__getattr__("_decoder");
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(1709);
            var3 = var1.getlocal(2);
            PyObject var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var3 = var1.getlocal(0).__getattr__("_snapshot");
               var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(1710);
               if (var1.getlocal(0).__getattr__("_decoded_chars").__nonzero__()) {
                  var1.setline(1712);
                  throw Py.makeException(var1.getglobal("AssertionError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("pending decoded text")));
               } else {
                  var1.setline(1713);
                  var3 = var1.getlocal(1);
                  var1.f_lasti = -1;
                  return var3;
               }
            } else {
               var1.setline(1716);
               PyObject var4 = var1.getlocal(0).__getattr__("_snapshot");
               PyObject[] var5 = Py.unpackSequence(var4, 2);
               PyObject var6 = var5[0];
               var1.setlocal(3, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(4, var6);
               var6 = null;
               var4 = null;
               var1.setline(1717);
               var4 = var1.getlocal(1);
               var4 = var4._isub(var1.getglobal("len").__call__(var2, var1.getlocal(4)));
               var1.setlocal(1, var4);
               var1.setline(1720);
               var4 = var1.getlocal(0).__getattr__("_decoded_chars_used");
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(1721);
               var4 = var1.getlocal(5);
               var10000 = var4._eq(Py.newInteger(0));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1723);
                  var3 = var1.getlocal(0).__getattr__("_pack_cookie").__call__(var2, var1.getlocal(1), var1.getlocal(3));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(1727);
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
                        var1.setline(1730);
                        var1.getlocal(2).__getattr__("setstate").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned(""), var1.getlocal(3)})));
                        var1.setline(1731);
                        var15 = var1.getlocal(1);
                        var1.setlocal(7, var15);
                        var5 = null;
                        var1.setline(1732);
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
                        var1.setline(1733);
                        PyInteger var17 = Py.newInteger(0);
                        var1.setlocal(11, var17);
                        var5 = null;
                        var1.setline(1739);
                        var15 = var1.getlocal(4).__iter__();
                     } catch (Throwable var12) {
                        var23 = var12;
                        var10001 = false;
                        break label83;
                     }

                     while(true) {
                        PyObject[] var8;
                        try {
                           var1.setline(1739);
                           var6 = var15.__iternext__();
                           if (var6 == null) {
                              var1.setline(1752);
                              var7 = var1.getlocal(10);
                              var10000 = var1.getglobal("len");
                              PyObject var10002 = var1.getlocal(2).__getattr__("decode");
                              var8 = new PyObject[]{PyString.fromInterned(""), var1.getglobal("True")};
                              String[] var20 = new String[]{"final"};
                              var10002 = var10002.__call__(var2, var8, var20);
                              var8 = null;
                              var7 = var7._iadd(var10000.__call__(var2, var10002));
                              var1.setlocal(10, var7);
                              var1.setline(1753);
                              PyInteger var22 = Py.newInteger(1);
                              var1.setlocal(11, var22);
                              var7 = null;
                              var1.setline(1754);
                              var7 = var1.getlocal(10);
                              var10000 = var7._lt(var1.getlocal(5));
                              var7 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(1755);
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
                           var1.setline(1740);
                           var7 = var1.getlocal(9);
                           var7 = var7._iadd(Py.newInteger(1));
                           var1.setlocal(9, var7);
                           var1.setline(1741);
                           var7 = var1.getlocal(10);
                           var7 = var7._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(2).__getattr__("decode").__call__(var2, var1.getlocal(12))));
                           var1.setlocal(10, var7);
                           var1.setline(1742);
                           var7 = var1.getlocal(2).__getattr__("getstate").__call__(var2);
                           var8 = Py.unpackSequence(var7, 2);
                           PyObject var9 = var8[0];
                           var1.setlocal(13, var9);
                           var9 = null;
                           var9 = var8[1];
                           var1.setlocal(3, var9);
                           var9 = null;
                           var7 = null;
                           var1.setline(1743);
                           var10000 = var1.getlocal(13).__not__();
                           if (var10000.__nonzero__()) {
                              var7 = var1.getlocal(10);
                              var10000 = var7._le(var1.getlocal(5));
                              var7 = null;
                           }

                           if (var10000.__nonzero__()) {
                              var1.setline(1745);
                              var7 = var1.getlocal(7);
                              var7 = var7._iadd(var1.getlocal(9));
                              var1.setlocal(7, var7);
                              var1.setline(1746);
                              var7 = var1.getlocal(5);
                              var7 = var7._isub(var1.getlocal(10));
                              var1.setlocal(5, var7);
                              var1.setline(1747);
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

                           var1.setline(1748);
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
                        var1.setline(1758);
                        var10000 = var1.getlocal(0).__getattr__("_pack_cookie");
                        var5 = new PyObject[]{var1.getlocal(7), var1.getlocal(8), var1.getlocal(9), var1.getlocal(11), var1.getlocal(5)};
                        var3 = var10000.__call__(var2, var5);
                     } catch (Throwable var11) {
                        var23 = var11;
                        var10001 = false;
                        break label83;
                     }

                     var1.setline(1761);
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
                  var1.setline(1761);
                  var1.getlocal(2).__getattr__("setstate").__call__(var2, var1.getlocal(6));
                  throw (Throwable)var19;
               }
            }
         }
      }
   }

   public PyObject truncate$161(PyFrame var1, ThreadState var2) {
      var1.setline(1764);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(1765);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1766);
         var3 = var1.getlocal(0).__getattr__("tell").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1767);
      var3 = var1.getlocal(0).__getattr__("buffer").__getattr__("truncate").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject detach$162(PyFrame var1, ThreadState var2) {
      var1.setline(1770);
      PyObject var3 = var1.getlocal(0).__getattr__("buffer");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1771);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("buffer is already detached")));
      } else {
         var1.setline(1772);
         var1.getlocal(0).__getattr__("flush").__call__(var2);
         var1.setline(1773);
         var3 = var1.getlocal(0).__getattr__("_buffer");
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(1774);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("_buffer", var3);
         var3 = null;
         var1.setline(1775);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject seek$163(PyFrame var1, ThreadState var2) {
      var1.setline(1778);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(1779);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("tell on closed file")));
      } else {
         var1.setline(1780);
         if (var1.getlocal(0).__getattr__("_seekable").__not__().__nonzero__()) {
            var1.setline(1781);
            throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("underlying stream is not seekable")));
         } else {
            var1.setline(1782);
            PyObject var3 = var1.getlocal(2);
            PyObject var10000 = var3._eq(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1783);
               var3 = var1.getlocal(1);
               var10000 = var3._ne(Py.newInteger(0));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1784);
                  throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("can't do nonzero cur-relative seeks")));
               }

               var1.setline(1787);
               PyInteger var8 = Py.newInteger(0);
               var1.setlocal(2, var8);
               var3 = null;
               var1.setline(1788);
               var3 = var1.getlocal(0).__getattr__("tell").__call__(var2);
               var1.setlocal(1, var3);
               var3 = null;
            }

            var1.setline(1789);
            var3 = var1.getlocal(2);
            var10000 = var3._eq(Py.newInteger(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1790);
               var3 = var1.getlocal(1);
               var10000 = var3._ne(Py.newInteger(0));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1791);
                  throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("can't do nonzero end-relative seeks")));
               } else {
                  var1.setline(1792);
                  var1.getlocal(0).__getattr__("flush").__call__(var2);
                  var1.setline(1793);
                  var3 = var1.getlocal(0).__getattr__("buffer").__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(2));
                  var1.setlocal(3, var3);
                  var3 = null;
                  var1.setline(1794);
                  var1.getlocal(0).__getattr__("_set_decoded_chars").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned(""));
                  var1.setline(1795);
                  var3 = var1.getglobal("None");
                  var1.getlocal(0).__setattr__("_snapshot", var3);
                  var3 = null;
                  var1.setline(1796);
                  if (var1.getlocal(0).__getattr__("_decoder").__nonzero__()) {
                     var1.setline(1797);
                     var1.getlocal(0).__getattr__("_decoder").__getattr__("reset").__call__(var2);
                  }

                  var1.setline(1798);
                  var3 = var1.getlocal(3);
                  var1.f_lasti = -1;
                  return var3;
               }
            } else {
               var1.setline(1799);
               PyObject var4 = var1.getlocal(2);
               var10000 = var4._ne(Py.newInteger(0));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1800);
                  throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyUnicode.fromInterned("invalid whence (%r, should be 0, 1 or 2)")._mod(new PyTuple(new PyObject[]{var1.getlocal(2)}))));
               } else {
                  var1.setline(1802);
                  var4 = var1.getlocal(1);
                  var10000 = var4._lt(Py.newInteger(0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1803);
                     throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyUnicode.fromInterned("negative seek position %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(1)}))));
                  } else {
                     var1.setline(1804);
                     var1.getlocal(0).__getattr__("flush").__call__(var2);
                     var1.setline(1808);
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
                     var1.setline(1812);
                     var1.getlocal(0).__getattr__("buffer").__getattr__("seek").__call__(var2, var1.getlocal(4));
                     var1.setline(1813);
                     var1.getlocal(0).__getattr__("_set_decoded_chars").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned(""));
                     var1.setline(1814);
                     var4 = var1.getglobal("None");
                     var1.getlocal(0).__setattr__("_snapshot", var4);
                     var4 = null;
                     var1.setline(1817);
                     var4 = var1.getlocal(1);
                     var10000 = var4._eq(Py.newInteger(0));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var10000 = var1.getlocal(0).__getattr__("_decoder");
                     }

                     PyTuple var10;
                     if (var10000.__nonzero__()) {
                        var1.setline(1818);
                        var1.getlocal(0).__getattr__("_decoder").__getattr__("reset").__call__(var2);
                     } else {
                        var1.setline(1819);
                        var10000 = var1.getlocal(0).__getattr__("_decoder");
                        if (!var10000.__nonzero__()) {
                           var10000 = var1.getlocal(5);
                           if (!var10000.__nonzero__()) {
                              var10000 = var1.getlocal(8);
                           }
                        }

                        if (var10000.__nonzero__()) {
                           var1.setline(1820);
                           var10000 = var1.getlocal(0).__getattr__("_decoder");
                           if (!var10000.__nonzero__()) {
                              var10000 = var1.getlocal(0).__getattr__("_get_decoder").__call__(var2);
                           }

                           var4 = var10000;
                           var1.getlocal(0).__setattr__("_decoder", var4);
                           var4 = null;
                           var1.setline(1821);
                           var1.getlocal(0).__getattr__("_decoder").__getattr__("setstate").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned(""), var1.getlocal(5)})));
                           var1.setline(1822);
                           var10 = new PyTuple(new PyObject[]{var1.getlocal(5), PyString.fromInterned("")});
                           var1.getlocal(0).__setattr__((String)"_snapshot", var10);
                           var4 = null;
                        }
                     }

                     var1.setline(1824);
                     if (var1.getlocal(8).__nonzero__()) {
                        var1.setline(1826);
                        var4 = var1.getlocal(0).__getattr__("buffer").__getattr__("read").__call__(var2, var1.getlocal(6));
                        var1.setlocal(9, var4);
                        var4 = null;
                        var1.setline(1827);
                        var1.getlocal(0).__getattr__("_set_decoded_chars").__call__(var2, var1.getlocal(0).__getattr__("_decoder").__getattr__("decode").__call__(var2, var1.getlocal(9), var1.getlocal(7)));
                        var1.setline(1829);
                        var10 = new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(9)});
                        var1.getlocal(0).__setattr__((String)"_snapshot", var10);
                        var4 = null;
                        var1.setline(1832);
                        var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_decoded_chars"));
                        var10000 = var4._lt(var1.getlocal(8));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(1833);
                           throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("can't restore logical file position")));
                        }

                        var1.setline(1834);
                        var4 = var1.getlocal(8);
                        var1.getlocal(0).__setattr__("_decoded_chars_used", var4);
                        var4 = null;
                     }

                     label80: {
                        try {
                           var1.setline(1838);
                           var10000 = var1.getlocal(0).__getattr__("_encoder");
                           if (!var10000.__nonzero__()) {
                              var10000 = var1.getlocal(0).__getattr__("_get_encoder").__call__(var2);
                           }

                           var4 = var10000;
                           var1.setlocal(10, var4);
                           var4 = null;
                        } catch (Throwable var7) {
                           PyException var11 = Py.setException(var7, var1);
                           if (var11.match(var1.getglobal("LookupError"))) {
                              var1.setline(1841);
                              break label80;
                           }

                           throw var11;
                        }

                        var1.setline(1843);
                        PyObject var9 = var1.getlocal(1);
                        var10000 = var9._ne(Py.newInteger(0));
                        var5 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(1844);
                           var1.getlocal(10).__getattr__("setstate").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                        } else {
                           var1.setline(1846);
                           var1.getlocal(10).__getattr__("reset").__call__(var2);
                        }
                     }

                     var1.setline(1847);
                     var3 = var1.getlocal(1);
                     var1.f_lasti = -1;
                     return var3;
                  }
               }
            }
         }
      }
   }

   public PyObject read$164(PyFrame var1, ThreadState var2) {
      var1.setline(1850);
      var1.getlocal(0).__getattr__("_checkReadable").__call__(var2);
      var1.setline(1851);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1852);
         PyInteger var6 = Py.newInteger(-1);
         var1.setlocal(1, var6);
         var3 = null;
      }

      var1.setline(1853);
      var10000 = var1.getlocal(0).__getattr__("_decoder");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("_get_decoder").__call__(var2);
      }

      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;

      try {
         var1.setline(1855);
         var1.getlocal(1).__getattr__("__index__");
      } catch (Throwable var5) {
         PyException var7 = Py.setException(var5, var1);
         if (var7.match(var1.getglobal("AttributeError"))) {
            var1.setline(1857);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("an integer is required")));
         }

         throw var7;
      }

      var1.setline(1858);
      var3 = var1.getlocal(1);
      var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1860);
         var10000 = var1.getlocal(0).__getattr__("_get_decoded_chars").__call__(var2);
         PyObject var10001 = var1.getlocal(2).__getattr__("decode");
         PyObject[] var8 = new PyObject[]{var1.getlocal(0).__getattr__("buffer").__getattr__("read").__call__(var2), var1.getglobal("True")};
         String[] var9 = new String[]{"final"};
         var10001 = var10001.__call__(var2, var8, var9);
         var3 = null;
         var3 = var10000._add(var10001);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1862);
         var1.getlocal(0).__getattr__("_set_decoded_chars").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned(""));
         var1.setline(1863);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("_snapshot", var3);
         var3 = null;
         var1.setline(1864);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1867);
         PyObject var4 = var1.getglobal("False");
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(1868);
         var4 = var1.getlocal(0).__getattr__("_get_decoded_chars").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var4);
         var4 = null;

         while(true) {
            var1.setline(1869);
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
            var10000 = var4._lt(var1.getlocal(1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(4).__not__();
            }

            if (!var10000.__nonzero__()) {
               var1.setline(1872);
               var3 = var1.getlocal(3);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(1870);
            var4 = var1.getlocal(0).__getattr__("_read_chunk").__call__(var2).__not__();
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(1871);
            var4 = var1.getlocal(3);
            var4 = var4._iadd(var1.getlocal(0).__getattr__("_get_decoded_chars").__call__(var2, var1.getlocal(1)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(3)))));
            var1.setlocal(3, var4);
         }
      }
   }

   public PyObject next$165(PyFrame var1, ThreadState var2) {
      var1.setline(1875);
      PyObject var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("_telling", var3);
      var3 = null;
      var1.setline(1876);
      var3 = var1.getlocal(0).__getattr__("readline").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1877);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(1878);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("_snapshot", var3);
         var3 = null;
         var1.setline(1879);
         var3 = var1.getlocal(0).__getattr__("_seekable");
         var1.getlocal(0).__setattr__("_telling", var3);
         var3 = null;
         var1.setline(1880);
         throw Py.makeException(var1.getglobal("StopIteration"));
      } else {
         var1.setline(1881);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject readline$166(PyFrame var1, ThreadState var2) {
      var1.setline(1884);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(1885);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("read from closed file")));
      } else {
         var1.setline(1886);
         PyObject var3 = var1.getlocal(1);
         PyObject var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         PyInteger var5;
         if (var10000.__nonzero__()) {
            var1.setline(1887);
            var5 = Py.newInteger(-1);
            var1.setlocal(1, var5);
            var3 = null;
         } else {
            var1.setline(1888);
            if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__not__().__nonzero__()) {
               var1.setline(1889);
               throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("limit must be an integer")));
            }
         }

         var1.setline(1892);
         var3 = var1.getlocal(0).__getattr__("_get_decoded_chars").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1894);
         var5 = Py.newInteger(0);
         var1.setlocal(3, var5);
         var3 = null;
         var1.setline(1896);
         if (var1.getlocal(0).__getattr__("_decoder").__not__().__nonzero__()) {
            var1.setline(1897);
            var1.getlocal(0).__getattr__("_get_decoder").__call__(var2);
         }

         var1.setline(1899);
         var3 = var1.getglobal("None");
         var1.setlocal(4, var3);
         var1.setlocal(5, var3);

         while(true) {
            var1.setline(1900);
            if (!var1.getglobal("True").__nonzero__()) {
               break;
            }

            var1.setline(1901);
            if (var1.getlocal(0).__getattr__("_readtranslate").__nonzero__()) {
               var1.setline(1903);
               var3 = var1.getlocal(2).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\n"), (PyObject)var1.getlocal(3));
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(1904);
               var3 = var1.getlocal(4);
               var10000 = var3._ge(Py.newInteger(0));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1905);
                  var3 = var1.getlocal(4)._add(Py.newInteger(1));
                  var1.setlocal(5, var3);
                  var3 = null;
                  break;
               }

               var1.setline(1908);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
               var1.setlocal(3, var3);
               var3 = null;
            } else {
               var1.setline(1910);
               if (var1.getlocal(0).__getattr__("_readuniversal").__nonzero__()) {
                  var1.setline(1915);
                  var3 = var1.getlocal(2).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\n"), (PyObject)var1.getlocal(3));
                  var1.setlocal(6, var3);
                  var3 = null;
                  var1.setline(1916);
                  var3 = var1.getlocal(2).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\r"), (PyObject)var1.getlocal(3));
                  var1.setlocal(7, var3);
                  var3 = null;
                  var1.setline(1917);
                  var3 = var1.getlocal(7);
                  var10000 = var3._eq(Py.newInteger(-1));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(1925);
                     var3 = var1.getlocal(6);
                     var10000 = var3._eq(Py.newInteger(-1));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(1927);
                        var3 = var1.getlocal(7)._add(Py.newInteger(1));
                        var1.setlocal(5, var3);
                        var3 = null;
                     } else {
                        var1.setline(1929);
                        var3 = var1.getlocal(6);
                        var10000 = var3._lt(var1.getlocal(7));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(1931);
                           var3 = var1.getlocal(6)._add(Py.newInteger(1));
                           var1.setlocal(5, var3);
                           var3 = null;
                        } else {
                           var1.setline(1933);
                           var3 = var1.getlocal(6);
                           var10000 = var3._eq(var1.getlocal(7)._add(Py.newInteger(1)));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(1935);
                              var3 = var1.getlocal(7)._add(Py.newInteger(2));
                              var1.setlocal(5, var3);
                              var3 = null;
                           } else {
                              var1.setline(1939);
                              var3 = var1.getlocal(7)._add(Py.newInteger(1));
                              var1.setlocal(5, var3);
                              var3 = null;
                           }
                        }
                     }
                     break;
                  }

                  var1.setline(1918);
                  var3 = var1.getlocal(6);
                  var10000 = var3._eq(Py.newInteger(-1));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(1923);
                     var3 = var1.getlocal(6)._add(Py.newInteger(1));
                     var1.setlocal(5, var3);
                     var3 = null;
                     break;
                  }

                  var1.setline(1920);
                  var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
                  var1.setlocal(3, var3);
                  var3 = null;
               } else {
                  var1.setline(1943);
                  var3 = var1.getlocal(2).__getattr__("find").__call__(var2, var1.getlocal(0).__getattr__("_readnl"));
                  var1.setlocal(4, var3);
                  var3 = null;
                  var1.setline(1944);
                  var3 = var1.getlocal(4);
                  var10000 = var3._ge(Py.newInteger(0));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1945);
                     var3 = var1.getlocal(4)._add(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_readnl")));
                     var1.setlocal(5, var3);
                     var3 = null;
                     break;
                  }
               }
            }

            var1.setline(1948);
            var3 = var1.getlocal(1);
            var10000 = var3._ge(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
               var10000 = var3._ge(var1.getlocal(1));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(1949);
               var3 = var1.getlocal(1);
               var1.setlocal(5, var3);
               var3 = null;
               break;
            }

            do {
               var1.setline(1953);
               if (!var1.getlocal(0).__getattr__("_read_chunk").__call__(var2).__nonzero__()) {
                  break;
               }

               var1.setline(1954);
            } while(!var1.getlocal(0).__getattr__("_decoded_chars").__nonzero__());

            var1.setline(1956);
            if (!var1.getlocal(0).__getattr__("_decoded_chars").__nonzero__()) {
               var1.setline(1960);
               var1.getlocal(0).__getattr__("_set_decoded_chars").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned(""));
               var1.setline(1961);
               var3 = var1.getglobal("None");
               var1.getlocal(0).__setattr__("_snapshot", var3);
               var3 = null;
               var1.setline(1962);
               var3 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(1957);
            var3 = var1.getlocal(2);
            var3 = var3._iadd(var1.getlocal(0).__getattr__("_get_decoded_chars").__call__(var2));
            var1.setlocal(2, var3);
         }

         var1.setline(1964);
         PyObject var4 = var1.getlocal(1);
         var10000 = var4._ge(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var4 = var1.getlocal(5);
            var10000 = var4._gt(var1.getlocal(1));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1965);
            var4 = var1.getlocal(1);
            var1.setlocal(5, var4);
            var4 = null;
         }

         var1.setline(1968);
         var1.getlocal(0).__getattr__("_rewind_decoded_chars").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(2))._sub(var1.getlocal(5)));
         var1.setline(1969);
         var3 = var1.getlocal(2).__getslice__((PyObject)null, var1.getlocal(5), (PyObject)null);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject newlines$167(PyFrame var1, ThreadState var2) {
      var1.setline(1973);
      var1.setline(1973);
      PyObject var3 = var1.getlocal(0).__getattr__("_decoder").__nonzero__() ? var1.getlocal(0).__getattr__("_decoder").__getattr__("newlines") : var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject StringIO$168(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyUnicode.fromInterned("Text I/O implementation using an in-memory buffer.\n\n    The initial_value argument sets the value of object.  The newline\n    argument is like the one of TextIOWrapper's constructor.\n    "));
      var1.setline(1981);
      PyUnicode.fromInterned("Text I/O implementation using an in-memory buffer.\n\n    The initial_value argument sets the value of object.  The newline\n    argument is like the one of TextIOWrapper's constructor.\n    ");
      var1.setline(1983);
      PyObject[] var3 = new PyObject[]{PyUnicode.fromInterned(""), PyUnicode.fromInterned("\n")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$169, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1998);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getvalue$170, (PyObject)null);
      var1.setlocal("getvalue", var4);
      var3 = null;
      var1.setline(2002);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$171, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(2007);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, errors$172, (PyObject)null);
      PyObject var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("errors", var5);
      var3 = null;
      var1.setline(2011);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, encoding$173, (PyObject)null);
      var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("encoding", var5);
      var3 = null;
      var1.setline(2015);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, detach$174, (PyObject)null);
      var1.setlocal("detach", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$169(PyFrame var1, ThreadState var2) {
      var1.setline(1984);
      PyObject var10000 = var1.getglobal("super").__call__(var2, var1.getglobal("StringIO"), var1.getlocal(0)).__getattr__("__init__");
      PyObject[] var3 = new PyObject[]{var1.getglobal("BytesIO").__call__(var2), PyUnicode.fromInterned("utf-8"), PyUnicode.fromInterned("strict"), var1.getlocal(2)};
      String[] var4 = new String[]{"encoding", "errors", "newline"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(1990);
      PyObject var5 = var1.getlocal(2);
      var10000 = var5._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1991);
         var5 = var1.getglobal("False");
         var1.getlocal(0).__setattr__("_writetranslate", var5);
         var3 = null;
      }

      var1.setline(1992);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(1993);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("unicode")).__not__().__nonzero__()) {
            var1.setline(1994);
            var5 = var1.getglobal("unicode").__call__(var2, var1.getlocal(1));
            var1.setlocal(1, var5);
            var3 = null;
         }

         var1.setline(1995);
         var1.getlocal(0).__getattr__("write").__call__(var2, var1.getlocal(1));
         var1.setline(1996);
         var1.getlocal(0).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getvalue$170(PyFrame var1, ThreadState var2) {
      var1.setline(1999);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(2000);
      PyObject var3 = var1.getlocal(0).__getattr__("buffer").__getattr__("getvalue").__call__(var2).__getattr__("decode").__call__(var2, var1.getlocal(0).__getattr__("_encoding"), var1.getlocal(0).__getattr__("_errors"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$171(PyFrame var1, ThreadState var2) {
      var1.setline(2005);
      PyObject var3 = var1.getglobal("object").__getattr__("__repr__").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject errors$172(PyFrame var1, ThreadState var2) {
      var1.setline(2009);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject encoding$173(PyFrame var1, ThreadState var2) {
      var1.setline(2013);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject detach$174(PyFrame var1, ThreadState var2) {
      var1.setline(2017);
      var1.getlocal(0).__getattr__("_unsupported").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("detach"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public _pyio$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      BlockingIOError$1 = Py.newCode(0, var2, var1, "BlockingIOError", 32, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "errno", "strerror", "characters_written"};
      __init__$2 = Py.newCode(4, var2, var1, "__init__", 36, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"file", "mode", "buffering", "encoding", "errors", "newline", "closefd", "modes", "reading", "writing", "appending", "updating", "text", "binary", "raw", "line_buffering", "bs", "buffer"};
      open$3 = Py.newCode(7, var2, var1, "open", 43, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DocDescriptor$4 = Py.newCode(0, var2, var1, "DocDescriptor", 229, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "obj", "typ"};
      __get__$5 = Py.newCode(3, var2, var1, "__get__", 232, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      OpenWrapper$6 = Py.newCode(0, var2, var1, "OpenWrapper", 238, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls", "args", "kwargs"};
      __new__$7 = Py.newCode(3, var2, var1, "__new__", 248, true, true, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      UnsupportedOperation$8 = Py.newCode(0, var2, var1, "UnsupportedOperation", 252, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      IOBase$9 = Py.newCode(0, var2, var1, "IOBase", 256, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name"};
      _unsupported$10 = Py.newCode(2, var2, var1, "_unsupported", 291, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos", "whence"};
      seek$11 = Py.newCode(3, var2, var1, "seek", 298, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tell$12 = Py.newCode(1, var2, var1, "tell", 313, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos"};
      truncate$13 = Py.newCode(2, var2, var1, "truncate", 317, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      flush$14 = Py.newCode(1, var2, var1, "flush", 327, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$15 = Py.newCode(1, var2, var1, "close", 337, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __del__$16 = Py.newCode(1, var2, var1, "__del__", 348, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      seekable$17 = Py.newCode(1, var2, var1, "seekable", 362, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      _checkSeekable$18 = Py.newCode(2, var2, var1, "_checkSeekable", 370, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      readable$19 = Py.newCode(1, var2, var1, "readable", 378, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      _checkReadable$20 = Py.newCode(2, var2, var1, "_checkReadable", 385, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      writable$21 = Py.newCode(1, var2, var1, "writable", 392, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      _checkWritable$22 = Py.newCode(2, var2, var1, "_checkWritable", 399, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      closed$23 = Py.newCode(1, var2, var1, "closed", 406, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      _checkClosed$24 = Py.newCode(2, var2, var1, "_checkClosed", 414, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __enter__$25 = Py.newCode(1, var2, var1, "__enter__", 423, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      __exit__$26 = Py.newCode(2, var2, var1, "__exit__", 428, true, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      fileno$27 = Py.newCode(1, var2, var1, "fileno", 436, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isatty$28 = Py.newCode(1, var2, var1, "isatty", 443, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "limit", "nreadahead", "res", "b"};
      String[] var10001 = var2;
      _pyio$py var10007 = self;
      var2 = new String[]{"limit", "self"};
      readline$29 = Py.newCode(2, var10001, var1, "readline", 453, false, false, var10007, 29, var2, (String[])null, 0, 4097);
      var2 = new String[]{"readahead", "n"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self", "limit"};
      nreadahead$30 = Py.newCode(0, var10001, var1, "nreadahead", 464, false, false, var10007, 30, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      nreadahead$31 = Py.newCode(0, var2, var1, "nreadahead", 473, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __iter__$32 = Py.newCode(1, var2, var1, "__iter__", 489, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      next$33 = Py.newCode(1, var2, var1, "next", 493, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "hint", "n", "lines", "line"};
      readlines$34 = Py.newCode(2, var2, var1, "readlines", 499, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "lines", "line"};
      writelines$35 = Py.newCode(2, var2, var1, "writelines", 519, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      RawIOBase$36 = Py.newCode(0, var2, var1, "RawIOBase", 527, false, false, self, 36, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "n", "b"};
      read$37 = Py.newCode(2, var2, var1, "read", 541, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "res", "data"};
      readall$38 = Py.newCode(1, var2, var1, "readall", 558, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "b"};
      readinto$39 = Py.newCode(2, var2, var1, "readinto", 572, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "b"};
      write$40 = Py.newCode(2, var2, var1, "write", 580, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BufferedIOBase$41 = Py.newCode(0, var2, var1, "BufferedIOBase", 592, false, false, self, 41, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "n"};
      read$42 = Py.newCode(2, var2, var1, "read", 609, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      read1$43 = Py.newCode(2, var2, var1, "read1", 629, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "b", "data", "n", "err", "array"};
      readinto$44 = Py.newCode(2, var2, var1, "readinto", 633, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "b"};
      write$45 = Py.newCode(2, var2, var1, "write", 656, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      detach$46 = Py.newCode(1, var2, var1, "detach", 667, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _BufferedIOMixin$47 = Py.newCode(0, var2, var1, "_BufferedIOMixin", 679, false, false, self, 47, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "raw"};
      __init__$48 = Py.newCode(2, var2, var1, "__init__", 688, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos", "whence", "new_position"};
      seek$49 = Py.newCode(3, var2, var1, "seek", 693, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos"};
      tell$50 = Py.newCode(1, var2, var1, "tell", 699, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos"};
      truncate$51 = Py.newCode(2, var2, var1, "truncate", 705, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      flush$52 = Py.newCode(1, var2, var1, "flush", 719, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$53 = Py.newCode(1, var2, var1, "close", 724, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "raw"};
      detach$54 = Py.newCode(1, var2, var1, "detach", 732, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      seekable$55 = Py.newCode(1, var2, var1, "seekable", 742, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      readable$56 = Py.newCode(1, var2, var1, "readable", 745, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      writable$57 = Py.newCode(1, var2, var1, "writable", 748, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      raw$58 = Py.newCode(1, var2, var1, "raw", 751, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      closed$59 = Py.newCode(1, var2, var1, "closed", 755, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      name$60 = Py.newCode(1, var2, var1, "name", 759, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      mode$61 = Py.newCode(1, var2, var1, "mode", 763, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "clsname", "name"};
      __repr__$62 = Py.newCode(1, var2, var1, "__repr__", 767, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      fileno$63 = Py.newCode(1, var2, var1, "fileno", 778, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isatty$64 = Py.newCode(1, var2, var1, "isatty", 781, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BytesIO$65 = Py.newCode(0, var2, var1, "BytesIO", 785, false, false, self, 65, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "initial_bytes", "buf"};
      __init__$66 = Py.newCode(2, var2, var1, "__init__", 789, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __getstate__$67 = Py.newCode(1, var2, var1, "__getstate__", 796, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getvalue$68 = Py.newCode(1, var2, var1, "getvalue", 801, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "newpos", "b"};
      read$69 = Py.newCode(2, var2, var1, "read", 808, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      read1$70 = Py.newCode(2, var2, var1, "read1", 825, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "b", "n", "pos", "padding"};
      write$71 = Py.newCode(2, var2, var1, "write", 830, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos", "whence"};
      seek$72 = Py.newCode(3, var2, var1, "seek", 848, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tell$73 = Py.newCode(1, var2, var1, "tell", 867, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos"};
      truncate$74 = Py.newCode(2, var2, var1, "truncate", 872, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      readable$75 = Py.newCode(1, var2, var1, "readable", 887, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      writable$76 = Py.newCode(1, var2, var1, "writable", 892, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      seekable$77 = Py.newCode(1, var2, var1, "seekable", 897, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BufferedReader$78 = Py.newCode(0, var2, var1, "BufferedReader", 903, false, false, self, 78, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "raw", "buffer_size"};
      __init__$79 = Py.newCode(3, var2, var1, "__init__", 914, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _reset_read_buf$80 = Py.newCode(1, var2, var1, "_reset_read_buf", 927, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      read$81 = Py.newCode(2, var2, var1, "read", 931, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "nodata_val", "empty_values", "buf", "pos", "chunks", "current_size", "chunk", "e", "avail", "wanted", "out"};
      _read_unlocked$82 = Py.newCode(2, var2, var1, "_read_unlocked", 944, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      peek$83 = Py.newCode(2, var2, var1, "peek", 1000, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "want", "have", "to_read", "current", "e"};
      _peek_unlocked$84 = Py.newCode(2, var2, var1, "_peek_unlocked", 1010, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      read1$85 = Py.newCode(2, var2, var1, "read1", 1028, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tell$86 = Py.newCode(1, var2, var1, "tell", 1041, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos", "whence"};
      seek$87 = Py.newCode(3, var2, var1, "seek", 1044, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BufferedWriter$88 = Py.newCode(0, var2, var1, "BufferedWriter", 1054, false, false, self, 88, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "raw", "buffer_size", "max_buffer_size"};
      __init__$89 = Py.newCode(4, var2, var1, "__init__", 1065, false, false, self, 89, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "b", "before", "written", "e", "overage"};
      write$90 = Py.newCode(2, var2, var1, "write", 1080, false, false, self, 90, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos"};
      truncate$91 = Py.newCode(2, var2, var1, "truncate", 1108, false, false, self, 91, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      flush$92 = Py.newCode(1, var2, var1, "flush", 1115, false, false, self, 92, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "e"};
      _flush_unlocked$93 = Py.newCode(1, var2, var1, "_flush_unlocked", 1119, false, false, self, 93, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tell$94 = Py.newCode(1, var2, var1, "tell", 1140, false, false, self, 94, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos", "whence"};
      seek$95 = Py.newCode(3, var2, var1, "seek", 1143, false, false, self, 95, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BufferedRWPair$96 = Py.newCode(0, var2, var1, "BufferedRWPair", 1151, false, false, self, 96, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "reader", "writer", "buffer_size", "max_buffer_size"};
      __init__$97 = Py.newCode(5, var2, var1, "__init__", 1167, false, false, self, 97, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      read$98 = Py.newCode(2, var2, var1, "read", 1185, false, false, self, 98, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "b"};
      readinto$99 = Py.newCode(2, var2, var1, "readinto", 1190, false, false, self, 99, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "b"};
      write$100 = Py.newCode(2, var2, var1, "write", 1193, false, false, self, 100, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      peek$101 = Py.newCode(2, var2, var1, "peek", 1196, false, false, self, 101, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      read1$102 = Py.newCode(2, var2, var1, "read1", 1199, false, false, self, 102, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      readable$103 = Py.newCode(1, var2, var1, "readable", 1202, false, false, self, 103, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      writable$104 = Py.newCode(1, var2, var1, "writable", 1205, false, false, self, 104, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      flush$105 = Py.newCode(1, var2, var1, "flush", 1208, false, false, self, 105, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$106 = Py.newCode(1, var2, var1, "close", 1211, false, false, self, 106, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isatty$107 = Py.newCode(1, var2, var1, "isatty", 1215, false, false, self, 107, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      closed$108 = Py.newCode(1, var2, var1, "closed", 1218, false, false, self, 108, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BufferedRandom$109 = Py.newCode(0, var2, var1, "BufferedRandom", 1223, false, false, self, 109, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "raw", "buffer_size", "max_buffer_size"};
      __init__$110 = Py.newCode(4, var2, var1, "__init__", 1234, false, false, self, 110, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos", "whence"};
      seek$111 = Py.newCode(3, var2, var1, "seek", 1240, false, false, self, 111, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tell$112 = Py.newCode(1, var2, var1, "tell", 1257, false, false, self, 112, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos"};
      truncate$113 = Py.newCode(2, var2, var1, "truncate", 1263, false, false, self, 113, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      read$114 = Py.newCode(2, var2, var1, "read", 1269, false, false, self, 114, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "b"};
      readinto$115 = Py.newCode(2, var2, var1, "readinto", 1275, false, false, self, 115, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      peek$116 = Py.newCode(2, var2, var1, "peek", 1279, false, false, self, 116, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      read1$117 = Py.newCode(2, var2, var1, "read1", 1283, false, false, self, 117, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "b"};
      write$118 = Py.newCode(2, var2, var1, "write", 1287, false, false, self, 118, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TextIOBase$119 = Py.newCode(0, var2, var1, "TextIOBase", 1296, false, false, self, 119, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "n"};
      read$120 = Py.newCode(2, var2, var1, "read", 1305, false, false, self, 120, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s"};
      write$121 = Py.newCode(2, var2, var1, "write", 1313, false, false, self, 121, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos"};
      truncate$122 = Py.newCode(2, var2, var1, "truncate", 1317, false, false, self, 122, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      readline$123 = Py.newCode(1, var2, var1, "readline", 1321, false, false, self, 123, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      detach$124 = Py.newCode(1, var2, var1, "detach", 1328, false, false, self, 124, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      encoding$125 = Py.newCode(1, var2, var1, "encoding", 1337, false, false, self, 125, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      newlines$126 = Py.newCode(1, var2, var1, "newlines", 1342, false, false, self, 126, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      errors$127 = Py.newCode(1, var2, var1, "errors", 1352, false, false, self, 127, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalNewlineDecoder$128 = Py.newCode(0, var2, var1, "IncrementalNewlineDecoder", 1362, false, false, self, 128, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "decoder", "translate", "errors"};
      __init__$129 = Py.newCode(4, var2, var1, "__init__", 1369, false, false, self, 129, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "final", "output", "crlf", "cr", "lf"};
      decode$130 = Py.newCode(3, var2, var1, "decode", 1376, false, false, self, 130, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "buf", "flag"};
      getstate$131 = Py.newCode(1, var2, var1, "getstate", 1407, false, false, self, 131, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "state", "buf", "flag"};
      setstate$132 = Py.newCode(2, var2, var1, "setstate", 1418, false, false, self, 132, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$133 = Py.newCode(1, var2, var1, "reset", 1424, false, false, self, 133, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      newlines$134 = Py.newCode(1, var2, var1, "newlines", 1434, false, false, self, 134, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TextIOWrapper$135 = Py.newCode(0, var2, var1, "TextIOWrapper", 1447, false, false, self, 135, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "buffer", "encoding", "errors", "newline", "line_buffering", "locale", "position"};
      __init__$136 = Py.newCode(6, var2, var1, "__init__", 1473, false, false, self, 136, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      __repr__$137 = Py.newCode(1, var2, var1, "__repr__", 1531, false, false, self, 137, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      encoding$138 = Py.newCode(1, var2, var1, "encoding", 1540, false, false, self, 138, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      errors$139 = Py.newCode(1, var2, var1, "errors", 1544, false, false, self, 139, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      line_buffering$140 = Py.newCode(1, var2, var1, "line_buffering", 1548, false, false, self, 140, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      buffer$141 = Py.newCode(1, var2, var1, "buffer", 1552, false, false, self, 141, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      seekable$142 = Py.newCode(1, var2, var1, "seekable", 1556, false, false, self, 142, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      readable$143 = Py.newCode(1, var2, var1, "readable", 1561, false, false, self, 143, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      writable$144 = Py.newCode(1, var2, var1, "writable", 1564, false, false, self, 144, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      flush$145 = Py.newCode(1, var2, var1, "flush", 1567, false, false, self, 145, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$146 = Py.newCode(1, var2, var1, "close", 1571, false, false, self, 146, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      closed$147 = Py.newCode(1, var2, var1, "closed", 1578, false, false, self, 147, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      name$148 = Py.newCode(1, var2, var1, "name", 1582, false, false, self, 148, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      fileno$149 = Py.newCode(1, var2, var1, "fileno", 1586, false, false, self, 149, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isatty$150 = Py.newCode(1, var2, var1, "isatty", 1589, false, false, self, 150, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "length", "haslf", "encoder", "b"};
      write$151 = Py.newCode(2, var2, var1, "write", 1592, false, false, self, 151, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "make_encoder"};
      _get_encoder$152 = Py.newCode(1, var2, var1, "_get_encoder", 1613, false, false, self, 152, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "make_decoder", "decoder"};
      _get_decoder$153 = Py.newCode(1, var2, var1, "_get_decoder", 1618, false, false, self, 153, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "chars"};
      _set_decoded_chars$154 = Py.newCode(2, var2, var1, "_set_decoded_chars", 1629, false, false, self, 154, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "offset", "chars"};
      _get_decoded_chars$155 = Py.newCode(2, var2, var1, "_get_decoded_chars", 1634, false, false, self, 155, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      _rewind_decoded_chars$156 = Py.newCode(2, var2, var1, "_rewind_decoded_chars", 1644, false, false, self, 156, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dec_buffer", "dec_flags", "input_chunk", "eof"};
      _read_chunk$157 = Py.newCode(1, var2, var1, "_read_chunk", 1650, false, false, self, 157, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "position", "dec_flags", "bytes_to_feed", "need_eof", "chars_to_skip"};
      _pack_cookie$158 = Py.newCode(6, var2, var1, "_pack_cookie", 1684, false, false, self, 158, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "bigint", "rest", "position", "dec_flags", "bytes_to_feed", "need_eof", "chars_to_skip"};
      _unpack_cookie$159 = Py.newCode(2, var2, var1, "_unpack_cookie", 1694, false, false, self, 159, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "position", "decoder", "dec_flags", "next_input", "chars_to_skip", "saved_state", "start_pos", "start_flags", "bytes_fed", "chars_decoded", "need_eof", "next_byte", "dec_buffer"};
      tell$160 = Py.newCode(1, var2, var1, "tell", 1701, false, false, self, 160, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos"};
      truncate$161 = Py.newCode(2, var2, var1, "truncate", 1763, false, false, self, 161, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "buffer"};
      detach$162 = Py.newCode(1, var2, var1, "detach", 1769, false, false, self, 162, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cookie", "whence", "position", "start_pos", "dec_flags", "bytes_to_feed", "need_eof", "chars_to_skip", "input_chunk", "encoder"};
      seek$163 = Py.newCode(3, var2, var1, "seek", 1777, false, false, self, 163, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "decoder", "result", "eof"};
      read$164 = Py.newCode(2, var2, var1, "read", 1849, false, false, self, 164, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      next$165 = Py.newCode(1, var2, var1, "next", 1874, false, false, self, 165, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "limit", "line", "start", "pos", "endpos", "nlpos", "crpos"};
      readline$166 = Py.newCode(2, var2, var1, "readline", 1883, false, false, self, 166, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      newlines$167 = Py.newCode(1, var2, var1, "newlines", 1971, false, false, self, 167, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StringIO$168 = Py.newCode(0, var2, var1, "StringIO", 1976, false, false, self, 168, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "initial_value", "newline"};
      __init__$169 = Py.newCode(3, var2, var1, "__init__", 1983, false, false, self, 169, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getvalue$170 = Py.newCode(1, var2, var1, "getvalue", 1998, false, false, self, 170, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$171 = Py.newCode(1, var2, var1, "__repr__", 2002, false, false, self, 171, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      errors$172 = Py.newCode(1, var2, var1, "errors", 2007, false, false, self, 172, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      encoding$173 = Py.newCode(1, var2, var1, "encoding", 2011, false, false, self, 173, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      detach$174 = Py.newCode(1, var2, var1, "detach", 2015, false, false, self, 174, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new _pyio$py("_pyio$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(_pyio$py.class);
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
            return this.open$3(var2, var3);
         case 4:
            return this.DocDescriptor$4(var2, var3);
         case 5:
            return this.__get__$5(var2, var3);
         case 6:
            return this.OpenWrapper$6(var2, var3);
         case 7:
            return this.__new__$7(var2, var3);
         case 8:
            return this.UnsupportedOperation$8(var2, var3);
         case 9:
            return this.IOBase$9(var2, var3);
         case 10:
            return this._unsupported$10(var2, var3);
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
            return this.__del__$16(var2, var3);
         case 17:
            return this.seekable$17(var2, var3);
         case 18:
            return this._checkSeekable$18(var2, var3);
         case 19:
            return this.readable$19(var2, var3);
         case 20:
            return this._checkReadable$20(var2, var3);
         case 21:
            return this.writable$21(var2, var3);
         case 22:
            return this._checkWritable$22(var2, var3);
         case 23:
            return this.closed$23(var2, var3);
         case 24:
            return this._checkClosed$24(var2, var3);
         case 25:
            return this.__enter__$25(var2, var3);
         case 26:
            return this.__exit__$26(var2, var3);
         case 27:
            return this.fileno$27(var2, var3);
         case 28:
            return this.isatty$28(var2, var3);
         case 29:
            return this.readline$29(var2, var3);
         case 30:
            return this.nreadahead$30(var2, var3);
         case 31:
            return this.nreadahead$31(var2, var3);
         case 32:
            return this.__iter__$32(var2, var3);
         case 33:
            return this.next$33(var2, var3);
         case 34:
            return this.readlines$34(var2, var3);
         case 35:
            return this.writelines$35(var2, var3);
         case 36:
            return this.RawIOBase$36(var2, var3);
         case 37:
            return this.read$37(var2, var3);
         case 38:
            return this.readall$38(var2, var3);
         case 39:
            return this.readinto$39(var2, var3);
         case 40:
            return this.write$40(var2, var3);
         case 41:
            return this.BufferedIOBase$41(var2, var3);
         case 42:
            return this.read$42(var2, var3);
         case 43:
            return this.read1$43(var2, var3);
         case 44:
            return this.readinto$44(var2, var3);
         case 45:
            return this.write$45(var2, var3);
         case 46:
            return this.detach$46(var2, var3);
         case 47:
            return this._BufferedIOMixin$47(var2, var3);
         case 48:
            return this.__init__$48(var2, var3);
         case 49:
            return this.seek$49(var2, var3);
         case 50:
            return this.tell$50(var2, var3);
         case 51:
            return this.truncate$51(var2, var3);
         case 52:
            return this.flush$52(var2, var3);
         case 53:
            return this.close$53(var2, var3);
         case 54:
            return this.detach$54(var2, var3);
         case 55:
            return this.seekable$55(var2, var3);
         case 56:
            return this.readable$56(var2, var3);
         case 57:
            return this.writable$57(var2, var3);
         case 58:
            return this.raw$58(var2, var3);
         case 59:
            return this.closed$59(var2, var3);
         case 60:
            return this.name$60(var2, var3);
         case 61:
            return this.mode$61(var2, var3);
         case 62:
            return this.__repr__$62(var2, var3);
         case 63:
            return this.fileno$63(var2, var3);
         case 64:
            return this.isatty$64(var2, var3);
         case 65:
            return this.BytesIO$65(var2, var3);
         case 66:
            return this.__init__$66(var2, var3);
         case 67:
            return this.__getstate__$67(var2, var3);
         case 68:
            return this.getvalue$68(var2, var3);
         case 69:
            return this.read$69(var2, var3);
         case 70:
            return this.read1$70(var2, var3);
         case 71:
            return this.write$71(var2, var3);
         case 72:
            return this.seek$72(var2, var3);
         case 73:
            return this.tell$73(var2, var3);
         case 74:
            return this.truncate$74(var2, var3);
         case 75:
            return this.readable$75(var2, var3);
         case 76:
            return this.writable$76(var2, var3);
         case 77:
            return this.seekable$77(var2, var3);
         case 78:
            return this.BufferedReader$78(var2, var3);
         case 79:
            return this.__init__$79(var2, var3);
         case 80:
            return this._reset_read_buf$80(var2, var3);
         case 81:
            return this.read$81(var2, var3);
         case 82:
            return this._read_unlocked$82(var2, var3);
         case 83:
            return this.peek$83(var2, var3);
         case 84:
            return this._peek_unlocked$84(var2, var3);
         case 85:
            return this.read1$85(var2, var3);
         case 86:
            return this.tell$86(var2, var3);
         case 87:
            return this.seek$87(var2, var3);
         case 88:
            return this.BufferedWriter$88(var2, var3);
         case 89:
            return this.__init__$89(var2, var3);
         case 90:
            return this.write$90(var2, var3);
         case 91:
            return this.truncate$91(var2, var3);
         case 92:
            return this.flush$92(var2, var3);
         case 93:
            return this._flush_unlocked$93(var2, var3);
         case 94:
            return this.tell$94(var2, var3);
         case 95:
            return this.seek$95(var2, var3);
         case 96:
            return this.BufferedRWPair$96(var2, var3);
         case 97:
            return this.__init__$97(var2, var3);
         case 98:
            return this.read$98(var2, var3);
         case 99:
            return this.readinto$99(var2, var3);
         case 100:
            return this.write$100(var2, var3);
         case 101:
            return this.peek$101(var2, var3);
         case 102:
            return this.read1$102(var2, var3);
         case 103:
            return this.readable$103(var2, var3);
         case 104:
            return this.writable$104(var2, var3);
         case 105:
            return this.flush$105(var2, var3);
         case 106:
            return this.close$106(var2, var3);
         case 107:
            return this.isatty$107(var2, var3);
         case 108:
            return this.closed$108(var2, var3);
         case 109:
            return this.BufferedRandom$109(var2, var3);
         case 110:
            return this.__init__$110(var2, var3);
         case 111:
            return this.seek$111(var2, var3);
         case 112:
            return this.tell$112(var2, var3);
         case 113:
            return this.truncate$113(var2, var3);
         case 114:
            return this.read$114(var2, var3);
         case 115:
            return this.readinto$115(var2, var3);
         case 116:
            return this.peek$116(var2, var3);
         case 117:
            return this.read1$117(var2, var3);
         case 118:
            return this.write$118(var2, var3);
         case 119:
            return this.TextIOBase$119(var2, var3);
         case 120:
            return this.read$120(var2, var3);
         case 121:
            return this.write$121(var2, var3);
         case 122:
            return this.truncate$122(var2, var3);
         case 123:
            return this.readline$123(var2, var3);
         case 124:
            return this.detach$124(var2, var3);
         case 125:
            return this.encoding$125(var2, var3);
         case 126:
            return this.newlines$126(var2, var3);
         case 127:
            return this.errors$127(var2, var3);
         case 128:
            return this.IncrementalNewlineDecoder$128(var2, var3);
         case 129:
            return this.__init__$129(var2, var3);
         case 130:
            return this.decode$130(var2, var3);
         case 131:
            return this.getstate$131(var2, var3);
         case 132:
            return this.setstate$132(var2, var3);
         case 133:
            return this.reset$133(var2, var3);
         case 134:
            return this.newlines$134(var2, var3);
         case 135:
            return this.TextIOWrapper$135(var2, var3);
         case 136:
            return this.__init__$136(var2, var3);
         case 137:
            return this.__repr__$137(var2, var3);
         case 138:
            return this.encoding$138(var2, var3);
         case 139:
            return this.errors$139(var2, var3);
         case 140:
            return this.line_buffering$140(var2, var3);
         case 141:
            return this.buffer$141(var2, var3);
         case 142:
            return this.seekable$142(var2, var3);
         case 143:
            return this.readable$143(var2, var3);
         case 144:
            return this.writable$144(var2, var3);
         case 145:
            return this.flush$145(var2, var3);
         case 146:
            return this.close$146(var2, var3);
         case 147:
            return this.closed$147(var2, var3);
         case 148:
            return this.name$148(var2, var3);
         case 149:
            return this.fileno$149(var2, var3);
         case 150:
            return this.isatty$150(var2, var3);
         case 151:
            return this.write$151(var2, var3);
         case 152:
            return this._get_encoder$152(var2, var3);
         case 153:
            return this._get_decoder$153(var2, var3);
         case 154:
            return this._set_decoded_chars$154(var2, var3);
         case 155:
            return this._get_decoded_chars$155(var2, var3);
         case 156:
            return this._rewind_decoded_chars$156(var2, var3);
         case 157:
            return this._read_chunk$157(var2, var3);
         case 158:
            return this._pack_cookie$158(var2, var3);
         case 159:
            return this._unpack_cookie$159(var2, var3);
         case 160:
            return this.tell$160(var2, var3);
         case 161:
            return this.truncate$161(var2, var3);
         case 162:
            return this.detach$162(var2, var3);
         case 163:
            return this.seek$163(var2, var3);
         case 164:
            return this.read$164(var2, var3);
         case 165:
            return this.next$165(var2, var3);
         case 166:
            return this.readline$166(var2, var3);
         case 167:
            return this.newlines$167(var2, var3);
         case 168:
            return this.StringIO$168(var2, var3);
         case 169:
            return this.__init__$169(var2, var3);
         case 170:
            return this.getvalue$170(var2, var3);
         case 171:
            return this.__repr__$171(var2, var3);
         case 172:
            return this.errors$172(var2, var3);
         case 173:
            return this.encoding$173(var2, var3);
         case 174:
            return this.detach$174(var2, var3);
         default:
            return null;
      }
   }
}
