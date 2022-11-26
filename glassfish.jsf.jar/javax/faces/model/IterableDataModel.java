package javax.faces.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class IterableDataModel extends DataModel {
   private int index;
   private Iterable iterable;
   private List list;

   public IterableDataModel() {
      this((Iterable)null);
   }

   public IterableDataModel(Iterable iterable) {
      this.index = -1;
      this.setWrappedData(iterable);
   }

   public boolean isRowAvailable() {
      return this.list != null && this.index >= 0 && this.index < this.list.size();
   }

   public int getRowCount() {
      return this.list == null ? -1 : this.list.size();
   }

   public Object getRowData() {
      if (this.list == null) {
         return null;
      } else if (!this.isRowAvailable()) {
         throw new IllegalArgumentException();
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
         int oldRowIndex = this.index;
         this.index = rowIndex;
         if (this.list != null) {
            this.notifyListeners(oldRowIndex, rowIndex);
         }
      }
   }

   public Object getWrappedData() {
      return this.iterable;
   }

   public void setWrappedData(Object data) {
      if (data == null) {
         this.iterable = null;
         this.list = null;
         this.setRowIndex(-1);
      } else {
         this.iterable = (Iterable)data;
         this.list = iterableToList(this.iterable);
         this.setRowIndex(0);
      }

   }

   private Object getRowDataOrNull() {
      return this.isRowAvailable() ? this.getRowData() : null;
   }

   private void notifyListeners(int oldRowIndex, int rowIndex) {
      DataModelListener[] dataModelListeners = this.getDataModelListeners();
      if (oldRowIndex != rowIndex && dataModelListeners != null) {
         DataModelEvent dataModelEvent = new DataModelEvent(this, rowIndex, this.getRowDataOrNull());
         DataModelListener[] var5 = dataModelListeners;
         int var6 = dataModelListeners.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            DataModelListener dataModelListener = var5[var7];
            if (dataModelListener != null) {
               dataModelListener.rowSelected(dataModelEvent);
            }
         }
      }

   }

   private static List iterableToList(Iterable iterable) {
      List list = null;
      if (iterable instanceof List) {
         list = (List)iterable;
      } else if (iterable instanceof Collection) {
         list = new ArrayList((Collection)iterable);
      } else {
         list = new ArrayList();
         Iterator iterator = iterable.iterator();

         while(iterator.hasNext()) {
            ((List)list).add(iterator.next());
         }
      }

      return (List)list;
   }
}
