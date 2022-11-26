package com.sun.faces.push;

import com.sun.faces.cdi.CdiUtils;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.push.PushContext;

public class WebsocketPushContext implements PushContext {
   private static final long serialVersionUID = 1L;
   private String channel;
   private Map sessionScope;
   private Map viewScope;
   private WebsocketSessionManager socketSessions;
   private WebsocketUserManager socketUsers;

   public WebsocketPushContext(String channel, WebsocketSessionManager socketSessions, WebsocketUserManager socketUsers) {
      this.channel = channel;
      boolean hasSession = CdiUtils.isScopeActive(SessionScoped.class);
      this.sessionScope = hasSession ? WebsocketChannelManager.getSessionScope() : WebsocketChannelManager.EMPTY_SCOPE;
      this.viewScope = hasSession && FacesContext.getCurrentInstance() != null ? WebsocketChannelManager.getViewScope(true) : WebsocketChannelManager.EMPTY_SCOPE;
      this.socketSessions = socketSessions;
      this.socketUsers = socketUsers;
   }

   public Set send(Object message) {
      return this.socketSessions.send(WebsocketChannelManager.getChannelId(this.channel, this.sessionScope, this.viewScope), message);
   }

   public Set send(Object message, Serializable user) {
      return (Set)this.send(message, (Collection)Collections.singleton(user)).get(user);
   }

   public Map send(Object message, Collection users) {
      Map resultsByUser = new HashMap(users.size());
      Iterator var4 = users.iterator();

      while(var4.hasNext()) {
         Serializable user = (Serializable)var4.next();
         Set channelIds = this.socketUsers.getChannelIds(user, this.channel);
         Set results = new HashSet(channelIds.size());
         Iterator var8 = channelIds.iterator();

         while(var8.hasNext()) {
            String channelId = (String)var8.next();
            results.addAll(this.socketSessions.send(channelId, message));
         }

         resultsByUser.put(user, results);
      }

      return resultsByUser;
   }
}
