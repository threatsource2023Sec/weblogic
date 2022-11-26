package org.python.indexer.ast;

import org.python.indexer.Indexer;
import org.python.indexer.NBinding;
import org.python.indexer.Scope;
import org.python.indexer.types.NType;
import org.python.indexer.types.NUnknownType;

public class NName extends NNode {
   static final long serialVersionUID = -1160862551327528304L;
   public final String id;

   public NName(String id) {
      this(id, 0, 1);
   }

   public NName(String id, int start, int end) {
      super(start, end);
      if (id == null) {
         throw new IllegalArgumentException("'id' param cannot be null");
      } else {
         this.id = id;
      }
   }

   public NType resolve(Scope s) throws Exception {
      NBinding b = s.lookup(this.id);
      if (b == null) {
         b = this.makeTempBinding(s);
      }

      Indexer.idx.putLocation((NNode)this, b);
      return this.setType(b.followType());
   }

   public boolean isCall() {
      if (this.parent != null && this.parent.isCall() && this == ((NCall)this.parent).func) {
         return true;
      } else {
         NNode gramps;
         return this.parent instanceof NAttribute && this == ((NAttribute)this.parent).attr && (gramps = this.parent.parent) instanceof NCall && this.parent == ((NCall)gramps).func;
      }
   }

   private NBinding makeTempBinding(Scope s) {
      Scope scope = s.getScopeSymtab();
      NBinding b = scope.put(this.id, this, new NUnknownType(), NBinding.Kind.SCOPE);
      this.setType(b.getType().follow());
      this.getTable().setPath(scope.extendPath(this.id));
      return b;
   }

   public boolean isAttribute() {
      return this.parent instanceof NAttribute && ((NAttribute)this.parent).getAttr() == this;
   }

   public String toString() {
      return "<Name:" + this.start() + ":" + this.id + ">";
   }

   public void visit(NNodeVisitor v) {
      v.visit(this);
   }
}
