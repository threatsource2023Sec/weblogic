package org.apache.xmlbeans.impl.schema;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.xmlbeans.QNameSet;
import org.apache.xmlbeans.QNameSetBuilder;
import org.apache.xmlbeans.SchemaAttributeModel;
import org.apache.xmlbeans.SchemaBookmark;
import org.apache.xmlbeans.SchemaField;
import org.apache.xmlbeans.SchemaGlobalAttribute;
import org.apache.xmlbeans.SchemaGlobalElement;
import org.apache.xmlbeans.SchemaIdentityConstraint;
import org.apache.xmlbeans.SchemaParticle;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlAnySimpleType;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlNonNegativeInteger;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlPositiveInteger;
import org.apache.xmlbeans.impl.common.QNameHelper;
import org.apache.xmlbeans.impl.common.XMLChar;
import org.apache.xmlbeans.impl.common.XPath;
import org.apache.xmlbeans.impl.regex.RegularExpression;
import org.apache.xmlbeans.impl.values.NamespaceContext;
import org.apache.xmlbeans.impl.values.XmlNonNegativeIntegerImpl;
import org.apache.xmlbeans.impl.values.XmlPositiveIntegerImpl;
import org.apache.xmlbeans.impl.values.XmlValueOutOfRangeException;
import org.apache.xmlbeans.impl.xb.xsdschema.Annotated;
import org.apache.xmlbeans.impl.xb.xsdschema.AnnotationDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.apache.xmlbeans.impl.xb.xsdschema.AttributeGroup;
import org.apache.xmlbeans.impl.xb.xsdschema.Element;
import org.apache.xmlbeans.impl.xb.xsdschema.FieldDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.FormChoice;
import org.apache.xmlbeans.impl.xb.xsdschema.Keybase;
import org.apache.xmlbeans.impl.xb.xsdschema.KeyrefDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.LocalElement;
import org.apache.xmlbeans.impl.xb.xsdschema.LocalSimpleType;
import org.apache.xmlbeans.impl.xb.xsdschema.NamedAttributeGroup;
import org.apache.xmlbeans.impl.xb.xsdschema.NamedGroup;
import org.apache.xmlbeans.impl.xb.xsdschema.RedefineDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.SchemaDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.SimpleType;
import org.apache.xmlbeans.impl.xb.xsdschema.TopLevelAttribute;
import org.apache.xmlbeans.impl.xb.xsdschema.TopLevelComplexType;
import org.apache.xmlbeans.impl.xb.xsdschema.TopLevelElement;
import org.apache.xmlbeans.impl.xb.xsdschema.TopLevelSimpleType;
import org.apache.xmlbeans.soap.SOAPArrayType;

public class StscTranslator {
   private static final QName WSDL_ARRAYTYPE_NAME = QNameHelper.forLNS("arrayType", "http://schemas.xmlsoap.org/wsdl/");
   private static final String FORM_QUALIFIED = "qualified";
   public static final RegularExpression XPATH_REGEXP = new RegularExpression("(\\.//)?((((child::)?((\\i\\c*:)?(\\i\\c*|\\*)))|\\.)/)*((((child::)?((\\i\\c*:)?(\\i\\c*|\\*)))|\\.)|((attribute::|@)((\\i\\c*:)?(\\i\\c*|\\*))))(\\|(\\.//)?((((child::)?((\\i\\c*:)?(\\i\\c*|\\*)))|\\.)/)*((((child::)?((\\i\\c*:)?(\\i\\c*|\\*)))|\\.)|((attribute::|@)((\\i\\c*:)?(\\i\\c*|\\*)))))*", "X");

   public static void addAllDefinitions(StscImporter.SchemaToProcess[] schemasAndChameleons) {
      List redefinitions = new ArrayList();

      for(int i = 0; i < schemasAndChameleons.length; ++i) {
         List redefines = schemasAndChameleons[i].getRedefines();
         if (redefines != null) {
            List redefineObjects = schemasAndChameleons[i].getRedefineObjects();
            Iterator it = redefines.iterator();
            Iterator ito = redefineObjects.iterator();

            while(it.hasNext()) {
               assert ito.hasNext() : "The array of redefines and redefine objects have to have the same length";

               redefinitions.add(new RedefinitionHolder((StscImporter.SchemaToProcess)it.next(), (RedefineDocument.Redefine)ito.next()));
            }
         }
      }

      RedefinitionMaster globalRedefinitions = new RedefinitionMaster((RedefinitionHolder[])((RedefinitionHolder[])redefinitions.toArray(new RedefinitionHolder[redefinitions.size()])));
      StscState state = StscState.get();

      int j;
      for(j = 0; j < schemasAndChameleons.length; ++j) {
         SchemaDocument.Schema schema = schemasAndChameleons[j].getSchema();
         String givenTargetNamespace = schemasAndChameleons[j].getChameleonNamespace();
         if (schema.sizeOfNotationArray() > 0) {
            state.warning("Schema <notation> is not yet supported for this release.", 51, schema.getNotationArray(0));
         }

         String targetNamespace = schema.getTargetNamespace();
         boolean chameleon = false;
         if (givenTargetNamespace != null && targetNamespace == null) {
            targetNamespace = givenTargetNamespace;
            chameleon = true;
         }

         if (targetNamespace == null) {
            targetNamespace = "";
         }

         if (targetNamespace.length() > 0 || !isEmptySchema(schema)) {
            state.registerContribution(targetNamespace, schema.documentProperties().getSourceName());
            state.addNewContainer(targetNamespace);
         }

         List redefChain = new ArrayList();
         TopLevelComplexType[] complexTypes = schema.getComplexTypeArray();

         int i;
         SchemaTypeImpl t;
         int i;
         for(int i = 0; i < complexTypes.length; ++i) {
            TopLevelComplexType type = complexTypes[i];
            RedefinitionHolder[] rhArray = globalRedefinitions.getComplexTypeRedefinitions(type.getName(), schemasAndChameleons[j]);

            TopLevelComplexType redef;
            for(i = 0; i < rhArray.length; ++i) {
               if (rhArray[i] != null) {
                  redef = rhArray[i].redefineComplexType(type.getName());

                  assert redef != null;

                  redefChain.add(type);
                  type = redef;
               }
            }

            SchemaTypeImpl t = translateGlobalComplexType(type, targetNamespace, chameleon, redefChain.size() > 0);
            state.addGlobalType(t, (SchemaTypeImpl)null);

            for(i = redefChain.size() - 1; i >= 0; --i) {
               redef = (TopLevelComplexType)redefChain.remove(i);
               t = translateGlobalComplexType(redef, targetNamespace, chameleon, i > 0);
               state.addGlobalType(t, t);
               t = t;
            }
         }

         TopLevelSimpleType[] simpleTypes = schema.getSimpleTypeArray();

         int i;
         for(int i = 0; i < simpleTypes.length; ++i) {
            TopLevelSimpleType type = simpleTypes[i];
            RedefinitionHolder[] rhArray = globalRedefinitions.getSimpleTypeRedefinitions(type.getName(), schemasAndChameleons[j]);

            TopLevelSimpleType redef;
            for(i = 0; i < rhArray.length; ++i) {
               if (rhArray[i] != null) {
                  redef = rhArray[i].redefineSimpleType(type.getName());

                  assert redef != null;

                  redefChain.add(type);
                  type = redef;
               }
            }

            t = translateGlobalSimpleType(type, targetNamespace, chameleon, redefChain.size() > 0);
            state.addGlobalType(t, (SchemaTypeImpl)null);

            for(int k = redefChain.size() - 1; k >= 0; --k) {
               redef = (TopLevelSimpleType)redefChain.remove(k);
               SchemaTypeImpl r = translateGlobalSimpleType(redef, targetNamespace, chameleon, k > 0);
               state.addGlobalType(r, t);
               t = r;
            }
         }

         TopLevelElement[] elements = schema.getElementArray();

         for(int i = 0; i < elements.length; ++i) {
            TopLevelElement element = elements[i];
            state.addDocumentType(translateDocumentType(element, targetNamespace, chameleon), QNameHelper.forLNS(element.getName(), targetNamespace));
         }

         TopLevelAttribute[] attributes = schema.getAttributeArray();

         for(int i = 0; i < attributes.length; ++i) {
            TopLevelAttribute attribute = attributes[i];
            state.addAttributeType(translateAttributeType(attribute, targetNamespace, chameleon), QNameHelper.forLNS(attribute.getName(), targetNamespace));
         }

         NamedGroup[] modelgroups = schema.getGroupArray();

         for(i = 0; i < modelgroups.length; ++i) {
            NamedGroup group = modelgroups[i];
            RedefinitionHolder[] rhArray = globalRedefinitions.getModelGroupRedefinitions(group.getName(), schemasAndChameleons[j]);

            NamedGroup redef;
            for(int k = 0; k < rhArray.length; ++k) {
               if (rhArray[k] != null) {
                  redef = rhArray[k].redefineModelGroup(group.getName());

                  assert redef != null;

                  redefChain.add(group);
                  group = redef;
               }
            }

            SchemaModelGroupImpl g = translateModelGroup(group, targetNamespace, chameleon, redefChain.size() > 0);
            state.addModelGroup(g, (SchemaModelGroupImpl)null);

            for(int k = redefChain.size() - 1; k >= 0; --k) {
               redef = (NamedGroup)redefChain.remove(k);
               SchemaModelGroupImpl r = translateModelGroup(redef, targetNamespace, chameleon, k > 0);
               state.addModelGroup(r, g);
               g = r;
            }
         }

         NamedAttributeGroup[] attrgroups = schema.getAttributeGroupArray();

         for(i = 0; i < attrgroups.length; ++i) {
            NamedAttributeGroup group = attrgroups[i];
            RedefinitionHolder[] rhArray = globalRedefinitions.getAttributeGroupRedefinitions(group.getName(), schemasAndChameleons[j]);

            NamedAttributeGroup redef;
            for(int k = 0; k < rhArray.length; ++k) {
               if (rhArray[k] != null) {
                  redef = rhArray[k].redefineAttributeGroup(group.getName());

                  assert redef != null;

                  redefChain.add(group);
                  group = redef;
               }
            }

            SchemaAttributeGroupImpl g = translateAttributeGroup(group, targetNamespace, chameleon, redefChain.size() > 0);
            state.addAttributeGroup(g, (SchemaAttributeGroupImpl)null);

            for(int k = redefChain.size() - 1; k >= 0; --k) {
               redef = (NamedAttributeGroup)redefChain.remove(k);
               SchemaAttributeGroupImpl r = translateAttributeGroup(redef, targetNamespace, chameleon, k > 0);
               state.addAttributeGroup(r, g);
               g = r;
            }
         }

         AnnotationDocument.Annotation[] annotations = schema.getAnnotationArray();

         for(i = 0; i < annotations.length; ++i) {
            state.addAnnotation(SchemaAnnotationImpl.getAnnotation(state.getContainer(targetNamespace), schema, annotations[i]), targetNamespace);
         }
      }

      for(j = 0; j < redefinitions.size(); ++j) {
         ((RedefinitionHolder)redefinitions.get(j)).complainAboutMissingDefinitions();
      }

   }

