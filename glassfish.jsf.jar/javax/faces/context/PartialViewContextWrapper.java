package javax.faces.context;

import java.util.Collection;
import java.util.List;
import javax.faces.FacesWrapper;
import javax.faces.event.PhaseId;

public abstract class PartialViewContextWrapper extends PartialViewContext implements FacesWrapper {
   private PartialViewContext wrapped;

   /** @deprecated */
   @Deprecated
   public PartialViewContextWrapper() {
   }

   public PartialViewContextWrapper(PartialViewContext wrapped) {
      this.wrapped = wrapped;
   }

   public PartialViewContext getWrapped() {
      return this.wrapped;
   }

   public Collection getExecuteIds() {
      return this.getWrapped().getExecuteIds();
   }

   public Collection getRenderIds() {
      return this.getWrapped().getRenderIds();
   }

   public List getEvalScripts() {
      return this.getWrapped().getEvalScripts();
   }

   public PartialResponseWriter getPartialResponseWriter() {
      return this.getWrapped().getPartialResponseWriter();
   }

   public void setPartialRequest(boolean isPartialRequest) {
      this.getWrapped().setPartialRequest(isPartialRequest);
   }

   public boolean isAjaxRequest() {
      return this.getWrapped().isAjaxRequest();
   }

   public boolean isPartialRequest() {
      return this.getWrapped().isPartialRequest();
   }

   public boolean isExecuteAll() {
      return this.getWrapped().isExecuteAll();
   }

   public boolean isRenderAll() {
      return this.getWrapped().isRenderAll();
   }

   public boolean isResetValues() {
      return this.getWrapped().isResetValues();
   }

   public void setRenderAll(boolean renderAll) {
      this.getWrapped().setRenderAll(renderAll);
   }

   public void release() {
      this.getWrapped().release();
   }

   public void processPartial(PhaseId phaseId) {
      this.getWrapped().processPartial(phaseId);
   }
}
