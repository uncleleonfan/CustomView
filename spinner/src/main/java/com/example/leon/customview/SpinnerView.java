package com.example.leon.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 2017/2/24.
 */

public class SpinnerView extends RelativeLayout {

    private EditText mEditText;
    private ImageView mArrow;

    private List<String> mDataList;
    private PopupWindow mPopupWindow;

    public SpinnerView(Context context) {
        this(context, null);
    }

    public SpinnerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_spinner, this);
        mEditText = (EditText) findViewById(R.id.edit);
        mArrow = (ImageView) findViewById(R.id.arrow);
        mArrow.setOnClickListener(mOnClickListener);
        mockDataList();
    }

    private void mockDataList() {
        mDataList = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            mDataList.add(String.valueOf(i));
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            popupList();
        }
    };

    private void popupList() {
        if (mPopupWindow == null) {
            int height = getResources().getDimensionPixelSize(R.dimen.popup_height);
            mPopupWindow = new PopupWindow(mEditText.getWidth(), height);
//        TextView textView = new TextView(this);
//        textView.setBackgroundColor(Color.BLUE);
//        popupWindow.setContentView(textView);
            ListView listView = new ListView(getContext());
            listView.setBackgroundResource(R.mipmap.listview_background);
            listView.setAdapter(mBaseAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mEditText.setText(mDataList.get(position));
                    mEditText.setSelection(mDataList.get(position).length());
                    mPopupWindow.dismiss();
                }
            });
            mPopupWindow.setContentView(listView);
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setFocusable(true);
        }

        mPopupWindow.showAsDropDown(mEditText);

    }

    private BaseAdapter mBaseAdapter = new BaseAdapter() {

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_list_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final String data = mDataList.get(position);
            viewHolder.mUserId.setText(data);
            viewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDataList.remove(data);
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }
    };

    private class ViewHolder {
        TextView mUserId;
        ImageView mDelete;

        public ViewHolder(View root) {
            mUserId = (TextView) root.findViewById(R.id.user_id);
            mDelete = (ImageView) root.findViewById(R.id.delete);
        }
    }
}
