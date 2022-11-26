package weblogic.application.archive.collage.impl;

import java.io.File;
import weblogic.application.archive.collage.Node;

public abstract class NodeImpl implements Node {
   private File applicationUri;
   private String name;
   private long time;
   private ContainerImpl parent;
   private String source;

   public NodeImpl(ContainerImpl aParent, String aName, long aTime, String aSource) {
      this.parent = aParent;
      this.name = aName;
      this.source = aSource;
      this.time = aTime;
   }

   public String getName() {
      return this.name;
   }

   public File getFile() {
      if (this.applicationUri == null) {
         this.applicationUri = this.parent == null ? new File(this.name) : new File(this.parent.getFile(), this.name);
      }

      return this.applicationUri;
   }

   public long getTime() {
      return this.time;
   }

   public String toString() {
      return this.getFile().getPath() + " -> " + this.getSource() + " (" + super.toString().substring(37) + " )";
   }

   public String getSource() {
      return this.source;
   }
}
