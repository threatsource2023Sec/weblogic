package org.python.jsr223;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.nio.CharBuffer;
import java.util.Arrays;
import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import junit.framework.TestCase;
import org.junit.Assert;
import org.python.core.Options;
import org.python.core.PyString;

public class ScriptEngineTest extends TestCase {
   public void testEvalString() throws ScriptException {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine pythonEngine = manager.getEngineByName("python");
      ScriptContext context = pythonEngine.getContext();
      context.setAttribute("javax.script.filename", "sample.py", 100);
      assertNull(pythonEngine.eval("x = 5"));
      assertEquals(5, pythonEngine.eval("x"));
      assertEquals("sample.py", pythonEngine.eval("__file__"));
      pythonEngine.eval("import sys");
      assertEquals(Arrays.asList("sample.py"), pythonEngine.eval("sys.argv"));
   }

   public void testEvalStringArgv() throws ScriptException {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine pythonEngine = manager.getEngineByName("python");
      ScriptContext context = pythonEngine.getContext();
      context.setAttribute("javax.script.filename", "sample.py", 100);
      context.setAttribute("javax.script.argv", new String[]{"foo", "bar"}, 100);
      assertNull(pythonEngine.eval("x = 5"));
      assertEquals(5, pythonEngine.eval("x"));
      assertEquals("sample.py", pythonEngine.eval("__file__"));
      pythonEngine.eval("import sys");
      assertEquals(Arrays.asList("sample.py", "foo", "bar"), pythonEngine.eval("sys.argv"));
   }

   public void testEvalStringNoFilenameWithArgv() throws ScriptException {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine pythonEngine = manager.getEngineByName("python");
      ScriptContext context = pythonEngine.getContext();
      context.setAttribute("javax.script.argv", new String[]{"foo", "bar"}, 100);
      assertNull(pythonEngine.eval("x = 5"));
      assertEquals(5, pythonEngine.eval("x"));
      boolean gotExpectedException = false;

      try {
         pythonEngine.eval("__file__");
      } catch (ScriptException var6) {
         assertTrue(var6.getMessage().startsWith("NameError: "));
         gotExpectedException = true;
      }

      if (!gotExpectedException) {
         fail("Excepted __file__ to be undefined");
      }

      pythonEngine.eval("import sys");
      assertEquals(Arrays.asList("foo", "bar"), pythonEngine.eval("sys.argv"));
   }

   public void testSyntaxError() {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine pythonEngine = manager.getEngineByName("python");

      try {
         pythonEngine.eval("5q");
      } catch (ScriptException var4) {
         assertEquals(var4.getColumnNumber(), 1);
         assertEquals(var4.getLineNumber(), 1);
         assertTrue(var4.getMessage().startsWith("SyntaxError: "));
         return;
      }

      assertTrue("Expected a ScriptException", false);
   }

   public void testPythonException() {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine pythonEngine = manager.getEngineByName("python");

      try {
         pythonEngine.eval("pass\ndel undefined");
      } catch (ScriptException var4) {
         assertEquals(var4.getLineNumber(), 2);
         assertTrue(var4.getMessage().startsWith("NameError: "));
         return;
      }

      assertTrue("Expected a ScriptException", false);
   }

   public void testScriptFilename() {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine pythonEngine = manager.getEngineByName("python");
      SimpleScriptContext scriptContext = new SimpleScriptContext();
      scriptContext.setAttribute("javax.script.filename", "sample.py", 100);

      try {
         pythonEngine.eval("foo", scriptContext);
      } catch (ScriptException var5) {
         assertEquals("sample.py", var5.getFileName());
         return;
      }

      assertTrue("Expected a ScriptException", false);
   }

   public void testCompileEvalString() throws ScriptException {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine pythonEngine = manager.getEngineByName("python");
      ScriptContext context = pythonEngine.getContext();
      context.setAttribute("javax.script.filename", "sample.py", 100);
      CompiledScript five = ((Compilable)pythonEngine).compile("5");
      assertEquals(5, five.eval());
      assertEquals("sample.py", pythonEngine.eval("__file__"));
      pythonEngine.eval("import sys");
      assertEquals(Arrays.asList("sample.py"), pythonEngine.eval("sys.argv"));
   }

