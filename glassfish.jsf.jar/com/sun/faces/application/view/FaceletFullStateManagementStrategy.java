package com.sun.faces.application.view;

import com.sun.faces.context.StateContext;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.util.ComponentStruct;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.application.ProjectStage;
import javax.faces.application.ViewHandler;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitHint;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.render.ResponseStateManager;
import javax.faces.view.StateManagementStrategy;
import javax.faces.view.ViewDeclarationLanguage;

public class FaceletFullStateManagementStrategy extends StateManagementStrategy {
   private static final Logger LOGGER;
   private static final String SKIP_ITERATION_HINT = "javax.faces.visit.SKIP_ITERATION";
   private Map classMap;
   private boolean isDevelopmentMode;

   public FaceletFullStateManagementStrategy() {
      this(FacesContext.getCurrentInstance());
   }

   public FaceletFullStateManagementStrategy(FacesContext context) {
      this.isDevelopmentMode = context.isProjectStage(ProjectStage.Development);
      this.classMap = new ConcurrentHashMap(32);
   }

   private void captureChild(List tree, int parent, UIComponent component) {
      if (!component.isTransient() && !component.getAttributes().containsKey("com.sun.faces.DynamicComponent")) {
         TreeNode treeNode = new TreeNode(parent, component);
         int pos = tree.size();
         tree.add(treeNode);
         this.captureRest(tree, pos, component);
      }

   }

   private void captureFacet(List tree, int parent, String name, UIComponent component) {
      if (!component.isTransient() && !component.getAttributes().containsKey("com.sun.faces.DynamicComponent")) {
         FacetNode facetNode = new FacetNode(parent, name, component);
         int pos = tree.size();
         tree.add(facetNode);
         this.captureRest(tree, pos, component);
      }

   }

   private void captureRest(List tree, int pos, UIComponent component) {
      int sz = component.getChildCount();
      if (sz > 0) {
         List child = component.getChildren();

         for(int i = 0; i < sz; ++i) {
            this.captureChild(tree, pos, (UIComponent)child.get(i));
         }
      }

      sz = component.getFacetCount();
      if (sz > 0) {
         Iterator var7 = component.getFacets().entrySet().iterator();

         while(var7.hasNext()) {
            Map.Entry entry = (Map.Entry)var7.next();
            this.captureFacet(tree, pos, (String)entry.getKey(), (UIComponent)entry.getValue());
         }
      }

   }

