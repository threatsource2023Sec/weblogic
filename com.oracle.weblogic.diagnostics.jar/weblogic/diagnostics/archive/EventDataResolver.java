package weblogic.diagnostics.archive;

import java.util.Map;

public class EventDataResolver extends DataResolver {
   private static Map indexMap = DataResolver.computeIndex(ArchiveConstants.getColumns(1));

   public EventDataResolver() {
      super(indexMap);
   }
}
