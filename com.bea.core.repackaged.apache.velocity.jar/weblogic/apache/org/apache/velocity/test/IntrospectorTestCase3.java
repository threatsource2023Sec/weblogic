package weblogic.apache.org.apache.velocity.test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;
import weblogic.apache.org.apache.velocity.runtime.RuntimeSingleton;

public class IntrospectorTestCase3 extends BaseTestCase {
   // $FF: synthetic field
   static Class class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase3;
   // $FF: synthetic field
   static Class class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase3$MethodProvider;

   public IntrospectorTestCase3(String name) {
      super(name);
   }

   public static Test suite() {
      return new TestSuite(class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase3 == null ? (class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase3 = class$("weblogic.apache.org.apache.velocity.test.IntrospectorTestCase3")) : class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase3);
   }

   public void testSimple() throws Exception {
      MethodProvider mp = new MethodProvider();
      Object[] listIntInt = new Object[]{new ArrayList(), new Integer(1), new Integer(2)};
      Object[] listLongList = new Object[]{new ArrayList(), new Long(1L), new ArrayList()};
      Object[] var10000 = new Object[]{new ArrayList(), new Long(1L), new Integer(2)};
      Object[] intInt = new Object[]{new Integer(1), new Integer(2)};
      Object[] longInt = new Object[]{new Long(1L), new Integer(2)};
      Object[] longLong = new Object[]{new Long(1L), new Long(2L)};
      Method method = RuntimeSingleton.getIntrospector().getMethod(class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase3$MethodProvider == null ? (class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase3$MethodProvider = class$("weblogic.apache.org.apache.velocity.test.IntrospectorTestCase3$MethodProvider")) : class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase3$MethodProvider, "lii", listIntInt);
      String result = (String)method.invoke(mp, listIntInt);
      Assert.assertTrue(result.equals("lii"));
      method = RuntimeSingleton.getIntrospector().getMethod(class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase3$MethodProvider == null ? (class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase3$MethodProvider = class$("weblogic.apache.org.apache.velocity.test.IntrospectorTestCase3$MethodProvider")) : class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase3$MethodProvider, "ii", intInt);
      result = (String)method.invoke(mp, intInt);
      Assert.assertTrue(result.equals("ii"));
      method = RuntimeSingleton.getIntrospector().getMethod(class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase3$MethodProvider == null ? (class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase3$MethodProvider = class$("weblogic.apache.org.apache.velocity.test.IntrospectorTestCase3$MethodProvider")) : class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase3$MethodProvider, "ll", longInt);
      result = (String)method.invoke(mp, longInt);
      Assert.assertTrue(result.equals("ll"));
      method = RuntimeSingleton.getIntrospector().getMethod(class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase3$MethodProvider == null ? (class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase3$MethodProvider = class$("weblogic.apache.org.apache.velocity.test.IntrospectorTestCase3$MethodProvider")) : class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase3$MethodProvider, "ll", longLong);
      result = (String)method.invoke(mp, longLong);
      Assert.assertTrue(result.equals("ll"));
      method = RuntimeSingleton.getIntrospector().getMethod(class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase3$MethodProvider == null ? (class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase3$MethodProvider = class$("weblogic.apache.org.apache.velocity.test.IntrospectorTestCase3$MethodProvider")) : class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase3$MethodProvider, "lll", listLongList);
      result = (String)method.invoke(mp, listLongList);
      Assert.assertTrue(result.equals("lll"));
      Object[] oa = new Object[]{null, new Integer(0)};
      method = RuntimeSingleton.getIntrospector().getMethod(class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase3$MethodProvider == null ? (class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase3$MethodProvider = class$("weblogic.apache.org.apache.velocity.test.IntrospectorTestCase3$MethodProvider")) : class$weblogic$apache$org$apache$velocity$test$IntrospectorTestCase3$MethodProvider, "lll", oa);
      result = (String)method.invoke(mp, oa);
      Assert.assertTrue(result.equals("Listl"));
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
      public String ii(int p, int d) {
         return "ii";
      }

      public String lii(List s, int p, int d) {
         return "lii";
      }

      public String lll(List s, long p, List d) {
         return "lll";
      }

      public String lll(List s, long p, int d) {
         return "lli";
      }

      public String lll(List s, long p) {
         return "Listl";
      }

      public String ll(long p, long d) {
         return "ll";
      }
   }
}
