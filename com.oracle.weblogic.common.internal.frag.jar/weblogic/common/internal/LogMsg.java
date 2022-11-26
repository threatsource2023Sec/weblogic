package weblogic.common.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;

public final class LogMsg implements Externalizable {
   private static final long serialVersionUID = -4898439307567022541L;
   public static final String PROXYCLASS = "weblogic.common.internal.LogProxy";
   public static final String PROXYLAZYCLASS = "weblogic.common.internal.LogProxyLazy";
   private static boolean verbose = false;
   public byte cmd;
   public String logmsg;
   public String exception;
   public static final byte INFO = 0;
   public static final byte ERROR = 1;
   public static final byte WARNING = 2;
   public static final byte SECURITY = 3;
   public static final byte DEBUG = 4;

   public String toString() {
      return "LogMsg " + getCmdVerb(this.cmd) + " - " + this.logmsg;
   }

   private static String getCmdVerb(byte val) {
      switch (val) {
         case 0:
            return "INFO";
         case 1:
            return "ERROR";
         case 2:
            return "WARNING";
         case 3:
            return "SECURITY";
         case 4:
            return "DEBUG";
         default:
            return (new Integer(val)).toString();
      }
   }

   public void initialize() {
   }

   public void destroy() {
   }

   public LogMsg() {
   }

   public LogMsg(byte cmd) {
      this.cmd = cmd;
   }

   public LogMsg(byte cmd, String logmsg) {
      this(cmd);
      this.logmsg = logmsg;
   }

   public LogMsg(byte cmd, String logmsg, String exception) {
      this(cmd, logmsg);
      this.exception = exception;
   }

   public void readExternal(ObjectInput oi) throws IOException {
      WLObjectInput sis = (WLObjectInput)oi;
      this.cmd = sis.readByte();
      this.logmsg = sis.readString();
      this.exception = sis.readString();
   }

   public void writeExternal(ObjectOutput oo) throws IOException {
      WLObjectOutput sos = (WLObjectOutput)oo;
      sos.writeByte(this.cmd);
      sos.writeString(this.logmsg);
      sos.writeString(this.exception);
   }
}
