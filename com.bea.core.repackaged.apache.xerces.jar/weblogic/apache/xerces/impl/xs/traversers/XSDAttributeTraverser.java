package weblogic.apache.xerces.impl.xs.traversers;

import org.w3c.dom.Element;
import weblogic.apache.xerces.impl.dv.InvalidDatatypeValueException;
import weblogic.apache.xerces.impl.dv.ValidatedInfo;
import weblogic.apache.xerces.impl.dv.XSSimpleType;
import weblogic.apache.xerces.impl.xs.SchemaGrammar;
import weblogic.apache.xerces.impl.xs.SchemaSymbols;
import weblogic.apache.xerces.impl.xs.XSAnnotationImpl;
import weblogic.apache.xerces.impl.xs.XSAttributeDecl;
import weblogic.apache.xerces.impl.xs.XSAttributeUseImpl;
import weblogic.apache.xerces.impl.xs.XSComplexTypeDecl;
import weblogic.apache.xerces.impl.xs.util.XInt;
import weblogic.apache.xerces.impl.xs.util.XSObjectListImpl;
import weblogic.apache.xerces.util.DOMUtil;
import weblogic.apache.xerces.util.XMLSymbols;
import weblogic.apache.xerces.xni.QName;
import weblogic.apache.xerces.xs.XSTypeDefinition;

class XSDAttributeTraverser extends XSDAbstractTraverser {
   public XSDAttributeTraverser(XSDHandler var1, XSAttributeChecker var2) {
      super(var1, var2);
   }

   protected XSAttributeUseImpl traverseLocal(Element var1, XSDocumentInfo var2, SchemaGrammar var3, XSComplexTypeDecl var4) {
      Object[] var5 = this.fAttrChecker.checkAttributes(var1, false, var2);
      String var6 = (String)var5[XSAttributeChecker.ATTIDX_DEFAULT];
      String var7 = (String)var5[XSAttributeChecker.ATTIDX_FIXED];
      String var8 = (String)var5[XSAttributeChecker.ATTIDX_NAME];
      QName var9 = (QName)var5[XSAttributeChecker.ATTIDX_REF];
      XInt var10 = (XInt)var5[XSAttributeChecker.ATTIDX_USE];
      XSAttributeDecl var11 = null;
      XSAnnotationImpl var12 = null;
      if (var1.getAttributeNode(SchemaSymbols.ATT_REF) != null) {
         if (var9 != null) {
            var11 = (XSAttributeDecl)this.fSchemaHandler.getGlobalDecl(var2, 1, var9, var1);
            Element var13 = DOMUtil.getFirstChildElement(var1);
            if (var13 != null && DOMUtil.getLocalName(var13).equals(SchemaSymbols.ELT_ANNOTATION)) {
               var12 = this.traverseAnnotationDecl(var13, var5, false, var2);
               var13 = DOMUtil.getNextSiblingElement(var13);
            } else {
               String var14 = DOMUtil.getSyntheticAnnotation(var1);
               if (var14 != null) {
                  var12 = this.traverseSyntheticAnnotation(var1, var14, var5, false, var2);
               }
            }

            if (var13 != null) {
               this.reportSchemaError("src-attribute.3.2", new Object[]{var9.rawname}, var13);
            }

            var8 = var9.localpart;
         } else {
            var11 = null;
         }
      } else {
         var11 = this.traverseNamedAttr(var1, var5, var2, var3, false, var4);
      }

      byte var17 = 0;
      if (var6 != null) {
         var17 = 1;
      } else if (var7 != null) {
         var17 = 2;
         var6 = var7;
         var7 = null;
      }

      XSAttributeUseImpl var18 = null;
      if (var11 != null) {
         if (this.fSchemaHandler.fDeclPool != null) {
            var18 = this.fSchemaHandler.fDeclPool.getAttributeUse();
         } else {
            var18 = new XSAttributeUseImpl();
         }

         var18.fAttrDecl = var11;
         var18.fUse = var10.shortValue();
         var18.fConstraintType = var17;
         if (var6 != null) {
            var18.fDefault = new ValidatedInfo();
            var18.fDefault.normalizedValue = var6;
         }

         if (var1.getAttributeNode(SchemaSymbols.ATT_REF) == null) {
            var18.fAnnotations = var11.getAnnotations();
         } else {
            XSObjectListImpl var15;
            if (var12 != null) {
               var15 = new XSObjectListImpl();
               ((XSObjectListImpl)var15).addXSObject(var12);
            } else {
               var15 = XSObjectListImpl.EMPTY_LIST;
            }

            var18.fAnnotations = var15;
         }
      }

      if (var6 != null && var7 != null) {
         this.reportSchemaError("src-attribute.1", new Object[]{var8}, var1);
      }

      if (var17 == 1 && var10 != null && var10.intValue() != 0) {
         this.reportSchemaError("src-attribute.2", new Object[]{var8}, var1);
         var18.fUse = 0;
      }

      if (var6 != null && var18 != null) {
         this.fValidationState.setNamespaceSupport(var2.fNamespaceSupport);

         try {
            this.checkDefaultValid(var18);
         } catch (InvalidDatatypeValueException var16) {
            this.reportSchemaError(var16.getKey(), var16.getArgs(), var1);
            this.reportSchemaError("a-props-correct.2", new Object[]{var8, var6}, var1);
            var18.fDefault = null;
            var18.fConstraintType = 0;
         }

         if (((XSSimpleType)var11.getTypeDefinition()).isIDType()) {
            this.reportSchemaError("a-props-correct.3", new Object[]{var8}, var1);
            var18.fDefault = null;
            var18.fConstraintType = 0;
         }

         if (var18.fAttrDecl.getConstraintType() == 2 && var18.fConstraintType != 0 && (var18.fConstraintType != 2 || !var18.fAttrDecl.getValInfo().actualValue.equals(var18.fDefault.actualValue))) {
            this.reportSchemaError("au-props-correct.2", new Object[]{var8, var18.fAttrDecl.getValInfo().stringValue()}, var1);
            var18.fDefault = var18.fAttrDecl.getValInfo();
            var18.fConstraintType = 2;
         }
      }

      this.fAttrChecker.returnAttrArray(var5, var2);
      return var18;
   }

