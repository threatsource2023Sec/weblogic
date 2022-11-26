package com.bea.staxb.buildtime;

import com.bea.staxb.buildtime.internal.EnumerationPrintHelper;
import com.bea.staxb.buildtime.internal.bts.BindingFile;
import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.bts.BindingType;
import com.bea.staxb.buildtime.internal.bts.BindingTypeName;
import com.bea.staxb.buildtime.internal.bts.ByNameBean;
import com.bea.staxb.buildtime.internal.bts.GenericXmlProperty;
import com.bea.staxb.buildtime.internal.bts.JavaTypeName;
import com.bea.staxb.buildtime.internal.bts.JaxrpcEnumType;
import com.bea.staxb.buildtime.internal.bts.ListArrayType;
import com.bea.staxb.buildtime.internal.bts.MethodName;
import com.bea.staxb.buildtime.internal.bts.QNameProperty;
import com.bea.staxb.buildtime.internal.bts.SimpleBindingType;
import com.bea.staxb.buildtime.internal.bts.SimpleContentBean;
import com.bea.staxb.buildtime.internal.bts.SimpleContentProperty;
import com.bea.staxb.buildtime.internal.bts.SimpleDocumentBinding;
import com.bea.staxb.buildtime.internal.bts.SoapArrayType;
import com.bea.staxb.buildtime.internal.bts.WrappedArrayType;
import com.bea.staxb.buildtime.internal.bts.XmlTypeName;
import com.bea.staxb.buildtime.internal.joust.CompilingJavaOutputStream;
import com.bea.staxb.buildtime.internal.joust.Expression;
import com.bea.staxb.buildtime.internal.joust.JavaOutputStream;
import com.bea.staxb.buildtime.internal.joust.Variable;
import com.bea.staxb.buildtime.internal.tylar.DebugTylarWriter;
import com.bea.staxb.buildtime.internal.tylar.ExplodedTylarImpl;
import com.bea.staxb.buildtime.internal.tylar.TylarWriter;
import com.bea.util.jam.JElement;
import com.bea.xbean.common.NameUtil;
import com.bea.xbean.config.BindingConfigImpl;
import com.bea.xbean.schema.SoapEncSchemaTypeSystem;
import com.bea.xbean.xb.xmlconfig.ConfigDocument;
import com.bea.xbean.xb.xsdschema.SchemaDocument.Factory;
import com.bea.xml.BindingConfig;
import com.bea.xml.SchemaField;
import com.bea.xml.SchemaGlobalElement;
import com.bea.xml.SchemaLocalAttribute;
import com.bea.xml.SchemaParticle;
import com.bea.xml.SchemaProperty;
import com.bea.xml.SchemaType;
import com.bea.xml.SchemaTypeLoader;
import com.bea.xml.SchemaTypeSystem;
import com.bea.xml.XmlAnySimpleType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlQName;
import com.bea.xml.soap.SOAPArrayType;
import com.bea.xml.soap.SchemaWSDLArrayType;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;

public class Schema2Java extends BindingCompiler {
   private Set usedNames = new HashSet();
   private Set duplicateNames = new HashSet();
   private SchemaTypeSystem sts = null;
   private List mParamTypes;
   private List mParamElements;
   private List mFaultTypes;
   private List mFaultElements;
   private List mWrapperOperations;
   private boolean mIncludeGlobalTypes = false;
   private boolean mSortSchemaTypes = false;
   private boolean mWriteJavaFiles = true;
   private Map nameListFromWrapperElement = new HashMap();
   private Map scratchFromXmlName = new LinkedHashMap();
   private Map scratchFromSchemaType = new HashMap();
   private Map scratchFromJavaNameString = new HashMap();
   private BindingLoader mLoader;
   private int structureCount;
   private BindingFile bindingFile = new BindingFile();
   private JavaOutputStream mJoust = null;
   private CompilingJavaOutputStream mDefaultJoust = null;
   private File mCodegenDir = null;
   private BindingConfig config = null;
   private boolean ALWAYS_GENERATE_SETTER = Boolean.getBoolean("weblogic.wsee.alwaysGenSetter");
   private static String[] PRIMITIVE_TYPES = new String[]{"int", "boolean", "float", "long", "double", "short", "byte", "char"};
   private static String[] BOXED_TYPES = new String[]{"java.lang.Integer", "java.lang.Boolean", "java.lang.Float", "java.lang.Long", "java.lang.Double", "java.lang.Short", "java.lang.Byte", "java.lang.Character"};
   private static final String SOAPELEMENT_CLASSNAME = SOAPElement.class.getName();
   private static String[] IMPLEMENTED_INTERFACES = new String[]{"java.io.Serializable"};
   private static String WILDCARD_ELEMENT_MAPPING;
   private static String WILDCARD_ATTRIBUTE_MAPPING;
   private static final String xsns = "http://www.w3.org/2001/XMLSchema";
   private static final String SOAPENC_NS = "http://schemas.xmlsoap.org/soap/encoding/";
   private static final QName NMTOKENS;
   private static Set BLACKPACKAGE;
   private static final boolean ISPostJdk8;
   private static final QName arrayType;

   public Schema2Java(SchemaTypeSystem s) {
      this.setSchemaTypeSystem(s);
   }

   Schema2Java() {
   }

   void setSchemaTypeSystem(SchemaTypeSystem s) {
      if (s == null) {
         throw new IllegalArgumentException("null sts");
      } else {
         this.sts = s;
      }
   }

   public void setCompileJava(boolean b) {
      this.assertCompilationStarted(false);
      this.getDefaultJoust().setDoCompile(b);
   }

   public void setJavac(String javacPath) {
      this.assertCompilationStarted(false);
      this.getDefaultJoust().setJavac(javacPath);
   }

   public void setJavacSourceAndTarget(String version) {
      this.assertCompilationStarted(false);
      this.getDefaultJoust().setJavacSourceAndTarget(version);
   }

   public void setJavacClasspath(File[] classpath) {
      this.assertCompilationStarted(false);
      this.getDefaultJoust().setJavacClasspath(classpath);
   }

   public void setKeepGeneratedJava(boolean b) {
      this.assertCompilationStarted(false);
      this.getDefaultJoust().setKeepGenerated(b);
   }

   public void setCodegenDir(File dir) {
      this.mCodegenDir = dir;
   }

   public void setParamTypes(List paramTypes) {
      this.assertCompilationStarted(false);
      this.mParamTypes = paramTypes;
   }

   public void setParamElements(List paramElements) {
      this.assertCompilationStarted(false);
      this.mParamElements = paramElements;
   }

   public void setFaultTypes(List faultTypes) {
      this.assertCompilationStarted(false);
      this.mFaultTypes = faultTypes;
   }

   public void setFaultElements(List faultElements) {
      this.assertCompilationStarted(false);
      this.mFaultElements = faultElements;
   }

   public void setWrapperOperations(List wrapperOperations) {
      this.assertCompilationStarted(false);
      this.mWrapperOperations = wrapperOperations;
   }

   public Map getNameListFromWrapperElement() {
      return this.nameListFromWrapperElement;
   }

   public void setWriteJavaFiles(boolean b) {
      this.mWriteJavaFiles = b;
   }

   public void setIncludeGlobalTypes(boolean include) {
      this.assertCompilationStarted(false);
      this.mIncludeGlobalTypes = include;
   }

   public void setSortSchemaTypes(boolean sort) {
      this.assertCompilationStarted(false);
      this.mSortSchemaTypes = sort;
   }

   protected ExplodedTylarImpl createDefaultExplodedTylarImpl(File tylarDestDir) throws IOException {
      CompilingJavaOutputStream joust = this.getDefaultJoust();
      joust.setSourceDir(this.getCodegenDir(tylarDestDir));
      joust.setCompilationDir(tylarDestDir);
      this.mJoust = joust;
      return ExplodedTylarImpl.create(tylarDestDir, this.mJoust);
   }

   protected void internalBind(TylarWriter writer) {
      if (this.sts == null) {
         throw new IllegalStateException("SchemaTypeSystem not set");
      } else if ((this.mJoust = writer.getJavaOutputStream()) == null) {
         throw new IllegalArgumentException("The specified TylarWriter does not provide a JavaOutputStream, and so it cannot be used with schema2java.");
      } else {
         this.bind();

         try {
            writer.writeBindingFile(this.bindingFile);
            writer.writeSchemaTypeSystem(this.sts);
         } catch (IOException var3) {
            if (!this.logError(var3)) {
               return;
            }
         }

         if (this.mWriteJavaFiles) {
            this.writeJavaFiles();
         }

      }
   }

   private File getCodegenDir(File tylarDestDir) {
      return this.mCodegenDir != null ? this.mCodegenDir : new File(tylarDestDir, "META-INF/src");
   }

   private CompilingJavaOutputStream getDefaultJoust() {
      if (this.mDefaultJoust == null) {
         this.mDefaultJoust = new CompilingJavaOutputStream();
         this.mDefaultJoust.setLogger(this);
      }

      return this.mDefaultJoust;
   }

   private void bind() {
      if (this.mJoust == null) {
         throw new IllegalStateException("joust not set");
      } else {
         this.mLoader = super.getBaseBindingLoader();
         this.createScratchArea();
         this.computeDuplicateNameList();
         Iterator i = this.scratchIterator();

         Scratch scratch;
         while(i.hasNext()) {
            scratch = (Scratch)i.next();
            this.resolveJavaName(scratch);
            this.createBindingType(scratch);
         }

         i = this.scratchIterator();

         while(i.hasNext()) {
            scratch = (Scratch)i.next();
            this.resolveJavaStructure(scratch);
            this.resolveJavaEnumeration(scratch);
            this.resolveJavaArray(scratch);
            this.resolveJavaException(scratch);
         }

         this.processWrapperElements();
         i = this.scratchIterator();

         while(i.hasNext()) {
            scratch = (Scratch)i.next();
            this.addExtraBTN(scratch);
         }

      }
   }

   private void addExtraBTN(Scratch scratch) {
      SchemaType sType = scratch.getSchemaType();
      if (scratch.getCategory() == 2 && sType.isAnonymousType() && sType.getContentType() == 3 && sType.getDerivationType() == 1 && !sType.getBaseType().isBuiltinType() && !scratch.isMultiplicityChanged()) {
         Scratch baseScratch = this.scratchForSchemaType(sType.getBaseType());
         if (baseScratch != null) {
            ByNameBean original = (ByNameBean)scratch.getBindingType();
            BindingTypeName btn = BindingTypeName.forPair(baseScratch.getJavaName(), scratch.getXmlName());
            ByNameBean newOne = new ByNameBean(btn);
            newOne.setAnyAttributeProperty(original.getAnyAttributeProperty());
            newOne.setAnyElementProperty(original.getAnyElementProperty());
            Iterator i = original.getProperties().iterator();

            while(i.hasNext()) {
               newOne.addProperty((QNameProperty)i.next());
            }

            this.bindingFile.addBindingType(newOne, true, true);
         }
      }

   }

   private boolean isSoapEncPrimitiveWrapper(SchemaType st) {
      QName qn = st.getName();
      if (qn == null) {
         return false;
      } else {
         String nsURI = qn.getNamespaceURI();
         if (nsURI == null) {
            return false;
         } else if (!nsURI.equals("http://schemas.xmlsoap.org/soap/encoding/")) {
            return false;
         } else {
            String l = qn.getLocalPart();
            return l.equals("int") || l.equals("float") || l.equals("long") || l.equals("double") || l.equals("boolean") || l.equals("byte") || l.equals("short");
         }
      }
   }

   private String javaClassNameForSoapEncPrimitiveWrapper(SchemaType st) {
      String javaName = "COULD_NOT_GET_JAVA_NAME_FOR_" + st.toString();
      if (!this.isSoapEncPrimitiveWrapper(st)) {
         return javaName;
      } else {
         QName qn = st.getName();
         javaName = "COULD_NOT_GET_JAVA_NAME_FOR_" + qn.toString();
         if (!qn.getNamespaceURI().equals("http://schemas.xmlsoap.org/soap/encoding/")) {
            return javaName;
         } else {
            String l = qn.getLocalPart();
            if (l.equals("int")) {
               return "java.lang.Integer";
            } else if (l.equals("float")) {
               return "java.lang.Float";
            } else if (l.equals("long")) {
               return "java.lang.Long";
            } else if (l.equals("double")) {
               return "java.lang.Double";
            } else if (l.equals("boolean")) {
               return "java.lang.Boolean";
            } else if (l.equals("byte")) {
               return "java.lang.Byte";
            } else {
               return l.equals("short") ? "java.lang.Short" : javaName;
            }
         }
      }
   }

