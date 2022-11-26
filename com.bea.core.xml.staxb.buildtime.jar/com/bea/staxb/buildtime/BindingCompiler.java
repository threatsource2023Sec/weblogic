package com.bea.staxb.buildtime;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.bts.BuiltinBindingLoader;
import com.bea.staxb.buildtime.internal.bts.CompositeBindingLoader;
import com.bea.staxb.buildtime.internal.joust.JavaOutputStream;
import com.bea.staxb.buildtime.internal.logger.BindingLogger;
import com.bea.staxb.buildtime.internal.mbean.TypeMatcherContext;
import com.bea.staxb.buildtime.internal.tylar.DefaultTylarLoader;
import com.bea.staxb.buildtime.internal.tylar.ExplodedTylar;
import com.bea.staxb.buildtime.internal.tylar.ExplodedTylarImpl;
import com.bea.staxb.buildtime.internal.tylar.Tylar;
import com.bea.staxb.buildtime.internal.tylar.TylarWriter;
import com.bea.util.jam.JamClassLoader;
import com.bea.util.jam.JamServiceFactory;
import com.bea.xbean.xb.xsdschema.SchemaDocument;
import com.bea.xml.SchemaTypeLoader;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BindingCompiler extends BindingLogger implements TypeMatcherContext {
   private Tylar mBaseTylar = null;
   private boolean mIsCompilationStarted = false;
   private List mSchemasToInclude = null;
   protected boolean mJaxRpcRules = false;
   protected boolean mJaxRPCWrappedArrayStyle = true;
   BindingLoader cachedLoader = null;

   protected abstract void internalBind(TylarWriter var1);

   protected ExplodedTylarImpl createDefaultExplodedTylarImpl(File destDir) throws IOException {
      return ExplodedTylarImpl.create(destDir, (JavaOutputStream)null);
   }

   public final void bind(TylarWriter writer) {
      if (writer == null) {
         throw new IllegalArgumentException("null writer");
      } else if (this.isAnyErrorsFound()) {
         this.logError("initialization errors encountered, skipping compilation");
      } else {
         this.mIsCompilationStarted = true;
         this.internalBind(writer);
         if (this.mSchemasToInclude != null) {
            for(int i = 0; i < this.mSchemasToInclude.size(); ++i) {
               SchemaToInclude sti = (SchemaToInclude)this.mSchemasToInclude.get(i);

               try {
                  writer.writeSchema(sti.schema, sti.filepath, sti.tns2prefix);
               } catch (IOException var5) {
                  this.logError(var5);
               }
            }
         }

      }
   }

   public ExplodedTylar bindAsExplodedTylar(File tylarDestDir) {
      if (tylarDestDir == null) {
         throw new IllegalArgumentException("null dir");
      } else if (this.isAnyErrorsFound()) {
         this.logError("initialization errors encountered, skipping compilation");
         return null;
      } else {
         ExplodedTylarImpl tylar;
         try {
            tylar = this.createDefaultExplodedTylarImpl(tylarDestDir);
         } catch (IOException var5) {
            this.logError(var5);
            return null;
         }

         if (!tylarDestDir.exists() && !tylarDestDir.mkdirs()) {
            this.logError("failed to create " + tylarDestDir);
            return null;
         } else {
            this.bind(tylar);

            try {
               tylar.close();
            } catch (IOException var4) {
               this.logError(var4);
            }

            return super.isAnyErrorsFound() && !super.isIgnoreErrors() ? null : tylar;
         }
      }
   }

   public Tylar bindAsJarredTylar(File tylarJar) {
      if (this.isAnyErrorsFound()) {
         this.logError("initialization errors encountered, skipping compilation");
         return null;
      } else {
         File tempDir = null;

         try {
            tempDir = createTempDir();
            tempDir.deleteOnExit();
            ExplodedTylar et = this.bindAsExplodedTylar(tempDir);
            if (et == null) {
               this.logError("Fatal error encountered building tylar.");
               return null;
            } else {
               return et.toJar(tylarJar);
            }
         } catch (IOException var4) {
            this.logError(var4);
            return null;
         }
      }
   }

   public void setBaseLibrary(Tylar lib) {
      if (lib == null) {
         throw new IllegalArgumentException("null lib");
      } else {
         this.mBaseTylar = lib;
      }
   }

   public void setBaseLibraries(File[] files) throws IOException {
      if (files == null) {
         throw new IllegalArgumentException("null files");
      } else {
         URL[] urls = new URL[files.length];

         for(int i = 0; i < urls.length; ++i) {
            try {
               urls[i] = files[i].toURL();
            } catch (MalformedURLException var6) {
               throw new IllegalStateException(var6.getMessage());
            }
         }

         URLClassLoader ucl = new URLClassLoader(urls);

         try {
            this.setBaseLibrary(DefaultTylarLoader.getInstance().load(ucl));
         } catch (XmlException var5) {
            throw new IOException(var5);
         }
      }
   }

   public void setExcludeLibraries(File[] files) throws IOException {
      if (files == null) {
         throw new IllegalArgumentException("null files");
      } else {
         URL[] urls = new URL[files.length];

         for(int i = 0; i < urls.length; ++i) {
            try {
               urls[i] = files[i].toURL();
            } catch (MalformedURLException var5) {
               throw new IllegalStateException(var5.getMessage());
            }
         }

         if (this.mBaseTylar != null) {
            this.mBaseTylar.setExcludeLocations(urls);
         }

      }
   }

   public void setJaxRpcRules(boolean b) {
      this.assertCompilationStarted(false);
      this.mJaxRpcRules = b;
   }

   public void setJaxRPCWrappedArrayStyle(boolean b) {
      this.mJaxRPCWrappedArrayStyle = b;
   }

   public void includeSchema(SchemaDocument xsd, String filepath, Map tns2prefix) {
      if (xsd == null) {
         throw new IllegalArgumentException("null xsd");
      } else if (filepath == null) {
         throw new IllegalArgumentException("null filepath");
      } else {
         if (this.mSchemasToInclude == null) {
            this.mSchemasToInclude = new ArrayList();
         }

         this.mSchemasToInclude.add(new SchemaToInclude(xsd, filepath, tns2prefix));
      }
   }

   public void setIgnoreSevereErrors(boolean really) {
      this.assertCompilationStarted(false);
      super.setIgnoreErrors(really);
   }

   public void setVerbose(boolean b) {
      this.assertCompilationStarted(false);
      super.setVerbose(b);
   }

   public BindingLogger getLogger() {
      return this;
   }

   public BindingLoader getBaseBindingLoader() {
      this.assertCompilationStarted(true);
      if (this.cachedLoader != null) {
         return this.cachedLoader;
      } else {
         BindingLoader builtin = BuiltinBindingLoader.getBuiltinBindingLoader(this.mJaxRpcRules);
         if (this.mBaseTylar == null) {
            this.cachedLoader = builtin;
            return this.cachedLoader;
         } else {
            BindingLoader[] loaders = null;

            try {
               loaders = new BindingLoader[]{this.mBaseTylar.getBindingLoader(), builtin};
            } catch (Exception var4) {
               this.logError(var4);
            }

            if (loaders == null) {
               this.cachedLoader = builtin;
            } else {
               this.cachedLoader = CompositeBindingLoader.forPath(loaders);
            }

            return this.cachedLoader;
         }
      }
   }

   public SchemaTypeLoader getBaseSchemaTypeLoader() {
      this.assertCompilationStarted(true);
      if (this.mBaseTylar != null) {
         try {
            return this.mBaseTylar.getSchemaTypeLoader();
         } catch (IOException var2) {
            this.logError(var2);
         } catch (XmlException var3) {
            this.logError(var3);
         }
      }

      return XmlBeans.getBuiltinTypeSystem();
   }

   public JamClassLoader getBaseJavaTypeLoader() {
      this.assertCompilationStarted(true);
      return this.mBaseTylar == null ? JamServiceFactory.getInstance().createSystemJamClassLoader() : this.mBaseTylar.getJamClassLoader();
   }

   protected void assertCompilationStarted(boolean isStarted) {
      if (this.mIsCompilationStarted != isStarted) {
         throw new IllegalStateException("This method cannot be invoked " + (this.mIsCompilationStarted ? "after" : "before") + " binding compilation has begun");
      }
   }

   private static File createTempDir() throws IOException {
      String prefix = "BindingCompiler-" + System.currentTimeMillis();

      File tempFile;
      try {
         tempFile = File.createTempFile(prefix, (String)null);
      } catch (IOException var4) {
         throw new IOException("Unable to create file in temporary directory");
      }

      File tempDir = tempFile.getParentFile();
      tempFile.delete();
      return new File(tempDir, prefix);
   }

   private class SchemaToInclude {
      SchemaDocument schema;
      String filepath;
      Map tns2prefix;

      SchemaToInclude(SchemaDocument sd, String fp, Map t2p) {
         this.schema = sd;
         this.filepath = fp;
         this.tns2prefix = t2p;
      }
   }
}
