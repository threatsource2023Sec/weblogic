package javax.resource.spi.work;

import java.io.Serializable;

public interface WorkContext extends Serializable {
   String getName();

   String getDescription();
}
