package com.sun.faces.facelets.tag.jsf;

import com.sun.faces.facelets.el.VariableMapperWrapper;
import com.sun.faces.facelets.tag.MetaRulesetImpl;
import com.sun.faces.facelets.tag.MetadataTargetImpl;
import com.sun.faces.facelets.util.ReflectionUtil;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELException;
import javax.el.ValueExpression;
import javax.el.VariableMapper;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.ProjectStage;
import javax.faces.application.Resource;
import javax.faces.component.ActionSource;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.component.UISelectMany;
import javax.faces.component.UISelectOne;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewDeclarationLanguage;
import javax.faces.view.ViewDeclarationLanguageFactory;
import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.MetaRule;
import javax.faces.view.facelets.MetaRuleset;
import javax.faces.view.facelets.Metadata;
import javax.faces.view.facelets.MetadataTarget;
import javax.faces.view.facelets.Tag;
import javax.faces.view.facelets.TagAttribute;

public class CompositeComponentTagHandler extends ComponentHandler implements ComponentTagHandlerDelegateImpl.CreateComponentDelegate {
   private static final Logger LOGGER;
   private Resource ccResource;
   private TagAttribute binding;
   private static final String ccInstanceVariableStandinKey;
   private static final String ATTACHED_OBJECT_HANDLERS_KEY = "javax.faces.view.AttachedObjectHandlers";

   public CompositeComponentTagHandler(Resource ccResource, ComponentConfig config) {
      super(config);
      this.ccResource = ccResource;
      this.binding = config.getTag().getAttributes().get("binding");
      ((ComponentTagHandlerDelegateImpl)this.getTagHandlerDelegate()).setCreateCompositeComponentDelegate(this);
   }

   public UIComponent createComponent(FaceletContext ctx) {
      FacesContext context = ctx.getFacesContext();
      UIComponent cc;
      if (this.binding != null) {
         ValueExpression ve = this.binding.getValueExpression(ctx, UIComponent.class);
         cc = (UIComponent)ve.getValue(ctx);
         if (cc != null && !UIComponent.isCompositeComponent(cc)) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, "jsf.compcomp.binding.eval.non.compcomp", this.binding.toString());
            }

