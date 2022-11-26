package monfox.toolkit.snmp.metadata.output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
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
import monfox.toolkit.snmp.util.XmlException;
import monfox.toolkit.snmp.util.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlMibOutputter {
   private boolean a = false;
   private static Properties b = new Properties();
   private static Properties c;
   private static String d = c("\u0003BH8O\u0003BH8O\u0003BH8O\u0003BH8O\u0003BH8O\u0003BH8O\u0003BH8O\u0003BH8O\u0003BH8O\u0003BH");
   private DocumentBuilder e;
   private static SnmpOid f = new SnmpOid(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 1L, 1L, 5L});
   private Logger g = Logger.getInstance(c("g1&U?"), c("n'<Y+b6)"), c("{\u000f\u0004U\u0006A-\u001dl\u001fV\u0016\u001c}\u001d"));
   // $FF: synthetic field
   static Class h;

   public XmlMibOutputter() throws XmlException {
      try {
         DocumentBuilderFactory var1 = DocumentBuilderFactory.newInstance();
         this.e = var1.newDocumentBuilder();
      } catch (Exception var2) {
         throw new XmlException(c("`-&^&d7:Y;j-&8*q0'JU\u0003\u0001\tv\u0001L\u0016H{\u001dF\u0003\u001c}Og\r\u000bm\u0002F\f\u001c8-V\u000b\u0004|\nQ"));
      }
   }

   private Document a() {
      return this.e.newDocument();
   }

   public void outputModuleFiles(SnmpMetadata var1, String var2, String var3, boolean var4) throws SnmpValueException, IOException, XmlException {
      boolean var10 = MibOutputter.i;
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
            throw new IOException(c("`\u0003\u0006v\u0000WB'n\nQ\u0015\u001aq\u001bFB\u000eq\u0003FXH") + var8);
         }

         this.outputModule(var1, var7, var9);
         ++var6;
         if (var10) {
            break;
         }
      }

   }

   public void outputModule(SnmpMetadata var1, String var2, String var3, String var4, boolean var5) throws SnmpValueException, IOException, XmlException {
      SnmpModule var6;
      String var7;
      label23: {
         var6 = var1.getModule(var2);
         var7 = "";
         if (var3 != null && var3.length() > 0) {
            var7 = var3;
            if (!var3.endsWith(File.separator)) {
               var7 = var3 + File.separator;
            }

            var7 = var7 + var6.getName() + "." + var4;
            if (!MibOutputter.i) {
               break label23;
            }
         }

         var7 = var6.getName() + "." + var4;
      }

      File var8 = new File(var7);
      if (var8.exists() && !var5) {
         throw new IOException(c("`\u0003\u0006v\u0000WB'n\nQ\u0015\u001aq\u001bFB\u000eq\u0003FXH") + var7);
      } else {
         this.outputModule(var1, var6, var8);
      }
   }

   public void outputModule(SnmpMetadata var1, SnmpModule var2, File var3) throws IOException, XmlException, SnmpValueException {
      Document var4 = this.toXml(var1, var2);
      XmlUtil.printXML(var4, new FileOutputStream(var3), c("K\u0016\u001chU\fM\u001fo\u0018\r\u000f\u0007v\tL\u001aF{\u0000NM\u0005w\u0001E\r\u00107\u001bL\r\u0004s\u0006WM\u001bv\u0002SM\fl\u000b\f\u0011\u0006u\u001fn\u0007\u001cy\u000bB\u0016\t6\u000bW\u0006"), false);
   }

   public Document toXml(SnmpMetadata var1) throws XmlException, SnmpValueException {
      Document var2 = this.a();
      Element var3 = this.a(var2, var1);
      var2.appendChild(var3);
      return var2;
   }

   private Element a(Document var1, SnmpMetadata var2) throws XmlException, SnmpValueException {
      boolean var7 = MibOutputter.i;
      Element var3 = var1.createElement(c("p\f\u0005h\"F\u0016\t|\u000eW\u0003"));
      SnmpModule[] var4 = var2.getModules();
      int var5 = 0;

      Element var10000;
      while(true) {
         if (var5 < var4.length) {
            Element var6 = this.a(var1, var2, var4[var5]);
            var10000 = var3;
            if (var7) {
               break;
            }

            var3.appendChild(var6);
            ++var5;
            if (!var7) {
               continue;
            }
         }

         var10000 = var3;
         break;
      }

      return var10000;
   }

   public Document toXml(SnmpMetadata var1, SnmpModule var2) throws XmlException, SnmpValueException {
      Document var3 = this.a();
      Element var4 = this.a(var3, var1, var2);
      var3.appendChild(var4);
      return var3;
   }

   private Element a(Document var1, SnmpMetadata var2, SnmpModule var3) throws XmlException, SnmpValueException {
      boolean var12 = MibOutputter.i;
      Element var4 = var1.createElement(c("n\r\fm\u0003F"));
      var4.setAttribute(c("M\u0003\u0005}"), var3.getName());
      Element var5 = var1.createElement(c("q\u0007\u0019m\u0006Q\u0007\u001b"));
      var4.appendChild(var5);
      String[] var6 = var3.getRequiredModules();
      int var7 = 0;

      while(true) {
         Element var8;
         if (var7 < var6.length) {
            var8 = var1.createElement(c("n\r\fm\u0003F0\r~"));
            var8.setAttribute(c("M\u0003\u0005}"), var6[var7]);
            var5.appendChild(var8);
            ++var7;
            if (var12) {
               break;
            }

            if (!var12) {
               continue;
            }
         }

         if (var3.getIdentity() != null) {
            SnmpModuleIdentityInfo var13 = var3.getIdentity();
            var8 = var1.createElement(c("n\r\fm\u0003F+\f}\u0001W\u000b\u001ca"));
            var4.appendChild(var8);
            var8.setAttribute(c("M\u0003\u0005}"), var13.getName());
            var8.setAttribute(c("L\u000b\f"), this.a(var2, (SnmpOidInfo)var13, false));
            var8.setAttribute(c("O\u0003\u001bl:S\u0006\tl\nG"), var13.getLastUpdated());
            Element var9 = var1.createElement(c("l\u0010\u000fy\u0001J\u0018\tl\u0006L\f"));
            XmlUtil.addText(var9, var13.getOrganization());
            var8.appendChild(var9);
            Element var10 = var1.createElement(c("`\r\u0006l\u000e@\u0016!v\tL"));
            XmlUtil.addText(var10, var13.getContactInfo());
            var8.appendChild(var10);
            Element var11 = var1.createElement(c("g\u0007\u001b{\u001dJ\u0012\u001cq\u0000M"));
            XmlUtil.addText(var11, var13.getDescription());
            var8.appendChild(var11);
         }

         this.a(var2, var3, var1, var4);
         this.b(var2, var3, var1, var4);
         break;
      }

      return var4;
   }

   private void a(SnmpMetadata var1, SnmpModule var2, Document var3, Element var4) throws SnmpValueException {
      SnmpTypeInfo[] var5 = var2.getTypes();
      int var6 = 0;

      while(var6 < var5.length) {
         Element var7 = this.a(var3, var1, var5[var6]);
         if (var7 != null) {
            var4.appendChild(var7);
         }

         ++var6;
         if (MibOutputter.i) {
            break;
         }
      }

   }

   private void a(SnmpMetadata var1, SnmpOidInfo var2, Document var3, Element var4) {
      if (this.g.isDebugEnabled()) {
         this.g.debug(c("S\u0010\u0007{\nP\u0011;v\u0002S-\u0001|&M\u0004\u0007\"O)") + var2);
      }

      Element var5 = var3.createElement(c("l\u000b\f"));
      var5.setAttribute(c("M\u0003\u0005}"), var2.getName());
      var5.setAttribute(c("L\u000b\f"), this.a(var1, var2, false));
      var5.setAttribute(c("Q\u0003\u001fN\u000eO\u0017\r"), this.a(var1, var2, true));
      var4.appendChild(var5);
   }

   private void b(SnmpMetadata var1, SnmpModule var2, Document var3, Element var4) throws SnmpValueException {
      boolean var7 = MibOutputter.i;
      SnmpOidInfo[] var5 = var2.getOidInfo();
      int var6 = 0;

      while(var6 < var5.length) {
         label54: {
            if (var5[var6] instanceof SnmpTableInfo) {
               this.a(var1, (SnmpTableInfo)var5[var6], var3, var4);
               if (!var7) {
                  break label54;
               }
            }

            if (!(var5[var6] instanceof SnmpTableEntryInfo)) {
               label62: {
                  if (var5[var6] instanceof SnmpObjectInfo) {
                     if (!((SnmpObjectInfo)var5[var6]).isScalar()) {
                        break label62;
                     }

                     this.a(var1, (SnmpObjectInfo)var5[var6], var3, var4);
                     if (!var7) {
                        break label62;
                     }
                  }

                  if (var5[var6] instanceof SnmpNotificationInfo) {
                     this.a(var1, (SnmpNotificationInfo)var5[var6], var3, var4);
                     if (!var7) {
                        break label62;
                     }
                  }

                  if (var5[var6] instanceof SnmpNotificationGroupInfo) {
                     this.a(var1, (SnmpNotificationGroupInfo)var5[var6], var3, var4);
                     if (!var7) {
                        break label62;
                     }
                  }

                  if (var5[var6] instanceof SnmpObjectGroupInfo) {
                     this.a(var1, (SnmpObjectGroupInfo)var5[var6], var3, var4);
                     if (!var7) {
                        break label62;
                     }
                  }

                  if (var5[var6].getClass() == (h == null ? (h = b(c("N\r\u0006~\u0000[L\u001cw\u0000O\t\u0001lAP\f\u0005hAN\u0007\u001cy\u000bB\u0016\t6<M\u000f\u0018W\u0006G+\u0006~\u0000"))) : h)) {
                     this.a(var1, var5[var6], var3, var4);
                  }
               }
            }
         }

         ++var6;
         if (var7) {
            break;
         }
      }

   }

   private void a(SnmpMetadata var1, SnmpObjectGroupInfo var2, Document var3, Element var4) {
      Element var7;
      boolean var12;
      Element var10000;
      label29: {
         var12 = MibOutputter.i;
         String var5 = var2.getName();
         (new StringBuffer()).append(var5.substring(0, 1).toUpperCase()).append(var5.substring(1)).toString();
         var7 = var3.createElement(c("l\u0000\u0002}\fW%\u001aw\u001aS"));
         var4.appendChild(var7);
         var7.setAttribute(c("M\u0003\u0005}"), var2.getName());
         var7.setAttribute(c("L\u000b\f"), this.a(var1, (SnmpOidInfo)var2, false));
         this.a(var7, var2.getStatus());
         SnmpObjectInfo[] var8 = var2.getObjects();
         Element var9 = var3.createElement(c("l\u0000\u0002}\fW\u0011"));
         var7.appendChild(var9);
         if (var8 != null) {
            int var10 = 0;

            while(var10 < var8.length) {
               Element var11 = var3.createElement(c("l\u0000\u0002}\fW0\r~"));
               var9.appendChild(var11);
               var11.setAttribute(c("M\u0003\u0005}"), var8[var10].getName());
               var11.setAttribute(c("L\u000b\f"), this.a(var1, (SnmpOidInfo)var8[var10], false));
               var11.setAttribute(c("P\u000f\u0001l\u0016S\u0007"), var8[var10].getSmiTypeShortString());
               var10000 = var11;
               if (var12) {
                  break label29;
               }

               var11.setAttribute(c("L\u0000\u0002l\u0016S\u0007"), SnmpValue.typeToShortString(var8[var10].getType()));
               ++var10;
               if (var12) {
                  break;
               }
            }
         }

         var10000 = var3.createElement(c("g\u0007\u001b{\u001dJ\u0012\u001cq\u0000M"));
      }

      Element var13 = var10000;
      var7.appendChild(var13);
      if (var2.getDescription() == null) {
         XmlUtil.addText(var13, "");
         if (!var12) {
            return;
         }
      }

      XmlUtil.addText(var13, var2.getDescription());
   }

   private void a(SnmpMetadata var1, SnmpNotificationGroupInfo var2, Document var3, Element var4) {
      Element var7;
      boolean var12;
      Element var10000;
      label29: {
         var12 = MibOutputter.i;
         String var5 = var2.getName();
         (new StringBuffer()).append(var5.substring(0, 1).toUpperCase()).append(var5.substring(1)).toString();
         var7 = var3.createElement(c("m\r\u001cq\tJ\u0001\tl\u0006L\f/j\u0000V\u0012"));
         var4.appendChild(var7);
         var7.setAttribute(c("M\u0003\u0005}"), var2.getName());
         var7.setAttribute(c("L\u000b\f"), this.a(var1, (SnmpOidInfo)var2, false));
         this.a(var7, var2.getStatus());
         SnmpNotificationInfo[] var8 = var2.getNotifications();
         Element var9 = var3.createElement(c("m\r\u001cq\tJ\u0001\tl\u0006L\f\u001b"));
         var7.appendChild(var9);
         if (var8 != null) {
            int var10 = 0;

            while(var10 < var8.length) {
               Element var11 = var3.createElement(c("m\r\u001cq\tJ\u0001\tl\u0006L\f:}\t"));
               var9.appendChild(var11);
               var11.setAttribute(c("M\u0003\u0005}"), var8[var10].getName());
               var10000 = var11;
               if (var12) {
                  break label29;
               }

               var11.setAttribute(c("L\u000b\f"), this.a(var1, (SnmpOidInfo)var8[var10], false));
               ++var10;
               if (var12) {
                  break;
               }
            }
         }

         var10000 = var3.createElement(c("g\u0007\u001b{\u001dJ\u0012\u001cq\u0000M"));
      }

      Element var13 = var10000;
      var7.appendChild(var13);
      if (var2.getDescription() == null) {
         XmlUtil.addText(var13, "");
         if (!var12) {
            return;
         }
      }

      XmlUtil.addText(var13, var2.getDescription());
   }

   private void a(SnmpMetadata var1, SnmpNotificationInfo var2, Document var3, Element var4) {
      Element var7;
      boolean var12;
      Element var10000;
      label29: {
         var12 = MibOutputter.i;
         String var5 = var2.getName();
         (new StringBuffer()).append(var5.substring(0, 1).toUpperCase()).append(var5.substring(1)).toString();
         var7 = var3.createElement(c("m\r\u001cq\tJ\u0001\tl\u0006L\f"));
         var4.appendChild(var7);
         var7.setAttribute(c("M\u0003\u0005}"), var2.getName());
         var7.setAttribute(c("L\u000b\f"), this.a(var1, (SnmpOidInfo)var2, false));
         this.a(var7, var2.getStatus());
         SnmpObjectInfo[] var8 = var2.getObjects();
         Element var9 = var3.createElement(c("l\u0000\u0002}\fW\u0011"));
         var7.appendChild(var9);
         if (var8 != null) {
            int var10 = 0;

            while(var10 < var8.length) {
               Element var11 = var3.createElement(c("l\u0000\u0002}\fW0\r~"));
               var9.appendChild(var11);
               var11.setAttribute(c("M\u0003\u0005}"), var8[var10].getName());
               var11.setAttribute(c("L\u000b\f"), this.a(var1, (SnmpOidInfo)var8[var10], false));
               var11.setAttribute(c("P\u000f\u0001l\u0016S\u0007"), var8[var10].getSmiTypeShortString());
               var10000 = var11;
               if (var12) {
                  break label29;
               }

               var11.setAttribute(c("L\u0000\u0002l\u0016S\u0007"), SnmpValue.typeToShortString(var8[var10].getType()));
               ++var10;
               if (var12) {
                  break;
               }
            }
         }

         var10000 = var3.createElement(c("g\u0007\u001b{\u001dJ\u0012\u001cq\u0000M"));
      }

      Element var13 = var10000;
      var7.appendChild(var13);
      if (var2.getDescription() == null) {
         XmlUtil.addText(var13, "");
         if (!var12) {
            return;
         }
      }

      XmlUtil.addText(var13, var2.getDescription());
   }

   private void a(SnmpMetadata var1, SnmpObjectInfo var2, Document var3, Element var4) {
      boolean var9 = MibOutputter.i;
      if (this.g.isDebugEnabled()) {
         this.g.debug(c("S\u0010\u0007{\nP\u0011;v\u0002S-\nr\n@\u0016!v\tLXH\u0012") + var2);
      }

      if (var2.getType() != 255) {
         String var5 = var2.getName();
         (new StringBuffer()).append(var5.substring(0, 1).toUpperCase()).append(var5.substring(1)).toString();
         Element var7 = var3.createElement(c("l\u0000\u0002}\fW"));
         var4.appendChild(var7);
         var7.setAttribute(c("M\u0003\u0005}"), var2.getName());
         var7.setAttribute(c("L\u000b\f"), this.a(var1, (SnmpOidInfo)var2, false));
         var7.setAttribute(c("L\u0000\u0002l\u0016S\u0007"), SnmpValue.typeToShortString(var2.getType()));
         var7.setAttribute(c("P\u000f\u0001l\u0016S\u0007"), var2.getSmiTypeShortString());
         var7.setAttribute(c("B\u0001\u000b}\u001cP"), SnmpObjectInfo.accessToShortString(var2.getAccess()));
         if (var2.getTypeInfo() != null) {
            String var8 = this.c(var2.getTypeInfo());
            if (var8 == null) {
               var8 = this.b(var2.getTypeInfo());
            }

            if (var8 != null) {
               var7.setAttribute(c("W\u001b\u0018}\u001dF\u0004"), var8);
            }

            if (var2.getDefVal() != null) {
               var7.setAttribute(c("G\u0007\u000en\u000eO"), var2.getDefVal());
            }
         }

         label57: {
            if (var2.isScalar()) {
               var7.setAttribute(c("E\r\u001au"), c("P\u0001\tt\u000eQ"));
               if (!var9) {
                  break label57;
               }
            }

            if (var2.isColumnar()) {
               var7.setAttribute(c("E\r\u001au"), c("@\r\u0004m\u0002M"));
               if (!var9) {
                  break label57;
               }
            }

            var7.setAttribute(c("E\r\u001au"), c("F\f\u001cj\u0016"));
         }

         this.a(var7, var2.getStatus());
         if (var2.getTypeInfo() != null && var2.getTypeInfo().getModule() == null) {
            this.a(var7, var2.getTypeInfo());
         }

         Element var10 = var3.createElement(c("g\u0007\u001b{\u001dJ\u0012\u001cq\u0000M"));
         var7.appendChild(var10);
         if (var2.getDescription() == null) {
            XmlUtil.addText(var10, "");
            if (!var9) {
               return;
            }
         }

         XmlUtil.addText(var10, var2.getDescription());
      }
   }

   private void a(SnmpMetadata var1, SnmpTableInfo var2, Document var3, Element var4) throws SnmpValueException {
      Element var5;
      SnmpTableEntryInfo var6;
      Element var7;
      SnmpObjectInfo[] var8;
      Element var9;
      Element var11;
      boolean var14;
      byte var10000;
      Element var18;
      label96: {
         label95: {
            var14 = MibOutputter.i;
            var5 = var3.createElement(c("w\u0003\nt\n"));
            var4.appendChild(var5);
            var5.setAttribute(c("M\u0003\u0005}"), var2.getName());
            var5.setAttribute(c("L\u000b\f"), this.a(var1, (SnmpOidInfo)var2, false));
            this.a(var5, var2.getStatus());
            var6 = var2.getEntry();
            var7 = var3.createElement(c("f\f\u001cj\u0016"));
            var5.appendChild(var7);
            var7.setAttribute(c("M\u0003\u0005}"), var6.getName());
            var7.setAttribute(c("L\u000b\f"), this.a(var1, (SnmpOidInfo)var6, false));
            this.a(var7, var6.getStatus());
            if (var6.getIndexes() != null) {
               var8 = var6.getIndexes();
               var9 = var3.createElement(c("j\f\f}\u0017F\u0011"));
               var7.appendChild(var9);
               int var10 = 0;

               while(var10 < var8.length) {
                  var11 = var3.createElement(c("l\u0000\u0002}\fW0\r~"));
                  var9.appendChild(var11);
                  var11.setAttribute(c("M\u0003\u0005}"), var8[var10].getName());
                  var10000 = var6.isImplied();
                  if (var14) {
                     break label96;
                  }

                  if (var10000 != 0 && var10 == var8.length - 1) {
                     var11.setAttribute(c("N\r\fq\tJ\u0007\u001a"), c("J\u000f\u0018t\u0006F\u0006"));
                  }

                  var11.setAttribute(c("L\u000b\f"), this.a(var1, (SnmpOidInfo)var8[var10], false));
                  var11.setAttribute(c("P\u000f\u0001l\u0016S\u0007"), var8[var10].getSmiTypeShortString());
                  var11.setAttribute(c("L\u0000\u0002l\u0016S\u0007"), SnmpValue.typeToShortString(var8[var10].getType()));
                  ++var10;
                  if (var14) {
                     break;
                  }
               }

               if (!var14) {
                  break label95;
               }
            }

            if (var6.getAugments() != null) {
               SnmpTableEntryInfo var16 = var6.getAugments();
               var9 = var3.createElement(c("b\u0017\u000fu\nM\u0016\u001b"));
               var7.appendChild(var9);
               var18 = var3.createElement(c("l\u0000\u0002}\fW0\r~"));
               var9.appendChild(var18);
               var18.setAttribute(c("M\u0003\u0005}"), var16.getName());
               var18.setAttribute(c("L\u000b\f"), this.a(var1, (SnmpOidInfo)var16, false));
            }
         }

         var8 = var6.getColumns();
         var10000 = 0;
      }

      int var17 = var10000;

      Element var20;
      while(true) {
         if (var17 < var8.length) {
            var18 = var3.createElement(c("`\r\u0004m\u0002M"));
            var7.appendChild(var18);
            var20 = var18;
            if (var14) {
               break;
            }

            var18.setAttribute(c("M\u0003\u0005}"), var8[var17].getName());
            if (var6.isImplied() && var17 == var8.length - 1) {
               var18.setAttribute(c("N\r\fq\tJ\u0007\u001a"), c("J\u000f\u0018t\u0006F\u0006"));
            }

            var18.setAttribute(c("@\r\u0004m\u0002M"), "" + var8[var17].getOid().getLast());
            var18.setAttribute(c("L\u0000\u0002l\u0016S\u0007"), SnmpValue.typeToShortString(var8[var17].getType()));
            ++var17;
            if (!var14) {
               continue;
            }
         }

         var20 = var3.createElement(c("g\u0007\u001b{\u001dJ\u0012\u001cq\u0000M"));
         break;
      }

      label69: {
         var9 = var20;
         var7.appendChild(var9);
         if (var6.getDescription() == null) {
            XmlUtil.addText(var9, "");
            if (!var14) {
               break label69;
            }
         }

         XmlUtil.addText(var9, var6.getDescription());
      }

      label64: {
         var18 = var3.createElement(c("g\u0007\u001b{\u001dJ\u0012\u001cq\u0000M"));
         var5.appendChild(var18);
         if (var2.getDescription() == null) {
            XmlUtil.addText(var18, "");
            if (!var14) {
               break label64;
            }
         }

         XmlUtil.addText(var18, var2.getDescription());
      }

      String var12;
      label59: {
         var11 = var3.createElement(c("l\u0000\u0002}\fW"));
         var4.appendChild(var11);
         var12 = this.a(var6.getName());
         var11.setAttribute(c("M\u0003\u0005}"), var6.getName());
         var11.setAttribute(c("L\u000b\f"), this.a(var1, (SnmpOidInfo)var6, false));
         var11.setAttribute(c("L\u0000\u0002l\u0016S\u0007"), c("F\f\u001cj\u0016"));
         var11.setAttribute(c("P\u000f\u0001l\u0016S\u0007"), c("F\f\u001cj\u0016"));
         var11.setAttribute(c("B\u0001\u000b}\u001cP"), c("M\u0003"));
         var11.setAttribute(c("W\u001b\u0018}\u001dF\u0004"), var2.getModule().getName() + ":" + var12);
         var11.setAttribute(c("E\r\u001au"), c("F\f\u001cj\u0016"));
         this.a(var11, var6.getStatus());
         var9 = var3.createElement(c("g\u0007\u001b{\u001dJ\u0012\u001cq\u0000M"));
         var11.appendChild(var9);
         if (var6.getDescription() == null) {
            XmlUtil.addText(var9, "");
            if (!var14) {
               break label59;
            }
         }

         XmlUtil.addText(var9, var6.getDescription());
      }

      try {
         Element var13 = var3.createElement(c("w\u001b\u0018}"));
         var13.setAttribute(c("M\u0003\u0005}"), var12);
         var13.setAttribute(c("W\u001b\u0018}"), c("p'9M*m!-"));
         var13.setAttribute(c("L\u0000\u0002l\u0016S\u0007"), c("F\f\u001cj\u0016"));
         var4.appendChild(var13);
      } catch (Exception var15) {
      }

      int var19 = 0;

      while(var19 < var8.length) {
         this.a(var1, var8[var19], var3, var4);
         ++var19;
         if (var14) {
            break;
         }
      }

   }

   String a(String var1, int var2) {
      int var3 = var1.length();
      return var3 > var2 ? var1 : var1 + d.substring(0, var2 - var3);
   }

   String a(int var1) {
      switch (var1) {
         case 0:
            return c("M\r\u001c5\u000e@\u0001\rk\u001cJ\u0000\u0004}");
         case 1:
            return c("Q\u0007\t|BL\f\u0004a");
         case 2:
            return c("T\u0010\u0001l\n\u000e\r\u0006t\u0016");
         case 3:
            return c("Q\u0007\t|BT\u0010\u0001l\n");
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
            return c("Q\u0007\t|B@\u0010\ry\u001bF");
         case 8:
            return c("B\u0001\u000b}\u001cP\u000b\nt\n\u000e\u0004\u0007jBM\r\u001cq\tZ");
         case 16:
            return c("M\r\u001c5\u0006N\u0012\u0004}\u0002F\f\u001c}\u000b");
      }
   }

   String a(SnmpMetadata var1, SnmpOidInfo var2, boolean var3) {
      return this.a(var1, var2.getOid(), var3);
   }

   String a(SnmpMetadata var1, SnmpOid var2, boolean var3) {
      boolean var9 = MibOutputter.i;
      if (var3) {
         try {
            SnmpOid var4 = var2.getParent();
            SnmpOidInfo var5 = var1.resolveBaseOid(var4);
            if (var5 != null && var5.getName() != null) {
               SnmpOid var6 = var5.getOid();
               String var7 = var5.getName();
               int var8 = var6.getLength();

               String var14;
               while(true) {
                  if (var8 < var2.getLength()) {
                     var14 = var7 + "." + var2.get(var8);
                     if (var9) {
                        break;
                     }

                     var7 = var14;
                     ++var8;
                     if (!var9) {
                        continue;
                     }
                  }

                  var14 = var7;
                  break;
               }

               return var14;
            }
         } catch (SnmpValueException var11) {
         }
      }

      StringBuffer var12 = new StringBuffer();
      int var13 = 0;

      StringBuffer var10000;
      while(true) {
         if (var13 < var2.getLength()) {
            try {
               var10000 = var12.append(var2.get(var13));
               if (var9) {
                  break;
               }
            } catch (SnmpValueException var10) {
            }

            if (var13 < var2.getLength() - 1) {
               var12.append('.');
            }

            ++var13;
            if (!var9) {
               continue;
            }
         }

         var10000 = var12;
         break;
      }

      return var10000.toString();
   }

   String a(String var1) {
      return var1.substring(0, 1).toUpperCase() + var1.substring(1);
   }

   private String a(SnmpTypeInfo var1) {
      return var1 != null ? this.c(var1.getTypeRef()) : null;
   }

   private String b(SnmpTypeInfo var1) {
      return var1 != null ? this.c(var1.getBaseType()) : null;
   }

   private String c(SnmpTypeInfo var1) {
      if (var1 != null && var1.getName() != null && var1.getModule() != null) {
         try {
            String var2 = null;
            if (var1.getModule() != null) {
               var2 = var1.getModule().getName();
            }

            String var3 = var1.getName();
            if (var2 != null && var3 != null) {
               return var2 + ":" + var3;
            }

            if (var3 != null) {
               return var3;
            }
         } catch (Exception var4) {
         }
      }

      return null;
   }

   Element a(Document var1, SnmpMetadata var2, SnmpTypeInfo var3) throws SnmpValueException {
      if (var3.getType() == 255) {
         return null;
      } else {
         Element var4 = var1.createElement(c("w\u001b\u0018}"));
         var4.setAttribute(c("M\u0003\u0005}"), var3.getName());
         var4.setAttribute(c("W\u001b\u0018}"), var3.getDefinedType());
         var4.setAttribute(c("L\u0000\u0002l\u0016S\u0007"), SnmpValue.typeToShortString(var3.getType()));
         String var5 = this.a(var3);
         if (var5 != null) {
            var4.setAttribute(c("W\u001b\u0018}\u001dF\u0004"), var5);
         }

         if (var3.getDisplayHint() != null) {
            var4.setAttribute(c("G\u000b\u001bh\u0003B\u001b q\u0001W"), var3.getDisplayHint());
         }

         this.a(var4, var3.getStatus());
         this.a(var4, var3);
         if (var3.getDescription() != null) {
            Element var6 = var1.createElement(c("g\u0007\u001b{\u001dJ\u0012\u001cq\u0000M"));
            var4.appendChild(var6);
            XmlUtil.addText(var6, var3.getDescription());
         }

         return var4;
      }
   }

   private void a(Element var1, SnmpTypeInfo var2) {
      boolean var9 = MibOutputter.i;
      Document var3 = var1.getOwnerDocument();
      if (var2.getNameToNumberMap() != null) {
         this.a(var2.getNameToNumberMap(), var1);
      }

      if (var2.hasRangeSpec()) {
         Element var4 = var1;
         Element var5;
         if (var2.getType() == 4) {
            var5 = var3.createElement(c("p\u000b\u0012}"));
            var1.appendChild(var5);
            var4 = var5;
         }

         var5 = var3.createElement(c("q\u0003\u0006\u007f\np\u0012\r{"));
         var4.appendChild(var5);
         RangeItem[] var6 = var2.getRangeSpec();
         int var7 = 0;

         while(var7 < var6.length) {
            label25: {
               Element var8 = var3.createElement(c("q\u0003\u0006\u007f\nj\u0016\ru"));
               var5.appendChild(var8);
               if (var6[var7].isSingle()) {
                  var8.setAttribute(c("P\u000b\u0006\u007f\u0003F"), "" + var6[var7].getSingleValue());
                  if (!var9) {
                     break label25;
                  }
               }

               var8.setAttribute(c("O\r\u001f}\u001d"), "" + var6[var7].getLowerValue());
               var8.setAttribute(c("V\u0012\u0018}\u001d"), "" + var6[var7].getUpperValue());
            }

            ++var7;
            if (var9) {
               break;
            }
         }
      }

   }

   private void a(Hashtable var1, Element var2) {
      boolean var13 = MibOutputter.i;
      Document var3 = var2.getOwnerDocument();
      Enumeration var4 = var1.keys();
      Vector var5 = new Vector();
      Vector var6 = new Vector();

      while(var4.hasMoreElements()) {
         String var7 = (String)var4.nextElement();
         Long var8 = (Long)var1.get(var7);
         int var9 = 0;
         int var10 = var5.size() - 1;

         int var10000;
         while(true) {
            if (var10 >= 0) {
               label55: {
                  String var11 = (String)var5.elementAt(var10);
                  Long var12 = (Long)var6.elementAt(var10);
                  long var19;
                  var10000 = (var19 = var12 - var8) == 0L ? 0 : (var19 < 0L ? -1 : 1);
                  if (var13) {
                     break;
                  }

                  if (var10000 < 0) {
                     var9 = var10 + 1;
                     if (!var13) {
                        break label55;
                     }
                  }

                  --var10;
                  if (!var13) {
                     continue;
                  }
               }
            }

            var10000 = var9;
            break;
         }

         label35: {
            if (var10000 >= var5.size()) {
               var5.addElement(var7);
               var6.addElement(var8);
               if (!var13) {
                  break label35;
               }
            }

            var5.insertElementAt(var7, var9);
            var6.insertElementAt(var8, var9);
         }

         if (var13) {
            break;
         }
      }

      Element var14 = var3.createElement(c("m\u0003\u0005}\u000bm\u0017\u0005z\nQ.\u0001k\u001b"));
      var2.appendChild(var14);
      int var15 = 0;

      while(var15 < var5.size()) {
         String var16 = (String)var5.elementAt(var15);
         Long var17 = (Long)var6.elementAt(var15);
         Element var18 = var3.createElement(c("M\f"));
         var14.appendChild(var18);
         var18.setAttribute(c("M\u0003\u0005}"), var16);
         var18.setAttribute(c("U\u0003\u0004m\n"), "" + var17);
         ++var15;
         if (var13) {
            break;
         }
      }

   }

   private void a(Element var1, String var2) {
      if (var2 == null) {
         var2 = c("@\u0017\u001aj\nM\u0016");
      }

      var2 = c.getProperty(var2, c("@\u0017\u001aj\nM\u0016"));
      var1.setAttribute(c("P\u0016\tl\u001aP"), var2);
   }

   public void setVerbose(boolean var1) {
      this.a = var1;
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
      b.put(c("N\u0003\u0006|\u000eW\r\u001aa"), c("N\u0003\u0006|\u000eW\r\u001aa"));
      b.put(c("G\u0007\u0018j\n@\u0003\u001c}\u000b"), c("G\u0007\u0018j\n@\u0003\u001c}\u000b"));
      b.put(c("L\u0000\u001bw\u0003F\u0016\r"), c("L\u0000\u001bw\u0003F\u0016\r"));
      b.put(c("L\u0012\u001cq\u0000M\u0003\u0004"), c("L\u0012\u001cq\u0000M\u0003\u0004"));
      b.put(c("@\u0017\u001aj\nM\u0016"), c("N\u0003\u0006|\u000eW\r\u001aa"));
      c = new Properties();
      c.put(c("N\u0003\u0006|\u000eW\r\u001aa"), c("@\u0017\u001aj\nM\u0016"));
      c.put(c("G\u0007\u0018j\n@\u0003\u001c}\u000b"), c("G\u0007\u0018j\n@\u0003\u001c}\u000b"));
      c.put(c("L\u0000\u001bw\u0003F\u0016\r"), c("L\u0000\u001bw\u0003F\u0016\r"));
      c.put(c("L\u0012\u001cq\u0000M\u0003\u0004"), c("@\u0017\u001aj\nM\u0016"));
      c.put(c("@\u0017\u001aj\nM\u0016"), c("@\u0017\u001aj\nM\u0016"));
   }

   private static String c(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 35;
               break;
            case 1:
               var10003 = 98;
               break;
            case 2:
               var10003 = 104;
               break;
            case 3:
               var10003 = 24;
               break;
            default:
               var10003 = 111;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
