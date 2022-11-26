package weblogic.workarea;

import java.io.IOException;
import java.io.Serializable;

public class AsciiWorkContext implements PrimitiveWorkContext, Serializable {
   private String str;

   public AsciiWorkContext() {
   }

   AsciiWorkContext(String str) {
      this.str = str;
   }

   public String toString() {
      return this.str;
   }

   public Object get() {
      return this.str;
   }

   public int hashCode() {
      return this.str == null ? 0 : this.str.hashCode();
   }

   public boolean equals(Object obj) {
      return obj instanceof AsciiWorkContext ? ((AsciiWorkContext)obj).str.equals(this.str) : false;
   }

   public void writeContext(WorkContextOutput out) throws IOException {
      out.writeASCII(this.str);
   }

   public void readContext(WorkContextInput in) throws IOException {
      this.str = in.readASCII();
   }
}
