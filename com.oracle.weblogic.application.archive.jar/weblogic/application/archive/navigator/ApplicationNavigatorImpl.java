package weblogic.application.archive.navigator;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.jar.JarFile;

public class ApplicationNavigatorImpl implements ApplicationNavigator {
   private File writeableRootDir;
   private DirectoryNode rootNode;
   private File archive;

   protected ApplicationNavigatorImpl(File archive, File writeableRoot) throws IOException {
      if (archive == null) {
         throw new IOException("Specify a valid archive");
      } else {
         this.resetWritableRootDir(writeableRoot);
         this.archive = archive;
         this.init(archive);
      }
   }

   public weblogic.application.archive.collage.Node getNode(String... uri) {
      return this.rootNode.getNode(uri, 0, uri.length);
   }

   public Iterable listNode(String[] path, FileFilter filter) {
      Node node = (Node)this.getNode(path);
      if (node == null) {
         return null;
      } else if (node instanceof DirectoryNode) {
         Iterable it = ((DirectoryNode)node).listNodes(filter, this.rootNode);
         IteratorIterable.Transformer tr = new IteratorIterable.Transformer() {
            public weblogic.application.archive.collage.Node transform(Node obj) {
               return obj;
            }
         };
         return new IteratorIterable(it.iterator(), tr);
      } else {
         throw new IllegalArgumentException("Path points to a leaf node");
      }
   }

   public Iterable find(String[] path, FileFilter filter) {
      DirectoryNode dn = (DirectoryNode)this.getNode(path);
      Iterator nodeTraverser = new NodeTraverser(this.rootNode, dn, filter);
      return new IteratorIterable(nodeTraverser, (IteratorIterable.Transformer)null);
   }

   private void init(File archive) throws IOException {
      switch (this.getArchiveType(archive)) {
         case dir:
            this.initAsDirectory(archive);
            break;
         case split_dir:
            this.initAsSplitSource(this.getSplitDirFile(archive));
            break;
         case collage:
            this.initAsCollage(archive);
            break;
         case jar:
            this.initAsJarFile(archive);
            break;
         case simple_file:
            this.initAsSimpleFile(archive);
            break;
         default:
            throw new IOException("Unsupported archive: " + archive);
      }

   }

   private ArchiveType getArchiveType(File archive) throws IOException {
      if (archive.isDirectory()) {
         return this.getSplitDirFile(archive) == null ? ApplicationNavigatorImpl.ArchiveType.dir : ApplicationNavigatorImpl.ArchiveType.split_dir;
      } else {
         String name = archive.getName();
         int l = name.length();
         if (l >= 5) {
            String lastTwoChars = this.getLastChars(name, 2);
            if (name.charAt(l - 4) == '.' && "ar".equalsIgnoreCase(lastTwoChars)) {
               return ApplicationNavigatorImpl.ArchiveType.jar;
            }

            if ("-app.xml".equalsIgnoreCase(this.getLastChars(name, 8))) {
               return ApplicationNavigatorImpl.ArchiveType.collage;
            }
         }

         return ApplicationNavigatorImpl.ArchiveType.simple_file;
      }
   }

   protected File getSplitDirFile(File archive) {
      File[] splitDirFiles = archive.listFiles(new FilenameFilter() {
         public boolean accept(File dir, String name) {
            return name.endsWith(".beabuild.txt");
         }
      });
      return splitDirFiles != null && splitDirFiles.length != 0 ? splitDirFiles[0] : null;
   }

   private void initAsSimpleFile(File f) {
      this.rootNode = new DirectoryNode(f);
   }

   private ApplicationNavigatorImpl(DirectoryNode newRoot, DirectoryNode oldRoot, File archivePath, File newWriteableRootDir) throws IOException {
      if (newRoot == null) {
         throw new IOException("Unable to create ApplicationNavigator for path: " + archivePath.getPath());
      } else {
         this.rootNode = newRoot;
         this.writeableRootDir = newWriteableRootDir;
         this.archive = archivePath;
      }
   }

   protected ApplicationNavigatorImpl(File archive) throws IOException {
      this.writeableRootDir = null;
      this.archive = archive;
      this.init(archive);
   }

   protected File getArchive() {
      return this.archive;
   }

