package com.sun.faces.flow;

import com.sun.faces.application.NavigationHandlerImpl;
import com.sun.faces.util.Util;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.flow.Flow;
import javax.faces.flow.FlowCallNode;
import javax.faces.flow.FlowHandler;
import javax.faces.flow.Parameter;

public class FlowHandlerImpl extends FlowHandler {
   public static final String ABANDONED_FLOW = "javax.faces.flow.AbandonedFlow";
   private boolean flowFeatureIsEnabled = false;
   private final Map flows = new ConcurrentHashMap();
   private final Map flowsByFlowId = new ConcurrentHashMap();
   public static final String FLOW_RETURN_DEPTH_PARAM_NAME = "jffrd";

   public Map getCurrentFlowScope() {
      return FlowCDIContext.getCurrentFlowScopeAndUpdateSession();
   }

   public Flow getFlow(FacesContext context, String definingDocumentId, String id) {
      Util.notNull("context", context);
      Util.notNull("definingDocumentId", definingDocumentId);
      Util.notNull("id", id);
      Flow result = null;
      Map mapsForDefiningDocument = (Map)this.flows.get(definingDocumentId);
      if (null != mapsForDefiningDocument) {
         result = (Flow)mapsForDefiningDocument.get(id);
      }

      return result;
   }

   public void addFlow(FacesContext context, Flow toAdd) {
      Util.notNull("context", context);
      Util.notNull("toAdd", toAdd);
      String id = toAdd.getId();
      if (null != id && 0 != id.length()) {
         String definingDocumentId = toAdd.getDefiningDocumentId();
         if (null == definingDocumentId) {
            throw new IllegalArgumentException("The definingDocumentId of the flow may not be null.");
         } else {
            Map mapsForDefiningDocument = (Map)this.flows.get(definingDocumentId);
            if (null == mapsForDefiningDocument) {
               mapsForDefiningDocument = new ConcurrentHashMap();
               this.flows.put(toAdd.getDefiningDocumentId(), mapsForDefiningDocument);
            }

            Flow oldFlow = (Flow)((Map)mapsForDefiningDocument).put(id, toAdd);
            if (null != oldFlow) {
               String message = MessageFormat.format("Flow with id \"{0}\" and definingDocumentId \"{1}\" already exists.", id, definingDocumentId);
               throw new IllegalStateException(message);
            } else {
               List flowsWithId = (List)this.flowsByFlowId.get(id);
               if (null == flowsWithId) {
                  flowsWithId = new CopyOnWriteArrayList();
                  this.flowsByFlowId.put(id, flowsWithId);
               }

               ((List)flowsWithId).add(toAdd);
               NavigationHandler navigationHandler = context.getApplication().getNavigationHandler();
               if (navigationHandler instanceof ConfigurableNavigationHandler) {
                  ((ConfigurableNavigationHandler)navigationHandler).inspectFlow(context, toAdd);
               }

               this.flowFeatureIsEnabled = true;
            }
         }
      } else {
         throw new IllegalArgumentException("The id of the flow may not be null or zero-length.");
      }
   }

   public boolean isActive(FacesContext context, String definingDocumentId, String id) {
      Util.notNull("context", context);
      Util.notNull("definingDocumentId", definingDocumentId);
      Util.notNull("id", id);
      boolean result = false;
      FlowDeque flowStack = getFlowStack(context);
      Iterator var6 = flowStack.iterator();

      while(var6.hasNext()) {
         Flow cur = (Flow)var6.next();
         if (id.equals(cur.getId()) && definingDocumentId.equals(cur.getDefiningDocumentId())) {
            result = true;
            break;
         }
      }

      return result;
   }

