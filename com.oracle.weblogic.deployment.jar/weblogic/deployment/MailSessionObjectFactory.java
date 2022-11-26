package weblogic.deployment;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.spi.ObjectFactory;
import weblogic.application.naming.EnvReference;
import weblogic.application.naming.EnvUtils;
import weblogic.application.naming.MailSessionReference;
import weblogic.application.utils.ManagementUtils;
import weblogic.management.configuration.MailSessionMBean;

public class MailSessionObjectFactory implements ObjectFactory {
   public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable environment) throws NamingException {
      String user = null;
      String password = null;
      Properties props = null;
      if (obj instanceof MailSessionReference) {
         MailSessionReference ref = (MailSessionReference)obj;
         props = ref.getProperties();
         user = ref.getUser();
         password = ref.getPassword();
      } else {
         if (!(obj instanceof EnvReference)) {
            throw new AssertionError("object factory should have been referenced only from MailSessionReference or EnvReference");
         }

         Map mailSessionsMap = MailSessionObjectFactory.MailSessionBeanLoader.mailSessions;
         EnvReference ref = (EnvReference)obj;
         String transJndiName = EnvUtils.transformJNDIName(ref.getJndiName(), ref.getEnvironment().getApplicationName());
         MailSessionMBean mbean = (MailSessionMBean)mailSessionsMap.get(transJndiName);
         if (mbean == null) {
            String newJndiName = transJndiName.replace('/', '.');
            mbean = (MailSessionMBean)mailSessionsMap.get(newJndiName);
            if (mbean == null) {
               newJndiName = transJndiName.replace('.', '/');
            }

            mbean = (MailSessionMBean)mailSessionsMap.get(newJndiName);
            if (mbean == null) {
               throw new AssertionError("Error received when trying to get a mail session with jndiName: " + transJndiName);
            }
         }

         user = mbean.getSessionUsername();
         password = mbean.getSessionPassword();
         props = mbean.getProperties();
      }

      if (props == null) {
         props = new Properties();
      }

      try {
         return user != null && user.length() > 0 && password != null && password.length() > 0 ? Session.getInstance(props, new MailSessionAuthenticator(user, password)) : Session.getInstance(props, (Authenticator)null);
      } catch (Exception var13) {
         throw new AssertionError("Error received when trying to create a mail session", var13);
      }
   }

   private static final class MailSessionAuthenticator extends Authenticator {
      private String user;
      private String password;

      MailSessionAuthenticator(String user, String password) {
         this.user = user;
         this.password = password;
      }

      protected PasswordAuthentication getPasswordAuthentication() {
         return new PasswordAuthentication(this.user, this.password);
      }
   }

   private static class MailSessionBeanLoader {
      static final Map mailSessions = getMailSessions();

      private static Map getMailSessions() {
         MailSessionMBean[] sessions = ManagementUtils.getDomainMBean().getMailSessions();
         Map mailSessionsMap = new HashMap();

         for(int i = 0; sessions != null && i < sessions.length; ++i) {
            mailSessionsMap.put(sessions[i].getJNDIName(), sessions[i]);
         }

         return mailSessionsMap;
      }
   }
}
