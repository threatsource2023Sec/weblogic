package org.apache.taglibs.standard.tag.rt.core;

import org.apache.taglibs.standard.tag.common.core.SetSupport;

public class SetTag extends SetSupport {
   public void setValue(Object value) {
      this.value = value;
      this.valueSpecified = true;
   }

   public void setTarget(Object target) {
      this.target = target;
   }

   public void setProperty(String property) {
      this.property = property;
   }
}
