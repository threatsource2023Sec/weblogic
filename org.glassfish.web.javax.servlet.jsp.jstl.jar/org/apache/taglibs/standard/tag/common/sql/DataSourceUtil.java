package org.apache.taglibs.standard.tag.common.sql;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.jstl.core.Config;
import javax.sql.DataSource;
import org.apache.taglibs.standard.resources.Resources;

public class DataSourceUtil {
   private static final String ESCAPE = "\\";
   private static final String TOKEN = ",";

   static DataSource getDataSource(Object rawDataSource, PageContext pc) throws JspException {
      DataSource dataSource = null;
      if (rawDataSource == null) {
         rawDataSource = Config.find(pc, "javax.servlet.jsp.jstl.sql.dataSource");
      }

      if (rawDataSource == null) {
         return null;
      } else {
         if (rawDataSource instanceof String) {
            try {
               Context ctx = new InitialContext();
               Context envCtx = (Context)ctx.lookup("java:comp/env");
               dataSource = (DataSource)envCtx.lookup((String)rawDataSource);
            } catch (NamingException var5) {
               dataSource = getDataSource((String)rawDataSource);
            }
         } else {
            if (!(rawDataSource instanceof DataSource)) {
               throw new JspException(Resources.getMessage("SQL_DATASOURCE_INVALID_TYPE"));
            }

            dataSource = (DataSource)rawDataSource;
         }

         return dataSource;
      }
   }

   private static DataSource getDataSource(String params) throws JspException {
      DataSourceWrapper dataSource = new DataSourceWrapper();
      String[] paramString = new String[4];
      int escCount = 0;
      int aryCount = 0;
      int begin = 0;

      for(int index = 0; index < params.length(); ++index) {
         char nextChar = params.charAt(index);
         if (",".indexOf(nextChar) != -1 && escCount == 0) {
            paramString[aryCount] = params.substring(begin, index).trim();
            begin = index + 1;
            ++aryCount;
            if (aryCount > 4) {
               throw new JspTagException(Resources.getMessage("JDBC_PARAM_COUNT"));
            }
         }

         if ("\\".indexOf(nextChar) != -1) {
            ++escCount;
         } else {
            escCount = 0;
         }
      }

      paramString[aryCount] = params.substring(begin).trim();
      dataSource.setJdbcURL(paramString[0]);
      if (paramString[1] != null) {
         try {
            dataSource.setDriverClassName(paramString[1]);
         } catch (Exception var8) {
            throw new JspTagException(Resources.getMessage("DRIVER_INVALID_CLASS", (Object)var8.toString()), var8);
         }
      }

      dataSource.setUserName(paramString[2]);
      dataSource.setPassword(paramString[3]);
      return dataSource;
   }
}
