package com.sun.faces.facelets.tag;

import com.sun.faces.util.Util;
import javax.faces.view.facelets.MetaRuleset;
import javax.faces.view.facelets.MetaTagHandler;
import javax.faces.view.facelets.TagConfig;

public abstract class MetaTagHandlerImpl extends MetaTagHandler {
   public MetaTagHandlerImpl(TagConfig config) {
      super(config);
   }

   protected MetaRuleset createMetaRuleset(Class type) {
      Util.notNull("type", type);
      return new MetaRulesetImpl(this.tag, type);
   }
}
