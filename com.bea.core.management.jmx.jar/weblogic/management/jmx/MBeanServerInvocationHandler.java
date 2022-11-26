package weblogic.management.jmx;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.IdentityHashMap;
import java.util.Map;
import javax.management.Descriptor;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.NotificationBroadcaster;
import javax.management.NotificationEmitter;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.openmbean.CompositeData;
import javax.management.remote.JMXConnector;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.WebLogicObjectName;
import weblogic.utils.collections.WeakConcurrentHashMap;

public class MBeanServerInvocationHandler extends javax.management.MBeanServerInvocationHandler {
   protected WeakReference connection = new WeakReference((Object)null);
   protected JMXConnector connector;
   protected static final IdentityHashMap connectionIDMap = new IdentityHashMap();
   protected ObjectName objectName = null;
   protected ObjectNameManager nameManager;
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugJMXCoreConcise");
   private MBeanInfo mInfo = null;

   public MBeanServerConnection _getConnection() {
      return (MBeanServerConnection)this.connection.get();
   }

   public void _setJMXConnector(JMXConnector conn) {
      this.connector = conn;
   }

   public ObjectName _getObjectName() {
      return this.objectName;
   }

   public static ObjectName getObjectName(Object proxy) {
      if (Proxy.isProxyClass(proxy.getClass())) {
         InvocationHandler ihandler = Proxy.getInvocationHandler(proxy);
         if (ihandler instanceof MBeanServerInvocationHandler) {
            MBeanServerInvocationHandler handler = (MBeanServerInvocationHandler)ihandler;
            return handler._getObjectName();
         }
      }

      throw new IllegalArgumentException("The object specified was not an instance of " + MBeanServerInvocationHandler.class.getName());
   }

   public static MBeanServerConnection getMBeanServerConnection(Object proxy) {
      if (Proxy.isProxyClass(proxy.getClass())) {
         InvocationHandler ihandler = Proxy.getInvocationHandler(proxy);
         if (ihandler instanceof MBeanServerInvocationHandler) {
            MBeanServerInvocationHandler handler = (MBeanServerInvocationHandler)ihandler;
            return handler._getConnection();
         }
      }

      throw new IllegalArgumentException("The object specified was not an instance of " + MBeanServerInvocationHandler.class.getName());
   }

   private WebLogicObjectName getWebLogicObjectName() {
      try {
         return new WebLogicObjectName(this.objectName);
      } catch (MalformedObjectNameException var2) {
         throw new RuntimeException(var2);
      }
   }

   public MBeanServerInvocationHandler(MBeanServerConnection connection, ObjectName objectName) {
      super(connection, objectName);
      this.connection = new WeakReference(connection);
      this.objectName = objectName;
      this.nameManager = MBeanServerInvocationHandler.ObjectNameManagerFactory.getObjectNameManager(connection);
   }

   public MBeanServerInvocationHandler(JMXConnector connector, MBeanServerConnection connection, ObjectName objectName) {
      super(connection, objectName);
      this.connection = new WeakReference(connection);
      this.objectName = objectName;
      this.connector = connector;
      this.nameManager = MBeanServerInvocationHandler.ObjectNameManagerFactory.getObjectNameManager(connection);
   }

   public static Object newProxyInstance(JMXConnector conn, ObjectName objectName, Class interfaceClass, boolean notificationBroadcaster) {
      try {
         if (conn != null) {
            MBeanServerConnection serverConn = null;
            if (!connectionIDMap.containsKey(conn)) {
               serverConn = conn.getMBeanServerConnection();
               connectionIDMap.put(conn, serverConn);
               NotificationListener listener = new JMXConnectionNotificationListener(serverConn, conn);
               conn.addConnectionNotificationListener(listener, (NotificationFilter)null, conn);
            } else {
               serverConn = (MBeanServerConnection)connectionIDMap.get(conn);
            }

            Object o = newProxyInstance(serverConn, objectName, interfaceClass, notificationBroadcaster);
            if (Proxy.isProxyClass(o.getClass())) {
               InvocationHandler ihandler = Proxy.getInvocationHandler(o);
               if (ihandler instanceof MBeanServerInvocationHandler) {
                  ((MBeanServerInvocationHandler)ihandler)._setJMXConnector(conn);
               }
            }

            return o;
         } else {
            return null;
         }
      } catch (IOException var7) {
         return null;
      }
   }

