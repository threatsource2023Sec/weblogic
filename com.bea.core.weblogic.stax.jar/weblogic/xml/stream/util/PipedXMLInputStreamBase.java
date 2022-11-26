package weblogic.xml.stream.util;

import weblogic.xml.stream.ReferenceResolver;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLInputStreamFilter;
import weblogic.xml.stream.XMLName;
import weblogic.xml.stream.XMLStreamException;
import weblogic.xml.stream.events.NullEvent;

/** @deprecated */
@Deprecated
public class PipedXMLInputStreamBase implements XMLInputStreamFilter {
   protected XMLInputStream parent;
   protected boolean open;

   public PipedXMLInputStreamBase() {
   }

   public PipedXMLInputStreamBase(XMLInputStream parent) throws XMLStreamException {
      this.setParent(parent);
      this.open = parent.hasNext();
   }

   protected XMLEvent filter(XMLEvent e) throws XMLStreamException {
      return e;
   }

   public XMLEvent next() throws XMLStreamException {
      return (XMLEvent)(this.hasNext() ? this.filter(this.parent.next()) : new NullEvent());
   }

   public boolean hasNext() throws XMLStreamException {
      return this.open ? this.parent.hasNext() : false;
   }

   public void skip() throws XMLStreamException {
      if (this.open) {
         this.parent.skip();
      }

   }

   public void skipElement() throws XMLStreamException {
      if (this.open) {
         this.parent.skipElement();
      }

   }

   public XMLEvent peek() throws XMLStreamException {
      return (XMLEvent)(this.open ? this.filter(this.parent.peek()) : new NullEvent());
   }

   public boolean skip(int elementType) throws XMLStreamException {
      return this.open ? this.parent.skip(elementType) : false;
   }

   public boolean skip(XMLName name) throws XMLStreamException {
      return this.open ? this.parent.skip(name) : false;
   }

   public boolean skip(XMLName name, int elementType) throws XMLStreamException {
      return this.open ? this.parent.skip(name, elementType) : false;
   }

   public XMLInputStream getSubStream() throws XMLStreamException {
      return this.parent.getSubStream();
   }

   public void close() throws XMLStreamException {
      this.parent.close();
      this.parent = null;
      this.open = false;
   }

   public XMLInputStream getParent() {
      return this.parent;
   }

   public void setParent(XMLInputStream parent) throws XMLStreamException {
      if (parent == null) {
         throw new XMLStreamException("Unable to set PipedXMLInputStreamBase.parent to null");
      } else {
         this.parent = parent;
      }
   }

   public void setReferenceResolver(ReferenceResolver resolver) {
      this.parent.setReferenceResolver(resolver);
   }

   public ReferenceResolver getReferenceResolver() {
      return this.parent.getReferenceResolver();
   }
}
