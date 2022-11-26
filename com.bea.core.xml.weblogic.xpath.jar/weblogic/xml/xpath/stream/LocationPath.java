package weblogic.xml.xpath.stream;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import weblogic.xml.xpath.XPathUnsupportedException;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.expressions.NodesetExpression;
import weblogic.xml.xpath.parser.LocationPathModel;

public final class LocationPath extends NodesetExpression implements LocationPathModel {
   private Step[] mSteps;
   private StreamContextRequirements mReqs = null;

   public LocationPath(Step[] steps) {
      super(StreamInterrogator.INSTANCE);
      if (steps == null) {
         throw new IllegalArgumentException("null steps.");
      } else if (steps.length == 0) {
         throw new IllegalArgumentException("empty steps.");
      } else {
         this.mSteps = steps;

         for(int i = 0; i < steps.length - 1; ++i) {
            steps[i].setNextStep(steps[i + 1]);
         }

      }
   }

   public Step createMatcherStep() {
      return this.mSteps[0].copy();
   }

   public void validateAsRootExpr() throws XPathUnsupportedException {
      for(int i = 0; i < this.mSteps.length; ++i) {
         this.mSteps[i].validateAsRootExpr();
      }

   }

   void validateAsPredicateExpr() throws XPathUnsupportedException {
      for(int i = 0; i < this.mSteps.length; ++i) {
         this.mSteps[i].validateAsPredicateExpr();
      }

   }

   void validateStringConversion() throws XPathUnsupportedException {
      this.mSteps[this.mSteps.length - 1].validateStringConversion();
   }

   public void setRequirements(StreamContextRequirements req) {
      if (this.mReqs != null) {
         throw new IllegalStateException();
      } else {
         this.mReqs = req;
      }
   }

   StreamContextRequirements getRequirements() {
      return this.mReqs;
   }

   public List evaluateAsNodeset(Context c) {
      StreamContext ctx = (StreamContext)c;
      List resultList = this.mSteps[0].getNodeset(ctx);

      for(int i = 1; i < this.mSteps.length; ++i) {
         List iterList = resultList;
         resultList = new ArrayList();

         for(int j = 0; j < ((List)iterList).size(); ++j) {
            ctx.setContextNode((StreamContext)((List)iterList).get(j));
            List n = this.mSteps[i].getNodeset(ctx);
            if (n != null) {
               ((List)resultList).addAll(n);
            }
         }
      }

      return (List)(resultList == null ? new ArrayList() : resultList);
   }

   public void getSubExpressions(Collection out) {
      for(int i = 0; i < this.mSteps.length; ++i) {
         this.mSteps[i].getSubExpressions(out);
      }

   }

   public String toString() {
      StringWriter out = new StringWriter();

      for(int i = 0; i < this.mSteps.length; ++i) {
         out.write("/");
         out.write(this.mSteps[i].toString());
      }

      return out.toString();
   }
}