   public static Object newProxyInstance(JMXConnector conn, ObjectName objectName) {
      try {
         if (conn != null) {
            MBeanServerConnection serverConn = null;
            if (!connectionIDMap.containsKey(conn)) {
               serverConn = conn.getMBeanServerConnection();
               connectionIDMap.put(conn, serverConn);
               NotificationListener listener = new JMXConnectionNotificationListener(serverConn, conn);
               conn.addConnectionNotificationListener(listener, (NotificationFilter)null, conn);
            } else {
               serverConn = (MBeanServerConnection)connectionIDMap.get(conn);
            }

            Object o = newProxyInstance(serverConn, objectName);
            if (Proxy.isProxyClass(o.getClass())) {
               InvocationHandler ihandler = Proxy.getInvocationHandler(o);
               if (ihandler instanceof MBeanServerInvocationHandler) {
                  ((MBeanServerInvocationHandler)ihandler)._setJMXConnector(conn);
               }
            }

            return o;
         } else {
            return null;
         }
      } catch (IOException var5) {
         return null;
      }
   }

   public static Object newProxyInstance(MBeanServerConnection connection, ObjectName objectName, Class interfaceClass, boolean notificationBroadcaster) {
      ObjectNameManager nameManager = MBeanServerInvocationHandler.ObjectNameManagerFactory.getObjectNameManager(connection);
      Object existingProxy = nameManager.lookupObject(objectName);
      if (existingProxy != null) {
         return existingProxy;
      } else {
         synchronized(nameManager) {
            existingProxy = nameManager.lookupObject(objectName);
            if (existingProxy != null) {
               return existingProxy;
            } else {
               InvocationHandler handler = new MBeanServerInvocationHandler(connection, objectName);
               Class[] interfaces;
               if (notificationBroadcaster) {
                  interfaces = new Class[]{interfaceClass, NotificationEmitter.class};
               } else {
                  interfaces = new Class[]{interfaceClass};
               }

               Object newProxy = Proxy.newProxyInstance(interfaceClass.getClassLoader(), interfaces, handler);
               nameManager.registerObject(objectName, newProxy);
               return newProxy;
            }
         }
      }
   }

