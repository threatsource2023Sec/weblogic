package weblogic.connector.utils;

public class ObjectPair {
   private final Object first;
   private final Object second;

   public ObjectPair(Object first, Object second) {
      this.first = first;
      this.second = second;
   }

   public Object getFirst() {
      return this.first;
   }

   public String toString() {
      return "ObjectPair [first=" + this.first + ", second=" + this.second + "]";
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + (this.first == null ? 0 : this.first.hashCode());
      result = 31 * result + (this.second == null ? 0 : this.second.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         ObjectPair other = (ObjectPair)obj;
         if (this.first == null) {
            if (other.first != null) {
               return false;
            }
         } else if (!this.first.equals(other.first)) {
            return false;
         }

         if (this.second == null) {
            if (other.second != null) {
               return false;
            }
         } else if (!this.second.equals(other.second)) {
            return false;
         }

         return true;
      }
   }

   public Object getSecond() {
      return this.second;
   }
}
