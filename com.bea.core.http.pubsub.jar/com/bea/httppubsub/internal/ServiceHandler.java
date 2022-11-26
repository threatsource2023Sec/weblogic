package com.bea.httppubsub.internal;

import com.bea.httppubsub.EventMessage;
import com.bea.httppubsub.PubSubServerException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import weblogic.diagnostics.debug.DebugLogger;

public final class ServiceHandler {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugPubSubBayeux");
   private String serviceChannel;
   private Method serviceMethod;
   private Object serviceClass;

   public ServiceHandler(String serviceChannel, String serviceClass, String serviceMethod) throws PubSubServerException {
      try {
         Class c = Thread.currentThread().getContextClassLoader().loadClass(serviceClass);
         Constructor constructor = c.getConstructor();
         this.serviceChannel = serviceChannel;
         this.serviceClass = constructor.newInstance();
         this.serviceMethod = this.serviceClass.getClass().getMethod(serviceMethod, Object.class);
         logger.debug("Register method " + serviceMethod + " on class " + serviceClass + " for channel " + serviceChannel);
      } catch (Exception var6) {
         throw new PubSubServerException("cannot init service registered on channel: " + serviceChannel, var6);
      }
   }

   public Object handleMessage(EventMessage message) throws PubSubServerException {
      Object payload = message.getPayLoad();
      logger.debug("Serve incoming message (" + message + ") on channel " + this.serviceChannel);

      try {
         return this.serviceMethod.invoke(this.serviceClass, payload);
      } catch (Exception var4) {
         throw new PubSubServerException("Cannot serve message (" + message + ") on channel " + this.serviceChannel, var4);
      }
   }
}
