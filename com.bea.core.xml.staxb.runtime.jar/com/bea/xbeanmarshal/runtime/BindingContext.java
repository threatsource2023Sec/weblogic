package com.bea.xbeanmarshal.runtime;

import com.bea.xml.XmlException;

public interface BindingContext {
   Unmarshaller createUnmarshaller() throws XmlException;

   Marshaller createMarshaller() throws XmlException;
}
