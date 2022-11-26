package com.bea.xbean.schema;

import com.bea.xbean.common.NameUtil;
import com.bea.xbean.common.QNameHelper;
import com.bea.xbean.common.XBeanDebug;
import com.bea.xbean.regex.RegularExpression;
import com.bea.xbean.util.FilerImpl;
import com.bea.xbean.util.HexBin;
import com.bea.xbean.values.XmlObjectBase;
import com.bea.xbean.xb.xsdschema.AttributeGroupDocument;
import com.bea.xbean.xb.xsdschema.GroupDocument;
import com.bea.xml.Filer;
import com.bea.xml.QNameSet;
import com.bea.xml.ResourceLoader;
import com.bea.xml.SchemaAnnotation;
import com.bea.xml.SchemaAttributeGroup;
import com.bea.xml.SchemaAttributeModel;
import com.bea.xml.SchemaComponent;
import com.bea.xml.SchemaGlobalAttribute;
import com.bea.xml.SchemaGlobalElement;
import com.bea.xml.SchemaIdentityConstraint;
import com.bea.xml.SchemaLocalAttribute;
import com.bea.xml.SchemaLocalElement;
import com.bea.xml.SchemaModelGroup;
import com.bea.xml.SchemaParticle;
import com.bea.xml.SchemaProperty;
import com.bea.xml.SchemaStringEnumEntry;
import com.bea.xml.SchemaType;
import com.bea.xml.SchemaTypeLoader;
import com.bea.xml.SchemaTypeLoaderException;
import com.bea.xml.SchemaTypeSystem;
import com.bea.xml.SimpleValue;
import com.bea.xml.SystemProperties;
import com.bea.xml.XmlAnySimpleType;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.soap.SOAPArrayType;
import com.bea.xml.soap.SchemaWSDLArrayType;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.xml.namespace.QName;
import repackage.Repackager;

public class SchemaTypeSystemImpl extends SchemaTypeLoaderBase implements SchemaTypeSystem {
   public static final int DATA_BABE = -629491010;
   public static final int MAJOR_VERSION = 2;
   public static final int MINOR_VERSION = 24;
   public static final int RELEASE_NUMBER = 0;
   public static final int FILETYPE_SCHEMAINDEX = 1;
   public static final int FILETYPE_SCHEMATYPE = 2;
   public static final int FILETYPE_SCHEMAELEMENT = 3;
   public static final int FILETYPE_SCHEMAATTRIBUTE = 4;
   public static final int FILETYPE_SCHEMAPOINTER = 5;
   public static final int FILETYPE_SCHEMAMODELGROUP = 6;
   public static final int FILETYPE_SCHEMAATTRIBUTEGROUP = 7;
   public static final int FILETYPE_SCHEMAIDENTITYCONSTRAINT = 8;
   public static final int FLAG_PART_SKIPPABLE = 1;
   public static final int FLAG_PART_FIXED = 4;
   public static final int FLAG_PART_NILLABLE = 8;
   public static final int FLAG_PART_BLOCKEXT = 16;
   public static final int FLAG_PART_BLOCKREST = 32;
   public static final int FLAG_PART_BLOCKSUBST = 64;
   public static final int FLAG_PART_ABSTRACT = 128;
   public static final int FLAG_PART_FINALEXT = 256;
   public static final int FLAG_PART_FINALREST = 512;
   public static final int FLAG_PROP_ISATTR = 1;
   public static final int FLAG_PROP_JAVASINGLETON = 2;
   public static final int FLAG_PROP_JAVAOPTIONAL = 4;
   public static final int FLAG_PROP_JAVAARRAY = 8;
   public static final int FIELD_NONE = 0;
   public static final int FIELD_GLOBAL = 1;
   public static final int FIELD_LOCALATTR = 2;
   public static final int FIELD_LOCALELT = 3;
   static final int FLAG_SIMPLE_TYPE = 1;
   static final int FLAG_DOCUMENT_TYPE = 2;
   static final int FLAG_ORDERED = 4;
   static final int FLAG_BOUNDED = 8;
   static final int FLAG_FINITE = 16;
   static final int FLAG_NUMERIC = 32;
   static final int FLAG_STRINGENUM = 64;
   static final int FLAG_UNION_OF_LISTS = 128;
   static final int FLAG_HAS_PATTERN = 256;
   static final int FLAG_ORDER_SENSITIVE = 512;
   static final int FLAG_TOTAL_ORDER = 1024;
   static final int FLAG_COMPILED = 2048;
   static final int FLAG_BLOCK_EXT = 4096;
   static final int FLAG_BLOCK_REST = 8192;
   static final int FLAG_FINAL_EXT = 16384;
   static final int FLAG_FINAL_REST = 32768;
   static final int FLAG_FINAL_UNION = 65536;
   static final int FLAG_FINAL_LIST = 131072;
   static final int FLAG_ABSTRACT = 262144;
   static final int FLAG_ATTRIBUTE_TYPE = 524288;
   public static String METADATA_PACKAGE_GEN;
   private static final String HOLDER_TEMPLATE_CLASS = "com.bea.xbean.schema.TypeSystemHolder";
   private static final String HOLDER_TEMPLATE_CLASSFILE = "TypeSystemHolder.template";
   private static final String[] HOLDER_TEMPLATE_NAMES;
   private static final int CONSTANT_UTF8 = 1;
   private static final int CONSTANT_UNICODE = 2;
   private static final int CONSTANT_INTEGER = 3;
   private static final int CONSTANT_FLOAT = 4;
   private static final int CONSTANT_LONG = 5;
   private static final int CONSTANT_DOUBLE = 6;
   private static final int CONSTANT_CLASS = 7;
   private static final int CONSTANT_STRING = 8;
   private static final int CONSTANT_FIELD = 9;
   private static final int CONSTANT_METHOD = 10;
   private static final int CONSTANT_INTERFACEMETHOD = 11;
   private static final int CONSTANT_NAMEANDTYPE = 12;
   private static final int MAX_UNSIGNED_SHORT = 65535;
   private static Random _random;
   private static byte[] _mask;
   private String _name;
   private String _basePackage;
   private boolean _incomplete = false;
   private ClassLoader _classloader;
   private ResourceLoader _resourceLoader;
   SchemaTypeLoader _linker;
   private HandlePool _localHandles;
   private Filer _filer;
   private List _annotations;
   private Map _containers = new HashMap();
   private SchemaDependencies _deps;
   private List _redefinedModelGroups;
   private List _redefinedAttributeGroups;
   private List _redefinedGlobalTypes;
   private Map _globalElements;
   private Map _globalAttributes;
   private Map _modelGroups;
   private Map _attributeGroups;
   private Map _globalTypes;
   private Map _documentTypes;
   private Map _attributeTypes;
   private Map _identityConstraints;
   private Map _typeRefsByClassname;
   private Set _namespaces;
   private static final SchemaType[] EMPTY_ST_ARRAY;
   private static final SchemaGlobalElement[] EMPTY_GE_ARRAY;
   private static final SchemaGlobalAttribute[] EMPTY_GA_ARRAY;
   private static final SchemaModelGroup[] EMPTY_MG_ARRAY;
   private static final SchemaAttributeGroup[] EMPTY_AG_ARRAY;
   private static final SchemaIdentityConstraint[] EMPTY_IC_ARRAY;
   private static final SchemaAnnotation[] EMPTY_ANN_ARRAY;
   static final byte[] SINGLE_ZERO_BYTE;
   private final Map _resolvedHandles;
   private boolean _allNonGroupHandlesResolved;

   private static String nameToPathString(String nameForSystem) {
      nameForSystem = nameForSystem.replace('.', '/');
      if (!nameForSystem.endsWith("/") && nameForSystem.length() > 0) {
         nameForSystem = nameForSystem + "/";
      }

      return nameForSystem;
   }

   public SchemaTypeSystemImpl() {
      this._identityConstraints = Collections.EMPTY_MAP;
      this._typeRefsByClassname = new HashMap();
      this._resolvedHandles = new HashMap();
      this._allNonGroupHandlesResolved = false;
   }

   public SchemaTypeSystemImpl(Class indexclass) {
      this._identityConstraints = Collections.EMPTY_MAP;
      this._typeRefsByClassname = new HashMap();
      this._resolvedHandles = new HashMap();
      this._allNonGroupHandlesResolved = false;
      String fullname = indexclass.getName();
      this._name = fullname.substring(0, fullname.lastIndexOf(46));
      XBeanDebug.trace(1, "Loading type system " + this._name, 1);
      this._basePackage = nameToPathString(this._name);
      this._classloader = indexclass.getClassLoader();
      this._linker = SchemaTypeLoaderImpl.build((SchemaTypeLoader[])null, (ResourceLoader)null, this._classloader);
      this._resourceLoader = new ClassLoaderResourceLoader(this._classloader);

      try {
         this.initFromHeader();
      } catch (RuntimeException var4) {
         XBeanDebug.logException(var4);
         throw var4;
      } catch (Error var5) {
         XBeanDebug.logException(var5);
         throw var5;
      }

      XBeanDebug.trace(1, "Finished loading type system " + this._name, -1);
   }

   public static boolean fileContainsTypeSystem(File file, String name) {
      String indexname = nameToPathString(name) + "index.xsb";
      if (file.isDirectory()) {
         return (new File(file, indexname)).isFile();
      } else {
         ZipFile zipfile = null;

         boolean var5;
         try {
            zipfile = new ZipFile(file);
            ZipEntry entry = zipfile.getEntry(indexname);
            var5 = entry != null && !entry.isDirectory();
         } catch (IOException var14) {
            XBeanDebug.log("Problem loading SchemaTypeSystem, zipfilename " + file);
            XBeanDebug.logException(var14);
            throw new SchemaTypeLoaderException(var14.getMessage(), name, "index", 9);
         } finally {
            if (zipfile != null) {
               try {
                  zipfile.close();
               } catch (IOException var13) {
               }
            }

         }

         return var5;
      }
   }

   public static SchemaTypeSystemImpl forName(String name, ClassLoader loader) {
      try {
         Class c = Class.forName(name + "." + "TypeSystemHolder", true, loader);
         return (SchemaTypeSystemImpl)c.getField("typeSystem").get((Object)null);
      } catch (Exception var3) {
         return null;
      }
   }

   public SchemaTypeSystemImpl(ResourceLoader resourceLoader, String name, SchemaTypeLoader linker) {
      this._identityConstraints = Collections.EMPTY_MAP;
      this._typeRefsByClassname = new HashMap();
      this._resolvedHandles = new HashMap();
      this._allNonGroupHandlesResolved = false;
      this._name = name;
      this._basePackage = nameToPathString(this._name);
      this._linker = linker;
      this._resourceLoader = resourceLoader;

      try {
         this.initFromHeader();
      } catch (RuntimeException var5) {
         XBeanDebug.logException(var5);
         throw var5;
      } catch (Error var6) {
         XBeanDebug.logException(var6);
         throw var6;
      }
   }

   private void initFromHeader() {
      XBeanDebug.trace(1, "Reading unresolved handles for type system " + this._name, 0);
      XsbReader reader = null;

      try {
         reader = new XsbReader("index", 1);
         this._localHandles = new HandlePool();
         reader.readHandlePool(this._localHandles);
         this._globalElements = reader.readQNameRefMap();
         this._globalAttributes = reader.readQNameRefMap();
         this._modelGroups = reader.readQNameRefMap();
         this._attributeGroups = reader.readQNameRefMap();
         this._identityConstraints = reader.readQNameRefMap();
         this._globalTypes = reader.readQNameRefMap();
         this._documentTypes = reader.readQNameRefMap();
         this._attributeTypes = reader.readQNameRefMap();
         this._typeRefsByClassname = reader.readClassnameRefMap();
         this._namespaces = reader.readNamespaces();
         List typeNames = new ArrayList();
         List modelGroupNames = new ArrayList();
         List attributeGroupNames = new ArrayList();
         if (reader.atLeast(2, 15, 0)) {
            this._redefinedGlobalTypes = reader.readQNameRefMapAsList(typeNames);
            this._redefinedModelGroups = reader.readQNameRefMapAsList(modelGroupNames);
            this._redefinedAttributeGroups = reader.readQNameRefMapAsList(attributeGroupNames);
         }

         if (reader.atLeast(2, 19, 0)) {
            this._annotations = reader.readAnnotations();
         }

         this.buildContainers(typeNames, modelGroupNames, attributeGroupNames);
      } finally {
         if (reader != null) {
            reader.readEnd();
         }

      }

   }

   void saveIndex() {
      String handle = "index";
      XsbReader saver = new XsbReader(handle);
      saver.writeIndexData();
      saver.writeRealHeader(handle, 1);
      saver.writeIndexData();
      saver.writeEnd();
   }

   void savePointers() {
      this.savePointersForComponents(this.globalElements(), "schema" + METADATA_PACKAGE_GEN + "/element/");
      this.savePointersForComponents(this.globalAttributes(), "schema" + METADATA_PACKAGE_GEN + "/attribute/");
      this.savePointersForComponents(this.modelGroups(), "schema" + METADATA_PACKAGE_GEN + "/modelgroup/");
      this.savePointersForComponents(this.attributeGroups(), "schema" + METADATA_PACKAGE_GEN + "/attributegroup/");
      this.savePointersForComponents(this.globalTypes(), "schema" + METADATA_PACKAGE_GEN + "/type/");
      this.savePointersForComponents(this.identityConstraints(), "schema" + METADATA_PACKAGE_GEN + "/identityconstraint/");
      this.savePointersForNamespaces(this._namespaces, "schema" + METADATA_PACKAGE_GEN + "/namespace/");
      this.savePointersForClassnames(this._typeRefsByClassname.keySet(), "schema" + METADATA_PACKAGE_GEN + "/javaname/");
      this.savePointersForComponents(this.redefinedModelGroups(), "schema" + METADATA_PACKAGE_GEN + "/redefinedmodelgroup/");
      this.savePointersForComponents(this.redefinedAttributeGroups(), "schema" + METADATA_PACKAGE_GEN + "/redefinedattributegroup/");
      this.savePointersForComponents(this.redefinedGlobalTypes(), "schema" + METADATA_PACKAGE_GEN + "/redefinedtype/");
   }

   void savePointersForComponents(SchemaComponent[] components, String dir) {
      for(int i = 0; i < components.length; ++i) {
         this.savePointerFile(dir + QNameHelper.hexsafedir(components[i].getName()), this._name);
      }

   }

   void savePointersForClassnames(Set classnames, String dir) {
      Iterator i = classnames.iterator();

      while(i.hasNext()) {
         String classname = (String)i.next();
         this.savePointerFile(dir + classname.replace('.', '/'), this._name);
      }

   }

   void savePointersForNamespaces(Set namespaces, String dir) {
      Iterator i = namespaces.iterator();

      while(i.hasNext()) {
         String ns = (String)i.next();
         this.savePointerFile(dir + QNameHelper.hexsafedir(new QName(ns, "xmlns")), this._name);
      }

   }

   void savePointerFile(String filename, String name) {
      XsbReader saver = new XsbReader(filename);
      saver.writeString(name);
      saver.writeRealHeader(filename, 5);
      saver.writeString(name);
      saver.writeEnd();
   }

