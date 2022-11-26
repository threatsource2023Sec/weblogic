package monfox.jdom;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public final class Namespace {
   private static HashMap a = new HashMap();
   private static HashMap b = new HashMap();
   public static final Namespace NO_NAMESPACE = new Namespace("", "");
   public static final Namespace XML_NAMESPACE = new Namespace(a("\u0007#M"), a("\u0017:U\u001adPaV\u001d)Q9\u0012D1\r)\u000e2\u00133a\u0010SgGaO\u000b3\u001a=Q\u000b=\u001a"));
   private String c;
   private String d;

   public static Namespace getNamespace(String var0, String var1) {
      if (var0 == null || var0.trim().equals("")) {
         var0 = "";
      }

      if (var1 == null || var1.trim().equals("")) {
         var1 = "";
      }

      if (var0.equals(a("\u0007#M"))) {
         return XML_NAMESPACE;
      } else if (a.containsKey(var1)) {
         return (Namespace)a.get(var1);
      } else {
         String var2;
         if ((var2 = Verifier.checkNamespacePrefix(var0)) != null) {
            throw new IllegalNameException(var0, a("1/L\u000f-\u000f/B\u000f~\u000f<D\f7\u0007"), var2);
         } else if ((var2 = Verifier.checkNamespaceURI(var1)) != null) {
            throw new IllegalNameException(var1, a("1/L\u000f-\u000f/B\u000f~*\u001ch"), var2);
         } else if (!var0.equals("") && var1.equals("")) {
            throw new IllegalNameException("", a("\u0011/L\u000f-\u000f/B\u000f"), a("1/L\u000f-\u000f/B\u000f~*\u001ch\u0019~\u0012;R\u001e~\u001d+\u0001\u00041\u0011cO\u001f2\u0013n@\u0004:_ N\u0004s\u001a#Q\u001e'_\u001dU\u00187\u0011)RD"));
         } else if (a.containsKey(var1)) {
            return (Namespace)a.get(var0 + "&" + var1);
         } else {
            b.put(var0, var1);
            Namespace var3 = new Namespace(var0, var1);
            a.put(var0 + "&" + var1, var3);
            return var3;
         }
      }
   }

   public static Namespace getNamespace(String var0) {
      return getNamespace("", var0);
   }

   /** @deprecated */
   public static Namespace getNamespace(String var0, Element var1) {
      boolean var6 = Element.b;
      if (var1 == null) {
         return null;
      } else {
         Namespace var2 = var1.getNamespace();
         if (var2.getPrefix().equals(var0)) {
            return var2;
         } else {
            List var3 = var1.getAttributes();
            Iterator var4 = var3.iterator();

            String var10000;
            while(true) {
               if (var4.hasNext()) {
                  Attribute var5 = (Attribute)var4.next();
                  var2 = var5.getNamespace();
                  var10000 = var2.getPrefix();
                  if (var6) {
                     break;
                  }

                  if (var10000.equals(var0)) {
                     return var2;
                  }

                  if (!var6) {
                     continue;
                  }
               }

               var10000 = var0;
               break;
            }

            return getNamespace(var10000, var1.getParent());
         }
      }
   }

   private Namespace(String var1, String var2) {
      this.c = var1;
      this.d = var2;
   }

   public String getPrefix() {
      return this.c;
   }

   public String getURI() {
      return this.d;
   }

   public boolean equals(Object var1) {
      if (var1 == null) {
         return false;
      } else {
         if (var1 instanceof Namespace) {
            Namespace var2 = (Namespace)var1;
            if (var2.getURI().equals(this.d) && var2.getPrefix().equals(this.c)) {
               return true;
            }
         }

         return false;
      }
   }

   public String toString() {
      return a("$\u0000@\u0007;\f>@\t;EnQ\u0018;\u0019'YJ|") + this.c + a("]nH\u0019~\u0012/Q\u001a;\u001bnU\u0005~*\u001chJ|") + this.d + a("]\u0013");
   }

   public int hashCode() {
      return (this.c + this.d).hashCode();
   }

   static {
      a.put("", NO_NAMESPACE);
      b.put("", "");
      b.put(a("\u0007#M"), a("\u0017:U\u001adPaV\u001d)Q9\u0012D1\r)\u000e2\u00133a\u0010SgGaO\u000b3\u001a=Q\u000b=\u001a"));
      a.put(a("\u0007#ML6\u000b:QPqP9V\u001dp\b}\u000f\u0005,\u0018ay'\u0012P\u007f\u0018SfP @\u0007;\f>@\t;"), XML_NAMESPACE);
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 127;
               break;
            case 1:
               var10003 = 78;
               break;
            case 2:
               var10003 = 33;
               break;
            case 3:
               var10003 = 106;
               break;
            default:
               var10003 = 94;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
