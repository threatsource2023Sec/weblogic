package weblogic.utils.classloaders.debug;

import java.util.List;

public interface ClasspathElement {
   String getPath();

   List getChildren();

   ClasspathElement getParent();
}
