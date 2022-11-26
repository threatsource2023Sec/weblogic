package weblogic.diagnostics.runtimecontrol;

import weblogic.diagnostics.descriptor.WLDFResourceBean;

public class BuiltinSRDescriptorBeanHolder {
   private static BuiltinSRDescriptorBeanHolder INSTANCE = new BuiltinSRDescriptorBeanHolder();
   private WLDFResourceBean descriptorBean = null;

   private BuiltinSRDescriptorBeanHolder() {
   }

   public static BuiltinSRDescriptorBeanHolder getInstance() {
      return INSTANCE;
   }

   public WLDFResourceBean getBuiltinSRDescriptorBean() {
      return this.descriptorBean;
   }

   public synchronized void setBuildinSRDescriptorBean(WLDFResourceBean resource) {
      this.descriptorBean = resource;
   }
}
