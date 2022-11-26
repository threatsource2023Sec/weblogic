package org.apache.openjpa.jdbc.kernel;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.MappingRepository;
import org.apache.openjpa.jdbc.meta.QueryResultMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.ResultSetResult;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.SQLExceptions;
import org.apache.openjpa.kernel.AbstractStoreQuery;
import org.apache.openjpa.kernel.QueryContext;
import org.apache.openjpa.kernel.StoreQuery;
import org.apache.openjpa.lib.rop.RangeResultObjectProvider;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.util.UserException;
import serp.util.Numbers;

public class SQLStoreQuery extends AbstractStoreQuery {
   private static final Localizer _loc = Localizer.forPackage(SQLStoreQuery.class);
   private final transient JDBCStore _store;

   public SQLStoreQuery(JDBCStore store) {
      this._store = store;
   }

   public JDBCStore getStore() {
      return this._store;
   }

   private static String substituteParams(String sql, List params) throws IOException {
      if (sql.indexOf("?1") == -1) {
         return sql;
      } else {
         List paramOrder = new ArrayList();
         StreamTokenizer tok = new StreamTokenizer(new StringReader(sql));
         tok.resetSyntax();
         tok.quoteChar(39);
         tok.wordChars(48, 57);
         tok.wordChars(63, 63);
         StringBuffer buf = new StringBuffer(sql.length());

         while(true) {
            int ttype;
            while((ttype = tok.nextToken()) != -1) {
               switch (ttype) {
                  case -3:
                     if (tok.sval.startsWith("?") && tok.sval.length() > 1 && tok.sval.substring(1).indexOf("?") == -1) {
                        buf.append("?");
                        paramOrder.add(Integer.valueOf(tok.sval.substring(1)));
                        break;
                     }

                     buf.append(tok.sval);
                     break;
                  case 39:
                     buf.append('\'');
                     if (tok.sval != null) {
                        buf.append(tok.sval);
                        buf.append('\'');
                     }
                     break;
                  default:
                     buf.append((char)ttype);
               }
            }

            List translated = new ArrayList();
            Iterator i = paramOrder.iterator();

            while(i.hasNext()) {
               int index = ((Number)i.next()).intValue() - 1;
               if (index >= params.size()) {
                  throw new UserException(_loc.get("sqlquery-missing-params", sql, String.valueOf(index), params));
               }

               translated.add(params.get(index));
            }

            params.clear();
            params.addAll(translated);
            return buf.toString();
         }
      }
   }

   public boolean supportsParameterDeclarations() {
      return false;
   }

   public boolean supportsDataStoreExecution() {
      return true;
   }

   public StoreQuery.Executor newDataStoreExecutor(ClassMetaData meta, boolean subclasses) {
      return new SQLExecutor(this, meta);
   }

   public boolean requiresCandidateType() {
      return false;
   }

   public boolean requiresParameterDeclarations() {
      return false;
   }

   protected static class SQLExecutor extends AbstractStoreQuery.AbstractExecutor {
      private final ClassMetaData _meta;
      private final boolean _select;
      private final boolean _call;
      private final QueryResultMapping _resultMapping;

      public SQLExecutor(SQLStoreQuery q, ClassMetaData candidate) {
         QueryContext ctx = q.getContext();
         String resultMapping = ctx.getResultMappingName();
         if (resultMapping == null) {
            this._resultMapping = null;
         } else {
            ClassLoader envLoader = ctx.getStoreContext().getClassLoader();
            MappingRepository repos = q.getStore().getConfiguration().getMappingRepositoryInstance();
            this._resultMapping = repos.getQueryResultMapping(ctx.getResultMappingScope(), resultMapping, envLoader, true);
         }

         this._meta = candidate;
         String sql = StringUtils.trimToNull(ctx.getQueryString());
         if (sql == null) {
            throw new UserException(SQLStoreQuery._loc.get("no-sql"));
         } else {
            this._select = q.getStore().getDBDictionary().isSelect(sql);
            this._call = sql.length() > 4 && sql.substring(0, 4).equalsIgnoreCase("call");
         }
      }

      public int getOperation(StoreQuery q) {
         return this._select ? 1 : (q.getContext().getCandidateType() == null && q.getContext().getResultType() == null && q.getContext().getResultMappingName() == null && q.getContext().getResultMappingScope() == null ? 3 : 1);
      }

      public Number executeUpdate(StoreQuery q, Object[] params) {
         JDBCStore store = ((SQLStoreQuery)q).getStore();
         DBDictionary dict = store.getDBDictionary();
         String sql = q.getContext().getQueryString();
         Object paramList;
         if (params.length > 0) {
            paramList = new ArrayList(Arrays.asList(params));

            try {
               sql = SQLStoreQuery.substituteParams(sql, (List)paramList);
            } catch (IOException var48) {
               throw new UserException(var48);
            }
         } else {
            paramList = Collections.EMPTY_LIST;
         }

         SQLBuffer buf = (new SQLBuffer(dict)).append(sql);
         store.getContext().beginStore();
         Connection conn = store.getConnection();
         JDBCFetchConfiguration fetch = (JDBCFetchConfiguration)q.getContext().getFetchConfiguration();
         PreparedStatement stmnt = null;

         Integer var12;
         try {
            stmnt = this.prepareCall(conn, buf);
            buf.setParameters((List)paramList);
            if (stmnt != null) {
               buf.setParameters(stmnt);
            }

            int count = this.executeUpdate(store, conn, stmnt, buf);
            var12 = Numbers.valueOf(count);
         } catch (SQLException var49) {
            throw SQLExceptions.getStore(var49, dict);
         } finally {
            if (stmnt != null) {
               try {
                  stmnt.close();
               } catch (SQLException var46) {
               } finally {
                  stmnt = null;
               }
            }

            try {
               conn.close();
            } catch (SQLException var45) {
            }

         }

         return var12;
      }

