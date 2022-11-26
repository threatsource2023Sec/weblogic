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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("chunk.py")
public class chunk$py extends PyFunctionTable implements PyRunnable {
   static chunk$py self;
   static final PyCode f$0;
   static final PyCode Chunk$1;
   static final PyCode __init__$2;
   static final PyCode getname$3;
   static final PyCode getsize$4;
   static final PyCode close$5;
   static final PyCode isatty$6;
   static final PyCode seek$7;
   static final PyCode tell$8;
   static final PyCode read$9;
   static final PyCode skip$10;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Simple class to read IFF chunks.\n\nAn IFF chunk (used in formats such as AIFF, TIFF, RMFF (RealMedia File\nFormat)) has the following structure:\n\n+----------------+\n| ID (4 bytes)   |\n+----------------+\n| size (4 bytes) |\n+----------------+\n| data           |\n| ...            |\n+----------------+\n\nThe ID is a 4-byte string which identifies the type of chunk.\n\nThe size field (a 32-bit value, encoded using big-endian byte order)\ngives the size of the whole chunk, including the 8-byte header.\n\nUsually an IFF-type file consists of one or more chunks.  The proposed\nusage of the Chunk class defined here is to instantiate an instance at\nthe start of each chunk and read from the instance until it reaches\nthe end, after which a new instance can be instantiated.  At the end\nof the file, creating a new instance will fail with a EOFError\nexception.\n\nUsage:\nwhile True:\n    try:\n        chunk = Chunk(file)\n    except EOFError:\n        break\n    chunktype = chunk.getname()\n    while True:\n        data = chunk.read(nbytes)\n        if not data:\n            pass\n        # do something with data\n\nThe interface is file-like.  The implemented methods are:\nread, close, seek, tell, isatty.\nExtra methods are: skip() (called by close, skips to the end of the chunk),\ngetname() (returns the name (ID) of the chunk)\n\nThe __init__ method has one required argument, a file-like object\n(including a chunk instance), and one optional argument, a flag which\nspecifies whether or not chunks are aligned on 2-byte boundaries.  The\ndefault is 1, i.e. aligned.\n"));
      var1.setline(49);
      PyString.fromInterned("Simple class to read IFF chunks.\n\nAn IFF chunk (used in formats such as AIFF, TIFF, RMFF (RealMedia File\nFormat)) has the following structure:\n\n+----------------+\n| ID (4 bytes)   |\n+----------------+\n| size (4 bytes) |\n+----------------+\n| data           |\n| ...            |\n+----------------+\n\nThe ID is a 4-byte string which identifies the type of chunk.\n\nThe size field (a 32-bit value, encoded using big-endian byte order)\ngives the size of the whole chunk, including the 8-byte header.\n\nUsually an IFF-type file consists of one or more chunks.  The proposed\nusage of the Chunk class defined here is to instantiate an instance at\nthe start of each chunk and read from the instance until it reaches\nthe end, after which a new instance can be instantiated.  At the end\nof the file, creating a new instance will fail with a EOFError\nexception.\n\nUsage:\nwhile True:\n    try:\n        chunk = Chunk(file)\n    except EOFError:\n        break\n    chunktype = chunk.getname()\n    while True:\n        data = chunk.read(nbytes)\n        if not data:\n            pass\n        # do something with data\n\nThe interface is file-like.  The implemented methods are:\nread, close, seek, tell, isatty.\nExtra methods are: skip() (called by close, skips to the end of the chunk),\ngetname() (returns the name (ID) of the chunk)\n\nThe __init__ method has one required argument, a file-like object\n(including a chunk instance), and one optional argument, a flag which\nspecifies whether or not chunks are aligned on 2-byte boundaries.  The\ndefault is 1, i.e. aligned.\n");
      var1.setline(51);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("Chunk", var3, Chunk$1);
      var1.setlocal("Chunk", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Chunk$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(52);
      PyObject[] var3 = new PyObject[]{var1.getname("True"), var1.getname("True"), var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(78);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getname$3, PyString.fromInterned("Return the name (ID) of the current chunk."));
      var1.setlocal("getname", var4);
      var3 = null;
      var1.setline(82);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getsize$4, PyString.fromInterned("Return the size of the current chunk."));
      var1.setlocal("getsize", var4);
      var3 = null;
      var1.setline(86);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$5, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(91);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isatty$6, (PyObject)null);
      var1.setlocal("isatty", var4);
      var3 = null;
      var1.setline(96);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, seek$7, PyString.fromInterned("Seek to specified position into the chunk.\n        Default position is 0 (start of chunk).\n        If the file is not seekable, this will result in an error.\n        "));
      var1.setlocal("seek", var4);
      var3 = null;
      var1.setline(115);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tell$8, (PyObject)null);
      var1.setlocal("tell", var4);
      var3 = null;
      var1.setline(120);
      var3 = new PyObject[]{Py.newInteger(-1)};
      var4 = new PyFunction(var1.f_globals, var3, read$9, PyString.fromInterned("Read at most size bytes from the chunk.\n        If size is omitted or negative, read until the end\n        of the chunk.\n        "));
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(143);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, skip$10, PyString.fromInterned("Skip the rest of the chunk.\n        If you are not interested in the contents of the chunk,\n        this method should be called so that the file points to\n        the start of the next chunk.\n        "));
      var1.setlocal("skip", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(53);
      PyObject var3 = imp.importOne("struct", var1, -1);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(54);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("closed", var3);
      var3 = null;
      var1.setline(55);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("align", var3);
      var3 = null;
      var1.setline(56);
      PyString var7;
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(57);
         var7 = PyString.fromInterned(">");
         var1.setlocal(6, var7);
         var3 = null;
      } else {
         var1.setline(59);
         var7 = PyString.fromInterned("<");
         var1.setlocal(6, var7);
         var3 = null;
      }

      var1.setline(60);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("file", var3);
      var3 = null;
      var1.setline(61);
      var3 = var1.getlocal(1).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(4));
      var1.getlocal(0).__setattr__("chunkname", var3);
      var3 = null;
      var1.setline(62);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("chunkname"));
      PyObject var10000 = var3._lt(Py.newInteger(4));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(63);
         throw Py.makeException(var1.getglobal("EOFError"));
      } else {
         PyException var8;
         try {
            var1.setline(65);
            var3 = var1.getlocal(5).__getattr__("unpack").__call__(var2, var1.getlocal(6)._add(PyString.fromInterned("L")), var1.getlocal(1).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(4))).__getitem__(Py.newInteger(0));
            var1.getlocal(0).__setattr__("chunksize", var3);
            var3 = null;
         } catch (Throwable var5) {
            var8 = Py.setException(var5, var1);
            if (var8.match(var1.getlocal(5).__getattr__("error"))) {
               var1.setline(67);
               throw Py.makeException(var1.getglobal("EOFError"));
            }

            throw var8;
         }

         var1.setline(68);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(69);
            var3 = var1.getlocal(0).__getattr__("chunksize")._sub(Py.newInteger(8));
            var1.getlocal(0).__setattr__("chunksize", var3);
            var3 = null;
         }

         var1.setline(70);
         PyInteger var9 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"size_read", var9);
         var3 = null;

         label38: {
            PyObject var4;
            try {
               var1.setline(72);
               var3 = var1.getlocal(0).__getattr__("file").__getattr__("tell").__call__(var2);
               var1.getlocal(0).__setattr__("offset", var3);
               var3 = null;
            } catch (Throwable var6) {
               var8 = Py.setException(var6, var1);
               if (var8.match(new PyTuple(new PyObject[]{var1.getglobal("AttributeError"), var1.getglobal("IOError")}))) {
                  var1.setline(74);
                  var4 = var1.getglobal("False");
                  var1.getlocal(0).__setattr__("seekable", var4);
                  var4 = null;
                  break label38;
               }

               throw var8;
            }

            var1.setline(76);
            var4 = var1.getglobal("True");
            var1.getlocal(0).__setattr__("seekable", var4);
            var4 = null;
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject getname$3(PyFrame var1, ThreadState var2) {
      var1.setline(79);
      PyString.fromInterned("Return the name (ID) of the current chunk.");
      var1.setline(80);
      PyObject var3 = var1.getlocal(0).__getattr__("chunkname");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getsize$4(PyFrame var1, ThreadState var2) {
      var1.setline(83);
      PyString.fromInterned("Return the size of the current chunk.");
      var1.setline(84);
      PyObject var3 = var1.getlocal(0).__getattr__("chunksize");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject close$5(PyFrame var1, ThreadState var2) {
      var1.setline(87);
      if (var1.getlocal(0).__getattr__("closed").__not__().__nonzero__()) {
         var1.setline(88);
         var1.getlocal(0).__getattr__("skip").__call__(var2);
         var1.setline(89);
         PyObject var3 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("closed", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject isatty$6(PyFrame var1, ThreadState var2) {
      var1.setline(92);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(93);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("I/O operation on closed file"));
      } else {
         var1.setline(94);
         PyObject var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject seek$7(PyFrame var1, ThreadState var2) {
      var1.setline(100);
      PyString.fromInterned("Seek to specified position into the chunk.\n        Default position is 0 (start of chunk).\n        If the file is not seekable, this will result in an error.\n        ");
      var1.setline(102);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(103);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("I/O operation on closed file"));
      } else {
         var1.setline(104);
         if (var1.getlocal(0).__getattr__("seekable").__not__().__nonzero__()) {
            var1.setline(105);
            throw Py.makeException(var1.getglobal("IOError"), PyString.fromInterned("cannot seek"));
         } else {
            var1.setline(106);
            PyObject var3 = var1.getlocal(2);
            PyObject var10000 = var3._eq(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(107);
               var3 = var1.getlocal(1)._add(var1.getlocal(0).__getattr__("size_read"));
               var1.setlocal(1, var3);
               var3 = null;
            } else {
               var1.setline(108);
               var3 = var1.getlocal(2);
               var10000 = var3._eq(Py.newInteger(2));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(109);
                  var3 = var1.getlocal(1)._add(var1.getlocal(0).__getattr__("chunksize"));
                  var1.setlocal(1, var3);
                  var3 = null;
               }
            }

            var1.setline(110);
            var3 = var1.getlocal(1);
            var10000 = var3._lt(Py.newInteger(0));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var3 = var1.getlocal(1);
               var10000 = var3._gt(var1.getlocal(0).__getattr__("chunksize"));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(111);
               throw Py.makeException(var1.getglobal("RuntimeError"));
            } else {
               var1.setline(112);
               var1.getlocal(0).__getattr__("file").__getattr__("seek").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("offset")._add(var1.getlocal(1)), (PyObject)Py.newInteger(0));
               var1.setline(113);
               var3 = var1.getlocal(1);
               var1.getlocal(0).__setattr__("size_read", var3);
               var3 = null;
               var1.f_lasti = -1;
               return Py.None;
            }
         }
      }
   }

   public PyObject tell$8(PyFrame var1, ThreadState var2) {
      var1.setline(116);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(117);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("I/O operation on closed file"));
      } else {
         var1.setline(118);
         PyObject var3 = var1.getlocal(0).__getattr__("size_read");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject read$9(PyFrame var1, ThreadState var2) {
      var1.setline(124);
      PyString.fromInterned("Read at most size bytes from the chunk.\n        If size is omitted or negative, read until the end\n        of the chunk.\n        ");
      var1.setline(126);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(127);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("I/O operation on closed file"));
      } else {
         var1.setline(128);
         PyObject var3 = var1.getlocal(0).__getattr__("size_read");
         PyObject var10000 = var3._ge(var1.getlocal(0).__getattr__("chunksize"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(129);
            PyString var5 = PyString.fromInterned("");
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(130);
            PyObject var4 = var1.getlocal(1);
            var10000 = var4._lt(Py.newInteger(0));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(131);
               var4 = var1.getlocal(0).__getattr__("chunksize")._sub(var1.getlocal(0).__getattr__("size_read"));
               var1.setlocal(1, var4);
               var4 = null;
            }

            var1.setline(132);
            var4 = var1.getlocal(1);
            var10000 = var4._gt(var1.getlocal(0).__getattr__("chunksize")._sub(var1.getlocal(0).__getattr__("size_read")));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(133);
               var4 = var1.getlocal(0).__getattr__("chunksize")._sub(var1.getlocal(0).__getattr__("size_read"));
               var1.setlocal(1, var4);
               var4 = null;
            }

            var1.setline(134);
            var4 = var1.getlocal(0).__getattr__("file").__getattr__("read").__call__(var2, var1.getlocal(1));
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(135);
            var4 = var1.getlocal(0).__getattr__("size_read")._add(var1.getglobal("len").__call__(var2, var1.getlocal(2)));
            var1.getlocal(0).__setattr__("size_read", var4);
            var4 = null;
            var1.setline(136);
            var4 = var1.getlocal(0).__getattr__("size_read");
            var10000 = var4._eq(var1.getlocal(0).__getattr__("chunksize"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("align");
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(0).__getattr__("chunksize")._and(Py.newInteger(1));
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(139);
               var4 = var1.getlocal(0).__getattr__("file").__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(140);
               var4 = var1.getlocal(0).__getattr__("size_read")._add(var1.getglobal("len").__call__(var2, var1.getlocal(3)));
               var1.getlocal(0).__setattr__("size_read", var4);
               var4 = null;
            }

            var1.setline(141);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject skip$10(PyFrame var1, ThreadState var2) {
      var1.setline(148);
      PyString.fromInterned("Skip the rest of the chunk.\n        If you are not interested in the contents of the chunk,\n        this method should be called so that the file points to\n        the start of the next chunk.\n        ");
      var1.setline(150);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(151);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("I/O operation on closed file"));
      } else {
         var1.setline(152);
         PyObject var10000;
         PyException var3;
         PyObject var5;
         if (var1.getlocal(0).__getattr__("seekable").__nonzero__()) {
            try {
               var1.setline(154);
               var5 = var1.getlocal(0).__getattr__("chunksize")._sub(var1.getlocal(0).__getattr__("size_read"));
               var1.setlocal(1, var5);
               var3 = null;
               var1.setline(156);
               var10000 = var1.getlocal(0).__getattr__("align");
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(0).__getattr__("chunksize")._and(Py.newInteger(1));
               }

               if (var10000.__nonzero__()) {
                  var1.setline(157);
                  var5 = var1.getlocal(1)._add(Py.newInteger(1));
                  var1.setlocal(1, var5);
                  var3 = null;
               }

               var1.setline(158);
               var1.getlocal(0).__getattr__("file").__getattr__("seek").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(1));
               var1.setline(159);
               var5 = var1.getlocal(0).__getattr__("size_read")._add(var1.getlocal(1));
               var1.getlocal(0).__setattr__("size_read", var5);
               var3 = null;
               var1.setline(160);
               var1.f_lasti = -1;
               return Py.None;
            } catch (Throwable var4) {
               var3 = Py.setException(var4, var1);
               if (!var3.match(var1.getglobal("IOError"))) {
                  throw var3;
               }

               var1.setline(162);
            }
         }

         do {
            var1.setline(163);
            var5 = var1.getlocal(0).__getattr__("size_read");
            var10000 = var5._lt(var1.getlocal(0).__getattr__("chunksize"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setline(164);
            var5 = var1.getglobal("min").__call__((ThreadState)var2, (PyObject)Py.newInteger(8192), (PyObject)var1.getlocal(0).__getattr__("chunksize")._sub(var1.getlocal(0).__getattr__("size_read")));
            var1.setlocal(1, var5);
            var3 = null;
            var1.setline(165);
            var5 = var1.getlocal(0).__getattr__("read").__call__(var2, var1.getlocal(1));
            var1.setlocal(2, var5);
            var3 = null;
            var1.setline(166);
         } while(!var1.getlocal(2).__not__().__nonzero__());

         var1.setline(167);
         throw Py.makeException(var1.getglobal("EOFError"));
      }
   }

   public chunk$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Chunk$1 = Py.newCode(0, var2, var1, "Chunk", 51, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "file", "align", "bigendian", "inclheader", "struct", "strflag"};
      __init__$2 = Py.newCode(5, var2, var1, "__init__", 52, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getname$3 = Py.newCode(1, var2, var1, "getname", 78, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getsize$4 = Py.newCode(1, var2, var1, "getsize", 82, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$5 = Py.newCode(1, var2, var1, "close", 86, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isatty$6 = Py.newCode(1, var2, var1, "isatty", 91, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos", "whence"};
      seek$7 = Py.newCode(3, var2, var1, "seek", 96, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tell$8 = Py.newCode(1, var2, var1, "tell", 115, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size", "data", "dummy"};
      read$9 = Py.newCode(2, var2, var1, "read", 120, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "dummy"};
      skip$10 = Py.newCode(1, var2, var1, "skip", 143, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new chunk$py("chunk$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(chunk$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Chunk$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.getname$3(var2, var3);
         case 4:
            return this.getsize$4(var2, var3);
         case 5:
            return this.close$5(var2, var3);
         case 6:
            return this.isatty$6(var2, var3);
         case 7:
            return this.seek$7(var2, var3);
         case 8:
            return this.tell$8(var2, var3);
         case 9:
            return this.read$9(var2, var3);
         case 10:
            return this.skip$10(var2, var3);
         default:
            return null;
      }
   }
}
