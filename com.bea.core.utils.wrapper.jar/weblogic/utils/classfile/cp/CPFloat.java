package weblogic.utils.classfile.cp;

public class CPFloat extends CPInfo {
   public float value;

   public CPFloat() {
      this.setTag(4);
   }

   public void init(Object o) {
      this.value = (Float)o;
   }
}