   private static String findFilename(XmlObject xobj) {
      return StscState.get().sourceNameForUri(xobj.documentProperties().getSourceName());
   }

   private static SchemaTypeImpl translateDocumentType(TopLevelElement xsdType, String targetNamespace, boolean chameleon) {
      SchemaTypeImpl sType = new SchemaTypeImpl(StscState.get().getContainer(targetNamespace));
      sType.setDocumentType(true);
      sType.setParseContext(xsdType, targetNamespace, chameleon, (String)null, (String)null, false);
      sType.setFilename(findFilename(xsdType));
      return sType;
   }

   private static SchemaTypeImpl translateAttributeType(TopLevelAttribute xsdType, String targetNamespace, boolean chameleon) {
      SchemaTypeImpl sType = new SchemaTypeImpl(StscState.get().getContainer(targetNamespace));
      sType.setAttributeType(true);
      sType.setParseContext(xsdType, targetNamespace, chameleon, (String)null, (String)null, false);
      sType.setFilename(findFilename(xsdType));
      return sType;
   }

   private static SchemaTypeImpl translateGlobalComplexType(TopLevelComplexType xsdType, String targetNamespace, boolean chameleon, boolean redefinition) {
      StscState state = StscState.get();
      String localname = xsdType.getName();
      if (localname == null) {
         state.error("missing-name", new Object[]{"global type"}, xsdType);
         return null;
      } else {
         if (!XMLChar.isValidNCName(localname)) {
            state.error("invalid-value", new Object[]{localname, "name"}, xsdType.xgetName());
         }

         QName name = QNameHelper.forLNS(localname, targetNamespace);
         if (isReservedTypeName(name)) {
            state.warning("reserved-type-name", new Object[]{QNameHelper.pretty(name)}, xsdType);
            return null;
         } else {
            SchemaTypeImpl sType = new SchemaTypeImpl(state.getContainer(targetNamespace));
            sType.setParseContext(xsdType, targetNamespace, chameleon, (String)null, (String)null, redefinition);
            sType.setFilename(findFilename(xsdType));
            sType.setName(QNameHelper.forLNS(localname, targetNamespace));
            sType.setAnnotation(SchemaAnnotationImpl.getAnnotation(state.getContainer(targetNamespace), xsdType));
            sType.setUserData(getUserData(xsdType));
            return sType;
         }
      }
   }

   private static SchemaTypeImpl translateGlobalSimpleType(TopLevelSimpleType xsdType, String targetNamespace, boolean chameleon, boolean redefinition) {
      StscState state = StscState.get();
      String localname = xsdType.getName();
      if (localname == null) {
         state.error("missing-name", new Object[]{"global type"}, xsdType);
         return null;
      } else {
         if (!XMLChar.isValidNCName(localname)) {
            state.error("invalid-value", new Object[]{localname, "name"}, xsdType.xgetName());
         }

         QName name = QNameHelper.forLNS(localname, targetNamespace);
         if (isReservedTypeName(name)) {
            state.warning("reserved-type-name", new Object[]{QNameHelper.pretty(name)}, xsdType);
            return null;
         } else {
            SchemaTypeImpl sType = new SchemaTypeImpl(state.getContainer(targetNamespace));
            sType.setSimpleType(true);
            sType.setParseContext(xsdType, targetNamespace, chameleon, (String)null, (String)null, redefinition);
            sType.setFilename(findFilename(xsdType));
            sType.setName(name);
            sType.setAnnotation(SchemaAnnotationImpl.getAnnotation(state.getContainer(targetNamespace), xsdType));
            sType.setUserData(getUserData(xsdType));
            return sType;
         }
      }
   }

