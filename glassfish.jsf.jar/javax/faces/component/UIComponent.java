package javax.faces.component;

import com.sun.faces.util.Util;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.FacesWrapper;
import javax.faces.application.Resource;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitHint;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ComponentSystemEventListener;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.event.PostRestoreStateEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;
import javax.faces.event.SystemEventListenerHolder;
import javax.faces.render.Renderer;

public abstract class UIComponent implements PartialStateHolder, TransientStateHolder, SystemEventListenerHolder, ComponentSystemEventListener {
   private static Logger LOGGER = Logger.getLogger("javax.faces.component", "javax.faces.LogStrings");
   public static final String HONOR_CURRENT_COMPONENT_ATTRIBUTES_PARAM_NAME = "javax.faces.HONOR_CURRENT_COMPONENT_ATTRIBUTES";
   public static final String BEANINFO_KEY = "javax.faces.component.BEANINFO_KEY";
   public static final String FACETS_KEY = "javax.faces.component.FACETS_KEY";
   public static final String VIEW_LOCATION_KEY = "javax.faces.component.VIEW_LOCATION_KEY";
   public static final String COMPOSITE_COMPONENT_TYPE_KEY = "javax.faces.component.COMPOSITE_COMPONENT_TYPE";
   public static final String COMPOSITE_FACET_NAME = "javax.faces.component.COMPOSITE_FACET_NAME";
   public static final String ATTRS_WITH_DECLARED_DEFAULT_VALUES = "javax.faces.component.ATTR_NAMES_WITH_DEFAULT_VALUES";
   private static final String _CURRENT_COMPONENT_STACK_KEY = "javax.faces.component.CURRENT_COMPONENT_STACK";
   private static final String _CURRENT_COMPOSITE_COMPONENT_STACK_KEY = "javax.faces.component.CURRENT_COMPOSITE_COMPONENT_STACK";
   List attributesThatAreSet;
   ComponentStateHelper stateHelper;
   UIComponent compositeParent;
   private boolean isInView;
   private Map resourceBundleMap;
   private transient Boolean isSetCurrentComponent;
   private transient Boolean isCompositeComponent;
   private int _isPushedAsCurrentRefCount = 0;
   boolean initialState;
   /** @deprecated */
   public static final String CURRENT_COMPONENT = "javax.faces.component.CURRENT_COMPONENT";
   /** @deprecated */
   public static final String CURRENT_COMPOSITE_COMPONENT = "javax.faces.component.CURRENT_COMPOSITE_COMPONENT";
   /** @deprecated */
   @Deprecated
   protected Map bindings = null;

   public abstract Map getAttributes();

   public Map getPassThroughAttributes() {
      return this.getPassThroughAttributes(true);
   }

   public Map getPassThroughAttributes(boolean create) {
      return Collections.emptyMap();
   }

   public ValueExpression getValueExpression(String name) {
      if (name == null) {
         throw new NullPointerException();
      } else {
         Map map = (Map)this.getStateHelper().get(UIComponent.PropertyKeys.bindings);
         return map != null ? (ValueExpression)map.get(name) : null;
      }
   }

   public void setValueExpression(String name, ValueExpression binding) {
      if (name == null) {
         throw new NullPointerException();
      } else if (Util.isOneOf(name, "id", "parent")) {
         throw new IllegalArgumentException();
      } else {
         if (binding != null) {
            if (!binding.isLiteralText()) {
               List sProperties = (List)this.getStateHelper().get(UIComponent.PropertyKeysPrivate.attributesThatAreSet);
               if (sProperties == null) {
                  this.getStateHelper().add(UIComponent.PropertyKeysPrivate.attributesThatAreSet, name);
               } else if (!sProperties.contains(name)) {
                  this.getStateHelper().add(UIComponent.PropertyKeysPrivate.attributesThatAreSet, name);
               }

               this.getStateHelper().put(UIComponent.PropertyKeys.bindings, name, binding);
            } else {
               ELContext context = FacesContext.getCurrentInstance().getELContext();

               try {
                  this.getAttributes().put(name, binding.getValue(context));
               } catch (ELException var5) {
                  throw new FacesException(var5);
               }
            }
         } else {
            this.getStateHelper().remove(UIComponent.PropertyKeysPrivate.attributesThatAreSet, name);
            this.getStateHelper().remove(UIComponent.PropertyKeys.bindings, name);
         }

      }
   }

