package weblogic.diagnostics.harvester.internal;

import com.bea.adaptive.harvester.Harvester;
import java.util.Iterator;
import java.util.List;

interface DelegateHarvesterManager {
   void addDelegateHarvester(DelegateHarvesterControl var1);

   void removeDelegateHarvesterByName(String var1);

   void removeAll();

   Harvester getDefaultDelegate();

   Iterator iterator();

   Iterator harvesterIterator();

   Iterator activeOnlyIterator();

   Iterator activatingIterator();

   Iterator activatingIterator(DelegateHarvesterControl.ActivationPolicy var1);

   Iterator activatingIterator(List var1, DelegateHarvesterControl.ActivationPolicy var2);

   int getConfiguredHarvestersCount();

   int getActiveHarvestersCount();

   Harvester getHarvesterByName(String var1);
}