   static SchemaTypeImpl translateAnonymousSimpleType(SimpleType typedef, String targetNamespace, boolean chameleon, String elemFormDefault, String attFormDefault, List anonymousTypes, SchemaType outerType) {
      StscState state = StscState.get();
      SchemaTypeImpl sType = new SchemaTypeImpl(state.getContainer(targetNamespace));
      sType.setSimpleType(true);
      sType.setParseContext(typedef, targetNamespace, chameleon, elemFormDefault, attFormDefault, false);
      sType.setOuterSchemaTypeRef(outerType.getRef());
      sType.setAnnotation(SchemaAnnotationImpl.getAnnotation(state.getContainer(targetNamespace), typedef));
      sType.setUserData(getUserData(typedef));
      anonymousTypes.add(sType);
      return sType;
   }

   static FormChoice findElementFormDefault(XmlObject obj) {
      XmlCursor cur = obj.newCursor();

      do {
         if (cur.getObject().schemaType() == SchemaDocument.Schema.type) {
            return ((SchemaDocument.Schema)cur.getObject()).xgetElementFormDefault();
         }
      } while(cur.toParent());

      return null;
   }

   public static boolean uriMatch(String s1, String s2) {
      if (s1 != null) {
         return s2 == null ? s1.equals("") : s1.equals(s2);
      } else {
         return s2 == null || s2.equals("");
      }
   }

   public static void copyGlobalElementToLocalElement(SchemaGlobalElement referenced, SchemaLocalElementImpl target) {
      target.setNameAndTypeRef(referenced.getName(), referenced.getType().getRef());
      target.setNillable(referenced.isNillable());
      target.setDefault(referenced.getDefaultText(), referenced.isFixed(), ((SchemaGlobalElementImpl)referenced).getParseObject());
      target.setIdentityConstraints(((SchemaLocalElementImpl)referenced).getIdentityConstraintRefs());
      target.setBlock(referenced.blockExtension(), referenced.blockRestriction(), referenced.blockSubstitution());
      target.setAbstract(referenced.isAbstract());
      target.setTransitionRules(((SchemaParticle)referenced).acceptedStartNames(), ((SchemaParticle)referenced).isSkippable());
      target.setAnnotation(referenced.getAnnotation());
   }

   public static void copyGlobalAttributeToLocalAttribute(SchemaGlobalAttributeImpl referenced, SchemaLocalAttributeImpl target) {
      target.init(referenced.getName(), referenced.getTypeRef(), referenced.getUse(), referenced.getDefaultText(), referenced.getParseObject(), referenced._defaultValue, referenced.isFixed(), referenced.getWSDLArrayType(), referenced.getAnnotation(), (Object)null);
   }

