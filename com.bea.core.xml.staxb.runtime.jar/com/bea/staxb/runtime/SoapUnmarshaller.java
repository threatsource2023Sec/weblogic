package com.bea.staxb.runtime;

import com.bea.staxb.buildtime.internal.bts.XmlTypeName;
import com.bea.xml.XmlException;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;

public interface SoapUnmarshaller {
   Object unmarshalType(XMLStreamReader var1, QName var2, String var3, UnmarshalOptions var4) throws XmlException;

   Object unmarshalType(XMLStreamReader var1, XmlTypeName var2, String var3, UnmarshalOptions var4) throws XmlException;
}
