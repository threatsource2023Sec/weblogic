package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface DataCacheType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DataCacheType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("datacachetype15fbtype");

   String getName();

   XmlString xgetName();

   boolean isSetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   void unsetName();

   public static final class Factory {
      public static DataCacheType newInstance() {
         return (DataCacheType)XmlBeans.getContextTypeLoader().newInstance(DataCacheType.type, (XmlOptions)null);
      }

      public static DataCacheType newInstance(XmlOptions options) {
         return (DataCacheType)XmlBeans.getContextTypeLoader().newInstance(DataCacheType.type, options);
      }

      public static DataCacheType parse(String xmlAsString) throws XmlException {
         return (DataCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DataCacheType.type, (XmlOptions)null);
      }

      public static DataCacheType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DataCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DataCacheType.type, options);
      }

      public static DataCacheType parse(File file) throws XmlException, IOException {
         return (DataCacheType)XmlBeans.getContextTypeLoader().parse(file, DataCacheType.type, (XmlOptions)null);
      }

      public static DataCacheType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DataCacheType)XmlBeans.getContextTypeLoader().parse(file, DataCacheType.type, options);
      }

      public static DataCacheType parse(URL u) throws XmlException, IOException {
         return (DataCacheType)XmlBeans.getContextTypeLoader().parse(u, DataCacheType.type, (XmlOptions)null);
      }

      public static DataCacheType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DataCacheType)XmlBeans.getContextTypeLoader().parse(u, DataCacheType.type, options);
      }

      public static DataCacheType parse(InputStream is) throws XmlException, IOException {
         return (DataCacheType)XmlBeans.getContextTypeLoader().parse(is, DataCacheType.type, (XmlOptions)null);
      }

      public static DataCacheType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DataCacheType)XmlBeans.getContextTypeLoader().parse(is, DataCacheType.type, options);
      }

      public static DataCacheType parse(Reader r) throws XmlException, IOException {
         return (DataCacheType)XmlBeans.getContextTypeLoader().parse(r, DataCacheType.type, (XmlOptions)null);
      }

      public static DataCacheType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DataCacheType)XmlBeans.getContextTypeLoader().parse(r, DataCacheType.type, options);
      }

      public static DataCacheType parse(XMLStreamReader sr) throws XmlException {
         return (DataCacheType)XmlBeans.getContextTypeLoader().parse(sr, DataCacheType.type, (XmlOptions)null);
      }

      public static DataCacheType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DataCacheType)XmlBeans.getContextTypeLoader().parse(sr, DataCacheType.type, options);
      }

      public static DataCacheType parse(Node node) throws XmlException {
         return (DataCacheType)XmlBeans.getContextTypeLoader().parse(node, DataCacheType.type, (XmlOptions)null);
      }

      public static DataCacheType parse(Node node, XmlOptions options) throws XmlException {
         return (DataCacheType)XmlBeans.getContextTypeLoader().parse(node, DataCacheType.type, options);
      }

      /** @deprecated */
      public static DataCacheType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DataCacheType)XmlBeans.getContextTypeLoader().parse(xis, DataCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DataCacheType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DataCacheType)XmlBeans.getContextTypeLoader().parse(xis, DataCacheType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DataCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DataCacheType.type, options);
      }

      private Factory() {
      }
   }
}
