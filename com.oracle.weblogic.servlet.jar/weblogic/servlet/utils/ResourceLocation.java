package weblogic.servlet.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import weblogic.servlet.internal.HTTPDebugLogger;
import weblogic.utils.zip.SafeZipFileInputStream;

public class ResourceLocation implements Serializable {
   public static final int TYPE_TLD = 1;
   public static final int TYPE_FACES_CONFIG = 2;
   protected final int type;
   protected final File location;
   private final String uri;

   public ResourceLocation(File location, String uri, int type) {
      this.location = location;
      this.uri = uri;
      this.type = type;
   }

   public InputStream getInputStream() throws IOException {
      return new FileInputStream(this.location);
   }

   public String getLocation() {
      return this.location.getAbsolutePath();
   }

   public String getURI() {
      return this.uri;
   }

   int getType() {
      return this.type;
   }

   public static final class JarResourceLocation extends ResourceLocation {
      private String fragment;

      public JarResourceLocation(File jarPath, String fragment, int type) {
         super(jarPath, jarPath.getName() + '/' + fragment, type);
         this.fragment = fragment;
      }

      public InputStream getInputStream() {
         JarFile jar = null;

         try {
            jar = new JarFile(this.location);
            JarEntry entry = (JarEntry)jar.getEntry(this.fragment);
            if (entry != null) {
               return new SafeZipFileInputStream(jar, entry);
            }
         } catch (IOException var5) {
            if (HTTPDebugLogger.isEnabled()) {
               if (this.type == 1) {
                  HTTPDebugLogger.debug("[War] Unable to find tld at location: " + this.getLocation());
               } else if (this.type == 2) {
                  HTTPDebugLogger.debug("[War] Unable to find faces config file at location: " + this.getLocation());
               }
            }

            if (jar != null) {
               try {
                  jar.close();
               } catch (IOException var4) {
               }
            }
         }

         return null;
      }

      public String getLocation() {
         return this.location.getAbsolutePath() + "!" + (this.fragment.endsWith("/") ? this.fragment : "/" + this.fragment);
      }
   }
}
