package weblogic.corba.iiop.cluster;

import java.util.HashMap;

public class SelectorFactory {
   public static final Selector getSelector(String name) {
      Selector selector = (Selector)SelectorFactory.SelectorMaker.SELECTOR_MAP.get(name);
      return (Selector)(selector != null ? selector : StickySelector.SELECTOR);
   }

   private static final HashMap getSelectorMap() {
      HashMap smap = new HashMap();
      smap.put("random", RandomSelector.SELECTOR);
      smap.put("round-robin", RoundRobinSelector.SELECTOR);
      smap.put("round-robin-affinity", ServerAffinitySelector.SELECTOR);
      smap.put("random-affinity", ServerAffinitySelector.SELECTOR);
      smap.put("server-affinity", ServerAffinitySelector.SELECTOR);
      smap.put("weight-based-affinity", ServerAffinitySelector.SELECTOR);
      smap.put("primary-secondary", PrimarySecondarySelector.SELECTOR);
      return smap;
   }

   private static final class SelectorMaker {
      private static final HashMap SELECTOR_MAP = SelectorFactory.getSelectorMap();
   }
}
