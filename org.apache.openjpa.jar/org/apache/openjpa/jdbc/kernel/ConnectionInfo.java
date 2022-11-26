package org.apache.openjpa.jdbc.kernel;

import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public class ConnectionInfo {
   public Result result = null;
   public OpenJPAStateManager sm = null;
   public ClassMapping mapping = null;
}
