package weblogic.management.rest.lib.bean.utils;

import java.beans.PropertyDescriptor;
import javax.servlet.http.HttpServletRequest;

class ContainedBeansTypeImpl extends ContainedBeanAttributeTypeImpl implements ContainedBeansType {
   private boolean isTaskRuntimeMBean;
   private MethodReference finder;
   private boolean ordered;
   private String createFormResourceName;

   ContainedBeansTypeImpl(BeanType beanType, PropertyDescriptor pd) throws Exception {
      super(beanType, pd);
      this.initDestroyer(new Class[]{this.getPropertyDescriptor().getPropertyType().getComponentType()});
      this.isTaskRuntimeMBean = TaskRuntimeMBeanUtils.isTaskRuntimeMBean(pd.getPropertyType());
      this.finder = new MethodReference(this.getBeanType(), DescriptorUtils.getFinder(this.getBeanType(), pd), new Class[]{getIdentityPropertyType(pd)});
      this.ordered = DescriptorUtils.isOrdered(pd);
      this.createFormResourceName = DescriptorUtils.getSingularRestName(pd) + "CreateForm";
   }

   public MethodType getFinder(HttpServletRequest request) throws Exception {
      return this.finder.getMethodType(request);
   }

   public boolean isOrdered() {
      return this.ordered;
   }

   public ResourceDef getResourceDef(String beanTree) throws Exception {
      return BeanResourceRegistry.instance().collectionResources().get(this.getBeanType().getName(), this.getName()).get(beanTree);
   }

   public ResourceDef getChildResourceDef(String beanTree) throws Exception {
      ResourceDefs defs = this.isTaskRuntimeMBean ? BeanResourceRegistry.instance().taskRuntimeCollectionChildResources().get(this.getBeanType().getName(), this.getName()) : BeanResourceRegistry.instance().collectionChildResources().get(this.getBeanType().getName(), this.getName());
      return defs.get(beanTree);
   }

   public ResourceDef getCreateFormResourceDef(String beanTree) throws Exception {
      return BeanResourceRegistry.instance().collectionChildCreateFormResources().get(this.getBeanType().getName(), this.getCreateFormResourceName()).get(beanTree);
   }

   public String getCreateFormResourceName() throws Exception {
      return this.createFormResourceName;
   }

   private static Class getIdentityPropertyType(PropertyDescriptor pd) throws Exception {
      return String.class;
   }
}
