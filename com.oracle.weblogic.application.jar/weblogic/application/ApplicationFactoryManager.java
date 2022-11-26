package weblogic.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.application.internal.CarDeploymentFactory;
import weblogic.application.internal.EarDeploymentFactory;
import weblogic.application.internal.library.EarLibraryFactory;
import weblogic.application.internal.library.JarLibraryFactory;
import weblogic.application.internal.library.LibraryDeploymentFactory;
import weblogic.application.library.LibraryFactory;
import weblogic.application.utils.AppSupportDeclarations;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.J2EELogger;

public final class ApplicationFactoryManager {
   private DeploymentFactory lastDeploymentFactory;
   private LibraryFactory defaultLibFactory;
   private List deploymentFactories = Collections.synchronizedList(new ArrayList());
   private List moduleFactories = Collections.emptyList();
   private List weblogicModuleFactories = Collections.emptyList();
   private List wlappModuleFactories = Collections.emptyList();
   private List libraryFactories = Collections.emptyList();
   private List wlExtensionModuleFactories = Collections.emptyList();
   private List compFactories = null;
   private List appExtFactories = Collections.emptyList();
   private Map moduleExtensionFactories = new HashMap();
   private DebugLogger debugger = DebugLogger.getDebugLogger("DebugAppContainer");
   private static final String[] compMBeanFactories = new String[]{"weblogic.connector.deploy.ConnectorDeploymentFactory", "weblogic.application.internal.EarDeploymentFactory", "weblogic.ejb.container.deployer.EJBDeploymentFactory", "weblogic.coherence.container.server.CoherenceDeploymentFactory", "weblogic.servlet.internal.WarDeploymentFactory"};
   private static final ApplicationFactoryManager theOne = new ApplicationFactoryManager();

   private ApplicationFactoryManager() {
      this.addLibraryFactory(new EarLibraryFactory());
      this.addDefaultLibraryFactory(new JarLibraryFactory());
      this.addDeploymentFactory(new LibraryDeploymentFactory());
      this.addDeploymentFactory(new EarDeploymentFactory());
      this.addDeploymentFactory(new CarDeploymentFactory());
   }

   public static ApplicationFactoryManager getApplicationFactoryManager() {
      return theOne;
   }

   public static ApplicationFactoryManager getEmptyApplicationFactoryManager() {
      return new ApplicationFactoryManager();
   }

   public synchronized void addLastDeploymentFactory(DeploymentFactory f) {
      if (this.lastDeploymentFactory != null) {
         throw new AssertionError("Attempt to add " + f.getClass().getName() + " as the last DeploymentFactory, but " + this.lastDeploymentFactory.getClass().getName() + " has already used this hack.");
      } else {
         this.lastDeploymentFactory = f;
         this.deploymentFactories.add(f);
      }
   }

   public synchronized void addDeploymentFactory(DeploymentFactory f) {
      if (this.lastDeploymentFactory == null) {
         this.deploymentFactories.add(f);
      } else {
         this.deploymentFactories.add(this.deploymentFactories.size() - 1, f);
      }

   }

   public synchronized Iterator getDeploymentFactories() {
      return this.deploymentFactories.iterator();
   }

   public synchronized void addModuleFactory(ModuleFactory f) {
      List mf = new ArrayList(this.moduleFactories.size() + 1);
      mf.addAll(this.moduleFactories);
      mf.add(f);
      this.moduleFactories = mf;
      AppSupportDeclarations.instance.register(f);
   }

   public Iterator getModuleFactories() {
      return this.moduleFactories.iterator();
   }

   public synchronized void nullInitCompFactories() {
      this.compFactories = new ArrayList();
   }

   public synchronized Iterator getComponentMBeanFactories() {
      if (this.compFactories == null) {
         this.initCompFactories();
      }

      return this.compFactories.iterator();
   }