   void saveLoader() {
      String indexClassName = SchemaTypeCodePrinter.indexClassForSystem(this);
      String[] replace = makeClassStrings(indexClassName);

      assert replace.length == HOLDER_TEMPLATE_NAMES.length;

      InputStream is = null;
      OutputStream os = null;
      DataInputStream in = null;
      DataOutputStream out = null;
      Repackager repackager = null;
      if (this._filer instanceof FilerImpl) {
         repackager = ((FilerImpl)this._filer).getRepackager();
      }

      try {
         is = SchemaTypeSystemImpl.class.getResourceAsStream("TypeSystemHolder.template");
         if (is == null) {
            throw new SchemaTypeLoaderException("couldn't find resource: TypeSystemHolder.template", this._name, (String)null, 9);
         }

         in = new DataInputStream(is);
         os = this._filer.createBinaryFile(indexClassName.replace('.', '/') + ".class");
         out = new DataOutputStream(os);
         out.writeInt(in.readInt());
         out.writeShort(in.readUnsignedShort());
         out.writeShort(in.readUnsignedShort());
         int poolsize = in.readUnsignedShort();
         out.writeShort(poolsize);

         for(int i = 1; i < poolsize; ++i) {
            int tag = in.readUnsignedByte();
            out.writeByte(tag);
            switch (tag) {
               case 1:
                  String value = in.readUTF();
                  out.writeUTF(repackageConstant(value, replace, repackager));
                  break;
               case 2:
               default:
                  throw new RuntimeException("Unexpected constant type: " + tag);
               case 3:
               case 4:
                  out.writeInt(in.readInt());
                  break;
               case 5:
               case 6:
                  out.writeInt(in.readInt());
                  out.writeInt(in.readInt());
                  ++i;
                  break;
               case 7:
               case 8:
                  out.writeShort(in.readUnsignedShort());
                  break;
               case 9:
               case 10:
               case 11:
               case 12:
                  out.writeShort(in.readUnsignedShort());
                  out.writeShort(in.readUnsignedShort());
            }
         }

         try {
            while(true) {
               out.writeByte(in.readByte());
            }
         } catch (EOFException var25) {
         }
      } catch (IOException var26) {
      } finally {
         if (is != null) {
            try {
               is.close();
            } catch (Exception var24) {
            }
         }

         if (os != null) {
            try {
               os.close();
            } catch (Exception var23) {
            }
         }

      }

   }

   private static String repackageConstant(String value, String[] replace, Repackager repackager) {
      for(int i = 0; i < HOLDER_TEMPLATE_NAMES.length; ++i) {
         if (HOLDER_TEMPLATE_NAMES[i].equals(value)) {
            return replace[i];
         }
      }

      if (repackager != null) {
         return repackager.repackage(new StringBuffer(value)).toString();
      } else {
         return value;
      }
   }

   private static String[] makeClassStrings(String classname) {
      String[] result = new String[]{classname, classname.replace('.', '/'), null, null};
      result[2] = "L" + result[1] + ";";
      result[3] = "class$" + classname.replace('.', '$');
      return result;
   }

   private Map buildTypeRefsByClassname() {
      List allSeenTypes = new ArrayList();
      Map result = new LinkedHashMap();
      allSeenTypes.addAll(Arrays.asList(this.documentTypes()));
      allSeenTypes.addAll(Arrays.asList(this.attributeTypes()));
      allSeenTypes.addAll(Arrays.asList(this.globalTypes()));

      for(int i = 0; i < allSeenTypes.size(); ++i) {
         SchemaType gType = (SchemaType)allSeenTypes.get(i);
         String className = gType.getFullJavaName();
         if (className != null) {
            result.put(className.replace('$', '.'), gType.getRef());
         }

         allSeenTypes.addAll(Arrays.asList(gType.getAnonymousTypes()));
      }

      return result;
   }

   private Map buildTypeRefsByClassname(Map typesByClassname) {
      Map result = new LinkedHashMap();
      Iterator i = typesByClassname.keySet().iterator();

      while(i.hasNext()) {
         String className = (String)i.next();
         result.put(className, ((SchemaType)typesByClassname.get(className)).getRef());
      }

      return result;
   }

   private static Map buildComponentRefMap(SchemaComponent[] components) {
      Map result = new LinkedHashMap();

      for(int i = 0; i < components.length; ++i) {
         result.put(components[i].getName(), components[i].getComponentRef());
      }

      return result;
   }

   private static List buildComponentRefList(SchemaComponent[] components) {
      List result = new ArrayList();

      for(int i = 0; i < components.length; ++i) {
         result.add(components[i].getComponentRef());
      }

      return result;
   }

   private static Map buildDocumentMap(SchemaType[] types) {
      Map result = new LinkedHashMap();

      for(int i = 0; i < types.length; ++i) {
         result.put(types[i].getDocumentElementName(), types[i].getRef());
      }

      return result;
   }

   private static Map buildAttributeTypeMap(SchemaType[] types) {
      Map result = new LinkedHashMap();

      for(int i = 0; i < types.length; ++i) {
         result.put(types[i].getAttributeTypeAttributeName(), types[i].getRef());
      }

      return result;
   }

   private SchemaContainer getContainer(String namespace) {
      return (SchemaContainer)this._containers.get(namespace);
   }

   private void addContainer(String namespace) {
      SchemaContainer c = new SchemaContainer(namespace);
      c.setTypeSystem(this);
      this._containers.put(namespace, c);
   }

   private SchemaContainer getContainerNonNull(String namespace) {
      SchemaContainer result = this.getContainer(namespace);
      if (result == null) {
         this.addContainer(namespace);
         result = this.getContainer(namespace);
      }

      return result;
   }

   private void buildContainers(List redefTypeNames, List redefModelGroupNames, List redefAttributeGroupNames) {
      Iterator it = this._globalElements.entrySet().iterator();

      Map.Entry entry;
      String ns;
      while(it.hasNext()) {
         entry = (Map.Entry)it.next();
         ns = ((QName)entry.getKey()).getNamespaceURI();
         this.getContainerNonNull(ns).addGlobalElement((SchemaGlobalElement.Ref)entry.getValue());
      }

      it = this._globalAttributes.entrySet().iterator();

      while(it.hasNext()) {
         entry = (Map.Entry)it.next();
         ns = ((QName)entry.getKey()).getNamespaceURI();
         this.getContainerNonNull(ns).addGlobalAttribute((SchemaGlobalAttribute.Ref)entry.getValue());
      }

      it = this._modelGroups.entrySet().iterator();

      while(it.hasNext()) {
         entry = (Map.Entry)it.next();
         ns = ((QName)entry.getKey()).getNamespaceURI();
         this.getContainerNonNull(ns).addModelGroup((SchemaModelGroup.Ref)entry.getValue());
      }

      it = this._attributeGroups.entrySet().iterator();

      while(it.hasNext()) {
         entry = (Map.Entry)it.next();
         ns = ((QName)entry.getKey()).getNamespaceURI();
         this.getContainerNonNull(ns).addAttributeGroup((SchemaAttributeGroup.Ref)entry.getValue());
      }

      it = this._identityConstraints.entrySet().iterator();

      while(it.hasNext()) {
         entry = (Map.Entry)it.next();
         ns = ((QName)entry.getKey()).getNamespaceURI();
         this.getContainerNonNull(ns).addIdentityConstraint((SchemaIdentityConstraint.Ref)entry.getValue());
      }

      it = this._globalTypes.entrySet().iterator();

      while(it.hasNext()) {
         entry = (Map.Entry)it.next();
         ns = ((QName)entry.getKey()).getNamespaceURI();
         this.getContainerNonNull(ns).addGlobalType((SchemaType.Ref)entry.getValue());
      }

      it = this._documentTypes.entrySet().iterator();

      while(it.hasNext()) {
         entry = (Map.Entry)it.next();
         ns = ((QName)entry.getKey()).getNamespaceURI();
         this.getContainerNonNull(ns).addDocumentType((SchemaType.Ref)entry.getValue());
      }

      it = this._attributeTypes.entrySet().iterator();

      while(it.hasNext()) {
         entry = (Map.Entry)it.next();
         ns = ((QName)entry.getKey()).getNamespaceURI();
         this.getContainerNonNull(ns).addAttributeType((SchemaType.Ref)entry.getValue());
      }

      if (this._redefinedGlobalTypes != null && this._redefinedModelGroups != null && this._redefinedAttributeGroups != null) {
         assert this._redefinedGlobalTypes.size() == redefTypeNames.size();

         it = this._redefinedGlobalTypes.iterator();
         Iterator itname = redefTypeNames.iterator();

         while(it.hasNext()) {
            ns = ((QName)itname.next()).getNamespaceURI();
            this.getContainerNonNull(ns).addRedefinedType((SchemaType.Ref)it.next());
         }

         it = this._redefinedModelGroups.iterator();
         itname = redefModelGroupNames.iterator();

         while(it.hasNext()) {
            ns = ((QName)itname.next()).getNamespaceURI();
            this.getContainerNonNull(ns).addRedefinedModelGroup((SchemaModelGroup.Ref)it.next());
         }

         it = this._redefinedAttributeGroups.iterator();
         itname = redefAttributeGroupNames.iterator();

         while(it.hasNext()) {
            ns = ((QName)itname.next()).getNamespaceURI();
            this.getContainerNonNull(ns).addRedefinedAttributeGroup((SchemaAttributeGroup.Ref)it.next());
         }
      }

      if (this._annotations != null) {
         it = this._annotations.iterator();

         while(it.hasNext()) {
            SchemaAnnotation ann = (SchemaAnnotation)it.next();
            this.getContainerNonNull("").addAnnotation(ann);
         }
      }

      it = this._containers.values().iterator();

      while(it.hasNext()) {
         ((SchemaContainer)it.next()).setImmutable();
      }

   }

   private void fixupContainers() {
      Iterator it = this._containers.values().iterator();

      while(it.hasNext()) {
         SchemaContainer container = (SchemaContainer)it.next();
         container.setTypeSystem(this);
         container.setImmutable();
      }

   }

   private void assertContainersSynchronized() {
      boolean assertEnabled = false;
      if (!$assertionsDisabled) {
         assertEnabled = true;
         if (false) {
            throw new AssertionError();
         }
      }

      if (assertEnabled) {
         Map temp = new HashMap();
         Iterator it = this._containers.values().iterator();

         while(it.hasNext()) {
            temp.putAll(buildComponentRefMap((SchemaComponent[])((SchemaComponent[])((SchemaContainer)it.next()).globalElements().toArray(new SchemaComponent[0]))));
         }

         assert this._globalElements.equals(temp);

         temp = new HashMap();
         it = this._containers.values().iterator();

         while(it.hasNext()) {
            temp.putAll(buildComponentRefMap((SchemaComponent[])((SchemaComponent[])((SchemaContainer)it.next()).globalAttributes().toArray(new SchemaComponent[0]))));
         }

         assert this._globalAttributes.equals(temp);

         temp = new HashMap();
         it = this._containers.values().iterator();

         while(it.hasNext()) {
            temp.putAll(buildComponentRefMap((SchemaComponent[])((SchemaComponent[])((SchemaContainer)it.next()).modelGroups().toArray(new SchemaComponent[0]))));
         }

         assert this._modelGroups.equals(temp);

         Set temp2 = new HashSet();
         Iterator it = this._containers.values().iterator();

         while(it.hasNext()) {
            temp2.addAll(buildComponentRefList((SchemaComponent[])((SchemaComponent[])((SchemaContainer)it.next()).redefinedModelGroups().toArray(new SchemaComponent[0]))));
         }

         assert (new HashSet(this._redefinedModelGroups)).equals(temp2);

         temp = new HashMap();
         it = this._containers.values().iterator();

         while(it.hasNext()) {
            temp.putAll(buildComponentRefMap((SchemaComponent[])((SchemaComponent[])((SchemaContainer)it.next()).attributeGroups().toArray(new SchemaComponent[0]))));
         }

         assert this._attributeGroups.equals(temp);

         temp2 = new HashSet();
         it = this._containers.values().iterator();

         while(it.hasNext()) {
            temp2.addAll(buildComponentRefList((SchemaComponent[])((SchemaComponent[])((SchemaContainer)it.next()).redefinedAttributeGroups().toArray(new SchemaComponent[0]))));
         }

         assert (new HashSet(this._redefinedAttributeGroups)).equals(temp2);

         temp = new HashMap();
         it = this._containers.values().iterator();

         while(it.hasNext()) {
            temp.putAll(buildComponentRefMap((SchemaComponent[])((SchemaComponent[])((SchemaContainer)it.next()).globalTypes().toArray(new SchemaComponent[0]))));
         }

         assert this._globalTypes.equals(temp);

         temp2 = new HashSet();
         it = this._containers.values().iterator();

         while(it.hasNext()) {
            temp2.addAll(buildComponentRefList((SchemaComponent[])((SchemaComponent[])((SchemaContainer)it.next()).redefinedGlobalTypes().toArray(new SchemaComponent[0]))));
         }

         assert (new HashSet(this._redefinedGlobalTypes)).equals(temp2);

         temp = new HashMap();
         it = this._containers.values().iterator();

         while(it.hasNext()) {
            temp.putAll(buildDocumentMap((SchemaType[])((SchemaType[])((SchemaContainer)it.next()).documentTypes().toArray(new SchemaType[0]))));
         }

         assert this._documentTypes.equals(temp);

         temp = new HashMap();
         it = this._containers.values().iterator();

         while(it.hasNext()) {
            temp.putAll(buildAttributeTypeMap((SchemaType[])((SchemaType[])((SchemaContainer)it.next()).attributeTypes().toArray(new SchemaType[0]))));
         }

         assert this._attributeTypes.equals(temp);

         temp = new HashMap();
         it = this._containers.values().iterator();

         while(it.hasNext()) {
            temp.putAll(buildComponentRefMap((SchemaComponent[])((SchemaComponent[])((SchemaContainer)it.next()).identityConstraints().toArray(new SchemaComponent[0]))));
         }

         assert this._identityConstraints.equals(temp);

         temp2 = new HashSet();
         it = this._containers.values().iterator();

         while(it.hasNext()) {
            temp2.addAll(((SchemaContainer)it.next()).annotations());
         }

         assert (new HashSet(this._annotations)).equals(temp2);

         temp2 = new HashSet();
         it = this._containers.values().iterator();

         while(it.hasNext()) {
            temp2.add(((SchemaContainer)it.next()).getNamespace());
         }

         assert this._namespaces.equals(temp2);

      }
   }

   private static synchronized void nextBytes(byte[] result) {
      if (_random == null) {
         try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream daos = new DataOutputStream(baos);
            daos.writeInt(System.identityHashCode(SchemaTypeSystemImpl.class));
            String[] props = new String[]{"user.name", "user.dir", "user.timezone", "user.country", "java.class.path", "java.home", "java.vendor", "java.version", "os.version"};

            for(int i = 0; i < props.length; ++i) {
               String prop = SystemProperties.getProperty(props[i]);
               if (prop != null) {
                  daos.writeUTF(prop);
                  daos.writeInt(System.identityHashCode(prop));
               }
            }

            daos.writeLong(Runtime.getRuntime().freeMemory());
            daos.close();
            byte[] bytes = baos.toByteArray();

            for(int i = 0; i < bytes.length; ++i) {
               int j = i % _mask.length;
               byte[] var10000 = _mask;
               var10000[j] = (byte)(var10000[j] * 21);
               var10000 = _mask;
               var10000[j] = (byte)(var10000[j] + i);
            }
         } catch (IOException var7) {
            XBeanDebug.logException(var7);
         }

         _random = new Random(System.currentTimeMillis());
      }

      _random.nextBytes(result);

