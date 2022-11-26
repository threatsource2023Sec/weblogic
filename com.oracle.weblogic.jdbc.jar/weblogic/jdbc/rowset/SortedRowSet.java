package weblogic.jdbc.rowset;

import java.util.Comparator;

/** @deprecated */
@Deprecated
public interface SortedRowSet {
   void setSorter(Comparator var1);

   Comparator getSorter();
}
