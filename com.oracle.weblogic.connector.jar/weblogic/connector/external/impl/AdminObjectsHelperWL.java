package weblogic.connector.external.impl;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import weblogic.connector.common.Debug;
import weblogic.connector.external.AdminObjInfo;
import weblogic.connector.utils.ArrayUtils;
import weblogic.connector.utils.ObjectPair;
import weblogic.connector.utils.PropertyNameNormalizer;
import weblogic.connector.utils.PropertyNameNormalizerFactory;
import weblogic.connector.utils.StringUtils;
import weblogic.descriptor.DescriptorManager;
import weblogic.j2ee.descriptor.AdminObjectBean;
import weblogic.j2ee.descriptor.AdministeredObjectBean;
import weblogic.j2ee.descriptor.ConnectorBean;
import weblogic.j2ee.descriptor.JavaEEPropertyBean;
import weblogic.j2ee.descriptor.wl.AdminObjectGroupBean;
import weblogic.j2ee.descriptor.wl.AdminObjectInstanceBean;
import weblogic.j2ee.descriptor.wl.AdminObjectsBean;
import weblogic.j2ee.descriptor.wl.ConfigPropertiesBean;
import weblogic.j2ee.descriptor.wl.ConfigPropertyBean;
import weblogic.j2ee.descriptor.wl.WeblogicConnectorBean;

public class AdminObjectsHelperWL {
   private final ConnectorBean connBean;
   private final WeblogicConnectorBean wlConnBean;
   private static SoftReference dmRef = null;
   private final AdminObjectsHelper helper;
   Map jndiToAdminObjects = new HashMap();
   Map keyToGroup = null;
   private PropertyNameNormalizer propertyNameNormalizer;

   public AdminObjectsHelper getHelper() {
      return this.helper;
   }

   public AdminObjectsHelperWL(ConnectorBean connBean, WeblogicConnectorBean wlConnBean) {
      this.connBean = connBean;
      this.wlConnBean = wlConnBean;
      if (connBean != null) {
         this.helper = new AdminObjectsHelper(connBean);
         this.propertyNameNormalizer = PropertyNameNormalizerFactory.getPropertyNameNormalizer(connBean.getVersion());
         this.initAdminObjectInstance();
      } else {
         this.helper = null;
         this.keyToGroup = Collections.emptyMap();
      }

   }

   private static DescriptorManager getDescriptorManager() {
      if (dmRef == null || dmRef.get() == null) {
         dmRef = new SoftReference(new DescriptorManager());
      }

      return (DescriptorManager)dmRef.get();
   }

   public AdminObjectGroupBean getOrCreatAdminObjectGroupBean(String className, String interfaceName) {
      AdminObjectsBean wlAdminBeans = this.wlConnBean.getAdminObjects();
      AdminObjectGroupBean[] adminGroupBeans = wlAdminBeans.getAdminObjectGroups();
      AdminObjectGroupBean[] var5 = adminGroupBeans;
      int var6 = adminGroupBeans.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         AdminObjectGroupBean bean = var5[var7];
         if (interfaceName.equals(bean.getAdminObjectInterface()) && (!StringUtils.notEmpty(bean.getAdminObjectClass()) || className.equals(bean.getAdminObjectClass()))) {
            return bean;
         }
      }

