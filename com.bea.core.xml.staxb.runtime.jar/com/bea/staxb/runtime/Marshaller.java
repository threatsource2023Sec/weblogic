package com.bea.staxb.runtime;

import com.bea.staxb.buildtime.internal.bts.XmlTypeName;
import com.bea.xml.XmlException;
import java.io.OutputStream;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public interface Marshaller {
   XMLStreamReader marshal(Object var1, MarshalOptions var2) throws XmlException;

   void marshal(XMLStreamWriter var1, Object var2) throws XmlException;

   void marshal(XMLStreamWriter var1, Object var2, MarshalOptions var3) throws XmlException;

   void marshal(OutputStream var1, Object var2) throws XmlException;

   void marshal(OutputStream var1, Object var2, MarshalOptions var3) throws XmlException;

   XMLStreamReader marshalType(Object var1, QName var2, QName var3, String var4, MarshalOptions var5) throws XmlException;

   XMLStreamReader marshalElement(Object var1, QName var2, String var3, MarshalOptions var4) throws XmlException;

   void marshalType(XMLStreamWriter var1, Object var2, QName var3, QName var4, String var5) throws XmlException;

   void marshalType(XMLStreamWriter var1, Object var2, QName var3, QName var4, String var5, MarshalOptions var6) throws XmlException;

   void marshalType(XMLStreamWriter var1, Object var2, XmlTypeName var3, XmlTypeName var4, String var5, MarshalOptions var6) throws XmlException;

   void marshalType(XMLStreamWriter var1, Object var2, QName var3, XmlTypeName var4, String var5, MarshalOptions var6) throws XmlException;

   void marshalElement(XMLStreamWriter var1, Object var2, QName var3, String var4, MarshalOptions var5) throws XmlException;

   void marshalElement(XMLStreamWriter var1, Object var2, XmlTypeName var3, String var4, MarshalOptions var5) throws XmlException;
}
