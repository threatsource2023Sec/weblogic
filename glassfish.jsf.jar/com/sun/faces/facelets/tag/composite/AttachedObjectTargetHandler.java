package com.sun.faces.facelets.tag.composite;

import com.sun.faces.application.view.FaceletViewHandlingStrategy;
import com.sun.faces.facelets.tag.TagHandlerImpl;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.io.IOException;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagException;

public abstract class AttachedObjectTargetHandler extends TagHandlerImpl {
   private TagAttribute name = null;
   private TagAttribute targets = null;

   public AttachedObjectTargetHandler(TagConfig config) {
      super(config);
      this.name = this.getRequiredAttribute("name");
      this.targets = this.getAttribute("targets");
   }

   abstract AttachedObjectTargetImpl newAttachedObjectTargetImpl();

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      assert ctx.getFacesContext().getAttributes().containsKey(FaceletViewHandlingStrategy.IS_BUILDING_METADATA);

      if (null != parent && null != (parent = parent.getParent()) && ComponentHandler.isNew(parent)) {
         BeanInfo componentBeanInfo = (BeanInfo)parent.getAttributes().get("javax.faces.component.BEANINFO_KEY");
         if (null == componentBeanInfo) {
            throw new TagException(this.tag, "Error: I have an EditableValueHolder tag, but no enclosing composite component");
         } else {
            BeanDescriptor componentDescriptor = componentBeanInfo.getBeanDescriptor();
            if (null == componentDescriptor) {
               throw new TagException(this.tag, "Error: I have an EditableValueHolder tag, but no enclosing composite component");
            } else {
               List targetList = (List)componentDescriptor.getValue("javax.faces.view.AttachedObjectTargets");
               AttachedObjectTargetImpl target = this.newAttachedObjectTargetImpl();
               targetList.add(target);
               ValueExpression ve = this.name.getValueExpression(ctx, String.class);
               String strValue = (String)ve.getValue(ctx);
               if (null != strValue) {
                  target.setName(strValue);
               }

               if (null != this.targets) {
                  ve = this.targets.getValueExpression(ctx, String.class);
                  target.setTargetsList(ve);
               }

               this.nextHandler.apply(ctx, parent);
            }
         }
      }
   }
}
