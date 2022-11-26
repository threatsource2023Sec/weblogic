package weblogic.workarea;

import java.io.IOException;
import java.io.Serializable;

public class LongWorkContext implements PrimitiveWorkContext, Serializable {
   private long longValue;

   public LongWorkContext() {
   }

   LongWorkContext(long l) {
      this.longValue = l;
   }

   public Object get() {
      return new Long(this.longValue());
   }

   public String toString() {
      return "" + this.longValue;
   }

   public int hashCode() {
      return (new Long(this.longValue)).hashCode();
   }

   public boolean equals(Object obj) {
      if (obj instanceof LongWorkContext) {
         return ((LongWorkContext)obj).longValue == this.longValue;
      } else {
         return false;
      }
   }

   public long longValue() {
      return this.longValue;
   }

   public void writeContext(WorkContextOutput out) throws IOException {
      out.writeLong(this.longValue);
   }

   public void readContext(WorkContextInput in) throws IOException {
      this.longValue = in.readLong();
   }
}
