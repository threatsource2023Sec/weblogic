package org.apache.openjpa.slice;

import org.apache.openjpa.kernel.FinalizingBrokerImpl;
import org.apache.openjpa.kernel.OpCallbacks;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.UserException;

public class DistributedBrokerImpl extends FinalizingBrokerImpl {
   private transient String slice;
   private static final Localizer _loc = Localizer.forPackage(DistributedBrokerImpl.class);

   public OpenJPAStateManager persist(Object pc, Object id, boolean explicit, OpCallbacks call) {
      OpenJPAStateManager sm = this.getStateManager(pc);
      if (this.getOperatingSet().isEmpty() && (sm == null || sm.getImplData() == null)) {
         this.slice = this.getSlice(pc);
      }

      sm = super.persist(pc, id, explicit, call);
      if (sm.getImplData() == null) {
         sm.setImplData(this.slice, true);
      }

      return sm;
   }

   String getSlice(Object pc) {
      DistributedConfiguration conf = (DistributedConfiguration)this.getConfiguration();
      String slice = conf.getDistributionPolicyInstance().distribute(pc, conf.getActiveSliceNames(), this);
      if (!conf.getActiveSliceNames().contains(slice)) {
         throw new UserException(_loc.get("bad-policy-slice", new Object[]{conf.getDistributionPolicyInstance().getClass().getName(), slice, pc, conf.getActiveSliceNames()}));
      } else {
         return slice;
      }
   }

   public boolean endOperation() {
      try {
         return super.endOperation();
      } catch (Exception var2) {
         return true;
      }
   }
}
