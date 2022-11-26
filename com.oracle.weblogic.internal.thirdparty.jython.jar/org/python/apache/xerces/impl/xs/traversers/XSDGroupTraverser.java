package org.python.apache.xerces.impl.xs.traversers;

import org.python.apache.xerces.impl.xs.SchemaGrammar;
import org.python.apache.xerces.impl.xs.SchemaSymbols;
import org.python.apache.xerces.impl.xs.XSAnnotationImpl;
import org.python.apache.xerces.impl.xs.XSConstraints;
import org.python.apache.xerces.impl.xs.XSGroupDecl;
import org.python.apache.xerces.impl.xs.XSModelGroupImpl;
import org.python.apache.xerces.impl.xs.XSParticleDecl;
import org.python.apache.xerces.impl.xs.util.XInt;
import org.python.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.python.apache.xerces.util.DOMUtil;
import org.python.apache.xerces.util.XMLSymbols;
import org.python.apache.xerces.xni.QName;
import org.w3c.dom.Element;

class XSDGroupTraverser extends XSDAbstractParticleTraverser {
   XSDGroupTraverser(XSDHandler var1, XSAttributeChecker var2) {
      super(var1, var2);
   }

   XSParticleDecl traverseLocal(Element var1, XSDocumentInfo var2, SchemaGrammar var3) {
      Object[] var4 = this.fAttrChecker.checkAttributes(var1, false, var2);
      QName var5 = (QName)var4[XSAttributeChecker.ATTIDX_REF];
      XInt var6 = (XInt)var4[XSAttributeChecker.ATTIDX_MINOCCURS];
      XInt var7 = (XInt)var4[XSAttributeChecker.ATTIDX_MAXOCCURS];
      XSGroupDecl var8 = null;
      if (var5 == null) {
         this.reportSchemaError("s4s-att-must-appear", new Object[]{"group (local)", "ref"}, var1);
      } else {
         var8 = (XSGroupDecl)this.fSchemaHandler.getGlobalDecl(var2, 4, var5, var1);
      }

      XSAnnotationImpl var9 = null;
      Element var10 = DOMUtil.getFirstChildElement(var1);
      if (var10 != null && DOMUtil.getLocalName(var10).equals(SchemaSymbols.ELT_ANNOTATION)) {
         var9 = this.traverseAnnotationDecl(var10, var4, false, var2);
         var10 = DOMUtil.getNextSiblingElement(var10);
      } else {
         String var11 = DOMUtil.getSyntheticAnnotation(var1);
         if (var11 != null) {
            var9 = this.traverseSyntheticAnnotation(var1, var11, var4, false, var2);
         }
      }

      if (var10 != null) {
         this.reportSchemaError("s4s-elt-must-match.1", new Object[]{"group (local)", "(annotation?)", DOMUtil.getLocalName(var1)}, var1);
      }

      int var15 = var6.intValue();
      int var12 = var7.intValue();
      XSParticleDecl var13 = null;
      if (var8 != null && var8.fModelGroup != null && (var15 != 0 || var12 != 0)) {
         if (this.fSchemaHandler.fDeclPool != null) {
            var13 = this.fSchemaHandler.fDeclPool.getParticleDecl();
         } else {
            var13 = new XSParticleDecl();
         }

         var13.fType = 3;
         var13.fValue = var8.fModelGroup;
         var13.fMinOccurs = var15;
         var13.fMaxOccurs = var12;
         if (var8.fModelGroup.fCompositor == 103) {
            Long var14 = (Long)var4[XSAttributeChecker.ATTIDX_FROMDEFAULT];
            var13 = this.checkOccurrences(var13, SchemaSymbols.ELT_GROUP, (Element)var1.getParentNode(), 2, var14);
         }

         if (var5 != null) {
            XSObjectListImpl var16;
            if (var9 != null) {
               var16 = new XSObjectListImpl();
               ((XSObjectListImpl)var16).addXSObject(var9);
            } else {
               var16 = XSObjectListImpl.EMPTY_LIST;
            }

            var13.fAnnotations = var16;
         } else {
            var13.fAnnotations = var8.fAnnotations;
         }
      }

      this.fAttrChecker.returnAttrArray(var4, var2);
      return var13;
   }

