package json.tests;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("json/tests/test_pass1.py")
public class test_pass1$py extends PyFunctionTable implements PyRunnable {
   static test_pass1$py self;
   static final PyCode f$0;
   static final PyCode TestPass1$1;
   static final PyCode test_parse$2;
   static final PyCode TestPyPass1$3;
   static final PyCode TestCPass1$4;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      String[] var3 = new String[]{"PyTest", "CTest"};
      PyObject[] var5 = imp.importFrom("json.tests", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("PyTest", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("CTest", var4);
      var4 = null;
      var1.setline(5);
      PyString var6 = PyString.fromInterned("\n[\n    \"JSON Test Pattern pass1\",\n    {\"object with 1 member\":[\"array with 1 element\"]},\n    {},\n    [],\n    -42,\n    true,\n    false,\n    null,\n    {\n        \"integer\": 1234567890,\n        \"real\": -9876.543210,\n        \"e\": 0.123456789e-12,\n        \"E\": 1.234567890E+34,\n        \"\":  23456789012E66,\n        \"zero\": 0,\n        \"one\": 1,\n        \"space\": \" \",\n        \"quote\": \"\\\"\",\n        \"backslash\": \"\\\\\",\n        \"controls\": \"\\b\\f\\n\\r\\t\",\n        \"slash\": \"/ & \\/\",\n        \"alpha\": \"abcdefghijklmnopqrstuvwyz\",\n        \"ALPHA\": \"ABCDEFGHIJKLMNOPQRSTUVWYZ\",\n        \"digit\": \"0123456789\",\n        \"0123456789\": \"digit\",\n        \"special\": \"`1~!@#$%^&*()_+-={':[,]}|;.</>?\",\n        \"hex\": \"\\u0123\\u4567\\u89AB\\uCDEF\\uabcd\\uef4A\",\n        \"true\": true,\n        \"false\": false,\n        \"null\": null,\n        \"array\":[  ],\n        \"object\":{  },\n        \"address\": \"50 St. James Street\",\n        \"url\": \"http://www.JSON.org/\",\n        \"comment\": \"// /* <!-- --\",\n        \"# -- --> */\": \" \",\n        \" s p a c e d \" :[1,2 , 3\n\n,\n\n4 , 5        ,          6           ,7        ],\"compact\":[1,2,3,4,5,6,7],\n        \"jsontext\": \"{\\\"object with 1 member\\\":[\\\"array with 1 element\\\"]}\",\n        \"quotes\": \"&#34; \\u0022 %22 0x22 034 &#x22;\",\n        \"\\/\\\\\\\"\\uCAFE\\uBABE\\uAB98\\uFCDE\\ubcda\\uef4A\\b\\f\\n\\r\\t`1~!@#$%^&*()_+-=[]{}|;:',./<>?\"\n: \"A key can be any string\"\n    },\n    0.5 ,98.6\n,\n99.44\n,\n\n1066,\n1e1,\n0.1e1,\n1e-1,\n1e00,2e+00,2e-00\n,\"rosebud\"]\n");
      var1.setlocal("JSON", var6);
      var3 = null;
      var1.setline(66);
      var5 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("TestPass1", var5, TestPass1$1);
      var1.setlocal("TestPass1", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(74);
      var5 = new PyObject[]{var1.getname("TestPass1"), var1.getname("PyTest")};
      var4 = Py.makeClass("TestPyPass1", var5, TestPyPass1$3);
      var1.setlocal("TestPyPass1", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(75);
      var5 = new PyObject[]{var1.getname("TestPass1"), var1.getname("CTest")};
      var4 = Py.makeClass("TestCPass1", var5, TestCPass1$4);
      var1.setlocal("TestCPass1", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestPass1$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(67);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_parse$2, (PyObject)null);
      var1.setlocal("test_parse", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_parse$2(PyFrame var1, ThreadState var2) {
      var1.setline(69);
      PyObject var3 = var1.getlocal(0).__getattr__("loads").__call__(var2, var1.getglobal("JSON"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(70);
      var3 = var1.getlocal(0).__getattr__("dumps").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(71);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("loads").__call__(var2, var1.getlocal(2)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestPyPass1$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(74);
      return var1.getf_locals();
   }

   public PyObject TestCPass1$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(75);
      return var1.getf_locals();
   }

   public test_pass1$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestPass1$1 = Py.newCode(0, var2, var1, "TestPass1", 66, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "res", "out"};
      test_parse$2 = Py.newCode(1, var2, var1, "test_parse", 67, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestPyPass1$3 = Py.newCode(0, var2, var1, "TestPyPass1", 74, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestCPass1$4 = Py.newCode(0, var2, var1, "TestCPass1", 75, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_pass1$py("json/tests/test_pass1$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_pass1$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.TestPass1$1(var2, var3);
         case 2:
            return this.test_parse$2(var2, var3);
         case 3:
            return this.TestPyPass1$3(var2, var3);
         case 4:
            return this.TestCPass1$4(var2, var3);
         default:
            return null;
      }
   }
}
