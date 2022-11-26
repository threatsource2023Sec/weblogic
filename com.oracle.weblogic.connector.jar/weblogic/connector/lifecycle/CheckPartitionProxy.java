package weblogic.connector.lifecycle;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import javax.enterprise.inject.spi.BeanManager;
import javax.resource.spi.ConnectionEventListener;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import weblogic.connector.utils.PartitionUtils;

public class CheckPartitionProxy implements InvocationHandler {
   private final String partitionName;
   private final Object target;

   private CheckPartitionProxy(Object target, String partitionName) {
      this.partitionName = partitionName;
      this.target = target;
   }

   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      PartitionUtils.checkPartition(this.partitionName);

      try {
         return method.invoke(this.target, args);
      } catch (InvocationTargetException var5) {
         throw var5.getCause();
      }
   }

   public static ConnectionEventListener wrapConnectionEventListener(ConnectionEventListener listener, String partitionName) {
      return (ConnectionEventListener)Proxy.newProxyInstance(ConnectionEventListener.class.getClassLoader(), new Class[]{ConnectionEventListener.class}, new CheckPartitionProxy(listener, partitionName));
   }

   public static BeanManager wrapBeanManager(BeanManager beanManager, String partitionName) {
      return (BeanManager)Proxy.newProxyInstance(BeanManager.class.getClassLoader(), new Class[]{BeanManager.class}, new CheckPartitionProxy(beanManager, partitionName));
   }

   public static ValidatorFactory wrapValidatorFactory(ValidatorFactory validatorFactory, String partitionName) {
      return (ValidatorFactory)Proxy.newProxyInstance(ValidatorFactory.class.getClassLoader(), new Class[]{ValidatorFactory.class}, new CheckPartitionProxy(validatorFactory, partitionName));
   }

   public static Validator wrapValidator(Validator validator, String partitionName) {
      return (Validator)Proxy.newProxyInstance(Validator.class.getClassLoader(), new Class[]{Validator.class}, new CheckPartitionProxy(validator, partitionName));
   }
}
