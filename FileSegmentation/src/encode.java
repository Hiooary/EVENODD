import java.io.IOException;


public class encode extends fileMatrix{

/******
 * 编码
 */	
@SuppressWarnings("unused")
public static void encode(int[][][] tempMemory)
{

}
	
@SuppressWarnings("null")
public static void main(String[] args) throws IOException
	{
	    int[][] buffer=split();//分好的块
	    
	    //System.out.print(buffer[0].length);
	    
	    int count=0;
	    int len=buffer[0].length;
	    if((len)%(M-1)!=0)
	    	count= len / (M-1) + 1;
	    //System.out.print(count);
	    
	    int[][][] tempMemory=new int[count][M-1][M];//将数据划分为矩阵
	    int t=0;
		for(int k=0;k < count;k++)
		{
		    for(int i=0;i<M-1;i++)
		    {
		    	for(int j=0;j<M;j++)
		    	{
		    		//System.out.print(buffer[i][t]);
		    		
		    		//对最后的一个矩阵补齐行数
		    		if(t>=len)
		    		{
		    			tempMemory[k][i][j]=0;
		    		}
		    		else
		    			tempMemory[k][i][j]=buffer[i][t];
		    		t++;
		    	}			
			}
		 }
	    
		//输出
//	    for(int k=0;k<2;k++)
//	    {
//		    for(int i=0;i<M-1;i++)
//			    {
//			    	for(int j=0;j<M;j++)
//			    	{
//			    		System.out.print(tempMemory[k][i][j]+" ");
//			    	}
//			    	System.out.print("\n");
//			    }
//		    System.out.print("\n");
//	    }
//	    
	    
//	     String filePath="./1.jpg";
//	     int bufSize;
		//编码
		encode(tempMemory);
		//decode();
		//merge();
	}

}