   public static SchemaLocalElementImpl translateElement(Element xsdElt, String targetNamespace, boolean chameleon, String elemFormDefault, String attFormDefault, List anonymousTypes, SchemaType outerType) {
      StscState state = StscState.get();
      SchemaTypeImpl sgHead = null;
      if (xsdElt.isSetSubstitutionGroup()) {
         sgHead = state.findDocumentType(xsdElt.getSubstitutionGroup(), ((SchemaTypeImpl)outerType).getChameleonNamespace(), targetNamespace);
         if (sgHead != null) {
            StscResolver.resolveType(sgHead);
         }
      }

      String name = xsdElt.getName();
      QName ref = xsdElt.getRef();
      if (ref != null && name != null) {
         state.error("src-element.2.1a", new Object[]{name}, xsdElt.xgetRef());
         name = null;
      }

      if (ref == null && name == null) {
         state.error("src-element.2.1b", (Object[])null, xsdElt);
         return null;
      } else {
         if (name != null && !XMLChar.isValidNCName(name)) {
            state.error("invalid-value", new Object[]{name, "name"}, xsdElt.xgetName());
         }

         if (ref != null) {
            if (xsdElt.getType() != null) {
               state.error("src-element.2.2", new Object[]{"type"}, xsdElt.xgetType());
            }

            if (xsdElt.getSimpleType() != null) {
               state.error("src-element.2.2", new Object[]{"<simpleType>"}, xsdElt.getSimpleType());
            }

            if (xsdElt.getComplexType() != null) {
               state.error("src-element.2.2", new Object[]{"<complexType>"}, xsdElt.getComplexType());
            }

            if (xsdElt.getForm() != null) {
               state.error("src-element.2.2", new Object[]{"form"}, xsdElt.xgetForm());
            }

            if (xsdElt.sizeOfKeyArray() > 0) {
               state.warning("src-element.2.2", new Object[]{"<key>"}, xsdElt);
            }

            if (xsdElt.sizeOfKeyrefArray() > 0) {
               state.warning("src-element.2.2", new Object[]{"<keyref>"}, xsdElt);
            }

            if (xsdElt.sizeOfUniqueArray() > 0) {
               state.warning("src-element.2.2", new Object[]{"<unique>"}, xsdElt);
            }

            if (xsdElt.isSetDefault()) {
               state.warning("src-element.2.2", new Object[]{"default"}, xsdElt.xgetDefault());
            }

            if (xsdElt.isSetFixed()) {
               state.warning("src-element.2.2", new Object[]{"fixed"}, xsdElt.xgetFixed());
            }

            if (xsdElt.isSetBlock()) {
               state.warning("src-element.2.2", new Object[]{"block"}, xsdElt.xgetBlock());
            }

            if (xsdElt.isSetNillable()) {
               state.warning("src-element.2.2", new Object[]{"nillable"}, xsdElt.xgetNillable());
            }

            assert xsdElt instanceof LocalElement;

            SchemaGlobalElement referenced = state.findGlobalElement(ref, chameleon ? targetNamespace : null, targetNamespace);
            if (referenced == null) {
               state.notFoundError(ref, 1, xsdElt.xgetRef(), true);
               return null;
            } else {
               SchemaLocalElementImpl target = new SchemaLocalElementImpl();
               target.setParticleType(4);
               target.setUserData(getUserData(xsdElt));
               copyGlobalElementToLocalElement(referenced, target);
               return target;
            }
         } else {
            SchemaType sType = null;
            QName qname;
            Object impl;
            if (xsdElt instanceof LocalElement) {
               impl = new SchemaLocalElementImpl();
               boolean qualified = false;
               FormChoice form = xsdElt.xgetForm();
               if (form != null) {
                  qualified = form.getStringValue().equals("qualified");
               } else if (elemFormDefault != null) {
                  qualified = elemFormDefault.equals("qualified");
               } else {
                  form = findElementFormDefault(xsdElt);
                  qualified = form != null && form.getStringValue().equals("qualified");
               }

               qname = qualified ? QNameHelper.forLNS(name, targetNamespace) : QNameHelper.forLN(name);
            } else {
               SchemaGlobalElementImpl gelt = new SchemaGlobalElementImpl(state.getContainer(targetNamespace));
               impl = gelt;
               if (sgHead != null) {
                  SchemaGlobalElementImpl head = state.findGlobalElement(xsdElt.getSubstitutionGroup(), chameleon ? targetNamespace : null, targetNamespace);
                  if (head != null) {
                     gelt.setSubstitutionGroup(head.getRef());
                  }
               }

               qname = QNameHelper.forLNS(name, targetNamespace);
               SchemaTypeImpl docType = (SchemaTypeImpl)outerType;
               QName[] sgMembers = docType.getSubstitutionGroupMembers();
               QNameSetBuilder transitionRules = new QNameSetBuilder();
               transitionRules.add(qname);

               for(int i = 0; i < sgMembers.length; ++i) {
                  gelt.addSubstitutionGroupMember(sgMembers[i]);
                  transitionRules.add(sgMembers[i]);
               }

               gelt.setTransitionRules(QNameSet.forSpecification(transitionRules), false);
               gelt.setTransitionNotes(QNameSet.EMPTY, true);
               boolean finalExt = false;
               boolean finalRest = false;
               Object ds = xsdElt.getFinal();
               if (ds != null) {
                  if (ds instanceof String && ds.equals("#all")) {
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

               gelt.setFinal(finalExt, finalRest);
               gelt.setAbstract(xsdElt.getAbstract());
               gelt.setFilename(findFilename(xsdElt));
               gelt.setParseContext(xsdElt, targetNamespace, chameleon);
            }

            SchemaAnnotationImpl ann = SchemaAnnotationImpl.getAnnotation(state.getContainer(targetNamespace), xsdElt);
            ((SchemaLocalElementImpl)impl).setAnnotation(ann);
            ((SchemaLocalElementImpl)impl).setUserData(getUserData(xsdElt));
            if (xsdElt.getType() != null) {
               sType = state.findGlobalType(xsdElt.getType(), chameleon ? targetNamespace : null, targetNamespace);
               if (sType == null) {
                  state.notFoundError(xsdElt.getType(), 0, xsdElt.xgetType(), true);
               }
            }

            boolean simpleTypedef = false;
            Annotated typedef = xsdElt.getComplexType();
            if (typedef == null) {
               typedef = xsdElt.getSimpleType();
               simpleTypedef = true;
            }

            if (sType != null && typedef != null) {
               state.error("src-element.3", (Object[])null, (XmlObject)typedef);
               typedef = null;
            }

            if (typedef != null) {
               Object[] grps = state.getCurrentProcessing();
               QName[] context = new QName[grps.length];

               for(int i = 0; i < context.length; ++i) {
                  if (grps[i] instanceof SchemaModelGroupImpl) {
                     context[i] = ((SchemaModelGroupImpl)grps[i]).getName();
                  }
               }

               SchemaType repeat = checkRecursiveGroupReference(context, qname, (SchemaTypeImpl)outerType);
               if (repeat != null) {
                  sType = repeat;
               } else {
                  SchemaTypeImpl sTypeImpl = new SchemaTypeImpl(state.getContainer(targetNamespace));
                  sType = sTypeImpl;
                  sTypeImpl.setContainerField((SchemaField)impl);
                  sTypeImpl.setOuterSchemaTypeRef(outerType == null ? null : outerType.getRef());
                  sTypeImpl.setGroupReferenceContext(context);
                  anonymousTypes.add(sTypeImpl);
                  sTypeImpl.setSimpleType(simpleTypedef);
                  sTypeImpl.setParseContext((XmlObject)typedef, targetNamespace, chameleon, elemFormDefault, attFormDefault, false);
                  sTypeImpl.setAnnotation(SchemaAnnotationImpl.getAnnotation(state.getContainer(targetNamespace), (Annotated)typedef));
                  sTypeImpl.setUserData(getUserData((XmlObject)typedef));
               }
            }

            if (sType == null && sgHead != null) {
               SchemaGlobalElement head = state.findGlobalElement(xsdElt.getSubstitutionGroup(), chameleon ? targetNamespace : null, targetNamespace);
               if (head != null) {
                  sType = head.getType();
               }
            }

            if (sType == null) {
               sType = BuiltinSchemaTypeSystem.ST_ANY_TYPE;
            }

            SOAPArrayType wat = null;
            XmlCursor c = xsdElt.newCursor();
            String arrayType = c.getAttributeText(WSDL_ARRAYTYPE_NAME);
            c.dispose();
            if (arrayType != null) {
               try {
                  wat = new SOAPArrayType(arrayType, new NamespaceContext(xsdElt));
               } catch (XmlValueOutOfRangeException var34) {
                  state.error("soaparray", new Object[]{arrayType}, xsdElt);
               }
            }

            ((SchemaLocalElementImpl)impl).setWsdlArrayType(wat);
            boolean isFixed = xsdElt.isSetFixed();
            if (xsdElt.isSetDefault() && isFixed) {
               state.error("src-element.1", (Object[])null, xsdElt.xgetFixed());
               isFixed = false;
            }

            ((SchemaLocalElementImpl)impl).setParticleType(4);
            ((SchemaLocalElementImpl)impl).setNameAndTypeRef(qname, ((SchemaType)sType).getRef());
            ((SchemaLocalElementImpl)impl).setNillable(xsdElt.getNillable());
            ((SchemaLocalElementImpl)impl).setDefault(isFixed ? xsdElt.getFixed() : xsdElt.getDefault(), isFixed, xsdElt);
            Object block = xsdElt.getBlock();
            boolean blockExt = false;
            boolean blockRest = false;
            boolean blockSubst = false;
            if (block != null) {
               if (block instanceof String && block.equals("#all")) {
                  blockSubst = true;
                  blockRest = true;
                  blockExt = true;
               } else if (block instanceof List) {
                  if (((List)block).contains("extension")) {
                     blockExt = true;
                  }

                  if (((List)block).contains("restriction")) {
                     blockRest = true;
                  }

                  if (((List)block).contains("substitution")) {
                     blockSubst = true;
                  }
               }
            }

            ((SchemaLocalElementImpl)impl).setBlock(blockExt, blockRest, blockSubst);
            boolean constraintFailed = false;
            int length = xsdElt.sizeOfKeyArray() + xsdElt.sizeOfKeyrefArray() + xsdElt.sizeOfUniqueArray();
            SchemaIdentityConstraintImpl[] constraints = new SchemaIdentityConstraintImpl[length];
            int cur = 0;
            Keybase[] keys = xsdElt.getKeyArray();

            for(int i = 0; i < keys.length; ++cur) {
               constraints[cur] = translateIdentityConstraint(keys[i], targetNamespace, chameleon);
               if (constraints[cur] != null) {
                  constraints[cur].setConstraintCategory(1);
               } else {
                  constraintFailed = true;
               }

               ++i;
            }

            Keybase[] uc = xsdElt.getUniqueArray();

            for(int i = 0; i < uc.length; ++cur) {
               constraints[cur] = translateIdentityConstraint(uc[i], targetNamespace, chameleon);
               if (constraints[cur] != null) {
                  constraints[cur].setConstraintCategory(3);
               } else {
                  constraintFailed = true;
               }

               ++i;
            }

            KeyrefDocument.Keyref[] krs = xsdElt.getKeyrefArray();

            for(int i = 0; i < krs.length; ++cur) {
               constraints[cur] = translateIdentityConstraint(krs[i], targetNamespace, chameleon);
               if (constraints[cur] != null) {
                  constraints[cur].setConstraintCategory(2);
               } else {
                  constraintFailed = true;
               }

               ++i;
            }

            if (!constraintFailed) {
               SchemaIdentityConstraint.Ref[] refs = new SchemaIdentityConstraint.Ref[length];

               for(int i = 0; i < refs.length; ++i) {
                  refs[i] = constraints[i].getRef();
               }

               ((SchemaLocalElementImpl)impl).setIdentityConstraints(refs);
            }

            return (SchemaLocalElementImpl)impl;
         }
      }
   }

   private static SchemaType checkRecursiveGroupReference(QName[] context, QName containingElement, SchemaTypeImpl outerType) {
      if (context.length < 1) {
         return null;
      } else {
         for(SchemaTypeImpl type = outerType; type != null; type = (SchemaTypeImpl)type.getOuterType()) {
            if (type.getName() != null || type.isDocumentType()) {
               return null;
            }

            if (containingElement.equals(type.getContainerField().getName())) {
               QName[] outerContext = type.getGroupReferenceContext();
               if (outerContext != null && outerContext.length == context.length) {
                  boolean equal = true;

                  for(int i = 0; i < context.length; ++i) {
                     if ((context[i] != null || outerContext[i] != null) && (context[i] == null || !context[i].equals(outerContext[i]))) {
                        equal = false;
                        break;
                     }
                  }

                  if (equal) {
                     return type;
                  }
               }
            }
         }

         return null;
      }
   }

   private static String removeWhitespace(String xpath) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < xpath.length(); ++i) {
         char ch = xpath.charAt(i);
         if (!XMLChar.isSpace(ch)) {
            sb.append(ch);
         }
      }

      return sb.toString();
   }

