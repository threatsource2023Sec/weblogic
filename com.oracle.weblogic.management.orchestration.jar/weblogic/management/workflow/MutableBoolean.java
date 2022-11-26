package weblogic.management.workflow;

import java.io.Serializable;

public class MutableBoolean implements Serializable {
   private static final long serialVersionUID = 311692325810959396L;
   private Boolean value;

   public MutableBoolean() {
      this.value = null;
   }

   public MutableBoolean(Boolean value) {
      this.value = value;
   }

   public void setValue(Boolean value) {
      this.value = value;
   }

   public Boolean getValue() {
      return this.value;
   }
}
