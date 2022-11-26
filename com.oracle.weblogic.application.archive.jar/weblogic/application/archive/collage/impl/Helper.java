package weblogic.application.archive.collage.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import weblogic.application.archive.collage.Directory;
import weblogic.application.archive.collage.Node;
import weblogic.application.archive.navigator.IteratorIterable;
import weblogic.application.archive.utils.CollectionUtils;
import weblogic.application.archive.utils.FileFilters;
import weblogic.application.archive.utils.FilePathUtils;

public enum Helper {
   Singleton;

   private static String endOfLine = System.getProperty("line.separator");
   private static final String[] ROOT_PATH = new String[0];

   public Directory createDirectoryForArchive(File archive) throws IOException {
      if (archive.isDirectory()) {
         File[] splitDirFiles = archive.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
               return name.endsWith(".beabuild.txt");
            }
         });
         return splitDirFiles != null && splitDirFiles.length != 0 ? this.initAsSplitSource(splitDirFiles[0]) : this.initAsDirectory(archive);
      } else {
         String name = archive.getName();
         int l = name.length();
         if (l >= 5) {
            String lastTwoChars = this.getLastChars(name, 2);
            if (name.charAt(l - 4) == '.' && "ar".equalsIgnoreCase(lastTwoChars)) {
               return this.initAsJarFile(archive);
            }

            if ("-app.xml".equalsIgnoreCase(this.getLastChars(name, 8))) {
               return this.initAsCollage(archive);
            }
         }

         throw new IOException("Unabled to determine the deployment file type. " + archive);
      }
   }

   public Iterable listNodes(Directory d, final FileFilter filter) {
      IteratorIterable.Predicate predicate = new IteratorIterable.Predicate() {
         public boolean accept(Node node) {
            return filter.accept(node.getFile());
         }
      };
      IteratorIterable.FilteredIterator fi = new IteratorIterable.FilteredIterator(d.getAll().iterator(), predicate);
      return new IteratorIterable(fi, (IteratorIterable.Transformer)null);
   }

   public void populate(DirectoryImpl d, File directory, FileFilter filter) throws IOException {
      if (directory.isFile()) {
         ArtifactImpl a = ArtifactImpl.createFileArtifact(d, directory.getName(), directory, directory.getCanonicalPath());
         d.addChild(a);
      } else {
         File[] files = directory.listFiles(filter);
         if (files == null) {
            String message = !directory.isDirectory() ? "This is not a valid directory: " : "An IO exception while listing this directory: ";
            throw new IOException(message + directory);
         }

         File[] var5 = files;
         int var6 = files.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            File f = var5[var7];
            Object n;
            if (f.isFile()) {
               n = ArtifactImpl.createFileArtifact(d, f.getName(), f, f.getCanonicalPath());
            } else {
               if (!f.isDirectory()) {
                  continue;
               }

               n = new DirectoryImpl(d, f.getName(), f.lastModified(), f.getCanonicalPath());
               this.populate((DirectoryImpl)n, f, filter);
            }

            d.addChild((Node)n);
         }
      }

   }

   private ContainerImpl mkdir(ContainerImpl directory, Iterator uriIterator) {
      if (uriIterator.hasNext()) {
         String name = (String)uriIterator.next();
         DirectoryImpl d = (DirectoryImpl)directory.get(name);
         if (d == null) {
            d = new DirectoryImpl(directory, name, System.currentTimeMillis(), (String)null);
            directory.addChild(d);
         }

         return this.mkdir(d, uriIterator);
      } else {
         return directory;
      }
   }

   public String toString(ContainerImpl c) {
      return this.toString(new StringBuffer(), "", c).toString();
   }

   private StringBuffer toString(StringBuffer sb, String indent, ContainerImpl c) {
      this.append(sb, indent, c);
      indent = indent + "  ";
      Iterator var4 = c.getAll().iterator();

      while(var4.hasNext()) {
         Node n = (Node)var4.next();
         if (n instanceof Directory) {
            this.toString(sb, indent, (ContainerImpl)n);
         } else {
            this.append(sb, indent, n);
         }
      }

      return sb;
   }

   private StringBuffer append(StringBuffer sb, String indent, Node n) {
      return sb.append(indent).append(n.toString()).append(endOfLine);
   }

   private String toPathString(String... pathElements) {
      File pathFile = new File("");
      String[] var3 = pathElements;
      int var4 = pathElements.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String path = var3[var5];
         pathFile = new File(pathFile, path);
      }

      return pathFile.getPath();
   }

   private Directory initAsSplitSource(File splitSrcDesc) throws IOException {
      System.out.println("0");
      File srcDir = splitSrcDesc.getParentFile();
      Properties props = new Properties();
      System.out.println("1");
      props.load(new FileInputStream(splitSrcDesc));
      System.out.println("props: " + props.getProperty("bea.srcdir"));
      File buildDir = new File(props.getProperty("bea.srcdir"));
      System.out.println("about to call");
      return this.rootNodeForSplitDir(srcDir, buildDir);
   }

   private Directory rootNodeForSplitDir(File highPrecedenceSrcDirectory, File buildDirectory) throws IOException {
      DirectoryImpl dn = new DirectoryImpl((ContainerImpl)null, highPrecedenceSrcDirectory.getName(), highPrecedenceSrcDirectory.lastModified(), highPrecedenceSrcDirectory.getCanonicalPath());
      this.populate(dn, highPrecedenceSrcDirectory, FileFilters.ADE_PATH);
      this.populate(dn, buildDirectory, FileFilters.ADE_PATH);
      return dn;
   }

   private Directory initAsCollage(File archive) {
      throw new RuntimeException("Unimplemented method: Helper.initAsCollage");
   }

   private Directory initAsJarFile(File archive) throws IOException {
      return this.rootNodeForJarFile(archive);
   }

   private Directory rootNodeForJarFile(File f) throws IOException {
      ArchiveImpl a = ArchiveImpl.createArchive((ContainerImpl)null, f.getName(), f, f.getCanonicalPath());
      this.populate(a, new JarFile(f, false), FileFilters.ADE_PATH);
      return a;
   }

   public void populate(ArchiveImpl a, JarFile jf, FileFilter aDE_PATH) {
      String jarPath = jf.getName() + "!";
      Enumeration e = jf.entries();

      ContainerImpl p;
      Object n;
      for(int i = false; e.hasMoreElements(); p.addChild((Node)n)) {
         JarEntry je = (JarEntry)e.nextElement();
         String entryName = FilePathUtils.fixJarEntryOnWindows(je.getName());
         String[] path = this.splitPath(entryName);
         p = this.mkdir(a, CollectionUtils.iterator(path, 0, path.length - 1));
         String name = path[path.length - 1];
         if (je.isDirectory()) {
            n = new DirectoryImpl(p, name, je.getTime(), jarPath + entryName);
         } else {
            n = ArtifactImpl.createJarEntryArtifact(p, name, jf, entryName, jarPath + entryName);
         }
      }

   }

   private Directory initAsDirectory(File f) throws IOException {
      DirectoryImpl d = new DirectoryImpl((ContainerImpl)null, f.getName(), f.lastModified(), f.getCanonicalPath());
      this.populate(d, f, FileFilters.ADE_PATH);
      return d;
   }

   private String getLastChars(String name, int n) {
      int l = name.length();
      return l >= n ? name.substring(l - n, l) : null;
   }

   private String[] splitPath(String path) {
      String[] elems = (path.charAt(0) == '/' ? path.substring(1) : path).split("/");
      return elems.length == 1 && elems[0].isEmpty() ? ROOT_PATH : elems;
   }
}
