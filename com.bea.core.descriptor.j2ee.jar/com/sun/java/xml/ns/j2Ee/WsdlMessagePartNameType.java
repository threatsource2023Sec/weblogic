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

public interface WsdlMessagePartNameType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WsdlMessagePartNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("wsdlmessagepartnametype50c9type");

   public static final class Factory {
      public static WsdlMessagePartNameType newInstance() {
         return (WsdlMessagePartNameType)XmlBeans.getContextTypeLoader().newInstance(WsdlMessagePartNameType.type, (XmlOptions)null);
      }

      public static WsdlMessagePartNameType newInstance(XmlOptions options) {
         return (WsdlMessagePartNameType)XmlBeans.getContextTypeLoader().newInstance(WsdlMessagePartNameType.type, options);
      }

      public static WsdlMessagePartNameType parse(java.lang.String xmlAsString) throws XmlException {
         return (WsdlMessagePartNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WsdlMessagePartNameType.type, (XmlOptions)null);
      }

      public static WsdlMessagePartNameType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (WsdlMessagePartNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WsdlMessagePartNameType.type, options);
      }

      public static WsdlMessagePartNameType parse(File file) throws XmlException, IOException {
         return (WsdlMessagePartNameType)XmlBeans.getContextTypeLoader().parse(file, WsdlMessagePartNameType.type, (XmlOptions)null);
      }

      public static WsdlMessagePartNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WsdlMessagePartNameType)XmlBeans.getContextTypeLoader().parse(file, WsdlMessagePartNameType.type, options);
      }

      public static WsdlMessagePartNameType parse(URL u) throws XmlException, IOException {
         return (WsdlMessagePartNameType)XmlBeans.getContextTypeLoader().parse(u, WsdlMessagePartNameType.type, (XmlOptions)null);
      }

      public static WsdlMessagePartNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WsdlMessagePartNameType)XmlBeans.getContextTypeLoader().parse(u, WsdlMessagePartNameType.type, options);
      }

      public static WsdlMessagePartNameType parse(InputStream is) throws XmlException, IOException {
         return (WsdlMessagePartNameType)XmlBeans.getContextTypeLoader().parse(is, WsdlMessagePartNameType.type, (XmlOptions)null);
      }

      public static WsdlMessagePartNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WsdlMessagePartNameType)XmlBeans.getContextTypeLoader().parse(is, WsdlMessagePartNameType.type, options);
      }

      public static WsdlMessagePartNameType parse(Reader r) throws XmlException, IOException {
         return (WsdlMessagePartNameType)XmlBeans.getContextTypeLoader().parse(r, WsdlMessagePartNameType.type, (XmlOptions)null);
      }

      public static WsdlMessagePartNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WsdlMessagePartNameType)XmlBeans.getContextTypeLoader().parse(r, WsdlMessagePartNameType.type, options);
      }

      public static WsdlMessagePartNameType parse(XMLStreamReader sr) throws XmlException {
         return (WsdlMessagePartNameType)XmlBeans.getContextTypeLoader().parse(sr, WsdlMessagePartNameType.type, (XmlOptions)null);
      }

      public static WsdlMessagePartNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WsdlMessagePartNameType)XmlBeans.getContextTypeLoader().parse(sr, WsdlMessagePartNameType.type, options);
      }

      public static WsdlMessagePartNameType parse(Node node) throws XmlException {
         return (WsdlMessagePartNameType)XmlBeans.getContextTypeLoader().parse(node, WsdlMessagePartNameType.type, (XmlOptions)null);
      }

      public static WsdlMessagePartNameType parse(Node node, XmlOptions options) throws XmlException {
         return (WsdlMessagePartNameType)XmlBeans.getContextTypeLoader().parse(node, WsdlMessagePartNameType.type, options);
      }

      /** @deprecated */
      public static WsdlMessagePartNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WsdlMessagePartNameType)XmlBeans.getContextTypeLoader().parse(xis, WsdlMessagePartNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WsdlMessagePartNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WsdlMessagePartNameType)XmlBeans.getContextTypeLoader().parse(xis, WsdlMessagePartNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WsdlMessagePartNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WsdlMessagePartNameType.type, options);
      }

      private Factory() {
      }
   }
}
