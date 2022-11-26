package com.bea.staxb.buildtime;

import com.bea.staxb.buildtime.internal.annobeans.ClassBindingInfoBean;
import com.bea.staxb.buildtime.internal.annobeans.FieldBindingInfoBean;
import com.bea.staxb.buildtime.internal.annobeans.MethodBindingInfoBean;
import com.bea.staxb.buildtime.internal.annobeans.TargetFacetBean;
import com.bea.staxb.buildtime.internal.annobeans.TargetSchemaPropertyBean;
import com.bea.staxb.buildtime.internal.annobeans.TargetSchemaTypeBean;
import com.bea.staxb.buildtime.internal.annobeans.TargetTopLevelElementBean;
import com.bea.staxb.buildtime.internal.bts.BindingFile;
import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.bts.BindingType;
import com.bea.staxb.buildtime.internal.bts.BindingTypeName;
import com.bea.staxb.buildtime.internal.bts.BuiltinBindingLoader;
import com.bea.staxb.buildtime.internal.bts.ByNameBean;
import com.bea.staxb.buildtime.internal.bts.CompositeBindingLoader;
import com.bea.staxb.buildtime.internal.bts.JavaTypeName;
import com.bea.staxb.buildtime.internal.bts.JaxrpcEnumType;
import com.bea.staxb.buildtime.internal.bts.QNameProperty;
import com.bea.staxb.buildtime.internal.bts.SimpleDocumentBinding;
import com.bea.staxb.buildtime.internal.bts.SoapArrayType;
import com.bea.staxb.buildtime.internal.bts.WrappedArrayType;
import com.bea.staxb.buildtime.internal.bts.XmlTypeName;
import com.bea.staxb.buildtime.internal.facade.DefaultTypegenFacade;
import com.bea.staxb.buildtime.internal.facade.ExceptionTypegenFacade;
import com.bea.staxb.buildtime.internal.facade.Java2SchemaContext;
import com.bea.staxb.buildtime.internal.facade.PropgenFacade;
import com.bea.staxb.buildtime.internal.facade.TypegenFacade;
import com.bea.staxb.buildtime.internal.logger.BindingLogger;
import com.bea.staxb.buildtime.internal.tylar.ExplodedTylarImpl;
import com.bea.staxb.buildtime.internal.tylar.TylarWriter;
import com.bea.staxb.runtime.ObjectFactory;
import com.bea.util.annogen.view.JamAnnoViewer;
import com.bea.util.annogen.view.JamAnnoViewer.Factory;
import com.bea.util.jam.JAnnotatedElement;
import com.bea.util.jam.JClass;
import com.bea.util.jam.JComment;
import com.bea.util.jam.JConstructor;
import com.bea.util.jam.JElement;
import com.bea.util.jam.JField;
import com.bea.util.jam.JMethod;
import com.bea.util.jam.JPackage;
import com.bea.util.jam.JParameter;
import com.bea.util.jam.JProperty;
import com.bea.util.jam.JamUtils;
import com.bea.xbean.common.XMLChar;
import com.bea.xbean.util.FilerImpl;
import com.bea.xbean.xb.xmlconfig.ConfigDocument;
import com.bea.xbean.xb.xmlconfig.Nsconfig;
import com.bea.xbean.xb.xmlconfig.Qnameconfig;
import com.bea.xbean.xb.xsdschema.ComplexContentDocument;
import com.bea.xbean.xb.xsdschema.ExtensionType;
import com.bea.xbean.xb.xsdschema.FormChoice;
import com.bea.xbean.xb.xsdschema.Group;
import com.bea.xbean.xb.xsdschema.ImportDocument;
import com.bea.xbean.xb.xsdschema.LocalElement;
import com.bea.xbean.xb.xsdschema.RestrictionDocument;
import com.bea.xbean.xb.xsdschema.SchemaDocument;
import com.bea.xbean.xb.xsdschema.TopLevelComplexType;
import com.bea.xbean.xb.xsdschema.TopLevelElement;
import com.bea.xbean.xb.xsdschema.TopLevelSimpleType;
import com.bea.xml.SchemaTypeSystem;
import com.bea.xml.XmlAnySimpleType;
import com.bea.xml.XmlException;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import org.xml.sax.EntityResolver;

public class Java2Schema extends BindingCompiler {
   public static final String XS = "http://www.w3.org/2001/XMLSchema";
   public static final QName XS_ANYTYPE = new QName("http://www.w3.org/2001/XMLSchema", "anyType");
   public static final QName XS_BASE_64_BINARY = new QName("http://www.w3.org/2001/XMLSchema", "base64Binary");
   private static final char SAFE_CHAR = '_';
   private static final boolean ENABLE_CR197956_FIX = false;
   private static final boolean ENABLE_CR196163_FIX = true;
   private static final boolean DEV_MODE_SHOW_ALL_XMLBEAN_COMPILE_ERRORS = false;
   private static final String[] IGNORABLE_PROD_XMLBEAN_COMPILE_ERRORS = new String[]{"Invalid SOAP Array reference: xs", "Invalid QName value: {0}", "error: src-resolve: type"};
   private static final String JAVA_URI_SCHEME = "java:";
   private static final String JAVA_NAMESPACE_URI = "language_builtins";
   private static final String SCHEMA_NAMESPACE_URI = "http://www.w3.org/2001/XMLSchema";
   private JamAnnoViewer mAnnoViewer = null;
   protected BindingFile mBindingFile;
   private BindingLoader mLoader;
   protected Map mTns2Schemadoc = new HashMap();
   private Map mTns2filename = null;
   private Map mTns2prefix = null;
   protected Map mTns2importNs = null;
   protected Map mTns2location = new HashMap();
   protected Set mClassesToBind = new TreeSet();
   private Map mArrayInfo2SetOfLitArrayElemQNames = null;
   private Map mArrayInfo2SetOfPrimitiveByteArrayElemQNames = null;
   private Set mCheckForElements = new HashSet();
   private boolean mOrderPropertiesBySource = false;
   private boolean mGenerateDocs = true;
   protected final Set mSoapSpecificTypes = new HashSet();
   protected boolean mLiteralArrayConstituentElementNameBySchemaArrayElement = true;
   protected boolean mElementFormDefaultQualified = true;
   private boolean mAttributeFormDefaultQualified = false;
   private boolean mLocalElementDefaultRequired = false;
   private boolean mLocalElementDefaultNillable = true;
   private boolean hasMultiDimArrayPropertyOrField = false;
   private XSDConfig config = null;
   private boolean upperCasePropName = true;
   protected Map mGeneratedArrayJavaTypeNames = new HashMap();
   private Map wildcardBindings = new HashMap();
   private Java2SchemaContext mFacadeContext = new Java2SchemaContext() {
      public BindingLogger getLogger() {
         return Java2Schema.this;
      }

      public BindingLoader getBindingLoader() {
         return Java2Schema.this.getBindingLoader();
      }

      public BindingType findOrCreateBindingTypeFor(JClass clazz) {
         return Java2Schema.this.findOrCreateBindingTypeFor(clazz);
      }

      public void checkNsForImport(String thisNs, String otherNs) {
         Java2Schema.this.checkNsForImport(thisNs, otherNs);
      }

      public boolean isElementFormDefaultQualified() {
         return Java2Schema.this.isElementFormDefaultQualified();
      }
   };
   private boolean print = false;
   private String mTypeSystemName = System.getProperty("SchemaTypeSystemName");
   private String mVersion = null;
   private static final String COLLECTION_ELEMENT_NAME = "item";
   private static BindingLoader builtinBindingLoader = BuiltinBindingLoader.getInstance();
   private static Comparator SIMPLENAME_COMP = new Comparator() {
      public int compare(Object o1, Object o2) {
         if (o1 instanceof JElement && o2 instanceof JElement) {
            return ((JElement)o1).getSimpleName().compareTo(((JElement)o2).getSimpleName());
         } else {
            throw new IllegalArgumentException();
         }
      }
   };

   public Java2Schema() {
   }

   public Java2Schema(JClass[] classesToBind) {
      if (classesToBind == null) {
         throw new IllegalArgumentException("null classes");
      } else {
         for(int i = 0; i < classesToBind.length; ++i) {
            this.addClassToBind(classesToBind[i]);
         }

      }
   }

   protected boolean isBoundToAnyType(String className) {
      WildcardParticle particle = (WildcardParticle)this.wildcardBindings.get(className);
      if (particle == null && className.endsWith("[]")) {
         String arrayComponentName = className.substring(0, className.length() - 2);
         particle = (WildcardParticle)this.wildcardBindings.get(arrayComponentName);
      }

      if (particle == null) {
         particle = WildcardParticle.ANYTYPE;
      }

      return particle.equals(WildcardParticle.ANYTYPE);
   }

   public void addClassToBind(JClass c) {
      this.addClassToBind(c, true);
   }

   public void addClassToBind(JClass c, boolean addMultiDimArray) {
      this.assertCompilationStarted(false);
      if (!this.mClassesToBind.contains(c)) {
         this.assertResolved(c);
         if (!isValidInputType(c)) {
            this.logError("Invalid input java type '" + c.getQualifiedName() + "'");
         } else {
            this.mClassesToBind.add(c);
            if (addMultiDimArray && !c.isPrimitiveType() && builtinBindingLoader.lookupTypeFor(getJavaName(c)) == null) {
               this.addMultiDimArrayPropertyAndFieldToBind(c);
            }

         }
      }
   }

