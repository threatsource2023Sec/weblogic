package javax.resource.cci;

import java.io.Serializable;

public interface InteractionSpec extends Serializable {
   int SYNC_SEND = 0;
   int SYNC_SEND_RECEIVE = 1;
   int SYNC_RECEIVE = 2;
}
