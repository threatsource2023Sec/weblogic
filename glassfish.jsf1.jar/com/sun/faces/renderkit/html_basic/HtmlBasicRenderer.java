package com.sun.faces.renderkit.html_basic;

import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIParameter;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.render.Renderer;

public abstract class HtmlBasicRenderer extends Renderer {
   protected static final Logger logger;
   private static final Param[] EMPTY_PARAMS;

   public String convertClientId(FacesContext context, String clientId) {
      return clientId;
   }

   public void decode(FacesContext context, UIComponent component) {
      this.rendererParamsNotNull(context, component);
      if (!(component instanceof UIInput)) {
         if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "No decoding necessary since the component {0} is not an instance or a sub class of UIInput", component.getId());
         }

      } else if (this.shouldDecode(component)) {
         String clientId = component.getClientId(context);

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
      Iterator messageIter;
      if (null != forComponent) {
         if (forComponent.length() == 0) {
            messageIter = context.getMessages((String)null);
         } else {
            UIComponent result = this.getForComponent(context, forComponent, component);
            if (result == null) {
               messageIter = Collections.EMPTY_LIST.iterator();
            } else {
               messageIter = context.getMessages(result.getClientId(context));
            }
         }
      } else {
         messageIter = context.getMessages();
      }

      return messageIter;
   }

   protected Param[] getParamList(UIComponent command) {
      if (command.getChildCount() > 0) {
         ArrayList parameterList = new ArrayList();
         Iterator i$ = command.getChildren().iterator();

         while(i$.hasNext()) {
            UIComponent kid = (UIComponent)i$.next();
            if (kid instanceof UIParameter) {
               UIParameter uiParam = (UIParameter)kid;
               Object value = uiParam.getValue();
               Param param = new Param(uiParam.getName(), value == null ? null : value.toString());
               parameterList.add(param);
            }
         }

         return (Param[])parameterList.toArray(new Param[parameterList.size()]);
      } else {
         return EMPTY_PARAMS;
      }
   }

   protected Object getValue(UIComponent component) {
      throw new UnsupportedOperationException();
   }

   protected void setSubmittedValue(UIComponent component, Object value) {
   }

   protected boolean shouldWriteIdAttribute(UIComponent component) {
      String id;
      return null != (id = component.getId()) && !id.startsWith("j_id");
   }

   protected String writeIdAttributeIfNecessary(FacesContext context, ResponseWriter writer, UIComponent component) {
      String id = null;
      if (this.shouldWriteIdAttribute(component)) {
         try {
            writer.writeAttribute("id", id = component.getClientId(context), "id");
         } catch (IOException var7) {
            if (logger.isLoggable(Level.WARNING)) {
               String message = MessageUtils.getExceptionMessageString("com.sun.faces.CANT_WRITE_ID_ATTRIBUTE", var7.getMessage());
               logger.warning(message);
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
   }

   public static class OptionComponentInfo {
      String disabledClass;
      String enabledClass;
      boolean disabled;

      public OptionComponentInfo(String disabledClass, String enabledClass, boolean disabled) {
         this.disabledClass = disabledClass;
         this.enabledClass = enabledClass;
         this.disabled = disabled;
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
