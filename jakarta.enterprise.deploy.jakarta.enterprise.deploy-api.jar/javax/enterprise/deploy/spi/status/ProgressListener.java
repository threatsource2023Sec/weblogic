package javax.enterprise.deploy.spi.status;

import java.util.EventListener;

public interface ProgressListener extends EventListener {
   void handleProgressEvent(ProgressEvent var1);
}
