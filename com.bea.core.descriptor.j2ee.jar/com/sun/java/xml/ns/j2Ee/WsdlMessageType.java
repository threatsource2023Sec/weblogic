package com.sun.java.xml.ns.j2Ee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface WsdlMessageType extends XsdQNameType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WsdlMessageType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("wsdlmessagetype7ef1type");

   public static final class Factory {
      public static WsdlMessageType newInstance() {
         return (WsdlMessageType)XmlBeans.getContextTypeLoader().newInstance(WsdlMessageType.type, (XmlOptions)null);
      }

      public static WsdlMessageType newInstance(XmlOptions options) {
         return (WsdlMessageType)XmlBeans.getContextTypeLoader().newInstance(WsdlMessageType.type, options);
      }

      public static WsdlMessageType parse(java.lang.String xmlAsString) throws XmlException {
         return (WsdlMessageType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WsdlMessageType.type, (XmlOptions)null);
      }

      public static WsdlMessageType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (WsdlMessageType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WsdlMessageType.type, options);
      }

      public static WsdlMessageType parse(File file) throws XmlException, IOException {
         return (WsdlMessageType)XmlBeans.getContextTypeLoader().parse(file, WsdlMessageType.type, (XmlOptions)null);
      }

      public static WsdlMessageType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WsdlMessageType)XmlBeans.getContextTypeLoader().parse(file, WsdlMessageType.type, options);
      }

      public static WsdlMessageType parse(URL u) throws XmlException, IOException {
         return (WsdlMessageType)XmlBeans.getContextTypeLoader().parse(u, WsdlMessageType.type, (XmlOptions)null);
      }

      public static WsdlMessageType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WsdlMessageType)XmlBeans.getContextTypeLoader().parse(u, WsdlMessageType.type, options);
      }

      public static WsdlMessageType parse(InputStream is) throws XmlException, IOException {
         return (WsdlMessageType)XmlBeans.getContextTypeLoader().parse(is, WsdlMessageType.type, (XmlOptions)null);
      }

      public static WsdlMessageType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WsdlMessageType)XmlBeans.getContextTypeLoader().parse(is, WsdlMessageType.type, options);
      }

      public static WsdlMessageType parse(Reader r) throws XmlException, IOException {
         return (WsdlMessageType)XmlBeans.getContextTypeLoader().parse(r, WsdlMessageType.type, (XmlOptions)null);
      }

      public static WsdlMessageType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WsdlMessageType)XmlBeans.getContextTypeLoader().parse(r, WsdlMessageType.type, options);
      }

      public static WsdlMessageType parse(XMLStreamReader sr) throws XmlException {
         return (WsdlMessageType)XmlBeans.getContextTypeLoader().parse(sr, WsdlMessageType.type, (XmlOptions)null);
      }

      public static WsdlMessageType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WsdlMessageType)XmlBeans.getContextTypeLoader().parse(sr, WsdlMessageType.type, options);
      }

      public static WsdlMessageType parse(Node node) throws XmlException {
         return (WsdlMessageType)XmlBeans.getContextTypeLoader().parse(node, WsdlMessageType.type, (XmlOptions)null);
      }

      public static WsdlMessageType parse(Node node, XmlOptions options) throws XmlException {
         return (WsdlMessageType)XmlBeans.getContextTypeLoader().parse(node, WsdlMessageType.type, options);
      }

      /** @deprecated */
      public static WsdlMessageType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WsdlMessageType)XmlBeans.getContextTypeLoader().parse(xis, WsdlMessageType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WsdlMessageType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WsdlMessageType)XmlBeans.getContextTypeLoader().parse(xis, WsdlMessageType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WsdlMessageType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WsdlMessageType.type, options);
      }

      private Factory() {
      }
   }
}
