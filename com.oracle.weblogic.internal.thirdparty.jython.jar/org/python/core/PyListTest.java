package org.python.core;

import junit.framework.TestCase;

public class PyListTest extends TestCase {
   private PyList p = null;

   protected void setUp() throws Exception {
      this.p = new PyList();
      this.p.add("foo");
      this.p.add("bar");
   }

   protected void tearDown() throws Exception {
      this.p = null;
   }

   public void testIndexOf() {
      assertEquals(0, this.p.indexOf("foo"));
      assertEquals(1, this.p.indexOf("bar"));
   }

   public void testToArray() {
      Object[] test = new String[1];
      String[] s = (String[])((String[])this.p.toArray(test));
      assertEquals(s[0], "foo");
      assertEquals(s[1], "bar");
   }
}
