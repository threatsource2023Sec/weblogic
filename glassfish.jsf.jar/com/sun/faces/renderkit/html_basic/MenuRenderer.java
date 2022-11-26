package com.sun.faces.renderkit.html_basic;

import com.sun.faces.io.FastStringWriter;
import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.renderkit.SelectItemsIterator;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.ReflectionUtils;
import com.sun.faces.util.RequestStateManager;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.stream.Collectors;
import javax.el.ELException;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.FacesException;
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
   private static final Attribute[] ATTRIBUTES;

   public Object convertSelectManyValue(FacesContext context, UISelectMany uiSelectMany, String[] newValues) throws ConverterException {
      ValueExpression valueExpression = uiSelectMany.getValueExpression("value");
      Object convertedValue = newValues;
      if (valueExpression != null) {
         Class modelType = valueExpression.getType(context.getELContext());
         if (modelType != null) {
            convertedValue = this.convertSelectManyValuesForModel(context, uiSelectMany, modelType, newValues);
         }

         if (convertedValue == null) {
            Object value = valueExpression.getValue(context.getELContext());
            if (value != null) {
               convertedValue = this.convertSelectManyValuesForModel(context, uiSelectMany, value.getClass(), newValues);
            }
         }

         if (convertedValue == null) {
            Object[] params = new Object[]{newValues == null ? "" : Arrays.stream(newValues).collect(Collectors.joining("")), valueExpression.getExpressionString()};
            throw new ConverterException(MessageUtils.getExceptionMessage("com.sun.faces.TYPECONVERSION_ERROR", params));
         }
      } else {
         convertedValue = this.convertSelectManyValuesForArray(context, uiSelectMany, Object.class, newValues);
      }

      if (logger.isLoggable(Level.FINE)) {
         logger.fine("SelectMany Component  " + uiSelectMany.getId() + " convertedValues " + convertedValue);
      }

      return convertedValue;
   }

   public Object convertSelectOneValue(FacesContext context, UISelectOne uiSelectOne, String newValue) throws ConverterException {
      if (this.isNoValueOrNull(newValue, uiSelectOne)) {
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
         String clientId = this.decodeBehaviors(context, component);
         if (clientId == null) {
            clientId = component.getClientId(context);
         }

         if (component instanceof UISelectMany) {
            this.decodeUISelectMany(context, (UISelectMany)component, clientId);
         } else {
            this.decodeUISelectOne(context, component, clientId);
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
      if (modelType.isArray()) {
         return this.convertSelectManyValuesForArray(context, uiSelectMany, modelType.getComponentType(), newValues);
      } else if (Collection.class.isAssignableFrom(modelType)) {
         return this.convertSelectManyValuesForCollection(context, uiSelectMany, modelType, newValues);
      } else if (Object.class.equals(modelType)) {
         return this.convertSelectManyValuesForArray(context, uiSelectMany, modelType, newValues);
      } else {
         throw new FacesException("Target model Type is no a Collection or Array");
      }
   }

   protected Object convertSelectManyValuesForArray(FacesContext context, UISelectMany uiSelectMany, Class elementType, String[] newValues) throws ConverterException {
      int length = newValues != null ? newValues.length : 0;
      if (elementType.equals(String.class)) {
         return newValues;
      } else {
         Object array;
         try {
            array = Array.newInstance(elementType, length);
         } catch (Exception var10) {
            throw new ConverterException(var10);
         }

         if (newValues == null) {
            return array;
         } else {
            Converter converter = uiSelectMany.getConverter();
            if (converter == null) {
               converter = Util.getConverterForClass(elementType, context);
               if (converter == null) {
                  if (elementType.equals(Object.class)) {
                     return newValues;
                  }

                  Object[] params = new Object[]{Arrays.stream(newValues).collect(Collectors.joining(" ")), "null Converter"};
                  throw new ConverterException(MessageUtils.getExceptionMessage("com.sun.faces.TYPECONVERSION_ERROR", params));
               }
            }

            for(int i = 0; i < length; ++i) {
               Object converted = converter.getAsObject(context, uiSelectMany, newValues[i]);
               Array.set(array, i, converted);
               if (!elementType.isPrimitive() && logger.isLoggable(Level.FINE)) {
                  logger.fine("String value: " + newValues[i] + " converts to : " + converted);
               }
            }

            return array;
         }
      }
   }

   protected Collection convertSelectManyValuesForCollection(FacesContext context, UISelectMany uiSelectMany, Class collectionType, String[] newValues) {
      Collection collection = null;
      int length = null != newValues ? newValues.length : 0;
      Object collectionTypeHint = uiSelectMany.getAttributes().get("collectionType");
      if (collectionTypeHint != null) {
         collection = this.createCollectionFromHint(collectionTypeHint);
      } else {
         Collection currentValue = (Collection)uiSelectMany.getValue();
         if (currentValue != null) {
            collection = this.cloneValue(currentValue);
         }

         if (collection == null) {
            collection = this.createCollection(currentValue, collectionType);
         }

         if (collection == null) {
            collection = this.bestGuess(collectionType, length);
         }
      }

      if (newValues == null) {
         return collection;
      } else {
         Converter converter = uiSelectMany.getConverter();
         String newValue;
         if (converter != null) {
            String[] var17 = newValues;
            int var10 = newValues.length;

            for(int var11 = 0; var11 < var10; ++var11) {
               newValue = var17[var11];
               Object converted = converter.getAsObject(context, uiSelectMany, newValue);
               if (logger.isLoggable(Level.FINE)) {
                  logger.fine("String value: " + newValue + " converts to : " + converted);
               }

               collection.add(converted);
            }
         } else {
            SelectItemsIterator iterator = new SelectItemsIterator(context, uiSelectMany);
            Map availableItems = new HashMap();

            while(true) {
               int var24;
               while(iterator.hasNext()) {
                  SelectItem item = iterator.next();
                  if (item instanceof SelectItemGroup) {
                     SelectItem[] var22 = ((SelectItemGroup)item).getSelectItems();
                     var24 = var22.length;

                     for(int var14 = 0; var14 < var24; ++var14) {
                        SelectItem groupItem = var22[var14];
                        String asString = this.getFormattedValue(context, uiSelectMany, groupItem.getValue());
                        availableItems.put(asString, groupItem.getValue());
                     }
                  } else {
                     newValue = this.getFormattedValue(context, uiSelectMany, item.getValue());
                     availableItems.put(newValue, item.getValue());
                  }
               }

               String[] var21 = newValues;
               int var23 = newValues.length;

               for(var24 = 0; var24 < var23; ++var24) {
                  String newValue = var21[var24];
                  collection.add(availableItems.containsKey(newValue) ? availableItems.get(newValue) : newValue);
               }
               break;
            }
         }

         return collection;
      }
   }

   protected boolean renderOption(FacesContext context, UIComponent component, UIComponent selectComponent, Converter converter, SelectItem curItem, Object currentSelections, Object[] submittedValues, HtmlBasicRenderer.OptionComponentInfo optionInfo) throws IOException {
      String valueString = this.getFormattedValue(context, component, curItem.getValue(), converter);
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

      boolean isSelected = this.isSelected(context, component, itemValue, valuesArray, converter);
      if (optionInfo.isHideNoSelection() && curItem.isNoSelectionOption() && currentSelections != null && !isSelected) {
         return false;
      } else {
         ResponseWriter writer = context.getResponseWriter();

         assert writer != null;

         writer.writeText("\t", component, (String)null);
         writer.startElement("option", null != selectComponent ? selectComponent : component);
         writer.writeAttribute("value", valueString, "value");
         if (isSelected) {
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
         return true;
      }
   }

   protected void writeDefaultSize(ResponseWriter writer, int itemCount) throws IOException {
      writer.writeAttribute("size", "1", "size");
   }

   protected boolean containsaValue(Object valueArray) {
      if (valueArray != null) {
         int len = Array.getLength(valueArray);

         for(int i = 0; i < len; ++i) {
            Object value = Array.get(valueArray, i);
            if (value != null && !value.equals("")) {
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
         if (value == null) {
            return null;
         } else if (value instanceof Collection) {
            return ((Collection)value).toArray();
         } else {
            if (value.getClass().isArray()) {
               if (Array.getLength(value) == 0) {
                  return null;
               }
            } else if (!value.getClass().isArray()) {
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
      if (Util.isAllNull(itemValue, valueArray)) {
         return true;
      } else {
         if (valueArray != null) {
            if (!valueArray.getClass().isArray()) {
               logger.warning("valueArray is not an array, the actual type is " + valueArray.getClass());
               return valueArray.equals(itemValue);
            }

            int len = Array.getLength(valueArray);

            for(int i = 0; i < len; ++i) {
               Object value = Array.get(valueArray, i);
               if (Util.isAllNull(itemValue, value)) {
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

   protected int renderOptions(FacesContext context, UIComponent component, SelectItemsIterator items) throws IOException {
      ResponseWriter writer = context.getResponseWriter();
      Converter converter = null;
      if (component instanceof ValueHolder) {
         converter = ((ValueHolder)component).getConverter();
      }

      int count = 0;
      Object currentSelections = this.getCurrentSelectedValues(component);
      Object[] submittedValues = this.getSubmittedSelectedValues(component);
      HtmlBasicRenderer.OptionComponentInfo optionInfo = new HtmlBasicRenderer.OptionComponentInfo(component);
      RequestStateManager.set(context, "com.sun.faces.ComponentForValue", component);

      while(true) {
         while(items.hasNext()) {
            SelectItem item = items.next();
            UIComponent selectComponent = items.currentSelectComponent();
            if (item instanceof SelectItemGroup) {
               writer.startElement("optgroup", null != selectComponent ? selectComponent : component);
               writer.writeAttribute("label", item.getLabel(), "label");
               if (!optionInfo.isDisabled() && item.isDisabled()) {
                  writer.writeAttribute("disabled", true, "disabled");
               }

               ++count;
               SelectItem[] itemsArray = ((SelectItemGroup)item).getSelectItems();
               SelectItem[] var13 = itemsArray;
               int var14 = itemsArray.length;

               for(int var15 = 0; var15 < var14; ++var15) {
                  SelectItem element = var13[var15];
                  if (this.renderOption(context, component, selectComponent, converter, element, currentSelections, submittedValues, optionInfo)) {
                     ++count;
                  }
               }

               writer.endElement("optgroup");
            } else if (this.renderOption(context, component, selectComponent, converter, item, currentSelections, submittedValues, optionInfo)) {
               ++count;
            }
         }

         return count;
      }
   }

   protected void renderSelect(FacesContext context, UIComponent component) throws IOException {
      ResponseWriter writer = context.getResponseWriter();
      if (logger.isLoggable(Level.FINER)) {
         logger.log(Level.FINER, "Rendering 'select'");
      }

      writer.startElement("select", component);
      this.writeIdAttributeIfNecessary(context, writer, component);
      writer.writeAttribute("name", component.getClientId(context), "clientId");
      String styleClass;
      if ((styleClass = (String)component.getAttributes().get("styleClass")) != null) {
         writer.writeAttribute("class", styleClass, "styleClass");
      }

      if (!this.getMultipleText(component).equals("")) {
         writer.writeAttribute("multiple", true, "multiple");
      }

      SelectItemsIterator items = RenderKitUtils.getSelectItems(context, component);
      FastStringWriter bufferedWriter = new FastStringWriter(128);
      context.setResponseWriter(writer.cloneWithWriter(bufferedWriter));
      int count = this.renderOptions(context, component, items);
      context.setResponseWriter(writer);
      Integer size = (Integer)component.getAttributes().get("size");
      if (size == null || size == Integer.MIN_VALUE) {
         size = count;
      }

      this.writeDefaultSize(writer, size);
      RenderKitUtils.renderPassThruAttributes(context, writer, component, ATTRIBUTES, getNonOnChangeBehaviors(component));
      RenderKitUtils.renderXHTMLStyleBooleanAttributes(writer, component);
      RenderKitUtils.renderOnchange(context, component, false);
      writer.write(bufferedWriter.toString());
      writer.endElement("select");
   }

   protected Object coerceToModelType(FacesContext ctx, Object value, Class itemValueType) {
      Object newValue;
      try {
         ExpressionFactory ef = ctx.getApplication().getExpressionFactory();
         newValue = ef.coerceToType(value, itemValueType);
      } catch (IllegalArgumentException | ELException var6) {
         newValue = value;
      }

      return newValue;
   }

   protected Collection createCollection(Collection collection, Class fallBackType) {
      Class lookupClass = collection != null ? collection.getClass() : fallBackType;
      if (!lookupClass.isInterface() && !Modifier.isAbstract(lookupClass.getModifiers())) {
         try {
            return (Collection)lookupClass.newInstance();
         } catch (IllegalAccessException | InstantiationException var5) {
            if (logger.isLoggable(Level.SEVERE)) {
               logger.log(Level.SEVERE, "Unable to create new Collection instance for type " + lookupClass.getName(), var5);
            }
         }
      }

      return null;
   }

   protected Collection cloneValue(Object value) {
      if (value instanceof Cloneable) {
         Method cloneMethod = ReflectionUtils.lookupMethod(value, "clone");
         if (cloneMethod != null) {
            try {
               Collection clonedCollected = (Collection)cloneMethod.invoke(value);
               clonedCollected.clear();
               return clonedCollected;
            } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var4) {
               if (logger.isLoggable(Level.SEVERE)) {
                  logger.log(Level.SEVERE, "Unable to clone collection type: {0}", value.getClass().getName());
                  logger.log(Level.SEVERE, var4.toString(), var4);
               }
            }
         } else if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Type {0} implements Cloneable, but has no public clone method.", value.getClass().getName());
         }
      }

      return null;
   }

   protected Collection bestGuess(Class type, int initialSize) {
      if (SortedSet.class.isAssignableFrom(type)) {
         return new TreeSet();
      } else if (Queue.class.isAssignableFrom(type)) {
         return new LinkedList();
      } else {
         return (Collection)(Set.class.isAssignableFrom(type) ? new HashSet(initialSize) : new ArrayList(initialSize));
      }
   }

   protected Collection createCollectionFromHint(Object collectionTypeHint) {
      Class collectionType;
      if (collectionTypeHint instanceof Class) {
         collectionType = (Class)collectionTypeHint;
      } else {
         if (!(collectionTypeHint instanceof String)) {
            throw new FacesException("'collectionType' should resolve to type String or Class.  Found: " + collectionTypeHint.getClass().getName());
         }

         try {
            collectionType = Util.loadClass((String)collectionTypeHint, this);
         } catch (ClassNotFoundException var4) {
            throw new FacesException(var4);
         }
      }

      Collection createdCollection = this.createCollection((Collection)null, collectionType);
      if (createdCollection == null) {
         throw new FacesException("Unable to create collection type " + collectionType);
      } else {
         return createdCollection;
      }
   }

   protected static boolean isHideNoSelection(UIComponent component) {
      Object result = component.getAttributes().get("hideNoSelectionOption");
      return result != null ? (Boolean)result : false;
   }

   private void decodeUISelectMany(FacesContext context, UISelectMany component, String clientId) {
      Map requestParameterValuesMap = context.getExternalContext().getRequestParameterValuesMap();
      if (requestParameterValuesMap.containsKey(clientId)) {
         String[] newValues = (String[])requestParameterValuesMap.get(clientId);
         if (newValues != null && newValues.length > 0) {
            Set disabledSelectItemValues = this.getDisabledSelectItemValues(context, component);
            if (!disabledSelectItemValues.isEmpty()) {
               List newValuesList = new ArrayList(Arrays.asList(newValues));
               if (newValuesList.removeAll(disabledSelectItemValues)) {
                  newValues = (String[])newValuesList.toArray(new String[newValuesList.size()]);
               }
            }
         }

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

   }

   private void decodeUISelectOne(FacesContext context, UIComponent component, String clientId) {
      Map requestParameterMap = context.getExternalContext().getRequestParameterMap();
      if (requestParameterMap.containsKey(clientId)) {
         String newValue = (String)requestParameterMap.get(clientId);
         if (newValue != null && !newValue.isEmpty()) {
            Set disabledSelectItemValues = this.getDisabledSelectItemValues(context, component);
            if (disabledSelectItemValues.contains(newValue)) {
               newValue = "";
            }
         }

         this.setSubmittedValue(component, newValue);
         if (logger.isLoggable(Level.FINE)) {
            logger.fine("submitted value for UISelectOne component " + component.getId() + " after decoding " + newValue);
         }
      } else {
         this.setSubmittedValue(component, "");
      }

   }

   private Set getDisabledSelectItemValues(FacesContext context, UIComponent component) {
      Set disabledSelectItemValues = new HashSet(2);
      RenderKitUtils.getSelectItems(context, component).forEachRemaining((selectItem) -> {
         if (selectItem.isDisabled()) {
            disabledSelectItemValues.add(this.getFormattedValue(context, component, selectItem.getValue()));
         }

      });
      return disabledSelectItemValues;
   }

   private boolean isNoValueOrNull(String newValue, UIComponent component) {
      if ("".equals(newValue)) {
         return true;
      } else if (newValue == null) {
         if (logger.isLoggable(Level.FINE)) {
            logger.fine("No conversion necessary for SelectOne Component  " + component.getId() + " since the new value is null ");
         }

         return true;
      } else {
         return false;
      }
   }

   static {
      ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.SELECTMANYMENU);
   }
}
