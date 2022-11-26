package weblogic.apache.xerces.impl.xs.traversers;

import java.util.Locale;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import weblogic.apache.xerces.impl.dv.ValidatedInfo;
import weblogic.apache.xerces.impl.dv.XSSimpleType;
import weblogic.apache.xerces.impl.xs.SchemaGrammar;
import weblogic.apache.xerces.impl.xs.SchemaSymbols;
import weblogic.apache.xerces.impl.xs.XSAnnotationImpl;
import weblogic.apache.xerces.impl.xs.XSComplexTypeDecl;
import weblogic.apache.xerces.impl.xs.XSConstraints;
import weblogic.apache.xerces.impl.xs.XSElementDecl;
import weblogic.apache.xerces.impl.xs.XSParticleDecl;
import weblogic.apache.xerces.impl.xs.util.XInt;
import weblogic.apache.xerces.impl.xs.util.XSObjectListImpl;
import weblogic.apache.xerces.util.DOMUtil;
import weblogic.apache.xerces.util.SymbolTable;
import weblogic.apache.xerces.util.XMLChar;
import weblogic.apache.xerces.xni.QName;
import weblogic.apache.xerces.xs.XSObject;
import weblogic.apache.xerces.xs.XSObjectList;
import weblogic.apache.xerces.xs.XSTypeDefinition;

class XSDElementTraverser extends XSDAbstractTraverser {
   protected final XSElementDecl fTempElementDecl = new XSElementDecl();
   boolean fDeferTraversingLocalElements;

   XSDElementTraverser(XSDHandler var1, XSAttributeChecker var2) {
      super(var1, var2);
   }

   XSParticleDecl traverseLocal(Element var1, XSDocumentInfo var2, SchemaGrammar var3, int var4, XSObject var5) {
      XSParticleDecl var6 = null;
      if (this.fSchemaHandler.fDeclPool != null) {
         var6 = this.fSchemaHandler.fDeclPool.getParticleDecl();
      } else {
         var6 = new XSParticleDecl();
      }

      if (this.fDeferTraversingLocalElements) {
         var6.fType = 1;
         Attr var7 = var1.getAttributeNode(SchemaSymbols.ATT_MINOCCURS);
         if (var7 != null) {
            String var8 = var7.getValue();

            try {
               int var9 = Integer.parseInt(XMLChar.trim(var8));
               if (var9 >= 0) {
                  var6.fMinOccurs = var9;
               }
            } catch (NumberFormatException var10) {
            }
         }

         this.fSchemaHandler.fillInLocalElemInfo(var1, var2, var4, var5, var6);
      } else {
         this.traverseLocal(var6, var1, var2, var3, var4, var5, (String[])null);
         if (var6.fType == 0) {
            var6 = null;
         }
      }

      return var6;
   }

