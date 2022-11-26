package org.python.tests.props;

import junit.framework.TestCase;
import org.python.core.PyStringMap;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

public class BeanPropertyTest extends TestCase {
   private PythonInterpreter interp;

   protected void setUp() throws Exception {
      this.interp = new PythonInterpreter(new PyStringMap(), new PySystemState());
   }

   public void testReadonly() {
      this.interp.exec("from org.python.tests.props import Readonly;Readonly().a = 'test'");
   }

   public void testShadowing() {
      this.interp.execfile("tests/python/prop_test.py");
   }
}
