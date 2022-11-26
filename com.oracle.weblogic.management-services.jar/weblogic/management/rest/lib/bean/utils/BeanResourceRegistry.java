package weblogic.management.rest.lib.bean.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.glassfish.admin.rest.utils.WebAppUtil;
import weblogic.management.rest.lib.bean.resources.BeanActionResource;
import weblogic.management.rest.lib.bean.resources.BeanActionResourceMetaData;
import weblogic.management.rest.lib.bean.resources.BeanCollectionResource;
import weblogic.management.rest.lib.bean.resources.BeanCollectionResourceMetaData;
import weblogic.management.rest.lib.bean.resources.CollectionChildBeanCreateFormResource;
import weblogic.management.rest.lib.bean.resources.CollectionChildBeanCreateFormResourceMetaData;
import weblogic.management.rest.lib.bean.resources.CollectionChildBeanResource;
import weblogic.management.rest.lib.bean.resources.CollectionChildBeanResourceMetaData;
import weblogic.management.rest.lib.bean.resources.SingletonChildBeanCreateFormResource;
import weblogic.management.rest.lib.bean.resources.SingletonChildBeanCreateFormResourceMetaData;
import weblogic.management.rest.lib.bean.resources.SingletonChildBeanResource;
import weblogic.management.rest.lib.bean.resources.SingletonChildBeanResourceMetaData;
import weblogic.management.rest.lib.bean.resources.TaskRuntimeBeanActionResource;
import weblogic.management.rest.lib.bean.resources.TaskRuntimeCollectionChildBeanResource;
import weblogic.management.rest.lib.bean.resources.TaskRuntimeSingletonChildBeanResource;

public class BeanResourceRegistry {
   private static Map webAppToRegistry = new HashMap();
   public static final String BEAN_TREE_ANY = "any";
   private Map customPropertyMarshallers = new HashMap();
   private Map customPropertyUnmarshallers = new HashMap();
   private Map customPropertyMarshallerInstances = new HashMap();
   private Map customPropertyUnmarshallerInstances = new HashMap();
   private BeanResourceRegistryCategory singletonChildResources = new BeanResourceRegistryCategory(ResourceDef.resourceDef(SingletonChildBeanResourceMetaData.class, SingletonChildBeanResource.class), new String[0]);
   private BeanResourceRegistryCategory taskRuntimeSingletonChildResources = new BeanResourceRegistryCategory(ResourceDef.resourceDef(SingletonChildBeanResourceMetaData.class, TaskRuntimeSingletonChildBeanResource.class), new String[0]);
   private BeanResourceRegistryCategory singletonChildCreateFormResources = new BeanResourceRegistryCategory(ResourceDef.resourceDef(SingletonChildBeanCreateFormResourceMetaData.class, SingletonChildBeanCreateFormResource.class), new String[]{"edit", "serverRuntime", "domainRuntime"});
   private BeanResourceRegistryCategory collectionChildResources = new BeanResourceRegistryCategory(ResourceDef.resourceDef(CollectionChildBeanResourceMetaData.class, CollectionChildBeanResource.class), new String[0]);
   private BeanResourceRegistryCategory taskRuntimeCollectionChildResources = new BeanResourceRegistryCategory(ResourceDef.resourceDef(CollectionChildBeanResourceMetaData.class, TaskRuntimeCollectionChildBeanResource.class), new String[0]);
   private BeanResourceRegistryCategory collectionChildCreateFormResources = new BeanResourceRegistryCategory(ResourceDef.resourceDef(CollectionChildBeanCreateFormResourceMetaData.class, CollectionChildBeanCreateFormResource.class), new String[]{"edit", "serverRuntime", "domainRuntime"});
   private BeanResourceRegistryCategory collectionResources = new BeanResourceRegistryCategory(ResourceDef.resourceDef(BeanCollectionResourceMetaData.class, BeanCollectionResource.class), new String[0]);
   private BeanResourceRegistryCategory actionResources = new BeanResourceRegistryCategory(ResourceDef.resourceDef(BeanActionResourceMetaData.class, BeanActionResource.class), new String[0]);
   private BeanResourceRegistryCategory taskRuntimeActionResources = new BeanResourceRegistryCategory(ResourceDef.resourceDef(BeanActionResourceMetaData.class, TaskRuntimeBeanActionResource.class), new String[0]);
   private BeanResourceRegistryCustomResources customResources = new BeanResourceRegistryCustomResources();
   private Map customResourceTypes = new HashMap();
   private List resourceMetaDataDescriptions = new ArrayList();
   private RequestBodyHelper requestBodyHelper = new RequestBodyHelper();

   public static BeanResourceRegistry instance() {
      String webAppId = getThreadWebAppId();
      BeanResourceRegistry registry = (BeanResourceRegistry)webAppToRegistry.get(webAppId);
      if (registry == null) {
         registry = new BeanResourceRegistry();
         registry.registerResourceMetaDataDescriptions("weblogic.management.rest.lib.bean.utils.DefaultResourceMetaDataDescriptions", registry.getClass().getClassLoader());
         webAppToRegistry.put(webAppId, registry);
      }

      return registry;
   }

