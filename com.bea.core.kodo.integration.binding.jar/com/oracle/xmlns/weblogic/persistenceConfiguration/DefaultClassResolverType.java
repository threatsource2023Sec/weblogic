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

public interface DefaultClassResolverType extends ClassResolverType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DefaultClassResolverType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("defaultclassresolvertypeb853type");

   public static final class Factory {
      public static DefaultClassResolverType newInstance() {
         return (DefaultClassResolverType)XmlBeans.getContextTypeLoader().newInstance(DefaultClassResolverType.type, (XmlOptions)null);
      }

      public static DefaultClassResolverType newInstance(XmlOptions options) {
         return (DefaultClassResolverType)XmlBeans.getContextTypeLoader().newInstance(DefaultClassResolverType.type, options);
      }

      public static DefaultClassResolverType parse(String xmlAsString) throws XmlException {
         return (DefaultClassResolverType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultClassResolverType.type, (XmlOptions)null);
      }

      public static DefaultClassResolverType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DefaultClassResolverType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultClassResolverType.type, options);
      }

      public static DefaultClassResolverType parse(File file) throws XmlException, IOException {
         return (DefaultClassResolverType)XmlBeans.getContextTypeLoader().parse(file, DefaultClassResolverType.type, (XmlOptions)null);
      }

      public static DefaultClassResolverType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DefaultClassResolverType)XmlBeans.getContextTypeLoader().parse(file, DefaultClassResolverType.type, options);
      }

      public static DefaultClassResolverType parse(URL u) throws XmlException, IOException {
         return (DefaultClassResolverType)XmlBeans.getContextTypeLoader().parse(u, DefaultClassResolverType.type, (XmlOptions)null);
      }

      public static DefaultClassResolverType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DefaultClassResolverType)XmlBeans.getContextTypeLoader().parse(u, DefaultClassResolverType.type, options);
      }

      public static DefaultClassResolverType parse(InputStream is) throws XmlException, IOException {
         return (DefaultClassResolverType)XmlBeans.getContextTypeLoader().parse(is, DefaultClassResolverType.type, (XmlOptions)null);
      }

      public static DefaultClassResolverType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DefaultClassResolverType)XmlBeans.getContextTypeLoader().parse(is, DefaultClassResolverType.type, options);
      }

      public static DefaultClassResolverType parse(Reader r) throws XmlException, IOException {
         return (DefaultClassResolverType)XmlBeans.getContextTypeLoader().parse(r, DefaultClassResolverType.type, (XmlOptions)null);
      }

      public static DefaultClassResolverType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DefaultClassResolverType)XmlBeans.getContextTypeLoader().parse(r, DefaultClassResolverType.type, options);
      }

      public static DefaultClassResolverType parse(XMLStreamReader sr) throws XmlException {
         return (DefaultClassResolverType)XmlBeans.getContextTypeLoader().parse(sr, DefaultClassResolverType.type, (XmlOptions)null);
      }

      public static DefaultClassResolverType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DefaultClassResolverType)XmlBeans.getContextTypeLoader().parse(sr, DefaultClassResolverType.type, options);
      }

      public static DefaultClassResolverType parse(Node node) throws XmlException {
         return (DefaultClassResolverType)XmlBeans.getContextTypeLoader().parse(node, DefaultClassResolverType.type, (XmlOptions)null);
      }

      public static DefaultClassResolverType parse(Node node, XmlOptions options) throws XmlException {
         return (DefaultClassResolverType)XmlBeans.getContextTypeLoader().parse(node, DefaultClassResolverType.type, options);
      }

      /** @deprecated */
      public static DefaultClassResolverType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DefaultClassResolverType)XmlBeans.getContextTypeLoader().parse(xis, DefaultClassResolverType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DefaultClassResolverType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DefaultClassResolverType)XmlBeans.getContextTypeLoader().parse(xis, DefaultClassResolverType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultClassResolverType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultClassResolverType.type, options);
      }

      private Factory() {
      }
   }
}
