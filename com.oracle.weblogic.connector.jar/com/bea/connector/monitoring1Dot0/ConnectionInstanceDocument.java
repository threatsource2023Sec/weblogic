package com.bea.connector.monitoring1Dot0;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
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

public interface ConnectionInstanceDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConnectionInstanceDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("connectioninstance215cdoctype");

   ConnectionInstance getConnectionInstance();

   void setConnectionInstance(ConnectionInstance var1);

   ConnectionInstance addNewConnectionInstance();

   public static final class Factory {
      public static ConnectionInstanceDocument newInstance() {
         return (ConnectionInstanceDocument)XmlBeans.getContextTypeLoader().newInstance(ConnectionInstanceDocument.type, (XmlOptions)null);
      }

      public static ConnectionInstanceDocument newInstance(XmlOptions options) {
         return (ConnectionInstanceDocument)XmlBeans.getContextTypeLoader().newInstance(ConnectionInstanceDocument.type, options);
      }

      public static ConnectionInstanceDocument parse(String xmlAsString) throws XmlException {
         return (ConnectionInstanceDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionInstanceDocument.type, (XmlOptions)null);
      }

      public static ConnectionInstanceDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConnectionInstanceDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionInstanceDocument.type, options);
      }

      public static ConnectionInstanceDocument parse(File file) throws XmlException, IOException {
         return (ConnectionInstanceDocument)XmlBeans.getContextTypeLoader().parse(file, ConnectionInstanceDocument.type, (XmlOptions)null);
      }

      public static ConnectionInstanceDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConnectionInstanceDocument)XmlBeans.getContextTypeLoader().parse(file, ConnectionInstanceDocument.type, options);
      }

      public static ConnectionInstanceDocument parse(URL u) throws XmlException, IOException {
         return (ConnectionInstanceDocument)XmlBeans.getContextTypeLoader().parse(u, ConnectionInstanceDocument.type, (XmlOptions)null);
      }

      public static ConnectionInstanceDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConnectionInstanceDocument)XmlBeans.getContextTypeLoader().parse(u, ConnectionInstanceDocument.type, options);
      }

      public static ConnectionInstanceDocument parse(InputStream is) throws XmlException, IOException {
         return (ConnectionInstanceDocument)XmlBeans.getContextTypeLoader().parse(is, ConnectionInstanceDocument.type, (XmlOptions)null);
      }

      public static ConnectionInstanceDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConnectionInstanceDocument)XmlBeans.getContextTypeLoader().parse(is, ConnectionInstanceDocument.type, options);
      }

      public static ConnectionInstanceDocument parse(Reader r) throws XmlException, IOException {
         return (ConnectionInstanceDocument)XmlBeans.getContextTypeLoader().parse(r, ConnectionInstanceDocument.type, (XmlOptions)null);
      }

      public static ConnectionInstanceDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConnectionInstanceDocument)XmlBeans.getContextTypeLoader().parse(r, ConnectionInstanceDocument.type, options);
      }

      public static ConnectionInstanceDocument parse(XMLStreamReader sr) throws XmlException {
         return (ConnectionInstanceDocument)XmlBeans.getContextTypeLoader().parse(sr, ConnectionInstanceDocument.type, (XmlOptions)null);
      }

      public static ConnectionInstanceDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConnectionInstanceDocument)XmlBeans.getContextTypeLoader().parse(sr, ConnectionInstanceDocument.type, options);
      }

      public static ConnectionInstanceDocument parse(Node node) throws XmlException {
         return (ConnectionInstanceDocument)XmlBeans.getContextTypeLoader().parse(node, ConnectionInstanceDocument.type, (XmlOptions)null);
      }

      public static ConnectionInstanceDocument parse(Node node, XmlOptions options) throws XmlException {
         return (ConnectionInstanceDocument)XmlBeans.getContextTypeLoader().parse(node, ConnectionInstanceDocument.type, options);
      }

      /** @deprecated */
      public static ConnectionInstanceDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConnectionInstanceDocument)XmlBeans.getContextTypeLoader().parse(xis, ConnectionInstanceDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConnectionInstanceDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConnectionInstanceDocument)XmlBeans.getContextTypeLoader().parse(xis, ConnectionInstanceDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionInstanceDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionInstanceDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface ConnectionInstance extends XmlObject {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConnectionInstance.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("connectioninstance544celemtype");

      String getJndiName();

      XmlString xgetJndiName();

      void setJndiName(String var1);

      void xsetJndiName(XmlString var1);

      String getDescription();

      XmlString xgetDescription();

      boolean isSetDescription();

      void setDescription(String var1);

      void xsetDescription(XmlString var1);

      void unsetDescription();

      String getTransactionSupport();

      XmlString xgetTransactionSupport();

      void setTransactionSupport(String var1);

      void xsetTransactionSupport(XmlString var1);

      ConnectionPoolParamsType getPoolParams();

      boolean isSetPoolParams();

      void setPoolParams(ConnectionPoolParamsType var1);

      ConnectionPoolParamsType addNewPoolParams();

      void unsetPoolParams();

      LoggingType getLogging();

      boolean isSetLogging();

      void setLogging(LoggingType var1);

      LoggingType addNewLogging();

      void unsetLogging();

      AuthenticationMechanismType[] getAuthenticationMechanismArray();

      AuthenticationMechanismType getAuthenticationMechanismArray(int var1);

      int sizeOfAuthenticationMechanismArray();

      void setAuthenticationMechanismArray(AuthenticationMechanismType[] var1);

      void setAuthenticationMechanismArray(int var1, AuthenticationMechanismType var2);

      AuthenticationMechanismType insertNewAuthenticationMechanism(int var1);

      AuthenticationMechanismType addNewAuthenticationMechanism();

      void removeAuthenticationMechanism(int var1);

      boolean getReauthenticationSupport();

      XmlBoolean xgetReauthenticationSupport();

      boolean isSetReauthenticationSupport();

      void setReauthenticationSupport(boolean var1);

      void xsetReauthenticationSupport(XmlBoolean var1);

      void unsetReauthenticationSupport();

      ConfigPropertiesType getProperties();

      boolean isSetProperties();

      void setProperties(ConfigPropertiesType var1);

      ConfigPropertiesType addNewProperties();

      void unsetProperties();

      String getResAuth();

      XmlString xgetResAuth();

      boolean isSetResAuth();

      void setResAuth(String var1);

      void xsetResAuth(XmlString var1);

      void unsetResAuth();

      public static final class Factory {
         public static ConnectionInstance newInstance() {
            return (ConnectionInstance)XmlBeans.getContextTypeLoader().newInstance(ConnectionInstanceDocument.ConnectionInstance.type, (XmlOptions)null);
         }

         public static ConnectionInstance newInstance(XmlOptions options) {
            return (ConnectionInstance)XmlBeans.getContextTypeLoader().newInstance(ConnectionInstanceDocument.ConnectionInstance.type, options);
         }

         private Factory() {
         }
      }
   }
}
