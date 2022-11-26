package weblogic.security.acl;

import org.jvnet.hk2.annotations.Contract;
import weblogic.protocol.Protocol;
import weblogic.rjvm.RJVM;

@Contract
public interface SecurityServiceGenerator {
   SecurityService createBootService(RJVM var1, Protocol var2, String var3);

   SecurityService createBootService(RJVM var1, Protocol var2, String var3, String var4, String var5);

   SecurityService createRMIBootService(RJVM var1, String var2, int var3, String var4, String var5);
}
