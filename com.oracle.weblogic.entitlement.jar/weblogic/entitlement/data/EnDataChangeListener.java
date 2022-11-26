package weblogic.entitlement.data;

public interface EnDataChangeListener {
   void resourceChanged(String var1);

   void roleChanged(ERoleId var1);

   void predicateChanged(String var1);
}
