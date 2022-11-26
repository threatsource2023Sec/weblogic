package weblogic.utils.classfile.cp;

public abstract class CPInfo implements ConstantPoolTags {
   private int index = -1;
   private int tag;

   public int getIndex() {
      return this.index;
   }

   public void setIndex(int i) {
      this.index = i;
   }

   protected void setTag(int i) {
      this.tag = i;
   }

   public int getTag() {
      return this.tag;
   }

   public abstract void init(Object var1);
}
