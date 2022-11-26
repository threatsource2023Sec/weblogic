package org.python.core;

import java.math.BigInteger;
import junit.framework.TestCase;
import org.python.util.PythonInterpreter;

public class WrappedLongTest extends TestCase {
   private PythonInterpreter interp;
   private WrappedLong a;
   private WrappedLong b;

   protected void setUp() throws Exception {
      this.interp = new PythonInterpreter(new PyStringMap(), new PySystemState());
      this.a = new WrappedLong();
      this.b = new WrappedLong();
      this.a.setMutableValue(13000000000L);
      this.b.setMutableValue(17000000000L);
      this.interp.set("a", (PyObject)this.a);
      this.interp.set("b", (PyObject)this.b);
   }

   public void testAdd() {
      this.interp.exec("c = a + b");
      assertEquals(new PyLong(30000000000L), this.interp.get("c"));
      this.b.setMutableValue(18000000000L);
      this.interp.exec("c = a + b");
      assertEquals(new PyLong(31000000000L), this.interp.get("c"));
   }

   public void testMod() {
      this.interp.exec("c = b % a");
      assertEquals(new PyLong(4000000000L), this.interp.get("c"));
   }

   static class WrappedLong extends PyLong {
      private long mutableValue;

      public WrappedLong() {
         super(0L);
      }

      public BigInteger getValue() {
         return BigInteger.valueOf(this.mutableValue);
      }

      public void setMutableValue(long newValue) {
         this.mutableValue = newValue;
      }
   }
}
