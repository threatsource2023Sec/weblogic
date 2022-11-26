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
@MTime(1498849384000L)
@Filename("gzip.py")
public class gzip$py extends PyFunctionTable implements PyRunnable {
   static gzip$py self;
   static final PyCode f$0;
   static final PyCode write32u$1;
   static final PyCode read32$2;
   static final PyCode open$3;
   static final PyCode GzipFile$4;
   static final PyCode __init__$5;
   static final PyCode filename$6;
   static final PyCode __repr__$7;
   static final PyCode _check_closed$8;
   static final PyCode _init_write$9;
   static final PyCode _write_gzip_header$10;
   static final PyCode _init_read$11;
   static final PyCode _read_gzip_header$12;
   static final PyCode write$13;
   static final PyCode read$14;
   static final PyCode _unread$15;
   static final PyCode _read$16;
   static final PyCode _add_read_data$17;
   static final PyCode _read_eof$18;
   static final PyCode closed$19;
   static final PyCode close$20;
   static final PyCode __enter__$21;
   static final PyCode flush$22;
   static final PyCode fileno$23;
   static final PyCode rewind$24;
   static final PyCode readable$25;
   static final PyCode writable$26;
   static final PyCode seekable$27;
   static final PyCode seek$28;
   static final PyCode readline$29;
   static final PyCode _test$30;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Functions that read and write gzipped files.\n\nThe user of the file doesn't have to worry about the compression,\nbut random access is not allowed."));
      var1.setline(4);
      PyString.fromInterned("Functions that read and write gzipped files.\n\nThe user of the file doesn't have to worry about the compression,\nbut random access is not allowed.");
      var1.setline(8);
      PyObject var3 = imp.importOne("struct", var1, -1);
      var1.setlocal("struct", var3);
      var3 = null;
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var3 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var3);
      var3 = null;
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(9);
      var3 = imp.importOne("zlib", var1, -1);
      var1.setlocal("zlib", var3);
      var3 = null;
      var1.setline(10);
      var3 = imp.importOne("io", var1, -1);
      var1.setlocal("io", var3);
      var3 = null;
      var1.setline(11);
      var3 = imp.importOne("__builtin__", var1, -1);
      var1.setlocal("__builtin__", var3);
      var3 = null;
      var1.setline(13);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("GzipFile"), PyString.fromInterned("open")});
      var1.setlocal("__all__", var7);
      var3 = null;
      var1.setline(15);
      PyTuple var8 = new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(2), Py.newInteger(4), Py.newInteger(8), Py.newInteger(16)});
      PyObject[] var4 = Py.unpackSequence(var8, 5);
      PyObject var5 = var4[0];
      var1.setlocal("FTEXT", var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal("FHCRC", var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal("FEXTRA", var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal("FNAME", var5);
      var5 = null;
      var5 = var4[4];
      var1.setlocal("FCOMMENT", var5);
      var5 = null;
      var3 = null;
      var1.setline(17);
      var8 = new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(2)});
      var4 = Py.unpackSequence(var8, 2);
      var5 = var4[0];
      var1.setlocal("READ", var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal("WRITE", var5);
      var5 = null;
      var3 = null;
      var1.setline(19);
      PyObject[] var9 = Py.EmptyObjects;
      PyFunction var10 = new PyFunction(var1.f_globals, var9, write32u$1, (PyObject)null);
      var1.setlocal("write32u", var10);
      var3 = null;
      var1.setline(24);
      var9 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var9, read32$2, (PyObject)null);
      var1.setlocal("read32", var10);
      var3 = null;
      var1.setline(27);
      var9 = new PyObject[]{PyString.fromInterned("rb"), Py.newInteger(9)};
      var10 = new PyFunction(var1.f_globals, var9, open$3, PyString.fromInterned("Shorthand for GzipFile(filename, mode, compresslevel).\n\n    The filename argument is required; mode defaults to 'rb'\n    and compresslevel defaults to 9.\n\n    "));
      var1.setlocal("open", var10);
      var3 = null;
      var1.setline(36);
      var9 = new PyObject[]{var1.getname("io").__getattr__("BufferedIOBase")};
      PyObject var6 = Py.makeClass("GzipFile", var9, GzipFile$4);
      var1.setlocal("GzipFile", var6);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(497);
      var9 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var9, _test$30, (PyObject)null);
      var1.setlocal("_test", var10);
      var3 = null;
      var1.setline(535);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(536);
         var1.getname("_test").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject write32u$1(PyFrame var1, ThreadState var2) {
      var1.setline(22);
      var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("struct").__getattr__("pack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<L"), (PyObject)var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read32$2(PyFrame var1, ThreadState var2) {
      var1.setline(25);
      PyObject var3 = var1.getglobal("struct").__getattr__("unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<I"), (PyObject)var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(4))).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject open$3(PyFrame var1, ThreadState var2) {
      var1.setline(33);
      PyString.fromInterned("Shorthand for GzipFile(filename, mode, compresslevel).\n\n    The filename argument is required; mode defaults to 'rb'\n    and compresslevel defaults to 9.\n\n    ");
      var1.setline(34);
      PyObject var3 = var1.getglobal("GzipFile").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject GzipFile$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("The GzipFile class simulates most of the methods of a file object with\n    the exception of the readinto() and truncate() methods.\n\n    "));
      var1.setline(40);
      PyString.fromInterned("The GzipFile class simulates most of the methods of a file object with\n    the exception of the readinto() and truncate() methods.\n\n    ");
      var1.setline(42);
      PyObject var3 = var1.getname("None");
      var1.setlocal("myfileobj", var3);
      var3 = null;
      var1.setline(43);
      var3 = Py.newInteger(10)._mul(Py.newInteger(1024))._mul(Py.newInteger(1024));
      var1.setlocal("max_read_chunk", var3);
      var3 = null;
      var1.setline(45);
      PyObject[] var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), Py.newInteger(9), var1.getname("None"), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$5, PyString.fromInterned("Constructor for the GzipFile class.\n\n        At least one of fileobj and filename must be given a\n        non-trivial value.\n\n        The new class instance is based on fileobj, which can be a regular\n        file, a StringIO object, or any other object which simulates a file.\n        It defaults to None, in which case filename is opened to provide\n        a file object.\n\n        When fileobj is not None, the filename argument is only used to be\n        included in the gzip file header, which may includes the original\n        filename of the uncompressed file.  It defaults to the filename of\n        fileobj, if discernible; otherwise, it defaults to the empty string,\n        and in this case the original filename is not included in the header.\n\n        The mode argument can be any of 'r', 'rb', 'a', 'ab', 'w', or 'wb',\n        depending on whether the file will be read or written.  The default\n        is the mode of fileobj if discernible; otherwise, the default is 'rb'.\n        Be aware that only the 'rb', 'ab', and 'wb' values should be used\n        for cross-platform portability.\n\n        The compresslevel argument is an integer from 0 to 9 controlling the\n        level of compression; 1 is fastest and produces the least compression,\n        and 9 is slowest and produces the most compression. 0 is no compression\n        at all. The default is 9.\n\n        The mtime argument is an optional numeric timestamp to be written\n        to the stream when compressing.  All gzip compressed streams\n        are required to contain a timestamp.  If omitted or None, the\n        current time is used.  This module ignores the timestamp when\n        decompressing; however, some programs, such as gunzip, make use\n        of it.  The format of the timestamp is the same as that of the\n        return value of time.time() and of the st_mtime member of the\n        object returned by os.stat().\n\n        "));
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(138);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, filename$6, (PyObject)null);
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("filename", var3);
      var3 = null;
      var1.setline(146);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __repr__$7, (PyObject)null);
      var1.setlocal("__repr__", var5);
      var3 = null;
      var1.setline(150);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _check_closed$8, PyString.fromInterned("Raises a ValueError if the underlying file object has been closed.\n\n        "));
      var1.setlocal("_check_closed", var5);
      var3 = null;
      var1.setline(157);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _init_write$9, (PyObject)null);
      var1.setlocal("_init_write", var5);
      var3 = null;
      var1.setline(164);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _write_gzip_header$10, (PyObject)null);
      var1.setlocal("_write_gzip_header", var5);
      var3 = null;
      var1.setline(190);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _init_read$11, (PyObject)null);
      var1.setlocal("_init_read", var5);
      var3 = null;
      var1.setline(194);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _read_gzip_header$12, (PyObject)null);
      var1.setlocal("_read_gzip_header", var5);
      var3 = null;
      var1.setline(227);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, write$13, (PyObject)null);
      var1.setlocal("write", var5);
      var3 = null;
      var1.setline(248);
      var4 = new PyObject[]{Py.newInteger(-1)};
      var5 = new PyFunction(var1.f_globals, var4, read$14, (PyObject)null);
      var1.setlocal("read", var5);
      var3 = null;
      var1.setline(281);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _unread$15, (PyObject)null);
      var1.setlocal("_unread", var5);
      var3 = null;
      var1.setline(285);
      var4 = new PyObject[]{Py.newInteger(1024)};
      var5 = new PyFunction(var1.f_globals, var4, _read$16, (PyObject)null);
      var1.setlocal("_read", var5);
      var3 = null;
      var1.setline(335);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _add_read_data$17, (PyObject)null);
      var1.setlocal("_add_read_data", var5);
      var3 = null;
      var1.setline(343);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _read_eof$18, (PyObject)null);
      var1.setlocal("_read_eof", var5);
      var3 = null;
      var1.setline(367);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, closed$19, (PyObject)null);
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("closed", var3);
      var3 = null;
      var1.setline(371);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, close$20, (PyObject)null);
      var1.setlocal("close", var5);
      var3 = null;
      var1.setline(388);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __enter__$21, (PyObject)null);
      var1.setlocal("__enter__", var5);
      var3 = null;
      var1.setline(397);
      var4 = new PyObject[]{var1.getname("zlib").__getattr__("Z_SYNC_FLUSH")};
      var5 = new PyFunction(var1.f_globals, var4, flush$22, (PyObject)null);
      var1.setlocal("flush", var5);
      var3 = null;
      var1.setline(404);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, fileno$23, PyString.fromInterned("Invoke the underlying file object's fileno() method.\n\n        This will raise AttributeError if the underlying file object\n        doesn't support fileno().\n        "));
      var1.setlocal("fileno", var5);
      var3 = null;
      var1.setline(412);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, rewind$24, PyString.fromInterned("Return the uncompressed stream file position indicator to the\n        beginning of the file"));
      var1.setlocal("rewind", var5);
      var3 = null;
      var1.setline(424);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, readable$25, (PyObject)null);
      var1.setlocal("readable", var5);
      var3 = null;
      var1.setline(427);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, writable$26, (PyObject)null);
      var1.setlocal("writable", var5);
      var3 = null;
      var1.setline(430);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, seekable$27, (PyObject)null);
      var1.setlocal("seekable", var5);
      var3 = null;
      var1.setline(433);
      var4 = new PyObject[]{Py.newInteger(0)};
      var5 = new PyFunction(var1.f_globals, var4, seek$28, (PyObject)null);
      var1.setlocal("seek", var5);
      var3 = null;
      var1.setline(457);
      var4 = new PyObject[]{Py.newInteger(-1)};
      var5 = new PyFunction(var1.f_globals, var4, readline$29, (PyObject)null);
      var1.setlocal("readline", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$5(PyFrame var1, ThreadState var2) {
      var1.setline(83);
      PyString.fromInterned("Constructor for the GzipFile class.\n\n        At least one of fileobj and filename must be given a\n        non-trivial value.\n\n        The new class instance is based on fileobj, which can be a regular\n        file, a StringIO object, or any other object which simulates a file.\n        It defaults to None, in which case filename is opened to provide\n        a file object.\n\n        When fileobj is not None, the filename argument is only used to be\n        included in the gzip file header, which may includes the original\n        filename of the uncompressed file.  It defaults to the filename of\n        fileobj, if discernible; otherwise, it defaults to the empty string,\n        and in this case the original filename is not included in the header.\n\n        The mode argument can be any of 'r', 'rb', 'a', 'ab', 'w', or 'wb',\n        depending on whether the file will be read or written.  The default\n        is the mode of fileobj if discernible; otherwise, the default is 'rb'.\n        Be aware that only the 'rb', 'ab', and 'wb' values should be used\n        for cross-platform portability.\n\n        The compresslevel argument is an integer from 0 to 9 controlling the\n        level of compression; 1 is fastest and produces the least compression,\n        and 9 is slowest and produces the most compression. 0 is no compression\n        at all. The default is 9.\n\n        The mtime argument is an optional numeric timestamp to be written\n        to the stream when compressing.  All gzip compressed streams\n        are required to contain a timestamp.  If omitted or None, the\n        current time is used.  This module ignores the timestamp when\n        decompressing; however, some programs, such as gunzip, make use\n        of it.  The format of the timestamp is the same as that of the\n        return value of time.time() and of the st_mtime member of the\n        object returned by os.stat().\n\n        ");
      var1.setline(87);
      PyObject var3;
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(88);
         var3 = var1.getlocal(2).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("U"), (PyObject)PyString.fromInterned(""));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(91);
      PyObject var10000 = var1.getlocal(2);
      PyString var4;
      if (var10000.__nonzero__()) {
         var4 = PyString.fromInterned("b");
         var10000 = var4._notin(var1.getlocal(2));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(92);
         var3 = var1.getlocal(2);
         var3 = var3._iadd(PyString.fromInterned("b"));
         var1.setlocal(2, var3);
      }

      var1.setline(93);
      var3 = var1.getlocal(4);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(94);
         var10000 = var1.getglobal("__builtin__").__getattr__("open");
         PyObject var10002 = var1.getlocal(1);
         Object var10003 = var1.getlocal(2);
         if (!((PyObject)var10003).__nonzero__()) {
            var10003 = PyString.fromInterned("rb");
         }

         var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)var10003);
         var1.setlocal(4, var3);
         var1.getlocal(0).__setattr__("myfileobj", var3);
      }

      var1.setline(95);
      var3 = var1.getlocal(1);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(98);
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("name"));
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(4).__getattr__("name");
            var10000 = var3._ne(PyString.fromInterned("<fdopen>"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(99);
            var3 = var1.getlocal(4).__getattr__("name");
            var1.setlocal(1, var3);
            var3 = null;
         } else {
            var1.setline(101);
            var4 = PyString.fromInterned("");
            var1.setlocal(1, var4);
            var3 = null;
         }
      }

      var1.setline(102);
      var3 = var1.getlocal(2);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(103);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("mode")).__nonzero__()) {
            var1.setline(103);
            var3 = var1.getlocal(4).__getattr__("mode");
            var1.setlocal(2, var3);
            var3 = null;
         } else {
            var1.setline(104);
            var4 = PyString.fromInterned("rb");
            var1.setlocal(2, var4);
            var3 = null;
         }
      }

      var1.setline(106);
      var3 = var1.getlocal(2).__getslice__(Py.newInteger(0), Py.newInteger(1), (PyObject)null);
      var10000 = var3._eq(PyString.fromInterned("r"));
      var3 = null;
      PyInteger var5;
      if (var10000.__nonzero__()) {
         var1.setline(107);
         var3 = var1.getglobal("READ");
         var1.getlocal(0).__setattr__("mode", var3);
         var3 = null;
         var1.setline(109);
         var3 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("_new_member", var3);
         var3 = null;
         var1.setline(113);
         var4 = PyString.fromInterned("");
         var1.getlocal(0).__setattr__((String)"extrabuf", var4);
         var3 = null;
         var1.setline(114);
         var5 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"extrasize", var5);
         var3 = null;
         var1.setline(115);
         var5 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"extrastart", var5);
         var3 = null;
         var1.setline(116);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("name", var3);
         var3 = null;
         var1.setline(118);
         var5 = Py.newInteger(100);
         var1.getlocal(0).__setattr__((String)"min_readsize", var5);
         var3 = null;
      } else {
         var1.setline(120);
         var3 = var1.getlocal(2).__getslice__(Py.newInteger(0), Py.newInteger(1), (PyObject)null);
         var10000 = var3._eq(PyString.fromInterned("w"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(2).__getslice__(Py.newInteger(0), Py.newInteger(1), (PyObject)null);
            var10000 = var3._eq(PyString.fromInterned("a"));
            var3 = null;
         }

         if (!var10000.__nonzero__()) {
            var1.setline(129);
            throw Py.makeException(var1.getglobal("IOError"), PyString.fromInterned("Mode ")._add(var1.getlocal(2))._add(PyString.fromInterned(" not supported")));
         }

         var1.setline(121);
         var3 = var1.getglobal("WRITE");
         var1.getlocal(0).__setattr__("mode", var3);
         var3 = null;
         var1.setline(122);
         var1.getlocal(0).__getattr__("_init_write").__call__(var2, var1.getlocal(1));
         var1.setline(123);
         var10000 = var1.getglobal("zlib").__getattr__("compressobj");
         PyObject[] var6 = new PyObject[]{var1.getlocal(3), var1.getglobal("zlib").__getattr__("DEFLATED"), var1.getglobal("zlib").__getattr__("MAX_WBITS").__neg__(), var1.getglobal("zlib").__getattr__("DEF_MEM_LEVEL"), Py.newInteger(0)};
         var3 = var10000.__call__(var2, var6);
         var1.getlocal(0).__setattr__("compress", var3);
         var3 = null;
      }

      var1.setline(131);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("fileobj", var3);
      var3 = null;
      var1.setline(132);
      var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"offset", var5);
      var3 = null;
      var1.setline(133);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("mtime", var3);
      var3 = null;
      var1.setline(135);
      var3 = var1.getlocal(0).__getattr__("mode");
      var10000 = var3._eq(var1.getglobal("WRITE"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(136);
         var1.getlocal(0).__getattr__("_write_gzip_header").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject filename$6(PyFrame var1, ThreadState var2) {
      var1.setline(140);
      PyObject var3 = imp.importOne("warnings", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(141);
      var1.getlocal(1).__getattr__("warn").__call__((ThreadState)var2, PyString.fromInterned("use the name attribute"), (PyObject)var1.getglobal("DeprecationWarning"), (PyObject)Py.newInteger(2));
      var1.setline(142);
      var3 = var1.getlocal(0).__getattr__("mode");
      PyObject var10000 = var3._eq(var1.getglobal("WRITE"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("name").__getslice__(Py.newInteger(-3), (PyObject)null, (PyObject)null);
         var10000 = var3._ne(PyString.fromInterned(".gz"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(143);
         var3 = var1.getlocal(0).__getattr__("name")._add(PyString.fromInterned(".gz"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(144);
         var3 = var1.getlocal(0).__getattr__("name");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __repr__$7(PyFrame var1, ThreadState var2) {
      var1.setline(147);
      PyObject var3 = var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("fileobj"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(148);
      var3 = PyString.fromInterned("<gzip ")._add(var1.getlocal(1).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null))._add(PyString.fromInterned(" "))._add(var1.getglobal("hex").__call__(var2, var1.getglobal("id").__call__(var2, var1.getlocal(0))))._add(PyString.fromInterned(">"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _check_closed$8(PyFrame var1, ThreadState var2) {
      var1.setline(153);
      PyString.fromInterned("Raises a ValueError if the underlying file object has been closed.\n\n        ");
      var1.setline(154);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(155);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("I/O operation on closed file.")));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _init_write$9(PyFrame var1, ThreadState var2) {
      var1.setline(158);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("name", var3);
      var3 = null;
      var1.setline(159);
      var3 = var1.getglobal("zlib").__getattr__("crc32").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""))._and(Py.newLong("4294967295"));
      var1.getlocal(0).__setattr__("crc", var3);
      var3 = null;
      var1.setline(160);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"size", var4);
      var3 = null;
      var1.setline(161);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"writebuf", var5);
      var3 = null;
      var1.setline(162);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"bufsize", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _write_gzip_header$10(PyFrame var1, ThreadState var2) {
      var1.setline(165);
      var1.getlocal(0).__getattr__("fileobj").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\u001f\u008b"));
      var1.setline(166);
      var1.getlocal(0).__getattr__("fileobj").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\b"));

      PyException var3;
      PyObject var6;
      try {
         var1.setline(170);
         var6 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(0).__getattr__("name"));
         var1.setlocal(1, var6);
         var3 = null;
         var1.setline(171);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str")).__not__().__nonzero__()) {
            var1.setline(172);
            var6 = var1.getlocal(1).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("latin-1"));
            var1.setlocal(1, var6);
            var3 = null;
         }

         var1.setline(173);
         if (var1.getlocal(1).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".gz")).__nonzero__()) {
            var1.setline(174);
            var6 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-3), (PyObject)null);
            var1.setlocal(1, var6);
            var3 = null;
         }
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (!var3.match(var1.getglobal("UnicodeEncodeError"))) {
            throw var3;
         }

         var1.setline(176);
         PyString var4 = PyString.fromInterned("");
         var1.setlocal(1, var4);
         var4 = null;
      }

      var1.setline(177);
      PyInteger var7 = Py.newInteger(0);
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(178);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(179);
         var6 = var1.getglobal("FNAME");
         var1.setlocal(2, var6);
         var3 = null;
      }

      var1.setline(180);
      var1.getlocal(0).__getattr__("fileobj").__getattr__("write").__call__(var2, var1.getglobal("chr").__call__(var2, var1.getlocal(2)));
      var1.setline(181);
      var6 = var1.getlocal(0).__getattr__("mtime");
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(182);
      var6 = var1.getlocal(3);
      PyObject var10000 = var6._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(183);
         var6 = var1.getglobal("time").__getattr__("time").__call__(var2);
         var1.setlocal(3, var6);
         var3 = null;
      }

      var1.setline(184);
      var1.getglobal("write32u").__call__(var2, var1.getlocal(0).__getattr__("fileobj"), var1.getglobal("long").__call__(var2, var1.getlocal(3)));
      var1.setline(185);
      var1.getlocal(0).__getattr__("fileobj").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\u0002"));
      var1.setline(186);
      var1.getlocal(0).__getattr__("fileobj").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ÿ"));
      var1.setline(187);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(188);
         var1.getlocal(0).__getattr__("fileobj").__getattr__("write").__call__(var2, var1.getlocal(1)._add(PyString.fromInterned("\u0000")));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _init_read$11(PyFrame var1, ThreadState var2) {
      var1.setline(191);
      PyObject var3 = var1.getglobal("zlib").__getattr__("crc32").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""))._and(Py.newLong("4294967295"));
      var1.getlocal(0).__setattr__("crc", var3);
      var3 = null;
      var1.setline(192);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"size", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _read_gzip_header$12(PyFrame var1, ThreadState var2) {
      var1.setline(195);
      PyObject var3 = var1.getlocal(0).__getattr__("fileobj").__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(196);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._ne(PyString.fromInterned("\u001f\u008b"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(197);
         throw Py.makeException(var1.getglobal("IOError"), PyString.fromInterned("Not a gzipped file"));
      } else {
         var1.setline(198);
         var3 = var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getattr__("fileobj").__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(199);
         var3 = var1.getlocal(2);
         var10000 = var3._ne(Py.newInteger(8));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(200);
            throw Py.makeException(var1.getglobal("IOError"), PyString.fromInterned("Unknown compression method"));
         } else {
            var1.setline(201);
            var3 = var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getattr__("fileobj").__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(202);
            var3 = var1.getglobal("read32").__call__(var2, var1.getlocal(0).__getattr__("fileobj"));
            var1.getlocal(0).__setattr__("mtime", var3);
            var3 = null;
            var1.setline(205);
            var1.getlocal(0).__getattr__("fileobj").__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
            var1.setline(207);
            if (var1.getlocal(3)._and(var1.getglobal("FEXTRA")).__nonzero__()) {
               var1.setline(209);
               var3 = var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getattr__("fileobj").__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(210);
               var3 = var1.getlocal(4)._add(Py.newInteger(256)._mul(var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getattr__("fileobj").__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)))));
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(211);
               var1.getlocal(0).__getattr__("fileobj").__getattr__("read").__call__(var2, var1.getlocal(4));
            }

            var1.setline(212);
            if (var1.getlocal(3)._and(var1.getglobal("FNAME")).__nonzero__()) {
               do {
                  var1.setline(214);
                  if (!var1.getglobal("True").__nonzero__()) {
                     break;
                  }

                  var1.setline(215);
                  var3 = var1.getlocal(0).__getattr__("fileobj").__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
                  var1.setlocal(5, var3);
                  var3 = null;
                  var1.setline(216);
                  var10000 = var1.getlocal(5).__not__();
                  if (!var10000.__nonzero__()) {
                     var3 = var1.getlocal(5);
                     var10000 = var3._eq(PyString.fromInterned("\u0000"));
                     var3 = null;
                  }
               } while(!var10000.__nonzero__());
            }

            var1.setline(218);
            if (var1.getlocal(3)._and(var1.getglobal("FCOMMENT")).__nonzero__()) {
               do {
                  var1.setline(220);
                  if (!var1.getglobal("True").__nonzero__()) {
                     break;
                  }

                  var1.setline(221);
                  var3 = var1.getlocal(0).__getattr__("fileobj").__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
                  var1.setlocal(5, var3);
                  var3 = null;
                  var1.setline(222);
                  var10000 = var1.getlocal(5).__not__();
                  if (!var10000.__nonzero__()) {
                     var3 = var1.getlocal(5);
                     var10000 = var3._eq(PyString.fromInterned("\u0000"));
                     var3 = null;
                  }
               } while(!var10000.__nonzero__());
            }

            var1.setline(224);
            if (var1.getlocal(3)._and(var1.getglobal("FHCRC")).__nonzero__()) {
               var1.setline(225);
               var1.getlocal(0).__getattr__("fileobj").__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
            }

            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject write$13(PyFrame var1, ThreadState var2) {
      var1.setline(228);
      var1.getlocal(0).__getattr__("_check_closed").__call__(var2);
      var1.setline(229);
      PyObject var3 = var1.getlocal(0).__getattr__("mode");
      PyObject var10000 = var3._ne(var1.getglobal("WRITE"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(230);
         var3 = imp.importOne("errno", var1, -1);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(231);
         throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("EBADF"), (PyObject)PyString.fromInterned("write() on read-only GzipFile object")));
      } else {
         var1.setline(233);
         var3 = var1.getlocal(0).__getattr__("fileobj");
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(234);
            throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("write() on closed GzipFile object"));
         } else {
            var1.setline(237);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("memoryview")).__nonzero__()) {
               var1.setline(238);
               var3 = var1.getlocal(1).__getattr__("tobytes").__call__(var2);
               var1.setlocal(1, var3);
               var3 = null;
            }

            var1.setline(240);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
            var10000 = var3._gt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(241);
               var1.getlocal(0).__getattr__("fileobj").__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("compress").__getattr__("compress").__call__(var2, var1.getlocal(1)));
               var1.setline(242);
               var10000 = var1.getlocal(0);
               String var6 = "size";
               PyObject var4 = var10000;
               PyObject var5 = var4.__getattr__(var6);
               var5 = var5._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
               var4.__setattr__(var6, var5);
               var1.setline(243);
               var3 = var1.getglobal("zlib").__getattr__("crc32").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("crc"))._and(Py.newLong("4294967295"));
               var1.getlocal(0).__setattr__("crc", var3);
               var3 = null;
               var1.setline(244);
               var10000 = var1.getlocal(0);
               var6 = "offset";
               var4 = var10000;
               var5 = var4.__getattr__(var6);
               var5 = var5._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
               var4.__setattr__(var6, var5);
            }

            var1.setline(246);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject read$14(PyFrame var1, ThreadState var2) {
      var1.setline(249);
      var1.getlocal(0).__getattr__("_check_closed").__call__(var2);
      var1.setline(250);
      PyObject var3 = var1.getlocal(0).__getattr__("mode");
      PyObject var10000 = var3._ne(var1.getglobal("READ"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(251);
         var3 = imp.importOne("errno", var1, -1);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(252);
         throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("EBADF"), (PyObject)PyString.fromInterned("read() on write-only GzipFile object")));
      } else {
         var1.setline(254);
         var3 = var1.getlocal(0).__getattr__("extrasize");
         var10000 = var3._le(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(0).__getattr__("fileobj");
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(255);
            PyString var10 = PyString.fromInterned("");
            var1.f_lasti = -1;
            return var10;
         } else {
            var1.setline(257);
            PyInteger var4 = Py.newInteger(1024);
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(258);
            PyObject var9 = var1.getlocal(1);
            var10000 = var9._lt(Py.newInteger(0));
            var4 = null;
            PyObject var5;
            PyException var11;
            if (var10000.__nonzero__()) {
               try {
                  while(true) {
                     var1.setline(260);
                     if (!var1.getglobal("True").__nonzero__()) {
                        break;
                     }

                     var1.setline(261);
                     var1.getlocal(0).__getattr__("_read").__call__(var2, var1.getlocal(3));
                     var1.setline(262);
                     var9 = var1.getglobal("min").__call__(var2, var1.getlocal(0).__getattr__("max_read_chunk"), var1.getlocal(3)._mul(Py.newInteger(2)));
                     var1.setlocal(3, var9);
                     var4 = null;
                  }
               } catch (Throwable var8) {
                  var11 = Py.setException(var8, var1);
                  if (!var11.match(var1.getglobal("EOFError"))) {
                     throw var11;
                  }

                  var1.setline(264);
                  var5 = var1.getlocal(0).__getattr__("extrasize");
                  var1.setlocal(1, var5);
                  var5 = null;
               }
            } else {
               try {
                  while(true) {
                     var1.setline(267);
                     var9 = var1.getlocal(1);
                     var10000 = var9._gt(var1.getlocal(0).__getattr__("extrasize"));
                     var4 = null;
                     if (!var10000.__nonzero__()) {
                        break;
                     }

                     var1.setline(268);
                     var1.getlocal(0).__getattr__("_read").__call__(var2, var1.getlocal(3));
                     var1.setline(269);
                     var9 = var1.getglobal("min").__call__(var2, var1.getlocal(0).__getattr__("max_read_chunk"), var1.getlocal(3)._mul(Py.newInteger(2)));
                     var1.setlocal(3, var9);
                     var4 = null;
                  }
               } catch (Throwable var7) {
                  var11 = Py.setException(var7, var1);
                  if (!var11.match(var1.getglobal("EOFError"))) {
                     throw var11;
                  }

                  var1.setline(271);
                  var5 = var1.getlocal(1);
                  var10000 = var5._gt(var1.getlocal(0).__getattr__("extrasize"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(272);
                     var5 = var1.getlocal(0).__getattr__("extrasize");
                     var1.setlocal(1, var5);
                     var5 = null;
                  }
               }
            }

            var1.setline(274);
            var9 = var1.getlocal(0).__getattr__("offset")._sub(var1.getlocal(0).__getattr__("extrastart"));
            var1.setlocal(4, var9);
            var4 = null;
            var1.setline(275);
            var9 = var1.getlocal(0).__getattr__("extrabuf").__getslice__(var1.getlocal(4), var1.getlocal(4)._add(var1.getlocal(1)), (PyObject)null);
            var1.setlocal(5, var9);
            var4 = null;
            var1.setline(276);
            var9 = var1.getlocal(0).__getattr__("extrasize")._sub(var1.getlocal(1));
            var1.getlocal(0).__setattr__("extrasize", var9);
            var4 = null;
            var1.setline(278);
            var10000 = var1.getlocal(0);
            String var12 = "offset";
            var5 = var10000;
            PyObject var6 = var5.__getattr__(var12);
            var6 = var6._iadd(var1.getlocal(1));
            var5.__setattr__(var12, var6);
            var1.setline(279);
            var3 = var1.getlocal(5);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _unread$15(PyFrame var1, ThreadState var2) {
      var1.setline(282);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1))._add(var1.getlocal(0).__getattr__("extrasize"));
      var1.getlocal(0).__setattr__("extrasize", var3);
      var3 = null;
      var1.setline(283);
      PyObject var10000 = var1.getlocal(0);
      String var6 = "offset";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var6);
      var5 = var5._isub(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
      var4.__setattr__(var6, var5);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _read$16(PyFrame var1, ThreadState var2) {
      var1.setline(286);
      PyObject var3 = var1.getlocal(0).__getattr__("fileobj");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(287);
         throw Py.makeException(var1.getglobal("EOFError"), PyString.fromInterned("Reached EOF"));
      } else {
         var1.setline(289);
         if (var1.getlocal(0).__getattr__("_new_member").__nonzero__()) {
            var1.setline(295);
            var3 = var1.getlocal(0).__getattr__("fileobj").__getattr__("tell").__call__(var2);
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(296);
            var1.getlocal(0).__getattr__("fileobj").__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(2));
            var1.setline(297);
            var3 = var1.getlocal(2);
            var10000 = var3._eq(var1.getlocal(0).__getattr__("fileobj").__getattr__("tell").__call__(var2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(298);
               throw Py.makeException(var1.getglobal("EOFError"), PyString.fromInterned("Reached EOF"));
            }

            var1.setline(300);
            var1.getlocal(0).__getattr__("fileobj").__getattr__("seek").__call__(var2, var1.getlocal(2));
            var1.setline(302);
            var1.getlocal(0).__getattr__("_init_read").__call__(var2);
            var1.setline(303);
            var1.getlocal(0).__getattr__("_read_gzip_header").__call__(var2);
            var1.setline(304);
            var3 = var1.getglobal("zlib").__getattr__("decompressobj").__call__(var2, var1.getglobal("zlib").__getattr__("MAX_WBITS").__neg__());
            var1.getlocal(0).__setattr__("decompress", var3);
            var3 = null;
            var1.setline(305);
            var3 = var1.getglobal("False");
            var1.getlocal(0).__setattr__("_new_member", var3);
            var3 = null;
         }

         var1.setline(308);
         var3 = var1.getlocal(0).__getattr__("fileobj").__getattr__("read").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(313);
         var3 = var1.getlocal(3);
         var10000 = var3._eq(PyString.fromInterned(""));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(314);
            var3 = var1.getlocal(0).__getattr__("decompress").__getattr__("flush").__call__(var2);
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(315);
            var1.getlocal(0).__getattr__("_read_eof").__call__(var2);
            var1.setline(316);
            var1.getlocal(0).__getattr__("_add_read_data").__call__(var2, var1.getlocal(4));
            var1.setline(317);
            throw Py.makeException(var1.getglobal("EOFError"), PyString.fromInterned("Reached EOF"));
         } else {
            var1.setline(319);
            var3 = var1.getlocal(0).__getattr__("decompress").__getattr__("decompress").__call__(var2, var1.getlocal(3));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(320);
            var1.getlocal(0).__getattr__("_add_read_data").__call__(var2, var1.getlocal(4));
            var1.setline(322);
            var3 = var1.getlocal(0).__getattr__("decompress").__getattr__("unused_data");
            var10000 = var3._ne(PyString.fromInterned(""));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(328);
               var1.getlocal(0).__getattr__("fileobj").__getattr__("seek").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("decompress").__getattr__("unused_data")).__neg__()._add(Py.newInteger(8)), (PyObject)Py.newInteger(1));
               var1.setline(332);
               var1.getlocal(0).__getattr__("_read_eof").__call__(var2);
               var1.setline(333);
               var3 = var1.getglobal("True");
               var1.getlocal(0).__setattr__("_new_member", var3);
               var3 = null;
            }

            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject _add_read_data$17(PyFrame var1, ThreadState var2) {
      var1.setline(336);
      PyObject var3 = var1.getglobal("zlib").__getattr__("crc32").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("crc"))._and(Py.newLong("4294967295"));
      var1.getlocal(0).__setattr__("crc", var3);
      var3 = null;
      var1.setline(337);
      var3 = var1.getlocal(0).__getattr__("offset")._sub(var1.getlocal(0).__getattr__("extrastart"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(338);
      var3 = var1.getlocal(0).__getattr__("extrabuf").__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null)._add(var1.getlocal(1));
      var1.getlocal(0).__setattr__("extrabuf", var3);
      var3 = null;
      var1.setline(339);
      var3 = var1.getlocal(0).__getattr__("extrasize")._add(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
      var1.getlocal(0).__setattr__("extrasize", var3);
      var3 = null;
      var1.setline(340);
      var3 = var1.getlocal(0).__getattr__("offset");
      var1.getlocal(0).__setattr__("extrastart", var3);
      var3 = null;
      var1.setline(341);
      var3 = var1.getlocal(0).__getattr__("size")._add(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
      var1.getlocal(0).__setattr__("size", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _read_eof$18(PyFrame var1, ThreadState var2) {
      var1.setline(349);
      var1.getlocal(0).__getattr__("fileobj").__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(-8), (PyObject)Py.newInteger(1));
      var1.setline(350);
      PyObject var3 = var1.getglobal("read32").__call__(var2, var1.getlocal(0).__getattr__("fileobj"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(351);
      var3 = var1.getglobal("read32").__call__(var2, var1.getlocal(0).__getattr__("fileobj"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(352);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._ne(var1.getlocal(0).__getattr__("crc"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(353);
         throw Py.makeException(var1.getglobal("IOError").__call__(var2, PyString.fromInterned("CRC check failed %s != %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("hex").__call__(var2, var1.getlocal(1)), var1.getglobal("hex").__call__(var2, var1.getlocal(0).__getattr__("crc"))}))));
      } else {
         var1.setline(355);
         var3 = var1.getlocal(2);
         var10000 = var3._ne(var1.getlocal(0).__getattr__("size")._and(Py.newLong("4294967295")));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(356);
            throw Py.makeException(var1.getglobal("IOError"), PyString.fromInterned("Incorrect length of data produced"));
         } else {
            var1.setline(361);
            PyString var4 = PyString.fromInterned("\u0000");
            var1.setlocal(3, var4);
            var3 = null;

            while(true) {
               var1.setline(362);
               var3 = var1.getlocal(3);
               var10000 = var3._eq(PyString.fromInterned("\u0000"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(364);
                  if (var1.getlocal(3).__nonzero__()) {
                     var1.setline(365);
                     var1.getlocal(0).__getattr__("fileobj").__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(-1), (PyObject)Py.newInteger(1));
                  }

                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setline(363);
               var3 = var1.getlocal(0).__getattr__("fileobj").__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
               var1.setlocal(3, var3);
               var3 = null;
            }
         }
      }
   }

   public PyObject closed$19(PyFrame var1, ThreadState var2) {
      var1.setline(369);
      PyObject var3 = var1.getlocal(0).__getattr__("fileobj");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject close$20(PyFrame var1, ThreadState var2) {
      var1.setline(372);
      PyObject var3 = var1.getlocal(0).__getattr__("fileobj");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(373);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(374);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(375);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("fileobj", var3);
         var3 = null;
         var3 = null;

         PyObject var4;
         try {
            var1.setline(377);
            var4 = var1.getlocal(0).__getattr__("mode");
            var10000 = var4._eq(var1.getglobal("WRITE"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(378);
               var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("compress").__getattr__("flush").__call__(var2));
               var1.setline(379);
               var1.getglobal("write32u").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("crc"));
               var1.setline(381);
               var1.getglobal("write32u").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("size")._and(Py.newLong("4294967295")));
            }
         } catch (Throwable var5) {
            Py.addTraceback(var5, var1);
            var1.setline(383);
            var4 = var1.getlocal(0).__getattr__("myfileobj");
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(384);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(385);
               var4 = var1.getglobal("None");
               var1.getlocal(0).__setattr__("myfileobj", var4);
               var4 = null;
               var1.setline(386);
               var1.getlocal(2).__getattr__("close").__call__(var2);
            }

            throw (Throwable)var5;
         }

         var1.setline(383);
         var4 = var1.getlocal(0).__getattr__("myfileobj");
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(384);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(385);
            var4 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("myfileobj", var4);
            var4 = null;
            var1.setline(386);
            var1.getlocal(2).__getattr__("close").__call__(var2);
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject __enter__$21(PyFrame var1, ThreadState var2) {
      var1.setline(394);
      var1.getlocal(0).__getattr__("_check_closed").__call__(var2);
      var1.setline(395);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject flush$22(PyFrame var1, ThreadState var2) {
      var1.setline(398);
      var1.getlocal(0).__getattr__("_check_closed").__call__(var2);
      var1.setline(399);
      PyObject var3 = var1.getlocal(0).__getattr__("mode");
      PyObject var10000 = var3._eq(var1.getglobal("WRITE"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(401);
         var1.getlocal(0).__getattr__("fileobj").__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("compress").__getattr__("flush").__call__(var2, var1.getlocal(1)));
         var1.setline(402);
         var1.getlocal(0).__getattr__("fileobj").__getattr__("flush").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject fileno$23(PyFrame var1, ThreadState var2) {
      var1.setline(409);
      PyString.fromInterned("Invoke the underlying file object's fileno() method.\n\n        This will raise AttributeError if the underlying file object\n        doesn't support fileno().\n        ");
      var1.setline(410);
      PyObject var3 = var1.getlocal(0).__getattr__("fileobj").__getattr__("fileno").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject rewind$24(PyFrame var1, ThreadState var2) {
      var1.setline(414);
      PyString.fromInterned("Return the uncompressed stream file position indicator to the\n        beginning of the file");
      var1.setline(415);
      PyObject var3 = var1.getlocal(0).__getattr__("mode");
      PyObject var10000 = var3._ne(var1.getglobal("READ"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(416);
         throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Can't rewind in write mode")));
      } else {
         var1.setline(417);
         var1.getlocal(0).__getattr__("fileobj").__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setline(418);
         var3 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("_new_member", var3);
         var3 = null;
         var1.setline(419);
         PyString var4 = PyString.fromInterned("");
         var1.getlocal(0).__setattr__((String)"extrabuf", var4);
         var3 = null;
         var1.setline(420);
         PyInteger var5 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"extrasize", var5);
         var3 = null;
         var1.setline(421);
         var5 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"extrastart", var5);
         var3 = null;
         var1.setline(422);
         var5 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"offset", var5);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject readable$25(PyFrame var1, ThreadState var2) {
      var1.setline(425);
      PyObject var3 = var1.getlocal(0).__getattr__("mode");
      PyObject var10000 = var3._eq(var1.getglobal("READ"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject writable$26(PyFrame var1, ThreadState var2) {
      var1.setline(428);
      PyObject var3 = var1.getlocal(0).__getattr__("mode");
      PyObject var10000 = var3._eq(var1.getglobal("WRITE"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject seekable$27(PyFrame var1, ThreadState var2) {
      var1.setline(431);
      PyObject var3 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject seek$28(PyFrame var1, ThreadState var2) {
      var1.setline(434);
      PyObject var10000;
      PyObject var3;
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(435);
         var3 = var1.getlocal(2);
         var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(438);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Seek from end not supported")));
         }

         var1.setline(436);
         var3 = var1.getlocal(0).__getattr__("offset")._add(var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(439);
      var3 = var1.getlocal(0).__getattr__("mode");
      var10000 = var3._eq(var1.getglobal("WRITE"));
      var3 = null;
      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(440);
         var3 = var1.getlocal(1);
         var10000 = var3._lt(var1.getlocal(0).__getattr__("offset"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(441);
            throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Negative seek in write mode")));
         }

         var1.setline(442);
         var3 = var1.getlocal(1)._sub(var1.getlocal(0).__getattr__("offset"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(443);
         var3 = var1.getglobal("xrange").__call__(var2, var1.getlocal(3)._floordiv(Py.newInteger(1024))).__iter__();

         while(true) {
            var1.setline(443);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(445);
               var1.getlocal(0).__getattr__("write").__call__(var2, var1.getlocal(3)._mod(Py.newInteger(1024))._mul(PyString.fromInterned("\u0000")));
               break;
            }

            var1.setlocal(4, var4);
            var1.setline(444);
            var1.getlocal(0).__getattr__("write").__call__(var2, Py.newInteger(1024)._mul(PyString.fromInterned("\u0000")));
         }
      } else {
         var1.setline(446);
         var3 = var1.getlocal(0).__getattr__("mode");
         var10000 = var3._eq(var1.getglobal("READ"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(447);
            var3 = var1.getlocal(1);
            var10000 = var3._lt(var1.getlocal(0).__getattr__("offset"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(449);
               var1.getlocal(0).__getattr__("rewind").__call__(var2);
            }

            var1.setline(450);
            var3 = var1.getlocal(1)._sub(var1.getlocal(0).__getattr__("offset"));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(451);
            var3 = var1.getglobal("xrange").__call__(var2, var1.getlocal(3)._floordiv(Py.newInteger(1024))).__iter__();

            while(true) {
               var1.setline(451);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(453);
                  var1.getlocal(0).__getattr__("read").__call__(var2, var1.getlocal(3)._mod(Py.newInteger(1024)));
                  break;
               }

               var1.setlocal(4, var4);
               var1.setline(452);
               var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1024));
            }
         }
      }

      var1.setline(455);
      var3 = var1.getlocal(0).__getattr__("offset");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject readline$29(PyFrame var1, ThreadState var2) {
      var1.setline(458);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(460);
         var3 = var1.getlocal(0).__getattr__("offset")._sub(var1.getlocal(0).__getattr__("extrastart"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(461);
         var3 = var1.getlocal(0).__getattr__("extrabuf").__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"), (PyObject)var1.getlocal(2))._add(Py.newInteger(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(462);
         var3 = var1.getlocal(3);
         var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(463);
            var10000 = var1.getlocal(0);
            String var6 = "extrasize";
            var4 = var10000;
            PyObject var5 = var4.__getattr__(var6);
            var5 = var5._isub(var1.getlocal(3)._sub(var1.getlocal(2)));
            var4.__setattr__(var6, var5);
            var1.setline(464);
            var10000 = var1.getlocal(0);
            var6 = "offset";
            var4 = var10000;
            var5 = var4.__getattr__(var6);
            var5 = var5._iadd(var1.getlocal(3)._sub(var1.getlocal(2)));
            var4.__setattr__(var6, var5);
            var1.setline(465);
            var3 = var1.getlocal(0).__getattr__("extrabuf").__getslice__(var1.getlocal(2), var1.getlocal(3), (PyObject)null);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(467);
         var4 = var1.getglobal("sys").__getattr__("maxint");
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(468);
         var4 = var1.getlocal(0).__getattr__("min_readsize");
         var1.setlocal(4, var4);
         var4 = null;
      } else {
         var1.setline(470);
         var4 = var1.getlocal(1);
         var1.setlocal(4, var4);
         var4 = null;
      }

      var1.setline(471);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.setlocal(5, var7);
      var4 = null;

      while(true) {
         var1.setline(472);
         var4 = var1.getlocal(1);
         var10000 = var4._ne(Py.newInteger(0));
         var4 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(473);
         var4 = var1.getlocal(0).__getattr__("read").__call__(var2, var1.getlocal(4));
         var1.setlocal(6, var4);
         var4 = null;
         var1.setline(474);
         var4 = var1.getlocal(6).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(480);
         var4 = var1.getlocal(1);
         var10000 = var4._le(var1.getlocal(3));
         var4 = null;
         if (!var10000.__nonzero__()) {
            var4 = var1.getlocal(3);
            var10000 = var4._eq(Py.newInteger(-1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(6));
               var10000 = var4._gt(var1.getlocal(1));
               var4 = null;
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(481);
            var4 = var1.getlocal(1)._sub(Py.newInteger(1));
            var1.setlocal(3, var4);
            var4 = null;
         }

         var1.setline(483);
         var4 = var1.getlocal(3);
         var10000 = var4._ge(Py.newInteger(0));
         var4 = null;
         if (!var10000.__nonzero__()) {
            var4 = var1.getlocal(6);
            var10000 = var4._eq(PyString.fromInterned(""));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(484);
            var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(6).__getslice__((PyObject)null, var1.getlocal(3)._add(Py.newInteger(1)), (PyObject)null));
            var1.setline(485);
            var1.getlocal(0).__getattr__("_unread").__call__(var2, var1.getlocal(6).__getslice__(var1.getlocal(3)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null));
            break;
         }

         var1.setline(489);
         var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(6));
         var1.setline(490);
         var4 = var1.getlocal(1)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(6)));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(491);
         var4 = var1.getglobal("min").__call__(var2, var1.getlocal(1), var1.getlocal(4)._mul(Py.newInteger(2)));
         var1.setlocal(4, var4);
         var4 = null;
      }

      var1.setline(492);
      var4 = var1.getlocal(4);
      var10000 = var4._gt(var1.getlocal(0).__getattr__("min_readsize"));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(493);
         var4 = var1.getglobal("min").__call__((ThreadState)var2, var1.getlocal(4), (PyObject)var1.getlocal(0).__getattr__("min_readsize")._mul(Py.newInteger(2)), (PyObject)Py.newInteger(512));
         var1.getlocal(0).__setattr__("min_readsize", var4);
         var4 = null;
      }

      var1.setline(494);
      var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(5));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _test$30(PyFrame var1, ThreadState var2) {
      var1.setline(501);
      PyObject var3 = var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(502);
      PyObject var10000 = var1.getlocal(0);
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
         var10000 = var3._eq(PyString.fromInterned("-d"));
         var3 = null;
      }

      var3 = var10000;
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(503);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(504);
         var3 = var1.getlocal(0).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(505);
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(506);
         PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("-")});
         var1.setlocal(0, var7);
         var3 = null;
      }

      var1.setline(507);
      var3 = var1.getlocal(0).__iter__();

      while(true) {
         while(true) {
            var1.setline(507);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(2, var4);
            var1.setline(508);
            PyObject var5;
            String[] var6;
            PyObject[] var8;
            if (var1.getlocal(1).__nonzero__()) {
               var1.setline(509);
               var5 = var1.getlocal(2);
               var10000 = var5._eq(PyString.fromInterned("-"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(510);
                  var10000 = var1.getglobal("GzipFile");
                  var8 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("rb"), var1.getglobal("sys").__getattr__("stdin")};
                  var6 = new String[]{"filename", "mode", "fileobj"};
                  var10000 = var10000.__call__(var2, var8, var6);
                  var5 = null;
                  var5 = var10000;
                  var1.setlocal(3, var5);
                  var5 = null;
                  var1.setline(511);
                  var5 = var1.getglobal("sys").__getattr__("stdout");
                  var1.setlocal(4, var5);
                  var5 = null;
               } else {
                  var1.setline(513);
                  var5 = var1.getlocal(2).__getslice__(Py.newInteger(-3), (PyObject)null, (PyObject)null);
                  var10000 = var5._ne(PyString.fromInterned(".gz"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(514);
                     Py.printComma(PyString.fromInterned("filename doesn't end in .gz:"));
                     Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(2)));
                     continue;
                  }

                  var1.setline(516);
                  var5 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("rb"));
                  var1.setlocal(3, var5);
                  var5 = null;
                  var1.setline(517);
                  var5 = var1.getglobal("__builtin__").__getattr__("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(-3), (PyObject)null), (PyObject)PyString.fromInterned("wb"));
                  var1.setlocal(4, var5);
                  var5 = null;
               }
            } else {
               var1.setline(519);
               var5 = var1.getlocal(2);
               var10000 = var5._eq(PyString.fromInterned("-"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(520);
                  var5 = var1.getglobal("sys").__getattr__("stdin");
                  var1.setlocal(3, var5);
                  var5 = null;
                  var1.setline(521);
                  var10000 = var1.getglobal("GzipFile");
                  var8 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("wb"), var1.getglobal("sys").__getattr__("stdout")};
                  var6 = new String[]{"filename", "mode", "fileobj"};
                  var10000 = var10000.__call__(var2, var8, var6);
                  var5 = null;
                  var5 = var10000;
                  var1.setlocal(4, var5);
                  var5 = null;
               } else {
                  var1.setline(523);
                  var5 = var1.getglobal("__builtin__").__getattr__("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("rb"));
                  var1.setlocal(3, var5);
                  var5 = null;
                  var1.setline(524);
                  var5 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(2)._add(PyString.fromInterned(".gz")), (PyObject)PyString.fromInterned("wb"));
                  var1.setlocal(4, var5);
                  var5 = null;
               }
            }

            while(true) {
               var1.setline(525);
               if (!var1.getglobal("True").__nonzero__()) {
                  break;
               }

               var1.setline(526);
               var5 = var1.getlocal(3).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1024));
               var1.setlocal(5, var5);
               var5 = null;
               var1.setline(527);
               if (var1.getlocal(5).__not__().__nonzero__()) {
                  break;
               }

               var1.setline(529);
               var1.getlocal(4).__getattr__("write").__call__(var2, var1.getlocal(5));
            }

            var1.setline(530);
            var5 = var1.getlocal(4);
            var10000 = var5._isnot(var1.getglobal("sys").__getattr__("stdout"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(531);
               var1.getlocal(4).__getattr__("close").__call__(var2);
            }

            var1.setline(532);
            var5 = var1.getlocal(3);
            var10000 = var5._isnot(var1.getglobal("sys").__getattr__("stdin"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(533);
               var1.getlocal(3).__getattr__("close").__call__(var2);
            }
         }
      }
   }

   public gzip$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"output", "value"};
      write32u$1 = Py.newCode(2, var2, var1, "write32u", 19, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"input"};
      read32$2 = Py.newCode(1, var2, var1, "read32", 24, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"filename", "mode", "compresslevel"};
      open$3 = Py.newCode(3, var2, var1, "open", 27, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      GzipFile$4 = Py.newCode(0, var2, var1, "GzipFile", 36, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "filename", "mode", "compresslevel", "fileobj", "mtime"};
      __init__$5 = Py.newCode(6, var2, var1, "__init__", 45, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "warnings"};
      filename$6 = Py.newCode(1, var2, var1, "filename", 138, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s"};
      __repr__$7 = Py.newCode(1, var2, var1, "__repr__", 146, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _check_closed$8 = Py.newCode(1, var2, var1, "_check_closed", 150, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename"};
      _init_write$9 = Py.newCode(2, var2, var1, "_init_write", 157, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fname", "flags", "mtime"};
      _write_gzip_header$10 = Py.newCode(1, var2, var1, "_write_gzip_header", 164, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _init_read$11 = Py.newCode(1, var2, var1, "_init_read", 190, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "magic", "method", "flag", "xlen", "s"};
      _read_gzip_header$12 = Py.newCode(1, var2, var1, "_read_gzip_header", 194, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "errno"};
      write$13 = Py.newCode(2, var2, var1, "write", 227, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size", "errno", "readsize", "offset", "chunk"};
      read$14 = Py.newCode(2, var2, var1, "read", 248, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "buf"};
      _unread$15 = Py.newCode(2, var2, var1, "_unread", 281, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size", "pos", "buf", "uncompress"};
      _read$16 = Py.newCode(2, var2, var1, "_read", 285, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "offset"};
      _add_read_data$17 = Py.newCode(2, var2, var1, "_add_read_data", 335, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "crc32", "isize", "c"};
      _read_eof$18 = Py.newCode(1, var2, var1, "_read_eof", 343, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      closed$19 = Py.newCode(1, var2, var1, "closed", 367, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fileobj", "myfileobj"};
      close$20 = Py.newCode(1, var2, var1, "close", 371, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __enter__$21 = Py.newCode(1, var2, var1, "__enter__", 388, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "zlib_mode"};
      flush$22 = Py.newCode(2, var2, var1, "flush", 397, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      fileno$23 = Py.newCode(1, var2, var1, "fileno", 404, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      rewind$24 = Py.newCode(1, var2, var1, "rewind", 412, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      readable$25 = Py.newCode(1, var2, var1, "readable", 424, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      writable$26 = Py.newCode(1, var2, var1, "writable", 427, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      seekable$27 = Py.newCode(1, var2, var1, "seekable", 430, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "offset", "whence", "count", "i"};
      seek$28 = Py.newCode(3, var2, var1, "seek", 433, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size", "offset", "i", "readsize", "bufs", "c"};
      readline$29 = Py.newCode(2, var2, var1, "readline", 457, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "decompress", "arg", "f", "g", "chunk"};
      _test$30 = Py.newCode(0, var2, var1, "_test", 497, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new gzip$py("gzip$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(gzip$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.write32u$1(var2, var3);
         case 2:
            return this.read32$2(var2, var3);
         case 3:
            return this.open$3(var2, var3);
         case 4:
            return this.GzipFile$4(var2, var3);
         case 5:
            return this.__init__$5(var2, var3);
         case 6:
            return this.filename$6(var2, var3);
         case 7:
            return this.__repr__$7(var2, var3);
         case 8:
            return this._check_closed$8(var2, var3);
         case 9:
            return this._init_write$9(var2, var3);
         case 10:
            return this._write_gzip_header$10(var2, var3);
         case 11:
            return this._init_read$11(var2, var3);
         case 12:
            return this._read_gzip_header$12(var2, var3);
         case 13:
            return this.write$13(var2, var3);
         case 14:
            return this.read$14(var2, var3);
         case 15:
            return this._unread$15(var2, var3);
         case 16:
            return this._read$16(var2, var3);
         case 17:
            return this._add_read_data$17(var2, var3);
         case 18:
            return this._read_eof$18(var2, var3);
         case 19:
            return this.closed$19(var2, var3);
         case 20:
            return this.close$20(var2, var3);
         case 21:
            return this.__enter__$21(var2, var3);
         case 22:
            return this.flush$22(var2, var3);
         case 23:
            return this.fileno$23(var2, var3);
         case 24:
            return this.rewind$24(var2, var3);
         case 25:
            return this.readable$25(var2, var3);
         case 26:
            return this.writable$26(var2, var3);
         case 27:
            return this.seekable$27(var2, var3);
         case 28:
            return this.seek$28(var2, var3);
         case 29:
            return this.readline$29(var2, var3);
         case 30:
            return this._test$30(var2, var3);
         default:
            return null;
      }
   }
}
