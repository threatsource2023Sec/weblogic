package weblogic.management.provider;

public interface Service {
   String getName();

   String getType();

   String getPath();

   Service getParentService();

   String getParentAttribute();
}