      AdminObjectGroupBean adminGroupBean = (AdminObjectGroupBean)getDescriptorManager().createDescriptorRoot(AdminObjectGroupBean.class).getRootBean();
      adminGroupBean.setAdminObjectClass(className);
      adminGroupBean.setAdminObjectInterface(interfaceName);
      return adminGroupBean;
   }

   public AdminObjectInstanceBean createDummyAdminObjectInstanceBean(AdministeredObjectBean aoBean) {
      AdminObjectInstanceBean adminInstanceBean = (AdminObjectInstanceBean)getDescriptorManager().createDescriptorRoot(AdminObjectInstanceBean.class).getRootBean();
      adminInstanceBean.setJNDIName(aoBean.getName());
      ConfigPropertiesBean wlBeans = adminInstanceBean.getProperties();
      JavaEEPropertyBean[] var4 = aoBean.getProperties();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         JavaEEPropertyBean source = var4[var6];
         ConfigPropertyBean wlBean = wlBeans.createProperty();
         wlBean.setName(source.getName().trim());
         wlBean.setValue(source.getValue().trim());
      }

      return adminInstanceBean;
   }

   public List getAdminObjs() {
      ArrayList adminObjects = new ArrayList();
      adminObjects.addAll(this.jndiToAdminObjects.values());
      return adminObjects;
   }

   public Hashtable getAdminObjectGroupProperties(String adminObjectInterface, String adminObjectClass) {
      this.initAdminObjectGroupIfNot();
      Hashtable mergedConfigProperties = (Hashtable)this.keyToGroup.get(new ObjectPair(adminObjectInterface, adminObjectClass));
      return mergedConfigProperties;
   }

   public AdminObjInfo getAdminObject(String jndiName) {
      return (AdminObjInfo)this.jndiToAdminObjects.get(jndiName);
   }

   private void initAdminObjectInstance() {
      if (this.wlConnBean.isAdminObjectsSet()) {
         AdminObjectsBean wlAdminObjs = this.wlConnBean.getAdminObjects();
         AdminObjectGroupBean[] adminGroups = wlAdminObjs.getAdminObjectGroups();
         AdminObjectGroupBean[] var3 = adminGroups;
         int var4 = adminGroups.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            AdminObjectGroupBean adminGroup = var3[var5];
            AdminObjectInstanceBean[] adminInstances = adminGroup.getAdminObjectInstances();
            AdminObjectBean raAdminObjectBean = this.getMappingAdminObjectBean(adminGroup);
            if (raAdminObjectBean == null) {
               Debug.throwAssertionError("Failed to find definition of AdminObject with interface:" + adminGroup.getAdminObjectInterface() + " and class:" + adminGroup.getAdminObjectClass());
            }

            AdminObjectInstanceBean[] var9 = adminInstances;
            int var10 = adminInstances.length;

            for(int var11 = 0; var11 < var10; ++var11) {
               AdminObjectInstanceBean adminObjectInstance = var9[var11];
               AdminObjInfo adminObjInfo = new AdminObjInfoImpl(this.connBean, raAdminObjectBean, adminObjectInstance, this.wlConnBean, adminGroup);
               this.jndiToAdminObjects.put(adminObjInfo.getKey(), adminObjInfo);
            }
         }
      }

   }

   public void initAdminObjectGroupIfNot() {
      if (this.keyToGroup == null) {
         this.keyToGroup = new HashMap();
         AdminObjectsBean wlAdminObjs = this.wlConnBean.getAdminObjects();
         ConfigPropertiesBean[] wlraConfigProps = new ConfigPropertiesBean[]{wlAdminObjs.getDefaultProperties(), null};
         AdminObjectGroupBean[] var3 = wlAdminObjs.getAdminObjectGroups();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            AdminObjectGroupBean adminGroup = var3[var5];
            wlraConfigProps[1] = adminGroup.getDefaultProperties();
            String adminObjInterface = adminGroup.getAdminObjectInterface();
            String adminObjClass = adminGroup.getAdminObjectClass();
            weblogic.j2ee.descriptor.ConfigPropertyBean[] raConfigProps = this.helper.getRAAdminProperties(adminObjInterface, adminObjClass);
            Hashtable mergedProperties = DDLayerUtils.mergeConfigProperties(raConfigProps, wlraConfigProps, this.propertyNameNormalizer);
            ObjectPair key = new ObjectPair(adminObjInterface, adminObjClass);
            this.keyToGroup.put(key, mergedProperties);
         }
      }

   }

   private List generatePropertyBeansList(ConfigPropertiesBean defaultProperties, ConfigPropertiesBean groupProperties) {
      List result = new ArrayList();
      if (groupProperties != null && groupProperties.getProperties() != null) {
         result.add(toWLRAPropMap(groupProperties.getProperties()));
      }

      if (defaultProperties != null && defaultProperties.getProperties() != null) {
         result.add(toWLRAPropMap(defaultProperties.getProperties()));
      }

      return result;
   }

   public static Map toWLRAPropMap(ConfigPropertyBean[] beans) {
      Map beanMap = new HashMap();
      ArrayUtils.putInMap(beans, new ArrayUtils.KeyLocator() {
         public String getKey(ConfigPropertyBean entry) {
            return entry.getName();
         }
      }, beanMap);
      return beanMap;
   }

   AdminObjectBean getMappingAdminObjectBean(AdminObjectGroupBean group) {
      String adminObjInterface = group.getAdminObjectInterface();
      String adminObjClass = group.getAdminObjectClass();
      return this.helper.getMappingAdminObjectBean(adminObjInterface, adminObjClass);
   }
}
