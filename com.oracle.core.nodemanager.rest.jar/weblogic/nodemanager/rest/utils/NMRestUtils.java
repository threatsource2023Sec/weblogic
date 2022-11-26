package weblogic.nodemanager.rest.utils;

import javax.ws.rs.core.StreamingOutput;
import weblogic.nodemanager.common.Command;
import weblogic.nodemanager.rest.RestInputOutput;
import weblogic.nodemanager.server.InternalNMCommandHandler;
import weblogic.nodemanager.server.NMServer;

public class NMRestUtils {
   public static StreamingOutput getNodeManagerLog(String domainName) {
      RestInputOutput rio = new RestInputOutput();
      InternalNMCommandHandler impl = new InternalNMCommandHandler(NMServer.getInstance(), rio);
      CommonUtils.addDomainCommands(rio, domainName);
      rio.addCommand(Command.GETNMLOG.toString());
      return new StreamingOutputImpl(impl);
   }
}
