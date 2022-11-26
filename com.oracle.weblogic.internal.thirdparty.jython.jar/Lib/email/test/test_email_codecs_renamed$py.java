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
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("email/test/test_email_codecs_renamed.py")
public class test_email_codecs_renamed$py extends PyFunctionTable implements PyRunnable {
   static test_email_codecs_renamed$py self;
   static final PyCode f$0;
   static final PyCode TestEmailAsianCodecs$1;
   static final PyCode test_japanese_codecs$2;
   static final PyCode test_payload_encoding$3;
   static final PyCode suite$4;
   static final PyCode test_main$5;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(5);
      PyObject var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(6);
      String[] var6 = new String[]{"run_unittest"};
      PyObject[] var7 = imp.importFrom("test.test_support", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var1.setline(8);
      var6 = new String[]{"TestEmailBase"};
      var7 = imp.importFrom("email.test.test_email", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("TestEmailBase", var4);
      var4 = null;
      var1.setline(9);
      var6 = new String[]{"Charset"};
      var7 = imp.importFrom("email.charset", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("Charset", var4);
      var4 = null;
      var1.setline(10);
      var6 = new String[]{"Header", "decode_header"};
      var7 = imp.importFrom("email.header", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("Header", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("decode_header", var4);
      var4 = null;
      var1.setline(11);
      var6 = new String[]{"Message"};
      var7 = imp.importFrom("email.message", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("Message", var4);
      var4 = null;

      try {
         var1.setline(16);
         var1.getname("unicode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo"), (PyObject)PyString.fromInterned("euc-jp"));
      } catch (Throwable var5) {
         PyException var8 = Py.setException(var5, var1);
         if (var8.match(var1.getname("LookupError"))) {
            var1.setline(18);
            throw Py.makeException(var1.getname("unittest").__getattr__("SkipTest"));
         }

         throw var8;
      }

      var1.setline(22);
      var7 = new PyObject[]{var1.getname("TestEmailBase")};
      var4 = Py.makeClass("TestEmailAsianCodecs", var7, TestEmailAsianCodecs$1);
      var1.setlocal("TestEmailAsianCodecs", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(65);
      var7 = Py.EmptyObjects;
      PyFunction var9 = new PyFunction(var1.f_globals, var7, suite$4, (PyObject)null);
      var1.setlocal("suite", var9);
      var3 = null;
      var1.setline(71);
      var7 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var7, test_main$5, (PyObject)null);
      var1.setlocal("test_main", var9);
      var3 = null;
      var1.setline(76);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(77);
         var10000 = var1.getname("unittest").__getattr__("main");
         var7 = new PyObject[]{PyString.fromInterned("suite")};
         String[] var10 = new String[]{"defaultTest"};
         var10000.__call__(var2, var7, var10);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestEmailAsianCodecs$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(23);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_japanese_codecs$2, (PyObject)null);
      var1.setlocal("test_japanese_codecs", var4);
      var3 = null;
      var1.setline(55);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_payload_encoding$3, (PyObject)null);
      var1.setlocal("test_payload_encoding", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_japanese_codecs$2(PyFrame var1, ThreadState var2) {
      var1.setline(24);
      PyObject var3 = var1.getlocal(0).__getattr__("ndiffAssertEqual");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(25);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("euc-jp"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(26);
      var3 = var1.getglobal("Charset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("iso-8859-1"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(27);
      var3 = var1.getglobal("Header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Hello World!"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(28);
      PyString var5 = PyString.fromInterned("¥Ï¥í¡¼¥ï¡¼¥ë¥É¡ª");
      var1.setlocal(5, var5);
      var3 = null;
      var1.setline(29);
      var5 = PyString.fromInterned("Grüß Gott!");
      var1.setlocal(6, var5);
      var3 = null;
      var1.setline(30);
      var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(5), var1.getlocal(2));
      var1.setline(31);
      var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(6), var1.getlocal(3));
      var1.setline(37);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("encode").__call__(var2), (PyObject)PyString.fromInterned("Hello World! =?iso-2022-jp?b?GyRCJU8lbSE8JW8hPCVrJUkhKhsoQg==?=\n =?iso-8859-1?q?Gr=FC=DF?= =?iso-8859-1?q?_Gott!?="));
      var1.setline(40);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("decode_header").__call__(var2, var1.getlocal(4).__getattr__("encode").__call__(var2)), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("Hello World!"), var1.getglobal("None")}), new PyTuple(new PyObject[]{PyString.fromInterned("\u001b$B%O%m!<%o!<%k%I!*\u001b(B"), PyString.fromInterned("iso-2022-jp")}), new PyTuple(new PyObject[]{PyString.fromInterned("Grüß Gott!"), PyString.fromInterned("iso-8859-1")})})));
      var1.setline(44);
      var5 = PyString.fromInterned("test-ja ¤ØÅê¹Æ¤µ¤ì¤¿¥á¡¼¥ë¤Ï»Ê²ñ¼Ô¤Î¾µÇ§¤òÂÔ¤Ã¤Æ¤¤¤Þ¤¹");
      var1.setlocal(7, var5);
      var3 = null;
      var1.setline(45);
      PyObject var10000 = var1.getglobal("Header");
      PyObject[] var6 = new PyObject[]{var1.getlocal(7), var1.getlocal(2), PyString.fromInterned("Subject")};
      String[] var4 = new String[]{"header_name"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(47);
      var3 = var1.getlocal(4).__getattr__("encode").__call__(var2);
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(49);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(8), (PyObject)PyString.fromInterned("=?iso-2022-jp?b?dGVzdC1qYSAbJEIkWEVqOUYkNSRsJD8lYSE8JWskTztKGyhC?=\n =?iso-2022-jp?b?GyRCMnE8VCROPjVHJyRyQlQkQyRGJCQkXiQ5GyhC?="));
      var1.setline(53);
      var1.getlocal(1).__call__(var2, var1.getlocal(4).__getattr__("__unicode__").__call__(var2).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("euc-jp")), var1.getlocal(7));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_payload_encoding$3(PyFrame var1, ThreadState var2) {
      var1.setline(56);
      PyString var3 = PyString.fromInterned("¥Ï¥í¡¼¥ï¡¼¥ë¥É¡ª");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(57);
      var3 = PyString.fromInterned("euc-jp");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(58);
      PyObject var4 = var1.getglobal("Message").__call__(var2);
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(59);
      var1.getlocal(3).__getattr__("set_payload").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(60);
      var4 = var1.getglobal("unicode").__call__(var2, var1.getlocal(3).__getattr__("get_payload").__call__(var2), var1.getlocal(3).__getattr__("get_content_charset").__call__(var2));
      var1.setlocal(4, var4);
      var3 = null;
      var1.setline(61);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1), var1.getlocal(4).__getattr__("encode").__call__(var2, var1.getlocal(2)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject suite$4(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(67);
      var1.getlocal(0).__getattr__("addTest").__call__(var2, var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("TestEmailAsianCodecs")));
      var1.setline(68);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_main$5(PyFrame var1, ThreadState var2) {
      var1.setline(72);
      var1.getglobal("run_unittest").__call__(var2, var1.getglobal("TestEmailAsianCodecs"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public test_email_codecs_renamed$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestEmailAsianCodecs$1 = Py.newCode(0, var2, var1, "TestEmailAsianCodecs", 22, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "eq", "j", "g", "h", "jhello", "ghello", "long", "enc"};
      test_japanese_codecs$2 = Py.newCode(1, var2, var1, "test_japanese_codecs", 23, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "jhello", "jcode", "msg", "ustr"};
      test_payload_encoding$3 = Py.newCode(1, var2, var1, "test_payload_encoding", 55, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"suite"};
      suite$4 = Py.newCode(0, var2, var1, "suite", 65, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_main$5 = Py.newCode(0, var2, var1, "test_main", 71, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_email_codecs_renamed$py("email/test/test_email_codecs_renamed$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_email_codecs_renamed$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.TestEmailAsianCodecs$1(var2, var3);
         case 2:
            return this.test_japanese_codecs$2(var2, var3);
         case 3:
            return this.test_payload_encoding$3(var2, var3);
         case 4:
            return this.suite$4(var2, var3);
         case 5:
            return this.test_main$5(var2, var3);
         default:
            return null;
      }
   }
}
