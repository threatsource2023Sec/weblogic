package weblogic.management.runtime;

/** @deprecated */
@Deprecated
public interface ServletSessionRuntimeMBean extends RuntimeMBean {
   /** @deprecated */
   @Deprecated
   void invalidate();

   /** @deprecated */
   @Deprecated
   long getTimeLastAccessed();

   /** @deprecated */
   @Deprecated
   long getMaxInactiveInterval();

   /** @deprecated */
   @Deprecated
   String getMainAttribute();
}
