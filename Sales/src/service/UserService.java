package service;

import entity.CashUser;
import entity.Manager;

public interface UserService {
	
	//通过用户名和密码查询收银用户
	public CashUser selectOneWithCash(Object[] paraArray) throws Exception;
	
	//通过用户名和密码查询收银用户
	public Manager selectOneWithManager(Object[] paraArray) throws Exception;
}
