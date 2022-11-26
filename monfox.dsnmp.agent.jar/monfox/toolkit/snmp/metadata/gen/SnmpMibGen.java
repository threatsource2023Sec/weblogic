package monfox.toolkit.snmp.metadata.gen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import monfox.jdom.Comment;
import monfox.jdom.DocType;
import monfox.jdom.Document;
import monfox.jdom.Element;
import monfox.jdom.JDOMException;
import monfox.jdom.output.DOMOutputter;
import monfox.jdom.output.XMLOutputter;
import monfox.log.Logger;
import monfox.log.SimpleLogger;
import monfox.toolkit.snmp.Version;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.html.HTMLMIBOutputter;
import monfox.toolkit.snmp.metadata.xml.SnmpMetadataLoader;
import monfox.toolkit.snmp.util.AsciiReader;
import monfox.toolkit.snmp.util.Commandline;

public class SnmpMibGen {
   static final int a = 0;
   static final int b = 1;
   static final int c = 2;
   private String d;
   private String e;
   private String f;
   private String g;
   private boolean h;
   private boolean i;
   private boolean j;
   private boolean k;
   private boolean l;
   private boolean m;
   private boolean n;
   private boolean o;
   private int p;
   private Element q;
   private Element r;
   private Properties s;
   private SnmpMibGenListener t;
   private PrintStream u;
   private Logger v;
   private Result w;
   private Result x;
   private SnmpMetadata y;
   private String z;
   private Hashtable A;
   private static final String B = "$Id: SnmpMibGen.java,v 1.53 2006/07/14 20:48:04 sking Exp $";
   // $FF: synthetic field
   static Class C;

   public static void main(String[] var0) {
      try {
         SnmpMibGen var1 = new SnmpMibGen();
         var1.a(var0);
      } catch (Exception var2) {
      }

   }

   public SnmpMibGen() {
      this.d = null;
      this.e = null;
      this.f = ".";
      this.g = g("\u0014(O%{\u00015\u0001dvU,D;#\u0014(O;");
      this.h = false;
      this.i = false;
      this.j = true;
      this.k = true;
      this.l = false;
      this.m = false;
      this.n = true;
      this.o = false;
      this.p = 0;
      this.q = null;
      this.r = null;
      this.s = new Properties();
      this.u = System.out;
      this.v = Logger.getInstance(g("*/@yB\u0010#jla"));
      this.w = new Result();
      this.x = new Result();
      this.y = null;
      this.z = g("\u00115Yy5VnZ~xW,Bgi\u00169\u0003j`\u0014n@fa\u001f.U&{\u0016.Abf\rn^gb\tnI}kV2Cd\u007f4$Yhk\u00185L'k\r%");
      this.A = new Hashtable();
      this.reset();
   }

   public void reset() {
      this.q = new Element(g("=.N|b\u001c/Y"));
      this.w = new Result();
      this.x = new Result();
      this.y = null;
      this.r = null;
   }

