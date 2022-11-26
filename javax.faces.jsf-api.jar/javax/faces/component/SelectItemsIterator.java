package javax.faces.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.faces.model.SelectItem;

final class SelectItemsIterator implements Iterator {
   private Iterator items = null;
   private ListIterator kids = null;

   public SelectItemsIterator(UIComponent parent) {
      this.kids = parent.getChildren().listIterator();
   }

   public boolean hasNext() {
      if (this.items != null) {
         if (this.items.hasNext()) {
            return true;
         }

         this.items = null;
      }

      Object next = this.findNextValidChild();
      if (next != null) {
         this.kids.previous();
         return true;
      } else {
         return false;
      }
   }

   public SelectItem next() {
      if (!this.hasNext()) {
         throw new NoSuchElementException();
      } else if (this.items != null) {
         return (SelectItem)this.items.next();
      } else {
         UIComponent kid = (UIComponent)this.findNextValidChild();
         if (kid instanceof UISelectItem) {
            UISelectItem ui = (UISelectItem)kid;
            SelectItem item = (SelectItem)ui.getValue();
            if (item == null) {
               item = new SelectItem(ui.getItemValue(), ui.getItemLabel(), ui.getItemDescription(), ui.isItemDisabled(), ui.isItemEscaped());
            }

            return item;
         } else if (kid instanceof UISelectItems) {
            UISelectItems ui = (UISelectItems)kid;
            Object value = ui.getValue();
            if (value instanceof SelectItem) {
               return (SelectItem)value;
            } else if (value instanceof SelectItem[]) {
               this.items = Arrays.asList((SelectItem[])((SelectItem[])value)).iterator();
               return this.next();
            } else if (value instanceof Collection) {
               this.items = ((Collection)value).iterator();
               return this.next();
            } else if (value instanceof Map) {
               List list = new ArrayList(((Map)value).size());
               Iterator keys = ((Map)value).keySet().iterator();

               while(keys.hasNext()) {
                  Object key = keys.next();
                  if (key != null) {
                     Object val = ((Map)value).get(key);
                     if (val != null) {
                        list.add(new SelectItem(val, key.toString(), (String)null));
                     }
                  }
               }

               this.items = list.iterator();
               return this.next();
            } else {
               throw new IllegalArgumentException();
            }
         } else {
            throw new NoSuchElementException();
         }
      }
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }

   private Object findNextValidChild() {
      if (this.kids.hasNext()) {
         Object next = this.kids.next();

         while(true) {
            if (!this.kids.hasNext() || next instanceof UISelectItem || next instanceof UISelectItems) {
               if (next instanceof UISelectItem || next instanceof UISelectItems) {
                  return next;
               }
               break;
            }

            next = this.kids.next();
         }
      }

      return null;
   }
}
