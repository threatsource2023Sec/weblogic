package org.python.netty.handler.ssl;

import java.util.List;
import org.python.netty.util.internal.ObjectUtil;

public final class OpenSslDefaultApplicationProtocolNegotiator implements OpenSslApplicationProtocolNegotiator {
   private final ApplicationProtocolConfig config;

   public OpenSslDefaultApplicationProtocolNegotiator(ApplicationProtocolConfig config) {
      this.config = (ApplicationProtocolConfig)ObjectUtil.checkNotNull(config, "config");
   }

   public List protocols() {
      return this.config.supportedProtocols();
   }

   public ApplicationProtocolConfig.Protocol protocol() {
      return this.config.protocol();
   }

   public ApplicationProtocolConfig.SelectorFailureBehavior selectorFailureBehavior() {
      return this.config.selectorFailureBehavior();
   }

   public ApplicationProtocolConfig.SelectedListenerFailureBehavior selectedListenerFailureBehavior() {
      return this.config.selectedListenerFailureBehavior();
   }
}
