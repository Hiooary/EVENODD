import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class encode extends fileMatrix{
	public static int LEN;

/******
 * ����
 * @throws IOException 
 */	
public static int[][][] encode(int[][][] tempMemory) throws IOException
{
	int count=tempMemory.length;
	//System.out.print(count);
	
	int[][][] dataCache = new int[count][M-1][M];           //������ʽ�洢 {{1,0,1,1,0},{0,1,1,0,0},{1,1,0,0,0},{0,1,0,1,1}};
	int[][] tempMatrix1=new int[count][M-1];
	int[][]tempMatrix2=new int[count][M-1];//��У��ͶԽ���У��
	int[][][] temp=new int[count][M-1][M+2];//�洢У�����ݺ���̷�
	int s[] = new int[count];//��żУ�����
//	
	System.out.print("���ԭ���ݣ�"+"\n");
    display(tempMemory[0]);
//    
////	
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

	
//	s=getCommonFactor(dataCache);
//    System.out.print(s[0]+"\n");//�����s
//	
//	//��ȡˮƽУ��
//	tempMatrix1=horiExclusive_OR(dataCache);
//	for(int i=0;i<tempMatrix1[0].length;i++)
//	{
//		System.out.print(tempMatrix1[0][i]+" ");
//	}
//	System.out.print("\n");
//	//��ȡ�Խ���У��
	//tempMatrix2=diagExclusive_OR(dataCache,s);
//	for(int i=0;i<tempMatrix2[0].length;i++){
//		System.out.print(tempMatrix2[0][i]+" ");
//	}
//	System.out.print("\n");
//	
//	//�洢�õ���У����
//	temp=matrixTransposition(tempMatrix1,tempMatrix2,dataCache);
	System.out.print("�洢�õ���У����\n");
	display(temp[0]);
	
//	//�������Ľ�����ļ���ʽ�洢
//	DataOutputStream fpw1=new DataOutputStream(new FileOutputStream("./2-"+M+".jpg"));
//	DataOutputStream fpw2=new DataOutputStream(new FileOutputStream("./2-"+(M+1)+".jpg"));
//	
////	int len=0;
////	while(len <= LEN)
////	{
//		for(int i=0;i<tempMatrix1.length;i++)
//		{
//			for(int j=0;j<tempMatrix1[0].length;j++)
//			{
//				fpw1.writeByte(tempMatrix1[i][j]);
//				fpw2.writeByte(tempMatrix2[i][j]);
//			//	len++;
//			}	
//		}	
//	//}
//		fpw1.close();
//		fpw2.close();
		
	return temp;
}

/******
 * ����
 * @throws IOException 
 */		
public static void decode(int error1,int error2,int[][][] dataCache) throws IOException
{
	int m=M;
	int count=dataCache.length;
	
	if(error1 != -1 && error2 !=-1)////�������̳���
	{
		//�����������У���
		if(error1 == m && error2 == (m+1))
		{
			//�������ͬ�ڱ���
			//�ƻ�����,�˴�����0/////////////////////////////////////////////////
			Destroy(error1,error2,dataCache);
//			for(int k=0;k<count;k++)
//			{
//				for(int i=0;i<dataCache[0].length;i++)
//				{
//					dataCache[k][i][error1]=0;
//					dataCache[k][i][error2]=0;
//				}
//			}
			
			//�ƻ��������
			System.out.print("�ƻ��������\n");
			display(dataCache[0]);
			
			//ȥ��У���
			int[][][] tempArray=new int[count][m-1][m];
			for(int k=0;k<count;k++)
			{
				for(int i=0;i<(m-1);i++)
				{
					for(int j=0;j<m;j++)
					{
						tempArray[k][i][j]=dataCache[k][i][j];
					}
				}
			}
			
			
			//���б���
			int[] s=new int[count];
			int[][] tempMatrix1=new int[count][M-1];
			int[][] tempMatrix2=new int[count][M-1];
			for(int i=0;i<count;i++)
			{
				s[i]=getCommonFactor(tempArray[i]);
				tempMatrix1[i]=horiExclusive_OR(tempArray[i]);
				tempMatrix2[i]=diagExclusive_OR(tempArray[i],s[i]);
			}
			
			//�������������д�ض�ʧ����
			for(int k=0;k<count;k++)
			{
				for(int i=0;i<dataCache[0].length;i++)
				{
					dataCache[k][i][error1]=tempMatrix1[k][i];
					dataCache[k][i][error2]=tempMatrix2[k][i];
				}
			}
			
			//�������Ľ�����ļ���ʽ�洢
			DataOutputStream fpw1=new DataOutputStream(new FileOutputStream("./2-"+error1+".jpg"));
			DataOutputStream fpw2=new DataOutputStream(new FileOutputStream("./2-"+error2+".jpg"));
			for(int i=0;i<tempMatrix1.length;i++)
				{
					for(int j=0;j<tempMatrix1[0].length;j++)
					{
						fpw1.writeByte(tempMatrix1[i][j]);
						fpw2.writeByte(tempMatrix2[i][j]);
					}	
				}	
			fpw1.close();
			fpw2.close();
			
			//����̨���				
			System.out.print("�޸��������\n");
			display(dataCache[0]);
		}
		//�����һ�����ݿ��ˮƽУ���,��ʱӦ�ô������У��ľ���
		else if((error1 >= 0 && error1 < m ) && error2 == m)
		{				
			//�ƻ�����,�˴�����0������д��һ������/////////////////////////////////////////////////
			Destroy(error1,error2,dataCache);
			//�ƻ��������
			System.out.print("�ƻ��������\n");
			display(dataCache[0]);
			
			//����һ��Ԫ��ȫΪ0����,���������s
			int[][][] temp=new int[count][m][m+2];
			int[] s=new int[count];
			for(int c=0;c<count;c++)
			{
				temp[c]=addRow(dataCache[c],temp[c]);
				s[c]=temp[c][getMod((error1-1),m)][m+1];
				for(int j=0;j<m;j++)
				{
					s[c]=s[c]^temp[c][getMod((error1-j-1),m)][j];
				}
			}
//			display(temp[0]);
//			System.out.print(s[0]);
			
			//�ָ�error1,��ʽ�����޷��ָ�,�޸Ĺ�ʽ�󣬿��Իָ�/////////////////////////////////////////////////////////////////////////////
			//�����ļ���ʽ���
			DataOutputStream fpw1=new DataOutputStream(new FileOutputStream("./2-"+error1+".jpg"));
			
			for(int c=0;c < count;c++)
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
					fpw1.writeByte(dataCache[c][k][error1]);
				}		
			}
			fpw1.close();
				
			//�ָ�error2,��ˮƽУ�鹫ʽ
			//�����ļ���ʽ��ʾ
			
			DataOutputStream fpw2=new DataOutputStream(new FileOutputStream("./2-"+error2+".jpg"));
			
			//ȥ��У���
			int[][][] tempArray=new int[count][m-1][m];
			for(int c=0;c<count;c++)
			{
				for(int i=0;i<tempArray[c].length;i++)
				{
					for(int j=0;j<tempArray[c][i].length;j++)
					{
						tempArray[c][i][j]=temp[c][i][j];
					}
				}
			}
			
			int[][] tempMatrix1=new int[count][];
			for(int c=0;c<count;c++)
			{
				tempMatrix1[c]=horiExclusive_OR(tempArray[c]);
			    for(int i=0;i<temp[c].length-1;i++)
				    {
				    	//temp[i][error2]=tempMatrix1[i];
				    	dataCache[c][i][error2]=tempMatrix1[c][i];
				    	fpw2.writeByte(tempMatrix1[c][i]);
				    }
			}
		    
			fpw2.close();
			
		    //����̨���
		    System.out.print("�޸��������\n");
		    display(dataCache[0]);
			
		}
		//�����һ�����ݿ�ͶԽ���У���
		else if((error1 >= 0 && error1 < m) && error2 == (m+1) )
		{
			//�ƻ�����,�˴�����0/////////////////////////////////////////////////
			Destroy(error1,error2,dataCache);
			
			//�ƻ��������
			System.out.print("�ƻ��������\n");
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
						  dataCache[c][i][error1]=dataCache[c][i][error1]^dataCache[c][i][j];
					}
				}	
			}
			
			//���ݶԽ��߹�ʽ�ָ�error2
			//ȥ��У���
			int[][][] tempArray=new int[count][m-1][m];
			for(int c=0;c<count;c++)
			{
				for(int i=0;i<tempArray[c].length;i++)
				{
					for(int j=0;j<tempArray[c][i].length;j++)
					{
						tempArray[c][i][j]=dataCache[c][i][j];
					}
				}	
				
			}
			
			int[] s=new int[count];
			int[][] tempMatrix2=new int[count][];
			for(int c=0;c<count;c++)
			{
				s[c]=getCommonFactor(tempArray[c]);
				tempMatrix2[c]=diagExclusive_OR(tempArray[c],s[c]);
				for(int i=0;i<tempMatrix2[c].length;i++)
			    {
			    	dataCache[c][i][error2]=tempMatrix2[c][i];
			    }
			}
			
			//���
			 System.out.print("�޸��������\n");
			 display(dataCache[0]);
			
		}
		//������������ݿ�
		else if((error1 >= 0 && error1 < m) && (error2 >= 0 && error2 < m))
		{
			//�ƻ�����,�˴�����0/////////////////////////////////////////////////
			Destroy(error1,error2,dataCache);
			
			//�ƻ��������
			System.out.print("�ƻ��������\n");
			display(dataCache[0]);
			
			//����һ��Ԫ��ȫΪ0����
			int[][][] temp=new int[count][m][m+2];
			int[] s=new int[count];//��ż����
			
			for(int c=0;c<count;c++)
			{
				temp[c]=addRow(dataCache[c],temp[c]);
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
			
//			for(int i=0;i<S0.length;i++)
//			{
//				System.out.print(S0[i]);
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
			
//			for(int i=0;i<S1.length;i++)
//			{
//				System.out.print(S1[0][i]);
//			}
			
			//ͨ���������
			for(int c=0;c<count;c++)
			{
				s[c]=getMod((-(error2-error1)-1),m);
				while(s[c]!=m-1)
				{
					temp[c][(s[c])][error2]=S1[c][getMod(error2+s[c],m)]^temp[c][getMod(s[c]+(error2-error1),m)][error1];
					dataCache[(s[c])][error1]=temp[(s[c])][error2];
					temp[c][(s[c])][error1]=S0[c][(s[c])]^temp[c][(s[c])][error2];
					dataCache[c][(s[c])][error1]=temp[c][(s[c])][error1];
					s[c]=getMod(s[c]-(error2-error1),m);
				}
			}
			
			
			System.out.print("�޸��������\n");
			display(dataCache[0]);
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
	    int[][] buffer=split();//�ֺõĿ�
	    
	    //System.out.print(buffer[0].length);
	    
	    int count=0;
	    int LEN=buffer[0].length;
	    if((LEN)%(M-1)!=0)
	    	count= LEN / (M-1) + 1;
	    //System.out.print(count);
	    
	    int[][][] tempMemory=new int[count][M][M-1];//�����ݻ���Ϊ����
	    int t=0;
		for(int k=0;k < count;k++)
		{
		    for(int i=0;i<M;i++)
		    {
		    	for(int j=0;j<M-1;j++)
		    	{
		    		//System.out.print(buffer[i][t]);
		    		//������һ������������
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
	    
		//���
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
		////����
		int[][][] dataCache;//���ձ����ľ���, Ӧ������Ϊ���б�������������뻹������
		dataCache=encode(tempMemory);
		
		//����
		//��û���ȫ��dataCache������£���ע��dataCache���ļ����
		int error1 = 1,error2 = 2;//����λ��
		decode(error1,error2,dataCache);
//		
		//decode();
		//merge();
	}

}
