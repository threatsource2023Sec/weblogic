package com.bea.httppubsub.servlet;

import com.bea.httppubsub.BayeuxMessage;
import com.bea.httppubsub.FactoryFinder;
import com.bea.httppubsub.PubSubConfigFactory;
import com.bea.httppubsub.PubSubLogger;
import com.bea.httppubsub.PubSubServer;
import com.bea.httppubsub.PubSubServerException;
import com.bea.httppubsub.PubSubServerFactory;
import com.bea.httppubsub.bayeux.errors.ErrorFactory;
import com.bea.httppubsub.bayeux.errors.ErrorFactoryImpl;
import com.bea.httppubsub.bayeux.messages.AbstractBayeuxMessage;
import com.bea.httppubsub.bayeux.messages.Advice;
import com.bea.httppubsub.bayeux.messages.BayeuxMessageParser;
import com.bea.httppubsub.bayeux.messages.BayeuxMessageParserImpl;
import com.bea.httppubsub.bayeux.messages.BayeuxParseException;
import com.bea.httppubsub.descriptor.WeblogicPubsubBean;
import com.bea.httppubsub.internal.AbstractTransport;
import com.bea.httppubsub.internal.CallbackPollingTransport;
import com.bea.httppubsub.internal.LongPollingTransport;
import com.bea.httppubsub.internal.RegistrablePubSubServer;
import com.bea.httppubsub.json.JSONArray;
import com.bea.httppubsub.json.JSONObject;
import com.bea.httppubsub.runtime.MBeanManagerHelper;
import com.bea.httppubsub.util.JSONPCallbackFunctionNameValidator;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.servlet.http.AbstractAsyncServlet;
import weblogic.servlet.http.RequestResponseKey;
import weblogic.utils.XSSUtils;
import weblogic.utils.http.BytesToString;
import weblogic.utils.io.Chunk;

public final class ControllerServlet extends AbstractAsyncServlet {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugPubSubBayeux");
   private static final String JSON_MESSAGES = "message";
   private static final String JSON_PARAM = "jsonp";
   private static final String BAYEUX_CONTENT_TYPE = "application/x-www-form-urlencoded";
   private PubSubServer server;
   private ErrorFactory errorFactory;
   private BayeuxMessageParser parser;
   private String contextPath;
   private WeblogicPubsubBean bean;
   private ServletConfig config;
   private HttpSessionRetriever httpSessionRetriever;
   private boolean replicatedSessionEnable = false;
   private Map reconnectMessages;

   public ControllerServlet() {
   }

   public ControllerServlet(String contextPath, WeblogicPubsubBean bean) {
      this.contextPath = contextPath;
      this.bean = bean;
   }

   public PubSubServer getPubSubServer() {
      return this.server;
   }

   public ServletConfig getServletConfig() {
      return this.config;
   }

   public void init(ServletConfig config) throws ServletException {
      super.init(config);
      this.config = config;
      this.reconnectMessages = new ConcurrentHashMap();
      this.errorFactory = new ErrorFactoryImpl();

      try {
         if (this.contextPath == null) {
            ServletContext ctx = config.getServletContext();
            PubSubConfigFactory configFactory = (PubSubConfigFactory)FactoryFinder.getFactory("com.bea.httppubsub.PubSubConfigFactory");
            this.bean = configFactory.getWeblogicPubsubBean(ctx);
            this.contextPath = ctx.getContextPath();
            this.replicatedSessionEnable = configFactory.isReplicatedSessionEnable(ctx);
         }

         this.httpSessionRetriever = new HttpSessionRetrieverImpl(this.replicatedSessionEnable);
         this.parser = new BayeuxMessageParserImpl();
         PubSubServerFactory serverFactory = (PubSubServerFactory)FactoryFinder.getFactory("com.bea.httppubsub.PubSubServerFactory");
         String serverName = this.bean.getServerConfig() == null ? null : this.bean.getServerConfig().getName();
         if (serverName == null) {
            serverName = this.contextPath;
         }

         this.server = serverFactory.lookupPubSubServer(serverName);
         if (this.server == null) {
            this.createMBeanManager(config.getServletContext());
            Hashtable env = new Hashtable(4);
            env.put("com.bea.httppubsub.ServerName", serverName);
            env.put("com.bea.httppubsub.PubSubBean", this.bean);
            env.put("com.bea.httppubsub.ServletContext", config.getServletContext());
            env.put("com.bea.httppubsub.ServletContextPath", this.contextPath);
            this.server = serverFactory.createPubSubServer(env);
         }

         this.setTimeout(this.server.getConnectionIdleTimeout());
         setScavangeInterval(this.server.getConnectionIdleTimeout());
      } catch (Exception var5) {
         PubSubLogger.logControllerServletInitFails(var5.getMessage(), var5);
         throw new ServletException(PubSubLogger.logControllerServletInitFailsLoggable(var5.getMessage(), var5).getMessageBody());
      }

      this.bean = null;
   }

