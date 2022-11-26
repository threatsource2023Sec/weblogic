package weblogic.utils.classfile.cp;

public class CPLong extends CPInfo {
   public long value;

   public CPLong() {
      this.setTag(5);
   }

   public void init(Object o) {
      this.value = (Long)o;
   }
}
