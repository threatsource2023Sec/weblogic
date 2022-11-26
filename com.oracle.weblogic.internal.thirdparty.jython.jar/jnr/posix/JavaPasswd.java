package jnr.posix;

final class JavaPasswd implements Passwd {
   private final POSIXHandler handler;

   public JavaPasswd(POSIXHandler handler) {
      this.handler = handler;
   }

   public String getAccessClass() {
      this.handler.unimplementedError("passwd.pw_access unimplemented");
      return null;
   }

   public String getGECOS() {
      return this.getLoginName();
   }

   public long getGID() {
      return (long)JavaPOSIX.LoginInfo.GID;
   }

   public String getHome() {
      return System.getProperty("user.home");
   }

   public String getLoginName() {
      return System.getProperty("user.name");
   }

   public int getPasswdChangeTime() {
      this.handler.unimplementedError("passwd.pw_change unimplemented");
      return 0;
   }

   public String getPassword() {
      this.handler.unimplementedError("passwd.pw_passwd unimplemented");
      return null;
   }

   public String getShell() {
      this.handler.unimplementedError("passwd.pw_env unimplemented");
      return null;
   }

   public long getUID() {
      return (long)JavaPOSIX.LoginInfo.UID;
   }

   public int getExpire() {
      this.handler.unimplementedError("passwd.expire unimplemented");
      return -1;
   }
}
