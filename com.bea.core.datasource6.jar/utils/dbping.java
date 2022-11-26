package utils;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.Properties;

public class dbping {
   static Class csfClass = null;
   static Method csfMethod = null;
   static boolean test = false;
   static String msg = null;
   private static final String generalUsage = "Usage: java utils.dbping DB2B  [-d dynamicSections] USER PASS HOST:PORT/DBNAME\nor     java utils.dbping DERBY        USER PASS HOST:PORT/DBNAME\nor     java utils.dbping JCONN2       USER PASS HOST:PORT/DBNAME\nor     java utils.dbping JCONN3       USER PASS HOST:PORT/DBNAME\nor     java utils.dbping JCONNECT     USER PASS HOST:PORT/DBNAME\nor     java utils.dbping INFORMIXB    USER PASS HOST:PORT/DBNAME/INFORMIXSERVER\nor     java utils.dbping MSSQLSERVERB USER PASS HOST:PORT/[DBNAME]\nor     java utils.dbping MYSQL        USER PASS [HOST][:PORT]/[DBNAME]\nor     java utils.dbping ORACLEB      USER PASS HOST:PORT/DBNAME\nor     java utils.dbping ORACLE_THIN  USER PASS HOST:PORT:DBNAME\nor     java utils.dbping POINTBASE    USER PASS HOST[:PORT]/DBNAME\nor     java utils.dbping SYBASEB      USER PASS HOST:PORT/DBNAME";

   void setTest(boolean val) {
      test = val;
   }

   String getMsg() {
      return test ? msg : null;
   }

