package org.python.core;

import junit.framework.TestCase;
import org.python.util.PythonInterpreter;

public class WrappedBooleanTest extends TestCase {
   private PythonInterpreter interp;
   private WrappedBoolean a;
   private WrappedBoolean b;

   protected void setUp() throws Exception {
      this.interp = new PythonInterpreter(new PyStringMap(), new PySystemState());
      this.a = new WrappedBoolean();
      this.b = new WrappedBoolean();
      this.a.setMutableValue(true);
      this.b.setMutableValue(false);
      this.interp.set("a", (PyObject)this.a);
      this.interp.set("b", (PyObject)this.b);
   }

   public void testAnd() {
      this.interp.exec("c = a and b");
      assertEquals(new PyBoolean(false), this.interp.get("c"));
      this.b.setMutableValue(true);
      this.interp.exec("c = a and b");
      assertEquals(new PyBoolean(true), this.interp.get("c"));
   }

   public void testOr() {
      this.interp.exec("c = a or b");
      assertEquals(new PyBoolean(true), this.interp.get("c"));
      this.a.setMutableValue(false);
      this.interp.exec("c = a or b");
      assertEquals(new PyBoolean(false), this.interp.get("c"));
   }

   static class WrappedBoolean extends PyBoolean {
      private boolean mutableValue;

      public WrappedBoolean() {
         super(true);
      }

      public boolean getBooleanValue() {
         return this.mutableValue;
      }

      public void setMutableValue(boolean newValue) {
         this.mutableValue = newValue;
      }
   }
}