   public Flow getCurrentFlow(FacesContext context) {
      Util.notNull("context", context);
      if (!this.flowFeatureIsEnabled) {
         return null;
      } else {
         Flow result = null;
         if (null == context.getExternalContext().getSession(false)) {
            return null;
         } else {
            FlowDeque flowStack = getFlowStack(context);
            int returnDepth = flowStack.getReturnDepth();
            if (flowStack.size() <= returnDepth) {
               return null;
            } else {
               if (0 < returnDepth) {
                  Iterator stackIter = flowStack.iterator();
                  int i = 0;
                  stackIter.next();
                  if (stackIter.hasNext()) {
                     do {
                        result = (Flow)stackIter.next();
                        ++i;
                     } while(i < returnDepth);
                  }
               } else {
                  result = (Flow)getFlowStack(context).peekFirst();
               }

               return result;
            }
         }
      }
   }

   public String getLastDisplayedViewId(FacesContext context) {
      Util.notNull("context", context);
      String result = null;
      FlowDeque flowStack = getFlowStack(context);
      result = flowStack.peekLastDisplayedViewId();
      return result;
   }

   public int getAndClearReturnModeDepth(FacesContext context) {
      int result = false;
      FlowDeque flowStack = getFlowStack(context);
      int result = flowStack.getAndClearMaxReturnDepth(context);
      return result;
   }

   public void pushReturnMode(FacesContext context) {
      Util.notNull("context", context);
      FlowDeque flowStack = getFlowStack(context);
      flowStack.pushReturnMode();
   }

   public void popReturnMode(FacesContext context) {
      Util.notNull("context", context);
      FlowDeque flowStack = getFlowStack(context);
      flowStack.popReturnMode();
   }

   public void transition(FacesContext context, Flow sourceFlow, Flow targetFlow, FlowCallNode outboundCallNode, String toViewId) {
      Util.notNull("context", context);
      Util.notNull("toViewId", toViewId);
      if (this.flowFeatureIsEnabled) {
         if (!this.flowsEqual(sourceFlow, targetFlow)) {
            Map evaluatedParams = null;
            if (null != outboundCallNode) {
               Map outboundParameters = outboundCallNode.getOutboundParameters();
               Map inboundParameters = targetFlow.getInboundParameters();
               if (null != outboundParameters && !outboundParameters.isEmpty() && null != inboundParameters && !inboundParameters.isEmpty()) {
                  ELContext elContext = context.getELContext();
                  Iterator var11 = outboundParameters.entrySet().iterator();

                  while(var11.hasNext()) {
                     Map.Entry curOutbound = (Map.Entry)var11.next();
                     String curName = (String)curOutbound.getKey();
                     if (inboundParameters.containsKey(curName)) {
                        if (null == evaluatedParams) {
                           evaluatedParams = new HashMap();
                        }

                        evaluatedParams.put(curName, ((Parameter)curOutbound.getValue()).getValue().getValue(elContext));
                     }
                  }
               }
            }

            this.performPops(context, sourceFlow, targetFlow);
            if (null != targetFlow && !targetFlow.equals(FlowImpl.ABANDONED_FLOW)) {
               this.pushFlow(context, targetFlow, toViewId, evaluatedParams);
            } else {
               this.assignInboundParameters(context, targetFlow, evaluatedParams);
            }
         }

      }
   }

   private void assignInboundParameters(FacesContext context, Flow calledFlow, Map evaluatedParams) {
      if (null != evaluatedParams) {
         Map inboundParameters = calledFlow.getInboundParameters();
         ELContext elContext = context.getELContext();
         Iterator var8 = evaluatedParams.entrySet().iterator();

         while(var8.hasNext()) {
            Map.Entry curOutbound = (Map.Entry)var8.next();
            String curName = (String)curOutbound.getKey();

            assert inboundParameters.containsKey(curName);

            ValueExpression toSet = ((Parameter)inboundParameters.get(curName)).getValue();
            toSet.setValue(elContext, curOutbound.getValue());
         }
      }

   }

