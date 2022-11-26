package com.bea.httppubsub.bayeux.handlers;

import com.bea.httppubsub.EventMessage;
import com.bea.httppubsub.PubSubLogger;
import com.bea.httppubsub.PubSubServerException;
import com.bea.httppubsub.Transport;
import com.bea.httppubsub.bayeux.messages.ServiceMessageImpl;
import com.bea.httppubsub.descriptor.ServiceBean;
import com.bea.httppubsub.descriptor.WeblogicPubsubBean;
import com.bea.httppubsub.internal.ServiceHandler;
import java.util.HashMap;
import java.util.Map;

public class ServiceRequestHandler extends AbstractBayeuxHandler {
   private Map serviceHandlerMapping;

   public ServiceRequestHandler(WeblogicPubsubBean configuration) throws PubSubServerException {
      this.serviceHandlerMapping = this.initPreconfiguredServices(configuration);
   }

   protected void doHandle(ServiceMessageImpl message, Transport transport) throws PubSubServerException {
      String channelName = message.getChannel();

      assert channelName.startsWith("/service/") || !channelName.endsWith("/*") : "Service channel name must start with /service and cannot end with * or **";

      Object result = this.handleService(channelName, message);
      if (result != null) {
         message.setSuccessful(true);
         message.setPayLoad(result.toString());
      }

   }

   private Object handleService(String channel, EventMessage message) throws PubSubServerException {
      if (this.serviceHandlerMapping == null) {
         return null;
      } else {
         ServiceHandler serviceHandler = (ServiceHandler)this.serviceHandlerMapping.get(channel);
         return serviceHandler != null ? serviceHandler.handleMessage(message) : null;
      }
   }

   private Map initPreconfiguredServices(WeblogicPubsubBean configuration) throws PubSubServerException {
      if (configuration == null) {
         return null;
      } else {
         ServiceBean[] services = configuration.getServices();
         if (services == null) {
            return null;
         } else {
            Map result = new HashMap();
            ServiceBean[] var4 = services;
            int var5 = services.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               ServiceBean service = var4[var6];
               String serviceChannel = service.getServiceChannel();
               String serviceClass = service.getServiceClass();
               String serviceMethod = service.getServiceMethod();

               try {
                  result.put(serviceChannel, new ServiceHandler(serviceChannel, serviceClass, serviceMethod));
                  if (this.bayeuxLogger.isDebugEnabled()) {
                     this.bayeuxLogger.debug("Register service [ " + serviceClass + "." + serviceMethod + "(Object payload) ] on " + serviceChannel + ".");
                  }
               } catch (Exception var12) {
                  PubSubLogger.logCannotInitServiceChannel(serviceChannel, var12);
                  throw new PubSubServerException(PubSubLogger.logCannotInitServiceChannelLoggable(serviceChannel, var12).getMessage());
               }
            }

            return result;
         }
      }
   }
}
