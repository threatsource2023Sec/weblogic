package com.bea.xml_.impl.jam.annotation;

import com.bea.xml_.impl.jam.JClass;
import com.bea.xml_.impl.jam.internal.elements.ElementContext;
import com.bea.xml_.impl.jam.mutable.MAnnotatedElement;
import com.bea.xml_.impl.jam.mutable.MAnnotation;
import com.bea.xml_.impl.jam.mutable.MSourcePosition;
import com.bea.xml_.impl.jam.provider.JamLogger;
import com.bea.xml_.impl.jam.provider.JamServiceContext;
import com.sun.javadoc.SourcePosition;
import com.sun.javadoc.Tag;

public abstract class JavadocTagParser {
   private JamServiceContext mContext = null;
   private boolean mAddSingleValueMembers = false;

   public void setAddSingleValueMembers(boolean b) {
      this.mAddSingleValueMembers = b;
   }

   public void init(JamServiceContext ctx) {
      if (ctx == null) {
         throw new IllegalArgumentException("null logger");
      } else if (this.mContext != null) {
         throw new IllegalStateException("JavadocTagParser.init() called twice");
      } else {
         this.mContext = ctx;
      }
   }

   public abstract void parse(MAnnotatedElement var1, Tag var2);

   protected MAnnotation[] createAnnotations(MAnnotatedElement target, Tag tag) {
      String tagName = tag.name().trim().substring(1);
      MAnnotation current = target.getMutableAnnotation(tagName);
      if (current == null) {
         current = target.findOrCreateAnnotation(tagName);
         this.setPosition(current, tag);
      }

      MAnnotation literal = target.addLiteralAnnotation(tagName);
      this.setPosition(literal, tag);
      MAnnotation[] out = new MAnnotation[]{literal, current};
      if (this.mAddSingleValueMembers) {
         this.setSingleValueText(out, tag);
      }

      return out;
   }

   protected void setValue(MAnnotation[] anns, String memberName, String value) {
      value = value.trim();
      memberName = memberName.trim();

      for(int i = 0; i < anns.length; ++i) {
         if (anns[i].getValue(memberName) == null) {
            anns[i].setSimpleValue(memberName, value, this.getStringType());
         }
      }

   }

   protected JamLogger getLogger() {
      return this.mContext.getLogger();
   }

   protected JClass getStringType() {
      return ((ElementContext)this.mContext).getClassLoader().loadClass("java.lang.String");
   }

   protected void setSingleValueText(MAnnotation[] targets, Tag tag) {
      String tagText = tag.text();

      for(int i = 0; i < targets.length; ++i) {
         targets[i].setSimpleValue("value", tagText, this.getStringType());
      }

   }

   private void setPosition(MAnnotation target, Tag tag) {
      SourcePosition pos = tag.position();
      if (pos != null) {
         MSourcePosition mpos = target.createSourcePosition();
         mpos.setLine(pos.line());
         mpos.setColumn(pos.column());
         if (pos.file() != null) {
            mpos.setSourceURI(pos.file().toURI());
         }
      }

   }
}
