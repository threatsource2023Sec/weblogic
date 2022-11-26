package com.bea.xml_.impl.jam.xml;

import javax.xml.stream.XMLStreamException;

public class TunnelledException extends RuntimeException {
   private XMLStreamException mXSE = null;

   public TunnelledException(XMLStreamException xse) {
      this.mXSE = xse;
   }

   public XMLStreamException getXMLStreamException() {
      return this.mXSE;
   }
}
