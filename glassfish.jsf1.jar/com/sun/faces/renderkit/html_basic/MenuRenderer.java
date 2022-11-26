package com.sun.faces.renderkit.html_basic;

import com.sun.faces.RIConstants;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.RequestStateManager;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.el.ELException;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectMany;
import javax.faces.component.UISelectOne;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

public class MenuRenderer extends HtmlBasicInputRenderer {
   private static final String[] ATTRIBUTES;

   public Object convertSelectManyValue(FacesContext context, UISelectMany uiSelectMany, String[] newValues) throws ConverterException {
      ValueExpression valueExpression = uiSelectMany.getValueExpression("value");
      Object result = newValues;
      boolean throwException = false;
      if (null != valueExpression) {
         Class modelType = valueExpression.getType(context.getELContext());
         if (modelType != null) {
            result = this.convertSelectManyValuesForModel(context, uiSelectMany, modelType, newValues);
         }

         if (result == null) {
            Object value = valueExpression.getValue(context.getELContext());
            if (value != null) {
               result = this.convertSelectManyValuesForModel(context, uiSelectMany, value.getClass(), newValues);
            }
         }

         if (result == null) {
            throwException = true;
         }
      } else {
         result = this.convertSelectManyValues(context, uiSelectMany, Object[].class, newValues);
      }

      if (!throwException) {
         if (logger.isLoggable(Level.FINE)) {
            logger.fine("SelectMany Component  " + uiSelectMany.getId() + " convertedValues " + result);
         }

         return result;
      } else {
         StringBuffer values = new StringBuffer();
         if (null != newValues) {
            for(int i = 0; i < newValues.length; ++i) {
               if (i == 0) {
                  values.append(newValues[i]);
               } else {
                  values.append(' ').append(newValues[i]);
               }
            }
         }

         Object[] params = new Object[]{values.toString(), valueExpression.getExpressionString()};
         throw new ConverterException(MessageUtils.getExceptionMessage("com.sun.faces.TYPECONVERSION_ERROR", params));
      }
   }

   public Object convertSelectOneValue(FacesContext context, UISelectOne uiSelectOne, String newValue) throws ConverterException {
      if (RIConstants.NO_VALUE.equals(newValue)) {
         return null;
      } else if (newValue == null) {
         if (logger.isLoggable(Level.FINE)) {
            logger.fine("No conversion necessary for SelectOne Component  " + uiSelectOne.getId() + " since the new value is null ");
         }

         return null;
      } else {
         Object convertedValue = super.getConvertedValue(context, uiSelectOne, newValue);
         if (logger.isLoggable(Level.FINE)) {
            logger.fine("SelectOne Component  " + uiSelectOne.getId() + " convertedValue " + convertedValue);
         }

         return convertedValue;
      }
   }

