package com.bea.staxb.runtime;

import com.bea.staxb.buildtime.internal.bts.XmlTypeName;
import com.bea.xml.XmlException;
import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public interface SoapMarshaller {
   void marshalType(XMLStreamWriter var1, Object var2, QName var3, QName var4, String var5, MarshalOptions var6) throws XmlException;

   void marshalType(XMLStreamWriter var1, Object var2, XmlTypeName var3, XmlTypeName var4, String var5, MarshalOptions var6) throws XmlException;

   void marshalType(XMLStreamWriter var1, Object var2, QName var3, XmlTypeName var4, String var5, MarshalOptions var6) throws XmlException;

   void marshalReferenced(XMLStreamWriter var1, MarshalOptions var2) throws XmlException;

   XMLStreamReader marshalType(Object var1, QName var2, QName var3, String var4, MarshalOptions var5) throws XmlException;

   Iterator marshalReferenced(MarshalOptions var1) throws XmlException;
}
