package main;

import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import view.LoginJFrame;

public class Enhance {
	public static void main(String[] args) {
		try {
			// ���ô��ڱ߿���ʽ  
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			UIManager.put("RootPane.setupButtonVisible", false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		new LoginJFrame();
	}
}
