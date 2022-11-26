package org.glassfish.hk2.extras.events.internal;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class TopicDistributionModule extends AbstractBinder {
   protected void configure() {
      this.addActiveDescriptor(DefaultTopicDistributionService.class);
   }
}
