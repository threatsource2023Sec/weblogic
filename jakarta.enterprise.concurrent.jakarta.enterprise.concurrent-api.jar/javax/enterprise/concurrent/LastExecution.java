package javax.enterprise.concurrent;

import java.util.Date;

public interface LastExecution {
   String getIdentityName();

   Object getResult();

   Date getScheduledStart();

   Date getRunStart();

   Date getRunEnd();
}
