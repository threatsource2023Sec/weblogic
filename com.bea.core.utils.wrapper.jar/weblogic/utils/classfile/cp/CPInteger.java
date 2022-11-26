package weblogic.utils.classfile.cp;

public class CPInteger extends CPInfo {
   public int value;

   public CPInteger() {
      this.setTag(3);
   }

   public void init(Object o) {
      this.value = (Integer)o;
   }
}
