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
@SuppressWarnings("unused")
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
    System.out.print(s[0]+"\n");//�����s
//	
//	//��ȡˮƽУ��
//	tempMatrix1=horiExclusive_OR(dataCache);
	for(int i=0;i<tempMatrix1[0].length;i++)
	{
		System.out.print(tempMatrix1[0][i]+" ");
	}
	System.out.print("\n");
//	//��ȡ�Խ���У��
	//tempMatrix2=diagExclusive_OR(dataCache,s);
	for(int i=0;i<tempMatrix2[0].length;i++){
		System.out.print(tempMatrix2[0][i]+" ");
	}
	System.out.print("\n");
//	
//	//�洢�õ���У����
//	temp=matrixTransposition(tempMatrix1,tempMatrix2,dataCache);
	System.out.print("�洢�õ���У����\n");
	display(temp[0]);
	
	//�������Ľ�����ļ���ʽ�洢
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
	    
//	     String filePath="./1.jpg";
//	     int bufSize;
		//����
		encode(tempMemory);
		
		
		
		
		//decode();
		//merge();
	}

}
