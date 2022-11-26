package org.python.util;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.python.core.Console;
import org.python.core.PlainConsole;
import org.python.core.Py;
import org.python.core.PyDictionary;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyUnicode;

public class InterpreterTest extends TestCase {
   public void testBasicEval() throws Exception {
      PyDictionary test = new PyDictionary();
      test.__setitem__(new PyUnicode("one"), new PyUnicode("two"));
      PythonInterpreter.initialize(System.getProperties(), (Properties)null, new String[0]);
      PythonInterpreter interp = new PythonInterpreter();
      PyObject pyo = interp.eval("{u'one': u'two'}");
      assertEquals(test, pyo);
   }

   public void testMultipleThreads() {
      final CountDownLatch doneSignal = new CountDownLatch(10);

      for(int i = 0; i < 10; ++i) {
         (new Thread() {
            public void run() {
               PythonInterpreter interp = new PythonInterpreter();
               interp.exec("import sys");
               interp.set("a", (PyObject)(new PyInteger(41)));
               int set = (Integer)Py.tojava(interp.get("a"), Integer.class);
               Assert.assertEquals(41, set);
               interp.exec("x = 'hello ' + 'goodbye'");
               Assert.assertEquals("hello goodbye", (String)Py.tojava(interp.get("x"), String.class));
               doneSignal.countDown();
            }
         }).start();
      }

      try {
         doneSignal.await();
      } catch (InterruptedException var3) {
         System.err.println("Interpreters in multiple threads test interrupted, bailing");
      }

   }

   public void testCallInstancesFromJava() {
      PythonInterpreter interp = new PythonInterpreter();
      interp.exec("class Blah(object):\n    def __init__(self, val):\n        self.val = val\n    def incval(self):\n        self.val += 1\n        return self.val");
      PyObject blahClass = interp.get("Blah");
      int base = 42;
      PyObject blahInstance = blahClass.__call__((PyObject)(new PyInteger(base)));

      for(int i = 0; i < 4; ++i) {
         ++base;
         assertEquals(base, blahInstance.invoke("incval").__tojava__(Integer.class));
      }

   }

   public void testConsoleIsPlain() throws Exception {
      PythonInterpreter interp = new PythonInterpreter();
      interp.exec("import sys");
      Console console = (Console)Py.tojava(interp.eval("sys._jy_console"), Console.class);
      assertEquals(PlainConsole.class, console.getClass());
      Console console2 = Py.getConsole();
      assertEquals(PlainConsole.class, console2.getClass());
   }
}
