package weblogic.management.provider.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import weblogic.utils.collections.TreeMap;

public class ReorderableRoster {
   static final String ELEMENTS_OUT_OF_ORDER_MESSAGE = "Reorderable elements are not adjacent";
   private final ElementInfo elementInfo;

   public ReorderableRoster(ElementInfo elementInfo) {
      this.elementInfo = elementInfo;
   }

   public List reorder(boolean isForward, List currentList) {
      List result = new ArrayList();
      SortedMap reorderableElements = new TreeMap();
      boolean isProcessingReorderableElements = false;
      boolean isDoneProcessingReorderableElements = false;
      Iterator var7 = currentList.iterator();

      while(var7.hasNext()) {
         Object element = var7.next();
         if (this.elementInfo.isReorderable(element)) {
            if (isDoneProcessingReorderableElements) {
               throw new IllegalArgumentException("Reorderable elements are not adjacent");
            }

            isProcessingReorderableElements = true;
            int order = this.elementInfo.getOrder(element);
            if (!isForward) {
               order = -order;
            }

            reorderableElements.put(order, element);
         } else {
            if (isProcessingReorderableElements) {
               isProcessingReorderableElements = false;
               isDoneProcessingReorderableElements = true;
               result.addAll(reorderableElements.values());
            }

            result.add(element);
         }
      }

      if (!isDoneProcessingReorderableElements) {
         result.addAll(reorderableElements.values());
      }

      return result;
   }

   public interface ElementInfo {
      boolean isReorderable(Object var1);

      int getOrder(Object var1);
   }
}
