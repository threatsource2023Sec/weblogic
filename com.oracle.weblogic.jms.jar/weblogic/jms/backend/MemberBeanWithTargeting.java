package weblogic.jms.backend;

import weblogic.j2ee.descriptor.wl.DistributedDestinationMemberBean;

public interface MemberBeanWithTargeting extends DistributedDestinationMemberBean {
   String getServerName();

   String getClusterName();

   String getMigratableTargetName();
}
