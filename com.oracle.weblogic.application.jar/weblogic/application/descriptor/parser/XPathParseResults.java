package weblogic.application.descriptor.parser;

import java.util.ArrayList;
import java.util.Collection;

public class XPathParseResults {
   private ArrayList steps = new ArrayList();
   private String xpath;

   public void setXPath(String XPath) {
      this.xpath = XPath;
   }

   public String getXPath() {
      return this.xpath;
   }

   public Collection getSteps() {
      return this.steps;
   }

   public void addStep(StepExpression step) {
      this.steps.add(step);
   }

   public StepExpression getLeafStepExpression() {
      return this.steps.size() > 0 ? (StepExpression)this.steps.get(this.steps.size() - 1) : null;
   }
}
