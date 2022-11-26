package com.bea.connector.diagnostic;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
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

public interface EndpointType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EndpointType.class.getClassLoader(), "schemacom_bea_xml.system.conn_diag").resolveHandle("endpointtype7808type");

   String getName();

   XmlString xgetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   String getMsgType();

   XmlString xgetMsgType();

   void setMsgType(String var1);

   void xsetMsgType(XmlString var1);

   public static final class Factory {
      public static EndpointType newInstance() {
         return (EndpointType)XmlBeans.getContextTypeLoader().newInstance(EndpointType.type, (XmlOptions)null);
      }

      public static EndpointType newInstance(XmlOptions options) {
         return (EndpointType)XmlBeans.getContextTypeLoader().newInstance(EndpointType.type, options);
      }

      public static EndpointType parse(String xmlAsString) throws XmlException {
         return (EndpointType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EndpointType.type, (XmlOptions)null);
      }

      public static EndpointType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (EndpointType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EndpointType.type, options);
      }

      public static EndpointType parse(File file) throws XmlException, IOException {
         return (EndpointType)XmlBeans.getContextTypeLoader().parse(file, EndpointType.type, (XmlOptions)null);
      }

      public static EndpointType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EndpointType)XmlBeans.getContextTypeLoader().parse(file, EndpointType.type, options);
      }

      public static EndpointType parse(URL u) throws XmlException, IOException {
         return (EndpointType)XmlBeans.getContextTypeLoader().parse(u, EndpointType.type, (XmlOptions)null);
      }

      public static EndpointType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EndpointType)XmlBeans.getContextTypeLoader().parse(u, EndpointType.type, options);
      }

      public static EndpointType parse(InputStream is) throws XmlException, IOException {
         return (EndpointType)XmlBeans.getContextTypeLoader().parse(is, EndpointType.type, (XmlOptions)null);
      }

      public static EndpointType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EndpointType)XmlBeans.getContextTypeLoader().parse(is, EndpointType.type, options);
      }

      public static EndpointType parse(Reader r) throws XmlException, IOException {
         return (EndpointType)XmlBeans.getContextTypeLoader().parse(r, EndpointType.type, (XmlOptions)null);
      }

      public static EndpointType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EndpointType)XmlBeans.getContextTypeLoader().parse(r, EndpointType.type, options);
      }

      public static EndpointType parse(XMLStreamReader sr) throws XmlException {
         return (EndpointType)XmlBeans.getContextTypeLoader().parse(sr, EndpointType.type, (XmlOptions)null);
      }

      public static EndpointType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EndpointType)XmlBeans.getContextTypeLoader().parse(sr, EndpointType.type, options);
      }

      public static EndpointType parse(Node node) throws XmlException {
         return (EndpointType)XmlBeans.getContextTypeLoader().parse(node, EndpointType.type, (XmlOptions)null);
      }

      public static EndpointType parse(Node node, XmlOptions options) throws XmlException {
         return (EndpointType)XmlBeans.getContextTypeLoader().parse(node, EndpointType.type, options);
      }

      /** @deprecated */
      public static EndpointType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EndpointType)XmlBeans.getContextTypeLoader().parse(xis, EndpointType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EndpointType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EndpointType)XmlBeans.getContextTypeLoader().parse(xis, EndpointType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EndpointType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EndpointType.type, options);
      }

      private Factory() {
      }
   }
}
