package weblogic.xml.xpath.stream;

import weblogic.xml.xpath.XPathStreamObserver;

final class PlaybackEvaluator implements Evaluator {
   private RecordingXPathStreamObserver mSource;
   private XPathStreamObserver mDestination;

   public PlaybackEvaluator(RecordingXPathStreamObserver src, XPathStreamObserver dest) {
      if (src == null) {
         throw new IllegalArgumentException();
      } else if (dest == null) {
         throw new IllegalArgumentException();
      } else {
         this.mSource = src;
         this.mDestination = dest;
      }
   }

   public void evaluate(StreamContext ctx) {
      this.mSource.replayObservations(this.mDestination);
   }
}
