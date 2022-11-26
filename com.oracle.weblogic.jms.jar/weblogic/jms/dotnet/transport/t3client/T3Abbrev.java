package weblogic.jms.dotnet.transport.t3client;

class T3Abbrev {
   private final int id;
   private final byte[] content;
   static final T3Abbrev[] NULL_ABBREVS;

   T3Abbrev(int id, T3JVMID jvmid) {
      this.id = id;
      if (jvmid != null) {
         this.content = jvmid.getBuf();
      } else {
         this.content = null;
      }

   }

   int getId() {
      return this.id;
   }

   byte[] getContent() {
      return this.content;
   }

   void write(MarshalWriterImpl output) {
      T3.writeLength(output, this.id);
      if (this.id > T3.PROTOCOL_ABBV_SIZE) {
         output.write(this.content, 0, this.content.length);
      }

   }

   int size() {
      int len = T3.getLengthNumBytes(this.id);
      if (this.id > T3.PROTOCOL_ABBV_SIZE) {
         len += this.content.length;
      }

      return len;
   }

   static {
      T3Abbrev[] abbrevs = new T3Abbrev[]{new T3Abbrev(255, (T3JVMID)null)};
      NULL_ABBREVS = abbrevs;
   }
}
