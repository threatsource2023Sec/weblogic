package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
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

public interface QueryCompilationCacheType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(QueryCompilationCacheType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("querycompilationcachetype5fd9type");

   public static final class Factory {
      public static QueryCompilationCacheType newInstance() {
         return (QueryCompilationCacheType)XmlBeans.getContextTypeLoader().newInstance(QueryCompilationCacheType.type, (XmlOptions)null);
      }

      public static QueryCompilationCacheType newInstance(XmlOptions options) {
         return (QueryCompilationCacheType)XmlBeans.getContextTypeLoader().newInstance(QueryCompilationCacheType.type, options);
      }

      public static QueryCompilationCacheType parse(String xmlAsString) throws XmlException {
         return (QueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, QueryCompilationCacheType.type, (XmlOptions)null);
      }

      public static QueryCompilationCacheType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (QueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, QueryCompilationCacheType.type, options);
      }

      public static QueryCompilationCacheType parse(File file) throws XmlException, IOException {
         return (QueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(file, QueryCompilationCacheType.type, (XmlOptions)null);
      }

      public static QueryCompilationCacheType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (QueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(file, QueryCompilationCacheType.type, options);
      }

      public static QueryCompilationCacheType parse(URL u) throws XmlException, IOException {
         return (QueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(u, QueryCompilationCacheType.type, (XmlOptions)null);
      }

      public static QueryCompilationCacheType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (QueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(u, QueryCompilationCacheType.type, options);
      }

      public static QueryCompilationCacheType parse(InputStream is) throws XmlException, IOException {
         return (QueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(is, QueryCompilationCacheType.type, (XmlOptions)null);
      }

      public static QueryCompilationCacheType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (QueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(is, QueryCompilationCacheType.type, options);
      }

      public static QueryCompilationCacheType parse(Reader r) throws XmlException, IOException {
         return (QueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(r, QueryCompilationCacheType.type, (XmlOptions)null);
      }

      public static QueryCompilationCacheType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (QueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(r, QueryCompilationCacheType.type, options);
      }

      public static QueryCompilationCacheType parse(XMLStreamReader sr) throws XmlException {
         return (QueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(sr, QueryCompilationCacheType.type, (XmlOptions)null);
      }

      public static QueryCompilationCacheType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (QueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(sr, QueryCompilationCacheType.type, options);
      }

      public static QueryCompilationCacheType parse(Node node) throws XmlException {
         return (QueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(node, QueryCompilationCacheType.type, (XmlOptions)null);
      }

      public static QueryCompilationCacheType parse(Node node, XmlOptions options) throws XmlException {
         return (QueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(node, QueryCompilationCacheType.type, options);
      }

      /** @deprecated */
      public static QueryCompilationCacheType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (QueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(xis, QueryCompilationCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static QueryCompilationCacheType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (QueryCompilationCacheType)XmlBeans.getContextTypeLoader().parse(xis, QueryCompilationCacheType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, QueryCompilationCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, QueryCompilationCacheType.type, options);
      }

      private Factory() {
      }
   }
}
