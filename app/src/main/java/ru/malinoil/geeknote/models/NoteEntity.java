package ru.malinoil.geeknote.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.UUID;

public class NoteEntity implements Parcelable {

    private String id;
    private String name;
    private String description;
    private long createDate;
    private long deadline = 0;

    public NoteEntity() {

    }

    public NoteEntity(String name, String description) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.createDate = Calendar.getInstance().getTimeInMillis();
    }

    protected NoteEntity(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        createDate = in.readLong();
        deadline = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeLong(createDate);
        dest.writeLong(deadline);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public long getCreateDate() {
        return createDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }
}
