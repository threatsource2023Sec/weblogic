package weblogic.workarea;

import java.io.IOException;
import java.io.Serializable;

public class StringWorkContext implements PrimitiveWorkContext, Serializable {
   private String str;

   public StringWorkContext() {
   }

   StringWorkContext(String str) {
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
      return obj instanceof StringWorkContext ? ((StringWorkContext)obj).str.equals(this.str) : false;
   }

   public void writeContext(WorkContextOutput out) throws IOException {
      out.writeUTF(this.str);
   }

   public void readContext(WorkContextInput in) throws IOException {
      this.str = in.readUTF();
   }
}
