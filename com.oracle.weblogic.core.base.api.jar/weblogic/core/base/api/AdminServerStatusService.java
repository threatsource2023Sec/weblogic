package weblogic.core.base.api;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface AdminServerStatusService {
   boolean isAdminServerAvailable();
}
