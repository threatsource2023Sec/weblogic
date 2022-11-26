package org.apache.openjpa.jdbc.sql;

import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;

public class SQLFactoryImpl implements SQLFactory, Configurable {
   private JDBCConfiguration _conf = null;

   public JDBCConfiguration getConfiguration() {
      return this._conf;
   }

   public Select newSelect() {
      return new SelectImpl(this._conf);
   }

   public Union newUnion(int selects) {
      return new LogicalUnion(this._conf, selects);
   }

   public Union newUnion(Select[] selects) {
      return new LogicalUnion(this._conf, selects);
   }

   public void setConfiguration(Configuration conf) {
      this._conf = (JDBCConfiguration)conf;
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
   }
}
