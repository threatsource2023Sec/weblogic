package weblogic.time.common;

import weblogic.common.ParamSet;
import weblogic.common.ParamSetException;
import weblogic.common.T3ServicesDef;

/** @deprecated */
@Deprecated
public interface TriggerDef extends Triggerable {
   /** @deprecated */
   @Deprecated
   void setServices(T3ServicesDef var1);

   /** @deprecated */
   @Deprecated
   void triggerInit(ParamSet var1) throws ParamSetException;
}
