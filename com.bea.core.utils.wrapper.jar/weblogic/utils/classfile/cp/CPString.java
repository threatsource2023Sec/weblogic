package weblogic.utils.classfile.cp;

import weblogic.utils.classfile.DoubleKey;

public class CPString extends CPInfo {
   public CPUtf8 utf8;

   public CPString() {
      this.setTag(8);
   }

   public void init(Object o) {
      DoubleKey key = (DoubleKey)o;
      this.utf8 = (CPUtf8)key.two;
   }

   public String toString() {
      return this.utf8.toString();
   }
}