   private synchronized void initCompFactories() {
      if (this.debugger.isDebugEnabled()) {
         this.debugger.debug("Finding ComponentMBeanFactory instances from registered DeploymentFactory instance");
         Iterator i = this.getDeploymentFactories();

         while(i.hasNext()) {
            DeploymentFactory factory = (DeploymentFactory)i.next();
            this.debugger.debug("Registered DeploymentFactory: " + factory);
         }
      }

      this.compFactories = Collections.synchronizedList(new ArrayList());
      String[] var9 = compMBeanFactories;
      int var10 = var9.length;

      for(int var3 = 0; var3 < var10; ++var3) {
         String factoryName = var9[var3];
         boolean found = false;
         Iterator i = this.getDeploymentFactories();

         while(i.hasNext()) {
            DeploymentFactory factory = (DeploymentFactory)i.next();
            if (factory.getClass().getName().equals(factoryName)) {
               if (this.debugger.isDebugEnabled()) {
                  this.debugger.debug("Adding component factory " + factoryName);
               }

               this.compFactories.add((ComponentMBeanFactory)factory);
               found = true;
            }
         }

         if (!found) {
            J2EELogger.logComponentMBeanFactoryNotRegisteredAsDeploymentFactory(factoryName);
            if (this.debugger.isDebugEnabled()) {
               this.debugger.debug("Component factory " + factoryName + " could not be found as a registered deployment factory. Creating using reflection.");
            }

            try {
               ComponentMBeanFactory factory = (ComponentMBeanFactory)Class.forName(factoryName).newInstance();
               this.compFactories.add(factory);
               if (this.debugger.isDebugEnabled()) {
                  this.debugger.debug("Adding component factory " + factoryName + ": " + factory);
               }
            } catch (Exception var8) {
               J2EELogger.logComponentMBeanFactoryInstantiationFailed(factoryName, var8);
            }
         }
      }

   }

   public synchronized void addWblogicModuleFactory(WeblogicModuleFactory f) {
      List mf = new ArrayList(this.weblogicModuleFactories.size() + 1);
      mf.addAll(this.weblogicModuleFactories);
      mf.add(f);
      this.weblogicModuleFactories = mf;
   }

   public Iterator getWeblogicModuleFactories() {
      return this.weblogicModuleFactories.iterator();
   }

   public synchronized void addWLAppModuleFactory(WebLogicApplicationModuleFactory f) {
      List mf = new ArrayList(this.wlappModuleFactories.size() + 1);
      mf.addAll(this.wlappModuleFactories);
      mf.add(f);
      this.wlappModuleFactories = mf;
   }

   public Iterator getWLAppModuleFactories() {
      return this.wlappModuleFactories.iterator();
   }

   public synchronized void addDefaultLibraryFactory(LibraryFactory f) {
      if (this.defaultLibFactory != null) {
         throw new AssertionError("Attempt to add " + f.getClass().getName() + " as the default LibraryFactory, but " + this.defaultLibFactory.getClass().getName() + " has already used this feature.");
      } else {
         this.addLibraryFactory(f);
         this.defaultLibFactory = f;
      }
   }

   public synchronized void addLibraryFactory(LibraryFactory f) {
      List lf = new ArrayList(this.libraryFactories.size() + 1);
      lf.addAll(this.libraryFactories);
      if (this.defaultLibFactory != null) {
         lf.add(this.libraryFactories.size() - 1, f);
      } else {
         lf.add(f);
      }

      this.libraryFactories = lf;
   }

   public Iterator getLibraryFactories() {
      return this.libraryFactories.iterator();
   }

   public synchronized void addWebLogicExtensionModuleFactory(ExtensionModuleFactory f) {
      List list = new ArrayList(this.wlExtensionModuleFactories.size() + 1);
      list.addAll(this.wlExtensionModuleFactories);
      list.add(f);
      this.wlExtensionModuleFactories = list;
   }

   public Iterator getWebLogicExtenstionModuleFactories() {
      return this.wlExtensionModuleFactories.iterator();
   }

   public synchronized void addAppDeploymentExtensionFactory(AppDeploymentExtensionFactory f) {
      List list = new ArrayList(this.appExtFactories.size() + 1);
      list.addAll(this.appExtFactories);
      list.add(f);
      this.appExtFactories = list;
   }

   public Iterator getAppExtensionFactories() {
      return this.appExtFactories.iterator();
   }

   public synchronized void addModuleExtensionFactory(String extensibleModuleType, ModuleExtensionFactory factory) {
      if (!this.moduleExtensionFactories.containsKey(extensibleModuleType)) {
         this.moduleExtensionFactories.put(extensibleModuleType, new ArrayList());
      }

      ((List)this.moduleExtensionFactories.get(extensibleModuleType)).add(factory);
      AppSupportDeclarations.instance.register(factory);
   }

   public Iterator getModuleExtensionFactories(String extensibleModuleType) {
      return this.moduleExtensionFactories.containsKey(extensibleModuleType) ? ((List)this.moduleExtensionFactories.get(extensibleModuleType)).iterator() : Collections.emptyList().iterator();
   }
}
