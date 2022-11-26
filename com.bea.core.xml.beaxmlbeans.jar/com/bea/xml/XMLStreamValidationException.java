package com.bea.xml;

import weblogic.xml.stream.XMLStreamException;

public class XMLStreamValidationException extends XMLStreamException {
   private XmlError _xmlError;

   public XMLStreamValidationException(XmlError xmlError) {
      super(xmlError.toString());
      this._xmlError = xmlError;
   }

   public XmlError getXmlError() {
      return this._xmlError;
   }
}
