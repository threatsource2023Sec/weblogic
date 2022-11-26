package com.sun.faces.application.applicationimpl;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.application.ConverterPropertyEditorFactory;
import com.sun.faces.application.ViewMemberInstanceFactoryMetadataMap;
import com.sun.faces.cdi.CdiUtils;
import com.sun.faces.config.WebConfiguration;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.ReflectionUtils;
import com.sun.faces.util.Util;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.Resource;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;
import javax.faces.el.ValueBinding;
import javax.faces.render.RenderKit;
import javax.faces.render.Renderer;
import javax.faces.validator.Validator;
import javax.faces.view.ViewDeclarationLanguage;

public class InstanceFactory {
   private static final Logger LOGGER;
   private static final String CONTEXT = "context";
   private static final String COMPONENT_EXPRESSION = "componentExpression";
   private static final String COMPONENT_TYPE = "componentType";
   private static final String COMPONENT_CLASS = "componentClass";
   private static final Map STANDARD_CONV_ID_TO_TYPE_MAP;
   private static final Map STANDARD_TYPE_TO_CONV_ID_MAP;
   private final String[] STANDARD_BY_TYPE_CONVERTER_CLASSES = new String[]{"java.math.BigDecimal", "java.lang.Boolean", "java.lang.Byte", "java.lang.Character", "java.lang.Double", "java.lang.Float", "java.lang.Integer", "java.lang.Long", "java.lang.Short", "java.lang.Enum"};
   private Map converterTypeMap;
   private boolean registerPropertyEditors;
   private boolean passDefaultTimeZone;
   private TimeZone systemTimeZone;
   private ViewMemberInstanceFactoryMetadataMap componentMap;
   private ViewMemberInstanceFactoryMetadataMap behaviorMap;
   private ViewMemberInstanceFactoryMetadataMap converterIdMap;
   private ViewMemberInstanceFactoryMetadataMap validatorMap;
   private Set defaultValidatorIds;
   private volatile Map defaultValidatorInfo;
   private final ApplicationAssociate associate;
   private Version version;
   private BeanManager beanManager;

