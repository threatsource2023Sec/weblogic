package monfox.toolkit.snmp.agent.x.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import monfox.log.Logger;
import monfox.toolkit.snmp.agent.x.common.AgentXCommunicationsException;
import monfox.toolkit.snmp.agent.x.common.AgentXSocketFactory;
import monfox.toolkit.snmp.agent.x.sub.SubAgentX;

public class SubAgentXConnection extends AgentXConnection {
   private Logger p = Logger.getInstance(a("xNS)\r"), a("}ZX*\t\u0011E"), a("oh\u007f%:Ysi<\u001eSss\u0001>Htr\n"));

   public SubAgentXConnection(InetAddress var1, int var2, AgentXConnection.StatusListener var3, AgentXConnection.RequestListener var4) throws AgentXCommunicationsException {
      this.p.debug(a("oh\u007f%:Ysi<\u001eSss\u0001>Htr\nu") + var1 + "," + var2 + ")");
      AgentXSocketFactory var5 = SubAgentX.getSocketFactory();
      if (var5 != null) {
         try {
            this.a(var5.newSocket(var1, var2), var3, var4);
         } catch (IOException var8) {
            this.p.error(a("_rs\n8_it\u000b3\u001c{|\r1Yy"), var8);
            throw new AgentXCommunicationsException(a("_rs\n8_it\u000b3\u001c{|\r1Yy'") + var8);
         }
      } else {
         try {
            this.a(new Socket(var1, var2), var3, var4);
         } catch (IOException var7) {
            this.p.error(a("_rs\n8_it\u000b3\u001c{|\r1Yy"), var7);
            throw new AgentXCommunicationsException(a("_rs\n8_it\u000b3\u001c{|\r1Yy'") + var7);
         }
      }

   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 60;
               break;
            case 1:
               var10003 = 29;
               break;
            case 2:
               var10003 = 29;
               break;
            case 3:
               var10003 = 100;
               break;
            default:
               var10003 = 93;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
