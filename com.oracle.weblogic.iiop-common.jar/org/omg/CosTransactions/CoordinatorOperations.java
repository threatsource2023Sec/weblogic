package org.omg.CosTransactions;

public interface CoordinatorOperations {
   Status get_status();

   Status get_parent_status();

   Status get_top_level_status();

   boolean is_same_transaction(Coordinator var1);

   boolean is_related_transaction(Coordinator var1);

   boolean is_ancestor_transaction(Coordinator var1);

   boolean is_descendant_transaction(Coordinator var1);

   boolean is_top_level_transaction();

   int hash_transaction();

   int hash_top_level_tran();

   RecoveryCoordinator register_resource(Resource var1) throws Inactive;

   void register_synchronization(Synchronization var1) throws Inactive, SynchronizationUnavailable;

   void register_subtran_aware(SubtransactionAwareResource var1) throws Inactive, NotSubtransaction;

   void rollback_only() throws Inactive;

   String get_transaction_name();

   Control create_subtransaction() throws SubtransactionsUnavailable, Inactive;

   PropagationContext get_txcontext() throws Unavailable;
}
