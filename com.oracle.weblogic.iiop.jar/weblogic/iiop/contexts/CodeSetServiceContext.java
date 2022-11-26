package weblogic.iiop.contexts;

import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;

public final class CodeSetServiceContext extends ServiceContext {
   private int charCodeSet;
   private int wcharCodeSet;

   public CodeSetServiceContext(int charCodeSet, int wcharCodeSet) {
      super(1);
      this.charCodeSet = charCodeSet;
      this.wcharCodeSet = wcharCodeSet;
   }

   public CodeSetServiceContext(CorbaInputStream in) {
      super(1);
      this.readEncapsulatedContext(in);
   }

   public int getCharCodeSet() {
      return this.charCodeSet;
   }

   public int getWcharCodeSet() {
      return this.wcharCodeSet;
   }

   public void write(CorbaOutputStream out) {
      this.writeEncapsulatedContext(out);
   }

   protected void readEncapsulation(CorbaInputStream in) {
      this.charCodeSet = in.read_ulong();
      this.wcharCodeSet = in.read_ulong();
   }

   protected void writeEncapsulation(CorbaOutputStream out) {
      out.write_ulong(this.charCodeSet);
      out.write_ulong(this.wcharCodeSet);
   }

   public boolean equals(Object o) {
      return this == o || o != null && this.getClass().equals(o.getClass()) && this.equals((CodeSetServiceContext)o);
   }

   private boolean equals(CodeSetServiceContext other) {
      return this.charCodeSet == other.charCodeSet && this.wcharCodeSet == other.wcharCodeSet;
   }

   public int hashCode() {
      int result = this.charCodeSet;
      result = 31 * result + this.wcharCodeSet;
      return result;
   }

   public String toString() {
      return "CodeSet Context (char_data = " + Integer.toHexString(this.charCodeSet) + ", wchar_data = " + Integer.toHexString(this.wcharCodeSet) + ")";
   }
}
