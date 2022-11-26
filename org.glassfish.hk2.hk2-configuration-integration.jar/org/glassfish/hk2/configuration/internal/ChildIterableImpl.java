package org.glassfish.hk2.configuration.internal;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.configuration.api.ChildIterable;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;

public class ChildIterableImpl implements ChildIterable {
   private final ServiceLocator locator;
   private final Type childType;
   private final String prefix;
   private final String separator;
   private final ChildFilter baseFilter;

   ChildIterableImpl(ServiceLocator locator, Type childType, String prefix, String separator) {
      this.locator = locator;
      this.childType = childType;
      this.prefix = prefix;
      this.separator = separator;
      this.baseFilter = new ChildFilter(childType, prefix);
   }

   public Iterator iterator() {
      List matches = this.locator.getAllServices(this.baseFilter);
      return matches.iterator();
   }

   public Object byKey(String key) {
      if (key == null) {
         throw new IllegalArgumentException();
      } else {
         ChildFilter filter = new ChildFilter(this.childType, this.prefix, this.separator + key);
         ActiveDescriptor result = this.locator.getBestDescriptor(filter);
         if (result == null) {
            return null;
         } else {
            ServiceHandle handle = this.locator.getServiceHandle(result);
            return handle.getService();
         }
      }
   }

   public Iterable handleIterator() {
      List matches = this.locator.getAllServiceHandles(this.baseFilter);
      final List tMatches = (List)ReflectionHelper.cast(matches);
      return new Iterable() {
         public Iterator iterator() {
            return tMatches.iterator();
         }
      };
   }
}
