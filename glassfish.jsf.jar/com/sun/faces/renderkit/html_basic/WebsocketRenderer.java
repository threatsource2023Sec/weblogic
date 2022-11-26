package com.sun.faces.renderkit.html_basic;

import com.sun.faces.cdi.CdiUtils;
import com.sun.faces.push.WebsocketChannelManager;
import com.sun.faces.push.WebsocketFacesListener;
import com.sun.faces.renderkit.RenderKitUtils;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.component.UIWebsocket;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ComponentSystemEventListener;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;

@ListenerFor(
   systemEventClass = PostAddToViewEvent.class
)
public class WebsocketRenderer extends HtmlBasicRenderer implements ComponentSystemEventListener {
   public static final String RENDERER_TYPE = "javax.faces.Websocket";
   private static final String SCRIPT_INIT = "jsf.push.init('%s','%s','%s',%s,%s,%s);";

   public void processEvent(ComponentSystemEvent event) throws AbortProcessingException {
      WebsocketFacesListener.subscribeIfNecessary(event.getFacesContext());
   }

   public void decode(FacesContext context, UIComponent component) {
      this.decodeBehaviors(context, component);
   }

   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      UIWebsocket websocket = (UIWebsocket)component;
      if (WebsocketFacesListener.isNew(context, websocket)) {
         WebsocketChannelManager websocketChannelManager = (WebsocketChannelManager)CdiUtils.getBeanReference(WebsocketChannelManager.class);
         String clientId = websocket.getClientId(context);
         String channel = websocket.getChannel();
         String url = websocketChannelManager.register(context, channel, websocket.getScope(), websocket.getUser());
         String functions = websocket.getOnopen() + "," + websocket.getOnmessage() + "," + websocket.getOnclose();
         String behaviors = getBehaviorScripts(context, websocket);
         boolean connected = websocket.isConnected();
         RenderKitUtils.renderJsfJsIfNecessary(context);
         ResponseWriter writer = context.getResponseWriter();
         writer.startElement("script", component);
         writer.writeAttribute("id", clientId, "id");
         writer.write(String.format("jsf.push.init('%s','%s','%s',%s,%s,%s);", clientId, url, channel, functions, behaviors, connected));
         writer.endElement("script");
      }

   }

   private static String getBehaviorScripts(FacesContext context, UIWebsocket websocket) {
      Map clientBehaviorsByEvent = websocket.getClientBehaviors();
      if (clientBehaviorsByEvent.isEmpty()) {
         return "{}";
      } else {
         String clientId = websocket.getClientId(context);
         StringBuilder scripts = new StringBuilder("{");
         Iterator var5 = clientBehaviorsByEvent.entrySet().iterator();

         while(var5.hasNext()) {
            Map.Entry entry = (Map.Entry)var5.next();
            String event = (String)entry.getKey();
            List clientBehaviors = (List)entry.getValue();
            scripts.append(scripts.length() > 1 ? "," : "").append(event).append(":[");

            for(int i = 0; i < clientBehaviors.size(); ++i) {
               scripts.append(i > 0 ? "," : "").append("function(event){");
               scripts.append(((ClientBehavior)clientBehaviors.get(i)).getScript(ClientBehaviorContext.createClientBehaviorContext(context, websocket, event, clientId, (Collection)null)));
               scripts.append("}");
            }

            scripts.append("]");
         }

         return scripts.append("}").toString();
      }
   }
}
