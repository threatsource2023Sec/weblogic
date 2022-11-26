package weblogic.common;

import org.jvnet.hk2.annotations.Contract;
import weblogic.time.common.TimeServicesDef;

@Contract
public interface T3ServicesDef {
   String NAME = "weblogic.common.T3Services";

   TimeServicesDef time();
}
