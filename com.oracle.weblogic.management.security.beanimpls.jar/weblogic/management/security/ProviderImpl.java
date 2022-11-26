package weblogic.management.security;

import javax.management.MBeanException;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.RequiredModelMBean;
import weblogic.descriptor.DescriptorBean;

public class ProviderImpl extends BaseMBeanImpl {
   public ProviderImpl(ModelMBean base) throws MBeanException {
      super(base);
   }

   protected ProviderImpl(RequiredModelMBean base) throws MBeanException {
      super(base);
   }

   private ProviderMBean getProvider() {
      try {
         return (ProviderMBean)this.getProxy();
      } catch (MBeanException var2) {
         throw new AssertionError(var2);
      }
   }

   public RealmMBean getRealm() {
      DescriptorBean parent = this.getProvider().getParentBean();
      return parent instanceof RealmMBean ? (RealmMBean)parent : null;
   }

   public String getCompatibilityObjectName() {
      String prefix = "Security:Name=";
      String result = prefix + this.getRealm().getName() + this.getProvider().getName();
      return result;
   }
}
