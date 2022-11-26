package monfox.toolkit.snmp.metadata.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import monfox.jdom.Document;
import monfox.jdom.Element;
import monfox.jdom.JDOMException;
import monfox.jdom.input.SAXBuilder;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.metadata.MetadataError;
import monfox.toolkit.snmp.metadata.RangeItem;
import monfox.toolkit.snmp.metadata.Result;
import monfox.toolkit.snmp.metadata.SnmpAgentCapabilitiesInfo;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.SnmpModule;
import monfox.toolkit.snmp.metadata.SnmpModuleIdentityInfo;
import monfox.toolkit.snmp.metadata.SnmpNotificationGroupInfo;
import monfox.toolkit.snmp.metadata.SnmpNotificationInfo;
import monfox.toolkit.snmp.metadata.SnmpObjectGroupInfo;
import monfox.toolkit.snmp.metadata.SnmpObjectInfo;
import monfox.toolkit.snmp.metadata.SnmpObjectVariation;
import monfox.toolkit.snmp.metadata.SnmpOidInfo;
import monfox.toolkit.snmp.metadata.SnmpSupportedModule;
import monfox.toolkit.snmp.metadata.SnmpTableEntryInfo;
import monfox.toolkit.snmp.metadata.SnmpTableInfo;
import monfox.toolkit.snmp.metadata.SnmpTypeInfo;
import monfox.toolkit.snmp.util.FileUtil;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SnmpMetadataLoader {
   private Logger a;
   private boolean b;
   private String c;
   private String d;
   private Map e;
   private boolean f;
   private Hashtable g;
   private static final String h = "$Id: SnmpMetadataLoader.java,v 1.45 2009/07/16 18:25:14 sking Exp $";
   public static boolean i;

   public SnmpMetadataLoader() {
      boolean var1 = i;
      super();
      this.a = null;
      this.b = true;
      this.c = a(")NfI\u001b<O&=\u000e\u001d");
      this.d = a("\u007f\u0018%\b,?Ee\u001dl%Le\t(8W%\u0016-<S%\b*3P%\u0007\"\"F1J.>Ml\n;~We\n/:J~J0?NzJ.8AyJ&)W1");
      this.e = new Hashtable();
      this.f = true;
      this.g = new Hashtable();
      this.a = Logger.getInstance(a("\u0002Mg\u0015\u000e4Wk\u0001\"%BF\n\"5Fx"));
      if (var1) {
         SnmpException.b = !SnmpException.b;
      }

   }

   public Result load(String var1, SnmpMetadata var2) throws IOException, XMLException {
      return this.load(var1, var2, false);
   }

   public Result load(String var1, SnmpMetadata var2, boolean var3) throws IOException, XMLException {
      if (var1.indexOf(58) >= 0) {
         URL var5 = new URL(var1);
         return this.load(var5.openStream(), var2, var3);
      } else {
         FileInputStream var4 = new FileInputStream(var1);
         return this.load((InputStream)var4, var2, var3);
      }
   }

   public Result load(InputStream var1, SnmpMetadata var2) throws IOException, XMLException {
      return this.load(var1, var2, false);
   }

   public Result load(InputStream var1, SnmpMetadata var2, boolean var3) throws IOException, XMLException {
      try {
         SAXBuilder var4 = new SAXBuilder(true);
         var4.setEntityResolver(new Resolver());
         Document var5 = var4.build(var1);
         Element var6 = var5.getRootElement();
         return this.process(var6, var2, var3);
      } catch (JDOMException var7) {
         throw new XMLException(var7.toString());
      }
   }

   public Result loadModules(String[] var1, SnmpMetadata var2) {
      boolean var6 = i;
      if (this.a.isDebugEnabled()) {
         this.a.debug(a("=Lk\u0001\u000e>G\u007f\t&\"\u000b$Kmx"));
      }

      Vector var3 = new Vector();
      Result var4 = new Result();
      int var5 = 0;

      while(true) {
         if (var5 < var1.length) {
            this.a.debug(a("|\u000e*\t,0Gc\u000b$k") + var1[var5]);
            this.a((List)var3, (String)var1[var5], (String)null, var4);
            ++var5;
            if (var6) {
               break;
            }

            if (!var6) {
               continue;
            }
         }

         this.a(var3, var2, false, var4);
         break;
      }

      return var4;
   }

   public Result loadModule(String var1, SnmpMetadata var2) {
      if (this.a.isDebugEnabled()) {
         this.a.debug(a("=Lk\u0001\u000e>G\u007f\t&y") + var1 + a("}\r$Kj"));
      }

      Result var3 = new Result();
      Vector var4 = new Vector();
      this.a((List)var4, (String)var1, (String)null, var3);
      this.a(var4, var2, false, var3);
      return var3;
   }

   public Result load(String[] var1, SnmpMetadata var2) throws IOException, XMLException {
      boolean var11 = i;
      if (this.a.isDebugEnabled()) {
         this.a.debug(a("=Lk\u0001k\u007f\r$L"));
      }

      SAXBuilder var3 = new SAXBuilder(true);
      var3.setEntityResolver(new Resolver());
      Vector var4 = new Vector();
      int var5 = 0;

      while(var5 < var1.length) {
         String var6 = var1[var5];
         InputStream var7 = FileUtil.getInputStream(var6);

         try {
            label47: {
               Document var8 = var3.build(var7);
               Element var9 = var8.getRootElement();
               if (a("\u0002Mg\u0015\u000e4Wk\u0001\"%B").equals(var9.getName())) {
                  List var10 = var9.getChildren(a("\u001cLn\u0010/4"));
                  if (var10 != null) {
                     var4.addAll(var10);
                  }

                  if (!var11) {
                     break label47;
                  }
               }

               if (a("\u001cLn\u0010/4").equals(var9.getName())) {
                  var4.add(var9);
                  if (!var11) {
                     break label47;
                  }
               }

               throw new XMLException(a("\u0018M|\u0004/8G*=\u000e\u001d\u0003x\n,%\u0003o\t&<Fd\u0011c%Zz\u0000cv") + var9.getName() + a("\u007f\u0003Y\r,$OnE!4\u0003-6-<SG\u000070Gk\u0011\"v\u0003e\u0017cvne\u00016=F-K"));
            }
         } catch (JDOMException var12) {
            throw new XMLException(var12.getMessage());
         }

         ++var5;
         if (var11) {
            break;
         }
      }

      return this.a(var4, var2, false);
   }

   public Result process(Element var1, SnmpMetadata var2, boolean var3) throws XMLException {
      boolean var8 = i;
      Result var4 = new Result();
      Object var5 = null;
      if (var1.getName().equals(a("\u0002Mg\u0015\u000e4Wk\u0001\"%B"))) {
         var5 = var1.getChildren(a("\u001cLn\u0010/4"));
         List var6 = var1.getChildren(a("\u001cLn\u0010/4qo\u0003"));
         this.a((List)var5, (List)var6, (Result)var4);
         String var7 = var1.getAttributeValue(a("?Bg\u0000"));
         if (var7 != null) {
            var2.setName(var7);
         }
      } else {
         if (!var1.getName().equals(a("\u001cLn\u0010/4"))) {
            throw new XMLException(a("\u0018M|\u0004/8G*7,>W0E\u00109L\u007f\t'qAoEd\u0002Mg\u0015\u000e4Wk\u0001\"%B-E,#\u0003-(,5Vf\u0000dqMe\u0011cv") + var1.getName() + a("v\r"));
         }

         var5 = new Vector();
         ((List)var5).add(var1);
      }

      Result var10000 = this.a((List)var5, var2, var3, var4);
      if (SnmpException.b) {
         i = !var8;
      }

      return var10000;
   }

   private Result a(List var1, SnmpMetadata var2, boolean var3) {
      Result var4 = new Result();
      return this.a(var1, var2, var3, var4);
   }

   private Result a(List var1, SnmpMetadata var2, boolean var3, Result var4) {
      if (!var3) {
         this.a(var1, var2, var4);
         this.a(var1, var2);
         if (var1.size() <= 0) {
            return var4;
         }

         this.c(var1, var2, var4);
         this.e(var1, var2, var4);
         this.b(var2, var4);
         this.d(var1, var2, var4);
         this.f(var1, var2, var4);
         this.g(var1, var2, var4);
         this.h(var1, var2, var4);
         this.i(var1, var2, var4);
         this.j(var1, var2, var4);
         this.k(var1, var2, var4);
         this.l(var1, var2, var4);
         this.a(var2, var4);
         this.m(var1, var2, var4);
         if (!i) {
            return var4;
         }
      }

      this.b(var1, var2, var4);
      return var4;
   }

   private void a(List var1, SnmpMetadata var2) {
      boolean var8 = i;
      if (this.a.isDebugEnabled()) {
         this.a.debug(a("#Fg\n58MmE'$Sf\f 0Wo\u0016"));
      }

      HashSet var3 = new HashSet();
      SnmpModule[] var4 = var2.getModules();
      int var5 = 0;

      while(var5 < var4.length) {
         var3.add(var4[var5].getName());
         ++var5;
         if (var8) {
            break;
         }
      }

      ListIterator var9 = var1.listIterator();

      while(var9.hasNext()) {
         label27: {
            Element var6 = (Element)var9.next();
            String var7 = var6.getAttributeValue(a("?Bg\u0000"));
            this.a.debug(a("2Ko\u0006(8MmE.>G\u007f\t&k") + var7);
            if (var3.contains(var7)) {
               var9.remove();
               if (!this.a.isDebugEnabled()) {
                  break label27;
               }

               this.a.debug(a("8Dd\n18MmE'$Sf\f 0WoE.>G\u007f\t&k\u0003") + var7);
               if (!var8) {
                  break label27;
               }
            }

            var3.add(var7);
         }

         if (var8) {
            break;
         }
      }

   }

   private void a(List var1, List var2, Result var3) {
      boolean var9 = i;
      HashSet var4 = new HashSet();
      ListIterator var5 = var2.listIterator();

      while(true) {
         if (var5.hasNext()) {
            Element var6 = (Element)var5.next();
            String var7 = var6.getAttributeValue(a("?Bg\u0000"));
            var4.add(var7);
            if (!var9 || !var9) {
               continue;
            }
            break;
         }

         this.a((List)var1, (Set)var4, (Result)var3);
         break;
      }

      ListIterator var10 = var1.listIterator();

      HashSet var10000;
      while(true) {
         if (var10.hasNext()) {
            Element var11 = (Element)var10.next();
            String var8 = var11.getAttributeValue(a("?Bg\u0000"));
            var10000 = var4;
            if (var9) {
               break;
            }

            var4.remove(var8);
            if (!var9) {
               continue;
            }
         }

         var10000 = var4;
         break;
      }

      Iterator var12 = var10000.iterator();

      while(var12.hasNext()) {
         var3.addError(new MetadataError.NoSuchModule((String)var12.next()));
         if (var9) {
            break;
         }
      }

   }

   private void a(List var1, SnmpMetadata var2, Result var3) {
      boolean var7 = i;
      if (this.a.isDebugEnabled()) {
         this.a.debug(a("!Qe\u0006&\"PX\u00002$Jx\u00000y\r$Kj"));
      }

      Set var10000;
      label40: {
         Set var4 = this.b(var1, var2);
         if (this.b) {
            int var5 = 0;
            int var6 = 1;

            while(var6 > 0) {
               var10000 = var4;
               if (var7) {
                  break label40;
               }

               if (var4.size() <= 0 || var5 >= 20) {
                  break;
               }

               var6 = this.a(var1, var4, var3);
               var4 = this.b(var1, var2);
               ++var5;
               if (var7) {
                  break;
               }
            }
         }

         var10000 = var4;
      }

      Iterator var8 = var10000.iterator();

      while(var8.hasNext()) {
         String var9 = (String)var8.next();
         var3.addError(new MetadataError.NoSuchModule(var9));
         if (var7) {
            break;
         }
      }

   }

   private Set b(List var1, SnmpMetadata var2) {
      boolean var13 = i;
      HashSet var3 = new HashSet();
      HashSet var4 = new HashSet();
      ListIterator var5 = var1.listIterator();

      label41:
      while(var5.hasNext()) {
         Element var6 = (Element)var5.next();
         String var7 = var6.getAttributeValue(a("?Bg\u0000"));
         var4.add(var7);
         Element var8 = var6.getChild(a("\u0003F{\u0010*#Fy"));
         if (var8 != null) {
            List var9 = var8.getChildren();
            ListIterator var10 = var9.listIterator();

            while(var10.hasNext()) {
               Element var11 = (Element)var10.next();
               String var12 = var11.getAttributeValue(a("?Bg\u0000"));
               var3.add(var12);
               if (var13 && var13) {
                  continue label41;
               }
            }
         }

         if (var13) {
            break;
         }
      }

      SnmpModule[] var14 = var2.getModules();
      int var15 = 0;

      while(true) {
         if (var15 < var14.length) {
            var4.add(var14[var15].getName());
            ++var15;
            if (var13) {
               break;
            }

            if (!var13) {
               continue;
            }
         }

         var3.removeAll(var4);
         break;
      }

      return var3;
   }

   private void a(SnmpMetadata var1, Result var2) {
      Enumeration var3 = this.g.keys();

      while(var3.hasMoreElements()) {
         String var4 = (String)var3.nextElement();
         SnmpTableEntryInfo var5 = (SnmpTableEntryInfo)this.g.get(var4);
         StringTokenizer var6 = new StringTokenizer(var4, ":", false);
         String var7 = "";
         String var8 = var4;
         if (var6.countTokens() > 1) {
            var7 = var6.nextToken();
            var8 = var6.nextToken();
         }

         try {
            SnmpTableEntryInfo var9 = this.a(var7, var8, var1);
            var5.setAugments(var9);
         } catch (SnmpValueException var10) {
            var2.addError(new MetadataError.NoSuchTableEntry(var7, var8, var5.getName()));
         }

         if (i) {
            break;
         }
      }

   }

   private void b(List var1, SnmpMetadata var2, Result var3) {
      boolean var12 = i;
      ListIterator var4 = var1.listIterator();

      while(var4.hasNext()) {
         Element var5 = (Element)var4.next();
         String var10000 = var5.getAttribute(a("?Bg\u0000")).getValue();

         label35:
         while(true) {
            String var6 = var10000;
            List var7 = var5.getChildren();
            ListIterator var8 = var7.listIterator();
            String var9 = null;

            while(true) {
               if (!var8.hasNext()) {
                  break label35;
               }

               try {
                  Element var10 = (Element)var8.next();
                  var9 = var10.getAttributeValue(a("?Bg\u0000"));
                  String var11 = var10.getAttributeValue(a(">Jn"));
                  var10000 = var11;
                  if (var12 || var12) {
                     break;
                  }

                  if (var11 != null) {
                     var2.addOid(var6, var11, var9);
                  }
               } catch (NullPointerException var13) {
                  var3.addError(new MetadataError.InvalidOID(var6, var9, a("<Jy\u0016*?D*\u00047%Qc\u00076%F")));
                  if (var12) {
                     break label35;
                  }
               }
            }
         }

         if (var12) {
            break;
         }
      }

   }

   private void c(List var1, SnmpMetadata var2, Result var3) {
      boolean var12 = i;
      ListIterator var4 = var1.listIterator();

      label33:
      while(var4.hasNext()) {
         Element var5 = (Element)var4.next();
         String var6 = var5.getAttribute(a("?Bg\u0000")).getValue();
         this.a.debug(a("!Qe\u0006&\"Pc\u000b$qLc\u00010qEe\u0017cv") + var6 + "'");
         List var7 = var5.getChildren(a("\u001eJn"));
         ListIterator var8 = var7.listIterator();
         String var9 = null;

         while(var8.hasNext()) {
            try {
               Element var10 = (Element)var8.next();
               var9 = var10.getAttribute(a("?Bg\u0000")).getValue();
               String var11 = var10.getAttribute(a(">Jn")).getValue();
               var2.addOid(var6, var11, var9);
            } catch (NullPointerException var13) {
               var3.addError(new MetadataError.InvalidOID(var6, var9, a("<Jy\u0016*?D*\u00047%Qc\u00076%F")));
               if (var12) {
                  break;
               }
               continue;
            }

            if (var12 || var12) {
               continue label33;
            }
         }

         if (var12) {
            break;
         }
      }

   }

   private void d(List var1, SnmpMetadata var2, Result var3) {
      ListIterator var4 = var1.listIterator();

      while(var4.hasNext()) {
         Element var5 = (Element)var4.next();
         String var6 = var5.getAttribute(a("?Bg\u0000")).getValue();
         this.a.debug(a("!Qe\u0006&\"Pc\u000b$qNe\u00016=F*\f'4M~\f78FyE%>Q*B") + var6 + "'");
         Element var7 = var5.getChild(a("\u001cLn\u0010/4jn\u0000-%J~\u001c"));
         if (var7 != null) {
            String var8 = null;

            try {
               var8 = var7.getAttribute(a("?Bg\u0000")).getValue();
               String var9 = var7.getAttribute(a(">Jn")).getValue();
               String var10 = var7.getAttributeValue(a("=By\u0011\u0016!Gk\u0011&5"));
               Element var11 = var7.getChild(a("\u0015Fy\u000618S~\f,?"));
               Element var12 = var7.getChild(a("\u001eQm\u0004-8Yk\u0011*>M"));
               Element var13 = var7.getChild(a("\u0012Ld\u0011\"2WC\u000b%>"));
               String var14 = null;
               String var15 = null;
               String var16 = null;
               if (var11 != null) {
                  var15 = var11.getText();
               }

               if (var12 != null) {
                  var14 = var12.getText();
               }

               if (var13 != null) {
                  var16 = var13.getText();
               }

               SnmpModuleIdentityInfo var17 = new SnmpModuleIdentityInfo(var10, var14, var16, var15);

               try {
                  SnmpOidInfo var18 = var2.resolveOid(var9);
                  if (var18 != null && var18 instanceof SnmpModuleIdentityInfo) {
                     var3.addError(new MetadataError.DuplicateOID(var6, var9, var8));
                  }
               } catch (Throwable var19) {
               }

               var2.add(var6, var9, var8, var17);
            } catch (NullPointerException var20) {
               var3.addError(new MetadataError.InvalidModuleIdentity(var6, var8, a("<Jy\u0016*?D*\u00047%Qc\u00076%F")));
            }
         }

         if (i) {
            break;
         }
      }

   }

   private void b(SnmpMetadata var1, Result var2) {
      boolean var11 = i;
      SnmpModule[] var3 = var1.getModules();
      int var4 = 0;

      while(true) {
         label44:
         while(true) {
            if (var4 >= var3.length) {
               return;
            }

            SnmpTypeInfo[] var5 = var3[var4].getTypes();
            int var6 = 0;

            while(true) {
               if (var6 >= var5.length) {
                  break label44;
               }

               SnmpTypeInfo var7 = var5[var6];
               String var8 = var7.getTypeRefName();
               if (var11) {
                  break;
               }

               if (var8 != null) {
                  try {
                     SnmpTypeInfo var9 = null;

                     try {
                        var9 = var1.getType(var8);
                     } catch (SnmpValueException var12) {
                     }

                     if (var9 != null) {
                        var7.setTypeRef(var9);
                     }
                  } catch (Exception var13) {
                  }
               }

               ++var6;
               if (var11) {
                  break label44;
               }
            }
         }

         ++var4;
         if (var11) {
         }
      }
   }

   private void e(List var1, SnmpMetadata var2, Result var3) {
      boolean var19 = i;
      ListIterator var4 = var1.listIterator();

      while(var4.hasNext()) {
         Element var5 = (Element)var4.next();
         String var10000 = var5.getAttribute(a("?Bg\u0000")).getValue();

         label56:
         while(true) {
            String var6 = var10000;
            this.a.debug(a("!Qe\u0006&\"Pc\u000b$qWs\u0015&\"\u0003l\n1q\u0004") + var6 + "'");
            List var7 = var5.getChildren(a("\u0005Zz\u0000"));
            ListIterator var8 = var7.listIterator();

            while(true) {
               if (!var8.hasNext()) {
                  break label56;
               }

               Element var9 = (Element)var8.next();
               String var10 = var9.getAttributeValue(a("?Bg\u0000"));
               String var11 = var9.getAttributeValue(a("%Zz\u0000"));
               String var12 = var9.getAttributeValue(a("%Zz\u000014E"));
               String var13 = var9.getAttributeValue(a(">A`\u0011:!F"));
               String var14 = var9.getAttributeValue(a("\"Nc\u0011:!F"));
               String var15 = var9.getAttributeValue(a("5Jy\u0015/0ZB\f-%"));
               String var16 = var9.getAttributeValue(a("\"Wk\u00116\""));

               try {
                  SnmpTypeInfo var17 = new SnmpTypeInfo(var10, var11, var13, var15);
                  var10000 = var14;
                  if (var19) {
                     break;
                  }

                  if (var14 != null) {
                     var17.setSmiType(var14);
                  }

                  if (var16 != null) {
                     var17.setStatus(var16);
                  }

                  if (var12 != null) {
                     var17.setTypeRefName(var12);
                  }

                  if (this.f) {
                     Element var18 = var9.getChild(a("\u0015Fy\u000618S~\f,?"));
                     if (this.f && var18 != null) {
                        var17.setDescription(var18.getText());
                     }
                  }

                  this.a(var2, var6, var10, var17, false, var9, (String)null, var3);
                  var2.add(var6, var10, var17);
               } catch (SnmpValueException var20) {
                  this.a.debug(a("8M|\u0004/8G*\u0011:!F0E") + var9, var20);
                  var3.addError(new MetadataError.InvalidType(var6, var10, var20.getMessage()));
               } catch (Exception var21) {
                  this.a.debug(a("8M|\u0004/8G*\u0011:!F0E") + var9, var21);
                  var3.addError(new MetadataError.InvalidType(var6, var10, var21.toString()));
               }

               if (var19) {
                  break label56;
               }
            }
         }

         if (var19) {
            break;
         }
      }

   }

   private void f(List var1, SnmpMetadata var2, Result var3) {
      boolean var21 = i;
      ListIterator var4 = var1.listIterator();

      while(var4.hasNext()) {
         Element var5 = (Element)var4.next();
         String var10000 = var5.getAttribute(a("?Bg\u0000")).getValue();

         label78:
         while(true) {
            String var6 = var10000;
            this.a.debug(a("!Qe\u0006&\"Pc\u000b$qLh\u000f&2WyE%>Q*B") + var6 + "'");
            List var7 = var5.getChildren(a("\u001eA`\u0000 %"));
            ListIterator var8 = var7.listIterator();

            while(true) {
               if (!var8.hasNext()) {
                  break label78;
               }

               String var9 = null;

               try {
                  Element var10 = (Element)var8.next();
                  var9 = var10.getAttribute(a("?Bg\u0000")).getValue();
                  String var11 = var10.getAttribute(a(">Jn")).getValue();
                  String var12 = var10.getAttribute(a(">A`\u0011:!F")).getValue();
                  String var13 = var10.getAttribute(a("\"Nc\u0011:!F")).getValue();
                  String var14 = var10.getAttribute(a("0@i\u00000\"")).getValue();
                  String var15 = var10.getAttributeValue(a("\"Wk\u00116\""));
                  String var16 = var10.getAttributeValue(a("5Fl\u0013\"="));
                  SnmpObjectInfo var17 = new SnmpObjectInfo(var12, var14);
                  var10000 = var15;
                  if (var21) {
                     break;
                  }

                  if (var15 != null) {
                     var17.setStatus(var15);
                  }

                  var17.setDefVal(var16);
                  if (var13 != null) {
                     var17.setSmiType(var13);
                  }

                  String var18 = var10.getAttributeValue(a("%Zz\u000014E"));
                  SnmpTypeInfo var19 = null;
                  if (var18 != null) {
                     try {
                        var19 = var2.getType(var18);
                     } catch (SnmpValueException var23) {
                     }

                     if (var19 == null) {
                        var3.addError(new MetadataError.NoSuchType(var6, var18, var9));
                     }
                  }

                  var19 = this.a(var2, var6, var9, var19, true, var10, var12, var3);
                  if (var19 != null) {
                     var17.setTypeInfo(var19);
                     if (var13 != null) {
                        var19.setSmiType(var13);
                     }
                  }

                  if (this.f) {
                     Element var20 = var10.getChild(a("\u0015Fy\u000618S~\f,?"));
                     if (var20 != null) {
                        var17.setDescription(var20.getText());
                     }
                  }

                  try {
                     SnmpOidInfo var26 = var2.resolveOid(var11);
                     if (var26 != null && var26 instanceof SnmpObjectInfo) {
                        var3.addError(new MetadataError.DuplicateOID(var6, var11, var9));
                     }
                  } catch (Throwable var22) {
                  }

                  var2.add(var6, var11, var9, var17);
               } catch (SnmpValueException var24) {
                  var3.addError(new MetadataError.InvalidObject(var6, var9, var24.getMessage()));
               } catch (Exception var25) {
                  var3.addError(new MetadataError.InvalidObject(var6, var9, var25.toString()));
               }

               if (var21) {
                  break label78;
               }
            }
         }

         if (var21) {
            break;
         }
      }

   }

   private SnmpTypeInfo a(SnmpMetadata var1, String var2, String var3, SnmpTypeInfo var4, boolean var5, Element var6, String var7, Result var8) throws Exception {
      boolean var13 = i;

      try {
         Element var9 = var6.getChild(a("\u001fBg\u0000'\u001fVg\u0007&#oc\u00167"));
         if (var9 != null) {
            label48: {
               if (var4 == null) {
                  var4 = new SnmpTypeInfo((String)null, (String)null, var7, (String)null);
                  if (!var13) {
                     break label48;
                  }
               }

               if (var5) {
                  SnmpTypeInfo var10 = var4;
                  var4 = new SnmpTypeInfo(var4);
                  var4.setBaseType(var10);
               }
            }

            this.c(var2, var3, var4, var9, var8);
         }

         Element var15 = var6.getChild(a("\u0002Jp\u0000"));
         if (var15 != null) {
            label41: {
               if (var4 == null) {
                  var4 = new SnmpTypeInfo((String)null, (String)null, var7, (String)null);
                  if (!var13) {
                     break label41;
                  }
               }

               if (var5) {
                  SnmpTypeInfo var11 = var4;
                  var4 = new SnmpTypeInfo(var4);
                  var4.setBaseType(var11);
               }
            }

            this.a(var2, var3, var4, var15, var8);
         }

         Element var16 = var6.getChild(a("\u0003Bd\u0002&\u0002So\u0006"));
         if (var16 != null) {
            label34: {
               if (var4 == null) {
                  var4 = new SnmpTypeInfo((String)null, (String)null, var7, (String)null);
                  if (!var13) {
                     break label34;
                  }
               }

               if (var5) {
                  SnmpTypeInfo var12 = var4;
                  var4 = new SnmpTypeInfo(var4);
                  var4.setBaseType(var12);
               }
            }

            this.b(var2, var3, var4, var16, var8);
         }

         return var4;
      } catch (SnmpValueException var14) {
         this.a.debug(a("4[i\u00003%Je\u000bc8M*\u001663Ws\u0015&qSx\n 4Py\f-6"), var14);
         throw new Exception(a("\u0002Vh\u0011:!F*634@0E") + var14.getMessage());
      }
   }

   private void a(String var1, String var2, SnmpTypeInfo var3, Element var4, Result var5) throws Exception {
      try {
         Element var6 = var4.getChild(a("\u0003Bd\u0002&\u0002So\u0006"));
         if (var6 != null) {
            this.b(var1, var2, var3, var6, var5);
         }

      } catch (NumberFormatException var7) {
         throw new Exception(a("\u0018M|\u0004/8G*6*+F*\u001634@c\u0003*4Q0E") + var4.getAttributeValue(a("\"Jp\u0000")));
      }
   }

   private void b(String var1, String var2, SnmpTypeInfo var3, Element var4, Result var5) throws Exception {
      boolean var15 = i;

      try {
         new Vector();
         List var7 = var4.getChildren(a("\u0003Bd\u0002&\u0018Wo\b"));
         ListIterator var8 = var7.listIterator();
         RangeItem[] var9 = new RangeItem[var7.size()];
         int var10 = 0;

         while(var8.hasNext()) {
            Element var11 = (Element)var8.next();
            String var12 = var11.getAttributeValue(a("\"Jd\u0002/4"));
            if (var15) {
               return;
            }

            label27: {
               if (var12 != null) {
                  var9[var10++] = new RangeItem(var12);
                  if (!var15) {
                     break label27;
                  }
               }

               String var13 = var11.getAttributeValue(a("=L}\u00001"));
               String var14 = var11.getAttributeValue(a("$Sz\u00001"));
               var9[var10++] = new RangeItem(var13, var14);
            }

            if (var15) {
               break;
            }
         }

         var3.setRangeSpec(var9);
      } catch (NumberFormatException var16) {
         var5.addError(new MetadataError.InvalidRange(var1, var2, a("8M|\u0004/8G*\u000b6<Ao\u0017cv") + var16.getMessage() + "'"));
      }

   }

   private void c(String var1, String var2, SnmpTypeInfo var3, Element var4, Result var5) {
      boolean var13 = i;
      List var6 = var4.getChildren(a("?M"));
      ListIterator var7 = var6.listIterator();
      Hashtable var8 = new Hashtable();

      while(true) {
         if (var7.hasNext()) {
            Element var9 = (Element)var7.next();
            String var10 = var9.getAttributeValue(a("?Bg\u0000"));
            String var11 = var9.getAttributeValue(a("'Bf\u0010&"));

            label21: {
               try {
                  Long var12 = new Long(var11);
                  var8.put(var10, var12);
               } catch (NumberFormatException var14) {
                  var5.addError(new MetadataError.InvalidNamedNumber(var1, var2, var10, var11));
                  break label21;
               }

               if (var13) {
                  break;
               }
            }

            if (!var13) {
               continue;
            }
         }

         var3.setNameToNumberMap(var8);
         break;
      }

   }

   private void g(List var1, SnmpMetadata var2, Result var3) {
      boolean var28 = i;
      ListIterator var4 = var1.listIterator();

      while(var4.hasNext()) {
         Element var5 = (Element)var4.next();
         String var6 = var5.getAttribute(a("?Bg\u0000")).getValue();
         Element var10000 = var5;

         label100:
         while(true) {
            List var7 = var10000.getChildren(a("\u0005Bh\t&"));
            ListIterator var8 = var7.listIterator();

            while(true) {
               if (!var8.hasNext()) {
                  break label100;
               }

               String var9 = null;

               try {
                  label109: {
                     Element var10 = (Element)var8.next();
                     var9 = var10.getAttribute(a("?Bg\u0000")).getValue();
                     String var11 = var10.getAttribute(a(">Jn")).getValue();
                     String var12 = var10.getAttributeValue(a("\"Wk\u00116\""));
                     Element var13 = var10.getChild(a("\u0014M~\u0017:"));
                     var10000 = var13;
                     if (var28) {
                        break;
                     }

                     if (var13 != null) {
                        String var14 = var13.getAttribute(a("?Bg\u0000")).getValue();
                        String var15 = var13.getAttribute(a(">Jn")).getValue();
                        String var16 = var10.getAttributeValue(a("\"Wk\u00116\""));
                        List var17 = var13.getChildren(a("\u0012Lf\u0010.?"));
                        SnmpObjectInfo[] var18 = this.a(var6, var17, var2);
                        Element var19 = var13.getChild(a("\u0018Mn\u0000;4P"));
                        Element var20 = var13.getChild(a("\u0010Vm\b&?Wy"));
                        SnmpObjectInfo[] var21 = null;
                        SnmpTableEntryInfo var22 = null;
                        if (var19 != null) {
                           List var23 = var19.getChildren();
                           UserData var24 = new UserData();
                           var21 = this.a(var6, var23, var2, true, var24);
                           var22 = new SnmpTableEntryInfo(var18, var21);
                           var22.setImplied(var24.a);
                        } else if (var20 != null) {
                           Element var33 = var20.getChild(a("\u001eA`\u0000 %qo\u0003"));
                           if (var33 != null) {
                              String var35 = var33.getAttributeValue(a("?Bg\u0000"));
                              StringTokenizer var25 = new StringTokenizer(var35, ":", false);
                              String var26 = var6;
                              if (var25.countTokens() > 1) {
                                 var26 = var25.nextToken();
                                 var35 = var25.nextToken();
                              }

                              try {
                                 SnmpTableEntryInfo var27 = this.a(var26, var35, var2);
                                 var22 = new SnmpTableEntryInfo(var18, var27);
                              } catch (SnmpValueException var30) {
                                 var22 = new SnmpTableEntryInfo(var18, (SnmpTableEntryInfo)null);
                                 this.g.put(var26 + ":" + var35, var22);
                              }
                           }
                        }

                        SnmpTableInfo var34 = new SnmpTableInfo(var18, var21, var22);
                        if (var12 != null) {
                           var34.setStatus(var12);
                        }

                        if (var16 != null) {
                           var34.setStatus(var16);
                        }

                        if (this.f) {
                           Element var37 = var13.getChild(a("\u0015Fy\u000618S~\f,?"));
                           if (var37 != null) {
                              var22.setDescription(var37.getText());
                           }

                           Element var36 = var10.getChild(a("\u0015Fy\u000618S~\f,?"));
                           if (var36 != null) {
                              var34.setDescription(var36.getText());
                           }
                        }

                        int var38 = 0;

                        while(var38 < var18.length) {
                           var18[var38].setColumnar(true);
                           var18[var38].setTableInfo(var34);
                           ++var38;
                           if (var28) {
                              break label109;
                           }

                           if (var28) {
                              break;
                           }
                        }

                        try {
                           SnmpOidInfo var39 = var2.resolveOid(var11);
                           if (var39 != null && var39 instanceof SnmpTableInfo) {
                              var3.addError(new MetadataError.DuplicateOID(var6, var11, var9));
                           }
                        } catch (Throwable var29) {
                        }

                        var2.add(var6, var15, var14, (SnmpOidInfo)var22);
                        var2.add(var6, var11, var9, var34);
                        if (!var28) {
                           break label109;
                        }
                     }

                     var3.addError(new MetadataError.ElementMissing(var6, a("\u0005Bh\t&qfd\u00111("), var9));
                  }
               } catch (SnmpValueException var31) {
                  var3.addError(new MetadataError.InvalidTable(var6, var9, var31.getMessage()));
               } catch (Exception var32) {
                  var3.addError(new MetadataError.InvalidTable(var6, var9, var32.toString()));
               }

               if (var28) {
                  break label100;
               }
            }
         }

         if (var28) {
            break;
         }
      }

   }

   private void h(List var1, SnmpMetadata var2, Result var3) {
      boolean var18 = i;
      ListIterator var4 = var1.listIterator();

      while(var4.hasNext()) {
         Element var5 = (Element)var4.next();
         String var10000 = var5.getAttribute(a("?Bg\u0000")).getValue();

         label56:
         while(true) {
            String var6 = var10000;
            this.a.debug(a("!Qe\u0006&\"Pc\u000b$qMe\u0011*7Ji\u000478Ld\u0016c7LxEd") + var6 + "'");
            List var7 = var5.getChildren(a("\u001fL~\f%8@k\u0011*>M"));
            ListIterator var8 = var7.listIterator();

            while(true) {
               if (!var8.hasNext()) {
                  break label56;
               }

               String var9 = null;

               try {
                  Element var10 = (Element)var8.next();
                  var9 = var10.getAttribute(a("?Bg\u0000")).getValue();
                  String var11 = var10.getAttribute(a(">Jn")).getValue();
                  String var12 = var10.getAttributeValue(a("\"Wk\u00116\""));
                  Element var13 = var10.getChild(a("\u001eA`\u0000 %P"));
                  List var14 = var13.getChildren();
                  SnmpObjectInfo[] var15 = this.a(var6, var14, var2);
                  SnmpNotificationInfo var16 = new SnmpNotificationInfo(var15);
                  var10000 = var12;
                  if (var18) {
                     break;
                  }

                  if (var12 != null) {
                     var16.setStatus(var12);
                  }

                  if (this.f) {
                     Element var17 = var10.getChild(a("\u0015Fy\u000618S~\f,?"));
                     if (var17 != null) {
                        var16.setDescription(var17.getText());
                     }
                  }

                  try {
                     SnmpOidInfo var22 = var2.resolveOid(var11);
                     if (var22 != null && var22 instanceof SnmpNotificationInfo) {
                        var3.addError(new MetadataError.DuplicateOID(var6, var11, var9));
                     }
                  } catch (Throwable var19) {
                  }

                  var2.add(var6, var11, var9, var16);
               } catch (SnmpValueException var20) {
                  var3.addError(new MetadataError.InvalidNotification(var6, var9, var20.getMessage()));
               } catch (Exception var21) {
                  var3.addError(new MetadataError.InvalidNotification(var6, var9, var21.toString()));
               }

               if (var18) {
                  break label56;
               }
            }
         }

         if (var18) {
            break;
         }
      }

   }

   private void i(List var1, SnmpMetadata var2, Result var3) {
      boolean var20 = i;
      ListIterator var4 = var1.listIterator();

      label87:
      do {
         boolean var10000 = var4.hasNext();

         while(var10000) {
            Element var5 = (Element)var4.next();
            String var6 = var5.getAttribute(a("?Bg\u0000")).getValue();
            this.a.debug(a("!Qe\u0006&\"Pc\u000b$qWx\u00043\"\u0003l\n1q\u0004") + var6 + "'");
            List var7 = var5.getChildren(a("\u0005Qk\u0015"));
            ListIterator var8 = var7.listIterator();

            while(true) {
               if (!var8.hasNext()) {
                  continue label87;
               }

               String var9 = null;

               try {
                  Element var10 = (Element)var8.next();
                  var9 = var10.getAttribute(a("?Bg\u0000")).getValue();
                  String var11 = var10.getAttribute(a("4M~\u00001!Qc\u0016&")).getValue();
                  String var12 = var10.getAttribute(a("8G")).getValue();
                  String var13 = var10.getAttributeValue(a("\"Wk\u00116\""));
                  String var14 = var11 + a("\u007f\u0013$") + var12;
                  var10000 = var11.equalsIgnoreCase(a("\"Mg\u0015"));
                  if (var20) {
                     break;
                  }

                  label95: {
                     if (var10000 || var9.equals(a("2Lf\u0001\u0010%Bx\u0011")) || var9.equals(a("&Bx\b\u0010%Bx\u0011")) || var9.equals(a("=Jd\u000e\u0016!")) || var9.equals(a("=Jd\u000e\u0007>Td")) || var9.equals(a("0V~\r&?Wc\u0006\"%Je\u000b\u00050Jf\u001014"))) {
                        try {
                           int var15 = Integer.parseInt(var12) + 1;
                           var14 = a("`\r9Ku\u007f\u0012$Smb\r;Kr\u007f\u0016$") + var15;
                           break label95;
                        } catch (Exception var22) {
                           if (!var20) {
                              break label95;
                           }
                        }
                     }

                     if (var10.getAttributeValue(a(">Jn")) != null) {
                        var14 = var10.getAttributeValue(a(">Jn"));
                     }
                  }

                  Element var25 = var10.getChild(a("\u0007Bx\f\"3Oo\u0016"));
                  List var16 = var25.getChildren();
                  SnmpObjectInfo[] var17 = this.a(var6, var16, var2);
                  SnmpNotificationInfo var18 = new SnmpNotificationInfo(var17);
                  if (var13 != null) {
                     var18.setStatus(var13);
                  }

                  if (this.f) {
                     Element var19 = var10.getChild(a("\u0015Fy\u000618S~\f,?"));
                     if (var19 != null) {
                        var18.setDescription(var19.getText());
                     }
                  }

                  try {
                     SnmpOidInfo var26 = var2.resolveOid(var14);
                     if (var26 != null && var26 instanceof SnmpNotificationInfo) {
                        var3.addError(new MetadataError.DuplicateOID(var6, var14, var9));
                     }
                  } catch (Throwable var21) {
                  }

                  var2.add(var6, var14, var9, var18);
               } catch (SnmpValueException var23) {
                  this.a.debug(a("\u0018M|\u0004/8G*+,%Jl\f 0Wc\n-k\u0003") + var9 + "(" + var23 + ")");
                  var3.addError(new MetadataError.InvalidNotification(var6, var9, var23.getMessage()));
               } catch (Exception var24) {
                  var3.addError(new MetadataError.InvalidNotification(var6, var9, var24.toString()));
               }

               if (var20) {
                  continue label87;
               }
            }
         }

         return;
      } while(!var20);

   }

   private void j(List var1, SnmpMetadata var2, Result var3) {
      boolean var18 = i;
      ListIterator var4 = var1.listIterator();

      while(var4.hasNext()) {
         Element var5 = (Element)var4.next();
         String var10000 = var5.getAttribute(a("?Bg\u0000")).getValue();

         label56:
         while(true) {
            String var6 = var10000;
            this.a.debug(a("!Qe\u0006&\"Pc\u000b$qLh\u000f&2W*\u00021>Vz\u0016c7LxEd") + var6 + "'");
            List var7 = var5.getChildren(a("\u001eA`\u0000 %dx\n6!"));
            ListIterator var8 = var7.listIterator();

            while(true) {
               if (!var8.hasNext()) {
                  break label56;
               }

               String var9 = null;

               try {
                  Element var10 = (Element)var8.next();
                  var9 = var10.getAttribute(a("?Bg\u0000")).getValue();
                  String var11 = var10.getAttribute(a(">Jn")).getValue();
                  String var12 = var10.getAttributeValue(a("\"Wk\u00116\""));
                  Element var13 = var10.getChild(a("\u001eA`\u0000 %P"));
                  List var14 = var13.getChildren();
                  SnmpObjectInfo[] var15 = this.a(var6, var14, var2);
                  SnmpObjectGroupInfo var16 = new SnmpObjectGroupInfo(var15);
                  var10000 = var12;
                  if (var18) {
                     break;
                  }

                  if (var12 != null) {
                     var16.setStatus(var12);
                  }

                  if (this.f) {
                     Element var17 = var10.getChild(a("\u0015Fy\u000618S~\f,?"));
                     if (var17 != null) {
                        var16.setDescription(var17.getText());
                     }
                  }

                  try {
                     SnmpOidInfo var22 = var2.resolveOid(var11);
                     if (var22 != null && var22 instanceof SnmpObjectGroupInfo) {
                        var3.addError(new MetadataError.DuplicateOID(var6, var11, var9));
                     }
                  } catch (Throwable var19) {
                  }

                  var2.add(var6, var11, var9, var16);
               } catch (SnmpValueException var20) {
                  var3.addError(new MetadataError.InvalidObjectGroup(var6, var9, var20.getMessage()));
               } catch (Exception var21) {
                  var3.addError(new MetadataError.InvalidObjectGroup(var6, var9, var21.toString()));
               }

               if (var18) {
                  break label56;
               }
            }
         }

         if (var18) {
            break;
         }
      }

   }

   private void k(List var1, SnmpMetadata var2, Result var3) {
      boolean var18 = i;
      ListIterator var4 = var1.listIterator();

      while(var4.hasNext()) {
         Element var5 = (Element)var4.next();
         String var10000 = var5.getAttribute(a("?Bg\u0000")).getValue();

         label56:
         while(true) {
            String var6 = var10000;
            this.a.debug(a("!Qe\u0006&\"Pc\u000b$qMe\u0011*7Ji\u000478LdE$#L\u007f\u00150qEe\u0017cv") + var6 + "'");
            List var7 = var5.getChildren(a("\u001fL~\f%8@k\u0011*>MM\u0017,$S"));
            ListIterator var8 = var7.listIterator();

            while(true) {
               if (!var8.hasNext()) {
                  break label56;
               }

               String var9 = null;

               try {
                  Element var10 = (Element)var8.next();
                  var9 = var10.getAttribute(a("?Bg\u0000")).getValue();
                  String var11 = var10.getAttribute(a(">Jn")).getValue();
                  String var12 = var10.getAttributeValue(a("\"Wk\u00116\""));
                  Element var13 = var10.getChild(a("\u001fL~\f%8@k\u0011*>My"));
                  List var14 = var13.getChildren();
                  SnmpNotificationInfo[] var15 = this.b(var6, var14, var2);
                  SnmpNotificationGroupInfo var16 = new SnmpNotificationGroupInfo(var15);
                  var10000 = var12;
                  if (var18) {
                     break;
                  }

                  if (var12 != null) {
                     var16.setStatus(var12);
                  }

                  if (this.f) {
                     Element var17 = var10.getChild(a("\u0015Fy\u000618S~\f,?"));
                     if (var17 != null) {
                        var16.setDescription(var17.getText());
                     }
                  }

                  try {
                     SnmpOidInfo var22 = var2.resolveOid(var11);
                     if (var22 != null && var22 instanceof SnmpNotificationGroupInfo) {
                        var3.addError(new MetadataError.DuplicateOID(var6, var11, var9));
                     }
                  } catch (Throwable var19) {
                  }

                  var2.add(var6, var11, var9, var16);
               } catch (SnmpValueException var20) {
                  var3.addError(new MetadataError.InvalidNotificationGroup(var6, var9, var20.getMessage()));
               } catch (Exception var21) {
                  var3.addError(new MetadataError.InvalidNotificationGroup(var6, var9, var21.toString()));
               }

               if (var18) {
                  break label56;
               }
            }
         }

         if (var18) {
            break;
         }
      }

   }

   private void l(List var1, SnmpMetadata var2, Result var3) {
      ListIterator var4 = var1.listIterator();

      while(var4.hasNext()) {
         Element var5 = (Element)var4.next();
         String var6 = var5.getAttribute(a("?Bg\u0000")).getValue();
         this.a.debug(a("!Qe\u0006&\"Pc\u000b$qNe\u00016=F*\f'4M~\f78FyE%>Q*B") + var6 + "'");

         try {
            this.a(var6, var5, var2, var3);
         } catch (Exception var8) {
            this.a.debug(a("!Qe\u0006&\"PK\u0002&?WI\u000430Ac\t*%Jo\u0016c4[i\u00003%Je\u000bc7LxE.>G\u007f\t&k\u0003") + var6, var8);
            var3.addError(new MetadataError.ProcessingFailure(var6, a("4Qx\n1qJdE\u00026Fd\u0011\u00000Sk\u0007*=J~\f&\"\u0003z\u0017,2Fy\u0016*?D") + var8));
         }

         if (i) {
            break;
         }
      }

   }

   private void a(String var1, Element var2, SnmpMetadata var3, Result var4) {
      boolean var40 = i;
      List var5 = var2.getChildren(a("\u0010Do\u000b7\u0012Bz\u0004!8Oc\u0011*4P"));
      ListIterator var6 = var5.listIterator();

      label200:
      while(var6.hasNext()) {
         Element var7 = (Element)var6.next();
         String var8 = var7.getAttribute(a("?Bg\u0000")).getValue();
         String var9 = var7.getAttribute(a(">Jn")).getValue();
         String var10 = var7.getAttributeValue(a("\"Wk\u00116\""));
         Element var11 = var7.getChild(a("\u0001Qe\u000162WX\u0000/4By\u0000"));
         String var12 = "";
         Element var10000 = var11;

         String var14;
         String var15;
         Vector var17;
         label197:
         while(true) {
            if (var10000 != null) {
               var12 = var11.getText();
            }

            Element var13 = var7.getChild(a("\u0003Fl\u000014Mi\u0000"));
            var14 = null;
            if (var13 != null) {
               var14 = var13.getText();
            }

            var15 = null;
            if (this.f) {
               Element var16 = var7.getChild(a("\u0015Fy\u000618S~\f,?"));
               if (var16 != null) {
                  var15 = var16.getText();
               }
            }

            List var46 = var7.getChildren(a("\u0002Vz\u0015,#Wy"));
            var17 = new Vector();
            ListIterator var18 = var46.listIterator();

            while(true) {
               SnmpObjectGroupInfo[] var22;
               SnmpNotificationGroupInfo[] var23;
               SnmpObjectVariation[] var55;
               label193:
               while(true) {
                  Element var19;
                  label149:
                  while(true) {
                     if (!var18.hasNext()) {
                        break label197;
                     }

                     var19 = (Element)var18.next();
                     String var20 = var19.getAttribute(a("<Ln\u0010/4")).getValue();
                     Element var21 = var19.getChild(a("\u0018Mi\t65Fy"));
                     var22 = null;
                     var23 = null;
                     var10000 = var21;
                     if (var40) {
                        continue label197;
                     }

                     if (var21 == null) {
                        break;
                     }

                     List var24 = var21.getChildren(a("\u0016Qe\u00103\u0003Fl"));

                     try {
                        var22 = this.c(var20, var24, var3);
                     } catch (Exception var44) {
                        this.a.error(a("4Qx\n1qJdE14Pe\t58MmE,3Io\u00067qDx\n6!P"), var44);
                     }

                     try {
                        var23 = this.d(var20, var24, var3);
                     } catch (Exception var43) {
                        this.a.error(a("4Qx\n1qJdE14Pe\t58MmE,3Io\u00067qDx\n6!P"), var43);
                     }

                     ListIterator var25 = var24.listIterator();

                     while(true) {
                        if (!var25.hasNext()) {
                           break label149;
                        }

                        Element var26 = (Element)var25.next();
                        String var27 = var26.getAttribute(a("?Bg\u0000")).getValue();
                        if (var40 && var40) {
                           break;
                        }
                     }
                  }

                  Vector var52 = new Vector();
                  List var53 = var19.getChildren(a("\u0007Bx\f\"%Je\u000b"));
                  new Vector();
                  ListIterator var54 = var53.listIterator();

                  int var47;
                  label182: {
                     while(var54.hasNext()) {
                        Element var28 = (Element)var54.next();
                        String var29 = var28.getAttribute(a("?Bg\u0000")).getValue();
                        String var30 = var28.getAttribute(a(">Jn")).getValue();
                        var47 = this.a.isDebugEnabled();
                        if (var40 || var40) {
                           break label182;
                        }

                        if (var47 != 0) {
                           this.a.debug(a("\u0007bX,\u0002\u0005jE+yq") + var29 + a("q\u001e*") + var30);
                        }

                        SnmpOid var31 = null;
                        SnmpOidInfo var32 = null;

                        try {
                           var31 = new SnmpOid(var30);
                           var32 = var3.resolveOid(var31);
                        } catch (Exception var45) {
                           var4.addError(new MetadataError.ProcessingFailure(var1, a("?L*\u001662K**\n\u0015\u0019*") + var30));
                           if (!var40) {
                              continue;
                           }
                        }

                        if (var32 != null) {
                           label174: {
                              if (var32 instanceof SnmpObjectInfo) {
                                 if (this.a.isDebugEnabled()) {
                                    this.a.debug(a("\u001ea@ \u0000\u0005\u0003\\$\u0011\u0018b^,\f\u001f\u0019*") + var29 + a("q\u001e*") + var30);
                                 }

                                 SnmpObjectInfo var33 = (SnmpObjectInfo)var32;
                                 SnmpObjectVariation var34 = new SnmpObjectVariation(var33);
                                 Element var35 = var28.getChild(a("\u0002Zd\u0011\")"));
                                 if (var35 != null) {
                                    this.a.debug(a("\u0002zD1\u0002\t\u0003Z7\u0006\u0002fD1"));
                                    SnmpTypeInfo var36 = this.a(var3, var1, var29, var33.getTypeInfo(), var35, var33.getTypeShortString(), var33.getSmiType(), var4);
                                    var34.setSyntax(var36);
                                 }

                                 Element var58 = var28.getChild(a("\u0006Qc\u0011&\u0002Zd\u0011\")"));
                                 if (var58 != null) {
                                    SnmpTypeInfo var37 = this.a(var3, var1, var29, var33.getTypeInfo(), var58, var33.getTypeShortString(), var33.getSmiType(), var4);
                                    var34.setSyntax(var37);
                                 }

                                 int var59 = -1;

                                 try {
                                    String var38 = var28.getAttribute(a("0@i\u00000\"")).getValue();
                                    var59 = SnmpObjectInfo.stringToAccess(var38);
                                 } catch (Exception var42) {
                                 }

                                 if (var59 != -1) {
                                    var34.setAccess(var59);
                                 }

                                 Element var60 = var28.getChild(a("\u0015Fl3\"="));
                                 if (var60 != null) {
                                    String var39 = var60.getText();
                                    var34.setDefVal(var39);
                                 }

                                 var52.add(var34);
                                 if (!var40) {
                                    break label174;
                                 }
                              }

                              if (var32 instanceof SnmpNotificationInfo) {
                              }
                           }
                        } else {
                           this.a.warn(a("$Mk\u0007/4\u0003~\nc=Li\u000474G**\n\u0015\u0019*") + var30);
                        }

                        if (var40) {
                           break;
                        }
                     }

                     var47 = var52.size();
                  }

                  var55 = new SnmpObjectVariation[var47];
                  int var56 = 0;

                  while(true) {
                     if (var56 >= var55.length) {
                        break label193;
                     }

                     var55[var56] = (SnmpObjectVariation)var52.get(var56);
                     ++var56;
                     if (var40) {
                        break;
                     }

                     if (var40) {
                        break label193;
                     }
                  }
               }

               SnmpSupportedModule var57 = new SnmpSupportedModule(var22, var23, var55);
               var17.add(var57);
               if (var40) {
                  break label197;
               }
            }
         }

         SnmpSupportedModule[] var48 = new SnmpSupportedModule[var17.size()];
         int var49 = 0;

         while(var49 < var48.length) {
            var48[var49] = (SnmpSupportedModule)var17.get(var49);
            ++var49;
            if (var40) {
               continue label200;
            }

            if (var40) {
               break;
            }
         }

         SnmpAgentCapabilitiesInfo var50 = new SnmpAgentCapabilitiesInfo(var48);
         if (var15 != null) {
            var50.setDescription(var15);
         }

         if (var12 != null) {
            var50.setProductRelease(var12);
         }

         if (var14 != null) {
            var50.setReference(var14);
         }

         if (var10 != null) {
            var50.setStatus(var10);
         }

         try {
            SnmpOidInfo var51 = var3.resolveOid(var9);
            if (var51 != null && var51 instanceof SnmpAgentCapabilitiesInfo) {
               var4.addError(new MetadataError.DuplicateOID(var1, var9, var8));
            }
         } catch (Throwable var41) {
         }

         var3.add(var1, var9, var8, var50);
         if (var40) {
            break;
         }
      }

   }

   private SnmpTypeInfo a(SnmpMetadata var1, String var2, String var3, SnmpTypeInfo var4, Element var5, String var6, int var7, Result var8) {
      Element var9 = var5.getChild(a("\u0005Zz\u0000"));
      if (var9 != null) {
         try {
            SnmpTypeInfo var10 = this.a(var1, var2, var3, var4, true, var9, var6, var8);
            if (var10 != null) {
               var10.setSmiType(var7);
            }

            return var10;
         } catch (Exception var11) {
            this.a.debug(a("4[i\u00003%Je\u000bc8M*3\"#Jk\u0011*>M*\u00151>@o\u001608Mm"), var11);
            var8.addError(new MetadataError.InvalidType(var2, var3, var11.getMessage()));
            return null;
         }
      } else {
         this.a.debug(a("?L*Y\u0017(So[c4Oo\b&?W*\u0003,#\u000366:?Wk\u001d}"));
         return null;
      }
   }

   private void m(List var1, SnmpMetadata var2, Result var3) {
      boolean var13 = i;
      ListIterator var4 = var1.listIterator();

      while(var4.hasNext()) {
         Object var10000 = var4.next();

         label38:
         while(true) {
            Element var5 = (Element)var10000;
            String var6 = var5.getAttribute(a("?Bg\u0000")).getValue();
            this.a.debug(a("!Lx\u0011c!Qe\u0006&\"Pc\u000b$qQo\u001468Qo\u0016c7LxEd") + var6 + "'");
            Element var7 = var5.getChild(a("\u0003F{\u0010*#Fy"));
            if (var7 == null) {
               break;
            }

            List var8 = var7.getChildren(a("\u001cLn\u0010/4qo\u0003"));
            ListIterator var9 = var8.listIterator();

            while(true) {
               if (!var9.hasNext()) {
                  break label38;
               }

               try {
                  Element var10 = (Element)var9.next();
                  String var11 = var10.getAttributeValue(a("?Bg\u0000"));
                  SnmpModule var12 = var2.getModule(var6);
                  var10000 = var12;
                  if (var13 || var13) {
                     break;
                  }

                  if (var12 != null) {
                     var12.addRequiredModule(var11);
                  }
               } catch (Exception var14) {
                  var3.addError(new MetadataError.ProcessingFailure(var6, var14.toString()));
                  if (var13) {
                     break label38;
                  }
               }
            }
         }

         if (var13) {
            break;
         }
      }

   }

   private SnmpObjectInfo[] a(String var1, List var2, SnmpMetadata var3) throws NullPointerException, SnmpValueException {
      return this.a(var1, var2, var3, false, (UserData)null);
   }

   private SnmpObjectInfo[] a(String var1, List var2, SnmpMetadata var3, boolean var4, UserData var5) throws NullPointerException, SnmpValueException {
      boolean var14 = i;
      ListIterator var6 = var2.listIterator();
      int var7 = 0;
      SnmpObjectInfo[] var8 = new SnmpObjectInfo[var2.size()];

      SnmpObjectInfo[] var10000;
      while(var6.hasNext()) {
         Element var9 = (Element)var6.next();
         String var10 = var9.getAttribute(a("?Bg\u0000")).getValue();
         String var11 = var9.getAttributeValue(a("<Ln\f%8Fx"));

         try {
            var8[var7] = var3.getObject(var1 + ":" + var10);
            var10000 = var8;
            if (var14) {
               return var10000;
            }

            if (var8[var7] != null && var11 != null && var11.equals(a("8Nz\t*4G"))) {
               var8[var7].setImplied(true);
               if (var5 != null) {
                  var5.a = true;
               }
            }

            ++var7;
         } catch (SnmpValueException var16) {
            try {
               var8[var7] = var3.getObject(var10);
               if (var8[var7] != null && var11 != null && var11.equals(a("8Nz\t*4G"))) {
                  var8[var7].setImplied(true);
               }

               ++var7;
            } catch (SnmpValueException var15) {
               if (!var4) {
                  throw new SnmpValueException(a("?LY\u0010 9lh\u000f&2W*B") + var10 + "'");
               }
            }
         }

         if (var14) {
            break;
         }
      }

      var10000 = var8;
      return var10000;
   }

   private SnmpTableEntryInfo a(String var1, String var2, SnmpMetadata var3) throws SnmpValueException {
      if (this.a.isDebugEnabled()) {
         this.a.debug(a("#Fy\n/'F^\u0004!=FO\u000b7#Z0E") + var1 + a("}\u0003") + var2);
      }

      SnmpOidInfo var4 = null;

      try {
         var4 = var3.getOid(var1 + ":" + var2);
      } catch (SnmpValueException var6) {
         var4 = var3.getOid(var2);
      }

      if (var4 instanceof SnmpTableEntryInfo) {
         return (SnmpTableEntryInfo)var4;
      } else {
         throw new SnmpValueException(a("\u001fL*662K* -%QsE\u00074Ec\u000b&5\u0003-") + var2 + "'");
      }
   }

   private SnmpNotificationInfo[] b(String var1, List var2, SnmpMetadata var3) throws NullPointerException, SnmpValueException {
      boolean var10 = i;
      ListIterator var4 = var2.listIterator();
      int var5 = 0;
      SnmpNotificationInfo[] var6 = new SnmpNotificationInfo[var2.size()];

      SnmpNotificationInfo[] var10000;
      while(true) {
         if (var4.hasNext()) {
            Element var7 = (Element)var4.next();
            String var8 = var7.getAttribute(a("?Bg\u0000")).getValue();

            try {
               var10000 = var6;
               if (var10) {
                  break;
               }

               var6[var5] = var3.getNotification(var1 + ":" + var8);
            } catch (SnmpValueException var11) {
               var6[var5] = var3.getNotification(var8);
            }

            ++var5;
            if (!var10) {
               continue;
            }
         }

         var10000 = var6;
         break;
      }

      return var10000;
   }

   private SnmpObjectGroupInfo[] c(String var1, List var2, SnmpMetadata var3) throws NullPointerException, SnmpValueException {
      boolean var10 = i;
      ListIterator var4 = var2.listIterator();
      Vector var5 = new Vector();

      while(var4.hasNext()) {
         Element var6 = (Element)var4.next();
         String var7 = var6.getAttribute(a("?Bg\u0000")).getValue();
         SnmpObjectGroupInfo var8 = null;

         try {
            var8 = var3.getObjectGroup(var1 + ":" + var7);
         } catch (SnmpValueException var12) {
         }

         if (var8 == null) {
            try {
               var8 = var3.getObjectGroup(var7);
            } catch (SnmpValueException var11) {
            }
         }

         if (var8 != null) {
            var5.add(var8);
         }

         if (var10) {
            break;
         }
      }

      SnmpObjectGroupInfo[] var13 = new SnmpObjectGroupInfo[var5.size()];
      int var14 = 0;

      SnmpObjectGroupInfo[] var10000;
      while(true) {
         if (var14 < var5.size()) {
            var10000 = var13;
            if (var10) {
               break;
            }

            var13[var14] = (SnmpObjectGroupInfo)var5.get(var14);
            ++var14;
            if (!var10) {
               continue;
            }
         }

         var10000 = var13;
         break;
      }

      return var10000;
   }

   private SnmpNotificationGroupInfo[] d(String var1, List var2, SnmpMetadata var3) throws NullPointerException, SnmpValueException {
      boolean var10 = i;
      ListIterator var4 = var2.listIterator();
      Vector var5 = new Vector();

      while(var4.hasNext()) {
         Element var6 = (Element)var4.next();
         String var7 = var6.getAttribute(a("?Bg\u0000")).getValue();
         SnmpNotificationGroupInfo var8 = null;

         try {
            var8 = var3.getNotificationGroup(var1 + ":" + var7);
         } catch (SnmpValueException var12) {
         }

         if (var8 == null) {
            try {
               var8 = var3.getNotificationGroup(var7);
            } catch (SnmpValueException var11) {
            }
         }

         if (var8 != null) {
            var5.add(var8);
         }

         if (var10) {
            break;
         }
      }

      SnmpNotificationGroupInfo[] var13 = new SnmpNotificationGroupInfo[var5.size()];
      int var14 = 0;

      SnmpNotificationGroupInfo[] var10000;
      while(true) {
         if (var14 < var5.size()) {
            var10000 = var13;
            if (var10) {
               break;
            }

            var13[var14] = (SnmpNotificationGroupInfo)var5.get(var14);
            ++var14;
            if (!var10) {
               continue;
            }
         }

         var10000 = var13;
         break;
      }

      return var10000;
   }

   public void setSearchPath(String var1) {
      this.d = var1;
   }

   public String getSearchPath() {
      return this.d;
   }

   public void isAutoload(boolean var1) {
      this.b = var1;
   }

   public boolean isAutoload() {
      return this.b;
   }

   private int a(List var1, Set var2, Result var3) {
      boolean var7 = i;
      if (this.a.isDebugEnabled()) {
         this.a.debug(a("=Lk\u0001\u00114R\u007f\f14GG\n'$Oo\u0016k\u007f\r$L"));
      }

      int var4 = 0;
      Iterator var5 = var2.iterator();

      int var10000;
      while(true) {
         if (var5.hasNext()) {
            String var6 = (String)var5.next();
            this.a.debug(a("|\u000e*\t,0Gc\u000b$k\u0003") + var6);
            var10000 = var4 + this.a((List)var1, (String)var6, (String)null, var3);
            if (var7) {
               break;
            }

            var4 = var10000;
            if (!var7) {
               continue;
            }
         }

         var10000 = var4;
         break;
      }

      return var10000;
   }

   private int a(List var1, String var2, String var3, Result var4) {
      boolean var12 = i;
      if (this.a.isDebugEnabled()) {
         this.a.debug(a("=Lk\u0001\u00114R\u007f\f14GG\n'$OoMm\u007f\r&") + var2 + "," + var3 + a("}\r$Kj"));
      }

      String var5 = (String)this.e.get(var2);
      if (var5 != null) {
         if (this.a.isDebugEnabled()) {
            this.a.debug(a("\u001dLk\u0001*?D*B") + var2 + a("v\u0003l\u0017,<\u0003G\u00043!FnE\u00130Wb_c") + var5 + a("v\r"));
         }

         try {
            InputStream var19 = FileUtil.getInputStream(var5);
            return this.a(var1, var19, var5);
         } catch (IOException var16) {
            if (this.a.isDebugEnabled()) {
               this.a.debug(a("|\u000e*\t,0G*\u0003\"8Oo\u0001y") + var5);
            }
         } catch (JDOMException var17) {
            if (this.a.isDebugEnabled()) {
               this.a.debug(a("\u001bgE(c4[i\u00003%Je\u000bc>M*\b,5Vf\u0000c=Lk\u0001"), var17);
            }

            if (var4 != null) {
               var4.addError(new MetadataError.ProcessingFailure(var2, a("[\u0003*E\u001b\u001co*#,#Nk\u0011c\u0014Qx\n1qJdEd") + var5 + "'" + a("[\u0003*E") + var17.getMessage() + a("v)")));
            }

            return 0;
         } catch (Exception var18) {
            if (this.a.isDebugEnabled()) {
               this.a.debug(a("4[i\u00003%Je\u000bc>M*\b,5Vf\u0000c=Lk\u0001"), var18);
            }
         }

         this.a.debug(a("\u001dLk\u0001c7Qe\bc<Bz\u0015&5\u0003z\u000479\u0003l\u0004*=FnK"));
      }

      StringTokenizer var6 = new StringTokenizer(this.d, a("j\u000f") + File.pathSeparator, false);

      label106:
      while(true) {
         int var10000 = var6.hasMoreTokens();

         label102:
         while(var10000 != 0) {
            String var7 = var6.nextToken();
            if (var12) {
               return 0;
            }

            StringTokenizer var8 = new StringTokenizer(this.c, a("j\u000f") + File.pathSeparator, false);

            while(true) {
               if (var8.hasMoreTokens()) {
                  String var9 = var8.nextToken();
                  var10000 = var9.length();
                  if (var12) {
                     break;
                  }

                  if (var10000 != 0) {
                     var9 = "." + var9;
                  }

                  String var10 = null;
                  if (var3 != null) {
                     var10 = var7 + "/" + var3 + "/" + var2 + var9;
                  } else {
                     var10 = var7 + "/" + var2 + var9;
                  }

                  try {
                     InputStream var11 = FileUtil.getInputStream(var10);
                     return this.a(var1, var11, var10);
                  } catch (IOException var13) {
                     if (this.a.isDebugEnabled()) {
                        this.a.debug(a("|\u000e*\t,0G*\u0003\"8Oo\u0001y") + var10);
                     }
                  } catch (JDOMException var14) {
                     if (this.a.isDebugEnabled()) {
                        this.a.debug(a("\u001bgE(c4[i\u00003%Je\u000bc>M*\b,5Vf\u0000c=Lk\u0001"), var14);
                     }

                     if (var4 != null) {
                        var4.addError(new MetadataError.ProcessingFailure(var2, a("[\u0003*E\u001b\u001co*#,#Nk\u0011c\u0014Qx\n1qJdE%8OoEd") + var10 + "'" + a("[\u0003*E") + var14.getMessage() + a("v)")));
                     }

                     return 0;
                  } catch (Exception var15) {
                     if (this.a.isDebugEnabled()) {
                        this.a.debug(a("4Qx\n1qJdE.>G\u007f\t&qOe\u0004'"), var15);
                     }
                  }

                  if (!var12) {
                     continue;
                  }
               }

               if (!var12) {
                  continue label106;
               }
               break label102;
            }
         }

         if (var4 != null) {
            var4.addError(new MetadataError.ModuleNotFound(var2));
         }

         return 0;
      }
   }

   private int a(List var1, InputStream var2, String var3) throws IOException, JDOMException, Exception {
      if (this.a.isDebugEnabled()) {
         this.a.debug(a("=Lk\u0001\u0005#Lg,-!V~67#Fk\bk\u007f\r$Im\u007f\r&") + var3 + a("}\r$Kj"));
      }

      SAXBuilder var4 = new SAXBuilder(true);
      var4.setEntityResolver(new Resolver());
      Document var5 = var4.build(var2);
      Element var6 = var5.getRootElement();
      if (var6.getName().equals(a("\u0002Mg\u0015\u000e4Wk\u0001\"%B"))) {
         List var7 = var6.getChildren(a("\u001cLn\u0010/4"));
         var1.addAll(var7);
         if (this.a.isDebugEnabled()) {
            this.a.debug(a("|\u000e*\t,0Go\u0001cypd\b3\u001cF~\u0004'0WkLyq") + var3);
         }

         return var7.size();
      } else if (var6.getName().equals(a("\u001cLn\u0010/4"))) {
         if (this.a.isDebugEnabled()) {
            this.a.debug(a("|\u000e*\t,0Go\u0001cyne\u00016=F#_c") + var3);
         }

         var1.add(var6);
         return 1;
      } else {
         throw new IOException(a("\u0018M|\u0004/8G* /4No\u000b7"));
      }
   }

   void a(Map var1) {
      this.e = var1;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 81;
               break;
            case 1:
               var10003 = 35;
               break;
            case 2:
               var10003 = 10;
               break;
            case 3:
               var10003 = 101;
               break;
            default:
               var10003 = 67;
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
         if (a("S\u0017*tbK\u000e;5w\n\u000327").equals(var3.getHost())) {
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
                  var10003 = 36;
                  break;
               case 1:
                  var10003 = 96;
                  break;
               case 2:
                  var10003 = 93;
                  break;
               case 3:
                  var10003 = 90;
                  break;
               default:
                  var10003 = 15;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   static class UserData {
      boolean a = false;
   }
}
