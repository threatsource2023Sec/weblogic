import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyFrame;
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
@Filename("unittest/__init__.py")
public class unittest$py extends PyFunctionTable implements PyRunnable {
   static unittest$py self;
   static final PyCode f$0;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nPython unit testing framework, based on Erich Gamma's JUnit and Kent Beck's\nSmalltalk testing framework.\n\nThis module contains the core framework classes that form the basis of\nspecific test cases and suites (TestCase, TestSuite etc.), and also a\ntext-based utility class for running the tests and reporting the results\n (TextTestRunner).\n\nSimple usage:\n\n    import unittest\n\n    class IntegerArithmenticTestCase(unittest.TestCase):\n        def testAdd(self):  ## test method names begin 'test*'\n            self.assertEqual((1 + 2), 3)\n            self.assertEqual(0 + 1, 1)\n        def testMultiply(self):\n            self.assertEqual((0 * 10), 0)\n            self.assertEqual((5 * 8), 40)\n\n    if __name__ == '__main__':\n        unittest.main()\n\nFurther information is available in the bundled documentation, and from\n\n  http://docs.python.org/library/unittest.html\n\nCopyright (c) 1999-2003 Steve Purcell\nCopyright (c) 2003-2010 Python Software Foundation\nThis module is free software, and you may redistribute it and/or modify\nit under the same terms as Python itself, so long as this copyright message\nand disclaimer are retained in their original form.\n\nIN NO EVENT SHALL THE AUTHOR BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,\nSPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES ARISING OUT OF THE USE OF\nTHIS CODE, EVEN IF THE AUTHOR HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH\nDAMAGE.\n\nTHE AUTHOR SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING, BUT NOT\nLIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A\nPARTICULAR PURPOSE.  THE CODE PROVIDED HEREUNDER IS ON AN \"AS IS\" BASIS,\nAND THERE IS NO OBLIGATION WHATSOEVER TO PROVIDE MAINTENANCE,\nSUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.\n"));
      var1.setline(45);
      PyString.fromInterned("\nPython unit testing framework, based on Erich Gamma's JUnit and Kent Beck's\nSmalltalk testing framework.\n\nThis module contains the core framework classes that form the basis of\nspecific test cases and suites (TestCase, TestSuite etc.), and also a\ntext-based utility class for running the tests and reporting the results\n (TextTestRunner).\n\nSimple usage:\n\n    import unittest\n\n    class IntegerArithmenticTestCase(unittest.TestCase):\n        def testAdd(self):  ## test method names begin 'test*'\n            self.assertEqual((1 + 2), 3)\n            self.assertEqual(0 + 1, 1)\n        def testMultiply(self):\n            self.assertEqual((0 * 10), 0)\n            self.assertEqual((5 * 8), 40)\n\n    if __name__ == '__main__':\n        unittest.main()\n\nFurther information is available in the bundled documentation, and from\n\n  http://docs.python.org/library/unittest.html\n\nCopyright (c) 1999-2003 Steve Purcell\nCopyright (c) 2003-2010 Python Software Foundation\nThis module is free software, and you may redistribute it and/or modify\nit under the same terms as Python itself, so long as this copyright message\nand disclaimer are retained in their original form.\n\nIN NO EVENT SHALL THE AUTHOR BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,\nSPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES ARISING OUT OF THE USE OF\nTHIS CODE, EVEN IF THE AUTHOR HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH\nDAMAGE.\n\nTHE AUTHOR SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING, BUT NOT\nLIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A\nPARTICULAR PURPOSE.  THE CODE PROVIDED HEREUNDER IS ON AN \"AS IS\" BASIS,\nAND THERE IS NO OBLIGATION WHATSOEVER TO PROVIDE MAINTENANCE,\nSUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.\n");
      var1.setline(47);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("TestResult"), PyString.fromInterned("TestCase"), PyString.fromInterned("TestSuite"), PyString.fromInterned("TextTestRunner"), PyString.fromInterned("TestLoader"), PyString.fromInterned("FunctionTestCase"), PyString.fromInterned("main"), PyString.fromInterned("defaultTestLoader"), PyString.fromInterned("SkipTest"), PyString.fromInterned("skip"), PyString.fromInterned("skipIf"), PyString.fromInterned("skipUnless"), PyString.fromInterned("expectedFailure"), PyString.fromInterned("TextTestResult"), PyString.fromInterned("installHandler"), PyString.fromInterned("registerResult"), PyString.fromInterned("removeResult"), PyString.fromInterned("removeHandler")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(54);
      var1.getname("__all__").__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("getTestCaseNames"), PyString.fromInterned("makeSuite"), PyString.fromInterned("findTestCases")})));
      var1.setline(56);
      PyObject var5 = var1.getname("True");
      var1.setlocal("__unittest", var5);
      var3 = null;
      var1.setline(58);
      String[] var6 = new String[]{"TestResult"};
      PyObject[] var7 = imp.importFrom("result", var6, var1, 1);
      PyObject var4 = var7[0];
      var1.setlocal("TestResult", var4);
      var4 = null;
      var1.setline(59);
      var6 = new String[]{"TestCase", "FunctionTestCase", "SkipTest", "skip", "skipIf", "skipUnless", "expectedFailure"};
      var7 = imp.importFrom("case", var6, var1, 1);
      var4 = var7[0];
      var1.setlocal("TestCase", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("FunctionTestCase", var4);
      var4 = null;
      var4 = var7[2];
      var1.setlocal("SkipTest", var4);
      var4 = null;
      var4 = var7[3];
      var1.setlocal("skip", var4);
      var4 = null;
      var4 = var7[4];
      var1.setlocal("skipIf", var4);
      var4 = null;
      var4 = var7[5];
      var1.setlocal("skipUnless", var4);
      var4 = null;
      var4 = var7[6];
      var1.setlocal("expectedFailure", var4);
      var4 = null;
      var1.setline(61);
      var6 = new String[]{"BaseTestSuite", "TestSuite"};
      var7 = imp.importFrom("suite", var6, var1, 1);
      var4 = var7[0];
      var1.setlocal("BaseTestSuite", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("TestSuite", var4);
      var4 = null;
      var1.setline(62);
      var6 = new String[]{"TestLoader", "defaultTestLoader", "makeSuite", "getTestCaseNames", "findTestCases"};
      var7 = imp.importFrom("loader", var6, var1, 1);
      var4 = var7[0];
      var1.setlocal("TestLoader", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("defaultTestLoader", var4);
      var4 = null;
      var4 = var7[2];
      var1.setlocal("makeSuite", var4);
      var4 = null;
      var4 = var7[3];
      var1.setlocal("getTestCaseNames", var4);
      var4 = null;
      var4 = var7[4];
      var1.setlocal("findTestCases", var4);
      var4 = null;
      var1.setline(64);
      var6 = new String[]{"TestProgram", "main"};
      var7 = imp.importFrom("main", var6, var1, 1);
      var4 = var7[0];
      var1.setlocal("TestProgram", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("main", var4);
      var4 = null;
      var1.setline(65);
      var6 = new String[]{"TextTestRunner", "TextTestResult"};
      var7 = imp.importFrom("runner", var6, var1, 1);
      var4 = var7[0];
      var1.setlocal("TextTestRunner", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("TextTestResult", var4);
      var4 = null;
      var1.setline(66);
      var6 = new String[]{"installHandler", "registerResult", "removeResult", "removeHandler"};
      var7 = imp.importFrom("signals", var6, var1, 1);
      var4 = var7[0];
      var1.setlocal("installHandler", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("registerResult", var4);
      var4 = null;
      var4 = var7[2];
      var1.setlocal("removeResult", var4);
      var4 = null;
      var4 = var7[3];
      var1.setlocal("removeHandler", var4);
      var4 = null;
      var1.setline(69);
      var5 = var1.getname("TextTestResult");
      var1.setlocal("_TextTestResult", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public unittest$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new unittest$py("unittest$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(unittest$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         default:
            return null;
      }
   }
}
