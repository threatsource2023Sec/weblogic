package monfox.log.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;
import monfox.log.Logger;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

class a implements EntityResolver {
   private Hashtable a;
   private static Logger b = Logger.getInstance(a("\u0000\t y"), a("\u001c\u0010!"), a("\u001c0\u0001r/04\u0019N\u0013!.\u0002[7!/"));

   public a() {
      this(a("3*\u001a\u0019,+3\u000bX9j>\u0002Z"));
   }

   public a(String var1) {
      this.a = new Hashtable();
      this.a.put(var1, var1);
   }

   public a(String[] var1) {
      this.a = new Hashtable();
      int var2 = 0;

      while(var2 < var1.length) {
         this.a.put(var1[var2].toLowerCase(), var1[var2]);
         ++var2;
         if (XmlLoggerConfigurator.a) {
            break;
         }
      }

   }

   public InputSource resolveEntity(String var1, String var2) throws SAXException, IOException {
      if (b.isDetailedEnabled()) {
         b.detailed(a("68\u001eX-28\u001fr/04\u0019N{d") + var2);
      }

      URL var3 = new URL(var2);
      String var4 = var3.getHost();
      if (var4 != null && this.a.containsKey(var4.toLowerCase())) {
         String var5 = var3.getFile();
         URL var6 = this.getClass().getResource(var5);
         if (var6 == null) {
            b.error(a("&<\t\u00173!.\u0002B3'8Mb\u0013\b}EY.d;\u0004[$mgM") + var3);
            return null;
         } else {
            InputStream var7 = var6.openStream();
            return new InputSource(var7);
         }
      } else {
         if (b.isDetailedEnabled()) {
            b.detailed(a("ipM\u0017/+}\u0018E-d0\fC\",gM") + var4);
         }

         return null;
      }
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 68;
               break;
            case 1:
               var10003 = 93;
               break;
            case 2:
               var10003 = 109;
               break;
            case 3:
               var10003 = 55;
               break;
            default:
               var10003 = 65;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
