package weblogic.rmi.cluster;

import java.io.Serializable;
import java.lang.reflect.Method;

public interface CallRouter extends Serializable {
   String[] getServerList(Method var1, Object[] var2);
}
