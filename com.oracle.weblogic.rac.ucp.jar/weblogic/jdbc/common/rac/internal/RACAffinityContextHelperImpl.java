package weblogic.jdbc.common.rac.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import oracle.ucp.UniversalConnectionPoolException;
import oracle.ucp.jdbc.oracle.RACManager;
import oracle.ucp.jdbc.oracle.RACManagerFactory;
import weblogic.jdbc.common.internal.JdbcDebug;
import weblogic.jdbc.common.rac.RACAffinityContextException;
import weblogic.jdbc.common.rac.RACAffinityContextHelper;
import weblogic.jdbc.common.rac.RACInstance;
import weblogic.jdbc.common.rac.RACInstanceFactory;

public class RACAffinityContextHelperImpl implements RACAffinityContextHelper {
   private static final String DEFAULT_AFFINITY_CONTEXT_VERSION = "1.0";
   private static final int AFFINITY_TYPE_ORDINAL_WEBSESSION = 0;
   private static final int AFFINITY_TYPE_ORDINAL_TRANSACTION = 1;
   private boolean useUCPAffinityContextAPI;
   private Method affinityContextFactoryMethod;
   private RACManager affinityContextFactory;
   private Object[] affinityTypes;
   private Method methodGetDatabaseUniqueName;
   private Method methodGetInstanceName;
   private Method methodGetServiceName;
   private Method methodGetAffinityType;

   private RACAffinityContextHelperImpl() {
      this.checkForUCPAffinityContextAPI();
   }

   public static RACAffinityContextHelper getInstance() {
      return RACAffinityContextHelperImpl.SingletonHolder.INSTANCE;
   }

   public Object createAffinityContext(String databaseName, String serviceName, String instanceName) throws RACAffinityContextException {
      return this.createAffinityContextInternal(databaseName, serviceName, instanceName, false);
   }

   public Object createAffinityContext(String databaseName, String serviceName, String instanceName, boolean forInstanceAffinity) throws RACAffinityContextException {
      return this.createAffinityContextInternal(databaseName, serviceName, instanceName, forInstanceAffinity);
   }

