package javax.faces.component;

import javax.faces.convert.Converter;

public interface ValueHolder {
   Object getLocalValue();

   Object getValue();

   void setValue(Object var1);

   Converter getConverter();

   void setConverter(Converter var1);
}
