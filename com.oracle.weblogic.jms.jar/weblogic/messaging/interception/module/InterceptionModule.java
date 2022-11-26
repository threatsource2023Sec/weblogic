package weblogic.messaging.interception.module;

import java.io.File;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.ApplicationContext;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.UpdateListener;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.AssociationBean;
import weblogic.j2ee.descriptor.wl.InterceptionBean;
import weblogic.management.ManagementException;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.messaging.interception.MessageInterceptionService;
import weblogic.messaging.interception.exceptions.InterceptionServiceException;
import weblogic.messaging.interception.interfaces.AssociationHandle;
import weblogic.messaging.interception.interfaces.InterceptionPointNameDescriptionListener;
import weblogic.messaging.interception.interfaces.InterceptionService;
import weblogic.messaging.interception.interfaces.ProcessorHandle;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.utils.classloaders.GenericClassLoader;

public class InterceptionModule implements Module, UpdateListener {
   private InterceptionBean wholeModule = null;
   private HashMap doLaterList = new HashMap();
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private ApplicationContextInternal appCtx;
   private InterceptionParser parser;
   private final String uri;
   private InterceptionComponent interceptionComponent;

   public InterceptionModule(String uri) {
      this.uri = uri;
      this.parser = new InterceptionParser();
   }

   public String getId() {
      return this.uri;
   }

   public String getType() {
      return WebLogicModuleType.MODULETYPE_INTERCEPT;
   }

   public DescriptorBean[] getDescriptors() {
      return new DescriptorBean[0];
   }

   public ComponentRuntimeMBean[] getComponentRuntimeMBeans() {
      return new ComponentRuntimeMBean[]{this.interceptionComponent};
   }

   public void initUsingLoader(ApplicationContext ac, GenericClassLoader parent, UpdateListener.Registration reg) throws ModuleException {
      this.init(ac, parent, reg);
   }

   public static String getCanonicalPath(ApplicationContextInternal appCtx, String fileName) {
      File[] paths = appCtx.getApplicationPaths();
      fileName = paths[0] + "/" + fileName;
      return (new File(fileName)).getAbsolutePath().replace(File.separatorChar, '/');
   }

   static void callAddAssociation(AssociationBean association) throws InterceptionServiceException {
      InterceptionService service = MessageInterceptionService.getSingleton();
      service.addAssociation(association.getInterceptionPoint().getType(), association.getInterceptionPoint().getNameSegment(), association.getProcessor().getType(), association.getProcessor().getName(), true);
   }

   private static void callRemoveAssociation(AssociationBean association) throws InterceptionServiceException {
      InterceptionService service = MessageInterceptionService.getSingleton();
      AssociationHandle removeMe = service.getAssociationHandle(association.getInterceptionPoint().getType(), association.getInterceptionPoint().getNameSegment());
      if (removeMe != null) {
         service.removeAssociation(removeMe);
      }

   }

   public boolean loadConfiguration() throws InterceptionServiceException {
      InterceptionService service = MessageInterceptionService.getSingleton();

      int i;
      for(i = 0; i < this.wholeModule.getProcessorTypes().length; ++i) {
         try {
            service.registerProcessorType(this.wholeModule.getProcessorTypes()[i].getType(), this.wholeModule.getProcessorTypes()[i].getFactory());
         } catch (InterceptionServiceException var6) {
         }
      }

      for(i = 0; i < this.wholeModule.getProcessors().length; ++i) {
         service.addProcessor(this.wholeModule.getProcessors()[i].getType(), this.wholeModule.getProcessors()[i].getName(), this.wholeModule.getProcessors()[i].getMetadata());
      }

      for(i = 0; i < this.wholeModule.getAssociations().length; ++i) {
         AssociationBean association = this.wholeModule.getAssociations()[i];
         String IPtype = association.getInterceptionPoint().getType();
         if (service.getInterceptionPointNameDescription(IPtype) == null) {
            DoLaterClass doLater = (DoLaterClass)this.doLaterList.get(IPtype);
            if (doLater == null) {
               doLater = new DoLaterClass(IPtype);
               this.doLaterList.put(IPtype, doLater);
            }

            doLater.addLater(association);
         } else {
            callAddAssociation(association);
         }
      }

      return true;
   }

   public boolean unloadConfiguration() throws InterceptionServiceException {
      InterceptionService service = MessageInterceptionService.getSingleton();

      int i;
      for(i = 0; i < this.wholeModule.getAssociations().length; ++i) {
         AssociationBean association = this.wholeModule.getAssociations()[i];
         String IPtype = association.getInterceptionPoint().getType();
         if (service.getInterceptionPointNameDescription(IPtype) == null) {
            DoLaterClass doLater = (DoLaterClass)this.doLaterList.get(IPtype);
            if (doLater != null) {
               doLater.dontAddLater(association);
            }
         } else {
            callRemoveAssociation(association);
         }
      }

      for(i = 0; i < this.wholeModule.getProcessors().length; ++i) {
         ProcessorHandle removeMe = service.getProcessorHandle(this.wholeModule.getProcessors()[i].getType(), this.wholeModule.getProcessors()[i].getName());
         if (removeMe != null) {
            service.removeProcessor(removeMe);
         }
      }

      return true;
   }

