package weblogic.jdbc.common.internal;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.workarea.NoWorkContextException;
import weblogic.workarea.PrimitiveContextFactory;
import weblogic.workarea.PrimitiveWorkContext;
import weblogic.workarea.PropertyReadOnlyException;
import weblogic.workarea.WorkContext;
import weblogic.workarea.WorkContextHelper;
import weblogic.workarea.WorkContextMap;

public class SessionAffinityContextHelper extends AbstractAffinityContextHelper {
   private WorkContextMap wcMap = null;

   SessionAffinityContextHelper() {
      this.wcMap = WorkContextHelper.getWorkContextHelper().getWorkContextMap();
   }

   public String[] getKeys() {
      List keylist = new ArrayList();
      Iterator keyIt = this.wcMap.keys();

      while(keyIt.hasNext()) {
         String key = (String)keyIt.next();
         if (key.startsWith("weblogic.jdbc.affinity.")) {
            keylist.add(key);
         }
      }

      if (keylist.size() == 0) {
         return null;
      } else {
         return (String[])keylist.toArray(new String[keylist.size()]);
      }
   }

   public boolean isApplicationContextAvailable() {
      return true;
   }

   public Object getAffinityContext(String key) {
      if (this.wcMap.getPropagationMode(key) != 130) {
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug("getConnectionAffinityContext() invalid work context propagation mode, returning null");
         }

         return null;
      } else {
         WorkContext awc = this.wcMap.get(key);
         Object context = null;
         if (awc != null && awc instanceof PrimitiveWorkContext) {
            context = ((PrimitiveWorkContext)awc).get();
         }

         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug("getConnectionAffinityContext() returns " + context);
         }

         return context;
      }
   }

   public boolean setAffinityContext(String key, Object context) {
      if (context == null) {
         try {
            this.wcMap.remove(key);
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               this.debug("setConnectionAffinityContext() removed affinity context");
            }

            return true;
         } catch (NoWorkContextException var4) {
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               this.debug("setAffinityContext(null) failed with exception: " + var4);
            }

            return false;
         } catch (PropertyReadOnlyException var5) {
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               this.debug("setAffinityContext(null) failed with exception: " + var5);
            }

            return false;
         }
      } else {
         try {
            WorkContext wc = PrimitiveContextFactory.createMutable((Serializable)context);
            this.wcMap.put(key, wc, 130);
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               this.debug("setConnectionAffinityContext() context=" + context);
            }

            return true;
         } catch (PropertyReadOnlyException var6) {
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               this.debug("setAffinityContext failed with exception: " + var6);
            }

            return false;
         } catch (IOException var7) {
            if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
               this.debug("setAffinityContext failed with exception: " + var7);
            }

            return false;
         }
      }
   }
}
