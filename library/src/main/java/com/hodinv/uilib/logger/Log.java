package com.hodinv.uilib.logger;

import android.content.Context;
import android.os.Environment;

import com.hodinv.uilib.utils.FilesHelper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Created by vasily on 07.01.16.
 * Logger with saving into file
 */
public class Log {
    private static String LOGGER_ID = "log_%g";
    private static String LOGGER_FOLDER = "logger";
    private static int MAX_SIZE_OF_LOG_FILE = 1024 * 1024; // 1 MB
    private static int NUMBER_OF_LOG_FILES = 30;
    private static long MINIMAL_STORAGE_FREE_SPACE_TO_RUN_LOGGING = 1024 * 1024 * 100; // 100 MB
    private static FileHandler sFileHandler;
    private static int LEVEL = android.util.Log.VERBOSE;
    private static int LEVEL_FOR_FILE = android.util.Log.VERBOSE;
    private static boolean mFileLog = true;


    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    private Log() {
    }


    public static void v(String TAG, String msg) {
        if (LEVEL <= android.util.Log.VERBOSE) {
            android.util.Log.v(TAG, msg);
        }
        if (mFileLog && LEVEL_FOR_FILE <= android.util.Log.VERBOSE) {
            sFileHandler.publish(new LogRecord(Level.FINEST, TAG + ": " + msg));
        }
    }

    public static void d(String TAG, String msg) {
        if (LEVEL <= android.util.Log.DEBUG) {
            android.util.Log.d(TAG, msg);
        }
        if (mFileLog && LEVEL_FOR_FILE <= android.util.Log.DEBUG) {
            sFileHandler.publish(new LogRecord(Level.FINE, TAG + ": " + msg));
        }
    }

    public static void i(String TAG, String msg) {
        if (LEVEL <= android.util.Log.INFO) {
            android.util.Log.i(TAG, msg);
        }
        if (mFileLog && LEVEL_FOR_FILE <= android.util.Log.INFO) {
            sFileHandler.publish(new LogRecord(Level.INFO, TAG + ": " + msg));
        }
    }

    public static void w(String TAG, String msg) {
        if (LEVEL <= android.util.Log.WARN) {
            android.util.Log.w(TAG, msg);
        }
        if (mFileLog && LEVEL_FOR_FILE <= android.util.Log.WARN) {
            sFileHandler.publish(new LogRecord(Level.WARNING, TAG + ": " + msg));
        }
    }

    public static void e(String TAG, String msg) {
        if (LEVEL <= android.util.Log.ERROR) {
            android.util.Log.e(TAG, msg);
        }
        if (mFileLog && LEVEL_FOR_FILE <= android.util.Log.ERROR) {
            sFileHandler.publish(new LogRecord(Level.SEVERE, TAG + ": " + msg));
        }

    }

    /**
     * Sets max free space reqiered to start logging. By Default 100Mb
     * Should be called before initLogger or will not have any sense
     *
     * @param sizeInBytes required for logging
     */
    public static void setMinimalStorageFreeSpaceToRunLogging(long sizeInBytes) {
        MINIMAL_STORAGE_FREE_SPACE_TO_RUN_LOGGING = sizeInBytes;
    }

    /**
     * Sets folder to save logs. Default = logger
     * Should be called before initLogger or will not have any sense
     *
     * @param loggerFolder folder to save logs inside application data folder
     */
    public static void setLoggerFolder(String loggerFolder) {
        LOGGER_FOLDER = loggerFolder;
    }

    /**
     * Sets max size of one log file in bytes, By default 1Mb
     * Should be called before initLogger or will not have any sense
     *
     * @param maxSizeOfLogFile in bytes
     */
    public static void setMaxSizeOfLogFile(int maxSizeOfLogFile) {
        MAX_SIZE_OF_LOG_FILE = maxSizeOfLogFile;
    }

    /**
     * Number of log files to keep. Default 30
     * * Should be called before initLogger or will not have any sense
     *
     * @param numberOfLogFiles number of log files
     */
    public static void setNumberOfLogFiles(int numberOfLogFiles) {
        NUMBER_OF_LOG_FILES = numberOfLogFiles;
    }

    /**
     * Init logger in Application onCreate().
     *
     * @param level     minimal logging level to be shown in adb
     * @param fileLevel minimal loggin level to be wriiten into file
     * @param logToFile flag weather to write log to file
     * @param context   application context
     */
    public static void initLogger(int level, int fileLevel, boolean logToFile, Context context) {
        LEVEL = level;
        LEVEL_FOR_FILE = fileLevel;
        mFileLog = logToFile;
        if (!mFileLog) {
            return;
        }

        File filesDir;
        try {
            filesDir = FilesHelper.getDataDir(context);
        } catch (RuntimeException ex) {
            // cant create directory - no loging
            mFileLog = false;
            return;
        }


        if (Environment.getExternalStorageDirectory().getFreeSpace() < MINIMAL_STORAGE_FREE_SPACE_TO_RUN_LOGGING) {
            mFileLog = false;
            return;
        }

        new File(filesDir + "/" + LOGGER_FOLDER + "/").mkdirs();

        String logPath = filesDir + "/" + LOGGER_FOLDER + "/" + LOGGER_ID;
        try {
            sFileHandler = new FileHandler(logPath, MAX_SIZE_OF_LOG_FILE, NUMBER_OF_LOG_FILES);
            sFileHandler.setFormatter(new Formatter() {

                @Override
                public String format(LogRecord arg0) {
                    String type = null;
                    if (Level.SEVERE.intValue() == arg0.getLevel().intValue()) {
                        type = "E";
                    }
                    if (Level.WARNING.intValue() == arg0.getLevel().intValue()) {
                        type = "W";
                    }
                    if (Level.INFO.intValue() == arg0.getLevel().intValue()) {
                        type = "I";
                    }
                    if (Level.FINE.intValue() == arg0.getLevel().intValue()) {
                        type = "D";
                    }
                    if (type == null) {
                        type = "V";
                    }
                    return DATE_FORMAT.format(new Date(arg0.getMillis())) + " | " + type + " | " + arg0.getMessage() + "\n";
                }
            });
        } catch (IOException e) {
            mFileLog = false;
            e.printStackTrace();
        }
    }

}


