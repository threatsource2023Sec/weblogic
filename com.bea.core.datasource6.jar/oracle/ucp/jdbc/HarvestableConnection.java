package oracle.ucp.jdbc;

import java.sql.SQLException;
import oracle.ucp.ConnectionHarvestingCallback;

public interface HarvestableConnection {
   void setConnectionHarvestable(boolean var1) throws SQLException;

   boolean isConnectionHarvestable() throws SQLException;

   void registerConnectionHarvestingCallback(ConnectionHarvestingCallback var1) throws SQLException;

   void removeConnectionHarvestingCallback() throws SQLException;
}
