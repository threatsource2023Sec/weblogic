package org.apache.velocity.test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import junit.framework.Assert;
import junit.framework.Test;
import org.apache.velocity.runtime.RuntimeSingleton;

public class IntrospectorTestCase extends BaseTestCase {
   private Method method;
   private String result;
   private String type;
   private ArrayList failures = new ArrayList();
   // $FF: synthetic field
   static Class class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider;

   IntrospectorTestCase() {
      super("IntrospectorTestCase");
   }

   public IntrospectorTestCase(String name) {
      super(name);
   }

   public static Test suite() {
      return new IntrospectorTestCase();
   }

   public void runTest() {
      MethodProvider mp = new MethodProvider();

      try {
         Object[] booleanParams = new Object[]{new Boolean(true)};
         this.type = "boolean";
         this.method = RuntimeSingleton.getIntrospector().getMethod(class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider == null ? (class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider = class$("org.apache.velocity.test.IntrospectorTestCase$MethodProvider")) : class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider, this.type + "Method", booleanParams);
         this.result = (String)this.method.invoke(mp, booleanParams);
         if (!this.result.equals(this.type)) {
            this.failures.add(this.type + "Method could not be found!");
         }

         Object[] byteParams = new Object[]{new Byte("1")};
         this.type = "byte";
         this.method = RuntimeSingleton.getIntrospector().getMethod(class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider == null ? (class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider = class$("org.apache.velocity.test.IntrospectorTestCase$MethodProvider")) : class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider, this.type + "Method", byteParams);
         this.result = (String)this.method.invoke(mp, byteParams);
         if (!this.result.equals(this.type)) {
            this.failures.add(this.type + "Method could not be found!");
         }

         Object[] characterParams = new Object[]{new Character('a')};
         this.type = "character";
         this.method = RuntimeSingleton.getIntrospector().getMethod(class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider == null ? (class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider = class$("org.apache.velocity.test.IntrospectorTestCase$MethodProvider")) : class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider, this.type + "Method", characterParams);
         this.result = (String)this.method.invoke(mp, characterParams);
         if (!this.result.equals(this.type)) {
            this.failures.add(this.type + "Method could not be found!");
         }

         Object[] doubleParams = new Object[]{new Double(1.0)};
         this.type = "double";
         this.method = RuntimeSingleton.getIntrospector().getMethod(class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider == null ? (class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider = class$("org.apache.velocity.test.IntrospectorTestCase$MethodProvider")) : class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider, this.type + "Method", doubleParams);
         this.result = (String)this.method.invoke(mp, doubleParams);
         if (!this.result.equals(this.type)) {
            this.failures.add(this.type + "Method could not be found!");
         }

         Object[] floatParams = new Object[]{new Float(1.0F)};
         this.type = "float";
         this.method = RuntimeSingleton.getIntrospector().getMethod(class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider == null ? (class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider = class$("org.apache.velocity.test.IntrospectorTestCase$MethodProvider")) : class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider, this.type + "Method", floatParams);
         this.result = (String)this.method.invoke(mp, floatParams);
         if (!this.result.equals(this.type)) {
            this.failures.add(this.type + "Method could not be found!");
         }

         Object[] integerParams = new Object[]{new Integer(1)};
         this.type = "integer";
         this.method = RuntimeSingleton.getIntrospector().getMethod(class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider == null ? (class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider = class$("org.apache.velocity.test.IntrospectorTestCase$MethodProvider")) : class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider, this.type + "Method", integerParams);
         this.result = (String)this.method.invoke(mp, integerParams);
         if (!this.result.equals(this.type)) {
            this.failures.add(this.type + "Method could not be found!");
         }

         Object[] longParams = new Object[]{new Long(1L)};
         this.type = "long";
         this.method = RuntimeSingleton.getIntrospector().getMethod(class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider == null ? (class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider = class$("org.apache.velocity.test.IntrospectorTestCase$MethodProvider")) : class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider, this.type + "Method", longParams);
         this.result = (String)this.method.invoke(mp, longParams);
         if (!this.result.equals(this.type)) {
            this.failures.add(this.type + "Method could not be found!");
         }

         Object[] shortParams = new Object[]{new Short((short)1)};
         this.type = "short";
         this.method = RuntimeSingleton.getIntrospector().getMethod(class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider == null ? (class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider = class$("org.apache.velocity.test.IntrospectorTestCase$MethodProvider")) : class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider, this.type + "Method", shortParams);
         this.result = (String)this.method.invoke(mp, shortParams);
         if (!this.result.equals(this.type)) {
            this.failures.add(this.type + "Method could not be found!");
         }

         Object[] params = new Object[0];
         this.method = RuntimeSingleton.getIntrospector().getMethod(class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider == null ? (class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider = class$("org.apache.velocity.test.IntrospectorTestCase$MethodProvider")) : class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider, "untouchable", params);
         if (this.method != null) {
            this.failures.add(this.type + "able to access a private-access method.");
         }

         this.method = RuntimeSingleton.getIntrospector().getMethod(class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider == null ? (class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider = class$("org.apache.velocity.test.IntrospectorTestCase$MethodProvider")) : class$org$apache$velocity$test$IntrospectorTestCase$MethodProvider, "reallyuntouchable", params);
         if (this.method != null) {
            this.failures.add(this.type + "able to access a default-access method.");
         }

         int totalFailures = this.failures.size();
         if (totalFailures > 0) {
            StringBuffer sb = new StringBuffer("\nIntrospection Errors:\n");

            for(int i = 0; i < totalFailures; ++i) {
               sb.append((String)this.failures.get(i)).append("\n");
            }

            Assert.fail(sb.toString());
         }
      } catch (Exception var14) {
         Assert.fail(var14.toString());
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

   public static class MethodProvider {
      public String booleanMethod(boolean p) {
         return "boolean";
      }

      public String byteMethod(byte p) {
         return "byte";
      }

      public String characterMethod(char p) {
         return "character";
      }

      public String doubleMethod(double p) {
         return "double";
      }

      public String floatMethod(float p) {
         return "float";
      }

      public String integerMethod(int p) {
         return "integer";
      }

      public String longMethod(long p) {
         return "long";
      }

      public String shortMethod(short p) {
         return "short";
      }

      String untouchable() {
         return "yech";
      }

      private String reallyuntouchable() {
         return "yech!";
      }
   }
}
