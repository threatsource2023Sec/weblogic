package com.oracle.xmlns.weblogic.weblogicConnector;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.String;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface LinkRefType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LinkRefType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("linkreftypef258type");

   String getConnectionFactoryName();

   void setConnectionFactoryName(String var1);

   String addNewConnectionFactoryName();

   String getRaLinkRef();

   boolean isSetRaLinkRef();

   void setRaLinkRef(String var1);

   String addNewRaLinkRef();

   void unsetRaLinkRef();

   public static final class Factory {
      public static LinkRefType newInstance() {
         return (LinkRefType)XmlBeans.getContextTypeLoader().newInstance(LinkRefType.type, (XmlOptions)null);
      }

      public static LinkRefType newInstance(XmlOptions options) {
         return (LinkRefType)XmlBeans.getContextTypeLoader().newInstance(LinkRefType.type, options);
      }

      public static LinkRefType parse(java.lang.String xmlAsString) throws XmlException {
         return (LinkRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LinkRefType.type, (XmlOptions)null);
      }

      public static LinkRefType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (LinkRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LinkRefType.type, options);
      }

      public static LinkRefType parse(File file) throws XmlException, IOException {
         return (LinkRefType)XmlBeans.getContextTypeLoader().parse(file, LinkRefType.type, (XmlOptions)null);
      }

      public static LinkRefType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LinkRefType)XmlBeans.getContextTypeLoader().parse(file, LinkRefType.type, options);
      }

      public static LinkRefType parse(URL u) throws XmlException, IOException {
         return (LinkRefType)XmlBeans.getContextTypeLoader().parse(u, LinkRefType.type, (XmlOptions)null);
      }

      public static LinkRefType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LinkRefType)XmlBeans.getContextTypeLoader().parse(u, LinkRefType.type, options);
      }

      public static LinkRefType parse(InputStream is) throws XmlException, IOException {
         return (LinkRefType)XmlBeans.getContextTypeLoader().parse(is, LinkRefType.type, (XmlOptions)null);
      }

      public static LinkRefType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LinkRefType)XmlBeans.getContextTypeLoader().parse(is, LinkRefType.type, options);
      }

      public static LinkRefType parse(Reader r) throws XmlException, IOException {
         return (LinkRefType)XmlBeans.getContextTypeLoader().parse(r, LinkRefType.type, (XmlOptions)null);
      }

      public static LinkRefType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LinkRefType)XmlBeans.getContextTypeLoader().parse(r, LinkRefType.type, options);
      }

      public static LinkRefType parse(XMLStreamReader sr) throws XmlException {
         return (LinkRefType)XmlBeans.getContextTypeLoader().parse(sr, LinkRefType.type, (XmlOptions)null);
      }

      public static LinkRefType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LinkRefType)XmlBeans.getContextTypeLoader().parse(sr, LinkRefType.type, options);
      }

      public static LinkRefType parse(Node node) throws XmlException {
         return (LinkRefType)XmlBeans.getContextTypeLoader().parse(node, LinkRefType.type, (XmlOptions)null);
      }

      public static LinkRefType parse(Node node, XmlOptions options) throws XmlException {
         return (LinkRefType)XmlBeans.getContextTypeLoader().parse(node, LinkRefType.type, options);
      }

      /** @deprecated */
      public static LinkRefType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LinkRefType)XmlBeans.getContextTypeLoader().parse(xis, LinkRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LinkRefType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LinkRefType)XmlBeans.getContextTypeLoader().parse(xis, LinkRefType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LinkRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LinkRefType.type, options);
      }

      private Factory() {
      }
   }
}
