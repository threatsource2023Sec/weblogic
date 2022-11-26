package monfox.toolkit.snmp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;
import monfox.log.Logger;

public class FileLocator {
   private List a = null;
   private String b = null;
   private NameUtil c = new NameUtil();
   private List d = new Vector();
   private Logger e = Logger.getInstance(d("(px+w"), d("9w\u007f*"), d("*JZ\u0003k\u0003@W\u0012H\u001e"));
   // $FF: synthetic field
   static Class f;

   public FileLocator() {
   }

   public FileLocator(String var1) {
      this.setSearchPath(var1);
   }

   public void setVariable(String var1, String var2) {
      this.c.setVariable(var1, var2);
   }

   public File getFile(String var1) throws IOException {
      return this.getFile(var1, true);
   }

   public File getFile(String var1, boolean var2) throws IOException {
      this.e = Logger.getInstance(d("\"nw6w?\u000ep1"), d("9w\u007f*"), d("*JZ\u0003k\u0003@W\u0012H\u001e"));
      if (var1 == null) {
         throw new IOException(d("\u0002VZ\n\u0007\nJZ\u0003I\rNS"));
      } else {
         if (this.e.isDetailedEnabled()) {
            this.e.detailed(d("\u000bFB N\u0000F\fFA\u0005OS[") + var1 + d("@\u0003[\u0013T\u0018\u000eS\u001eN\u001fW\u000b") + var2);
         }

         var1 = this.c.resolveVars(var1);
         if (this.e.isDetailedEnabled()) {
            this.e.detailed(d("L\u0003\u001bK\u0007\u001aBD\u0015\u0007\u001eFE\tK\u001aFR\\\u0007") + var1);
         }

         if (!var2) {
            return new File(var1);
         } else {
            File var3 = null;
            if (this.a != null && this.a.size() != 0) {
               if (var1.startsWith("/")) {
                  this.e.detailed(d("A\u000e\u0016\u0007E\u001fLZ\u0013S\t\u0003P\u000fK\t\u0003F\u0007S\u0004"));
                  var3 = new File(var1);
               } else if (var1.startsWith("\\")) {
                  this.e.detailed(d("A\u000e\u0016\u0007E\u001fLZ\u0013S\t\u0003r)tLE_\nBLSW\u0012O"));
                  var3 = new File(var1);
               } else if (var1.length() > 2 && var1.charAt(1) == ':') {
                  this.e.detailed(d("A\u000e\u0016\u0007E\u001fLZ\u0013S\t\u0003r)tLGD\u000fQ\t\u0003P\u000fK\t\u0003F\u0007S\u0004"));
                  var3 = new File(var1);
               } else if (var1.startsWith(d("\nJZ\u0003\u001d"))) {
                  URL var4 = new URL(var1);
                  var3 = new File(var4.getFile());
               } else if (var1.startsWith(d("\u0004WB\u0016\u001d")) || var1.startsWith(d("\nWF\\")) || var1.startsWith(d("\u0006BD\\"))) {
                  this.e.detailed(d("A\u000e\u0016\u0007E\u001fLZ\u0013S\t\u0003C\u0014KLSW\u0012OV\u0003X\tSLUW\nN\b"));
                  throw new IOException(d("\u0000LU\u0007S\u0005LXFJ\u0019PBFE\t\u0003WFA\u0005OS\u0015^\u001fWS\u000b\u0007\u001cBB\u000e\u0007\u0002LBFFLvd*\u001dL") + var1);
               }
            } else {
               this.e.detailed(d("A\u000e\u0016\bHLPS\u0007U\u000fK\u0016\u0016F\u0018K"));
               var3 = new File(var1);
            }

            if (var3 != null) {
               if (var3.exists()) {
                  return var3;
               } else {
                  this.e.error(d("\u0002L\u0016\u0015R\u000fK\u0016\u0000N\u0000F\fF") + var1);
                  throw new IOException(d("\u0002L\u0016\u0015R\u000fK\u0016\u0000N\u0000F\fF") + var1);
               }
            } else {
               this.e.detailed(d("A\u000e\u0016\u0014B\u0000BB\u000fQ\t\u0003F\u0007S\u0004\u000f\u0016\u0005O\t@]\u000fI\u000b\u0003E\u0003F\u001e@^FW\rW^"));
               ListIterator var9 = this.a.listIterator();

               while(var9.hasNext()) {
                  String var5 = (String)var9.next();
                  String var6 = var5 + var1;
                  if (this.e.isDetailedEnabled()) {
                     this.e.detailed(d("L\u0003\u0016\u0016F\u0018K\u001b\u0003K\tN\fF") + var5);
                  }

                  try {
                     var3 = new File(var6);
                     if (var3.exists()) {
                        return var3;
                     }
                  } catch (Exception var8) {
                  }

                  if (WorkItem.d != 0) {
                     break;
                  }
               }

               throw new IOException(d("\u0002L\u0016\u0015R\u000fK\u0016\u0000N\u0000F\u0016A\u0007") + var1 + d("K\u0003_\b\u0007\u001fFW\u0014D\u0004\u0003F\u0007S\u0004\u0003\u0011") + this.b + "'");
            }
         }
      }
   }

