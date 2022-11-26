package org.apache.xmlbeans.impl.schema;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.namespace.QName;
import org.apache.xmlbeans.QNameSet;
import org.apache.xmlbeans.QNameSetBuilder;
import org.apache.xmlbeans.QNameSetSpecification;
import org.apache.xmlbeans.SchemaAttributeModel;
import org.apache.xmlbeans.SchemaField;
import org.apache.xmlbeans.SchemaLocalAttribute;
import org.apache.xmlbeans.SchemaLocalElement;
import org.apache.xmlbeans.SchemaParticle;
import org.apache.xmlbeans.SchemaProperty;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlInteger;
import org.apache.xmlbeans.XmlNonNegativeInteger;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.common.QNameHelper;
import org.apache.xmlbeans.impl.xb.xsdschema.AllNNI;
import org.apache.xmlbeans.impl.xb.xsdschema.AnyDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.ComplexContentDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.ComplexRestrictionType;
import org.apache.xmlbeans.impl.xb.xsdschema.ComplexType;
import org.apache.xmlbeans.impl.xb.xsdschema.ExtensionType;
import org.apache.xmlbeans.impl.xb.xsdschema.Group;
import org.apache.xmlbeans.impl.xb.xsdschema.LocalElement;
import org.apache.xmlbeans.impl.xb.xsdschema.LocalSimpleType;
import org.apache.xmlbeans.impl.xb.xsdschema.NamespaceList;
import org.apache.xmlbeans.impl.xb.xsdschema.SchemaDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.SimpleContentDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.SimpleExtensionType;
import org.apache.xmlbeans.impl.xb.xsdschema.SimpleRestrictionType;
import org.apache.xmlbeans.impl.xb.xsdschema.Wildcard;

public class StscComplexTypeResolver {
   private static final int MODEL_GROUP_CODE = 100;
   private static CodeForNameEntry[] particleCodes = new CodeForNameEntry[]{new CodeForNameEntry(QNameHelper.forLNS("all", "http://www.w3.org/2001/XMLSchema"), 1), new CodeForNameEntry(QNameHelper.forLNS("sequence", "http://www.w3.org/2001/XMLSchema"), 3), new CodeForNameEntry(QNameHelper.forLNS("choice", "http://www.w3.org/2001/XMLSchema"), 2), new CodeForNameEntry(QNameHelper.forLNS("element", "http://www.w3.org/2001/XMLSchema"), 4), new CodeForNameEntry(QNameHelper.forLNS("any", "http://www.w3.org/2001/XMLSchema"), 5), new CodeForNameEntry(QNameHelper.forLNS("group", "http://www.w3.org/2001/XMLSchema"), 100)};
   private static Map particleCodeMap = buildParticleCodeMap();
   private static final int ATTRIBUTE_CODE = 100;
   private static final int ATTRIBUTE_GROUP_CODE = 101;
   private static final int ANY_ATTRIBUTE_CODE = 102;
   private static CodeForNameEntry[] attributeCodes = new CodeForNameEntry[]{new CodeForNameEntry(QNameHelper.forLNS("attribute", "http://www.w3.org/2001/XMLSchema"), 100), new CodeForNameEntry(QNameHelper.forLNS("attributeGroup", "http://www.w3.org/2001/XMLSchema"), 101), new CodeForNameEntry(QNameHelper.forLNS("anyAttribute", "http://www.w3.org/2001/XMLSchema"), 102)};
   private static Map attributeCodeMap = buildAttributeCodeMap();

   public static Group getContentModel(ComplexType parseCt) {
      if (parseCt.getAll() != null) {
         return parseCt.getAll();
      } else if (parseCt.getSequence() != null) {
         return parseCt.getSequence();
      } else if (parseCt.getChoice() != null) {
         return parseCt.getChoice();
      } else {
         return parseCt.getGroup() != null ? parseCt.getGroup() : null;
      }
   }

   public static Group getContentModel(ComplexRestrictionType parseRest) {
      if (parseRest.getAll() != null) {
         return parseRest.getAll();
      } else if (parseRest.getSequence() != null) {
         return parseRest.getSequence();
      } else if (parseRest.getChoice() != null) {
         return parseRest.getChoice();
      } else {
         return parseRest.getGroup() != null ? parseRest.getGroup() : null;
      }
   }

   public static Group getContentModel(ExtensionType parseExt) {
      if (parseExt.getAll() != null) {
         return parseExt.getAll();
      } else if (parseExt.getSequence() != null) {
         return parseExt.getSequence();
      } else if (parseExt.getChoice() != null) {
         return parseExt.getChoice();
      } else {
         return parseExt.getGroup() != null ? parseExt.getGroup() : null;
      }
   }

   static SchemaDocument.Schema getSchema(XmlObject o) {
      XmlCursor c = o.newCursor();

      try {
         while(c.toParent()) {
            o = c.getObject();
            if (o.schemaType().equals(SchemaDocument.Schema.type)) {
               SchemaDocument.Schema var2 = (SchemaDocument.Schema)o;
               return var2;
            }
         }
      } finally {
         c.dispose();
      }

      return null;
   }

   public static void resolveComplexType(SchemaTypeImpl sImpl) {
      ComplexType parseCt = (ComplexType)sImpl.getParseObject();
      StscState state = StscState.get();
      SchemaDocument.Schema schema = getSchema(parseCt);
      boolean abs = parseCt.isSetAbstract() ? parseCt.getAbstract() : false;
      boolean finalExt = false;
      boolean finalRest = false;
      boolean finalList = false;
      boolean finalUnion = false;
      Object ds = null;
      if (parseCt.isSetFinal()) {
         ds = parseCt.getFinal();
      } else if (schema != null && schema.isSetFinalDefault()) {
         ds = schema.getFinalDefault();
      }

      if (ds != null) {
         if (ds instanceof String && ds.equals("#all")) {
            finalUnion = true;
            finalList = true;
            finalRest = true;
            finalExt = true;
         } else if (ds instanceof List) {
            if (((List)ds).contains("extension")) {
               finalExt = true;
            }

            if (((List)ds).contains("restriction")) {
               finalRest = true;
            }
         }
      }

      sImpl.setAbstractFinal(abs, finalExt, finalRest, finalList, finalUnion);
      boolean blockExt = false;
      boolean blockRest = false;
      Object block = null;
      if (parseCt.isSetBlock()) {
         block = parseCt.getBlock();
      } else if (schema != null && schema.isSetBlockDefault()) {
         block = schema.getBlockDefault();
      }

      if (block != null) {
         if (block instanceof String && block.equals("#all")) {
            blockRest = true;
            blockExt = true;
         } else if (block instanceof List) {
            if (((List)block).contains("extension")) {
               blockExt = true;
            }

            if (((List)block).contains("restriction")) {
               blockRest = true;
            }
         }
      }

      sImpl.setBlock(blockExt, blockRest);
      ComplexContentDocument.ComplexContent parseCc = parseCt.getComplexContent();
      SimpleContentDocument.SimpleContent parseSc = parseCt.getSimpleContent();
      Group parseGroup = getContentModel(parseCt);
      int count = (parseCc != null ? 1 : 0) + (parseSc != null ? 1 : 0) + (parseGroup != null ? 1 : 0);
      if (count > 1) {
         state.error("A complex type must define either a content model, or a simpleContent or complexContent derivation: more than one found.", 26, parseCt);
         parseGroup = null;
         if (parseCc != null && parseSc != null) {
            parseSc = null;
         }
      }

      if (parseCc != null) {
         if (parseCc.getExtension() != null && parseCc.getRestriction() != null) {
            state.error("Restriction conflicts with extension", 26, parseCc.getRestriction());
         }

         boolean mixed = parseCc.isSetMixed() ? parseCc.getMixed() : parseCt.getMixed();
         if (parseCc.getExtension() != null) {
            resolveCcExtension(sImpl, parseCc.getExtension(), mixed);
         } else if (parseCc.getRestriction() != null) {
            resolveCcRestriction(sImpl, parseCc.getRestriction(), mixed);
         } else {
            state.error("Missing restriction or extension", 27, parseCc);
            resolveErrorType(sImpl);
         }

      } else if (parseSc != null) {
         if (parseSc.getExtension() != null && parseSc.getRestriction() != null) {
            state.error("Restriction conflicts with extension", 26, parseSc.getRestriction());
         }

         if (parseSc.getExtension() != null) {
            resolveScExtension(sImpl, parseSc.getExtension());
         } else if (parseSc.getRestriction() != null) {
            resolveScRestriction(sImpl, parseSc.getRestriction());
         } else {
            state.error("Missing restriction or extension", 27, parseSc);
            resolveErrorType(sImpl);
         }

      } else {
         resolveBasicComplexType(sImpl);
      }
   }