            cc = null;
         }

         if (cc == null) {
            cc = context.getApplication().createComponent(context, this.ccResource);
            cc.setValueExpression("binding", ve);
            ve.setValue(ctx, cc);
         }
      } else {
         cc = context.getApplication().createComponent(context, this.ccResource);
      }

      context.getViewRoot().getAttributes().put("com.sun.faces.TreeHasDynamicComponents", Boolean.TRUE);
      this.setCompositeComponent(context, cc);
      return cc;
   }

   public void applyNextHandler(FaceletContext ctx, UIComponent c) throws IOException, FacesException, ELException {
      this.setAttributes(ctx, c);
      super.applyNextHandler(ctx, c);
      this.applyCompositeComponent(ctx, c);
      if (ComponentHandler.isNew(c)) {
         FacesContext context = ctx.getFacesContext();
         String viewId = context.getViewRoot().getViewId();
         ViewDeclarationLanguageFactory factory = (ViewDeclarationLanguageFactory)FactoryFinder.getFactory("javax.faces.view.ViewDeclarationLanguageFactory");
         ViewDeclarationLanguage vdl = factory.getViewDeclarationLanguage(viewId);
         vdl.retargetAttachedObjects(context, c, getAttachedObjectHandlers(c, false));
         vdl.retargetMethodExpressions(context, c);
         getAttachedObjectHandlers(c).clear();
      }

   }

   public void setCompositeComponent(FacesContext context, UIComponent cc) {
      Map contextMap = context.getAttributes();
      String key = ccInstanceVariableStandinKey + this.tagId;
      if (!contextMap.containsKey(key)) {
         contextMap.put(key, cc);
      }

   }

   public UIComponent getCompositeComponent(FacesContext context) {
      Map contextMap = context.getAttributes();
      String key = ccInstanceVariableStandinKey + this.tagId;
      UIComponent result = (UIComponent)contextMap.get(key);
      return result;
   }

   public void setAttributes(FaceletContext ctx, Object instance) {
      if (instance != null) {
         if (ctx.getFacesContext().isProjectStage(ProjectStage.Development)) {
            Metadata meta = this.createMetaRuleset(instance.getClass()).finish();
            meta.applyMetadata(ctx, instance);
         } else {
            super.setAttributes(ctx, instance);
         }
      }

   }

   protected MetaRuleset createMetaRuleset(Class type) {
      Util.notNull("type", type);
      FacesContext context = FacesContext.getCurrentInstance();
      UIComponent cc = this.getCompositeComponent(context);
      if (null == cc) {
         FaceletContext faceletContext = (FaceletContext)context.getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
         cc = this.createComponent(faceletContext);
         this.setCompositeComponent(context, cc);
      }

      MetaRuleset m = new CompositeComponentMetaRuleset(this.getTag(), type, (BeanInfo)cc.getAttributes().get("javax.faces.component.BEANINFO_KEY"));
      m.ignore("binding").ignore("id");
      m.addRule(CompositeComponentTagHandler.CompositeComponentRule.Instance);
      if (ActionSource.class.isAssignableFrom(type)) {
         m.addRule(ActionSourceRule.Instance);
      }

      if (ValueHolder.class.isAssignableFrom(type)) {
         m.addRule(ValueHolderRule.Instance);
         if (EditableValueHolder.class.isAssignableFrom(type)) {
            m.ignore("submittedValue");
            m.ignore("valid");
            m.addRule(EditableValueHolderRule.Instance);
         }
      }

      if (UISelectOne.class.isAssignableFrom(type) || UISelectMany.class.isAssignableFrom(type)) {
         m.addRule(RenderPropertyRule.Instance);
      }

      return m;
   }

   public static List getAttachedObjectHandlers(UIComponent component) {
      return getAttachedObjectHandlers(component, true);
   }

   public static List getAttachedObjectHandlers(UIComponent component, boolean create) {
      Map attrs = component.getAttributes();
      List result = (List)attrs.get("javax.faces.view.AttachedObjectHandlers");
      if (result == null) {
         if (create) {
            result = new ArrayList();
            attrs.put("javax.faces.view.AttachedObjectHandlers", result);
         } else {
            result = Collections.EMPTY_LIST;
         }
      }

      return (List)result;
   }

   private void applyCompositeComponent(FaceletContext ctx, UIComponent c) throws IOException {
      FacesContext facesContext = ctx.getFacesContext();
      VariableMapper orig = ctx.getVariableMapper();
      UIPanel facetComponent;
      if (ComponentHandler.isNew(c)) {
         facetComponent = (UIPanel)facesContext.getApplication().createComponent("javax.faces.Panel");
         facetComponent.setRendererType("javax.faces.Group");
         c.getFacets().put("javax.faces.component.COMPOSITE_FACET_NAME", facetComponent);
      } else {
         facetComponent = (UIPanel)c.getFacets().get("javax.faces.component.COMPOSITE_FACET_NAME");
      }

      assert null != facetComponent;

      try {
         VariableMapper wrapper = new VariableMapperWrapper(orig) {
            public ValueExpression resolveVariable(String variable) {
               return super.resolveVariable(variable);
            }
         };
         ctx.setVariableMapper(wrapper);
         ctx.includeFacelet(facetComponent, (URL)this.ccResource.getURL());
      } finally {
         ctx.setVariableMapper(orig);
      }

   }

   static {
      LOGGER = FacesLogger.TAGLIB.getLogger();
      ccInstanceVariableStandinKey = CompositeComponentTagHandler.class.getName() + "_";
   }

   private static class CompositeComponentRule extends MetaRule {
      private static final CompositeComponentRule Instance = new CompositeComponentRule();

      public Metadata applyRule(String name, TagAttribute attribute, MetadataTarget meta) {
         if (meta.isTargetInstanceOf(UIComponent.class)) {
            Class type = meta.getPropertyType(name);
            if (type == null) {
               type = Object.class;
            }

            return (Metadata)(!attribute.isLiteral() ? new CompositeExpressionMetadata(name, type, attribute) : new LiteralAttributeMetadata(name, type, attribute));
         } else {
            return null;
         }
      }

      private static final class CompositeExpressionMetadata extends Metadata {
         private String name;
         private Class type;
         private TagAttribute attr;

         public CompositeExpressionMetadata(String name, Class type, TagAttribute attr) {
            this.name = name;
            this.type = type;
            this.attr = attr;
         }

         public void applyMetadata(FaceletContext ctx, Object instance) {
            ValueExpression ve = this.attr.getValueExpression(ctx, this.type);
            UIComponent cc = (UIComponent)instance;

            assert UIComponent.isCompositeComponent(cc);

            Map attrs = cc.getAttributes();
            BeanInfo componentMetadata = (BeanInfo)attrs.get("javax.faces.component.BEANINFO_KEY");
            BeanDescriptor desc = componentMetadata.getBeanDescriptor();
            Collection attributesWithDeclaredDefaultValues = (Collection)desc.getValue("javax.faces.component.ATTR_NAMES_WITH_DEFAULT_VALUES");
            if (null != attributesWithDeclaredDefaultValues && attributesWithDeclaredDefaultValues.contains(this.name) && attrs.containsKey(this.name)) {
               attrs.remove(this.name);
            }

            cc.setValueExpression(this.name, ve);
         }
      }

      private static final class LiteralAttributeMetadata extends Metadata {
         private String name;
         private Class type;
         private TagAttribute attribute;

         public LiteralAttributeMetadata(String name, Class type, TagAttribute attribute) {
            this.name = name;
            this.type = type;
            this.attribute = attribute;
         }

         public void applyMetadata(FaceletContext ctx, Object instance) {
            UIComponent c = (UIComponent)instance;
            Object value = this.attribute.getObject(ctx, this.type);
            if (value != null) {
               c.getAttributes().put(this.name, value);
            }

         }
      }
   }

   private static final class CompositeComponentMetaRuleset extends MetaRulesetImpl {
      private BeanInfo compBeanInfo;
      private Class type;

      public CompositeComponentMetaRuleset(Tag tag, Class type, BeanInfo compBeanInfo) {
         super(tag, type);
         this.compBeanInfo = compBeanInfo;
         this.type = type;
      }

      protected MetadataTarget getMetadataTarget() {
         try {
            return new CompositeMetadataTarget(this.type, this.compBeanInfo);
         } catch (IntrospectionException var2) {
            throw new FacesException(var2);
         }
      }

      private static final class CompositeMetadataTarget extends MetadataTargetImpl {
         private BeanInfo compBeanInfo;

         public CompositeMetadataTarget(Class type, BeanInfo compBeanInfo) throws IntrospectionException {
            super(type);
            this.compBeanInfo = compBeanInfo;
         }

         public Class getPropertyType(String name) {
            PropertyDescriptor compDescriptor = this.findDescriptor(name);
            if (compDescriptor != null) {
               Object obj = compDescriptor.getValue("type");
               if (null != obj && !(obj instanceof Class)) {
                  ValueExpression typeVE = (ValueExpression)obj;
                  String className = (String)typeVE.getValue(FacesContext.getCurrentInstance().getELContext());
                  if (className != null) {
                     className = this.prefix(className);

                     try {
                        return ReflectionUtil.forName(className);
                     } catch (ClassNotFoundException var7) {
                        throw new FacesException(var7);
                     }
                  } else {
                     return Object.class;
                  }
               } else {
                  return (Class)obj;
               }
            } else {
               return super.getPropertyType(name);
            }
         }

         private PropertyDescriptor findDescriptor(String name) {
            PropertyDescriptor[] var2 = this.compBeanInfo.getPropertyDescriptors();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               PropertyDescriptor pd = var2[var4];
               if (pd.getName().equals(name)) {
                  return pd;
               }
            }

            return null;
         }

         private String prefix(String className) {
            return className.indexOf(46) == -1 && Character.isUpperCase(className.charAt(0)) ? "java.lang." + className : className;
         }
      }
   }
}
