package ru.malinoil.geeknote.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class NoteEntity implements Parcelable {

    private String name;
    private String description;
    private Date createDate;
    private Date deadline = null;

    public NoteEntity(String name, String description) {
        this.name = name;
        this.description = description;
        this.createDate = new Date();
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public static final Creator<NoteEntity> CREATOR = new Creator<NoteEntity>() {
        @Override
        public NoteEntity createFromParcel(Parcel in) {
            return new NoteEntity(in);
        }

        @Override
        public NoteEntity[] newArray(int size) {
            return new NoteEntity[size];
        }
    };

    protected NoteEntity(Parcel in) {
        name = in.readString();
        description = in.readString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
    }
}
