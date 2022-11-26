package weblogic.deployment.jms;

import java.lang.ref.ReferenceQueue;
import javax.jms.JMSContext;
import org.jvnet.hk2.annotations.Contract;
import weblogic.common.resourcepool.ResourcePool;
import weblogic.security.subject.AbstractSubject;

@Contract
public interface PrimaryContextHelperService {
   boolean getFactorySetClientID();

   void markAsPooled();

   void close();

   AbstractSubject getSubject();

   int incrementReferenceCount();

   int decrementReferenceCount();

   int getReferenceCount();

   PooledSecondaryContext createPooledWrapper(SecondaryContextHolder var1, ResourcePool var2, ReferenceQueue var3);

   boolean isUsedForPooltesting();

   void setUsedForPooltesting(boolean var1);

   SecondaryContextHolder getNewSecondaryContext(int var1, boolean var2);

   JMSContext getPrimaryContext();

   void firstTimeInit();

   String getXAResourceName();

   boolean hasNativeTransactions();
}
