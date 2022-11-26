package weblogic.messaging.path;

import java.io.Serializable;

public interface Member extends Serializable, Version {
   Serializable getMemberId();

   String getWLServerName();
}
