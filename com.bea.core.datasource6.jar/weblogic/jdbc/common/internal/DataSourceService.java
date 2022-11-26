package weblogic.jdbc.common.internal;

import java.io.OutputStream;
import javax.sql.DataSource;
import oracle.ucp.ConnectionAffinityCallback;
import oracle.ucp.jdbc.oracle.DataBasedConnectionAffinityCallback;
import weblogic.common.ResourceException;
import weblogic.j2ee.descriptor.DataSourceBean;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.jdbc.extensions.AffinityCallback;

public interface DataSourceService {
   void registerAffinityCallback(AffinityCallback var1);

   boolean unregisterAffinityCallback(AffinityCallback var1);

   boolean isAffinityPolicyDeployed(ConnectionAffinityCallback.AffinityPolicy var1);

   void registerAffinityPolicyListener(AffinityPolicyListener var1, ConnectionAffinityCallback.AffinityPolicy var2);

   boolean unregisterAffinityPolicyListener(AffinityPolicyListener var1);

   void registerDataAffinityCallback(DataBasedConnectionAffinityCallback var1);

   JDBCDataSourceBean createJDBCDataSourceBean(DataSourceBean var1) throws ResourceException;

   DataSource createDataSource(JDBCDataSourceBean var1, String var2, String var3, String var4) throws ResourceException;

   void destroyDataSource(String var1, String var2, String var3, String var4) throws IllegalStateException, ResourceException;

   OutputStream getLogFileOutputStream() throws Exception;
}
