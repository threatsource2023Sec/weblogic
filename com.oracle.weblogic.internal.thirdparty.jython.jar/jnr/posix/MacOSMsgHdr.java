package jnr.posix;

import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.StructLayout;

class MacOSMsgHdr extends BaseMsgHdr {
   private static final Layout layout = new Layout(Runtime.getSystemRuntime());

   protected MacOSMsgHdr(NativePOSIX posix) {
      super(posix, layout);
      this.setName((String)null);
   }

   CmsgHdr allocateCmsgHdrInternal(NativePOSIX posix, Pointer pointer, int len) {
      return len > 0 ? new MacOSCmsgHdr(posix, pointer, len) : new MacOSCmsgHdr(posix, pointer);
   }

   void setControlPointer(Pointer control) {
      layout.msg_control.set(this.memory, control);
   }

   void setControlLen(int len) {
      layout.msg_controllen.set(this.memory, (long)len);
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("msghdr {\n");
      buf.append("  msg_name=").append(this.getName()).append(",\n");
      buf.append("  msg_namelen=").append(this.getNameLen()).append(",\n");
      buf.append("  msg_iov=[\n");
      Pointer iovp = layout.msg_iov.get(this.memory);
      int numIov = this.getIovLen();

      for(int i = 0; i < numIov; ++i) {
         Pointer eachp = iovp.slice((long)(i * BaseIovec.layout.size()));
         buf.append((new BaseIovec(this.posix, eachp)).toString("    "));
         if (i < numIov - 1) {
            buf.append(",\n");
         } else {
            buf.append("\n");
         }
      }

      buf.append("  ],\n");
      buf.append("  msg_control=[\n");
      CmsgHdr[] controls = this.getControls();

      for(int i = 0; i < controls.length; ++i) {
         buf.append(((MacOSCmsgHdr)controls[i]).toString("    "));
         if (i < controls.length - 1) {
            buf.append(",\n");
         } else {
            buf.append("\n");
         }
      }

      buf.append("  ],\n");
      buf.append("  msg_controllen=").append(layout.msg_controllen.get(this.memory)).append("\n");
      buf.append("  msg_iovlen=").append(this.getIovLen()).append(",\n");
      buf.append("  msg_flags=").append(this.getFlags()).append(",\n");
      buf.append("}");
      return buf.toString();
   }

   void setNamePointer(Pointer name) {
      layout.msg_name.set(this.memory, name);
   }

   Pointer getNamePointer() {
      return layout.msg_name.get(this.memory);
   }

   void setNameLen(int len) {
      layout.msg_namelen.set(this.memory, (long)len);
   }

   int getNameLen() {
      return (int)layout.msg_namelen.get(this.memory);
   }

   void setIovPointer(Pointer iov) {
      layout.msg_iov.set(this.memory, iov);
   }

   Pointer getIovPointer() {
      return layout.msg_iov.get(this.memory);
   }

   void setIovLen(int len) {
      layout.msg_iovlen.set(this.memory, len);
   }

   int getIovLen() {
      return layout.msg_iovlen.get(this.memory);
   }

   Pointer getControlPointer() {
      return layout.msg_control.get(this.memory);
   }

   public int getControlLen() {
      return (int)layout.msg_controllen.get(this.memory);
   }

   public void setFlags(int flags) {
      layout.msg_flags.set(this.memory, flags);
   }

   public int getFlags() {
      return layout.msg_flags.get(this.memory);
   }

   public static class Layout extends StructLayout {
      public final StructLayout.Pointer msg_name = new StructLayout.Pointer();
      public final StructLayout.socklen_t msg_namelen = new StructLayout.socklen_t();
      public final StructLayout.Pointer msg_iov = new StructLayout.Pointer();
      public final StructLayout.Signed32 msg_iovlen = new StructLayout.Signed32();
      public final StructLayout.Pointer msg_control = new StructLayout.Pointer();
      public final StructLayout.socklen_t msg_controllen = new StructLayout.socklen_t();
      public final StructLayout.Signed32 msg_flags = new StructLayout.Signed32();

      protected Layout(Runtime runtime) {
         super(runtime);
      }
   }
}
