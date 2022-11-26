package utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Schema {
   static Class csfClass = null;
   static Method csfMethod = null;

   public static void main(String[] args) throws Exception {
      String url = "jdbc:cloudscape:demo;create=true";
      String driver = "COM.cloudscape.core.JDBCDriver";
      String user = "";
      String password = "";
      String dbFile = "none";
      boolean verbose = false;
      boolean debug = false;
      Properties csfprops = null;

      try {
         int i = 0;
         url = args[i++];
         driver = args[i++];

         for(int x = 0; x < args.length; ++x) {
            if (args[i].equals("-u")) {
               ++i;
               user = args[i++];
            }

            if (args[i].equals("-p")) {
               ++i;
               password = args[i++];
            }

            if (args[i].equals("-s")) {
               ++i;
               ++i;
               System.out.println("WARNING: The -s option is obsolete. All DBMS properties  besides user and password should be in the URL");
            }

            if (args[i].equals("-verbose")) {
               ++i;
               verbose = true;
            }

            if (args[i].equals("-debug")) {
               ++i;
               debug = true;
            }
         }

         try {
            csfprops = getCsfProps(url);
         } catch (Exception var11) {
            System.out.println("Failed to get csf properties");
            var11.printStackTrace();
            System.out.println("exiting....");
            System.exit(1);
         }

         if (csfprops != null) {
            user = csfprops.getProperty("user");
            password = csfprops.getProperty("password");
            url = csfprops.getProperty("url");
         }

         dbFile = args[i];
         if (debug) {
            System.out.println("Options are set as follows: url=" + url + " driver=" + driver + " user=" + user + " password=" + password + " verbose=" + verbose + " dbFile=" + dbFile);
            System.out.println("exiting....");
            System.exit(1);
         }
      } catch (ArrayIndexOutOfBoundsException var16) {
         System.err.println("Usage: java utils.Schema <url> <driver> [options] <SQL file>\n\nwhere:\n    <url>              JDBC driver URL.\n    <driver>           JDBC driver class pathname.\n    <SQL file>         Text file with SQL statements.\n\nwhere options include:\n    -u <user>          User name to be passed to database.\n    -p <password>      User password to be passed to database.\n    -verbose           Print SQL statements and database messages.\n");
         System.exit(1);
      }

      System.out.println("utils.Schema will use these parameters:");
      System.out.println("       url: " + url);
      System.out.println("    driver: " + driver);
      System.out.println("      user: " + user);
      System.out.println("  password: " + password);
      System.out.println("  SQL file: " + dbFile);

      try {
         Class clas = Class.forName(driver);
         DriverManager.registerDriver((Driver)clas.newInstance());
      } catch (SQLException var12) {
         SQLException e;
         for(e = var12; e.getNextException() != null; e = e.getNextException()) {
            e.printStackTrace();
         }

         e.printStackTrace();
         System.err.println("Database driver " + driver + " not found.");
         System.exit(1);
         return;
      } catch (Exception var13) {
         var13.printStackTrace();
         System.err.println("Database driver " + driver + " not found.");
         System.exit(1);
         return;
      }

      Connection conn;
      try {
         Properties props = new Properties();
         props.setProperty("user", user);
         props.setProperty("password", password);
         if (url != null && url.toLowerCase().matches(".*protocol\\s*=\\s*sdp.*")) {
            props.put("oracle.net.SDP", "true");
         }

         conn = getConnection(url, props);
      } catch (Exception var15) {
         Exception e = var15;
         if (var15 instanceof SQLException) {
            while(((SQLException)e).getNextException() != null) {
               ((SQLException)e).printStackTrace();
               e = ((SQLException)e).getNextException();
            }
         }

         ((Exception)e).printStackTrace();
         System.err.println("Could not make database connection");
         System.exit(1);
         return;
      }

      try {
         executeCommands(conn, dbFile, verbose);
      } catch (SQLException var14) {
         SQLException e;
         for(e = var14; e.getNextException() != null; e = e.getNextException()) {
            e.printStackTrace();
         }

         e.printStackTrace();
         System.out.println("Could not populate tables.");
         System.exit(1);
      }
   }

   private static Connection getConnection(final String url, final Properties props) throws Exception {
      return (Connection)AccessController.doPrivileged(new PrivilegedExceptionAction() {
         public Object run() throws Exception {
            return DriverManager.getConnection(url, props);
         }
      });
   }

   private static String gatherLine(BufferedReader br) throws IOException {
      StringBuffer result = null;
      char terminator = 59;

      String line;
      while((line = br.readLine()) != null) {
         if (result == null) {
            result = new StringBuffer("");
         }

         if (line.trim().length() > 0 && !line.startsWith("#") && !line.startsWith("--")) {
            char lastChar = line.charAt(line.length() - 1);
            boolean termination = lastChar == terminator;
            result.append(termination ? line.substring(0, line.length() - 1) : line + " ");
            if (termination) {
               break;
            }
         }
      }

      return result != null ? result.toString() : null;
   }

   private static void executeCommands(Connection conn, String fileName, boolean verbose) throws IOException, SQLException {
      FileInputStream fis = new FileInputStream(fileName);
      BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

      String line;
      while((line = gatherLine(reader)) != null) {
         if (line.trim().length() > 0) {
            executeDDL(conn, line, verbose);
         }
      }

   }

   private static void executeDDL(Connection conn, String ddl, boolean verbose) throws SQLException {
      if (verbose) {
         System.out.println(ddl);
      }

      Statement stmt = conn.createStatement();

      try {
         stmt.execute(ddl);
      } catch (SQLException var7) {
         SQLException sqle = var7;
         if (verbose) {
            System.err.println(var7);

            try {
               System.err.println("SQL Error Code: " + sqle.getErrorCode());
               System.err.println("SQL State: " + sqle.getSQLState());
            } catch (NullPointerException var6) {
               System.err.println("NullPointer Exception attempting to get SQL Error Code or SQL State");
               var6.printStackTrace();
            }
         }
      }

      stmt.close();
   }

   public static Properties getCsfProps(String Url) throws Exception {
      int i = Url.indexOf(":");
      if (i != -1 && Url.substring(0, i + 1).toLowerCase().equals("csf:")) {
         if (csfClass == null) {
            try {
               csfClass = Class.forName("weblogic.jdbc.common.security.CsfSA");
               csfMethod = csfClass.getMethod("getCredential", Properties.class);
            } catch (NoClassDefFoundError var5) {
               throw new Exception("Oracle Security system not available", var5);
            } catch (Exception var6) {
               throw new Exception("Failed to find getCredential method");
            }
         }

         Properties csfprops = new Properties();
         csfprops.setProperty("url", Url);

         try {
            csfMethod.invoke(csfClass, csfprops);
            return csfprops;
         } catch (Throwable var4) {
            throw new Exception("getting CSF credential failed", var4);
         }
      } else {
         return null;
      }
   }
}
