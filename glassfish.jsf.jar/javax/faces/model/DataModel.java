package javax.faces.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class DataModel implements Iterable {
   private static final DataModelListener[] EMPTY_DATA_MODEL_LISTENER = new DataModelListener[0];
   private List listeners = null;

   public abstract boolean isRowAvailable();

   public abstract int getRowCount();

   public abstract Object getRowData();

   public abstract int getRowIndex();

   public abstract void setRowIndex(int var1);

   public abstract Object getWrappedData();

   public abstract void setWrappedData(Object var1);

   public void addDataModelListener(DataModelListener listener) {
      if (listener == null) {
         throw new NullPointerException();
      } else {
         if (this.listeners == null) {
            this.listeners = new ArrayList();
         }

         this.listeners.add(listener);
      }
   }

   public DataModelListener[] getDataModelListeners() {
      return this.listeners == null ? EMPTY_DATA_MODEL_LISTENER : (DataModelListener[])this.listeners.toArray(new DataModelListener[this.listeners.size()]);
   }

   public void removeDataModelListener(DataModelListener listener) {
      if (listener == null) {
         throw new NullPointerException();
      } else {
         if (this.listeners != null) {
            this.listeners.remove(listener);
            if (this.listeners.isEmpty()) {
               this.listeners = null;
            }
         }

      }
   }

   public Iterator iterator() {
      return new DataModelIterator(this);
   }

   private static final class DataModelIterator implements Iterator {
      private DataModel model;
      private int index;

      DataModelIterator(DataModel model) {
         this.model = model;
         this.model.setRowIndex(this.index);
      }

      public boolean hasNext() {
         return this.model.isRowAvailable();
      }

      public Object next() {
         if (!this.model.isRowAvailable()) {
            throw new NoSuchElementException();
         } else {
            Object o = this.model.getRowData();
            this.model.setRowIndex(++this.index);
            return o;
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
}
