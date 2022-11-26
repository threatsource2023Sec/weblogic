package weblogic.corba.rmic;

public final class IDLOptions {
   static boolean m_idlStrict = false;
   static boolean m_factories = false;
   static boolean m_noValueTypes = false;
   static boolean m_noAbstract = false;
   static boolean m_compatibility = false;
   static boolean m_visibroker = false;
   static boolean m_orbix = false;

   private IDLOptions() {
   }

   public static void setIDLStrict(boolean idlStrict) {
      m_idlStrict = idlStrict;
   }

   public static boolean getIDLStrict() {
      return m_idlStrict;
   }

   public static void setFactories(boolean factories) {
      m_factories = factories;
   }

   public static boolean getFactories() {
      return m_factories;
   }

   public static boolean getNoValueTypes() {
      return m_noValueTypes;
   }

   public static void setNoValueTypes(boolean noValueTypes) {
      m_noValueTypes = noValueTypes;
   }

   public static boolean getNoAbstract() {
      return m_noAbstract;
   }

   public static void setNoAbstract(boolean noAbstract) {
      m_noAbstract = noAbstract;
   }

   public static boolean getVisibroker() {
      return m_visibroker;
   }

   public static void setVisibroker(boolean vtil) {
      m_visibroker = vtil;
   }

   public static boolean getOrbix() {
      return m_orbix;
   }

   public static void setOrbix(boolean vtil) {
      m_orbix = vtil;
   }

   public static boolean getCompatibility() {
      return m_compatibility;
   }

   public static void setCompatibility(boolean compatibility) {
      m_compatibility = compatibility;
   }
}
