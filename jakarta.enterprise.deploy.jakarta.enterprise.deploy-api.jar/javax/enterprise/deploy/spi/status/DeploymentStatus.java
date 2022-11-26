package javax.enterprise.deploy.spi.status;

import javax.enterprise.deploy.shared.ActionType;
import javax.enterprise.deploy.shared.CommandType;
import javax.enterprise.deploy.shared.StateType;

public interface DeploymentStatus {
   StateType getState();

   CommandType getCommand();

   ActionType getAction();

   String getMessage();

   boolean isCompleted();

   boolean isFailed();

   boolean isRunning();
}
