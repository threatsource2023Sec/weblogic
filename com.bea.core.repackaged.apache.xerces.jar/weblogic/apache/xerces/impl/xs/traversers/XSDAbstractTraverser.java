package weblogic.apache.xerces.impl.xs.traversers;

import java.util.Locale;
import java.util.Vector;
import org.w3c.dom.Element;
import weblogic.apache.xerces.impl.dv.InvalidDatatypeValueException;
import weblogic.apache.xerces.impl.dv.ValidatedInfo;
import weblogic.apache.xerces.impl.dv.XSFacets;
import weblogic.apache.xerces.impl.dv.XSSimpleType;
import weblogic.apache.xerces.impl.dv.util.Base64;
import weblogic.apache.xerces.impl.dv.xs.XSSimpleTypeDecl;
import weblogic.apache.xerces.impl.validation.ValidationState;
import weblogic.apache.xerces.impl.xs.SchemaGrammar;
import weblogic.apache.xerces.impl.xs.SchemaSymbols;
import weblogic.apache.xerces.impl.xs.XSAnnotationImpl;
import weblogic.apache.xerces.impl.xs.XSAttributeGroupDecl;
import weblogic.apache.xerces.impl.xs.XSAttributeUseImpl;
import weblogic.apache.xerces.impl.xs.XSComplexTypeDecl;
import weblogic.apache.xerces.impl.xs.XSElementDecl;
import weblogic.apache.xerces.impl.xs.XSParticleDecl;
import weblogic.apache.xerces.impl.xs.XSWildcardDecl;
import weblogic.apache.xerces.impl.xs.util.XInt;
import weblogic.apache.xerces.impl.xs.util.XSObjectListImpl;
import weblogic.apache.xerces.util.DOMUtil;
import weblogic.apache.xerces.util.NamespaceSupport;
import weblogic.apache.xerces.util.SymbolTable;
import weblogic.apache.xerces.xni.QName;
import weblogic.apache.xerces.xs.XSAttributeUse;
import weblogic.apache.xerces.xs.XSObject;
import weblogic.apache.xerces.xs.XSObjectList;
import weblogic.apache.xerces.xs.XSSimpleTypeDefinition;
import weblogic.apache.xerces.xs.XSTypeDefinition;

abstract class XSDAbstractTraverser {
   protected static final String NO_NAME = "(no name)";
   protected static final int NOT_ALL_CONTEXT = 0;
   protected static final int PROCESSING_ALL_EL = 1;
   protected static final int GROUP_REF_WITH_ALL = 2;
   protected static final int CHILD_OF_GROUP = 4;
   protected static final int PROCESSING_ALL_GP = 8;
   protected XSDHandler fSchemaHandler = null;
   protected SymbolTable fSymbolTable = null;
   protected XSAttributeChecker fAttrChecker = null;
   protected boolean fValidateAnnotations = false;
   ValidationState fValidationState = new ValidationState();
   private static final XSSimpleType fQNameDV;
   private StringBuffer fPattern = new StringBuffer();
   private final XSFacets xsFacets = new XSFacets();

   XSDAbstractTraverser(XSDHandler var1, XSAttributeChecker var2) {
      this.fSchemaHandler = var1;
      this.fAttrChecker = var2;
   }

   void reset(SymbolTable var1, boolean var2, Locale var3) {
      this.fSymbolTable = var1;
      this.fValidateAnnotations = var2;
      this.fValidationState.setExtraChecking(false);
      this.fValidationState.setSymbolTable(var1);
      this.fValidationState.setLocale(var3);
   }

