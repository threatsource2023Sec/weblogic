package jnr.posix;

public interface Passwd {
   String getLoginName();

   String getPassword();

   long getUID();

   long getGID();

   int getPasswdChangeTime();

   String getAccessClass();

   String getGECOS();

   String getHome();

   String getShell();

   int getExpire();
}
