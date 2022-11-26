package org.apache.xmlbeans.impl.schema;

import java.io.File;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.xml.namespace.QName;
import org.apache.xmlbeans.Filer;
import org.apache.xmlbeans.QNameSet;
import org.apache.xmlbeans.SchemaAnnotation;
import org.apache.xmlbeans.SchemaAttributeGroup;
import org.apache.xmlbeans.SchemaComponent;
import org.apache.xmlbeans.SchemaGlobalAttribute;
import org.apache.xmlbeans.SchemaGlobalElement;
import org.apache.xmlbeans.SchemaIdentityConstraint;
import org.apache.xmlbeans.SchemaModelGroup;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.SchemaTypeSystem;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.soap.SOAPArrayType;

public class SoapEncSchemaTypeSystem extends SchemaTypeLoaderBase implements SchemaTypeSystem {
   public static final String SOAPENC = "http://schemas.xmlsoap.org/soap/encoding/";
   public static final String SOAP_ARRAY = "Array";
   public static final String ARRAY_TYPE = "arrayType";
   private static final String ATTR_ID = "id";
   private static final String ATTR_HREF = "href";
   private static final String ATTR_OFFSET = "offset";
   private static final SchemaType[] EMPTY_SCHEMATYPE_ARRAY = new SchemaType[0];
   private static final SchemaGlobalElement[] EMPTY_SCHEMAELEMENT_ARRAY = new SchemaGlobalElement[0];
   private static final SchemaModelGroup[] EMPTY_SCHEMAMODELGROUP_ARRAY = new SchemaModelGroup[0];
   private static final SchemaAttributeGroup[] EMPTY_SCHEMAATTRIBUTEGROUP_ARRAY = new SchemaAttributeGroup[0];
   private static final SchemaAnnotation[] EMPTY_SCHEMAANNOTATION_ARRAY = new SchemaAnnotation[0];
   private static SoapEncSchemaTypeSystem _global = new SoapEncSchemaTypeSystem();
   private SchemaTypeImpl soapArray;
   private SchemaGlobalAttributeImpl arrayType;
   private Map _handlesToObjects = new HashMap();
   private String soapArrayHandle;
   private SchemaContainer _container = new SchemaContainer("http://schemas.xmlsoap.org/soap/encoding/");

   public static SchemaTypeSystem get() {
      return _global;
   }

   private SoapEncSchemaTypeSystem() {
      this._container.setTypeSystem(this);
      this.soapArray = new SchemaTypeImpl(this._container, true);
      this._container.addGlobalType(this.soapArray.getRef());
      this.soapArray.setName(new QName("http://schemas.xmlsoap.org/soap/encoding/", "Array"));
      this.soapArrayHandle = "Array".toLowerCase() + "type";
      this.soapArray.setComplexTypeVariety(3);
      this.soapArray.setBaseTypeRef(BuiltinSchemaTypeSystem.ST_ANY_TYPE.getRef());
      this.soapArray.setBaseDepth(1);
      this.soapArray.setDerivationType(2);
      this.soapArray.setSimpleTypeVariety(0);
      SchemaParticleImpl contentModel = new SchemaParticleImpl();
      contentModel.setParticleType(3);
      contentModel.setMinOccurs(BigInteger.ZERO);
      contentModel.setMaxOccurs(BigInteger.ONE);
      contentModel.setTransitionRules(QNameSet.ALL, true);
      SchemaParticleImpl[] children = new SchemaParticleImpl[1];
      contentModel.setParticleChildren(children);
      SchemaParticleImpl contentModel2 = new SchemaParticleImpl();
      contentModel2.setParticleType(5);
      contentModel2.setWildcardSet(QNameSet.ALL);
      contentModel2.setWildcardProcess(2);
      contentModel2.setMinOccurs(BigInteger.ZERO);
      contentModel2.setMaxOccurs((BigInteger)null);
      contentModel2.setTransitionRules(QNameSet.ALL, true);
      children[0] = contentModel2;
      SchemaAttributeModelImpl attrModel = new SchemaAttributeModelImpl();
      attrModel.setWildcardProcess(2);
      HashSet excludedURI = new HashSet();
      excludedURI.add("http://schemas.xmlsoap.org/soap/encoding/");
      attrModel.setWildcardSet(QNameSet.forSets(excludedURI, (Set)null, Collections.EMPTY_SET, Collections.EMPTY_SET));
      SchemaLocalAttributeImpl attr = new SchemaLocalAttributeImpl();
      attr.init(new QName("", "id"), BuiltinSchemaTypeSystem.ST_ID.getRef(), 2, (String)null, (XmlObject)null, (XmlValueRef)null, false, (SOAPArrayType)null, (SchemaAnnotation)null, (Object)null);
      attrModel.addAttribute(attr);
      attr = new SchemaLocalAttributeImpl();
      attr.init(new QName("", "href"), BuiltinSchemaTypeSystem.ST_ANY_URI.getRef(), 2, (String)null, (XmlObject)null, (XmlValueRef)null, false, (SOAPArrayType)null, (SchemaAnnotation)null, (Object)null);
      attrModel.addAttribute(attr);
      attr = new SchemaLocalAttributeImpl();
      attr.init(new QName("http://schemas.xmlsoap.org/soap/encoding/", "arrayType"), BuiltinSchemaTypeSystem.ST_STRING.getRef(), 2, (String)null, (XmlObject)null, (XmlValueRef)null, false, (SOAPArrayType)null, (SchemaAnnotation)null, (Object)null);
      attrModel.addAttribute(attr);
      attr = new SchemaLocalAttributeImpl();
      attr.init(new QName("http://schemas.xmlsoap.org/soap/encoding/", "offset"), BuiltinSchemaTypeSystem.ST_STRING.getRef(), 2, (String)null, (XmlObject)null, (XmlValueRef)null, false, (SOAPArrayType)null, (SchemaAnnotation)null, (Object)null);
      attrModel.addAttribute(attr);
      this.soapArray.setContentModel(contentModel, attrModel, Collections.EMPTY_MAP, Collections.EMPTY_MAP, false);
      this.arrayType = new SchemaGlobalAttributeImpl(this._container);
      this._container.addGlobalAttribute(this.arrayType.getRef());
      this.arrayType.init(new QName("http://schemas.xmlsoap.org/soap/encoding/", "arrayType"), BuiltinSchemaTypeSystem.ST_STRING.getRef(), 2, (String)null, (XmlObject)null, (XmlValueRef)null, false, (SOAPArrayType)null, (SchemaAnnotation)null, (Object)null);
      this._handlesToObjects.put(this.soapArrayHandle, this.soapArray);
      this._handlesToObjects.put("arrayType".toLowerCase() + "attribute", this.arrayType);
      this._container.setImmutable();
   }