   protected void traverseLocal(XSParticleDecl var1, Element var2, XSDocumentInfo var3, SchemaGrammar var4, int var5, XSObject var6, String[] var7) {
      if (var7 != null) {
         var3.fNamespaceSupport.setEffectiveContext(var7);
      }

      Object[] var8 = this.fAttrChecker.checkAttributes(var2, false, var3);
      QName var9 = (QName)var8[XSAttributeChecker.ATTIDX_REF];
      XInt var10 = (XInt)var8[XSAttributeChecker.ATTIDX_MINOCCURS];
      XInt var11 = (XInt)var8[XSAttributeChecker.ATTIDX_MAXOCCURS];
      XSElementDecl var12 = null;
      XSAnnotationImpl var13 = null;
      if (var2.getAttributeNode(SchemaSymbols.ATT_REF) != null) {
         if (var9 != null) {
            var12 = (XSElementDecl)this.fSchemaHandler.getGlobalDecl(var3, 3, var9, var2);
            Element var14 = DOMUtil.getFirstChildElement(var2);
            if (var14 != null && DOMUtil.getLocalName(var14).equals(SchemaSymbols.ELT_ANNOTATION)) {
               var13 = this.traverseAnnotationDecl(var14, var8, false, var3);
               var14 = DOMUtil.getNextSiblingElement(var14);
            } else {
               String var15 = DOMUtil.getSyntheticAnnotation(var2);
               if (var15 != null) {
                  var13 = this.traverseSyntheticAnnotation(var2, var15, var8, false, var3);
               }
            }

            if (var14 != null) {
               this.reportSchemaError("src-element.2.2", new Object[]{var9.rawname, DOMUtil.getLocalName(var14)}, var14);
            }
         } else {
            var12 = null;
         }
      } else {
         var12 = this.traverseNamedElement(var2, var8, var3, var4, false, var6);
      }

      var1.fMinOccurs = var10.intValue();
      var1.fMaxOccurs = var11.intValue();
      if (var12 != null) {
         var1.fType = 1;
         var1.fValue = var12;
      } else {
         var1.fType = 0;
      }

      if (var9 != null) {
         XSObjectListImpl var16;
         if (var13 != null) {
            var16 = new XSObjectListImpl();
            ((XSObjectListImpl)var16).addXSObject(var13);
         } else {
            var16 = XSObjectListImpl.EMPTY_LIST;
         }

         var1.fAnnotations = var16;
      } else {
         var1.fAnnotations = (XSObjectList)(var12 != null ? var12.fAnnotations : XSObjectListImpl.EMPTY_LIST);
      }

      Long var17 = (Long)var8[XSAttributeChecker.ATTIDX_FROMDEFAULT];
      this.checkOccurrences(var1, SchemaSymbols.ELT_ELEMENT, (Element)var2.getParentNode(), var5, var17);
      this.fAttrChecker.returnAttrArray(var8, var3);
   }

   XSElementDecl traverseGlobal(Element var1, XSDocumentInfo var2, SchemaGrammar var3) {
      Object[] var4 = this.fAttrChecker.checkAttributes(var1, true, var2);
      XSElementDecl var5 = this.traverseNamedElement(var1, var4, var2, var3, true, (XSObject)null);
      this.fAttrChecker.returnAttrArray(var4, var2);
      return var5;
   }

