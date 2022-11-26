package kodo.kernel;

import com.solarmetric.profile.ExecutionContextNameProvider;
import kodo.profile.KodoExecutionContextNameProviderImpl;
import kodo.profile.TransactionNameExecutionContextNameProvider;
import kodo.profile.UserObjectExecutionContextNameProvider;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.lib.conf.PluginValue;

public class ExecutionContextNameProviderValue extends PluginValue {
   public static final String KEY = "ExecutionContextNameProvider";

   public ExecutionContextNameProviderValue() {
      super("ExecutionContextNameProvider", true);
      String[] aliases = new String[]{"stack", KodoExecutionContextNameProviderImpl.class.getName(), "user-object", UserObjectExecutionContextNameProvider.class.getName(), "transaction-name", TransactionNameExecutionContextNameProvider.class.getName()};
      this.setAliases(aliases);
      this.setDefault(aliases[0]);
      this.setString(aliases[0]);
      this.setScope(this.getClass());
   }

   public static ExecutionContextNameProviderValue getInstance(OpenJPAConfiguration conf) {
      return (ExecutionContextNameProviderValue)conf.getValue("ExecutionContextNameProvider");
   }

   public static ExecutionContextNameProvider getExecutionContextNameProvider(OpenJPAConfiguration conf) {
      ExecutionContextNameProviderValue value = getInstance(conf);
      if (value.get() == null) {
         value.instantiate(ExecutionContextNameProvider.class, conf);
      }

      return (ExecutionContextNameProvider)value.get();
   }
}