   protected ApplicationNavigatorImpl createApplicationNavigator(String... path) throws IOException {
      Node nd = (Node)this.getNode(path);
      String pathString = this.toPathString(path);
      File newWriteableRoot = new File(this.writeableRootDir, pathString);
      newWriteableRoot.mkdirs();
      DirectoryNode newRoot;
      File newArchive;
      if (nd instanceof EntryNode) {
         newRoot = ((EntryNode)nd).getDirectoryNode();
         newArchive = newWriteableRoot;
      } else {
         if (nd instanceof FileNode) {
            return new ApplicationNavigatorImpl(((FileNode)nd).file, newWriteableRoot);
         }

         newRoot = (DirectoryNode)nd;
         newArchive = newWriteableRoot;
      }

      return new ApplicationNavigatorImpl(newRoot, this.rootNode, newArchive, new File(this.writeableRootDir, pathString));
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

   private void initAsSplitSource(File splitSrcDesc) throws IOException {
      File srcDir = splitSrcDesc.getParentFile();
      Properties props = new Properties();
      props.load(new FileInputStream(splitSrcDesc));
      File buildDir = new File(props.getProperty("bea.srcdir"));
      this.rootNodeForSplitDir(srcDir, buildDir);
   }

   private DirectoryNode rootNodeForSplitDir(File highPrecedenceSrcDirectory, File buildDirectory) throws IOException {
      DirectoryNode dn = new DirectoryNode(highPrecedenceSrcDirectory, this.writeableRootDir);
      dn.populate(buildDirectory, DirectoryNode.ADE_FILTER);
      return dn;
   }

   private void initAsCollage(File archive) throws IOException {
      throw new IOException("Collage archive not supported");
   }

   private void initAsJarFile(File archive) throws IOException {
      this.rootNode = this.rootNodeForJarFile(new JarFile(archive, false));
   }

   private DirectoryNode rootNodeForJarFile(JarFile jar) throws IOException {
      File tmpLoc = File.createTempFile("wls_tmp-", "", this.writeableRootDir);
      File tmpDir = new File(tmpLoc.getParentFile(), tmpLoc.getName() + ".dir");
      tmpDir.mkdir();
      return new DirectoryNode(jar, tmpDir);
   }

   private void initAsDirectory(File archive) throws IOException {
      this.rootNode = new DirectoryNode(archive, this.writeableRootDir);
   }

   private String getLastChars(String name, int n) {
      int l = name.length();
      return l >= n ? name.substring(l - n, l) : null;
   }

   public File getWriteableRootDir() {
      if (this.writeableRootDir == null) {
         throw new IllegalStateException();
      } else {
         return this.writeableRootDir;
      }
   }

   public void registerMapping(String[] uris, File path) throws IOException {
      this.rootNode.registerMapping(uris, path);
   }

   public String getName() {
      return this.getArchive().getPath();
   }

   public void resetWritableRootDir(File writableRoot) {
      if (writableRoot != null && writableRoot.isDirectory()) {
         if (this.writeableRootDir != null && !this.writeableRootDir.equals(writableRoot)) {
            throw new IllegalArgumentException("Writable root may only be reset if it is currently null. Current value: " + this.writeableRootDir + ", New value: " + writableRoot);
         } else {
            this.writeableRootDir = writableRoot;
         }
      } else {
         throw new IllegalArgumentException("appRoot, " + writableRoot + " must be a valid directory");
      }
   }

   static enum ArchiveType {
      jar,
      dir,
      split_dir,
      collage,
      simple_file;
   }

   protected static class NodeTraverser extends IteratorIterable.AbstractIterator {
      private final Iterator nodes;
      private Node currentNode;
      private boolean tryAgain = true;
      private NodeTraverser currentNodeIterator;
      private final FileFilter ff;
      private final DirectoryNode rootNode;

      protected NodeTraverser(DirectoryNode rootNode, DirectoryNode dn, FileFilter fileFilter) {
         this.ff = fileFilter;
         this.nodes = dn.children().iterator();
         this.rootNode = rootNode;
      }

      public boolean hasNext() {
         if (this.currentNode == null) {
            this.tryAgain = true;
            if (this.nodes.hasNext()) {
               this.next = this.currentNode = (Node)this.nodes.next();
               if (this.currentNode instanceof DirectoryNode) {
                  this.currentNodeIterator = new NodeTraverser(this.rootNode, (DirectoryNode)this.currentNode, this.ff);
               } else {
                  this.currentNodeIterator = null;
               }

               return this.ff.accept(((Node)this.next).getFile(this.rootNode)) ? true : this.skipNode();
            }
         } else {
            if (this.currentNodeIterator != null && this.currentNodeIterator.hasNext()) {
               this.next = this.currentNodeIterator.next();
               return this.ff.accept(((Node)this.next).getFile(this.rootNode)) ? true : this.hasNext();
            }

            if (this.tryAgain) {
               this.currentNode = null;
               this.tryAgain = false;
               return this.hasNext();
            }
         }

         this.next = null;
         return false;
      }

      private boolean skipNode() {
         this.currentNode = null;
         return this.hasNext();
      }
   }
}
