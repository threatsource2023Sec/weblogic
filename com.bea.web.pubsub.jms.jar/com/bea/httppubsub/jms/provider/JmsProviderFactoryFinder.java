package com.bea.httppubsub.jms.provider;

import com.bea.httppubsub.PubSubServerException;
import com.bea.httppubsub.jms.PubSubJmsLogger;
import com.bea.httppubsub.jms.internal.JmsProviderFactoryImpl;
import com.bea.httppubsub.util.StringUtils;

public final class JmsProviderFactoryFinder {
   private static JmsProviderFactory factory = new JmsProviderFactoryImpl();

   private JmsProviderFactoryFinder() {
   }

   public static JmsProviderFactory getFactory() {
      return factory;
   }

   public static void registerFactory(String jmsProviderFactoryImplName) throws PubSubServerException {
      if (StringUtils.isEmpty(jmsProviderFactoryImplName)) {
         throw new IllegalArgumentException("JmsProviderFactory implName cannot be empty.");
      } else {
         ClassLoader cl = Thread.currentThread().getContextClassLoader();

         try {
            Class clz = cl.loadClass(jmsProviderFactoryImplName);
            factory = (JmsProviderFactory)clz.newInstance();
            PubSubJmsLogger.logJmsProviderFactoryRegistered(jmsProviderFactoryImplName);
         } catch (Exception var3) {
            throw new PubSubServerException(PubSubJmsLogger.logCannotCreateJmsProviderFactoryForGivenClassNameLoggable(jmsProviderFactoryImplName, var3).getMessage());
         }
      }
   }
}