   static void resolveErrorType(SchemaTypeImpl sImpl) {
      throw new RuntimeException("This type of error recovery not yet implemented.");
   }

   private static SchemaType.Ref[] makeRefArray(Collection typeList) {
      SchemaType.Ref[] result = new SchemaType.Ref[typeList.size()];
      int j = 0;

      for(Iterator i = typeList.iterator(); i.hasNext(); ++j) {
         result[j] = ((SchemaType)i.next()).getRef();
      }

      return result;
   }

   static void resolveBasicComplexType(SchemaTypeImpl sImpl) {
      List anonymousTypes = new ArrayList();
      ComplexType parseTree = (ComplexType)sImpl.getParseObject();
      String targetNamespace = sImpl.getTargetNamespace();
      boolean chameleon = sImpl.getChameleonNamespace() != null;
      Group parseGroup = getContentModel(parseTree);
      if (sImpl.isRedefinition()) {
         StscState.get().error("src-redefine.5a", new Object[]{"<complexType>"}, parseTree);
      }

      int particleCode = translateParticleCode(parseGroup);
      Map elementModel = new LinkedHashMap();
      SchemaParticle contentModel = translateContentModel(sImpl, parseGroup, targetNamespace, chameleon, sImpl.getElemFormDefault(), sImpl.getAttFormDefault(), particleCode, anonymousTypes, elementModel, false, (RedefinitionForGroup)null);
      boolean isAll = contentModel != null && contentModel.getParticleType() == 1;
      SchemaAttributeModelImpl attrModel = new SchemaAttributeModelImpl();
      translateAttributeModel(parseTree, targetNamespace, chameleon, sImpl.getAttFormDefault(), anonymousTypes, sImpl, (Set)null, attrModel, (SchemaType)null, true, (SchemaAttributeGroupImpl)null);
      WildcardResult wcElt = summarizeEltWildcards(contentModel);
      WildcardResult wcAttr = summarizeAttrWildcards(attrModel);
      if (contentModel != null) {
         buildStateMachine(contentModel);
         if (!StscState.get().noUpa() && !((SchemaParticleImpl)contentModel).isDeterministic()) {
            StscState.get().error("cos-nonambig", (Object[])null, parseGroup);
         }
      }

      Map elementPropertyModel = buildContentPropertyModelByQName(contentModel, sImpl);
      Map attributePropertyModel = buildAttributePropertyModelByQName(attrModel, sImpl);
      int complexVariety = parseTree.getMixed() ? 4 : (contentModel == null ? 1 : 3);
      sImpl.setBaseTypeRef(BuiltinSchemaTypeSystem.ST_ANY_TYPE.getRef());
      sImpl.setBaseDepth(BuiltinSchemaTypeSystem.ST_ANY_TYPE.getBaseDepth() + 1);
      sImpl.setDerivationType(2);
      sImpl.setComplexTypeVariety(complexVariety);
      sImpl.setContentModel(contentModel, attrModel, elementPropertyModel, attributePropertyModel, isAll);
      sImpl.setAnonymousTypeRefs(makeRefArray(anonymousTypes));
      sImpl.setWildcardSummary(wcElt.typedWildcards, wcElt.hasWildcards, wcAttr.typedWildcards, wcAttr.hasWildcards);
   }

   static void resolveCcRestriction(SchemaTypeImpl sImpl, ComplexRestrictionType parseTree, boolean mixed) {
      StscState state = StscState.get();
      String targetNamespace = sImpl.getTargetNamespace();
      boolean chameleon = sImpl.getChameleonNamespace() != null;
      SchemaTypeImpl baseType;
      if (parseTree.getBase() == null) {
         state.error("A complexContent must define a base type", 28, parseTree);
         baseType = null;
      } else {
         if (sImpl.isRedefinition()) {
            baseType = state.findRedefinedGlobalType(parseTree.getBase(), sImpl.getChameleonNamespace(), sImpl);
            if (baseType != null && !baseType.getName().equals(sImpl.getName())) {
               state.error("src-redefine.5b", new Object[]{"<complexType>", QNameHelper.pretty(baseType.getName()), QNameHelper.pretty(sImpl.getName())}, parseTree);
            }
         } else {
            baseType = state.findGlobalType(parseTree.getBase(), sImpl.getChameleonNamespace(), targetNamespace);
         }

         if (baseType == null) {
            state.notFoundError(parseTree.getBase(), 0, parseTree.xgetBase(), true);
         }
      }

      if (baseType == null) {
         baseType = BuiltinSchemaTypeSystem.ST_ANY_TYPE;
      }

      if (baseType != null && baseType.finalRestriction()) {
         state.error("derivation-ok-restriction.1", new Object[]{QNameHelper.pretty(baseType.getName()), QNameHelper.pretty(sImpl.getName())}, parseTree.xgetBase());
      }

      if (baseType != null && !StscResolver.resolveType((SchemaTypeImpl)baseType)) {
         baseType = null;
      }

      List anonymousTypes = new ArrayList();
      Group parseEg = getContentModel(parseTree);
      int particleCode = translateParticleCode(parseEg);
      Map elementModel = new LinkedHashMap();
      SchemaParticle contentModel = translateContentModel(sImpl, parseEg, targetNamespace, chameleon, sImpl.getElemFormDefault(), sImpl.getAttFormDefault(), particleCode, anonymousTypes, elementModel, false, (RedefinitionForGroup)null);
      boolean isAll = contentModel != null && contentModel.getParticleType() == 1;
      SchemaAttributeModelImpl attrModel;
      if (baseType == null) {
         attrModel = new SchemaAttributeModelImpl();
      } else {
         attrModel = new SchemaAttributeModelImpl(baseType.getAttributeModel());
      }

      translateAttributeModel(parseTree, targetNamespace, chameleon, sImpl.getAttFormDefault(), anonymousTypes, sImpl, (Set)null, attrModel, baseType, false, (SchemaAttributeGroupImpl)null);
      WildcardResult wcElt = summarizeEltWildcards(contentModel);
      WildcardResult wcAttr = summarizeAttrWildcards(attrModel);
      if (contentModel != null) {
         buildStateMachine(contentModel);
         if (!StscState.get().noUpa() && !((SchemaParticleImpl)contentModel).isDeterministic()) {
            StscState.get().error("cos-nonambig", (Object[])null, parseEg);
         }
      }

      Map elementPropertyModel = buildContentPropertyModelByQName(contentModel, sImpl);
      Map attributePropertyModel = buildAttributePropertyModelByQName(attrModel, sImpl);
      int complexVariety = mixed ? 4 : (contentModel == null ? 1 : 3);
      sImpl.setBaseTypeRef(baseType.getRef());
      sImpl.setBaseDepth(((SchemaTypeImpl)baseType).getBaseDepth() + 1);
      sImpl.setDerivationType(1);
      sImpl.setComplexTypeVariety(complexVariety);
      sImpl.setContentModel(contentModel, attrModel, elementPropertyModel, attributePropertyModel, isAll);
      sImpl.setAnonymousTypeRefs(makeRefArray(anonymousTypes));
      sImpl.setWildcardSummary(wcElt.typedWildcards, wcElt.hasWildcards, wcAttr.typedWildcards, wcAttr.hasWildcards);
   }