   private static boolean checkXPathSyntax(String xpath) {
      if (xpath == null) {
         return false;
      } else {
         xpath = removeWhitespace(xpath);
         synchronized(XPATH_REGEXP) {
            return XPATH_REGEXP.matches(xpath);
         }
      }
   }

   private static SchemaIdentityConstraintImpl translateIdentityConstraint(Keybase parseIC, String targetNamespace, boolean chameleon) {
      StscState state = StscState.get();
      String selector = parseIC.getSelector() == null ? null : parseIC.getSelector().getXpath();
      if (!checkXPathSyntax(selector)) {
         state.error("c-selector-xpath", new Object[]{selector}, parseIC.getSelector().xgetXpath());
         return null;
      } else {
         FieldDocument.Field[] fieldElts = parseIC.getFieldArray();

         for(int j = 0; j < fieldElts.length; ++j) {
            if (!checkXPathSyntax(fieldElts[j].getXpath())) {
               state.error("c-fields-xpaths", new Object[]{fieldElts[j].getXpath()}, fieldElts[j].xgetXpath());
               return null;
            }
         }

         SchemaIdentityConstraintImpl ic = new SchemaIdentityConstraintImpl(state.getContainer(targetNamespace));
         ic.setName(QNameHelper.forLNS(parseIC.getName(), targetNamespace));
         ic.setSelector(parseIC.getSelector().getXpath());
         ic.setParseContext(parseIC, targetNamespace, chameleon);
         SchemaAnnotationImpl ann = SchemaAnnotationImpl.getAnnotation(state.getContainer(targetNamespace), parseIC);
         ic.setAnnotation(ann);
         ic.setUserData(getUserData(parseIC));
         XmlCursor c = parseIC.newCursor();
         Map nsMap = new HashMap();
         c.getAllNamespaces(nsMap);
         nsMap.remove("");
         ic.setNSMap(nsMap);
         c.dispose();
         String[] fields = new String[fieldElts.length];

         for(int j = 0; j < fields.length; ++j) {
            fields[j] = fieldElts[j].getXpath();
         }

         ic.setFields(fields);

         try {
            ic.buildPaths();
         } catch (XPath.XPathCompileException var12) {
            state.error("invalid-xpath", new Object[]{var12.getMessage()}, parseIC);
            return null;
         }

         state.addIdConstraint(ic);
         ic.setFilename(findFilename(parseIC));
         return state.findIdConstraint(ic.getName(), targetNamespace, (String)null);
      }
   }

   public static SchemaModelGroupImpl translateModelGroup(NamedGroup namedGroup, String targetNamespace, boolean chameleon, boolean redefinition) {
      String name = namedGroup.getName();
      if (name == null) {
         StscState.get().error("missing-name", new Object[]{"model group"}, namedGroup);
         return null;
      } else {
         SchemaContainer c = StscState.get().getContainer(targetNamespace);
         SchemaModelGroupImpl result = new SchemaModelGroupImpl(c);
         SchemaAnnotationImpl ann = SchemaAnnotationImpl.getAnnotation(c, namedGroup);
         FormChoice elemFormDefault = findElementFormDefault(namedGroup);
         FormChoice attFormDefault = findAttributeFormDefault(namedGroup);
         result.init(QNameHelper.forLNS(name, targetNamespace), targetNamespace, chameleon, elemFormDefault == null ? null : elemFormDefault.getStringValue(), attFormDefault == null ? null : attFormDefault.getStringValue(), redefinition, namedGroup, ann, getUserData(namedGroup));
         result.setFilename(findFilename(namedGroup));
         return result;
      }
   }

   public static SchemaAttributeGroupImpl translateAttributeGroup(AttributeGroup attrGroup, String targetNamespace, boolean chameleon, boolean redefinition) {
      String name = attrGroup.getName();
      if (name == null) {
         StscState.get().error("missing-name", new Object[]{"attribute group"}, attrGroup);
         return null;
      } else {
         SchemaContainer c = StscState.get().getContainer(targetNamespace);
         SchemaAttributeGroupImpl result = new SchemaAttributeGroupImpl(c);
         SchemaAnnotationImpl ann = SchemaAnnotationImpl.getAnnotation(c, attrGroup);
         FormChoice formDefault = findAttributeFormDefault(attrGroup);
         result.init(QNameHelper.forLNS(name, targetNamespace), targetNamespace, chameleon, formDefault == null ? null : formDefault.getStringValue(), redefinition, attrGroup, ann, getUserData(attrGroup));
         result.setFilename(findFilename(attrGroup));
         return result;
      }
   }

   static FormChoice findAttributeFormDefault(XmlObject obj) {
      XmlCursor cur = obj.newCursor();

      do {
         if (cur.getObject().schemaType() == SchemaDocument.Schema.type) {
            return ((SchemaDocument.Schema)cur.getObject()).xgetAttributeFormDefault();
         }
      } while(cur.toParent());

      return null;
   }

