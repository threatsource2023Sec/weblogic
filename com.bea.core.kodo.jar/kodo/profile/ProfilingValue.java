package kodo.profile;

import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.lib.conf.PluginValue;

public class ProfilingValue extends PluginValue {
   public static final String KEY = "Profiling";

   public ProfilingValue() {
      super("Profiling", true);
      String[] aliases = new String[]{"none", "kodo.profile.NoneProfiling", "local", "kodo.profile.LocalProfiling", "export", "kodo.profile.ExportProfiling", "gui", "kodo.profile.GUIProfiling"};
      this.setAliases(aliases);
      this.setDefault(aliases[0]);
      this.setString(aliases[0]);
      this.setScope(this.getClass());
   }

   public static ProfilingValue getInstance(OpenJPAConfiguration conf) {
      return (ProfilingValue)conf.getValue("Profiling");
   }

   public static Profiling getProfiling(OpenJPAConfiguration conf) {
      ProfilingValue value = getInstance(conf);
      if (value.get() == null) {
         value.instantiate(Profiling.class, conf);
      }

      return (Profiling)value.get();
   }
}
