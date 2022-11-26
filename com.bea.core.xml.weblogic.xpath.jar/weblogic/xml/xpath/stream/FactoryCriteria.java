package weblogic.xml.xpath.stream;

import java.util.ArrayList;
import java.util.List;
import weblogic.xml.xpath.StreamXPath;
import weblogic.xml.xpath.XPathStreamObserver;

public final class FactoryCriteria {
   private StreamContextRequirements mReqs = new StreamContextRequirements();
   private Evaluator[] mEvaluators;

   public FactoryCriteria(InstalledPair[] pairs) {
      for(int i = 0; i < pairs.length; ++i) {
         this.mReqs.union(pairs[i].xpath.getLocationPath().getRequirements());
      }

      this.mEvaluators = createEvaluators(pairs);
   }

   StreamContextRequirements getRequirements() {
      return this.mReqs;
   }

   Evaluator[] getEvaluators() {
      return this.mEvaluators;
   }

   private static Evaluator[] createEvaluators(InstalledPair[] pairs) {
      pairs = collapse(pairs);
      Evaluator[] out = new Evaluator[pairs.length];

      for(int i = 0; i < pairs.length; ++i) {
         if (out[i] == null) {
            StreamXPath xpath = pairs[i].xpath;
            RecordingXPathStreamObserver recorder = null;

            for(int j = i + 1; j < pairs.length; ++j) {
               if (pairs[j].xpath == xpath) {
                  if (recorder == null) {
                     recorder = new RecordingXPathStreamObserver();
                     out[i] = new XPathEvaluator(pairs[i].xpath, new CompositeXPathStreamObserver(pairs[i].observer, recorder));
                  }

                  out[j] = new PlaybackEvaluator(recorder, pairs[j].observer);
               }
            }

            if (out[i] == null) {
               out[i] = new XPathEvaluator(pairs[i].xpath, pairs[i].observer);
            }
         }
      }

      return out;
   }

   private static InstalledPair[] collapse(InstalledPair[] pairs) {
      List collapsed = new ArrayList();

      for(int i = 0; i < pairs.length; ++i) {
         StreamXPath xp = pairs[i].xpath;
         List observers = new ArrayList();
         observers.add(pairs[i].observer);

         while(i + 1 < pairs.length && pairs[i + 1].xpath == xp) {
            ++i;
            observers.add(pairs[i].observer);
         }

         XPathStreamObserver[] obs = new XPathStreamObserver[observers.size()];
         observers.toArray(obs);
         collapsed.add(new InstalledPair(xp, new CompositeXPathStreamObserver(obs)));
      }

      InstalledPair[] out = new InstalledPair[collapsed.size()];
      collapsed.toArray(out);
      return out;
   }
}
