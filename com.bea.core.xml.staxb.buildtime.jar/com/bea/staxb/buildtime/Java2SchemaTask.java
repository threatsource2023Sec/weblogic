package com.bea.staxb.buildtime;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.Path;

public class Java2SchemaTask extends JavaBindTask {
   private Path mSrcDir;
   private List mNamespaces;
   private Java2SchemaProcessor processor;

   public Java2SchemaTask() {
      this(new Java2Schema());
   }

   protected Java2SchemaTask(Java2Schema compiler) {
      super(new Java2SchemaProcessor(compiler));
      this.mSrcDir = null;
      this.mNamespaces = new ArrayList();
      this.processor = (Java2SchemaProcessor)super.getProcessor();
   }

   public void setSrcdir(Path srcDir) {
      if (this.mSrcDir == null) {
         this.mSrcDir = srcDir;
      } else {
         this.mSrcDir.append(srcDir);
      }

   }

   public void setIncludes(String includes) {
      this.processor.setIncludes(includes);
   }

   public void setElementFormDefaultQualified(boolean b) {
      this.processor.setElementFormDefaultQualified(b);
   }

   public void setAttributeFormDefaultQualified(boolean b) {
      this.processor.setAttributeFormDefaultQualified(b);
   }

   public void setOrderPropertiesBySource(boolean b) {
      this.processor.setOrderPropertiesBySource(b);
   }

   public void setGenerateDocumentation(boolean b) {
      this.processor.setGenerateDocumentation(b);
   }

   public void setInputEncoding(String encoding) {
      this.processor.setInputEncoding(encoding);
   }

   public void setVersion(String version) {
      this.processor.setVersion(version);
   }

   public Namespace createNamespace() {
      Namespace namespace = new Namespace();
      this.addNamespace(namespace);
      return namespace;
   }

   public void addNamespace(Namespace namespace) {
      this.mNamespaces.add(namespace);
   }

   protected void populateProcessor() {
      super.populateProcessor();
      this.processor.setNamespaces((Namespace[])this.mNamespaces.toArray(new Namespace[this.mNamespaces.size()]));
      this.processor.setSourceRoot(this.getSourceRoot());
   }

   private File getSourceRoot() {
      if (this.mSrcDir == null) {
         throw new BuildException("srcDir attribute required");
      } else {
         String[] list = this.mSrcDir.list();
         if (list.length == 0) {
            throw new BuildException("srcDir attribute required");
         } else if (list.length > 1) {
            throw new BuildException("multiple srcDirs NYI");
         } else {
            return (new File(list[0])).getAbsoluteFile();
         }
      }
   }

   private File[] path2files(Path path) {
      String[] list = path.list();
      File[] out = new File[list.length];

      for(int i = 0; i < out.length; ++i) {
         out[i] = (new File(list[i])).getAbsoluteFile();
      }

      return out;
   }
}