   private void addMultiDimArrayPropertyAndFieldToBind(JClass c) {
      if (this.mAnnoViewer == null) {
         this.mAnnoViewer = Factory.create();
      }

      JProperty[] props = this.getBindableProperties(c);
      JField[] fields = this.getBindableFieldsOn(c);
      if (props != null || fields != null) {
         int j;
         if (props != null) {
            for(j = 0; j < props.length; ++j) {
               if (props[j].getType().getArrayDimensions() > 1) {
                  this.hasMultiDimArrayPropertyOrField = true;
                  this.bindJavaArrayToLiteralArray((String)null, c, props[j].getType());
               } else if (this.isBindablePropertyOrField(props[j].getType())) {
                  if (props[j].getType().isArrayType()) {
                     this.addClassToBind(props[j].getType().getArrayComponentType());
                  } else {
                     this.addClassToBind(props[j].getType());
                  }
               }
            }
         }

         if (fields != null) {
            for(j = 0; j < fields.length; ++j) {
               if (fields[j].getType().getArrayDimensions() > 1) {
                  this.hasMultiDimArrayPropertyOrField = true;
                  this.bindJavaArrayToLiteralArray((String)null, c, fields[j].getType());
               } else if (this.isBindablePropertyOrField(fields[j].getType())) {
                  if (fields[j].getType().isArrayType()) {
                     this.addClassToBind(fields[j].getType().getArrayComponentType());
                  } else {
                     this.addClassToBind(fields[j].getType());
                  }
               }
            }
         }

      }
   }

   private boolean isBindablePropertyOrField(JClass prop) {
      return prop.getSourcePosition() != null && !prop.isPrimitiveType() && prop.getContainingClass() == null && isValidInputType(prop);
   }

   public void bindJavaArrayToLiteralArray(String nsOverride, JClass serviceClass, JClass arrayType) {
      this.bindJavaArrayToLiteralArray(nsOverride, serviceClass, arrayType, (QName)null);
   }

   public void bindJavaArrayToLiteralArray(String nsOverride, JClass serviceClass, JClass arrayType, QName elementName) {
      validateArrayType(arrayType);
      Set elemNameSet = this.findOrCreateLiteralElementNameSetFor(nsOverride, serviceClass, arrayType);
      if (elementName != null) {
         elemNameSet.add(elementName);
      }

      if (!this.typeIs1DPrimitiveByteArray(arrayType) && !this.typeIs2DPrimitiveByteArray(arrayType)) {
         int arrayDim = arrayType.getArrayDimensions();
         String arrayComponentTypeName = arrayType.getArrayComponentType().getQualifiedName();
         if (arrayComponentTypeName.equals(SOAPElement.class.getName()) && arrayDim == 1 && this.isBoundToAnyType(SOAPElement.class.getName())) {
            this.p(" do NOT register for binding: array component class '" + arrayComponentTypeName + "'");
         } else {
            this.p(" Register for binding: array component class '" + arrayComponentTypeName + "'");
            this.addClassToBind(arrayType.getArrayComponentType());
         }

      }
   }

   protected boolean typeIs1DPrimitiveByteArray(JClass type) {
      return this.typeIsPrimitiveByteArrayOfDimension(type, 1);
   }

   protected boolean typeIs2DPrimitiveByteArray(JClass type) {
      return this.typeIsPrimitiveByteArrayOfDimension(type, 2);
   }

   public void addWildcardBinding(String className, WildcardParticle particle) {
      this.wildcardBindings.put(className, particle);
   }

   private boolean typeIsPrimitiveByteArrayOfDimension(JClass type, int dim) {
      if (type == null) {
         return false;
      } else if (!type.isArrayType()) {
         return false;
      } else {
         JClass componentType = type.getArrayComponentType();
         if (!componentType.getQualifiedName().equals("byte")) {
            return false;
         } else {
            return type.getArrayDimensions() == dim;
         }
      }
   }

   public void setOrderPropertiesBySource(boolean b) {
      this.mOrderPropertiesBySource = b;
   }

   public void setElementFormDefaultQualified(boolean b) {
      this.mElementFormDefaultQualified = b;
   }

   public boolean isElementFormDefaultQualified() {
      return this.mElementFormDefaultQualified;
   }

   public void setAttributeFormDefaultQualified(boolean b) {
      this.mAttributeFormDefaultQualified = b;
   }

   public void setLocalElementDefaultRequired(boolean b) {
      this.mLocalElementDefaultRequired = b;
   }

   public void setLocalElementDefaultNillable(boolean b) {
      this.mLocalElementDefaultNillable = b;
   }

   public void setAnnoViewer(JamAnnoViewer jav) {
      this.mAnnoViewer = jav;
   }

   public void setGenerateDocumentation(boolean b) {
      this.mGenerateDocs = b;
   }

   public void setOutputFileFor(String tns, String schemaFile) {
      if (this.mTns2filename == null) {
         this.mTns2filename = new HashMap();
      }

      this.mTns2filename.put(tns, schemaFile);
   }

   public void setPrefixFor(String tns, String prefix) {
      if (this.mTns2prefix == null) {
         this.mTns2prefix = new HashMap();
      }

      this.mTns2prefix.put(tns, prefix);
   }

   public void setLocationFor(String tns, String location) {
      if (this.mTns2location == null) {
         this.mTns2location = new HashMap();
      }

      this.mTns2location.put(tns, location);
   }

   protected void internalBind(TylarWriter writer) {
      if (this.mAnnoViewer == null) {
         this.mAnnoViewer = Factory.create();
      }

      this.mGeneratedArrayJavaTypeNames.clear();
      this.mBindingFile = new BindingFile();
      this.mLoader = CompositeBindingLoader.forPath(new BindingLoader[]{this.mBindingFile, super.getBaseBindingLoader()});
      if (this.hasMultiDimArrayPropertyOrField) {
         this.generateLiteralArrays();
      }

      Iterator i = this.mClassesToBind.iterator();

      while(true) {
         JClass next;
         ClassBindingInfoBean baseURI;
         do {
            if (!i.hasNext()) {
               i = this.mCheckForElements.iterator();

               while(i.hasNext()) {
                  this.ensureDocumentElementsExistFor((JClass)i.next());
               }

               if (!this.hasMultiDimArrayPropertyOrField) {
                  this.generateLiteralArrays();
               }

               this.generatePrimitiveByteArrayElements();
               this.postProcessOutputs(this.mTns2Schemadoc, this.mBindingFile);
               this.addSchemaImports();
               SchemaDocument[] xsds = new SchemaDocument[this.mTns2Schemadoc.values().size()];
               this.mTns2Schemadoc.values().toArray(xsds);

               try {
                  writer.writeBindingFile(this.mBindingFile);
               } catch (IOException var7) {
                  this.logError(var7);
               }

               for(int i = 0; i < xsds.length; ++i) {
                  if (this.mVersion != null) {
                     xsds[i].getSchema().setVersion(this.mVersion);
                  }

                  String file = null;
                  if (this.mTns2filename != null) {
                     file = (String)this.mTns2filename.get(xsds[i].getSchema().getTargetNamespace());
                  }

                  if (file == null) {
                     file = "schema-" + i + ".xsd";
                  }

                  super.includeSchema(xsds[i], file, this.mTns2prefix);
               }

               SchemaTypeSystem sts = null;

               try {
                  baseURI = null;
                  sts = BindingCompilerProcessor.createSchemaTypeSystem(xsds, (FilerImpl)null, this.mTypeSystemName, (EntityResolver)null, this.getBaseSchemaTypeLoader(), baseURI);
                  if (sts == null) {
                     throw new IllegalStateException("createSchemaTypeSystem returned null");
                  }
               } catch (XmlException var6) {
                  this.logXmlBeanCompileErrors(var6);
               }

               if (sts != null) {
                  try {
                     writer.writeSchemaTypeSystem(sts);
                  } catch (IOException var5) {
                     ExplodedTylarImpl.showXsbError(var5, (URI)null, "compile", false);
                  }
               }

               return;
            }

            next = (JClass)i.next();
            baseURI = (ClassBindingInfoBean)this.mAnnoViewer.getAnnotation(ClassBindingInfoBean.class, next);
         } while(baseURI != null && baseURI.isExclude());

         this.findOrCreateBindingTypeFor(next);
      }
   }

   private void logXmlBeanCompileErrors(XmlException xe) {
      if (xe != null) {
         String message = xe.getMessage();
         if (message != null) {
            if (message.length() > 0) {
               for(int i = 0; i < IGNORABLE_PROD_XMLBEAN_COMPILE_ERRORS.length; ++i) {
                  if (message.indexOf(IGNORABLE_PROD_XMLBEAN_COMPILE_ERRORS[i]) != -1) {
                     return;
                  }
               }

               this.logWarning(" Java2Schema internalBind warning: '" + xe.getMessage() + "'");
            }
         }
      }
   }

   protected void postProcessOutputs(Map tns2schema, BindingFile bf) {
   }

   protected BindingLoader getBindingLoader() {
      return this.mLoader;
   }

   protected SchemaDocument.Schema findOrCreateSchema(String tns) {
      if (tns == null) {
         throw new IllegalArgumentException();
      } else {
         tns = tns.trim();
         SchemaDocument doc = (SchemaDocument)this.mTns2Schemadoc.get(tns);
         if (doc == null) {
            doc = com.bea.xbean.xb.xsdschema.SchemaDocument.Factory.newInstance();
            SchemaDocument.Schema xsd = doc.addNewSchema();
            xsd.setTargetNamespace(tns);
            xsd.setElementFormDefault(this.mElementFormDefaultQualified ? FormChoice.QUALIFIED : FormChoice.UNQUALIFIED);
            xsd.setAttributeFormDefault(this.mAttributeFormDefaultQualified ? FormChoice.QUALIFIED : FormChoice.UNQUALIFIED);
            this.mTns2Schemadoc.put(tns, doc);
         }

         return doc.getSchema();
      }
   }

   protected void generateWrapperElements() {
   }

   protected TypegenFacade createTypegenFacade(TopLevelComplexType xsType, ExtensionType xsExt, String xsNs, ByNameBean bt) {
      return new DefaultTypegenFacade(this.mFacadeContext, xsType, xsExt, xsNs, bt);
   }

