package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.AdminObjectsDocument;
import com.bea.connector.monitoring1Dot0.ConfigPropertiesType;
import com.bea.connector.monitoring1Dot0.ConnectorDocument;
import com.bea.connector.monitoring1Dot0.ConnectorWorkManagerType;
import com.bea.connector.monitoring1Dot0.InboundDocument;
import com.bea.connector.monitoring1Dot0.LicenseDocument;
import com.bea.connector.monitoring1Dot0.OutboundDocument;
import com.bea.connector.monitoring1Dot0.ResourceAdapterSecurityType;
import com.bea.connector.monitoring1Dot0.SecurityPermissionType;
import com.bea.connector.monitoring1Dot0.WorkManagerType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ConnectorDocumentImpl extends XmlComplexContentImpl implements ConnectorDocument {
   private static final long serialVersionUID = 1L;
   private static final QName CONNECTOR$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "connector");

   public ConnectorDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public ConnectorDocument.Connector getConnector() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectorDocument.Connector target = null;
         target = (ConnectorDocument.Connector)this.get_store().find_element_user(CONNECTOR$0, 0);
         return target == null ? null : target;
      }
   }

   public void setConnector(ConnectorDocument.Connector connector) {
      this.generatedSetterHelperImpl(connector, CONNECTOR$0, 0, (short)1);
   }

   public ConnectorDocument.Connector addNewConnector() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectorDocument.Connector target = null;
         target = (ConnectorDocument.Connector)this.get_store().add_element_user(CONNECTOR$0);
         return target;
      }
   }

   public static class ConnectorImpl extends XmlComplexContentImpl implements ConnectorDocument.Connector {
      private static final long serialVersionUID = 1L;
      private static final QName JNDINAME$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "jndi-name");
      private static final QName MODULENAME$2 = new QName("http://www.bea.com/connector/monitoring1dot0", "module-name");
      private static final QName NATIVELIBDIR$4 = new QName("http://www.bea.com/connector/monitoring1dot0", "native-libdir");
      private static final QName VERSION$6 = new QName("http://www.bea.com/connector/monitoring1dot0", "version");
      private static final QName LICENSE$8 = new QName("http://www.bea.com/connector/monitoring1dot0", "license");
      private static final QName DESCRIPTION$10 = new QName("http://www.bea.com/connector/monitoring1dot0", "description");
      private static final QName VENDORNAME$12 = new QName("http://www.bea.com/connector/monitoring1dot0", "vendor-name");
      private static final QName EISTYPE$14 = new QName("http://www.bea.com/connector/monitoring1dot0", "eis-type");
      private static final QName METADATACOMPLETE$16 = new QName("http://www.bea.com/connector/monitoring1dot0", "metadata-complete");
      private static final QName REQUIREDWORKCONTEXT$18 = new QName("http://www.bea.com/connector/monitoring1dot0", "required-work-context");
      private static final QName RESOURCEADAPTERVERSION$20 = new QName("http://www.bea.com/connector/monitoring1dot0", "resourceadapter-version");
      private static final QName RESOURCEADAPTERCLASS$22 = new QName("http://www.bea.com/connector/monitoring1dot0", "resourceadapter-class");
      private static final QName ENABLEGLOBALACCESSTOCLASSES$24 = new QName("http://www.bea.com/connector/monitoring1dot0", "enable-global-access-to-classes");
      private static final QName ENABLEACCESSOUTSIDEAPP$26 = new QName("http://www.bea.com/connector/monitoring1dot0", "enable-access-outside-app");
      private static final QName DEPLOYASAWHOLE$28 = new QName("http://www.bea.com/connector/monitoring1dot0", "deploy-as-a-whole");
      private static final QName LINKREF$30 = new QName("http://www.bea.com/connector/monitoring1dot0", "link-ref");
      private static final QName PROPERTIES$32 = new QName("http://www.bea.com/connector/monitoring1dot0", "properties");
      private static final QName WORKMANAGER$34 = new QName("http://www.bea.com/connector/monitoring1dot0", "work-manager");
      private static final QName CONNECTORWORKMANAGER$36 = new QName("http://www.bea.com/connector/monitoring1dot0", "connector-work-manager");
      private static final QName SECURITY$38 = new QName("http://www.bea.com/connector/monitoring1dot0", "security");
      private static final QName OUTBOUND$40 = new QName("http://www.bea.com/connector/monitoring1dot0", "outbound");
      private static final QName ADMINOBJECTS$42 = new QName("http://www.bea.com/connector/monitoring1dot0", "admin-objects");
      private static final QName INBOUND$44 = new QName("http://www.bea.com/connector/monitoring1dot0", "inbound");
      private static final QName SECURITYPERMISSION$46 = new QName("http://www.bea.com/connector/monitoring1dot0", "security-permission");

      public ConnectorImpl(SchemaType sType) {
         super(sType);
      }

      public String getJndiName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(JNDINAME$0, 0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetJndiName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(JNDINAME$0, 0);
            return target;
         }
      }

      public void setJndiName(String jndiName) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(JNDINAME$0, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(JNDINAME$0);
            }

            target.setStringValue(jndiName);
         }
      }

      public void xsetJndiName(XmlString jndiName) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(JNDINAME$0, 0);
            if (target == null) {
               target = (XmlString)this.get_store().add_element_user(JNDINAME$0);
            }

            target.set(jndiName);
         }
      }

      public String getModuleName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(MODULENAME$2, 0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetModuleName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(MODULENAME$2, 0);
            return target;
         }
      }

      public void setModuleName(String moduleName) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(MODULENAME$2, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(MODULENAME$2);
            }

            target.setStringValue(moduleName);
         }
      }

      public void xsetModuleName(XmlString moduleName) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(MODULENAME$2, 0);
            if (target == null) {
               target = (XmlString)this.get_store().add_element_user(MODULENAME$2);
            }

            target.set(moduleName);
         }
      }

      public String getNativeLibdir() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(NATIVELIBDIR$4, 0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetNativeLibdir() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(NATIVELIBDIR$4, 0);
            return target;
         }
      }

      public void setNativeLibdir(String nativeLibdir) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(NATIVELIBDIR$4, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(NATIVELIBDIR$4);
            }

            target.setStringValue(nativeLibdir);
         }
      }

      public void xsetNativeLibdir(XmlString nativeLibdir) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(NATIVELIBDIR$4, 0);
            if (target == null) {
               target = (XmlString)this.get_store().add_element_user(NATIVELIBDIR$4);
            }

            target.set(nativeLibdir);
         }
      }

      public String getVersion() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(VERSION$6, 0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetVersion() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(VERSION$6, 0);
            return target;
         }
      }

      public boolean isSetVersion() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(VERSION$6) != 0;
         }
      }

      public void setVersion(String version) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(VERSION$6, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(VERSION$6);
            }

            target.setStringValue(version);
         }
      }

      public void xsetVersion(XmlString version) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(VERSION$6, 0);
            if (target == null) {
               target = (XmlString)this.get_store().add_element_user(VERSION$6);
            }

            target.set(version);
         }
      }

      public void unsetVersion() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(VERSION$6, 0);
         }
      }

      public LicenseDocument.License getLicense() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            LicenseDocument.License target = null;
            target = (LicenseDocument.License)this.get_store().find_element_user(LICENSE$8, 0);
            return target == null ? null : target;
         }
      }

      public boolean isSetLicense() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(LICENSE$8) != 0;
         }
      }

      public void setLicense(LicenseDocument.License license) {
         this.generatedSetterHelperImpl(license, LICENSE$8, 0, (short)1);
      }

      public LicenseDocument.License addNewLicense() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            LicenseDocument.License target = null;
            target = (LicenseDocument.License)this.get_store().add_element_user(LICENSE$8);
            return target;
         }
      }

      public void unsetLicense() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(LICENSE$8, 0);
         }
      }

      public String[] getDescriptionArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users(DESCRIPTION$10, targetList);
            String[] result = new String[targetList.size()];
            int i = 0;

            for(int len = targetList.size(); i < len; ++i) {
               result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
            }

            return result;
         }
      }

      public String getDescriptionArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(DESCRIPTION$10, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target.getStringValue();
            }
         }
      }

      public XmlString[] xgetDescriptionArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users(DESCRIPTION$10, targetList);
            XmlString[] result = new XmlString[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public XmlString xgetDescriptionArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(DESCRIPTION$10, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfDescriptionArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(DESCRIPTION$10);
         }
      }

      public void setDescriptionArray(String[] descriptionArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(descriptionArray, DESCRIPTION$10);
         }
      }

      public void setDescriptionArray(int i, String description) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(DESCRIPTION$10, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.setStringValue(description);
            }
         }
      }

      public void xsetDescriptionArray(XmlString[] descriptionArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(descriptionArray, DESCRIPTION$10);
         }
      }

      public void xsetDescriptionArray(int i, XmlString description) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(DESCRIPTION$10, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.set(description);
            }
         }
      }

      public void insertDescription(int i, String description) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = (SimpleValue)this.get_store().insert_element_user(DESCRIPTION$10, i);
            target.setStringValue(description);
         }
      }

      public void addDescription(String description) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().add_element_user(DESCRIPTION$10);
            target.setStringValue(description);
         }
      }

      public XmlString insertNewDescription(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().insert_element_user(DESCRIPTION$10, i);
            return target;
         }
      }

      public XmlString addNewDescription() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().add_element_user(DESCRIPTION$10);
            return target;
         }
      }

      public void removeDescription(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(DESCRIPTION$10, i);
         }
      }

      public String getVendorName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(VENDORNAME$12, 0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetVendorName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(VENDORNAME$12, 0);
            return target;
         }
      }

      public boolean isSetVendorName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(VENDORNAME$12) != 0;
         }
      }

      public void setVendorName(String vendorName) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(VENDORNAME$12, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(VENDORNAME$12);
            }

            target.setStringValue(vendorName);
         }
      }

      public void xsetVendorName(XmlString vendorName) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(VENDORNAME$12, 0);
            if (target == null) {
               target = (XmlString)this.get_store().add_element_user(VENDORNAME$12);
            }

            target.set(vendorName);
         }
      }

      public void unsetVendorName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(VENDORNAME$12, 0);
         }
      }

      public String getEisType() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(EISTYPE$14, 0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetEisType() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(EISTYPE$14, 0);
            return target;
         }
      }

      public boolean isSetEisType() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(EISTYPE$14) != 0;
         }
      }

      public void setEisType(String eisType) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(EISTYPE$14, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(EISTYPE$14);
            }

            target.setStringValue(eisType);
         }
      }

      public void xsetEisType(XmlString eisType) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(EISTYPE$14, 0);
            if (target == null) {
               target = (XmlString)this.get_store().add_element_user(EISTYPE$14);
            }

            target.set(eisType);
         }
      }

      public void unsetEisType() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(EISTYPE$14, 0);
         }
      }

      public boolean getMetadataComplete() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(METADATACOMPLETE$16, 0);
            return target == null ? false : target.getBooleanValue();
         }
      }

      public XmlBoolean xgetMetadataComplete() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlBoolean target = null;
            target = (XmlBoolean)this.get_store().find_element_user(METADATACOMPLETE$16, 0);
            return target;
         }
      }

      public boolean isSetMetadataComplete() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(METADATACOMPLETE$16) != 0;
         }
      }

      public void setMetadataComplete(boolean metadataComplete) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(METADATACOMPLETE$16, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(METADATACOMPLETE$16);
            }

            target.setBooleanValue(metadataComplete);
         }
      }

      public void xsetMetadataComplete(XmlBoolean metadataComplete) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlBoolean target = null;
            target = (XmlBoolean)this.get_store().find_element_user(METADATACOMPLETE$16, 0);
            if (target == null) {
               target = (XmlBoolean)this.get_store().add_element_user(METADATACOMPLETE$16);
            }

            target.set(metadataComplete);
         }
      }

      public void unsetMetadataComplete() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(METADATACOMPLETE$16, 0);
         }
      }

      public String[] getRequiredWorkContextArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users(REQUIREDWORKCONTEXT$18, targetList);
            String[] result = new String[targetList.size()];
            int i = 0;

            for(int len = targetList.size(); i < len; ++i) {
               result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
            }

            return result;
         }
      }

      public String getRequiredWorkContextArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(REQUIREDWORKCONTEXT$18, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target.getStringValue();
            }
         }
      }

      public XmlString[] xgetRequiredWorkContextArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users(REQUIREDWORKCONTEXT$18, targetList);
            XmlString[] result = new XmlString[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public XmlString xgetRequiredWorkContextArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(REQUIREDWORKCONTEXT$18, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfRequiredWorkContextArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(REQUIREDWORKCONTEXT$18);
         }
      }

      public void setRequiredWorkContextArray(String[] requiredWorkContextArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(requiredWorkContextArray, REQUIREDWORKCONTEXT$18);
         }
      }

      public void setRequiredWorkContextArray(int i, String requiredWorkContext) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(REQUIREDWORKCONTEXT$18, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.setStringValue(requiredWorkContext);
            }
         }
      }

      public void xsetRequiredWorkContextArray(XmlString[] requiredWorkContextArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(requiredWorkContextArray, REQUIREDWORKCONTEXT$18);
         }
      }

      public void xsetRequiredWorkContextArray(int i, XmlString requiredWorkContext) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(REQUIREDWORKCONTEXT$18, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.set(requiredWorkContext);
            }
         }
      }

      public void insertRequiredWorkContext(int i, String requiredWorkContext) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = (SimpleValue)this.get_store().insert_element_user(REQUIREDWORKCONTEXT$18, i);
            target.setStringValue(requiredWorkContext);
         }
      }

      public void addRequiredWorkContext(String requiredWorkContext) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().add_element_user(REQUIREDWORKCONTEXT$18);
            target.setStringValue(requiredWorkContext);
         }
      }

      public XmlString insertNewRequiredWorkContext(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().insert_element_user(REQUIREDWORKCONTEXT$18, i);
            return target;
         }
      }

      public XmlString addNewRequiredWorkContext() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().add_element_user(REQUIREDWORKCONTEXT$18);
            return target;
         }
      }

      public void removeRequiredWorkContext(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(REQUIREDWORKCONTEXT$18, i);
         }
      }

      public String getResourceadapterVersion() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(RESOURCEADAPTERVERSION$20, 0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetResourceadapterVersion() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(RESOURCEADAPTERVERSION$20, 0);
            return target;
         }
      }

      public boolean isSetResourceadapterVersion() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(RESOURCEADAPTERVERSION$20) != 0;
         }
      }

      public void setResourceadapterVersion(String resourceadapterVersion) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(RESOURCEADAPTERVERSION$20, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(RESOURCEADAPTERVERSION$20);
            }

            target.setStringValue(resourceadapterVersion);
         }
      }

      public void xsetResourceadapterVersion(XmlString resourceadapterVersion) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(RESOURCEADAPTERVERSION$20, 0);
            if (target == null) {
               target = (XmlString)this.get_store().add_element_user(RESOURCEADAPTERVERSION$20);
            }

            target.set(resourceadapterVersion);
         }
      }

      public void unsetResourceadapterVersion() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(RESOURCEADAPTERVERSION$20, 0);
         }
      }

      public String getResourceadapterClass() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(RESOURCEADAPTERCLASS$22, 0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetResourceadapterClass() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(RESOURCEADAPTERCLASS$22, 0);
            return target;
         }
      }

      public boolean isSetResourceadapterClass() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(RESOURCEADAPTERCLASS$22) != 0;
         }
      }

      public void setResourceadapterClass(String resourceadapterClass) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(RESOURCEADAPTERCLASS$22, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(RESOURCEADAPTERCLASS$22);
            }

            target.setStringValue(resourceadapterClass);
         }
      }

      public void xsetResourceadapterClass(XmlString resourceadapterClass) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(RESOURCEADAPTERCLASS$22, 0);
            if (target == null) {
               target = (XmlString)this.get_store().add_element_user(RESOURCEADAPTERCLASS$22);
            }

            target.set(resourceadapterClass);
         }
      }

      public void unsetResourceadapterClass() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(RESOURCEADAPTERCLASS$22, 0);
         }
      }

      public boolean getEnableGlobalAccessToClasses() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(ENABLEGLOBALACCESSTOCLASSES$24, 0);
            return target == null ? false : target.getBooleanValue();
         }
      }

      public XmlBoolean xgetEnableGlobalAccessToClasses() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlBoolean target = null;
            target = (XmlBoolean)this.get_store().find_element_user(ENABLEGLOBALACCESSTOCLASSES$24, 0);
            return target;
         }
      }

      public boolean isSetEnableGlobalAccessToClasses() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(ENABLEGLOBALACCESSTOCLASSES$24) != 0;
         }
      }

      public void setEnableGlobalAccessToClasses(boolean enableGlobalAccessToClasses) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(ENABLEGLOBALACCESSTOCLASSES$24, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(ENABLEGLOBALACCESSTOCLASSES$24);
            }

            target.setBooleanValue(enableGlobalAccessToClasses);
         }
      }

      public void xsetEnableGlobalAccessToClasses(XmlBoolean enableGlobalAccessToClasses) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlBoolean target = null;
            target = (XmlBoolean)this.get_store().find_element_user(ENABLEGLOBALACCESSTOCLASSES$24, 0);
            if (target == null) {
               target = (XmlBoolean)this.get_store().add_element_user(ENABLEGLOBALACCESSTOCLASSES$24);
            }

            target.set(enableGlobalAccessToClasses);
         }
      }

      public void unsetEnableGlobalAccessToClasses() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(ENABLEGLOBALACCESSTOCLASSES$24, 0);
         }
      }

      public boolean getEnableAccessOutsideApp() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(ENABLEACCESSOUTSIDEAPP$26, 0);
            return target == null ? false : target.getBooleanValue();
         }
      }

      public XmlBoolean xgetEnableAccessOutsideApp() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlBoolean target = null;
            target = (XmlBoolean)this.get_store().find_element_user(ENABLEACCESSOUTSIDEAPP$26, 0);
            return target;
         }
      }

      public void setEnableAccessOutsideApp(boolean enableAccessOutsideApp) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(ENABLEACCESSOUTSIDEAPP$26, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(ENABLEACCESSOUTSIDEAPP$26);
            }

            target.setBooleanValue(enableAccessOutsideApp);
         }
      }

      public void xsetEnableAccessOutsideApp(XmlBoolean enableAccessOutsideApp) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlBoolean target = null;
            target = (XmlBoolean)this.get_store().find_element_user(ENABLEACCESSOUTSIDEAPP$26, 0);
            if (target == null) {
               target = (XmlBoolean)this.get_store().add_element_user(ENABLEACCESSOUTSIDEAPP$26);
            }

            target.set(enableAccessOutsideApp);
         }
      }

      public boolean getDeployAsAWhole() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(DEPLOYASAWHOLE$28, 0);
            return target == null ? false : target.getBooleanValue();
         }
      }

      public XmlBoolean xgetDeployAsAWhole() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlBoolean target = null;
            target = (XmlBoolean)this.get_store().find_element_user(DEPLOYASAWHOLE$28, 0);
            return target;
         }
      }

      public boolean isSetDeployAsAWhole() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(DEPLOYASAWHOLE$28) != 0;
         }
      }

      public void setDeployAsAWhole(boolean deployAsAWhole) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(DEPLOYASAWHOLE$28, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(DEPLOYASAWHOLE$28);
            }

            target.setBooleanValue(deployAsAWhole);
         }
      }

      public void xsetDeployAsAWhole(XmlBoolean deployAsAWhole) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlBoolean target = null;
            target = (XmlBoolean)this.get_store().find_element_user(DEPLOYASAWHOLE$28, 0);
            if (target == null) {
               target = (XmlBoolean)this.get_store().add_element_user(DEPLOYASAWHOLE$28);
            }

            target.set(deployAsAWhole);
         }
      }

      public void unsetDeployAsAWhole() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(DEPLOYASAWHOLE$28, 0);
         }
      }

      public String getLinkRef() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(LINKREF$30, 0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetLinkRef() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(LINKREF$30, 0);
            return target;
         }
      }

      public boolean isSetLinkRef() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(LINKREF$30) != 0;
         }
      }

      public void setLinkRef(String linkRef) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(LINKREF$30, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(LINKREF$30);
            }

            target.setStringValue(linkRef);
         }
      }

      public void xsetLinkRef(XmlString linkRef) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(LINKREF$30, 0);
            if (target == null) {
               target = (XmlString)this.get_store().add_element_user(LINKREF$30);
            }

            target.set(linkRef);
         }
      }

      public void unsetLinkRef() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(LINKREF$30, 0);
         }
      }

      public ConfigPropertiesType getProperties() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ConfigPropertiesType target = null;
            target = (ConfigPropertiesType)this.get_store().find_element_user(PROPERTIES$32, 0);
            return target == null ? null : target;
         }
      }

      public void setProperties(ConfigPropertiesType properties) {
         this.generatedSetterHelperImpl(properties, PROPERTIES$32, 0, (short)1);
      }

      public ConfigPropertiesType addNewProperties() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ConfigPropertiesType target = null;
            target = (ConfigPropertiesType)this.get_store().add_element_user(PROPERTIES$32);
            return target;
         }
      }

      public WorkManagerType getWorkManager() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            WorkManagerType target = null;
            target = (WorkManagerType)this.get_store().find_element_user(WORKMANAGER$34, 0);
            return target == null ? null : target;
         }
      }

      public boolean isSetWorkManager() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(WORKMANAGER$34) != 0;
         }
      }

      public void setWorkManager(WorkManagerType workManager) {
         this.generatedSetterHelperImpl(workManager, WORKMANAGER$34, 0, (short)1);
      }

      public WorkManagerType addNewWorkManager() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            WorkManagerType target = null;
            target = (WorkManagerType)this.get_store().add_element_user(WORKMANAGER$34);
            return target;
         }
      }

      public void unsetWorkManager() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(WORKMANAGER$34, 0);
         }
      }

      public ConnectorWorkManagerType getConnectorWorkManager() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ConnectorWorkManagerType target = null;
            target = (ConnectorWorkManagerType)this.get_store().find_element_user(CONNECTORWORKMANAGER$36, 0);
            return target == null ? null : target;
         }
      }

      public boolean isSetConnectorWorkManager() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(CONNECTORWORKMANAGER$36) != 0;
         }
      }

      public void setConnectorWorkManager(ConnectorWorkManagerType connectorWorkManager) {
         this.generatedSetterHelperImpl(connectorWorkManager, CONNECTORWORKMANAGER$36, 0, (short)1);
      }

      public ConnectorWorkManagerType addNewConnectorWorkManager() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ConnectorWorkManagerType target = null;
            target = (ConnectorWorkManagerType)this.get_store().add_element_user(CONNECTORWORKMANAGER$36);
            return target;
         }
      }

      public void unsetConnectorWorkManager() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(CONNECTORWORKMANAGER$36, 0);
         }
      }

      public ResourceAdapterSecurityType getSecurity() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ResourceAdapterSecurityType target = null;
            target = (ResourceAdapterSecurityType)this.get_store().find_element_user(SECURITY$38, 0);
            return target == null ? null : target;
         }
      }

      public boolean isSetSecurity() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(SECURITY$38) != 0;
         }
      }

      public void setSecurity(ResourceAdapterSecurityType security) {
         this.generatedSetterHelperImpl(security, SECURITY$38, 0, (short)1);
      }

      public ResourceAdapterSecurityType addNewSecurity() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ResourceAdapterSecurityType target = null;
            target = (ResourceAdapterSecurityType)this.get_store().add_element_user(SECURITY$38);
            return target;
         }
      }

      public void unsetSecurity() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(SECURITY$38, 0);
         }
      }

      public OutboundDocument.Outbound getOutbound() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            OutboundDocument.Outbound target = null;
            target = (OutboundDocument.Outbound)this.get_store().find_element_user(OUTBOUND$40, 0);
            return target == null ? null : target;
         }
      }

      public boolean isSetOutbound() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(OUTBOUND$40) != 0;
         }
      }

      public void setOutbound(OutboundDocument.Outbound outbound) {
         this.generatedSetterHelperImpl(outbound, OUTBOUND$40, 0, (short)1);
      }

      public OutboundDocument.Outbound addNewOutbound() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            OutboundDocument.Outbound target = null;
            target = (OutboundDocument.Outbound)this.get_store().add_element_user(OUTBOUND$40);
            return target;
         }
      }

      public void unsetOutbound() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(OUTBOUND$40, 0);
         }
      }

      public AdminObjectsDocument.AdminObjects getAdminObjects() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            AdminObjectsDocument.AdminObjects target = null;
            target = (AdminObjectsDocument.AdminObjects)this.get_store().find_element_user(ADMINOBJECTS$42, 0);
            return target == null ? null : target;
         }
      }

      public boolean isSetAdminObjects() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(ADMINOBJECTS$42) != 0;
         }
      }

      public void setAdminObjects(AdminObjectsDocument.AdminObjects adminObjects) {
         this.generatedSetterHelperImpl(adminObjects, ADMINOBJECTS$42, 0, (short)1);
      }

      public AdminObjectsDocument.AdminObjects addNewAdminObjects() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            AdminObjectsDocument.AdminObjects target = null;
            target = (AdminObjectsDocument.AdminObjects)this.get_store().add_element_user(ADMINOBJECTS$42);
            return target;
         }
      }

      public void unsetAdminObjects() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(ADMINOBJECTS$42, 0);
         }
      }

      public InboundDocument.Inbound getInbound() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            InboundDocument.Inbound target = null;
            target = (InboundDocument.Inbound)this.get_store().find_element_user(INBOUND$44, 0);
            return target == null ? null : target;
         }
      }

      public boolean isSetInbound() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(INBOUND$44) != 0;
         }
      }

      public void setInbound(InboundDocument.Inbound inbound) {
         this.generatedSetterHelperImpl(inbound, INBOUND$44, 0, (short)1);
      }

      public InboundDocument.Inbound addNewInbound() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            InboundDocument.Inbound target = null;
            target = (InboundDocument.Inbound)this.get_store().add_element_user(INBOUND$44);
            return target;
         }
      }

      public void unsetInbound() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(INBOUND$44, 0);
         }
      }

      public SecurityPermissionType[] getSecurityPermissionArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users(SECURITYPERMISSION$46, targetList);
            SecurityPermissionType[] result = new SecurityPermissionType[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public SecurityPermissionType getSecurityPermissionArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SecurityPermissionType target = null;
            target = (SecurityPermissionType)this.get_store().find_element_user(SECURITYPERMISSION$46, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfSecurityPermissionArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(SECURITYPERMISSION$46);
         }
      }

      public void setSecurityPermissionArray(SecurityPermissionType[] securityPermissionArray) {
         this.check_orphaned();
         this.arraySetterHelper(securityPermissionArray, SECURITYPERMISSION$46);
      }

      public void setSecurityPermissionArray(int i, SecurityPermissionType securityPermission) {
         this.generatedSetterHelperImpl(securityPermission, SECURITYPERMISSION$46, i, (short)2);
      }

      public SecurityPermissionType insertNewSecurityPermission(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SecurityPermissionType target = null;
            target = (SecurityPermissionType)this.get_store().insert_element_user(SECURITYPERMISSION$46, i);
            return target;
         }
      }

      public SecurityPermissionType addNewSecurityPermission() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SecurityPermissionType target = null;
            target = (SecurityPermissionType)this.get_store().add_element_user(SECURITYPERMISSION$46);
            return target;
         }
      }

      public void removeSecurityPermission(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(SECURITYPERMISSION$46, i);
         }
      }
   }
}
