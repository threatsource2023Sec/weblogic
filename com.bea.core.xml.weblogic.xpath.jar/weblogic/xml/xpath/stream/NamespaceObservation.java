package weblogic.xml.xpath.stream;

import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.StartElement;
import weblogic.xml.xpath.XPathStreamObserver;

final class NamespaceObservation implements Observation {
   private StartElement element;
   private Attribute attribute;

   public NamespaceObservation(StartElement e, Attribute a) {
      this.element = e;
      this.attribute = a;
   }

   public void notify(XPathStreamObserver o) {
      o.observeNamespace(this.element, this.attribute);
   }
}