   static SchemaLocalAttributeImpl translateAttribute(Attribute xsdAttr, String targetNamespace, String formDefault, boolean chameleon, List anonymousTypes, SchemaType outerType, SchemaAttributeModel baseModel, boolean local) {
      StscState state = StscState.get();
      String name = xsdAttr.getName();
      QName ref = xsdAttr.getRef();
      if (ref != null && name != null) {
         if (name.equals(ref.getLocalPart()) && uriMatch(targetNamespace, ref.getNamespaceURI())) {
            state.warning("src-attribute.3.1a", new Object[]{name}, xsdAttr.xgetRef());
         } else {
            state.error("src-attribute.3.1a", new Object[]{name}, xsdAttr.xgetRef());
         }

         name = null;
      }

      if (ref == null && name == null) {
         state.error("src-attribute.3.1b", (Object[])null, xsdAttr);
         return null;
      } else {
         if (name != null && !XMLChar.isValidNCName(name)) {
            state.error("invalid-value", new Object[]{name, "name"}, xsdAttr.xgetName());
         }

         boolean isFixed = false;
         String deftext = null;
         String fmrfixedtext = null;
         SchemaType sType = null;
         int use = 2;
         Object sAttr;
         if (local) {
            sAttr = new SchemaLocalAttributeImpl();
         } else {
            sAttr = new SchemaGlobalAttributeImpl(StscState.get().getContainer(targetNamespace));
            ((SchemaGlobalAttributeImpl)sAttr).setParseContext(xsdAttr, targetNamespace, chameleon);
         }

         QName qname;
         if (ref != null) {
            if (xsdAttr.getType() != null) {
               state.error("src-attribute.3.2", new Object[]{"type"}, xsdAttr.xgetType());
            }

            if (xsdAttr.getSimpleType() != null) {
               state.error("src-attribute.3.2", new Object[]{"<simpleType>"}, xsdAttr.getSimpleType());
            }

            if (xsdAttr.getForm() != null) {
               state.error("src-attribute.3.2", new Object[]{"form"}, xsdAttr.xgetForm());
            }

            SchemaGlobalAttribute referenced = state.findGlobalAttribute(ref, chameleon ? targetNamespace : null, targetNamespace);
            if (referenced == null) {
               state.notFoundError(ref, 3, xsdAttr.xgetRef(), true);
               return null;
            }

            qname = ref;
            use = referenced.getUse();
            sType = referenced.getType();
            deftext = referenced.getDefaultText();
            if (deftext != null) {
               isFixed = referenced.isFixed();
               if (isFixed) {
                  fmrfixedtext = deftext;
               }
            }
         } else {
            if (!local) {
               qname = QNameHelper.forLNS(name, targetNamespace);
            } else {
               boolean qualified = false;
               FormChoice form = xsdAttr.xgetForm();
               if (form != null) {
                  qualified = form.getStringValue().equals("qualified");
               } else if (formDefault != null) {
                  qualified = formDefault.equals("qualified");
               } else {
                  form = findAttributeFormDefault(xsdAttr);
                  qualified = form != null && form.getStringValue().equals("qualified");
               }

               qname = qualified ? QNameHelper.forLNS(name, targetNamespace) : QNameHelper.forLN(name);
            }

            if (xsdAttr.getType() != null) {
               sType = state.findGlobalType(xsdAttr.getType(), chameleon ? targetNamespace : null, targetNamespace);
               if (sType == null) {
                  state.notFoundError(xsdAttr.getType(), 0, xsdAttr.xgetType(), true);
               }
            }

            if (qname.getNamespaceURI().equals("http://www.w3.org/2001/XMLSchema-instance")) {
               state.error("no-xsi", new Object[]{"http://www.w3.org/2001/XMLSchema-instance"}, xsdAttr.xgetName());
            }

            if (qname.getNamespaceURI().length() == 0 && qname.getLocalPart().equals("xmlns")) {
               state.error("no-xmlns", (Object[])null, xsdAttr.xgetName());
            }

            LocalSimpleType typedef = xsdAttr.getSimpleType();
            if (sType != null && typedef != null) {
               state.error("src-attribute.4", (Object[])null, typedef);
               typedef = null;
            }

            if (typedef != null) {
               SchemaTypeImpl sTypeImpl = new SchemaTypeImpl(state.getContainer(targetNamespace));
               sType = sTypeImpl;
               sTypeImpl.setContainerField((SchemaField)sAttr);
               sTypeImpl.setOuterSchemaTypeRef(outerType == null ? null : outerType.getRef());
               anonymousTypes.add(sTypeImpl);
               sTypeImpl.setSimpleType(true);
               sTypeImpl.setParseContext(typedef, targetNamespace, chameleon, (String)null, (String)null, false);
               sTypeImpl.setAnnotation(SchemaAnnotationImpl.getAnnotation(state.getContainer(targetNamespace), typedef));
               sTypeImpl.setUserData(getUserData(typedef));
            }

            if (sType == null && baseModel != null && baseModel.getAttribute(qname) != null) {
               sType = baseModel.getAttribute(qname).getType();
            }
         }

         if (sType == null) {
            sType = BuiltinSchemaTypeSystem.ST_ANY_SIMPLE;
         }

         if (!((SchemaType)sType).isSimpleType()) {
            state.error("Attributes must have a simple type (not complex).", 46, xsdAttr);
            sType = BuiltinSchemaTypeSystem.ST_ANY_SIMPLE;
         }

         if (xsdAttr.isSetUse()) {
            use = translateUseCode(xsdAttr.xgetUse());
            if (use != 2 && !isFixed) {
               deftext = null;
            }
         }

         if (xsdAttr.isSetDefault() || xsdAttr.isSetFixed()) {
            if (isFixed && !xsdAttr.isSetFixed()) {
               state.error("A use of a fixed attribute definition must also be fixed", 9, xsdAttr.xgetFixed());
            }

            isFixed = xsdAttr.isSetFixed();
            if (xsdAttr.isSetDefault() && isFixed) {
               state.error("src-attribute.1", (Object[])null, xsdAttr.xgetFixed());
               isFixed = false;
            }

            deftext = isFixed ? xsdAttr.getFixed() : xsdAttr.getDefault();
            if (fmrfixedtext != null && !fmrfixedtext.equals(deftext)) {
               state.error("au-value_constraint", (Object[])null, xsdAttr.xgetFixed());
               deftext = fmrfixedtext;
            }
         }

         if (!local) {
            ((SchemaGlobalAttributeImpl)sAttr).setFilename(findFilename(xsdAttr));
         }

         SOAPArrayType wat = null;
         XmlCursor c = xsdAttr.newCursor();
         String arrayType = c.getAttributeText(WSDL_ARRAYTYPE_NAME);
         c.dispose();
         if (arrayType != null) {
            try {
               wat = new SOAPArrayType(arrayType, new NamespaceContext(xsdAttr));
            } catch (XmlValueOutOfRangeException var22) {
               state.error("soaparray", new Object[]{arrayType}, xsdAttr);
            }
         }

         SchemaAnnotationImpl ann = SchemaAnnotationImpl.getAnnotation(state.getContainer(targetNamespace), xsdAttr);
         ((SchemaLocalAttributeImpl)sAttr).init(qname, ((SchemaType)sType).getRef(), use, deftext, xsdAttr, (XmlValueRef)null, isFixed, wat, ann, getUserData(xsdAttr));
         return (SchemaLocalAttributeImpl)sAttr;
      }
   }

   static int translateUseCode(Attribute.Use attruse) {
      if (attruse == null) {
         return 2;
      } else {
         String val = attruse.getStringValue();
         if (val.equals("optional")) {
            return 2;
         } else if (val.equals("required")) {
            return 3;
         } else {
            return val.equals("prohibited") ? 1 : 2;
         }
      }
   }

   static BigInteger buildBigInt(XmlAnySimpleType value) {
      if (value == null) {
         return null;
      } else {
         String text = value.getStringValue();

         BigInteger bigInt;
         try {
            bigInt = new BigInteger(text);
         } catch (NumberFormatException var4) {
            StscState.get().error("invalid-value-detail", new Object[]{text, "nonNegativeInteger", var4.getMessage()}, value);
            return null;
         }

         if (bigInt.signum() < 0) {
            StscState.get().error("invalid-value", new Object[]{text, "nonNegativeInteger"}, value);
            return null;
         } else {
            return bigInt;
         }
      }
   }

   static XmlNonNegativeInteger buildNnInteger(XmlAnySimpleType value) {
      BigInteger bigInt = buildBigInt(value);

      try {
         XmlNonNegativeIntegerImpl i = new XmlNonNegativeIntegerImpl();
         i.set(bigInt);
         i.setImmutable();
         return i;
      } catch (XmlValueOutOfRangeException var3) {
         StscState.get().error("Internal error processing number", 21, value);
         return null;
      }
   }

   static XmlPositiveInteger buildPosInteger(XmlAnySimpleType value) {
      BigInteger bigInt = buildBigInt(value);

      try {
         XmlPositiveIntegerImpl i = new XmlPositiveIntegerImpl();
         i.set(bigInt);
         i.setImmutable();
         return i;
      } catch (XmlValueOutOfRangeException var3) {
         StscState.get().error("Internal error processing number", 21, value);
         return null;
      }
   }

   private static Object getUserData(XmlObject pos) {
      XmlCursor.XmlBookmark b = pos.newCursor().getBookmark(SchemaBookmark.class);
      return b != null && b instanceof SchemaBookmark ? ((SchemaBookmark)b).getValue() : null;
   }

