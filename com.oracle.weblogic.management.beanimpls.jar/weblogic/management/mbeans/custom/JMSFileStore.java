package weblogic.management.mbeans.custom;

import javax.management.InvalidAttributeValueException;
import weblogic.management.DistributedManagementException;
import weblogic.management.configuration.FileStoreMBean;
import weblogic.management.configuration.JMSFileStoreMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;

public class JMSFileStore extends ConfigurationMBeanCustomizer {
   private transient FileStoreMBean newBean;
   private transient JMSServerMBean serverBean;
   private String directory;
   private String synchronousWritePolicy;

   public JMSFileStore(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public void setDelegatedBean(FileStoreMBean newBean) {
      this.newBean = newBean;
   }

   public FileStoreMBean getDelegatedBean() {
      return this.newBean;
   }

   public void setDelegatedJMSServer(JMSServerMBean server) {
      this.serverBean = server;
   }

   public JMSServerMBean getDelegatedJMSServer() {
      return this.serverBean;
   }

   public static void copy(JMSFileStoreMBean oldBean, FileStoreMBean newBean) {
      try {
         if (oldBean.getDirectory() != null) {
            newBean.setDirectory(oldBean.getDirectory());
         }

         if (oldBean.getSynchronousWritePolicy() != null) {
            newBean.setSynchronousWritePolicy(oldBean.getSynchronousWritePolicy());
         }
      } catch (InvalidAttributeValueException var3) {
      } catch (DistributedManagementException var4) {
      }

   }

   public void setDirectory(String directory) throws InvalidAttributeValueException {
      if (this.newBean != null) {
         this.newBean.setDirectory(directory);
      } else if (this.serverBean != null) {
         this.serverBean.setPagingDirectory(directory);
      } else {
         this.directory = directory;
      }

   }

   public String getDirectory() {
      if (this.newBean != null) {
         return this.newBean.getDirectory();
      } else {
         return this.serverBean != null ? this.serverBean.getPagingDirectory() : this.directory;
      }
   }

   public void setSynchronousWritePolicy(String policy) throws InvalidAttributeValueException, DistributedManagementException {
      if (this.newBean != null) {
         this.newBean.setSynchronousWritePolicy(policy);
      } else {
         this.synchronousWritePolicy = policy;
      }

   }

   public String getSynchronousWritePolicy() {
      return this.newBean != null ? this.newBean.getSynchronousWritePolicy() : this.synchronousWritePolicy;
   }
}
