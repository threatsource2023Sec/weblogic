package org.apache.taglibs.standard.lang.jstl.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import org.apache.taglibs.standard.lang.jstl.Evaluator;
import org.apache.taglibs.standard.lang.jstl.test.beans.Factory;

public class EvaluationTest {
   public static void runTests(DataInput pIn, PrintStream pOut) throws IOException {
      PageContext context = createTestContext();

      while(true) {
         while(true) {
            String str = pIn.readLine();
            if (str == null) {
               return;
            }

            if (!str.startsWith("#") && !"".equals(str.trim())) {
               String typeStr = pIn.readLine();
               pOut.println("Expression: " + str);

               try {
                  Class cl = parseClassName(typeStr);
                  pOut.println("ExpectedType: " + cl);
                  Evaluator e = new Evaluator();
                  Object val = e.evaluate("test", str, cl, (Tag)null, context);
                  pOut.println("Evaluates to: " + val);
                  if (val != null) {
                     pOut.println("With type: " + val.getClass().getName());
                  }

                  pOut.println();
               } catch (JspException var8) {
                  pOut.println("Causes an error: " + var8);
               } catch (ClassNotFoundException var9) {
                  pOut.println("Causes an error: " + var9);
               }
            } else {
               pOut.println(str);
            }
         }
      }
   }

   static Class parseClassName(String pClassName) throws ClassNotFoundException {
      String c = pClassName.trim();
      if ("boolean".equals(c)) {
         return Boolean.TYPE;
      } else if ("byte".equals(c)) {
         return Byte.TYPE;
      } else if ("char".equals(c)) {
         return Character.TYPE;
      } else if ("short".equals(c)) {
         return Short.TYPE;
      } else if ("int".equals(c)) {
         return Integer.TYPE;
      } else if ("long".equals(c)) {
         return Long.TYPE;
      } else if ("float".equals(c)) {
         return Float.TYPE;
      } else {
         return "double".equals(c) ? Double.TYPE : Class.forName(pClassName);
      }
   }

   public static void runTests(File pInputFile, File pOutputFile) throws IOException {
      FileInputStream fin = null;
      FileOutputStream fout = null;

      try {
         fin = new FileInputStream(pInputFile);
         BufferedInputStream bin = new BufferedInputStream(fin);
         DataInputStream din = new DataInputStream(bin);

         try {
            fout = new FileOutputStream(pOutputFile);
            BufferedOutputStream bout = new BufferedOutputStream(fout);
            PrintStream pout = new PrintStream(bout);
            runTests((DataInput)din, (PrintStream)pout);
            pout.flush();
         } finally {
            if (fout != null) {
               fout.close();
            }

         }
      } finally {
         if (fin != null) {
            fin.close();
         }

      }

   }

   public static boolean isDifferentFiles(DataInput pIn1, DataInput pIn2) throws IOException {
      while(true) {
         String str1 = pIn1.readLine();
         String str2 = pIn2.readLine();
         if (str1 == null && str2 == null) {
            return false;
         }

         if (str1 != null && str2 != null) {
            if (str1.equals(str2)) {
               continue;
            }

            return true;
         }

         return true;
      }
   }

   public static boolean isDifferentFiles(File pFile1, File pFile2) throws IOException {
      FileInputStream fin1 = null;

      boolean var8;
      try {
         fin1 = new FileInputStream(pFile1);
         BufferedInputStream bin1 = new BufferedInputStream(fin1);
         DataInputStream din1 = new DataInputStream(bin1);
         FileInputStream fin2 = null;

         try {
            fin2 = new FileInputStream(pFile2);
            BufferedInputStream bin2 = new BufferedInputStream(fin2);
            DataInputStream din2 = new DataInputStream(bin2);
            var8 = isDifferentFiles((DataInput)din1, (DataInput)din2);
         } finally {
            if (fin2 != null) {
               fin2.close();
            }

         }
      } finally {
         if (fin1 != null) {
            fin1.close();
         }

      }

      return var8;
   }

   static PageContext createTestContext() {
      PageContext ret = new PageContextImpl();
      ret.setAttribute("val1a", "page-scoped1", 1);
      ret.setAttribute("val1b", "request-scoped1", 2);
      ret.setAttribute("val1c", "session-scoped1", 3);
      ret.setAttribute("val1d", "app-scoped1", 4);
      Bean1 b1 = new Bean1();
      b1.setBoolean1(true);
      b1.setByte1((byte)12);
      b1.setShort1((short)-124);
      b1.setChar1('b');
      b1.setInt1(4);
      b1.setLong1(222423L);
      b1.setFloat1(12.4F);
      b1.setDouble1(89.224);
      b1.setString1("hello");
      b1.setStringArray1(new String[]{"string1", "string2", "string3", "string4"});
      List l = new ArrayList();
      l.add(new Integer(14));
      l.add("another value");
      l.add(b1.getStringArray1());
      b1.setList1(l);
      Map m2 = new HashMap();
      m2.put("key1", "value1");
      m2.put(new Integer(14), "value2");
      m2.put(new Long(14L), "value3");
      m2.put("recurse", b1);
      b1.setMap1(m2);
      ret.setAttribute("bean1a", b1);
      Bean1 b2 = new Bean1();
      b2.setInt2(new Integer(-224));
      b2.setString2("bean2's string");
      b1.setBean1(b2);
      Bean1 b3 = new Bean1();
      b3.setDouble1(1422.332);
      b3.setString2("bean3's string");
      b2.setBean2(b3);
      ret.setAttribute("pbean1", Factory.createBean1());
      ret.setAttribute("pbean2", Factory.createBean2());
      ret.setAttribute("pbean3", Factory.createBean3());
      ret.setAttribute("pbean4", Factory.createBean4());
      ret.setAttribute("pbean5", Factory.createBean5());
      ret.setAttribute("pbean6", Factory.createBean6());
      ret.setAttribute("pbean7", Factory.createBean7());
      Map m = new HashMap();
      m.put("emptyArray", new Object[0]);
      m.put("nonemptyArray", new Object[]{"abc"});
      m.put("emptyList", new ArrayList());
      l = new ArrayList();
      l.add("hello");
      m.put("nonemptyList", l);
      m.put("emptyMap", new HashMap());
      m2 = new HashMap();
      m2.put("a", "a");
      m.put("nonemptyMap", m2);
      m.put("emptySet", new HashSet());
      Set s = new HashSet();
      s.add("hello");
      m.put("nonemptySet", s);
      ret.setAttribute("emptyTests", m);
      return ret;
   }

   public static void main(String[] pArgs) throws IOException {
      if (pArgs.length != 2 && pArgs.length != 3) {
         usage();
         System.exit(1);
      }

      File in = new File(pArgs[0]);
      File out = new File(pArgs[1]);
      runTests(in, out);
      if (pArgs.length > 2) {
         File compare = new File(pArgs[2]);
         if (isDifferentFiles(out, compare)) {
            System.out.println("Test failure - output file " + out + " differs from expected output file " + compare);
         } else {
            System.out.println("tests passed");
         }
      }

   }

   static void usage() {
      System.err.println("usage: java org.apache.taglibs.standard.lang.jstl.test.EvaluationTest {input file} {output file} [{compare file}]");
   }
}
