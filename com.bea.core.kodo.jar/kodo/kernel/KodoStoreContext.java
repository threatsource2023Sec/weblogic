package kodo.kernel;

import com.solarmetric.profile.ProfilingAgentProvider;
import com.solarmetric.profile.ProfilingEnvironment;
import org.apache.openjpa.kernel.StoreContext;

public interface KodoStoreContext extends StoreContext, ProfilingEnvironment, ProfilingAgentProvider {
}
