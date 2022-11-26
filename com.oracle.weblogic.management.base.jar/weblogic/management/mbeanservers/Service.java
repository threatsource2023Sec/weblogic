package weblogic.management.mbeanservers;

public interface Service extends weblogic.management.provider.Service {
   String getName();

   String getType();

   String getPath();

   String getParentAttribute();

   weblogic.management.provider.Service getParentService();
}
