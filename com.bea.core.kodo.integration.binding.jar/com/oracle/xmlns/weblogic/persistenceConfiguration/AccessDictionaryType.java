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

public interface AccessDictionaryType extends BuiltInDbdictionaryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AccessDictionaryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("accessdictionarytype8dadtype");

   public static final class Factory {
      public static AccessDictionaryType newInstance() {
         return (AccessDictionaryType)XmlBeans.getContextTypeLoader().newInstance(AccessDictionaryType.type, (XmlOptions)null);
      }

      public static AccessDictionaryType newInstance(XmlOptions options) {
         return (AccessDictionaryType)XmlBeans.getContextTypeLoader().newInstance(AccessDictionaryType.type, options);
      }

      public static AccessDictionaryType parse(String xmlAsString) throws XmlException {
         return (AccessDictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AccessDictionaryType.type, (XmlOptions)null);
      }

      public static AccessDictionaryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AccessDictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AccessDictionaryType.type, options);
      }

      public static AccessDictionaryType parse(File file) throws XmlException, IOException {
         return (AccessDictionaryType)XmlBeans.getContextTypeLoader().parse(file, AccessDictionaryType.type, (XmlOptions)null);
      }

      public static AccessDictionaryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AccessDictionaryType)XmlBeans.getContextTypeLoader().parse(file, AccessDictionaryType.type, options);
      }

      public static AccessDictionaryType parse(URL u) throws XmlException, IOException {
         return (AccessDictionaryType)XmlBeans.getContextTypeLoader().parse(u, AccessDictionaryType.type, (XmlOptions)null);
      }

      public static AccessDictionaryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AccessDictionaryType)XmlBeans.getContextTypeLoader().parse(u, AccessDictionaryType.type, options);
      }

      public static AccessDictionaryType parse(InputStream is) throws XmlException, IOException {
         return (AccessDictionaryType)XmlBeans.getContextTypeLoader().parse(is, AccessDictionaryType.type, (XmlOptions)null);
      }

      public static AccessDictionaryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AccessDictionaryType)XmlBeans.getContextTypeLoader().parse(is, AccessDictionaryType.type, options);
      }

      public static AccessDictionaryType parse(Reader r) throws XmlException, IOException {
         return (AccessDictionaryType)XmlBeans.getContextTypeLoader().parse(r, AccessDictionaryType.type, (XmlOptions)null);
      }

      public static AccessDictionaryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AccessDictionaryType)XmlBeans.getContextTypeLoader().parse(r, AccessDictionaryType.type, options);
      }

      public static AccessDictionaryType parse(XMLStreamReader sr) throws XmlException {
         return (AccessDictionaryType)XmlBeans.getContextTypeLoader().parse(sr, AccessDictionaryType.type, (XmlOptions)null);
      }

      public static AccessDictionaryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AccessDictionaryType)XmlBeans.getContextTypeLoader().parse(sr, AccessDictionaryType.type, options);
      }

      public static AccessDictionaryType parse(Node node) throws XmlException {
         return (AccessDictionaryType)XmlBeans.getContextTypeLoader().parse(node, AccessDictionaryType.type, (XmlOptions)null);
      }

      public static AccessDictionaryType parse(Node node, XmlOptions options) throws XmlException {
         return (AccessDictionaryType)XmlBeans.getContextTypeLoader().parse(node, AccessDictionaryType.type, options);
      }

      /** @deprecated */
      public static AccessDictionaryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AccessDictionaryType)XmlBeans.getContextTypeLoader().parse(xis, AccessDictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AccessDictionaryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AccessDictionaryType)XmlBeans.getContextTypeLoader().parse(xis, AccessDictionaryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AccessDictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AccessDictionaryType.type, options);
      }

      private Factory() {
      }
   }
}
