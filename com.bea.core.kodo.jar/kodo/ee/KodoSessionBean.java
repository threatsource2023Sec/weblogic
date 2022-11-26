package kodo.ee;

import java.io.PrintWriter;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.NamingException;

public abstract class KodoSessionBean extends KodoBean implements SessionBean {
   protected SessionContext ctx;

   public void setSessionContext(SessionContext ctx) throws EJBException, RemoteException {
      this.ejbLog("setSessionContext(" + ctx + ")");
      this.ctx = ctx;

      try {
         this.cacheConnectionFactory();
      } catch (EJBException var3) {
         throw var3;
      } catch (NamingException var4) {
         throw this.ejbException(var4);
      } catch (RuntimeException var5) {
         throw this.ejbException(var5);
      }
   }

   public void ejbActivate() throws EJBException, RemoteException {
      this.ejbLog("ejbActivate");
   }

   public void ejbPassivate() throws EJBException, RemoteException {
      this.ejbLog("ejbPassivate");
   }

   public void ejbRemove() throws EJBException, RemoteException {
      this.ejbLog("ejbRemove");
   }

   protected void ejbLog(String msg, Throwable t, PrintWriter logWriter) {
      if (logWriter != null) {
         try {
            logWriter.println(this + "[" + this.ctx + "]: " + msg);
         } catch (Exception var6) {
            logWriter.println("???[???]: " + msg);
         }

         try {
            t.printStackTrace(logWriter);
         } catch (Exception var5) {
         }
      }

   }
}