   private void createScratchArea() {
      this.logVerbose("creating scratch area...");
      Iterator it = this.allTypeIterator(this.mIncludeGlobalTypes);

      XmlTypeName altXmlName;
      while(it.hasNext()) {
         SchemaType sType = (SchemaType)it.next();
         this.logVerbose("processing schema type " + sType);
         XmlTypeName xmlName = XmlTypeName.forSchemaType(sType);
         Scratch scratch;
         if (sType.isSimpleType()) {
            if (isEnumeration(sType)) {
               scratch = new Scratch(sType, xmlName, 3);
            } else if (isList(sType)) {
               if (isEnumeration(getListItemType(sType))) {
                  scratch = new Scratch(sType, xmlName, 12);
               } else if (isUnion(getListItemType(sType))) {
                  scratch = new Scratch(sType, xmlName, 13);
               } else {
                  scratch = new Scratch(sType, xmlName, 4);
               }
            } else {
               scratch = new Scratch(sType, xmlName, 1);
            }
         } else if (sType.isDocumentType()) {
            QName elName = sType.getDocumentElementName();
            scratch = new Scratch(sType, XmlTypeName.forGlobalName('e', elName), 8);
            SchemaType var14 = sType.getProperties()[0].getType();
         } else if (sType.isAttributeType()) {
            scratch = new Scratch(sType, XmlTypeName.forGlobalName('a', sType.getAttributeTypeAttributeName()), 9);
         } else if (isSoapArray(sType)) {
            scratch = new Scratch(sType, xmlName, 7);
            altXmlName = soapArrayTypeName(sType);
            scratch.setAsIf(altXmlName);
            SOAPArrayType soapType = getWsdlArrayType(sType);
            if (soapType != null) {
               scratch.setSoapRank(soapType.getDimensions().length);
            } else {
               scratch.setSoapRank(1);
            }

            int[] ranks = soapType == null ? new int[0] : soapType.getRanks();
            int j = ranks.length;

            for(altXmlName = altXmlName.getOuterComponent(); altXmlName.getComponentType() == 121; altXmlName = altXmlName.getOuterComponent()) {
               Scratch altScratch = new Scratch(sType, altXmlName, 7);
               --j;
               altScratch.setSoapRank(ranks[j]);
               this.scratchFromXmlName.put(altXmlName, altScratch);
            }
         } else if (isLiteralArray(sType) && !this.mJaxRPCWrappedArrayStyle) {
            scratch = new Scratch(sType, xmlName, 5);
            this.logWarning("Generating array which is non-compliant with JaxRPC 1.1 for XML name: " + xmlName);
         } else if (this.isSoapEncPrimitiveWrapper(sType)) {
            scratch = new Scratch(sType, xmlName, 11);
         } else {
            scratch = new Scratch(sType, xmlName, 2);
         }

         this.scratchFromXmlName.put(xmlName, scratch);
         this.scratchFromSchemaType.put(sType, scratch);
         this.logVerbose("registered scratch " + scratch.getXmlName() + " for " + sType);
      }

      FaultMessage m;
      SchemaType type;
      if (this.mFaultTypes != null) {
         it = this.mFaultTypes.iterator();

         while(it.hasNext()) {
            m = (FaultMessage)it.next();
            QName typeName = m.getComponentName();
            type = this.sts.findType(typeName);
            altXmlName = XmlTypeName.forTypeNamed(typeName);
            this.registerScratchForFault(m.getMessageName(), m.getPartName(), type, altXmlName, altXmlName);
         }
      }

      if (this.mFaultElements != null) {
         it = this.mFaultElements.iterator();

         while(it.hasNext()) {
            m = (FaultMessage)it.next();
            SchemaGlobalElement elem = this.sts.findElement(m.getComponentName());
            if (elem != null) {
               type = elem.getType();
               altXmlName = XmlTypeName.forSchemaType(type);
               XmlTypeName outerName = XmlTypeName.forGlobalName('e', m.getComponentName());
               if (type.getTypeSystem() != this.sts) {
                  type = null;
               }

               this.registerScratchForFault(m.getMessageName(), m.getPartName(), type, altXmlName, outerName);
            }
         }
      }

   }

   private void computeDuplicateNameList() {
      Iterator i = this.scratchIterator();

      while(true) {
         while(i.hasNext()) {
            Scratch scratch = (Scratch)i.next();
            if (scratch.getCategory() != 2 && scratch.getCategory() != 3) {
               if (scratch.getCategory() == 10) {
                  String name = this.getClassNameFromQName(scratch.getPreferredName()).toLowerCase();
                  if (this.usedNames.contains(name)) {
                     this.duplicateNames.add(name);
                  }

                  this.usedNames.add(name);
               }
            } else {
               SchemaType sType = scratch.getSchemaType();
               String name = this.getDefaultJavaName(sType).toLowerCase();
               if (this.usedNames.contains(name)) {
                  this.duplicateNames.add(name);
               }

               this.usedNames.add(name);
            }
         }

         this.usedNames.clear();
         return;
      }
   }

   private void resolveJavaName(Scratch scratch) {
      if (scratch == null) {
         this.logVerbose("FIXME null scratch, ignoring for now");
      } else if (scratch == null) {
         throw new IllegalArgumentException("null scratch");
      } else {
         this.logVerbose("Resolving " + scratch.getXmlName());
         if (scratch.getJavaName() == null) {
            SchemaType contentType;
            Scratch itemScratch;
            JavaTypeName contentName;
            String primitive;
            BindingType bType;
            JavaTypeName javaName;
            boolean nillable;
            JavaTypeName boxedName;
            switch (scratch.getCategory()) {
               case 1:
                  this.resolveSimpleScratch(scratch);
                  return;
               case 2:
               case 3:
                  javaName = this.pickUniqueJavaName(scratch.getSchemaType(), scratch.getCategory() == 3);
                  ++this.structureCount;
                  scratch.setJavaName(javaName);
                  this.scratchFromJavaNameString.put(javaName.toString(), scratch);
                  return;
               case 4:
                  contentType = getListItemType(scratch.getSchemaType());
                  itemScratch = this.scratchForSchemaType(contentType);
                  contentName = null;
                  if (itemScratch == null) {
                     if (contentType.isAnonymousType()) {
                        contentName = this.getTypeNameFromLoader(contentType.getBaseType());
                     } else {
                        contentName = this.getTypeNameFromLoader(contentType);
                     }
                  } else {
                     this.resolveJavaName(itemScratch);
                     contentName = itemScratch.getJavaName();
                  }

                  if (contentName != null) {
                     scratch.setJavaName(JavaTypeName.forArray(contentName, 1));
                  }

                  return;
               case 5:
                  contentType = getLiteralArrayItemType(scratch.getSchemaType());
                  nillable = scratch.getSchemaType().getProperties()[0].hasNillable() != 0;
                  Scratch itemScratch = this.scratchForSchemaType(contentType);
                  primitive = null;
                  JavaTypeName itemName;
                  if (itemScratch == null) {
                     itemName = this.getTypeNameFromLoader(contentType);
                  } else {
                     this.resolveJavaName(itemScratch);
                     itemName = itemScratch.getJavaName();
                  }

                  if (itemName != null) {
                     if (nillable) {
                        boxedName = this.getBoxedName(itemName);
                        if (boxedName != null) {
                           scratch.setJavaName(JavaTypeName.forArray(boxedName, 1));
                        } else {
                           scratch.setJavaName(JavaTypeName.forArray(itemName, 1));
                        }
                     } else {
                        scratch.setJavaName(JavaTypeName.forArray(itemName, 1));
                     }
                  }

                  return;
               case 6:
               default:
                  throw new IllegalStateException("Unrecognized category");
               case 7:
                  SchemaProperty[] props = scratch.getSchemaType().getElementProperties();
                  nillable = props != null && props.length == 1 ? props[0].hasNillable() != 0 : false;
                  XmlTypeName arrayName = scratch.getAsIf();
                  if (arrayName == null) {
                     arrayName = scratch.getXmlName();
                  }

                  XmlTypeName itemName = arrayName.getOuterComponent();
                  Scratch itemScratch = this.scratchForXmlName(itemName);
                  bType = null;
                  JavaTypeName itemJavaName;
                  if (itemScratch == null) {
                     itemJavaName = this.getTypeNameFromLoader(itemName);
                     if (itemJavaName == null) {
                        this.logError("Could not find reference to type \"" + itemName.getQName() + "\"", (JElement)null, scratch.getSchemaType());
                        itemJavaName = JavaTypeName.forString("unknown");
                     }
                  } else {
                     this.resolveJavaName(itemScratch);
                     itemJavaName = itemScratch.getJavaName();
                  }

                  if (nillable) {
                     JavaTypeName boxedTypeName = this.getBoxedName(itemJavaName);
                     if (boxedTypeName != null) {
                        scratch.setJavaName(JavaTypeName.forArray(boxedTypeName, arrayName.getNumber()));
                     } else {
                        scratch.setJavaName(JavaTypeName.forArray(itemJavaName, arrayName.getNumber()));
                     }
                  } else {
                     scratch.setJavaName(JavaTypeName.forArray(itemJavaName, arrayName.getNumber()));
                  }

                  return;
               case 8:
               case 9:
                  this.logVerbose("processing element " + scratch.getXmlName());
                  contentType = scratch.getSchemaType().getProperties()[0].getType();
                  nillable = scratch.getSchemaType().getProperties()[0].hasNillable() != 0;
                  this.logVerbose("content type is " + contentType.getName());
                  contentName = null;
                  Scratch contentScratch = this.scratchForSchemaType(contentType);
                  this.logVerbose("content scratch is " + contentScratch);
                  if (contentScratch == null) {
                     XmlTypeName treatAs = XmlTypeName.forSchemaType(contentType);
                     bType = this.mLoader.getBindingType(this.mLoader.lookupPojoFor(treatAs));
                     if (bType != null) {
                        contentName = bType.getName().getJavaName();
                        scratch.setAsIf(treatAs);
                     } else {
                        if (!contentType.isBuiltinType()) {
                           throw new IllegalStateException(contentType.getName().toString() + " type is not on mLoader");
                        }

                        this.logError("Builtin type " + contentType.getName() + " is not supported", (JElement)null, contentType);
                     }
                  } else {
                     this.resolveJavaName(contentScratch);
                     contentName = contentScratch.getJavaName();
                     scratch.setAsIf(contentScratch.getXmlName());
                  }

                  if (contentName != null) {
                     if (nillable) {
                        boxedName = this.getBoxedName(contentName);
                        if (boxedName != null) {
                           scratch.setJavaName(boxedName);
                        } else {
                           scratch.setJavaName(contentName);
                        }
                     } else {
                        scratch.setJavaName(contentName);
                     }
                  }

                  return;
               case 10:
                  javaName = this.pickUniqueJavaName(scratch.getPreferredName());
                  ++this.structureCount;
                  scratch.setJavaName(javaName);
                  this.scratchFromJavaNameString.put(javaName.toString(), scratch);
                  return;
               case 11:
                  contentType = scratch.getSchemaType();
                  String jName = this.javaClassNameForSoapEncPrimitiveWrapper(contentType);
                  contentName = JavaTypeName.forString(jName);
                  scratch.setJavaName(contentName);
                  primitive = contentType.getName().getLocalPart();
                  QName asIfQ = new QName("http://www.w3.org/2001/XMLSchema" != null ? "http://www.w3.org/2001/XMLSchema".intern() : null, primitive != null ? primitive.intern() : null);
                  scratch.setAsIf(XmlTypeName.forTypeNamed(asIfQ));
                  this.scratchFromJavaNameString.put(contentName.toString(), scratch);
                  return;
               case 12:
                  contentType = getListItemType(scratch.getSchemaType()).getBaseType();
                  itemScratch = this.scratchForSchemaType(contentType);
                  contentName = null;
                  if (itemScratch == null) {
                     contentName = this.getTypeNameFromLoader(contentType);
                  } else {
                     this.resolveJavaName(itemScratch);
                     contentName = itemScratch.getJavaName();
                  }

                  if (contentName != null) {
                     scratch.setJavaName(JavaTypeName.forArray(contentName, 1));
                  }

                  return;
               case 13:
                  contentType = getListItemType(scratch.getSchemaType());
                  contentType = isUnion(contentType) ? contentType.getUnionCommonBaseType() : contentType.getBaseType();
                  itemScratch = this.scratchForSchemaType(contentType);
                  contentName = null;
                  if (itemScratch == null) {
                     contentName = this.getTypeNameFromLoader(contentType);
                  } else {
                     this.resolveJavaName(itemScratch);
                     contentName = itemScratch.getJavaName();
                  }

                  if (contentName != null) {
                     scratch.setJavaName(JavaTypeName.forArray(contentName, 1));
                  }

            }
         }
      }
   }

