package com.sun.faces.push;

import com.sun.faces.cdi.CdiUtils;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@SessionScoped
public class WebsocketChannelManager implements Serializable {
   private static final long serialVersionUID = 1L;
   private static final String ERROR_INVALID_SCOPE = "f:websocket 'scope' attribute '%s' does not represent a valid scope. It may not be an EL expression and allowed values are 'application', 'session' and 'view', case insensitive. The default is 'application'. When 'user' attribute is specified, then scope defaults to 'session' and may not be 'application'.";
   private static final String ERROR_DUPLICATE_CHANNEL = "f:websocket channel '%s' is already registered on a different scope. Choose an unique channel name for a different channel (or shutdown all browsers and restart the server if you were just testing).";
   private static final int ESTIMATED_CHANNELS_PER_APPLICATION = 1;
   private static final int ESTIMATED_CHANNELS_PER_SESSION = 1;
   private static final int ESTIMATED_CHANNELS_PER_VIEW = 1;
   private static final int ESTIMATED_USERS_PER_SESSION = 1;
   static final int ESTIMATED_TOTAL_CHANNELS = 3;
   static final Map EMPTY_SCOPE = Collections.emptyMap();
   private static final ConcurrentMap APPLICATION_SCOPE = new ConcurrentHashMap(1);
   private final ConcurrentMap sessionScope = new ConcurrentHashMap(1);
   private final ConcurrentMap sessionUsers = new ConcurrentHashMap(1);
   @Inject
   private WebsocketSessionManager socketSessions;
   @Inject
   private WebsocketUserManager socketUsers;

   public String register(FacesContext context, String channel, String scope, Serializable user) {
      switch (WebsocketChannelManager.Scope.of(scope, user)) {
         case APPLICATION:
            return this.register(context, (Serializable)null, channel, APPLICATION_SCOPE, this.sessionScope, getViewScope(false));
         case SESSION:
            return this.register(context, user, channel, this.sessionScope, APPLICATION_SCOPE, getViewScope(false));
         case VIEW:
            return this.register(context, user, channel, getViewScope(true), APPLICATION_SCOPE, this.sessionScope);
         default:
            throw new UnsupportedOperationException();
      }
   }

   private String register(FacesContext context, Serializable user, String channel, Map targetScope, Map... otherScopes) {
      String url = context.getApplication().getViewHandler().getWebsocketURL(context, channel);
      String channelId;
      if (!targetScope.containsKey(channel)) {
         Map[] var7 = otherScopes;
         int var8 = otherScopes.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            Map otherScope = var7[var9];
            if (otherScope.containsKey(channel)) {
               throw new IllegalArgumentException(String.format("f:websocket channel '%s' is already registered on a different scope. Choose an unique channel name for a different channel (or shutdown all browsers and restart the server if you were just testing).", channel));
            }
         }

         channelId = UUID.randomUUID().toString();
         ((ConcurrentMap)targetScope).putIfAbsent(channel, channelId);
      }

      channelId = (String)targetScope.get(channel);
      if (user != null) {
         if (!this.sessionUsers.containsKey(user)) {
            this.sessionUsers.putIfAbsent(user, UUID.randomUUID().toString());
            this.socketUsers.register(user, (String)this.sessionUsers.get(user));
         }

         this.socketUsers.addChannelId((String)this.sessionUsers.get(user), channel, channelId);
      }

      this.socketSessions.register(channelId);
      return url + "?" + channelId;
   }

   @PreDestroy
   protected void deregisterSessionScope() {
      Iterator var1 = this.sessionUsers.entrySet().iterator();

      while(var1.hasNext()) {
         Map.Entry sessionUser = (Map.Entry)var1.next();
         this.socketUsers.deregister((Serializable)sessionUser.getKey(), (String)sessionUser.getValue());
      }

      this.socketSessions.deregister(this.sessionScope.values());
   }

   static Map getSessionScope() {
      return ((WebsocketChannelManager)CdiUtils.getBeanInstance(WebsocketChannelManager.class, true)).sessionScope;
   }

   static Map getViewScope(boolean create) {
      ViewScope bean = (ViewScope)CdiUtils.getBeanInstance(ViewScope.class, create);
      return (Map)(bean == null ? EMPTY_SCOPE : bean.viewScope);
   }

   static String getChannelId(String channel, Map sessionScope, Map viewScope) {
      String channelId = (String)viewScope.get(channel);
      if (channelId == null) {
         channelId = (String)sessionScope.get(channel);
         if (channelId == null) {
            channelId = (String)APPLICATION_SCOPE.get(channel);
         }
      }

      return channelId;
   }

   private void writeObject(ObjectOutputStream output) throws IOException {
      output.defaultWriteObject();
      output.writeObject(APPLICATION_SCOPE);
      Map sessionUserChannels = new HashMap(this.sessionUsers.size());
      Iterator var3 = this.sessionUsers.values().iterator();

      while(var3.hasNext()) {
         String userId = (String)var3.next();
         sessionUserChannels.put(userId, WebsocketUserManager.getUserChannels().get(userId));
      }

      output.writeObject(sessionUserChannels);
   }

   private void readObject(ObjectInputStream input) throws IOException, ClassNotFoundException {
      input.defaultReadObject();
      APPLICATION_SCOPE.putAll((Map)input.readObject());
      Map sessionUserChannels = (Map)input.readObject();
      Iterator var3 = this.sessionUsers.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry sessionUser = (Map.Entry)var3.next();
         String userId = (String)sessionUser.getValue();
         this.socketUsers.register((Serializable)sessionUser.getKey(), userId);
         WebsocketUserManager.getUserChannels().put(userId, sessionUserChannels.get(userId));
      }

      this.socketSessions.register((Iterable)this.sessionScope.values());
      this.socketSessions.register((Iterable)APPLICATION_SCOPE.values());
   }

   @ViewScoped
   public static class ViewScope implements Serializable {
      private static final long serialVersionUID = 1L;
      private ConcurrentMap viewScope = new ConcurrentHashMap(1);

      @PreDestroy
      protected void deregisterViewScope() {
         WebsocketSessionManager.getInstance().deregister(this.viewScope.values());
      }
   }

   private static enum Scope {
      APPLICATION,
      SESSION,
      VIEW;

      static Scope of(String value, Serializable user) {
         if (value == null) {
            return user == null ? APPLICATION : SESSION;
         } else {
            Scope[] var2 = values();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               Scope scope = var2[var4];
               if (scope.name().equalsIgnoreCase(value) && (user == null || scope != APPLICATION)) {
                  return scope;
               }
            }

            throw new IllegalArgumentException(String.format("f:websocket 'scope' attribute '%s' does not represent a valid scope. It may not be an EL expression and allowed values are 'application', 'session' and 'view', case insensitive. The default is 'application'. When 'user' attribute is specified, then scope defaults to 'session' and may not be 'application'.", value));
         }
      }
   }
}
