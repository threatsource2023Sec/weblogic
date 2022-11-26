package weblogic.jms.forwarder.dd;

import java.io.Externalizable;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.PerJVMLBAwareDDMember;

public interface DDMemberInfo extends PerJVMLBAwareDDMember, Externalizable {
   String getJMSServerInstanceName();

   String getJMSServerConfigName();

   String getDDMemberConfigName();

   String getType();

   DDMemberRuntimeInformation getDDMemberRuntimeInformation();

   DestinationImpl getDestination();

   void setDestination(DestinationImpl var1);
}