   XSAnnotationImpl traverseAnnotationDecl(Element var1, Object[] var2, boolean var3, XSDocumentInfo var4) {
      Object[] var5 = this.fAttrChecker.checkAttributes(var1, var3, var4);
      this.fAttrChecker.returnAttrArray(var5, var4);
      String var6 = DOMUtil.getAnnotation(var1);
      Element var7 = DOMUtil.getFirstChildElement(var1);
      if (var7 != null) {
         do {
            String var8 = DOMUtil.getLocalName(var7);
            if (!var8.equals(SchemaSymbols.ELT_APPINFO) && !var8.equals(SchemaSymbols.ELT_DOCUMENTATION)) {
               this.reportSchemaError("src-annotation", new Object[]{var8}, var7);
            } else {
               var5 = this.fAttrChecker.checkAttributes(var7, true, var4);
               this.fAttrChecker.returnAttrArray(var5, var4);
            }

            var7 = DOMUtil.getNextSiblingElement(var7);
         } while(var7 != null);
      }

      if (var6 == null) {
         return null;
      } else {
         SchemaGrammar var18 = this.fSchemaHandler.getGrammar(var4.fTargetNamespace);
         Vector var9 = (Vector)var2[XSAttributeChecker.ATTIDX_NONSCHEMA];
         if (var9 != null && !var9.isEmpty()) {
            StringBuffer var10 = new StringBuffer(64);
            var10.append(" ");
            int var11 = 0;

            int var13;
            String var14;
            while(var11 < var9.size()) {
               String var12 = (String)var9.elementAt(var11++);
               var13 = var12.indexOf(58);
               String var15;
               if (var13 == -1) {
                  var14 = "";
                  var15 = var12;
               } else {
                  var14 = var12.substring(0, var13);
                  var15 = var12.substring(var13 + 1);
               }

               String var16 = var4.fNamespaceSupport.getURI(this.fSymbolTable.addSymbol(var14));
               if (var1.getAttributeNS(var16, var15).length() != 0) {
                  ++var11;
               } else {
                  var10.append(var12).append("=\"");
                  String var17 = (String)var9.elementAt(var11++);
                  var17 = processAttValue(var17);
                  var10.append(var17).append("\" ");
               }
            }

            StringBuffer var19 = new StringBuffer(var6.length() + var10.length());
            var13 = var6.indexOf(SchemaSymbols.ELT_ANNOTATION);
            if (var13 == -1) {
               return null;
            } else {
               var13 += SchemaSymbols.ELT_ANNOTATION.length();
               var19.append(var6.substring(0, var13));
               var19.append(var10.toString());
               var19.append(var6.substring(var13, var6.length()));
               var14 = var19.toString();
               if (this.fValidateAnnotations) {
                  var4.addAnnotation(new XSAnnotationInfo(var14, var1));
               }

               return new XSAnnotationImpl(var14, var18);
            }
         } else {
            if (this.fValidateAnnotations) {
               var4.addAnnotation(new XSAnnotationInfo(var6, var1));
            }

            return new XSAnnotationImpl(var6, var18);
         }
      }
   }

   XSAnnotationImpl traverseSyntheticAnnotation(Element var1, String var2, Object[] var3, boolean var4, XSDocumentInfo var5) {
      SchemaGrammar var7 = this.fSchemaHandler.getGrammar(var5.fTargetNamespace);
      Vector var8 = (Vector)var3[XSAttributeChecker.ATTIDX_NONSCHEMA];
      if (var8 != null && !var8.isEmpty()) {
         StringBuffer var9 = new StringBuffer(64);
         var9.append(" ");
         int var10 = 0;

         int var12;
         String var13;
         while(var10 < var8.size()) {
            String var11 = (String)var8.elementAt(var10++);
            var12 = var11.indexOf(58);
            if (var12 == -1) {
               var13 = "";
            } else {
               var13 = var11.substring(0, var12);
               var11.substring(var12 + 1);
            }

            String var15 = var5.fNamespaceSupport.getURI(this.fSymbolTable.addSymbol(var13));
            var9.append(var11).append("=\"");
            String var16 = (String)var8.elementAt(var10++);
            var16 = processAttValue(var16);
            var9.append(var16).append("\" ");
         }

         StringBuffer var17 = new StringBuffer(var2.length() + var9.length());
         var12 = var2.indexOf(SchemaSymbols.ELT_ANNOTATION);
         if (var12 == -1) {
            return null;
         } else {
            var12 += SchemaSymbols.ELT_ANNOTATION.length();
            var17.append(var2.substring(0, var12));
            var17.append(var9.toString());
            var17.append(var2.substring(var12, var2.length()));
            var13 = var17.toString();
            if (this.fValidateAnnotations) {
               var5.addAnnotation(new XSAnnotationInfo(var13, var1));
            }

            return new XSAnnotationImpl(var13, var7);
         }
      } else {
         if (this.fValidateAnnotations) {
            var5.addAnnotation(new XSAnnotationInfo(var2, var1));
         }

         return new XSAnnotationImpl(var2, var7);
      }
   }

