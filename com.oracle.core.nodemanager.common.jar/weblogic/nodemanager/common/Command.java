package weblogic.nodemanager.common;

import java.util.HashMap;
import java.util.Locale;
import weblogic.nodemanager.NodeManagerCommonTextFormatter;

public class Command {
   private String name;
   private static final HashMap commands = new HashMap();
   public static final Command VERSION = put("VERSION");
   public static final Command DOMAIN = put("DOMAIN");
   public static final Command SERVER = put("SERVER");
   public static final Command SERVER_TYPE = put("SERVER_TYPE");
   /** @deprecated */
   @Deprecated
   public static final Command COHERENCESERVER = put("COHERENCESERVER");
   public static final Command USER = put("USER");
   public static final Command PASS = put("PASS");
   public static final Command STAT = put("STAT");
   public static final Command QUIT = put("QUIT");
   public static final Command START = put("START");
   public static final Command STARTP = put("STARTP");
   public static final Command KILL = put("KILL");
   public static final Command GETLOG = put("GETLOG");
   public static final Command GETNMLOG = put("GETNMLOG");
   public static final Command HELLO = put("HELLO");
   public static final Command CHGCRED = put("CHGCRED");
   public static final Command GETSTATES = put("GETSTATES");
   public static final Command EXECSCRIPT = put("EXECSCRIPT");
   public static final Command UPDATEPROPS = put("UPDATEPROPS");
   public static final Command CHANGELIST = put("CHANGELIST");
   public static final Command VALIDATE_CHANGELIST = put("VALIDATE_CHANGELIST");
   public static final Command COMMIT_CHANGELIST = put("COMMIT_CHANGELIST");
   public static final Command ROLLBACK_CHANGELIST = put("ROLLBACK_CHANGELIST");
   public static final Command SYNC_CHANGELIST = put("SYNC_CHANGELIST");
   public static final Command INVOCATION = put("INVOCATION");
   public static final Command DIAGNOSTICS = put("DIAGNOSTICS");
   public static final Command PUTFILE = put("PUTFILE");
   public static final Command SOFTRESTART = put("SOFTRESTART");
   public static final Command PROPERTIES = put("PROPERTIES");
   public static final Command REMOVE = put("REMOVE");
   public static final Command INITSTATE = put("INITSTATE");
   public static final Command GETCHANGELIST = put("GETCHANGELIST");
   public static final Command GETFILE = put("GETFILE");
   public static final Command SCRIPTCALL = put("SCRIPTCALL");
   public static final Command RESTARTNM = put("RESTARTNM");
   public static final Command NMPROTOCOL = put("NMPROTOCOL");
   public static final Command DONE = put("DONE");
   public static final Command PRINTTHREADDUMP = put("PRINTTHREADDUMP");
   public static final Command PROGRESS = put("PROGRESS");

   private Command(String name) {
      this.name = name;
   }

   private static Command put(String name) {
      Command cmd = new Command(name);
      commands.put(name.toUpperCase(Locale.ENGLISH), cmd);
      return cmd;
   }

   public static Command parse(String name) {
      Command cmd = (Command)commands.get(name.toUpperCase(Locale.ENGLISH));
      if (cmd != null) {
         return cmd;
      } else {
         throw new IllegalArgumentException(NodeManagerCommonTextFormatter.getInstance().getInvalidCommand(name));
      }
   }

   public String getName() {
      return this.name;
   }

   public boolean equals(Object obj) {
      return obj instanceof Command ? this.name.equals(((Command)obj).name) : false;
   }

   public int hashCode() {
      return this.name.hashCode();
   }

   public String toString() {
      return this.name;
   }
}
