package org.python.util;

import org.junit.Assert;
import org.junit.Test;
import org.python.core.Console;
import org.python.core.PlainConsole;
import org.python.core.Py;

public class jythonTestPlain {
   private static final String PYTHON_CONSOLE = "python.console";
   private static String[] commands = new String[]{"-c", "import sys; print type(sys._jy_console)"};

   @Test
   public void testFallbackConsole() {
      System.out.println("testFallbackConsole");
      System.getProperties().setProperty("python.console", "org.python.util.InteractiveConsole");
      jython.run(commands);
      Console console = Py.getConsole();
      Assert.assertEquals(PlainConsole.class, console.getClass());
   }

   @Test
   public void testChangeConsole() throws Exception {
      System.out.println("testChangeConsole");
      System.getProperties().setProperty("python.console", "org.python.core.PlainConsole");
      PythonInterpreter interp = new PythonInterpreter();
      Py.installConsole(new JLineConsole((String)null));
      jython.run(commands);
      Console console = Py.getConsole();
      Assert.assertEquals(JLineConsole.class, console.getClass());
      interp.cleanup();
   }
}
