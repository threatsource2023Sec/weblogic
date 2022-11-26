package com.bea.ns.weblogic.x60;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface DataSourceJndiNameType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DataSourceJndiNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("datasourcejndinametype7ecdtype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static DataSourceJndiNameType newInstance() {
         return (DataSourceJndiNameType)XmlBeans.getContextTypeLoader().newInstance(DataSourceJndiNameType.type, (XmlOptions)null);
      }

      public static DataSourceJndiNameType newInstance(XmlOptions options) {
         return (DataSourceJndiNameType)XmlBeans.getContextTypeLoader().newInstance(DataSourceJndiNameType.type, options);
      }

      public static DataSourceJndiNameType parse(String xmlAsString) throws XmlException {
         return (DataSourceJndiNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DataSourceJndiNameType.type, (XmlOptions)null);
      }

      public static DataSourceJndiNameType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DataSourceJndiNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DataSourceJndiNameType.type, options);
      }

      public static DataSourceJndiNameType parse(File file) throws XmlException, IOException {
         return (DataSourceJndiNameType)XmlBeans.getContextTypeLoader().parse(file, DataSourceJndiNameType.type, (XmlOptions)null);
      }

      public static DataSourceJndiNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DataSourceJndiNameType)XmlBeans.getContextTypeLoader().parse(file, DataSourceJndiNameType.type, options);
      }

      public static DataSourceJndiNameType parse(URL u) throws XmlException, IOException {
         return (DataSourceJndiNameType)XmlBeans.getContextTypeLoader().parse(u, DataSourceJndiNameType.type, (XmlOptions)null);
      }

      public static DataSourceJndiNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DataSourceJndiNameType)XmlBeans.getContextTypeLoader().parse(u, DataSourceJndiNameType.type, options);
      }

      public static DataSourceJndiNameType parse(InputStream is) throws XmlException, IOException {
         return (DataSourceJndiNameType)XmlBeans.getContextTypeLoader().parse(is, DataSourceJndiNameType.type, (XmlOptions)null);
      }

      public static DataSourceJndiNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DataSourceJndiNameType)XmlBeans.getContextTypeLoader().parse(is, DataSourceJndiNameType.type, options);
      }

      public static DataSourceJndiNameType parse(Reader r) throws XmlException, IOException {
         return (DataSourceJndiNameType)XmlBeans.getContextTypeLoader().parse(r, DataSourceJndiNameType.type, (XmlOptions)null);
      }

      public static DataSourceJndiNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DataSourceJndiNameType)XmlBeans.getContextTypeLoader().parse(r, DataSourceJndiNameType.type, options);
      }

      public static DataSourceJndiNameType parse(XMLStreamReader sr) throws XmlException {
         return (DataSourceJndiNameType)XmlBeans.getContextTypeLoader().parse(sr, DataSourceJndiNameType.type, (XmlOptions)null);
      }

      public static DataSourceJndiNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DataSourceJndiNameType)XmlBeans.getContextTypeLoader().parse(sr, DataSourceJndiNameType.type, options);
      }

      public static DataSourceJndiNameType parse(Node node) throws XmlException {
         return (DataSourceJndiNameType)XmlBeans.getContextTypeLoader().parse(node, DataSourceJndiNameType.type, (XmlOptions)null);
      }

      public static DataSourceJndiNameType parse(Node node, XmlOptions options) throws XmlException {
         return (DataSourceJndiNameType)XmlBeans.getContextTypeLoader().parse(node, DataSourceJndiNameType.type, options);
      }

      /** @deprecated */
      public static DataSourceJndiNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DataSourceJndiNameType)XmlBeans.getContextTypeLoader().parse(xis, DataSourceJndiNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DataSourceJndiNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DataSourceJndiNameType)XmlBeans.getContextTypeLoader().parse(xis, DataSourceJndiNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DataSourceJndiNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DataSourceJndiNameType.type, options);
      }

      private Factory() {
      }
   }
}
