package weblogic.wtc.jatmi;

public abstract class MBStringTypes extends StandardTypes implements TypedBuffer {
   private static final long serialVersionUID = -2883389108309083009L;
   protected String mbencoding = null;

   public MBStringTypes(String type, int index) {
      super(type, index);
   }

   public void setMBEncoding(String encoding) {
      encoding = MBEncoding.checkMBEncoding(encoding);
      this.mbencoding = encoding == null ? null : new String(encoding);
   }

   public String getMBEncoding() {
      return this.mbencoding;
   }
}
