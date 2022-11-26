package weblogic.deployment.jms;

import java.lang.ref.ReferenceQueue;
import javax.jms.Connection;
import javax.jms.JMSException;
import org.jvnet.hk2.annotations.Contract;
import weblogic.common.resourcepool.ResourcePool;
import weblogic.security.subject.AbstractSubject;

@Contract
public interface JMSConnectionHelperService {
   String WEBLOGIC_JMS_PACKAGE = "weblogic.jms";

   Connection getConnection();

   JMSSessionHolder getNewSession(int var1, boolean var2, int var3, boolean var4) throws JMSException;

   String getXAResourceName();

   boolean hasNativeTransactions();

   void pushSubject();

   void popSubject();

   String getPoolName();

   AbstractSubject getSubject();

   int incrementReferenceCount();

   int decrementReferenceCount();

   int getReferenceCount();

   boolean getFactorySetClientID();

   void markAsPooled() throws JMSException;

   void close() throws JMSException;

   PooledSession createPooledWrapper(JMSSessionHolder var1, ResourcePool var2, ReferenceQueue var3) throws JMSException;

   boolean isUsedForPooltesting();

   void setUsedForPooltesting(boolean var1);

   void firstTimeInit() throws JMSException;
}
