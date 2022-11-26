package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.util.FacesLogger;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.ActionSource;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.component.html.HtmlCommandScript;
import javax.faces.component.search.ComponentNotFoundException;
import javax.faces.component.search.SearchExpressionContext;
import javax.faces.component.search.SearchExpressionHandler;
import javax.faces.component.search.SearchExpressionHint;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.PhaseId;
import javax.faces.render.ClientBehaviorRenderer;

public class AjaxBehaviorRenderer extends ClientBehaviorRenderer {
   protected static final Logger logger;
   private static final Set EXPRESSION_HINTS;

   public String getScript(ClientBehaviorContext behaviorContext, ClientBehavior behavior) {
      if (!(behavior instanceof AjaxBehavior)) {
         throw new IllegalArgumentException("Instance of javax.faces.component.behavior.AjaxBehavior required: " + behavior);
      } else {
         return ((AjaxBehavior)behavior).isDisabled() ? null : buildAjaxCommand(behaviorContext, (AjaxBehavior)behavior);
      }
   }

   public void decode(FacesContext context, UIComponent component, ClientBehavior behavior) {
      if (null != context && null != component && null != behavior) {
         if (!(behavior instanceof AjaxBehavior)) {
            throw new IllegalArgumentException("Instance of javax.faces.component.behavior.AjaxBehavior required: " + behavior);
         } else {
            AjaxBehavior ajaxBehavior = (AjaxBehavior)behavior;
            if (!ajaxBehavior.isDisabled()) {
               component.queueEvent(createEvent(context, component, ajaxBehavior));
               if (logger.isLoggable(Level.FINE)) {
                  logger.fine("This command resulted in form submission  AjaxBehaviorEvent queued.");
                  logger.log(Level.FINE, "End decoding component {0}", component.getId());
               }

            }
         }
      } else {
         throw new NullPointerException();
      }
   }

   private static AjaxBehaviorEvent createEvent(FacesContext facesContext, UIComponent component, AjaxBehavior ajaxBehavior) {
      AjaxBehaviorEvent event = new AjaxBehaviorEvent(facesContext, component, ajaxBehavior);
      PhaseId phaseId = isImmediate(component, ajaxBehavior) ? PhaseId.APPLY_REQUEST_VALUES : PhaseId.INVOKE_APPLICATION;
      event.setPhaseId(phaseId);
      return event;
   }

   private static boolean isImmediate(UIComponent component, AjaxBehavior ajaxBehavior) {
      boolean immediate = false;
      if (ajaxBehavior.isImmediateSet()) {
         immediate = ajaxBehavior.isImmediate();
      } else if (component instanceof EditableValueHolder) {
         immediate = ((EditableValueHolder)component).isImmediate();
      } else if (component instanceof ActionSource) {
         immediate = ((ActionSource)component).isImmediate();
      }

      return immediate;
   }