   public void destroy() {
      super.destroy();

      try {
         this.destroyMBeanManager(this.server.getName());
         PubSubServerFactory serverFactory = (PubSubServerFactory)FactoryFinder.getFactory("com.bea.httppubsub.PubSubServerFactory");
         serverFactory.removePubSubServer(this.server);
      } catch (Exception var2) {
      }

   }

   private void createMBeanManager(ServletContext ctx) {
      try {
         MBeanManagerHelper.createMBeanManager(this.bean, ctx).registerWebPubSubRuntimeMBean();
      } catch (Exception var3) {
         PubSubLogger.logFailRegisterWebPubSubRuntimeMBean(ctx.getContextPath(), var3);
      }

   }

   private void destroyMBeanManager(String pubSubServerName) {
      try {
         MBeanManagerHelper.getMBeanManager(pubSubServerName).unregisterWebPubSubRuntimeMBean();
         MBeanManagerHelper.destroyMBeanManager(pubSubServerName);
      } catch (Exception var3) {
         PubSubLogger.logFailUnregisterWebPubSubRuntimeMBean(pubSubServerName, var3);
      }

   }

   private String composeErrorResponse(String errMsg) {
      JSONArray arr = new JSONArray();
      JSONObject json = new JSONObject();
      json.put("channel", (Object)"");
      json.put("successful", false);
      json.put("error", (Object)this.errorFactory.getError(400, errMsg));
      arr.put((Object)json);
      return arr.toString();
   }

   protected boolean doRequest(RequestResponseKey rrk) throws IOException, ServletException {
      HttpServletRequest request = rrk.getRequest();
      String[] jsonMessages = this.getJSONMessages(request);
      if (jsonMessages == null) {
         PubSubLogger.logCannotFetchJsonMessageFromRequest();
         throw new IOException(PubSubLogger.logCannotFetchJsonMessageFromRequestLoggable().getMessageBody());
      } else {
         List bayeuxMessages;
         try {
            bayeuxMessages = this.getBayuexMessages(jsonMessages);
         } catch (BayeuxParseException var11) {
            String errMsg = var11.getCause() != null ? var11.getCause().getMessage() : var11.getMessage();
            rrk.getResponse().setContentType("text/json");
            PrintWriter writer = rrk.getResponse().getWriter();
            writer.write(this.composeErrorResponse(errMsg));
            writer.flush();
            return false;
         }

         if (bayeuxMessages == null) {
            return false;
         } else {
            boolean isCallbackPolling = false;

            try {
               HttpSession session = null;
               if (this.httpSessionRetriever != null) {
                  session = this.httpSessionRetriever.retrieve(request);
               }

               String jsonParameter = request.getParameter("jsonp");
               if (jsonParameter != null && !(new JSONPCallbackFunctionNameValidator(jsonParameter)).validate()) {
                  String errMsg = "the only supported callback function name patterns are: functionName, obj.functionName, obj[\"function-name\"], and combination of those patterns, but without any comment";
                  rrk.getResponse().setContentType("text/json");
                  PrintWriter writer = rrk.getResponse().getWriter();
                  writer.write(this.composeErrorResponse(errMsg));
                  writer.flush();
                  return false;
               } else {
                  Iterator var8 = bayeuxMessages.iterator();

                  while(true) {
                     AbstractBayeuxMessage bayeuxMessage;
                     do {
                        if (!var8.hasNext()) {
                           if (!isCallbackPolling && jsonParameter == null) {
                              return this.server.routeMessages(bayeuxMessages, new LongPollingTransport(rrk, ((RegistrablePubSubServer)this.server).getCookiePath()));
                           }

                           return this.server.routeMessages(bayeuxMessages, new CallbackPollingTransport(rrk, jsonParameter, ((RegistrablePubSubServer)this.server).getCookiePath()));
                        }

                        bayeuxMessage = (AbstractBayeuxMessage)var8.next();
                        bayeuxMessage.setHttpSession(session);
                        String connectionType = bayeuxMessage.getConnectionType();
                        if (connectionType != null && "callback-polling".equals(connectionType)) {
                           isCallbackPolling = true;
                        }
                     } while(bayeuxMessage.getType() != BayeuxMessage.TYPE.CONNECT && bayeuxMessage.getType() != BayeuxMessage.TYPE.RECONNECT);

                     this.reconnectMessages.put(rrk, bayeuxMessage);
                  }
               }
            } catch (PubSubServerException var12) {
               throw new ServletException(var12.getMessage(), var12);
            }
         }
      }
   }

