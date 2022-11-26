package weblogic.time.common;

import weblogic.common.ParamSet;
import weblogic.common.ParamSetException;
import weblogic.common.T3ServicesDef;

/** @deprecated */
@Deprecated
public interface ScheduleDef extends Schedulable {
   /** @deprecated */
   @Deprecated
   void setServices(T3ServicesDef var1);

   /** @deprecated */
   @Deprecated
   void scheduleInit(ParamSet var1) throws ParamSetException;
}
