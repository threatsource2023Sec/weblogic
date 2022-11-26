package javax.faces.model;

import java.util.Collection;

public class CollectionDataModel extends DataModel {
   private int index;
   private Collection inner;
   private Object[] arrayFromInner;

   public CollectionDataModel() {
      this((Collection)null);
   }

   public CollectionDataModel(Collection collection) {
      this.index = -1;
      this.setWrappedData(collection);
   }

   public boolean isRowAvailable() {
      if (this.arrayFromInner == null) {
         return false;
      } else {
         return this.index >= 0 && this.index < this.arrayFromInner.length;
      }
   }

   public int getRowCount() {
      return this.arrayFromInner == null ? -1 : this.arrayFromInner.length;
   }

   public Object getRowData() {
      if (this.arrayFromInner == null) {
         return null;
      } else if (!this.isRowAvailable()) {
         throw new NoRowAvailableException();
      } else {
         return this.arrayFromInner[this.index];
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
         if (this.arrayFromInner != null) {
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
      return this.inner;
   }

   public void setWrappedData(Object data) {
      if (data == null) {
         this.inner = null;
         this.arrayFromInner = null;
         this.setRowIndex(-1);
      } else {
         this.inner = (Collection)data;
         this.arrayFromInner = (Object[])(new Object[this.inner.size()]);
         this.inner.toArray(this.arrayFromInner);
         this.setRowIndex(0);
      }

   }
}
