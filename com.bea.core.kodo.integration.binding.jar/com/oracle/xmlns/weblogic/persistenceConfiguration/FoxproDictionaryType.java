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

public interface FoxproDictionaryType extends BuiltInDbdictionaryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(FoxproDictionaryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("foxprodictionarytype6f47type");

   public static final class Factory {
      public static FoxproDictionaryType newInstance() {
         return (FoxproDictionaryType)XmlBeans.getContextTypeLoader().newInstance(FoxproDictionaryType.type, (XmlOptions)null);
      }

      public static FoxproDictionaryType newInstance(XmlOptions options) {
         return (FoxproDictionaryType)XmlBeans.getContextTypeLoader().newInstance(FoxproDictionaryType.type, options);
      }

      public static FoxproDictionaryType parse(String xmlAsString) throws XmlException {
         return (FoxproDictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FoxproDictionaryType.type, (XmlOptions)null);
      }

      public static FoxproDictionaryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (FoxproDictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FoxproDictionaryType.type, options);
      }

      public static FoxproDictionaryType parse(File file) throws XmlException, IOException {
         return (FoxproDictionaryType)XmlBeans.getContextTypeLoader().parse(file, FoxproDictionaryType.type, (XmlOptions)null);
      }

      public static FoxproDictionaryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (FoxproDictionaryType)XmlBeans.getContextTypeLoader().parse(file, FoxproDictionaryType.type, options);
      }

      public static FoxproDictionaryType parse(URL u) throws XmlException, IOException {
         return (FoxproDictionaryType)XmlBeans.getContextTypeLoader().parse(u, FoxproDictionaryType.type, (XmlOptions)null);
      }

      public static FoxproDictionaryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (FoxproDictionaryType)XmlBeans.getContextTypeLoader().parse(u, FoxproDictionaryType.type, options);
      }

      public static FoxproDictionaryType parse(InputStream is) throws XmlException, IOException {
         return (FoxproDictionaryType)XmlBeans.getContextTypeLoader().parse(is, FoxproDictionaryType.type, (XmlOptions)null);
      }

      public static FoxproDictionaryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (FoxproDictionaryType)XmlBeans.getContextTypeLoader().parse(is, FoxproDictionaryType.type, options);
      }

      public static FoxproDictionaryType parse(Reader r) throws XmlException, IOException {
         return (FoxproDictionaryType)XmlBeans.getContextTypeLoader().parse(r, FoxproDictionaryType.type, (XmlOptions)null);
      }

      public static FoxproDictionaryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (FoxproDictionaryType)XmlBeans.getContextTypeLoader().parse(r, FoxproDictionaryType.type, options);
      }

      public static FoxproDictionaryType parse(XMLStreamReader sr) throws XmlException {
         return (FoxproDictionaryType)XmlBeans.getContextTypeLoader().parse(sr, FoxproDictionaryType.type, (XmlOptions)null);
      }

      public static FoxproDictionaryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (FoxproDictionaryType)XmlBeans.getContextTypeLoader().parse(sr, FoxproDictionaryType.type, options);
      }

      public static FoxproDictionaryType parse(Node node) throws XmlException {
         return (FoxproDictionaryType)XmlBeans.getContextTypeLoader().parse(node, FoxproDictionaryType.type, (XmlOptions)null);
      }

      public static FoxproDictionaryType parse(Node node, XmlOptions options) throws XmlException {
         return (FoxproDictionaryType)XmlBeans.getContextTypeLoader().parse(node, FoxproDictionaryType.type, options);
      }

      /** @deprecated */
      public static FoxproDictionaryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (FoxproDictionaryType)XmlBeans.getContextTypeLoader().parse(xis, FoxproDictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static FoxproDictionaryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (FoxproDictionaryType)XmlBeans.getContextTypeLoader().parse(xis, FoxproDictionaryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FoxproDictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FoxproDictionaryType.type, options);
      }

      private Factory() {
      }
   }
}
