package weblogic.application.custom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.ApplicationAccess;
import weblogic.application.Module;
import weblogic.application.internal.FlowContext;
import weblogic.application.internal.ModuleAttributes;
import weblogic.application.utils.EarUtils;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;

public class CustomizationManagerInternal extends CustomizationManager {
   private Map registry = new HashMap();

   public static CustomizationManagerInternal getInstance() {
      return instance;
   }

   public DescriptorLookup registerCustomDescriptor(ModuleType moduleType, String uri, Class rootBeanType, DescriptorRegistration reg) throws CustomizationException {
      ModuleTypeData mData;
      if (!this.registry.containsKey(moduleType)) {
         mData = new ModuleTypeData();
      } else {
         mData = (ModuleTypeData)this.registry.get(moduleType);
         if (mData.uris.containsKey(uri)) {
            throw new CustomizationException("Uri already registered : " + mData.uris.get(uri));
         }

         if (mData.rootBeanTypes.containsKey(rootBeanType)) {
            throw new CustomizationException("RootBenaType already registered : " + mData.rootBeanTypes.get(rootBeanType));
         }
      }

      reg.setUri(uri);
      reg.setRootBeanType(rootBeanType);
      mData.moduleRegistrations.add(reg);
      mData.uris.put(uri, reg);
      mData.rootBeanTypes.put(rootBeanType, reg);
      this.registry.put(moduleType, mData);
      return new DescriptorLookupAdapter(uri, rootBeanType);
   }

   public Set getRegisteredDescriptors(ModuleType moduleType) {
      return this.registry.containsKey(moduleType) ? ((ModuleTypeData)this.registry.get(moduleType)).uris.keySet() : null;
   }

   private class ModuleTypeData {
      List moduleRegistrations;
      Map uris;
      Map rootBeanTypes;

      private ModuleTypeData() {
         this.moduleRegistrations = new ArrayList();
         this.uris = new HashMap();
         this.rootBeanTypes = new HashMap();
      }

      // $FF: synthetic method
      ModuleTypeData(Object x1) {
         this();
      }
   }

   private class DescriptorLookupAdapter implements DescriptorLookup {
      private final String descriptorUri;
      private final Class rootBeanType;

      public DescriptorLookupAdapter(String descriptorUri, Class rootBeanType) {
         this.descriptorUri = descriptorUri;
         this.rootBeanType = rootBeanType;
      }

      public Descriptor get(String applicationId) {
         FlowContext ctx = (FlowContext)ApplicationAccess.getApplicationAccess().getApplicationContext(applicationId);
         ModuleAttributes attribs = ctx.getModuleAttributes(this.descriptorUri);
         if (attribs != null && attribs.getUnwrappedModule().getType().equals(WebLogicModuleType.CONFIG.toString())) {
            DescriptorBean[] beans = attribs.getUnwrappedModule().getDescriptors();
            if (beans != null && beans.length > 0) {
               return attribs.getUnwrappedModule().getDescriptors()[0].getDescriptor();
            }
         }

         return null;
      }

      public Descriptor get(String applicationId, String moduleUri) {
         FlowContext ctx = (FlowContext)ApplicationAccess.getApplicationAccess().getApplicationContext(applicationId);
         Module module = EarUtils.getModule(ctx, moduleUri);
         if (module != null) {
            DescriptorBean[] descriptorBeans = module.getDescriptors();
            if (descriptorBeans != null) {
               DescriptorBean[] var6 = descriptorBeans;
               int var7 = descriptorBeans.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  DescriptorBean descriptorBean = var6[var8];
                  if (this.rootBeanType.isInstance(descriptorBean)) {
                     return descriptorBean.getDescriptor();
                  }
               }
            }
         }

         return null;
      }
   }
}
