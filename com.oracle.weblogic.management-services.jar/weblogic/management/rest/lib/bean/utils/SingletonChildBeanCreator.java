package weblogic.management.rest.lib.bean.utils;

public class SingletonChildBeanCreator extends ChildBeanCreator {
   public SingletonChildBeanCreator(InvocationContext ic, ContainedBeanType type) throws Exception {
      super(ic, type);
   }

   protected void deleteNewBean() throws Exception {
      BeanUtils.deleteSingletonChildBean(this.invocationContext().clone(this.getNewBean()), this.invocationContext().bean(), (ContainedBeanType)this.getType());
   }
}
