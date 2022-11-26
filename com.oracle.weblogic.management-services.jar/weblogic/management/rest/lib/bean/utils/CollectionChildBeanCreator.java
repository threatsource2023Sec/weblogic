package weblogic.management.rest.lib.bean.utils;

public class CollectionChildBeanCreator extends ChildBeanCreator {
   public CollectionChildBeanCreator(InvocationContext ic, ContainedBeansType type) throws Exception {
      super(ic, type);
   }

   protected void deleteNewBean() throws Exception {
      BeanUtils.deleteCollectionChildBean(this.invocationContext().clone(this.getNewBean()), this.invocationContext().bean(), (ContainedBeansType)this.getType());
   }
}
