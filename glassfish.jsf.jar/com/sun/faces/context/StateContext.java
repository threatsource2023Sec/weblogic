package com.sun.faces.context;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.application.ApplicationStateInfo;
import com.sun.faces.facelets.tag.jsf.ComponentSupport;
import com.sun.faces.util.ComponentStruct;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MostlySingletonSet;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.event.PreRemoveFromViewEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

public class StateContext {
   private static final String KEY = StateContext.class.getName() + "_KEY";
   private boolean partial;
   private boolean partialLocked;
   private boolean trackMods = true;
   private AddRemoveListener modListener;
   private ApplicationStateInfo stateInfo;
   private WeakReference viewRootRef = new WeakReference((Object)null);
   private static final Logger LOGGER;

   private StateContext(ApplicationStateInfo stateInfo) {
      this.stateInfo = stateInfo;
   }

   public static void release(FacesContext facesContext) {
      StateContext stateContext = (StateContext)facesContext.getAttributes().get(KEY);
      UIViewRoot viewRoot = facesContext.getViewRoot();
      if (viewRoot != null && stateContext.modListener != null) {
         viewRoot.unsubscribeFromViewEvent(PostAddToViewEvent.class, stateContext.modListener);
         viewRoot.unsubscribeFromViewEvent(PreRemoveFromViewEvent.class, stateContext.modListener);
      }

      facesContext.getAttributes().remove(KEY);
   }

   public static StateContext getStateContext(FacesContext ctx) {
      StateContext stateCtx = (StateContext)ctx.getAttributes().get(KEY);
      if (stateCtx == null) {
         ApplicationAssociate associate = ApplicationAssociate.getCurrentInstance();
         ApplicationStateInfo info = associate.getApplicationStateInfo();
         stateCtx = new StateContext(info);
         ctx.getAttributes().put(KEY, stateCtx);
      }

      return stateCtx;
   }

   public boolean isPartialStateSaving(FacesContext ctx, String viewId) {
      UIViewRoot root = ctx.getViewRoot();
      UIViewRoot refRoot = (UIViewRoot)this.viewRootRef.get();
      if (root != refRoot) {
         this.viewRootRef = new WeakReference(root);
         if (refRoot != null) {
            this.modListener = null;
            this.partialLocked = false;
         }
      }

      if (!this.partialLocked) {
         if (viewId == null) {
            if (root != null) {
               viewId = root.getViewId();
            } else {
               viewId = (String)ctx.getAttributes().get("com.sun.faces.viewId");
            }
         }

         this.partial = this.stateInfo.usePartialStateSaving(viewId);
         this.partialLocked = true;
      }

      return this.partial;
   }

   public boolean trackViewModifications() {
      return this.trackMods;
   }

   public void startTrackViewModifications(FacesContext ctx, UIViewRoot root) {
      if (this.modListener == null) {
         if (root != null) {
            this.modListener = this.createAddRemoveListener(ctx, root);
            root.subscribeToViewEvent(PostAddToViewEvent.class, this.modListener);
            root.subscribeToViewEvent(PreRemoveFromViewEvent.class, this.modListener);
         } else {
            LOGGER.warning("Unable to attach AddRemoveListener to UIViewRoot because it is null");
         }
      }

      this.setTrackViewModifications(true);
   }

   public void setTrackViewModifications(boolean trackMods) {
      this.trackMods = trackMods;
   }

   public boolean componentAddedDynamically(UIComponent c) {
      return c.getAttributes().containsKey("com.sun.faces.DynamicComponent");
   }

   public int getIndexOfDynamicallyAddedChildInParent(UIComponent c) {
      int result = -1;
      Map attrs = c.getAttributes();
      if (attrs.containsKey("com.sun.faces.DynamicComponent")) {
         result = (Integer)attrs.get("com.sun.faces.DynamicComponent");
      }

      return result;
   }

   public boolean hasOneOrMoreDynamicChild(UIComponent parent) {
      return parent.getAttributes().containsKey("com.sun.faces.DynamicChildCount");
   }