   public void clientWindowTransition(FacesContext context) {
      Map requestParamMap = context.getExternalContext().getRequestParameterMap();
      String toFlowDocumentId = (String)requestParamMap.get("jftfdi");
      String flowId = (String)requestParamMap.get("jffi");
      if (null != toFlowDocumentId) {
         FlowHandler fh = context.getApplication().getFlowHandler();
         Flow sourceFlow = fh.getCurrentFlow(context);
         Flow targetFlow = null;
         FlowCallNode flowCallNode = null;
         if (null != flowId && !"javax.faces.flow.NullFlow".equals(toFlowDocumentId)) {
            targetFlow = fh.getFlow(context, toFlowDocumentId, flowId);
            if (null != targetFlow && null != sourceFlow) {
               flowCallNode = sourceFlow.getFlowCall(targetFlow);
            }
         } else {
            String maxReturnDepthStr = (String)requestParamMap.get("jffrd");
            int maxReturnDepth = Integer.parseInt(maxReturnDepthStr);
            FlowDeque flowStack = getFlowStack(context);
            flowStack.setMaxReturnDepth(context, maxReturnDepth);
         }

         fh.transition(context, sourceFlow, targetFlow, flowCallNode, context.getViewRoot().getViewId());
      }

   }

   private void performPops(FacesContext context, Flow sourceFlow, Flow targetFlow) {
      if (null == sourceFlow) {
         assert null == this.peekFlow(context);

      } else {
         FlowDeque flowStack;
         int depth;
         int i;
         if (null == targetFlow) {
            flowStack = getFlowStack(context);
            depth = flowStack.getAndClearMaxReturnDepth(context);

            for(i = 0; i < depth; ++i) {
               this.popFlow(context);
            }

         } else if (!FlowImpl.ABANDONED_FLOW.equals(targetFlow)) {
            if (null == sourceFlow.getFlowCall(targetFlow)) {
               this.popFlow(context);
            }

         } else {
            flowStack = getFlowStack(context);
            depth = flowStack.size();

            for(i = 0; i < depth; ++i) {
               this.popFlow(context);
            }

         }
      }
   }

   private boolean flowsEqual(Flow flow1, Flow flow2) {
      boolean result = false;
      if (flow1 == flow2) {
         result = true;
      } else if (null != flow1 && null != flow2) {
         result = flow1.equals(flow2);
      } else {
         result = false;
      }

      return result;
   }

   private void pushFlow(FacesContext context, Flow toPush, String lastDisplayedViewId, Map evaluatedParams) {
      FlowDeque flowStack = getFlowStack(context);
      flowStack.addFirst(toPush, lastDisplayedViewId);
      FlowCDIContext.flowEntered();
      this.assignInboundParameters(context, toPush, evaluatedParams);
      MethodExpression me = toPush.getInitializer();
      if (null != me) {
         me.invoke(context.getELContext(), (Object[])null);
      }

      this.forceSessionUpdateForFlowStack(context, flowStack);
   }

   private Flow peekFlow(FacesContext context) {
      FlowDeque flowStack = getFlowStack(context);
      return (Flow)flowStack.peekFirst();
   }

   private Flow popFlow(FacesContext context) {
      FlowDeque flowStack = getFlowStack(context);
      Flow currentFlow = this.peekFlow(context);
      if (null != currentFlow) {
         this.callFinalizer(context, currentFlow, flowStack.size());
      }

      Flow result = (Flow)flowStack.pollFirst();
      this.forceSessionUpdateForFlowStack(context, flowStack);
      return result;
   }

   private void callFinalizer(FacesContext context, Flow currentFlow, int depth) {
      MethodExpression me = currentFlow.getFinalizer();
      if (null != me) {
         me.invoke(context.getELContext(), (Object[])null);
      }

      FlowCDIContext.flowExited(currentFlow, depth);
   }

   static FlowDeque getFlowStack(FacesContext context) {
      FlowDeque result = null;
      ExternalContext extContext = context.getExternalContext();
      String sessionKey = extContext.getClientWindow().getId() + "_flowStack";
      Map sessionMap = extContext.getSessionMap();
      result = (FlowDeque)sessionMap.get(sessionKey);
      if (null == result) {
         result = new FlowDeque(sessionKey);
         sessionMap.put(sessionKey, result);
      }

      return result;
   }