   private void createBindingType(Scratch scratch) {
      assert scratch.getBindingType() == null;

      this.logVerbose("createBindingType for " + scratch);
      BindingTypeName btName = BindingTypeName.forPair(scratch.getJavaName(), scratch.getXmlName());
      this.logVerbose("BindingTypeName is " + btName);
      switch (scratch.getCategory()) {
         case 1:
         case 9:
            SimpleBindingType simpleResult = new SimpleBindingType(btName);
            simpleResult.setAsIfXmlType(scratch.getAsIf());
            scratch.setBindingType(simpleResult);
            this.bindingFile.addBindingType(simpleResult, this.shouldBeFromJavaDefault(btName), true);
            break;
         case 2:
            Object structResult;
            if (scratch.getSchemaType().getContentType() == 2) {
               structResult = new SimpleContentBean(btName);
            } else {
               structResult = new ByNameBean(btName);
            }

            scratch.setBindingType((BindingType)structResult);
            this.bindingFile.addBindingType((BindingType)structResult, true, true);
            break;
         case 3:
            JaxrpcEnumType enumResult = new JaxrpcEnumType(btName);
            enumResult.setGetValueMethod(JaxrpcEnumType.DEFAULT_GET_VALUE);
            enumResult.setFromStringMethod(JaxrpcEnumType.DEFAULT_FROM_STRING);
            enumResult.setToXMLMethod(JaxrpcEnumType.DEFAULT_TO_XML);
            scratch.setBindingType(enumResult);
            this.bindingFile.addBindingType(enumResult, true, true);
            break;
         case 4:
         case 12:
         case 13:
            ListArrayType listResult = new ListArrayType(btName);
            scratch.setBindingType(listResult);
            this.bindingFile.addBindingType(listResult, this.shouldBeFromJavaDefault(btName), true);
            break;
         case 5:
            WrappedArrayType arrayResult = new WrappedArrayType(btName);
            scratch.setBindingType(arrayResult);
            this.bindingFile.addBindingType(arrayResult, this.shouldBeFromJavaDefault(btName), true);
            break;
         case 6:
         default:
            throw new IllegalStateException("Unrecognized category");
         case 7:
            SoapArrayType soapArray = new SoapArrayType(btName);
            scratch.setBindingType(soapArray);
            this.bindingFile.addBindingType(soapArray, true, true);
            break;
         case 8:
            SimpleDocumentBinding docResult = new SimpleDocumentBinding(btName);
            docResult.setTypeOfElement(scratch.getAsIf());
            scratch.setBindingType(docResult);
            this.bindingFile.addBindingType(docResult, this.shouldBeFromJavaDefault(btName), true);
            this.logVerbose(" for ELEMENT added BindingType='" + docResult);
            break;
         case 10:
            ByNameBean faultResult = new ByNameBean(btName);
            scratch.setBindingType(faultResult);
            this.bindingFile.addBindingType(faultResult, true, true);
            break;
         case 11:
            SimpleBindingType sbt = new SimpleBindingType(btName);
            sbt.setAsIfXmlType(scratch.getAsIf());
            scratch.setBindingType(sbt);
            this.bindingFile.addBindingType(sbt, this.shouldBeFromJavaDefault(btName), true);
      }

   }

   private void registerScratchForFault(QName messageName, String partName, SchemaType type, XmlTypeName typeName, XmlTypeName xmlName) {
      if (typeName != null && xmlName != null) {
         if (type != null) {
            Scratch scratch = this.scratchForSchemaType(type);

            assert scratch != null;

            int scratchType = scratch.getCategory();
            if (scratchType == 2) {
               this.setExceptionOnStructBaseType(scratch);
               return;
            }

            if (scratchType != 5 && scratchType != 7 && scratchType != 1 && scratchType != 3 && scratchType != 4) {
               assert false : "Unexpected scratch type: " + scratchType;

               return;
            }
         }

         XmlTypeName scratchName = XmlTypeName.forFaultType(messageName, partName, xmlName);
         Scratch scratch = new Scratch(type, scratchName, 10);
         scratch.setPreferredName(messageName);
         scratch.setPreferredPropertyName(partName);
         scratch.setException(true);
         scratch.setAsIf(typeName);
         this.scratchFromXmlName.put(scratchName, scratch);
      } else {
         throw new IllegalArgumentException();
      }
   }

   private void setExceptionOnStructBaseType(Scratch scratch) {
      assert scratch != null;

      assert scratch.getCategory() == 2;

      SchemaType scratchType = scratch.getSchemaType();
      SchemaType baseType = scratchType.getBaseType();
      Scratch baseScratch = null;
      if (baseType != null && !baseType.isBuiltinType()) {
         baseScratch = this.scratchForSchemaType(baseType);
      }

      if (baseScratch == null) {
         scratch.setException(true);
      } else {
         this.setExceptionOnStructBaseType(baseScratch);
      }

   }

   private boolean shouldBeFromJavaDefault(BindingTypeName btName) {
      JavaTypeName jName = btName.getJavaName();
      XmlTypeName xName = btName.getXmlName();
      if (xName.isSchemaType()) {
         return this.bindingFile.lookupTypeFor(jName) == null && this.mLoader.lookupTypeFor(jName) == null;
      } else if (xName.getComponentType() != 101) {
         return false;
      } else {
         return this.bindingFile.lookupElementFor(jName) == null && this.mLoader.lookupElementFor(jName) == null;
      }
   }

   private void resolveJavaStructure(Scratch scratch) {
      if (scratch.getCategory() == 2) {
         if (!scratch.isStructureResolved()) {
            scratch.setStructureResolved(true);
            SchemaType schemaType = scratch.getSchemaType();
            boolean exception = scratch.isException();
            boolean useCtorIndex = exception;
            SchemaType baseType = exception ? null : schemaType.getBaseType();
            Collection baseProperties = null;
            boolean isDerivedByRestriction = isDerivedByRestriction(schemaType);
            int propertyCount = exception ? 0 : -1;
            if (baseType != null) {
               baseProperties = this.extractProperties(baseType, schemaType.getProperties(), isDerivedByRestriction, scratch);
            }

            if (baseProperties == null) {
               baseProperties = Collections.EMPTY_LIST;
            }

            scratch.setBaseProperties((Collection)baseProperties);
            Map seenAttrProps = new HashMap();
            Map seenEltProps = new HashMap();
            Set seenMethodNames = new HashSet();
            seenMethodNames.add("getClass");
            Iterator i = ((Collection)baseProperties).iterator();

            while(i.hasNext()) {
               QNameProperty prop = (QNameProperty)i.next();
               if (prop.isAttribute()) {
                  seenAttrProps.put(prop.getQName(), prop);
               } else {
                  seenEltProps.put(prop.getQName(), prop);
               }

               if (prop.getGetterName() != null) {
                  seenMethodNames.add(prop.getGetterName());
               }

               if (prop.getSetterName() != null) {
                  seenMethodNames.add(prop.getSetterName());
               }

               if (prop.getCtorParamIndex() >= 0 && prop.getCtorParamIndex() >= propertyCount) {
                  propertyCount = prop.getCtorParamIndex() + 1;
                  useCtorIndex = true;
               }
            }

            String propName;
            GenericXmlProperty prop;
            if (schemaType.getContentType() == 2) {
               SchemaType simpleContentType = this.getBaseSimpleType(schemaType);
               BindingType bType = this.extractBindingType(simpleContentType);
               if (bType == null) {
                  throw new IllegalStateException("Type " + baseType.getName() + " not found in type loader");
               }

               propName = "_value";
               SimpleContentProperty prop = new SimpleContentProperty();
               if (useCtorIndex) {
                  prop.setCtorParamIndex(propertyCount++);
               }

               prop.setSetterName(MethodName.create("set" + propName, bType.getName().getJavaName()));
               prop.setGetterName(MethodName.create("get" + propName));
               prop.setBindingType(bType);
               scratch.setSimpleContentProperty(prop);
            } else if (schemaType.hasElementWildcards()) {
               boolean multiple = this.countWildcards(schemaType.getContentModel()) > 1;
               if (baseType != null && baseType.getBuiltinTypeCode() != 1) {
                  boolean hasBaseElementWildcards = baseType.hasElementWildcards();
                  boolean baseMultiple = this.countWildcards(baseType.getContentModel()) > 1;
                  if (hasBaseElementWildcards && multiple != baseMultiple) {
                     this.logError("Could not bind type\"" + schemaType.getName() + "\" because its base type \"" + baseType.getName() + "\" has only one element wildcard and the current type has more.", (JElement)null, schemaType);
                  }
               }

               prop = new GenericXmlProperty();
               propName = "_any";
               BindingType bType = this.getWildcardElementBindingType(multiple);
               if (useCtorIndex) {
                  prop.setCtorParamIndex(propertyCount++);
               }

               prop.setSetterName(MethodName.create("set" + propName, bType.getName().getJavaName()));
               prop.setGetterName(MethodName.create("get" + propName));
               prop.setBindingType(bType);
               scratch.setAnyElementProperty(prop);
            }

            if (schemaType.hasAttributeWildcards()) {
               String propName = "_anyAttribute";
               prop = new GenericXmlProperty();
               BindingType bType = this.getWildcardAttributeBindingType();
               if (useCtorIndex) {
                  prop.setCtorParamIndex(propertyCount++);
               }

               prop.setSetterName(MethodName.create("set" + propName, bType.getName().getJavaName()));
               prop.setGetterName(MethodName.create("get" + propName));
               prop.setBindingType(bType);
               scratch.setAnyAttributeProperty(prop);
            }

            SchemaProperty[] props = schemaType.getProperties();

            for(int i = 0; i < props.length; ++i) {
               QNameProperty prop = (QNameProperty)(props[i].isAttribute() ? seenAttrProps : seenEltProps).get(props[i].getName());
               if (prop != null) {
                  if (!isDerivedByRestriction && prop.isMultiple() != isMultiple(props[i])) {
                     this.logError("Could not bind element \"" + props[i].getName() + "\" because the corresponding element in the base type has a different 'maxOccurs' value", (JElement)null, props[i]);
                  }
               } else {
                  SchemaType sType = props[i].getType();
                  SchemaField sField = sType.getContainerField();
                  if (sField != null && "schema".equals(sField.getName().getLocalPart()) && "http://www.w3.org/2001/XMLSchema".equals(sField.getName().getNamespaceURI())) {
                     continue;
                  }

                  BindingType bType = this.bindingTypeForSchemaType(sType);
                  if (bType == null) {
                     throw new IllegalStateException("Type " + sType + "not found in type loader");
                  }

                  String propName = this.pickUniquePropertyName(props[i].getName(), seenMethodNames);
                  boolean isMultiple = isMultiple(props[i]);
                  prop = new QNameProperty();
                  prop.setQName(props[i].getName());
                  prop.setAttribute(props[i].isAttribute());
                  prop.setNillable(props[i].hasNillable() != 0);
                  prop.setOptional(isOptional(props[i]));
                  prop.setMultiple(isMultiple);
                  prop.setDefault(props[i].getDefaultText());
                  if (prop.isNillable() || prop.isOptional()) {
                     bType = this.findBoxedType(bType);
                  }

                  prop.setBindingType(bType);
                  if (useCtorIndex) {
                     prop.setCtorParamIndex(propertyCount++);
                  }

                  String setter_name = "set" + propName;
                  prop.setGetterName(MethodName.create("get" + propName));
                  if (bType.getName().getJavaName().getClassName().equals("boolean")) {
                     prop.setIssetterName(MethodName.create("is" + propName));
                  }

                  if (prop.isMultiple()) {
                     JavaTypeName collection_type = JavaTypeName.forArray(bType.getName().getJavaName(), 1);
                     prop.setCollectionClass(collection_type);
                     prop.setSetterName(MethodName.create(setter_name, collection_type));
                  } else {
                     prop.setSetterName(MethodName.create(setter_name, bType.getName().getJavaName()));
                  }
               }

               scratch.addQNameProperty(prop);
            }

         }
      }
   }

   private void resolveJavaException(Scratch scratch) {
      if (scratch.getCategory() == 10) {
         if (!scratch.isStructureResolved()) {
            scratch.setStructureResolved(true);
            QNameProperty prop = new QNameProperty();
            QName propQName = new QName("", scratch.getPreferredPropertyName());
            HashSet seenProps = new HashSet();
            seenProps.add("getClass");
            String propName = this.pickUniquePropertyName(propQName, seenProps);
            prop.setQName(propQName);
            XmlTypeName tn = scratch.getAsIf();
            Scratch tScratch = this.scratchForXmlName(tn);
            BindingType bType;
            if (tScratch == null) {
               bType = this.mLoader.getBindingType(this.mLoader.lookupPojoFor(tn));
            } else {
               bType = tScratch.getBindingType();
            }

            if (bType == null) {
               this.logError("Could not locate type \"" + tn + "\", referenced from WSDL fault message");
            } else {
               prop.setBindingType(bType);
               prop.setCtorParamIndex(0);
               prop.setSetterName(MethodName.create("set" + propName, bType.getName().getJavaName()));
               prop.setGetterName(MethodName.create("get" + propName));
               scratch.addQNameProperty(prop);
            }
         }
      }
   }

