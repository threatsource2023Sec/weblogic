package weblogic.application.archive.navigator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import weblogic.application.archive.ApplicationArchive;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

class ApplicationArchiveVirtualJarFile implements VirtualJarFile {
   private final VirtualJarFile underlyingJarFile;
   private final ApplicationArchiveImpl aa;
   private File directory = null;

   public ApplicationArchiveVirtualJarFile(ApplicationArchive aa) throws IOException {
      this.underlyingJarFile = VirtualJarFactory.createVirtualJar(new JarAdapter(aa));
      this.aa = (ApplicationArchiveImpl)aa;
      Node nd = (Node)aa.getEntry("");
      File d = nd.getDiscLocation();
      if (d.exists() && d.isDirectory()) {
         this.directory = d;
      }

   }

   public String getName() {
      return this.underlyingJarFile.getName();
   }

   public void close() throws IOException {
      this.underlyingJarFile.close();
   }

   public Iterator entries() {
      return this.underlyingJarFile.entries();
   }

   public URL getResource(String name) {
      if (this.isDirectory()) {
         Node nd = (Node)this.aa.getEntry(name);
         if (nd == null) {
            return null;
         } else {
            try {
               return new URL("file", "", nd.getDiscLocation().getCanonicalPath());
            } catch (MalformedURLException var4) {
               var4.printStackTrace();
               return null;
            } catch (IOException var5) {
               var5.printStackTrace();
               return null;
            }
         }
      } else {
         return this.underlyingJarFile.getResource(name);
      }
   }

   public ZipEntry getEntry(String name) {
      return this.underlyingJarFile.getEntry(name);
   }

   public Iterator getEntries(String uri) throws IOException {
      return this.underlyingJarFile.getEntries(uri);
   }

   public InputStream getInputStream(ZipEntry ze) throws IOException {
      return this.underlyingJarFile.getInputStream(ze);
   }

   public Manifest getManifest() throws IOException {
      return this.underlyingJarFile.getManifest();
   }

   public File[] getRootFiles() {
      return this.underlyingJarFile.getRootFiles();
   }

   public boolean isDirectory() {
      return this.directory != null;
   }

   public JarFile getJarFile() {
      return this.underlyingJarFile.getJarFile();
   }

   public File getDirectory() {
      return this.isDirectory() ? this.directory : this.underlyingJarFile.getDirectory();
   }
}
