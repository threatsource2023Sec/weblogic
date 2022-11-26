package javax.faces.model;

public class ArrayDataModel extends DataModel {
   private Object[] array;
   private int index;

   public ArrayDataModel() {
      this((Object[])null);
   }

   public ArrayDataModel(Object[] array) {
      this.index = -1;
      this.setWrappedData(array);
   }

   public boolean isRowAvailable() {
      if (this.array == null) {
         return false;
      } else {
         return this.index >= 0 && this.index < this.array.length;
      }
   }

   public int getRowCount() {
      return this.array == null ? -1 : this.array.length;
   }

   public Object getRowData() {
      if (this.array == null) {
         return null;
      } else if (!this.isRowAvailable()) {
         throw new NoRowAvailableException();
      } else {
         return this.array[this.index];
      }
   }

   public int getRowIndex() {
      return this.index;
   }

   public void setRowIndex(int rowIndex) {
      if (rowIndex < -1) {
         throw new IllegalArgumentException();
      } else {
         int old = this.index;
         this.index = rowIndex;
         if (this.array != null) {
            DataModelListener[] listeners = this.getDataModelListeners();
            if (old != this.index && listeners != null) {
               Object rowData = null;
               if (this.isRowAvailable()) {
                  rowData = this.getRowData();
               }

               DataModelEvent event = new DataModelEvent(this, this.index, rowData);
               int n = listeners.length;

               for(int i = 0; i < n; ++i) {
                  if (null != listeners[i]) {
                     listeners[i].rowSelected(event);
                  }
               }
            }

         }
      }
   }

   public Object getWrappedData() {
      return this.array;
   }

   public void setWrappedData(Object data) {
      if (data == null) {
         this.array = null;
         this.setRowIndex(-1);
      } else {
         this.array = (Object[])((Object[])data);
         this.index = -1;
         this.setRowIndex(0);
      }

   }
}
