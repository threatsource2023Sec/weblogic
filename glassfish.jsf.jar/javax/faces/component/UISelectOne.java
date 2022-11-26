package javax.faces.component;

import javax.faces.application.FacesMessage;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;

public class UISelectOne extends UIInput {
   public static final String COMPONENT_TYPE = "javax.faces.SelectOne";
   public static final String COMPONENT_FAMILY = "javax.faces.SelectOne";
   public static final String INVALID_MESSAGE_ID = "javax.faces.component.UISelectOne.INVALID";

   public UISelectOne() {
      this.setRendererType("javax.faces.Menu");
   }

   public String getFamily() {
      return "javax.faces.SelectOne";
   }

   public String getGroup() {
      return (String)this.getStateHelper().eval(UISelectOne.PropertyKeys.group);
   }

   public void setGroup(String group) {
      this.getStateHelper().put(UISelectOne.PropertyKeys.group, group);
   }

   public void processValidators(final FacesContext context) {
      final String group = this.getGroup();
      if (group != null && isEmpty(this.getSubmittedValue())) {
         final String clientId = this.getClientId(context);
         UIComponent groupContainer = getGroupContainer(context, this);
         final boolean[] alreadySubmittedOrValidatedAsGroup = new boolean[1];
         groupContainer.visitTree(VisitContext.createVisitContext(context), new VisitCallback() {
            public VisitResult visit(VisitContext visitContext, UIComponent target) {
               if (target instanceof UISelectOne) {
                  UISelectOne radio = (UISelectOne)target;
                  if (UISelectOne.isOtherMemberOfSameGroup(context, group, clientId, radio) && UISelectOne.isAlreadySubmittedOrValidated(radio)) {
                     alreadySubmittedOrValidatedAsGroup[0] = true;
                     return VisitResult.COMPLETE;
                  }
               }

               return VisitResult.ACCEPT;
            }
         });
         if (alreadySubmittedOrValidatedAsGroup[0]) {
            return;
         }
      }

      super.processValidators(context);
   }

   private static UIComponent getGroupContainer(FacesContext context, UISelectOne radio) {
      UIComponent namingContainer;
      for(namingContainer = radio.getNamingContainer(); namingContainer != null && !(namingContainer instanceof UIForm) && namingContainer.getParent() != null; namingContainer = namingContainer.getParent().getNamingContainer()) {
      }

      return (UIComponent)(namingContainer != null ? namingContainer : context.getViewRoot());
   }

   private static boolean isOtherMemberOfSameGroup(FacesContext context, String group, String clientId, UISelectOne radio) {
      return group.equals(radio.getGroup()) && !clientId.equals(radio.getClientId(context));
   }

   private static boolean isAlreadySubmittedOrValidated(EditableValueHolder input) {
      return !isEmpty(input.getSubmittedValue()) || input.isLocalValueSet() || !input.isValid();
   }

   protected void validateValue(FacesContext context, Object value) {
      super.validateValue(context, value);
      if (this.isValid() && value != null) {
         boolean found = SelectUtils.matchValue(this.getFacesContext(), this, value, new SelectItemsIterator(context, this), this.getConverter());
         boolean isNoSelection = SelectUtils.valueIsNoSelectionOption(this.getFacesContext(), this, value, new SelectItemsIterator(context, this), this.getConverter());
         if (!found || this.isRequired() && isNoSelection) {
            FacesMessage message = MessageFactory.getMessage(context, "javax.faces.component.UISelectOne.INVALID", MessageFactory.getLabel(context, this));
            context.addMessage(this.getClientId(context), message);
            this.setValid(false);
         }

      }
   }

   static enum PropertyKeys {
      group;
   }
}
