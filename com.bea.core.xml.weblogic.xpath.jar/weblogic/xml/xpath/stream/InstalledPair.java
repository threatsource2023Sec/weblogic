package weblogic.xml.xpath.stream;

import weblogic.xml.xpath.StreamXPath;
import weblogic.xml.xpath.XPathStreamObserver;

public final class InstalledPair {
   StreamXPath xpath;
   XPathStreamObserver observer;

   public InstalledPair(StreamXPath x, XPathStreamObserver o) {
      this.xpath = x;
      this.observer = o;
   }
}
