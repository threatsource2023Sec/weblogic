package com.bea.util.jam.internal.elements;

import com.bea.util.jam.JTag;
import com.bea.util.jam.mutable.MTag;
import com.bea.util.jam.visitor.JVisitor;
import com.bea.util.jam.visitor.MVisitor;
import java.util.Properties;

public class TagImpl extends ElementImpl implements MTag {
   private String mText;

   public TagImpl(ElementImpl parent) {
      super(parent);
   }

   public String getName() {
      return super.getSimpleName();
   }

   public String getText() {
      return this.mText;
   }

   public Properties getProperties_lineDelimited() {
      Properties out = new Properties();
      LinebreakTagPropertyParser.getInstance().parse(out, this.mText);
      return out;
   }

   public Properties getProperties_whitespaceDelimited() {
      Properties out = new Properties();
      WhitespaceTagPropertyParser.getInstance().parse(out, this.mText, this.getLogger());
      return out;
   }

   public void setName(String name) {
      super.setSimpleName(name);
   }

   public void setValue(String val) {
      this.mText = val;
   }

   public void accept(MVisitor visitor) {
      visitor.visit((MTag)this);
   }

   public String getQualifiedName() {
      return this.getSimpleName();
   }

   public void accept(JVisitor visitor) {
      visitor.visit((JTag)this);
   }
}
