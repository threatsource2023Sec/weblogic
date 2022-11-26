package javax.resource.spi.work;

import java.io.Serializable;
import java.util.List;

public interface WorkContextProvider extends Serializable {
   List getWorkContexts();
}
