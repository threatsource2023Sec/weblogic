package weblogic.application.archive.collage.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import weblogic.application.archive.collage.Directory;
import weblogic.application.archive.collage.Node;

public class ContainerImpl extends NodeImpl implements Directory {
   private Map children = new HashMap();

   protected ContainerImpl(ContainerImpl parent, String name, long time, String source) {
      super(parent, name, time, source);
   }

   protected void addChild(Node a) {
      this.children.put(a.getName(), a);
   }

   public Node get(String name) {
      return (Node)(name.isEmpty() ? this : (Node)this.children.get(name));
   }

   public Collection getAll() {
      return this.children.values();
   }
}
