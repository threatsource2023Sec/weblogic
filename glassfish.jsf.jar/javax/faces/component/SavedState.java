package javax.faces.component;

import java.io.Serializable;

class SavedState implements Serializable {
   private static final long serialVersionUID = 2920252657338389849L;
   private Object submittedValue;
   private boolean submitted;
   private boolean valid = true;
   private Object value;
   private boolean localValueSet;

   Object getSubmittedValue() {
      return this.submittedValue;
   }

   void setSubmittedValue(Object submittedValue) {
      this.submittedValue = submittedValue;
   }

   boolean isValid() {
      return this.valid;
   }

   void setValid(boolean valid) {
      this.valid = valid;
   }

   Object getValue() {
      return this.value;
   }

   public void setValue(Object value) {
      this.value = value;
   }

   boolean isLocalValueSet() {
      return this.localValueSet;
   }

   public void setLocalValueSet(boolean localValueSet) {
      this.localValueSet = localValueSet;
   }

   public boolean getSubmitted() {
      return this.submitted;
   }

   public void setSubmitted(boolean submitted) {
      this.submitted = submitted;
   }

   public boolean hasDeltaState() {
      return this.submittedValue != null || this.value != null || this.localValueSet || !this.valid || this.submitted;
   }

   public String toString() {
      return "submittedValue: " + this.submittedValue + " value: " + this.value + " localValueSet: " + this.localValueSet;
   }
}
