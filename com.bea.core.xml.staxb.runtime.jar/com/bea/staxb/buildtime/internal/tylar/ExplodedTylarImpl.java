package com.bea.staxb.buildtime.internal.tylar;

import com.bea.ns.staxb.bindingConfig.x90.BindingConfigDocument;
import com.bea.staxb.buildtime.internal.bts.BindingFile;
import com.bea.staxb.buildtime.internal.bts.BindingFileUtils;
import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.bts.BindingMappingFile;
import com.bea.staxb.buildtime.internal.bts.BuiltinBindingLoader;
import com.bea.staxb.buildtime.internal.joust.FileWriterFactory;
import com.bea.staxb.buildtime.internal.joust.JavaOutputStream;
import com.bea.staxb.buildtime.internal.joust.SourceJavaOutputStream;
import com.bea.staxb.buildtime.internal.joust.ValidatingJavaOutputStream;
import com.bea.xbean.common.JarHelper;
import com.bea.xbean.schema.SchemaTypeSystemImpl;
import com.bea.xbean.tool.SchemaCodeGenerator;
import com.bea.xbean.xb.xsdschema.SchemaDocument;
import com.bea.xbean.xb.xsdschema.SchemaDocument.Factory;
import com.bea.xml.SchemaTypeLoader;
import com.bea.xml.SchemaTypeSystem;
import com.bea.xml.XmlException;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import repackage.Repackager;

public class ExplodedTylarImpl extends BaseTylarImpl implements TylarConstants, ExplodedTylar, TylarWriter {
   private static final int XML_INDENT = 2;
   private static final boolean VERBOSE = false;
   private File mRootDir;
   private File mSourceRoot;
   private File mSchemaDir;
   private BindingFile mBindingFile = null;
   private JavaOutputStream mJoust = null;
   private Collection mSchemaDocuments = null;
   private SchemaTypeSystem mSchemaTypeSystem = null;
   private boolean jaxRpc = false;

   public static ExplodedTylarImpl create(File dir) throws IOException {
      return create(dir, createDefaultJoust(dir));
   }

   public static ExplodedTylarImpl load(File dir) throws IOException, XmlException {
      return load(dir, createDefaultJoust(dir));
   }

   public static ExplodedTylarImpl create(File dir, JavaOutputStream joust) throws IOException {
      return create(dir, joust, false);
   }

   public static ExplodedTylarImpl create(File dir, JavaOutputStream joust, boolean jaxRpc) throws IOException {
      if (dir.exists()) {
         if (dir.isFile()) {
            throw new IOException("Already a file at " + dir);
         }
      } else if (!dir.mkdirs()) {
         throw new IOException("Failed to create " + dir);
      }

      return new ExplodedTylarImpl(dir, (BindingFile)null, (Collection)null, (SchemaTypeSystem)null, joust, jaxRpc);
   }

   public static ExplodedTylarImpl load(File dir, JavaOutputStream joust) throws IOException, XmlException {
      if (dir.exists()) {
         if (dir.isFile()) {
            throw new IOException(dir + " is a file");
         } else {
            BindingFile bf = parseBindingFile(new File(dir, "META-INF/binding-file.xml"));
            Collection schemas = new ArrayList();
            parseSchemas(new File(dir, "META-INF/schemas"), schemas);
            SchemaTypeSystem sts = null;

            try {
               File stsDir = new File(dir, "schemacom_bea_xml/system");
               String stsName = stsDir.list()[0];
               ClassLoader cl = new URLClassLoader(new URL[]{dir.toURL()});
               sts = SchemaTypeSystemImpl.forName("schemacom_bea_xml.system." + stsName, cl);
               if (sts == null) {
                  throw new IllegalStateException("null returned by SchemaTypeSystemImpl.forName()");
               }
            } catch (Exception var8) {
               showXsbError(var8, dir.toURI(), "read", false);
            }

            return new ExplodedTylarImpl(dir, bf, schemas, sts, joust, false);
         }
      } else {
         throw new IOException("No such directory " + dir);
      }
   }

