package weblogic.utils.compiler;

import java.util.List;

public interface JavaCompilingResult {
   List getDisgnotics();

   boolean getHasError();
}
