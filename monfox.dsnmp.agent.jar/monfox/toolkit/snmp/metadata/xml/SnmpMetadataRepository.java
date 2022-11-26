package monfox.toolkit.snmp.metadata.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import monfox.jdom.Document;
import monfox.jdom.Element;
import monfox.jdom.JDOMException;
import monfox.jdom.input.SAXBuilder;
import monfox.log.Logger;
import monfox.toolkit.snmp.metadata.Result;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.SnmpMetadataException;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SnmpMetadataRepository implements SnmpMetadata.Repository {
   private Logger a = null;
   private boolean b = false;
   private boolean c = false;
   private Map d = new Hashtable();
   private Map e = new Hashtable();
   private String f = b("}`HWB==\bB\u0002'4\bVF:/HIC>+HWD1(HXL >\\\u0015@<5\u0001UU|/\bUA82\u0013\u0015^=6\u0017\u0015@:9\u0014\u0015H+/\\");
   // $FF: synthetic field
   static Class g;

   public SnmpMetadataRepository() {
      this.a = Logger.getInstance(b("\u00005\nJ`6/\u0006^L':5_]<(\u000eNB!\""));
   }

   public String getSearchPath() {
      return this.f;
   }

   public void setSearchPath(String var1) {
      this.f = var1;
   }

   public Result loadModules(String[] var1, SnmpMetadata var2) throws IOException {
      SnmpMetadataLoader var3 = new SnmpMetadataLoader();
      var3.setSearchPath(this.f);
      var3.a(this.e);
      return var3.loadModules(var1, var2);
   }

   public Result loadModule(String var1, SnmpMetadata var2) throws IOException, SnmpMetadataException {
      SnmpMetadataLoader var3 = new SnmpMetadataLoader();
      var3.setSearchPath(this.f);
      var3.a(this.e);
      return var3.loadModule(var1, var2);
   }

   public Result loadFile(String var1, SnmpMetadata var2) throws IOException, SnmpMetadataException {
      try {
         SnmpMetadataLoader var3 = new SnmpMetadataLoader();
         var3.setSearchPath(this.f);
         var3.a(this.e);
         return var3.load(var1, var2);
      } catch (XMLException var4) {
         throw new SnmpMetadataException(b("\u000b\u0016+\u001aa<:\u0003\u001ah+8\u0002JY:4\t\u0000") + var4.toString());
      }
   }

   public Set getModuleNames() {
      return this.e.keySet();
   }

   public void refresh() {
      boolean var9 = SnmpMetadataLoader.i;
      StringTokenizer var1 = new StringTokenizer(this.f, b("hw") + File.pathSeparator, false);

      label74:
      do {
         boolean var10000 = var1.hasMoreTokens();

         label71:
         while(var10000) {
            String var2 = var1.nextToken();
            File var3 = new File(var2);
            if (var3.isDirectory() && var3.exists()) {
               this.a.debug(b("\u001f4\u0006^D=<GhH#4\u0014SY<)\u001e\u001a}2/\u000f\u001a\u0005\u00172\u0015_N'4\u0015C\u0004i{") + var2);
               File[] var4 = var3.listFiles();
               int var5 = 0;

               while(var5 < var4.length) {
                  File var6 = var4[var5];
                  var10000 = var6.isFile();
                  if (var9) {
                     continue label71;
                  }

                  if (var10000) {
                     String var7 = var6.getAbsolutePath();
                     if (var7.endsWith(b("}#\nV"))) {
                        try {
                           FileInputStream var8 = new FileInputStream(var6);
                           this.a(var6.getAbsolutePath(), var8);
                           var8.close();
                        } catch (IOException var12) {
                        }
                     }
                  }

                  ++var5;
                  if (var9) {
                     break;
                  }
               }

               if (!var9) {
                  continue label74;
               }
            }

            String var13 = var2 + b("|8\bTY65\u0013I\u0003+6\u000b");
            InputStream var14 = null;
            boolean var15 = false;

            try {
               URL var16 = new URL(var13);
               var14 = var16.openStream();
               var15 = true;
            } catch (MalformedURLException var10) {
            } catch (IOException var11) {
            }

            if (var14 == null) {
               var14 = (g == null ? (g = a(b(">4\t\\B+u\u0013UB?0\u000eN\u0003 5\nJ\u0003>>\u0013[I2/\u0006\u0014U>7IiC>+*_Y2?\u0006NL\u0001>\u0017U^:/\bHT"))) : g).getResourceAsStream(var13);
               var15 = false;
            }

            if (var14 != null) {
               this.a(var14, var2, var15);
               if (!var9) {
                  continue label74;
               }
            }

            this.a.warn(b("\u001d4G\u001dN<5\u0013_C'(IB@?|G\\B&5\u0003\u001aK<)G\u001d") + var2);
            continue label74;
         }

         return;
      } while(!var9);

   }

   private void a(InputStream var1, String var2, boolean var3) {
      boolean var14 = SnmpMetadataLoader.i;
      this.a.debug(b("\u001f4\u0006^D=<GhH#4\u0014SY<)\u001e\u001a}2/\u000f\u0000\r") + var2);

      try {
         SAXBuilder var4 = new SAXBuilder(true);
         var4.setEntityResolver(new Resolver());
         Document var5 = var4.build(var1);
         Element var6 = var5.getRootElement();
         if (var6.getName().equals(b("\u001e4\u0003OA6\u0017\u000eIY"))) {
            List var7 = var6.getChildren(b("\u001e4\u0003OA6\t\u0002\\"));
            ListIterator var8 = var7.listIterator();

            while(var8.hasNext()) {
               Element var9 = (Element)var8.next();
               String var10 = var9.getAttributeValue(b("=:\n_"));
               String var11 = var2 + "/" + var10 + b("}#\nV");

               try {
                  InputStream var12 = null;
                  if (var14) {
                     break;
                  }

                  if (var3) {
                     URL var13 = new URL(var11);
                     var12 = var13.openStream();
                  } else {
                     var12 = (g == null ? (g = a(b(">4\t\\B+u\u0013UB?0\u000eN\u0003 5\nJ\u0003>>\u0013[I2/\u0006\u0014U>7IiC>+*_Y2?\u0006NL\u0001>\u0017U^:/\bHT"))) : g).getResourceAsStream(var11);
                  }

                  if (var12 != null) {
                     this.a(var11, var12);
                     var12.close();
                  }
               } catch (MalformedURLException var15) {
                  this.a.error(b("\u0011:\u0003\u001a@<?\u0012VHs\u000e5v"), var15);
               } catch (IOException var16) {
                  this.a.error(b("\u001a\u0014G\u007f_!4\u0015\u001aB={\nUI&7\u0002\u001aA<:\u0003"), var16);
               }

               if (var14) {
                  break;
               }
            }
         }
      } catch (JDOMException var17) {
         this.a.error(b("\u000b\u0016+\u001ah!)\bH\r:5G\u001d") + var2 + b("t{OYB=/\u0002TY u\u001fWAz"), var17);
      }

   }

   private void a(String var1, InputStream var2) {
      boolean var10 = SnmpMetadataLoader.i;
      String var6;
      if (this.b) {
         try {
            SAXBuilder var13 = new SAXBuilder(true);
            var13.setEntityResolver(new Resolver());
            Document var14 = var13.build(var2);
            Element var15 = var14.getRootElement();
            if (b("\u001e4\u0003OA6").equals(var15.getName())) {
               var6 = var15.getAttributeValue(b("=:\n_"));
               if (!this.e.containsKey(var6)) {
                  this.e.put(var6, var1);
                  if (this.a.isDebugEnabled()) {
                     this.a.debug(b("\u00154\u0012TIs6\b^X?>G\u001d") + var6 + b("tuGjL'3]\u001a") + var1);
                  }
               }

               if (!var10) {
                  return;
               }
            }

            this.a.warn(b("\u001a5\u0011[A:?GWB7.\u000b_\r52\u000b_\u0017s|G") + var1 + b("tuGm_<5\u0000\u001a_<4\u0013\u001aH?>\n_C'{\u0013C]6{@") + var15.getName() + b("twGIE<.\u000b^\r1>G\u001d`<?\u0012VHt"));
            return;
         } catch (JDOMException var12) {
            this.a.error(b("\u000b\u0016+\u001a`<?\u0012VHs\u0017\b[Is\u001e\u0015HB!"), var12);
            if (!var10) {
               return;
            }
         }
      }

      boolean var3 = false;
      InputStreamReader var4 = new InputStreamReader(var2);
      BufferedReader var5 = new BufferedReader(var4);

      boolean var10000;
      label71: {
         try {
            label69:
            do {
               var6 = var5.readLine();
               if (var6 == null) {
                  break;
               }

               var6 = var6.trim();
               if (var6.startsWith(b("o\b\tW]\u001e>\u0013[I2/\u0006"))) {
                  this.a.warn(b("\u001a5\u0011[A:?GwB7.\u000b_\r52\u000b_\rt") + var1 + b("tuG\u007fU#>\u0004NH7{\u0015UB'{\u0002VH>>\tN\r'\"\u0017_\r<=G\u001d`<?\u0012VHtu"));
                  if (!var10) {
                     break;
                  }
               }

               if (var6.startsWith(b("o\u0016\b^X?>"))) {
                  StringTokenizer var7 = new StringTokenizer(var6, b("nyG"), false);
                  String var8 = "";

                  do {
                     if (!var7.hasMoreTokens()) {
                        break label69;
                     }

                     String var9 = var7.nextToken();
                     var10000 = var8.equals(b("=:\n_"));
                     if (var10) {
                        break label71;
                     }

                     if (var10000) {
                        label60: {
                           this.a.debug(b("\u00154\u0012TIs6\b^X?>G\u001d") + var9 + b("tuGjL'3]\u001a") + var1);
                           if (!this.e.containsKey(var9)) {
                              this.e.put(var9, var1);
                              if (!var10) {
                                 break label60;
                              }
                           }

                           this.a.debug(b("\u001a<\tU_6{\u0003O]?2\u0004[Y6{\nUI&7\u0002\u001aK:7\u0002\u001a\n") + var1 + b("tu"));
                        }

                        var3 = true;
                        if (!var10) {
                           break label69;
                        }
                     }

                     var8 = var9;
                  } while(!var10);
               }
            } while(!var10);
         } catch (IOException var11) {
         }

         var10000 = var3;
      }

      if (!var10000) {
         this.a.error(b("\u001a5\u0011[A:?GwB7.\u000b_\u0017s") + var1);
      }

   }

   // $FF: synthetic method
   static Class a(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw (new NoClassDefFoundError()).initCause(var2);
      }
   }

   private static String b(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 83;
               break;
            case 1:
               var10003 = 91;
               break;
            case 2:
               var10003 = 103;
               break;
            case 3:
               var10003 = 58;
               break;
            default:
               var10003 = 45;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   private class Resolver implements EntityResolver {
      private Resolver() {
      }

      public InputSource resolveEntity(String var1, String var2) throws SAXException, IOException {
         URL var3 = new URL(var2);
         if (a("\u0015?C}N\r&R<[L+[>").equals(var3.getHost())) {
            String var7 = var3.getFile();
            URL var5 = this.getClass().getResource(var7);
            InputStream var6 = var5.openStream();
            return new InputSource(var6);
         } else {
            InputStream var4 = var3.openStream();
            return new InputSource(var4);
         }
      }

      // $FF: synthetic method
      Resolver(Object var2) {
         this();
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 98;
                  break;
               case 1:
                  var10003 = 72;
                  break;
               case 2:
                  var10003 = 52;
                  break;
               case 3:
                  var10003 = 83;
                  break;
               default:
                  var10003 = 35;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }
}