   public String getName() {
      return "schema.typesystem.soapenc.builtin";
   }

   public SchemaType findType(QName qName) {
      return "http://schemas.xmlsoap.org/soap/encoding/".equals(qName.getNamespaceURI()) && "Array".equals(qName.getLocalPart()) ? this.soapArray : null;
   }

   public SchemaType findDocumentType(QName qName) {
      return null;
   }

   public SchemaType findAttributeType(QName qName) {
      return null;
   }

   public SchemaGlobalElement findElement(QName qName) {
      return null;
   }

   public SchemaGlobalAttribute findAttribute(QName qName) {
      return "http://schemas.xmlsoap.org/soap/encoding/".equals(qName.getNamespaceURI()) && "arrayType".equals(qName.getLocalPart()) ? this.arrayType : null;
   }

   public SchemaModelGroup findModelGroup(QName qName) {
      return null;
   }

   public SchemaAttributeGroup findAttributeGroup(QName qName) {
      return null;
   }

   public boolean isNamespaceDefined(String string) {
      return "http://schemas.xmlsoap.org/soap/encoding/".equals(string);
   }

   public SchemaType.Ref findTypeRef(QName qName) {
      SchemaType type = this.findType(qName);
      return type == null ? null : type.getRef();
   }

   public SchemaType.Ref findDocumentTypeRef(QName qName) {
      return null;
   }

   public SchemaType.Ref findAttributeTypeRef(QName qName) {
      return null;
   }

   public SchemaGlobalElement.Ref findElementRef(QName qName) {
      return null;
   }

   public SchemaGlobalAttribute.Ref findAttributeRef(QName qName) {
      SchemaGlobalAttribute attr = this.findAttribute(qName);
      return attr == null ? null : attr.getRef();
   }

   public SchemaModelGroup.Ref findModelGroupRef(QName qName) {
      return null;
   }

   public SchemaAttributeGroup.Ref findAttributeGroupRef(QName qName) {
      return null;
   }

   public SchemaIdentityConstraint.Ref findIdentityConstraintRef(QName qName) {
      return null;
   }

   public SchemaType typeForClassname(String string) {
      return null;
   }

   public InputStream getSourceAsStream(String string) {
      return null;
   }

   public ClassLoader getClassLoader() {
      return SoapEncSchemaTypeSystem.class.getClassLoader();
   }

   public void resolve() {
   }

   public SchemaType[] globalTypes() {
      return new SchemaType[]{this.soapArray};
   }

   public SchemaType[] documentTypes() {
      return EMPTY_SCHEMATYPE_ARRAY;
   }

   public SchemaType[] attributeTypes() {
      return EMPTY_SCHEMATYPE_ARRAY;
   }

   public SchemaGlobalElement[] globalElements() {
      return EMPTY_SCHEMAELEMENT_ARRAY;
   }

   public SchemaGlobalAttribute[] globalAttributes() {
      return new SchemaGlobalAttribute[]{this.arrayType};
   }

   public SchemaModelGroup[] modelGroups() {
      return EMPTY_SCHEMAMODELGROUP_ARRAY;
   }

   public SchemaAttributeGroup[] attributeGroups() {
      return EMPTY_SCHEMAATTRIBUTEGROUP_ARRAY;
   }

   public SchemaAnnotation[] annotations() {
      return EMPTY_SCHEMAANNOTATION_ARRAY;
   }

   public String handleForType(SchemaType type) {
      return this.soapArray.equals(type) ? this.soapArrayHandle : null;
   }

   public SchemaComponent resolveHandle(String string) {
      return (SchemaComponent)this._handlesToObjects.get(string);
   }

   public SchemaType typeForHandle(String string) {
      return (SchemaType)this._handlesToObjects.get(string);
   }

   public void saveToDirectory(File file) {
      throw new UnsupportedOperationException("The builtin soap encoding schema type system cannot be saved.");
   }

   public void save(Filer filer) {
      throw new UnsupportedOperationException("The builtin soap encoding schema type system cannot be saved.");
   }
}
