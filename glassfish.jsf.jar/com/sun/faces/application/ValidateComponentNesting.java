package com.sun.faces.application;

import com.sun.faces.util.MessageUtils;
import java.util.Collection;
import java.util.EnumSet;
import javax.faces.application.FacesMessage;
import javax.faces.component.ActionSource;
import javax.faces.component.ActionSource2;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIViewAction;
import javax.faces.component.UIViewParameter;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitHint;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

public class ValidateComponentNesting implements SystemEventListener {
   public boolean isListenerForSource(Object source) {
      return source instanceof UIViewRoot;
   }

   public void processEvent(SystemEvent event) throws AbortProcessingException {
      UIComponent root = (UIComponent)event.getSource();
      FacesContext jsf = FacesContext.getCurrentInstance();
      EnumSet hints = EnumSet.of(VisitHint.SKIP_ITERATION);
      VisitContext visitContext = VisitContext.createVisitContext(jsf, (Collection)null, hints);
      root.visitTree(visitContext, new ValidateFormNestingCallback());
   }

   private static void addOmittedMessage(FacesContext jsf, String clientId, String key) {
      Object[] params = new Object[0];
      FacesMessage m = MessageUtils.getExceptionMessage(key, params);
      m.setSeverity(FacesMessage.SEVERITY_WARN);
      jsf.addMessage(clientId, m);
   }

   static class ValidateFormNestingCallback implements VisitCallback {
      boolean reportedOmittedFormOnce = false;
      boolean reportedOmittedMetadataOnce = false;

      public VisitResult visit(VisitContext context, UIComponent target) {
         VisitResult result = VisitResult.ACCEPT;
         if (!(target instanceof UIForm) && !target.getFamily().endsWith("Form") && !"javax_faces_metadata".equals(target.getId())) {
            if (!(target instanceof UIViewParameter) && !(target instanceof UIViewAction)) {
               if (target instanceof EditableValueHolder || target instanceof ActionSource || target instanceof ActionSource2) {
                  if (this.reportedOmittedFormOnce) {
                     result = VisitResult.COMPLETE;
                  }

                  ValidateComponentNesting.addOmittedMessage(context.getFacesContext(), target.getClientId(context.getFacesContext()), "com.sun.faces.MISSING_FORM_ERROR");
                  this.reportedOmittedFormOnce = true;
               }
            } else {
               if (this.reportedOmittedMetadataOnce) {
                  result = VisitResult.COMPLETE;
               }

               ValidateComponentNesting.addOmittedMessage(context.getFacesContext(), target.getClientId(context.getFacesContext()), "com.sun.faces.MISSING_METADATA_ERROR");
               this.reportedOmittedMetadataOnce = true;
            }
         } else {
            result = VisitResult.REJECT;
         }

         return result;
      }
   }
}
