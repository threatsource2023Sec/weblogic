package com.bea.adaptive.harvester;

import java.util.ArrayList;
import java.util.List;

public class AttributeTrackedDataItemImpl implements WatchedValues.AttributeTrackedDataItem {
   private List dataContext = new ArrayList();
   private Object data;

   public AttributeTrackedDataItemImpl(List dataContext, Object data) {
      this.dataContext = dataContext;
      this.data = data;
   }

   public List getDataContext() {
      return this.dataContext;
   }

   public Object getData() {
      return this.data;
   }
}
