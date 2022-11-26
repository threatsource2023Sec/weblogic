package weblogic.messaging.path;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import weblogic.messaging.runtime.OpenDataConverter;

public class MemberOpenDataHelper implements OpenDataConverter {
   public CompositeData createCompositeData(Object object) throws OpenDataException {
      if (object == null) {
         return null;
      } else if (!(object instanceof Member)) {
         throw new OpenDataException("Unexpected class " + object.getClass().getName());
      } else {
         MemberInfo info = new MemberInfo((Member)object);
         return info.toCompositeData();
      }
   }
}
