package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.Strategy;
import org.apache.openjpa.jdbc.sql.RowManager;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public abstract class AbstractStrategy implements Strategy {
   public String getAlias() {
      return this.getClass().getName();
   }

   public void map(boolean adapt) {
   }

   public void initialize() {
   }

   public void insert(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
   }

   public void update(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
   }

   public void delete(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
   }

   public Boolean isCustomInsert(OpenJPAStateManager sm, JDBCStore store) {
      return Boolean.FALSE;
   }

   public Boolean isCustomUpdate(OpenJPAStateManager sm, JDBCStore store) {
      return Boolean.FALSE;
   }

   public Boolean isCustomDelete(OpenJPAStateManager sm, JDBCStore store) {
      return Boolean.FALSE;
   }

   public void customInsert(OpenJPAStateManager sm, JDBCStore store) throws SQLException {
   }

   public void customUpdate(OpenJPAStateManager sm, JDBCStore store) throws SQLException {
   }

   public void customDelete(OpenJPAStateManager sm, JDBCStore store) throws SQLException {
   }
}
