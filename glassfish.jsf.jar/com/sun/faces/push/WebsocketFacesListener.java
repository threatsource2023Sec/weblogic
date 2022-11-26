package com.sun.faces.push;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIWebsocket;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.event.PreRenderViewEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

public class WebsocketFacesListener implements SystemEventListener {
   private static final String SCRIPT_OPEN = "jsf.push.open('%s');";
   private static final String SCRIPT_CLOSE = "jsf.push.close('%s');";

   public static void subscribeIfNecessary(FacesContext context) {
      UIViewRoot view = context.getViewRoot();
      List listeners = view.getListenersForEventClass(PostAddToViewEvent.class);
      if (listeners == null || !listeners.stream().anyMatch((l) -> {
         return l instanceof WebsocketFacesListener;
      })) {
         view.subscribeToViewEvent(PreRenderViewEvent.class, new WebsocketFacesListener());
      }

   }

   public static boolean isNew(FacesContext context, UIWebsocket websocket) {
      return getInitializedWebsockets(context).putIfAbsent(websocket.getClientId(context), websocket.isConnected()) == null;
   }

   public boolean isListenerForSource(Object source) {
      return source instanceof UIViewRoot;
   }

   public void processEvent(SystemEvent event) throws AbortProcessingException {
      if (event instanceof PreRenderViewEvent) {
         FacesContext context = ((ComponentSystemEvent)event).getFacesContext();
         Map initializedWebsockets = getInitializedWebsockets(context);
         if (!context.getPartialViewContext().isAjaxRequest()) {
            initializedWebsockets.clear();
         }

         Iterator var4 = initializedWebsockets.entrySet().iterator();

         while(true) {
            while(true) {
               String clientId;
               boolean connected;
               boolean previouslyConnected;
               do {
                  if (!var4.hasNext()) {
                     return;
                  }

                  Map.Entry initializedWebsocket = (Map.Entry)var4.next();
                  clientId = (String)initializedWebsocket.getKey();
                  UIWebsocket websocket = (UIWebsocket)context.getViewRoot().findComponent(clientId);
                  connected = websocket.isRendered() && websocket.isConnected();
                  previouslyConnected = (Boolean)initializedWebsocket.setValue(connected);
               } while(previouslyConnected == connected);

               String script = String.format(connected ? "jsf.push.open('%s');" : "jsf.push.close('%s');", clientId);
               PartialViewContext pvc = context.getPartialViewContext();
               if (pvc.isAjaxRequest() && !pvc.isRenderAll()) {
                  context.getPartialViewContext().getEvalScripts().add(script);
               } else {
                  UIOutput outputScript = new UIOutput();
                  outputScript.setRendererType("javax.faces.resource.Script");
                  UIOutput content = new UIOutput();
                  content.setValue(script);
                  outputScript.getChildren().add(content);
                  context.getViewRoot().addComponentResource(context, outputScript, "body");
               }
            }
         }
      }
   }

   private static Map getInitializedWebsockets(FacesContext context) {
      Map viewScope = context.getViewRoot().getViewMap();
      Map initializedWebsockets = (Map)viewScope.get(WebsocketFacesListener.class.getName());
      if (initializedWebsockets == null) {
         initializedWebsockets = new HashMap();
         viewScope.put(WebsocketFacesListener.class.getName(), initializedWebsockets);
      }

      return (Map)initializedWebsockets;
   }
}
