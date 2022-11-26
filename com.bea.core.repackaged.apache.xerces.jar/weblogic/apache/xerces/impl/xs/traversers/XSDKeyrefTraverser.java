package weblogic.apache.xerces.impl.xs.traversers;

import org.w3c.dom.Element;
import weblogic.apache.xerces.impl.xs.SchemaGrammar;
import weblogic.apache.xerces.impl.xs.SchemaSymbols;
import weblogic.apache.xerces.impl.xs.XSElementDecl;
import weblogic.apache.xerces.impl.xs.identity.IdentityConstraint;
import weblogic.apache.xerces.impl.xs.identity.KeyRef;
import weblogic.apache.xerces.impl.xs.identity.UniqueOrKey;
import weblogic.apache.xerces.xni.QName;

class XSDKeyrefTraverser extends XSDAbstractIDConstraintTraverser {
   public XSDKeyrefTraverser(XSDHandler var1, XSAttributeChecker var2) {
      super(var1, var2);
   }

   void traverse(Element var1, XSElementDecl var2, XSDocumentInfo var3, SchemaGrammar var4) {
      Object[] var5 = this.fAttrChecker.checkAttributes(var1, false, var3);
      String var6 = (String)var5[XSAttributeChecker.ATTIDX_NAME];
      if (var6 == null) {
         this.reportSchemaError("s4s-att-must-appear", new Object[]{SchemaSymbols.ELT_KEYREF, SchemaSymbols.ATT_NAME}, var1);
         this.fAttrChecker.returnAttrArray(var5, var3);
      } else {
         QName var7 = (QName)var5[XSAttributeChecker.ATTIDX_REFER];
         if (var7 == null) {
            this.reportSchemaError("s4s-att-must-appear", new Object[]{SchemaSymbols.ELT_KEYREF, SchemaSymbols.ATT_REFER}, var1);
            this.fAttrChecker.returnAttrArray(var5, var3);
         } else {
            UniqueOrKey var8 = null;
            IdentityConstraint var9 = (IdentityConstraint)this.fSchemaHandler.getGlobalDecl(var3, 5, var7, var1);
            if (var9 != null) {
               if (var9.getCategory() != 1 && var9.getCategory() != 3) {
                  this.reportSchemaError("src-resolve", new Object[]{var7.rawname, "identity constraint key/unique"}, var1);
               } else {
                  var8 = (UniqueOrKey)var9;
               }
            }

            if (var8 == null) {
               this.fAttrChecker.returnAttrArray(var5, var3);
            } else {
               KeyRef var10 = new KeyRef(var3.fTargetNamespace, var6, var2.fName, var8);
               if (this.traverseIdentityConstraint(var10, var1, var3, var5)) {
                  if (var8.getFieldCount() != var10.getFieldCount()) {
                     this.reportSchemaError("c-props-correct.2", new Object[]{var6, var8.getIdentityConstraintName()}, var1);
                  } else {
                     if (var4.getIDConstraintDecl(var10.getIdentityConstraintName()) == null) {
                        var4.addIDConstraintDecl(var2, var10);
                     }

                     String var11 = this.fSchemaHandler.schemaDocument2SystemId(var3);
                     IdentityConstraint var12 = var4.getIDConstraintDecl(var10.getIdentityConstraintName(), var11);
                     if (var12 == null) {
                        var4.addIDConstraintDecl(var2, var10, var11);
                     }

                     if (this.fSchemaHandler.fTolerateDuplicates) {
                        if (var12 != null && var12 instanceof KeyRef) {
                           var10 = (KeyRef)var12;
                        }

                        this.fSchemaHandler.addIDConstraintDecl(var10);
                     }
                  }
               }

               this.fAttrChecker.returnAttrArray(var5, var3);
            }
         }
      }
   }
}
