import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class fileMatrix {
	
	
private static final int BUFFER_SIZE = 512;
private static final int BLOCK_SIZE = 2048;

/******
 * 对文件进行划分块
 * @return
 * @throws IOException 
 */
	public int split() throws IOException
	{
		int blockNo,c1,c2,length,limit,count;
		String filePath="./2.jpg";		
		char[] a = new char[20];
		String str = new String(a); ;
		
		//对buffer进行内存分配
		int[] buffer=new int[BUFFER_SIZE];
//		if(buffer == null)//!!!!!!
//		{
//			System.out.print("Fail allocating buffer\n");
//			return -1;
//		}
		
		//以二进制打开文件
		DataInputStream fpr=new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
		
		length=getFileLength(filePath);
		
		blockNo=0;
		
		while(true)
		{
			limit = length - BLOCK_SIZE * blockNo;//检测文件是否读完
			if(limit > BLOCK_SIZE)
				limit = BLOCK_SIZE;
			if(limit <=0 )
				break;
			
			str=String.format("2-%d.jpg", blockNo);//格式化str
			System.out.print(str);
			
			//以二进制写方式打开文件
			DataOutputStream fpw=new DataOutputStream(new FileOutputStream(filePath));
			count=0;
			while(count < limit)
			{
				//一次性读取  BLOCK_SIZE * 1 字节的数据
				for(int i=0;i<buffer.length;i++)
					buffer[i]=fpr.readInt();
				
//				c1=fread(buffer,1,BUFFER_SIZE,fpr);
//				if(c1<=0)
//					break;
				//输出数据
				for(int i=0;i<buffer.length;i++)
					fpw.writeInt(buffer[i]);
//				c2=fwrite(buffer,1,c1,fpw);
//				if(c2!=c1)
//					break;
				count+=BUFFER_SIZE;
			}
			fpw.close();
			blockNo++;
		}
		fpr.close();
		return 0;
	}
	
    public int merge() throws IOException
    {
    	int blockNo;
    	int[] buffer=new int[BUFFER_SIZE];
    	char[] a = new char[20];
		String str = new String(a); ;
    	String filePath="./3.jpg";
    	
    	DataOutputStream fpw=new DataOutputStream(new FileOutputStream(filePath));
    	blockNo=0;
    	while(true)
    	{
    		str=String.format("2-%d.jpg", blockNo);//格式化str
			System.out.print(str);
			
			DataInputStream fpr=new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
//	    	if(!fpr)
//	    		break;
			while(true)
			{
				for(int i=0;i<buffer.length;i++)
					buffer[i]=fpr.readInt();
				for(int i=0;i<buffer.length;i++)
					fpw.writeInt(buffer[i]);
			}
			fpr.close();
			blockNo++;
    	}
    	fpw.close();
    	return 0;
    }

private int getFileLength(String filePath) {
	// TODO Auto-generated method stub
	return 0;
}

}