   void a(String[] var1) throws Exception {
      int var15 = Message.d;
      this.reset();
      new Version();
      System.out.println(g("sl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"TK\u0000$/=8Chb\u0010\"~GB)i\u007f /*\u000f`Y/4\bo)L\u0016,]`c\u001c3\u0002Dj\r Ih{\u0018ajla\u001c3L}`\u000bK\u0000$/+$Aln\n$\r") + Version.getBuildRelease() + g("sl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"T") + g("sl\u0000)'\u001ah\r86@x\u0000;?Iw\u0001)B\u0016/KfwUaaELUalecY\u0013Dng\r2\r[j\n$_\u007fj\u001d") + g("sl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"T"));
      Commandline var3 = null;

      String var6;
      try {
         String var4 = g("F7I~f\b _");
         String[] var5 = new String[]{g("\u000e _g"), g("\u0010&Cf}\u001c"), g("\b4Dl{"), g("\u001d$O|h"), g("\u000f$_k`\n$"), g("\u0011$Ay"), g("\u00184Yfc\u0016 I"), g("\u001d4@y"), g("\u001d$^j}\u00101Y``\u0017"), g("\u0014.I|c\u001c2"), g("\u000b(Na"), g("\u0015 U")};
         var6 = g("\u0011.Ud\u007f\u001c/b");
         String[] var7 = new String[]{g("\u0018-Dh|\u001c2"), g("\u0001,Dk"), g("\u0001,A"), g("\u0014$Yhk\u00185L"), g("\u0016(Iz"), g("\u00115@e"), g("\u001c9Y"), g("\t Ya"), g("\u0017 @l"), g("\u001d5I")};
         var3 = new Commandline(var1, var4, var6, var5, var7);
      } catch (Throwable var23) {
         System.out.println(var23.getMessage());
         b();
         this.a((ErrorMessage)(new ErrorMessage.InvalidOption(var23.getMessage())), (Object)null);
         throw new Exception();
      }

      if (!var3.hasFlag(g("F{Elc\t")) && var3.params.length != 0) {
         this.x = new Result();
         this.p = var3.getIntOption("O", 0);
         this.d = var3.getOption(g("\u0017{Chb\u001c"), (String)null);
         this.z = var3.getOption(g("\u001d5I"), this.z);
         this.h = var3.hasFlag(g("\u001d{Ilm\f&"));
         this.i = var3.hasFlag(g("\u000f{[l}\u001b.^l"));
         this.l = var3.hasFlag(g("\u000e{Zh}\u0017"));
         this.o = var3.hasFlag(g("\u000b{_`l\u0011"));
         this.l = this.l || !var3.hasFlag(g("\b{\\|f\u001c5"));
         this.j = !var3.hasFlag(g("\u0010{Dna\u00163H")) && !var3.hasFlag(g("\u0015 U"));
         this.q = new Element(g("=.N|b\u001c/Y"));
         if (this.h) {
            Logger.setProvider(new SimpleLogger.Provider(g("*/@yB\u0010#jlaW%On")));
            this.v = Logger.getInstance(g("*/@yB\u0010#jla"));
         }

         this.m = var3.hasFlag(g("\u001d$^j}\u00101Y``\u0017"));
         this.k = var3.hasFlag(g("\u0014.I|c\u001c2"));
         this.f = var3.getOption(g("\t{]h{\u0011"), g("Wm\u0002d`\u0017'Bq \r.Bed\u00105\u0002za\u00141\u0002df\u001b2\u0002kn\n$\u0001&b\u0016/KfwV5Bfc\u0012(Y&|\u0017,]&b\u0010#^&j\u00015\u0001"));
         this.g = var3.getOption(g("\u001c{Hq{"), g("\u0014(O%{\u00015\u0001dfKm@p#\u0014(O;"));
         this.n = var3.hasFlag(g("\u0018{L|{\u0016-Bhk")) || this.k;
         StringTokenizer var25 = new StringTokenizer(this.g, g("U{\u0016"), false);
         String var26 = "";

         int var10000;
         while(true) {
            if (var25.hasMoreTokens()) {
               var6 = var25.nextToken();
               var10000 = var6.length();
               if (var15 != 0) {
                  break;
               }

               if (var10000 != 0) {
                  var26 = var26 + "," + var6 + "," + var6.toLowerCase() + "," + var6.toUpperCase();
               }

               if (var15 == 0) {
                  continue;
               }
            }

            this.g = var26;
            var10000 = this.p;
            break;
         }

         if (var10000 > 0) {
            try {
               URL var27 = this.getClass().getResource(g("\u00161Y`b\u0010;HV") + this.p + g("W1_f\u007f\u001c3Y`j\n"));
               InputStream var28 = var27.openStream();
               this.s.load(var28);
            } catch (Exception var22) {
               this.v.debug(g("0/[hc\u0010%\rF\u007f\r(@`u\u00185DfaY\rH\u007fj\u0015"), var22);
               throw new Exception();
            }
         }

         var6 = var3.getOption(g("\u0011{E}b\u0015"), (String)null);
         String var29 = var3.getOption(g("\u0001,Dk"), (String)null);
         String var8 = var3.getOption(g("\u0001{Udc"), (String)null);
         String var9 = var3.getOption(g("\u0014{@l{\u0018%L}n"), (String)null);
         String var10 = var3.getOption(g("\u0016{B`k\n"), (String)null);
         this.e = var3.getOption(g("\u0018-Dh|\u001c2"), (String)null);
         boolean var11 = var3.hasFlag(g("\u001d4@y"));
         this.setListener(new DefaultSnmpMibGenListener());
         if (var6 != null) {
            this.o = true;
            this.m = true;
            File var12 = new File(var6);
            if (!var12.exists()) {
               var12.mkdir();
            }

            if (!var12.exists()) {
               this.a((ErrorMessage)(new ErrorMessage.FileOpenError(var6, g("\u0017.\rzz\u001a)\rmf\u000b$N}`\u000b8"))));
               throw new Exception();
            }

            if (!var12.isDirectory()) {
               this.a((ErrorMessage)(new ErrorMessage.FileOpenError(var6, g("\u0017.Y)nY%D{j\u001a5B{v"))));
               throw new Exception();
            }
         }

         label193: {
            label232: {
               this.a(new InfoMessage(g("sl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"TK\u0000$/5.Lmf\u0017&\r//) _zf\u0017&\rZA4\u0011\rDF;a`fk\f-Hz\u0005Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"Tl\u0000$\"")));
               String var13;
               int var30;
               if (this.k) {
                  var30 = 0;

                  while(var30 < var3.params.length) {
                     var13 = var3.params[var30];
                     this.loadMibModule(var13);
                     ++var30;
                     if (var15 != 0) {
                        break label232;
                     }

                     if (var15 != 0) {
                        break;
                     }
                  }

                  if (var15 == 0) {
                     break label232;
                  }
               }

               var30 = 0;

               while(var30 < var3.params.length) {
                  var13 = var3.params[var30];

                  label175: {
                     try {
                        this.loadMibFile(var13);
                     } catch (Exception var24) {
                        break label175;
                     }

                     if (var15 != 0) {
                        break label193;
                     }
                  }

                  ++var30;
                  if (var15 != 0) {
                     break;
                  }
               }
            }

            if (this.n) {
               this.a();
            }
         }

         if (this.x.getErrorCount() > 0) {
            this.a((InfoMessage)(new InfoMessage.ErrorSummary(this.x)), (Object)null);
            if (this.j) {
               this.a(new InfoMessage(g("<3_f}\naHqf\n5\u0001){\u001c3@`a\u00185Dgh")));
               this.a(new InfoMessage(g(",2H){\u0011$\r.\"\u0010&Cf}\u001cf\rf\u007f\r(Bg/\r.\r`h\u0017._l/\u001c3_f}\n")));
               throw new Exception();
            }
         }

         label163: {
            Result var31 = this.validateMetadata();
            if (var31.getErrorCount() > 0) {
               if (this.j) {
                  this.a((ErrorMessage)(new ErrorMessage.FatalError(g("Sk\rL}\u000b._z/\u001c9Dz{UaNha\u0017.Y)l\u0016/Y`a\f$"))));
                  this.a(new InfoMessage(g("Sk\rL}\u000b._z/\u001c9Dz{UaYl}\u0014(Ch{\u0010/J)%S")));
                  this.a(new InfoMessage(g("Tl\r\\|\u001caYajYf\u0000`h\u0017._l(Y.]}f\u0016/\r}`Y(Jg`\u000b$\rl}\u000b._z")));
                  throw new Exception();
               }

               this.a((ErrorMessage)(new ErrorMessage.GenericWarning(g("Sk\rZ{\u000b(N}/\u000f A`k\u00185DfaY%Dzn\u001b-Hm!Y}Dna\u00163DghY$_{`\u000b2\u0013"))));
               if (var15 == 0) {
                  break label163;
               }
            }

            if (var8 != null) {
               label157: {
                  int var32 = var8.lastIndexOf(46);
                  if (var32 > 0) {
                     this.r.addAttribute(g("\u0017 @l"), var8.substring(0, var32));
                     if (var15 == 0) {
                        break label157;
                     }
                  }

                  this.r.addAttribute(g("\u0017 @l"), var8);
               }
            }
         }

         if (var8 == null && var29 == null && var9 == null && var10 == null && var6 == null) {
            var8 = new String(g("\u001d$Khz\u00155\u0003qb\u0015"));
         }

         if (var29 != null) {
            try {
               this.a((InfoMessage)(new InfoMessage.SavingFile(var29, g("* [`a\u001ea\u0011QB0\u0003\rQB5\u007f"))));
               this.e(var29);
            } catch (Exception var21) {
               this.a((ErrorMessage)(new ErrorMessage.FileSaveError(var29, g("E\u0019`@MY\u0019`E1Y\u0007DejY\u0012L\u007fjY\u0007L`c\u001c%\r!") + var21 + ")")));
               this.v.debug(g("E\tyDCY\u000eX}\u007f\f5\u0013)I\u0010-H)\\\u00187H)I\u0018(AlkC"), var21);
            }
         }

         if (var8 != null) {
            try {
               this.d(var8);
            } catch (Exception var20) {
               this.a((ErrorMessage)(new ErrorMessage.FileSaveError(var8, g("E\u0019`E/4$Yhk\u00185L7/?(Al/* [l/? Dej\u001da\u0005") + var20 + ")")));
               this.v.debug(g("E\tyDCY\u000eX}\u007f\f5\u0013)I\u0010-H)\\\u00187H)I\u0018(AlkC"), var20);
            }
         }

         if (var6 != null) {
            try {
               this.c(var6);
            } catch (Exception var19) {
               this.a((ErrorMessage)(new ErrorMessage.FileSaveError(var6, g("E\tyDCY\u000eX}\u007f\f5\u0013)I\u0010-H)H\u001c/H{n\r(Bg/? Dej\u001da\u0005") + var19 + ")")));
               this.v.debug(g("E\tyDCY\u000eX}\u007f\f5\u0013)I\u0010-H)H\u001c/H{n\r(Bg/? Dej\u001d{"), var19);
            }
         }

         if (var11) {
            try {
               System.out.println(this.y);
            } catch (Exception var18) {
            }
         }

         if (var9 != null) {
            this.a((InfoMessage)(new InfoMessage.SavingFile(var9, g("* [`a\u001ea\u0011Dj\r Ih{\u0018aoln\u0017\u007f"))));

            try {
               this.a(var9, false);
            } catch (Exception var17) {
               this.a((ErrorMessage)(new ErrorMessage.FileSaveError(var9, g("E\fH}n\u001d Yh/;$Lg1Y\u0007DejY\u0012L\u007fjY\u0007L`c\u001c%\r!") + var17 + ")")));
            }
         }

         if (var10 != null) {
            this.a((InfoMessage)(new InfoMessage.SavingFile(var10, g("* [`a\u001ea\u0011Ff\u001daoln\u0017\u007f"))));

            try {
               this.a(var10, true);
            } catch (Exception var16) {
               this.a((ErrorMessage)(new ErrorMessage.FileSaveError(var10, g("E\fH}n\u001d Yh/;$Lg1Y\u0007DejY\u0012L\u007fjY\u0007L`c\u001c%\r!") + var16 + ")")));
            }
         }

         this.a(new InfoMessage(g("sK\u0000$/:.@yc\u001c5Hm\u0005s")));
      } else {
         b();
         throw new Exception();
      }
   }

   SnmpMibGen(boolean var1, boolean var2, boolean var3) {
      this();
   }

   public synchronized Result autoLoadModules() {
      this.w = new Result();
      this.a();
      return this.w;
   }

   public synchronized Result loadMib(String var1) throws FileNotFoundException, MalformedURLException, IOException {
      return var1.indexOf(46) > 0 ? this.loadMibFile(var1) : this.loadMibModule(var1);
   }

   public synchronized Result loadMibFile(String var1) throws FileNotFoundException, MalformedURLException, IOException {
      this.w = new Result();

      try {
         this.a(var1, 2, this.j);
      } catch (FileNotFoundException var3) {
         this.a((ErrorMessage)(new ErrorMessage.FileOpenError(var1, g("?(Al/\u0017.Y)I\u00164Cm"))));
         throw var3;
      } catch (MalformedURLException var4) {
         this.a((ErrorMessage)(new ErrorMessage.FileOpenError(var1, g("0/[hc\u0010%\r\\]5"))));
         throw var4;
      } catch (IOException var5) {
         this.a((ErrorMessage)(new ErrorMessage.FileOpenError(var1, g("0\u000e\rL}\u000b._)'") + var5.getMessage() + ")")));
         throw var5;
      } catch (Exception var6) {
      }

      return this.w;
   }

   public synchronized Result loadMibModule(String var1) {
      this.w = new Result();
      Vector var2 = new Vector();
      if (var1.indexOf(".") >= 0) {
         this.a((ErrorMessage)(new ErrorMessage.GenericWarning(var1, g("4.I|c\u001caChb\u001ca\n") + var1 + g("^aNfa\r Dg|Y'DejY$U}j\u00172Dfa"))));
      }

      this.a(var1, var2);
      return this.w;
   }

   public synchronized Result validateMetadata() {
      this.r = null;
      this.y = null;
      this.w = new Result();
      o var1 = new o(this.s, this);
      var1.b(this.l);
      var1.c(this.h);
      var1.setAliases(this.e);
      var1.d(this.m);
      var1.a(this.j);
      var1.isRichMode(this.o);
      var1.a(this.q);
      this.r = var1.metadata;
      if (this.d != null) {
         this.r.addAttribute(g("\u0017 @l"), this.d);
      }

      SnmpMetadata var2 = new SnmpMetadata();
      SnmpMetadataLoader var3 = new SnmpMetadataLoader();

      try {
         var3.process(this.r, var2, false);
      } catch (Exception var5) {
         if (this.i) {
            this.a((ErrorMessage)(new ErrorMessage.GenericWarning(var5.getMessage())));
         }

         this.v.error(g("!\fa$1*/@yB\u001c5Lmn\r \rE`\u0018%DghY\u0004_{`\u000b"), var5);
      }

      this.y = var2;
      this.a((InfoMessage)(new InfoMessage.ValidationComplete(this.w)));
      return this.w;
   }

   void a(ErrorMessage var1) {
      this.a((ErrorMessage)var1, (Object)null);
   }

   void a(ErrorMessage var1, Object var2) {
      if (var2 != null && var2 instanceof Element) {
         String var3 = ((Element)var2).getAttributeValue(g("\t("));
         if (var3 != null) {
            try {
               this.v.debug(g("\u0018%I`a\u001ea]h}\n$\r`a\u001f.\u0005") + var3 + ")");
               StringTokenizer var4 = new StringTokenizer(var3, ",", false);
               if (var4.countTokens() >= 3) {
                  int var5 = Integer.parseInt(var4.nextToken());
                  int var6 = Integer.parseInt(var4.nextToken());
                  String var7 = var4.nextToken(g("sL")).substring(1).trim();
                  this.v.debug(g("Tl\rof\u0015$\u0017)") + var7);
                  int var8 = this.a(var7, var5);
                  this.v.debug(g("Tl\ref\u0017$\u0017)") + var8);
                  if (var8 >= 0) {
                     var1.setFilename(var7);
                     var1.setLineNumber(var8);
                  }
               }
            } catch (Exception var9) {
               this.v.error(g("\u0015(Cl/\u00174@kj\u000ba]{`\u001a$^zf\u0017&\rlw\u001a$]}f\u0016/"), var9);
            }
         }
      }

      if (this.t != null) {
         this.t.handleError(var1, var2);
      }

      if (this.w != null) {
         this.w.a(var1);
      }

      if (this.x != null) {
         this.x.a(var1);
      }

   }

   int a(String var1, int var2) {
      int var7 = Message.d;
      int[] var3 = (int[])((int[])this.A.get(var1));
      if (var3 == null) {
         try {
            var3 = this.a(var1);
            this.A.put(var1, var3);
         } catch (IOException var8) {
            this.v.debug(g("\u001a Cg`\raByj\u0017aK`c\u001c{") + var1, var8);
            return -1;
         }
      }

      if (var3.length < 4) {
         return -1;
      } else {
         int var4 = var3[var3.length - 1];
         if (var4 == 0) {
            return -1;
         } else {
            int var5 = var2 / var4 * (var3.length - 1);
            int var10000;
            int var6;
            if (var3[var5] > var2) {
               var6 = var5;

               while(true) {
                  if (var6 >= 1) {
                     var10000 = var3[var6];
                     if (var7 != 0) {
                        break;
                     }

                     if (var10000 < var2) {
                        return var6;
                     }

                     --var6;
                     if (var7 == 0) {
                        continue;
                     }
                  }

                  var10000 = 1;
                  break;
               }

               return var10000;
            } else {
               var6 = var5;

               int var10001;
               while(true) {
                  if (var6 < var3.length) {
                     var10000 = var3[var6];
                     var10001 = var2;
                     if (var7 != 0) {
                        break;
                     }

                     if (var10000 > var2) {
                        return var6 - 1;
                     }

                     ++var6;
                     if (var7 == 0) {
                        continue;
                     }
                  }

                  var10000 = var3.length;
                  var10001 = 1;
                  break;
               }

               return var10000 - var10001;
            }
         }
      }
   }

   int[] a(String var1) throws IOException {
      int var10 = Message.d;
      Object var2 = null;
      if (!var1.startsWith(g("\u001f(Al5")) && !var1.startsWith(g("\u00115Yy5")) && !var1.startsWith(g("\u0013 _3")) && !var1.startsWith(g("\u001f5]3"))) {
         File var11 = new File(var1);
         if (var11.exists()) {
            var2 = new FileInputStream(var1);
         } else {
            var2 = (C == null ? (C = f(g("\u0014.Co`\u0001oYf`\u0015*D}!\n/@y!\u0014$Yhk\u00185L'h\u001c/\u0003Za\u00141``m>$C"))) : C).getResourceAsStream(var1);
            if (var2 == null) {
               throw new FileNotFoundException(var1);
            }
         }
      } else {
         URL var3 = new URL(var1);
         var2 = var3.openStream();
      }

      AsciiReader var12 = new AsciiReader(new InputStreamReader((InputStream)var2));
      Vector var4 = new Vector();
      var4.addElement(new Integer(0));
      var4.addElement(new Integer(0));
      boolean var5 = true;
      int var6 = 0;
      boolean var7 = false;

      int var10000;
      while(true) {
         int var13;
         if ((var13 = var12.read()) >= 0) {
            var10000 = var13;
            if (var10 != 0) {
               break;
            }

            if (var13 == 10) {
               this.v.debug(g("Tl\rhk\u001d(Cn/\u0015(Clm\u000b$Lb5Y") + var6);
               var4.addElement(new Integer(var6));
            }

            ++var6;
            if (var10 == 0) {
               continue;
            }
         }

         var10000 = var4.size();
         break;
      }

      int[] var8 = new int[var10000];
      int var9 = 0;

      int[] var14;
      while(true) {
         if (var9 < var8.length) {
            var14 = var8;
            if (var10 != 0) {
               break;
            }

            var8[var9] = (Integer)var4.elementAt(var9);
            ++var9;
            if (var10 == 0) {
               continue;
            }
         }

         var14 = var8;
         break;
      }

      return var14;
   }

   void a(InfoMessage var1) {
      this.a((InfoMessage)var1, (Object)null);
   }

   void a(InfoMessage var1, Object var2) {
      if (this.t != null) {
         this.t.handleInfo(var1, var2);
      }

   }

   void b(String var1, int var2) throws FileNotFoundException, IOException, MalformedURLException, Exception {
      this.a((InfoMessage)(new InfoMessage.LoadingFile(var1)));
      bp var3 = null;
      b var4 = null;

      try {
         if (!var1.startsWith(g("\u001f(Al5")) && !var1.startsWith(g("\u00115Yy5")) && !var1.startsWith(g("\u0013 _3")) && !var1.startsWith(g("\u001f5]3"))) {
            File var11 = new File(var1);
            if (var11.exists()) {
               var3 = new bp(var1, this.i, this.h);
            } else {
               InputStream var6 = (C == null ? (C = f(g("\u0014.Co`\u0001oYf`\u0015*D}!\n/@y!\u0014$Yhk\u00185L'h\u001c/\u0003Za\u00141``m>$C"))) : C).getResourceAsStream(var1);
               if (var6 == null) {
                  throw new FileNotFoundException(var1);
               }

               var3 = new bp(var6, this.i, this.h, this.l);
            }
         } else {
            URL var5 = new URL(var1);
            var3 = new bp(var5.openStream(), this.i, this.h, this.l);
            var3.a(var1);
         }

         switch (var2) {
            case 0:
               var3.a(0);
               if (Message.d == 0) {
                  break;
               }
            case 1:
               var3.a(1);
         }

         var4 = new b(var3, this.i, this.h, this.l, this.q, this);
         var4.parse();
      } catch (MalformedURLException var7) {
         throw var7;
      } catch (FileNotFoundException var8) {
         throw var8;
      } catch (IOException var9) {
         throw var9;
      } catch (Exception var10) {
         this.v.error(g(",/_ll\u00167H{n\u001b-H)\u007f\u00183^l/\u001c3_f}Q2\u0004"), var10);
         throw new Exception(g(",/_ll\u00167H{n\u001b-H)\u007f\u00183^l/\u001c3_f}Q2\u00043/\"") + var10.getMessage() + "]");
      }

      if (var4.b()) {
         throw new Exception(g("</Nfz\u00175H{j\u001da") + var4.c() + g("Y1L{|\u001caH{}\u00163^'"));
      }
   }

   void a(String var1, int var2, boolean var3) throws FileNotFoundException, IOException, MalformedURLException, Exception {
      if (var3) {
         this.b(var1, var2);
         if (Message.d == 0) {
            return;
         }
      }

      this.c(var1, var2);
   }

   void c(String var1, int var2) throws FileNotFoundException, IOException, MalformedURLException, Exception {
      this.a((InfoMessage)(new InfoMessage.LoadingFile(var1)));
      i var3 = null;
      a var4 = null;

      try {
         if (!var1.startsWith(g("\u001f(Al5")) && !var1.startsWith(g("\u00115Yy5")) && !var1.startsWith(g("\u0013 _3")) && !var1.startsWith(g("\u001f5]3"))) {
            File var11 = new File(var1);
            if (var11.exists()) {
               var3 = new i(var1, this.i, this.h);
            } else {
               InputStream var6 = (C == null ? (C = f(g("\u0014.Co`\u0001oYf`\u0015*D}!\n/@y!\u0014$Yhk\u00185L'h\u001c/\u0003Za\u00141``m>$C"))) : C).getResourceAsStream(var1);
               if (var6 == null) {
                  throw new FileNotFoundException(var1);
               }

               var3 = new i(var6, this.i, this.h, this.l);
            }
         } else {
            URL var5 = new URL(var1);
            var3 = new i(var5.openStream(), this.i, this.h, this.l);
            var3.a(var1);
         }

         switch (var2) {
            case 0:
               var3.a(0);
               if (Message.d == 0) {
                  break;
               }
            case 1:
               var3.a(1);
         }

         var4 = new a(var3, this.i, this.h, this.l, this.q, this);
         var4.parse();
      } catch (MalformedURLException var7) {
         throw var7;
      } catch (FileNotFoundException var8) {
         throw var8;
      } catch (IOException var9) {
         throw var9;
      } catch (Exception var10) {
         this.v.error(g(",/_ll\u00167H{n\u001b-H)\u007f\u00183^l/\u001c3_f}Q2\u0004"), var10);
         throw new Exception(g(",/_ll\u00167H{n\u001b-H)\u007f\u00183^l/\u001c3_f}Q2\u00043/\"") + var10.getMessage() + "]");
      }

      if (var4.b()) {
         throw new Exception(g("</Nfz\u00175H{j\u001da") + var4.c() + g("Y1L{|\u001caH{}\u00163^'"));
      }
   }

   boolean b(String var1) {
      int var6 = Message.d;
      List var2 = this.q.getChildren(g("4.I|c\u001c"));
      ListIterator var3 = var2.listIterator();

      boolean var10000;
      while(true) {
         if (var3.hasNext()) {
            Element var4 = (Element)var3.next();
            String var5 = var4.getAttributeValue(g("\u0017 @l"));
            var10000 = var1.equals(var5);
            if (var6 != 0) {
               break;
            }

            if (var10000) {
               return true;
            }

            if (var6 == 0) {
               continue;
            }
         }

         var10000 = false;
         break;
      }

      return var10000;
   }

   int a() {
      int var4 = Message.d;
      this.a((InfoMessage)(new InfoMessage.AutoLoading()));
      int var1 = 0;
      Vector var2 = new Vector();
      Vector var3 = null;

      do {
         int var10000;
         label21: {
            if (var3 != null) {
               var10000 = var3.size();
               if (var4 != 0) {
                  break label21;
               }

               if (var10000 <= 0) {
                  break;
               }
            }

            var10000 = var1;
         }

         if (var10000 >= 5) {
            break;
         }

         var3 = new Vector();
         var1 += this.a(var2, var3);
      } while(var4 == 0);

      return var1;
   }

   int a(Vector var1, Vector var2) {
      int var18 = Message.d;
      int var3 = 0;
      Vector var4 = new Vector();
      Vector var5 = new Vector();
      var4.addElement(g("*\u000f`YyKlnFA?"));
      List var6 = this.q.getChildren(g("4.I|c\u001c"));
      ListIterator var7 = var6.listIterator();

      Object var30;
      label131:
      while(true) {
         ListIterator var10000 = var7;

         label128:
         while(true) {
            boolean var29 = var10000.hasNext();

            label125:
            while(true) {
               if (!var29) {
                  break label128;
               }

               Element var8 = (Element)var7.next();
               String var9 = var8.getAttributeValue(g("\u0017 @l"));
               var30 = var9;
               if (var18 != 0) {
                  break label131;
               }

               if (var9 != null && !var4.contains(var9)) {
                  var4.addElement(var9);
               }

               Element var10 = var8.getChild(g("0\f}F]-\u0012"));
               if (var10 != null) {
                  List var11 = var10.getChildren(g("*8@k`\u00152k{`\u0014\fBmz\u0015$a`|\r"));
                  ListIterator var12 = var11.listIterator();

                  label111:
                  do {
                     var29 = var12.hasNext();

                     while(true) {
                        if (!var29) {
                           break label111;
                        }

                        Element var13 = (Element)var12.next();
                        List var14 = var13.getChildren(g("?\u0013bD"));
                        var10000 = var14.listIterator();
                        if (var18 != 0) {
                           continue label128;
                        }

                        ListIterator var15 = var10000;

                        while(true) {
                           if (!var15.hasNext()) {
                              continue label111;
                           }

                           Element var16 = (Element)var15.next();
                           String var17 = var16.getAttributeValue(g("\u0014.I|c\u001c"));
                           var29 = var5.contains(var17);
                           if (var18 != 0) {
                              break;
                           }

                           if (!var29) {
                              var5.addElement(var17);
                           }

                           if (var18 != 0) {
                              continue label111;
                           }
                        }
                     }
                  } while(var18 == 0);
               }

               Element var23 = var8.getChild(g("4.I|c\u001c\u0013Ho|"));
               if (var23 == null) {
                  break;
               }

               List var25 = var23.getChildren(g("4.I|c\u001c\u0013Ho"));
               ListIterator var26 = var25.listIterator();

               while(true) {
                  if (!var26.hasNext()) {
                     break label125;
                  }

                  Element var27 = (Element)var26.next();
                  String var28 = var27.getAttributeValue(g("\u0017 @l"));
                  var29 = var5.contains(var28);
                  if (var18 != 0) {
                     break;
                  }

                  if (!var29) {
                     var5.addElement(var28);
                  }

                  if (var18 != 0) {
                     break label125;
                  }
               }
            }

            if (var18 == 0) {
               continue label131;
            }
            break;
         }

         var30 = var5.clone();
         break;
      }

      Vector var19 = (Vector)var30;
      Enumeration var20 = var5.elements();

      Vector var31;
      label79:
      while(true) {
         if (var20.hasMoreElements()) {
            String var21 = (String)var20.nextElement();
            var31 = var4;
            if (var18 != 0) {
               break;
            }

            if (var4.contains(var21) || var1.contains(var21)) {
               while(var19.contains(var21)) {
                  var19.removeElement(var21);
                  if (var18 != 0 && var18 != 0) {
                     continue label79;
                  }
               }
            }

            if (var18 == 0) {
               continue;
            }
         }

         var31 = var19;
         break;
      }

      Enumeration var22 = var31.elements();

      int var32;
      while(true) {
         if (var22.hasMoreElements()) {
            String var24 = (String)var22.nextElement();
            var32 = var3 + this.a(var24, var1);
            if (var18 != 0) {
               break;
            }

            var3 = var32;
            var2.addElement(var24);
            if (var18 == 0) {
               continue;
            }
         }

         var32 = var3;
         break;
      }

      return var32;
   }

   int a(String var1, Vector var2) {
      int var12 = Message.d;
      String var3 = var1 + "," + var1.toLowerCase() + "," + var1.toUpperCase();
      StringTokenizer var4 = new StringTokenizer(var3, ",", false);

      label98:
      while(true) {
         StringTokenizer var10000 = var4;

         label95:
         while(var10000.hasMoreTokens()) {
            String var5 = var4.nextToken();
            if (var12 != 0) {
               return 0;
            }

            StringTokenizer var6 = new StringTokenizer(this.f, g("Bm"), false);

            label92:
            do {
               int var17 = var6.hasMoreTokens();

               while(true) {
                  if (var17 == 0) {
                     break label92;
                  }

                  String var7 = var6.nextToken();
                  var10000 = new StringTokenizer(this.g, g("Bm"), false);
                  if (var12 != 0) {
                     continue label95;
                  }

                  StringTokenizer var8 = var10000;

                  while(true) {
                     if (!var8.hasMoreTokens()) {
                        continue label92;
                     }

                     String var9 = var8.nextToken();
                     var17 = var9.length();
                     if (var12 != 0) {
                        break;
                     }

                     if (var17 != 0) {
                        var9 = "." + var9;
                     }

                     String var10 = var7 + "/" + var5 + var9;
                     this.v.debug(g("84Yfc\u0016 I3/\n$L{l\u0011(Cn/\u001f._3/\"") + var10 + "]");

                     try {
                        label110: {
                           if (var10.startsWith(g("\u001f(Al5")) || var10.startsWith(g("\u0013 _3")) || var10.startsWith(g("\u00115Yy5")) || var10.startsWith(g("\u001f5]3"))) {
                              URL var11 = new URL(var10);
                              var11.openStream();
                              if (var12 == 0) {
                                 break label110;
                              }
                           }

                           File var16 = new File(var10);
                           if (!var16.exists() && (C == null ? (C = f(g("\u0014.Co`\u0001oYf`\u0015*D}!\n/@y!\u0014$Yhk\u00185L'h\u001c/\u0003Za\u00141``m>$C"))) : C).getResourceAsStream(var10) == null) {
                              throw new IOException("");
                           }
                        }

                        this.a(var10, 2, this.j);
                        if (this.b(var1)) {
                           return 0;
                        }
                     } catch (MalformedURLException var13) {
                     } catch (IOException var14) {
                     } catch (Exception var15) {
                        this.v.error(g("84Yfc\u0016 I)J\u000b3B{5Y") + var15.getMessage());
                        var2.addElement(var1);
                        this.a((ErrorMessage)(new ErrorMessage.ModuleLoadFailed(var1, g(":.XekY\u000fB}/5.Lm"))));
                        return 1;
                     }

                     if (var12 != 0) {
                        continue label92;
                     }
                  }
               }
            } while(var12 == 0);

            if (var12 == 0) {
               continue label98;
            }
            break;
         }

         this.a((ErrorMessage)(new ErrorMessage.ModuleLoadFailed(var1, g(":.XekY\u000fB}/5.Lm"))));
         var2.addElement(var1);
         return 0;
      }
   }

   public void setListener(SnmpMibGenListener var1) {
      this.t = var1;
   }

   public SnmpMetadata getMetadata() {
      return this.y;
   }

   public void saveAsBean(String var1) throws IOException, MibGenException {
      this.a(var1, false);
   }

   public void saveAsBean(OutputStream var1) throws IOException, MibGenException {
      this.a(var1, false);
   }

   void a(String var1, boolean var2) throws IOException, MibGenException {
      FileOutputStream var3 = new FileOutputStream(var1);
      this.a((OutputStream)var3, var2);
      var3.close();
   }

   void a(OutputStream var1, boolean var2) throws IOException, MibGenException {
      SnmpMetadata var3 = this.getMetadata();
      if (var3 == null) {
         throw new MibGenException(g("4$Yhk\u00185L)a\u00165\r\u007fn\u0015(Ih{\u001c%"));
      } else {
         ObjectOutputStream var4 = new ObjectOutputStream(var1);
         var4.writeObject(var3);
      }
   }

   public void saveAsXML(String var1) throws IOException {
      this.d(var1);
   }

   public void saveAsXML(OutputStream var1) throws IOException {
      this.a((Element)null, (OutputStream)var1);
   }

   void c(String var1) throws IOException {
      int var13 = Message.d;

      try {
         HTMLMIBOutputter var2 = new HTMLMIBOutputter();
         File var3 = new File(var1);
         var2.setDirectory(var3);
         var2.preProcess();
         List var4 = this.r.getChildren(g("4.I|c\u001c"));
         ListIterator var5 = var4.listIterator();

         while(var5.hasNext()) {
            Element var6 = (Element)var5.next();
            String var7 = var6.getAttributeValue(g("\u0017 @l"));
            String var8 = var7 + g("W)Ydc");
            File var9 = new File(var3, var8);
            this.a((InfoMessage)(new InfoMessage.SavingFile(var9.toString(), g(">$Cl}\u00185DghY}e]B5a`@MG"))));
            Document var10 = new Document((Element)null);
            var10.setRootElement(var6);
            DOMOutputter var11 = new DOMOutputter();
            org.w3c.dom.Document var12 = var11.output(var10);
            var2.output(var12, var8);
            if (var13 != 0) {
               return;
            }

            if (var13 != 0) {
               break;
            }
         }

         var2.postProcess();
      } catch (MIBOutputterException var14) {
         this.a((ErrorMessage)(new ErrorMessage.GenericWarning(g("1\u0015`E/?(Al/>$Cl}\u00185DfaY\u0004_{`\u000bi") + var14.getMessage())));
         this.v.debug(g("1\u0015`E/?(Al/>$Cl}\u00185DfaY\u0004_{`\u000b"), var14);
      } catch (JDOMException var15) {
         this.a((ErrorMessage)(new ErrorMessage.GenericWarning(g("1\u0015`E/?(Al/>$Cl}\u00185DfaY\u0004_{`\u000bi") + var15.getMessage())));
         this.v.debug(g("1\u0015`E/?(Al/>$Cl}\u00185DfaY\u0004_{`\u000b"), var15);
      } catch (NoClassDefFoundError var16) {
         this.a((ErrorMessage)(new ErrorMessage.GenericWarning(g("1\u0015`E/>$Cl}\u00185DfaY\u0007L`c\f3H3/\u0014(^zf\u0017&\rQN5\u0000c)`\u000ba_lc\u00185Hm/\n4]y`\u000b5\ref\u001b3L{f\u001c2\r!") + var16.getMessage())));
         this.v.debug(g("1\u0015`E/>$Cl}\u00185DfaY\u0007L`c\f3H3/\u0014(^zf\u0017&\rQN5\u0000c)`\u000ba_lc\u00185Hm/\n4]y`\u000b5\ref\u001b3L{f\u001c2"), var16);
      }

   }

   void d(String var1) throws IOException {
      int var10 = Message.d;
      File var2 = new File(var1);
      if (var2.isDirectory()) {
         List var3 = this.r.getChildren(g("4.I|c\u001c"));
         ListIterator var4 = var3.listIterator();

         while(var4.hasNext()) {
            Element var5 = (Element)var4.next();
            String var6 = var5.getAttributeValue(g("\u0017 @l"));
            String var7 = var6 + g("W9@e");
            File var8 = new File(var2, var7);
            FileOutputStream var9 = new FileOutputStream(var8);
            this.a((InfoMessage)(new InfoMessage.SavingFile(var8.toString(), g("* [`a\u001ea\u0011QB5a`l{\u0018%L}nG"))));
            this.a((Element)var5, (OutputStream)var9);
            var9.close();
            if (var10 != 0) {
               return;
            }

            if (var10 != 0) {
               break;
            }
         }

         if (var10 == 0) {
            return;
         }
      }

      this.a((InfoMessage)(new InfoMessage.SavingFile(var2.toString(), g("* [`a\u001ea\u0011QB5a`l{\u0018%L}nG"))));
      FileOutputStream var11 = new FileOutputStream(var2);
      this.a((Element)null, (OutputStream)var11);
      var11.close();
   }

   public void saveAsXML(String var1, String var2) throws IOException {
      int var7 = Message.d;
      if (var1 != null && var1.length() != 0) {
         Element var3 = null;
         Iterator var4 = this.r.getChildren(g("4.I|c\u001c")).iterator();

         while(true) {
            if (var4.hasNext()) {
               Element var5 = (Element)var4.next();
               if (var7 != 0) {
                  break;
               }

               if (var1.equals(var5.getAttributeValue(g("\u0017 @l")))) {
                  var3 = var5;
               }

               if (var7 == 0) {
                  continue;
               }
            }

            if (var3 == null) {
               return;
            }
            break;
         }

         File var8 = new File(var2);
         if (var8.isDirectory()) {
            var8 = new File(var8, var1 + g("W9@e"));
         }

         this.a((InfoMessage)(new InfoMessage.SavingFile(var8.toString(), g("* [`a\u001ea\u0011QB5a`l{\u0018%L}nG"))));
         FileOutputStream var6 = new FileOutputStream(var8);
         this.a((Element)var3, (OutputStream)var6);
         var6.close();
      }
   }

   void a(Element var1, OutputStream var2) throws IOException {
      Document var3 = new Document((Element)null);
      var3.addContent(new Comment(g("-)Dz/\u00102\rhaY X}`\u0014 Y`l\u0018-Ap/\u001e$Cl}\u00185Hm/\u001f(Al!")));
      var3.addContent(new Comment(g("=.\rg`\rahmf\r`")));
      var3.addContent(new Comment("[" + new Date() + "]"));
      XMLOutputter var4;
      if (var1 == null) {
         var3.setDocType(new DocType(g("*/@yB\u001c5Lmn\r "), this.z));
         var3.setRootElement(this.r);
         var4 = new XMLOutputter(g("Ya\r"), true);
         var4.output(var3, var2);
         if (Message.d == 0) {
            return;
         }
      }

      var3.setDocType(new DocType(g("4.I|c\u001c"), this.z));
      var3.setRootElement(var1);
      var4 = new XMLOutputter(g("Ya\r"), true);
      var4.output(var3, var2);
   }

   public void saveAsXMIB(String var1) throws IOException {
      this.e(var1);
   }

   public void saveAsXMIB(OutputStream var1) throws IOException {
      this.a(var1);
   }

   void e(String var1) throws IOException {
      FileOutputStream var2 = new FileOutputStream(var1);
      this.a((OutputStream)var2);
      var2.close();
   }

   void a(OutputStream var1) throws IOException {
      Document var2 = new Document(this.q);
      XMLOutputter var3 = new XMLOutputter(g("Ya\r"), true);
      var3.output(var2, var1);
   }

   private static void b() {
      System.out.println(g("saxZN>\u0004'\u0003\u0005Ya\r)/\u0013 [h/\u0014.Co`\u0001oYf`\u0015*D}!\n/@y!4 Dg/*/@yB\u0010#jlaY\u001aBy{\u0010.CzRY}@`mT'Dej\n\u007f\u0006)!Wo'\u0003/Ya\r)e\u00187L)\"\u0013 _)k\n/@y\"\u0014&_'e\u00183\rZa\u00141``m>$C)T\u00161Y``\u00172p)3\u0014(O$i\u0010-Hz1Ra\u0003'!sK\r)/YaGhy\u0018a\u0000cn\u000baIza\u00141\u0000hh\u001c/Y'e\u00183\rZa\u00141``m>$C)T\u00161Y``\u00172p)3\u0014(O$i\u0010-Hz1Ra\u0003'!sK')/Ya\rcn\u000f \rd`\u0017'Bq!\r.Bed\u00105\u0003za\u00141\u0003dj\r Ih{\u0018oJlaW\u0012Cd\u007f4(ONj\u0017avf\u007f\r(Bg|$a\u0011df\u001blK`c\u001c2\u0013\"/Wo\u0003\u0003\u0005Y\u0005hZL+\b}]F6\u000f'\u0003/Ya\r)[\u0011$\rZa\u00141``m>$C)z\r(A`{\u0000aAfn\u001d2\rZA4\u0011\rDF;aIli\u0010/D}f\u0016/\rof\u0015$^\u0003/Ya\r)n\u0017%\rj`\u00177H{{\naYaj\u0017aDg{\u0016aipa\u0018,Dj\\7\f})b\u001c5Lmn\r \u0003)[\u0011(^)b\u001c5Lmn\r ')/Ya\rdn\u0000aOl/\u0010/\r}g\u001caKf}\u0014aBo/\u001c(Yaj\u000baLg/\u00161Y`b\u0010;Hm/!\fa)b\u001c5Lmn\r \u0001)\u0005Ya\r)/\u0018a^l}\u0010 A`u\u001c%\rdj\r Ih{\u0018aOln\u0017aB{/\u0018a^l}\u0010 A`u\u001c%\rFF=aY{j\u001co'\u0003/Ya\r)F\raLe|\u0016aNhaY&Hgj\u000b Yl/\u0018/\rQB5a`@MY3Hy}\u001c2Hg{\u00185DfaY6E`l\u0011aDz/\u0018K\r)/YaBgjT5B$`\u0017$\rdn\t1DghY.K)\\7\f})B0\u0003\rz{\u000b4N}z\u000b A)l\u0016,]fa\u001c/Yz/\u0010/Yf/sa\r)/Y\u0019`E/\u001c-Hdj\u00175^'/-)Dz/\u001f._dn\ra@hvY#H)z\n$K|cY'B{/\u001a-Dla\raLy\u007f\u0015(Nh{\u0010.Cz\u0005Ya\r)/\u000e)DjgY3Hxz\u00103H)\\7\f})B0\u0003\rba\u00166Alk\u001e$\rkz\raIfa^5\r~n\u00175\r}`Y(Cjc\f%H)nYK\r)/YaK|c\u0015aOe`\u000e/\rDF;aNfb\t(Al}WK')/Ya\r@aY2BdjY\"Lzj\nm\rjj\u000b5L`aY.X}k\u00185Hm/0\f}F]-\u0012\rjc\u00184^l|Y,Lp/\u000b$Kl}\u001c/Nl/sa\r)/Y\fdK/\u001c-Hdj\u00175^)x\u0011(Na/\u00183H)i\u00164Cm/\u0010/\rh/\u0017$Zl}V4]mn\r$I)b\u0016%XejWadg/sa\r)/Y5El|\u001caNh|\u001c2\rh/\u000e _gf\u0017&\r~f\u0015-\rfl\u001a4_)f\u001faYajY3Hoj\u000b$Cjj\u001da@fk\f-H)f\na')/Ya\rmf\u001f'H{j\u00175\ro}\u0016,\r}g\u001caLj{\f A)b\u0016%XejY\"Bg{\u0018(C`a\u001eaYajY$Alb\u001c/Y'\u0005Ya\r)/0/\rf}\u001d$_){\u0016a]{j\u000f$C}/\r)Dz/\u001c3_f}Y8B|/\u0014 T)\u007f\u000b.[`k\u001caLg/\u0018-Dh|\u001c2\r\u0003/Ya\r)i\u0010-H)x\u0011(Na/\u0014 ]z/\u0016-I)b\u0016%XejY/Ldj\naYf/\u0017$Z&z\t%L}j\u001da@fk\f-H)a\u0018,Hz!sa\r)/Y\u0015E`|Y'DejY(^)nY\u000bL\u007fnY1_f\u007f\u001c3Y`j\naK`c\u001caZ`{\u0011aYajY'Bec\u00166DghY'B{b\u00185'\u0003/Ya\r)/Ya{HC0\u0005\u0000D@=p\u0010KN=l`FKHmoHKT\fbM=Uo\u0003'\u0005Ya\r)/Ya\r_N5\bi$B6\u0005\u001f4M8\u0005\u0000D@=r\u0001KN=l`FKJm\u0003'!sK\r)/Yayaf\naZ`c\u0015a_l|\f-Y)f\u0017aLgvY3Hoj\u000b$Cjj\naYf/\r)H)M8\u0005\rd`\u001d4Al/\r.\r{j\n.A\u007fjsa\r)/Y5B){\u0011$\r_N5\bi$B6\u0005\u0003)F\u0017aB{k\u001c3\r}`Y1_fy\u0010%H){\u0011(^)n\u0015(Lz/\u001f(Al/\r.\r}g\u001ca')/Ya\rj`\u00141Dej\u000bm\ryc\u001c ^l/\f2H){\u0011$\r.\"\u0018-Dh|\u001c2\n)`\t5DfaWK')/Ya\rG`\r$\u0017)F\u0017a@f|\raNh|\u001c2\rp`\faZ`c\u0015aXzjY5El/^lUdc^aBy{\u0010.C){\u0016aJla\u001c3L}jsa\r)/Ya\r)/YaYajY\u0019`E/\u0014$Yhk\u00185L'/0'\rp`\fa^|\u007f\t-T)nY%D{j\u001a5B{vY/LdjY ^)n\u0017K\r)/Ya\r)/Ya\rh}\u001e4@la\raYf/\r)H)(T9@e(Y.]}f\u0016/\u0001){\u0011$C)\\\u0017,]Df\u001b\u0006Hg/\u000e(Ae/\u001e$Cl}\u00185H\u0003/Ya\r)/Ya\r)/\u0018a^l\u007f\u00183L}jY\u0019`E/\u0014$Yhk\u00185L)i\u0010-H)i\u00163\rln\u001a)\rd`\u001d4Al/\u000e)Djgsa\r)/Ya\r)/YaZh|Y\"Bd\u007f\u0010-Hm!sK\rF_-\bbG\\sa\r)/Yl\u0012ug\u001c-])/Ya\r)/Ya\r)/Ya\u0017)\u007f\u000b(C}/\r)Dz/\u0014$^zn\u001e$')/Ya\r$~\"4Dl{$a\r)/Ya\r)/Ya\r)5Y%Dzn\u001b-H)x\u00183C`a\u001e2\r)/Ya\r)/Ya\rR`\u001f'p\u0003/Ya\r)\"\u001d$^j}\u00101Y``\u0017a\r)/Ya\r)/CaDgl\u00154Il/=\u0004~J]0\u0011y@@7aYlw\ra\r)T\u001f Azj$K\r)/Ya\u0000hc\u0010 ^l|Y}K`c\u001c\u007f\r)/Ya\r3/\u0014.I|c\u001caLef\u00182Hz/\t3Byj\u000b5Dl|Yavg`\u0017$p\u0003/Ya\r)\"\u0010\u001aJg`\u000b$p)/Ya\r)/Ya\r)/CaDna\u00163H)j\u000b3B{|Ya\r)/Ya\r)/Ya\r)T\u0016'KT\u0005Ya\r)/T-Lq/Ya\r)/Ya\r)/Ya\r)/Y{\rg`\u0017l^}}\u0010\"Y)y\u0018-Dmn\r(Bg/Ya\r)/\".KoRsa\r)/Yl[Rj\u000b#Bzj$a\r)/Ya\r)/Ya\u0017)j\u0017 OejY7H{m\u00162H)b\u0016%H)/Ya\r)/Y\u001aI`|\u0018#Alk$K\r)/Ya\u0000mT\u001c#XnRYa\r)/Ya\r)/Ya\r3/\u001c/Lkc\u001caIlm\f&\rd`\u001d$\r)/Ya\r)/Yavmf\n Oej\u001d\u001c'\u0003/Ya\r)\"\u0001\u001a@eRY}Kgn\u0014$\u0013)/Ya\r)/CauDCY.X}\u007f\f5\rof\u0015$\rf}Y%D{j\u001a5B{vsa\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)'\u0010'\rmf\u000b$N}`\u000b8\u0001){\u0011$C)f\u0017%D\u007ff\u001d4Le/\u0014.I|c\u001cK\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)/Y'Dej\naZ`c\u0015aOl/\u001e$Cl}\u00185Hm/\u0010/\r}g\u001caI`}Wh'\u0003/Ya\r)\"\u0017\u001aLdj$a\r5a\u0018,H7/Ya\r)/CaJla\u001c3L}j\u001da@l{\u0018%L}nY/Ldjsa\r)/Yl@Rj\r Ih{\u0018\u001c\r5i\u0017 @l1Ya\u0017)|\u001c3Dhc\u0010;Hm/\u0014$Yhk\u00185L)i\u0010-H\u0003\u0005Ya\r)/T v|{\u0016-Bhk$a\r)/Ya\r)/Y{\rhz\r.\re`\u0018%\rDF;aK`c\u001c2\ro}\u0016,\r+\u007f\u00185E+\u0005Ya\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)/Ya\rf\u007f\r(Bg/\u000e)H{jY'Dej\u0017 @l|Y,Xz{sa\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)g\u00187H){\u0011$\rzn\u0014$\rkn\n$\r)n\naYajsa\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)B0\u0003\rd`\u001d4Al!Y\u0015E`|Y6DecY Y}j\u00141Y){\u0016aL|{\u0016-Bhksa\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)n\u00178\rDF;a@fk\f-Hz/\u000e)DjgY6H{jY/B}/\u0017 @lkY.C)\u0005Ya\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r}g\u001caNfb\u0014 Cm/\u0015(Cl!Ya\r)/Ya\r)/\".KoRsK\r)/Ya\u0000d`\u001d4Al|Ya\r)/Ya\r)/Ya\r3/\n1Hjf\u001f(Hz/\r)L}/\r)H)c\u00102Y)`\u001faL{h\f,Hg{\nK\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)/\u00183H)b\u0016%XejY/Ldj\na_h{\u0011$_){\u0011 C)i\u0010-H)a\u0018,Hz!sa\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)[\u0011$\rd`\u001d4Al|Y6DecY#H)c\u0016 IlkY'_fbY5El/\t Ya\u0005Ya\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)/Ya\rz\u007f\u001c\"Dof\u001c%\r~f\r)\r}g\u001ca\u0000yn\r)\rf\u007f\r(Bg\u0005sa\r)/Yl]Rn\r)p)3\t Ya5Ga\r)/Ya\u0017)c\u00102Y)`\u001fa\u000f%-Y._)-Bc\rzj\t _h{\u001c%\rDF;K\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)/\u001d(_ll\r._`j\naB{/,\u0013az/\r)L}/\u00102\r||\u001c%\r~f\r)\r\u0003/Ya\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)/Ya\u000f$n\f5Be`\u0018%\u000f'/8-^f/\u0017.Yl/\r)L}/\r)H)\u007f\u00185E)\u0005Ya\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)/Ya\rdn\u0000aNfa\r Dg/\u001d(_ll\r._`j\naNfa\r Dgj\u001daDg/\r)H\u0003/Ya\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)/YanEN*\u0012}H[1o\r)/Ya\r)/Ya\r)/Ya\r)T[o\u0017!e\u00183K`c\u001ch\u000fT\u0005sa\r)/YlHRw\r\u001c\r5j\u00015Hg|\u0010.Cz5Ga\u0017)c\u00102Y)`\u001fa\u000f%-Y2Hyn\u000b YlkY\fdK\u0005Ya\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)/Ya\rof\u0015$\rlw\r$Czf\u0016/^){\u0011 Y)f\naXzj\u001dK\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)/\u000e(Ya/[lL|{\u0016-Bhk[a\r)/Ya\r)/Yav+b\u0010#\u0001}w\rcp\u0003\u0005Ya\r)/T%Ym/E%Ym\"\f3A7/Ya\r)/Y{\re`\u001a Y``\u0017aBo/\r)H)(\n/@yB\u001c5Lmn\r \u0003m{\u001df\rof\u0015$\u0003\u0003/Ya\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)/YaOp/\u001d$Khz\u00155\r}g\u00102\r\u007fn\u00154H)}\u001c'H{|Y5B){\u0011$\rM[=K\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)/\u0015.Nh{\u001c%\r`aY5El/=8Chb\u0010\"~GB)agH]Y'Dej\no'\u0003/Ya\r)\"\u00115@e/E%Hz{T%D{1Ya\r)/CaJla\u001c3L}jY\tyDCY'Dej\naKf}Y5El/\u001a.@yf\u0015$I)B0\u0003')/Ya\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)/Y,Bmz\u0015$^)n\u0017%\r~}\u00105H){\u0011$@){\u0016aYajY/Ldj\u001daI`}\u001c\"Yf}\u0000o')/Ya\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)/Y\u0015E`|Y6DecY&Hgj\u000b Yl/\r)H)n\n2Bjf\u00185Hm/\u0010/IlwY'Dej\nK\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)/Q(Cmj\u0001oE}b\u0015m\rl{\u001ah\rh|Y6HecWK')/Ya\r$}\u0010\"E)/Ya\r)/Ya\r)/Ya\r)5Y$Chm\u0015$\r{f\u001a)\rd`\u001d$\u0003)N\u001d%^)j\u00015_h/\u0010/Kf}\u0014 Y``\u0017K\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)/\r.\r}g\u001caJla\u001c3L}j\u001dauDCWaxzj\u001daZ`{\u0011ae]B5aB|{\t4Y)\u0005Ya\r)/Ya\r)/Ya\r)/Ya\r)/Ya\r)/Ya\rnj\u0017$_h{\u0010.C)T\u0016'KT"));
   }

   public void setMetadataName(String var1) {
      this.d = var1;
   }

   public String getMetadataName() {
      return this.d;
   }

   public void setLoadPath(String var1) {
      this.f = var1;
   }

   public String getLoadPath() {
      return this.f;
   }

   public void setAliasesFile(String var1) {
      this.e = var1;
   }

   public String getAliasesFile() {
      return this.e;
   }

   public void setExtensions(String var1) {
      this.g = var1;
   }

   public String getExtensions() {
      return this.g;
   }

   public void isDebugOn(boolean var1) {
      this.h = var1;
   }

   public boolean isDebugOn() {
      return this.h;
   }

   public void isVerbose(boolean var1) {
      this.i = var1;
   }

   public boolean isVerbose() {
      return this.i;
   }

   public void isWarning(boolean var1) {
      this.l = var1;
   }

   public boolean isWarning() {
      return this.l;
   }

   public void isStrict(boolean var1) {
      this.j = var1;
   }

   public boolean isStrict() {
      return this.j;
   }

   public void setOptimizationLevel(int var1) {
      this.p = var1;
   }

   public int getOptimizationLevel() {
      return this.p;
   }

   public void useDescription(boolean var1) {
      this.m = var1;
   }

   public boolean useDescription() {
      return this.m;
   }

   public Result getCumulativeResult() {
      return this.x;
   }

   // $FF: synthetic method
   static Class f(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw (new NoClassDefFoundError()).initCause(var2);
      }
   }

   static {
      bn.getInstance();
   }

   private static String g(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 121;
               break;
            case 1:
               var10003 = 65;
               break;
            case 2:
               var10003 = 45;
               break;
            case 3:
               var10003 = 9;
               break;
            default:
               var10003 = 15;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   private class DefaultSnmpMibGenListener implements SnmpMibGenListener {
      private DefaultSnmpMibGenListener() {
      }

      public void handleError(ErrorMessage var1, Object var2) {
         switch (var1.getSeverity()) {
            case 1:
            case 2:
            case 3:
               System.out.println("");
               System.out.println(var1);
               if (Message.d == 0) {
                  break;
               }
            default:
               if (SnmpMibGen.this.l) {
                  System.out.println("");
                  System.out.println(var1);
               }
         }

         SnmpMibGen.this.v.error(var1.toString());
      }

      public void handleInfo(InfoMessage var1, Object var2) {
         int var3 = Message.d;
         switch (var1.getType()) {
            case 53:
               SnmpMibGen.this.v.debug(var1.toString());
               if (var3 == 0) {
                  break;
               }
            case 57:
               SnmpMibGen.this.v.info(var1.toString());
               if (!SnmpMibGen.this.i) {
                  break;
               }

               System.out.println(var1);
               if (var3 == 0) {
                  break;
               }
            default:
               SnmpMibGen.this.v.info(var1.toString());
               System.out.println(var1);
         }

      }

      // $FF: synthetic method
      DefaultSnmpMibGenListener(Object var2) {
         this();
      }
   }
}