   private ExplodedTylarImpl(File dir, BindingFile bindingFile, Collection schemas, SchemaTypeSystem sts, JavaOutputStream joust, boolean jaxRpc) {
      this.mRootDir = dir;
      this.mSourceRoot = new File(this.mRootDir, "META-INF/src");
      this.mSchemaDir = new File(this.mRootDir, "META-INF/schemas");
      this.mJoust = joust;
      this.mBindingFile = bindingFile;
      this.mSchemaDocuments = schemas;
      this.mSchemaTypeSystem = sts;
      this.jaxRpc = jaxRpc;
   }

   protected BindingLoader getBuiltinBindingLoader() {
      return BuiltinBindingLoader.getBuiltinBindingLoader(this.jaxRpc);
   }

   public void writeBindingFile(BindingFile bf) throws IOException {
      this.mBindingFile = bf;
      writeBindingFile(bf, new File(this.mRootDir, "META-INF/binding-file.xml"));
      this.writeBindingSer(bf);
      this.writeBindingMappingSer(bf);
   }

   private void writeBindingSer(BindingFile bf) throws IOException {
      File ser_file = new File(this.mRootDir, "META-INF/binding-file.ser");
      FileOutputStream fos = new FileOutputStream(ser_file);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(bf);
      oos.close();
      fos.close();

      assert ser_file.exists();

   }

   private void writeBindingMappingSer(BindingFile bf) throws IOException {
      BindingMappingFile bmf = new BindingMappingFile(bf);
      File ser_file = new File(this.mRootDir, "META-INF/binding-mapping-file.ser");
      FileOutputStream fos = new FileOutputStream(ser_file);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(bmf);
      oos.close();
      fos.close();

      assert ser_file.exists();

   }

   public void writeSchema(SchemaDocument xsd, String schemaFileName, Map tns2prefix) throws IOException {
      if (schemaFileName == null) {
         throw new IllegalArgumentException("null schemaFileName");
      } else {
         if (this.mSchemaDocuments == null) {
            this.mSchemaDocuments = new ArrayList();
         }

         this.mSchemaDocuments.add(xsd);
         writeXsd(xsd, new File(this.mSchemaDir, schemaFileName), tns2prefix);
      }
   }

   public void writeSchemaTypeSystem(SchemaTypeSystem sts) throws IOException {
      if (sts == null) {
         throw new IllegalArgumentException("null sts");
      } else {
         this.mSchemaTypeSystem = sts;

         try {
            SchemaCodeGenerator.saveTypeSystem(sts, this.mRootDir, (File)null, (Repackager)null, (XmlOptions)null);
         } catch (Exception var3) {
            showXsbError(var3, this.mRootDir.toURI(), "write", false);
         }

      }
   }

   public JavaOutputStream getJavaOutputStream() {
      return this.mJoust;
   }

   public void close() throws IOException {
      if (this.mJoust != null) {
         this.mJoust.close();
      }

   }

   public static void showXsbError(Throwable e, URI where, String readOrWrite, boolean showTrace) {
      e.printStackTrace();
   }

   public BindingFile[] getBindingFiles() {
      return new BindingFile[]{this.mBindingFile};
   }

   public SchemaDocument[] getSchemas() {
      if (this.mSchemaDocuments == null) {
         return new SchemaDocument[0];
      } else {
         SchemaDocument[] out = new SchemaDocument[this.mSchemaDocuments.size()];
         this.mSchemaDocuments.toArray(out);
         return out;
      }
   }

   public SchemaTypeLoader getSchemaTypeLoader() throws IOException, XmlException {
      if (this.mSchemaTypeSystem == null) {
         this.mSchemaTypeSystem = this.getDefaultSchemaTypeSystem();
      }

      return this.mSchemaTypeSystem;
   }

