package weblogic.application.archive.navigator;

import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import weblogic.application.archive.ApplicationArchiveEntry;

public class JarEntryProxy extends JarEntry {
   private DirectoryNode rootNode;
   Node node;

   protected JarEntryProxy(ApplicationArchiveEntry aae, DirectoryNode rootNode) {
      super(((Node)aae).getPath(rootNode));
      this.node = (Node)aae;
      this.rootNode = rootNode;
      this.setTime(this.node.getTime());
      this.setSize(this.node.getSize());
   }

   public Attributes getAttributes() throws IOException {
      throw new UnsupportedOperationException();
   }

   public boolean isDirectory() {
      return this.node.isDirectory();
   }
}
