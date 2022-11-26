package org.apache.log.output.io.rotate;

import java.io.File;

public interface FileStrategy {
   File nextFile();
}
