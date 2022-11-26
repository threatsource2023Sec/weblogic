package weblogic.jdbc.common.internal;

import java.rmi.Remote;
import javax.sql.DataSource;
import weblogic.jdbc.extensions.WLDataSource;

public interface RemoteDataSource extends DataSource, WLDataSource, Remote {
}
