package weblogic.management.mbeans.custom;

import javax.management.InvalidAttributeValueException;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JDBCStoreMBean;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.configuration.JMSJDBCStoreMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;

public class JMSJDBCStore extends ConfigurationMBeanCustomizer {
   private transient JDBCStoreMBean newBean;
   private String prefixName;
   private String createTableDDLFile;

   public JMSJDBCStore(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public void setDelegatedBean(JDBCStoreMBean newBean) {
      this.newBean = newBean;
   }

   public static void copy(JMSJDBCStoreMBean oldBean, JDBCStoreMBean newBean) {
      try {
         if (oldBean.getPrefixName() != null) {
            newBean.setPrefixName(oldBean.getPrefixName());
         }

         if (oldBean.getCreateTableDDLFile() != null) {
            newBean.setCreateTableDDLFile(oldBean.getCreateTableDDLFile());
         }
      } catch (InvalidAttributeValueException var3) {
      }

   }

   public void setPrefixName(String name) throws InvalidAttributeValueException {
      if (this.newBean != null) {
         this.newBean.setPrefixName(name);
      } else {
         this.prefixName = name;
      }

   }

   public String getPrefixName() {
      return this.newBean != null ? this.newBean.getPrefixName() : this.prefixName;
   }

   public void setCreateTableDDLFile(String name) throws InvalidAttributeValueException {
      if (this.newBean != null) {
         this.newBean.setCreateTableDDLFile(name);
      } else {
         this.createTableDDLFile = name;
      }

   }

   public String getCreateTableDDLFile() {
      return this.newBean != null ? this.newBean.getCreateTableDDLFile() : this.createTableDDLFile;
   }

   private JDBCSystemResourceMBean lookupDataSource(DomainMBean root, String dsName) {
      JDBCSystemResourceMBean[] mbeans = root.getJDBCSystemResources();
      if (mbeans != null) {
         for(int lcv = 0; lcv < mbeans.length; ++lcv) {
            JDBCSystemResourceMBean mbean = mbeans[lcv];
            JDBCDataSourceBean dsBean = mbean.getJDBCResource();
            if (dsBean != null && dsName.equals(dsBean.getName())) {
               return mbean;
            }
         }
      }

      return null;
   }
}
