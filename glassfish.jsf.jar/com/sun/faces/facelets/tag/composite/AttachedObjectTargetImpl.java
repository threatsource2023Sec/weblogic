package com.sun.faces.facelets.tag.composite;

import com.sun.faces.util.Util;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.view.AttachedObjectTarget;

public class AttachedObjectTargetImpl implements AttachedObjectTarget {
   private String name = null;
   private ValueExpression targetsList;

   public String getName() {
      return this.name;
   }

   void setName(String name) {
      this.name = name;
   }

   public List getTargets(UIComponent topLevelComponent) {
      assert null != this.name;

      FacesContext ctx = FacesContext.getCurrentInstance();
      ArrayList result;
      if (null != this.targetsList) {
         String targetsListStr = (String)this.targetsList.getValue(ctx.getELContext());
         Map appMap = FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();
         String[] targetArray = Util.split(appMap, targetsListStr, " ");
         result = new ArrayList(targetArray.length);
         int i = 0;

         for(int len = targetArray.length; i < len; ++i) {
            UIComponent comp = topLevelComponent.findComponent(this.augmentSearchId(ctx, topLevelComponent, targetArray[i]));
            if (null != comp) {
               result.add(comp);
            }
         }
      } else {
         result = new ArrayList(1);
         UIComponent comp = topLevelComponent.findComponent(this.augmentSearchId(ctx, topLevelComponent, this.name));
         if (null != comp) {
            result.add(comp);
         }
      }

      return result;
   }

   void setTargetsList(ValueExpression targetsList) {
      this.targetsList = targetsList;
   }

   private String augmentSearchId(FacesContext ctx, UIComponent c, String targetId) {
      return targetId.equals(c.getId()) ? targetId + UINamingContainer.getSeparatorChar(ctx) + targetId : targetId;
   }
}
