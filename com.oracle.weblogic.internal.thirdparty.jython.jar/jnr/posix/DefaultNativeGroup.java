package jnr.posix;

import java.util.ArrayList;
import java.util.List;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.StructLayout;

public final class DefaultNativeGroup extends NativeGroup implements Group {
   static final Layout layout = new Layout(Runtime.getSystemRuntime());
   private final Pointer memory;

   DefaultNativeGroup(Pointer memory) {
      super(memory.getRuntime(), layout);
      this.memory = memory;
   }

   public String getName() {
      return layout.gr_name.get(this.memory);
   }

   public String getPassword() {
      return layout.gr_passwd.get(this.memory);
   }

   public long getGID() {
      return (long)layout.gr_gid.get(this.memory);
   }

   public String[] getMembers() {
      List lst = new ArrayList();
      Pointer ptr = layout.gr_mem.get(this.memory);
      int ptrSize = this.runtime.addressSize();

      Pointer member;
      for(int i = 0; (member = ptr.getPointer((long)i)) != null; i += ptrSize) {
         lst.add(member.getString(0L));
      }

      return (String[])lst.toArray(new String[lst.size()]);
   }

   static final class Layout extends StructLayout {
      public final StructLayout.UTF8StringRef gr_name = new StructLayout.UTF8StringRef();
      public final StructLayout.UTF8StringRef gr_passwd = new StructLayout.UTF8StringRef();
      public final StructLayout.Signed32 gr_gid = new StructLayout.Signed32();
      public final StructLayout.Pointer gr_mem = new StructLayout.Pointer();

      public Layout(Runtime runtime) {
         super(runtime);
      }
   }
}
