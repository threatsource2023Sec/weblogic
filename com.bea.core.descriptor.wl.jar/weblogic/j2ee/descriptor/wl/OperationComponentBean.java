package weblogic.j2ee.descriptor.wl;

public interface OperationComponentBean {
   void setName(String var1);

   String getName();

   WSATConfigBean getWSATConfig();

   WSATConfigBean createWSATConfig();

   void destroyWSATConfig();
}