   static Map extractElementModel(SchemaType sType) {
      Map elementModel = new HashMap();
      if (sType != null) {
         SchemaProperty[] sProps = sType.getProperties();

         for(int i = 0; i < sProps.length; ++i) {
            if (!sProps[i].isAttribute()) {
               elementModel.put(sProps[i].getName(), sProps[i].getType());
            }
         }
      }

      return elementModel;
   }

   static void resolveCcExtension(SchemaTypeImpl sImpl, ExtensionType parseTree, boolean mixed) {
      StscState state = StscState.get();
      String targetNamespace = sImpl.getTargetNamespace();
      boolean chameleon = sImpl.getChameleonNamespace() != null;
      Object baseType;
      if (parseTree.getBase() == null) {
         state.error("A complexContent must define a base type", 28, parseTree);
         baseType = null;
      } else {
         if (sImpl.isRedefinition()) {
            baseType = state.findRedefinedGlobalType(parseTree.getBase(), sImpl.getChameleonNamespace(), sImpl);
            if (baseType != null && !((SchemaType)baseType).getName().equals(sImpl.getName())) {
               state.error("src-redefine.5b", new Object[]{"<complexType>", QNameHelper.pretty(((SchemaType)baseType).getName()), QNameHelper.pretty(sImpl.getName())}, parseTree);
            }
         } else {
            baseType = state.findGlobalType(parseTree.getBase(), sImpl.getChameleonNamespace(), targetNamespace);
         }

         if (baseType == null) {
            state.notFoundError(parseTree.getBase(), 0, parseTree.xgetBase(), true);
         }
      }

      if (baseType != null && !StscResolver.resolveType((SchemaTypeImpl)baseType)) {
         baseType = null;
      }

      if (baseType != null && ((SchemaType)baseType).isSimpleType()) {
         state.recover("src-ct.1", new Object[]{QNameHelper.pretty(((SchemaType)baseType).getName())}, parseTree.xgetBase());
         baseType = null;
      }

      if (baseType != null && ((SchemaType)baseType).finalExtension()) {
         state.error("cos-ct-extends.1.1", new Object[]{QNameHelper.pretty(((SchemaType)baseType).getName()), QNameHelper.pretty(sImpl.getName())}, parseTree.xgetBase());
      }

      SchemaParticle baseContentModel = baseType == null ? null : ((SchemaType)baseType).getContentModel();
      List anonymousTypes = new ArrayList();
      Map baseElementModel = extractElementModel((SchemaType)baseType);
      Group parseEg = getContentModel(parseTree);
      if (baseType != null && ((SchemaType)baseType).getContentType() == 2) {
         if (parseEg == null) {
            resolveScExtensionPart2(sImpl, (SchemaType)baseType, parseTree, targetNamespace, chameleon);
            return;
         }

         state.recover("cos-ct-extends.1.4.1", new Object[]{QNameHelper.pretty(((SchemaType)baseType).getName())}, parseTree.xgetBase());
         baseType = null;
      }

      SchemaParticle extensionModel = translateContentModel(sImpl, parseEg, targetNamespace, chameleon, sImpl.getElemFormDefault(), sImpl.getAttFormDefault(), translateParticleCode(parseEg), anonymousTypes, baseElementModel, false, (RedefinitionForGroup)null);
      if (extensionModel == null && !mixed) {
         mixed = baseType != null && ((SchemaType)baseType).getContentType() == 4;
      }

      if (baseType != null && ((SchemaType)baseType).getContentType() != 1 && ((SchemaType)baseType).getContentType() == 4 != mixed) {
         state.error("cos-ct-extends.1.4.2.2", (Object[])null, parseTree.xgetBase());
      }

      if (baseType != null && ((SchemaType)baseType).hasAllContent() && extensionModel != null) {
         state.error("Cannot extend a type with 'all' content model", 42, parseTree.xgetBase());
         extensionModel = null;
      }

      SchemaParticle contentModel = extendContentModel(baseContentModel, extensionModel, parseTree);
      boolean isAll = contentModel != null && contentModel.getParticleType() == 1;
      SchemaAttributeModelImpl attrModel;
      if (baseType == null) {
         attrModel = new SchemaAttributeModelImpl();
      } else {
         attrModel = new SchemaAttributeModelImpl(((SchemaType)baseType).getAttributeModel());
      }

      translateAttributeModel(parseTree, targetNamespace, chameleon, sImpl.getAttFormDefault(), anonymousTypes, sImpl, (Set)null, attrModel, (SchemaType)baseType, true, (SchemaAttributeGroupImpl)null);
      WildcardResult wcElt = summarizeEltWildcards(contentModel);
      WildcardResult wcAttr = summarizeAttrWildcards(attrModel);
      if (contentModel != null) {
         buildStateMachine(contentModel);
         if (!StscState.get().noUpa() && !((SchemaParticleImpl)contentModel).isDeterministic()) {
            StscState.get().error("cos-nonambig", (Object[])null, parseEg);
         }
      }

      Map elementPropertyModel = buildContentPropertyModelByQName(contentModel, sImpl);
      Map attributePropertyModel = buildAttributePropertyModelByQName(attrModel, sImpl);
      int complexVariety;
      if (contentModel == null && baseType != null && ((SchemaType)baseType).getContentType() == 2) {
         complexVariety = 2;
         sImpl.setContentBasedOnTypeRef(((SchemaType)baseType).getContentBasedOnType().getRef());
      } else {
         complexVariety = mixed ? 4 : (contentModel == null ? 1 : 3);
      }

      if (baseType == null) {
         baseType = XmlObject.type;
      }

      sImpl.setBaseTypeRef(((SchemaType)baseType).getRef());
      sImpl.setBaseDepth(((SchemaTypeImpl)baseType).getBaseDepth() + 1);
      sImpl.setDerivationType(2);
      sImpl.setComplexTypeVariety(complexVariety);
      sImpl.setContentModel(contentModel, attrModel, elementPropertyModel, attributePropertyModel, isAll);
      sImpl.setAnonymousTypeRefs(makeRefArray(anonymousTypes));
      sImpl.setWildcardSummary(wcElt.typedWildcards, wcElt.hasWildcards, wcAttr.typedWildcards, wcAttr.hasWildcards);
   }

