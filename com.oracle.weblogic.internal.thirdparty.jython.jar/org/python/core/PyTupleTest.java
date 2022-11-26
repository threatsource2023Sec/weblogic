package org.python.core;

import junit.framework.TestCase;

public class PyTupleTest extends TestCase {
   private PyTuple p = null;

   protected void setUp() throws Exception {
      this.p = new PyTuple(new PyObject[]{new PyString("foo"), new PyString("bar")});
   }

   protected void tearDown() throws Exception {
      this.p = null;
   }

   public void testIndexOf() {
      PyTuple p = new PyTuple(new PyObject[]{new PyString("foo"), new PyString("bar")});
      assertEquals(0, p.indexOf("foo"));
      assertEquals(1, p.indexOf("bar"));
   }

   public void testToArray() {
      Object[] test = new String[1];
      String[] s = (String[])((String[])this.p.toArray(test));
      assertEquals(s[0], "foo");
      assertEquals(s[1], "bar");
   }
}
