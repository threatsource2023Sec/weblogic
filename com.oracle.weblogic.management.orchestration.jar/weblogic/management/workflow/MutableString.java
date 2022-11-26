package weblogic.management.workflow;

import java.io.Serializable;

public class MutableString implements Serializable {
   private static final long serialVersionUID = -8228760741140459899L;
   private String value;

   public MutableString() {
      this.value = null;
   }

   public MutableString(String value) {
      this.value = value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public String getValue() {
      return this.value;
   }

   public String toString() {
      return this.getValue();
   }
}
