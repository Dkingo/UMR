package service;

import entity.CashUser;
import entity.Manager;

public interface UserService {
	
	//ͨ���û����������ѯ�����û�
	public CashUser selectOneWithCash(Object[] paraArray) throws Exception;
	
	//ͨ���û����������ѯ�����û�
	public Manager selectOneWithManager(Object[] paraArray) throws Exception;
}
