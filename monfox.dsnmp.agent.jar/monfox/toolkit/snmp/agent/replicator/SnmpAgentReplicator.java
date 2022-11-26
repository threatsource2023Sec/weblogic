package monfox.toolkit.snmp.agent.replicator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Provider;
import java.security.Security;
import java.util.Hashtable;
import java.util.ListIterator;
import java.util.Map;
import java.util.Vector;
import monfox.jdom.DocType;
import monfox.jdom.Document;
import monfox.jdom.Element;
import monfox.jdom.output.XMLOutputter;
import monfox.log.Logger;
import monfox.log.SimpleLogger;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.engine.SnmpContext;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.SnmpModule;
import monfox.toolkit.snmp.metadata.SnmpObjectInfo;
import monfox.toolkit.snmp.metadata.SnmpOidInfo;
import monfox.toolkit.snmp.metadata.SnmpTableInfo;
import monfox.toolkit.snmp.metadata.xml.SnmpMetadataRepository;
import monfox.toolkit.snmp.mgr.SnmpErrorException;
import monfox.toolkit.snmp.mgr.SnmpParameters;
import monfox.toolkit.snmp.mgr.SnmpPeer;
import monfox.toolkit.snmp.mgr.SnmpPendingRequest;
import monfox.toolkit.snmp.mgr.SnmpResponseListener;
import monfox.toolkit.snmp.mgr.SnmpSession;
import monfox.toolkit.snmp.mgr.SnmpTimeoutException;
import monfox.toolkit.snmp.util.Commandline;
import monfox.toolkit.snmp.v3.usm.USMUserTable;

public class SnmpAgentReplicator implements SnmpResponseListener {
   private SnmpMetadata a;
   private SnmpSession b;
   private String[] c;
   private String d;
   private String e;
   private String f;
   private String g;
   private String h;
   private String i;
   private int j;
   private int k;
   private int l;
   private int m;
   private Element n;
   private SnmpTableInfo o;
   private Element p;
   private Map q;
   private SnmpOid r;
   private static final String s = "$Id: SnmpAgentReplicator.java,v 1.11 2016/10/25 19:35:03 sking Exp $";
   public static int t;

   public static void main(String[] var0) {
      int var2 = t;

      try {
         SnmpAgentReplicator var1 = new SnmpAgentReplicator(var0);
         var1.a();
      } catch (Throwable var3) {
         System.out.println(a("\u00188lv@gJ") + var3);
      }

      if (SnmpException.b) {
         ++var2;
         t = var2;
      }

   }

   SnmpAgentReplicator(String[] var1) throws Throwable {
      int var2 = t;
      super();
      this.a = null;
      this.b = null;
      this.c = null;
      this.d = null;
      this.e = null;
      this.f = null;
      this.g = null;
      this.h = null;
      this.i = null;
      this.j = 0;
      this.k = 3;
      this.l = 3000;
      this.m = 161;
      this.n = null;
      this.o = null;
      this.p = null;
      this.q = null;
      this.r = null;
      this.c = var1;
      if (var2 != 0) {
         SnmpException.b = !SnmpException.b;
      }

   }

