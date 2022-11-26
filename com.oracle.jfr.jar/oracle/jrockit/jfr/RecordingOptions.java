package oracle.jrockit.jfr;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public interface RecordingOptions {
   long getDuration(TimeUnit var1);

   long getMaxAge(TimeUnit var1);

   long getMaxSize();

   String getDestination();

   Date getStartTime();

   boolean isDestinationCompressed();

   boolean isToDisk();
}