   protected TypegenFacade createExceptionTypegenFacade(JClass exceptionClass, TopLevelComplexType xsType, ExtensionType xsExt, String xsNs, ByNameBean bt) {
      return new ExceptionTypegenFacade(exceptionClass, this.mFacadeContext, xsType, xsExt, xsNs, bt);
   }

   protected Map getTns2Schemadoc() {
      return this.mTns2Schemadoc;
   }

   protected static boolean containsElementNamed(SchemaDocument.Schema xsd, String elementName) {
      TopLevelElement[] es = xsd.getElementArray();

      for(int i = 0; i < es.length; ++i) {
         if (elementName.equals(es[i].getName())) {
            return true;
         }
      }

      return false;
   }

   protected static TopLevelComplexType findComplexTypeNamed(SchemaDocument.Schema xsd, String typeName) {
      TopLevelComplexType[] cts = xsd.getComplexTypeArray();

      for(int i = 0; i < cts.length; ++i) {
         if (typeName.equals(cts[i].getName())) {
            return cts[i];
         }
      }

      return null;
   }

   protected void checkNsForImport(String thisNs, String otherNs) {
      if (!thisNs.equals(otherNs)) {
         if (!otherNs.equals("http://www.w3.org/2001/XMLSchema")) {
            if (this.mTns2importNs == null) {
               this.mTns2importNs = new HashMap();
            }

            Set s = (Set)this.mTns2importNs.get(thisNs);
            if (s == null) {
               s = new HashSet();
               this.mTns2importNs.put(thisNs, s);
            }

            ((Set)s).add(otherNs);
         }
      }
   }

   protected void assertResolved(JClass clazz) {
      if (clazz.isUnresolvedType()) {
         this.getLogger().logError("Could not resolve class: " + clazz.getQualifiedName());
      }

   }

   protected BindingType findOrCreateBindingTypeFor(JClass clazz) {
      if (clazz == null) {
         throw new IllegalArgumentException("null clazz");
      } else {
         this.mCheckForElements.add(clazz);
         BindingTypeName btn = this.mLoader.lookupTypeFor(getJavaName(clazz));
         if (btn != null) {
            BindingType out = this.mLoader.getBindingType(btn);
            if (out != null) {
               return out;
            }
         }

         return this.createBindingTypeFor(clazz);
      }
   }

   private void ensureDocumentElementsExistFor(JClass clazz) {
      if (clazz == null) {
         throw new IllegalArgumentException("null clazz");
      } else {
         BindingTypeName btn = this.mLoader.lookupTypeFor(JavaTypeName.forJClass(clazz));
         if (btn == null) {
            this.logWarning("No bindings produced for " + clazz.getQualifiedName() + ", skipping element generation");
         } else {
            QName typeQName = btn.getXmlName().getQName();
            ClassBindingInfoBean cbi = (ClassBindingInfoBean)this.mAnnoViewer.getAnnotation(ClassBindingInfoBean.class, clazz);
            TargetSchemaTypeBean schemaType = cbi == null ? null : cbi.schemaType();
            TargetTopLevelElementBean[] tes = schemaType == null ? null : schemaType.topLevelElements();
            if (tes != null && tes.length > 0) {
               for(int i = 0; i < tes.length; ++i) {
                  String tns = tes[i].namespaceUri();
                  if (isEmpty(tns)) {
                     tns = typeQName.getNamespaceURI();
                  }

                  String localName = tes[i].localName();
                  if (isEmpty(localName)) {
                     localName = typeQName.getLocalPart();
                  }

                  SchemaDocument.Schema destSchema = this.findOrCreateSchema(tns);
                  if (!containsElementNamed(destSchema, localName)) {
                     TopLevelElement elem = destSchema.addNewElement();
                     elem.setName(localName);
                     elem.setType(typeQName);
                     String otherNs = typeQName.getNamespaceURI();
                     this.checkNsForImport(tns, otherNs);
                  }

                  BindingTypeName docBtName = BindingTypeName.forPair(getJavaName(clazz), XmlTypeName.forGlobalName('e', new QName(tns, localName)));
                  SimpleDocumentBinding sdb = new SimpleDocumentBinding(docBtName);
                  sdb.setTypeOfElement(XmlTypeName.forTypeNamed(typeQName));
                  this.mBindingFile.addBindingType(sdb, true, true);
               }
            }

         }
      }
   }

   private BindingType createBindingTypeFor(JClass clazz) {
      this.logVerbose("** creating binding type for " + clazz.getQualifiedName() + " " + clazz.isPrimitiveType());
      ClassBindingInfoBean cbi = (ClassBindingInfoBean)this.mAnnoViewer.getAnnotation(ClassBindingInfoBean.class, clazz);
      TargetSchemaTypeBean targetSchemaType = cbi == null ? null : cbi.schemaType();
      String nsUri = null;
      String localName = null;
      QName configqname = this.getQNameforJavaClass(clazz);
      if (configqname == null) {
         if (targetSchemaType != null) {
            nsUri = targetSchemaType.namespaceUri();
         }

         if (isEmpty(nsUri)) {
            nsUri = this.getConfigNsFor(clazz);
         }

         if (targetSchemaType != null) {
            localName = targetSchemaType.localName();
         }

         if (isEmpty(localName)) {
            localName = this.getDefaultLocalNameFor(clazz);
         }
      } else {
         nsUri = configqname.getNamespaceURI();
         localName = configqname.getLocalPart();
      }

      SchemaDocument.Schema schema = this.findOrCreateSchema(nsUri);
      BindingTypeName btname;
      if (isCollectionType(clazz)) {
         btname = BindingTypeName.forPair(getJavaName(clazz), XmlTypeName.forTypeNamed(new QName(nsUri, localName)));
         return this.createJavaCollectionType(btname);
      } else if (isJaxRpcEnumType(clazz)) {
         btname = BindingTypeName.forPair(getJavaName(clazz), XmlTypeName.forTypeNamed(new QName(nsUri, localName)));
         return this.createJaxrpcEnumType(btname, clazz);
      } else {
         TopLevelComplexType xsType = findComplexTypeNamed(schema, localName);
         boolean isNewComplexType = false;
         JClass superclass = clazz.getSuperclass();
         ExtensionType extType = null;
         BindingType superBindingType = null;
         if (xsType == null) {
            isNewComplexType = true;
            xsType = schema.addNewComplexType();
            xsType.setName(localName);
            if (superclass != null && !superclass.isObjectType() && (cbi == null || !cbi.isIgnoreSuperclass()) && !superclass.getQualifiedName().equals("java.lang.Exception")) {
               superBindingType = this.findOrCreateBindingTypeFor(superclass);
               ComplexContentDocument.ComplexContent ccd = xsType.addNewComplexContent();
               extType = ccd.addNewExtension();
               extType.setBase(superBindingType.getName().getXmlName().getQName());
               String superNamespace = superBindingType.getName().getXmlName().getQName().getNamespaceURI();
               this.checkNsForImport(nsUri, superNamespace);
            }
         }

         BindingTypeName btname = BindingTypeName.forPair(getJavaName(clazz), XmlTypeName.forTypeNamed(new QName(nsUri, localName)));
         ByNameBean bindType = new ByNameBean(btname);
         this.mBindingFile.addBindingType(bindType, true, true);
         if (clazz.isPrimitiveType()) {
            this.logError("Unexpected simple type", clazz);
            return bindType;
         } else {
            if (superBindingType != null && !isExceptionType(superclass)) {
               ByNameBean super_type = (ByNameBean)superBindingType;
               Iterator itr = super_type.getProperties().iterator();

               while(itr.hasNext()) {
                  QNameProperty prop = (QNameProperty)itr.next();
                  bindType.addProperty(prop);
               }

               bindType.setAnyAttributeProperty(super_type.getAnyAttributeProperty());
               bindType.setAnyElementProperty(super_type.getAnyElementProperty());
            }

            if (isNewComplexType) {
               String docs = null;
               if (this.mGenerateDocs) {
                  docs = this.getDocumentationFor(clazz);
               }

               Map props2issetters = new HashMap();
               this.getIsSetters(clazz, props2issetters);
               Map props2factories = new HashMap();
               this.getFactoryMethodsOn(clazz, props2factories);
               if (isExceptionType(clazz)) {
                  this.bindExceptionProperties(clazz, props2issetters, props2factories, xsType, extType, nsUri, bindType, docs);
               } else {
                  this.bindProperties(clazz, props2issetters, props2factories, xsType, extType, nsUri, bindType, docs);
               }
            }

            return bindType;
         }
      }
   }

   protected BindingType createJaxrpcEnumType(BindingTypeName btname, JClass clazz) {
      JaxrpcEnumType bindType = new JaxrpcEnumType(btname);
      this.mBindingFile.addBindingType(bindType, true, true);
      QName type_qname = btname.getXmlName().getQName();
      SchemaDocument.Schema schema = this.findOrCreateSchema(type_qname.getNamespaceURI());
      TopLevelSimpleType xsType = schema.addNewSimpleType();
      xsType.setName(type_qname.getLocalPart());
      RestrictionDocument.Restriction mRestriction = xsType.addNewRestriction();
      JMethod[] methods = clazz.getMethods();
      if (methods != null && methods.length > 0) {
         for(int i = 0; i < methods.length; ++i) {
            if ("getValue".equals(methods[i].getSimpleName())) {
               JClass baseType = methods[i].getReturnType();
               JavaTypeName javaTypeName = JavaTypeName.forJClass(baseType);
               BindingTypeName btn = this.getBindingLoader().lookupTypeFor(javaTypeName);
               mRestriction.setBase(btn.getXmlName().getQName());
               break;
            }
         }
      }

      JField[] fields = this.getJaxrpcEnumBindableFieldsOn(clazz);
      if (fields != null && fields.length > 0) {
         XmlAnySimpleType xmlval = com.bea.xml.XmlAnySimpleType.Factory.newInstance();

         for(int i = 0; i < fields.length; ++i) {
            Object fieldValue = fields[i].getConstantValue();
            if (fieldValue != null) {
               xmlval.setStringValue(fieldValue.toString());
               mRestriction.addNewEnumeration().setValue(xmlval);
            }
         }
      }

      return bindType;
   }

