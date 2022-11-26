package com.sun.faces.facelets.tag.jsf;

import com.sun.faces.component.CompositeComponentStackManager;
import com.sun.faces.component.behavior.AjaxBehaviors;
import com.sun.faces.component.validator.ComponentValidators;
import com.sun.faces.context.StateContext;
import com.sun.faces.facelets.impl.IdMapper;
import com.sun.faces.facelets.tag.MetaRulesetImpl;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.ProjectStage;
import javax.faces.component.ActionSource;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectMany;
import javax.faces.component.UISelectOne;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UniqueIdVendor;
import javax.faces.component.ValueHolder;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.MetaRuleset;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagException;
import javax.faces.view.facelets.TagHandlerDelegate;

public class ComponentTagHandlerDelegateImpl extends TagHandlerDelegate {
   private ComponentHandler owner;
   private static final Logger log;
   private final TagAttribute binding;
   protected String componentType;
   protected final TagAttribute id;
   private final String rendererType;
   private CreateComponentDelegate createCompositeComponentDelegate;

   public ComponentTagHandlerDelegateImpl(ComponentHandler owner) {
      this.owner = owner;
      ComponentConfig config = owner.getComponentConfig();
      this.componentType = config.getComponentType();
      this.rendererType = config.getRendererType();
      this.id = owner.getTagAttribute("id");
      this.binding = owner.getTagAttribute("binding");
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      FacesContext context = ctx.getFacesContext();
      if (parent == null) {
         throw new TagException(this.owner.getTag(), "Parent UIComponent was null");
      } else {
         String id = ctx.generateUniqueId(this.owner.getTagId());
         UIComponent c = this.findChild(ctx, parent, id);
         if (null == c && context.isPostback() && UIComponent.isCompositeComponent(parent) && parent.getAttributes().get(id) != null) {
            c = this.findReparentedComponent(ctx, parent, id);
         } else if (c != null && c.getParent() != parent && c.getAttributes().containsKey("com.sun.faces.DynamicComponent")) {
            c.getParent().getChildren().remove(c);
         }

         boolean componentFound = false;
         boolean parentModified = false;
         if (c != null) {
            componentFound = true;
            this.doExistingComponentActions(ctx, id, c);
         } else {
            if (this.suppressRemovedChild(parent, id)) {
               return;
            }

            c = this.owner.createComponent(ctx);
            if (c == null) {
               c = this.createComponent(ctx);
            }

            this.doNewComponentActions(ctx, id, c);
            this.assignUniqueId(ctx, parent, id, c);
            this.owner.onComponentCreated(ctx, c, parent);
         }

         CompositeComponentStackManager ccStackManager = CompositeComponentStackManager.getManager(context);
         boolean compcompPushed = this.pushComponentToEL(ctx, c, ccStackManager);
         if (ProjectStage.Development == context.getApplication().getProjectStage()) {
            ComponentSupport.setTagForComponent(context, c, this.owner.getTag());
         }

         boolean isNaming = false;
         if (c instanceof NamingContainer) {
            isNaming = true;
            IterationIdManager.startNamingContainer(ctx);
         }

         try {
            this.owner.applyNextHandler(ctx, c);
         } finally {
            if (isNaming) {
               IterationIdManager.stopNamingContainer(ctx);
            }

         }

         if (componentFound) {
            parentModified = this.isParentChildrenModified(parent);
            this.doOrphanedChildCleanup(ctx, parent, c, parentModified);
         }

         this.privateOnComponentPopulated(ctx, c);
         this.owner.onComponentPopulated(ctx, c, parent);
         this.addComponentToView(ctx, parent, c, componentFound, parentModified);
         ComponentSupport.copyPassthroughAttributes(ctx, c, this.owner.getTag());
         this.adjustIndexOfDynamicChildren(context, c);
         this.popComponentFromEL(ctx, c, ccStackManager, compcompPushed);
      }
   }

   protected boolean isIterating(FaceletContext context) {
      return IterationIdManager.isIterating(context);
   }

   private boolean suppressRemovedChild(UIComponent parent, String childTagId) {
      Collection removedChildren = (Collection)parent.getAttributes().get("com.sun.faces.facelets.REMOVED_CHILDREN");
      return removedChildren != null && removedChildren.contains(childTagId);
   }

   private boolean isParentChildrenModified(UIComponent parent) {
      return parent.getAttributes().get("com.sun.faces.facelets.MARK_CHILDREN_MODIFIED") != null;
   }

