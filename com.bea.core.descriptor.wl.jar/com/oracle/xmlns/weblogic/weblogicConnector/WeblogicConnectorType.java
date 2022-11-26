package com.oracle.xmlns.weblogic.weblogicConnector;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import com.sun.java.xml.ns.javaee.JndiNameType;
import com.sun.java.xml.ns.javaee.String;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface WeblogicConnectorType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicConnectorType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("weblogicconnectortype97a2type");

   String getNativeLibdir();

   boolean isSetNativeLibdir();

   void setNativeLibdir(String var1);

   String addNewNativeLibdir();

   void unsetNativeLibdir();

   JndiNameType getJndiName();

   boolean isSetJndiName();

   void setJndiName(JndiNameType var1);

   JndiNameType addNewJndiName();

   void unsetJndiName();

   TrueFalseType getEnableAccessOutsideApp();

   boolean isSetEnableAccessOutsideApp();

   void setEnableAccessOutsideApp(TrueFalseType var1);

   TrueFalseType addNewEnableAccessOutsideApp();

   void unsetEnableAccessOutsideApp();

   TrueFalseType getEnableGlobalAccessToClasses();

   boolean isSetEnableGlobalAccessToClasses();

   void setEnableGlobalAccessToClasses(TrueFalseType var1);

   TrueFalseType addNewEnableGlobalAccessToClasses();

   void unsetEnableGlobalAccessToClasses();

   TrueFalseType getDeployAsAWhole();

   boolean isSetDeployAsAWhole();

   void setDeployAsAWhole(TrueFalseType var1);

   TrueFalseType addNewDeployAsAWhole();

   void unsetDeployAsAWhole();

   WorkManagerType getWorkManager();

   boolean isSetWorkManager();

   void setWorkManager(WorkManagerType var1);

   WorkManagerType addNewWorkManager();

   void unsetWorkManager();

   ConnectorWorkManagerType getConnectorWorkManager();

   boolean isSetConnectorWorkManager();

   void setConnectorWorkManager(ConnectorWorkManagerType var1);

   ConnectorWorkManagerType addNewConnectorWorkManager();

   void unsetConnectorWorkManager();

   ResourceAdapterSecurityType getSecurity();

   boolean isSetSecurity();

   void setSecurity(ResourceAdapterSecurityType var1);

   ResourceAdapterSecurityType addNewSecurity();

   void unsetSecurity();

   ConfigPropertiesType getProperties();

   boolean isSetProperties();

   void setProperties(ConfigPropertiesType var1);

   ConfigPropertiesType addNewProperties();

   void unsetProperties();

   AdminObjectsType getAdminObjects();

   boolean isSetAdminObjects();

   void setAdminObjects(AdminObjectsType var1);

   AdminObjectsType addNewAdminObjects();

   void unsetAdminObjects();

   OutboundResourceAdapterType getOutboundResourceAdapter();

   boolean isSetOutboundResourceAdapter();

   void setOutboundResourceAdapter(OutboundResourceAdapterType var1);

   OutboundResourceAdapterType addNewOutboundResourceAdapter();

   void unsetOutboundResourceAdapter();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   java.lang.String getVersion();

   XmlString xgetVersion();

   boolean isSetVersion();

   void setVersion(java.lang.String var1);

   void xsetVersion(XmlString var1);

   void unsetVersion();

   public static final class Factory {
      public static WeblogicConnectorType newInstance() {
         return (WeblogicConnectorType)XmlBeans.getContextTypeLoader().newInstance(WeblogicConnectorType.type, (XmlOptions)null);
      }

      public static WeblogicConnectorType newInstance(XmlOptions options) {
         return (WeblogicConnectorType)XmlBeans.getContextTypeLoader().newInstance(WeblogicConnectorType.type, options);
      }

      public static WeblogicConnectorType parse(java.lang.String xmlAsString) throws XmlException {
         return (WeblogicConnectorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicConnectorType.type, (XmlOptions)null);
      }

      public static WeblogicConnectorType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicConnectorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicConnectorType.type, options);
      }

      public static WeblogicConnectorType parse(File file) throws XmlException, IOException {
         return (WeblogicConnectorType)XmlBeans.getContextTypeLoader().parse(file, WeblogicConnectorType.type, (XmlOptions)null);
      }

      public static WeblogicConnectorType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicConnectorType)XmlBeans.getContextTypeLoader().parse(file, WeblogicConnectorType.type, options);
      }

      public static WeblogicConnectorType parse(URL u) throws XmlException, IOException {
         return (WeblogicConnectorType)XmlBeans.getContextTypeLoader().parse(u, WeblogicConnectorType.type, (XmlOptions)null);
      }

      public static WeblogicConnectorType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicConnectorType)XmlBeans.getContextTypeLoader().parse(u, WeblogicConnectorType.type, options);
      }

      public static WeblogicConnectorType parse(InputStream is) throws XmlException, IOException {
         return (WeblogicConnectorType)XmlBeans.getContextTypeLoader().parse(is, WeblogicConnectorType.type, (XmlOptions)null);
      }

      public static WeblogicConnectorType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicConnectorType)XmlBeans.getContextTypeLoader().parse(is, WeblogicConnectorType.type, options);
      }

      public static WeblogicConnectorType parse(Reader r) throws XmlException, IOException {
         return (WeblogicConnectorType)XmlBeans.getContextTypeLoader().parse(r, WeblogicConnectorType.type, (XmlOptions)null);
      }

      public static WeblogicConnectorType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicConnectorType)XmlBeans.getContextTypeLoader().parse(r, WeblogicConnectorType.type, options);
      }

      public static WeblogicConnectorType parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicConnectorType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicConnectorType.type, (XmlOptions)null);
      }

      public static WeblogicConnectorType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicConnectorType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicConnectorType.type, options);
      }

      public static WeblogicConnectorType parse(Node node) throws XmlException {
         return (WeblogicConnectorType)XmlBeans.getContextTypeLoader().parse(node, WeblogicConnectorType.type, (XmlOptions)null);
      }

      public static WeblogicConnectorType parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicConnectorType)XmlBeans.getContextTypeLoader().parse(node, WeblogicConnectorType.type, options);
      }

      /** @deprecated */
      public static WeblogicConnectorType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicConnectorType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicConnectorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicConnectorType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicConnectorType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicConnectorType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicConnectorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicConnectorType.type, options);
      }

      private Factory() {
      }
   }
}
