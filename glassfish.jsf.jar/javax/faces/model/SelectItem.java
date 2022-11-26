package javax.faces.model;

import java.io.Serializable;

public class SelectItem implements Serializable {
   private static final long serialVersionUID = 876782311414654999L;
   private String description;
   private boolean disabled;
   private String label;
   private Object value;
   private boolean escape;
   private boolean noSelectionOption;

   public SelectItem() {
      this.description = null;
      this.disabled = false;
      this.label = null;
      this.value = null;
      this.noSelectionOption = false;
   }

   public SelectItem(Object value) {
      this(value, value == null ? null : value.toString(), (String)null, false, true, false);
   }

   public SelectItem(Object value, String label) {
      this(value, label, (String)null, false, true, false);
   }

   public SelectItem(Object value, String label, String description) {
      this(value, label, description, false, true, false);
   }

   public SelectItem(Object value, String label, String description, boolean disabled) {
      this(value, label, description, disabled, true, false);
   }

   public SelectItem(Object value, String label, String description, boolean disabled, boolean escape) {
      this(value, label, description, disabled, escape, false);
   }

   public SelectItem(Object value, String label, String description, boolean disabled, boolean escape, boolean noSelectionOption) {
      this.description = null;
      this.disabled = false;
      this.label = null;
      this.value = null;
      this.noSelectionOption = false;
      this.setValue(value);
      this.setLabel(label);
      this.setDescription(description);
      this.setDisabled(disabled);
      this.setEscape(escape);
      this.setNoSelectionOption(noSelectionOption);
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

   public boolean isNoSelectionOption() {
      return this.noSelectionOption;
   }

   public void setNoSelectionOption(boolean noSelectionOption) {
      this.noSelectionOption = noSelectionOption;
   }
}
