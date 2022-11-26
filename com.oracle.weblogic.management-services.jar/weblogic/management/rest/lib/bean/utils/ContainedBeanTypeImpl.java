package weblogic.management.rest.lib.bean.utils;

import java.beans.PropertyDescriptor;
import javax.servlet.http.HttpServletRequest;

class ContainedBeanTypeImpl extends ContainedBeanAttributeTypeImpl implements ContainedBeanType {
   private boolean isTaskRuntimeMBean;
   private String createFormResourceName;
   private boolean ignore = false;
   private boolean noArgDestroyer = false;

   ContainedBeanTypeImpl(HttpServletRequest request, BeanType beanType, PropertyDescriptor pd) throws Exception {
      super(beanType, pd);
      this.initDestroyer(new Class[]{this.getPropertyDescriptor().getPropertyType()});
      if (this.getDestroyer(request) == null) {
         this.initDestroyer(new Class[0]);
         if (this.getDestroyer(request) != null) {
            this.noArgDestroyer = true;
         }
      }

      this.isTaskRuntimeMBean = TaskRuntimeMBeanUtils.isTaskRuntimeMBean(pd.getPropertyType());
      this.createFormResourceName = this.getName() + "CreateForm";
   }

   public ResourceDef getResourceDef(String beanTree) throws Exception {
      ResourceDefs defs = this.isTaskRuntimeMBean ? BeanResourceRegistry.instance().taskRuntimeSingletonChildResources().get(this.getBeanType().getName(), this.getName()) : BeanResourceRegistry.instance().singletonChildResources().get(this.getBeanType().getName(), this.getName());
      return defs.get(beanTree);
   }

   public ResourceDef getCreateFormResourceDef(String beanTree) throws Exception {
      return BeanResourceRegistry.instance().singletonChildCreateFormResources().get(this.getBeanType().getName(), this.getCreateFormResourceName()).get(beanTree);
   }

   public String getCreateFormResourceName() throws Exception {
      return this.createFormResourceName;
   }

   public boolean noArgDestroyer() throws Exception {
      return this.noArgDestroyer;
   }
}
