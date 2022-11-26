package com.oracle.xmlns.weblogic.weblogicConnector;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.AuthenticationMechanismType;
import com.sun.java.xml.ns.javaee.ResAuthType;
import com.sun.java.xml.ns.javaee.TransactionSupportType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface ConnectionDefinitionPropertiesType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConnectionDefinitionPropertiesType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("connectiondefinitionpropertiestype1956type");

   PoolParamsType getPoolParams();

   boolean isSetPoolParams();

   void setPoolParams(PoolParamsType var1);

   PoolParamsType addNewPoolParams();

   void unsetPoolParams();

   LoggingType getLogging();

   boolean isSetLogging();

   void setLogging(LoggingType var1);

   LoggingType addNewLogging();

   void unsetLogging();

   TransactionSupportType getTransactionSupport();

   boolean isSetTransactionSupport();

   void setTransactionSupport(TransactionSupportType var1);

   TransactionSupportType addNewTransactionSupport();

   void unsetTransactionSupport();

   AuthenticationMechanismType[] getAuthenticationMechanismArray();

   AuthenticationMechanismType getAuthenticationMechanismArray(int var1);

   int sizeOfAuthenticationMechanismArray();

   void setAuthenticationMechanismArray(AuthenticationMechanismType[] var1);

   void setAuthenticationMechanismArray(int var1, AuthenticationMechanismType var2);

   AuthenticationMechanismType insertNewAuthenticationMechanism(int var1);

   AuthenticationMechanismType addNewAuthenticationMechanism();

   void removeAuthenticationMechanism(int var1);

   TrueFalseType getReauthenticationSupport();

   boolean isSetReauthenticationSupport();

   void setReauthenticationSupport(TrueFalseType var1);

   TrueFalseType addNewReauthenticationSupport();

   void unsetReauthenticationSupport();

   ConfigPropertiesType getProperties();

   boolean isSetProperties();

   void setProperties(ConfigPropertiesType var1);

   ConfigPropertiesType addNewProperties();

   void unsetProperties();

   ResAuthType getResAuth();

   boolean isSetResAuth();

   void setResAuth(ResAuthType var1);

   ResAuthType addNewResAuth();

   void unsetResAuth();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ConnectionDefinitionPropertiesType newInstance() {
         return (ConnectionDefinitionPropertiesType)XmlBeans.getContextTypeLoader().newInstance(ConnectionDefinitionPropertiesType.type, (XmlOptions)null);
      }

      public static ConnectionDefinitionPropertiesType newInstance(XmlOptions options) {
         return (ConnectionDefinitionPropertiesType)XmlBeans.getContextTypeLoader().newInstance(ConnectionDefinitionPropertiesType.type, options);
      }

      public static ConnectionDefinitionPropertiesType parse(String xmlAsString) throws XmlException {
         return (ConnectionDefinitionPropertiesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionDefinitionPropertiesType.type, (XmlOptions)null);
      }

      public static ConnectionDefinitionPropertiesType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConnectionDefinitionPropertiesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionDefinitionPropertiesType.type, options);
      }

      public static ConnectionDefinitionPropertiesType parse(File file) throws XmlException, IOException {
         return (ConnectionDefinitionPropertiesType)XmlBeans.getContextTypeLoader().parse(file, ConnectionDefinitionPropertiesType.type, (XmlOptions)null);
      }

      public static ConnectionDefinitionPropertiesType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConnectionDefinitionPropertiesType)XmlBeans.getContextTypeLoader().parse(file, ConnectionDefinitionPropertiesType.type, options);
      }

      public static ConnectionDefinitionPropertiesType parse(URL u) throws XmlException, IOException {
         return (ConnectionDefinitionPropertiesType)XmlBeans.getContextTypeLoader().parse(u, ConnectionDefinitionPropertiesType.type, (XmlOptions)null);
      }

      public static ConnectionDefinitionPropertiesType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConnectionDefinitionPropertiesType)XmlBeans.getContextTypeLoader().parse(u, ConnectionDefinitionPropertiesType.type, options);
      }

      public static ConnectionDefinitionPropertiesType parse(InputStream is) throws XmlException, IOException {
         return (ConnectionDefinitionPropertiesType)XmlBeans.getContextTypeLoader().parse(is, ConnectionDefinitionPropertiesType.type, (XmlOptions)null);
      }

      public static ConnectionDefinitionPropertiesType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConnectionDefinitionPropertiesType)XmlBeans.getContextTypeLoader().parse(is, ConnectionDefinitionPropertiesType.type, options);
      }

      public static ConnectionDefinitionPropertiesType parse(Reader r) throws XmlException, IOException {
         return (ConnectionDefinitionPropertiesType)XmlBeans.getContextTypeLoader().parse(r, ConnectionDefinitionPropertiesType.type, (XmlOptions)null);
      }

      public static ConnectionDefinitionPropertiesType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConnectionDefinitionPropertiesType)XmlBeans.getContextTypeLoader().parse(r, ConnectionDefinitionPropertiesType.type, options);
      }

      public static ConnectionDefinitionPropertiesType parse(XMLStreamReader sr) throws XmlException {
         return (ConnectionDefinitionPropertiesType)XmlBeans.getContextTypeLoader().parse(sr, ConnectionDefinitionPropertiesType.type, (XmlOptions)null);
      }

      public static ConnectionDefinitionPropertiesType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConnectionDefinitionPropertiesType)XmlBeans.getContextTypeLoader().parse(sr, ConnectionDefinitionPropertiesType.type, options);
      }

      public static ConnectionDefinitionPropertiesType parse(Node node) throws XmlException {
         return (ConnectionDefinitionPropertiesType)XmlBeans.getContextTypeLoader().parse(node, ConnectionDefinitionPropertiesType.type, (XmlOptions)null);
      }

      public static ConnectionDefinitionPropertiesType parse(Node node, XmlOptions options) throws XmlException {
         return (ConnectionDefinitionPropertiesType)XmlBeans.getContextTypeLoader().parse(node, ConnectionDefinitionPropertiesType.type, options);
      }

      /** @deprecated */
      public static ConnectionDefinitionPropertiesType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConnectionDefinitionPropertiesType)XmlBeans.getContextTypeLoader().parse(xis, ConnectionDefinitionPropertiesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConnectionDefinitionPropertiesType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConnectionDefinitionPropertiesType)XmlBeans.getContextTypeLoader().parse(xis, ConnectionDefinitionPropertiesType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionDefinitionPropertiesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionDefinitionPropertiesType.type, options);
      }

      private Factory() {
      }
   }
}
