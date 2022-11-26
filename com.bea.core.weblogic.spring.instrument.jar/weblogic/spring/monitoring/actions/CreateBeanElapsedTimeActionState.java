package weblogic.spring.monitoring.actions;

import weblogic.spring.monitoring.utils.AbstractBeanDefinitionDelegator;

public class CreateBeanElapsedTimeActionState extends ElapsedTimeActionState {
   private boolean isSingleton;
   private boolean isPrototype;
   private String scopeName;

   public void setAbstractBeanDefinition(Object abstractBeanDefinition) {
      AbstractBeanDefinitionDelegator delegator = new AbstractBeanDefinitionDelegator(abstractBeanDefinition);
      this.isSingleton = delegator.isSingleton();
      this.isPrototype = delegator.isPrototype();
      this.scopeName = delegator.getScope();
   }

   public boolean isSingleton() {
      return this.isSingleton;
   }

   public boolean isPrototype() {
      return this.isPrototype;
   }

   public String getScopeName() {
      return this.scopeName;
   }
}
