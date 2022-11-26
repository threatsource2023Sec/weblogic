package org.python.indexer.types;

import org.python.indexer.Scope;

public class NClassType extends NType {
   private String name;

   public NClassType() {
      this("<unknown>", (Scope)null);
   }

   public NClassType(String name, Scope parent) {
      this.name = name;
      this.setTable(new Scope(parent, Scope.Type.CLASS));
      if (parent != null) {
         this.getTable().setPath(parent.extendPath(name));
      } else {
         this.getTable().setPath(name);
      }

   }

   public NClassType(String name, Scope parent, NClassType superClass) {
      this(name, parent);
      if (superClass != null) {
         this.addSuper(superClass);
      }

   }

   public void setName(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public void addSuper(NType sp) {
      this.getTable().addSuper(sp.getTable());
   }

   public void printKids(NType.CyclicTypeRecorder ctr, StringBuilder sb) {
      sb.append(this.name);
   }
}
