package weblogic.management.runtime;

public interface MessageDrivenControlEJBRuntimeMBean extends RuntimeMBean {
   boolean suspendMDBs(String var1, String var2);

   boolean resumeMDBs(String var1, String var2);

   boolean printMDBStatus(String var1, String var2);
}