   public static void main(String[] argv) {
      int dynamicSections = -1;
      int inc = 0;
      msg = null;
      if (argv.length > 2 && argv[1].equals("-d")) {
         try {
            dynamicSections = Integer.parseInt(argv[2]);
         } catch (Throwable var17) {
         }

         if (dynamicSections <= 0) {
            msg = "-d option must be a positive integer";
            System.out.println(msg);
            if (test) {
               return;
            }

            System.exit(1);
         }

         inc = 2;
      }

      if (argv.length < 2 + inc) {
         msg = "Usage: java utils.dbping DB2B  [-d dynamicSections] USER PASS HOST:PORT/DBNAME\nor     java utils.dbping DERBY        USER PASS HOST:PORT/DBNAME\nor     java utils.dbping JCONN2       USER PASS HOST:PORT/DBNAME\nor     java utils.dbping JCONN3       USER PASS HOST:PORT/DBNAME\nor     java utils.dbping JCONNECT     USER PASS HOST:PORT/DBNAME\nor     java utils.dbping INFORMIXB    USER PASS HOST:PORT/DBNAME/INFORMIXSERVER\nor     java utils.dbping MSSQLSERVERB USER PASS HOST:PORT/[DBNAME]\nor     java utils.dbping MYSQL        USER PASS [HOST][:PORT]/[DBNAME]\nor     java utils.dbping ORACLEB      USER PASS HOST:PORT/DBNAME\nor     java utils.dbping ORACLE_THIN  USER PASS HOST:PORT:DBNAME\nor     java utils.dbping POINTBASE    USER PASS HOST[:PORT]/DBNAME\nor     java utils.dbping SYBASEB      USER PASS HOST:PORT/DBNAME";
         System.out.println(msg);
         if (test) {
            return;
         }

         System.exit(1);
      }

      Properties csfprops = null;

      try {
         csfprops = getCsfProps(argv[1 + inc]);
      } catch (Exception var19) {
         msg = "Failed to get csf properties";
         System.out.println(msg);
         var19.printStackTrace();
         if (test) {
            return;
         }

         System.exit(1);
      }

      if (csfprops == null && argv.length != 4 + inc) {
         msg = "Usage: java utils.dbping DB2B  [-d dynamicSections] USER PASS HOST:PORT/DBNAME\nor     java utils.dbping DERBY        USER PASS HOST:PORT/DBNAME\nor     java utils.dbping JCONN2       USER PASS HOST:PORT/DBNAME\nor     java utils.dbping JCONN3       USER PASS HOST:PORT/DBNAME\nor     java utils.dbping JCONNECT     USER PASS HOST:PORT/DBNAME\nor     java utils.dbping INFORMIXB    USER PASS HOST:PORT/DBNAME/INFORMIXSERVER\nor     java utils.dbping MSSQLSERVERB USER PASS HOST:PORT/[DBNAME]\nor     java utils.dbping MYSQL        USER PASS [HOST][:PORT]/[DBNAME]\nor     java utils.dbping ORACLEB      USER PASS HOST:PORT/DBNAME\nor     java utils.dbping ORACLE_THIN  USER PASS HOST:PORT:DBNAME\nor     java utils.dbping POINTBASE    USER PASS HOST[:PORT]/DBNAME\nor     java utils.dbping SYBASEB      USER PASS HOST:PORT/DBNAME";
         System.out.println(msg);
         if (test) {
            return;
         }

         System.exit(1);
      }

      String dbType = argv[0];
      String user = "";
      String password = "";
      String server = "";
      String url = null;
      if (csfprops != null) {
         user = csfprops.getProperty("user");
         password = csfprops.getProperty("password");
         url = csfprops.getProperty("url");
      } else {
         user = argv[1 + inc];
         password = argv[2 + inc];
         server = argv[3 + inc];
      }

      Properties props = new Properties();
      props.put("user", user);
      props.put("password", password);
      int slash = server.indexOf("/");
      int colon = server.indexOf(":");
      String driverstr;
      String dbname;
      if (dbType.equalsIgnoreCase("ORACLE_THIN")) {
         if (csfprops == null) {
            url = "jdbc:oracle:thin:@" + server;
         }

         driverstr = "oracle.jdbc.OracleDriver";
      } else if (dbType.equalsIgnoreCase("DERBY")) {
         if (csfprops == null) {
            if (slash < 1 || colon < 1 || colon >= slash - 1) {
               msg = "Format of last parameter must be HOST:PORT/DBNAME";
               System.out.println(msg);
               if (test) {
                  return;
               }

               System.exit(1);
            }

            url = "jdbc:derby://" + server;
         }

         driverstr = "org.apache.derby.jdbc.ClientDriver";
      } else if (dbType.equalsIgnoreCase("JCONNECT")) {
         if (csfprops == null) {
            if (slash < 1 || colon < 1 || colon >= slash - 1) {
               msg = "Format of last parameter must be HOST:PORT/DBNAME";
               System.out.println(msg);
               if (test) {
                  return;
               }

               System.exit(1);
            }

            url = "jdbc:sybase:Tds:" + server;
         }

         driverstr = "com.sybase.jdbc.SybDriver";
      } else if (dbType.equalsIgnoreCase("JCONN2")) {
         if (csfprops == null) {
            if (slash < 1 || colon < 1 || colon >= slash - 1) {
               msg = "Format of last parameter must be HOST:PORT/DBNAME";
               System.out.println(msg);
               if (test) {
                  return;
               }

               System.exit(1);
            }

            url = "jdbc:sybase:Tds:" + server;
         }

         driverstr = "com.sybase.jdbc2.jdbc.SybDriver";
      } else if (dbType.equalsIgnoreCase("JCONN3")) {
         if (csfprops == null) {
            if (slash < 1 || colon < 1 || colon >= slash - 1) {
               msg = "Format of last parameter must be HOST:PORT/DBNAME";
               System.out.println(msg);
               if (test) {
                  return;
               }

               System.exit(1);
            }

            url = "jdbc:sybase:Tds:" + server;
         }

         driverstr = "com.sybase.jdbc3.jdbc.SybDriver";
      } else {
         String dbname;
         if (dbType.equalsIgnoreCase("MSSQLSERVERB")) {
            if (csfprops == null) {
               if (colon < 1 || slash != -1 && colon >= slash - 1) {
                  msg = "Format of last parameter must be HOST:PORT[/DBNAME]";
                  System.out.println(msg);
                  if (test) {
                     return;
                  }

                  System.exit(1);
               }

               if (slash != -1) {
                  dbname = server.substring(slash + 1);
                  props.put("DatabaseName", dbname);
                  server = server.substring(0, slash);
               } else {
                  dbname = "";
               }

               props.put("PortNumber", server.substring(colon + 1));
               props.put("ServerName", server.substring(0, colon));
               url = "jdbc:weblogic:sqlserver://" + server;
            }

            driverstr = "weblogic.jdbc.sqlserver.SQLServerDriver";
         } else if (dbType.equalsIgnoreCase("ORACLEB")) {
            if (csfprops == null) {
               if (slash < 1 || colon < 1 || colon >= slash - 1) {
                  msg = "Format of last parameter must be HOST:PORT/DBNAME";
                  System.out.println(msg);
                  if (test) {
                     return;
                  }

                  System.exit(1);
               }

               dbname = server.substring(slash + 1);
               server = server.substring(0, slash);
               props.put("PortNumber", server.substring(colon + 1));
               props.put("ServerName", server.substring(0, colon));
               props.put("SID", dbname);
               url = "jdbc:bea:oracle://" + server;
            }

            driverstr = "weblogic.jdbc.oracle.OracleDriver";
         } else if (dbType.equalsIgnoreCase("MYSQL")) {
            if (csfprops == null) {
               if (slash < 0 || colon >= 0 && colon >= slash - 1) {
                  msg = "Format of last parameter must be [HOST][:PORT]/[DBNAME]";
                  System.out.println(msg);
                  if (test) {
                     return;
                  }

                  System.exit(1);
               }

               url = "jdbc:mysql://" + server;
            }

            driverstr = "com.mysql.cj.jdbc.Driver";
         } else if (dbType.equalsIgnoreCase("DB2B")) {
            if (csfprops == null) {
               if (slash < 1 || colon < 1 || colon >= slash - 1) {
                  msg = "Format of last parameter must be HOST:PORT/DBNAME";
                  System.out.println(msg);
                  if (test) {
                     return;
                  }

                  System.exit(1);
               }

               dbname = server.substring(slash + 1);
               server = server.substring(0, slash);
               props.put("PortNumber", server.substring(colon + 1));
               props.put("ServerName", server.substring(0, colon));
               props.put("DatabaseName", dbname);
               url = "jdbc:weblogic:db2://" + server;
            }

            if (dynamicSections > 0) {
               props.put("DynamicSections", "" + dynamicSections);
               props.put("CreateDefaultPackage", "true");
               props.put("ReplacePackage", "true");
            }

            driverstr = "weblogic.jdbc.db2.DB2Driver";
         } else if (dbType.equalsIgnoreCase("SYBASEB")) {
            if (csfprops == null) {
               if (slash < 1 || colon < 1 || colon >= slash - 1) {
                  msg = "Format of last parameter must be HOST:PORT/DBNAME";
                  System.out.println(msg);
                  if (test) {
                     return;
                  }

                  System.exit(1);
               }

               dbname = server.substring(slash + 1);
               server = server.substring(0, slash);
               props.put("PortNumber", server.substring(colon + 1));
               props.put("ServerName", server.substring(0, colon));
               props.put("DatabaseName", dbname);
               url = "jdbc:weblogic:sybase://" + server;
            }

            driverstr = "weblogic.jdbc.sybase.SybaseDriver";
         } else if (dbType.equalsIgnoreCase("INFORMIXB")) {
            if (csfprops == null) {
               int slash2 = server.lastIndexOf("/");
               if (slash < 1 || colon < 1 || colon >= slash - 1 || slash2 <= slash + 1 || slash2 + 1 >= server.length()) {
                  msg = "Format of last parameter must be HOST:PORT/DBNAME/INFORMIXSERVER";
                  System.out.println(msg);
                  if (test) {
                     return;
                  }

                  System.exit(1);
               }

               dbname = server.substring(slash + 1, slash2);
               String informixserver = server.substring(slash2 + 1);
               server = server.substring(0, slash);
               props.put("PortNumber", server.substring(colon + 1));
               props.put("ServerName", server.substring(0, colon));
               props.put("DatabaseName", dbname);
               props.put("InformixServer", informixserver);
               url = "jdbc:weblogic:informix://" + server;
            }

            driverstr = "weblogic.jdbc.informix.InformixDriver";
         } else if (dbType.equalsIgnoreCase("POINTBASE")) {
            if (csfprops == null) {
               if (slash < 1) {
                  msg = "Format of last parameter must be HOST[:PORT]/DBNAME";
                  System.out.println(msg);
                  if (test) {
                     return;
                  }

                  System.exit(1);
               }

               url = "jdbc:pointbase:server://" + server;
            }

            driverstr = "com.pointbase.jdbc.jdbcUniversalDriver";
         } else {
            url = null;
            driverstr = null;
            msg = "Invalid database type specified";
            System.out.println(msg);
            System.out.println("Usage: java utils.dbping DB2B  [-d dynamicSections] USER PASS HOST:PORT/DBNAME\nor     java utils.dbping DERBY        USER PASS HOST:PORT/DBNAME\nor     java utils.dbping JCONN2       USER PASS HOST:PORT/DBNAME\nor     java utils.dbping JCONN3       USER PASS HOST:PORT/DBNAME\nor     java utils.dbping JCONNECT     USER PASS HOST:PORT/DBNAME\nor     java utils.dbping INFORMIXB    USER PASS HOST:PORT/DBNAME/INFORMIXSERVER\nor     java utils.dbping MSSQLSERVERB USER PASS HOST:PORT/[DBNAME]\nor     java utils.dbping MYSQL        USER PASS [HOST][:PORT]/[DBNAME]\nor     java utils.dbping ORACLEB      USER PASS HOST:PORT/DBNAME\nor     java utils.dbping ORACLE_THIN  USER PASS HOST:PORT:DBNAME\nor     java utils.dbping POINTBASE    USER PASS HOST[:PORT]/DBNAME\nor     java utils.dbping SYBASEB      USER PASS HOST:PORT/DBNAME");
            if (test) {
               return;
            }

            System.exit(1);
         }
      }

      if (System.getProperty("debug") != null) {
         DriverManager.setLogWriter(new PrintWriter(System.out));
      }

      Properties propsCopy = (Properties)props.clone();

      try {
         if (!test) {
            Class.forName(driverstr).newInstance();
         }

         if (url != null && url.toLowerCase().matches(".*protocol\\s*=\\s*sdp.*")) {
            props.put("oracle.net.SDP", "true");
         }

         Connection conn = getConnection(url, props);
         if (conn != null) {
            conn.close();
         }
      } catch (Exception var18) {
         msg = "\nError encountered:\n";
         System.out.println(msg);
         var18.printStackTrace();
         if (test) {
            return;
         }

         System.exit(1);
      }

      dbname = "\n**** Success!!! ****\n\nYou can connect to the database in your app using:\n\n";
      if (csfprops == null) {
         dbname = dbname + "  java.util.Properties props = new java.util.Properties();\n";

         String s;
         for(Enumeration e = propsCopy.propertyNames(); e.hasMoreElements(); dbname = dbname + "  props.put(\"" + s + "\", \"" + propsCopy.get(s) + "\");\n") {
            s = (String)e.nextElement();
         }
      }

      dbname = dbname + "  java.sql.Driver d =\n    Class.forName(\"" + driverstr + "\").newInstance();\n  java.sql.Connection conn =\n    Driver.connect(\"" + argv[1 + inc] + "\", props);";
      System.out.println(dbname);
      if (!test) {
         System.exit(0);
      }
   }

   private static Connection getConnection(final String url, final Properties props) throws Exception {
      return (Connection)AccessController.doPrivileged(new PrivilegedExceptionAction() {
         public Object run() throws Exception {
            return dbping.test ? null : DriverManager.getConnection(url, props);
         }
      });
   }

   public static Properties getCsfProps(String Url) throws Exception {
      int i = Url.indexOf(":");
      if (i != -1 && Url.substring(0, i + 1).toLowerCase().equals("csf:")) {
         if (csfClass == null) {
            try {
               csfClass = Class.forName("weblogic.jdbc.common.security.CsfSA");
               csfMethod = csfClass.getMethod("getCredential", Properties.class);
            } catch (NoClassDefFoundError var5) {
               msg = "Oracle Security system not available";
               throw new Exception(msg, var5);
            } catch (Exception var6) {
               msg = "Failed to find getCredential method";
               throw new Exception(msg);
            }
         }

         Properties csfprops = new Properties();
         csfprops.setProperty("url", Url);

         try {
            csfMethod.invoke(csfClass, csfprops);
            return csfprops;
         } catch (Throwable var4) {
            var4.printStackTrace();
            msg = "getting CSF credential failed";
            throw new Exception(msg, var4);
         }
      } else {
         return null;
      }
   }
}
