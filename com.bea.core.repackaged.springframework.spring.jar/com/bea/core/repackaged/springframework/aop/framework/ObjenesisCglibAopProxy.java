package com.bea.core.repackaged.springframework.aop.framework;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.cglib.proxy.Callback;
import com.bea.core.repackaged.springframework.cglib.proxy.Enhancer;
import com.bea.core.repackaged.springframework.cglib.proxy.Factory;
import com.bea.core.repackaged.springframework.objenesis.SpringObjenesis;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.lang.reflect.Constructor;

class ObjenesisCglibAopProxy extends CglibAopProxy {
   private static final Log logger = LogFactory.getLog(ObjenesisCglibAopProxy.class);
   private static final SpringObjenesis objenesis = new SpringObjenesis();

   public ObjenesisCglibAopProxy(AdvisedSupport config) {
      super(config);
   }

   protected Object createProxyClassAndInstance(Enhancer enhancer, Callback[] callbacks) {
      Class proxyClass = enhancer.createClass();
      Object proxyInstance = null;
      if (objenesis.isWorthTrying()) {
         try {
            proxyInstance = objenesis.newInstance(proxyClass, enhancer.getUseCache());
         } catch (Throwable var7) {
            logger.debug("Unable to instantiate proxy using Objenesis, falling back to regular proxy construction", var7);
         }
      }

      if (proxyInstance == null) {
         try {
            Constructor ctor = this.constructorArgs != null ? proxyClass.getDeclaredConstructor(this.constructorArgTypes) : proxyClass.getDeclaredConstructor();
            ReflectionUtils.makeAccessible(ctor);
            proxyInstance = this.constructorArgs != null ? ctor.newInstance(this.constructorArgs) : ctor.newInstance();
         } catch (Throwable var6) {
            throw new AopConfigException("Unable to instantiate proxy using Objenesis, and regular proxy instantiation via default constructor fails as well", var6);
         }
      }

      ((Factory)proxyInstance).setCallbacks(callbacks);
      return proxyInstance;
   }
}
