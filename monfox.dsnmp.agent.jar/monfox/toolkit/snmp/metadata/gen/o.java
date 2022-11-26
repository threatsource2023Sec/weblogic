package monfox.toolkit.snmp.metadata.gen;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import monfox.jdom.Element;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.util.OidTree;

class o extends n {
   private OidTree a;
   public Vector typeRefs;
   public Hashtable definedTypes;
   public Properties moduleAliases;
   public Hashtable oidToNameMap;
   public Hashtable oidToElementMap;
   public Hashtable nameToOidMap;
   public Hashtable nameToRefMap;
   public Hashtable flatNameToElementMap;
   public Hashtable nameToElementMap;
   public Properties optimizationTable;
   public Element metadata;
   private static String[] b = new String[]{b("3RSD\u001f\u001bPSJ\u001f\u0014["), b("(YI_\u001a\fUTWV,]VE\u0013\t"), b("(YI_\u001a\fUTWV.EJU\u0005"), b("(YI_\u001a\fUTWV5^PU\u0015\u000eO"), b("=YTU\u0004\u001bHS^\u0011Zq_D\u0017\u001e]NQ"), b("=YTU\u0004\u001bHS^\u0011ZsstV\u000eN_U"), b("5LNY\u001b\u0013FS^\u0011")};
   private Hashtable j;
   private Hashtable c;
   private boolean d;
   static Properties e = null;
   static Properties f = null;
   static Properties g = null;
   private static final String o = "$Id: SnmpValidationCoordinator.java,v 1.25 2003/07/16 23:40:02 sking Exp $";
   // $FF: synthetic field
   static Class h;

   o(Properties var1, SnmpMibGen var2) {
      int var3 = Message.d;
      super(b, b("\u0017STV\u0019\u0002\u0012N_\u0019\u0016WSDX\tRW@X\u0017YNQ\u0012\u001bH[\u001e\u0011\u001fR"), h == null ? (h = c(b("\u0017STV\u0019\u0002\u0012N_\u0019\u0016WSDX\tRW@X\u0017YNQ\u0012\u001bH[\u001e\u0011\u001fR\u0014A"))) : h, 10, var2);
      this.a = new OidTree();
      this.typeRefs = new Vector();
      this.definedTypes = new Hashtable();
      this.moduleAliases = new Properties();
      this.oidToNameMap = new Hashtable();
      this.oidToElementMap = new Hashtable();
      this.nameToOidMap = new Hashtable();
      this.nameToRefMap = new Hashtable();
      this.flatNameToElementMap = new Hashtable();
      this.nameToElementMap = new Hashtable();
      this.optimizationTable = new Properties();
      this.metadata = new Element(b(")RW@;\u001fH[T\u0017\u000e]"));
      this.j = new Hashtable();
      this.c = new Hashtable();
      this.d = true;
      this.optimizationTable = var1;
      this.a(b(">SYE\u001b\u001fRN"), new s());
      this.a(b("4sny03\u007f{d?5r"), new bb());
      this.a(b("4sny03\u007f{d?5rew$5ij"), new ba());
      this.a(b("5~pu5."), new bd());
      this.a(b("5~pu5.c}b9/l"), new bc());
      this.a(b(".}x|3"), new bg());
      this.a(b(".}x|3%ytd$#"), new be());
      this.a(b(".n{`"), new bh());
      this.a(b(".ybd#;pes94j\u007f~\"3st"), new bj());
      this.a(b("7s~e:?cst34hsd/"), new y());
      this.a(b("<nu}"), new u());
      this.a(b(".EJU"), new bi());
      this.a(b(",]VE\u0013"), new bk());
      this.a(b("\u0014R"), new bm());
      this.a(b(";{\u007f~\"%\u007f{`78uvy\"3yi"), new r());
      this.a(b(")ij`9(hi"), new bf());
      this.a(b(",}hy7.uu~"), new bl());
      this.a(b("7s~e:?"), new z());
      this.a(b("7s~e:?cy\u007f;*psq89y"), new x());
      this.a(b("=nue&"), new v());
      this.a(b("?dyu&.uu~"), new t());
      this.a(b("\u0013H_]"), new w());
      if (var3 != 0) {
         SnmpException.b = !SnmpException.b;
      }

   }

   public String addOID(String var1, String var2, Element var3) {
      try {
         OidTree.Node var4 = this.a.add((String)var1, var2, var3);
         return var4 != null ? var4.toOidString() : null;
      } catch (Exception var5) {
         return null;
      }
   }