   static void resolveScRestriction(SchemaTypeImpl sImpl, SimpleRestrictionType parseTree) {
      SchemaType contentType = null;
      StscState state = StscState.get();
      String targetNamespace = sImpl.getTargetNamespace();
      boolean chameleon = sImpl.getChameleonNamespace() != null;
      List anonymousTypes = new ArrayList();
      if (parseTree.getSimpleType() != null) {
         LocalSimpleType typedef = parseTree.getSimpleType();
         SchemaTypeImpl anonType = StscTranslator.translateAnonymousSimpleType(typedef, targetNamespace, chameleon, sImpl.getElemFormDefault(), sImpl.getAttFormDefault(), anonymousTypes, sImpl);
         contentType = anonType;
      }

      SchemaTypeImpl baseType;
      if (parseTree.getBase() == null) {
         state.error("A simpleContent restriction must define a base type", 28, parseTree);
         baseType = BuiltinSchemaTypeSystem.ST_ANY_SIMPLE;
      } else {
         if (sImpl.isRedefinition()) {
            baseType = state.findRedefinedGlobalType(parseTree.getBase(), sImpl.getChameleonNamespace(), sImpl);
            if (baseType != null && !baseType.getName().equals(sImpl.getName())) {
               state.error("src-redefine.5b", new Object[]{"<simpleType>", QNameHelper.pretty(baseType.getName()), QNameHelper.pretty(sImpl.getName())}, parseTree);
            }
         } else {
            baseType = state.findGlobalType(parseTree.getBase(), sImpl.getChameleonNamespace(), targetNamespace);
         }

         if (baseType == null) {
            state.notFoundError(parseTree.getBase(), 0, parseTree.xgetBase(), true);
            baseType = BuiltinSchemaTypeSystem.ST_ANY_SIMPLE;
         }
      }

      StscResolver.resolveType((SchemaTypeImpl)baseType);
      if (contentType != null) {
         StscResolver.resolveType((SchemaTypeImpl)contentType);
      } else {
         contentType = baseType;
      }

      if (baseType.isSimpleType()) {
         state.recover("ct-props-correct.2", new Object[]{QNameHelper.pretty(baseType.getName())}, parseTree);
         baseType = BuiltinSchemaTypeSystem.ST_ANY_SIMPLE;
      } else if (baseType.getContentType() != 2 && contentType == null) {
         baseType = BuiltinSchemaTypeSystem.ST_ANY_SIMPLE;
      }

      if (baseType != null && baseType.finalRestriction()) {
         state.error("derivation-ok-restriction.1", new Object[]{QNameHelper.pretty(baseType.getName()), QNameHelper.pretty(sImpl.getName())}, parseTree.xgetBase());
      }

      SchemaAttributeModelImpl attrModel;
      if (baseType == null) {
         attrModel = new SchemaAttributeModelImpl();
      } else {
         attrModel = new SchemaAttributeModelImpl(baseType.getAttributeModel());
      }

      translateAttributeModel(parseTree, targetNamespace, chameleon, sImpl.getAttFormDefault(), anonymousTypes, sImpl, (Set)null, attrModel, baseType, false, (SchemaAttributeGroupImpl)null);
      WildcardResult wcAttr = summarizeAttrWildcards(attrModel);
      Map attributePropertyModel = buildAttributePropertyModelByQName(attrModel, sImpl);
      sImpl.setBaseTypeRef(baseType.getRef());
      sImpl.setBaseDepth(((SchemaTypeImpl)baseType).getBaseDepth() + 1);
      sImpl.setContentBasedOnTypeRef(contentType.getRef());
      sImpl.setDerivationType(1);
      sImpl.setAnonymousTypeRefs(makeRefArray(anonymousTypes));
      sImpl.setWildcardSummary(QNameSet.EMPTY, false, wcAttr.typedWildcards, wcAttr.hasWildcards);
      sImpl.setComplexTypeVariety(2);
      sImpl.setContentModel((SchemaParticle)null, attrModel, (Map)null, attributePropertyModel, false);
      sImpl.setSimpleTypeVariety(contentType.getSimpleVariety());
      sImpl.setPrimitiveTypeRef(contentType.getPrimitiveType() == null ? null : contentType.getPrimitiveType().getRef());
      switch (sImpl.getSimpleVariety()) {
         case 2:
            sImpl.setUnionMemberTypeRefs(makeRefArray(Arrays.asList(contentType.getUnionMemberTypes())));
            break;
         case 3:
            sImpl.setListItemTypeRef(contentType.getListItemType().getRef());
      }

      StscSimpleTypeResolver.resolveFacets(sImpl, parseTree, (SchemaTypeImpl)contentType);
      StscSimpleTypeResolver.resolveFundamentalFacets(sImpl);
   }

   static void resolveScExtension(SchemaTypeImpl sImpl, SimpleExtensionType parseTree) {
      StscState state = StscState.get();
      String targetNamespace = sImpl.getTargetNamespace();
      boolean chameleon = sImpl.getChameleonNamespace() != null;
      SchemaTypeImpl baseType;
      if (parseTree.getBase() == null) {
         state.error("A simpleContent extension must define a base type", 28, parseTree);
         baseType = BuiltinSchemaTypeSystem.ST_ANY_SIMPLE;
      } else {
         if (sImpl.isRedefinition()) {
            baseType = state.findRedefinedGlobalType(parseTree.getBase(), sImpl.getChameleonNamespace(), sImpl);
            if (baseType != null && !baseType.getName().equals(sImpl.getName())) {
               state.error("src-redefine.5b", new Object[]{"<simpleType>", QNameHelper.pretty(baseType.getName()), QNameHelper.pretty(sImpl.getName())}, parseTree);
            }
         } else {
            baseType = state.findGlobalType(parseTree.getBase(), sImpl.getChameleonNamespace(), targetNamespace);
         }

         if (baseType == null) {
            state.notFoundError(parseTree.getBase(), 0, parseTree.xgetBase(), true);
            baseType = BuiltinSchemaTypeSystem.ST_ANY_SIMPLE;
         }
      }

      StscResolver.resolveType((SchemaTypeImpl)baseType);
      if (!baseType.isSimpleType() && baseType.getContentType() != 2) {
         state.error("src-ct.2", new Object[]{QNameHelper.pretty(baseType.getName())}, parseTree);
         baseType = BuiltinSchemaTypeSystem.ST_ANY_SIMPLE;
      }

      if (baseType != null && baseType.finalExtension()) {
         state.error("cos-ct-extends.1.1", new Object[]{QNameHelper.pretty(baseType.getName()), QNameHelper.pretty(sImpl.getName())}, parseTree.xgetBase());
      }

      resolveScExtensionPart2(sImpl, baseType, parseTree, targetNamespace, chameleon);
   }

   static void resolveScExtensionPart2(SchemaTypeImpl sImpl, SchemaType baseType, ExtensionType parseTree, String targetNamespace, boolean chameleon) {
      List anonymousTypes = new ArrayList();
      SchemaAttributeModelImpl attrModel = new SchemaAttributeModelImpl(baseType.getAttributeModel());
      translateAttributeModel(parseTree, targetNamespace, chameleon, sImpl.getAttFormDefault(), anonymousTypes, sImpl, (Set)null, attrModel, baseType, true, (SchemaAttributeGroupImpl)null);
      WildcardResult wcAttr = summarizeAttrWildcards(attrModel);
      Map attributePropertyModel = buildAttributePropertyModelByQName(attrModel, sImpl);
      sImpl.setBaseTypeRef(baseType.getRef());
      sImpl.setBaseDepth(((SchemaTypeImpl)baseType).getBaseDepth() + 1);
      sImpl.setContentBasedOnTypeRef(baseType.getRef());
      sImpl.setDerivationType(2);
      sImpl.setAnonymousTypeRefs(makeRefArray(anonymousTypes));
      sImpl.setWildcardSummary(QNameSet.EMPTY, false, wcAttr.typedWildcards, wcAttr.hasWildcards);
      sImpl.setComplexTypeVariety(2);
      sImpl.setContentModel((SchemaParticle)null, attrModel, (Map)null, attributePropertyModel, false);
      sImpl.setSimpleTypeVariety(baseType.getSimpleVariety());
      sImpl.setPrimitiveTypeRef(baseType.getPrimitiveType() == null ? null : baseType.getPrimitiveType().getRef());
      switch (sImpl.getSimpleVariety()) {
         case 2:
            sImpl.setUnionMemberTypeRefs(makeRefArray(Arrays.asList(baseType.getUnionMemberTypes())));
            break;
         case 3:
            sImpl.setListItemTypeRef(baseType.getListItemType().getRef());
      }

      StscSimpleTypeResolver.resolveFacets(sImpl, (XmlObject)null, (SchemaTypeImpl)baseType);
      StscSimpleTypeResolver.resolveFundamentalFacets(sImpl);
   }

