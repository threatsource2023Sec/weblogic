package monfox.toolkit.snmp.agent.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import monfox.jdom.Attribute;
import monfox.jdom.Document;
import monfox.jdom.Element;
import monfox.jdom.JDOMException;
import monfox.jdom.input.SAXBuilder;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpString;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpAccessControlModel;
import monfox.toolkit.snmp.agent.SnmpAccessPolicy;
import monfox.toolkit.snmp.agent.SnmpAgent;
import monfox.toolkit.snmp.agent.SnmpMib;
import monfox.toolkit.snmp.agent.SnmpMibCommand;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibLeaf;
import monfox.toolkit.snmp.agent.SnmpMibLeafFactory;
import monfox.toolkit.snmp.agent.SnmpMibNode;
import monfox.toolkit.snmp.agent.SnmpMibProxy;
import monfox.toolkit.snmp.agent.SnmpMibTable;
import monfox.toolkit.snmp.agent.SnmpMibTableRow;
import monfox.toolkit.snmp.agent.SnmpMibView;
import monfox.toolkit.snmp.agent.SnmpSecurityModel;
import monfox.toolkit.snmp.agent.modules.SnmpV2Mib;
import monfox.toolkit.snmp.agent.notify.SnmpNotifier;
import monfox.toolkit.snmp.agent.notify.SnmpNotifyTable;
import monfox.toolkit.snmp.agent.sim.SnmpSimLeaf;
import monfox.toolkit.snmp.agent.target.SnmpTarget;
import monfox.toolkit.snmp.agent.target.SnmpTargetAddrTable;
import monfox.toolkit.snmp.agent.target.SnmpTargetParamsTable;
import monfox.toolkit.snmp.agent.usm.Usm;
import monfox.toolkit.snmp.agent.vacm.SnmpCommunityTable;
import monfox.toolkit.snmp.agent.vacm.Vacm;
import monfox.toolkit.snmp.agent.vacm.VacmAccessTable;
import monfox.toolkit.snmp.agent.vacm.VacmSecurityToGroupTable;
import monfox.toolkit.snmp.agent.vacm.VacmViewTreeFamilyTable;
import monfox.toolkit.snmp.engine.SnmpEngineID;
import monfox.toolkit.snmp.engine.TransportProvider;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.SnmpObjectInfo;
import monfox.toolkit.snmp.mgr.SnmpParameters;
import monfox.toolkit.snmp.mgr.SnmpPeer;
import monfox.toolkit.snmp.mgr.SnmpSession;
import monfox.toolkit.snmp.v3.usm.USMUser;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlSnmpAgentLoader implements EntityResolver {
   private Map a;
   private Element b;
   private boolean c;
   private boolean d;
   private Map e;
   private static Logger f = null;
   public static boolean g;

   public XmlSnmpAgentLoader(String var1) throws FileNotFoundException, Exception {
      this(var1, false);
   }

   public XmlSnmpAgentLoader(String var1, boolean var2) throws FileNotFoundException, Exception {
      this.a = new HashMap();
      this.d = false;
      this.c = var2;
      this.a.put(c("3Miy?+Tx8*jYq:"), c("3Miy?+Tx8*jYq:"));
      this.initLog();
      this.a(var1);
   }

   public XmlSnmpAgentLoader(File var1, boolean var2) throws FileNotFoundException, Exception {
      this.a = new HashMap();
      this.d = false;
      this.c = var2;
      this.a.put(c("3Miy?+Tx8*jYq:"), c("3Miy?+Tx8*jYq:"));
      this.initLog();
      this.a(var1);
   }

   protected XmlSnmpAgentLoader(Object var1, boolean var2) throws FileNotFoundException, Exception {
      this.a = new HashMap();
      this.d = false;
      this.c = var2;
      this.a.put(c("3Miy?+Tx8*jYq:"), c("3Miy?+Tx8*jYq:"));
      this.initLog();
      this.a((Document)var1);
   }

   protected Object getRoot() {
      return this.b;
   }

   public void isOverwriteAllowed(boolean var1) {
      this.d = var1;
   }

   public boolean isOverwriteAllowed() {
      return this.d;
   }

   private void a(File var1) throws FileNotFoundException, JDOMException {
      FileInputStream var2 = new FileInputStream(var1);
      this.a((InputStream)var2);
   }

   private void a(String var1) throws FileNotFoundException, JDOMException {
      FileInputStream var2 = new FileInputStream(var1);
      this.a((InputStream)var2);
   }

   private void a(InputStream var1) throws JDOMException {
      SAXBuilder var2 = new SAXBuilder(true);
      var2.setEntityResolver(this);
      Document var3 = var2.build(var1);
      this.a(var3);
   }

   private void a(Document var1) throws JDOMException {
      this.b = var1.getRootElement();
      this.c = false;
      if (this.b.getAttributeValue(c("2_l5=7_")) != null && this.b.getAttributeValue(c("2_l5=7_")).equals(c("0Hk2"))) {
         this.c = true;
      }

   }

   public int getPort() throws NumberFormatException {
      String var1 = this.b.getAttributeValue(c("4Ul#"));
      Element var2 = this.b.getChild(c("*_j\u0014=*\\w0"));
      if (var1 == null) {
         return var2 == null ? 161 : -1;
      } else {
         return Integer.parseInt(var1);
      }
   }

   public int getTransport() throws NumberFormatException {
      String var1 = this.b.getAttributeValue(c("0H\u007f9!4Ul#"));
      Element var2 = this.b.getChild(c("*_j\u0014=*\\w0"));
      if (var1 == null) {
         return var2 == null ? 1 : -1;
      } else {
         return var1.equalsIgnoreCase(c("0Yn")) ? 2 : 1;
      }
   }

   public void loadNetConfig(SnmpAgent var1) {
      this.a((SnmpAgent)var1, (NetConfigListener)null);
   }

   public void loadNetConfig(NetConfigListener var1) {
      this.a((SnmpAgent)null, (NetConfigListener)var1);
   }

   private void a(SnmpAgent var1, NetConfigListener var2) {
      boolean var21 = g;
      Element var3 = this.b.getChild(c("*_j\u0014=*\\w0"));
      if (var3 != null) {
         String var4 = this.b.getAttributeValue(c("#Hq\"\""));
         List var5 = var3.getChildren();
         Iterator var6 = var5.iterator();

         while(var6.hasNext()) {
            label114: {
               Element var7 = (Element)var6.next();
               String var8;
               String var9;
               String var10;
               String var11;
               String var12;
               String var17;
               int var32;
               if (var7.getName().equals(c("-J_366_m$"))) {
                  var8 = var7.getAttributeValue(c("%^z%77I"));
                  var9 = var7.getAttributeValue(c("4Ul#"));
                  var10 = var7.getAttributeValue(c("0H\u007f9!4Ul#"));
                  var11 = var7.getAttributeValue(c("#Hq\"\""));
                  var12 = var7.getAttributeValue(c("([|2>"));
                  if (var11 == null) {
                     var11 = var4;
                  }

                  int var13 = 161;
                  if (var9 != null) {
                     try {
                        var13 = Integer.parseInt(var9);
                     } catch (NumberFormatException var28) {
                        String var15 = c("\u001fT{#\u0011+Tx>5\u0019\u0000>k;4{z3 !ImihdSp!3(Szw\"+Hjw$%Vk2u") + var9 + "'";
                        this.b(var15);
                        f.error(var15, var28);
                        if (!var21) {
                           continue;
                        }
                     }
                  }

                  InetAddress var14 = null;
                  String var16;
                  if (var8 != null) {
                     try {
                        var14 = InetAddress.getByName(var8);
                     } catch (Exception var27) {
                        var16 = c("\u001fT{#\u0011+Tx>5\u0019\u0000>k;4{z3 !ImihdSp!3(Szw3 ^l2!7\u001a9") + var8 + "'";
                        this.b(var16);
                        f.error(var16, var27);
                        if (!var21) {
                           continue;
                        }
                     }
                  }

                  var32 = 1;
                  if (var10.equalsIgnoreCase(c("\u0010yN"))) {
                     var32 = 2;
                  }

                  label107: {
                     if (var1 != null) {
                        try {
                           TransportProvider var33 = TransportProvider.newInstance(var32, var14, var13);
                           var1.getEngine().addTransportProvider(var33);
                           var17 = c("\u001fT{#\u0011+Tx>5\u0019\u0000>;;7N{9;*]>8<~\u001a") + var8 + ":" + var13;
                           this.b(var17);
                           f.debug(var17);
                           break label107;
                        } catch (Exception var29) {
                           var17 = c("\u001fT{#\u0011+Tx>5\u0019\u0000>k;4{z3 !ImihdY\u007f9<+N>><-Nw6>-@{w3 ^l2!7\u001a9") + var8 + "'";
                           this.b(var17);
                           f.error(var17, var29);
                           if (!var21) {
                              continue;
                           }
                        }
                     }

                     if (var2 != null) {
                        try {
                           var2.processIPAddress(var11, var12, var14, var13, var32);
                           var16 = c("\u001fT{#\u0011+Tx>5\u0019\u0000>;;7N{9;*]>8<~\u001a") + var8 + ":" + var13;
                           this.b(var16);
                           f.debug(var16);
                        } catch (Exception var26) {
                           var17 = c("\u001fT{#\u0011+Tx>5\u0019\u0000>k;4{z3 !ImihdY\u007f9<+N>><-Nw6>-@{w3 ^l2!7\u001a9") + var8 + "'";
                           this.b(var17);
                           f.error(var17, var26);
                           if (!var21) {
                              continue;
                           }
                        }
                     }
                  }

                  if (!var21) {
                     break label114;
                  }
               }

               if (var7.getName().equals(c("-JL6<#_"))) {
                  var8 = var7.getAttributeValue(c("7N\u007f%&"));
                  var9 = var7.getAttributeValue(c("!Tz"));
                  var10 = var7.getAttributeValue(c(")[m<"));
                  var11 = var7.getAttributeValue(c("4Ul#"));
                  var12 = var7.getAttributeValue(c("0H\u007f9!4Ul#"));
                  String var30 = var7.getAttributeValue(c("#Hq\"\""));
                  if (var30 == null) {
                     var30 = var4;
                  }

                  byte var31 = 1;
                  if (var12.equalsIgnoreCase(c("\u0010yN"))) {
                     var31 = 2;
                  }

                  var32 = 161;
                  if (var11 != null) {
                     try {
                        var32 = Integer.parseInt(var11);
                     } catch (NumberFormatException var25) {
                        var17 = c("\u001fT{#\u0011+Tx>5\u0019\u0000>k;4h\u007f95!\u0004$w;*L\u007f;; \u001an8 0\u001ah6>1_>p") + var11 + "'";
                        this.b(var17);
                        f.error(var17, var25);
                        if (!var21) {
                           continue;
                        }
                     }
                  }

                  InetAddress var34 = null;

                  try {
                     var34 = InetAddress.getByName(var8);
                  } catch (Exception var24) {
                     String var18 = c("\u001fT{#\u0011+Tx>5\u0019\u0000>k;4h\u007f95!\u0004$w;*L\u007f;; \u001am#36N>66 H{$!d\u001d") + var8 + "'";
                     this.b(var18);
                     f.error(var18, var24);
                     if (!var21) {
                        continue;
                     }
                  }

                  InetAddress var35 = null;

                  try {
                     var35 = InetAddress.getByName(var9);
                  } catch (Exception var23) {
                     String var19 = c("\u001fT{#\u0011+Tx>5\u0019\u0000>k;4h\u007f95!\u0004$w;*L\u007f;; \u001a{96d[z3 !Imwu") + var9 + "'";
                     this.b(var19);
                     f.error(var19, var23);
                     if (!var21) {
                        continue;
                     }
                  }

                  InetAddress var36 = null;
                  if (var10 != null) {
                     try {
                        var36 = InetAddress.getByName(var10);
                     } catch (Exception var22) {
                        String var20 = c("\u001fT{#\u0011+Tx>5\u0019\u0000>k;4h\u007f95!\u0004$w;*L\u007f;; \u001ap2&dW\u007f$9d\u001d") + var10 + "'";
                        this.b(var20);
                        f.error(var20, var22);
                        if (!var21) {
                           continue;
                        }
                     }
                  }

                  this.a(var1, var2, var34, var35, var36, var32, var31, var30);
               }
            }

            if (var21) {
               break;
            }
         }

      }
   }

   private void a(SnmpAgent param1, NetConfigListener param2, InetAddress param3, InetAddress param4, InetAddress param5, int param6, int param7, String param8) {
      // $FF: Couldn't be decompiled
   }

   public String getMetadataFile() {
      String var1 = this.b.getAttributeValue(c(")_j66%N\u007f"));
      if (var1 == null || var1.equals("")) {
         var1 = null;
      }

      return var1;
   }

   public String getSecurityProvider() {
      String var1 = this.b.getAttributeValue(c("7_}\" -Ng\u0007 +Lw376"));
      if (var1 == null || var1.equals("")) {
         var1 = c("'Usy!1T04 =Jj8|4Hq!; _ly\u00011TT\u0014\u0017");
      }

      return var1;
   }

   public String getLogFile() {
      String var1 = this.b.getAttributeValue(c("(Uy"));
      if (var1 == null || var1.equals("")) {
         var1 = null;
      }

      return var1;
   }

   public void initLog() {
      f = Logger.getInstance(c("\u001cWr\u0004<)J_07*NR83 _l"));
   }

   public SnmpEngineID getEngineID() {
      if (f.isDebugEnabled()) {
         f.debug(c("\bU\u007f3;*]>\u0012<#Sp2\u001b\u0000"));
      }

      String var1 = this.b.getAttributeValue(c("!Ty><!sZ"));
      String var2 = this.b.getAttributeValue(c("!Tj2 4Hw$7"));
      if (f.isDebugEnabled()) {
         f.debug(c("!Ty><!sZj") + var1);
         f.debug(c("!Tj2 4Hw$7y") + var2);
      }

      SnmpEngineID var3 = null;
      if (var1 != null && !"".equals(var1)) {
         try {
            SnmpString var4 = new SnmpString();
            var4.fromHexString(var1);
            var3 = new SnmpEngineID(var4.toByteArray(), true);
         } catch (SnmpValueException var5) {
            f.warn(c("\u0007[p9=0\u001aW9;0S\u007f;;>_>\u0012<#Sp2\u001b\u0000\u001a6><2[r>6d\\q%?%N7wz") + var5 + ")");
         }
      } else if (var2 != null && !"".equals(var2)) {
         try {
            var3 = new SnmpEngineID((long)Integer.parseInt(var2), InetAddress.getLocalHost().getAddress(), true);
         } catch (UnknownHostException var6) {
            f.warn(c("\u0007[p9=0\u001aW9;0S\u007f;;>_>\u0012<#Sp2\u001b\u0000\u001a6") + var6 + ")");
         } catch (NumberFormatException var7) {
            f.warn(c("\rTh6>-^>\u0012<0_l' -I{wz") + var2 + ")");
         }
      }

      if (f.isDebugEnabled()) {
         f.debug(c("\u0017Ts'\u0017*]w97\r~#") + var3);
      }

      return var3;
   }

   public void loadMib(SnmpMib var1) throws Exception {
      this.loadMib(var1, c(")S|"));
   }

   public void loadMib(SnmpMib var1, String var2) throws Exception {
      boolean var7 = g;
      List var3 = this.b.getChildren(c("7_m$;+T"));
      Iterator var4 = var3.iterator();

      Element var5;
      while(true) {
         if (var4.hasNext()) {
            var5 = (Element)var4.next();
            this.a(var5);
            if (var7) {
               break;
            }

            if (!var7) {
               continue;
            }
         }

         var5 = this.b.getChild(var2);
         break;
      }

      if (var5 != null) {
         var3 = var5.getChildren(c("(_\u007f1"));
         var4 = var3.iterator();

         Element var6;
         label65: {
            while(var4.hasNext()) {
               var6 = (Element)var4.next();
               this.a(var6, var1);
               if (var7) {
                  break label65;
               }

               if (var7) {
                  break;
               }
            }

            var3 = var5.getChildren(c("'Us:3*^"));
         }

         var4 = var3.iterator();

         do {
            if (!var4.hasNext()) {
               var3 = var5.getChildren(c("4Hq/+"));
               break;
            }

            var6 = (Element)var4.next();
            this.b(var6, var1);
         } while(!var7 || !var7);

         var4 = var3.iterator();

         label46: {
            while(var4.hasNext()) {
               var6 = (Element)var4.next();
               this.c(var6, var1);
               if (var7) {
                  break label46;
               }

               if (var7) {
                  break;
               }
            }

            var3 = var5.getChildren(c("0[|;7"));
         }

         var4 = var3.iterator();

         do {
            if (!var4.hasNext()) {
               return;
            }

            var6 = (Element)var4.next();
            this.d(var6, var1);
         } while(!var7);
      }

      f.debug(c("*U>p") + var2 + c("c\u001a{;7)_p#r4H{$7*N"));
   }

   private void a(SnmpAgent var1) throws SnmpException, UnknownHostException {
      boolean var11 = g;
      Element var2 = this.b.getChild(c("*Uj>4-_l"));
      if (var2 != null) {
         SnmpNotifier var3 = var1.getNotifier();
         List var4 = var2.getChildren(c("0H\u007f'\u0006%Hy2&"));
         Iterator var5 = var4.iterator();

         while(var5.hasNext()) {
            label21: {
               Element var6 = (Element)var5.next();
               String var7 = var6.getAttributeValue(c(",Um#"));
               String var8 = var6.getAttributeValue(c("4Ul#"));
               String var9 = var6.getAttributeValue(c("'Us:'*Sj."));
               String var10 = var6.getAttributeValue(c("2_l$;+T"));
               if ("2".equals(var10)) {
                  var3.addTrapV2Target(var7, Integer.parseInt(var8), var9);
                  if (!var11) {
                     break label21;
                  }
               }

               var3.addTrapV1Target(var7, Integer.parseInt(var8), var9);
            }

            if (var11) {
               break;
            }
         }

      }
   }

   private Properties a(List var1) throws SnmpValueException, SnmpMibException {
      boolean var5 = g;
      Properties var2 = new Properties();
      Iterator var3 = var1.iterator();

      Properties var10000;
      while(true) {
         if (var3.hasNext()) {
            Attribute var4 = (Attribute)var3.next();
            var10000 = var2;
            if (var5) {
               break;
            }

            var2.setProperty(var4.getName(), var4.getValue());
            if (!var5) {
               continue;
            }
         }

         var10000 = var2;
         break;
      }

      return var10000;
   }

   public void loadSnmpV2Mib(SnmpAgent var1) throws SnmpValueException, SnmpMibException, SnmpException {
      Element var2 = this.b.getChild(c("7Ts'\u0004vww5"));
      if (var2 != null) {
         SnmpV2Mib var3 = new SnmpV2Mib(var1);
         var1.addMibModule(var3);
         Properties var4 = this.a(var2.getAttributes());
         var3.loadFromProperties(var4);
         var3.sendColdStart();
      }
   }

   public void loadAgentConfig(SnmpAgent var1) throws SnmpValueException, SnmpMibException, SnmpException, UnknownHostException {
      if (var1.isV3()) {
         this.b(var1);
         this.c(var1);
      }

      this.loadAccessPolicies(var1);
      this.a(var1);
      this.d(var1);
      this.e(var1);
      this.loadSnmpV2Mib(var1);
   }

   public void loadAccessPolicies(SnmpAgent var1) throws SnmpValueException {
      List var2 = this.b.getChildren(c("%Y}2!7jq;;'C"));
      Iterator var3 = var2.iterator();

      while(var3.hasNext()) {
         Element var4 = (Element)var3.next();
         this.a(var4, var1);
         if (g) {
            break;
         }
      }

   }

   private void a(Element var1) throws SnmpException, UnknownHostException {
      String var2;
      String var3;
      int var4;
      int var8;
      long var9;
      SnmpParameters var11;
      label11: {
         var2 = var1.getAttributeValue(c("([|2>"));
         var3 = var1.getAttributeValue(c(",Um#"));
         var4 = Integer.parseInt(var1.getAttributeValue(c("4Ul#")));
         int var5 = Integer.parseInt(var1.getAttributeValue(c("2_l$;+T")));
         String var6 = var1.getAttributeValue(c("6_\u007f3\u0011+Ws\"<-Ng"));
         String var7 = var1.getAttributeValue(c("3Hw#7\u0007Us:'*Sj."));
         var8 = Integer.parseInt(var1.getAttributeValue(c("6_j%;!I")));
         var9 = Long.parseLong(var1.getAttributeValue(c("0Ss2=1N")));
         var11 = new SnmpParameters(var6, var7, c("4O|;;'"));
         if (var5 == 1) {
            var11.setVersion(0);
            if (!g) {
               break label11;
            }
         }

         var11.setVersion(1);
      }

      SnmpPeer var12 = new SnmpPeer(var3, var4);
      var12.setParameters(var11);
      var12.setMaxRetries(var8);
      var12.setTimeout(var9);
      SnmpSession var13 = new SnmpSession(var12);
      this.addSession(var2, var13);
   }

   public void addSession(String var1, SnmpSession var2) {
      if (this.e == null) {
         this.e = new HashMap();
      }

      this.e.put(var1, var2);
   }

   public SnmpSession getSession(String var1) {
      return this.e == null ? null : (SnmpSession)this.e.get(var1);
   }

   public Map getSessions() {
      return this.e;
   }

   private void a(Element var1, SnmpMib var2) throws SnmpMibException, SnmpValueException {
      String var3 = var1.getAttributeValue(c("+Sz")).trim();
      String var4 = var1.getAttributeValue(c("2[r\"7"));
      String var5 = var1.getAttributeValue(c("0Cn2"));
      String var6 = var1.getAttributeValue(c("%Y}2!7"));
      String var7 = var1.getAttributeValue(c("\"Op4&-Up"));
      SnmpOid var8 = null;
      SnmpOid var9 = null;
      if (var3.length() == 0) {
         throw new SnmpValueException(c("-Th6>-^>8; \u001a62?4Ng~"));
      } else {
         SnmpOid var10 = new SnmpOid(var2.getMetadata(), var3);
         if (var10.getOidInfo() != null && var10.getOidInfo() instanceof SnmpObjectInfo) {
            if (var3.endsWith(c("j\n"))) {
               var8 = new SnmpOid(var2.getMetadata(), var3);
               var9 = var8.getParent();
            } else {
               var9 = new SnmpOid(var2.getMetadata(), var3);
               var8 = (SnmpOid)var9.clone();
               var8.append(0L);
            }
         } else {
            var8 = new SnmpOid(var2.getMetadata(), var3);
            var9 = var8.getParent();
         }

         SnmpMibNode var11;
         if (this.d) {
            var11 = var2.get(var8);
            if (var11 != null) {
               var2.remove(var11);
            }
         }

         var11 = null;
         if (f.isDebugEnabled()) {
            f.debug(c("4Hq477I>;7%\\$w") + var8 + "=" + var4 + c("d\u0012jj") + var5 + c("h\u001a\u007f41!Imj") + var6 + ")");
         }

         SnmpMibLeaf var13;
         if (var7 == null) {
            var13 = new SnmpMibLeaf(var9, var8);
         } else {
            SnmpMibLeafFactory var12 = SnmpSimLeaf.getFactory(var7);
            var13 = var12.getInstance((SnmpMibTable)null, var9, var8);
         }

         if (var5 != null) {
            var13.setType(SnmpValue.stringToType(var5));
         }

         if (var6 != null) {
            var13.setAccess(SnmpObjectInfo.stringToAccess(var6));
         }

         if (var4 != null) {
            var13.setValue(var4);
         }

         var2.add(var13);
      }
   }

   private void b(Element var1, SnmpMib var2) throws SnmpMibException, SnmpValueException {
      String var3 = var1.getAttributeValue(c("+Sz"));
      String var4 = var1.getAttributeValue(c("#_j"));
      String var5 = var1.getAttributeValue(c("0Cn2"));
      String var6 = var1.getAttributeValue(c("%Y}2!7"));
      SnmpMibCommand var7 = new SnmpMibCommand(var2.getMetadata(), var3, var4);
      if (var5 != null) {
         var7.setType(SnmpValue.stringToType(var5));
      }

      if (var6 != null) {
         var7.setAccess(SnmpObjectInfo.stringToAccess(var6));
      }

      var2.add(var7);
   }

   private void c(Element var1, SnmpMib var2) throws SnmpMibException, SnmpValueException {
      String var3 = var1.getAttributeValue(c("+Sz"));
      String var4 = var1.getAttributeValue(c(")[f\u0018; "));
      String var5 = var1.getAttributeValue(c("7_m$;+T"));
      SnmpSession var6 = this.getSession(var5);
      if (var6 == null) {
         throw new SnmpMibException(c("7_m$;+T>9=0\u001ax8'*^$w") + var6);
      } else {
         SnmpMibProxy var7;
         label23: {
            if (var4 == null || var4.equals("")) {
               var7 = new SnmpMibProxy(var2.getMetadata(), var3, var6);
               if (!g) {
                  break label23;
               }
            }

            var7 = new SnmpMibProxy(var2.getMetadata(), var3, var4, var6);
         }

         var2.add(var7);
      }
   }

   private void d(Element var1, SnmpMib var2) throws SnmpMibException, SnmpValueException {
      boolean var10 = g;
      String var3 = var1.getAttributeValue(c("+Sz"));
      String var4 = var1.getAttributeValue(c("*Os\u00184\u0007Ur\"?*I"));
      String var5 = var1.getAttributeValue(c("1I{\u00137\"L\u007f;!"));
      SnmpMibTable var6 = null;
      if (this.d) {
         SnmpMibNode var7 = var2.get(var3);
         if (var7 != null && var7 instanceof SnmpMibTable) {
            var6 = (SnmpMibTable)var7;
         }
      }

      Iterator var8;
      Element var9;
      List var12;
      label66: {
         if (var6 == null) {
            if (var2.getMetadata() == null) {
               throw new SnmpMibException(c(")_j66%N\u007fw !Kk> !^>1=6\u001aj60(_m"));
            }

            var6 = new SnmpMibTable(var2.getMetadata(), var3);
            if (var5 != null && c("0Hk2").equals(var5)) {
               try {
                  var6.setInitialValuesFromDefVals();
               } catch (Exception var11) {
                  if (var10) {
                     throw new SnmpMibException(c(")_j66%N\u007fw !Kk> !^>1=6\u001aj60(_m"));
                  }
               }
            }

            var2.add(var6);
            var12 = var1.getChildren(c("'Ur\"?*"));
            var8 = var12.iterator();

            while(var8.hasNext()) {
               var9 = (Element)var8.next();
               this.a(var9, var6, var2);
               if (var10 && var10) {
                  break label66;
               }
            }
         }

         var12 = var1.getChildren(c("6Ui"));
      }

      var8 = var12.iterator();

      while(var8.hasNext()) {
         var9 = (Element)var8.next();
         this.a(var9, var6);
         if (var10) {
            break;
         }
      }

   }

   private void a(Element var1, SnmpMibTable var2, SnmpMib var3) throws SnmpMibException, SnmpValueException {
      String var4 = var1.getAttributeValue(c("+Sz"));
      String var5 = var1.getAttributeValue(c("'V\u007f$!"));
      String var6 = var1.getAttributeValue(c(" _x!3("));
      if (var5 != null) {
         label31: {
            SnmpMibLeafFactory var7;
            try {
               Class var8 = Class.forName(var5);
               var7 = (SnmpMibLeafFactory)var8.newInstance();
            } catch (Exception var10) {
               throw new SnmpMibException(var10.toString());
            }

            if (c(" _x6'(N").equals(var4)) {
               var2.setDefaultFactory(var7);
               if (!g) {
                  break label31;
               }
            }

            var2.setFactory(var4, var7);
         }
      }

      if (var6 != null) {
         try {
            SnmpOid var11 = new SnmpOid(var3.getMetadata(), var4);
            SnmpValue var12 = SnmpValue.getInstance((SnmpObjectInfo)var11.getOidInfo(), var6);
            var2.setInitialValue(var11, var12);
         } catch (Exception var9) {
            f.error(c("!B}2\"0Sq9r-T>' +Y{$!-Tyw6!\\h6>d\u001d") + var6 + c("c\u001ax8 dLq;')T>p") + var4 + c("c\u001a"), var9);
            throw new SnmpValueException(c("-Th6>-^>37\"L\u007f;rc") + var6 + c("c\u001ax8 dYq;')T>p") + var4 + "'");
         }
      }

   }

   private void a(Element var1, SnmpMibTable var2) throws SnmpMibException, SnmpValueException {
      boolean var15 = g;
      String var3 = var1.getAttributeValue(c("-Tz2*"));
      SnmpMibTableRow var4 = null;
      if (this.d) {
         var4 = var2.getRow(var3);
      }

      boolean var5 = false;
      if (var4 == null) {
         var4 = var2.addRow(var3);
         var2.removeRow(var4.getIndex());
         var5 = true;
      }

      List var6 = var1.getChildren(c("(_\u007f1"));
      Iterator var7 = var6.iterator();

      while(true) {
         if (var7.hasNext()) {
            Element var8 = (Element)var7.next();
            String var9 = var8.getAttributeValue(c("+Sz"));
            String var10 = var8.getAttributeValue(c("2[r\"7"));
            String var11 = var8.getAttributeValue(c("\"Op4&-Up"));
            SnmpMibLeaf var12 = var4.getLeaf(var9);
            if (var15) {
               break;
            }

            if (var12 != null && var11 != null) {
               SnmpMibLeafFactory var13 = SnmpSimLeaf.getFactory(var11);
               SnmpMibLeaf var14 = var13.getInstance((SnmpMibTable)null, var12.getClassOid(), var12.getInstanceOid());
               var4.setLeaf((int)var12.getClassOid().getLast(), var14);
               if (!var5) {
                  var2.getMib().remove((SnmpMibNode)var12);
                  var2.getMib().add(var14);
               }
            }

            if (var10 != null) {
               var4.setLeafValue(var9, var10);
            }

            if (!var15) {
               continue;
            }
         }

         if (var5) {
            var2.addRow(var4);
         }
         break;
      }

   }

   private void a(Element var1, SnmpAgent var2) throws SnmpValueException {
      String var3;
      boolean var5;
      boolean var14;
      label76: {
         var14 = g;
         var3 = var1.getAttributeValue(c("'Us:'*Sj."));
         String var4 = var1.getAttributeValue(c("%Y}2!7wq37"));
         if (c("\u0016\u007f_\u0013\r\u0013hW\u0003\u0017").equals(var4)) {
            var5 = true;
            if (!var14) {
               break label76;
            }
         }

         var5 = false;
      }

      SnmpAccessPolicy var6 = new SnmpAccessPolicy(var3, var5);
      SnmpMibView var7 = var6.getView();
      SnmpMib var8 = var2.getMib();
      SnmpMetadata var9 = null;
      if (var8 != null) {
         var9 = var8.getMetadata();
      }

      List var10 = var1.getChildren(c("-T};' _"));
      Iterator var11 = var10.iterator();

      Element var12;
      SnmpOid var13;
      while(true) {
         if (var11.hasNext()) {
            var12 = (Element)var11.next();
            var13 = new SnmpOid(var9, var12.getAttributeValue(c("+Sz")));
            var7.include(var13);
            if (!var14 || !var14) {
               continue;
            }
            break;
         }

         var10 = var1.getChildren(c("-T};' _M\"0\u0010H{2"));
         break;
      }

      var11 = var10.iterator();

      while(true) {
         if (var11.hasNext()) {
            var12 = (Element)var11.next();
            var13 = new SnmpOid(var9, var12.getAttributeValue(c("+Sz")));
            var7.includeSubTree(var13);
            if (var14) {
               break;
            }

            if (!var14) {
               continue;
            }
         }

         var10 = var1.getChildren(c("!B};' _"));
         break;
      }

      var11 = var10.iterator();

      while(true) {
         if (var11.hasNext()) {
            var12 = (Element)var11.next();
            var13 = new SnmpOid(var9, var12.getAttributeValue(c("+Sz")));
            var7.exclude(var13);
            if (var14) {
               break;
            }

            if (!var14) {
               continue;
            }
         }

         var10 = var1.getChildren(c("!B};' _M\"0\u0010H{2"));
         break;
      }

      var11 = var10.iterator();

      while(true) {
         if (var11.hasNext()) {
            var12 = (Element)var11.next();
            var13 = new SnmpOid(var9, var12.getAttributeValue(c("+Sz")));
            var7.excludeSubTree(var13);
            if (var14) {
               break;
            }

            if (!var14) {
               continue;
            }
         }

         if (c(" _x6'(N").equals(var3)) {
            var2.setDefaultAccessPolicy(var6);
         }

         var2.addAccessPolicy(var6);
         break;
      }

   }

   public SnmpMetadata loadMetadata() throws SnmpException, IOException {
      return this.loadMetadata((SnmpMetadata)null);
   }

   public SnmpMetadata loadMetadata(SnmpMetadata var1) throws SnmpException, IOException {
      boolean var13 = g;
      SnmpMetadata var2 = var1;
      if (var1 == null) {
         var2 = new SnmpMetadata();
      }

      String var3 = this.getMetadataFile();
      if (var3 != null && !"".equals(var3)) {
         var2.loadFile(var3);
      }

      Element var4 = this.b.getChild(c(")_j66%N\u007f"));
      if (var4 == null) {
         return var2;
      } else {
         String var5 = var4.getAttributeValue(c("7_\u007f%1,j\u007f#:"));
         if (var5 != null && !"".equals(var5)) {
            String var6 = SnmpMetadata.getRepository().getSearchPath();
            var5 = var5 + ";" + var6;
            SnmpMetadata.getRepository().setSearchPath(var5);
         }

         List var15 = var4.getChildren(c("\"Sr2"));
         Iterator var7 = var15.iterator();

         Element var10000;
         String var10001;
         while(true) {
            if (var7.hasNext()) {
               Element var8 = (Element)var7.next();
               var10000 = var8;
               var10001 = c("*[s2");
               if (var13) {
                  break;
               }

               String var9 = var8.getAttributeValue(var10001);
               var2.loadFile(var9);
               if (!var13) {
                  continue;
               }
            }

            var10000 = var4;
            var10001 = c(")Uz\">!");
            break;
         }

         List var16 = var10000.getChildren(var10001);
         Iterator var17 = var16.iterator();

         while(true) {
            if (var17.hasNext()) {
               Element var10 = (Element)var17.next();
               String var11 = var10.getAttributeValue(c("*[s2"));

               label46: {
                  try {
                     var2.getModule(var11);
                  } catch (SnmpValueException var14) {
                     var2.loadModule(var11);
                     break label46;
                  }

                  if (var13) {
                     break;
                  }
               }

               if (!var13) {
                  continue;
               }
            }

            SnmpFramework.setMetadata(var2);
            break;
         }

         return var2;
      }
   }

   private void b(SnmpAgent var1) throws SnmpException, UnknownHostException {
      boolean var18 = g;
      if (f.isDebugEnabled()) {
         f.debug(c("\bU\u007f3;*]>\u0002\u0001\t\u001aK$76\u001aJ60(_"));
      }

      Element var2 = this.b.getChild(c("1Is"));
      if (var2 != null) {
         SnmpSecurityModel var3 = var1.getSecurityModel();
         if (var3 != null && var3 instanceof Usm) {
            Usm var4 = (Usm)var3;
            List var5 = var2.getChildren(c("1Is\u0002!!H"));
            Iterator var6 = var5.iterator();

            while(var6.hasNext()) {
               String var8;
               String var9;
               String var10;
               String var11;
               String var13;
               String var14;
               int var15;
               short var16;
               label99: {
                  Element var7 = (Element)var6.next();
                  var8 = var7.getAttributeValue(c("7_}\u00193)_"));
                  var9 = var7.getAttributeValue(c("%Oj?\u0002%Im =6^"));
                  var10 = var7.getAttributeValue(c("%Oj?\u00026Uj81+V"));
                  var11 = var7.getAttributeValue(c("4Hw!\u0002%Im =6^"));
                  String var12 = var7.getAttributeValue(c("4Hw!\u00026Uj81+V"));
                  var13 = var7.getAttributeValue(c("%Oj?\u0019!C"));
                  var14 = var7.getAttributeValue(c("4Hw!\u0019!C"));
                  var15 = c("\u0017r_").equals(var10) ? 1 : 0;
                  var16 = 2;
                  if (c("\u0005\u007fM").equals(var12)) {
                     var16 = 4;
                     if (!var18) {
                        break label99;
                     }
                  }

                  if (c("\u0005\u007fMf`|").equals(var12)) {
                     var16 = 4;
                     if (!var18) {
                        break label99;
                     }
                  }

                  if (c("\u0005\u007fMfkv").equals(var12)) {
                     var16 = 5;
                     if (!var18) {
                        break label99;
                     }
                  }

                  if (c("\u0005\u007fMegr").equals(var12)) {
                     var16 = 6;
                     if (!var18) {
                        break label99;
                     }
                  }

                  if (c("w~[\u0004").equals(var12)) {
                     var16 = 14832;
                     if (!var18) {
                        break label99;
                     }
                  }

                  if (c("\u0010~[\u0004").equals(var12)) {
                     var16 = 14832;
                     if (!var18) {
                        break label99;
                     }
                  }

                  if (c("\u0010Hw'>!~[\u0004").equals(var12)) {
                     var16 = 14832;
                  }
               }

               try {
                  USMUser var17 = null;
                  if (var9 != null && var11 != null) {
                     var17 = new USMUser(var8, var9, var11, var15, var16);
                  } else if (var9 != null) {
                     var17 = new USMUser(var8, var9, var15);
                  } else if (var13 != null) {
                     var17 = USMUser.createKeyUser(var8, (String)var13, (String)var14, var15, var16);
                  } else {
                     var17 = new USMUser(var8);
                  }

                  var4.addUser(var17);
                  f.debug(c("\u0005^z26dom2 ~\u001a") + var8 + ")");
               } catch (NoSuchAlgorithmException var19) {
                  f.error(c("\u0007Uk;6dTq#r(U\u007f3r1I{%rc") + var8 + c("c\u001aK9!1Jn8 0_zw31Nv2<0S}6&-Upw\"6Uj81+V>p") + var10 + "'");
               }

               if (var18) {
                  break;
               }
            }

         } else {
            f.warn(c("\u0007[p9=0\u001ar83 \u001aK\u0004\u001fdN\u007f5>!I0w\u0013#_p#r*Ujw1+Tx>51H{3r\"Ulw\u0001*Wn\u0001a"));
         }
      }
   }

   private void c(SnmpAgent var1) throws SnmpException, UnknownHostException {
      boolean var27 = g;
      if (f.isDebugEnabled()) {
         f.debug(c("\bU\u007f3;*]>\u0001\u0013\u0007w>\u00033&V{$"));
      }

      Element var2 = this.b.getChild(c("2[}:"));
      if (var2 != null) {
         SnmpAccessControlModel var3 = var1.getAccessControlModel();
         if (var3 != null && var3 instanceof Vacm) {
            Vacm var4 = (Vacm)var3;
            SnmpCommunityTable var5 = var4.getCommunityTable();
            Element var6 = var2.getChild(c("7Ts'\u0011+Ws\"<-Ng\u00033&V{"));
            String var12;
            String var13;
            if (var6 != null) {
               List var7 = var6.getChildren(c("7Ts'\u0011+Ws\"<-Ng"));
               Iterator var8 = var7.iterator();

               while(var8.hasNext()) {
                  Element var9 = (Element)var8.next();
                  String var10 = var9.getAttributeValue(c("-Tz2*"));
                  String var11 = var9.getAttributeValue(c("'Us:'*Sj."));
                  var12 = var9.getAttributeValue(c("7_}\u00193)_"));
                  var13 = var9.getAttributeValue(c("'Up#7<NP6?!"));
                  var5.add(var10, var11, var12, var13);
                  f.debug(c("\u0005^z26dyq:?1Tw#+~\u001a") + var10 + c("d\u0012}8?)Op>&=\u0007") + var11 + c("h\u001am21\n[s2o") + var12 + c("h\u001a}8<0_f#\u001c%W{j") + var13 + ")");
                  if (var27) {
                     break;
                  }
               }
            }

            VacmSecurityToGroupTable var28 = var4.getSecurityToGroupTable();
            Element var29 = var2.getChild(c("2[}:\u0001!Yk%;0CJ8\u00156Uk'\u0006%Xr2"));
            List var30 = var29.getChildren(c("2[}:\u0001!Yk%;0CJ8\u00156Uk'"));
            Iterator var31 = var30.iterator();

            while(var31.hasNext()) {
               String var14;
               byte var15;
               label128: {
                  Element var32 = (Element)var31.next();
                  var12 = var32.getAttributeValue(c("7_}\u001a= _r"));
                  var13 = var32.getAttributeValue(c("7_}\u00193)_"));
                  var14 = var32.getAttributeValue(c("#Hq\"\"\n[s2"));
                  var15 = 3;
                  if (c("2\u000b").equals(var12)) {
                     var15 = 1;
                     if (!var27) {
                        break label128;
                     }
                  }

                  if (c("2\b}").equals(var12)) {
                     var15 = 2;
                     if (!var27) {
                        break label128;
                     }
                  }

                  if (c("1Is").equals(var12)) {
                     var15 = 3;
                  }
               }

               var28.add(var15, var13, var14);
               f.debug(c("\u0005^z26di{4'6Sj.r\u0010U>\u0010 +Onw\u0017*Nl.hd\u001a6$7'wq37(\u0007") + var12 + c("h\u001am21\n[s2o") + var13 + c("h\u001ay%=1JP6?!\u0007") + var14 + ")");
               if (var27) {
                  break;
               }
            }

            VacmAccessTable var33 = var4.getAccessTable();
            Element var34 = var2.getChild(c("2[}:\u0013'Y{$!\u0010[|;7"));
            List var35 = var34.getChildren(c("2[}:\u0013'Y{$!"));
            Iterator var36 = var35.iterator();

            String var20;
            String var21;
            String var22;
            String var23;
            int var24;
            while(var36.hasNext()) {
               String var16;
               String var17;
               String var19;
               byte var25;
               label130: {
                  Element var38 = (Element)var36.next();
                  var16 = var38.getAttributeValue(c("#Hq\"\"\n[s2"));
                  var17 = var38.getAttributeValue(c("'Up#7<NN%7\"Sf"));
                  String var18 = var38.getAttributeValue(c("'Up#7<NS6&'R"));
                  var19 = var38.getAttributeValue(c("7_}\u001a= _r"));
                  var20 = var38.getAttributeValue(c("7_}\u001b72_r"));
                  var21 = var38.getAttributeValue(c("6_\u007f3\u0004-_i\u00193)_"));
                  var22 = var38.getAttributeValue(c("3Hw#7\u0012S{ \u001c%W{"));
                  var23 = var38.getAttributeValue(c("*Uj>4=lw2%\n[s2"));
                  var24 = c("!B\u007f4&").equals(var18) ? 1 : 2;
                  var25 = 0;
                  if (c("2\u000b").equals(var19)) {
                     var25 = 1;
                     if (!var27) {
                        break label130;
                     }
                  }

                  if (c("2\b}").equals(var19)) {
                     var25 = 2;
                     if (!var27) {
                        break label130;
                     }
                  }

                  if (c("1Is").equals(var19)) {
                     var25 = 3;
                     if (!var27) {
                        break label130;
                     }
                  }

                  if (c("%Tg").equals(var19)) {
                     var25 = 0;
                  }
               }

               byte var26;
               label131: {
                  var26 = 0;
                  if (c("%Oj?\u00026Sh").equals(var20)) {
                     var26 = 3;
                     if (!var27) {
                        break label131;
                     }
                  }

                  if (c("%Oj?\u001c+jl>$").equals(var20)) {
                     var26 = 1;
                     if (!var27) {
                        break label131;
                     }
                  }

                  if (c("*U_\"&,tq\u0007 -L").equals(var20)) {
                     var26 = 0;
                  }
               }

               var33.add(var16, var17, var25, var26, var24, var21, var22, var23);
               f.debug(c("\u0005^z26dL\u007f4?\u0005Y}2!7n\u007f5>!\u001a[9&6C$wrl]l8'4t\u007f:7y") + var16 + c("h\u001a}8<0_f#\u00026_x>*y") + var17 + c("h\u001a}8<0_f#\u001f%N}?o") + var24 + c("h\u001am21\tUz2>y") + var19 + c("h\u001am21\b_h2>y") + var20 + c("h\u001al23 lw2%y") + var21 + c("h\u001ai%;0_H>73\u0007") + var22 + c("h\u001ap8&-\\g\u0001;!M#") + var23 + ")");
               if (var27) {
                  break;
               }
            }

            VacmViewTreeFamilyTable var39 = var4.getViewTreeFamilyTable();
            Element var37 = var2.getChild(c("2[}:\u0004-_i\u0003 !_X6?-Vg\u00033&V{"));
            List var40 = var37.getChildren(c("2[}:\u0004-_i\u0003 !_X6?-Vg"));
            Iterator var41 = var40.iterator();

            while(var41.hasNext()) {
               Element var42 = (Element)var41.next();
               var20 = var42.getAttributeValue(c("2S{ \u001c%W{"));
               var21 = var42.getAttributeValue(c("7O|# !_"));
               var22 = var42.getAttributeValue(c(")[m<"));
               var23 = var42.getAttributeValue(c("0Cn2"));
               var24 = c("-T};' _").equals(var23) ? 1 : 2;
               var39.add(var20, new SnmpOid(var21), var22.getBytes(), var24);
               f.debug(c("\u0005^z26dL\u007f4?\u0010H{2\u0014%Ww;+\u0010[|;7d\u007fp# =\u0000>wz2S{ \u001c%W{j") + var20 + c("h\u001am\"00H{2o") + var21 + c("h\u001as6!/\u0007") + var22 + c("h\u001aj.\"!\u0007") + var23 + ")");
               if (var27) {
                  break;
               }
            }

         } else {
            f.warn(c("\u0007[p9=0\u001ar83 \u001aH\u0016\u0011\t\u001aj60(_myr\u0005]{9&dTq#r'Up1;#Ol26d\\q%r\u0017Ts'\u0004w"));
         }
      }
   }

   private void d(SnmpAgent var1) throws SnmpException, UnknownHostException {
      boolean var19 = g;
      if (f.isDebugEnabled()) {
         f.debug(c("\bU\u007f3;*]>\u0004<)JJ6 #_j\u00166 H>\u00033&V{"));
      }

      Element var2 = this.b.getChild(c("7Ts'\u0006%Hy2&"));
      if (var2 != null) {
         SnmpTarget var3 = var1.getTarget();
         SnmpTargetAddrTable var4 = var3.getAddrTable();
         Element var5 = var2.getChild(c("7Ts'\u0006%Hy2&\u0005^z%\u0006%Xr2"));
         String var11;
         String var12;
         String var13;
         String var14;
         if (var5 != null) {
            int var6 = 0;
            List var7 = var5.getChildren(c("7Ts'\u0006%Hy2&\u0005^z%"));
            Iterator var8 = var7.iterator();

            while(var8.hasNext()) {
               Element var9 = (Element)var8.next();
               String var10 = var9.getAttributeValue(c("*[s2"));
               var11 = var9.getAttributeValue(c(",Um#"));
               var12 = var9.getAttributeValue(c("4Ul#"));
               var13 = var9.getAttributeValue(c("0[y\u001b;7N"));
               var14 = var9.getAttributeValue(c("4[l6?7"));

               try {
                  if (var10 == null) {
                     var10 = var11 + "-" + var6++;
                  }

                  var4.add(var10, var11, Integer.parseInt(var12), var13, var14);
                  f.debug(c("\u0005^z26dip:\"\u0010[l070{z3 ~\u001a>\u007f<%W{j") + var10 + c("h\u001av8!0\u0007") + var11 + c("h\u001an8 0\u0007") + var12 + c("h\u001aj65\bSm#o") + var13 + c("h\u001an6 %Wmj") + var14 + ")");
               } catch (NumberFormatException var20) {
                  f.error(c("\u0006[zw\"+Hjw<1W|2 d\u0012") + var12 + c("m\u001ax8 dip:\"\u0010[l070{z3 d_p# =\u001a") + var10);
                  throw new SnmpException(c("\u0006[zw\"+Hjw<1W|2 ~\u001a") + var12);
               }

               if (var19) {
                  break;
               }
            }
         }

         SnmpTargetParamsTable var21 = var3.getParamsTable();
         Element var22 = var2.getChild(c("7Ts'\u0006%Hy2&\u0014[l6?7n\u007f5>!"));
         if (var22 != null) {
            List var23 = var22.getChildren(c("7Ts'\u0006%Hy2&\u0012\u000bN6 %Wm"));
            Iterator var24 = var23.iterator();

            do {
               if (!var24.hasNext()) {
                  List var26 = var22.getChildren(c("7Ts'\u0006%Hy2&\u0012\bN6 %Wm"));
                  Iterator var27 = var26.iterator();

                  while(var27.hasNext()) {
                     Element var28 = (Element)var27.next();
                     var13 = var28.getAttributeValue(c("*[s2"));
                     var14 = var28.getAttributeValue(c("'Us:'*Sj."));
                     var21.addV2(var13, var14);
                     f.debug(c("\u0005^z26dl,w\u0001*Wn\u000336]{#\u0002%H\u007f:!~\u001a>\u007f<%W{j") + var13 + c("h\u001a}8?)Op>&=\u0007") + var14 + ")");
                     if (var19) {
                        return;
                     }

                     if (var19) {
                        break;
                     }
                  }

                  List var29 = var22.getChildren(c("7Ts'\u0006%Hy2&\u0012\tN6 %Wm"));
                  Iterator var30 = var29.iterator();

                  do {
                     if (!var30.hasNext()) {
                        return;
                     }

                     String var15;
                     String var16;
                     String var17;
                     byte var18;
                     label97: {
                        Element var31 = (Element)var30.next();
                        var15 = var31.getAttributeValue(c("*[s2"));
                        var16 = var31.getAttributeValue(c("7_}\u00193)_"));
                        var17 = var31.getAttributeValue(c("7_}\u001b72_r"));
                        var18 = 0;
                        if (c("%Oj?\u00026Sh").equals(var17)) {
                           var18 = 3;
                           if (!var19) {
                              break label97;
                           }
                        }

                        if (c("%Oj?\u001c+jl>$").equals(var17)) {
                           var18 = 1;
                           if (!var19) {
                              break label97;
                           }
                        }

                        if (c("*U_\"&,tq\u0007 -L").equals(var17)) {
                           var18 = 0;
                        }
                     }

                     var21.addV3(var15, var16, var18);
                     f.debug(c("\u0005^z26dl-w\u0001*Wn\u000336]{#\u0002%H\u007f:!~\u001a>\u007f<%W{j") + var15 + c("h\u001am21\n[s2o") + var16 + c("h\u001am21\b_h2>y") + var17 + ")");
                  } while(!var19);

                  return;
               }

               Element var25 = (Element)var24.next();
               var11 = var25.getAttributeValue(c("*[s2"));
               var12 = var25.getAttributeValue(c("'Us:'*Sj."));
               var21.addV1(var11, var12);
               f.debug(c("\u0005^z26dl/w\u0001*Wn\u000336]{#\u0002%H\u007f:!~\u001a>\u007f<%W{j") + var11 + c("h\u001a}8?)Op>&=\u0007") + var12 + ")");
            } while(!var19 || !var19);
         }

      }
   }

   private void e(SnmpAgent var1) throws SnmpException, UnknownHostException {
      if (f.isDebugEnabled()) {
         f.debug(c("\bU\u007f3;*]>\u0004<)JP8&-\\w2 "));
      }

      Element var2 = this.b.getChild(c("7Ts'\u001c+Nw1;!H"));
      if (var2 != null) {
         SnmpNotifier var3 = var1.getNotifier();
         SnmpNotifyTable var4 = var3.getNotifyTable();
         List var5 = var2.getChildren(c("7Ts'\u001c+Nw1+\u0010[l070"));
         Iterator var6 = var5.iterator();

         while(var6.hasNext()) {
            Element var7 = (Element)var6.next();
            String var8 = var7.getAttributeValue(c("*Uj>4=}l8'4"));
            String var9 = var7.getAttributeValue(c("0[y"));
            var4.add(var8, var9);
            f.debug(c("\u0005^z26dip:\"\nUj>4=n\u007f%5!N$wrl]l8'4\u0007") + var8 + c("h\u001aj65y") + var9 + ")");
            if (g) {
               break;
            }
         }

      }
   }

   private void b(String var1) {
      if (this.c) {
         System.out.println(var1);
      }

   }

   public InputSource resolveEntity(String var1, String var2) throws SAXException, IOException {
      if (f.isDetailedEnabled()) {
         f.detailed(c("6_m8>2_l\u0012<0Sj.hd") + var2);
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
               if (!g) {
                  break label38;
               }
            }

            if ((var10 = var4.lastIndexOf(92)) >= 0) {
               var5 = var4.substring(var10 + 1);
            }
         }

         InputStream var7 = SnmpFramework.getFileLocator().getInputStream(var5);
         if (var7 != null) {
            f.debug(c("!Tj>&=\u001al2!+Vh26dSpw!![l4:dJ\u007f#:~\u001a") + var5);
            return new InputSource(var7);
         }
      } catch (Exception var9) {
         f.debug(c("!Tj>&=\u001ax>>!\u001ap8&dSpw\u0001*Wn\u00165!Tjw!![l4:dJ\u007f#:~\u001a") + var4, var9);
      }

      var5 = var3.getHost();
      if (var5 != null && this.a.containsKey(var5.toLowerCase())) {
         String var11 = var3.getFile();
         URL var12 = this.getClass().getResource(var11);
         if (var12 == null) {
            f.error(c("&[zw !Iq\" '_>\u0002\u0000\b\u001a69=d\\w;7m\u0000>") + var3);
            return null;
         } else {
            InputStream var8 = var12.openStream();
            return new InputSource(var8);
         }
      } else {
         if (f.isDetailedEnabled()) {
            f.detailed(c("i\u0017>w<+\u001ak%>dW\u007f#1,\u0000>") + var5);
         }

         return null;
      }
   }

   private static String c(String var0) {
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
               var10003 = 58;
               break;
            case 2:
               var10003 = 30;
               break;
            case 3:
               var10003 = 87;
               break;
            default:
               var10003 = 82;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   public interface NetConfigListener {
      void processIPAddress(String var1, String var2, InetAddress var3, int var4, int var5);
   }
}
