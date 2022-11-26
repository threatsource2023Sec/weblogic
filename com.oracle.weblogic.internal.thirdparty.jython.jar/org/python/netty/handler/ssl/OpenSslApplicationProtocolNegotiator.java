package org.python.netty.handler.ssl;

public interface OpenSslApplicationProtocolNegotiator extends ApplicationProtocolNegotiator {
   ApplicationProtocolConfig.Protocol protocol();

   ApplicationProtocolConfig.SelectorFailureBehavior selectorFailureBehavior();

   ApplicationProtocolConfig.SelectedListenerFailureBehavior selectedListenerFailureBehavior();
}
