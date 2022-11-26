package com.sun.faces.facelets.tag.jsf;

import com.sun.faces.facelets.el.LegacyMethodBinding;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.faces.component.ActionSource;
import javax.faces.component.ActionSource2;
import javax.faces.event.ActionEvent;
import javax.faces.event.MethodExpressionActionListener;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.MetaRule;
import javax.faces.view.facelets.Metadata;
import javax.faces.view.facelets.MetadataTarget;
import javax.faces.view.facelets.TagAttribute;

final class ActionSourceRule extends MetaRule {
   public static final Class[] ACTION_SIG = new Class[0];
   public static final Class[] ACTION_LISTENER_SIG = new Class[]{ActionEvent.class};
   public static final Class[] ACTION_LISTENER_ZEROARG_SIG = new Class[0];
   public static final ActionSourceRule Instance = new ActionSourceRule();

   public ActionSourceRule() {
   }

   public Metadata applyRule(String name, TagAttribute attribute, MetadataTarget meta) {
      if (meta.isTargetInstanceOf(ActionSource.class)) {
         if ("action".equals(name)) {
            if (meta.isTargetInstanceOf(ActionSource2.class)) {
               return new ActionMapper2(attribute);
            }

            return new ActionMapper(attribute);
         }

         if ("actionListener".equals(name)) {
            if (meta.isTargetInstanceOf(ActionSource2.class)) {
               return new ActionListenerMapper2(attribute);
            }

            return new ActionListenerMapper(attribute);
         }
      }

      return null;
   }

   static final class ActionListenerMapper2 extends Metadata {
      private final TagAttribute attr;

      public ActionListenerMapper2(TagAttribute attr) {
         this.attr = attr;
      }

      public void applyMetadata(FaceletContext ctx, Object instance) {
         ExpressionFactory expressionFactory = ctx.getExpressionFactory();
         MethodExpression methodExpressionOneArg = this.attr.getMethodExpression(ctx, (Class)null, ActionSourceRule.ACTION_LISTENER_SIG);
         MethodExpression methodExpressionZeroArg = expressionFactory.createMethodExpression(ctx, methodExpressionOneArg.getExpressionString(), Void.class, ActionSourceRule.ACTION_LISTENER_ZEROARG_SIG);
         ((ActionSource2)instance).addActionListener(new MethodExpressionActionListener(methodExpressionOneArg, methodExpressionZeroArg));
      }
   }

   static final class ActionListenerMapper extends Metadata {
      private final TagAttribute attr;

      public ActionListenerMapper(TagAttribute attr) {
         this.attr = attr;
      }

      public void applyMetadata(FaceletContext ctx, Object instance) {
         ((ActionSource)instance).setActionListener(new LegacyMethodBinding(this.attr.getMethodExpression(ctx, (Class)null, ActionSourceRule.ACTION_LISTENER_SIG)));
      }
   }

   static final class ActionMapper2 extends Metadata {
      private final TagAttribute attr;

      public ActionMapper2(TagAttribute attr) {
         this.attr = attr;
      }

      public void applyMetadata(FaceletContext ctx, Object instance) {
         ((ActionSource2)instance).setActionExpression(this.attr.getMethodExpression(ctx, Object.class, ActionSourceRule.ACTION_SIG));
      }
   }

   static final class ActionMapper extends Metadata {
      private final TagAttribute attr;

      public ActionMapper(TagAttribute attr) {
         this.attr = attr;
      }

      public void applyMetadata(FaceletContext ctx, Object instance) {
         ((ActionSource)instance).setAction(new LegacyMethodBinding(this.attr.getMethodExpression(ctx, Object.class, ActionSourceRule.ACTION_SIG)));
      }
   }
}
