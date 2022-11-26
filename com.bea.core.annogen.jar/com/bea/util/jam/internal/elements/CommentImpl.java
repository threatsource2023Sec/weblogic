package com.bea.util.jam.internal.elements;

import com.bea.util.jam.JComment;
import com.bea.util.jam.mutable.MComment;
import com.bea.util.jam.visitor.JVisitor;
import com.bea.util.jam.visitor.MVisitor;

public final class CommentImpl extends ElementImpl implements MComment {
   private String mText = null;

   CommentImpl(ElementImpl parent) {
      super(parent);
   }

   public void setText(String text) {
      this.mText = text;
   }

   public String getText() {
      return this.mText == null ? "" : this.mText;
   }

   public void accept(MVisitor visitor) {
      visitor.visit((MComment)this);
   }

   public void accept(JVisitor visitor) {
      visitor.visit((JComment)this);
   }

   public String getQualifiedName() {
      return this.getParent().getQualifiedName() + ".{comment}";
   }
}