   public void markInitialState() {
      this.initialState = true;
   }

   public boolean initialStateMarked() {
      return this.initialState;
   }

   public void clearInitialState() {
      this.initialState = false;
   }

   protected StateHelper getStateHelper() {
      return this.getStateHelper(true);
   }

   protected StateHelper getStateHelper(boolean create) {
      if (create && this.stateHelper == null) {
         this.stateHelper = new ComponentStateHelper(this);
      }

      return this.stateHelper;
   }

   public TransientStateHelper getTransientStateHelper() {
      return this.getTransientStateHelper(true);
   }

   public TransientStateHelper getTransientStateHelper(boolean create) {
      if (create && this.stateHelper == null) {
         this.stateHelper = new ComponentStateHelper(this);
      }

      return this.stateHelper;
   }

   public void restoreTransientState(FacesContext context, Object state) {
      boolean forceCreate = state != null;
      TransientStateHelper helper = this.getTransientStateHelper(forceCreate);
      if (helper != null) {
         helper.restoreTransientState(context, state);
      }

   }

   public Object saveTransientState(FacesContext context) {
      TransientStateHelper helper = this.getTransientStateHelper(false);
      return helper == null ? null : helper.saveTransientState(context);
   }

   public boolean isInView() {
      return this.isInView;
   }

   public void setInView(boolean isInView) {
      this.isInView = isInView;
   }

   public String getClientId() {
      return this.getClientId(FacesContext.getCurrentInstance());
   }

   public abstract String getClientId(FacesContext var1);