   protected BindingType createJavaCollectionType(BindingTypeName btname) {
      WrappedArrayType bindType = createCollectionType(btname);
      this.mBindingFile.addBindingType(bindType, true, true);
      QName type_qname = btname.getXmlName().getQName();
      SchemaDocument.Schema schema = this.findOrCreateSchema(type_qname.getNamespaceURI());
      TopLevelComplexType complexType = schema.addNewComplexType();
      complexType.setName(type_qname.getLocalPart());
      Group seq = complexType.addNewSequence();
      LocalElement le = seq.addNewElement();
      le.setName("item");
      le.setForm(FormChoice.UNQUALIFIED);
      le.setType(XS_ANYTYPE);
      le.setMinOccurs(BigInteger.ZERO);
      le.setMaxOccurs("unbounded");
      if (this.mLocalElementDefaultNillable) {
         le.setNillable(true);
      }

      return bindType;
   }

   public static WrappedArrayType createCollectionType(BindingTypeName btname) {
      WrappedArrayType bindType = new WrappedArrayType(btname);
      bindType.setItemNillable(true);
      BindingTypeName anyname = BindingTypeName.forPair(JavaTypeName.forString(Object.class.getName()), XmlTypeName.forTypeNamed(XS_ANYTYPE));
      bindType.setItemType(anyname);
      bindType.setItemName(new QName("item"));
      return bindType;
   }

   public static SoapArrayType createSoapEncCollectionType(BindingTypeName btname) {
      SoapArrayType bindType = new SoapArrayType(btname);
      bindType.setItemNillable(true);
      bindType.setRanks(1);
      BindingTypeName anyname = BindingTypeName.forPair(JavaTypeName.forString(Object.class.getName()), XmlTypeName.forTypeNamed(XS_ANYTYPE));
      bindType.setItemType(anyname);
      return bindType;
   }

   private void generateLiteralArrays() {
      if (this.mArrayInfo2SetOfLitArrayElemQNames != null) {
         ArrayNamespaceInfo[] arrayNamespaceInfos = this.getArrayNamespaceInfos();

         for(int i = 0; i < arrayNamespaceInfos.length; ++i) {
            this.generateLiteralArray(arrayNamespaceInfos[i], this.mGeneratedArrayJavaTypeNames);
         }

      }
   }

   protected BindingType generateLiteralArray(ArrayNamespaceInfo arrayNamespaceInfo, Map generatedArrayJavaTypeNames) {
      JClass arrayClass = arrayNamespaceInfo.getArrayClass();
      JavaTypeName javaArrayName = JavaTypeName.forJClass(arrayClass);
      int arrayDim = arrayClass.getArrayDimensions();
      JClass arrayElementClass = arrayClass.getArrayComponentType();
      JClass arrayConstituentClass = arrayElementClass;
      if (this.typeIs2DPrimitiveByteArray(arrayClass)) {
         arrayElementClass = arrayClass.forName("byte[]");
         arrayConstituentClass = arrayElementClass;
      } else if (arrayDim > 1) {
         String constituentClassName = arrayElementClass.getQualifiedName();

         for(int i = 0; i < arrayDim - 1; ++i) {
            constituentClassName = constituentClassName + "[]";
         }

         arrayConstituentClass = arrayElementClass.forName(constituentClassName);
         ArrayNamespaceInfo ani = new ArrayNamespaceInfo(arrayNamespaceInfo.getServiceClass(), arrayConstituentClass, arrayNamespaceInfo.getNamespace());
         this.generateLiteralArray(ani, generatedArrayJavaTypeNames);
      }

      boolean arrayElementIsPrimitive = arrayElementClass.isPrimitiveType();
      BindingTypeName arrayElementBinding = this.findOrCreateBindingTypeFor(arrayElementClass).getName();
      QName schemaArrayElementQName = arrayElementBinding.getXmlName().getQName();
      QName[] elementQNames = this.getElementNamesForLiteralArrayClass(arrayNamespaceInfo, schemaArrayElementQName);
      JavaTypeName javaArrayConstituentTypeName = JavaTypeName.forJClass(arrayConstituentClass);
      BindingTypeName arrayConstituentBinding = this.getBindingLoader().lookupTypeFor(javaArrayConstituentTypeName);
      if (arrayConstituentBinding == null) {
         throw new IllegalArgumentException("could not find binding type for literal array constituent class type: " + arrayConstituentClass);
      } else {
         QName arrayConstituentQName = null;
         String arrayConstituentClassName = arrayConstituentClass.getQualifiedName();
         if ((arrayConstituentClassName.equals("javax.xml.soap.SOAPElement") || arrayConstituentClassName.equals("org.apache.xmlbeans.XmlObject") || arrayConstituentClassName.equals("com.bea.xml.XmlObject")) && this.isBoundToAnyType(arrayClass.getQualifiedName())) {
            arrayConstituentQName = XS_ANYTYPE;
         } else {
            arrayConstituentQName = arrayConstituentBinding.getXmlName().getQName();
         }

         QName arrayTypeQName = this.getLiteralArrayTypeName(arrayNamespaceInfo, schemaArrayElementQName.getLocalPart());
         String arrayConstituentElementName = null;
         if (arrayDim <= 1) {
            arrayConstituentElementName = this.arrayComponentNameForJClass(arrayElementClass, schemaArrayElementQName.getLocalPart());
         } else if (this.mLiteralArrayConstituentElementNameBySchemaArrayElement) {
            arrayConstituentElementName = schemaArrayElementQName.getLocalPart();
         } else {
            arrayConstituentElementName = arrayConstituentQName.getLocalPart();
         }

         Object returnBindingType;
         if (!generatedArrayJavaTypeNames.keySet().contains(javaArrayName)) {
            generatedArrayJavaTypeNames.put(javaArrayName, arrayTypeQName.getNamespaceURI());
            SchemaDocument.Schema schema = this.findOrCreateSchema(arrayTypeQName.getNamespaceURI());
            TopLevelComplexType complexType = schema.addNewComplexType();
            complexType.setName(arrayTypeQName.getLocalPart());
            Group seq = complexType.addNewSequence();
            LocalElement le = seq.addNewElement();
            le.setName(arrayConstituentElementName);
            le.setMinOccurs(new BigInteger("0"));
            le.setMaxOccurs("unbounded");
            if (this.typeIs2DPrimitiveByteArray(arrayClass)) {
               le.setType(XS_BASE_64_BINARY);
               le.setNillable(false);
            } else {
               le.setType(arrayConstituentQName);
               if (arrayElementIsPrimitive) {
                  le.setNillable(false);
               } else if (this.mLocalElementDefaultNillable) {
                  le.setNillable(true);
               }
            }

            XmlTypeName typeXmlName = XmlTypeName.forTypeNamed(arrayTypeQName);
            BindingTypeName btn = BindingTypeName.forPair(javaArrayName, typeXmlName);
            WrappedArrayType wat = new WrappedArrayType(btn);
            wat.setItemType(arrayConstituentBinding);
            if (arrayElementIsPrimitive) {
               wat.setItemNillable(false);
            } else {
               wat.setItemNillable(true);
            }

            this.mBindingFile.addBindingType(wat, true, true);
            returnBindingType = wat;
            this.checkNsForImport(arrayTypeQName.getNamespaceURI(), arrayConstituentQName.getNamespaceURI());
         } else {
            String nsuri = (String)generatedArrayJavaTypeNames.get(javaArrayName);
            if (!nsuri.equals(arrayTypeQName.getNamespaceURI())) {
               arrayTypeQName = new QName(nsuri, arrayTypeQName.getLocalPart(), arrayTypeQName.getPrefix());
            }

            XmlTypeName typeXmlName = XmlTypeName.forTypeNamed(arrayTypeQName);
            BindingTypeName btn = BindingTypeName.forPair(javaArrayName, typeXmlName);
            returnBindingType = this.mBindingFile.getBindingType(btn);
         }

         this.createLitArraySchemaElementsAndBindings(javaArrayName, arrayTypeQName, elementQNames);
         return (BindingType)returnBindingType;
      }
   }

   private void generatePrimitiveByteArrayElements() {
      if (this.mArrayInfo2SetOfPrimitiveByteArrayElemQNames != null) {
         ArrayNamespaceInfo[] arrayNamespaceInfos = this.getPrimitiveByteArrayNamespaceInfos();
         JavaTypeName javaArrayName = JavaTypeName.forString("byte[]");
         JavaTypeName javaArrayElementTypeName = JavaTypeName.forString("byte");
         JClass arrayElementClass = arrayNamespaceInfos[0].getServiceClass().forName("byte");
         BindingTypeName arrayElementBinding = this.getBindingLoader().lookupTypeFor(javaArrayElementTypeName);
         if (arrayElementBinding == null) {
            throw new IllegalArgumentException("could not find binding type for literal array element class type: " + arrayElementClass);
         } else {
            QName schemaArrayElementQName = arrayElementBinding.getXmlName().getQName();

            for(int i = 0; i < arrayNamespaceInfos.length; ++i) {
               QName[] elementQNames = this.getElementNamesForPrimitiveByteArrayClass(arrayNamespaceInfos[i], schemaArrayElementQName);
               this.createLitArraySchemaElementsAndBindings(javaArrayName, XS_BASE_64_BINARY, elementQNames);
            }

         }
      }
   }

