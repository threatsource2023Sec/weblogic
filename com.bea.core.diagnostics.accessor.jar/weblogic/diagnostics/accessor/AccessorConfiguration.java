package weblogic.diagnostics.accessor;

import java.util.Map;

public interface AccessorConfiguration {
   String getName();

   boolean isModifiableConfiguration();

   ColumnInfo[] getColumns();

   Map getAccessorParameters();
}
