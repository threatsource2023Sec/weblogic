package org.glassfish.hk2.runlevel;

public interface ChangeableRunLevelFuture extends RunLevelFuture {
   int changeProposedLevel(int var1) throws IllegalStateException;
}
