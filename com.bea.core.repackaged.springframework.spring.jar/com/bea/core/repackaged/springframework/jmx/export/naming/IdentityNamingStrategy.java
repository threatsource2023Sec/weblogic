package com.bea.core.repackaged.springframework.jmx.export.naming;

import com.bea.core.repackaged.springframework.jmx.support.ObjectNameManager;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.util.Hashtable;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

public class IdentityNamingStrategy implements ObjectNamingStrategy {
   public static final String TYPE_KEY = "type";
   public static final String HASH_CODE_KEY = "hashCode";

   public ObjectName getObjectName(Object managedBean, @Nullable String beanKey) throws MalformedObjectNameException {
      String domain = ClassUtils.getPackageName(managedBean.getClass());
      Hashtable keys = new Hashtable();
      keys.put("type", ClassUtils.getShortName(managedBean.getClass()));
      keys.put("hashCode", ObjectUtils.getIdentityHexString(managedBean));
      return ObjectNameManager.getInstance(domain, keys);
   }
}