   FacetInfo traverseFacets(Element var1, XSTypeDefinition var2, XSSimpleType var3, XSDocumentInfo var4) {
      short var5 = 0;
      short var6 = 0;
      boolean var8 = this.containsQName(var3);
      Vector var9 = null;
      XSObjectListImpl var10 = null;
      XSObjectListImpl var11 = null;
      Vector var12 = var8 ? new Vector() : null;
      boolean var13 = false;
      this.xsFacets.reset();
      boolean var14 = false;
      Element var15 = (Element)var1.getParentNode();
      boolean var16 = false;
      boolean var17 = false;
      boolean var18 = false;

      while(var1 != null) {
         Object[] var19 = null;
         String var7 = DOMUtil.getLocalName(var1);
         String var20;
         if (var7.equals(SchemaSymbols.ELT_ENUMERATION)) {
            var19 = this.fAttrChecker.checkAttributes(var1, false, var4, var8);
            var20 = (String)var19[XSAttributeChecker.ATTIDX_VALUE];
            if (var20 == null) {
               this.reportSchemaError("s4s-att-must-appear", new Object[]{SchemaSymbols.ELT_ENUMERATION, SchemaSymbols.ATT_VALUE}, var1);
               this.fAttrChecker.returnAttrArray(var19, var4);
               var1 = DOMUtil.getNextSiblingElement(var1);
               continue;
            }

            NamespaceSupport var21 = (NamespaceSupport)var19[XSAttributeChecker.ATTIDX_ENUMNSDECLS];
            if (var3.getVariety() == 1 && var3.getPrimitiveKind() == 20) {
               var4.fValidationContext.setNamespaceSupport(var21);
               Object var22 = null;

               try {
                  QName var23 = (QName)fQNameDV.validate((String)var20, var4.fValidationContext, (ValidatedInfo)null);
                  var22 = this.fSchemaHandler.getGlobalDecl(var4, 6, var23, var1);
               } catch (InvalidDatatypeValueException var24) {
                  this.reportSchemaError(var24.getKey(), var24.getArgs(), var1);
               }

               if (var22 == null) {
                  this.fAttrChecker.returnAttrArray(var19, var4);
                  var1 = DOMUtil.getNextSiblingElement(var1);
                  continue;
               }

               var4.fValidationContext.setNamespaceSupport(var4.fNamespaceSupport);
            }

            if (var9 == null) {
               var9 = new Vector();
               var10 = new XSObjectListImpl();
            }

            var9.addElement(var20);
            var10.addXSObject((XSObject)null);
            if (var8) {
               var12.addElement(var21);
            }

            Element var29 = DOMUtil.getFirstChildElement(var1);
            if (var29 != null && DOMUtil.getLocalName(var29).equals(SchemaSymbols.ELT_ANNOTATION)) {
               var10.addXSObject(var10.getLength() - 1, this.traverseAnnotationDecl(var29, var19, false, var4));
               var29 = DOMUtil.getNextSiblingElement(var29);
            } else {
               String var30 = DOMUtil.getSyntheticAnnotation(var1);
               if (var30 != null) {
                  var10.addXSObject(var10.getLength() - 1, this.traverseSyntheticAnnotation(var1, var30, var19, false, var4));
               }
            }

            if (var29 != null) {
               this.reportSchemaError("s4s-elt-must-match.1", new Object[]{"enumeration", "(annotation?)", DOMUtil.getLocalName(var29)}, var29);
            }
         } else {
            String var31;
            if (var7.equals(SchemaSymbols.ELT_PATTERN)) {
               var19 = this.fAttrChecker.checkAttributes(var1, false, var4);
               var20 = (String)var19[XSAttributeChecker.ATTIDX_VALUE];
               if (var20 == null) {
                  this.reportSchemaError("s4s-att-must-appear", new Object[]{SchemaSymbols.ELT_PATTERN, SchemaSymbols.ATT_VALUE}, var1);
                  this.fAttrChecker.returnAttrArray(var19, var4);
                  var1 = DOMUtil.getNextSiblingElement(var1);
                  continue;
               }

               var14 = true;
               if (this.fPattern.length() == 0) {
                  this.fPattern.append(var20);
               } else {
                  this.fPattern.append("|");
                  this.fPattern.append(var20);
               }

               Element var27 = DOMUtil.getFirstChildElement(var1);
               if (var27 != null && DOMUtil.getLocalName(var27).equals(SchemaSymbols.ELT_ANNOTATION)) {
                  if (var11 == null) {
                     var11 = new XSObjectListImpl();
                  }

                  var11.addXSObject(this.traverseAnnotationDecl(var27, var19, false, var4));
                  var27 = DOMUtil.getNextSiblingElement(var27);
               } else {
                  var31 = DOMUtil.getSyntheticAnnotation(var1);
                  if (var31 != null) {
                     if (var11 == null) {
                        var11 = new XSObjectListImpl();
                     }

                     var11.addXSObject(this.traverseSyntheticAnnotation(var1, var31, var19, false, var4));
                  }
               }

               if (var27 != null) {
                  this.reportSchemaError("s4s-elt-must-match.1", new Object[]{"pattern", "(annotation?)", DOMUtil.getLocalName(var27)}, var27);
               }
            } else {
               short var25;
               if (var7.equals(SchemaSymbols.ELT_MINLENGTH)) {
                  var25 = 2;
               } else if (var7.equals(SchemaSymbols.ELT_MAXLENGTH)) {
                  var25 = 4;
               } else if (var7.equals(SchemaSymbols.ELT_MAXEXCLUSIVE)) {
                  var25 = 64;
               } else if (var7.equals(SchemaSymbols.ELT_MAXINCLUSIVE)) {
                  var25 = 32;
               } else if (var7.equals(SchemaSymbols.ELT_MINEXCLUSIVE)) {
                  var25 = 128;
               } else if (var7.equals(SchemaSymbols.ELT_MININCLUSIVE)) {
                  var25 = 256;
               } else if (var7.equals(SchemaSymbols.ELT_TOTALDIGITS)) {
                  var25 = 512;
               } else if (var7.equals(SchemaSymbols.ELT_FRACTIONDIGITS)) {
                  var25 = 1024;
               } else if (var7.equals(SchemaSymbols.ELT_WHITESPACE)) {
                  var25 = 16;
               } else {
                  if (!var7.equals(SchemaSymbols.ELT_LENGTH)) {
                     break;
                  }

                  var25 = 1;
               }

               var19 = this.fAttrChecker.checkAttributes(var1, false, var4);
               if ((var5 & var25) != 0) {
                  this.reportSchemaError("src-single-facet-value", new Object[]{var7}, var1);
                  this.fAttrChecker.returnAttrArray(var19, var4);
                  var1 = DOMUtil.getNextSiblingElement(var1);
                  continue;
               }

               if (var19[XSAttributeChecker.ATTIDX_VALUE] == null) {
                  if (var1.getAttributeNodeNS((String)null, "value") == null) {
                     this.reportSchemaError("s4s-att-must-appear", new Object[]{var1.getLocalName(), SchemaSymbols.ATT_VALUE}, var1);
                  }

                  this.fAttrChecker.returnAttrArray(var19, var4);
                  var1 = DOMUtil.getNextSiblingElement(var1);
                  continue;
               }

               var5 |= var25;
               if ((Boolean)var19[XSAttributeChecker.ATTIDX_FIXED]) {
                  var6 |= var25;
               }

               switch (var25) {
                  case 1:
                     this.xsFacets.length = ((XInt)var19[XSAttributeChecker.ATTIDX_VALUE]).intValue();
                     var16 = true;
                     break;
                  case 2:
                     this.xsFacets.minLength = ((XInt)var19[XSAttributeChecker.ATTIDX_VALUE]).intValue();
                     var17 = true;
                     break;
                  case 4:
                     this.xsFacets.maxLength = ((XInt)var19[XSAttributeChecker.ATTIDX_VALUE]).intValue();
                     var18 = true;
                     break;
                  case 16:
                     this.xsFacets.whiteSpace = ((XInt)var19[XSAttributeChecker.ATTIDX_VALUE]).shortValue();
                     break;
                  case 32:
                     this.xsFacets.maxInclusive = (String)var19[XSAttributeChecker.ATTIDX_VALUE];
                     break;
                  case 64:
                     this.xsFacets.maxExclusive = (String)var19[XSAttributeChecker.ATTIDX_VALUE];
                     break;
                  case 128:
                     this.xsFacets.minExclusive = (String)var19[XSAttributeChecker.ATTIDX_VALUE];
                     break;
                  case 256:
                     this.xsFacets.minInclusive = (String)var19[XSAttributeChecker.ATTIDX_VALUE];
                     break;
                  case 512:
                     this.xsFacets.totalDigits = ((XInt)var19[XSAttributeChecker.ATTIDX_VALUE]).intValue();
                     break;
                  case 1024:
                     this.xsFacets.fractionDigits = ((XInt)var19[XSAttributeChecker.ATTIDX_VALUE]).intValue();
               }

               Element var26 = DOMUtil.getFirstChildElement(var1);
               XSAnnotationImpl var28 = null;
               if (var26 != null && DOMUtil.getLocalName(var26).equals(SchemaSymbols.ELT_ANNOTATION)) {
                  var28 = this.traverseAnnotationDecl(var26, var19, false, var4);
                  var26 = DOMUtil.getNextSiblingElement(var26);
               } else {
                  var31 = DOMUtil.getSyntheticAnnotation(var1);
                  if (var31 != null) {
                     var28 = this.traverseSyntheticAnnotation(var1, var31, var19, false, var4);
                  }
               }

               switch (var25) {
                  case 1:
                     this.xsFacets.lengthAnnotation = var28;
                     break;
                  case 2:
                     this.xsFacets.minLengthAnnotation = var28;
                     break;
                  case 4:
                     this.xsFacets.maxLengthAnnotation = var28;
                     break;
                  case 16:
                     this.xsFacets.whiteSpaceAnnotation = var28;
                     break;
                  case 32:
                     this.xsFacets.maxInclusiveAnnotation = var28;
                     break;
                  case 64:
                     this.xsFacets.maxExclusiveAnnotation = var28;
                     break;
                  case 128:
                     this.xsFacets.minExclusiveAnnotation = var28;
                     break;
                  case 256:
                     this.xsFacets.minInclusiveAnnotation = var28;
                     break;
                  case 512:
                     this.xsFacets.totalDigitsAnnotation = var28;
                     break;
                  case 1024:
                     this.xsFacets.fractionDigitsAnnotation = var28;
               }

               if (var26 != null) {
                  this.reportSchemaError("s4s-elt-must-match.1", new Object[]{var7, "(annotation?)", DOMUtil.getLocalName(var26)}, var26);
               }
            }
         }

         this.fAttrChecker.returnAttrArray(var19, var4);
         var1 = DOMUtil.getNextSiblingElement(var1);
      }

      if (var9 != null) {
         var5 = (short)(var5 | 2048);
         this.xsFacets.enumeration = var9;
         this.xsFacets.enumNSDecls = var12;
         this.xsFacets.enumAnnotations = var10;
      }

      if (var14) {
         var5 = (short)(var5 | 8);
         this.xsFacets.pattern = this.fPattern.toString();
         this.xsFacets.patternAnnotations = var11;
      }

      this.fPattern.setLength(0);
      if (var9 != null) {
         if (var16) {
            this.checkEnumerationAndLengthInconsistency(var3, var9, var15, getSchemaTypeName(var2));
         }

         if (var17) {
            this.checkEnumerationAndMinLengthInconsistency(var3, var9, var15, getSchemaTypeName(var2));
         }

         if (var18) {
            this.checkEnumerationAndMaxLengthInconsistency(var3, var9, var15, getSchemaTypeName(var2));
         }
      }

      return new FacetInfo(this.xsFacets, var1, var5, var6);
   }

