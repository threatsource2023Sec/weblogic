package weblogic.ejb.container.internal;

import weblogic.ejb.container.interfaces.LocalHandle30;

public class StatelessLocalObject extends BaseLocalObject {
   public final LocalHandle30 getLocalHandle30Object(Object businessLocalObject) {
      return new LocalHandle30Impl(this, businessLocalObject);
   }
}
