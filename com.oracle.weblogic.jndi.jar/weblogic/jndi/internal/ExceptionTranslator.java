package weblogic.jndi.internal;

import java.io.IOException;
import java.io.NotSerializableException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.ConnectException;
import java.rmi.ConnectIOException;
import java.rmi.MarshalException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.ServerError;
import java.rmi.ServerException;
import java.rmi.StubNotFoundException;
import java.rmi.UnmarshalException;
import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.ConfigurationException;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.ServiceUnavailableException;
import javax.security.auth.login.LoginException;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.RequestTimeoutException;
import weblogic.utils.Debug;
import weblogic.utils.StackTraceUtils;

public final class ExceptionTranslator {
   public static NamingException toNamingException(Throwable e) {
      if (e instanceof RemoteRuntimeException) {
         e = ((RemoteRuntimeException)e).getNested();
         Debug.assertion(e instanceof RemoteException, "RemoteRuntimeException must nest a RemoteException");
      }

      Object ne;
      if (e instanceof NamingException) {
         ne = (NamingException)e;
      } else {
         if (e instanceof RemoteException) {
            return toNamingException((RemoteException)e);
         }

         if (e instanceof UnknownHostException) {
            ne = new ServiceUnavailableException(e.getMessage());
         } else if (e instanceof MalformedURLException) {
            ne = new ConfigurationException(e.getMessage());
         } else if (e instanceof NotSerializableException) {
            ne = new ConfigurationException(e.getMessage());
         } else if (e instanceof IOException) {
            ne = new CommunicationException(e.getMessage());
         } else if (e instanceof SecurityException) {
            ne = new AuthenticationException(e.getMessage());
         } else if (e instanceof LoginException) {
            ne = new AuthenticationException(e.getMessage());
         } else {
            if (e instanceof Error) {
               throw (Error)e;
            }

            if (e instanceof RuntimeException) {
               throw (RuntimeException)e;
            }

            ne = new NamingException("Unexpected exception: " + StackTraceUtils.throwable2StackTrace(e));
         }
      }

      ((NamingException)ne).setRootCause(e);
      return (NamingException)ne;
   }

   private static NamingException toNamingException(RemoteException e) {
      Object ne;
      if (e instanceof java.rmi.UnknownHostException) {
         ne = new ServiceUnavailableException(e.getMessage());
      } else if (e instanceof ConnectException) {
         ne = new CommunicationException(e.getMessage());
      } else if (e instanceof ConnectIOException) {
         ne = new CommunicationException(e.getMessage());
      } else if (e instanceof MarshalException) {
         ne = toNamingException(e.detail);
      } else if (e instanceof NoSuchObjectException) {
         ne = new ServiceUnavailableException(e.getMessage());
      } else if (e instanceof StubNotFoundException) {
         ne = new ConfigurationException(e.getMessage());
      } else if (e instanceof UnmarshalException) {
         ne = new CommunicationException(e.getMessage());
      } else if (e instanceof ServerError) {
         ne = new CommunicationException(e.getMessage());
      } else {
         if (e instanceof ServerException) {
            return toNamingException((ServerException)e);
         }

         if (e instanceof RequestTimeoutException) {
            ne = new ServiceUnavailableException(e.getMessage());
         } else if (e instanceof NameAlreadyUnboundException) {
            ne = new NameNotFoundException(e.getMessage());
         } else {
            ne = new NamingException("Unexpected exception: " + StackTraceUtils.throwable2StackTrace(e));
         }
      }

      ((NamingException)ne).setRootCause(e);
      return (NamingException)ne;
   }

   private static NamingException toNamingException(ServerException e) {
      Throwable rootCause = e;
      Throwable nested = e.detail;
      Debug.assertion(nested instanceof RemoteException, "ServerException must nest a RemoteException");
      Object ne;
      if (nested instanceof UnmarshalException) {
         ne = new CommunicationException(e.getMessage());
      } else if (nested instanceof MarshalException) {
         ne = new ConfigurationException(e.getMessage());
      } else if (nested instanceof StubNotFoundException) {
         ne = new ConfigurationException(e.getMessage());
         rootCause = nested;
      } else {
         ne = new NamingException("Unexpected exception: " + StackTraceUtils.throwable2StackTrace(e));
      }

      ((NamingException)ne).setRootCause((Throwable)rootCause);
      return (NamingException)ne;
   }
}
