package weblogic.ejb.container.interfaces;

import java.util.Collection;

public interface IsolationLevel {
   String getIsolationLevel();

   Collection getAllMethodDescriptors();
}
