package com.bea.xml;

import java.io.File;
import java.lang.ref.SoftReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;

public final class XmlBeans {
   private static String XMLBEANS_TITLE = "com.bea.xml";
   private static String XMLBEANS_VERSION = "2.6.0";
   private static String XMLBEANS_VENDOR = "Apache Software Foundation";
   private static final ThreadLocal _threadLocalLoaderQNameCache;
   private static final Method _getContextTypeLoaderMethod;
   private static final Method _getBuiltinSchemaTypeSystemMethod;
   private static final Method _getNoTypeMethod;
   private static final Method _typeLoaderBuilderMethod;
   private static final Method _compilationMethod;
   private static final Method _nodeToCursorMethod;
   private static final Method _nodeToXmlObjectMethod;
   private static final Method _nodeToXmlStreamMethod;
   private static final Method _streamToNodeMethod;
   private static final Constructor _pathResourceLoaderConstructor;
   private static final String HOLDER_CLASS_NAME = "TypeSystemHolder";
   private static final String TYPE_SYSTEM_FIELD = "typeSystem";
   public static SchemaType NO_TYPE;

   public static final String getTitle() {
      return XMLBEANS_TITLE;
   }

   public static final String getVendor() {
      return XMLBEANS_VENDOR;
   }

   public static final String getVersion() {
      return XMLBEANS_VERSION;
   }

   public static QNameCache getQNameCache() {
      SoftReference softRef = (SoftReference)_threadLocalLoaderQNameCache.get();
      QNameCache qnameCache = (QNameCache)softRef.get();
      if (qnameCache == null) {
         qnameCache = new QNameCache(32);
         _threadLocalLoaderQNameCache.set(new SoftReference(qnameCache));
      }

      return qnameCache;
   }

   public static QName getQName(String localPart) {
      return getQNameCache().getName("", localPart);
   }

   public static QName getQName(String namespaceUri, String localPart) {
      return getQNameCache().getName(namespaceUri, localPart);
   }

   private static RuntimeException causedException(RuntimeException e, Throwable cause) {
      e.initCause(cause);
      return e;
   }

   private static XmlException wrappedException(Throwable e) {
      return e instanceof XmlException ? (XmlException)e : new XmlException(e.getMessage(), e);
   }

   private static final Constructor buildConstructor(String className, Class[] args) {
      try {
         return Class.forName(className, false, XmlBeans.class.getClassLoader()).getConstructor(args);
      } catch (Exception var3) {
         throw causedException(new IllegalStateException("Cannot load constructor for " + className + ": verify that xbean.jar is on the classpath"), var3);
      }
   }

   private static final Method buildMethod(String className, String methodName, Class[] args) {
      try {
         return Class.forName(className, false, XmlBeans.class.getClassLoader()).getMethod(methodName, args);
      } catch (Exception var4) {
         throw causedException(new IllegalStateException("Cannot load " + methodName + ": verify that xbean.jar is on the classpath"), var4);
      }
   }

   private static final Method buildNoArgMethod(String className, String methodName) {
      return buildMethod(className, methodName, new Class[0]);
   }

   private static final Method buildNodeMethod(String className, String methodName) {
      return buildMethod(className, methodName, new Class[]{Node.class});
   }

   private static Method buildGetContextTypeLoaderMethod() {
      return buildNoArgMethod("com.bea.xbean.schema.SchemaTypeLoaderImpl", "getContextTypeLoader");
   }

   private static final Method buildGetBuiltinSchemaTypeSystemMethod() {
      return buildNoArgMethod("com.bea.xbean.schema.BuiltinSchemaTypeSystem", "get");
   }

   private static final Method buildGetNoTypeMethod() {
      return buildNoArgMethod("com.bea.xbean.schema.BuiltinSchemaTypeSystem", "getNoType");
   }

   private static final Method buildTypeLoaderBuilderMethod() {
      return buildMethod("com.bea.xbean.schema.SchemaTypeLoaderImpl", "build", new Class[]{SchemaTypeLoader[].class, ResourceLoader.class, ClassLoader.class});
   }

   private static final Method buildCompilationMethod() {
      return buildMethod("com.bea.xbean.schema.SchemaTypeSystemCompiler", "compile", new Class[]{String.class, SchemaTypeSystem.class, XmlObject[].class, BindingConfig.class, SchemaTypeLoader.class, Filer.class, XmlOptions.class});
   }

