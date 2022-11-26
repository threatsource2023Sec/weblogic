package com.bea.adaptive.harvester;

public class DataContextImpl implements WatchedValues.ContextItem {
   private WatchedValues.ContextItem.AttributeTermType attrTermType;
   private Object context;

   public DataContextImpl(WatchedValues.ContextItem.AttributeTermType attrTermType, Object context) {
      this.attrTermType = attrTermType;
      this.context = context;
   }

   public WatchedValues.ContextItem.AttributeTermType getAttributeTermType() {
      return this.attrTermType;
   }

   public Object getContext() {
      return this.context;
   }
}
