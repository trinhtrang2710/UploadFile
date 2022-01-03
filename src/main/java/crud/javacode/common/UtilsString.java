package crud.javacode.common;

public class UtilsString {
    public static String pathFileUpload = "G:/upload/";

    public static class FileTail {
        public static String WORD_1 = "DOC";
        public static String WORD_2 = "DOCX";

        public static String IMAGE_1 = "PNG";
        public static String IMAGE_2 = "JPG";

        public static String EXCEL = "xlsx";

        public static String PDF = "PDF";

        public static boolean checkFile(String type, String tail) {
            if (type.trim().equalsIgnoreCase("WORD")) {
                if (tail.trim().equalsIgnoreCase(WORD_1) || tail.trim().equalsIgnoreCase(WORD_2)) {
                    return true;
                }
            }
            if (type.trim().equalsIgnoreCase("image")) {
                if (tail.trim().equalsIgnoreCase(IMAGE_1) || tail.trim().equalsIgnoreCase(IMAGE_2)) {
                    return true;
                }
            }
            if (type.trim().equalsIgnoreCase("excel")) {
                if (tail.trim().equalsIgnoreCase(EXCEL)) {
                    return true;
                }

            }
            if (type.trim().equalsIgnoreCase("pdf")) {
                if (tail.trim().equalsIgnoreCase(PDF)) {
                    return true;
                }
            }
            return false;
        }

    }
}
