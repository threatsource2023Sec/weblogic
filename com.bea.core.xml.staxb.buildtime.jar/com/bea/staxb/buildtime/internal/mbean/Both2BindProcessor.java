package com.bea.staxb.buildtime.internal.mbean;

import com.bea.staxb.buildtime.BindingCompiler;
import com.bea.staxb.buildtime.BindingCompilerException;
import com.bea.staxb.buildtime.JavaBindProcessor;
import com.bea.staxb.buildtime.internal.tylar.DefaultTylarLoader;
import com.bea.staxb.buildtime.internal.tylar.Tylar;
import com.bea.util.jam.JClass;
import com.bea.util.jam.JamServiceFactory;
import com.bea.util.jam.JamServiceParams;
import com.bea.xbean.schema.SoapEncSchemaTypeSystem;
import com.bea.xbean.util.FilerImpl;
import com.bea.xbean.xb.xsdschema.SchemaDocument;
import com.bea.xml.BindingConfig;
import com.bea.xml.ResourceLoader;
import com.bea.xml.SchemaTypeLoader;
import com.bea.xml.SchemaTypeSystem;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.xml.sax.EntityResolver;
import repackage.Repackager;

public class Both2BindProcessor extends JavaBindProcessor {
   private static final String SCHEMA_DIR = "META-INF/schemas/";
   private static final String SCHEMA_DIR_LOWER = "meta-inf/schemas/";
   private static final int SCHEMA_DIR_LEN = "META-INF/schemas/".length();
   private File[] bindingJarFiles;
   private File[] javaFiles;
   private File[] xsdFiles;
   private URI baseURI;
   private File generatedDir = null;
   private boolean generateDefaultXmlBeans = false;
   private FilerImpl filer;
   private Both2Bind compiler = new Both2Bind();
   private EntityResolver entityResolver = null;
   private SchemaTypeLoader schemaTypeLoader = null;
   private Tylar tylar = null;
   private File[] baseLibraries = new File[0];

   public void setEntityResolver(EntityResolver entityResolver) {
      this.entityResolver = entityResolver;
   }

   public void setTypeMatcher(TypeMatcher matcher) {
      this.compiler.setTypeMatcher(matcher);
   }

   void setBaseURI(String paramBaseURI) {
      this.baseURI = paramBaseURI == null ? null : (new File(paramBaseURI)).toURI();
   }

   void setGeneratedDir(File generatedDir) {
      this.generatedDir = generatedDir;
   }

   void setGenerateDefaultXmlBeans(boolean generateDefaultXmlBeans) {
      this.generateDefaultXmlBeans = generateDefaultXmlBeans;
   }

   public void setBaseLibraries(File[] baseLibraries) {
      this.baseLibraries = baseLibraries;
   }

   public void setBindingJarFiles(File[] bindingJarFiles) {
      this.bindingJarFiles = bindingJarFiles;
   }

   public void setJavaFiles(File[] javaFiles) {
      this.javaFiles = javaFiles;
   }

   public void setXsdFiles(File[] xsdFiles) {
      this.xsdFiles = xsdFiles;
   }

   protected void cleanupCompiler() {
      this.compiler = null;
      this.entityResolver = null;
   }

   protected BindingCompiler getCompilerToExecute() throws IOException, XmlException {
      this.initLoaders();
      Tylar baseTylar = this.getBaseTylar();
      if (baseTylar != null) {
         this.compiler.setBaseLibrary(this.getBaseTylar());
      }

      if (this.generatedDir != null && this.generateDefaultXmlBeans) {
         this.filer = new FilerImpl(this.generatedDir, this.generatedDir, (Repackager)null, this.isVerbose(), false);
      }

      ArrayList schemasDocsFromXSDs = new ArrayList();
      File[] var3 = this.xsdFiles;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         File xsdFile = var3[var5];
         this.getLogger().logDebug("Compiling Schema " + xsdFile);
         SchemaDocument schemaDocument = parseSchemaFile(xsdFile);
         schemasDocsFromXSDs.add(schemaDocument);
         this.compiler.includeSchema(schemaDocument, xsdFile.getName(), (Map)null);
      }