   private void adjustIndexOfDynamicChildren(FacesContext context, UIComponent parent) {
      StateContext stateContext = StateContext.getStateContext(context);
      if (stateContext.hasOneOrMoreDynamicChild(parent)) {
         List children = parent.getChildren();
         List dynamicChildren = Collections.emptyList();
         Iterator var6 = children.iterator();

         UIComponent cur;
         while(var6.hasNext()) {
            cur = (UIComponent)var6.next();
            if (stateContext.componentAddedDynamically(cur)) {
               if (((List)dynamicChildren).isEmpty()) {
                  dynamicChildren = new ArrayList(children.size());
               }

               ((List)dynamicChildren).add(cur);
            }
         }

         var6 = ((List)dynamicChildren).iterator();

         int i;
         while(var6.hasNext()) {
            cur = (UIComponent)var6.next();
            i = stateContext.getIndexOfDynamicallyAddedChildInParent(cur);
            if (-1 != i) {
               children.remove(cur);
            }
         }

         var6 = ((List)dynamicChildren).iterator();

         while(var6.hasNext()) {
            cur = (UIComponent)var6.next();
            i = stateContext.getIndexOfDynamicallyAddedChildInParent(cur);
            if (-1 != i) {
               if (i < children.size()) {
                  children.add(i, cur);
               } else {
                  children.add(cur);
               }
            }
         }

      }
   }

