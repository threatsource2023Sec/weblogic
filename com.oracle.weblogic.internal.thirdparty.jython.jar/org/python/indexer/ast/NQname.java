package org.python.indexer.ast;

import java.io.File;
import org.python.indexer.Indexer;
import org.python.indexer.NBinding;
import org.python.indexer.Scope;
import org.python.indexer.Util;
import org.python.indexer.types.NModuleType;
import org.python.indexer.types.NType;
import org.python.indexer.types.NUnknownType;

public class NQname extends NNode {
   static final long serialVersionUID = -5892553606852895686L;
   private NQname next;
   private NName name;

   public NQname(NQname next, NName name) {
      this(next, name, 0, 1);
   }

   public NQname(NQname next, NName name, int start, int end) {
      super(start, end);
      if (name == null) {
         throw new IllegalArgumentException("null name");
      } else {
         this.name = name;
         this.next = next;
         this.addChildren(new NNode[]{name, next});
      }
   }

   public NName getName() {
      return this.name;
   }

   public NQname getPrevious() {
      NNode parent = this.getParent();
      return parent instanceof NQname ? (NQname)parent : null;
   }

   public NQname getNext() {
      return this.next;
   }

   public NQname getBottom() {
      return this.next == null ? this : this.next.getBottom();
   }

   public boolean isTop() {
      return this.getPrevious() == null;
   }

   public boolean isBottom() {
      return this.next == null;
   }

   public boolean isUnqualified() {
      return this.isTop() && this.isBottom();
   }

   public String toQname() {
      return this.isBottom() ? this.name.id : this.name.id + "." + this.next.toQname();
   }

   public String thisQname() {
      NQname n = this.getTop();
      StringBuilder sb = new StringBuilder();
      sb.append(n.name.id);

      while(n != this) {
         sb.append(".");
         n = n.next;
         sb.append(n.name.id);
      }

      return sb.toString();
   }

   public NQname getTop() {
      return this.isTop() ? this : this.getPrevious().getTop();
   }

   public boolean isDot() {
      return ".".equals(this.name.id);
   }

   public NType resolve(Scope s) throws Exception {
      this.setType(this.name.setType(new NUnknownType()));
      NModuleType mt;
      if (this.isUnqualified()) {
         mt = Indexer.idx.loadModule(this.name.id);
         if (mt != null) {
            return this.setType(this.name.setType(mt));
         }
      } else {
         mt = Indexer.idx.getBuiltinModule(this.thisQname());
         if (mt != null) {
            this.setType(this.name.setType(mt));
            resolveExpr(this.next, s);
            return mt;
         }
      }

      return this.resolveInFilesystem(s);
   }

   private NType resolveInFilesystem(Scope s) throws Exception {
      NModuleType start = this.getStartModule(s);
      if (start == null) {
         this.reportUnresolvedModule();
         return this.getType();
      } else {
         String qname = start.getTable().getPath();
         String relQname;
         if (this.isDot()) {
            relQname = Util.getQnameParent(qname);
         } else if (!this.isTop()) {
            relQname = qname + "." + this.name.id;
         } else {
            String dirQname = this.isInitPy() ? qname : Util.getQnameParent(qname);
            relQname = dirQname + "." + this.name.id;
            if (Indexer.idx.loadModule(relQname) == null) {
               relQname = this.name.id;
            }
         }

         NModuleType mod = Indexer.idx.loadModule(relQname);
         if (mod == null) {
            this.reportUnresolvedModule();
            return this.getType();
         } else {
            this.setType(this.name.setType(mod));
            if (!this.isTop() && mod.getFile() != null) {
               Scope parentPkg = this.getPrevious().getTable();
               NBinding mb = Indexer.idx.moduleTable.lookup(mod.getFile());
               parentPkg.put(this.name.id, mb);
            }

            resolveExpr(this.next, s);
            return this.getType();
         }
      }
   }

   private boolean isInitPy() {
      String path = this.getFile();
      return path == null ? false : (new File(path)).getName().equals("__init__.py");
   }

   private NModuleType getStartModule(Scope s) throws Exception {
      if (!this.isTop()) {
         return this.getPrevious().getType().asModuleType();
      } else {
         NModuleType start = null;
         Scope mtable = s.getSymtabOfType(Scope.Type.MODULE);
         if (mtable != null) {
            start = Indexer.idx.loadModule(mtable.getPath());
            if (start != null) {
               return start;
            }
         }

         String dir = (new File(this.getFile())).getParent();
         if (dir == null) {
            Indexer.idx.warn("Unable to find parent dir for " + this.getFile());
            return null;
         } else {
            return Indexer.idx.loadModule(dir);
         }
      }
   }

   private void reportUnresolvedModule() {
      this.addError("module not found: " + this.name.id);
      Indexer.idx.recordUnresolvedModule(this.thisQname(), this.getFile());
   }

   public String toString() {
      return "<QName:" + this.name + ":" + this.next + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.next, v);
         this.visitNode(this.name, v);
      }

   }
}
