package weblogic.utils.classfile.cp;

public class CPUtf8 extends CPInfo {
   private String value;

   public CPUtf8() {
      this.setTag(1);
   }

   public String getValue() {
      return this.value;
   }

   public void setValue(String s) {
      this.value = s;
   }

   public void init(Object o) {
      this.value = (String)o;
   }

   public String toString() {
      return this.value;
   }

   public boolean equals(Object o) {
      return !(o instanceof CPUtf8) ? false : this.getValue().equals(((CPUtf8)o).getValue());
   }

   public int hashCode() {
      return this.getValue().hashCode();
   }
}
