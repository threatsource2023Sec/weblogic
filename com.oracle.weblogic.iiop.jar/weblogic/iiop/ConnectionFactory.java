package weblogic.iiop;

import java.io.IOException;
import org.jvnet.hk2.annotations.Contract;
import weblogic.iiop.ior.IOR;
import weblogic.protocol.Protocol;
import weblogic.protocol.ServerChannel;

@Contract
public interface ConnectionFactory {
   int IIOP_RANK = 10;
   int IIOPS_RANK = 20;
   int TGIOP_RANK = 30;

   boolean claimsIOR(IOR var1);

   Connection createConnection(IOR var1, ServerChannel var2) throws IOException;

   Protocol getProtocol();
}
