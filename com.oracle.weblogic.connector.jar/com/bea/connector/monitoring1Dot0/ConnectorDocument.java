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

public interface ConnectorDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConnectorDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("connector5193doctype");

   Connector getConnector();

   void setConnector(Connector var1);

   Connector addNewConnector();

   public static final class Factory {
      public static ConnectorDocument newInstance() {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().newInstance(ConnectorDocument.type, (XmlOptions)null);
      }

      public static ConnectorDocument newInstance(XmlOptions options) {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().newInstance(ConnectorDocument.type, options);
      }

      public static ConnectorDocument parse(String xmlAsString) throws XmlException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectorDocument.type, (XmlOptions)null);
      }

      public static ConnectorDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectorDocument.type, options);
      }

      public static ConnectorDocument parse(File file) throws XmlException, IOException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(file, ConnectorDocument.type, (XmlOptions)null);
      }

      public static ConnectorDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(file, ConnectorDocument.type, options);
      }

      public static ConnectorDocument parse(URL u) throws XmlException, IOException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(u, ConnectorDocument.type, (XmlOptions)null);
      }

      public static ConnectorDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(u, ConnectorDocument.type, options);
      }

      public static ConnectorDocument parse(InputStream is) throws XmlException, IOException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(is, ConnectorDocument.type, (XmlOptions)null);
      }

      public static ConnectorDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(is, ConnectorDocument.type, options);
      }

      public static ConnectorDocument parse(Reader r) throws XmlException, IOException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(r, ConnectorDocument.type, (XmlOptions)null);
      }

      public static ConnectorDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(r, ConnectorDocument.type, options);
      }

      public static ConnectorDocument parse(XMLStreamReader sr) throws XmlException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(sr, ConnectorDocument.type, (XmlOptions)null);
      }

      public static ConnectorDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(sr, ConnectorDocument.type, options);
      }

      public static ConnectorDocument parse(Node node) throws XmlException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(node, ConnectorDocument.type, (XmlOptions)null);
      }

      public static ConnectorDocument parse(Node node, XmlOptions options) throws XmlException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(node, ConnectorDocument.type, options);
      }

      /** @deprecated */
      public static ConnectorDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(xis, ConnectorDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConnectorDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConnectorDocument)XmlBeans.getContextTypeLoader().parse(xis, ConnectorDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectorDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectorDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface Connector extends XmlObject {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Connector.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("connectorb0faelemtype");

      String getJndiName();

      XmlString xgetJndiName();

      void setJndiName(String var1);

      void xsetJndiName(XmlString var1);

      String getModuleName();

      XmlString xgetModuleName();

      void setModuleName(String var1);

      void xsetModuleName(XmlString var1);

      String getNativeLibdir();

      XmlString xgetNativeLibdir();

      void setNativeLibdir(String var1);

      void xsetNativeLibdir(XmlString var1);

      String getVersion();

      XmlString xgetVersion();

      boolean isSetVersion();

      void setVersion(String var1);

      void xsetVersion(XmlString var1);

      void unsetVersion();

      LicenseDocument.License getLicense();

      boolean isSetLicense();

      void setLicense(LicenseDocument.License var1);

      LicenseDocument.License addNewLicense();

      void unsetLicense();

      String[] getDescriptionArray();

      String getDescriptionArray(int var1);

      XmlString[] xgetDescriptionArray();

      XmlString xgetDescriptionArray(int var1);

      int sizeOfDescriptionArray();

      void setDescriptionArray(String[] var1);

      void setDescriptionArray(int var1, String var2);

      void xsetDescriptionArray(XmlString[] var1);

      void xsetDescriptionArray(int var1, XmlString var2);

      void insertDescription(int var1, String var2);

      void addDescription(String var1);

      XmlString insertNewDescription(int var1);

      XmlString addNewDescription();

      void removeDescription(int var1);

      String getVendorName();

      XmlString xgetVendorName();

      boolean isSetVendorName();

      void setVendorName(String var1);

      void xsetVendorName(XmlString var1);

      void unsetVendorName();

      String getEisType();

      XmlString xgetEisType();

      boolean isSetEisType();

      void setEisType(String var1);

      void xsetEisType(XmlString var1);

      void unsetEisType();

      boolean getMetadataComplete();

      XmlBoolean xgetMetadataComplete();

      boolean isSetMetadataComplete();

      void setMetadataComplete(boolean var1);

      void xsetMetadataComplete(XmlBoolean var1);

      void unsetMetadataComplete();

      String[] getRequiredWorkContextArray();

      String getRequiredWorkContextArray(int var1);

      XmlString[] xgetRequiredWorkContextArray();

      XmlString xgetRequiredWorkContextArray(int var1);

      int sizeOfRequiredWorkContextArray();

      void setRequiredWorkContextArray(String[] var1);

      void setRequiredWorkContextArray(int var1, String var2);

      void xsetRequiredWorkContextArray(XmlString[] var1);

      void xsetRequiredWorkContextArray(int var1, XmlString var2);

      void insertRequiredWorkContext(int var1, String var2);

      void addRequiredWorkContext(String var1);

      XmlString insertNewRequiredWorkContext(int var1);

      XmlString addNewRequiredWorkContext();

      void removeRequiredWorkContext(int var1);

      String getResourceadapterVersion();

      XmlString xgetResourceadapterVersion();

      boolean isSetResourceadapterVersion();

      void setResourceadapterVersion(String var1);

      void xsetResourceadapterVersion(XmlString var1);

      void unsetResourceadapterVersion();

      String getResourceadapterClass();

      XmlString xgetResourceadapterClass();

      boolean isSetResourceadapterClass();

      void setResourceadapterClass(String var1);

      void xsetResourceadapterClass(XmlString var1);

      void unsetResourceadapterClass();

      boolean getEnableGlobalAccessToClasses();

      XmlBoolean xgetEnableGlobalAccessToClasses();

      boolean isSetEnableGlobalAccessToClasses();

      void setEnableGlobalAccessToClasses(boolean var1);

      void xsetEnableGlobalAccessToClasses(XmlBoolean var1);

      void unsetEnableGlobalAccessToClasses();

      boolean getEnableAccessOutsideApp();

      XmlBoolean xgetEnableAccessOutsideApp();

      void setEnableAccessOutsideApp(boolean var1);

      void xsetEnableAccessOutsideApp(XmlBoolean var1);

      boolean getDeployAsAWhole();

      XmlBoolean xgetDeployAsAWhole();

      boolean isSetDeployAsAWhole();

      void setDeployAsAWhole(boolean var1);

      void xsetDeployAsAWhole(XmlBoolean var1);

      void unsetDeployAsAWhole();

      String getLinkRef();

      XmlString xgetLinkRef();

      boolean isSetLinkRef();

      void setLinkRef(String var1);

      void xsetLinkRef(XmlString var1);

      void unsetLinkRef();

      ConfigPropertiesType getProperties();

      void setProperties(ConfigPropertiesType var1);

      ConfigPropertiesType addNewProperties();

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

      OutboundDocument.Outbound getOutbound();

      boolean isSetOutbound();

      void setOutbound(OutboundDocument.Outbound var1);

      OutboundDocument.Outbound addNewOutbound();

      void unsetOutbound();

      AdminObjectsDocument.AdminObjects getAdminObjects();

      boolean isSetAdminObjects();

      void setAdminObjects(AdminObjectsDocument.AdminObjects var1);

      AdminObjectsDocument.AdminObjects addNewAdminObjects();

      void unsetAdminObjects();

      InboundDocument.Inbound getInbound();

      boolean isSetInbound();

      void setInbound(InboundDocument.Inbound var1);

      InboundDocument.Inbound addNewInbound();

      void unsetInbound();

      SecurityPermissionType[] getSecurityPermissionArray();

      SecurityPermissionType getSecurityPermissionArray(int var1);

      int sizeOfSecurityPermissionArray();

      void setSecurityPermissionArray(SecurityPermissionType[] var1);

      void setSecurityPermissionArray(int var1, SecurityPermissionType var2);

      SecurityPermissionType insertNewSecurityPermission(int var1);

      SecurityPermissionType addNewSecurityPermission();

      void removeSecurityPermission(int var1);

      public static final class Factory {
         public static Connector newInstance() {
            return (Connector)XmlBeans.getContextTypeLoader().newInstance(ConnectorDocument.Connector.type, (XmlOptions)null);
         }

         public static Connector newInstance(XmlOptions options) {
            return (Connector)XmlBeans.getContextTypeLoader().newInstance(ConnectorDocument.Connector.type, options);
         }

         private Factory() {
         }
      }
   }
}
