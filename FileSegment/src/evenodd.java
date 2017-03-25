import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/******************************
 *******算法的一些基本函数
 *******
 */  
public class evenodd {
	private static final int M = 5;//块数，为素数
	
	public static int getMod(int a,int b)//有限域上的模运算
	{
		if(a>=0)
		{
			return a%b;
		}
		else
			return (a+b)%b;
	}
	public static int[][] getColumnData(int[][] tempMemory){//从对象数组中获取某列的方法
		int m=tempMemory.length;
		int l=tempMemory[0].length;
		int[][] temp=new int[l][m];
		for(int i=0;i<l;i++)
		{
			for(int j=0;j<m;j++)
			{
				temp[i][j]=tempMemory[j][i];
			}
		}
		return temp;	
	}	
    public static int getCommonFactor(int[][] dataCache)//求s
	{
		int s=0;
		int m=M;
		for(int i=1;i<m;i++)
		{
			s=s^dataCache[getMod(m-1-i, m)][i];
			//System.out.print(dataCache[getMod(m-1-i, m)][i]+" ");
		}
		return s;
	}
	public static int[] horiExclusive_OR(int[][] dataCache) {//水平校验
		// TODO Auto-generated method stub
		int l=M-1;		
		int horiExculsive[]=new int[l];
		for(int i=0;i<l;i++)
		{
			int temp=0;
			for(int j=0;j<M;j++)
			{
				temp=temp^dataCache[i][j];
			}
			horiExculsive[i]=temp;
		}
		return horiExculsive;
	}
	public static int[] diagExclusive_OR(int[][] dataCache, int commonFactor) {//对角线校验
		// TODO Auto-generated method stub
		int l=M-1;
		int diagExclusive[] = new int[l];
		for(int i=0;i<l;i++)
		{
			int temp=commonFactor;
			for(int j=0; j<M;j++)
			{
				int t=getMod((i-j),M);
				if(t>=l)
				{
					continue;
				}
				temp=temp^dataCache[t][j];
				//System.out.print(dataCache[t][j]);
				
			}
			diagExclusive[i]=temp;
		}
		return diagExclusive;
	}
	public static int[][] matrixTransposition(int[] tempMatrix1,int[] tempMatrix2, int[][] dataCache) {//将获得的校验矩阵存储
		// TODO Auto-generated method stub
		int m=M;
		int[][] temp=new int[m-1][m+2];
		for(int i=0;i<dataCache.length;i++)
		{
			for(int j=0;j<m;j++)
			{
				temp[i][j]=dataCache[i][j];
			}
		}
		for(int i=0;i<tempMatrix1.length;i++)
		{
			temp[i][m]=tempMatrix1[i];
			temp[i][m+1]=tempMatrix2[i];
		}
		
		return temp;
	}
	public static int[][] addRow(int[][] dataCache,int[][] temp) {//增加一个0行
		// TODO Auto-generated method stub
		int i,j;
		for(i=0;i<dataCache.length;i++)
		{
			for(j=0;j<dataCache[i].length;j++)
			{
				temp[i][j]=dataCache[i][j];
			}
		}
		for(j=0;j<dataCache[0].length;j++)
		{
			temp[i][j]=0;
		}
		return temp;
	}
	public static void display(int[][] temp)
	{
		
		for(int i=0;i<temp.length;i++)
		{
			for(int j=0;j<temp[i].length;j++)
			{
				System.out.printf("%6d",temp[i][j]);
			}
			System.out.print(" \n");
		}
		System.out.print(" \n");
	}	
/******
 *破坏数据,此处置为0
 */    
//	public static int[][][] Destroy(int error1,int error2,int[][][] dataCache)
//	{
//		int count=dataCache.length;
//		for(int k=0;k<count;k++)
//		{
//			for(int i=0;i<dataCache[0].length;i++)
//			{
//				dataCache[k][i][error1]=0;
//				dataCache[k][i][error2]=0;
//			}
//		}
//		return dataCache;
//	}
//	public static void ShowChange(int[][][] temp,String filePath) throws IOException
//	{
//		FileOutputStream fpw=(new FileOutputStream(filePath));
//    	try{
//	    	for(int k=0;k<temp.length;k++)/////////////////////////////////////////////////////////
//	    	{
//	    		for(int i=0;i<temp[k].length;i++)
//	    		{
//	    			for(int j=0;j<temp[k][i].length;j++)
//	    			{
//						fpw.write(temp[k][i][j]);
//	    			}
//	    		}
//	    	}
//    	}catch(EOFException e){
//    		fpw.close();
//    	}
	}

