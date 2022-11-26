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

public interface SimpleDriverDataSourceType extends DriverDataSourceType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SimpleDriverDataSourceType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("simpledriverdatasourcetype0538type");

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
      public static SimpleDriverDataSourceType newInstance() {
         return (SimpleDriverDataSourceType)XmlBeans.getContextTypeLoader().newInstance(SimpleDriverDataSourceType.type, (XmlOptions)null);
      }

      public static SimpleDriverDataSourceType newInstance(XmlOptions options) {
         return (SimpleDriverDataSourceType)XmlBeans.getContextTypeLoader().newInstance(SimpleDriverDataSourceType.type, options);
      }

      public static SimpleDriverDataSourceType parse(String xmlAsString) throws XmlException {
         return (SimpleDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SimpleDriverDataSourceType.type, (XmlOptions)null);
      }

      public static SimpleDriverDataSourceType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SimpleDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SimpleDriverDataSourceType.type, options);
      }

      public static SimpleDriverDataSourceType parse(File file) throws XmlException, IOException {
         return (SimpleDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(file, SimpleDriverDataSourceType.type, (XmlOptions)null);
      }

      public static SimpleDriverDataSourceType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SimpleDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(file, SimpleDriverDataSourceType.type, options);
      }

      public static SimpleDriverDataSourceType parse(URL u) throws XmlException, IOException {
         return (SimpleDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(u, SimpleDriverDataSourceType.type, (XmlOptions)null);
      }

      public static SimpleDriverDataSourceType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SimpleDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(u, SimpleDriverDataSourceType.type, options);
      }

      public static SimpleDriverDataSourceType parse(InputStream is) throws XmlException, IOException {
         return (SimpleDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(is, SimpleDriverDataSourceType.type, (XmlOptions)null);
      }

      public static SimpleDriverDataSourceType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SimpleDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(is, SimpleDriverDataSourceType.type, options);
      }

      public static SimpleDriverDataSourceType parse(Reader r) throws XmlException, IOException {
         return (SimpleDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(r, SimpleDriverDataSourceType.type, (XmlOptions)null);
      }

      public static SimpleDriverDataSourceType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SimpleDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(r, SimpleDriverDataSourceType.type, options);
      }

      public static SimpleDriverDataSourceType parse(XMLStreamReader sr) throws XmlException {
         return (SimpleDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(sr, SimpleDriverDataSourceType.type, (XmlOptions)null);
      }

      public static SimpleDriverDataSourceType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SimpleDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(sr, SimpleDriverDataSourceType.type, options);
      }

      public static SimpleDriverDataSourceType parse(Node node) throws XmlException {
         return (SimpleDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(node, SimpleDriverDataSourceType.type, (XmlOptions)null);
      }

      public static SimpleDriverDataSourceType parse(Node node, XmlOptions options) throws XmlException {
         return (SimpleDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(node, SimpleDriverDataSourceType.type, options);
      }

      /** @deprecated */
      public static SimpleDriverDataSourceType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SimpleDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(xis, SimpleDriverDataSourceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SimpleDriverDataSourceType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SimpleDriverDataSourceType)XmlBeans.getContextTypeLoader().parse(xis, SimpleDriverDataSourceType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SimpleDriverDataSourceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SimpleDriverDataSourceType.type, options);
      }

      private Factory() {
      }
   }
}