   private void forceSessionUpdateForFlowStack(FacesContext context, FlowDeque stack) {
      ExternalContext extContext = context.getExternalContext();
      Map sessionMap = extContext.getSessionMap();
      sessionMap.put(stack.getSessionKey(), stack);
   }

   static class FlowDeque implements Iterable, Serializable {
      private static final long serialVersionUID = 7915803727932706270L;
      private int returnDepth;
      private ArrayDeque data = new ArrayDeque();
      private ArrayDeque rideAlong = new ArrayDeque();
      private final String sessionKey;

      public FlowDeque(String sessionKey) {
         this.sessionKey = sessionKey;
      }

      public String getSessionKey() {
         return this.sessionKey;
      }

      public int size() {
         return this.data.size();
      }

      public Iterator iterator() {
         return this.data.iterator();
      }

      public void addFirst(Object e, String lastDisplayedViewId) {
         this.rideAlong.addFirst(new RideAlong(lastDisplayedViewId));
         this.data.addFirst(e);
      }

      public Object pollFirst() {
         this.rideAlong.pollFirst();
         return this.data.pollFirst();
      }

      public int getCurrentFlowDepth() {
         int returnDepth = this.returnDepth;
         if (this.data.size() <= returnDepth) {
            return 0;
         } else {
            int result = this.data.size();
            if (0 < returnDepth) {
               Iterator stackIter = this.data.iterator();

               for(int i = 0; stackIter.hasNext() && i < returnDepth; ++i) {
                  stackIter.next();
                  --result;
               }
            }

            return result;
         }
      }

      public Object peekFirst() {
         return this.data.peekFirst();
      }

      public String peekLastDisplayedViewId() {
         String result = null;
         RideAlong helper = null;
         int myReturnDepth = this.getReturnDepth();
         if (0 < myReturnDepth) {
            Iterator stackIter = this.rideAlong.iterator();
            stackIter.next();
            int i = 0;
            if (stackIter.hasNext()) {
               do {
                  helper = (RideAlong)stackIter.next();
                  ++i;
               } while(i < myReturnDepth);
            }
         } else {
            helper = (RideAlong)this.rideAlong.peekFirst();
         }

         if (null != helper) {
            result = helper.lastDisplayedViewId;
         }

         return result;
      }

      public int getReturnDepth() {
         return this.returnDepth;
      }

      private void setMaxReturnDepth(FacesContext context, int value) {
         Map attrs = context.getAttributes();
         attrs.put("jffrd", value);
      }

      private int getAndClearMaxReturnDepth(FacesContext context) {
         Map attrs = context.getAttributes();
         int result = 0;
         if (attrs.containsKey("jffrd")) {
            result = (Integer)attrs.remove("jffrd");
         }

         return result;
      }

      private void incrementMaxReturnDepth() {
         FacesContext context = FacesContext.getCurrentInstance();
         Map attrs = context.getAttributes();
         if (!attrs.containsKey("jffrd")) {
            attrs.put("jffrd", 1);
         } else {
            Integer cur = (Integer)attrs.get("jffrd");
            attrs.put("jffrd", cur + 1);
         }

      }

      private void decrementMaxReturnDepth() {
         FacesContext context = FacesContext.getCurrentInstance();
         Map attrs = context.getAttributes();
         if (attrs.containsKey("jffrd")) {
            Integer cur = (Integer)attrs.get("jffrd");
            if (cur > 1) {
               attrs.put("jffrd", cur - 1);
            } else {
               attrs.remove("jffrd");
            }
         }

      }

      public void pushReturnMode() {
         this.incrementMaxReturnDepth();
         ++this.returnDepth;
      }

      public void popReturnMode() {
         if (NavigationHandlerImpl.isResetFlowHandlerState(FacesContext.getCurrentInstance())) {
            this.decrementMaxReturnDepth();
         }

         --this.returnDepth;
      }

      private static class RideAlong implements Serializable {
         private static final long serialVersionUID = -1899365746835118058L;
         String lastDisplayedViewId;

         public RideAlong(String lastDisplayedViewId) {
            this.lastDisplayedViewId = lastDisplayedViewId;
         }
      }
   }
}
