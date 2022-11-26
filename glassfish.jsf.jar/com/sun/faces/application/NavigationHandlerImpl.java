package com.sun.faces.application;

import com.sun.faces.config.InitFacesContext;
import com.sun.faces.flow.FlowHandlerImpl;
import com.sun.faces.flow.FlowImpl;
import com.sun.faces.flow.builder.MutableNavigationCase;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationCase;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewAction;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.context.PartialViewContext;
import javax.faces.flow.Flow;
import javax.faces.flow.FlowCallNode;
import javax.faces.flow.FlowHandler;
import javax.faces.flow.FlowNode;
import javax.faces.flow.MethodCallNode;
import javax.faces.flow.Parameter;
import javax.faces.flow.ReturnNode;
import javax.faces.flow.SwitchCase;
import javax.faces.flow.SwitchNode;
import javax.faces.flow.ViewNode;

public class NavigationHandlerImpl extends ConfigurableNavigationHandler {
   private static final String RESET_FLOW_HANDLER_STATE_KEY = NavigationHandlerImpl.class.getName() + "_RESET_FLOW_HANDLER_STATE_KEY";
   private static final Logger LOGGER;
   private volatile Map navigationMaps;
   private boolean development;
   private static final Pattern REDIRECT_EQUALS_TRUE;
   private static final Pattern INCLUDE_VIEW_PARAMS_EQUALS_TRUE;
   private static final String ROOT_NAVIGATION_MAP_ID;
   private static final String DID_TRANSITION_FLAG = "com.sun.faces.NavigationHandlerDidTransition";

   public static boolean isResetFlowHandlerState(FacesContext facesContext) {
      Boolean obtainingNavigationCase = (Boolean)FacesContext.getCurrentInstance().getAttributes().get(RESET_FLOW_HANDLER_STATE_KEY);
      return obtainingNavigationCase != null && obtainingNavigationCase;
   }

   public static void setResetFlowHandlerStateIfUnset(FacesContext facesContext, boolean resetFlowHandlerState) {
      Map attributes = facesContext.getAttributes();
      if (!attributes.containsKey(RESET_FLOW_HANDLER_STATE_KEY)) {
         attributes.put(RESET_FLOW_HANDLER_STATE_KEY, resetFlowHandlerState);
      }

   }

   public static void unsetResetFlowHandlerState(FacesContext facesContext) {
      facesContext.getAttributes().remove(RESET_FLOW_HANDLER_STATE_KEY);
   }

