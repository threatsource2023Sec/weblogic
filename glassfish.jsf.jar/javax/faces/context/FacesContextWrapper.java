package javax.faces.context;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.el.ELContext;
import javax.faces.FacesWrapper;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ProjectStage;
import javax.faces.component.UIViewRoot;
import javax.faces.event.PhaseId;
import javax.faces.render.RenderKit;

public abstract class FacesContextWrapper extends FacesContext implements FacesWrapper {
   private FacesContext wrapped;

   /** @deprecated */
   @Deprecated
   public FacesContextWrapper() {
   }

   public FacesContextWrapper(FacesContext wrapped) {
      this.wrapped = wrapped;
   }

   public FacesContext getWrapped() {
      return this.wrapped;
   }

   public Application getApplication() {
      return this.getWrapped().getApplication();
   }

   public Iterator getClientIdsWithMessages() {
      return this.getWrapped().getClientIdsWithMessages();
   }

   public ExternalContext getExternalContext() {
      return this.getWrapped().getExternalContext();
   }

   public FacesMessage.Severity getMaximumSeverity() {
      return this.getWrapped().getMaximumSeverity();
   }

   public Iterator getMessages() {
      return this.getWrapped().getMessages();
   }

   public Iterator getMessages(String clientId) {
      return this.getWrapped().getMessages(clientId);
   }

   public RenderKit getRenderKit() {
      return this.getWrapped().getRenderKit();
   }

   public boolean getRenderResponse() {
      return this.getWrapped().getRenderResponse();
   }

   public List getResourceLibraryContracts() {
      return this.getWrapped().getResourceLibraryContracts();
   }

   public void setResourceLibraryContracts(List contracts) {
      this.getWrapped().setResourceLibraryContracts(contracts);
   }

   public boolean getResponseComplete() {
      return this.getWrapped().getResponseComplete();
   }

   public ResponseStream getResponseStream() {
      return this.getWrapped().getResponseStream();
   }

   public void setResponseStream(ResponseStream responseStream) {
      this.getWrapped().setResponseStream(responseStream);
   }

   public ResponseWriter getResponseWriter() {
      return this.getWrapped().getResponseWriter();
   }

   public void setResponseWriter(ResponseWriter responseWriter) {
      this.getWrapped().setResponseWriter(responseWriter);
   }

   public UIViewRoot getViewRoot() {
      return this.getWrapped().getViewRoot();
   }

   public void setViewRoot(UIViewRoot root) {
      this.getWrapped().setViewRoot(root);
   }

   public void addMessage(String clientId, FacesMessage message) {
      this.getWrapped().addMessage(clientId, message);
   }

   public boolean isReleased() {
      return this.getWrapped().isReleased();
   }

   public void release() {
      this.getWrapped().release();
   }

   public void renderResponse() {
      this.getWrapped().renderResponse();
   }

   public void responseComplete() {
      this.getWrapped().responseComplete();
   }

   public Map getAttributes() {
      return this.getWrapped().getAttributes();
   }

   public char getNamingContainerSeparatorChar() {
      return this.getWrapped().getNamingContainerSeparatorChar();
   }

   public PartialViewContext getPartialViewContext() {
      return this.getWrapped().getPartialViewContext();
   }

   public ELContext getELContext() {
      return this.getWrapped().getELContext();
   }

   public ExceptionHandler getExceptionHandler() {
      return this.getWrapped().getExceptionHandler();
   }

   public void setExceptionHandler(ExceptionHandler exceptionHandler) {
      this.getWrapped().setExceptionHandler(exceptionHandler);
   }

   public List getMessageList() {
      return this.getWrapped().getMessageList();
   }

   public List getMessageList(String clientId) {
      return this.getWrapped().getMessageList(clientId);
   }

   public boolean isPostback() {
      return this.getWrapped().isPostback();
   }

   public PhaseId getCurrentPhaseId() {
      return this.getWrapped().getCurrentPhaseId();
   }

   public void setCurrentPhaseId(PhaseId currentPhaseId) {
      this.getWrapped().setCurrentPhaseId(currentPhaseId);
   }

   public boolean isValidationFailed() {
      return this.getWrapped().isValidationFailed();
   }

   public void validationFailed() {
      this.getWrapped().validationFailed();
   }

   public void setProcessingEvents(boolean processingEvents) {
      this.getWrapped().setProcessingEvents(processingEvents);
   }

   public boolean isProcessingEvents() {
      return this.getWrapped().isProcessingEvents();
   }

   public boolean isProjectStage(ProjectStage stage) {
      return this.getWrapped().isProjectStage(stage);
   }
}
