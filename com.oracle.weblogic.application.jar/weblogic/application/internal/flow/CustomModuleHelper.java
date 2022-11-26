package weblogic.application.internal.flow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.CustomModuleFactory;
import weblogic.application.Module;
import weblogic.application.ModuleContext;
import weblogic.application.custom.internal.DescriptorModuleFactory;
import weblogic.application.internal.CustomModuleContextImpl;
import weblogic.application.internal.FlowContext;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.j2ee.descriptor.wl.CustomModuleBean;
import weblogic.j2ee.descriptor.wl.ModuleProviderBean;
import weblogic.j2ee.descriptor.wl.WeblogicExtensionBean;
import weblogic.management.DeploymentException;
import weblogic.utils.classloaders.GenericClassLoader;

public final class CustomModuleHelper {
   public static Map initFactories(WeblogicExtensionBean extDD, GenericClassLoader gcl) throws DeploymentException {
      return initFactories(extDD, gcl, (String)null, (String)null);
   }

   public static Map initFactories(WeblogicExtensionBean extDD, GenericClassLoader gcl, String parentModuleId, String parentModuleUri) throws DeploymentException {
      if (extDD == null) {
         return null;
      } else {
         ModuleProviderBean[] providerDD = extDD.getModuleProviders();
         if (providerDD != null && providerDD.length != 0) {
            Map factoryMap = new HashMap(providerDD.length);

            for(int i = 0; i < providerDD.length; ++i) {
               CustomModuleFactory f = loadModuleFactory(providerDD[i].getModuleFactoryClassName(), gcl);
               f.init(new CustomModuleContextImpl(providerDD[i], parentModuleId, parentModuleUri));
               factoryMap.put(providerDD[i].getName(), f);
            }

            return factoryMap;
         } else {
            return null;
         }
      }
   }

   private static CustomModuleFactory loadModuleFactory(String className, GenericClassLoader gcl) throws DeploymentException {
      try {
         Class c = Class.forName(className, false, gcl);
         if (!CustomModuleFactory.class.isAssignableFrom(c)) {
            throw new DeploymentException("Your module-provider's module-factory-class " + className + " does not implement weblogic.application.CustomModuleFactory");
         } else {
            return (CustomModuleFactory)c.newInstance();
         }
      } catch (ClassNotFoundException var3) {
         throw new DeploymentException("Unable to load your custom module provider's module-factory-class " + className);
      } catch (InstantiationException var4) {
         throw new DeploymentException(var4);
      } catch (IllegalAccessException var5) {
         throw new DeploymentException(var5);
      }
   }

   public static Module[] createScopedCustomModules(FlowContext ctx, Module module, String moduleUri, WeblogicExtensionBean extDD, GenericClassLoader moduleClassLoader) throws DeploymentException {
      List l = new ArrayList();
      if (extDD != null) {
         Map factories = initFactories(extDD, moduleClassLoader, module.getId(), moduleUri);
         createCustomModules(WebLogicModuleType.getTypeFromString(module.getType()), ctx.getModuleContext(module.getId()).getURI(), module.getId(), extDD.getCustomModules(), l, factories);
      } else {
         ModuleContext mc = ctx.getModuleContext(module.getId());
         if (mc != null) {
            createCustomModules(WebLogicModuleType.getTypeFromString(module.getType()), mc.getURI(), module.getId(), (CustomModuleBean[])null, l, (Map)null);
         }
      }

      return (Module[])((Module[])l.toArray(new Module[l.size()]));
   }

   public static void createCustomModules(ModuleType parentModuleType, String parentModuleUri, String parentModuleId, CustomModuleBean[] cm, List moduleList, Map customModuleFactories) throws DeploymentException {
      if (cm != null && customModuleFactories != null) {
         CustomModuleBean[] var6 = cm;
         int var7 = cm.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            CustomModuleBean cmb = var6[var8];
            CustomModuleFactory factory = (CustomModuleFactory)customModuleFactories.get(cmb.getProviderName());
            if (factory == null) {
               throw new DeploymentException("The custom module with the uri " + cmb.getUri() + " specified a provider-name of " + cmb.getProviderName() + ". However, there was no module-provider with this name in your weblogic-extension.xml.");
            }

            moduleList.add(factory.createModule(cmb));
         }
      }

      moduleList.addAll(DescriptorModuleFactory.createModules(parentModuleType, parentModuleUri, parentModuleId));
   }
}
