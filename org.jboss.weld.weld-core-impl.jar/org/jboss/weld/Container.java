package org.jboss.weld;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import org.jboss.weld.annotated.slim.AnnotatedTypeIdentifier;
import org.jboss.weld.bootstrap.BeanDeploymentArchiveMapping;
import org.jboss.weld.bootstrap.api.Environment;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.bootstrap.api.Singleton;
import org.jboss.weld.bootstrap.api.SingletonProvider;
import org.jboss.weld.logging.BeanManagerLogger;
import org.jboss.weld.manager.BeanManagerImpl;

public class Container {
   public static final String CONTEXT_ID_KEY = "WELD_CONTEXT_ID_KEY";
   private static Singleton instance = SingletonProvider.instance().create(Container.class);
   private static final AtomicReference ENV_REFERENCE = new AtomicReference((Object)null);
   private final String contextId;
   private final BeanManagerImpl deploymentManager;
   private final Map managers;
   private final Map beanDeploymentArchives;
   private final ServiceRegistry deploymentServices;
   private ContainerState state;

   public static Container instance() {
      return (Container)instance.get("STATIC_INSTANCE");
   }

   public static boolean available() {
      return available("STATIC_INSTANCE");
   }

   public static Container instance(String contextId) {
      return (Container)instance.get(contextId);
   }

   public static Container instance(BeanManagerImpl manager) {
      return instance(manager.getContextId());
   }

   public static Container instance(AnnotatedTypeIdentifier identifier) {
      return instance(identifier.getContextId());
   }

   public static boolean available(String contextId) {
      return isSet(contextId) && instance(contextId).getState().isAvailable();
   }

   public static boolean isSet(String contextId) {
      return instance.isSet(contextId);
   }

   public static void initialize(BeanManagerImpl deploymentManager, ServiceRegistry deploymentServices) {
      Container instance = new Container("STATIC_INSTANCE", deploymentManager, deploymentServices);
      Container.instance.set("STATIC_INSTANCE", instance);
   }

   public static void initialize(String contextId, BeanManagerImpl deploymentManager, ServiceRegistry deploymentServices) {
      initialize(contextId, deploymentManager, deploymentServices, (Environment)null);
   }

   public static void initialize(String contextId, BeanManagerImpl deploymentManager, ServiceRegistry deploymentServices, Environment environment) {
      Container instance = new Container(contextId, deploymentManager, deploymentServices, environment);
      Container.instance.set(contextId, instance);
   }

   public Container(String contextId, BeanManagerImpl deploymentManager, ServiceRegistry deploymentServices) {
      this(contextId, deploymentManager, deploymentServices, (Environment)null);
   }

   public Container(BeanManagerImpl deploymentManager, ServiceRegistry deploymentServices) {
      this("STATIC_INSTANCE", deploymentManager, deploymentServices);
   }

   public Container(String contextId, BeanManagerImpl deploymentManager, ServiceRegistry deploymentServices, Environment environment) {
      this.state = ContainerState.STOPPED;
      this.deploymentManager = deploymentManager;
      this.managers = new ConcurrentHashMap();
      this.managers.put(deploymentManager.getId(), deploymentManager);
      this.beanDeploymentArchives = new ConcurrentHashMap();
      this.deploymentServices = deploymentServices;
      this.contextId = contextId;
      ENV_REFERENCE.compareAndSet((Object)null, environment);
   }

   public void cleanup() {
      this.managers.clear();
      Iterator var1 = this.beanDeploymentArchives.values().iterator();

      while(var1.hasNext()) {
         BeanManagerImpl beanManager = (BeanManagerImpl)var1.next();
         beanManager.cleanup();
      }

      this.beanDeploymentArchives.clear();
      this.deploymentServices.cleanup();
      this.deploymentManager.cleanup();
      instance.clear(this.contextId);
   }

   public BeanManagerImpl deploymentManager() {
      return this.deploymentManager;
   }

   public Map beanDeploymentArchives() {
      return this.beanDeploymentArchives;
   }

   public BeanManagerImpl getBeanManager(String key) {
      return (BeanManagerImpl)this.managers.get(key);
   }

   /** @deprecated */
   @Deprecated
   public BeanManagerImpl activityManager(String key) {
      return this.getBeanManager(key);
   }

   private String addBeanManager(BeanManagerImpl manager) {
      String id = manager.getId();
      if (manager.getId() == null) {
         throw BeanManagerLogger.LOG.nullBeanManagerId();
      } else {
         this.managers.put(id, manager);
         return id;
      }
   }

   public ServiceRegistry services() {
      return this.deploymentServices;
   }

   public void putBeanDeployments(BeanDeploymentArchiveMapping bdaMapping) {
      Iterator var2 = bdaMapping.getBdaToBeanManagerMap().entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         this.beanDeploymentArchives.put(entry.getKey(), entry.getValue());
         this.addBeanManager((BeanManagerImpl)entry.getValue());
      }

   }

   public ContainerState getState() {
      return this.state;
   }

   public void setState(ContainerState state) {
      this.state = state;
   }

   public static Environment getEnvironment() {
      return (Environment)ENV_REFERENCE.get();
   }
}
