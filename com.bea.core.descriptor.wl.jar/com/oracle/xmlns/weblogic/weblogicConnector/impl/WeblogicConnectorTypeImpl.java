package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicConnector.AdminObjectsType;
import com.oracle.xmlns.weblogic.weblogicConnector.ConfigPropertiesType;
import com.oracle.xmlns.weblogic.weblogicConnector.ConnectorWorkManagerType;
import com.oracle.xmlns.weblogic.weblogicConnector.OutboundResourceAdapterType;
import com.oracle.xmlns.weblogic.weblogicConnector.ResourceAdapterSecurityType;
import com.oracle.xmlns.weblogic.weblogicConnector.TrueFalseType;
import com.oracle.xmlns.weblogic.weblogicConnector.WeblogicConnectorType;
import com.oracle.xmlns.weblogic.weblogicConnector.WorkManagerType;
import com.sun.java.xml.ns.javaee.JndiNameType;
import com.sun.java.xml.ns.javaee.String;
import javax.xml.namespace.QName;

public class WeblogicConnectorTypeImpl extends XmlComplexContentImpl implements WeblogicConnectorType {
   private static final long serialVersionUID = 1L;
   private static final QName NATIVELIBDIR$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "native-libdir");
   private static final QName JNDINAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "jndi-name");
   private static final QName ENABLEACCESSOUTSIDEAPP$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "enable-access-outside-app");
   private static final QName ENABLEGLOBALACCESSTOCLASSES$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "enable-global-access-to-classes");
   private static final QName DEPLOYASAWHOLE$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "deploy-as-a-whole");
   private static final QName WORKMANAGER$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "work-manager");
   private static final QName CONNECTORWORKMANAGER$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "connector-work-manager");
   private static final QName SECURITY$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "security");
   private static final QName PROPERTIES$16 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "properties");
   private static final QName ADMINOBJECTS$18 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "admin-objects");
   private static final QName OUTBOUNDRESOURCEADAPTER$20 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "outbound-resource-adapter");
   private static final QName ID$22 = new QName("", "id");
   private static final QName VERSION$24 = new QName("", "version");

   public WeblogicConnectorTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getNativeLibdir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(NATIVELIBDIR$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetNativeLibdir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NATIVELIBDIR$0) != 0;
      }
   }

   public void setNativeLibdir(String nativeLibdir) {
      this.generatedSetterHelperImpl(nativeLibdir, NATIVELIBDIR$0, 0, (short)1);
   }

   public String addNewNativeLibdir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(NATIVELIBDIR$0);
         return target;
      }
   }

   public void unsetNativeLibdir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NATIVELIBDIR$0, 0);
      }
   }

   public JndiNameType getJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiNameType target = null;
         target = (JndiNameType)this.get_store().find_element_user(JNDINAME$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JNDINAME$2) != 0;
      }
   }

   public void setJndiName(JndiNameType jndiName) {
      this.generatedSetterHelperImpl(jndiName, JNDINAME$2, 0, (short)1);
   }

   public JndiNameType addNewJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiNameType target = null;
         target = (JndiNameType)this.get_store().add_element_user(JNDINAME$2);
         return target;
      }
   }

   public void unsetJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JNDINAME$2, 0);
      }
   }

   public TrueFalseType getEnableAccessOutsideApp() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ENABLEACCESSOUTSIDEAPP$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEnableAccessOutsideApp() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENABLEACCESSOUTSIDEAPP$4) != 0;
      }
   }

   public void setEnableAccessOutsideApp(TrueFalseType enableAccessOutsideApp) {
      this.generatedSetterHelperImpl(enableAccessOutsideApp, ENABLEACCESSOUTSIDEAPP$4, 0, (short)1);
   }

   public TrueFalseType addNewEnableAccessOutsideApp() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ENABLEACCESSOUTSIDEAPP$4);
         return target;
      }
   }

   public void unsetEnableAccessOutsideApp() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENABLEACCESSOUTSIDEAPP$4, 0);
      }
   }

   public TrueFalseType getEnableGlobalAccessToClasses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ENABLEGLOBALACCESSTOCLASSES$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEnableGlobalAccessToClasses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENABLEGLOBALACCESSTOCLASSES$6) != 0;
      }
   }

   public void setEnableGlobalAccessToClasses(TrueFalseType enableGlobalAccessToClasses) {
      this.generatedSetterHelperImpl(enableGlobalAccessToClasses, ENABLEGLOBALACCESSTOCLASSES$6, 0, (short)1);
   }

   public TrueFalseType addNewEnableGlobalAccessToClasses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ENABLEGLOBALACCESSTOCLASSES$6);
         return target;
      }
   }

   public void unsetEnableGlobalAccessToClasses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENABLEGLOBALACCESSTOCLASSES$6, 0);
      }
   }

   public TrueFalseType getDeployAsAWhole() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(DEPLOYASAWHOLE$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDeployAsAWhole() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEPLOYASAWHOLE$8) != 0;
      }
   }

   public void setDeployAsAWhole(TrueFalseType deployAsAWhole) {
      this.generatedSetterHelperImpl(deployAsAWhole, DEPLOYASAWHOLE$8, 0, (short)1);
   }

   public TrueFalseType addNewDeployAsAWhole() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(DEPLOYASAWHOLE$8);
         return target;
      }
   }

   public void unsetDeployAsAWhole() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEPLOYASAWHOLE$8, 0);
      }
   }

   public WorkManagerType getWorkManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WorkManagerType target = null;
         target = (WorkManagerType)this.get_store().find_element_user(WORKMANAGER$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetWorkManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WORKMANAGER$10) != 0;
      }
   }

   public void setWorkManager(WorkManagerType workManager) {
      this.generatedSetterHelperImpl(workManager, WORKMANAGER$10, 0, (short)1);
   }

   public WorkManagerType addNewWorkManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WorkManagerType target = null;
         target = (WorkManagerType)this.get_store().add_element_user(WORKMANAGER$10);
         return target;
      }
   }

   public void unsetWorkManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WORKMANAGER$10, 0);
      }
   }

   public ConnectorWorkManagerType getConnectorWorkManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectorWorkManagerType target = null;
         target = (ConnectorWorkManagerType)this.get_store().find_element_user(CONNECTORWORKMANAGER$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetConnectorWorkManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTORWORKMANAGER$12) != 0;
      }
   }

   public void setConnectorWorkManager(ConnectorWorkManagerType connectorWorkManager) {
      this.generatedSetterHelperImpl(connectorWorkManager, CONNECTORWORKMANAGER$12, 0, (short)1);
   }

   public ConnectorWorkManagerType addNewConnectorWorkManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectorWorkManagerType target = null;
         target = (ConnectorWorkManagerType)this.get_store().add_element_user(CONNECTORWORKMANAGER$12);
         return target;
      }
   }

   public void unsetConnectorWorkManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTORWORKMANAGER$12, 0);
      }
   }

   public ResourceAdapterSecurityType getSecurity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceAdapterSecurityType target = null;
         target = (ResourceAdapterSecurityType)this.get_store().find_element_user(SECURITY$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSecurity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SECURITY$14) != 0;
      }
   }

   public void setSecurity(ResourceAdapterSecurityType security) {
      this.generatedSetterHelperImpl(security, SECURITY$14, 0, (short)1);
   }

   public ResourceAdapterSecurityType addNewSecurity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceAdapterSecurityType target = null;
         target = (ResourceAdapterSecurityType)this.get_store().add_element_user(SECURITY$14);
         return target;
      }
   }

   public void unsetSecurity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECURITY$14, 0);
      }
   }

   public ConfigPropertiesType getProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertiesType target = null;
         target = (ConfigPropertiesType)this.get_store().find_element_user(PROPERTIES$16, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PROPERTIES$16) != 0;
      }
   }

   public void setProperties(ConfigPropertiesType properties) {
      this.generatedSetterHelperImpl(properties, PROPERTIES$16, 0, (short)1);
   }

   public ConfigPropertiesType addNewProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertiesType target = null;
         target = (ConfigPropertiesType)this.get_store().add_element_user(PROPERTIES$16);
         return target;
      }
   }

   public void unsetProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROPERTIES$16, 0);
      }
   }

   public AdminObjectsType getAdminObjects() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdminObjectsType target = null;
         target = (AdminObjectsType)this.get_store().find_element_user(ADMINOBJECTS$18, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAdminObjects() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ADMINOBJECTS$18) != 0;
      }
   }

   public void setAdminObjects(AdminObjectsType adminObjects) {
      this.generatedSetterHelperImpl(adminObjects, ADMINOBJECTS$18, 0, (short)1);
   }

   public AdminObjectsType addNewAdminObjects() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdminObjectsType target = null;
         target = (AdminObjectsType)this.get_store().add_element_user(ADMINOBJECTS$18);
         return target;
      }
   }

   public void unsetAdminObjects() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ADMINOBJECTS$18, 0);
      }
   }

   public OutboundResourceAdapterType getOutboundResourceAdapter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OutboundResourceAdapterType target = null;
         target = (OutboundResourceAdapterType)this.get_store().find_element_user(OUTBOUNDRESOURCEADAPTER$20, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetOutboundResourceAdapter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(OUTBOUNDRESOURCEADAPTER$20) != 0;
      }
   }

   public void setOutboundResourceAdapter(OutboundResourceAdapterType outboundResourceAdapter) {
      this.generatedSetterHelperImpl(outboundResourceAdapter, OUTBOUNDRESOURCEADAPTER$20, 0, (short)1);
   }

   public OutboundResourceAdapterType addNewOutboundResourceAdapter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OutboundResourceAdapterType target = null;
         target = (OutboundResourceAdapterType)this.get_store().add_element_user(OUTBOUNDRESOURCEADAPTER$20);
         return target;
      }
   }

   public void unsetOutboundResourceAdapter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(OUTBOUNDRESOURCEADAPTER$20, 0);
      }
   }

   public java.lang.String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$22);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$22);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$22) != null;
      }
   }

   public void setId(java.lang.String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$22);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$22);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$22);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$22);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$22);
      }
   }

   public java.lang.String getVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$24);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VERSION$24);
         return target;
      }
   }

   public boolean isSetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(VERSION$24) != null;
      }
   }

   public void setVersion(java.lang.String version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$24);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(VERSION$24);
         }

         target.setStringValue(version);
      }
   }

   public void xsetVersion(XmlString version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VERSION$24);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(VERSION$24);
         }

         target.set(version);
      }
   }

   public void unsetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(VERSION$24);
      }
   }
}