   public static void removeWebApp() {
      webAppToRegistry.remove(getThreadWebAppId());
   }

   private static String getThreadWebAppId() {
      return WebAppUtil.getThreadWebAppId();
   }

   public BeanResourceRegistryCategory singletonChildResources() {
      return this.singletonChildResources;
   }

   public BeanResourceRegistryCategory taskRuntimeSingletonChildResources() {
      return this.taskRuntimeSingletonChildResources;
   }

   public BeanResourceRegistryCategory singletonChildCreateFormResources() {
      return this.singletonChildCreateFormResources;
   }

   public BeanResourceRegistryCategory collectionChildResources() {
      return this.collectionChildResources;
   }

   public BeanResourceRegistryCategory taskRuntimeCollectionChildResources() {
      return this.taskRuntimeCollectionChildResources;
   }

   public BeanResourceRegistryCategory collectionChildCreateFormResources() {
      return this.collectionChildCreateFormResources;
   }

   public BeanResourceRegistryCategory collectionResources() {
      return this.collectionResources;
   }

   public BeanResourceRegistryCategory actionResources() {
      return this.actionResources;
   }

   public BeanResourceRegistryCategory taskRuntimeActionResources() {
      return this.taskRuntimeActionResources;
   }

   public BeanResourceRegistryCustomResources customResources() {
      return this.customResources;
   }

   public void registerConfidentialPropertyNamePattern(String pattern) {
      this.getRequestBodyHelper().registerConfidentialPropertyNamePattern(pattern);
   }

   public RequestBodyHelper getRequestBodyHelper() {
      return this.requestBodyHelper;
   }

   public void registerCustomPropertyMarshaller(String beanIntf, String propertyName, Class clz) {
      Map classes = (Map)this.customPropertyMarshallers.get(beanIntf);
      if (classes == null) {
         classes = new HashMap();
         this.customPropertyMarshallers.put(beanIntf, classes);
      }

      ((Map)classes).put(propertyName, clz);
   }

   public Class getCustomPropertyMarshaller(String beanIntf, String propertyName) {
      Map classes = (Map)this.customPropertyMarshallers.get(beanIntf);
      return classes == null ? null : (Class)classes.get(propertyName);
   }

   public void registerCustomPropertyUnmarshaller(String beanIntf, String propertyName, Class clz) {
      Map classes = (Map)this.customPropertyUnmarshallers.get(beanIntf);
      if (classes == null) {
         classes = new HashMap();
         this.customPropertyUnmarshallers.put(beanIntf, classes);
      }

      ((Map)classes).put(propertyName, clz);
   }

   public Class getCustomPropertyUnmarshaller(String beanIntf, String propertyName) {
      Map classes = (Map)this.customPropertyUnmarshallers.get(beanIntf);
      return classes == null ? null : (Class)classes.get(propertyName);
   }

   public void registerCustomPropertyMarshallerInstance(String beanIntf, String propertyName, PropertyMarshaller instance) {
      Map instances = (Map)this.customPropertyMarshallerInstances.get(beanIntf);
      if (instances == null) {
         instances = new HashMap();
         this.customPropertyMarshallerInstances.put(beanIntf, instances);
      }

      ((Map)instances).put(propertyName, instance);
   }

   public PropertyMarshaller getCustomPropertyMarshallerInstance(String beanIntf, String propertyName) {
      Map instances = (Map)this.customPropertyMarshallerInstances.get(beanIntf);
      return instances == null ? null : (PropertyMarshaller)instances.get(propertyName);
   }

   public void registerCustomPropertyUnmarshallerInstance(String beanIntf, String propertyName, PropertyUnmarshaller instance) {
      Map instances = (Map)this.customPropertyUnmarshallerInstances.get(beanIntf);
      if (instances == null) {
         instances = new HashMap();
         this.customPropertyUnmarshallerInstances.put(beanIntf, instances);
      }

      ((Map)instances).put(propertyName, instance);
   }

   public PropertyUnmarshaller getCustomPropertyUnmarshallerInstance(String beanIntf, String propertyName) {
      Map instances = (Map)this.customPropertyUnmarshallerInstances.get(beanIntf);
      return instances == null ? null : (PropertyUnmarshaller)instances.get(propertyName);
   }

   public void registerCustomResourceTypes(String beanIntf, Map val) {
      this.customResourceTypes.put(beanIntf, val);
   }

   public Map getCustomResourceTypes(String beanIntf) {
      return (Map)this.customResourceTypes.get(beanIntf);
   }

   public void registerResourceMetaDataDescriptions(String className, ClassLoader classLoader) {
      this.resourceMetaDataDescriptions.add(new ResourceMetaDataDescriptions(className, classLoader));
   }

   public List getResourceMetaDataDescriptions() {
      return this.resourceMetaDataDescriptions;
   }
}
