package javax.faces.model;

public class ScalarDataModel extends DataModel {
   private int index;
   private Object scalar;

   public ScalarDataModel() {
      this((Object)null);
   }

   public ScalarDataModel(Object scalar) {
      this.setWrappedData(scalar);
   }

   public boolean isRowAvailable() {
      if (this.scalar == null) {
         return false;
      } else {
         return this.index == 0;
      }
   }

   public int getRowCount() {
      return this.scalar == null ? -1 : 1;
   }

   public Object getRowData() {
      if (this.scalar == null) {
         return null;
      } else if (!this.isRowAvailable()) {
         throw new NoRowAvailableException();
      } else {
         return this.scalar;
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
         if (this.scalar != null) {
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
      return this.scalar;
   }

   public void setWrappedData(Object data) {
      if (data == null) {
         this.scalar = null;
         this.setRowIndex(-1);
      } else {
         this.scalar = data;
         this.index = -1;
         this.setRowIndex(0);
      }

   }
}
