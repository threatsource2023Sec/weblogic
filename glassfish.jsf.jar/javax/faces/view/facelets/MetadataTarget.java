package javax.faces.view.facelets;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public abstract class MetadataTarget {
   public abstract PropertyDescriptor getProperty(String var1);

   public abstract boolean isTargetInstanceOf(Class var1);

   public abstract Class getTargetClass();

   public abstract Class getPropertyType(String var1);

   public abstract Method getWriteMethod(String var1);

   public abstract Method getReadMethod(String var1);
}