   public String getDatabaseName(Object affinityContext) throws RACAffinityContextException {
      if (this.useUCPAffinityContextAPI) {
         try {
            return (String)this.methodGetDatabaseUniqueName.invoke(affinityContext);
         } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var3) {
            throw new RACAffinityContextException(var3);
         }
      } else {
         return (String)this.getAffinityContextField(affinityContext, "getDatabaseUniqueName");
      }
   }

   public String getInstanceName(Object affinityContext) throws RACAffinityContextException {
      if (this.useUCPAffinityContextAPI) {
         try {
            return (String)this.methodGetInstanceName.invoke(affinityContext);
         } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var3) {
            throw new RACAffinityContextException(var3);
         }
      } else {
         return (String)this.getAffinityContextField(affinityContext, "getInstanceName");
      }
   }

   public String getServiceName(Object affinityContext) throws RACAffinityContextException {
      if (this.useUCPAffinityContextAPI) {
         try {
            return (String)this.methodGetServiceName.invoke(affinityContext);
         } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var3) {
            throw new RACAffinityContextException(var3);
         }
      } else {
         return (String)this.getAffinityContextField(affinityContext, "getServiceName");
      }
   }

   public boolean isForInstanceAffinity(Object affinityContext) throws RACAffinityContextException {
      if (this.useUCPAffinityContextAPI) {
         try {
            Object type = this.methodGetAffinityType.invoke(affinityContext);
            return type == this.affinityTypes[1];
         } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var3) {
            throw new RACAffinityContextException(var3);
         }
      } else {
         return (Boolean)this.getAffinityContextField(affinityContext, "isForInstanceAffinity");
      }
   }

   public RACInstance createRACInstance(Object affinityContext) throws RACAffinityContextException {
      return RACInstanceFactory.getInstance().create(this.getDatabaseName(affinityContext), this.getInstanceName(affinityContext), this.getServiceName(affinityContext));
   }

   private Object createAffinityContextInternal(String databaseName, String serviceName, String instanceName, boolean forInstanceAffinity) throws RACAffinityContextException {
      return this.useUCPAffinityContextAPI ? this.createAffinityContextUsingAPI(databaseName, serviceName, instanceName, forInstanceAffinity) : this.createAffinityContextUsingReflection(databaseName, serviceName, instanceName, forInstanceAffinity);
   }

   private Object createAffinityContextUsingAPI(String databaseName, String serviceName, String instanceName, boolean forInstanceAffinity) throws RACAffinityContextException {
      Object type;
      if (forInstanceAffinity) {
         type = this.affinityTypes[1];
      } else {
         type = this.affinityTypes[0];
      }

      try {
         return this.affinityContextFactoryMethod.invoke(this.affinityContextFactory, serviceName, databaseName, instanceName, "1.0", type);
      } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var7) {
         throw new RACAffinityContextException(var7);
      }
   }

   private Object createAffinityContextUsingReflection(String databaseName, String serviceName, String instanceName, boolean forInstanceAffinity) throws RACAffinityContextException {
      try {
         Class ocacc = Class.forName("oracle.ucp.jdbc.oracle.OracleConnectionAffinityContext");
         Constructor init = ocacc.getDeclaredConstructor((Class[])null);
         init.setAccessible(true);
         Object acontext = init.newInstance((Object[])null);
         Method setDatabaseUniqueName = ocacc.getMethod("setDatabaseUniqueName", String.class);
         setDatabaseUniqueName.setAccessible(true);
         setDatabaseUniqueName.invoke(acontext, databaseName);
         Method setServiceName = ocacc.getMethod("setServiceName", String.class);
         setServiceName.setAccessible(true);
         setServiceName.invoke(acontext, serviceName);
         Method setInstanceName = ocacc.getMethod("setInstanceName", String.class);
         setInstanceName.setAccessible(true);
         setInstanceName.invoke(acontext, instanceName);
         Method setForInstanceAffinity = ocacc.getDeclaredMethod("setForInstanceAffinity", Boolean.TYPE);
         setForInstanceAffinity.setAccessible(true);
         setForInstanceAffinity.invoke(acontext, forInstanceAffinity);
         return acontext;
      } catch (IllegalArgumentException | InvocationTargetException | ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException var12) {
         throw new RACAffinityContextException(var12);
      }
   }

   private Object getAffinityContextField(Object affinityContext, String getter) throws RACAffinityContextException {
      try {
         Class ocacc = Class.forName("oracle.ucp.jdbc.oracle.OracleConnectionAffinityContext");
         if (!ocacc.isAssignableFrom(affinityContext.getClass())) {
            throw new RACAffinityContextException("unsupported affinity context object");
         } else {
            Method m = ocacc.getDeclaredMethod(getter, (Class[])null);
            m.setAccessible(true);
            return m.invoke(affinityContext, (Object[])null);
         }
      } catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException var5) {
         throw new RACAffinityContextException(var5);
      }
   }

   private void checkForUCPAffinityContextAPI() {
      try {
         Class classRACAffinityContext = Class.forName("oracle.ucp.jdbc.oracle.RACAffinityContext");
         this.methodGetDatabaseUniqueName = classRACAffinityContext.getMethod("getDatabaseUniqueName");
         this.methodGetInstanceName = classRACAffinityContext.getMethod("getInstanceName");
         this.methodGetServiceName = classRACAffinityContext.getMethod("getServiceName");
         this.methodGetAffinityType = classRACAffinityContext.getMethod("getAffinityType");
         Class classAffinityType = Class.forName("oracle.ucp.jdbc.oracle.RACAffinityContext$AffinityType");
         this.affinityTypes = classAffinityType.getEnumConstants();
         this.affinityContextFactory = RACManagerFactory.getRACManager();
         this.affinityContextFactoryMethod = this.affinityContextFactory.getClass().getMethod("createRACAffinityContext", String.class, String.class, String.class, String.class, classAffinityType);
         this.useUCPAffinityContextAPI = true;
      } catch (SecurityException | UniversalConnectionPoolException | ClassNotFoundException | NoSuchMethodException var3) {
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            JdbcDebug.JDBCRAC.debug("Unable to load UCP RACAffinityContext API: " + var3.toString());
         }
      }

   }

   // $FF: synthetic method
   RACAffinityContextHelperImpl(Object x0) {
      this();
   }

   private static class SingletonHolder {
      public static final RACAffinityContextHelper INSTANCE = new RACAffinityContextHelperImpl();
   }
}
