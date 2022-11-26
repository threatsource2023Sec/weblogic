package weblogic.diagnostics.watch;

import java.util.HashSet;
import java.util.Set;
import weblogic.diagnostics.accessor.ColumnInfo;
import weblogic.diagnostics.archive.ArchiveConstants;
import weblogic.diagnostics.query.UnknownVariableException;
import weblogic.diagnostics.query.VariableIndexResolver;

final class FixedSetVariableValidator implements VariableIndexResolver {
   private Set variableSet = new HashSet();
   private int indexCounter = 0;

   public FixedSetVariableValidator(int archiveType) {
      ColumnInfo[] cols = ArchiveConstants.getColumns(archiveType);

      for(int i = 0; i < cols.length; ++i) {
         this.variableSet.add(cols[i].getColumnName());
      }

   }

   public int getVariableIndex(String varName) throws UnknownVariableException {
      if (!this.variableSet.contains(varName)) {
         throw new UnknownVariableException(varName);
      } else {
         return this.indexCounter++;
      }
   }
}
