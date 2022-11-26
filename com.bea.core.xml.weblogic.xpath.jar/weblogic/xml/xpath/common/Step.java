package weblogic.xml.xpath.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.xml.xpath.parser.StepModel;

final class Step implements StepModel {
   private Axis mAxis;
   private NodeTest mNodeTest = null;
   private Expression[] mPredicates = null;

   public Step(Axis axis, NodeTest nodeTest) {
      if (axis == null) {
         throw new IllegalArgumentException("null axis");
      } else if (nodeTest == null) {
         throw new IllegalArgumentException("null axis");
      } else {
         this.mAxis = axis;
         this.mNodeTest = nodeTest;
      }
   }

   public Step(Axis axis, NodeTest nodeTest, Expression[] preds) {
      if (axis == null) {
         throw new IllegalArgumentException("null axis");
      } else if (nodeTest == null) {
         throw new IllegalArgumentException("null axis");
      } else if (preds == null) {
         throw new IllegalArgumentException("null preds");
      } else {
         for(int i = 0; i < preds.length; ++i) {
            if (preds[i] == null) {
               throw new IllegalArgumentException("null predicate[" + i + "]");
            }
         }

         this.mAxis = axis;
         this.mNodeTest = nodeTest;
         this.mPredicates = preds;
      }
   }

   public Axis getAxis() {
      return this.mAxis;
   }

   public List getMatches(Context ctx) {
      Object originalNode = ctx.node;
      int originalSize = ctx.size;
      int originalPosition = ctx.position;
      List nodeset = new ArrayList();
      Iterator i = this.mAxis.createIterator(ctx);

      while(i.hasNext()) {
         Object n = i.next();
         if (this.mNodeTest.isMatch(n)) {
            nodeset.add(n);
         }
      }

      if (this.mPredicates != null) {
         List holdNodeset = new ArrayList();

         for(int p = 0; p < this.mPredicates.length; ++p) {
            holdNodeset.clear();
            ctx.size = nodeset.size();

            for(int n = 0; n < nodeset.size(); ++n) {
               ctx.position = n + 1;
               ctx.node = nodeset.get(n);
               if (this.mPredicates[p].evaluateAsBoolean((Context)ctx.clone())) {
                  holdNodeset.add(nodeset.get(n));
               }
            }

            nodeset = holdNodeset;
         }
      }

      ctx.size = originalSize;
      ctx.position = originalPosition;
      ctx.node = originalNode;
      return nodeset;
   }

   public String toString() {
      StringBuffer out = new StringBuffer();
      out.append(this.mAxis.toString());
      out.append(this.mNodeTest.toString());
      if (this.mPredicates != null) {
         for(int i = 0; i < this.mPredicates.length; ++i) {
            out.append("[");
            out.append(this.mPredicates[i].toString());
            out.append("]");
         }
      }

      return out.toString();
   }
}
