package org.apache.taglibs.standard.extra.spath;

import java.util.List;
import java.util.Stack;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLFilterImpl;

public class SPathFilter extends XMLFilterImpl {
   protected List steps;
   private int depth;
   private Stack acceptedDepths;
   private int excludedDepth;
   private static final boolean DEBUG = false;

   public SPathFilter(Path path) {
      this.init();
      this.steps = path.getSteps();
   }

   private void init() {
      this.depth = 0;
      this.excludedDepth = -1;
      this.acceptedDepths = new Stack();
   }

   public void startElement(String uri, String localName, String qName, Attributes a) throws SAXException {
      ++this.depth;
      if (this.isAccepted()) {
         this.getContentHandler().startElement(uri, localName, qName, a);
      } else if (!this.isExcluded()) {
         Step currentStep = (Step)this.steps.get(this.acceptedDepths.size());
         if (nodeMatchesStep(currentStep, uri, localName, qName, a)) {
            this.acceptedDepths.push(this.depth - 1);
            if (this.isAccepted()) {
               this.getContentHandler().startElement(uri, localName, qName, a);
            }
         } else if (!currentStep.isDepthUnlimited()) {
            this.excludedDepth = this.depth - 1;
         }

      }
   }

   public void endElement(String uri, String localName, String qName) throws SAXException {
      --this.depth;
      if (this.isExcluded()) {
         if (this.excludedDepth == this.depth) {
            this.excludedDepth = -1;
         }

      } else {
         if (this.isAccepted()) {
            this.getContentHandler().endElement(uri, localName, qName);
         }

         if (this.acceptedDepths.size() > 0 && (Integer)this.acceptedDepths.peek() == this.depth) {
            this.acceptedDepths.pop();
         }

      }
   }

   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
      if (this.isAccepted()) {
         this.getContentHandler().ignorableWhitespace(ch, start, length);
      }

   }

   public void characters(char[] ch, int start, int length) throws SAXException {
      if (this.isAccepted()) {
         this.getContentHandler().characters(ch, start, length);
      }

   }

   public void startPrefixMapping(String prefix, String uri) throws SAXException {
      if (this.isAccepted()) {
         this.getContentHandler().startPrefixMapping(prefix, uri);
      }

   }

   public void endPrefixMapping(String prefix) throws SAXException {
      if (this.isAccepted()) {
         this.getContentHandler().endPrefixMapping(prefix);
      }

   }

   public void processingInstruction(String target, String data) throws SAXException {
      if (this.isAccepted()) {
         this.getContentHandler().processingInstruction(target, data);
      }

   }

   public void skippedEntity(String name) throws SAXException {
      if (this.isAccepted()) {
         this.getContentHandler().skippedEntity(name);
      }

   }

   public void startDocument() {
      this.init();
   }

   public static boolean nodeMatchesStep(Step s, String uri, String localName, String qName, Attributes a) {
      if (!s.isMatchingName(uri, localName)) {
         return false;
      } else {
         List l = s.getPredicates();

         for(int i = 0; l != null && i < l.size(); ++i) {
            Predicate p = (Predicate)l.get(i);
            if (!(p instanceof AttributePredicate)) {
               throw new UnsupportedOperationException("only attribute predicates are supported by filter");
            }

            if (!((AttributePredicate)p).isMatchingAttribute(a)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean isAccepted() {
      return this.acceptedDepths.size() >= this.steps.size();
   }

   private boolean isExcluded() {
      return this.excludedDepth != -1;
   }
}