   protected void createLitArraySchemaElementsAndBindings(JavaTypeName javaArrayName, QName arrayTypeQName, QName[] elementQNames) {
      for(int j = 0; j < elementQNames.length; ++j) {
         XmlTypeName elementXmlName = XmlTypeName.forGlobalName('e', elementQNames[j]);
         BindingTypeName btn2 = BindingTypeName.forPair(javaArrayName, elementXmlName);
         if (this.getBindingLoader().getBindingType(btn2) == null) {
            SchemaDocument.Schema schema = this.findOrCreateSchema(elementQNames[j].getNamespaceURI());
            TopLevelElement tle = schema.addNewElement();
            tle.setName(elementQNames[j].getLocalPart());
            tle.setType(arrayTypeQName);
            this.checkNsForImport(elementQNames[j].getNamespaceURI(), arrayTypeQName.getNamespaceURI());
            SimpleDocumentBinding sdb = new SimpleDocumentBinding(btn2);
            sdb.setTypeOfElement(XmlTypeName.forTypeNamed(arrayTypeQName));
            this.mBindingFile.addBindingType(sdb, true, true);
         }
      }

   }

   private ArrayNamespaceInfo[] getArrayNamespaceInfos() {
      return this.getArrayNamespaceInfosFor(this.mArrayInfo2SetOfLitArrayElemQNames);
   }

   private ArrayNamespaceInfo[] getPrimitiveByteArrayNamespaceInfos() {
      return this.getArrayNamespaceInfosFor(this.mArrayInfo2SetOfPrimitiveByteArrayElemQNames);
   }

   private ArrayNamespaceInfo[] getArrayNamespaceInfosFor(Map arrayInfo2Set) {
      ArrayNamespaceInfo[] out = new ArrayNamespaceInfo[arrayInfo2Set.keySet().size()];
      arrayInfo2Set.keySet().toArray(out);
      return out;
   }

   private QName[] getElementNamesForLiteralArrayClass(ArrayNamespaceInfo arrayNamespaceInfo, QName schemaComponentQName) {
      return this.getElementNamesForLiteralArrayClass(arrayNamespaceInfo, schemaComponentQName, this.mArrayInfo2SetOfLitArrayElemQNames);
   }

   private QName[] getElementNamesForPrimitiveByteArrayClass(ArrayNamespaceInfo arrayNamespaceInfo, QName schemaComponentQName) {
      return this.getElementNamesForLiteralArrayClass(arrayNamespaceInfo, schemaComponentQName, this.mArrayInfo2SetOfPrimitiveByteArrayElemQNames);
   }

   private QName[] getElementNamesForLiteralArrayClass(ArrayNamespaceInfo arrayNamespaceInfo, QName schemaComponentQName, Map arrayInfo2SetOfElemQNames) {
      Set set = (Set)arrayInfo2SetOfElemQNames.get(arrayNamespaceInfo);
      if (set == null) {
         arrayInfo2SetOfElemQNames.put(arrayNamespaceInfo, set = new HashSet());
      }

      QName defaultElement = this.getLiteralArrayTypeName(arrayNamespaceInfo, schemaComponentQName.getLocalPart());
      ((Set)set).add(defaultElement);
      QName[] out = new QName[((Set)set).size()];
      ((Set)set).toArray(out);
      return out;
   }

   private void getIsSetters(JClass clazz, Map outPropname2jmethod) {
      JMethod[] methods = clazz.getDeclaredMethods();

      for(int i = 0; i < methods.length; ++i) {
         MethodBindingInfoBean mbi = (MethodBindingInfoBean)this.mAnnoViewer.getAnnotation(MethodBindingInfoBean.class, methods[i]);
         if (mbi != null) {
            String defaultCheckerFor = mbi.defaultCheckerFor();
            if (!isEmpty(defaultCheckerFor)) {
               if (!methods[i].getReturnType().getQualifiedName().equals("boolean")) {
                  this.logWarning("Method " + methods[i].getQualifiedName() + " is marked 'defaultCheckerFor' but it does not return boolean.Ignoring.");
               } else if (methods[i].getParameters().length > 0) {
                  this.logWarning("Method " + methods[i].getQualifiedName() + " is marked 'defaultCheckerFor' but takes arguments.  Ignoring.");
               } else {
                  outPropname2jmethod.put(defaultCheckerFor, methods[i]);
               }
            }
         }
      }

   }

   private void getFactoryMethodsOn(JClass clazz, Map outPropname2jmethod) {
      JMethod[] methods = clazz.getDeclaredMethods();

      for(int i = 0; i < methods.length; ++i) {
         MethodBindingInfoBean mbi = (MethodBindingInfoBean)this.mAnnoViewer.getAnnotation(MethodBindingInfoBean.class, methods[i]);
         if (mbi != null) {
            String factoryFor = mbi.factoryFor();
            if (!isEmpty(factoryFor)) {
               if (methods[i].getParameters().length > 0) {
                  this.logWarning("Method " + methods[i].getQualifiedName() + " is marked 'factoryFor' but takes arguments.  Ignoring.");
               } else {
                  outPropname2jmethod.put(factoryFor, methods[i]);
               }
            }
         }
      }

   }

   private void bindProperties(JClass clazz, Map props2issetters, Map props2factories, TopLevelComplexType xsType, ExtensionType extType, String nsUri, ByNameBean bindType, String docs) {
      TypegenFacade facade = this.createTypegenFacade(xsType, extType, nsUri, bindType);
      if (docs != null) {
         facade.setDocumentation(docs);
      }

      JProperty[] props = this.getBindableProperties(clazz);
      JField[] fields = this.getBindableFieldsOn(clazz);
      Object fieldsAndProps;
      if (props == null) {
         if (fields == null) {
            return;
         }

         fieldsAndProps = fields;
      } else if (fields == null) {
         fieldsAndProps = props;
      } else {
         fieldsAndProps = new JElement[props.length + fields.length];
         System.arraycopy(props, 0, fieldsAndProps, 0, props.length);
         System.arraycopy(fields, 0, fieldsAndProps, props.length, fields.length);
      }

      if (this.mOrderPropertiesBySource) {
         if (clazz.getSourcePosition() == null) {
            this.logError("'orderPropertiesBySource' specified but no source info available for " + clazz.getQualifiedName());
         } else {
            JamUtils.getInstance().placeInSourceOrder((JElement[])fieldsAndProps);
         }
      }

      JMethod objectFactoryMethod = this.getObjectFactoryMethod(clazz);

      for(int i = 0; i < ((Object[])fieldsAndProps).length; ++i) {
         if (((Object[])fieldsAndProps)[i] instanceof JProperty) {
            this.processProperty(clazz, (JProperty)((Object[])fieldsAndProps)[i], facade, props2issetters, props2factories, objectFactoryMethod, nsUri);
         } else {
            this.processField((JField)((Object[])fieldsAndProps)[i], facade);
         }
      }

      facade.finish();
   }

   private JProperty[] getBindableProperties(JClass clazz) {
      List propList = new LinkedList();
      propList.addAll(Arrays.asList(clazz.getDeclaredProperties()));
      List fieldList = new LinkedList();
      fieldList.addAll(Arrays.asList(clazz.getFields()));
      if (!isJaxRpcEnumType(clazz)) {
         this.removeIgnorableProperties(propList, fieldList);
      }

      LinkedList parentPropList;
      for(ClassBindingInfoBean cbi = (ClassBindingInfoBean)this.mAnnoViewer.getAnnotation(ClassBindingInfoBean.class, clazz); (cbi == null || !cbi.isIgnoreSuperclass()) && clazz.getSuperclass() != null && !clazz.getSuperclass().isObjectType(); this.removeRedeclaredProperties(propList, parentPropList)) {
         clazz = clazz.getSuperclass();
         parentPropList = new LinkedList();
         parentPropList.addAll(Arrays.asList(clazz.getDeclaredProperties()));
         List parentFieldList = new LinkedList();
         parentFieldList.addAll(Arrays.asList(clazz.getFields()));
         if (!isJaxRpcEnumType(clazz)) {
            this.removeIgnorableProperties(parentPropList, parentFieldList);
         }
      }

      if (propList.size() == 0) {
         return null;
      } else {
         JProperty[] out = new JProperty[propList.size()];
         propList.toArray(out);
         return out;
      }
   }

   private void removeIgnorableProperties(List props, List fields) {
      Iterator i = props.iterator();

      while(true) {
         while(i.hasNext()) {
            JProperty prop = (JProperty)i.next();
            MethodBindingInfoBean mbi = (MethodBindingInfoBean)this.mAnnoViewer.getAnnotation(MethodBindingInfoBean.class, prop);
            if (mbi != null && mbi.isExclude()) {
               this.logVerbose("Marked excluded, skipping", prop);
               i.remove();
            } else if (prop.getGetter() != null && prop.getSetter() != null) {
               Iterator fieldIter = fields.iterator();

               while(fieldIter.hasNext()) {
                  JField field = (JField)fieldIter.next();
                  if (this.lowercaseFirstLetter(field.getSimpleName()).equals(this.lowercaseFirstLetter(prop.getSimpleName()))) {
                     if (field.isTransient()) {
                        this.logVerbose("skipping transient field ", prop);
                        i.remove();
                     } else if (field.isPublic()) {
                        this.logWarning("bean property and public field found with the same name: " + field.getSimpleName() + ". It is against JAX-RPC 1.1 spec 5.4.1.");
                        i.remove();
                     }
                  }
               }
            } else {
               this.logVerbose("Does not have both getter and setter, skipping", prop);
               i.remove();
            }
         }

         return;
      }
   }

   private String lowercaseFirstLetter(String s) {
      if (s.length() < 1) {
         return s;
      } else {
         StringBuffer buf = new StringBuffer(s);
         char fc = buf.charAt(0);
         buf.setCharAt(0, Character.toLowerCase(fc));
         return buf.toString();
      }
   }