   static WildcardResult summarizeAttrWildcards(SchemaAttributeModel attrModel) {
      if (attrModel.getWildcardProcess() == 0) {
         return new WildcardResult(QNameSet.EMPTY, false);
      } else {
         return attrModel.getWildcardProcess() == 3 ? new WildcardResult(QNameSet.EMPTY, true) : new WildcardResult(attrModel.getWildcardSet(), true);
      }
   }

   static WildcardResult summarizeEltWildcards(SchemaParticle contentModel) {
      if (contentModel == null) {
         return new WildcardResult(QNameSet.EMPTY, false);
      } else {
         switch (contentModel.getParticleType()) {
            case 1:
            case 2:
            case 3:
               QNameSetBuilder set = new QNameSetBuilder();
               boolean hasWildcards = false;

               for(int i = 0; i < contentModel.countOfParticleChild(); ++i) {
                  WildcardResult inner = summarizeEltWildcards(contentModel.getParticleChild(i));
                  set.addAll(inner.typedWildcards);
                  hasWildcards |= inner.hasWildcards;
               }

               return new WildcardResult(set.toQNameSet(), hasWildcards);
            case 4:
            default:
               return new WildcardResult(QNameSet.EMPTY, false);
            case 5:
               return new WildcardResult(contentModel.getWildcardProcess() == 3 ? QNameSet.EMPTY : contentModel.getWildcardSet(), true);
         }
      }
   }

   static void translateAttributeModel(XmlObject var0, String var1, boolean var2, String var3, List var4, SchemaType var5, Set var6, SchemaAttributeModelImpl var7, SchemaType var8, boolean var9, SchemaAttributeGroupImpl var10) {
      // $FF: Couldn't be decompiled
   }

   static SchemaParticle extendContentModel(SchemaParticle baseContentModel, SchemaParticle extendedContentModel, XmlObject parseTree) {
      if (extendedContentModel == null) {
         return baseContentModel;
      } else if (baseContentModel == null) {
         return extendedContentModel;
      } else {
         SchemaParticleImpl sPart = new SchemaParticleImpl();
         sPart.setParticleType(3);
         List accumulate = new ArrayList();
         addMinusPointlessParticles(accumulate, baseContentModel, 3);
         addMinusPointlessParticles(accumulate, extendedContentModel, 3);
         sPart.setMinOccurs(BigInteger.ONE);
         sPart.setMaxOccurs(BigInteger.ONE);
         sPart.setParticleChildren((SchemaParticle[])((SchemaParticle[])accumulate.toArray(new SchemaParticle[accumulate.size()])));
         return filterPointlessParticlesAndVerifyAllParticles(sPart, parseTree);
      }
   }

   static BigInteger extractMinOccurs(XmlNonNegativeInteger nni) {
      if (nni == null) {
         return BigInteger.ONE;
      } else {
         BigInteger result = nni.getBigIntegerValue();
         return result == null ? BigInteger.ONE : result;
      }
   }

   static BigInteger extractMaxOccurs(AllNNI allNNI) {
      if (allNNI == null) {
         return BigInteger.ONE;
      } else {
         return allNNI.instanceType().getPrimitiveType().getBuiltinTypeCode() == 11 ? ((XmlInteger)allNNI).getBigIntegerValue() : null;
      }
   }

   static SchemaParticle translateContentModel(SchemaType outerType, XmlObject parseTree, String targetNamespace, boolean chameleon, String elemFormDefault, String attFormDefault, int particleCode, List anonymousTypes, Map elementModel, boolean allowElt, RedefinitionForGroup redefinitionFor) {
      if (parseTree != null && particleCode != 0) {
         StscState state = StscState.get();

         assert particleCode != 0;

         boolean hasChildren = false;
         SchemaModelGroupImpl group = null;
         BigInteger minOccurs;
         BigInteger maxOccurs;
         Object sPart;
         if (particleCode == 4) {
            if (!allowElt) {
               state.error("Must be a sequence, choice or all here", 32, (XmlObject)parseTree);
            }

            LocalElement parseElt = (LocalElement)parseTree;
            sPart = StscTranslator.translateElement(parseElt, targetNamespace, chameleon, elemFormDefault, attFormDefault, anonymousTypes, outerType);
            if (sPart == null) {
               return null;
            }

            minOccurs = extractMinOccurs(parseElt.xgetMinOccurs());
            maxOccurs = extractMaxOccurs(parseElt.xgetMaxOccurs());
            SchemaType oldType = (SchemaType)elementModel.get(((SchemaParticleImpl)sPart).getName());
            if (oldType == null) {
               elementModel.put(((SchemaParticleImpl)sPart).getName(), ((SchemaParticleImpl)sPart).getType());
            } else if (!((SchemaParticleImpl)sPart).getType().equals(oldType)) {
               state.error("cos-element-consistent", new Object[]{QNameHelper.pretty(((SchemaParticleImpl)sPart).getName())}, (XmlObject)parseTree);
               return null;
            }
         } else if (particleCode == 5) {
            if (!allowElt) {
               state.error("Must be a sequence, choice or all here", 32, (XmlObject)parseTree);
            }

            AnyDocument.Any parseAny = (AnyDocument.Any)parseTree;
            sPart = new SchemaParticleImpl();
            ((SchemaParticleImpl)sPart).setParticleType(5);
            NamespaceList nslist = parseAny.xgetNamespace();
            QNameSet wcset;
            if (nslist == null) {
               wcset = QNameSet.ALL;
            } else {
               wcset = QNameSet.forWildcardNamespaceString(nslist.getStringValue(), targetNamespace);
            }

            ((SchemaParticleImpl)sPart).setWildcardSet(wcset);
            ((SchemaParticleImpl)sPart).setWildcardProcess(translateWildcardProcess(parseAny.xgetProcessContents()));
            minOccurs = extractMinOccurs(parseAny.xgetMinOccurs());
            maxOccurs = extractMaxOccurs(parseAny.xgetMaxOccurs());
         } else {
            Group parseGroup = (Group)parseTree;
            sPart = new SchemaParticleImpl();
            minOccurs = extractMinOccurs(parseGroup.xgetMinOccurs());
            maxOccurs = extractMaxOccurs(parseGroup.xgetMaxOccurs());
            if (particleCode == 100) {
               QName ref = parseGroup.getRef();
               if (ref == null) {
                  state.error("Group reference must have a ref attribute", 33, (XmlObject)parseTree);
                  return null;
               }

               if (redefinitionFor != null) {
                  group = state.findRedefinedModelGroup(ref, chameleon ? targetNamespace : null, redefinitionFor.getGroup());
                  if (group != null && group.getName().equals(redefinitionFor.getGroup().getName())) {
                     if (redefinitionFor.isSeenRedefinition()) {
                        state.error("src-redefine.6.1.1", new Object[]{QNameHelper.pretty(group.getName())}, (XmlObject)parseTree);
                     }

                     if (!BigInteger.ONE.equals(maxOccurs) || !BigInteger.ONE.equals(minOccurs)) {
                        state.error("src-redefine.6.1.2", new Object[]{QNameHelper.pretty(group.getName())}, (XmlObject)parseTree);
                     }

                     redefinitionFor.setSeenRedefinition(true);
                  }
               } else {
                  group = state.findModelGroup(ref, chameleon ? targetNamespace : null, targetNamespace);
               }

               if (group == null) {
                  state.notFoundError(ref, 6, ((Group)parseTree).xgetRef(), true);
                  return null;
               }

               if (state.isProcessing(group)) {
                  state.error("mg-props-correct.2", new Object[]{QNameHelper.pretty(group.getName())}, group.getParseObject());
                  return null;
               }

               XmlCursor cur = group.getParseObject().newCursor();

               for(boolean more = cur.toFirstChild(); more; more = cur.toNextSibling()) {
                  particleCode = translateParticleCode(cur.getName());
                  if (particleCode != 0) {
                     parseTree = (Group)cur.getObject();
                     break;
                  }
               }

               if (particleCode == 0) {
                  state.error("Model group " + QNameHelper.pretty(group.getName()) + " is empty", 32, group.getParseObject());
                  return null;
               }

               if (particleCode != 1 && particleCode != 3 && particleCode != 2) {
                  state.error("Model group " + QNameHelper.pretty(group.getName()) + " is not a sequence, all, or choice", 32, group.getParseObject());
               }

               String newTargetNamespace = group.getTargetNamespace();
               if (newTargetNamespace != null) {
                  targetNamespace = newTargetNamespace;
               }

               elemFormDefault = group.getElemFormDefault();
               attFormDefault = group.getAttFormDefault();
               chameleon = group.getChameleonNamespace() != null;
            }

            switch (particleCode) {
               case 1:
               case 2:
               case 3:
                  ((SchemaParticleImpl)sPart).setParticleType(particleCode);
                  hasChildren = true;
                  break;
               default:
                  assert false;

                  throw new IllegalStateException();
            }
         }

         if (maxOccurs != null && minOccurs.compareTo(maxOccurs) > 0) {
            state.error("p-props-correct.2.1", (Object[])null, (XmlObject)parseTree);
            maxOccurs = minOccurs;
         }

         if (maxOccurs != null && maxOccurs.compareTo(BigInteger.ONE) < 0) {
            state.warning("p-props-correct.2.2", (Object[])null, (XmlObject)parseTree);
            anonymousTypes.remove(((SchemaParticleImpl)sPart).getType());
            return null;
         } else {
            ((SchemaParticleImpl)sPart).setMinOccurs(minOccurs);
            ((SchemaParticleImpl)sPart).setMaxOccurs(maxOccurs);
            if (group != null) {
               state.startProcessing(group);
               redefinitionFor = null;
               if (group.isRedefinition()) {
                  redefinitionFor = new RedefinitionForGroup(group);
               }
            }

            if (hasChildren) {
               XmlCursor cur = ((XmlObject)parseTree).newCursor();
               List accumulate = new ArrayList();

               for(boolean more = cur.toFirstChild(); more; more = cur.toNextSibling()) {
                  int code = translateParticleCode(cur.getName());
                  if (code != 0) {
                     addMinusPointlessParticles(accumulate, translateContentModel(outerType, cur.getObject(), targetNamespace, chameleon, elemFormDefault, attFormDefault, code, anonymousTypes, elementModel, true, redefinitionFor), ((SchemaParticleImpl)sPart).getParticleType());
                  }
               }

               ((SchemaParticleImpl)sPart).setParticleChildren((SchemaParticle[])((SchemaParticle[])accumulate.toArray(new SchemaParticle[accumulate.size()])));
               cur.dispose();
            }

            SchemaParticle result = filterPointlessParticlesAndVerifyAllParticles((SchemaParticle)sPart, (XmlObject)parseTree);
            if (group != null) {
               state.finishProcessing(group);
            }

            return result;
         }
      } else {
         return null;
      }
   }

