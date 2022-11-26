package org.jboss.weld.bootstrap.events;

import java.lang.reflect.Type;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.ProcessBean;
import org.jboss.weld.bean.ClassBean;
import org.jboss.weld.manager.BeanManagerImpl;

public abstract class AbstractProcessClassBean extends AbstractDefinitionContainerEvent implements ProcessBean {
   private final ClassBean bean;

   public AbstractProcessClassBean(BeanManagerImpl beanManager, Type rawType, Type[] actualTypeArguments, ClassBean bean) {
      super(beanManager, rawType, actualTypeArguments);
      this.bean = bean;
   }

   public Annotated getAnnotated() {
      this.checkWithinObserverNotification();
      return this.bean.getAnnotated();
   }

   public ClassBean getBean() {
      this.checkWithinObserverNotification();
      return this.bean;
   }
}
