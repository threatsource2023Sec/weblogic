package weblogic.jms.forwarder.dd;

import java.util.Map;
import weblogic.jms.cache.Cache;
import weblogic.jms.common.DDMemberInformation;
import weblogic.jms.forwarder.DestinationName;

public interface DDMembersCache extends Cache {
   DDMemberInformation[] getDDMemberConfigInformation();

   Map getDDMemberRuntimeInformation();

   void addDDMembersCacheChangeListener(DDMembersCacheChangeListener var1);

   void removeDDMembersCacheChangeListener(DDMembersCacheChangeListener var1);

   void setDestinationName(DestinationName var1);

   DestinationName getDestinationName();
}
