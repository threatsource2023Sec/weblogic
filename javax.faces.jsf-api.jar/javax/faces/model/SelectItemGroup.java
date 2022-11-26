package javax.faces.model;

public class SelectItemGroup extends SelectItem {
   private SelectItem[] selectItems = null;

   public SelectItemGroup() {
   }

   public SelectItemGroup(String label) {
      super("", label);
   }

   public SelectItemGroup(String label, String description, boolean disabled, SelectItem[] selectItems) {
      super("", label, description, disabled);
      this.setSelectItems(selectItems);
   }

   public SelectItem[] getSelectItems() {
      return this.selectItems;
   }

   public void setSelectItems(SelectItem[] selectItems) {
      if (selectItems == null) {
         throw new NullPointerException();
      } else {
         this.selectItems = selectItems;
      }
   }
}
