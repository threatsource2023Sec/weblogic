package monfox.toolkit.snmp.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;
import monfox.log.Logger;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlEntityResolver implements EntityResolver {
   private Hashtable a;
   private Logger b;

   public XmlEntityResolver() {
      this(a("7^\u0005>5/G\u0014\u007f nJ\u001d}"));
   }

   public XmlEntityResolver(String var1) {
      this.a = new Hashtable();
      this.b = Logger.getInstance(a("\u0004z<]\b"), a("\u0015};\\"), a("\u0018D\u001eU64@\u0006i\n%Z\u001d|.%["));
      this.a.put(var1, var1);
   }

   public XmlEntityResolver(String[] var1) {
      int var3 = WorkItem.d;
      super();
      this.a = new Hashtable();
      this.b = Logger.getInstance(a("\u0004z<]\b"), a("\u0015};\\"), a("\u0018D\u001eU64@\u0006i\n%Z\u001d|.%["));
      if (var1 != null) {
         int var2 = 0;

         while(var2 < var1.length) {
            this.a.put(var1[var2].toLowerCase(), var1[var2]);
            ++var2;
            if (var3 != 0) {
               return;
            }

            if (var3 != 0) {
               break;
            }
         }

         if (var3 == 0) {
            return;
         }
      }

      this.a.put(a("7^\u0005>5/G\u0014\u007f nJ\u001d}"), a("7^\u0005>5/G\u0014\u007f nJ\u001d}"));
   }

   public InputSource resolveEntity(String var1, String var2) throws SAXException, IOException {
      URL var3 = new URL(var2);
      String var4 = var3.getFile();

      String var5;
      try {
         label36: {
            var5 = var4;
            boolean var6 = true;
            int var10;
            if ((var10 = var4.lastIndexOf(47)) >= 0) {
               var5 = var4.substring(var10 + 1);
               if (WorkItem.d == 0) {
                  break label36;
               }
            }

            if ((var10 = var4.lastIndexOf(92)) >= 0) {
               var5 = var4.substring(var10 + 1);
            }
         }

         InputStream var7 = XmlUtil.GetFileLocator().getInputStream(var5);
         if (var7 != null) {
            this.b.debug(a("%G\u0006y,9\t\u0000u+/E\u0004u<`@\u001c0+%H\u0000s0`Y\u0013d0z\t") + var5);
            return new InputSource(var7);
         }
      } catch (Exception var9) {
         this.b.debug(a("%G\u0006y,9\t\u0014y4%\t\u001c\u007f,`@\u001c0\f\rg4b9-L\u0005\u007f*+\t\u0001u92J\u001a0(!]\u001a*x") + var4);
      }

      var5 = var3.getHost();
      if (var5 != null && this.a.containsKey(var5.toLowerCase())) {
         String var11 = var3.getFile();
         URL var12 = this.getClass().getResource(var11);
         if (var12 == null) {
            this.b.error(a("\"H\u00160*%Z\u001de*#LRE\n\f\tZ~7`O\u001b|=i\u0013R") + var3);
            return null;
         } else {
            InputStream var8 = var12.openStream();
            return new InputSource(var8);
         }
      } else {
         if (this.b.isDetailedEnabled()) {
            this.b.detailed(a("m\u0004R06/\t\u0007b4`D\u0013d;(\u0013R") + var5);
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
               var10003 = 64;
               break;
            case 1:
               var10003 = 41;
               break;
            case 2:
               var10003 = 114;
               break;
            case 3:
               var10003 = 16;
               break;
            default:
               var10003 = 88;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
