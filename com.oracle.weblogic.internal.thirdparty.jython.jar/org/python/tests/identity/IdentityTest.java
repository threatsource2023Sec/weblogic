package org.python.tests.identity;

import junit.framework.TestCase;
import org.python.core.PyStringMap;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

public class IdentityTest extends TestCase {
   private PythonInterpreter interp;

   protected void setUp() throws Exception {
      PySystemState sys = new PySystemState();
      this.interp = new PythonInterpreter(new PyStringMap(), sys);
   }

   public void testReadonly() {
      this.interp.execfile("tests/python/identity_test.py");
   }
}
