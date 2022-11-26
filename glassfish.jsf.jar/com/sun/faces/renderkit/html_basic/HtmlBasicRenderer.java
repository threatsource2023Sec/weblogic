package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIParameter;
import javax.faces.component.ValueHolder;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.component.search.SearchExpressionContext;
import javax.faces.component.search.SearchExpressionHandler;
import javax.faces.component.search.SearchExpressionHint;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.render.Renderer;

public abstract class HtmlBasicRenderer extends Renderer {
   protected static final Logger logger;
   protected static final Param[] EMPTY_PARAMS;
   private static final Set EXPRESSION_HINTS;

   public String convertClientId(FacesContext context, String clientId) {
      return clientId;
   }

   public void decode(FacesContext context, UIComponent component) {
      this.rendererParamsNotNull(context, component);
      if (this.shouldDecode(component)) {
         String clientId = this.decodeBehaviors(context, component);
         if (!(component instanceof UIInput)) {
            if (logger.isLoggable(Level.FINE)) {
               logger.log(Level.FINE, "No decoding necessary since the component {0} is not an instance or a sub class of UIInput", component.getId());
            }

         } else {
            if (clientId == null) {
               clientId = component.getClientId(context);
            }

            assert clientId != null;

            Map requestMap = context.getExternalContext().getRequestParameterMap();
            String newValue = (String)requestMap.get(clientId);
            if (newValue != null) {
               this.setSubmittedValue(component, newValue);
               if (logger.isLoggable(Level.FINE)) {
                  logger.log(Level.FINE, "new value after decoding {0}", newValue);
               }
            }

         }
      }
   }

   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncode(component)) {
         ResponseWriter writer = context.getResponseWriter();

         assert writer != null;

         String currentValue = this.getCurrentValue(context, component);
         if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Value to be rendered {0}", currentValue);
         }

         this.getEndTextToRender(context, component, currentValue);
      }
   }

   public boolean getRendersChildren() {
      return true;
   }

   protected final String decodeBehaviors(FacesContext context, UIComponent component) {
      if (!(component instanceof ClientBehaviorHolder)) {
         return null;
      } else {
         ClientBehaviorHolder holder = (ClientBehaviorHolder)component;
         Map behaviors = holder.getClientBehaviors();
         if (behaviors.isEmpty()) {
            return null;
         } else {
            String behaviorEvent = RenderKitUtils.PredefinedPostbackParameter.BEHAVIOR_EVENT_PARAM.getValue(context);
            if (null != behaviorEvent) {
               List behaviorsForEvent = (List)behaviors.get(behaviorEvent);
               if (behaviorsForEvent != null && behaviorsForEvent.size() > 0) {
                  String behaviorSource = RenderKitUtils.PredefinedPostbackParameter.BEHAVIOR_SOURCE_PARAM.getValue(context);
                  String clientId = component.getClientId();
                  if (this.isBehaviorSource(context, behaviorSource, clientId)) {
                     Iterator var9 = behaviorsForEvent.iterator();

                     while(var9.hasNext()) {
                        ClientBehavior behavior = (ClientBehavior)var9.next();
                        behavior.decode(context, component);
                     }
                  }

                  return clientId;
               }
            }

            return null;
         }
      }
   }

   protected boolean isBehaviorSource(FacesContext ctx, String behaviorSourceId, String componentClientId) {
      return behaviorSourceId != null && behaviorSourceId.equals(componentClientId);
   }

   protected String augmentIdReference(String forValue, UIComponent fromComponent) {
      int forSuffix = forValue.lastIndexOf("j_id");
      if (forSuffix <= 0) {
         String id = fromComponent.getId();
         if (id != null) {
            int idSuffix = id.lastIndexOf("j_id");
            if (idSuffix > 0) {
               if (logger.isLoggable(Level.FINE)) {
                  logger.fine("Augmenting for attribute with " + id.substring(idSuffix) + " suffix from Id attribute");
               }

               forValue = forValue + id.substring(idSuffix);
            }
         }
      }

      return forValue;
   }

   protected void encodeRecursive(FacesContext context, UIComponent component) throws IOException {
      if (component.isRendered()) {
         component.encodeBegin(context);
         if (component.getRendersChildren()) {
            component.encodeChildren(context);
         } else {
            Iterator kids = this.getChildren(component);

            while(kids.hasNext()) {
               UIComponent kid = (UIComponent)kids.next();
               this.encodeRecursive(context, kid);
            }
         }

         component.encodeEnd(context);
      }
   }

   protected Iterator getChildren(UIComponent component) {
      int childCount = component.getChildCount();
      return childCount > 0 ? component.getChildren().iterator() : Collections.emptyList().iterator();
   }

   protected String getCurrentValue(FacesContext context, UIComponent component) {
      if (component instanceof UIInput) {
         Object submittedValue = ((UIInput)component).getSubmittedValue();
         if (submittedValue != null) {
            return submittedValue.toString();
         }
      }

      String currentValue = null;
      Object currentObj = this.getValue(component);
      if (currentObj != null) {
         currentValue = this.getFormattedValue(context, component, currentObj);
      }

      return currentValue;
   }

   protected void getEndTextToRender(FacesContext context, UIComponent component, String currentValue) throws IOException {
   }

   protected UIComponent getFacet(UIComponent component, String name) {
      UIComponent facet = null;
      if (component.getFacetCount() > 0) {
         facet = component.getFacet(name);
         if (facet != null && !facet.isRendered()) {
            facet = null;
         }
      }

      return facet;
   }

   protected UIComponent getForComponent(FacesContext context, String forComponent, UIComponent component) {
      if (null != forComponent && forComponent.length() != 0) {
         UIComponent result = null;
         UIComponent currentParent = component;

         try {
            while(currentParent != null) {
               result = currentParent.findComponent(forComponent);
               if (result != null) {
                  break;
               }

               currentParent = currentParent.getParent();
            }

            if (result == null) {
               result = findUIComponentBelow(context.getViewRoot(), forComponent);
            }
         } catch (Exception var7) {
            if (logger.isLoggable(Level.FINEST)) {
               logger.log(Level.FINEST, "Unable to find for component", var7);
            }
         }

         if (result == null && logger.isLoggable(Level.WARNING)) {
            logger.warning(MessageUtils.getExceptionMessageString("com.sun.faces.COMPONENT_NOT_FOUND_IN_VIEW_WARNING", forComponent));
         }

         return result;
      } else {
         return null;
      }
   }

   protected String getFormattedValue(FacesContext context, UIComponent component, Object currentValue, Converter converter) throws ConverterException {
      if (!(component instanceof ValueHolder)) {
         return currentValue != null ? currentValue.toString() : null;
      } else {
         if (converter == null) {
            converter = ((ValueHolder)component).getConverter();
         }

         if (converter == null) {
            if (currentValue == null) {
               return "";
            }

            if (currentValue instanceof String) {
               return (String)currentValue;
            }

            Class converterType = currentValue.getClass();
            converter = Util.getConverterForClass(converterType, context);
            if (converter == null) {
               return currentValue.toString();
            }
         }

         return converter.getAsString(context, component, currentValue);
      }
   }

   protected String getFormattedValue(FacesContext context, UIComponent component, Object currentValue) throws ConverterException {
      return this.getFormattedValue(context, component, currentValue, (Converter)null);
   }

   protected Iterator getMessageIter(FacesContext context, String forComponent, UIComponent component) {
      if (forComponent == null) {
         return context.getMessages();
      } else if (forComponent.trim().isEmpty()) {
         return context.getMessages((String)null);
      } else {
         SearchExpressionHandler searchExpressionHandler = context.getApplication().getSearchExpressionHandler();
         String clientId = searchExpressionHandler.resolveClientId(SearchExpressionContext.createSearchExpressionContext(context, component, EXPRESSION_HINTS, (Set)null), forComponent);
         return clientId == null ? Collections.emptyIterator() : context.getMessages(clientId);
      }
   }

   protected Param[] getParamList(UIComponent command) {
      if (command.getChildCount() > 0) {
         ArrayList parameterList = new ArrayList();
         Iterator var3 = command.getChildren().iterator();

         while(var3.hasNext()) {
            UIComponent kid = (UIComponent)var3.next();
            if (kid instanceof UIParameter) {
               UIParameter uiParam = (UIParameter)kid;
               if (!uiParam.isDisable()) {
                  Object value = uiParam.getValue();
                  Param param = new Param(uiParam.getName(), value == null ? null : value.toString());
                  parameterList.add(param);
               }
            }
         }

         return (Param[])parameterList.toArray(new Param[parameterList.size()]);
      } else {
         return EMPTY_PARAMS;
      }
   }

   protected Collection getBehaviorParameters(UIComponent command) {
      ArrayList params = null;
      int childCount = command.getChildCount();
      if (childCount > 0) {
         Iterator var4 = command.getChildren().iterator();

         while(var4.hasNext()) {
            UIComponent kid = (UIComponent)var4.next();
            if (kid instanceof UIParameter) {
               UIParameter uiParam = (UIParameter)kid;
               String name = uiParam.getName();
               Object value = uiParam.getValue();
               if (!Util.isEmpty(name)) {
                  if (params == null) {
                     params = new ArrayList(childCount);
                  }

                  params.add(new ClientBehaviorContext.Parameter(name, value));
               }
            }
         }
      }

      return (Collection)(params == null ? Collections.emptyList() : params);
   }

   protected Object getValue(UIComponent component) {
      throw new UnsupportedOperationException();
   }

   protected void setSubmittedValue(UIComponent component, Object value) {
   }

   protected boolean shouldWriteIdAttribute(UIComponent component) {
      String id;
      return null != (id = component.getId()) && (!id.startsWith("j_id") || component instanceof ClientBehaviorHolder && !((ClientBehaviorHolder)component).getClientBehaviors().isEmpty());
   }

   protected String writeIdAttributeIfNecessary(FacesContext context, ResponseWriter writer, UIComponent component) {
      String id = null;
      if (this.shouldWriteIdAttribute(component)) {
         try {
            writer.writeAttribute("id", id = component.getClientId(context), "id");
         } catch (IOException var6) {
            if (logger.isLoggable(Level.WARNING)) {
               logger.warning(MessageUtils.getExceptionMessageString("com.sun.faces.CANT_WRITE_ID_ATTRIBUTE", var6.getMessage()));
            }
         }
      }

      return id;
   }

   protected void rendererParamsNotNull(FacesContext context, UIComponent component) {
      Util.notNull("context", context);
      Util.notNull("component", component);
   }

   protected boolean shouldEncode(UIComponent component) {
      if (!component.isRendered()) {
         if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "End encoding component {0} since rendered attribute is set to false", component.getId());
         }

         return false;
      } else {
         return true;
      }
   }

   protected boolean shouldDecode(UIComponent component) {
      if (Util.componentIsDisabledOrReadonly(component)) {
         if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "No decoding necessary since the component {0} is disabled or read-only", component.getId());
         }

         return false;
      } else {
         return true;
      }
   }

   protected boolean shouldEncodeChildren(UIComponent component) {
      if (!component.isRendered()) {
         if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Children of component {0} will not be encoded since this component's rendered attribute is false", component.getId());
         }

         return false;
      } else {
         return true;
      }
   }

   protected static Map getPassThruBehaviors(UIComponent component, String domEventName, String componentEventName) {
      if (!(component instanceof ClientBehaviorHolder)) {
         return null;
      } else {
         Map behaviors = ((ClientBehaviorHolder)component).getClientBehaviors();
         int size = behaviors.size();
         if (size == 1 || size == 2) {
            boolean hasDomBehavior = behaviors.containsKey(domEventName);
            boolean hasComponentBehavior = behaviors.containsKey(componentEventName);
            if (size == 1 && (hasDomBehavior || hasComponentBehavior) || size == 2 && hasDomBehavior && hasComponentBehavior) {
               return null;
            }
         }

         return behaviors;
      }
   }

   private static UIComponent findUIComponentBelow(UIComponent startPoint, String forComponent) {
      UIComponent retComp = null;
      if (startPoint.getChildCount() > 0) {
         List children = startPoint.getChildren();
         int i = 0;

         for(int size = children.size(); i < size; ++i) {
            UIComponent comp = (UIComponent)children.get(i);
            if (comp instanceof NamingContainer) {
               try {
                  retComp = comp.findComponent(forComponent);
               } catch (IllegalArgumentException var8) {
                  continue;
               }
            }

            if (retComp == null && comp.getChildCount() > 0) {
               retComp = findUIComponentBelow(comp, forComponent);
            }

            if (retComp != null) {
               break;
            }
         }
      }

      return retComp;
   }

   static {
      logger = FacesLogger.RENDERKIT.getLogger();
      EMPTY_PARAMS = new Param[0];
      EXPRESSION_HINTS = EnumSet.of(SearchExpressionHint.IGNORE_NO_RESULT, SearchExpressionHint.RESOLVE_SINGLE_COMPONENT);
   }

   public static class OptionComponentInfo {
      String disabledClass;
      String enabledClass;
      String selectedClass;
      String unselectedClass;
      boolean disabled;
      boolean hideNoSelection;

      public OptionComponentInfo(UIComponent component) {
         Map attributes = component.getAttributes();
         this.disabledClass = (String)attributes.get("disabledClass");
         this.enabledClass = (String)attributes.get("enabledClass");
         this.selectedClass = (String)attributes.get("selectedClass");
         this.unselectedClass = (String)attributes.get("unselectedClass");
         this.disabled = Util.componentIsDisabled(component);
         this.hideNoSelection = MenuRenderer.isHideNoSelection(component);
      }

      public String getDisabledClass() {
         return this.disabledClass;
      }

      public String getEnabledClass() {
         return this.enabledClass;
      }

      public boolean isDisabled() {
         return this.disabled;
      }

      public boolean isHideNoSelection() {
         return this.hideNoSelection;
      }

      public String getSelectedClass() {
         return this.selectedClass;
      }

      public String getUnselectedClass() {
         return this.unselectedClass;
      }
   }

   public static class Param {
      public String name;
      public String value;

      public Param(String name, String value) {
         this.name = name;
         this.value = value;
      }
   }
}
