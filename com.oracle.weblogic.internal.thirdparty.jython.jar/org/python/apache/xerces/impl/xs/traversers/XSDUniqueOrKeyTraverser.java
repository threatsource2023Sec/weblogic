package org.python.apache.xerces.impl.xs.traversers;

import org.python.apache.xerces.impl.xs.SchemaGrammar;
import org.python.apache.xerces.impl.xs.SchemaSymbols;
import org.python.apache.xerces.impl.xs.XSElementDecl;
import org.python.apache.xerces.impl.xs.identity.IdentityConstraint;
import org.python.apache.xerces.impl.xs.identity.UniqueOrKey;
import org.python.apache.xerces.util.DOMUtil;
import org.w3c.dom.Element;

class XSDUniqueOrKeyTraverser extends XSDAbstractIDConstraintTraverser {
   public XSDUniqueOrKeyTraverser(XSDHandler var1, XSAttributeChecker var2) {
      super(var1, var2);
   }

   void traverse(Element var1, XSElementDecl var2, XSDocumentInfo var3, SchemaGrammar var4) {
      Object[] var5 = this.fAttrChecker.checkAttributes(var1, false, var3);
      String var6 = (String)var5[XSAttributeChecker.ATTIDX_NAME];
      if (var6 == null) {
         this.reportSchemaError("s4s-att-must-appear", new Object[]{DOMUtil.getLocalName(var1), SchemaSymbols.ATT_NAME}, var1);
         this.fAttrChecker.returnAttrArray(var5, var3);
      } else {
         UniqueOrKey var7 = null;
         if (DOMUtil.getLocalName(var1).equals(SchemaSymbols.ELT_UNIQUE)) {
            var7 = new UniqueOrKey(var3.fTargetNamespace, var6, var2.fName, (short)3);
         } else {
            var7 = new UniqueOrKey(var3.fTargetNamespace, var6, var2.fName, (short)1);
         }

         if (this.traverseIdentityConstraint(var7, var1, var3, var5)) {
            if (var4.getIDConstraintDecl(var7.getIdentityConstraintName()) == null) {
               var4.addIDConstraintDecl(var2, var7);
            }

            String var8 = this.fSchemaHandler.schemaDocument2SystemId(var3);
            IdentityConstraint var9 = var4.getIDConstraintDecl(var7.getIdentityConstraintName(), var8);
            if (var9 == null) {
               var4.addIDConstraintDecl(var2, var7, var8);
            }

            if (this.fSchemaHandler.fTolerateDuplicates) {
               if (var9 != null && var9 instanceof UniqueOrKey) {
                  var7 = var7;
               }

               this.fSchemaHandler.addIDConstraintDecl(var7);
            }
         }

         this.fAttrChecker.returnAttrArray(var5, var3);
      }
   }
}
