package com.bea.xbeanmarshal.runtime;

import com.bea.xml.XmlException;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import org.w3c.dom.Document;

public interface Marshaller {
   void serializeXmlObject(boolean var1, Document var2, SOAPElement var3, Object var4, QName var5) throws XmlException;
}