   public File getDirectory(String var1) throws IOException {
      return this.getDirectory(var1, true);
   }

   public File getDirectory(String var1, boolean var2) throws IOException {
      this.e = Logger.getInstance(d("\"nw6w?\u000ep1"), d("9w\u007f*"), d("*JZ\u0003k\u0003@W\u0012H\u001e"));
      if (this.e.isDetailedEnabled()) {
         this.e.detailed(d("\u000bFB\"N\u001eFU\u0012H\u001eZ\fFC\u0005QX\u0007J\t\u001e") + var1);
      }

      if (var1 == null) {
         throw new IOException(d("\u0002VZ\n\u0007\bJD\bF\u0001F"));
      } else {
         File var3 = this.getFile(var1, var2);
         if (!var2) {
            return var3;
         } else if (var3.isDirectory()) {
            return var3;
         } else {
            throw new IOException(d("\u0002LBFFLG_\u0014B\u000fWY\u0014^L\u0004\u0016") + var1 + "'");
         }
      }
   }

   public void setSearchPath(String var1) {
      var1 = this.c.resolveVars(var1);
      this.a = this.b(var1);
      this.b = var1;
      this.e.debug(d("\u001fFB5B\rQU\u000ew\rW^\\\u0007K") + var1 + "'");
   }

   public void addSearchPath(String var1) {
      label11: {
         var1 = this.c.resolveVars(var1);
         List var2 = this.b(var1);
         if (this.a != null) {
            this.a.addAll(var2);
            if (WorkItem.d == 0) {
               break label11;
            }
         }

         this.a = var2;
         this.a.add("." + File.separator);
      }

      this.b = this.b + File.pathSeparator + var1;
      this.e.debug(d("\rGR5B\rQU\u000ew\rW^\\\u0007\rGR\u000fI\u000b\u001e\u0011") + var1 + "'");
      this.e.debug(d("\rGR5B\rQU\u000ew\rW^\\\u0007\u001eFE\u0013K\u0018\u001e\u0011") + this.b + "'");
   }

   public List getSearchPathList() {
      return this.a;
   }

   private InputStream a(String var1) throws IOException {
      this.e = Logger.getInstance(d("\"nw6w?\u000ep1"), d("9w\u007f*"), d("*JZ\u0003k\u0003@W\u0012H\u001e"));
      if (this.e.isDetailedEnabled()) {
         this.e.detailed(d("\u0000LU\u0007K+FB/I\u001cVB5S\u001eFW\u000b\u001dLE_\nBQ") + var1);
      }

      Object var2 = null;
      if (var2 == null) {
         try {
            URL var3 = new URL(var1);
            var2 = var3.openStream();
         } catch (IOException var5) {
         }
      }

      if (var2 == null) {
         try {
            var2 = new FileInputStream(var1);
         } catch (IOException var4) {
         }
      }

      if (var2 == null) {
         var2 = (f == null ? (f = c(d("\u0001LX\u0000H\u0014\rB\tH\u0000H_\u0012\t\u001fM[\u0016\t\u0019W_\n\t*JZ\u0003k\u0003@W\u0012H\u001e"))) : f).getResourceAsStream(var1);
      }

      if (var2 == null) {
         throw new IOException(d("\u0005M@\u0007K\u0005G\u0016\nH\u000fBB\u000fH\u0002\u0019\u0016") + var1);
      } else {
         return (InputStream)var2;
      }
   }

