package weblogic.jms;

import java.security.PrivilegedActionException;
import java.util.Hashtable;
import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.security.auth.Subject;
import weblogic.jms.client.JMSConnectionFactory;

public class WrappedInitialContextFactory implements InitialContextFactory {
   public static final String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";

   public Context getInitialContext(Hashtable env) throws NamingException {
      Hashtable newEnv = null;
      newEnv = (Hashtable)env.clone();
      newEnv.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
      Context ctx = null;

      try {
         Object[] obj = JMSConnectionFactory.createSubjectByAnonymousforRA(newEnv);
         ctx = (Context)obj[0];
         Subject subject = (Subject)obj[1];
         return new WrappedInitialContext(ctx, subject, newEnv);
      } catch (PrivilegedActionException var6) {
         throw convertException(var6);
      } catch (JMSException var7) {
         throw new NamingException(var7.getMessage());
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
