package weblogic.jms.client;

import java.util.Set;
import javax.jms.Connection;
import javax.jms.XAJMSContext;
import javax.jms.XASession;
import javax.transaction.xa.XAResource;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.jms.common.WLJMSRuntimeException;

public class XAJMSContextImpl extends JMSContextImpl implements XAJMSContextInternal {
   public XAJMSContextImpl(JMSXAConnectionFactory jmsxaConnectionFactory, ContainerType containerType) {
      super(jmsxaConnectionFactory, containerType, 0.0);
   }

   public XAJMSContextImpl(JMSXAConnectionFactory jmsxaConnectionFactory, ContainerType containerType, String userName, String password) {
      super(jmsxaConnectionFactory, containerType, userName, password, 0.0);
   }

   public XAJMSContextImpl(ContainerType containerType, Set contextSet, Connection connection) {
      super(containerType, contextSet, connection, 0.0);
   }

   public XAJMSContext createXAContext() {
      if (this.containerType == ContainerType.JavaEE_Web_or_EJB) {
         throw new WLJMSRuntimeException(JMSClientExceptionLogger.logMethodForbiddenInJavaEEWebEJBLoggable());
      } else {
         this.checkNotClosed();
         this.disallowSetClientID();
         return new XAJMSContextImpl(this.containerType, this.contextSet, this._getConnection());
      }
   }

   public javax.jms.JMSContext getContext() {
      return this;
   }

   public XAResource getXAResource() {
      XASession xaSession = (XASession)this._getSession();
      return xaSession.getXAResource();
   }
}