   static int translateWildcardProcess(Wildcard.ProcessContents process) {
      if (process == null) {
         return 1;
      } else {
         String processValue = process.getStringValue();
         if ("lax".equals(processValue)) {
            return 2;
         } else {
            return "skip".equals(processValue) ? 3 : 1;
         }
      }
   }

   static SchemaParticle filterPointlessParticlesAndVerifyAllParticles(SchemaParticle part, XmlObject parseTree) {
      if (part.getMaxOccurs() != null && part.getMaxOccurs().signum() == 0) {
         return null;
      } else {
         switch (part.getParticleType()) {
            case 1:
            case 3:
               if (part.getParticleChildren().length == 0) {
                  return null;
               }

               if (part.isSingleton() && part.countOfParticleChild() == 1) {
                  return part.getParticleChild(0);
               }
               break;
            case 2:
               if (part.getParticleChildren().length == 0 && part.getMinOccurs().compareTo(BigInteger.ZERO) == 0) {
                  return null;
               }

               if (part.isSingleton() && part.countOfParticleChild() == 1) {
                  return part.getParticleChild(0);
               }
               break;
            case 4:
            case 5:
               return part;
            default:
               assert false;

               throw new IllegalStateException();
         }

         boolean isAll = part.getParticleType() == 1;
         if (isAll && (part.getMaxOccurs() == null || part.getMaxOccurs().compareTo(BigInteger.ONE) > 0)) {
            StscState.get().error("cos-all-limited.1.2a", (Object[])null, parseTree);
         }

         for(int i = 0; i < part.countOfParticleChild(); ++i) {
            SchemaParticle child = part.getParticleChild(i);
            if (child.getParticleType() == 1) {
               StscState.get().error("cos-all-limited.1.2b", (Object[])null, parseTree);
            } else if (isAll && (child.getParticleType() != 4 || child.getMaxOccurs() == null || child.getMaxOccurs().compareTo(BigInteger.ONE) > 0)) {
               StscState.get().error("cos-all-limited.2", (Object[])null, parseTree);
            }
         }

         return part;
      }
   }

   static void addMinusPointlessParticles(List list, SchemaParticle part, int parentParticleType) {
      if (part != null) {
         switch (part.getParticleType()) {
            case 1:
            default:
               break;
            case 2:
               if (parentParticleType == 2 && part.isSingleton()) {
                  list.addAll(Arrays.asList(part.getParticleChildren()));
                  return;
               }
               break;
            case 3:
               if (parentParticleType == 3 && part.isSingleton()) {
                  list.addAll(Arrays.asList(part.getParticleChildren()));
                  return;
               }
         }

         list.add(part);
      }
   }

   static Map buildAttributePropertyModelByQName(SchemaAttributeModel attrModel, SchemaType owner) {
      Map result = new LinkedHashMap();
      SchemaLocalAttribute[] attruses = attrModel.getAttributes();

      for(int i = 0; i < attruses.length; ++i) {
         result.put(attruses[i].getName(), buildUseProperty(attruses[i], owner));
      }

      return result;
   }

