package kodo.jdbc.sql;

import com.solarmetric.profile.MethodEnterEvent;
import com.solarmetric.profile.MethodExitEvent;
import com.solarmetric.profile.MethodInfoImpl;
import com.solarmetric.profile.ProfilingAgent;
import com.solarmetric.profile.ProfilingAgentProvider;
import com.solarmetric.profile.ProfilingCapable;
import com.solarmetric.profile.ProfilingEnvironment;
import java.sql.SQLException;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.SelectImpl;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.jdbc.SQLFormatter;

public class KodoSelectImpl extends SelectImpl implements ProfilingCapable {
   public KodoSelectImpl(JDBCConfiguration conf) {
      super(conf);
   }

   protected Result execute(StoreContext ctx, JDBCStore store, JDBCFetchConfiguration fetch, int lockLevel) throws SQLException {
      MethodInfoImpl var5 = null;
      ProfilingAgent var6 = null;
      if (ctx instanceof ProfilingAgentProvider) {
         var6 = ((ProfilingAgentProvider)ctx).getProfilingAgent();
         if (var6 != null) {
            String var7 = "" + (new SQLFormatter()).prettyPrint(this);
            var5 = new MethodInfoImpl("Select.select()", var7);
            var5.setCategory("SQL");
            var6.handleEvent(new MethodEnterEvent((ProfilingEnvironment)ctx, var5));
         }
      }

      Result var10;
      try {
         var10 = super.execute(ctx, store, fetch, lockLevel);
      } finally {
         if (var6 != null) {
            var6.handleEvent(new MethodExitEvent((ProfilingEnvironment)ctx, var5));
         }

      }

      return var10;
   }
}
