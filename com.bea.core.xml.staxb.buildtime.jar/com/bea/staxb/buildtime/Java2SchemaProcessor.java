package com.bea.staxb.buildtime;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JamServiceFactory;
import com.bea.util.jam.JamServiceParams;
import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

public class Java2SchemaProcessor extends JavaBindProcessor {
   private String includes = null;
   private Java2Schema java2Schema;
   private String inputEncoding = null;
   private Namespace[] namespaces;
   private File sourceRoot;

   public Java2SchemaProcessor(Java2Schema java2Schema) {
      this.java2Schema = java2Schema;
   }

   public void setElementFormDefaultQualified(boolean qualified) {
      this.java2Schema.setElementFormDefaultQualified(qualified);
   }

   public void setAttributeFormDefaultQualified(boolean qualified) {
      this.java2Schema.setAttributeFormDefaultQualified(qualified);
   }

   public void setInputEncoding(String encoding) {
      this.inputEncoding = encoding;
   }

   public void setGenerateDocumentation(boolean generate) {
      this.java2Schema.setGenerateDocumentation(generate);
   }

   public void setOrderPropertiesBySource(boolean bySource) {
      this.java2Schema.setOrderPropertiesBySource(bySource);
   }

   public void setIncludes(String includes) {
      if (includes != null && includes.trim().length() != 0) {
         this.includes = includes.trim();
      } else {
         throw new IllegalArgumentException("empty includes");
      }
   }

   public void setNamespaces(Namespace[] namespaces) {
      this.namespaces = namespaces;
   }

   public void setSourceRoot(File sourceRoot) {
      this.sourceRoot = sourceRoot;
   }

   public void setVersion(String version) {
      this.java2Schema.setVersion(version);
   }

   protected void cleanupCompiler() {
      this.java2Schema = null;
   }

   protected BindingCompiler getCompilerToExecute() throws IOException {
      JClass[] var1 = this.getSpecifiedJClasses();
      int var2 = var1.length;

      int var3;
      for(var3 = 0; var3 < var2; ++var3) {
         JClass inputClass = var1[var3];
         this.java2Schema.addClassToBind(inputClass);
      }

      this.java2Schema.setTypeSystemName(this.getTypeSystemName());
      Namespace[] var5 = this.namespaces;
      var2 = var5.length;

      for(var3 = 0; var3 < var2; ++var3) {
         Namespace ns = var5[var3];
         if (ns.getUri() == null) {
            throw new IllegalArgumentException("uri required in <namespace>");
         }

         if (ns.getOutputFile() != null) {
            this.java2Schema.setOutputFileFor(ns.getUri(), ns.getOutputFile());
         }

         if (ns.getPrefix() != null) {
            this.java2Schema.setPrefixFor(ns.getUri(), ns.getPrefix());
         }

         if (ns.getSchemaLocation() != null) {
            this.java2Schema.setLocationFor(ns.getUri(), ns.getSchemaLocation());
         }
      }

      return this.java2Schema;
   }

   private JClass[] getSpecifiedJClasses() throws IOException {
      if (this.sourceRoot == null) {
         throw new BindingCompilerException("No source root was specified.");
      } else if (this.includes == null) {
         throw new BindingCompilerException("The 'includes' attribute must be set.");
      } else {
         JamServiceFactory jf = JamServiceFactory.getInstance();
         JamServiceParams params = jf.createServiceParams();
         if (this.inputEncoding != null) {
            params.setCharacterEncoding(this.inputEncoding);
         }

         StringTokenizer st = new StringTokenizer(this.includes, ",");

         while(st.hasMoreTokens()) {
            params.includeSourcePattern(new File[]{this.sourceRoot}, st.nextToken().trim());
         }

         this.addSourcepathElements(params);
         this.addClasspathElements(params);
         return jf.createService(params).getAllClasses();
      }
   }
}
