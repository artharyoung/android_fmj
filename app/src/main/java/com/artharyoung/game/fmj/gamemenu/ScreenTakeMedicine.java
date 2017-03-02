package com.artharyoung.game.fmj.gamemenu;


import android.graphics.Canvas;

import com.artharyoung.game.fmj.GameView;
import com.artharyoung.game.fmj.Global;
import com.artharyoung.game.fmj.goods.BaseGoods;
import com.artharyoung.game.fmj.goods.GoodsMedicine;
import com.artharyoung.game.fmj.goods.IEatMedicine;
import com.artharyoung.game.fmj.graphics.TextRender;
import com.artharyoung.game.fmj.scene.ScreenMainGame;
import com.artharyoung.game.fmj.views.BaseScreen;

public class ScreenTakeMedicine extends BaseScreen {
	
	private BaseGoods mMedicine;
	
	private int mStatePageIndex = 0; // 人物属性页，共两页
	
	private int mActorIndex = -1;
	
	public ScreenTakeMedicine(BaseGoods goods) {
		mMedicine = goods;
		if (ScreenMainGame.sPlayerList.size() > 0) {
			mActorIndex = 0;
		}
	}

	@Override
	public void update(long delta) {
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawColor(Global.COLOR_WHITE);
		ScreenMainGame.sPlayerList.get(mActorIndex).drawState(canvas, mStatePageIndex);
		ScreenMainGame.sPlayerList.get(mActorIndex).drawHead(canvas, 5, 60);
		if (mMedicine.getGoodsNum() > 0) {
			mMedicine.draw(canvas, 5, 10);
			TextRender.drawText(canvas, "" + mMedicine.getGoodsNum(), 13, 35);
		}
	}

	@Override
	public void onKeyDown(int key) {
		if (key == Global.KEY_PAGEDOWN) {
			mStatePageIndex = 1;
		} else if (key == Global.KEY_PAGEUP) {
			mStatePageIndex = 0;
		} else if (key == Global.KEY_LEFT && mActorIndex > 0) {
			--mActorIndex;
		} else if (key == Global.KEY_RIGHT && mActorIndex < ScreenMainGame.sPlayerList.size() - 1) {
			++mActorIndex;
		}
	}

	@Override
	public void onKeyUp(int key) {
		if (key == Global.KEY_CANCEL) {
			GameView.getInstance().popScreen();
		} else if (key == Global.KEY_ENTER) {
			if (mMedicine.getGoodsNum() > 0) {
				if (mMedicine.getType() == 9 && ((GoodsMedicine)mMedicine).effectAll()) { // 普通药物，判断是否全体
					for (int i = ScreenMainGame.sPlayerList.size() - 1; i >= 0; i--) {
						((IEatMedicine)mMedicine).eat(ScreenMainGame.sPlayerList.get(i));
					}
				} else { // 仙药、灵药 不具有全体效果
					((IEatMedicine)mMedicine).eat(ScreenMainGame.sPlayerList.get(mActorIndex));
				}
			} else {
				GameView.getInstance().popScreen();
			}
		}
	}

}
