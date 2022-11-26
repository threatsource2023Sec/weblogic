package weblogic.application;

import weblogic.descriptor.DescriptorBean;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.utils.classloaders.GenericClassLoader;

public interface Module {
   String getId();

   String getType();

   ComponentRuntimeMBean[] getComponentRuntimeMBeans();

   DescriptorBean[] getDescriptors();

   GenericClassLoader init(ApplicationContext var1, GenericClassLoader var2, UpdateListener.Registration var3) throws ModuleException;

   void initUsingLoader(ApplicationContext var1, GenericClassLoader var2, UpdateListener.Registration var3) throws ModuleException;

   void prepare() throws ModuleException;

   void activate() throws ModuleException;

   void start() throws ModuleException;

   void deactivate() throws ModuleException;

   void unprepare() throws ModuleException;

   void destroy(UpdateListener.Registration var1) throws ModuleException;

   void remove() throws ModuleException;

   void adminToProduction();

   void gracefulProductionToAdmin(AdminModeCompletionBarrier var1) throws ModuleException;

   void forceProductionToAdmin() throws ModuleException;
}
