package monfox.toolkit.snmp.agent.x.connection;

import java.io.IOException;
import java.net.Socket;
import monfox.log.Logger;

public class MasterAgentXConnection extends AgentXConnection {
   private MasterAgentXServer a = null;
   private Logger p = Logger.getInstance(a("Y\"~8z"), a("\\6u;~0)"), a("P\u0010C\u0001Oo0W\u0010Di)s\u001aDs\u0014S\u0001Cr\u001f"));

   MasterAgentXConnection(MasterAgentXServer var1, Socket var2, AgentXConnection.StatusListener var3, AgentXConnection.RequestListener var4) throws IOException {
      this.p.comms(a("~\u001e^\u001bO~\u0005Y\u001aD=\u0012B\u0010Ki\u0014TULr\u0003\u0010\u0006E~\u001aU\u0001\u0010=") + var2);
      this.a = var1;
      this.a(var2, var3, var4);
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 29;
               break;
            case 1:
               var10003 = 113;
               break;
            case 2:
               var10003 = 48;
               break;
            case 3:
               var10003 = 117;
               break;
            default:
               var10003 = 42;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
