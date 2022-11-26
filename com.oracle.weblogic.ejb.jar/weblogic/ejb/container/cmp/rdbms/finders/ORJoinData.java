package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class ORJoinData {
   private Expr orTerm;
   private final Vector orVector = new Vector();
   private final Map orCmpFieldJoinMap = new HashMap();
   private final Map orSQLTableGenSymbolMap = new HashMap();
   private final Set orSQLTableGenTableSet = new HashSet();

   public ORJoinData() {
   }

   public ORJoinData(Expr term) {
      this.orTerm = term;
   }

   public Expr getOrTerm() {
      return this.orTerm;
   }

   public Vector getOrVector() {
      return this.orVector;
   }

   public Map getOrCmpFieldJoinMap() {
      return this.orCmpFieldJoinMap;
   }

   public void addOrSQLTableGenInfo(String pathExpression, String tableName) {
      this.addOrSQLTableGenSymbolMap(pathExpression, tableName);
      this.addOrSQLTableGenTableSet(tableName);
   }

   public Map getOrSQLTableGenSymbolMap() {
      return this.orSQLTableGenSymbolMap;
   }

   public void addOrSQLTableGenSymbolMap(String pathExpression, String tableName) {
      this.orSQLTableGenSymbolMap.put(pathExpression, tableName);
   }

   public Set getOrSQLTableGenTableSet() {
      return this.orSQLTableGenTableSet;
   }

   public void addOrSQLTableGenTableSet(String tableName) {
      this.orSQLTableGenTableSet.add(tableName);
   }
}
