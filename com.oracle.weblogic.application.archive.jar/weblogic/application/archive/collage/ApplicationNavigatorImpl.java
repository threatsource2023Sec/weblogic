package weblogic.application.archive.collage;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.CodeSigner;
import java.security.cert.Certificate;
import java.util.Iterator;
import weblogic.application.archive.ApplicationArchiveEntry;
import weblogic.application.archive.collage.impl.DirectoryImpl;
import weblogic.application.archive.collage.impl.Helper;
import weblogic.application.archive.navigator.ApplicationNavigator;
import weblogic.application.archive.navigator.IteratorIterable;
import weblogic.application.archive.utils.FileFilters;

public class ApplicationNavigatorImpl implements ApplicationNavigator {
   private static Helper h;
   private Directory directory;
   private File writeableRoot;
   private static IteratorIterable.Transformer transformer;

   public ApplicationNavigatorImpl(Directory aDirectory, File aWriteableRoot) {
      this.directory = aDirectory;
      this.writeableRoot = aWriteableRoot;
   }

   public Node getNode(String... uri) {
      Directory d = this.directory;
      Node n = null;
      String[] var4 = uri;
      int var5 = uri.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String name = var4[var6];
         n = d.get(name);
         if (n == null) {
            return null;
         }
      }

      return n;
   }

   public Iterable listNode(String[] path, FileFilter filter) {
      Node n = this.getNode(path);
      return n instanceof Directory ? h.listNodes((Directory)n, filter) : null;
   }

   public Iterable find(String[] path, FileFilter filter) {
      Directory d = (Directory)this.getNode(path);
      Iterator nodeTraverser = new NodeTraverser(d, filter);
      return new IteratorIterable(nodeTraverser, transformer);
   }

   public File getWriteableRootDir() {
      return this.writeableRoot;
   }

   public void registerMapping(String[] uris, File path) throws IOException {
      Directory d = (Directory)this.getNode(uris);
      h.populate((DirectoryImpl)d, path, FileFilters.ADE_PATH);
   }

   public String getName() {
      return this.directory.getFile().getPath();
   }

   public void resetWritableRootDir(File writableRoot) {
      if (writableRoot != null && writableRoot.isDirectory()) {
         if (this.writeableRoot != null && !this.writeableRoot.equals(writableRoot)) {
            throw new IllegalArgumentException("Writable root may only be reset if it is currently null. Current value: " + this.writeableRoot + ", New value: " + writableRoot);
         } else {
            this.writeableRoot = writableRoot;
         }
      } else {
         throw new IllegalArgumentException("appRoot, " + writableRoot + " must be a valid directory");
      }
   }

   static {
      h = Helper.Singleton;
      transformer = new IteratorIterable.Transformer() {
         public ApplicationArchiveEntry transform(final Node node) {
            return new ApplicationArchiveEntry() {
               public String getName() {
                  return node.getName();
               }

               public boolean isDirectory() {
                  return node instanceof Directory;
               }

               public long getSize() {
                  return this.isDirectory() ? 0L : ((Artifact)node).getTime();
               }

               public long getTime() {
                  return node.getTime();
               }

               public Certificate[] getCertificates() {
                  return null;
               }

               public CodeSigner[] getCodeSigners() {
                  return null;
               }

               public InputStream getInputStream() throws IOException {
                  return this.isDirectory() ? null : ((Artifact)node).getInputStream();
               }

               public URL getURL() throws IOException {
                  return null;
               }

               /** @deprecated */
               @Deprecated
               public String getPath() {
                  return node.getFile().getPath();
               }
            };
         }
      };
   }

   protected static class NodeTraverser extends IteratorIterable.AbstractIterator {
      private final Iterator nodes;
      private Node currentNode;
      private boolean tryAgain = true;
      private NodeTraverser currentNodeIterator;
      private final FileFilter ff;

      protected NodeTraverser(Directory d, FileFilter fileFilter) {
         this.ff = fileFilter;
         this.nodes = d.getAll().iterator();
      }

      public boolean hasNext() {
         if (this.currentNode == null) {
            this.tryAgain = true;
            if (this.nodes.hasNext()) {
               this.next = this.currentNode = (Node)this.nodes.next();
               if (this.currentNode instanceof Directory) {
                  this.currentNodeIterator = new NodeTraverser((Directory)this.currentNode, this.ff);
               } else {
                  this.currentNodeIterator = null;
               }

               return this.ff.accept(((Node)this.next).getFile()) ? true : this.skipNode();
            }
         } else {
            if (this.currentNodeIterator != null && this.currentNodeIterator.hasNext()) {
               this.next = this.currentNodeIterator.next();
               return this.ff.accept(((Node)this.next).getFile()) ? true : this.hasNext();
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
