package com.bea.connector.monitoring1Dot0;

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

public interface LinkRefDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LinkRefDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("linkrefc1eedoctype");

   String getLinkRef();

   XmlString xgetLinkRef();

   void setLinkRef(String var1);

   void xsetLinkRef(XmlString var1);

   public static final class Factory {
      public static LinkRefDocument newInstance() {
         return (LinkRefDocument)XmlBeans.getContextTypeLoader().newInstance(LinkRefDocument.type, (XmlOptions)null);
      }

      public static LinkRefDocument newInstance(XmlOptions options) {
         return (LinkRefDocument)XmlBeans.getContextTypeLoader().newInstance(LinkRefDocument.type, options);
      }

      public static LinkRefDocument parse(String xmlAsString) throws XmlException {
         return (LinkRefDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, LinkRefDocument.type, (XmlOptions)null);
      }

      public static LinkRefDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (LinkRefDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, LinkRefDocument.type, options);
      }

      public static LinkRefDocument parse(File file) throws XmlException, IOException {
         return (LinkRefDocument)XmlBeans.getContextTypeLoader().parse(file, LinkRefDocument.type, (XmlOptions)null);
      }

      public static LinkRefDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LinkRefDocument)XmlBeans.getContextTypeLoader().parse(file, LinkRefDocument.type, options);
      }

      public static LinkRefDocument parse(URL u) throws XmlException, IOException {
         return (LinkRefDocument)XmlBeans.getContextTypeLoader().parse(u, LinkRefDocument.type, (XmlOptions)null);
      }

      public static LinkRefDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LinkRefDocument)XmlBeans.getContextTypeLoader().parse(u, LinkRefDocument.type, options);
      }

      public static LinkRefDocument parse(InputStream is) throws XmlException, IOException {
         return (LinkRefDocument)XmlBeans.getContextTypeLoader().parse(is, LinkRefDocument.type, (XmlOptions)null);
      }

      public static LinkRefDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LinkRefDocument)XmlBeans.getContextTypeLoader().parse(is, LinkRefDocument.type, options);
      }

      public static LinkRefDocument parse(Reader r) throws XmlException, IOException {
         return (LinkRefDocument)XmlBeans.getContextTypeLoader().parse(r, LinkRefDocument.type, (XmlOptions)null);
      }

      public static LinkRefDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LinkRefDocument)XmlBeans.getContextTypeLoader().parse(r, LinkRefDocument.type, options);
      }

      public static LinkRefDocument parse(XMLStreamReader sr) throws XmlException {
         return (LinkRefDocument)XmlBeans.getContextTypeLoader().parse(sr, LinkRefDocument.type, (XmlOptions)null);
      }

      public static LinkRefDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LinkRefDocument)XmlBeans.getContextTypeLoader().parse(sr, LinkRefDocument.type, options);
      }

      public static LinkRefDocument parse(Node node) throws XmlException {
         return (LinkRefDocument)XmlBeans.getContextTypeLoader().parse(node, LinkRefDocument.type, (XmlOptions)null);
      }

      public static LinkRefDocument parse(Node node, XmlOptions options) throws XmlException {
         return (LinkRefDocument)XmlBeans.getContextTypeLoader().parse(node, LinkRefDocument.type, options);
      }

      /** @deprecated */
      public static LinkRefDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LinkRefDocument)XmlBeans.getContextTypeLoader().parse(xis, LinkRefDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LinkRefDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LinkRefDocument)XmlBeans.getContextTypeLoader().parse(xis, LinkRefDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LinkRefDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LinkRefDocument.type, options);
      }

      private Factory() {
      }
   }
}
