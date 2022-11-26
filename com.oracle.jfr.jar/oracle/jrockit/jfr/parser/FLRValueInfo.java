package oracle.jrockit.jfr.parser;

import com.oracle.jrockit.jfr.ContentType;
import com.oracle.jrockit.jfr.DataType;
import com.oracle.jrockit.jfr.Transition;

public interface FLRValueInfo {
   String getId();

   String getName();

   String getDescription();

   DataType getDataType();

   ContentType getContentType();

   Transition getTransition();
}
