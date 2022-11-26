package javax.faces.application;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.el.ELContextListener;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.FacesWrapper;
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

public abstract class ApplicationWrapper extends Application implements FacesWrapper {
   private Application wrapped;

   /** @deprecated */
   @Deprecated
   public ApplicationWrapper() {
   }

   public ApplicationWrapper(Application wrapped) {
      this.wrapped = wrapped;
   }

   public Application getWrapped() {
      return this.wrapped;
   }

   public ActionListener getActionListener() {
      return this.getWrapped().getActionListener();
   }

   public void setActionListener(ActionListener listener) {
      this.getWrapped().setActionListener(listener);
   }

   public Locale getDefaultLocale() {
      return this.getWrapped().getDefaultLocale();
   }

   public void setDefaultLocale(Locale locale) {
      this.getWrapped().setDefaultLocale(locale);
   }

   public String getDefaultRenderKitId() {
      return this.getWrapped().getDefaultRenderKitId();
   }

   public void addDefaultValidatorId(String validatorId) {
      this.getWrapped().addDefaultValidatorId(validatorId);
   }

   public Map getDefaultValidatorInfo() {
      return this.getWrapped().getDefaultValidatorInfo();
   }

   public void setDefaultRenderKitId(String renderKitId) {
      this.getWrapped().setDefaultRenderKitId(renderKitId);
   }

   public String getMessageBundle() {
      return this.getWrapped().getMessageBundle();
   }

   public void setMessageBundle(String bundle) {
      this.getWrapped().setMessageBundle(bundle);
   }

   public NavigationHandler getNavigationHandler() {
      return this.getWrapped().getNavigationHandler();
   }

   public void setNavigationHandler(NavigationHandler handler) {
      this.getWrapped().setNavigationHandler(handler);
   }

   /** @deprecated */
   @Deprecated
   public PropertyResolver getPropertyResolver() {
      return this.getWrapped().getPropertyResolver();
   }

   /** @deprecated */
   @Deprecated
   public void setPropertyResolver(PropertyResolver resolver) {
      this.getWrapped().setPropertyResolver(resolver);
   }

   /** @deprecated */
   @Deprecated
   public VariableResolver getVariableResolver() {
      return this.getWrapped().getVariableResolver();
   }

   /** @deprecated */
   @Deprecated
   public void setVariableResolver(VariableResolver resolver) {
      this.getWrapped().setVariableResolver(resolver);
   }

   public ViewHandler getViewHandler() {
      return this.getWrapped().getViewHandler();
   }

   public void setViewHandler(ViewHandler handler) {
      this.getWrapped().setViewHandler(handler);
   }

   public StateManager getStateManager() {
      return this.getWrapped().getStateManager();
   }

   public void setStateManager(StateManager manager) {
      this.getWrapped().setStateManager(manager);
   }

   public void addComponent(String componentType, String componentClass) {
      this.getWrapped().addComponent(componentType, componentClass);
   }

   public UIComponent createComponent(String componentType) throws FacesException {
      return this.getWrapped().createComponent(componentType);
   }

   /** @deprecated */
   @Deprecated
   public UIComponent createComponent(ValueBinding componentBinding, FacesContext context, String componentType) throws FacesException {
      return this.getWrapped().createComponent(componentBinding, context, componentType);
   }

   public Iterator getComponentTypes() {
      return this.getWrapped().getComponentTypes();
   }

   public void addConverter(String converterId, String converterClass) {
      this.getWrapped().addConverter(converterId, converterClass);
   }

   public void addConverter(Class targetClass, String converterClass) {
      this.getWrapped().addConverter(targetClass, converterClass);
   }

   public Converter createConverter(String converterId) {
      return this.getWrapped().createConverter(converterId);
   }

   public Converter createConverter(Class targetClass) {
      return this.getWrapped().createConverter(targetClass);
   }

   public Iterator getConverterIds() {
      return this.getWrapped().getConverterIds();
   }

   public Iterator getConverterTypes() {
      return this.getWrapped().getConverterTypes();
   }

   /** @deprecated */
   @Deprecated
   public MethodBinding createMethodBinding(String ref, Class[] params) throws ReferenceSyntaxException {
      return this.getWrapped().createMethodBinding(ref, params);
   }

   public Iterator getSupportedLocales() {
      return this.getWrapped().getSupportedLocales();
   }

   public void setSupportedLocales(Collection locales) {
      this.getWrapped().setSupportedLocales(locales);
   }

   public void addBehavior(String behaviorId, String behaviorClass) {
      this.getWrapped().addBehavior(behaviorId, behaviorClass);
   }

