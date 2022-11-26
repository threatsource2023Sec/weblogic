package weblogic.xml.xpath.stream;

import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.xpath.XPathStreamObserver;

public class CompositeXPathStreamObserver implements XPathStreamObserver {
   private XPathStreamObserver[] mObservers;

   public CompositeXPathStreamObserver(XPathStreamObserver[] obs) {
      if (obs == null) {
         throw new IllegalArgumentException();
      } else if (obs.length == 0) {
         throw new IllegalArgumentException();
      } else {
         this.mObservers = obs;
      }
   }

   public CompositeXPathStreamObserver(XPathStreamObserver ob1, XPathStreamObserver ob2) {
      if (ob1 == null) {
         throw new IllegalArgumentException();
      } else if (ob2 == null) {
         throw new IllegalArgumentException();
      } else {
         this.mObservers = new XPathStreamObserver[2];
         this.mObservers[0] = ob1;
         this.mObservers[1] = ob2;
      }
   }

   public void observe(XMLEvent e) {
      for(int i = 0; i < this.mObservers.length; ++i) {
         this.mObservers[i].observe(e);
      }

   }

   public void observeAttribute(StartElement e, Attribute a) {
      for(int i = 0; i < this.mObservers.length; ++i) {
         this.mObservers[i].observeAttribute(e, a);
      }

   }

   public void observeNamespace(StartElement e, Attribute a) {
      for(int i = 0; i < this.mObservers.length; ++i) {
         this.mObservers[i].observeNamespace(e, a);
      }

   }
}