   public InputStream getInputStream(String var1) throws IOException {
      int var6 = WorkItem.d;
      this.e = Logger.getInstance(d("\"nw6w?\u000ep1"), d("9w\u007f*"), d("*JZ\u0003k\u0003@W\u0012H\u001e"));
      if (var1 == null) {
         throw new IOException(d("\u0002VZ\n\u0007\nJZ\u0003I\rNS"));
      } else {
         if (this.e.isDetailedEnabled()) {
            this.e.detailed(d("\u000bFB/I\u001cVB5S\u001eFW\u000b\u001dLE_\nBQ") + var1);
         }

         var1 = this.c.resolveVars(var1);
         InputStream var10000;
         if (this.d.size() > 0) {
            Iterator var2 = this.d.iterator();

            while(var2.hasNext()) {
               Resolver var3 = (Resolver)var2.next();

               try {
                  InputStream var4 = var3.getInputStream(var1);
                  var10000 = var4;
                  if (var6 != 0) {
                     return var10000;
                  }

                  if (var4 != null) {
                     return var4;
                  }
               } catch (Exception var8) {
                  this.e.error(d("\t[U\u0003W\u0018JY\b\u0007\u0005M\u0016 N\u0000Fz\tD\rWY\u0014\t>FE\tK\u001aFDH@\tW\u007f\bW\u0019We\u0012U\tB[N") + var1 + ")", var8);
               }

               if (var6 != 0) {
                  break;
               }
            }
         }

         if (this.e.isDetailedEnabled()) {
            this.e.detailed(d("L\u0003\u001bK\u0007\u001aBD\u0015\u0007\u001eFE\tK\u001aFR\\\u0007") + var1);
         }

         if (this.a != null && this.a.size() != 0) {
            if (var1.startsWith("/")) {
               this.e.detailed(d("A\u000e\u0016\u0007E\u001fLZ\u0013S\t\u0003P\u000fK\t\u0003F\u0007S\u0004"));
               return this.a(var1);
            } else if (var1.startsWith("\\")) {
               this.e.detailed(d("A\u000e\u0016\u0007E\u001fLZ\u0013S\t\u0003r)tLE_\nBLSW\u0012O"));
               return this.a(var1);
            } else if (var1.length() > 2 && var1.charAt(1) == ':') {
               this.e.detailed(d("A\u000e\u0016\u0007E\u001fLZ\u0013S\t\u0003r)tLGD\u000fQ\t\u0003P\u000fK\t\u0003F\u0007S\u0004"));
               return this.a(var1);
            } else if (!var1.startsWith(d("\u0004WB\u0016\u001d")) && !var1.startsWith(d("\nWF\\")) && !var1.startsWith(d("\nJZ\u0003\u001d")) && !var1.startsWith(d("\u0006BD\\"))) {
               this.e.detailed(d("A\u000e\u0016\u0014B\u0000BB\u000fQ\t\u0003F\u0007S\u0004\u000f\u0016\u0005O\t@]\u000fI\u000b\u0003E\u0003F\u001e@^FW\rW^"));
               ListIterator var9 = this.a.listIterator();

               while(var9.hasNext()) {
                  String var10 = (String)var9.next();
                  String var11 = var10 + var1;
                  if (this.e.isDetailedEnabled()) {
                     this.e.detailed(d("L\u0003\u0016\u0016F\u0018K\u001b\u0003K\tN\fF") + var10);
                  }

                  try {
                     InputStream var5 = this.a(var11);
                     if (var5 != null) {
                        return var5;
                     }
                  } catch (Exception var7) {
                  }

                  if (var6 != 0) {
                     break;
                  }
               }

               throw new IOException(d("\u0002L\u0016\u0015R\u000fK\u0016\u0014B\u001fLC\u0014D\t\u0003\u0011F") + var1 + d("K\u0003_\b\u0007\u001fFW\u0014D\u0004\u0003F\u0007S\u0004\u0003\u0011") + this.b + "'");
            } else {
               this.e.detailed(d("A\u000e\u0016\u0007E\u001fLZ\u0013S\t\u0003C\u0014KLSW\u0012O"));
               return this.a(var1);
            }
         } else {
            this.e.detailed(d("A\u000e\u0016\bHLPS\u0007U\u000fK\u0016\u0016F\u0018K"));
            var10000 = this.a(var1);
            return var10000;
         }
      }
   }

   private List b(String var1) {
      int var5 = WorkItem.d;
      this.e = Logger.getInstance(d("\"nw6w?\u000ep1"), d("9w\u007f*"), d("*JZ\u0003k\u0003@W\u0012H\u001e"));
      if (var1 == null) {
         return null;
      } else {
         Vector var2 = new Vector();
         StringTokenizer var3 = new StringTokenizer(var1, d("W\u000f") + File.pathSeparator, false);

         while(var3.hasMoreTokens()) {
            String var4 = var3.nextToken().trim();
            if (var4.length() == 0) {
               var4 = ".";
            }

            if (var4.length() != 0 && !var4.endsWith("/") && !var4.endsWith(File.separator)) {
               label47: {
                  if (var4.startsWith("/") || var4.indexOf(47) >= 0 && var4.indexOf(92) < 0) {
                     var4 = var4 + '/';
                     if (var5 == 0) {
                        break label47;
                     }
                  }

                  var4 = var4 + File.separator;
               }
            }

            var2.add(var4);
            if (var5 != 0) {
               break;
            }
         }

         return var2;
      }
   }

   public void addResolver(Resolver var1) {
      this.d.add(var1);
   }

   public void removeResolver(Resolver var1) {
      this.d.remove(var1);
   }

   // $FF: synthetic method
   static Class c(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw (new NoClassDefFoundError()).initCause(var2);
      }
   }

   private static String d(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 108;
               break;
            case 1:
               var10003 = 35;
               break;
            case 2:
               var10003 = 54;
               break;
            case 3:
               var10003 = 102;
               break;
            default:
               var10003 = 39;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   public interface Resolver {
      InputStream getInputStream(String var1);
   }
}