   void a() {
      int var17 = t;
      Commandline var1 = null;

      String var4;
      try {
         String var2 = a("\u000b\u000e\u0001");
         String[] var3 = new String[]{a("+["), a("+X]"), a("+X"), a("+Y"), a("(\u0019_^w"), a("9\u001fSI")};
         var4 = a("5\u0007sKf-\tQxJ(\u0006P\\");
         String[] var5 = new String[]{a("5\u0005MM"), a("0\u000fJXv<\u001e_"), a("0\u0003\\J"), a("0\u0003\\]{/\u0019"), a("/\u000fJK{8\u0019"), a(")\u0003S\\}(\u001e"), a("-\u0005LM"), a(">\u0005STg3\u0003J@"), a("(\u0019[K"), a(".\u000f]uw+\u000fR"), a("2\u001fJ_{1\u000f")};
         var1 = new Commandline(this.c, var2, var4, var3, var5);
      } catch (Throwable var19) {
         System.out.println(a("\u001f\u000bZ\u0019}-\u001eWV|gJ") + var19.getMessage());
         b();
         System.exit(1);
      }

      String[] var24 = var1.params;
      if (var1.hasFlag(a("bPKJs:\u000f")) || var24.length == 0) {
         b();
         System.exit(1);
      }

      try {
         label251: {
            if (var1.hasFlag(a("+["))) {
               this.j = 0;
               if (var17 == 0) {
                  break label251;
               }
            }

            if (var1.hasFlag(a("+X]"))) {
               this.j = 1;
               if (var17 == 0) {
                  break label251;
               }
            }

            if (var1.hasFlag(a("+X"))) {
               this.j = 1;
               if (var17 == 0) {
                  break label251;
               }
            }

            if (var1.hasFlag(a("+Y"))) {
               this.j = 3;
            }
         }

         if (var1.hasFlag(a("9PZL\u007f-"))) {
            Logger.setProvider(new SimpleLogger.Provider(a("\u000e\u0013MMw0DQLf")));
         }

         this.e = var1.getOption(a("5PVVa)"), a("1\u0005]X~5\u0005MM"));
         this.f = var1.getOption(a("0\u000fJXv<\u001e_"), (String)null);
         this.h = var1.getOption(a("0PSPp."), (String)null);
         this.i = var1.getOption(a("\u0010PSPp9\u0003LJ"), (String)null);
         this.d = var1.getOption(a(">P]V\u007f0\u001fPPf$"), a("-\u001f\\U{>"));
         this.k = var1.getIntOption(a("/PL\\f/\u0003[J"), 3);
         this.l = var1.getIntOption(a(")PJP\u007f8\u0005KM"), 3000);
         this.m = var1.getIntOption(a("-PNV`)"), 161);
         this.g = var1.getOption(a("2PQLf;\u0003R\\"), this.e + a("\u001c\r[Wfs\u0012SU"));
         SnmpMetadataRepository var25 = new SnmpMetadataRepository();
         if (this.i != null) {
            var4 = var25.getSearchPath();
            var4 = this.i + ";" + var4;
            var25.setSearchPath(var4);
         }

         SnmpMetadata var26 = SnmpMetadata.load((String)null);
         if (this.f != null) {
            var26.loadFile(var25, this.f);
         }

         if (this.h != null) {
            var26.loadModules(var25, (String)this.h);
         }

         SnmpFramework.setMetadata(var26);
         SnmpPeer var27 = new SnmpPeer(this.e, this.m);
         var27.setMaxRetries(this.k);
         var27.setTimeout((long)this.l);
         SnmpParameters var6 = null;
         String var8;
         int var30;
         if (this.j != 3) {
            label187: {
               var6 = new SnmpParameters(this.j, this.d);
               if (this.j == 0) {
                  System.out.println(a("pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?p"));
                  System.out.println(a("pG\u001ej\\\u0010:H\b(}B\u001eZ}0\u0007KW{)\u0013\u0003") + this.d + ")");
                  System.out.println(a("pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?p"));
                  if (var17 == 0) {
                     break label187;
                  }
               }

               System.out.println(a("pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?p"));
               System.out.println(a("pG\u001ej\\\u0010:H\u000bqgJ\u0016\u0019q2\u0007SL|4\u001eG\u0004") + this.d + ")");
               System.out.println(a("pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?p"));
            }
         } else {
            String var7 = var1.getOption(a("(PKJw/"), (String)null);
            if (var7 == null) {
               System.out.println(a("\u00188lv@gJpV2(\u0019[K2uGK\u00102.\u001a[Z{;\u0003[]<"));
               System.exit(1);
            }

            var8 = var1.getOption(a("-\u0018QO{9\u000fL"), a(">\u0005S\u0017a(\u0004\u0010Z`$\u001aJV<-\u0018QO{9\u000fL\u0017A(\u0004tzW"));

            try {
               Class var9 = Class.forName(var8);
               Provider var31 = (Provider)var9.newInstance();
               Security.addProvider(var31);
            } catch (Exception var20) {
               Provider[] var10 = Security.getProviders();
               if (var10 == null || var10.length == 0) {
                  System.out.println(a("\u00188lv@gJm\\q(\u0018WMk}\u001aLVd4\u000e[K2z") + var8 + a("zJ[K`2\u0018\u0010"));
                  System.exit(1);
               }
            }

            String var12;
            String var13;
            String var32;
            label253: {
               var30 = 0;
               var32 = var1.getOption("A", (String)null);
               String var11 = var1.getOption("a", (String)null);
               var12 = var1.getOption("X", (String)null);
               var13 = var1.getOption(a("1PM\\q\u0011\u000fH\\~"), (String)null);
               if (var13 != null) {
                  if (var13.equalsIgnoreCase(a("3\u0005\u007fLf5$Qi`4\u001c"))) {
                     var30 = 0;
                     if (var17 == 0) {
                        break label253;
                     }
                  }

                  if (var13.equalsIgnoreCase(a("<\u001fJQ\\2:LPd"))) {
                     var30 = 1;
                     if (var17 == 0) {
                        break label253;
                     }
                  }

                  if (var13.equalsIgnoreCase(a("<\u001fJQB/\u0003H"))) {
                     var30 = 3;
                     if (var17 == 0) {
                        break label253;
                     }
                  }

                  System.out.println(a("\u00188lv@gJwWd<\u0006W]2.\u000f]L`4\u001eG\u0019~8\u001c[U2uGR\u00102z") + var13);
                  System.exit(1);
                  if (var17 == 0) {
                     break label253;
                  }
               }

               if (var32 == null && var12 == null) {
                  var30 = 0;
                  if (var17 == 0) {
                     break label253;
                  }
               }

               if (var32 != null && var12 == null) {
                  var30 = 1;
                  if (var17 == 0) {
                     break label253;
                  }
               }

               if (var32 != null && var12 != null) {
                  var30 = 3;
               }
            }

            USMUserTable var14 = new USMUserTable();
            var14.addUser(var7, var32 != null ? var32 : a("\u00052faJ\u00052f"), var12 != null ? var12 : a("\u00052faJ\u00052f"));
            if (var30 != 0) {
               if (var32 == null) {
                  System.out.println(a("\u00188lv@gJpV2<\u001fJQ2uG\u007f\u00102-\u000bMJe2\u0018Z\u0019b/\u0005HPv8\u000e\u0010"));
                  System.exit(1);
               }

               if (var30 == 3 && var12 == null) {
                  System.out.println(a("\u00188lv@gJpV2-\u0018WO2uGf\u00102-\u000bMJe2\u0018Z\u0019b/\u0005HPv8\u000e\u0010"));
                  System.exit(1);
               }
            }

            var6 = new SnmpParameters(3, 3, var30, var7);
            var6.setUserTable(var14);
            System.out.println(a("pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?p"));
            System.out.println(a("pG\u001ej\\\u0010:H\n(}B\u001eLa8\u0018\u0003") + var7 + a("}\u0019[Z^8\u001c[U/") + var13 + ")");
            System.out.println(a("pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?p"));
         }

         var27.setParameters(var6);
         SnmpSession var28 = new SnmpSession(var27);
         if (this.j == 3) {
            var28.performDiscovery(var27, (SnmpResponseListener)null);
            var8 = var1.getOption("n", "");
            var27.setDefaultContext(new SnmpContext(var27.getSnmpEngineID(), var8));
         }

         this.n = new Element(a("0\u0003\\"));
         Vector var29 = new Vector();
         var30 = 0;

         ListIterator var10000;
         label181: {
            while(var30 < var24.length) {
               SnmpOid var33 = new SnmpOid(var24[var30]);
               boolean var34 = true;
               var10000 = var29.listIterator();
               if (var17 != 0) {
                  break label181;
               }

               ListIterator var37 = var10000;

               boolean var47;
               while(true) {
                  if (var37.hasNext()) {
                     SnmpOid var42 = (SnmpOid)var37.next();
                     var47 = var33.contains(var42);
                     if (var17 != 0) {
                        break;
                     }

                     label172: {
                        if (var47) {
                           var37.remove();
                           if (var17 == 0) {
                              break label172;
                           }
                        }

                        if (var42.contains(var33)) {
                           var34 = false;
                        }
                     }

                     if (var17 == 0) {
                        continue;
                     }
                  }

                  var47 = var34;
                  break;
               }

               if (var47) {
                  var29.add(var33);
               }

               ++var30;
               if (var17 != 0) {
                  break;
               }
            }

            var10000 = var29.listIterator();
         }

         ListIterator var39 = var10000;

         do {
            if (!var39.hasNext()) {
               Element var36 = new Element(a("<\r[Wf"));
               var36.addAttribute(a("-\u0005LM"), "" + this.m);
               Element var38 = new Element(a("0\u000fJXv<\u001e_"));
               var38.addAttribute(a(".\u000f_Kq5:_Mz"), var25.getSearchPath());
               if (this.f != null) {
                  Element var40 = new Element(a(";\u0003R\\"));
                  var40.addAttribute(a("3\u000bS\\"), this.f);
                  var38.addContent(var40);
               }

               SnmpModule[] var41 = var26.getModules();
               int var43 = 0;

               label148: {
                  while(var43 < var41.length) {
                     Element var44 = new Element(a("0\u0005ZL~8"));
                     var44.addAttribute(a("3\u000bS\\"), var41[var43].getName());
                     var38.addContent(var44);
                     ++var43;
                     if (var17 != 0) {
                        break label148;
                     }

                     if (var17 != 0) {
                        break;
                     }
                  }

                  var36.addContent(var38);
               }

               Element var45 = new Element(a("<\t]\\a.:QU{>\u0013"));
               var45.addAttribute(a(">\u0005STg3\u0003J@"), this.d);
               var45.addAttribute(a("<\t]\\a.'Q]w"), a("\u000f/\u007f}M\u0012$r`"));
               var36.addContent(var45);
               var36.addContent(this.n);
               Document var46 = new Document((Element)null);
               var46.setDocType(new DocType(a("<\r[Wf"), a("5\u001eJI(rEINes\u0007QWt2\u0012\u0010Z}0ESV|;\u0005F\u0016f2\u0005RR{)EMW\u007f-EZMvr\u0019PTb\u001c\r[Wfs\u000eJ]")));
               var46.setRootElement(var36);
               XMLOutputter var15 = new XMLOutputter(a("}J\u001e"), true);

               try {
                  System.out.println(a("WG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG"));
                  System.out.println(a("pG\u001ez`8\u000bJP|:Jft^}+Y\\|)JxP~8P\u001e\u001e") + this.g + a("zD"));
                  System.out.println(a("pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?p"));
                  FileOutputStream var16 = new FileOutputStream(this.g);
                  var15.output((Document)var46, (OutputStream)var16);
                  var16.close();
               } catch (IOException var18) {
                  System.out.println(a("\u00188lv@gJxP~8J}Kw<\u001eWV|}/LK}/P\u001e") + var18.getMessage());
               }
               break;
            }

            this.r = (SnmpOid)var39.next();
            SnmpVarBindList var35 = new SnmpVarBindList();
            var35.add(this.r);
            System.out.println(a("pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?p"));
            System.out.println(a("pG\u001ekw)\u0018W\\d4\u0004Y\u0019a(\bJKw8J\u0019") + this.r + a("zJXK}0J\u0019") + this.e + ":" + this.m + a("zD"));
            System.out.println(a("pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?pG\u0013\u0014?p"));
            var28.performWalk(this, var35, this.r, true);
         } while(var17 == 0 || var17 == 0);
      } catch (SnmpTimeoutException var21) {
         System.out.println(a("\u00188lv@gJjP\u007f8\u0005KM"));
      } catch (SnmpErrorException var22) {
      } catch (Exception var23) {
         System.out.println(a("\u00182}|B\t#qw(}") + var23);
      }

   }

