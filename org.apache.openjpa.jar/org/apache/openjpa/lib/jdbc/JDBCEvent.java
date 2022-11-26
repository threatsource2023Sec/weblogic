package org.apache.openjpa.lib.jdbc;

import java.sql.Connection;
import java.sql.Statement;
import java.util.EventObject;

public class JDBCEvent extends EventObject {
   public static final short BEFORE_PREPARE_STATEMENT = 1;
   public static final short AFTER_PREPARE_STATEMENT = 2;
   public static final short BEFORE_CREATE_STATEMENT = 3;
   public static final short AFTER_CREATE_STATEMENT = 4;
   public static final short BEFORE_EXECUTE_STATEMENT = 5;
   public static final short AFTER_EXECUTE_STATEMENT = 6;
   public static final short BEFORE_COMMIT = 7;
   public static final short AFTER_COMMIT = 8;
   public static final short BEFORE_ROLLBACK = 9;
   public static final short AFTER_ROLLBACK = 10;
   public static final short AFTER_CONNECT = 11;
   public static final short BEFORE_CLOSE = 12;
   private final short type;
   private final long time;
   private final String sql;
   private final JDBCEvent associatedEvent;
   private final transient Statement statement;

   public JDBCEvent(Connection source, short type, JDBCEvent associatedEvent, Statement statement, String sql) {
      super(source);
      this.type = type;
      this.time = System.currentTimeMillis();
      this.associatedEvent = associatedEvent;
      this.sql = sql;
      this.statement = statement;
   }

   public final short getType() {
      return this.type;
   }

   public final Connection getConnection() {
      return (Connection)this.getSource();
   }

   public final long getTime() {
      return this.time;
   }

   public final JDBCEvent getAssociatedEvent() {
      return this.associatedEvent;
   }

   public final String getSQL() {
      return this.sql;
   }

   public final Statement getStatement() {
      return this.statement;
   }
}
