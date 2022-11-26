package weblogic.jdbc.wrapper;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import weblogic.jdbc.common.internal.JDBCHelper;
import weblogic.kernel.KernelStatus;
import weblogic.utils.classloaders.AugmentableClassLoaderManager;
import weblogic.utils.collections.ConcurrentHashMap;
import weblogic.utils.wrapper.WrapperClassFile;
import weblogic.utils.wrapper.WrapperFactory;

public class JDBCWrapperFactory extends WrapperFactory {
   private static JDBCWrapperFactory jdbcWrapperFactory = new JDBCWrapperFactory();
   public static final int WRAPPER_POOL_CONNECTION = 0;
   public static final int WRAPPER_JTS_CONNECTION = 1;
   public static final int WRAPPER_JTA_CONNECTION = 2;
   public static final int WRAPPER_STATEMENT = 3;
   public static final int WRAPPER_PREPARED_STATEMENT = 4;
   public static final int WRAPPER_CALLABLE_STATEMENT = 5;
   public static final int WRAPPER_RESULT_SET = 6;
   public static final int WRAPPER_RESULT_SET_META_DATA = 7;
   public static final int WRAPPER_DATABASE_META_DATA = 8;
   public static final int WRAPPER_ARRAY = 9;
   public static final int WRAPPER_STRUCT = 10;
   public static final int WRAPPER_REF = 11;
   public static final int WRAPPER_CLOB = 12;
   public static final int WRAPPER_BLOB = 13;
   public static final int WRAPPER_PARAMETER_META_DATA = 14;
   public static final int WRAPPER_NCLOB = 15;
   public static final int WRAPPER_SQLXML = 16;
   private boolean AutoConnectionCloseFlag = JDBCHelper.getHelper().getAutoConnectionClose();
   private static boolean setCoreEngine = false;
   private static boolean isCoreEngine = false;
   private static final String[] JDBC_SUPER_CLASSES = new String[]{"weblogic.jdbc.wrapper.PoolConnection", "weblogic.jdbc.wrapper.JTSConnection", "weblogic.jdbc.wrapper.JTAConnection", "weblogic.jdbc.wrapper.Statement", "weblogic.jdbc.wrapper.PreparedStatement", "weblogic.jdbc.wrapper.CallableStatement", "weblogic.jdbc.wrapper.ResultSet", "weblogic.jdbc.wrapper.ResultSetMetaData", "weblogic.jdbc.wrapper.DatabaseMetaData", "weblogic.jdbc.wrapper.Array", "weblogic.jdbc.wrapper.Struct", "weblogic.jdbc.wrapper.Ref", "weblogic.jdbc.wrapper.Clob", "weblogic.jdbc.wrapper.Blob", "weblogic.jdbc.wrapper.ParameterMetaData", "weblogic.jdbc.wrapper.WrapperNClob", "weblogic.jdbc.wrapper.WrapperSQLXML"};
   private static ConcurrentHashMap[] jdbcWrapperClasses;

   public static boolean isJDBCRMIWrapperClass(String name) {
      if (!name.startsWith("weblogic/jdbc/rmi")) {
         return false;
      } else {
         return name.startsWith("weblogic/jdbc/rmi/Serial") || name.startsWith("weblogic/jdbc/rmi/RMIWrapperImpl") || name.startsWith("weblogic/jdbc/rmi/internal/StatementStub") || name.startsWith("weblogic/jdbc/rmi/internal/PreparedStatementStub") || name.startsWith("weblogic/jdbc/rmi/internal/CallableStatementStub") || name.startsWith("weblogic/jdbc/rmi/internal/ResultSetStub") || name.startsWith("weblogic/jdbc/rmi/internal/OracleTBlobStub") || name.startsWith("weblogic/jdbc/rmi/internal/OracleTClobStub") || name.startsWith("weblogic/jdbc/rmi/internal/BlobStub") || name.startsWith("weblogic/jdbc/rmi/internal/ClobStub") || name.startsWith("weblogic/jdbc/rmi/internal/RefStub") || name.startsWith("weblogic/jdbc/rmi/internal/StructStub") || name.startsWith("weblogic/jdbc/rmi/internal/ArrayStub") || name.startsWith("weblogic/jdbc/rmi/internal/ParameterMetaDataStub") || name.startsWith("weblogic/jdbc/rmi/internal/OracleTNClobStub") || name.startsWith("weblogic/jdbc/rmi/internal/SQLXMLStub");
      }
   }

