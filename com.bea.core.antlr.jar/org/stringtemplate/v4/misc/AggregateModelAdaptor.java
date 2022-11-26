package org.stringtemplate.v4.misc;

import java.util.Map;
import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ST;

public class AggregateModelAdaptor extends MapModelAdaptor {
   public Object getProperty(Interpreter interp, ST self, Object o, Object property, String propertyName) throws STNoSuchPropertyException {
      Map map = ((Aggregate)o).properties;
      return super.getProperty(interp, self, map, property, propertyName);
   }
}
