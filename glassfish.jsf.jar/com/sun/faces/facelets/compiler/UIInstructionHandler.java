package com.sun.faces.facelets.compiler;

import com.sun.faces.facelets.el.ELText;
import com.sun.faces.facelets.impl.IdMapper;
import com.sun.faces.facelets.tag.jsf.ComponentSupport;
import com.sun.faces.facelets.util.FastWriter;
import java.io.IOException;
import java.io.Writer;
import javax.el.ELException;
import javax.faces.component.UIComponent;
import javax.faces.component.UniqueIdVendor;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;

final class UIInstructionHandler extends AbstractUIHandler {
   private final String alias;
   private final String id;
   private final ELText txt;
   private final Instruction[] instructions;
   private final int length;
   private final boolean literal;

   public UIInstructionHandler(String alias, String id, Instruction[] instructions, ELText txt) {
      this.alias = alias;
      this.id = id;
      this.instructions = instructions;
      this.txt = txt;
      this.length = txt.toString().length();
      boolean literal = true;
      int size = instructions.length;

      for(int i = 0; i < size; ++i) {
         Instruction ins = this.instructions[i];
         if (!ins.isLiteral()) {
            literal = false;
            break;
         }
      }

      this.literal = literal;
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      if (parent != null) {
         String id = ctx.generateUniqueId(this.id);
         FacesContext context = ctx.getFacesContext();
         UIComponent c = ComponentSupport.findUIInstructionChildByTagId(context, parent, id);
         boolean componentFound = false;
         boolean suppressEvents = false;
         if (c != null) {
            componentFound = true;
            suppressEvents = ComponentSupport.suppressViewModificationEvents(ctx.getFacesContext());
            ComponentSupport.markForDeletion((UIComponent)c);
         } else {
            Instruction[] applied;
            if (this.literal) {
               applied = this.instructions;
            } else {
               int size = this.instructions.length;
               applied = new Instruction[size];

               for(int i = 0; i < size; ++i) {
                  Instruction ins = this.instructions[i];
                  applied[i] = ins.apply(ctx.getExpressionFactory(), ctx);
               }
            }

            c = new UIInstructions(this.txt, applied);
            IdMapper mapper = IdMapper.getMapper(ctx.getFacesContext());
            String mid = mapper != null ? mapper.getAliasedId(id) : id;
            UIComponent ancestorNamingContainer = parent.getNamingContainer();
            String uid;
            if (null != ancestorNamingContainer && ancestorNamingContainer instanceof UniqueIdVendor) {
               uid = ((UniqueIdVendor)ancestorNamingContainer).createUniqueId(ctx.getFacesContext(), mid);
            } else {
               uid = ComponentSupport.getViewRoot(ctx, parent).createUniqueId(ctx.getFacesContext(), mid);
            }

            ((UIComponent)c).setId(uid);
            ((UIComponent)c).getAttributes().put("com.sun.faces.facelets.MARK_ID", id);
         }

         if (componentFound) {
            ComponentSupport.finalizeForDeletion((UIComponent)c);
            if (suppressEvents) {
               context.setProcessingEvents(false);
            }

            parent.getChildren().remove(c);
            if (suppressEvents) {
               context.setProcessingEvents(true);
            }
         }

         if (componentFound && suppressEvents) {
            context.setProcessingEvents(false);
         }

         this.addComponent(ctx, parent, (UIComponent)c);
         if (componentFound && suppressEvents) {
            context.setProcessingEvents(true);
         }
      }

   }

   public String toString() {
      return this.txt.toString();
   }

   public String getText() {
      return this.txt.toString();
   }

   public String getText(FaceletContext ctx) {
      Writer writer = new FastWriter(this.length);

      try {
         this.txt.apply(ctx.getExpressionFactory(), ctx).write(writer, ctx);
      } catch (IOException var4) {
         throw new ELException(this.alias + ": " + var4.getMessage(), var4.getCause());
      }

      return writer.toString();
   }
}