   public static String getSchemaTypeName(XSTypeDefinition var0) {
      String var1 = "";
      if (var0 instanceof XSSimpleTypeDefinition) {
         var1 = ((XSSimpleTypeDecl)var0).getTypeName();
      } else {
         var1 = ((XSComplexTypeDecl)var0).getTypeName();
      }

      return var1;
   }

   private void checkEnumerationAndMaxLengthInconsistency(XSSimpleType var1, Vector var2, Element var3, String var4) {
      int var5;
      String var6;
      if (SchemaSymbols.URI_SCHEMAFORSCHEMA.equals(var1.getNamespace()) && "hexBinary".equals(var1.getName())) {
         for(var5 = 0; var5 < var2.size(); ++var5) {
            var6 = (String)var2.get(var5);
            if (var6.length() / 2 > this.xsFacets.maxLength) {
               this.reportSchemaWarning("FacetsContradict", new Object[]{var6, SchemaSymbols.ELT_MAXLENGTH, var4}, var3);
            }
         }
      } else if (SchemaSymbols.URI_SCHEMAFORSCHEMA.equals(var1.getNamespace()) && "base64Binary".equals(var1.getName())) {
         for(var5 = 0; var5 < var2.size(); ++var5) {
            var6 = (String)var2.get(var5);
            byte[] var7 = Base64.decode(var6);
            if (var7 != null && (new String(var7)).length() > this.xsFacets.maxLength) {
               this.reportSchemaWarning("FacetsContradict", new Object[]{var6, SchemaSymbols.ELT_MAXLENGTH, var4}, var3);
            }
         }
      } else {
         for(var5 = 0; var5 < var2.size(); ++var5) {
            var6 = (String)var2.get(var5);
            if (var6.length() > this.xsFacets.maxLength) {
               this.reportSchemaWarning("FacetsContradict", new Object[]{var6, SchemaSymbols.ELT_MAXLENGTH, var4}, var3);
            }
         }
      }

   }

