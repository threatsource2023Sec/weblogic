package org.omg.CosTransactions;

public interface RecoveryCoordinatorOperations {
   Status replay_completion(Resource var1) throws NotPrepared;
}
