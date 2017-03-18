import java.io.IOException;


public class encode extends fileMatrix{

/******
 * 编码
 */	
@SuppressWarnings("unused")
public static int[][] encode(int[][] tempMemory)
{
	int count=tempMemory[0].length;
	//System.out.print(count);
	int dataCache[][] = null;           //矩阵形式存储 {{1,0,1,1,0},{0,1,1,0,0},{1,1,0,0,0},{0,1,0,1,1}};
	int[] tempMatrix1=null,tempMatrix2=null;//行校验和对角线校验
	int[][] temp=null;//存储校验数据后的盘符
	int s;//奇偶校验符号
	
	System.out.print("输出原数据："+"\n");
    display(tempMemory);
    
//	
	System.out.print("输出转存数据："+"\n");
	dataCache=getColumnData(tempMemory);
	display(dataCache);
	
	s=getCommonFactor(dataCache);
	//System.out.print(s);//求符号s
	
	//获取水平校验
	tempMatrix1=horiExclusive_OR(dataCache);
//	for(int i=0;i<tempMatrix1.length;i++){
//		System.out.print(tempMatrix1[i]);
//	}
	//获取对角线校验
	tempMatrix2=diagExclusive_OR(dataCache,s);
//	for(int i=0;i<tempMatrix2.length;i++){
//		System.out.print(tempMatrix2[i]+" ");
//	}
	
	//存储得到的校验盘
	temp=matrixTransposition(tempMatrix1,tempMatrix2,dataCache);
	System.out.print("存储得到的校验盘\n");
	display(temp);
	return temp;
	

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
		encode(tempMemory[0]);
		//decode();
		//merge();
	}

}
