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

public interface DefaultQueryCompilationCacheType extends QueryCompilationCacheType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DefaultQueryCompilationCacheType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("defaultquerycompilationcachetypee7e5type");

   public static final class Factory {
      public static DefaultQueryCompilationCacheType newInstance() {
         return (DefaultQueryCompilationCacheType)XmlBeans.getContextTypeLoader().newInstance(DefaultQueryCompilationCacheType.type, (XmlOptions)null);
      }

      public static DefaultQueryCompilationCacheType newInstance(XmlOptions options) {
         return (DefaultQueryCompilationCacheType)XmlBeans.getContextTypeLoader().newInstance(DefaultQueryCompilationCacheType.type, options);
      }

      public static DefaultQueryCompilationCacheType parse(String xmlAsString) throws XmlException {
         return (DefaultQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultQueryCompilationCacheType.type, (XmlOptions)null);
      }

      public static DefaultQueryCompilationCacheType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DefaultQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultQueryCompilationCacheType.type, options);
      }

      public static DefaultQueryCompilationCacheType parse(File file) throws XmlException, IOException {
         return (DefaultQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(file, DefaultQueryCompilationCacheType.type, (XmlOptions)null);
      }

      public static DefaultQueryCompilationCacheType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DefaultQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(file, DefaultQueryCompilationCacheType.type, options);
      }

      public static DefaultQueryCompilationCacheType parse(URL u) throws XmlException, IOException {
         return (DefaultQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(u, DefaultQueryCompilationCacheType.type, (XmlOptions)null);
      }

      public static DefaultQueryCompilationCacheType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DefaultQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(u, DefaultQueryCompilationCacheType.type, options);
      }

      public static DefaultQueryCompilationCacheType parse(InputStream is) throws XmlException, IOException {
         return (DefaultQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(is, DefaultQueryCompilationCacheType.type, (XmlOptions)null);
      }

      public static DefaultQueryCompilationCacheType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DefaultQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(is, DefaultQueryCompilationCacheType.type, options);
      }

      public static DefaultQueryCompilationCacheType parse(Reader r) throws XmlException, IOException {
         return (DefaultQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(r, DefaultQueryCompilationCacheType.type, (XmlOptions)null);
      }

      public static DefaultQueryCompilationCacheType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DefaultQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(r, DefaultQueryCompilationCacheType.type, options);
      }

      public static DefaultQueryCompilationCacheType parse(XMLStreamReader sr) throws XmlException {
         return (DefaultQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(sr, DefaultQueryCompilationCacheType.type, (XmlOptions)null);
      }

      public static DefaultQueryCompilationCacheType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DefaultQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(sr, DefaultQueryCompilationCacheType.type, options);
      }

      public static DefaultQueryCompilationCacheType parse(Node node) throws XmlException {
         return (DefaultQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(node, DefaultQueryCompilationCacheType.type, (XmlOptions)null);
      }

      public static DefaultQueryCompilationCacheType parse(Node node, XmlOptions options) throws XmlException {
         return (DefaultQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(node, DefaultQueryCompilationCacheType.type, options);
      }

      /** @deprecated */
      public static DefaultQueryCompilationCacheType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DefaultQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(xis, DefaultQueryCompilationCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DefaultQueryCompilationCacheType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DefaultQueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(xis, DefaultQueryCompilationCacheType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultQueryCompilationCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultQueryCompilationCacheType.type, options);
      }

      private Factory() {
      }
   }
}
