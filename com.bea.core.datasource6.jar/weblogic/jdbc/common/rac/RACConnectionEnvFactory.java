package weblogic.jdbc.common.rac;

import weblogic.common.ResourceException;
import weblogic.jdbc.common.internal.JDBCResourceFactory;

public interface RACConnectionEnvFactory extends JDBCResourceFactory {
   RACPooledConnectionState createRACPooledConnectionState(RACConnectionEnv var1) throws ResourceException;
}
