package com.sun.faces.application;

import com.sun.faces.RIConstants;
import com.sun.faces.config.WebConfiguration;
import com.sun.faces.el.ELUtils;
import com.sun.faces.el.FacesCompositeELResolver;
import com.sun.faces.el.PropertyResolverImpl;
import com.sun.faces.el.VariableResolverImpl;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.ReflectionUtils;
import com.sun.faces.util.Util;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.reflect.Constructor;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.CompositeELResolver;
import javax.el.ELContextListener;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.application.StateManager;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.MethodBinding;
import javax.faces.el.PropertyResolver;
import javax.faces.el.ReferenceSyntaxException;
import javax.faces.el.ValueBinding;
import javax.faces.el.VariableResolver;
import javax.faces.event.ActionListener;
import javax.faces.validator.Validator;

public class ApplicationImpl extends Application {
   private static final Logger logger;
   private static final ELContextListener[] EMPTY_EL_CTX_LIST_ARRAY;
   private static final Map STANDARD_CONV_ID_TO_TYPE_MAP;
   private static final Map STANDARD_TYPE_TO_CONV_ID_MAP;
   private ApplicationAssociate associate = null;
   private volatile ActionListener actionListener = null;
   private volatile NavigationHandler navigationHandler = null;
   private volatile PropertyResolverImpl propertyResolver = null;
   volatile VariableResolverImpl variableResolver = null;
   private volatile ViewHandler viewHandler = null;
   private volatile StateManager stateManager = null;
   private volatile ArrayList supportedLocales = null;
   private volatile Locale defaultLocale = null;
   private Map componentMap = null;
   private Map converterIdMap = null;
   private Map converterTypeMap = null;
   private Map validatorMap = null;
   private volatile String messageBundle = null;
   private ArrayList elContextListeners = null;
   CompositeELResolver elResolvers = null;
   FacesCompositeELResolver compositeELResolver = null;
   private boolean registerPropertyEditors;
   private final String[] STANDARD_BY_TYPE_CONVERTER_CLASSES = new String[]{"java.math.BigDecimal", "java.lang.Boolean", "java.lang.Byte", "java.lang.Character", "java.lang.Double", "java.lang.Float", "java.lang.Integer", "java.lang.Long", "java.lang.Short", "java.lang.Enum"};
   protected String defaultRenderKitId = null;

