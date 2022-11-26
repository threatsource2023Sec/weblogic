package org.python.netty.handler.codec.serialization;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;

final class WeakReferenceMap extends ReferenceMap {
   WeakReferenceMap(Map delegate) {
      super(delegate);
   }

   Reference fold(Object value) {
      return new WeakReference(value);
   }
}
