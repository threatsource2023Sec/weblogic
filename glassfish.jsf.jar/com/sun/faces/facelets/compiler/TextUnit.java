package com.sun.faces.facelets.compiler;

import com.sun.faces.facelets.el.ELText;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.el.ELException;
import javax.faces.view.facelets.CompositeFaceletHandler;
import javax.faces.view.facelets.FaceletHandler;
import javax.faces.view.facelets.Tag;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagException;

final class TextUnit extends CompilationUnit {
   private final StringBuffer buffer;
   private final StringBuffer textBuffer;
   private final List instructionBuffer;
   private final Stack tags;
   private final List children;
   private boolean startTagOpen;
   private final String alias;
   private final String id;

   public TextUnit(String alias, String id) {
      this.alias = alias;
      this.id = id;
      this.buffer = new StringBuffer();
      this.textBuffer = new StringBuffer();
      this.instructionBuffer = new ArrayList();
      this.tags = new Stack();
      this.children = new ArrayList();
      this.startTagOpen = false;
   }

   public FaceletHandler createFaceletHandler() {
      this.flushBufferToConfig(true);
      if (this.children.size() == 0) {
         return LEAF;
      } else {
         FaceletHandler[] h = new FaceletHandler[this.children.size()];

         for(int i = 0; i < h.length; ++i) {
            Object obj = this.children.get(i);
            if (obj instanceof FaceletHandler) {
               h[i] = (FaceletHandler)obj;
            } else {
               h[i] = ((CompilationUnit)obj).createFaceletHandler();
            }
         }

         if (h.length == 1) {
            return h[0];
         } else {
            return new CompositeFaceletHandler(h);
         }
      }
   }

   private void addInstruction(Instruction instruction) {
      this.flushTextBuffer(false);
      this.instructionBuffer.add(instruction);
   }

   private void flushTextBuffer(boolean child) {
      if (this.textBuffer.length() > 0) {
         String s = this.textBuffer.toString();
         if (child) {
            s = trimRight(s);
         }

         if (s.length() > 0) {
            ELText txt = ELText.parse(s, this.alias);
            if (txt != null) {
               if (txt.isLiteral()) {
                  this.instructionBuffer.add(new LiteralTextInstruction(txt.toString()));
               } else {
                  this.instructionBuffer.add(new TextInstruction(this.alias, txt));
               }
            }
         }
      }

      this.textBuffer.setLength(0);
   }

   public void write(String text) {
      this.finishStartTag();
      this.textBuffer.append(text);
      this.buffer.append(text);
   }

   public void writeInstruction(String text) {
      this.finishStartTag();
      ELText el = ELText.parse(text);
      if (el.isLiteral()) {
         this.addInstruction(new LiteralXMLInstruction(text));
      } else {
         this.addInstruction(new XMLInstruction(el));
      }

      this.buffer.append(text);
   }

   public void writeComment(String text) {
      this.finishStartTag();
      ELText el = ELText.parse(text);
      if (el.isLiteral()) {
         this.addInstruction(new LiteralCommentInstruction(text));
      } else {
         this.addInstruction(new CommentInstruction(el));
      }

      this.buffer.append("<!--" + text + "-->");
   }

   public void startTag(Tag tag) {
      this.finishStartTag();
      this.tags.push(tag);
      this.buffer.append('<');
      this.buffer.append(tag.getQName());
      this.addInstruction(new StartElementInstruction(tag.getQName()));
      TagAttribute[] attrs = tag.getAttributes().getAll();
      if (attrs.length > 0) {
         for(int i = 0; i < attrs.length; ++i) {
            String qname = attrs[i].getQName();
            String value = attrs[i].getValue();
            this.buffer.append(' ').append(qname).append("=\"").append(value).append("\"");
            ELText txt = ELText.parse(value);
            if (txt != null) {
               if (txt.isLiteral()) {
                  this.addInstruction(new LiteralAttributeInstruction(qname, txt.toString()));
               } else {
                  this.addInstruction(new AttributeInstruction(this.alias, qname, txt));
               }
            }
         }
      }

      this.startTagOpen = true;
   }

   private void finishStartTag() {
      if (this.tags.size() > 0 && this.startTagOpen) {
         this.buffer.append(">");
         this.startTagOpen = false;
      }

   }

   public void endTag() {
      Tag tag = (Tag)this.tags.pop();
      this.addInstruction(new EndElementInstruction(tag.getQName()));
      if (this.startTagOpen) {
         this.buffer.append("/>");
         this.startTagOpen = false;
      } else {
         this.buffer.append("</").append(tag.getQName()).append('>');
      }

   }

   public void addChild(CompilationUnit unit) {
      this.finishStartTag();
      this.flushBufferToConfig(true);
      this.children.add(unit);
   }

   protected void flushBufferToConfig(boolean child) {
      this.flushTextBuffer(child);
      int size = this.instructionBuffer.size();
      if (size > 0) {
         try {
            String s = this.buffer.toString();
            if (child) {
               s = trimRight(s);
            }

            ELText txt = ELText.parse(s);
            if (txt != null) {
               Instruction[] instructions = (Instruction[])((Instruction[])this.instructionBuffer.toArray(new Instruction[size]));
               this.children.add(new UIInstructionHandler(this.alias, this.id, instructions, txt));
               this.instructionBuffer.clear();
            }
         } catch (ELException var6) {
            if (this.tags.size() > 0) {
               throw new TagException((Tag)this.tags.peek(), var6.getMessage());
            }

            throw new ELException(this.alias + ": " + var6.getMessage(), var6.getCause());
         }
      }

      this.buffer.setLength(0);
   }

   public boolean isClosed() {
      return this.tags.empty();
   }

   private static String trimRight(String s) {
      for(int i = s.length() - 1; i >= 0; --i) {
         if (!Character.isWhitespace(s.charAt(i))) {
            return s;
         }
      }

      return "";
   }

   public String toString() {
      return "TextUnit[" + this.children.size() + "]";
   }
}
