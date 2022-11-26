package weblogic.time.common;

import weblogic.common.ParamSet;
import weblogic.common.ParamSetException;
import weblogic.common.T3Exception;
import weblogic.common.T3ServicesDef;
import weblogic.common.internal.RemoteEntryPoint;

/** @deprecated */
@Deprecated
public final class Trigger extends RemoteEntryPoint {
   private static final long serialVersionUID = -1055933006857913665L;

   public Trigger() {
   }

   /** @deprecated */
   @Deprecated
   public Trigger(Triggerable o, ParamSet ps) {
      super((Object)o, ps);
   }

   /** @deprecated */
   @Deprecated
   public Trigger(Triggerable o) {
      super((Object)o);
   }

   /** @deprecated */
   @Deprecated
   public Trigger(String entry, ParamSet ps) {
      super(entry, ps);
   }

   /** @deprecated */
   @Deprecated
   public Trigger(String entry) {
      super(entry);
   }

   /** @deprecated */
   @Deprecated
   public void private_set_instance(Triggerable object) {
      this.theObject = object;
   }

   /** @deprecated */
   @Deprecated
   public void private_initialize(T3ServicesDef services) throws TimeTriggerException {
      try {
         if (this.newInstance() instanceof TriggerDef) {
            TriggerDef trigger = (TriggerDef)this.theObject();
            trigger.setServices(services);
            trigger.triggerInit(this.params());
         }

      } catch (ParamSetException var3) {
         throw new TimeTriggerException("Nested Exception: " + var3);
      } catch (T3Exception var4) {
         throw new TimeTriggerException("Nested Exception: " + var4);
      }
   }

   /** @deprecated */
   @Deprecated
   public void private_initialize(T3ServicesDef services, Triggerable object) throws TimeTriggerException {
      this.theObject = object;
   }
}
