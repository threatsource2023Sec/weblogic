package org.python.indexer.ast;

import java.util.Iterator;
import org.python.indexer.Indexer;
import org.python.indexer.NBinding;
import org.python.indexer.Scope;
import org.python.indexer.types.NType;
import org.python.indexer.types.NUnionType;
import org.python.indexer.types.NUnknownType;

public class NAttribute extends NNode {
   static final long serialVersionUID = -1120979305017812255L;
   public NNode target;
   public NName attr;

   public NAttribute(NNode target, NName attr) {
      this(target, attr, 0, 1);
   }

   public NAttribute(NNode target, NName attr, int start, int end) {
      super(start, end);
      this.setTarget(target);
      this.setAttr(attr);
      this.addChildren(new NNode[]{target, attr});
   }

   public String getAttributeName() {
      return this.attr.id;
   }

   public void setAttr(NName attr) {
      if (attr == null) {
         throw new IllegalArgumentException("param cannot be null");
      } else {
         this.attr = attr;
      }
   }

   public NName getAttr() {
      return this.attr;
   }

   public void setTarget(NNode target) {
      if (target == null) {
         throw new IllegalArgumentException("param cannot be null");
      } else {
         this.target = target;
      }
   }

   public NNode getTarget() {
      return this.target;
   }

   public void setAttr(Scope s, NType v) throws Exception {
      this.setType(new NUnknownType());
      NType targetType = resolveExpr(this.target, s);
      if (targetType.isUnionType()) {
         targetType = targetType.asUnionType().firstKnownNonNullAlternate();
         if (targetType == null) {
            return;
         }
      }

      targetType = targetType.follow();
      if (targetType != Indexer.idx.builtins.None) {
         NBinding b = targetType.getTable().putAttr(this.attr.id, this.attr, v, NBinding.Kind.ATTRIBUTE);
         if (b != null) {
            this.setType(this.attr.setType(b.followType()));
         }

      }
   }

   public NType resolve(Scope s) throws Exception {
      this.setType(new NUnknownType());
      NType targetType = resolveExpr(this.target, s);
      if (targetType.isUnionType()) {
         NType ret = new NUnknownType();

         for(Iterator var4 = targetType.asUnionType().getTypes().iterator(); var4.hasNext(); ret = NUnionType.union((NType)ret, this.getType())) {
            NType tp = (NType)var4.next();
            this.resolveAttributeOnType(tp);
         }

         this.setType(this.attr.setType(((NType)ret).follow()));
      } else {
         this.resolveAttributeOnType(targetType);
      }

      return this.getType();
   }

   private void resolveAttributeOnType(NType targetType) {
      NType ttype = targetType.follow();
      NBinding b = ttype.getTable().lookupAttr(this.attr.id);
      if (b == null) {
         b = this.makeProvisionalBinding(ttype);
      }

      if (b != null) {
         Indexer.idx.putLocation((NNode)this.attr, b);
         this.setType(this.attr.setType(b.getType()));
      }

   }

   private NBinding makeProvisionalBinding(NType targetType) {
      if (targetType.isNative()) {
         return null;
      } else {
         Scope targetScope = targetType.getTable();
         if ("".equals(targetScope.getPath())) {
            return null;
         } else {
            NType utype = new NUnknownType();
            NBinding b = targetScope.putAttr(this.attr.id, (NNode)null, utype, NBinding.Kind.ATTRIBUTE);
            if (b != null) {
               b.setProvisional(true);
               utype.getTable().setPath(b.getQname());
            }

            return b;
         }
      }
   }

   public String toString() {
      return "<Attribute:" + this.start() + ":" + this.target + "." + this.getAttributeName() + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.target, v);
         this.visitNode(this.attr, v);
      }

   }
}
