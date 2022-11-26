package weblogic.ejb20.internal;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Properties;
import javax.ejb.FinderException;
import weblogic.ejb.Query;
import weblogic.ejb20.interfaces.QueryHandler;
import weblogic.utils.StackTraceUtils;

public class QueryImpl extends WLQueryPropertiesImpl implements Query, Serializable {
   private static final long serialVersionUID = -6324059333171473291L;
   private QueryHandler handler;
   private boolean isSql = false;

   public QueryImpl(QueryHandler handler) {
      this.handler = handler;
   }

   public QueryImpl(QueryHandler handler, boolean isSql) {
      this.handler = handler;
      this.isSql = isSql;
   }

   public String getLanguage() {
      return this.isSql ? "SQL" : "EJB QL";
   }

   public Collection find(String query) throws FinderException {
      try {
         return (Collection)this.handler.executeQuery(query, this, false, this.isSql);
      } catch (FinderException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw new FinderException(StackTraceUtils.throwable2StackTrace(var4));
      }
   }

   public Collection find(String query, Properties props) throws FinderException {
      this.setProperties(props);

      try {
         return (Collection)this.handler.executeQuery(query, this, false, this.isSql);
      } catch (FinderException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new FinderException(StackTraceUtils.throwable2StackTrace(var5));
      }
   }

   public ResultSet execute(String query) throws FinderException {
      try {
         return (ResultSet)this.handler.executeQuery(query, this, true, this.isSql);
      } catch (FinderException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw new FinderException(StackTraceUtils.throwable2StackTrace(var4));
      }
   }

   public ResultSet execute(String query, Properties props) throws FinderException {
      this.setProperties(props);

      try {
         return (ResultSet)this.handler.executeQuery(query, this, true, this.isSql);
      } catch (FinderException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new FinderException(StackTraceUtils.throwable2StackTrace(var5));
      }
   }
}
