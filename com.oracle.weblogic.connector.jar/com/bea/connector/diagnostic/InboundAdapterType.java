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

public interface InboundAdapterType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(InboundAdapterType.class.getClassLoader(), "schemacom_bea_xml.system.conn_diag").resolveHandle("inboundadaptertype3fc9type");

   String getName();

   XmlString xgetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   String getState();

   XmlString xgetState();

   void setState(String var1);

   void xsetState(XmlString var1);

   EndpointsType getEndpoints();

   void setEndpoints(EndpointsType var1);

   EndpointsType addNewEndpoints();

   public static final class Factory {
      public static InboundAdapterType newInstance() {
         return (InboundAdapterType)XmlBeans.getContextTypeLoader().newInstance(InboundAdapterType.type, (XmlOptions)null);
      }

      public static InboundAdapterType newInstance(XmlOptions options) {
         return (InboundAdapterType)XmlBeans.getContextTypeLoader().newInstance(InboundAdapterType.type, options);
      }

      public static InboundAdapterType parse(String xmlAsString) throws XmlException {
         return (InboundAdapterType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InboundAdapterType.type, (XmlOptions)null);
      }

      public static InboundAdapterType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (InboundAdapterType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InboundAdapterType.type, options);
      }

      public static InboundAdapterType parse(File file) throws XmlException, IOException {
         return (InboundAdapterType)XmlBeans.getContextTypeLoader().parse(file, InboundAdapterType.type, (XmlOptions)null);
      }

      public static InboundAdapterType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (InboundAdapterType)XmlBeans.getContextTypeLoader().parse(file, InboundAdapterType.type, options);
      }

      public static InboundAdapterType parse(URL u) throws XmlException, IOException {
         return (InboundAdapterType)XmlBeans.getContextTypeLoader().parse(u, InboundAdapterType.type, (XmlOptions)null);
      }

      public static InboundAdapterType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (InboundAdapterType)XmlBeans.getContextTypeLoader().parse(u, InboundAdapterType.type, options);
      }

      public static InboundAdapterType parse(InputStream is) throws XmlException, IOException {
         return (InboundAdapterType)XmlBeans.getContextTypeLoader().parse(is, InboundAdapterType.type, (XmlOptions)null);
      }

      public static InboundAdapterType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (InboundAdapterType)XmlBeans.getContextTypeLoader().parse(is, InboundAdapterType.type, options);
      }

      public static InboundAdapterType parse(Reader r) throws XmlException, IOException {
         return (InboundAdapterType)XmlBeans.getContextTypeLoader().parse(r, InboundAdapterType.type, (XmlOptions)null);
      }

      public static InboundAdapterType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (InboundAdapterType)XmlBeans.getContextTypeLoader().parse(r, InboundAdapterType.type, options);
      }

      public static InboundAdapterType parse(XMLStreamReader sr) throws XmlException {
         return (InboundAdapterType)XmlBeans.getContextTypeLoader().parse(sr, InboundAdapterType.type, (XmlOptions)null);
      }

      public static InboundAdapterType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (InboundAdapterType)XmlBeans.getContextTypeLoader().parse(sr, InboundAdapterType.type, options);
      }

      public static InboundAdapterType parse(Node node) throws XmlException {
         return (InboundAdapterType)XmlBeans.getContextTypeLoader().parse(node, InboundAdapterType.type, (XmlOptions)null);
      }

      public static InboundAdapterType parse(Node node, XmlOptions options) throws XmlException {
         return (InboundAdapterType)XmlBeans.getContextTypeLoader().parse(node, InboundAdapterType.type, options);
      }

      /** @deprecated */
      public static InboundAdapterType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (InboundAdapterType)XmlBeans.getContextTypeLoader().parse(xis, InboundAdapterType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static InboundAdapterType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (InboundAdapterType)XmlBeans.getContextTypeLoader().parse(xis, InboundAdapterType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InboundAdapterType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InboundAdapterType.type, options);
      }

      private Factory() {
      }
   }
}
