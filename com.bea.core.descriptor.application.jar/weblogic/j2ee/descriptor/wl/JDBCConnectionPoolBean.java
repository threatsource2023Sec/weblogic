package weblogic.j2ee.descriptor.wl;

/** @deprecated */
@Deprecated
public interface JDBCConnectionPoolBean {
   String getDataSourceJNDIName();

   void setDataSourceJNDIName(String var1);

   ConnectionFactoryBean getConnectionFactory();

   ConnectionFactoryBean createConnectionFactory();

   void destroyConnectionFactory(ConnectionFactoryBean var1);

   ApplicationPoolParamsBean getPoolParams();

   ApplicationPoolParamsBean createPoolParams();

   void destroyPoolParams(ApplicationPoolParamsBean var1);

   DriverParamsBean getDriverParams();

   DriverParamsBean createDriverParams();

   void destroyDriverParams(DriverParamsBean var1);

   String getAclName();

   void setAclName(String var1);
}
