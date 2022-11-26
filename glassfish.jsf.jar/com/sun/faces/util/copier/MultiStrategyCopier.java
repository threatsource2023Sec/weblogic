package com.sun.faces.util.copier;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MultiStrategyCopier implements Copier {
   private static final List COPIERS = Arrays.asList(new SerializationCopier(), new CloneCopier(), new CopyCtorCopier(), new NewInstanceCopier());

   public Object copy(Object object) {
      Iterator var2 = COPIERS.iterator();

      while(var2.hasNext()) {
         Copier copier = (Copier)var2.next();

         try {
            return copier.copy(object);
         } catch (Exception var5) {
         }
      }

      throw new IllegalStateException("Can't copy object of type " + object.getClass() + ". No copier appeared to be capable of copying it.");
   }
}
