package weblogic.servlet.internal.dd;

public final class DescriptorError {
   String err = null;
   String msg = null;
   String type = null;

   public DescriptorError(String _err) {
      this.err = _err;
   }

   public DescriptorError(String _err, String _msg) {
      this.err = _err;
      this.msg = _msg;
   }

   public DescriptorError(String _err, String _msg, String _type) {
      this.err = _err;
      this.msg = _msg;
      this.type = _type;
   }

   public String getError() {
      return this.err;
   }

   public String toString() {
      String result = "DescriptorError(";
      if (this.err != null) {
         result = result + this.err;
      }

      if (this.msg != null) {
         result = result + "," + this.msg;
      }

      if (this.type != null) {
         result = result + "," + this.type;
      }

      result = result + ")";
      return result;
   }
}
