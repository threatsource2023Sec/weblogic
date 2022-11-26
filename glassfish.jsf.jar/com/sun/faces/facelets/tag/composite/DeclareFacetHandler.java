package com.sun.faces.facelets.tag.composite;

import com.sun.faces.facelets.tag.TagHandlerImpl;
import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagException;

public class DeclareFacetHandler extends TagHandlerImpl {
   private static final String[] ATTRIBUTES = new String[]{"required", "displayName", "expert", "hidden", "preferred", "shortDescription", "default"};
   private static final PropertyHandlerManager ATTRIBUTE_MANAGER;
   private TagAttribute name = null;

   public DeclareFacetHandler(TagConfig config) {
      super(config);
      this.name = this.getRequiredAttribute("name");
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      if (null != parent && null != (parent = parent.getParent()) && ComponentHandler.isNew(parent)) {
         Map componentAttrs = parent.getAttributes();
         CompositeComponentBeanInfo componentBeanInfo = (CompositeComponentBeanInfo)componentAttrs.get("javax.faces.component.BEANINFO_KEY");
         ValueExpression ve = this.name.getValueExpression(ctx, String.class);
         String strValue = (String)ve.getValue(ctx);
         BeanDescriptor componentBeanDescriptor = componentBeanInfo.getBeanDescriptor();
         Map facetDescriptors = (Map)componentBeanDescriptor.getValue("javax.faces.component.FACETS_KEY");
         if (facetDescriptors == null) {
            facetDescriptors = new HashMap();
            componentBeanDescriptor.setValue("javax.faces.component.FACETS_KEY", facetDescriptors);
         }

         PropertyDescriptor propertyDescriptor;
         try {
            propertyDescriptor = new PropertyDescriptor(strValue, (Method)null, (Method)null);
         } catch (IntrospectionException var16) {
            throw new TagException(this.tag, "Unable to create property descriptor for facet" + strValue, var16);
         }

         ((Map)facetDescriptors).put(strValue, propertyDescriptor);
         TagAttribute[] var10 = this.tag.getAttributes().getAll();
         int var11 = var10.length;

         for(int var12 = 0; var12 < var11; ++var12) {
            TagAttribute tagAttribute = var10[var12];
            String attributeName = tagAttribute.getLocalName();
            PropertyHandler handler = ATTRIBUTE_MANAGER.getHandler(ctx, attributeName);
            if (handler != null) {
               handler.apply(ctx, attributeName, propertyDescriptor, tagAttribute);
            }
         }

         this.nextHandler.apply(ctx, parent);
      }
   }

   static {
      ATTRIBUTE_MANAGER = PropertyHandlerManager.getInstance(ATTRIBUTES);
   }
}
