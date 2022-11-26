package org.python.tests;

import junit.framework.TestCase;
import org.python.core.Py;
import org.python.util.PythonInterpreter;

public class ExceptionTest extends TestCase {
   protected Thrower t;

   public void setUp() {
      String raiser = "from java.lang import Throwable\nfrom org.python.tests.ExceptionTest import Checked, Thrower\nclass Raiser(Thrower):\n    def checked(self):\n         raise Checked()\n    def checkedOrRuntime(self, checked):\n         if checked:\n             raise Checked()\n         else:\n             raise Throwable()\nr = Raiser()";
      PythonInterpreter interp = new PythonInterpreter();
      interp.exec(raiser);
      this.t = (Thrower)Py.tojava(interp.get("r"), Thrower.class);
   }

   public void testRaisingCheckedException() {
      try {
         this.t.checked();
         fail("Calling checked should raise Checked!");
      } catch (Checked var3) {
      }

      try {
         this.t.checkedOrRuntime(true);
         fail("Calling checkedOrRuntime(true) should raise Checked!");
      } catch (Checked var2) {
      }

   }

   public void testRaisingRuntimeException() {
      try {
         this.t.checkedOrRuntime(false);
         fail("Calling checkedOrRuntime(false) should raise Throwable!");
      } catch (Checked var2) {
         fail("Calling checkedOrRuntime(false) should raise Throwable!");
      } catch (Throwable var3) {
      }

   }

   public interface Thrower {
      void checked() throws Checked;

      void checkedOrRuntime(boolean var1) throws Checked;
   }

   public static class Checked extends Exception {
   }
}
