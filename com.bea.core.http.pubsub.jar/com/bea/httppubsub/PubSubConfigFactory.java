package com.bea.httppubsub;

import com.bea.httppubsub.descriptor.WeblogicPubsubBean;
import javax.servlet.ServletContext;

public interface PubSubConfigFactory {
   WeblogicPubsubBean getWeblogicPubsubBean(ServletContext var1) throws PubSubServerException;

   boolean isReplicatedSessionEnable(ServletContext var1);
}
