package org.python.compiler.custom_proxymaker;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.python.util.ProxyCompiler;

public class MethodSignatureTest {
   Class proxy;

   @Before
   public void setUp() throws Exception {
      ProxyCompiler.compile("tests/python/custom_proxymaker/method_signatures.py", "build/classes");
      this.proxy = Class.forName("custom_proxymaker.tests.MethodSignatures");
   }

   @Test
   public void methodThrows() throws Exception {
      Method method = this.proxy.getMethod("throwsException");
      Assert.assertArrayEquals(new Class[]{RuntimeException.class}, method.getExceptionTypes());
   }

   @Test
   public void returnsVoid() throws Exception {
      Method method = this.proxy.getMethod("throwsException");
      Assert.assertEquals(Void.TYPE, method.getReturnType());
   }

   @Test
   public void returnsLong() throws Exception {
      Method method = this.proxy.getMethod("returnsLong");
      Assert.assertEquals(Long.TYPE, method.getReturnType());
   }

   @Test
   public void returnsObject() throws Exception {
      Method method = this.proxy.getMethod("returnsObject");
      Assert.assertEquals(Object.class, method.getReturnType());
   }

   @Test
   public void returnsArray() throws Exception {
      Method method = this.proxy.getMethod("returnsArray");
      Object compareType = Array.newInstance(Long.TYPE, 0);
      Assert.assertEquals(compareType.getClass(), method.getReturnType());
   }

   @Test
   public void returnsArrayObj() throws Exception {
      Method method = this.proxy.getMethod("returnsArrayObj");
      Object compareType = Array.newInstance(Object.class, 0);
      Assert.assertEquals(compareType.getClass(), method.getReturnType());
   }

   @Test
   public void acceptsString() throws Exception {
      Class[] partypes = new Class[]{String.class};
      this.proxy.getMethod("acceptsString", partypes);
   }

   @Test
   public void acceptsArray() throws Exception {
      Object compareType = Array.newInstance(Long.TYPE, 0);
      Class[] partypes = new Class[]{compareType.getClass()};
      this.proxy.getMethod("acceptsArray", partypes);
   }
}
