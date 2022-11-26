package com.sun.faces.application;

import com.sun.faces.application.applicationimpl.Events;
import com.sun.faces.application.applicationimpl.ExpressionLanguage;
import com.sun.faces.application.applicationimpl.InstanceFactory;
import com.sun.faces.application.applicationimpl.SearchExpression;
import com.sun.faces.application.applicationimpl.Singletons;
import com.sun.faces.application.applicationimpl.Stage;
import com.sun.faces.el.FacesCompositeELResolver;
import com.sun.faces.util.FacesLogger;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.CompositeELResolver;
import javax.el.ELContextListener;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ProjectStage;
import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.application.StateManager;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;
import javax.faces.component.search.SearchExpressionHandler;
import javax.faces.component.search.SearchKeywordResolver;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.MethodBinding;
import javax.faces.el.PropertyResolver;
import javax.faces.el.ReferenceSyntaxException;
import javax.faces.el.ValueBinding;
import javax.faces.el.VariableResolver;
import javax.faces.event.ActionListener;
import javax.faces.event.SystemEventListener;
import javax.faces.flow.FlowHandler;
import javax.faces.validator.Validator;

public class ApplicationImpl extends Application {
   public static final String THIS_LIBRARY = "com.sun.faces.composite.this.library";
   private static final Logger LOGGER;
   private final ApplicationAssociate associate = new ApplicationAssociate(this);
   private final Events events = new Events();
   private final Singletons singletons;
   private final ExpressionLanguage expressionLanguage;
   private final InstanceFactory instanceFactory;
   private final SearchExpression searchExpression;
   private final Stage stage = new Stage();

