package com.sun.faces.facelets.tag.jsf;

import com.sun.faces.util.Util;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagException;

public class PassThroughElementComponentHandler extends ComponentHandler {
   private final TagAttribute elementName = this.getRequiredPassthroughAttribute("elementName");

   protected final TagAttribute getRequiredPassthroughAttribute(String localName) throws TagException {
      TagAttribute attr = this.tag.getAttributes().get("http://xmlns.jcp.org/jsf/passthrough", localName);
      if (attr == null) {
         throw new TagException(this.tag, "Attribute '" + localName + "' is required");
      } else {
         return attr;
      }
   }

   public PassThroughElementComponentHandler(ComponentConfig config) {
      super(config);
   }

   public UIComponent createComponent(FaceletContext ctx) {
      UIComponent result = null;

      try {
         Class clazz = Util.loadClass("com.sun.faces.component.PassthroughElement", this);
         result = (UIComponent)clazz.newInstance();
         return result;
      } catch (IllegalAccessException | InstantiationException | ClassNotFoundException var4) {
         throw new FacesException(var4);
      }
   }

   public void onComponentCreated(FaceletContext ctx, UIComponent c, UIComponent parent) {
      if (parent.getParent() == null) {
         Map passThroughAttrs = c.getPassThroughAttributes(true);
         Object attrValue = this.elementName.isLiteral() ? this.elementName.getValue(ctx) : this.elementName.getValueExpression(ctx, Object.class);
         passThroughAttrs.put("elementName", attrValue);
      }

   }
}
