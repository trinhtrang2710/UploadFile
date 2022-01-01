package crud.javacode.common;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilsDate {
    private static final Logger logger = LoggerFactory.getLogger(UtilsDate.class);
    private SimpleDateFormat calendarDatetimeSdf = new SimpleDateFormat("yyyyMMdd'T'HHmm'00'");
    public static String dateFormatDDMMYYY = "dd/MM/yyyy HH:mm:ss";

    public static Date str2date(String dateStr, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);//"yyyy-MM-dd HH:mm"
        Date date = null;
        try {
            if(StringUtils.isNotBlank(dateStr))
                date = sdf.parse(dateStr);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return date;
    }

    public static String date2str(Date input, String oFormat) {
        String result = "";
        if (input != null) {
            try {
                DateFormat df = new SimpleDateFormat(oFormat);
                result = df.format(input);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
