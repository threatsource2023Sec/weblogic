package weblogic.store.io;

import java.nio.ByteBuffer;

public class IORecord {
   private int handle;
   private int typeCode;
   private ByteBuffer data;

   public IORecord() {
   }

   public IORecord(int handle, int typeCode, ByteBuffer data) {
      this.handle = handle;
      this.typeCode = typeCode;
      this.data = data;
   }

   public final int getHandle() {
      return this.handle;
   }

   public final void setHandle(int handle) {
      this.handle = handle;
   }

   public final int getTypeCode() {
      return this.typeCode;
   }

   public final void setTypeCode(int typeCode) {
      this.typeCode = typeCode;
   }

   public final ByteBuffer getData() {
      return this.data;
   }
}
