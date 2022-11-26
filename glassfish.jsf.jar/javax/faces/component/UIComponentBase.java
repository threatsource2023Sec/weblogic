package javax.faces.component;

import com.sun.faces.application.ValueBindingValueExpressionAdapter;
import com.sun.faces.application.ValueExpressionValueBindingAdapter;
import com.sun.faces.util.Util;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.component.behavior.Behavior;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.BehaviorEvent;
import javax.faces.event.ComponentSystemEventListener;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.event.PostValidateEvent;
import javax.faces.event.PreRemoveFromViewEvent;
import javax.faces.event.PreRenderComponentEvent;
import javax.faces.event.PreValidateEvent;
import javax.faces.event.SystemEventListener;
import javax.faces.render.Renderer;

public abstract class UIComponentBase extends UIComponent {
   private static Logger LOGGER = Logger.getLogger("javax.faces.component", "javax.faces.LogStrings");
   private static final String ADDED = UIComponentBase.class.getName() + ".ADDED";
   private static final int MY_STATE = 0;
   private static final int CHILD_STATE = 1;
   private Map descriptors;
   private Map propertyDescriptorMap;
   private Map listenersByEventClass;
   private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
   private AttributesMap attributes;
   private String id;
   private String clientId;
   private UIComponent parent;
   private List children;
   private Map facets;
   private AttachedObjectListHolder listeners;
   private boolean transientFlag;
   private BehaviorsMap behaviors;
   private static final Object[] EMPTY_ARRAY = new Object[0];
   private static final Iterator EMPTY_ITERATOR = new Iterator() {
      public void remove() {
         throw new UnsupportedOperationException();
      }

      public UIComponent next() {
         throw new NoSuchElementException("Empty Iterator");
      }

      public boolean hasNext() {
         return false;
      }
   };

   public UIComponentBase() {
      this.populateDescriptorsMapIfNecessary();
   }

   public Map getAttributes() {
      if (this.attributes == null) {
         this.attributes = new AttributesMap(this);
      }

      return this.attributes;
   }

   public Map getPassThroughAttributes(boolean create) {
      Map passThroughAttributes = (Map)this.getStateHelper().get(UIComponent.PropertyKeys.passThroughAttributes);
      if (passThroughAttributes == null && create) {
         passThroughAttributes = new PassThroughAttributesMap();
         this.getStateHelper().put(UIComponent.PropertyKeys.passThroughAttributes, passThroughAttributes);
      }

      return (Map)passThroughAttributes;
   }

