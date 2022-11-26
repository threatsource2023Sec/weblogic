package weblogic.xml.xpath.stream;

import weblogic.xml.stream.XMLEvent;

public final class XPathStreamDelegate {
   private static final boolean DEBUG = false;
   private Evaluator[] mEvaluators;
   private StreamContext mContext;

   XPathStreamDelegate(FactoryCriteria criteria) {
      if (criteria == null) {
         throw new IllegalArgumentException("null criteria.");
      } else {
         this.mContext = new StreamContext(criteria.getRequirements());
         this.mEvaluators = criteria.getEvaluators();
      }
   }

   void observe(XMLEvent event) {
      switch (event.getType()) {
         case 128:
            return;
         default:
            if (this.mContext.observeNext(event)) {
               for(int i = 0; i < this.mEvaluators.length; ++i) {
                  this.mEvaluators[i].evaluate(this.mContext);
               }
            }

      }
   }
}