   public ApplicationImpl() {
      this.singletons = new Singletons(this.associate);
      this.expressionLanguage = new ExpressionLanguage(this.associate);
      this.instanceFactory = new InstanceFactory(this.associate);
      this.searchExpression = new SearchExpression(this.associate);
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, "Created Application instance ");
      }

   }

   public void publishEvent(FacesContext context, Class systemEventClass, Object source) {
      this.publishEvent(context, systemEventClass, (Class)null, source);
   }

   public void publishEvent(FacesContext context, Class systemEventClass, Class sourceBaseType, Object source) {
      this.events.publishEvent(context, systemEventClass, sourceBaseType, source, this.getProjectStage());
   }

   public void subscribeToEvent(Class systemEventClass, SystemEventListener listener) {
      this.subscribeToEvent(systemEventClass, (Class)null, listener);
   }

   public void subscribeToEvent(Class systemEventClass, Class sourceClass, SystemEventListener listener) {
      this.events.subscribeToEvent(systemEventClass, sourceClass, listener);
   }

   public void unsubscribeFromEvent(Class systemEventClass, SystemEventListener listener) {
      this.unsubscribeFromEvent(systemEventClass, (Class)null, listener);
   }

   public void unsubscribeFromEvent(Class systemEventClass, Class sourceClass, SystemEventListener listener) {
      this.events.unsubscribeFromEvent(systemEventClass, sourceClass, listener);
   }

   public void addELContextListener(ELContextListener listener) {
      this.expressionLanguage.addELContextListener(listener);
   }

   public void removeELContextListener(ELContextListener listener) {
      this.expressionLanguage.removeELContextListener(listener);
   }

   public ELContextListener[] getELContextListeners() {
      return this.expressionLanguage.getELContextListeners();
   }

   public ExpressionFactory getExpressionFactory() {
      return this.expressionLanguage.getExpressionFactory();
   }

   public Object evaluateExpressionGet(FacesContext context, String expression, Class expectedType) throws ELException {
      return this.expressionLanguage.evaluateExpressionGet(context, expression, expectedType);
   }

   public ELResolver getELResolver() {
      return this.expressionLanguage.getELResolver();
   }

   public void addELResolver(ELResolver resolver) {
      this.expressionLanguage.addELResolver(resolver);
   }

   public CompositeELResolver getApplicationELResolvers() {
      return this.expressionLanguage.getApplicationELResolvers();
   }

   public FacesCompositeELResolver getCompositeELResolver() {
      return this.expressionLanguage.getCompositeELResolver();
   }

   public void setCompositeELResolver(FacesCompositeELResolver compositeELResolver) {
      this.expressionLanguage.setCompositeELResolver(compositeELResolver);
   }

   public ViewHandler getViewHandler() {
      return this.singletons.getViewHandler();
   }

   public void setViewHandler(ViewHandler viewHandler) {
      this.singletons.setViewHandler(viewHandler);
   }

   public ResourceHandler getResourceHandler() {
      return this.singletons.getResourceHandler();
   }

   public void setResourceHandler(ResourceHandler resourceHandler) {
      this.singletons.setResourceHandler(resourceHandler);
   }

   public StateManager getStateManager() {
      return this.singletons.getStateManager();
   }

   public void setStateManager(StateManager stateManager) {
      this.singletons.setStateManager(stateManager);
   }

   public ActionListener getActionListener() {
      return this.singletons.getActionListener();
   }

   public void setActionListener(ActionListener actionListener) {
      this.singletons.setActionListener(actionListener);
   }

   public NavigationHandler getNavigationHandler() {
      return this.singletons.getNavigationHandler();
   }

   public void setNavigationHandler(NavigationHandler navigationHandler) {
      this.singletons.setNavigationHandler(navigationHandler);
   }

   public FlowHandler getFlowHandler() {
      return this.singletons.getFlowHandler();
   }

   public void setFlowHandler(FlowHandler flowHandler) {
      this.singletons.setFlowHandler(flowHandler);
   }

   public Iterator getSupportedLocales() {
      return this.singletons.getSupportedLocales();
   }

   public void setSupportedLocales(Collection newLocales) {
      this.singletons.setSupportedLocales(newLocales);
   }

   public Locale getDefaultLocale() {
      return this.singletons.getDefaultLocale();
   }

   public void setDefaultLocale(Locale locale) {
      this.singletons.setDefaultLocale(locale);
   }

   public void setMessageBundle(String messageBundle) {
      this.singletons.setMessageBundle(messageBundle);
   }

   public String getMessageBundle() {
      return this.singletons.getMessageBundle();
   }

   public String getDefaultRenderKitId() {
      return this.singletons.getDefaultRenderKitId();
   }

   public void setDefaultRenderKitId(String renderKitId) {
      this.singletons.setDefaultRenderKitId(renderKitId);
   }

   public ResourceBundle getResourceBundle(FacesContext context, String var) {
      return this.singletons.getResourceBundle(context, var);
   }

   public void addBehavior(String behaviorId, String behaviorClass) {
      this.instanceFactory.addBehavior(behaviorId, behaviorClass);
   }

   public Behavior createBehavior(String behaviorId) throws FacesException {
      return this.instanceFactory.createBehavior(behaviorId);
   }

   public Iterator getBehaviorIds() {
      return this.instanceFactory.getBehaviorIds();
   }

   public UIComponent createComponent(String componentType) throws FacesException {
      return this.instanceFactory.createComponent(componentType);
   }

   public void addComponent(String componentType, String componentClass) {
      this.instanceFactory.addComponent(componentType, componentClass);
   }

   public UIComponent createComponent(ValueExpression componentExpression, FacesContext context, String componentType) throws FacesException {
      return this.instanceFactory.createComponent(componentExpression, context, componentType);
   }

   public UIComponent createComponent(ValueExpression componentExpression, FacesContext context, String componentType, String rendererType) {
      return this.instanceFactory.createComponent(componentExpression, context, componentType, rendererType);
   }

   public UIComponent createComponent(FacesContext context, String componentType, String rendererType) {
      return this.instanceFactory.createComponent(context, componentType, rendererType);
   }

   public UIComponent createComponent(FacesContext context, Resource componentResource) throws FacesException {
      return this.instanceFactory.createComponent(context, componentResource, this.getExpressionFactory());
   }

   public UIComponent createComponent(ValueBinding componentBinding, FacesContext context, String componentType) throws FacesException {
      return this.instanceFactory.createComponent(componentBinding, context, componentType);
   }

   public Iterator getComponentTypes() {
      return this.instanceFactory.getComponentTypes();
   }

   public void addConverter(String converterId, String converterClass) {
      this.instanceFactory.addConverter(converterId, converterClass);
   }

   public void addConverter(Class targetClass, String converterClass) {
      this.instanceFactory.addConverter(targetClass, converterClass);
   }

   public Converter createConverter(String converterId) {
      return this.instanceFactory.createConverter(converterId);
   }

   public Converter createConverter(Class targetClass) {
      return this.instanceFactory.createConverter(targetClass);
   }

   public Iterator getConverterIds() {
      return this.instanceFactory.getConverterIds();
   }

   public Iterator getConverterTypes() {
      return this.instanceFactory.getConverterTypes();
   }

   public void addValidator(String validatorId, String validatorClass) {
      this.instanceFactory.addValidator(validatorId, validatorClass);
   }

   public Validator createValidator(String validatorId) throws FacesException {
      return this.instanceFactory.createValidator(validatorId);
   }

   public Iterator getValidatorIds() {
      return this.instanceFactory.getValidatorIds();
   }

   public void addDefaultValidatorId(String validatorId) {
      this.instanceFactory.addDefaultValidatorId(validatorId);
   }

   public Map getDefaultValidatorInfo() {
      return this.instanceFactory.getDefaultValidatorInfo();
   }

   public ProjectStage getProjectStage() {
      return this.stage.getProjectStage(this);
   }

   public SearchExpressionHandler getSearchExpressionHandler() {
      return this.searchExpression.getSearchExpressionHandler();
   }

   public void setSearchExpressionHandler(SearchExpressionHandler searchExpressionHandler) {
      this.searchExpression.setSearchExpressionHandler(searchExpressionHandler);
   }

   public void addSearchKeywordResolver(SearchKeywordResolver resolver) {
      this.searchExpression.addSearchKeywordResolver(resolver);
   }

   public SearchKeywordResolver getSearchKeywordResolver() {
      return this.searchExpression.getSearchKeywordResolver();
   }

   /** @deprecated */
   @Deprecated
   public PropertyResolver getPropertyResolver() {
      return this.expressionLanguage.getPropertyResolver();
   }

   /** @deprecated */
   @Deprecated
   public void setPropertyResolver(PropertyResolver resolver) {
      this.expressionLanguage.setPropertyResolver(resolver);
   }

   /** @deprecated */
   @Deprecated
   public MethodBinding createMethodBinding(String ref, Class[] params) {
      return this.expressionLanguage.createMethodBinding(ref, params);
   }

   /** @deprecated */
   @Deprecated
   public ValueBinding createValueBinding(String ref) throws ReferenceSyntaxException {
      return this.expressionLanguage.createValueBinding(ref);
   }

   /** @deprecated */
   @Deprecated
   public VariableResolver getVariableResolver() {
      return this.expressionLanguage.getVariableResolver();
   }

   /** @deprecated */
   @Deprecated
   public void setVariableResolver(VariableResolver resolver) {
      this.expressionLanguage.setVariableResolver(resolver);
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
   }
}
