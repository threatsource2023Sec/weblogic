package org.jboss.weld.bean.proxy;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.Container;
import org.jboss.weld.bean.RIBean;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.serialization.spi.ContextualStore;
import org.jboss.weld.util.Proxies;
import org.jboss.weld.util.cache.ComputingCache;
import org.jboss.weld.util.cache.ComputingCacheBuilder;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Reflections;

public class ClientProxyProvider {
   private static final Object BEAN_NOT_PROXYABLE_MARKER = new Object();
   private final ComputingCache beanTypeClosureProxyPool;
   private final ComputingCache requestedTypeClosureProxyPool;
   private final String contextId;
   private volatile ServiceRegistry services;

   public ClientProxyProvider(String contextId) {
      ComputingCacheBuilder cacheBuilder = ComputingCacheBuilder.newBuilder();
      this.beanTypeClosureProxyPool = cacheBuilder.build(new CreateClientProxy());
      this.requestedTypeClosureProxyPool = cacheBuilder.build(new CreateClientProxyForType());
      this.contextId = contextId;
   }

   private ServiceRegistry services() {
      if (this.services == null) {
         synchronized(this) {
            if (this.services == null) {
               this.services = Container.instance(this.contextId).services();
            }
         }
      }

      return this.services;
   }

   private Object createClientProxy(Bean bean) throws RuntimeException {
      return this.createClientProxy(bean, bean.getTypes());
   }

   private Object createClientProxy(Bean bean, Set types) {
      BeanIdentifier id = ((ContextualStore)Container.instance(this.contextId).services().get(ContextualStore.class)).putIfAbsent(bean);
      if (id == null) {
         throw BeanLogger.LOG.beanIdCreationFailed(bean);
      } else {
         BeanInstance beanInstance = new ContextBeanInstance(bean, id, this.contextId);
         Proxies.TypeInfo typeInfo = Proxies.TypeInfo.of(types);
         Object proxy = (new ClientProxyFactory(this.contextId, typeInfo.getSuperClass(), types, bean)).create(beanInstance);
         BeanLogger.LOG.createdNewClientProxyType(proxy.getClass(), bean, id);
         return proxy;
      }
   }

   public Object getClientProxy(Bean bean) {
      Object proxy = this.beanTypeClosureProxyPool.getCastValue(bean);
      if (proxy == BEAN_NOT_PROXYABLE_MARKER) {
         throw Proxies.getUnproxyableTypesException(bean, this.services());
      } else {
         BeanLogger.LOG.lookedUpClientProxy(proxy.getClass(), bean);
         return proxy;
      }
   }

   public Object getClientProxy(Bean bean, Type requestedType) {
      Object proxy = this.beanTypeClosureProxyPool.getCastValue(bean);
      if (proxy == BEAN_NOT_PROXYABLE_MARKER) {
         proxy = this.requestedTypeClosureProxyPool.getCastValue(new RequestedTypeHolder(requestedType, bean));
         if (proxy == BEAN_NOT_PROXYABLE_MARKER) {
            throw Proxies.getUnproxyableTypeException(requestedType, this.services());
         }
      }

      BeanLogger.LOG.lookedUpClientProxy(proxy.getClass(), bean);
      return proxy;
   }

   public String toString() {
      return "Proxy pool with " + this.beanTypeClosureProxyPool.size() + " bean type proxies and " + this.requestedTypeClosureProxyPool.size() + "injection point type proxies.";
   }

   public void clear() {
      this.beanTypeClosureProxyPool.clear();
      this.requestedTypeClosureProxyPool.clear();
   }

   private static class RequestedTypeHolder {
      private final Type requestedType;
      private final Bean bean;

      private RequestedTypeHolder(Type requestedType, Bean bean) {
         this.requestedType = requestedType;
         this.bean = bean;
      }

      public int hashCode() {
         int prime = true;
         int result = 1;
         result = 31 * result + (this.bean == null ? 0 : this.bean.hashCode());
         result = 31 * result + (this.requestedType == null ? 0 : this.requestedType.hashCode());
         return result;
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (obj == null) {
            return false;
         } else if (this.getClass() != obj.getClass()) {
            return false;
         } else {
            RequestedTypeHolder other = (RequestedTypeHolder)obj;
            if (this.bean == null) {
               if (other.bean != null) {
                  return false;
               }
            } else if (!this.bean.equals(other.bean)) {
               return false;
            }

            if (this.requestedType == null) {
               if (other.requestedType != null) {
                  return false;
               }
            } else if (!this.requestedType.equals(other.requestedType)) {
               return false;
            }

            return true;
         }
      }

      // $FF: synthetic method
      RequestedTypeHolder(Type x0, Bean x1, Object x2) {
         this(x0, x1);
      }
   }

   private class CreateClientProxyForType implements Function {
      private CreateClientProxyForType() {
      }

      public Object apply(RequestedTypeHolder input) {
         ImmutableSet.Builder types = ImmutableSet.builder();
         Iterator var3 = input.bean.getTypes().iterator();

         while(var3.hasNext()) {
            Type type = (Type)var3.next();
            if (Reflections.getRawType(type).isInterface()) {
               types.add(type);
            }
         }

         Class requestedRawType;
         if (input.requestedType.equals(Object.class)) {
            if (input.bean instanceof RIBean) {
               RIBean riBean = (RIBean)input.bean;
               requestedRawType = riBean.getType();
            } else {
               requestedRawType = input.bean.getBeanClass();
            }

            if (Proxies.isTypeProxyable(requestedRawType, ClientProxyProvider.this.services())) {
               return ClientProxyProvider.this.createClientProxy(input.bean, types.add(requestedRawType).build());
            }
         }

         if (Proxies.isTypeProxyable(input.requestedType, ClientProxyProvider.this.services())) {
            return ClientProxyProvider.this.createClientProxy(input.bean, types.add(input.requestedType).build());
         } else {
            requestedRawType = Reflections.getRawType(input.requestedType);
            Iterator var8 = input.bean.getTypes().iterator();

            Type typex;
            do {
               if (!var8.hasNext()) {
                  return ClientProxyProvider.BEAN_NOT_PROXYABLE_MARKER;
               }

               typex = (Type)var8.next();
            } while(!requestedRawType.isAssignableFrom(Reflections.getRawType(typex)) || !Proxies.isTypeProxyable(typex, ClientProxyProvider.this.services()));

            return ClientProxyProvider.this.createClientProxy(input.bean, types.add(typex).build());
         }
      }

      // $FF: synthetic method
      CreateClientProxyForType(Object x1) {
         this();
      }
   }

   private class CreateClientProxy implements Function {
      private CreateClientProxy() {
      }

      public Object apply(Bean from) {
         return Proxies.isTypesProxyable(from, ClientProxyProvider.this.services()) ? ClientProxyProvider.this.createClientProxy(from) : ClientProxyProvider.BEAN_NOT_PROXYABLE_MARKER;
      }

      // $FF: synthetic method
      CreateClientProxy(Object x1) {
         this();
      }
   }
}
