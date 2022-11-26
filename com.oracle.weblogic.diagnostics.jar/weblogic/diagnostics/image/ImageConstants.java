package weblogic.diagnostics.image;

import java.io.File;

public interface ImageConstants {
   int MIN_THREADS = 1;
   int MAX_THREADS = 1;
   int MAX_SOURCE_WAIT_TIME = 60000;
   int CHECK_SOURCE_WAIT_TIME = 500;
   String IMAGE_WORK_MNGR = "ImageWorkManager";
   int NO_LOCKOUT = -1;
   int DEFAULT_LOCKOUT_MINUTES = 1;
   int MINIMUM_LOCKOUT_MINUTES = 0;
   int MAXIMUM_LOCKOUT_MINUTES = 1440;
   String FILENAME_PATTERN = "yyyy_MM_dd_HH_mm_ss";
   String IMAGE_DIR = "diagnostic_images" + File.separator;
   String IMAGE_SOURCE_FILE_EXT = ".img";
   String IMAGE_SOURCE_XML_EXT = ".xml";
   String IMAGE_SOURCE_TEXT_EXT = ".txt";
   String IMAGE_SOURCE_ZIP_EXT = ".zip";
   String FLIGHT_RECORDER_IMAGE_SOURCE_FILE_EXT = ".jfr";
   String IMAGE_FILE_EXT = ".zip";
   String IMAGE_FILE_SEP = "_";
   String IMAGE_FILE_START = "diagnostic_image_";
   String IMAGE_SUMMARY_FILENAME = "image.summary";
   int CAPTURE_PENDING = 0;
   int CAPTURE_EXECUTING = 1;
   int CAPTURE_COMPLETED = 2;
   int CAPTURE_FAILED = 3;
   String[] CAPTURE_STATES = new String[]{"Pending", "Executing", "Completed", "Failed"};
}