   private void removeRedeclaredProperties(List childProps, List parentProps) {
      Iterator parentIter = parentProps.iterator();

      while(parentIter.hasNext()) {
         JProperty parentProp = (JProperty)parentIter.next();
         Iterator childIter = childProps.iterator();

         while(childIter.hasNext()) {
            JProperty childProp = (JProperty)childIter.next();
            if (childProp.getSimpleName().equals(parentProp.getSimpleName())) {
               childIter.remove();
            }
         }
      }

   }

   private void removeRedeclaredFields(List childFields, List parentFields) {
      if (childFields != null) {
         Iterator parentIter = parentFields.iterator();

         while(parentIter.hasNext()) {
            JField parentField = (JField)parentIter.next();
            Iterator childIter = childFields.iterator();

            while(childIter.hasNext()) {
               JField childField = (JField)childIter.next();
               if (childField.getSimpleName().equals(parentField.getSimpleName())) {
                  childIter.remove();
               }
            }
         }

      }
   }

   private JProperty[] getExceptionHierarchyProperties(JClass clazz) {
      List propList = new LinkedList();
      propList.addAll(Arrays.asList(clazz.getDeclaredProperties()));
      this.removeIgnorableExceptionProperties(propList);
      ClassBindingInfoBean cbi = (ClassBindingInfoBean)this.mAnnoViewer.getAnnotation(ClassBindingInfoBean.class, clazz);

      while((cbi == null || !cbi.isIgnoreSuperclass()) && clazz.getSuperclass() != null && !clazz.getSuperclass().isObjectType()) {
         clazz = clazz.getSuperclass();
         List parentPropList = new LinkedList();
         parentPropList.addAll(Arrays.asList(clazz.getDeclaredProperties()));
         this.removeIgnorableExceptionProperties(parentPropList);
         propList.addAll(parentPropList);
      }

      if (propList.size() == 0) {
         return new JProperty[0];
      } else {
         JProperty[] out = new JProperty[propList.size()];
         propList.toArray(out);
         return out;
      }
   }

   private void removeIgnorableExceptionProperties(List props) {
      Iterator i = props.iterator();

      while(true) {
         while(i.hasNext()) {
            JProperty prop = (JProperty)i.next();
            MethodBindingInfoBean mbi = (MethodBindingInfoBean)this.mAnnoViewer.getAnnotation(MethodBindingInfoBean.class, prop);
            if (mbi != null && mbi.isExclude()) {
               this.logVerbose("Marked excluded, skipping", prop);
               i.remove();
            } else if (prop.getGetter() == null) {
               this.logVerbose("Does not have a getter, skipping", prop);
               i.remove();
            }
         }

         return;
      }
   }

   private JField[] getBindableFieldsOn(JClass clazz) {
      List list = null;
      JField[] field = clazz.getFields();
      if (field != null && field.length != 0) {
         for(int i = 0; i < field.length; ++i) {
            if (field[i].isPublic() && !field[i].isFinal() && !field[i].isStatic() && !field[i].isTransient()) {
               FieldBindingInfoBean fbi = (FieldBindingInfoBean)this.mAnnoViewer.getAnnotation(FieldBindingInfoBean.class, field[i]);
               if (fbi == null || !fbi.isExclude()) {
                  if (list == null) {
                     list = new ArrayList();
                  }

                  list.add(field[i]);
               }
            }
         }

         ClassBindingInfoBean cbi = (ClassBindingInfoBean)this.mAnnoViewer.getAnnotation(ClassBindingInfoBean.class, clazz);

         while((cbi == null || !cbi.isIgnoreSuperclass()) && clazz.getSuperclass() != null && !clazz.getSuperclass().isObjectType()) {
            clazz = clazz.getSuperclass();
            List parentFieldList = new LinkedList();
            parentFieldList.addAll(Arrays.asList(clazz.getFields()));
            this.removeRedeclaredFields(list, parentFieldList);
         }

         if (list == null) {
            return null;
         } else {
            JField[] out = new JField[list.size()];
            list.toArray(out);
            return out;
         }
      } else {
         return null;
      }
   }

   private JField[] getJaxrpcEnumBindableFieldsOn(JClass clazz) {
      List list = null;
      JField[] fields = clazz.getDeclaredFields();
      if (fields != null && fields.length != 0) {
         for(int i = 0; i < fields.length; ++i) {
            JField f = fields[i];
            if (!f.getType().equals(clazz) && fields[i].isPublic() && fields[i].isFinal() && fields[i].isStatic()) {
               FieldBindingInfoBean fbi = (FieldBindingInfoBean)this.mAnnoViewer.getAnnotation(FieldBindingInfoBean.class, fields[i]);
               if (fbi == null || !fbi.isExclude()) {
                  if (list == null) {
                     list = new ArrayList();
                  }

                  list.add(fields[i]);
               }
            }
         }

         if (list == null) {
            return null;
         } else {
            JField[] out = new JField[list.size()];
            list.toArray(out);
            return out;
         }
      } else {
         return null;
      }
   }

