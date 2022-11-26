package com.sun.faces.application;

import com.sun.faces.util.FacesLogger;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.application.Application;
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
      ActionSource actionSource = (ActionSource)source;
      FacesContext context = FacesContext.getCurrentInstance();
      Application application = context.getApplication();
      String outcome = null;
      MethodBinding binding = actionSource.getAction();
      if (binding != null) {
         try {
            Object invokeResult;
            if (null != (invokeResult = binding.invoke(context, (Object[])null))) {
               outcome = invokeResult.toString();
            }
         } catch (MethodNotFoundException var10) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, var10.getMessage(), var10);
            }

            throw new FacesException(binding.getExpressionString() + ": " + var10.getMessage(), var10);
         } catch (EvaluationException var11) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, var11.getMessage(), var11);
            }

            throw new FacesException(binding.getExpressionString() + ": " + var11.getMessage(), var11);
         }
      }

      NavigationHandler navHandler = application.getNavigationHandler();
      navHandler.handleNavigation(context, null != binding ? binding.getExpressionString() : null, outcome);
      context.renderResponse();
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
   }
}
