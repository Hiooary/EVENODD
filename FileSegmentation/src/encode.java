import java.io.IOException;


public class encode extends fileMatrix{

/******
 * ����
 */	
@SuppressWarnings("unused")
public static int[][] encode(int[][] tempMemory)
{
	int count=tempMemory[0].length;
	//System.out.print(count);
	int dataCache[][] = null;           //������ʽ�洢 {{1,0,1,1,0},{0,1,1,0,0},{1,1,0,0,0},{0,1,0,1,1}};
	int[] tempMatrix1=null,tempMatrix2=null;//��У��ͶԽ���У��
	int[][] temp=null;//�洢У�����ݺ���̷�
	int s;//��żУ�����
	
	System.out.print("���ԭ���ݣ�"+"\n");
    display(tempMemory);
    
//	
	System.out.print("���ת�����ݣ�"+"\n");
	dataCache=getColumnData(tempMemory);
	display(dataCache);
	
	s=getCommonFactor(dataCache);
	//System.out.print(s);//�����s
	
	//��ȡˮƽУ��
	tempMatrix1=horiExclusive_OR(dataCache);
//	for(int i=0;i<tempMatrix1.length;i++){
//		System.out.print(tempMatrix1[i]);
//	}
	//��ȡ�Խ���У��
	tempMatrix2=diagExclusive_OR(dataCache,s);
//	for(int i=0;i<tempMatrix2.length;i++){
//		System.out.print(tempMatrix2[i]+" ");
//	}
	
	//�洢�õ���У����
	temp=matrixTransposition(tempMatrix1,tempMatrix2,dataCache);
	System.out.print("�洢�õ���У����\n");
	display(temp);
	return temp;
	

}
	
@SuppressWarnings("null")
public static void main(String[] args) throws IOException
	{
	    int[][] buffer=split();//�ֺõĿ�
	    
	    //System.out.print(buffer[0].length);
	    
	    int count=0;
	    int len=buffer[0].length;
	    if((len)%(M-1)!=0)
	    	count= len / (M-1) + 1;
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
	    
//	     String filePath="./1.jpg";
//	     int bufSize;
		//����
		encode(tempMemory[0]);
		//decode();
		//merge();
	}

}
