package weblogic.protocol;

import java.rmi.UnknownHostException;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface URLManagerService {
   String normalizeToHttpProtocol(String var1);

   String normalizeToAdminProtocol(String var1);

   String findURL(String var1, Protocol var2) throws UnknownHostException;

   String findAdministrationURL(String var1) throws UnknownHostException;

   String findAdministrationURL(ServerIdentity var1);
}
