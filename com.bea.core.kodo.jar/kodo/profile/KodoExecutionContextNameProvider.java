package kodo.profile;

import com.solarmetric.profile.ExecutionContextNameProvider;

public interface KodoExecutionContextNameProvider extends ExecutionContextNameProvider {
   String PT_STORECTX_OPEN = "PT_STORECTX_OPEN";
   String PT_TRANS_BEGIN = "PT_TRANS_BEGIN";
}
