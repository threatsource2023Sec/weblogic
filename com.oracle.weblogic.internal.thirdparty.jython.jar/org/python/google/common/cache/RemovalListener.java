package org.python.google.common.cache;

import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
public interface RemovalListener {
   void onRemoval(RemovalNotification var1);
}
