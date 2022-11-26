package javax.faces.model;

import java.io.Serializable;

public class SelectItem implements Serializable {
   private static final long serialVersionUID = 876782311414654999L;
   private String description;
   private boolean disabled;
   private String label;
   private Object value;
   private boolean escape;

   public SelectItem() {
      this.description = null;
      this.disabled = false;
      this.label = null;
      this.value = null;
   }

   public SelectItem(Object value) {
      this(value, value == null ? null : value.toString(), (String)null, false, true);
   }

   public SelectItem(Object value, String label) {
      this(value, label, (String)null, false, true);
   }

   public SelectItem(Object value, String label, String description) {
      this(value, label, description, false, true);
   }

   public SelectItem(Object value, String label, String description, boolean disabled) {
      this(value, label, description, disabled, true);
   }

   public SelectItem(Object value, String label, String description, boolean disabled, boolean escape) {
      this.description = null;
      this.disabled = false;
      this.label = null;
      this.value = null;
      this.setValue(value);
      this.setLabel(label);
      this.setDescription(description);
      this.setDisabled(disabled);
      this.setEscape(escape);
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public boolean isDisabled() {
      return this.disabled;
   }

   public void setDisabled(boolean disabled) {
      this.disabled = disabled;
   }

   public String getLabel() {
      return this.label;
   }

   public void setLabel(String label) {
      this.label = label;
   }

   public Object getValue() {
      return this.value;
   }

   public void setValue(Object value) {
      this.value = value;
   }

   public boolean isEscape() {
      return this.escape;
   }

   public void setEscape(boolean escape) {
      this.escape = escape;
   }
}
