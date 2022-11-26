package weblogic.utils.classfile.cp;

public class CPDouble extends CPInfo {
   public double value;

   public CPDouble() {
      this.setTag(6);
   }

   public void init(Object o) {
      this.value = (Double)o;
   }
}
