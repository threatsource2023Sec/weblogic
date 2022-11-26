package weblogic.servlet.internal.fragment;

import java.util.HashSet;
import java.util.Set;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.SecurityConstraintBean;
import weblogic.j2ee.descriptor.WebFragmentBean;

public class SecurityConstraintsMerger extends AbstractMerger {
   private Set cache = new HashSet();
   private static final String PROPERTY = "SecurityConstraint";

   public boolean accept(DescriptorBean bean, BeanUpdateEvent.PropertyUpdate update) {
      if (bean instanceof WebFragmentBean && "SecurityConstraint".equals(singularizeProperty(update.getPropertyName()))) {
         return true;
      } else {
         return bean.getParentBean() instanceof SecurityConstraintBean || bean instanceof SecurityConstraintBean;
      }
   }

   public void handleAddEvent(DescriptorBean targetBean, DescriptorBean sourceBean, DescriptorBean proposedBean, BeanUpdateEvent.PropertyUpdate update) throws MergeException {
      this.addSecurityConstraintBean(targetBean.getDescriptor().getRootBean(), this.findSecurityConstraintBean(proposedBean));
   }

   protected void handleChangeEvent(DescriptorBean targetBean, DescriptorBean sourceBean, DescriptorBean proposedBean, BeanUpdateEvent.PropertyUpdate update) throws MergeException {
      this.addSecurityConstraintBean(targetBean.getDescriptor().getRootBean(), this.findSecurityConstraintBean(proposedBean));
   }

   private void addSecurityConstraintBean(DescriptorBean baseBean, SecurityConstraintBean bean) {
      if (bean != null && !this.cache.contains(bean)) {
         addChildBean(baseBean, "SecurityConstraint", bean);
         this.cache.add(bean);
      }

   }

   private SecurityConstraintBean findSecurityConstraintBean(DescriptorBean bean) {
      if (bean == null) {
         return null;
      } else {
         return bean instanceof SecurityConstraintBean ? (SecurityConstraintBean)bean : this.findSecurityConstraintBean(bean.getParentBean());
      }
   }
}
