package javax.faces.event;

public interface ActionListener extends FacesListener {
   String TO_FLOW_DOCUMENT_ID_ATTR_NAME = "to-flow-document-id";

   void processAction(ActionEvent var1) throws AbortProcessingException;
}
