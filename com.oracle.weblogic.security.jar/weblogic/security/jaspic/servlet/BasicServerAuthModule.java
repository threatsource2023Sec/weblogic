package weblogic.security.jaspic.servlet;

import java.io.IOException;
import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.AuthStatus;
import javax.security.auth.message.MessageInfo;
import javax.security.auth.message.MessagePolicy;
import javax.security.auth.message.callback.PasswordValidationCallback;
import javax.security.auth.message.module.ServerAuthModule;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.security.SubjectUtils;
import weblogic.utils.StringUtils;
import weblogic.utils.encoders.BASE64Decoder;

public class BasicServerAuthModule implements ServerAuthModule {
   private static final String KEY_MUST_AUTHENTICATE = "javax.security.auth.message.MessagePolicy.isMandatory";
   private static final String KEY_CURRENT_USER = "com.oracle.weblogic.servlet.current_subject";
   private MessagePolicy requestPolicy;
   private MessagePolicy responsePolicy;
   private CallbackHandler callbackHandler;
   private ServerAuthSupport serverAuthSupport;

   public void initialize(MessagePolicy requestPolicy, MessagePolicy responsePolicy, CallbackHandler callbackHandler, Map options) {
      this.requestPolicy = requestPolicy;
      this.responsePolicy = responsePolicy;
      this.callbackHandler = callbackHandler;
      this.serverAuthSupport = (ServerAuthSupport)options.get("com.oracle.weblogic.servlet.auth_support");
   }

   public AuthStatus validateRequest(MessageInfo messageInfo, Subject clientSubject, Subject serverSubject) throws AuthException {
      try {
         String[] up = splitAuthHeader((HttpServletRequest)messageInfo.getRequestMessage());
         AuthStatus status = this.doValidation(up, messageInfo, clientSubject);
         if (status == AuthStatus.SEND_CONTINUE) {
            this.sendChallenge((HttpServletResponse)messageInfo.getResponseMessage());
         }

         messageInfo.getMap().put("javax.servlet.http.registerSession", "true");
         return status;
      } catch (IOException var6) {
         throw new AuthException("Unable to parse authentication header");
      } catch (UnsupportedCallbackException var7) {
         throw new AuthException(var7.getMessage());
      }
   }

   public AuthStatus secureResponse(MessageInfo messageInfo, Subject subject) throws AuthException {
      try {
         return this.secureResponse((HttpServletResponse)messageInfo.getResponseMessage());
      } catch (IOException var4) {
         throw new AuthException(var4.toString());
      }
   }

   private AuthStatus secureResponse(HttpServletResponse response) throws AuthException, IOException {
      return AuthStatus.SEND_SUCCESS;
   }

   private AuthStatus toResponseAuthStatus(int status) {
      switch (status) {
         case 200:
            return AuthStatus.SEND_SUCCESS;
         case 401:
            return AuthStatus.SEND_CONTINUE;
         default:
            return AuthStatus.SEND_FAILURE;
      }
   }

   public Class[] getSupportedMessageTypes() {
      return new Class[]{HttpServletRequest.class, HttpServletResponse.class};
   }

   public void cleanSubject(MessageInfo messageInfo, Subject subject) throws AuthException {
   }

   private static AuthStatus useCurrentSubjectOrFail(MessageInfo messageInfo, Subject subject) {
      if (!haveCurrentSubject(messageInfo)) {
         return mustAuthenticate(messageInfo) ? AuthStatus.SEND_CONTINUE : AuthStatus.SUCCESS;
      } else {
         updateSubject(subject, messageInfo);
         return AuthStatus.SUCCESS;
      }
   }

   private static void updateSubject(Subject subject, MessageInfo messageInfo) {
      Subject currentSubject = (Subject)messageInfo.getMap().get("com.oracle.weblogic.servlet.current_subject");
      SubjectUtils.setFrom(subject, currentSubject);
   }

   private static boolean haveCurrentSubject(MessageInfo messageInfo) {
      return messageInfo.getMap().containsKey("com.oracle.weblogic.servlet.current_subject");
   }

   private static boolean mustAuthenticate(MessageInfo messageInfo) {
      return getBooleanValue(messageInfo.getMap(), "javax.security.auth.message.MessagePolicy.isMandatory");
   }

   private static boolean getBooleanValue(Map map, String key) {
      Object value = map.get(key);
      return value instanceof String && Boolean.parseBoolean((String)value);
   }

   private static String[] splitAuthHeader(HttpServletRequest request) throws IOException {
      String auth = request.getHeader("Authorization");
      if (auth == null) {
         return null;
      } else {
         String[] authStr = StringUtils.split(auth, ' ');
         if (!authStr[0].equals("Basic")) {
            return null;
         } else {
            BASE64Decoder base64 = new BASE64Decoder();
            byte[] mydata = base64.decodeBuffer(authStr[1]);
            return StringUtils.split(new String(mydata), ':');
         }
      }
   }

   private void sendChallenge(HttpServletResponse response) throws AuthException {
      try {
         response.setHeader("WWW-Authenticate", this.serverAuthSupport.getRealmBanner());
         response.sendError(401, this.serverAuthSupport.getErrorPage(401));
      } catch (IOException var3) {
         throw new AuthException(var3.toString());
      }
   }

   private AuthStatus doValidation(String[] up, MessageInfo messageInfo, Subject subject) throws UnsupportedCallbackException, IOException {
      if (up == null) {
         return useCurrentSubjectOrFail(messageInfo, subject);
      } else {
         PasswordValidationCallback callback = new ServletPasswordValidationCallback(subject, up[0], up[1].toCharArray(), (HttpServletRequest)messageInfo.getRequestMessage(), (HttpServletResponse)messageInfo.getResponseMessage());
         this.callbackHandler.handle(new Callback[]{callback});
         if (callback.getResult()) {
            return AuthStatus.SUCCESS;
         } else if (mustAuthenticate(messageInfo)) {
            return useCurrentSubjectOrFail(messageInfo, subject);
         } else {
            return this.enforceCredentials() ? AuthStatus.SEND_CONTINUE : AuthStatus.SUCCESS;
         }
      }
   }

   protected boolean enforceCredentials() {
      return this.getServerAuthSupport().isEnforceBasicAuth();
   }

   protected ServerAuthSupport getServerAuthSupport() {
      return this.serverAuthSupport;
   }
}
