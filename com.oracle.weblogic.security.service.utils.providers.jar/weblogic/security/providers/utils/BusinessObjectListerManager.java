package weblogic.security.providers.utils;

import java.util.List;
import weblogic.management.utils.InvalidCursorException;

public class BusinessObjectListerManager extends ListerManager {
   public String addLister(List businessObjectList, int maxToReturn) {
      return addLister(new BusinessObjectLister(businessObjectList, maxToReturn));
   }

   public Object getCurrentBusinessObject(String cursor) throws InvalidCursorException {
      BusinessObjectLister lister = (BusinessObjectLister)getLister(cursor);
      Object currentObject = lister.getCurrentBusinessObject();
      return currentObject;
   }
}
