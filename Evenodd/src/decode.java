
public class decode {
	
	
	protected void decode(){
		if(error1 != -1 && errer2 != -1){//���������ݿ����
			int [][] dataCache,tempMatrix1,tempMatrix2;
			pictureInComp[] corectObj;//�ݴ���ȷ���ݿ������
			/****/ Object[] tempMemory;/****/
			tempMatrix1=new int[tempMemory[0].getColumn()][tempMemory.getRow()];//�ݴ����������ˮƽУ��λ������ֵ��ת������
			tempMatrix2=new int[tempMemory[0].getColumn()][tempMemory.getRow()];//�ݴ���������жԽ���У��λ������ֵ��ת������
			int[][] temp = new int[tempMemory[0].getRow()][tempMemory.getColumn()];//�䵱����ת��ʱ����ʱ����
			if(error1 == tempMemory.length - 2 && error2 == tempMemory.length - 1){//����У�����ݿ����
				corectObj=new pictureInComp[tempMemory.length - 2 ];
				System.arraycopy(tempMemory0, 0, corectObj,0,tempMemory.length-2);//����ȷ�Ķ������鿽����corectObj��
				dataCache = new int[tempMemory[0].getRow()][corectOnj.length];
				for(int i=0;i<corectObj.length;i++)
				{
					dataCache = super.getColumnData(corectObj,i);.
				}
			}
		}
		
		
	}

}
