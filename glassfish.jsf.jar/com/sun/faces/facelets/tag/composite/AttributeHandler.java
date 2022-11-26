package com.sun.faces.facelets.tag.composite;

import com.sun.faces.facelets.tag.TagHandlerImpl;
import com.sun.faces.facelets.util.ReflectionUtil;
import com.sun.faces.util.FacesLogger;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributeException;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagException;

public class AttributeHandler extends TagHandlerImpl {
   private final Logger LOGGER;
   private static final String[] COMPOSITE_ATTRIBUTE_ATTRIBUTES = new String[]{"required", "targets", "targetAttributeName", "default", "displayName", "preferred", "hidden", "expert", "shortDescription", "method-signature", "type"};
   private static final PropertyHandlerManager ATTRIBUTE_MANAGER;
   private TagAttribute name;

   public AttributeHandler(TagConfig config) {
      super(config);
      this.LOGGER = FacesLogger.TAGLIB.getLogger();
      this.name = this.getRequiredAttribute("name");
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      if (null != parent && null != (parent = parent.getParent()) && ComponentHandler.isNew(parent)) {
         Map attrs = parent.getAttributes();
         CompositeComponentBeanInfo componentBeanInfo = (CompositeComponentBeanInfo)attrs.get("javax.faces.component.BEANINFO_KEY");

         assert null != componentBeanInfo;

         List declaredAttributes = componentBeanInfo.getPropertyDescriptorsList();
         ValueExpression ve = this.name.getValueExpression(ctx, String.class);
         String strValue = (String)ve.getValue(ctx);
         Iterator var8 = declaredAttributes.iterator();

         PropertyDescriptor cur;
         do {
            if (!var8.hasNext()) {
               CCAttributePropertyDescriptor propertyDescriptor;
               try {
                  propertyDescriptor = new CCAttributePropertyDescriptor(strValue, (Method)null, (Method)null);
                  declaredAttributes.add(propertyDescriptor);
               } catch (IntrospectionException var18) {
                  throw new TagException(this.tag, "Unable to create property descriptor for property " + strValue, var18);
               }

               TagAttribute defaultTagAttribute = null;
               PropertyHandler defaultHandler = null;
               TagAttribute[] var11 = this.tag.getAttributes().getAll();
               int var12 = var11.length;

               for(int var13 = 0; var13 < var12; ++var13) {
                  TagAttribute tagAttribute = var11[var13];
                  String attributeName = tagAttribute.getLocalName();
                  if ("default".equals(attributeName)) {
                     defaultTagAttribute = tagAttribute;
                     defaultHandler = ATTRIBUTE_MANAGER.getHandler(ctx, "default");
                  } else {
                     PropertyHandler handler = ATTRIBUTE_MANAGER.getHandler(ctx, attributeName);
                     if (handler != null) {
                        handler.apply(ctx, attributeName, propertyDescriptor, tagAttribute);
                     }
                  }
               }

               if (defaultHandler != null) {
                  try {
                     defaultHandler.apply(ctx, "default", propertyDescriptor, defaultTagAttribute);
                  } catch (IllegalArgumentException var17) {
                     throw new TagException(this.tag, "'type' could not be resolved: " + var17.getCause(), var17.getCause());
                  }
               }

               this.nextHandler.apply(ctx, parent);
               return;
            }

            cur = (PropertyDescriptor)var8.next();
         } while(!strValue.endsWith(cur.getName()));

      }
   }

   static {
      ATTRIBUTE_MANAGER = PropertyHandlerManager.getInstance(COMPOSITE_ATTRIBUTE_ATTRIBUTES);
   }

   private class CCAttributePropertyDescriptor extends PropertyDescriptor {
      public CCAttributePropertyDescriptor(String propertyName, Method readMethod, Method writeMethod) throws IntrospectionException {
         super(propertyName, readMethod, writeMethod);
      }

      public Object getValue(String attributeName) {
         Object result = super.getValue(attributeName);
         if ("type".equals(attributeName) && null != result && !(result instanceof Class)) {
            FacesContext context = FacesContext.getCurrentInstance();
            ELContext elContext = context.getELContext();
            String classStr = (String)((ValueExpression)result).getValue(elContext);
            if (null != classStr) {
               try {
                  result = ReflectionUtil.forName(classStr);
                  this.setValue(attributeName, result);
               } catch (ClassNotFoundException var10) {
                  classStr = "java.lang." + classStr;
                  boolean throwException = false;

                  try {
                     result = ReflectionUtil.forName(classStr);
                     this.setValue(attributeName, result);
                  } catch (ClassNotFoundException var9) {
                     throwException = true;
                  }

                  if (throwException) {
                     String message = "Unable to obtain class for " + classStr;
                     if (AttributeHandler.this.LOGGER.isLoggable(Level.INFO)) {
                        AttributeHandler.this.LOGGER.log(Level.INFO, message, var10);
                     }

                     throw new TagAttributeException(AttributeHandler.this.tag, AttributeHandler.this.name, message, var10);
                  }
               }
            }
         }

         return result;
      }
   }
}
