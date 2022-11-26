package javax.faces.model;

import java.util.ArrayList;
import java.util.List;

public abstract class DataModel {
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
}