   public Behavior createBehavior(String behaviorId) throws FacesException {
      return this.getWrapped().createBehavior(behaviorId);
   }

   public Iterator getBehaviorIds() {
      return this.getWrapped().getBehaviorIds();
   }

   public void addValidator(String validatorId, String validatorClass) {
      this.getWrapped().addValidator(validatorId, validatorClass);
   }

   public Validator createValidator(String validatorId) throws FacesException {
      return this.getWrapped().createValidator(validatorId);
   }

   public Iterator getValidatorIds() {
      return this.getWrapped().getValidatorIds();
   }

   public ValueBinding createValueBinding(String ref) throws ReferenceSyntaxException {
      return this.getWrapped().createValueBinding(ref);
   }

   public ResourceHandler getResourceHandler() {
      return this.getWrapped().getResourceHandler();
   }

   public void setResourceHandler(ResourceHandler resourceHandler) {
      this.getWrapped().setResourceHandler(resourceHandler);
   }

   public ResourceBundle getResourceBundle(FacesContext ctx, String name) {
      return this.getWrapped().getResourceBundle(ctx, name);
   }

   public ProjectStage getProjectStage() {
      return this.getWrapped().getProjectStage();
   }

   public void addELResolver(ELResolver resolver) {
      this.getWrapped().addELResolver(resolver);
   }

   public ELResolver getELResolver() {
      return this.getWrapped().getELResolver();
   }

   public UIComponent createComponent(ValueExpression componentExpression, FacesContext context, String componentType) throws FacesException {
      return this.getWrapped().createComponent(componentExpression, context, componentType);
   }

   public UIComponent createComponent(ValueExpression componentExpression, FacesContext context, String componentType, String rendererType) {
      return this.getWrapped().createComponent(componentExpression, context, componentType, rendererType);
   }

   public UIComponent createComponent(FacesContext context, String componentType, String rendererType) {
      return this.getWrapped().createComponent(context, componentType, rendererType);
   }

   public UIComponent createComponent(FacesContext context, Resource componentResource) {
      return this.getWrapped().createComponent(context, componentResource);
   }

   public ExpressionFactory getExpressionFactory() {
      return this.getWrapped().getExpressionFactory();
   }

   public FlowHandler getFlowHandler() {
      return this.getWrapped().getFlowHandler();
   }

   public void setFlowHandler(FlowHandler newHandler) {
      super.setFlowHandler(newHandler);
   }

   /** @deprecated */
   @Deprecated
   public Object evaluateExpressionGet(FacesContext context, String expression, Class expectedType) throws ELException {
      return this.getWrapped().evaluateExpressionGet(context, expression, expectedType);
   }

   public void addELContextListener(ELContextListener listener) {
      this.getWrapped().addELContextListener(listener);
   }

   public void removeELContextListener(ELContextListener listener) {
      this.getWrapped().removeELContextListener(listener);
   }

   public ELContextListener[] getELContextListeners() {
      return this.getWrapped().getELContextListeners();
   }

   public void publishEvent(FacesContext context, Class systemEventClass, Object source) {
      this.getWrapped().publishEvent(context, systemEventClass, source);
   }

   public void publishEvent(FacesContext context, Class systemEventClass, Class sourceBaseType, Object source) {
      this.getWrapped().publishEvent(context, systemEventClass, sourceBaseType, source);
   }

   public void subscribeToEvent(Class systemEventClass, Class sourceClass, SystemEventListener listener) {
      this.getWrapped().subscribeToEvent(systemEventClass, sourceClass, listener);
   }

   public void subscribeToEvent(Class systemEventClass, SystemEventListener listener) {
      this.getWrapped().subscribeToEvent(systemEventClass, listener);
   }

   public void unsubscribeFromEvent(Class systemEventClass, Class sourceClass, SystemEventListener listener) {
      this.getWrapped().unsubscribeFromEvent(systemEventClass, sourceClass, listener);
   }

   public void unsubscribeFromEvent(Class systemEventClass, SystemEventListener listener) {
      this.getWrapped().unsubscribeFromEvent(systemEventClass, listener);
   }

   public SearchExpressionHandler getSearchExpressionHandler() {
      return this.getWrapped().getSearchExpressionHandler();
   }

   public void setSearchExpressionHandler(SearchExpressionHandler searchExpressionHandler) {
      this.getWrapped().setSearchExpressionHandler(searchExpressionHandler);
   }

   public void addSearchKeywordResolver(SearchKeywordResolver resolver) {
      this.getWrapped().addSearchKeywordResolver(resolver);
   }

   public SearchKeywordResolver getSearchKeywordResolver() {
      return this.getWrapped().getSearchKeywordResolver();
   }
}
