package com.bea.staxb.buildtime.internal.tylar;

import com.bea.ns.staxb.bindingConfig.x90.BindingConfigDocument;
import com.bea.staxb.buildtime.internal.bts.BindingFile;
import com.bea.staxb.buildtime.internal.bts.BindingFileUtils;
import com.bea.xbean.schema.SchemaTypeSystemImpl;
import com.bea.xbean.xb.xsdschema.SchemaDocument.Factory;
import com.bea.xml.SchemaTypeSystem;
import com.bea.xml.XmlException;
import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class DefaultTylarLoader implements TylarLoader, TylarConstants {
   private static TylarLoader DEFAULT_INSTANCE = new DefaultTylarLoader();
   private static final String FILE_SCHEME = "file";
   private static final char[] OTHER_SEPCHARS = new char[]{'\\'};
   private static final char SEPCHAR = '/';
   private static final boolean VERBOSE = false;
   private static final String BINDING_FILE_JARENTRY = normalizeEntryName("META-INF/binding-file.xml").toLowerCase();
   private static final String SCHEMA_DIR_JARENTRY = normalizeEntryName("META-INF/schemas").toLowerCase();
   private static final String SCHEMA_EXT = ".xsd";
   private static final String STS_PREFIX = "schemacom_bea_xml/system/";

   public static final TylarLoader getInstance() {
      return DEFAULT_INSTANCE;
   }

   /** @deprecated */
   @Deprecated
   public static void setInstance(TylarLoader newDefaultLoader) {
      DEFAULT_INSTANCE = newDefaultLoader;
   }

   protected DefaultTylarLoader() {
   }

   public Tylar load(ClassLoader cl) throws IOException, XmlException {
      return RuntimeTylar.hasTylar(cl) ? new RuntimeTylar(cl) : null;
   }

   public Tylar load(URI uri) throws IOException, XmlException {
      if (uri == null) {
         throw new IllegalArgumentException("null uri");
      } else {
         File file = null;

         try {
            file = new File(uri);
         } catch (Exception var4) {
         }

         return (Tylar)(file != null && file.exists() && file.isDirectory() ? ExplodedTylarImpl.load(file) : loadFromJar(new JarInputStream(uri.toURL().openStream()), uri));
      }
   }

   public Tylar load(URI[] uris) throws IOException, XmlException {
      Tylar[] tylars = new Tylar[uris.length];

      for(int i = 0; i < tylars.length; ++i) {
         tylars[i] = this.load(uris[i]);
      }

      return new CompositeTylar(tylars);
   }

   public Tylar load(JarInputStream jar) throws IOException, XmlException {
      if (jar == null) {
         throw new IllegalArgumentException("null stream");
      } else {
         return loadFromJar(jar, (URI)null);
      }
   }

   protected static Tylar loadFromJar(JarInputStream jin, URI source) throws IOException, XmlException {
      if (jin == null) {
         throw new IllegalArgumentException("null stream");
      } else {
         BindingFile bf = null;
         Collection schemas = null;
         StubbornInputStream stubborn = new StubbornInputStream(jin);
         String stsName = null;

         JarEntry entry;
         while((entry = jin.getNextJarEntry()) != null) {
            String name = normalizeEntryName(entry.getName());
            if (name.endsWith("/")) {
               if (name.startsWith("schemacom_bea_xml/system/") && name.length() > "schemacom_bea_xml/system/".length()) {
                  stsName = "schemacom_bea_xml.system." + name.substring("schemacom_bea_xml/system/".length(), name.length() - 1);
               }
            } else {
               name = name.toLowerCase();
               if (name.equals(BINDING_FILE_JARENTRY)) {
                  bf = BindingFileUtils.forDoc(BindingConfigDocument.Factory.parse((InputStream)stubborn));
               } else if (name.startsWith(SCHEMA_DIR_JARENTRY) && name.endsWith(".xsd")) {
                  if (schemas == null) {
                     schemas = new ArrayList();
                  }

                  schemas.add(Factory.parse(stubborn));
               }

               jin.closeEntry();
            }
         }

         if (bf == null) {
            throw new IOException("resource at '" + source + "' is not a tylar: it does not contain a binding file");
         } else {
            jin.close();
            SchemaTypeSystem sts = null;
            if (stsName != null && source != null) {
               try {
                  URLClassLoader ucl = new URLClassLoader(new URL[]{source.toURL()});
                  sts = SchemaTypeSystemImpl.forName(stsName, ucl);
                  if (sts == null) {
                     throw new IllegalStateException("null returned by SchemaTypeSystemImpl.forName()");
                  }
               } catch (Exception var9) {
                  ExplodedTylarImpl.showXsbError(var9, source, "read", false);
               }
            }

            return new TylarImpl(source == null ? null : new URL[]{source.toURL()}, bf, schemas, sts);
         }
      }
   }

   private static final String normalizeEntryName(String name) {
      name = name.trim();

      for(int i = 0; i < OTHER_SEPCHARS.length; ++i) {
         name = name.replace(OTHER_SEPCHARS[i], '/');
      }

      if (name.charAt(0) == '/') {
         name = name.substring(1);
      }

      return name;
   }

   private static class StubbornInputStream extends FilterInputStream {
      StubbornInputStream(InputStream in) {
         super(in);
      }

      public void close() {
      }

      public void reallyClose() throws IOException {
         super.close();
      }
   }
}
