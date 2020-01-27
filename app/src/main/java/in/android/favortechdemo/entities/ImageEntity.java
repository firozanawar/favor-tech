package in.android.favortechdemo.entities;


import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


@Entity(tableName = "imagecache")
public class ImageEntity extends BaseObservable {


    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "url")
    @SerializedName("url")
    public String url;

    @ColumnInfo(name = "picName")
    @SerializedName("picName")
    public String picName;


}
