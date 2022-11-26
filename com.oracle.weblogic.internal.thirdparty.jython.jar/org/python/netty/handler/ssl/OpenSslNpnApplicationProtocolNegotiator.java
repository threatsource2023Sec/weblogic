package org.python.netty.handler.ssl;

import java.util.List;
import org.python.netty.util.internal.ObjectUtil;

/** @deprecated */
@Deprecated
public final class OpenSslNpnApplicationProtocolNegotiator implements OpenSslApplicationProtocolNegotiator {
   private final List protocols;

   public OpenSslNpnApplicationProtocolNegotiator(Iterable protocols) {
      this.protocols = (List)ObjectUtil.checkNotNull(ApplicationProtocolUtil.toList(protocols), "protocols");
   }

   public OpenSslNpnApplicationProtocolNegotiator(String... protocols) {
      this.protocols = (List)ObjectUtil.checkNotNull(ApplicationProtocolUtil.toList(protocols), "protocols");
   }

   public ApplicationProtocolConfig.Protocol protocol() {
      return ApplicationProtocolConfig.Protocol.NPN;
   }

   public List protocols() {
      return this.protocols;
   }

   public ApplicationProtocolConfig.SelectorFailureBehavior selectorFailureBehavior() {
      return ApplicationProtocolConfig.SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL;
   }

   public ApplicationProtocolConfig.SelectedListenerFailureBehavior selectedListenerFailureBehavior() {
      return ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT;
   }
}
