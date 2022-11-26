package weblogic.jdbc.extensions;

import java.io.Serializable;
import javax.sql.DataSource;

public interface DataSourceSwitchingCallback extends Serializable {
   DataSource getDataSource(String var1, String var2);
}
