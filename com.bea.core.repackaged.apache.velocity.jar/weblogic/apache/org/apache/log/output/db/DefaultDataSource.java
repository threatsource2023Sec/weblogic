package weblogic.apache.org.apache.log.output.db;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.DataSource;

public class DefaultDataSource implements DataSource {
   private final String m_username;
   private final String m_password;
   private final String m_url;
   private PrintWriter m_logWriter;
   private int m_loginTimeout;

   public DefaultDataSource(String url, String username, String password) {
      this.m_url = url;
      this.m_username = username;
      this.m_password = password;
      this.m_logWriter = new PrintWriter(System.err, true);
   }

   public Connection getConnection() throws SQLException {
      return this.getConnection(this.m_username, this.m_password);
   }

   public Connection getConnection(String username, String password) throws SQLException {
      return DriverManager.getConnection(this.m_url, username, password);
   }

   public int getLoginTimeout() throws SQLException {
      return this.m_loginTimeout;
   }

   public PrintWriter getLogWriter() throws SQLException {
      return this.m_logWriter;
   }

   public void setLoginTimeout(int loginTimeout) throws SQLException {
      this.m_loginTimeout = loginTimeout;
   }

   public void setLogWriter(PrintWriter logWriter) throws SQLException {
      this.m_logWriter = logWriter;
   }
}
