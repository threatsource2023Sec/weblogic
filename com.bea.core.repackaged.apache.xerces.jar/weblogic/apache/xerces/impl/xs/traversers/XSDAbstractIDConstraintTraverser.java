package weblogic.apache.xerces.impl.xs.traversers;

import org.w3c.dom.Element;
import weblogic.apache.xerces.impl.xpath.XPathException;
import weblogic.apache.xerces.impl.xs.SchemaSymbols;
import weblogic.apache.xerces.impl.xs.identity.Field;
import weblogic.apache.xerces.impl.xs.identity.IdentityConstraint;
import weblogic.apache.xerces.impl.xs.identity.Selector;
import weblogic.apache.xerces.util.DOMUtil;
import weblogic.apache.xerces.util.XMLChar;

class XSDAbstractIDConstraintTraverser extends XSDAbstractTraverser {
   public XSDAbstractIDConstraintTraverser(XSDHandler var1, XSAttributeChecker var2) {
      super(var1, var2);
   }

   boolean traverseIdentityConstraint(IdentityConstraint var1, Element var2, XSDocumentInfo var3, Object[] var4) {
      Element var5 = DOMUtil.getFirstChildElement(var2);
      if (var5 == null) {
         this.reportSchemaError("s4s-elt-must-match.2", new Object[]{"identity constraint", "(annotation?, selector, field+)"}, var2);
         return false;
      } else {
         if (DOMUtil.getLocalName(var5).equals(SchemaSymbols.ELT_ANNOTATION)) {
            var1.addAnnotation(this.traverseAnnotationDecl(var5, var4, false, var3));
            var5 = DOMUtil.getNextSiblingElement(var5);
            if (var5 == null) {
               this.reportSchemaError("s4s-elt-must-match.2", new Object[]{"identity constraint", "(annotation?, selector, field+)"}, var2);
               return false;
            }
         } else {
            String var6 = DOMUtil.getSyntheticAnnotation(var2);
            if (var6 != null) {
               var1.addAnnotation(this.traverseSyntheticAnnotation(var2, var6, var4, false, var3));
            }
         }

         if (!DOMUtil.getLocalName(var5).equals(SchemaSymbols.ELT_SELECTOR)) {
            this.reportSchemaError("s4s-elt-must-match.1", new Object[]{"identity constraint", "(annotation?, selector, field+)", SchemaSymbols.ELT_SELECTOR}, var5);
            return false;
         } else {
            Object[] var17 = this.fAttrChecker.checkAttributes(var5, false, var3);
            Element var7 = DOMUtil.getFirstChildElement(var5);
            String var8;
            if (var7 != null) {
               if (DOMUtil.getLocalName(var7).equals(SchemaSymbols.ELT_ANNOTATION)) {
                  var1.addAnnotation(this.traverseAnnotationDecl(var7, var17, false, var3));
                  var7 = DOMUtil.getNextSiblingElement(var7);
               } else {
                  this.reportSchemaError("s4s-elt-must-match.1", new Object[]{SchemaSymbols.ELT_SELECTOR, "(annotation?)", DOMUtil.getLocalName(var7)}, var7);
               }

               if (var7 != null) {
                  this.reportSchemaError("s4s-elt-must-match.1", new Object[]{SchemaSymbols.ELT_SELECTOR, "(annotation?)", DOMUtil.getLocalName(var7)}, var7);
               }
            } else {
               var8 = DOMUtil.getSyntheticAnnotation(var5);
               if (var8 != null) {
                  var1.addAnnotation(this.traverseSyntheticAnnotation(var2, var8, var17, false, var3));
               }
            }

            var8 = (String)var17[XSAttributeChecker.ATTIDX_XPATH];
            if (var8 == null) {
               this.reportSchemaError("s4s-att-must-appear", new Object[]{SchemaSymbols.ELT_SELECTOR, SchemaSymbols.ATT_XPATH}, var5);
               return false;
            } else {
               var8 = XMLChar.trim(var8);
               Selector.XPath var9 = null;

               try {
                  var9 = new Selector.XPath(var8, this.fSymbolTable, var3.fNamespaceSupport);
                  Selector var10 = new Selector(var9, var1);
                  var1.setSelector(var10);
               } catch (XPathException var16) {
                  this.reportSchemaError(var16.getKey(), new Object[]{var8}, var5);
                  this.fAttrChecker.returnAttrArray(var17, var3);
                  return false;
               }

               this.fAttrChecker.returnAttrArray(var17, var3);
               Element var18 = DOMUtil.getNextSiblingElement(var5);
               if (var18 == null) {
                  this.reportSchemaError("s4s-elt-must-match.2", new Object[]{"identity constraint", "(annotation?, selector, field+)"}, var5);
                  return false;
               } else {
                  while(var18 != null) {
                     if (!DOMUtil.getLocalName(var18).equals(SchemaSymbols.ELT_FIELD)) {
                        this.reportSchemaError("s4s-elt-must-match.1", new Object[]{"identity constraint", "(annotation?, selector, field+)", SchemaSymbols.ELT_FIELD}, var18);
                        var18 = DOMUtil.getNextSiblingElement(var18);
                     } else {
                        var17 = this.fAttrChecker.checkAttributes(var18, false, var3);
                        Element var11 = DOMUtil.getFirstChildElement(var18);
                        if (var11 != null && DOMUtil.getLocalName(var11).equals(SchemaSymbols.ELT_ANNOTATION)) {
                           var1.addAnnotation(this.traverseAnnotationDecl(var11, var17, false, var3));
                           var11 = DOMUtil.getNextSiblingElement(var11);
                        }

                        String var12;
                        if (var11 != null) {
                           this.reportSchemaError("s4s-elt-must-match.1", new Object[]{SchemaSymbols.ELT_FIELD, "(annotation?)", DOMUtil.getLocalName(var11)}, var11);
                        } else {
                           var12 = DOMUtil.getSyntheticAnnotation(var18);
                           if (var12 != null) {
                              var1.addAnnotation(this.traverseSyntheticAnnotation(var2, var12, var17, false, var3));
                           }
                        }

                        var12 = (String)var17[XSAttributeChecker.ATTIDX_XPATH];
                        if (var12 == null) {
                           this.reportSchemaError("s4s-att-must-appear", new Object[]{SchemaSymbols.ELT_FIELD, SchemaSymbols.ATT_XPATH}, var18);
                           this.fAttrChecker.returnAttrArray(var17, var3);
                           return false;
                        }

                        var12 = XMLChar.trim(var12);

                        try {
                           Field.XPath var13 = new Field.XPath(var12, this.fSymbolTable, var3.fNamespaceSupport);
                           Field var14 = new Field(var13, var1);
                           var1.addField(var14);
                        } catch (XPathException var15) {
                           this.reportSchemaError(var15.getKey(), new Object[]{var12}, var18);
                           this.fAttrChecker.returnAttrArray(var17, var3);
                           return false;
                        }

                        var18 = DOMUtil.getNextSiblingElement(var18);
                        this.fAttrChecker.returnAttrArray(var17, var3);
                     }
                  }

                  return var1.getFieldCount() > 0;
               }
            }
         }
      }
   }
}
