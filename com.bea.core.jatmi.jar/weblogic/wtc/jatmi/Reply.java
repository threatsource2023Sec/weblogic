package weblogic.wtc.jatmi;

import java.io.Serializable;

public interface Reply extends Serializable {
   TypedBuffer getReplyBuffer();

   int gettpurcode();

   CallDescriptor getCallDescriptor();
}
