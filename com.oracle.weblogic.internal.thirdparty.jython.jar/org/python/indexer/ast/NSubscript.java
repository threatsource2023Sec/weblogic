package org.python.indexer.ast;

import java.util.Iterator;
import org.python.indexer.Indexer;
import org.python.indexer.Scope;
import org.python.indexer.types.NType;
import org.python.indexer.types.NUnknownType;

public class NSubscript extends NNode {
   static final long serialVersionUID = -493854491438387425L;
   public NNode value;
   public NNode slice;

   public NSubscript(NNode value, NNode slice) {
      this(value, slice, 0, 1);
   }

   public NSubscript(NNode value, NNode slice, int start, int end) {
      super(start, end);
      this.value = value;
      this.slice = slice;
      this.addChildren(new NNode[]{value, slice});
   }

   public NType resolve(Scope s) throws Exception {
      NType vt = resolveExpr(this.value, s);
      NType st = resolveExpr(this.slice, s);
      if (vt.isUnknownType()) {
         return st.isListType() ? this.setType(vt) : this.setType(new NUnknownType());
      } else {
         NType gt;
         if (st.isListType()) {
            gt = vt.getTable().lookupTypeAttr("__getslice__");
            if (gt == null) {
               this.addError("The type can't be sliced: " + vt);
               return this.setType(new NUnknownType());
            } else if (!gt.isFuncType()) {
               this.addError("The type's __getslice__ method is not a function: " + gt);
               return this.setType(new NUnknownType());
            } else {
               return this.setType(gt.asFuncType().getReturnType().follow());
            }
         } else {
            if (this.slice instanceof NIndex) {
               if (vt.isListType()) {
                  this.warnUnlessNumIndex(st);
                  return this.setType(vt.asListType().getElementType());
               }

               if (vt.isTupleType()) {
                  this.warnUnlessNumIndex(st);
                  return this.setType(vt.asTupleType().toListType().getElementType());
               }

               if (vt.isStrType()) {
                  this.warnUnlessNumIndex(st);
                  return this.setType(Indexer.idx.builtins.BaseStr);
               }

               if (vt.isDictType()) {
                  if (!st.follow().equals(vt.asDictType().getKeyType())) {
                     this.addWarning("Possible KeyError (wrong type for subscript)");
                  }

                  return this.setType(vt.asDictType().getValueType());
               }
            }

            if (vt.isUnionType()) {
               Iterator var4 = vt.asUnionType().getTypes().iterator();

               while(var4.hasNext()) {
                  NType u = (NType)var4.next();
                  NType gt = vt.getTable().lookupTypeAttr("__getitem__");
                  if (gt != null) {
                     return this.setType(this.get__getitem__type(gt, gt));
                  }
               }
            }

            gt = vt.getTable().lookupTypeAttr("__getitem__");
            return this.setType(this.get__getitem__type(gt, vt));
         }
      }
   }

   private void warnUnlessNumIndex(NType subscriptType) {
      NType follow = subscriptType.follow();
      if (!follow.isNumType() && !follow.isUnknownType()) {
         this.addWarning("Possible KeyError: subscript should be a number; found " + follow);
      }

   }

   private NType get__getitem__type(NType gt, NType vt) {
      if (gt == null) {
         this.addError("indexing type without __getitem__ method: " + vt);
         return new NUnknownType();
      } else if (!gt.isFuncType()) {
         this.addError("The type's __getitem__ method is not a function: " + gt);
         return new NUnknownType();
      } else {
         return gt.asFuncType().getReturnType().follow();
      }
   }

   public String toString() {
      return "<Subscript:" + this.value + ":" + this.slice + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.value, v);
         this.visitNode(this.slice, v);
      }

   }
}
