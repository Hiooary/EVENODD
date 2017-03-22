
/******************************
 *******�㷨��һЩ��������
 *******
 */  
public class evenodd {
	private static final int M = 5;//������Ϊ����
	
	public static int getMod(int a,int b)//�������ϵ�ģ����
	{
		if(a>=0)
		{
			return a%b;
		}
		else
			return (a+b)%b;
	}
	public static int[][] getColumnData(int[][] tempMemory){//�Ӷ��������л�ȡĳ�еķ���
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
    public static int getCommonFactor(int[][] dataCache)//��s
	{
		int s=0;
		int m=M;
		for(int i=1;i<m;i++)
		{
			s=s^dataCache[getMod(m-1-i, m)][i];
		}
		return s;
	}
	public static int[] horiExclusive_OR(int[][] dataCache) {//ˮƽУ��
		// TODO Auto-generated method stub
		int l=dataCache.length;		
		int horiExculsive[]=new int[l];
		for(int i=0;i<l;i++)
		{
			int temp=0;
			for(int j=0;j<dataCache[i].length;j++)
			{
				temp=temp^dataCache[i][j];
			}
			horiExculsive[i]=temp;
		}
		return horiExculsive;
	}
	public static int[] diagExclusive_OR(int[][] dataCache, int commonFactor) {//�Խ���У��
		// TODO Auto-generated method stub
		int l=dataCache.length;
		int diagExclusive[] = new int[l];
		for(int i=0;i<l;i++)
		{
			int temp=commonFactor;
			for(int j=0; j<dataCache[i].length;j++)
			{
				int t=getMod((i-j),dataCache[i].length);
				if(t>=l)
				{
					continue;
				}
				temp=temp^dataCache[t][j];
			}
			diagExclusive[i]=temp;
		}
		return diagExclusive;
	}
	public static int[][] matrixTransposition(int[] tempMatrix1,int[] tempMatrix2, int[][] dataCache) {//����õ�У�����洢
		// TODO Auto-generated method stub
		int m=dataCache[0].length;
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
	public static int[][] addRow(int[][] dataCache,int[][] temp) {//����һ��0��
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
	public static void display(int[][] temp)//��ʾ
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
 *�ƻ�����,�˴���Ϊ0
 */    
	public static int[][][] Destroy(int error1,int error2,int[][][] dataCache)
	{
		int count=dataCache.length;
		for(int k=0;k<count;k++)
		{
			for(int i=0;i<dataCache[0].length;i++)
			{
				dataCache[k][i][error1]=0;
				dataCache[k][i][error2]=0;
			}
		}
		return dataCache;
	}
	
	
	//	public static int[][] encode(int[][] tempMemory)//����
//	{
//		int dataCache[][] = null;//������ʽ�洢 {{1,0,1,1,0},{0,1,1,0,0},{1,1,0,0,0},{0,1,0,1,1}};
//		int[] tempMatrix1=null,tempMatrix2=null;//��У��ͶԽ���У��
//		int[][] temp=null;//�洢У�����ݺ���̷�
//		int s;//��żУ�����
//		
////		System.out.print("���ԭ���ݣ�"+"\n");
////      display(tempMemory);
//		
////		System.out.print("���ת�����ݣ�"+"\n");
//		dataCache=getColumnData(tempMemory);
//		//display(dataCache);
//		
//		s=getCommonFactor(dataCache);
//		//System.out.print(s);//�����s
//		
//		//��ȡˮƽУ��
//		tempMatrix1=horiExclusive_OR(dataCache);
////		for(int i=0;i<tempMatrix1.length;i++){
////			System.out.print(tempMatrix1[i]);
////		}
//		//��ȡ�Խ���У��
//		tempMatrix2=diagExclusive_OR(dataCache,s);
////		for(int i=0;i<tempMatrix2.length;i++){
////			System.out.print(tempMatrix2[i]+" ");
////		}
//		
//		//�洢�õ���У����
//		temp=matrixTransposition(tempMatrix1,tempMatrix2,dataCache);
//		System.out.print("�洢�õ���У����\n");
//		display(temp);
//		return temp;
//	}
//	public static void decode(int error1,int error2,int[][] dataCache)//����
//	{	
//		int m=dataCache[0].length-2;
//		if(error1 != -1 && error2 !=-1)////�������̳���
//		{
//			//�����������У���
//			if(error1 == dataCache[0].length-2 && error2 == dataCache[0].length-1)
//			{
//				//�������ͬ�ڱ���
//				//�ƻ�����,�˴�����0/////////////////////////////////////////////////
//				for(int i=0;i<dataCache.length;i++)
//				{
//					dataCache[i][error1]=0;
//					dataCache[i][error2]=0;
//				}
//				//�ƻ��������
//				System.out.print("�ƻ��������\n");
//				display(dataCache);
//				
//				int[][] tempArray=new int[m-1][m];
//				for(int i=0;i<tempArray.length;i++)
//				{
//					for(int j=0;j<tempArray[i].length;j++)
//					{
//						tempArray[i][j]=dataCache[i][j];
//					}
//				}
//				
//				//���б���
//				int s=getCommonFactor(tempArray);
//				int[] tempMatrix1=horiExclusive_OR(tempArray);
//				int[] tempMatrix2=diagExclusive_OR(tempArray,s);
//				
//				for(int i=0;i<dataCache.length;i++)
//				{
//					dataCache[i][m]=tempMatrix1[i];
//					dataCache[i][m+1]=tempMatrix2[i];
//				}
//				//���				
//				System.out.print("�޸��������\n");
//				display(dataCache);
//			}
//			//�����һ�����ݿ��ˮƽУ���,��ʱӦ�ô������У��ľ���
//			else if((error1 >= 0 && error1 < dataCache[0].length-2) && error2 == dataCache[0].length-2)
//			{				
//				//�ƻ�����,�˴�����0/////////////////////////////////////////////////
//				for(int i=0;i<dataCache.length;i++)
//				{
//					dataCache[i][error1]=0;
//					dataCache[i][error2]=0;
//				}
//				//�ƻ��������
//				System.out.print("�ƻ��������\n");
//				display(dataCache);
//				
//				int[][] temp=new int[m][m+2];
//				//����һ��Ԫ��ȫΪ0����
//				temp=addRow(dataCache,temp);
////				display(temp);
//				
//				int s=temp[getMod((error1-1),m)][m+1];
//				for(int j=0;j<m;j++)
//				{
//					s=s^temp[getMod((error1-j-1),m)][j];
//				}
//				//System.out.print(s);
//				//�ָ�error1,��ʽ�����޷��ָ�,�޸Ĺ�ʽ�󣬿��Իָ�/////////////////////////////////////////////////////////////////////////////
//				for(int k=0;k<temp.length-1;k++)
//				{
//					temp[k][error1]=s^temp[getMod(error1+k,m)][m+1];
//					for(int l=0;l<temp.length;l++)
//					{	
//						if(l!=error1)
//						{		
//							temp[k][error1]=temp[k][error1]^temp[getMod(k+error1-l,m)][l];	
//							dataCache[k][error1]=temp[k][error1];
//					    }	
//					}
//				}			
//				//�ָ�error2,��ˮƽУ�鹫ʽ
//				int[][] tempArray=new int[m-1][m];
//				for(int i=0;i<tempArray.length;i++)
//				{
//					for(int j=0;j<tempArray[i].length;j++)
//					{
//						tempArray[i][j]=temp[i][j];
//					}
//				}
//			    int[] tempMatrix1=horiExclusive_OR(tempArray);
//			    for(int i=0;i<temp.length-1;i++)
//			    {
//			    	//temp[i][error2]=tempMatrix1[i];
//			    	dataCache[i][error2]=tempMatrix1[i];
//			    }
//			    //���
//			    System.out.print("�޸��������\n");
//			    display(dataCache);
//				
//			}
//			//�����һ�����ݿ�ͶԽ���У���
//			else if((error1 >= 0 && error1 < dataCache[0].length-2) && error2 == dataCache[0].length-1)
//			{
//				//�ƻ�����,�˴�����0/////////////////////////////////////////////////
//				for(int i=0;i<dataCache.length;i++)
//				{
//					dataCache[i][error1]=0;
//					dataCache[i][error2]=0;
//				}
//				//�ƻ��������
//				System.out.print("�ƻ��������\n");
//				display(dataCache);
//				//����ˮƽУ�鹫ʽ�ָ�error1
//				for(int i=0;i<dataCache.length;i++)
//				{
//					dataCache[i][error1]=0;
//					for(int j=0;j<dataCache[i].length-1;j++)
//					{
//						if(j != error1)
//						  dataCache[i][error1]=dataCache[i][error1]^dataCache[i][j];
//					}
//				}
//				//���ݶԽ��߹�ʽ�ָ�error2
//				int[][] tempArray=new int[m-1][m];
//				for(int i=0;i<tempArray.length;i++)
//				{
//					for(int j=0;j<tempArray[i].length;j++)
//					{
//						tempArray[i][j]=dataCache[i][j];
//					}
//				}
//				int s=getCommonFactor(tempArray);
//				int[] tempMatrix2=diagExclusive_OR(tempArray,s);
//				 for(int i=0;i<tempMatrix2.length;i++)
//				    {
//				    	//temp[i][error2]=tempMatrix1[i];
//				    	dataCache[i][error2]=tempMatrix2[i];
//				    }
//				//���
//				 System.out.print("�޸��������\n");
//				 display(dataCache);
//				
//			}
//			//������������ݿ�
//			else if((error1 >= 0 && error1 < dataCache[0].length-2) && (error2 >= 0 && error2 < dataCache[0].length-2))
//			{
//				//�ƻ�����,�˴�����0/////////////////////////////////////////////////
//				for(int i=0;i<dataCache.length;i++)
//				{
//					dataCache[i][error1]=0;
//					dataCache[i][error2]=0;
//				}
//				//�ƻ��������
//				System.out.print("�ƻ��������\n");
//				display(dataCache);
//				
//				//����0��
//				int[][] temp=new int[m][m+2];
//				//����һ��Ԫ��ȫΪ0����
//				temp=addRow(dataCache,temp);
//				int s=0;//��ż����
//				for(int l=0;l<m-1;l++)
//				{
//					s=s^dataCache[l][m]^dataCache[l][m+1];
//				}
//				//System.out.print(s);
//				//Ѱ��ˮƽ�ۺ���
//				int[] S0 = new int[m];
//				for(int u=0;u<m;u++)
//				{
//					S0[u]=0;
//					for(int l=0;l<=m;l++)
//					{
//						if(l != error1 && l!= error2)
//							S0[u]=S0[u]^temp[u][l];
//					}
//				}
////				for(int i=0;i<S0.length;i++)
////				{
////					System.out.print(S0[i]);
////				}
//				int[] S1=new int[m];//�Խ����ۺ���
//				for(int u=0;u<m;u++)
//				{
//					S1[u]=s^temp[u][m+1];
//					for(int l=0;l<m;l++)
//					{
//						if(l != error1 && l!= error2){	
//							S1[u]=S1[u]^temp[getMod(u-l,m)][l];
//						}
//					}
//				}
////				for(int i=0;i<S1.length;i++)
////				{
////					System.out.print(S1[i]);
////				}
//				//ͨ���������
//				s=getMod((-(error2-error1)-1),m);
//				while(s!=m-1)
//				{
//					temp[s][error2]=S1[getMod(error2+s,m)]^temp[getMod(s+(error2-error1),m)][error1];
//					dataCache[s][error2]=temp[s][error2];
//					temp[s][error1]=S0[s]^temp[s][error2];
//					dataCache[s][error1]=temp[s][error1];
//					s=getMod(s-(error2-error1),m);
//				}
//				
//				System.out.print("�޸��������\n");
//				display(dataCache);
//			}
//		}
//		//ֻ��һ�����ݿ����
//		else if(error2 == -1 && error1 != -1)
//		{
//			
//		}
//		else
//		{//�������ݿ鲻���ҵ�
//			System.out.print("error:fail to find the error disk!!");
//			System.exit(0);
//		
//	    }
//	}
		
//	public static void main(String[] args)
//	{
//		int[][] tempMemory={{0,1,0,1},{0,1,1,1},{0,0,0,0},{1,0,0,1},{0,0,0,1}};//���տ�洢��ԭ����
//		
////		encode(tempMemory);//����
//		
//		int error1 = -1,error2 = -1;//��������λ��
//		//����error1��error2��ֵ
//		int[][] dataCache=null;
//		dataCache=encode(tempMemory);//�����Ĵ���
//		decode(error1,error2,dataCache);
//
//	}

}
