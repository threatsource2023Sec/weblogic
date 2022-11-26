package monfox.toolkit.snmp.metadata.html;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpFramework;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

class a implements EntityResolver {
   private Hashtable a;
   private Logger b;

   public a() {
      this(a("~duiXf}d(M'pm*"));
   }

   public a(String var1) {
      this.a = new Hashtable();
      this.b = Logger.getInstance(a("Z]O\u0017"), a("Q^N"), a("Q~n\u0002[}zv>gl`m+Cla"));
      this.a.put(var1, var1);
   }

   public a(String[] var1) {
      boolean var3 = HTMLMIBOutputter.h;
      super();
      this.a = new Hashtable();
      this.b = Logger.getInstance(a("Z]O\u0017"), a("Q^N"), a("Q~n\u0002[}zv>gl`m+Cla"));
      if (var1 != null) {
         int var2 = 0;

         while(var2 < var1.length) {
            this.a.put(var1[var2].toLowerCase(), var1[var2]);
            ++var2;
            if (var3) {
               return;
            }

            if (var3) {
               break;
            }
         }

         if (!var3) {
            return;
         }
      }

      this.a.put(a("~duiXf}d(M'pm*"), a("~duiXf}d(M'pm*"));
   }

   public InputSource resolveEntity(String var1, String var2) throws SAXException, IOException {
      if (this.b.isDetailedEnabled()) {
         this.b.detailed(a("{vq(Y\u007fvp\u0002[}zv>\u000f)") + var2);
      }

      URL var3 = new URL(var2);
      String var4 = var3.getFile();

      String var5;
      try {
         label38: {
            var5 = var4;
            boolean var6 = true;
            int var10;
            if ((var10 = var4.lastIndexOf(47)) >= 0) {
               var5 = var4.substring(var10 + 1);
               if (!HTMLMIBOutputter.h) {
                  break label38;
               }
            }

            if ((var10 = var4.lastIndexOf(92)) >= 0) {
               var5 = var4.substring(var10 + 1);
            }
         }

         InputStream var7 = SnmpFramework.getFileLocator().getInputStream(var5);
         if (var7 != null) {
            this.b.debug(a("l}v.Ap3p\"Ff\u007ft\"Q)zlgFlrp$])cc3]33") + var5);
            return new InputSource(var7);
         }
      } catch (Exception var9) {
         this.b.debug(a("l}v.Ap3d.Yl3l(A)zlgaD]D5Tdvu(Gb3q\"T{pjgEhgj}\u0015") + var4);
      }

      var5 = var3.getHost();
      if (var5 != null && this.a.containsKey(var5.toLowerCase())) {
         String var11 = var3.getFile();
         URL var12 = this.getClass().getResource(var11);
         if (var12 == null) {
            this.b.error(a("krfgGl`m2Gjv\"\u0012gE3*)Z)uk+P )\"") + var3);
            return null;
         } else {
            InputStream var8 = var12.openStream();
            return new InputSource(var8);
         }
      } else {
         if (this.b.isDetailedEnabled()) {
            this.b.detailed(a("$>\"g[f3w5Y)~c3Va)\"") + var5);
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
               var10003 = 9;
               break;
            case 1:
               var10003 = 19;
               break;
            case 2:
               var10003 = 2;
               break;
            case 3:
               var10003 = 71;
               break;
            default:
               var10003 = 53;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
