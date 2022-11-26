package weblogic.ejb20.internal;

import java.io.Serializable;
import javax.ejb.EJBHome;
import javax.ejb.EJBMetaData;
import weblogic.ejb.container.EJBLogger;
import weblogic.iiop.PortableReplaceable;
import weblogic.logging.Loggable;
import weblogic.utils.AssertionError;

public final class EJBMetaDataImpl implements Serializable, EJBMetaData, PortableReplaceable {
   private static final long serialVersionUID = -7427414006230547782L;
   private final EJBHome ejbHome;
   private final String homeInterfaceClassName;
   private final String primaryKeyClassName;
   private final String remoteInterfaceClassName;
   private transient Class homeInterfaceClass;
   private transient Class primaryKeyClass;
   private transient Class remoteInterfaceClass;
   private final boolean isSession;
   private final boolean isStatelessSession;

   public EJBMetaDataImpl(EJBHome eh, Class homeInterfaceClass, Class remoteInterfaceClass, Class primaryKeyClass, boolean isSession, boolean isStatelessSession) {
      if (isSession && primaryKeyClass != null) {
         throw new AssertionError("PK class should be null for session beans!");
      } else {
         this.ejbHome = eh;
         this.homeInterfaceClass = homeInterfaceClass;
         this.remoteInterfaceClass = remoteInterfaceClass;
         this.primaryKeyClass = primaryKeyClass;
         this.isSession = isSession;
         this.isStatelessSession = isStatelessSession;
         this.homeInterfaceClassName = homeInterfaceClass.getName();
         this.remoteInterfaceClassName = remoteInterfaceClass.getName();
         if (primaryKeyClass == null) {
            this.primaryKeyClassName = null;
         } else {
            this.primaryKeyClassName = primaryKeyClass.getName();
         }

      }
   }

   public EJBHome getEJBHome() {
      return this.ejbHome;
   }

   public Class getHomeInterfaceClass() {
      if (this.homeInterfaceClass == null) {
         try {
            this.homeInterfaceClass = this.ejbHome.getClass().getClassLoader().loadClass(this.homeInterfaceClassName);
         } catch (Exception var2) {
            throw new AssertionError("Unable to load home class: " + var2);
         }
      }

      return this.homeInterfaceClass;
   }

   public Class getPrimaryKeyClass() {
      if (this.isSession) {
         Loggable l = EJBLogger.logillegalAttemptToInvokeGetPrimaryKeyClassLoggable();
         throw new RuntimeException(l.getMessageText());
      } else {
         if (this.primaryKeyClass == null) {
            try {
               this.primaryKeyClass = this.ejbHome.getClass().getClassLoader().loadClass(this.primaryKeyClassName);
            } catch (Exception var2) {
               throw new AssertionError("Unable to load pk class: " + var2);
            }
         }

         return this.primaryKeyClass;
      }
   }

   public Class getRemoteInterfaceClass() {
      if (this.remoteInterfaceClass == null) {
         try {
            this.remoteInterfaceClass = this.ejbHome.getClass().getClassLoader().loadClass(this.remoteInterfaceClassName);
         } catch (Exception var2) {
            throw new AssertionError("Unable to load remote interface: " + var2);
         }
      }

      return this.remoteInterfaceClass;
   }

   public boolean isSession() {
      return this.isSession;
   }

   public boolean isStatelessSession() {
      return this.isStatelessSession;
   }
}
