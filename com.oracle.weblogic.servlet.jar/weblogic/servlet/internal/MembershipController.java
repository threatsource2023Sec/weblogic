package weblogic.servlet.internal;

import java.util.Map;
import weblogic.protocol.ServerChannel;

public interface MembershipController {
   Map getClusterMembers();

   String getHash();

   String[] getClusterList(ServerChannel var1);
}
