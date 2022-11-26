package weblogic.jms.module;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualTargetMBean;

public class TargetListSave {
   private List savedTargets = null;

   public TargetListSave(List realTargets) {
      if (realTargets == null) {
         this.savedTargets = null;
      } else {
         this.savedTargets = new LinkedList();
         Iterator iter = realTargets.iterator();

         while(iter.hasNext()) {
            TargetMBean target = (TargetMBean)iter.next();
            this.savedTargets.add(new TargetSave(target));
         }
      }

   }

   public List restoreTargets(DomainMBean domain) {
      List ret = new LinkedList();
      if (this.savedTargets == null) {
         return ret;
      } else {
         Iterator iter = this.savedTargets.iterator();

         while(iter.hasNext()) {
            TargetSave target = (TargetSave)iter.next();
            TargetMBean tmb = target.restore(domain);
            if (tmb != null) {
               ret.add(tmb);
            }
         }

         return ret;
      }
   }

   public int size() {
      return this.savedTargets == null ? 0 : this.savedTargets.size();
   }

   private static class TargetSave {
      private static final int JMS_SERVER = 0;
      private static final int WLS_SERVER = 1;
      private static final int CLUSTER = 2;
      private static final int SAF_AGENT = 3;
      private static final int MIGRATABLE_TARGET = 4;
      private static final int VIRTUAL_TARGET = 5;
      private String name;
      private int type;

      public TargetSave(TargetMBean target) {
         this.name = target.getName();
         if (target instanceof JMSServerMBean) {
            this.type = 0;
         } else if (target instanceof ServerMBean) {
            this.type = 1;
         } else if (target instanceof ClusterMBean) {
            this.type = 2;
         } else if (target instanceof SAFAgentMBean) {
            this.type = 3;
         } else if (target instanceof MigratableTargetMBean) {
            this.type = 4;
         } else {
            if (!(target instanceof VirtualTargetMBean)) {
               throw new AssertionError("Bad type: " + target.getClass().getName());
            }

            this.type = 5;
         }

      }

      public TargetMBean restore(DomainMBean domain) {
         switch (this.type) {
            case 0:
               return domain.lookupJMSServer(this.name);
            case 1:
               return domain.lookupServer(this.name);
            case 2:
               return domain.lookupCluster(this.name);
            case 3:
               return domain.lookupSAFAgent(this.name);
            case 4:
               return domain.lookupMigratableTarget(this.name);
            case 5:
               return domain.lookupVirtualTarget(this.name);
            default:
               throw new AssertionError("Cannot get here");
         }
      }
   }
}
