package org.apache.xmlbeans.impl.jam.internal.elements;

import org.apache.xmlbeans.impl.jam.JComment;
import org.apache.xmlbeans.impl.jam.mutable.MComment;
import org.apache.xmlbeans.impl.jam.visitor.JVisitor;
import org.apache.xmlbeans.impl.jam.visitor.MVisitor;

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
