package org.omg.CosTransactions;

public interface ControlOperations {
   Terminator get_terminator() throws Unavailable;

   Coordinator get_coordinator() throws Unavailable;
}
