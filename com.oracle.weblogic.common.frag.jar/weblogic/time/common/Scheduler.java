package weblogic.time.common;

import weblogic.common.ParamSet;
import weblogic.common.ParamSetException;
import weblogic.common.T3Exception;
import weblogic.common.T3ServicesDef;
import weblogic.common.internal.RemoteEntryPoint;

/** @deprecated */
@Deprecated
public final class Scheduler extends RemoteEntryPoint {
   private static final long serialVersionUID = -2245180095783249659L;

   public Scheduler() {
   }

   /** @deprecated */
   @Deprecated
   public Scheduler(Schedulable o, ParamSet ps) {
      super((Object)o, ps);
   }

   /** @deprecated */
   @Deprecated
   public Scheduler(Schedulable o) {
      super((Object)o);
   }

   /** @deprecated */
   @Deprecated
   public Scheduler(String entry, ParamSet ps) {
      super(entry, ps);
   }

   /** @deprecated */
   @Deprecated
   public Scheduler(String entry) {
      super(entry);
   }

   public void private_initialize(T3ServicesDef services) throws TimeTriggerException {
      try {
         this.newInstance();
         if (this.theObject() instanceof ScheduleDef) {
            ScheduleDef scheduler = (ScheduleDef)this.theObject();
            scheduler.setServices(services);
            scheduler.scheduleInit(this.params());
         }

      } catch (T3Exception var3) {
         throw new TimeTriggerException("Nested Exception: " + var3);
      } catch (ParamSetException var4) {
         throw new TimeTriggerException("Nested Exception: " + var4);
      }
   }
}
