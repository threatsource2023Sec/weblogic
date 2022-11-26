package kodo.jdbc.kernel;

import com.solarmetric.profile.MethodEnterEvent;
import com.solarmetric.profile.MethodExitEvent;
import com.solarmetric.profile.MethodInfoImpl;
import com.solarmetric.profile.ProfilingAgent;
import com.solarmetric.profile.ProfilingAgentProvider;
import com.solarmetric.profile.ProfilingCapable;
import com.solarmetric.profile.ProfilingEnvironment;
import java.sql.SQLException;
import kodo.profile.StateManagerFormatter;
import org.apache.openjpa.jdbc.kernel.JDBCStoreManager;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.StoreContext;

public class KodoJDBCStoreManager extends JDBCStoreManager implements ProfilingCapable {
   private StoreContext _ctx;

   public void setContext(StoreContext ctx) {
      this._ctx = ctx;
      super.setContext(ctx);
   }

   protected JDBCStoreManager.RefCountConnection connectInternal() throws SQLException {
      MethodInfoImpl var1 = null;
      ProfilingAgent var2 = null;
      if (this._ctx instanceof ProfilingAgentProvider) {
         var2 = ((ProfilingAgentProvider)this._ctx).getProfilingAgent();
         if (var2 != null) {
            var1 = new MethodInfoImpl("Store.connect()", "");
            var2.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this._ctx, var1));
         }
      }

      JDBCStoreManager.RefCountConnection var5;
      try {
         var5 = super.connectInternal();
      } finally {
         if (var2 != null) {
            var2.handleEvent(new MethodExitEvent((ProfilingEnvironment)this._ctx, var1));
         }

      }

      return var5;
   }

   public boolean assignObjectId(OpenJPAStateManager sm, boolean preFlush) {
      MethodInfoImpl var3 = null;
      ProfilingAgent var4 = null;
      if (this._ctx instanceof ProfilingAgentProvider) {
         var4 = ((ProfilingAgentProvider)this._ctx).getProfilingAgent();
         if (var4 != null) {
            String var5 = "Class: " + (new StateManagerFormatter()).getClassName(sm);
            var3 = new MethodInfoImpl("Store.assignObjectId()", var5);
            var4.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this._ctx, var3));
         }
      }

      boolean var8;
      try {
         var8 = super.assignObjectId(sm, preFlush);
      } finally {
         if (var4 != null) {
            var4.handleEvent(new MethodExitEvent((ProfilingEnvironment)this._ctx, var3));
         }

      }

      return var8;
   }
}
