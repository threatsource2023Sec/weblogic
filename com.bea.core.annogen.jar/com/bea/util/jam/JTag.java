package com.bea.util.jam;

import java.util.Properties;

public interface JTag extends JElement {
   String getName();

   String getText();

   Properties getProperties_lineDelimited();

   Properties getProperties_whitespaceDelimited();
}