   private UIComponent locateComponentByClientId(FacesContext context, UIComponent subTree, final String clientId) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "FaceletFullStateManagementStrategy.locateComponentByClientId", clientId);
      }

      final List found = new ArrayList();
      UIComponent result = null;

      try {
         context.getAttributes().put("javax.faces.visit.SKIP_ITERATION", true);
         Set hints = EnumSet.of(VisitHint.SKIP_ITERATION);
         VisitContext visitContext = VisitContext.createVisitContext(context, (Collection)null, hints);
         subTree.visitTree(visitContext, new VisitCallback() {
            public VisitResult visit(VisitContext visitContext, UIComponent component) {
               VisitResult result = VisitResult.ACCEPT;
               if (component.getClientId(visitContext.getFacesContext()).equals(clientId)) {
                  found.add(component);
                  result = VisitResult.COMPLETE;
               } else if (component instanceof UIForm) {
                  UIForm form = (UIForm)component;
                  if (form.isPrependId() && !clientId.startsWith(form.getClientId(visitContext.getFacesContext()))) {
                     result = VisitResult.REJECT;
                  }
               } else if (component instanceof NamingContainer && !clientId.startsWith(component.getClientId(visitContext.getFacesContext()))) {
                  result = VisitResult.REJECT;
               }

               return result;
            }
         });
      } finally {
         context.getAttributes().remove("javax.faces.visit.SKIP_ITERATION");
      }

      if (!found.isEmpty()) {
         result = (UIComponent)found.get(0);
      }

      return result;
   }

   private UIComponent newInstance(TreeNode treeNode) throws FacesException {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "FaceletFullStateManagementStrategy.newInstance", treeNode.componentType);
      }

      try {
         Class clazz = this.classMap != null ? (Class)this.classMap.get(treeNode.componentType) : null;
         if (clazz == null) {
            clazz = Util.loadClass(treeNode.componentType, treeNode);
            if (!Util.isAnyNull(clazz, this.classMap)) {
               this.classMap.put(treeNode.componentType, clazz);
            } else if (!this.isDevelopmentMode) {
               throw new NullPointerException();
            }
         }

         UIComponent component = (UIComponent)clazz.newInstance();
         component.setId(treeNode.id);
         return component;
      } catch (NullPointerException | InstantiationException | IllegalAccessException | ClassNotFoundException var4) {
         throw new FacesException(var4);
      }
   }

   private void pruneAndReAddToDynamicActions(List dynamicActionList, ComponentStruct struct) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.finest("FaceletFullStateManagementStrategy.pruneAndReAddToDynamicActions");
      }

      int firstIndex = dynamicActionList.indexOf(struct);
      if (firstIndex == -1) {
         dynamicActionList.add(struct);
      } else {
         int lastIndex = dynamicActionList.lastIndexOf(struct);
         if (lastIndex != -1 && lastIndex != firstIndex) {
            if ("ADD".equals(struct.getAction())) {
               dynamicActionList.remove(lastIndex);
               dynamicActionList.remove(firstIndex);
               dynamicActionList.add(struct);
            }

            if ("REMOVE".equals(struct.getAction())) {
               dynamicActionList.remove(lastIndex);
            }
         } else {
            dynamicActionList.add(struct);
         }
      }

   }

   private void restoreComponentState(final FacesContext context, final HashMap state) {
      final StateContext stateContext = StateContext.getStateContext(context);
      UIViewRoot viewRoot = context.getViewRoot();

      try {
         context.getAttributes().put("javax.faces.visit.SKIP_ITERATION", true);
         Set hints = EnumSet.of(VisitHint.SKIP_ITERATION);
         VisitContext visitContext = VisitContext.createVisitContext(context, (Collection)null, hints);
         viewRoot.visitTree(visitContext, new VisitCallback() {
            public VisitResult visit(VisitContext visitContext, UIComponent component) {
               VisitResult result = VisitResult.ACCEPT;
               String clientId = component.getClientId(context);
               Object stateObj = state.get(clientId);
               if (stateObj != null && !stateContext.componentAddedDynamically(component)) {
                  boolean restoreStateNow = true;
                  if (stateObj instanceof StateHolderSaver) {
                     restoreStateNow = !((StateHolderSaver)stateObj).componentAddedDynamically();
                  }

                  if (restoreStateNow) {
                     try {
                        component.restoreState(context, stateObj);
                     } catch (Exception var8) {
                        throw new FacesException(var8);
                     }
                  }
               }

               return result;
            }
         });
      } finally {
         context.getAttributes().remove("javax.faces.visit.SKIP_ITERATION");
      }

   }

   private void restoreDynamicActions(FacesContext context, StateContext stateContext, HashMap state) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.finest("FaceletFullStateManagementStrategy.restoreDynamicActions");
      }

      List savedActions = (List)context.getViewRoot().getAttributes().get("com.sun.faces.DynamicActions");
      List actions = stateContext.getDynamicActions();
      ComponentStruct action;
      if (!Util.isEmpty((Collection)savedActions)) {
         for(Iterator var6 = savedActions.iterator(); var6.hasNext(); this.pruneAndReAddToDynamicActions(actions, action)) {
            Object savedAction = var6.next();
            action = new ComponentStruct();
            action.restoreState(context, savedAction);
            if ("ADD".equals(action.getAction())) {
               this.restoreDynamicAdd(context, state, action);
            }

            if ("REMOVE".equals(action.getAction())) {
               this.restoreDynamicRemove(context, action);
            }
         }
      }

   }

   private void restoreDynamicAdd(FacesContext context, Map state, ComponentStruct struct) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.finest("FaceletFullStateManagementStrategy.restoreDynamicAdd");
      }

      UIComponent parent = this.locateComponentByClientId(context, context.getViewRoot(), struct.getParentClientId());
      if (parent != null) {
         UIComponent child = this.locateComponentByClientId(context, parent, struct.getClientId());
         if (child != null) {
            if (struct.getFacetName() == null) {
               parent.getChildren().remove(child);
            } else {
               parent.getFacets().remove(struct.getFacetName());
            }
         }

         if (child == null) {
            StateHolderSaver saver = (StateHolderSaver)state.get(struct.getClientId());
            if (saver != null) {
               child = (UIComponent)saver.restore(context);
            }
         }

         StateContext stateContext = StateContext.getStateContext(context);
         if (child == null) {
            child = (UIComponent)stateContext.getDynamicComponents().get(struct.getClientId());
         }

         if (child != null) {
            if (struct.getFacetName() != null) {
               parent.getFacets().put(struct.getFacetName(), child);
            } else {
               int childIndex = -1;
               if (child.getAttributes().containsKey("com.sun.faces.DynamicComponent")) {
                  childIndex = (Integer)child.getAttributes().get("com.sun.faces.DynamicComponent");
               }

               child.setId(struct.getId());
               if (childIndex < parent.getChildCount() && childIndex != -1) {
                  parent.getChildren().add(childIndex, child);
               } else {
                  parent.getChildren().add(child);
               }

               child.getClientId();
            }

            child.getAttributes().put("com.sun.faces.DynamicComponent", child.getParent().getChildren().indexOf(child));
            stateContext.getDynamicComponents().put(struct.getClientId(), child);
         }
      }

   }

   private void restoreDynamicRemove(FacesContext context, ComponentStruct struct) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.finest("FaceletFullStateManagementStrategy.restoreDynamicRemove");
      }

      UIComponent child = this.locateComponentByClientId(context, context.getViewRoot(), struct.getClientId());
      if (child != null) {
         StateContext stateContext = StateContext.getStateContext(context);
         stateContext.getDynamicComponents().put(struct.getClientId(), child);
         UIComponent parent = child.getParent();
         parent.getChildren().remove(child);
      }

   }

   private UIViewRoot restoreTree(FacesContext context, String renderKitId, Object[] tree) throws FacesException {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "FaceletFullStateManagementStrategy.restoreTree", renderKitId);
      }

      for(int i = 0; i < tree.length; ++i) {
         UIComponent component;
         if (tree[i] instanceof FacetNode) {
            FacetNode facetNode = (FacetNode)tree[i];
            component = this.newInstance(facetNode);
            tree[i] = component;
            if (i != facetNode.getParent()) {
               ((UIComponent)tree[facetNode.getParent()]).getFacets().put(facetNode.facetName, component);
            }
         } else {
            TreeNode treeNode = (TreeNode)tree[i];
            component = this.newInstance(treeNode);
            tree[i] = component;
            if (i != treeNode.parent) {
               ((UIComponent)tree[treeNode.parent]).getChildren().add(component);
            } else {
               UIViewRoot viewRoot = (UIViewRoot)component;
               context.setViewRoot(viewRoot);
               viewRoot.setRenderKitId(renderKitId);
            }
         }
      }

      return (UIViewRoot)tree[0];
   }

   public UIViewRoot restoreView(FacesContext context, String viewId, String renderKitId) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "FaceletFullStateManagementStrategy.restoreView", new Object[]{viewId, renderKitId});
      }

      UIViewRoot result = null;
      ResponseStateManager rsm = RenderKitUtils.getResponseStateManager(context, renderKitId);
      Object[] state = (Object[])((Object[])rsm.getState(context, viewId));
      if (state != null && state.length >= 2) {
         if (state[0] != null) {
            result = this.restoreTree(context, renderKitId, (Object[])((Object[])((Object[])state[0])).clone());
            context.setViewRoot(result);
         }

         if (result != null) {
            StateContext stateContext = StateContext.getStateContext(context);
            stateContext.startTrackViewModifications(context, result);
            stateContext.setTrackViewModifications(false);

            try {
               HashMap stateMap = (HashMap)state[1];
               if (stateMap != null) {
                  this.restoreComponentState(context, stateMap);
                  this.restoreDynamicActions(context, stateContext, stateMap);
               }
            } finally {
               stateContext.setTrackViewModifications(true);
            }
         }
      }

      ViewHandler viewHandler = context.getApplication().getViewHandler();
      ViewDeclarationLanguage vdl = viewHandler.getViewDeclarationLanguage(context, viewId);
      context.setResourceLibraryContracts(vdl.calculateResourceLibraryContracts(context, viewId));
      return result;
   }

   private Object saveComponentState(FacesContext context) {
      final HashMap stateMap = new HashMap();
      final StateContext stateContext = StateContext.getStateContext(context);
      UIViewRoot viewRoot = context.getViewRoot();
      final FacesContext finalContext = context;
      context.getAttributes().put("javax.faces.visit.SKIP_ITERATION", true);
      Set hints = EnumSet.of(VisitHint.SKIP_ITERATION);
      VisitContext visitContext = VisitContext.createVisitContext(context, (Collection)null, hints);

      try {
         viewRoot.visitTree(visitContext, new VisitCallback() {
            public VisitResult visit(VisitContext context, UIComponent component) {
               VisitResult result = VisitResult.ACCEPT;
               if (!component.isTransient()) {
                  Object stateObj;
                  if (stateContext.componentAddedDynamically(component)) {
                     component.getAttributes().put("com.sun.faces.DynamicComponent", new Integer(FaceletFullStateManagementStrategy.this.getProperChildIndex(component)));
                     stateObj = new StateHolderSaver(finalContext, component);
                  } else {
                     stateObj = component.saveState(finalContext);
                  }

                  if (stateObj != null) {
                     stateMap.put(component.getClientId(finalContext), stateObj);
                  }
               } else {
                  result = VisitResult.REJECT;
               }

               return result;
            }
         });
      } finally {
         context.getAttributes().remove("javax.faces.visit.SKIP_ITERATION");
      }

      return stateMap;
   }

   private void saveDynamicActions(FacesContext context, StateContext stateContext, UIViewRoot viewRoot) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.finest("FaceletFullStateManagementStrategy.saveDynamicActions");
      }

      List actions = stateContext.getDynamicActions();
      HashMap componentMap = stateContext.getDynamicComponents();
      if (actions != null) {
         List savedActions = new ArrayList(actions.size());
         Iterator var7 = actions.iterator();

         while(var7.hasNext()) {
            ComponentStruct action = (ComponentStruct)var7.next();
            UIComponent component = (UIComponent)componentMap.get(action.getClientId());
            if (component == null && context.isProjectStage(ProjectStage.Development)) {
               LOGGER.log(Level.WARNING, "Unable to save dynamic action with clientId ''{0}'' because the UIComponent cannot be found", action.getClientId());
            }

            if (component != null) {
               savedActions.add(action.saveState(context));
            }
         }

         viewRoot.getAttributes().put("com.sun.faces.DynamicActions", savedActions);
      }

   }

   public Object saveView(FacesContext context) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.finest("FaceletFullStateManagementStrategy.saveView");
      }

      UIViewRoot viewRoot = context.getViewRoot();
      Util.checkIdUniqueness(context, viewRoot, new HashSet(viewRoot.getChildCount() << 1));
      StateContext stateContext = StateContext.getStateContext(context);
      this.saveDynamicActions(context, stateContext, viewRoot);
      Object state = this.saveComponentState(context);
      List treeList = new ArrayList(32);
      this.captureChild(treeList, 0, viewRoot);
      Object[] tree = treeList.toArray();
      Object[] result = new Object[]{tree, state};
      StateContext.release(context);
      return result;
   }

   private int getProperChildIndex(UIComponent component) {
      int result = -1;
      if (component.getParent().getChildren().indexOf(component) != -1) {
         UIComponent parent = component.getParent();
         int index = 0;
         Iterator iterator = parent.getChildren().iterator();

         while(iterator.hasNext()) {
            UIComponent child = (UIComponent)iterator.next();
            if (child == component) {
               break;
            }

            if (!child.isTransient()) {
               ++index;
            }
         }

         if (index == 0 && !parent.getChildren().isEmpty() && ((UIComponent)parent.getChildren().get(0)).isTransient()) {
            index = -1;
         }

         result = index;
      }

      return result;
   }

   static {
      LOGGER = FacesLogger.APPLICATION_VIEW.getLogger();
   }

   private static class TreeNode implements Externalizable {
      private static final long serialVersionUID = -835775352718473281L;
      private static final String NULL_ID = "";
      private String componentType;
      private String id;
      private int parent;

      public TreeNode() {
      }

      public TreeNode(int parent, UIComponent c) {
         this.parent = parent;
         this.id = c.getId();
         this.componentType = c.getClass().getName();
      }

      public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
         this.parent = in.readInt();
         this.componentType = in.readUTF();
         this.id = in.readUTF();
         if (this.id.length() == 0) {
            this.id = null;
         }

      }

      public void writeExternal(ObjectOutput out) throws IOException {
         out.writeInt(this.parent);
         out.writeUTF(this.componentType);
         if (this.id != null) {
            out.writeUTF(this.id);
         } else {
            out.writeUTF("");
         }

      }

      public int getParent() {
         return this.parent;
      }
   }

   private static final class FacetNode extends TreeNode {
      private static final long serialVersionUID = -3777170310958005106L;
      private String facetName;

      public FacetNode() {
      }

      public FacetNode(int parent, String name, UIComponent c) {
         super(parent, c);
         this.facetName = name;
      }

      public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
         super.readExternal(in);
         this.facetName = in.readUTF();
      }

      public void writeExternal(ObjectOutput out) throws IOException {
         super.writeExternal(out);
         out.writeUTF(this.facetName);
      }
   }
}