   protected void doResponse(RequestResponseKey rrk, Object context) throws IOException, ServletException {
      AbstractTransport transport = (AbstractTransport)context;
      transport.send();
      this.reconnectMessages.remove(rrk);
   }

   protected void doTimeout(RequestResponseKey rrk) throws IOException, ServletException {
      ServletResponse response = rrk.getResponse();
      AbstractBayeuxMessage reconnectMessage = (AbstractBayeuxMessage)this.reconnectMessages.remove(rrk);
      if (reconnectMessage != null) {
         Advice advice = new Advice();
         advice.setReconnect(Advice.RECONNECT.retry);
         reconnectMessage.setAdvice(advice);
         reconnectMessage.setSuccessful(true);
         Writer out = response.getWriter();
         if (out != null) {
            String header = "[";
            String tailer = "]";
            String contentType = "text/json";
            if ("callback-polling".equals(reconnectMessage.getConnectionType())) {
               String jsonParameter = XSSUtils.encodeXSS(rrk.getRequest().getParameter("jsonp"));
               if (jsonParameter == null) {
                  jsonParameter = "jsonpcallback";
               }

               header = jsonParameter + "([";
               tailer = "])";
               contentType = "text/javascript";
            }

            response.setContentType(contentType);
            out.write(header);
            out.write(reconnectMessage.toJSONResponseString());
            if (logger.isDebugEnabled()) {
               logger.debug("<<< connection idle timeout response: " + reconnectMessage.toJSONResponseString());
            }

            out.write(tailer);
            out.flush();
         }
      }

   }

   private List getBayuexMessages(String[] jsonMessages) {
      if (jsonMessages == null) {
         return null;
      } else {
         List bayeuxMessages = new ArrayList();
         String[] var3 = jsonMessages;
         int var4 = jsonMessages.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String msg = var3[var5];
            List msgs = this.parser.parse(msg);
            this.associateEventWithReconnectForClientId(msgs);
            bayeuxMessages.addAll(msgs);
         }

         return bayeuxMessages;
      }
   }

   private String[] getJSONMessages(HttpServletRequest request) throws IOException {
      String[] jsonMessages = null;
      String contentType = request.getContentType();
      if (contentType == null || contentType.toLowerCase().indexOf("charset") < -1) {
         request.setCharacterEncoding("UTF-8");
      }

      if (contentType != null && !contentType.startsWith("application/x-www-form-urlencoded")) {
         String enc = request.getCharacterEncoding();
         Chunk head = Chunk.getChunk();
         int length = Chunk.chunkFully(head, request.getInputStream());
         if (length <= Chunk.CHUNK_SIZE) {
            jsonMessages = new String[]{BytesToString.newString(head.buf, 0, length, enc)};
         } else {
            byte[] buf = new byte[length];
            int offset = 0;

            for(Chunk tmp = head; tmp != null; tmp = tmp.next) {
               System.arraycopy(tmp.buf, 0, buf, offset, tmp.end);
               offset += tmp.end;
            }

            jsonMessages = new String[]{BytesToString.newString(buf, 0, length, enc)};
         }

         Chunk.releaseChunks(head);
      } else {
         jsonMessages = request.getParameterValues("message");
      }

      return jsonMessages;
   }

   private void associateEventWithReconnectForClientId(List messages) {
      String reconncetClientId = null;
      Iterator var3 = messages.iterator();

      AbstractBayeuxMessage message;
      while(var3.hasNext()) {
         message = (AbstractBayeuxMessage)var3.next();
         if (message.getType() == BayeuxMessage.TYPE.RECONNECT || message.getType() == BayeuxMessage.TYPE.CONNECT) {
            reconncetClientId = message.getClientId();
            break;
         }
      }

      if (reconncetClientId != null) {
         var3 = messages.iterator();

         while(var3.hasNext()) {
            message = (AbstractBayeuxMessage)var3.next();
            if (message.getType() == BayeuxMessage.TYPE.PUBLISH && message.getClientId() == null) {
               message.setClientId(reconncetClientId);
            }
         }
      }

   }
}
