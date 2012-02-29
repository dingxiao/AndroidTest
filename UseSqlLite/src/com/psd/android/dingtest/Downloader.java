package com.psd.android.dingtest;

import android.os.Message;
import android.os.SystemClock;
import java.security.MessageDigest;
import android.util.Log;
import android.util.Xml;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Downloader {

    /**
     * Ensure we have sizes for all the items.
     * @param config
     * @throws ClientProtocolException
     * @throws IOException
     * @throws DownloaderException
    
    private void getSizes(Config config)
        throws ClientProtocolException, IOException, DownloaderException {
        for (Config.File file : config.mFiles) {
            for(Config.File.Part part : file.mParts) {
                if (part.size < 0) {
                    part.size = getSize(part.src);
                }
            }
        }
        mTotalExpectedSize = config.getSize();
    }
 */
    public long getSize(String url) throws ClientProtocolException,
        IOException {
        url = normalizeUrl(url);
        Log.i(LOG_TAG, "Head " + url);
        HttpHead httpGet = new HttpHead(url);
        HttpResponse response = mHttpClient.execute(httpGet);
        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new IOException("Unexpected Http status code "
                + response.getStatusLine().getStatusCode());
        }
        Header[] clHeaders = response.getHeaders("Content-Length");
        if (clHeaders.length > 0) {
            Header header = clHeaders[0];
            return Long.parseLong(header.getValue());
        }
        return -1;
    }

    private String normalizeUrl(String url) throws MalformedURLException {
        return (new URL(new URL(mFileConfigUrl), url)).toString();
    }

    private InputStream get(String url, long startOffset,
            long expectedLength)
        throws ClientProtocolException, IOException {
        url = normalizeUrl(url);
        Log.i(LOG_TAG, "Get " + url);

        mHttpGet = new HttpGet(url);
        int expectedStatusCode = HttpStatus.SC_OK;
        if (startOffset > 0) {
            String range = "bytes=" + startOffset + "-";
            if (expectedLength >= 0) {
                range += expectedLength-1;
            }
            Log.i(LOG_TAG, "requesting byte range " + range);
            mHttpGet.addHeader("Range", range);
            expectedStatusCode = HttpStatus.SC_PARTIAL_CONTENT;
        }
        HttpResponse response = mHttpClient.execute(mHttpGet);
        long bytesToSkip = 0;
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != expectedStatusCode) {
            if ((statusCode == HttpStatus.SC_OK)
                    && (expectedStatusCode
                            == HttpStatus.SC_PARTIAL_CONTENT)) {
                Log.i(LOG_TAG, "Byte range request ignored");
                bytesToSkip = startOffset;
            } else {
                throw new IOException("Unexpected Http status code "
                        + statusCode + " expected "
                        + expectedStatusCode);
            }
        }
        HttpEntity entity = response.getEntity();
        InputStream is = entity.getContent();
        if (bytesToSkip > 0) {
            is.skip(bytesToSkip);
        }
        return is;
    }

    public File download(String src, String dest)
        throws DownloaderException, ClientProtocolException, IOException {
        File destFile = new File(mDataDir, dest);
        FileOutputStream os = openOutput(dest, false);
        try {
            downloadPart(src, os, 0, -1, null);
        } finally {
            os.close();
        }
        return destFile;
    }

    private void downloadPart(String src, FileOutputStream os,
            long startOffset, long expectedLength, MessageDigest digest)
        throws ClientProtocolException, IOException, DownloaderException {
        boolean lengthIsKnown = expectedLength >= 0;
        if (startOffset < 0) {
            throw new IllegalArgumentException("Negative startOffset:"
                    + startOffset);
        }
        if (lengthIsKnown && (startOffset > expectedLength)) {
            throw new IllegalArgumentException(
                    "startOffset > expectedLength" + startOffset + " "
                    + expectedLength);
        }
        InputStream is = get(src, startOffset, expectedLength);
        try {
            long bytesRead = downloadStream(is, os, digest);
            if (lengthIsKnown) {
                long expectedBytesRead = expectedLength - startOffset;
                if (expectedBytesRead != bytesRead) {
                    Log.e(LOG_TAG, "Bad file transfer from server: " + src
                            + " Expected " + expectedBytesRead
                            + " Received " + bytesRead);
                    throw new DownloaderException(
                            "Incorrect number of bytes received from server");
                }
            }
        } finally {
            is.close();
            mHttpGet = null;
        }
    }

    private FileOutputStream openOutput(String dest, boolean append)
        throws FileNotFoundException, DownloaderException {
        File destFile = new File(mDataDir, dest);
        File parent = destFile.getParentFile();
        if (! parent.exists()) {
            parent.mkdirs();
        }
        if (! parent.exists()) {
            throw new DownloaderException("Could not create directory "
                    + parent.toString());
        }
        FileOutputStream os = new FileOutputStream(destFile, append);
        return os;
    }

    public FileInputStream openInput(String src)
        throws FileNotFoundException, DownloaderException {
        File srcFile = new File(mDataDir, src);
        File parent = srcFile.getParentFile();
        if (! parent.exists()) {
            parent.mkdirs();
        }
        if (! parent.exists()) {
            throw new DownloaderException("Could not create directory "
                    + parent.toString());
        }
        return new FileInputStream(srcFile);
    }

    private long downloadStream(InputStream is, FileOutputStream os,
            MessageDigest digest)
            throws DownloaderException, IOException {
        long totalBytesRead = 0;
        while(true){
            if (Thread.interrupted()) {
                Log.i(LOG_TAG, "downloader thread interrupted.");
                mHttpGet.abort();
                throw new DownloaderException("Thread interrupted");
            }
            int bytesRead = is.read(mFileIOBuffer);
            if (bytesRead < 0) {
                break;
            }
            if (digest != null) {
                //updateDigest(digest, bytesRead);
            }
            totalBytesRead += bytesRead;
            os.write(mFileIOBuffer, 0, bytesRead);
            mDownloadedSize += bytesRead;
            int progress = (int) (Math.min(mTotalExpectedSize,
                    mDownloadedSize * 10000 /
                    Math.max(1, mTotalExpectedSize)));
            if (progress != mReportedProgress) {
                mReportedProgress = progress;
                //reportProgress(progress);
            }
        }
        return totalBytesRead;
    }
    private class DownloaderException extends Exception {
        public DownloaderException(String reason) {
            super(reason);
        }
    }

    private DefaultHttpClient mHttpClient;
    private HttpGet mHttpGet;
    private String mFileConfigUrl;
    private String mConfigVersion;
    private String mDataPath;
    private File mDataDir;
    private String mUserAgent;
    private long mTotalExpectedSize;
    private long mDownloadedSize;
    private int mReportedProgress;
    private final static int CHUNK_SIZE = 32 * 1024;
    byte[] mFileIOBuffer = new byte[CHUNK_SIZE];

    private final static String LOG_TAG = "Downloader";
    //private TextView mProgress;
    //private TextView mTimeRemaining;
    private final DecimalFormat mPercentFormat = new DecimalFormat("0.00 %");
    private long mStartTime;
    private Thread mDownloadThread;
    private boolean mSuppressErrorMessages;

    private final static long MS_PER_SECOND = 1000;
    private final static long MS_PER_MINUTE = 60 * 1000;
    private final static long MS_PER_HOUR = 60 * 60 * 1000;
    private final static long MS_PER_DAY = 24 * 60 * 60 * 1000;

    private final static String LOCAL_CONFIG_FILE = ".downloadConfig";
    private final static String LOCAL_CONFIG_FILE_TEMP = ".downloadConfig_temp";
    private final static String LOCAL_FILTERED_FILE = ".downloadConfig_filtered";
    private final static String EXTRA_CUSTOM_TEXT = "DownloaderActivity_custom_text";
    private final static String EXTRA_FILE_CONFIG_URL = "DownloaderActivity_config_url";
    private final static String EXTRA_CONFIG_VERSION = "DownloaderActivity_config_version";
    private final static String EXTRA_DATA_PATH = "DownloaderActivity_data_path";
    private final static String EXTRA_USER_AGENT = "DownloaderActivity_user_agent";

    private final static int MSG_DOWNLOAD_SUCCEEDED = 0;
    private final static int MSG_DOWNLOAD_FAILED = 1;
    private final static int MSG_REPORT_PROGRESS = 2;
    private final static int MSG_REPORT_VERIFYING = 3;


	
}
