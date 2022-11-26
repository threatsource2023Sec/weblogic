package com.bea.httppubsub.runtime;

import com.bea.httppubsub.Channel;
import javax.management.MBeanException;

public interface MBeanManager {
   void registerChannelRuntimeMBean(Channel var1) throws MBeanException;

   void unregisterChannelRuntimeMBean(Channel var1) throws MBeanException;

   void registerWebPubSubRuntimeMBean() throws MBeanException;

   void unregisterWebPubSubRuntimeMBean() throws MBeanException;
}
