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
	byte[][] tempMatrix = new byte[M][(int) DISK_SIZE];
	for(int i=0;i<M;i++)
	{
		tempMatrix[i]=FileToBlock("./2-"+i+".jpg");
	}
	//display(tempMatrix);
	
	byte[][][] tempMemory=BlockToMatrix(tempMatrix);
	System.out.print("���ԭ���ݣ�"+"\n");
	display(tempMemory[0]);
	
	byte[][][] dataCache = new byte[count][M-1][M];//ת�þ���4*5
	
	System.out.print("���ת�����ݣ�"+"\n");
	for(int i=0;i<count;i++)
	{
		dataCache[i]=getColumnData(tempMemory[i]);
	}
	display(dataCache[0]);

	
	byte[][] tempMatrix1=new byte[count][M-1];//��У��
	byte[][]tempMatrix2=new byte[count][M-1];//�Խ���У��
	byte[][][] temp=new byte[count][M-1][M+2];//�洢У�����ݺ���̷�
	byte s[] = new byte[count];//��żУ�����
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

	System.out.print("�洢�õ���У����\n");
	display(temp[0]);
	
	//�������Ľ�����ļ���ʽ�洢
	BlockToFile(MatrixToBlock(temp,M),M);
	BlockToFile(MatrixToBlock(temp,M+1),M+1);

}

/******
 * ����
 * @throws IOException 
 */		
