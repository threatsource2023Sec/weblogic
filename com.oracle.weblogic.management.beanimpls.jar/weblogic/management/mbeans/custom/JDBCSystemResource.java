package weblogic.management.mbeans.custom;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.jdbc.module.JDBCDeploymentHelper;
import weblogic.kernel.KernelStatus;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.internal.DescriptorInfoUtils;

public class JDBCSystemResource extends ConfigurationExtension {
   private String _DescriptorFileName;

   public JDBCSystemResource(ConfigurationMBeanCustomized customized) {
      super(customized);
   }

   public String getDescriptorFileName() {
      return this._DescriptorFileName;
   }

   public void setDescriptorFileName(String descriptorFileName) {
      String prefix = this.getFileNamePrefix();
      if (descriptorFileName != null && this.isEdit() && !(new File(descriptorFileName)).getPath().startsWith((new File(prefix)).getPath())) {
         this._DescriptorFileName = prefix + descriptorFileName;
      } else {
         this._DescriptorFileName = descriptorFileName;
      }

   }

   public DescriptorBean getResource() {
      return (DescriptorBean)this.getJDBCResource();
   }

   public JDBCDataSourceBean getJDBCResource() {
      return (JDBCDataSourceBean)this.getExtensionRoot(JDBCDataSourceBean.class, "JDBCResource", "jdbc");
   }

   public void _postCreate() {
      if (KernelStatus.isServer()) {
         this.getJDBCResource();
      }

   }

   public void _preDestroy() {
      DescriptorBean bean = this.getMbean();
      DescriptorInfoUtils.removeDescriptorInfo((DescriptorBean)this.getJDBCResource(), bean.getDescriptor());
   }

   protected Descriptor loadDescriptor(DescriptorManager dm, InputStream in, List holder) throws Exception {
      return ((DescriptorBean)(new JDBCDeploymentHelper(this.getJDBCSystemResourceMBean())).createJDBCDataSourceDescriptor(in, dm, holder, true)).getDescriptor();
   }

   private JDBCSystemResourceMBean getJDBCSystemResourceMBean() {
      if (!(this.getMbean() instanceof JDBCSystemResourceMBean)) {
         throw new IllegalArgumentException();
      } else {
         return (JDBCSystemResourceMBean)this.getMbean();
      }
   }
}
