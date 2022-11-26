package org.jboss.weld.module.ejb;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJBException;
import org.jboss.weld.annotated.enhanced.MethodSignature;
import org.jboss.weld.annotated.enhanced.jlr.MethodSignatureImpl;
import org.jboss.weld.bean.proxy.Marker;
import org.jboss.weld.bean.proxy.MethodHandler;
import org.jboss.weld.ejb.api.SessionObjectReference;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.logging.SerializationLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.util.collections.ImmutableMap;
import org.jboss.weld.util.reflection.HierarchyDiscovery;
import org.jboss.weld.util.reflection.Reflections;

class EnterpriseBeanProxyMethodHandler implements MethodHandler, Serializable {
   private static final long serialVersionUID = 2107723373882153667L;
   private final BeanManagerImpl manager;
   private final BeanIdentifier beanId;
   private final SessionObjectReference reference;
   private final transient SessionBeanImpl bean;
   private final transient Map typeToBusinessInterfaceMap;

   EnterpriseBeanProxyMethodHandler(SessionBeanImpl bean) {
      this(bean, (SessionObjectReference)null);
   }

   private EnterpriseBeanProxyMethodHandler(SessionBeanImpl bean, SessionObjectReference reference) {
      this.bean = bean;
      this.manager = bean.getBeanManager();
      this.beanId = bean.getIdentifier();
      Map typeToBusinessInterfaceMap = new HashMap();
      this.discoverBusinessInterfaces(typeToBusinessInterfaceMap, bean.getEjbDescriptor().getRemoteBusinessInterfacesAsClasses());
      this.discoverBusinessInterfaces(typeToBusinessInterfaceMap, bean.getEjbDescriptor().getLocalBusinessInterfacesAsClasses());
      this.typeToBusinessInterfaceMap = ImmutableMap.copyOf(typeToBusinessInterfaceMap);
      if (reference == null) {
         this.reference = bean.createReference();
         BeanLogger.LOG.createdSessionBeanProxy(bean);
      } else {
         this.reference = reference;
         BeanLogger.LOG.activatedSessionBeanProxy(bean);
      }

   }

   public Object invoke(Object self, Method method, Method proceed, Object[] args) throws Throwable {
      if ("destroy".equals(method.getName()) && Marker.isMarker(0, method, args)) {
         if (this.bean.getEjbDescriptor().isStateful() && !this.reference.isRemoved()) {
            this.reference.remove();
         }

         return null;
      } else if (!this.bean.isClientCanCallRemoveMethods() && this.isRemoveMethod(method)) {
         throw BeanLogger.LOG.invalidRemoveMethodInvocation(method);
      } else {
         Class businessInterface = this.getBusinessInterface(method);
         if (this.reference.isRemoved() && this.isToStringMethod(method)) {
            return businessInterface.getName() + " [REMOVED]";
         } else {
            Object proxiedInstance = this.reference.getBusinessObject(businessInterface);
            if (!Modifier.isPublic(method.getModifiers())) {
               throw new EJBException("Not a business method " + method.toString() + ". Do not call non-public methods on EJB's.");
            } else {
               Object returnValue = Reflections.invokeAndUnwrap(proxiedInstance, method, args);
               BeanLogger.LOG.callProxiedMethod(method, proxiedInstance, args, returnValue);
               return returnValue;
            }
         }
      }
   }

   private boolean isRemoveMethod(Method method) {
      MethodSignature methodSignature = new MethodSignatureImpl(method);
      return this.bean.getEjbDescriptor().getRemoveMethodSignatures().contains(methodSignature);
   }

   private boolean isToStringMethod(Method method) {
      return "toString".equals(method.getName()) && method.getParameterTypes().length == 0;
   }

   private Class getBusinessInterface(Method method) {
      Class declaringClass = method.getDeclaringClass();
      Class businessInterface = null;
      if (declaringClass.equals(Object.class)) {
         businessInterface = this.bean.getEjbDescriptor().getObjectInterface();
      } else {
         businessInterface = (Class)this.typeToBusinessInterfaceMap.get(declaringClass);
      }

      if (businessInterface == null) {
         throw new RuntimeException("Unable to locate a business interface declaring " + method);
      } else {
         return businessInterface;
      }
   }

   private Object readResolve() throws ObjectStreamException {
      try {
         return new EnterpriseBeanProxyMethodHandler((SessionBeanImpl)this.manager.getPassivationCapableBean(this.beanId), this.reference);
      } catch (Exception var2) {
         throw SerializationLogger.LOG.unableToDeserialize(this.beanId, var2);
      }
   }

   private void discoverBusinessInterfaces(Map typeToBusinessInterfaceMap, Set businessInterfaces) {
      Iterator var3 = businessInterfaces.iterator();

      while(var3.hasNext()) {
         Class businessInterfaceClass = (Class)var3.next();
         Iterator var5 = HierarchyDiscovery.forNormalizedType(businessInterfaceClass).getTypeMap().keySet().iterator();

         while(var5.hasNext()) {
            Class type = (Class)var5.next();
            typeToBusinessInterfaceMap.put(type, businessInterfaceClass);
         }
      }

   }
}
