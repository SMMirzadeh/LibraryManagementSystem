package Tools;

import java.io.*;

public class FileManager {

    private String path;
    private boolean useFileSystem;
    private String dataRepository = "";
    public FileManager(String path , boolean useFileSystem){

        this.path = path;
        this.useFileSystem = useFileSystem;

    }
    public void firstInit(){

        if (useFileSystem) {
            //Creating file.txt file into the path direction
            try {
                File file = new File(path);
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

    }
    public void setAllData(String data){

        if (useFileSystem){

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(path));

                writer.write(data);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{

            dataRepository = data;

        }


    }
    public String getAllData(){

        if (useFileSystem){

            String dataInfo = "";
            try {
                BufferedReader reader = new BufferedReader(new FileReader(path));
                String line = "";
                while ((line = reader.readLine()) != null){

                    dataInfo +=line+"\n";
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return dataInfo;

        }else{

            return dataRepository;

        }

    }
}
