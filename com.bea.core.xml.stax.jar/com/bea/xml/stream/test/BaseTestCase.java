package com.bea.xml.stream.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import org.apache.log4j.Logger;

public class BaseTestCase extends TestCase {
   protected Logger logger = Logger.getLogger("JSR173");
   protected GlobalCounter globalCounter;
   // $FF: synthetic field
   static Class class$com$bea$xml$stream$test$SimpleReaderTest;

   public BaseTestCase() {
      this.globalCounter = GlobalCounter.getInstance();
   }

   public BaseTestCase(String fName) {
      super(fName);
   }

   public static void main(String[] args) throws Exception {
      TestRunner.run(suite());
   }

   protected void setUp() {
   }

   public static Test suite() {
      return new TestSuite(class$com$bea$xml$stream$test$SimpleReaderTest == null ? (class$com$bea$xml$stream$test$SimpleReaderTest = class$("com.bea.xml.stream.test.SimpleReaderTest")) : class$com$bea$xml$stream$test$SimpleReaderTest);
   }

   protected void runTest() throws Throwable {
      Method[] runMethods = null;

      try {
         runMethods = this.getClass().getDeclaredMethods();
      } catch (Exception var7) {
         Assert.fail("No Methods declared in this test suite");
      }

      for(int i = 0; i < runMethods.length; ++i) {
         if (runMethods[i].getName().startsWith("test")) {
            try {
               runMethods[i].invoke(this);
            } catch (InvocationTargetException var5) {
               var5.fillInStackTrace();
               throw var5.getTargetException();
            } catch (IllegalAccessException var6) {
               var6.fillInStackTrace();
               throw var6;
            }
         }
      }

   }

   // $FF: synthetic method
   static Class class$(String x0) {
      try {
         return Class.forName(x0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }
}
