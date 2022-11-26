package weblogic.apache.xerces.impl.xs.traversers;

import org.w3c.dom.Element;
import weblogic.apache.xerces.impl.xs.SchemaGrammar;
import weblogic.apache.xerces.impl.xs.SchemaSymbols;
import weblogic.apache.xerces.impl.xs.XSAnnotationImpl;
import weblogic.apache.xerces.impl.xs.XSNotationDecl;
import weblogic.apache.xerces.impl.xs.util.XSObjectListImpl;
import weblogic.apache.xerces.util.DOMUtil;

class XSDNotationTraverser extends XSDAbstractTraverser {
   XSDNotationTraverser(XSDHandler var1, XSAttributeChecker var2) {
      super(var1, var2);
   }

   XSNotationDecl traverse(Element var1, XSDocumentInfo var2, SchemaGrammar var3) {
      Object[] var4 = this.fAttrChecker.checkAttributes(var1, true, var2);
      String var5 = (String)var4[XSAttributeChecker.ATTIDX_NAME];
      String var6 = (String)var4[XSAttributeChecker.ATTIDX_PUBLIC];
      String var7 = (String)var4[XSAttributeChecker.ATTIDX_SYSTEM];
      if (var5 == null) {
         this.reportSchemaError("s4s-att-must-appear", new Object[]{SchemaSymbols.ELT_NOTATION, SchemaSymbols.ATT_NAME}, var1);
         this.fAttrChecker.returnAttrArray(var4, var2);
         return null;
      } else {
         if (var7 == null && var6 == null) {
            this.reportSchemaError("PublicSystemOnNotation", (Object[])null, var1);
            var6 = "missing";
         }

         XSNotationDecl var8 = new XSNotationDecl();
         var8.fName = var5;
         var8.fTargetNamespace = var2.fTargetNamespace;
         var8.fPublicId = var6;
         var8.fSystemId = var7;
         Element var9 = DOMUtil.getFirstChildElement(var1);
         XSAnnotationImpl var10 = null;
         if (var9 != null && DOMUtil.getLocalName(var9).equals(SchemaSymbols.ELT_ANNOTATION)) {
            var10 = this.traverseAnnotationDecl(var9, var4, false, var2);
            var9 = DOMUtil.getNextSiblingElement(var9);
         } else {
            String var11 = DOMUtil.getSyntheticAnnotation(var1);
            if (var11 != null) {
               var10 = this.traverseSyntheticAnnotation(var1, var11, var4, false, var2);
            }
         }

         XSObjectListImpl var14;
         if (var10 != null) {
            var14 = new XSObjectListImpl();
            ((XSObjectListImpl)var14).addXSObject(var10);
         } else {
            var14 = XSObjectListImpl.EMPTY_LIST;
         }

         var8.fAnnotations = var14;
         if (var9 != null) {
            Object[] var12 = new Object[]{SchemaSymbols.ELT_NOTATION, "(annotation?)", DOMUtil.getLocalName(var9)};
            this.reportSchemaError("s4s-elt-must-match.1", var12, var9);
         }

         if (var3.getGlobalNotationDecl(var8.fName) == null) {
            var3.addGlobalNotationDecl(var8);
         }

         String var15 = this.fSchemaHandler.schemaDocument2SystemId(var2);
         XSNotationDecl var13 = var3.getGlobalNotationDecl(var8.fName, var15);
         if (var13 == null) {
            var3.addGlobalNotationDecl(var8, var15);
         }

         if (this.fSchemaHandler.fTolerateDuplicates) {
            if (var13 != null) {
               var8 = var13;
            }

            this.fSchemaHandler.addGlobalNotationDecl(var8);
         }

         this.fAttrChecker.returnAttrArray(var4, var2);
         return var8;
      }
   }
}