   public void testEvalReader() throws ScriptException {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine pythonEngine = manager.getEngineByName("python");
      ScriptContext context = pythonEngine.getContext();
      context.setAttribute("javax.script.filename", "sample.py", 100);
      assertNull(pythonEngine.eval(new StringReader("x = 5")));
      assertEquals(5, pythonEngine.eval(new StringReader("x")));
      assertEquals("sample.py", pythonEngine.eval("__file__"));
      pythonEngine.eval("import sys");
      assertEquals(Arrays.asList("sample.py"), pythonEngine.eval("sys.argv"));
   }

   public void testCompileEvalReader() throws ScriptException {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine pythonEngine = manager.getEngineByName("python");
      ScriptContext context = pythonEngine.getContext();
      context.setAttribute("javax.script.filename", "sample.py", 100);
      CompiledScript five = ((Compilable)pythonEngine).compile(new StringReader("5"));
      assertEquals(5, five.eval());
      assertEquals("sample.py", pythonEngine.eval("__file__"));
      pythonEngine.eval("import sys");
      assertEquals(Arrays.asList("sample.py"), pythonEngine.eval("sys.argv"));
   }

   public void testBindings() throws ScriptException {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine pythonEngine = manager.getEngineByName("python");
      pythonEngine.put("a", 42);
      assertEquals(42, pythonEngine.eval("a"));
      assertNull(pythonEngine.eval("x = 5"));
      assertEquals(5, pythonEngine.get("x"));
      assertNull(pythonEngine.eval("del x"));
      assertNull(pythonEngine.get("x"));
   }

   public void testThreadLocalBindings() throws ScriptException, InterruptedException {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine pythonEngine = manager.getEngineByName("python");
      pythonEngine.put("a", 42);
      pythonEngine.put("x", 15);
      ThreadLocalBindingsTest test = new ThreadLocalBindingsTest(pythonEngine);
      Thread thread = new Thread(test);
      thread.run();
      thread.join();
      assertNull(test.exception);
      assertEquals(-7, test.x);
      assertEquals(15, pythonEngine.get("x"));
      assertNull(pythonEngine.eval("del x"));
      assertNull(pythonEngine.get("x"));
   }

   public void testInvoke() throws ScriptException, NoSuchMethodException {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine pythonEngine = manager.getEngineByName("python");
      Invocable invocableEngine = (Invocable)pythonEngine;
      assertNull(pythonEngine.eval("def f(x): return abs(x)"));
      assertEquals(5, invocableEngine.invokeFunction("f", new Object[]{-5}));
      assertEquals("spam", invocableEngine.invokeMethod(new PyString("  spam  "), "strip", new Object[0]));
      assertEquals("spam", invocableEngine.invokeMethod("  spam  ", "strip", new Object[0]));
   }

   public void testInvokeFunctionNoSuchMethod() throws ScriptException {
      ScriptEngineManager manager = new ScriptEngineManager();
      Invocable invocableEngine = (Invocable)manager.getEngineByName("python");

      try {
         invocableEngine.invokeFunction("undefined", new Object[0]);
      } catch (NoSuchMethodException var4) {
         return;
      }

      assertTrue("Expected a NoSuchMethodException", false);
   }

   public void testInvokeMethodNoSuchMethod() throws ScriptException {
      ScriptEngineManager manager = new ScriptEngineManager();
      Invocable invocableEngine = (Invocable)manager.getEngineByName("python");

      try {
         invocableEngine.invokeMethod("eggs", "undefined", new Object[0]);
         fail("Expected a NoSuchMethodException");
      } catch (NoSuchMethodException var4) {
         assertEquals("undefined", var4.getMessage());
      }

   }

   public void testGetInterface() throws ScriptException, IOException {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine pythonEngine = manager.getEngineByName("python");
      Invocable invocableEngine = (Invocable)pythonEngine;
      assertNull(pythonEngine.eval("def read(cb): return 1"));
      Readable readable = (Readable)invocableEngine.getInterface(Readable.class);
      assertEquals(1, readable.read((CharBuffer)null));
      assertNull(pythonEngine.eval("class C(object):\n    def read(self, cb): return 2\nc = C()"));
      readable = (Readable)invocableEngine.getInterface(pythonEngine.get("c"), Readable.class);
      assertEquals(2, readable.read((CharBuffer)null));
   }

