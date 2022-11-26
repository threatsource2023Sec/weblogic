package kodo.ee;

import java.io.PrintWriter;
import java.io.StringWriter;
import javax.ejb.EJBException;
import javax.ejb.EnterpriseBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.resource.cci.ConnectionFactory;
import javax.rmi.PortableRemoteObject;
import org.apache.openjpa.kernel.Bootstrap;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.FindCallbacks;
import org.apache.openjpa.lib.conf.ConfigurationProvider;
import org.apache.openjpa.lib.conf.ProductDerivations;

public abstract class KodoBean implements EnterpriseBean {
   public static final String CF_JNDI_SUFFIX = "Factory";
   private ConnectionFactory _cf = null;
   private String _rsrc = null;
   private String _jndi = null;
   private Context _context = null;
   private PrintWriter _logWriter = null;

   public KodoBean() {
      this.setJNDIName("java:/comp/env/kodo/BrokerFactory");
   }

   public String getJNDIName() {
      return this._rsrc != null ? this._rsrc + "Factory" : this._jndi;
   }

   public void setJNDIName(String name) {
      this._jndi = name;
   }

   public String getPropertiesResource() {
      return this._rsrc;
   }

   public void setPropertiesResource(String rsrc) {
      this._rsrc = rsrc;
   }

   public void setLogWriter(PrintWriter writer) {
      this._logWriter = writer;
   }

   protected Broker getBroker() throws EJBException {
      try {
         return (Broker)this._cf.getConnection();
      } catch (EJBException var2) {
         throw var2;
      } catch (Exception var3) {
         throw this.ejbException(var3);
      }
   }

   protected Object getById(Object id) throws EJBException {
      this.ejbLog("getById(" + id + ")");
      return this.getBroker().find(id, true, (FindCallbacks)null);
   }

   protected void cacheConnectionFactory() throws NamingException, EJBException {
      Context ctx = this.getContext();
      String jndi = this.getJNDIName();

      try {
         this._cf = (ConnectionFactory)this.lookup(ctx, jndi, ConnectionFactory.class);
      } catch (NamingException var7) {
         if (this._rsrc == null) {
            throw this.ejbException(var7);
         }

         ClassLoader loader = this.getClass().getClassLoader();
         this.ejbLog("[" + loader + "]" + this._rsrc);

         try {
            this._cf = this.newConnectionFactory(this._rsrc, loader);
         } catch (RuntimeException var6) {
            throw this.ejbException(var6);
         }

         this.ejbLog("bindConnectionFactory(" + jndi + ")");
         this.bind(this._cf, ctx, jndi);
      }

   }

   protected void releaseConnectionFactory() {
      this._cf = null;
   }

   protected ConnectionFactory getConnectionFactory() {
      return this._cf;
   }

   protected ConnectionFactory newConnectionFactory(String rsrc, ClassLoader loader) {
      ConfigurationProvider cp = ProductDerivations.load(rsrc, (String)null, loader);
      return (ConnectionFactory)Bootstrap.getBrokerFactory(cp, loader);
   }

   protected Context getContext() throws NamingException {
      if (this._context == null) {
         this._context = new InitialContext();
      }

      return this._context;
   }

   protected Object lookup(Context ctx, String name, Class narrow) throws NamingException {
      return PortableRemoteObject.narrow(ctx.lookup(name), narrow);
   }

   protected void bind(Object obj, Context ctx, String name) throws NamingException {
      ctx.bind(name, obj);
   }

   protected EJBException ejbException(Exception e) {
      StringWriter sout = new StringWriter();
      PrintWriter pw = new PrintWriter(sout);
      e.printStackTrace(pw);
      pw.close();
      this.ejbLog(e.getMessage(), e, this._logWriter);
      throw new EJBException(sout.toString(), e);
   }

   protected void ejbLog(String msg) {
      this.ejbLog(msg, (Throwable)null, this._logWriter);
   }

   protected abstract void ejbLog(String var1, Throwable var2, PrintWriter var3);
}
