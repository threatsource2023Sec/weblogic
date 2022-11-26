package com.bea.staxb.buildtime;

import com.bea.xbean.xb.xsdschema.SchemaDocument;
import com.bea.xml.XmlException;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Schema2JavaProcessor extends BindingCompilerProcessor {
   private Schema2Java compiler = new Schema2Java();
   private File[] schemaFiles;

   public void setUseJaxRpcRules(boolean jaxRpc) {
      this.compiler.setJaxRpcRules(jaxRpc);
   }

   public void setSchemaFiles(File[] schemaFiles) {
      this.schemaFiles = schemaFiles;
   }

   public void setCompileJava(boolean b) {
      this.compiler.setCompileJava(b);
   }

   public void setPathToJavac(String javacPath) {
      this.compiler.setJavac(javacPath);
   }

   public void setJavacSourceAndTarget(String version) {
      this.compiler.setJavacSourceAndTarget(version);
   }

   public void setJavacClasspath(File[] classpath) {
      this.compiler.setJavacClasspath(classpath);
   }

   public void setKeepGeneratedJava(boolean keepGenerated) {
      this.compiler.setKeepGeneratedJava(keepGenerated);
   }

   protected BindingCompiler getCompilerToExecute() throws IOException, XmlException {
      SchemaDocument[] documents = new SchemaDocument[this.schemaFiles.length];

      for(int i = 0; i < this.schemaFiles.length; ++i) {
         documents[i] = parseSchemaFile(this.schemaFiles[i]);
         this.compiler.includeSchema(documents[i], this.schemaFiles[i].getName(), (Map)null);
      }

      this.compiler.setSchemaTypeSystem(createSchemaTypeSystem(documents));
      return this.compiler;
   }

   protected void cleanupCompiler() {
      this.compiler = null;
   }
}
