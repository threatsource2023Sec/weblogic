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

            Object itemValue = item.getValue();
            if (itemValue == null && value == null) {
               return true;
            }

            if (value == null ^ itemValue == null) {
               continue;
            }

            Object compareValue;
            if (converter == null) {
               compareValue = coerceToModelType(ctx, itemValue, value.getClass());
            } else {
               compareValue = itemValue;
               if (itemValue instanceof String && !(value instanceof String)) {
                  compareValue = converter.getAsObject(ctx, component, (String)itemValue);
               }
            }

            if (!value.equals(compareValue)) {
               continue;
            }

            return true;
         }

         return false;
      }
   }

   private static Object coerceToModelType(FacesContext ctx, Object value, Class toType) {
      Object newValue;
      try {
         ExpressionFactory ef = ctx.getApplication().getExpressionFactory();
         newValue = ef.coerceToType(value, toType);
      } catch (ELException var5) {
         newValue = value;
      } catch (IllegalArgumentException var6) {
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
