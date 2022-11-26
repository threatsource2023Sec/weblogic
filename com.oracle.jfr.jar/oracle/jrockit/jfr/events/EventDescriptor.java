package oracle.jrockit.jfr.events;

import java.net.URI;

public interface EventDescriptor {
   String getName();

   String getDescription();

   String getPath();

   URI getURI();

   boolean isTimed();

   boolean isRequestable();

   boolean hasThread();

   boolean hasStackTrace();

   boolean hasStartTime();

   int getId();
}
