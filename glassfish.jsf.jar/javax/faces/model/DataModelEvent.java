package javax.faces.model;

import java.util.EventObject;

public class DataModelEvent extends EventObject {
   private static final long serialVersionUID = -1822980374964965366L;
   private Object data = null;
   private int index = 0;

   public DataModelEvent(DataModel model, int index, Object data) {
      super(model);
      this.index = index;
      this.data = data;
   }

   public DataModel getDataModel() {
      return (DataModel)this.getSource();
   }

   public Object getRowData() {
      return this.data;
   }

   public int getRowIndex() {
      return this.index;
   }
}