      for(int i = 0; i < result.length; ++i) {
         int j = i & _mask.length;
         result[i] ^= _mask[j];
      }

   }

   public SchemaTypeSystemImpl(String nameForSystem) {
      this._identityConstraints = Collections.EMPTY_MAP;
      this._typeRefsByClassname = new HashMap();
      this._resolvedHandles = new HashMap();
      this._allNonGroupHandlesResolved = false;
      if (nameForSystem == null) {
         byte[] bytes = new byte[16];
         nextBytes(bytes);
         nameForSystem = "s" + new String(HexBin.encode(bytes));
      }

      this._name = "schema" + METADATA_PACKAGE_GEN + ".system." + nameForSystem;
      this._basePackage = nameToPathString(this._name);
      this._classloader = null;
   }

   public void loadFromBuilder(SchemaGlobalElement[] globalElements, SchemaGlobalAttribute[] globalAttributes, SchemaType[] globalTypes, SchemaType[] documentTypes, SchemaType[] attributeTypes) {
      assert this._classloader == null;

      this._localHandles = new HandlePool();
      this._globalElements = buildComponentRefMap(globalElements);
      this._globalAttributes = buildComponentRefMap(globalAttributes);
      this._globalTypes = buildComponentRefMap(globalTypes);
      this._documentTypes = buildDocumentMap(documentTypes);
      this._attributeTypes = buildAttributeTypeMap(attributeTypes);
      this._typeRefsByClassname = this.buildTypeRefsByClassname();
      this.buildContainers(Collections.EMPTY_LIST, Collections.EMPTY_LIST, Collections.EMPTY_LIST);
      this._namespaces = new HashSet();
   }

   public void loadFromStscState(StscState state) {
      assert this._classloader == null;

      this._localHandles = new HandlePool();
      this._globalElements = buildComponentRefMap(state.globalElements());
      this._globalAttributes = buildComponentRefMap(state.globalAttributes());
      this._modelGroups = buildComponentRefMap(state.modelGroups());
      this._redefinedModelGroups = buildComponentRefList(state.redefinedModelGroups());
      this._attributeGroups = buildComponentRefMap(state.attributeGroups());
      this._redefinedAttributeGroups = buildComponentRefList(state.redefinedAttributeGroups());
      this._globalTypes = buildComponentRefMap(state.globalTypes());
      this._redefinedGlobalTypes = buildComponentRefList(state.redefinedGlobalTypes());
      this._documentTypes = buildDocumentMap(state.documentTypes());
      this._attributeTypes = buildAttributeTypeMap(state.attributeTypes());
      this._typeRefsByClassname = this.buildTypeRefsByClassname(state.typesByClassname());
      this._identityConstraints = buildComponentRefMap(state.idConstraints());
      this._annotations = state.annotations();
      this._namespaces = new HashSet(Arrays.asList(state.getNamespaces()));
      this._containers = state.getContainerMap();
      this.fixupContainers();
      this.assertContainersSynchronized();
      this.setDependencies(state.getDependencies());
   }

   final SchemaTypeSystemImpl getTypeSystem() {
      return this;
   }

   void setDependencies(SchemaDependencies deps) {
      this._deps = deps;
   }

   SchemaDependencies getDependencies() {
      return this._deps;
   }

   public boolean isIncomplete() {
      return this._incomplete;
   }

   void setIncomplete(boolean incomplete) {
      this._incomplete = incomplete;
   }

   public void saveToDirectory(File classDir) {
      this.save(new FilerImpl(classDir, (File)null, (Repackager)null, false, false));
   }

   public void save(Filer filer) {
      if (this._incomplete) {
         throw new IllegalStateException("Incomplete SchemaTypeSystems cannot be saved.");
      } else if (filer == null) {
         throw new IllegalArgumentException("filer must not be null");
      } else {
         this._filer = filer;
         this._localHandles.startWriteMode();
         this.saveTypesRecursively(this.globalTypes());
         this.saveTypesRecursively(this.documentTypes());
         this.saveTypesRecursively(this.attributeTypes());
         this.saveGlobalElements(this.globalElements());
         this.saveGlobalAttributes(this.globalAttributes());
         this.saveModelGroups(this.modelGroups());
         this.saveAttributeGroups(this.attributeGroups());
         this.saveIdentityConstraints(this.identityConstraints());
         this.saveTypesRecursively(this.redefinedGlobalTypes());
         this.saveModelGroups(this.redefinedModelGroups());
         this.saveAttributeGroups(this.redefinedAttributeGroups());
         this.saveIndex();
         this.savePointers();
         this.saveLoader();
      }
   }

   void saveTypesRecursively(SchemaType[] types) {
      for(int i = 0; i < types.length; ++i) {
         if (types[i].getTypeSystem() == this.getTypeSystem()) {
            this.saveType(types[i]);
            this.saveTypesRecursively(types[i].getAnonymousTypes());
         }
      }

   }

   public void saveGlobalElements(SchemaGlobalElement[] elts) {
      if (this._incomplete) {
         throw new IllegalStateException("This SchemaTypeSystem cannot be saved.");
      } else {
         for(int i = 0; i < elts.length; ++i) {
            this.saveGlobalElement(elts[i]);
         }

      }
   }

   public void saveGlobalAttributes(SchemaGlobalAttribute[] attrs) {
      if (this._incomplete) {
         throw new IllegalStateException("This SchemaTypeSystem cannot be saved.");
      } else {
         for(int i = 0; i < attrs.length; ++i) {
            this.saveGlobalAttribute(attrs[i]);
         }

      }
   }

   public void saveModelGroups(SchemaModelGroup[] groups) {
      if (this._incomplete) {
         throw new IllegalStateException("This SchemaTypeSystem cannot be saved.");
      } else {
         for(int i = 0; i < groups.length; ++i) {
            this.saveModelGroup(groups[i]);
         }

      }
   }

   public void saveAttributeGroups(SchemaAttributeGroup[] groups) {
      if (this._incomplete) {
         throw new IllegalStateException("This SchemaTypeSystem cannot be saved.");
      } else {
         for(int i = 0; i < groups.length; ++i) {
            this.saveAttributeGroup(groups[i]);
         }

      }
   }

   public void saveIdentityConstraints(SchemaIdentityConstraint[] idcs) {
      if (this._incomplete) {
         throw new IllegalStateException("This SchemaTypeSystem cannot be saved.");
      } else {
         for(int i = 0; i < idcs.length; ++i) {
            this.saveIdentityConstraint(idcs[i]);
         }

      }
   }

   public void saveGlobalElement(SchemaGlobalElement elt) {
      if (this._incomplete) {
         throw new IllegalStateException("This SchemaTypeSystem cannot be saved.");
      } else {
         String handle = this._localHandles.handleForElement(elt);
         XsbReader saver = new XsbReader(handle);
         saver.writeParticleData((SchemaParticle)elt);
         saver.writeString(elt.getSourceName());
         saver.writeRealHeader(handle, 3);
         saver.writeParticleData((SchemaParticle)elt);
         saver.writeString(elt.getSourceName());
         saver.writeEnd();
      }
   }

   public void saveGlobalAttribute(SchemaGlobalAttribute attr) {
      if (this._incomplete) {
         throw new IllegalStateException("This SchemaTypeSystem cannot be saved.");
      } else {
         String handle = this._localHandles.handleForAttribute(attr);
         XsbReader saver = new XsbReader(handle);
         saver.writeAttributeData(attr);
         saver.writeString(attr.getSourceName());
         saver.writeRealHeader(handle, 4);
         saver.writeAttributeData(attr);
         saver.writeString(attr.getSourceName());
         saver.writeEnd();
      }
   }

   public void saveModelGroup(SchemaModelGroup grp) {
      if (this._incomplete) {
         throw new IllegalStateException("This SchemaTypeSystem cannot be saved.");
      } else {
         String handle = this._localHandles.handleForModelGroup(grp);
         XsbReader saver = new XsbReader(handle);
         saver.writeModelGroupData(grp);
         saver.writeRealHeader(handle, 6);
         saver.writeModelGroupData(grp);
         saver.writeEnd();
      }
   }

   public void saveAttributeGroup(SchemaAttributeGroup grp) {
      if (this._incomplete) {
         throw new IllegalStateException("This SchemaTypeSystem cannot be saved.");
      } else {
         String handle = this._localHandles.handleForAttributeGroup(grp);
         XsbReader saver = new XsbReader(handle);
         saver.writeAttributeGroupData(grp);
         saver.writeRealHeader(handle, 7);
         saver.writeAttributeGroupData(grp);
         saver.writeEnd();
      }
   }

   public void saveIdentityConstraint(SchemaIdentityConstraint idc) {
      if (this._incomplete) {
         throw new IllegalStateException("This SchemaTypeSystem cannot be saved.");
      } else {
         String handle = this._localHandles.handleForIdentityConstraint(idc);
         XsbReader saver = new XsbReader(handle);
         saver.writeIdConstraintData(idc);
         saver.writeRealHeader(handle, 8);
         saver.writeIdConstraintData(idc);
         saver.writeEnd();
      }
   }

   void saveType(SchemaType type) {
      String handle = this._localHandles.handleForType(type);
      XsbReader saver = new XsbReader(handle);
      saver.writeTypeData(type);
      saver.writeRealHeader(handle, 2);
      saver.writeTypeData(type);
      saver.writeEnd();
   }

   public static String crackPointer(InputStream stream) {
      DataInputStream input = null;

      Object var3;
      try {
         input = new DataInputStream(stream);
         int magic = input.readInt();
         if (magic == -629491010) {
            int majorver = input.readShort();
            int minorver = input.readShort();
            Object var24;
            if (majorver != 2) {
               var24 = null;
               return (String)var24;
            }

            if (minorver > 24) {
               var24 = null;
               return (String)var24;
            }

            if (majorver > 2 || majorver == 2 && minorver >= 18) {
               input.readShort();
            }

            int actualfiletype = input.readShort();
            StringPool stringPool;
            if (actualfiletype != 5) {
               stringPool = null;
               return stringPool;
            }

            stringPool = new StringPool("pointer", "unk");
            stringPool.readFrom(input);
            String var7 = stringPool.stringForCode(input.readShort());
            return var7;
         }

         var3 = null;
      } catch (IOException var21) {
         var3 = null;
         return (String)var3;
      } finally {
         if (input != null) {
            try {
               input.close();
            } catch (IOException var20) {
            }
         }

      }

      return (String)var3;
   }

   public SchemaType typeForHandle(String handle) {
      synchronized(this._resolvedHandles) {
         return (SchemaType)this._resolvedHandles.get(handle);
      }
   }

   public SchemaType typeForClassname(String classname) {
      SchemaType.Ref ref = (SchemaType.Ref)this._typeRefsByClassname.get(classname);
      return ref != null ? ref.get() : null;
   }

   public SchemaComponent resolveHandle(String handle) {
      Object result;
      synchronized(this._resolvedHandles) {
         result = (SchemaComponent)this._resolvedHandles.get(handle);
      }

      if (result == null) {
         XsbReader reader = new XsbReader(handle, 65535);
         int filetype = reader.getActualFiletype();
         switch (filetype) {
            case 2:
               XBeanDebug.trace(1, "Resolving type for handle " + handle, 0);
               result = reader.finishLoadingType();
               break;
            case 3:
               XBeanDebug.trace(1, "Resolving element for handle " + handle, 0);
               result = reader.finishLoadingElement();
               break;
            case 4:
               XBeanDebug.trace(1, "Resolving attribute for handle " + handle, 0);
               result = reader.finishLoadingAttribute();
               break;
            case 5:
            default:
               throw new IllegalStateException("Illegal handle type");
            case 6:
               XBeanDebug.trace(1, "Resolving model group for handle " + handle, 0);
               result = reader.finishLoadingModelGroup();
               break;
            case 7:
               XBeanDebug.trace(1, "Resolving attribute group for handle " + handle, 0);
               result = reader.finishLoadingAttributeGroup();
               break;
            case 8:
               XBeanDebug.trace(1, "Resolving id constraint for handle " + handle, 0);
               result = reader.finishLoadingIdentityConstraint();
         }

         synchronized(this._resolvedHandles) {
            if (!this._resolvedHandles.containsKey(handle)) {
               this._resolvedHandles.put(handle, result);
            } else {
               result = (SchemaComponent)this._resolvedHandles.get(handle);
            }
         }
      }

      return (SchemaComponent)result;
   }

   public void resolve() {
      XBeanDebug.trace(1, "Resolve called type system " + this._name, 0);
      if (!this._allNonGroupHandlesResolved) {
         XBeanDebug.trace(1, "Resolving all handles for type system " + this._name, 1);
         List refs = new ArrayList();
         refs.addAll(this._globalElements.values());
         refs.addAll(this._globalAttributes.values());
         refs.addAll(this._globalTypes.values());
         refs.addAll(this._documentTypes.values());
         refs.addAll(this._attributeTypes.values());
         refs.addAll(this._identityConstraints.values());
         Iterator i = refs.iterator();

         while(i.hasNext()) {
            SchemaComponent.Ref ref = (SchemaComponent.Ref)i.next();
            ref.getComponent();
         }

         XBeanDebug.trace(1, "Finished resolving type system " + this._name, -1);
         this._allNonGroupHandlesResolved = true;
      }
   }

   public boolean isNamespaceDefined(String namespace) {
      return this._namespaces.contains(namespace);
   }

   public SchemaType.Ref findTypeRef(QName name) {
      return (SchemaType.Ref)this._globalTypes.get(name);
   }

   public SchemaType.Ref findDocumentTypeRef(QName name) {
      return (SchemaType.Ref)this._documentTypes.get(name);
   }

   public SchemaType.Ref findAttributeTypeRef(QName name) {
      return (SchemaType.Ref)this._attributeTypes.get(name);
   }

   public SchemaGlobalElement.Ref findElementRef(QName name) {
      return (SchemaGlobalElement.Ref)this._globalElements.get(name);
   }

   public SchemaGlobalAttribute.Ref findAttributeRef(QName name) {
      return (SchemaGlobalAttribute.Ref)this._globalAttributes.get(name);
   }

   public SchemaModelGroup.Ref findModelGroupRef(QName name) {
      return (SchemaModelGroup.Ref)this._modelGroups.get(name);
   }

   public SchemaAttributeGroup.Ref findAttributeGroupRef(QName name) {
      return (SchemaAttributeGroup.Ref)this._attributeGroups.get(name);
   }

   public SchemaIdentityConstraint.Ref findIdentityConstraintRef(QName name) {
      return (SchemaIdentityConstraint.Ref)this._identityConstraints.get(name);
   }

   public SchemaType[] globalTypes() {
      if (this._globalTypes.isEmpty()) {
         return EMPTY_ST_ARRAY;
      } else {
         SchemaType[] result = new SchemaType[this._globalTypes.size()];
         int j = 0;

         for(Iterator i = this._globalTypes.values().iterator(); i.hasNext(); ++j) {
            result[j] = ((SchemaType.Ref)i.next()).get();
         }

         return result;
      }
   }

   public SchemaType[] redefinedGlobalTypes() {
      if (this._redefinedGlobalTypes != null && !this._redefinedGlobalTypes.isEmpty()) {
         SchemaType[] result = new SchemaType[this._redefinedGlobalTypes.size()];
         int j = 0;

         for(Iterator i = this._redefinedGlobalTypes.iterator(); i.hasNext(); ++j) {
            result[j] = ((SchemaType.Ref)i.next()).get();
         }

         return result;
      } else {
         return EMPTY_ST_ARRAY;
      }
   }

   public InputStream getSourceAsStream(String sourceName) {
      if (!sourceName.startsWith("/")) {
         sourceName = "/" + sourceName;
      }

      return this._resourceLoader.getResourceAsStream("schema" + METADATA_PACKAGE_GEN + "/src" + sourceName);
   }

   SchemaContainer[] containers() {
      SchemaContainer[] result = new SchemaContainer[this._containers.size()];
      int j = 0;

      for(Iterator i = this._containers.values().iterator(); i.hasNext(); ++j) {
         result[j] = (SchemaContainer)i.next();
      }

      return result;
   }

   public SchemaType[] documentTypes() {
      if (this._documentTypes.isEmpty()) {
         return EMPTY_ST_ARRAY;
      } else {
         SchemaType[] result = new SchemaType[this._documentTypes.size()];
         int j = 0;

         for(Iterator i = this._documentTypes.values().iterator(); i.hasNext(); ++j) {
            result[j] = ((SchemaType.Ref)i.next()).get();
         }

         return result;
      }
   }

   public SchemaType[] attributeTypes() {
      if (this._attributeTypes.isEmpty()) {
         return EMPTY_ST_ARRAY;
      } else {
         SchemaType[] result = new SchemaType[this._attributeTypes.size()];
         int j = 0;

         for(Iterator i = this._attributeTypes.values().iterator(); i.hasNext(); ++j) {
            result[j] = ((SchemaType.Ref)i.next()).get();
         }

         return result;
      }
   }

   public SchemaGlobalElement[] globalElements() {
      if (this._globalElements.isEmpty()) {
         return EMPTY_GE_ARRAY;
      } else {
         SchemaGlobalElement[] result = new SchemaGlobalElement[this._globalElements.size()];
         int j = 0;

         for(Iterator i = this._globalElements.values().iterator(); i.hasNext(); ++j) {
            result[j] = ((SchemaGlobalElement.Ref)i.next()).get();
         }

         return result;
      }
   }

   public SchemaGlobalAttribute[] globalAttributes() {
      if (this._globalAttributes.isEmpty()) {
         return EMPTY_GA_ARRAY;
      } else {
         SchemaGlobalAttribute[] result = new SchemaGlobalAttribute[this._globalAttributes.size()];
         int j = 0;

         for(Iterator i = this._globalAttributes.values().iterator(); i.hasNext(); ++j) {
            result[j] = ((SchemaGlobalAttribute.Ref)i.next()).get();
         }

         return result;
      }
   }

   public SchemaModelGroup[] modelGroups() {
      if (this._modelGroups.isEmpty()) {
         return EMPTY_MG_ARRAY;
      } else {
         SchemaModelGroup[] result = new SchemaModelGroup[this._modelGroups.size()];
         int j = 0;

         for(Iterator i = this._modelGroups.values().iterator(); i.hasNext(); ++j) {
            result[j] = ((SchemaModelGroup.Ref)i.next()).get();
         }

         return result;
      }
   }

   public SchemaModelGroup[] redefinedModelGroups() {
      if (this._redefinedModelGroups != null && !this._redefinedModelGroups.isEmpty()) {
         SchemaModelGroup[] result = new SchemaModelGroup[this._redefinedModelGroups.size()];
         int j = 0;

         for(Iterator i = this._redefinedModelGroups.iterator(); i.hasNext(); ++j) {
            result[j] = ((SchemaModelGroup.Ref)i.next()).get();
         }

         return result;
      } else {
         return EMPTY_MG_ARRAY;
      }
   }

   public SchemaAttributeGroup[] attributeGroups() {
      if (this._attributeGroups.isEmpty()) {
         return EMPTY_AG_ARRAY;
      } else {
         SchemaAttributeGroup[] result = new SchemaAttributeGroup[this._attributeGroups.size()];
         int j = 0;

         for(Iterator i = this._attributeGroups.values().iterator(); i.hasNext(); ++j) {
            result[j] = ((SchemaAttributeGroup.Ref)i.next()).get();
         }

         return result;
      }
   }

   public SchemaAttributeGroup[] redefinedAttributeGroups() {
      if (this._redefinedAttributeGroups != null && !this._redefinedAttributeGroups.isEmpty()) {
         SchemaAttributeGroup[] result = new SchemaAttributeGroup[this._redefinedAttributeGroups.size()];
         int j = 0;

         for(Iterator i = this._redefinedAttributeGroups.iterator(); i.hasNext(); ++j) {
            result[j] = ((SchemaAttributeGroup.Ref)i.next()).get();
         }

         return result;
      } else {
         return EMPTY_AG_ARRAY;
      }
   }

   public SchemaAnnotation[] annotations() {
      if (this._annotations != null && !this._annotations.isEmpty()) {
         SchemaAnnotation[] result = new SchemaAnnotation[this._annotations.size()];
         result = (SchemaAnnotation[])((SchemaAnnotation[])this._annotations.toArray(result));
         return result;
      } else {
         return EMPTY_ANN_ARRAY;
      }
   }

   public SchemaIdentityConstraint[] identityConstraints() {
      if (this._identityConstraints.isEmpty()) {
         return EMPTY_IC_ARRAY;
      } else {
         SchemaIdentityConstraint[] result = new SchemaIdentityConstraint[this._identityConstraints.size()];
         int j = 0;

         for(Iterator i = this._identityConstraints.values().iterator(); i.hasNext(); ++j) {
            result[j] = ((SchemaIdentityConstraint.Ref)i.next()).get();
         }

         return result;
      }
   }

   public ClassLoader getClassLoader() {
      return this._classloader;
   }

   public String handleForType(SchemaType type) {
      return this._localHandles.handleForType(type);
   }

   public String getName() {
      return this._name;
   }

   public SchemaTypeSystem typeSystemForName(String name) {
      return this._name != null && name.equals(this._name) ? this : null;
   }

   static {
      Package stsPackage = SchemaTypeSystem.class.getPackage();
      String stsPackageName = stsPackage == null ? SchemaTypeSystem.class.getName().substring(0, SchemaTypeSystem.class.getName().lastIndexOf(".")) : stsPackage.getName();
      METADATA_PACKAGE_GEN = stsPackageName.replaceAll("\\.", "_");
      HOLDER_TEMPLATE_NAMES = makeClassStrings("com.bea.xbean.schema.TypeSystemHolder");
      _mask = new byte[16];
      EMPTY_ST_ARRAY = new SchemaType[0];
      EMPTY_GE_ARRAY = new SchemaGlobalElement[0];
      EMPTY_GA_ARRAY = new SchemaGlobalAttribute[0];
      EMPTY_MG_ARRAY = new SchemaModelGroup[0];
      EMPTY_AG_ARRAY = new SchemaAttributeGroup[0];
      EMPTY_IC_ARRAY = new SchemaIdentityConstraint[0];
      EMPTY_ANN_ARRAY = new SchemaAnnotation[0];
      SINGLE_ZERO_BYTE = new byte[]{0};
   }

   private class XsbReader {
      DataInputStream _input;
      DataOutputStream _output;
      StringPool _stringPool;
      String _handle;
      private int _majorver;
      private int _minorver;
      private int _releaseno;
      int _actualfiletype;

      public XsbReader(String handle, int filetype) {
         String resourcename = SchemaTypeSystemImpl.this._basePackage + handle + ".xsb";
         InputStream rawinput = this.getLoaderStream(resourcename);
         if (rawinput == null) {
            throw new SchemaTypeLoaderException("XML-BEANS compiled schema: Could not locate compiled schema resource " + resourcename, SchemaTypeSystemImpl.this._name, handle, 0);
         } else {
            this._input = new DataInputStream(rawinput);
            this._handle = handle;
            int magic = this.readInt();
            if (magic != -629491010) {
               throw new SchemaTypeLoaderException("XML-BEANS compiled schema: Wrong magic cookie", SchemaTypeSystemImpl.this._name, handle, 1);
            } else {
               this._majorver = this.readShort();
               this._minorver = this.readShort();
               if (this._majorver != 2) {
                  throw new SchemaTypeLoaderException("XML-BEANS compiled schema: Wrong major version - expecting 2, got " + this._majorver, SchemaTypeSystemImpl.this._name, handle, 2);
               } else if (this._minorver > 24) {
                  throw new SchemaTypeLoaderException("XML-BEANS compiled schema: Incompatible minor version - expecting up to 24, got " + this._minorver, SchemaTypeSystemImpl.this._name, handle, 3);
               } else if (this._minorver < 14) {
                  throw new SchemaTypeLoaderException("XML-BEANS compiled schema: Incompatible minor version - expecting at least 14, got " + this._minorver, SchemaTypeSystemImpl.this._name, handle, 3);
               } else {
                  if (this.atLeast(2, 18, 0)) {
                     this._releaseno = this.readShort();
                  }

                  int actualfiletype = this.readShort();
                  if (actualfiletype != filetype && filetype != 65535) {
                     throw new SchemaTypeLoaderException("XML-BEANS compiled schema: File has the wrong type - expecting type " + filetype + ", got type " + actualfiletype, SchemaTypeSystemImpl.this._name, handle, 4);
                  } else {
                     this._stringPool = new StringPool(this._handle, SchemaTypeSystemImpl.this._name);
                     this._stringPool.readFrom(this._input);
                     this._actualfiletype = actualfiletype;
                  }
               }
            }
         }
      }

      protected boolean atLeast(int majorver, int minorver, int releaseno) {
         if (this._majorver > majorver) {
            return true;
         } else if (this._majorver < majorver) {
            return false;
         } else if (this._minorver > minorver) {
            return true;
         } else if (this._minorver < minorver) {
            return false;
         } else {
            return this._releaseno >= releaseno;
         }
      }

      protected boolean atMost(int majorver, int minorver, int releaseno) {
         if (this._majorver > majorver) {
            return false;
         } else if (this._majorver < majorver) {
            return true;
         } else if (this._minorver > minorver) {
            return false;
         } else if (this._minorver < minorver) {
            return true;
         } else {
            return this._releaseno <= releaseno;
         }
      }

      int getActualFiletype() {
         return this._actualfiletype;
      }

      XsbReader(String handle) {
         this._handle = handle;
         this._stringPool = new StringPool(this._handle, SchemaTypeSystemImpl.this._name);
      }

      void writeRealHeader(String handle, int filetype) {
         String resourcename;
         if (handle.indexOf(47) >= 0) {
            resourcename = handle + ".xsb";
         } else {
            resourcename = SchemaTypeSystemImpl.this._basePackage + handle + ".xsb";
         }

         OutputStream rawoutput = this.getSaverStream(resourcename);
         if (rawoutput == null) {
            throw new SchemaTypeLoaderException("Could not write compiled schema resource " + resourcename, SchemaTypeSystemImpl.this._name, handle, 12);
         } else {
            this._output = new DataOutputStream(rawoutput);
            this._handle = handle;
            this.writeInt(-629491010);
            this.writeShort(2);
            this.writeShort(24);
            this.writeShort(0);
            this.writeShort(filetype);
            this._stringPool.writeTo(this._output);
         }
      }

      void readEnd() {
         try {
            if (this._input != null) {
               this._input.close();
            }
         } catch (IOException var2) {
         }

         this._input = null;
         this._stringPool = null;
         this._handle = null;
      }

      void writeEnd() {
         try {
            if (this._output != null) {
               this._output.flush();
               this._output.close();
            }
         } catch (IOException var2) {
            throw new SchemaTypeLoaderException(var2.getMessage(), SchemaTypeSystemImpl.this._name, this._handle, 9);
         }

         this._output = null;
         this._stringPool = null;
         this._handle = null;
      }

      int fileTypeFromComponentType(int componentType) {
         switch (componentType) {
            case 0:
               return 2;
            case 1:
               return 3;
            case 2:
            default:
               throw new IllegalStateException("Unexpected component type");
            case 3:
               return 4;
            case 4:
               return 7;
            case 5:
               return 8;
            case 6:
               return 6;
         }
      }

      void writeIndexData() {
         this.writeHandlePool(SchemaTypeSystemImpl.this._localHandles);
         this.writeQNameMap(SchemaTypeSystemImpl.this.globalElements());
         this.writeQNameMap(SchemaTypeSystemImpl.this.globalAttributes());
         this.writeQNameMap(SchemaTypeSystemImpl.this.modelGroups());
         this.writeQNameMap(SchemaTypeSystemImpl.this.attributeGroups());
         this.writeQNameMap(SchemaTypeSystemImpl.this.identityConstraints());
         this.writeQNameMap(SchemaTypeSystemImpl.this.globalTypes());
         this.writeDocumentTypeMap(SchemaTypeSystemImpl.this.documentTypes());
         this.writeAttributeTypeMap(SchemaTypeSystemImpl.this.attributeTypes());
         this.writeClassnameMap(SchemaTypeSystemImpl.this._typeRefsByClassname);
         this.writeNamespaces(SchemaTypeSystemImpl.this._namespaces);
         this.writeQNameMap(SchemaTypeSystemImpl.this.redefinedGlobalTypes());
         this.writeQNameMap(SchemaTypeSystemImpl.this.redefinedModelGroups());
         this.writeQNameMap(SchemaTypeSystemImpl.this.redefinedAttributeGroups());
         this.writeAnnotations(SchemaTypeSystemImpl.this.annotations());
      }

      void writeHandlePool(HandlePool pool) {
         this.writeShort(pool._componentsToHandles.size());
         Iterator i = pool._componentsToHandles.keySet().iterator();

         while(i.hasNext()) {
            SchemaComponent comp = (SchemaComponent)i.next();
            String handle = (String)pool._componentsToHandles.get(comp);
            int code = this.fileTypeFromComponentType(comp.getComponentType());
            this.writeString(handle);
            this.writeShort(code);
         }

      }

      void readHandlePool(HandlePool pool) {
         if (pool._handlesToRefs.size() == 0 && !pool._started) {
            int size = this.readShort();

            for(int i = 0; i < size; ++i) {
               String handle = this.readString();
               int code = this.readShort();
               Object result;
               switch (code) {
                  case 2:
                     result = new SchemaType.Ref(SchemaTypeSystemImpl.this.getTypeSystem(), handle);
                     break;
                  case 3:
                     result = new SchemaGlobalElement.Ref(SchemaTypeSystemImpl.this.getTypeSystem(), handle);
                     break;
                  case 4:
                     result = new SchemaGlobalAttribute.Ref(SchemaTypeSystemImpl.this.getTypeSystem(), handle);
                     break;
                  case 5:
                  default:
                     throw new SchemaTypeLoaderException("Schema index has an unrecognized entry of type " + code, SchemaTypeSystemImpl.this._name, handle, 5);
                  case 6:
                     result = new SchemaModelGroup.Ref(SchemaTypeSystemImpl.this.getTypeSystem(), handle);
                     break;
                  case 7:
                     result = new SchemaAttributeGroup.Ref(SchemaTypeSystemImpl.this.getTypeSystem(), handle);
                     break;
                  case 8:
                     result = new SchemaIdentityConstraint.Ref(SchemaTypeSystemImpl.this.getTypeSystem(), handle);
               }

               pool._handlesToRefs.put(handle, result);
            }

         } else {
            throw new IllegalStateException("Nonempty handle set before read");
         }
      }

      int readShort() {
         try {
            return this._input.readUnsignedShort();
         } catch (IOException var2) {
            throw new SchemaTypeLoaderException(var2.getMessage(), SchemaTypeSystemImpl.this._name, this._handle, 9);
         }
      }

      void writeShort(int s) {
         if (s < 65535 && s >= -1) {
            if (this._output != null) {
               try {
                  this._output.writeShort(s);
               } catch (IOException var3) {
                  throw new SchemaTypeLoaderException(var3.getMessage(), SchemaTypeSystemImpl.this._name, this._handle, 9);
               }
            }

         } else {
            throw new SchemaTypeLoaderException("Value " + s + " out of range: must fit in a 16-bit unsigned short.", SchemaTypeSystemImpl.this._name, this._handle, 10);
         }
      }

      int readInt() {
         try {
            return this._input.readInt();
         } catch (IOException var2) {
            throw new SchemaTypeLoaderException(var2.getMessage(), SchemaTypeSystemImpl.this._name, this._handle, 9);
         }
      }

      void writeInt(int i) {
         if (this._output != null) {
            try {
               this._output.writeInt(i);
            } catch (IOException var3) {
               throw new SchemaTypeLoaderException(var3.getMessage(), SchemaTypeSystemImpl.this._name, this._handle, 9);
            }
         }

      }

      String readString() {
         return this._stringPool.stringForCode(this.readShort());
      }

      void writeString(String str) {
         int code = this._stringPool.codeForString(str);
         this.writeShort(code);
      }

      QName readQName() {
         String namespace = this.readString();
         String localname = this.readString();
         return localname == null ? null : new QName(namespace, localname);
      }

      void writeQName(QName qname) {
         if (qname == null) {
            this.writeString((String)null);
            this.writeString((String)null);
         } else {
            this.writeString(qname.getNamespaceURI());
            this.writeString(qname.getLocalPart());
         }
      }

      SOAPArrayType readSOAPArrayType() {
         QName qName = this.readQName();
         String dimensions = this.readString();
         return qName == null ? null : new SOAPArrayType(qName, dimensions);
      }

      void writeSOAPArrayType(SOAPArrayType arrayType) {
         if (arrayType == null) {
            this.writeQName((QName)null);
            this.writeString((String)null);
         } else {
            this.writeQName(arrayType.getQName());
            this.writeString(arrayType.soap11DimensionString());
         }

      }

      void writeAnnotation(SchemaAnnotation a) {
         if (a == null) {
            this.writeInt(-1);
         } else {
            SchemaAnnotation.Attribute[] attributes = a.getAttributes();
            this.writeInt(attributes.length);

            for(int ix = 0; ix < attributes.length; ++ix) {
               QName name = attributes[ix].getName();
               String value = attributes[ix].getValue();
               String valueURI = attributes[ix].getValueUri();
               this.writeQName(name);
               this.writeString(value);
               this.writeString(valueURI);
            }

            XmlObject[] documentationItems = a.getUserInformation();
            this.writeInt(documentationItems.length);
            XmlOptions opt = (new XmlOptions()).setSaveOuter().setSaveAggressiveNamespaces();

            for(int ixx = 0; ixx < documentationItems.length; ++ixx) {
               XmlObject doc = documentationItems[ixx];
               this.writeString(doc.xmlText(opt));
            }

            XmlObject[] appInfoItems = a.getApplicationInformation();
            this.writeInt(appInfoItems.length);

            for(int i = 0; i < appInfoItems.length; ++i) {
               XmlObject docx = appInfoItems[i];
               this.writeString(docx.xmlText(opt));
            }

         }
      }

      SchemaAnnotation readAnnotation(SchemaContainer c) {
         if (!this.atLeast(2, 19, 0)) {
            return null;
         } else {
            int n = this.readInt();
            if (n == -1) {
               return null;
            } else {
               SchemaAnnotation.Attribute[] attributes = new SchemaAnnotation.Attribute[n];

               for(int ix = 0; ix < n; ++ix) {
                  QName name = this.readQName();
                  String value = this.readString();
                  String valueUri = null;
                  if (this.atLeast(2, 24, 0)) {
                     valueUri = this.readString();
                  }

                  attributes[ix] = new SchemaAnnotationImpl.AttributeImpl(name, value, valueUri);
               }

               n = this.readInt();
               String[] docStrings = new String[n];

               for(int ixx = 0; ixx < n; ++ixx) {
                  docStrings[ixx] = this.readString();
               }

               n = this.readInt();
               String[] appInfoStrings = new String[n];

               for(int i = 0; i < n; ++i) {
                  appInfoStrings[i] = this.readString();
               }

               return new SchemaAnnotationImpl(c, appInfoStrings, docStrings, attributes);
            }
         }
      }

      void writeAnnotations(SchemaAnnotation[] anns) {
         this.writeInt(anns.length);

         for(int i = 0; i < anns.length; ++i) {
            this.writeAnnotation(anns[i]);
         }

      }

      List readAnnotations() {
         int n = this.readInt();
         List result = new ArrayList(n);
         SchemaContainer container = SchemaTypeSystemImpl.this.getContainerNonNull("");

         for(int i = 0; i < n; ++i) {
            result.add(this.readAnnotation(container));
         }

         return result;
      }

      SchemaComponent.Ref readHandle() {
         String handle = this.readString();
         if (handle == null) {
            return null;
         } else if (handle.charAt(0) != '_') {
            return SchemaTypeSystemImpl.this._localHandles.refForHandle(handle);
         } else {
            switch (handle.charAt(2)) {
               case 'A':
                  return SchemaTypeSystemImpl.this._linker.findAttributeRef(QNameHelper.forPretty(handle, 4));
               case 'B':
               case 'C':
               case 'F':
               case 'G':
               case 'H':
               case 'J':
               case 'K':
               case 'L':
               case 'P':
               case 'Q':
               case 'U':
               case 'V':
               case 'W':
               case 'X':
               default:
                  throw new SchemaTypeLoaderException("Cannot resolve handle " + handle, SchemaTypeSystemImpl.this._name, this._handle, 13);
               case 'D':
                  return SchemaTypeSystemImpl.this._linker.findIdentityConstraintRef(QNameHelper.forPretty(handle, 4));
               case 'E':
                  return SchemaTypeSystemImpl.this._linker.findElementRef(QNameHelper.forPretty(handle, 4));
               case 'I':
                  SchemaType st = (SchemaType)BuiltinSchemaTypeSystem.get().resolveHandle(handle);
                  if (st != null) {
                     return st.getRef();
                  }

                  st = (SchemaType)XQuerySchemaTypeSystem.get().resolveHandle(handle);
                  return st.getRef();
               case 'M':
                  return SchemaTypeSystemImpl.this._linker.findModelGroupRef(QNameHelper.forPretty(handle, 4));
               case 'N':
                  return SchemaTypeSystemImpl.this._linker.findAttributeGroupRef(QNameHelper.forPretty(handle, 4));
               case 'O':
                  return SchemaTypeSystemImpl.this._linker.findDocumentTypeRef(QNameHelper.forPretty(handle, 4));
               case 'R':
                  SchemaGlobalAttribute attr = SchemaTypeSystemImpl.this._linker.findAttribute(QNameHelper.forPretty(handle, 4));
                  if (attr == null) {
                     throw new SchemaTypeLoaderException("Cannot resolve attribute for handle " + handle, SchemaTypeSystemImpl.this._name, this._handle, 13);
                  }

                  return attr.getType().getRef();
               case 'S':
                  SchemaGlobalElement elem = SchemaTypeSystemImpl.this._linker.findElement(QNameHelper.forPretty(handle, 4));
                  if (elem == null) {
                     throw new SchemaTypeLoaderException("Cannot resolve element for handle " + handle, SchemaTypeSystemImpl.this._name, this._handle, 13);
                  }

                  return elem.getType().getRef();
               case 'T':
                  return SchemaTypeSystemImpl.this._linker.findTypeRef(QNameHelper.forPretty(handle, 4));
               case 'Y':
                  SchemaType type = SchemaTypeSystemImpl.this._linker.typeForSignature(handle.substring(4));
                  if (type == null) {
                     throw new SchemaTypeLoaderException("Cannot resolve type for handle " + handle, SchemaTypeSystemImpl.this._name, this._handle, 13);
                  } else {
                     return type.getRef();
                  }
            }
         }
      }

      void writeHandle(SchemaComponent comp) {
         if (comp != null && comp.getTypeSystem() != SchemaTypeSystemImpl.this.getTypeSystem()) {
            switch (comp.getComponentType()) {
               case 0:
                  SchemaType type = (SchemaType)comp;
                  if (type.isBuiltinType()) {
                     this.writeString("_BI_" + type.getName().getLocalPart());
                     return;
                  }

                  if (type.getName() != null) {
                     this.writeString("_XT_" + QNameHelper.pretty(type.getName()));
                  } else if (type.isDocumentType()) {
                     this.writeString("_XO_" + QNameHelper.pretty(type.getDocumentElementName()));
                  } else {
                     this.writeString("_XY_" + type.toString());
                  }

                  return;
               case 1:
                  this.writeString("_XE_" + QNameHelper.pretty(comp.getName()));
                  return;
               case 2:
               default:
                  assert false;

                  throw new SchemaTypeLoaderException("Cannot write handle for component " + comp, SchemaTypeSystemImpl.this._name, this._handle, 13);
               case 3:
                  this.writeString("_XA_" + QNameHelper.pretty(comp.getName()));
                  return;
               case 4:
                  this.writeString("_XN_" + QNameHelper.pretty(comp.getName()));
                  return;
               case 5:
                  this.writeString("_XD_" + QNameHelper.pretty(comp.getName()));
                  return;
               case 6:
                  this.writeString("_XM_" + QNameHelper.pretty(comp.getName()));
            }
         } else {
            this.writeString(SchemaTypeSystemImpl.this._localHandles.handleForComponent(comp));
         }
      }

      SchemaType.Ref readTypeRef() {
         return (SchemaType.Ref)this.readHandle();
      }

      void writeType(SchemaType type) {
         this.writeHandle(type);
      }

      Map readQNameRefMap() {
         Map result = new HashMap();
         int size = this.readShort();

         for(int i = 0; i < size; ++i) {
            QName name = this.readQName();
            SchemaComponent.Ref obj = this.readHandle();
            result.put(name, obj);
         }

         return result;
      }

      List readQNameRefMapAsList(List names) {
         int size = this.readShort();
         List result = new ArrayList(size);

         for(int i = 0; i < size; ++i) {
            QName name = this.readQName();
            SchemaComponent.Ref obj = this.readHandle();
            result.add(obj);
            names.add(name);
         }

         return result;
      }

      void writeQNameMap(SchemaComponent[] components) {
         this.writeShort(components.length);

         for(int i = 0; i < components.length; ++i) {
            this.writeQName(components[i].getName());
            this.writeHandle(components[i]);
         }

      }

      void writeDocumentTypeMap(SchemaType[] doctypes) {
         this.writeShort(doctypes.length);

         for(int i = 0; i < doctypes.length; ++i) {
            this.writeQName(doctypes[i].getDocumentElementName());
            this.writeHandle(doctypes[i]);
         }

      }

      void writeAttributeTypeMap(SchemaType[] attrtypes) {
         this.writeShort(attrtypes.length);

         for(int i = 0; i < attrtypes.length; ++i) {
            this.writeQName(attrtypes[i].getAttributeTypeAttributeName());
            this.writeHandle(attrtypes[i]);
         }

      }

      SchemaType.Ref[] readTypeRefArray() {
         int size = this.readShort();
         SchemaType.Ref[] result = new SchemaType.Ref[size];

         for(int i = 0; i < size; ++i) {
            result[i] = this.readTypeRef();
         }

         return result;
      }

      void writeTypeArray(SchemaType[] array) {
         this.writeShort(array.length);

         for(int i = 0; i < array.length; ++i) {
            this.writeHandle(array[i]);
         }

      }

      Map readClassnameRefMap() {
         Map result = new HashMap();
         int size = this.readShort();

         for(int i = 0; i < size; ++i) {
            String name = this.readString();
            SchemaComponent.Ref obj = this.readHandle();
            result.put(name, obj);
         }

         return result;
      }

      void writeClassnameMap(Map typesByClass) {
         this.writeShort(typesByClass.size());
         Iterator i = typesByClass.keySet().iterator();

         while(i.hasNext()) {
            String className = (String)i.next();
            this.writeString(className);
            this.writeHandle(((SchemaType.Ref)typesByClass.get(className)).get());
         }

      }

      Set readNamespaces() {
         Set result = new HashSet();
         int size = this.readShort();

         for(int i = 0; i < size; ++i) {
            String ns = this.readString();
            result.add(ns);
         }

         return result;
      }

      void writeNamespaces(Set namespaces) {
         this.writeShort(namespaces.size());
         Iterator i = namespaces.iterator();

         while(i.hasNext()) {
            String ns = (String)i.next();
            this.writeString(ns);
         }

      }

      OutputStream getSaverStream(String name) {
         try {
            return SchemaTypeSystemImpl.this._filer.createBinaryFile(name);
         } catch (IOException var3) {
            throw new SchemaTypeLoaderException(var3.getMessage(), SchemaTypeSystemImpl.this._name, this._handle, 9);
         }
      }

      InputStream getLoaderStream(String resourcename) {
         return SchemaTypeSystemImpl.this._resourceLoader.getResourceAsStream(resourcename);
      }

      void checkContainerNotNull(SchemaContainer container, QName name) {
         if (container == null) {
            throw new LinkageError("Loading of resource " + SchemaTypeSystemImpl.this._name + '.' + this._handle + "failed, information from " + SchemaTypeSystemImpl.this._name + ".index.xsb is " + " out of sync (or conflicting index files found)");
         }
      }

      public SchemaGlobalElement finishLoadingElement() {
         String handle = null;

         try {
            int particleType = this.readShort();
            if (particleType != 4) {
               throw new SchemaTypeLoaderException("Wrong particle type ", SchemaTypeSystemImpl.this._name, this._handle, 11);
            } else {
               int particleFlags = this.readShort();
               BigInteger minOccurs = this.readBigInteger();
               BigInteger maxOccurs = this.readBigInteger();
               QNameSet transitionRules = this.readQNameSet();
               QName name = this.readQName();
               SchemaContainer container = SchemaTypeSystemImpl.this.getContainer(name.getNamespaceURI());
               this.checkContainerNotNull(container, name);
               SchemaGlobalElementImpl impl = new SchemaGlobalElementImpl(container);
               impl.setParticleType(particleType);
               impl.setMinOccurs(minOccurs);
               impl.setMaxOccurs(maxOccurs);
               impl.setTransitionRules(transitionRules, (particleFlags & 1) != 0);
               impl.setNameAndTypeRef(name, this.readTypeRef());
               impl.setDefault(this.readString(), (particleFlags & 4) != 0, (XmlObject)null);
               if (this.atLeast(2, 16, 0)) {
                  impl.setDefaultValue(this.readXmlValueObject());
               }

               impl.setNillable((particleFlags & 8) != 0);
               impl.setBlock((particleFlags & 16) != 0, (particleFlags & 32) != 0, (particleFlags & 64) != 0);
               impl.setWsdlArrayType(this.readSOAPArrayType());
               impl.setAbstract((particleFlags & 128) != 0);
               impl.setAnnotation(this.readAnnotation(container));
               impl.setFinal((particleFlags & 256) != 0, (particleFlags & 512) != 0);
               if (this.atLeast(2, 17, 0)) {
                  impl.setSubstitutionGroup((SchemaGlobalElement.Ref)this.readHandle());
               }

               int substGroupCount = this.readShort();

               for(int i = 0; i < substGroupCount; ++i) {
                  impl.addSubstitutionGroupMember(this.readQName());
               }

               SchemaIdentityConstraint.Ref[] idcs = new SchemaIdentityConstraint.Ref[this.readShort()];

               for(int ix = 0; ix < idcs.length; ++ix) {
                  idcs[ix] = (SchemaIdentityConstraint.Ref)this.readHandle();
               }

               impl.setIdentityConstraints(idcs);
               impl.setFilename(this.readString());
               SchemaGlobalElementImpl var21 = impl;
               return var21;
            }
         } catch (SchemaTypeLoaderException var17) {
            throw var17;
         } catch (Exception var18) {
            throw new SchemaTypeLoaderException("Cannot load type from typesystem", SchemaTypeSystemImpl.this._name, (String)handle, 14, var18);
         } finally {
            this.readEnd();
         }
      }

      public SchemaGlobalAttribute finishLoadingAttribute() {
         SchemaGlobalAttributeImpl var4;
         try {
            QName name = this.readQName();
            SchemaContainer container = SchemaTypeSystemImpl.this.getContainer(name.getNamespaceURI());
            this.checkContainerNotNull(container, name);
            SchemaGlobalAttributeImpl impl = new SchemaGlobalAttributeImpl(container);
            this.loadAttribute(impl, name, container);
            impl.setFilename(this.readString());
            var4 = impl;
         } catch (SchemaTypeLoaderException var9) {
            throw var9;
         } catch (Exception var10) {
            throw new SchemaTypeLoaderException("Cannot load type from typesystem", SchemaTypeSystemImpl.this._name, this._handle, 14, var10);
         } finally {
            this.readEnd();
         }

         return var4;
      }

      SchemaModelGroup finishLoadingModelGroup() {
         QName name = this.readQName();
         SchemaContainer container = SchemaTypeSystemImpl.this.getContainer(name.getNamespaceURI());
         this.checkContainerNotNull(container, name);
         SchemaModelGroupImpl impl = new SchemaModelGroupImpl(container);

         SchemaModelGroupImpl var4;
         try {
            impl.init(name, this.readString(), this.readShort() == 1, this.atLeast(2, 22, 0) ? this.readString() : null, this.atLeast(2, 22, 0) ? this.readString() : null, this.atLeast(2, 15, 0) ? this.readShort() == 1 : false, GroupDocument.Factory.parse(this.readString()).getGroup(), this.readAnnotation(container), (Object)null);
            if (this.atLeast(2, 21, 0)) {
               impl.setFilename(this.readString());
            }

            var4 = impl;
         } catch (SchemaTypeLoaderException var9) {
            throw var9;
         } catch (Exception var10) {
            throw new SchemaTypeLoaderException("Cannot load type from typesystem", SchemaTypeSystemImpl.this._name, this._handle, 14, var10);
         } finally {
            this.readEnd();
         }

         return var4;
      }

      SchemaIdentityConstraint finishLoadingIdentityConstraint() {
         SchemaIdentityConstraintImpl var17;
         try {
            QName name = this.readQName();
            SchemaContainer container = SchemaTypeSystemImpl.this.getContainer(name.getNamespaceURI());
            this.checkContainerNotNull(container, name);
            SchemaIdentityConstraintImpl impl = new SchemaIdentityConstraintImpl(container);
            impl.setName(name);
            impl.setConstraintCategory(this.readShort());
            impl.setSelector(this.readString());
            impl.setAnnotation(this.readAnnotation(container));
            String[] fields = new String[this.readShort()];

            int mapCount;
            for(mapCount = 0; mapCount < fields.length; ++mapCount) {
               fields[mapCount] = this.readString();
            }

            impl.setFields(fields);
            if (impl.getConstraintCategory() == 2) {
               impl.setReferencedKey((SchemaIdentityConstraint.Ref)this.readHandle());
            }

            mapCount = this.readShort();
            Map nsMappings = new HashMap();

            for(int i = 0; i < mapCount; ++i) {
               String prefix = this.readString();
               String uri = this.readString();
               nsMappings.put(prefix, uri);
            }

            impl.setNSMap(nsMappings);
            if (this.atLeast(2, 21, 0)) {
               impl.setFilename(this.readString());
            }

            var17 = impl;
         } catch (SchemaTypeLoaderException var14) {
            throw var14;
         } catch (Exception var15) {
            throw new SchemaTypeLoaderException("Cannot load type from typesystem", SchemaTypeSystemImpl.this._name, this._handle, 14, var15);
         } finally {
            this.readEnd();
         }

         return var17;
      }

      SchemaAttributeGroup finishLoadingAttributeGroup() {
         QName name = this.readQName();
         SchemaContainer container = SchemaTypeSystemImpl.this.getContainer(name.getNamespaceURI());
         this.checkContainerNotNull(container, name);
         SchemaAttributeGroupImpl impl = new SchemaAttributeGroupImpl(container);

         SchemaAttributeGroupImpl var4;
         try {
            impl.init(name, this.readString(), this.readShort() == 1, this.atLeast(2, 22, 0) ? this.readString() : null, this.atLeast(2, 15, 0) ? this.readShort() == 1 : false, AttributeGroupDocument.Factory.parse(this.readString()).getAttributeGroup(), this.readAnnotation(container), (Object)null);
            if (this.atLeast(2, 21, 0)) {
               impl.setFilename(this.readString());
            }

            var4 = impl;
         } catch (SchemaTypeLoaderException var9) {
            throw var9;
         } catch (Exception var10) {
            throw new SchemaTypeLoaderException("Cannot load type from typesystem", SchemaTypeSystemImpl.this._name, this._handle, 14, var10);
         } finally {
            this.readEnd();
         }

         return var4;
      }

      public SchemaType finishLoadingType() {
         SchemaTypeImpl var30;
         try {
            SchemaContainer cNonNull = SchemaTypeSystemImpl.this.getContainerNonNull("");
            SchemaTypeImpl impl = new SchemaTypeImpl(cNonNull, true);
            impl.setName(this.readQName());
            impl.setOuterSchemaTypeRef(this.readTypeRef());
            impl.setBaseDepth(this.readShort());
            impl.setBaseTypeRef(this.readTypeRef());
            impl.setDerivationType(this.readShort());
            impl.setAnnotation(this.readAnnotation((SchemaContainer)null));
            switch (this.readShort()) {
               case 1:
                  impl.setContainerFieldRef(this.readHandle());
                  break;
               case 2:
                  impl.setContainerFieldIndex((short)1, this.readShort());
                  break;
               case 3:
                  impl.setContainerFieldIndex((short)2, this.readShort());
            }

            String jn = this.readString();
            impl.setFullJavaName(jn == null ? "" : jn);
            jn = this.readString();
            impl.setFullJavaImplName(jn == null ? "" : jn);
            impl.setAnonymousTypeRefs(this.readTypeRefArray());
            impl.setAnonymousUnionMemberOrdinal(this.readShort());
            int flags = this.readInt();
            boolean isComplexType = (flags & 1) == 0;
            impl.setCompiled((flags & 2048) != 0);
            impl.setDocumentType((flags & 2) != 0);
            impl.setAttributeType((flags & 524288) != 0);
            impl.setSimpleType(!isComplexType);
            int complexVariety = 0;
            int ix;
            int isAll;
            int ixx;
            if (isComplexType) {
               impl.setAbstractFinal((flags & 262144) != 0, (flags & 16384) != 0, (flags & '耀') != 0, (flags & 131072) != 0, (flags & 65536) != 0);
               impl.setBlock((flags & 4096) != 0, (flags & 8192) != 0);
               impl.setOrderSensitive((flags & 512) != 0);
               complexVariety = this.readShort();
               impl.setComplexTypeVariety(complexVariety);
               if (this.atLeast(2, 23, 0)) {
                  impl.setContentBasedOnTypeRef(this.readTypeRef());
               }

               SchemaAttributeModelImpl attrModel = new SchemaAttributeModelImpl();
               int attrCount = this.readShort();

               for(int i = 0; i < attrCount; ++i) {
                  attrModel.addAttribute(this.readAttributeData());
               }

               attrModel.setWildcardSet(this.readQNameSet());
               attrModel.setWildcardProcess(this.readShort());
               Map attrProperties = new LinkedHashMap();
               int attrPropCount = this.readShort();

               for(ix = 0; ix < attrPropCount; ++ix) {
                  SchemaProperty prop = this.readPropertyData();
                  if (!prop.isAttribute()) {
                     throw new SchemaTypeLoaderException("Attribute property " + ix + " is not an attribute", SchemaTypeSystemImpl.this._name, this._handle, 6);
                  }

                  attrProperties.put(prop.getName(), prop);
               }

               SchemaParticle contentModel = null;
               Map elemProperties = null;
               isAll = 0;
               if (complexVariety == 3 || complexVariety == 4) {
                  isAll = this.readShort();
                  SchemaParticle[] parts = this.readParticleArray();
                  if (parts.length == 1) {
                     contentModel = parts[0];
                  } else {
                     if (parts.length != 0) {
                        throw new SchemaTypeLoaderException("Content model not well-formed", SchemaTypeSystemImpl.this._name, this._handle, 7);
                     }

                     contentModel = null;
                  }

                  elemProperties = new LinkedHashMap();
                  int elemPropCount = this.readShort();

                  for(ixx = 0; ixx < elemPropCount; ++ixx) {
                     SchemaProperty propx = this.readPropertyData();
                     if (propx.isAttribute()) {
                        throw new SchemaTypeLoaderException("Element property " + ixx + " is not an element", SchemaTypeSystemImpl.this._name, this._handle, 6);
                     }

                     elemProperties.put(propx.getName(), propx);
                  }
               }

               impl.setContentModel(contentModel, attrModel, elemProperties, attrProperties, isAll == 1);
               StscComplexTypeResolver.WildcardResult wcElt = StscComplexTypeResolver.summarizeEltWildcards(contentModel);
               StscComplexTypeResolver.WildcardResult wcAttr = StscComplexTypeResolver.summarizeAttrWildcards(attrModel);
               impl.setWildcardSummary(wcElt.typedWildcards, wcElt.hasWildcards, wcAttr.typedWildcards, wcAttr.hasWildcards);
            }

            if (!isComplexType || complexVariety == 2) {
               int simpleVariety = this.readShort();
               impl.setSimpleTypeVariety(simpleVariety);
               boolean isStringEnum = (flags & 64) != 0;
               impl.setOrdered((flags & 4) != 0 ? 0 : ((flags & 1024) != 0 ? 2 : 1));
               impl.setBounded((flags & 8) != 0);
               impl.setFinite((flags & 16) != 0);
               impl.setNumeric((flags & 32) != 0);
               impl.setUnionOfLists((flags & 128) != 0);
               impl.setSimpleFinal((flags & '耀') != 0, (flags & 131072) != 0, (flags & 65536) != 0);
               XmlValueRef[] facets = new XmlValueRef[12];
               boolean[] fixedFacets = new boolean[12];
               ix = this.readShort();

               int patternCount;
               for(patternCount = 0; patternCount < ix; ++patternCount) {
                  isAll = this.readShort();
                  facets[isAll] = this.readXmlValueObject();
                  fixedFacets[isAll] = this.readShort() == 1;
               }

               impl.setBasicFacets(facets, fixedFacets);
               impl.setWhiteSpaceRule(this.readShort());
               impl.setPatternFacet((flags & 256) != 0);
               patternCount = this.readShort();
               RegularExpression[] patterns = new RegularExpression[patternCount];

               int enumCount;
               for(enumCount = 0; enumCount < patternCount; ++enumCount) {
                  patterns[enumCount] = new RegularExpression(this.readString(), "X");
               }

               impl.setPatterns(patterns);
               enumCount = this.readShort();
               XmlValueRef[] enumValues = new XmlValueRef[enumCount];

               for(ixx = 0; ixx < enumCount; ++ixx) {
                  enumValues[ixx] = this.readXmlValueObject();
               }

               impl.setEnumerationValues(enumCount == 0 ? null : enumValues);
               impl.setBaseEnumTypeRef(this.readTypeRef());
               if (isStringEnum) {
                  ixx = this.readShort();
                  SchemaStringEnumEntry[] entries = new SchemaStringEnumEntry[ixx];

                  for(int ixxx = 0; ixxx < ixx; ++ixxx) {
                     entries[ixxx] = new SchemaStringEnumEntryImpl(this.readString(), this.readShort(), this.readString());
                  }

                  impl.setStringEnumEntries(entries);
               }

               switch (simpleVariety) {
                  case 1:
                     impl.setPrimitiveTypeRef(this.readTypeRef());
                     impl.setDecimalSize(this.readInt());
                     break;
                  case 2:
                     impl.setPrimitiveTypeRef(BuiltinSchemaTypeSystem.ST_ANY_SIMPLE.getRef());
                     impl.setUnionMemberTypeRefs(this.readTypeRefArray());
                     break;
                  case 3:
                     impl.setPrimitiveTypeRef(BuiltinSchemaTypeSystem.ST_ANY_SIMPLE.getRef());
                     impl.setListItemTypeRef(this.readTypeRef());
                     break;
                  default:
                     throw new SchemaTypeLoaderException("Simple type does not have a recognized variety", SchemaTypeSystemImpl.this._name, this._handle, 8);
               }
            }

            impl.setFilename(this.readString());
            if (impl.getName() != null) {
               SchemaContainer container = SchemaTypeSystemImpl.this.getContainer(impl.getName().getNamespaceURI());
               this.checkContainerNotNull(container, impl.getName());
               impl.setContainer(container);
            } else {
               QName name;
               SchemaContainer containerx;
               if (impl.isDocumentType()) {
                  name = impl.getDocumentElementName();
                  if (name != null) {
                     containerx = SchemaTypeSystemImpl.this.getContainer(name.getNamespaceURI());
                     this.checkContainerNotNull(containerx, name);
                     impl.setContainer(containerx);
                  }
               } else if (impl.isAttributeType()) {
                  name = impl.getAttributeTypeAttributeName();
                  if (name != null) {
                     containerx = SchemaTypeSystemImpl.this.getContainer(name.getNamespaceURI());
                     this.checkContainerNotNull(containerx, name);
                     impl.setContainer(containerx);
                  }
               }
            }

            var30 = impl;
         } catch (SchemaTypeLoaderException var23) {
            throw var23;
         } catch (Exception var24) {
            throw new SchemaTypeLoaderException("Cannot load type from typesystem", SchemaTypeSystemImpl.this._name, this._handle, 14, var24);
         } finally {
            this.readEnd();
         }

         return var30;
      }

      void writeTypeData(SchemaType type) {
         this.writeQName(type.getName());
         this.writeType(type.getOuterType());
         this.writeShort(((SchemaTypeImpl)type).getBaseDepth());
         this.writeType(type.getBaseType());
         this.writeShort(type.getDerivationType());
         this.writeAnnotation(type.getAnnotation());
         if (type.getContainerField() == null) {
            this.writeShort(0);
         } else if (!type.getOuterType().isAttributeType() && !type.getOuterType().isDocumentType()) {
            if (type.getContainerField().isAttribute()) {
               this.writeShort(2);
               this.writeShort(((SchemaTypeImpl)type.getOuterType()).getIndexForLocalAttribute((SchemaLocalAttribute)type.getContainerField()));
            } else {
               this.writeShort(3);
               this.writeShort(((SchemaTypeImpl)type.getOuterType()).getIndexForLocalElement((SchemaLocalElement)type.getContainerField()));
            }
         } else {
            this.writeShort(1);
            this.writeHandle((SchemaComponent)type.getContainerField());
         }

         this.writeString(type.getFullJavaName());
         this.writeString(type.getFullJavaImplName());
         this.writeTypeArray(type.getAnonymousTypes());
         this.writeShort(type.getAnonymousUnionMemberOrdinal());
         int flags = 0;
         if (type.isSimpleType()) {
            flags |= 1;
         }

         if (type.isDocumentType()) {
            flags |= 2;
         }

         if (type.isAttributeType()) {
            flags |= 524288;
         }

         if (type.ordered() != 0) {
            flags |= 4;
         }

         if (type.ordered() == 2) {
            flags |= 1024;
         }

         if (type.isBounded()) {
            flags |= 8;
         }

         if (type.isFinite()) {
            flags |= 16;
         }

         if (type.isNumeric()) {
            flags |= 32;
         }

         if (type.hasStringEnumValues()) {
            flags |= 64;
         }

         if (((SchemaTypeImpl)type).isUnionOfLists()) {
            flags |= 128;
         }

         if (type.hasPatternFacet()) {
            flags |= 256;
         }

         if (type.isOrderSensitive()) {
            flags |= 512;
         }

         if (type.blockExtension()) {
            flags |= 4096;
         }

         if (type.blockRestriction()) {
            flags |= 8192;
         }

         if (type.finalExtension()) {
            flags |= 16384;
         }

         if (type.finalRestriction()) {
            flags |= 16384;
         }

         if (type.finalList()) {
            flags |= 131072;
         }

         if (type.finalUnion()) {
            flags |= 65536;
         }

         if (type.isAbstract()) {
            flags |= 262144;
         }

         this.writeInt(flags);
         int i;
         int ixx;
         if (!type.isSimpleType()) {
            this.writeShort(type.getContentType());
            this.writeType(type.getContentBasedOnType());
            SchemaAttributeModel attrModel = type.getAttributeModel();
            SchemaLocalAttribute[] attrs = attrModel.getAttributes();
            this.writeShort(attrs.length);

            for(i = 0; i < attrs.length; ++i) {
               this.writeAttributeData(attrs[i]);
            }

            this.writeQNameSet(attrModel.getWildcardSet());
            this.writeShort(attrModel.getWildcardProcess());
            SchemaProperty[] attrProperties = type.getAttributeProperties();
            this.writeShort(attrProperties.length);

            for(ixx = 0; ixx < attrProperties.length; ++ixx) {
               this.writePropertyData(attrProperties[ixx]);
            }

            if (type.getContentType() == 3 || type.getContentType() == 4) {
               this.writeShort(type.hasAllContent() ? 1 : 0);
               SchemaParticle[] parts;
               if (type.getContentModel() != null) {
                  parts = new SchemaParticle[]{type.getContentModel()};
               } else {
                  parts = new SchemaParticle[0];
               }

               this.writeParticleArray(parts);
               SchemaProperty[] eltProperties = type.getElementProperties();
               this.writeShort(eltProperties.length);

               for(int ixxx = 0; ixxx < eltProperties.length; ++ixxx) {
                  this.writePropertyData(eltProperties[ixxx]);
               }
            }
         }

         if (type.isSimpleType() || type.getContentType() == 2) {
            this.writeShort(type.getSimpleVariety());
            int facetCount = 0;

            int ixxxx;
            for(ixxxx = 0; ixxxx <= 11; ++ixxxx) {
               if (type.getFacet(ixxxx) != null) {
                  ++facetCount;
               }
            }

            this.writeShort(facetCount);

            for(ixxxx = 0; ixxxx <= 11; ++ixxxx) {
               XmlAnySimpleType facet = type.getFacet(ixxxx);
               if (facet != null) {
                  this.writeShort(ixxxx);
                  this.writeXmlValueObject(facet);
                  this.writeShort(type.isFacetFixed(ixxxx) ? 1 : 0);
               }
            }

            this.writeShort(type.getWhiteSpaceRule());
            RegularExpression[] patterns = ((SchemaTypeImpl)type).getPatternExpressions();
            this.writeShort(patterns.length);

            for(i = 0; i < patterns.length; ++i) {
               this.writeString(patterns[i].getPattern());
            }

            XmlAnySimpleType[] enumValues = type.getEnumerationValues();
            if (enumValues == null) {
               this.writeShort(0);
            } else {
               this.writeShort(enumValues.length);

               for(ixx = 0; ixx < enumValues.length; ++ixx) {
                  this.writeXmlValueObject(enumValues[ixx]);
               }
            }

            this.writeType(type.getBaseEnumType());
            if (type.hasStringEnumValues()) {
               SchemaStringEnumEntry[] entries = type.getStringEnumEntries();
               this.writeShort(entries.length);

               for(int ix = 0; ix < entries.length; ++ix) {
                  this.writeString(entries[ix].getString());
                  this.writeShort(entries[ix].getIntValue());
                  this.writeString(entries[ix].getEnumName());
               }
            }

            switch (type.getSimpleVariety()) {
               case 1:
                  this.writeType(type.getPrimitiveType());
                  this.writeInt(type.getDecimalSize());
                  break;
               case 2:
                  this.writeTypeArray(type.getUnionMemberTypes());
                  break;
               case 3:
                  this.writeType(type.getListItemType());
            }
         }

         this.writeString(type.getSourceName());
      }

      void readExtensionsList() {
         int count = this.readShort();

         assert count == 0;

         for(int i = 0; i < count; ++i) {
            this.readString();
            this.readString();
            this.readString();
         }

      }

      SchemaLocalAttribute readAttributeData() {
         SchemaLocalAttributeImpl result = new SchemaLocalAttributeImpl();
         this.loadAttribute(result, this.readQName(), (SchemaContainer)null);
         return result;
      }

      void loadAttribute(SchemaLocalAttributeImpl result, QName name, SchemaContainer container) {
         result.init(name, this.readTypeRef(), this.readShort(), this.readString(), (XmlObject)null, this.atLeast(2, 16, 0) ? this.readXmlValueObject() : null, this.readShort() == 1, this.readSOAPArrayType(), this.readAnnotation(container), (Object)null);
      }

      void writeAttributeData(SchemaLocalAttribute attr) {
         this.writeQName(attr.getName());
         this.writeType(attr.getType());
         this.writeShort(attr.getUse());
         this.writeString(attr.getDefaultText());
         this.writeXmlValueObject(attr.getDefaultValue());
         this.writeShort(attr.isFixed() ? 1 : 0);
         this.writeSOAPArrayType(((SchemaWSDLArrayType)attr).getWSDLArrayType());
         this.writeAnnotation(attr.getAnnotation());
      }

      void writeIdConstraintData(SchemaIdentityConstraint idc) {
         this.writeQName(idc.getName());
         this.writeShort(idc.getConstraintCategory());
         this.writeString(idc.getSelector());
         this.writeAnnotation(idc.getAnnotation());
         String[] fields = idc.getFields();
         this.writeShort(fields.length);

         for(int i = 0; i < fields.length; ++i) {
            this.writeString(fields[i]);
         }

         if (idc.getConstraintCategory() == 2) {
            this.writeHandle(idc.getReferencedKey());
         }

         Set mappings = idc.getNSMap().entrySet();
         this.writeShort(mappings.size());
         Iterator it = mappings.iterator();

         while(it.hasNext()) {
            Map.Entry e = (Map.Entry)it.next();
            String prefix = (String)e.getKey();
            String uri = (String)e.getValue();
            this.writeString(prefix);
            this.writeString(uri);
         }

         this.writeString(idc.getSourceName());
      }

      SchemaParticle[] readParticleArray() {
         SchemaParticle[] result = new SchemaParticle[this.readShort()];

         for(int i = 0; i < result.length; ++i) {
            result[i] = this.readParticleData();
         }

         return result;
      }

      void writeParticleArray(SchemaParticle[] spa) {
         this.writeShort(spa.length);

         for(int i = 0; i < spa.length; ++i) {
            this.writeParticleData(spa[i]);
         }

      }

      SchemaParticle readParticleData() {
         int particleType = this.readShort();
         Object result;
         if (particleType != 4) {
            result = new SchemaParticleImpl();
         } else {
            result = new SchemaLocalElementImpl();
         }

         this.loadParticle((SchemaParticleImpl)result, particleType);
         return (SchemaParticle)result;
      }

      void loadParticle(SchemaParticleImpl result, int particleType) {
         int particleFlags = this.readShort();
         result.setParticleType(particleType);
         result.setMinOccurs(this.readBigInteger());
         result.setMaxOccurs(this.readBigInteger());
         result.setTransitionRules(this.readQNameSet(), (particleFlags & 1) != 0);
         switch (particleType) {
            case 1:
            case 2:
            case 3:
               result.setParticleChildren(this.readParticleArray());
               break;
            case 4:
               SchemaLocalElementImpl lresult = (SchemaLocalElementImpl)result;
               lresult.setNameAndTypeRef(this.readQName(), this.readTypeRef());
               lresult.setDefault(this.readString(), (particleFlags & 4) != 0, (XmlObject)null);
               if (this.atLeast(2, 16, 0)) {
                  lresult.setDefaultValue(this.readXmlValueObject());
               }

               lresult.setNillable((particleFlags & 8) != 0);
               lresult.setBlock((particleFlags & 16) != 0, (particleFlags & 32) != 0, (particleFlags & 64) != 0);
               lresult.setWsdlArrayType(this.readSOAPArrayType());
               lresult.setAbstract((particleFlags & 128) != 0);
               lresult.setAnnotation(this.readAnnotation((SchemaContainer)null));
               SchemaIdentityConstraint.Ref[] idcs = new SchemaIdentityConstraint.Ref[this.readShort()];

               for(int i = 0; i < idcs.length; ++i) {
                  idcs[i] = (SchemaIdentityConstraint.Ref)this.readHandle();
               }

               lresult.setIdentityConstraints(idcs);
               break;
            case 5:
               result.setWildcardSet(this.readQNameSet());
               result.setWildcardProcess(this.readShort());
               break;
            default:
               throw new SchemaTypeLoaderException("Unrecognized particle type ", SchemaTypeSystemImpl.this._name, this._handle, 11);
         }

      }

      void writeParticleData(SchemaParticle part) {
         this.writeShort(part.getParticleType());
         short flags = 0;
         if (part.isSkippable()) {
            flags = (short)(flags | 1);
         }

         SchemaLocalElement lpart;
         SchemaGlobalElement gpart;
         if (part.getParticleType() == 4) {
            lpart = (SchemaLocalElement)part;
            if (lpart.isFixed()) {
               flags = (short)(flags | 4);
            }

            if (lpart.isNillable()) {
               flags = (short)(flags | 8);
            }

            if (lpart.blockExtension()) {
               flags = (short)(flags | 16);
            }

            if (lpart.blockRestriction()) {
               flags = (short)(flags | 32);
            }

            if (lpart.blockSubstitution()) {
               flags = (short)(flags | 64);
            }

            if (lpart.isAbstract()) {
               flags = (short)(flags | 128);
            }

            if (lpart instanceof SchemaGlobalElement) {
               gpart = (SchemaGlobalElement)lpart;
               if (gpart.finalExtension()) {
                  flags = (short)(flags | 256);
               }

               if (gpart.finalRestriction()) {
                  flags = (short)(flags | 512);
               }
            }
         }

         this.writeShort(flags);
         this.writeBigInteger(part.getMinOccurs());
         this.writeBigInteger(part.getMaxOccurs());
         this.writeQNameSet(part.acceptedStartNames());
         switch (part.getParticleType()) {
            case 1:
            case 2:
            case 3:
               this.writeParticleArray(part.getParticleChildren());
               break;
            case 4:
               lpart = (SchemaLocalElement)part;
               this.writeQName(lpart.getName());
               this.writeType(lpart.getType());
               this.writeString(lpart.getDefaultText());
               this.writeXmlValueObject(lpart.getDefaultValue());
               this.writeSOAPArrayType(((SchemaWSDLArrayType)lpart).getWSDLArrayType());
               this.writeAnnotation(lpart.getAnnotation());
               if (lpart instanceof SchemaGlobalElement) {
                  gpart = (SchemaGlobalElement)lpart;
                  this.writeHandle(gpart.substitutionGroup());
                  QName[] substGroupMembers = gpart.substitutionGroupMembers();
                  this.writeShort(substGroupMembers.length);

                  for(int i = 0; i < substGroupMembers.length; ++i) {
                     this.writeQName(substGroupMembers[i]);
                  }
               }

               SchemaIdentityConstraint[] idcs = lpart.getIdentityConstraints();
               this.writeShort(idcs.length);

               for(int ix = 0; ix < idcs.length; ++ix) {
                  this.writeHandle(idcs[ix]);
               }

               return;
            case 5:
               this.writeQNameSet(part.getWildcardSet());
               this.writeShort(part.getWildcardProcess());
               break;
            default:
               throw new SchemaTypeLoaderException("Unrecognized particle type ", SchemaTypeSystemImpl.this._name, this._handle, 11);
         }

      }

      SchemaProperty readPropertyData() {
         SchemaPropertyImpl prop = new SchemaPropertyImpl();
         prop.setName(this.readQName());
         prop.setTypeRef(this.readTypeRef());
         int propflags = this.readShort();
         prop.setAttribute((propflags & 1) != 0);
         prop.setContainerTypeRef(this.readTypeRef());
         prop.setMinOccurs(this.readBigInteger());
         prop.setMaxOccurs(this.readBigInteger());
         prop.setNillable(this.readShort());
         prop.setDefault(this.readShort());
         prop.setFixed(this.readShort());
         prop.setDefaultText(this.readString());
         prop.setJavaPropertyName(this.readString());
         prop.setJavaTypeCode(this.readShort());
         prop.setExtendsJava(this.readTypeRef(), (propflags & 2) != 0, (propflags & 4) != 0, (propflags & 8) != 0);
         if (this.atMost(2, 19, 0)) {
            prop.setJavaSetterDelimiter(this.readQNameSet());
         }

         if (this.atLeast(2, 16, 0)) {
            prop.setDefaultValue(this.readXmlValueObject());
         }

         if (!prop.isAttribute() && this.atLeast(2, 17, 0)) {
            int size = this.readShort();
            LinkedHashSet qnames = new LinkedHashSet(size);

            for(int i = 0; i < size; ++i) {
               qnames.add(this.readQName());
            }

            prop.setAcceptedNames((Set)qnames);
         }

         prop.setImmutable();
         return prop;
      }

      void writePropertyData(SchemaProperty prop) {
         this.writeQName(prop.getName());
         this.writeType(prop.getType());
         this.writeShort((prop.isAttribute() ? 1 : 0) | (prop.extendsJavaSingleton() ? 2 : 0) | (prop.extendsJavaOption() ? 4 : 0) | (prop.extendsJavaArray() ? 8 : 0));
         this.writeType(prop.getContainerType());
         this.writeBigInteger(prop.getMinOccurs());
         this.writeBigInteger(prop.getMaxOccurs());
         this.writeShort(prop.hasNillable());
         this.writeShort(prop.hasDefault());
         this.writeShort(prop.hasFixed());
         this.writeString(prop.getDefaultText());
         this.writeString(prop.getJavaPropertyName());
         this.writeShort(prop.getJavaTypeCode());
         this.writeType(prop.javaBasedOnType());
         this.writeXmlValueObject(prop.getDefaultValue());
         if (!prop.isAttribute()) {
            QName[] names = prop.acceptedNames();
            this.writeShort(names.length);

            for(int i = 0; i < names.length; ++i) {
               this.writeQName(names[i]);
            }
         }

      }

      void writeModelGroupData(SchemaModelGroup grp) {
         SchemaModelGroupImpl impl = (SchemaModelGroupImpl)grp;
         this.writeQName(impl.getName());
         this.writeString(impl.getTargetNamespace());
         this.writeShort(impl.getChameleonNamespace() != null ? 1 : 0);
         this.writeString(impl.getElemFormDefault());
         this.writeString(impl.getAttFormDefault());
         this.writeShort(impl.isRedefinition() ? 1 : 0);
         this.writeString(impl.getParseObject().xmlText((new XmlOptions()).setSaveOuter()));
         this.writeAnnotation(impl.getAnnotation());
         this.writeString(impl.getSourceName());
      }

      void writeAttributeGroupData(SchemaAttributeGroup grp) {
         SchemaAttributeGroupImpl impl = (SchemaAttributeGroupImpl)grp;
         this.writeQName(impl.getName());
         this.writeString(impl.getTargetNamespace());
         this.writeShort(impl.getChameleonNamespace() != null ? 1 : 0);
         this.writeString(impl.getFormDefault());
         this.writeShort(impl.isRedefinition() ? 1 : 0);
         this.writeString(impl.getParseObject().xmlText((new XmlOptions()).setSaveOuter()));
         this.writeAnnotation(impl.getAnnotation());
         this.writeString(impl.getSourceName());
      }

      XmlValueRef readXmlValueObject() {
         SchemaType.Ref typeref = this.readTypeRef();
         if (typeref == null) {
            return null;
         } else {
            int btc = this.readShort();
            switch (btc) {
               case 2:
               case 3:
               case 6:
               case 11:
               case 12:
               case 13:
               case 14:
               case 15:
               case 16:
               case 17:
               case 18:
               case 19:
               case 20:
               case 21:
                  return new XmlValueRef(typeref, this.readString());
               case 4:
               case 5:
                  return new XmlValueRef(typeref, this.readByteArray());
               case 7:
               case 8:
                  return new XmlValueRef(typeref, this.readQName());
               case 9:
               case 10:
                  return new XmlValueRef(typeref, new Double(this.readDouble()));
               case 65535:
                  int size = this.readShort();
                  List values = new ArrayList();
                  this.writeShort(values.size());

                  for(int i = 0; i < size; ++i) {
                     values.add(this.readXmlValueObject());
                  }

                  return new XmlValueRef(typeref, values);
               default:
                  assert false;
               case 0:
                  return new XmlValueRef(typeref, (Object)null);
            }
         }
      }

      void writeXmlValueObject(XmlAnySimpleType value) {
         SchemaType type = value == null ? null : value.schemaType();
         this.writeType(type);
         if (type != null) {
            SchemaType iType = ((SimpleValue)value).instanceType();
            if (iType == null) {
               this.writeShort(0);
            } else if (iType.getSimpleVariety() == 3) {
               this.writeShort(-1);
               List values = ((XmlObjectBase)value).xgetListValue();
               this.writeShort(values.size());
               Iterator i = values.iterator();

               while(i.hasNext()) {
                  this.writeXmlValueObject((XmlAnySimpleType)i.next());
               }
            } else {
               int btc = iType.getPrimitiveType().getBuiltinTypeCode();
               this.writeShort(btc);
               switch (btc) {
                  case 2:
                  case 3:
                  case 6:
                  case 11:
                  case 12:
                  case 13:
                  case 14:
                  case 15:
                  case 16:
                  case 17:
                  case 18:
                  case 19:
                  case 20:
                  case 21:
                     this.writeString(value.getStringValue());
                     break;
                  case 4:
                  case 5:
                     this.writeByteArray(((SimpleValue)value).getByteArrayValue());
                     break;
                  case 7:
                  case 8:
                     this.writeQName(((SimpleValue)value).getQNameValue());
                     break;
                  case 9:
                     this.writeDouble((double)((SimpleValue)value).getFloatValue());
                     break;
                  case 10:
                     this.writeDouble(((SimpleValue)value).getDoubleValue());
               }
            }

         }
      }

      double readDouble() {
         try {
            return this._input.readDouble();
         } catch (IOException var2) {
            throw new SchemaTypeLoaderException(var2.getMessage(), SchemaTypeSystemImpl.this._name, this._handle, 9);
         }
      }

      void writeDouble(double d) {
         if (this._output != null) {
            try {
               this._output.writeDouble(d);
            } catch (IOException var4) {
               throw new SchemaTypeLoaderException(var4.getMessage(), SchemaTypeSystemImpl.this._name, this._handle, 9);
            }
         }

      }

      QNameSet readQNameSet() {
         int flag = this.readShort();
         Set uriSet = new HashSet();
         int uriCount = this.readShort();

         for(int ixx = 0; ixx < uriCount; ++ixx) {
            uriSet.add(this.readString());
         }

         Set qnameSet1 = new HashSet();
         int qncount1 = this.readShort();

         for(int i = 0; i < qncount1; ++i) {
            qnameSet1.add(this.readQName());
         }

         Set qnameSet2 = new HashSet();
         int qncount2 = this.readShort();

         for(int ix = 0; ix < qncount2; ++ix) {
            qnameSet2.add(this.readQName());
         }

         return flag == 1 ? QNameSet.forSets(uriSet, (Set)null, qnameSet1, qnameSet2) : QNameSet.forSets((Set)null, uriSet, qnameSet2, qnameSet1);
      }

      void writeQNameSet(QNameSet set) {
         boolean invert = set.excludedURIs() != null;
         this.writeShort(invert ? 1 : 0);
         Set uriSet = invert ? set.excludedURIs() : set.includedURIs();
         this.writeShort(uriSet.size());
         Iterator ix = uriSet.iterator();

         while(ix.hasNext()) {
            this.writeString((String)ix.next());
         }

         Set qnameSet1 = invert ? set.excludedQNamesInIncludedURIs() : set.includedQNamesInExcludedURIs();
         this.writeShort(qnameSet1.size());
         Iterator ixx = qnameSet1.iterator();

         while(ixx.hasNext()) {
            this.writeQName((QName)ixx.next());
         }

         Set qnameSet2 = invert ? set.includedQNamesInExcludedURIs() : set.excludedQNamesInIncludedURIs();
         this.writeShort(qnameSet2.size());
         Iterator i = qnameSet2.iterator();

         while(i.hasNext()) {
            this.writeQName((QName)i.next());
         }

      }

      byte[] readByteArray() {
         try {
            int len = this._input.readShort();
            byte[] result = new byte[len];
            this._input.readFully(result);
            return result;
         } catch (IOException var3) {
            throw new SchemaTypeLoaderException(var3.getMessage(), SchemaTypeSystemImpl.this._name, this._handle, 9);
         }
      }

      void writeByteArray(byte[] ba) {
         try {
            this.writeShort(ba.length);
            if (this._output != null) {
               this._output.write(ba);
            }

         } catch (IOException var3) {
            throw new SchemaTypeLoaderException(var3.getMessage(), SchemaTypeSystemImpl.this._name, this._handle, 9);
         }
      }

      BigInteger readBigInteger() {
         byte[] result = this.readByteArray();
         if (result.length == 0) {
            return null;
         } else if (result.length == 1 && result[0] == 0) {
            return BigInteger.ZERO;
         } else {
            return result.length == 1 && result[0] == 1 ? BigInteger.ONE : new BigInteger(result);
         }
      }

      void writeBigInteger(BigInteger bi) {
         if (bi == null) {
            this.writeShort(0);
         } else if (bi.signum() == 0) {
            this.writeByteArray(SchemaTypeSystemImpl.SINGLE_ZERO_BYTE);
         } else {
            this.writeByteArray(bi.toByteArray());
         }

      }
   }

   class HandlePool {
      private Map _handlesToRefs = new LinkedHashMap();
      private Map _componentsToHandles = new LinkedHashMap();
      private boolean _started;

      private String addUniqueHandle(SchemaComponent obj, String base) {
         base = base.toLowerCase();
         String handle = base;

         for(int index = 2; this._handlesToRefs.containsKey(handle); ++index) {
            handle = base + index;
         }

         this._handlesToRefs.put(handle, obj.getComponentRef());
         this._componentsToHandles.put(obj, handle);
         return handle;
      }

      String handleForComponent(SchemaComponent comp) {
         if (comp == null) {
            return null;
         } else if (comp.getTypeSystem() != SchemaTypeSystemImpl.this.getTypeSystem()) {
            throw new IllegalArgumentException("Cannot supply handles for types from another type system");
         } else if (comp instanceof SchemaType) {
            return this.handleForType((SchemaType)comp);
         } else if (comp instanceof SchemaGlobalElement) {
            return this.handleForElement((SchemaGlobalElement)comp);
         } else if (comp instanceof SchemaGlobalAttribute) {
            return this.handleForAttribute((SchemaGlobalAttribute)comp);
         } else if (comp instanceof SchemaModelGroup) {
            return this.handleForModelGroup((SchemaModelGroup)comp);
         } else if (comp instanceof SchemaAttributeGroup) {
            return this.handleForAttributeGroup((SchemaAttributeGroup)comp);
         } else if (comp instanceof SchemaIdentityConstraint) {
            return this.handleForIdentityConstraint((SchemaIdentityConstraint)comp);
         } else {
            throw new IllegalStateException("Component type cannot have a handle");
         }
      }

      String handleForElement(SchemaGlobalElement element) {
         if (element == null) {
            return null;
         } else if (element.getTypeSystem() != SchemaTypeSystemImpl.this.getTypeSystem()) {
            throw new IllegalArgumentException("Cannot supply handles for types from another type system");
         } else {
            String handle = (String)this._componentsToHandles.get(element);
            if (handle == null) {
               handle = this.addUniqueHandle(element, NameUtil.upperCamelCase(element.getName().getLocalPart()) + "Element");
            }

            return handle;
         }
      }

      String handleForAttribute(SchemaGlobalAttribute attribute) {
         if (attribute == null) {
            return null;
         } else if (attribute.getTypeSystem() != SchemaTypeSystemImpl.this.getTypeSystem()) {
            throw new IllegalArgumentException("Cannot supply handles for types from another type system");
         } else {
            String handle = (String)this._componentsToHandles.get(attribute);
            if (handle == null) {
               handle = this.addUniqueHandle(attribute, NameUtil.upperCamelCase(attribute.getName().getLocalPart()) + "Attribute");
            }

            return handle;
         }
      }

      String handleForModelGroup(SchemaModelGroup group) {
         if (group == null) {
            return null;
         } else if (group.getTypeSystem() != SchemaTypeSystemImpl.this.getTypeSystem()) {
            throw new IllegalArgumentException("Cannot supply handles for types from another type system");
         } else {
            String handle = (String)this._componentsToHandles.get(group);
            if (handle == null) {
               handle = this.addUniqueHandle(group, NameUtil.upperCamelCase(group.getName().getLocalPart()) + "ModelGroup");
            }

            return handle;
         }
      }

      String handleForAttributeGroup(SchemaAttributeGroup group) {
         if (group == null) {
            return null;
         } else if (group.getTypeSystem() != SchemaTypeSystemImpl.this.getTypeSystem()) {
            throw new IllegalArgumentException("Cannot supply handles for types from another type system");
         } else {
            String handle = (String)this._componentsToHandles.get(group);
            if (handle == null) {
               handle = this.addUniqueHandle(group, NameUtil.upperCamelCase(group.getName().getLocalPart()) + "AttributeGroup");
            }

            return handle;
         }
      }

      String handleForIdentityConstraint(SchemaIdentityConstraint idc) {
         if (idc == null) {
            return null;
         } else if (idc.getTypeSystem() != SchemaTypeSystemImpl.this.getTypeSystem()) {
            throw new IllegalArgumentException("Cannot supply handles for types from another type system");
         } else {
            String handle = (String)this._componentsToHandles.get(idc);
            if (handle == null) {
               handle = this.addUniqueHandle(idc, NameUtil.upperCamelCase(idc.getName().getLocalPart()) + "IdentityConstraint");
            }

            return handle;
         }
      }

      String handleForType(SchemaType type) {
         if (type == null) {
            return null;
         } else if (type.getTypeSystem() != SchemaTypeSystemImpl.this.getTypeSystem()) {
            throw new IllegalArgumentException("Cannot supply handles for types from another type system");
         } else {
            String handle = (String)this._componentsToHandles.get(type);
            if (handle == null) {
               QName name = type.getName();
               String suffix = "";
               if (name == null) {
                  if (type.isDocumentType()) {
                     name = type.getDocumentElementName();
                     suffix = "Doc";
                  } else if (type.isAttributeType()) {
                     name = type.getAttributeTypeAttributeName();
                     suffix = "AttrType";
                  } else if (type.getContainerField() != null) {
                     name = type.getContainerField().getName();
                     suffix = type.getContainerField().isAttribute() ? "Attr" : "Elem";
                  }
               }

               String uniq = Integer.toHexString(type.toString().hashCode() | Integer.MIN_VALUE).substring(4).toUpperCase();
               String baseName;
               if (name == null) {
                  baseName = "Anon" + uniq + "Type";
               } else {
                  baseName = NameUtil.upperCamelCase(name.getLocalPart()) + uniq + suffix + "Type";
               }

               handle = this.addUniqueHandle(type, baseName);
            }

            return handle;
         }
      }

      SchemaComponent.Ref refForHandle(String handle) {
         return handle == null ? null : (SchemaComponent.Ref)this._handlesToRefs.get(handle);
      }

      Set getAllHandles() {
         return this._handlesToRefs.keySet();
      }

      void startWriteMode() {
         this._started = true;
         this._componentsToHandles = new LinkedHashMap();
         Iterator i = this._handlesToRefs.keySet().iterator();

         while(i.hasNext()) {
            String handle = (String)i.next();
            SchemaComponent comp = ((SchemaComponent.Ref)this._handlesToRefs.get(handle)).getComponent();
            this._componentsToHandles.put(comp, handle);
         }

      }
   }

   static class StringPool {
      private List intsToStrings = new ArrayList();
      private Map stringsToInts = new HashMap();
      private String _handle;
      private String _name;

      StringPool(String handle, String name) {
         this._handle = handle;
         this._name = name;
         this.intsToStrings.add((Object)null);
      }

      int codeForString(String str) {
         if (str == null) {
            return 0;
         } else {
            Integer result = (Integer)this.stringsToInts.get(str);
            if (result == null) {
               result = new Integer(this.intsToStrings.size());
               this.intsToStrings.add(str);
               this.stringsToInts.put(str, result);
            }

            return result;
         }
      }

      String stringForCode(int code) {
         return code == 0 ? null : (String)this.intsToStrings.get(code);
      }

      void writeTo(DataOutputStream output) {
         if (this.intsToStrings.size() >= 65535) {
            throw new SchemaTypeLoaderException("Too many strings (" + this.intsToStrings.size() + ")", this._name, this._handle, 10);
         } else {
            try {
               output.writeShort(this.intsToStrings.size());
               Iterator i = this.intsToStrings.iterator();
               i.next();

               while(i.hasNext()) {
                  String str = (String)i.next();
                  output.writeUTF(str);
               }

            } catch (IOException var4) {
               throw new SchemaTypeLoaderException(var4.getMessage(), this._name, this._handle, 9);
            }
         }
      }

      void readFrom(DataInputStream input) {
         if (this.intsToStrings.size() == 1 && this.stringsToInts.size() == 0) {
            try {
               int size = input.readUnsignedShort();

               for(int i = 1; i < size; ++i) {
                  String str = input.readUTF().intern();
                  int code = this.codeForString(str);
                  if (code != i) {
                     throw new IllegalStateException();
                  }
               }

            } catch (IOException var6) {
               throw new SchemaTypeLoaderException(var6.getMessage() == null ? var6.getMessage() : "IO Exception", this._name, this._handle, 9, var6);
            }
         } else {
            throw new IllegalStateException();
         }
      }
   }
}
