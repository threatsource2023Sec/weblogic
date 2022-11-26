package com.bea.core.repackaged.aspectj.runtime.internal;

import com.bea.core.repackaged.aspectj.lang.NoAspectBoundException;
import com.bea.core.repackaged.aspectj.runtime.CFlow;
import com.bea.core.repackaged.aspectj.runtime.internal.cflowstack.ThreadStack;
import com.bea.core.repackaged.aspectj.runtime.internal.cflowstack.ThreadStackFactory;
import com.bea.core.repackaged.aspectj.runtime.internal.cflowstack.ThreadStackFactoryImpl;
import com.bea.core.repackaged.aspectj.runtime.internal.cflowstack.ThreadStackFactoryImpl11;
import java.util.Stack;

public class CFlowStack {
   private static ThreadStackFactory tsFactory;
   private ThreadStack stackProxy;

   public CFlowStack() {
      this.stackProxy = tsFactory.getNewThreadStack();
   }

   private Stack getThreadStack() {
      return this.stackProxy.getThreadStack();
   }

   public void push(Object obj) {
      this.getThreadStack().push(obj);
   }

   public void pushInstance(Object obj) {
      this.getThreadStack().push(new CFlow(obj));
   }

   public void push(Object[] obj) {
      this.getThreadStack().push(new CFlowPlusState(obj));
   }

   public void pop() {
      Stack s = this.getThreadStack();
      s.pop();
      if (s.isEmpty()) {
         this.stackProxy.removeThreadStack();
      }

   }

   public Object peek() {
      Stack stack = this.getThreadStack();
      if (stack.isEmpty()) {
         throw new NoAspectBoundException();
      } else {
         return stack.peek();
      }
   }

   public Object get(int index) {
      CFlow cf = this.peekCFlow();
      return null == cf ? null : cf.get(index);
   }

   public Object peekInstance() {
      CFlow cf = this.peekCFlow();
      if (cf != null) {
         return cf.getAspect();
      } else {
         throw new NoAspectBoundException();
      }
   }

   public CFlow peekCFlow() {
      Stack stack = this.getThreadStack();
      return stack.isEmpty() ? null : (CFlow)stack.peek();
   }

   public CFlow peekTopCFlow() {
      Stack stack = this.getThreadStack();
      return stack.isEmpty() ? null : (CFlow)stack.elementAt(0);
   }

   public boolean isValid() {
      return !this.getThreadStack().isEmpty();
   }

   private static ThreadStackFactory getThreadLocalStackFactory() {
      return new ThreadStackFactoryImpl();
   }

   private static ThreadStackFactory getThreadLocalStackFactoryFor11() {
      return new ThreadStackFactoryImpl11();
   }

   private static void selectFactoryForVMVersion() {
      String override = getSystemPropertyWithoutSecurityException("aspectj.runtime.cflowstack.usethreadlocal", "unspecified");
      boolean useThreadLocalImplementation = false;
      if (override.equals("unspecified")) {
         String v = System.getProperty("java.class.version", "0.0");
         useThreadLocalImplementation = v.compareTo("46.0") >= 0;
      } else {
         useThreadLocalImplementation = override.equals("yes") || override.equals("true");
      }

      if (useThreadLocalImplementation) {
         tsFactory = getThreadLocalStackFactory();
      } else {
         tsFactory = getThreadLocalStackFactoryFor11();
      }

   }

   private static String getSystemPropertyWithoutSecurityException(String aPropertyName, String aDefaultValue) {
      try {
         return System.getProperty(aPropertyName, aDefaultValue);
      } catch (SecurityException var3) {
         return aDefaultValue;
      }
   }

   public static String getThreadStackFactoryClassName() {
      return tsFactory.getClass().getName();
   }

   static {
      selectFactoryForVMVersion();
   }
}