   static Map buildContentPropertyModelByQName(SchemaParticle part, SchemaType owner) {
      if (part == null) {
         return Collections.EMPTY_MAP;
      } else {
         boolean asSequence = false;
         Map model = null;
         switch (part.getParticleType()) {
            case 1:
            case 3:
               asSequence = true;
               break;
            case 2:
               asSequence = false;
               break;
            case 4:
               model = buildElementPropertyModel((SchemaLocalElement)part, owner);
               break;
            case 5:
               model = Collections.EMPTY_MAP;
               break;
            default:
               assert false;

               throw new IllegalStateException();
         }

         if (model == null) {
            model = new LinkedHashMap();
            SchemaParticle[] children = part.getParticleChildren();

            Iterator j;
            SchemaProperty iProp;
            for(int i = 0; i < children.length; ++i) {
               Map childModel = buildContentPropertyModelByQName(children[i], owner);
               j = childModel.values().iterator();

               while(j.hasNext()) {
                  iProp = (SchemaProperty)j.next();
                  SchemaPropertyImpl oProp = (SchemaPropertyImpl)((Map)model).get(iProp.getName());
                  if (oProp == null) {
                     if (!asSequence) {
                        ((SchemaPropertyImpl)iProp).setMinOccurs(BigInteger.ZERO);
                     }

                     ((Map)model).put(iProp.getName(), iProp);
                  } else {
                     assert oProp.getType().equals(iProp.getType());

                     mergeProperties(oProp, iProp, asSequence);
                  }
               }
            }

            BigInteger min = part.getMinOccurs();
            BigInteger max = part.getMaxOccurs();
            j = ((Map)model).values().iterator();

            while(j.hasNext()) {
               iProp = (SchemaProperty)j.next();
               BigInteger minOccurs = iProp.getMinOccurs();
               BigInteger maxOccurs = iProp.getMaxOccurs();
               minOccurs = minOccurs.multiply(min);
               if (max != null && max.equals(BigInteger.ZERO)) {
                  maxOccurs = BigInteger.ZERO;
               } else if (maxOccurs != null && !maxOccurs.equals(BigInteger.ZERO)) {
                  maxOccurs = max == null ? null : maxOccurs.multiply(max);
               }

               ((SchemaPropertyImpl)iProp).setMinOccurs(minOccurs);
               ((SchemaPropertyImpl)iProp).setMaxOccurs(maxOccurs);
            }
         }

         return (Map)model;
      }
   }

   static Map buildElementPropertyModel(SchemaLocalElement epart, SchemaType owner) {
      Map result = new HashMap(1);
      SchemaProperty sProp = buildUseProperty(epart, owner);
      result.put(sProp.getName(), sProp);
      return result;
   }

   static SchemaProperty buildUseProperty(SchemaField use, SchemaType owner) {
      SchemaPropertyImpl sPropImpl = new SchemaPropertyImpl();
      sPropImpl.setName(use.getName());
      sPropImpl.setContainerTypeRef(owner.getRef());
      sPropImpl.setTypeRef(use.getType().getRef());
      sPropImpl.setAttribute(use.isAttribute());
      sPropImpl.setDefault(use.isDefault() ? 2 : 0);
      sPropImpl.setFixed(use.isFixed() ? 2 : 0);
      sPropImpl.setNillable(use.isNillable() ? 2 : 0);
      sPropImpl.setDefaultText(use.getDefaultText());
      sPropImpl.setMinOccurs(use.getMinOccurs());
      sPropImpl.setMaxOccurs(use.getMaxOccurs());
      if (use instanceof SchemaLocalElementImpl) {
         SchemaLocalElementImpl elt = (SchemaLocalElementImpl)use;
         sPropImpl.setAcceptedNames(elt.acceptedStartNames());
      }

      return sPropImpl;
   }

   static void mergeProperties(SchemaPropertyImpl into, SchemaProperty from, boolean asSequence) {
      BigInteger minOccurs = into.getMinOccurs();
      BigInteger maxOccurs = into.getMaxOccurs();
      if (asSequence) {
         minOccurs = minOccurs.add(from.getMinOccurs());
         if (maxOccurs != null) {
            maxOccurs = from.getMaxOccurs() == null ? null : maxOccurs.add(from.getMaxOccurs());
         }
      } else {
         minOccurs = minOccurs.min(from.getMinOccurs());
         if (maxOccurs != null) {
            maxOccurs = from.getMaxOccurs() == null ? null : maxOccurs.max(from.getMaxOccurs());
         }
      }

      into.setMinOccurs(minOccurs);
      into.setMaxOccurs(maxOccurs);
      if (from.hasNillable() != into.hasNillable()) {
         into.setNillable(1);
      }

      if (from.hasDefault() != into.hasDefault()) {
         into.setDefault(1);
      }

      if (from.hasFixed() != into.hasFixed()) {
         into.setFixed(1);
      }

      if (into.getDefaultText() != null && (from.getDefaultText() == null || !into.getDefaultText().equals(from.getDefaultText()))) {
         into.setDefaultText((String)null);
      }

   }

   static SchemaParticle[] ensureStateMachine(SchemaParticle[] children) {
      for(int i = 0; i < children.length; ++i) {
         buildStateMachine(children[i]);
      }

      return children;
   }

   static void buildStateMachine(SchemaParticle contentModel) {
      if (contentModel != null) {
         SchemaParticleImpl partImpl = (SchemaParticleImpl)contentModel;
         if (!partImpl.hasTransitionNotes()) {
            QNameSetBuilder start;
            QNameSetBuilder excludenext;
            boolean deterministic;
            boolean canskip;
            start = new QNameSetBuilder();
            excludenext = new QNameSetBuilder();
            deterministic = true;
            SchemaParticle[] children = null;
            canskip = partImpl.getMinOccurs().signum() == 0;
            int i;
            label222:
            switch (partImpl.getParticleType()) {
               case 1:
                  children = ensureStateMachine(partImpl.getParticleChildren());
                  canskip = true;

                  for(i = 0; !canskip && i < children.length; ++i) {
                     if (!children[i].isSkippable()) {
                        canskip = false;
                     }
                  }

                  for(i = 0; deterministic && i < children.length; ++i) {
                     if (!((SchemaParticleImpl)children[i]).isDeterministic()) {
                        deterministic = false;
                     }
                  }

                  for(i = 0; i < children.length; ++i) {
                     if (deterministic && !start.isDisjoint(children[i].acceptedStartNames())) {
                        deterministic = false;
                     }

                     start.addAll(children[i].acceptedStartNames());
                     excludenext.addAll(((SchemaParticleImpl)children[i]).getExcludeNextSet());
                  }

                  if (canskip) {
                     excludenext.addAll(start);
                  }
                  break;
               case 2:
                  children = ensureStateMachine(partImpl.getParticleChildren());
                  canskip = false;

                  for(i = 0; !canskip && i < children.length; ++i) {
                     if (children[i].isSkippable()) {
                        canskip = true;
                     }
                  }

                  for(i = 0; deterministic && i < children.length; ++i) {
                     if (!((SchemaParticleImpl)children[i]).isDeterministic()) {
                        deterministic = false;
                     }
                  }

                  i = 0;

                  while(true) {
                     if (i >= children.length) {
                        break label222;
                     }

                     if (deterministic && !start.isDisjoint(children[i].acceptedStartNames())) {
                        deterministic = false;
                     }

                     start.addAll(children[i].acceptedStartNames());
                     excludenext.addAll(((SchemaParticleImpl)children[i]).getExcludeNextSet());
                     ++i;
                  }
               case 3:
                  children = ensureStateMachine(partImpl.getParticleChildren());
                  canskip = true;

                  for(i = 0; canskip && i < children.length; ++i) {
                     if (!children[i].isSkippable()) {
                        canskip = false;
                     }
                  }

                  for(i = 0; deterministic && i < children.length; ++i) {
                     if (!((SchemaParticleImpl)children[i]).isDeterministic()) {
                        deterministic = false;
                     }
                  }

                  for(i = 1; i < children.length; ++i) {
                     excludenext.addAll(((SchemaParticleImpl)children[i - 1]).getExcludeNextSet());
                     if (deterministic && !excludenext.isDisjoint(children[i].acceptedStartNames())) {
                        deterministic = false;
                     }

                     if (children[i].isSkippable()) {
                        excludenext.addAll(children[i].acceptedStartNames());
                     } else {
                        excludenext.clear();
                     }
                  }

                  i = 0;

                  while(true) {
                     if (i >= children.length) {
                        break label222;
                     }

                     start.addAll(children[i].acceptedStartNames());
                     if (!children[i].isSkippable()) {
                        break label222;
                     }

                     ++i;
                  }
               case 4:
                  if (partImpl.hasTransitionRules()) {
                     start.addAll(partImpl.acceptedStartNames());
                  } else {
                     start.add(partImpl.getName());
                  }
                  break;
               case 5:
                  start.addAll(partImpl.getWildcardSet());
                  break;
               default:
                  throw new IllegalStateException("Unrecognized schema particle");
            }

            BigInteger minOccurs = partImpl.getMinOccurs();
            BigInteger maxOccurs = partImpl.getMaxOccurs();
            boolean canloop = maxOccurs == null || maxOccurs.compareTo(BigInteger.ONE) > 0;
            boolean varloop = maxOccurs == null || minOccurs.compareTo(maxOccurs) < 0;
            if (canloop && deterministic && !excludenext.isDisjoint(start)) {
               QNameSet suspectSet = excludenext.intersect(start);
               Map startMap = new HashMap();
               particlesMatchingStart(partImpl, suspectSet, startMap, new QNameSetBuilder());
               Map afterMap = new HashMap();
               particlesMatchingAfter(partImpl, suspectSet, afterMap, new QNameSetBuilder(), true);
               deterministic = afterMapSubsumedByStartMap(startMap, afterMap);
            }

            if (varloop) {
               excludenext.addAll(start);
            }

            canskip = canskip || minOccurs.signum() == 0;
            partImpl.setTransitionRules(start.toQNameSet(), canskip);
            partImpl.setTransitionNotes(excludenext.toQNameSet(), deterministic);
         }
      }
   }