      SchemaDocument[] schemaDocArray = (SchemaDocument[])schemasDocsFromXSDs.toArray(new SchemaDocument[schemasDocsFromXSDs.size()]);
      SchemaTypeSystem schemaTypeSystem = this.entityResolver != null ? createSchemaTypeSystem(schemaDocArray, this.filer, this.getTypeSystemName(), this.entityResolver) : createSchemaTypeSystem(schemaDocArray, this.filer, this.getTypeSystemName(), this.createEntityResolverFromJars(), this.getSchemaLoader(), this.baseURI);
      this.compiler.setSchemaTypesToMatch(schemaTypeSystem);
      this.compiler.setJavaTypesToMatch(this.loadJClasses(this.javaFiles));
      return this.compiler;
   }

   private static SchemaTypeSystem createSchemaTypeSystem(SchemaDocument[] xsds, FilerImpl filer, String typeSystemName, EntityResolver er) throws XmlException {
      if (er == null) {
         return createSchemaTypeSystem(xsds);
      } else {
         SchemaTypeLoader soapencLoader = SoapEncSchemaTypeSystem.get();
         SchemaTypeLoader xsdLoader = XmlBeans.getBuiltinTypeSystem();
         XmlOptions xo = new XmlOptions();
         xo.setEntityResolver(er);
         xo.setCompileNoAnnotations();
         return XmlBeans.compileXmlBeans(typeSystemName, (SchemaTypeSystem)null, xsds, (BindingConfig)null, XmlBeans.typeLoaderUnion(new SchemaTypeLoader[]{xsdLoader, soapencLoader}), filer, xo);
      }
   }

   private JClass[] loadJClasses(File[] javaFiles) throws IOException {
      JamServiceFactory factory = JamServiceFactory.getInstance();
      JamServiceParams params = factory.createServiceParams();
      File[] var4 = javaFiles;
      int var5 = javaFiles.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         File javaFile = var4[var6];
         params.includeSourceFile(javaFile);
      }

      this.addSourcepathElements(params);
      this.addClasspathElements(params);
      return factory.createService(params).getAllClasses();
   }

   private EntityResolver createEntityResolverFromJars() throws IOException {
      if (this.bindingJarFiles != null && this.bindingJarFiles.length != 0) {
         SchemaEntityResolver resolver = new SchemaEntityResolver(this);
         File[] var2 = this.bindingJarFiles;
         int var3 = var2.length;

         label33:
         for(int var4 = 0; var4 < var3; ++var4) {
            File bindingJarFile = var2[var4];
            JarFile bindingJar = new JarFile(bindingJarFile);
            Enumeration entries = bindingJar.entries();
            XmlOptions options = new XmlOptions();
            options.setLoadLineNumbers();
            options.setLoadMessageDigest();

            while(true) {
               JarEntry entry;
               String entryName;
               do {
                  if (!entries.hasMoreElements()) {
                     continue label33;
                  }

                  entry = (JarEntry)entries.nextElement();
                  entryName = entry.getName();
               } while(!entryName.startsWith("META-INF/schemas/") && !entryName.startsWith("meta-inf/schemas/"));

               if (entryName.endsWith(".xsd")) {
                  String shortName = entryName.substring(SCHEMA_DIR_LEN);
                  this.getLogger().logVerbose("Adding schema to resolver:" + shortName + " from " + bindingJarFile.getAbsolutePath());
                  resolver.addSchema(shortName, bindingJar.getInputStream(entry));
               }
            }
         }

         return resolver;
      } else {
         return null;
      }
   }

   private SchemaTypeLoader getSchemaLoader() throws XmlException, IOException {
      return this.schemaTypeLoader;
   }

   private Tylar getBaseTylar() throws XmlException, IOException {
      return this.tylar;
   }

   private void initLoaders() throws XmlException, IOException {
      if (this.baseLibraries.length == 0) {
         this.initializeWithoutLibraries();
      } else {
         this.initializeWithLibraries();
      }

   }

   private void initializeWithoutLibraries() throws IOException, XmlException {
      ClassLoader bindingLoader = SchemaDocument.class.getClassLoader();
      this.schemaTypeLoader = XmlBeans.typeLoaderForClassLoader(bindingLoader);
      this.tylar = DefaultTylarLoader.getInstance().load(bindingLoader);
   }

   private void initializeWithLibraries() throws IOException, XmlException {
      try {
         ResourceLoader resourceLoader = XmlBeans.resourceLoaderForPath(this.baseLibraries);
         this.schemaTypeLoader = XmlBeans.typeLoaderForResource(resourceLoader);
         ClassLoader bindingLoader = new URLClassLoader(this.getBaseLibraryUrls(), ClassLoader.getSystemClassLoader());
         this.tylar = DefaultTylarLoader.getInstance().load(bindingLoader);
      } catch (MalformedURLException var3) {
         throw new BindingCompilerException(var3);
      }
   }

   private URL[] getBaseLibraryUrls() throws MalformedURLException {
      ArrayList urlList = new ArrayList();
      File[] var2 = this.baseLibraries;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         File baseLibrary = var2[var4];
         URL url = baseLibrary.toURI().toURL();
         urlList.add(url);
         this.getLogger().logVerbose("Including Base Binding Jar " + url);
      }

      return (URL[])urlList.toArray(new URL[urlList.size()]);
   }

   public String toString() {
      return "Both2BindProcessor : { entityResolver = " + this.entityResolver + ", typeMatcher = " + this.compiler.getTypeMatcher() + ", baseURI = " + this.baseURI + ", generatedDir = " + (this.generatedDir == null ? null : this.generatedDir.getAbsolutePath()) + ", generateDefaultXmlBeans = " + this.generateDefaultXmlBeans + ", baseLibraries = " + (this.baseLibraries == null ? null : Arrays.toString(this.baseLibraries)) + ", bindingJarFiles = " + (this.bindingJarFiles == null ? null : Arrays.toString(this.bindingJarFiles)) + ", javaFiles = " + (this.javaFiles == null ? null : Arrays.toString(this.javaFiles)) + ", xsdFiles = " + (this.xsdFiles == null ? null : Arrays.toString(this.xsdFiles)) + "\n" + super.toString() + "}";
   }
}
