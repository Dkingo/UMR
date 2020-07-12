package service.Impl;

import java.util.List;

import dao.Impl.BaseDaoImpl;
import entity.CashUser;
import entity.Manager;
import service.UserService;

public class UserServiceImpl implements UserService{

	@Override
	public CashUser selectOneWithCash(Object[] paraArray) throws Exception {
		CashUser user = new CashUser(); 
		BaseDaoImpl dao = new BaseDaoImpl();
		String sql = "select cash_username, cash_password from cashier where cash_userId =? and cash_password=?";
		List list = dao.select(sql, 2, paraArray);
		if (!list.isEmpty()) {
			user.setCash_username((String) ((Object[]) list.get(0))[0]);
			user.setCash_password((String) ((Object[]) list.get(0))[1]);
			return user;
		}
		return null;
	}

	@Override
	public Manager selectOneWithManager(Object[] paraArray) throws Exception {
		Manager user = new Manager();
		BaseDaoImpl dao = new BaseDaoImpl();
		String sql = "select mag_username, mag_password from management where mag_username=? and mag_password=?";
		List list = dao.select(sql, 2, paraArray);
		if (!list.isEmpty()) {
			user.setMag_username((String) ((Object[]) list.get(0))[0]);
			user.setMag_password((String) ((Object[]) list.get(0))[1]);
			return user;
		}
		return null;
	}

}
