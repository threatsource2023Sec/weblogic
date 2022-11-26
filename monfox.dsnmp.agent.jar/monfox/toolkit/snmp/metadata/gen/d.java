package monfox.toolkit.snmp.metadata.gen;

import java.util.Enumeration;
import java.util.Stack;
import java.util.Vector;
import monfox.java_cup.runtime.Symbol;
import monfox.java_cup.runtime.lr_parser;
import monfox.jdom.Element;

class d {
   a a = null;
   boolean b = false;
   Vector c = new Vector();

   String a() {
      return this.a.a();
   }

   protected void addBooleanAttribute(Element var1, String var2, Boolean var3) {
      this.addBooleanAttribute(var1, var2, var3);
   }

   protected void addBooleanAttribute(Element var1, String var2, boolean var3) {
      if (var1 != null) {
         if (var3) {
            var1.addAttribute(var2, c("d\u0018\u0014\\"));
            if (Message.d == 0) {
               return;
            }
         }

         var1.addAttribute(var2, c("v\u000b\rJ\u001b"));
      }

   }

   protected void addModRef(String var1) {
      if (!this.c.contains(var1)) {
         this.c.addElement(var1);
      }

   }

   protected void addModRefsToModule(Element var1) {
      if (this.c.size() > 0) {
         Element var2 = new Element(c("]\u0005\u0005L\u0012u8\u0004_\r"));
         var1.addContent(var2);
         Enumeration var3 = this.c.elements();

         while(var3.hasMoreElements()) {
            String var4 = (String)var3.nextElement();
            Element var5 = new Element(c("]\u0005\u0005L\u0012u8\u0004_"));
            var5.addAttribute(c("~\u000b\f\\"), var4);
            var2.addContent(var5);
            if (Message.d != 0) {
               break;
            }
         }
      }

   }

   protected void addParseInfo(int var1, int var2, Element var3) {
      String var4 = this.a.b.e();
      if (var3.getAttribute(c("`\u0003")) == null) {
         var3.addAttribute(c("`\u0003"), "" + var1 + "," + var2 + "," + var4);
      }

   }

   protected m getError(int var1, int var2) {
      return this.a.b.a(var1, var2);
   }

   protected void addIfNotNull(Element var1, Vector var2) {
      if (var2 != null) {
         Enumeration var3 = var2.elements();

         while(var3.hasMoreElements()) {
            var1.addContent((Element)var3.nextElement());
            if (Message.d != 0) {
               break;
            }
         }
      }

   }

   protected void addIfNotNull(Element var1, Element var2) {
      if (var2 != null) {
         var1.addContent(var2);
      }

   }

   public void show_error(String var1, Object var2) {
      this.a.a(var1, var2);
   }

   public void syntax_error(m var1) {
      this.a.syntax_error(var1);
   }

   public void syntax_error(m var1, String var2) {
      this.a.syntax_error(var1, var2);
   }

   public void show_warning(m var1) {
      this.a.show_warning(var1);
   }

   public void show_warning(m var1, String var2) {
      this.a.show_warning(var1, var2);
   }

   public void show_error(m var1, String var2) {
      this.a.syntax_error(var1, var2);
   }

   public void show_error(String var1) {
      this.a.a(var1, (Object)null);
   }

   void a(String var1) {
      if (this.a._done_parsing) {
         this.a.f.debug(var1);
      }

   }

   void b(String var1) {
      if (this.a.c) {
         this.a.f.debug(var1);
      }

   }

   boolean b() {
      return this.a.d;
   }

   public final Symbol CUP$lax_xsnmp_parser$do_action(int param1, lr_parser param2, Stack param3, int param4) throws Exception {
      // $FF: Couldn't be decompiled
   }

   private static String c(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 16;
               break;
            case 1:
               var10003 = 106;
               break;
            case 2:
               var10003 = 97;
               break;
            case 3:
               var10003 = 57;
               break;
            default:
               var10003 = 126;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