   public InstanceFactory(ApplicationAssociate applicationAssociate) {
      this.associate = applicationAssociate;
      this.version = new Version();
      this.componentMap = new ViewMemberInstanceFactoryMetadataMap(new ConcurrentHashMap());
      this.converterIdMap = new ViewMemberInstanceFactoryMetadataMap(new ConcurrentHashMap());
      this.converterTypeMap = new ConcurrentHashMap();
      this.validatorMap = new ViewMemberInstanceFactoryMetadataMap(new ConcurrentHashMap());
      this.defaultValidatorIds = new LinkedHashSet();
      this.behaviorMap = new ViewMemberInstanceFactoryMetadataMap(new ConcurrentHashMap());
      WebConfiguration webConfig = WebConfiguration.getInstance(FacesContext.getCurrentInstance().getExternalContext());
      this.registerPropertyEditors = webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.RegisterConverterPropertyEditors);
      this.passDefaultTimeZone = webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.DateTimeConverterUsesSystemTimezone);
      if (this.passDefaultTimeZone) {
         this.systemTimeZone = TimeZone.getDefault();
      }

   }

   public void addComponent(String componentType, String componentClass) {
      Util.notNull("componentType", componentType);
      Util.notNull("componentClass", componentClass);
      if (LOGGER.isLoggable(Level.FINE) && this.componentMap.containsKey(componentType)) {
         LOGGER.log(Level.FINE, "componentType {0} has already been registered.  Replacing existing component class type {1} with {2}.", new Object[]{componentType, this.componentMap.get(componentType), componentClass});
      }

      this.componentMap.put(componentType, componentClass);
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.fine(MessageFormat.format("added component of type ''{0}'' and class ''{1}''", componentType, componentClass));
      }

   }

   public UIComponent createComponent(ValueExpression componentExpression, FacesContext context, String componentType) throws FacesException {
      Util.notNull("componentExpression", componentExpression);
      Util.notNull("context", context);
      Util.notNull("componentType", componentType);
      return this.createComponentApplyAnnotations(context, componentExpression, componentType, (String)null, true);
   }

   public UIComponent createComponent(String componentType) throws FacesException {
      Util.notNull("componentType", componentType);
      return this.createComponentApplyAnnotations(FacesContext.getCurrentInstance(), componentType, (String)null, true);
   }

   public UIComponent createComponent(FacesContext context, Resource componentResource, ExpressionFactory expressionFactory) throws FacesException {
      Util.notNull("context", context);
      Util.notNull("componentResource", componentResource);
      UIComponent result = null;
      Application app = context.getApplication();
      ViewDeclarationLanguage vdl = app.getViewHandler().getViewDeclarationLanguage(context, context.getViewRoot().getViewId());
      BeanInfo componentMetadata = vdl.getComponentMetadata(context, componentResource);
      if (componentMetadata != null) {
         BeanDescriptor componentBeanDescriptor = componentMetadata.getBeanDescriptor();
         ValueExpression valueExpression = (ValueExpression)componentBeanDescriptor.getValue("javax.faces.component.COMPOSITE_COMPONENT_TYPE");
         if (valueExpression != null) {
            String componentType = (String)valueExpression.getValue(context.getELContext());
            if (!Util.isEmpty(componentType)) {
               result = app.createComponent(componentType);
            }
         }
      }

      if (result == null) {
         Resource scriptComponentResource = vdl.getScriptComponentResource(context, componentResource);
         if (scriptComponentResource != null) {
            result = this.createComponentFromScriptResource(context, scriptComponentResource, componentResource);
         }
      }

      if (result == null) {
         String packageName = componentResource.getLibraryName();
         String className = componentResource.getResourceName();
         className = packageName + '.' + className.substring(0, className.lastIndexOf(46));

         try {
            Class clazz = (Class)this.componentMap.get(className);
            if (clazz == null) {
               clazz = Util.loadClass(className, this);
            }

            if (clazz != ComponentResourceClassNotFound.class) {
               if (!this.associate.isDevModeEnabled()) {
                  this.componentMap.put(className, clazz);
               }

               result = (UIComponent)clazz.newInstance();
            }
         } catch (ClassNotFoundException var11) {
            if (!this.associate.isDevModeEnabled()) {
               this.componentMap.put(className, ComponentResourceClassNotFound.class);
            }
         } catch (IllegalAccessException | ClassCastException | InstantiationException var12) {
            throw new FacesException(var12);
         }
      }

      if (result == null) {
         result = app.createComponent("javax.faces.NamingContainer");
      }

      result.setRendererType("javax.faces.Composite");
      Map attrs = result.getAttributes();
      attrs.put("javax.faces.application.Resource.ComponentResource", componentResource);
      attrs.put("javax.faces.component.BEANINFO_KEY", componentMetadata);
      this.associate.getAnnotationManager().applyComponentAnnotations(context, result);
      this.pushDeclaredDefaultValuesToAttributesMap(context, componentMetadata, attrs, result, expressionFactory);
      return result;
   }

   public UIComponent createComponent(FacesContext context, String componentType, String rendererType) {
      Util.notNull("context", context);
      Util.notNull("componentType", componentType);
      return this.createComponentApplyAnnotations(context, componentType, rendererType, true);
   }

   public UIComponent createComponent(ValueExpression componentExpression, FacesContext context, String componentType, String rendererType) {
      Util.notNull("componentExpression", componentExpression);
      Util.notNull("context", context);
      Util.notNull("componentType", componentType);
      return this.createComponentApplyAnnotations(context, componentExpression, componentType, rendererType, true);
   }

   public UIComponent createComponent(ValueBinding componentBinding, FacesContext context, String componentType) throws FacesException {
      Util.notNull("componentBinding", componentBinding);
      Util.notNull("context", context);
      Util.notNull("componentType", componentType);
      boolean createOne = false;

      Object result;
      try {
         result = componentBinding.getValue(context);
         if (result != null) {
            createOne = !(result instanceof UIComponent);
         }

         if (result == null || createOne) {
            result = this.createComponentApplyAnnotations(context, componentType, (String)null, false);
            componentBinding.setValue(context, result);
         }
      } catch (Exception var7) {
         throw new FacesException(var7);
      }

      return (UIComponent)result;
   }

   public Iterator getComponentTypes() {
      return this.componentMap.keySet().iterator();
   }

   public void addBehavior(String behaviorId, String behaviorClass) {
      Util.notNull("behaviorId", behaviorId);
      Util.notNull("behaviorClass", behaviorClass);
      if (LOGGER.isLoggable(Level.FINE) && this.behaviorMap.containsKey(behaviorId)) {
         LOGGER.log(Level.FINE, "behaviorId {0} has already been registered.  Replacing existing behavior class type {1} with {2}.", new Object[]{behaviorId, this.behaviorMap.get(behaviorId), behaviorClass});
      }

      this.behaviorMap.put(behaviorId, behaviorClass);
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.fine(MessageFormat.format("added behavior of type ''{0}'' class ''{1}''", behaviorId, behaviorClass));
      }

   }

   public Behavior createBehavior(String behaviorId) throws FacesException {
      Util.notNull("behaviorId", behaviorId);
      Behavior behavior = this.createCDIBehavior(behaviorId);
      if (behavior != null) {
         return behavior;
      } else {
         behavior = (Behavior)this.newThing(behaviorId, this.behaviorMap);
         Util.notNullNamedObject(behavior, behaviorId, "jsf.cannot_instantiate_behavior_error");
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine(MessageFormat.format("created behavior of type ''{0}''", behaviorId));
         }

         this.associate.getAnnotationManager().applyBehaviorAnnotations(FacesContext.getCurrentInstance(), behavior);
         return behavior;
      }
   }

   public Iterator getBehaviorIds() {
      return this.behaviorMap.keySet().iterator();
   }

   public void addConverter(String converterId, String converterClass) {
      Util.notNull("converterId", converterId);
      Util.notNull("converterClass", converterClass);
      if (LOGGER.isLoggable(Level.FINE) && this.converterIdMap.containsKey(converterId)) {
         LOGGER.log(Level.FINE, "converterId {0} has already been registered.  Replacing existing converter class type {1} with {2}.", new Object[]{converterId, this.converterIdMap.get(converterId), converterClass});
      }

      this.converterIdMap.put(converterId, converterClass);
      Class[] types = (Class[])STANDARD_CONV_ID_TO_TYPE_MAP.get(converterId);
      if (types != null) {
         Class[] var4 = types;
         int var5 = types.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Class clazz = var4[var6];
            this.converterTypeMap.put(clazz, converterClass);
            this.addPropertyEditorIfNecessary(clazz);
         }
      }

      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.fine(MessageFormat.format("added converter of type ''{0}'' and class ''{1}''", converterId, converterClass));
      }

   }

   public void addConverter(Class targetClass, String converterClass) {
      Util.notNull("targetClass", targetClass);
      Util.notNull("converterClass", converterClass);
      String converterId = (String)STANDARD_TYPE_TO_CONV_ID_MAP.get(targetClass);
      if (converterId != null) {
         this.addConverter(converterId, converterClass);
      } else {
         if (LOGGER.isLoggable(Level.FINE) && this.converterTypeMap.containsKey(targetClass)) {
            LOGGER.log(Level.FINE, "converter target class {0} has already been registered.  Replacing existing converter class type {1} with {2}.", new Object[]{targetClass.getName(), this.converterTypeMap.get(targetClass), converterClass});
         }

         this.converterTypeMap.put(targetClass, converterClass);
         this.addPropertyEditorIfNecessary(targetClass);
      }

      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.fine(MessageFormat.format("added converter of class type ''{0}''", converterClass));
      }

   }

   public Converter createConverter(String converterId) {
      Util.notNull("converterId", converterId);
      Converter converter = this.createCDIConverter(converterId);
      if (converter != null) {
         return converter;
      } else {
         converter = (Converter)this.newThing(converterId, this.converterIdMap);
         Util.notNullNamedObject(converter, converterId, "jsf.cannot_instantiate_converter_error");
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine(MessageFormat.format("created converter of type ''{0}''", converterId));
         }

         if (this.passDefaultTimeZone && converter instanceof DateTimeConverter) {
            ((DateTimeConverter)converter).setTimeZone(this.systemTimeZone);
         }

         this.associate.getAnnotationManager().applyConverterAnnotations(FacesContext.getCurrentInstance(), converter);
         return converter;
      }
   }

   public Converter createConverter(Class targetClass) {
      Util.notNull("targetClass", targetClass);
      Converter returnVal = null;
      if (this.version.isJsf23()) {
         BeanManager beanManager = this.getBeanManager();
         returnVal = CdiUtils.createConverter(beanManager, targetClass);
         if (returnVal != null) {
            return returnVal;
         }
      }

      returnVal = (Converter)this.newConverter(targetClass, this.converterTypeMap, targetClass);
      if (returnVal != null) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine(MessageFormat.format("Created converter of type ''{0}''", returnVal.getClass().getName()));
         }

         if (this.passDefaultTimeZone && returnVal instanceof DateTimeConverter) {
            ((DateTimeConverter)returnVal).setTimeZone(this.systemTimeZone);
         }

         this.associate.getAnnotationManager().applyConverterAnnotations(FacesContext.getCurrentInstance(), returnVal);
         return returnVal;
      } else {
         Class[] interfaces = targetClass.getInterfaces();
         if (interfaces != null) {
            for(int i = 0; i < interfaces.length; ++i) {
               returnVal = this.createConverterBasedOnClass(interfaces[i], targetClass);
               if (returnVal != null) {
                  if (LOGGER.isLoggable(Level.FINE)) {
                     LOGGER.fine(MessageFormat.format("Created converter of type ''{0}''", returnVal.getClass().getName()));
                  }

                  if (this.passDefaultTimeZone && returnVal instanceof DateTimeConverter) {
                     ((DateTimeConverter)returnVal).setTimeZone(this.systemTimeZone);
                  }

                  this.associate.getAnnotationManager().applyConverterAnnotations(FacesContext.getCurrentInstance(), returnVal);
                  return returnVal;
               }
            }
         }

         Class superclass = targetClass.getSuperclass();
         if (superclass != null) {
            returnVal = this.createConverterBasedOnClass(superclass, targetClass);
            if (returnVal != null) {
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.fine(MessageFormat.format("Created converter of type ''{0}''", returnVal.getClass().getName()));
               }

               if (this.passDefaultTimeZone && returnVal instanceof DateTimeConverter) {
                  ((DateTimeConverter)returnVal).setTimeZone(this.systemTimeZone);
               }

               this.associate.getAnnotationManager().applyConverterAnnotations(FacesContext.getCurrentInstance(), returnVal);
               return returnVal;
            }
         }

         return returnVal;
      }
   }

   public Iterator getConverterIds() {
      return this.converterIdMap.keySet().iterator();
   }

   public Iterator getConverterTypes() {
      return this.converterTypeMap.keySet().iterator();
   }

   public void addValidator(String validatorId, String validatorClass) {
      Util.notNull("validatorId", validatorId);
      Util.notNull("validatorClass", validatorClass);
      if (LOGGER.isLoggable(Level.FINE) && this.validatorMap.containsKey(validatorId)) {
         LOGGER.log(Level.FINE, "validatorId {0} has already been registered.  Replacing existing validator class type {1} with {2}.", new Object[]{validatorId, this.validatorMap.get(validatorId), validatorClass});
      }

      this.validatorMap.put(validatorId, validatorClass);
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.fine(MessageFormat.format("added validator of type ''{0}'' class ''{1}''", validatorId, validatorClass));
      }

   }

   public Validator createValidator(String validatorId) throws FacesException {
      Util.notNull("validatorId", validatorId);
      Validator validator = this.createCDIValidator(validatorId);
      if (validator != null) {
         return validator;
      } else {
         validator = (Validator)this.newThing(validatorId, this.validatorMap);
         Util.notNullNamedObject(validator, validatorId, "jsf.cannot_instantiate_validator_error");
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine(MessageFormat.format("created validator of type ''{0}''", validatorId));
         }

         this.associate.getAnnotationManager().applyValidatorAnnotations(FacesContext.getCurrentInstance(), validator);
         return validator;
      }
   }

   public Iterator getValidatorIds() {
      return this.validatorMap.keySet().iterator();
   }

   public synchronized void addDefaultValidatorId(String validatorId) {
      Util.notNull("validatorId", validatorId);
      this.defaultValidatorInfo = null;
      this.defaultValidatorIds.add(validatorId);
   }

   public Map getDefaultValidatorInfo() {
      if (this.defaultValidatorInfo == null) {
         synchronized(this) {
            if (this.defaultValidatorInfo == null) {
               this.defaultValidatorInfo = new LinkedHashMap();
               if (!this.defaultValidatorIds.isEmpty()) {
                  Iterator var2 = this.defaultValidatorIds.iterator();

                  while(var2.hasNext()) {
                     String id = (String)var2.next();
                     Object result = this.validatorMap.get(id);
                     if (null != result) {
                        String validatorClass;
                        if (result instanceof Class) {
                           validatorClass = ((Class)result).getName();
                        } else {
                           validatorClass = result.toString();
                        }

                        this.defaultValidatorInfo.put(id, validatorClass);
                     }
                  }
               }
            }
         }

         this.defaultValidatorInfo = Collections.unmodifiableMap(this.defaultValidatorInfo);
      }

      return this.defaultValidatorInfo;
   }

   private UIComponent createComponentFromScriptResource(FacesContext context, Resource scriptComponentResource, Resource componentResource) {
      UIComponent result = null;
      String className = scriptComponentResource.getResourceName();
      int lastDot = className.lastIndexOf(46);
      className = className.substring(0, lastDot);

      try {
         Class componentClass = (Class)this.componentMap.get(className);
         if (componentClass == null) {
            componentClass = Util.loadClass(className, this);
         }

         if (!this.associate.isDevModeEnabled()) {
            this.componentMap.put(className, componentClass);
         }

         result = (UIComponent)componentClass.newInstance();
      } catch (InstantiationException | ClassNotFoundException | IllegalAccessException var12) {
         if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(Level.SEVERE, (String)null, var12);
         }
      }

      if (result != null) {
         result.getAttributes().put("javax.faces.application.Resource.ComponentResource", componentResource);
         context.getAttributes().put("com.sun.faces.composite.this.library", componentResource.getLibraryName());

         try {
            this.associate.getAnnotationManager().applyComponentAnnotations(context, result);
         } finally {
            context.getAttributes().remove("com.sun.faces.composite.this.library");
         }
      }

      return result;
   }

   private UIComponent createComponentApplyAnnotations(FacesContext ctx, ValueExpression componentExpression, String componentType, String rendererType, boolean applyAnnotations) {
      try {
         UIComponent c = (UIComponent)componentExpression.getValue(ctx.getELContext());
         if (c == null) {
            c = this.createComponentApplyAnnotations(ctx, componentType, rendererType, applyAnnotations);
            componentExpression.setValue(ctx.getELContext(), c);
         } else if (applyAnnotations) {
            this.applyAnnotations(ctx, rendererType, c);
         }

         return c;
      } catch (Exception var8) {
         throw new FacesException(var8);
      }
   }

   private UIComponent createComponentApplyAnnotations(FacesContext ctx, String componentType, String rendererType, boolean applyAnnotations) {
      UIComponent component;
      try {
         component = (UIComponent)this.newThing(componentType, this.componentMap);
      } catch (Exception var7) {
         if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(Level.SEVERE, "jsf.cannot_instantiate_component_error", componentType);
         }

         throw new FacesException(var7);
      }

      Util.notNullNamedObject(component, componentType, "jsf.cannot_instantiate_component_error");
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, MessageFormat.format("Created component with component type of ''{0}''", componentType));
      }

      if (applyAnnotations) {
         this.applyAnnotations(ctx, rendererType, component);
      }

      return component;
   }

   private void applyAnnotations(FacesContext ctx, String rendererType, UIComponent c) {
      if (c != null && ctx != null) {
         this.associate.getAnnotationManager().applyComponentAnnotations(ctx, c);
         if (rendererType != null) {
            RenderKit rk = ctx.getRenderKit();
            Renderer r = null;
            if (rk != null) {
               r = rk.getRenderer(c.getFamily(), rendererType);
               if (r != null) {
                  c.setRendererType(rendererType);
                  this.associate.getAnnotationManager().applyRendererAnnotations(ctx, r, c);
               }
            }

            if ((rk == null || r == null) && LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "Unable to create Renderer with rendererType {0} for component with component type of {1}", new Object[]{rendererType, c.getFamily()});
            }
         }
      }

   }

   private Object newThing(String key, ViewMemberInstanceFactoryMetadataMap map) {
      Object value = map.get(key);
      if (value == null) {
         return null;
      } else {
         assert value instanceof String || value instanceof Class;

         Class clazz;
         if (value instanceof String) {
            String cValue = (String)value;

            try {
               clazz = Util.loadClass(cValue, value);
               if (!this.associate.isDevModeEnabled()) {
                  map.put(key, clazz);
               }

               assert clazz != null;
            } catch (Exception var8) {
               throw new FacesException(var8.getMessage(), var8);
            }
         } else {
            clazz = (Class)value;
         }

         try {
            Object result = clazz.newInstance();
            return result;
         } catch (Throwable var9) {
            Throwable t = var9;

            Throwable previousT;
            do {
               previousT = t;
               if (LOGGER.isLoggable(Level.SEVERE)) {
                  LOGGER.log(Level.SEVERE, "Unable to load class: ", t);
               }
            } while(null != (t = t.getCause()));

            throw new FacesException(MessageUtils.getExceptionMessageString("com.sun.faces.CANT_INSTANTIATE_CLASS", clazz.getName()), previousT);
         }
      }
   }

   private void pushDeclaredDefaultValuesToAttributesMap(FacesContext context, BeanInfo componentMetadata, Map attrs, UIComponent component, ExpressionFactory expressionFactory) {
      Collection attributesWithDeclaredDefaultValues = null;
      PropertyDescriptor[] propertyDescriptors = null;
      PropertyDescriptor[] var8 = componentMetadata.getPropertyDescriptors();
      int var9 = var8.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         PropertyDescriptor propertyDescriptor = var8[var10];
         Object defaultValue = propertyDescriptor.getValue("default");
         if (defaultValue != null) {
            String key = propertyDescriptor.getName();
            boolean isLiteralText = false;
            if (defaultValue instanceof ValueExpression) {
               isLiteralText = ((ValueExpression)defaultValue).isLiteralText();
               if (isLiteralText) {
                  defaultValue = ((ValueExpression)defaultValue).getValue(context.getELContext());
               }
            }

            if (propertyDescriptor.getValue("method-signature") == null || propertyDescriptor.getValue("type") != null) {
               if (attributesWithDeclaredDefaultValues == null) {
                  BeanDescriptor beanDescriptor = componentMetadata.getBeanDescriptor();
                  attributesWithDeclaredDefaultValues = (Collection)beanDescriptor.getValue("javax.faces.component.ATTR_NAMES_WITH_DEFAULT_VALUES");
                  if (attributesWithDeclaredDefaultValues == null) {
                     attributesWithDeclaredDefaultValues = new HashSet();
                     beanDescriptor.setValue("javax.faces.component.ATTR_NAMES_WITH_DEFAULT_VALUES", attributesWithDeclaredDefaultValues);
                  }
               }

               ((Collection)attributesWithDeclaredDefaultValues).add(key);
               if (isLiteralText) {
                  try {
                     if (propertyDescriptors == null) {
                        propertyDescriptors = Introspector.getBeanInfo(component.getClass()).getPropertyDescriptors();
                     }
                  } catch (IntrospectionException var16) {
                     throw new FacesException(var16);
                  }

                  defaultValue = this.convertValueToTypeIfNecessary(key, defaultValue, propertyDescriptors, expressionFactory);
                  attrs.put(key, defaultValue);
               }
            }
         }
      }

   }

   private Object convertValueToTypeIfNecessary(String name, Object value, PropertyDescriptor[] propertyDescriptors, ExpressionFactory expressionFactory) {
      PropertyDescriptor[] var5 = propertyDescriptors;
      int var6 = propertyDescriptors.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         PropertyDescriptor propertyDescriptor = var5[var7];
         if (propertyDescriptor.getName().equals(name)) {
            value = expressionFactory.coerceToType(value, propertyDescriptor.getPropertyType());
            break;
         }
      }

      return value;
   }

   private void addPropertyEditorIfNecessary(Class targetClass) {
      if (this.registerPropertyEditors) {
         PropertyEditor editor = PropertyEditorManager.findEditor(targetClass);
         if (editor == null) {
            String className = targetClass.getName();
            if (!targetClass.isPrimitive()) {
               String[] var4 = this.STANDARD_BY_TYPE_CONVERTER_CLASSES;
               int var5 = var4.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  String standardClass = var4[var6];
                  if (standardClass.indexOf(className) != -1) {
                     return;
                  }
               }

               Class editorClass = ConverterPropertyEditorFactory.getDefaultInstance().definePropertyEditorClassFor(targetClass);
               if (editorClass != null) {
                  PropertyEditorManager.registerEditor(targetClass, editorClass);
               } else if (LOGGER.isLoggable(Level.WARNING)) {
                  LOGGER.warning(MessageFormat.format("definePropertyEditorClassFor({0}) returned null.", targetClass.getName()));
               }

            }
         }
      }
   }

   private Converter createConverterBasedOnClass(Class targetClass, Class baseClass) {
      Converter returnVal = (Converter)this.newConverter(targetClass, this.converterTypeMap, baseClass);
      if (returnVal != null) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine(MessageFormat.format("Created converter of type ''{0}''", returnVal.getClass().getName()));
         }

         return returnVal;
      } else {
         Class[] interfaces = targetClass.getInterfaces();
         if (interfaces != null) {
            for(int i = 0; i < interfaces.length; ++i) {
               returnVal = this.createConverterBasedOnClass(interfaces[i], (Class)null);
               if (returnVal != null) {
                  if (LOGGER.isLoggable(Level.FINE)) {
                     LOGGER.fine(MessageFormat.format("Created converter of type ''{0}''", returnVal.getClass().getName()));
                  }

                  return returnVal;
               }
            }
         }

         Class superclass = targetClass.getSuperclass();
         if (superclass != null) {
            returnVal = this.createConverterBasedOnClass(superclass, targetClass);
            if (returnVal != null) {
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.fine(MessageFormat.format("Created converter of type ''{0}''", returnVal.getClass().getName()));
               }

               return returnVal;
            }
         }

         return returnVal;
      }
   }

   protected Object newConverter(Class key, Map map, Class targetClass) {
      assert key != null && map != null;

      Object result = null;
      Object value = map.get(key);
      if (value == null) {
         return null;
      } else {
         assert value instanceof String || value instanceof Class;

         Class clazz;
         if (value instanceof String) {
            String cValue = (String)value;

            try {
               clazz = Util.loadClass(cValue, value);
               if (!this.associate.isDevModeEnabled()) {
                  map.put(key, clazz);
               }

               assert clazz != null;
            } catch (Exception var12) {
               throw new FacesException(var12.getMessage(), var12);
            }
         } else {
            clazz = (Class)value;
         }

         Constructor ctor = ReflectionUtils.lookupConstructor(clazz, Class.class);
         Throwable cause = null;
         if (ctor != null) {
            try {
               result = ctor.newInstance(targetClass);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException var11) {
               cause = var11;
            }
         } else {
            try {
               result = clazz.newInstance();
            } catch (IllegalAccessException | InstantiationException var10) {
               cause = var10;
            }
         }

         if (null != cause) {
            throw new FacesException(MessageUtils.getExceptionMessageString("com.sun.faces.CANT_INSTANTIATE_CLASS", clazz.getName()), cause);
         } else {
            return result;
         }
      }
   }

   private BeanManager getBeanManager() {
      if (this.beanManager == null) {
         FacesContext facesContext = FacesContext.getCurrentInstance();
         this.beanManager = Util.getCdiBeanManager(facesContext);
      }

      return this.beanManager;
   }

   private Behavior createCDIBehavior(String behaviorId) {
      return this.version.isJsf23() ? CdiUtils.createBehavior(this.getBeanManager(), behaviorId) : null;
   }

   private Converter createCDIConverter(String converterId) {
      return this.version.isJsf23() ? CdiUtils.createConverter(this.getBeanManager(), converterId) : null;
   }

   private Validator createCDIValidator(String validatorId) {
      return this.version.isJsf23() ? CdiUtils.createValidator(this.getBeanManager(), validatorId) : null;
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
      STANDARD_CONV_ID_TO_TYPE_MAP = new HashMap(8, 1.0F);
      STANDARD_TYPE_TO_CONV_ID_MAP = new HashMap(16, 1.0F);
      STANDARD_CONV_ID_TO_TYPE_MAP.put("javax.faces.Byte", new Class[]{Byte.TYPE, Byte.class});
      STANDARD_CONV_ID_TO_TYPE_MAP.put("javax.faces.Boolean", new Class[]{Boolean.TYPE, Boolean.class});
      STANDARD_CONV_ID_TO_TYPE_MAP.put("javax.faces.Character", new Class[]{Character.TYPE, Character.class});
      STANDARD_CONV_ID_TO_TYPE_MAP.put("javax.faces.Short", new Class[]{Short.TYPE, Short.class});
      STANDARD_CONV_ID_TO_TYPE_MAP.put("javax.faces.Integer", new Class[]{Integer.TYPE, Integer.class});
      STANDARD_CONV_ID_TO_TYPE_MAP.put("javax.faces.Long", new Class[]{Long.TYPE, Long.class});
      STANDARD_CONV_ID_TO_TYPE_MAP.put("javax.faces.Float", new Class[]{Float.TYPE, Float.class});
      STANDARD_CONV_ID_TO_TYPE_MAP.put("javax.faces.Double", new Class[]{Double.TYPE, Double.class});
      Iterator var0 = STANDARD_CONV_ID_TO_TYPE_MAP.entrySet().iterator();

      while(var0.hasNext()) {
         Map.Entry entry = (Map.Entry)var0.next();
         Class[] types = (Class[])entry.getValue();
         String key = (String)entry.getKey();
         Class[] var4 = types;
         int var5 = types.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Class clazz = var4[var6];
            STANDARD_TYPE_TO_CONV_ID_MAP.put(clazz, key);
         }
      }

   }

   private static final class ComponentResourceClassNotFound {
   }
}
