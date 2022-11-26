package weblogic.jdbc.common.rac;

import java.sql.SQLException;

public interface RACPooledConnectionState {
   RACInstance getRACInstance();

   RACConnectionEnv getConnection();

   boolean closeOnRelease();

   boolean isStatusValid();

   boolean isConnectionUsable();

   boolean pingDatabase() throws SQLException;

   void markConnectionGood();

   void markConnectionCloseOnRelease();

   boolean isConnectionCloseOnRelease();
}
