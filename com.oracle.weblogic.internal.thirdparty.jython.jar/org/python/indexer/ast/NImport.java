package org.python.indexer.ast;

import java.util.Iterator;
import java.util.List;
import org.python.indexer.Indexer;
import org.python.indexer.NBinding;
import org.python.indexer.Scope;
import org.python.indexer.types.NModuleType;
import org.python.indexer.types.NType;
import org.python.indexer.types.NUnknownType;

public class NImport extends NNode {
   static final long serialVersionUID = -2180402676651342012L;
   public List aliases;

   public NImport(List aliases) {
      this(aliases, 0, 1);
   }

   public NImport(List aliases, int start, int end) {
      super(start, end);
      this.aliases = aliases;
      this.addChildren(aliases);
   }

   public boolean bindsName() {
      return true;
   }

   protected void bindNames(Scope s) throws Exception {
      bindAliases(s, this.aliases);
   }

   static void bindAliases(Scope s, List aliases) throws Exception {
      NameBinder binder = NameBinder.make();
      Iterator var3 = aliases.iterator();

      while(var3.hasNext()) {
         NAlias a = (NAlias)var3.next();
         if (a.aname != null) {
            binder.bind(s, (NNode)a.aname, new NUnknownType());
         }
      }

   }

   public NType resolve(Scope s) throws Exception {
      Scope scope = s.getScopeSymtab();
      Iterator var3 = this.aliases.iterator();

      while(var3.hasNext()) {
         NAlias a = (NAlias)var3.next();
         NType modtype = resolveExpr(a, s);
         if (modtype.isModuleType()) {
            this.importName(scope, a, modtype.asModuleType());
         }
      }

      return this.getType();
   }

   private void importName(Scope s, NAlias a, NModuleType mt) throws Exception {
      if (a.aname != null) {
         if (mt.getFile() != null) {
            NameBinder.make().bind(s, (NNode)a.aname, mt);
         } else {
            s.update(a.aname.id, (NNode)(new NUrl("http://docs.python.org/library/" + mt.getTable().getPath() + ".html")), mt, NBinding.Kind.SCOPE);
         }
      }

      addReferences(s, a.qname, true);
   }

   static void addReferences(Scope s, NQname qname, boolean putTopInScope) {
      if (qname != null) {
         if (qname.getType().isModuleType()) {
            NModuleType mt = qname.getType().asModuleType();
            String modQname = mt.getTable().getPath();
            NBinding mb = Indexer.idx.lookupQname(modQname);
            if (mb == null) {
               mb = Indexer.idx.moduleTable.lookup(modQname);
            }

            if (mb == null) {
               Indexer.idx.putProblem(qname.getName(), "module not found");
            } else {
               Indexer.idx.putLocation((NNode)qname.getName(), mb);
               if (putTopInScope && qname.isTop()) {
                  s.put(qname.getName().id, mb);
               }

               addReferences(s, qname.getNext(), false);
            }
         }
      }
   }

   public String toString() {
      return "<Import:" + this.aliases + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNodeList(this.aliases, v);
      }

   }
}
