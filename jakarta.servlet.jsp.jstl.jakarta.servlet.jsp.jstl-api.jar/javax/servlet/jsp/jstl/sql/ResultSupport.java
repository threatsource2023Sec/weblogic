package javax.servlet.jsp.jstl.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSupport {
   public static Result toResult(ResultSet rs) {
      try {
         return new ResultImpl(rs, -1, -1);
      } catch (SQLException var2) {
         return null;
      }
   }

   public static Result toResult(ResultSet rs, int maxRows) {
      try {
         return new ResultImpl(rs, -1, maxRows);
      } catch (SQLException var3) {
         return null;
      }
   }
}
