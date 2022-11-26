package com.sun.faces.application.view;

import com.sun.faces.context.StateContext;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.util.ComponentStruct;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.Util;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.application.ProjectStage;
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

public class FaceletPartialStateManagementStrategy extends StateManagementStrategy {
   private static final Logger LOGGER;
   private static final String SKIP_ITERATION_HINT = "javax.faces.visit.SKIP_ITERATION";

   public FaceletPartialStateManagementStrategy() {
      this(FacesContext.getCurrentInstance());
   }

   public FaceletPartialStateManagementStrategy(FacesContext context) {
   }

   private UIComponent locateComponentByClientId(FacesContext context, UIComponent subTree, final String clientId) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "FaceletPartialStateManagementStrategy.locateComponentByClientId", clientId);
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

   private void pruneAndReAddToDynamicActions(List dynamicActionList, ComponentStruct struct) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.finest("FaceletPartialStateManagementStrategy.pruneAndReAddToDynamicActions");
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

   private void restoreDynamicActions(FacesContext context, StateContext stateContext, Map stateMap) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.finest("FaceletPartialStateManagementStrategy.restoreDynamicActions");
      }

      List savedActions = (List)stateMap.get("com.sun.faces.DynamicActions");
      List actions = stateContext.getDynamicActions();
      ComponentStruct action;
      if (!Util.isEmpty((Collection)savedActions)) {
         for(Iterator var6 = savedActions.iterator(); var6.hasNext(); this.pruneAndReAddToDynamicActions(actions, action)) {
            Object savedAction = var6.next();
            action = new ComponentStruct();
            action.restoreState(context, savedAction);
            if ("ADD".equals(action.getAction())) {
               this.restoreDynamicAdd(context, stateMap, action);
            }

            if ("REMOVE".equals(action.getAction())) {
               this.restoreDynamicRemove(context, action);
            }
         }
      }

   }

   private void restoreDynamicAdd(FacesContext context, Map state, ComponentStruct struct) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.finest("FaceletPartialStateManagementStrategy.restoreDynamicAdd");
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
         LOGGER.finest("FaceletPartialStateManagementStrategy.restoreDynamicRemove");
      }

      UIComponent child = this.locateComponentByClientId(context, context.getViewRoot(), struct.getClientId());
      if (child != null) {
         StateContext stateContext = StateContext.getStateContext(context);
         stateContext.getDynamicComponents().put(struct.getClientId(), child);
         UIComponent parent = child.getParent();
         parent.getChildren().remove(child);
      }

   }

   public UIViewRoot restoreView(FacesContext context, String viewId, String renderKitId) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "FaceletPartialStateManagementStrategy.restoreView", new Object[]{viewId, renderKitId});
      }

      ResponseStateManager rsm = RenderKitUtils.getResponseStateManager(context, renderKitId);
      boolean processingEvents = context.isProcessingEvents();
      UIViewRoot viewRoot = context.getViewRoot();
      Object[] rawState = (Object[])((Object[])rsm.getState(context, viewId));
      if (rawState == null) {
         return null;
      } else {
         final Map state = (Map)rawState[1];
         final StateContext stateContext = StateContext.getStateContext(context);
         if (state != null) {
            try {
               stateContext.setTrackViewModifications(false);
               context.getAttributes().put("javax.faces.visit.SKIP_ITERATION", true);
               Set hints = EnumSet.of(VisitHint.SKIP_ITERATION, VisitHint.EXECUTE_LIFECYCLE);
               VisitContext visitContext = VisitContext.createVisitContext(context, (Collection)null, hints);
               viewRoot.visitTree(visitContext, new VisitCallback() {
                  public VisitResult visit(VisitContext context, UIComponent target) {
                     VisitResult result = VisitResult.ACCEPT;
                     String cid = target.getClientId(context.getFacesContext());
                     Object stateObj = state.get(cid);
                     if (stateObj != null && !stateContext.componentAddedDynamically(target)) {
                        boolean restoreStateNow = true;
                        if (stateObj instanceof StateHolderSaver) {
                           restoreStateNow = !((StateHolderSaver)stateObj).componentAddedDynamically();
                        }

                        if (restoreStateNow) {
                           try {
                              target.restoreState(context.getFacesContext(), stateObj);
                           } catch (Exception var9) {
                              String msg = MessageUtils.getExceptionMessageString("com.sun.faces.partial.statesaving.ERROR_RESTORING_STATE_FOR_COMPONENT", cid, var9.toString());
                              throw new FacesException(msg, var9);
                           }
                        }
                     }

                     return result;
                  }
               });
               this.restoreDynamicActions(context, stateContext, state);
            } finally {
               stateContext.setTrackViewModifications(true);
               context.getAttributes().remove("javax.faces.visit.SKIP_ITERATION");
            }
         } else {
            viewRoot = null;
         }

         context.setProcessingEvents(processingEvents);
         return viewRoot;
      }
   }

   private void saveDynamicActions(FacesContext context, StateContext stateContext, Map stateMap) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.finest("FaceletPartialStateManagementStrategy.saveDynamicActions");
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

         stateMap.put("com.sun.faces.DynamicActions", savedActions);
      }

   }

   public Object saveView(FacesContext context) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.finest("FaceletPartialStateManagementStrategy.saveView");
      }

      if (context == null) {
         return null;
      } else {
         UIViewRoot viewRoot = context.getViewRoot();
         if (viewRoot.isTransient()) {
            return null;
         } else {
            Util.checkIdUniqueness(context, viewRoot, new HashSet(viewRoot.getChildCount() << 1));
            final Map stateMap = new HashMap();
            final StateContext stateContext = StateContext.getStateContext(context);
            context.getAttributes().put("javax.faces.visit.SKIP_ITERATION", true);
            Set hints = EnumSet.of(VisitHint.SKIP_ITERATION);
            VisitContext visitContext = VisitContext.createVisitContext(context, (Collection)null, hints);
            final FacesContext finalContext = context;

            try {
               viewRoot.visitTree(visitContext, new VisitCallback() {
                  public VisitResult visit(VisitContext context, UIComponent target) {
                     VisitResult result = VisitResult.ACCEPT;
                     if (!target.isTransient()) {
                        Object stateObj;
                        if (stateContext.componentAddedDynamically(target)) {
                           target.getAttributes().put("com.sun.faces.DynamicComponent", target.getParent().getChildren().indexOf(target));
                           stateObj = new StateHolderSaver(finalContext, target);
                        } else {
                           stateObj = target.saveState(context.getFacesContext());
                        }

                        if (stateObj != null) {
                           stateMap.put(target.getClientId(context.getFacesContext()), stateObj);
                        }

                        return result;
                     } else {
                        return VisitResult.REJECT;
                     }
                  }
               });
            } finally {
               context.getAttributes().remove("javax.faces.visit.SKIP_ITERATION");
            }

            this.saveDynamicActions(context, stateContext, stateMap);
            StateContext.release(context);
            return new Object[]{null, stateMap};
         }
      }
   }

   static {
      LOGGER = FacesLogger.APPLICATION_VIEW.getLogger();
   }
}
