package weblogic.management.provider.internal;

import java.util.List;

public final class DescriptorManagerHelperContext {
   private List errors;
   private boolean transform;
   private boolean editable;
   private boolean validate;
   private boolean transformed;
   private boolean eProductionModeEnabled;
   private boolean rProductionModeEnabled;

   public void setErrors(List errors) {
      this.errors = errors;
   }

   public List getErrors() {
      return this.errors;
   }

   public boolean isTransform() {
      return this.transform;
   }

   public void setTransform(boolean transform) {
      this.transform = transform;
   }

   public boolean isValidate() {
      return this.validate;
   }

   public void setValidate(boolean validate) {
      this.validate = validate;
   }

   public boolean isEditable() {
      return this.editable;
   }

   public void setEditable(boolean editable) {
      this.editable = editable;
   }

   public boolean isTransformed() {
      return this.transformed;
   }

   public void setTransformed(boolean transformed) {
      this.transformed = transformed;
   }

   public boolean isEProductionModeEnabled() {
      return this.eProductionModeEnabled;
   }

   public void setEProductionModeEnabled(boolean enabled) {
      this.eProductionModeEnabled = enabled;
   }

   public boolean isRProductionModeEnabled() {
      return this.rProductionModeEnabled;
   }

   public void setRProductionModeEnabled(boolean enabled) {
      this.rProductionModeEnabled = enabled;
   }
}