      public ResultObjectProvider executeQuery(StoreQuery q, Object[] params, StoreQuery.Range range) {
         JDBCStore store = ((SQLStoreQuery)q).getStore();
         DBDictionary dict = store.getDBDictionary();
         String sql = q.getContext().getQueryString();
         Object paramList;
         if (params.length > 0) {
            paramList = new ArrayList(Arrays.asList(params));

            try {
               sql = SQLStoreQuery.substituteParams(sql, (List)paramList);
            } catch (IOException var18) {
               throw new UserException(var18);
            }
         } else {
            paramList = Collections.EMPTY_LIST;
         }

         SQLBuffer buf = (new SQLBuffer(dict)).append(sql);
         Connection conn = store.getConnection();
         JDBCFetchConfiguration fetch = (JDBCFetchConfiguration)q.getContext().getFetchConfiguration();
         PreparedStatement stmnt = null;

         Object rop;
         try {
            if (this._select && !range.lrs) {
               stmnt = this.prepareStatement(conn, buf);
            } else if (this._select) {
               stmnt = this.prepareStatement(conn, buf, fetch, -1, -1);
            } else if (!range.lrs) {
               stmnt = this.prepareCall(conn, buf);
            } else {
               stmnt = this.prepareCall(conn, buf, fetch, -1, -1);
            }

            int index = 0;
            Iterator i = ((List)paramList).iterator();

            while(true) {
               if (!i.hasNext() || stmnt == null) {
                  ResultSet rs = this.executeQuery(store, conn, stmnt, buf, (List)paramList);
                  ResultSetResult res = stmnt != null ? new ResultSetResult(conn, stmnt, rs, store) : new ResultSetResult(conn, rs, dict);
                  if (this._resultMapping != null) {
                     rop = new MappedQueryResultObjectProvider(this._resultMapping, store, fetch, res);
                  } else if (q.getContext().getCandidateType() != null) {
                     rop = new GenericResultObjectProvider((ClassMapping)this._meta, store, fetch, res);
                  } else {
                     rop = new SQLProjectionResultObjectProvider(store, fetch, res, q.getContext().getResultType());
                  }
                  break;
               }

               ++index;
               dict.setUnknown(stmnt, index, i.next(), (Column)null);
            }
         } catch (SQLException var19) {
            if (stmnt != null) {
               try {
                  stmnt.close();
               } catch (SQLException var17) {
               }
            }

            try {
               conn.close();
            } catch (SQLException var16) {
            }

            throw SQLExceptions.getStore(var19, dict);
         }

         if (range.start != 0L || range.end != Long.MAX_VALUE) {
            rop = new RangeResultObjectProvider((ResultObjectProvider)rop, range.start, range.end);
         }

         return (ResultObjectProvider)rop;
      }

      public String[] getDataStoreActions(StoreQuery q, Object[] params, StoreQuery.Range range) {
         return new String[]{q.getContext().getQueryString()};
      }

      public boolean isPacking(StoreQuery q) {
         return q.getContext().getCandidateType() == null;
      }

      protected PreparedStatement prepareCall(Connection conn, SQLBuffer buf) throws SQLException {
         return buf.prepareCall(conn);
      }

      protected int executeUpdate(JDBCStore store, Connection conn, PreparedStatement stmnt, SQLBuffer buf) throws SQLException {
         int count = false;
         int count;
         if (this._call && !stmnt.execute()) {
            count = stmnt.getUpdateCount();
         } else {
            count = stmnt.executeUpdate();
         }

         return count;
      }

      protected PreparedStatement prepareCall(Connection conn, SQLBuffer buf, JDBCFetchConfiguration fetch, int rsType, int rsConcur) throws SQLException {
         return buf.prepareCall(conn, fetch, rsType, rsConcur);
      }

      protected PreparedStatement prepareStatement(Connection conn, SQLBuffer buf) throws SQLException {
         return buf.prepareStatement(conn);
      }

      protected PreparedStatement prepareStatement(Connection conn, SQLBuffer buf, JDBCFetchConfiguration fetch, int rsType, int rsConcur) throws SQLException {
         return buf.prepareStatement(conn, fetch, rsType, rsConcur);
      }

      protected ResultSet executeQuery(JDBCStore store, Connection conn, PreparedStatement stmnt, SQLBuffer buf, List paramList) throws SQLException {
         return stmnt.executeQuery();
      }
   }
}
