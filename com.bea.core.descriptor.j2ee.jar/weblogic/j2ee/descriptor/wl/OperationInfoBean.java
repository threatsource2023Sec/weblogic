package weblogic.j2ee.descriptor.wl;

public interface OperationInfoBean {
   String getName();

   void setName(String var1);

   WSATConfigBean getWSATConfig();

   WSATConfigBean createWSATConfig();

   void destroyWSATConfig();
}
