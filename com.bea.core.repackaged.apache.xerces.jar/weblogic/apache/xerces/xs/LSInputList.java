package weblogic.apache.xerces.xs;

import java.util.List;
import org.w3c.dom.ls.LSInput;

public interface LSInputList extends List {
   int getLength();

   LSInput item(int var1);
}
