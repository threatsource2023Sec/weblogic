package org.jboss.weld.security;

import java.lang.reflect.InvocationTargetException;
import java.security.PrivilegedExceptionAction;

public class ConstructorNewInstanceAction extends AbstractGenericReflectionAction implements PrivilegedExceptionAction {
   private final Class[] constructorParamTypes;
   private final Object[] constructorParamInstances;

   public static ConstructorNewInstanceAction of(Class javaClass, Class[] constructorParamTypes, Object... constructorParamInstances) {
      return new ConstructorNewInstanceAction(javaClass, constructorParamTypes, constructorParamInstances);
   }

   public ConstructorNewInstanceAction(Class javaClass, Class[] constructorParamTypes, Object... constructorParamInstances) {
      super(javaClass);
      this.constructorParamTypes = (Class[])constructorParamTypes.clone();
      this.constructorParamInstances = constructorParamInstances;
   }

   public Object run() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
      return this.javaClass.getConstructor(this.constructorParamTypes).newInstance(this.constructorParamInstances);
   }
}
