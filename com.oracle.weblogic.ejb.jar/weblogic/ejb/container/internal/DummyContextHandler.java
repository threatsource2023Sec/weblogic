package weblogic.ejb.container.internal;

import weblogic.security.jacc.PolicyContextHandlerData;
import weblogic.security.service.ContextElement;
import weblogic.security.service.ContextHandler;

public class DummyContextHandler implements ContextHandler, PolicyContextHandlerData {
   public static final DummyContextHandler THE_ONE = new DummyContextHandler();

   private DummyContextHandler() {
   }

   public int size() {
      throw NeedRealContextHandlerError.THE_ONE;
   }

   public String[] getNames() {
      throw NeedRealContextHandlerError.THE_ONE;
   }

   public Object getValue(String name) {
      throw NeedRealContextHandlerError.THE_ONE;
   }

   public ContextElement[] getValues(String[] names) {
      throw NeedRealContextHandlerError.THE_ONE;
   }

   public Object getContext(String key) {
      throw NeedRealContextHandlerError.THE_ONE;
   }
}
