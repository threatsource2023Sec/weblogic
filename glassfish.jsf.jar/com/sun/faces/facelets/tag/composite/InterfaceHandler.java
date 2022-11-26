package com.sun.faces.facelets.tag.composite;

import com.sun.faces.application.view.FaceletViewHandlingStrategy;
import com.sun.faces.facelets.tag.TagHandlerImpl;
import com.sun.faces.facelets.tag.jsf.ComponentSupport;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.el.ValueExpression;
import javax.faces.application.ProjectStage;
import javax.faces.application.Resource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.Tag;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagException;

public class InterfaceHandler extends TagHandlerImpl {
   private final Logger LOGGER;
   private static final String[] ATTRIBUTES_DEV = new String[]{"displayName", "expert", "hidden", "preferred", "shortDescription", "name", "componentType"};
   private static final PropertyHandlerManager INTERFACE_HANDLERS;
   public static final String Name = "interface";

   public InterfaceHandler(TagConfig config) {
      super(config);
      this.LOGGER = FacesLogger.TAGLIB.getLogger();
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      FacesContext context = ctx.getFacesContext();
      if (FaceletViewHandlingStrategy.isBuildingMetadata(context)) {
         this.imbueComponentWithMetadata(ctx, parent);
         this.nextHandler.apply(ctx, parent);
      } else if (ProjectStage.Development == context.getApplication().getProjectStage()) {
         this.validateComponent(context, parent);
      }

   }

   private void validateComponent(FacesContext context, UIComponent ccParent) throws TagException {
      UIComponent cc = ccParent.getParent();
      if (null == cc) {
         String clientId = ccParent.getClientId(context);
         throw new TagException(this.tag, MessageUtils.getExceptionMessageString("com.sun.faces.COMPONENT_NOT_FOUND_ERROR", clientId + ".getParent()"));
      } else {
         Tag usingPageTag = ComponentSupport.getTagForComponent(context, cc);
         Map attrs = cc.getAttributes();
         BeanInfo componentMetadata = (BeanInfo)attrs.get("javax.faces.component.BEANINFO_KEY");
         if (null == componentMetadata) {
            String clientId = ccParent.getClientId(context);
            throw new TagException(usingPageTag, MessageUtils.getExceptionMessageString("com.sun.faces.MISSING_COMPONENT_METADATA", clientId));
         } else {
            PropertyDescriptor[] declaredAttributes = componentMetadata.getPropertyDescriptors();
            boolean found = false;
            boolean required = false;
            StringBuffer buf = null;
            String attrMessage = "";
            String facetMessage = "";
            PropertyDescriptor[] var15 = declaredAttributes;
            int var16 = declaredAttributes.length;

            String key;
            Object requiredValue;
            for(int var17 = 0; var17 < var16; ++var17) {
               PropertyDescriptor cur = var15[var17];
               required = false;
               requiredValue = cur.getValue("required");
               if (null != requiredValue && requiredValue instanceof ValueExpression) {
                  requiredValue = ((ValueExpression)requiredValue).getValue(context.getELContext());
                  required = Boolean.parseBoolean(requiredValue.toString());
               }

               if (required) {
                  key = cur.getName();
                  found = false;
                  if (null != cur.getValue("method-signature") && null == cur.getValue("type")) {
                     found = null != cc.getValueExpression(key);
                  } else {
                     found = attrs.containsKey(key);
                     if (!found) {
                        found = null != cc.getValueExpression(key);
                     }
                  }

                  if (!found) {
                     if (null == buf) {
                        buf = new StringBuffer();
                        buf.append(key);
                     } else {
                        buf.append(", " + key);
                     }
                  }
               }
            }

            if (null != buf) {
               attrMessage = MessageUtils.getExceptionMessageString("com.sun.faces.MISSING_COMPONENT_ATTRIBUTE_VALUE", buf.toString());
            }

            buf = null;
            Map declaredFacets = (Map)componentMetadata.getBeanDescriptor().getValue("javax.faces.component.FACETS_KEY");
            if (null != declaredFacets) {
               Iterator var22 = declaredFacets.values().iterator();

               while(var22.hasNext()) {
                  PropertyDescriptor cur = (PropertyDescriptor)var22.next();
                  required = false;
                  requiredValue = cur.getValue("required");
                  if (null != requiredValue && requiredValue instanceof ValueExpression) {
                     requiredValue = ((ValueExpression)requiredValue).getValue(context.getELContext());
                     required = Boolean.parseBoolean(requiredValue.toString());
                  }

                  if (required) {
                     key = cur.getName();
                     if (!cc.getFacets().containsKey(key)) {
                        if (null == buf) {
                           buf = new StringBuffer();
                           buf.append(key);
                        } else {
                           buf.append(", " + key);
                        }
                     }
                  }
               }
            }

            if (null != buf) {
               facetMessage = MessageUtils.getExceptionMessageString("com.sun.faces.MISSING_COMPONENT_FACET", buf.toString());
            }

            if (0 < attrMessage.length() || 0 < facetMessage.length()) {
               throw new TagException(usingPageTag, attrMessage + " " + facetMessage);
            }
         }
      }
   }

   private void imbueComponentWithMetadata(FaceletContext ctx, UIComponent parent) {
      if (null != parent && null != (parent = parent.getParent()) && ComponentHandler.isNew(parent)) {
         Map attrs = parent.getAttributes();
         CompositeComponentBeanInfo componentBeanInfo = (CompositeComponentBeanInfo)attrs.get("javax.faces.component.BEANINFO_KEY");
         if (componentBeanInfo == null) {
            componentBeanInfo = new CompositeComponentBeanInfo();
            attrs.put("javax.faces.component.BEANINFO_KEY", componentBeanInfo);
            BeanDescriptor componentDescriptor = new BeanDescriptor(parent.getClass());
            componentBeanInfo.setBeanDescriptor(componentDescriptor);
            TagAttribute[] var6 = this.tag.getAttributes().getAll();
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               TagAttribute tagAttribute = var6[var8];
               String attributeName = tagAttribute.getLocalName();
               PropertyHandler handler = INTERFACE_HANDLERS.getHandler(ctx, attributeName);
               if (handler != null) {
                  handler.apply(ctx, attributeName, componentDescriptor, tagAttribute);
               }
            }

            List targetList = (List)componentDescriptor.getValue("javax.faces.view.AttachedObjectTargets");
            if (null == targetList) {
               List targetList = new ArrayList();
               componentDescriptor.setValue("javax.faces.view.AttachedObjectTargets", targetList);
            }

            Resource componentResource = (Resource)attrs.get("javax.faces.application.Resource.ComponentResource");
            if (null == componentResource) {
               throw new NullPointerException("Unable to find Resource for composite component");
            }
         }

      }
   }

   static {
      INTERFACE_HANDLERS = PropertyHandlerManager.getInstance(ATTRIBUTES_DEV);
   }
}
