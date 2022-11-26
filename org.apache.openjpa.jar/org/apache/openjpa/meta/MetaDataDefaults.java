package org.apache.openjpa.meta;

import java.lang.reflect.Member;
import org.apache.openjpa.event.CallbackModes;

public interface MetaDataDefaults extends CallbackModes {
   int getDefaultAccessType();

   int getDefaultIdentityType();

   int getCallbackMode();

   boolean getCallbacksBeforeListeners(int var1);

   boolean isDeclaredInterfacePersistent();

   boolean isDataStoreObjectIdFieldUnwrapped();

   void setIgnoreNonPersistent(boolean var1);

   void populate(ClassMetaData var1, int var2);

   Member getBackingMember(FieldMetaData var1);

   Class getUnimplementedExceptionType();
}