   XSElementDecl traverseNamedElement(Element var1, Object[] var2, XSDocumentInfo var3, SchemaGrammar var4, boolean var5, XSObject var6) {
      Boolean var7 = (Boolean)var2[XSAttributeChecker.ATTIDX_ABSTRACT];
      XInt var8 = (XInt)var2[XSAttributeChecker.ATTIDX_BLOCK];
      String var9 = (String)var2[XSAttributeChecker.ATTIDX_DEFAULT];
      XInt var10 = (XInt)var2[XSAttributeChecker.ATTIDX_FINAL];
      String var11 = (String)var2[XSAttributeChecker.ATTIDX_FIXED];
      XInt var12 = (XInt)var2[XSAttributeChecker.ATTIDX_FORM];
      String var13 = (String)var2[XSAttributeChecker.ATTIDX_NAME];
      Boolean var14 = (Boolean)var2[XSAttributeChecker.ATTIDX_NILLABLE];
      QName var15 = (QName)var2[XSAttributeChecker.ATTIDX_SUBSGROUP];
      QName var16 = (QName)var2[XSAttributeChecker.ATTIDX_TYPE];
      XSElementDecl var17 = null;
      if (this.fSchemaHandler.fDeclPool != null) {
         var17 = this.fSchemaHandler.fDeclPool.getElementDecl();
      } else {
         var17 = new XSElementDecl();
      }

      if (var13 != null) {
         var17.fName = this.fSymbolTable.addSymbol(var13);
      }

      if (var5) {
         var17.fTargetNamespace = var3.fTargetNamespace;
         var17.setIsGlobal();
      } else {
         if (var6 instanceof XSComplexTypeDecl) {
            var17.setIsLocal((XSComplexTypeDecl)var6);
         }

         if (var12 != null) {
            if (var12.intValue() == 1) {
               var17.fTargetNamespace = var3.fTargetNamespace;
            } else {
               var17.fTargetNamespace = null;
            }
         } else if (var3.fAreLocalElementsQualified) {
            var17.fTargetNamespace = var3.fTargetNamespace;
         } else {
            var17.fTargetNamespace = null;
         }
      }

      var17.fBlock = var8 == null ? var3.fBlockDefault : var8.shortValue();
      var17.fFinal = var10 == null ? var3.fFinalDefault : var10.shortValue();
      var17.fBlock = (short)(var17.fBlock & 7);
      var17.fFinal = (short)(var17.fFinal & 3);
      if (var14) {
         var17.setIsNillable();
      }

      if (var7 != null && var7) {
         var17.setIsAbstract();
      }

      if (var11 != null) {
         var17.fDefault = new ValidatedInfo();
         var17.fDefault.normalizedValue = var11;
         var17.setConstraintType((short)2);
      } else if (var9 != null) {
         var17.fDefault = new ValidatedInfo();
         var17.fDefault.normalizedValue = var9;
         var17.setConstraintType((short)1);
      } else {
         var17.setConstraintType((short)0);
      }

      if (var15 != null) {
         var17.fSubGroup = (XSElementDecl)this.fSchemaHandler.getGlobalDecl(var3, 3, var15, var1);
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

      XSObjectListImpl var25;
      if (var19 != null) {
         var25 = new XSObjectListImpl();
         ((XSObjectListImpl)var25).addXSObject(var19);
      } else {
         var25 = XSObjectListImpl.EMPTY_LIST;
      }

      var17.fAnnotations = var25;
      Object var21 = null;
      boolean var22 = false;
      String var23;
      if (var18 != null) {
         var23 = DOMUtil.getLocalName(var18);
         if (var23.equals(SchemaSymbols.ELT_COMPLEXTYPE)) {
            var21 = this.fSchemaHandler.fComplexTypeTraverser.traverseLocal(var18, var3, var4);
            var22 = true;
            var18 = DOMUtil.getNextSiblingElement(var18);
         } else if (var23.equals(SchemaSymbols.ELT_SIMPLETYPE)) {
            var21 = this.fSchemaHandler.fSimpleTypeTraverser.traverseLocal(var18, var3, var4);
            var22 = true;
            var18 = DOMUtil.getNextSiblingElement(var18);
         }
      }

      if (var21 == null && var16 != null) {
         var21 = (XSTypeDefinition)this.fSchemaHandler.getGlobalDecl(var3, 7, var16, var1);
         if (var21 == null) {
            var17.fUnresolvedTypeName = var16;
         }
      }

      if (var21 == null && var17.fSubGroup != null) {
         var21 = var17.fSubGroup.fType;
      }

      if (var21 == null) {
         var21 = SchemaGrammar.fAnyType;
      }

      var17.fType = (XSTypeDefinition)var21;
      if (var18 != null) {
         var23 = DOMUtil.getLocalName(var18);

         while(var18 != null && (var23.equals(SchemaSymbols.ELT_KEY) || var23.equals(SchemaSymbols.ELT_KEYREF) || var23.equals(SchemaSymbols.ELT_UNIQUE))) {
            if (!var23.equals(SchemaSymbols.ELT_KEY) && !var23.equals(SchemaSymbols.ELT_UNIQUE)) {
               if (var23.equals(SchemaSymbols.ELT_KEYREF)) {
                  this.fSchemaHandler.storeKeyRef(var18, var3, var17);
               }
            } else {
               DOMUtil.setHidden(var18, this.fSchemaHandler.fHiddenNodes);
               this.fSchemaHandler.fUniqueOrKeyTraverser.traverse(var18, var17, var3, var4);
               if (DOMUtil.getAttrValue(var18, SchemaSymbols.ATT_NAME).length() != 0) {
                  this.fSchemaHandler.checkForDuplicateNames(var3.fTargetNamespace == null ? "," + DOMUtil.getAttrValue(var18, SchemaSymbols.ATT_NAME) : var3.fTargetNamespace + "," + DOMUtil.getAttrValue(var18, SchemaSymbols.ATT_NAME), 1, this.fSchemaHandler.getIDRegistry(), this.fSchemaHandler.getIDRegistry_sub(), var18, var3);
               }
            }

            var18 = DOMUtil.getNextSiblingElement(var18);
            if (var18 != null) {
               var23 = DOMUtil.getLocalName(var18);
            }
         }
      }

      if (var13 == null) {
         if (var5) {
            this.reportSchemaError("s4s-att-must-appear", new Object[]{SchemaSymbols.ELT_ELEMENT, SchemaSymbols.ATT_NAME}, var1);
         } else {
            this.reportSchemaError("src-element.2.1", (Object[])null, var1);
         }

         var13 = "(no name)";
      }

      if (var18 != null) {
         this.reportSchemaError("s4s-elt-must-match.1", new Object[]{var13, "(annotation?, (simpleType | complexType)?, (unique | key | keyref)*))", DOMUtil.getLocalName(var18)}, var18);
      }

      if (var9 != null && var11 != null) {
         this.reportSchemaError("src-element.1", new Object[]{var13}, var1);
      }

      if (var22 && var16 != null) {
         this.reportSchemaError("src-element.3", new Object[]{var13}, var1);
      }

      this.checkNotationType(var13, (XSTypeDefinition)var21, var1);
      if (var17.fDefault != null) {
         this.fValidationState.setNamespaceSupport(var3.fNamespaceSupport);
         if (XSConstraints.ElementDefaultValidImmediate(var17.fType, var17.fDefault.normalizedValue, this.fValidationState, var17.fDefault) == null) {
            this.reportSchemaError("e-props-correct.2", new Object[]{var13, var17.fDefault.normalizedValue}, var1);
            var17.fDefault = null;
            var17.setConstraintType((short)0);
         }
      }

      if (var17.fSubGroup != null && !XSConstraints.checkTypeDerivationOk(var17.fType, var17.fSubGroup.fType, var17.fSubGroup.fFinal)) {
         this.reportSchemaError("e-props-correct.4", new Object[]{var13, var15.prefix + ":" + var15.localpart}, var1);
         var17.fSubGroup = null;
      }

      if (var17.fDefault != null && (((XSTypeDefinition)var21).getTypeCategory() == 16 && ((XSSimpleType)var21).isIDType() || ((XSTypeDefinition)var21).getTypeCategory() == 15 && ((XSComplexTypeDecl)var21).containsTypeID())) {
         this.reportSchemaError("e-props-correct.5", new Object[]{var17.fName}, var1);
         var17.fDefault = null;
         var17.setConstraintType((short)0);
      }

      if (var17.fName == null) {
         return null;
      } else {
         if (var5) {
            var4.addGlobalElementDeclAll(var17);
            if (var4.getGlobalElementDecl(var17.fName) == null) {
               var4.addGlobalElementDecl(var17);
            }

            var23 = this.fSchemaHandler.schemaDocument2SystemId(var3);
            XSElementDecl var24 = var4.getGlobalElementDecl(var17.fName, var23);
            if (var24 == null) {
               var4.addGlobalElementDecl(var17, var23);
            }

            if (this.fSchemaHandler.fTolerateDuplicates) {
               if (var24 != null) {
                  var17 = var24;
               }

               this.fSchemaHandler.addGlobalElementDecl(var17);
            }
         }

         return var17;
      }
   }

   void reset(SymbolTable var1, boolean var2, Locale var3) {
      super.reset(var1, var2, var3);
      this.fDeferTraversingLocalElements = true;
   }
}