   public MetaRuleset createMetaRuleset(Class type) {
      Util.notNull("type", type);
      MetaRuleset m = new MetaRulesetImpl(this.owner.getTag(), type);
      m.ignore("binding").ignore("id");
      m.addRule(ComponentRule.Instance);
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

   private void addComponentToView(FaceletContext ctx, UIComponent parent, UIComponent c, boolean componentFound, boolean parentModified) {
      if (!componentFound || !parentModified) {
         this.addComponentToView(ctx, parent, c, componentFound);
      }

   }

   protected void addComponentToView(FaceletContext ctx, UIComponent parent, UIComponent c, boolean componentFound) {
      FacesContext context = ctx.getFacesContext();
      boolean suppressEvents = ComponentSupport.suppressViewModificationEvents(context);
      boolean compcomp = UIComponent.isCompositeComponent(c);
      if (suppressEvents && componentFound && !compcomp) {
         context.setProcessingEvents(false);
      }

      ComponentSupport.addComponent(ctx, parent, c);
      if (suppressEvents && componentFound && !compcomp) {
         context.setProcessingEvents(true);
      }

   }

   protected boolean pushComponentToEL(FaceletContext ctx, UIComponent c, CompositeComponentStackManager ccStackManager) {
      c.pushComponentToEL(ctx.getFacesContext(), c);
      boolean compcompPushed = false;
      if (UIComponent.isCompositeComponent(c)) {
         compcompPushed = ccStackManager.push(c, CompositeComponentStackManager.StackType.TreeCreation);
      }

      return compcompPushed;
   }

   protected void popComponentFromEL(FaceletContext ctx, UIComponent c, CompositeComponentStackManager ccStackManager, boolean compCompPushed) {
      c.popComponentFromEL(ctx.getFacesContext());
      if (compCompPushed) {
         ccStackManager.pop(CompositeComponentStackManager.StackType.TreeCreation);
      }

   }

   private void doOrphanedChildCleanup(FaceletContext ctx, UIComponent parent, UIComponent c, boolean parentModified) {
      if (parentModified) {
         ComponentSupport.finalizeForDeletion(c);
      } else {
         this.doOrphanedChildCleanup(ctx, parent, c);
      }

   }

   protected void doOrphanedChildCleanup(FaceletContext ctx, UIComponent parent, UIComponent c) {
      ComponentSupport.finalizeForDeletion(c);
      if (this.getFacetName(parent) == null) {
         FacesContext context = ctx.getFacesContext();
         boolean suppressEvents = ComponentSupport.suppressViewModificationEvents(context);
         if (suppressEvents) {
            context.setProcessingEvents(false);
         }

         parent.getChildren().remove(c);
         if (suppressEvents) {
            context.setProcessingEvents(true);
         }
      }

   }

   protected void assignUniqueId(FaceletContext ctx, UIComponent parent, String id, UIComponent c) {
      if (this.id == null || this.id.isLiteral() && IterationIdManager.registerLiteralId(ctx, this.id.getValue())) {
         UIViewRoot root = ComponentSupport.getViewRoot(ctx, parent);
         if (root != null) {
            IdMapper mapper = IdMapper.getMapper(ctx.getFacesContext());
            String mid = mapper != null ? mapper.getAliasedId(id) : id;
            UIComponent ancestorNamingContainer = parent.getNamingContainer();
            String uid;
            if (null != ancestorNamingContainer && ancestorNamingContainer instanceof UniqueIdVendor) {
               uid = ((UniqueIdVendor)ancestorNamingContainer).createUniqueId(ctx.getFacesContext(), mid);
            } else {
               uid = root.createUniqueId(ctx.getFacesContext(), mid);
            }

            c.setId(uid);
         }
      } else {
         c.setId(this.id.getValue(ctx));
      }

      if (this.rendererType != null) {
         c.setRendererType(this.rendererType);
      }

   }

   protected void doNewComponentActions(FaceletContext ctx, String id, UIComponent c) {
      if (log.isLoggable(Level.FINE)) {
         log.fine(this.owner.getTag() + " Component[" + id + "] Created: " + c.getClass().getName());
      }

      if (null == this.createCompositeComponentDelegate) {
         this.owner.setAttributes(ctx, c);
      }

      c.getAttributes().put("com.sun.faces.facelets.MARK_ID", id);
      if (ctx.getFacesContext().isProjectStage(ProjectStage.Development)) {
         c.getAttributes().put("javax.faces.component.VIEW_LOCATION_KEY", this.owner.getTag().getLocation());
      }

   }

   protected void doExistingComponentActions(FaceletContext ctx, String id, UIComponent c) {
      if (log.isLoggable(Level.FINE)) {
         log.fine(this.owner.getTag() + " Component[" + id + "] Found, marking children for cleanup");
      }

      ComponentSupport.markForDeletion(c);
      if (this.id != null) {
         boolean autoGenerated = this.id.isLiteral() && IterationIdManager.registerLiteralId(ctx, this.id.getValue());
         if (!autoGenerated) {
            c.setId(this.id.getValue(ctx));
         }
      }

   }

   protected UIComponent findChild(FaceletContext ctx, UIComponent parent, String tagId) {
      return ComponentSupport.findChildByTagId(ctx.getFacesContext(), parent, tagId);
   }

   protected UIComponent findReparentedComponent(FaceletContext ctx, UIComponent parent, String tagId) {
      UIComponent facet = (UIComponent)parent.getFacets().get("javax.faces.component.COMPOSITE_FACET_NAME");
      if (facet != null) {
         UIComponent newParent = facet.findComponent((String)parent.getAttributes().get(tagId));
         if (newParent != null) {
            return ComponentSupport.findChildByTagId(ctx.getFacesContext(), newParent, tagId);
         }
      }

      return null;
   }

   void setCreateCompositeComponentDelegate(CreateComponentDelegate createComponentDelegate) {
      this.createCompositeComponentDelegate = createComponentDelegate;
   }

   private UIComponent createComponent(FaceletContext ctx) {
      if (null != this.createCompositeComponentDelegate) {
         return this.createCompositeComponentDelegate.createComponent(ctx);
      } else {
         FacesContext faces = ctx.getFacesContext();
         Application app = faces.getApplication();
         UIComponent c;
         if (this.binding != null) {
            ValueExpression ve = this.binding.getValueExpression(ctx, Object.class);
            c = app.createComponent(ve, faces, this.componentType, this.rendererType);
            if (c != null) {
               c.setValueExpression("binding", ve);
            }
         } else {
            c = app.createComponent(faces, this.componentType, this.rendererType);
         }

         return c;
      }
   }

   private void privateOnComponentPopulated(FaceletContext ctx, UIComponent c) {
      if (c instanceof ClientBehaviorHolder) {
         FacesContext context = ctx.getFacesContext();
         AjaxBehaviors ajaxBehaviors = AjaxBehaviors.getAjaxBehaviors(context, false);
         if (ajaxBehaviors != null) {
            ajaxBehaviors.addBehaviors(context, (ClientBehaviorHolder)c);
         }
      }

      if (c instanceof EditableValueHolder) {
         this.processValidators(ctx.getFacesContext(), (EditableValueHolder)c);
      }

   }

   private void processValidators(FacesContext ctx, EditableValueHolder editableValueHolder) {
      ComponentValidators componentValidators = ComponentValidators.getValidators(ctx, false);
      if (componentValidators != null) {
         componentValidators.addValidators(ctx, editableValueHolder);
      } else {
         ComponentValidators.addDefaultValidatorsToComponent(ctx, editableValueHolder);
      }

   }

   private String getFacetName(UIComponent parent) {
      return (String)parent.getAttributes().get("facelets.FACET_NAME");
   }

   static {
      log = FacesLogger.FACELETS_COMPONENT.getLogger();
   }

   interface CreateComponentDelegate {
      UIComponent createComponent(FaceletContext var1);

      void setCompositeComponent(FacesContext var1, UIComponent var2);

      UIComponent getCompositeComponent(FacesContext var1);
   }
}
