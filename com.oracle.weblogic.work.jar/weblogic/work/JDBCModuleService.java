package weblogic.work;

import org.jvnet.hk2.annotations.Contract;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;

@Contract
public interface JDBCModuleService {
   JDBCDataSourceBean[] getJDBCDataSourceBean(String var1);
}
