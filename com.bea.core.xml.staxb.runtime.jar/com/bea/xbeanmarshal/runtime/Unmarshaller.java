package com.bea.xbeanmarshal.runtime;

import com.bea.xml.XmlException;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;

public interface Unmarshaller {
   Object deserializeXmlObjects(boolean var1, SOAPElement var2, Class var3, QName var4) throws XmlException;
}
