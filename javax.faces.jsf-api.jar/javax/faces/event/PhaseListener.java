package javax.faces.event;

import java.io.Serializable;
import java.util.EventListener;

public interface PhaseListener extends EventListener, Serializable {
   void afterPhase(PhaseEvent var1);

   void beforePhase(PhaseEvent var1);

   PhaseId getPhaseId();
}
