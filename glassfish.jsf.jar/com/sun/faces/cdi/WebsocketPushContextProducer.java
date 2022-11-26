package com.sun.faces.cdi;

import com.sun.faces.push.WebsocketPushContext;
import com.sun.faces.push.WebsocketSessionManager;
import com.sun.faces.push.WebsocketUserManager;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;

@Dependent
public class WebsocketPushContextProducer {
   @Inject
   private InjectionPoint injectionPoint;
   @Inject
   private WebsocketSessionManager socketSessions;
   @Inject
   private WebsocketUserManager socketUsers;

   @Produces
   @Push
   public PushContext produce(InjectionPoint injectionPoint) {
      Push push = (Push)CdiUtils.getQualifier(injectionPoint, Push.class);
      String channel = push.channel().isEmpty() ? injectionPoint.getMember().getName() : push.channel();
      return new WebsocketPushContext(channel, this.socketSessions, this.socketUsers);
   }
}
