package org.glassfish.hk2.extras.events;

import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.messaging.Topic;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface DefaultTopicDistributionErrorService {
   void subscribersFailed(Topic var1, Object var2, MultiException var3);
}