   private static boolean isEmptySchema(SchemaDocument.Schema schema) {
      XmlCursor cursor = schema.newCursor();
      boolean result = !cursor.toFirstChild();
      cursor.dispose();
      return result;
   }

   private static boolean isReservedTypeName(QName name) {
      return BuiltinSchemaTypeSystem.get().findType(name) != null;
   }

   private static class RedefinitionMaster {
      private Map stRedefinitions;
      private Map ctRedefinitions;
      private Map agRedefinitions;
      private Map mgRedefinitions;
      private static final RedefinitionHolder[] EMPTY_REDEFINTION_HOLDER_ARRAY = new RedefinitionHolder[0];
      private static final short SIMPLE_TYPE = 1;
      private static final short COMPLEX_TYPE = 2;
      private static final short MODEL_GROUP = 3;
      private static final short ATTRIBUTE_GROUP = 4;

      RedefinitionMaster(RedefinitionHolder[] redefHolders) {
         this.stRedefinitions = Collections.EMPTY_MAP;
         this.ctRedefinitions = Collections.EMPTY_MAP;
         this.agRedefinitions = Collections.EMPTY_MAP;
         this.mgRedefinitions = Collections.EMPTY_MAP;
         if (redefHolders.length > 0) {
            this.stRedefinitions = new HashMap();
            this.ctRedefinitions = new HashMap();
            this.agRedefinitions = new HashMap();
            this.mgRedefinitions = new HashMap();

            for(int i = 0; i < redefHolders.length; ++i) {
               RedefinitionHolder redefHolder = redefHolders[i];

               Iterator it;
               Object key;
               Object redefinedIn;
               for(it = redefHolder.stRedefinitions.keySet().iterator(); it.hasNext(); ((List)redefinedIn).add(redefHolders[i])) {
                  key = it.next();
                  redefinedIn = (List)this.stRedefinitions.get(key);
                  if (redefinedIn == null) {
                     redefinedIn = new ArrayList();
                     this.stRedefinitions.put(key, redefinedIn);
                  }
               }

               for(it = redefHolder.ctRedefinitions.keySet().iterator(); it.hasNext(); ((List)redefinedIn).add(redefHolders[i])) {
                  key = it.next();
                  redefinedIn = (List)this.ctRedefinitions.get(key);
                  if (redefinedIn == null) {
                     redefinedIn = new ArrayList();
                     this.ctRedefinitions.put(key, redefinedIn);
                  }
               }

               for(it = redefHolder.agRedefinitions.keySet().iterator(); it.hasNext(); ((List)redefinedIn).add(redefHolders[i])) {
                  key = it.next();
                  redefinedIn = (List)this.agRedefinitions.get(key);
                  if (redefinedIn == null) {
                     redefinedIn = new ArrayList();
                     this.agRedefinitions.put(key, redefinedIn);
                  }
               }

               for(it = redefHolder.mgRedefinitions.keySet().iterator(); it.hasNext(); ((List)redefinedIn).add(redefHolders[i])) {
                  key = it.next();
                  redefinedIn = (List)this.mgRedefinitions.get(key);
                  if (redefinedIn == null) {
                     redefinedIn = new ArrayList();
                     this.mgRedefinitions.put(key, redefinedIn);
                  }
               }
            }
         }

      }

      RedefinitionHolder[] getSimpleTypeRedefinitions(String name, StscImporter.SchemaToProcess schema) {
         List redefines = (List)this.stRedefinitions.get(name);
         return redefines == null ? EMPTY_REDEFINTION_HOLDER_ARRAY : this.doTopologicalSort(redefines, schema, name, (short)1);
      }

      RedefinitionHolder[] getComplexTypeRedefinitions(String name, StscImporter.SchemaToProcess schema) {
         List redefines = (List)this.ctRedefinitions.get(name);
         return redefines == null ? EMPTY_REDEFINTION_HOLDER_ARRAY : this.doTopologicalSort(redefines, schema, name, (short)2);
      }

      RedefinitionHolder[] getAttributeGroupRedefinitions(String name, StscImporter.SchemaToProcess schema) {
         List redefines = (List)this.agRedefinitions.get(name);
         return redefines == null ? EMPTY_REDEFINTION_HOLDER_ARRAY : this.doTopologicalSort(redefines, schema, name, (short)4);
      }

      RedefinitionHolder[] getModelGroupRedefinitions(String name, StscImporter.SchemaToProcess schema) {
         List redefines = (List)this.mgRedefinitions.get(name);
         return redefines == null ? EMPTY_REDEFINTION_HOLDER_ARRAY : this.doTopologicalSort(redefines, schema, name, (short)3);
      }

      private RedefinitionHolder[] doTopologicalSort(List genericRedefines, StscImporter.SchemaToProcess schema, String name, short componentType) {
         RedefinitionHolder[] specificRedefines = new RedefinitionHolder[genericRedefines.size()];
         int n = 0;

         for(int i = 0; i < genericRedefines.size(); ++i) {
            RedefinitionHolder h = (RedefinitionHolder)genericRedefines.get(i);
            if (h.schemaRedefined == schema || h.schemaRedefined.indirectIncludes(schema)) {
               specificRedefines[n++] = h;
            }
         }

         RedefinitionHolder[] sortedRedefines = new RedefinitionHolder[n];
         int[] numberOfIncludes = new int[n];

         int i;
         int index;
         int var10002;
         for(i = 0; i < n - 1; ++i) {
            RedefinitionHolder current = specificRedefines[i];

            for(index = i + 1; index < n; ++index) {
               if (current.schemaRedefined.indirectIncludes(specificRedefines[index].schemaRedefined)) {
                  var10002 = numberOfIncludes[i]++;
               }

               if (specificRedefines[index].schemaRedefined.indirectIncludes(current.schemaRedefined)) {
                  var10002 = numberOfIncludes[index]++;
               }
            }
         }

         i = 0;
         boolean errorReported = false;

         while(true) {
            int i;
            while(i < n) {
               index = -1;

               for(i = 0; i < numberOfIncludes.length; ++i) {
                  if (numberOfIncludes[i] == 0 && index < 0) {
                     index = i;
                  }
               }

               if (index < 0) {
                  if (!errorReported) {
                     StringBuffer fileNameList = new StringBuffer();
                     XmlObject location = null;

                     for(int i = 0; i < n; ++i) {
                        if (specificRedefines[i] != null) {
                           fileNameList.append(specificRedefines[i].schemaLocation).append(',').append(' ');
                           if (location == null) {
                              location = this.locationFromRedefinitionAndCode(specificRedefines[i], name, componentType);
                           }
                        }
                     }

                     StscState.get().error("Detected circular redefinition of " + this.componentNameFromCode(componentType) + " \"" + name + "\"; Files involved: " + fileNameList.toString(), 60, location);
                     errorReported = true;
                  }

                  i = n;

                  for(int i = 0; i < n; ++i) {
                     if (numberOfIncludes[i] > 0 && numberOfIncludes[i] < i) {
                        i = numberOfIncludes[i];
                        index = i;
                     }
                  }

                  var10002 = numberOfIncludes[index]--;
               } else {
                  assert specificRedefines[index] != null;

                  sortedRedefines[i++] = specificRedefines[index];

                  for(i = 0; i < n; ++i) {
                     if (specificRedefines[i] != null && specificRedefines[i].schemaRedefined.indirectIncludes(specificRedefines[index].schemaRedefined)) {
                        var10002 = numberOfIncludes[i]--;
                     }
                  }

                  specificRedefines[index] = null;
                  var10002 = numberOfIncludes[index]--;
               }
            }

            for(index = 1; index < n; ++index) {
               for(i = index - 1; i >= 0 && sortedRedefines[i] == null; --i) {
               }

               if (!sortedRedefines[index].schemaRedefined.indirectIncludes(sortedRedefines[i].schemaRedefined)) {
                  StscState.get().error("Detected multiple redefinitions of " + this.componentNameFromCode(componentType) + " \"" + name + "\"; Files involved: " + sortedRedefines[i].schemaRedefined.getSourceName() + ", " + sortedRedefines[index].schemaRedefined.getSourceName(), 49, this.locationFromRedefinitionAndCode(sortedRedefines[index], name, componentType));
                  switch (componentType) {
                     case 1:
                        sortedRedefines[index].redefineSimpleType(name);
                        break;
                     case 2:
                        sortedRedefines[index].redefineComplexType(name);
                        break;
                     case 3:
                        sortedRedefines[index].redefineModelGroup(name);
                        break;
                     case 4:
                        sortedRedefines[index].redefineAttributeGroup(name);
                  }

                  sortedRedefines[index] = null;
               }
            }

            return sortedRedefines;
         }
      }