   public ApplicationImpl() {
      this.associate = new ApplicationAssociate(this);
      this.componentMap = new ConcurrentHashMap();
      this.converterIdMap = new ConcurrentHashMap();
      this.converterTypeMap = new ConcurrentHashMap();
      this.validatorMap = new ConcurrentHashMap();
      this.navigationHandler = new NavigationHandlerImpl(this.associate);
      this.propertyResolver = new PropertyResolverImpl();
      this.variableResolver = new VariableResolverImpl();
      this.elResolvers = new CompositeELResolver();
      WebConfiguration webConfig = WebConfiguration.getInstance();
      this.registerPropertyEditors = webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.RegisterConverterPropertyEditors);
      if (logger.isLoggable(Level.FINE)) {
         logger.log(Level.FINE, "Created Application instance ");
      }

   }

   public void addELContextListener(ELContextListener listener) {
      if (listener != null) {
         if (this.elContextListeners == null) {
            this.elContextListeners = new ArrayList();
         }

         this.elContextListeners.add(listener);
      }

   }

   public void removeELContextListener(ELContextListener listener) {
      if (listener != null && this.elContextListeners != null) {
         this.elContextListeners.remove(listener);
      }

   }

   public ELContextListener[] getELContextListeners() {
      return this.elContextListeners != null ? (ELContextListener[])this.elContextListeners.toArray(new ELContextListener[this.elContextListeners.size()]) : EMPTY_EL_CTX_LIST_ARRAY;
   }

   public ExpressionFactory getExpressionFactory() {
      return this.associate.getExpressionFactory();
   }

   public Object evaluateExpressionGet(FacesContext context, String expression, Class expectedType) throws ELException {
      ValueExpression ve = this.getExpressionFactory().createValueExpression(context.getELContext(), expression, expectedType);
      return ve.getValue(context.getELContext());
   }

   public UIComponent createComponent(ValueExpression componentExpression, FacesContext context, String componentType) throws FacesException {
      String message;
      if (null == componentExpression) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "componentExpression");
         throw new NullPointerException(message);
      } else if (null == context) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "context");
         throw new NullPointerException(message);
      } else if (null == componentType) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "componentType");
         throw new NullPointerException(message);
      } else {
         boolean createOne = false;

         Object result;
         try {
            if (null != (result = componentExpression.getValue(context.getELContext()))) {
               createOne = !(result instanceof UIComponent);
            }

            if (null == result || createOne) {
               result = this.createComponent(componentType);
               componentExpression.setValue(context.getELContext(), result);
            }
         } catch (Exception var7) {
            throw new FacesException(var7);
         }

         return (UIComponent)result;
      }
   }

   public ELResolver getELResolver() {
      if (this.compositeELResolver == null) {
         this.performOneTimeELInitialization();
      }

      return this.compositeELResolver;
   }

   public void addELResolver(ELResolver resolver) {
      if (this.associate.hasRequestBeenServiced()) {
         throw new IllegalStateException(MessageUtils.getExceptionMessageString("com.sun.faces.APPLICATION_INIT_COMPLETE_ERROR_ID"));
      } else {
         this.elResolvers.add(resolver);
      }
   }

   public CompositeELResolver getApplicationELResolvers() {
      return this.elResolvers;
   }

   public ActionListener getActionListener() {
      return this.actionListener;
   }

   public ViewHandler getViewHandler() {
      return this.viewHandler;
   }

   public synchronized void setViewHandler(ViewHandler handler) {
      if (handler == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "handler");
         throw new NullPointerException(message);
      } else if (this.associate.isResponseRendered()) {
         if (logger.isLoggable(Level.SEVERE)) {
            logger.log(Level.SEVERE, "jsf.illegal_attempt_setting_viewhandler_error");
         }

         throw new IllegalStateException(MessageUtils.getExceptionMessageString("com.sun.faces.ILLEGAL_ATTEMPT_SETTING_VIEWHANDLER"));
      } else {
         this.viewHandler = handler;
         if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, MessageFormat.format("set ViewHandler Instance to ''{0}''", this.viewHandler.getClass().getName()));
         }

      }
   }

   public StateManager getStateManager() {
      return this.stateManager;
   }

   public synchronized void setStateManager(StateManager manager) {
      if (manager == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "manager");
         throw new NullPointerException(message);
      } else if (this.associate.isResponseRendered()) {
         if (logger.isLoggable(Level.SEVERE)) {
            logger.log(Level.SEVERE, "jsf.illegal_attempt_setting_statemanager_error");
         }

         throw new IllegalStateException(MessageUtils.getExceptionMessageString("com.sun.faces.ILLEGAL_ATTEMPT_SETTING_STATEMANAGER"));
      } else {
         this.stateManager = manager;
         if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, MessageFormat.format("set StateManager Instance to ''{0}''", this.stateManager.getClass().getName()));
         }

      }
   }

   public synchronized void setActionListener(ActionListener listener) {
      if (listener == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "listener");
         throw new NullPointerException(message);
      } else {
         this.actionListener = listener;
         if (logger.isLoggable(Level.FINE)) {
            logger.fine(MessageFormat.format("set ActionListener Instance to ''{0}''", this.actionListener.getClass().getName()));
         }

      }
   }

   public NavigationHandler getNavigationHandler() {
      return this.navigationHandler;
   }

   public synchronized void setNavigationHandler(NavigationHandler handler) {
      if (handler == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "handler");
         throw new NullPointerException(message);
      } else {
         this.navigationHandler = handler;
         if (logger.isLoggable(Level.FINE)) {
            logger.fine(MessageFormat.format("set NavigationHandler Instance to ''{0}''", this.navigationHandler.getClass().getName()));
         }

      }
   }

   public PropertyResolver getPropertyResolver() {
      if (this.compositeELResolver == null) {
         this.performOneTimeELInitialization();
      }

      return this.propertyResolver;
   }

   public ResourceBundle getResourceBundle(FacesContext context, String var) {
      if (null != context && null != var) {
         return this.associate.getResourceBundle(context, var);
      } else {
         throw new FacesException("context or var is null.");
      }
   }

   public void setPropertyResolver(PropertyResolver resolver) {
      if (this.associate.hasRequestBeenServiced()) {
         throw new IllegalStateException(MessageUtils.getExceptionMessageString("com.sun.faces.APPLICATION_INIT_COMPLETE_ERROR_ID"));
      } else if (resolver == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "resolver");
         throw new NullPointerException(message);
      } else {
         this.propertyResolver.setDelegate(ELUtils.getDelegatePR(this.associate, true));
         this.associate.setLegacyPropertyResolver(resolver);
         this.propertyResolver = new PropertyResolverImpl();
         if (logger.isLoggable(Level.FINE)) {
            logger.fine(MessageFormat.format("set PropertyResolver Instance to ''{0}''", resolver.getClass().getName()));
         }

      }
   }

   public MethodBinding createMethodBinding(String ref, Class[] params) {
      if (ref == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "ref");
         throw new NullPointerException(message);
      } else if (ref.startsWith("#{") && ref.endsWith("}")) {
         FacesContext context = FacesContext.getCurrentInstance();

         MethodExpression result;
         try {
            if (null == params) {
               params = RIConstants.EMPTY_CLASS_ARGS;
            }

            result = this.getExpressionFactory().createMethodExpression(context.getELContext(), ref, (Class)null, params);
         } catch (ELException var6) {
            throw new ReferenceSyntaxException(var6);
         }

         return new MethodBindingMethodExpressionAdapter(result);
      } else {
         if (logger.isLoggable(Level.FINE)) {
            logger.fine(MessageFormat.format("Expression ''{0}'' does not follow the syntax #{...}", ref));
         }

         throw new ReferenceSyntaxException(ref);
      }
   }

   public ValueBinding createValueBinding(String ref) throws ReferenceSyntaxException {
      if (ref == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "ref");
         throw new NullPointerException(message);
      } else {
         FacesContext context = FacesContext.getCurrentInstance();

         ValueExpression result;
         try {
            result = this.getExpressionFactory().createValueExpression(context.getELContext(), ref, Object.class);
         } catch (ELException var5) {
            throw new ReferenceSyntaxException(var5);
         }

         return new ValueBindingValueExpressionAdapter(result);
      }
   }

   public VariableResolver getVariableResolver() {
      if (this.compositeELResolver == null) {
         this.performOneTimeELInitialization();
      }

      return this.variableResolver;
   }

   public void setVariableResolver(VariableResolver resolver) {
      if (this.associate.hasRequestBeenServiced()) {
         throw new IllegalStateException(MessageUtils.getExceptionMessageString("com.sun.faces.APPLICATION_INIT_COMPLETE_ERROR_ID"));
      } else if (resolver == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "resolver");
         throw new NullPointerException(message);
      } else {
         this.variableResolver.setDelegate(ELUtils.getDelegateVR(this.associate, true));
         this.associate.setLegacyVariableResolver(resolver);
         if (logger.isLoggable(Level.FINE)) {
            logger.fine(MessageFormat.format("set VariableResolver Instance to ''{0}''", this.variableResolver.getClass().getName()));
         }

      }
   }

   public void addComponent(String componentType, String componentClass) {
      String message;
      if (componentType == null) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "componentType");
         throw new NullPointerException(message);
      } else if (componentClass == null) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "componentClass");
         throw new NullPointerException(message);
      } else {
         this.componentMap.put(componentType, componentClass);
         if (logger.isLoggable(Level.FINE)) {
            logger.fine(MessageFormat.format("added component of type ''{0}'' and class ''{1}''", componentType, componentClass));
         }

      }
   }

   public UIComponent createComponent(String componentType) throws FacesException {
      if (componentType == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "componentType");
         throw new NullPointerException(message);
      } else {
         UIComponent returnVal;
         try {
            returnVal = (UIComponent)this.newThing(componentType, this.componentMap);
         } catch (Exception var4) {
            if (logger.isLoggable(Level.WARNING)) {
               logger.log(Level.WARNING, "jsf.cannot_instantiate_component_error", componentType);
            }

            throw new FacesException(var4);
         }

         if (returnVal == null) {
            Object[] params = new Object[]{componentType};
            if (logger.isLoggable(Level.SEVERE)) {
               logger.log(Level.SEVERE, "jsf.cannot_instantiate_component_error", params);
            }

            throw new FacesException(MessageUtils.getExceptionMessageString("com.sun.faces.NAMED_OBJECT_NOT_FOUND_ERROR", params));
         } else {
            if (logger.isLoggable(Level.FINE)) {
               logger.log(Level.FINE, MessageFormat.format("Created component with component type of ''{0}''", componentType));
            }

            return returnVal;
         }
      }
   }

   public UIComponent createComponent(ValueBinding componentBinding, FacesContext context, String componentType) throws FacesException {
      String message;
      if (null == componentBinding) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "componentBinding");
         throw new NullPointerException(message);
      } else if (null == context) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "context");
         throw new NullPointerException(message);
      } else if (null == componentType) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "componentType");
         throw new NullPointerException(message);
      } else {
         boolean createOne = false;

         Object result;
         try {
            if (null != (result = componentBinding.getValue(context))) {
               createOne = !(result instanceof UIComponent);
            }

            if (null == result || createOne) {
               result = this.createComponent(componentType);
               componentBinding.setValue(context, result);
            }
         } catch (Exception var7) {
            throw new FacesException(var7);
         }

         return (UIComponent)result;
      }
   }

   public Iterator getComponentTypes() {
      return this.componentMap.keySet().iterator();
   }

   public void addConverter(String converterId, String converterClass) {
      String message;
      if (converterId == null) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "converterId");
         throw new NullPointerException(message);
      } else if (converterClass == null) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "converterClass");
         throw new NullPointerException(message);
      } else {
         this.converterIdMap.put(converterId, converterClass);
         Class[] types = (Class[])STANDARD_CONV_ID_TO_TYPE_MAP.get(converterId);
         if (types != null) {
            Class[] arr$ = types;
            int len$ = types.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               Class clazz = arr$[i$];
               this.converterTypeMap.put(clazz, converterClass);
               this.addPropertyEditorIfNecessary(clazz);
            }
         }

         if (logger.isLoggable(Level.FINE)) {
            logger.fine(MessageFormat.format("added converter of type ''{0}'' and class ''{1}''", converterId, converterClass));
         }

      }
   }

   public void addConverter(Class targetClass, String converterClass) {
      String converterId;
      if (targetClass == null) {
         converterId = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "targetClass");
         throw new NullPointerException(converterId);
      } else if (converterClass == null) {
         converterId = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "converterClass");
         throw new NullPointerException(converterId);
      } else {
         converterId = (String)STANDARD_TYPE_TO_CONV_ID_MAP.get(targetClass);
         if (converterId != null) {
            this.addConverter(converterId, converterClass);
         } else {
            this.converterTypeMap.put(targetClass, converterClass);
            this.addPropertyEditorIfNecessary(targetClass);
         }

         if (logger.isLoggable(Level.FINE)) {
            logger.fine(MessageFormat.format("added converter of class type ''{0}''", converterClass));
         }

      }
   }

   private void addPropertyEditorIfNecessary(Class targetClass) {
      if (this.registerPropertyEditors) {
         PropertyEditor editor = PropertyEditorManager.findEditor(targetClass);
         if (null == editor) {
            String className = targetClass.getName();
            if (!targetClass.isPrimitive()) {
               String[] arr$ = this.STANDARD_BY_TYPE_CONVERTER_CLASSES;
               int len$ = arr$.length;

               for(int i$ = 0; i$ < len$; ++i$) {
                  String standardClass = arr$[i$];
                  if (-1 != standardClass.indexOf(className)) {
                     return;
                  }
               }

               Class editorClass = ConverterPropertyEditorFactory.getDefaultInstance().definePropertyEditorClassFor(targetClass);
               if (editorClass != null) {
                  PropertyEditorManager.registerEditor(targetClass, editorClass);
               } else if (logger.isLoggable(Level.WARNING)) {
                  logger.warning(MessageFormat.format("definePropertyEditorClassFor({0}) returned null.", targetClass.getName()));
               }

            }
         }
      }
   }

   private void performOneTimeELInitialization() {
      if (null != this.compositeELResolver) {
         throw new IllegalStateException("Class invariant invalidated: The Application instance's ELResolver is not null and it should be.");
      } else {
         this.associate.initializeELResolverChains();
      }
   }

   public Converter createConverter(String converterId) {
      if (converterId == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "convertedId");
         throw new NullPointerException(message);
      } else {
         Converter returnVal = (Converter)this.newThing(converterId, this.converterIdMap);
         if (returnVal == null) {
            Object[] params = new Object[]{converterId};
            if (logger.isLoggable(Level.SEVERE)) {
               logger.log(Level.SEVERE, "jsf.cannot_instantiate_converter_error", converterId);
            }

            throw new FacesException(MessageUtils.getExceptionMessageString("com.sun.faces.NAMED_OBJECT_NOT_FOUND_ERROR", params));
         } else {
            if (logger.isLoggable(Level.FINE)) {
               logger.fine(MessageFormat.format("created converter of type ''{0}''", converterId));
            }

            return returnVal;
         }
      }
   }

   public Converter createConverter(Class targetClass) {
      if (targetClass == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "targetClass");
         throw new NullPointerException(message);
      } else {
         Converter returnVal = (Converter)this.newConverter(targetClass, this.converterTypeMap, targetClass);
         if (returnVal != null) {
            if (logger.isLoggable(Level.FINE)) {
               logger.fine(MessageFormat.format("Created converter of type ''{0}''", returnVal.getClass().getName()));
            }

            return returnVal;
         } else {
            Class[] interfaces = targetClass.getInterfaces();
            if (interfaces != null) {
               for(int i = 0; i < interfaces.length; ++i) {
                  returnVal = this.createConverterBasedOnClass(interfaces[i], targetClass);
                  if (returnVal != null) {
                     if (logger.isLoggable(Level.FINE)) {
                        logger.fine(MessageFormat.format("Created converter of type ''{0}''", returnVal.getClass().getName()));
                     }

                     return returnVal;
                  }
               }
            }

            Class superclass = targetClass.getSuperclass();
            if (superclass != null) {
               returnVal = this.createConverterBasedOnClass(superclass, targetClass);
               if (returnVal != null) {
                  if (logger.isLoggable(Level.FINE)) {
                     logger.fine(MessageFormat.format("Created converter of type ''{0}''", returnVal.getClass().getName()));
                  }

                  return returnVal;
               }
            }

            return returnVal;
         }
      }
   }

   protected Converter createConverterBasedOnClass(Class targetClass, Class baseClass) {
      Converter returnVal = (Converter)this.newConverter(targetClass, this.converterTypeMap, baseClass);
      if (returnVal != null) {
         if (logger.isLoggable(Level.FINE)) {
            logger.fine(MessageFormat.format("Created converter of type ''{0}''", returnVal.getClass().getName()));
         }

         return returnVal;
      } else {
         Class[] interfaces = targetClass.getInterfaces();
         if (interfaces != null) {
            for(int i = 0; i < interfaces.length; ++i) {
               returnVal = this.createConverterBasedOnClass(interfaces[i], (Class)null);
               if (returnVal != null) {
                  if (logger.isLoggable(Level.FINE)) {
                     logger.fine(MessageFormat.format("Created converter of type ''{0}''", returnVal.getClass().getName()));
                  }

                  return returnVal;
               }
            }
         }

         Class superclass = targetClass.getSuperclass();
         if (superclass != null) {
            returnVal = this.createConverterBasedOnClass(superclass, (Class)null);
            if (returnVal != null) {
               if (logger.isLoggable(Level.FINE)) {
                  logger.fine(MessageFormat.format("Created converter of type ''{0}''", returnVal.getClass().getName()));
               }

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

   public Iterator getSupportedLocales() {
      return null != this.supportedLocales ? this.supportedLocales.iterator() : Collections.emptyList().iterator();
   }

   public synchronized void setSupportedLocales(Collection newLocales) {
      if (null == newLocales) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "newLocales");
         throw new NullPointerException(message);
      } else {
         this.supportedLocales = new ArrayList(newLocales);
         if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, MessageFormat.format("set Supported Locales ''{0}''", this.supportedLocales.toString()));
         }

      }
   }

   public Locale getDefaultLocale() {
      return this.defaultLocale;
   }

   public synchronized void setDefaultLocale(Locale locale) {
      if (locale == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "locale");
         throw new NullPointerException(message);
      } else {
         this.defaultLocale = locale;
         if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, MessageFormat.format("set defaultLocale ''{0}''", this.defaultLocale.getClass().getName()));
         }

      }
   }

   public String getDefaultRenderKitId() {
      return this.defaultRenderKitId;
   }

   public void setDefaultRenderKitId(String renderKitId) {
      this.defaultRenderKitId = renderKitId;
   }

   public void addValidator(String validatorId, String validatorClass) {
      String message;
      if (validatorId == null) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "validatorId");
         throw new NullPointerException(message);
      } else if (validatorClass == null) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "validatorClass");
         throw new NullPointerException(message);
      } else {
         this.validatorMap.put(validatorId, validatorClass);
         if (logger.isLoggable(Level.FINE)) {
            logger.fine(MessageFormat.format("added validator of type ''{0}'' class ''{1}''", validatorId, validatorClass));
         }

      }
   }

   public Validator createValidator(String validatorId) throws FacesException {
      if (validatorId == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "validatorId");
         throw new NullPointerException(message);
      } else {
         Validator returnVal = (Validator)this.newThing(validatorId, this.validatorMap);
         if (returnVal == null) {
            Object[] params = new Object[]{validatorId};
            if (logger.isLoggable(Level.SEVERE)) {
               logger.log(Level.SEVERE, "jsf.cannot_instantiate_validator_error", params);
            }

            throw new FacesException(MessageUtils.getExceptionMessageString("com.sun.faces.NAMED_OBJECT_NOT_FOUND_ERROR", params));
         } else {
            if (logger.isLoggable(Level.FINE)) {
               logger.fine(MessageFormat.format("created validator of type ''{0}''", validatorId));
            }

            return returnVal;
         }
      }
   }

   public Iterator getValidatorIds() {
      return this.validatorMap.keySet().iterator();
   }

   public synchronized void setMessageBundle(String messageBundle) {
      if (null == messageBundle) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "messageBundle");
         throw new NullPointerException(message);
      } else {
         this.messageBundle = messageBundle;
         if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, MessageFormat.format("set messageBundle ''{0}''", messageBundle));
         }

      }
   }

   public String getMessageBundle() {
      return this.messageBundle;
   }

   protected Object newThing(String key, Map map) {
      assert key != null && map != null;

      Class clazz = null;
      Object value = map.get(key);
      if (value == null) {
         return null;
      } else {
         assert value instanceof String || value instanceof Class;

         if (value instanceof String) {
            String cValue = (String)value;

            try {
               clazz = Util.loadClass(cValue, value);
               if (!this.associate.isDevModeEnabled()) {
                  map.put(key, clazz);
               }

               assert clazz != null;
            } catch (Throwable var9) {
               throw new FacesException(var9.getMessage(), var9);
            }
         } else {
            clazz = (Class)value;
         }

         try {
            Object result = clazz.newInstance();
            return result;
         } catch (Throwable var8) {
            throw new FacesException(MessageUtils.getExceptionMessageString("com.sun.faces.CANT_INSTANTIATE_CLASS", clazz.getName()), var8);
         }
      }
   }

   protected Object newConverter(Class key, Map map, Class targetClass) {
      assert key != null && map != null;

      Object result = null;
      Class clazz = null;
      Object value = map.get(key);
      if (value == null) {
         return null;
      } else {
         assert value instanceof String || value instanceof Class;

         if (value instanceof String) {
            String cValue = (String)value;

            try {
               clazz = Util.loadClass(cValue, value);
               if (!this.associate.isDevModeEnabled()) {
                  map.put(key, clazz);
               }

               assert clazz != null;
            } catch (Throwable var12) {
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
            } catch (Exception var11) {
               cause = var11;
            }
         } else {
            try {
               result = clazz.newInstance();
            } catch (Exception var10) {
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

   static {
      logger = FacesLogger.APPLICATION.getLogger();
      EMPTY_EL_CTX_LIST_ARRAY = new ELContextListener[0];
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
      Iterator i$ = STANDARD_CONV_ID_TO_TYPE_MAP.entrySet().iterator();

      while(i$.hasNext()) {
         Map.Entry entry = (Map.Entry)i$.next();
         Class[] types = (Class[])entry.getValue();
         String key = (String)entry.getKey();
         Class[] arr$ = types;
         int len$ = types.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Class clazz = arr$[i$];
            STANDARD_TYPE_TO_CONV_ID_MAP.put(clazz, key);
         }
      }

   }
}
