package com.bea.connector.diagnostic;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface EndpointsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EndpointsType.class.getClassLoader(), "schemacom_bea_xml.system.conn_diag").resolveHandle("endpointstypec3bftype");

   EndpointType[] getEndpointArray();

   EndpointType getEndpointArray(int var1);

   int sizeOfEndpointArray();

   void setEndpointArray(EndpointType[] var1);

   void setEndpointArray(int var1, EndpointType var2);

   EndpointType insertNewEndpoint(int var1);

   EndpointType addNewEndpoint();

   void removeEndpoint(int var1);

   public static final class Factory {
      public static EndpointsType newInstance() {
         return (EndpointsType)XmlBeans.getContextTypeLoader().newInstance(EndpointsType.type, (XmlOptions)null);
      }

      public static EndpointsType newInstance(XmlOptions options) {
         return (EndpointsType)XmlBeans.getContextTypeLoader().newInstance(EndpointsType.type, options);
      }

      public static EndpointsType parse(String xmlAsString) throws XmlException {
         return (EndpointsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EndpointsType.type, (XmlOptions)null);
      }

      public static EndpointsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (EndpointsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EndpointsType.type, options);
      }

      public static EndpointsType parse(File file) throws XmlException, IOException {
         return (EndpointsType)XmlBeans.getContextTypeLoader().parse(file, EndpointsType.type, (XmlOptions)null);
      }

      public static EndpointsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EndpointsType)XmlBeans.getContextTypeLoader().parse(file, EndpointsType.type, options);
      }

      public static EndpointsType parse(URL u) throws XmlException, IOException {
         return (EndpointsType)XmlBeans.getContextTypeLoader().parse(u, EndpointsType.type, (XmlOptions)null);
      }

      public static EndpointsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EndpointsType)XmlBeans.getContextTypeLoader().parse(u, EndpointsType.type, options);
      }

      public static EndpointsType parse(InputStream is) throws XmlException, IOException {
         return (EndpointsType)XmlBeans.getContextTypeLoader().parse(is, EndpointsType.type, (XmlOptions)null);
      }

      public static EndpointsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EndpointsType)XmlBeans.getContextTypeLoader().parse(is, EndpointsType.type, options);
      }

      public static EndpointsType parse(Reader r) throws XmlException, IOException {
         return (EndpointsType)XmlBeans.getContextTypeLoader().parse(r, EndpointsType.type, (XmlOptions)null);
      }

      public static EndpointsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EndpointsType)XmlBeans.getContextTypeLoader().parse(r, EndpointsType.type, options);
      }

      public static EndpointsType parse(XMLStreamReader sr) throws XmlException {
         return (EndpointsType)XmlBeans.getContextTypeLoader().parse(sr, EndpointsType.type, (XmlOptions)null);
      }

      public static EndpointsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EndpointsType)XmlBeans.getContextTypeLoader().parse(sr, EndpointsType.type, options);
      }

      public static EndpointsType parse(Node node) throws XmlException {
         return (EndpointsType)XmlBeans.getContextTypeLoader().parse(node, EndpointsType.type, (XmlOptions)null);
      }

      public static EndpointsType parse(Node node, XmlOptions options) throws XmlException {
         return (EndpointsType)XmlBeans.getContextTypeLoader().parse(node, EndpointsType.type, options);
      }

      /** @deprecated */
      public static EndpointsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EndpointsType)XmlBeans.getContextTypeLoader().parse(xis, EndpointsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EndpointsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EndpointsType)XmlBeans.getContextTypeLoader().parse(xis, EndpointsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EndpointsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EndpointsType.type, options);
      }

      private Factory() {
      }
   }
}
