
public class evenodd {

	//	public static int getColumn(int[][] dataCache)//��ȡ����
//	{
//		int t=dataCache[0].length;
//		return t;
//		
//	}
//	public static int getRow(int[][] dataCache)//��ȡ����
//	{
//		int l=dataCache.length;
//		return l;	
//	}
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
	
    public static int getCommonFactor(int[][] dataCache)
	{
		int s=0;
		for(int i=0;i<dataCache.length;i++)
		{
			s=s^dataCache[dataCache.length-1-i][i];
		}
		return s;
	}
	public static int[] horiExclusive_OR(int[][] dataCache) {
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
	public static int[] diagExclusive_OR(int[][] dataCache, int commonFactor) {
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
	private static int[][] addRow(int[][] dataCache,int[][] temp) {//����һ��0��
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
	
	
	
	
	public static int[][] encode(int[][] tempMemory)//����
	{
		int dataCache[][] = null;//������ʽ�洢 {{1,0,1,1,0},{0,1,1,0,0},{1,1,0,0,0},{0,1,0,1,1}};
		int[] tempMatrix1=null,tempMatrix2=null;//��У��ͶԽ���У��
		int[][] temp=null;//�洢У�����ݺ���̷�
		int s;//��żУ�����
		
//		System.out.print("���ԭ���ݣ�"+"\n");
//		for(int i=0;i<tempMemory.length;i++)
//		{
//			for(int j=0;j<tempMemory[i].length;j++)
//			{
//				System.out.print(tempMemory[i][j]+" ");
//			}
//			System.out.print(" \n");
//		}
//		System.out.print("���ת�����ݣ�"+"\n");
		dataCache=getColumnData(tempMemory);
//		for(int i=0;i<dataCache.length;i++)
//		{
//			for(int j=0;j<dataCache[i].length;j++)
//			{
//				System.out.print(dataCache[i][j]+" ");
//			}
//			System.out.print(" \n");
//		}
		
		s=getCommonFactor(dataCache);
		//System.out.print(s);//�����s
		
		//��ȡˮƽУ��
		tempMatrix1=horiExclusive_OR(dataCache);
//		for(int i=0;i<tempMatrix1.length;i++){
//			System.out.print(tempMatrix1[i]);
//		}
		//��ȡ�Խ���У��
		tempMatrix2=diagExclusive_OR(dataCache,s);
//		for(int i=0;i<tempMatrix2.length;i++){
//			System.out.print(tempMatrix2[i]+" ");
//		}
		
		//�洢�õ���У����
		temp=matrixTransposition(tempMatrix1,tempMatrix2,dataCache);
//		for(int i=0;i<temp.length;i++)
//		{
//			for(int j=0;j<temp[i].length;j++)
//			{
//				System.out.print(temp[i][j]+" ");
//			}
//			System.out.print(" \n");
//		}
		return temp;
	}
	public static void decode(int error1,int error2,int[][] tempMemory)//����
	{
		
		if(error1 != -1 && error2 !=-1)////�������̳���
		{
			//int[][] tempMemory = null;//���д洢��ԭʼ����
			int[][] dataCache=null;
			int[] tempMatrix1=null,tempMatrix2=null;//��У��ͶԽ���У��
			int[][] temp=null;
			//�����������У���
			if(error1 == tempMemory.length-2 && error2 == tempMemory.length-1)
			{
				//�������ͬ�ڱ���
				encode(tempMemory);
			}
			//�����һ�����ݿ��ˮƽУ���,��ʱӦ�ô������У��ľ���
			else if((error1 >= 0 && error1 < tempMemory.length-2) && error2 == tempMemory.length-2)
			{
				dataCache=getColumnData(tempMemory);//���д洢
				//����һ��Ԫ��ȫΪ0����
				temp=addRow(dataCache,temp);
				//����s
				int s=0;
				int m=temp.length-2;
				s=temp[getMod((error1-1),m)][m+1];
				
				
				
				
				
				
				
			}
			//�����һ�����ݿ�ͶԽ���У���
			else if((error1 >= 0 && error1 < tempMemory.length-2) && error2 == tempMemory.length-1)
			{}
			//������������ݿ�
			else if((error1 >= 0 && error1 < tempMemory.length-2) && (error2 >= 0 && error2 < tempMemory.length-2))
			{}
			
			
			
		}
	}
	
	
	
	

	public static void main(String[] args)
	{
		int[][] tempMemory={{0,1,0,1},{0,1,1,1},{0,0,0,0},{1,0,0,1},{0,0,0,1}};//���տ�洢��ԭ����
//		encode(tempMemory);//����
		int error1 = 1,error2 = 5;//��������λ��
		int[][] dataCache=null;
		
		//����error1��error2��ֵ
		//decode(error1,error2,tempMemory);
		
		dataCache=encode(tempMemory);//�����Ĵ���
		
		//�ƻ�����,�˴�����0/////////////////////////////////////////////////
		for(int i=0;i<dataCache.length;i++)
		{
			dataCache[i][error1]=0;
			dataCache[i][error2]=0;
			//System.out.print(dataCache[i][error1]);
		}

		//�ƻ��������
//		for(int i=0;i<dataCache.length;i++)
//		{
//			for(int j=0;j<dataCache[i].length;j++)
//			{
//				System.out.print(dataCache[i][j]+" ");
//			} 
//			System.out.print("\n");
//		}
		
		int m=dataCache[0].length-2;
		int[][] temp=new int[m][m+2];
		//����һ��Ԫ��ȫΪ0����
		temp=addRow(dataCache,temp);
//		for(int i=0;i<temp.length;i++)
//		{
//			for(int j=0;j<temp[i].length;j++)
//			{
//				System.out.print(temp[i][j]+" ");
//			} 
//			System.out.print("\n");
//		}
		
		int s=temp[getMod((error1-1),m)][m+1];
		for(int j=0;j<m;j++)
		{
			s=s^temp[getMod((error1-j-1),m)][j];
		}
		//System.out.print(s);
		//�ָ�error1
		
		for(int i=0;i<=3;i++)
		{
			temp[i][error1]=s^temp[getMod(error1-1,m)][m+1];;
			System.out.print(temp[i][error1]);
			for(int l=0;l<temp.length;l++)
			{	
				if(l!=error1)
				{		
					System.out.print(temp[getMod(i+error1-l,m)][l]);
					temp[i][error1]=temp[i][error1]^temp[getMod(i+error1-l,m)][l];				
			    }	
			}
			System.out.print("\n");
		}
		for(int i=0;i<temp.length;i++)
		{
			for(int j=0;j<temp[i].length;j++)
			{
				System.out.print(temp[i][j]+" ");
			} 
			System.out.print("\n");
		}
//		
		
		
	}

	

}
