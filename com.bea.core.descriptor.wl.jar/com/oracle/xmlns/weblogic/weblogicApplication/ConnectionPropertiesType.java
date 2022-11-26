package com.oracle.xmlns.weblogic.weblogicApplication;

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

public interface ConnectionPropertiesType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConnectionPropertiesType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("connectionpropertiestypebbabtype");

   String getUserName();

   XmlString xgetUserName();

   boolean isSetUserName();

   void setUserName(String var1);

   void xsetUserName(XmlString var1);

   void unsetUserName();

   String getPassword();

   XmlString xgetPassword();

   boolean isSetPassword();

   void setPassword(String var1);

   void xsetPassword(XmlString var1);

   void unsetPassword();

   String getUrl();

   XmlString xgetUrl();

   boolean isSetUrl();

   void setUrl(String var1);

   void xsetUrl(XmlString var1);

   void unsetUrl();

   String getDriverClassName();

   XmlString xgetDriverClassName();

   boolean isSetDriverClassName();

   void setDriverClassName(String var1);

   void xsetDriverClassName(XmlString var1);

   void unsetDriverClassName();

   ConnectionParamsType[] getConnectionParamsArray();

   ConnectionParamsType getConnectionParamsArray(int var1);

   int sizeOfConnectionParamsArray();

   void setConnectionParamsArray(ConnectionParamsType[] var1);

   void setConnectionParamsArray(int var1, ConnectionParamsType var2);

   ConnectionParamsType insertNewConnectionParams(int var1);

   ConnectionParamsType addNewConnectionParams();

   void removeConnectionParams(int var1);

   public static final class Factory {
      public static ConnectionPropertiesType newInstance() {
         return (ConnectionPropertiesType)XmlBeans.getContextTypeLoader().newInstance(ConnectionPropertiesType.type, (XmlOptions)null);
      }

      public static ConnectionPropertiesType newInstance(XmlOptions options) {
         return (ConnectionPropertiesType)XmlBeans.getContextTypeLoader().newInstance(ConnectionPropertiesType.type, options);
      }

      public static ConnectionPropertiesType parse(String xmlAsString) throws XmlException {
         return (ConnectionPropertiesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionPropertiesType.type, (XmlOptions)null);
      }

      public static ConnectionPropertiesType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConnectionPropertiesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionPropertiesType.type, options);
      }

      public static ConnectionPropertiesType parse(File file) throws XmlException, IOException {
         return (ConnectionPropertiesType)XmlBeans.getContextTypeLoader().parse(file, ConnectionPropertiesType.type, (XmlOptions)null);
      }

      public static ConnectionPropertiesType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConnectionPropertiesType)XmlBeans.getContextTypeLoader().parse(file, ConnectionPropertiesType.type, options);
      }

      public static ConnectionPropertiesType parse(URL u) throws XmlException, IOException {
         return (ConnectionPropertiesType)XmlBeans.getContextTypeLoader().parse(u, ConnectionPropertiesType.type, (XmlOptions)null);
      }

      public static ConnectionPropertiesType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConnectionPropertiesType)XmlBeans.getContextTypeLoader().parse(u, ConnectionPropertiesType.type, options);
      }

      public static ConnectionPropertiesType parse(InputStream is) throws XmlException, IOException {
         return (ConnectionPropertiesType)XmlBeans.getContextTypeLoader().parse(is, ConnectionPropertiesType.type, (XmlOptions)null);
      }

      public static ConnectionPropertiesType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConnectionPropertiesType)XmlBeans.getContextTypeLoader().parse(is, ConnectionPropertiesType.type, options);
      }

      public static ConnectionPropertiesType parse(Reader r) throws XmlException, IOException {
         return (ConnectionPropertiesType)XmlBeans.getContextTypeLoader().parse(r, ConnectionPropertiesType.type, (XmlOptions)null);
      }

      public static ConnectionPropertiesType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConnectionPropertiesType)XmlBeans.getContextTypeLoader().parse(r, ConnectionPropertiesType.type, options);
      }

      public static ConnectionPropertiesType parse(XMLStreamReader sr) throws XmlException {
         return (ConnectionPropertiesType)XmlBeans.getContextTypeLoader().parse(sr, ConnectionPropertiesType.type, (XmlOptions)null);
      }

      public static ConnectionPropertiesType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConnectionPropertiesType)XmlBeans.getContextTypeLoader().parse(sr, ConnectionPropertiesType.type, options);
      }

      public static ConnectionPropertiesType parse(Node node) throws XmlException {
         return (ConnectionPropertiesType)XmlBeans.getContextTypeLoader().parse(node, ConnectionPropertiesType.type, (XmlOptions)null);
      }

      public static ConnectionPropertiesType parse(Node node, XmlOptions options) throws XmlException {
         return (ConnectionPropertiesType)XmlBeans.getContextTypeLoader().parse(node, ConnectionPropertiesType.type, options);
      }

      /** @deprecated */
      public static ConnectionPropertiesType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConnectionPropertiesType)XmlBeans.getContextTypeLoader().parse(xis, ConnectionPropertiesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConnectionPropertiesType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConnectionPropertiesType)XmlBeans.getContextTypeLoader().parse(xis, ConnectionPropertiesType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionPropertiesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionPropertiesType.type, options);
      }

      private Factory() {
      }
   }
}
