package com.cs.zhishu.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Othell0 on 2016/7/13.
 */
public class DailyListBean implements Parcelable {
    private String date;

    private List<DailyBean> stories;

    private List<TopDailies> top_stories;

    public List<TopDailies> getTop_stories()
    {

        return top_stories;
    }

    public void setTop_stories(List<TopDailies> top_stories)
    {

        this.top_stories = top_stories;
    }

    public String getDate()
    {

        return date;
    }

    public void setDate(String date)
    {

        this.date = date;
    }

    public List<DailyBean> getStories()
    {

        return stories;
    }

    public void setStories(List<DailyBean> stories)
    {

        this.stories = stories;
    }

    @Override
    public int describeContents()
    {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {

        dest.writeString(this.date);
        dest.writeTypedList(stories);
        dest.writeTypedList(top_stories);
    }

    public DailyListBean()
    {

    }

    protected DailyListBean(Parcel in)
    {

        this.date = in.readString();
        this.stories = in.createTypedArrayList(DailyBean.CREATOR);
        this.top_stories = in.createTypedArrayList(TopDailies.CREATOR);
    }

    public static final Parcelable.Creator<DailyListBean> CREATOR = new Parcelable.Creator<DailyListBean>()
    {

        public DailyListBean createFromParcel(Parcel source)
        {

            return new DailyListBean(source);
        }

        public DailyListBean[] newArray(int size)
        {

            return new DailyListBean[size];
        }
    };
}
