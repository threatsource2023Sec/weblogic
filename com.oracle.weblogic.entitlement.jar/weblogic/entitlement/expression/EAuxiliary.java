package weblogic.entitlement.expression;

public class EAuxiliary {
   private String auxValue;
   public static final String AUX_ID = "weblogic.entitlement.expression.EAuxiliary.ID";

   public EAuxiliary(String auxiliary) {
      this.auxValue = auxiliary;
   }

   public String toString() {
      return this.auxValue;
   }
}
