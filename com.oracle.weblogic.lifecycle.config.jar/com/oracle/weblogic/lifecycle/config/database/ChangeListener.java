package com.oracle.weblogic.lifecycle.config.database;

import java.util.List;
import javax.inject.Inject;
import org.glassfish.hk2.api.PostConstruct;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.ServiceLocatorFactory;
import org.glassfish.hk2.configuration.hub.api.BeanDatabase;
import org.glassfish.hk2.configuration.hub.api.BeanDatabaseUpdateListener;
import org.jvnet.hk2.annotations.Service;

@Service(
   name = "ConfigChangeListener"
)
public class ChangeListener implements BeanDatabaseUpdateListener, PostConstruct {
   @Inject
   private ServiceLocator systemServiceLocator;
   private ServiceLocator serviceLocator;
   private LifecyclePersistencePlugin plugin;

   public void postConstruct() {
      this.serviceLocator = ServiceLocatorFactory.getInstance().find("LifecycleConfig");
      if (this.serviceLocator == null) {
         this.serviceLocator = ServiceLocatorFactory.getInstance().create("LifecycleConfig", this.systemServiceLocator);
      }

      if (this.serviceLocator == null) {
         throw new AssertionError("Could not create serviceLocator named LifecycleConfig");
      }
   }

   public LifecyclePersistencePlugin getPlugin() {
      return this.plugin;
   }

   public void setPlugin(LifecyclePersistencePlugin plugin) {
      this.plugin = plugin;
   }

   public void removePlugin() {
      this.plugin = null;
   }

   public void commitDatabaseChange(BeanDatabase referenceDatabase, BeanDatabase currentDatabase, Object commitMessage, List changes) {
      try {
         if (this.plugin != null) {
            this.plugin.persist(changes);
         }

      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareDatabaseChange(BeanDatabase currentDatabase, BeanDatabase proposedDatabase, Object commitMessage, List changes) {
   }

   public void rollbackDatabaseChange(BeanDatabase currentDatabase, BeanDatabase proposedDatabase, Object commitMessage, List changes) {
   }
}