   protected XSAttributeDecl traverseGlobal(Element var1, XSDocumentInfo var2, SchemaGrammar var3) {
      Object[] var4 = this.fAttrChecker.checkAttributes(var1, true, var2);
      XSAttributeDecl var5 = this.traverseNamedAttr(var1, var4, var2, var3, true, (XSComplexTypeDecl)null);
      this.fAttrChecker.returnAttrArray(var4, var2);
      return var5;
   }

   XSAttributeDecl traverseNamedAttr(Element var1, Object[] var2, XSDocumentInfo var3, SchemaGrammar var4, boolean var5, XSComplexTypeDecl var6) {
      String var7 = (String)var2[XSAttributeChecker.ATTIDX_DEFAULT];
      String var8 = (String)var2[XSAttributeChecker.ATTIDX_FIXED];
      XInt var9 = (XInt)var2[XSAttributeChecker.ATTIDX_FORM];
      String var10 = (String)var2[XSAttributeChecker.ATTIDX_NAME];
      QName var11 = (QName)var2[XSAttributeChecker.ATTIDX_TYPE];
      XSAttributeDecl var12 = null;
      if (this.fSchemaHandler.fDeclPool != null) {
         var12 = this.fSchemaHandler.fDeclPool.getAttributeDecl();
      } else {
         var12 = new XSAttributeDecl();
      }

      if (var10 != null) {
         var10 = this.fSymbolTable.addSymbol(var10);
      }

      String var13 = null;
      XSComplexTypeDecl var14 = null;
      byte var15 = 0;
      if (var5) {
         var13 = var3.fTargetNamespace;
         var15 = 1;
      } else {
         if (var6 != null) {
            var14 = var6;
            var15 = 2;
         }

         if (var9 != null) {
            if (var9.intValue() == 1) {
               var13 = var3.fTargetNamespace;
            }
         } else if (var3.fAreLocalAttributesQualified) {
            var13 = var3.fTargetNamespace;
         }
      }

      ValidatedInfo var16 = null;
      byte var17 = 0;
      if (var5) {
         if (var8 != null) {
            var16 = new ValidatedInfo();
            var16.normalizedValue = var8;
            var17 = 2;
         } else if (var7 != null) {
            var16 = new ValidatedInfo();
            var16.normalizedValue = var7;
            var17 = 1;
         }
      }

      Element var18 = DOMUtil.getFirstChildElement(var1);
      XSAnnotationImpl var19 = null;
      if (var18 != null && DOMUtil.getLocalName(var18).equals(SchemaSymbols.ELT_ANNOTATION)) {
         var19 = this.traverseAnnotationDecl(var18, var2, false, var3);
         var18 = DOMUtil.getNextSiblingElement(var18);
      } else {
         String var20 = DOMUtil.getSyntheticAnnotation(var1);
         if (var20 != null) {
            var19 = this.traverseSyntheticAnnotation(var1, var20, var2, false, var3);
         }
      }

      XSSimpleType var26 = null;
      boolean var21 = false;
      if (var18 != null) {
         String var22 = DOMUtil.getLocalName(var18);
         if (var22.equals(SchemaSymbols.ELT_SIMPLETYPE)) {
            var26 = this.fSchemaHandler.fSimpleTypeTraverser.traverseLocal(var18, var3, var4);
            var21 = true;
            var18 = DOMUtil.getNextSiblingElement(var18);
         }
      }

      if (var26 == null && var11 != null) {
         XSTypeDefinition var27 = (XSTypeDefinition)this.fSchemaHandler.getGlobalDecl(var3, 7, var11, var1);
         if (var27 != null && var27.getTypeCategory() == 16) {
            var26 = (XSSimpleType)var27;
         } else {
            this.reportSchemaError("src-resolve", new Object[]{var11.rawname, "simpleType definition"}, var1);
            if (var27 == null) {
               var12.fUnresolvedTypeName = var11;
            }
         }
      }

      if (var26 == null) {
         var26 = SchemaGrammar.fAnySimpleType;
      }

      XSObjectListImpl var28;
      if (var19 != null) {
         var28 = new XSObjectListImpl();
         ((XSObjectListImpl)var28).addXSObject(var19);
      } else {
         var28 = XSObjectListImpl.EMPTY_LIST;
      }

      var12.setValues(var10, var13, var26, var17, var15, var16, var14, var28);
      if (var10 == null) {
         if (var5) {
            this.reportSchemaError("s4s-att-must-appear", new Object[]{SchemaSymbols.ELT_ATTRIBUTE, SchemaSymbols.ATT_NAME}, var1);
         } else {
            this.reportSchemaError("src-attribute.3.1", (Object[])null, var1);
         }

         var10 = "(no name)";
      }

      if (var18 != null) {
         this.reportSchemaError("s4s-elt-must-match.1", new Object[]{var10, "(annotation?, (simpleType?))", DOMUtil.getLocalName(var18)}, var18);
      }

      if (var7 != null && var8 != null) {
         this.reportSchemaError("src-attribute.1", new Object[]{var10}, var1);
      }

      if (var21 && var11 != null) {
         this.reportSchemaError("src-attribute.4", new Object[]{var10}, var1);
      }

      this.checkNotationType(var10, var26, var1);
      if (var16 != null) {
         this.fValidationState.setNamespaceSupport(var3.fNamespaceSupport);

         try {
            this.checkDefaultValid(var12);
         } catch (InvalidDatatypeValueException var25) {
            this.reportSchemaError(var25.getKey(), var25.getArgs(), var1);
            this.reportSchemaError("a-props-correct.2", new Object[]{var10, var16.normalizedValue}, var1);
            var16 = null;
            var17 = 0;
            var12.setValues(var10, var13, var26, var17, var15, var16, var14, var28);
         }
      }

      if (var16 != null && var26.isIDType()) {
         this.reportSchemaError("a-props-correct.3", new Object[]{var10}, var1);
         var16 = null;
         var17 = 0;
         var12.setValues(var10, var13, var26, var17, var15, var16, var14, var28);
      }

      if (var10 != null && var10.equals(XMLSymbols.PREFIX_XMLNS)) {
         this.reportSchemaError("no-xmlns", (Object[])null, var1);
         return null;
      } else if (var13 != null && var13.equals(SchemaSymbols.URI_XSI)) {
         this.reportSchemaError("no-xsi", new Object[]{SchemaSymbols.URI_XSI}, var1);
         return null;
      } else if (var10.equals("(no name)")) {
         return null;
      } else {
         if (var5) {
            if (var4.getGlobalAttributeDecl(var10) == null) {
               var4.addGlobalAttributeDecl(var12);
            }

            String var23 = this.fSchemaHandler.schemaDocument2SystemId(var3);
            XSAttributeDecl var24 = var4.getGlobalAttributeDecl(var10, var23);
            if (var24 == null) {
               var4.addGlobalAttributeDecl(var12, var23);
            }

            if (this.fSchemaHandler.fTolerateDuplicates) {
               if (var24 != null) {
                  var12 = var24;
               }

               this.fSchemaHandler.addGlobalAttributeDecl(var12);
            }
         }

         return var12;
      }
   }

   void checkDefaultValid(XSAttributeDecl var1) throws InvalidDatatypeValueException {
      ((XSSimpleType)var1.getTypeDefinition()).validate((String)var1.getValInfo().normalizedValue, this.fValidationState, var1.getValInfo());
      ((XSSimpleType)var1.getTypeDefinition()).validate((String)var1.getValInfo().stringValue(), this.fValidationState, var1.getValInfo());
   }

   void checkDefaultValid(XSAttributeUseImpl var1) throws InvalidDatatypeValueException {
      ((XSSimpleType)var1.fAttrDecl.getTypeDefinition()).validate((String)var1.fDefault.normalizedValue, this.fValidationState, var1.fDefault);
      ((XSSimpleType)var1.fAttrDecl.getTypeDefinition()).validate((String)var1.fDefault.stringValue(), this.fValidationState, var1.fDefault);
   }
}
