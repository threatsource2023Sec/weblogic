package monfox.toolkit.snmp.metadata.output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.metadata.RangeItem;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.SnmpModule;
import monfox.toolkit.snmp.metadata.SnmpModuleIdentityInfo;
import monfox.toolkit.snmp.metadata.SnmpNotificationGroupInfo;
import monfox.toolkit.snmp.metadata.SnmpNotificationInfo;
import monfox.toolkit.snmp.metadata.SnmpObjectGroupInfo;
import monfox.toolkit.snmp.metadata.SnmpObjectInfo;
import monfox.toolkit.snmp.metadata.SnmpOidInfo;
import monfox.toolkit.snmp.metadata.SnmpTableEntryInfo;
import monfox.toolkit.snmp.metadata.SnmpTableInfo;
import monfox.toolkit.snmp.metadata.SnmpTypeInfo;
import monfox.toolkit.snmp.util.TextBuffer;

public class MibOutputter {
   private boolean a;
   private int b;
   private boolean c;
   private static Properties d = new Properties();
   private static Properties e;
   private static String f = c("'4h\r\u007f'4h\r\u007f'4h\r\u007f'4h\r\u007f'4h\r\u007f'4h\r\u007f'4h\r\u007f'4h\r\u007f'4h\r\u007f'4h");
   private static SnmpOid g = new SnmpOid(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 1L, 1L, 5L});
   // $FF: synthetic field
   static Class h;
   public static boolean i;

   public MibOutputter() {
      this(1);
   }

   public MibOutputter(int var1) {
      this.a = true;
      this.b = 1;
      this.c = false;
      this.b = var1;
   }

   public void setResolveParent(boolean var1) {
      this.a = var1;
   }

   public void output(SnmpMetadata var1, OutputStream var2) throws IOException, SnmpValueException {
      this.output(var1, new PrintStream(var2, true));
      var2.flush();
   }

   public void output(SnmpMetadata var1, String var2) throws IOException, FileNotFoundException, SnmpValueException {
      this.output(var1, new File(var2));
   }

   public void output(SnmpMetadata var1, File var2) throws IOException, FileNotFoundException, SnmpValueException {
      FileOutputStream var3 = new FileOutputStream(var2);
      this.output(var1, (OutputStream)var3);
      var3.flush();
      var3.close();
   }

   public void output(SnmpMetadata var1, PrintStream var2) throws SnmpValueException {
      TextBuffer var3 = new TextBuffer(var2);
      this.a(var1, var3);
   }

   public TextBuffer output(SnmpMetadata var1) throws SnmpValueException {
      TextBuffer var2 = new TextBuffer();
      this.a(var1, var2);
      return var2;
   }

   public void outputModule(SnmpMetadata var1, String var2, OutputStream var3) throws IOException, SnmpValueException {
      this.outputModule(var1, var2, new PrintStream(var3, true));
      var3.flush();
   }

   public void outputModule(SnmpMetadata var1, String var2, String var3) throws IOException, FileNotFoundException, SnmpValueException {
      this.outputModule(var1, var2, new File(var3));
   }

   public void outputModule(SnmpMetadata var1, String var2, File var3) throws IOException, FileNotFoundException, SnmpValueException {
      if (this.c) {
         System.out.println(c("@q&H-f`!C8'O") + var2 + c("')v\r") + var3.getName() + "]");
      }

      FileOutputStream var4 = new FileOutputStream(var3);
      this.outputModule(var1, var2, (OutputStream)var4);
      var4.flush();
      var4.close();
   }

   public void outputModule(SnmpMetadata var1, String var2, PrintStream var3) throws SnmpValueException {
      TextBuffer var4 = new TextBuffer(var3);
      SnmpModule var5 = var1.getModule(var2);
      if (var5 == null) {
         throw new SnmpValueException(c("I{h~*d|h~1jd\u0005B;rx-\u0017\u007f") + var2);
      } else {
         this.a(var1, var5, var4);
      }
   }

   public TextBuffer outputModule(SnmpMetadata var1, String var2) throws SnmpValueException {
      TextBuffer var3 = new TextBuffer();
      SnmpModule var4 = var1.getModule(var2);
      if (var4 == null) {
         throw new SnmpValueException(c("I{h~*d|h~1jd\u0005B;rx-\u0017\u007f") + var2);
      } else {
         this.a(var1, var4, var3);
         return var3;
      }
   }

   public void outputModuleFiles(SnmpMetadata var1, String var2, String var3, boolean var4) throws SnmpValueException, IOException {
      boolean var10 = i;
      SnmpModule[] var5 = var1.getModules();
      int var6 = 0;

      while(var6 < var5.length) {
         SnmpModule var7;
         String var8;
         label29: {
            var7 = var5[var6];
            var8 = "";
            if (var2 != null && var2.length() > 0) {
               var8 = var2;
               if (!var2.endsWith(File.separator)) {
                  var8 = var2 + File.separator;
               }

               var8 = var8 + var7.getName() + "." + var3;
               if (!var10) {
                  break label29;
               }
            }

            var8 = var7.getName() + "." + var3;
         }

         File var9 = new File(var8);
         if (var9.exists() && !var4) {
            throw new IOException(c("Du&C0s4\u0007[:uc:D+b4.D3b.h") + var8);
         }

         this.outputModule(var1, var7.getName(), var9);
         ++var6;
         if (var10) {
            break;
         }
      }

   }

   private void a(SnmpMetadata var1, TextBuffer var2) throws SnmpValueException {
      var2.append((Object)c("*9hj\u001aIQ\u001al\u000bBPh`\u0016E4\u000ed\u0013B:hi\u0010'Z\u0007y\u007fBP\u0001y\u007f*9B"));
      SnmpModule[] var3 = var1.getModules();
      int var4 = 0;

      while(var4 < var3.length) {
         this.a(var1, var3[var4], var2);
         var2.append((Object)c("\r\u001e"));
         ++var4;
         if (i) {
            break;
         }
      }

   }

   private void a(SnmpMetadata var1, SnmpModule var2, TextBuffer var3) throws SnmpValueException {
      boolean var11 = i;
      Context var4 = new Context(var2);
      TextBuffer var5 = new TextBuffer();
      if (var2.getIdentity() != null && this.b == 1) {
         SnmpModuleIdentityInfo var6 = var2.getIdentity();
         var5.append((Object)var6.getName()).append((Object)c("'Y\u0007i\nKQed\u001bBZ\u001cd\u000b^"));
         var5.pushIndent();
         var5.append((Object)c("KU\u001byrRD\fl\u000bBPh\u000f")).append((Object)var6.getLastUpdated());
         var5.append((Object)(c("%\u001e\u0007\u007f\u0018FZ\u0001w\u001eS]\u0007c\u007f%") + var6.getOrganization()));
         var5.append((Object)(c("%\u001e\u000bb\u0011SU\u000byrNZ\u000eb\u007f%") + var6.getContactInfo()));
         var5.append((Object)c("%\u001e\fh\fDF\u0001}\u000bN[\u0006\r}")).append((Object)var6.getDescription());
         var5.append((Object)c("%\u001e"));
         var5.append((Object)c("=.u\r")).append((Object)this.a(var1, (SnmpOidInfo)var6, true, var4));
         var5.append((Object)"\n");
         var5.popIndent();
         var5.append((Object)"\n");
         var4.uses(c("TZ\u0005})59\u001b`\u0016"), c("J[\fx\u0013B9\u0001i\u001aI@\u0001y\u0006"));
      }

      SnmpTypeInfo[] var12 = var2.getTypes();
      int var7 = 0;

      while(var7 < var12.length) {
         this.a(var1, var5, var12[var7], var4);
         ++var7;
         if (var11) {
            break;
         }
      }

      SnmpOidInfo[] var13 = var2.getOidInfo();
      List var8 = this.a(var13);
      int var9 = 1;

      while(true) {
         if (var9 <= 5) {
            if (var11) {
               break;
            }

            int var10 = 0;

            do {
               if (var10 >= var8.size()) {
                  ++var9;
                  break;
               }

               this.a(var1, var5, (SnmpOidInfo)var8.get(var10), var4, var9);
               ++var10;
            } while(!var11 || !var11);

            if (!var11) {
               continue;
            }
         }

         var3.append((Object)var2.getName());
         var3.append((Object)c("'P\rk\u0016I]\u001cd\u0010IGh\u0017e:\u001e\nh\u0018NZB"));
         var3.pushIndent();
         this.a(var1, var3, var4);
         var3.append((Object)var5.toString());
         var3.popIndent();
         var3.append((Object)c("BZ\f'"));
         break;
      }

   }

   private List a(SnmpOidInfo[] var1) {
      boolean var8 = i;
      Vector var2 = new Vector();
      int var3 = 0;

      while(var3 < var1.length) {
         SnmpOidInfo var10000 = var1[var3];

         SnmpOidInfo var4;
         boolean var5;
         label62:
         while(true) {
            var4 = var10000;
            var5 = false;
            int var6 = 0;

            while(true) {
               if (var6 >= var2.size()) {
                  break label62;
               }

               SnmpOidInfo var7 = (SnmpOidInfo)var2.get(var6);
               var10000 = var4;
               if (var8) {
                  break;
               }

               label71: {
                  if (var4.getClass() == (h == null ? (h = b(c("j{&K0\u007f:<B0k\u007f!Yqtz%]qjq<L;f`)\u0003\fiy8b6c]&K0"))) : h)) {
                     if (var7.getClass() == (h == null ? (h = b(c("j{&K0\u007f:<B0k\u007f!Yqtz%]qjq<L;f`)\u0003\fiy8b6c]&K0"))) : h) && var7.getOid().compareTo(var4.getOid()) < 0) {
                        break label71;
                     }

                     var2.add(var6, var4);
                     var5 = true;
                     if (!var8) {
                        break label62;
                     }
                  }

                  if (var7.getClass() != (h == null ? (h = b(c("j{&K0\u007f:<B0k\u007f!Yqtz%]qjq<L;f`)\u0003\fiy8b6c]&K0"))) : h) && var7.getOid().compareTo(var4.getOid()) >= 0) {
                     var2.add(var6, var4);
                     var5 = true;
                     if (!var8) {
                        break label62;
                     }
                  }
               }

               ++var6;
               if (var8) {
                  break label62;
               }
            }
         }

         if (!var5) {
            var2.add(var4);
         }

         ++var3;
         if (var8) {
            break;
         }
      }

      return var2;
   }

   void a(SnmpMetadata var1, TextBuffer var2, Context var3) {
      boolean var11 = i;
      Hashtable var4 = var3.getImportMap();
      if (var4.size() > 0) {
         var2.append((Object)c("NY\u0018b\rSG"));
         var2.pushIndent();
         Enumeration var5 = var4.keys();

         label44: {
            label43:
            do {
               int var10000 = var5.hasMoreElements();

               String var6;
               label40:
               while(true) {
                  if (var10000 == 0) {
                     break label43;
                  }

                  var6 = (String)var5.nextElement();
                  var2.append((Object)"\n");
                  int var7 = 0;
                  Hashtable var8 = (Hashtable)var4.get(var6);
                  if (var11) {
                     break label44;
                  }

                  Enumeration var9 = var8.keys();

                  while(true) {
                     if (!var9.hasMoreElements()) {
                        break label40;
                     }

                     String var10 = (String)var9.nextElement();
                     var7 += var10.length();
                     var10000 = var7;
                     if (var11) {
                        break;
                     }

                     if (var7 > 60) {
                        var2.append((Object)"\n");
                        var7 = var10.length();
                     }

                     var2.append((Object)var10);
                     if (var9.hasMoreElements()) {
                        var2.append((Object)c("+4"));
                        var7 += 2;
                     }

                     if (var11) {
                        break label40;
                     }
                  }
               }

               var2.append((Object)c("'4h\r\u0019U[\u0005\r")).append((Object)var6).append((Object)"\n");
            } while(!var11);

            var2.popIndent();
         }

         var2.append((Object)c("<\u001eB"));
      }

   }

   protected void processSnmpTableInfo(SnmpMetadata var1, TextBuffer var2, SnmpTableInfo var3, Context var4) throws SnmpValueException {
      boolean var9;
      label54: {
         var9 = i;
         if (this.b == 0) {
            var4.uses(c("UR\u000b\u001cn2!e~\u0012N"), c("HV\u0002h\u001cS9\u001ct\u000fB"));
            if (!var9) {
               break label54;
            }
         }

         var4.uses(c("TZ\u0005})59\u001b`\u0016"), c("HV\u0002h\u001cS9\u001ct\u000fB"));
      }

      SnmpTableEntryInfo var5 = var3.getEntry();
      if (var5 == null) {
         throw new SnmpValueException(c("I{hY>ex-\r:i`:T\u007fa{:\r+fv$H\u007f ") + var3.getName());
      } else {
         label48: {
            String var6 = this.a(var5.getName());
            var2.append((Object)var3.getName()).append((Object)c("'[\ng\u001aD@ey\u0006WQ"));
            var2.pushIndent();
            var2.append((Object)c("TM\u0006y\u001e_4h\r\u007f'4\u001bh\u000eRQ\u0006n\u001a'[\u000e\r")).append((Object)var6).append((Object)"\n");
            if (this.b == 0) {
               var2.append((Object)c("FW\u000bh\fT4h\r\u007f'4&B+*u+N:tg!O3b\u001e"));
               if (!var9) {
                  break label48;
               }
            }

            var2.append((Object)c("JU\u0010\u0000\u001eDW\r~\f'4&B+*u+N:tg!O3b\u001e"));
         }

         label43: {
            this.a(var3.getStatus(), var2);
            if (var3.getDescription() == null) {
               var2.append((Object)c("CQ\u001bn\rND\u001cd\u0010I4jj:iq:L+bph~\u0011JDhy>ex-\u0003}\r"));
               if (!var9) {
                  break label43;
               }
            }

            var2.append((Object)c("CQ\u001bn\rND\u001cd\u0010I4j")).append((Object)var3.getDescription());
            var2.append((Object)c("%\u001e"));
         }

         var2.append((Object)c("=.u\r")).append((Object)this.a(var1, (SnmpOidInfo)var3, true, var4));
         var2.append((Object)"\n");
         var2.popIndent();
         var2.append((Object)"\n");
         this.processSnmpTableEntry(var1, var3, var5, var2, var4);
         SnmpObjectInfo[] var7 = var5.getColumns();
         int var8 = 0;

         while(var8 < var7.length) {
            if (var3.getOid().contains(var7[var8].getOid())) {
               this.processSnmpObjectInfo(var1, var2, var7[var8], var4);
               var2.append((Object)"\n");
            }

            ++var8;
            if (var9) {
               break;
            }
         }

         if (SnmpException.b) {
            i = !var9;
         }

      }
   }

   protected void processSnmpTableEntry(SnmpMetadata var1, SnmpTableInfo var2, SnmpTableEntryInfo var3, TextBuffer var4, Context var5) {
      boolean var10;
      label81: {
         var10 = i;
         if (this.b == 0) {
            var5.uses(c("UR\u000b\u001cn2!e~\u0012N"), c("HV\u0002h\u001cS9\u001ct\u000fB"));
            if (!var10) {
               break label81;
            }

            SnmpException.b = !SnmpException.b;
         }

         var5.uses(c("TZ\u0005})59\u001b`\u0016"), c("HV\u0002h\u001cS9\u001ct\u000fB"));
      }

      String var6;
      label75: {
         var6 = this.a(var3.getName());
         var4.append((Object)var3.getName()).append((Object)c("'[\ng\u001aD@ey\u0006WQ"));
         var4.pushIndent();
         var4.append((Object)c("TM\u0006y\u001e_4h\r\u007f'4")).append((Object)var6).append((Object)"\n");
         if (this.b == 0) {
            var4.append((Object)c("FW\u000bh\fT4h\r\u007f'4&B+*u+N:tg!O3b\u001e"));
            if (!var10) {
               break label75;
            }
         }

         var4.append((Object)c("JU\u0010\u0000\u001eDW\r~\f'4&B+*u+N:tg!O3b\u001e"));
      }

      label70: {
         this.a(var3.getStatus(), var4);
         if (var3.getDescription() == null) {
            var4.append((Object)c("CQ\u001bn\rND\u001cd\u0010I4jj:iq:L+bph~\u0011JDhy>ex-\r\u001ai`:Tq%\u001e"));
            if (!var10) {
               break label70;
            }
         }

         var4.append((Object)c("CQ\u001bn\rND\u001cd\u0010I4j")).append((Object)var3.getDescription());
         var4.append((Object)c("%\u001e"));
      }

      SnmpObjectInfo[] var7 = var3.getIndexes();
      if (var7 != null) {
         if (var7.length > 0) {
            label85: {
               var4.append((Object)c("NZ\fh\u0007'oh"));
               int var8 = 0;

               while(var8 < var7.length) {
                  SnmpObjectInfo var9 = var7[var8];
                  var4.append((Object)var9.getName());
                  var5.uses((SnmpOidInfo)var9);
                  if (var10) {
                     break label85;
                  }

                  if (var8 + 1 < var7.length) {
                     var4.append((Object)c("+4"));
                  }

                  ++var8;
                  if (var10) {
                     break;
                  }
               }

               var4.append((Object)c("z\u001e"));
               if (var10) {
               }
            }
         }
      } else if (var3.getAugments() != null) {
         var5.uses((SnmpOidInfo)var3.getAugments());
         var4.append((Object)c("FA\u000f`\u001aI@\u001b\r$'"));
         var4.append((Object)var3.getAugments().getName());
         var4.append((Object)c("'iB"));
      }

      var5.uses((SnmpOidInfo)var2);
      var4.append((Object)c("=.u\r$'")).append((Object)var2.getName()).append((Object)c("'%hP"));
      var4.append((Object)"\n");
      var4.popIndent();
      var4.append((Object)var6).append((Object)c("'.r\u0010"));
      var4.pushIndent();
      var4.append((Object)c("TQ\u0019x\u001aIW\r'"));
      var4.append((Object)"{");
      var4.pushIndent();
      SnmpObjectInfo[] var11 = var3.getColumns();
      int var12 = 0;

      while(true) {
         if (var12 < var11.length) {
            var5.uses((SnmpOidInfo)var11[var12]);
            var4.append((Object)this.a(var11[var12].getName(), 20));
            var4.append((Object)" ");
            this.a(var11[var12], var4, var5, false);
            if (var10) {
               break;
            }

            if (var12 + 1 < var11.length) {
               var4.append((Object)c("+\u001e"));
            }

            ++var12;
            if (!var10) {
               continue;
            }
         }

         var4.popIndent();
         var4.append((Object)c("z\u001e"));
         var4.popIndent();
         var4.append((Object)"\n");
         break;
      }

   }

   String a(String var1, int var2) {
      int var3 = var1.length();
      return var3 > var2 ? var1 : var1 + f.substring(0, var2 - var3);
   }

   protected void processSnmpObjectInfo(SnmpMetadata var1, TextBuffer var2, SnmpObjectInfo var3, Context var4) {
      boolean var7;
      label35: {
         var7 = i;
         if (this.b == 0) {
            var4.uses(c("UR\u000b\u001cn2!e~\u0012N"), c("HV\u0002h\u001cS9\u001ct\u000fB"));
            if (!var7) {
               break label35;
            }
         }

         var4.uses(c("TZ\u0005})59\u001b`\u0016"), c("HV\u0002h\u001cS9\u001ct\u000fB"));
      }

      if (var3.getType() != 255) {
         label29: {
            String var5 = var3.getName();
            (new StringBuffer()).append(var5.substring(0, 1).toUpperCase()).append(var5.substring(1)).toString();
            var2.append((Object)var5).append((Object)c("'[\ng\u001aD@ey\u0006WQ"));
            var2.pushIndent();
            var2.append((Object)c("TM\u0006y\u001e_4h\r\u007f'4"));
            this.a(var3, var2, var4);
            var2.append((Object)"\n");
            if (this.b == 0) {
               var2.append((Object)c("FW\u000bh\fT4h\r\u007f'4"));
               var2.append((Object)this.a(var3.getAccess()));
               var2.append((Object)"\n");
               if (!var7) {
                  break label29;
               }
            }

            var2.append((Object)c("JU\u0010\u0000\u001eDW\r~\f'4"));
            var2.append((Object)this.a(var3.getAccess()));
            var2.append((Object)"\n");
         }

         label24: {
            this.a(var3.getStatus(), var2);
            if (var3.getDescription() == null) {
               var2.append((Object)c("CQ\u001bn\rND\u001cd\u0010I4jj:iq:L+bphb=mq+Yq%\u001e"));
               if (!var7) {
                  break label24;
               }
            }

            var2.append((Object)c("CQ\u001bn\rND\u001cd\u0010I4j")).append((Object)var3.getDescription());
            var2.append((Object)c("%\u001e"));
         }

         if (var3.getDefVal() != null) {
            var2.append((Object)c("CQ\u000e{\u001eK43")).append((Object)var3.getDefVal());
            var2.append((Object)c("'iB"));
         }

         var2.append((Object)c("=.u\r")).append((Object)this.a(var1, (SnmpOidInfo)var3, true, var4));
         var2.popIndent();
         var2.append((Object)"\n");
      }
   }

   protected void processSnmpNotificationInfo(SnmpMetadata var1, TextBuffer var2, SnmpNotificationInfo var3, Context var4) {
      boolean var10 = i;
      String var5;
      if (this.b == 0) {
         SnmpOid var6;
         label96: {
            var4.uses(c("UR\u000b\u001cn2!e~\u0012N"), c("SF\t}rSM\u0018h"));
            var5 = var3.getName();
            var2.append((Object)var5).append((Object)c("'@\u001al\u000f*@\u0011}\u001a"));
            var2.pushIndent();
            var6 = var3.getOid();
            if (g.contains(var6)) {
               var2.append((Object)c("BZ\u001ch\rWF\u0001~\u001a'oh^1jdhPU"));
               if (!var10) {
                  break label96;
               }
            }

            SnmpOid var7 = var6.getParent();
            if (var7.longValue() == 0L) {
               var2.append((Object)c("BZ\u001ch\rWF\u0001~\u001a'")).append((Object)var7.getParent()).append((Object)"\n");
               if (!var10) {
                  break label96;
               }
            }

            var2.append((Object)c("BZ\u001ch\rWF\u0001~\u001a'")).append((Object)var7).append((Object)"\n");
         }

         SnmpObjectInfo[] var12 = var3.getObjects();
         if (var12 != null && var12.length > 0) {
            label102: {
               var2.append((Object)c("QU\u001ad\u001eEX\r~\u007f|"));
               int var8 = 0;

               while(var8 < var12.length) {
                  SnmpObjectInfo var9 = var12[var8];
                  var2.append((Object)var9.getName());
                  if (var10) {
                     break label102;
                  }

                  if (var8 + 1 < var12.length) {
                     var2.append((Object)c("+4"));
                  }

                  var4.uses((SnmpOidInfo)var9);
                  ++var8;
                  if (var10) {
                     break;
                  }
               }

               var2.append((Object)c("z\u001e"));
            }
         }

         label65: {
            if (var3.getDescription() == null) {
               var2.append((Object)c("CQ\u001bn\rND\u001cd\u0010I4jj:iq:L+bphn0jd'C:i`f\u000fU"));
               if (!var10) {
                  break label65;
               }
            }

            var2.append((Object)c("CQ\u001bn\rND\u001cd\u0010I4j")).append((Object)var3.getDescription());
            var2.append((Object)c("%\u001e"));
         }

         var2.append((Object)c("=.u\r")).append(var6.longValue()).append((Object)"\n");
         var2.popIndent();
         if (!var10) {
            return;
         }
      }

      label58: {
         var4.uses(c("TZ\u0005})59\u001b`\u0016"), c("I[\u001cd\u0019NW\ty\u0016HZey\u0006WQ"));
         var5 = var3.getName();
         var2.append((Object)var5).append((Object)c("'Z\u0007y\u0016A]\u000bl\u000bN[\u0006\u0000\u000b^D\r"));
         var2.pushIndent();
         SnmpObjectInfo[] var11 = var3.getObjects();
         if (var11 != null && var11.length > 0) {
            var2.append((Object)c("HV\u0002h\u001cSGhV"));
            int var13 = 0;

            while(var13 < var11.length) {
               SnmpObjectInfo var14 = var11[var13];
               var2.append((Object)var14.getName());
               if (var10) {
                  break label58;
               }

               if (var13 + 1 < var11.length) {
                  var2.append((Object)c("+4"));
               }

               var4.uses((SnmpOidInfo)var14);
               ++var13;
               if (var10) {
                  break;
               }
            }

            var2.append((Object)c("z\u001e"));
         }

         this.a(var3.getStatus(), var2);
      }

      label43: {
         if (var3.getDescription() == null) {
            var2.append((Object)c("CQ\u001bn\rND\u001cd\u0010I4jj:iq:L+bphn0jd'C:i`f\u000fU"));
            if (!var10) {
               break label43;
            }
         }

         var2.append((Object)c("CQ\u001bn\rND\u001cd\u0010I4j")).append((Object)var3.getDescription());
         var2.append((Object)c("%\u001e"));
      }

      var2.append((Object)c("=.u\r")).append((Object)this.a(var1, (SnmpOidInfo)var3, true, var4));
      var2.popIndent();
      var2.append((Object)"\n");
   }

   protected void processSnmpNotificationGroupInfo(SnmpMetadata var1, TextBuffer var2, SnmpNotificationGroupInfo var3, Context var4) {
      boolean var9 = i;
      if (this.b != 0) {
         label40: {
            var4.uses(c("TZ\u0005})59\u000bb\u0011A"), c("I[\u001cd\u0019NW\ty\u0016HZej\rHA\u0018"));
            String var5 = var3.getName();
            var2.append((Object)var5).append((Object)c("'Z\u0007y\u0016A]\u000bl\u000bN[\u0006\u0000\u0018U[\u001d}"));
            var2.pushIndent();
            SnmpNotificationInfo[] var6 = var3.getNotifications();
            if (var6 != null && var6.length > 0) {
               var2.append((Object)c("I[\u001cd\u0019NW\ty\u0016HZ\u001b\r$"));
               int var7 = 0;

               while(var7 < var6.length) {
                  SnmpNotificationInfo var8 = var6[var7];
                  var2.append((Object)var8.getName());
                  if (var9) {
                     break label40;
                  }

                  if (var7 + 1 < var6.length) {
                     var2.append((Object)c("+4"));
                  }

                  var4.uses((SnmpOidInfo)var8);
                  ++var7;
                  if (var9) {
                     break;
                  }
               }

               var2.append((Object)c("z\u001e"));
            }

            this.a(var3.getStatus(), var2);
         }

         label25: {
            if (var3.getDescription() == null) {
               var2.append((Object)c("CQ\u001bn\rND\u001cd\u0010I4jj:iq:L+bphn0jd'C:i`f\u000fU"));
               if (!var9) {
                  break label25;
               }
            }

            var2.append((Object)c("CQ\u001bn\rND\u001cd\u0010I4j")).append((Object)var3.getDescription());
            var2.append((Object)c("%\u001e"));
         }

         var2.append((Object)c("=.u\r")).append((Object)this.a(var1, (SnmpOidInfo)var3, true, var4));
         var2.popIndent();
         var2.append((Object)"\n");
      }
   }

   protected void processSnmpObjectGroupInfo(SnmpMetadata var1, TextBuffer var2, SnmpObjectGroupInfo var3, Context var4) {
      boolean var9 = i;
      if (this.b != 0) {
         label40: {
            var4.uses(c("TZ\u0005})59\u000bb\u0011A"), c("HV\u0002h\u001cS9\u000f\u007f\u0010RD"));
            String var5 = var3.getName();
            var2.append((Object)var5).append((Object)c("'[\ng\u001aD@ej\rHA\u0018"));
            var2.pushIndent();
            SnmpObjectInfo[] var6 = var3.getObjects();
            if (var6 != null && var6.length > 0) {
               var2.append((Object)c("HV\u0002h\u001cSGhV"));
               int var7 = 0;

               while(var7 < var6.length) {
                  SnmpObjectInfo var8 = var6[var7];
                  var2.append((Object)var8.getName());
                  if (var9) {
                     break label40;
                  }

                  if (var7 + 1 < var6.length) {
                     var2.append((Object)c("+4"));
                  }

                  var4.uses((SnmpOidInfo)var8);
                  ++var7;
                  if (var9) {
                     break;
                  }
               }

               var2.append((Object)c("z\u001e"));
            }

            this.a(var3.getStatus(), var2);
         }

         label25: {
            if (var3.getDescription() == null) {
               var2.append((Object)c("CQ\u001bn\rND\u001cd\u0010I4jj:iq:L+bphn0jd'C:i`f\u000fU"));
               if (!var9) {
                  break label25;
               }
            }

            var2.append((Object)c("CQ\u001bn\rND\u001cd\u0010I4j")).append((Object)var3.getDescription());
            var2.append((Object)c("%\u001e"));
         }

         var2.append((Object)c("=.u\r")).append((Object)this.a(var1, (SnmpOidInfo)var3, true, var4));
         var2.popIndent();
         var2.append((Object)"\n");
      }
   }

   String a(int var1) {
      switch (var1) {
         case 0:
            return c("i{<\u0000>dw-^,nv$H");
         case 1:
            return c("uq)Irhz$T");
         case 2:
            return c("pf!Y:*{&A&");
         case 3:
            return c("uq)Irpf!Y:");
         case 4:
         case 6:
         case 7:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         default:
            return "?";
         case 5:
            return c("uq)Irdf-L+b");
         case 8:
            return c("fw+H,t}*A:*r'_ri{<D9~");
         case 16:
            return c("i{<\u00006jd$H2bz<H;");
      }
   }

   private void a(SnmpObjectInfo var1, TextBuffer var2, Context var3) {
      this.a(var1, var2, var3, true);
   }

   private void a(SnmpObjectInfo var1, TextBuffer var2, Context var3, boolean var4) {
      boolean var12 = i;
      boolean var5 = false;
      String var6 = null;
      String var7 = null;
      SnmpTypeInfo var13;
      if (var1.getTypeInfo() != null && var1.getTypeInfo().getName() != null) {
         var6 = null;
         var13 = var1.getTypeInfo();
         if (var13.getModule() != null && var13.getModule().getName() != null) {
            var6 = var13.getModule().getName();
         } else if (var13.getBaseType() != null) {
            SnmpModule var14 = var13.getBaseType().getModule();
            if (var14 != null && var14.getName() != null) {
               var6 = var14.getName();
            }
         }

         var7 = var13.getName();
         var5 = true;
      } else {
         int var8 = var1.getType();
         if (this.b == 0) {
            var6 = c("UR\u000b\u001cn2!e~\u0012N");
            switch (var8) {
               case 2:
                  var7 = c("NZ\u001ch\u0018BF");
                  break;
               case 4:
                  var7 = c("HW\u001ch\u000b'G\u001c\u007f\u0016IS");
                  break;
               case 5:
                  var7 = c("IA\u0004a");
                  break;
               case 6:
                  var7 = c("HV\u0002h\u001cS4\u0001i\u001aI@\u0001k\u0016BF");
                  break;
               case 64:
                  var7 = c("Nd\tI;uq;^");
                  var5 = true;
                  break;
               case 65:
                  var7 = c("D{=C+bf");
                  var5 = true;
                  break;
               case 66:
                  var7 = c("@u=J:");
                  var5 = true;
                  break;
               case 67:
                  var7 = c("S}%H\u000bnw#^");
                  var5 = true;
                  break;
               case 68:
                  var7 = c("Hd)\\*b");
                  var5 = true;
                  break;
               case 70:
                  var7 = c("D{=C+bf~\u0019");
                  var5 = true;
                  break;
               default:
                  var7 = c("IA\u0004a");
            }
         } else {
            var6 = c("TZ\u0005})59\u001b`\u0016");
            int var9 = var1.getSmiType();
            if (var9 >= 0) {
               switch (var9) {
                  case 1:
                     var7 = c("NZ\u001ch\u0018BF");
                     break;
                  case 2:
                     var7 = c("Nz<H8bf{\u001f");
                     var5 = true;
                     break;
                  case 3:
                     var7 = c("Rz;D8iq,\u001em");
                     var5 = true;
                     break;
                  case 4:
                     var7 = c("D{=C+bf{\u001f");
                     var5 = true;
                     break;
                  case 5:
                     var7 = c("D{=C+bf{\u001f");
                     var5 = true;
                     break;
                  case 6:
                     var7 = c("D{=C+bf~\u0019");
                     var5 = true;
                     break;
                  case 7:
                     var7 = c("@u=J:4&");
                     var5 = true;
                     break;
                  case 8:
                     var7 = c("@u=J:4&");
                     var5 = true;
                     break;
                  case 9:
                     var7 = c("Nd\tI;uq;^");
                     var5 = true;
                     break;
                  case 10:
                     var7 = c("S}%H\u000bnw#^");
                     var5 = true;
                     break;
                  case 11:
                     var7 = c("Hd)\\*b");
                     var5 = true;
                     break;
                  case 12:
                     var7 = c("HV\u0002h\u001cS4\u0001i\u001aI@\u0001k\u0016BF");
                     break;
                  case 13:
                     var7 = c("HW\u001ch\u000b'G\u001c\u007f\u0016IS");
                     break;
                  case 14:
                     var7 = c("E]\u001c~");
                     break;
                  default:
                     var7 = c("IA\u0004a");
               }
            } else {
               switch (var8) {
                  case 2:
                     var7 = c("Nz<H8bf{\u001f");
                     var5 = true;
                     break;
                  case 4:
                     var7 = c("HW\u001ch\u000b'G\u001c\u007f\u0016IS");
                     break;
                  case 5:
                     var7 = c("IA\u0004a");
                     break;
                  case 6:
                     var7 = c("HV\u0002h\u001cS4\u0001i\u001aI@\u0001k\u0016BF");
                     break;
                  case 64:
                     var7 = c("Nd\tI;uq;^");
                     var5 = true;
                     break;
                  case 65:
                     var7 = c("D{=C+bf{\u001f");
                     var5 = true;
                     break;
                  case 66:
                     var7 = c("@u=J:4&");
                     var5 = true;
                     break;
                  case 67:
                     var7 = c("S}%H\u000bnw#^");
                     var5 = true;
                     break;
                  case 68:
                     var7 = c("Hd)\\*b");
                     var5 = true;
                     break;
                  case 70:
                     var7 = c("D{=C+bf~\u0019");
                     var5 = true;
                     break;
                  default:
                     var7 = c("IA\u0004a");
               }
            }
         }
      }

      if (var5) {
         var3.uses(var6, var7);
      }

      var2.append((Object)var7);
      if (var1.getTypeInfo() != null && var1.getTypeInfo().getModule() == null && var4) {
         var13 = var1.getTypeInfo();
         if (var13.hasRangeSpec()) {
            new StringBuffer();
            var2.append((Object)" ");
            if (var1.getType() == 4) {
               var2.append((Object)c("/G\u0001w\u001a'"));
            }

            var2.append((Object)"(");
            RangeItem[] var10 = var13.getRangeSpec();
            int var11 = 0;

            int var10000;
            int var10001;
            while(true) {
               if (var11 < var10.length) {
                  var2.append((Object)var10[var11].toString());
                  var10000 = var11 + 1;
                  var10001 = var10.length;
                  if (var12) {
                     break;
                  }

                  if (var10000 < var10001) {
                     var2.append((Object)"|");
                  }

                  ++var11;
                  if (!var12) {
                     continue;
                  }
               }

               var2.append((Object)")");
               var10000 = var1.getType();
               var10001 = 4;
               break;
            }

            if (var10000 == var10001) {
               var2.append((Object)")");
            }
         }

         if (var13.getNameToNumberMap() != null) {
            var2.pushIndent(4);
            this.a(var13.getNameToNumberMap(), var2);
            var2.popIndent();
         }
      }

   }

   String a(SnmpMetadata var1, SnmpOidInfo var2, boolean var3, Context var4) {
      return this.a(var1, var2.getOid(), var3, var4);
   }

   String a(SnmpMetadata var1, SnmpOid var2, boolean var3, Context var4) {
      boolean var11 = i;
      if (var3) {
         try {
            SnmpOid var5 = var2.getParent();
            SnmpOidInfo var6 = var1.resolveBaseOid(var5);
            if (var6 != null && var6.getName() != null) {
               SnmpOidInfo var7 = var4.a.getOidInfo(var6.getName());
               if (var7 != null && var7.getOid().equals(var6.getOid())) {
                  var6 = var7;
               }

               var4.uses(var6);
               SnmpOid var8 = var6.getOid();
               String var9 = c("|4") + var6.getName() + " ";
               int var10 = var8.getLength();

               while(true) {
                  if (var10 < var2.getLength()) {
                     var9 = var9 + var2.get(var10) + " ";
                     ++var10;
                     if (!var11 || !var11) {
                        continue;
                     }
                     break;
                  }

                  var9 = var9 + c("'i");
                  break;
               }

               return var9;
            }
         } catch (SnmpValueException var14) {
         }
      }

      StringBuffer var15 = new StringBuffer();
      var15.append(c("|4"));

      try {
         switch ((int)var2.get(0)) {
            case 0:
               var15.append(c("dw!Y+'"));
               if (!var11) {
                  break;
               }
            case 1:
               var15.append(c("ng'\r"));
               if (!var11) {
                  break;
               }
            case 2:
               var15.append(c("m{!C+*};Brdw!Y+'"));
               if (!var11) {
                  break;
               }
            default:
               var15.append(c("ng'\r"));
         }
      } catch (Exception var13) {
      }

      int var16 = 1;

      while(true) {
         if (var16 < var2.getLength()) {
            label49: {
               try {
                  var15.append(var2.get(var16));
               } catch (SnmpValueException var12) {
                  break label49;
               }

               if (var11) {
                  break;
               }
            }

            var15.append(' ');
            ++var16;
            if (!var11) {
               continue;
            }
         }

         var15.append("}");
         break;
      }

      return var15.toString();
   }

   protected void processSnmpOidInfo(SnmpMetadata var1, TextBuffer var2, SnmpOidInfo var3, Context var4) {
      var2.append((Object)var3.getName());
      var2.append((Object)c("'[\ng\u001aD@hd\u001bBZ\u001cd\u0019NQ\u001a\re=)h"));
      var2.append((Object)this.a(var1, var3, this.a, var4));
      var2.append((Object)c("\r\u001e"));
   }

   void a(SnmpMetadata var1, TextBuffer var2, SnmpOid var3, String var4, Context var5) {
      if (var3.getLength() > 1) {
         var2.append((Object)var4);
         var2.append((Object)c("'[\ng\u001aD@hd\u001bBZ\u001cd\u0019NQ\u001a\re=)h"));
         var2.append((Object)this.a(var1, var3, this.a, var5));
         var2.append((Object)c("\r\u001e"));
      }

   }

   String a(String var1) {
      return var1.substring(0, 1).toUpperCase() + var1.substring(1);
   }

   void a(SnmpMetadata var1, TextBuffer var2, SnmpTypeInfo var3, Context var4) throws SnmpValueException {
      boolean var9 = i;
      if (var3.getType() != 255) {
         boolean var5;
         label116: {
            var5 = false;
            if (var3.getDisplayHint() != null || var3.getDescription() != null) {
               var2.append((Object)var3.getName()).append((Object)c("'.r\u0010\u007f'@\ru\u000bRU\u0004\u0000\u001cHZ\u001eh\u0011S]\u0007c"));
               var2.pushIndent();
               var5 = true;
               if (var3.getDisplayHint() != null) {
                  var2.append((Object)c("C]\u001b}\u0013FMee\u0016I@h\u000f")).append((Object)var3.getDisplayHint());
                  var2.append((Object)c("%\u001e"));
               }

               label93: {
                  this.a(var3.getStatus(), var2);
                  if (var3.getDescription() == null) {
                     var2.append((Object)c("CQ\u001bn\rND\u001cd\u0010I4ji:tw:D/s}'C\u007fI{<\r\u001equ!A>ex-\u0003}\r"));
                     if (!var9) {
                        break label93;
                     }
                  }

                  var2.append((Object)c("CQ\u001bn\rND\u001cd\u0010I4j"));
                  var2.append((Object)var3.getDescription()).append((Object)c("%\u001e"));
               }

               var2.append((Object)c("TM\u0006y\u001e_4h\r\u007f'4h"));
               if (!var9) {
                  break label116;
               }
            }

            var2.append((Object)var3.getName()).append((Object)c("'.r\u0010\u007f"));
         }

         label114: {
            SnmpTypeInfo var6 = var3.getBaseType();
            if (var6 != null && var6.getName() != null && var6.getModule() != null) {
               var2.append((Object)var6.getName());
               var4.uses(var6.getModule().getName(), var6.getName());
               if (!var9) {
                  break label114;
               }
            }

            String var7 = var3.getDefinedType();
            if (!var7.equals(c("NZ\u001ch\u0018BF")) && !var7.equals(c("E]\u001c~")) && !var7.equals(c("HW\u001ch\u000b'G\u001c\u007f\u0016IS")) && !var7.equals(c("HV\u0002h\u001cS4\u0001i\u001aI@\u0001k\u0016BF"))) {
               label113: {
                  if (this.b == 0) {
                     var4.uses(c("UR\u000b\u001cn2!e~\u0012N"), var7);
                     if (!var9) {
                        break label113;
                     }
                  }

                  var4.uses(c("TZ\u0005})59\u001b`\u0016"), var7);
               }
            }

            var2.append((Object)var7);
         }

         if (var3.hasRangeSpec()) {
            var2.append((Object)" ");
            if (var3.getType() == 4) {
               var2.append((Object)c("/G\u0001w\u001a'"));
            }

            var2.append((Object)"(");
            RangeItem[] var10 = var3.getRangeSpec();
            int var8 = 0;

            int var10000;
            int var10001;
            label63: {
               while(var8 < var10.length) {
                  var2.append((Object)var10[var8].toString());
                  var10000 = var8 + 1;
                  var10001 = var10.length;
                  if (var9) {
                     break label63;
                  }

                  if (var10000 < var10001) {
                     var2.append((Object)"|");
                  }

                  ++var8;
                  if (var9) {
                     break;
                  }
               }

               var2.append((Object)")");
               var10000 = var3.getType();
               var10001 = 4;
            }

            if (var10000 == var10001) {
               var2.append((Object)")");
            }
         }

         if (var3.getNameToNumberMap() != null) {
            var2.pushIndent(4);
            this.a(var3.getNameToNumberMap(), var2);
            var2.popIndent();
         }

         if (var5) {
            var2.popIndent();
         }

         var2.append((Object)"\n");
      }
   }

   private void a(Hashtable var1, TextBuffer var2) {
      boolean var12 = i;
      var2.append((Object)"{");
      var2.pushIndent();
      Enumeration var3 = var1.keys();
      Vector var4 = new Vector();
      Vector var5 = new Vector();

      int var10000;
      while(true) {
         if (var3.hasMoreElements()) {
            String var6 = (String)var3.nextElement();
            Long var7 = (Long)var1.get(var6);
            int var8 = 0;
            var10000 = var4.size() - 1;
            if (var12) {
               break;
            }

            int var9 = var10000;

            label58: {
               while(var9 >= 0) {
                  String var10 = (String)var4.elementAt(var9);
                  Long var11 = (Long)var5.elementAt(var9);
                  long var16;
                  var10000 = (var16 = var11 - var7) == 0L ? 0 : (var16 < 0L ? -1 : 1);
                  if (var12) {
                     break label58;
                  }

                  if (var10000 < 0) {
                     var8 = var9 + 1;
                     if (!var12) {
                        break;
                     }
                  }

                  --var9;
                  if (var12) {
                     break;
                  }
               }

               var10000 = var8;
            }

            label45: {
               if (var10000 >= var4.size()) {
                  var4.addElement(var6);
                  var5.addElement(var7);
                  if (!var12) {
                     break label45;
                  }
               }

               var4.insertElementAt(var6, var8);
               var5.insertElementAt(var7, var8);
            }

            if (!var12) {
               continue;
            }
         }

         var10000 = 0;
         break;
      }

      int var13 = var10000;

      while(true) {
         if (var13 < var4.size()) {
            String var14 = (String)var4.elementAt(var13);
            Long var15 = (Long)var5.elementAt(var13);
            var2.append((Object)var14).append((Object)"(").append((Object)var15).append((Object)")");
            if (var12) {
               break;
            }

            if (var13 + 1 < var4.size()) {
               var2.append((Object)c("+\u001e"));
            }

            ++var13;
            if (!var12) {
               continue;
            }
         }

         var2.popIndent();
         var2.append((Object)"}");
         break;
      }

   }

   protected void processOidInfo(SnmpMetadata var1, TextBuffer var2, SnmpOidInfo var3, Context var4) throws SnmpValueException {
      boolean var6 = i;
      if (var3 instanceof SnmpTableInfo) {
         this.processSnmpTableInfo(var1, var2, (SnmpTableInfo)var3, var4);
         if (!var6) {
            return;
         }
      }

      if (!(var3 instanceof SnmpTableEntryInfo)) {
         if (var3 instanceof SnmpObjectInfo) {
            SnmpObjectInfo var5 = (SnmpObjectInfo)var3;
            if (var5.getTableInfo() == null) {
               this.processSnmpObjectInfo(var1, var2, (SnmpObjectInfo)var3, var4);
            }

            if (!var6) {
               return;
            }
         }

         if (var3 instanceof SnmpNotificationInfo) {
            this.processSnmpNotificationInfo(var1, var2, (SnmpNotificationInfo)var3, var4);
            if (!var6) {
               return;
            }
         }

         if (var3 instanceof SnmpNotificationGroupInfo) {
            this.processSnmpNotificationGroupInfo(var1, var2, (SnmpNotificationGroupInfo)var3, var4);
            if (!var6) {
               return;
            }
         }

         if (var3 instanceof SnmpObjectGroupInfo) {
            this.processSnmpObjectGroupInfo(var1, var2, (SnmpObjectGroupInfo)var3, var4);
            if (!var6) {
               return;
            }
         }

         this.processSnmpOidInfo(var1, var2, var3, var4);
      }

   }

   private void a(SnmpMetadata var1, TextBuffer var2, SnmpOidInfo var3, Context var4, int var5) throws SnmpValueException {
      boolean var7 = i;
      switch (var5) {
         case 1:
            if (!var3.getClass().equals(h == null ? (h = b(c("j{&K0\u007f:<B0k\u007f!Yqtz%]qjq<L;f`)\u0003\fiy8b6c]&K0"))) : h)) {
               break;
            }

            this.processSnmpOidInfo(var1, var2, var3, var4);
            if (!var7) {
               break;
            }
         case 2:
            if (!(var3 instanceof SnmpObjectInfo)) {
               break;
            }

            SnmpObjectInfo var6 = (SnmpObjectInfo)var3;
            if (!var6.isColumnar()) {
               this.processSnmpObjectInfo(var1, var2, (SnmpObjectInfo)var3, var4);
            }

            if (!var7) {
               break;
            }
         case 3:
            if (!(var3 instanceof SnmpTableInfo)) {
               break;
            }

            this.processSnmpTableInfo(var1, var2, (SnmpTableInfo)var3, var4);
            if (!var7) {
               break;
            }
         case 4:
            if (!(var3 instanceof SnmpNotificationInfo)) {
               break;
            }

            this.processSnmpNotificationInfo(var1, var2, (SnmpNotificationInfo)var3, var4);
            if (!var7) {
               break;
            }
         case 5:
            if (var3 instanceof SnmpNotificationGroupInfo) {
               this.processSnmpNotificationGroupInfo(var1, var2, (SnmpNotificationGroupInfo)var3, var4);
               if (!var7) {
                  return;
               }
            }

            if (var3 instanceof SnmpObjectGroupInfo) {
               this.processSnmpObjectGroupInfo(var1, var2, (SnmpObjectGroupInfo)var3, var4);
            }
      }

   }

   private void a(String var1, TextBuffer var2) {
      if (var1 == null) {
         var1 = c("da:_:i`");
      }

      if (this.b == 0) {
         var1 = d.getProperty(var1, c("ju&I>s{:T"));
         var2.append((Object)c("T@\ty\nT4h\r\u007f'4")).append((Object)var1).append((Object)"\n");
         if (!i) {
            return;
         }
      }

      var1 = e.getProperty(var1, c("da:_:i`"));
      var2.append((Object)c("T@\ty\nT4h\r\u007f'4")).append((Object)var1).append((Object)"\n");
   }

   public void setVerbose(boolean var1) {
      this.c = var1;
   }

   // $FF: synthetic method
   static Class b(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw (new NoClassDefFoundError()).initCause(var2);
      }
   }

   static {
      d.put(c("ju&I>s{:T"), c("ju&I>s{:T"));
      d.put(c("cq8_:du<H;"), c("cq8_:du<H;"));
      d.put(c("hv;B3b`-"), c("hv;B3b`-"));
      d.put(c("hd<D0iu$"), c("hd<D0iu$"));
      d.put(c("da:_:i`"), c("ju&I>s{:T"));
      e = new Properties();
      e.put(c("ju&I>s{:T"), c("da:_:i`"));
      e.put(c("cq8_:du<H;"), c("cq8_:du<H;"));
      e.put(c("hv;B3b`-"), c("hv;B3b`-"));
      e.put(c("hd<D0iu$"), c("da:_:i`"));
      e.put(c("da:_:i`"), c("da:_:i`"));
   }

   private static String c(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 7;
               break;
            case 1:
               var10003 = 20;
               break;
            case 2:
               var10003 = 72;
               break;
            case 3:
               var10003 = 45;
               break;
            default:
               var10003 = 95;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   protected static class Context {
      SnmpModule a;
      String b;
      private Hashtable c = new Hashtable();

      public Context(SnmpModule var1) {
         this.a = var1;
         if (var1 != null) {
            this.b = var1.getName() != null ? var1.getName() : null;
         }

      }

      public void uses(String var1, String var2) {
         if (var1 != null) {
            if (!var1.equals(this.b)) {
               Hashtable var3 = (Hashtable)this.c.get(var1);
               if (var3 == null) {
                  var3 = new Hashtable();
                  this.c.put(var1, var3);
               }

               var3.put(var2, var2);
            }

         }
      }

      public void uses(SnmpTypeInfo var1) {
         SnmpModule var2 = var1.getModule();
         String var3 = var2 != null ? var2.getName() : null;
         if (var3 != null) {
            if (!var3.equals(this.b)) {
               Hashtable var4 = (Hashtable)this.c.get(var3);
               if (var4 == null) {
                  var4 = new Hashtable();
                  this.c.put(var3, var4);
               }

               var4.put(var1.getName(), var1);
            }

         }
      }

      public void uses(SnmpOidInfo var1) {
         SnmpModule var2 = var1.getModule();
         String var3 = var2 != null ? var2.getName() : null;
         if (var3 != null) {
            if (!var3.equals(this.b)) {
               Hashtable var4 = (Hashtable)this.c.get(var3);
               if (var4 == null) {
                  var4 = new Hashtable();
                  this.c.put(var3, var4);
               }

               var4.put(var1.getName(), var1);
            }

         }
      }

      public Hashtable getImportMap() {
         return this.c;
      }
   }
}
