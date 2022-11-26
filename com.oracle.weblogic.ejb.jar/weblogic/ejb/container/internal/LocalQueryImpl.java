package weblogic.ejb.container.internal;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.Properties;
import javax.ejb.FinderException;
import weblogic.ejb.Query;
import weblogic.ejb.container.interfaces.LocalQueryHandler;
import weblogic.ejb20.internal.WLQueryPropertiesImpl;
import weblogic.utils.StackTraceUtilsClient;

public class LocalQueryImpl extends WLQueryPropertiesImpl implements Query {
   private static final long serialVersionUID = -3321561806626875272L;
   private LocalQueryHandler handler;
   private boolean isSql = false;

   public LocalQueryImpl(LocalQueryHandler handler) {
      this.handler = handler;
   }

   public LocalQueryImpl(LocalQueryHandler handler, boolean isSql) {
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
         throw new FinderException(StackTraceUtilsClient.throwable2StackTrace(var4));
      }
   }

   public Collection find(String query, Properties props) throws FinderException {
      this.setProperties(props);

      try {
         return (Collection)this.handler.executeQuery(query, this, false, this.isSql);
      } catch (FinderException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new FinderException(StackTraceUtilsClient.throwable2StackTrace(var5));
      }
   }

   public ResultSet execute(String query) throws FinderException {
      try {
         return (ResultSet)this.handler.executeQuery(query, this, true, this.isSql);
      } catch (FinderException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw new FinderException(StackTraceUtilsClient.throwable2StackTrace(var4));
      }
   }

   public ResultSet execute(String query, Properties props) throws FinderException {
      this.setProperties(props);

      try {
         return (ResultSet)this.handler.executeQuery(query, this, true, this.isSql);
      } catch (FinderException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new FinderException(StackTraceUtilsClient.throwable2StackTrace(var5));
      }
   }
}