   private void checkEnumerationAndMinLengthInconsistency(XSSimpleType var1, Vector var2, Element var3, String var4) {
      int var5;
      String var6;
      if (SchemaSymbols.URI_SCHEMAFORSCHEMA.equals(var1.getNamespace()) && "hexBinary".equals(var1.getName())) {
         for(var5 = 0; var5 < var2.size(); ++var5) {
            var6 = (String)var2.get(var5);
            if (var6.length() / 2 < this.xsFacets.minLength) {
               this.reportSchemaWarning("FacetsContradict", new Object[]{var6, SchemaSymbols.ELT_MINLENGTH, var4}, var3);
            }
         }
      } else if (SchemaSymbols.URI_SCHEMAFORSCHEMA.equals(var1.getNamespace()) && "base64Binary".equals(var1.getName())) {
         for(var5 = 0; var5 < var2.size(); ++var5) {
            var6 = (String)var2.get(var5);
            byte[] var7 = Base64.decode(var6);
            if (var7 != null && (new String(var7)).length() < this.xsFacets.minLength) {
               this.reportSchemaWarning("FacetsContradict", new Object[]{var6, SchemaSymbols.ELT_MINLENGTH, var4}, var3);
            }
         }
      } else {
         for(var5 = 0; var5 < var2.size(); ++var5) {
            var6 = (String)var2.get(var5);
            if (var6.length() < this.xsFacets.minLength) {
               this.reportSchemaWarning("FacetsContradict", new Object[]{var6, SchemaSymbols.ELT_MINLENGTH, var4}, var3);
            }
         }
      }

   }

