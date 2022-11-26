package kodo.profile;

import org.apache.openjpa.meta.ClassMetaData;

public class ClassMetaDataFormatter {
   public Object getClassName(Object input) {
      return ((ClassMetaData)input).getDescribedType().getName();
   }
}
