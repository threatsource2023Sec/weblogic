package kodo.profile;

import org.apache.openjpa.kernel.Query;

public class QueryFormatter {
   public Object printFilter(Object queryObject) {
      if (!(queryObject instanceof Query)) {
         return "";
      } else {
         Query query = (Query)queryObject;
         StringBuffer buf = new StringBuffer();
         buf.append("Class: ").append(query.getCandidateType().getName());
         buf.append("\n");
         buf.append("Filter: ").append(query.getQueryString());
         return buf.toString();
      }
   }
}
