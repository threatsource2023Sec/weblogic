package javax.faces.component;

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
import java.util.AbstractCollection;
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
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.render.Renderer;

public abstract class UIComponentBase extends UIComponent {
   private static Logger log = Logger.getLogger("javax.faces.component", "javax.faces.LogStrings");
   private static Map descriptors = new WeakHashMap();
   private Map pdMap = null;
   private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
   private static final String IS_SAVE_BINDINGS_STATE_PARAM_NAME = "com.sun.faces.IS_SAVE_BINDINGS_STATE";
   private AttributesMap attributes = null;
   private String clientId = null;
   private String id = null;
   private UIComponent parent = null;
   private boolean rendered = true;
   private boolean renderedSet = false;
   private String rendererType = null;
   private List children = null;
   private static final String SEPARATOR_STRING = String.valueOf(':');
   private Map facets = null;
   private List listeners;
   private static final int MY_STATE = 0;
   private static final int CHILD_STATE = 1;
   private Object[] values;
   private boolean transientFlag = false;
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

   private void populateDescriptorsMapIfNecessary() {
      Class clazz = this.getClass();
      this.pdMap = (Map)descriptors.get(clazz);
      if (null == this.pdMap) {
         PropertyDescriptor[] pd = this.getPropertyDescriptors();
         if (pd != null) {
            this.pdMap = new HashMap(pd.length, 1.0F);
            PropertyDescriptor[] var3 = pd;
            int var4 = pd.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               PropertyDescriptor aPd = var3[var5];
               this.pdMap.put(aPd.getName(), aPd);
            }

            if (log.isLoggable(Level.FINE)) {
               log.log(Level.FINE, "fine.component.populating_descriptor_map", new Object[]{clazz, Thread.currentThread().getName()});
            }

            Map reCheckMap = (Map)descriptors.get(clazz);
            if (null != reCheckMap) {
               return;
            }

            descriptors.put(clazz, this.pdMap);
         }

      }
   }

   private PropertyDescriptor[] getPropertyDescriptors() {
      try {
         PropertyDescriptor[] pd = Introspector.getBeanInfo(this.getClass()).getPropertyDescriptors();
         return pd;
      } catch (IntrospectionException var3) {
         throw new FacesException(var3);
      }
   }

   public Map getAttributes() {
      if (this.attributes == null) {
         this.attributes = new AttributesMap(this);
      }

      return this.attributes;
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

   public String getClientId(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         if (this.clientId == null) {
            UIComponent parent = this.getNamingContainer();
            String parentId = null;
            if (parent != null) {
               parentId = parent.getContainerClientId(context);
            }

            this.clientId = this.getId();
            if (this.clientId == null) {
               this.setId(context.getViewRoot().createUniqueId());
               this.clientId = this.getId();
            }

            if (parentId != null) {
               StringBuilder idBuilder = new StringBuilder(parentId.length() + 1 + this.clientId.length());
               this.clientId = idBuilder.append(parentId).append(':').append(this.clientId).toString();
            }

            Renderer renderer = this.getRenderer(context);
            if (renderer != null) {
               this.clientId = renderer.convertClientId(context, this.clientId);
            }
         }

         return this.clientId;
      }
   }

   private UIComponent getNamingContainer() {
      for(UIComponent namingContainer = this.getParent(); namingContainer != null; namingContainer = namingContainer.getParent()) {
         if (namingContainer instanceof NamingContainer) {
            return namingContainer;
         }
      }

      return null;
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
      this.parent = parent;
   }

   public boolean isRendered() {
      if (this.renderedSet) {
         return this.rendered;
      } else {
         ValueExpression ve = this.getValueExpression("rendered");
         if (ve != null) {
            try {
               return !Boolean.FALSE.equals(ve.getValue(this.getFacesContext().getELContext()));
            } catch (ELException var3) {
               throw new FacesException(var3);
            }
         } else {
            return this.rendered;
         }
      }
   }

   public void setRendered(boolean rendered) {
      this.rendered = rendered;
      this.renderedSet = true;
   }

   public String getRendererType() {
      if (this.rendererType != null) {
         return this.rendererType;
      } else {
         ValueExpression ve = this.getValueExpression("rendererType");
         if (ve != null) {
            try {
               return (String)ve.getValue(this.getFacesContext().getELContext());
            } catch (ELException var3) {
               throw new FacesException(var3);
            }
         } else {
            return null;
         }
      }
   }

   public void setRendererType(String rendererType) {
      this.rendererType = rendererType;
   }

   public boolean getRendersChildren() {
      boolean result = false;
      Renderer renderer;
      if (this.getRendererType() != null && null != (renderer = this.getRenderer(this.getFacesContext()))) {
         result = renderer.getRendersChildren();
      }

      return result;
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
         int n = id.length();
         if (n < 1) {
            throw new IllegalArgumentException("The id attribute may not be empty");
         } else {
            for(int i = 0; i < n; ++i) {
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

   public UIComponent findComponent(String expr) {
      if (expr == null) {
         throw new NullPointerException();
      } else if (expr.length() == 0) {
         throw new IllegalArgumentException("\"\"");
      } else {
         UIComponent base = this;
         if (expr.charAt(0) != ':') {
            if (!(this instanceof NamingContainer)) {
               while(((UIComponent)base).getParent() != null && !(base instanceof NamingContainer)) {
                  base = ((UIComponent)base).getParent();
               }
            }
         } else {
            while(((UIComponent)base).getParent() != null) {
               base = ((UIComponent)base).getParent();
            }

            expr = expr.substring(1);
         }

         UIComponent result = null;
         String[] segments = expr.split(SEPARATOR_STRING);
         int i = 0;

         for(int length = segments.length - 1; i < segments.length; --length) {
            result = findComponent((UIComponent)base, segments[i], i == 0);
            if (i == 0 && result == null && segments[i].equals(((UIComponent)base).getId())) {
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

         return (UIComponent)result;
      }
   }

   private static UIComponent findComponent(UIComponent base, String id, boolean checkId) {
      if (checkId && id.equals(base.getId())) {
         return base;
      } else {
         UIComponent result = null;
         Iterator i = base.getFacetsAndChildren();

         while(i.hasNext()) {
            UIComponent kid = (UIComponent)i.next();
            if (!(kid instanceof NamingContainer)) {
               if (checkId && id.equals(kid.getId())) {
                  result = kid;
                  break;
               }

               result = findComponent(kid, id, true);
               if (result != null) {
                  break;
               }
            } else if (id.equals(kid.getId())) {
               result = kid;
               break;
            }
         }

         return result;
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
      Object result;
      if (0 == childCount && 0 == facetCount) {
         result = EMPTY_ITERATOR;
      } else if (0 == childCount) {
         Collection unmodifiable = Collections.unmodifiableCollection(this.getFacets().values());
         result = unmodifiable.iterator();
      } else if (0 == facetCount) {
         List unmodifiable = Collections.unmodifiableList(this.getChildren());
         result = unmodifiable.iterator();
      } else {
         result = new FacetsAndChildrenIterator(this);
      }

      return (Iterator)result;
   }

   public void broadcast(FacesEvent event) throws AbortProcessingException {
      if (event == null) {
         throw new NullPointerException();
      } else if (this.listeners != null) {
         Iterator iter = this.listeners.iterator();

         while(iter.hasNext()) {
            FacesListener listener = (FacesListener)iter.next();
            if (event.isAppropriateListener(listener)) {
               event.processListener(listener);
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
            } else {
               log.fine("Can't get Renderer for type " + rendererType);
            }
         }

      }
   }

   public void encodeBegin(FacesContext context) throws IOException {
      if (context == null) {
         throw new NullPointerException();
      } else if (this.isRendered()) {
         String rendererType = this.getRendererType();
         if (rendererType != null) {
            Renderer renderer = this.getRenderer(context);
            if (renderer != null) {
               renderer.encodeBegin(context, this);
            } else {
               log.fine("Can't get Renderer for type " + rendererType);
            }
         }

      }
   }

   public void encodeChildren(FacesContext context) throws IOException {
      if (context == null) {
         throw new NullPointerException();
      } else if (this.isRendered()) {
         String rendererType = this.getRendererType();
         if (rendererType != null) {
            Renderer renderer = this.getRenderer(context);
            if (renderer != null) {
               renderer.encodeChildren(context, this);
            }
         }

      }
   }

   public void encodeEnd(FacesContext context) throws IOException {
      if (context == null) {
         throw new NullPointerException();
      } else if (this.isRendered()) {
         String rendererType = this.getRendererType();
         if (rendererType != null) {
            Renderer renderer = this.getRenderer(context);
            if (renderer != null) {
               renderer.encodeEnd(context, this);
            }
         }

      }
   }

   protected void addFacesListener(FacesListener listener) {
      if (listener == null) {
         throw new NullPointerException();
      } else {
         if (this.listeners == null) {
            this.listeners = new ArrayList();
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
         List results = new ArrayList();
         Iterator items = this.listeners.iterator();

         while(items.hasNext()) {
            FacesListener item = (FacesListener)items.next();
            if (clazz.isAssignableFrom(item.getClass())) {
               results.add(item);
            }
         }

         return (FacesListener[])results.toArray((FacesListener[])((FacesListener[])Array.newInstance(clazz, results.size())));
      }
   }

   protected void removeFacesListener(FacesListener listener) {
      if (listener == null) {
         throw new NullPointerException();
      } else if (this.listeners != null) {
         this.listeners.remove(listener);
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

   public void processDecodes(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else if (this.isRendered()) {
         Iterator kids = this.getFacetsAndChildren();

         while(kids.hasNext()) {
            UIComponent kid = (UIComponent)kids.next();
            kid.processDecodes(context);
         }

         try {
            this.decode(context);
         } catch (RuntimeException var4) {
            context.renderResponse();
            throw var4;
         }
      }
   }

   public void processValidators(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else if (this.isRendered()) {
         Iterator kids = this.getFacetsAndChildren();

         while(kids.hasNext()) {
            UIComponent kid = (UIComponent)kids.next();
            kid.processValidators(context);
         }

      }
   }

   public void processUpdates(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else if (this.isRendered()) {
         Iterator kids = this.getFacetsAndChildren();

         while(kids.hasNext()) {
            UIComponent kid = (UIComponent)kids.next();
            kid.processUpdates(context);
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
         stateStruct[0] = this.saveState(context);
         int count = this.getChildCount() + this.getFacetCount();
         if (count > 0) {
            List stateList = new ArrayList(count);
            Iterator myFacets;
            UIComponent facet;
            if (this.getChildCount() > 0) {
               myFacets = this.getChildren().iterator();

               while(myFacets.hasNext()) {
                  facet = (UIComponent)myFacets.next();
                  if (!facet.isTransient()) {
                     stateList.add(facet.processSaveState(context));
                  }
               }
            }

            if (this.getFacetCount() > 0) {
               myFacets = this.getFacets().entrySet().iterator();

               while(myFacets.hasNext()) {
                  Map.Entry entry = (Map.Entry)myFacets.next();
                  facet = (UIComponent)entry.getValue();
                  if (!facet.isTransient()) {
                     Object facetState = facet.processSaveState(context);
                     Object[] facetSaveState = new Object[]{entry.getKey(), facetState};
                     stateList.add(facetSaveState);
                  }
               }
            }

            childState = stateList.toArray();
         }

         stateStruct[1] = childState;
         return stateStruct;
      }
   }

   public void processRestoreState(FacesContext context, Object state) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         Object[] stateStruct = (Object[])((Object[])state);
         Object[] childState = (Object[])((Object[])stateStruct[1]);
         this.restoreState(context, stateStruct[0]);
         int i = 0;
         if (this.getChildCount() > 0) {
            Iterator kids = this.getChildren().iterator();

            while(kids.hasNext()) {
               UIComponent kid = (UIComponent)kids.next();
               if (!kid.isTransient()) {
                  Object currentState = childState[i++];
                  if (currentState != null) {
                     kid.processRestoreState(context, currentState);
                  }
               }
            }
         }

         if (this.getFacetCount() > 0) {
            int facetsSize = this.getFacets().size();

            for(int j = 0; j < facetsSize; ++j) {
               Object[] facetSaveState;
               if (null != (facetSaveState = (Object[])((Object[])childState[i++]))) {
                  String facetName = (String)facetSaveState[0];
                  Object facetState = facetSaveState[1];
                  UIComponent facet = (UIComponent)this.getFacets().get(facetName);
                  facet.processRestoreState(context, facetState);
               }
            }
         }

      }
   }

   protected FacesContext getFacesContext() {
      return FacesContext.getCurrentInstance();
   }

   protected Renderer getRenderer(FacesContext context) {
      String rendererType = this.getRendererType();
      Renderer result = null;
      if (rendererType != null) {
         result = context.getRenderKit().getRenderer(this.getFamily(), rendererType);
         if (null == result && log.isLoggable(Level.FINE)) {
            log.fine("Can't get Renderer for type " + rendererType);
         }
      } else if (log.isLoggable(Level.FINE)) {
         String id = this.getId();
         id = null != id ? id : this.getClass().getName();
         log.fine("No renderer-type for component " + id);
      }

      return result;
   }

   private static boolean isSaveBindingsState(FacesContext context) {
      ExternalContext extContext = context.getExternalContext();
      Map requestMap = extContext.getRequestMap();
      Boolean flag = Boolean.TRUE;
      if (null == (flag = (Boolean)requestMap.get("com.sun.faces.IS_SAVE_BINDINGS_STATE"))) {
         Map appMap = extContext.getApplicationMap();
         if (null == (flag = (Boolean)appMap.get("com.sun.faces.IS_SAVE_BINDINGS_STATE"))) {
            String setting = extContext.getInitParameter("com.sun.faces.IS_SAVE_BINDINGS_STATE");
            flag = setting == null || Boolean.parseBoolean(setting);
            appMap.put("com.sun.faces.IS_SAVE_BINDINGS_STATE", flag);
         }

         requestMap.put("com.sun.faces.IS_SAVE_BINDINGS_STATE", flag);
      }

      return flag;
   }

   public Object saveState(FacesContext context) {
      if (this.values == null) {
         this.values = new Object[9];
      }

      if (this.attributes != null) {
         Map backing = this.attributes.getBackingAttributes();
         if (backing != null && !backing.isEmpty()) {
            this.values[0] = backing;
         }
      }

      Object bindingsState = this.saveBindingsState(context);
      if (isSaveBindingsState(context)) {
         this.values[1] = bindingsState;
      }

      this.values[2] = this.clientId;
      this.values[3] = this.id;
      this.values[4] = this.rendered ? Boolean.TRUE : Boolean.FALSE;
      this.values[5] = this.renderedSet ? Boolean.TRUE : Boolean.FALSE;
      this.values[6] = this.rendererType;
      this.values[7] = saveAttachedState(context, this.listeners);
      this.values[8] = this.attributesThatAreSet;

      assert !this.transientFlag;

      return this.values;
   }

   public void restoreState(FacesContext context, Object state) {
      this.values = (Object[])((Object[])state);
      if (this.values[0] != null) {
         this.attributes = new AttributesMap(this, (HashMap)TypedCollections.dynamicallyCastMap((Map)this.values[0], String.class, Object.class));
      }

      this.bindings = restoreBindingsState(context, this.values[1]);
      this.clientId = (String)this.values[2];
      this.id = (String)this.values[3];
      this.rendered = (Boolean)this.values[4];
      this.renderedSet = (Boolean)this.values[5];
      this.rendererType = (String)this.values[6];
      List restoredListeners;
      if (null != (restoredListeners = TypedCollections.dynamicallyCastList((List)restoreAttachedState(context, this.values[7]), FacesListener.class))) {
         if (null != this.listeners) {
            this.listeners.addAll(restoredListeners);
         } else {
            this.listeners = restoredListeners;
         }
      }

      this.attributesThatAreSet = (List)this.values[8];
   }

   public boolean isTransient() {
      return this.transientFlag;
   }

   public void setTransient(boolean transientFlag) {
      this.transientFlag = transientFlag;
   }

   public static Object saveAttachedState(FacesContext context, Object attachedObject) {
      if (null == context) {
         throw new NullPointerException();
      } else if (null == attachedObject) {
         return null;
      } else {
         Object result;
         if (attachedObject instanceof List) {
            List attachedList = (List)attachedObject;
            List resultList = new ArrayList(attachedList.size());
            Iterator listIter = attachedList.iterator();

            while(listIter.hasNext()) {
               Object cur;
               if (null != (cur = listIter.next())) {
                  resultList.add(new StateHolderSaver(context, cur));
               }
            }

            result = resultList;
         } else {
            result = new StateHolderSaver(context, attachedObject);
         }

         return result;
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
            List retList = new ArrayList(stateList.size());
            Iterator var5 = stateList.iterator();

            while(var5.hasNext()) {
               Object item = var5.next();

               try {
                  retList.add(((StateHolderSaver)item).restore(context));
               } catch (ClassCastException var8) {
                  throw new IllegalStateException("Unknown object type");
               }
            }

            result = retList;
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

   Map getDescriptorMap() {
      return this.pdMap;
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
         Iterator v = c.iterator();

         do {
            if (!v.hasNext()) {
               return true;
            }
         } while(this.map.containsKey(v.next()));

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
         Iterator v = c.iterator();

         while(v.hasNext()) {
            Object o = v.next();
            if (this.map.containsKey(o)) {
               this.map.remove(o);
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
         Iterator v = c.iterator();

         while(v.hasNext()) {
            if (this.remove(v.next())) {
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
      UIComponent component;

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
               value.setParent(this.component);
               return (UIComponent)super.put(key, value);
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
            element.setParent(this.component);
            super.add(index, element);
         } else {
            throw new IndexOutOfBoundsException();
         }
      }

      public boolean add(UIComponent element) {
         if (element == null) {
            throw new NullPointerException();
         } else {
            UIComponentBase.eraseParent(element);
            element.setParent(this.component);
            return super.add(element);
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
         super.remove(index);
         child.setParent((UIComponent)null);
         return child;
      }

      public boolean remove(Object elementObj) {
         UIComponent element = (UIComponent)elementObj;
         if (element == null) {
            throw new NullPointerException();
         } else if (super.remove(element)) {
            element.setParent((UIComponent)null);
            return true;
         } else {
            return false;
         }
      }

      public boolean removeAll(Collection collection) {
         boolean result = false;
         Iterator elements = collection.iterator();

         while(elements.hasNext()) {
            if (this.remove(elements.next())) {
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
            previous.setParent((UIComponent)null);
            element.setParent(this.component);
            super.set(index, element);
            return previous;
         } else {
            throw new IndexOutOfBoundsException();
         }
      }
   }

   private static class AttributesMap implements Map, Serializable {
      private static final String ATTRIBUTES_THAT_ARE_SET_KEY = UIComponentBase.class.getName() + ".attributesThatAreSet";
      private HashMap attributes;
      private transient Map pdMap;
      private transient UIComponent component;
      private static final long serialVersionUID = -6773035086539772945L;

      private AttributesMap(UIComponent component) {
         this.component = component;
         this.pdMap = ((UIComponentBase)component).getDescriptorMap();
      }

      private AttributesMap(UIComponent component, HashMap attributes) {
         this(component);
         this.attributes = attributes;
      }

      public boolean containsKey(Object keyObj) {
         if (ATTRIBUTES_THAT_ARE_SET_KEY.equals(keyObj)) {
            return true;
         } else {
            String key = (String)keyObj;
            PropertyDescriptor pd = this.getPropertyDescriptor(key);
            if (pd == null) {
               return this.attributes != null ? this.attributes.containsKey(key) : false;
            } else {
               return false;
            }
         }
      }

      public Object get(Object keyObj) {
         String key = (String)keyObj;
         if (key == null) {
            throw new NullPointerException();
         } else if (ATTRIBUTES_THAT_ARE_SET_KEY.equals(key)) {
            return this.component.getAttributesThatAreSet(false);
         } else {
            PropertyDescriptor pd = this.getPropertyDescriptor(key);
            if (pd != null) {
               try {
                  Method readMethod = pd.getReadMethod();
                  if (readMethod != null) {
                     return readMethod.invoke(this.component, UIComponentBase.EMPTY_OBJECT_ARRAY);
                  } else {
                     throw new IllegalArgumentException(key);
                  }
               } catch (IllegalAccessException var6) {
                  throw new FacesException(var6);
               } catch (InvocationTargetException var7) {
                  throw new FacesException(var7.getTargetException());
               }
            } else if (this.attributes != null && this.attributes.containsKey(key)) {
               return this.attributes.get(key);
            } else {
               ValueExpression ve = this.component.getValueExpression(key);
               if (ve != null) {
                  try {
                     return ve.getValue(this.component.getFacesContext().getELContext());
                  } catch (ELException var8) {
                     throw new FacesException(var8);
                  }
               } else {
                  return null;
               }
            }
         }
      }

      public Object put(String keyValue, Object value) {
         if (keyValue == null) {
            throw new NullPointerException();
         } else if (ATTRIBUTES_THAT_ARE_SET_KEY.equals(keyValue)) {
            if (this.component.attributesThatAreSet == null && value instanceof List) {
               this.component.attributesThatAreSet = (List)value;
               return this.component.attributesThatAreSet;
            } else {
               return null;
            }
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
               if (this.attributes == null) {
                  this.initMap();
               }

               List sProperties = this.component.getAttributesThatAreSet(true);
               if (sProperties != null && !sProperties.contains(keyValue)) {
                  sProperties.add(keyValue);
               }

               return this.attributes.put(keyValue, value);
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
            } else if (this.attributes != null) {
               List sProperties = this.component.getAttributesThatAreSet(false);
               if (sProperties != null) {
                  sProperties.remove(key);
               }

               return this.attributes.remove(key);
            } else {
               return null;
            }
         }
      }

      public int size() {
         return this.attributes != null ? this.attributes.size() : 0;
      }

      public boolean isEmpty() {
         return this.attributes == null || this.attributes.isEmpty();
      }

      public boolean containsValue(Object value) {
         return this.attributes != null && this.attributes.containsValue(value);
      }

      public void clear() {
         if (this.attributes != null) {
            this.attributes.clear();
         }

      }

      public Set keySet() {
         return this.attributes != null ? this.attributes.keySet() : Collections.emptySet();
      }

      public Collection values() {
         return (Collection)(this.attributes != null ? this.attributes.values() : Collections.emptyList());
      }

      public Set entrySet() {
         return this.attributes != null ? this.attributes.entrySet() : Collections.emptySet();
      }

      Map getBackingAttributes() {
         return this.attributes;
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
                  label43:
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
                           continue label43;
                        }
                     } while(value.equals(t.get(key)));

                     return false;
                  } while(t.get(key) == null && t.containsKey(key));

                  return false;
               } catch (ClassCastException var8) {
                  return false;
               } catch (NullPointerException var9) {
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

      private void initMap() {
         this.attributes = new HashMap(8);
      }

      PropertyDescriptor getPropertyDescriptor(String name) {
         return this.pdMap != null ? (PropertyDescriptor)this.pdMap.get(name) : null;
      }

      private void writeObject(ObjectOutputStream out) throws IOException {
         if (this.attributes == null) {
            out.writeObject(new HashMap(1, 1.0F));
         } else {
            out.writeObject(this.attributes);
         }

         out.writeObject(this.component.getClass());
         out.writeObject(this.component.saveState(FacesContext.getCurrentInstance()));
      }

      private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
         this.attributes = null;
         this.pdMap = null;
         this.component = null;
         this.attributes = (HashMap)in.readObject();
         Class clazz = (Class)in.readObject();

         try {
            this.component = (UIComponent)clazz.newInstance();
         } catch (Exception var4) {
            throw new RuntimeException(var4);
         }

         this.component.restoreState(FacesContext.getCurrentInstance(), in.readObject());
      }

      // $FF: synthetic method
      AttributesMap(UIComponent x0, Object x1) {
         this(x0);
      }

      // $FF: synthetic method
      AttributesMap(UIComponent x0, HashMap x1, Object x2) {
         this(x0, x1);
      }
   }
}
