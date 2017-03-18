import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class encode extends fileMatrix{
	public static int LEN;

/******
 * 编码
 * @throws IOException 
 */	
@SuppressWarnings("unused")
public static int[][][] encode(int[][][] tempMemory) throws IOException
{
	int count=tempMemory.length;
	//System.out.print(count);
	
	int[][][] dataCache = new int[count][M-1][M];           //矩阵形式存储 {{1,0,1,1,0},{0,1,1,0,0},{1,1,0,0,0},{0,1,0,1,1}};
	int[][] tempMatrix1=new int[count][M-1];
	int[][]tempMatrix2=new int[count][M-1];//行校验和对角线校验
	int[][][] temp=new int[count][M-1][M+2];//存储校验数据后的盘符
	int s[] = new int[count];//奇偶校验符号
//	
	System.out.print("输出原数据："+"\n");
    display(tempMemory[0]);
//    
////	
	System.out.print("输出转存数据："+"\n");
	for(int i=0;i<count;i++)
	{
		dataCache[i]=getColumnData(tempMemory[i]);
		s[i]=getCommonFactor(dataCache[i]);
		tempMatrix1[i]=horiExclusive_OR(dataCache[i]);
		tempMatrix2[i]=diagExclusive_OR(dataCache[i],s[i]);
		temp[i]=matrixTransposition(tempMatrix1[i],tempMatrix2[i],dataCache[i]);
	}
	display(dataCache[0]);

	
//	s=getCommonFactor(dataCache);
    System.out.print(s[0]+"\n");//求符号s
//	
//	//获取水平校验
//	tempMatrix1=horiExclusive_OR(dataCache);
	for(int i=0;i<tempMatrix1[0].length;i++)
	{
		System.out.print(tempMatrix1[0][i]+" ");
	}
	System.out.print("\n");
//	//获取对角线校验
	//tempMatrix2=diagExclusive_OR(dataCache,s);
	for(int i=0;i<tempMatrix2[0].length;i++){
		System.out.print(tempMatrix2[0][i]+" ");
	}
	System.out.print("\n");
//	
//	//存储得到的校验盘
//	temp=matrixTransposition(tempMatrix1,tempMatrix2,dataCache);
	System.out.print("存储得到的校验盘\n");
	display(temp[0]);
	
	//将编码后的结果以文件形式存储
	DataOutputStream fpw1=new DataOutputStream(new FileOutputStream("./2-"+M+"p.jpg"));
	DataOutputStream fpw2=new DataOutputStream(new FileOutputStream("./2-"+(M+1)+"p.jpg"));
	
//	int len=0;
//	while(len <= LEN)
//	{
		for(int i=0;i<tempMatrix1.length;i++)
		{
			for(int j=0;j<tempMatrix1[0].length;j++)
			{
				fpw1.writeByte(tempMatrix1[i][j]);
				fpw2.writeByte(tempMatrix2[i][j]);
			//	len++;
			}	
		}	
	//}
		fpw1.close();
		fpw2.close();
	
	
	return temp;
}
	
@SuppressWarnings("null")
public static void main(String[] args) throws IOException
	{
	    int[][] buffer=split();//分好的块
	    
	    //System.out.print(buffer[0].length);
	    
	    int count=0;
	    int LEN=buffer[0].length;
	    if((LEN)%(M-1)!=0)
	    	count= LEN / (M-1) + 1;
	    //System.out.print(count);
	    
	    int[][][] tempMemory=new int[count][M][M-1];//将数据划分为矩阵
	    int t=0;
		for(int k=0;k < count;k++)
		{
		    for(int i=0;i<M;i++)
		    {
		    	for(int j=0;j<M-1;j++)
		    	{
		    		//System.out.print(buffer[i][t]);
		    		
		    		//对最后的一个矩阵补齐行数
		    		if(t>=LEN)
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
