package org.python.indexer.ast;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.python.indexer.Indexer;
import org.python.indexer.NBinding;
import org.python.indexer.Scope;
import org.python.indexer.types.NModuleType;
import org.python.indexer.types.NType;
import org.python.indexer.types.NUnknownType;

public class NImportFrom extends NNode {
   static final long serialVersionUID = 5070549408963950138L;
   public String module;
   public NQname qname;
   public List aliases;

   public NImportFrom(String module, NQname qname, List aliases) {
      this(module, qname, aliases, 0, 1);
   }

   public NImportFrom(String module, NQname qname, List aliases, int start, int end) {
      super(start, end);
      this.module = module;
      this.qname = qname;
      this.aliases = aliases;
      this.addChildren(new NNode[]{qname});
      this.addChildren(aliases);
   }

   public boolean bindsName() {
      return true;
   }

   protected void bindNames(Scope s) throws Exception {
      if (!this.isImportStar()) {
         NImport.bindAliases(s, this.aliases);
      }
   }

   public NType resolve(Scope s) throws Exception {
      Scope scope = s.getScopeSymtab();
      resolveExpr(this.qname, s);
      NType bottomType = this.qname.getBottom().getType();
      if (!bottomType.isModuleType()) {
         return this.setType(new NUnknownType());
      } else {
         NModuleType mt = (NModuleType)bottomType;
         this.setType(mt);
         NImport.addReferences(s, this.qname, false);
         if (this.isImportStar()) {
            this.importStar(s, mt);
            return this.getType();
         } else {
            Iterator var5 = this.aliases.iterator();

            while(var5.hasNext()) {
               NAlias a = (NAlias)var5.next();
               this.resolveAlias(scope, mt, a);
            }

            return this.getType();
         }
      }
   }

   public boolean isImportStar() {
      return this.aliases.size() == 1 && "*".equals(((NAlias)this.aliases.get(0)).name);
   }

   private void resolveAlias(Scope scope, NModuleType mt, NAlias a) throws Exception {
      NBinding entry = mt.getTable().lookup(a.name);
      String qname;
      if (entry == null) {
         qname = this.qname.toQname() + "." + a.qname.toQname();
         NModuleType mt2 = Indexer.idx.loadModule(qname);
         if (mt2 != null) {
            entry = Indexer.idx.lookupQname(mt2.getTable().getPath());
         }
      }

      if (entry == null) {
         this.addError(a, "name " + a.qname.getName().id + " not found in module " + this.module);
      } else {
         qname = a.qname.getName().id;
         String aname = a.aname != null ? a.aname.id : null;
         Indexer.idx.putLocation((NNode)a.qname.getName(), entry);
         if (aname != null) {
            Indexer.idx.putLocation((NNode)a.aname, entry);
            scope.put(aname, entry);
         } else {
            scope.put(qname, entry);
         }

      }
   }

   private void importStar(Scope s, NModuleType mt) throws Exception {
      if (mt != null && mt.getFile() != null) {
         NModule mod = Indexer.idx.getAstForFile(mt.getFile());
         if (mod != null) {
            List names = mod.getExportedNames();
            Iterator var5;
            if (!names.isEmpty()) {
               var5 = names.iterator();

               while(var5.hasNext()) {
                  String name = (String)var5.next();
                  NBinding nb = mt.getTable().lookupLocal(name);
                  if (nb != null) {
                     s.put(name, nb);
                  }
               }
            } else {
               var5 = mt.getTable().entrySet().iterator();

               while(var5.hasNext()) {
                  Map.Entry e = (Map.Entry)var5.next();
                  if (!((String)e.getKey()).startsWith("_")) {
                     s.put((String)e.getKey(), (NBinding)e.getValue());
                  }
               }
            }

         }
      }
   }

   public String toString() {
      return "<FromImport:" + this.module + ":" + this.aliases + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.qname, v);
         this.visitNodeList(this.aliases, v);
      }

   }
}
