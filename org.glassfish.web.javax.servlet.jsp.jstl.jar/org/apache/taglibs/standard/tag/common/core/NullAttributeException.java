package org.apache.taglibs.standard.tag.common.core;

import javax.servlet.jsp.JspTagException;
import org.apache.taglibs.standard.resources.Resources;

public class NullAttributeException extends JspTagException {
   public NullAttributeException(String tag, String att) {
      super(Resources.getMessage("TAG_NULL_ATTRIBUTE", att, tag));
   }
}
