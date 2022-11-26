package weblogic.jms.forwarder.dd;

import weblogic.jms.cache.CacheEntryChangeListener;
import weblogic.jms.common.JMSID;

public interface DDMembersCacheChangeListener extends CacheEntryChangeListener {
   JMSID getId();
}
