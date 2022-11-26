package weblogic.servlet.http2.hpack;

public class HeaderEntry {
   protected static final String NULL_STRING = new String("");
   protected static final byte[] NULL_VALUE = new byte[0];
   private String name;
   private byte[] value;
   private boolean useIndexing;
   private boolean checkNameOnly;

   public HeaderEntry(String name, byte[] value) {
      this(name, value, false);
   }

   public HeaderEntry(String name, byte[] value, boolean useIndexing) {
      this.useIndexing = false;
      this.checkNameOnly = false;
      if (name == null) {
         this.name = NULL_STRING;
      } else {
         this.name = name;
      }

      if (value == null) {
         this.value = NULL_VALUE;
      } else {
         this.value = value;
      }

      this.useIndexing = useIndexing;
   }

   public String getName() {
      return this.name;
   }

   public byte[] getValue() {
      return this.value;
   }

   public boolean isUseIndexing() {
      return this.useIndexing;
   }

   public void setCheckNameOnly(boolean checkNameOnly) {
      this.checkNameOnly = checkNameOnly;
   }

   public void setUseIndexing(boolean useIndexing) {
      this.useIndexing = useIndexing;
   }

   int size() {
      return this.name.length() + this.value.length + 32;
   }

   public String toString() {
      return "HeaderEntry [name=" + this.name + ", value=" + new String(this.value) + ", useIndexing=" + this.useIndexing + ", checkNameOnly=" + this.checkNameOnly + "]";
   }
}
