package javax.faces.model;

import java.util.SortedMap;
import javax.servlet.jsp.jstl.sql.Result;

public class ResultDataModel extends DataModel {
   private int index;
   private Result result;
   private SortedMap[] rows;

   public ResultDataModel() {
      this((Result)null);
   }

   public ResultDataModel(Result result) {
      this.index = -1;
      this.result = null;
      this.rows = null;
      this.setWrappedData(result);
   }

   public boolean isRowAvailable() {
      if (this.result == null) {
         return false;
      } else {
         return this.index >= 0 && this.index < this.rows.length;
      }
   }

   public int getRowCount() {
      return this.result == null ? -1 : this.rows.length;
   }

   public SortedMap getRowData() {
      if (this.result == null) {
         return null;
      } else if (!this.isRowAvailable()) {
         throw new NoRowAvailableException();
      } else {
         return this.rows[this.index];
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
         if (this.result != null) {
            DataModelListener[] listeners = this.getDataModelListeners();
            if (old != this.index && listeners != null) {
               SortedMap rowData = null;
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
      return this.result;
   }

   public void setWrappedData(Object data) {
      if (data == null) {
         this.result = null;
         this.rows = null;
         this.setRowIndex(-1);
      } else {
         this.result = (Result)data;
         this.rows = this.result.getRows();
         this.index = -1;
         this.setRowIndex(0);
      }

   }
}
