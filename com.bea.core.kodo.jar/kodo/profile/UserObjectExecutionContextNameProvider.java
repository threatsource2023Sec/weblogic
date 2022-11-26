package kodo.profile;

import com.solarmetric.profile.ExecutionContextNameProvider;
import org.apache.openjpa.kernel.Broker;

public class UserObjectExecutionContextNameProvider implements KodoExecutionContextNameProvider {
   public String getCreationPoint(Object creationPtType, Broker broker) {
      Object userObject = broker.getUserObject(ExecutionContextNameProvider.class);
      return userObject == null ? "null" : userObject.toString();
   }
}
