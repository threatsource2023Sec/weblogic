package weblogic.diagnostics.harvester;

import com.bea.adaptive.harvester.Harvester;
import com.bea.adaptive.harvester.WatchedValues;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface WLDFHarvester extends Harvester {
   String[] getSupportedNamespaces();

   WatchedValues createWatchedValues(String var1);

   WatchedValues createWatchedValues(String var1, String var2, String var3);

   String getDefaultNamespace();

   void validateNamespace(String var1) throws InvalidHarvesterNamespaceException;

   String[][] getKnownHarvestableTypes(String var1, String var2) throws IOException;

   List getKnownHarvestableInstances(String var1, String var2, String var3) throws IOException;

   Set getHarvestedInstances(String var1, String var2);

   Set getHarvestedAttributes(String var1, String var2);

   void harvest(int var1);
}
