package weblogic.socket;

import java.io.IOException;

final class PosixSocketInfo extends NativeSocketInfo {
   static final int FD_NOT_READY = 0;
   static final int FD_READY = 1;
   static final int FD_ERROR = 2;
   static final int FD_CLIENT_ERROR = 3;
   FdStruct fdStruct;

   protected String fieldsToString() {
      StringBuffer sb = new StringBuffer(200);
      sb.append(super.fieldsToString()).append(", ").append("fdStruct = ").append(this.fdStruct);
      return sb.toString();
   }

   PosixSocketInfo(MuxableSocket ms) throws IOException {
      super(ms);
      this.fdStruct = new FdStruct(this.fd, this);
   }

   static class FdStruct {
      int fd;
      int status = 0;
      int revents = 0;
      PosixSocketInfo info;

      FdStruct(int fd, PosixSocketInfo info) {
         this.fd = fd;
         this.info = info;
      }

      public String toString() {
         return "PosixSocketInfo.FdStruct[fd=" + this.fd + ", status=" + this.status + "]";
      }
   }
}