   public NavigationHandlerImpl() {
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, "Created NavigationHandler instance ");
      }

      ApplicationAssociate associate = ApplicationAssociate.getInstance(FacesContext.getCurrentInstance().getExternalContext());
      if (associate != null) {
         this.development = associate.isDevModeEnabled();
      }

   }

   public NavigationCase getNavigationCase(FacesContext context, String fromAction, String outcome) {
      return this.getNavigationCase(context, fromAction, outcome, "");
   }

   public NavigationCase getNavigationCase(FacesContext context, String fromAction, String outcome, String toFlowDocumentId) {
      Util.notNull("context", context);
      Util.notNull("toFlowDocumentId", toFlowDocumentId);
      setResetFlowHandlerStateIfUnset(context, true);

      NavigationCase var6;
      try {
         CaseStruct caseStruct = this.getViewId(context, fromAction, outcome, toFlowDocumentId);
         if (null != caseStruct) {
            var6 = caseStruct.navCase;
            return var6;
         }

         var6 = null;
      } finally {
         unsetResetFlowHandlerState(context);
      }

      return var6;
   }

   public Map getNavigationCases() {
      FacesContext context = FacesContext.getCurrentInstance();
      setResetFlowHandlerStateIfUnset(context, true);

      Map var3;
      try {
         Map result = this.getNavigationMap(context);
         var3 = result;
      } finally {
         unsetResetFlowHandlerState(context);
      }

      return var3;
   }

   public void inspectFlow(FacesContext context, Flow flow) {
      this.initializeNavigationFromFlow(context, flow);
   }

   public void handleNavigation(FacesContext context, String fromAction, String outcome) {
      this.handleNavigation(context, fromAction, outcome, "");
   }

   private boolean flowsEqual(Flow flow1, Flow flow2) {
      boolean result = false;
      if (flow1 == flow2) {
         result = true;
      } else if (flow1 != null && flow2 != null) {
         result = flow1.equals(flow2);
      } else {
         result = false;
      }

      return result;
   }

   public void handleNavigation(FacesContext context, String fromAction, String outcome, String toFlowDocumentId) {
      Util.notNull("context", context);
      CaseStruct caseStruct = this.getViewId(context, fromAction, outcome, toFlowDocumentId);
      if (caseStruct != null) {
         ExternalContext extContext = context.getExternalContext();
         ViewHandler viewHandler = Util.getViewHandler(context);

         assert null != viewHandler;

         Flash flash = extContext.getFlash();
         boolean isUIViewActionBroadcastAndViewdsDiffer = false;
         String redirectUrl;
         if (UIViewAction.isProcessingBroadcast(context)) {
            flash.setKeepMessages(true);
            String viewIdBefore = context.getViewRoot().getViewId();
            viewIdBefore = null == viewIdBefore ? "" : viewIdBefore;
            redirectUrl = caseStruct.navCase.getToViewId(context);
            redirectUrl = null == redirectUrl ? "" : redirectUrl;
            isUIViewActionBroadcastAndViewdsDiffer = !viewIdBefore.equals(redirectUrl);
         }

         FlowHandler flowHandler;
         if (!caseStruct.navCase.isRedirect() && !isUIViewActionBroadcastAndViewdsDiffer) {
            UIViewRoot newRoot = viewHandler.createView(context, caseStruct.viewId);
            this.updateRenderTargets(context, caseStruct.viewId);
            context.setViewRoot(newRoot);
            flowHandler = context.getApplication().getFlowHandler();
            if (flowHandler != null && !this.isDidTransition(context)) {
               flowHandler.transition(context, caseStruct.currentFlow, caseStruct.newFlow, caseStruct.facesFlowCallNode, caseStruct.viewId);
               this.setDidTransition(context, false);
            }

            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "Set new view in FacesContext for {0}", caseStruct.viewId);
            }
         } else {
            Map parameters = caseStruct.navCase.getParameters();
            if ((caseStruct.newFlow != null || caseStruct.currentFlow != null) && !this.flowsEqual(caseStruct.newFlow, caseStruct.currentFlow)) {
               if (parameters == null) {
                  parameters = new HashMap();
               }

               if (caseStruct.newFlow == null) {
                  ((Map)parameters).put("jftfdi", Arrays.asList("javax.faces.flow.NullFlow"));
                  ((Map)parameters).put("jffi", Arrays.asList(""));
                  flowHandler = context.getApplication().getFlowHandler();
                  if (flowHandler instanceof FlowHandlerImpl) {
                     FlowHandlerImpl flowHandlerImpl = (FlowHandlerImpl)flowHandler;
                     List flowReturnDepthValues = new ArrayList();
                     flowReturnDepthValues.add(Integer.toString(flowHandlerImpl.getAndClearReturnModeDepth(context)));
                     ((Map)parameters).put("jffrd", flowReturnDepthValues);
                  }
               } else if (!((Map)parameters).containsKey("jftfdi") || !((Map)parameters).containsKey("jffi")) {
                  List toFlowDocumentIdParam = Arrays.asList(caseStruct.navCase.getToFlowDocumentId());
                  ((Map)parameters).put("jftfdi", toFlowDocumentIdParam);
                  List flowIdParam = Arrays.asList(caseStruct.newFlow.getId());
                  ((Map)parameters).put("jffi", flowIdParam);
               }
            }

            redirectUrl = viewHandler.getRedirectURL(context, caseStruct.viewId, SharedUtils.evaluateExpressions(context, (Map)parameters), caseStruct.navCase.isIncludeViewParams());

            try {
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, "Redirecting to path {0} for outcome {1}and viewId {2}", new Object[]{redirectUrl, outcome, caseStruct.viewId});
               }

               this.updateRenderTargets(context, caseStruct.viewId);
               flash.setRedirect(true);
               extContext.redirect(redirectUrl);
            } catch (IOException var14) {
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, "jsf.redirect_failed_error", redirectUrl);
               }

               throw new FacesException(var14.getMessage(), var14);
            }

            context.responseComplete();
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "Response complete for {0}", caseStruct.viewId);
            }
         }

         this.clearViewMapIfNecessary(context, caseStruct.viewId);
      }

   }

   private Map getRootNaviMap() {
      Map result = null;
      NavigationInfo info = null;
      if (null == this.navigationMaps) {
         this.createNavigationMaps();
         result = ((NavigationInfo)this.navigationMaps.get(ROOT_NAVIGATION_MAP_ID)).ruleSet;
      } else {
         info = (NavigationInfo)this.navigationMaps.get(ROOT_NAVIGATION_MAP_ID);
         if (null == info.ruleSet) {
            result = Collections.emptyMap();
         } else {
            result = info.ruleSet;
         }
      }

      return (Map)result;
   }

   private Map getNavigationMap(FacesContext context) {
      Map result = null;
      NavigationInfo info = null;
      if (null == this.navigationMaps) {
         this.createNavigationMaps();
         result = ((NavigationInfo)this.navigationMaps.get(ROOT_NAVIGATION_MAP_ID)).ruleSet;
      } else {
         FlowHandler fh = context.getApplication().getFlowHandler();
         if (null != fh) {
            Flow currentFlow = fh.getCurrentFlow(context);
            if (null != currentFlow) {
               info = (NavigationInfo)this.navigationMaps.get(currentFlow.getDefiningDocumentId() + currentFlow.getId());
               if (null == info) {
                  return Collections.emptyMap();
               }
            }
         }

         if (null == info) {
            info = (NavigationInfo)this.navigationMaps.get(ROOT_NAVIGATION_MAP_ID);
         }

         if (null == info.ruleSet) {
            result = Collections.emptyMap();
         } else {
            result = info.ruleSet;
         }
      }

      return (Map)result;
   }

   private void createNavigationMaps() {
      if (null == this.navigationMaps) {
         Map maps = new ConcurrentHashMap();
         NavigationMap result = new NavigationMap();
         NavigationInfo info = new NavigationInfo();
         info.ruleSet = result;
         maps.put(ROOT_NAVIGATION_MAP_ID, info);
         this.navigationMaps = maps;
      }

   }

   private Map getRootNavigationMap() {
      this.createNavigationMaps();
      return ((NavigationInfo)this.navigationMaps.get(ROOT_NAVIGATION_MAP_ID)).ruleSet;
   }

   private Set getWildCardMatchList(FacesContext context) {
      Set result = Collections.emptySet();
      NavigationInfo info = null;
      FlowHandler fh = context.getApplication().getFlowHandler();
      if (null != fh) {
         Flow currentFlow = fh.getCurrentFlow(context);
         if (null != currentFlow) {
            info = (NavigationInfo)this.navigationMaps.get(currentFlow.getDefiningDocumentId() + currentFlow.getId());
         }
      }

      if (null == info) {
         info = (NavigationInfo)this.navigationMaps.get(ROOT_NAVIGATION_MAP_ID);
      }

      if (null != info.ruleSet && null != info.ruleSet.wildcardMatchList) {
         result = info.ruleSet.wildcardMatchList;
      }

      return (Set)result;
   }

   private NavigationInfo getNavigationInfo(FacesContext context, String toFlowDocumentId, String flowId) {
      NavigationInfo result = null;

      assert null != this.navigationMaps;

      result = (NavigationInfo)this.navigationMaps.get(toFlowDocumentId + flowId);
      if (null == result) {
         FlowHandler fh = context.getApplication().getFlowHandler();
         if (null != fh) {
            Flow currentFlow = fh.getCurrentFlow(context);
            if (null != currentFlow) {
               result = (NavigationInfo)this.navigationMaps.get(currentFlow.getDefiningDocumentId() + currentFlow.getId());
            }
         }
      }

      return result;
   }

   private void initializeNavigationFromAssociate() {
      ApplicationAssociate associate = ApplicationAssociate.getCurrentInstance();
      if (associate != null) {
         Map m = associate.getNavigationCaseListMappings();
         Map rootMap = this.getRootNavigationMap();
         if (m != null) {
            rootMap.putAll(m);
         }
      }

   }

   private void initializeNavigationFromFlow(FacesContext context, Flow toInspect) {
      if (context instanceof InitFacesContext) {
         this.createNavigationMaps();
         this.initializeNavigationFromFlowNonThreadSafe(toInspect);
      } else {
         assert null != this.navigationMaps;

         this.initializeNavigationFromFlowThreadSafe(toInspect);
      }

   }

   private void initializeNavigationFromFlowNonThreadSafe(Flow toInspect) {
      String fullyQualifiedFlowId = toInspect.getDefiningDocumentId() + toInspect.getId();
      if (this.navigationMaps.containsKey(fullyQualifiedFlowId)) {
         if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(Level.INFO, "PENDING(edburns): merge existing map");
         }
      } else {
         Map navRules = toInspect.getNavigationCases();
         Map switches = toInspect.getSwitches();
         if (!navRules.isEmpty() || !switches.isEmpty()) {
            NavigationInfo info = new NavigationInfo();
            if (!switches.isEmpty()) {
               info.switches = new ConcurrentHashMap();
               Iterator var6 = switches.entrySet().iterator();

               while(var6.hasNext()) {
                  Map.Entry cur = (Map.Entry)var6.next();
                  info.switches.put(cur.getKey(), cur.getValue());
               }
            }

            if (!navRules.isEmpty()) {
               info.ruleSet = new NavigationMap();
               info.ruleSet.putAll(navRules);
            }

            this.navigationMaps.put(fullyQualifiedFlowId, info);
         }
      }

   }

   private void initializeNavigationFromFlowThreadSafe(Flow toInspect) {
      synchronized(this) {
         this.initializeNavigationFromFlowNonThreadSafe(toInspect);
      }
   }

   private void clearViewMapIfNecessary(FacesContext facesContext, String newId) {
      UIViewRoot root = facesContext.getViewRoot();
      if (root != null && !root.getViewId().equals(newId)) {
         Map viewMap = root.getViewMap(false);
         if (viewMap != null) {
            viewMap.clear();
         }
      }

   }

   private void updateRenderTargets(FacesContext ctx, String newId) {
      if (ctx.getViewRoot() == null || !ctx.getViewRoot().getViewId().equals(newId)) {
         PartialViewContext pctx = ctx.getPartialViewContext();
         if (!pctx.isRenderAll()) {
            pctx.setRenderAll(true);
         }
      }

   }

   private CaseStruct getViewId(FacesContext ctx, String fromAction, String outcome, String toFlowDocumentId) {
      if (this.navigationMaps == null) {
         synchronized(this) {
            this.initializeNavigationFromAssociate();
         }
      }

      UIViewRoot root = ctx.getViewRoot();
      String viewId = root != null ? root.getViewId() : null;
      CaseStruct caseStruct = null;
      Map navMap = this.getNavigationMap(ctx);
      if (viewId != null) {
         caseStruct = this.findExactMatch(ctx, viewId, fromAction, outcome, toFlowDocumentId, navMap);
         if (caseStruct == null) {
            caseStruct = this.findWildCardMatch(ctx, viewId, fromAction, outcome, toFlowDocumentId, navMap);
         }
      }

      if (caseStruct == null) {
         caseStruct = this.findDefaultMatch(ctx, fromAction, outcome, toFlowDocumentId, navMap);
      }

      FlowHandler fh = ctx.getApplication().getFlowHandler();
      String key;
      FlowNode currentFlow;
      if (null != caseStruct && caseStruct.isFlowEntryFromExplicitRule) {
         toFlowDocumentId = null != caseStruct.navCase.getToFlowDocumentId() ? caseStruct.navCase.getToFlowDocumentId() : toFlowDocumentId;
         caseStruct = this.findFacesFlowCallMatch(ctx, fromAction, this.convertToViewIdToFlowOrNodeId(ctx, caseStruct), toFlowDocumentId);
      } else if (null != caseStruct && fh != null && fh.getCurrentFlow() != null) {
         key = this.convertToViewIdToFlowOrNodeId(ctx, caseStruct);
         currentFlow = fh.getCurrentFlow().getNode(key);
         if (currentFlow != null) {
            caseStruct = null;
            outcome = key;
         }
      }

      if (null == caseStruct && null != fromAction && null != outcome) {
         caseStruct = this.findViewNodeMatch(ctx, fromAction, outcome);
      }

      if (null == caseStruct && null != fromAction && null != outcome) {
         caseStruct = this.findSwitchMatch(ctx, fromAction, outcome, toFlowDocumentId);
      }

      if (null == caseStruct && null != fromAction && null != outcome) {
         caseStruct = this.findMethodCallMatch(ctx, fromAction, outcome, toFlowDocumentId);
      }

      if (null == caseStruct && null != outcome) {
         caseStruct = this.findFacesFlowCallMatch(ctx, fromAction, outcome, toFlowDocumentId);
      }

      if (null == caseStruct && null != outcome) {
         caseStruct = this.findReturnMatch(ctx, fromAction, outcome);
      }

      if (caseStruct == null && outcome != null && viewId != null) {
         if (0 == outcome.length()) {
            outcome = null;
         } else {
            caseStruct = this.findImplicitMatch(ctx, viewId, fromAction, outcome, toFlowDocumentId);
         }
      }

      if (caseStruct == null && outcome != null && viewId != null) {
         FlowHandler flowHandler = ctx.getApplication().getFlowHandler();
         if (null != flowHandler) {
            currentFlow = null;
            Flow currentFlow = flowHandler.getCurrentFlow(ctx);
            if (null != currentFlow) {
               caseStruct = this.findRootNavigationMapAbandonedFlowMatch(ctx, viewId, fromAction, outcome, toFlowDocumentId);
            }
         }
      }

      if (caseStruct == null && outcome != null && this.development) {
         Object[] params;
         if (fromAction == null) {
            key = "com.sun.faces.NAVIGATION_NO_MATCHING_OUTCOME";
            params = new Object[]{viewId, outcome};
         } else {
            key = "com.sun.faces.NAVIGATION_NO_MATCHING_OUTCOME_ACTION";
            params = new Object[]{viewId, fromAction, outcome};
         }

         FacesMessage m = MessageUtils.getExceptionMessage(key, params);
         m.setSeverity(FacesMessage.SEVERITY_WARN);
         ctx.addMessage((String)null, m);
      }

      return caseStruct;
   }

   private CaseStruct findExactMatch(FacesContext ctx, String viewId, String fromAction, String outcome, String toFlowDocumentId, Map navMap) {
      Set caseSet = (Set)navMap.get(viewId);
      if (caseSet == null) {
         return null;
      } else {
         CaseStruct result = this.determineViewFromActionOutcome(ctx, caseSet, fromAction, outcome, toFlowDocumentId);
         if (null != result) {
            FlowHandler flowHandler = ctx.getApplication().getFlowHandler();
            if (null != flowHandler) {
               result.currentFlow = flowHandler.getCurrentFlow(ctx);
               result.newFlow = result.currentFlow;
            }
         }

         return result;
      }
   }

   private CaseStruct findWildCardMatch(FacesContext ctx, String viewId, String fromAction, String outcome, String toFlowDocumentId, Map navMap) {
      CaseStruct result = null;
      StringBuilder sb = new StringBuilder(32);
      Iterator var9 = this.getWildCardMatchList(ctx).iterator();

      while(var9.hasNext()) {
         String fromViewId = (String)var9.next();
         if (viewId.startsWith(fromViewId)) {
            if (sb.length() != 0) {
               sb.delete(0, sb.length());
            }

            String wcFromViewId = sb.append(fromViewId).append('*').toString();
            Set ccaseSet = (Set)navMap.get(wcFromViewId);
            if (ccaseSet == null) {
               return null;
            }

            result = this.determineViewFromActionOutcome(ctx, ccaseSet, fromAction, outcome, toFlowDocumentId);
            if (result != null) {
               break;
            }
         }
      }

      if (null != result) {
         FlowHandler flowHandler = ctx.getApplication().getFlowHandler();
         if (null != flowHandler) {
            result.currentFlow = flowHandler.getCurrentFlow(ctx);
            result.newFlow = result.currentFlow;
         }
      }

      return result;
   }

   private CaseStruct findDefaultMatch(FacesContext ctx, String fromAction, String outcome, String toFlowDocumentId, Map navMap) {
      Set caseSet = (Set)navMap.get("*");
      if (caseSet == null) {
         return null;
      } else {
         CaseStruct result = this.determineViewFromActionOutcome(ctx, caseSet, fromAction, outcome, toFlowDocumentId);
         if (null != result) {
            FlowHandler flowHandler = ctx.getApplication().getFlowHandler();
            if (null != flowHandler) {
               result.currentFlow = flowHandler.getCurrentFlow(ctx);
               result.newFlow = result.currentFlow;
            }
         }

         return result;
      }
   }

   private CaseStruct findRootNavigationMapAbandonedFlowMatch(FacesContext ctx, String viewId, String fromAction, String outcome, String toFlowDocumentId) {
      CaseStruct caseStruct = null;
      Map navMap = this.getRootNaviMap();
      if (viewId != null) {
         caseStruct = this.findExactMatch(ctx, viewId, fromAction, outcome, toFlowDocumentId, navMap);
         if (caseStruct == null) {
            caseStruct = this.findWildCardMatch(ctx, viewId, fromAction, outcome, toFlowDocumentId, navMap);
         }
      }

      if (caseStruct == null) {
         caseStruct = this.findDefaultMatch(ctx, fromAction, outcome, toFlowDocumentId, navMap);
      }

      if (null != caseStruct) {
         caseStruct.newFlow = FlowImpl.ABANDONED_FLOW;
      }

      return caseStruct;
   }

   private CaseStruct findImplicitMatch(FacesContext context, String viewId, String fromAction, String outcome, String flowDefiningDocumentId) {
      String viewIdToTest = outcome;
      String currentViewId = viewId;
      Map parameters = null;
      boolean isRedirect = false;
      boolean isIncludeViewParams = false;
      CaseStruct result = null;
      int questionMark = outcome.indexOf(63);
      if (-1 != questionMark) {
         int viewIdLen = outcome.length();
         String queryString;
         if (viewIdLen <= questionMark + 1) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, "jsf.navigation_invalid_query_string", outcome);
            }

            if (this.development) {
               String key = "com.sun.faces.NAVIGATION_INVALID_QUERY_STRING";
               Object[] params = new Object[]{outcome};
               FacesMessage m = MessageUtils.getExceptionMessage(key, params);
               m.setSeverity(FacesMessage.SEVERITY_WARN);
               context.addMessage((String)null, m);
            }

            queryString = null;
            viewIdToTest = outcome.substring(0, questionMark);
         } else {
            queryString = outcome.substring(questionMark + 1);
            viewIdToTest = outcome.substring(0, questionMark);
            Matcher m = REDIRECT_EQUALS_TRUE.matcher(queryString);
            if (m.find()) {
               isRedirect = true;
               queryString = queryString.replace(m.group(2), "");
            }

            m = INCLUDE_VIEW_PARAMS_EQUALS_TRUE.matcher(queryString);
            if (m.find()) {
               isIncludeViewParams = true;
               queryString = queryString.replace(m.group(2), "");
            }
         }

         if (queryString != null && queryString.length() > 0) {
            Map appMap = context.getExternalContext().getApplicationMap();
            String[] queryElements = Util.split(appMap, queryString, "&amp;|&");
            int i = 0;

            for(int len = queryElements.length; i < len; ++i) {
               String[] elements = Util.split(appMap, queryElements[i], "=");
               if (elements.length == 2) {
                  String rightHandSide = elements[1];
                  String sanitized = null != rightHandSide && 2 < rightHandSide.length() ? rightHandSide.trim() : "";
                  if (sanitized.contains("#{") || sanitized.contains("${")) {
                     if (LOGGER.isLoggable(Level.INFO)) {
                        LOGGER.log(Level.INFO, "jsf.navigation_invalid_query_string", rightHandSide);
                     }

                     rightHandSide = "";
                  }

                  if (parameters == null) {
                     parameters = new LinkedHashMap(len / 2, 1.0F);
                     List values = new ArrayList(2);
                     values.add(rightHandSide);
                     parameters.put(elements[0], values);
                  } else {
                     List values = (List)parameters.get(elements[0]);
                     if (values == null) {
                        values = new ArrayList(2);
                        parameters.put(elements[0], values);
                     }

                     ((List)values).add(rightHandSide);
                  }
               }
            }
         }
      }

      StringBuilder viewIdBuf = new StringBuilder(viewIdToTest);
      int lastSlash;
      if (viewIdToTest.lastIndexOf(46) == -1) {
         lastSlash = viewId.lastIndexOf(46);
         if (lastSlash != -1) {
            viewIdBuf.append(viewId.substring(lastSlash));
         }
      }

      if (viewIdToTest.charAt(0) != '/') {
         lastSlash = viewId.lastIndexOf(47);
         if (lastSlash != -1) {
            currentViewId = viewId.substring(0, lastSlash + 1);
            viewIdBuf.insert(0, currentViewId);
         } else {
            viewIdBuf.insert(0, "/");
         }
      }

      viewIdToTest = viewIdBuf.toString();
      ViewHandler viewHandler = Util.getViewHandler(context);
      FlowHandler flowHandler = context.getApplication().getFlowHandler();
      Flow currentFlow = null;
      Flow newFlow = null;
      if (null != flowHandler) {
         currentFlow = flowHandler.getCurrentFlow(context);
         newFlow = currentFlow;
         if (null != currentFlow && null != viewIdToTest && !viewIdToTest.startsWith("/" + currentFlow.getId())) {
            if ("javax.faces.flow.NullFlow".equals(flowDefiningDocumentId)) {
               newFlow = null;
               viewIdToTest = null;
            } else {
               newFlow = FlowImpl.ABANDONED_FLOW;
            }
         }
      }

      if (null != viewIdToTest) {
         viewIdToTest = viewHandler.deriveViewId(context, viewIdToTest);
      }

      if (null == result && null != viewIdToTest) {
         result = new CaseStruct();
         result.viewId = viewIdToTest;
         if (null == newFlow && null == currentFlow && !"javax.faces.flow.NullFlow".equals(flowDefiningDocumentId)) {
            flowDefiningDocumentId = null;
         }

         result.navCase = new NavigationCase(currentViewId, fromAction, outcome, (String)null, viewIdToTest, flowDefiningDocumentId, parameters, isRedirect, isIncludeViewParams);
      }

      if (null != result) {
         result.currentFlow = currentFlow;
         result.newFlow = newFlow;
      }

      return result;
   }

   private CaseStruct findSwitchMatch(FacesContext context, String fromAction, String outcome, String toFlowDocumentId) {
      FlowHandler flowHandler = context.getApplication().getFlowHandler();
      if (null == flowHandler) {
         return null;
      } else {
         CaseStruct result = null;
         NavigationInfo info = this.getNavigationInfo(context, toFlowDocumentId, fromAction);
         Flow currentFlow = flowHandler.getCurrentFlow(context);
         if (null != info && null != info.switches && !info.switches.isEmpty()) {
            SwitchNode switchNode = (SwitchNode)info.switches.get(outcome);
            if (null != switchNode) {
               List cases = switchNode.getCases();
               boolean matched = false;
               Iterator var12 = cases.iterator();

               while(var12.hasNext()) {
                  SwitchCase cur = (SwitchCase)var12.next();
                  if (cur.getCondition(context)) {
                     outcome = cur.getFromOutcome();
                     matched = true;
                     break;
                  }
               }

               if (!matched || outcome == null) {
                  outcome = switchNode.getDefaultOutcome(context);
               }

               if (null != outcome) {
                  if (null != currentFlow) {
                     FlowNode targetNode = currentFlow.getNode(outcome);
                     if (targetNode instanceof MethodCallNode) {
                        result = this.findMethodCallMatch(context, fromAction, outcome, toFlowDocumentId);
                     } else if (targetNode instanceof SwitchNode) {
                        result = this.findSwitchMatch(context, fromAction, outcome, toFlowDocumentId);
                     } else if (targetNode instanceof FlowCallNode) {
                        result = this.findFacesFlowCallMatch(context, fromAction, outcome, toFlowDocumentId);
                     }
                  }

                  if (null == result) {
                     Flow newFlow = flowHandler.getFlow(context, toFlowDocumentId, fromAction);
                     if (null != newFlow) {
                        result = this.synthesizeCaseStruct(context, newFlow, fromAction, outcome);
                     } else {
                        newFlow = flowHandler.getCurrentFlow(context);
                        if (null != newFlow) {
                           result = this.synthesizeCaseStruct(context, newFlow, fromAction, outcome);
                        }
                     }
                  }
               }
            }
         }

         if (null != result) {
            result.currentFlow = currentFlow;
            if (result.newFlow == FlowImpl.SYNTHESIZED_RETURN_CASE_FLOW) {
               result.newFlow = null;
            } else {
               result.newFlow = currentFlow;
            }
         }

         return result;
      }
   }

   private CaseStruct synthesizeCaseStruct(FacesContext context, Flow flow, String fromAction, String outcome) {
      CaseStruct result = null;
      FlowNode node = flow.getNode(outcome);
      String toViewId;
      if (null != node) {
         if (node instanceof ViewNode) {
            result = new CaseStruct();
            result.viewId = ((ViewNode)node).getVdlDocumentId();
            result.navCase = new MutableNavigationCase(fromAction, fromAction, outcome, (String)null, result.viewId, flow.getDefiningDocumentId(), (Map)null, false, false);
         } else if (node instanceof ReturnNode) {
            String fromOutcome = ((ReturnNode)node).getFromOutcome(context);
            FlowHandler flowHandler = context.getApplication().getFlowHandler();

            try {
               flowHandler.pushReturnMode(context);
               result = this.getViewId(context, fromAction, fromOutcome, "javax.faces.flow.NullFlow");
               if (null == result) {
                  Flow precedingFlow = flowHandler.getCurrentFlow(context);
                  if (null != precedingFlow) {
                     toViewId = flowHandler.getLastDisplayedViewId(context);
                     if (null != toViewId) {
                        result = new CaseStruct();
                        result.viewId = toViewId;
                        result.navCase = new MutableNavigationCase(context.getViewRoot().getViewId(), fromAction, outcome, (String)null, toViewId, "javax.faces.flow.NullFlow", (Map)null, false, false);
                     }
                  }
               } else {
                  flowHandler.transition(context, flow, (Flow)null, (FlowCallNode)null, result.viewId);
                  this.setDidTransition(context, true);
                  result.newFlow = FlowImpl.SYNTHESIZED_RETURN_CASE_FLOW;
               }
            } finally {
               flowHandler.popReturnMode(context);
            }
         }
      } else {
         int idx = outcome.lastIndexOf(46);
         String currentExtension;
         if (idx != -1) {
            currentExtension = outcome.substring(idx);
         } else {
            currentExtension = ".xhtml";
         }

         toViewId = "/" + flow.getId() + "/" + outcome + currentExtension;
         ViewHandler viewHandler = Util.getViewHandler(context);
         toViewId = viewHandler.deriveViewId(context, toViewId);
         if (null != toViewId) {
            result = new CaseStruct();
            result.viewId = toViewId;
            result.navCase = new MutableNavigationCase(fromAction, fromAction, outcome, (String)null, result.viewId, (String)null, false, false);
         }
      }

      return result;
   }

   private CaseStruct findMethodCallMatch(FacesContext context, String fromAction, String outcome, String toFlowDocumentId) {
      FlowHandler flowHandler = context.getApplication().getFlowHandler();
      if (null == flowHandler) {
         return null;
      } else {
         CaseStruct result = null;
         Flow currentFlow = flowHandler.getCurrentFlow(context);
         if (null != currentFlow) {
            FlowNode node = currentFlow.getNode(outcome);
            if (node instanceof MethodCallNode) {
               MethodCallNode methodCallNode = (MethodCallNode)node;
               MethodExpression me = methodCallNode.getMethodExpression();
               if (null != me) {
                  List paramList = methodCallNode.getParameters();
                  Object[] params = null;
                  if (null != paramList) {
                     params = new Object[paramList.size()];
                     int i = 0;
                     ELContext elContext = context.getELContext();

                     Parameter cur;
                     for(Iterator var15 = paramList.iterator(); var15.hasNext(); params[i++] = cur.getValue().getValue(elContext)) {
                        cur = (Parameter)var15.next();
                     }
                  }

                  Object invokeResult = me.invoke(context.getELContext(), params);
                  if (null == invokeResult) {
                     ValueExpression ve = methodCallNode.getOutcome();
                     if (null != ve) {
                        invokeResult = ve.getValue(context.getELContext());
                     }
                  }

                  outcome = invokeResult.toString();
                  FlowNode targetNode = currentFlow.getNode(outcome);
                  if (targetNode instanceof MethodCallNode) {
                     result = this.findMethodCallMatch(context, fromAction, outcome, toFlowDocumentId);
                  } else if (targetNode instanceof SwitchNode) {
                     result = this.findSwitchMatch(context, fromAction, outcome, toFlowDocumentId);
                  } else if (targetNode instanceof FlowCallNode) {
                     result = this.findFacesFlowCallMatch(context, fromAction, outcome, toFlowDocumentId);
                  } else {
                     result = this.synthesizeCaseStruct(context, currentFlow, fromAction, outcome);
                     if (null != result) {
                        result.currentFlow = currentFlow;
                        if (result.newFlow == FlowImpl.SYNTHESIZED_RETURN_CASE_FLOW) {
                           result.newFlow = null;
                        } else {
                           result.newFlow = currentFlow;
                        }
                     }
                  }
               }
            }
         }

         return result;
      }
   }

   private CaseStruct findFacesFlowCallMatch(FacesContext context, String fromAction, String outcome, String toFlowDocumentId) {
      FlowHandler flowHandler = context.getApplication().getFlowHandler();
      if (null == flowHandler) {
         return null;
      } else {
         CaseStruct result = null;
         Flow currentFlow = flowHandler.getCurrentFlow(context);
         Flow newFlow = null;
         FlowCallNode facesFlowCallNode = null;
         if (null != currentFlow) {
            FlowNode node = currentFlow.getNode(outcome);
            if (node instanceof FlowCallNode) {
               facesFlowCallNode = (FlowCallNode)node;
               String flowId = facesFlowCallNode.getCalledFlowId(context);
               String flowDocumentId = facesFlowCallNode.getCalledFlowDocumentId(context);
               if (null != flowId) {
                  newFlow = flowHandler.getFlow(context, flowDocumentId, flowId);
                  if (null != newFlow) {
                     String startNodeId = newFlow.getStartNodeId();
                     result = this.synthesizeCaseStruct(context, newFlow, fromAction, startNodeId);
                     if (null == result) {
                        assert null != currentFlow;

                        try {
                           this.setDidTransition(context, true);
                           flowHandler.transition(context, currentFlow, newFlow, facesFlowCallNode, startNodeId);
                           result = this.getViewId(context, fromAction, startNodeId, toFlowDocumentId);
                        } finally {
                           if (null == result) {
                              flowHandler.transition(context, newFlow, currentFlow, (FlowCallNode)null, outcome);
                              this.setDidTransition(context, false);
                           }

                        }
                     }
                  }
               }
            }
         } else {
            newFlow = flowHandler.getFlow(context, toFlowDocumentId, outcome);
            if (null != newFlow) {
               String startNodeId = newFlow.getStartNodeId();
               result = this.synthesizeCaseStruct(context, newFlow, fromAction, startNodeId);
               if (null == result) {
                  assert null == currentFlow;

                  try {
                     this.setDidTransition(context, true);
                     flowHandler.transition(context, (Flow)null, newFlow, (FlowCallNode)null, startNodeId);
                     result = this.getViewId(context, fromAction, startNodeId, toFlowDocumentId);
                  } finally {
                     if (null == result) {
                        flowHandler.transition(context, newFlow, (Flow)null, (FlowCallNode)null, outcome);
                        this.setDidTransition(context, false);
                     }

                  }
               } else if (!outcome.equals(startNodeId) && null != result.navCase) {
                  ((MutableNavigationCase)result.navCase).setFromOutcome(outcome);
               }
            }
         }

         if (null != result) {
            result.currentFlow = currentFlow;
            result.newFlow = newFlow;
            result.facesFlowCallNode = facesFlowCallNode;
         }

         return result;
      }
   }

   private boolean isDidTransition(FacesContext context) {
      boolean result = context.getAttributes().containsKey("com.sun.faces.NavigationHandlerDidTransition");
      return result;
   }

   private void setDidTransition(FacesContext context, boolean value) {
      Map contextMap = context.getAttributes();
      if (value) {
         contextMap.put("com.sun.faces.NavigationHandlerDidTransition", Boolean.TRUE);
      } else {
         contextMap.remove("com.sun.faces.NavigationHandlerDidTransition");
      }

   }

   private CaseStruct findViewNodeMatch(FacesContext context, String fromAction, String outcome) {
      FlowHandler flowHandler = context.getApplication().getFlowHandler();
      if (null == flowHandler) {
         return null;
      } else {
         CaseStruct result = null;
         Flow currentFlow = flowHandler.getCurrentFlow(context);
         if (null != currentFlow) {
            FlowNode node = currentFlow.getNode(outcome);
            if (null != node && node instanceof ViewNode) {
               result = this.synthesizeCaseStruct(context, currentFlow, fromAction, outcome);
            }
         }

         if (null != result) {
            result.currentFlow = currentFlow;
            result.newFlow = currentFlow;
            result.facesFlowCallNode = null;
         }

         return result;
      }
   }

   private CaseStruct findReturnMatch(FacesContext context, String fromAction, String outcome) {
      FlowHandler flowHandler = context.getApplication().getFlowHandler();
      if (null == flowHandler) {
         return null;
      } else {
         CaseStruct result = null;
         Flow currentFlow = flowHandler.getCurrentFlow(context);
         if (null != currentFlow) {
            ReturnNode returnNode = (ReturnNode)currentFlow.getReturns().get(outcome);
            if (null != returnNode) {
               String fromOutcome = returnNode.getFromOutcome(context);

               try {
                  flowHandler.pushReturnMode(context);
                  result = this.getViewId(context, fromAction, fromOutcome, "javax.faces.flow.NullFlow");
                  if (null == result) {
                     Flow precedingFlow = flowHandler.getCurrentFlow(context);
                     if (null != precedingFlow) {
                        String toViewId = flowHandler.getLastDisplayedViewId(context);
                        if (null != toViewId) {
                           result = new CaseStruct();
                           result.viewId = toViewId;
                           result.navCase = new NavigationCase(context.getViewRoot().getViewId(), fromAction, outcome, (String)null, toViewId, "javax.faces.flow.NullFlow", (Map)null, false, false);
                        }
                     }
                  }
               } finally {
                  flowHandler.popReturnMode(context);
               }
            }
         }

         if (null != result && result.facesFlowCallNode == null) {
            result.currentFlow = currentFlow;
            result.newFlow = null;
         }

         return result;
      }
   }

   private CaseStruct determineViewFromActionOutcome(FacesContext ctx, Set caseSet, String fromAction, String outcome, String toFlowDocumentId) {
      CaseStruct result = new CaseStruct();
      boolean match = false;
      Iterator var8 = caseSet.iterator();

      while(true) {
         NavigationCase cnc;
         boolean cncHasCondition;
         String cncToViewId;
         do {
            if (!var8.hasNext()) {
               return null;
            }

            cnc = (NavigationCase)var8.next();
            String cncFromAction = cnc.getFromAction();
            String cncFromOutcome = cnc.getFromOutcome();
            cncHasCondition = cnc.hasCondition();
            cncToViewId = cnc.getToViewId(ctx);
            if (cncFromAction != null && cncFromAction.equals(fromAction) && cncFromOutcome != null && cncFromOutcome.equals(outcome)) {
               match = true;
            } else if (cncFromAction == null && cncFromOutcome != null && cncFromOutcome.equals(outcome)) {
               match = true;
            } else if (cncFromAction == null || !cncFromAction.equals(fromAction) || cncFromOutcome != null || outcome == null && !cncHasCondition) {
               if (cncFromAction == null && cncFromOutcome == null && (outcome != null || cncHasCondition)) {
                  match = true;
               }
            } else {
               match = true;
            }
         } while(!match);

         result.viewId = cncToViewId;
         result.navCase = cnc;
         if (!cncHasCondition || !Boolean.FALSE.equals(cnc.getCondition(ctx))) {
            toFlowDocumentId = null != cnc.getToFlowDocumentId() ? cnc.getToFlowDocumentId() : toFlowDocumentId;
            if (null != toFlowDocumentId) {
               FlowHandler fh = ctx.getApplication().getFlowHandler();
               if (null != outcome) {
                  result.isFlowEntryFromExplicitRule = null != fh.getFlow(ctx, toFlowDocumentId, this.convertToViewIdToFlowOrNodeId(ctx, result));
               }
            }

            return result;
         }

         match = false;
      }
   }

   private String convertToViewIdToFlowOrNodeId(FacesContext ctx, CaseStruct caseStruct) {
      String viewId = caseStruct.navCase.getToViewId(ctx);
      return viewId.substring(viewId.lastIndexOf(47) + 1);
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
      REDIRECT_EQUALS_TRUE = Pattern.compile("(.*)(faces-redirect=true)(.*)");
      INCLUDE_VIEW_PARAMS_EQUALS_TRUE = Pattern.compile("(.*)(includeViewParams=true)(.*)");
      ROOT_NAVIGATION_MAP_ID = NavigationHandlerImpl.class.getName() + ".NAVIGATION_MAP";
   }

   private static final class NavigationMap extends AbstractMap {
      private HashMap mapToLookForNavCase;
      private TreeSet wildcardMatchList;

      private NavigationMap() {
         this.mapToLookForNavCase = new HashMap();
         this.wildcardMatchList = new TreeSet(new Comparator() {
            public int compare(String fromViewId1, String fromViewId2) {
               return -fromViewId1.compareTo(fromViewId2);
            }
         });
      }

      public int size() {
         return this.mapToLookForNavCase.size();
      }

      public boolean isEmpty() {
         return this.mapToLookForNavCase.isEmpty();
      }

      public Set put(String key, Set value) {
         if (key == null) {
            throw new IllegalArgumentException(key);
         } else if (value == null) {
            throw new IllegalArgumentException();
         } else {
            this.updateWildcards(key);
            Set existing = (Set)this.mapToLookForNavCase.get(key);
            if (existing == null) {
               this.mapToLookForNavCase.put(key, value);
               return null;
            } else {
               existing.addAll(value);
               return existing;
            }
         }
      }

      public void putAll(Map m) {
         if (m != null) {
            Iterator var2 = m.entrySet().iterator();

            while(var2.hasNext()) {
               Map.Entry entry = (Map.Entry)var2.next();
               String key = (String)entry.getKey();
               this.updateWildcards(key);
               Set existing = (Set)this.mapToLookForNavCase.get(key);
               if (existing == null) {
                  this.mapToLookForNavCase.put(key, entry.getValue());
               } else {
                  existing.addAll((Collection)entry.getValue());
               }
            }

         }
      }

      public Set keySet() {
         return new AbstractSet() {
            public Iterator iterator() {
               return new Iterator() {
                  Iterator i = NavigationMap.this.entrySet().iterator();

                  public boolean hasNext() {
                     return this.i.hasNext();
                  }

                  public String next() {
                     return (String)((Map.Entry)this.i.next()).getKey();
                  }

                  public void remove() {
                     throw new UnsupportedOperationException();
                  }
               };
            }

            public int size() {
               return NavigationMap.this.size();
            }
         };
      }

      public Collection values() {
         return new AbstractCollection() {
            public Iterator iterator() {
               return new Iterator() {
                  Iterator i = NavigationMap.this.entrySet().iterator();

                  public boolean hasNext() {
                     return this.i.hasNext();
                  }

                  public Set next() {
                     return (Set)((Map.Entry)this.i.next()).getValue();
                  }

                  public void remove() {
                     throw new UnsupportedOperationException();
                  }
               };
            }

            public int size() {
               return NavigationMap.this.size();
            }
         };
      }

      public Set entrySet() {
         return new AbstractSet() {
            public Iterator iterator() {
               return new Iterator() {
                  Iterator i;

                  {
                     this.i = NavigationMap.this.mapToLookForNavCase.entrySet().iterator();
                  }

                  public boolean hasNext() {
                     return this.i.hasNext();
                  }

                  public Map.Entry next() {
                     return (Map.Entry)this.i.next();
                  }

                  public void remove() {
                     throw new UnsupportedOperationException();
                  }
               };
            }

            public int size() {
               return NavigationMap.this.size();
            }
         };
      }

      private void updateWildcards(String fromViewId) {
         if (!this.mapToLookForNavCase.containsKey(fromViewId) && fromViewId.endsWith("*")) {
            this.wildcardMatchList.add(fromViewId.substring(0, fromViewId.lastIndexOf(42)));
         }

      }

      // $FF: synthetic method
      NavigationMap(Object x0) {
         this();
      }
   }

   private static final class NavigationInfo {
      private NavigationMap ruleSet;
      private Map switches;

      private NavigationInfo() {
      }

      // $FF: synthetic method
      NavigationInfo(Object x0) {
         this();
      }
   }

   private static class CaseStruct {
      String viewId;
      NavigationCase navCase;
      Flow currentFlow;
      Flow newFlow;
      FlowCallNode facesFlowCallNode;
      boolean isFlowEntryFromExplicitRule;

      private CaseStruct() {
      }

      // $FF: synthetic method
      CaseStruct(Object x0) {
         this();
      }
   }
}
