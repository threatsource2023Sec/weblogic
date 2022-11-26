package weblogic.ejbgen;

public @interface DBSpecificSQL {
   String databaseType();

   String sql();
}
