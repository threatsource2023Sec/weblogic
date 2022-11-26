package weblogic.protocol.configuration;

import org.jvnet.hk2.annotations.Contract;
import weblogic.protocol.Protocol;
import weblogic.rmi.spi.Channel;

@Contract
public interface ChannelHelperService {
   String getURL(Protocol var1);

   boolean isAdminServerAdminChannelEnabled();

   String createCodeBaseURL(Channel var1);
}
