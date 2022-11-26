package weblogic.rjvm;

import java.io.IOException;
import java.net.InetAddress;
import weblogic.protocol.ServerChannel;

public interface RJVMConnectionFactory {
   /** @deprecated */
   @Deprecated
   MsgAbbrevJVMConnection createConnection(String var1, InetAddress var2, int var3, ServerChannel var4, JVMID var5, int var6) throws IOException;

   MsgAbbrevJVMConnection createConnection(String var1, InetAddress var2, int var3, ServerChannel var4, JVMID var5, int var6, String var7) throws IOException;
}
