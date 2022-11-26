package org.jboss.weld.bean.builtin.ee;

import java.io.Serializable;
import java.util.concurrent.Callable;
import org.jboss.weld.manager.BeanManagerImpl;

public abstract class AbstractEECallable implements Callable, Serializable {
   private static final long serialVersionUID = 2685728358029843185L;
   private final BeanManagerImpl beanManager;

   protected AbstractEECallable(BeanManagerImpl beanManager) {
      this.beanManager = beanManager;
   }

   public BeanManagerImpl getBeanManager() {
      return this.beanManager;
   }
}