   private static boolean afterMapSubsumedByStartMap(Map startMap, Map afterMap) {
      if (afterMap.size() > startMap.size()) {
         return false;
      } else if (afterMap.isEmpty()) {
         return true;
      } else {
         Iterator i = startMap.keySet().iterator();

         do {
            if (!i.hasNext()) {
               return afterMap.isEmpty();
            }

            SchemaParticle part = (SchemaParticle)i.next();
            if (part.getParticleType() == 5 && afterMap.containsKey(part)) {
               QNameSet startSet = (QNameSet)startMap.get(part);
               QNameSet afterSet = (QNameSet)afterMap.get(part);
               if (!startSet.containsAll(afterSet)) {
                  return false;
               }
            }

            afterMap.remove(part);
         } while(!afterMap.isEmpty());

         return true;
      }
   }

   private static void particlesMatchingStart(SchemaParticle part, QNameSetSpecification suspectSet, Map result, QNameSetBuilder eliminate) {
      SchemaParticle[] children;
      switch (part.getParticleType()) {
         case 1:
         case 2:
            children = part.getParticleChildren();

            for(int i = 0; i < children.length; ++i) {
               particlesMatchingStart(children[i], suspectSet, result, eliminate);
            }

            return;
         case 3:
            children = part.getParticleChildren();
            if (children.length == 0) {
               return;
            } else if (!children[0].isSkippable()) {
               particlesMatchingStart(children[0], suspectSet, result, eliminate);
               return;
            } else {
               QNameSetBuilder remainingSuspects = new QNameSetBuilder(suspectSet);
               QNameSetBuilder suspectsToEliminate = new QNameSetBuilder();

               for(int i = 0; i < children.length; ++i) {
                  particlesMatchingStart(children[i], remainingSuspects, result, suspectsToEliminate);
                  eliminate.addAll(suspectsToEliminate);
                  if (!children[i].isSkippable()) {
                     return;
                  }

                  remainingSuspects.removeAll(suspectsToEliminate);
                  if (remainingSuspects.isEmpty()) {
                     return;
                  }

                  suspectsToEliminate.clear();
               }

               return;
            }
         case 4:
            if (!suspectSet.contains(part.getName())) {
               return;
            }

            result.put(part, (Object)null);
            eliminate.add(part.getName());
            return;
         case 5:
            if (suspectSet.isDisjoint(part.getWildcardSet())) {
               return;
            }

            result.put(part, part.getWildcardSet().intersect(suspectSet));
            eliminate.addAll(part.getWildcardSet());
            return;
         default:
      }
   }

   private static void particlesMatchingAfter(SchemaParticle part, QNameSetSpecification suspectSet, Map result, QNameSetBuilder eliminate, boolean top) {
      SchemaParticle[] children;
      label53:
      switch (part.getParticleType()) {
         case 1:
         case 2:
            children = part.getParticleChildren();
            int i = 0;

            while(true) {
               if (i >= children.length) {
                  break label53;
               }

               particlesMatchingAfter(children[i], suspectSet, result, eliminate, false);
               ++i;
            }
         case 3:
            children = part.getParticleChildren();
            if (children.length != 0) {
               if (!children[children.length - 1].isSkippable()) {
                  particlesMatchingAfter(children[0], suspectSet, result, eliminate, false);
               } else {
                  QNameSetBuilder remainingSuspects = new QNameSetBuilder(suspectSet);
                  QNameSetBuilder suspectsToEliminate = new QNameSetBuilder();

                  for(int i = children.length - 1; i >= 0; --i) {
                     particlesMatchingAfter(children[i], remainingSuspects, result, suspectsToEliminate, false);
                     eliminate.addAll(suspectsToEliminate);
                     if (!children[i].isSkippable()) {
                        break;
                     }

                     remainingSuspects.removeAll(suspectsToEliminate);
                     if (remainingSuspects.isEmpty()) {
                        break;
                     }

                     suspectsToEliminate.clear();
                  }
               }
            }
      }

      if (!top) {
         BigInteger minOccurs = part.getMinOccurs();
         BigInteger maxOccurs = part.getMaxOccurs();
         boolean varloop = maxOccurs == null || minOccurs.compareTo(maxOccurs) < 0;
         if (varloop) {
            particlesMatchingStart(part, suspectSet, result, eliminate);
         }
      }

   }

   private static Map buildParticleCodeMap() {
      Map result = new HashMap();

      for(int i = 0; i < particleCodes.length; ++i) {
         result.put(particleCodes[i].name, new Integer(particleCodes[i].code));
      }

      return result;
   }

   private static int translateParticleCode(Group parseEg) {
      return parseEg == null ? 0 : translateParticleCode(parseEg.newCursor().getName());
   }

   private static int translateParticleCode(QName name) {
      Integer result = (Integer)particleCodeMap.get(name);
      return result == null ? 0 : result;
   }

   private static Map buildAttributeCodeMap() {
      Map result = new HashMap();

      for(int i = 0; i < attributeCodes.length; ++i) {
         result.put(attributeCodes[i].name, new Integer(attributeCodes[i].code));
      }

      return result;
   }

   static int translateAttributeCode(QName currentName) {
      Integer result = (Integer)attributeCodeMap.get(currentName);
      return result == null ? 0 : result;
   }

   private static class CodeForNameEntry {
      public QName name;
      public int code;

      CodeForNameEntry(QName name, int code) {
         this.name = name;
         this.code = code;
      }
   }

   private static class RedefinitionForGroup {
      private SchemaModelGroupImpl group;
      private boolean seenRedefinition = false;

      public RedefinitionForGroup(SchemaModelGroupImpl group) {
         this.group = group;
      }

      public SchemaModelGroupImpl getGroup() {
         return this.group;
      }

      public boolean isSeenRedefinition() {
         return this.seenRedefinition;
      }

      public void setSeenRedefinition(boolean seenRedefinition) {
         this.seenRedefinition = seenRedefinition;
      }
   }

   static class WildcardResult {
      QNameSet typedWildcards;
      boolean hasWildcards;

      WildcardResult(QNameSet typedWildcards, boolean hasWildcards) {
         this.typedWildcards = typedWildcards;
         this.hasWildcards = hasWildcards;
      }
   }
}
