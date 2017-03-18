import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;


public class fileMatrix {
	
	
private static final int BUFFER_SIZE = 512;
protected static final int BLOCK_SIZE = 2048;

/******
 * 对文件进行划分块
 * @return
 * @throws IOException 
 */
	public static void split() throws IOException
	{
		int blockNo;
		String filePath="./1.jpg";		
		
		//对buffer进行内存分配
		int[] buffer=new int[BLOCK_SIZE];
        //System.out.print(buffer.length);
		
		//以二进制打开文件
		DataInputStream fpr=new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
		blockNo=0;
		try{
			int j=0;
			while(true)
			{
				DataOutputStream fpw=new DataOutputStream(new FileOutputStream("./2-"+blockNo+".jpg"));
				
				for(int i=0;i<BLOCK_SIZE;i++)			
				{
					buffer[i]=fpr.readByte();
					fpw.writeByte((buffer[i]));
					System.out.print(buffer[i]+" ");
					j++;
				}
				fpw.close();
				blockNo++;
			}
		}catch(EOFException e)
		{
			fpr.close();
		}		
	}
/******
 * 对文件进行组合
 * @return
 * @throws IOException 
 */	
    @SuppressWarnings("resource")
	public static void merge() throws FileNotFoundException,IOException
    {
    	int blockNo;
    	int[] buffer=new int[BLOCK_SIZE];
    	String filePath="./3.jpg";
   
    	DataOutputStream fpw=new DataOutputStream(new FileOutputStream(filePath));
    	blockNo=0;    	
    	try{
	    	for(int k=0;k<6;k++)/////////////////////////////////////////////////////////
	    	{
	    		DataInputStream fpr=new DataInputStream(new BufferedInputStream(new FileInputStream("./2-"+blockNo+".jpg")));
	    		//System.out.print(getFileLength("./2-"+blockNo+".jpg"));
	    		for(int j=0;j<BLOCK_SIZE;j++)
	    		{
	    			buffer[j]=fpr.readByte();
					fpw.writeByte(buffer[j]);   			
	    		}
	    		fpr.close();
	    		blockNo++;	
	    	}
    	}catch(EOFException e){
    		fpw.close();
    	}
    }
/******
  * 求文件的长度
  * @return
  * 
  */	
public static long getFileLength(String filePath)
{
	// TODO Auto-generated method stub
	long ret = 0;
	FileChannel fc=null;
	try{
		File f=new File(filePath);
		if(f.exists() && f.isFile())
		{
			FileInputStream fis=new FileInputStream(f);
			fc=fis.getChannel();
			ret=fc.size();
		}
		else
		{
			System.out.print("file dones't exit or is not a file");
		}
	}catch(FileNotFoundException e)
	{
		
	}
	catch(IOException e){
		
	}
	finally{
		if(null!=fc)
		{
			try{
				fc.close();
			}catch(IOException e)
			{
				
			}
		}
	}
	return ret;
}

}
