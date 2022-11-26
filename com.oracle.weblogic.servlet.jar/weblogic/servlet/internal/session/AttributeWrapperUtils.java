package weblogic.servlet.internal.session;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.ejb.Handle;
import javax.ejb.HomeHandle;
import javax.rmi.PortableRemoteObject;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.spi.BusinessHandle;
import weblogic.ejb.spi.BusinessObject;
import weblogic.logging.Loggable;
import weblogic.servlet.internal.AttributeWrapper;
import weblogic.utils.NestedRuntimeException;

public final class AttributeWrapperUtils {
   public static AttributeWrapper wrapObject(String name, Object o) {
      return wrapObject(name, o, (DebugLogger)null);
   }

   public static AttributeWrapper wrapObject(String name, Object o, DebugLogger logger) {
      if (o instanceof AttributeWrapper) {
         return (AttributeWrapper)o;
      } else {
         debugSetAttribute(name, o, logger);
         AttributeWrapper value = wrapEJBObjects(name, o);
         if (value == null) {
            value = wrapRegularObjects(o);
         }

         return value;
      }
   }

   public static Object resolveEJBHandleIfNecessary(String name, Object value) {
      Object ejbHandle = getHandleFromEJBObjects(name, value);
      value = ejbHandle != null ? ejbHandle : value;
      return value;
   }

   private static Object getHandleFromEJBObjects(String name, Object o) {
      if (o instanceof EJBObject) {
         try {
            return ((EJBObject)o).getHandle();
         } catch (RemoteException var4) {
            HTTPSessionLogger.logErrorFindingHandle(name, var4);
            throw new NestedRuntimeException(var4);
         }
      } else if (o instanceof EJBHome) {
         try {
            return ((EJBHome)o).getHomeHandle();
         } catch (RemoteException var5) {
            HTTPSessionLogger.logErrorFindingHomeHandle(name, var5);
            throw new NestedRuntimeException(var5);
         }
      } else {
         if (o instanceof Serializable || o instanceof Remote) {
            if (o instanceof BusinessObject) {
               try {
                  return ((BusinessObject)o)._WL_getBusinessObjectHandle();
               } catch (RemoteException var6) {
                  HTTPSessionLogger.logErrorFindingHandle(name, var6);
                  throw new NestedRuntimeException(var6);
               }
            }

            if (o instanceof Remote) {
               try {
                  BusinessObject bo = (BusinessObject)PortableRemoteObject.narrow(o, BusinessObject.class);

                  try {
                     return bo._WL_getBusinessObjectHandle();
                  } catch (RemoteException var7) {
                     HTTPSessionLogger.logErrorFindingHandle(name, var7);
                     throw new NestedRuntimeException(var7);
                  }
               } catch (ClassCastException var8) {
               }
            } else if (o instanceof BusinessHandle) {
               return o;
            }
         }

         return null;
      }
   }

   private static AttributeWrapper wrapRegularObjects(Object o) {
      return o instanceof Serializable ? new AttributeWrapper(o) : null;
   }

   private static AttributeWrapper wrapEJBObjects(String name, Object o) {
      AttributeWrapper attribWrapper = null;
      Object ejbHandle = getHandleFromEJBObjects(name, o);
      if (ejbHandle != null) {
         attribWrapper = new AttributeWrapper(ejbHandle);
         attribWrapper.setEJBObjectWrapped(true);
      }

      return attribWrapper;
   }

   public static Object unwrapObject(String name, AttributeWrapper wrapper) throws ClassNotFoundException, IOException {
      return unwrapObject(name, wrapper, (DebugLogger)null);
   }

   public static Object unwrapObject(String name, AttributeWrapper wrapper, DebugLogger logger) throws IOException, ClassNotFoundException {
      Object unwrappedObject = wrapper.getObject();
      debugGetAttribute(name, unwrappedObject, logger);
      return wrapper.isEJBObjectWrapped() ? unwrapEJBObjects(name, unwrappedObject) : unwrappedObject;
   }

   private static void debugSetAttribute(String name, Object o, DebugLogger logger) {
      if (logger != null && logger.isDebugEnabled()) {
         Loggable l;
         if (!(o instanceof EJBObject) && !(o instanceof BusinessObject)) {
            if (o instanceof EJBHome) {
               l = HTTPSessionLogger.logSetAttributeEJBHomeLoggable(name);
               logger.debug(l.getMessage());
            }
         } else {
            l = HTTPSessionLogger.logSetAttributeEJBObjectLoggable(name);
            logger.debug(l.getMessage());
         }

      }
   }

   private static Object unwrapEJBObjects(String name, Object unwrappedObject) throws RemoteException {
      if (unwrappedObject instanceof HomeHandle) {
         try {
            return ((HomeHandle)unwrappedObject).getEJBHome();
         } catch (RemoteException var3) {
            HTTPSessionLogger.logErrorReconstructingEJBHome(name, var3);
            throw var3;
         }
      } else if (unwrappedObject instanceof BusinessHandle) {
         try {
            return ((BusinessHandle)unwrappedObject).getBusinessObject();
         } catch (RemoteException var4) {
            HTTPSessionLogger.logErrorReconstructingEJBObject(name, var4);
            throw var4;
         }
      } else if (unwrappedObject instanceof Handle) {
         try {
            return ((Handle)unwrappedObject).getEJBObject();
         } catch (RemoteException var5) {
            HTTPSessionLogger.logErrorReconstructingEJBObject(name, var5);
            throw var5;
         }
      } else {
         return unwrappedObject;
      }
   }

   private static void debugGetAttribute(String name, Object o, DebugLogger logger) {
      if (logger != null && logger.isDebugEnabled()) {
         Loggable l;
         if (o instanceof HomeHandle) {
            l = HTTPSessionLogger.logGetAttributeEJBHomeLoggable(name);
            logger.debug(l.getMessage());
         } else if (o instanceof BusinessHandle || o instanceof Handle) {
            l = HTTPSessionLogger.logGetAttributeEJBObjectLoggable(name);
            logger.debug(l.getMessage());
         }

      }
   }
}
