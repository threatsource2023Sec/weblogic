package org.apache.velocity.runtime.directive;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.runtime.resource.Resource;

public abstract class InputBase extends Directive {
   protected String getInputEncoding(InternalContextAdapter context) {
      Resource current = context.getCurrentResource();
      return current != null ? current.getEncoding() : (String)super.rsvc.getProperty("input.encoding");
   }
}
