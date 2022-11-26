package javax.resource.cci;

import java.io.Serializable;

public interface Record extends Cloneable, Serializable {
   String getRecordName();

   void setRecordName(String var1);

   void setRecordShortDescription(String var1);

   String getRecordShortDescription();

   boolean equals(Object var1);

   int hashCode();

   Object clone() throws CloneNotSupportedException;
}
