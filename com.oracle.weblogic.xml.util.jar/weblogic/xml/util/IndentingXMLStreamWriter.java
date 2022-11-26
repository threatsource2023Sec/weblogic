package weblogic.xml.util;

import java.util.Stack;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class IndentingXMLStreamWriter extends DelegatingXMLStreamWriter {
   private static final Object SEEN_NOTHING = new Object();
   private static final Object SEEN_ELEMENT = new Object();
   private static final Object SEEN_DATA = new Object();
   private Object state;
   private Stack stateStack;
   private String indentStep;
   private int depth;

   public IndentingXMLStreamWriter(XMLStreamWriter writer) {
      super(writer);
      this.state = SEEN_NOTHING;
      this.stateStack = new Stack();
      this.indentStep = "  ";
      this.depth = 0;
   }

   /** @deprecated */
   @Deprecated
   public int getIndentStep() {
      return this.indentStep.length();
   }

   /** @deprecated */
   @Deprecated
   public void setIndentStep(int indentStep) {
      StringBuilder s;
      for(s = new StringBuilder(); indentStep > 0; --indentStep) {
         s.append(' ');
      }

      this.setIndentStep(s.toString());
   }

   public void setIndentStep(String s) {
      this.indentStep = s;
   }

   private void onStartElement() throws XMLStreamException {
      this.stateStack.push(SEEN_ELEMENT);
      this.state = SEEN_NOTHING;
      if (this.depth > 0) {
         super.writeCharacters("\n");
      }

      this.doIndent();
      ++this.depth;
   }

   private void onEndElement() throws XMLStreamException {
      --this.depth;
      if (this.state == SEEN_ELEMENT) {
         super.writeCharacters("\n");
         this.doIndent();
      }

      this.state = this.stateStack.pop();
   }

   private void onEmptyElement() throws XMLStreamException {
      this.state = SEEN_ELEMENT;
      if (this.depth > 0) {
         super.writeCharacters("\n");
      }

      this.doIndent();
   }

   private void doIndent() throws XMLStreamException {
      if (this.depth > 0) {
         for(int i = 0; i < this.depth; ++i) {
            super.writeCharacters(this.indentStep);
         }
      }

   }

   public void writeStartDocument() throws XMLStreamException {
      super.writeStartDocument();
      super.writeCharacters("\n");
   }

   public void writeStartDocument(String version) throws XMLStreamException {
      super.writeStartDocument(version);
      super.writeCharacters("\n");
   }

   public void writeStartDocument(String encoding, String version) throws XMLStreamException {
      super.writeStartDocument(encoding, version);
      super.writeCharacters("\n");
   }

   public void writeStartElement(String localName) throws XMLStreamException {
      this.onStartElement();
      super.writeStartElement(localName);
   }

   public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
      this.onStartElement();
      super.writeStartElement(namespaceURI, localName);
   }

   public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
      this.onStartElement();
      super.writeStartElement(prefix, localName, namespaceURI);
   }

   public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
      this.onEmptyElement();
      super.writeEmptyElement(namespaceURI, localName);
   }

   public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
      this.onEmptyElement();
      super.writeEmptyElement(prefix, localName, namespaceURI);
   }

   public void writeEmptyElement(String localName) throws XMLStreamException {
      this.onEmptyElement();
      super.writeEmptyElement(localName);
   }

   public void writeEndElement() throws XMLStreamException {
      this.onEndElement();
      super.writeEndElement();
   }

   public void writeCharacters(String text) throws XMLStreamException {
      this.state = SEEN_DATA;
      super.writeCharacters(text);
   }

   public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
      this.state = SEEN_DATA;
      super.writeCharacters(text, start, len);
   }

   public void writeCData(String data) throws XMLStreamException {
      this.state = SEEN_DATA;
      super.writeCData(data);
   }
}
