package weblogic.security.internal;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface AdminService {
   boolean isProductionModeEnabled();

   boolean isPasswordEchoAllowed();
}