   public String getContainerClientId(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         return this.getClientId(context);
      }
   }

   public abstract String getFamily();

   public abstract String getId();

   public abstract void setId(String var1);

   public abstract UIComponent getParent();

   public abstract void setParent(UIComponent var1);

   public abstract boolean isRendered();

   public abstract void setRendered(boolean var1);

   public abstract String getRendererType();

   public abstract void setRendererType(String var1);

   public abstract boolean getRendersChildren();

   public Map getResourceBundleMap() {
      if (this.resourceBundleMap == null) {
         FacesContext context = FacesContext.getCurrentInstance();
         ResourceBundle resourceBundle = this.findResourceBundleUnderFQCNofThis(context);
         if (resourceBundle == null) {
            resourceBundle = this.findResourceBundleAsResource(context);
         }

         if (resourceBundle != null) {
            this.resourceBundleMap = this.wrapBundleAsMap(resourceBundle);
         }

         if (this.resourceBundleMap == null) {
            this.resourceBundleMap = Collections.emptyMap();
         }
      }

      return this.resourceBundleMap;
   }

   public abstract List getChildren();

   public abstract int getChildCount();

   public abstract UIComponent findComponent(String var1);

   public boolean invokeOnComponent(FacesContext context, String clientId, ContextCallback callback) throws FacesException {
      if (Util.isAnyNull(context, clientId, callback)) {
         throw new NullPointerException();
      } else {
         boolean found = false;
         if (clientId.equals(this.getClientId(context))) {
            boolean var11;
            try {
               this.pushComponentToEL(context, this);
               callback.invokeContextCallback(context, this);
               var11 = true;
            } catch (Exception var9) {
               throw new FacesException(var9);
            } finally {
               this.popComponentFromEL(context);
            }

            return var11;
         } else {
            for(Iterator facetsAndChildrenIterator = this.getFacetsAndChildren(); facetsAndChildrenIterator.hasNext() && !found; found = ((UIComponent)facetsAndChildrenIterator.next()).invokeOnComponent(context, clientId, callback)) {
            }

            return found;
         }
      }
   }

   public abstract Map getFacets();

   public int getFacetCount() {
      return this.getFacets().size();
   }

   public abstract UIComponent getFacet(String var1);

   public abstract Iterator getFacetsAndChildren();

   public abstract void broadcast(FacesEvent var1) throws AbortProcessingException;

   public abstract void decode(FacesContext var1);

   public boolean visitTree(VisitContext visitContext, VisitCallback callback) {
      if (!this.isVisitable(visitContext)) {
         return false;
      } else {
         FacesContext facesContext = visitContext.getFacesContext();
         this.pushComponentToEL(facesContext, (UIComponent)null);

         boolean var5;
         try {
            VisitResult result = visitContext.invokeVisitCallback(this, callback);
            if (result != VisitResult.COMPLETE) {
               if (result != VisitResult.ACCEPT) {
                  return false;
               }

               Iterator kids = this.getFacetsAndChildren();

               boolean done;
               do {
                  if (!kids.hasNext()) {
                     return false;
                  }

                  done = ((UIComponent)kids.next()).visitTree(visitContext, callback);
               } while(!done);

               boolean var7 = true;
               return var7;
            }

            var5 = true;
         } finally {
            this.popComponentFromEL(facesContext);
         }

         return var5;
      }
   }

   protected boolean isVisitable(VisitContext context) {
      Set hints = context.getHints();
      return (!hints.contains(VisitHint.SKIP_UNRENDERED) || this.isRendered()) && (!hints.contains(VisitHint.SKIP_TRANSIENT) || !this.isTransient());
   }

   public abstract void encodeBegin(FacesContext var1) throws IOException;

   public abstract void encodeChildren(FacesContext var1) throws IOException;

   public abstract void encodeEnd(FacesContext var1) throws IOException;

   public void encodeAll(FacesContext context) throws IOException {
      if (context == null) {
         throw new NullPointerException();
      } else if (this.isRendered()) {
         this.encodeBegin(context);
         if (this.getRendersChildren()) {
            this.encodeChildren(context);
         } else if (this.getChildCount() > 0) {
            Iterator var2 = this.getChildren().iterator();

            while(var2.hasNext()) {
               UIComponent kid = (UIComponent)var2.next();
               kid.encodeAll(context);
            }
         }

         this.encodeEnd(context);
      }
   }

   private static ArrayDeque _getComponentELStack(String keyName, Map contextAttributes) {
      return (ArrayDeque)contextAttributes.computeIfAbsent(keyName, (e) -> {
         return new ArrayDeque();
      });
   }

   private boolean isSetCurrentComponent(FacesContext context) {
      if (this.isSetCurrentComponent != null) {
         return this.isSetCurrentComponent;
      } else {
         Boolean honorComponentAttribute = (Boolean)context.getAttributes().get("javax.faces.HONOR_CURRENT_COMPONENT_ATTRIBUTES");
         return honorComponentAttribute != null ? honorComponentAttribute : Boolean.valueOf(context.getExternalContext().getInitParameter("javax.faces.HONOR_CURRENT_COMPONENT_ATTRIBUTES"));
      }
   }

   public void pushComponentToEL(FacesContext context, UIComponent component) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         if (component == null) {
            component = this;
         }

         Map contextAttributes = context.getAttributes();
         ArrayDeque componentELStack = _getComponentELStack("javax.faces.component.CURRENT_COMPONENT_STACK", contextAttributes);
         componentELStack.push(component);
         ++component._isPushedAsCurrentRefCount;
         boolean setCurrentComponent = this.isSetCurrentComponent(context);
         if (setCurrentComponent) {
            contextAttributes.put("javax.faces.component.CURRENT_COMPONENT", component);
         }

         if (isCompositeComponent(component)) {
            _getComponentELStack("javax.faces.component.CURRENT_COMPOSITE_COMPONENT_STACK", contextAttributes).push(component);
            if (setCurrentComponent) {
               contextAttributes.put("javax.faces.component.CURRENT_COMPOSITE_COMPONENT", component);
            }
         }

      }
   }

   public void popComponentFromEL(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         Map contextAttributes = context.getAttributes();
         ArrayDeque componentELStack = _getComponentELStack("javax.faces.component.CURRENT_COMPONENT_STACK", contextAttributes);
         if (this._isPushedAsCurrentRefCount < 1) {
            if (componentELStack.peek() != this) {
               return;
            }

            LOGGER.log(Level.SEVERE, "the component(" + this + ") is the head component of the stack, but it's _isPushedAsCurrentRefCount < 1");
         }

         for(UIComponent topComponent = (UIComponent)componentELStack.peek(); topComponent != this; topComponent = (UIComponent)componentELStack.peek()) {
            topComponent.popComponentFromEL(context);
         }

         componentELStack.pop();
         --this._isPushedAsCurrentRefCount;
         boolean setCurrentComponent = this.isSetCurrentComponent(context);
         if (setCurrentComponent) {
            contextAttributes.put("javax.faces.component.CURRENT_COMPONENT", componentELStack.peek());
         }

         if (isCompositeComponent(this)) {
            ArrayDeque compositeELStack = _getComponentELStack("javax.faces.component.CURRENT_COMPOSITE_COMPONENT_STACK", contextAttributes);
            if (!compositeELStack.isEmpty()) {
               compositeELStack.pop();
            }

            if (setCurrentComponent) {
               contextAttributes.put("javax.faces.component.CURRENT_COMPOSITE_COMPONENT", compositeELStack.peek());
            }
         }

      }
   }

   public static boolean isCompositeComponent(UIComponent component) {
      if (component == null) {
         throw new NullPointerException();
      } else {
         boolean result = false;
         if (null != component.isCompositeComponent) {
            result = component.isCompositeComponent;
         } else {
            result = component.isCompositeComponent = component.getAttributes().containsKey("javax.faces.application.Resource.ComponentResource");
         }

         return result;
      }
   }

   public static UIComponent getCompositeComponentParent(UIComponent component) {
      if (component == null) {
         return null;
      } else if (component.compositeParent != null) {
         return component.compositeParent;
      } else {
         for(UIComponent parent = component.getParent(); parent != null; parent = parent.getParent()) {
            if (isCompositeComponent(parent)) {
               if (component.isInView()) {
                  component.compositeParent = parent;
               }

               return parent;
            }
         }

         return null;
      }
   }

   public static UIComponent getCurrentComponent(FacesContext context) {
      Map contextAttributes = context.getAttributes();
      ArrayDeque componentELStack = _getComponentELStack("javax.faces.component.CURRENT_COMPONENT_STACK", contextAttributes);
      return (UIComponent)componentELStack.peek();
   }

   public static UIComponent getCurrentCompositeComponent(FacesContext context) {
      return (UIComponent)_getComponentELStack("javax.faces.component.CURRENT_COMPOSITE_COMPONENT_STACK", context.getAttributes()).peek();
   }

   protected abstract void addFacesListener(FacesListener var1);

   protected abstract FacesListener[] getFacesListeners(Class var1);

   protected abstract void removeFacesListener(FacesListener var1);

   public abstract void queueEvent(FacesEvent var1);

   public void subscribeToEvent(Class eventClass, ComponentSystemEventListener componentListener) {
      throw new UnsupportedOperationException();
   }

   public void unsubscribeFromEvent(Class eventClass, ComponentSystemEventListener componentListener) {
      throw new UnsupportedOperationException();
   }

   public List getListenersForEventClass(Class eventClass) {
      throw new UnsupportedOperationException();
   }

   public UIComponent getNamingContainer() {
      for(UIComponent namingContainer = this; namingContainer != null; namingContainer = namingContainer.getParent()) {
         if (namingContainer instanceof NamingContainer) {
            return namingContainer;
         }
      }

      return null;
   }

   public abstract void processRestoreState(FacesContext var1, Object var2);

   public abstract void processDecodes(FacesContext var1);

   public void processEvent(ComponentSystemEvent event) throws AbortProcessingException {
      if (event instanceof PostRestoreStateEvent) {
         ValueExpression valueExpression = this.getValueExpression("binding");
         if (valueExpression != null) {
            valueExpression.setValue(FacesContext.getCurrentInstance().getELContext(), this);
         }

         this.isCompositeComponent = null;
      }

   }

   public abstract void processValidators(FacesContext var1);

   public abstract void processUpdates(FacesContext var1);

   public abstract Object processSaveState(FacesContext var1);

   protected abstract FacesContext getFacesContext();

   protected abstract Renderer getRenderer(FacesContext var1);

   private Map wrapBundleAsMap(final ResourceBundle bundle) {
      return new Map() {
         public String toString() {
            StringBuffer sb = new StringBuffer();
            Iterator entries = this.entrySet().iterator();

            while(entries.hasNext()) {
               Map.Entry cur = (Map.Entry)entries.next();
               sb.append((String)cur.getKey()).append(": ").append((String)cur.getValue()).append('\n');
            }

            return sb.toString();
         }

         public void clear() {
            throw new UnsupportedOperationException();
         }

         public boolean containsKey(Object key) {
            if (key != null) {
               return bundle.getString(key.toString()) != null;
            } else {
               return false;
            }
         }

         public boolean containsValue(Object value) {
            Enumeration keys = bundle.getKeys();

            do {
               if (!keys.hasMoreElements()) {
                  return false;
               }
            } while(!Objects.equals(value, bundle.getString((String)keys.nextElement())));

            return true;
         }

         public Set entrySet() {
            HashMap mappings = new HashMap();
            Enumeration keys = bundle.getKeys();

            while(keys.hasMoreElements()) {
               String key = (String)keys.nextElement();
               String value = bundle.getString(key);
               mappings.put(key, value);
            }

            return mappings.entrySet();
         }

         public boolean equals(Object obj) {
            return obj != null && obj instanceof Map && this.entrySet().equals(((Map)obj).entrySet());
         }

         public String get(Object key) {
            if (key == null) {
               return null;
            } else {
               try {
                  return bundle.getString(key.toString());
               } catch (MissingResourceException var3) {
                  return "???" + key + "???";
               }
            }
         }

         public int hashCode() {
            return bundle.hashCode();
         }

         public boolean isEmpty() {
            return !bundle.getKeys().hasMoreElements();
         }

         public Set keySet() {
            Set keySet = new HashSet();
            Enumeration keys = bundle.getKeys();

            while(keys.hasMoreElements()) {
               keySet.add(keys.nextElement());
            }

            return keySet;
         }

         public String put(String k, String v) {
            throw new UnsupportedOperationException();
         }

         public void putAll(Map m) {
            throw new UnsupportedOperationException();
         }

         public String remove(Object k) {
            throw new UnsupportedOperationException();
         }

         public int size() {
            int result = 0;

            for(Enumeration keys = bundle.getKeys(); keys.hasMoreElements(); ++result) {
               keys.nextElement();
            }

            return result;
         }

         public Collection values() {
            List result = new ArrayList();
            Enumeration keys = bundle.getKeys();

            while(keys.hasMoreElements()) {
               result.add(bundle.getString((String)keys.nextElement()));
            }

            return result;
         }
      };
   }

   private ResourceBundle findResourceBundleUnderFQCNofThis(FacesContext context) {
      String className = this.getClass().getName();
      Locale currentLocale = null;
      UIViewRoot root = null;
      ResourceBundle resourceBundle = null;
      if (context != null && (root = context.getViewRoot()) != null) {
         currentLocale = root.getLocale();
      }

      if (currentLocale == null) {
         currentLocale = Locale.getDefault();
      }

      try {
         resourceBundle = ResourceBundle.getBundle(className, currentLocale);
      } catch (MissingResourceException var7) {
      }

      return resourceBundle;
   }

   private ResourceBundle findResourceBundleAsResource(FacesContext context) {
      if (this.getAttributes().containsKey("javax.faces.application.Resource.ComponentResource")) {
         Resource ccResource = (Resource)this.getAttributes().get("javax.faces.application.Resource.ComponentResource");
         if (ccResource != null) {
            ccResource = this.findComponentResourceBundleLocaleMatch(context, ccResource.getResourceName(), ccResource.getLibraryName());
            if (ccResource != null) {
               try {
                  InputStream propertiesInputStream = ccResource.getInputStream();
                  Throwable var4 = null;

                  PropertyResourceBundle var5;
                  try {
                     var5 = new PropertyResourceBundle(propertiesInputStream);
                  } catch (Throwable var15) {
                     var4 = var15;
                     throw var15;
                  } finally {
                     if (propertiesInputStream != null) {
                        if (var4 != null) {
                           try {
                              propertiesInputStream.close();
                           } catch (Throwable var14) {
                              var4.addSuppressed(var14);
                           }
                        } else {
                           propertiesInputStream.close();
                        }
                     }

                  }

                  return var5;
               } catch (IOException var17) {
                  Logger.getLogger(UIComponent.class.getName()).log(Level.SEVERE, (String)null, var17);
               }
            }
         }
      }

      return null;
   }

   private Resource findComponentResourceBundleLocaleMatch(FacesContext context, String resourceName, String libraryName) {
      Resource result = null;
      ResourceBundle resourceBundle = null;
      int i;
      if (-1 != (i = resourceName.lastIndexOf("."))) {
         resourceName = resourceName.substring(0, i) + ".properties";
         if (null != context) {
            result = context.getApplication().getResourceHandler().createResource(resourceName, libraryName);
            InputStream propertiesInputStream = null;

            try {
               propertiesInputStream = result.getInputStream();
               resourceBundle = new PropertyResourceBundle(propertiesInputStream);
            } catch (IOException var16) {
               Logger.getLogger(UIComponent.class.getName()).log(Level.SEVERE, (String)null, var16);
            } finally {
               if (propertiesInputStream != null) {
                  try {
                     propertiesInputStream.close();
                  } catch (IOException var17) {
                     if (LOGGER.isLoggable(Level.SEVERE)) {
                        LOGGER.log(Level.SEVERE, (String)null, var17);
                     }
                  }
               }

            }
         }
      }

      return resourceBundle != null ? result : null;
   }

   /** @deprecated */
   public abstract ValueBinding getValueBinding(String var1);

   /** @deprecated */
   public abstract void setValueBinding(String var1, ValueBinding var2);

   static final class ComponentSystemEventListenerAdapter implements ComponentSystemEventListener, SystemEventListener, StateHolder, FacesWrapper {
      ComponentSystemEventListener wrapped;
      Class instanceClass;

      ComponentSystemEventListenerAdapter() {
      }

      ComponentSystemEventListenerAdapter(ComponentSystemEventListener wrapped, UIComponent component) {
         this.wrapped = wrapped;
         this.instanceClass = component.getClass();
      }

      public void processEvent(SystemEvent event) throws AbortProcessingException {
         this.wrapped.processEvent((ComponentSystemEvent)event);
      }

      public void processEvent(ComponentSystemEvent event) throws AbortProcessingException {
         this.wrapped.processEvent(event);
      }

      public boolean isListenerForSource(Object component) {
         return this.wrapped instanceof SystemEventListener ? ((SystemEventListener)this.wrapped).isListenerForSource(component) : this.instanceClass.isAssignableFrom(component.getClass());
      }

      public Object saveState(FacesContext context) {
         if (context == null) {
            throw new NullPointerException();
         } else {
            return new Object[]{this.wrapped instanceof UIComponent ? null : new StateHolderSaver(context, this.wrapped), this.instanceClass};
         }
      }

      public void restoreState(FacesContext context, Object state) {
         if (context == null) {
            throw new NullPointerException();
         } else if (state != null) {
            Object[] s = (Object[])((Object[])state);
            Object listener = s[0];
            this.wrapped = (ComponentSystemEventListener)((ComponentSystemEventListener)(listener == null ? UIComponent.getCurrentComponent(context) : ((StateHolderSaver)listener).restore(context)));
            this.instanceClass = (Class)s[1];
         }
      }

      public boolean isTransient() {
         return this.wrapped instanceof StateHolder ? ((StateHolder)this.wrapped).isTransient() : false;
      }

      public void setTransient(boolean newTransientValue) {
      }

      public ComponentSystemEventListener getWrapped() {
         return this.wrapped;
      }

      public int hashCode() {
         return this.wrapped.hashCode() ^ this.instanceClass.hashCode();
      }

      public boolean equals(Object obj) {
         if (!(obj instanceof ComponentSystemEventListenerAdapter)) {
            return false;
         } else {
            ComponentSystemEventListenerAdapter in = (ComponentSystemEventListenerAdapter)obj;
            return this.wrapped.equals(in.wrapped) && this.instanceClass.equals(in.instanceClass);
         }
      }
   }

   static enum PropertyKeys {
      rendered,
      attributes,
      bindings,
      rendererType,
      systemEventListeners,
      behaviors,
      passThroughAttributes;
   }

   static enum PropertyKeysPrivate {
      attributesThatAreSet;
   }
}
