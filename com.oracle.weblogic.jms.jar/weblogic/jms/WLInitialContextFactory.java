package weblogic.jms;

import java.security.PrivilegedActionException;
import java.util.Hashtable;
import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.security.auth.Subject;
import weblogic.jms.client.JMSConnectionFactory;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSOBSHelper;
import weblogic.security.Security;

public class WLInitialContextFactory implements InitialContextFactory {
   public static final String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";

   public Context getInitialContext(Hashtable environment) throws NamingException {
      if (JMSDebug.JMSOBS.isDebugEnabled()) {
         JMSDebug.JMSOBS.debug("WLInitialContextFactory:getInitialContext environment=" + JMSOBSHelper.filterProperties(environment));
      }

      Hashtable newEnvironment = (Hashtable)environment.clone();
      newEnvironment.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
      String securityPolicy = (String)newEnvironment.get("weblogic.jndi.securityPolicy");
      if (securityPolicy == null) {
         securityPolicy = "ObjectBased";
         newEnvironment.put("weblogic.jndi.securityPolicy", securityPolicy);
      } else if (!securityPolicy.equalsIgnoreCase("ObjectBased") && !securityPolicy.equalsIgnoreCase("ObjectBasedHybrid")) {
         throw new IllegalArgumentException("Invalid security policy: " + securityPolicy);
      }

      try {
         Object[] obj = JMSConnectionFactory.createSubjectByAnonymous(newEnvironment);
         Context ctx = (Context)obj[0];
         Subject subject = (Subject)obj[1];
         if (securityPolicy.equalsIgnoreCase("ObjectBasedHybrid") && newEnvironment.get("java.naming.security.principal") == null) {
            subject = Security.getCurrentSubject();
         }

         if (JMSDebug.JMSOBS.isDebugEnabled()) {
            JMSDebug.JMSOBS.debug("WLInitialContextFactory:getInitialContext return OBS WLInitialContext ctx=" + ctx + ", subject=" + subject + ", newEnvironment=" + JMSOBSHelper.filterProperties(newEnvironment));
         }

         return new WLInitialContext(ctx, subject, newEnvironment);
      } catch (PrivilegedActionException var7) {
         throw convertException(var7);
      } catch (JMSException var8) {
         throw new NamingException(var8.getMessage());
      }
   }

   static NamingException convertException(PrivilegedActionException pae) {
      Exception e = pae.getException();
      if (e instanceof NamingException) {
         return (NamingException)e;
      } else {
         NamingException ne = new NamingException(e.getMessage());
         ne.setRootCause(e);
         return ne;
      }
   }
}