   public void decode(FacesContext context, UIComponent component) {
      this.rendererParamsNotNull(context, component);
      if (this.shouldDecode(component)) {
         String clientId = component.getClientId(context);

         assert clientId != null;

         Map requestParameterValuesMap;
         if (component instanceof UISelectMany) {
            requestParameterValuesMap = context.getExternalContext().getRequestParameterValuesMap();
            if (requestParameterValuesMap.containsKey(clientId)) {
               String[] newValues = (String[])requestParameterValuesMap.get(clientId);
               this.setSubmittedValue(component, newValues);
               if (logger.isLoggable(Level.FINE)) {
                  logger.fine("submitted values for UISelectMany component " + component.getId() + " after decoding " + Arrays.toString(newValues));
               }
            } else {
               this.setSubmittedValue(component, new String[0]);
               if (logger.isLoggable(Level.FINE)) {
                  logger.fine("Set empty array for UISelectMany component " + component.getId() + " after decoding ");
               }
            }
         } else {
            requestParameterValuesMap = context.getExternalContext().getRequestParameterMap();
            if (requestParameterValuesMap.containsKey(clientId)) {
               String newValue = (String)requestParameterValuesMap.get(clientId);
               this.setSubmittedValue(component, newValue);
               if (logger.isLoggable(Level.FINE)) {
                  logger.fine("submitted value for UISelectOne component " + component.getId() + " after decoding " + newValue);
               }
            } else {
               this.setSubmittedValue(component, RIConstants.NO_VALUE);
            }
         }

      }
   }

   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
   }

   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncode(component)) {
         this.renderSelect(context, component);
      }
   }

   public Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue) throws ConverterException {
      if (component instanceof UISelectMany) {
         RequestStateManager.set(context, "com.sun.faces.ComponentForValue", component);
         return this.convertSelectManyValue(context, (UISelectMany)component, (String[])((String[])submittedValue));
      } else {
         return this.convertSelectOneValue(context, (UISelectOne)component, (String)submittedValue);
      }
   }

   protected Object convertSelectManyValuesForModel(FacesContext context, UISelectMany uiSelectMany, Class modelType, String[] newValues) {
      Object result = null;
      if (modelType.isArray()) {
         result = this.convertSelectManyValues(context, uiSelectMany, modelType, newValues);
      } else if (List.class.isAssignableFrom(modelType)) {
         Object[] values = (Object[])((Object[])this.convertSelectManyValues(context, uiSelectMany, Object[].class, newValues));
         List l = new ArrayList(values.length);
         Object[] arr$ = values;
         int len$ = values.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Object v = arr$[i$];
            l.add(v);
         }

         result = l;
      }

      return result;
   }

   protected Object convertSelectManyValues(FacesContext context, UISelectMany uiSelectMany, Class arrayClass, String[] newValues) throws ConverterException {
      int len = null != newValues ? newValues.length : 0;
      Class elementType = arrayClass.getComponentType();
      if (elementType.equals(String.class)) {
         return newValues;
      } else {
         Object result;
         try {
            result = Array.newInstance(elementType, len);
         } catch (Exception var11) {
            throw new ConverterException(var11);
         }

         if (null == newValues) {
            return result;
         } else {
            Converter converter;
            if (null == (converter = uiSelectMany.getConverter()) && null == (converter = Util.getConverterForClass(elementType, context))) {
               if (elementType.equals(Object.class)) {
                  return newValues;
               } else {
                  StringBuffer valueStr = new StringBuffer();

                  for(int i = 0; i < len; ++i) {
                     if (i == 0) {
                        valueStr.append(newValues[i]);
                     } else {
                        valueStr.append(' ').append(newValues[i]);
                     }
                  }

                  Object[] params = new Object[]{valueStr.toString(), "null Converter"};
                  throw new ConverterException(MessageUtils.getExceptionMessage("com.sun.faces.TYPECONVERSION_ERROR", params));
               }
            } else {
               assert null != result;

               int i;
               if (elementType.isPrimitive()) {
                  for(i = 0; i < len; ++i) {
                     if (elementType.equals(Boolean.TYPE)) {
                        Array.setBoolean(result, i, (Boolean)converter.getAsObject(context, uiSelectMany, newValues[i]));
                     } else if (elementType.equals(Byte.TYPE)) {
                        Array.setByte(result, i, (Byte)converter.getAsObject(context, uiSelectMany, newValues[i]));
                     } else if (elementType.equals(Double.TYPE)) {
                        Array.setDouble(result, i, (Double)converter.getAsObject(context, uiSelectMany, newValues[i]));
                     } else if (elementType.equals(Float.TYPE)) {
                        Array.setFloat(result, i, (Float)converter.getAsObject(context, uiSelectMany, newValues[i]));
                     } else if (elementType.equals(Integer.TYPE)) {
                        Array.setInt(result, i, (Integer)converter.getAsObject(context, uiSelectMany, newValues[i]));
                     } else if (elementType.equals(Character.TYPE)) {
                        Array.setChar(result, i, (Character)converter.getAsObject(context, uiSelectMany, newValues[i]));
                     } else if (elementType.equals(Short.TYPE)) {
                        Array.setShort(result, i, (Short)converter.getAsObject(context, uiSelectMany, newValues[i]));
                     } else if (elementType.equals(Long.TYPE)) {
                        Array.setLong(result, i, (Long)converter.getAsObject(context, uiSelectMany, newValues[i]));
                     }
                  }
               } else {
                  for(i = 0; i < len; ++i) {
                     if (logger.isLoggable(Level.FINE)) {
                        Object converted = converter.getAsObject(context, uiSelectMany, newValues[i]);
                        logger.fine("String value: " + newValues[i] + " converts to : " + converted);
                     }

                     Array.set(result, i, converter.getAsObject(context, uiSelectMany, newValues[i]));
                  }
               }

               return result;
            }
         }
      }
   }

   protected void renderOption(FacesContext context, UIComponent component, Converter converter, SelectItem curItem, Object currentSelections, Object[] submittedValues, HtmlBasicRenderer.OptionComponentInfo optionInfo) throws IOException {
      ResponseWriter writer = context.getResponseWriter();

      assert writer != null;

      writer.writeText("\t", component, (String)null);
      writer.startElement("option", component);
      String valueString = this.getFormattedValue(context, component, curItem.getValue(), converter);
      writer.writeAttribute("value", valueString, "value");
      Object valuesArray;
      Object itemValue;
      if (submittedValues != null) {
         boolean containsValue = this.containsaValue(submittedValues);
         if (containsValue) {
            valuesArray = submittedValues;
            itemValue = valueString;
         } else {
            valuesArray = currentSelections;
            itemValue = curItem.getValue();
         }
      } else {
         valuesArray = currentSelections;
         itemValue = curItem.getValue();
      }

      if (this.isSelected(context, component, itemValue, valuesArray, converter)) {
         writer.writeAttribute("selected", true, "selected");
      }

      if (!optionInfo.isDisabled() && curItem.isDisabled()) {
         writer.writeAttribute("disabled", true, "disabled");
      }

      String labelClass;
      if (!optionInfo.isDisabled() && !curItem.isDisabled()) {
         labelClass = optionInfo.getEnabledClass();
      } else {
         labelClass = optionInfo.getDisabledClass();
      }

      if (labelClass != null) {
         writer.writeAttribute("class", labelClass, "labelClass");
      }

      if (curItem.isEscape()) {
         String label = curItem.getLabel();
         if (label == null) {
            label = valueString;
         }

         writer.writeText(label, component, "label");
      } else {
         writer.write(curItem.getLabel());
      }

      writer.endElement("option");
      writer.writeText("\n", component, (String)null);
   }

   protected void writeDefaultSize(ResponseWriter writer, int itemCount) throws IOException {
      writer.writeAttribute("size", "1", "size");
   }

   protected boolean containsaValue(Object valueArray) {
      if (null != valueArray) {
         int len = Array.getLength(valueArray);

         for(int i = 0; i < len; ++i) {
            Object value = Array.get(valueArray, i);
            if (value != null && !value.equals(RIConstants.NO_VALUE)) {
               return true;
            }
         }
      }

      return false;
   }

   protected Object getCurrentSelectedValues(UIComponent component) {
      Object value;
      if (component instanceof UISelectMany) {
         UISelectMany select = (UISelectMany)component;
         value = select.getValue();
         if (value instanceof Collection) {
            return ((Collection)value).toArray();
         } else {
            if (value != null && !value.getClass().isArray()) {
               logger.warning("The UISelectMany value should be an array or a collection type, the actual type is " + value.getClass().getName());
            }

            return value;
         }
      } else {
         UISelectOne select = (UISelectOne)component;
         value = select.getValue();
         return value != null ? new Object[]{value} : null;
      }
   }

   protected String getMultipleText(UIComponent component) {
      return component instanceof UISelectMany ? " multiple " : "";
   }

   protected int getOptionNumber(List selectItems) {
      int itemCount = 0;
      if (!selectItems.isEmpty()) {
         Iterator items = selectItems.iterator();

         while(items.hasNext()) {
            ++itemCount;
            SelectItem item = (SelectItem)items.next();
            if (item instanceof SelectItemGroup) {
               int optionsLength = ((SelectItemGroup)item).getSelectItems().length;
               itemCount += optionsLength;
            }
         }
      }

      return itemCount;
   }

   protected Object[] getSubmittedSelectedValues(UIComponent component) {
      if (component instanceof UISelectMany) {
         UISelectMany select = (UISelectMany)component;
         return (Object[])((Object[])select.getSubmittedValue());
      } else {
         UISelectOne select = (UISelectOne)component;
         Object val = select.getSubmittedValue();
         return val != null ? new Object[]{val} : null;
      }
   }

   protected boolean isSelected(FacesContext context, UIComponent component, Object itemValue, Object valueArray, Converter converter) {
      if (itemValue == null && valueArray == null) {
         return true;
      } else {
         if (null != valueArray) {
            if (!valueArray.getClass().isArray()) {
               logger.warning("valueArray is not an array, the actual type is " + valueArray.getClass());
               return valueArray.equals(itemValue);
            }

            int len = Array.getLength(valueArray);

            for(int i = 0; i < len; ++i) {
               Object value = Array.get(valueArray, i);
               if (value == null && itemValue == null) {
                  return true;
               }

               if (!(value == null ^ itemValue == null)) {
                  Object compareValue;
                  if (converter == null) {
                     compareValue = this.coerceToModelType(context, itemValue, value.getClass());
                  } else {
                     compareValue = itemValue;
                     if (itemValue instanceof String && !(value instanceof String)) {
                        compareValue = converter.getAsObject(context, component, (String)itemValue);
                     }
                  }

                  if (value.equals(compareValue)) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   protected void renderOptions(FacesContext context, UIComponent component, List items) throws IOException {
      ResponseWriter writer = context.getResponseWriter();

      assert writer != null;

      Converter converter = null;
      if (component instanceof ValueHolder) {
         converter = ((ValueHolder)component).getConverter();
      }

      if (!items.isEmpty()) {
         Object currentSelections = this.getCurrentSelectedValues(component);
         Object[] submittedValues = this.getSubmittedSelectedValues(component);
         RequestStateManager.set(context, "com.sun.faces.ComponentForValue", component);
         Map attributes = component.getAttributes();
         HtmlBasicRenderer.OptionComponentInfo optionInfo = new HtmlBasicRenderer.OptionComponentInfo((String)attributes.get("disabledClass"), (String)attributes.get("enabledClass"), Util.componentIsDisabled(component));
         Iterator i$ = items.iterator();

         while(true) {
            while(i$.hasNext()) {
               SelectItem item = (SelectItem)i$.next();
               if (item instanceof SelectItemGroup) {
                  writer.startElement("optgroup", component);
                  writer.writeAttribute("label", item.getLabel(), "label");
                  boolean componentDisabled = Boolean.TRUE.equals(component.getAttributes().get("disabled"));
                  if (!componentDisabled && item.isDisabled()) {
                     writer.writeAttribute("disabled", true, "disabled");
                  }

                  SelectItem[] itemsArray = ((SelectItemGroup)item).getSelectItems();

                  for(int i = 0; i < itemsArray.length; ++i) {
                     this.renderOption(context, component, converter, itemsArray[i], currentSelections, submittedValues, optionInfo);
                  }

                  writer.endElement("optgroup");
               } else {
                  this.renderOption(context, component, converter, item, currentSelections, submittedValues, optionInfo);
               }
            }

            return;
         }
      }
   }

   protected void renderSelect(FacesContext context, UIComponent component) throws IOException {
      ResponseWriter writer = context.getResponseWriter();

      assert writer != null;

      if (logger.isLoggable(Level.FINER)) {
         logger.log(Level.FINER, "Rendering 'select'");
      }

      writer.startElement("select", component);
      this.writeIdAttributeIfNecessary(context, writer, component);
      writer.writeAttribute("name", component.getClientId(context), "clientId");
      String styleClass;
      if (null != (styleClass = (String)component.getAttributes().get("styleClass"))) {
         writer.writeAttribute("class", styleClass, "styleClass");
      }

      if (!this.getMultipleText(component).equals("")) {
         writer.writeAttribute("multiple", true, "multiple");
      }

      List items = RenderKitUtils.getSelectItems(context, component);
      int itemCount = this.getOptionNumber(items);
      if (logger.isLoggable(Level.FINE)) {
         logger.fine("Rendering " + itemCount + " options");
      }

      Integer size = (Integer)component.getAttributes().get("size");
      if (size == null || size == Integer.MIN_VALUE) {
         size = itemCount;
      }

      this.writeDefaultSize(writer, size);
      RenderKitUtils.renderPassThruAttributes(writer, component, ATTRIBUTES);
      RenderKitUtils.renderXHTMLStyleBooleanAttributes(writer, component);
      this.renderOptions(context, component, items);
      writer.endElement("select");
   }

   protected Object coerceToModelType(FacesContext ctx, Object value, Class itemValueType) {
      Object newValue;
      try {
         ExpressionFactory ef = ctx.getApplication().getExpressionFactory();
         newValue = ef.coerceToType(value, itemValueType);
      } catch (ELException var6) {
         newValue = value;
      } catch (IllegalArgumentException var7) {
         newValue = value;
      }

      return newValue;
   }

   static {
      ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.SELECTMANYMENU);
   }
}
