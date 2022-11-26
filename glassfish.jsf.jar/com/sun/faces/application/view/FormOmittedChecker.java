package com.sun.faces.application.view;

import com.sun.faces.util.MessageUtils;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.component.ActionSource;
import javax.faces.component.ActionSource2;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitHint;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;

class FormOmittedChecker {
   private static String SKIP_ITERATION_HINT = "javax.faces.visit.SKIP_ITERATION";

   private FormOmittedChecker() {
   }

   public static void check(FacesContext context) {
      final FacesContext finalContext = context;
      UIViewRoot viewRoot = context.getViewRoot();
      List children = viewRoot.getChildren();
      Iterator var4 = children.iterator();

      while(var4.hasNext()) {
         UIComponent child = (UIComponent)var4.next();

         try {
            context.getAttributes().put(SKIP_ITERATION_HINT, true);
            Set hints = EnumSet.of(VisitHint.SKIP_ITERATION);
            VisitContext visitContext = VisitContext.createVisitContext(context, (Collection)null, hints);
            child.visitTree(visitContext, new VisitCallback() {
               public VisitResult visit(VisitContext visitContext, UIComponent component) {
                  VisitResult result = VisitResult.ACCEPT;
                  if (FormOmittedChecker.isForm(component)) {
                     result = VisitResult.REJECT;
                  } else if (FormOmittedChecker.isInNeedOfForm(component)) {
                     FormOmittedChecker.addFormOmittedMessage(finalContext, component);
                  }

                  return result;
               }
            });
         } finally {
            context.getAttributes().remove(SKIP_ITERATION_HINT);
         }
      }

   }

   private static boolean isForm(UIComponent component) {
      return component instanceof UIForm || component.getFamily() != null && component.getFamily().endsWith("Form");
   }

   private static boolean isInNeedOfForm(UIComponent component) {
      return component instanceof ActionSource || component instanceof ActionSource2 || component instanceof EditableValueHolder;
   }

   private static void addFormOmittedMessage(FacesContext context, UIComponent component) {
      String key = "com.sun.faces.MISSING_FORM_ERROR";
      Object[] parameters = new Object[]{component.getClientId(context)};
      boolean missingFormReported = false;
      FacesMessage message = MessageUtils.getExceptionMessage(key, parameters);
      List messages = context.getMessageList();
      Iterator var7 = messages.iterator();

      while(var7.hasNext()) {
         FacesMessage item = (FacesMessage)var7.next();
         if (item.getDetail().equals(message.getDetail())) {
            missingFormReported = true;
            break;
         }
      }

      if (!missingFormReported) {
         message.setSeverity(FacesMessage.SEVERITY_WARN);
         context.addMessage((String)null, message);
      }

   }
}
