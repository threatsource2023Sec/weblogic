package com.sun.faces.cdi;

import com.sun.faces.push.WebsocketChannelManager;
import com.sun.faces.push.WebsocketSessionManager;
import com.sun.faces.push.WebsocketUserManager;
import com.sun.faces.util.FacesLogger;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessBean;
import javax.enterprise.inject.spi.ProcessManagedBean;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.ManagedProperty;
import javax.faces.model.FacesDataModel;

public class CdiExtension implements Extension {
   private Map forClassToDataModelClass = new HashMap();
   private Set managedPropertyTargetTypes = new HashSet();
   private boolean addBeansForJSFImplicitObjects;
   private static final Logger LOGGER;

   public void beforeBean(@Observes BeforeBeanDiscovery beforeBeanDiscovery, BeanManager beanManager) {
      beforeBeanDiscovery.addAnnotatedType(beanManager.createAnnotatedType(WebsocketUserManager.class), (String)null);
      beforeBeanDiscovery.addAnnotatedType(beanManager.createAnnotatedType(WebsocketSessionManager.class), (String)null);
      beforeBeanDiscovery.addAnnotatedType(beanManager.createAnnotatedType(WebsocketChannelManager.class), (String)null);
      beforeBeanDiscovery.addAnnotatedType(beanManager.createAnnotatedType(WebsocketChannelManager.ViewScope.class), (String)null);
      beforeBeanDiscovery.addAnnotatedType(beanManager.createAnnotatedType(InjectionPointGenerator.class), (String)null);
      beforeBeanDiscovery.addAnnotatedType(beanManager.createAnnotatedType(WebsocketPushContextProducer.class), (String)null);
   }

   public void afterBean(@Observes AfterBeanDiscovery afterBeanDiscovery, BeanManager beanManager) {
      if (this.addBeansForJSFImplicitObjects) {
         afterBeanDiscovery.addBean(new ApplicationProducer());
         afterBeanDiscovery.addBean(new ApplicationMapProducer());
         afterBeanDiscovery.addBean(new CompositeComponentProducer());
         afterBeanDiscovery.addBean(new ComponentProducer());
         afterBeanDiscovery.addBean(new FlashProducer());
         afterBeanDiscovery.addBean(new FlowMapProducer());
         afterBeanDiscovery.addBean(new HeaderMapProducer());
         afterBeanDiscovery.addBean(new HeaderValuesMapProducer());
         afterBeanDiscovery.addBean(new InitParameterMapProducer());
         afterBeanDiscovery.addBean(new RequestParameterMapProducer());
         afterBeanDiscovery.addBean(new RequestParameterValuesMapProducer());
         afterBeanDiscovery.addBean(new RequestProducer());
         afterBeanDiscovery.addBean(new RequestMapProducer());
         afterBeanDiscovery.addBean(new ResourceHandlerProducer());
         afterBeanDiscovery.addBean(new ExternalContextProducer());
         afterBeanDiscovery.addBean(new FacesContextProducer());
         afterBeanDiscovery.addBean(new RequestCookieMapProducer());
         afterBeanDiscovery.addBean(new SessionProducer());
         afterBeanDiscovery.addBean(new SessionMapProducer());
         afterBeanDiscovery.addBean(new ViewMapProducer());
         afterBeanDiscovery.addBean(new ViewProducer());
      }

      afterBeanDiscovery.addBean(new DataModelClassesMapProducer());
      Iterator var3 = this.managedPropertyTargetTypes.iterator();

      while(var3.hasNext()) {
         Type type = (Type)var3.next();
         afterBeanDiscovery.addBean(new ManagedPropertyProducer(type, beanManager));
      }

   }

   public void processBean(@Observes ProcessBean event, BeanManager beanManager) {
      Optional optionalFacesDataModel = CdiUtils.getAnnotation(beanManager, event.getAnnotated(), FacesDataModel.class);
      if (optionalFacesDataModel.isPresent()) {
         this.forClassToDataModelClass.put(((FacesDataModel)optionalFacesDataModel.get()).forClass(), event.getBean().getBeanClass());
      }

   }

   public void collect(@Observes ProcessManagedBean eventIn, BeanManager beanManager) {
      try {
         CdiUtils.getAnnotation(beanManager, eventIn.getAnnotated(), FacesConfig.class).ifPresent((config) -> {
            this.setAddBeansForJSFImplicitObjects(config.version().ordinal() >= FacesConfig.Version.JSF_2_3.ordinal());
         });
         Iterator var4 = eventIn.getAnnotatedBeanClass().getFields().iterator();

         while(true) {
            AnnotatedField field;
            do {
               do {
                  if (!var4.hasNext()) {
                     return;
                  }

                  field = (AnnotatedField)var4.next();
               } while(!field.isAnnotationPresent(ManagedProperty.class));
            } while(!(field.getBaseType() instanceof Class) && !(field.getBaseType() instanceof ParameterizedType));

            this.managedPropertyTargetTypes.add(field.getBaseType());
         }
      } catch (Exception var6) {
         if (LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.warning("Exception happened when collecting: " + var6);
         }
      }

   }

   public void afterDeploymentValidation(@Observes AfterDeploymentValidation event, BeanManager beanManager) {
      List sortedForDataModelClasses = new ArrayList();
      Iterator var4 = this.forClassToDataModelClass.keySet().iterator();

      while(var4.hasNext()) {
         Class clazz = (Class)var4.next();
         int highestSuper = -1;
         boolean added = false;

         for(int i = 0; i < sortedForDataModelClasses.size(); ++i) {
            if (((Class)sortedForDataModelClasses.get(i)).isAssignableFrom(clazz)) {
               sortedForDataModelClasses.add(i, clazz);
               added = true;
               break;
            }

            if (clazz.isAssignableFrom((Class)sortedForDataModelClasses.get(i))) {
               highestSuper = i;
            }
         }

         if (!added) {
            if (highestSuper > -1) {
               sortedForDataModelClasses.add(highestSuper + 1, clazz);
            } else {
               sortedForDataModelClasses.add(clazz);
            }
         }
      }

      Map linkedForClassToDataModelClass = new LinkedHashMap();
      Iterator var10 = sortedForDataModelClasses.iterator();

      while(var10.hasNext()) {
         Class sortedClass = (Class)var10.next();
         linkedForClassToDataModelClass.put(sortedClass, this.forClassToDataModelClass.get(sortedClass));
      }

      this.forClassToDataModelClass = Collections.unmodifiableMap(linkedForClassToDataModelClass);
   }

   public Map getForClassToDataModelClass() {
      return this.forClassToDataModelClass;
   }

   public boolean isAddBeansForJSFImplicitObjects() {
      return this.addBeansForJSFImplicitObjects;
   }

   public void setAddBeansForJSFImplicitObjects(boolean addBeansForJSFImplicitObjects) {
      this.addBeansForJSFImplicitObjects = addBeansForJSFImplicitObjects;
   }

   static {
      LOGGER = FacesLogger.APPLICATION_VIEW.getLogger();
   }
}
