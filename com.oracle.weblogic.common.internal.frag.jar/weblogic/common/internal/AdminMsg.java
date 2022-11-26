package weblogic.common.internal;

import java.io.Serializable;

public final class AdminMsg implements Serializable {
   private static final long serialVersionUID = -6671831720088467261L;
   public static final String PROXYCLASS = "weblogic.common.internal.AdminProxy";
   public static final int PROXYCLASSNUM = 1;
   public static final byte CMD_NONE = 0;
   public static final byte CMD_ECHO = 1;
   public static final byte CMD_SHUT = 2;
   public static final byte CMD_VERSION = 5;
   public static final byte CMD_WD_ENABLE = 6;
   public static final byte CMD_WD_DISABLE = 7;
   public static final byte CMD_CONPOOL_RESET = 8;
   public static final byte CMD_IS_PRIVILEDGED_USER = 9;
   public static final byte CMD_LOCK = 10;
   public static final byte CMD_UNLOCK = 11;
   public static final byte CMD_CANCEL_SHUT = 12;
   public static final byte CMD_THREAD_DUMP = 13;
   private static boolean verbose = false;
   protected byte cmd;
   protected int intervalSecs;
   protected String argString;
   protected byte[] echo;

   public String toString() {
      return "AdminMsg " + getCmdVerb(this.cmd());
   }

   private static String getCmdVerb(byte val) {
      switch (val) {
         case 0:
            return "CMD_NONE";
         case 1:
            return "CMD_ECHO";
         case 2:
            return "CMD_SHUT";
         case 3:
         case 4:
         default:
            return (new Integer(val)).toString();
         case 5:
            return "CMD_VERSION";
         case 6:
            return "CMD_WD_ENABLE";
         case 7:
            return "CMD_WD_DISABLE";
         case 8:
            return "CMD_CONPOOL_RESET";
         case 9:
            return "CMD_IS_PRIVILEDGED_USER";
         case 10:
            return "CMD_LOCK";
         case 11:
            return "CMD_UNLOCK";
         case 12:
            return "CMD_CANCEL_SHUT";
         case 13:
            return "CMD_THREAD_DUMP";
      }
   }

   public byte cmd() {
      return this.cmd;
   }

   public int intervalSecs() {
      return this.intervalSecs;
   }

   public String argString() {
      return this.argString;
   }

   public byte[] echo() {
      return this.echo;
   }

   public void destroy() {
      this.cmd = 0;
      this.echo = null;
   }

   public void initialize() {
      this.cmd = 0;
      this.echo = null;
   }

   public AdminMsg() {
   }

   public AdminMsg(byte cmd) {
      this.cmd = cmd;
   }

   public AdminMsg setEcho(byte[] st) {
      this.echo = st;
      return this;
   }

   public AdminMsg setIntervalSecs(int intervalSecs) {
      this.intervalSecs = intervalSecs;
      return this;
   }

   public AdminMsg setArgString(String argString) {
      this.argString = argString;
      return this;
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (obj == this) {
         return true;
      } else {
         try {
            AdminMsg am = (AdminMsg)obj;
            return this.cmd == am.cmd && this.intervalSecs == am.intervalSecs && (this.echo == null && am.echo == null || this.echo == am.echo) && (this.argString == null && am.argString == null || this.argString.equals(am.argString));
         } catch (ClassCastException var3) {
            return false;
         } catch (NullPointerException var4) {
            return false;
         }
      }
   }

   public int hashCode() {
      return this.cmd << 24 | this.intervalSecs | (this.echo == null ? 0 : this.echo.hashCode()) | (this.argString == null ? 0 : this.argString.hashCode());
   }
}
