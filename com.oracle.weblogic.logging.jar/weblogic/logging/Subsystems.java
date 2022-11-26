package weblogic.logging;

import java.util.HashSet;
import java.util.Set;
import weblogic.i18ntools.L10nLookup;

public class Subsystems {
   private static Set allSubsystems = new HashSet();
   private static boolean l10nSubsystemsLoaded = false;

   public static synchronized void add(String name) {
      allSubsystems.add(name);
   }

   public static synchronized Set getAll() {
      if (!l10nSubsystemsLoaded) {
         allSubsystems.addAll(L10nLookup.getL10n().getSubSystems());
         l10nSubsystemsLoaded = true;
      }

      return allSubsystems;
   }

   static {
      allSubsystems.add("Default");
      allSubsystems.add("Logging");
      allSubsystems.add("NT Performance Pack");
   }
}
