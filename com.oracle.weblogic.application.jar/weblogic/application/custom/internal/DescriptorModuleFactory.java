package weblogic.application.custom.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.config.DefaultEARModule;
import weblogic.application.config.DefaultModule;
import weblogic.application.custom.CustomizationManagerInternal;
import weblogic.utils.compiler.ToolFailureException;

public class DescriptorModuleFactory {
   public static List createModules(ModuleType parentModuleType, String parentModuleUri, String parentModuleId) {
      Set uris = CustomizationManagerInternal.getInstance().getRegisteredDescriptors(parentModuleType);
      List modules = new ArrayList();
      if (uris != null) {
         Iterator var5 = uris.iterator();

         while(var5.hasNext()) {
            String uri = (String)var5.next();
            if (parentModuleUri == null) {
               if (parentModuleId != null || parentModuleType != ModuleType.EAR) {
                  throw new AssertionError("The following parameters must be consistent: parentModuleUri, parentModuleUri null and parentModuleType ModuleType.EAR");
               }

               modules.add(new DefaultModule(uri));
            } else {
               modules.add(new DefaultModule(parentModuleUri, parentModuleId, parentModuleType, uri));
            }
         }
      }

      return modules;
   }

   public static List createToolsModule(String parentModuleUri, String parentModuleId, ModuleType parentModuleType) throws ToolFailureException {
      Set uris = CustomizationManagerInternal.getInstance().getRegisteredDescriptors(parentModuleType);
      List modules = new ArrayList();
      if (uris != null) {
         Iterator var5 = uris.iterator();

         while(var5.hasNext()) {
            String uri = (String)var5.next();
            modules.add(new DefaultEARModule(parentModuleUri, parentModuleId, parentModuleType, uri, uri));
         }
      }

      return modules;
   }
}
