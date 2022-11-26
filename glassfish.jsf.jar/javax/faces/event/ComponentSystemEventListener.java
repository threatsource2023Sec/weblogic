package javax.faces.event;

public interface ComponentSystemEventListener extends FacesListener {
   void processEvent(ComponentSystemEvent var1) throws AbortProcessingException;
}
