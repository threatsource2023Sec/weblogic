package javax.faces.event;

import javax.faces.FacesWrapper;

public abstract class ActionListenerWrapper implements ActionListener, FacesWrapper {
   public void processAction(ActionEvent event) throws AbortProcessingException {
      this.getWrapped().processAction(event);
   }

   public abstract ActionListener getWrapped();
}
