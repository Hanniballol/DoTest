package com.msymobile.dotest.ui.main;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.msymobile.dotest.entity.FixerEntity;
import com.msymobile.dotest.util.SortUtil;

import java.util.Collections;
import java.util.List;

import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_DRAG;
import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_IDLE;

/**
 * autour: hannibal
 * date: 2018/4/6
 * e-mail:404769122@qq.com
 * description:拖拽实现
 */
public class MainItemTouchHelper extends ItemTouchHelper.Callback {

    private MainAdapter mAdapter;

    public MainItemTouchHelper(MainAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlag = ItemTouchHelper.DOWN | ItemTouchHelper.UP;
        return makeFlag(ACTION_STATE_IDLE, dragFlag)
                | makeFlag(ACTION_STATE_DRAG, dragFlag);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        List<FixerEntity.ItemInfo> fixers = mAdapter.getFixers();
        mAdapter.notifyItemMoved(fromPosition,toPosition);
        Collections.swap(fixers,fromPosition,toPosition);
        SortUtil.saveSortData(fixers);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public boolean canDropOver(RecyclerView recyclerView, RecyclerView.ViewHolder current, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }
}
