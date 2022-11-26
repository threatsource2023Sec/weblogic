package org.apache.velocity.test.misc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Stack;
import java.util.Vector;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.FieldMethodizer;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.event.EventCartridge;
import org.apache.velocity.app.event.MethodExceptionEventHandler;
import org.apache.velocity.app.event.NullSetEventHandler;
import org.apache.velocity.app.event.ReferenceInsertionEventHandler;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.test.provider.TestProvider;

public class Test implements ReferenceInsertionEventHandler, NullSetEventHandler, MethodExceptionEventHandler {
   private static Stack writerStack = new Stack();

   public Test(String templateFile, String encoding) {
      Writer writer = null;
      TestProvider provider = new TestProvider();
      ArrayList al = provider.getCustomers();
      Hashtable h = new Hashtable();
      h.put("Bar", "this is from a hashtable!");
      h.put("Foo", "this is from a hashtable too!");
      Vector v = new Vector();
      String str = "mystr";
      v.addElement(new String("hello"));
      v.addElement(new String("hello2"));
      v.addElement(str);

      try {
         Properties p = new Properties();

         try {
            FileInputStream fis = new FileInputStream(new File("velocity.properties"));
            if (fis != null) {
               p.load(fis);
            }
         } catch (Exception var21) {
         }

         Enumeration e = p.propertyNames();

         while(e.hasMoreElements()) {
            String el = (String)e.nextElement();
            Velocity.setProperty(el, p.getProperty(el));
         }

         Velocity.setProperty("runtime.log.error.stacktrace", "true");
         Velocity.setProperty("runtime.log.warn.stacktrace", "true");
         Velocity.setProperty("runtime.log.info.stacktrace", "true");
         Velocity.init();
         if (templateFile == null) {
            templateFile = "examples/example.vm";
         }

         Template template = null;

         try {
            template = RuntimeSingleton.getTemplate(templateFile, encoding);
         } catch (ResourceNotFoundException var19) {
            System.out.println("Test : RNFE : Cannot find template " + templateFile);
         } catch (ParseErrorException var20) {
            System.out.println("Test : Syntax error in template " + templateFile + ":" + var20);
         }

         VelocityContext context = new VelocityContext();
         context.put("provider", provider);
         context.put("name", "jason");
         context.put("providers", provider.getCustomers2());
         context.put("list", al);
         context.put("hashtable", h);
         context.put("search", provider.getSearch());
         context.put("relatedSearches", provider.getRelSearches());
         context.put("searchResults", provider.getRelSearches());
         context.put("menu", provider.getMenu());
         context.put("stringarray", provider.getArray());
         context.put("vector", v);
         context.put("mystring", new String());
         context.put("hashmap", new HashMap());
         context.put("runtime", new FieldMethodizer("org.apache.velocity.runtime.RuntimeSingleton"));
         context.put("fmprov", new FieldMethodizer(provider));
         context.put("Floog", "floogie woogie");
         context.put("geirstring", str);
         context.put("mylong", new Long(5L));
         int[] intarr = new int[]{10, 20, 30, 40, 50};
         Object[] oarr = new Object[]{"a", "b", "c", "d"};
         context.put("collection", v);
         context.put("iterator", v.iterator());
         context.put("map", h);
         context.put("obarr", oarr);
         context.put("intarr", intarr);
         String stest = " My name is $name -> $Floog";
         new StringWriter();
         new StringWriter();
         new StringWriter();
         EventCartridge ec = new EventCartridge();
         ec.addEventHandler(this);
         ec.attachToContext(context);
         VelocityContext vc = new VelocityContext(context);
         if (template != null) {
            writer = new BufferedWriter(new OutputStreamWriter(System.out, encoding));
            template.merge(vc, writer);
            writer.flush();
            writer.close();
         }
      } catch (MethodInvocationException var22) {
         System.out.println("MIE : " + var22);
      } catch (Exception var23) {
         RuntimeSingleton.error("Test- exception : " + var23);
         var23.printStackTrace();
      }

   }

   public Object referenceInsert(String reference, Object value) {
      if (value != null) {
      }

      return value;
   }

   public boolean shouldLogOnNullSet(String lhs, String rhs) {
      return !lhs.equals("$woogie");
   }

   public Object methodException(Class claz, String method, Exception e) throws Exception {
      if (method.equals("getThrow")) {
         return "I should have thrown";
      } else {
         throw e;
      }
   }

   public static void main(String[] args) {
      String encoding = "ISO-8859-1";
      if (args.length > 1) {
         encoding = args[1];
      }

      new Test(args[0], encoding);
   }
}
