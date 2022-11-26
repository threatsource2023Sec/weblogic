package weblogic.cluster;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface InboundListener {
   boolean isStarted();
}