   public void resetCaches() {
      this.mSchemaDocuments = null;
      this.mBindingFile = null;
   }

   public File getRootDir() {
      return this.mRootDir;
   }

   public Tylar toJar(File jarfile) throws IOException {
      JarHelper j = new JarHelper();
      jarfile.getParentFile().mkdirs();
      j.jarDir(this.mRootDir, jarfile);
      return new TylarImpl(new URL[]{jarfile.toURL()}, this.mBindingFile, this.mSchemaDocuments);
   }

   public File getSourceDir() {
      return this.mSourceRoot;
   }

   public File getClassDir() {
      return this.mRootDir;
   }

   public File getSchemaDir() {
      return this.mSchemaDir;
   }

   public ClassLoader createClassLoader(ClassLoader parent) {
      try {
         return new URLClassLoader(new URL[]{this.mRootDir.toURL()}, parent);
      } catch (MalformedURLException var3) {
         throw new RuntimeException(var3);
      }
   }

   private static JavaOutputStream createDefaultJoust(File dir) {
      File srcDir = new File(dir, "META-INF/src");
      return new ValidatingJavaOutputStream(new SourceJavaOutputStream(new FileWriterFactory(srcDir)));
   }

   private static void parseSchemas(File schemaDir, Collection out) throws IOException, XmlException {
      File[] xsds = schemaDir.listFiles();
      if (xsds != null) {
         for(int i = 0; i < xsds.length; ++i) {
            out.add(parseXsd(xsds[i]));
         }
      }

   }

   private static SchemaDocument parseXsd(File file) throws IOException, XmlException {
      FileReader in = null;

      SchemaDocument var2;
      try {
         in = new FileReader(file);
         var2 = Factory.parse(in);
      } catch (IOException var12) {
         throw var12;
      } catch (XmlException var13) {
         throw var13;
      } finally {
         if (in != null) {
            try {
               in.close();
            } catch (Exception var11) {
               var11.printStackTrace();
            }
         }

      }

      return var2;
   }

   private static void writeXsd(SchemaDocument xsd, File file, Map tns2prefix) throws IOException {
      FileOutputStream out = null;

      try {
         file.getParentFile().mkdirs();
         out = new FileOutputStream(file);
         XmlOptions options = (new XmlOptions()).setSavePrettyPrint().setSaveAggressiveNamespaces().setSavePrettyPrintIndent(2);
         if (tns2prefix != null) {
            options.setSaveSuggestedPrefixes(tns2prefix);
         }

         xsd.save(out, options);
      } catch (IOException var12) {
         throw var12;
      } finally {
         if (out != null) {
            try {
               out.close();
            } catch (Exception var11) {
               var11.printStackTrace();
            }
         }

      }

   }

   private static BindingFile parseBindingFile(File file) throws IOException, XmlException {
      FileReader in = null;

      BindingFile var2;
      try {
         in = new FileReader(file);
         var2 = BindingFileUtils.forDoc(BindingConfigDocument.Factory.parse((Reader)in));
      } catch (IOException var12) {
         throw var12;
      } catch (XmlException var13) {
         throw var13;
      } finally {
         if (in != null) {
            try {
               in.close();
            } catch (Exception var11) {
               var11.printStackTrace();
            }
         }

      }

      return var2;
   }

   private static void writeBindingFile(BindingFile bf, File file) throws IOException {
      PrintStream out = null;

      try {
         file.getParentFile().mkdirs();
         out = new PrintStream(new FileOutputStream(file), true, "UTF-8");
         BindingConfigDocument doc = BindingFileUtils.write(bf);
         doc.save(out, (new XmlOptions()).setSavePrettyPrint().setSavePrettyPrintIndent(2));
         out.flush();
      } catch (IOException var11) {
         throw var11;
      } finally {
         if (out != null) {
            try {
               out.close();
            } catch (Exception var10) {
               var10.printStackTrace();
            }
         }

      }

   }
}
