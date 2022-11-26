package weblogic.ejb20.internal;

import java.io.Serializable;
import javax.ejb.EJBLocalHome;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NamingException;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.internal.EJBRuntimeUtils;
import weblogic.ejb20.interfaces.LocalHomeHandle;

public final class LocalHomeHandleImpl implements LocalHomeHandle, Serializable {
   private static final long serialVersionUID = -4325019294372383553L;
   private Name localJNDIName;
   private transient EJBLocalHome home = null;

   public LocalHomeHandleImpl() {
   }

   public LocalHomeHandleImpl(EJBLocalHome h, Name localJNDIName) {
      this.home = h;
      this.localJNDIName = localJNDIName;
   }

   public EJBLocalHome getEJBLocalHome() {
      if (this.home == null) {
         try {
            Context ctx = new InitialContext();
            this.home = (EJBLocalHome)ctx.lookup(this.localJNDIName);
         } catch (ClassCastException var2) {
            EJBLogger.logStackTraceAndMessage(var2.getMessage(), var2);
            throw EJBRuntimeUtils.asEJBException("ClassCastException: ", var2);
         } catch (NamingException var3) {
            throw EJBRuntimeUtils.asEJBException("Unable to locate EJBLocalHome: '" + this.localJNDIName, var3);
         }
      }

      return this.home;
   }
}
