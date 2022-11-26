package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
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

public interface KodoPoolingDataSourceType extends DriverDataSourceType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(KodoPoolingDataSourceType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("kodopoolingdatasourcetype4561type");

   String getConnectionUserName();

   XmlString xgetConnectionUserName();

   boolean isNilConnectionUserName();

   boolean isSetConnectionUserName();

   void setConnectionUserName(String var1);

   void xsetConnectionUserName(XmlString var1);

   void setNilConnectionUserName();

   void unsetConnectionUserName();

   int getLoginTimeout();

   XmlInt xgetLoginTimeout();

   boolean isSetLoginTimeout();

   void setLoginTimeout(int var1);

   void xsetLoginTimeout(XmlInt var1);

   void unsetLoginTimeout();

   String getConnectionPassword();

   XmlString xgetConnectionPassword();

   boolean isNilConnectionPassword();

   boolean isSetConnectionPassword();

   void setConnectionPassword(String var1);

   void xsetConnectionPassword(XmlString var1);

   void setNilConnectionPassword();

   void unsetConnectionPassword();

   String getConnectionUrl();

   XmlString xgetConnectionUrl();

   boolean isNilConnectionUrl();

   boolean isSetConnectionUrl();

   void setConnectionUrl(String var1);

   void xsetConnectionUrl(XmlString var1);

   void setNilConnectionUrl();

   void unsetConnectionUrl();

   String getConnectionDriverName();

   XmlString xgetConnectionDriverName();

   boolean isNilConnectionDriverName();

   boolean isSetConnectionDriverName();

   void setConnectionDriverName(String var1);

   void xsetConnectionDriverName(XmlString var1);

   void setNilConnectionDriverName();

   void unsetConnectionDriverName();

   public static final class Factory {
      public static KodoPoolingDataSourceType newInstance() {
         return (KodoPoolingDataSourceType)XmlBeans.getContextTypeLoader().newInstance(KodoPoolingDataSourceType.type, (XmlOptions)null);
      }

      public static KodoPoolingDataSourceType newInstance(XmlOptions options) {
         return (KodoPoolingDataSourceType)XmlBeans.getContextTypeLoader().newInstance(KodoPoolingDataSourceType.type, options);
      }

      public static KodoPoolingDataSourceType parse(String xmlAsString) throws XmlException {
         return (KodoPoolingDataSourceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, KodoPoolingDataSourceType.type, (XmlOptions)null);
      }

      public static KodoPoolingDataSourceType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (KodoPoolingDataSourceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, KodoPoolingDataSourceType.type, options);
      }

      public static KodoPoolingDataSourceType parse(File file) throws XmlException, IOException {
         return (KodoPoolingDataSourceType)XmlBeans.getContextTypeLoader().parse(file, KodoPoolingDataSourceType.type, (XmlOptions)null);
      }

      public static KodoPoolingDataSourceType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (KodoPoolingDataSourceType)XmlBeans.getContextTypeLoader().parse(file, KodoPoolingDataSourceType.type, options);
      }

      public static KodoPoolingDataSourceType parse(URL u) throws XmlException, IOException {
         return (KodoPoolingDataSourceType)XmlBeans.getContextTypeLoader().parse(u, KodoPoolingDataSourceType.type, (XmlOptions)null);
      }

      public static KodoPoolingDataSourceType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (KodoPoolingDataSourceType)XmlBeans.getContextTypeLoader().parse(u, KodoPoolingDataSourceType.type, options);
      }

      public static KodoPoolingDataSourceType parse(InputStream is) throws XmlException, IOException {
         return (KodoPoolingDataSourceType)XmlBeans.getContextTypeLoader().parse(is, KodoPoolingDataSourceType.type, (XmlOptions)null);
      }

      public static KodoPoolingDataSourceType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (KodoPoolingDataSourceType)XmlBeans.getContextTypeLoader().parse(is, KodoPoolingDataSourceType.type, options);
      }

      public static KodoPoolingDataSourceType parse(Reader r) throws XmlException, IOException {
         return (KodoPoolingDataSourceType)XmlBeans.getContextTypeLoader().parse(r, KodoPoolingDataSourceType.type, (XmlOptions)null);
      }

      public static KodoPoolingDataSourceType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (KodoPoolingDataSourceType)XmlBeans.getContextTypeLoader().parse(r, KodoPoolingDataSourceType.type, options);
      }

      public static KodoPoolingDataSourceType parse(XMLStreamReader sr) throws XmlException {
         return (KodoPoolingDataSourceType)XmlBeans.getContextTypeLoader().parse(sr, KodoPoolingDataSourceType.type, (XmlOptions)null);
      }

      public static KodoPoolingDataSourceType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (KodoPoolingDataSourceType)XmlBeans.getContextTypeLoader().parse(sr, KodoPoolingDataSourceType.type, options);
      }

      public static KodoPoolingDataSourceType parse(Node node) throws XmlException {
         return (KodoPoolingDataSourceType)XmlBeans.getContextTypeLoader().parse(node, KodoPoolingDataSourceType.type, (XmlOptions)null);
      }

      public static KodoPoolingDataSourceType parse(Node node, XmlOptions options) throws XmlException {
         return (KodoPoolingDataSourceType)XmlBeans.getContextTypeLoader().parse(node, KodoPoolingDataSourceType.type, options);
      }

      /** @deprecated */
      public static KodoPoolingDataSourceType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (KodoPoolingDataSourceType)XmlBeans.getContextTypeLoader().parse(xis, KodoPoolingDataSourceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static KodoPoolingDataSourceType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (KodoPoolingDataSourceType)XmlBeans.getContextTypeLoader().parse(xis, KodoPoolingDataSourceType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, KodoPoolingDataSourceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, KodoPoolingDataSourceType.type, options);
      }

      private Factory() {
      }
   }
}
