package webservice2code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class FileHelper {
    
private static String m_CompleteFolderPath;

    public static String GetOutputFolderPath()
    {
        return m_CompleteFolderPath;
    }
  
  public boolean createPackageFolderStructure(String PackageName) {
      try
    {
        if (AppConfig.filepath.length() != 0)
        {
            
            if (AppConfig.filepath.endsWith("/"))
            {
                AppConfig.filepath = AppConfig.filepath.substring(0, AppConfig.filepath.length()-1);
            }

            if (AppConfig.package_name.endsWith("/"))
            {
                AppConfig.package_name = AppConfig.package_name.substring(0, AppConfig.package_name.length()-1);

            }


            String[] folders = AppConfig.package_name.split("\\.");


            
            String folderPath = AppConfig.filepath;

            
 
            for (int loop = 0; loop < folders.length; loop++)
            {
                folderPath = folderPath + "/" + folders[loop];

                File f = new File(folderPath);

                if (!f.exists()) {
                    if (!f.mkdir()) {
                        throw new Exception("Failed to create folder");
                    }
                }

            }
            

            m_CompleteFolderPath = folderPath;

        }
        else
        {
            throw new Exception("Output folder has not been set");
        }
    }
    catch (Exception ex)
    {
        return false;
    }

    return true;
}
 

  public boolean writeFile(String fileName, String data) {
      try {
            File fi = new File(fileName);

            if (fi.exists()) {
                fi.delete();
            }

            Writer out = new OutputStreamWriter(new FileOutputStream(fi));

            out.write(data);
            out.close();
        } catch (Exception ex) {
            return false;
        }


        return true;
    }
  

  public String readFile(String fileName) {
  return null;
  }
}

