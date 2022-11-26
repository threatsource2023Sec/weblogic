package weblogic.application.internal;

import weblogic.application.CustomModuleContext;
import weblogic.j2ee.descriptor.wl.ModuleProviderBean;

public class CustomModuleContextImpl implements CustomModuleContext {
   private final String parentModuleUri;
   private final String parentModuleId;
   private final ModuleProviderBean moduleProviderBean;

   public CustomModuleContextImpl(ModuleProviderBean moduleProviderBean) {
      this(moduleProviderBean, (String)null, (String)null);
   }

   public CustomModuleContextImpl(ModuleProviderBean moduleProviderBean, String parentModuleId, String parentModuleUri) {
      this.moduleProviderBean = moduleProviderBean;
      this.parentModuleId = parentModuleId;
      this.parentModuleUri = parentModuleUri;
   }

   public ModuleProviderBean getModuleProviderBean() {
      return this.moduleProviderBean;
   }

   public String getParentModuleId() {
      return this.parentModuleId;
   }

   public String getParentModuleUri() {
      return this.parentModuleUri;
   }
}
