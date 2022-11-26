package weblogic.utils.classfile;

public class DoubleKey {
   public Object one;
   public Object two;

   public DoubleKey() {
   }

   public DoubleKey(Object one, Object two) {
      this.one = one;
      this.two = two;
   }

   public int hashCode() {
      return this.one.hashCode() ^ this.two.hashCode();
   }

   public boolean equals(Object o) {
      if (!(o instanceof DoubleKey)) {
         return false;
      } else {
         DoubleKey other = (DoubleKey)o;
         return equal(this.one, other.one) && equal(this.two, other.two);
      }
   }

   private static final boolean equal(Object a, Object b) {
      if (a == null && b == null) {
         return true;
      } else {
         return a != null && b != null ? a.equals(b) : false;
      }
   }
}
