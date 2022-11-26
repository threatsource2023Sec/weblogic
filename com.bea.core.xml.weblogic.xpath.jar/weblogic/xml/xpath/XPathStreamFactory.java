package weblogic.xml.xpath;

import java.util.ArrayList;
import java.util.List;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLOutputStream;
import weblogic.xml.xpath.stream.FactoryCriteria;
import weblogic.xml.xpath.stream.InstalledPair;
import weblogic.xml.xpath.stream.XPathInputStream;
import weblogic.xml.xpath.stream.XPathOutputStream;

public final class XPathStreamFactory {
   private List mPairs;
   private FactoryCriteria mCriteria;

   public XPathStreamFactory() {
      this.mPairs = new ArrayList();
      this.mCriteria = null;
   }

   public XPathStreamFactory(StreamXPath xpath, XPathStreamObserver observer) {
      this();
      this.install(xpath, observer);
   }

   public void install(StreamXPath xpath, XPathStreamObserver observer) {
      if (xpath == null) {
         throw new IllegalArgumentException("null xpath.");
      } else if (observer == null) {
         throw new IllegalArgumentException("null observer.");
      } else {
         this.mCriteria = null;
         this.mPairs.add(new InstalledPair(xpath, observer));
      }
   }

   public XMLInputStream createStream(XMLInputStream source) {
      if (source == null) {
         throw new IllegalArgumentException("null source.");
      } else {
         return (XMLInputStream)(this.mPairs.size() == 0 ? source : new XPathInputStream(source, this.getCriteria()));
      }
   }

   public XMLOutputStream createStream(XMLOutputStream destination) {
      if (destination == null) {
         throw new IllegalArgumentException("null destination.");
      } else {
         return (XMLOutputStream)(this.mPairs.size() == 0 ? destination : new XPathOutputStream(destination, this.getCriteria()));
      }
   }

   private FactoryCriteria getCriteria() {
      if (this.mCriteria == null) {
         InstalledPair[] array = new InstalledPair[this.mPairs.size()];
         this.mPairs.toArray(array);
         this.mCriteria = new FactoryCriteria(array);
      }

      return this.mCriteria;
   }
}