      private String componentNameFromCode(short code) {
         String componentName;
         switch (code) {
            case 1:
               componentName = "simple type";
               break;
            case 2:
               componentName = "complex type";
               break;
            case 3:
               componentName = "model group";
               break;
            case 4:
               componentName = "attribute group";
               break;
            default:
               componentName = "";
         }

         return componentName;
      }

      private XmlObject locationFromRedefinitionAndCode(RedefinitionHolder redefinition, String name, short code) {
         XmlObject location;
         switch (code) {
            case 1:
               location = (XmlObject)redefinition.stRedefinitions.get(name);
               break;
            case 2:
               location = (XmlObject)redefinition.ctRedefinitions.get(name);
               break;
            case 3:
               location = (XmlObject)redefinition.mgRedefinitions.get(name);
               break;
            case 4:
               location = (XmlObject)redefinition.agRedefinitions.get(name);
               break;
            default:
               location = null;
         }

         return location;
      }
   }

   private static class RedefinitionHolder {
      private Map stRedefinitions;
      private Map ctRedefinitions;
      private Map agRedefinitions;
      private Map mgRedefinitions;
      private String schemaLocation;
      private StscImporter.SchemaToProcess schemaRedefined;

      RedefinitionHolder(StscImporter.SchemaToProcess schemaToProcess, RedefineDocument.Redefine redefine) {
         this.stRedefinitions = Collections.EMPTY_MAP;
         this.ctRedefinitions = Collections.EMPTY_MAP;
         this.agRedefinitions = Collections.EMPTY_MAP;
         this.mgRedefinitions = Collections.EMPTY_MAP;
         this.schemaLocation = "";
         this.schemaRedefined = schemaToProcess;
         if (redefine != null) {
            StscState state = StscState.get();
            this.stRedefinitions = new HashMap();
            this.ctRedefinitions = new HashMap();
            this.agRedefinitions = new HashMap();
            this.mgRedefinitions = new HashMap();
            if (redefine.getSchemaLocation() != null) {
               this.schemaLocation = redefine.getSchemaLocation();
            }

            TopLevelComplexType[] complexTypes = redefine.getComplexTypeArray();

            for(int i = 0; i < complexTypes.length; ++i) {
               if (complexTypes[i].getName() != null) {
                  if (this.ctRedefinitions.containsKey(complexTypes[i].getName())) {
                     state.error("Duplicate type redefinition: " + complexTypes[i].getName(), 49, (XmlObject)null);
                  } else {
                     this.ctRedefinitions.put(complexTypes[i].getName(), complexTypes[i]);
                  }
               }
            }

            TopLevelSimpleType[] simpleTypes = redefine.getSimpleTypeArray();

            for(int i = 0; i < simpleTypes.length; ++i) {
               if (simpleTypes[i].getName() != null) {
                  if (this.stRedefinitions.containsKey(simpleTypes[i].getName())) {
                     state.error("Duplicate type redefinition: " + simpleTypes[i].getName(), 49, (XmlObject)null);
                  } else {
                     this.stRedefinitions.put(simpleTypes[i].getName(), simpleTypes[i]);
                  }
               }
            }

            NamedGroup[] modelgroups = redefine.getGroupArray();

            for(int i = 0; i < modelgroups.length; ++i) {
               if (modelgroups[i].getName() != null) {
                  if (this.mgRedefinitions.containsKey(modelgroups[i].getName())) {
                     state.error("Duplicate type redefinition: " + modelgroups[i].getName(), 49, (XmlObject)null);
                  } else {
                     this.mgRedefinitions.put(modelgroups[i].getName(), modelgroups[i]);
                  }
               }
            }

            NamedAttributeGroup[] attrgroups = redefine.getAttributeGroupArray();

            for(int i = 0; i < attrgroups.length; ++i) {
               if (attrgroups[i].getName() != null) {
                  if (this.agRedefinitions.containsKey(attrgroups[i].getName())) {
                     state.error("Duplicate type redefinition: " + attrgroups[i].getName(), 49, (XmlObject)null);
                  } else {
                     this.agRedefinitions.put(attrgroups[i].getName(), attrgroups[i]);
                  }
               }
            }
         }

      }

      public TopLevelSimpleType redefineSimpleType(String name) {
         return name != null && this.stRedefinitions.containsKey(name) ? (TopLevelSimpleType)this.stRedefinitions.remove(name) : null;
      }

      public TopLevelComplexType redefineComplexType(String name) {
         return name != null && this.ctRedefinitions.containsKey(name) ? (TopLevelComplexType)this.ctRedefinitions.remove(name) : null;
      }

      public NamedGroup redefineModelGroup(String name) {
         return name != null && this.mgRedefinitions.containsKey(name) ? (NamedGroup)this.mgRedefinitions.remove(name) : null;
      }

      public NamedAttributeGroup redefineAttributeGroup(String name) {
         return name != null && this.agRedefinitions.containsKey(name) ? (NamedAttributeGroup)this.agRedefinitions.remove(name) : null;
      }

      public void complainAboutMissingDefinitions() {
         if (!this.stRedefinitions.isEmpty() || !this.ctRedefinitions.isEmpty() || !this.agRedefinitions.isEmpty() || !this.mgRedefinitions.isEmpty()) {
            StscState state = StscState.get();
            Iterator i = this.stRedefinitions.keySet().iterator();

            String name;
            while(i.hasNext()) {
               name = (String)i.next();
               state.error("Redefined simple type " + name + " not found in " + this.schemaLocation, 60, (XmlObject)this.stRedefinitions.get(name));
            }

            i = this.ctRedefinitions.keySet().iterator();

            while(i.hasNext()) {
               name = (String)i.next();
               state.error("Redefined complex type " + name + " not found in " + this.schemaLocation, 60, (XmlObject)this.ctRedefinitions.get(name));
            }

            i = this.agRedefinitions.keySet().iterator();

            while(i.hasNext()) {
               name = (String)i.next();
               state.error("Redefined attribute group " + name + " not found in " + this.schemaLocation, 60, (XmlObject)this.agRedefinitions.get(name));
            }

            i = this.mgRedefinitions.keySet().iterator();

            while(i.hasNext()) {
               name = (String)i.next();
               state.error("Redefined model group " + name + " not found in " + this.schemaLocation, 60, (XmlObject)this.mgRedefinitions.get(name));
            }

         }
      }
   }
}
