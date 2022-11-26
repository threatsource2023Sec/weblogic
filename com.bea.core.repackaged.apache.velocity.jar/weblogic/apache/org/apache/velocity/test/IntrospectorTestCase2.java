package weblogic.apache.org.apache.velocity.test;

import java.lang.reflect.Method;
import junit.framework.Assert;
import junit.framework.Test;
import weblogic.apache.org.apache.velocity.app.Velocity;
import weblogic.apache.org.apache.velocity.runtime.RuntimeSingleton;

public class IntrospectorTestCase2 extends BaseTestCase {
   // $FF: synthetic field
   static Class class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase2$Tester;
   // $FF: synthetic field
   static Class class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase2$Tester2;

   IntrospectorTestCase2() {
      super("IntrospectorTestCase2");
   }

   public IntrospectorTestCase2(String name) {
      super(name);
   }

   public static Test suite() {
      return new IntrospectorTestCase2();
   }

   public void runTest() {
      try {
         Velocity.init();
         Tester t = new Tester();
         Object[] params = new Object[]{new Foo(), new Foo()};
         Method method = RuntimeSingleton.getIntrospector().getMethod(class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase2$Tester == null ? (class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase2$Tester = class$("weblogic.apache.org.apache.velocity.test.IntrospectorTestCase2$Tester")) : class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase2$Tester, "find", params);
         if (method == null) {
            Assert.fail("Returned method was null");
         }

         String result = (String)method.invoke(t, params);
         if (!result.equals("Bar-Bar")) {
            Assert.fail("Should have gotten 'Bar-Bar' : recieved '" + result + "'");
         }

         method = RuntimeSingleton.getIntrospector().getMethod(class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase2$Tester2 == null ? (class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase2$Tester2 = class$("weblogic.apache.org.apache.velocity.test.IntrospectorTestCase2$Tester2")) : class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase2$Tester2, "find", params);
         if (method != null) {
            Assert.fail("Introspector shouldn't have found a method as it's ambiguous.");
         }
      } catch (Exception var5) {
         Assert.fail(var5.toString());
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

   public static class Tester2 {
      public static String find(Woogie w, Object o) {
         return "Woogie-Object";
      }

      public static String find(Object w, Bar o) {
         return "Object-Bar";
      }

      public static String find(Bar w, Object o) {
         return "Bar-Object";
      }

      public static String find(Object o) {
         return "Object";
      }

      public static String find(Woogie o) {
         return "Woogie";
      }
   }

   public static class Tester {
      public static String find(Woogie w, Object o) {
         return "Woogie-Object";
      }

      public static String find(Object w, Bar o) {
         return "Object-Bar";
      }

      public static String find(Bar w, Bar o) {
         return "Bar-Bar";
      }

      public static String find(Object o) {
         return "Object";
      }

      public static String find(Woogie o) {
         return "Woogie";
      }
   }

   public static class Foo extends Bar {
      int j;
   }

   public static class Bar implements Woogie {
      int i;
   }

   public interface Woogie {
   }
}
