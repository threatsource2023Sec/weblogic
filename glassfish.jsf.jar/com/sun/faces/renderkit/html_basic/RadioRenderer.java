package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.renderkit.SelectItemsIterator;
import com.sun.faces.util.RequestStateManager;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ComponentSystemEventListener;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.model.SelectItem;

@ListenerFor(
   systemEventClass = PostAddToViewEvent.class
)
public class RadioRenderer extends SelectManyCheckboxListRenderer implements ComponentSystemEventListener {
   private static final Attribute[] ATTRIBUTES;

   public void processEvent(ComponentSystemEvent event) throws AbortProcessingException {
      UISelectOne radio = (UISelectOne)event.getComponent();
      Group group = getGroup(event.getFacesContext(), radio);
      if (group != null) {
         group.addRadio(event.getFacesContext(), radio);
      }

   }

   public void decode(FacesContext context, UIComponent component) {
      UISelectOne radio = (UISelectOne)component;
      Group group = getGroup(context, radio);
      if (group != null) {
         this.decodeGroup(context, radio, group);
      } else {
         super.decode(context, component);
      }

   }

   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      UISelectOne radio = (UISelectOne)component;
      Group group = getGroup(context, radio);
      if (group != null) {
         this.encodeEndGroup(context, radio, group);
      } else {
         super.encodeEnd(context, component);
      }

   }

   protected void decodeGroup(FacesContext context, UISelectOne radio, Group group) {
      this.rendererParamsNotNull(context, radio);
      if (this.shouldDecode(radio)) {
         String clientId = this.decodeBehaviors(context, radio);
         if (clientId == null) {
            clientId = radio.getClientId(context);
         }

         assert clientId != null;

         Map requestParameterMap = context.getExternalContext().getRequestParameterMap();
         String newValue = (String)requestParameterMap.get(group.getClientName());
         String prefix = clientId + UINamingContainer.getSeparatorChar(context);
         if (newValue != null) {
            if (newValue.startsWith(prefix)) {
               String submittedValue = newValue.substring(prefix.length());
               this.setSubmittedValue(radio, submittedValue);
               if (logger.isLoggable(Level.FINE)) {
                  logger.fine("submitted value for UISelectOne group component " + radio.getId() + " after decoding " + submittedValue);
               }
            } else {
               radio.resetValue();
            }
         } else {
            radio.setSubmittedValue("");
         }

      }
   }

   protected void encodeEndGroup(FacesContext context, UISelectOne radio, Group group) throws IOException {
      this.rendererParamsNotNull(context, radio);
      if (this.shouldEncode(radio)) {
         SelectItem currentItem = RenderKitUtils.getSelectItems(context, radio).next();
         String clientId = radio.getClientId(context);
         Object itemValue = currentItem.getValue();
         Converter converter = radio.getConverter();
         boolean checked = this.isChecked(context, radio, itemValue);
         boolean disabled = Util.componentIsDisabled(radio);
         ResponseWriter writer = context.getResponseWriter();

         assert writer != null;

         this.renderInput(context, writer, radio, clientId, itemValue, converter, checked, disabled, group);
         if (currentItem.getLabel() != null) {
            this.renderLabel(writer, radio, clientId, currentItem, new HtmlBasicRenderer.OptionComponentInfo(radio));
         }

      }
   }

   protected boolean isChecked(FacesContext context, UISelectOne radio, Object itemValue) {
      Object currentValue = radio.getSubmittedValue();
      if (currentValue == null) {
         currentValue = radio.getValue();
      }

      Class type = String.class;
      if (currentValue != null) {
         type = currentValue.getClass();
         if (type.isArray()) {
            currentValue = ((Object[])((Object[])currentValue))[0];
            if (null != currentValue) {
               type = currentValue.getClass();
            }
         } else if (Collection.class.isAssignableFrom(type)) {
            Iterator valueIter = ((Collection)currentValue).iterator();
            if (null != valueIter && valueIter.hasNext()) {
               currentValue = valueIter.next();
               if (null != currentValue) {
                  type = currentValue.getClass();
               }
            }
         }
      }

      RequestStateManager.set(context, "com.sun.faces.ComponentForValue", radio);

      Object newValue;
      try {
         newValue = context.getApplication().getExpressionFactory().coerceToType(itemValue, type);
      } catch (IllegalArgumentException | ELException var8) {
         newValue = itemValue;
      }

      return newValue != null && newValue.equals(currentValue);
   }

   protected void renderOption(FacesContext context, UIComponent component, Converter converter, SelectItem curItem, Object currentSelections, Object[] submittedValues, boolean alignVertical, int itemNumber, HtmlBasicRenderer.OptionComponentInfo optionInfo) throws IOException {
      ResponseWriter writer = context.getResponseWriter();

      assert writer != null;

      UISelectOne selectOne = (UISelectOne)component;
      Object curValue = curItem.getValue();
      boolean checked = this.isChecked(context, selectOne, curValue);
      if (!optionInfo.isHideNoSelection() || !curItem.isNoSelectionOption() || curValue == null || checked) {
         if (alignVertical) {
            writer.writeText("\t", component, (String)null);
            writer.startElement("tr", component);
            writer.writeText("\n", component, (String)null);
         }

         writer.startElement("td", component);
         writer.writeText("\n", component, (String)null);
         String clientId = component.getClientId(context) + UINamingContainer.getSeparatorChar(context) + Integer.toString(itemNumber);
         boolean disabled = !optionInfo.isDisabled() && curItem.isDisabled();
         this.renderInput(context, writer, component, clientId, curValue, converter, checked, disabled, (Group)null);
         this.renderLabel(writer, component, clientId, curItem, optionInfo);
         writer.endElement("td");
         writer.writeText("\n", component, (String)null);
         if (alignVertical) {
            writer.writeText("\t", component, (String)null);
            writer.endElement("tr");
            writer.writeText("\n", component, (String)null);
         }

      }
   }

   protected void renderInput(FacesContext context, ResponseWriter writer, UIComponent component, String clientId, Object itemValue, Converter converter, boolean checked, boolean disabled, Group group) throws IOException {
      writer.startElement("input", component);
      writer.writeAttribute("type", "radio", "type");
      if (checked) {
         writer.writeAttribute("checked", Boolean.TRUE, (String)null);
      }

      Object value = this.getFormattedValue(context, component, itemValue, converter);
      if (group == null) {
         writer.writeAttribute("name", component.getClientId(context), "clientId");
         writer.writeAttribute("id", clientId, "id");
         writer.writeAttribute("value", value, "value");
      } else {
         writer.writeAttribute("name", group.getClientName(), "group");
         writer.writeAttribute("id", clientId, "id");
         writer.writeAttribute("value", clientId + UINamingContainer.getSeparatorChar(context) + value, "value");
      }

      if (disabled) {
         writer.writeAttribute("disabled", true, "disabled");
      }

      RenderKitUtils.renderPassThruAttributes(context, writer, component, ATTRIBUTES, getNonOnClickSelectBehaviors(component));
      RenderKitUtils.renderXHTMLStyleBooleanAttributes(writer, component);
      RenderKitUtils.renderSelectOnclick(context, component, false);
      writer.endElement("input");
   }

   protected void renderLabel(ResponseWriter writer, UIComponent component, String forClientId, SelectItem curItem, HtmlBasicRenderer.OptionComponentInfo optionInfo) throws IOException {
      String labelClass;
      if (!optionInfo.isDisabled() && !curItem.isDisabled()) {
         labelClass = optionInfo.getEnabledClass();
      } else {
         labelClass = optionInfo.getDisabledClass();
      }

      writer.startElement("label", component);
      writer.writeAttribute("for", forClientId, "for");
      if (labelClass != null) {
         writer.writeAttribute("class", labelClass, "labelClass");
      }

      String itemLabel = curItem.getLabel();
      if (itemLabel != null) {
         writer.writeText(" ", component, (String)null);
         if (!curItem.isEscape()) {
            writer.write(itemLabel);
         } else {
            writer.writeText(itemLabel, component, "label");
         }
      }

      writer.endElement("label");
   }

   protected static Group getGroup(FacesContext context, UISelectOne radio) {
      String groupName = radio.getGroup();
      if (groupName == null) {
         return null;
      } else {
         UIComponent groupContainer = RenderKitUtils.getForm(radio, context);
         if (groupContainer == null) {
            groupContainer = context.getViewRoot();
         }

         String clientName = ((UIComponent)groupContainer).getClientId(context) + UINamingContainer.getSeparatorChar(context) + groupName;
         Map radioButtonGroups = (Map)RequestStateManager.get(context, "com.sun.faces.PROCESSED_RADIO_BUTTON_GROUPS");
         if (radioButtonGroups == null) {
            radioButtonGroups = new HashMap();
            RequestStateManager.set(context, "com.sun.faces.PROCESSED_RADIO_BUTTON_GROUPS", radioButtonGroups);
         }

         Group group = (Group)((Map)radioButtonGroups).get(clientName);
         if (group == null) {
            group = new Group(context, clientName);
            ((Map)radioButtonGroups).put(clientName, group);
         }

         return group;
      }
   }

   static {
      ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.SELECTONERADIO);
   }

   public static class GroupSelectItem extends UISelectItem {
      private SelectItem selectItem;

      private SelectItem getSelectItem() {
         if (this.selectItem == null) {
            FacesContext context = this.getFacesContext();
            UISelectOne radio = (UISelectOne)this.getParent();
            List groupClientIds = (List)radio.getAttributes().get(GroupSelectItem.class.getName());
            UIComponent firstRadioOfGroup = context.getViewRoot().findComponent((String)groupClientIds.get(0));
            SelectItemsIterator iterator = RenderKitUtils.getSelectItems(context, firstRadioOfGroup);
            int index = groupClientIds.indexOf(radio.getClientId(context));

            while(index-- > 0 && iterator.hasNext()) {
               iterator.next();
            }

            if (!iterator.hasNext()) {
               throw new IllegalStateException(MessageFormat.format("UISelectOne component id=\"{0}\" group=\"{1}\" has no UISelectItem", radio.getId(), radio.getGroup()));
            }

            this.selectItem = iterator.next();
         }

         return this.selectItem;
      }

      public Object getItemValue() {
         return this.getSelectItem().getValue();
      }

      public String getItemLabel() {
         return this.getSelectItem().getLabel();
      }

      public String getItemDescription() {
         return this.getSelectItem().getDescription();
      }

      public boolean isItemEscaped() {
         return this.getSelectItem().isEscape();
      }

      public boolean isNoSelectionOption() {
         return this.getSelectItem().isNoSelectionOption();
      }

      public boolean isItemDisabled() {
         return this.getSelectItem().isDisabled();
      }
   }

   protected static class Group {
      private final String clientName;
      private final List clientIds;
      private ValueExpression value;

      public Group(FacesContext context, String clientName) {
         this.clientName = clientName;
         this.clientIds = new ArrayList();
      }

      public String getClientName() {
         return this.clientName;
      }

      public void addRadio(FacesContext context, UISelectOne radio) {
         String clientId = radio.getClientId(context);
         if (!this.clientIds.contains(clientId)) {
            if (this.clientIds.isEmpty()) {
               this.value = radio.getValueExpression("value");
            } else if (radio.getValueExpression("value") == null) {
               radio.setValueExpression("value", this.value);
            }

            if (!RenderKitUtils.getSelectItems(context, radio).hasNext()) {
               radio.getChildren().add(new GroupSelectItem());
            }

            this.clientIds.add(clientId);
            radio.getAttributes().put(GroupSelectItem.class.getName(), Collections.unmodifiableList(this.clientIds));
         }

      }
   }
}
