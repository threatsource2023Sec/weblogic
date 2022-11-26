package com.bea.httppubsub.jms.provider;

import com.bea.httppubsub.descriptor.JmsHandlerBean;

public interface JmsProviderFactory {
   JmsProvider createJmsProvider(JmsHandlerBean var1);
}