   public GenericClassLoader init(ApplicationContext paramAppCtx, GenericClassLoader parent, UpdateListener.Registration reg) throws ModuleException {
      boolean success = false;
      this.appCtx = (ApplicationContextInternal)paramAppCtx;
      reg.addUpdateListener(this);
      this.wholeModule = this.parser.createInterceptionDescriptor(this.appCtx, this.uri);

      try {
         this.interceptionComponent = new InterceptionComponent(this.appCtx.getApplicationId(), this.appCtx);
         this.interceptionComponent.begin();
         return parent;
      } catch (ManagementException var6) {
         throw new ModuleException("ERROR: Could not create JMSComponent", var6);
      }
   }

   public void prepare() {
   }

   public void activate() throws ModuleException {
      try {
         this.loadConfiguration();
      } catch (InterceptionServiceException var2) {
         throw new ModuleException("Cannot load interception configuration" + var2, var2);
      }
   }

   public void start() {
   }

   public void deactivate() throws ModuleException {
      try {
         this.unloadConfiguration();
      } catch (InterceptionServiceException var2) {
         throw new ModuleException("Cannot unload interception configuration");
      }
   }

   public void unprepare() {
   }

   public void destroy(UpdateListener.Registration reg) throws ModuleException {
      reg.removeUpdateListener(this);

      try {
         this.interceptionComponent.end();
      } catch (ManagementException var3) {
         throw new ModuleException("unregister of InterceptionComponent failed", var3);
      }
   }

   public void remove() {
   }

   public void adminToProduction() {
   }

   public void gracefulProductionToAdmin(AdminModeCompletionBarrier barrier) {
   }

   public void forceProductionToAdmin() {
   }

   public boolean acceptURI(String updateUri) {
      return ".".equals(updateUri) ? true : this.uri.equals(updateUri);
   }

   public void prepareUpdate(String uri) {
   }

   public void activateUpdate(String uri) throws ModuleException {
      this.deactivate();
      this.activate();
   }

   public void rollbackUpdate(String uri) {
   }

   private final class DoLaterClass implements InterceptionPointNameDescriptionListener {
      private String type = null;
      private LinkedList doLater = new LinkedList();
      private boolean registered = false;
      private InterceptionService service = MessageInterceptionService.getSingleton();

      public DoLaterClass(String type) {
         this.type = type;

         try {
            this.service.registerInterceptionPointNameDescriptionListener(this);
         } catch (InterceptionServiceException var4) {
            throw new AssertionError("Programmer error - exception when registering for notification with Interception Service" + var4);
         }
      }

      public void addLater(AssociationBean association) throws InterceptionServiceException {
         if (this.registered) {
            InterceptionModule.callAddAssociation(association);
         } else {
            this.doLater.add(association);
         }

      }

      private boolean sameNamedAssociations(AssociationBean a1, AssociationBean a2) {
         String[] s1 = a1.getInterceptionPoint().getNameSegment();
         String[] s2 = a2.getInterceptionPoint().getNameSegment();
         if (s1.length != s2.length) {
            return false;
         } else {
            for(int i = 0; i < s1.length; ++i) {
               if (!s1[i].equals(s2[i])) {
                  return false;
               }
            }

            return true;
         }
      }

      public void dontAddLater(AssociationBean association) {
         ListIterator iter = this.doLater.listIterator();

         while(iter.hasNext()) {
            AssociationBean laterAssociation = (AssociationBean)iter.next();
            if (this.sameNamedAssociations(association, laterAssociation)) {
               this.doLater.remove(laterAssociation);
               break;
            }
         }

      }

      public final void addAssociationsFromList() {
         ListIterator walk = this.doLater.listIterator(0);

         while(walk.hasNext()) {
            AssociationBean association = (AssociationBean)walk.next();

            try {
               InterceptionModule.callAddAssociation(association);
            } catch (InterceptionServiceException var4) {
            }
         }

         this.doLater = null;
      }

      public void onRegister() {
         this.registered = true;

         try {
            SecurityServiceManager.runAs(InterceptionModule.KERNEL_ID, InterceptionModule.KERNEL_ID, new PrivilegedExceptionAction() {
               public Object run() {
                  DoLaterClass.this.addAssociationsFromList();
                  return null;
               }
            });
         } catch (PrivilegedActionException var2) {
            throw new AssertionError("addAssociations problem" + var2);
         }
      }

      public String getType() {
         return this.type;
      }
   }
}
