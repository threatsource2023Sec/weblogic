package javax.faces.component;

import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.el.ELException;
import javax.el.ExpressionFactory;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

class SelectUtils {
   private SelectUtils() {
   }

   static boolean matchValue(FacesContext ctx, UIComponent component, Object value, Iterator items, Converter converter) {
      while(true) {
         if (items.hasNext()) {
            SelectItem item = (SelectItem)items.next();
            if (item instanceof SelectItemGroup) {
               SelectItem[] subitems = ((SelectItemGroup)item).getSelectItems();
               if (subitems == null || subitems.length <= 0 || !matchValue(ctx, component, value, new ArrayIterator(subitems), converter)) {
                  continue;
               }

               return true;
            }

            Object compareValue = null;

            try {
               compareValue = doConversion(ctx, component, item, value, converter);
            } catch (IllegalStateException var8) {
               continue;
            }

            if (null == compareValue && null == value) {
               return true;
            }

            if (!value.equals(compareValue)) {
               continue;
            }

            return true;
         }

         return false;
      }
   }

   static boolean valueIsNoSelectionOption(FacesContext ctx, UIComponent component, Object value, Iterator items, Converter converter) {
      boolean result = false;

      while(items.hasNext()) {
         SelectItem item = (SelectItem)items.next();
         if (item instanceof SelectItemGroup) {
            SelectItem[] subitems = ((SelectItemGroup)item).getSelectItems();
            if (subitems != null && subitems.length > 0 && valueIsNoSelectionOption(ctx, component, value, new ArrayIterator(subitems), converter)) {
               result = true;
               break;
            }
         } else {
            Object compareValue = null;

            try {
               compareValue = doConversion(ctx, component, item, value, converter);
            } catch (IllegalStateException var9) {
               continue;
            }

            if (null == compareValue && null == value && item.isNoSelectionOption()) {
               result = true;
               break;
            }

            if (value.equals(compareValue) && item.isNoSelectionOption()) {
               result = true;
               break;
            }
         }
      }

      return result;
   }

   private static Object doConversion(FacesContext ctx, UIComponent component, SelectItem item, Object value, Converter converter) throws IllegalStateException {
      Object itemValue = item.getValue();
      if (itemValue == null && value == null) {
         return null;
      } else if (value == null ^ itemValue == null) {
         throw new IllegalStateException("Either value was null, or itemValue was null, but not both.");
      } else {
         Object compareValue;
         if (converter == null) {
            compareValue = coerceToModelType(ctx, itemValue, value.getClass());
         } else {
            compareValue = itemValue;
            if (itemValue instanceof String && !(value instanceof String)) {
               compareValue = converter.getAsObject(ctx, component, (String)itemValue);
            }
         }

         return compareValue;
      }
   }

   private static Object coerceToModelType(FacesContext ctx, Object value, Class toType) {
      Object newValue;
      try {
         ExpressionFactory ef = ctx.getApplication().getExpressionFactory();
         newValue = ef.coerceToType(value, toType);
      } catch (IllegalArgumentException | ELException var5) {
         newValue = value;
      }

      return newValue;
   }

   static class ArrayIterator implements Iterator {
      private Object[] items;
      private int index = 0;

      public ArrayIterator(Object[] items) {
         this.items = items;
      }

      public boolean hasNext() {
         return this.index < this.items.length;
      }

      public Object next() {
         try {
            return this.items[this.index++];
         } catch (IndexOutOfBoundsException var2) {
            throw new NoSuchElementException();
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
}
