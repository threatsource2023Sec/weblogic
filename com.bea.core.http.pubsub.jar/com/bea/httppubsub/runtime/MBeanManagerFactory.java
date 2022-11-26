package com.bea.httppubsub.runtime;

import com.bea.httppubsub.descriptor.WeblogicPubsubBean;
import javax.servlet.ServletContext;

public interface MBeanManagerFactory {
   MBeanManager createMBeanManager(WeblogicPubsubBean var1, ServletContext var2);
}
