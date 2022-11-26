package weblogic.management.mbeans.custom;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.diagnostics.descriptor.util.WLDFDescriptorLoader;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.internal.DescriptorInfoUtils;

public class WLDFSystemResource extends ConfigurationExtension {
   private static final String DEFAULT_CONFIG = "schema/diagnostics-binding.jar";
   private String _DescriptorFileName;

   public WLDFSystemResource(ConfigurationMBeanCustomized customized) {
      super(customized);
   }

   public String getDescriptorFileName() {
      return this._DescriptorFileName;
   }

   public void setDescriptorFileName(String descriptorFileName) {
      String prefix = this.getFileNamePrefix() + "diagnostics" + "/";
      if (descriptorFileName != null && this.isEdit() && this.isParentAvailable() && !(new File(descriptorFileName)).getPath().startsWith((new File(prefix)).getPath())) {
         this._DescriptorFileName = prefix + descriptorFileName;
      } else {
         this._DescriptorFileName = descriptorFileName;
      }

   }

   private boolean isParentAvailable() {
      ConfigurationMBean mbean = this.getMbean();
      return mbean != null && mbean.getParent() != null;
   }

   public DescriptorBean getResource() {
      return (DescriptorBean)this.getWLDFResource();
   }

   public WLDFResourceBean getWLDFResource() {
      WLDFResourceBean wldfBean = (WLDFResourceBean)this.getExtensionRoot(WLDFResourceBean.class, "WLDFResource", "diagnostics");
      if (wldfBean == null) {
         return null;
      } else {
         if (wldfBean.getName() == null) {
            wldfBean.setName(this.getMbean().getName());
         }

         return wldfBean;
      }
   }

   protected Descriptor loadDescriptor(DescriptorManager dm, InputStream in, List errorHolder) throws Exception {
      WLDFDescriptorLoader loader = new WLDFDescriptorLoader(in, dm, errorHolder);
      return loader.loadDescriptorBean().getDescriptor();
   }

   public void _postCreate() {
      this.getWLDFResource();
   }

   public void _preDestroy() {
      DescriptorBean bean = this.getMbean();
      DescriptorInfoUtils.removeDescriptorInfo((DescriptorBean)this.getWLDFResource(), bean.getDescriptor());
   }
}
