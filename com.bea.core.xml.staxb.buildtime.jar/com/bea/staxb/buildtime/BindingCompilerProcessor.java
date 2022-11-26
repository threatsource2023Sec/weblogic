package com.bea.staxb.buildtime;

import com.bea.staxb.buildtime.internal.logger.MessageSink;
import com.bea.staxb.buildtime.internal.tylar.Tylar;
import com.bea.xbean.schema.SoapEncSchemaTypeSystem;
import com.bea.xbean.util.FilerImpl;
import com.bea.xbean.xb.xsdschema.SchemaDocument;
import com.bea.xml.BindingConfig;
import com.bea.xml.SchemaTypeLoader;
import com.bea.xml.SchemaTypeSystem;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import org.xml.sax.EntityResolver;

public abstract class BindingCompilerProcessor {
   private static final SchemaTypeLoader SCHEMA_LOADER = XmlBeans.typeLoaderForClassLoader(SchemaDocument.class.getClassLoader());
   private String typeSystemName = System.getProperty("SchemaTypeSystemName");
   private File destDir = null;
   private File destJar = null;
   private boolean verbose = false;
   private boolean ignoreErrors = false;
   private long lastFreeMemory = 0L;
   private BuildtimeLogger logger;

   public static SchemaTypeSystem createSchemaTypeSystem(SchemaDocument[] xsds) throws XmlException {
      return createSchemaTypeSystem(xsds, (SchemaTypeLoader)null);
   }

   public static SchemaTypeSystem createSchemaTypeSystem(SchemaDocument[] xsds, SchemaTypeLoader base) throws XmlException {
      return createSchemaTypeSystem(xsds, (FilerImpl)null, (String)null, (EntityResolver)null, base, (URI)null);
   }

   public static SchemaTypeSystem createSchemaTypeSystem(SchemaDocument[] xsds, FilerImpl filer, String typeSystemName, EntityResolver resolver, SchemaTypeLoader base, URI baseURI) throws XmlException {
      SchemaTypeLoader soapencLoader = SoapEncSchemaTypeSystem.get();
      SchemaTypeLoader xsdLoader = XmlBeans.getBuiltinTypeSystem();
      SchemaTypeLoader[] loaders;
      if (base != null) {
         loaders = new SchemaTypeLoader[]{xsdLoader, soapencLoader, base};
      } else {
         loaders = new SchemaTypeLoader[]{xsdLoader, soapencLoader};
      }

      XmlOptions xo = new XmlOptions();
      xo.setCompileNoAnnotations();
      if (resolver != null) {
         xo.setEntityResolver(resolver);
      }

      if (baseURI != null) {
         xo.setBaseURI(baseURI);
      }

      return XmlBeans.compileXmlBeans(typeSystemName, (SchemaTypeSystem)null, xsds, (BindingConfig)null, XmlBeans.typeLoaderUnion(loaders), filer, xo);
   }

   public static SchemaDocument parseSchemaFile(File file) throws IOException, XmlException {
      XmlOptions options = new XmlOptions();
      options.setLoadLineNumbers();
      options.setLoadMessageDigest();
      return (SchemaDocument)SCHEMA_LOADER.parse(file, SchemaDocument.type, options);
   }

   public void process(MessageSink messageSink, BuildtimeLogger buildtimeLogger) throws IOException, XmlException {
      this.logger = buildtimeLogger;
      if (this.logger.isVerbose()) {
         this.logMemory("Free memory before BindingCompilerTask   ");
      }

      if (this.getDestination() == null) {
         throw new BindingCompilerNoDestinationException();
      } else {
         BindingCompiler bc = this.getCompilerToExecute();
         bc.setIgnoreSevereErrors(this.ignoreErrors);
         bc.setMessageSink(messageSink);
         bc.setVerbose(this.verbose);
         Tylar tylar = this.destDir != null ? bc.bindAsExplodedTylar(this.destDir) : bc.bindAsJarredTylar(this.destJar);
         if (this.logger.isVerbose()) {
            this.logMemory("Free memory after BindingCompilerTask   ");
            this.cleanupCompiler();
            this.logMemory("Free memory after BindingCompilerTask GC");
         }

         if (tylar == null) {
            throw new BindingCompilerException("fatal errors encountered, see log for details.");
         } else {
            this.logger.logInfo("binding task complete, output at " + this.getDestination());
         }
      }
   }

   void logMemory(String msg) {
      Runtime.getRuntime().gc();
      long freeMemory = Runtime.getRuntime().freeMemory();
      this.logger.logVerbose(msg + ":\t " + freeMemory + "\tdelta:" + (this.lastFreeMemory - freeMemory));
      this.lastFreeMemory = freeMemory;
   }

   File getDestination() {
      return this.destDir != null ? this.destDir : this.destJar;
   }

   void setIgnoreErrors(boolean ignoreErrors) {
      this.ignoreErrors = ignoreErrors;
   }

   void setVerbose(boolean verbose) {
      this.verbose = verbose;
   }

   protected boolean isVerbose() {
      return this.verbose;
   }

   public void setDestinationJar(File jar) {
      if (this.destDir != null) {
         throw new DuplicateDestinationException();
      } else {
         this.destJar = jar;
      }
   }

   public void setDestinationDirectory(File dir) {
      if (this.destJar != null) {
         throw new DuplicateDestinationException();
      } else {
         this.destDir = dir;
      }
   }

   public String getTypeSystemName() {
      return this.typeSystemName;
   }

   public void setTypeSystemName(String typeSystemName) {
      this.typeSystemName = typeSystemName.replace('.', '_');
   }

   protected abstract void cleanupCompiler();

   public void setLogger(BuildtimeLogger logger) {
      this.logger = logger;
   }

   protected abstract BindingCompiler getCompilerToExecute() throws IOException, XmlException;

   public BuildtimeLogger getLogger() {
      return this.logger;
   }

   public String toString() {
      return "BindingCompilerOptions { destDir = " + (this.destDir == null ? null : this.destDir.getAbsolutePath()) + ", destJar = " + (this.destJar == null ? null : this.destJar.getAbsolutePath()) + ", typeSystemName = " + this.typeSystemName + "}";
   }
}
