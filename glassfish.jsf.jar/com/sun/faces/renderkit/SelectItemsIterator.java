package com.sun.faces.renderkit;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

public final class SelectItemsIterator implements Iterator {
   private ComponentAwareSelectItemIterator items;
   private ListIterator kids;
   private SingleElementIterator singleItemIterator;
   private FacesContext ctx;

   public SelectItemsIterator(FacesContext ctx, UIComponent parent) {
      this.kids = parent.getChildren().listIterator();
      this.ctx = ctx;
   }

   public boolean hasNext() {
      if (this.items != null) {
         if (this.items.hasNext()) {
            return true;
         }

         this.items = null;
      }

      for(Object next = this.findNextValidChild(); next != null; next = this.findNextValidChild()) {
         this.initializeItems(next);
         if (this.items != null) {
            return true;
         }
      }

      return false;
   }

   public SelectItem next() {
      if (!this.hasNext()) {
         throw new NoSuchElementException();
      } else {
         return this.items != null ? (SelectItem)this.items.next() : this.next();
      }
   }

   public UIComponent currentSelectComponent() {
      UIComponent result = this.items.currentSelectComponent();
      return result;
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }

   private void initializeItems(Object kid) {
      if (kid instanceof UISelectItem) {
         UISelectItem ui = (UISelectItem)kid;
         SelectItem item = (SelectItem)ui.getValue();
         if (item == null) {
            item = new SelectItem(ui.getItemValue(), ui.getItemLabel(), ui.getItemDescription(), ui.isItemDisabled(), ui.isItemEscaped(), ui.isNoSelectionOption());
         }

         this.updateSingeItemIterator(ui, item);
         this.items = this.singleItemIterator;
      } else if (kid instanceof UISelectItems) {
         UISelectItems ui = (UISelectItems)kid;
         Object value = ui.getValue();
         if (value != null) {
            if (value instanceof SelectItem) {
               this.updateSingeItemIterator(ui, (SelectItem)value);
               this.items = this.singleItemIterator;
            } else if (value.getClass().isArray()) {
               this.items = new ArrayIterator(this.ctx, (UISelectItems)kid, value);
            } else if (value instanceof Iterable) {
               this.items = new IterableItemIterator(this.ctx, (UISelectItems)kid, (Iterable)value);
            } else {
               if (!(value instanceof Map)) {
                  throw new IllegalArgumentException();
               }

               this.items = new MapIterator((Map)value, ui.getParent());
            }
         }

         if (this.items != null && !this.items.hasNext()) {
            this.items = null;
         }
      }

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

   private void updateSingeItemIterator(UIComponent selectComponent, SelectItem item) {
      if (this.singleItemIterator == null) {
         this.singleItemIterator = new SingleElementIterator();
      }

      this.singleItemIterator.updateItem(selectComponent, item);
   }

   private static final class IterableItemIterator extends GenericObjectSelectItemIterator {
      private FacesContext ctx;
      private Iterator iterator;

      private IterableItemIterator(FacesContext ctx, UISelectItems sourceComponent, Iterable iterable) {
         super(sourceComponent);
         this.ctx = ctx;
         this.iterator = iterable.iterator();
      }

      public boolean hasNext() {
         return this.iterator.hasNext();
      }

      public SelectItem next() {
         Object item = this.iterator.next();
         return item instanceof SelectItem ? (SelectItem)item : this.getSelectItemFor(this.ctx, item);
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      // $FF: synthetic method
      IterableItemIterator(FacesContext x0, UISelectItems x1, Iterable x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   private static final class ArrayIterator extends GenericObjectSelectItemIterator {
      private FacesContext ctx;
      private Object array;
      private int count;
      private int index;

      private ArrayIterator(FacesContext ctx, UISelectItems sourceComponent, Object array) {
         super(sourceComponent);
         this.ctx = ctx;
         this.array = array;
         this.count = Array.getLength(array);
      }

      public boolean hasNext() {
         return this.index < this.count;
      }

      public SelectItem next() {
         if (this.index >= this.count) {
            throw new NoSuchElementException();
         } else {
            Object item = Array.get(this.array, this.index++);
            return item instanceof SelectItem ? (SelectItem)item : this.getSelectItemFor(this.ctx, item);
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      // $FF: synthetic method
      ArrayIterator(FacesContext x0, UISelectItems x1, Object x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   private interface ComponentAwareSelectItemIterator extends Iterator {
      UIComponent currentSelectComponent();
   }

   private abstract static class GenericObjectSelectItemIterator implements ComponentAwareSelectItemIterator {
      private GenericObjectSelectItem genericObjectSI;
      protected transient UISelectItems sourceComponent;

      protected GenericObjectSelectItemIterator(UISelectItems sourceComponent) {
         this.sourceComponent = sourceComponent;
      }

      public UIComponent currentSelectComponent() {
         return this.sourceComponent;
      }

      protected SelectItem getSelectItemFor(FacesContext ctx, Object value) {
         if (this.genericObjectSI == null) {
            this.genericObjectSI = new GenericObjectSelectItem(this.sourceComponent);
         }

         this.genericObjectSI.updateItem(ctx, value);
         return this.genericObjectSI;
      }

      private static final class GenericObjectSelectItem extends SelectItem {
         private static final String VAR = "var";
         private static final String ITEM_VALUE = "itemValue";
         private static final String ITEM_LABEL = "itemLabel";
         private static final String ITEM_DESCRIPTION = "itemDescription";
         private static final String ITEM_ESCAPED = "itemLabelEscaped";
         private static final String ITEM_DISABLED = "itemDisabled";
         private static final String NO_SELECTION_OPTION = "noSelectionOption";
         private static final String NO_SELECTION_VALUE = "noSelectionValue";
         private String var;
         private UISelectItems sourceComponent;

         private GenericObjectSelectItem(UISelectItems sourceComponent) {
            this.var = (String)sourceComponent.getAttributes().get("var");
            this.sourceComponent = sourceComponent;
         }

         private void updateItem(FacesContext ctx, Object value) {
            Map reqMap = ctx.getExternalContext().getRequestMap();
            Object oldVarValue = null;
            if (this.var != null) {
               oldVarValue = reqMap.put(this.var, value);
            }

            try {
               Map attrs = this.sourceComponent.getAttributes();
               Object itemValueResult = attrs.get("itemValue");
               Object itemLabelResult = attrs.get("itemLabel");
               Object itemDescriptionResult = attrs.get("itemDescription");
               Object itemEscapedResult = attrs.get("itemLabelEscaped");
               Object itemDisabledResult = attrs.get("itemDisabled");
               Object noSelectionValueResult = attrs.get("noSelectionValue");
               Object noSelectionOptionResult = attrs.get("noSelectionOption");
               this.setValue(itemValueResult != null ? itemValueResult : value);
               this.setLabel(itemLabelResult != null ? itemLabelResult.toString() : value.toString());
               this.setDescription(itemDescriptionResult != null ? itemDescriptionResult.toString() : null);
               this.setEscape(itemEscapedResult != null ? Boolean.valueOf(itemEscapedResult.toString()) : true);
               this.setDisabled(itemDisabledResult != null ? Boolean.valueOf(itemDisabledResult.toString()) : false);
               if (null != noSelectionOptionResult) {
                  this.setNoSelectionOption(Boolean.valueOf(noSelectionOptionResult.toString()));
               } else if (null != noSelectionValueResult) {
                  this.setNoSelectionOption(this.getValue().equals(noSelectionValueResult));
               }
            } finally {
               if (this.var != null) {
                  if (oldVarValue != null) {
                     reqMap.put(this.var, oldVarValue);
                  } else {
                     reqMap.remove(this.var);
                  }
               }

            }

         }

         private void writeObject(ObjectOutputStream out) throws IOException {
            throw new NotSerializableException();
         }

         private void readObject(ObjectInputStream in) throws IOException {
            throw new NotSerializableException();
         }

         // $FF: synthetic method
         GenericObjectSelectItem(UISelectItems x0, Object x1) {
            this(x0);
         }
      }
   }

   private static final class MapIterator implements ComponentAwareSelectItemIterator {
      private SelectItem item;
      private Iterator iterator;
      private transient UIComponent parent;

      private MapIterator(Map map, UIComponent parent) {
         this.item = new SelectItem();
         this.iterator = map.entrySet().iterator();
         this.parent = parent;
      }

      public UIComponent currentSelectComponent() {
         return this.parent;
      }

      public boolean hasNext() {
         return this.iterator.hasNext();
      }

      public SelectItem next() {
         Map.Entry entry = (Map.Entry)this.iterator.next();
         Object key = entry.getKey();
         Object value = entry.getValue();
         this.item.setLabel(key != null ? key.toString() : value.toString());
         this.item.setValue(value != null ? value : "");
         return this.item;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      // $FF: synthetic method
      MapIterator(Map x0, UIComponent x1, Object x2) {
         this(x0, x1);
      }
   }

   private static final class SingleElementIterator implements ComponentAwareSelectItemIterator {
      private SelectItem item;
      private transient UIComponent selectComponent;
      private boolean nextCalled;

      private SingleElementIterator() {
      }

      public UIComponent currentSelectComponent() {
         return this.selectComponent;
      }

      public boolean hasNext() {
         return !this.nextCalled;
      }

      public SelectItem next() {
         if (this.nextCalled) {
            throw new NoSuchElementException();
         } else {
            this.nextCalled = true;
            return this.item;
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      private void updateItem(UIComponent selectComponent, SelectItem item) {
         this.item = item;
         this.selectComponent = selectComponent;
         this.nextCalled = false;
      }

      // $FF: synthetic method
      SingleElementIterator(Object x0) {
         this();
      }
   }
}
