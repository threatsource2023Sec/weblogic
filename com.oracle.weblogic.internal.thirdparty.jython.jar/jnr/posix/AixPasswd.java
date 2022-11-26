package jnr.posix;

import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.StructLayout;

public class AixPasswd extends NativePasswd implements Passwd {
   private static final Layout layout = new Layout(Runtime.getSystemRuntime());

   AixPasswd(Pointer memory) {
      super(memory);
   }

   public String getAccessClass() {
      return "unknown";
   }

   public String getGECOS() {
      return layout.pw_gecos.get(this.memory);
   }

   public long getGID() {
      return layout.pw_gid.get(this.memory);
   }

   public String getHome() {
      return layout.pw_dir.get(this.memory);
   }

   public String getLoginName() {
      return layout.pw_name.get(this.memory);
   }

   public int getPasswdChangeTime() {
      return 0;
   }

   public String getPassword() {
      return layout.pw_passwd.get(this.memory);
   }

   public String getShell() {
      return layout.pw_shell.get(this.memory);
   }

   public long getUID() {
      return layout.pw_uid.get(this.memory);
   }

   public int getExpire() {
      return Integer.MAX_VALUE;
   }

   private static final class Layout extends StructLayout {
      public final StructLayout.UTF8StringRef pw_name;
      public final StructLayout.UTF8StringRef pw_passwd;
      public final StructLayout.uid_t pw_uid;
      public final StructLayout.gid_t pw_gid;
      public final StructLayout.UTF8StringRef pw_gecos;
      public final StructLayout.UTF8StringRef pw_dir;
      public final StructLayout.UTF8StringRef pw_shell;

      private Layout(Runtime runtime) {
         super(runtime);
         this.pw_name = new StructLayout.UTF8StringRef();
         this.pw_passwd = new StructLayout.UTF8StringRef();
         this.pw_uid = new StructLayout.uid_t();
         this.pw_gid = new StructLayout.gid_t();
         this.pw_gecos = new StructLayout.UTF8StringRef();
         this.pw_dir = new StructLayout.UTF8StringRef();
         this.pw_shell = new StructLayout.UTF8StringRef();
      }

      // $FF: synthetic method
      Layout(Runtime x0, Object x1) {
         this(x0);
      }
   }
}
