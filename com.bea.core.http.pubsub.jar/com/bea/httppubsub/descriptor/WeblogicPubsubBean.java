package com.bea.httppubsub.descriptor;

public interface WeblogicPubsubBean {
   ServerConfigBean getServerConfig();

   ServerConfigBean createServerConfig();

   JmsHandlerMappingBean[] getJmsHandlerMappings();

   JmsHandlerMappingBean createJmsHandlerMapping();

   ChannelBean[] getChannels();

   ChannelBean createChannel();

   ChannelConstraintBean[] getChannelConstraints();

   ChannelConstraintBean createChannelConstraint();

   MessageFilterBean[] getMessageFilters();

   MessageFilterBean createMessageFilter();

   ServiceBean[] getServices();

   ServiceBean createService();
}
