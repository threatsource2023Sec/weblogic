package org.python.util;

import org.junit.Assert;
import org.junit.Test;
import org.python.core.Console;
import org.python.core.Py;

public class jythonTest {
   private static String[] commands = new String[]{"-c", "import sys; print type(sys._jy_console)"};

   @Test
   public void testDefaultConsole() {
      jython.run(commands);
      Console console = Py.getConsole();
      Assert.assertEquals(JLineConsole.class, console.getClass());
   }
}