   public String getClientId(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         if (this.clientId == null) {
            UIComponent namingContainerAncestor = this.getNamingContainerAncestor();
            String parentId = this.getParentId(context, namingContainerAncestor);
            this.clientId = this.getId();
            if (this.clientId == null) {
               this.setId(this.generateId(context, namingContainerAncestor));
               this.clientId = this.getId();
            }

            if (parentId != null) {
               this.clientId = this.addParentId(context, parentId, this.clientId);
            }

            Renderer renderer = this.getRenderer(context);
            if (renderer != null) {
               this.clientId = renderer.convertClientId(context, this.clientId);
            }
         }

         return this.clientId;
      }
   }

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      if (this.id == null || !this.id.equals(id)) {
         validateId(id);
         this.id = id;
      }

      this.clientId = null;
   }

   public UIComponent getParent() {
      return this.parent;
   }

   public void setParent(UIComponent parent) {
      if (parent == null) {
         if (this.parent != null) {
            this.doPreRemoveProcessing(FacesContext.getCurrentInstance(), this);
            this.parent = parent;
         }

         this.compositeParent = null;
      } else {
         this.parent = parent;
         if (this.getAttributes().get(ADDED) == null) {
            this.getAttributes().put(ADDED, Boolean.TRUE);
            this.doPostAddProcessing(FacesContext.getCurrentInstance(), this);
            this.getAttributes().remove(ADDED);
         }
      }

   }

   public boolean isRendered() {
      return Boolean.valueOf(this.getStateHelper().eval(UIComponent.PropertyKeys.rendered, Boolean.TRUE).toString());
   }

   public void setRendered(boolean rendered) {
      this.getStateHelper().put(UIComponent.PropertyKeys.rendered, rendered);
   }

   public String getRendererType() {
      return (String)this.getStateHelper().eval(UIComponent.PropertyKeys.rendererType);
   }

   public void setRendererType(String rendererType) {
      this.getStateHelper().put(UIComponent.PropertyKeys.rendererType, rendererType);
   }

   public boolean getRendersChildren() {
      if (this.getRendererType() != null) {
         Renderer renderer = this.getRenderer(this.getFacesContext());
         if (renderer != null) {
            return renderer.getRendersChildren();
         }
      }

      return false;
   }

   public List getChildren() {
      if (this.children == null) {
         this.children = new ChildrenList(this);
      }

      return this.children;
   }

   public int getChildCount() {
      return this.children != null ? this.children.size() : 0;
   }

   public UIComponent findComponent(String expression) {
      if (expression == null) {
         throw new NullPointerException();
      } else if (expression.isEmpty()) {
         throw new IllegalArgumentException("\"\"");
      } else {
         char sepChar = UINamingContainer.getSeparatorChar(FacesContext.getCurrentInstance());
         UIComponent base = this.findBaseComponent(expression, sepChar);
         if (expression.charAt(0) == sepChar) {
            expression = expression.substring(1);
         }

         return this.evaluateSearchExpression(base, expression, String.valueOf(sepChar));
      }
   }

   public boolean invokeOnComponent(FacesContext context, String clientId, ContextCallback callback) throws FacesException {
      return super.invokeOnComponent(context, clientId, callback);
   }

   public Map getFacets() {
      if (this.facets == null) {
         this.facets = new FacetsMap(this);
      }

      return this.facets;
   }

   public int getFacetCount() {
      return this.facets != null ? this.facets.size() : 0;
   }

   public UIComponent getFacet(String name) {
      return this.facets != null ? (UIComponent)this.facets.get(name) : null;
   }

   public Iterator getFacetsAndChildren() {
      int childCount = this.getChildCount();
      int facetCount = this.getFacetCount();
      if (childCount == 0 && facetCount == 0) {
         return EMPTY_ITERATOR;
      } else if (childCount == 0) {
         return Collections.unmodifiableCollection(this.getFacets().values()).iterator();
      } else {
         return (Iterator)(facetCount == 0 ? Collections.unmodifiableList(this.getChildren()).iterator() : new FacetsAndChildrenIterator(this));
      }
   }

   public void broadcast(FacesEvent event) throws AbortProcessingException {
      if (event == null) {
         throw new NullPointerException();
      } else {
         if (event instanceof BehaviorEvent) {
            BehaviorEvent behaviorEvent = (BehaviorEvent)event;
            Behavior behavior = behaviorEvent.getBehavior();
            behavior.broadcast(behaviorEvent);
         }

         if (this.listeners != null) {
            FacesListener[] var6 = (FacesListener[])this.listeners.asArray(FacesListener.class);
            int var7 = var6.length;

            for(int var4 = 0; var4 < var7; ++var4) {
               FacesListener listener = var6[var4];
               if (event.isAppropriateListener(listener)) {
                  event.processListener(listener);
               }
            }

         }
      }
   }

   public void decode(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         String rendererType = this.getRendererType();
         if (rendererType != null) {
            Renderer renderer = this.getRenderer(context);
            if (renderer != null) {
               renderer.decode(context, this);
            } else if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.fine("Can't get Renderer for type " + rendererType);
            }
         }

      }
   }

   public void encodeBegin(FacesContext context) throws IOException {
      if (context == null) {
         throw new NullPointerException();
      } else {
         this.pushComponentToEL(context, (UIComponent)null);
         if (this.isRendered()) {
            context.getApplication().publishEvent(context, PreRenderComponentEvent.class, this);
            String rendererType = this.getRendererType();
            if (rendererType != null) {
               Renderer renderer = this.getRenderer(context);
               if (renderer != null) {
                  renderer.encodeBegin(context, this);
               } else if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.fine("Can't get Renderer for type " + rendererType);
               }
            }

         }
      }
   }

   public void encodeChildren(FacesContext context) throws IOException {
      if (context == null) {
         throw new NullPointerException();
      } else if (this.isRendered()) {
         if (this.getRendererType() != null) {
            Renderer renderer = this.getRenderer(context);
            if (renderer != null) {
               renderer.encodeChildren(context, this);
            }
         } else if (this.getChildCount() > 0) {
            Iterator var4 = this.getChildren().iterator();

            while(var4.hasNext()) {
               UIComponent child = (UIComponent)var4.next();
               child.encodeAll(context);
            }
         }

      }
   }

   public void encodeEnd(FacesContext context) throws IOException {
      if (context == null) {
         throw new NullPointerException();
      } else if (!this.isRendered()) {
         this.popComponentFromEL(context);
      } else {
         if (this.getRendererType() != null) {
            Renderer renderer = this.getRenderer(context);
            if (renderer != null) {
               renderer.encodeEnd(context, this);
            }
         }

         this.popComponentFromEL(context);
      }
   }

   protected void addFacesListener(FacesListener listener) {
      if (listener == null) {
         throw new NullPointerException();
      } else {
         if (this.listeners == null) {
            this.listeners = new AttachedObjectListHolder();
         }

         this.listeners.add(listener);
      }
   }

   protected FacesListener[] getFacesListeners(Class clazz) {
      if (clazz == null) {
         throw new NullPointerException();
      } else if (!FacesListener.class.isAssignableFrom(clazz)) {
         throw new IllegalArgumentException();
      } else if (this.listeners == null) {
         return (FacesListener[])((FacesListener[])Array.newInstance(clazz, 0));
      } else {
         FacesListener[] listeners = (FacesListener[])this.listeners.asArray(FacesListener.class);
         if (listeners.length == 0) {
            return (FacesListener[])((FacesListener[])Array.newInstance(clazz, 0));
         } else {
            List results = new ArrayList(listeners.length);
            FacesListener[] var4 = listeners;
            int var5 = listeners.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               FacesListener listener = var4[var6];
               if (clazz.isAssignableFrom(listener.getClass())) {
                  results.add(listener);
               }
            }

            return (FacesListener[])results.toArray((FacesListener[])((FacesListener[])Array.newInstance(clazz, results.size())));
         }
      }
   }

   protected void removeFacesListener(FacesListener listener) {
      if (listener == null) {
         throw new NullPointerException();
      } else {
         if (this.listeners != null) {
            this.listeners.remove(listener);
         }

      }
   }

   public void queueEvent(FacesEvent event) {
      if (event == null) {
         throw new NullPointerException();
      } else {
         UIComponent parent = this.getParent();
         if (parent == null) {
            throw new IllegalStateException();
         } else {
            parent.queueEvent(event);
         }
      }
   }

   public void subscribeToEvent(Class eventClass, ComponentSystemEventListener componentListener) {
      if (Util.isAnyNull(eventClass, componentListener)) {
         throw new NullPointerException();
      } else {
         if (this.initialStateMarked()) {
            this.initialState = false;
         }

         if (this.listenersByEventClass == null) {
            this.listenersByEventClass = new HashMap(3, 1.0F);
         }

         SystemEventListener facesLifecycleListener = new UIComponent.ComponentSystemEventListenerAdapter(componentListener, this);
         List listenersForEventClass = (List)this.listenersByEventClass.get(eventClass);
         if (listenersForEventClass == null) {
            listenersForEventClass = new ArrayList(3);
            this.listenersByEventClass.put(eventClass, listenersForEventClass);
         }

         if (!((List)listenersForEventClass).contains(facesLifecycleListener)) {
            ((List)listenersForEventClass).add(facesLifecycleListener);
         }

      }
   }

   public void unsubscribeFromEvent(Class eventClass, ComponentSystemEventListener componentListener) {
      if (Util.isAnyNull(eventClass, componentListener)) {
         throw new NullPointerException();
      } else {
         List listeners = this.getListenersForEventClass(eventClass);
         if (!Util.isEmpty((Collection)listeners)) {
            Iterator i = listeners.iterator();

            while(i.hasNext()) {
               ComponentSystemEventListener existingListener = ((UIComponent.ComponentSystemEventListenerAdapter)i.next()).getWrapped();
               if (existingListener.equals(componentListener)) {
                  i.remove();
                  break;
               }
            }
         }

      }
   }

   public List getListenersForEventClass(Class eventClass) {
      if (eventClass == null) {
         throw new NullPointerException();
      } else {
         return this.listenersByEventClass != null ? (List)this.listenersByEventClass.get(eventClass) : null;
      }
   }

   public void processDecodes(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else if (this.isRendered()) {
         this.pushComponentToEL(context, (UIComponent)null);

         try {
            Iterator kids = this.getFacetsAndChildren();

            while(kids.hasNext()) {
               UIComponent kid = (UIComponent)kids.next();
               kid.processDecodes(context);
            }

            try {
               this.decode(context);
            } catch (RuntimeException var7) {
               context.renderResponse();
               throw var7;
            }
         } finally {
            this.popComponentFromEL(context);
         }

      }
   }

   public void processValidators(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else if (this.isRendered()) {
         this.pushComponentToEL(context, (UIComponent)null);

         try {
            Application application = context.getApplication();
            application.publishEvent(context, PreValidateEvent.class, this);
            Iterator kids = this.getFacetsAndChildren();

            while(kids.hasNext()) {
               UIComponent kid = (UIComponent)kids.next();
               kid.processValidators(context);
            }

            application.publishEvent(context, PostValidateEvent.class, this);
         } finally {
            this.popComponentFromEL(context);
         }
      }
   }

   public void processUpdates(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else if (this.isRendered()) {
         this.pushComponentToEL(context, (UIComponent)null);

         try {
            Iterator kids = this.getFacetsAndChildren();

            while(kids.hasNext()) {
               UIComponent kid = (UIComponent)kids.next();
               kid.processUpdates(context);
            }
         } finally {
            this.popComponentFromEL(context);
         }

      }
   }

   public Object processSaveState(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else if (this.isTransient()) {
         return null;
      } else {
         Object[] stateStruct = new Object[2];
         Object[] childState = EMPTY_ARRAY;
         this.pushComponentToEL(context, (UIComponent)null);

         try {
            stateStruct[0] = this.saveState(context);
            int count = this.getChildCount() + this.getFacetCount();
            if (count > 0) {
               List stateList = new ArrayList(count);
               this.collectChildState(context, stateList);
               this.collectFacetsState(context, stateList);
               childState = stateList.toArray();
            }
         } finally {
            this.popComponentFromEL(context);
         }

         stateStruct[1] = childState;
         return stateStruct;
      }
   }

   public void processRestoreState(FacesContext context, Object state) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         this.pushComponentToEL(context, (UIComponent)null);

         try {
            Object[] stateStruct = (Object[])((Object[])state);
            Object[] childState = (Object[])((Object[])stateStruct[1]);
            this.restoreState(context, stateStruct[0]);
            int i = this.restoreChildState(context, childState);
            this.restoreFacetsState(context, childState, i);
         } finally {
            this.popComponentFromEL(context);
         }

      }
   }

   protected FacesContext getFacesContext() {
      return FacesContext.getCurrentInstance();
   }

   protected Renderer getRenderer(FacesContext context) {
      Renderer renderer = null;
      String rendererType = this.getRendererType();
      if (rendererType != null) {
         renderer = context.getRenderKit().getRenderer(this.getFamily(), rendererType);
         if (renderer == null && LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine("Can't get Renderer for type " + rendererType);
         }
      } else if (LOGGER.isLoggable(Level.FINE)) {
         String id = this.getId();
         LOGGER.fine("No renderer-type for component " + id != null ? id : this.getClass().getName());
      }

      return renderer;
   }

   public void markInitialState() {
      super.markInitialState();
      if (this.listeners != null) {
         this.listeners.markInitialState();
      }

      Iterator var1;
      if (this.listenersByEventClass != null) {
         var1 = this.listenersByEventClass.values().iterator();

         while(var1.hasNext()) {
            List listener = (List)var1.next();
            if (listener instanceof PartialStateHolder) {
               ((PartialStateHolder)listener).markInitialState();
            }
         }
      }

      if (this.behaviors != null) {
         var1 = this.behaviors.entrySet().iterator();

         while(var1.hasNext()) {
            Map.Entry entry = (Map.Entry)var1.next();
            Iterator var3 = ((List)entry.getValue()).iterator();

            while(var3.hasNext()) {
               ClientBehavior behavior = (ClientBehavior)var3.next();
               if (behavior instanceof PartialStateHolder) {
                  ((PartialStateHolder)behavior).markInitialState();
               }
            }
         }
      }

   }

   public void clearInitialState() {
      super.clearInitialState();
      if (this.listeners != null) {
         this.listeners.clearInitialState();
      }

      Iterator var1;
      if (this.listenersByEventClass != null) {
         var1 = this.listenersByEventClass.values().iterator();

         while(var1.hasNext()) {
            List listener = (List)var1.next();
            if (listener instanceof PartialStateHolder) {
               ((PartialStateHolder)listener).clearInitialState();
            }
         }
      }

      if (this.behaviors != null) {
         var1 = this.behaviors.entrySet().iterator();

         while(var1.hasNext()) {
            Map.Entry entry = (Map.Entry)var1.next();
            Iterator var3 = ((List)entry.getValue()).iterator();

            while(var3.hasNext()) {
               ClientBehavior behavior = (ClientBehavior)var3.next();
               if (behavior instanceof PartialStateHolder) {
                  ((PartialStateHolder)behavior).clearInitialState();
               }
            }
         }
      }

   }

   public Object saveState(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         Object[] values = null;
         if (this.initialStateMarked()) {
            Object savedFacesListeners = this.listeners != null ? this.listeners.saveState(context) : null;
            Object savedSysEventListeners = this.saveSystemEventListeners(context);
            Object savedBehaviors = this.saveBehaviorsState(context);
            Object savedBindings = null;
            if (this.bindings != null) {
               savedBindings = this.saveBindingsState(context);
            }

            Object savedHelper = null;
            if (this.stateHelper != null) {
               savedHelper = this.stateHelper.saveState(context);
            }

            if (Util.isAllNull(savedFacesListeners, savedSysEventListeners, savedBehaviors, savedBindings, savedHelper)) {
               return null;
            } else {
               if (values == null || values.length != 5) {
                  values = new Object[5];
               }

               values[0] = savedFacesListeners;
               values[1] = savedSysEventListeners;
               values[2] = savedBehaviors;
               values[3] = savedBindings;
               values[4] = savedHelper;
               return values;
            }
         } else {
            if (values == null || values.length != 6) {
               values = new Object[6];
            }

            values[0] = this.listeners != null ? this.listeners.saveState(context) : null;
            values[1] = this.saveSystemEventListeners(context);
            values[2] = this.saveBehaviorsState(context);
            if (this.bindings != null) {
               values[3] = this.saveBindingsState(context);
            }

            if (this.stateHelper != null) {
               values[4] = this.stateHelper.saveState(context);
            }

            values[5] = this.id;
            return values;
         }
      }
   }

   public void restoreState(FacesContext context, Object state) {
      if (context == null) {
         throw new NullPointerException();
      } else if (state != null) {
         Object[] values = (Object[])((Object[])state);
         if (values[0] != null) {
            if (this.listeners == null) {
               this.listeners = new AttachedObjectListHolder();
            }

            this.listeners.restoreState(context, values[0]);
         }

         if (values[1] != null) {
            Map restoredListeners = this.restoreSystemEventListeners(context, values[1]);
            if (this.listenersByEventClass != null) {
               this.listenersByEventClass.putAll(restoredListeners);
            } else {
               this.listenersByEventClass = restoredListeners;
            }
         }

         if (values[2] != null) {
            this.behaviors = this.restoreBehaviorsState(context, values[2]);
         }

         if (values[3] != null) {
            this.bindings = restoreBindingsState(context, values[3]);
         }

         if (values[4] != null) {
            this.getStateHelper().restoreState(context, values[4]);
         }

         if (values.length == 6 && values[5] != null) {
            this.id = (String)values[5];
         }

      }
   }

   public boolean isTransient() {
      return this.transientFlag;
   }

   public void setTransient(boolean transientFlag) {
      this.transientFlag = transientFlag;
   }

   public static Object saveAttachedState(FacesContext context, Object attachedObject) {
      if (context == null) {
         throw new NullPointerException();
      } else if (attachedObject == null) {
         return null;
      } else {
         Class mapOrCollectionClass = attachedObject.getClass();
         boolean newWillSucceed = true;

         try {
            int modifiers = mapOrCollectionClass.getModifiers();
            newWillSucceed = Modifier.isPublic(modifiers);
            if (newWillSucceed) {
               newWillSucceed = null != mapOrCollectionClass.getConstructor();
            }
         } catch (Exception var11) {
            newWillSucceed = false;
         }

         Object result;
         ArrayList resultList;
         Object value;
         if (newWillSucceed && attachedObject instanceof Collection) {
            Collection attachedCollection = (Collection)attachedObject;
            resultList = null;
            Iterator var14 = attachedCollection.iterator();

            while(true) {
               do {
                  do {
                     if (!var14.hasNext()) {
                        result = resultList;
                        return result;
                     }

                     value = var14.next();
                  } while(value == null);
               } while(value instanceof StateHolder && ((StateHolder)value).isTransient());

               if (resultList == null) {
                  resultList = new ArrayList(attachedCollection.size() + 1);
                  resultList.add(new StateHolderSaver(context, mapOrCollectionClass));
               }

               resultList.add(new StateHolderSaver(context, value));
            }
         } else if (newWillSucceed && attachedObject instanceof Map) {
            Map attachedMap = (Map)attachedObject;
            resultList = null;
            Iterator var9 = attachedMap.entrySet().iterator();

            while(true) {
               Object key;
               do {
                  Map.Entry entry;
                  do {
                     if (!var9.hasNext()) {
                        result = resultList;
                        return result;
                     }

                     entry = (Map.Entry)var9.next();
                     key = entry.getKey();
                  } while(key instanceof StateHolder && ((StateHolder)key).isTransient());

                  value = entry.getValue();
               } while(value instanceof StateHolder && ((StateHolder)value).isTransient());

               if (resultList == null) {
                  resultList = new ArrayList(attachedMap.size() * 2 + 1);
                  resultList.add(new StateHolderSaver(context, mapOrCollectionClass));
               }

               resultList.add(new StateHolderSaver(context, key));
               resultList.add(new StateHolderSaver(context, value));
            }
         } else {
            result = new StateHolderSaver(context, attachedObject);
            return result;
         }
      }
   }

   public static Object restoreAttachedState(FacesContext context, Object stateObj) throws IllegalStateException {
      if (null == context) {
         throw new NullPointerException();
      } else if (null == stateObj) {
         return null;
      } else {
         Object result;
         if (stateObj instanceof List) {
            List stateList = (List)stateObj;
            StateHolderSaver collectionSaver = (StateHolderSaver)stateList.get(0);
            Class mapOrCollection = (Class)collectionSaver.restore(context);
            Collection retCollection;
            int i;
            int len;
            if (Collection.class.isAssignableFrom(mapOrCollection)) {
               retCollection = null;

               try {
                  retCollection = (Collection)mapOrCollection.newInstance();
               } catch (IllegalAccessException | InstantiationException var10) {
                  if (LOGGER.isLoggable(Level.SEVERE)) {
                     LOGGER.log(Level.SEVERE, var10.toString(), var10);
                  }

                  throw new IllegalStateException("Unknown object type");
               }

               i = 1;

               for(len = stateList.size(); i < len; ++i) {
                  try {
                     retCollection.add(((StateHolderSaver)stateList.get(i)).restore(context));
                  } catch (ClassCastException var11) {
                     if (LOGGER.isLoggable(Level.SEVERE)) {
                        LOGGER.log(Level.SEVERE, var11.toString(), var11);
                     }

                     throw new IllegalStateException("Unknown object type");
                  }
               }

               result = retCollection;
            } else {
               retCollection = null;

               Map retMap;
               try {
                  retMap = (Map)mapOrCollection.newInstance();
               } catch (IllegalAccessException | InstantiationException var13) {
                  if (LOGGER.isLoggable(Level.SEVERE)) {
                     LOGGER.log(Level.SEVERE, var13.toString(), var13);
                  }

                  throw new IllegalStateException("Unknown object type");
               }

               i = 1;

               for(len = stateList.size(); i < len; i += 2) {
                  try {
                     retMap.put(((StateHolderSaver)stateList.get(i)).restore(context), ((StateHolderSaver)stateList.get(i + 1)).restore(context));
                  } catch (ClassCastException var12) {
                     if (LOGGER.isLoggable(Level.SEVERE)) {
                        LOGGER.log(Level.SEVERE, var12.toString(), var12);
                     }

                     throw new IllegalStateException("Unknown object type");
                  }
               }

               result = retMap;
            }
         } else {
            if (!(stateObj instanceof StateHolderSaver)) {
               throw new IllegalStateException("Unknown object type");
            }

            StateHolderSaver saver = (StateHolderSaver)stateObj;
            result = saver.restore(context);
         }

         return result;
      }
   }

   private static Map restoreBindingsState(FacesContext context, Object state) {
      if (state == null) {
         return null;
      } else {
         Object[] values = (Object[])((Object[])state);
         String[] names = (String[])((String[])values[0]);
         Object[] states = (Object[])((Object[])values[1]);
         Map bindings = new HashMap(names.length);

         for(int i = 0; i < names.length; ++i) {
            bindings.put(names[i], (ValueExpression)restoreAttachedState(context, states[i]));
         }

         return bindings;
      }
   }

   private Object saveBindingsState(FacesContext context) {
      if (this.bindings == null) {
         return null;
      } else {
         Object[] values = new Object[]{this.bindings.keySet().toArray(new String[this.bindings.size()]), null};
         Object[] bindingValues = this.bindings.values().toArray();

         for(int i = 0; i < bindingValues.length; ++i) {
            bindingValues[i] = saveAttachedState(context, bindingValues[i]);
         }

         values[1] = bindingValues;
         return values;
      }
   }

   private Object saveSystemEventListeners(FacesContext ctx) {
      if (this.listenersByEventClass == null) {
         return null;
      } else {
         int size = this.listenersByEventClass.size();
         Object[][] listeners = new Object[size][2];
         int idx = 0;
         boolean savedState = false;
         Iterator var6 = this.listenersByEventClass.entrySet().iterator();

         while(var6.hasNext()) {
            Map.Entry e = (Map.Entry)var6.next();
            Object[] target = listeners[idx++];
            target[0] = e.getKey();
            target[1] = saveAttachedState(ctx, e.getValue());
            if (target[1] == null) {
               target[0] = null;
            } else {
               savedState = true;
            }
         }

         return savedState ? listeners : null;
      }
   }

   private Map restoreSystemEventListeners(FacesContext ctx, Object state) {
      if (state == null) {
         return null;
      } else {
         Object[][] listeners = (Object[][])((Object[][])state);
         Map m = new HashMap(listeners.length, 1.0F);
         int i = 0;

         for(int len = listeners.length; i < len; ++i) {
            Object[] source = listeners[i];
            m.put((Class)source[0], (List)restoreAttachedState(ctx, source[1]));
         }

         return m;
      }
   }

   Map getDescriptorMap() {
      return this.propertyDescriptorMap;
   }

   private void doPostAddProcessing(FacesContext context, UIComponent added) {
      if (this.parent.isInView()) {
         publishAfterViewEvents(context, context.getApplication(), added);
      }

   }

   private void doPreRemoveProcessing(FacesContext context, UIComponent toRemove) {
      if (this.parent.isInView()) {
         disconnectFromView(context, context.getApplication(), toRemove);
      }

   }

   public void addClientBehavior(String eventName, ClientBehavior behavior) {
      this.assertClientBehaviorHolder();
      Collection eventNames = this.getEventNames();
      if (eventNames == null) {
         throw new IllegalStateException("Attempting to add a Behavior to a component that does not support any event types. getEventTypes() must return a non-null Set.");
      } else {
         if (eventNames.contains(eventName)) {
            if (this.initialStateMarked() && this.behaviors != null) {
               Iterator var4 = this.behaviors.entrySet().iterator();

               while(var4.hasNext()) {
                  Map.Entry entry = (Map.Entry)var4.next();
                  Iterator var6 = ((List)entry.getValue()).iterator();

                  while(var6.hasNext()) {
                     ClientBehavior b = (ClientBehavior)var6.next();
                     if (b instanceof PartialStateHolder) {
                        ((PartialStateHolder)behavior).clearInitialState();
                     }
                  }
               }
            }

            if (null == this.behaviors) {
               Map modifiableMap = new HashMap(5, 1.0F);
               this.behaviors = new BehaviorsMap(modifiableMap);
            }

            List eventBehaviours = (List)this.behaviors.get(eventName);
            if (null == eventBehaviours) {
               eventBehaviours = new ArrayList(3);
               this.behaviors.getModifiableMap().put(eventName, eventBehaviours);
            }

            ((List)eventBehaviours).add(behavior);
         }

      }
   }

   public Collection getEventNames() {
      this.assertClientBehaviorHolder();
      return null;
   }

   public Map getClientBehaviors() {
      return (Map)(null == this.behaviors ? Collections.emptyMap() : this.behaviors);
   }

   public String getDefaultEventName() {
      this.assertClientBehaviorHolder();
      return null;
   }

   private void assertClientBehaviorHolder() {
      if (!this.isClientBehaviorHolder()) {
         throw new IllegalStateException("Attempting to use a Behavior feature with a component that does not support any event types. Component must implement BehaviourHolder interface.");
      }
   }

   private boolean isClientBehaviorHolder() {
      return ClientBehaviorHolder.class.isInstance(this);
   }

   private Object saveBehaviorsState(FacesContext context) {
      Object state = null;
      if (null != this.behaviors && this.behaviors.size() > 0) {
         boolean stateWritten = false;
         Object[] attachedBehaviors = new Object[this.behaviors.size()];
         int i = 0;

         Object[] attachedEventBehaviors;
         for(Iterator var6 = this.behaviors.values().iterator(); var6.hasNext(); attachedBehaviors[i++] = attachedEventBehaviors) {
            List eventBehaviors = (List)var6.next();
            attachedEventBehaviors = new Object[eventBehaviors.size()];

            for(int j = 0; j < attachedEventBehaviors.length; ++j) {
               attachedEventBehaviors[j] = this.initialStateMarked() ? this.saveBehavior(context, (ClientBehavior)eventBehaviors.get(j)) : saveAttachedState(context, eventBehaviors.get(j));
               if (!stateWritten) {
                  stateWritten = attachedEventBehaviors[j] != null;
               }
            }
         }

         if (stateWritten) {
            state = new Object[]{this.behaviors.keySet().toArray(new String[this.behaviors.size()]), attachedBehaviors};
         }
      }

      return state;
   }

   private BehaviorsMap restoreBehaviorsState(FacesContext context, Object state) {
      if (null == state) {
         return null;
      } else {
         Object[] values = (Object[])((Object[])state);
         String[] names = (String[])((String[])values[0]);
         Object[] attachedBehaviors = (Object[])((Object[])values[1]);
         int i;
         if (this.initialStateMarked()) {
            int i = 0;

            for(i = names.length; i < i; ++i) {
               if (this.behaviors != null) {
                  List existingBehaviors = (List)this.behaviors.get(names[i]);
                  this.restoreBehaviors(context, existingBehaviors, (Object[])((Object[])attachedBehaviors[i]));
               }
            }

            return this.behaviors;
         } else {
            Map modifiableMap = new HashMap(names.length, 1.0F);

            for(i = 0; i < attachedBehaviors.length; ++i) {
               Object[] attachedEventBehaviors = (Object[])((Object[])attachedBehaviors[i]);
               ArrayList eventBehaviors = new ArrayList(attachedBehaviors.length);

               for(int j = 0; j < attachedEventBehaviors.length; ++j) {
                  eventBehaviors.add((ClientBehavior)restoreAttachedState(context, attachedEventBehaviors[j]));
               }

               modifiableMap.put(names[i], eventBehaviors);
            }

            return new BehaviorsMap(modifiableMap);
         }
      }
   }

   private Object saveBehavior(FacesContext ctx, ClientBehavior behavior) {
      return behavior instanceof StateHolder ? ((StateHolder)behavior).saveState(ctx) : null;
   }

   private void restoreBehaviors(FacesContext ctx, List existingBehaviors, Object[] state) {
      int i = 0;

      for(int len = state.length; i < len; ++i) {
         ClientBehavior behavior = (ClientBehavior)existingBehaviors.get(i);
         if (state[i] != null && behavior instanceof StateHolder) {
            if (state[i] instanceof StateHolderSaver) {
               ((StateHolderSaver)state[i]).restore(ctx);
            } else {
               ((StateHolder)behavior).restoreState(ctx, state[i]);
            }
         }
      }

   }

   private static void publishAfterViewEvents(FacesContext context, Application application, UIComponent component) {
      component.setInView(true);

      try {
         component.pushComponentToEL(context, component);
         application.publishEvent(context, PostAddToViewEvent.class, component);
         ArrayList clist;
         Iterator var4;
         UIComponent c;
         if (component.getChildCount() > 0) {
            clist = new ArrayList(component.getChildren());
            var4 = clist.iterator();

            while(var4.hasNext()) {
               c = (UIComponent)var4.next();
               publishAfterViewEvents(context, application, c);
            }
         }

         if (component.getFacetCount() > 0) {
            clist = new ArrayList(component.getFacets().values());
            var4 = clist.iterator();

            while(var4.hasNext()) {
               c = (UIComponent)var4.next();
               publishAfterViewEvents(context, application, c);
            }
         }
      } finally {
         component.popComponentFromEL(context);
      }

   }

   private static void disconnectFromView(FacesContext context, Application application, UIComponent component) {
      application.publishEvent(context, PreRemoveFromViewEvent.class, component);
      component.setInView(false);
      component.compositeParent = null;
      Iterator var4;
      UIComponent c;
      if (component.getChildCount() > 0) {
         List children = component.getChildren();
         var4 = children.iterator();

         while(var4.hasNext()) {
            c = (UIComponent)var4.next();
            disconnectFromView(context, application, c);
         }
      }

      if (component.getFacetCount() > 0) {
         Map facets = component.getFacets();
         var4 = facets.values().iterator();

         while(var4.hasNext()) {
            c = (UIComponent)var4.next();
            disconnectFromView(context, application, c);
         }
      }

   }

   private void populateDescriptorsMapIfNecessary() {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      Class clazz = this.getClass();
      if (facesContext != null && facesContext.getExternalContext() != null && facesContext.getExternalContext().getApplicationMap() != null) {
         Map applicationMap = facesContext.getExternalContext().getApplicationMap();
         if (!applicationMap.containsKey("com.sun.faces.compnent.COMPONENT_DESCRIPTORS_MAP")) {
            applicationMap.put("com.sun.faces.compnent.COMPONENT_DESCRIPTORS_MAP", new ConcurrentHashMap());
         }

         this.descriptors = (Map)applicationMap.get("com.sun.faces.compnent.COMPONENT_DESCRIPTORS_MAP");
         this.propertyDescriptorMap = (Map)this.descriptors.get(clazz);
      }

      if (this.propertyDescriptorMap == null) {
         PropertyDescriptor[] propertyDescriptors = this.getPropertyDescriptors();
         if (propertyDescriptors != null) {
            this.propertyDescriptorMap = new HashMap(propertyDescriptors.length, 1.0F);
            PropertyDescriptor[] var4 = propertyDescriptors;
            int var5 = propertyDescriptors.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               PropertyDescriptor propertyDescriptor = var4[var6];
               this.propertyDescriptorMap.put(propertyDescriptor.getName(), propertyDescriptor);
            }

            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "fine.component.populating_descriptor_map", new Object[]{clazz, Thread.currentThread().getName()});
            }

            if (this.descriptors != null && !this.descriptors.containsKey(clazz)) {
               this.descriptors.put(clazz, this.propertyDescriptorMap);
            }
         }
      }

   }

   private PropertyDescriptor[] getPropertyDescriptors() {
      try {
         return Introspector.getBeanInfo(this.getClass()).getPropertyDescriptors();
      } catch (IntrospectionException var2) {
         throw new FacesException(var2);
      }
   }

   private String addParentId(FacesContext context, String parentId, String childId) {
      return (new StringBuilder(parentId.length() + 1 + childId.length())).append(parentId).append(UINamingContainer.getSeparatorChar(context)).append(childId).toString();
   }

   private String getParentId(FacesContext context, UIComponent parent) {
      return parent == null ? null : parent.getContainerClientId(context);
   }

   private String generateId(FacesContext context, UIComponent namingContainerAncestor) {
      return namingContainerAncestor instanceof UniqueIdVendor ? ((UniqueIdVendor)namingContainerAncestor).createUniqueId(context, (String)null) : context.getViewRoot().createUniqueId();
   }

   private UIComponent getNamingContainerAncestor() {
      for(UIComponent namingContainer = this.getParent(); namingContainer != null; namingContainer = namingContainer.getParent()) {
         if (namingContainer instanceof NamingContainer) {
            return namingContainer;
         }
      }

      return null;
   }

   private static void eraseParent(UIComponent component) {
      UIComponent parent = component.getParent();
      if (parent != null) {
         if (parent.getChildCount() > 0) {
            List children = parent.getChildren();
            int index = children.indexOf(component);
            if (index >= 0) {
               children.remove(index);
               return;
            }
         }

         if (parent.getFacetCount() > 0) {
            Map facets = parent.getFacets();
            Iterator entries = facets.entrySet().iterator();

            while(entries.hasNext()) {
               Map.Entry entry = (Map.Entry)entries.next();
               if (entry.getValue() == component) {
                  entries.remove();
                  return;
               }
            }
         }

         throw new IllegalStateException("Parent was not null, but this component not related");
      }
   }

   private static void validateId(String id) {
      if (id != null) {
         int idLength = id.length();
         if (idLength < 1) {
            throw new IllegalArgumentException("Empty id attribute is not allowed");
         } else {
            for(int i = 0; i < idLength; ++i) {
               char c = id.charAt(i);
               if (i == 0) {
                  if (!Character.isLetter(c) && c != '_') {
                     throw new IllegalArgumentException(id);
                  }
               } else if (!Character.isLetter(c) && !Character.isDigit(c) && c != '-' && c != '_') {
                  throw new IllegalArgumentException(id);
               }
            }

         }
      }
   }

   private UIComponent findBaseComponent(String expression, char sepChar) {
      UIComponent base = this;
      if (expression.charAt(0) != sepChar) {
         if (!(this instanceof NamingContainer)) {
            while(((UIComponent)base).getParent() != null && !(base instanceof NamingContainer)) {
               base = ((UIComponent)base).getParent();
            }
         }
      } else {
         while(true) {
            if (((UIComponent)base).getParent() == null) {
               expression = expression.substring(1);
               break;
            }

            base = ((UIComponent)base).getParent();
         }
      }

      return (UIComponent)base;
   }

   private UIComponent evaluateSearchExpression(UIComponent base, String expression, String SEPARATOR_STRING) {
      UIComponent result = null;
      String[] segments = expression.split(SEPARATOR_STRING);
      int i = 0;

      for(int length = segments.length - 1; i < segments.length; --length) {
         result = findComponent(base, segments[i], i == 0);
         if (i == 0 && result == null && segments[i].equals(base.getId())) {
            result = base;
         }

         if (result != null && !(result instanceof NamingContainer) && length > 0) {
            throw new IllegalArgumentException(segments[i]);
         }

         if (result == null) {
            break;
         }

         base = result;
         ++i;
      }

      return result;
   }

   private static UIComponent findComponent(UIComponent base, String id, boolean checkId) {
      if (checkId && id.equals(base.getId())) {
         return base;
      } else {
         UIComponent component = null;
         Iterator i = base.getFacetsAndChildren();

         while(i.hasNext()) {
            UIComponent kid = (UIComponent)i.next();
            if (!(kid instanceof NamingContainer)) {
               if (checkId && id.equals(kid.getId())) {
                  component = kid;
                  break;
               }

               component = findComponent(kid, id, true);
               if (component != null) {
                  break;
               }
            } else if (id.equals(kid.getId())) {
               component = kid;
               break;
            }
         }

         return component;
      }
   }

   private List collectChildState(FacesContext context, List stateList) {
      if (this.getChildCount() > 0) {
         Iterator kids = this.getChildren().iterator();

         while(kids.hasNext()) {
            UIComponent kid = (UIComponent)kids.next();
            if (!kid.isTransient()) {
               stateList.add(kid.processSaveState(context));
            }
         }
      }

      return stateList;
   }

   private List collectFacetsState(FacesContext context, List stateList) {
      if (this.getFacetCount() > 0) {
         Iterator myFacets = this.getFacets().entrySet().iterator();

         while(myFacets.hasNext()) {
            Map.Entry entry = (Map.Entry)myFacets.next();
            UIComponent facet = (UIComponent)entry.getValue();
            if (!facet.isTransient()) {
               Object facetState = facet.processSaveState(context);
               Object[] facetSaveState = new Object[]{entry.getKey(), facetState};
               stateList.add(facetSaveState);
            }
         }
      }

      return stateList;
   }

   private int restoreChildState(FacesContext context, Object[] childState) {
      int i = 0;
      if (this.getChildCount() > 0) {
         Iterator var4 = this.getChildren().iterator();

         while(var4.hasNext()) {
            UIComponent kid = (UIComponent)var4.next();
            if (!kid.isTransient()) {
               Object currentState = childState[i++];
               if (currentState != null) {
                  kid.processRestoreState(context, currentState);
               }
            }
         }
      }

      return i;
   }

   private void restoreFacetsState(FacesContext context, Object[] childState, int i) {
      if (this.getFacetCount() > 0) {
         int j = 0;

         for(int facetsSize = this.getFacets().size(); j < facetsSize; ++j) {
            Object[] facetSaveState = (Object[])((Object[])childState[i++]);
            if (facetSaveState != null) {
               String facetName = (String)facetSaveState[0];
               Object facetState = facetSaveState[1];
               UIComponent facet = (UIComponent)this.getFacets().get(facetName);
               facet.processRestoreState(context, facetState);
            }
         }
      }

   }

   /** @deprecated */
   public ValueBinding getValueBinding(String name) {
      if (name == null) {
         throw new NullPointerException();
      } else {
         ValueBinding result = null;
         ValueExpression ve;
         if (null != (ve = this.getValueExpression(name))) {
            if (ve.getClass().equals(ValueExpressionValueBindingAdapter.class)) {
               result = ((ValueExpressionValueBindingAdapter)ve).getWrapped();
            } else {
               result = new ValueBindingValueExpressionAdapter(ve);
            }
         }

         return (ValueBinding)result;
      }
   }

   /** @deprecated */
   public void setValueBinding(String name, ValueBinding binding) {
      if (name == null) {
         throw new NullPointerException();
      } else {
         if (binding != null) {
            ValueExpressionValueBindingAdapter adapter = new ValueExpressionValueBindingAdapter(binding);
            this.setValueExpression(name, adapter);
         } else {
            this.setValueExpression(name, (ValueExpression)null);
         }

      }
   }

   private static class PassThroughAttributesMap extends ConcurrentHashMap implements Serializable {
      private static final long serialVersionUID = 4230540513272170861L;

      private PassThroughAttributesMap() {
      }

      public Object put(String key, Object value) {
         if (null != key && null != value) {
            this.validateKey(key);
            return super.put(key, value);
         } else {
            throw new NullPointerException();
         }
      }

      public Object putIfAbsent(String key, Object value) {
         if (null != key && null != value) {
            this.validateKey(key);
            return super.putIfAbsent(key, value);
         } else {
            throw new NullPointerException();
         }
      }

      private void validateKey(Object key) {
         if (!(key instanceof String) || key instanceof ValueExpression || !(key instanceof Serializable)) {
            throw new IllegalArgumentException();
         }
      }

      // $FF: synthetic method
      PassThroughAttributesMap(Object x0) {
         this();
      }
   }

   private static class BehaviorsMap extends AbstractMap {
      private Map unmodifiableMap;
      private Map modifiableMap;

      private BehaviorsMap(Map modifiableMap) {
         this.modifiableMap = modifiableMap;
         this.unmodifiableMap = Collections.unmodifiableMap(modifiableMap);
      }

      public Set entrySet() {
         return this.unmodifiableMap.entrySet();
      }

      private Map getModifiableMap() {
         return this.modifiableMap;
      }

      // $FF: synthetic method
      BehaviorsMap(Map x0, Object x1) {
         this(x0);
      }
   }

   private static class FacetsMapValuesIterator implements Iterator {
      private FacetsMap map = null;
      private Iterator iterator = null;
      private Object last = null;

      public FacetsMapValuesIterator(FacetsMap map) {
         this.map = map;
         this.iterator = map.keySetIterator();
      }

      public boolean hasNext() {
         return this.iterator.hasNext();
      }

      public UIComponent next() {
         this.last = this.iterator.next();
         return (UIComponent)this.map.get(this.last);
      }

      public void remove() {
         if (this.last == null) {
            throw new IllegalStateException();
         } else {
            this.map.remove(this.last);
            this.last = null;
         }
      }
   }

   private static class FacetsMapValues extends AbstractCollection {
      private FacetsMap map;

      public FacetsMapValues(FacetsMap map) {
         this.map = map;
      }

      public boolean add(UIComponent o) {
         throw new UnsupportedOperationException();
      }

      public boolean addAll(Collection c) {
         throw new UnsupportedOperationException();
      }

      public void clear() {
         this.map.clear();
      }

      public boolean isEmpty() {
         return this.map.isEmpty();
      }

      public Iterator iterator() {
         return new FacetsMapValuesIterator(this.map);
      }

      public int size() {
         return this.map.size();
      }
   }

   private static class FacetsMapKeySetIterator implements Iterator {
      private FacetsMap map = null;
      private Iterator iterator = null;
      private String last = null;

      public FacetsMapKeySetIterator(FacetsMap map) {
         this.map = map;
         this.iterator = map.keySetIterator();
      }

      public boolean hasNext() {
         return this.iterator.hasNext();
      }

      public String next() {
         this.last = (String)this.iterator.next();
         return this.last;
      }

      public void remove() {
         if (this.last == null) {
            throw new IllegalStateException();
         } else {
            this.map.remove(this.last);
            this.last = null;
         }
      }
   }

   private static class FacetsMapKeySet extends AbstractSet {
      private FacetsMap map = null;

      public FacetsMapKeySet(FacetsMap map) {
         this.map = map;
      }

      public boolean add(String o) {
         throw new UnsupportedOperationException();
      }

      public boolean addAll(Collection c) {
         throw new UnsupportedOperationException();
      }

      public void clear() {
         this.map.clear();
      }

      public boolean contains(Object o) {
         return this.map.containsKey(o);
      }

      public boolean containsAll(Collection c) {
         Iterator var2 = c.iterator();

         Object item;
         do {
            if (!var2.hasNext()) {
               return true;
            }

            item = var2.next();
         } while(this.map.containsKey(item));

         return false;
      }

      public boolean isEmpty() {
         return this.map.isEmpty();
      }

      public Iterator iterator() {
         return new FacetsMapKeySetIterator(this.map);
      }

      public boolean remove(Object o) {
         if (this.map.containsKey(o)) {
            this.map.remove(o);
            return true;
         } else {
            return false;
         }
      }

      public boolean removeAll(Collection c) {
         boolean result = false;
         Iterator var3 = c.iterator();

         while(var3.hasNext()) {
            Object item = var3.next();
            if (this.map.containsKey(item)) {
               this.map.remove(item);
               result = true;
            }
         }

         return result;
      }

      public boolean retainAll(Collection c) {
         boolean result = false;
         Iterator v = this.iterator();

         while(v.hasNext()) {
            if (!c.contains(v.next())) {
               v.remove();
               result = true;
            }
         }

         return result;
      }

      public int size() {
         return this.map.size();
      }
   }

   private static class FacetsMapEntrySetIterator implements Iterator {
      private FacetsMap map = null;
      private Iterator iterator = null;
      private Map.Entry last = null;

      public FacetsMapEntrySetIterator(FacetsMap map) {
         this.map = map;
         this.iterator = map.keySetIterator();
      }

      public boolean hasNext() {
         return this.iterator.hasNext();
      }

      public Map.Entry next() {
         this.last = new FacetsMapEntrySetEntry(this.map, (String)this.iterator.next());
         return this.last;
      }

      public void remove() {
         if (this.last == null) {
            throw new IllegalStateException();
         } else {
            this.map.remove(this.last.getKey());
            this.last = null;
         }
      }
   }

   private static class FacetsMapEntrySetEntry implements Map.Entry {
      private FacetsMap map;
      private String key;

      public FacetsMapEntrySetEntry(FacetsMap map, String key) {
         this.map = map;
         this.key = key;
      }

      public boolean equals(Object o) {
         if (o == null) {
            return false;
         } else if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry e = (Map.Entry)o;
            if (this.key == null) {
               if (e.getKey() != null) {
                  return false;
               }
            } else if (!this.key.equals(e.getKey())) {
               return false;
            }

            UIComponent v = (UIComponent)this.map.get(this.key);
            if (v == null) {
               if (e.getValue() != null) {
                  return false;
               }
            } else if (!v.equals(e.getValue())) {
               return false;
            }

            return true;
         }
      }

      public String getKey() {
         return this.key;
      }

      public UIComponent getValue() {
         return (UIComponent)this.map.get(this.key);
      }

      public int hashCode() {
         Object value = this.map.get(this.key);
         return (this.key == null ? 0 : this.key.hashCode()) ^ (value == null ? 0 : value.hashCode());
      }

      public UIComponent setValue(UIComponent value) {
         UIComponent previous = (UIComponent)this.map.get(this.key);
         this.map.put(this.key, value);
         return previous;
      }
   }

   private static class FacetsMapEntrySet extends AbstractSet {
      private FacetsMap map = null;

      public FacetsMapEntrySet(FacetsMap map) {
         this.map = map;
      }

      public boolean add(Map.Entry o) {
         throw new UnsupportedOperationException();
      }

      public boolean addAll(Collection c) {
         throw new UnsupportedOperationException();
      }

      public void clear() {
         this.map.clear();
      }

      public boolean contains(Object o) {
         if (o == null) {
            throw new NullPointerException();
         } else if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry e = (Map.Entry)o;
            Object k = e.getKey();
            Object v = e.getValue();
            if (!this.map.containsKey(k)) {
               return false;
            } else if (v == null) {
               return this.map.get(k) == null;
            } else {
               return v.equals(this.map.get(k));
            }
         }
      }

      public boolean isEmpty() {
         return this.map.isEmpty();
      }

      public Iterator iterator() {
         return new FacetsMapEntrySetIterator(this.map);
      }

      public boolean remove(Object o) {
         if (o == null) {
            throw new NullPointerException();
         } else if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Object k = ((Map.Entry)o).getKey();
            if (this.map.containsKey(k)) {
               this.map.remove(k);
               return true;
            } else {
               return false;
            }
         }
      }

      public boolean removeAll(Collection c) {
         boolean result = false;
         Iterator var3 = c.iterator();

         while(var3.hasNext()) {
            Object element = var3.next();
            if (this.remove(element)) {
               result = true;
            }
         }

         return result;
      }

      public boolean retainAll(Collection c) {
         boolean result = false;
         Iterator v = this.iterator();

         while(v.hasNext()) {
            if (!c.contains(v.next())) {
               v.remove();
               result = true;
            }
         }

         return result;
      }

      public int size() {
         return this.map.size();
      }
   }

   private static class FacetsMap extends HashMap {
      private UIComponent component;

      public FacetsMap(UIComponent component) {
         super(3, 1.0F);
         this.component = component;
      }

      public void clear() {
         Iterator keys = this.keySet().iterator();

         while(keys.hasNext()) {
            keys.next();
            keys.remove();
         }

         super.clear();
      }

      public Set entrySet() {
         return new FacetsMapEntrySet(this);
      }

      public Set keySet() {
         return new FacetsMapKeySet(this);
      }

      public UIComponent put(String key, UIComponent value) {
         if (key != null && value != null) {
            if (key instanceof String && value instanceof UIComponent) {
               UIComponent previous = (UIComponent)super.get(key);
               if (previous != null) {
                  previous.setParent((UIComponent)null);
               }

               UIComponentBase.eraseParent(value);
               UIComponent result = (UIComponent)super.put(key, value);
               value.setParent(this.component);
               return result;
            } else {
               throw new ClassCastException();
            }
         } else {
            throw new NullPointerException();
         }
      }

      public void putAll(Map map) {
         if (map == null) {
            throw new NullPointerException();
         } else {
            Iterator var2 = map.entrySet().iterator();

            while(var2.hasNext()) {
               Map.Entry entry = (Map.Entry)var2.next();
               this.put((String)entry.getKey(), (UIComponent)entry.getValue());
            }

         }
      }

      public UIComponent remove(Object key) {
         UIComponent previous = (UIComponent)this.get(key);
         if (previous != null) {
            previous.setParent((UIComponent)null);
         }

         super.remove(key);
         return previous;
      }

      public Collection values() {
         return new FacetsMapValues(this);
      }

      Iterator keySetIterator() {
         return (new ArrayList(super.keySet())).iterator();
      }
   }

   private static final class FacetsAndChildrenIterator implements Iterator {
      private Iterator iterator;
      private boolean childMode;
      private UIComponent c;

      public FacetsAndChildrenIterator(UIComponent c) {
         this.c = c;
         this.childMode = false;
      }

      private void update() {
         if (this.iterator == null) {
            if (this.c.getFacetCount() != 0) {
               this.iterator = this.c.getFacets().values().iterator();
               this.childMode = false;
            } else if (this.c.getChildCount() != 0) {
               this.iterator = this.c.getChildren().iterator();
               this.childMode = true;
            } else {
               this.iterator = UIComponentBase.EMPTY_ITERATOR;
               this.childMode = true;
            }
         } else if (!this.childMode && !this.iterator.hasNext() && this.c.getChildCount() != 0) {
            this.iterator = this.c.getChildren().iterator();
            this.childMode = true;
         }

      }

      public boolean hasNext() {
         this.update();
         return this.iterator.hasNext();
      }

      public UIComponent next() {
         this.update();
         return (UIComponent)this.iterator.next();
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }

   private static class ChildrenListIterator implements ListIterator {
      private ChildrenList list;
      private int index;
      private int last = -1;

      public ChildrenListIterator(ChildrenList list) {
         this.list = list;
         this.index = 0;
      }

      public ChildrenListIterator(ChildrenList list, int index) {
         this.list = list;
         if (index >= 0 && index <= list.size()) {
            this.index = index;
         } else {
            throw new IndexOutOfBoundsException(String.valueOf(index));
         }
      }

      public boolean hasNext() {
         return this.index < this.list.size();
      }

      public UIComponent next() {
         try {
            UIComponent o = (UIComponent)this.list.get(this.index);
            this.last = this.index++;
            return o;
         } catch (IndexOutOfBoundsException var2) {
            throw new NoSuchElementException(String.valueOf(this.index));
         }
      }

      public void remove() {
         if (this.last == -1) {
            throw new IllegalStateException();
         } else {
            this.list.remove(this.last);
            if (this.last < this.index) {
               --this.index;
            }

            this.last = -1;
         }
      }

      public void add(UIComponent o) {
         this.last = -1;
         this.list.add(this.index++, o);
      }

      public boolean hasPrevious() {
         return this.index > 1;
      }

      public int nextIndex() {
         return this.index;
      }

      public UIComponent previous() {
         try {
            int current = this.index - 1;
            UIComponent o = (UIComponent)this.list.get(current);
            this.last = current;
            this.index = current;
            return o;
         } catch (IndexOutOfBoundsException var3) {
            throw new NoSuchElementException();
         }
      }

      public int previousIndex() {
         return this.index - 1;
      }

      public void set(UIComponent o) {
         if (this.last == -1) {
            throw new IllegalStateException();
         } else {
            this.list.set(this.last, o);
         }
      }
   }

   private static class ChildrenList extends ArrayList {
      private UIComponent component;

      public ChildrenList(UIComponent component) {
         super(6);
         this.component = component;
      }

      public void add(int index, UIComponent element) {
         if (element == null) {
            throw new NullPointerException();
         } else if (index >= 0 && index <= this.size()) {
            UIComponentBase.eraseParent(element);
            super.add(index, element);
            element.setParent(this.component);
         } else {
            throw new IndexOutOfBoundsException();
         }
      }

      public boolean add(UIComponent element) {
         if (element == null) {
            throw new NullPointerException();
         } else {
            UIComponentBase.eraseParent(element);
            boolean result = super.add(element);
            element.setParent(this.component);
            return result;
         }
      }

      public boolean addAll(Collection collection) {
         Iterator elements = (new ArrayList(collection)).iterator();

         boolean changed;
         for(changed = false; elements.hasNext(); changed = true) {
            UIComponent element = (UIComponent)elements.next();
            if (element == null) {
               throw new NullPointerException();
            }

            this.add(element);
         }

         return changed;
      }

      public boolean addAll(int index, Collection collection) {
         Iterator elements = (new ArrayList(collection)).iterator();

         boolean changed;
         for(changed = false; elements.hasNext(); changed = true) {
            UIComponent element = (UIComponent)elements.next();
            if (element == null) {
               throw new NullPointerException();
            }

            this.add(index++, element);
         }

         return changed;
      }

      public void clear() {
         int n = this.size();
         if (n >= 1) {
            for(int i = 0; i < n; ++i) {
               UIComponent child = (UIComponent)this.get(i);
               child.setParent((UIComponent)null);
            }

            super.clear();
         }
      }

      public Iterator iterator() {
         return new ChildrenListIterator(this);
      }

      public ListIterator listIterator() {
         return new ChildrenListIterator(this);
      }

      public ListIterator listIterator(int index) {
         return new ChildrenListIterator(this, index);
      }

      public UIComponent remove(int index) {
         UIComponent child = (UIComponent)this.get(index);
         child.setParent((UIComponent)null);
         super.remove(index);
         return child;
      }

      public boolean remove(Object elementObj) {
         UIComponent element = (UIComponent)elementObj;
         if (element == null) {
            throw new NullPointerException();
         } else {
            if (super.indexOf(element) != -1) {
               element.setParent((UIComponent)null);
            }

            return super.remove(element);
         }
      }

      public boolean removeAll(Collection collection) {
         boolean result = false;
         Iterator var3 = collection.iterator();

         while(var3.hasNext()) {
            Object elements = var3.next();
            if (this.remove(elements)) {
               result = true;
            }
         }

         return result;
      }

      public boolean retainAll(Collection collection) {
         boolean modified = false;
         Iterator items = this.iterator();

         while(items.hasNext()) {
            if (!collection.contains(items.next())) {
               items.remove();
               modified = true;
            }
         }

         return modified;
      }

      public UIComponent set(int index, UIComponent element) {
         if (element == null) {
            throw new NullPointerException();
         } else if (index >= 0 && index < this.size()) {
            UIComponentBase.eraseParent(element);
            UIComponent previous = (UIComponent)this.get(index);
            super.set(index, element);
            previous.setParent((UIComponent)null);
            element.setParent(this.component);
            return previous;
         } else {
            throw new IndexOutOfBoundsException();
         }
      }
   }

   private static class AttributesMap implements Map, Serializable {
      private static final String ATTRIBUTES_THAT_ARE_SET_KEY = UIComponentBase.class.getName() + ".attributesThatAreSet";
      private transient Map pdMap;
      private transient ConcurrentMap readMap;
      private transient UIComponent component;
      private static final long serialVersionUID = -6773035086539772945L;

      private AttributesMap(UIComponent component) {
         this.component = component;
         this.pdMap = ((UIComponentBase)component).getDescriptorMap();
      }

      public boolean containsKey(Object keyObj) {
         if (ATTRIBUTES_THAT_ARE_SET_KEY.equals(keyObj)) {
            return true;
         } else {
            String key = (String)keyObj;
            PropertyDescriptor pd = this.getPropertyDescriptor(key);
            if (pd == null) {
               Map attributes = (Map)this.component.getStateHelper().get(UIComponent.PropertyKeys.attributes);
               return attributes != null ? attributes.containsKey(key) : false;
            } else {
               return false;
            }
         }
      }

      public Object get(Object keyObj) {
         String key = (String)keyObj;
         Object result = null;
         if (key == null) {
            throw new NullPointerException();
         } else {
            if (ATTRIBUTES_THAT_ARE_SET_KEY.equals(key)) {
               result = this.component.getStateHelper().get(UIComponent.PropertyKeysPrivate.attributesThatAreSet);
            }

            Map attributes = (Map)this.component.getStateHelper().get(UIComponent.PropertyKeys.attributes);
            if (null == result) {
               PropertyDescriptor pd = this.getPropertyDescriptor(key);
               if (pd != null) {
                  try {
                     if (null == this.readMap) {
                        this.readMap = new ConcurrentHashMap();
                     }

                     Method readMethod = (Method)this.readMap.get(key);
                     if (null == readMethod) {
                        readMethod = pd.getReadMethod();
                        Method putResult = (Method)this.readMap.putIfAbsent(key, readMethod);
                        if (null != putResult) {
                           readMethod = putResult;
                        }
                     }

                     if (readMethod == null) {
                        throw new IllegalArgumentException(key);
                     }

                     result = readMethod.invoke(this.component, UIComponentBase.EMPTY_OBJECT_ARRAY);
                  } catch (IllegalAccessException var9) {
                     throw new FacesException(var9);
                  } catch (InvocationTargetException var10) {
                     throw new FacesException(var10.getTargetException());
                  }
               } else if (attributes != null && attributes.containsKey(key)) {
                  result = attributes.get(key);
               }
            }

            if (null == result) {
               ValueExpression ve = this.component.getValueExpression(key);
               if (ve != null) {
                  try {
                     result = ve.getValue(this.component.getFacesContext().getELContext());
                  } catch (ELException var8) {
                     throw new FacesException(var8);
                  }
               }
            }

            return result;
         }
      }

      public Object put(String keyValue, Object value) {
         if (keyValue == null) {
            throw new NullPointerException();
         } else if (ATTRIBUTES_THAT_ARE_SET_KEY.equals(keyValue)) {
            if (this.component.attributesThatAreSet == null && value instanceof List) {
               this.component.getStateHelper().put(UIComponent.PropertyKeysPrivate.attributesThatAreSet, value);
            }

            return null;
         } else {
            PropertyDescriptor pd = this.getPropertyDescriptor(keyValue);
            if (pd != null) {
               try {
                  Object result = null;
                  Method readMethod = pd.getReadMethod();
                  if (readMethod != null) {
                     result = readMethod.invoke(this.component, UIComponentBase.EMPTY_OBJECT_ARRAY);
                  }

                  Method writeMethod = pd.getWriteMethod();
                  if (writeMethod != null) {
                     writeMethod.invoke(this.component, value);
                     return result;
                  } else {
                     throw new IllegalArgumentException("Setter not found for property " + keyValue);
                  }
               } catch (IllegalAccessException var7) {
                  throw new FacesException(var7);
               } catch (InvocationTargetException var8) {
                  throw new FacesException(var8.getTargetException());
               }
            } else if (value == null) {
               throw new NullPointerException();
            } else {
               List sProperties = (List)this.component.getStateHelper().get(UIComponent.PropertyKeysPrivate.attributesThatAreSet);
               if (sProperties == null) {
                  this.component.getStateHelper().add(UIComponent.PropertyKeysPrivate.attributesThatAreSet, keyValue);
               } else if (!sProperties.contains(keyValue)) {
                  this.component.getStateHelper().add(UIComponent.PropertyKeysPrivate.attributesThatAreSet, keyValue);
               }

               return this.putAttribute(keyValue, value);
            }
         }
      }

      public void putAll(Map map) {
         if (map == null) {
            throw new NullPointerException();
         } else {
            Iterator var2 = map.entrySet().iterator();

            while(var2.hasNext()) {
               Map.Entry entry = (Map.Entry)var2.next();
               this.put((String)entry.getKey(), entry.getValue());
            }

         }
      }

      public Object remove(Object keyObj) {
         String key = (String)keyObj;
         if (key == null) {
            throw new NullPointerException();
         } else if (ATTRIBUTES_THAT_ARE_SET_KEY.equals(key)) {
            return null;
         } else {
            PropertyDescriptor pd = this.getPropertyDescriptor(key);
            if (pd != null) {
               throw new IllegalArgumentException(key);
            } else {
               Map attributes = this.getAttributes();
               if (attributes != null) {
                  this.component.getStateHelper().remove(UIComponent.PropertyKeysPrivate.attributesThatAreSet, key);
                  return this.component.getStateHelper().remove(UIComponent.PropertyKeys.attributes, key);
               } else {
                  return null;
               }
            }
         }
      }

      public int size() {
         Map attributes = this.getAttributes();
         return attributes != null ? attributes.size() : 0;
      }

      public boolean isEmpty() {
         Map attributes = this.getAttributes();
         return attributes == null || attributes.isEmpty();
      }

      public boolean containsValue(Object value) {
         Map attributes = this.getAttributes();
         return attributes != null && attributes.containsValue(value);
      }

      public void clear() {
         this.component.getStateHelper().remove(UIComponent.PropertyKeys.attributes);
         this.component.getStateHelper().remove(UIComponent.PropertyKeysPrivate.attributesThatAreSet);
      }

      public Set keySet() {
         Map attributes = this.getAttributes();
         return attributes != null ? Collections.unmodifiableSet(attributes.keySet()) : Collections.emptySet();
      }

      public Collection values() {
         Map attributes = this.getAttributes();
         return (Collection)(attributes != null ? Collections.unmodifiableCollection(attributes.values()) : Collections.emptyList());
      }

      public Set entrySet() {
         Map attributes = this.getAttributes();
         return attributes != null ? Collections.unmodifiableSet(attributes.entrySet()) : Collections.emptySet();
      }

      public boolean equals(Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof Map)) {
            return false;
         } else {
            Map t = (Map)o;
            if (t.size() != this.size()) {
               return false;
            } else {
               try {
                  Iterator var3 = this.entrySet().iterator();

                  Object key;
                  label40:
                  do {
                     Object value;
                     do {
                        if (!var3.hasNext()) {
                           return true;
                        }

                        Object e = var3.next();
                        Map.Entry entry = (Map.Entry)e;
                        key = entry.getKey();
                        value = entry.getValue();
                        if (value == null) {
                           continue label40;
                        }
                     } while(value.equals(t.get(key)));

                     return false;
                  } while(t.get(key) == null && t.containsKey(key));

                  return false;
               } catch (NullPointerException | ClassCastException var8) {
                  return false;
               }
            }
         }
      }

      public int hashCode() {
         int h = 0;

         Object o;
         for(Iterator var2 = this.entrySet().iterator(); var2.hasNext(); h += o.hashCode()) {
            o = var2.next();
         }

         return h;
      }

      private Map getAttributes() {
         return (Map)this.component.getStateHelper().get(UIComponent.PropertyKeys.attributes);
      }

      private Object putAttribute(String key, Object value) {
         return this.component.getStateHelper().put(UIComponent.PropertyKeys.attributes, key, value);
      }

      PropertyDescriptor getPropertyDescriptor(String name) {
         return this.pdMap != null ? (PropertyDescriptor)this.pdMap.get(name) : null;
      }

      private void writeObject(ObjectOutputStream out) throws IOException {
         out.writeObject(this.component.getClass());
         out.writeObject(this.component.saveState(FacesContext.getCurrentInstance()));
      }

      private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
         Class clazz = (Class)in.readObject();

         try {
            this.component = (UIComponent)clazz.newInstance();
         } catch (IllegalAccessException | InstantiationException var4) {
            throw new RuntimeException(var4);
         }

         this.component.restoreState(FacesContext.getCurrentInstance(), in.readObject());
      }

      // $FF: synthetic method
      AttributesMap(UIComponent x0, Object x1) {
         this(x0);
      }
   }
}