   XSGroupDecl traverseGlobal(Element var1, XSDocumentInfo var2, SchemaGrammar var3) {
      Object[] var4 = this.fAttrChecker.checkAttributes(var1, true, var2);
      String var5 = (String)var4[XSAttributeChecker.ATTIDX_NAME];
      if (var5 == null) {
         this.reportSchemaError("s4s-att-must-appear", new Object[]{"group (global)", "name"}, var1);
      }

      XSGroupDecl var6 = new XSGroupDecl();
      XSParticleDecl var7 = null;
      Element var8 = DOMUtil.getFirstChildElement(var1);
      XSAnnotationImpl var9 = null;
      String var11;
      if (var8 == null) {
         this.reportSchemaError("s4s-elt-must-match.2", new Object[]{"group (global)", "(annotation?, (all | choice | sequence))"}, var1);
      } else {
         String var10 = var8.getLocalName();
         if (var10.equals(SchemaSymbols.ELT_ANNOTATION)) {
            var9 = this.traverseAnnotationDecl(var8, var4, true, var2);
            var8 = DOMUtil.getNextSiblingElement(var8);
            if (var8 != null) {
               var10 = var8.getLocalName();
            }
         } else {
            var11 = DOMUtil.getSyntheticAnnotation(var1);
            if (var11 != null) {
               var9 = this.traverseSyntheticAnnotation(var1, var11, var4, false, var2);
            }
         }

         if (var8 == null) {
            this.reportSchemaError("s4s-elt-must-match.2", new Object[]{"group (global)", "(annotation?, (all | choice | sequence))"}, var1);
         } else if (var10.equals(SchemaSymbols.ELT_ALL)) {
            var7 = this.traverseAll(var8, var2, var3, 4, var6);
         } else if (var10.equals(SchemaSymbols.ELT_CHOICE)) {
            var7 = this.traverseChoice(var8, var2, var3, 4, var6);
         } else if (var10.equals(SchemaSymbols.ELT_SEQUENCE)) {
            var7 = this.traverseSequence(var8, var2, var3, 4, var6);
         } else {
            this.reportSchemaError("s4s-elt-must-match.1", new Object[]{"group (global)", "(annotation?, (all | choice | sequence))", DOMUtil.getLocalName(var8)}, var8);
         }

         if (var8 != null && DOMUtil.getNextSiblingElement(var8) != null) {
            this.reportSchemaError("s4s-elt-must-match.1", new Object[]{"group (global)", "(annotation?, (all | choice | sequence))", DOMUtil.getLocalName(DOMUtil.getNextSiblingElement(var8))}, DOMUtil.getNextSiblingElement(var8));
         }
      }

      if (var5 != null) {
         var6.fName = var5;
         var6.fTargetNamespace = var2.fTargetNamespace;
         if (var7 == null) {
            var7 = XSConstraints.getEmptySequence();
         }

         var6.fModelGroup = (XSModelGroupImpl)var7.fValue;
         XSObjectListImpl var13;
         if (var9 != null) {
            var13 = new XSObjectListImpl();
            ((XSObjectListImpl)var13).addXSObject(var9);
         } else {
            var13 = XSObjectListImpl.EMPTY_LIST;
         }

         var6.fAnnotations = var13;
         if (var3.getGlobalGroupDecl(var6.fName) == null) {
            var3.addGlobalGroupDecl(var6);
         }

         var11 = this.fSchemaHandler.schemaDocument2SystemId(var2);
         XSGroupDecl var12 = var3.getGlobalGroupDecl(var6.fName, var11);
         if (var12 == null) {
            var3.addGlobalGroupDecl(var6, var11);
         }

         if (this.fSchemaHandler.fTolerateDuplicates) {
            if (var12 != null) {
               var6 = var12;
            }

            this.fSchemaHandler.addGlobalGroupDecl(var6);
         }
      } else {
         var6 = null;
      }

      if (var6 != null) {
         Object var14 = this.fSchemaHandler.getGrpOrAttrGrpRedefinedByRestriction(4, new QName(XMLSymbols.EMPTY_STRING, var5, var5, var2.fTargetNamespace), var2, var1);
         if (var14 != null) {
            var3.addRedefinedGroupDecl(var6, (XSGroupDecl)var14, this.fSchemaHandler.element2Locator(var1));
         }
      }

      this.fAttrChecker.returnAttrArray(var4, var2);
      return var6;
   }
}
