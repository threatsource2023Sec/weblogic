package kodo.datacache;

import com.gemstone.gemfire.DataSerializer;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.openjpa.datacache.QueryResult;
import serp.util.Numbers;

class GemFireQueryResultSerializer extends DataSerializer {
   public static final byte ID = 42;

   public GemFireQueryResultSerializer() {
   }

   public Class[] getSupportedClasses() {
      return new Class[]{QueryResult.class};
   }

   public boolean toData(Object o, DataOutput out) throws IOException {
      if (o instanceof QueryResult) {
         QueryResult result = (QueryResult)o;
         DataSerializer.writeArrayList(result, out);
         DataSerializer.writeLong(Numbers.valueOf(result.getTimeoutTime()), out);
         return true;
      } else {
         return false;
      }
   }

   public Object fromData(DataInput in) throws IOException, ClassNotFoundException {
      ArrayList oids = DataSerializer.readArrayList(in);
      Long ex = DataSerializer.readLong(in);
      QueryResult result = new QueryResult(oids, ex);
      return result;
   }
}
