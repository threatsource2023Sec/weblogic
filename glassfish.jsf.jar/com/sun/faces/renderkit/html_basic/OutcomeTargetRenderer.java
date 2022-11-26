package com.sun.faces.renderkit.html_basic;

import com.sun.faces.application.NavigationHandlerImpl;
import com.sun.faces.flow.FlowHandlerImpl;
import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationCase;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutcomeTarget;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.flow.FlowHandler;
import javax.faces.lifecycle.ClientWindow;

public abstract class OutcomeTargetRenderer extends HtmlBasicRenderer {
   public void decode(FacesContext context, UIComponent component) {
   }

   protected void renderPassThruAttributes(FacesContext ctx, ResponseWriter writer, UIComponent component, Attribute[] attributes, List excludedAttributes) throws IOException {
      RenderKitUtils.renderPassThruAttributes(ctx, writer, component, attributes);
      RenderKitUtils.renderXHTMLStyleBooleanAttributes(writer, component, excludedAttributes);
   }

   protected String getLabel(UIComponent component) {
      Object value = ((UIOutcomeTarget)component).getValue();
      return value != null ? value.toString() : "";
   }

   protected String getFragment(UIComponent component) {
      String fragment = (String)component.getAttributes().get("fragment");
      fragment = fragment != null ? fragment.trim() : "";
      if (fragment.length() > 0) {
         fragment = "#" + fragment;
      }

      return fragment;
   }

   protected Object getValue(UIComponent component) {
      return ((UIOutcomeTarget)component).getValue();
   }

   protected boolean isIncludeViewParams(UIComponent component, NavigationCase navcase) {
      return ((UIOutcomeTarget)component).isIncludeViewParams() || navcase.isIncludeViewParams();
   }

   protected NavigationCase getNavigationCase(FacesContext context, UIComponent component) {
      NavigationHandler navHandler = context.getApplication().getNavigationHandler();
      if (!(navHandler instanceof ConfigurableNavigationHandler)) {
         if (logger.isLoggable(Level.WARNING)) {
            logger.log(Level.WARNING, "jsf.outcome.target.invalid.navigationhandler.type", component.getId());
         }

         return null;
      } else {
         String outcome = ((UIOutcomeTarget)component).getOutcome();
         if (outcome == null) {
            outcome = context.getViewRoot().getViewId();
         }

         String toFlowDocumentId = (String)component.getAttributes().get("to-flow-document-id");
         NavigationCase navCase = null;
         NavigationHandlerImpl.setResetFlowHandlerStateIfUnset(context, false);

         try {
            if (null == toFlowDocumentId) {
               navCase = ((ConfigurableNavigationHandler)navHandler).getNavigationCase(context, (String)null, outcome);
            } else {
               navCase = ((ConfigurableNavigationHandler)navHandler).getNavigationCase(context, (String)null, outcome, toFlowDocumentId);
            }
         } finally {
            NavigationHandlerImpl.unsetResetFlowHandlerState(context);
         }

         if (navCase == null && logger.isLoggable(Level.WARNING)) {
            logger.log(Level.WARNING, "jsf.outcometarget.navigation.case.not.resolved", component.getId());
         }

         return navCase;
      }
   }

   protected String getEncodedTargetURL(FacesContext context, UIComponent component, NavigationCase navCase) {
      String toViewId = navCase.getToViewId(context);
      Map params = this.getParamOverrides(component);
      this.addNavigationParams(navCase, params);
      String result = null;
      boolean didDisableClientWindowRendering = false;
      ClientWindow cw = null;

      try {
         Map attrs = component.getAttributes();
         Object val = attrs.get("disableClientWindow");
         if (null != val) {
            didDisableClientWindowRendering = "true".equalsIgnoreCase(val.toString());
         }

         if (didDisableClientWindowRendering) {
            cw = context.getExternalContext().getClientWindow();
            if (null != cw) {
               cw.disableClientWindowRenderMode(context);
            }
         }

         result = Util.getViewHandler(context).getBookmarkableURL(context, toViewId, params, this.isIncludeViewParams(component, navCase));
      } finally {
         if (didDisableClientWindowRendering && null != cw) {
            cw.enableClientWindowRenderMode(context);
         }

      }

      return result;
   }

   protected void addNavigationParams(NavigationCase navCase, Map existingParams) {
      Map navParams = navCase.getParameters();
      if (navParams != null && !navParams.isEmpty()) {
         Iterator var4 = navParams.entrySet().iterator();

         label55:
         while(true) {
            while(true) {
               while(true) {
                  Map.Entry entry;
                  String navParamName;
                  do {
                     if (!var4.hasNext()) {
                        break label55;
                     }

                     entry = (Map.Entry)var4.next();
                     navParamName = (String)entry.getKey();
                  } while(existingParams.containsKey(navParamName));

                  if (((List)entry.getValue()).size() == 1) {
                     String value = (String)((List)entry.getValue()).get(0);
                     String sanitized = null != value && 2 < value.length() ? value.trim() : "";
                     if (!sanitized.contains("#{") && !sanitized.contains("${")) {
                        existingParams.put(navParamName, entry.getValue());
                     } else {
                        FacesContext fc = FacesContext.getCurrentInstance();
                        value = (String)fc.getApplication().evaluateExpressionGet(fc, value, String.class);
                        List values = new ArrayList();
                        values.add(value);
                        existingParams.put(navParamName, values);
                     }
                  } else {
                     existingParams.put(navParamName, entry.getValue());
                  }
               }
            }
         }
      }

      String toFlowDocumentId = navCase.getToFlowDocumentId();
      if (null != toFlowDocumentId) {
         if ("javax.faces.flow.NullFlow".equals(toFlowDocumentId)) {
            List flowDocumentIdValues = new ArrayList();
            flowDocumentIdValues.add("javax.faces.flow.NullFlow");
            existingParams.put("jftfdi", flowDocumentIdValues);
            FacesContext context = FacesContext.getCurrentInstance();
            FlowHandler fh = context.getApplication().getFlowHandler();
            if (fh instanceof FlowHandlerImpl) {
               FlowHandlerImpl fhi = (FlowHandlerImpl)fh;
               List flowReturnDepthValues = new ArrayList();
               flowReturnDepthValues.add(Integer.toString(fhi.getAndClearReturnModeDepth(context)));
               existingParams.put("jffrd", flowReturnDepthValues);
            }
         } else {
            String flowId = navCase.getFromOutcome();
            List flowDocumentIdValues = new ArrayList();
            flowDocumentIdValues.add(toFlowDocumentId);
            existingParams.put("jftfdi", flowDocumentIdValues);
            List flowIdValues = new ArrayList();
            flowIdValues.add(flowId);
            existingParams.put("jffi", flowIdValues);
         }
      }

   }

   protected Map getParamOverrides(UIComponent component) {
      Map params = new LinkedHashMap();
      HtmlBasicRenderer.Param[] declaredParams = this.getParamList(component);
      HtmlBasicRenderer.Param[] var4 = declaredParams;
      int var5 = declaredParams.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         HtmlBasicRenderer.Param candidate = var4[var6];
         if (candidate.name != null && candidate.name.trim().length() > 0) {
            candidate.name = candidate.name.trim();
            List values = (List)params.get(candidate.name);
            if (values == null) {
               values = new ArrayList();
               params.put(candidate.name, values);
            }

            ((List)values).add(candidate.value);
         }
      }

      return params;
   }
}
