package weblogic.rmi.extensions.server;

import java.lang.reflect.Method;
import java.rmi.UnmarshalException;
import weblogic.rmi.internal.RuntimeDescriptor;
import weblogic.work.WorkManager;

public interface RuntimeMethodDescriptor {
   Class getDeclaringClass();

   Class[] getParameterTypes();

   Class getReturnType();

   String getSignature();

   int getIndex();

   String getMangledName();

   Method getMethod();

   int getAsyncParameterIndex();

   RuntimeMethodDescriptor getCanonical(RuntimeDescriptor var1) throws UnmarshalException;

   boolean isOnewayTransactionalRequest();

   boolean isTransactionalOnewayResponse();

   boolean isIdempotent();

   short[] getParameterTypeAbbrevs();

   short getReturnTypeAbbrev();

   boolean requiresTransaction();

   WorkManager getWorkManager();

   boolean workManagerAvailable();

   boolean getImplRespondsToClient();

   boolean isOneway();

   boolean isTransactional();

   boolean hasAsyncResponse();

   boolean hasAsyncParameter();

   int getTimeOut();

   String getRemoteExceptionWrapperClassName();
}
