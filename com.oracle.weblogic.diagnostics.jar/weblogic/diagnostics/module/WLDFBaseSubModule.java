package weblogic.diagnostics.module;

import weblogic.application.ApplicationContext;
import weblogic.diagnostics.descriptor.WLDFResourceBean;

public abstract class WLDFBaseSubModule implements WLDFSubModule {
   private String partitionName = "";
   private WLDFResourceBean wldfResouce;

   public void init(String pName, ApplicationContext appCtx, WLDFResourceBean wldfResourceBean) throws WLDFModuleException {
      this.partitionName = pName;
      this.wldfResouce = wldfResourceBean;
   }

   public WLDFResourceBean getWldfResouce() {
      return this.wldfResouce;
   }

   public String getPartitionName() {
      return this.partitionName;
   }
}
