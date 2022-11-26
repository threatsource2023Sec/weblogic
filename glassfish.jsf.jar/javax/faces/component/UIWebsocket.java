package javax.faces.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Pattern;
import javax.el.ValueExpression;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class UIWebsocket extends UIComponentBase implements ClientBehaviorHolder {
   public static final String COMPONENT_TYPE = "javax.faces.Websocket";
   public static final String COMPONENT_FAMILY = "javax.faces.Script";
   private static final Pattern PATTERN_CHANNEL_NAME = Pattern.compile("[\\w.-]+");
   private static final String ERROR_ENDPOINT_NOT_ENABLED = "f:websocket endpoint is not enabled. You need to set web.xml context param 'javax.faces.ENABLE_WEBSOCKET_ENDPOINT' with value 'true'.";
   private static final String ERROR_INVALID_CHANNEL = "f:websocket 'channel' attribute '%s' does not represent a valid channel name. It is required, it may not be anJakarta Expression Language expression and it may only contain alphanumeric characters, hyphens, underscores and periods.";
   private static final String ERROR_INVALID_USER = "f:websocket 'user' attribute '%s' does not represent a valid user identifier. It must implement Serializable and preferably have low memory footprint. Suggestion: use #{request.remoteUser} or #{someLoggedInUser.id}.";
   private static final Collection CONTAINS_EVERYTHING = Collections.unmodifiableList(new ArrayList() {
      private static final long serialVersionUID = 1L;

      public boolean contains(Object object) {
         return true;
      }
   });

   public UIWebsocket() {
      ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
      if (!Boolean.parseBoolean(externalContext.getInitParameter("javax.faces.ENABLE_WEBSOCKET_ENDPOINT"))) {
         throw new IllegalStateException("f:websocket endpoint is not enabled. You need to set web.xml context param 'javax.faces.ENABLE_WEBSOCKET_ENDPOINT' with value 'true'.");
      }
   }

   public String getFamily() {
      return "javax.faces.Script";
   }

   public void setValueExpression(String name, ValueExpression binding) {
      if (!UIWebsocket.PropertyKeys.channel.toString().equals(name) && !UIWebsocket.PropertyKeys.scope.toString().equals(name)) {
         if (UIWebsocket.PropertyKeys.user.toString().equals(name)) {
            Object user = binding.getValue(this.getFacesContext().getELContext());
            if (user != null && !(user instanceof Serializable)) {
               throw new IllegalArgumentException(String.format("f:websocket 'user' attribute '%s' does not represent a valid user identifier. It must implement Serializable and preferably have low memory footprint. Suggestion: use #{request.remoteUser} or #{someLoggedInUser.id}.", user));
            }
         }

         super.setValueExpression(name, binding);
      } else {
         throw new IllegalArgumentException(name);
      }
   }

   public Collection getEventNames() {
      return CONTAINS_EVERYTHING;
   }

   public String getChannel() {
      return (String)this.getStateHelper().eval(UIWebsocket.PropertyKeys.channel);
   }

   public void setChannel(String channel) {
      if (channel != null && PATTERN_CHANNEL_NAME.matcher(channel).matches()) {
         this.getStateHelper().put(UIWebsocket.PropertyKeys.channel, channel);
      } else {
         throw new IllegalArgumentException(String.format("f:websocket 'channel' attribute '%s' does not represent a valid channel name. It is required, it may not be anJakarta Expression Language expression and it may only contain alphanumeric characters, hyphens, underscores and periods.", channel));
      }
   }

   public String getScope() {
      return (String)this.getStateHelper().eval(UIWebsocket.PropertyKeys.scope);
   }

   public void setScope(String scope) {
      this.getStateHelper().put(UIWebsocket.PropertyKeys.scope, scope);
   }

   public Serializable getUser() {
      return (Serializable)this.getStateHelper().eval(UIWebsocket.PropertyKeys.user);
   }

   public void setUser(Serializable user) {
      this.getStateHelper().put(UIWebsocket.PropertyKeys.user, user);
   }

   public String getOnopen() {
      return (String)this.getStateHelper().eval(UIWebsocket.PropertyKeys.onopen);
   }

   public void setOnopen(String onopen) {
      this.getStateHelper().put(UIWebsocket.PropertyKeys.onopen, onopen);
   }

   public String getOnmessage() {
      return (String)this.getStateHelper().eval(UIWebsocket.PropertyKeys.onmessage);
   }

   public void setOnmessage(String onmessage) {
      this.getStateHelper().put(UIWebsocket.PropertyKeys.onmessage, onmessage);
   }

   public String getOnclose() {
      return (String)this.getStateHelper().eval(UIWebsocket.PropertyKeys.onclose);
   }

   public void setOnclose(String onclose) {
      this.getStateHelper().put(UIWebsocket.PropertyKeys.onclose, onclose);
   }

   public boolean isConnected() {
      return (Boolean)this.getStateHelper().eval(UIWebsocket.PropertyKeys.connected, Boolean.TRUE);
   }

   public void setConnected(boolean connected) {
      this.getStateHelper().put(UIWebsocket.PropertyKeys.connected, connected);
   }

   static enum PropertyKeys {
      channel,
      scope,
      user,
      onopen,
      onmessage,
      onclose,
      connected;
   }
}
