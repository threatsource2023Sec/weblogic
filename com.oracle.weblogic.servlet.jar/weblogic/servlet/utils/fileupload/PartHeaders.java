package weblogic.servlet.utils.fileupload;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

class PartHeaders implements Serializable {
   private static final long serialVersionUID = -4455695752627032559L;
   private final Map headerMap = new LinkedHashMap();
   private Set headerNames = null;

   public String getHeader(String name) {
      String nameLower = name.toLowerCase();
      List headerValueList = (List)this.headerMap.get(nameLower);
      return null == headerValueList ? null : (String)headerValueList.get(0);
   }

   public synchronized Collection getHeaderNames() {
      if (null == this.headerNames) {
         this.headerNames = Collections.unmodifiableSet(this.headerMap.keySet());
      }

      return this.headerNames;
   }

   public List getHeaders(String name) {
      String nameLower = name.toLowerCase();
      List headerValueList = (List)this.headerMap.get(nameLower);
      return null == headerValueList ? Collections.EMPTY_LIST : headerValueList;
   }

   public synchronized void addHeader(String name, String value) {
      String nameLower = name.toLowerCase();
      List headerValueList = (List)this.headerMap.get(nameLower);
      if (null == headerValueList) {
         headerValueList = new ArrayList();
         this.headerMap.put(nameLower, headerValueList);
      }

      ((List)headerValueList).add(value);
   }
}
