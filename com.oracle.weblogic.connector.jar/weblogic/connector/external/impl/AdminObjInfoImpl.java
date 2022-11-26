package weblogic.connector.external.impl;

import java.util.Hashtable;
import weblogic.connector.external.AdminObjInfo;
import weblogic.connector.utils.PropertyNameNormalizer;
import weblogic.connector.utils.PropertyNameNormalizerFactory;
import weblogic.j2ee.descriptor.AdminObjectBean;
import weblogic.j2ee.descriptor.ConfigPropertyBean;
import weblogic.j2ee.descriptor.ConnectorBean;
import weblogic.j2ee.descriptor.wl.AdminObjectGroupBean;
import weblogic.j2ee.descriptor.wl.AdminObjectInstanceBean;
import weblogic.j2ee.descriptor.wl.AdminObjectsBean;
import weblogic.j2ee.descriptor.wl.ConfigPropertiesBean;
import weblogic.j2ee.descriptor.wl.WeblogicConnectorBean;

public class AdminObjInfoImpl implements AdminObjInfo {
   AdminObjectBean adminObjBean = null;
   AdminObjectInstanceBean adminInstanceBean = null;
   WeblogicConnectorBean wlConnBean = null;
   AdminObjectsBean adminObjsBean = null;
   AdminObjectGroupBean adminGroupBean = null;
   private PropertyNameNormalizer propertyNameNormalizer;

   public AdminObjInfoImpl(ConnectorBean connBean, AdminObjectBean adminObjBean, AdminObjectInstanceBean adminInstanceBean, WeblogicConnectorBean wlConnBean, AdminObjectGroupBean adminGroupBean) {
      this.adminObjBean = adminObjBean;
      this.wlConnBean = wlConnBean;
      this.adminObjsBean = wlConnBean.getAdminObjects();
      this.adminGroupBean = adminGroupBean;
      this.adminInstanceBean = adminInstanceBean;
      this.propertyNameNormalizer = PropertyNameNormalizerFactory.getPropertyNameNormalizer(connBean.getVersion());
   }

   public String getInterface() {
      return this.adminObjBean.getAdminObjectInterface();
   }

   public String getAdminObjClass() {
      return this.adminObjBean.getAdminObjectClass();
   }

   public Hashtable getConfigProps() {
      ConfigPropertyBean[] raConfigPropBeans = this.adminObjBean.getConfigProperties();
      ConfigPropertiesBean[] wlsConfigProperties = new ConfigPropertiesBean[]{this.adminObjsBean.getDefaultProperties(), this.adminGroupBean.getDefaultProperties(), this.adminInstanceBean.getProperties()};
      return DDLayerUtils.mergeConfigProperties(raConfigPropBeans, wlsConfigProperties, this.propertyNameNormalizer);
   }

   public String getJndiName() {
      return this.adminInstanceBean.getJNDIName();
   }

   public String getKey() {
      return this.getJndiName();
   }
}