   private static final Method buildNodeToCursorMethod() {
      return buildNodeMethod("com.bea.xbean.store.Locale", "nodeToCursor");
   }

   private static final Method buildNodeToXmlObjectMethod() {
      return buildNodeMethod("com.bea.xbean.store.Locale", "nodeToXmlObject");
   }

   private static final Method buildNodeToXmlStreamMethod() {
      return buildNodeMethod("com.bea.xbean.store.Locale", "nodeToXmlStream");
   }

   private static final Method buildStreamToNodeMethod() {
      return buildMethod("com.bea.xbean.store.Locale", "streamToNode", new Class[]{XMLStreamReader.class});
   }

   private static final Constructor buildPathResourceLoaderConstructor() {
      return buildConstructor("com.bea.xbean.schema.PathResourceLoader", new Class[]{File[].class});
   }

   public static String compilePath(String pathExpr) throws XmlException {
      return compilePath(pathExpr, (XmlOptions)null);
   }

   public static String compilePath(String pathExpr, XmlOptions options) throws XmlException {
      return getContextTypeLoader().compilePath(pathExpr, options);
   }

   public static String compileQuery(String queryExpr) throws XmlException {
      return compileQuery(queryExpr, (XmlOptions)null);
   }

   public static String compileQuery(String queryExpr, XmlOptions options) throws XmlException {
      return getContextTypeLoader().compileQuery(queryExpr, options);
   }

   public static SchemaTypeLoader getContextTypeLoader() {
      try {
         return (SchemaTypeLoader)_getContextTypeLoaderMethod.invoke((Object)null, (Object[])null);
      } catch (IllegalAccessException var3) {
         throw causedException(new IllegalStateException("No access to SchemaTypeLoaderImpl.getContextTypeLoader(): verify that version of xbean.jar is correct"), var3);
      } catch (InvocationTargetException var4) {
         Throwable t = var4.getCause();
         IllegalStateException ise = new IllegalStateException(t.getMessage());
         ise.initCause(t);
         throw ise;
      }
   }

   public static SchemaTypeSystem getBuiltinTypeSystem() {
      try {
         return (SchemaTypeSystem)_getBuiltinSchemaTypeSystemMethod.invoke((Object)null, (Object[])null);
      } catch (IllegalAccessException var3) {
         throw causedException(new IllegalStateException("No access to BuiltinSchemaTypeSystem.get(): verify that version of xbean.jar is correct"), var3);
      } catch (InvocationTargetException var4) {
         Throwable t = var4.getCause();
         IllegalStateException ise = new IllegalStateException(t.getMessage());
         ise.initCause(t);
         throw ise;
      }
   }

   public static XmlCursor nodeToCursor(Node n) {
      try {
         return (XmlCursor)_nodeToCursorMethod.invoke((Object)null, n);
      } catch (IllegalAccessException var4) {
         throw causedException(new IllegalStateException("No access to nodeToCursor verify that version of xbean.jar is correct"), var4);
      } catch (InvocationTargetException var5) {
         Throwable t = var5.getCause();
         IllegalStateException ise = new IllegalStateException(t.getMessage());
         ise.initCause(t);
         throw ise;
      }
   }

   public static XmlObject nodeToXmlObject(Node n) {
      try {
         return (XmlObject)_nodeToXmlObjectMethod.invoke((Object)null, n);
      } catch (IllegalAccessException var4) {
         throw causedException(new IllegalStateException("No access to nodeToXmlObject verify that version of xbean.jar is correct"), var4);
      } catch (InvocationTargetException var5) {
         Throwable t = var5.getCause();
         IllegalStateException ise = new IllegalStateException(t.getMessage());
         ise.initCause(t);
         throw ise;
      }
   }

   public static XMLStreamReader nodeToXmlStreamReader(Node n) {
      try {
         return (XMLStreamReader)_nodeToXmlStreamMethod.invoke((Object)null, n);
      } catch (IllegalAccessException var4) {
         throw causedException(new IllegalStateException("No access to nodeToXmlStreamReader verify that version of xbean.jar is correct"), var4);
      } catch (InvocationTargetException var5) {
         Throwable t = var5.getCause();
         IllegalStateException ise = new IllegalStateException(t.getMessage());
         ise.initCause(t);
         throw ise;
      }
   }

