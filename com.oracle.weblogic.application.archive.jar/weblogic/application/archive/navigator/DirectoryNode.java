package weblogic.application.archive.navigator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;
import weblogic.application.archive.utils.CollectionUtils;
import weblogic.application.archive.utils.FileFilters;
import weblogic.application.archive.utils.FilePathUtils;

public class DirectoryNode extends Node {
   private final Map children;
   protected static final FileFilter ADE_FILTER;
   private static final String[] ROOT_PATH;

   protected DirectoryNode(DirectoryNode parent, File dir) throws IOException {
      this(parent, dir.getName());
      this.populate(dir, ADE_FILTER);
   }

   protected Iterable listNodes(final FileFilter filter, final Node root) {
      IteratorIterable.Predicate predicate = new IteratorIterable.Predicate() {
         public boolean accept(Node node) {
            return filter.accept(node.getFile(root));
         }
      };
      IteratorIterable.FilteredIterator fi = new IteratorIterable.FilteredIterator(this.children.values().iterator(), predicate);
      return new IteratorIterable(fi, (IteratorIterable.Transformer)null);
   }

   protected DirectoryNode(DirectoryNode parent, JarFile jf) {
      super(new File(jf.getName()), new File(parent.virtualLocation, (new File(jf.getName())).getName()));
      this.children = new HashMap();
      this.populate(jf);
   }

   private DirectoryNode(DirectoryNode parent, String name) {
      super((Node)parent, (String)name);
      this.children = new HashMap();
   }

   public DirectoryNode(EntryNode nd) throws IOException {
      this((DirectoryNode)nd.parent, nd.getName());
      this.parent = null;
      JarInputStream jis = new JarInputStream(nd.getInputStream());
      this.populate(jis);
   }

   public DirectoryNode(File archive, File virtualLocation) throws IOException {
      super(archive, virtualLocation);
      this.children = new HashMap();
      this.populate(archive, ADE_FILTER);
   }

   public DirectoryNode(File f) {
      super(f.getParentFile(), new File("."));
      this.children = new HashMap();
      FileNode fn = new FileNode(this, f);
      this.children.put(f.getName(), fn);
   }

   protected DirectoryNode(JarFile jar, File tempLoc) {
      super(new File(jar.getName()), tempLoc);
      this.children = new HashMap();
      this.populate(jar);
   }

   private void populate(JarInputStream jis) throws IOException {
      for(JarEntry je = jis.getNextJarEntry(); je != null; je = jis.getNextJarEntry()) {
         String[] path = this.splitPath(je.getName());
         DirectoryNode p = (DirectoryNode)this.getNode(path, 0, path.length - 1);
         if (p == null) {
            p = this;
         }

         Object n;
         if (je.isDirectory()) {
            n = new DirectoryNode(p, path[path.length - 1]);
         } else {
            byte[] contents = this.getContents(jis, je);
            n = new EntryNode(p, path[path.length - 1], contents, je);
         }

         p.addNode((Node)n);
      }

      Manifest m = jis.getManifest();
      if (m != null) {
         DirectoryNode p = (DirectoryNode)this.children.get("META-INF");
         if (p == null) {
            p = new DirectoryNode(this, "META-INF");
            this.addNode(p);
         }

         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         m.write(baos);
         p.addNode(new EntryNode(p, "MANIFEST.MF", baos.toByteArray(), (JarEntry)null));
      }

   }

   protected byte[] getContents(JarInputStream jis, JarEntry je) throws IOException {
      int size = (int)je.getSize();
      return size > 0 ? this.getContentsOfKnownSize(jis, je, size) : this.getContentsOfUnknownSize(jis, je);
   }

   private byte[] getContentsOfUnknownSize(JarInputStream jis, JarEntry je) throws IOException {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();

      while(jis.available() == 1) {
         baos.write(jis.read());
      }

      return Arrays.copyOf(baos.toByteArray(), baos.size() - 1);
   }

   protected byte[] getContentsOfKnownSize(JarInputStream jis, JarEntry je, int size) throws IOException {
      byte[] contents = new byte[size];
      int bytesRead = jis.read(contents, 0, contents.length);

      for(int totalBytesRead = bytesRead; bytesRead != -1 && totalBytesRead != contents.length; totalBytesRead += bytesRead = jis.read(contents, totalBytesRead, contents.length - totalBytesRead)) {
      }

      return contents;
   }

