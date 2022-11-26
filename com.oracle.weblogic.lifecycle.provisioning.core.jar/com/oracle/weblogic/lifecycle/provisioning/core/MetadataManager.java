package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningEvent;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.Component;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.Handler;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.HandlesResources;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.messaging.SubscribeTo;

/** @deprecated */
@Deprecated
@Singleton
public final class MetadataManager {
   @Inject
   ServiceLocator serviceLocator;
   private AtomicBoolean initializedComponentNames = new AtomicBoolean(false);
   private AtomicBoolean initializedHandlerInfo = new AtomicBoolean(false);
   private Set componentNames = new HashSet();
   private ConcurrentHashMap fileName2EventAnnotation = new ConcurrentHashMap();

   public Set getAvailableComponentNames() {
      if (!this.initializedComponentNames.get()) {
         synchronized(this.initializedComponentNames) {
            if (!this.initializedComponentNames.get()) {
               Iterator var2 = this.serviceLocator.getAllServiceHandles(Component.class, new Annotation[0]).iterator();

               while(var2.hasNext()) {
                  ServiceHandle handle = (ServiceHandle)var2.next();
                  Iterator var4 = handle.getActiveDescriptor().getQualifierAnnotations().iterator();

                  while(var4.hasNext()) {
                     Annotation ann = (Annotation)var4.next();
                     if (ann != null && Component.class.isAssignableFrom(ann.getClass())) {
                        this.componentNames.add(((Component)ann).value());
                     }
                  }
               }
            }
         }
      }

      return Collections.unmodifiableSet(this.componentNames);
   }

   public Annotation getEventAnnotationForPath(String path) {
      if (!this.initializedHandlerInfo.get()) {
         this.loadAllAvailableHandlerNames();
      }

      return (Annotation)this.fileName2EventAnnotation.get(path);
   }

   private synchronized void loadAllAvailableHandlerNames() {
      if (!this.initializedHandlerInfo.get()) {
         Iterator var1 = this.serviceLocator.getAllServiceHandles(Handler.class, new Annotation[0]).iterator();

         label54:
         while(var1.hasNext()) {
            ServiceHandle handle = (ServiceHandle)var1.next();
            Iterator var3 = handle.getActiveDescriptor().getQualifierAnnotations().iterator();

            while(true) {
               Annotation ann;
               do {
                  do {
                     if (!var3.hasNext()) {
                        continue label54;
                     }

                     ann = (Annotation)var3.next();
                  } while(ann == null);
               } while(!Component.class.isAssignableFrom(ann.getClass()));

               String componentName = ((Component)ann).value();
               Class handlerClazz = handle.getActiveDescriptor().getImplementationClass();
               Method[] var7 = handlerClazz.getMethods();
               int var8 = var7.length;

               for(int var9 = 0; var9 < var8; ++var9) {
                  Method method = var7[var9];
                  if (method.getDeclaringClass() != Object.class) {
                     int modifier = method.getModifiers();
                     if (Modifier.isPublic(modifier) && !Modifier.isAbstract(modifier) && !Modifier.isInterface(modifier) && !Modifier.isNative(modifier) && !Modifier.isStatic(modifier)) {
                        this.identifyHandlesResourcesIfPresent(method);
                     }
                  }
               }
            }
         }
      }

   }

   private void identifyHandlesResourcesIfPresent(Method method) {
      Class[] paramTypes = method.getParameterTypes();
      boolean hasSubscribeToAnnotation = false;
      Annotation eventAnn = null;
      HandlesResources handlesResources = null;
      int var7;
      int var8;
      if (paramTypes != null && paramTypes.length == 1 && paramTypes[0].isAssignableFrom(ProvisioningEvent.class)) {
         Annotation[][] var6 = method.getParameterAnnotations();
         var7 = var6.length;

         for(var8 = 0; var8 < var7; ++var8) {
            Annotation[] annArr = var6[var8];
            Annotation[] var10 = annArr;
            int var11 = annArr.length;

            for(int var12 = 0; var12 < var11; ++var12) {
               Annotation paramAnn = var10[var12];
               if (paramAnn instanceof SubscribeTo) {
                  hasSubscribeToAnnotation = true;
               } else {
                  handlesResources = (HandlesResources)paramAnn.annotationType().getAnnotation(HandlesResources.class);
                  if (handlesResources != null) {
                     eventAnn = paramAnn;
                  }
               }
            }
         }
      }

      if (handlesResources != null) {
         String[] var14 = handlesResources.value();
         var7 = var14.length;

         for(var8 = 0; var8 < var7; ++var8) {
            String fileName = var14[var8];
            if (this.fileName2EventAnnotation.containsKey(fileName)) {
               Annotation existingAnn = (Annotation)this.fileName2EventAnnotation.get(fileName);
               if (!existingAnn.annotationType().getName().equals(eventAnn.annotationType().getName())) {
                  throw new IllegalStateException("File name: " + fileName + " is already handled by: " + ((Annotation)this.fileName2EventAnnotation.get(fileName)).annotationType().getName());
               }
            }

            this.fileName2EventAnnotation.put(fileName, eventAnn);
         }
      }

   }
}
