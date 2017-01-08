package com.and119_idi.myfilmdatabase.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.and119_idi.myfilmdatabase.R;

/**
 * Created by Carlos Lázaro Costa on 25/12/16.
 */
@SuppressLint("InflateParams")
public class MyHelpListAdapter extends BaseExpandableListAdapter {

    private Activity mContext;
    private String[] mGroups;
    // mChildren[i] contains the children for mGroups[i].
    private CharSequence[] mChildren;

    public MyHelpListAdapter(Activity context) {
        mContext = context;
        mGroups = new String[]{
                mContext.getString(com.and119_idi.myfilmdatabase.R.string.show_films_section),
                mContext.getString(R.string.add_film_section),
                mContext.getString(R.string.delete_film_section),
                mContext.getString(R.string.change_rating_section),
                mContext.getString(R.string.search_film_by_actor_section),
        };
        mChildren = new CharSequence[]{
                mContext.getText(R.string.show_films_help),
                mContext.getText(R.string.add_film_help),
                mContext.getText(R.string.delete_film_help),
                mContext.getText(R.string.change_rating_help),
                mContext.getText(R.string.search_film_by_actor_help)
        };
    }

    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.help_section, null);
        }
        TextView section = (TextView) convertView.findViewById(R.id.section_text_view);
        section.setText(getGroup(groupPosition).toString());
        return convertView;
    }

    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.help_child_section, null);
        }
        TextView child_section = (TextView) convertView.findViewById(R.id.child_section_text_view);
        child_section.setText((CharSequence) getChild(groupPosition, childPosition));
        return convertView;
    }

    public Object getGroup(int groupPosition) {
        return mGroups[groupPosition];
    }

    public int getGroupCount() {
        return mGroups.length;
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return mChildren[groupPosition];
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public int getChildrenCount(int groupPosition) {
        return groupPosition < mChildren.length ? 1 : 0;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public boolean hasStableIds() {
        return true;
    }

}