   private void checkEnumerationAndLengthInconsistency(XSSimpleType var1, Vector var2, Element var3, String var4) {
      int var5;
      String var6;
      if (SchemaSymbols.URI_SCHEMAFORSCHEMA.equals(var1.getNamespace()) && "hexBinary".equals(var1.getName())) {
         for(var5 = 0; var5 < var2.size(); ++var5) {
            var6 = (String)var2.get(var5);
            if (var6.length() / 2 != this.xsFacets.length) {
               this.reportSchemaWarning("FacetsContradict", new Object[]{var6, SchemaSymbols.ELT_LENGTH, var4}, var3);
            }
         }
      } else if (SchemaSymbols.URI_SCHEMAFORSCHEMA.equals(var1.getNamespace()) && "base64Binary".equals(var1.getName())) {
         for(var5 = 0; var5 < var2.size(); ++var5) {
            var6 = (String)var2.get(var5);
            byte[] var7 = Base64.decode(var6);
            if (var7 != null && (new String(var7)).length() != this.xsFacets.length) {
               this.reportSchemaWarning("FacetsContradict", new Object[]{var6, SchemaSymbols.ELT_LENGTH, var4}, var3);
            }
         }
      } else {
         for(var5 = 0; var5 < var2.size(); ++var5) {
            var6 = (String)var2.get(var5);
            if (var6.length() != this.xsFacets.length) {
               this.reportSchemaWarning("FacetsContradict", new Object[]{var6, SchemaSymbols.ELT_LENGTH, var4}, var3);
            }
         }
      }

   }

