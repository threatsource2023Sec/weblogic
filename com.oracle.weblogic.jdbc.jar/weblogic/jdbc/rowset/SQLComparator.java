package weblogic.jdbc.rowset;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.StringTokenizer;

/** @deprecated */
@Deprecated
public class SQLComparator implements Comparator, Serializable {
   private static final long serialVersionUID = 7517197886917898522L;
   ArrayList cols = new ArrayList();

   public SQLComparator(String order) {
      if (order != null) {
         StringTokenizer st = new StringTokenizer(order, ",");

         while(st.hasMoreTokens()) {
            this.cols.add(st.nextToken().trim());
         }

      }
   }

   public final int compare(Object a, Object b) {
      for(int i = 0; i < this.cols.size(); ++i) {
         String colName = (String)this.cols.get(i);
         Object val1 = ((Map)a).get(colName);
         Object val2 = ((Map)b).get(colName);
         int ret = false;

         try {
            if (val1 == null && val2 == null) {
               return 0;
            }

            if (val1 == null) {
               return -1;
            }

            if (val2 == null) {
               return 1;
            }

            Class c1 = val1.getClass();
            Class c2 = val2.getClass();
            int ret;
            if (c1 == c2 && Comparable.class.isAssignableFrom(c1)) {
               ret = ((Comparable)val1).compareTo((Comparable)val2);
            } else if (!Number.class.isAssignableFrom(c1) && !Number.class.isAssignableFrom(c2)) {
               if (!Date.class.isAssignableFrom(c1) && !Date.class.isAssignableFrom(c2) && !Time.class.isAssignableFrom(c1) && !Time.class.isAssignableFrom(c2)) {
                  if (!Boolean.class.isAssignableFrom(c1) && !Boolean.class.isAssignableFrom(c2)) {
                     throw new RuntimeException(c1 + " can not be compared with " + c2);
                  }

                  if (String.class.isAssignableFrom(c1)) {
                     val1 = new Boolean(val1.toString().trim());
                  }

                  if (String.class.isAssignableFrom(c2)) {
                     val2 = new Boolean(val2.toString().trim());
                  }

                  ret = ((Comparable)val1).compareTo((Comparable)val2);
               } else {
                  if (String.class.isAssignableFrom(c1)) {
                     val1 = Date.valueOf((String)val1);
                  }

                  if (String.class.isAssignableFrom(c2)) {
                     val2 = Date.valueOf((String)val2);
                  }

                  ret = ((Comparable)val1).compareTo((Comparable)val2);
               }
            } else {
               if (Number.class.isAssignableFrom(c1) || String.class.isAssignableFrom(c1)) {
                  val1 = new BigDecimal(val1.toString().trim());
               }

               if (Number.class.isAssignableFrom(c2) || String.class.isAssignableFrom(c2)) {
                  val2 = new BigDecimal(val2.toString().trim());
               }

               ret = ((Comparable)val1).compareTo((Comparable)val2);
            }

            if (ret != 0) {
               return ret;
            }
         } catch (Throwable var10) {
            throw new RuntimeException(val1.getClass() + " can not be compared with " + val2.getClass());
         }
      }

      return 0;
   }
}
