package com.bea.util.annogen.generate.internal;

import com.bea.util.jam.JClass;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropfileUtils {
   private static final String PROPFILE_SUFFIX = ".annogen.properties";
   private static final String ANNOBEAN_PROP = "annobean";
   private static final PropfileUtils INSTANCE = new PropfileUtils();

   public static PropfileUtils getInstance() {
      return INSTANCE;
   }

   private PropfileUtils() {
   }

   public void writeAnnobeanTypeFor(JClass jsr175type, String annobeanType, File rootDir) throws IOException {
      if (jsr175type == null) {
         throw new IllegalArgumentException("null 175 type");
      } else if (annobeanType == null) {
         throw new IllegalArgumentException("null annobeanType");
      } else if (rootDir == null) {
         throw new IllegalArgumentException("null rootDir");
      } else {
         Properties props = new Properties();
         props.setProperty("annobean", annobeanType);
         this.writeProperties(jsr175type, props, rootDir);
      }
   }

   public Class getAnnobeanTypeFor(Class jsr175type, ClassLoader cl) throws IOException, ClassNotFoundException {
      Properties typeProps = this.getPropfileForAnnotype(jsr175type, cl);
      String name = typeProps.getProperty("annobean");
      if (name == null) {
         throw new IllegalStateException("no annobean in " + typeProps);
      } else {
         return cl.loadClass(name);
      }
   }

   private void writeProperties(JClass jsr175type, Properties props, File rootDir) throws IOException {
      String propFile = jsr175type.getQualifiedName().replace('.', File.separatorChar);
      File file = new File(rootDir, propFile + ".annogen.properties");
      if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
         throw new IOException("failed to create " + file.getParentFile());
      } else {
         FileOutputStream fos = null;

         try {
            fos = new FileOutputStream(file);
            props.store(fos, (String)null);
         } catch (RuntimeException var12) {
            throw var12;
         } catch (IOException var13) {
            throw var13;
         } finally {
            if (fos != null) {
               fos.close();
            }

         }

      }
   }

   public Properties getPropfileForAnnotype(Class jsr175type, ClassLoader cl) throws IOException {
      String propFile = jsr175type.getName().replace('.', File.separatorChar) + ".annogen.properties";
      InputStream in = cl.getResourceAsStream(propFile);
      if (in == null) {
         throw new IOException("Could not load " + propFile);
      } else {
         Properties out = new Properties();

         try {
            out.load(in);
         } catch (IOException var11) {
            throw var11;
         } catch (RuntimeException var12) {
            throw var12;
         } finally {
            in.close();
         }

         return out;
      }
   }
}
