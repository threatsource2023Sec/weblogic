package weblogic.common;

import weblogic.common.internal.Manufacturable;
import weblogic.t3.srvr.ExecutionContext;

/** @deprecated */
@Deprecated
public interface T3Executable extends Manufacturable {
   Object execute(ExecutionContext var1, Object var2) throws Exception;
}