   private static boolean isJaxRpcEnumType(JClass clazz) {
      JConstructor[] ctors = clazz.getConstructors();
      if (ctors != null && ctors.length > 0) {
         for(int i = 0; i < ctors.length; ++i) {
            if (ctors[i].isPublic()) {
               return false;
            }
         }
      }

      JField[] fields = clazz.getDeclaredFields();
      if (fields != null && fields.length != 0) {
         boolean hasRequiredField = false;

         for(int i = 0; i < fields.length; ++i) {
            JField f = fields[i];
            if (f.getType().equals(clazz) && f.isPublic() && f.isFinal() && f.isStatic()) {
               hasRequiredField = true;
            }
         }

         JMethod[] methods = clazz.getMethods();
         if (methods != null && methods.length != 0) {
            boolean hasGetValueMethod = false;
            boolean hasFromStringMethod = false;

            for(int i = 0; i < methods.length; ++i) {
               if ("getValue".equals(methods[i].getSimpleName())) {
                  hasGetValueMethod = true;
               }

               if ("fromString".equals(methods[i].getSimpleName()) && clazz.equals(methods[i].getReturnType())) {
                  hasFromStringMethod = true;
               }
            }

            if (hasRequiredField && hasGetValueMethod && hasFromStringMethod) {
               return true;
            } else {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private void p(String s) {
      if (this.print) {
         System.out.println(" [J2S] " + s);
      }

   }

   private void bindExceptionProperties(JClass exceptionClass, Map props2issetters, Map props2factories, TopLevelComplexType xsType, ExtensionType extType, String nsUri, ByNameBean bindType, String docs) {
      TypegenFacade facade = this.createExceptionTypegenFacade(exceptionClass, xsType, extType, nsUri, bindType);
      if (docs != null) {
         facade.setDocumentation(docs);
      }

      JProperty[] props = this.getExceptionHierarchyProperties(exceptionClass);
      JMethod objectFactoryMethod = this.getObjectFactoryMethod(exceptionClass);
      if (this.mOrderPropertiesBySource) {
         if (exceptionClass.getSourcePosition() == null) {
            this.logError("'orderPropertiesBySource' specified but no source info available for " + exceptionClass.getQualifiedName());
         } else {
            JamUtils.getInstance().placeInSourceOrder(props);
         }
      } else {
         placeInSimpleNameOrder(props);
      }

      try {
         JConstructor ctor = this.getExceptionConstructor(exceptionClass);
         JParameter[] args = ctor.getParameters();

         for(int a = 0; a < args.length; ++a) {
            int p = getPropertyForCtorParam(args[a], props, ctor);
            this.processProperty(exceptionClass, props[p], facade, props2issetters, props2factories, objectFactoryMethod, nsUri);
            facade.peekCurrentProp().setCtorParamIndex(a);
            props[p] = null;
         }
      } catch (Exception var16) {
         this.logWarning("skipping exception constructor for " + exceptionClass.getQualifiedName() + ": " + var16.getMessage());
      }

      facade.finish();
   }

   private void processProperty(JClass parent, JProperty jProp, TypegenFacade typeFacade, Map props2issetters, Map props2factories, JMethod genericObjectFactoryMethod, String nsUri) {
      this.checkPropertyConstructors(parent, jProp);
      MethodBindingInfoBean mbi = (MethodBindingInfoBean)this.mAnnoViewer.getAnnotation(MethodBindingInfoBean.class, jProp);
      PropgenFacade propFacade = this.createPropgenFacade(jProp, jProp.getType(), typeFacade, mbi == null ? null : mbi.schemaProperty(), mbi == null ? null : mbi.asTypeNamed());
      propFacade.setGetter(jProp.getGetter());
      if (jProp.getSetter() != null) {
         propFacade.setSetter(jProp.getSetter());
      }

      String propName = jProp.getSimpleName();
      QNameProperty qp = propFacade.getBtsProperty();
      QName propSchemaName = qp.getTypeName().getXmlName().getQName();
      this.checkNsForImport(nsUri, propSchemaName.getNamespaceURI());
      JMethod issetter = (JMethod)props2issetters.get(propName);
      if (issetter != null) {
         propFacade.setIssetter(issetter);
      } else {
         QName schemaName = qp.getQName();
         if (schemaName == null) {
            throw new IllegalStateException();
         }

         issetter = (JMethod)props2issetters.get(schemaName.getLocalPart());
         if (issetter != null) {
            propFacade.setIssetter(issetter);
         }
      }

      issetter = (JMethod)props2factories.get(propName);
      if (issetter != null) {
         propFacade.setFactory(issetter);
      } else if (genericObjectFactoryMethod != null && this.isOkForGenericObjectFactory(propFacade.getType())) {
         propFacade.setFactory(genericObjectFactoryMethod);
      }

      if (this.mGenerateDocs) {
         String docs = this.getDocumentationFor(jProp);
         if (docs != null) {
            propFacade.setDocumentation(docs);
         }
      }

   }

   private void checkPropertyConstructors(JClass parentClass, JProperty prop) {
      JClass propCheckClass = prop.getType();
      if (propCheckClass.isArrayType()) {
         propCheckClass = propCheckClass.getArrayComponentType();
      }

      if (!isJaxRpcEnumType(propCheckClass)) {
         JConstructor[] paramConstrs = propCheckClass.getConstructors();
         boolean needsNoArgConstr = false;
         if (paramConstrs.length > 0 && !propCheckClass.isBuiltinType() && !propCheckClass.getQualifiedName().startsWith("java.") && !propCheckClass.getQualifiedName().startsWith("javax.")) {
            needsNoArgConstr = true;

            for(int i = 0; i < paramConstrs.length; ++i) {
               JParameter[] ctorArgs = paramConstrs[i].getParameters();
               if (ctorArgs.length == 0) {
                  needsNoArgConstr = false;
                  break;
               }
            }
         }

         if (needsNoArgConstr) {
            this.logError("Java Class '" + parentClass + "', has a property of class '" + propCheckClass + "'.  The property class '" + propCheckClass + "' is required to have an empty ('no argument') constructor  but it does not.  You must add a 'no argument' constructor to '" + propCheckClass + "'.");
         }

      }
   }

   private void processField(JField field, TypegenFacade typeFacade) {
      FieldBindingInfoBean fbi = (FieldBindingInfoBean)this.mAnnoViewer.getAnnotation(FieldBindingInfoBean.class, field);
      PropgenFacade propFacade = this.createPropgenFacade(field, field.getType(), typeFacade, fbi == null ? null : fbi.schemaProperty(), fbi == null ? null : fbi.asTypeNamed());
      propFacade.setField(field);
   }

   private PropgenFacade createPropgenFacade(JElement element, JClass elementType, TypegenFacade typeFacade, TargetSchemaPropertyBean propAnn, String asType) {
      PropgenFacade propFacade;
      if (propAnn == null) {
         if (this.isAny(elementType.getQualifiedName())) {
            propFacade = typeFacade.createAny(element);
         } else {
            propFacade = typeFacade.createNextElement(element);
         }
      } else if (propAnn.isAttribute()) {
         propFacade = typeFacade.createNextAttribute(element);
      } else {
         propFacade = typeFacade.createNextElement(element);
      }

      String propName = propAnn == null ? null : propAnn.localName();
      if (isEmpty(propName)) {
         propName = element.getSimpleName();
         if (!this.upperCasePropName) {
            propName = this.lowercaseFirstLetter(propName);
         }
      }

      propFacade.setSchemaName(propName);
      propName = null;
      JClass propType;
      if (isEmpty(asType)) {
         propType = elementType;
         propFacade.setType(elementType);
      } else {
         if (elementType.isArrayType()) {
            asType = "[L" + asType + ";";
         }

         propType = elementType.forName(asType);
         if (propType.isUnresolvedType()) {
            this.logError("Could not find class named '" + propType.getQualifiedName() + "'", element);
         } else if (!isConcreteWithNoargCtor(propType)) {
            this.logWarning(propType.getQualifiedName() + " is not a valid class for 'asType'  It must be a concrete class with a default constructor.");
            propFacade.setType(propType);
         } else {
            propFacade.setType(propType);
         }
      }

      if (propAnn != null) {
         propFacade.setRequired(propAnn.isRequired());
      } else if (propType.isArrayType()) {
         propFacade.setRequired(false);
      } else {
         propFacade.setRequired(this.mLocalElementDefaultRequired);
      }

      JClass baseType = this.getBaseType(propType);
      if (baseType.isPrimitiveType()) {
         propFacade.setNillable(false);
      } else if (propAnn == null) {
         if (this.mLocalElementDefaultNillable) {
            propFacade.setNillable(true);
         }
      } else {
         propFacade.setNillable(propAnn.isNillable());
      }

      if (propAnn != null) {
         TargetFacetBean[] facets = propAnn.facets();
         if (facets != null) {
            for(int i = 0; i < facets.length; ++i) {
               propFacade.addFacet(facets[i].typeNum(), facets[i].value());
            }
         }

         String val = propAnn.defaultValue();
         if (val != null && val.length() > 0) {
            propFacade.setDefault(val);
         }
      }

      return propFacade;
   }

   private boolean isAny(String elementType) {
      if (!WildcardUtil.WILDCARD_CLASSNAMES.contains(elementType)) {
         return false;
      } else {
         return !this.isBoundToAnyType(elementType);
      }
   }

   private JClass getBaseType(JClass propType) {
      while(propType.isArrayType()) {
         propType = propType.getArrayComponentType();
      }

      return propType;
   }

   protected Set findOrCreateLiteralElementNameSetFor(String nsOverride, JClass serviceClass, JClass arrayType) {
      if (this.typeIs1DPrimitiveByteArray(arrayType)) {
         if (this.mArrayInfo2SetOfPrimitiveByteArrayElemQNames == null) {
            this.mArrayInfo2SetOfPrimitiveByteArrayElemQNames = new HashMap();
         }

         return this.findOrCreateLiteralElementNameSetFor(nsOverride, serviceClass, arrayType, this.mArrayInfo2SetOfPrimitiveByteArrayElemQNames);
      } else {
         if (this.mArrayInfo2SetOfLitArrayElemQNames == null) {
            this.mArrayInfo2SetOfLitArrayElemQNames = new HashMap();
         }

         return this.findOrCreateLiteralElementNameSetFor(nsOverride, serviceClass, arrayType, this.mArrayInfo2SetOfLitArrayElemQNames);
      }
   }

   private Set findOrCreateLiteralElementNameSetFor(String nsOverride, JClass serviceClass, JClass arrayType, Map arrayInfo2SetOfElemQNames) {
      ArrayNamespaceInfo ani = ArrayNameHelper.getArrayNamespaceInfo(nsOverride, serviceClass, arrayType);
      Set out = (Set)arrayInfo2SetOfElemQNames.get(ani);
      if (out == null) {
         arrayInfo2SetOfElemQNames.put(ani, out = new HashSet());
      }

      return (Set)out;
   }

   private void addSchemaImports() {
      if (this.mTns2importNs != null) {
         Iterator it = this.mTns2importNs.keySet().iterator();

         while(true) {
            String thisNamespaceUri;
            Set imports;
            do {
               if (!it.hasNext()) {
                  return;
               }

               thisNamespaceUri = (String)it.next();
               imports = (Set)this.mTns2importNs.get(thisNamespaceUri);
            } while(imports == null);

            SchemaDocument.Schema thisSchema = this.findOrCreateSchema(thisNamespaceUri);
            Iterator it2 = imports.iterator();

            while(it2.hasNext()) {
               String importNs = (String)it2.next();
               ImportDocument.Import imp = thisSchema.addNewImport();
               imp.setNamespace(importNs);
               String schemaLoc = (String)this.mTns2location.get(importNs);
               if (schemaLoc != null) {
                  imp.setSchemaLocation(schemaLoc);
               }
            }
         }
      }
   }

   private String getDocumentationFor(JAnnotatedElement element) {
      JComment comment = element.getComment();
      if (comment != null) {
         String text = comment.getText();
         text = this.removeJavaComments(text);
         if (text != null) {
            text = text.trim();
            if (text.length() > 0) {
               return text;
            }
         }
      }

      return null;
   }

   public String removeJavaComments(String text) {
      Pattern p2 = Pattern.compile(".*/\\*\\*.*|.*\\*/|.*\\*", 8);
      text = p2.matcher(text).replaceAll("");
      return text;
   }

   private static void placeInSimpleNameOrder(JElement[] elements) {
      Arrays.sort(elements, SIMPLENAME_COMP);
   }

   protected static boolean isValidInputType(JClass c) {
      return !c.isVoidType() && !c.isArrayType() && !c.isEnumType();
   }

   protected static void validateArrayType(JClass javaType) {
      if (javaType == null) {
         throw new IllegalArgumentException("null javatype");
      } else if (!javaType.isArrayType()) {
         throw new IllegalArgumentException(javaType.getQualifiedName() + " is not an array type");
      }
   }

   private static boolean isEmpty(String s) {
      return s == null || s.trim().length() == 0;
   }

   private static boolean isExceptionType(JClass clazz) {
      JClass thr = clazz.forName("java.lang.Throwable");
      return thr.isAssignableFrom(clazz);
   }

   private static boolean isConcreteWithNoargCtor(JClass jc) {
      if (jc.getArrayComponentType() != null) {
         jc = jc.getArrayComponentType();
      }

      if (jc.isInterface()) {
         return false;
      } else if (jc.isAbstract()) {
         return false;
      } else {
         JConstructor[] ctors = jc.getConstructors();

         for(int i = 0; i < ctors.length; ++i) {
            if (ctors[i].getParameters().length == 0) {
               return true;
            }
         }

         return false;
      }
   }

   private JConstructor getExceptionConstructor(JClass c) {
      JConstructor[] ctors = c.getConstructors();
      JProperty[] props = this.getExceptionHierarchyProperties(c);
      int highScore = 0;
      int highScoreIndex = 0;
      int[] score = new int[ctors.length];

      int i;
      for(i = 0; i < ctors.length; ++i) {
         score[i] = this.scoreConstructor1(ctors[i], props);
         if (score[i] > highScore) {
            highScore = score[i];
            highScoreIndex = i;
         }
      }

      if (highScore > 0) {
         return ctors[highScoreIndex];
      } else {
         highScore = 0;
         highScoreIndex = 0;

         for(i = 0; i < ctors.length; ++i) {
            score[i] = this.scoreConstructor2(ctors[i], props);
            if (score[i] > highScore) {
               highScore = score[i];
               highScoreIndex = i;
            }
         }

         return ctors[highScoreIndex];
      }
   }

   private int scoreConstructor1(JConstructor constr, JProperty[] props) {
      int score = 0;
      JParameter[] params = constr.getParameters();

      for(int i = 0; i < props.length; ++i) {
         JProperty prop = props[i];
         String propName = this.lowercaseFirstLetter(prop.getSimpleName());

         for(int j = 0; j < params.length; ++j) {
            String paramName = this.lowercaseFirstLetter(params[j].getSimpleName());
            if (paramName.equals(propName)) {
               ++score;
               break;
            }
         }
      }

      return score;
   }

   private int scoreConstructor2(JConstructor constr, JProperty[] props) {
      int score = 0;
      JParameter[] params = constr.getParameters();

      for(int i = 0; i < props.length; ++i) {
         JClass propType = props[i].getType();

         for(int j = 0; j < params.length; ++j) {
            JClass paramType = params[j].getType();
            if (paramType.equals(propType)) {
               ++score;
               break;
            }
         }
      }

      return score;
   }

   private static int getPropertyForCtorParam(JParameter param, JProperty[] props, JConstructor ctor) {
      for(int p = 0; p < props.length; ++p) {
         if (props[p] != null && props[p].getSimpleName().equalsIgnoreCase(param.getSimpleName())) {
            return p;
         }
      }

      throw new IllegalArgumentException("could not find property corresponding to ctor param '" + param.getSimpleName() + "' on " + ctor.getQualifiedName());
   }

   private static JavaTypeName getJavaName(JClass jc) {
      return JavaTypeName.forString(jc.getQualifiedName());
   }

   protected String getDefaultLocalNameFor(JClass clazz) {
      String baseName = clazz.getSimpleName();
      if (this.config != null) {
         String prefix = this.config.lookUpPrefixForClass(baseName);
         if (prefix != null) {
            baseName = baseName.substring(prefix.length());
         }

         String suffix = this.config.lookUpSuffixForClass(baseName);
         if (suffix != null) {
            baseName = baseName.substring(0, baseName.lastIndexOf(suffix));
         }
      }

      return makeNcNameSafe(baseName);
   }

   protected String getDefaultNamespaceForJClass(JClass clazz) {
      return getDefaultNsFor(clazz);
   }

   private String getConfigNsFor(JClass clazz) {
      String uri = null;
      if (this.config != null) {
         JPackage pkg = clazz.getContainingPackage();
         String pkg_name = pkg == null ? "" : pkg.getQualifiedName();
         uri = this.config.lookUpUriForPackageName(pkg_name);
         if (uri == null) {
            String classname = clazz.getSimpleName();
            uri = this.config.lookUpUriForPrefix(classname);
            if (uri == null) {
               uri = this.config.lookUpUriForSuffix(classname);
            }
         }
      }

      if (uri == null) {
         uri = this.getDefaultNamespaceForJClass(clazz);
      }

      return uri;
   }

   public static String getDefaultNsFor(JClass clazz) {
      String pkg_name;
      if (clazz.isPrimitiveType()) {
         pkg_name = "language_builtins";
      } else {
         JPackage pkg = clazz.getContainingPackage();
         pkg_name = pkg == null ? "" : pkg.getQualifiedName();
      }

      return "java:" + pkg_name;
   }

   public static String makeNcNameSafe(String name) {
      if (name != null && !XMLChar.isValidNCName(name) && name.length() != 0) {
         StringWriter out = new StringWriter();
         char ch = name.charAt(0);
         if (!XMLChar.isNCNameStart(ch)) {
            out.write(95);
            if (XMLChar.isNCName(ch)) {
               out.write(ch);
            }
         } else {
            out.write(ch);
         }

         for(int i = 1; i < name.length(); ++i) {
            ch = name.charAt(i);
            if (!XMLChar.isNCName(ch)) {
               out.write(95);
            } else {
               out.write(ch);
            }
         }

         return out.toString();
      } else {
         return name;
      }
   }

   private JMethod getObjectFactoryMethod(JClass clazz) {
      JClass objectFactory = clazz.forName(ObjectFactory.class.getName());
      return objectFactory.isAssignableFrom(clazz) ? objectFactory.getDeclaredMethods()[0] : null;
   }

   private boolean isOkForGenericObjectFactory(JClass propType) {
      JClass descBean = propType.forName("weblogic.descriptor.DescriptorBean");
      if (descBean.isUnresolvedType()) {
         throw new IllegalStateException();
      } else {
         if (propType.isArrayType()) {
            propType = propType.getArrayComponentType();
         }

         return descBean.isAssignableFrom(propType);
      }
   }

   public static boolean isCollectionType(JClass clazz) {
      JClass listBase = clazz.getClassLoader().loadClass(Collection.class.getName());
      return listBase.isAssignableFrom(clazz) && clazz.getQualifiedName().startsWith("java.util");
   }

   public static boolean isCollectionType(Class clazz) {
      return Collection.class.isAssignableFrom(clazz) && clazz.getName().startsWith("java.util.");
   }

   public static boolean isCollectionType(String clazz) {
      if (clazz.startsWith("java.util.")) {
         try {
            return isCollectionType(Class.forName(clazz));
         } catch (ClassNotFoundException var2) {
         }
      }

      return false;
   }

   protected QName getLiteralArrayTypeName(ArrayNamespaceInfo info, String schemaComponentQNameLocalPart) {
      return ArrayNameHelper.getLiteralArrayTypeName(info, schemaComponentQNameLocalPart);
   }

   protected String arrayComponentNameForJClass(JClass arrayComponentClass, String schemaComponentQNameLocalPart) {
      return ArrayNameHelper.arrayComponentNameForJClass(arrayComponentClass, schemaComponentQNameLocalPart);
   }

   public void setBindingConfig(ConfigDocument.Config[] c) {
      this.config = new XSDConfig(c);
   }

   public void setUpperCasePropName(boolean upperCasePropName) {
      this.upperCasePropName = upperCasePropName;
   }

   QName getQNameforJavaClass(JClass cz) {
      if (this.config != null) {
         QName name = this.config.lookupQNameforJavaName(cz.getQualifiedName());
         return name != null ? name : this.config.lookupQNameforJavaName(cz.getSimpleName());
      } else {
         return null;
      }
   }

   public void setTypeSystemName(String typeSystemName) {
      this.mTypeSystemName = typeSystemName;
   }

   public void setVersion(String version) {
      this.mVersion = version;
   }

   class XSDConfig {
      private Map packageNamespaceMap = new HashMap();
      private Map prefixNamespaceMap = new HashMap();
      private Map suffixNamespaceMap = new HashMap();
      private Map qnameMap = new HashMap();

      XSDConfig(ConfigDocument.Config[] config) {
         for(int i = 0; i < config.length; ++i) {
            Nsconfig[] nsc = config[i].getNamespaceArray();

            for(int j = 0; j < nsc.length; ++j) {
               this.processSetting(nsc[j].getUri(), nsc[j].getPackage(), this.packageNamespaceMap);
               this.processNamespacePrefixSetting(nsc[j].getUriprefix(), nsc[j].getPackage());
               this.processSetting(nsc[j].getUri(), nsc[j].getPrefix(), this.prefixNamespaceMap);
               this.processNamespacePrefixSetting(nsc[j].getUriprefix(), nsc[j].getPrefix());
               this.processSetting(nsc[j].getUri(), nsc[j].getSuffix(), this.suffixNamespaceMap);
               this.processNamespacePrefixSetting(nsc[j].getUriprefix(), nsc[j].getSuffix());
            }

            Qnameconfig[] qnc = config[i].getQnameArray();

            for(int k = 0; k < qnc.length; ++k) {
               this.qnameMap.put(qnc[k].getJavaname(), qnc[k].getName());
            }
         }

      }

      private void processSetting(Object key, String value, Map result) {
         if (value != null) {
            if (key == null) {
               result.put(value, "");
            } else if (key instanceof String && "##any".equals(key)) {
               result.put(value, key);
            } else {
               String uri;
               if (key instanceof List) {
                  for(Iterator i = ((List)key).iterator(); i.hasNext(); result.put(value, uri)) {
                     uri = (String)i.next();
                     if ("##local".equals(uri)) {
                        uri = "";
                     }
                  }
               }
            }

         }
      }

      private void processNamespacePrefixSetting(List list, String value) {
         if (value != null) {
            if (list != null) {
               throw new RuntimeException("we cannot support uriprefix attribute in a xsdconfig file, for java to schema mappings.");
            }
         }
      }

      String lookUpUriForPackageName(String packagename) {
         return (String)this.packageNamespaceMap.get(packagename);
      }

      String lookUpUriForPrefix(String baseClassName) {
         String prefix = this.lookUpPrefixForClass(baseClassName);
         return prefix != null ? (String)this.prefixNamespaceMap.get(prefix) : null;
      }

      String lookUpPrefixForClass(String baseClassName) {
         if (baseClassName == null) {
            return null;
         } else {
            Iterator prefixit = this.prefixNamespaceMap.keySet().iterator();

            String prefix;
            do {
               if (!prefixit.hasNext()) {
                  return null;
               }

               prefix = (String)prefixit.next();
            } while(!baseClassName.startsWith(prefix));

            return prefix;
         }
      }

      String lookUpSuffixForClass(String baseClassName) {
         if (baseClassName == null) {
            return null;
         } else {
            Iterator suffixit = this.suffixNamespaceMap.keySet().iterator();

            String suffix;
            do {
               if (!suffixit.hasNext()) {
                  return null;
               }

               suffix = (String)suffixit.next();
            } while(!baseClassName.endsWith(suffix));

            return suffix;
         }
      }

      String lookUpUriForSuffix(String baseClassName) {
         String suffix = this.lookUpSuffixForClass(baseClassName);
         return suffix != null ? (String)this.prefixNamespaceMap.get(suffix) : null;
      }

      QName lookupQNameforJavaName(String javaname) {
         return (QName)this.qnameMap.get(javaname);
      }
   }
}
