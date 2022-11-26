package org.apache.openjpa.event;

public interface LifecycleListener extends PersistListener, LoadListener, StoreListener, ClearListener, DeleteListener, DirtyListener, DetachListener, AttachListener {
}