   private void resolveJavaArray(Scratch scratch) {
      if (scratch.getCategory() == 5 || scratch.getCategory() == 4 || scratch.getCategory() == 12 || scratch.getCategory() == 13 || scratch.getCategory() == 7) {
         if (!scratch.isStructureResolved()) {
            scratch.setStructureResolved(true);
            BindingType scratchBindingType = scratch.getBindingType();
            JavaTypeName itemName;
            SchemaType sType;
            BindingType itemxType;
            if (scratchBindingType instanceof WrappedArrayType) {
               WrappedArrayType bType = (WrappedArrayType)scratchBindingType;
               itemName = scratch.getJavaName().getArrayItemType(1);

               assert itemName != null;

               sType = getLiteralArrayItemType(scratch.getSchemaType());

               assert sType != null : "This was already checked and determined to be non-null";

               SchemaProperty prop = scratch.getSchemaType().getProperties()[0];
               bType.setItemName(prop.getName());
               itemxType = this.bindingTypeForSchemaType(sType);
               if (itemxType == null) {
                  throw new IllegalStateException("Type " + sType.getName() + " not found in type loader");
               }

               bType.setItemNillable(prop.hasNillable() != 0);
               if (bType.isItemNillable()) {
                  itemxType = this.findBoxedType(itemxType);
               }

               bType.setItemType(itemxType.getName());
            } else {
               if (!(scratchBindingType instanceof ListArrayType)) {
                  if (scratchBindingType instanceof SoapArrayType) {
                     SoapArrayType bType = (SoapArrayType)scratchBindingType;
                     XmlTypeName typexName = scratch.getAsIf();
                     if (typexName == null) {
                        typexName = scratch.getXmlName();
                     }

                     XmlTypeName itemxName = typexName.getOuterComponent();
                     Scratch itemxScratch = this.scratchForXmlName(itemxName);
                     if (itemxScratch == null) {
                        itemxType = this.mLoader.getBindingType(this.mLoader.lookupPojoFor(itemxName));
                     } else {
                        itemxType = itemxScratch.getBindingType();
                     }

                     SchemaProperty[] props = scratch.getSchemaType().getElementProperties();
                     boolean hasElemProperty = props.length == 1;
                     if (hasElemProperty) {
                        bType.setItemName(props[0].getName());
                        bType.setItemNillable(props[0].hasNillable() != 0);
                     }

                     if (itemxType != null) {
                        if (hasElemProperty) {
                           if (bType.isItemNillable()) {
                              itemxType = this.findBoxedType(itemxType);
                           }
                        } else {
                           JavaTypeName boxedJavaName = this.getBoxedName(itemxType.getName().getJavaName());
                           if (boxedJavaName == null) {
                              bType.setItemNillable(true);
                           } else {
                              bType.setItemNillable(false);
                           }
                        }

                        bType.setItemType(itemxType.getName());
                     }

                     assert scratch.getSoapRank() != -1 : "Rank has to be >= 1 for SOAP Arrays";

                     bType.setRanks(scratch.getSoapRank());
                     return;
                  }

                  throw new IllegalStateException();
               }

               ListArrayType bType = (ListArrayType)scratchBindingType;
               itemName = scratch.getJavaName().getArrayItemType(1);

               assert itemName != null;

               sType = null;
               if (scratch.getCategory() == 12) {
                  sType = getListItemType(scratch.getSchemaType()).getBaseType();
               } else if (scratch.getCategory() == 13) {
                  sType = getListItemType(scratch.getSchemaType());
                  sType = isUnion(sType) ? sType.getUnionCommonBaseType() : sType.getBaseType();
               } else {
                  sType = getListItemType(scratch.getSchemaType());
               }

               assert sType != null;

               BindingType itemType = this.bindingTypeForSchemaType(sType);
               if (itemType == null) {
                  if (!sType.isAnonymousType()) {
                     throw new IllegalStateException("Type " + sType.getName() + " not found in the type loader");
                  }

                  itemType = this.bindingTypeForSchemaType(sType.getOuterType());
               }

               bType.setItemType(itemType.getName());
            }

         }
      }
   }

   private void resolveJavaEnumeration(Scratch scratch) {
      if (scratch.getCategory() == 3) {
         if (!scratch.isStructureResolved()) {
            scratch.setStructureResolved(true);
            SchemaType baseType = scratch.getSchemaType().getBaseType();

            while(!baseType.isBuiltinType() && baseType.getBaseType() != null) {
               SchemaType superBaseType = baseType;
               baseType = baseType.getBaseType();
               if (baseType.getName().equals(superBaseType.getName())) {
                  break;
               }
            }

            BindingType bType = this.bindingTypeForSchemaType(baseType);
            if (bType == null) {
               throw new IllegalArgumentException("When trying to resolve SimpleType enumeration, could not load bindingType for baseType '" + baseType + "', we're expecting a baseType that is an XML Schema Builtin");
            } else {
               JaxrpcEnumType enumType = (JaxrpcEnumType)scratch.getBindingType();
               enumType.setFromValueMethod(MethodName.create("fromValue", bType.getName().getJavaName()));
               enumType.setBaseType(bType);
            }
         }
      }
   }

   private void processWrapperElements() {
      Iterator it = this.nameListFromWrapperElement.keySet().iterator();

      while(it.hasNext()) {
         QName n = (QName)it.next();
         LinkedHashMap schemaTypesMap = (LinkedHashMap)this.nameListFromWrapperElement.get(n);
         SchemaType docType = this.sts.findDocumentType(n);
         WrappedArrayType wrapElementBindingType = null;
         boolean isWrappedArray = false;
         if (docType != null) {
            Scratch scratch = this.scratchForSchemaType(docType);

            while(!isWrappedArray && scratch != null && docType != null && scratch.getCategory() == 8 && docType.getProperties() != null) {
               docType = docType.getProperties()[0].getType();
               scratch = this.scratchForSchemaType(docType);
               if (scratch.getCategory() == 5 && scratch.getBindingType() != null) {
                  isWrappedArray = true;
                  wrapElementBindingType = (WrappedArrayType)scratch.getBindingType();
               }
            }
         }

         Map.Entry entry;
         String javaName;
         for(Iterator it2 = schemaTypesMap.entrySet().iterator(); it2.hasNext(); entry.setValue(javaName)) {
            entry = (Map.Entry)it2.next();
            SchemaProperty schemaProperty = (SchemaProperty)entry.getValue();
            javaName = null;
            String elemName = (String)entry.getKey();
            if (elemName.equals(XmlTypeName.forElementWildCardElement().getQName().getLocalPart())) {
               javaName = SOAPELEMENT_CLASSNAME;
            } else if (elemName.equals(XmlTypeName.forElementWildCardArrayElement().getQName().getLocalPart())) {
               javaName = SOAPELEMENT_CLASSNAME + "[]";
            } else {
               BindingType bType = this.bindingTypeForSchemaType(schemaProperty.getType());
               if (bType == null) {
                  this.logError("Could not find Java binding for type \"" + schemaProperty.getType().getName() + "\"", schemaProperty.getType());
                  javaName = "java.lang.Object";
               } else {
                  javaName = bType.getName().getJavaName().toString();
                  if (this.isBoxed(schemaProperty)) {
                     BindingType boxedBindingType = this.findBoxedType(bType);
                     if (boxedBindingType != null) {
                        javaName = boxedBindingType.getName().getJavaName().toString();
                     }
                  }

                  if (isWrappedArray) {
                     javaName = wrapElementBindingType.getName().getJavaName().toString();
                  }
               }
            }
         }
      }

   }

   private boolean isBoxed(SchemaProperty schemaProperty) {
      return schemaProperty.hasNillable() != 0 || isOptional(schemaProperty);
   }

   private String pickUniquePropertyName(QName name, Set seenMethodNames) {
      String baseName = NameUtil.upperCamelCase(name.getLocalPart(), this.mJaxRpcRules);
      String propName = baseName;
      int i = 1;

      while(true) {
         String getter = "get" + propName;
         String setter = "set" + propName;
         if (!seenMethodNames.contains(getter) && !seenMethodNames.contains(setter)) {
            seenMethodNames.add(getter);
            seenMethodNames.add(setter);
            return propName;
         }

         propName = baseName + i;
         ++i;
      }
   }

   private static boolean isMultiple(SchemaProperty prop) {
      return prop.getMaxOccurs() == null || prop.getMaxOccurs().compareTo(BigInteger.ONE) > 0;
   }

   private static boolean isDerivedByRestriction(SchemaType type) {
      return type.getDerivationType() == 1;
   }

   private static boolean isOptional(SchemaProperty prop) {
      return prop.getMinOccurs().signum() == 0 && !isMultiple(prop);
   }

   private Collection extractProperties(SchemaType sType, SchemaProperty[] props, boolean isDerivedByRestriction, Scratch childScratch) {
      Scratch scratch = this.scratchForSchemaType(sType);
      if (scratch != null) {
         if (scratch.getCategory() != 2) {
            return null;
         }

         this.resolveJavaStructure(scratch);
         if (!isDerivedByRestriction) {
            return scratch.getQNameProperties();
         }

         List parentPropList = new LinkedList(scratch.getQNameProperties());
         Iterator parentIter = parentPropList.iterator();

         while(parentIter.hasNext()) {
            QNameProperty parentProp = (QNameProperty)parentIter.next();

            for(int i = 0; i < props.length; ++i) {
               if (props[i].getName().equals(parentProp.getQName()) && (parentProp.isMultiple() != isMultiple(props[i]) || parentProp.isOptional() != isOptional(props[i]))) {
                  childScratch.setMultiplicityChanged(true);
                  parentPropList.clear();
                  return null;
               }
            }
         }
      }

      BindingType bType = this.mLoader.getBindingType(this.mLoader.lookupPojoFor(XmlTypeName.forSchemaType(sType)));
      Collection result = new ArrayList();
      Iterator i;
      if (bType instanceof ByNameBean) {
         ByNameBean bnb = (ByNameBean)bType;
         i = bnb.getProperties().iterator();

         while(i.hasNext()) {
            result.add(i.next());
         }
      } else {
         if (!(bType instanceof SimpleContentBean)) {
            return null;
         }

         SimpleContentBean scb = (SimpleContentBean)bType;
         i = scb.getAttributeProperties().iterator();

         while(i.hasNext()) {
            result.add(i.next());
         }
      }

      return result;
   }

   private SchemaType getBaseSimpleType(SchemaType type) {
      while(type != null && !type.isSimpleType()) {
         type = type.getContentBasedOnType();
      }

      return type;
   }

   private BindingType extractBindingType(SchemaType sType) {
      Scratch scratch = this.scratchForSchemaType(sType);
      if (scratch != null) {
         return scratch.getBindingType();
      } else {
         BindingType bType = this.mLoader.getBindingType(this.mLoader.lookupPojoFor(XmlTypeName.forSchemaType(sType)));
         return bType;
      }
   }

   private BindingType getWildcardElementBindingType(boolean multiple) {
      this.logVerbose(" --- getWildcardElementBindingType called. ---");
      JavaTypeName javaName = JavaTypeName.forString(WILDCARD_ELEMENT_MAPPING);
      if (multiple) {
         javaName = JavaTypeName.forArray(javaName, 1);
      }

      XmlTypeName xmlName = XmlTypeName.forTypeNamed(new QName("http://www.w3.org/2001/XMLSchema", "anyType"));
      return new SimpleBindingType(BindingTypeName.forPair(javaName, xmlName));
   }

   private BindingType getWildcardAttributeBindingType() {
      JavaTypeName javaName = JavaTypeName.forString(WILDCARD_ATTRIBUTE_MAPPING);
      XmlTypeName xmlName = XmlTypeName.forTypeNamed(new QName("http://www.w3.org/2001/XMLSchema", "anyType"));
      return new SimpleBindingType(BindingTypeName.forPair(javaName, xmlName));
   }

   private JavaTypeName getBoxedName(JavaTypeName jName) {
      for(int i = 0; i < PRIMITIVE_TYPES.length; ++i) {
         if (PRIMITIVE_TYPES[i].equals(jName.toString())) {
            return JavaTypeName.forString(BOXED_TYPES[i]);
         }
      }

      return null;
   }

   private BindingType findBoxedType(BindingType type) {
      BindingTypeName btName = type.getName();
      JavaTypeName javaName = btName.getJavaName();
      BindingType result = null;
      JavaTypeName boxedJavaName = this.getBoxedName(javaName);
      if (boxedJavaName != null) {
         BindingTypeName boxedName = BindingTypeName.forPair(boxedJavaName, btName.getXmlName());
         if (this.scratchForXmlName(btName.getXmlName()) != null) {
            result = this.bindingFile.getBindingType(boxedName);
            if (result == null) {
               result = this.changeJavaName((SimpleBindingType)type, boxedName);
               this.bindingFile.addBindingType(result, false, false);
            }

            return result;
         } else {
            result = this.mLoader.getBindingType(boxedName);
            if (result != null) {
               return result;
            } else {
               result = this.bindingFile.getBindingType(boxedName);
               if (result == null) {
                  result = this.changeJavaName((SimpleBindingType)type, boxedName);
                  this.bindingFile.addBindingType(result, false, false);
               }

               return result;
            }
         }
      } else {
         return type;
      }
   }

   private BindingType changeJavaName(SimpleBindingType bType, BindingTypeName btName) {
      SimpleBindingType result = new SimpleBindingType(btName);
      result.setAsIfXmlType(bType.getAsIfXmlType());
      result.setWhitespace(bType.getWhitespace());
      return result;
   }

   private static boolean isSoapArray(SchemaType sType) {
      while(sType != null) {
         String signature = XmlTypeName.forSchemaType(sType).toString();
         if (signature.equals("t=Array@http://schemas.xmlsoap.org/soap/encoding/") || signature.startsWith("t=Array@http://www.w3.org/") && signature.endsWith("/soap-encoding")) {
            return true;
         }

         sType = sType.getBaseType();
      }

      return false;
   }

   private static XmlTypeName soapArrayTypeName(SchemaType sType) {
      SOAPArrayType defaultArrayType = getWsdlArrayType(sType);
      if (defaultArrayType != null) {
         return XmlTypeName.forSoapArrayType(defaultArrayType);
      } else {
         SchemaType itemType = XmlObject.type;
         SchemaProperty[] props = sType.getElementProperties();
         if (props.length == 1) {
            itemType = props[0].getType();
         }

         return XmlTypeName.forNestedNumber('y', 1, XmlTypeName.forSchemaType(itemType));
      }
   }