   private Object getDisplayName(Object proxy) throws Throwable {
      Method[] mthds = proxy.getClass().getMethods();
      Method mthd = null;

      for(int i = 0; i < mthds.length; ++i) {
         if (mthds[i].getName().equals("getDisplayName")) {
            mthd = mthds[i];
            break;
         }
      }

      return mthd != null ? this.doInvoke(proxy, mthd, (Object[])null) : null;
   }

   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      MBeanServerConnection conn = (MBeanServerConnection)this.connection.get();
      if (conn == null) {
         throw new RuntimeException("invocation handler has null connection when called for invoke");
      } else if (method.getName().equals("getMBeanInfo") && args == null) {
         if (this.mInfo == null) {
            try {
               this.mInfo = conn.getMBeanInfo(this.objectName);
            } catch (InstanceNotFoundException var10) {
               throw new RuntimeException(var10);
            }
         }

         return this.mInfo;
      } else if (method.getName().equals("hashCode")) {
         String key = conn.toString() + this.objectName.toString();
         return new Integer(key.hashCode());
      } else {
         Object result;
         if (method.getName().equals("equals") && args != null && args.length == 1) {
            result = args[0];
            if (result == null) {
               if (this.objectName == null) {
                  return new Boolean(true);
               }

               return new Boolean(false);
            }

            if (result != null && Proxy.isProxyClass(result.getClass())) {
               InvocationHandler ihandler = Proxy.getInvocationHandler(result);
               if (ihandler instanceof MBeanServerInvocationHandler) {
                  MBeanServerInvocationHandler handler = (MBeanServerInvocationHandler)ihandler;
                  if ((this.objectName == null && handler._getObjectName() == null || this.objectName != null && this.objectName.equals(handler._getObjectName())) && conn.equals(handler._getConnection())) {
                     return new Boolean(true);
                  }

                  return new Boolean(false);
               }
            }
         }

         Class clazz;
         try {
            clazz = Class.forName("weblogic.management.WebLogicMBean");
            if (clazz.isAssignableFrom(proxy.getClass()) && method.getName().equals("getObjectName")) {
               return this.getWebLogicObjectName();
            }
         } catch (ClassNotFoundException var12) {
         }

         Object arg;
         try {
            clazz = Class.forName("weblogic.management.commo.StandardInterface");
            if (clazz.isAssignableFrom(proxy.getClass())) {
               if (method.getName().equals("wls_getObjectName")) {
                  return this.objectName;
               }

               if (method.getName().equals("wls_getDisplayName")) {
                  arg = this.getDisplayName(proxy);
                  if (arg != null) {
                     return arg;
                  }
               }
            }
         } catch (ClassNotFoundException var11) {
         }

         if (method.getName().equals("getType")) {
            return this.objectName.getKeyProperty("Type");
         } else {
            if (args != null) {
               for(int i = 0; i < args.length; ++i) {
                  arg = args[i];
                  if (arg != null) {
                     Class argClass = arg.getClass();
                     if (argClass.isArray()) {
                        if (!argClass.getComponentType().isPrimitive()) {
                           args[i] = this.unwrapProxyArray((Object[])((Object[])arg));
                        }
                     } else {
                        args[i] = this.unwrapProxy(arg);
                     }
                  }
               }
            }

            try {
               result = this.doInvoke(proxy, method, args);
            } catch (IOException var13) {
               if (debug.isDebugEnabled()) {
                  debug.debug("invoke, IOException, " + var13);
                  debug.debug("calling disconnect to clear all object name");
               }

               MBeanServerInvocationHandler.ObjectNameManagerFactory.disconnected(conn);
               throw this.checkThrowsIOException(method, var13);
            }

            if (result instanceof CompositeData) {
               return CompositeData.class.isAssignableFrom(method.getReturnType()) ? result : CompositeDataInvocationHandler.newProxyInstance(conn, (CompositeData)result);
            } else {
               Object[] resultArray;
               int i;
               Class returnType;
               if (!(result instanceof CompositeData[])) {
                  if (result instanceof ObjectName) {
                     returnType = method.getReturnType();
                     if (ObjectName.class.isAssignableFrom(returnType)) {
                        return result;
                     } else {
                        return this.connector != null ? newProxyInstance(this.connector, (ObjectName)result) : newProxyInstance(conn, (ObjectName)result);
                     }
                  } else if (result instanceof ObjectName[]) {
                     returnType = method.getReturnType();
                     if (ObjectName[].class.isAssignableFrom(returnType)) {
                        return result;
                     } else {
                        ObjectName[] objectNames = (ObjectName[])((ObjectName[])result);
                        resultArray = (Object[])((Object[])Array.newInstance(returnType.getComponentType(), objectNames.length));

                        for(i = 0; i < objectNames.length; ++i) {
                           if (objectNames[i] != null) {
                              if (this.connector != null) {
                                 resultArray[i] = newProxyInstance(this.connector, objectNames[i]);
                              } else {
                                 resultArray[i] = newProxyInstance(conn, objectNames[i]);
                              }
                           }
                        }

                        return resultArray;
                     }
                  } else {
                     return result;
                  }
               } else {
                  returnType = method.getReturnType();
                  if (CompositeData[].class.isAssignableFrom(returnType)) {
                     return result;
                  } else {
                     CompositeData[] composites = (CompositeData[])((CompositeData[])result);
                     resultArray = (Object[])((Object[])Array.newInstance(returnType.getComponentType(), composites.length));

                     for(i = 0; i < composites.length; ++i) {
                        resultArray[i] = CompositeDataInvocationHandler.newProxyInstance(conn, composites[i]);
                     }

                     return resultArray;
                  }
               }
            }
         }
      }
   }

   private Object unwrapProxy(Object arg) {
      if (Proxy.isProxyClass(arg.getClass())) {
         InvocationHandler ihandler = Proxy.getInvocationHandler(arg);
         if (ihandler instanceof MBeanServerInvocationHandler) {
            MBeanServerInvocationHandler handler = (MBeanServerInvocationHandler)ihandler;
            return handler._getObjectName();
         }
      }

      return arg;
   }

   private Object[] unwrapProxyArray(Object[] args) {
      if (args.length > 0 && Proxy.isProxyClass(args[0].getClass())) {
         ObjectName[] returnArgs = new ObjectName[args.length];

         for(int i = 0; i < args.length; ++i) {
            returnArgs[i] = (ObjectName)this.unwrapProxy(args[i]);
         }

         return returnArgs;
      } else {
         return args;
      }
   }

   private String determineParamClassName(Class paramClass) {
      String wrappedType = PrimitiveMapper.lookupWrapperClassName(paramClass);
      return wrappedType != null ? wrappedType : paramClass.getName();
   }

   private Object doInvoke(Object proxy, Method method, Object[] args) throws IOException, Throwable {
      Class methodClass = method.getDeclaringClass();
      String methodName = method.getName();
      Class[] paramTypes = method.getParameterTypes();
      Class returnType = method.getReturnType();
      int nargs = args == null ? 0 : args.length;
      if (methodName.equals("toString")) {
         return new String("[MBeanServerInvocationHandler]" + this.objectName);
      } else if (methodClass.equals(NotificationBroadcaster.class) || methodClass.equals(NotificationEmitter.class) || methodName.startsWith("get") && methodName.length() > 3 && nargs == 0 && !returnType.equals(Void.TYPE) || methodName.startsWith("is") && methodName.length() > 2 && nargs == 0 && returnType.equals(Boolean.TYPE) || returnType.equals(Boolean.class) || methodName.startsWith("set") && methodName.length() > 3 && nargs == 1 && returnType.equals(Void.TYPE)) {
         try {
            return super.invoke(proxy, method, args);
         } catch (Throwable var15) {
            throw ExceptionMapper.matchJMXException(method, var15);
         }
      } else {
         MBeanServerConnection conn = (MBeanServerConnection)this.connection.get();
         if (conn == null) {
            throw new RuntimeException("Invocation handler has null connection");
         } else {
            String[] signature = new String[paramTypes.length];

            for(int i = 0; i < paramTypes.length; ++i) {
               if (args[i] == null) {
                  ObjectNameManager nameManager = MBeanServerInvocationHandler.ObjectNameManagerFactory.getObjectNameManager(conn);
                  Class argClass = paramTypes[i];
                  if (argClass.isArray()) {
                     if (nameManager.isClassMapped(argClass.getComponentType())) {
                        signature[i] = ObjectName[].class.getName();
                     } else {
                        signature[i] = this.determineParamClassName(paramTypes[i]);
                     }
                  } else if (nameManager.isClassMapped(argClass)) {
                     signature[i] = ObjectName.class.getName();
                  } else {
                     signature[i] = this.determineParamClassName(paramTypes[i]);
                  }
               } else if (args[i] instanceof ObjectName) {
                  signature[i] = ObjectName.class.getName();
               } else if (args[i] instanceof ObjectName[]) {
                  signature[i] = ObjectName[].class.getName();
               } else {
                  signature[i] = this.determineParamClassName(paramTypes[i]);
               }
            }

            try {
               return conn.invoke(this.objectName, methodName, args, signature);
            } catch (Throwable var14) {
               throw ExceptionMapper.matchJMXException(method, var14);
            }
         }
      }
   }

   private Throwable checkThrowsIOException(Method method, IOException ioe) {
      Class[] exceptionTypes = method.getExceptionTypes();

      for(int i = 0; i < exceptionTypes.length; ++i) {
         Class exceptionType = exceptionTypes[i];
         if (exceptionType.isAssignableFrom(ioe.getClass())) {
            if (debug.isDebugEnabled()) {
               debug.debug("return IOException ");
            }

            return ioe;
         }
      }

      if (debug.isDebugEnabled()) {
         debug.debug("Wrap IOException to RemoteRuntimeException");
      }

      return new RemoteRuntimeException(ioe);
   }

   public static Object newProxyInstance(MBeanServerConnection connection, ObjectName objectName) {
      ObjectNameManager nameManager = MBeanServerInvocationHandler.ObjectNameManagerFactory.getObjectNameManager(connection);
      Object result = nameManager.lookupObject(objectName);
      if (result != null) {
         return result;
      } else {
         MBeanInfo info = null;

         try {
            info = connection.getMBeanInfo(objectName);
         } catch (InstanceNotFoundException var15) {
            throw new RuntimeException(var15);
         } catch (IntrospectionException var16) {
            throw new RuntimeException(var16);
         } catch (ReflectionException var17) {
            throw new RuntimeException(var17);
         } catch (IOException var18) {
            throw new RuntimeException(var18);
         }

         String className = null;
         Class interfaceClass = null;
         if (info instanceof ModelMBeanInfo) {
            Descriptor d = null;

            try {
               d = ((ModelMBeanInfo)info).getMBeanDescriptor();
            } catch (MBeanException var14) {
               throw new RuntimeException(var14);
            }

            className = (String)d.getFieldValue("interfaceClassName");
            if (className != null) {
               try {
                  interfaceClass = Class.forName(className);
               } catch (ClassNotFoundException var13) {
                  ClassLoader cl = Thread.currentThread().getContextClassLoader();

                  try {
                     interfaceClass = cl.loadClass(className);
                  } catch (ClassNotFoundException var12) {
                     throw new RuntimeException(var12);
                  }
               }
            }
         }

         if (interfaceClass == null) {
            className = info.getClassName();

            try {
               interfaceClass = Class.forName(className);
            } catch (ClassNotFoundException var11) {
               throw new RuntimeException(var11);
            }
         }

         result = newProxyInstance(connection, objectName, interfaceClass, true);
         return result;
      }
   }

   public static void clearCache(MBeanServerConnection serverConn) {
      MBeanServerInvocationHandler.ObjectNameManagerFactory.disconnected(serverConn);
      connectionIDMap.remove(serverConn);
   }

   static class ObjectNameManagerFactory {
      private static final Map nameManagersByConnection = new WeakConcurrentHashMap();

      static ObjectNameManager getObjectNameManager(MBeanServerConnection connection) {
         WeakReference resultReference = (WeakReference)nameManagersByConnection.get(connection);
         ObjectNameManager result = null;
         if (resultReference != null) {
            result = (ObjectNameManager)resultReference.get();
         }

         if (result == null) {
            result = new InvocationHandlerObjectNameManager();
            nameManagersByConnection.put(connection, new WeakReference(result));
         }

         return (ObjectNameManager)result;
      }

      static void disconnected(MBeanServerConnection connection) {
         if (connection != null) {
            nameManagersByConnection.remove(connection);
         }

      }
   }
}
