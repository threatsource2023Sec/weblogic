package com.oracle.weblogic.diagnostics.watch.actions;

import com.oracle.weblogic.diagnostics.utils.BeanInfoCopy;
import com.oracle.weblogic.diagnostics.utils.BeanInfoLocalizer;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;
import org.jvnet.hk2.annotations.Service;

@Service
@Singleton
public class ActionsManagerImpl implements ActionsManager {
   @Inject
   private ServiceLocator locator;

   public String[] getKnownActionTypes(Annotation... qualifiers) {
      Set matchingActionTypes = new HashSet();
      List matchingServiceHandles = this.locator.getAllServiceHandles(new ActionsFilter(qualifiers));
      Iterator var4 = matchingServiceHandles.iterator();

      while(var4.hasNext()) {
         ServiceHandle handle = (ServiceHandle)var4.next();
         matchingActionTypes.add(handle.getActiveDescriptor().getName());
      }

      return (String[])matchingActionTypes.toArray(new String[matchingActionTypes.size()]);
   }

   public ScopedActionsFactory createScopedActionsFactory(Annotation... qualifiers) {
      ScopedActionsFactoryImpl factoryImpl = new ScopedActionsFactoryImpl(qualifiers);
      this.locator.inject(factoryImpl);
      return factoryImpl;
   }

   public BeanInfo getActionInfo(String actionType, Locale l) {
      ActionConfigBean configBean = (ActionConfigBean)this.locator.getService(ActionConfigBean.class, actionType, new Annotation[0]);
      BeanInfo beanInfo = null;
      if (configBean != null) {
         try {
            BeanInfo _beanInfo = Introspector.getBeanInfo(configBean.getClass(), Object.class);
            beanInfo = new BeanInfoCopy(_beanInfo);
            beanInfo.getBeanDescriptor().setName(actionType);
            BeanInfoLocalizer.localize((BeanInfo)beanInfo, l);
         } catch (IntrospectionException var6) {
            throw new RuntimeException(var6);
         }
      }

      return beanInfo;
   }

   public Action getAction(String actionType, Annotation... qualifiers) {
      Action service = null;
      ServiceHandle handle = this.findServiceHandle(actionType, qualifiers);
      if (handle != null) {
         service = (Action)this.locator.getService(handle.getActiveDescriptor().getImplementationClass(), new Annotation[0]);
      }

      return service;
   }

   public ServiceHandle getActionServiceHandle(String actionType, Annotation... qualifiers) {
      ServiceHandle handle = this.findServiceHandle(actionType, qualifiers);
      return handle;
   }

   public ActionConfigBean getActionConfig(String actionType) {
      ActionConfigBean configBean = (ActionConfigBean)this.locator.getService(ActionConfigBean.class, actionType, new Annotation[0]);
      return configBean;
   }

   public void releaseAction(Action action) {
      Singleton singetonAnnotation = (Singleton)action.getClass().getAnnotation(Singleton.class);
      if (singetonAnnotation == null) {
         this.locator.preDestroy(action);
      }

   }

   private ServiceHandle findServiceHandle(String actionType, Annotation... qualifiers) {
      ServiceHandle handle = null;
      List matchingServiceHandles = this.locator.getAllServiceHandles(new ActionsFilter(actionType, qualifiers));
      if (matchingServiceHandles.size() >= 1) {
         handle = (ServiceHandle)matchingServiceHandles.iterator().next();
         this.locator.reifyDescriptor(handle.getActiveDescriptor());
      }

      return handle;
   }

   private static final class ActionsFilter implements Filter {
      private final Annotation[] qualifiers;
      private String actionType;

      private ActionsFilter(String type, Annotation[] qualifiers) {
         this.qualifiers = qualifiers;
         this.actionType = type;
      }

      public ActionsFilter(Annotation[] qualifiers) {
         this((String)null, qualifiers);
      }

      public boolean matches(Descriptor d) {
         return d.getAdvertisedContracts().contains(Action.class.getName()) && this.serviceNameMatches(d.getName()) ? this.matchAnyQualifiers(d, this.qualifiers) : false;
      }

      private boolean serviceNameMatches(String name) {
         return this.actionType == null || this.actionType.equals(name);
      }

      private boolean matchAnyQualifiers(Descriptor d, Annotation... qualifiers) {
         if (qualifiers != null && qualifiers.length != 0) {
            Set declaredQualifiers = d.getQualifiers();
            Annotation[] var4 = qualifiers;
            int var5 = qualifiers.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               Annotation qa = var4[var6];
               if (declaredQualifiers.contains(qa.annotationType().getName())) {
                  return true;
               }
            }

            return false;
         } else {
            return true;
         }
      }

      // $FF: synthetic method
      ActionsFilter(String x0, Annotation[] x1, Object x2) {
         this(x0, x1);
      }
   }
}
