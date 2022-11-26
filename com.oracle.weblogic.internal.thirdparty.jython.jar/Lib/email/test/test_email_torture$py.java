package email.test;

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
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("email/test/test_email_torture.py")
public class test_email_torture$py extends PyFunctionTable implements PyRunnable {
   static test_email_torture$py self;
   static final PyCode f$0;
   static final PyCode openfile$1;
   static final PyCode TortureBase$2;
   static final PyCode _msgobj$3;
   static final PyCode TestCrispinTorture$4;
   static final PyCode test_mondo_message$5;
   static final PyCode _testclasses$6;
   static final PyCode suite$7;
   static final PyCode test_main$8;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(9);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(10);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(11);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(12);
      String[] var6 = new String[]{"StringIO"};
      PyObject[] var7 = imp.importFrom("cStringIO", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("StringIO", var4);
      var4 = null;
      var1.setline(13);
      var6 = new String[]{"ListType"};
      var7 = imp.importFrom("types", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("ListType", var4);
      var4 = null;
      var1.setline(15);
      var6 = new String[]{"TestEmailBase"};
      var7 = imp.importFrom("email.test.test_email", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("TestEmailBase", var4);
      var4 = null;
      var1.setline(16);
      var6 = new String[]{"TestSkipped", "run_unittest"};
      var7 = imp.importFrom("test.test_support", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("TestSkipped", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var1.setline(18);
      var3 = imp.importOne("email", var1, -1);
      var1.setlocal("email", var3);
      var3 = null;
      var1.setline(19);
      var6 = new String[]{"__file__"};
      var7 = imp.importFrom("email", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("testfile", var4);
      var4 = null;
      var1.setline(20);
      var6 = new String[]{"_structure"};
      var7 = imp.importFrom("email.iterators", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("_structure", var4);
      var4 = null;
      var1.setline(22);
      var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, openfile$1, (PyObject)null);
      var1.setlocal("openfile", var8);
      var3 = null;

      try {
         var1.setline(29);
         var1.getname("openfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("crispin-torture.txt"));
      } catch (Throwable var5) {
         PyException var10 = Py.setException(var5, var1);
         if (var10.match(var1.getname("IOError"))) {
            var1.setline(31);
            throw Py.makeException(var1.getname("TestSkipped"));
         }

         throw var10;
      }

      var1.setline(35);
      var7 = new PyObject[]{var1.getname("TestEmailBase")};
      var4 = Py.makeClass("TortureBase", var7, TortureBase$2);
      var1.setlocal("TortureBase", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(46);
      var7 = new PyObject[]{var1.getname("TortureBase")};
      var4 = Py.makeClass("TestCrispinTorture", var7, TestCrispinTorture$4);
      var1.setlocal("TestCrispinTorture", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(117);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _testclasses$6, (PyObject)null);
      var1.setlocal("_testclasses", var8);
      var3 = null;
      var1.setline(122);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, suite$7, (PyObject)null);
      var1.setlocal("suite", var8);
      var3 = null;
      var1.setline(129);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, test_main$8, (PyObject)null);
      var1.setlocal("test_main", var8);
      var3 = null;
      var1.setline(135);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(136);
         var10000 = var1.getname("unittest").__getattr__("main");
         var7 = new PyObject[]{PyString.fromInterned("suite")};
         String[] var9 = new String[]{"defaultTest"};
         var10000.__call__(var2, var7, var9);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject openfile$1(PyFrame var1, ThreadState var2) {
      var1.setline(23);
      String[] var3 = new String[]{"join", "dirname", "abspath"};
      PyObject[] var5 = imp.importFrom("os.path", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(1, var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal(2, var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal(3, var4);
      var4 = null;
      var1.setline(24);
      PyObject var6 = var1.getlocal(3).__call__(var2, var1.getlocal(1).__call__(var2, var1.getlocal(2).__call__(var2, var1.getglobal("testfile")), var1.getglobal("os").__getattr__("pardir"), PyString.fromInterned("moredata"), var1.getlocal(0)));
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(25);
      var6 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("r"));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject TortureBase$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(36);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, _msgobj$3, (PyObject)null);
      var1.setlocal("_msgobj", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _msgobj$3(PyFrame var1, ThreadState var2) {
      var1.setline(37);
      PyObject var3 = var1.getglobal("openfile").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(39);
         PyObject var4 = var1.getglobal("email").__getattr__("message_from_file").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(41);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(41);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(42);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject TestCrispinTorture$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(48);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_mondo_message$5, (PyObject)null);
      var1.setlocal("test_mondo_message", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_mondo_message$5(PyFrame var1, ThreadState var2) {
      var1.setline(49);
      PyObject var3 = var1.getlocal(0).__getattr__("assertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(50);
      var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(51);
      var3 = var1.getlocal(0).__getattr__("_msgobj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("crispin-torture.txt"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(52);
      var3 = var1.getlocal(3).__getattr__("get_payload").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(53);
      var1.getlocal(1).__call__(var2, var1.getglobal("type").__call__(var2, var1.getlocal(4)), var1.getglobal("ListType"));
      var1.setline(54);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(4)), (PyObject)Py.newInteger(12));
      var1.setline(55);
      var1.getlocal(1).__call__(var2, var1.getlocal(3).__getattr__("preamble"), var1.getglobal("None"));
      var1.setline(56);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("epilogue"), (PyObject)PyString.fromInterned("\n"));
      var1.setline(59);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(60);
      PyObject var10000 = var1.getglobal("_structure");
      PyObject[] var5 = new PyObject[]{var1.getlocal(3), var1.getlocal(5)};
      String[] var4 = new String[]{"fp"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(61);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned("multipart/mixed\n    text/plain\n    message/rfc822\n        multipart/alternative\n            text/plain\n            multipart/mixed\n                text/richtext\n            application/andrew-inset\n    message/rfc822\n        audio/basic\n    audio/basic\n    image/pbm\n    message/rfc822\n        multipart/mixed\n            multipart/mixed\n                text/plain\n                audio/x-sun\n            multipart/mixed\n                image/gif\n                image/gif\n                application/x-be2\n                application/atomicmail\n            audio/x-sun\n    message/rfc822\n        multipart/mixed\n            text/plain\n            image/pgm\n            text/plain\n    message/rfc822\n        multipart/mixed\n            text/plain\n            image/pbm\n    message/rfc822\n        application/postscript\n    image/gif\n    message/rfc822\n        multipart/mixed\n            audio/basic\n            audio/basic\n    message/rfc822\n        multipart/mixed\n            application/postscript\n            text/plain\n            message/rfc822\n                multipart/mixed\n                    text/plain\n                    multipart/parallel\n                        image/gif\n                        audio/basic\n                    application/atomicmail\n                    message/rfc822\n                        audio/x-sun\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _testclasses$6(PyFrame var1, ThreadState var2) {
      var1.setline(118);
      PyObject var3 = var1.getglobal("sys").__getattr__("modules").__getitem__(var1.getglobal("__name__"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(119);
      PyList var10000 = new PyList();
      var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(119);
      var3 = var1.getglobal("dir").__call__(var2, var1.getlocal(0)).__iter__();

      while(true) {
         var1.setline(119);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(119);
            var1.dellocal(1);
            PyList var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(119);
         if (var1.getlocal(2).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Test")).__nonzero__()) {
            var1.setline(119);
            var1.getlocal(1).__call__(var2, var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(2)));
         }
      }
   }

   public PyObject suite$7(PyFrame var1, ThreadState var2) {
      var1.setline(123);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(124);
      var3 = var1.getglobal("_testclasses").__call__(var2).__iter__();

      while(true) {
         var1.setline(124);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(126);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(1, var4);
         var1.setline(125);
         var1.getlocal(0).__getattr__("addTest").__call__(var2, var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getlocal(1)));
      }
   }

   public PyObject test_main$8(PyFrame var1, ThreadState var2) {
      var1.setline(130);
      PyObject var3 = var1.getglobal("_testclasses").__call__(var2).__iter__();

      while(true) {
         var1.setline(130);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(0, var4);
         var1.setline(131);
         var1.getglobal("run_unittest").__call__(var2, var1.getlocal(0));
      }
   }

   public test_email_torture$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"filename", "join", "dirname", "abspath", "path"};
      openfile$1 = Py.newCode(1, var2, var1, "openfile", 22, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TortureBase$2 = Py.newCode(0, var2, var1, "TortureBase", 35, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "filename", "fp", "msg"};
      _msgobj$3 = Py.newCode(2, var2, var1, "_msgobj", 36, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestCrispinTorture$4 = Py.newCode(0, var2, var1, "TestCrispinTorture", 46, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "eq", "neq", "msg", "payload", "fp"};
      test_mondo_message$5 = Py.newCode(1, var2, var1, "test_mondo_message", 48, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mod", "_[119_12]", "name"};
      _testclasses$6 = Py.newCode(0, var2, var1, "_testclasses", 117, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"suite", "testclass"};
      suite$7 = Py.newCode(0, var2, var1, "suite", 122, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"testclass"};
      test_main$8 = Py.newCode(0, var2, var1, "test_main", 129, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_email_torture$py("email/test/test_email_torture$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_email_torture$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.openfile$1(var2, var3);
         case 2:
            return this.TortureBase$2(var2, var3);
         case 3:
            return this._msgobj$3(var2, var3);
         case 4:
            return this.TestCrispinTorture$4(var2, var3);
         case 5:
            return this.test_mondo_message$5(var2, var3);
         case 6:
            return this._testclasses$6(var2, var3);
         case 7:
            return this.suite$7(var2, var3);
         case 8:
            return this.test_main$8(var2, var3);
         default:
            return null;
      }
   }
}
