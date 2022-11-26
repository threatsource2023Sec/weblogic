package weblogic.jms.common;

import java.util.Map;
import weblogic.jms.forwarder.dd.DDLBTable;

public class PerJVMLoadBalancer implements LoadBalancer {
   private LoadBalancer perJVMLB;
   private DDLBTable ddLBTable;
   private PerJVMLBHelper helper;

   public PerJVMLoadBalancer(LoadBalancer preferedLB, DDLBTable ddLBTable) {
      this.perJVMLB = preferedLB;
      this.ddLBTable = ddLBTable;
      this.helper = new PerJVMLBHelper(ddLBTable);
   }

   public void refresh(DistributedDestinationImpl[] dests) {
      this.perJVMLB.refresh(this.createPerJVMLBDDMembers(dests));
   }

   public DistributedDestinationImpl getNext(DDTxLoadBalancingOptimizer ddTxLBOptimizer) {
      return this.perJVMLB.getNext(ddTxLBOptimizer);
   }

   public DistributedDestinationImpl getNext(int index) {
      return this.perJVMLB.getNext(index);
   }

   public int getSize() {
      return this.perJVMLB.getSize();
   }

   private DistributedDestinationImpl[] createPerJVMLBDDMembers(DistributedDestinationImpl[] dests) {
      Map newMap = this.helper.createPerJVMLBDDMemberList((PerJVMLBAwareDDMember[])dests);
      return (DistributedDestinationImpl[])((DistributedDestinationImpl[])newMap.values().toArray(new DistributedDestinationImpl[newMap.size()]));
   }
}
