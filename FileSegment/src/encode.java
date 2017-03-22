import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class encode extends fileMatrix{

/******
 * ����
 * @throws IOException 
 */	
public static void encode() throws IOException
{
	//��ȡ����ļ����
	int[][] tempMatrix = new int[M][length];
	for(int i=0;i<M;i++)
	{
		tempMatrix[i]=FileToBlock("./2-"+i+".jpg");
	}
	int[][][] tempMemory=BlockToMatrix(tempMatrix);
	
	int[][][] dataCache = new int[count][M-1][M];//ת�þ���4*5
	int[][] tempMatrix1=new int[count][M-1];//��У��
	int[][]tempMatrix2=new int[count][M-1];//�Խ���У��
	int[][][] temp=new int[count][M-1][M+2];//�洢У�����ݺ���̷�
	int s[] = new int[count];//��żУ�����

	System.out.print("���ԭ���ݣ�"+"\n");
    display(tempMemory[0]);
	
	System.out.print("���ת�����ݣ�"+"\n");
	for(int i=0;i<count;i++)
	{
		dataCache[i]=getColumnData(tempMemory[i]);
		s[i]=getCommonFactor(dataCache[i]);
		tempMatrix1[i]=horiExclusive_OR(dataCache[i]);
		tempMatrix2[i]=diagExclusive_OR(dataCache[i],s[i]);
		temp[i]=matrixTransposition(tempMatrix1[i],tempMatrix2[i],dataCache[i]);
	}
	display(dataCache[0]);

	System.out.print("�洢�õ���У����\n");
	display(temp[0]);
	
	//�������Ľ�����ļ���ʽ�洢
	BlockToFile(MatrixToBlock(temp),M);
	BlockToFile(MatrixToBlock(temp),M+1);
}

/******
 * ����
 * @throws IOException 
 */		
public static void decode(int error1,int error2) throws IOException
{
	int m=M;	
	if(error1 != -1 && error2 !=-1)////�������̳���
	{
		//�����������У���,�������ͬ�ڱ���
		if(error1 == m && error2 == (m+1))
		{
			//�Ӵ��̶�ȡ�ļ����
			int[][] tempMatrix = new int[M+2][length];
			for(int i=0;i<M+2;i++)
			{
				if(i==error1 || i==error2)
				{
//					for(int b=0;i<BLOCK_SIZE;i++)			
//					{
//						 tempMatrix[error1][b]=0;
//					}
				}
				else
					tempMatrix[i]=FileToBlock("./2-"+i+".jpg");
			}
			//ת��Ϊ����
			int[][][] tempMemory=BlockToMatrix(tempMatrix);
			
			//ת�þ���(M-1)*(M+2)
			int[][][] dataCache = new int[count][M-1][M+2];

			System.out.print("���ԭ���ݣ�"+"\n");
		    display(tempMemory[0]);
			
			System.out.print("���ת�����ݣ�"+"\n");
			for(int i=0;i<count;i++)
			{
				dataCache[i]=getColumnData(tempMemory[i]);
			}
			display(dataCache[0]);
										
			//���б���
			int[] s=new int[count];
			int[][] tempMatrix1=new int[count][M-1];
			int[][] tempMatrix2=new int[count][M-1];
			for(int i=0;i<count;i++)
			{
				s[i]=getCommonFactor(dataCache[i]);
				tempMatrix1[i]=horiExclusive_OR(dataCache[i]);
				tempMatrix2[i]=diagExclusive_OR(dataCache[i],s[i]);
			}
			
			//�������������д�ض�ʧ����
			for(int k=0;k<count;k++)
			{
				for(int i=0;i<M-1;i++)
				{
					dataCache[k][i][error1]=tempMatrix1[k][i];
					dataCache[k][i][error2]=tempMatrix2[k][i];
				}
			}
			
			//�������Ľ�����ļ���ʽ�洢
			BlockToFile(MatrixToBlock(dataCache),error1);
			BlockToFile(MatrixToBlock(dataCache),error2);
		
			//����̨���				
			System.out.print("�޸��������\n");
			display(dataCache[0]);
		}
		else if((error1 >= 0 && error1 < m ) && error2 == m)
		{	
			//�����һ�����ݿ��ˮƽУ���,��ʱӦ�ô������У��ľ���
			
			//�Ӵ��̶�ȡ�ļ����
			int[][] tempMatrix = new int[M+2][length];
			for(int i=0;i<M+2;i++)
			{
				if(i==error1 || i==error2)
				{
//					for(int b=0;i<BLOCK_SIZE;i++)			
//					{
//						 tempMatrix[error1][b]=0;
//					}
				}
				else
					tempMatrix[i]=FileToBlock("./2-"+i+".jpg");
			}
			//ת��Ϊ����
			
			int[][][] tempMemory=BlockToMatrix(tempMatrix);
			System.out.print("���ԭ���ݣ�"+"\n");
		    display(tempMemory[0]);
			
			//ת�þ���(M-1)*(M+2)
			int[][][] dataCache = new int[count][M-1][M+2];		
			for(int i=0;i<count;i++)
			{
				dataCache[i]=getColumnData(tempMemory[i]);
			}
			System.out.print("���ת�����ݣ�"+"\n");
			display(dataCache[0]);
			
			//����һ��Ԫ��ȫΪ0����
			int[][][] temp=new int[count][m][m+2];
			for(int c=0;c<count;c++)
			{
				temp[c]=addRow(dataCache[c],temp[c]);
			}
	
			//��������
			int[] s=new int[count];
			for(int c=0;c<count;c++)
			{
				s[c]=temp[c][getMod((error1-1),m)][m+1];
				for(int j=0;j<m;j++)
				{
					s[c]=s[c]^temp[c][getMod((error1-j-1),m)][j];
				}
			}
		
			//�ָ�error1,��ʽ�����޷��ָ�,�޸Ĺ�ʽ�󣬿��Իָ�/////////////////////////////////////////////////////////////////////////////		
			for(int c=0;c<count;c++)
			{
				for(int k=0;k<temp[c].length-1;k++)
				{
					temp[c][k][error1]=s[c]^temp[c][getMod(error1+k,m)][m+1];
					for(int l=0;l<temp[c].length;l++)
					{	
						if(l!=error1)
						{		
							temp[c][k][error1]=temp[c][k][error1]^temp[c][getMod(k+error1-l,m)][l];
					    }	
					}
					dataCache[c][k][error1]=temp[c][k][error1];
				}		
			}
			//�����ļ���ʽ���
			BlockToFile(MatrixToBlock(dataCache),error1);
			
			//�ָ�error2,��ˮƽУ�鹫ʽ	
//			//ȥ��У���
//			int[][][] tempArray=new int[count][m-1][m];
//			for(int c=0;c<count;c++)
//			{
//				for(int i=0;i<tempArray[c].length;i++)
//				{
//					for(int j=0;j<tempArray[c][i].length;j++)
//					{
//						tempArray[c][i][j]=temp[c][i][j];
//					}
//				}
//			}
			
			int[][] tempMatrix1=new int[count][];
			for(int c=0;c<count;c++)
			{
				tempMatrix1[c]=horiExclusive_OR(dataCache[c]);
			    for(int i=0;i<temp[c].length-1;i++)
				    {				
				    	dataCache[c][i][error2]=tempMatrix1[c][i];				    
				    }
			}
			
			//�����ļ���ʽ��ʾ
			BlockToFile(MatrixToBlock(dataCache),error2);
				
		    //����̨���
		    System.out.print("�޸��������\n");
		    display(dataCache[0]);
			
		}
		//�����һ�����ݿ�ͶԽ���У���
		else if((error1 >= 0 && error1 < m) && error2 == (m+1) )
		{
//			//�ƻ�����,�˴�����0/////////////////////////////////////////////////
//			Destroy(error1,error2,dataCache);
//			
//			//�ƻ��������
//			System.out.print("�ƻ��������\n");
//			display(dataCache[0]);
//			
//			//����ˮƽУ�鹫ʽ�ָ�error1
//			for(int c=0;c<count;c++)
//			{
//				for(int i=0;i<dataCache[c].length;i++)
//				{
//					dataCache[c][i][error1]=0;
//					for(int j=0;j<dataCache[c][i].length-1;j++)
//					{
//						if(j != error1)
//						  dataCache[c][i][error1]=dataCache[c][i][error1]^dataCache[c][i][j];
//					}
//				}	
//			}
//			
//			//���ݶԽ��߹�ʽ�ָ�error2
//			//ȥ��У���
//			int[][][] tempArray=new int[count][m-1][m];
//			for(int c=0;c<count;c++)
//			{
//				for(int i=0;i<tempArray[c].length;i++)
//				{
//					for(int j=0;j<tempArray[c][i].length;j++)
//					{
//						tempArray[c][i][j]=dataCache[c][i][j];
//					}
//				}	
//			}
//			
//			int[] s=new int[count];
//			int[][] tempMatrix2=new int[count][];
//			for(int c=0;c<count;c++)
//			{
//				s[c]=getCommonFactor(tempArray[c]);
//				tempMatrix2[c]=diagExclusive_OR(tempArray[c],s[c]);
//				for(int i=0;i<tempMatrix2[c].length;i++)
//			    {
//			    	dataCache[c][i][error2]=tempMatrix2[c][i];
//			    }
//			}
//			
//			//���
//			 System.out.print("�޸��������\n");
//			 display(dataCache[0]);
			
		}
		//������������ݿ�
		else if((error1 >= 0 && error1 < m) && (error2 >= 0 && error2 < m))
		{
//			//�ƻ�����,�˴�����0/////////////////////////////////////////////////
//			Destroy(error1,error2,dataCache);
//			
//			//�ƻ��������
//			System.out.print("�ƻ��������\n");
//			display(dataCache[0]);
//			
//			//����һ��Ԫ��ȫΪ0����
//			int[][][] temp=new int[count][m][m+2];
//			for(int c=0;c<count;c++)
//			{
//				temp[c]=addRow(dataCache[c],temp[c]);
//			}		
//			//display(temp[0]);
//			
//			int[] s=new int[count];//��ż����
//			for(int c=0;c<count;c++)
//			{
//				s[c]=0;
//				for(int l=0;l<m-1;l++)
//				{
//					s[c]=s[c]^dataCache[c][l][m]^dataCache[c][l][m+1];
//				}
//			}		
//			//System.out.print(s[0]);
//			
//			
//			//Ѱ��ˮƽ�ۺ���
//			int[][] S0 = new int[count][m];
//			for(int c=0;c<count;c++)
//			{
//				for(int u=0;u<m;u++)
//				{
//					S0[c][u]=0;
//					for(int l=0;l<=m;l++)
//					{
//						if(l != error1 && l!= error2)
//							S0[c][u]=S0[c][u]^temp[c][u][l];
//					}
//				}	
//			}
//			
////			for(int i=0;i<S0[0].length;i++)
////			{
////				System.out.print(S0[0][i]+" ");
////			}
//			
//			int[][] S1=new int[count][m];//�Խ����ۺ���
//			for(int c=0;c<count;c++)
//			{
//				for(int u=0;u<m;u++)
//				{
//					S1[c][u]=s[c]^temp[c][u][m+1];
//					for(int l=0;l<m;l++)
//					{
//						if(l != error1 && l!= error2){	
//							S1[c][u]=S1[c][u]^temp[c][getMod(u-l,m)][l];
//						}
//					}
//				}
//			}
//			
////			for(int i=0;i<S1[0].length;i++)
////			{
////				System.out.print(S1[0][i]);
////			}
//			
//			//ͨ���������
//			int temp_s;
//			for(int c=0;c<count;c++)
//			{
//				s[c]=getMod((-(error2-error1)-1),m);
//				temp_s=s[c];
//				while(temp_s!=(m-1))
//				{
//					temp[c][temp_s][error2]=S1[c][getMod(error2+temp_s,m)]^temp[c][getMod(temp_s+(error2-error1),m)][error1];
//					temp[c][temp_s][error1]=S0[c][temp_s]^temp[c][temp_s][error2];
//					dataCache[c][temp_s][error2]=temp[c][temp_s][error2];
//					dataCache[c][temp_s][error1]=temp[c][temp_s][error1];
//					temp_s=getMod(temp_s-(error2-error1),m);
//				}
//			}
//			
//			System.out.print("�޸��������\n");
//			display(dataCache[0]);
		}
	}
	//ֻ��һ�����ݿ����
	else if(error2 == -1 && error1 != -1)
	{
		
	}
	else
	{//�������ݿ鲻���ҵ�
		System.out.print("error:fail to find the error disk!!");
		System.exit(0);
	
    }
}
@SuppressWarnings("null")
public static void main(String[] args) throws IOException
	{
	    //split();//�ֿ�
	    encode();//����
	
		//����
		//��û���ȫ��dataCache������£���ע��dataCache���ļ����
		int error1 = 2,error2 = 5;//����λ��  //2
		//decode(error1,error2);
		
		//merge();
	}

}
