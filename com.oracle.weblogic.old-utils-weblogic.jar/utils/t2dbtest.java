package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Date;

public class t2dbtest {
   public static void main(String[] argv) throws Exception {
      if (argv.length != 8) {
         System.out.println("Usage: java utils.t2dbtest username password server weblogic.t2.driver weblogic.t2.url #logins #queries tablename");
      } else {
         String user = argv[0];
         String pw = argv[1];
         String server = argv[2];
         String driverName = argv[3];
         String driverUrl = argv[4];
         int logins = Integer.parseInt(argv[5]);
         int queries = Integer.parseInt(argv[6]);
         Class.forName(driverName).newInstance();

         for(int i = 0; i < logins; ++i) {
            if (server.length() > 0) {
               if (driverUrl.indexOf("?") == -1) {
                  driverUrl = driverUrl + "?server=" + server;
               } else {
                  driverUrl = driverUrl + "&server=" + server;
               }
            }

            Connection conn = DriverManager.getConnection(driverUrl, user, pw);
            System.out.println("Connection: " + i);

            label53:
            for(int j = 0; j < queries; ++j) {
               long snap = System.currentTimeMillis();
               System.out.println("\n" + new Date() + " - Login/Query: " + i + "/" + j);
               String prefix = "select * from ";
               if (System.getProperty("usesql") != null) {
                  prefix = "";
               }

               Statement stmt = conn.createStatement();
               ResultSet rs = stmt.executeQuery(prefix + argv[7]);
               ResultSetMetaData rsmd = rs.getMetaData();
               int colcnt = rsmd.getColumnCount();
               int reccnt = 0;

               while(true) {
                  do {
                     if (!rs.next()) {
                        System.out.println("\nRecord count = " + reccnt + " Total time = " + (double)(System.currentTimeMillis() - snap) / 1000.0);
                        rs.close();
                        stmt.close();
                        continue label53;
                     }

                     ++reccnt;
                  } while(System.getProperty("printit") == null);

                  System.out.println("\nRecord: " + reccnt);

                  for(int c = 1; c <= colcnt; ++c) {
                     System.out.println(rsmd.getColumnName(c) + ": " + rs.getString(c));
                  }
               }
            }

            conn.close();
         }

      }
   }
}
