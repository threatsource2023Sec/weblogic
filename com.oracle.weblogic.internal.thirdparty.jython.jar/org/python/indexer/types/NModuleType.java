package org.python.indexer.types;

import org.python.indexer.Indexer;
import org.python.indexer.Scope;
import org.python.indexer.Util;

public class NModuleType extends NType {
   private String file;
   private String name;
   private String qname;

   public NModuleType() {
   }

   public NModuleType(String name, String file, Scope parent) {
      this.name = name;
      this.file = file;
      if (file != null) {
         this.qname = Util.moduleQname(file);
      }

      if (this.qname == null) {
         this.qname = name;
      }

      this.setTable(new Scope(parent, Scope.Type.MODULE));
      this.getTable().setPath(this.qname);
      if (Indexer.idx.builtins != null) {
         this.getTable().addSuper(Indexer.idx.builtins.BaseModule.getTable());
      }

   }

   public void setFile(String file) {
      this.file = file;
   }

   public String getFile() {
      return this.file;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public void setQname(String qname) {
      this.qname = qname;
   }

   public String getQname() {
      return this.qname;
   }

   public void printKids(NType.CyclicTypeRecorder ctr, StringBuilder sb) {
      sb.append(this.qname);
   }
}
