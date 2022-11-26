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

public interface Db2DictionaryType extends BuiltInDbdictionaryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Db2DictionaryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("db2dictionarytypee78btype");

   public static final class Factory {
      public static Db2DictionaryType newInstance() {
         return (Db2DictionaryType)XmlBeans.getContextTypeLoader().newInstance(Db2DictionaryType.type, (XmlOptions)null);
      }

      public static Db2DictionaryType newInstance(XmlOptions options) {
         return (Db2DictionaryType)XmlBeans.getContextTypeLoader().newInstance(Db2DictionaryType.type, options);
      }

      public static Db2DictionaryType parse(String xmlAsString) throws XmlException {
         return (Db2DictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, Db2DictionaryType.type, (XmlOptions)null);
      }

      public static Db2DictionaryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (Db2DictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, Db2DictionaryType.type, options);
      }

      public static Db2DictionaryType parse(File file) throws XmlException, IOException {
         return (Db2DictionaryType)XmlBeans.getContextTypeLoader().parse(file, Db2DictionaryType.type, (XmlOptions)null);
      }

      public static Db2DictionaryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Db2DictionaryType)XmlBeans.getContextTypeLoader().parse(file, Db2DictionaryType.type, options);
      }

      public static Db2DictionaryType parse(URL u) throws XmlException, IOException {
         return (Db2DictionaryType)XmlBeans.getContextTypeLoader().parse(u, Db2DictionaryType.type, (XmlOptions)null);
      }

      public static Db2DictionaryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Db2DictionaryType)XmlBeans.getContextTypeLoader().parse(u, Db2DictionaryType.type, options);
      }

      public static Db2DictionaryType parse(InputStream is) throws XmlException, IOException {
         return (Db2DictionaryType)XmlBeans.getContextTypeLoader().parse(is, Db2DictionaryType.type, (XmlOptions)null);
      }

      public static Db2DictionaryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Db2DictionaryType)XmlBeans.getContextTypeLoader().parse(is, Db2DictionaryType.type, options);
      }

      public static Db2DictionaryType parse(Reader r) throws XmlException, IOException {
         return (Db2DictionaryType)XmlBeans.getContextTypeLoader().parse(r, Db2DictionaryType.type, (XmlOptions)null);
      }

      public static Db2DictionaryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Db2DictionaryType)XmlBeans.getContextTypeLoader().parse(r, Db2DictionaryType.type, options);
      }

      public static Db2DictionaryType parse(XMLStreamReader sr) throws XmlException {
         return (Db2DictionaryType)XmlBeans.getContextTypeLoader().parse(sr, Db2DictionaryType.type, (XmlOptions)null);
      }

      public static Db2DictionaryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Db2DictionaryType)XmlBeans.getContextTypeLoader().parse(sr, Db2DictionaryType.type, options);
      }

      public static Db2DictionaryType parse(Node node) throws XmlException {
         return (Db2DictionaryType)XmlBeans.getContextTypeLoader().parse(node, Db2DictionaryType.type, (XmlOptions)null);
      }

      public static Db2DictionaryType parse(Node node, XmlOptions options) throws XmlException {
         return (Db2DictionaryType)XmlBeans.getContextTypeLoader().parse(node, Db2DictionaryType.type, options);
      }

      /** @deprecated */
      public static Db2DictionaryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Db2DictionaryType)XmlBeans.getContextTypeLoader().parse(xis, Db2DictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Db2DictionaryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Db2DictionaryType)XmlBeans.getContextTypeLoader().parse(xis, Db2DictionaryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Db2DictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Db2DictionaryType.type, options);
      }

      private Factory() {
      }
   }
}
