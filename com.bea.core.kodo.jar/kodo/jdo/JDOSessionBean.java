package kodo.jdo;

import javax.ejb.EJBException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.resource.cci.ConnectionFactory;
import kodo.ee.KodoSessionBean;
import org.apache.openjpa.kernel.Broker;

public abstract class JDOSessionBean extends KodoSessionBean {
   private final boolean _pmf;

   public JDOSessionBean() {
      this(true);
   }

   public JDOSessionBean(boolean pmf) {
      this._pmf = pmf;
      if (pmf) {
         this.setJNDIName("java:/comp/env/jdo/PersistenceManagerFactory");
      }

   }

   protected PersistenceManager getPersistenceManager() throws EJBException {
      if (!this._pmf) {
         return KodoJDOHelper.toPersistenceManager(this.getBroker());
      } else {
         try {
            return (PersistenceManager)this.getConnectionFactory().getConnection();
         } catch (EJBException var2) {
            throw var2;
         } catch (Exception var3) {
            throw this.ejbException(var3);
         }
      }
   }

   protected Broker getBroker() throws EJBException {
      return this._pmf ? KodoJDOHelper.toBroker(this.getPersistenceManager()) : super.getBroker();
   }

   protected ConnectionFactory newConnectionFactory(String rsrc, ClassLoader loader) {
      return this._pmf ? (ConnectionFactory)JDOHelper.getPersistenceManagerFactory(rsrc, loader) : super.newConnectionFactory(rsrc, loader);
   }
}
