package javax.faces.event;

public interface ActionListener extends FacesListener {
   void processAction(ActionEvent var1) throws AbortProcessingException;
}
