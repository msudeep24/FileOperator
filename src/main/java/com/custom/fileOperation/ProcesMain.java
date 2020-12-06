package main.java.com.custom.fileOperation;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import main.java.com.custom.Exception.FolderNotFoundException;

public class ProcesMain {
	File names[];
    int length;
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Welcome to Custom File Operator created by Sudeep Mukherjee");
		Boolean bool = true;
		Scanner in = new Scanner(System.in);
		do {
			System.out.println("Please select from below options for operation:");
			System.out.println("1. Display all File");
			System.out.println("2. Search File(Case Sensitivity)");
			System.out.println("3. Add File");
			System.out.println("4. Delete File:");
			try {
//				Next Line is used here in scanner otherwise all operation with scanner next to it won't work
				String value = in.nextLine();
				int x = Integer.parseInt(value);
				File folder = new File("src/main/res");

				if(folder.isDirectory() && folder.exists())
	            {
					File[] fileList = folder.listFiles(new FileFilter() {
						
	                	@Override
	                    public boolean accept(File file) {
	                        return !file.isDirectory();
	                    }
	                });
					
					switch(x) {
						case 1:							
			                ProcesMain sorter = new ProcesMain();
			                sorter.sort(fileList);
			                
			                System.out.println("List of all files sorted Ascending order (Case Sensitive)");
			                for (File file : fileList) {
			                    System.out.println(file.getName());
			                }
							break;
						case 2:
							
							System.out.println("Please enter File you want to find out");
							String fileName = in.nextLine();
							
			                ProcesMain sorter1 = new ProcesMain();
			                sorter1.sort(fileList);
			                
//			                for (File file : fileList) {
//			                    System.out.println(file.getName());
//			                }
			                
			                int result = sorter1.binarySearch(fileList, fileName);
			                
			                if (result == -1)
			                   throw new FileNotFoundException(fileName + " is not present");
			                else
			                    System.out.println("File is present in the Directory");
			                
							break;
						case 3 :
							System.out.println("Please enter File with path you want to add in this directory");
							System.out.println("ie windows : D:/abc.txt linux : /var/abc.txt");
							String addFileName = in.nextLine();
						    
							File file = new File(addFileName);
				            
				            File copyFilePath = new File(folder.getAbsolutePath() + "/" + file.getName());
//				            System.out.println(copyFilePath.getAbsolutePath());
				            if (!copyFilePath.exists()) {
				                copyFilePath.createNewFile();
				            }
				            
				            copy(file, copyFilePath);
				            System.out.println("File Copy was successful");
				            break;
						case 4:
							System.out.println("Please enter file you want to delete from Directory");
							String deleteFileName = in.nextLine();
							String path = folder.getPath() + "/" + deleteFileName;
							Path filePath = Paths.get(path);
							Files.delete(filePath);
							break;
					}
	            } else {
	            	throw new FolderNotFoundException();			            
	            }
			} 
			catch(FolderNotFoundException e) {
				System.out.println("Resource Folder does not Exists");
				bool = false;
			} 
			catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			}catch (IOException e) {
				System.out.println("File Operation Failure");
				System.out.println(e.getMessage());
			}
			catch (Exception e) 
			{
				System.out.println("Something went wrong");
				bool = false;
			} finally {
				if(bool) {
					System.out.println("Do you want to repeat operation : y/n");
					String x = in.nextLine();
//					System.out.println("x : " +x);
					if(!x.equals("y"))
							bool = false;
				}
				
			}

		}while(bool);
		in.close();
	}

	void sort(File file[]) {
        if (file == null || file.length == 0) {
            return;
        }
        this.names = file;
        this.length = file.length;
        quickSort(0, length - 1);
    }

    void quickSort(int lowerIndex, int higherIndex) {
        int i = lowerIndex;
        int j = higherIndex;
        String pivot = this.names[lowerIndex + (higherIndex - lowerIndex) / 2].getName();

        while (i <= j) {
            while (this.names[i].getName().compareTo(pivot) < 0) {
                i++;
            }

            while (this.names[j].getName().compareTo(pivot) > 0) {
                j--;
            }

            if (i <= j) {
                exchangeNames(i, j);
                i++;
                j--;
            }
        }
        //call quickSort recursively
        if (lowerIndex < j) {
            quickSort(lowerIndex, j);
        }
        if (i < higherIndex) {
            quickSort(i, higherIndex);
        }
    }

    void exchangeNames(int i, int j) {
        File temp = this.names[i];
        this.names[i] = this.names[j];
        this.names[j] = temp;
    }
    
    int binarySearch(File[] arr, String x)
    {
        int low = 0, max = arr.length - 1;
        while (low <= max) {
            int mid = low + (max - low) / 2;

            int res = x.compareTo(arr[mid].getName());

            if (res == 0)
                return mid;

            if (res > 0) {
                low = mid + 1;
            }
            else
            {
                max = mid - 1;
            }
        }
        return -1;
    }
    
    public static void copy(File src, File dest) throws IOException {
        try(InputStream is = new FileInputStream(src);
            OutputStream os = new FileOutputStream(dest);)
        {
             byte[] buf = new byte[1024];
             int bytesRead; while ((bytesRead = is.read(buf)) > 0)
             { os.write(buf, 0, bytesRead); }
        }
    }

}
