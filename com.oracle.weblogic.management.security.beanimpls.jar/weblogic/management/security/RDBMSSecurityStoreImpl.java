package weblogic.management.security;

import javax.management.MBeanException;
import javax.management.modelmbean.ModelMBean;
import weblogic.descriptor.DescriptorBean;

public class RDBMSSecurityStoreImpl extends BaseMBeanImpl {
   public RDBMSSecurityStoreImpl(ModelMBean base) throws MBeanException {
      super(base);
   }

   public RealmMBean getRealm() {
      DescriptorBean parent = this.getRDBMSSecurityStore().getParentBean();
      return parent instanceof RealmMBean ? (RealmMBean)parent : null;
   }

   public String getCompatibilityObjectName() {
      String prefix = "Security:Name=";
      return prefix + this.getRealm().getName() + this.getRDBMSSecurityStore().getName();
   }

   private RDBMSSecurityStoreMBean getRDBMSSecurityStore() {
      try {
         return (RDBMSSecurityStoreMBean)this.getProxy();
      } catch (MBeanException var2) {
         throw new AssertionError(var2);
      }
   }
}
