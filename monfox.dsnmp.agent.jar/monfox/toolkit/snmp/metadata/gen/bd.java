package monfox.toolkit.snmp.metadata.gen;

import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import monfox.jdom.Attribute;
import monfox.jdom.Element;

class bd extends q {
   private static final String a = "$Id: OBJECTValidator.java,v 1.19 2003/07/16 23:40:02 sking Exp $";

   boolean f(n var1, Element var2) {
      o var3 = (o)var1;
      String var4 = null;

      String var6;
      String var7;
      try {
         var4 = var2.getAttributeValue(a("\u0010}gr"));
         String var5 = var2.getAttributeValue(a("\u0011un"));
         if (var5 != null && var4 != null) {
            label86: {
               var6 = (String)var3.oidToNameMap.get(var5);
               if (var6 != null && !var6.equals(var4)) {
                  var7 = bo.getParentValue(var2, a("3snbY\u001b"), a("\u0010}gr"));
                  var3.a(new ErrorMessage.DuplicateOID(var7, var5, var4, var6), var2);
                  if (Message.d == 0) {
                     break label86;
                  }
               }

               var3.oidToNameMap.put(var5, var4);
            }
         }
      } catch (Exception var14) {
      }

      Element var15 = var2.getChild(a("*ezr"));

      String var8;
      try {
         var4 = var2.getAttribute(a("\u0010}gr")).getValue();
         var6 = var15.getAttribute(a("\nezr")).getValue();
         if (var6 != null && var6.equals(a(",YL"))) {
            var7 = var15.getAttributeValue(a("\nezrG\u001bz"));
            if (var7 == null) {
               var7 = var4;
            }

            var8 = bo.getParentValue(var2, a("3snbY\u001b"), a("\u0010}gr"));
            var3.a(new ErrorMessage.UnresolvedType(var8, a("^<*B[\fyyxY\byn7A\u0007lo7\u0012") + var7 + "'"), var15);
         }

         var7 = (String)o.f.get(var6);
         var8 = (String)o.g.get(var6);
         if (var7 != null) {
            var2.addAttribute(a("\u0011~`cL\u000ey"), var7);
         }

         if (var8 != null) {
            var2.addAttribute(a("\rqccL\u000ey"), var8);
         }

         Element var9;
         try {
            var9 = var2.getChild(a("?_IRf-"));
            if (var9.getText() != null && var9.getText().length() > 0) {
               var2.addAttribute(a("\u001f\u007firF\r"), o.e.getProperty(var9.getText(), a("\u0010}")));
            }
         } catch (NullPointerException var12) {
         }

         try {
            var9 = var2.getChild(a("3]RHt=_ODf"));
            if (var9.getText() != null && var9.getText().length() > 0) {
               var2.addAttribute(a("\u001f\u007firF\r"), o.e.getProperty(var9.getText(), a("\u0010}")));
            }
         } catch (NullPointerException var11) {
         }

         try {
            String var17 = var2.getChild(a(":YLAt2")).getText().trim();
            if (var17.startsWith("{") && var17.endsWith("}")) {
               var17 = var17.substring(1, var17.length() - 1).trim();
            }

            var2.addAttribute(a("\u001aylaT\u0012"), var17);
         } catch (NullPointerException var10) {
         }

         if (var2.getAttribute(a("\u001f\u007firF\r")) == null) {
            var2.addAttribute(a("\u001f\u007firF\r"), a("\u0010}"));
         }
      } catch (Exception var13) {
         Element var16 = var15;
         if (var15 == null) {
            var16 = var2;
         }

         var8 = bo.getParentValue(var2, a("3snbY\u001b"), a("\u0010}gr"));
         var3.a(new ErrorMessage.UnresolvedObjectType(var8, var4), var16);
      }

      return true;
   }

   boolean g(n var1, Element var2) {
      int var13 = Message.d;
      o var3 = (o)var1;

      try {
         Element var4 = new Element(a("1~`rV\n"));
         String var5 = var2.getAttributeValue(a("\u0010}gr"));
         var4.addAttribute((Attribute)var2.getAttribute(a("\u0010}gr")).clone());
         var4.addAttribute((Attribute)var2.getAttribute(a("\u0011un")).clone());
         var4.addAttribute((Attribute)var2.getAttribute(a("\u0011~`cL\u000ey")).clone());
         var4.addAttribute((Attribute)var2.getAttribute(a("\rqccL\u000ey")).clone());
         var4.addAttribute((Attribute)var2.getAttribute(a("\u001f\u007firF\r")).clone());
         if (var2.getAttribute(a("\u001aylaT\u0012")) != null) {
            var4.addAttribute((Attribute)var2.getAttribute(a("\u001aylaT\u0012")).clone());
         }

         Element var6 = var2.getChild(a("*ezr"));
         HashSet var7 = null;
         if (var6 != null) {
            label62: {
               String var8 = var6.getAttributeValue(a("\nezrG\u001bz"));
               if (var8 != null) {
                  var4.addAttribute(a("\nezrG\u001bz"), var8);
                  if (var8.indexOf(58) > 0) {
                     var7 = new HashSet();
                     var7.add(var8.substring(0, var8.indexOf(58)));
                  }
               }

               List var9 = var6.getChildren();
               ListIterator var10 = var9.listIterator();

               while(var10.hasNext()) {
                  Element var11 = (Element)var10.next();
                  var4.addContent(cleanup((Element)var11.clone()));
                  if (var13 != 0) {
                     return true;
                  }

                  if (var13 != 0) {
                     break;
                  }
               }

               String var15 = var2.getAttributeValue(a("\u0011un"));
               var15 = var15.substring(0, var15.lastIndexOf(46));
               Element var12 = (Element)var3.oidToElementMap.get(var15);
               if (var12 == null) {
                  var4.addAttribute(a("\u0018sxz"), a("\r\u007fk{T\f"));
                  if (var13 == 0) {
                     break label62;
                  }
               }

               if (var12.getName().equals(a("*]H[p!YDCg'"))) {
                  var4.addAttribute(a("\u0018sxz"), a("\u001dsfbX\u0010"));
                  if (var13 == 0) {
                     break label62;
                  }
               }

               if (var12.getName().equals(a("*]H[p"))) {
                  var4.addAttribute(a("\u0018sxz"), a("\u001br~eL"));
                  if (var13 == 0) {
                     break label62;
                  }
               }

               var4.addAttribute(a("\u0018sxz"), a("\r\u007fk{T\f"));
            }
         }

         copyStatus(var3, var2, var4);
         copyDescription(var3, var2, var4);
         var3.addMetadata((Element)var2, var4, var7);
      } catch (NullPointerException var14) {
      }

      return true;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 126;
               break;
            case 1:
               var10003 = 28;
               break;
            case 2:
               var10003 = 10;
               break;
            case 3:
               var10003 = 23;
               break;
            default:
               var10003 = 53;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
