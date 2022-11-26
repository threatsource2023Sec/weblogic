package org.glassfish.hk2.configuration.hub.api;

import java.util.Set;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.TwoPhaseResource;

public interface WriteableBeanDatabase extends BeanDatabase {
   Set getAllWriteableTypes();

   WriteableType addType(String var1);

   Type removeType(String var1);

   WriteableType getWriteableType(String var1);

   WriteableType findOrAddWriteableType(String var1);

   Object getCommitMessage();

   void setCommitMessage(Object var1);

   TwoPhaseResource getTwoPhaseResource();

   void commit() throws IllegalStateException, MultiException;

   void commit(Object var1) throws IllegalStateException, MultiException;
}
