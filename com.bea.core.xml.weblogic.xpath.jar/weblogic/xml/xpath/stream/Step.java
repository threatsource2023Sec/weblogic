package weblogic.xml.xpath.stream;

import java.io.StringWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import weblogic.xml.xpath.XPathUnsupportedException;
import weblogic.xml.xpath.common.Expression;
import weblogic.xml.xpath.parser.StepModel;

public final class Step implements StepModel {
   public static final int HIT = 100;
   public static final int HIT_PRUNE = 101;
   public static final int MISS = 102;
   public static final int MISS_PRUNE = 103;
   private static final boolean DEBUG = false;
   private int mContextNodeDepth = 0;
   private int mFollowingCeiling = -1;
   private Axis mAxis;
   private NodeTest mNodeTest;
   private Expression[] mPredicates = null;
   private int[] mPredicatePositions = null;
   private Step mNextStep = null;

   private Step() {
   }

   Step(Axis axis, NodeTest nt) {
      if (axis == null) {
         throw new IllegalArgumentException("null axis.");
      } else if (nt == null) {
         throw new IllegalArgumentException("null node test.");
      } else {
         this.mAxis = axis;
         this.mNodeTest = nt;
      }
   }

   Step(Axis axis, NodeTest nt, Expression[] preds) {
      if (axis == null) {
         throw new IllegalArgumentException("null axis.");
      } else if (nt == null) {
         throw new IllegalArgumentException("null node test.");
      } else if (preds == null) {
         throw new IllegalArgumentException("null preds.");
      } else {
         this.mAxis = axis;
         this.mNodeTest = nt;
         this.mPredicates = preds;
         this.mPredicatePositions = new int[preds.length];
      }
   }

   public int getContextNodeDepth() {
      return this.mContextNodeDepth;
   }

   public int getFollowingCeiling() {
      return this.mFollowingCeiling;
   }

   public void setFollowingCeiling(int f) {
      this.mFollowingCeiling = f;
   }

   void setNextStep(Step s) {
      if (s == null) {
         throw new IllegalArgumentException();
      } else if (this.mNextStep != null) {
         throw new IllegalStateException();
      } else {
         this.mNextStep = s;
      }
   }

   public int matchNew(StreamContext ctx) {
      switch (this.mAxis.matchNew(ctx)) {
         case 200:
            if (!this.mNodeTest.isMatch(ctx)) {
               return 102;
            } else {
               if (this.mPredicates == null) {
                  return 100;
               }

               return this.matchPredicates(ctx) ? 100 : 102;
            }
         case 201:
            if (!this.mNodeTest.isMatch(ctx)) {
               return 103;
            } else {
               if (this.mPredicates == null) {
                  return 101;
               }

               return this.matchPredicates(ctx) ? 101 : 103;
            }
         case 202:
            return 102;
         case 203:
            return 103;
         default:
            throw new IllegalStateException();
      }
   }

   public int match(StreamContext ctx) {
      switch (this.mAxis.match(ctx)) {
         case 200:
            return this.matchNodeTestAndPredicates(ctx);
         case 201:
         default:
            throw new IllegalStateException();
         case 202:
            return 102;
         case 203:
            return 103;
      }
   }

   public int matchNodeTestAndPredicates(StreamContext ctx) {
      if (!this.mNodeTest.isMatch(ctx)) {
         return 102;
      } else if (this.mPredicates == null) {
         return 100;
      } else {
         return this.matchPredicates(ctx) ? 100 : 102;
      }
   }

   Step copy() {
      Step out = new Step();
      if (this.mPredicates != null) {
         out.mPredicatePositions = new int[this.mPredicatePositions.length];
      }

      out.mAxis = this.mAxis;
      out.mNodeTest = this.mNodeTest;
      out.mPredicates = this.mPredicates;
      out.mNextStep = this.mNextStep;
      out.mFollowingCeiling = this.mFollowingCeiling;
      return out;
   }

   public Step copyNext(StreamContext ctx) {
      if (this.mNextStep == null) {
         return null;
      } else {
         Step out = this.mNextStep.copy();
         out.mContextNodeDepth = ctx.mDepth;
         return out;
      }
   }

   Step getNext() {
      return this.mNextStep;
   }

   void validateAsPredicateExpr() throws XPathUnsupportedException {
      if (!this.mAxis.isAllowedInPredicate()) {
         throw new XPathUnsupportedException("StreamXPath does not allow the " + this.mAxis + " axis to appear in a predicate.");
      }
   }

   void validateAsRootExpr() throws XPathUnsupportedException {
      if (!this.mAxis.isAllowedInRoot()) {
         throw new XPathUnsupportedException("StreamXPath does not allow the " + this.mAxis + " axis to appear in the root location path; it can only appear in a predicate.");
      }
   }

   void validateStringConversion() throws XPathUnsupportedException {
      if (!this.mAxis.isStringConvertible() && !this.mNodeTest.isStringConvertible()) {
         throw new XPathUnsupportedException("StreamXPath does not allow the conversion of a nodeset to strings if the nodeset might contain element nodes.  Note that this includes cases where the context node must be converted to a string, e.g. with the 'string()' function. (" + this.mAxis + "::" + this.mNodeTest + ")");
      }
   }

   List getNodeset(StreamContext ctx) {
      List out = this.mAxis.getNodeset(ctx);
      if (out == null) {
         return null;
      } else {
         ctx = new StreamContext();
         Iterator i = out.iterator();

         while(i.hasNext()) {
            ctx.setContextNode((StreamContext)i.next());
            if (!this.mNodeTest.isMatch(ctx)) {
               i.remove();
            }
         }

         if (this.mPredicates != null) {
            for(int p = 0; p < this.mPredicates.length; ++p) {
               ctx.position = 0;
               i = out.iterator();

               while(i.hasNext()) {
                  ctx.setContextNode((StreamContext)i.next());
                  ++ctx.position;
                  if (!this.mPredicates[p].evaluateAsBoolean(ctx)) {
                     i.remove();
                  }
               }
            }
         }

         return out;
      }
   }

   void getSubExpressions(Collection out) {
      if (this.mPredicates != null) {
         for(int i = 0; i < this.mPredicates.length; ++i) {
            out.add(this.mPredicates[i]);
            this.mPredicates[i].getSubExpressions(out);
         }
      }

   }

   private boolean matchPredicates(StreamContext ctx) {
      int originalPosition = ctx.position;

      for(int i = 0; i < this.mPredicates.length; ++i) {
         ctx.position = ++this.mPredicatePositions[i];
         if (!this.mPredicates[i].evaluateAsBoolean(ctx)) {
            ctx.position = originalPosition;
            return false;
         }
      }

      ctx.position = originalPosition;
      return true;
   }

   public String toString() {
      StringWriter out = new StringWriter();
      out.write(this.mAxis.toString());
      out.write("::");
      out.write(this.mNodeTest.toString());
      if (this.mPredicates != null) {
         for(int i = 0; i < this.mPredicates.length; ++i) {
            out.write("[");
            out.write(this.mPredicates[i].toString());
            out.write("]");
         }
      }

      if (this.mNextStep != null) {
         out.write("/");
         out.write(this.mNextStep.toString());
      }

      return out.toString();
   }
}
