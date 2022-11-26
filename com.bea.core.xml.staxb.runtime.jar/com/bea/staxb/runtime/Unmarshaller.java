package com.bea.staxb.runtime;

import com.bea.staxb.buildtime.internal.bts.XmlTypeName;
import com.bea.xml.XmlException;
import java.io.InputStream;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;

public interface Unmarshaller {
   Object unmarshal(XMLStreamReader var1) throws XmlException;

   Object unmarshal(XMLStreamReader var1, UnmarshalOptions var2) throws XmlException;

   Object unmarshal(InputStream var1) throws XmlException;

   Object unmarshal(InputStream var1, UnmarshalOptions var2) throws XmlException;

   Object unmarshalType(XMLStreamReader var1, QName var2, String var3) throws XmlException;

   Object unmarshalType(XMLStreamReader var1, QName var2, String var3, UnmarshalOptions var4) throws XmlException;

   Object unmarshalType(XMLStreamReader var1, XmlTypeName var2, String var3, UnmarshalOptions var4) throws XmlException;

   Object unmarshalElement(XMLStreamReader var1, QName var2, String var3, UnmarshalOptions var4) throws XmlException;

   Object unmarshalElement(XMLStreamReader var1, XmlTypeName var2, String var3, UnmarshalOptions var4) throws XmlException;
}