   private int incrementDynamicChildCount(FacesContext context, UIComponent parent) {
      Map attrs = parent.getAttributes();
      Integer cur = (Integer)attrs.get("com.sun.faces.DynamicChildCount");
      int result;
      if (null != cur) {
         cur + 1;
         result = cur;
      } else {
         result = 1;
      }

      attrs.put("com.sun.faces.DynamicChildCount", result);
      context.getViewRoot().getAttributes().put("com.sun.faces.TreeHasDynamicComponents", Boolean.TRUE);
      return result;
   }

   private int decrementDynamicChildCount(FacesContext context, UIComponent parent) {
      int result = 0;
      Map attrs = parent.getAttributes();
      Integer cur = (Integer)attrs.get("com.sun.faces.DynamicChildCount");
      if (null != cur) {
         int var10000;
         if (0 < cur) {
            Integer var6 = cur;
            cur = cur - 1;
            var10000 = var6;
         } else {
            var10000 = 0;
         }

         result = var10000;
      }

      if (0 == result && null != cur) {
         attrs.remove("com.sun.faces.DynamicChildCount");
      }

      context.getViewRoot().getAttributes().put("com.sun.faces.TreeHasDynamicComponents", Boolean.TRUE);
      return result;
   }

   public List getDynamicActions() {
      return this.modListener != null ? this.modListener.getDynamicActions() : null;
   }

   public HashMap getDynamicComponents() {
      return this.modListener != null ? this.modListener.getDynamicComponents() : null;
   }

   private AddRemoveListener createAddRemoveListener(FacesContext context, UIViewRoot root) {
      return (AddRemoveListener)(this.isPartialStateSaving(context, root.getViewId()) ? new DynamicAddRemoveListener(context) : new StatelessAddRemoveListener(context));
   }

   static {
      LOGGER = FacesLogger.CONTEXT.getLogger();
   }

   public class DynamicAddRemoveListener extends AddRemoveListener {
      private List dynamicActions;
      private transient HashMap dynamicComponents;

      public DynamicAddRemoveListener(FacesContext context) {
         super(context);
      }

      public List getDynamicActions() {
         synchronized(this) {
            if (this.dynamicActions == null) {
               this.dynamicActions = new ArrayList();
            }
         }

         return this.dynamicActions;
      }

      public HashMap getDynamicComponents() {
         synchronized(this) {
            if (this.dynamicComponents == null) {
               this.dynamicComponents = new HashMap();
            }
         }

         return this.dynamicComponents;
      }

      protected void handleRemove(FacesContext context, UIComponent component) {
         if (component.isInView()) {
            StateContext.this.decrementDynamicChildCount(context, component.getParent());
            this.handleAddRemoveWithAutoPrune(component, new ComponentStruct("REMOVE", component.getClientId(context), component.getId()));
         }

      }

      protected void handleAdd(FacesContext context, UIComponent component) {
         if (component.getParent() != null && component.getParent().isInView()) {
            String id = component.getId();
            if (id != null) {
               component.setId(id);
            }

            String facetName = this.findFacetNameForComponent(component);
            ComponentStruct struct;
            if (facetName != null) {
               StateContext.this.incrementDynamicChildCount(context, component.getParent());
               component.clearInitialState();
               component.getAttributes().put("com.sun.faces.DynamicComponent", component.getParent().getChildren().indexOf(component));
               struct = new ComponentStruct("ADD", facetName, component.getParent().getClientId(context), component.getClientId(context), component.getId());
               this.handleAddRemoveWithAutoPrune(component, struct);
            } else {
               StateContext.this.incrementDynamicChildCount(context, component.getParent());
               component.clearInitialState();
               component.getAttributes().put("com.sun.faces.DynamicComponent", component.getParent().getChildren().indexOf(component));
               struct = new ComponentStruct("ADD", (String)null, component.getParent().getClientId(context), component.getClientId(context), component.getId());
               this.handleAddRemoveWithAutoPrune(component, struct);
            }
         }

      }