   public void handleResponse(SnmpPendingRequest var1, int var2, int var3, SnmpVarBindList var4) {
      int var14 = t;
      if (var4.size() == 1) {
         if (var2 == 2) {
            System.out.println(a("\u0018$z\u0019]\u001bJspP"));
            return;
         }

         if (var2 != 0) {
            System.out.println(a("\u000e\u0004SIW/\u0018QK(\u0006") + Snmp.errorStatusToString(var2) + a("qJ[K`2\u0018wWv8\u0012\u0003") + var3 + "]");
            return;
         }

         SnmpVarBind var5 = var4.get(0);
         SnmpOid var6 = var5.getOid();
         if (var6.compareTo(this.r) < 0 || this.r.contains(var6) && var6.compareTo(this.r) != 0) {
            if (var5.getValue().getTag() == 130) {
               System.out.println(a("\u0018$z\u0019]\u001bJspP"));
               if (var14 == 0) {
                  return;
               }
            }

            SnmpOidInfo var7 = var6.getOidInfo();
            if (var7 == null) {
               System.out.print(a("\u0019/xxG\u0011>\u0004\u0019"));
               System.out.print(var6);
               System.out.print("=");
               System.out.println(var5.getValueString());
               Element var8 = new Element(a("1\u000f__"));
               var8.addAttribute(a("2\u0003Z"), var6.toNumericString());
               var8.addAttribute(a("+\u000bRLw"), var5.getValue().toString());
               var8.addAttribute(a(")\u0013N\\"), var5.getValue().getTypeShortString());
               this.n.addContent(var8);
               if (var14 == 0) {
                  return;
               }
            }

            SnmpOid var17 = var7.getOid();
            SnmpOid var19;
            if (var7 instanceof SnmpObjectInfo) {
               label104: {
                  SnmpObjectInfo var9 = (SnmpObjectInfo)var7;
                  if (var9.isScalar()) {
                     Element var10;
                     label73: {
                        var10 = new Element(a("1\u000f__"));
                        var10.addAttribute(a("2\u0003Z"), var6.toString());
                        if (var5.getValue() instanceof SnmpOid) {
                           SnmpOid var11 = (SnmpOid)var5.getValue();
                           var10.addAttribute(a("+\u000bRLw"), var11.toNumericString());
                           if (var14 == 0) {
                              break label73;
                           }
                        }

                        var10.addAttribute(a("+\u000bRLw"), var5.getValue().toString());
                     }

                     var10.addAttribute(a(")\u0013N\\"), var5.getValue().getTypeShortString());
                     this.n.addContent(var10);
                     System.out.println(a("\u0006\u0019]X~<\u0018c\u00032") + var6 + "=" + var5.getValueString());
                     if (var14 == 0) {
                        break label104;
                     }
                  }

                  if (this.o == null || !this.o.getOid().contains(var6)) {
                     var19 = var17.getParent(2);

                     try {
                        this.o = (SnmpTableInfo)var19.getOidInfo();
                        this.p = new Element(a(")\u000b\\Uw"));
                        this.p.addAttribute(a("2\u0003Z"), this.o.getOid().toString());
                        this.n.addContent(this.p);
                        this.q = new Hashtable();
                        System.out.println(a("\u0006\u001e_[~87\u0004\u0019") + var19);
                     } catch (ClassCastException var15) {
                        System.out.println(a("\u0014\u0004HX~4\u000e\u001eMs?\u0006[\u00032") + var19);
                        System.exit(1);
                     }
                  }

                  try {
                     var19 = var6.suboid(var17.getLength());
                     Element var20 = (Element)this.q.get(var19);
                     if (var20 == null) {
                        var20 = new Element(a("/\u0005I"));
                        var20.addAttribute(a("4\u0004Z\\j"), var19.toNumericString());
                        this.p.addContent(var20);
                        this.q.put(var19, var20);
                     }

                     Element var12;
                     label61: {
                        var12 = new Element(a("1\u000f__"));
                        var12.addAttribute(a("2\u0003Z"), var7.getName());
                        if (var5.getValue() instanceof SnmpOid) {
                           SnmpOid var13 = (SnmpOid)var5.getValue();
                           var12.addAttribute(a("+\u000bRLw"), var13.toNumericString());
                           if (var14 == 0) {
                              break label61;
                           }
                        }

                        var12.addAttribute(a("+\u000bRLw"), var5.getValue().toString());
                     }

                     var12.addAttribute(a(")\u0013N\\"), var5.getValue().getTypeShortString());
                     var20.addContent(var12);
                     System.out.println(a("}JeZ}1\u001fSWOgJ") + var6 + "=" + var5.getValueString());
                  } catch (SnmpValueException var16) {
                     System.out.println(a("\u0014\u0004Z\\j}/LK}/P\u001e") + var16);
                  }
               }

               if (var14 == 0) {
                  return;
               }
            }

            Element var18;
            label52: {
               var18 = new Element(a("1\u000f__"));
               var18.addAttribute(a("2\u0003Z"), var6.toNumericString());
               if (var5.getValue() instanceof SnmpOid) {
                  var19 = (SnmpOid)var5.getValue();
                  var18.addAttribute(a("+\u000bRLw"), var19.toNumericString());
                  if (var14 == 0) {
                     break label52;
                  }
               }

               var18.addAttribute(a("+\u000bRLw"), var5.getValue().toString());
            }

            var18.addAttribute(a(")\u0013N\\"), var5.getValue().getTypeShortString());
            this.n.addContent(var18);
            System.out.println(a("\b\u0004UW}*\u0004\u0004\u0019") + var6 + "=" + var5.getValueString());
         }
      }

   }

