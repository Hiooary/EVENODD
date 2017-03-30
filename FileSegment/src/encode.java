import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class encode extends fileMatrix{

/******
 * 编码
 * @throws IOException 
 */	
public static void encode() throws IOException
{
	//读取五个文件碎块
	byte[][] tempMatrix = new byte[M][(int) DISK_SIZE];
	for(int i=0;i<M;i++)
	{
		tempMatrix[i]=FileToBlock("./2-"+i+".jpg");
	}
	//display(tempMatrix);
	
	byte[][][] tempMemory=BlockToMatrix(tempMatrix);
	System.out.print("输出原数据："+"\n");
	display(tempMemory[0]);
	
	byte[][][] dataCache = new byte[count][M-1][M];//转置矩阵，4*5
	
	System.out.print("输出转存数据："+"\n");
	for(int i=0;i<count;i++)
	{
		dataCache[i]=getColumnData(tempMemory[i]);
	}
	display(dataCache[0]);

	
	byte[][] tempMatrix1=new byte[count][M-1];//行校验
	byte[][]tempMatrix2=new byte[count][M-1];//对角线校验
	byte[][][] temp=new byte[count][M-1][M+2];//存储校验数据后的盘符
	byte s[] = new byte[count];//奇偶校验符号
	for(int i=0;i<count;i++)
	{
		s[i]=getCommonFactor(dataCache[i]);
		tempMatrix1[i]=horiExclusive_OR(dataCache[i]);
		tempMatrix2[i]=diagExclusive_OR(dataCache[i],s[i]);
		temp[i]=matrixTransposition(tempMatrix1[i],tempMatrix2[i],dataCache[i]);
	}
//	s[0]=getCommonFactor(dataCache[0]);
//	System.out.print(s[0]);
	
	//display(tempMatrix1);
	//display(tempMatrix2);

	System.out.print("存储得到的校验盘\n");
	display(temp[0]);
	
	//将编码后的结果以文件形式存储
	BlockToFile(MatrixToBlock(temp,M),M);
	BlockToFile(MatrixToBlock(temp,M+1),M+1);

}

/******
 * 译码
 * @throws IOException 
 */		
public static byte[][][] decode(int error1,int error2) throws IOException
{
	int m=M;	
	if(error1 != -1 && error2 !=-1)////两个磁盘出错
	{
		//错误的是两个校验块,此情况类同于编码
		if(error1 == m && error2 == (m+1))
		{
			//从磁盘读取文件碎块
			byte[][] tempMatrix = new byte[M+2][(int) DISK_SIZE];
			for(int i=0;i<M+2;i++)
			{
				if(i==error1 || i==error2)//损失的块补0
				{}
				else
					tempMatrix[i]=FileToBlock("./2-"+i+".jpg");
			}
			
			
			//转换为矩阵
			byte[][][] tempMemory=BlockToMatrix(tempMatrix);
			System.out.print("输出原数据："+"\n");
		    display(tempMemory[0]);
		    
			//转置矩阵，(M-1)*(M+2)
			byte[][][] dataCache = new byte[count][M-1][M+2];
			System.out.print("输出转存数据："+"\n");
			for(int i=0;i<count;i++)
			{
				dataCache[i]=getColumnData(tempMemory[i]);
			}
			display(dataCache[0]);
					
			//进行编码
			byte[] s=new byte[count];
			byte[][] tempMatrix1=new byte[count][M-1];
			byte[][] tempMatrix2=new byte[count][M-1];
			byte[][][] temp=new byte[count][M-1][M+2];//存储校验数据后的盘符	
			for(int i=0;i<count;i++)
			{
				s[i]=getCommonFactor(dataCache[i]);
				tempMatrix1[i]=horiExclusive_OR(dataCache[i]);
				tempMatrix2[i]=diagExclusive_OR(dataCache[i],s[i]);
				temp[i]=matrixTransposition(tempMatrix1[i],tempMatrix2[i],dataCache[i]);
			}
			
//			s[0]=getCommonFactor(dataCache[0]);
//			tempMatrix2[0]=diagExclusive_OR(dataCache[0],s[0]);
//			System.out.print(s[0]);
//			System.out.print(tempMatrix2[0][0]);
			//display(tempMatrix1);
			//display(tempMatrix2);
			
			//将译码出的数据写回丢失数据
			for(int k=0;k<count;k++)
			{
				for(int i=0;i<M-1;i++)
				{
					dataCache[k][i][error1]=tempMatrix1[k][i];
					dataCache[k][i][error2]=tempMatrix2[k][i];
				}
			}
			
			//将编码后的结果以文件形式存储
			BlockToFile(MatrixToBlock(dataCache,error1),error1);
			BlockToFile(MatrixToBlock(dataCache,error2),error2);
		
			//控制台输出				
			System.out.print("修复后的数据\n");
			display(dataCache[0]);
		}
		else if((error1 >= 0 && error1 < m ) && error2 == m)
		{	
			//错误的一个数据块和水平校验块
			
			//从磁盘读取文件碎块
			byte[][] tempMatrix = new byte[M+2][(int) DISK_SIZE];
			for(int i=0;i<M+2;i++)
			{
				if(i==error1 || i==error2)
				{
				}
				else
					tempMatrix[i]=FileToBlock("./2-"+i+".jpg");
			}

			for(int i=0;i<tempMatrix.length;i++)
			{
				for(int j=0;j<10;j++)
				{
					System.out.print(tempMatrix[i][j]+" ");
				}
				System.out.print("\n");
			}
			//System.out.print(tempMatrix.length);
			//display(tempMatrix);
			
			byte[][][] tempMemory=BlockToMatrix(tempMatrix);
			System.out.print("输出原数据："+"\n");
		    display(tempMemory[0]);
		   
			
			//转置矩阵，(M-1)*(M+2)
		    byte[][][] dataCache = new byte[count][M-1][M+2];		
			for(int i=0;i<count;i++)
			{
				dataCache[i]=getColumnData(tempMemory[i]);
			}
			System.out.print("输出转存数据："+"\n");
			display(dataCache[0]);
			
			//增加一个元素全为0的行
			byte[][][] temp=new byte[count][m][m+2];
			for(int c=0;c<count;c++)
			{
				temp[c]=addRow(dataCache[c],temp[c]);
			}
	
			//进行译码
			int[] s=new int[count];
			for(int c=0;c<count;c++)
			{
				s[c]=temp[c][getMod((error1-1),m)][m+1];
				for(int j=0;j<m;j++)
				{
					s[c]=s[c]^temp[c][getMod((error1-j-1),m)][j];
				}
			}
		
			//恢复error1,公式有误，无法恢复,修改公式后，可以恢复/////////////////////////////////////////////////////////////////////////////		
			for(int c=0;c<count;c++)
			{
				for(int k=0;k<temp[c].length-1;k++)
				{
					temp[c][k][error1]=(byte) (s[c]^temp[c][getMod(error1+k,m)][m+1]);
					for(int l=0;l<temp[c].length;l++)
					{	
						if(l!=error1)
						{		
							temp[c][k][error1]=(byte) (temp[c][k][error1]^temp[c][getMod(k+error1-l,m)][l]);
					    }	
					}
					dataCache[c][k][error1]=temp[c][k][error1];
				}		
			}
			//并用文件格式输出
			BlockToFile(MatrixToBlock(dataCache,error1),error1);
			
			//恢复error2
			byte[][] tempMatrix1=new byte[count][];
			for(int c=0;c<count;c++)
			{
				tempMatrix1[c]=horiExclusive_OR(dataCache[c]);
			    for(int i=0;i<M-1;i++)
				    {				
				    	dataCache[c][i][error2]=tempMatrix1[c][i];				    
				    }
			}
			//并用文件格式表示
			BlockToFile(MatrixToBlock(dataCache,error2),error2);
				
		    //控制台输出
		    System.out.print("修复后的数据\n");
		    display(dataCache[0]);
		    
		    return dataCache;
			
		}
		//错误的一个数据块和对角线校验块
		else if((error1 >= 0 && error1 < m) && error2 == (m+1) )
		{
			//从磁盘读取文件碎块
			byte[][] tempMatrix = new byte[M+2][(int) DISK_SIZE];
			for(int i=0;i<M+2;i++)
			{
				if(i==error1 || i==error2)
				{
					
				}
				else
					tempMatrix[i]=FileToBlock("./2-"+i+".jpg");
			}
			
			//转换为矩阵			
			byte[][][] tempMemory=BlockToMatrix(tempMatrix);
			System.out.print("输出原数据："+"\n");
		    display(tempMemory[0]);
			
			//转置矩阵，(M-1)*(M+2)
		    byte[][][] dataCache = new byte[count][M-1][M+2];		
			for(int i=0;i<count;i++)
			{
				dataCache[i]=getColumnData(tempMemory[i]);
			}
			System.out.print("输出转存数据："+"\n");
			display(dataCache[0]);
			
			//根据水平校验公式恢复error1
			for(int c=0;c<count;c++)
			{
				for(int i=0;i<dataCache[c].length;i++)
				{
					dataCache[c][i][error1]=0;
					for(int j=0;j<dataCache[c][i].length-1;j++)
					{
						if(j != error1)
						  dataCache[c][i][error1]=(byte) (dataCache[c][i][error1]^dataCache[c][i][j]);
					}
				}	
			}
			
			//根据对角线公式恢复error2
			byte[] s=new byte[count];
			byte[][] tempMatrix2=new byte[count][];
			for(int c=0;c<count;c++)
			{
				s[c]=getCommonFactor(dataCache[c]);
				tempMatrix2[c]=diagExclusive_OR(dataCache[c],s[c]);
				for(int i=0;i<tempMatrix2[c].length;i++)
			    {
			    	dataCache[c][i][error2]=tempMatrix2[c][i];
			    }
			}
			
			//输出
			 System.out.print("修复后的数据\n");
			 display(dataCache[0]);
			 
			 BlockToFile(MatrixToBlock(dataCache,error1),error1);
			BlockToFile(MatrixToBlock(dataCache,error2),error2);
			
		}
		//错误的两个数据块
		else if((error1 >= 0 && error1 < m) && (error2 >= 0 && error2 < m))
		{
			//从磁盘读取文件碎块
			byte[][] tempMatrix = new byte[M+2][(int) DISK_SIZE];
			for(int i=0;i<M+2;i++)
			{
				if(i==error1 || i==error2)
				{
					
				}
				else
					tempMatrix[i]=FileToBlock("./2-"+i+".jpg");
			}
			
			//转换为矩阵			
			byte[][][] tempMemory=BlockToMatrix(tempMatrix);
			System.out.print("输出原数据："+"\n");
		    display(tempMemory[0]);
			
			//转置矩阵，(M-1)*(M+2)
			byte[][][] dataCache = new byte[count][M-1][M+2];		
			for(int i=0;i<count;i++)
			{
				dataCache[i]=getColumnData(tempMemory[i]);
			}
			System.out.print("输出转存数据："+"\n");
			display(dataCache[0]);
			
			//增加一个元素全为0的行
			byte[][][] temp=new byte[count][m][m+2];
			for(int c=0;c<count;c++)
			{
				temp[c]=addRow(dataCache[c],temp[c]);
			}		
			//display(temp[0]);
			
			int[] s=new int[count];//奇偶符号
			for(int c=0;c<count;c++)
			{
				s[c]=0;
				for(int l=0;l<m-1;l++)
				{
					s[c]=s[c]^dataCache[c][l][m]^dataCache[c][l][m+1];
				}
			}		
			//System.out.print(s[0]);
			
			
			//寻找水平综合征
			int[][] S0 = new int[count][m];
			for(int c=0;c<count;c++)
			{
				for(int u=0;u<m;u++)
				{
					S0[c][u]=0;
					for(int l=0;l<=m;l++)
					{
						if(l != error1 && l!= error2)
							S0[c][u]=S0[c][u]^temp[c][u][l];
					}
				}	
			}
			
//			for(int i=0;i<S0[0].length;i++)
//			{
//				System.out.print(S0[0][i]+" ");
//			}
			
			int[][] S1=new int[count][m];//对角线综合征
			for(int c=0;c<count;c++)
			{
				for(int u=0;u<m;u++)
				{
					S1[c][u]=s[c]^temp[c][u][m+1];
					for(int l=0;l<m;l++)
					{
						if(l != error1 && l!= error2){	
							S1[c][u]=S1[c][u]^temp[c][getMod(u-l,m)][l];
						}
					}
				}
			}
			
//			for(int i=0;i<S1[0].length;i++)
//			{
//				System.out.print(S1[0][i]);
//			}
			
			//通过步骤计算
			int temp_s;
			for(int c=0;c<count;c++)
			{
				s[c]=getMod((-(error2-error1)-1),m);
				temp_s=s[c];
				while(temp_s!=(m-1))
				{
					temp[c][temp_s][error2]=(byte) (S1[c][getMod(error2+temp_s,m)]^temp[c][getMod(temp_s+(error2-error1),m)][error1]);
					temp[c][temp_s][error1]=(byte) (S0[c][temp_s]^temp[c][temp_s][error2]);
					dataCache[c][temp_s][error2]=temp[c][temp_s][error2];
					dataCache[c][temp_s][error1]=temp[c][temp_s][error1];
					temp_s=getMod(temp_s-(error2-error1),m);
				}
			}
			
			System.out.print("修复后的数据\n");
			display(dataCache[0]);
			
			 BlockToFile(MatrixToBlock(dataCache,error1),error1);
			 BlockToFile(MatrixToBlock(dataCache,error2),error2);
				
		}
	}
	//只有一个数据块出错
	else if(error2 == -1 && error1 != -1)
	{
		
	}
	else
	{//错误数据块不能找到
		System.out.print("error:fail to find the error disk!!");
		System.exit(0);
	
    }
	return null;
}

public static void main(String[] args) throws IOException
	{
	    //获取文件块长度
		String filePath="./1.jpg";		
		length=(int) getFileLength(filePath);
		if((length % M)!=0)
			DISK_SIZE = (length / M) + 1;//取上限
		else
			DISK_SIZE = length / M;
//	    System.out.print(length);//40875
//	    System.out.print(DISK_SIZE);//8175
//	
//		split();//分块   
//		encode();//编码
		int error1 = 2,error2 = 6;//错误位置       //56/                   /25/35/01/02/03/04/14/34/23
        decode(error1,error2);//译码
		merge();//合并文件块
		
//        System.out.print((-1)^(-1)^(72)^(22)^(-61));//第一行
//        System.out.print((-2)^(-26)^(7)^(-61));//s
//        System.out.print((-2)^(-26)^(7)^(-61)^(-1)^(105)^(33)^(43));//对角线
	}

}
