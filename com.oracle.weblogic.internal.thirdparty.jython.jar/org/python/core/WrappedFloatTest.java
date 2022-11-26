package org.python.core;

import junit.framework.TestCase;
import org.python.util.PythonInterpreter;

public class WrappedFloatTest extends TestCase {
   private PythonInterpreter interp;
   private WrappedFloat a;
   private WrappedFloat b;

   protected void setUp() throws Exception {
      this.interp = new PythonInterpreter(new PyStringMap(), new PySystemState());
      this.a = new WrappedFloat();
      this.b = new WrappedFloat();
      this.a.setMutableValue(13.0);
      this.b.setMutableValue(17.0);
      this.interp.set("a", (PyObject)this.a);
      this.interp.set("b", (PyObject)this.b);
   }

   public void testAdd() {
      this.interp.exec("c = a + b");
      assertEquals(new PyFloat(30.0F), this.interp.get("c"));
      this.b.setMutableValue(18.0);
      this.interp.exec("c = a + b");
      assertEquals(new PyFloat(31.0F), this.interp.get("c"));
   }

   public void testDiv() {
      this.interp.exec("c = a / b");
      assertEquals(new PyFloat(0.7647058823529411), this.interp.get("c"));
   }

   public void testMod() {
      this.interp.exec("c = b % a");
      assertEquals(new PyFloat(4.0F), this.interp.get("c"));
   }

   static class WrappedFloat extends PyFloat {
      private double mutableValue;

      public WrappedFloat() {
         super(0.0F);
      }

      public double getValue() {
         return this.mutableValue;
      }

      public void setMutableValue(double newValue) {
         this.mutableValue = newValue;
      }
   }
}