public static byte[][][] decode(int error1,int error2) throws IOException
{
	int m=M;	
	if(error1 != -1 && error2 !=-1)////�������̳���
	{
		//�����������У���,�������ͬ�ڱ���
		if(error1 == m && error2 == (m+1))
		{
			//�Ӵ��̶�ȡ�ļ����
			byte[][] tempMatrix = new byte[M+2][(int) DISK_SIZE];
			for(int i=0;i<M+2;i++)
			{
				if(i==error1 || i==error2)//��ʧ�Ŀ鲹0
				{}
				else
					tempMatrix[i]=FileToBlock("./2-"+i+".jpg");
			}
			
			
			//ת��Ϊ����
			byte[][][] tempMemory=BlockToMatrix(tempMatrix);
			System.out.print("���ԭ���ݣ�"+"\n");
		    display(tempMemory[0]);
		    
			//ת�þ���(M-1)*(M+2)
			byte[][][] dataCache = new byte[count][M-1][M+2];
			System.out.print("���ת�����ݣ�"+"\n");
			for(int i=0;i<count;i++)
			{
				dataCache[i]=getColumnData(tempMemory[i]);
			}
			display(dataCache[0]);
					
			//���б���
			byte[] s=new byte[count];
			byte[][] tempMatrix1=new byte[count][M-1];
			byte[][] tempMatrix2=new byte[count][M-1];
			byte[][][] temp=new byte[count][M-1][M+2];//�洢У�����ݺ���̷�	
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
			BlockToFile(MatrixToBlock(dataCache,error1),error1);
			BlockToFile(MatrixToBlock(dataCache,error2),error2);
		
			//����̨���				
			System.out.print("�޸��������\n");
			display(dataCache[0]);
		}
		else if((error1 >= 0 && error1 < m ) && error2 == m)
		{	
			//�����һ�����ݿ��ˮƽУ���
			
			//�Ӵ��̶�ȡ�ļ����
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
			System.out.print("���ԭ���ݣ�"+"\n");
		    display(tempMemory[0]);
		   
			
			//ת�þ���(M-1)*(M+2)
		    byte[][][] dataCache = new byte[count][M-1][M+2];		
			for(int i=0;i<count;i++)
			{
				dataCache[i]=getColumnData(tempMemory[i]);
			}
			System.out.print("���ת�����ݣ�"+"\n");
			display(dataCache[0]);
			
			//����һ��Ԫ��ȫΪ0����
			byte[][][] temp=new byte[count][m][m+2];
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
			//�����ļ���ʽ���
			BlockToFile(MatrixToBlock(dataCache,error1),error1);
			
			//�ָ�error2
			byte[][] tempMatrix1=new byte[count][];
			for(int c=0;c<count;c++)
			{
				tempMatrix1[c]=horiExclusive_OR(dataCache[c]);
			    for(int i=0;i<M-1;i++)
				    {				
				    	dataCache[c][i][error2]=tempMatrix1[c][i];				    
				    }
			}
			//�����ļ���ʽ��ʾ
			BlockToFile(MatrixToBlock(dataCache,error2),error2);
				
		    //����̨���
		    System.out.print("�޸��������\n");
		    display(dataCache[0]);
		    
		    return dataCache;
			
		}
		//�����һ�����ݿ�ͶԽ���У���
		else if((error1 >= 0 && error1 < m) && error2 == (m+1) )
		{
			//�Ӵ��̶�ȡ�ļ����
			byte[][] tempMatrix = new byte[M+2][(int) DISK_SIZE];
			for(int i=0;i<M+2;i++)
			{
				if(i==error1 || i==error2)
				{
					
				}
				else
					tempMatrix[i]=FileToBlock("./2-"+i+".jpg");
			}
			
			//ת��Ϊ����			
			byte[][][] tempMemory=BlockToMatrix(tempMatrix);
			System.out.print("���ԭ���ݣ�"+"\n");
		    display(tempMemory[0]);
			
			//ת�þ���(M-1)*(M+2)
		    byte[][][] dataCache = new byte[count][M-1][M+2];		
			for(int i=0;i<count;i++)
			{
				dataCache[i]=getColumnData(tempMemory[i]);
			}
			System.out.print("���ת�����ݣ�"+"\n");
			display(dataCache[0]);
			
			//����ˮƽУ�鹫ʽ�ָ�error1
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
			
			//���ݶԽ��߹�ʽ�ָ�error2
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
			
			//���
			 System.out.print("�޸��������\n");
			 display(dataCache[0]);
			 
			 BlockToFile(MatrixToBlock(dataCache,error1),error1);
			BlockToFile(MatrixToBlock(dataCache,error2),error2);
			
		}
		//������������ݿ�
		else if((error1 >= 0 && error1 < m) && (error2 >= 0 && error2 < m))
		{
			//�Ӵ��̶�ȡ�ļ����
			byte[][] tempMatrix = new byte[M+2][(int) DISK_SIZE];
			for(int i=0;i<M+2;i++)
			{
				if(i==error1 || i==error2)
				{
					
				}
				else
					tempMatrix[i]=FileToBlock("./2-"+i+".jpg");
			}
			
			//ת��Ϊ����			
			byte[][][] tempMemory=BlockToMatrix(tempMatrix);
			System.out.print("���ԭ���ݣ�"+"\n");
		    display(tempMemory[0]);
			
			//ת�þ���(M-1)*(M+2)
			byte[][][] dataCache = new byte[count][M-1][M+2];		
			for(int i=0;i<count;i++)
			{
				dataCache[i]=getColumnData(tempMemory[i]);
			}
			System.out.print("���ת�����ݣ�"+"\n");
			display(dataCache[0]);
			
			//����һ��Ԫ��ȫΪ0����
			byte[][][] temp=new byte[count][m][m+2];
			for(int c=0;c<count;c++)
			{
				temp[c]=addRow(dataCache[c],temp[c]);
			}		
			//display(temp[0]);
			
			int[] s=new int[count];//��ż����
			for(int c=0;c<count;c++)
			{
				s[c]=0;
				for(int l=0;l<m-1;l++)
				{
					s[c]=s[c]^dataCache[c][l][m]^dataCache[c][l][m+1];
				}
			}		
			//System.out.print(s[0]);
			
			
			//Ѱ��ˮƽ�ۺ���
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
			
			int[][] S1=new int[count][m];//�Խ����ۺ���
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
			
			//ͨ���������
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
			
			System.out.print("�޸��������\n");
			display(dataCache[0]);
			
			 BlockToFile(MatrixToBlock(dataCache,error1),error1);
			 BlockToFile(MatrixToBlock(dataCache,error2),error2);
				
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
	return null;
}

public static void main(String[] args) throws IOException
	{
	    //��ȡ�ļ��鳤��
		String filePath="./1.jpg";		
		length=(int) getFileLength(filePath);
		if((length % M)!=0)
			DISK_SIZE = (length / M) + 1;//ȡ����
		else
			DISK_SIZE = length / M;
//	    System.out.print(length);//40875
//	    System.out.print(DISK_SIZE);//8175
//	
//		split();//�ֿ�   
//		encode();//����
		int error1 = 2,error2 = 6;//����λ��       //56/                   /25/35/01/02/03/04/14/34/23
        decode(error1,error2);//����
		merge();//�ϲ��ļ���
		
//        System.out.print((-1)^(-1)^(72)^(22)^(-61));//��һ��
//        System.out.print((-2)^(-26)^(7)^(-61));//s
//        System.out.print((-2)^(-26)^(7)^(-61)^(-1)^(105)^(33)^(43));//�Խ���
	}

}