   public void handleReport(SnmpPendingRequest var1, int var2, int var3, SnmpVarBindList var4) {
      if (var2 != 0) {
         System.out.println("[" + Snmp.errorStatusToString(var2) + a("qJ[K`2\u0018wWv8\u0012\u0003") + var3 + "]");
      }

   }

   static void b() {
      System.out.println(a("WJ\u001elA\u001c-{3\u0018}J\u001e\u00192\u000e\u0004SIS:\u000fPM@8\u001aRPq<\u001eQK2\u0006G\u0001E}-\u001eWV|.7\u001e\u0005`2\u0005J\u0014}4\u000e\u0000\u0019Ia\u0018QVfp\u0005W],}D\u0010\u00172\u0000`4\u00192\u0019/mz@\u0014:jp]\u0013`4\u00192}J\u001eiw/\fQK\u007f.J_\u0019p<\u0019WZ2\u000e$si2\u0010#|\u0019e<\u0006U\u0019}-\u000fLXf4\u0005P\u0019f2J[Af/\u000b]M2<\u0006R\u0019_\u0014(4\u00192}J\u001eOs/\u0003_[~8JHX~(\u000fM\u0019q2\u0004JX{3\u000fZ\u0019g3\u000e[K2)\u0002[\u0019b/\u0005HPv8\u000e\u001e\u001e`2\u0005J\u001e2\u0012#zJ<WJ\u001e\u00192},QK24\u0004MMs3\t[\u00152)\u0005\u001eKw)\u0018W\\d8J_U~}MM@a)\u000fS\u001e22\bT\\q)\u0019\u0012\u0019k2\u001f\u001eN}(\u0006Z32}J\u001e\u0019a-\u000f]Pt$JJQw}MM@a)\u000fS\u001e2\u0012#z\u0019t/\u0005S\u0019A\u0013'nO p'w{<}>V\\2/\u000fMI}3\u0019[\u0019v<\u001e_32}J\u001e\u0019`8\t[Pd8\u000e\u001e_`2\u0007\u001eMz8JIX~6JQIw/\u000bJP}3JIP~1J\\\\2(\u0019[]2)\u0005\u001e^w3\u000fLXf8`\u001e\u00192}J_W2\u000e$si2<\r[Wf}2su2;\u0003R\\2;\u0005L\u0019g.\u000f\u001eN{)\u0002\u001eMz8Jz@|<\u0007WZA\u0013'n\u0011@tJ_^w3\u001e4\u00192}J\u001eJ{0\u001fRXf2\u0018\u0010\u0019B1\u000f_Jw}\u0004QMw}\u001eVXf}\u001eV\\2:\u000fP\\`<\u001e[]2\u0005'r\u0019t4\u0006[\u0019e4\u0006R\u0019q2\u0004JX{3`\u001e\u00192}J_\u0019a4\u0007NUw}\tQT\u007f(\u0004WMk}\b_Jw9J_Zq8\u0019M\u0019q2\u0004JK}1J]V|;\u0003YL`<\u001eWV|}\u000bP]20\u001fMM\u0018}J\u001e\u00192?\u000f\u001eT}9\u0003XPw9JWW22\u0018Z\\`}\u001eQ\u0019q2\u0004XPu(\u0018[\u0019s}\u000eW_t8\u0018[Wf}\u0019[Zg/\u0003J@2-\u000bLX\u007f8\u001e[Kas`4\u00192}J\u001ela<\r[\u0019w%\u000bSI~8\u0019\u00043\u0018}J\u001e\u00192}J\u001eSs+\u000b\u001eT}3\fQA<)\u0005QUy4\u001e\u0010J|0\u001a\u0010Xu8\u0004J\u0017A3\u0007Nxu8\u0004Jkw-\u0006WZs)\u0005L\u0019NWJ\u001e\u00192}J\u001e\u00192}J\u001e\u00192}J\u001e\u00192}J\u001e\u0014z2\u0019J\u0019v?\u0002QJfm[\u001e\u0014do\t\u001e\u0014q2\u0007SL|4\u001eG\u0019b(\bRPq}64\u00192}J\u001e\u00192}J\u001e\u00192}J\u001e\u00192}J\u001e\u00192p\u0007\u001e\u001bA\u0013'nO p'w{(\t)n\u0014_\u0014(\u0004pTp'w{0}64\u00192}J\u001e\u00192}J\u001e\u00192}J\u001e\u00192}J\u001e\u00192p'\u001e\u001b=+\u000bL\u0016}-\u001e\u0011j\\\u0010:\u0011T{?\u0019\u001c\u0019NWJ\u001e\u00192}J\u001e\u00192}J\u001e\u00192}J\u001e\u00192}J\u001eJk.\u001e[T24\u0004J\\`;\u000b]\\a}\u001e]I\u0018WJ\u001e\u00192}J\u001e\u0019x<\u001c_\u0019\u007f2\u0004XVjs\u001eQV~6\u0003J\u0017a3\u0007N\u0017s:\u000fPM<\u000e\u0004SIS:\u000fPM@8\u001aRPq<\u001eQK2\u0001`\u001e\u00192}J\u001e\u00192}J\u001e\u00192}J\u001e\u00192}J\u001e\u0019?5\u0005MM29\bVVa)Z\u000f\u0019?2JZ[z2\u0019Jfa4\u0007\u0010A\u007f1Jb32}J\u001e\u00192}J\u001e\u00192}J\u001e\u00192}J\u001e\u00192}GS\\f<\u000e_Ms}\tQKwp\u0007W[as\u0012SU2\u0001`\u001e\u00192}J\u001e\u00192}J\u001e\u00192}J\u001e\u00192}J\u001e\u0019?+Y\u001e\u0014g}\u000bZT{3J\u0013x2<\u000eSP|<\u001fJQ2p2\u001eXv0\u0003PI`4\u001c\u001e\u0014~}\u000bKMz\r\u0018WONWJ\u001e\u00192}J\u001e\u00192}J\u001e\u00192}J\u001e\u00192}J\u001e\u00192}J\u000f\u0017!s\\4\u00192\u0012:jp]\u001394\u00192}J\u0013O#!\u001c\fbq\u0000\u0016H\n2}J\u001e\u00192}J\u001e\u00192}P\u001eJ|0\u001a\u001eOw/\u0019WV|}J\u001e\u00192}J\u001e\u00192}J\u001e\u00192}J\u001e\u00192}1H\bOWJ\u001e\u00192p\u0007[Ms9\u000bJX2}J\u001e\u0005t4\u0006[Ws0\u000f\u0000\u0019(}\u0007[Ms9\u000bJX2;\u0003R\\2)\u0005\u001eU}<\u000e\u001e\u00192}J\u001e\u00192}J\u001e\u0019I0\u0003\\\u0014 \u0000`\u001e\u00192}GSb{?\u0019c\u00192}J\u001e\u0019.0\u0003\\\u0014~4\u0019J\u00072gJspP.JJV21\u0005_]2}J\u001e\u00192}J\u001e\u00192}J\u001e\u00192}J\u001e\u00192\u00069ptB+X\u0013t[\u001f74\u00192}J\u0013tn0\u0003\\]{/\u0019\u001e\u00192a\u000eWK?-\u000bJQ,}P\u001e}{/\u000f]M}/\u0003[J22\f\u001eI`8\tQTb4\u0006[]2\u0010#|J2}1PV|874\u00192}J\u0013KI8\u001eLPw.7\u001e\u00192a\u0018[M`4\u000fM\u00072}P\u001e\u001a22\f\u001eKw)\u0018W\\a}J\u001e\u00192}J\u001e\u00192}J\u001e\u00192}J\u001e\u00192}1\rd\u0018}J\u001e\u0019?)1WTw2\u001fJd2}J\u0002T{1\u0006WJ,}J\u001e\u000320\u000fMJs:\u000f\u001eM{0\u000fQLf}\u0003P\u0019\u007f4\u0006RPa}J\u001e\u00192}J\u001eb!mZ\u000ed\u0018}J\u001e\u0019?51QJf\u0000J\u001e\u00192}J\u0002Q}.\u001e\u0000\u00192}J\u001e\u00032\u000e\u0004SI2<\r[Wf}\u0002QJf}J\u001e\u00192}J\u001e\u00192}J\u001e\u00192}J\u001eb~2\t_Uz2\u0019Jd\u0018}J\u001e\u0019?-1QKf\u0000J\u001e\u00192}J\u0002I}/\u001e\u0000\u00192}J\u001e\u00032\u000e\u0004SI2<\r[Wf}\u001aQKf}J\u001e\u00192}J\u001e\u00192}J\u001e\u00192}J\u001eb#k[c32}J\u001e\u0014q\u0006\u0005STg3\u0003J@O}V]V\u007f0\u001fPPf$T\u0004\u0019a3\u0007N\u0019q2\u0007SL|4\u001eG\u0019f2JKJw}J\u001e\u00192}J\u001e\u00192}JeIg?\u0006WZOWJ\u001e\u00192p\u0005eLf;\u0003R\\O}J\u001e\u0005t4\u0006[Ws0\u000f\u0000\u0019(}\u0005KMb(\u001e\u001ea_\u0011JMP\u007f(\u0006_M}/JXP~8J\u001e\u00192}J\u001e\u0019Ia\u0002QJfc+Y\\|)DFT~\u0000`4\u00192\u000e$sidnJqiF\u0014%pj(WJ\u001e\u00192p\u001feJw/7\u001e\u0005a8\tKK{)\u0013\u0013La8\u0018\u0000\u0019(}?mt2(\u0019[K|<\u0007[\u00192}J\u001e\u00192}J\u001e\u00192}J\u001e\u00192}J\u001e\u0019I3\u0005P\\OWJ\u001e\u00192p+\u001e\u00192}J\u001e\u0005s(\u001eV\u0014b<\u0019MNvcJ\u001e\u0019(}+KMz8\u0004JPq<\u001eWV|}\u001a_Ja*\u0005L]2}J\u001e\u00192}J\u001e\u0019I3\u0005P\\OWJ\u001e\u00192p2\u001e\u00192}J\u001e\u0005b/\u0003H\u0014b<\u0019MNvcJ\u001e\u0019(}:LPd<\tG\u0019b<\u0019MN}/\u000e\u001e\u00192}J\u001e\u00192}J\u001e\u00192}J\u001e\u0019I3\u0005P\\OWJ\u001e\u00192p\u0006\u001e\u00192}J\u001e\u0005a8\tKK{)\u0013\u0013Uw+\u000fR\u0007(}\u0004Qxg)\u0002pVB/\u0003HEs(\u001eVw}\r\u0018WOn<\u001fJQB/\u0003H\u0019I<\u001fJQ\\2:LPd\u0000`\u001e\u00192}GP\u00192}J\u001e\u0019.>\u0005PMw%\u001e\u0013Ws0\u000f\u0000\u00192gJ]V|)\u000fFM23\u000bS\\2)\u0005\u001eLa8J\u001e\u00192}J\u001e\u00192}J\u001e\u00192\u0006H\u001cd\u0018"));
   }

   public void handleTimeout(SnmpPendingRequest var1) {
   }

   public void handleException(SnmpPendingRequest var1, Exception var2) {
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 93;
               break;
            case 1:
               var10003 = 106;
               break;
            case 2:
               var10003 = 62;
               break;
            case 3:
               var10003 = 57;
               break;
            default:
               var10003 = 18;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
