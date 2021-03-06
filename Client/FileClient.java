import java.io.*; 
import java.rmi.*;
import java.rmi.registry.*;

public class FileClient{

   private FileClient() {}
   public static void main(String argv[]) {
      if(argv.length != 3) {
        System.out.println("Usage: java FileClient fileName machineName upload/download");
        System.exit(0);
      }
      try {
         if (argv[2].equals("download")) {
            Registry registry = LocateRegistry.getRegistry();
            System.out.println("requesting "+argv[0]+" .......");
            FileInterface fi = (FileInterface) registry.lookup("FileServer");
            byte[] filedata = fi.downloadFile(argv[0]);
            File file = new File(argv[0]);
            BufferedOutputStream output = new
            BufferedOutputStream(new FileOutputStream(file.getName()));
            System.out.println(argv[0] + " is being downloaded...");
            output.write(filedata,0,filedata.length);
            System.out.println(argv[0] + " downloaded successfully");
            output.flush();
            output.close();
         }
         else if (argv[2].equals("upload")) {
            Registry registry = LocateRegistry.getRegistry();
            System.out.println("requesting "+argv[0]+" .......");
            FileInterface fi = (FileInterface) registry.lookup("FileServer");
            
            File file = new File(argv[0]);
            byte filedata[] = new byte[(int)file.length()];
            BufferedInputStream input = new BufferedInputStream(new FileInputStream(argv[0]));
            input.read(filedata,0,filedata.length);
			   input.close();
			   fi.uploadFile(filedata, argv[0]);
            System.out.println(argv[0] + " successfully uploaded");
         }
         
      } catch(Exception e) {
         System.err.println("FileServer exception: "+ e.getMessage());
         e.printStackTrace();
      }
   }
}