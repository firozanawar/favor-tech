package in.android.favortechdemo.entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "user")
public class UserEntity {


    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "mobile")
    public String mobile;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "email")
    public String email;


}