      private String findFacetNameForComponent(UIComponent component) {
         Set entrySet = component.getParent().getFacets().entrySet();
         Iterator entries = entrySet.iterator();

         Map.Entry candidate;
         do {
            if (!entries.hasNext()) {
               return null;
            }

            candidate = (Map.Entry)entries.next();
         } while(component != candidate.getValue());

         return (String)candidate.getKey();
      }

      private void handleAddRemoveWithAutoPrune(UIComponent component, ComponentStruct struct) {
         List actionList = this.getDynamicActions();
         HashMap componentMap = this.getDynamicComponents();
         int firstIndex = actionList.indexOf(struct);
         if (firstIndex == -1) {
            actionList.add(struct);
            componentMap.put(struct.getClientId(), component);
         } else {
            int lastIndex = actionList.lastIndexOf(struct);
            if (lastIndex == firstIndex) {
               ComponentStruct previousStruct = (ComponentStruct)actionList.get(firstIndex);
               if ("ADD".equals(previousStruct.getAction())) {
                  if ("ADD".equals(struct.getAction())) {
                     throw new FacesException("Cannot add the same component twice: " + struct.getClientId());
                  }

                  if ("REMOVE".equals(struct.getAction())) {
                     actionList.remove(firstIndex);
                     componentMap.remove(struct.getClientId());
                  }
               }

               if ("REMOVE".equals(previousStruct.getAction())) {
                  if ("ADD".equals(struct.getAction())) {
                     actionList.add(struct);
                     componentMap.put(struct.getClientId(), component);
                  }

                  if ("REMOVE".equals(struct.getAction())) {
                     throw new FacesException("Cannot remove the same component twice: " + struct.getClientId());
                  }
               }
            } else {
               if ("ADD".equals(struct.getAction())) {
                  throw new FacesException("Cannot add the same component twice: " + struct.getClientId());
               }

               if ("REMOVE".equals(struct.getAction())) {
                  actionList.remove(lastIndex);
               }
            }
         }

      }
   }

   public class StatelessAddRemoveListener extends NoopAddRemoveListener {
      private static final String DYNAMIC_COMPONENT_ADD_COLLECTION = "com.sun.faces.DynamicComponentSubtreeRoots";

      public StatelessAddRemoveListener(FacesContext context) {
         super(context);
      }

      private boolean thisEventCorrespondsToSubtreeRootRemove(FacesContext context, UIComponent c) {
         boolean result = false;
         if (null != c) {
            c = c.getParent();
            if (null != c) {
               result = c.isInView();
            }
         }

         return result;
      }

      private boolean thisEventCorrespondsToSubtreeRootAdd(FacesContext context, UIComponent c) {
         boolean result = false;
         Map contextMap = context.getAttributes();
         UIViewRoot root = context.getViewRoot();
         UIComponent originalComponent = c;
         if (null != c) {
            Collection dynamics = this.getDynamicComponentCollection(contextMap);
            if (dynamics.contains(c)) {
               result = true;
            } else {
               for(c = c.getParent(); null != c && !dynamics.contains(c); c = c.getParent()) {
               }

               if (null == c || root.equals(c)) {
                  dynamics.add(originalComponent);
                  result = true;
               }
            }
         }

         return result;
      }

      private Collection getDynamicComponentCollection(Map contextMap) {
         Collection result = (Collection)contextMap.get("com.sun.faces.DynamicComponentSubtreeRoots");
         if (null == result) {
            result = new HashSet();
            contextMap.put("com.sun.faces.DynamicComponentSubtreeRoots", result);
         }

         return (Collection)result;
      }

      protected void handleRemove(FacesContext context, UIComponent component) {
         if (this.thisEventCorrespondsToSubtreeRootRemove(context, component)) {
            Map attrs = component.getAttributes();
            String tagId = (String)attrs.remove("com.sun.faces.facelets.MARK_ID");
            if (tagId != null) {
               attrs.put(ComponentSupport.MARK_CREATED_REMOVED, tagId);
               this.childRemovedFromParent(component.getParent(), tagId);
            }

         }
      }

