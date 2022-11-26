package weblogic.management.mbeans.custom;

import java.io.File;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.jms.module.JMSParser;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.internal.DescriptorInfoUtils;

public class JMSSystemResource extends ConfigurationExtension {
   private String _DescriptorFileName;
   private static final String FILE_SEP = "/";

   public JMSSystemResource(ConfigurationMBeanCustomized customized) {
      super(customized);
   }

   public String getDescriptorFileName() {
      return this._DescriptorFileName;
   }

   private boolean descriptorFilePathContainJmsDirectory(String descriptorFileName) {
      Path path = FileSystems.getDefault().getPath(descriptorFileName);
      Iterator var3 = path.iterator();

      Path pi;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         pi = (Path)var3.next();
      } while(!pi.equals(FileSystems.getDefault().getPath("jms")));

      return true;
   }

   public void setDescriptorFileName(String descriptorFileName) {
      String prefix = this.getFileNamePrefix();
      if (prefix == "") {
         prefix = "jms/";
         if (descriptorFileName != null && this.isEdit() && !this.descriptorFilePathContainJmsDirectory(descriptorFileName)) {
            this._DescriptorFileName = prefix + descriptorFileName;
         } else {
            this._DescriptorFileName = descriptorFileName;
         }
      } else if (descriptorFileName != null && this.isEdit() && !(new File(descriptorFileName)).getPath().startsWith((new File(prefix)).getPath())) {
         this._DescriptorFileName = prefix + descriptorFileName;
      } else {
         this._DescriptorFileName = descriptorFileName;
      }

   }

   public DescriptorBean getResource() {
      return (DescriptorBean)this.getJMSResource();
   }

   public JMSBean getJMSResource() {
      return (JMSBean)this.getExtensionRoot(JMSBean.class, "JMSResource", "jms");
   }

   public void _postCreate() {
      this.getJMSResource();
   }

   public void _preDestroy() {
      DescriptorBean bean = this.getMbean();
      DescriptorInfoUtils.removeDescriptorInfo((DescriptorBean)this.getJMSResource(), bean.getDescriptor());
   }

   protected Descriptor loadDescriptor(DescriptorManager dm, InputStream in, List holder) throws Exception {
      return ((DescriptorBean)JMSParser.createJMSDescriptor(in, dm, holder, true)).getDescriptor();
   }
}