   public static Class generateWrapperClass(String wrapperClassName, boolean remote) {
      int index = wrapperClassName.indexOf("_weblogic_jdbc_rmi");
      if (index == -1) {
         return null;
      } else {
         String superClassName = wrapperClassName.substring(0, index).replace('/', '.');
         String vendorClassName = wrapperClassName.substring(index + 1, wrapperClassName.length());
         index = vendorClassName.indexOf("_weblogic_jdbc");
         vendorClassName = vendorClassName.substring(0, index).replace('_', '.') + vendorClassName.substring(index, vendorClassName.length());
         index = vendorClassName.indexOf(".class");
         vendorClassName = vendorClassName.substring(0, index);
         Class superClass = null;
         Class vendorClass = null;

         try {
            superClass = Class.forName(superClassName);
            vendorClass = AugmentableClassLoaderManager.getAugmentableSystemClassLoader().loadClass(vendorClassName);
         } catch (Throwable var8) {
            return null;
         }

         return !remote ? jdbcWrapperFactory.generateWrapperClass(superClass, vendorClass, vendorClass.getClassLoader()) : jdbcWrapperFactory.generateRemoteClass(superClass, vendorClass, vendorClass.getClassLoader());
      }
   }

   public static Object getWrapper(final int superClassIndex, final Object vendorObj, final boolean remote) {
      Class vendorClass = vendorObj.getClass();
      Class wrapperClass = (Class)jdbcWrapperClasses[superClassIndex].get(vendorClass);
      if (!setCoreEngine) {
         setCoreEngine = true;

         try {
            Class cls = Class.forName("com.bea.core.datasource.DataSourceService");
            isCoreEngine = true;
         } catch (Exception var9) {
         }
      }

      if (wrapperClass == null) {
         if (KernelStatus.isApplet()) {
            wrapperClass = jdbcWrapperFactory.getWrapperClass(JDBC_SUPER_CLASSES[superClassIndex], vendorObj, remote, true, vendorClass.getClassLoader());
         } else {
            final ClassLoader fclassLoader;
            if (isCoreEngine) {
               fclassLoader = JDBCWrapperFactory.class.getClassLoader();
            } else {
               fclassLoader = vendorObj.getClass().getClassLoader();
            }

            wrapperClass = (Class)AccessController.doPrivileged(new PrivilegedAction() {
               public Object run() {
                  return JDBCWrapperFactory.jdbcWrapperFactory.getWrapperClass(JDBCWrapperFactory.JDBC_SUPER_CLASSES[superClassIndex], vendorObj, remote, fclassLoader);
               }
            });
         }

         jdbcWrapperClasses[superClassIndex].put(vendorClass, wrapperClass);
      }

      return jdbcWrapperFactory.createWrapper(wrapperClass, vendorObj, remote);
   }

   public static Object getWrapper(String superClassName, Object vendorObj, boolean remote) {
      ClassLoader classLoader = null;
      if (!setCoreEngine) {
         setCoreEngine = true;

         try {
            Class cls = Class.forName("com.bea.core.datasource.DataSourceService");
            isCoreEngine = true;
         } catch (Exception var5) {
         }
      }

      if (isCoreEngine) {
         classLoader = JDBCWrapperFactory.class.getClassLoader();
      }

      return KernelStatus.isApplet() ? jdbcWrapperFactory.createWrapper(superClassName, vendorObj, remote, true, classLoader) : jdbcWrapperFactory.createWrapper(superClassName, vendorObj, remote, classLoader);
   }

   public int needPreInvocationHandler(Method vendorMethod) {
      return 6;
   }

   public boolean needFinalize() {
      return this.AutoConnectionCloseFlag;
   }

   public int needPostInvocationHandler(Method vendorMethod) {
      return 6;
   }

   public int needInvocationExceptionHandler(Method vendorMethod) {
      return 2;
   }

