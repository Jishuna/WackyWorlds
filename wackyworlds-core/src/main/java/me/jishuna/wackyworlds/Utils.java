package me.jishuna.wackyworlds;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils {
    public static boolean copyFile(InputStream input, File outputFile) {
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(outputFile);
            input.transferTo(output);
        } catch (IOException ioe) {
            return false;
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (output != null) {
                    output.close();
                }
            } catch (IOException ioe) {
                return false;
            }
        }
        return true;
    }
}
