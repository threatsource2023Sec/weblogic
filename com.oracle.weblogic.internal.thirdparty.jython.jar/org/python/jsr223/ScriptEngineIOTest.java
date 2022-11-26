package org.python.jsr223;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import junit.framework.TestCase;

public class ScriptEngineIOTest extends TestCase {
   ScriptEngineFactory pythonEngineFactory;
   ScriptEngine pythonEngine;

   public void setUp() throws ScriptException {
      this.pythonEngineFactory = new PyScriptEngineFactory();
      this.pythonEngine = new PyScriptEngine(this.pythonEngineFactory);
   }

   public void testEvalString() throws ScriptException {
      assertNull(this.pythonEngine.eval("x = 5"));
      assertEquals(5, this.pythonEngine.eval("x"));
   }

   public void testReadline() throws ScriptException {
      String testString = "Shazaam Batman!\n";
      this.pythonEngine.getContext().setReader(new StringReader("Shazaam Batman!\n"));
      assertNull(this.pythonEngine.eval("import sys"));
      assertEquals("Shazaam Batman!\n", this.pythonEngine.eval("sys.stdin.readline()"));
   }

   public void testReadlines() throws ScriptException {
      String testString = "Holy Smokes Batman!\nBIF!\r\n\nKAPOW!!!\rTHE END.";
      this.pythonEngine.getContext().setReader(new StringReader("Holy Smokes Batman!\nBIF!\r\n\nKAPOW!!!\rTHE END."));
      assertNull(this.pythonEngine.eval("import sys"));
      Object o = this.pythonEngine.eval("''.join(sys.stdin.readlines())");
      assertEquals("Holy Smokes Batman!\nBIF!\n\nKAPOW!!!\nTHE END.\n", o);
   }

   public void testWriter() throws ScriptException {
      StringWriter sw = new StringWriter();
      this.pythonEngine.getContext().setWriter(sw);
      String testString = "It is a wonderful world.";
      assertNull(this.pythonEngine.eval("print 'It is a wonderful world.',"));
      assertEquals("It is a wonderful world.", sw.toString());
   }

   public void testErrorWriter() throws ScriptException {
      StringWriter stdout = new StringWriter();
      StringWriter stderr = new StringWriter();
      this.pythonEngine.getContext().setWriter(stdout);
      this.pythonEngine.getContext().setErrorWriter(stderr);
      String testString1 = "It is a wonderful world.";
      String testString2 = "Stuff happens!";
      assertNull(this.pythonEngine.eval("import sys"));
      assertNull(this.pythonEngine.eval("sys.stdout.write('It is a wonderful world.')"));
      assertNull(this.pythonEngine.eval("sys.stderr.write('Stuff happens!')"));
      assertEquals("It is a wonderful world.", stdout.toString());
      assertEquals("Stuff happens!", stderr.toString());
   }

   public void testEvalWithReader() throws ScriptException, FileNotFoundException {
      ScriptEngineManager manager = new ScriptEngineManager();
      String engineType = "jython";
      ScriptEngine engine = manager.getEngineByName("jython");
      StringWriter stdout = new StringWriter();
      StringWriter stderr = new StringWriter();
      engine.getContext().setWriter(stdout);
      engine.getContext().setErrorWriter(stderr);
      Bindings bindings = new SimpleBindings();
      bindings.put("firstLevelNodes", 10);
      bindings.put("secondLevelNodes", 5);
      engine.setBindings(bindings, 100);
      engine.setBindings(bindings, 100);
      Reader dfsScript = new FileReader("tests/python/dfs.py");

      for(int i = 1; i <= 10; ++i) {
         engine.eval(dfsScript);
         assertEquals(61, engine.get("result"));
      }

   }

   public void testGetInterfaceCharSequence1() throws ScriptException, IOException {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine engine = manager.getEngineByName("python");
      Invocable invocableEngine = (Invocable)engine;
      assertNull(engine.eval("from java.lang import CharSequence\nclass MyString(CharSequence):\n   def length(self): return 3\n   def charAt(self, index): return 'a'\n   def subSequence(self, start, end): return \"\"\n   def toString(self): return \"aaa\"\nc = MyString()"));
      CharSequence seq = (CharSequence)invocableEngine.getInterface(engine.get("c"), CharSequence.class);
      assertEquals("aaa", seq.toString());
   }

   public void testGetInterfaceCharSequence2() throws ScriptException, IOException {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine pythonEngine = manager.getEngineByName("python");
      Invocable invocableEngine = (Invocable)pythonEngine;
      assertNull(pythonEngine.eval("from java.lang import StringBuilder\r\nc = StringBuilder(\"abc\")\r\n"));
      CharSequence seq = (CharSequence)invocableEngine.getInterface(pythonEngine.get("c"), CharSequence.class);
      assertEquals("abc", seq.toString());
   }
}