   private void populate(JarFile jf) {
      this.populate(jf, FileFilters.ACCEPT_ALL);
   }

   protected void populate(JarFile jf, FileFilter filter) {
      Enumeration e = jf.entries();

      while(e.hasMoreElements()) {
         JarEntry je = (JarEntry)e.nextElement();
         String name = FilePathUtils.fixJarEntryOnWindows(je.getName());
         if (filter.accept(new File(name))) {
            String[] path = this.splitPath(name);
            DirectoryNode p = this.mkdir(CollectionUtils.iterator(path, 0, path.length - 1));
            Object n;
            if (je.isDirectory()) {
               n = new DirectoryNode(p, path[path.length - 1]);
            } else {
               n = new EntryNode(p, path[path.length - 1], jf, je);
            }

            p.addNode((Node)n);
         }
      }

   }

   private void addNode(Node n) {
      this.children.put(n.getShortName(), n);
   }

   protected Node getNode(String[] path, int i, int j) {
      if (i == j) {
         return this;
      } else {
         Node n = (Node)this.children.get(path[i++]);
         return n == null ? null : n.getNode(path, i, j);
      }
   }

   protected void populate(File directory, FileFilter filter) throws IOException {
      if (directory.isFile()) {
         this.children.put(directory.getName(), new FileNode(this, directory));
      } else {
         File[] files = directory.listFiles(filter);
         if (files == null) {
            String message = !directory.isDirectory() ? "This is not a valid directory: " : "An IO exception while listing this directory: ";
            throw new IOException(message + directory);
         }

         File[] var4 = files;
         int var5 = files.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            File f = var4[var6];
            Object n;
            if (f.isFile()) {
               n = new FileNode(this, f);
            } else {
               if (!f.isDirectory()) {
                  continue;
               }

               n = new DirectoryNode(this, f);
            }

            this.children.put(f.getName(), n);
         }
      }

   }

   protected StringBuilder toString(String indent, StringBuilder sb) {
      super.toString(indent, sb);
      Iterator var3 = this.children.values().iterator();

      while(var3.hasNext()) {
         Node n = (Node)var3.next();
         n.toString(indent + "  ", sb);
      }

      return sb;
   }

   protected Iterable children() {
      return this.children.values();
   }

   public InputStream getInputStream() {
      return null;
   }

   public long getSize() {
      return 0L;
   }

   public long getTime() {
      return 0L;
   }

   protected String[] splitPath(String path) {
      String[] elems = (path.charAt(0) == '/' ? path.substring(1) : path).split("/");
      return elems.length == 1 && elems[0].isEmpty() ? ROOT_PATH : elems;
   }

   public File asFile() {
      return this.getDiscLocation();
   }

   public void registerMapping(String[] uris, File path) throws IOException {
      Iterator uriIterator = Arrays.asList(uris).iterator();
      DirectoryNode dn = this.mkdir(uriIterator);
      if (this.isArchiveFile(path.getName())) {
         try {
            dn.populate(new JarFile(path));
         } catch (IOException var6) {
            dn.populate(path, ADE_FILTER);
         }
      } else {
         dn.populate(path, ADE_FILTER);
      }

   }

   private boolean isArchiveFile(String name) {
      int l = name.length();
      if (l >= 5) {
         String lastTwoChars = name.substring(l - 2);
         if (name.charAt(l - 4) == '.' && "ar".equalsIgnoreCase(lastTwoChars)) {
            return true;
         }
      }

      return false;
   }

   protected DirectoryNode mkdir(Iterator uriIterator) {
      if (uriIterator.hasNext()) {
         String name = (String)uriIterator.next();
         DirectoryNode dn = (DirectoryNode)this.children.get(name);
         if (dn == null) {
            dn = new DirectoryNode(this, name);
            this.children.put(name, dn);
         }

         return dn.mkdir(uriIterator);
      } else {
         return this;
      }
   }

   public boolean isDirectory() {
      return true;
   }

   static {
      ADE_FILTER = FileFilters.ADE_PATH;
      ROOT_PATH = new String[0];
   }

   public static enum AcceptAllFilter implements FileFilter {
      SINGLETON;

      public boolean accept(File arg0) {
         return true;
      }
   }
}