   public void addRef(String var1, String var2, Element var3) {
      int var10 = Message.d;

      try {
         String var4 = bo.getParentValue(var3, b("7S^E\u001a\u001f"), b("\u0014]WU"));
         String var5 = var4 + ":" + var1;
         Element var6 = (Element)this.nameToElementMap.get(var5);
         if (var6 != null) {
            try {
               String var13 = bo.getParentValue(var6, b("7S^E\u001a\u001f"), b("\u0014]WU"));
               String var8 = var13 + ":" + var1;
               String var9 = var1 + "X";

               while(true) {
                  if (this.nameToElementMap.get(var4 + ":" + var9) != null) {
                     var9 = var9 + "X";
                     if (var10 != 0) {
                        break;
                     }

                     if (var10 == 0) {
                        continue;
                     }
                  }

                  this.a(new ErrorMessage.DuplicateName(var4, var5, var8, var9));
                  var3.getAttribute(b("\u0014]WU")).setValue(var9);
                  this.addRef(var9, var2, var3);
                  break;
               }

               return;
            } catch (Exception var11) {
               var11.printStackTrace();
               System.out.println(var11);
            }
         }

         char var7 = var2.charAt(0);
         if (Character.isLowerCase(var7)) {
            var2 = var4 + ":" + var2;
         }

         this.nameToRefMap.put(var5, var2);
         this.nameToElementMap.put(var5, var3);
         this.flatNameToElementMap.put(var1, var3);
      } catch (Exception var12) {
         var12.printStackTrace();
      }

   }

   public void addImport(String var1, String var2, Element var3) {
      try {
         String var4 = bo.getParentValue(var3, b("7S^E\u001a\u001f"), b("\u0014]WU"));
         String var5 = var4 + ":" + var1;
         Element var6 = (Element)this.nameToElementMap.get(var1);
         if (var6 != null) {
            try {
               String var7 = bo.getParentValue(var6, b("7S^E\u001a\u001f"), b("\u0014]WU"));
               String var8 = var7 + "." + var1;
               this.a(new ErrorMessage.DuplicateName(var4, var5, var8, (String)null));
            } catch (Exception var9) {
               this._log.error(b("\u001bX^y\u001b\nSHD^") + var1 + "," + var2 + "," + var3 + ")", var9);
            }
         }

         char var11 = var1.charAt(0);
         if (Character.isLowerCase(var11)) {
            this._log.debug(b(";X^Y\u0018\u001d\u001cs]\u0006\u0015NN\nV") + var5 + b("G\u0002") + var2 + ":" + var1);
            this.nameToRefMap.put(var5, var2 + ":" + var1);
            this.nameToElementMap.put(var5, var3);
            if (Message.d == 0) {
               return;
            }
         }

         this._log.debug(b(";X^Y\u0018\u001d\u001cs]\u0006\u0015NN\nV") + var5 + b("G\u0002") + var2 + ":" + var1);
         this.definedTypes.put(var5, var3);
      } catch (Exception var10) {
         var10.printStackTrace();
      }

   }

   public void addMetadata(String var1, Element var2, Set var3) {
      int var13 = Message.d;
      Element var4 = (Element)this.j.get(var1);
      if (var4 == null) {
         var4 = new Element(b("7S^E\u001a\u001f"));
         var4.addAttribute(b("\u0014]WU"), var1);
         var4.addContent(new Element(b("(YKE\u001f\bYI")));
         this.metadata.addContent(var4);
         this.j.put(var1, var4);
      }

      var4.addContent(var2);
      if (var3 != null && var3.size() != 0) {
         Element var5 = var4.getChild(b("(YKE\u001f\bYI"));
         if (var5 == null) {
            var5 = new Element(b("(YKE\u001f\bYI"));
            var4.addContent(var5);
         }

         Iterator var6 = var3.iterator();

         while(var6.hasNext()) {
            String var7 = (String)var6.next();
            if (!var7.equals(var1)) {
               boolean var8 = false;
               List var9 = var5.getChildren();
               Iterator var10 = var9.iterator();

               Element var11;
               boolean var10000;
               while(true) {
                  if (var10.hasNext()) {
                     label58: {
                        var11 = (Element)var10.next();
                        String var12 = var11.getAttributeValue(b("\u0014]WU"));
                        var10000 = var12.equals(var7);
                        if (var13 != 0) {
                           break;
                        }

                        if (var10000) {
                           var8 = true;
                           if (var13 == 0) {
                              break label58;
                           }
                        }

                        if (var13 == 0) {
                           continue;
                        }
                     }
                  }

                  var10000 = var8;
                  break;
               }

               if (!var10000) {
                  var11 = new Element(b("7S^E\u001a\u001fn_V"));
                  var11.addAttribute(b("\u0014]WU"), var7);
                  var5.addContent(var11);
               }
            }

            if (var13 != 0) {
               break;
            }
         }
      }

   }

   public void setAliases(String var1) {
      if (var1 != null) {
         try {
            FileInputStream var2 = new FileInputStream(var1);
            this.moduleAliases.load(var2);
            var2.close();
         } catch (IOException var3) {
            System.out.println(b("-}h~?4{\u0000\u00105\u001bRT_\u0002ZZS^\u0012Z}VY\u0017\tYI\u00100\u0013P_\nV]") + var1 + "'");
         }
      }

   }

   public void addMetadata(Element var1, Element var2, Set var3) {
      String var4 = bo.getParentValue(var1, b("7S^E\u001a\u001f"), b("\u0014]WU"));
      this.addMetadata(var4, var2, var3);
   }