      private void childRemovedFromParent(UIComponent parent, String childTagId) {
         if (parent != null) {
            Collection removedChildrenIds = this.getPreviouslyRemovedChildren(parent);
            removedChildrenIds.add(childTagId);
            this.markChildrenModified(parent);
         }

      }

      private Collection getPreviouslyRemovedChildren(UIComponent parent) {
         Map attrs = parent.getAttributes();
         Collection removedChildrenIds = (Collection)attrs.get("com.sun.faces.facelets.REMOVED_CHILDREN");
         if (removedChildrenIds == null) {
            removedChildrenIds = new MostlySingletonSet();
            attrs.put("com.sun.faces.facelets.REMOVED_CHILDREN", removedChildrenIds);
         }

         return (Collection)removedChildrenIds;
      }

      private void markChildrenModified(UIComponent parent) {
         parent.getAttributes().put("com.sun.faces.facelets.MARK_CHILDREN_MODIFIED", true);
      }

      protected void handleAdd(FacesContext context, UIComponent component) {
         if (this.thisEventCorrespondsToSubtreeRootAdd(context, component)) {
            Map attrs = component.getAttributes();
            String tagId = (String)attrs.get(ComponentSupport.MARK_CREATED_REMOVED);
            if (this.childAddedToSameParentAsBefore(component.getParent(), tagId)) {
               attrs.remove(ComponentSupport.MARK_CREATED_REMOVED);
               attrs.put("com.sun.faces.facelets.MARK_ID", tagId);
            }

            this.markChildrenModified(component.getParent());
         }
      }

      private boolean childAddedToSameParentAsBefore(UIComponent parent, String childTagId) {
         if (parent != null) {
            Map attrs = parent.getAttributes();
            Collection removedChildrenIds = (Collection)attrs.get("com.sun.faces.facelets.REMOVED_CHILDREN");
            if (removedChildrenIds != null && removedChildrenIds.remove(childTagId)) {
               if (removedChildrenIds.isEmpty()) {
                  attrs.remove("com.sun.faces.facelets.REMOVED_CHILDREN");
               }

               return true;
            }
         }

         return false;
      }
   }

   public class NoopAddRemoveListener extends AddRemoveListener {
      private HashMap emptyComponentsMap = new HashMap();

      public NoopAddRemoveListener(FacesContext context) {
         super(context);
      }

      public List getDynamicActions() {
         return Collections.emptyList();
      }

      public HashMap getDynamicComponents() {
         return this.emptyComponentsMap;
      }

      protected void handleRemove(FacesContext context, UIComponent component) {
      }

      protected void handleAdd(FacesContext context, UIComponent component) {
      }
   }

   private abstract class AddRemoveListener implements SystemEventListener {
      private StateContext stateCtx;

      protected AddRemoveListener(FacesContext context) {
         this.stateCtx = StateContext.getStateContext(context);
      }

      public abstract List getDynamicActions();

      public abstract HashMap getDynamicComponents();

      public void processEvent(SystemEvent event) throws AbortProcessingException {
         FacesContext ctx = FacesContext.getCurrentInstance();
         if (event instanceof PreRemoveFromViewEvent) {
            if (this.stateCtx.trackViewModifications()) {
               this.handleRemove(ctx, ((PreRemoveFromViewEvent)event).getComponent());
               ctx.getViewRoot().getAttributes().put("com.sun.faces.TreeHasDynamicComponents", Boolean.TRUE);
            }
         } else if (this.stateCtx.trackViewModifications()) {
            this.handleAdd(ctx, ((PostAddToViewEvent)event).getComponent());
            ctx.getViewRoot().getAttributes().put("com.sun.faces.TreeHasDynamicComponents", Boolean.TRUE);
         }

      }

      public boolean isListenerForSource(Object source) {
         return source instanceof UIComponent && !(source instanceof UIViewRoot);
      }

      protected abstract void handleRemove(FacesContext var1, UIComponent var2);

      protected abstract void handleAdd(FacesContext var1, UIComponent var2);
   }
}
