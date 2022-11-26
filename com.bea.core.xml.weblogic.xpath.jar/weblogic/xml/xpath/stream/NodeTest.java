package weblogic.xml.xpath.stream;

import weblogic.xml.xpath.parser.NodeTestModel;

public interface NodeTest extends NodeTestModel {
   boolean isMatch(StreamNode var1);

   boolean isStringConvertible();
}
