package com.sun.faces.application;

import com.sun.faces.util.FacesLogger;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

public class ActionListenerImpl implements ActionListener {
   private static final Logger LOGGER;

   public void processAction(ActionEvent event) {
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.fine(MessageFormat.format("processAction({0})", event.getComponent().getId()));
      }

      UIComponent source = event.getComponent();
      FacesContext context = event.getFacesContext();
      MethodBinding binding = ((ActionSource)source).getAction();
      this.invokeNavigationHandling(context, source, binding, this.getNavigationOutcome(context, binding));
      context.renderResponse();
   }

   private String getNavigationOutcome(FacesContext context, MethodBinding binding) {
      if (binding != null) {
         try {
            Object invokeResult;
            if ((invokeResult = binding.invoke(context, (Object[])null)) != null) {
               return invokeResult.toString();
            }
         } catch (MethodNotFoundException var4) {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, var4.getMessage(), var4);
            }

            throw new FacesException(binding.getExpressionString() + ": " + var4.getMessage(), var4);
         } catch (EvaluationException var5) {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, var5.getMessage(), var5);
            }

            throw new FacesException(binding.getExpressionString() + ": " + var5.getMessage(), var5);
         }
      }

      return null;
   }

   private void invokeNavigationHandling(FacesContext context, UIComponent source, MethodBinding binding, String outcome) {
      NavigationHandler navHandler = context.getApplication().getNavigationHandler();
      String toFlowDocumentId = (String)source.getAttributes().get("to-flow-document-id");
      if (toFlowDocumentId == null) {
         navHandler.handleNavigation(context, binding != null ? binding.getExpressionString() : null, outcome);
      } else {
         navHandler.handleNavigation(context, binding != null ? binding.getExpressionString() : null, outcome, toFlowDocumentId);
      }

   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
   }
}
