package com.bea.staxb.runtime;

import com.bea.xml.XmlException;
import org.w3c.dom.Element;

public interface BindingContext {
   Unmarshaller createUnmarshaller() throws XmlException;

   Marshaller createMarshaller() throws XmlException;

   SoapMarshaller createSoapMarshaller(EncodingStyle var1) throws XmlException;

   SoapUnmarshaller createSoapUnmarshaller(EncodingStyle var1, Element var2) throws XmlException;
}