   private static String buildAjaxCommand(ClientBehaviorContext behaviorContext, AjaxBehavior ajaxBehavior) {
      if (ajaxBehavior.isDisabled()) {
         return null;
      } else {
         UIComponent component = behaviorContext.getComponent();
         String eventName = behaviorContext.getEventName();
         StringBuilder ajaxCommand = new StringBuilder(256);
         Collection execute = ajaxBehavior.getExecute();
         Collection render = ajaxBehavior.getRender();
         String onevent = ajaxBehavior.getOnevent();
         String onerror = ajaxBehavior.getOnerror();
         String sourceId = behaviorContext.getSourceId();
         String delay = ajaxBehavior.getDelay();
         Boolean resetValues = null;
         if (ajaxBehavior.isResetValuesSet()) {
            resetValues = ajaxBehavior.isResetValues();
         }

         Collection params = behaviorContext.getParameters();
         ClientBehaviorContext.Parameter foundparam = null;
         Iterator var14 = ((Collection)params).iterator();

         while(var14.hasNext()) {
            ClientBehaviorContext.Parameter param = (ClientBehaviorContext.Parameter)var14.next();
            if (param.getName().equals("incExec") && (Boolean)param.getValue()) {
               foundparam = param;
            }
         }

         if (foundparam != null && !((Collection)execute).contains(sourceId)) {
            execute = new LinkedList((Collection)execute);
            ((Collection)execute).add(component.getClientId());
         }

         if (foundparam != null) {
            try {
               ((Collection)params).remove(foundparam);
            } catch (UnsupportedOperationException var19) {
               if (logger.isLoggable(Level.FINEST)) {
                  logger.log(Level.FINEST, "Unsupported operation", var19);
               }
            }
         }

         HtmlCommandScript commandScript = component instanceof HtmlCommandScript ? (HtmlCommandScript)component : null;
         if (commandScript != null) {
            String name = commandScript.getName();
            if (!name.contains(".")) {
               ajaxCommand.append("var ");
            }

            ajaxCommand.append(name).append('=').append("function(o){var o=(typeof o==='object')&&o?o:{};");

            for(Iterator var16 = ((Collection)params).iterator(); var16.hasNext(); ajaxCommand.append(";")) {
               ClientBehaviorContext.Parameter param = (ClientBehaviorContext.Parameter)var16.next();
               ajaxCommand.append("o[");
               RenderKitUtils.appendQuotedValue(ajaxCommand, param.getName());
               ajaxCommand.append("]=");
               Object paramValue = param.getValue();
               if (paramValue == null) {
                  ajaxCommand.append("null");
               } else {
                  RenderKitUtils.appendQuotedValue(ajaxCommand, paramValue.toString());
               }
            }

            params = Collections.singleton(new ClientBehaviorContext.Parameter("o", (Object)null));
         }

         ajaxCommand.append("mojarra.ab(");
         if (sourceId == null) {
            ajaxCommand.append("this");
         } else {
            ajaxCommand.append("'");
            ajaxCommand.append(sourceId);
            ajaxCommand.append("'");
         }

         ajaxCommand.append(",");
         ajaxCommand.append(commandScript == null ? "event" : "null");
         ajaxCommand.append(",'");
         ajaxCommand.append(eventName);
         ajaxCommand.append("',");
         appendIds(behaviorContext.getFacesContext(), component, ajaxCommand, (Collection)execute);
         ajaxCommand.append(",");
         appendIds(behaviorContext.getFacesContext(), component, ajaxCommand, render);
         if (onevent != null || onerror != null || delay != null || resetValues != null || !((Collection)params).isEmpty()) {
            ajaxCommand.append(",{");
            if (onevent != null) {
               RenderKitUtils.appendProperty(ajaxCommand, "onevent", onevent, false);
            }

            if (onerror != null) {
               RenderKitUtils.appendProperty(ajaxCommand, "onerror", onerror, false);
            }

            if (delay != null) {
               RenderKitUtils.appendProperty(ajaxCommand, "delay", delay, true);
            }

            if (resetValues != null) {
               RenderKitUtils.appendProperty(ajaxCommand, "resetValues", resetValues, false);
            }

            if (!((Collection)params).isEmpty()) {
               if (commandScript != null) {
                  RenderKitUtils.appendProperty(ajaxCommand, "params", ((ClientBehaviorContext.Parameter)((Collection)params).iterator().next()).getName(), false);
               } else {
                  RenderKitUtils.appendProperty(ajaxCommand, "params", "{", false);
                  Iterator var22 = ((Collection)params).iterator();

                  while(var22.hasNext()) {
                     ClientBehaviorContext.Parameter param = (ClientBehaviorContext.Parameter)var22.next();
                     RenderKitUtils.appendProperty(ajaxCommand, param.getName(), param.getValue());
                  }

                  ajaxCommand.append("}");
               }
            }

            ajaxCommand.append("}");
         }

         ajaxCommand.append(")");
         if (commandScript != null) {
            ajaxCommand.append("}");
            if (commandScript.isAutorun()) {
               ajaxCommand.append(";mojarra.l(").append(commandScript.getName()).append(")");
            }
         }

         return ajaxCommand.toString();
      }
   }

   private static void appendIds(FacesContext facesContext, UIComponent component, StringBuilder builder, Collection ids) {
      if (null != ids && !ids.isEmpty()) {
         builder.append("'");
         SearchExpressionHandler handler = null;
         SearchExpressionContext searchExpressionContext = null;
         boolean first = true;
         Iterator var7 = ids.iterator();

         while(true) {
            while(true) {
               String id;
               do {
                  if (!var7.hasNext()) {
                     builder.append("'");
                     return;
                  }

                  id = (String)var7.next();
               } while(id.trim().length() == 0);

               if (!first) {
                  builder.append(' ');
               } else {
                  first = false;
               }

               if (!id.equals("@all") && !id.equals("@none") && !id.equals("@form") && !id.equals("@this")) {
                  if (searchExpressionContext == null) {
                     searchExpressionContext = SearchExpressionContext.createSearchExpressionContext(facesContext, component, EXPRESSION_HINTS, (Set)null);
                  }

                  if (handler == null) {
                     handler = facesContext.getApplication().getSearchExpressionHandler();
                  }

                  String resolvedClientId = null;

                  try {
                     resolvedClientId = handler.resolveClientId(searchExpressionContext, id);
                  } catch (ComponentNotFoundException var11) {
                     resolvedClientId = getResolvedId(component, id);
                  }

                  builder.append(resolvedClientId);
               } else {
                  builder.append(id);
               }
            }
         }
      } else {
         builder.append('0');
      }
   }

   private static String getResolvedId(UIComponent component, String id) {
      UIComponent resolvedComponent = component.findComponent(id);
      if (resolvedComponent == null) {
         return id.charAt(0) == UINamingContainer.getSeparatorChar(FacesContext.getCurrentInstance()) ? id.substring(1) : id;
      } else {
         return resolvedComponent.getClientId();
      }
   }

   static {
      logger = FacesLogger.RENDERKIT.getLogger();
      EXPRESSION_HINTS = EnumSet.of(SearchExpressionHint.RESOLVE_CLIENT_SIDE, SearchExpressionHint.RESOLVE_SINGLE_COMPONENT);
   }
}
