package javax.faces.model;

import java.util.List;

public class ListDataModel extends DataModel {
   private int index;
   private List list;

   public ListDataModel() {
      this((List)null);
   }

   public ListDataModel(List list) {
      this.index = -1;
      this.setWrappedData(list);
   }

   public boolean isRowAvailable() {
      if (this.list == null) {
         return false;
      } else {
         return this.index >= 0 && this.index < this.list.size();
      }
   }

   public int getRowCount() {
      return this.list == null ? -1 : this.list.size();
   }

   public Object getRowData() {
      if (this.list == null) {
         return null;
      } else if (!this.isRowAvailable()) {
         throw new NoRowAvailableException();
      } else {
         return this.list.get(this.index);
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
         if (this.list != null) {
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
      return this.list;
   }

   public void setWrappedData(Object data) {
      if (data == null) {
         this.list = null;
         this.setRowIndex(-1);
      } else {
         this.list = (List)data;
         this.index = -1;
         this.setRowIndex(0);
      }

   }
}
