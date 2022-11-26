package org.python.indexer.ast;

import java.util.Iterator;
import java.util.List;
import org.python.indexer.Indexer;
import org.python.indexer.NBinding;
import org.python.indexer.Scope;
import org.python.indexer.types.NType;
import org.python.indexer.types.NUnknownType;

public class NGlobal extends NNode {
   static final long serialVersionUID = 5978320165592263568L;
   public List names;

   public NGlobal(List names) {
      this(names, 0, 1);
   }

   public NGlobal(List names, int start, int end) {
      super(start, end);
      this.names = names;
      this.addChildren(names);
   }

   public NType resolve(Scope s) throws Exception {
      Scope moduleTable = s.getGlobalTable();
      Iterator var3 = this.names.iterator();

      while(var3.hasNext()) {
         NName name = (NName)var3.next();
         if (!s.isGlobalName(name.id)) {
            s.addGlobalName(name.id);
            NBinding b = moduleTable.lookup((NNode)name);
            if (b == null) {
               b = moduleTable.put(name.id, (NNode)null, new NUnknownType(), NBinding.Kind.SCOPE);
            }

            Indexer.idx.putLocation((NNode)name, b);
         }
      }

      return this.getType();
   }

   public String toString() {
      return "<Global:" + this.names + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNodeList(this.names, v);
      }

   }
}
