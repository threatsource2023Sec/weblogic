package weblogic.application.archive.navigator;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.CodeSigner;
import java.security.cert.Certificate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class EntryNode extends Node {
   private final JarFile jarFile;
   private final JarEntry entry;
   private DirectoryNode directoryNode;
   private byte[] contents;
   private File explodedEntry;

   protected EntryNode(Node parent, String name, JarFile jarFile, JarEntry jarEntry) {
      super(parent, name);
      this.jarFile = jarFile;
      this.entry = jarEntry;
   }

   public EntryNode(DirectoryNode parent, String name, byte[] bytes, JarEntry je) {
      this((Node)parent, name, (JarFile)((JarFile)null), je);
      this.contents = bytes;
   }

   public InputStream getInputStream() throws IOException {
      return (InputStream)(this.jarFile == null ? new ByteArrayInputStream(this.contents) : this.jarFile.getInputStream(this.entry));
   }

   public long getSize() {
      return this.contents == null ? this.entry.getSize() : (long)this.contents.length;
   }

   public Certificate[] getCertificates() {
      return this.entry == null ? null : this.entry.getCertificates();
   }

   public CodeSigner[] getCodeSigners() {
      return this.entry == null ? null : this.entry.getCodeSigners();
   }

   public long getTime() {
      return this.entry == null ? 0L : this.entry.getTime();
   }

   public DirectoryNode getDirectoryNode() {
      if (this.directoryNode == null) {
         try {
            this.directoryNode = new DirectoryNode(this);
         } catch (IOException var2) {
            throw new RuntimeException(var2);
         }
      }

      return this.directoryNode;
   }

   public File asFile() {
      if (this.explodedEntry == null) {
         this.explodedEntry = this.virtualLocation;

         try {
            this.virtualLocation.getParentFile().mkdirs();
            FileOutputStream fos = new FileOutputStream(this.virtualLocation);
            InputStream is = this.getInputStream();

            for(int c = is.read(); c != -1; c = is.read()) {
               fos.write(c);
            }

            fos.close();
         } catch (IOException var4) {
            throw new RuntimeException(var4);
         }
      }

      return this.explodedEntry;
   }
}
