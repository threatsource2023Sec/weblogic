package com.oracle.xmlns.weblogic.weblogicEjbJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface DestinationJndiNameType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DestinationJndiNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("destinationjndinametypea894type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static DestinationJndiNameType newInstance() {
         return (DestinationJndiNameType)XmlBeans.getContextTypeLoader().newInstance(DestinationJndiNameType.type, (XmlOptions)null);
      }

      public static DestinationJndiNameType newInstance(XmlOptions options) {
         return (DestinationJndiNameType)XmlBeans.getContextTypeLoader().newInstance(DestinationJndiNameType.type, options);
      }

      public static DestinationJndiNameType parse(String xmlAsString) throws XmlException {
         return (DestinationJndiNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DestinationJndiNameType.type, (XmlOptions)null);
      }

      public static DestinationJndiNameType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DestinationJndiNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DestinationJndiNameType.type, options);
      }

      public static DestinationJndiNameType parse(File file) throws XmlException, IOException {
         return (DestinationJndiNameType)XmlBeans.getContextTypeLoader().parse(file, DestinationJndiNameType.type, (XmlOptions)null);
      }

      public static DestinationJndiNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DestinationJndiNameType)XmlBeans.getContextTypeLoader().parse(file, DestinationJndiNameType.type, options);
      }

      public static DestinationJndiNameType parse(URL u) throws XmlException, IOException {
         return (DestinationJndiNameType)XmlBeans.getContextTypeLoader().parse(u, DestinationJndiNameType.type, (XmlOptions)null);
      }

      public static DestinationJndiNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DestinationJndiNameType)XmlBeans.getContextTypeLoader().parse(u, DestinationJndiNameType.type, options);
      }

      public static DestinationJndiNameType parse(InputStream is) throws XmlException, IOException {
         return (DestinationJndiNameType)XmlBeans.getContextTypeLoader().parse(is, DestinationJndiNameType.type, (XmlOptions)null);
      }

      public static DestinationJndiNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DestinationJndiNameType)XmlBeans.getContextTypeLoader().parse(is, DestinationJndiNameType.type, options);
      }

      public static DestinationJndiNameType parse(Reader r) throws XmlException, IOException {
         return (DestinationJndiNameType)XmlBeans.getContextTypeLoader().parse(r, DestinationJndiNameType.type, (XmlOptions)null);
      }

      public static DestinationJndiNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DestinationJndiNameType)XmlBeans.getContextTypeLoader().parse(r, DestinationJndiNameType.type, options);
      }

      public static DestinationJndiNameType parse(XMLStreamReader sr) throws XmlException {
         return (DestinationJndiNameType)XmlBeans.getContextTypeLoader().parse(sr, DestinationJndiNameType.type, (XmlOptions)null);
      }

      public static DestinationJndiNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DestinationJndiNameType)XmlBeans.getContextTypeLoader().parse(sr, DestinationJndiNameType.type, options);
      }

      public static DestinationJndiNameType parse(Node node) throws XmlException {
         return (DestinationJndiNameType)XmlBeans.getContextTypeLoader().parse(node, DestinationJndiNameType.type, (XmlOptions)null);
      }

      public static DestinationJndiNameType parse(Node node, XmlOptions options) throws XmlException {
         return (DestinationJndiNameType)XmlBeans.getContextTypeLoader().parse(node, DestinationJndiNameType.type, options);
      }

      /** @deprecated */
      public static DestinationJndiNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DestinationJndiNameType)XmlBeans.getContextTypeLoader().parse(xis, DestinationJndiNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DestinationJndiNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DestinationJndiNameType)XmlBeans.getContextTypeLoader().parse(xis, DestinationJndiNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DestinationJndiNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DestinationJndiNameType.type, options);
      }

      private Factory() {
      }
   }
}
