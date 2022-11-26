package com.bea.common.store.service;

import java.util.Collection;

public interface RemoteCommitEvent {
   Collection getDeletedObjectIds();

   Collection getAddedObjectIds();

   Collection getUpdatedObjectIds();
}
