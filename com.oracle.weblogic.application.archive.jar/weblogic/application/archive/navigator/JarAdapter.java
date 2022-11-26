package weblogic.application.archive.navigator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.archive.ApplicationArchiveEntry;

public class JarAdapter extends JarFile {
   private static String endofline = System.getProperty("line.separator");
   private ApplicationArchiveImpl aa;
   private DirectoryNode directoryNode;

   public String toString() {
      StringBuilder sb = new StringBuilder(super.toString() + endofline);
      Enumeration e = this.entries();

      while(e.hasMoreElements()) {
         sb.append(((JarEntry)e.nextElement()).toString()).append(endofline);
      }

      return sb.toString();
   }

   public JarAdapter(ApplicationArchive archive) throws IOException {
      super(JarAdapter.DummyJar.getName(), false);
      this.aa = (ApplicationArchiveImpl)archive;
      this.directoryNode = (DirectoryNode)this.aa.underlying.getNode();
   }

   public Enumeration entries() {
      LinkedList results = new LinkedList();
      Iterator var2 = this.aa.find(".", DirectoryNode.AcceptAllFilter.SINGLETON).iterator();

      while(var2.hasNext()) {
         ApplicationArchiveEntry ae = (ApplicationArchiveEntry)var2.next();
         results.add(new JarEntryProxy(ae, this.directoryNode));
      }

      return Collections.enumeration(results);
   }

   public ZipEntry getEntry(String entryName) {
      ApplicationArchiveEntry aae = this.aa.getEntry(entryName);
      return aae == null ? null : new JarEntryProxy(aae, this.directoryNode);
   }

   public synchronized InputStream getInputStream(ZipEntry zip) throws IOException {
      ApplicationArchiveEntry ae = this.createApplicationArchiveEntry(zip);
      return this.aa.getInputStream(ae);
   }

   private ApplicationArchiveEntry createApplicationArchiveEntry(ZipEntry zip) {
      if (zip instanceof JarEntryProxy) {
         return ((JarEntryProxy)zip).node;
      } else {
         throw new IllegalArgumentException("The zip must be a JarEntryProxy");
      }
   }

   public JarEntry getJarEntry(String entryName) {
      return (JarEntry)this.getEntry(entryName);
   }

   public Manifest getManifest() throws IOException {
      return this.aa.getManifest();
   }

   public void close() throws IOException {
      this.aa.close();
   }

   public String getName() {
      return this.aa.getName();
   }

   public int size() {
      throw new UnsupportedOperationException();
   }

   private static File getDummyJar() {
      return JarAdapter.DummyJar.getFile();
   }

   /** @deprecated */
   @Deprecated
   public void saveAs(File file) throws FileNotFoundException, IOException {
      file.getParentFile().mkdirs();
      JarOutputStream jos = new JarOutputStream(new FileOutputStream(file), this.getManifest());

      for(Enumeration e = this.entries(); e.hasMoreElements(); jos.closeEntry()) {
         JarEntry je = (JarEntry)e.nextElement();
         String name = je.getName();
         if (!name.equals("META-INF/MANIFEST.MF")) {
            jos.putNextEntry(je);
            if (je.getSize() > 0L) {
               InputStream is = this.getInputStream(je);

               for(int c = is.read(); c != -1; c = is.read()) {
                  jos.write(c);
               }
            }
         }
      }

      jos.finish();
      jos.close();
   }

   private static enum DummyJar {
      INSTANCE;

      private File dummyJarFile;

      private DummyJar() {
         try {
            this.dummyJarFile = File.createTempFile(".tmp.dummy", ".jar");
            JarOutputStream jos = new JarOutputStream(new FileOutputStream(this.dummyJarFile));
            JarEntry je = new JarEntry("foo") {
               public boolean isDirectory() {
                  return true;
               }
            };
            jos.putNextEntry(je);
            jos.finish();
         } catch (IOException var5) {
            throw new AssertionError(var5);
         }
      }

      protected static String getName() {
         try {
            return INSTANCE.dummyJarFile.getCanonicalPath();
         } catch (IOException var1) {
            throw new AssertionError(var1);
         }
      }

      public static File getFile() {
         return INSTANCE.dummyJarFile;
      }
   }
}
