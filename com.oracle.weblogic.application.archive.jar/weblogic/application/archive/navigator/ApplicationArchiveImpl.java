package weblogic.application.archive.navigator;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.jar.Manifest;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.archive.ApplicationArchiveEntry;
import weblogic.application.archive.utils.FileFilters;

public class ApplicationArchiveImpl implements ApplicationArchive {
   ApplicationNavigator underlying;
   private static final String[] ROOT_PATH = new String[0];

   ApplicationArchiveImpl(ApplicationNavigator applicationNavigator) throws IOException {
      if (applicationNavigator == null) {
         throw new IllegalArgumentException("Must provide a valid applicationNavigator");
      } else {
         this.underlying = applicationNavigator;
      }
   }

   public String getName() {
      return this.underlying.getName();
   }

   public ApplicationArchive getApplicationArchive(String relativeUri) throws IOException {
      return new ApplicationArchiveImpl(((ApplicationNavigatorImpl)this.underlying).createApplicationNavigator(this.splitPath(relativeUri)));
   }

   public File getDirectoryOrJarFile(String uri) throws IOException {
      ApplicationArchiveEntry aae = this.getEntry(uri);
      if (aae == null) {
         throw new IOException("No entry at " + uri);
      } else {
         File f = ((Node)aae).asFile();
         if (f != null && f.exists()) {
            return f;
         } else {
            File file;
            if (aae.isDirectory()) {
               DirectoryNode dn = (DirectoryNode)aae;
               file = new File(this.getWriteableRootDir(true), uri);
               if (!file.exists()) {
                  ApplicationArchive aa = this.getApplicationArchive(uri);
                  Node rootNode = (Node)aa.getEntry("");
                  Iterator var8 = aa.find(".", FileFilters.ADE_PATH).iterator();

                  while(true) {
                     while(var8.hasNext()) {
                        ApplicationArchiveEntry entry = (ApplicationArchiveEntry)var8.next();
                        String name = ((Node)entry).getFile(rootNode).getPath();
                        if (entry.isDirectory()) {
                           (new File(file, name)).mkdirs();
                        } else {
                           FileOutputStream fos = new FileOutputStream(new File(file, name));
                           InputStream is = entry.getInputStream();

                           for(int c = is.read(); c != -1; c = is.read()) {
                              fos.write(c);
                           }

                           fos.close();
                        }
                     }

                     return file;
                  }
               } else {
                  return file;
               }
            } else {
               InputStream is = aae.getInputStream();
               if (is == null) {
                  throw new IOException("Null stream at " + uri + ", " + aae);
               } else {
                  file = new File(this.getWriteableRootDir(true), uri);
                  if (!file.exists()) {
                     FileOutputStream fos = new FileOutputStream(file);

                     for(int c = is.read(); c != -1; c = is.read()) {
                        fos.write(c);
                     }

                     fos.close();
                  }

                  return file;
               }
            }
         }
      }
   }

   public Iterable list(String relativeUri) {
      return this.list(relativeUri, DirectoryNode.AcceptAllFilter.SINGLETON);
   }

   public Iterable list(String relativeUri, FileFilter filter) {
      Iterable nodes = this.underlying.listNode(this.splitPath(relativeUri), filter);
      return new IteratorIterable(nodes.iterator(), (IteratorIterable.Transformer)null);
   }

   public Iterable find(String relativeUri, FileFilter filter) {
      return this.underlying.find(this.splitPath(relativeUri), filter);
   }

   public Manifest getManifest() throws IOException {
      ApplicationArchiveEntry aae = this.getEntry("META-INF/MANIFEST.MF");
      return aae == null ? null : new Manifest(this.getInputStream(aae));
   }

   public void close() throws IOException {
   }

   public InputStream getInputStream(ApplicationArchiveEntry ae) throws IOException {
      return ae.getInputStream();
   }

   public ApplicationArchiveEntry getEntry(String path) {
      return (ApplicationArchiveEntry)this.underlying.getNode(this.splitPath(path));
   }

   private String[] splitPath(String path) {
      path = path.trim();
      if (path.length() == 0) {
         return ROOT_PATH;
      } else {
         if (path.charAt(0) == '/') {
            path = path.substring(1);
         }

         String[] elems = path.split("/");
         int j = 0;

         for(int i = 0; i < elems.length; ++i) {
            String elem = elems[i].trim();
            if ("..".equals(elem)) {
               --j;
               if (j < 0) {
                  throw new IllegalArgumentException("Path cannot be normalized. " + path);
               }
            } else if (!".".equals(elem) && elem.length() != 0) {
               elems[j++] = elem;
            }
         }

         return j == elems.length ? elems : (String[])Arrays.copyOfRange(elems, 0, j);
      }
   }

   public File getWriteableRootDir(boolean ensureExistence) {
      if (ensureExistence) {
         this.underlying.getWriteableRootDir().mkdirs();
      }

      return this.underlying.getWriteableRootDir();
   }

   public void registerMapping(String uri, File path) throws IOException {
      this.underlying.registerMapping(this.splitPath(uri), path);
   }

   public void resetWritableRoot(File writableRoot) {
      this.underlying.resetWritableRootDir(writableRoot);
   }
}
