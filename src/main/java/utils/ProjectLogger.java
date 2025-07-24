package utils;

import java.io.IOException;
import java.util.logging.*;

public class ProjectLogger {
    public static final Logger logger = Logger.getLogger(ProjectLogger.class.getName());
    static {
        try {
            // Datei-Handler konfigurieren
            FileHandler fileHandler = new FileHandler("project.log");
            fileHandler.setFormatter(new OneLineFormatter());
            logger.addHandler(fileHandler);
            // Verhindern, dass der Logger Handler vom Parent erbt
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class OneLineFormatter extends Formatter {
        @Override
        public String format(LogRecord record) {
            return String.format("%1$td. %1$tb %1$tY %1$tT %1$Tp %2$s: %3$s%n",
                    record.getMillis(), mapLevel(record.getLevel()), record.getMessage());
        }

        private String mapLevel(Level level) {
            switch (level.getName()) {
                case "SEVERE":
                    return "SCHWERWIEGEND";
                case "WARNING":
                    return "WARNUNG";
                case "INFO":
                    return "INFORMATION";
                default:
                    return level.getLocalizedName();
            }
        }
    }

    public static void logInfo(String message) {
        logger.log(Level.INFO, message);
    }

    public static void logWarning(String message) {
        logger.log(Level.WARNING, message);
    }

    public static void logError(String message) {
        logger.log(Level.SEVERE, message);
    }

}