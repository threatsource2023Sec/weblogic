package org.python.tests.constructor_kwargs;

import junit.framework.TestCase;
import org.python.core.PyStringMap;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

public class ConstructorKWArgsTest extends TestCase {
   private PythonInterpreter interp;

   protected void setUp() throws Exception {
      PySystemState sys = new PySystemState();
      this.interp = new PythonInterpreter(new PyStringMap(), sys);
   }

   public void testConstructorKWArgs() {
      this.interp.execfile("tests/python/constructorkwargs_test.py");
   }
}
