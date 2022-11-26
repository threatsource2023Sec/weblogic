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

public interface PointbaseDictionaryType extends BuiltInDbdictionaryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PointbaseDictionaryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("pointbasedictionarytypec6f8type");

   public static final class Factory {
      public static PointbaseDictionaryType newInstance() {
         return (PointbaseDictionaryType)XmlBeans.getContextTypeLoader().newInstance(PointbaseDictionaryType.type, (XmlOptions)null);
      }

      public static PointbaseDictionaryType newInstance(XmlOptions options) {
         return (PointbaseDictionaryType)XmlBeans.getContextTypeLoader().newInstance(PointbaseDictionaryType.type, options);
      }

      public static PointbaseDictionaryType parse(String xmlAsString) throws XmlException {
         return (PointbaseDictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PointbaseDictionaryType.type, (XmlOptions)null);
      }

      public static PointbaseDictionaryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PointbaseDictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PointbaseDictionaryType.type, options);
      }

      public static PointbaseDictionaryType parse(File file) throws XmlException, IOException {
         return (PointbaseDictionaryType)XmlBeans.getContextTypeLoader().parse(file, PointbaseDictionaryType.type, (XmlOptions)null);
      }

      public static PointbaseDictionaryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PointbaseDictionaryType)XmlBeans.getContextTypeLoader().parse(file, PointbaseDictionaryType.type, options);
      }

      public static PointbaseDictionaryType parse(URL u) throws XmlException, IOException {
         return (PointbaseDictionaryType)XmlBeans.getContextTypeLoader().parse(u, PointbaseDictionaryType.type, (XmlOptions)null);
      }

      public static PointbaseDictionaryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PointbaseDictionaryType)XmlBeans.getContextTypeLoader().parse(u, PointbaseDictionaryType.type, options);
      }

      public static PointbaseDictionaryType parse(InputStream is) throws XmlException, IOException {
         return (PointbaseDictionaryType)XmlBeans.getContextTypeLoader().parse(is, PointbaseDictionaryType.type, (XmlOptions)null);
      }

      public static PointbaseDictionaryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PointbaseDictionaryType)XmlBeans.getContextTypeLoader().parse(is, PointbaseDictionaryType.type, options);
      }

      public static PointbaseDictionaryType parse(Reader r) throws XmlException, IOException {
         return (PointbaseDictionaryType)XmlBeans.getContextTypeLoader().parse(r, PointbaseDictionaryType.type, (XmlOptions)null);
      }

      public static PointbaseDictionaryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PointbaseDictionaryType)XmlBeans.getContextTypeLoader().parse(r, PointbaseDictionaryType.type, options);
      }

      public static PointbaseDictionaryType parse(XMLStreamReader sr) throws XmlException {
         return (PointbaseDictionaryType)XmlBeans.getContextTypeLoader().parse(sr, PointbaseDictionaryType.type, (XmlOptions)null);
      }

      public static PointbaseDictionaryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PointbaseDictionaryType)XmlBeans.getContextTypeLoader().parse(sr, PointbaseDictionaryType.type, options);
      }

      public static PointbaseDictionaryType parse(Node node) throws XmlException {
         return (PointbaseDictionaryType)XmlBeans.getContextTypeLoader().parse(node, PointbaseDictionaryType.type, (XmlOptions)null);
      }

      public static PointbaseDictionaryType parse(Node node, XmlOptions options) throws XmlException {
         return (PointbaseDictionaryType)XmlBeans.getContextTypeLoader().parse(node, PointbaseDictionaryType.type, options);
      }

      /** @deprecated */
      public static PointbaseDictionaryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PointbaseDictionaryType)XmlBeans.getContextTypeLoader().parse(xis, PointbaseDictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PointbaseDictionaryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PointbaseDictionaryType)XmlBeans.getContextTypeLoader().parse(xis, PointbaseDictionaryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PointbaseDictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PointbaseDictionaryType.type, options);
      }

      private Factory() {
      }
   }
}