   public void testInvokeMethodNoSuchArgs() throws ScriptException, NoSuchMethodException {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine pythonEngine = manager.getEngineByName("python");
      Invocable invocableEngine = (Invocable)pythonEngine;
      Object newStringCapitalize = invocableEngine.invokeMethod("test", "capitalize", new Object[0]);
      assertEquals(newStringCapitalize, "Test");
   }

   public void testPdb() {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine pythonEngine = manager.getEngineByName("python");
      String pdbString = "from pdb import set_trace; set_trace()";

      try {
         pythonEngine.eval(pdbString);
         fail("bdb.BdbQuit expected");
      } catch (ScriptException var5) {
         assertTrue(var5.getMessage().startsWith("bdb.BdbQuit"));
      }

   }

   public void testScope_repr() throws ScriptException {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine pythonEngine = manager.getEngineByName("python");
      pythonEngine.eval("a = 4");
      pythonEngine.eval("b = 'hi'");
      String repr = (String)pythonEngine.eval("repr(locals())");
      Assert.assertTrue(repr.contains("'a': 4"));
      Assert.assertTrue(repr.contains("'b': u'hi'"));
   }

   public void testScope_iter() throws ScriptException {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine pythonEngine = manager.getEngineByName("python");
      pythonEngine.eval("a = 4");
      pythonEngine.eval("b = 'hi'");
      assertEquals("['__builtins__', 'a', 'b']", pythonEngine.eval("repr(sorted((item for item in locals())))"));
   }

   public void testScope_lookup() throws ScriptException {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine pythonEngine = manager.getEngineByName("python");
      pythonEngine.eval("a = 4");
      pythonEngine.eval("b = 'hi'");
      pythonEngine.eval("var_a = locals()['a']");
      pythonEngine.eval("arepr = repr(var_a)");
      assertEquals("4", pythonEngine.get("arepr"));
   }

   public void testIssue1681() throws ScriptException {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine pythonEngine = manager.getEngineByName("python");
      pythonEngine.eval("from org.python.jsr223 import PythonCallable\nclass MyPythonCallable(PythonCallable):\n    def getAString(self): return 'a string'\n\nresult = MyPythonCallable().getAString()\ntest = MyPythonCallable()\nresult2 = test.getAString()");
      assertEquals("a string", pythonEngine.get("result"));
      assertEquals("a string", pythonEngine.get("result2"));
   }

   public void testIssue1698() throws ScriptException {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine pythonEngine = manager.getEngineByName("python");
      pythonEngine.eval("import warnings");
      pythonEngine.eval("warnings.warn('test')");
   }

   public void testSiteImportedByDefault() throws ScriptException {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine pythonEngine = manager.getEngineByName("python");
      pythonEngine.eval("import sys");
      pythonEngine.eval("'site' in sys.modules");
   }

   public void testSiteCanBeNotImported() throws ScriptException {
      try {
         Options.importSite = false;
         ScriptEngineManager manager = new ScriptEngineManager();
         ScriptEngine pythonEngine = manager.getEngineByName("python");
         pythonEngine.eval("import sys");
         pythonEngine.eval("'site' not in sys.modules");
      } finally {
         Options.importSite = true;
      }

   }

   public void testIssue2090() throws ScriptException {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine pythonEngine = manager.getEngineByName("python");
      pythonEngine.eval("a = 10L\nb = a-1");
      Object r = pythonEngine.get("b");
      assertEquals(new BigInteger("9"), r);
   }

   class ThreadLocalBindingsTest implements Runnable {
      ScriptEngine engine;
      Object x;
      Throwable exception;

      public ThreadLocalBindingsTest(ScriptEngine engine) {
         this.engine = engine;
      }

      public void run() {
         try {
            Bindings bindings = this.engine.createBindings();
            junit.framework.Assert.assertNull(this.engine.eval("try: a\nexcept NameError: pass\nelse: raise Exception('a is defined', a)", bindings));
            bindings.put("x", -7);
            this.x = this.engine.eval("x", bindings);
         } catch (Throwable var2) {
            var2.printStackTrace();
            this.exception = var2;
         }

      }
   }
}
