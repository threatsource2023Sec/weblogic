package org.apache.velocity.context;

public interface InternalWrapperContext {
   Context getInternalUserContext();

   InternalContextAdapter getBaseContext();
}
