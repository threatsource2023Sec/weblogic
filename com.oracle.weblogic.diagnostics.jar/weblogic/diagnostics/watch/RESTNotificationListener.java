package weblogic.diagnostics.watch;

import com.bea.diagnostics.notifications.InvalidNotificationException;
import com.bea.diagnostics.notifications.Notification;
import com.oracle.weblogic.diagnostics.expressions.EvaluatorFactory;
import com.oracle.weblogic.diagnostics.expressions.ExpressionEvaluator;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response.Status;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.descriptor.WLDFRESTNotificationBean;
import weblogic.diagnostics.watch.i18n.DiagnosticsWatchLogger;
import weblogic.server.GlobalServiceLocator;

public class RESTNotificationListener extends WatchNotificationListenerCommon implements WatchNotificationListener {
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugRESTNotifications");
   private WLDFRESTNotificationBean restNotificationBean;
   private WebTarget webTarget;
   private ServiceLocator serviceLocator = GlobalServiceLocator.getServiceLocator();

   RESTNotificationListener(WLDFRESTNotificationBean restNotificationBean, WatchManagerStatisticsImpl stats) throws InvalidNotificationException, NotificationCreateException {
      super(restNotificationBean, stats);
      this.restNotificationBean = restNotificationBean;
      Client client = ClientBuilder.newClient();
      if (restNotificationBean.getHttpAuthenticationMode().equals("Basic")) {
         String userName = restNotificationBean.getHttpAuthenticationUserName();
         String pwd = restNotificationBean.getHttpAuthenticationPassword();
         client.register(HttpAuthenticationFeature.basic(userName, pwd));
      }

      client.register(JacksonFeature.class);
      URI uri = this.getRESTEndpointURI();
      this.webTarget = client.target(uri);
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Initialized REST target:" + uri);
      }

   }

   public void processWatchNotification(Notification notification) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Processing REST notification:" + notification);
      }

      if (!this.restNotificationBean.isEnabled()) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("REST notification is disabled:" + this.getNotificationName());
         }

      } else {
         Invocation.Builder invBuilder = this.webTarget.request(new String[]{"application/json"});
         Properties customNotificationProps = this.restNotificationBean.getCustomNotificationProperties();
         Map defaultPayload = this.buildDefaultPayload(notification);
         Map notificationPayload = defaultPayload;
         if (customNotificationProps != null && !customNotificationProps.isEmpty()) {
            notificationPayload = this.buildCustomPayload(notification, defaultPayload);
         }

         Response response = null;
         if (this.restNotificationBean.getRestInvocationMethodType().equals("POST")) {
            response = invBuilder.buildPost(Entity.json(notificationPayload)).invoke();
         } else {
            response = invBuilder.buildPut(Entity.json(notificationPayload)).invoke();
         }

         Response.StatusType statusInfo = response.getStatusInfo();
         if (statusInfo.getStatusCode() >= Status.BAD_REQUEST.getStatusCode()) {
            DiagnosticsWatchLogger.logErrorSendingRestNotification(this.restNotificationBean.getName(), this.restNotificationBean.getEndpointURL(), statusInfo.getStatusCode(), statusInfo.getReasonPhrase());
         }

         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("REST response:" + (String)response.readEntity(String.class));
         }

      }
   }

   private Map buildDefaultPayload(Notification notification) {
      Map props = new HashMap();
      List keyList = notification.keyList();
      Iterator var4 = keyList.iterator();

      while(var4.hasNext()) {
         Object key = var4.next();
         props.put(key, notification.getValue(key));
      }

      return props;
   }

   private Map buildCustomPayload(Notification notification, Map defaultPayload) {
      Properties customNotifConfig = this.restNotificationBean.getCustomNotificationProperties();
      Map notificationPayload = new HashMap();
      Set keySet = customNotifConfig.stringPropertyNames();
      EvaluatorFactory evaluatorFactory = (EvaluatorFactory)this.serviceLocator.getService(EvaluatorFactory.class, new Annotation[0]);
      ExpressionEvaluator expressionEvaluator = evaluatorFactory.createEvaluator(new Annotation[0]);

      try {
         ExpressionEvaluationUtil.bindNotification(expressionEvaluator, notification);
         Iterator var8 = keySet.iterator();

         while(var8.hasNext()) {
            String key = (String)var8.next();
            String customExpr = customNotifConfig.getProperty(key);
            Object customExprValue = expressionEvaluator.evaluate(customExpr, Object.class);
            notificationPayload.put(key, customExprValue.toString());
         }
      } finally {
         evaluatorFactory.destroyEvaluator(expressionEvaluator);
      }

      return notificationPayload;
   }

   private URI getRESTEndpointURI() {
      return UriBuilder.fromUri(this.restNotificationBean.getEndpointURL()).build(new Object[0]);
   }
}
