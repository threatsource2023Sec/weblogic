package weblogic.xml.xpath.stream;

import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.StartElement;
import weblogic.xml.xpath.XPathStreamObserver;

final class AttributeObservation implements Observation {
   private StartElement element;
   private Attribute attribute;

   public AttributeObservation(StartElement e, Attribute a) {
      this.element = e;
      this.attribute = a;
   }

   public void notify(XPathStreamObserver o) {
      o.observeAttribute(this.element, this.attribute);
   }
}
