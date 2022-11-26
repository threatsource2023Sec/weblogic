import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyFrame;
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
@Filename("io.py")
public class io$py extends PyFunctionTable implements PyRunnable {
   static io$py self;
   static final PyCode f$0;
   static final PyCode IOBase$1;
   static final PyCode RawIOBase$2;
   static final PyCode BufferedIOBase$3;
   static final PyCode TextIOBase$4;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("The io module provides the Python interfaces to stream handling. The\nbuiltin open function is defined in this module.\n\nAt the top of the I/O hierarchy is the abstract base class IOBase. It\ndefines the basic interface to a stream. Note, however, that there is no\nseparation between reading and writing to streams; implementations are\nallowed to throw an IOError if they do not support a given operation.\n\nExtending IOBase is RawIOBase which deals simply with the reading and\nwriting of raw bytes to a stream. FileIO subclasses RawIOBase to provide\nan interface to OS files.\n\nBufferedIOBase deals with buffering on a raw byte stream (RawIOBase). Its\nsubclasses, BufferedWriter, BufferedReader, and BufferedRWPair buffer\nstreams that are readable, writable, and both respectively.\nBufferedRandom provides a buffered interface to random access\nstreams. BytesIO is a simple stream of in-memory bytes.\n\nAnother IOBase subclass, TextIOBase, deals with the encoding and decoding\nof streams into text. TextIOWrapper, which extends it, is a buffered text\ninterface to a buffered raw stream (`BufferedIOBase`). Finally, StringIO\nis a in-memory stream for text.\n\nArgument names are not part of the specification, and only the arguments\nof open() are intended to be used as keyword arguments.\n\ndata:\n\nDEFAULT_BUFFER_SIZE\n\n   An int containing the default buffer size used by the module's buffered\n   I/O classes. open() uses the file's blksize (as obtained by os.stat) if\n   possible.\n"));
      var1.setline(40);
      PyString.fromInterned("The io module provides the Python interfaces to stream handling. The\nbuiltin open function is defined in this module.\n\nAt the top of the I/O hierarchy is the abstract base class IOBase. It\ndefines the basic interface to a stream. Note, however, that there is no\nseparation between reading and writing to streams; implementations are\nallowed to throw an IOError if they do not support a given operation.\n\nExtending IOBase is RawIOBase which deals simply with the reading and\nwriting of raw bytes to a stream. FileIO subclasses RawIOBase to provide\nan interface to OS files.\n\nBufferedIOBase deals with buffering on a raw byte stream (RawIOBase). Its\nsubclasses, BufferedWriter, BufferedReader, and BufferedRWPair buffer\nstreams that are readable, writable, and both respectively.\nBufferedRandom provides a buffered interface to random access\nstreams. BytesIO is a simple stream of in-memory bytes.\n\nAnother IOBase subclass, TextIOBase, deals with the encoding and decoding\nof streams into text. TextIOWrapper, which extends it, is a buffered text\ninterface to a buffered raw stream (`BufferedIOBase`). Finally, StringIO\nis a in-memory stream for text.\n\nArgument names are not part of the specification, and only the arguments\nof open() are intended to be used as keyword arguments.\n\ndata:\n\nDEFAULT_BUFFER_SIZE\n\n   An int containing the default buffer size used by the module's buffered\n   I/O classes. open() uses the file's blksize (as obtained by os.stat) if\n   possible.\n");
      var1.setline(52);
      PyString var3 = PyString.fromInterned("Guido van Rossum <guido@python.org>, Mike Verdone <mike.verdone@gmail.com>, Mark Russell <mark.russell@zen.co.uk>, Antoine Pitrou <solipsis@pitrou.net>, Amaury Forgeot d'Arc <amauryfa@gmail.com>, Benjamin Peterson <benjamin@python.org>");
      var1.setlocal("__author__", var3);
      var3 = null;
      var1.setline(59);
      PyList var5 = new PyList(new PyObject[]{PyString.fromInterned("BlockingIOError"), PyString.fromInterned("open"), PyString.fromInterned("IOBase"), PyString.fromInterned("RawIOBase"), PyString.fromInterned("FileIO"), PyString.fromInterned("BytesIO"), PyString.fromInterned("StringIO"), PyString.fromInterned("BufferedIOBase"), PyString.fromInterned("BufferedReader"), PyString.fromInterned("BufferedWriter"), PyString.fromInterned("BufferedRWPair"), PyString.fromInterned("BufferedRandom"), PyString.fromInterned("TextIOBase"), PyString.fromInterned("TextIOWrapper"), PyString.fromInterned("UnsupportedOperation"), PyString.fromInterned("SEEK_SET"), PyString.fromInterned("SEEK_CUR"), PyString.fromInterned("SEEK_END")});
      var1.setlocal("__all__", var5);
      var3 = null;
      var1.setline(66);
      PyObject var6 = imp.importOne("abc", var1, -1);
      var1.setlocal("abc", var6);
      var3 = null;
      var1.setline(69);
      var6 = imp.importOne("_io", var1, -1);
      var1.setlocal("_io", var6);
      var3 = null;
      var1.setline(70);
      String[] var7 = new String[]{"DEFAULT_BUFFER_SIZE", "BlockingIOError", "UnsupportedOperation", "open", "FileIO", "BytesIO", "StringIO", "BufferedReader", "BufferedWriter", "BufferedRWPair", "BufferedRandom", "IncrementalNewlineDecoder", "TextIOWrapper"};
      PyObject[] var8 = imp.importFrom("_io", var7, var1, -1);
      PyObject var4 = var8[0];
      var1.setlocal("DEFAULT_BUFFER_SIZE", var4);
      var4 = null;
      var4 = var8[1];
      var1.setlocal("BlockingIOError", var4);
      var4 = null;
      var4 = var8[2];
      var1.setlocal("UnsupportedOperation", var4);
      var4 = null;
      var4 = var8[3];
      var1.setlocal("open", var4);
      var4 = null;
      var4 = var8[4];
      var1.setlocal("FileIO", var4);
      var4 = null;
      var4 = var8[5];
      var1.setlocal("BytesIO", var4);
      var4 = null;
      var4 = var8[6];
      var1.setlocal("StringIO", var4);
      var4 = null;
      var4 = var8[7];
      var1.setlocal("BufferedReader", var4);
      var4 = null;
      var4 = var8[8];
      var1.setlocal("BufferedWriter", var4);
      var4 = null;
      var4 = var8[9];
      var1.setlocal("BufferedRWPair", var4);
      var4 = null;
      var4 = var8[10];
      var1.setlocal("BufferedRandom", var4);
      var4 = null;
      var4 = var8[11];
      var1.setlocal("IncrementalNewlineDecoder", var4);
      var4 = null;
      var4 = var8[12];
      var1.setlocal("TextIOWrapper", var4);
      var4 = null;
      var1.setline(77);
      var6 = var1.getname("_io").__getattr__("open");
      var1.setlocal("OpenWrapper", var6);
      var3 = null;
      var1.setline(80);
      PyInteger var9 = Py.newInteger(0);
      var1.setlocal("SEEK_SET", var9);
      var3 = null;
      var1.setline(81);
      var9 = Py.newInteger(1);
      var1.setlocal("SEEK_CUR", var9);
      var3 = null;
      var1.setline(82);
      var9 = Py.newInteger(2);
      var1.setlocal("SEEK_END", var9);
      var3 = null;
      var1.setline(87);
      var8 = new PyObject[]{var1.getname("_io").__getattr__("_IOBase")};
      var4 = Py.makeClass("IOBase", var8, IOBase$1);
      var1.setlocal("IOBase", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(90);
      var8 = new PyObject[]{var1.getname("_io").__getattr__("_RawIOBase"), var1.getname("IOBase")};
      var4 = Py.makeClass("RawIOBase", var8, RawIOBase$2);
      var1.setlocal("RawIOBase", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(93);
      var8 = new PyObject[]{var1.getname("_io").__getattr__("_BufferedIOBase"), var1.getname("IOBase")};
      var4 = Py.makeClass("BufferedIOBase", var8, BufferedIOBase$3);
      var1.setlocal("BufferedIOBase", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(96);
      var8 = new PyObject[]{var1.getname("_io").__getattr__("_TextIOBase"), var1.getname("IOBase")};
      var4 = Py.makeClass("TextIOBase", var8, TextIOBase$4);
      var1.setlocal("TextIOBase", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(99);
      var1.getname("RawIOBase").__getattr__("register").__call__(var2, var1.getname("FileIO"));
      var1.setline(101);
      var6 = (new PyTuple(new PyObject[]{var1.getname("BytesIO"), var1.getname("BufferedReader"), var1.getname("BufferedWriter"), var1.getname("BufferedRandom"), var1.getname("BufferedRWPair")})).__iter__();

      while(true) {
         var1.setline(101);
         var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(105);
            var6 = (new PyTuple(new PyObject[]{var1.getname("StringIO"), var1.getname("TextIOWrapper")})).__iter__();

            while(true) {
               var1.setline(105);
               var4 = var6.__iternext__();
               if (var4 == null) {
                  var1.setline(107);
                  var1.dellocal("klass");
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal("klass", var4);
               var1.setline(106);
               var1.getname("TextIOBase").__getattr__("register").__call__(var2, var1.getname("klass"));
            }
         }

         var1.setlocal("klass", var4);
         var1.setline(103);
         var1.getname("BufferedIOBase").__getattr__("register").__call__(var2, var1.getname("klass"));
      }
   }

   public PyObject IOBase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(88);
      PyObject var3 = var1.getname("abc").__getattr__("ABCMeta");
      var1.setlocal("__metaclass__", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject RawIOBase$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(91);
      return var1.getf_locals();
   }

   public PyObject BufferedIOBase$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(94);
      return var1.getf_locals();
   }

   public PyObject TextIOBase$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(97);
      return var1.getf_locals();
   }

   public io$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      IOBase$1 = Py.newCode(0, var2, var1, "IOBase", 87, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      RawIOBase$2 = Py.newCode(0, var2, var1, "RawIOBase", 90, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      BufferedIOBase$3 = Py.newCode(0, var2, var1, "BufferedIOBase", 93, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TextIOBase$4 = Py.newCode(0, var2, var1, "TextIOBase", 96, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new io$py("io$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(io$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.IOBase$1(var2, var3);
         case 2:
            return this.RawIOBase$2(var2, var3);
         case 3:
            return this.BufferedIOBase$3(var2, var3);
         case 4:
            return this.TextIOBase$4(var2, var3);
         default:
            return null;
      }
   }
}
