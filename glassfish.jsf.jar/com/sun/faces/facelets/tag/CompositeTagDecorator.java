package com.sun.faces.facelets.tag;

import com.sun.faces.util.Util;
import javax.faces.view.facelets.Tag;
import javax.faces.view.facelets.TagDecorator;

public final class CompositeTagDecorator implements TagDecorator {
   private final TagDecorator[] decorators;
   private final DefaultTagDecorator defaultTagDecorator;

   public CompositeTagDecorator(TagDecorator[] decorators) {
      Util.notNull("decorators", decorators);
      this.decorators = decorators;
      this.defaultTagDecorator = new DefaultTagDecorator();
   }

   public Tag decorate(Tag tag) {
      Tag noJsfAttributes = this.defaultTagDecorator.decorate(tag);
      if (noJsfAttributes != null) {
         tag = noJsfAttributes;
      }

      Tag t = null;

      for(int i = 0; i < this.decorators.length; ++i) {
         t = this.decorators[i].decorate(tag);
         if (t != null) {
            return t;
         }
      }

      return tag;
   }
}
