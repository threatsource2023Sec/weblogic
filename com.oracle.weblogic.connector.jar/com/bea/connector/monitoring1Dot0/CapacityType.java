package com.bea.connector.monitoring1Dot0;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInteger;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigInteger;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface CapacityType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CapacityType.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("capacitytype502atype");

   String getName();

   XmlString xgetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   BigInteger getCount();

   XmlInteger xgetCount();

   void setCount(BigInteger var1);

   void xsetCount(XmlInteger var1);

   public static final class Factory {
      public static CapacityType newInstance() {
         return (CapacityType)XmlBeans.getContextTypeLoader().newInstance(CapacityType.type, (XmlOptions)null);
      }

      public static CapacityType newInstance(XmlOptions options) {
         return (CapacityType)XmlBeans.getContextTypeLoader().newInstance(CapacityType.type, options);
      }

      public static CapacityType parse(String xmlAsString) throws XmlException {
         return (CapacityType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CapacityType.type, (XmlOptions)null);
      }

      public static CapacityType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CapacityType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CapacityType.type, options);
      }

      public static CapacityType parse(File file) throws XmlException, IOException {
         return (CapacityType)XmlBeans.getContextTypeLoader().parse(file, CapacityType.type, (XmlOptions)null);
      }

      public static CapacityType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CapacityType)XmlBeans.getContextTypeLoader().parse(file, CapacityType.type, options);
      }

      public static CapacityType parse(URL u) throws XmlException, IOException {
         return (CapacityType)XmlBeans.getContextTypeLoader().parse(u, CapacityType.type, (XmlOptions)null);
      }

      public static CapacityType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CapacityType)XmlBeans.getContextTypeLoader().parse(u, CapacityType.type, options);
      }

      public static CapacityType parse(InputStream is) throws XmlException, IOException {
         return (CapacityType)XmlBeans.getContextTypeLoader().parse(is, CapacityType.type, (XmlOptions)null);
      }

      public static CapacityType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CapacityType)XmlBeans.getContextTypeLoader().parse(is, CapacityType.type, options);
      }

      public static CapacityType parse(Reader r) throws XmlException, IOException {
         return (CapacityType)XmlBeans.getContextTypeLoader().parse(r, CapacityType.type, (XmlOptions)null);
      }

      public static CapacityType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CapacityType)XmlBeans.getContextTypeLoader().parse(r, CapacityType.type, options);
      }

      public static CapacityType parse(XMLStreamReader sr) throws XmlException {
         return (CapacityType)XmlBeans.getContextTypeLoader().parse(sr, CapacityType.type, (XmlOptions)null);
      }

      public static CapacityType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CapacityType)XmlBeans.getContextTypeLoader().parse(sr, CapacityType.type, options);
      }

      public static CapacityType parse(Node node) throws XmlException {
         return (CapacityType)XmlBeans.getContextTypeLoader().parse(node, CapacityType.type, (XmlOptions)null);
      }

      public static CapacityType parse(Node node, XmlOptions options) throws XmlException {
         return (CapacityType)XmlBeans.getContextTypeLoader().parse(node, CapacityType.type, options);
      }

      /** @deprecated */
      public static CapacityType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CapacityType)XmlBeans.getContextTypeLoader().parse(xis, CapacityType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CapacityType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CapacityType)XmlBeans.getContextTypeLoader().parse(xis, CapacityType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CapacityType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CapacityType.type, options);
      }

      private Factory() {
      }
   }
}
