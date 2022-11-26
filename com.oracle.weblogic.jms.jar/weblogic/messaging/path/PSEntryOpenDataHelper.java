package weblogic.messaging.path;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import weblogic.messaging.runtime.OpenDataConverter;

public class PSEntryOpenDataHelper implements OpenDataConverter {
   public CompositeData createCompositeData(Object object) throws OpenDataException {
      if (object == null) {
         return null;
      } else if (!(object instanceof PSEntryInfo)) {
         throw new OpenDataException("Unexpected class " + object.getClass().getName());
      } else {
         return ((PSEntryInfo)object).toCompositeData();
      }
   }
}
