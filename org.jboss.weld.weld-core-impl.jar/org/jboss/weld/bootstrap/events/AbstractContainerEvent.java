package org.jboss.weld.bootstrap.events;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.reflection.ParameterizedTypeImpl;

public abstract class AbstractContainerEvent extends ContainerEvent {
   private final List errors = new ArrayList();
   private final BeanManagerImpl beanManager;
   private final Type[] actualTypeArguments;
   private final Type rawType;

   protected AbstractContainerEvent(BeanManagerImpl beanManager, Type rawType, Type[] actualTypeArguments) {
      this.beanManager = beanManager;
      this.actualTypeArguments = actualTypeArguments;
      this.rawType = rawType;
   }

   protected List getErrors() {
      return this.errors;
   }

   protected BeanManagerImpl getBeanManager() {
      return this.beanManager;
   }

   public void fire() {
      try {
         this.beanManager.getGlobalLenientObserverNotifier().fireEvent((Type)this.getEventType(), (Object)this);
      } catch (Exception var2) {
         this.getErrors().add(var2);
      }

   }

   protected Type getRawType() {
      return this.rawType;
   }

   protected Type[] getActualTypeArguments() {
      return this.actualTypeArguments;
   }

   public Type getEventType() {
      return new ParameterizedTypeImpl(this.getRawType(), this.getActualTypeArguments(), (Type)null);
   }
}
