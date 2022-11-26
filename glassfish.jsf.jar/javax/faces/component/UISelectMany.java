package javax.faces.component;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.ValueBinding;

public class UISelectMany extends UIInput {
   public static final String COMPONENT_TYPE = "javax.faces.SelectMany";
   public static final String COMPONENT_FAMILY = "javax.faces.SelectMany";
   public static final String INVALID_MESSAGE_ID = "javax.faces.component.UISelectMany.INVALID";

   public UISelectMany() {
      this.setRendererType("javax.faces.Listbox");
   }

   public String getFamily() {
      return "javax.faces.SelectMany";
   }

   public Object[] getSelectedValues() {
      return (Object[])((Object[])this.getValue());
   }

   public void setSelectedValues(Object[] selectedValues) {
      this.setValue(selectedValues);
   }

   /** @deprecated */
   public ValueBinding getValueBinding(String name) {
      return "selectedValues".equals(name) ? super.getValueBinding("value") : super.getValueBinding(name);
   }

   /** @deprecated */
   public void setValueBinding(String name, ValueBinding binding) {
      if ("selectedValues".equals(name)) {
         super.setValueBinding("value", binding);
      } else {
         super.setValueBinding(name, binding);
      }

   }

   public ValueExpression getValueExpression(String name) {
      return "selectedValues".equals(name) ? super.getValueExpression("value") : super.getValueExpression(name);
   }

   public void setValueExpression(String name, ValueExpression binding) {
      if ("selectedValues".equals(name)) {
         super.setValueExpression("value", binding);
      } else {
         super.setValueExpression(name, binding);
      }

   }

   protected boolean compareValues(Object previous, Object value) {
      if (previous == null && value != null) {
         return true;
      } else if (previous != null && value == null) {
         return true;
      } else if (previous == null) {
         return false;
      } else {
         boolean valueChanged = false;
         if (!(previous instanceof Object[])) {
            previous = toObjectArray(previous);
         }

         if (!(value instanceof Object[])) {
            value = toObjectArray(value);
         }

         if (previous instanceof Object[] && value instanceof Object[]) {
            Object[] oldarray = (Object[])((Object[])previous);
            Object[] newarray = (Object[])((Object[])value);
            if (oldarray.length != newarray.length) {
               return true;
            } else {
               for(int i = 0; i < oldarray.length; ++i) {
                  int count1 = countElementOccurrence(oldarray[i], oldarray);
                  int count2 = countElementOccurrence(oldarray[i], newarray);
                  if (count1 != count2) {
                     valueChanged = true;
                     break;
                  }
               }

               return valueChanged;
            }
         } else {
            return false;
         }
      }
   }

   private static int countElementOccurrence(Object element, Object[] array) {
      int count = 0;

      for(int i = 0; i < array.length; ++i) {
         Object arrayElement = array[i];
         if (arrayElement != null && element != null && arrayElement.equals(element)) {
            ++count;
         }
      }

      return count;
   }

   private static Object[] toObjectArray(Object primitiveArray) {
      if (primitiveArray == null) {
         throw new NullPointerException();
      } else if (primitiveArray instanceof Object[]) {
         return (Object[])((Object[])primitiveArray);
      } else if (primitiveArray instanceof Collection) {
         return ((Collection)primitiveArray).toArray();
      } else {
         Class clazz = primitiveArray.getClass();
         if (!clazz.isArray()) {
            return null;
         } else {
            int length = Array.getLength(primitiveArray);
            Object[] array = new Object[length];

            for(int i = 0; i < length; ++i) {
               array[i] = Array.get(primitiveArray, i);
            }

            return array;
         }
      }
   }

   protected void validateValue(FacesContext context, Object value) {
      super.validateValue(context, value);
      if (this.isValid() && value != null) {
         boolean doAddMessage = false;
         Converter converter = this.getConverter();
         Iterator i = this.getValuesIterator(value);

         SelectItemsIterator items;
         Object currentValue;
         while(i.hasNext()) {
            items = new SelectItemsIterator(context, this);
            currentValue = i.next();
            if (!SelectUtils.matchValue(context, this, currentValue, items, converter)) {
               doAddMessage = true;
               break;
            }
         }

         if (this.isRequired()) {
            i = this.getValuesIterator(value);

            while(i.hasNext()) {
               items = new SelectItemsIterator(context, this);
               currentValue = i.next();
               if (SelectUtils.valueIsNoSelectionOption(context, this, currentValue, items, converter)) {
                  doAddMessage = true;
                  break;
               }
            }
         }

         if (doAddMessage) {
            FacesMessage message = MessageFactory.getMessage(context, "javax.faces.component.UISelectMany.INVALID", MessageFactory.getLabel(context, this));
            context.addMessage(this.getClientId(context), message);
            this.setValid(false);
         }

      }
   }

   private Iterator getValuesIterator(Object value) {
      return (Iterator)(value instanceof Collection ? ((Collection)value).iterator() : new ArrayIterator(value));
   }

   private static final class ArrayIterator implements Iterator {
      private int length;
      private int idx = 0;
      private Object value;

      ArrayIterator(Object value) {
         this.value = value;
         this.length = Array.getLength(value);
      }

      public boolean hasNext() {
         return this.idx < this.length;
      }

      public Object next() {
         if (this.idx >= this.length) {
            throw new NoSuchElementException();
         } else {
            return Array.get(this.value, this.idx++);
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
}
