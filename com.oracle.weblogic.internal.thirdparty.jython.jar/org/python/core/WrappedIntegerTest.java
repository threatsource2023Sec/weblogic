package org.python.core;

import junit.framework.TestCase;
import org.python.util.PythonInterpreter;

public class WrappedIntegerTest extends TestCase {
   private PythonInterpreter interp;
   private WrappedInteger a;
   private WrappedInteger b;

   protected void setUp() throws Exception {
      this.interp = new PythonInterpreter(new PyStringMap(), new PySystemState());
      this.a = new WrappedInteger();
      this.b = new WrappedInteger();
      this.a.setMutableValue(13);
      this.b.setMutableValue(17);
      this.interp.set("a", (PyObject)this.a);
      this.interp.set("b", (PyObject)this.b);
   }

   public void testAdd() {
      this.interp.exec("c = a + b");
      assertEquals(new PyInteger(30), this.interp.get("c"));
      this.b.setMutableValue(18);
      this.interp.exec("c = a + b");
      assertEquals(new PyInteger(31), this.interp.get("c"));
   }

   public void testDiv() {
      this.interp.exec("c = a / float(b)");
      assertEquals(new PyFloat(0.7647058823529411), this.interp.get("c"));
   }

   public void testMod() {
      this.interp.exec("c = b % a");
      assertEquals(new PyInteger(4), this.interp.get("c"));
   }

   static class WrappedInteger extends PyInteger {
      private int mutableValue;

      public WrappedInteger() {
         super(0);
      }

      public int getValue() {
         return this.mutableValue;
      }

      public void setMutableValue(int newValue) {
         this.mutableValue = newValue;
      }
   }
}
