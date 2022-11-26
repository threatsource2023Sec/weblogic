package com.sun.faces.renderkit.html_basic;

import java.io.IOException;
import javax.faces.context.ResponseWriter;

public class ListboxRenderer extends MenuRenderer {
   protected void writeDefaultSize(ResponseWriter writer, int itemCount) throws IOException {
      writer.writeAttribute("size", itemCount, "size");
   }
}