   private static SOAPArrayType getWsdlArrayType(SchemaType sType) {
      SchemaLocalAttribute attr = sType.getAttributeModel().getAttribute(arrayType);
      return attr != null ? ((SchemaWSDLArrayType)attr).getWSDLArrayType() : null;
   }

   private String findContainingNamespace(SchemaType sType) {
      while(!sType.isDocumentType()) {
         if (sType.isAttributeType()) {
            return sType.getAttributeTypeAttributeName().getNamespaceURI();
         }

         if (sType.getName() != null) {
            return sType.getName().getNamespaceURI();
         }

         sType = sType.getOuterType();
      }

      return sType.getDocumentElementName().getNamespaceURI();
   }

   private int countWildcards(SchemaParticle p) {
      if (p == null) {
         return 0;
      } else {
         int totalWildcards = 0;
         SchemaParticle[] children;
         int i;
         switch (p.getParticleType()) {
            case 1:
            case 3:
               children = p.getParticleChildren();

               for(i = 0; i < children.length; ++i) {
                  totalWildcards += this.countWildcards(children[i]);
               }

               return totalWildcards;
            case 2:
               children = p.getParticleChildren();

               for(i = 0; i < children.length; ++i) {
                  int n = this.countWildcards(children[i]);
                  if (n > totalWildcards) {
                     totalWildcards = n;
                  }
               }
            case 4:
            default:
               break;
            case 5:
               totalWildcards = p.getIntMaxOccurs();
         }

         return totalWildcards;
      }
   }

   private JavaTypeName pickUniqueJavaName(QName preferredName) {
      String baseName = this.getClassNameFromQName(preferredName);
      if (this.duplicateNames.contains(baseName.toLowerCase())) {
         baseName = baseName + "_Exception";
      }

      return this.makeNameUnique(baseName);
   }

   private JavaTypeName pickUniqueJavaName(SchemaType sType, boolean isEnum) {
      String baseName = this.getDefaultJavaName(sType);
      if (this.duplicateNames.contains(baseName.toLowerCase())) {
         if (isEnum) {
            baseName = baseName + "_Enumeration";
         } else if (sType.getContainerField() != null && !sType.getContainerField().isAttribute()) {
            baseName = baseName + "_Element";
         } else if (sType.getName() != null) {
            baseName = baseName + "_Type";
         }
      }

      return this.makeNameUnique(baseName);
   }

   private String getDefaultJavaName(SchemaType sType) {
      QName qname;
      for(qname = null; qname == null; sType = sType.getOuterType()) {
         if (sType.isDocumentType()) {
            qname = sType.getDocumentElementName();
         } else if (sType.isAttributeType()) {
            qname = sType.getAttributeTypeAttributeName();
         } else if (sType.getName() != null) {
            qname = sType.getName();
         } else if (sType.getContainerField() != null) {
            qname = sType.getContainerField().getName();
            if (qname.getNamespaceURI().length() == 0) {
               qname = new QName(this.findContainingNamespace(sType), qname.getLocalPart());
            }
         }
      }

      this.logVerbose(" getDefaultJavaName:  qname is '" + qname + "'  about to call NameUtil.getClassNameFromQName");
      String base = this.getClassNameFromQName(qname);
      if (this.config != null) {
         String configname = this.config.lookupJavanameForQName(qname);
         if (configname != null && configname.indexOf(46) >= 0) {
            return configname;
         }

         String uri = qname.getNamespaceURI();
         String pkgPrefix = this.config.lookupPackageForNamespace(uri);
         if (pkgPrefix != null) {
            base = pkgPrefix + "." + base.substring(base.lastIndexOf(46) + 1);
         }

         String javaPrefix = this.config.lookupPrefixForNamespace(uri);
         if (javaPrefix != null) {
            base = base.substring(0, base.lastIndexOf(46) + 1) + javaPrefix + base.substring(base.lastIndexOf(46) + 1);
         }

         if (configname != null) {
            base = base.substring(0, base.lastIndexOf(46) + 1) + configname;
         } else {
            String javaSuffix = this.config.lookupSuffixForNamespace(uri);
            if (javaSuffix != null) {
               base = base + javaSuffix;
            }
         }
      }

      return base;
   }

   private String getClassNameFromQName(QName qname) {
      String clazz = NameUtil.getClassNameFromQName(qname, this.mJaxRpcRules);
      if (clazz.startsWith("java.")) {
         clazz = "x" + clazz;
      }

      return clazz;
   }

   private JavaTypeName makeNameUnique(String baseName) {
      String pickedName = baseName;

      for(int i = 1; this.usedNames.contains(pickedName.toLowerCase()); ++i) {
         pickedName = baseName + i;
      }

      this.usedNames.add(pickedName.toLowerCase());
      return JavaTypeName.forString(pickedName);
   }

   private void resolveSimpleScratch(Scratch scratch) {
      assert scratch.getCategory() == 1;

      this.logVerbose("resolveSimpleScratch " + scratch.getXmlName());
      if (scratch.getJavaName() == null) {
         for(SchemaType baseType = scratch.getSchemaType().getBaseType(); baseType != null; baseType = baseType.getBaseType()) {
            Scratch basedOnScratch = this.scratchForSchemaType(baseType);
            if (basedOnScratch != null) {
               if (basedOnScratch.getCategory() != 1) {
                  throw new IllegalStateException("Atomic types should only inherit from atomic types");
               }

               this.resolveSimpleScratch(basedOnScratch);
               scratch.setJavaName(basedOnScratch.getJavaName());
               scratch.setAsIf(basedOnScratch.getXmlName());
               return;
            }

            XmlTypeName treatAs = XmlTypeName.forSchemaType(baseType);
            BindingType basedOnBinding = this.mLoader.getBindingType(this.mLoader.lookupPojoFor(treatAs));
            if (basedOnBinding != null) {
               scratch.setJavaName(basedOnBinding.getName().getJavaName());
               scratch.setAsIf(treatAs);
               return;
            }
         }

         throw new IllegalStateException("Builtin binding type loader is not on mLoader.");
      }
   }

   private JavaTypeName getTypeNameFromLoader(SchemaType sType) {
      return this.getTypeNameFromLoader(XmlTypeName.forSchemaType(sType));
   }

   private JavaTypeName getTypeNameFromLoader(XmlTypeName typeName) {
      BindingType bType = this.mLoader.getBindingType(this.mLoader.lookupPojoFor(typeName));
      return bType != null ? bType.getName().getJavaName() : null;
   }

   private BindingType bindingTypeForSchemaType(SchemaType sType) {
      Scratch scratch = this.scratchForSchemaType(sType);
      return scratch != null ? scratch.getBindingType() : this.mLoader.getBindingType(this.mLoader.lookupPojoFor(XmlTypeName.forSchemaType(sType)));
   }

   private Scratch scratchForSchemaType(SchemaType sType) {
      return (Scratch)this.scratchFromSchemaType.get(sType);
   }

   private Scratch scratchForXmlName(XmlTypeName xmlName) {
      return (Scratch)this.scratchFromXmlName.get(xmlName);
   }

   private Scratch scratchForJavaNameString(String javaName) {
      return (Scratch)this.scratchFromJavaNameString.get(javaName);
   }