   public static Node streamToNode(XMLStreamReader xs) {
      try {
         return (Node)_streamToNodeMethod.invoke((Object)null, xs);
      } catch (IllegalAccessException var4) {
         throw causedException(new IllegalStateException("No access to streamToNode verify that version of xbean.jar is correct"), var4);
      } catch (InvocationTargetException var5) {
         Throwable t = var5.getCause();
         IllegalStateException ise = new IllegalStateException(t.getMessage());
         ise.initCause(t);
         throw ise;
      }
   }

   public static SchemaTypeLoader loadXsd(XmlObject[] schemas) throws XmlException {
      return loadXsd(schemas, (XmlOptions)null);
   }

   public static SchemaTypeLoader loadXsd(XmlObject[] schemas, XmlOptions options) throws XmlException {
      try {
         SchemaTypeSystem sts = (SchemaTypeSystem)_compilationMethod.invoke((Object)null, null, null, schemas, null, getContextTypeLoader(), null, options);
         return sts == null ? null : typeLoaderUnion(new SchemaTypeLoader[]{sts, getContextTypeLoader()});
      } catch (IllegalAccessException var3) {
         throw causedException(new IllegalStateException("No access to SchemaTypeLoaderImpl.forSchemaXml(): verify that version of xbean.jar is correct"), var3);
      } catch (InvocationTargetException var4) {
         throw wrappedException(var4.getCause());
      }
   }

   public static SchemaTypeSystem compileXsd(XmlObject[] schemas, SchemaTypeLoader typepath, XmlOptions options) throws XmlException {
      return compileXmlBeans((String)null, (SchemaTypeSystem)null, schemas, (BindingConfig)null, typepath, (Filer)null, options);
   }

   public static SchemaTypeSystem compileXsd(SchemaTypeSystem system, XmlObject[] schemas, SchemaTypeLoader typepath, XmlOptions options) throws XmlException {
      return compileXmlBeans((String)null, system, schemas, (BindingConfig)null, typepath, (Filer)null, options);
   }

   public static SchemaTypeSystem compileXmlBeans(String name, SchemaTypeSystem system, XmlObject[] schemas, BindingConfig config, SchemaTypeLoader typepath, Filer filer, XmlOptions options) throws XmlException {
      try {
         return (SchemaTypeSystem)_compilationMethod.invoke((Object)null, name, system, schemas, config, typepath != null ? typepath : getContextTypeLoader(), filer, options);
      } catch (IllegalAccessException var8) {
         throw new IllegalStateException("No access to SchemaTypeLoaderImpl.forSchemaXml(): verify that version of xbean.jar is correct");
      } catch (InvocationTargetException var9) {
         throw wrappedException(var9.getCause());
      }
   }

   public static SchemaTypeLoader typeLoaderUnion(SchemaTypeLoader[] typeLoaders) {
      try {
         return typeLoaders.length == 1 ? typeLoaders[0] : (SchemaTypeLoader)_typeLoaderBuilderMethod.invoke((Object)null, typeLoaders, null, null);
      } catch (IllegalAccessException var4) {
         throw causedException(new IllegalStateException("No access to SchemaTypeLoaderImpl: verify that version of xbean.jar is correct"), var4);
      } catch (InvocationTargetException var5) {
         Throwable t = var5.getCause();
         IllegalStateException ise = new IllegalStateException(t.getMessage());
         ise.initCause(t);
         throw ise;
      }
   }

   public static SchemaTypeLoader typeLoaderForClassLoader(ClassLoader loader) {
      try {
         return (SchemaTypeLoader)_typeLoaderBuilderMethod.invoke((Object)null, null, null, loader);
      } catch (IllegalAccessException var4) {
         throw causedException(new IllegalStateException("No access to SchemaTypeLoaderImpl: verify that version of xbean.jar is correct"), var4);
      } catch (InvocationTargetException var5) {
         Throwable t = var5.getCause();
         IllegalStateException ise = new IllegalStateException(t.getMessage());
         ise.initCause(t);
         throw ise;
      }
   }

   public static SchemaTypeLoader typeLoaderForResource(ResourceLoader resourceLoader) {
      try {
         return (SchemaTypeLoader)_typeLoaderBuilderMethod.invoke((Object)null, null, resourceLoader, null);
      } catch (IllegalAccessException var4) {
         throw causedException(new IllegalStateException("No access to SchemaTypeLoaderImpl: verify that version of xbean.jar is correct"), var4);
      } catch (InvocationTargetException var5) {
         Throwable t = var5.getCause();
         IllegalStateException ise = new IllegalStateException(t.getMessage());
         ise.initCause(t);
         throw ise;
      }
   }