   private boolean containsQName(XSSimpleType var1) {
      if (var1.getVariety() == 1) {
         short var4 = var1.getPrimitiveKind();
         return var4 == 18 || var4 == 20;
      } else if (var1.getVariety() == 2) {
         return this.containsQName((XSSimpleType)var1.getItemType());
      } else {
         if (var1.getVariety() == 3) {
            XSObjectList var2 = var1.getMemberTypes();

            for(int var3 = 0; var3 < var2.getLength(); ++var3) {
               if (this.containsQName((XSSimpleType)var2.item(var3))) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   Element traverseAttrsAndAttrGrps(Element var1, XSAttributeGroupDecl var2, XSDocumentInfo var3, SchemaGrammar var4, XSComplexTypeDecl var5) {
      Element var6 = null;
      XSAttributeGroupDecl var7 = null;
      XSAttributeUseImpl var8 = null;
      XSAttributeUse var9 = null;

      String var10;
      String var12;
      String var13;
      for(var6 = var1; var6 != null; var6 = DOMUtil.getNextSiblingElement(var6)) {
         var10 = DOMUtil.getLocalName(var6);
         if (var10.equals(SchemaSymbols.ELT_ATTRIBUTE)) {
            var8 = this.fSchemaHandler.fAttributeTraverser.traverseLocal(var6, var3, var4, var5);
            if (var8 != null) {
               if (var8.fUse == 2) {
                  var2.addAttributeUse(var8);
               } else {
                  var9 = var2.getAttributeUseNoProhibited(var8.fAttrDecl.getNamespace(), var8.fAttrDecl.getName());
                  String var11;
                  if (var9 == null) {
                     var11 = var2.addAttributeUse(var8);
                     if (var11 != null) {
                        var12 = var5 == null ? "ag-props-correct.3" : "ct-props-correct.5";
                        var13 = var5 == null ? var2.fName : var5.getName();
                        this.reportSchemaError(var12, new Object[]{var13, var8.fAttrDecl.getName(), var11}, var6);
                     }
                  } else if (var9 != var8) {
                     var11 = var5 == null ? "ag-props-correct.2" : "ct-props-correct.4";
                     var12 = var5 == null ? var2.fName : var5.getName();
                     this.reportSchemaError(var11, new Object[]{var12, var8.fAttrDecl.getName()}, var6);
                  }
               }
            }
         } else {
            if (!var10.equals(SchemaSymbols.ELT_ATTRIBUTEGROUP)) {
               break;
            }

            var7 = this.fSchemaHandler.fAttributeGroupTraverser.traverseLocal(var6, var3, var4);
            if (var7 != null) {
               XSObjectList var18 = var7.getAttributeUses();
               int var21 = var18.getLength();

               String var15;
               String var16;
               for(int var14 = 0; var14 < var21; ++var14) {
                  XSAttributeUseImpl var20 = (XSAttributeUseImpl)var18.item(var14);
                  if (var20.fUse == 2) {
                     var2.addAttributeUse(var20);
                  } else {
                     var9 = var2.getAttributeUseNoProhibited(var20.fAttrDecl.getNamespace(), var20.fAttrDecl.getName());
                     if (var9 == null) {
                        var15 = var2.addAttributeUse(var20);
                        if (var15 != null) {
                           var16 = var5 == null ? "ag-props-correct.3" : "ct-props-correct.5";
                           String var17 = var5 == null ? var2.fName : var5.getName();
                           this.reportSchemaError(var16, new Object[]{var17, var20.fAttrDecl.getName(), var15}, var6);
                        }
                     } else if (var20 != var9) {
                        var15 = var5 == null ? "ag-props-correct.2" : "ct-props-correct.4";
                        var16 = var5 == null ? var2.fName : var5.getName();
                        this.reportSchemaError(var15, new Object[]{var16, var20.fAttrDecl.getName()}, var6);
                     }
                  }
               }

               if (var7.fAttributeWC != null) {
                  if (var2.fAttributeWC == null) {
                     var2.fAttributeWC = var7.fAttributeWC;
                  } else {
                     var2.fAttributeWC = var2.fAttributeWC.performIntersectionWith(var7.fAttributeWC, var2.fAttributeWC.fProcessContents);
                     if (var2.fAttributeWC == null) {
                        var15 = var5 == null ? "src-attribute_group.2" : "src-ct.4";
                        var16 = var5 == null ? var2.fName : var5.getName();
                        this.reportSchemaError(var15, new Object[]{var16}, var6);
                     }
                  }
               }
            }
         }
      }

      if (var6 != null) {
         var10 = DOMUtil.getLocalName(var6);
         if (var10.equals(SchemaSymbols.ELT_ANYATTRIBUTE)) {
            XSWildcardDecl var19 = this.fSchemaHandler.fWildCardTraverser.traverseAnyAttribute(var6, var3, var4);
            if (var2.fAttributeWC == null) {
               var2.fAttributeWC = var19;
            } else {
               var2.fAttributeWC = var19.performIntersectionWith(var2.fAttributeWC, var19.fProcessContents);
               if (var2.fAttributeWC == null) {
                  var12 = var5 == null ? "src-attribute_group.2" : "src-ct.4";
                  var13 = var5 == null ? var2.fName : var5.getName();
                  this.reportSchemaError(var12, new Object[]{var13}, var6);
               }
            }

            var6 = DOMUtil.getNextSiblingElement(var6);
         }
      }

      return var6;
   }

   void reportSchemaError(String var1, Object[] var2, Element var3) {
      this.fSchemaHandler.reportSchemaError(var1, var2, var3);
   }

   void reportSchemaWarning(String var1, Object[] var2, Element var3) {
      this.fSchemaHandler.reportSchemaWarning(var1, var2, var3);
   }

   void checkNotationType(String var1, XSTypeDefinition var2, Element var3) {
      if (var2.getTypeCategory() == 16 && ((XSSimpleType)var2).getVariety() == 1 && ((XSSimpleType)var2).getPrimitiveKind() == 20 && (((XSSimpleType)var2).getDefinedFacets() & 2048) == 0) {
         this.reportSchemaError("enumeration-required-notation", new Object[]{var2.getName(), var1, DOMUtil.getLocalName(var3)}, var3);
      }

   }

   protected XSParticleDecl checkOccurrences(XSParticleDecl var1, String var2, Element var3, int var4, long var5) {
      int var7 = var1.fMinOccurs;
      int var8 = var1.fMaxOccurs;
      boolean var9 = (var5 & (long)(1 << XSAttributeChecker.ATTIDX_MINOCCURS)) != 0L;
      boolean var10 = (var5 & (long)(1 << XSAttributeChecker.ATTIDX_MAXOCCURS)) != 0L;
      boolean var11 = (var4 & 1) != 0;
      boolean var12 = (var4 & 8) != 0;
      boolean var13 = (var4 & 2) != 0;
      boolean var14 = (var4 & 4) != 0;
      if (var14) {
         Object[] var15;
         if (!var9) {
            var15 = new Object[]{var2, "minOccurs"};
            this.reportSchemaError("s4s-att-not-allowed", var15, var3);
            var7 = 1;
         }

         if (!var10) {
            var15 = new Object[]{var2, "maxOccurs"};
            this.reportSchemaError("s4s-att-not-allowed", var15, var3);
            var8 = 1;
         }
      }

      if (var7 == 0 && var8 == 0) {
         var1.fType = 0;
         return null;
      } else {
         if (var11) {
            if (var8 != 1) {
               this.reportSchemaError("cos-all-limited.2", new Object[]{var8 == -1 ? "unbounded" : Integer.toString(var8), ((XSElementDecl)var1.fValue).getName()}, var3);
               var8 = 1;
               if (var7 > 1) {
                  var7 = 1;
               }
            }
         } else if ((var12 || var13) && var8 != 1) {
            this.reportSchemaError("cos-all-limited.1.2", (Object[])null, var3);
            if (var7 > 1) {
               var7 = 1;
            }

            var8 = 1;
         }

         var1.fMinOccurs = var7;
         var1.fMaxOccurs = var8;
         return var1;
      }
   }

   private static String processAttValue(String var0) {
      int var1 = var0.length();

      for(int var2 = 0; var2 < var1; ++var2) {
         char var3 = var0.charAt(var2);
         if (var3 == '"' || var3 == '<' || var3 == '&' || var3 == '\t' || var3 == '\n' || var3 == '\r') {
            return escapeAttValue(var0, var2);
         }
      }

      return var0;
   }

   private static String escapeAttValue(String var0, int var1) {
      int var3 = var0.length();
      StringBuffer var4 = new StringBuffer(var3);
      var4.append(var0.substring(0, var1));

      for(int var2 = var1; var2 < var3; ++var2) {
         char var5 = var0.charAt(var2);
         if (var5 == '"') {
            var4.append("&quot;");
         } else if (var5 == '<') {
            var4.append("&lt;");
         } else if (var5 == '&') {
            var4.append("&amp;");
         } else if (var5 == '\t') {
            var4.append("&#x9;");
         } else if (var5 == '\n') {
            var4.append("&#xA;");
         } else if (var5 == '\r') {
            var4.append("&#xD;");
         } else {
            var4.append(var5);
         }
      }

      return var4.toString();
   }

   static {
      fQNameDV = (XSSimpleType)SchemaGrammar.SG_SchemaNS.getGlobalTypeDecl("QName");
   }

   static final class FacetInfo {
      final XSFacets facetdata;
      final Element nodeAfterFacets;
      final short fPresentFacets;
      final short fFixedFacets;

      FacetInfo(XSFacets var1, Element var2, short var3, short var4) {
         this.facetdata = var1;
         this.nodeAfterFacets = var2;
         this.fPresentFacets = var3;
         this.fFixedFacets = var4;
      }
   }
}