   private static SchemaType getLiteralArrayItemType(SchemaType sType) {
      if (!sType.isSimpleType() && sType.getContentType() != 2) {
         SchemaProperty[] prop = sType.getProperties();
         if (prop.length == 1 && !prop[0].isAttribute()) {
            BigInteger max = prop[0].getMaxOccurs();
            return max != null && max.compareTo(BigInteger.ONE) <= 0 ? null : prop[0].getType();
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private static SchemaType getListItemType(SchemaType sType) {
      return sType.getListItemType();
   }

   private static boolean isLiteralArray(SchemaType sType) {
      return getLiteralArrayItemType(sType) != null;
   }

   private static boolean isEnumeration(SchemaType sType) {
      return sType.getEnumerationValues() != null;
   }

   private static boolean isUnion(SchemaType sType) {
      SchemaType[] members = sType.getUnionMemberTypes();
      return members != null && members.length > 0;
   }

   private static boolean isList(SchemaType sType) {
      return getListItemType(sType) != null;
   }

   private Iterator scratchIterator() {
      if (this.mSortSchemaTypes) {
         ArrayList scratches = new ArrayList(this.scratchFromXmlName.values());
         Collections.sort(scratches, Schema2Java.ScratchComparator.INSTANCE);
         return scratches.iterator();
      } else {
         return this.scratchFromXmlName.values().iterator();
      }
   }

   private Iterator allTypeIterator(boolean includeGlobalTypes) {
      if ((this.mParamTypes == null || this.mParamTypes.isEmpty()) && (this.mParamElements == null || this.mParamElements.isEmpty()) && (this.mFaultTypes == null || this.mFaultTypes.isEmpty()) && (this.mFaultElements == null || this.mFaultElements.isEmpty()) && (this.mWrapperOperations == null || this.mWrapperOperations.isEmpty())) {
         class AllTypeIterator implements Iterator {
            int index;
            List allSeenTypes = new ArrayList();

            AllTypeIterator(SchemaTypeSystem sts) {
               this.allSeenTypes.addAll(Arrays.asList(sts.documentTypes()));
               this.allSeenTypes.addAll(Arrays.asList(sts.attributeTypes()));
               this.allSeenTypes.addAll(Arrays.asList(sts.globalTypes()));
               this.index = 0;
            }

            public boolean hasNext() {
               return this.index < this.allSeenTypes.size();
            }

            public Object next() {
               SchemaType next = (SchemaType)this.allSeenTypes.get(this.index);
               this.allSeenTypes.addAll(Arrays.asList(next.getAnonymousTypes()));
               ++this.index;
               return next;
            }

            public void remove() {
               throw new UnsupportedOperationException();
            }
         }

         return new AllTypeIterator(this.sts);
      } else {
         ArrayList arg1;
         if (this.mParamTypes == null) {
            arg1 = new ArrayList();
         } else {
            arg1 = new ArrayList(this.mParamTypes);
         }

         if (this.mFaultTypes != null) {
            Iterator it = this.mFaultTypes.iterator();

            while(it.hasNext()) {
               FaultMessage m = (FaultMessage)it.next();
               arg1.add(m.getComponentName());
            }
         }

         ArrayList arg2;
         if (this.mParamElements == null) {
            arg2 = new ArrayList();
         } else {
            arg2 = new ArrayList(this.mParamElements);
         }

         if (this.mFaultElements != null) {
            Iterator it = this.mFaultElements.iterator();

            while(it.hasNext()) {
               FaultMessage m = (FaultMessage)it.next();
               arg2.add(m.getComponentName());
            }
         }

         List arg3 = this.mWrapperOperations;
         if (arg3 == null) {
            arg3 = new ArrayList();
         }

         return this.transitiveClosure(this.sts, arg1, arg2, (List)arg3, includeGlobalTypes);
      }
   }

   private Iterator transitiveClosure(SchemaTypeSystem sts, List startingTypes, List startingElements, List wrapperOperations, boolean includeGlobalTypes) {
      Set allTypes = new LinkedHashSet();
      Set globalTypes = new HashSet(Arrays.asList(sts.globalTypes()));
      LinkedList scanNeeded = new LinkedList();
      SchemaType t;
      SchemaType t;
      if (includeGlobalTypes) {
         Set globalTypesCopy = new HashSet();
         globalTypesCopy.addAll(globalTypes);
         Iterator i = globalTypesCopy.iterator();

         while(i.hasNext()) {
            t = (SchemaType)i.next();
            if (!allTypes.contains(t)) {
               t = t.getBaseType();
               if (t != null && !allTypes.contains(t) && sts == t.getTypeSystem()) {
                  allTypes.add(t);
                  globalTypes.remove(t);
                  scanNeeded.add(t);
               }

               allTypes.add(t);
               globalTypes.remove(t);
               scanNeeded.add(t);
            }
         }
      }

      Iterator it = startingTypes.iterator();

      QName elementName;
      while(it.hasNext()) {
         elementName = (QName)it.next();
         if (elementName != null) {
            t = sts.findType(elementName);
            if (t != null && !allTypes.contains(t)) {
               allTypes.add(t);
               globalTypes.remove(t);
               scanNeeded.add(t);
            }
         }
      }

      it = startingElements.iterator();

      while(it.hasNext()) {
         elementName = (QName)it.next();
         if (elementName != null) {
            t = sts.findDocumentType(elementName);
            if (t != null && !allTypes.contains(t)) {
               allTypes.add(t);
               scanNeeded.add(t);
            }
         }
      }

      it = wrapperOperations.iterator();

      while(true) {
         while(it.hasNext()) {
            WrappedOperationInfo info = (WrappedOperationInfo)it.next();
            boolean isWrapped = info.isWrapped(sts);
            boolean isWrappedArray = info.isWrappedArray();
            QName[] elements = info.getElements();
            SchemaType[] docTypes = new SchemaType[elements.length];

            int i;
            for(i = 0; i < elements.length; ++i) {
               QName element = elements[i];
               docTypes[i] = sts.findDocumentType(element);
               if (allTypes.contains(docTypes[i])) {
                  isWrapped = false;
               }
            }

            SchemaType docType;
            if (isWrapped) {
               for(i = 0; i < docTypes.length; ++i) {
                  docType = docTypes[i];
                  if (isWrappedArray && !info.isWrappedElement(sts, elements[i], false)) {
                     allTypes.add(docType);
                     scanNeeded.add(docType);
                  }

                  SchemaType type = docType.getProperties()[0].getType();
                  this.addToNameListFromWrapperElement(type, elements[i]);
                  SchemaProperty[] props = type.getProperties();

                  for(int j = 0; j < props.length; ++j) {
                     SchemaType t = props[j].getType();
                     if (!allTypes.contains(t) && sts == t.getTypeSystem()) {
                        allTypes.add(t);
                        if (!t.isAnonymousType()) {
                           globalTypes.remove(t);
                        }

                        scanNeeded.add(t);
                     }
                  }
               }
            } else {
               for(i = 0; i < docTypes.length; ++i) {
                  docType = docTypes[i];
                  allTypes.add(docType);
                  scanNeeded.add(docType);
               }
            }
         }

         while(!scanNeeded.isEmpty()) {
            label175:
            while(true) {
               SchemaType type;
               label167:
               do {
                  do {
                     do {
                        if (scanNeeded.isEmpty()) {
                           it = globalTypes.iterator();

                           while(true) {
                              while(true) {
                                 SchemaType type;
                                 do {
                                    if (!it.hasNext()) {
                                       continue label175;
                                    }

                                    type = (SchemaType)it.next();
                                 } while(allTypes.contains(type));

                                 for(t = type.getBaseType(); t.getBuiltinTypeCode() != 1; t = t.getBaseType()) {
                                    if (sts == t.getTypeSystem() && allTypes.contains(t)) {
                                       allTypes.add(type);
                                       it.remove();
                                       scanNeeded.add(type);
                                       break;
                                    }
                                 }
                              }
                           }
                        }

                        type = (SchemaType)scanNeeded.removeFirst();
                     } while(type.isSimpleType() && type.getBaseType().isBuiltinType());

                     SchemaProperty[] props = type.getProperties();

                     for(int i = 0; i < props.length; ++i) {
                        t = props[i].getType();
                        if (!allTypes.contains(t) && sts == t.getTypeSystem()) {
                           allTypes.add(t);
                           if (!t.isAnonymousType()) {
                              globalTypes.remove(t);
                           }

                           scanNeeded.add(t);
                        }
                     }

                     t = type.getBaseType();
                     if (t != null && !allTypes.contains(t) && sts == t.getTypeSystem()) {
                        allTypes.add(t);
                        globalTypes.remove(t);
                        scanNeeded.add(t);
                     }

                     if (type.getContentType() != 2) {
                        continue label167;
                     }

                     t = this.getBaseSimpleType(type);
                     if (sts != t.getTypeSystem()) {
                        continue label167;
                     }
                  } while(allTypes.contains(t));

                  allTypes.add(t);
                  scanNeeded.add(t);
               } while(!isSoapArray(type));

               XmlTypeName xmlName;
               for(xmlName = soapArrayTypeName(type); xmlName.getComponentType() == 121; xmlName = xmlName.getOuterComponent()) {
               }

               SchemaType t = sts.findType(xmlName.getQName());
               if (t != null && !allTypes.contains(t)) {
                  allTypes.add(t);
                  if (!t.isAnonymousType()) {
                     globalTypes.remove(t);
                  }

                  scanNeeded.add(t);
               }
            }
         }

         return allTypes.iterator();
      }
   }

   private void addToNameListFromWrapperElement(SchemaType wrapperType, QName elementName) {
      LinkedHashMap typeMap = new LinkedHashMap(1);
      SchemaParticle contentModel = wrapperType.getContentModel();
      if (contentModel != null) {
         if (contentModel.getParticleType() == 4) {
            this.addElementToTypeMap(contentModel, wrapperType, typeMap);
         } else if (contentModel.getParticleType() == 5) {
            this.addWildcardToTypeMap(contentModel, typeMap);
         } else {
            SchemaParticle[] particles = contentModel.getParticleChildren();

            for(int i = 0; i < particles.length; ++i) {
               SchemaParticle particle = particles[i];
               if (particle.getParticleType() == 4) {
                  this.addElementToTypeMap(particle, wrapperType, typeMap);
               } else if (particle.getParticleType() == 5) {
                  this.addWildcardToTypeMap(particle, typeMap);
               }
            }
         }
      }

      this.nameListFromWrapperElement.put(elementName, typeMap);
   }

   private void addElementToTypeMap(SchemaParticle particle, SchemaType wrapperType, LinkedHashMap typeMap) {
      SchemaProperty property = wrapperType.getElementProperty(particle.getName());
      typeMap.put(property.getName().getLocalPart(), property);
   }

   private void addWildcardToTypeMap(SchemaParticle particle, LinkedHashMap typeMap) {
      String elemName = null;
      if (particle.getIntMaxOccurs() > 1) {
         elemName = XmlTypeName.forElementWildCardArrayElement().getQName().getLocalPart();
      } else {
         elemName = XmlTypeName.forElementWildCardElement().getQName().getLocalPart();
      }

      typeMap.put(elemName, (Object)null);
   }

   public static boolean hasWrapperFormat(SchemaType type, boolean max1Property) {
      if (type.isSimpleType()) {
         return false;
      } else if (type.getAttributeProperties().length > 0) {
         return false;
      } else if (type.getContentType() == 1) {
         return true;
      } else if (type.getContentType() != 3 && type.getContentType() != 4) {
         return false;
      } else {
         SchemaParticle contents = type.getContentModel();
         if (contents.getParticleType() == 4) {
            return true;
         } else {
            SchemaProperty[] props = type.getElementProperties();
            if (props.length == 0 && contents.countOfParticleChild() == 0 && contents.getParticleType() == 5) {
               return true;
            } else if (contents.getParticleType() != 3) {
               return false;
            } else {
               SchemaParticle[] children = contents.getParticleChildren();
               if (max1Property && children.length > 1) {
                  return false;
               } else {
                  HashSet usedNames = new HashSet();

                  for(int i = 0; i < children.length; ++i) {
                     if (children[i].getParticleType() != 5) {
                        if (children[i].getParticleType() != 4) {
                           return false;
                        }

                        QName name = children[i].getName();
                        if (usedNames.contains(name)) {
                           return false;
                        }

                        usedNames.add(name);
                     }
                  }

                  return true;
               }
            }
         }
      }
   }

   private void writeJavaFiles() {
      Collection classnames = this.getToplevelClasses();
      Iterator it = null;
      if (ISPostJdk8) {
         it = this.removeJDKFile(classnames.iterator());
      } else {
         it = classnames.iterator();
      }

      while(it.hasNext()) {
         String className = (String)it.next();
         this.printSourceCode(className);
      }

   }

   private Iterator removeJDKFile(Iterator it) {
      ArrayList tmp = new ArrayList();

      while(true) {
         String clzz;
         String packageName;
         do {
            do {
               if (!it.hasNext()) {
                  return tmp.iterator();
               }

               clzz = (String)it.next();
            } while(null == clzz);

            int index = clzz.lastIndexOf(46);
            if (index <= 0) {
               break;
            }

            packageName = clzz.substring(0, index);
         } while(packageName.startsWith("java.") || packageName.startsWith("javax.") || packageName.startsWith("jdk.") || BLACKPACKAGE.contains(packageName));

         tmp.add(clzz);
      }
   }

   public Collection getToplevelClasses() {
      return new AbstractCollection() {
         public Iterator iterator() {
            return Schema2Java.this.new TopLevelClassNameIterator();
         }

         public int size() {
            return Schema2Java.this.structureCount;
         }
      };
   }

   private void printSourceCode(String topLevelClassName) {
      Scratch scratch = this.scratchForJavaNameString(topLevelClassName);
      if (scratch == null) {
         this.logError("Could not find scratch for " + topLevelClassName);
      } else {
         try {
            this.printClass(scratch);
         } catch (IOException var4) {
            this.logError(var4);
         }

      }
   }

   private void printClass(Scratch scratch) throws IOException {
      assert scratch.getCategory() == 2 || scratch.getCategory() == 3 || scratch.getCategory() == 10;

      JavaTypeName javaName = scratch.getJavaName();
      String packageName = javaName.getPackage();
      String shortClassName = javaName.getShortClassName();
      String baseJavaname = null;
      if (scratch.isException()) {
         baseJavaname = "java.lang.Exception";
      } else {
         BindingType baseType = null;
         SchemaType bSchemaType = scratch.getSchemaType().getBaseType();
         if (!bSchemaType.isSimpleType()) {
            baseType = this.bindingTypeForSchemaType(bSchemaType);
         }

         if (baseType != null && !scratch.isMultiplicityChanged()) {
            baseJavaname = baseType.getName().getJavaName().toString();
            if (baseJavaname.equals("java.lang.Object")) {
               baseJavaname = null;
            }
         }
      }

      this.mJoust.startFile(packageName, shortClassName);
      this.mJoust.writeComment("Generated from schema type " + scratch.getXmlName());
      if (scratch.getCategory() == 3) {
         this.printJavaEnum(scratch, baseJavaname);
      } else {
         this.printJavaStruct(scratch, baseJavaname);
      }

      this.mJoust.endFile();
   }

   private void printJavaStruct(Scratch scratch, String baseJavaName) throws IOException {
      this.mJoust.startClass(1, baseJavaName, baseJavaName == null ? IMPLEMENTED_INTERFACES : null);
      Set seenFieldNames = new HashSet();
      ConstructorBuilder ctorBuilder = new ConstructorBuilder();
      SimpleContentProperty scprop = scratch.getSimpleContentProperty();
      if (scprop != null) {
         String fieldName = this.pickUniqueFieldName(scprop.getGetterName().getSimpleName(), seenFieldNames);
         JavaTypeName jType = scprop.getTypeName().getJavaName();
         this.addJavaBeanProperty(fieldName, jType.toString(), scprop.getGetterName().getSimpleName(), scprop.getSetterName().getSimpleName(), scprop.getCtorParamIndex(), ctorBuilder, (String)null);
      }

      GenericXmlProperty gxprop = scratch.getAnyElementProperty();
      JavaTypeName jType;
      String fieldName;
      if (gxprop != null) {
         fieldName = this.pickUniqueFieldName(gxprop.getGetterName().getSimpleName(), seenFieldNames);
         jType = gxprop.getTypeName().getJavaName();
         this.addJavaBeanProperty(fieldName, jType.toString(), gxprop.getGetterName().getSimpleName(), gxprop.getSetterName().getSimpleName(), gxprop.getCtorParamIndex(), ctorBuilder, (String)null);
      }

      gxprop = scratch.getAnyAttributeProperty();
      if (gxprop != null) {
         fieldName = this.pickUniqueFieldName(gxprop.getGetterName().getSimpleName(), seenFieldNames);
         jType = gxprop.getTypeName().getJavaName();
         this.addJavaBeanProperty(fieldName, jType.toString(), gxprop.getGetterName().getSimpleName(), gxprop.getSetterName().getSimpleName(), gxprop.getCtorParamIndex(), ctorBuilder, (String)null);
      }

      Collection props = scratch.getQNameProperties();
      List childPropList = new LinkedList(props);
      if (!scratch.getBaseProperties().isEmpty()) {
         List parentPropList = new LinkedList(scratch.getBaseProperties());
         this.removeBaseProperties(childPropList, parentPropList);
      }

      Map fieldNames = new HashMap();
      Iterator i = props.iterator();

      QNameProperty prop;
      while(i.hasNext()) {
         prop = (QNameProperty)i.next();
         fieldNames.put(prop, this.pickUniqueFieldName(prop.getGetterName().getSimpleName(), seenFieldNames));
      }

      i = props.iterator();

      while(i.hasNext()) {
         prop = (QNameProperty)i.next();
         JavaTypeName jType = prop.getTypeName().getJavaName();
         if (prop.getCollectionClass() != null) {
            jType = prop.getCollectionClass();
         }

         String fieldName = (String)fieldNames.get(prop);
         if (prop.hasIssetter()) {
            this.addJavaBeanProperty(fieldName, jType.toString(), prop.getGetterName().getSimpleName(), prop.getSetterName().getSimpleName(), prop.getCtorParamIndex(), ctorBuilder, prop.getIssetterName().getSimpleName());
         } else {
            this.addJavaBeanProperty(fieldName, jType.toString(), prop, ctorBuilder, childPropList);
         }
      }

      ctorBuilder.printConstructor(this.mJoust);
      this.mJoust.endClassOrInterface();
   }

   private void removeBaseProperties(List childProps, List parentProps) {
      Iterator parentIter = parentProps.iterator();

      while(parentIter.hasNext()) {
         QNameProperty parentProp = (QNameProperty)parentIter.next();
         Iterator childIter = childProps.iterator();

         while(childIter.hasNext()) {
            QNameProperty childProp = (QNameProperty)childIter.next();
            if (childProp.getQName().equals(parentProp.getQName())) {
               childIter.remove();
            }
         }
      }

   }

   private void addJavaBeanProperty(String name, String type, String getter, String setter, int ctorPos, ConstructorBuilder ctorBuilder, String isSetter) throws IOException {
      Variable propertyField = this.mJoust.writeField(2, type, name, (Expression)null);
      this.mJoust.startMethod(1, type, getter, (String[])null, (String[])null, (String[])null);
      this.mJoust.writeReturnStatement(propertyField);
      this.mJoust.endMethodOrConstructor();
      if (isSetter != null) {
         this.mJoust.startMethod(1, type, isSetter, (String[])null, (String[])null, (String[])null);
         this.mJoust.writeReturnStatement(propertyField);
         this.mJoust.endMethodOrConstructor();
      }

      if (ctorPos == -1) {
         Variable[] params = this.mJoust.startMethod(1, "void", setter, new String[]{type}, new String[]{name}, (String[])null);
         this.mJoust.writeAssignmentStatement(propertyField, params[0]);
         this.mJoust.endMethodOrConstructor();
      } else {
         ctorBuilder.addParameter(propertyField, name, type, ctorPos);
      }

   }

   private void addJavaBeanProperty(String name, String type, QNameProperty prop, ConstructorBuilder ctorBuilder, List childProps) throws IOException {
      String getter = prop.getGetterName().getSimpleName();
      String setter = prop.getSetterName().getSimpleName();
      int ctorPos = prop.getCtorParamIndex();
      Variable propertyField;
      if (childProps != null && childProps.contains(prop)) {
         propertyField = this.mJoust.writeField(2, type, name, (Expression)null);
         this.mJoust.startMethod(1, type, getter, (String[])null, (String[])null, (String[])null);
         if (prop.getDefault() != null && !this.isPrimitive(type)) {
            String defaultValue = null;
            if (prop.isAttribute() && NMTOKENS.equals(prop.getTypeName().getXmlName().getQName())) {
               defaultValue = "new " + type + "{\"" + prop.getDefault().replaceAll(" ", "\",\"") + "\"}";
            } else if (this.scratchForXmlName(prop.getTypeName().getXmlName()) != null && this.scratchForXmlName(prop.getTypeName().getXmlName()).getCategory() == 3) {
               defaultValue = type + ".fromString(\"" + prop.getDefault() + "\")";
            } else {
               defaultValue = "new " + type + "(\"" + prop.getDefault() + "\")";
            }

            this.mJoust.writeStatement("if (" + name + " == null) return " + this.mJoust.getExpressionFactory().createVerbatim(defaultValue).getMemento().toString());
            this.mJoust.writeStatement("else return " + name);
         } else {
            this.mJoust.writeReturnStatement(propertyField);
         }

         this.mJoust.endMethodOrConstructor();
         if (ctorPos == -1 || this.ALWAYS_GENERATE_SETTER) {
            Variable[] params = this.mJoust.startMethod(1, "void", setter, new String[]{type}, new String[]{name}, (String[])null);
            this.mJoust.writeAssignmentStatement(propertyField, params[0]);
            this.mJoust.endMethodOrConstructor();
         }
      }

      if (ctorPos != -1) {
         propertyField = this.mJoust.getNewVariable(name);
         ctorBuilder.addParameter(propertyField, name, type, ctorPos);
         if (childProps != null && !childProps.contains(prop)) {
            ctorBuilder.addBaseParameter(propertyField, ctorPos);
         }
      }

   }

   private boolean isPrimitive(String type) {
      for(int i = 0; i < PRIMITIVE_TYPES.length; ++i) {
         if (PRIMITIVE_TYPES[i].equals(type)) {
            return true;
         }
      }

      return false;
   }

   private void printJavaEnum(Scratch scratch, String baseJavaName) throws IOException {
      Set seenFieldNames = new HashSet();
      XmlAnySimpleType[] enumValues = scratch.getSchemaType().getEnumerationValues();
      JaxrpcEnumType enumType = (JaxrpcEnumType)scratch.getBindingType();
      JavaTypeName baseType = enumType.getBaseTypeName().getJavaName();
      EnumerationPrintHelper enumHelper = new EnumerationPrintHelper(baseType, this.mJoust.getExpressionFactory(), scratch.getSchemaType());
      boolean useArrays = enumHelper.isArray() || enumHelper.isBinary();
      if (useArrays) {
         this.mJoust.writeImportStatement("java.util.Arrays");
      }

      this.mJoust.writeImportStatement("java.util.HashMap");
      this.mJoust.writeImportStatement("java.util.Map");
      if (enumHelper.isArray()) {
         this.mJoust.writeImportStatement("java.util.StringTokenizer");
      }

      this.mJoust.writeImportStatement("com.bea.xbean.util.XsTypeConverter");
      this.mJoust.startClass(1, baseJavaName, baseJavaName == null ? IMPLEMENTED_INTERFACES : null);
      boolean matchOk = true;
      String[] fieldNames = new String[enumValues.length];
      String instanceVarName = "value";
      String instanceMapName = "map";
      boolean isQName = enumValues.length > 0 && enumValues[0] instanceof XmlQName;

      int i;
      String name;
      for(i = 0; i < enumValues.length; ++i) {
         name = NameUtil.lowerCamelCase(isQName ? ((XmlQName)enumValues[i]).getQNameValue().getLocalPart() : enumValues[i].getStringValue(), true, false);
         if (!NameUtil.isValidJavaIdentifier(name)) {
            matchOk = false;
            break;
         }

         if (seenFieldNames.contains(name)) {
            matchOk = false;
            break;
         }

         seenFieldNames.add(name);
         fieldNames[i] = name;
      }

      if (!matchOk) {
         for(i = 0; i < enumValues.length; ++i) {
            name = "value" + (i + 1);
            fieldNames[i] = name;
         }
      } else {
         while(seenFieldNames.contains(instanceVarName)) {
            instanceVarName = "x" + instanceVarName;
         }

         while(seenFieldNames.contains(instanceMapName)) {
            instanceMapName = "x" + instanceMapName;
         }
      }

      Variable instanceVar = this.mJoust.writeField(2, baseType.toString(), instanceVarName, (Expression)null);
      this.mJoust.writeField(10, "Map", instanceMapName, this.mJoust.getExpressionFactory().createVerbatim("new HashMap()"));
      Variable[] params = this.mJoust.startConstructor(4, new String[]{baseType.toString()}, new String[]{"value"}, (String[])null);
      this.mJoust.writeAssignmentStatement(instanceVar, params[0]);
      this.mJoust.endMethodOrConstructor();
      Variable[] constants = new Variable[enumValues.length];

      for(int i = 0; i < enumValues.length; ++i) {
         XmlAnySimpleType enumValue = enumValues[i];
         constants[i] = this.mJoust.writeField(25, baseType.toString(), "_" + fieldNames[i], enumHelper.getInitExpr(enumValue));
      }

      String shortClassName = scratch.getJavaName().getShortClassName();

      for(int i = 0; i < enumValues.length; ++i) {
         this.mJoust.writeField(25, shortClassName, fieldNames[i], this.mJoust.getExpressionFactory().createVerbatim("new " + shortClassName + "(_" + fieldNames[i] + ")"));
      }

      this.mJoust.startMethod(1, baseType.toString(), enumType.getGetValueMethod().getSimpleName(), (String[])null, (String[])null, (String[])null);
      this.mJoust.writeReturnStatement(instanceVar);
      this.mJoust.endMethodOrConstructor();
      this.mJoust.startMethod(9, shortClassName, enumType.getFromValueMethod().getSimpleName(), new String[]{baseType.toString()}, new String[]{"value"}, (String[])null);
      String valueVarName = "value";
      if (useArrays) {
         this.mJoust.writeStatement(shortClassName + " new" + valueVarName + " = new " + shortClassName + "(" + valueVarName + ")");
         valueVarName = "new" + valueVarName;
      }

      this.mJoust.writeStatement("if (" + instanceMapName + ".containsKey(" + (useArrays ? valueVarName : enumHelper.getObjectVersion("value")) + ")) return (" + shortClassName + ") " + instanceMapName + ".get(" + (useArrays ? valueVarName : enumHelper.getObjectVersion("value")) + ")");
      this.mJoust.writeStatement("else throw new IllegalArgumentException()");
      this.mJoust.endMethodOrConstructor();
      params = this.mJoust.startMethod(9, shortClassName, enumType.getFromStringMethod().getSimpleName(), new String[]{"String"}, new String[]{"value"}, (String[])null);
      String STRING_LIST;
      String fieldName;
      if (enumHelper.isArray()) {
         STRING_LIST = "parts";
         fieldName = "array";
         this.mJoust.writeStatement("String[] parts= com.bea.xbean.values.XmlListImpl.split_list(value)");
         this.mJoust.writeStatement(baseType.toString() + " " + "array" + " = new " + baseType.getArrayItemType(baseType.getArrayDepth()).toString() + "[" + "parts" + ".length]" + baseType.getArrayString(1));
         this.mJoust.writeStatement("for (int i = 0; i < array.length; i++) array[i] = " + enumHelper.getFromStringExpr(this.mJoust.getExpressionFactory().createVerbatim("parts[i]")).getMemento().toString());
         this.mJoust.writeReturnStatement(this.mJoust.getExpressionFactory().createVerbatim(enumType.getFromValueMethod().getSimpleName() + "(" + "array" + ")"));
      } else {
         this.mJoust.writeReturnStatement(this.mJoust.getExpressionFactory().createVerbatim(enumType.getFromValueMethod().getSimpleName() + "(" + enumHelper.getFromStringExpr(params[0]).getMemento().toString() + ")"));
      }

      this.mJoust.endMethodOrConstructor();
      this.mJoust.startMethod(1, "String", enumType.getToXMLMethod().getSimpleName(), (String[])null, (String[])null, (String[])null);
      if (enumHelper.isArray()) {
         STRING_LIST = "list";
         this.mJoust.writeStatement("StringBuffer list = new StringBuffer()");
         this.mJoust.writeStatement("for (int i = 0; i < " + instanceVarName + ".length; i++) " + "list" + ".append(" + enumHelper.getToXmlString(instanceVar, "i") + ").append(' ')");
         this.mJoust.writeReturnStatement(this.mJoust.getExpressionFactory().createVerbatim("list.toString()"));
      } else {
         this.mJoust.writeReturnStatement(enumHelper.getToXmlExpr(instanceVar));
      }

      this.mJoust.endMethodOrConstructor();
      this.mJoust.startMethod(1, "String", "toString", (String[])null, (String[])null, (String[])null);
      if (enumHelper.isArray()) {
         STRING_LIST = "list";
         this.mJoust.writeStatement("StringBuffer list = new StringBuffer()");
         this.mJoust.writeStatement("for (int i = 0; i < " + instanceVarName + ".length; i++) " + "list" + ".append(String.valueOf(" + instanceVarName + "[i])).append(' ')");
         this.mJoust.writeReturnStatement(this.mJoust.getExpressionFactory().createVerbatim("list.toString()"));
      } else {
         this.mJoust.writeReturnStatement(this.mJoust.getExpressionFactory().createVerbatim("String.valueOf(" + instanceVarName + ")"));
      }

      this.mJoust.endMethodOrConstructor();
      params = this.mJoust.startMethod(1, "boolean", "equals", new String[]{"Object"}, new String[]{"obj"}, (String[])null);
      this.mJoust.writeStatement("if (this == obj) return true");
      this.mJoust.writeStatement("if (!(obj instanceof " + shortClassName + ")) return false");
      this.mJoust.writeStatement("final " + shortClassName + " x = (" + shortClassName + ") obj");
      if (enumHelper.isArray() && enumHelper.isBinary()) {
         this.mJoust.writeStatement("if (x." + instanceVarName + ".length != " + instanceVarName + ".length) return false");
         this.mJoust.writeStatement("boolean b = true");
         this.mJoust.writeStatement("for (int i = 0; i < " + instanceVarName + ".length && b; i++) b &= Arrays.equals(x." + instanceVarName + "[i], " + instanceVarName + "[i])");
         this.mJoust.writeStatement("return b");
      } else if (enumHelper.isArray()) {
         this.mJoust.writeStatement("if (Arrays.equals(x." + instanceVarName + ", " + instanceVarName + ")) return true");
      } else {
         this.mJoust.writeStatement("if (" + enumHelper.getEquals("x." + instanceVarName, instanceVarName) + ") return true");
      }

      this.mJoust.writeReturnStatement(this.mJoust.getExpressionFactory().createBoolean(false));
      this.mJoust.endMethodOrConstructor();
      this.mJoust.startMethod(1, "int", "hashCode", (String[])null, (String[])null, (String[])null);
      if (enumHelper.isArray()) {
         this.mJoust.writeStatement("int val = 0;");
         this.mJoust.writeStatement("for (int i = 0; i < " + instanceVarName + ".length; i++) { val *= 19; val += " + enumHelper.getHashCode(instanceVarName + "[i]").getMemento().toString() + "; }");
         this.mJoust.writeStatement("return val");
      } else {
         this.mJoust.writeReturnStatement(enumHelper.getHashCode(instanceVarName));
      }

      this.mJoust.endMethodOrConstructor();
      this.mJoust.startStaticInitializer();

      for(int i = 0; i < fieldNames.length; ++i) {
         fieldName = fieldNames[i];
         if (useArrays) {
            this.mJoust.writeStatement(instanceMapName + ".put(" + fieldName + ", " + fieldName + ")");
         } else {
            this.mJoust.writeStatement(instanceMapName + ".put(" + enumHelper.getObjectVersion("_" + fieldName) + ", " + fieldName + ")");
         }
      }

      this.mJoust.endMethodOrConstructor();
      this.mJoust.endClassOrInterface();
   }

   private String pickUniqueFieldName(String getter, Set seenNames) {
      String baseName;
      if (getter.length() > 3 && getter.startsWith("get")) {
         baseName = Character.toLowerCase(getter.charAt(3)) + getter.substring(4);
      } else {
         baseName = "field";
      }

      String fieldName = baseName;
      if (!NameUtil.isValidJavaIdentifier(baseName)) {
         fieldName = "_" + baseName;
      }

      for(int i = 1; seenNames.contains(fieldName); ++i) {
         fieldName = baseName + i;
      }

      seenNames.add(fieldName);
      return fieldName;
   }

   public static void main(String[] schemas) {
      try {
         File[] schemaFiles = new File[schemas.length];

         for(int i = 0; i < schemas.length; ++i) {
            schemaFiles[i] = new File(schemas[i]);
         }

         XmlObject[] xsds = new XmlObject[schemas.length];

         for(int i = 0; i < xsds.length; ++i) {
            xsds[i] = Factory.parse(new File(schemas[i]));
         }

         SchemaTypeLoader soapencLoader = SoapEncSchemaTypeSystem.get();
         SchemaTypeLoader xsdLoader = XmlBeans.getBuiltinTypeSystem();
         XmlOptions xo = new XmlOptions();
         xo.setCompileNoAnnotations();
         SchemaTypeSystem sts = XmlBeans.compileXsd(xsds, XmlBeans.typeLoaderUnion(new SchemaTypeLoader[]{xsdLoader, soapencLoader}), xo);
         Schema2Java s2j = new Schema2Java(sts);
         s2j.setVerbose(true);
         s2j.setJaxRpcRules(true);
         TylarWriter tw = new DebugTylarWriter();
         s2j.bind(tw);
         tw.close();
      } catch (Exception var9) {
         var9.printStackTrace();
      }

      System.err.flush();
      System.out.flush();
   }

   public void setBindingConfig(ConfigDocument.Config[] cdocs) {
      if (cdocs != null && cdocs.length != 0) {
         File[] javaFiles = new File[0];
         File[] classpath = new File[0];
         this.config = BindingConfigImpl.forConfigDocuments(cdocs, javaFiles, classpath);
      }

   }

   static {
      WILDCARD_ELEMENT_MAPPING = SOAPELEMENT_CLASSNAME;
      WILDCARD_ATTRIBUTE_MAPPING = SOAPELEMENT_CLASSNAME;
      NMTOKENS = new QName("http://www.w3.org/2001/XMLSchema", "NMTOKENS");
      BLACKPACKAGE = null;
      ISPostJdk8 = !System.getProperty("java.version").startsWith("1.");
      if (ISPostJdk8) {
         InputStream is = Schema2Java.class.getClassLoader().getResourceAsStream("BlackList");
         BufferedReader reader = new BufferedReader(new InputStreamReader(is));
         String line = null;
         BLACKPACKAGE = new HashSet();

         try {
            while((line = reader.readLine()) != null) {
               BLACKPACKAGE.add(line);
            }

            reader.close();
            is.close();
         } catch (IOException var4) {
            BindingCompiler.DEFAULT.logError(var4);
         }
      }

      arrayType = new QName("http://schemas.xmlsoap.org/soap/encoding/", "arrayType");
   }

   private class ConstructorBuilder {
      Map orderedFields = new TreeMap();
      Map orderedBaseFields = new TreeMap();

      ConstructorBuilder() {
      }

      public void addParameter(Variable field, String name, String typeName, int pos) {
         this.orderedFields.put(new Integer(pos), new FieldStructure(field, name, typeName, pos));
      }

      public void addBaseParameter(Variable field, int pos) {
         this.orderedBaseFields.put(new Integer(pos), field);
      }

      public void printConstructor(JavaOutputStream joust) throws IOException {
         if (!this.orderedFields.isEmpty()) {
            Variable[] fields = new Variable[this.orderedFields.size()];
            String[] paramNames = new String[this.orderedFields.size()];
            String[] paramTypes = new String[this.orderedFields.size()];
            int[] ctorIndexes = new int[this.orderedFields.size()];
            int i = 0;

            for(Iterator it = this.orderedFields.values().iterator(); it.hasNext(); ++i) {
               FieldStructure field = (FieldStructure)it.next();
               fields[i] = field.variable;
               paramNames[i] = field.name;
               paramTypes[i] = field.typeName;
               ctorIndexes[i] = field.ctorIndex;
            }

            joust.startConstructor(1, (String[])null, (String[])null, (String[])null);
            joust.endMethodOrConstructor();
            Variable[] params = joust.startConstructor(1, paramTypes, paramNames, (String[])null);
            i = 0;
            int size = this.orderedBaseFields.size();
            if (size > 0) {
               StringBuffer sb;
               for(sb = new StringBuffer("super("); i < size; ++i) {
                  sb.append(paramNames[i]);
                  if (i == size - 1) {
                     sb.append(")");
                  } else {
                     sb.append(", ");
                  }
               }

               joust.writeStatement(sb.toString());
            }

            while(i < params.length) {
               joust.writeAssignmentStatement(fields[i], params[i]);
               ++i;
            }

            joust.endMethodOrConstructor();
         }
      }

      private class FieldStructure {
         Variable variable;
         String name;
         String typeName;
         int ctorIndex;

         FieldStructure(Variable v, String n, String t, int pos) {
            this.variable = v;
            this.name = n;
            this.typeName = t;
            this.ctorIndex = pos;
         }
      }
   }

   private class TopLevelClassNameIterator implements Iterator {
      private final Iterator si;
      private Scratch next;

      private TopLevelClassNameIterator() {
         this.si = Schema2Java.this.scratchIterator();
         this.next = this.nextStructure();
      }

      public boolean hasNext() {
         return this.next != null;
      }

      public Object next() {
         String result = this.next.getJavaName().toString();
         this.next = this.nextStructure();
         return result;
      }

      private Scratch nextStructure() {
         while(true) {
            if (this.si.hasNext()) {
               Scratch scratch = (Scratch)this.si.next();
               if (scratch.getCategory() != 2 && scratch.getCategory() != 3 && scratch.getCategory() != 10) {
                  continue;
               }

               return scratch;
            }

            return null;
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      // $FF: synthetic method
      TopLevelClassNameIterator(Object x1) {
         this();
      }
   }

   private static class ScratchComparator implements Comparator {
      static final ScratchComparator INSTANCE = new ScratchComparator();

      public int compare(Object o1, Object o2) {
         String name1 = ((Scratch)o1).getXmlName().toString();
         String name2 = ((Scratch)o2).getXmlName().toString();
         return name1.compareTo(name2);
      }
   }

   private static class Scratch {
      private BindingType bindingType;
      private SchemaType schemaType;
      private JavaTypeName javaName;
      private XmlTypeName xmlName;
      private int category;
      private boolean exception;
      private QName preferredQName;
      private String preferredPropertyName;
      private XmlTypeName asIf;
      private boolean isStructureResolved;
      Integer[] ctorDefaults;
      private int soapRank = -1;
      private boolean isMultiplicityChanged = false;
      private Collection baseProperties;
      public static final int ATOMIC_TYPE = 1;
      public static final int STRUCT_TYPE = 2;
      public static final int ENUM_TYPE = 3;
      public static final int LIST_TYPE = 4;
      public static final int LITERALARRAY_TYPE = 5;
      public static final int SOAPARRAY = 7;
      public static final int ELEMENT = 8;
      public static final int ATTRIBUTE = 9;
      public static final int FAULT_HOLDER = 10;
      public static final int SOAPENC_PRIMITIVE = 11;
      public static final int LIST_OF_ENUM_TYPE = 12;
      public static final int LIST_OF_UNION_TYPE = 13;

      Scratch(SchemaType schemaType, XmlTypeName xmlName, int category) {
         this.baseProperties = Collections.EMPTY_LIST;
         this.schemaType = schemaType;
         this.xmlName = xmlName;
         this.category = category;
      }

      public int getCategory() {
         return this.category;
      }

      public JavaTypeName getJavaName() {
         return this.javaName;
      }

      public void setJavaName(JavaTypeName javaName) {
         this.javaName = javaName;
      }

      public BindingType getBindingType() {
         return this.bindingType;
      }

      public void setBindingType(BindingType bindingType) {
         this.bindingType = bindingType;
      }

      public SchemaType getSchemaType() {
         return this.schemaType;
      }

      public XmlTypeName getXmlName() {
         return this.xmlName;
      }

      public void setXmlName(XmlTypeName xmlName) {
         this.xmlName = xmlName;
      }

      public boolean isException() {
         return this.exception;
      }

      public void setException(boolean exception) {
         this.exception = exception;
      }

      public QName getPreferredName() {
         return this.preferredQName;
      }

      public void setPreferredName(QName name) {
         this.preferredQName = name;
      }

      public String getPreferredPropertyName() {
         return this.preferredPropertyName;
      }

      public void setPreferredPropertyName(String name) {
         this.preferredPropertyName = name;
      }

      public XmlTypeName getAsIf() {
         return this.asIf;
      }

      public void setAsIf(XmlTypeName xmlName) {
         this.asIf = xmlName;
      }

      public int getSoapRank() {
         return this.soapRank;
      }

      public void setSoapRank(int rank) {
         this.soapRank = rank;
      }

      public Collection getBaseProperties() {
         return this.baseProperties;
      }

      public void setBaseProperties(Collection props) {
         this.baseProperties = props;
      }

      public Integer[] getCtorDefaults() {
         return this.ctorDefaults;
      }

      public void setCtorDefaults(Integer[] array) {
         this.ctorDefaults = array;
      }

      public void addQNameProperty(QNameProperty prop) {
         if (this.bindingType instanceof ByNameBean) {
            ((ByNameBean)this.bindingType).addProperty(prop);
         } else {
            if (!(this.bindingType instanceof SimpleContentBean)) {
               throw new IllegalStateException();
            }

            ((SimpleContentBean)this.bindingType).addProperty(prop);
         }

      }

      public Collection getQNameProperties() {
         if (this.bindingType instanceof ByNameBean) {
            return ((ByNameBean)this.bindingType).getProperties();
         } else if (this.bindingType instanceof SimpleContentBean) {
            return ((SimpleContentBean)this.bindingType).getAttributeProperties();
         } else {
            throw new IllegalStateException();
         }
      }

      public void setSimpleContentProperty(SimpleContentProperty prop) {
         if (this.bindingType instanceof SimpleContentBean) {
            ((SimpleContentBean)this.bindingType).setSimpleContentProperty(prop);
         } else {
            throw new IllegalStateException();
         }
      }

      public SimpleContentProperty getSimpleContentProperty() {
         return this.bindingType instanceof SimpleContentBean ? ((SimpleContentBean)this.bindingType).getSimpleContentProperty() : null;
      }

      public void setAnyAttributeProperty(GenericXmlProperty prop) {
         if (this.bindingType instanceof ByNameBean) {
            ((ByNameBean)this.bindingType).setAnyAttributeProperty(prop);
         } else {
            if (!(this.bindingType instanceof SimpleContentBean)) {
               throw new IllegalStateException();
            }

            ((SimpleContentBean)this.bindingType).setAnyAttributeProperty(prop);
         }

      }

      public GenericXmlProperty getAnyAttributeProperty() {
         if (this.bindingType instanceof ByNameBean) {
            return ((ByNameBean)this.bindingType).getAnyAttributeProperty();
         } else if (this.bindingType instanceof SimpleContentBean) {
            return ((SimpleContentBean)this.bindingType).getAnyAttributeProperty();
         } else {
            throw new IllegalStateException();
         }
      }

      public void setAnyElementProperty(GenericXmlProperty prop) {
         if (this.bindingType instanceof ByNameBean) {
            ((ByNameBean)this.bindingType).setAnyElementProperty(prop);
         } else {
            throw new IllegalStateException();
         }
      }

      public GenericXmlProperty getAnyElementProperty() {
         if (this.bindingType instanceof ByNameBean) {
            return ((ByNameBean)this.bindingType).getAnyElementProperty();
         } else if (this.bindingType instanceof SimpleContentBean) {
            return null;
         } else {
            throw new IllegalStateException();
         }
      }

      public boolean isStructureResolved() {
         return this.isStructureResolved;
      }

      public void setStructureResolved(boolean isStructureResolved) {
         this.isStructureResolved = isStructureResolved;
      }

      public boolean isMultiplicityChanged() {
         return this.isMultiplicityChanged;
      }

      public void setMultiplicityChanged(boolean isMultiplicityChanged) {
         this.isMultiplicityChanged = isMultiplicityChanged;
      }

      public String toString() {
         return this.getJavaName() + "<->" + this.getXmlName();
      }
   }
}