   public static SchemaTypeSystem typeSystemForClassLoader(ClassLoader loader, String stsName) {
      try {
         Class clazz = loader.loadClass(stsName + "." + "TypeSystemHolder");
         SchemaTypeSystem sts = (SchemaTypeSystem)((SchemaTypeSystem)clazz.getDeclaredField("typeSystem").get((Object)null));
         if (sts == null) {
            throw new RuntimeException("SchemaTypeSystem is null for field typeSystem on class with name " + stsName + "." + "TypeSystemHolder" + ". Please verify the version of xbean.jar is correct.");
         } else {
            return sts;
         }
      } catch (ClassNotFoundException var4) {
         throw causedException(new RuntimeException("Cannot load SchemaTypeSystem. Unable to load class with name " + stsName + "." + "TypeSystemHolder" + ". Make sure the generated binary files are on the classpath."), var4);
      } catch (NoSuchFieldException var5) {
         throw causedException(new RuntimeException("Cannot find field typeSystem on class " + stsName + "." + "TypeSystemHolder" + ". Please verify the version of xbean.jar is correct."), var5);
      } catch (IllegalAccessException var6) {
         throw causedException(new RuntimeException("Field typeSystem on class " + stsName + "." + "TypeSystemHolder" + "is not accessible. Please verify the version of xbean.jar is correct."), var6);
      }
   }

   public static ResourceLoader resourceLoaderForPath(File[] path) {
      try {
         return (ResourceLoader)_pathResourceLoaderConstructor.newInstance(path);
      } catch (IllegalAccessException var4) {
         throw causedException(new IllegalStateException("No access to SchemaTypeLoaderImpl: verify that version of xbean.jar is correct"), var4);
      } catch (InstantiationException var5) {
         throw causedException(new IllegalStateException(var5.getMessage()), var5);
      } catch (InvocationTargetException var6) {
         Throwable t = var6.getCause();
         IllegalStateException ise = new IllegalStateException(t.getMessage());
         ise.initCause(t);
         throw ise;
      }
   }

   public static SchemaType typeForClass(Class c) {
      if (c != null && XmlObject.class.isAssignableFrom(c)) {
         try {
            Field typeField = c.getField("type");
            return typeField == null ? null : (SchemaType)typeField.get((Object)null);
         } catch (Exception var2) {
            return null;
         }
      } else {
         return null;
      }
   }

   private static SchemaType getNoType() {
      try {
         return (SchemaType)_getNoTypeMethod.invoke((Object)null, (Object[])null);
      } catch (IllegalAccessException var3) {
         throw causedException(new IllegalStateException("No access to SchemaTypeLoaderImpl.getContextTypeLoader(): verify that version of xbean.jar is correct"), var3);
      } catch (InvocationTargetException var4) {
         Throwable t = var4.getCause();
         IllegalStateException ise = new IllegalStateException(t.getMessage());
         ise.initCause(t);
         throw ise;
      }
   }

   private XmlBeans() {
   }

   static {
      Package pkg = XmlBeans.class.getPackage();
      if (pkg != null) {
         XMLBEANS_TITLE = pkg.getImplementationTitle();
         XMLBEANS_VERSION = pkg.getImplementationVersion();
         XMLBEANS_VENDOR = pkg.getImplementationVendor();
      }

      _threadLocalLoaderQNameCache = new ThreadLocal() {
         protected Object initialValue() {
            return new SoftReference(new QNameCache(32));
         }
      };
      _getContextTypeLoaderMethod = buildGetContextTypeLoaderMethod();
      _getBuiltinSchemaTypeSystemMethod = buildGetBuiltinSchemaTypeSystemMethod();
      _getNoTypeMethod = buildGetNoTypeMethod();
      _typeLoaderBuilderMethod = buildTypeLoaderBuilderMethod();
      _compilationMethod = buildCompilationMethod();
      _nodeToCursorMethod = buildNodeToCursorMethod();
      _nodeToXmlObjectMethod = buildNodeToXmlObjectMethod();
      _nodeToXmlStreamMethod = buildNodeToXmlStreamMethod();
      _streamToNodeMethod = buildStreamToNodeMethod();
      _pathResourceLoaderConstructor = buildPathResourceLoaderConstructor();
      NO_TYPE = getNoType();
   }
}
