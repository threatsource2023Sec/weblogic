package weblogic.xml.util.xed;

public class StringConstant extends Variable {
   String value;

   public StringConstant(String value) {
      this.value = value;
   }

   public Object evaluate(Context context) throws StreamEditorException {
      return this.value;
   }

   public String toString() {
      return "'" + this.value + "'";
   }
}
