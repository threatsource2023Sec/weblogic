package org.glassfish.tyrus.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.Decoder;
import javax.websocket.DeploymentException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

public class ComponentProviderService {
   private final Map sessionToObject;
   private final List providers;

   public static ComponentProviderService create() {
      List foundProviders = new ArrayList();
      ServiceFinder finder = ServiceFinder.find(ComponentProvider.class);
      Iterator var2 = finder.iterator();

      while(var2.hasNext()) {
         ComponentProvider componentProvider = (ComponentProvider)var2.next();
         foundProviders.add(componentProvider);
      }

      foundProviders.add(new DefaultComponentProvider());
      return new ComponentProviderService(Collections.unmodifiableList(foundProviders));
   }

   public static ComponentProviderService createClient() {
      return new ComponentProviderService(Collections.unmodifiableList(Arrays.asList(new DefaultComponentProvider())));
   }

   private ComponentProviderService(List providers) {
      this.providers = providers;
      this.sessionToObject = new ConcurrentHashMap();
   }

   public ComponentProviderService(ComponentProviderService componentProviderService) {
      this.providers = componentProviderService.providers;
      this.sessionToObject = componentProviderService.sessionToObject;
   }

   public Object getInstance(Class c, Session session, ErrorCollector collector) {
      Object loaded = null;
      Map classObjectMap = (Map)this.sessionToObject.get(session);

      try {
         if (classObjectMap != null) {
            synchronized(classObjectMap) {
               if (classObjectMap.containsKey(c)) {
                  loaded = classObjectMap.get(c);
               } else {
                  loaded = this.getEndpointInstance(c);
                  ((Map)this.sessionToObject.get(session)).put(c, loaded);
               }
            }
         } else {
            loaded = this.getEndpointInstance(c);
            HashMap hashMap = new HashMap();
            hashMap.put(c, loaded);
            this.sessionToObject.put(session, hashMap);
         }
      } catch (Exception var9) {
         collector.addException(new DeploymentException(LocalizationMessages.COMPONENT_PROVIDER_THREW_EXCEPTION(c.getName()), var9));
      }

      return loaded;
   }

   public Object getCoderInstance(Class c, Session session, EndpointConfig endpointConfig, ErrorCollector collector) {
      Object loaded = null;
      Map classObjectMap = (Map)this.sessionToObject.get(session);

      try {
         if (classObjectMap != null) {
            synchronized(classObjectMap) {
               if (classObjectMap.containsKey(c)) {
                  loaded = classObjectMap.get(c);
               } else {
                  loaded = this.getInstance(c);
                  if (loaded != null) {
                     if (loaded instanceof Encoder) {
                        ((Encoder)loaded).init(endpointConfig);
                     } else if (loaded instanceof Decoder) {
                        ((Decoder)loaded).init(endpointConfig);
                     }

                     ((Map)this.sessionToObject.get(session)).put(c, loaded);
                  }
               }
            }
         } else {
            loaded = this.getInstance(c);
            if (loaded != null) {
               if (loaded instanceof Encoder) {
                  ((Encoder)loaded).init(endpointConfig);
               } else if (loaded instanceof Decoder) {
                  ((Decoder)loaded).init(endpointConfig);
               }

               HashMap hashMap = new HashMap();
               hashMap.put(c, loaded);
               this.sessionToObject.put(session, hashMap);
            }
         }
      } catch (InstantiationException var10) {
         collector.addException(new DeploymentException(LocalizationMessages.COMPONENT_PROVIDER_THREW_EXCEPTION(c.getName()), var10));
      }

      return loaded;
   }

   public Method getInvocableMethod(Method method) {
      Iterator var2 = this.providers.iterator();

      ComponentProvider componentProvider;
      do {
         if (!var2.hasNext()) {
            return method;
         }

         componentProvider = (ComponentProvider)var2.next();
      } while(!componentProvider.isApplicable(method.getDeclaringClass()));

      return componentProvider.getInvocableMethod(method);
   }

   private Object getInstance(Class clazz) throws InstantiationException {
      Iterator var2 = this.providers.iterator();

      while(var2.hasNext()) {
         ComponentProvider componentProvider = (ComponentProvider)var2.next();
         if (componentProvider.isApplicable(clazz)) {
            Object t = componentProvider.create(clazz);
            if (t != null) {
               return t;
            }
         }
      }

      throw new InstantiationException(LocalizationMessages.COMPONENT_PROVIDER_NOT_FOUND(clazz.getName()));
   }

   public void removeSession(Session session) {
      Map classObjectMap = (Map)this.sessionToObject.get(session);
      if (classObjectMap != null) {
         synchronized(classObjectMap) {
            Iterator var4 = classObjectMap.values().iterator();

            while(var4.hasNext()) {
               Object o = var4.next();
               if (o instanceof Encoder) {
                  ((Encoder)o).destroy();
               } else if (o instanceof Decoder) {
                  ((Decoder)o).destroy();
               }

               Iterator var6 = this.providers.iterator();

               while(var6.hasNext()) {
                  ComponentProvider componentProvider = (ComponentProvider)var6.next();
                  if (componentProvider.destroy(o)) {
                     break;
                  }
               }
            }
         }
      }

      this.sessionToObject.remove(session);
   }

   public Object getEndpointInstance(Class endpointClass) throws InstantiationException {
      return this.getInstance(endpointClass);
   }
}
