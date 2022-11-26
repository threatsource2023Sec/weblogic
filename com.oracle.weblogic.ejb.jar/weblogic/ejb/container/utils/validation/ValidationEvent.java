package weblogic.ejb.container.utils.validation;

import java.util.EventObject;

public final class ValidationEvent extends EventObject {
   private static final long serialVersionUID = 8541977237353603168L;
   protected boolean state;
   protected String errKey;
   protected String errText;

   public ValidationEvent(ValidatableWithNotify object, boolean state, String errKey, String errText) {
      super(object);
      this.state = state;
      this.errKey = errKey;
      this.errText = errText;
   }

   public boolean isValid() {
      return this.state;
   }

   public String getErrorKey() {
      return this.errKey;
   }

   public String getErrorText() {
      return this.errText;
   }

   public boolean equals(Object o) {
      if (o != null && o instanceof ValidationEvent) {
         ValidationEvent other = (ValidationEvent)o;
         return this.source.equals(other.source) && this.state == other.state && this.errKey.equals(other.errKey);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.source.hashCode() ^ (this.state ? Boolean.TRUE.hashCode() : Boolean.FALSE.hashCode()) ^ this.errText.hashCode();
   }

   public String toString() {
      return "ValidationEvent: obj = " + this.source.getClass().getName() + "[" + System.identityHashCode(this.source) + "], key = " + this.errKey + ", msg = " + this.errText + "\n";
   }
}
