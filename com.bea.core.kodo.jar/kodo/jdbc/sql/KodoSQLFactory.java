package kodo.jdbc.sql;

import java.util.HashMap;
import java.util.Map;
import org.apache.openjpa.jdbc.sql.AccessDictionary;
import org.apache.openjpa.jdbc.sql.CacheDictionary;
import org.apache.openjpa.jdbc.sql.DB2Dictionary;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.EmpressDictionary;
import org.apache.openjpa.jdbc.sql.FoxProDictionary;
import org.apache.openjpa.jdbc.sql.HSQLDictionary;
import org.apache.openjpa.jdbc.sql.InformixDictionary;
import org.apache.openjpa.jdbc.sql.InterbaseDictionary;
import org.apache.openjpa.jdbc.sql.JDataStoreDictionary;
import org.apache.openjpa.jdbc.sql.OracleDictionary;
import org.apache.openjpa.jdbc.sql.SQLFactoryImpl;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.jdbc.sql.Union;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.conf.GenericConfigurable;
import org.apache.openjpa.lib.util.Options;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.UserException;

public class KodoSQLFactory extends SQLFactoryImpl implements GenericConfigurable {
   private static final Map _advancedSQLTypes = new HashMap();
   private AdvancedSQL _advanced = null;
   private Class _advancedCls = null;

   public Select newSelect() {
      return new KodoSelectImpl(this.getConfiguration());
   }

   public AdvancedSQL getAdvancedSQL() {
      return this._advanced;
   }

   public void setAdvancedSQL(AdvancedSQL advanced) {
      this._advanced = advanced;
   }

   public void setAdvancedSQL(String type) {
      try {
         this._advancedCls = Class.forName(type, true, AdvancedSQL.class.getClassLoader());
      } catch (Throwable var3) {
         throw new UserException(var3);
      }
   }

   public Union newUnion(int selects) {
      return new TrueUnion(this.getConfiguration(), selects);
   }

   public Union newUnion(Select[] selects) {
      return new TrueUnion(this.getConfiguration(), selects);
   }

   public void setInto(Options opts) {
      if (this._advanced == null) {
         DBDictionary dict = this.getConfiguration().getDBDictionaryInstance();
         this._advanced = this.newAdvancedSQL(dict);
         Configurations.configureInstance(this._advanced, this.getConfiguration(), opts);
         opts.clear();
      }
   }

   protected AdvancedSQL newAdvancedSQL(DBDictionary dict) {
      if (this._advancedCls != null) {
         try {
            return (AdvancedSQL)this._advancedCls.newInstance();
         } catch (Throwable var4) {
            throw new UserException(var4);
         }
      } else {
         try {
            for(Class c = dict.getClass(); c != Object.class; c = c.getSuperclass()) {
               Class acls = (Class)_advancedSQLTypes.get(c);
               if (acls != null) {
                  return (AdvancedSQL)acls.newInstance();
               }
            }
         } catch (Throwable var5) {
            throw new InternalException(var5);
         }

         throw new InternalException();
      }
   }

   static {
      _advancedSQLTypes.put(DBDictionary.class, AdvancedSQL.class);
      _advancedSQLTypes.put(AccessDictionary.class, AccessAdvancedSQL.class);
      _advancedSQLTypes.put(CacheDictionary.class, CacheAdvancedSQL.class);
      _advancedSQLTypes.put(DB2Dictionary.class, DB2AdvancedSQL.class);
      _advancedSQLTypes.put(EmpressDictionary.class, EmpressAdvancedSQL.class);
      _advancedSQLTypes.put(FoxProDictionary.class, FoxProAdvancedSQL.class);
      _advancedSQLTypes.put(HSQLDictionary.class, HSQLAdvancedSQL.class);
      _advancedSQLTypes.put(InformixDictionary.class, InformixAdvancedSQL.class);
      _advancedSQLTypes.put(InterbaseDictionary.class, InterbaseAdvancedSQL.class);
      _advancedSQLTypes.put(JDataStoreDictionary.class, JDataStoreAdvancedSQL.class);
      _advancedSQLTypes.put(OracleDictionary.class, OracleAdvancedSQL.class);
   }
}
