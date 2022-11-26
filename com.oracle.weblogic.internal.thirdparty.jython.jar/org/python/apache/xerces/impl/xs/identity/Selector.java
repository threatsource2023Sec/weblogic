package org.python.apache.xerces.impl.xs.identity;

import org.python.apache.xerces.impl.xpath.XPathException;
import org.python.apache.xerces.util.SymbolTable;
import org.python.apache.xerces.util.XMLChar;
import org.python.apache.xerces.xni.NamespaceContext;
import org.python.apache.xerces.xni.QName;
import org.python.apache.xerces.xni.XMLAttributes;
import org.python.apache.xerces.xs.ShortList;
import org.python.apache.xerces.xs.XSTypeDefinition;

public class Selector {
   protected final XPath fXPath;
   protected final IdentityConstraint fIdentityConstraint;
   protected IdentityConstraint fIDConstraint;

   public Selector(XPath var1, IdentityConstraint var2) {
      this.fXPath = var1;
      this.fIdentityConstraint = var2;
   }

   public org.python.apache.xerces.impl.xpath.XPath getXPath() {
      return this.fXPath;
   }

   public IdentityConstraint getIDConstraint() {
      return this.fIdentityConstraint;
   }

   public XPathMatcher createMatcher(FieldActivator var1, int var2) {
      return new Matcher(this.fXPath, var1, var2);
   }

   public String toString() {
      return this.fXPath.toString();
   }

   public class Matcher extends XPathMatcher {
      protected final FieldActivator fFieldActivator;
      protected final int fInitialDepth;
      protected int fElementDepth;
      protected int fMatchedDepth;

      public Matcher(XPath var2, FieldActivator var3, int var4) {
         super(var2);
         this.fFieldActivator = var3;
         this.fInitialDepth = var4;
      }

      public void startDocumentFragment() {
         super.startDocumentFragment();
         this.fElementDepth = 0;
         this.fMatchedDepth = -1;
      }

      public void startElement(QName var1, XMLAttributes var2) {
         super.startElement(var1, var2);
         ++this.fElementDepth;
         if (this.isMatched()) {
            this.fMatchedDepth = this.fElementDepth;
            this.fFieldActivator.startValueScopeFor(Selector.this.fIdentityConstraint, this.fInitialDepth);
            int var3 = Selector.this.fIdentityConstraint.getFieldCount();

            for(int var4 = 0; var4 < var3; ++var4) {
               Field var5 = Selector.this.fIdentityConstraint.getFieldAt(var4);
               XPathMatcher var6 = this.fFieldActivator.activateField(var5, this.fInitialDepth);
               var6.startElement(var1, var2);
            }
         }

      }

      public void endElement(QName var1, XSTypeDefinition var2, boolean var3, Object var4, short var5, ShortList var6) {
         super.endElement(var1, var2, var3, var4, var5, var6);
         if (this.fElementDepth-- == this.fMatchedDepth) {
            this.fMatchedDepth = -1;
            this.fFieldActivator.endValueScopeFor(Selector.this.fIdentityConstraint, this.fInitialDepth);
         }

      }

      public IdentityConstraint getIdentityConstraint() {
         return Selector.this.fIdentityConstraint;
      }

      public int getInitialDepth() {
         return this.fInitialDepth;
      }
   }

   public static class XPath extends org.python.apache.xerces.impl.xpath.XPath {
      public XPath(String var1, SymbolTable var2, NamespaceContext var3) throws XPathException {
         super(normalize(var1), var2, var3);

         for(int var4 = 0; var4 < this.fLocationPaths.length; ++var4) {
            org.python.apache.xerces.impl.xpath.XPath.Axis var5 = this.fLocationPaths[var4].steps[this.fLocationPaths[var4].steps.length - 1].axis;
            if (var5.type == 2) {
               throw new XPathException("c-selector-xpath");
            }
         }

      }

      private static String normalize(String var0) {
         StringBuffer var1 = new StringBuffer(var0.length() + 5);
         boolean var2 = true;

         while(true) {
            if (!XMLChar.trim(var0).startsWith("/") && !XMLChar.trim(var0).startsWith(".")) {
               var1.append("./");
            }

            int var3 = var0.indexOf(124);
            if (var3 == -1) {
               var1.append(var0);
               return var1.toString();
            }

            var1.append(var0.substring(0, var3 + 1));
            var0 = var0.substring(var3 + 1, var0.length());
         }
      }
   }
}