   public boolean moduleExists(Element var1, String var2) {
      int var8 = Message.d;

      boolean var10000;
      try {
         Element var3 = bo.getParent(var1, b(">SYE\u001b\u001fRN"));
         List var4 = var3.getChildren(b("7S^E\u001a\u001f"));
         ListIterator var5 = var4.listIterator();

         while(var5.hasNext()) {
            Element var6 = (Element)var5.next();

            try {
               var10000 = var6.getAttributeValue(b("\u0014]WU")).equals(var2);
               if (var8 != 0) {
                  return var10000;
               }

               if (var10000) {
                  return true;
               }
            } catch (Exception var9) {
            }

            if (var8 != 0) {
               break;
            }
         }
      } catch (Exception var10) {
         this._log.debug(b("?NH_\u0004ZUT\u0010\u001b\u0015XO\\\u0013?DSC\u0002\t"), var10);
      }

      var10000 = false;
      return var10000;
   }

   // $FF: synthetic method
   static Class c(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw (new NoClassDefFoundError()).initCause(var2);
      }
   }

   static {
      f = new Properties();
      f.put(b("=]OW\u0013"), "g");
      f.put(b("=]OW\u0013I\u000e"), "g");
      f.put(b("/RIY\u0011\u0014Y^\u0003D"), "g");
      f.put(b("/RIY\u0011\u0014Y^"), "g");
      f.put(b("3rnu1?n"), "i");
      f.put(b("3RNU\u0011\u001fN"), "i");
      f.put(b("3RNU\u0011\u001fN\t\u0002"), "i");
      f.put(b("5~pu5.\u001cst34hsv??n"), "o");
      f.put(b("5L[A\u0003\u001f"), b("\u0015L"));
      f.put(b("9SO^\u0002\u001fN"), "c");
      f.put(b("9SO^\u0002\u001fN\t\u0002"), "c");
      f.put(b("9SO^\u0002\u001fN\f\u0004"), b("\u0019\n\u000e"));
      f.put(b(".UWU\"\u0013_QC"), "t");
      f.put(b("8unc"), "s");
      f.put(b("5\u007fnu\"Zonb?4{"), "s");
      f.put(b("3L{T\u0012\bYIC"), b("\u0013L"));
      f.put(b("4YNG\u0019\bW{T\u0012\bYIC"), b("\u0013L"));
      f.put(b(")yke34\u007f\u007f"), b("\u001fRNB\u000f"));
      g = new Properties();
      g.put(b("=]OW\u0013"), "g");
      g.put(b("=]OW\u0013I\u000e"), b("\u001d\u000f\b"));
      g.put(b("/RIY\u0011\u0014Y^\u0003D"), b("\u000f\u000f\b"));
      g.put(b("/RIY\u0011\u0014Y^"), "u");
      g.put(b("3rnu1?n"), "i");
      g.put(b("3RNU\u0011\u001fN"), "i");
      g.put(b("3RNU\u0011\u001fN\t\u0002"), b("\u0013\u000f\b"));
      g.put(b("5~pu5.\u001cst34hsv??n"), "o");
      g.put(b("5L[A\u0003\u001f"), b("\u0015L"));
      g.put(b("9SO^\u0002\u001fN"), "c");
      g.put(b("9SO^\u0002\u001fN\t\u0002"), b("\u0019\u000f\b"));
      g.put(b("9SO^\u0002\u001fN\f\u0004"), b("\u0019\n\u000e"));
      g.put(b(".UWU\"\u0013_QC"), "t");
      g.put(b("8unc"), "b");
      g.put(b("5\u007fnu\"Zonb?4{"), "s");
      g.put(b("3L{T\u0012\bYIC"), b("\u0013L"));
      g.put(b("4YNG\u0019\bW{T\u0012\bYIC"), b("\u0013L"));
      g.put(b(")yke34\u007f\u007f"), b("\u001fRNB\u000f"));
      e = new Properties();
      e.put(b("\bY[T[\u0015RVI"), b("\bS"));
      e.put(b("\rNSD\u0013WST\\\u000f"), b("\rS"));
      e.put(b("\bY[T[\rNSD\u0013"), b("\bK"));
      e.put(b("\u0014SN\u001d\u0017\u0019__C\u0005\u0013^VU"), b("\u0014]"));
      e.put(b("\bY[T[\u0019N_Q\u0002\u001f"), b("\b_"));
      e.put(b("\u001b_YU\u0005\tUX\\\u0013WZUB[\u0014SNY\u0010\u0003"), b("\u0014S"));
      e.put(b("\u0014SN\u001d\u001f\u0017LVU\u001b\u001fRNU\u0012"), b("\u0014U"));
   }

   private static String b(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 122;
               break;
            case 1:
               var10003 = 60;
               break;
            case 2:
               var10003 = 58;
               break;
            case 3:
               var10003 = 48;
               break;
            default:
               var10003 = 118;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
