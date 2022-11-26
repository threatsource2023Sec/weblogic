package weblogic.xml.xpath.stream;

import java.util.ArrayList;
import java.util.List;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.xpath.XPathStreamObserver;

final class RecordingXPathStreamObserver implements XPathStreamObserver {
   private boolean mClearOnNextObserve = false;
   private List mObservations = new ArrayList();

   public RecordingXPathStreamObserver() {
   }

   public void observe(XMLEvent e) {
      this.clearIfAppropriate();
      this.mObservations.add(new EventObservation(e));
   }

   public void observeAttribute(StartElement e, Attribute a) {
      this.clearIfAppropriate();
      this.mObservations.add(new AttributeObservation(e, a));
   }

   public void observeNamespace(StartElement e, Attribute a) {
      this.clearIfAppropriate();
      this.mObservations.add(new NamespaceObservation(e, a));
   }

   public void replayObservations(XPathStreamObserver o) {
      this.mClearOnNextObserve = true;
      int i = 0;

      for(int iL = this.mObservations.size(); i < iL; ++i) {
         ((Observation)this.mObservations.get(i)).notify(o);
      }

   }

   public void clearObservations() {
      this.mObservations.clear();
   }

   private void clearIfAppropriate() {
      if (this.mClearOnNextObserve) {
         this.mObservations.clear();
         this.mClearOnNextObserve = false;
      }

   }
}
