
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
					dataCache = super.getColumnData(corectObj,i);//��ö���Ԫ�ص�ÿһ�У����һ������
					tempMatrix1[i]=super.horiExclusive_OR(dataCache);//�޸����ݿ�error
					tempMatrix2[i]=super.diagExclusive_OR(dataCache,encoding.getCommonFactor(dataCache));//�޸����ݿ�error2
				}
				//���޸������ݴ������
				super.matrixTransposition(tempMatrix1,temp);
				tempMemory[error1].read(temp);
				super.matrixTransposition(tempMatrix2.temp);
				tempMemory[error2].read(temp);
			}
			else if(error1 >= 0 && error1 < tempMemory.length - 2) &&  error2 == tempMemory.length - 2){//ԭ���ݿ��ˮƽУ�����ݿ�����ݳ���
				int s=0;
				int m=tempMemory.length - 2;
				tempMatrix1 = new int[tempMemory[0].getRow()][tempMemory[0].getColumn()];
				int[][] tempCache = new int[tempMemory[0].getRow()][tempMemory.length];
				dataCache = new int[tempMemory[0].getRow()+1][tempMemory.length];//�洢����һ�е�����
				for(int i=0;i<tempMemory[0].getColumnData;i++){
					tempCache = super.getColumnData(tempMemory,i));//��ȡ����������ÿһ��Ԫ�ض�Ӧ�е�����
					dataCache=super.addRow(tempCache);//����һ��
					//���㹫������
					
				}	
			}
		}
		
		
	}

}
