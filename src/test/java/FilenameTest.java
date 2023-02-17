import org.junit.jupiter.api.Test;

public class FilenameTest {
  @Test
  public void test01() {
    System.out.println (removeFileExtension("z.tar.gz", true));
    System.out.println (removeFileExtension("z.tar.gz", false));
  }

  public static String removeFileExtension(String filename, boolean removeAllExtensions) {
    if (filename == null || filename.isEmpty()) {
      return filename;
    }

    String extPattern = "(?<!^)" + (removeAllExtensions ? "[.].*" : "[.][^.]*$");
    return filename.replaceAll(extPattern, "");
  }

}
