package csu.web.mypetstore.util;

import java.util.Random;

public class CodeUtil {
    public static String generateCode(int length) {
        String chars = "0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
