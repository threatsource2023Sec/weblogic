package com.oracle.xmlns.weblogic.persistenceConfiguration;

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

public interface NoneLogFactoryType extends LogType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(NoneLogFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("nonelogfactorytypeb64etype");

   public static final class Factory {
      public static NoneLogFactoryType newInstance() {
         return (NoneLogFactoryType)XmlBeans.getContextTypeLoader().newInstance(NoneLogFactoryType.type, (XmlOptions)null);
      }

      public static NoneLogFactoryType newInstance(XmlOptions options) {
         return (NoneLogFactoryType)XmlBeans.getContextTypeLoader().newInstance(NoneLogFactoryType.type, options);
      }

      public static NoneLogFactoryType parse(String xmlAsString) throws XmlException {
         return (NoneLogFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, NoneLogFactoryType.type, (XmlOptions)null);
      }

      public static NoneLogFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (NoneLogFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, NoneLogFactoryType.type, options);
      }

      public static NoneLogFactoryType parse(File file) throws XmlException, IOException {
         return (NoneLogFactoryType)XmlBeans.getContextTypeLoader().parse(file, NoneLogFactoryType.type, (XmlOptions)null);
      }

      public static NoneLogFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (NoneLogFactoryType)XmlBeans.getContextTypeLoader().parse(file, NoneLogFactoryType.type, options);
      }

      public static NoneLogFactoryType parse(URL u) throws XmlException, IOException {
         return (NoneLogFactoryType)XmlBeans.getContextTypeLoader().parse(u, NoneLogFactoryType.type, (XmlOptions)null);
      }

      public static NoneLogFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (NoneLogFactoryType)XmlBeans.getContextTypeLoader().parse(u, NoneLogFactoryType.type, options);
      }

      public static NoneLogFactoryType parse(InputStream is) throws XmlException, IOException {
         return (NoneLogFactoryType)XmlBeans.getContextTypeLoader().parse(is, NoneLogFactoryType.type, (XmlOptions)null);
      }

      public static NoneLogFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (NoneLogFactoryType)XmlBeans.getContextTypeLoader().parse(is, NoneLogFactoryType.type, options);
      }

      public static NoneLogFactoryType parse(Reader r) throws XmlException, IOException {
         return (NoneLogFactoryType)XmlBeans.getContextTypeLoader().parse(r, NoneLogFactoryType.type, (XmlOptions)null);
      }

      public static NoneLogFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (NoneLogFactoryType)XmlBeans.getContextTypeLoader().parse(r, NoneLogFactoryType.type, options);
      }

      public static NoneLogFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (NoneLogFactoryType)XmlBeans.getContextTypeLoader().parse(sr, NoneLogFactoryType.type, (XmlOptions)null);
      }

      public static NoneLogFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (NoneLogFactoryType)XmlBeans.getContextTypeLoader().parse(sr, NoneLogFactoryType.type, options);
      }

      public static NoneLogFactoryType parse(Node node) throws XmlException {
         return (NoneLogFactoryType)XmlBeans.getContextTypeLoader().parse(node, NoneLogFactoryType.type, (XmlOptions)null);
      }

      public static NoneLogFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (NoneLogFactoryType)XmlBeans.getContextTypeLoader().parse(node, NoneLogFactoryType.type, options);
      }

      /** @deprecated */
      public static NoneLogFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (NoneLogFactoryType)XmlBeans.getContextTypeLoader().parse(xis, NoneLogFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static NoneLogFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (NoneLogFactoryType)XmlBeans.getContextTypeLoader().parse(xis, NoneLogFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NoneLogFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NoneLogFactoryType.type, options);
      }

      private Factory() {
      }
   }
}
