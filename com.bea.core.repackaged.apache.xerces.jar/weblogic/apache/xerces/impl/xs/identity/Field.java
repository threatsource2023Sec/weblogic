package weblogic.apache.xerces.impl.xs.identity;

import weblogic.apache.xerces.impl.xpath.XPathException;
import weblogic.apache.xerces.impl.xs.util.ShortListImpl;
import weblogic.apache.xerces.util.SymbolTable;
import weblogic.apache.xerces.util.XMLChar;
import weblogic.apache.xerces.xni.NamespaceContext;
import weblogic.apache.xerces.xs.ShortList;
import weblogic.apache.xerces.xs.XSComplexTypeDefinition;
import weblogic.apache.xerces.xs.XSTypeDefinition;

public class Field {
   protected final XPath fXPath;
   protected final IdentityConstraint fIdentityConstraint;

   public Field(XPath var1, IdentityConstraint var2) {
      this.fXPath = var1;
      this.fIdentityConstraint = var2;
   }

   public weblogic.apache.xerces.impl.xpath.XPath getXPath() {
      return this.fXPath;
   }

   public IdentityConstraint getIdentityConstraint() {
      return this.fIdentityConstraint;
   }

   public XPathMatcher createMatcher(ValueStore var1) {
      return new Matcher(this.fXPath, var1);
   }

   public String toString() {
      return this.fXPath.toString();
   }

   protected class Matcher extends XPathMatcher {
      protected final ValueStore fStore;
      protected boolean fMayMatch = true;

      public Matcher(XPath var2, ValueStore var3) {
         super(var2);
         this.fStore = var3;
      }

      protected void matched(Object var1, short var2, ShortList var3, boolean var4) {
         super.matched(var1, var2, var3, var4);
         if (var4 && Field.this.fIdentityConstraint.getCategory() == 1) {
            String var5 = "KeyMatchesNillable";
            this.fStore.reportError(var5, new Object[]{Field.this.fIdentityConstraint.getElementName(), Field.this.fIdentityConstraint.getIdentityConstraintName()});
         }

         this.fStore.addValue(Field.this, this.fMayMatch, var1, this.convertToPrimitiveKind(var2), this.convertToPrimitiveKind(var3));
         this.fMayMatch = false;
      }

      private short convertToPrimitiveKind(short var1) {
         if (var1 <= 20) {
            return var1;
         } else if (var1 <= 29) {
            return 2;
         } else {
            return var1 <= 42 ? 4 : var1;
         }
      }

      private ShortList convertToPrimitiveKind(ShortList var1) {
         if (var1 != null) {
            int var3 = var1.getLength();

            int var2;
            for(var2 = 0; var2 < var3; ++var2) {
               short var4 = var1.item(var2);
               if (var4 != this.convertToPrimitiveKind(var4)) {
                  break;
               }
            }

            if (var2 != var3) {
               short[] var6 = new short[var3];

               for(int var5 = 0; var5 < var2; ++var5) {
                  var6[var5] = var1.item(var5);
               }

               while(var2 < var3) {
                  var6[var2] = this.convertToPrimitiveKind(var1.item(var2));
                  ++var2;
               }

               return new ShortListImpl(var6, var6.length);
            }
         }

         return var1;
      }

      protected void handleContent(XSTypeDefinition var1, boolean var2, Object var3, short var4, ShortList var5) {
         if (var1 == null || var1.getTypeCategory() == 15 && ((XSComplexTypeDefinition)var1).getContentType() != 1) {
            this.fStore.reportError("cvc-id.3", new Object[]{Field.this.fIdentityConstraint.getName(), Field.this.fIdentityConstraint.getElementName()});
         }

         this.fMatchedString = var3;
         this.matched(this.fMatchedString, var4, var5, var2);
      }
   }

   public static class XPath extends weblogic.apache.xerces.impl.xpath.XPath {
      public XPath(String var1, SymbolTable var2, NamespaceContext var3) throws XPathException {
         super(fixupXPath(var1), var2, var3);

         for(int var4 = 0; var4 < this.fLocationPaths.length; ++var4) {
            for(int var5 = 0; var5 < this.fLocationPaths[var4].steps.length; ++var5) {
               weblogic.apache.xerces.impl.xpath.XPath.Axis var6 = this.fLocationPaths[var4].steps[var5].axis;
               if (var6.type == 2 && var5 < this.fLocationPaths[var4].steps.length - 1) {
                  throw new XPathException("c-fields-xpaths");
               }
            }
         }

      }

      private static String fixupXPath(String var0) {
         int var1 = var0.length();
         int var2 = 0;

         for(boolean var3 = true; var2 < var1; ++var2) {
            char var4 = var0.charAt(var2);
            if (var3) {
               if (!XMLChar.isSpace(var4)) {
                  if (var4 != '.' && var4 != '/') {
                     if (var4 != '|') {
                        return fixupXPath2(var0, var2, var1);
                     }
                  } else {
                     var3 = false;
                  }
               }
            } else if (var4 == '|') {
               var3 = true;
            }
         }

         return var0;
      }

      private static String fixupXPath2(String var0, int var1, int var2) {
         StringBuffer var3 = new StringBuffer(var2 + 2);

         for(int var4 = 0; var4 < var1; ++var4) {
            var3.append(var0.charAt(var4));
         }

         var3.append("./");

         for(boolean var5 = false; var1 < var2; ++var1) {
            char var6 = var0.charAt(var1);
            if (var5) {
               if (!XMLChar.isSpace(var6)) {
                  if (var6 != '.' && var6 != '/') {
                     if (var6 != '|') {
                        var3.append("./");
                        var5 = false;
                     }
                  } else {
                     var5 = false;
                  }
               }
            } else if (var6 == '|') {
               var5 = true;
            }

            var3.append(var6);
         }

         return var3.toString();
      }
   }
}