   public void customerizeWrapperClass(WrapperClassFile cf, Class superClass, Class vendorClass, boolean remote) {
      String vendorClassName = vendorClass.getName();
      String superClassName = superClass.getName();
      if (!JDBCWrapperImpl.class.isAssignableFrom(vendorClass)) {
         if (vendorClassName.equals("oracle.sql.STRUCT")) {
            cf.addInterface("weblogic.jdbc.vendor.oracle.OracleStruct");
         } else if (vendorClassName.equals("oracle.sql.REF")) {
            cf.addInterface("weblogic.jdbc.vendor.oracle.OracleRef");
         } else if (vendorClassName.equals("oracle.sql.ARRAY")) {
            cf.addInterface("weblogic.jdbc.vendor.oracle.OracleArray");
         } else if (!vendorClassName.equals("oracle.sql.CLOB") && !vendorClassName.equals("oracle.sql.NCLOB")) {
            if (vendorClassName.equals("oracle.sql.BLOB")) {
               cf.addInterface("weblogic.jdbc.vendor.oracle.OracleThinBlob");
            }
         } else {
            cf.addInterface("weblogic.jdbc.vendor.oracle.OracleThinClob");
         }
      }

      if (remote) {
         if (superClassName.equals("weblogic.jdbc.rmi.internal.ConnectionImpl")) {
            cf.addInterface("weblogic.jdbc.rmi.internal.Connection");
            cf.addInterface("weblogic.jdbc.rmi.internal.LabelableConnection");
         } else if (superClassName.equals("weblogic.jdbc.rmi.internal.StatementImpl")) {
            cf.addInterface("weblogic.jdbc.rmi.internal.Statement");
         } else if (superClassName.equals("weblogic.jdbc.rmi.internal.PreparedStatementImpl")) {
            cf.addInterface("weblogic.jdbc.rmi.internal.PreparedStatement");
         } else if (superClassName.equals("weblogic.jdbc.rmi.internal.CallableStatementImpl")) {
            cf.addInterface("weblogic.jdbc.rmi.internal.CallableStatement");

            try {
               Method m1 = vendorClass.getMethod("getAsciiStream", Integer.TYPE);
               Method m2 = vendorClass.getMethod("getBinaryStream", Integer.TYPE);
               Method m3 = vendorClass.getMethod("getUnicodeStream", Integer.TYPE);
               if (m1 != null && m2 != null && m3 != null) {
                  cf.addMethods(new Method[]{m1, m2, m3});
               }
            } catch (Exception var10) {
            }
         } else if (superClassName.equals("weblogic.jdbc.rmi.internal.ResultSetImpl")) {
            cf.addInterface("weblogic.jdbc.rmi.internal.ResultSet");
         } else if (superClassName.equals("weblogic.jdbc.rmi.internal.DatabaseMetaDataImpl")) {
            cf.addInterface("weblogic.jdbc.rmi.internal.DatabaseMetaData");
         } else if (superClassName.equals("weblogic.jdbc.rmi.internal.OracleTBlobImpl")) {
            cf.addInterface("weblogic.jdbc.rmi.internal.OracleTBlob");
         } else if (superClassName.equals("weblogic.jdbc.rmi.internal.OracleTClobImpl")) {
            cf.addInterface("weblogic.jdbc.rmi.internal.OracleTClob");
         } else if (superClassName.equals("weblogic.jdbc.rmi.internal.ArrayImpl")) {
            cf.addInterface("weblogic.jdbc.rmi.internal.Array");
         } else if (superClassName.equals("weblogic.jdbc.rmi.internal.RefImpl")) {
            cf.addInterface("weblogic.jdbc.rmi.internal.Ref");
         } else if (superClassName.equals("weblogic.jdbc.rmi.internal.StructImpl")) {
            cf.addInterface("weblogic.jdbc.rmi.internal.Struct");
         } else if (superClassName.equals("weblogic.jdbc.rmi.internal.ParameterMetaDataImpl")) {
            cf.addInterface("weblogic.jdbc.rmi.internal.ParameterMetaData");
         } else if (superClassName.equals("weblogic.jdbc.rmi.internal.OracleTNClobImpl")) {
            cf.addInterface("weblogic.jdbc.rmi.internal.OracleTNClob");
         } else if (superClassName.equals("weblogic.jdbc.rmi.internal.SQLXMLImpl")) {
            cf.addInterface("weblogic.jdbc.rmi.internal.SQLXML");
         }
      }

   }

   static {
      jdbcWrapperClasses = new ConcurrentHashMap[JDBC_SUPER_CLASSES.length];

      for(int i = 0; i < JDBC_SUPER_CLASSES.length; ++i) {
         jdbcWrapperClasses[i] = new ConcurrentHashMap(5);
      }

   }
}
