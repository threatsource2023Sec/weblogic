package javax.faces.event;

public interface SystemEventListener extends FacesListener {
   void processEvent(SystemEvent var1) throws AbortProcessingException;

   boolean isListenerForSource(Object var1);
}
