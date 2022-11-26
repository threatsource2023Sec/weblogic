package org.stringtemplate.v4.misc;

import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ModelAdaptor;
import org.stringtemplate.v4.ST;

public class STModelAdaptor implements ModelAdaptor {
   public Object getProperty(Interpreter interp, ST self, Object o, Object property, String propertyName) throws STNoSuchPropertyException {
      ST st = (ST)o;
      return st.getAttribute(propertyName);
   }
}
