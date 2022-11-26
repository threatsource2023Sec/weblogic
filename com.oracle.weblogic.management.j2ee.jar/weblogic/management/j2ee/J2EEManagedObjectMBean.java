package weblogic.management.j2ee;

public interface J2EEManagedObjectMBean {
   String getobjectName();

   boolean isstateManageable();

   boolean isstatisticsProvider();

   boolean iseventProvider();
}
