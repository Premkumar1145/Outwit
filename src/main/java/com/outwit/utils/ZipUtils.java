package com.outwit.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.outwit.base.BasePageActions;

public class ZipUtils extends BasePageActions{
	
    public static List <String> fileList;
    public final static String OUTPUT_ZIP_FILE = "Folder.zip";
   // public final static String SOURCE_FOLDER = ReportZipFolderPath+"ExtentReport"; // SourceFolder path
	
    static String localDir = System.getProperty("user.dir"); 
   	File file = new File(localDir + "\\ExtentReport");
    
    
   public final static String SOURCE_FOLDER = localDir + "\\ExtentReport";
  
	
  
   
   
   
	public static void createReportZipFolder(){
		logger.info("Source Path : " +SOURCE_FOLDER);


	    fileList = new ArrayList < String > ();
	    generateFileList(new File(SOURCE_FOLDER));
	    zipIt(OUTPUT_ZIP_FILE);
	}
	   

    public static void zipIt(String zipFile) {
        byte[] buffer = new byte[1024];
        String source = new File(SOURCE_FOLDER).getName();
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(fos);

            logger.info("Output to Zip : " + zipFile);
            FileInputStream in = null;

            for (String file: fileList) {
                logger.info("File Added : " + file);
                ZipEntry ze = new ZipEntry(source + File.separator + file);
                zos.putNextEntry(ze);
                try {
                    in = new FileInputStream(SOURCE_FOLDER + File.separator + file);
                    int len;
                    while ((len = in .read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                } finally {
                    in.close();
                }
            }

            zos.closeEntry();
            logger.info("Folder successfully compressed");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                zos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void generateFileList(File node) {
        // add file only
        if (node.isFile()) {
            fileList.add(generateZipEntry(node.toString()));
        }

        if (node.isDirectory()) {
            String[] subNote = node.list();
            for (String filename: subNote) {
                generateFileList(new File(node, filename));
            }
        }
    }

    private static String generateZipEntry(String file) {
        return file.substring(SOURCE_FOLDER.length() + 1, file.length());
    }
}