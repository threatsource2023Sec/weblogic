package weblogic.core.base.api;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface CapabilitiesService {
   boolean isProductionMode();
}
