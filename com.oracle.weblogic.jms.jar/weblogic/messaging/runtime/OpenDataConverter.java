package weblogic.messaging.runtime;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;

public interface OpenDataConverter {
   CompositeData createCompositeData(Object var1) throws OpenDataException;
}
