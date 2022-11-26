package org.apache.taglibs.standard.lang.jstl.test;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import org.apache.taglibs.standard.lang.jstl.Evaluator;

public class StaticFunctionTests {
   public static void main(String[] args) throws Exception {
      Map m = getSampleMethodMap();
      Evaluator e = new Evaluator();
      Object o = e.evaluate("", "4", Integer.class, (Tag)null, (PageContext)null, m, "foo");
      System.out.println(o);
      o = e.evaluate("", "${4}", Integer.class, (Tag)null, (PageContext)null, m, "foo");
      System.out.println(o);
      o = e.evaluate("", "${2+2}", Integer.class, (Tag)null, (PageContext)null, m, "foo");
      System.out.println(o);
      o = e.evaluate("", "${foo:add(2, 3)}", Integer.class, (Tag)null, (PageContext)null, m, "foo");
      System.out.println(o);
      o = e.evaluate("", "${foo:multiply(2, 3)}", Integer.class, (Tag)null, (PageContext)null, m, "foo");
      System.out.println(o);
      o = e.evaluate("", "${add(2, 3)}", Integer.class, (Tag)null, (PageContext)null, m, "foo");
      System.out.println(o);
      o = e.evaluate("", "${multiply(2, 3)}", Integer.class, (Tag)null, (PageContext)null, m, "foo");
      System.out.println(o);
      o = e.evaluate("", "${add(2, 3) + 5}", Integer.class, (Tag)null, (PageContext)null, m, "foo");
      System.out.println(o);
      System.out.println("---");
      o = e.evaluate("", "${getInt(getInteger(getInt(5)))}", Integer.class, (Tag)null, (PageContext)null, m, "foo");
      System.out.println(o);
      o = e.evaluate("", "${getInteger(getInt(getInteger(5)))}", Integer.class, (Tag)null, (PageContext)null, m, "foo");
      System.out.println(o);
      o = e.evaluate("", "${getInt(getInt(getInt(5)))}", Integer.class, (Tag)null, (PageContext)null, m, "foo");
      System.out.println(o);
      o = e.evaluate("", "${getInteger(getInteger(getInteger(5)))}", Integer.class, (Tag)null, (PageContext)null, m, "foo");
      System.out.println(o);
   }

   public static int add(int a, int b) {
      return a + b;
   }

   public static int multiply(int a, int b) {
      return a * b;
   }

   public static int getInt(Integer i) {
      return i;
   }

   public static Integer getInteger(int i) {
      return i;
   }

   public static Map getSampleMethodMap() throws Exception {
      Map m = new HashMap();
      Class c = StaticFunctionTests.class;
      m.put("foo:add", c.getMethod("add", Integer.TYPE, Integer.TYPE));
      m.put("foo:multiply", c.getMethod("multiply", Integer.TYPE, Integer.TYPE));
      m.put("foo:getInt", c.getMethod("getInt", Integer.class));
      m.put("foo:getInteger", c.getMethod("getInteger", Integer.TYPE));
      return m;
   }
}
