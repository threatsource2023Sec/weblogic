package weblogic.security.auth;

import org.jvnet.hk2.annotations.Contract;
import weblogic.protocol.Protocol;

@Contract
public interface T3ProtocolFetcherService {
   Protocol fetchT3Protocol();
}
