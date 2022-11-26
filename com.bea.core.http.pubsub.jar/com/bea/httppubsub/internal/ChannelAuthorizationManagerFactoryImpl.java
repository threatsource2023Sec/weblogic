package com.bea.httppubsub.internal;

import com.bea.httppubsub.descriptor.WeblogicPubsubBean;
import com.bea.httppubsub.security.ChannelAuthorizationManager;
import com.bea.httppubsub.security.ChannelAuthorizationManagerFactory;
import com.bea.httppubsub.security.ChannelAuthorizationManagerImpl;
import com.bea.httppubsub.security.NullChannelAuthorizationManagerImpl;
import com.bea.httppubsub.util.ConfigUtils;

public class ChannelAuthorizationManagerFactoryImpl implements ChannelAuthorizationManagerFactory {
   private WeblogicPubsubBean configuration;

   public ChannelAuthorizationManagerFactoryImpl(WeblogicPubsubBean bean) {
      if (bean == null) {
         throw new IllegalArgumentException("bean is null");
      } else {
         this.configuration = bean;
      }
   }

   public ChannelAuthorizationManager getChannelAuthorizationManager() {
      return (ChannelAuthorizationManager)(ConfigUtils.isChannelConstraintEnable(this.configuration) ? new ChannelAuthorizationManagerImpl() : new NullChannelAuthorizationManagerImpl());
   }
}
